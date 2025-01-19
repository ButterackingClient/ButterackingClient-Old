/*      */ package net.minecraft.world.gen.structure;
/*      */ 
/*      */ import com.google.common.collect.Lists;
/*      */ import java.util.Iterator;
/*      */ import java.util.List;
/*      */ import java.util.Random;
/*      */ import net.minecraft.block.Block;
/*      */ import net.minecraft.block.BlockSandStone;
/*      */ import net.minecraft.block.BlockStairs;
/*      */ import net.minecraft.block.BlockTorch;
/*      */ import net.minecraft.block.material.Material;
/*      */ import net.minecraft.block.properties.IProperty;
/*      */ import net.minecraft.block.state.IBlockState;
/*      */ import net.minecraft.entity.Entity;
/*      */ import net.minecraft.entity.passive.EntityVillager;
/*      */ import net.minecraft.init.Blocks;
/*      */ import net.minecraft.init.Items;
/*      */ import net.minecraft.item.EnumDyeColor;
/*      */ import net.minecraft.item.Item;
/*      */ import net.minecraft.nbt.NBTTagCompound;
/*      */ import net.minecraft.util.BlockPos;
/*      */ import net.minecraft.util.EnumFacing;
/*      */ import net.minecraft.util.MathHelper;
/*      */ import net.minecraft.util.Vec3i;
/*      */ import net.minecraft.util.WeightedRandomChestContent;
/*      */ import net.minecraft.world.World;
/*      */ import net.minecraft.world.biome.BiomeGenBase;
/*      */ import net.minecraft.world.biome.WorldChunkManager;
/*      */ 
/*      */ public class StructureVillagePieces
/*      */ {
/*      */   public static void registerVillagePieces() {
/*   33 */     MapGenStructureIO.registerStructureComponent((Class)House1.class, "ViBH");
/*   34 */     MapGenStructureIO.registerStructureComponent((Class)Field1.class, "ViDF");
/*   35 */     MapGenStructureIO.registerStructureComponent((Class)Field2.class, "ViF");
/*   36 */     MapGenStructureIO.registerStructureComponent((Class)Torch.class, "ViL");
/*   37 */     MapGenStructureIO.registerStructureComponent((Class)Hall.class, "ViPH");
/*   38 */     MapGenStructureIO.registerStructureComponent((Class)House4Garden.class, "ViSH");
/*   39 */     MapGenStructureIO.registerStructureComponent((Class)WoodHut.class, "ViSmH");
/*   40 */     MapGenStructureIO.registerStructureComponent((Class)Church.class, "ViST");
/*   41 */     MapGenStructureIO.registerStructureComponent((Class)House2.class, "ViS");
/*   42 */     MapGenStructureIO.registerStructureComponent((Class)Start.class, "ViStart");
/*   43 */     MapGenStructureIO.registerStructureComponent((Class)Path.class, "ViSR");
/*   44 */     MapGenStructureIO.registerStructureComponent((Class)House3.class, "ViTRH");
/*   45 */     MapGenStructureIO.registerStructureComponent((Class)Well.class, "ViW");
/*      */   }
/*      */   
/*      */   public static List<PieceWeight> getStructureVillageWeightedPieceList(Random random, int size) {
/*   49 */     List<PieceWeight> list = Lists.newArrayList();
/*   50 */     list.add(new PieceWeight((Class)House4Garden.class, 4, MathHelper.getRandomIntegerInRange(random, 2 + size, 4 + size * 2)));
/*   51 */     list.add(new PieceWeight((Class)Church.class, 20, MathHelper.getRandomIntegerInRange(random, 0 + size, 1 + size)));
/*   52 */     list.add(new PieceWeight((Class)House1.class, 20, MathHelper.getRandomIntegerInRange(random, 0 + size, 2 + size)));
/*   53 */     list.add(new PieceWeight((Class)WoodHut.class, 3, MathHelper.getRandomIntegerInRange(random, 2 + size, 5 + size * 3)));
/*   54 */     list.add(new PieceWeight((Class)Hall.class, 15, MathHelper.getRandomIntegerInRange(random, 0 + size, 2 + size)));
/*   55 */     list.add(new PieceWeight((Class)Field1.class, 3, MathHelper.getRandomIntegerInRange(random, 1 + size, 4 + size)));
/*   56 */     list.add(new PieceWeight((Class)Field2.class, 3, MathHelper.getRandomIntegerInRange(random, 2 + size, 4 + size * 2)));
/*   57 */     list.add(new PieceWeight((Class)House2.class, 15, MathHelper.getRandomIntegerInRange(random, 0, 1 + size)));
/*   58 */     list.add(new PieceWeight((Class)House3.class, 8, MathHelper.getRandomIntegerInRange(random, 0 + size, 3 + size * 2)));
/*   59 */     Iterator<PieceWeight> iterator = list.iterator();
/*      */     
/*   61 */     while (iterator.hasNext()) {
/*   62 */       if (((PieceWeight)iterator.next()).villagePiecesLimit == 0) {
/*   63 */         iterator.remove();
/*      */       }
/*      */     } 
/*      */     
/*   67 */     return list;
/*      */   }
/*      */   
/*      */   private static int func_75079_a(List<PieceWeight> p_75079_0_) {
/*   71 */     boolean flag = false;
/*   72 */     int i = 0;
/*      */     
/*   74 */     for (PieceWeight structurevillagepieces$pieceweight : p_75079_0_) {
/*   75 */       if (structurevillagepieces$pieceweight.villagePiecesLimit > 0 && structurevillagepieces$pieceweight.villagePiecesSpawned < structurevillagepieces$pieceweight.villagePiecesLimit) {
/*   76 */         flag = true;
/*      */       }
/*      */       
/*   79 */       i += structurevillagepieces$pieceweight.villagePieceWeight;
/*      */     } 
/*      */     
/*   82 */     return flag ? i : -1;
/*      */   }
/*      */   
/*      */   private static Village func_176065_a(Start start, PieceWeight weight, List<StructureComponent> p_176065_2_, Random rand, int p_176065_4_, int p_176065_5_, int p_176065_6_, EnumFacing facing, int p_176065_8_) {
/*   86 */     Class<? extends Village> oclass = weight.villagePieceClass;
/*   87 */     Village structurevillagepieces$village = null;
/*      */     
/*   89 */     if (oclass == House4Garden.class) {
/*   90 */       structurevillagepieces$village = House4Garden.func_175858_a(start, p_176065_2_, rand, p_176065_4_, p_176065_5_, p_176065_6_, facing, p_176065_8_);
/*   91 */     } else if (oclass == Church.class) {
/*   92 */       structurevillagepieces$village = Church.func_175854_a(start, p_176065_2_, rand, p_176065_4_, p_176065_5_, p_176065_6_, facing, p_176065_8_);
/*   93 */     } else if (oclass == House1.class) {
/*   94 */       structurevillagepieces$village = House1.func_175850_a(start, p_176065_2_, rand, p_176065_4_, p_176065_5_, p_176065_6_, facing, p_176065_8_);
/*   95 */     } else if (oclass == WoodHut.class) {
/*   96 */       structurevillagepieces$village = WoodHut.func_175853_a(start, p_176065_2_, rand, p_176065_4_, p_176065_5_, p_176065_6_, facing, p_176065_8_);
/*   97 */     } else if (oclass == Hall.class) {
/*   98 */       structurevillagepieces$village = Hall.func_175857_a(start, p_176065_2_, rand, p_176065_4_, p_176065_5_, p_176065_6_, facing, p_176065_8_);
/*   99 */     } else if (oclass == Field1.class) {
/*  100 */       structurevillagepieces$village = Field1.func_175851_a(start, p_176065_2_, rand, p_176065_4_, p_176065_5_, p_176065_6_, facing, p_176065_8_);
/*  101 */     } else if (oclass == Field2.class) {
/*  102 */       structurevillagepieces$village = Field2.func_175852_a(start, p_176065_2_, rand, p_176065_4_, p_176065_5_, p_176065_6_, facing, p_176065_8_);
/*  103 */     } else if (oclass == House2.class) {
/*  104 */       structurevillagepieces$village = House2.func_175855_a(start, p_176065_2_, rand, p_176065_4_, p_176065_5_, p_176065_6_, facing, p_176065_8_);
/*  105 */     } else if (oclass == House3.class) {
/*  106 */       structurevillagepieces$village = House3.func_175849_a(start, p_176065_2_, rand, p_176065_4_, p_176065_5_, p_176065_6_, facing, p_176065_8_);
/*      */     } 
/*      */     
/*  109 */     return structurevillagepieces$village;
/*      */   }
/*      */   
/*      */   private static Village func_176067_c(Start start, List<StructureComponent> p_176067_1_, Random rand, int p_176067_3_, int p_176067_4_, int p_176067_5_, EnumFacing facing, int p_176067_7_) {
/*  113 */     int i = func_75079_a(start.structureVillageWeightedPieceList);
/*      */     
/*  115 */     if (i <= 0) {
/*  116 */       return null;
/*      */     }
/*  118 */     int j = 0;
/*      */     
/*  120 */     while (j < 5) {
/*  121 */       j++;
/*  122 */       int k = rand.nextInt(i);
/*      */       
/*  124 */       for (PieceWeight structurevillagepieces$pieceweight : start.structureVillageWeightedPieceList) {
/*  125 */         k -= structurevillagepieces$pieceweight.villagePieceWeight;
/*      */         
/*  127 */         if (k < 0) {
/*  128 */           if (!structurevillagepieces$pieceweight.canSpawnMoreVillagePiecesOfType(p_176067_7_) || (structurevillagepieces$pieceweight == start.structVillagePieceWeight && start.structureVillageWeightedPieceList.size() > 1)) {
/*      */             break;
/*      */           }
/*      */           
/*  132 */           Village structurevillagepieces$village = func_176065_a(start, structurevillagepieces$pieceweight, p_176067_1_, rand, p_176067_3_, p_176067_4_, p_176067_5_, facing, p_176067_7_);
/*      */           
/*  134 */           if (structurevillagepieces$village != null) {
/*  135 */             structurevillagepieces$pieceweight.villagePiecesSpawned++;
/*  136 */             start.structVillagePieceWeight = structurevillagepieces$pieceweight;
/*      */             
/*  138 */             if (!structurevillagepieces$pieceweight.canSpawnMoreVillagePieces()) {
/*  139 */               start.structureVillageWeightedPieceList.remove(structurevillagepieces$pieceweight);
/*      */             }
/*      */             
/*  142 */             return structurevillagepieces$village;
/*      */           } 
/*      */         } 
/*      */       } 
/*      */     } 
/*      */     
/*  148 */     StructureBoundingBox structureboundingbox = Torch.func_175856_a(start, p_176067_1_, rand, p_176067_3_, p_176067_4_, p_176067_5_, facing);
/*      */     
/*  150 */     if (structureboundingbox != null) {
/*  151 */       return new Torch(start, p_176067_7_, rand, structureboundingbox, facing);
/*      */     }
/*  153 */     return null;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private static StructureComponent func_176066_d(Start start, List<StructureComponent> p_176066_1_, Random rand, int p_176066_3_, int p_176066_4_, int p_176066_5_, EnumFacing facing, int p_176066_7_) {
/*  159 */     if (p_176066_7_ > 50)
/*  160 */       return null; 
/*  161 */     if (Math.abs(p_176066_3_ - (start.getBoundingBox()).minX) <= 112 && Math.abs(p_176066_5_ - (start.getBoundingBox()).minZ) <= 112) {
/*  162 */       StructureComponent structurecomponent = func_176067_c(start, p_176066_1_, rand, p_176066_3_, p_176066_4_, p_176066_5_, facing, p_176066_7_ + 1);
/*      */       
/*  164 */       if (structurecomponent != null) {
/*  165 */         int i = (structurecomponent.boundingBox.minX + structurecomponent.boundingBox.maxX) / 2;
/*  166 */         int j = (structurecomponent.boundingBox.minZ + structurecomponent.boundingBox.maxZ) / 2;
/*  167 */         int k = structurecomponent.boundingBox.maxX - structurecomponent.boundingBox.minX;
/*  168 */         int l = structurecomponent.boundingBox.maxZ - structurecomponent.boundingBox.minZ;
/*  169 */         int i1 = (k > l) ? k : l;
/*      */         
/*  171 */         if (start.getWorldChunkManager().areBiomesViable(i, j, i1 / 2 + 4, MapGenVillage.villageSpawnBiomes)) {
/*  172 */           p_176066_1_.add(structurecomponent);
/*  173 */           start.field_74932_i.add(structurecomponent);
/*  174 */           return structurecomponent;
/*      */         } 
/*      */       } 
/*      */       
/*  178 */       return null;
/*      */     } 
/*  180 */     return null;
/*      */   }
/*      */ 
/*      */   
/*      */   private static StructureComponent func_176069_e(Start start, List<StructureComponent> p_176069_1_, Random rand, int p_176069_3_, int p_176069_4_, int p_176069_5_, EnumFacing facing, int p_176069_7_) {
/*  185 */     if (p_176069_7_ > 3 + start.terrainType)
/*  186 */       return null; 
/*  187 */     if (Math.abs(p_176069_3_ - (start.getBoundingBox()).minX) <= 112 && Math.abs(p_176069_5_ - (start.getBoundingBox()).minZ) <= 112) {
/*  188 */       StructureBoundingBox structureboundingbox = Path.func_175848_a(start, p_176069_1_, rand, p_176069_3_, p_176069_4_, p_176069_5_, facing);
/*      */       
/*  190 */       if (structureboundingbox != null && structureboundingbox.minY > 10) {
/*  191 */         StructureComponent structurecomponent = new Path(start, p_176069_7_, rand, structureboundingbox, facing);
/*  192 */         int i = (structurecomponent.boundingBox.minX + structurecomponent.boundingBox.maxX) / 2;
/*  193 */         int j = (structurecomponent.boundingBox.minZ + structurecomponent.boundingBox.maxZ) / 2;
/*  194 */         int k = structurecomponent.boundingBox.maxX - structurecomponent.boundingBox.minX;
/*  195 */         int l = structurecomponent.boundingBox.maxZ - structurecomponent.boundingBox.minZ;
/*  196 */         int i1 = (k > l) ? k : l;
/*      */         
/*  198 */         if (start.getWorldChunkManager().areBiomesViable(i, j, i1 / 2 + 4, MapGenVillage.villageSpawnBiomes)) {
/*  199 */           p_176069_1_.add(structurecomponent);
/*  200 */           start.field_74930_j.add(structurecomponent);
/*  201 */           return structurecomponent;
/*      */         } 
/*      */       } 
/*      */       
/*  205 */       return null;
/*      */     } 
/*  207 */     return null;
/*      */   }
/*      */   
/*      */   public static class Church
/*      */     extends Village
/*      */   {
/*      */     public Church() {}
/*      */     
/*      */     public Church(StructureVillagePieces.Start start, int p_i45564_2_, Random rand, StructureBoundingBox p_i45564_4_, EnumFacing facing) {
/*  216 */       super(start, p_i45564_2_);
/*  217 */       this.coordBaseMode = facing;
/*  218 */       this.boundingBox = p_i45564_4_;
/*      */     }
/*      */     
/*      */     public static Church func_175854_a(StructureVillagePieces.Start start, List<StructureComponent> p_175854_1_, Random rand, int p_175854_3_, int p_175854_4_, int p_175854_5_, EnumFacing facing, int p_175854_7_) {
/*  222 */       StructureBoundingBox structureboundingbox = StructureBoundingBox.getComponentToAddBoundingBox(p_175854_3_, p_175854_4_, p_175854_5_, 0, 0, 0, 5, 12, 9, facing);
/*  223 */       return (canVillageGoDeeper(structureboundingbox) && StructureComponent.findIntersecting(p_175854_1_, structureboundingbox) == null) ? new Church(start, p_175854_7_, rand, structureboundingbox, facing) : null;
/*      */     }
/*      */     
/*      */     public boolean addComponentParts(World worldIn, Random randomIn, StructureBoundingBox structureBoundingBoxIn) {
/*  227 */       if (this.field_143015_k < 0) {
/*  228 */         this.field_143015_k = getAverageGroundLevel(worldIn, structureBoundingBoxIn);
/*      */         
/*  230 */         if (this.field_143015_k < 0) {
/*  231 */           return true;
/*      */         }
/*      */         
/*  234 */         this.boundingBox.offset(0, this.field_143015_k - this.boundingBox.maxY + 12 - 1, 0);
/*      */       } 
/*      */       
/*  237 */       fillWithBlocks(worldIn, structureBoundingBoxIn, 1, 1, 1, 3, 3, 7, Blocks.air.getDefaultState(), Blocks.air.getDefaultState(), false);
/*  238 */       fillWithBlocks(worldIn, structureBoundingBoxIn, 1, 5, 1, 3, 9, 3, Blocks.air.getDefaultState(), Blocks.air.getDefaultState(), false);
/*  239 */       fillWithBlocks(worldIn, structureBoundingBoxIn, 1, 0, 0, 3, 0, 8, Blocks.cobblestone.getDefaultState(), Blocks.cobblestone.getDefaultState(), false);
/*  240 */       fillWithBlocks(worldIn, structureBoundingBoxIn, 1, 1, 0, 3, 10, 0, Blocks.cobblestone.getDefaultState(), Blocks.cobblestone.getDefaultState(), false);
/*  241 */       fillWithBlocks(worldIn, structureBoundingBoxIn, 0, 1, 1, 0, 10, 3, Blocks.cobblestone.getDefaultState(), Blocks.cobblestone.getDefaultState(), false);
/*  242 */       fillWithBlocks(worldIn, structureBoundingBoxIn, 4, 1, 1, 4, 10, 3, Blocks.cobblestone.getDefaultState(), Blocks.cobblestone.getDefaultState(), false);
/*  243 */       fillWithBlocks(worldIn, structureBoundingBoxIn, 0, 0, 4, 0, 4, 7, Blocks.cobblestone.getDefaultState(), Blocks.cobblestone.getDefaultState(), false);
/*  244 */       fillWithBlocks(worldIn, structureBoundingBoxIn, 4, 0, 4, 4, 4, 7, Blocks.cobblestone.getDefaultState(), Blocks.cobblestone.getDefaultState(), false);
/*  245 */       fillWithBlocks(worldIn, structureBoundingBoxIn, 1, 1, 8, 3, 4, 8, Blocks.cobblestone.getDefaultState(), Blocks.cobblestone.getDefaultState(), false);
/*  246 */       fillWithBlocks(worldIn, structureBoundingBoxIn, 1, 5, 4, 3, 10, 4, Blocks.cobblestone.getDefaultState(), Blocks.cobblestone.getDefaultState(), false);
/*  247 */       fillWithBlocks(worldIn, structureBoundingBoxIn, 1, 5, 5, 3, 5, 7, Blocks.cobblestone.getDefaultState(), Blocks.cobblestone.getDefaultState(), false);
/*  248 */       fillWithBlocks(worldIn, structureBoundingBoxIn, 0, 9, 0, 4, 9, 4, Blocks.cobblestone.getDefaultState(), Blocks.cobblestone.getDefaultState(), false);
/*  249 */       fillWithBlocks(worldIn, structureBoundingBoxIn, 0, 4, 0, 4, 4, 4, Blocks.cobblestone.getDefaultState(), Blocks.cobblestone.getDefaultState(), false);
/*  250 */       setBlockState(worldIn, Blocks.cobblestone.getDefaultState(), 0, 11, 2, structureBoundingBoxIn);
/*  251 */       setBlockState(worldIn, Blocks.cobblestone.getDefaultState(), 4, 11, 2, structureBoundingBoxIn);
/*  252 */       setBlockState(worldIn, Blocks.cobblestone.getDefaultState(), 2, 11, 0, structureBoundingBoxIn);
/*  253 */       setBlockState(worldIn, Blocks.cobblestone.getDefaultState(), 2, 11, 4, structureBoundingBoxIn);
/*  254 */       setBlockState(worldIn, Blocks.cobblestone.getDefaultState(), 1, 1, 6, structureBoundingBoxIn);
/*  255 */       setBlockState(worldIn, Blocks.cobblestone.getDefaultState(), 1, 1, 7, structureBoundingBoxIn);
/*  256 */       setBlockState(worldIn, Blocks.cobblestone.getDefaultState(), 2, 1, 7, structureBoundingBoxIn);
/*  257 */       setBlockState(worldIn, Blocks.cobblestone.getDefaultState(), 3, 1, 6, structureBoundingBoxIn);
/*  258 */       setBlockState(worldIn, Blocks.cobblestone.getDefaultState(), 3, 1, 7, structureBoundingBoxIn);
/*  259 */       setBlockState(worldIn, Blocks.stone_stairs.getStateFromMeta(getMetadataWithOffset(Blocks.stone_stairs, 3)), 1, 1, 5, structureBoundingBoxIn);
/*  260 */       setBlockState(worldIn, Blocks.stone_stairs.getStateFromMeta(getMetadataWithOffset(Blocks.stone_stairs, 3)), 2, 1, 6, structureBoundingBoxIn);
/*  261 */       setBlockState(worldIn, Blocks.stone_stairs.getStateFromMeta(getMetadataWithOffset(Blocks.stone_stairs, 3)), 3, 1, 5, structureBoundingBoxIn);
/*  262 */       setBlockState(worldIn, Blocks.stone_stairs.getStateFromMeta(getMetadataWithOffset(Blocks.stone_stairs, 1)), 1, 2, 7, structureBoundingBoxIn);
/*  263 */       setBlockState(worldIn, Blocks.stone_stairs.getStateFromMeta(getMetadataWithOffset(Blocks.stone_stairs, 0)), 3, 2, 7, structureBoundingBoxIn);
/*  264 */       setBlockState(worldIn, Blocks.glass_pane.getDefaultState(), 0, 2, 2, structureBoundingBoxIn);
/*  265 */       setBlockState(worldIn, Blocks.glass_pane.getDefaultState(), 0, 3, 2, structureBoundingBoxIn);
/*  266 */       setBlockState(worldIn, Blocks.glass_pane.getDefaultState(), 4, 2, 2, structureBoundingBoxIn);
/*  267 */       setBlockState(worldIn, Blocks.glass_pane.getDefaultState(), 4, 3, 2, structureBoundingBoxIn);
/*  268 */       setBlockState(worldIn, Blocks.glass_pane.getDefaultState(), 0, 6, 2, structureBoundingBoxIn);
/*  269 */       setBlockState(worldIn, Blocks.glass_pane.getDefaultState(), 0, 7, 2, structureBoundingBoxIn);
/*  270 */       setBlockState(worldIn, Blocks.glass_pane.getDefaultState(), 4, 6, 2, structureBoundingBoxIn);
/*  271 */       setBlockState(worldIn, Blocks.glass_pane.getDefaultState(), 4, 7, 2, structureBoundingBoxIn);
/*  272 */       setBlockState(worldIn, Blocks.glass_pane.getDefaultState(), 2, 6, 0, structureBoundingBoxIn);
/*  273 */       setBlockState(worldIn, Blocks.glass_pane.getDefaultState(), 2, 7, 0, structureBoundingBoxIn);
/*  274 */       setBlockState(worldIn, Blocks.glass_pane.getDefaultState(), 2, 6, 4, structureBoundingBoxIn);
/*  275 */       setBlockState(worldIn, Blocks.glass_pane.getDefaultState(), 2, 7, 4, structureBoundingBoxIn);
/*  276 */       setBlockState(worldIn, Blocks.glass_pane.getDefaultState(), 0, 3, 6, structureBoundingBoxIn);
/*  277 */       setBlockState(worldIn, Blocks.glass_pane.getDefaultState(), 4, 3, 6, structureBoundingBoxIn);
/*  278 */       setBlockState(worldIn, Blocks.glass_pane.getDefaultState(), 2, 3, 8, structureBoundingBoxIn);
/*  279 */       setBlockState(worldIn, Blocks.torch.getDefaultState().withProperty((IProperty)BlockTorch.FACING, (Comparable)this.coordBaseMode.getOpposite()), 2, 4, 7, structureBoundingBoxIn);
/*  280 */       setBlockState(worldIn, Blocks.torch.getDefaultState().withProperty((IProperty)BlockTorch.FACING, (Comparable)this.coordBaseMode.rotateY()), 1, 4, 6, structureBoundingBoxIn);
/*  281 */       setBlockState(worldIn, Blocks.torch.getDefaultState().withProperty((IProperty)BlockTorch.FACING, (Comparable)this.coordBaseMode.rotateYCCW()), 3, 4, 6, structureBoundingBoxIn);
/*  282 */       setBlockState(worldIn, Blocks.torch.getDefaultState().withProperty((IProperty)BlockTorch.FACING, (Comparable)this.coordBaseMode), 2, 4, 5, structureBoundingBoxIn);
/*  283 */       int i = getMetadataWithOffset(Blocks.ladder, 4);
/*      */       
/*  285 */       for (int j = 1; j <= 9; j++) {
/*  286 */         setBlockState(worldIn, Blocks.ladder.getStateFromMeta(i), 3, j, 3, structureBoundingBoxIn);
/*      */       }
/*      */       
/*  289 */       setBlockState(worldIn, Blocks.air.getDefaultState(), 2, 1, 0, structureBoundingBoxIn);
/*  290 */       setBlockState(worldIn, Blocks.air.getDefaultState(), 2, 2, 0, structureBoundingBoxIn);
/*  291 */       placeDoorCurrentPosition(worldIn, structureBoundingBoxIn, randomIn, 2, 1, 0, EnumFacing.getHorizontal(getMetadataWithOffset(Blocks.oak_door, 1)));
/*      */       
/*  293 */       if (getBlockStateFromPos(worldIn, 2, 0, -1, structureBoundingBoxIn).getBlock().getMaterial() == Material.air && getBlockStateFromPos(worldIn, 2, -1, -1, structureBoundingBoxIn).getBlock().getMaterial() != Material.air) {
/*  294 */         setBlockState(worldIn, Blocks.stone_stairs.getStateFromMeta(getMetadataWithOffset(Blocks.stone_stairs, 3)), 2, 0, -1, structureBoundingBoxIn);
/*      */       }
/*      */       
/*  297 */       for (int l = 0; l < 9; l++) {
/*  298 */         for (int k = 0; k < 5; k++) {
/*  299 */           clearCurrentPositionBlocksUpwards(worldIn, k, 12, l, structureBoundingBoxIn);
/*  300 */           replaceAirAndLiquidDownwards(worldIn, Blocks.cobblestone.getDefaultState(), k, -1, l, structureBoundingBoxIn);
/*      */         } 
/*      */       } 
/*      */       
/*  304 */       spawnVillagers(worldIn, structureBoundingBoxIn, 2, 1, 2, 1);
/*  305 */       return true;
/*      */     }
/*      */     
/*      */     protected int func_180779_c(int p_180779_1_, int p_180779_2_) {
/*  309 */       return 2;
/*      */     }
/*      */   }
/*      */   
/*      */   public static class Field1
/*      */     extends Village {
/*      */     private Block cropTypeA;
/*      */     private Block cropTypeB;
/*      */     private Block cropTypeC;
/*      */     private Block cropTypeD;
/*      */     
/*      */     public Field1() {}
/*      */     
/*      */     public Field1(StructureVillagePieces.Start start, int p_i45570_2_, Random rand, StructureBoundingBox p_i45570_4_, EnumFacing facing) {
/*  323 */       super(start, p_i45570_2_);
/*  324 */       this.coordBaseMode = facing;
/*  325 */       this.boundingBox = p_i45570_4_;
/*  326 */       this.cropTypeA = func_151559_a(rand);
/*  327 */       this.cropTypeB = func_151559_a(rand);
/*  328 */       this.cropTypeC = func_151559_a(rand);
/*  329 */       this.cropTypeD = func_151559_a(rand);
/*      */     }
/*      */     
/*      */     protected void writeStructureToNBT(NBTTagCompound tagCompound) {
/*  333 */       super.writeStructureToNBT(tagCompound);
/*  334 */       tagCompound.setInteger("CA", Block.blockRegistry.getIDForObject(this.cropTypeA));
/*  335 */       tagCompound.setInteger("CB", Block.blockRegistry.getIDForObject(this.cropTypeB));
/*  336 */       tagCompound.setInteger("CC", Block.blockRegistry.getIDForObject(this.cropTypeC));
/*  337 */       tagCompound.setInteger("CD", Block.blockRegistry.getIDForObject(this.cropTypeD));
/*      */     }
/*      */     
/*      */     protected void readStructureFromNBT(NBTTagCompound tagCompound) {
/*  341 */       super.readStructureFromNBT(tagCompound);
/*  342 */       this.cropTypeA = Block.getBlockById(tagCompound.getInteger("CA"));
/*  343 */       this.cropTypeB = Block.getBlockById(tagCompound.getInteger("CB"));
/*  344 */       this.cropTypeC = Block.getBlockById(tagCompound.getInteger("CC"));
/*  345 */       this.cropTypeD = Block.getBlockById(tagCompound.getInteger("CD"));
/*      */     }
/*      */     
/*      */     private Block func_151559_a(Random rand) {
/*  349 */       switch (rand.nextInt(5)) {
/*      */         case 0:
/*  351 */           return Blocks.carrots;
/*      */         
/*      */         case 1:
/*  354 */           return Blocks.potatoes;
/*      */       } 
/*      */       
/*  357 */       return Blocks.wheat;
/*      */     }
/*      */ 
/*      */     
/*      */     public static Field1 func_175851_a(StructureVillagePieces.Start start, List<StructureComponent> p_175851_1_, Random rand, int p_175851_3_, int p_175851_4_, int p_175851_5_, EnumFacing facing, int p_175851_7_) {
/*  362 */       StructureBoundingBox structureboundingbox = StructureBoundingBox.getComponentToAddBoundingBox(p_175851_3_, p_175851_4_, p_175851_5_, 0, 0, 0, 13, 4, 9, facing);
/*  363 */       return (canVillageGoDeeper(structureboundingbox) && StructureComponent.findIntersecting(p_175851_1_, structureboundingbox) == null) ? new Field1(start, p_175851_7_, rand, structureboundingbox, facing) : null;
/*      */     }
/*      */     
/*      */     public boolean addComponentParts(World worldIn, Random randomIn, StructureBoundingBox structureBoundingBoxIn) {
/*  367 */       if (this.field_143015_k < 0) {
/*  368 */         this.field_143015_k = getAverageGroundLevel(worldIn, structureBoundingBoxIn);
/*      */         
/*  370 */         if (this.field_143015_k < 0) {
/*  371 */           return true;
/*      */         }
/*      */         
/*  374 */         this.boundingBox.offset(0, this.field_143015_k - this.boundingBox.maxY + 4 - 1, 0);
/*      */       } 
/*      */       
/*  377 */       fillWithBlocks(worldIn, structureBoundingBoxIn, 0, 1, 0, 12, 4, 8, Blocks.air.getDefaultState(), Blocks.air.getDefaultState(), false);
/*  378 */       fillWithBlocks(worldIn, structureBoundingBoxIn, 1, 0, 1, 2, 0, 7, Blocks.farmland.getDefaultState(), Blocks.farmland.getDefaultState(), false);
/*  379 */       fillWithBlocks(worldIn, structureBoundingBoxIn, 4, 0, 1, 5, 0, 7, Blocks.farmland.getDefaultState(), Blocks.farmland.getDefaultState(), false);
/*  380 */       fillWithBlocks(worldIn, structureBoundingBoxIn, 7, 0, 1, 8, 0, 7, Blocks.farmland.getDefaultState(), Blocks.farmland.getDefaultState(), false);
/*  381 */       fillWithBlocks(worldIn, structureBoundingBoxIn, 10, 0, 1, 11, 0, 7, Blocks.farmland.getDefaultState(), Blocks.farmland.getDefaultState(), false);
/*  382 */       fillWithBlocks(worldIn, structureBoundingBoxIn, 0, 0, 0, 0, 0, 8, Blocks.log.getDefaultState(), Blocks.log.getDefaultState(), false);
/*  383 */       fillWithBlocks(worldIn, structureBoundingBoxIn, 6, 0, 0, 6, 0, 8, Blocks.log.getDefaultState(), Blocks.log.getDefaultState(), false);
/*  384 */       fillWithBlocks(worldIn, structureBoundingBoxIn, 12, 0, 0, 12, 0, 8, Blocks.log.getDefaultState(), Blocks.log.getDefaultState(), false);
/*  385 */       fillWithBlocks(worldIn, structureBoundingBoxIn, 1, 0, 0, 11, 0, 0, Blocks.log.getDefaultState(), Blocks.log.getDefaultState(), false);
/*  386 */       fillWithBlocks(worldIn, structureBoundingBoxIn, 1, 0, 8, 11, 0, 8, Blocks.log.getDefaultState(), Blocks.log.getDefaultState(), false);
/*  387 */       fillWithBlocks(worldIn, structureBoundingBoxIn, 3, 0, 1, 3, 0, 7, Blocks.water.getDefaultState(), Blocks.water.getDefaultState(), false);
/*  388 */       fillWithBlocks(worldIn, structureBoundingBoxIn, 9, 0, 1, 9, 0, 7, Blocks.water.getDefaultState(), Blocks.water.getDefaultState(), false);
/*      */       
/*  390 */       for (int i = 1; i <= 7; i++) {
/*  391 */         setBlockState(worldIn, this.cropTypeA.getStateFromMeta(MathHelper.getRandomIntegerInRange(randomIn, 2, 7)), 1, 1, i, structureBoundingBoxIn);
/*  392 */         setBlockState(worldIn, this.cropTypeA.getStateFromMeta(MathHelper.getRandomIntegerInRange(randomIn, 2, 7)), 2, 1, i, structureBoundingBoxIn);
/*  393 */         setBlockState(worldIn, this.cropTypeB.getStateFromMeta(MathHelper.getRandomIntegerInRange(randomIn, 2, 7)), 4, 1, i, structureBoundingBoxIn);
/*  394 */         setBlockState(worldIn, this.cropTypeB.getStateFromMeta(MathHelper.getRandomIntegerInRange(randomIn, 2, 7)), 5, 1, i, structureBoundingBoxIn);
/*  395 */         setBlockState(worldIn, this.cropTypeC.getStateFromMeta(MathHelper.getRandomIntegerInRange(randomIn, 2, 7)), 7, 1, i, structureBoundingBoxIn);
/*  396 */         setBlockState(worldIn, this.cropTypeC.getStateFromMeta(MathHelper.getRandomIntegerInRange(randomIn, 2, 7)), 8, 1, i, structureBoundingBoxIn);
/*  397 */         setBlockState(worldIn, this.cropTypeD.getStateFromMeta(MathHelper.getRandomIntegerInRange(randomIn, 2, 7)), 10, 1, i, structureBoundingBoxIn);
/*  398 */         setBlockState(worldIn, this.cropTypeD.getStateFromMeta(MathHelper.getRandomIntegerInRange(randomIn, 2, 7)), 11, 1, i, structureBoundingBoxIn);
/*      */       } 
/*      */       
/*  401 */       for (int k = 0; k < 9; k++) {
/*  402 */         for (int j = 0; j < 13; j++) {
/*  403 */           clearCurrentPositionBlocksUpwards(worldIn, j, 4, k, structureBoundingBoxIn);
/*  404 */           replaceAirAndLiquidDownwards(worldIn, Blocks.dirt.getDefaultState(), j, -1, k, structureBoundingBoxIn);
/*      */         } 
/*      */       } 
/*      */       
/*  408 */       return true;
/*      */     }
/*      */   }
/*      */   
/*      */   public static class Field2
/*      */     extends Village {
/*      */     private Block cropTypeA;
/*      */     private Block cropTypeB;
/*      */     
/*      */     public Field2() {}
/*      */     
/*      */     public Field2(StructureVillagePieces.Start start, int p_i45569_2_, Random rand, StructureBoundingBox p_i45569_4_, EnumFacing facing) {
/*  420 */       super(start, p_i45569_2_);
/*  421 */       this.coordBaseMode = facing;
/*  422 */       this.boundingBox = p_i45569_4_;
/*  423 */       this.cropTypeA = func_151560_a(rand);
/*  424 */       this.cropTypeB = func_151560_a(rand);
/*      */     }
/*      */     
/*      */     protected void writeStructureToNBT(NBTTagCompound tagCompound) {
/*  428 */       super.writeStructureToNBT(tagCompound);
/*  429 */       tagCompound.setInteger("CA", Block.blockRegistry.getIDForObject(this.cropTypeA));
/*  430 */       tagCompound.setInteger("CB", Block.blockRegistry.getIDForObject(this.cropTypeB));
/*      */     }
/*      */     
/*      */     protected void readStructureFromNBT(NBTTagCompound tagCompound) {
/*  434 */       super.readStructureFromNBT(tagCompound);
/*  435 */       this.cropTypeA = Block.getBlockById(tagCompound.getInteger("CA"));
/*  436 */       this.cropTypeB = Block.getBlockById(tagCompound.getInteger("CB"));
/*      */     }
/*      */     
/*      */     private Block func_151560_a(Random rand) {
/*  440 */       switch (rand.nextInt(5)) {
/*      */         case 0:
/*  442 */           return Blocks.carrots;
/*      */         
/*      */         case 1:
/*  445 */           return Blocks.potatoes;
/*      */       } 
/*      */       
/*  448 */       return Blocks.wheat;
/*      */     }
/*      */ 
/*      */     
/*      */     public static Field2 func_175852_a(StructureVillagePieces.Start start, List<StructureComponent> p_175852_1_, Random rand, int p_175852_3_, int p_175852_4_, int p_175852_5_, EnumFacing facing, int p_175852_7_) {
/*  453 */       StructureBoundingBox structureboundingbox = StructureBoundingBox.getComponentToAddBoundingBox(p_175852_3_, p_175852_4_, p_175852_5_, 0, 0, 0, 7, 4, 9, facing);
/*  454 */       return (canVillageGoDeeper(structureboundingbox) && StructureComponent.findIntersecting(p_175852_1_, structureboundingbox) == null) ? new Field2(start, p_175852_7_, rand, structureboundingbox, facing) : null;
/*      */     }
/*      */     
/*      */     public boolean addComponentParts(World worldIn, Random randomIn, StructureBoundingBox structureBoundingBoxIn) {
/*  458 */       if (this.field_143015_k < 0) {
/*  459 */         this.field_143015_k = getAverageGroundLevel(worldIn, structureBoundingBoxIn);
/*      */         
/*  461 */         if (this.field_143015_k < 0) {
/*  462 */           return true;
/*      */         }
/*      */         
/*  465 */         this.boundingBox.offset(0, this.field_143015_k - this.boundingBox.maxY + 4 - 1, 0);
/*      */       } 
/*      */       
/*  468 */       fillWithBlocks(worldIn, structureBoundingBoxIn, 0, 1, 0, 6, 4, 8, Blocks.air.getDefaultState(), Blocks.air.getDefaultState(), false);
/*  469 */       fillWithBlocks(worldIn, structureBoundingBoxIn, 1, 0, 1, 2, 0, 7, Blocks.farmland.getDefaultState(), Blocks.farmland.getDefaultState(), false);
/*  470 */       fillWithBlocks(worldIn, structureBoundingBoxIn, 4, 0, 1, 5, 0, 7, Blocks.farmland.getDefaultState(), Blocks.farmland.getDefaultState(), false);
/*  471 */       fillWithBlocks(worldIn, structureBoundingBoxIn, 0, 0, 0, 0, 0, 8, Blocks.log.getDefaultState(), Blocks.log.getDefaultState(), false);
/*  472 */       fillWithBlocks(worldIn, structureBoundingBoxIn, 6, 0, 0, 6, 0, 8, Blocks.log.getDefaultState(), Blocks.log.getDefaultState(), false);
/*  473 */       fillWithBlocks(worldIn, structureBoundingBoxIn, 1, 0, 0, 5, 0, 0, Blocks.log.getDefaultState(), Blocks.log.getDefaultState(), false);
/*  474 */       fillWithBlocks(worldIn, structureBoundingBoxIn, 1, 0, 8, 5, 0, 8, Blocks.log.getDefaultState(), Blocks.log.getDefaultState(), false);
/*  475 */       fillWithBlocks(worldIn, structureBoundingBoxIn, 3, 0, 1, 3, 0, 7, Blocks.water.getDefaultState(), Blocks.water.getDefaultState(), false);
/*      */       
/*  477 */       for (int i = 1; i <= 7; i++) {
/*  478 */         setBlockState(worldIn, this.cropTypeA.getStateFromMeta(MathHelper.getRandomIntegerInRange(randomIn, 2, 7)), 1, 1, i, structureBoundingBoxIn);
/*  479 */         setBlockState(worldIn, this.cropTypeA.getStateFromMeta(MathHelper.getRandomIntegerInRange(randomIn, 2, 7)), 2, 1, i, structureBoundingBoxIn);
/*  480 */         setBlockState(worldIn, this.cropTypeB.getStateFromMeta(MathHelper.getRandomIntegerInRange(randomIn, 2, 7)), 4, 1, i, structureBoundingBoxIn);
/*  481 */         setBlockState(worldIn, this.cropTypeB.getStateFromMeta(MathHelper.getRandomIntegerInRange(randomIn, 2, 7)), 5, 1, i, structureBoundingBoxIn);
/*      */       } 
/*      */       
/*  484 */       for (int k = 0; k < 9; k++) {
/*  485 */         for (int j = 0; j < 7; j++) {
/*  486 */           clearCurrentPositionBlocksUpwards(worldIn, j, 4, k, structureBoundingBoxIn);
/*  487 */           replaceAirAndLiquidDownwards(worldIn, Blocks.dirt.getDefaultState(), j, -1, k, structureBoundingBoxIn);
/*      */         } 
/*      */       } 
/*      */       
/*  491 */       return true;
/*      */     }
/*      */   }
/*      */   
/*      */   public static class Hall
/*      */     extends Village {
/*      */     public Hall() {}
/*      */     
/*      */     public Hall(StructureVillagePieces.Start start, int p_i45567_2_, Random rand, StructureBoundingBox p_i45567_4_, EnumFacing facing) {
/*  500 */       super(start, p_i45567_2_);
/*  501 */       this.coordBaseMode = facing;
/*  502 */       this.boundingBox = p_i45567_4_;
/*      */     }
/*      */     
/*      */     public static Hall func_175857_a(StructureVillagePieces.Start start, List<StructureComponent> p_175857_1_, Random rand, int p_175857_3_, int p_175857_4_, int p_175857_5_, EnumFacing facing, int p_175857_7_) {
/*  506 */       StructureBoundingBox structureboundingbox = StructureBoundingBox.getComponentToAddBoundingBox(p_175857_3_, p_175857_4_, p_175857_5_, 0, 0, 0, 9, 7, 11, facing);
/*  507 */       return (canVillageGoDeeper(structureboundingbox) && StructureComponent.findIntersecting(p_175857_1_, structureboundingbox) == null) ? new Hall(start, p_175857_7_, rand, structureboundingbox, facing) : null;
/*      */     }
/*      */     
/*      */     public boolean addComponentParts(World worldIn, Random randomIn, StructureBoundingBox structureBoundingBoxIn) {
/*  511 */       if (this.field_143015_k < 0) {
/*  512 */         this.field_143015_k = getAverageGroundLevel(worldIn, structureBoundingBoxIn);
/*      */         
/*  514 */         if (this.field_143015_k < 0) {
/*  515 */           return true;
/*      */         }
/*      */         
/*  518 */         this.boundingBox.offset(0, this.field_143015_k - this.boundingBox.maxY + 7 - 1, 0);
/*      */       } 
/*      */       
/*  521 */       fillWithBlocks(worldIn, structureBoundingBoxIn, 1, 1, 1, 7, 4, 4, Blocks.air.getDefaultState(), Blocks.air.getDefaultState(), false);
/*  522 */       fillWithBlocks(worldIn, structureBoundingBoxIn, 2, 1, 6, 8, 4, 10, Blocks.air.getDefaultState(), Blocks.air.getDefaultState(), false);
/*  523 */       fillWithBlocks(worldIn, structureBoundingBoxIn, 2, 0, 6, 8, 0, 10, Blocks.dirt.getDefaultState(), Blocks.dirt.getDefaultState(), false);
/*  524 */       setBlockState(worldIn, Blocks.cobblestone.getDefaultState(), 6, 0, 6, structureBoundingBoxIn);
/*  525 */       fillWithBlocks(worldIn, structureBoundingBoxIn, 2, 1, 6, 2, 1, 10, Blocks.oak_fence.getDefaultState(), Blocks.oak_fence.getDefaultState(), false);
/*  526 */       fillWithBlocks(worldIn, structureBoundingBoxIn, 8, 1, 6, 8, 1, 10, Blocks.oak_fence.getDefaultState(), Blocks.oak_fence.getDefaultState(), false);
/*  527 */       fillWithBlocks(worldIn, structureBoundingBoxIn, 3, 1, 10, 7, 1, 10, Blocks.oak_fence.getDefaultState(), Blocks.oak_fence.getDefaultState(), false);
/*  528 */       fillWithBlocks(worldIn, structureBoundingBoxIn, 1, 0, 1, 7, 0, 4, Blocks.planks.getDefaultState(), Blocks.planks.getDefaultState(), false);
/*  529 */       fillWithBlocks(worldIn, structureBoundingBoxIn, 0, 0, 0, 0, 3, 5, Blocks.cobblestone.getDefaultState(), Blocks.cobblestone.getDefaultState(), false);
/*  530 */       fillWithBlocks(worldIn, structureBoundingBoxIn, 8, 0, 0, 8, 3, 5, Blocks.cobblestone.getDefaultState(), Blocks.cobblestone.getDefaultState(), false);
/*  531 */       fillWithBlocks(worldIn, structureBoundingBoxIn, 1, 0, 0, 7, 1, 0, Blocks.cobblestone.getDefaultState(), Blocks.cobblestone.getDefaultState(), false);
/*  532 */       fillWithBlocks(worldIn, structureBoundingBoxIn, 1, 0, 5, 7, 1, 5, Blocks.cobblestone.getDefaultState(), Blocks.cobblestone.getDefaultState(), false);
/*  533 */       fillWithBlocks(worldIn, structureBoundingBoxIn, 1, 2, 0, 7, 3, 0, Blocks.planks.getDefaultState(), Blocks.planks.getDefaultState(), false);
/*  534 */       fillWithBlocks(worldIn, structureBoundingBoxIn, 1, 2, 5, 7, 3, 5, Blocks.planks.getDefaultState(), Blocks.planks.getDefaultState(), false);
/*  535 */       fillWithBlocks(worldIn, structureBoundingBoxIn, 0, 4, 1, 8, 4, 1, Blocks.planks.getDefaultState(), Blocks.planks.getDefaultState(), false);
/*  536 */       fillWithBlocks(worldIn, structureBoundingBoxIn, 0, 4, 4, 8, 4, 4, Blocks.planks.getDefaultState(), Blocks.planks.getDefaultState(), false);
/*  537 */       fillWithBlocks(worldIn, structureBoundingBoxIn, 0, 5, 2, 8, 5, 3, Blocks.planks.getDefaultState(), Blocks.planks.getDefaultState(), false);
/*  538 */       setBlockState(worldIn, Blocks.planks.getDefaultState(), 0, 4, 2, structureBoundingBoxIn);
/*  539 */       setBlockState(worldIn, Blocks.planks.getDefaultState(), 0, 4, 3, structureBoundingBoxIn);
/*  540 */       setBlockState(worldIn, Blocks.planks.getDefaultState(), 8, 4, 2, structureBoundingBoxIn);
/*  541 */       setBlockState(worldIn, Blocks.planks.getDefaultState(), 8, 4, 3, structureBoundingBoxIn);
/*  542 */       int i = getMetadataWithOffset(Blocks.oak_stairs, 3);
/*  543 */       int j = getMetadataWithOffset(Blocks.oak_stairs, 2);
/*      */       
/*  545 */       for (int k = -1; k <= 2; k++) {
/*  546 */         for (int l = 0; l <= 8; l++) {
/*  547 */           setBlockState(worldIn, Blocks.oak_stairs.getStateFromMeta(i), l, 4 + k, k, structureBoundingBoxIn);
/*  548 */           setBlockState(worldIn, Blocks.oak_stairs.getStateFromMeta(j), l, 4 + k, 5 - k, structureBoundingBoxIn);
/*      */         } 
/*      */       } 
/*      */       
/*  552 */       setBlockState(worldIn, Blocks.log.getDefaultState(), 0, 2, 1, structureBoundingBoxIn);
/*  553 */       setBlockState(worldIn, Blocks.log.getDefaultState(), 0, 2, 4, structureBoundingBoxIn);
/*  554 */       setBlockState(worldIn, Blocks.log.getDefaultState(), 8, 2, 1, structureBoundingBoxIn);
/*  555 */       setBlockState(worldIn, Blocks.log.getDefaultState(), 8, 2, 4, structureBoundingBoxIn);
/*  556 */       setBlockState(worldIn, Blocks.glass_pane.getDefaultState(), 0, 2, 2, structureBoundingBoxIn);
/*  557 */       setBlockState(worldIn, Blocks.glass_pane.getDefaultState(), 0, 2, 3, structureBoundingBoxIn);
/*  558 */       setBlockState(worldIn, Blocks.glass_pane.getDefaultState(), 8, 2, 2, structureBoundingBoxIn);
/*  559 */       setBlockState(worldIn, Blocks.glass_pane.getDefaultState(), 8, 2, 3, structureBoundingBoxIn);
/*  560 */       setBlockState(worldIn, Blocks.glass_pane.getDefaultState(), 2, 2, 5, structureBoundingBoxIn);
/*  561 */       setBlockState(worldIn, Blocks.glass_pane.getDefaultState(), 3, 2, 5, structureBoundingBoxIn);
/*  562 */       setBlockState(worldIn, Blocks.glass_pane.getDefaultState(), 5, 2, 0, structureBoundingBoxIn);
/*  563 */       setBlockState(worldIn, Blocks.glass_pane.getDefaultState(), 6, 2, 5, structureBoundingBoxIn);
/*  564 */       setBlockState(worldIn, Blocks.oak_fence.getDefaultState(), 2, 1, 3, structureBoundingBoxIn);
/*  565 */       setBlockState(worldIn, Blocks.wooden_pressure_plate.getDefaultState(), 2, 2, 3, structureBoundingBoxIn);
/*  566 */       setBlockState(worldIn, Blocks.planks.getDefaultState(), 1, 1, 4, structureBoundingBoxIn);
/*  567 */       setBlockState(worldIn, Blocks.oak_stairs.getStateFromMeta(getMetadataWithOffset(Blocks.oak_stairs, 3)), 2, 1, 4, structureBoundingBoxIn);
/*  568 */       setBlockState(worldIn, Blocks.oak_stairs.getStateFromMeta(getMetadataWithOffset(Blocks.oak_stairs, 1)), 1, 1, 3, structureBoundingBoxIn);
/*  569 */       fillWithBlocks(worldIn, structureBoundingBoxIn, 5, 0, 1, 7, 0, 3, Blocks.double_stone_slab.getDefaultState(), Blocks.double_stone_slab.getDefaultState(), false);
/*  570 */       setBlockState(worldIn, Blocks.double_stone_slab.getDefaultState(), 6, 1, 1, structureBoundingBoxIn);
/*  571 */       setBlockState(worldIn, Blocks.double_stone_slab.getDefaultState(), 6, 1, 2, structureBoundingBoxIn);
/*  572 */       setBlockState(worldIn, Blocks.air.getDefaultState(), 2, 1, 0, structureBoundingBoxIn);
/*  573 */       setBlockState(worldIn, Blocks.air.getDefaultState(), 2, 2, 0, structureBoundingBoxIn);
/*  574 */       setBlockState(worldIn, Blocks.torch.getDefaultState().withProperty((IProperty)BlockTorch.FACING, (Comparable)this.coordBaseMode), 2, 3, 1, structureBoundingBoxIn);
/*  575 */       placeDoorCurrentPosition(worldIn, structureBoundingBoxIn, randomIn, 2, 1, 0, EnumFacing.getHorizontal(getMetadataWithOffset(Blocks.oak_door, 1)));
/*      */       
/*  577 */       if (getBlockStateFromPos(worldIn, 2, 0, -1, structureBoundingBoxIn).getBlock().getMaterial() == Material.air && getBlockStateFromPos(worldIn, 2, -1, -1, structureBoundingBoxIn).getBlock().getMaterial() != Material.air) {
/*  578 */         setBlockState(worldIn, Blocks.stone_stairs.getStateFromMeta(getMetadataWithOffset(Blocks.stone_stairs, 3)), 2, 0, -1, structureBoundingBoxIn);
/*      */       }
/*      */       
/*  581 */       setBlockState(worldIn, Blocks.air.getDefaultState(), 6, 1, 5, structureBoundingBoxIn);
/*  582 */       setBlockState(worldIn, Blocks.air.getDefaultState(), 6, 2, 5, structureBoundingBoxIn);
/*  583 */       setBlockState(worldIn, Blocks.torch.getDefaultState().withProperty((IProperty)BlockTorch.FACING, (Comparable)this.coordBaseMode.getOpposite()), 6, 3, 4, structureBoundingBoxIn);
/*  584 */       placeDoorCurrentPosition(worldIn, structureBoundingBoxIn, randomIn, 6, 1, 5, EnumFacing.getHorizontal(getMetadataWithOffset(Blocks.oak_door, 1)));
/*      */       
/*  586 */       for (int i1 = 0; i1 < 5; i1++) {
/*  587 */         for (int j1 = 0; j1 < 9; j1++) {
/*  588 */           clearCurrentPositionBlocksUpwards(worldIn, j1, 7, i1, structureBoundingBoxIn);
/*  589 */           replaceAirAndLiquidDownwards(worldIn, Blocks.cobblestone.getDefaultState(), j1, -1, i1, structureBoundingBoxIn);
/*      */         } 
/*      */       } 
/*      */       
/*  593 */       spawnVillagers(worldIn, structureBoundingBoxIn, 4, 1, 2, 2);
/*  594 */       return true;
/*      */     }
/*      */     
/*      */     protected int func_180779_c(int p_180779_1_, int p_180779_2_) {
/*  598 */       return (p_180779_1_ == 0) ? 4 : super.func_180779_c(p_180779_1_, p_180779_2_);
/*      */     }
/*      */   }
/*      */   
/*      */   public static class House1
/*      */     extends Village {
/*      */     public House1() {}
/*      */     
/*      */     public House1(StructureVillagePieces.Start start, int p_i45571_2_, Random rand, StructureBoundingBox p_i45571_4_, EnumFacing facing) {
/*  607 */       super(start, p_i45571_2_);
/*  608 */       this.coordBaseMode = facing;
/*  609 */       this.boundingBox = p_i45571_4_;
/*      */     }
/*      */     
/*      */     public static House1 func_175850_a(StructureVillagePieces.Start start, List<StructureComponent> p_175850_1_, Random rand, int p_175850_3_, int p_175850_4_, int p_175850_5_, EnumFacing facing, int p_175850_7_) {
/*  613 */       StructureBoundingBox structureboundingbox = StructureBoundingBox.getComponentToAddBoundingBox(p_175850_3_, p_175850_4_, p_175850_5_, 0, 0, 0, 9, 9, 6, facing);
/*  614 */       return (canVillageGoDeeper(structureboundingbox) && StructureComponent.findIntersecting(p_175850_1_, structureboundingbox) == null) ? new House1(start, p_175850_7_, rand, structureboundingbox, facing) : null;
/*      */     }
/*      */     
/*      */     public boolean addComponentParts(World worldIn, Random randomIn, StructureBoundingBox structureBoundingBoxIn) {
/*  618 */       if (this.field_143015_k < 0) {
/*  619 */         this.field_143015_k = getAverageGroundLevel(worldIn, structureBoundingBoxIn);
/*      */         
/*  621 */         if (this.field_143015_k < 0) {
/*  622 */           return true;
/*      */         }
/*      */         
/*  625 */         this.boundingBox.offset(0, this.field_143015_k - this.boundingBox.maxY + 9 - 1, 0);
/*      */       } 
/*      */       
/*  628 */       fillWithBlocks(worldIn, structureBoundingBoxIn, 1, 1, 1, 7, 5, 4, Blocks.air.getDefaultState(), Blocks.air.getDefaultState(), false);
/*  629 */       fillWithBlocks(worldIn, structureBoundingBoxIn, 0, 0, 0, 8, 0, 5, Blocks.cobblestone.getDefaultState(), Blocks.cobblestone.getDefaultState(), false);
/*  630 */       fillWithBlocks(worldIn, structureBoundingBoxIn, 0, 5, 0, 8, 5, 5, Blocks.cobblestone.getDefaultState(), Blocks.cobblestone.getDefaultState(), false);
/*  631 */       fillWithBlocks(worldIn, structureBoundingBoxIn, 0, 6, 1, 8, 6, 4, Blocks.cobblestone.getDefaultState(), Blocks.cobblestone.getDefaultState(), false);
/*  632 */       fillWithBlocks(worldIn, structureBoundingBoxIn, 0, 7, 2, 8, 7, 3, Blocks.cobblestone.getDefaultState(), Blocks.cobblestone.getDefaultState(), false);
/*  633 */       int i = getMetadataWithOffset(Blocks.oak_stairs, 3);
/*  634 */       int j = getMetadataWithOffset(Blocks.oak_stairs, 2);
/*      */       
/*  636 */       for (int k = -1; k <= 2; k++) {
/*  637 */         for (int l = 0; l <= 8; l++) {
/*  638 */           setBlockState(worldIn, Blocks.oak_stairs.getStateFromMeta(i), l, 6 + k, k, structureBoundingBoxIn);
/*  639 */           setBlockState(worldIn, Blocks.oak_stairs.getStateFromMeta(j), l, 6 + k, 5 - k, structureBoundingBoxIn);
/*      */         } 
/*      */       } 
/*      */       
/*  643 */       fillWithBlocks(worldIn, structureBoundingBoxIn, 0, 1, 0, 0, 1, 5, Blocks.cobblestone.getDefaultState(), Blocks.cobblestone.getDefaultState(), false);
/*  644 */       fillWithBlocks(worldIn, structureBoundingBoxIn, 1, 1, 5, 8, 1, 5, Blocks.cobblestone.getDefaultState(), Blocks.cobblestone.getDefaultState(), false);
/*  645 */       fillWithBlocks(worldIn, structureBoundingBoxIn, 8, 1, 0, 8, 1, 4, Blocks.cobblestone.getDefaultState(), Blocks.cobblestone.getDefaultState(), false);
/*  646 */       fillWithBlocks(worldIn, structureBoundingBoxIn, 2, 1, 0, 7, 1, 0, Blocks.cobblestone.getDefaultState(), Blocks.cobblestone.getDefaultState(), false);
/*  647 */       fillWithBlocks(worldIn, structureBoundingBoxIn, 0, 2, 0, 0, 4, 0, Blocks.cobblestone.getDefaultState(), Blocks.cobblestone.getDefaultState(), false);
/*  648 */       fillWithBlocks(worldIn, structureBoundingBoxIn, 0, 2, 5, 0, 4, 5, Blocks.cobblestone.getDefaultState(), Blocks.cobblestone.getDefaultState(), false);
/*  649 */       fillWithBlocks(worldIn, structureBoundingBoxIn, 8, 2, 5, 8, 4, 5, Blocks.cobblestone.getDefaultState(), Blocks.cobblestone.getDefaultState(), false);
/*  650 */       fillWithBlocks(worldIn, structureBoundingBoxIn, 8, 2, 0, 8, 4, 0, Blocks.cobblestone.getDefaultState(), Blocks.cobblestone.getDefaultState(), false);
/*  651 */       fillWithBlocks(worldIn, structureBoundingBoxIn, 0, 2, 1, 0, 4, 4, Blocks.planks.getDefaultState(), Blocks.planks.getDefaultState(), false);
/*  652 */       fillWithBlocks(worldIn, structureBoundingBoxIn, 1, 2, 5, 7, 4, 5, Blocks.planks.getDefaultState(), Blocks.planks.getDefaultState(), false);
/*  653 */       fillWithBlocks(worldIn, structureBoundingBoxIn, 8, 2, 1, 8, 4, 4, Blocks.planks.getDefaultState(), Blocks.planks.getDefaultState(), false);
/*  654 */       fillWithBlocks(worldIn, structureBoundingBoxIn, 1, 2, 0, 7, 4, 0, Blocks.planks.getDefaultState(), Blocks.planks.getDefaultState(), false);
/*  655 */       setBlockState(worldIn, Blocks.glass_pane.getDefaultState(), 4, 2, 0, structureBoundingBoxIn);
/*  656 */       setBlockState(worldIn, Blocks.glass_pane.getDefaultState(), 5, 2, 0, structureBoundingBoxIn);
/*  657 */       setBlockState(worldIn, Blocks.glass_pane.getDefaultState(), 6, 2, 0, structureBoundingBoxIn);
/*  658 */       setBlockState(worldIn, Blocks.glass_pane.getDefaultState(), 4, 3, 0, structureBoundingBoxIn);
/*  659 */       setBlockState(worldIn, Blocks.glass_pane.getDefaultState(), 5, 3, 0, structureBoundingBoxIn);
/*  660 */       setBlockState(worldIn, Blocks.glass_pane.getDefaultState(), 6, 3, 0, structureBoundingBoxIn);
/*  661 */       setBlockState(worldIn, Blocks.glass_pane.getDefaultState(), 0, 2, 2, structureBoundingBoxIn);
/*  662 */       setBlockState(worldIn, Blocks.glass_pane.getDefaultState(), 0, 2, 3, structureBoundingBoxIn);
/*  663 */       setBlockState(worldIn, Blocks.glass_pane.getDefaultState(), 0, 3, 2, structureBoundingBoxIn);
/*  664 */       setBlockState(worldIn, Blocks.glass_pane.getDefaultState(), 0, 3, 3, structureBoundingBoxIn);
/*  665 */       setBlockState(worldIn, Blocks.glass_pane.getDefaultState(), 8, 2, 2, structureBoundingBoxIn);
/*  666 */       setBlockState(worldIn, Blocks.glass_pane.getDefaultState(), 8, 2, 3, structureBoundingBoxIn);
/*  667 */       setBlockState(worldIn, Blocks.glass_pane.getDefaultState(), 8, 3, 2, structureBoundingBoxIn);
/*  668 */       setBlockState(worldIn, Blocks.glass_pane.getDefaultState(), 8, 3, 3, structureBoundingBoxIn);
/*  669 */       setBlockState(worldIn, Blocks.glass_pane.getDefaultState(), 2, 2, 5, structureBoundingBoxIn);
/*  670 */       setBlockState(worldIn, Blocks.glass_pane.getDefaultState(), 3, 2, 5, structureBoundingBoxIn);
/*  671 */       setBlockState(worldIn, Blocks.glass_pane.getDefaultState(), 5, 2, 5, structureBoundingBoxIn);
/*  672 */       setBlockState(worldIn, Blocks.glass_pane.getDefaultState(), 6, 2, 5, structureBoundingBoxIn);
/*  673 */       fillWithBlocks(worldIn, structureBoundingBoxIn, 1, 4, 1, 7, 4, 1, Blocks.planks.getDefaultState(), Blocks.planks.getDefaultState(), false);
/*  674 */       fillWithBlocks(worldIn, structureBoundingBoxIn, 1, 4, 4, 7, 4, 4, Blocks.planks.getDefaultState(), Blocks.planks.getDefaultState(), false);
/*  675 */       fillWithBlocks(worldIn, structureBoundingBoxIn, 1, 3, 4, 7, 3, 4, Blocks.bookshelf.getDefaultState(), Blocks.bookshelf.getDefaultState(), false);
/*  676 */       setBlockState(worldIn, Blocks.planks.getDefaultState(), 7, 1, 4, structureBoundingBoxIn);
/*  677 */       setBlockState(worldIn, Blocks.oak_stairs.getStateFromMeta(getMetadataWithOffset(Blocks.oak_stairs, 0)), 7, 1, 3, structureBoundingBoxIn);
/*  678 */       int j1 = getMetadataWithOffset(Blocks.oak_stairs, 3);
/*  679 */       setBlockState(worldIn, Blocks.oak_stairs.getStateFromMeta(j1), 6, 1, 4, structureBoundingBoxIn);
/*  680 */       setBlockState(worldIn, Blocks.oak_stairs.getStateFromMeta(j1), 5, 1, 4, structureBoundingBoxIn);
/*  681 */       setBlockState(worldIn, Blocks.oak_stairs.getStateFromMeta(j1), 4, 1, 4, structureBoundingBoxIn);
/*  682 */       setBlockState(worldIn, Blocks.oak_stairs.getStateFromMeta(j1), 3, 1, 4, structureBoundingBoxIn);
/*  683 */       setBlockState(worldIn, Blocks.oak_fence.getDefaultState(), 6, 1, 3, structureBoundingBoxIn);
/*  684 */       setBlockState(worldIn, Blocks.wooden_pressure_plate.getDefaultState(), 6, 2, 3, structureBoundingBoxIn);
/*  685 */       setBlockState(worldIn, Blocks.oak_fence.getDefaultState(), 4, 1, 3, structureBoundingBoxIn);
/*  686 */       setBlockState(worldIn, Blocks.wooden_pressure_plate.getDefaultState(), 4, 2, 3, structureBoundingBoxIn);
/*  687 */       setBlockState(worldIn, Blocks.crafting_table.getDefaultState(), 7, 1, 1, structureBoundingBoxIn);
/*  688 */       setBlockState(worldIn, Blocks.air.getDefaultState(), 1, 1, 0, structureBoundingBoxIn);
/*  689 */       setBlockState(worldIn, Blocks.air.getDefaultState(), 1, 2, 0, structureBoundingBoxIn);
/*  690 */       placeDoorCurrentPosition(worldIn, structureBoundingBoxIn, randomIn, 1, 1, 0, EnumFacing.getHorizontal(getMetadataWithOffset(Blocks.oak_door, 1)));
/*      */       
/*  692 */       if (getBlockStateFromPos(worldIn, 1, 0, -1, structureBoundingBoxIn).getBlock().getMaterial() == Material.air && getBlockStateFromPos(worldIn, 1, -1, -1, structureBoundingBoxIn).getBlock().getMaterial() != Material.air) {
/*  693 */         setBlockState(worldIn, Blocks.stone_stairs.getStateFromMeta(getMetadataWithOffset(Blocks.stone_stairs, 3)), 1, 0, -1, structureBoundingBoxIn);
/*      */       }
/*      */       
/*  696 */       for (int k1 = 0; k1 < 6; k1++) {
/*  697 */         for (int i1 = 0; i1 < 9; i1++) {
/*  698 */           clearCurrentPositionBlocksUpwards(worldIn, i1, 9, k1, structureBoundingBoxIn);
/*  699 */           replaceAirAndLiquidDownwards(worldIn, Blocks.cobblestone.getDefaultState(), i1, -1, k1, structureBoundingBoxIn);
/*      */         } 
/*      */       } 
/*      */       
/*  703 */       spawnVillagers(worldIn, structureBoundingBoxIn, 2, 1, 2, 1);
/*  704 */       return true;
/*      */     }
/*      */     
/*      */     protected int func_180779_c(int p_180779_1_, int p_180779_2_) {
/*  708 */       return 1;
/*      */     }
/*      */   }
/*      */   
/*      */   public static class House2 extends Village {
/*  713 */     private static final List<WeightedRandomChestContent> villageBlacksmithChestContents = Lists.newArrayList((Object[])new WeightedRandomChestContent[] { new WeightedRandomChestContent(Items.diamond, 0, 1, 3, 3), new WeightedRandomChestContent(Items.iron_ingot, 0, 1, 5, 10), new WeightedRandomChestContent(Items.gold_ingot, 0, 1, 3, 5), new WeightedRandomChestContent(Items.bread, 0, 1, 3, 15), new WeightedRandomChestContent(Items.apple, 0, 1, 3, 15), new WeightedRandomChestContent(Items.iron_pickaxe, 0, 1, 1, 5), new WeightedRandomChestContent(Items.iron_sword, 0, 1, 1, 5), new WeightedRandomChestContent((Item)Items.iron_chestplate, 0, 1, 1, 5), new WeightedRandomChestContent((Item)Items.iron_helmet, 0, 1, 1, 5), new WeightedRandomChestContent((Item)Items.iron_leggings, 0, 1, 1, 5), new WeightedRandomChestContent((Item)Items.iron_boots, 0, 1, 1, 5), new WeightedRandomChestContent(Item.getItemFromBlock(Blocks.obsidian), 0, 3, 7, 5), new WeightedRandomChestContent(Item.getItemFromBlock(Blocks.sapling), 0, 3, 7, 5), new WeightedRandomChestContent(Items.saddle, 0, 1, 1, 3), new WeightedRandomChestContent(Items.iron_horse_armor, 0, 1, 1, 1), new WeightedRandomChestContent(Items.golden_horse_armor, 0, 1, 1, 1), new WeightedRandomChestContent(Items.diamond_horse_armor, 0, 1, 1, 1) });
/*      */     
/*      */     private boolean hasMadeChest;
/*      */     
/*      */     public House2() {}
/*      */     
/*      */     public House2(StructureVillagePieces.Start start, int p_i45563_2_, Random rand, StructureBoundingBox p_i45563_4_, EnumFacing facing) {
/*  720 */       super(start, p_i45563_2_);
/*  721 */       this.coordBaseMode = facing;
/*  722 */       this.boundingBox = p_i45563_4_;
/*      */     }
/*      */     
/*      */     public static House2 func_175855_a(StructureVillagePieces.Start start, List<StructureComponent> p_175855_1_, Random rand, int p_175855_3_, int p_175855_4_, int p_175855_5_, EnumFacing facing, int p_175855_7_) {
/*  726 */       StructureBoundingBox structureboundingbox = StructureBoundingBox.getComponentToAddBoundingBox(p_175855_3_, p_175855_4_, p_175855_5_, 0, 0, 0, 10, 6, 7, facing);
/*  727 */       return (canVillageGoDeeper(structureboundingbox) && StructureComponent.findIntersecting(p_175855_1_, structureboundingbox) == null) ? new House2(start, p_175855_7_, rand, structureboundingbox, facing) : null;
/*      */     }
/*      */     
/*      */     protected void writeStructureToNBT(NBTTagCompound tagCompound) {
/*  731 */       super.writeStructureToNBT(tagCompound);
/*  732 */       tagCompound.setBoolean("Chest", this.hasMadeChest);
/*      */     }
/*      */     
/*      */     protected void readStructureFromNBT(NBTTagCompound tagCompound) {
/*  736 */       super.readStructureFromNBT(tagCompound);
/*  737 */       this.hasMadeChest = tagCompound.getBoolean("Chest");
/*      */     }
/*      */     
/*      */     public boolean addComponentParts(World worldIn, Random randomIn, StructureBoundingBox structureBoundingBoxIn) {
/*  741 */       if (this.field_143015_k < 0) {
/*  742 */         this.field_143015_k = getAverageGroundLevel(worldIn, structureBoundingBoxIn);
/*      */         
/*  744 */         if (this.field_143015_k < 0) {
/*  745 */           return true;
/*      */         }
/*      */         
/*  748 */         this.boundingBox.offset(0, this.field_143015_k - this.boundingBox.maxY + 6 - 1, 0);
/*      */       } 
/*      */       
/*  751 */       fillWithBlocks(worldIn, structureBoundingBoxIn, 0, 1, 0, 9, 4, 6, Blocks.air.getDefaultState(), Blocks.air.getDefaultState(), false);
/*  752 */       fillWithBlocks(worldIn, structureBoundingBoxIn, 0, 0, 0, 9, 0, 6, Blocks.cobblestone.getDefaultState(), Blocks.cobblestone.getDefaultState(), false);
/*  753 */       fillWithBlocks(worldIn, structureBoundingBoxIn, 0, 4, 0, 9, 4, 6, Blocks.cobblestone.getDefaultState(), Blocks.cobblestone.getDefaultState(), false);
/*  754 */       fillWithBlocks(worldIn, structureBoundingBoxIn, 0, 5, 0, 9, 5, 6, Blocks.stone_slab.getDefaultState(), Blocks.stone_slab.getDefaultState(), false);
/*  755 */       fillWithBlocks(worldIn, structureBoundingBoxIn, 1, 5, 1, 8, 5, 5, Blocks.air.getDefaultState(), Blocks.air.getDefaultState(), false);
/*  756 */       fillWithBlocks(worldIn, structureBoundingBoxIn, 1, 1, 0, 2, 3, 0, Blocks.planks.getDefaultState(), Blocks.planks.getDefaultState(), false);
/*  757 */       fillWithBlocks(worldIn, structureBoundingBoxIn, 0, 1, 0, 0, 4, 0, Blocks.log.getDefaultState(), Blocks.log.getDefaultState(), false);
/*  758 */       fillWithBlocks(worldIn, structureBoundingBoxIn, 3, 1, 0, 3, 4, 0, Blocks.log.getDefaultState(), Blocks.log.getDefaultState(), false);
/*  759 */       fillWithBlocks(worldIn, structureBoundingBoxIn, 0, 1, 6, 0, 4, 6, Blocks.log.getDefaultState(), Blocks.log.getDefaultState(), false);
/*  760 */       setBlockState(worldIn, Blocks.planks.getDefaultState(), 3, 3, 1, structureBoundingBoxIn);
/*  761 */       fillWithBlocks(worldIn, structureBoundingBoxIn, 3, 1, 2, 3, 3, 2, Blocks.planks.getDefaultState(), Blocks.planks.getDefaultState(), false);
/*  762 */       fillWithBlocks(worldIn, structureBoundingBoxIn, 4, 1, 3, 5, 3, 3, Blocks.planks.getDefaultState(), Blocks.planks.getDefaultState(), false);
/*  763 */       fillWithBlocks(worldIn, structureBoundingBoxIn, 0, 1, 1, 0, 3, 5, Blocks.planks.getDefaultState(), Blocks.planks.getDefaultState(), false);
/*  764 */       fillWithBlocks(worldIn, structureBoundingBoxIn, 1, 1, 6, 5, 3, 6, Blocks.planks.getDefaultState(), Blocks.planks.getDefaultState(), false);
/*  765 */       fillWithBlocks(worldIn, structureBoundingBoxIn, 5, 1, 0, 5, 3, 0, Blocks.oak_fence.getDefaultState(), Blocks.oak_fence.getDefaultState(), false);
/*  766 */       fillWithBlocks(worldIn, structureBoundingBoxIn, 9, 1, 0, 9, 3, 0, Blocks.oak_fence.getDefaultState(), Blocks.oak_fence.getDefaultState(), false);
/*  767 */       fillWithBlocks(worldIn, structureBoundingBoxIn, 6, 1, 4, 9, 4, 6, Blocks.cobblestone.getDefaultState(), Blocks.cobblestone.getDefaultState(), false);
/*  768 */       setBlockState(worldIn, Blocks.flowing_lava.getDefaultState(), 7, 1, 5, structureBoundingBoxIn);
/*  769 */       setBlockState(worldIn, Blocks.flowing_lava.getDefaultState(), 8, 1, 5, structureBoundingBoxIn);
/*  770 */       setBlockState(worldIn, Blocks.iron_bars.getDefaultState(), 9, 2, 5, structureBoundingBoxIn);
/*  771 */       setBlockState(worldIn, Blocks.iron_bars.getDefaultState(), 9, 2, 4, structureBoundingBoxIn);
/*  772 */       fillWithBlocks(worldIn, structureBoundingBoxIn, 7, 2, 4, 8, 2, 5, Blocks.air.getDefaultState(), Blocks.air.getDefaultState(), false);
/*  773 */       setBlockState(worldIn, Blocks.cobblestone.getDefaultState(), 6, 1, 3, structureBoundingBoxIn);
/*  774 */       setBlockState(worldIn, Blocks.furnace.getDefaultState(), 6, 2, 3, structureBoundingBoxIn);
/*  775 */       setBlockState(worldIn, Blocks.furnace.getDefaultState(), 6, 3, 3, structureBoundingBoxIn);
/*  776 */       setBlockState(worldIn, Blocks.double_stone_slab.getDefaultState(), 8, 1, 1, structureBoundingBoxIn);
/*  777 */       setBlockState(worldIn, Blocks.glass_pane.getDefaultState(), 0, 2, 2, structureBoundingBoxIn);
/*  778 */       setBlockState(worldIn, Blocks.glass_pane.getDefaultState(), 0, 2, 4, structureBoundingBoxIn);
/*  779 */       setBlockState(worldIn, Blocks.glass_pane.getDefaultState(), 2, 2, 6, structureBoundingBoxIn);
/*  780 */       setBlockState(worldIn, Blocks.glass_pane.getDefaultState(), 4, 2, 6, structureBoundingBoxIn);
/*  781 */       setBlockState(worldIn, Blocks.oak_fence.getDefaultState(), 2, 1, 4, structureBoundingBoxIn);
/*  782 */       setBlockState(worldIn, Blocks.wooden_pressure_plate.getDefaultState(), 2, 2, 4, structureBoundingBoxIn);
/*  783 */       setBlockState(worldIn, Blocks.planks.getDefaultState(), 1, 1, 5, structureBoundingBoxIn);
/*  784 */       setBlockState(worldIn, Blocks.oak_stairs.getStateFromMeta(getMetadataWithOffset(Blocks.oak_stairs, 3)), 2, 1, 5, structureBoundingBoxIn);
/*  785 */       setBlockState(worldIn, Blocks.oak_stairs.getStateFromMeta(getMetadataWithOffset(Blocks.oak_stairs, 1)), 1, 1, 4, structureBoundingBoxIn);
/*      */       
/*  787 */       if (!this.hasMadeChest && structureBoundingBoxIn.isVecInside((Vec3i)new BlockPos(getXWithOffset(5, 5), getYWithOffset(1), getZWithOffset(5, 5)))) {
/*  788 */         this.hasMadeChest = true;
/*  789 */         generateChestContents(worldIn, structureBoundingBoxIn, randomIn, 5, 1, 5, villageBlacksmithChestContents, 3 + randomIn.nextInt(6));
/*      */       } 
/*      */       
/*  792 */       for (int i = 6; i <= 8; i++) {
/*  793 */         if (getBlockStateFromPos(worldIn, i, 0, -1, structureBoundingBoxIn).getBlock().getMaterial() == Material.air && getBlockStateFromPos(worldIn, i, -1, -1, structureBoundingBoxIn).getBlock().getMaterial() != Material.air) {
/*  794 */           setBlockState(worldIn, Blocks.stone_stairs.getStateFromMeta(getMetadataWithOffset(Blocks.stone_stairs, 3)), i, 0, -1, structureBoundingBoxIn);
/*      */         }
/*      */       } 
/*      */       
/*  798 */       for (int k = 0; k < 7; k++) {
/*  799 */         for (int j = 0; j < 10; j++) {
/*  800 */           clearCurrentPositionBlocksUpwards(worldIn, j, 6, k, structureBoundingBoxIn);
/*  801 */           replaceAirAndLiquidDownwards(worldIn, Blocks.cobblestone.getDefaultState(), j, -1, k, structureBoundingBoxIn);
/*      */         } 
/*      */       } 
/*      */       
/*  805 */       spawnVillagers(worldIn, structureBoundingBoxIn, 7, 1, 1, 1);
/*  806 */       return true;
/*      */     }
/*      */     
/*      */     protected int func_180779_c(int p_180779_1_, int p_180779_2_) {
/*  810 */       return 3;
/*      */     }
/*      */   }
/*      */   
/*      */   public static class House3
/*      */     extends Village {
/*      */     public House3() {}
/*      */     
/*      */     public House3(StructureVillagePieces.Start start, int p_i45561_2_, Random rand, StructureBoundingBox p_i45561_4_, EnumFacing facing) {
/*  819 */       super(start, p_i45561_2_);
/*  820 */       this.coordBaseMode = facing;
/*  821 */       this.boundingBox = p_i45561_4_;
/*      */     }
/*      */     
/*      */     public static House3 func_175849_a(StructureVillagePieces.Start start, List<StructureComponent> p_175849_1_, Random rand, int p_175849_3_, int p_175849_4_, int p_175849_5_, EnumFacing facing, int p_175849_7_) {
/*  825 */       StructureBoundingBox structureboundingbox = StructureBoundingBox.getComponentToAddBoundingBox(p_175849_3_, p_175849_4_, p_175849_5_, 0, 0, 0, 9, 7, 12, facing);
/*  826 */       return (canVillageGoDeeper(structureboundingbox) && StructureComponent.findIntersecting(p_175849_1_, structureboundingbox) == null) ? new House3(start, p_175849_7_, rand, structureboundingbox, facing) : null;
/*      */     }
/*      */     
/*      */     public boolean addComponentParts(World worldIn, Random randomIn, StructureBoundingBox structureBoundingBoxIn) {
/*  830 */       if (this.field_143015_k < 0) {
/*  831 */         this.field_143015_k = getAverageGroundLevel(worldIn, structureBoundingBoxIn);
/*      */         
/*  833 */         if (this.field_143015_k < 0) {
/*  834 */           return true;
/*      */         }
/*      */         
/*  837 */         this.boundingBox.offset(0, this.field_143015_k - this.boundingBox.maxY + 7 - 1, 0);
/*      */       } 
/*      */       
/*  840 */       fillWithBlocks(worldIn, structureBoundingBoxIn, 1, 1, 1, 7, 4, 4, Blocks.air.getDefaultState(), Blocks.air.getDefaultState(), false);
/*  841 */       fillWithBlocks(worldIn, structureBoundingBoxIn, 2, 1, 6, 8, 4, 10, Blocks.air.getDefaultState(), Blocks.air.getDefaultState(), false);
/*  842 */       fillWithBlocks(worldIn, structureBoundingBoxIn, 2, 0, 5, 8, 0, 10, Blocks.planks.getDefaultState(), Blocks.planks.getDefaultState(), false);
/*  843 */       fillWithBlocks(worldIn, structureBoundingBoxIn, 1, 0, 1, 7, 0, 4, Blocks.planks.getDefaultState(), Blocks.planks.getDefaultState(), false);
/*  844 */       fillWithBlocks(worldIn, structureBoundingBoxIn, 0, 0, 0, 0, 3, 5, Blocks.cobblestone.getDefaultState(), Blocks.cobblestone.getDefaultState(), false);
/*  845 */       fillWithBlocks(worldIn, structureBoundingBoxIn, 8, 0, 0, 8, 3, 10, Blocks.cobblestone.getDefaultState(), Blocks.cobblestone.getDefaultState(), false);
/*  846 */       fillWithBlocks(worldIn, structureBoundingBoxIn, 1, 0, 0, 7, 2, 0, Blocks.cobblestone.getDefaultState(), Blocks.cobblestone.getDefaultState(), false);
/*  847 */       fillWithBlocks(worldIn, structureBoundingBoxIn, 1, 0, 5, 2, 1, 5, Blocks.cobblestone.getDefaultState(), Blocks.cobblestone.getDefaultState(), false);
/*  848 */       fillWithBlocks(worldIn, structureBoundingBoxIn, 2, 0, 6, 2, 3, 10, Blocks.cobblestone.getDefaultState(), Blocks.cobblestone.getDefaultState(), false);
/*  849 */       fillWithBlocks(worldIn, structureBoundingBoxIn, 3, 0, 10, 7, 3, 10, Blocks.cobblestone.getDefaultState(), Blocks.cobblestone.getDefaultState(), false);
/*  850 */       fillWithBlocks(worldIn, structureBoundingBoxIn, 1, 2, 0, 7, 3, 0, Blocks.planks.getDefaultState(), Blocks.planks.getDefaultState(), false);
/*  851 */       fillWithBlocks(worldIn, structureBoundingBoxIn, 1, 2, 5, 2, 3, 5, Blocks.planks.getDefaultState(), Blocks.planks.getDefaultState(), false);
/*  852 */       fillWithBlocks(worldIn, structureBoundingBoxIn, 0, 4, 1, 8, 4, 1, Blocks.planks.getDefaultState(), Blocks.planks.getDefaultState(), false);
/*  853 */       fillWithBlocks(worldIn, structureBoundingBoxIn, 0, 4, 4, 3, 4, 4, Blocks.planks.getDefaultState(), Blocks.planks.getDefaultState(), false);
/*  854 */       fillWithBlocks(worldIn, structureBoundingBoxIn, 0, 5, 2, 8, 5, 3, Blocks.planks.getDefaultState(), Blocks.planks.getDefaultState(), false);
/*  855 */       setBlockState(worldIn, Blocks.planks.getDefaultState(), 0, 4, 2, structureBoundingBoxIn);
/*  856 */       setBlockState(worldIn, Blocks.planks.getDefaultState(), 0, 4, 3, structureBoundingBoxIn);
/*  857 */       setBlockState(worldIn, Blocks.planks.getDefaultState(), 8, 4, 2, structureBoundingBoxIn);
/*  858 */       setBlockState(worldIn, Blocks.planks.getDefaultState(), 8, 4, 3, structureBoundingBoxIn);
/*  859 */       setBlockState(worldIn, Blocks.planks.getDefaultState(), 8, 4, 4, structureBoundingBoxIn);
/*  860 */       int i = getMetadataWithOffset(Blocks.oak_stairs, 3);
/*  861 */       int j = getMetadataWithOffset(Blocks.oak_stairs, 2);
/*      */       
/*  863 */       for (int k = -1; k <= 2; k++) {
/*  864 */         for (int l = 0; l <= 8; l++) {
/*  865 */           setBlockState(worldIn, Blocks.oak_stairs.getStateFromMeta(i), l, 4 + k, k, structureBoundingBoxIn);
/*      */           
/*  867 */           if ((k > -1 || l <= 1) && (k > 0 || l <= 3) && (k > 1 || l <= 4 || l >= 6)) {
/*  868 */             setBlockState(worldIn, Blocks.oak_stairs.getStateFromMeta(j), l, 4 + k, 5 - k, structureBoundingBoxIn);
/*      */           }
/*      */         } 
/*      */       } 
/*      */       
/*  873 */       fillWithBlocks(worldIn, structureBoundingBoxIn, 3, 4, 5, 3, 4, 10, Blocks.planks.getDefaultState(), Blocks.planks.getDefaultState(), false);
/*  874 */       fillWithBlocks(worldIn, structureBoundingBoxIn, 7, 4, 2, 7, 4, 10, Blocks.planks.getDefaultState(), Blocks.planks.getDefaultState(), false);
/*  875 */       fillWithBlocks(worldIn, structureBoundingBoxIn, 4, 5, 4, 4, 5, 10, Blocks.planks.getDefaultState(), Blocks.planks.getDefaultState(), false);
/*  876 */       fillWithBlocks(worldIn, structureBoundingBoxIn, 6, 5, 4, 6, 5, 10, Blocks.planks.getDefaultState(), Blocks.planks.getDefaultState(), false);
/*  877 */       fillWithBlocks(worldIn, structureBoundingBoxIn, 5, 6, 3, 5, 6, 10, Blocks.planks.getDefaultState(), Blocks.planks.getDefaultState(), false);
/*  878 */       int k1 = getMetadataWithOffset(Blocks.oak_stairs, 0);
/*      */       
/*  880 */       for (int l1 = 4; l1 >= 1; l1--) {
/*  881 */         setBlockState(worldIn, Blocks.planks.getDefaultState(), l1, 2 + l1, 7 - l1, structureBoundingBoxIn);
/*      */         
/*  883 */         for (int i1 = 8 - l1; i1 <= 10; i1++) {
/*  884 */           setBlockState(worldIn, Blocks.oak_stairs.getStateFromMeta(k1), l1, 2 + l1, i1, structureBoundingBoxIn);
/*      */         }
/*      */       } 
/*      */       
/*  888 */       int i2 = getMetadataWithOffset(Blocks.oak_stairs, 1);
/*  889 */       setBlockState(worldIn, Blocks.planks.getDefaultState(), 6, 6, 3, structureBoundingBoxIn);
/*  890 */       setBlockState(worldIn, Blocks.planks.getDefaultState(), 7, 5, 4, structureBoundingBoxIn);
/*  891 */       setBlockState(worldIn, Blocks.oak_stairs.getStateFromMeta(i2), 6, 6, 4, structureBoundingBoxIn);
/*      */       
/*  893 */       for (int j2 = 6; j2 <= 8; j2++) {
/*  894 */         for (int j1 = 5; j1 <= 10; j1++) {
/*  895 */           setBlockState(worldIn, Blocks.oak_stairs.getStateFromMeta(i2), j2, 12 - j2, j1, structureBoundingBoxIn);
/*      */         }
/*      */       } 
/*      */       
/*  899 */       setBlockState(worldIn, Blocks.log.getDefaultState(), 0, 2, 1, structureBoundingBoxIn);
/*  900 */       setBlockState(worldIn, Blocks.log.getDefaultState(), 0, 2, 4, structureBoundingBoxIn);
/*  901 */       setBlockState(worldIn, Blocks.glass_pane.getDefaultState(), 0, 2, 2, structureBoundingBoxIn);
/*  902 */       setBlockState(worldIn, Blocks.glass_pane.getDefaultState(), 0, 2, 3, structureBoundingBoxIn);
/*  903 */       setBlockState(worldIn, Blocks.log.getDefaultState(), 4, 2, 0, structureBoundingBoxIn);
/*  904 */       setBlockState(worldIn, Blocks.glass_pane.getDefaultState(), 5, 2, 0, structureBoundingBoxIn);
/*  905 */       setBlockState(worldIn, Blocks.log.getDefaultState(), 6, 2, 0, structureBoundingBoxIn);
/*  906 */       setBlockState(worldIn, Blocks.log.getDefaultState(), 8, 2, 1, structureBoundingBoxIn);
/*  907 */       setBlockState(worldIn, Blocks.glass_pane.getDefaultState(), 8, 2, 2, structureBoundingBoxIn);
/*  908 */       setBlockState(worldIn, Blocks.glass_pane.getDefaultState(), 8, 2, 3, structureBoundingBoxIn);
/*  909 */       setBlockState(worldIn, Blocks.log.getDefaultState(), 8, 2, 4, structureBoundingBoxIn);
/*  910 */       setBlockState(worldIn, Blocks.planks.getDefaultState(), 8, 2, 5, structureBoundingBoxIn);
/*  911 */       setBlockState(worldIn, Blocks.log.getDefaultState(), 8, 2, 6, structureBoundingBoxIn);
/*  912 */       setBlockState(worldIn, Blocks.glass_pane.getDefaultState(), 8, 2, 7, structureBoundingBoxIn);
/*  913 */       setBlockState(worldIn, Blocks.glass_pane.getDefaultState(), 8, 2, 8, structureBoundingBoxIn);
/*  914 */       setBlockState(worldIn, Blocks.log.getDefaultState(), 8, 2, 9, structureBoundingBoxIn);
/*  915 */       setBlockState(worldIn, Blocks.log.getDefaultState(), 2, 2, 6, structureBoundingBoxIn);
/*  916 */       setBlockState(worldIn, Blocks.glass_pane.getDefaultState(), 2, 2, 7, structureBoundingBoxIn);
/*  917 */       setBlockState(worldIn, Blocks.glass_pane.getDefaultState(), 2, 2, 8, structureBoundingBoxIn);
/*  918 */       setBlockState(worldIn, Blocks.log.getDefaultState(), 2, 2, 9, structureBoundingBoxIn);
/*  919 */       setBlockState(worldIn, Blocks.log.getDefaultState(), 4, 4, 10, structureBoundingBoxIn);
/*  920 */       setBlockState(worldIn, Blocks.glass_pane.getDefaultState(), 5, 4, 10, structureBoundingBoxIn);
/*  921 */       setBlockState(worldIn, Blocks.log.getDefaultState(), 6, 4, 10, structureBoundingBoxIn);
/*  922 */       setBlockState(worldIn, Blocks.planks.getDefaultState(), 5, 5, 10, structureBoundingBoxIn);
/*  923 */       setBlockState(worldIn, Blocks.air.getDefaultState(), 2, 1, 0, structureBoundingBoxIn);
/*  924 */       setBlockState(worldIn, Blocks.air.getDefaultState(), 2, 2, 0, structureBoundingBoxIn);
/*  925 */       setBlockState(worldIn, Blocks.torch.getDefaultState().withProperty((IProperty)BlockTorch.FACING, (Comparable)this.coordBaseMode), 2, 3, 1, structureBoundingBoxIn);
/*  926 */       placeDoorCurrentPosition(worldIn, structureBoundingBoxIn, randomIn, 2, 1, 0, EnumFacing.getHorizontal(getMetadataWithOffset(Blocks.oak_door, 1)));
/*  927 */       fillWithBlocks(worldIn, structureBoundingBoxIn, 1, 0, -1, 3, 2, -1, Blocks.air.getDefaultState(), Blocks.air.getDefaultState(), false);
/*      */       
/*  929 */       if (getBlockStateFromPos(worldIn, 2, 0, -1, structureBoundingBoxIn).getBlock().getMaterial() == Material.air && getBlockStateFromPos(worldIn, 2, -1, -1, structureBoundingBoxIn).getBlock().getMaterial() != Material.air) {
/*  930 */         setBlockState(worldIn, Blocks.stone_stairs.getStateFromMeta(getMetadataWithOffset(Blocks.stone_stairs, 3)), 2, 0, -1, structureBoundingBoxIn);
/*      */       }
/*      */       
/*  933 */       for (int k2 = 0; k2 < 5; k2++) {
/*  934 */         for (int i3 = 0; i3 < 9; i3++) {
/*  935 */           clearCurrentPositionBlocksUpwards(worldIn, i3, 7, k2, structureBoundingBoxIn);
/*  936 */           replaceAirAndLiquidDownwards(worldIn, Blocks.cobblestone.getDefaultState(), i3, -1, k2, structureBoundingBoxIn);
/*      */         } 
/*      */       } 
/*      */       
/*  940 */       for (int l2 = 5; l2 < 11; l2++) {
/*  941 */         for (int j3 = 2; j3 < 9; j3++) {
/*  942 */           clearCurrentPositionBlocksUpwards(worldIn, j3, 7, l2, structureBoundingBoxIn);
/*  943 */           replaceAirAndLiquidDownwards(worldIn, Blocks.cobblestone.getDefaultState(), j3, -1, l2, structureBoundingBoxIn);
/*      */         } 
/*      */       } 
/*      */       
/*  947 */       spawnVillagers(worldIn, structureBoundingBoxIn, 4, 1, 2, 2);
/*  948 */       return true;
/*      */     }
/*      */   }
/*      */   
/*      */   public static class House4Garden
/*      */     extends Village {
/*      */     private boolean isRoofAccessible;
/*      */     
/*      */     public House4Garden() {}
/*      */     
/*      */     public House4Garden(StructureVillagePieces.Start start, int p_i45566_2_, Random rand, StructureBoundingBox p_i45566_4_, EnumFacing facing) {
/*  959 */       super(start, p_i45566_2_);
/*  960 */       this.coordBaseMode = facing;
/*  961 */       this.boundingBox = p_i45566_4_;
/*  962 */       this.isRoofAccessible = rand.nextBoolean();
/*      */     }
/*      */     
/*      */     protected void writeStructureToNBT(NBTTagCompound tagCompound) {
/*  966 */       super.writeStructureToNBT(tagCompound);
/*  967 */       tagCompound.setBoolean("Terrace", this.isRoofAccessible);
/*      */     }
/*      */     
/*      */     protected void readStructureFromNBT(NBTTagCompound tagCompound) {
/*  971 */       super.readStructureFromNBT(tagCompound);
/*  972 */       this.isRoofAccessible = tagCompound.getBoolean("Terrace");
/*      */     }
/*      */     
/*      */     public static House4Garden func_175858_a(StructureVillagePieces.Start start, List<StructureComponent> p_175858_1_, Random rand, int p_175858_3_, int p_175858_4_, int p_175858_5_, EnumFacing facing, int p_175858_7_) {
/*  976 */       StructureBoundingBox structureboundingbox = StructureBoundingBox.getComponentToAddBoundingBox(p_175858_3_, p_175858_4_, p_175858_5_, 0, 0, 0, 5, 6, 5, facing);
/*  977 */       return (StructureComponent.findIntersecting(p_175858_1_, structureboundingbox) != null) ? null : new House4Garden(start, p_175858_7_, rand, structureboundingbox, facing);
/*      */     }
/*      */     
/*      */     public boolean addComponentParts(World worldIn, Random randomIn, StructureBoundingBox structureBoundingBoxIn) {
/*  981 */       if (this.field_143015_k < 0) {
/*  982 */         this.field_143015_k = getAverageGroundLevel(worldIn, structureBoundingBoxIn);
/*      */         
/*  984 */         if (this.field_143015_k < 0) {
/*  985 */           return true;
/*      */         }
/*      */         
/*  988 */         this.boundingBox.offset(0, this.field_143015_k - this.boundingBox.maxY + 6 - 1, 0);
/*      */       } 
/*      */       
/*  991 */       fillWithBlocks(worldIn, structureBoundingBoxIn, 0, 0, 0, 4, 0, 4, Blocks.cobblestone.getDefaultState(), Blocks.cobblestone.getDefaultState(), false);
/*  992 */       fillWithBlocks(worldIn, structureBoundingBoxIn, 0, 4, 0, 4, 4, 4, Blocks.log.getDefaultState(), Blocks.log.getDefaultState(), false);
/*  993 */       fillWithBlocks(worldIn, structureBoundingBoxIn, 1, 4, 1, 3, 4, 3, Blocks.planks.getDefaultState(), Blocks.planks.getDefaultState(), false);
/*  994 */       setBlockState(worldIn, Blocks.cobblestone.getDefaultState(), 0, 1, 0, structureBoundingBoxIn);
/*  995 */       setBlockState(worldIn, Blocks.cobblestone.getDefaultState(), 0, 2, 0, structureBoundingBoxIn);
/*  996 */       setBlockState(worldIn, Blocks.cobblestone.getDefaultState(), 0, 3, 0, structureBoundingBoxIn);
/*  997 */       setBlockState(worldIn, Blocks.cobblestone.getDefaultState(), 4, 1, 0, structureBoundingBoxIn);
/*  998 */       setBlockState(worldIn, Blocks.cobblestone.getDefaultState(), 4, 2, 0, structureBoundingBoxIn);
/*  999 */       setBlockState(worldIn, Blocks.cobblestone.getDefaultState(), 4, 3, 0, structureBoundingBoxIn);
/* 1000 */       setBlockState(worldIn, Blocks.cobblestone.getDefaultState(), 0, 1, 4, structureBoundingBoxIn);
/* 1001 */       setBlockState(worldIn, Blocks.cobblestone.getDefaultState(), 0, 2, 4, structureBoundingBoxIn);
/* 1002 */       setBlockState(worldIn, Blocks.cobblestone.getDefaultState(), 0, 3, 4, structureBoundingBoxIn);
/* 1003 */       setBlockState(worldIn, Blocks.cobblestone.getDefaultState(), 4, 1, 4, structureBoundingBoxIn);
/* 1004 */       setBlockState(worldIn, Blocks.cobblestone.getDefaultState(), 4, 2, 4, structureBoundingBoxIn);
/* 1005 */       setBlockState(worldIn, Blocks.cobblestone.getDefaultState(), 4, 3, 4, structureBoundingBoxIn);
/* 1006 */       fillWithBlocks(worldIn, structureBoundingBoxIn, 0, 1, 1, 0, 3, 3, Blocks.planks.getDefaultState(), Blocks.planks.getDefaultState(), false);
/* 1007 */       fillWithBlocks(worldIn, structureBoundingBoxIn, 4, 1, 1, 4, 3, 3, Blocks.planks.getDefaultState(), Blocks.planks.getDefaultState(), false);
/* 1008 */       fillWithBlocks(worldIn, structureBoundingBoxIn, 1, 1, 4, 3, 3, 4, Blocks.planks.getDefaultState(), Blocks.planks.getDefaultState(), false);
/* 1009 */       setBlockState(worldIn, Blocks.glass_pane.getDefaultState(), 0, 2, 2, structureBoundingBoxIn);
/* 1010 */       setBlockState(worldIn, Blocks.glass_pane.getDefaultState(), 2, 2, 4, structureBoundingBoxIn);
/* 1011 */       setBlockState(worldIn, Blocks.glass_pane.getDefaultState(), 4, 2, 2, structureBoundingBoxIn);
/* 1012 */       setBlockState(worldIn, Blocks.planks.getDefaultState(), 1, 1, 0, structureBoundingBoxIn);
/* 1013 */       setBlockState(worldIn, Blocks.planks.getDefaultState(), 1, 2, 0, structureBoundingBoxIn);
/* 1014 */       setBlockState(worldIn, Blocks.planks.getDefaultState(), 1, 3, 0, structureBoundingBoxIn);
/* 1015 */       setBlockState(worldIn, Blocks.planks.getDefaultState(), 2, 3, 0, structureBoundingBoxIn);
/* 1016 */       setBlockState(worldIn, Blocks.planks.getDefaultState(), 3, 3, 0, structureBoundingBoxIn);
/* 1017 */       setBlockState(worldIn, Blocks.planks.getDefaultState(), 3, 2, 0, structureBoundingBoxIn);
/* 1018 */       setBlockState(worldIn, Blocks.planks.getDefaultState(), 3, 1, 0, structureBoundingBoxIn);
/*      */       
/* 1020 */       if (getBlockStateFromPos(worldIn, 2, 0, -1, structureBoundingBoxIn).getBlock().getMaterial() == Material.air && getBlockStateFromPos(worldIn, 2, -1, -1, structureBoundingBoxIn).getBlock().getMaterial() != Material.air) {
/* 1021 */         setBlockState(worldIn, Blocks.stone_stairs.getStateFromMeta(getMetadataWithOffset(Blocks.stone_stairs, 3)), 2, 0, -1, structureBoundingBoxIn);
/*      */       }
/*      */       
/* 1024 */       fillWithBlocks(worldIn, structureBoundingBoxIn, 1, 1, 1, 3, 3, 3, Blocks.air.getDefaultState(), Blocks.air.getDefaultState(), false);
/*      */       
/* 1026 */       if (this.isRoofAccessible) {
/* 1027 */         setBlockState(worldIn, Blocks.oak_fence.getDefaultState(), 0, 5, 0, structureBoundingBoxIn);
/* 1028 */         setBlockState(worldIn, Blocks.oak_fence.getDefaultState(), 1, 5, 0, structureBoundingBoxIn);
/* 1029 */         setBlockState(worldIn, Blocks.oak_fence.getDefaultState(), 2, 5, 0, structureBoundingBoxIn);
/* 1030 */         setBlockState(worldIn, Blocks.oak_fence.getDefaultState(), 3, 5, 0, structureBoundingBoxIn);
/* 1031 */         setBlockState(worldIn, Blocks.oak_fence.getDefaultState(), 4, 5, 0, structureBoundingBoxIn);
/* 1032 */         setBlockState(worldIn, Blocks.oak_fence.getDefaultState(), 0, 5, 4, structureBoundingBoxIn);
/* 1033 */         setBlockState(worldIn, Blocks.oak_fence.getDefaultState(), 1, 5, 4, structureBoundingBoxIn);
/* 1034 */         setBlockState(worldIn, Blocks.oak_fence.getDefaultState(), 2, 5, 4, structureBoundingBoxIn);
/* 1035 */         setBlockState(worldIn, Blocks.oak_fence.getDefaultState(), 3, 5, 4, structureBoundingBoxIn);
/* 1036 */         setBlockState(worldIn, Blocks.oak_fence.getDefaultState(), 4, 5, 4, structureBoundingBoxIn);
/* 1037 */         setBlockState(worldIn, Blocks.oak_fence.getDefaultState(), 4, 5, 1, structureBoundingBoxIn);
/* 1038 */         setBlockState(worldIn, Blocks.oak_fence.getDefaultState(), 4, 5, 2, structureBoundingBoxIn);
/* 1039 */         setBlockState(worldIn, Blocks.oak_fence.getDefaultState(), 4, 5, 3, structureBoundingBoxIn);
/* 1040 */         setBlockState(worldIn, Blocks.oak_fence.getDefaultState(), 0, 5, 1, structureBoundingBoxIn);
/* 1041 */         setBlockState(worldIn, Blocks.oak_fence.getDefaultState(), 0, 5, 2, structureBoundingBoxIn);
/* 1042 */         setBlockState(worldIn, Blocks.oak_fence.getDefaultState(), 0, 5, 3, structureBoundingBoxIn);
/*      */       } 
/*      */       
/* 1045 */       if (this.isRoofAccessible) {
/* 1046 */         int i = getMetadataWithOffset(Blocks.ladder, 3);
/* 1047 */         setBlockState(worldIn, Blocks.ladder.getStateFromMeta(i), 3, 1, 3, structureBoundingBoxIn);
/* 1048 */         setBlockState(worldIn, Blocks.ladder.getStateFromMeta(i), 3, 2, 3, structureBoundingBoxIn);
/* 1049 */         setBlockState(worldIn, Blocks.ladder.getStateFromMeta(i), 3, 3, 3, structureBoundingBoxIn);
/* 1050 */         setBlockState(worldIn, Blocks.ladder.getStateFromMeta(i), 3, 4, 3, structureBoundingBoxIn);
/*      */       } 
/*      */       
/* 1053 */       setBlockState(worldIn, Blocks.torch.getDefaultState().withProperty((IProperty)BlockTorch.FACING, (Comparable)this.coordBaseMode), 2, 3, 1, structureBoundingBoxIn);
/*      */       
/* 1055 */       for (int k = 0; k < 5; k++) {
/* 1056 */         for (int j = 0; j < 5; j++) {
/* 1057 */           clearCurrentPositionBlocksUpwards(worldIn, j, 6, k, structureBoundingBoxIn);
/* 1058 */           replaceAirAndLiquidDownwards(worldIn, Blocks.cobblestone.getDefaultState(), j, -1, k, structureBoundingBoxIn);
/*      */         } 
/*      */       } 
/*      */       
/* 1062 */       spawnVillagers(worldIn, structureBoundingBoxIn, 1, 1, 2, 1);
/* 1063 */       return true;
/*      */     }
/*      */   }
/*      */   
/*      */   public static class Path
/*      */     extends Road {
/*      */     private int length;
/*      */     
/*      */     public Path() {}
/*      */     
/*      */     public Path(StructureVillagePieces.Start start, int p_i45562_2_, Random rand, StructureBoundingBox p_i45562_4_, EnumFacing facing) {
/* 1074 */       super(start, p_i45562_2_);
/* 1075 */       this.coordBaseMode = facing;
/* 1076 */       this.boundingBox = p_i45562_4_;
/* 1077 */       this.length = Math.max(p_i45562_4_.getXSize(), p_i45562_4_.getZSize());
/*      */     }
/*      */     
/*      */     protected void writeStructureToNBT(NBTTagCompound tagCompound) {
/* 1081 */       super.writeStructureToNBT(tagCompound);
/* 1082 */       tagCompound.setInteger("Length", this.length);
/*      */     }
/*      */     
/*      */     protected void readStructureFromNBT(NBTTagCompound tagCompound) {
/* 1086 */       super.readStructureFromNBT(tagCompound);
/* 1087 */       this.length = tagCompound.getInteger("Length");
/*      */     }
/*      */     
/*      */     public void buildComponent(StructureComponent componentIn, List<StructureComponent> listIn, Random rand) {
/* 1091 */       boolean flag = false;
/*      */       
/* 1093 */       for (int i = rand.nextInt(5); i < this.length - 8; i += 2 + rand.nextInt(5)) {
/* 1094 */         StructureComponent structurecomponent = getNextComponentNN((StructureVillagePieces.Start)componentIn, listIn, rand, 0, i);
/*      */         
/* 1096 */         if (structurecomponent != null) {
/* 1097 */           i += Math.max(structurecomponent.boundingBox.getXSize(), structurecomponent.boundingBox.getZSize());
/* 1098 */           flag = true;
/*      */         } 
/*      */       } 
/*      */       
/* 1102 */       for (int j = rand.nextInt(5); j < this.length - 8; j += 2 + rand.nextInt(5)) {
/* 1103 */         StructureComponent structurecomponent1 = getNextComponentPP((StructureVillagePieces.Start)componentIn, listIn, rand, 0, j);
/*      */         
/* 1105 */         if (structurecomponent1 != null) {
/* 1106 */           j += Math.max(structurecomponent1.boundingBox.getXSize(), structurecomponent1.boundingBox.getZSize());
/* 1107 */           flag = true;
/*      */         } 
/*      */       } 
/*      */       
/* 1111 */       if (flag && rand.nextInt(3) > 0 && this.coordBaseMode != null) {
/* 1112 */         switch (this.coordBaseMode) {
/*      */           case NORTH:
/* 1114 */             StructureVillagePieces.func_176069_e((StructureVillagePieces.Start)componentIn, listIn, rand, this.boundingBox.minX - 1, this.boundingBox.minY, this.boundingBox.minZ, EnumFacing.WEST, getComponentType());
/*      */             break;
/*      */           
/*      */           case SOUTH:
/* 1118 */             StructureVillagePieces.func_176069_e((StructureVillagePieces.Start)componentIn, listIn, rand, this.boundingBox.minX - 1, this.boundingBox.minY, this.boundingBox.maxZ - 2, EnumFacing.WEST, getComponentType());
/*      */             break;
/*      */           
/*      */           case WEST:
/* 1122 */             StructureVillagePieces.func_176069_e((StructureVillagePieces.Start)componentIn, listIn, rand, this.boundingBox.minX, this.boundingBox.minY, this.boundingBox.minZ - 1, EnumFacing.NORTH, getComponentType());
/*      */             break;
/*      */           
/*      */           case EAST:
/* 1126 */             StructureVillagePieces.func_176069_e((StructureVillagePieces.Start)componentIn, listIn, rand, this.boundingBox.maxX - 2, this.boundingBox.minY, this.boundingBox.minZ - 1, EnumFacing.NORTH, getComponentType());
/*      */             break;
/*      */         } 
/*      */       }
/* 1130 */       if (flag && rand.nextInt(3) > 0 && this.coordBaseMode != null)
/* 1131 */         switch (this.coordBaseMode) {
/*      */           case NORTH:
/* 1133 */             StructureVillagePieces.func_176069_e((StructureVillagePieces.Start)componentIn, listIn, rand, this.boundingBox.maxX + 1, this.boundingBox.minY, this.boundingBox.minZ, EnumFacing.EAST, getComponentType());
/*      */             break;
/*      */           
/*      */           case SOUTH:
/* 1137 */             StructureVillagePieces.func_176069_e((StructureVillagePieces.Start)componentIn, listIn, rand, this.boundingBox.maxX + 1, this.boundingBox.minY, this.boundingBox.maxZ - 2, EnumFacing.EAST, getComponentType());
/*      */             break;
/*      */           
/*      */           case WEST:
/* 1141 */             StructureVillagePieces.func_176069_e((StructureVillagePieces.Start)componentIn, listIn, rand, this.boundingBox.minX, this.boundingBox.minY, this.boundingBox.maxZ + 1, EnumFacing.SOUTH, getComponentType());
/*      */             break;
/*      */           
/*      */           case EAST:
/* 1145 */             StructureVillagePieces.func_176069_e((StructureVillagePieces.Start)componentIn, listIn, rand, this.boundingBox.maxX - 2, this.boundingBox.minY, this.boundingBox.maxZ + 1, EnumFacing.SOUTH, getComponentType());
/*      */             break;
/*      */         }  
/*      */     }
/*      */     
/*      */     public static StructureBoundingBox func_175848_a(StructureVillagePieces.Start start, List<StructureComponent> p_175848_1_, Random rand, int p_175848_3_, int p_175848_4_, int p_175848_5_, EnumFacing facing) {
/* 1151 */       for (int i = 7 * MathHelper.getRandomIntegerInRange(rand, 3, 5); i >= 7; i -= 7) {
/* 1152 */         StructureBoundingBox structureboundingbox = StructureBoundingBox.getComponentToAddBoundingBox(p_175848_3_, p_175848_4_, p_175848_5_, 0, 0, 0, 3, 3, i, facing);
/*      */         
/* 1154 */         if (StructureComponent.findIntersecting(p_175848_1_, structureboundingbox) == null) {
/* 1155 */           return structureboundingbox;
/*      */         }
/*      */       } 
/*      */       
/* 1159 */       return null;
/*      */     }
/*      */     
/*      */     public boolean addComponentParts(World worldIn, Random randomIn, StructureBoundingBox structureBoundingBoxIn) {
/* 1163 */       IBlockState iblockstate = func_175847_a(Blocks.gravel.getDefaultState());
/* 1164 */       IBlockState iblockstate1 = func_175847_a(Blocks.cobblestone.getDefaultState());
/*      */       
/* 1166 */       for (int i = this.boundingBox.minX; i <= this.boundingBox.maxX; i++) {
/* 1167 */         for (int j = this.boundingBox.minZ; j <= this.boundingBox.maxZ; j++) {
/* 1168 */           BlockPos blockpos = new BlockPos(i, 64, j);
/*      */           
/* 1170 */           if (structureBoundingBoxIn.isVecInside((Vec3i)blockpos)) {
/* 1171 */             blockpos = worldIn.getTopSolidOrLiquidBlock(blockpos).down();
/* 1172 */             worldIn.setBlockState(blockpos, iblockstate, 2);
/* 1173 */             worldIn.setBlockState(blockpos.down(), iblockstate1, 2);
/*      */           } 
/*      */         } 
/*      */       } 
/*      */       
/* 1178 */       return true;
/*      */     }
/*      */   }
/*      */   
/*      */   public static class PieceWeight {
/*      */     public Class<? extends StructureVillagePieces.Village> villagePieceClass;
/*      */     public final int villagePieceWeight;
/*      */     public int villagePiecesSpawned;
/*      */     public int villagePiecesLimit;
/*      */     
/*      */     public PieceWeight(Class<? extends StructureVillagePieces.Village> p_i2098_1_, int p_i2098_2_, int p_i2098_3_) {
/* 1189 */       this.villagePieceClass = p_i2098_1_;
/* 1190 */       this.villagePieceWeight = p_i2098_2_;
/* 1191 */       this.villagePiecesLimit = p_i2098_3_;
/*      */     }
/*      */     
/*      */     public boolean canSpawnMoreVillagePiecesOfType(int p_75085_1_) {
/* 1195 */       return !(this.villagePiecesLimit != 0 && this.villagePiecesSpawned >= this.villagePiecesLimit);
/*      */     }
/*      */     
/*      */     public boolean canSpawnMoreVillagePieces() {
/* 1199 */       return !(this.villagePiecesLimit != 0 && this.villagePiecesSpawned >= this.villagePiecesLimit);
/*      */     }
/*      */   }
/*      */   
/*      */   public static abstract class Road
/*      */     extends Village {
/*      */     public Road() {}
/*      */     
/*      */     protected Road(StructureVillagePieces.Start start, int type) {
/* 1208 */       super(start, type);
/*      */     }
/*      */   }
/*      */   
/*      */   public static class Start extends Well {
/*      */     public WorldChunkManager worldChunkMngr;
/*      */     public boolean inDesert;
/*      */     public int terrainType;
/*      */     public StructureVillagePieces.PieceWeight structVillagePieceWeight;
/*      */     public List<StructureVillagePieces.PieceWeight> structureVillageWeightedPieceList;
/* 1218 */     public List<StructureComponent> field_74932_i = Lists.newArrayList();
/* 1219 */     public List<StructureComponent> field_74930_j = Lists.newArrayList();
/*      */ 
/*      */     
/*      */     public Start() {}
/*      */     
/*      */     public Start(WorldChunkManager chunkManagerIn, int p_i2104_2_, Random rand, int p_i2104_4_, int p_i2104_5_, List<StructureVillagePieces.PieceWeight> p_i2104_6_, int p_i2104_7_) {
/* 1225 */       super((Start)null, 0, rand, p_i2104_4_, p_i2104_5_);
/* 1226 */       this.worldChunkMngr = chunkManagerIn;
/* 1227 */       this.structureVillageWeightedPieceList = p_i2104_6_;
/* 1228 */       this.terrainType = p_i2104_7_;
/* 1229 */       BiomeGenBase biomegenbase = chunkManagerIn.getBiomeGenerator(new BlockPos(p_i2104_4_, 0, p_i2104_5_), BiomeGenBase.field_180279_ad);
/* 1230 */       this.inDesert = !(biomegenbase != BiomeGenBase.desert && biomegenbase != BiomeGenBase.desertHills);
/* 1231 */       func_175846_a(this.inDesert);
/*      */     }
/*      */     
/*      */     public WorldChunkManager getWorldChunkManager() {
/* 1235 */       return this.worldChunkMngr;
/*      */     }
/*      */   }
/*      */   
/*      */   public static class Torch
/*      */     extends Village {
/*      */     public Torch() {}
/*      */     
/*      */     public Torch(StructureVillagePieces.Start start, int p_i45568_2_, Random rand, StructureBoundingBox p_i45568_4_, EnumFacing facing) {
/* 1244 */       super(start, p_i45568_2_);
/* 1245 */       this.coordBaseMode = facing;
/* 1246 */       this.boundingBox = p_i45568_4_;
/*      */     }
/*      */     
/*      */     public static StructureBoundingBox func_175856_a(StructureVillagePieces.Start start, List<StructureComponent> p_175856_1_, Random rand, int p_175856_3_, int p_175856_4_, int p_175856_5_, EnumFacing facing) {
/* 1250 */       StructureBoundingBox structureboundingbox = StructureBoundingBox.getComponentToAddBoundingBox(p_175856_3_, p_175856_4_, p_175856_5_, 0, 0, 0, 3, 4, 2, facing);
/* 1251 */       return (StructureComponent.findIntersecting(p_175856_1_, structureboundingbox) != null) ? null : structureboundingbox;
/*      */     }
/*      */     
/*      */     public boolean addComponentParts(World worldIn, Random randomIn, StructureBoundingBox structureBoundingBoxIn) {
/* 1255 */       if (this.field_143015_k < 0) {
/* 1256 */         this.field_143015_k = getAverageGroundLevel(worldIn, structureBoundingBoxIn);
/*      */         
/* 1258 */         if (this.field_143015_k < 0) {
/* 1259 */           return true;
/*      */         }
/*      */         
/* 1262 */         this.boundingBox.offset(0, this.field_143015_k - this.boundingBox.maxY + 4 - 1, 0);
/*      */       } 
/*      */       
/* 1265 */       fillWithBlocks(worldIn, structureBoundingBoxIn, 0, 0, 0, 2, 3, 1, Blocks.air.getDefaultState(), Blocks.air.getDefaultState(), false);
/* 1266 */       setBlockState(worldIn, Blocks.oak_fence.getDefaultState(), 1, 0, 0, structureBoundingBoxIn);
/* 1267 */       setBlockState(worldIn, Blocks.oak_fence.getDefaultState(), 1, 1, 0, structureBoundingBoxIn);
/* 1268 */       setBlockState(worldIn, Blocks.oak_fence.getDefaultState(), 1, 2, 0, structureBoundingBoxIn);
/* 1269 */       setBlockState(worldIn, Blocks.wool.getStateFromMeta(EnumDyeColor.WHITE.getDyeDamage()), 1, 3, 0, structureBoundingBoxIn);
/* 1270 */       boolean flag = !(this.coordBaseMode != EnumFacing.EAST && this.coordBaseMode != EnumFacing.NORTH);
/* 1271 */       setBlockState(worldIn, Blocks.torch.getDefaultState().withProperty((IProperty)BlockTorch.FACING, (Comparable)this.coordBaseMode.rotateY()), flag ? 2 : 0, 3, 0, structureBoundingBoxIn);
/* 1272 */       setBlockState(worldIn, Blocks.torch.getDefaultState().withProperty((IProperty)BlockTorch.FACING, (Comparable)this.coordBaseMode), 1, 3, 1, structureBoundingBoxIn);
/* 1273 */       setBlockState(worldIn, Blocks.torch.getDefaultState().withProperty((IProperty)BlockTorch.FACING, (Comparable)this.coordBaseMode.rotateYCCW()), flag ? 0 : 2, 3, 0, structureBoundingBoxIn);
/* 1274 */       setBlockState(worldIn, Blocks.torch.getDefaultState().withProperty((IProperty)BlockTorch.FACING, (Comparable)this.coordBaseMode.getOpposite()), 1, 3, -1, structureBoundingBoxIn);
/* 1275 */       return true;
/*      */     }
/*      */   }
/*      */   
/*      */   static abstract class Village extends StructureComponent {
/* 1280 */     protected int field_143015_k = -1;
/*      */     
/*      */     private int villagersSpawned;
/*      */     
/*      */     private boolean isDesertVillage;
/*      */ 
/*      */     
/*      */     protected Village(StructureVillagePieces.Start start, int type) {
/* 1288 */       super(type);
/*      */       
/* 1290 */       if (start != null) {
/* 1291 */         this.isDesertVillage = start.inDesert;
/*      */       }
/*      */     }
/*      */     
/*      */     protected void writeStructureToNBT(NBTTagCompound tagCompound) {
/* 1296 */       tagCompound.setInteger("HPos", this.field_143015_k);
/* 1297 */       tagCompound.setInteger("VCount", this.villagersSpawned);
/* 1298 */       tagCompound.setBoolean("Desert", this.isDesertVillage);
/*      */     }
/*      */     
/*      */     protected void readStructureFromNBT(NBTTagCompound tagCompound) {
/* 1302 */       this.field_143015_k = tagCompound.getInteger("HPos");
/* 1303 */       this.villagersSpawned = tagCompound.getInteger("VCount");
/* 1304 */       this.isDesertVillage = tagCompound.getBoolean("Desert");
/*      */     }
/*      */     
/*      */     protected StructureComponent getNextComponentNN(StructureVillagePieces.Start start, List<StructureComponent> p_74891_2_, Random rand, int p_74891_4_, int p_74891_5_) {
/* 1308 */       if (this.coordBaseMode != null) {
/* 1309 */         switch (this.coordBaseMode) {
/*      */           case NORTH:
/* 1311 */             return StructureVillagePieces.func_176066_d(start, p_74891_2_, rand, this.boundingBox.minX - 1, this.boundingBox.minY + p_74891_4_, this.boundingBox.minZ + p_74891_5_, EnumFacing.WEST, getComponentType());
/*      */           
/*      */           case SOUTH:
/* 1314 */             return StructureVillagePieces.func_176066_d(start, p_74891_2_, rand, this.boundingBox.minX - 1, this.boundingBox.minY + p_74891_4_, this.boundingBox.minZ + p_74891_5_, EnumFacing.WEST, getComponentType());
/*      */           
/*      */           case WEST:
/* 1317 */             return StructureVillagePieces.func_176066_d(start, p_74891_2_, rand, this.boundingBox.minX + p_74891_5_, this.boundingBox.minY + p_74891_4_, this.boundingBox.minZ - 1, EnumFacing.NORTH, getComponentType());
/*      */           
/*      */           case EAST:
/* 1320 */             return StructureVillagePieces.func_176066_d(start, p_74891_2_, rand, this.boundingBox.minX + p_74891_5_, this.boundingBox.minY + p_74891_4_, this.boundingBox.minZ - 1, EnumFacing.NORTH, getComponentType());
/*      */         } 
/*      */       
/*      */       }
/* 1324 */       return null;
/*      */     }
/*      */     
/*      */     protected StructureComponent getNextComponentPP(StructureVillagePieces.Start start, List<StructureComponent> p_74894_2_, Random rand, int p_74894_4_, int p_74894_5_) {
/* 1328 */       if (this.coordBaseMode != null) {
/* 1329 */         switch (this.coordBaseMode) {
/*      */           case NORTH:
/* 1331 */             return StructureVillagePieces.func_176066_d(start, p_74894_2_, rand, this.boundingBox.maxX + 1, this.boundingBox.minY + p_74894_4_, this.boundingBox.minZ + p_74894_5_, EnumFacing.EAST, getComponentType());
/*      */           
/*      */           case SOUTH:
/* 1334 */             return StructureVillagePieces.func_176066_d(start, p_74894_2_, rand, this.boundingBox.maxX + 1, this.boundingBox.minY + p_74894_4_, this.boundingBox.minZ + p_74894_5_, EnumFacing.EAST, getComponentType());
/*      */           
/*      */           case WEST:
/* 1337 */             return StructureVillagePieces.func_176066_d(start, p_74894_2_, rand, this.boundingBox.minX + p_74894_5_, this.boundingBox.minY + p_74894_4_, this.boundingBox.maxZ + 1, EnumFacing.SOUTH, getComponentType());
/*      */           
/*      */           case EAST:
/* 1340 */             return StructureVillagePieces.func_176066_d(start, p_74894_2_, rand, this.boundingBox.minX + p_74894_5_, this.boundingBox.minY + p_74894_4_, this.boundingBox.maxZ + 1, EnumFacing.SOUTH, getComponentType());
/*      */         } 
/*      */       
/*      */       }
/* 1344 */       return null;
/*      */     }
/*      */     
/*      */     protected int getAverageGroundLevel(World worldIn, StructureBoundingBox p_74889_2_) {
/* 1348 */       int i = 0;
/* 1349 */       int j = 0;
/* 1350 */       BlockPos.MutableBlockPos blockpos$mutableblockpos = new BlockPos.MutableBlockPos();
/*      */       
/* 1352 */       for (int k = this.boundingBox.minZ; k <= this.boundingBox.maxZ; k++) {
/* 1353 */         for (int l = this.boundingBox.minX; l <= this.boundingBox.maxX; l++) {
/* 1354 */           blockpos$mutableblockpos.set(l, 64, k);
/*      */           
/* 1356 */           if (p_74889_2_.isVecInside((Vec3i)blockpos$mutableblockpos)) {
/* 1357 */             i += Math.max(worldIn.getTopSolidOrLiquidBlock((BlockPos)blockpos$mutableblockpos).getY(), worldIn.provider.getAverageGroundLevel());
/* 1358 */             j++;
/*      */           } 
/*      */         } 
/*      */       } 
/*      */       
/* 1363 */       if (j == 0) {
/* 1364 */         return -1;
/*      */       }
/* 1366 */       return i / j;
/*      */     }
/*      */ 
/*      */     
/*      */     protected static boolean canVillageGoDeeper(StructureBoundingBox p_74895_0_) {
/* 1371 */       return (p_74895_0_ != null && p_74895_0_.minY > 10);
/*      */     }
/*      */     
/*      */     protected void spawnVillagers(World worldIn, StructureBoundingBox p_74893_2_, int p_74893_3_, int p_74893_4_, int p_74893_5_, int p_74893_6_) {
/* 1375 */       if (this.villagersSpawned < p_74893_6_) {
/* 1376 */         int i = this.villagersSpawned;
/* 1377 */         int j = getXWithOffset(p_74893_3_ + i, p_74893_5_);
/* 1378 */         int k = getYWithOffset(p_74893_4_);
/* 1379 */         int l = getZWithOffset(p_74893_3_ + i, p_74893_5_);
/*      */         
/* 1381 */         for (; i < p_74893_6_ && p_74893_2_.isVecInside((Vec3i)new BlockPos(j, k, l)); i++) {
/*      */ 
/*      */ 
/*      */           
/* 1385 */           this.villagersSpawned++;
/* 1386 */           EntityVillager entityvillager = new EntityVillager(worldIn);
/* 1387 */           entityvillager.setLocationAndAngles(j + 0.5D, k, l + 0.5D, 0.0F, 0.0F);
/* 1388 */           entityvillager.onInitialSpawn(worldIn.getDifficultyForLocation(new BlockPos((Entity)entityvillager)), null);
/* 1389 */           entityvillager.setProfession(func_180779_c(i, entityvillager.getProfession()));
/* 1390 */           worldIn.spawnEntityInWorld((Entity)entityvillager);
/*      */         } 
/*      */       } 
/*      */     }
/*      */     
/*      */     protected int func_180779_c(int p_180779_1_, int p_180779_2_) {
/* 1396 */       return p_180779_2_;
/*      */     }
/*      */     
/*      */     protected IBlockState func_175847_a(IBlockState p_175847_1_) {
/* 1400 */       if (this.isDesertVillage) {
/* 1401 */         if (p_175847_1_.getBlock() == Blocks.log || p_175847_1_.getBlock() == Blocks.log2) {
/* 1402 */           return Blocks.sandstone.getDefaultState();
/*      */         }
/*      */         
/* 1405 */         if (p_175847_1_.getBlock() == Blocks.cobblestone) {
/* 1406 */           return Blocks.sandstone.getStateFromMeta(BlockSandStone.EnumType.DEFAULT.getMetadata());
/*      */         }
/*      */         
/* 1409 */         if (p_175847_1_.getBlock() == Blocks.planks) {
/* 1410 */           return Blocks.sandstone.getStateFromMeta(BlockSandStone.EnumType.SMOOTH.getMetadata());
/*      */         }
/*      */         
/* 1413 */         if (p_175847_1_.getBlock() == Blocks.oak_stairs) {
/* 1414 */           return Blocks.sandstone_stairs.getDefaultState().withProperty((IProperty)BlockStairs.FACING, p_175847_1_.getValue((IProperty)BlockStairs.FACING));
/*      */         }
/*      */         
/* 1417 */         if (p_175847_1_.getBlock() == Blocks.stone_stairs) {
/* 1418 */           return Blocks.sandstone_stairs.getDefaultState().withProperty((IProperty)BlockStairs.FACING, p_175847_1_.getValue((IProperty)BlockStairs.FACING));
/*      */         }
/*      */         
/* 1421 */         if (p_175847_1_.getBlock() == Blocks.gravel) {
/* 1422 */           return Blocks.sandstone.getDefaultState();
/*      */         }
/*      */       } 
/*      */       
/* 1426 */       return p_175847_1_;
/*      */     }
/*      */     
/*      */     protected void setBlockState(World worldIn, IBlockState blockstateIn, int x, int y, int z, StructureBoundingBox boundingboxIn) {
/* 1430 */       IBlockState iblockstate = func_175847_a(blockstateIn);
/* 1431 */       super.setBlockState(worldIn, iblockstate, x, y, z, boundingboxIn);
/*      */     }
/*      */     
/*      */     protected void fillWithBlocks(World worldIn, StructureBoundingBox boundingboxIn, int xMin, int yMin, int zMin, int xMax, int yMax, int zMax, IBlockState boundaryBlockState, IBlockState insideBlockState, boolean existingOnly) {
/* 1435 */       IBlockState iblockstate = func_175847_a(boundaryBlockState);
/* 1436 */       IBlockState iblockstate1 = func_175847_a(insideBlockState);
/* 1437 */       super.fillWithBlocks(worldIn, boundingboxIn, xMin, yMin, zMin, xMax, yMax, zMax, iblockstate, iblockstate1, existingOnly);
/*      */     }
/*      */     
/*      */     protected void replaceAirAndLiquidDownwards(World worldIn, IBlockState blockstateIn, int x, int y, int z, StructureBoundingBox boundingboxIn) {
/* 1441 */       IBlockState iblockstate = func_175847_a(blockstateIn);
/* 1442 */       super.replaceAirAndLiquidDownwards(worldIn, iblockstate, x, y, z, boundingboxIn);
/*      */     }
/*      */     
/*      */     protected void func_175846_a(boolean p_175846_1_) {
/* 1446 */       this.isDesertVillage = p_175846_1_;
/*      */     }
/*      */     
/*      */     public Village() {} }
/*      */   
/*      */   public static class Well extends Village {
/*      */     public Well() {}
/*      */     
/*      */     public Well(StructureVillagePieces.Start start, int p_i2109_2_, Random rand, int p_i2109_4_, int p_i2109_5_) {
/* 1455 */       super(start, p_i2109_2_);
/* 1456 */       this.coordBaseMode = EnumFacing.Plane.HORIZONTAL.random(rand);
/*      */       
/* 1458 */       switch (this.coordBaseMode) {
/*      */         case NORTH:
/*      */         case SOUTH:
/* 1461 */           this.boundingBox = new StructureBoundingBox(p_i2109_4_, 64, p_i2109_5_, p_i2109_4_ + 6 - 1, 78, p_i2109_5_ + 6 - 1);
/*      */           return;
/*      */       } 
/*      */       
/* 1465 */       this.boundingBox = new StructureBoundingBox(p_i2109_4_, 64, p_i2109_5_, p_i2109_4_ + 6 - 1, 78, p_i2109_5_ + 6 - 1);
/*      */     }
/*      */ 
/*      */     
/*      */     public void buildComponent(StructureComponent componentIn, List<StructureComponent> listIn, Random rand) {
/* 1470 */       StructureVillagePieces.func_176069_e((StructureVillagePieces.Start)componentIn, listIn, rand, this.boundingBox.minX - 1, this.boundingBox.maxY - 4, this.boundingBox.minZ + 1, EnumFacing.WEST, getComponentType());
/* 1471 */       StructureVillagePieces.func_176069_e((StructureVillagePieces.Start)componentIn, listIn, rand, this.boundingBox.maxX + 1, this.boundingBox.maxY - 4, this.boundingBox.minZ + 1, EnumFacing.EAST, getComponentType());
/* 1472 */       StructureVillagePieces.func_176069_e((StructureVillagePieces.Start)componentIn, listIn, rand, this.boundingBox.minX + 1, this.boundingBox.maxY - 4, this.boundingBox.minZ - 1, EnumFacing.NORTH, getComponentType());
/* 1473 */       StructureVillagePieces.func_176069_e((StructureVillagePieces.Start)componentIn, listIn, rand, this.boundingBox.minX + 1, this.boundingBox.maxY - 4, this.boundingBox.maxZ + 1, EnumFacing.SOUTH, getComponentType());
/*      */     }
/*      */     
/*      */     public boolean addComponentParts(World worldIn, Random randomIn, StructureBoundingBox structureBoundingBoxIn) {
/* 1477 */       if (this.field_143015_k < 0) {
/* 1478 */         this.field_143015_k = getAverageGroundLevel(worldIn, structureBoundingBoxIn);
/*      */         
/* 1480 */         if (this.field_143015_k < 0) {
/* 1481 */           return true;
/*      */         }
/*      */         
/* 1484 */         this.boundingBox.offset(0, this.field_143015_k - this.boundingBox.maxY + 3, 0);
/*      */       } 
/*      */       
/* 1487 */       fillWithBlocks(worldIn, structureBoundingBoxIn, 1, 0, 1, 4, 12, 4, Blocks.cobblestone.getDefaultState(), Blocks.flowing_water.getDefaultState(), false);
/* 1488 */       setBlockState(worldIn, Blocks.air.getDefaultState(), 2, 12, 2, structureBoundingBoxIn);
/* 1489 */       setBlockState(worldIn, Blocks.air.getDefaultState(), 3, 12, 2, structureBoundingBoxIn);
/* 1490 */       setBlockState(worldIn, Blocks.air.getDefaultState(), 2, 12, 3, structureBoundingBoxIn);
/* 1491 */       setBlockState(worldIn, Blocks.air.getDefaultState(), 3, 12, 3, structureBoundingBoxIn);
/* 1492 */       setBlockState(worldIn, Blocks.oak_fence.getDefaultState(), 1, 13, 1, structureBoundingBoxIn);
/* 1493 */       setBlockState(worldIn, Blocks.oak_fence.getDefaultState(), 1, 14, 1, structureBoundingBoxIn);
/* 1494 */       setBlockState(worldIn, Blocks.oak_fence.getDefaultState(), 4, 13, 1, structureBoundingBoxIn);
/* 1495 */       setBlockState(worldIn, Blocks.oak_fence.getDefaultState(), 4, 14, 1, structureBoundingBoxIn);
/* 1496 */       setBlockState(worldIn, Blocks.oak_fence.getDefaultState(), 1, 13, 4, structureBoundingBoxIn);
/* 1497 */       setBlockState(worldIn, Blocks.oak_fence.getDefaultState(), 1, 14, 4, structureBoundingBoxIn);
/* 1498 */       setBlockState(worldIn, Blocks.oak_fence.getDefaultState(), 4, 13, 4, structureBoundingBoxIn);
/* 1499 */       setBlockState(worldIn, Blocks.oak_fence.getDefaultState(), 4, 14, 4, structureBoundingBoxIn);
/* 1500 */       fillWithBlocks(worldIn, structureBoundingBoxIn, 1, 15, 1, 4, 15, 4, Blocks.cobblestone.getDefaultState(), Blocks.cobblestone.getDefaultState(), false);
/*      */       
/* 1502 */       for (int i = 0; i <= 5; i++) {
/* 1503 */         for (int j = 0; j <= 5; j++) {
/* 1504 */           if (j == 0 || j == 5 || i == 0 || i == 5) {
/* 1505 */             setBlockState(worldIn, Blocks.gravel.getDefaultState(), j, 11, i, structureBoundingBoxIn);
/* 1506 */             clearCurrentPositionBlocksUpwards(worldIn, j, 12, i, structureBoundingBoxIn);
/*      */           } 
/*      */         } 
/*      */       } 
/*      */       
/* 1511 */       return true;
/*      */     }
/*      */   }
/*      */   
/*      */   public static class WoodHut
/*      */     extends Village {
/*      */     private boolean isTallHouse;
/*      */     private int tablePosition;
/*      */     
/*      */     public WoodHut() {}
/*      */     
/*      */     public WoodHut(StructureVillagePieces.Start start, int p_i45565_2_, Random rand, StructureBoundingBox p_i45565_4_, EnumFacing facing) {
/* 1523 */       super(start, p_i45565_2_);
/* 1524 */       this.coordBaseMode = facing;
/* 1525 */       this.boundingBox = p_i45565_4_;
/* 1526 */       this.isTallHouse = rand.nextBoolean();
/* 1527 */       this.tablePosition = rand.nextInt(3);
/*      */     }
/*      */     
/*      */     protected void writeStructureToNBT(NBTTagCompound tagCompound) {
/* 1531 */       super.writeStructureToNBT(tagCompound);
/* 1532 */       tagCompound.setInteger("T", this.tablePosition);
/* 1533 */       tagCompound.setBoolean("C", this.isTallHouse);
/*      */     }
/*      */     
/*      */     protected void readStructureFromNBT(NBTTagCompound tagCompound) {
/* 1537 */       super.readStructureFromNBT(tagCompound);
/* 1538 */       this.tablePosition = tagCompound.getInteger("T");
/* 1539 */       this.isTallHouse = tagCompound.getBoolean("C");
/*      */     }
/*      */     
/*      */     public static WoodHut func_175853_a(StructureVillagePieces.Start start, List<StructureComponent> p_175853_1_, Random rand, int p_175853_3_, int p_175853_4_, int p_175853_5_, EnumFacing facing, int p_175853_7_) {
/* 1543 */       StructureBoundingBox structureboundingbox = StructureBoundingBox.getComponentToAddBoundingBox(p_175853_3_, p_175853_4_, p_175853_5_, 0, 0, 0, 4, 6, 5, facing);
/* 1544 */       return (canVillageGoDeeper(structureboundingbox) && StructureComponent.findIntersecting(p_175853_1_, structureboundingbox) == null) ? new WoodHut(start, p_175853_7_, rand, structureboundingbox, facing) : null;
/*      */     }
/*      */     
/*      */     public boolean addComponentParts(World worldIn, Random randomIn, StructureBoundingBox structureBoundingBoxIn) {
/* 1548 */       if (this.field_143015_k < 0) {
/* 1549 */         this.field_143015_k = getAverageGroundLevel(worldIn, structureBoundingBoxIn);
/*      */         
/* 1551 */         if (this.field_143015_k < 0) {
/* 1552 */           return true;
/*      */         }
/*      */         
/* 1555 */         this.boundingBox.offset(0, this.field_143015_k - this.boundingBox.maxY + 6 - 1, 0);
/*      */       } 
/*      */       
/* 1558 */       fillWithBlocks(worldIn, structureBoundingBoxIn, 1, 1, 1, 3, 5, 4, Blocks.air.getDefaultState(), Blocks.air.getDefaultState(), false);
/* 1559 */       fillWithBlocks(worldIn, structureBoundingBoxIn, 0, 0, 0, 3, 0, 4, Blocks.cobblestone.getDefaultState(), Blocks.cobblestone.getDefaultState(), false);
/* 1560 */       fillWithBlocks(worldIn, structureBoundingBoxIn, 1, 0, 1, 2, 0, 3, Blocks.dirt.getDefaultState(), Blocks.dirt.getDefaultState(), false);
/*      */       
/* 1562 */       if (this.isTallHouse) {
/* 1563 */         fillWithBlocks(worldIn, structureBoundingBoxIn, 1, 4, 1, 2, 4, 3, Blocks.log.getDefaultState(), Blocks.log.getDefaultState(), false);
/*      */       } else {
/* 1565 */         fillWithBlocks(worldIn, structureBoundingBoxIn, 1, 5, 1, 2, 5, 3, Blocks.log.getDefaultState(), Blocks.log.getDefaultState(), false);
/*      */       } 
/*      */       
/* 1568 */       setBlockState(worldIn, Blocks.log.getDefaultState(), 1, 4, 0, structureBoundingBoxIn);
/* 1569 */       setBlockState(worldIn, Blocks.log.getDefaultState(), 2, 4, 0, structureBoundingBoxIn);
/* 1570 */       setBlockState(worldIn, Blocks.log.getDefaultState(), 1, 4, 4, structureBoundingBoxIn);
/* 1571 */       setBlockState(worldIn, Blocks.log.getDefaultState(), 2, 4, 4, structureBoundingBoxIn);
/* 1572 */       setBlockState(worldIn, Blocks.log.getDefaultState(), 0, 4, 1, structureBoundingBoxIn);
/* 1573 */       setBlockState(worldIn, Blocks.log.getDefaultState(), 0, 4, 2, structureBoundingBoxIn);
/* 1574 */       setBlockState(worldIn, Blocks.log.getDefaultState(), 0, 4, 3, structureBoundingBoxIn);
/* 1575 */       setBlockState(worldIn, Blocks.log.getDefaultState(), 3, 4, 1, structureBoundingBoxIn);
/* 1576 */       setBlockState(worldIn, Blocks.log.getDefaultState(), 3, 4, 2, structureBoundingBoxIn);
/* 1577 */       setBlockState(worldIn, Blocks.log.getDefaultState(), 3, 4, 3, structureBoundingBoxIn);
/* 1578 */       fillWithBlocks(worldIn, structureBoundingBoxIn, 0, 1, 0, 0, 3, 0, Blocks.log.getDefaultState(), Blocks.log.getDefaultState(), false);
/* 1579 */       fillWithBlocks(worldIn, structureBoundingBoxIn, 3, 1, 0, 3, 3, 0, Blocks.log.getDefaultState(), Blocks.log.getDefaultState(), false);
/* 1580 */       fillWithBlocks(worldIn, structureBoundingBoxIn, 0, 1, 4, 0, 3, 4, Blocks.log.getDefaultState(), Blocks.log.getDefaultState(), false);
/* 1581 */       fillWithBlocks(worldIn, structureBoundingBoxIn, 3, 1, 4, 3, 3, 4, Blocks.log.getDefaultState(), Blocks.log.getDefaultState(), false);
/* 1582 */       fillWithBlocks(worldIn, structureBoundingBoxIn, 0, 1, 1, 0, 3, 3, Blocks.planks.getDefaultState(), Blocks.planks.getDefaultState(), false);
/* 1583 */       fillWithBlocks(worldIn, structureBoundingBoxIn, 3, 1, 1, 3, 3, 3, Blocks.planks.getDefaultState(), Blocks.planks.getDefaultState(), false);
/* 1584 */       fillWithBlocks(worldIn, structureBoundingBoxIn, 1, 1, 0, 2, 3, 0, Blocks.planks.getDefaultState(), Blocks.planks.getDefaultState(), false);
/* 1585 */       fillWithBlocks(worldIn, structureBoundingBoxIn, 1, 1, 4, 2, 3, 4, Blocks.planks.getDefaultState(), Blocks.planks.getDefaultState(), false);
/* 1586 */       setBlockState(worldIn, Blocks.glass_pane.getDefaultState(), 0, 2, 2, structureBoundingBoxIn);
/* 1587 */       setBlockState(worldIn, Blocks.glass_pane.getDefaultState(), 3, 2, 2, structureBoundingBoxIn);
/*      */       
/* 1589 */       if (this.tablePosition > 0) {
/* 1590 */         setBlockState(worldIn, Blocks.oak_fence.getDefaultState(), this.tablePosition, 1, 3, structureBoundingBoxIn);
/* 1591 */         setBlockState(worldIn, Blocks.wooden_pressure_plate.getDefaultState(), this.tablePosition, 2, 3, structureBoundingBoxIn);
/*      */       } 
/*      */       
/* 1594 */       setBlockState(worldIn, Blocks.air.getDefaultState(), 1, 1, 0, structureBoundingBoxIn);
/* 1595 */       setBlockState(worldIn, Blocks.air.getDefaultState(), 1, 2, 0, structureBoundingBoxIn);
/* 1596 */       placeDoorCurrentPosition(worldIn, structureBoundingBoxIn, randomIn, 1, 1, 0, EnumFacing.getHorizontal(getMetadataWithOffset(Blocks.oak_door, 1)));
/*      */       
/* 1598 */       if (getBlockStateFromPos(worldIn, 1, 0, -1, structureBoundingBoxIn).getBlock().getMaterial() == Material.air && getBlockStateFromPos(worldIn, 1, -1, -1, structureBoundingBoxIn).getBlock().getMaterial() != Material.air) {
/* 1599 */         setBlockState(worldIn, Blocks.stone_stairs.getStateFromMeta(getMetadataWithOffset(Blocks.stone_stairs, 3)), 1, 0, -1, structureBoundingBoxIn);
/*      */       }
/*      */       
/* 1602 */       for (int i = 0; i < 5; i++) {
/* 1603 */         for (int j = 0; j < 4; j++) {
/* 1604 */           clearCurrentPositionBlocksUpwards(worldIn, j, 6, i, structureBoundingBoxIn);
/* 1605 */           replaceAirAndLiquidDownwards(worldIn, Blocks.cobblestone.getDefaultState(), j, -1, i, structureBoundingBoxIn);
/*      */         } 
/*      */       } 
/*      */       
/* 1609 */       spawnVillagers(worldIn, structureBoundingBoxIn, 1, 1, 2, 1);
/* 1610 */       return true;
/*      */     }
/*      */   }
/*      */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\minecraft\world\gen\structure\StructureVillagePieces.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */