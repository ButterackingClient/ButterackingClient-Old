/*      */ package net.minecraft.world.gen.structure;
/*      */ 
/*      */ import com.google.common.collect.Lists;
/*      */ import java.util.List;
/*      */ import java.util.Random;
/*      */ import net.minecraft.block.BlockEndPortalFrame;
/*      */ import net.minecraft.block.BlockSilverfish;
/*      */ import net.minecraft.block.BlockStoneBrick;
/*      */ import net.minecraft.block.BlockStoneSlab;
/*      */ import net.minecraft.block.properties.IProperty;
/*      */ import net.minecraft.init.Blocks;
/*      */ import net.minecraft.init.Items;
/*      */ import net.minecraft.item.Item;
/*      */ import net.minecraft.nbt.NBTTagCompound;
/*      */ import net.minecraft.tileentity.TileEntity;
/*      */ import net.minecraft.tileentity.TileEntityMobSpawner;
/*      */ import net.minecraft.util.BlockPos;
/*      */ import net.minecraft.util.EnumFacing;
/*      */ import net.minecraft.util.Vec3i;
/*      */ import net.minecraft.util.WeightedRandomChestContent;
/*      */ import net.minecraft.world.World;
/*      */ 
/*      */ public class StructureStrongholdPieces {
/*   24 */   private static final PieceWeight[] pieceWeightArray = new PieceWeight[] { new PieceWeight((Class)Straight.class, 40, 0), new PieceWeight((Class)Prison.class, 5, 5), new PieceWeight((Class)LeftTurn.class, 20, 0), new PieceWeight((Class)RightTurn.class, 20, 0), new PieceWeight((Class)RoomCrossing.class, 10, 6), new PieceWeight((Class)StairsStraight.class, 5, 5), new PieceWeight((Class)Stairs.class, 5, 5), new PieceWeight((Class)Crossing.class, 5, 4), new PieceWeight((Class)ChestCorridor.class, 5, 4), new PieceWeight(Library.class, 10, 2) {
/*      */         public boolean canSpawnMoreStructuresOfType(int p_75189_1_) {
/*   26 */           return (super.canSpawnMoreStructuresOfType(p_75189_1_) && p_75189_1_ > 4); }
/*      */       }, 
/*   28 */       new PieceWeight(PortalRoom.class, 20, 1) {
/*      */         public boolean canSpawnMoreStructuresOfType(int p_75189_1_) {
/*   30 */           return (super.canSpawnMoreStructuresOfType(p_75189_1_) && p_75189_1_ > 5);
/*      */         }
/*      */       } };
/*      */   
/*      */   private static List<PieceWeight> structurePieceList;
/*      */   private static Class<? extends Stronghold> strongComponentType;
/*      */   static int totalWeight;
/*   37 */   private static final Stones strongholdStones = new Stones(null);
/*      */   
/*      */   public static void registerStrongholdPieces() {
/*   40 */     MapGenStructureIO.registerStructureComponent((Class)ChestCorridor.class, "SHCC");
/*   41 */     MapGenStructureIO.registerStructureComponent((Class)Corridor.class, "SHFC");
/*   42 */     MapGenStructureIO.registerStructureComponent((Class)Crossing.class, "SH5C");
/*   43 */     MapGenStructureIO.registerStructureComponent((Class)LeftTurn.class, "SHLT");
/*   44 */     MapGenStructureIO.registerStructureComponent((Class)Library.class, "SHLi");
/*   45 */     MapGenStructureIO.registerStructureComponent((Class)PortalRoom.class, "SHPR");
/*   46 */     MapGenStructureIO.registerStructureComponent((Class)Prison.class, "SHPH");
/*   47 */     MapGenStructureIO.registerStructureComponent((Class)RightTurn.class, "SHRT");
/*   48 */     MapGenStructureIO.registerStructureComponent((Class)RoomCrossing.class, "SHRC");
/*   49 */     MapGenStructureIO.registerStructureComponent((Class)Stairs.class, "SHSD");
/*   50 */     MapGenStructureIO.registerStructureComponent((Class)Stairs2.class, "SHStart");
/*   51 */     MapGenStructureIO.registerStructureComponent((Class)Straight.class, "SHS");
/*   52 */     MapGenStructureIO.registerStructureComponent((Class)StairsStraight.class, "SHSSD");
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void prepareStructurePieces() {
/*   59 */     structurePieceList = Lists.newArrayList(); byte b; int i;
/*      */     PieceWeight[] arrayOfPieceWeight;
/*   61 */     for (i = (arrayOfPieceWeight = pieceWeightArray).length, b = 0; b < i; ) { PieceWeight structurestrongholdpieces$pieceweight = arrayOfPieceWeight[b];
/*   62 */       structurestrongholdpieces$pieceweight.instancesSpawned = 0;
/*   63 */       structurePieceList.add(structurestrongholdpieces$pieceweight);
/*      */       b++; }
/*      */     
/*   66 */     strongComponentType = null;
/*      */   }
/*      */   
/*      */   private static boolean canAddStructurePieces() {
/*   70 */     boolean flag = false;
/*   71 */     totalWeight = 0;
/*      */     
/*   73 */     for (PieceWeight structurestrongholdpieces$pieceweight : structurePieceList) {
/*   74 */       if (structurestrongholdpieces$pieceweight.instancesLimit > 0 && structurestrongholdpieces$pieceweight.instancesSpawned < structurestrongholdpieces$pieceweight.instancesLimit) {
/*   75 */         flag = true;
/*      */       }
/*      */       
/*   78 */       totalWeight += structurestrongholdpieces$pieceweight.pieceWeight;
/*      */     } 
/*      */     
/*   81 */     return flag;
/*      */   }
/*      */   
/*      */   private static Stronghold func_175954_a(Class<? extends Stronghold> p_175954_0_, List<StructureComponent> p_175954_1_, Random p_175954_2_, int p_175954_3_, int p_175954_4_, int p_175954_5_, EnumFacing p_175954_6_, int p_175954_7_) {
/*   85 */     Stronghold structurestrongholdpieces$stronghold = null;
/*      */     
/*   87 */     if (p_175954_0_ == Straight.class) {
/*   88 */       structurestrongholdpieces$stronghold = Straight.func_175862_a(p_175954_1_, p_175954_2_, p_175954_3_, p_175954_4_, p_175954_5_, p_175954_6_, p_175954_7_);
/*   89 */     } else if (p_175954_0_ == Prison.class) {
/*   90 */       structurestrongholdpieces$stronghold = Prison.func_175860_a(p_175954_1_, p_175954_2_, p_175954_3_, p_175954_4_, p_175954_5_, p_175954_6_, p_175954_7_);
/*   91 */     } else if (p_175954_0_ == LeftTurn.class) {
/*   92 */       structurestrongholdpieces$stronghold = LeftTurn.func_175867_a(p_175954_1_, p_175954_2_, p_175954_3_, p_175954_4_, p_175954_5_, p_175954_6_, p_175954_7_);
/*   93 */     } else if (p_175954_0_ == RightTurn.class) {
/*   94 */       structurestrongholdpieces$stronghold = RightTurn.func_175867_a(p_175954_1_, p_175954_2_, p_175954_3_, p_175954_4_, p_175954_5_, p_175954_6_, p_175954_7_);
/*   95 */     } else if (p_175954_0_ == RoomCrossing.class) {
/*   96 */       structurestrongholdpieces$stronghold = RoomCrossing.func_175859_a(p_175954_1_, p_175954_2_, p_175954_3_, p_175954_4_, p_175954_5_, p_175954_6_, p_175954_7_);
/*   97 */     } else if (p_175954_0_ == StairsStraight.class) {
/*   98 */       structurestrongholdpieces$stronghold = StairsStraight.func_175861_a(p_175954_1_, p_175954_2_, p_175954_3_, p_175954_4_, p_175954_5_, p_175954_6_, p_175954_7_);
/*   99 */     } else if (p_175954_0_ == Stairs.class) {
/*  100 */       structurestrongholdpieces$stronghold = Stairs.func_175863_a(p_175954_1_, p_175954_2_, p_175954_3_, p_175954_4_, p_175954_5_, p_175954_6_, p_175954_7_);
/*  101 */     } else if (p_175954_0_ == Crossing.class) {
/*  102 */       structurestrongholdpieces$stronghold = Crossing.func_175866_a(p_175954_1_, p_175954_2_, p_175954_3_, p_175954_4_, p_175954_5_, p_175954_6_, p_175954_7_);
/*  103 */     } else if (p_175954_0_ == ChestCorridor.class) {
/*  104 */       structurestrongholdpieces$stronghold = ChestCorridor.func_175868_a(p_175954_1_, p_175954_2_, p_175954_3_, p_175954_4_, p_175954_5_, p_175954_6_, p_175954_7_);
/*  105 */     } else if (p_175954_0_ == Library.class) {
/*  106 */       structurestrongholdpieces$stronghold = Library.func_175864_a(p_175954_1_, p_175954_2_, p_175954_3_, p_175954_4_, p_175954_5_, p_175954_6_, p_175954_7_);
/*  107 */     } else if (p_175954_0_ == PortalRoom.class) {
/*  108 */       structurestrongholdpieces$stronghold = PortalRoom.func_175865_a(p_175954_1_, p_175954_2_, p_175954_3_, p_175954_4_, p_175954_5_, p_175954_6_, p_175954_7_);
/*      */     } 
/*      */     
/*  111 */     return structurestrongholdpieces$stronghold;
/*      */   }
/*      */   
/*      */   private static Stronghold func_175955_b(Stairs2 p_175955_0_, List<StructureComponent> p_175955_1_, Random p_175955_2_, int p_175955_3_, int p_175955_4_, int p_175955_5_, EnumFacing p_175955_6_, int p_175955_7_) {
/*  115 */     if (!canAddStructurePieces()) {
/*  116 */       return null;
/*      */     }
/*  118 */     if (strongComponentType != null) {
/*  119 */       Stronghold structurestrongholdpieces$stronghold = func_175954_a(strongComponentType, p_175955_1_, p_175955_2_, p_175955_3_, p_175955_4_, p_175955_5_, p_175955_6_, p_175955_7_);
/*  120 */       strongComponentType = null;
/*      */       
/*  122 */       if (structurestrongholdpieces$stronghold != null) {
/*  123 */         return structurestrongholdpieces$stronghold;
/*      */       }
/*      */     } 
/*      */     
/*  127 */     int j = 0;
/*      */     
/*  129 */     while (j < 5) {
/*  130 */       j++;
/*  131 */       int i = p_175955_2_.nextInt(totalWeight);
/*      */       
/*  133 */       for (PieceWeight structurestrongholdpieces$pieceweight : structurePieceList) {
/*  134 */         i -= structurestrongholdpieces$pieceweight.pieceWeight;
/*      */         
/*  136 */         if (i < 0) {
/*  137 */           if (!structurestrongholdpieces$pieceweight.canSpawnMoreStructuresOfType(p_175955_7_) || structurestrongholdpieces$pieceweight == p_175955_0_.strongholdPieceWeight) {
/*      */             break;
/*      */           }
/*      */           
/*  141 */           Stronghold structurestrongholdpieces$stronghold1 = func_175954_a(structurestrongholdpieces$pieceweight.pieceClass, p_175955_1_, p_175955_2_, p_175955_3_, p_175955_4_, p_175955_5_, p_175955_6_, p_175955_7_);
/*      */           
/*  143 */           if (structurestrongholdpieces$stronghold1 != null) {
/*  144 */             structurestrongholdpieces$pieceweight.instancesSpawned++;
/*  145 */             p_175955_0_.strongholdPieceWeight = structurestrongholdpieces$pieceweight;
/*      */             
/*  147 */             if (!structurestrongholdpieces$pieceweight.canSpawnMoreStructures()) {
/*  148 */               structurePieceList.remove(structurestrongholdpieces$pieceweight);
/*      */             }
/*      */             
/*  151 */             return structurestrongholdpieces$stronghold1;
/*      */           } 
/*      */         } 
/*      */       } 
/*      */     } 
/*      */     
/*  157 */     StructureBoundingBox structureboundingbox = Corridor.func_175869_a(p_175955_1_, p_175955_2_, p_175955_3_, p_175955_4_, p_175955_5_, p_175955_6_);
/*      */     
/*  159 */     if (structureboundingbox != null && structureboundingbox.minY > 1) {
/*  160 */       return new Corridor(p_175955_7_, p_175955_2_, structureboundingbox, p_175955_6_);
/*      */     }
/*  162 */     return null;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private static StructureComponent func_175953_c(Stairs2 p_175953_0_, List<StructureComponent> p_175953_1_, Random p_175953_2_, int p_175953_3_, int p_175953_4_, int p_175953_5_, EnumFacing p_175953_6_, int p_175953_7_) {
/*  168 */     if (p_175953_7_ > 50)
/*  169 */       return null; 
/*  170 */     if (Math.abs(p_175953_3_ - (p_175953_0_.getBoundingBox()).minX) <= 112 && Math.abs(p_175953_5_ - (p_175953_0_.getBoundingBox()).minZ) <= 112) {
/*  171 */       StructureComponent structurecomponent = func_175955_b(p_175953_0_, p_175953_1_, p_175953_2_, p_175953_3_, p_175953_4_, p_175953_5_, p_175953_6_, p_175953_7_ + 1);
/*      */       
/*  173 */       if (structurecomponent != null) {
/*  174 */         p_175953_1_.add(structurecomponent);
/*  175 */         p_175953_0_.field_75026_c.add(structurecomponent);
/*      */       } 
/*      */       
/*  178 */       return structurecomponent;
/*      */     } 
/*  180 */     return null;
/*      */   }
/*      */   
/*      */   public static class ChestCorridor
/*      */     extends Stronghold {
/*  185 */     private static final List<WeightedRandomChestContent> strongholdChestContents = Lists.newArrayList((Object[])new WeightedRandomChestContent[] { new WeightedRandomChestContent(Items.ender_pearl, 0, 1, 1, 10), new WeightedRandomChestContent(Items.diamond, 0, 1, 3, 3), new WeightedRandomChestContent(Items.iron_ingot, 0, 1, 5, 10), new WeightedRandomChestContent(Items.gold_ingot, 0, 1, 3, 5), new WeightedRandomChestContent(Items.redstone, 0, 4, 9, 5), new WeightedRandomChestContent(Items.bread, 0, 1, 3, 15), new WeightedRandomChestContent(Items.apple, 0, 1, 3, 15), new WeightedRandomChestContent(Items.iron_pickaxe, 0, 1, 1, 5), new WeightedRandomChestContent(Items.iron_sword, 0, 1, 1, 5), new WeightedRandomChestContent((Item)Items.iron_chestplate, 0, 1, 1, 5), new WeightedRandomChestContent((Item)Items.iron_helmet, 0, 1, 1, 5), new WeightedRandomChestContent((Item)Items.iron_leggings, 0, 1, 1, 5), new WeightedRandomChestContent((Item)Items.iron_boots, 0, 1, 1, 5), new WeightedRandomChestContent(Items.golden_apple, 0, 1, 1, 1), new WeightedRandomChestContent(Items.saddle, 0, 1, 1, 1), new WeightedRandomChestContent(Items.iron_horse_armor, 0, 1, 1, 1), new WeightedRandomChestContent(Items.golden_horse_armor, 0, 1, 1, 1), new WeightedRandomChestContent(Items.diamond_horse_armor, 0, 1, 1, 1) });
/*      */     
/*      */     private boolean hasMadeChest;
/*      */     
/*      */     public ChestCorridor() {}
/*      */     
/*      */     public ChestCorridor(int p_i45582_1_, Random p_i45582_2_, StructureBoundingBox p_i45582_3_, EnumFacing p_i45582_4_) {
/*  192 */       super(p_i45582_1_);
/*  193 */       this.coordBaseMode = p_i45582_4_;
/*  194 */       this.field_143013_d = getRandomDoor(p_i45582_2_);
/*  195 */       this.boundingBox = p_i45582_3_;
/*      */     }
/*      */     
/*      */     protected void writeStructureToNBT(NBTTagCompound tagCompound) {
/*  199 */       super.writeStructureToNBT(tagCompound);
/*  200 */       tagCompound.setBoolean("Chest", this.hasMadeChest);
/*      */     }
/*      */     
/*      */     protected void readStructureFromNBT(NBTTagCompound tagCompound) {
/*  204 */       super.readStructureFromNBT(tagCompound);
/*  205 */       this.hasMadeChest = tagCompound.getBoolean("Chest");
/*      */     }
/*      */     
/*      */     public void buildComponent(StructureComponent componentIn, List<StructureComponent> listIn, Random rand) {
/*  209 */       getNextComponentNormal((StructureStrongholdPieces.Stairs2)componentIn, listIn, rand, 1, 1);
/*      */     }
/*      */     
/*      */     public static ChestCorridor func_175868_a(List<StructureComponent> p_175868_0_, Random p_175868_1_, int p_175868_2_, int p_175868_3_, int p_175868_4_, EnumFacing p_175868_5_, int p_175868_6_) {
/*  213 */       StructureBoundingBox structureboundingbox = StructureBoundingBox.getComponentToAddBoundingBox(p_175868_2_, p_175868_3_, p_175868_4_, -1, -1, 0, 5, 5, 7, p_175868_5_);
/*  214 */       return (canStrongholdGoDeeper(structureboundingbox) && StructureComponent.findIntersecting(p_175868_0_, structureboundingbox) == null) ? new ChestCorridor(p_175868_6_, p_175868_1_, structureboundingbox, p_175868_5_) : null;
/*      */     }
/*      */     
/*      */     public boolean addComponentParts(World worldIn, Random randomIn, StructureBoundingBox structureBoundingBoxIn) {
/*  218 */       if (isLiquidInStructureBoundingBox(worldIn, structureBoundingBoxIn)) {
/*  219 */         return false;
/*      */       }
/*  221 */       fillWithRandomizedBlocks(worldIn, structureBoundingBoxIn, 0, 0, 0, 4, 4, 6, true, randomIn, StructureStrongholdPieces.strongholdStones);
/*  222 */       placeDoor(worldIn, randomIn, structureBoundingBoxIn, this.field_143013_d, 1, 1, 0);
/*  223 */       placeDoor(worldIn, randomIn, structureBoundingBoxIn, StructureStrongholdPieces.Stronghold.Door.OPENING, 1, 1, 6);
/*  224 */       fillWithBlocks(worldIn, structureBoundingBoxIn, 3, 1, 2, 3, 1, 4, Blocks.stonebrick.getDefaultState(), Blocks.stonebrick.getDefaultState(), false);
/*  225 */       setBlockState(worldIn, Blocks.stone_slab.getStateFromMeta(BlockStoneSlab.EnumType.SMOOTHBRICK.getMetadata()), 3, 1, 1, structureBoundingBoxIn);
/*  226 */       setBlockState(worldIn, Blocks.stone_slab.getStateFromMeta(BlockStoneSlab.EnumType.SMOOTHBRICK.getMetadata()), 3, 1, 5, structureBoundingBoxIn);
/*  227 */       setBlockState(worldIn, Blocks.stone_slab.getStateFromMeta(BlockStoneSlab.EnumType.SMOOTHBRICK.getMetadata()), 3, 2, 2, structureBoundingBoxIn);
/*  228 */       setBlockState(worldIn, Blocks.stone_slab.getStateFromMeta(BlockStoneSlab.EnumType.SMOOTHBRICK.getMetadata()), 3, 2, 4, structureBoundingBoxIn);
/*      */       
/*  230 */       for (int i = 2; i <= 4; i++) {
/*  231 */         setBlockState(worldIn, Blocks.stone_slab.getStateFromMeta(BlockStoneSlab.EnumType.SMOOTHBRICK.getMetadata()), 2, 1, i, structureBoundingBoxIn);
/*      */       }
/*      */       
/*  234 */       if (!this.hasMadeChest && structureBoundingBoxIn.isVecInside((Vec3i)new BlockPos(getXWithOffset(3, 3), getYWithOffset(2), getZWithOffset(3, 3)))) {
/*  235 */         this.hasMadeChest = true;
/*  236 */         generateChestContents(worldIn, structureBoundingBoxIn, randomIn, 3, 2, 3, WeightedRandomChestContent.func_177629_a(strongholdChestContents, new WeightedRandomChestContent[] { Items.enchanted_book.getRandom(randomIn) }), 2 + randomIn.nextInt(2));
/*      */       } 
/*      */       
/*  239 */       return true;
/*      */     }
/*      */   }
/*      */   
/*      */   public static class Corridor
/*      */     extends Stronghold
/*      */   {
/*      */     private int field_74993_a;
/*      */     
/*      */     public Corridor() {}
/*      */     
/*      */     public Corridor(int p_i45581_1_, Random p_i45581_2_, StructureBoundingBox p_i45581_3_, EnumFacing p_i45581_4_) {
/*  251 */       super(p_i45581_1_);
/*  252 */       this.coordBaseMode = p_i45581_4_;
/*  253 */       this.boundingBox = p_i45581_3_;
/*  254 */       this.field_74993_a = (p_i45581_4_ != EnumFacing.NORTH && p_i45581_4_ != EnumFacing.SOUTH) ? p_i45581_3_.getXSize() : p_i45581_3_.getZSize();
/*      */     }
/*      */     
/*      */     protected void writeStructureToNBT(NBTTagCompound tagCompound) {
/*  258 */       super.writeStructureToNBT(tagCompound);
/*  259 */       tagCompound.setInteger("Steps", this.field_74993_a);
/*      */     }
/*      */     
/*      */     protected void readStructureFromNBT(NBTTagCompound tagCompound) {
/*  263 */       super.readStructureFromNBT(tagCompound);
/*  264 */       this.field_74993_a = tagCompound.getInteger("Steps");
/*      */     }
/*      */     
/*      */     public static StructureBoundingBox func_175869_a(List<StructureComponent> p_175869_0_, Random p_175869_1_, int p_175869_2_, int p_175869_3_, int p_175869_4_, EnumFacing p_175869_5_) {
/*  268 */       int i = 3;
/*  269 */       StructureBoundingBox structureboundingbox = StructureBoundingBox.getComponentToAddBoundingBox(p_175869_2_, p_175869_3_, p_175869_4_, -1, -1, 0, 5, 5, 4, p_175869_5_);
/*  270 */       StructureComponent structurecomponent = StructureComponent.findIntersecting(p_175869_0_, structureboundingbox);
/*      */       
/*  272 */       if (structurecomponent == null) {
/*  273 */         return null;
/*      */       }
/*  275 */       if ((structurecomponent.getBoundingBox()).minY == structureboundingbox.minY) {
/*  276 */         for (int j = 3; j >= 1; j--) {
/*  277 */           structureboundingbox = StructureBoundingBox.getComponentToAddBoundingBox(p_175869_2_, p_175869_3_, p_175869_4_, -1, -1, 0, 5, 5, j - 1, p_175869_5_);
/*      */           
/*  279 */           if (!structurecomponent.getBoundingBox().intersectsWith(structureboundingbox)) {
/*  280 */             return StructureBoundingBox.getComponentToAddBoundingBox(p_175869_2_, p_175869_3_, p_175869_4_, -1, -1, 0, 5, 5, j, p_175869_5_);
/*      */           }
/*      */         } 
/*      */       }
/*      */       
/*  285 */       return null;
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean addComponentParts(World worldIn, Random randomIn, StructureBoundingBox structureBoundingBoxIn) {
/*  290 */       if (isLiquidInStructureBoundingBox(worldIn, structureBoundingBoxIn)) {
/*  291 */         return false;
/*      */       }
/*  293 */       for (int i = 0; i < this.field_74993_a; i++) {
/*  294 */         setBlockState(worldIn, Blocks.stonebrick.getDefaultState(), 0, 0, i, structureBoundingBoxIn);
/*  295 */         setBlockState(worldIn, Blocks.stonebrick.getDefaultState(), 1, 0, i, structureBoundingBoxIn);
/*  296 */         setBlockState(worldIn, Blocks.stonebrick.getDefaultState(), 2, 0, i, structureBoundingBoxIn);
/*  297 */         setBlockState(worldIn, Blocks.stonebrick.getDefaultState(), 3, 0, i, structureBoundingBoxIn);
/*  298 */         setBlockState(worldIn, Blocks.stonebrick.getDefaultState(), 4, 0, i, structureBoundingBoxIn);
/*      */         
/*  300 */         for (int j = 1; j <= 3; j++) {
/*  301 */           setBlockState(worldIn, Blocks.stonebrick.getDefaultState(), 0, j, i, structureBoundingBoxIn);
/*  302 */           setBlockState(worldIn, Blocks.air.getDefaultState(), 1, j, i, structureBoundingBoxIn);
/*  303 */           setBlockState(worldIn, Blocks.air.getDefaultState(), 2, j, i, structureBoundingBoxIn);
/*  304 */           setBlockState(worldIn, Blocks.air.getDefaultState(), 3, j, i, structureBoundingBoxIn);
/*  305 */           setBlockState(worldIn, Blocks.stonebrick.getDefaultState(), 4, j, i, structureBoundingBoxIn);
/*      */         } 
/*      */         
/*  308 */         setBlockState(worldIn, Blocks.stonebrick.getDefaultState(), 0, 4, i, structureBoundingBoxIn);
/*  309 */         setBlockState(worldIn, Blocks.stonebrick.getDefaultState(), 1, 4, i, structureBoundingBoxIn);
/*  310 */         setBlockState(worldIn, Blocks.stonebrick.getDefaultState(), 2, 4, i, structureBoundingBoxIn);
/*  311 */         setBlockState(worldIn, Blocks.stonebrick.getDefaultState(), 3, 4, i, structureBoundingBoxIn);
/*  312 */         setBlockState(worldIn, Blocks.stonebrick.getDefaultState(), 4, 4, i, structureBoundingBoxIn);
/*      */       } 
/*      */       
/*  315 */       return true;
/*      */     }
/*      */   }
/*      */   
/*      */   public static class Crossing
/*      */     extends Stronghold
/*      */   {
/*      */     private boolean field_74996_b;
/*      */     private boolean field_74997_c;
/*      */     private boolean field_74995_d;
/*      */     private boolean field_74999_h;
/*      */     
/*      */     public Crossing() {}
/*      */     
/*      */     public Crossing(int p_i45580_1_, Random p_i45580_2_, StructureBoundingBox p_i45580_3_, EnumFacing p_i45580_4_) {
/*  330 */       super(p_i45580_1_);
/*  331 */       this.coordBaseMode = p_i45580_4_;
/*  332 */       this.field_143013_d = getRandomDoor(p_i45580_2_);
/*  333 */       this.boundingBox = p_i45580_3_;
/*  334 */       this.field_74996_b = p_i45580_2_.nextBoolean();
/*  335 */       this.field_74997_c = p_i45580_2_.nextBoolean();
/*  336 */       this.field_74995_d = p_i45580_2_.nextBoolean();
/*  337 */       this.field_74999_h = (p_i45580_2_.nextInt(3) > 0);
/*      */     }
/*      */     
/*      */     protected void writeStructureToNBT(NBTTagCompound tagCompound) {
/*  341 */       super.writeStructureToNBT(tagCompound);
/*  342 */       tagCompound.setBoolean("leftLow", this.field_74996_b);
/*  343 */       tagCompound.setBoolean("leftHigh", this.field_74997_c);
/*  344 */       tagCompound.setBoolean("rightLow", this.field_74995_d);
/*  345 */       tagCompound.setBoolean("rightHigh", this.field_74999_h);
/*      */     }
/*      */     
/*      */     protected void readStructureFromNBT(NBTTagCompound tagCompound) {
/*  349 */       super.readStructureFromNBT(tagCompound);
/*  350 */       this.field_74996_b = tagCompound.getBoolean("leftLow");
/*  351 */       this.field_74997_c = tagCompound.getBoolean("leftHigh");
/*  352 */       this.field_74995_d = tagCompound.getBoolean("rightLow");
/*  353 */       this.field_74999_h = tagCompound.getBoolean("rightHigh");
/*      */     }
/*      */     
/*      */     public void buildComponent(StructureComponent componentIn, List<StructureComponent> listIn, Random rand) {
/*  357 */       int i = 3;
/*  358 */       int j = 5;
/*      */       
/*  360 */       if (this.coordBaseMode == EnumFacing.WEST || this.coordBaseMode == EnumFacing.NORTH) {
/*  361 */         i = 8 - i;
/*  362 */         j = 8 - j;
/*      */       } 
/*      */       
/*  365 */       getNextComponentNormal((StructureStrongholdPieces.Stairs2)componentIn, listIn, rand, 5, 1);
/*      */       
/*  367 */       if (this.field_74996_b) {
/*  368 */         getNextComponentX((StructureStrongholdPieces.Stairs2)componentIn, listIn, rand, i, 1);
/*      */       }
/*      */       
/*  371 */       if (this.field_74997_c) {
/*  372 */         getNextComponentX((StructureStrongholdPieces.Stairs2)componentIn, listIn, rand, j, 7);
/*      */       }
/*      */       
/*  375 */       if (this.field_74995_d) {
/*  376 */         getNextComponentZ((StructureStrongholdPieces.Stairs2)componentIn, listIn, rand, i, 1);
/*      */       }
/*      */       
/*  379 */       if (this.field_74999_h) {
/*  380 */         getNextComponentZ((StructureStrongholdPieces.Stairs2)componentIn, listIn, rand, j, 7);
/*      */       }
/*      */     }
/*      */     
/*      */     public static Crossing func_175866_a(List<StructureComponent> p_175866_0_, Random p_175866_1_, int p_175866_2_, int p_175866_3_, int p_175866_4_, EnumFacing p_175866_5_, int p_175866_6_) {
/*  385 */       StructureBoundingBox structureboundingbox = StructureBoundingBox.getComponentToAddBoundingBox(p_175866_2_, p_175866_3_, p_175866_4_, -4, -3, 0, 10, 9, 11, p_175866_5_);
/*  386 */       return (canStrongholdGoDeeper(structureboundingbox) && StructureComponent.findIntersecting(p_175866_0_, structureboundingbox) == null) ? new Crossing(p_175866_6_, p_175866_1_, structureboundingbox, p_175866_5_) : null;
/*      */     }
/*      */     
/*      */     public boolean addComponentParts(World worldIn, Random randomIn, StructureBoundingBox structureBoundingBoxIn) {
/*  390 */       if (isLiquidInStructureBoundingBox(worldIn, structureBoundingBoxIn)) {
/*  391 */         return false;
/*      */       }
/*  393 */       fillWithRandomizedBlocks(worldIn, structureBoundingBoxIn, 0, 0, 0, 9, 8, 10, true, randomIn, StructureStrongholdPieces.strongholdStones);
/*  394 */       placeDoor(worldIn, randomIn, structureBoundingBoxIn, this.field_143013_d, 4, 3, 0);
/*      */       
/*  396 */       if (this.field_74996_b) {
/*  397 */         fillWithBlocks(worldIn, structureBoundingBoxIn, 0, 3, 1, 0, 5, 3, Blocks.air.getDefaultState(), Blocks.air.getDefaultState(), false);
/*      */       }
/*      */       
/*  400 */       if (this.field_74995_d) {
/*  401 */         fillWithBlocks(worldIn, structureBoundingBoxIn, 9, 3, 1, 9, 5, 3, Blocks.air.getDefaultState(), Blocks.air.getDefaultState(), false);
/*      */       }
/*      */       
/*  404 */       if (this.field_74997_c) {
/*  405 */         fillWithBlocks(worldIn, structureBoundingBoxIn, 0, 5, 7, 0, 7, 9, Blocks.air.getDefaultState(), Blocks.air.getDefaultState(), false);
/*      */       }
/*      */       
/*  408 */       if (this.field_74999_h) {
/*  409 */         fillWithBlocks(worldIn, structureBoundingBoxIn, 9, 5, 7, 9, 7, 9, Blocks.air.getDefaultState(), Blocks.air.getDefaultState(), false);
/*      */       }
/*      */       
/*  412 */       fillWithBlocks(worldIn, structureBoundingBoxIn, 5, 1, 10, 7, 3, 10, Blocks.air.getDefaultState(), Blocks.air.getDefaultState(), false);
/*  413 */       fillWithRandomizedBlocks(worldIn, structureBoundingBoxIn, 1, 2, 1, 8, 2, 6, false, randomIn, StructureStrongholdPieces.strongholdStones);
/*  414 */       fillWithRandomizedBlocks(worldIn, structureBoundingBoxIn, 4, 1, 5, 4, 4, 9, false, randomIn, StructureStrongholdPieces.strongholdStones);
/*  415 */       fillWithRandomizedBlocks(worldIn, structureBoundingBoxIn, 8, 1, 5, 8, 4, 9, false, randomIn, StructureStrongholdPieces.strongholdStones);
/*  416 */       fillWithRandomizedBlocks(worldIn, structureBoundingBoxIn, 1, 4, 7, 3, 4, 9, false, randomIn, StructureStrongholdPieces.strongholdStones);
/*  417 */       fillWithRandomizedBlocks(worldIn, structureBoundingBoxIn, 1, 3, 5, 3, 3, 6, false, randomIn, StructureStrongholdPieces.strongholdStones);
/*  418 */       fillWithBlocks(worldIn, structureBoundingBoxIn, 1, 3, 4, 3, 3, 4, Blocks.stone_slab.getDefaultState(), Blocks.stone_slab.getDefaultState(), false);
/*  419 */       fillWithBlocks(worldIn, structureBoundingBoxIn, 1, 4, 6, 3, 4, 6, Blocks.stone_slab.getDefaultState(), Blocks.stone_slab.getDefaultState(), false);
/*  420 */       fillWithRandomizedBlocks(worldIn, structureBoundingBoxIn, 5, 1, 7, 7, 1, 8, false, randomIn, StructureStrongholdPieces.strongholdStones);
/*  421 */       fillWithBlocks(worldIn, structureBoundingBoxIn, 5, 1, 9, 7, 1, 9, Blocks.stone_slab.getDefaultState(), Blocks.stone_slab.getDefaultState(), false);
/*  422 */       fillWithBlocks(worldIn, structureBoundingBoxIn, 5, 2, 7, 7, 2, 7, Blocks.stone_slab.getDefaultState(), Blocks.stone_slab.getDefaultState(), false);
/*  423 */       fillWithBlocks(worldIn, structureBoundingBoxIn, 4, 5, 7, 4, 5, 9, Blocks.stone_slab.getDefaultState(), Blocks.stone_slab.getDefaultState(), false);
/*  424 */       fillWithBlocks(worldIn, structureBoundingBoxIn, 8, 5, 7, 8, 5, 9, Blocks.stone_slab.getDefaultState(), Blocks.stone_slab.getDefaultState(), false);
/*  425 */       fillWithBlocks(worldIn, structureBoundingBoxIn, 5, 5, 7, 7, 5, 9, Blocks.double_stone_slab.getDefaultState(), Blocks.double_stone_slab.getDefaultState(), false);
/*  426 */       setBlockState(worldIn, Blocks.torch.getDefaultState(), 6, 5, 6, structureBoundingBoxIn);
/*  427 */       return true;
/*      */     }
/*      */   }
/*      */   
/*      */   public static class LeftTurn
/*      */     extends Stronghold
/*      */   {
/*      */     public LeftTurn() {}
/*      */     
/*      */     public LeftTurn(int p_i45579_1_, Random p_i45579_2_, StructureBoundingBox p_i45579_3_, EnumFacing p_i45579_4_) {
/*  437 */       super(p_i45579_1_);
/*  438 */       this.coordBaseMode = p_i45579_4_;
/*  439 */       this.field_143013_d = getRandomDoor(p_i45579_2_);
/*  440 */       this.boundingBox = p_i45579_3_;
/*      */     }
/*      */     
/*      */     public void buildComponent(StructureComponent componentIn, List<StructureComponent> listIn, Random rand) {
/*  444 */       if (this.coordBaseMode != EnumFacing.NORTH && this.coordBaseMode != EnumFacing.EAST) {
/*  445 */         getNextComponentZ((StructureStrongholdPieces.Stairs2)componentIn, listIn, rand, 1, 1);
/*      */       } else {
/*  447 */         getNextComponentX((StructureStrongholdPieces.Stairs2)componentIn, listIn, rand, 1, 1);
/*      */       } 
/*      */     }
/*      */     
/*      */     public static LeftTurn func_175867_a(List<StructureComponent> p_175867_0_, Random p_175867_1_, int p_175867_2_, int p_175867_3_, int p_175867_4_, EnumFacing p_175867_5_, int p_175867_6_) {
/*  452 */       StructureBoundingBox structureboundingbox = StructureBoundingBox.getComponentToAddBoundingBox(p_175867_2_, p_175867_3_, p_175867_4_, -1, -1, 0, 5, 5, 5, p_175867_5_);
/*  453 */       return (canStrongholdGoDeeper(structureboundingbox) && StructureComponent.findIntersecting(p_175867_0_, structureboundingbox) == null) ? new LeftTurn(p_175867_6_, p_175867_1_, structureboundingbox, p_175867_5_) : null;
/*      */     }
/*      */     
/*      */     public boolean addComponentParts(World worldIn, Random randomIn, StructureBoundingBox structureBoundingBoxIn) {
/*  457 */       if (isLiquidInStructureBoundingBox(worldIn, structureBoundingBoxIn)) {
/*  458 */         return false;
/*      */       }
/*  460 */       fillWithRandomizedBlocks(worldIn, structureBoundingBoxIn, 0, 0, 0, 4, 4, 4, true, randomIn, StructureStrongholdPieces.strongholdStones);
/*  461 */       placeDoor(worldIn, randomIn, structureBoundingBoxIn, this.field_143013_d, 1, 1, 0);
/*      */       
/*  463 */       if (this.coordBaseMode != EnumFacing.NORTH && this.coordBaseMode != EnumFacing.EAST) {
/*  464 */         fillWithBlocks(worldIn, structureBoundingBoxIn, 4, 1, 1, 4, 3, 3, Blocks.air.getDefaultState(), Blocks.air.getDefaultState(), false);
/*      */       } else {
/*  466 */         fillWithBlocks(worldIn, structureBoundingBoxIn, 0, 1, 1, 0, 3, 3, Blocks.air.getDefaultState(), Blocks.air.getDefaultState(), false);
/*      */       } 
/*      */       
/*  469 */       return true;
/*      */     }
/*      */   }
/*      */   
/*      */   public static class Library
/*      */     extends Stronghold {
/*  475 */     private static final List<WeightedRandomChestContent> strongholdLibraryChestContents = Lists.newArrayList((Object[])new WeightedRandomChestContent[] { new WeightedRandomChestContent(Items.book, 0, 1, 3, 20), new WeightedRandomChestContent(Items.paper, 0, 2, 7, 20), new WeightedRandomChestContent((Item)Items.map, 0, 1, 1, 1), new WeightedRandomChestContent(Items.compass, 0, 1, 1, 1) });
/*      */     
/*      */     private boolean isLargeRoom;
/*      */     
/*      */     public Library() {}
/*      */     
/*      */     public Library(int p_i45578_1_, Random p_i45578_2_, StructureBoundingBox p_i45578_3_, EnumFacing p_i45578_4_) {
/*  482 */       super(p_i45578_1_);
/*  483 */       this.coordBaseMode = p_i45578_4_;
/*  484 */       this.field_143013_d = getRandomDoor(p_i45578_2_);
/*  485 */       this.boundingBox = p_i45578_3_;
/*  486 */       this.isLargeRoom = (p_i45578_3_.getYSize() > 6);
/*      */     }
/*      */     
/*      */     protected void writeStructureToNBT(NBTTagCompound tagCompound) {
/*  490 */       super.writeStructureToNBT(tagCompound);
/*  491 */       tagCompound.setBoolean("Tall", this.isLargeRoom);
/*      */     }
/*      */     
/*      */     protected void readStructureFromNBT(NBTTagCompound tagCompound) {
/*  495 */       super.readStructureFromNBT(tagCompound);
/*  496 */       this.isLargeRoom = tagCompound.getBoolean("Tall");
/*      */     }
/*      */     
/*      */     public static Library func_175864_a(List<StructureComponent> p_175864_0_, Random p_175864_1_, int p_175864_2_, int p_175864_3_, int p_175864_4_, EnumFacing p_175864_5_, int p_175864_6_) {
/*  500 */       StructureBoundingBox structureboundingbox = StructureBoundingBox.getComponentToAddBoundingBox(p_175864_2_, p_175864_3_, p_175864_4_, -4, -1, 0, 14, 11, 15, p_175864_5_);
/*      */       
/*  502 */       if (!canStrongholdGoDeeper(structureboundingbox) || StructureComponent.findIntersecting(p_175864_0_, structureboundingbox) != null) {
/*  503 */         structureboundingbox = StructureBoundingBox.getComponentToAddBoundingBox(p_175864_2_, p_175864_3_, p_175864_4_, -4, -1, 0, 14, 6, 15, p_175864_5_);
/*      */         
/*  505 */         if (!canStrongholdGoDeeper(structureboundingbox) || StructureComponent.findIntersecting(p_175864_0_, structureboundingbox) != null) {
/*  506 */           return null;
/*      */         }
/*      */       } 
/*      */       
/*  510 */       return new Library(p_175864_6_, p_175864_1_, structureboundingbox, p_175864_5_);
/*      */     }
/*      */     
/*      */     public boolean addComponentParts(World worldIn, Random randomIn, StructureBoundingBox structureBoundingBoxIn) {
/*  514 */       if (isLiquidInStructureBoundingBox(worldIn, structureBoundingBoxIn)) {
/*  515 */         return false;
/*      */       }
/*  517 */       int i = 11;
/*      */       
/*  519 */       if (!this.isLargeRoom) {
/*  520 */         i = 6;
/*      */       }
/*      */       
/*  523 */       fillWithRandomizedBlocks(worldIn, structureBoundingBoxIn, 0, 0, 0, 13, i - 1, 14, true, randomIn, StructureStrongholdPieces.strongholdStones);
/*  524 */       placeDoor(worldIn, randomIn, structureBoundingBoxIn, this.field_143013_d, 4, 1, 0);
/*  525 */       func_175805_a(worldIn, structureBoundingBoxIn, randomIn, 0.07F, 2, 1, 1, 11, 4, 13, Blocks.web.getDefaultState(), Blocks.web.getDefaultState(), false);
/*  526 */       int j = 1;
/*  527 */       int k = 12;
/*      */       
/*  529 */       for (int l = 1; l <= 13; l++) {
/*  530 */         if ((l - 1) % 4 == 0) {
/*  531 */           fillWithBlocks(worldIn, structureBoundingBoxIn, 1, 1, l, 1, 4, l, Blocks.planks.getDefaultState(), Blocks.planks.getDefaultState(), false);
/*  532 */           fillWithBlocks(worldIn, structureBoundingBoxIn, 12, 1, l, 12, 4, l, Blocks.planks.getDefaultState(), Blocks.planks.getDefaultState(), false);
/*  533 */           setBlockState(worldIn, Blocks.torch.getDefaultState(), 2, 3, l, structureBoundingBoxIn);
/*  534 */           setBlockState(worldIn, Blocks.torch.getDefaultState(), 11, 3, l, structureBoundingBoxIn);
/*      */           
/*  536 */           if (this.isLargeRoom) {
/*  537 */             fillWithBlocks(worldIn, structureBoundingBoxIn, 1, 6, l, 1, 9, l, Blocks.planks.getDefaultState(), Blocks.planks.getDefaultState(), false);
/*  538 */             fillWithBlocks(worldIn, structureBoundingBoxIn, 12, 6, l, 12, 9, l, Blocks.planks.getDefaultState(), Blocks.planks.getDefaultState(), false);
/*      */           } 
/*      */         } else {
/*  541 */           fillWithBlocks(worldIn, structureBoundingBoxIn, 1, 1, l, 1, 4, l, Blocks.bookshelf.getDefaultState(), Blocks.bookshelf.getDefaultState(), false);
/*  542 */           fillWithBlocks(worldIn, structureBoundingBoxIn, 12, 1, l, 12, 4, l, Blocks.bookshelf.getDefaultState(), Blocks.bookshelf.getDefaultState(), false);
/*      */           
/*  544 */           if (this.isLargeRoom) {
/*  545 */             fillWithBlocks(worldIn, structureBoundingBoxIn, 1, 6, l, 1, 9, l, Blocks.bookshelf.getDefaultState(), Blocks.bookshelf.getDefaultState(), false);
/*  546 */             fillWithBlocks(worldIn, structureBoundingBoxIn, 12, 6, l, 12, 9, l, Blocks.bookshelf.getDefaultState(), Blocks.bookshelf.getDefaultState(), false);
/*      */           } 
/*      */         } 
/*      */       } 
/*      */       
/*  551 */       for (int k1 = 3; k1 < 12; k1 += 2) {
/*  552 */         fillWithBlocks(worldIn, structureBoundingBoxIn, 3, 1, k1, 4, 3, k1, Blocks.bookshelf.getDefaultState(), Blocks.bookshelf.getDefaultState(), false);
/*  553 */         fillWithBlocks(worldIn, structureBoundingBoxIn, 6, 1, k1, 7, 3, k1, Blocks.bookshelf.getDefaultState(), Blocks.bookshelf.getDefaultState(), false);
/*  554 */         fillWithBlocks(worldIn, structureBoundingBoxIn, 9, 1, k1, 10, 3, k1, Blocks.bookshelf.getDefaultState(), Blocks.bookshelf.getDefaultState(), false);
/*      */       } 
/*      */       
/*  557 */       if (this.isLargeRoom) {
/*  558 */         fillWithBlocks(worldIn, structureBoundingBoxIn, 1, 5, 1, 3, 5, 13, Blocks.planks.getDefaultState(), Blocks.planks.getDefaultState(), false);
/*  559 */         fillWithBlocks(worldIn, structureBoundingBoxIn, 10, 5, 1, 12, 5, 13, Blocks.planks.getDefaultState(), Blocks.planks.getDefaultState(), false);
/*  560 */         fillWithBlocks(worldIn, structureBoundingBoxIn, 4, 5, 1, 9, 5, 2, Blocks.planks.getDefaultState(), Blocks.planks.getDefaultState(), false);
/*  561 */         fillWithBlocks(worldIn, structureBoundingBoxIn, 4, 5, 12, 9, 5, 13, Blocks.planks.getDefaultState(), Blocks.planks.getDefaultState(), false);
/*  562 */         setBlockState(worldIn, Blocks.planks.getDefaultState(), 9, 5, 11, structureBoundingBoxIn);
/*  563 */         setBlockState(worldIn, Blocks.planks.getDefaultState(), 8, 5, 11, structureBoundingBoxIn);
/*  564 */         setBlockState(worldIn, Blocks.planks.getDefaultState(), 9, 5, 10, structureBoundingBoxIn);
/*  565 */         fillWithBlocks(worldIn, structureBoundingBoxIn, 3, 6, 2, 3, 6, 12, Blocks.oak_fence.getDefaultState(), Blocks.oak_fence.getDefaultState(), false);
/*  566 */         fillWithBlocks(worldIn, structureBoundingBoxIn, 10, 6, 2, 10, 6, 10, Blocks.oak_fence.getDefaultState(), Blocks.oak_fence.getDefaultState(), false);
/*  567 */         fillWithBlocks(worldIn, structureBoundingBoxIn, 4, 6, 2, 9, 6, 2, Blocks.oak_fence.getDefaultState(), Blocks.oak_fence.getDefaultState(), false);
/*  568 */         fillWithBlocks(worldIn, structureBoundingBoxIn, 4, 6, 12, 8, 6, 12, Blocks.oak_fence.getDefaultState(), Blocks.oak_fence.getDefaultState(), false);
/*  569 */         setBlockState(worldIn, Blocks.oak_fence.getDefaultState(), 9, 6, 11, structureBoundingBoxIn);
/*  570 */         setBlockState(worldIn, Blocks.oak_fence.getDefaultState(), 8, 6, 11, structureBoundingBoxIn);
/*  571 */         setBlockState(worldIn, Blocks.oak_fence.getDefaultState(), 9, 6, 10, structureBoundingBoxIn);
/*  572 */         int l1 = getMetadataWithOffset(Blocks.ladder, 3);
/*  573 */         setBlockState(worldIn, Blocks.ladder.getStateFromMeta(l1), 10, 1, 13, structureBoundingBoxIn);
/*  574 */         setBlockState(worldIn, Blocks.ladder.getStateFromMeta(l1), 10, 2, 13, structureBoundingBoxIn);
/*  575 */         setBlockState(worldIn, Blocks.ladder.getStateFromMeta(l1), 10, 3, 13, structureBoundingBoxIn);
/*  576 */         setBlockState(worldIn, Blocks.ladder.getStateFromMeta(l1), 10, 4, 13, structureBoundingBoxIn);
/*  577 */         setBlockState(worldIn, Blocks.ladder.getStateFromMeta(l1), 10, 5, 13, structureBoundingBoxIn);
/*  578 */         setBlockState(worldIn, Blocks.ladder.getStateFromMeta(l1), 10, 6, 13, structureBoundingBoxIn);
/*  579 */         setBlockState(worldIn, Blocks.ladder.getStateFromMeta(l1), 10, 7, 13, structureBoundingBoxIn);
/*  580 */         int i1 = 7;
/*  581 */         int j1 = 7;
/*  582 */         setBlockState(worldIn, Blocks.oak_fence.getDefaultState(), i1 - 1, 9, j1, structureBoundingBoxIn);
/*  583 */         setBlockState(worldIn, Blocks.oak_fence.getDefaultState(), i1, 9, j1, structureBoundingBoxIn);
/*  584 */         setBlockState(worldIn, Blocks.oak_fence.getDefaultState(), i1 - 1, 8, j1, structureBoundingBoxIn);
/*  585 */         setBlockState(worldIn, Blocks.oak_fence.getDefaultState(), i1, 8, j1, structureBoundingBoxIn);
/*  586 */         setBlockState(worldIn, Blocks.oak_fence.getDefaultState(), i1 - 1, 7, j1, structureBoundingBoxIn);
/*  587 */         setBlockState(worldIn, Blocks.oak_fence.getDefaultState(), i1, 7, j1, structureBoundingBoxIn);
/*  588 */         setBlockState(worldIn, Blocks.oak_fence.getDefaultState(), i1 - 2, 7, j1, structureBoundingBoxIn);
/*  589 */         setBlockState(worldIn, Blocks.oak_fence.getDefaultState(), i1 + 1, 7, j1, structureBoundingBoxIn);
/*  590 */         setBlockState(worldIn, Blocks.oak_fence.getDefaultState(), i1 - 1, 7, j1 - 1, structureBoundingBoxIn);
/*  591 */         setBlockState(worldIn, Blocks.oak_fence.getDefaultState(), i1 - 1, 7, j1 + 1, structureBoundingBoxIn);
/*  592 */         setBlockState(worldIn, Blocks.oak_fence.getDefaultState(), i1, 7, j1 - 1, structureBoundingBoxIn);
/*  593 */         setBlockState(worldIn, Blocks.oak_fence.getDefaultState(), i1, 7, j1 + 1, structureBoundingBoxIn);
/*  594 */         setBlockState(worldIn, Blocks.torch.getDefaultState(), i1 - 2, 8, j1, structureBoundingBoxIn);
/*  595 */         setBlockState(worldIn, Blocks.torch.getDefaultState(), i1 + 1, 8, j1, structureBoundingBoxIn);
/*  596 */         setBlockState(worldIn, Blocks.torch.getDefaultState(), i1 - 1, 8, j1 - 1, structureBoundingBoxIn);
/*  597 */         setBlockState(worldIn, Blocks.torch.getDefaultState(), i1 - 1, 8, j1 + 1, structureBoundingBoxIn);
/*  598 */         setBlockState(worldIn, Blocks.torch.getDefaultState(), i1, 8, j1 - 1, structureBoundingBoxIn);
/*  599 */         setBlockState(worldIn, Blocks.torch.getDefaultState(), i1, 8, j1 + 1, structureBoundingBoxIn);
/*      */       } 
/*      */       
/*  602 */       generateChestContents(worldIn, structureBoundingBoxIn, randomIn, 3, 3, 5, WeightedRandomChestContent.func_177629_a(strongholdLibraryChestContents, new WeightedRandomChestContent[] { Items.enchanted_book.getRandom(randomIn, 1, 5, 2) }), 1 + randomIn.nextInt(4));
/*      */       
/*  604 */       if (this.isLargeRoom) {
/*  605 */         setBlockState(worldIn, Blocks.air.getDefaultState(), 12, 9, 1, structureBoundingBoxIn);
/*  606 */         generateChestContents(worldIn, structureBoundingBoxIn, randomIn, 12, 8, 1, WeightedRandomChestContent.func_177629_a(strongholdLibraryChestContents, new WeightedRandomChestContent[] { Items.enchanted_book.getRandom(randomIn, 1, 5, 2) }), 1 + randomIn.nextInt(4));
/*      */       } 
/*      */       
/*  609 */       return true;
/*      */     }
/*      */   }
/*      */   
/*      */   static class PieceWeight
/*      */   {
/*      */     public Class<? extends StructureStrongholdPieces.Stronghold> pieceClass;
/*      */     public final int pieceWeight;
/*      */     public int instancesSpawned;
/*      */     public int instancesLimit;
/*      */     
/*      */     public PieceWeight(Class<? extends StructureStrongholdPieces.Stronghold> p_i2076_1_, int p_i2076_2_, int p_i2076_3_) {
/*  621 */       this.pieceClass = p_i2076_1_;
/*  622 */       this.pieceWeight = p_i2076_2_;
/*  623 */       this.instancesLimit = p_i2076_3_;
/*      */     }
/*      */     
/*      */     public boolean canSpawnMoreStructuresOfType(int p_75189_1_) {
/*  627 */       return !(this.instancesLimit != 0 && this.instancesSpawned >= this.instancesLimit);
/*      */     }
/*      */     
/*      */     public boolean canSpawnMoreStructures() {
/*  631 */       return !(this.instancesLimit != 0 && this.instancesSpawned >= this.instancesLimit);
/*      */     }
/*      */   }
/*      */   
/*      */   public static class PortalRoom
/*      */     extends Stronghold {
/*      */     private boolean hasSpawner;
/*      */     
/*      */     public PortalRoom() {}
/*      */     
/*      */     public PortalRoom(int p_i45577_1_, Random p_i45577_2_, StructureBoundingBox p_i45577_3_, EnumFacing p_i45577_4_) {
/*  642 */       super(p_i45577_1_);
/*  643 */       this.coordBaseMode = p_i45577_4_;
/*  644 */       this.boundingBox = p_i45577_3_;
/*      */     }
/*      */     
/*      */     protected void writeStructureToNBT(NBTTagCompound tagCompound) {
/*  648 */       super.writeStructureToNBT(tagCompound);
/*  649 */       tagCompound.setBoolean("Mob", this.hasSpawner);
/*      */     }
/*      */     
/*      */     protected void readStructureFromNBT(NBTTagCompound tagCompound) {
/*  653 */       super.readStructureFromNBT(tagCompound);
/*  654 */       this.hasSpawner = tagCompound.getBoolean("Mob");
/*      */     }
/*      */     
/*      */     public void buildComponent(StructureComponent componentIn, List<StructureComponent> listIn, Random rand) {
/*  658 */       if (componentIn != null) {
/*  659 */         ((StructureStrongholdPieces.Stairs2)componentIn).strongholdPortalRoom = this;
/*      */       }
/*      */     }
/*      */     
/*      */     public static PortalRoom func_175865_a(List<StructureComponent> p_175865_0_, Random p_175865_1_, int p_175865_2_, int p_175865_3_, int p_175865_4_, EnumFacing p_175865_5_, int p_175865_6_) {
/*  664 */       StructureBoundingBox structureboundingbox = StructureBoundingBox.getComponentToAddBoundingBox(p_175865_2_, p_175865_3_, p_175865_4_, -4, -1, 0, 11, 8, 16, p_175865_5_);
/*  665 */       return (canStrongholdGoDeeper(structureboundingbox) && StructureComponent.findIntersecting(p_175865_0_, structureboundingbox) == null) ? new PortalRoom(p_175865_6_, p_175865_1_, structureboundingbox, p_175865_5_) : null;
/*      */     }
/*      */     
/*      */     public boolean addComponentParts(World worldIn, Random randomIn, StructureBoundingBox structureBoundingBoxIn) {
/*  669 */       fillWithRandomizedBlocks(worldIn, structureBoundingBoxIn, 0, 0, 0, 10, 7, 15, false, randomIn, StructureStrongholdPieces.strongholdStones);
/*  670 */       placeDoor(worldIn, randomIn, structureBoundingBoxIn, StructureStrongholdPieces.Stronghold.Door.GRATES, 4, 1, 0);
/*  671 */       int i = 6;
/*  672 */       fillWithRandomizedBlocks(worldIn, structureBoundingBoxIn, 1, i, 1, 1, i, 14, false, randomIn, StructureStrongholdPieces.strongholdStones);
/*  673 */       fillWithRandomizedBlocks(worldIn, structureBoundingBoxIn, 9, i, 1, 9, i, 14, false, randomIn, StructureStrongholdPieces.strongholdStones);
/*  674 */       fillWithRandomizedBlocks(worldIn, structureBoundingBoxIn, 2, i, 1, 8, i, 2, false, randomIn, StructureStrongholdPieces.strongholdStones);
/*  675 */       fillWithRandomizedBlocks(worldIn, structureBoundingBoxIn, 2, i, 14, 8, i, 14, false, randomIn, StructureStrongholdPieces.strongholdStones);
/*  676 */       fillWithRandomizedBlocks(worldIn, structureBoundingBoxIn, 1, 1, 1, 2, 1, 4, false, randomIn, StructureStrongholdPieces.strongholdStones);
/*  677 */       fillWithRandomizedBlocks(worldIn, structureBoundingBoxIn, 8, 1, 1, 9, 1, 4, false, randomIn, StructureStrongholdPieces.strongholdStones);
/*  678 */       fillWithBlocks(worldIn, structureBoundingBoxIn, 1, 1, 1, 1, 1, 3, Blocks.flowing_lava.getDefaultState(), Blocks.flowing_lava.getDefaultState(), false);
/*  679 */       fillWithBlocks(worldIn, structureBoundingBoxIn, 9, 1, 1, 9, 1, 3, Blocks.flowing_lava.getDefaultState(), Blocks.flowing_lava.getDefaultState(), false);
/*  680 */       fillWithRandomizedBlocks(worldIn, structureBoundingBoxIn, 3, 1, 8, 7, 1, 12, false, randomIn, StructureStrongholdPieces.strongholdStones);
/*  681 */       fillWithBlocks(worldIn, structureBoundingBoxIn, 4, 1, 9, 6, 1, 11, Blocks.flowing_lava.getDefaultState(), Blocks.flowing_lava.getDefaultState(), false);
/*      */       
/*  683 */       for (int j = 3; j < 14; j += 2) {
/*  684 */         fillWithBlocks(worldIn, structureBoundingBoxIn, 0, 3, j, 0, 4, j, Blocks.iron_bars.getDefaultState(), Blocks.iron_bars.getDefaultState(), false);
/*  685 */         fillWithBlocks(worldIn, structureBoundingBoxIn, 10, 3, j, 10, 4, j, Blocks.iron_bars.getDefaultState(), Blocks.iron_bars.getDefaultState(), false);
/*      */       } 
/*      */       
/*  688 */       for (int k1 = 2; k1 < 9; k1 += 2) {
/*  689 */         fillWithBlocks(worldIn, structureBoundingBoxIn, k1, 3, 15, k1, 4, 15, Blocks.iron_bars.getDefaultState(), Blocks.iron_bars.getDefaultState(), false);
/*      */       }
/*      */       
/*  692 */       int l1 = getMetadataWithOffset(Blocks.stone_brick_stairs, 3);
/*  693 */       fillWithRandomizedBlocks(worldIn, structureBoundingBoxIn, 4, 1, 5, 6, 1, 7, false, randomIn, StructureStrongholdPieces.strongholdStones);
/*  694 */       fillWithRandomizedBlocks(worldIn, structureBoundingBoxIn, 4, 2, 6, 6, 2, 7, false, randomIn, StructureStrongholdPieces.strongholdStones);
/*  695 */       fillWithRandomizedBlocks(worldIn, structureBoundingBoxIn, 4, 3, 7, 6, 3, 7, false, randomIn, StructureStrongholdPieces.strongholdStones);
/*      */       
/*  697 */       for (int k = 4; k <= 6; k++) {
/*  698 */         setBlockState(worldIn, Blocks.stone_brick_stairs.getStateFromMeta(l1), k, 1, 4, structureBoundingBoxIn);
/*  699 */         setBlockState(worldIn, Blocks.stone_brick_stairs.getStateFromMeta(l1), k, 2, 5, structureBoundingBoxIn);
/*  700 */         setBlockState(worldIn, Blocks.stone_brick_stairs.getStateFromMeta(l1), k, 3, 6, structureBoundingBoxIn);
/*      */       } 
/*      */       
/*  703 */       int i2 = EnumFacing.NORTH.getHorizontalIndex();
/*  704 */       int l = EnumFacing.SOUTH.getHorizontalIndex();
/*  705 */       int i1 = EnumFacing.EAST.getHorizontalIndex();
/*  706 */       int j1 = EnumFacing.WEST.getHorizontalIndex();
/*      */       
/*  708 */       if (this.coordBaseMode != null) {
/*  709 */         switch (this.coordBaseMode) {
/*      */           case SOUTH:
/*  711 */             i2 = EnumFacing.SOUTH.getHorizontalIndex();
/*  712 */             l = EnumFacing.NORTH.getHorizontalIndex();
/*      */             break;
/*      */           
/*      */           case WEST:
/*  716 */             i2 = EnumFacing.WEST.getHorizontalIndex();
/*  717 */             l = EnumFacing.EAST.getHorizontalIndex();
/*  718 */             i1 = EnumFacing.SOUTH.getHorizontalIndex();
/*  719 */             j1 = EnumFacing.NORTH.getHorizontalIndex();
/*      */             break;
/*      */           
/*      */           case EAST:
/*  723 */             i2 = EnumFacing.EAST.getHorizontalIndex();
/*  724 */             l = EnumFacing.WEST.getHorizontalIndex();
/*  725 */             i1 = EnumFacing.SOUTH.getHorizontalIndex();
/*  726 */             j1 = EnumFacing.NORTH.getHorizontalIndex();
/*      */             break;
/*      */         } 
/*      */       }
/*  730 */       setBlockState(worldIn, Blocks.end_portal_frame.getStateFromMeta(i2).withProperty((IProperty)BlockEndPortalFrame.EYE, Boolean.valueOf((randomIn.nextFloat() > 0.9F))), 4, 3, 8, structureBoundingBoxIn);
/*  731 */       setBlockState(worldIn, Blocks.end_portal_frame.getStateFromMeta(i2).withProperty((IProperty)BlockEndPortalFrame.EYE, Boolean.valueOf((randomIn.nextFloat() > 0.9F))), 5, 3, 8, structureBoundingBoxIn);
/*  732 */       setBlockState(worldIn, Blocks.end_portal_frame.getStateFromMeta(i2).withProperty((IProperty)BlockEndPortalFrame.EYE, Boolean.valueOf((randomIn.nextFloat() > 0.9F))), 6, 3, 8, structureBoundingBoxIn);
/*  733 */       setBlockState(worldIn, Blocks.end_portal_frame.getStateFromMeta(l).withProperty((IProperty)BlockEndPortalFrame.EYE, Boolean.valueOf((randomIn.nextFloat() > 0.9F))), 4, 3, 12, structureBoundingBoxIn);
/*  734 */       setBlockState(worldIn, Blocks.end_portal_frame.getStateFromMeta(l).withProperty((IProperty)BlockEndPortalFrame.EYE, Boolean.valueOf((randomIn.nextFloat() > 0.9F))), 5, 3, 12, structureBoundingBoxIn);
/*  735 */       setBlockState(worldIn, Blocks.end_portal_frame.getStateFromMeta(l).withProperty((IProperty)BlockEndPortalFrame.EYE, Boolean.valueOf((randomIn.nextFloat() > 0.9F))), 6, 3, 12, structureBoundingBoxIn);
/*  736 */       setBlockState(worldIn, Blocks.end_portal_frame.getStateFromMeta(i1).withProperty((IProperty)BlockEndPortalFrame.EYE, Boolean.valueOf((randomIn.nextFloat() > 0.9F))), 3, 3, 9, structureBoundingBoxIn);
/*  737 */       setBlockState(worldIn, Blocks.end_portal_frame.getStateFromMeta(i1).withProperty((IProperty)BlockEndPortalFrame.EYE, Boolean.valueOf((randomIn.nextFloat() > 0.9F))), 3, 3, 10, structureBoundingBoxIn);
/*  738 */       setBlockState(worldIn, Blocks.end_portal_frame.getStateFromMeta(i1).withProperty((IProperty)BlockEndPortalFrame.EYE, Boolean.valueOf((randomIn.nextFloat() > 0.9F))), 3, 3, 11, structureBoundingBoxIn);
/*  739 */       setBlockState(worldIn, Blocks.end_portal_frame.getStateFromMeta(j1).withProperty((IProperty)BlockEndPortalFrame.EYE, Boolean.valueOf((randomIn.nextFloat() > 0.9F))), 7, 3, 9, structureBoundingBoxIn);
/*  740 */       setBlockState(worldIn, Blocks.end_portal_frame.getStateFromMeta(j1).withProperty((IProperty)BlockEndPortalFrame.EYE, Boolean.valueOf((randomIn.nextFloat() > 0.9F))), 7, 3, 10, structureBoundingBoxIn);
/*  741 */       setBlockState(worldIn, Blocks.end_portal_frame.getStateFromMeta(j1).withProperty((IProperty)BlockEndPortalFrame.EYE, Boolean.valueOf((randomIn.nextFloat() > 0.9F))), 7, 3, 11, structureBoundingBoxIn);
/*      */       
/*  743 */       if (!this.hasSpawner) {
/*  744 */         i = getYWithOffset(3);
/*  745 */         BlockPos blockpos = new BlockPos(getXWithOffset(5, 6), i, getZWithOffset(5, 6));
/*      */         
/*  747 */         if (structureBoundingBoxIn.isVecInside((Vec3i)blockpos)) {
/*  748 */           this.hasSpawner = true;
/*  749 */           worldIn.setBlockState(blockpos, Blocks.mob_spawner.getDefaultState(), 2);
/*  750 */           TileEntity tileentity = worldIn.getTileEntity(blockpos);
/*      */           
/*  752 */           if (tileentity instanceof TileEntityMobSpawner) {
/*  753 */             ((TileEntityMobSpawner)tileentity).getSpawnerBaseLogic().setEntityName("Silverfish");
/*      */           }
/*      */         } 
/*      */       } 
/*      */       
/*  758 */       return true;
/*      */     }
/*      */   }
/*      */   
/*      */   public static class Prison
/*      */     extends Stronghold {
/*      */     public Prison() {}
/*      */     
/*      */     public Prison(int p_i45576_1_, Random p_i45576_2_, StructureBoundingBox p_i45576_3_, EnumFacing p_i45576_4_) {
/*  767 */       super(p_i45576_1_);
/*  768 */       this.coordBaseMode = p_i45576_4_;
/*  769 */       this.field_143013_d = getRandomDoor(p_i45576_2_);
/*  770 */       this.boundingBox = p_i45576_3_;
/*      */     }
/*      */     
/*      */     public void buildComponent(StructureComponent componentIn, List<StructureComponent> listIn, Random rand) {
/*  774 */       getNextComponentNormal((StructureStrongholdPieces.Stairs2)componentIn, listIn, rand, 1, 1);
/*      */     }
/*      */     
/*      */     public static Prison func_175860_a(List<StructureComponent> p_175860_0_, Random p_175860_1_, int p_175860_2_, int p_175860_3_, int p_175860_4_, EnumFacing p_175860_5_, int p_175860_6_) {
/*  778 */       StructureBoundingBox structureboundingbox = StructureBoundingBox.getComponentToAddBoundingBox(p_175860_2_, p_175860_3_, p_175860_4_, -1, -1, 0, 9, 5, 11, p_175860_5_);
/*  779 */       return (canStrongholdGoDeeper(structureboundingbox) && StructureComponent.findIntersecting(p_175860_0_, structureboundingbox) == null) ? new Prison(p_175860_6_, p_175860_1_, structureboundingbox, p_175860_5_) : null;
/*      */     }
/*      */     
/*      */     public boolean addComponentParts(World worldIn, Random randomIn, StructureBoundingBox structureBoundingBoxIn) {
/*  783 */       if (isLiquidInStructureBoundingBox(worldIn, structureBoundingBoxIn)) {
/*  784 */         return false;
/*      */       }
/*  786 */       fillWithRandomizedBlocks(worldIn, structureBoundingBoxIn, 0, 0, 0, 8, 4, 10, true, randomIn, StructureStrongholdPieces.strongholdStones);
/*  787 */       placeDoor(worldIn, randomIn, structureBoundingBoxIn, this.field_143013_d, 1, 1, 0);
/*  788 */       fillWithBlocks(worldIn, structureBoundingBoxIn, 1, 1, 10, 3, 3, 10, Blocks.air.getDefaultState(), Blocks.air.getDefaultState(), false);
/*  789 */       fillWithRandomizedBlocks(worldIn, structureBoundingBoxIn, 4, 1, 1, 4, 3, 1, false, randomIn, StructureStrongholdPieces.strongholdStones);
/*  790 */       fillWithRandomizedBlocks(worldIn, structureBoundingBoxIn, 4, 1, 3, 4, 3, 3, false, randomIn, StructureStrongholdPieces.strongholdStones);
/*  791 */       fillWithRandomizedBlocks(worldIn, structureBoundingBoxIn, 4, 1, 7, 4, 3, 7, false, randomIn, StructureStrongholdPieces.strongholdStones);
/*  792 */       fillWithRandomizedBlocks(worldIn, structureBoundingBoxIn, 4, 1, 9, 4, 3, 9, false, randomIn, StructureStrongholdPieces.strongholdStones);
/*  793 */       fillWithBlocks(worldIn, structureBoundingBoxIn, 4, 1, 4, 4, 3, 6, Blocks.iron_bars.getDefaultState(), Blocks.iron_bars.getDefaultState(), false);
/*  794 */       fillWithBlocks(worldIn, structureBoundingBoxIn, 5, 1, 5, 7, 3, 5, Blocks.iron_bars.getDefaultState(), Blocks.iron_bars.getDefaultState(), false);
/*  795 */       setBlockState(worldIn, Blocks.iron_bars.getDefaultState(), 4, 3, 2, structureBoundingBoxIn);
/*  796 */       setBlockState(worldIn, Blocks.iron_bars.getDefaultState(), 4, 3, 8, structureBoundingBoxIn);
/*  797 */       setBlockState(worldIn, Blocks.iron_door.getStateFromMeta(getMetadataWithOffset(Blocks.iron_door, 3)), 4, 1, 2, structureBoundingBoxIn);
/*  798 */       setBlockState(worldIn, Blocks.iron_door.getStateFromMeta(getMetadataWithOffset(Blocks.iron_door, 3) + 8), 4, 2, 2, structureBoundingBoxIn);
/*  799 */       setBlockState(worldIn, Blocks.iron_door.getStateFromMeta(getMetadataWithOffset(Blocks.iron_door, 3)), 4, 1, 8, structureBoundingBoxIn);
/*  800 */       setBlockState(worldIn, Blocks.iron_door.getStateFromMeta(getMetadataWithOffset(Blocks.iron_door, 3) + 8), 4, 2, 8, structureBoundingBoxIn);
/*  801 */       return true;
/*      */     }
/*      */   }
/*      */   
/*      */   public static class RightTurn
/*      */     extends LeftTurn {
/*      */     public void buildComponent(StructureComponent componentIn, List<StructureComponent> listIn, Random rand) {
/*  808 */       if (this.coordBaseMode != EnumFacing.NORTH && this.coordBaseMode != EnumFacing.EAST) {
/*  809 */         getNextComponentX((StructureStrongholdPieces.Stairs2)componentIn, listIn, rand, 1, 1);
/*      */       } else {
/*  811 */         getNextComponentZ((StructureStrongholdPieces.Stairs2)componentIn, listIn, rand, 1, 1);
/*      */       } 
/*      */     }
/*      */     
/*      */     public boolean addComponentParts(World worldIn, Random randomIn, StructureBoundingBox structureBoundingBoxIn) {
/*  816 */       if (isLiquidInStructureBoundingBox(worldIn, structureBoundingBoxIn)) {
/*  817 */         return false;
/*      */       }
/*  819 */       fillWithRandomizedBlocks(worldIn, structureBoundingBoxIn, 0, 0, 0, 4, 4, 4, true, randomIn, StructureStrongholdPieces.strongholdStones);
/*  820 */       placeDoor(worldIn, randomIn, structureBoundingBoxIn, this.field_143013_d, 1, 1, 0);
/*      */       
/*  822 */       if (this.coordBaseMode != EnumFacing.NORTH && this.coordBaseMode != EnumFacing.EAST) {
/*  823 */         fillWithBlocks(worldIn, structureBoundingBoxIn, 0, 1, 1, 0, 3, 3, Blocks.air.getDefaultState(), Blocks.air.getDefaultState(), false);
/*      */       } else {
/*  825 */         fillWithBlocks(worldIn, structureBoundingBoxIn, 4, 1, 1, 4, 3, 3, Blocks.air.getDefaultState(), Blocks.air.getDefaultState(), false);
/*      */       } 
/*      */       
/*  828 */       return true;
/*      */     }
/*      */   }
/*      */   
/*      */   public static class RoomCrossing
/*      */     extends Stronghold {
/*  834 */     private static final List<WeightedRandomChestContent> strongholdRoomCrossingChestContents = Lists.newArrayList((Object[])new WeightedRandomChestContent[] { new WeightedRandomChestContent(Items.iron_ingot, 0, 1, 5, 10), new WeightedRandomChestContent(Items.gold_ingot, 0, 1, 3, 5), new WeightedRandomChestContent(Items.redstone, 0, 4, 9, 5), new WeightedRandomChestContent(Items.coal, 0, 3, 8, 10), new WeightedRandomChestContent(Items.bread, 0, 1, 3, 15), new WeightedRandomChestContent(Items.apple, 0, 1, 3, 15), new WeightedRandomChestContent(Items.iron_pickaxe, 0, 1, 1, 1) });
/*      */     
/*      */     protected int roomType;
/*      */     
/*      */     public RoomCrossing() {}
/*      */     
/*      */     public RoomCrossing(int p_i45575_1_, Random p_i45575_2_, StructureBoundingBox p_i45575_3_, EnumFacing p_i45575_4_) {
/*  841 */       super(p_i45575_1_);
/*  842 */       this.coordBaseMode = p_i45575_4_;
/*  843 */       this.field_143013_d = getRandomDoor(p_i45575_2_);
/*  844 */       this.boundingBox = p_i45575_3_;
/*  845 */       this.roomType = p_i45575_2_.nextInt(5);
/*      */     }
/*      */     
/*      */     protected void writeStructureToNBT(NBTTagCompound tagCompound) {
/*  849 */       super.writeStructureToNBT(tagCompound);
/*  850 */       tagCompound.setInteger("Type", this.roomType);
/*      */     }
/*      */     
/*      */     protected void readStructureFromNBT(NBTTagCompound tagCompound) {
/*  854 */       super.readStructureFromNBT(tagCompound);
/*  855 */       this.roomType = tagCompound.getInteger("Type");
/*      */     }
/*      */     
/*      */     public void buildComponent(StructureComponent componentIn, List<StructureComponent> listIn, Random rand) {
/*  859 */       getNextComponentNormal((StructureStrongholdPieces.Stairs2)componentIn, listIn, rand, 4, 1);
/*  860 */       getNextComponentX((StructureStrongholdPieces.Stairs2)componentIn, listIn, rand, 1, 4);
/*  861 */       getNextComponentZ((StructureStrongholdPieces.Stairs2)componentIn, listIn, rand, 1, 4);
/*      */     }
/*      */     
/*      */     public static RoomCrossing func_175859_a(List<StructureComponent> p_175859_0_, Random p_175859_1_, int p_175859_2_, int p_175859_3_, int p_175859_4_, EnumFacing p_175859_5_, int p_175859_6_) {
/*  865 */       StructureBoundingBox structureboundingbox = StructureBoundingBox.getComponentToAddBoundingBox(p_175859_2_, p_175859_3_, p_175859_4_, -4, -1, 0, 11, 7, 11, p_175859_5_);
/*  866 */       return (canStrongholdGoDeeper(structureboundingbox) && StructureComponent.findIntersecting(p_175859_0_, structureboundingbox) == null) ? new RoomCrossing(p_175859_6_, p_175859_1_, structureboundingbox, p_175859_5_) : null;
/*      */     }
/*      */     public boolean addComponentParts(World worldIn, Random randomIn, StructureBoundingBox structureBoundingBoxIn) {
/*      */       int i1, i, j, k, l;
/*  870 */       if (isLiquidInStructureBoundingBox(worldIn, structureBoundingBoxIn)) {
/*  871 */         return false;
/*      */       }
/*  873 */       fillWithRandomizedBlocks(worldIn, structureBoundingBoxIn, 0, 0, 0, 10, 6, 10, true, randomIn, StructureStrongholdPieces.strongholdStones);
/*  874 */       placeDoor(worldIn, randomIn, structureBoundingBoxIn, this.field_143013_d, 4, 1, 0);
/*  875 */       fillWithBlocks(worldIn, structureBoundingBoxIn, 4, 1, 10, 6, 3, 10, Blocks.air.getDefaultState(), Blocks.air.getDefaultState(), false);
/*  876 */       fillWithBlocks(worldIn, structureBoundingBoxIn, 0, 1, 4, 0, 3, 6, Blocks.air.getDefaultState(), Blocks.air.getDefaultState(), false);
/*  877 */       fillWithBlocks(worldIn, structureBoundingBoxIn, 10, 1, 4, 10, 3, 6, Blocks.air.getDefaultState(), Blocks.air.getDefaultState(), false);
/*      */       
/*  879 */       switch (this.roomType) {
/*      */         case 0:
/*  881 */           setBlockState(worldIn, Blocks.stonebrick.getDefaultState(), 5, 1, 5, structureBoundingBoxIn);
/*  882 */           setBlockState(worldIn, Blocks.stonebrick.getDefaultState(), 5, 2, 5, structureBoundingBoxIn);
/*  883 */           setBlockState(worldIn, Blocks.stonebrick.getDefaultState(), 5, 3, 5, structureBoundingBoxIn);
/*  884 */           setBlockState(worldIn, Blocks.torch.getDefaultState(), 4, 3, 5, structureBoundingBoxIn);
/*  885 */           setBlockState(worldIn, Blocks.torch.getDefaultState(), 6, 3, 5, structureBoundingBoxIn);
/*  886 */           setBlockState(worldIn, Blocks.torch.getDefaultState(), 5, 3, 4, structureBoundingBoxIn);
/*  887 */           setBlockState(worldIn, Blocks.torch.getDefaultState(), 5, 3, 6, structureBoundingBoxIn);
/*  888 */           setBlockState(worldIn, Blocks.stone_slab.getDefaultState(), 4, 1, 4, structureBoundingBoxIn);
/*  889 */           setBlockState(worldIn, Blocks.stone_slab.getDefaultState(), 4, 1, 5, structureBoundingBoxIn);
/*  890 */           setBlockState(worldIn, Blocks.stone_slab.getDefaultState(), 4, 1, 6, structureBoundingBoxIn);
/*  891 */           setBlockState(worldIn, Blocks.stone_slab.getDefaultState(), 6, 1, 4, structureBoundingBoxIn);
/*  892 */           setBlockState(worldIn, Blocks.stone_slab.getDefaultState(), 6, 1, 5, structureBoundingBoxIn);
/*  893 */           setBlockState(worldIn, Blocks.stone_slab.getDefaultState(), 6, 1, 6, structureBoundingBoxIn);
/*  894 */           setBlockState(worldIn, Blocks.stone_slab.getDefaultState(), 5, 1, 4, structureBoundingBoxIn);
/*  895 */           setBlockState(worldIn, Blocks.stone_slab.getDefaultState(), 5, 1, 6, structureBoundingBoxIn);
/*      */           break;
/*      */         
/*      */         case 1:
/*  899 */           for (i1 = 0; i1 < 5; i1++) {
/*  900 */             setBlockState(worldIn, Blocks.stonebrick.getDefaultState(), 3, 1, 3 + i1, structureBoundingBoxIn);
/*  901 */             setBlockState(worldIn, Blocks.stonebrick.getDefaultState(), 7, 1, 3 + i1, structureBoundingBoxIn);
/*  902 */             setBlockState(worldIn, Blocks.stonebrick.getDefaultState(), 3 + i1, 1, 3, structureBoundingBoxIn);
/*  903 */             setBlockState(worldIn, Blocks.stonebrick.getDefaultState(), 3 + i1, 1, 7, structureBoundingBoxIn);
/*      */           } 
/*      */           
/*  906 */           setBlockState(worldIn, Blocks.stonebrick.getDefaultState(), 5, 1, 5, structureBoundingBoxIn);
/*  907 */           setBlockState(worldIn, Blocks.stonebrick.getDefaultState(), 5, 2, 5, structureBoundingBoxIn);
/*  908 */           setBlockState(worldIn, Blocks.stonebrick.getDefaultState(), 5, 3, 5, structureBoundingBoxIn);
/*  909 */           setBlockState(worldIn, Blocks.flowing_water.getDefaultState(), 5, 4, 5, structureBoundingBoxIn);
/*      */           break;
/*      */         
/*      */         case 2:
/*  913 */           for (i = 1; i <= 9; i++) {
/*  914 */             setBlockState(worldIn, Blocks.cobblestone.getDefaultState(), 1, 3, i, structureBoundingBoxIn);
/*  915 */             setBlockState(worldIn, Blocks.cobblestone.getDefaultState(), 9, 3, i, structureBoundingBoxIn);
/*      */           } 
/*      */           
/*  918 */           for (j = 1; j <= 9; j++) {
/*  919 */             setBlockState(worldIn, Blocks.cobblestone.getDefaultState(), j, 3, 1, structureBoundingBoxIn);
/*  920 */             setBlockState(worldIn, Blocks.cobblestone.getDefaultState(), j, 3, 9, structureBoundingBoxIn);
/*      */           } 
/*      */           
/*  923 */           setBlockState(worldIn, Blocks.cobblestone.getDefaultState(), 5, 1, 4, structureBoundingBoxIn);
/*  924 */           setBlockState(worldIn, Blocks.cobblestone.getDefaultState(), 5, 1, 6, structureBoundingBoxIn);
/*  925 */           setBlockState(worldIn, Blocks.cobblestone.getDefaultState(), 5, 3, 4, structureBoundingBoxIn);
/*  926 */           setBlockState(worldIn, Blocks.cobblestone.getDefaultState(), 5, 3, 6, structureBoundingBoxIn);
/*  927 */           setBlockState(worldIn, Blocks.cobblestone.getDefaultState(), 4, 1, 5, structureBoundingBoxIn);
/*  928 */           setBlockState(worldIn, Blocks.cobblestone.getDefaultState(), 6, 1, 5, structureBoundingBoxIn);
/*  929 */           setBlockState(worldIn, Blocks.cobblestone.getDefaultState(), 4, 3, 5, structureBoundingBoxIn);
/*  930 */           setBlockState(worldIn, Blocks.cobblestone.getDefaultState(), 6, 3, 5, structureBoundingBoxIn);
/*      */           
/*  932 */           for (k = 1; k <= 3; k++) {
/*  933 */             setBlockState(worldIn, Blocks.cobblestone.getDefaultState(), 4, k, 4, structureBoundingBoxIn);
/*  934 */             setBlockState(worldIn, Blocks.cobblestone.getDefaultState(), 6, k, 4, structureBoundingBoxIn);
/*  935 */             setBlockState(worldIn, Blocks.cobblestone.getDefaultState(), 4, k, 6, structureBoundingBoxIn);
/*  936 */             setBlockState(worldIn, Blocks.cobblestone.getDefaultState(), 6, k, 6, structureBoundingBoxIn);
/*      */           } 
/*      */           
/*  939 */           setBlockState(worldIn, Blocks.torch.getDefaultState(), 5, 3, 5, structureBoundingBoxIn);
/*      */           
/*  941 */           for (l = 2; l <= 8; l++) {
/*  942 */             setBlockState(worldIn, Blocks.planks.getDefaultState(), 2, 3, l, structureBoundingBoxIn);
/*  943 */             setBlockState(worldIn, Blocks.planks.getDefaultState(), 3, 3, l, structureBoundingBoxIn);
/*      */             
/*  945 */             if (l <= 3 || l >= 7) {
/*  946 */               setBlockState(worldIn, Blocks.planks.getDefaultState(), 4, 3, l, structureBoundingBoxIn);
/*  947 */               setBlockState(worldIn, Blocks.planks.getDefaultState(), 5, 3, l, structureBoundingBoxIn);
/*  948 */               setBlockState(worldIn, Blocks.planks.getDefaultState(), 6, 3, l, structureBoundingBoxIn);
/*      */             } 
/*      */             
/*  951 */             setBlockState(worldIn, Blocks.planks.getDefaultState(), 7, 3, l, structureBoundingBoxIn);
/*  952 */             setBlockState(worldIn, Blocks.planks.getDefaultState(), 8, 3, l, structureBoundingBoxIn);
/*      */           } 
/*      */           
/*  955 */           setBlockState(worldIn, Blocks.ladder.getStateFromMeta(getMetadataWithOffset(Blocks.ladder, EnumFacing.WEST.getIndex())), 9, 1, 3, structureBoundingBoxIn);
/*  956 */           setBlockState(worldIn, Blocks.ladder.getStateFromMeta(getMetadataWithOffset(Blocks.ladder, EnumFacing.WEST.getIndex())), 9, 2, 3, structureBoundingBoxIn);
/*  957 */           setBlockState(worldIn, Blocks.ladder.getStateFromMeta(getMetadataWithOffset(Blocks.ladder, EnumFacing.WEST.getIndex())), 9, 3, 3, structureBoundingBoxIn);
/*  958 */           generateChestContents(worldIn, structureBoundingBoxIn, randomIn, 3, 4, 8, WeightedRandomChestContent.func_177629_a(strongholdRoomCrossingChestContents, new WeightedRandomChestContent[] { Items.enchanted_book.getRandom(randomIn) }), 1 + randomIn.nextInt(4));
/*      */           break;
/*      */       } 
/*  961 */       return true;
/*      */     }
/*      */   }
/*      */   
/*      */   public static class Stairs
/*      */     extends Stronghold
/*      */   {
/*      */     private boolean field_75024_a;
/*      */     
/*      */     public Stairs() {}
/*      */     
/*      */     public Stairs(int p_i2081_1_, Random p_i2081_2_, int p_i2081_3_, int p_i2081_4_) {
/*  973 */       super(p_i2081_1_);
/*  974 */       this.field_75024_a = true;
/*  975 */       this.coordBaseMode = EnumFacing.Plane.HORIZONTAL.random(p_i2081_2_);
/*  976 */       this.field_143013_d = StructureStrongholdPieces.Stronghold.Door.OPENING;
/*      */       
/*  978 */       switch (this.coordBaseMode) {
/*      */         case NORTH:
/*      */         case SOUTH:
/*  981 */           this.boundingBox = new StructureBoundingBox(p_i2081_3_, 64, p_i2081_4_, p_i2081_3_ + 5 - 1, 74, p_i2081_4_ + 5 - 1);
/*      */           return;
/*      */       } 
/*      */       
/*  985 */       this.boundingBox = new StructureBoundingBox(p_i2081_3_, 64, p_i2081_4_, p_i2081_3_ + 5 - 1, 74, p_i2081_4_ + 5 - 1);
/*      */     }
/*      */ 
/*      */     
/*      */     public Stairs(int p_i45574_1_, Random p_i45574_2_, StructureBoundingBox p_i45574_3_, EnumFacing p_i45574_4_) {
/*  990 */       super(p_i45574_1_);
/*  991 */       this.field_75024_a = false;
/*  992 */       this.coordBaseMode = p_i45574_4_;
/*  993 */       this.field_143013_d = getRandomDoor(p_i45574_2_);
/*  994 */       this.boundingBox = p_i45574_3_;
/*      */     }
/*      */     
/*      */     protected void writeStructureToNBT(NBTTagCompound tagCompound) {
/*  998 */       super.writeStructureToNBT(tagCompound);
/*  999 */       tagCompound.setBoolean("Source", this.field_75024_a);
/*      */     }
/*      */     
/*      */     protected void readStructureFromNBT(NBTTagCompound tagCompound) {
/* 1003 */       super.readStructureFromNBT(tagCompound);
/* 1004 */       this.field_75024_a = tagCompound.getBoolean("Source");
/*      */     }
/*      */     
/*      */     public void buildComponent(StructureComponent componentIn, List<StructureComponent> listIn, Random rand) {
/* 1008 */       if (this.field_75024_a) {
/* 1009 */         StructureStrongholdPieces.strongComponentType = (Class)StructureStrongholdPieces.Crossing.class;
/*      */       }
/*      */       
/* 1012 */       getNextComponentNormal((StructureStrongholdPieces.Stairs2)componentIn, listIn, rand, 1, 1);
/*      */     }
/*      */     
/*      */     public static Stairs func_175863_a(List<StructureComponent> p_175863_0_, Random p_175863_1_, int p_175863_2_, int p_175863_3_, int p_175863_4_, EnumFacing p_175863_5_, int p_175863_6_) {
/* 1016 */       StructureBoundingBox structureboundingbox = StructureBoundingBox.getComponentToAddBoundingBox(p_175863_2_, p_175863_3_, p_175863_4_, -1, -7, 0, 5, 11, 5, p_175863_5_);
/* 1017 */       return (canStrongholdGoDeeper(structureboundingbox) && StructureComponent.findIntersecting(p_175863_0_, structureboundingbox) == null) ? new Stairs(p_175863_6_, p_175863_1_, structureboundingbox, p_175863_5_) : null;
/*      */     }
/*      */     
/*      */     public boolean addComponentParts(World worldIn, Random randomIn, StructureBoundingBox structureBoundingBoxIn) {
/* 1021 */       if (isLiquidInStructureBoundingBox(worldIn, structureBoundingBoxIn)) {
/* 1022 */         return false;
/*      */       }
/* 1024 */       fillWithRandomizedBlocks(worldIn, structureBoundingBoxIn, 0, 0, 0, 4, 10, 4, true, randomIn, StructureStrongholdPieces.strongholdStones);
/* 1025 */       placeDoor(worldIn, randomIn, structureBoundingBoxIn, this.field_143013_d, 1, 7, 0);
/* 1026 */       placeDoor(worldIn, randomIn, structureBoundingBoxIn, StructureStrongholdPieces.Stronghold.Door.OPENING, 1, 1, 4);
/* 1027 */       setBlockState(worldIn, Blocks.stonebrick.getDefaultState(), 2, 6, 1, structureBoundingBoxIn);
/* 1028 */       setBlockState(worldIn, Blocks.stonebrick.getDefaultState(), 1, 5, 1, structureBoundingBoxIn);
/* 1029 */       setBlockState(worldIn, Blocks.stone_slab.getStateFromMeta(BlockStoneSlab.EnumType.STONE.getMetadata()), 1, 6, 1, structureBoundingBoxIn);
/* 1030 */       setBlockState(worldIn, Blocks.stonebrick.getDefaultState(), 1, 5, 2, structureBoundingBoxIn);
/* 1031 */       setBlockState(worldIn, Blocks.stonebrick.getDefaultState(), 1, 4, 3, structureBoundingBoxIn);
/* 1032 */       setBlockState(worldIn, Blocks.stone_slab.getStateFromMeta(BlockStoneSlab.EnumType.STONE.getMetadata()), 1, 5, 3, structureBoundingBoxIn);
/* 1033 */       setBlockState(worldIn, Blocks.stonebrick.getDefaultState(), 2, 4, 3, structureBoundingBoxIn);
/* 1034 */       setBlockState(worldIn, Blocks.stonebrick.getDefaultState(), 3, 3, 3, structureBoundingBoxIn);
/* 1035 */       setBlockState(worldIn, Blocks.stone_slab.getStateFromMeta(BlockStoneSlab.EnumType.STONE.getMetadata()), 3, 4, 3, structureBoundingBoxIn);
/* 1036 */       setBlockState(worldIn, Blocks.stonebrick.getDefaultState(), 3, 3, 2, structureBoundingBoxIn);
/* 1037 */       setBlockState(worldIn, Blocks.stonebrick.getDefaultState(), 3, 2, 1, structureBoundingBoxIn);
/* 1038 */       setBlockState(worldIn, Blocks.stone_slab.getStateFromMeta(BlockStoneSlab.EnumType.STONE.getMetadata()), 3, 3, 1, structureBoundingBoxIn);
/* 1039 */       setBlockState(worldIn, Blocks.stonebrick.getDefaultState(), 2, 2, 1, structureBoundingBoxIn);
/* 1040 */       setBlockState(worldIn, Blocks.stonebrick.getDefaultState(), 1, 1, 1, structureBoundingBoxIn);
/* 1041 */       setBlockState(worldIn, Blocks.stone_slab.getStateFromMeta(BlockStoneSlab.EnumType.STONE.getMetadata()), 1, 2, 1, structureBoundingBoxIn);
/* 1042 */       setBlockState(worldIn, Blocks.stonebrick.getDefaultState(), 1, 1, 2, structureBoundingBoxIn);
/* 1043 */       setBlockState(worldIn, Blocks.stone_slab.getStateFromMeta(BlockStoneSlab.EnumType.STONE.getMetadata()), 1, 1, 3, structureBoundingBoxIn);
/* 1044 */       return true;
/*      */     }
/*      */   }
/*      */   
/*      */   public static class Stairs2
/*      */     extends Stairs {
/*      */     public StructureStrongholdPieces.PieceWeight strongholdPieceWeight;
/*      */     public StructureStrongholdPieces.PortalRoom strongholdPortalRoom;
/* 1052 */     public List<StructureComponent> field_75026_c = Lists.newArrayList();
/*      */ 
/*      */     
/*      */     public Stairs2() {}
/*      */     
/*      */     public Stairs2(int p_i2083_1_, Random p_i2083_2_, int p_i2083_3_, int p_i2083_4_) {
/* 1058 */       super(0, p_i2083_2_, p_i2083_3_, p_i2083_4_);
/*      */     }
/*      */     
/*      */     public BlockPos getBoundingBoxCenter() {
/* 1062 */       return (this.strongholdPortalRoom != null) ? this.strongholdPortalRoom.getBoundingBoxCenter() : super.getBoundingBoxCenter();
/*      */     }
/*      */   }
/*      */   
/*      */   public static class StairsStraight
/*      */     extends Stronghold {
/*      */     public StairsStraight() {}
/*      */     
/*      */     public StairsStraight(int p_i45572_1_, Random p_i45572_2_, StructureBoundingBox p_i45572_3_, EnumFacing p_i45572_4_) {
/* 1071 */       super(p_i45572_1_);
/* 1072 */       this.coordBaseMode = p_i45572_4_;
/* 1073 */       this.field_143013_d = getRandomDoor(p_i45572_2_);
/* 1074 */       this.boundingBox = p_i45572_3_;
/*      */     }
/*      */     
/*      */     public void buildComponent(StructureComponent componentIn, List<StructureComponent> listIn, Random rand) {
/* 1078 */       getNextComponentNormal((StructureStrongholdPieces.Stairs2)componentIn, listIn, rand, 1, 1);
/*      */     }
/*      */     
/*      */     public static StairsStraight func_175861_a(List<StructureComponent> p_175861_0_, Random p_175861_1_, int p_175861_2_, int p_175861_3_, int p_175861_4_, EnumFacing p_175861_5_, int p_175861_6_) {
/* 1082 */       StructureBoundingBox structureboundingbox = StructureBoundingBox.getComponentToAddBoundingBox(p_175861_2_, p_175861_3_, p_175861_4_, -1, -7, 0, 5, 11, 8, p_175861_5_);
/* 1083 */       return (canStrongholdGoDeeper(structureboundingbox) && StructureComponent.findIntersecting(p_175861_0_, structureboundingbox) == null) ? new StairsStraight(p_175861_6_, p_175861_1_, structureboundingbox, p_175861_5_) : null;
/*      */     }
/*      */     
/*      */     public boolean addComponentParts(World worldIn, Random randomIn, StructureBoundingBox structureBoundingBoxIn) {
/* 1087 */       if (isLiquidInStructureBoundingBox(worldIn, structureBoundingBoxIn)) {
/* 1088 */         return false;
/*      */       }
/* 1090 */       fillWithRandomizedBlocks(worldIn, structureBoundingBoxIn, 0, 0, 0, 4, 10, 7, true, randomIn, StructureStrongholdPieces.strongholdStones);
/* 1091 */       placeDoor(worldIn, randomIn, structureBoundingBoxIn, this.field_143013_d, 1, 7, 0);
/* 1092 */       placeDoor(worldIn, randomIn, structureBoundingBoxIn, StructureStrongholdPieces.Stronghold.Door.OPENING, 1, 1, 7);
/* 1093 */       int i = getMetadataWithOffset(Blocks.stone_stairs, 2);
/*      */       
/* 1095 */       for (int j = 0; j < 6; j++) {
/* 1096 */         setBlockState(worldIn, Blocks.stone_stairs.getStateFromMeta(i), 1, 6 - j, 1 + j, structureBoundingBoxIn);
/* 1097 */         setBlockState(worldIn, Blocks.stone_stairs.getStateFromMeta(i), 2, 6 - j, 1 + j, structureBoundingBoxIn);
/* 1098 */         setBlockState(worldIn, Blocks.stone_stairs.getStateFromMeta(i), 3, 6 - j, 1 + j, structureBoundingBoxIn);
/*      */         
/* 1100 */         if (j < 5) {
/* 1101 */           setBlockState(worldIn, Blocks.stonebrick.getDefaultState(), 1, 5 - j, 1 + j, structureBoundingBoxIn);
/* 1102 */           setBlockState(worldIn, Blocks.stonebrick.getDefaultState(), 2, 5 - j, 1 + j, structureBoundingBoxIn);
/* 1103 */           setBlockState(worldIn, Blocks.stonebrick.getDefaultState(), 3, 5 - j, 1 + j, structureBoundingBoxIn);
/*      */         } 
/*      */       } 
/*      */       
/* 1107 */       return true;
/*      */     }
/*      */   }
/*      */   
/*      */   static class Stones
/*      */     extends StructureComponent.BlockSelector
/*      */   {
/*      */     private Stones() {}
/*      */     
/*      */     public void selectBlocks(Random rand, int x, int y, int z, boolean p_75062_5_) {
/* 1117 */       if (p_75062_5_) {
/* 1118 */         float f = rand.nextFloat();
/*      */         
/* 1120 */         if (f < 0.2F) {
/* 1121 */           this.blockstate = Blocks.stonebrick.getStateFromMeta(BlockStoneBrick.CRACKED_META);
/* 1122 */         } else if (f < 0.5F) {
/* 1123 */           this.blockstate = Blocks.stonebrick.getStateFromMeta(BlockStoneBrick.MOSSY_META);
/* 1124 */         } else if (f < 0.55F) {
/* 1125 */           this.blockstate = Blocks.monster_egg.getStateFromMeta(BlockSilverfish.EnumType.STONEBRICK.getMetadata());
/*      */         } else {
/* 1127 */           this.blockstate = Blocks.stonebrick.getDefaultState();
/*      */         } 
/*      */       } else {
/* 1130 */         this.blockstate = Blocks.air.getDefaultState();
/*      */       } 
/*      */     }
/*      */   }
/*      */   
/*      */   public static class Straight
/*      */     extends Stronghold {
/*      */     private boolean expandsX;
/*      */     private boolean expandsZ;
/*      */     
/*      */     public Straight() {}
/*      */     
/*      */     public Straight(int p_i45573_1_, Random p_i45573_2_, StructureBoundingBox p_i45573_3_, EnumFacing p_i45573_4_) {
/* 1143 */       super(p_i45573_1_);
/* 1144 */       this.coordBaseMode = p_i45573_4_;
/* 1145 */       this.field_143013_d = getRandomDoor(p_i45573_2_);
/* 1146 */       this.boundingBox = p_i45573_3_;
/* 1147 */       this.expandsX = (p_i45573_2_.nextInt(2) == 0);
/* 1148 */       this.expandsZ = (p_i45573_2_.nextInt(2) == 0);
/*      */     }
/*      */     
/*      */     protected void writeStructureToNBT(NBTTagCompound tagCompound) {
/* 1152 */       super.writeStructureToNBT(tagCompound);
/* 1153 */       tagCompound.setBoolean("Left", this.expandsX);
/* 1154 */       tagCompound.setBoolean("Right", this.expandsZ);
/*      */     }
/*      */     
/*      */     protected void readStructureFromNBT(NBTTagCompound tagCompound) {
/* 1158 */       super.readStructureFromNBT(tagCompound);
/* 1159 */       this.expandsX = tagCompound.getBoolean("Left");
/* 1160 */       this.expandsZ = tagCompound.getBoolean("Right");
/*      */     }
/*      */     
/*      */     public void buildComponent(StructureComponent componentIn, List<StructureComponent> listIn, Random rand) {
/* 1164 */       getNextComponentNormal((StructureStrongholdPieces.Stairs2)componentIn, listIn, rand, 1, 1);
/*      */       
/* 1166 */       if (this.expandsX) {
/* 1167 */         getNextComponentX((StructureStrongholdPieces.Stairs2)componentIn, listIn, rand, 1, 2);
/*      */       }
/*      */       
/* 1170 */       if (this.expandsZ) {
/* 1171 */         getNextComponentZ((StructureStrongholdPieces.Stairs2)componentIn, listIn, rand, 1, 2);
/*      */       }
/*      */     }
/*      */     
/*      */     public static Straight func_175862_a(List<StructureComponent> p_175862_0_, Random p_175862_1_, int p_175862_2_, int p_175862_3_, int p_175862_4_, EnumFacing p_175862_5_, int p_175862_6_) {
/* 1176 */       StructureBoundingBox structureboundingbox = StructureBoundingBox.getComponentToAddBoundingBox(p_175862_2_, p_175862_3_, p_175862_4_, -1, -1, 0, 5, 5, 7, p_175862_5_);
/* 1177 */       return (canStrongholdGoDeeper(structureboundingbox) && StructureComponent.findIntersecting(p_175862_0_, structureboundingbox) == null) ? new Straight(p_175862_6_, p_175862_1_, structureboundingbox, p_175862_5_) : null;
/*      */     }
/*      */     
/*      */     public boolean addComponentParts(World worldIn, Random randomIn, StructureBoundingBox structureBoundingBoxIn) {
/* 1181 */       if (isLiquidInStructureBoundingBox(worldIn, structureBoundingBoxIn)) {
/* 1182 */         return false;
/*      */       }
/* 1184 */       fillWithRandomizedBlocks(worldIn, structureBoundingBoxIn, 0, 0, 0, 4, 4, 6, true, randomIn, StructureStrongholdPieces.strongholdStones);
/* 1185 */       placeDoor(worldIn, randomIn, structureBoundingBoxIn, this.field_143013_d, 1, 1, 0);
/* 1186 */       placeDoor(worldIn, randomIn, structureBoundingBoxIn, StructureStrongholdPieces.Stronghold.Door.OPENING, 1, 1, 6);
/* 1187 */       randomlyPlaceBlock(worldIn, structureBoundingBoxIn, randomIn, 0.1F, 1, 2, 1, Blocks.torch.getDefaultState());
/* 1188 */       randomlyPlaceBlock(worldIn, structureBoundingBoxIn, randomIn, 0.1F, 3, 2, 1, Blocks.torch.getDefaultState());
/* 1189 */       randomlyPlaceBlock(worldIn, structureBoundingBoxIn, randomIn, 0.1F, 1, 2, 5, Blocks.torch.getDefaultState());
/* 1190 */       randomlyPlaceBlock(worldIn, structureBoundingBoxIn, randomIn, 0.1F, 3, 2, 5, Blocks.torch.getDefaultState());
/*      */       
/* 1192 */       if (this.expandsX) {
/* 1193 */         fillWithBlocks(worldIn, structureBoundingBoxIn, 0, 1, 2, 0, 3, 4, Blocks.air.getDefaultState(), Blocks.air.getDefaultState(), false);
/*      */       }
/*      */       
/* 1196 */       if (this.expandsZ) {
/* 1197 */         fillWithBlocks(worldIn, structureBoundingBoxIn, 4, 1, 2, 4, 3, 4, Blocks.air.getDefaultState(), Blocks.air.getDefaultState(), false);
/*      */       }
/*      */       
/* 1200 */       return true;
/*      */     }
/*      */   }
/*      */   
/*      */   static abstract class Stronghold
/*      */     extends StructureComponent {
/* 1206 */     protected Door field_143013_d = Door.OPENING;
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     protected Stronghold(int p_i2087_1_) {
/* 1212 */       super(p_i2087_1_);
/*      */     }
/*      */     
/*      */     protected void writeStructureToNBT(NBTTagCompound tagCompound) {
/* 1216 */       tagCompound.setString("EntryDoor", this.field_143013_d.name());
/*      */     }
/*      */     
/*      */     protected void readStructureFromNBT(NBTTagCompound tagCompound) {
/* 1220 */       this.field_143013_d = Door.valueOf(tagCompound.getString("EntryDoor"));
/*      */     }
/*      */     
/*      */     protected void placeDoor(World worldIn, Random p_74990_2_, StructureBoundingBox p_74990_3_, Door p_74990_4_, int p_74990_5_, int p_74990_6_, int p_74990_7_) {
/* 1224 */       switch (p_74990_4_) {
/*      */         
/*      */         default:
/* 1227 */           fillWithBlocks(worldIn, p_74990_3_, p_74990_5_, p_74990_6_, p_74990_7_, p_74990_5_ + 3 - 1, p_74990_6_ + 3 - 1, p_74990_7_, Blocks.air.getDefaultState(), Blocks.air.getDefaultState(), false);
/*      */           return;
/*      */         
/*      */         case WOOD_DOOR:
/* 1231 */           setBlockState(worldIn, Blocks.stonebrick.getDefaultState(), p_74990_5_, p_74990_6_, p_74990_7_, p_74990_3_);
/* 1232 */           setBlockState(worldIn, Blocks.stonebrick.getDefaultState(), p_74990_5_, p_74990_6_ + 1, p_74990_7_, p_74990_3_);
/* 1233 */           setBlockState(worldIn, Blocks.stonebrick.getDefaultState(), p_74990_5_, p_74990_6_ + 2, p_74990_7_, p_74990_3_);
/* 1234 */           setBlockState(worldIn, Blocks.stonebrick.getDefaultState(), p_74990_5_ + 1, p_74990_6_ + 2, p_74990_7_, p_74990_3_);
/* 1235 */           setBlockState(worldIn, Blocks.stonebrick.getDefaultState(), p_74990_5_ + 2, p_74990_6_ + 2, p_74990_7_, p_74990_3_);
/* 1236 */           setBlockState(worldIn, Blocks.stonebrick.getDefaultState(), p_74990_5_ + 2, p_74990_6_ + 1, p_74990_7_, p_74990_3_);
/* 1237 */           setBlockState(worldIn, Blocks.stonebrick.getDefaultState(), p_74990_5_ + 2, p_74990_6_, p_74990_7_, p_74990_3_);
/* 1238 */           setBlockState(worldIn, Blocks.oak_door.getDefaultState(), p_74990_5_ + 1, p_74990_6_, p_74990_7_, p_74990_3_);
/* 1239 */           setBlockState(worldIn, Blocks.oak_door.getStateFromMeta(8), p_74990_5_ + 1, p_74990_6_ + 1, p_74990_7_, p_74990_3_);
/*      */           return;
/*      */         
/*      */         case null:
/* 1243 */           setBlockState(worldIn, Blocks.air.getDefaultState(), p_74990_5_ + 1, p_74990_6_, p_74990_7_, p_74990_3_);
/* 1244 */           setBlockState(worldIn, Blocks.air.getDefaultState(), p_74990_5_ + 1, p_74990_6_ + 1, p_74990_7_, p_74990_3_);
/* 1245 */           setBlockState(worldIn, Blocks.iron_bars.getDefaultState(), p_74990_5_, p_74990_6_, p_74990_7_, p_74990_3_);
/* 1246 */           setBlockState(worldIn, Blocks.iron_bars.getDefaultState(), p_74990_5_, p_74990_6_ + 1, p_74990_7_, p_74990_3_);
/* 1247 */           setBlockState(worldIn, Blocks.iron_bars.getDefaultState(), p_74990_5_, p_74990_6_ + 2, p_74990_7_, p_74990_3_);
/* 1248 */           setBlockState(worldIn, Blocks.iron_bars.getDefaultState(), p_74990_5_ + 1, p_74990_6_ + 2, p_74990_7_, p_74990_3_);
/* 1249 */           setBlockState(worldIn, Blocks.iron_bars.getDefaultState(), p_74990_5_ + 2, p_74990_6_ + 2, p_74990_7_, p_74990_3_);
/* 1250 */           setBlockState(worldIn, Blocks.iron_bars.getDefaultState(), p_74990_5_ + 2, p_74990_6_ + 1, p_74990_7_, p_74990_3_);
/* 1251 */           setBlockState(worldIn, Blocks.iron_bars.getDefaultState(), p_74990_5_ + 2, p_74990_6_, p_74990_7_, p_74990_3_); return;
/*      */         case IRON_DOOR:
/*      */           break;
/*      */       } 
/* 1255 */       setBlockState(worldIn, Blocks.stonebrick.getDefaultState(), p_74990_5_, p_74990_6_, p_74990_7_, p_74990_3_);
/* 1256 */       setBlockState(worldIn, Blocks.stonebrick.getDefaultState(), p_74990_5_, p_74990_6_ + 1, p_74990_7_, p_74990_3_);
/* 1257 */       setBlockState(worldIn, Blocks.stonebrick.getDefaultState(), p_74990_5_, p_74990_6_ + 2, p_74990_7_, p_74990_3_);
/* 1258 */       setBlockState(worldIn, Blocks.stonebrick.getDefaultState(), p_74990_5_ + 1, p_74990_6_ + 2, p_74990_7_, p_74990_3_);
/* 1259 */       setBlockState(worldIn, Blocks.stonebrick.getDefaultState(), p_74990_5_ + 2, p_74990_6_ + 2, p_74990_7_, p_74990_3_);
/* 1260 */       setBlockState(worldIn, Blocks.stonebrick.getDefaultState(), p_74990_5_ + 2, p_74990_6_ + 1, p_74990_7_, p_74990_3_);
/* 1261 */       setBlockState(worldIn, Blocks.stonebrick.getDefaultState(), p_74990_5_ + 2, p_74990_6_, p_74990_7_, p_74990_3_);
/* 1262 */       setBlockState(worldIn, Blocks.iron_door.getDefaultState(), p_74990_5_ + 1, p_74990_6_, p_74990_7_, p_74990_3_);
/* 1263 */       setBlockState(worldIn, Blocks.iron_door.getStateFromMeta(8), p_74990_5_ + 1, p_74990_6_ + 1, p_74990_7_, p_74990_3_);
/* 1264 */       setBlockState(worldIn, Blocks.stone_button.getStateFromMeta(getMetadataWithOffset(Blocks.stone_button, 4)), p_74990_5_ + 2, p_74990_6_ + 1, p_74990_7_ + 1, p_74990_3_);
/* 1265 */       setBlockState(worldIn, Blocks.stone_button.getStateFromMeta(getMetadataWithOffset(Blocks.stone_button, 3)), p_74990_5_ + 2, p_74990_6_ + 1, p_74990_7_ - 1, p_74990_3_);
/*      */     }
/*      */ 
/*      */     
/*      */     protected Door getRandomDoor(Random p_74988_1_) {
/* 1270 */       int i = p_74988_1_.nextInt(5);
/*      */       
/* 1272 */       switch (i) {
/*      */ 
/*      */         
/*      */         default:
/* 1276 */           return Door.OPENING;
/*      */         
/*      */         case 2:
/* 1279 */           return Door.WOOD_DOOR;
/*      */         
/*      */         case 3:
/* 1282 */           return Door.GRATES;
/*      */         case 4:
/*      */           break;
/* 1285 */       }  return Door.IRON_DOOR;
/*      */     }
/*      */ 
/*      */     
/*      */     protected StructureComponent getNextComponentNormal(StructureStrongholdPieces.Stairs2 p_74986_1_, List<StructureComponent> p_74986_2_, Random p_74986_3_, int p_74986_4_, int p_74986_5_) {
/* 1290 */       if (this.coordBaseMode != null) {
/* 1291 */         switch (this.coordBaseMode) {
/*      */           case NORTH:
/* 1293 */             return StructureStrongholdPieces.func_175953_c(p_74986_1_, p_74986_2_, p_74986_3_, this.boundingBox.minX + p_74986_4_, this.boundingBox.minY + p_74986_5_, this.boundingBox.minZ - 1, this.coordBaseMode, getComponentType());
/*      */           
/*      */           case SOUTH:
/* 1296 */             return StructureStrongholdPieces.func_175953_c(p_74986_1_, p_74986_2_, p_74986_3_, this.boundingBox.minX + p_74986_4_, this.boundingBox.minY + p_74986_5_, this.boundingBox.maxZ + 1, this.coordBaseMode, getComponentType());
/*      */           
/*      */           case WEST:
/* 1299 */             return StructureStrongholdPieces.func_175953_c(p_74986_1_, p_74986_2_, p_74986_3_, this.boundingBox.minX - 1, this.boundingBox.minY + p_74986_5_, this.boundingBox.minZ + p_74986_4_, this.coordBaseMode, getComponentType());
/*      */           
/*      */           case EAST:
/* 1302 */             return StructureStrongholdPieces.func_175953_c(p_74986_1_, p_74986_2_, p_74986_3_, this.boundingBox.maxX + 1, this.boundingBox.minY + p_74986_5_, this.boundingBox.minZ + p_74986_4_, this.coordBaseMode, getComponentType());
/*      */         } 
/*      */       
/*      */       }
/* 1306 */       return null;
/*      */     }
/*      */     
/*      */     protected StructureComponent getNextComponentX(StructureStrongholdPieces.Stairs2 p_74989_1_, List<StructureComponent> p_74989_2_, Random p_74989_3_, int p_74989_4_, int p_74989_5_) {
/* 1310 */       if (this.coordBaseMode != null) {
/* 1311 */         switch (this.coordBaseMode) {
/*      */           case NORTH:
/* 1313 */             return StructureStrongholdPieces.func_175953_c(p_74989_1_, p_74989_2_, p_74989_3_, this.boundingBox.minX - 1, this.boundingBox.minY + p_74989_4_, this.boundingBox.minZ + p_74989_5_, EnumFacing.WEST, getComponentType());
/*      */           
/*      */           case SOUTH:
/* 1316 */             return StructureStrongholdPieces.func_175953_c(p_74989_1_, p_74989_2_, p_74989_3_, this.boundingBox.minX - 1, this.boundingBox.minY + p_74989_4_, this.boundingBox.minZ + p_74989_5_, EnumFacing.WEST, getComponentType());
/*      */           
/*      */           case WEST:
/* 1319 */             return StructureStrongholdPieces.func_175953_c(p_74989_1_, p_74989_2_, p_74989_3_, this.boundingBox.minX + p_74989_5_, this.boundingBox.minY + p_74989_4_, this.boundingBox.minZ - 1, EnumFacing.NORTH, getComponentType());
/*      */           
/*      */           case EAST:
/* 1322 */             return StructureStrongholdPieces.func_175953_c(p_74989_1_, p_74989_2_, p_74989_3_, this.boundingBox.minX + p_74989_5_, this.boundingBox.minY + p_74989_4_, this.boundingBox.minZ - 1, EnumFacing.NORTH, getComponentType());
/*      */         } 
/*      */       
/*      */       }
/* 1326 */       return null;
/*      */     }
/*      */     
/*      */     protected StructureComponent getNextComponentZ(StructureStrongholdPieces.Stairs2 p_74987_1_, List<StructureComponent> p_74987_2_, Random p_74987_3_, int p_74987_4_, int p_74987_5_) {
/* 1330 */       if (this.coordBaseMode != null) {
/* 1331 */         switch (this.coordBaseMode) {
/*      */           case NORTH:
/* 1333 */             return StructureStrongholdPieces.func_175953_c(p_74987_1_, p_74987_2_, p_74987_3_, this.boundingBox.maxX + 1, this.boundingBox.minY + p_74987_4_, this.boundingBox.minZ + p_74987_5_, EnumFacing.EAST, getComponentType());
/*      */           
/*      */           case SOUTH:
/* 1336 */             return StructureStrongholdPieces.func_175953_c(p_74987_1_, p_74987_2_, p_74987_3_, this.boundingBox.maxX + 1, this.boundingBox.minY + p_74987_4_, this.boundingBox.minZ + p_74987_5_, EnumFacing.EAST, getComponentType());
/*      */           
/*      */           case WEST:
/* 1339 */             return StructureStrongholdPieces.func_175953_c(p_74987_1_, p_74987_2_, p_74987_3_, this.boundingBox.minX + p_74987_5_, this.boundingBox.minY + p_74987_4_, this.boundingBox.maxZ + 1, EnumFacing.SOUTH, getComponentType());
/*      */           
/*      */           case EAST:
/* 1342 */             return StructureStrongholdPieces.func_175953_c(p_74987_1_, p_74987_2_, p_74987_3_, this.boundingBox.minX + p_74987_5_, this.boundingBox.minY + p_74987_4_, this.boundingBox.maxZ + 1, EnumFacing.SOUTH, getComponentType());
/*      */         } 
/*      */       
/*      */       }
/* 1346 */       return null;
/*      */     }
/*      */     
/*      */     protected static boolean canStrongholdGoDeeper(StructureBoundingBox p_74991_0_) {
/* 1350 */       return (p_74991_0_ != null && p_74991_0_.minY > 10);
/*      */     }
/*      */     public Stronghold() {}
/*      */     
/* 1354 */     public enum Door { OPENING,
/* 1355 */       WOOD_DOOR,
/* 1356 */       GRATES,
/* 1357 */       IRON_DOOR; }
/*      */   
/*      */   }
/*      */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\minecraft\world\gen\structure\StructureStrongholdPieces.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */