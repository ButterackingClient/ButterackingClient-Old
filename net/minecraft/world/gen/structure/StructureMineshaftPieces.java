/*     */ package net.minecraft.world.gen.structure;
/*     */ import com.google.common.collect.Lists;
/*     */ import java.util.List;
/*     */ import java.util.Random;
/*     */ import net.minecraft.block.material.Material;
/*     */ import net.minecraft.block.state.IBlockState;
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.entity.item.EntityMinecartChest;
/*     */ import net.minecraft.init.Blocks;
/*     */ import net.minecraft.init.Items;
/*     */ import net.minecraft.inventory.IInventory;
/*     */ import net.minecraft.item.EnumDyeColor;
/*     */ import net.minecraft.item.Item;
/*     */ import net.minecraft.nbt.NBTBase;
/*     */ import net.minecraft.nbt.NBTTagCompound;
/*     */ import net.minecraft.nbt.NBTTagList;
/*     */ import net.minecraft.tileentity.TileEntity;
/*     */ import net.minecraft.tileentity.TileEntityMobSpawner;
/*     */ import net.minecraft.util.BlockPos;
/*     */ import net.minecraft.util.EnumFacing;
/*     */ import net.minecraft.util.Vec3i;
/*     */ import net.minecraft.util.WeightedRandomChestContent;
/*     */ import net.minecraft.world.World;
/*     */ 
/*     */ public class StructureMineshaftPieces {
/*  26 */   private static final List<WeightedRandomChestContent> CHEST_CONTENT_WEIGHT_LIST = Lists.newArrayList((Object[])new WeightedRandomChestContent[] { new WeightedRandomChestContent(Items.iron_ingot, 0, 1, 5, 10), new WeightedRandomChestContent(Items.gold_ingot, 0, 1, 3, 5), new WeightedRandomChestContent(Items.redstone, 0, 4, 9, 5), new WeightedRandomChestContent(Items.dye, EnumDyeColor.BLUE.getDyeDamage(), 4, 9, 5), new WeightedRandomChestContent(Items.diamond, 0, 1, 2, 3), new WeightedRandomChestContent(Items.coal, 0, 3, 8, 10), new WeightedRandomChestContent(Items.bread, 0, 1, 3, 15), new WeightedRandomChestContent(Items.iron_pickaxe, 0, 1, 1, 1), new WeightedRandomChestContent(Item.getItemFromBlock(Blocks.rail), 0, 4, 8, 1), new WeightedRandomChestContent(Items.melon_seeds, 0, 2, 4, 10), new WeightedRandomChestContent(Items.pumpkin_seeds, 0, 2, 4, 10), new WeightedRandomChestContent(Items.saddle, 0, 1, 1, 3), new WeightedRandomChestContent(Items.iron_horse_armor, 0, 1, 1, 1) });
/*     */   
/*     */   public static void registerStructurePieces() {
/*  29 */     MapGenStructureIO.registerStructureComponent((Class)Corridor.class, "MSCorridor");
/*  30 */     MapGenStructureIO.registerStructureComponent((Class)Cross.class, "MSCrossing");
/*  31 */     MapGenStructureIO.registerStructureComponent((Class)Room.class, "MSRoom");
/*  32 */     MapGenStructureIO.registerStructureComponent((Class)Stairs.class, "MSStairs");
/*     */   }
/*     */   
/*     */   private static StructureComponent func_175892_a(List<StructureComponent> listIn, Random rand, int x, int y, int z, EnumFacing facing, int type) {
/*  36 */     int i = rand.nextInt(100);
/*     */     
/*  38 */     if (i >= 80) {
/*  39 */       StructureBoundingBox structureboundingbox = Cross.func_175813_a(listIn, rand, x, y, z, facing);
/*     */       
/*  41 */       if (structureboundingbox != null) {
/*  42 */         return new Cross(type, rand, structureboundingbox, facing);
/*     */       }
/*  44 */     } else if (i >= 70) {
/*  45 */       StructureBoundingBox structureboundingbox1 = Stairs.func_175812_a(listIn, rand, x, y, z, facing);
/*     */       
/*  47 */       if (structureboundingbox1 != null) {
/*  48 */         return new Stairs(type, rand, structureboundingbox1, facing);
/*     */       }
/*     */     } else {
/*  51 */       StructureBoundingBox structureboundingbox2 = Corridor.func_175814_a(listIn, rand, x, y, z, facing);
/*     */       
/*  53 */       if (structureboundingbox2 != null) {
/*  54 */         return new Corridor(type, rand, structureboundingbox2, facing);
/*     */       }
/*     */     } 
/*     */     
/*  58 */     return null;
/*     */   }
/*     */   
/*     */   private static StructureComponent func_175890_b(StructureComponent componentIn, List<StructureComponent> listIn, Random rand, int x, int y, int z, EnumFacing facing, int type) {
/*  62 */     if (type > 8)
/*  63 */       return null; 
/*  64 */     if (Math.abs(x - (componentIn.getBoundingBox()).minX) <= 80 && Math.abs(z - (componentIn.getBoundingBox()).minZ) <= 80) {
/*  65 */       StructureComponent structurecomponent = func_175892_a(listIn, rand, x, y, z, facing, type + 1);
/*     */       
/*  67 */       if (structurecomponent != null) {
/*  68 */         listIn.add(structurecomponent);
/*  69 */         structurecomponent.buildComponent(componentIn, listIn, rand);
/*     */       } 
/*     */       
/*  72 */       return structurecomponent;
/*     */     } 
/*  74 */     return null;
/*     */   }
/*     */   
/*     */   public static class Corridor
/*     */     extends StructureComponent
/*     */   {
/*     */     private boolean hasRails;
/*     */     private boolean hasSpiders;
/*     */     private boolean spawnerPlaced;
/*     */     private int sectionCount;
/*     */     
/*     */     public Corridor() {}
/*     */     
/*     */     protected void writeStructureToNBT(NBTTagCompound tagCompound) {
/*  88 */       tagCompound.setBoolean("hr", this.hasRails);
/*  89 */       tagCompound.setBoolean("sc", this.hasSpiders);
/*  90 */       tagCompound.setBoolean("hps", this.spawnerPlaced);
/*  91 */       tagCompound.setInteger("Num", this.sectionCount);
/*     */     }
/*     */     
/*     */     protected void readStructureFromNBT(NBTTagCompound tagCompound) {
/*  95 */       this.hasRails = tagCompound.getBoolean("hr");
/*  96 */       this.hasSpiders = tagCompound.getBoolean("sc");
/*  97 */       this.spawnerPlaced = tagCompound.getBoolean("hps");
/*  98 */       this.sectionCount = tagCompound.getInteger("Num");
/*     */     }
/*     */     
/*     */     public Corridor(int type, Random rand, StructureBoundingBox structurebb, EnumFacing facing) {
/* 102 */       super(type);
/* 103 */       this.coordBaseMode = facing;
/* 104 */       this.boundingBox = structurebb;
/* 105 */       this.hasRails = (rand.nextInt(3) == 0);
/* 106 */       this.hasSpiders = (!this.hasRails && rand.nextInt(23) == 0);
/*     */       
/* 108 */       if (this.coordBaseMode != EnumFacing.NORTH && this.coordBaseMode != EnumFacing.SOUTH) {
/* 109 */         this.sectionCount = structurebb.getXSize() / 5;
/*     */       } else {
/* 111 */         this.sectionCount = structurebb.getZSize() / 5;
/*     */       } 
/*     */     }
/*     */     
/*     */     public static StructureBoundingBox func_175814_a(List<StructureComponent> p_175814_0_, Random rand, int x, int y, int z, EnumFacing facing) {
/* 116 */       StructureBoundingBox structureboundingbox = new StructureBoundingBox(x, y, z, x, y + 2, z);
/*     */       
/*     */       int i;
/* 119 */       for (i = rand.nextInt(3) + 2; i > 0; i--) {
/* 120 */         int j = i * 5;
/*     */         
/* 122 */         switch (facing) {
/*     */           case NORTH:
/* 124 */             structureboundingbox.maxX = x + 2;
/* 125 */             structureboundingbox.minZ = z - j - 1;
/*     */             break;
/*     */           
/*     */           case SOUTH:
/* 129 */             structureboundingbox.maxX = x + 2;
/* 130 */             structureboundingbox.maxZ = z + j - 1;
/*     */             break;
/*     */           
/*     */           case WEST:
/* 134 */             structureboundingbox.minX = x - j - 1;
/* 135 */             structureboundingbox.maxZ = z + 2;
/*     */             break;
/*     */           
/*     */           case EAST:
/* 139 */             structureboundingbox.maxX = x + j - 1;
/* 140 */             structureboundingbox.maxZ = z + 2;
/*     */             break;
/*     */         } 
/* 143 */         if (StructureComponent.findIntersecting(p_175814_0_, structureboundingbox) == null) {
/*     */           break;
/*     */         }
/*     */       } 
/*     */       
/* 148 */       return (i > 0) ? structureboundingbox : null;
/*     */     }
/*     */     
/*     */     public void buildComponent(StructureComponent componentIn, List<StructureComponent> listIn, Random rand) {
/* 152 */       int i = getComponentType();
/* 153 */       int j = rand.nextInt(4);
/*     */       
/* 155 */       if (this.coordBaseMode != null) {
/* 156 */         switch (this.coordBaseMode) {
/*     */           case NORTH:
/* 158 */             if (j <= 1) {
/* 159 */               StructureMineshaftPieces.func_175890_b(componentIn, listIn, rand, this.boundingBox.minX, this.boundingBox.minY - 1 + rand.nextInt(3), this.boundingBox.minZ - 1, this.coordBaseMode, i); break;
/* 160 */             }  if (j == 2) {
/* 161 */               StructureMineshaftPieces.func_175890_b(componentIn, listIn, rand, this.boundingBox.minX - 1, this.boundingBox.minY - 1 + rand.nextInt(3), this.boundingBox.minZ, EnumFacing.WEST, i); break;
/*     */             } 
/* 163 */             StructureMineshaftPieces.func_175890_b(componentIn, listIn, rand, this.boundingBox.maxX + 1, this.boundingBox.minY - 1 + rand.nextInt(3), this.boundingBox.minZ, EnumFacing.EAST, i);
/*     */             break;
/*     */ 
/*     */ 
/*     */           
/*     */           case SOUTH:
/* 169 */             if (j <= 1) {
/* 170 */               StructureMineshaftPieces.func_175890_b(componentIn, listIn, rand, this.boundingBox.minX, this.boundingBox.minY - 1 + rand.nextInt(3), this.boundingBox.maxZ + 1, this.coordBaseMode, i); break;
/* 171 */             }  if (j == 2) {
/* 172 */               StructureMineshaftPieces.func_175890_b(componentIn, listIn, rand, this.boundingBox.minX - 1, this.boundingBox.minY - 1 + rand.nextInt(3), this.boundingBox.maxZ - 3, EnumFacing.WEST, i); break;
/*     */             } 
/* 174 */             StructureMineshaftPieces.func_175890_b(componentIn, listIn, rand, this.boundingBox.maxX + 1, this.boundingBox.minY - 1 + rand.nextInt(3), this.boundingBox.maxZ - 3, EnumFacing.EAST, i);
/*     */             break;
/*     */ 
/*     */ 
/*     */           
/*     */           case WEST:
/* 180 */             if (j <= 1) {
/* 181 */               StructureMineshaftPieces.func_175890_b(componentIn, listIn, rand, this.boundingBox.minX - 1, this.boundingBox.minY - 1 + rand.nextInt(3), this.boundingBox.minZ, this.coordBaseMode, i); break;
/* 182 */             }  if (j == 2) {
/* 183 */               StructureMineshaftPieces.func_175890_b(componentIn, listIn, rand, this.boundingBox.minX, this.boundingBox.minY - 1 + rand.nextInt(3), this.boundingBox.minZ - 1, EnumFacing.NORTH, i); break;
/*     */             } 
/* 185 */             StructureMineshaftPieces.func_175890_b(componentIn, listIn, rand, this.boundingBox.minX, this.boundingBox.minY - 1 + rand.nextInt(3), this.boundingBox.maxZ + 1, EnumFacing.SOUTH, i);
/*     */             break;
/*     */ 
/*     */ 
/*     */           
/*     */           case EAST:
/* 191 */             if (j <= 1) {
/* 192 */               StructureMineshaftPieces.func_175890_b(componentIn, listIn, rand, this.boundingBox.maxX + 1, this.boundingBox.minY - 1 + rand.nextInt(3), this.boundingBox.minZ, this.coordBaseMode, i); break;
/* 193 */             }  if (j == 2) {
/* 194 */               StructureMineshaftPieces.func_175890_b(componentIn, listIn, rand, this.boundingBox.maxX - 3, this.boundingBox.minY - 1 + rand.nextInt(3), this.boundingBox.minZ - 1, EnumFacing.NORTH, i); break;
/*     */             } 
/* 196 */             StructureMineshaftPieces.func_175890_b(componentIn, listIn, rand, this.boundingBox.maxX - 3, this.boundingBox.minY - 1 + rand.nextInt(3), this.boundingBox.maxZ + 1, EnumFacing.SOUTH, i);
/*     */             break;
/*     */         } 
/*     */       
/*     */       }
/* 201 */       if (i < 8) {
/* 202 */         if (this.coordBaseMode != EnumFacing.NORTH && this.coordBaseMode != EnumFacing.SOUTH) {
/* 203 */           for (int i1 = this.boundingBox.minX + 3; i1 + 3 <= this.boundingBox.maxX; i1 += 5) {
/* 204 */             int j1 = rand.nextInt(5);
/*     */             
/* 206 */             if (j1 == 0) {
/* 207 */               StructureMineshaftPieces.func_175890_b(componentIn, listIn, rand, i1, this.boundingBox.minY, this.boundingBox.minZ - 1, EnumFacing.NORTH, i + 1);
/* 208 */             } else if (j1 == 1) {
/* 209 */               StructureMineshaftPieces.func_175890_b(componentIn, listIn, rand, i1, this.boundingBox.minY, this.boundingBox.maxZ + 1, EnumFacing.SOUTH, i + 1);
/*     */             } 
/*     */           } 
/*     */         } else {
/* 213 */           for (int k = this.boundingBox.minZ + 3; k + 3 <= this.boundingBox.maxZ; k += 5) {
/* 214 */             int l = rand.nextInt(5);
/*     */             
/* 216 */             if (l == 0) {
/* 217 */               StructureMineshaftPieces.func_175890_b(componentIn, listIn, rand, this.boundingBox.minX - 1, this.boundingBox.minY, k, EnumFacing.WEST, i + 1);
/* 218 */             } else if (l == 1) {
/* 219 */               StructureMineshaftPieces.func_175890_b(componentIn, listIn, rand, this.boundingBox.maxX + 1, this.boundingBox.minY, k, EnumFacing.EAST, i + 1);
/*     */             } 
/*     */           } 
/*     */         } 
/*     */       }
/*     */     }
/*     */     
/*     */     protected boolean generateChestContents(World worldIn, StructureBoundingBox boundingBoxIn, Random rand, int x, int y, int z, List<WeightedRandomChestContent> listIn, int max) {
/* 227 */       BlockPos blockpos = new BlockPos(getXWithOffset(x, z), getYWithOffset(y), getZWithOffset(x, z));
/*     */       
/* 229 */       if (boundingBoxIn.isVecInside((Vec3i)blockpos) && worldIn.getBlockState(blockpos).getBlock().getMaterial() == Material.air) {
/* 230 */         int i = rand.nextBoolean() ? 1 : 0;
/* 231 */         worldIn.setBlockState(blockpos, Blocks.rail.getStateFromMeta(getMetadataWithOffset(Blocks.rail, i)), 2);
/* 232 */         EntityMinecartChest entityminecartchest = new EntityMinecartChest(worldIn, (blockpos.getX() + 0.5F), (blockpos.getY() + 0.5F), (blockpos.getZ() + 0.5F));
/* 233 */         WeightedRandomChestContent.generateChestContents(rand, listIn, (IInventory)entityminecartchest, max);
/* 234 */         worldIn.spawnEntityInWorld((Entity)entityminecartchest);
/* 235 */         return true;
/*     */       } 
/* 237 */       return false;
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean addComponentParts(World worldIn, Random randomIn, StructureBoundingBox structureBoundingBoxIn) {
/* 242 */       if (isLiquidInStructureBoundingBox(worldIn, structureBoundingBoxIn)) {
/* 243 */         return false;
/*     */       }
/* 245 */       int i = 0;
/* 246 */       int j = 2;
/* 247 */       int k = 0;
/* 248 */       int l = 2;
/* 249 */       int i1 = this.sectionCount * 5 - 1;
/* 250 */       fillWithBlocks(worldIn, structureBoundingBoxIn, 0, 0, 0, 2, 1, i1, Blocks.air.getDefaultState(), Blocks.air.getDefaultState(), false);
/* 251 */       func_175805_a(worldIn, structureBoundingBoxIn, randomIn, 0.8F, 0, 2, 0, 2, 2, i1, Blocks.air.getDefaultState(), Blocks.air.getDefaultState(), false);
/*     */       
/* 253 */       if (this.hasSpiders) {
/* 254 */         func_175805_a(worldIn, structureBoundingBoxIn, randomIn, 0.6F, 0, 0, 0, 2, 1, i1, Blocks.web.getDefaultState(), Blocks.air.getDefaultState(), false);
/*     */       }
/*     */       
/* 257 */       for (int j1 = 0; j1 < this.sectionCount; j1++) {
/* 258 */         int k1 = 2 + j1 * 5;
/* 259 */         fillWithBlocks(worldIn, structureBoundingBoxIn, 0, 0, k1, 0, 1, k1, Blocks.oak_fence.getDefaultState(), Blocks.air.getDefaultState(), false);
/* 260 */         fillWithBlocks(worldIn, structureBoundingBoxIn, 2, 0, k1, 2, 1, k1, Blocks.oak_fence.getDefaultState(), Blocks.air.getDefaultState(), false);
/*     */         
/* 262 */         if (randomIn.nextInt(4) == 0) {
/* 263 */           fillWithBlocks(worldIn, structureBoundingBoxIn, 0, 2, k1, 0, 2, k1, Blocks.planks.getDefaultState(), Blocks.air.getDefaultState(), false);
/* 264 */           fillWithBlocks(worldIn, structureBoundingBoxIn, 2, 2, k1, 2, 2, k1, Blocks.planks.getDefaultState(), Blocks.air.getDefaultState(), false);
/*     */         } else {
/* 266 */           fillWithBlocks(worldIn, structureBoundingBoxIn, 0, 2, k1, 2, 2, k1, Blocks.planks.getDefaultState(), Blocks.air.getDefaultState(), false);
/*     */         } 
/*     */         
/* 269 */         randomlyPlaceBlock(worldIn, structureBoundingBoxIn, randomIn, 0.1F, 0, 2, k1 - 1, Blocks.web.getDefaultState());
/* 270 */         randomlyPlaceBlock(worldIn, structureBoundingBoxIn, randomIn, 0.1F, 2, 2, k1 - 1, Blocks.web.getDefaultState());
/* 271 */         randomlyPlaceBlock(worldIn, structureBoundingBoxIn, randomIn, 0.1F, 0, 2, k1 + 1, Blocks.web.getDefaultState());
/* 272 */         randomlyPlaceBlock(worldIn, structureBoundingBoxIn, randomIn, 0.1F, 2, 2, k1 + 1, Blocks.web.getDefaultState());
/* 273 */         randomlyPlaceBlock(worldIn, structureBoundingBoxIn, randomIn, 0.05F, 0, 2, k1 - 2, Blocks.web.getDefaultState());
/* 274 */         randomlyPlaceBlock(worldIn, structureBoundingBoxIn, randomIn, 0.05F, 2, 2, k1 - 2, Blocks.web.getDefaultState());
/* 275 */         randomlyPlaceBlock(worldIn, structureBoundingBoxIn, randomIn, 0.05F, 0, 2, k1 + 2, Blocks.web.getDefaultState());
/* 276 */         randomlyPlaceBlock(worldIn, structureBoundingBoxIn, randomIn, 0.05F, 2, 2, k1 + 2, Blocks.web.getDefaultState());
/* 277 */         randomlyPlaceBlock(worldIn, structureBoundingBoxIn, randomIn, 0.05F, 1, 2, k1 - 1, Blocks.torch.getStateFromMeta(EnumFacing.UP.getIndex()));
/* 278 */         randomlyPlaceBlock(worldIn, structureBoundingBoxIn, randomIn, 0.05F, 1, 2, k1 + 1, Blocks.torch.getStateFromMeta(EnumFacing.UP.getIndex()));
/*     */         
/* 280 */         if (randomIn.nextInt(100) == 0) {
/* 281 */           generateChestContents(worldIn, structureBoundingBoxIn, randomIn, 2, 0, k1 - 1, WeightedRandomChestContent.func_177629_a(StructureMineshaftPieces.CHEST_CONTENT_WEIGHT_LIST, new WeightedRandomChestContent[] { Items.enchanted_book.getRandom(randomIn) }), 3 + randomIn.nextInt(4));
/*     */         }
/*     */         
/* 284 */         if (randomIn.nextInt(100) == 0) {
/* 285 */           generateChestContents(worldIn, structureBoundingBoxIn, randomIn, 0, 0, k1 + 1, WeightedRandomChestContent.func_177629_a(StructureMineshaftPieces.CHEST_CONTENT_WEIGHT_LIST, new WeightedRandomChestContent[] { Items.enchanted_book.getRandom(randomIn) }), 3 + randomIn.nextInt(4));
/*     */         }
/*     */         
/* 288 */         if (this.hasSpiders && !this.spawnerPlaced) {
/* 289 */           int l1 = getYWithOffset(0);
/* 290 */           int i2 = k1 - 1 + randomIn.nextInt(3);
/* 291 */           int j2 = getXWithOffset(1, i2);
/* 292 */           i2 = getZWithOffset(1, i2);
/* 293 */           BlockPos blockpos = new BlockPos(j2, l1, i2);
/*     */           
/* 295 */           if (structureBoundingBoxIn.isVecInside((Vec3i)blockpos)) {
/* 296 */             this.spawnerPlaced = true;
/* 297 */             worldIn.setBlockState(blockpos, Blocks.mob_spawner.getDefaultState(), 2);
/* 298 */             TileEntity tileentity = worldIn.getTileEntity(blockpos);
/*     */             
/* 300 */             if (tileentity instanceof TileEntityMobSpawner) {
/* 301 */               ((TileEntityMobSpawner)tileentity).getSpawnerBaseLogic().setEntityName("CaveSpider");
/*     */             }
/*     */           } 
/*     */         } 
/*     */       } 
/*     */       
/* 307 */       for (int k2 = 0; k2 <= 2; k2++) {
/* 308 */         for (int i3 = 0; i3 <= i1; i3++) {
/* 309 */           int j3 = -1;
/* 310 */           IBlockState iblockstate1 = getBlockStateFromPos(worldIn, k2, j3, i3, structureBoundingBoxIn);
/*     */           
/* 312 */           if (iblockstate1.getBlock().getMaterial() == Material.air) {
/* 313 */             int k3 = -1;
/* 314 */             setBlockState(worldIn, Blocks.planks.getDefaultState(), k2, k3, i3, structureBoundingBoxIn);
/*     */           } 
/*     */         } 
/*     */       } 
/*     */       
/* 319 */       if (this.hasRails) {
/* 320 */         for (int l2 = 0; l2 <= i1; l2++) {
/* 321 */           IBlockState iblockstate = getBlockStateFromPos(worldIn, 1, -1, l2, structureBoundingBoxIn);
/*     */           
/* 323 */           if (iblockstate.getBlock().getMaterial() != Material.air && iblockstate.getBlock().isFullBlock()) {
/* 324 */             randomlyPlaceBlock(worldIn, structureBoundingBoxIn, randomIn, 0.7F, 1, 0, l2, Blocks.rail.getStateFromMeta(getMetadataWithOffset(Blocks.rail, 0)));
/*     */           }
/*     */         } 
/*     */       }
/*     */       
/* 329 */       return true;
/*     */     }
/*     */   }
/*     */   
/*     */   public static class Cross
/*     */     extends StructureComponent
/*     */   {
/*     */     private EnumFacing corridorDirection;
/*     */     private boolean isMultipleFloors;
/*     */     
/*     */     public Cross() {}
/*     */     
/*     */     protected void writeStructureToNBT(NBTTagCompound tagCompound) {
/* 342 */       tagCompound.setBoolean("tf", this.isMultipleFloors);
/* 343 */       tagCompound.setInteger("D", this.corridorDirection.getHorizontalIndex());
/*     */     }
/*     */     
/*     */     protected void readStructureFromNBT(NBTTagCompound tagCompound) {
/* 347 */       this.isMultipleFloors = tagCompound.getBoolean("tf");
/* 348 */       this.corridorDirection = EnumFacing.getHorizontal(tagCompound.getInteger("D"));
/*     */     }
/*     */     
/*     */     public Cross(int type, Random rand, StructureBoundingBox structurebb, EnumFacing facing) {
/* 352 */       super(type);
/* 353 */       this.corridorDirection = facing;
/* 354 */       this.boundingBox = structurebb;
/* 355 */       this.isMultipleFloors = (structurebb.getYSize() > 3);
/*     */     }
/*     */     
/*     */     public static StructureBoundingBox func_175813_a(List<StructureComponent> listIn, Random rand, int x, int y, int z, EnumFacing facing) {
/* 359 */       StructureBoundingBox structureboundingbox = new StructureBoundingBox(x, y, z, x, y + 2, z);
/*     */       
/* 361 */       if (rand.nextInt(4) == 0) {
/* 362 */         structureboundingbox.maxY += 4;
/*     */       }
/*     */       
/* 365 */       switch (facing) {
/*     */         case NORTH:
/* 367 */           structureboundingbox.minX = x - 1;
/* 368 */           structureboundingbox.maxX = x + 3;
/* 369 */           structureboundingbox.minZ = z - 4;
/*     */           break;
/*     */         
/*     */         case SOUTH:
/* 373 */           structureboundingbox.minX = x - 1;
/* 374 */           structureboundingbox.maxX = x + 3;
/* 375 */           structureboundingbox.maxZ = z + 4;
/*     */           break;
/*     */         
/*     */         case WEST:
/* 379 */           structureboundingbox.minX = x - 4;
/* 380 */           structureboundingbox.minZ = z - 1;
/* 381 */           structureboundingbox.maxZ = z + 3;
/*     */           break;
/*     */         
/*     */         case EAST:
/* 385 */           structureboundingbox.maxX = x + 4;
/* 386 */           structureboundingbox.minZ = z - 1;
/* 387 */           structureboundingbox.maxZ = z + 3;
/*     */           break;
/*     */       } 
/* 390 */       return (StructureComponent.findIntersecting(listIn, structureboundingbox) != null) ? null : structureboundingbox;
/*     */     }
/*     */     
/*     */     public void buildComponent(StructureComponent componentIn, List<StructureComponent> listIn, Random rand) {
/* 394 */       int i = getComponentType();
/*     */       
/* 396 */       switch (this.corridorDirection) {
/*     */         case NORTH:
/* 398 */           StructureMineshaftPieces.func_175890_b(componentIn, listIn, rand, this.boundingBox.minX + 1, this.boundingBox.minY, this.boundingBox.minZ - 1, EnumFacing.NORTH, i);
/* 399 */           StructureMineshaftPieces.func_175890_b(componentIn, listIn, rand, this.boundingBox.minX - 1, this.boundingBox.minY, this.boundingBox.minZ + 1, EnumFacing.WEST, i);
/* 400 */           StructureMineshaftPieces.func_175890_b(componentIn, listIn, rand, this.boundingBox.maxX + 1, this.boundingBox.minY, this.boundingBox.minZ + 1, EnumFacing.EAST, i);
/*     */           break;
/*     */         
/*     */         case SOUTH:
/* 404 */           StructureMineshaftPieces.func_175890_b(componentIn, listIn, rand, this.boundingBox.minX + 1, this.boundingBox.minY, this.boundingBox.maxZ + 1, EnumFacing.SOUTH, i);
/* 405 */           StructureMineshaftPieces.func_175890_b(componentIn, listIn, rand, this.boundingBox.minX - 1, this.boundingBox.minY, this.boundingBox.minZ + 1, EnumFacing.WEST, i);
/* 406 */           StructureMineshaftPieces.func_175890_b(componentIn, listIn, rand, this.boundingBox.maxX + 1, this.boundingBox.minY, this.boundingBox.minZ + 1, EnumFacing.EAST, i);
/*     */           break;
/*     */         
/*     */         case WEST:
/* 410 */           StructureMineshaftPieces.func_175890_b(componentIn, listIn, rand, this.boundingBox.minX + 1, this.boundingBox.minY, this.boundingBox.minZ - 1, EnumFacing.NORTH, i);
/* 411 */           StructureMineshaftPieces.func_175890_b(componentIn, listIn, rand, this.boundingBox.minX + 1, this.boundingBox.minY, this.boundingBox.maxZ + 1, EnumFacing.SOUTH, i);
/* 412 */           StructureMineshaftPieces.func_175890_b(componentIn, listIn, rand, this.boundingBox.minX - 1, this.boundingBox.minY, this.boundingBox.minZ + 1, EnumFacing.WEST, i);
/*     */           break;
/*     */         
/*     */         case EAST:
/* 416 */           StructureMineshaftPieces.func_175890_b(componentIn, listIn, rand, this.boundingBox.minX + 1, this.boundingBox.minY, this.boundingBox.minZ - 1, EnumFacing.NORTH, i);
/* 417 */           StructureMineshaftPieces.func_175890_b(componentIn, listIn, rand, this.boundingBox.minX + 1, this.boundingBox.minY, this.boundingBox.maxZ + 1, EnumFacing.SOUTH, i);
/* 418 */           StructureMineshaftPieces.func_175890_b(componentIn, listIn, rand, this.boundingBox.maxX + 1, this.boundingBox.minY, this.boundingBox.minZ + 1, EnumFacing.EAST, i);
/*     */           break;
/*     */       } 
/* 421 */       if (this.isMultipleFloors) {
/* 422 */         if (rand.nextBoolean()) {
/* 423 */           StructureMineshaftPieces.func_175890_b(componentIn, listIn, rand, this.boundingBox.minX + 1, this.boundingBox.minY + 3 + 1, this.boundingBox.minZ - 1, EnumFacing.NORTH, i);
/*     */         }
/*     */         
/* 426 */         if (rand.nextBoolean()) {
/* 427 */           StructureMineshaftPieces.func_175890_b(componentIn, listIn, rand, this.boundingBox.minX - 1, this.boundingBox.minY + 3 + 1, this.boundingBox.minZ + 1, EnumFacing.WEST, i);
/*     */         }
/*     */         
/* 430 */         if (rand.nextBoolean()) {
/* 431 */           StructureMineshaftPieces.func_175890_b(componentIn, listIn, rand, this.boundingBox.maxX + 1, this.boundingBox.minY + 3 + 1, this.boundingBox.minZ + 1, EnumFacing.EAST, i);
/*     */         }
/*     */         
/* 434 */         if (rand.nextBoolean()) {
/* 435 */           StructureMineshaftPieces.func_175890_b(componentIn, listIn, rand, this.boundingBox.minX + 1, this.boundingBox.minY + 3 + 1, this.boundingBox.maxZ + 1, EnumFacing.SOUTH, i);
/*     */         }
/*     */       } 
/*     */     }
/*     */     
/*     */     public boolean addComponentParts(World worldIn, Random randomIn, StructureBoundingBox structureBoundingBoxIn) {
/* 441 */       if (isLiquidInStructureBoundingBox(worldIn, structureBoundingBoxIn)) {
/* 442 */         return false;
/*     */       }
/* 444 */       if (this.isMultipleFloors) {
/* 445 */         fillWithBlocks(worldIn, structureBoundingBoxIn, this.boundingBox.minX + 1, this.boundingBox.minY, this.boundingBox.minZ, this.boundingBox.maxX - 1, this.boundingBox.minY + 3 - 1, this.boundingBox.maxZ, Blocks.air.getDefaultState(), Blocks.air.getDefaultState(), false);
/* 446 */         fillWithBlocks(worldIn, structureBoundingBoxIn, this.boundingBox.minX, this.boundingBox.minY, this.boundingBox.minZ + 1, this.boundingBox.maxX, this.boundingBox.minY + 3 - 1, this.boundingBox.maxZ - 1, Blocks.air.getDefaultState(), Blocks.air.getDefaultState(), false);
/* 447 */         fillWithBlocks(worldIn, structureBoundingBoxIn, this.boundingBox.minX + 1, this.boundingBox.maxY - 2, this.boundingBox.minZ, this.boundingBox.maxX - 1, this.boundingBox.maxY, this.boundingBox.maxZ, Blocks.air.getDefaultState(), Blocks.air.getDefaultState(), false);
/* 448 */         fillWithBlocks(worldIn, structureBoundingBoxIn, this.boundingBox.minX, this.boundingBox.maxY - 2, this.boundingBox.minZ + 1, this.boundingBox.maxX, this.boundingBox.maxY, this.boundingBox.maxZ - 1, Blocks.air.getDefaultState(), Blocks.air.getDefaultState(), false);
/* 449 */         fillWithBlocks(worldIn, structureBoundingBoxIn, this.boundingBox.minX + 1, this.boundingBox.minY + 3, this.boundingBox.minZ + 1, this.boundingBox.maxX - 1, this.boundingBox.minY + 3, this.boundingBox.maxZ - 1, Blocks.air.getDefaultState(), Blocks.air.getDefaultState(), false);
/*     */       } else {
/* 451 */         fillWithBlocks(worldIn, structureBoundingBoxIn, this.boundingBox.minX + 1, this.boundingBox.minY, this.boundingBox.minZ, this.boundingBox.maxX - 1, this.boundingBox.maxY, this.boundingBox.maxZ, Blocks.air.getDefaultState(), Blocks.air.getDefaultState(), false);
/* 452 */         fillWithBlocks(worldIn, structureBoundingBoxIn, this.boundingBox.minX, this.boundingBox.minY, this.boundingBox.minZ + 1, this.boundingBox.maxX, this.boundingBox.maxY, this.boundingBox.maxZ - 1, Blocks.air.getDefaultState(), Blocks.air.getDefaultState(), false);
/*     */       } 
/*     */       
/* 455 */       fillWithBlocks(worldIn, structureBoundingBoxIn, this.boundingBox.minX + 1, this.boundingBox.minY, this.boundingBox.minZ + 1, this.boundingBox.minX + 1, this.boundingBox.maxY, this.boundingBox.minZ + 1, Blocks.planks.getDefaultState(), Blocks.air.getDefaultState(), false);
/* 456 */       fillWithBlocks(worldIn, structureBoundingBoxIn, this.boundingBox.minX + 1, this.boundingBox.minY, this.boundingBox.maxZ - 1, this.boundingBox.minX + 1, this.boundingBox.maxY, this.boundingBox.maxZ - 1, Blocks.planks.getDefaultState(), Blocks.air.getDefaultState(), false);
/* 457 */       fillWithBlocks(worldIn, structureBoundingBoxIn, this.boundingBox.maxX - 1, this.boundingBox.minY, this.boundingBox.minZ + 1, this.boundingBox.maxX - 1, this.boundingBox.maxY, this.boundingBox.minZ + 1, Blocks.planks.getDefaultState(), Blocks.air.getDefaultState(), false);
/* 458 */       fillWithBlocks(worldIn, structureBoundingBoxIn, this.boundingBox.maxX - 1, this.boundingBox.minY, this.boundingBox.maxZ - 1, this.boundingBox.maxX - 1, this.boundingBox.maxY, this.boundingBox.maxZ - 1, Blocks.planks.getDefaultState(), Blocks.air.getDefaultState(), false);
/*     */       
/* 460 */       for (int i = this.boundingBox.minX; i <= this.boundingBox.maxX; i++) {
/* 461 */         for (int j = this.boundingBox.minZ; j <= this.boundingBox.maxZ; j++) {
/* 462 */           if (getBlockStateFromPos(worldIn, i, this.boundingBox.minY - 1, j, structureBoundingBoxIn).getBlock().getMaterial() == Material.air) {
/* 463 */             setBlockState(worldIn, Blocks.planks.getDefaultState(), i, this.boundingBox.minY - 1, j, structureBoundingBoxIn);
/*     */           }
/*     */         } 
/*     */       } 
/*     */       
/* 468 */       return true;
/*     */     }
/*     */   }
/*     */   
/*     */   public static class Room
/*     */     extends StructureComponent {
/* 474 */     private List<StructureBoundingBox> roomsLinkedToTheRoom = Lists.newLinkedList();
/*     */ 
/*     */     
/*     */     public Room() {}
/*     */     
/*     */     public Room(int type, Random rand, int x, int z) {
/* 480 */       super(type);
/* 481 */       this.boundingBox = new StructureBoundingBox(x, 50, z, x + 7 + rand.nextInt(6), 54 + rand.nextInt(6), z + 7 + rand.nextInt(6));
/*     */     }
/*     */     
/*     */     public void buildComponent(StructureComponent componentIn, List<StructureComponent> listIn, Random rand) {
/* 485 */       int i = getComponentType();
/* 486 */       int j = this.boundingBox.getYSize() - 3 - 1;
/*     */       
/* 488 */       if (j <= 0) {
/* 489 */         j = 1;
/*     */       }
/*     */       int k;
/* 492 */       for (k = 0; k < this.boundingBox.getXSize(); k += 4) {
/* 493 */         k += rand.nextInt(this.boundingBox.getXSize());
/*     */         
/* 495 */         if (k + 3 > this.boundingBox.getXSize()) {
/*     */           break;
/*     */         }
/*     */         
/* 499 */         StructureComponent structurecomponent = StructureMineshaftPieces.func_175890_b(componentIn, listIn, rand, this.boundingBox.minX + k, this.boundingBox.minY + rand.nextInt(j) + 1, this.boundingBox.minZ - 1, EnumFacing.NORTH, i);
/*     */         
/* 501 */         if (structurecomponent != null) {
/* 502 */           StructureBoundingBox structureboundingbox = structurecomponent.getBoundingBox();
/* 503 */           this.roomsLinkedToTheRoom.add(new StructureBoundingBox(structureboundingbox.minX, structureboundingbox.minY, this.boundingBox.minZ, structureboundingbox.maxX, structureboundingbox.maxY, this.boundingBox.minZ + 1));
/*     */         } 
/*     */       } 
/*     */       
/* 507 */       for (k = 0; k < this.boundingBox.getXSize(); k += 4) {
/* 508 */         k += rand.nextInt(this.boundingBox.getXSize());
/*     */         
/* 510 */         if (k + 3 > this.boundingBox.getXSize()) {
/*     */           break;
/*     */         }
/*     */         
/* 514 */         StructureComponent structurecomponent1 = StructureMineshaftPieces.func_175890_b(componentIn, listIn, rand, this.boundingBox.minX + k, this.boundingBox.minY + rand.nextInt(j) + 1, this.boundingBox.maxZ + 1, EnumFacing.SOUTH, i);
/*     */         
/* 516 */         if (structurecomponent1 != null) {
/* 517 */           StructureBoundingBox structureboundingbox1 = structurecomponent1.getBoundingBox();
/* 518 */           this.roomsLinkedToTheRoom.add(new StructureBoundingBox(structureboundingbox1.minX, structureboundingbox1.minY, this.boundingBox.maxZ - 1, structureboundingbox1.maxX, structureboundingbox1.maxY, this.boundingBox.maxZ));
/*     */         } 
/*     */       } 
/*     */       
/* 522 */       for (k = 0; k < this.boundingBox.getZSize(); k += 4) {
/* 523 */         k += rand.nextInt(this.boundingBox.getZSize());
/*     */         
/* 525 */         if (k + 3 > this.boundingBox.getZSize()) {
/*     */           break;
/*     */         }
/*     */         
/* 529 */         StructureComponent structurecomponent2 = StructureMineshaftPieces.func_175890_b(componentIn, listIn, rand, this.boundingBox.minX - 1, this.boundingBox.minY + rand.nextInt(j) + 1, this.boundingBox.minZ + k, EnumFacing.WEST, i);
/*     */         
/* 531 */         if (structurecomponent2 != null) {
/* 532 */           StructureBoundingBox structureboundingbox2 = structurecomponent2.getBoundingBox();
/* 533 */           this.roomsLinkedToTheRoom.add(new StructureBoundingBox(this.boundingBox.minX, structureboundingbox2.minY, structureboundingbox2.minZ, this.boundingBox.minX + 1, structureboundingbox2.maxY, structureboundingbox2.maxZ));
/*     */         } 
/*     */       } 
/*     */       
/* 537 */       for (k = 0; k < this.boundingBox.getZSize(); k += 4) {
/* 538 */         k += rand.nextInt(this.boundingBox.getZSize());
/*     */         
/* 540 */         if (k + 3 > this.boundingBox.getZSize()) {
/*     */           break;
/*     */         }
/*     */         
/* 544 */         StructureComponent structurecomponent3 = StructureMineshaftPieces.func_175890_b(componentIn, listIn, rand, this.boundingBox.maxX + 1, this.boundingBox.minY + rand.nextInt(j) + 1, this.boundingBox.minZ + k, EnumFacing.EAST, i);
/*     */         
/* 546 */         if (structurecomponent3 != null) {
/* 547 */           StructureBoundingBox structureboundingbox3 = structurecomponent3.getBoundingBox();
/* 548 */           this.roomsLinkedToTheRoom.add(new StructureBoundingBox(this.boundingBox.maxX - 1, structureboundingbox3.minY, structureboundingbox3.minZ, this.boundingBox.maxX, structureboundingbox3.maxY, structureboundingbox3.maxZ));
/*     */         } 
/*     */       } 
/*     */     }
/*     */     
/*     */     public boolean addComponentParts(World worldIn, Random randomIn, StructureBoundingBox structureBoundingBoxIn) {
/* 554 */       if (isLiquidInStructureBoundingBox(worldIn, structureBoundingBoxIn)) {
/* 555 */         return false;
/*     */       }
/* 557 */       fillWithBlocks(worldIn, structureBoundingBoxIn, this.boundingBox.minX, this.boundingBox.minY, this.boundingBox.minZ, this.boundingBox.maxX, this.boundingBox.minY, this.boundingBox.maxZ, Blocks.dirt.getDefaultState(), Blocks.air.getDefaultState(), true);
/* 558 */       fillWithBlocks(worldIn, structureBoundingBoxIn, this.boundingBox.minX, this.boundingBox.minY + 1, this.boundingBox.minZ, this.boundingBox.maxX, Math.min(this.boundingBox.minY + 3, this.boundingBox.maxY), this.boundingBox.maxZ, Blocks.air.getDefaultState(), Blocks.air.getDefaultState(), false);
/*     */       
/* 560 */       for (StructureBoundingBox structureboundingbox : this.roomsLinkedToTheRoom) {
/* 561 */         fillWithBlocks(worldIn, structureBoundingBoxIn, structureboundingbox.minX, structureboundingbox.maxY - 2, structureboundingbox.minZ, structureboundingbox.maxX, structureboundingbox.maxY, structureboundingbox.maxZ, Blocks.air.getDefaultState(), Blocks.air.getDefaultState(), false);
/*     */       }
/*     */       
/* 564 */       randomlyRareFillWithBlocks(worldIn, structureBoundingBoxIn, this.boundingBox.minX, this.boundingBox.minY + 4, this.boundingBox.minZ, this.boundingBox.maxX, this.boundingBox.maxY, this.boundingBox.maxZ, Blocks.air.getDefaultState(), false);
/* 565 */       return true;
/*     */     }
/*     */ 
/*     */     
/*     */     public void func_181138_a(int p_181138_1_, int p_181138_2_, int p_181138_3_) {
/* 570 */       super.func_181138_a(p_181138_1_, p_181138_2_, p_181138_3_);
/*     */       
/* 572 */       for (StructureBoundingBox structureboundingbox : this.roomsLinkedToTheRoom) {
/* 573 */         structureboundingbox.offset(p_181138_1_, p_181138_2_, p_181138_3_);
/*     */       }
/*     */     }
/*     */     
/*     */     protected void writeStructureToNBT(NBTTagCompound tagCompound) {
/* 578 */       NBTTagList nbttaglist = new NBTTagList();
/*     */       
/* 580 */       for (StructureBoundingBox structureboundingbox : this.roomsLinkedToTheRoom) {
/* 581 */         nbttaglist.appendTag((NBTBase)structureboundingbox.toNBTTagIntArray());
/*     */       }
/*     */       
/* 584 */       tagCompound.setTag("Entrances", (NBTBase)nbttaglist);
/*     */     }
/*     */     
/*     */     protected void readStructureFromNBT(NBTTagCompound tagCompound) {
/* 588 */       NBTTagList nbttaglist = tagCompound.getTagList("Entrances", 11);
/*     */       
/* 590 */       for (int i = 0; i < nbttaglist.tagCount(); i++)
/* 591 */         this.roomsLinkedToTheRoom.add(new StructureBoundingBox(nbttaglist.getIntArrayAt(i))); 
/*     */     }
/*     */   }
/*     */   
/*     */   public static class Stairs
/*     */     extends StructureComponent
/*     */   {
/*     */     public Stairs() {}
/*     */     
/*     */     public Stairs(int type, Random rand, StructureBoundingBox structurebb, EnumFacing facing) {
/* 601 */       super(type);
/* 602 */       this.coordBaseMode = facing;
/* 603 */       this.boundingBox = structurebb;
/*     */     }
/*     */ 
/*     */     
/*     */     protected void writeStructureToNBT(NBTTagCompound tagCompound) {}
/*     */ 
/*     */     
/*     */     protected void readStructureFromNBT(NBTTagCompound tagCompound) {}
/*     */     
/*     */     public static StructureBoundingBox func_175812_a(List<StructureComponent> listIn, Random rand, int x, int y, int z, EnumFacing facing) {
/* 613 */       StructureBoundingBox structureboundingbox = new StructureBoundingBox(x, y - 5, z, x, y + 2, z);
/*     */       
/* 615 */       switch (facing) {
/*     */         case NORTH:
/* 617 */           structureboundingbox.maxX = x + 2;
/* 618 */           structureboundingbox.minZ = z - 8;
/*     */           break;
/*     */         
/*     */         case SOUTH:
/* 622 */           structureboundingbox.maxX = x + 2;
/* 623 */           structureboundingbox.maxZ = z + 8;
/*     */           break;
/*     */         
/*     */         case WEST:
/* 627 */           structureboundingbox.minX = x - 8;
/* 628 */           structureboundingbox.maxZ = z + 2;
/*     */           break;
/*     */         
/*     */         case EAST:
/* 632 */           structureboundingbox.maxX = x + 8;
/* 633 */           structureboundingbox.maxZ = z + 2;
/*     */           break;
/*     */       } 
/* 636 */       return (StructureComponent.findIntersecting(listIn, structureboundingbox) != null) ? null : structureboundingbox;
/*     */     }
/*     */     
/*     */     public void buildComponent(StructureComponent componentIn, List<StructureComponent> listIn, Random rand) {
/* 640 */       int i = getComponentType();
/*     */       
/* 642 */       if (this.coordBaseMode != null)
/* 643 */         switch (this.coordBaseMode) {
/*     */           case NORTH:
/* 645 */             StructureMineshaftPieces.func_175890_b(componentIn, listIn, rand, this.boundingBox.minX, this.boundingBox.minY, this.boundingBox.minZ - 1, EnumFacing.NORTH, i);
/*     */             break;
/*     */           
/*     */           case SOUTH:
/* 649 */             StructureMineshaftPieces.func_175890_b(componentIn, listIn, rand, this.boundingBox.minX, this.boundingBox.minY, this.boundingBox.maxZ + 1, EnumFacing.SOUTH, i);
/*     */             break;
/*     */           
/*     */           case WEST:
/* 653 */             StructureMineshaftPieces.func_175890_b(componentIn, listIn, rand, this.boundingBox.minX - 1, this.boundingBox.minY, this.boundingBox.minZ, EnumFacing.WEST, i);
/*     */             break;
/*     */           
/*     */           case EAST:
/* 657 */             StructureMineshaftPieces.func_175890_b(componentIn, listIn, rand, this.boundingBox.maxX + 1, this.boundingBox.minY, this.boundingBox.minZ, EnumFacing.EAST, i);
/*     */             break;
/*     */         }  
/*     */     }
/*     */     
/*     */     public boolean addComponentParts(World worldIn, Random randomIn, StructureBoundingBox structureBoundingBoxIn) {
/* 663 */       if (isLiquidInStructureBoundingBox(worldIn, structureBoundingBoxIn)) {
/* 664 */         return false;
/*     */       }
/* 666 */       fillWithBlocks(worldIn, structureBoundingBoxIn, 0, 5, 0, 2, 7, 1, Blocks.air.getDefaultState(), Blocks.air.getDefaultState(), false);
/* 667 */       fillWithBlocks(worldIn, structureBoundingBoxIn, 0, 0, 7, 2, 2, 8, Blocks.air.getDefaultState(), Blocks.air.getDefaultState(), false);
/*     */       
/* 669 */       for (int i = 0; i < 5; i++) {
/* 670 */         fillWithBlocks(worldIn, structureBoundingBoxIn, 0, 5 - i - ((i < 4) ? 1 : 0), 2 + i, 2, 7 - i, 2 + i, Blocks.air.getDefaultState(), Blocks.air.getDefaultState(), false);
/*     */       }
/*     */       
/* 673 */       return true;
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\minecraft\world\gen\structure\StructureMineshaftPieces.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */