/*      */ package net.minecraft.world.gen.structure;
/*      */ 
/*      */ import com.google.common.collect.Lists;
/*      */ import java.util.List;
/*      */ import java.util.Random;
/*      */ import net.minecraft.block.Block;
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
/*      */ public class StructureNetherBridgePieces
/*      */ {
/*   21 */   private static final PieceWeight[] primaryComponents = new PieceWeight[] { new PieceWeight((Class)Straight.class, 30, 0, true), new PieceWeight((Class)Crossing3.class, 10, 4), new PieceWeight((Class)Crossing.class, 10, 4), new PieceWeight((Class)Stairs.class, 10, 3), new PieceWeight((Class)Throne.class, 5, 2), new PieceWeight((Class)Entrance.class, 5, 1) };
/*   22 */   private static final PieceWeight[] secondaryComponents = new PieceWeight[] { new PieceWeight((Class)Corridor5.class, 25, 0, true), new PieceWeight((Class)Crossing2.class, 15, 5), new PieceWeight((Class)Corridor2.class, 5, 10), new PieceWeight((Class)Corridor.class, 5, 10), new PieceWeight((Class)Corridor3.class, 10, 3, true), new PieceWeight((Class)Corridor4.class, 7, 2), new PieceWeight((Class)NetherStalkRoom.class, 5, 2) };
/*      */   
/*      */   public static void registerNetherFortressPieces() {
/*   25 */     MapGenStructureIO.registerStructureComponent((Class)Crossing3.class, "NeBCr");
/*   26 */     MapGenStructureIO.registerStructureComponent((Class)End.class, "NeBEF");
/*   27 */     MapGenStructureIO.registerStructureComponent((Class)Straight.class, "NeBS");
/*   28 */     MapGenStructureIO.registerStructureComponent((Class)Corridor3.class, "NeCCS");
/*   29 */     MapGenStructureIO.registerStructureComponent((Class)Corridor4.class, "NeCTB");
/*   30 */     MapGenStructureIO.registerStructureComponent((Class)Entrance.class, "NeCE");
/*   31 */     MapGenStructureIO.registerStructureComponent((Class)Crossing2.class, "NeSCSC");
/*   32 */     MapGenStructureIO.registerStructureComponent((Class)Corridor.class, "NeSCLT");
/*   33 */     MapGenStructureIO.registerStructureComponent((Class)Corridor5.class, "NeSC");
/*   34 */     MapGenStructureIO.registerStructureComponent((Class)Corridor2.class, "NeSCRT");
/*   35 */     MapGenStructureIO.registerStructureComponent((Class)NetherStalkRoom.class, "NeCSR");
/*   36 */     MapGenStructureIO.registerStructureComponent((Class)Throne.class, "NeMT");
/*   37 */     MapGenStructureIO.registerStructureComponent((Class)Crossing.class, "NeRC");
/*   38 */     MapGenStructureIO.registerStructureComponent((Class)Stairs.class, "NeSR");
/*   39 */     MapGenStructureIO.registerStructureComponent((Class)Start.class, "NeStart");
/*      */   }
/*      */   
/*      */   private static Piece func_175887_b(PieceWeight p_175887_0_, List<StructureComponent> p_175887_1_, Random p_175887_2_, int p_175887_3_, int p_175887_4_, int p_175887_5_, EnumFacing p_175887_6_, int p_175887_7_) {
/*   43 */     Class<? extends Piece> oclass = p_175887_0_.weightClass;
/*   44 */     Piece structurenetherbridgepieces$piece = null;
/*      */     
/*   46 */     if (oclass == Straight.class) {
/*   47 */       structurenetherbridgepieces$piece = Straight.func_175882_a(p_175887_1_, p_175887_2_, p_175887_3_, p_175887_4_, p_175887_5_, p_175887_6_, p_175887_7_);
/*   48 */     } else if (oclass == Crossing3.class) {
/*   49 */       structurenetherbridgepieces$piece = Crossing3.func_175885_a(p_175887_1_, p_175887_2_, p_175887_3_, p_175887_4_, p_175887_5_, p_175887_6_, p_175887_7_);
/*   50 */     } else if (oclass == Crossing.class) {
/*   51 */       structurenetherbridgepieces$piece = Crossing.func_175873_a(p_175887_1_, p_175887_2_, p_175887_3_, p_175887_4_, p_175887_5_, p_175887_6_, p_175887_7_);
/*   52 */     } else if (oclass == Stairs.class) {
/*   53 */       structurenetherbridgepieces$piece = Stairs.func_175872_a(p_175887_1_, p_175887_2_, p_175887_3_, p_175887_4_, p_175887_5_, p_175887_7_, p_175887_6_);
/*   54 */     } else if (oclass == Throne.class) {
/*   55 */       structurenetherbridgepieces$piece = Throne.func_175874_a(p_175887_1_, p_175887_2_, p_175887_3_, p_175887_4_, p_175887_5_, p_175887_7_, p_175887_6_);
/*   56 */     } else if (oclass == Entrance.class) {
/*   57 */       structurenetherbridgepieces$piece = Entrance.func_175881_a(p_175887_1_, p_175887_2_, p_175887_3_, p_175887_4_, p_175887_5_, p_175887_6_, p_175887_7_);
/*   58 */     } else if (oclass == Corridor5.class) {
/*   59 */       structurenetherbridgepieces$piece = Corridor5.func_175877_a(p_175887_1_, p_175887_2_, p_175887_3_, p_175887_4_, p_175887_5_, p_175887_6_, p_175887_7_);
/*   60 */     } else if (oclass == Corridor2.class) {
/*   61 */       structurenetherbridgepieces$piece = Corridor2.func_175876_a(p_175887_1_, p_175887_2_, p_175887_3_, p_175887_4_, p_175887_5_, p_175887_6_, p_175887_7_);
/*   62 */     } else if (oclass == Corridor.class) {
/*   63 */       structurenetherbridgepieces$piece = Corridor.func_175879_a(p_175887_1_, p_175887_2_, p_175887_3_, p_175887_4_, p_175887_5_, p_175887_6_, p_175887_7_);
/*   64 */     } else if (oclass == Corridor3.class) {
/*   65 */       structurenetherbridgepieces$piece = Corridor3.func_175883_a(p_175887_1_, p_175887_2_, p_175887_3_, p_175887_4_, p_175887_5_, p_175887_6_, p_175887_7_);
/*   66 */     } else if (oclass == Corridor4.class) {
/*   67 */       structurenetherbridgepieces$piece = Corridor4.func_175880_a(p_175887_1_, p_175887_2_, p_175887_3_, p_175887_4_, p_175887_5_, p_175887_6_, p_175887_7_);
/*   68 */     } else if (oclass == Crossing2.class) {
/*   69 */       structurenetherbridgepieces$piece = Crossing2.func_175878_a(p_175887_1_, p_175887_2_, p_175887_3_, p_175887_4_, p_175887_5_, p_175887_6_, p_175887_7_);
/*   70 */     } else if (oclass == NetherStalkRoom.class) {
/*   71 */       structurenetherbridgepieces$piece = NetherStalkRoom.func_175875_a(p_175887_1_, p_175887_2_, p_175887_3_, p_175887_4_, p_175887_5_, p_175887_6_, p_175887_7_);
/*      */     } 
/*      */     
/*   74 */     return structurenetherbridgepieces$piece;
/*      */   }
/*      */   
/*      */   public static class Corridor
/*      */     extends Piece {
/*      */     private boolean field_111021_b;
/*      */     
/*      */     public Corridor() {}
/*      */     
/*      */     public Corridor(int p_i45615_1_, Random p_i45615_2_, StructureBoundingBox p_i45615_3_, EnumFacing p_i45615_4_) {
/*   84 */       super(p_i45615_1_);
/*   85 */       this.coordBaseMode = p_i45615_4_;
/*   86 */       this.boundingBox = p_i45615_3_;
/*   87 */       this.field_111021_b = (p_i45615_2_.nextInt(3) == 0);
/*      */     }
/*      */     
/*      */     protected void readStructureFromNBT(NBTTagCompound tagCompound) {
/*   91 */       super.readStructureFromNBT(tagCompound);
/*   92 */       this.field_111021_b = tagCompound.getBoolean("Chest");
/*      */     }
/*      */     
/*      */     protected void writeStructureToNBT(NBTTagCompound tagCompound) {
/*   96 */       super.writeStructureToNBT(tagCompound);
/*   97 */       tagCompound.setBoolean("Chest", this.field_111021_b);
/*      */     }
/*      */     
/*      */     public void buildComponent(StructureComponent componentIn, List<StructureComponent> listIn, Random rand) {
/*  101 */       getNextComponentX((StructureNetherBridgePieces.Start)componentIn, listIn, rand, 0, 1, true);
/*      */     }
/*      */     
/*      */     public static Corridor func_175879_a(List<StructureComponent> p_175879_0_, Random p_175879_1_, int p_175879_2_, int p_175879_3_, int p_175879_4_, EnumFacing p_175879_5_, int p_175879_6_) {
/*  105 */       StructureBoundingBox structureboundingbox = StructureBoundingBox.getComponentToAddBoundingBox(p_175879_2_, p_175879_3_, p_175879_4_, -1, 0, 0, 5, 7, 5, p_175879_5_);
/*  106 */       return (isAboveGround(structureboundingbox) && StructureComponent.findIntersecting(p_175879_0_, structureboundingbox) == null) ? new Corridor(p_175879_6_, p_175879_1_, structureboundingbox, p_175879_5_) : null;
/*      */     }
/*      */     
/*      */     public boolean addComponentParts(World worldIn, Random randomIn, StructureBoundingBox structureBoundingBoxIn) {
/*  110 */       fillWithBlocks(worldIn, structureBoundingBoxIn, 0, 0, 0, 4, 1, 4, Blocks.nether_brick.getDefaultState(), Blocks.nether_brick.getDefaultState(), false);
/*  111 */       fillWithBlocks(worldIn, structureBoundingBoxIn, 0, 2, 0, 4, 5, 4, Blocks.air.getDefaultState(), Blocks.air.getDefaultState(), false);
/*  112 */       fillWithBlocks(worldIn, structureBoundingBoxIn, 4, 2, 0, 4, 5, 4, Blocks.nether_brick.getDefaultState(), Blocks.nether_brick.getDefaultState(), false);
/*  113 */       fillWithBlocks(worldIn, structureBoundingBoxIn, 4, 3, 1, 4, 4, 1, Blocks.nether_brick_fence.getDefaultState(), Blocks.nether_brick_fence.getDefaultState(), false);
/*  114 */       fillWithBlocks(worldIn, structureBoundingBoxIn, 4, 3, 3, 4, 4, 3, Blocks.nether_brick_fence.getDefaultState(), Blocks.nether_brick_fence.getDefaultState(), false);
/*  115 */       fillWithBlocks(worldIn, structureBoundingBoxIn, 0, 2, 0, 0, 5, 0, Blocks.nether_brick.getDefaultState(), Blocks.nether_brick.getDefaultState(), false);
/*  116 */       fillWithBlocks(worldIn, structureBoundingBoxIn, 0, 2, 4, 3, 5, 4, Blocks.nether_brick.getDefaultState(), Blocks.nether_brick.getDefaultState(), false);
/*  117 */       fillWithBlocks(worldIn, structureBoundingBoxIn, 1, 3, 4, 1, 4, 4, Blocks.nether_brick_fence.getDefaultState(), Blocks.nether_brick.getDefaultState(), false);
/*  118 */       fillWithBlocks(worldIn, structureBoundingBoxIn, 3, 3, 4, 3, 4, 4, Blocks.nether_brick_fence.getDefaultState(), Blocks.nether_brick.getDefaultState(), false);
/*      */       
/*  120 */       if (this.field_111021_b && structureBoundingBoxIn.isVecInside((Vec3i)new BlockPos(getXWithOffset(3, 3), getYWithOffset(2), getZWithOffset(3, 3)))) {
/*  121 */         this.field_111021_b = false;
/*  122 */         generateChestContents(worldIn, structureBoundingBoxIn, randomIn, 3, 2, 3, field_111019_a, 2 + randomIn.nextInt(4));
/*      */       } 
/*      */       
/*  125 */       fillWithBlocks(worldIn, structureBoundingBoxIn, 0, 6, 0, 4, 6, 4, Blocks.nether_brick.getDefaultState(), Blocks.nether_brick.getDefaultState(), false);
/*      */       
/*  127 */       for (int i = 0; i <= 4; i++) {
/*  128 */         for (int j = 0; j <= 4; j++) {
/*  129 */           replaceAirAndLiquidDownwards(worldIn, Blocks.nether_brick.getDefaultState(), i, -1, j, structureBoundingBoxIn);
/*      */         }
/*      */       } 
/*      */       
/*  133 */       return true;
/*      */     }
/*      */   }
/*      */   
/*      */   public static class Corridor2
/*      */     extends Piece {
/*      */     private boolean field_111020_b;
/*      */     
/*      */     public Corridor2() {}
/*      */     
/*      */     public Corridor2(int p_i45613_1_, Random p_i45613_2_, StructureBoundingBox p_i45613_3_, EnumFacing p_i45613_4_) {
/*  144 */       super(p_i45613_1_);
/*  145 */       this.coordBaseMode = p_i45613_4_;
/*  146 */       this.boundingBox = p_i45613_3_;
/*  147 */       this.field_111020_b = (p_i45613_2_.nextInt(3) == 0);
/*      */     }
/*      */     
/*      */     protected void readStructureFromNBT(NBTTagCompound tagCompound) {
/*  151 */       super.readStructureFromNBT(tagCompound);
/*  152 */       this.field_111020_b = tagCompound.getBoolean("Chest");
/*      */     }
/*      */     
/*      */     protected void writeStructureToNBT(NBTTagCompound tagCompound) {
/*  156 */       super.writeStructureToNBT(tagCompound);
/*  157 */       tagCompound.setBoolean("Chest", this.field_111020_b);
/*      */     }
/*      */     
/*      */     public void buildComponent(StructureComponent componentIn, List<StructureComponent> listIn, Random rand) {
/*  161 */       getNextComponentZ((StructureNetherBridgePieces.Start)componentIn, listIn, rand, 0, 1, true);
/*      */     }
/*      */     
/*      */     public static Corridor2 func_175876_a(List<StructureComponent> p_175876_0_, Random p_175876_1_, int p_175876_2_, int p_175876_3_, int p_175876_4_, EnumFacing p_175876_5_, int p_175876_6_) {
/*  165 */       StructureBoundingBox structureboundingbox = StructureBoundingBox.getComponentToAddBoundingBox(p_175876_2_, p_175876_3_, p_175876_4_, -1, 0, 0, 5, 7, 5, p_175876_5_);
/*  166 */       return (isAboveGround(structureboundingbox) && StructureComponent.findIntersecting(p_175876_0_, structureboundingbox) == null) ? new Corridor2(p_175876_6_, p_175876_1_, structureboundingbox, p_175876_5_) : null;
/*      */     }
/*      */     
/*      */     public boolean addComponentParts(World worldIn, Random randomIn, StructureBoundingBox structureBoundingBoxIn) {
/*  170 */       fillWithBlocks(worldIn, structureBoundingBoxIn, 0, 0, 0, 4, 1, 4, Blocks.nether_brick.getDefaultState(), Blocks.nether_brick.getDefaultState(), false);
/*  171 */       fillWithBlocks(worldIn, structureBoundingBoxIn, 0, 2, 0, 4, 5, 4, Blocks.air.getDefaultState(), Blocks.air.getDefaultState(), false);
/*  172 */       fillWithBlocks(worldIn, structureBoundingBoxIn, 0, 2, 0, 0, 5, 4, Blocks.nether_brick.getDefaultState(), Blocks.nether_brick.getDefaultState(), false);
/*  173 */       fillWithBlocks(worldIn, structureBoundingBoxIn, 0, 3, 1, 0, 4, 1, Blocks.nether_brick_fence.getDefaultState(), Blocks.nether_brick_fence.getDefaultState(), false);
/*  174 */       fillWithBlocks(worldIn, structureBoundingBoxIn, 0, 3, 3, 0, 4, 3, Blocks.nether_brick_fence.getDefaultState(), Blocks.nether_brick_fence.getDefaultState(), false);
/*  175 */       fillWithBlocks(worldIn, structureBoundingBoxIn, 4, 2, 0, 4, 5, 0, Blocks.nether_brick.getDefaultState(), Blocks.nether_brick.getDefaultState(), false);
/*  176 */       fillWithBlocks(worldIn, structureBoundingBoxIn, 1, 2, 4, 4, 5, 4, Blocks.nether_brick.getDefaultState(), Blocks.nether_brick.getDefaultState(), false);
/*  177 */       fillWithBlocks(worldIn, structureBoundingBoxIn, 1, 3, 4, 1, 4, 4, Blocks.nether_brick_fence.getDefaultState(), Blocks.nether_brick.getDefaultState(), false);
/*  178 */       fillWithBlocks(worldIn, structureBoundingBoxIn, 3, 3, 4, 3, 4, 4, Blocks.nether_brick_fence.getDefaultState(), Blocks.nether_brick.getDefaultState(), false);
/*      */       
/*  180 */       if (this.field_111020_b && structureBoundingBoxIn.isVecInside((Vec3i)new BlockPos(getXWithOffset(1, 3), getYWithOffset(2), getZWithOffset(1, 3)))) {
/*  181 */         this.field_111020_b = false;
/*  182 */         generateChestContents(worldIn, structureBoundingBoxIn, randomIn, 1, 2, 3, field_111019_a, 2 + randomIn.nextInt(4));
/*      */       } 
/*      */       
/*  185 */       fillWithBlocks(worldIn, structureBoundingBoxIn, 0, 6, 0, 4, 6, 4, Blocks.nether_brick.getDefaultState(), Blocks.nether_brick.getDefaultState(), false);
/*      */       
/*  187 */       for (int i = 0; i <= 4; i++) {
/*  188 */         for (int j = 0; j <= 4; j++) {
/*  189 */           replaceAirAndLiquidDownwards(worldIn, Blocks.nether_brick.getDefaultState(), i, -1, j, structureBoundingBoxIn);
/*      */         }
/*      */       } 
/*      */       
/*  193 */       return true;
/*      */     }
/*      */   }
/*      */   
/*      */   public static class Corridor3
/*      */     extends Piece {
/*      */     public Corridor3() {}
/*      */     
/*      */     public Corridor3(int p_i45619_1_, Random p_i45619_2_, StructureBoundingBox p_i45619_3_, EnumFacing p_i45619_4_) {
/*  202 */       super(p_i45619_1_);
/*  203 */       this.coordBaseMode = p_i45619_4_;
/*  204 */       this.boundingBox = p_i45619_3_;
/*      */     }
/*      */     
/*      */     public void buildComponent(StructureComponent componentIn, List<StructureComponent> listIn, Random rand) {
/*  208 */       getNextComponentNormal((StructureNetherBridgePieces.Start)componentIn, listIn, rand, 1, 0, true);
/*      */     }
/*      */     
/*      */     public static Corridor3 func_175883_a(List<StructureComponent> p_175883_0_, Random p_175883_1_, int p_175883_2_, int p_175883_3_, int p_175883_4_, EnumFacing p_175883_5_, int p_175883_6_) {
/*  212 */       StructureBoundingBox structureboundingbox = StructureBoundingBox.getComponentToAddBoundingBox(p_175883_2_, p_175883_3_, p_175883_4_, -1, -7, 0, 5, 14, 10, p_175883_5_);
/*  213 */       return (isAboveGround(structureboundingbox) && StructureComponent.findIntersecting(p_175883_0_, structureboundingbox) == null) ? new Corridor3(p_175883_6_, p_175883_1_, structureboundingbox, p_175883_5_) : null;
/*      */     }
/*      */     
/*      */     public boolean addComponentParts(World worldIn, Random randomIn, StructureBoundingBox structureBoundingBoxIn) {
/*  217 */       int i = getMetadataWithOffset(Blocks.nether_brick_stairs, 2);
/*      */       
/*  219 */       for (int j = 0; j <= 9; j++) {
/*  220 */         int k = Math.max(1, 7 - j);
/*  221 */         int l = Math.min(Math.max(k + 5, 14 - j), 13);
/*  222 */         int i1 = j;
/*  223 */         fillWithBlocks(worldIn, structureBoundingBoxIn, 0, 0, j, 4, k, j, Blocks.nether_brick.getDefaultState(), Blocks.nether_brick.getDefaultState(), false);
/*  224 */         fillWithBlocks(worldIn, structureBoundingBoxIn, 1, k + 1, j, 3, l - 1, j, Blocks.air.getDefaultState(), Blocks.air.getDefaultState(), false);
/*      */         
/*  226 */         if (j <= 6) {
/*  227 */           setBlockState(worldIn, Blocks.nether_brick_stairs.getStateFromMeta(i), 1, k + 1, j, structureBoundingBoxIn);
/*  228 */           setBlockState(worldIn, Blocks.nether_brick_stairs.getStateFromMeta(i), 2, k + 1, j, structureBoundingBoxIn);
/*  229 */           setBlockState(worldIn, Blocks.nether_brick_stairs.getStateFromMeta(i), 3, k + 1, j, structureBoundingBoxIn);
/*      */         } 
/*      */         
/*  232 */         fillWithBlocks(worldIn, structureBoundingBoxIn, 0, l, j, 4, l, j, Blocks.nether_brick.getDefaultState(), Blocks.nether_brick.getDefaultState(), false);
/*  233 */         fillWithBlocks(worldIn, structureBoundingBoxIn, 0, k + 1, j, 0, l - 1, j, Blocks.nether_brick.getDefaultState(), Blocks.nether_brick.getDefaultState(), false);
/*  234 */         fillWithBlocks(worldIn, structureBoundingBoxIn, 4, k + 1, j, 4, l - 1, j, Blocks.nether_brick.getDefaultState(), Blocks.nether_brick.getDefaultState(), false);
/*      */         
/*  236 */         if ((j & 0x1) == 0) {
/*  237 */           fillWithBlocks(worldIn, structureBoundingBoxIn, 0, k + 2, j, 0, k + 3, j, Blocks.nether_brick_fence.getDefaultState(), Blocks.nether_brick_fence.getDefaultState(), false);
/*  238 */           fillWithBlocks(worldIn, structureBoundingBoxIn, 4, k + 2, j, 4, k + 3, j, Blocks.nether_brick_fence.getDefaultState(), Blocks.nether_brick_fence.getDefaultState(), false);
/*      */         } 
/*      */         
/*  241 */         for (int j1 = 0; j1 <= 4; j1++) {
/*  242 */           replaceAirAndLiquidDownwards(worldIn, Blocks.nether_brick.getDefaultState(), j1, -1, i1, structureBoundingBoxIn);
/*      */         }
/*      */       } 
/*      */       
/*  246 */       return true;
/*      */     }
/*      */   }
/*      */   
/*      */   public static class Corridor4
/*      */     extends Piece {
/*      */     public Corridor4() {}
/*      */     
/*      */     public Corridor4(int p_i45618_1_, Random p_i45618_2_, StructureBoundingBox p_i45618_3_, EnumFacing p_i45618_4_) {
/*  255 */       super(p_i45618_1_);
/*  256 */       this.coordBaseMode = p_i45618_4_;
/*  257 */       this.boundingBox = p_i45618_3_;
/*      */     }
/*      */     
/*      */     public void buildComponent(StructureComponent componentIn, List<StructureComponent> listIn, Random rand) {
/*  261 */       int i = 1;
/*      */       
/*  263 */       if (this.coordBaseMode == EnumFacing.WEST || this.coordBaseMode == EnumFacing.NORTH) {
/*  264 */         i = 5;
/*      */       }
/*      */       
/*  267 */       getNextComponentX((StructureNetherBridgePieces.Start)componentIn, listIn, rand, 0, i, (rand.nextInt(8) > 0));
/*  268 */       getNextComponentZ((StructureNetherBridgePieces.Start)componentIn, listIn, rand, 0, i, (rand.nextInt(8) > 0));
/*      */     }
/*      */     
/*      */     public static Corridor4 func_175880_a(List<StructureComponent> p_175880_0_, Random p_175880_1_, int p_175880_2_, int p_175880_3_, int p_175880_4_, EnumFacing p_175880_5_, int p_175880_6_) {
/*  272 */       StructureBoundingBox structureboundingbox = StructureBoundingBox.getComponentToAddBoundingBox(p_175880_2_, p_175880_3_, p_175880_4_, -3, 0, 0, 9, 7, 9, p_175880_5_);
/*  273 */       return (isAboveGround(structureboundingbox) && StructureComponent.findIntersecting(p_175880_0_, structureboundingbox) == null) ? new Corridor4(p_175880_6_, p_175880_1_, structureboundingbox, p_175880_5_) : null;
/*      */     }
/*      */     
/*      */     public boolean addComponentParts(World worldIn, Random randomIn, StructureBoundingBox structureBoundingBoxIn) {
/*  277 */       fillWithBlocks(worldIn, structureBoundingBoxIn, 0, 0, 0, 8, 1, 8, Blocks.nether_brick.getDefaultState(), Blocks.nether_brick.getDefaultState(), false);
/*  278 */       fillWithBlocks(worldIn, structureBoundingBoxIn, 0, 2, 0, 8, 5, 8, Blocks.air.getDefaultState(), Blocks.air.getDefaultState(), false);
/*  279 */       fillWithBlocks(worldIn, structureBoundingBoxIn, 0, 6, 0, 8, 6, 5, Blocks.nether_brick.getDefaultState(), Blocks.nether_brick.getDefaultState(), false);
/*  280 */       fillWithBlocks(worldIn, structureBoundingBoxIn, 0, 2, 0, 2, 5, 0, Blocks.nether_brick.getDefaultState(), Blocks.nether_brick.getDefaultState(), false);
/*  281 */       fillWithBlocks(worldIn, structureBoundingBoxIn, 6, 2, 0, 8, 5, 0, Blocks.nether_brick.getDefaultState(), Blocks.nether_brick.getDefaultState(), false);
/*  282 */       fillWithBlocks(worldIn, structureBoundingBoxIn, 1, 3, 0, 1, 4, 0, Blocks.nether_brick_fence.getDefaultState(), Blocks.nether_brick_fence.getDefaultState(), false);
/*  283 */       fillWithBlocks(worldIn, structureBoundingBoxIn, 7, 3, 0, 7, 4, 0, Blocks.nether_brick_fence.getDefaultState(), Blocks.nether_brick_fence.getDefaultState(), false);
/*  284 */       fillWithBlocks(worldIn, structureBoundingBoxIn, 0, 2, 4, 8, 2, 8, Blocks.nether_brick.getDefaultState(), Blocks.nether_brick.getDefaultState(), false);
/*  285 */       fillWithBlocks(worldIn, structureBoundingBoxIn, 1, 1, 4, 2, 2, 4, Blocks.air.getDefaultState(), Blocks.air.getDefaultState(), false);
/*  286 */       fillWithBlocks(worldIn, structureBoundingBoxIn, 6, 1, 4, 7, 2, 4, Blocks.air.getDefaultState(), Blocks.air.getDefaultState(), false);
/*  287 */       fillWithBlocks(worldIn, structureBoundingBoxIn, 0, 3, 8, 8, 3, 8, Blocks.nether_brick_fence.getDefaultState(), Blocks.nether_brick_fence.getDefaultState(), false);
/*  288 */       fillWithBlocks(worldIn, structureBoundingBoxIn, 0, 3, 6, 0, 3, 7, Blocks.nether_brick_fence.getDefaultState(), Blocks.nether_brick_fence.getDefaultState(), false);
/*  289 */       fillWithBlocks(worldIn, structureBoundingBoxIn, 8, 3, 6, 8, 3, 7, Blocks.nether_brick_fence.getDefaultState(), Blocks.nether_brick_fence.getDefaultState(), false);
/*  290 */       fillWithBlocks(worldIn, structureBoundingBoxIn, 0, 3, 4, 0, 5, 5, Blocks.nether_brick.getDefaultState(), Blocks.nether_brick.getDefaultState(), false);
/*  291 */       fillWithBlocks(worldIn, structureBoundingBoxIn, 8, 3, 4, 8, 5, 5, Blocks.nether_brick.getDefaultState(), Blocks.nether_brick.getDefaultState(), false);
/*  292 */       fillWithBlocks(worldIn, structureBoundingBoxIn, 1, 3, 5, 2, 5, 5, Blocks.nether_brick.getDefaultState(), Blocks.nether_brick.getDefaultState(), false);
/*  293 */       fillWithBlocks(worldIn, structureBoundingBoxIn, 6, 3, 5, 7, 5, 5, Blocks.nether_brick.getDefaultState(), Blocks.nether_brick.getDefaultState(), false);
/*  294 */       fillWithBlocks(worldIn, structureBoundingBoxIn, 1, 4, 5, 1, 5, 5, Blocks.nether_brick_fence.getDefaultState(), Blocks.nether_brick_fence.getDefaultState(), false);
/*  295 */       fillWithBlocks(worldIn, structureBoundingBoxIn, 7, 4, 5, 7, 5, 5, Blocks.nether_brick_fence.getDefaultState(), Blocks.nether_brick_fence.getDefaultState(), false);
/*      */       
/*  297 */       for (int i = 0; i <= 5; i++) {
/*  298 */         for (int j = 0; j <= 8; j++) {
/*  299 */           replaceAirAndLiquidDownwards(worldIn, Blocks.nether_brick.getDefaultState(), j, -1, i, structureBoundingBoxIn);
/*      */         }
/*      */       } 
/*      */       
/*  303 */       return true;
/*      */     }
/*      */   }
/*      */   
/*      */   public static class Corridor5
/*      */     extends Piece {
/*      */     public Corridor5() {}
/*      */     
/*      */     public Corridor5(int p_i45614_1_, Random p_i45614_2_, StructureBoundingBox p_i45614_3_, EnumFacing p_i45614_4_) {
/*  312 */       super(p_i45614_1_);
/*  313 */       this.coordBaseMode = p_i45614_4_;
/*  314 */       this.boundingBox = p_i45614_3_;
/*      */     }
/*      */     
/*      */     public void buildComponent(StructureComponent componentIn, List<StructureComponent> listIn, Random rand) {
/*  318 */       getNextComponentNormal((StructureNetherBridgePieces.Start)componentIn, listIn, rand, 1, 0, true);
/*      */     }
/*      */     
/*      */     public static Corridor5 func_175877_a(List<StructureComponent> p_175877_0_, Random p_175877_1_, int p_175877_2_, int p_175877_3_, int p_175877_4_, EnumFacing p_175877_5_, int p_175877_6_) {
/*  322 */       StructureBoundingBox structureboundingbox = StructureBoundingBox.getComponentToAddBoundingBox(p_175877_2_, p_175877_3_, p_175877_4_, -1, 0, 0, 5, 7, 5, p_175877_5_);
/*  323 */       return (isAboveGround(structureboundingbox) && StructureComponent.findIntersecting(p_175877_0_, structureboundingbox) == null) ? new Corridor5(p_175877_6_, p_175877_1_, structureboundingbox, p_175877_5_) : null;
/*      */     }
/*      */     
/*      */     public boolean addComponentParts(World worldIn, Random randomIn, StructureBoundingBox structureBoundingBoxIn) {
/*  327 */       fillWithBlocks(worldIn, structureBoundingBoxIn, 0, 0, 0, 4, 1, 4, Blocks.nether_brick.getDefaultState(), Blocks.nether_brick.getDefaultState(), false);
/*  328 */       fillWithBlocks(worldIn, structureBoundingBoxIn, 0, 2, 0, 4, 5, 4, Blocks.air.getDefaultState(), Blocks.air.getDefaultState(), false);
/*  329 */       fillWithBlocks(worldIn, structureBoundingBoxIn, 0, 2, 0, 0, 5, 4, Blocks.nether_brick.getDefaultState(), Blocks.nether_brick.getDefaultState(), false);
/*  330 */       fillWithBlocks(worldIn, structureBoundingBoxIn, 4, 2, 0, 4, 5, 4, Blocks.nether_brick.getDefaultState(), Blocks.nether_brick.getDefaultState(), false);
/*  331 */       fillWithBlocks(worldIn, structureBoundingBoxIn, 0, 3, 1, 0, 4, 1, Blocks.nether_brick_fence.getDefaultState(), Blocks.nether_brick_fence.getDefaultState(), false);
/*  332 */       fillWithBlocks(worldIn, structureBoundingBoxIn, 0, 3, 3, 0, 4, 3, Blocks.nether_brick_fence.getDefaultState(), Blocks.nether_brick_fence.getDefaultState(), false);
/*  333 */       fillWithBlocks(worldIn, structureBoundingBoxIn, 4, 3, 1, 4, 4, 1, Blocks.nether_brick_fence.getDefaultState(), Blocks.nether_brick_fence.getDefaultState(), false);
/*  334 */       fillWithBlocks(worldIn, structureBoundingBoxIn, 4, 3, 3, 4, 4, 3, Blocks.nether_brick_fence.getDefaultState(), Blocks.nether_brick_fence.getDefaultState(), false);
/*  335 */       fillWithBlocks(worldIn, structureBoundingBoxIn, 0, 6, 0, 4, 6, 4, Blocks.nether_brick.getDefaultState(), Blocks.nether_brick.getDefaultState(), false);
/*      */       
/*  337 */       for (int i = 0; i <= 4; i++) {
/*  338 */         for (int j = 0; j <= 4; j++) {
/*  339 */           replaceAirAndLiquidDownwards(worldIn, Blocks.nether_brick.getDefaultState(), i, -1, j, structureBoundingBoxIn);
/*      */         }
/*      */       } 
/*      */       
/*  343 */       return true;
/*      */     }
/*      */   }
/*      */   
/*      */   public static class Crossing
/*      */     extends Piece {
/*      */     public Crossing() {}
/*      */     
/*      */     public Crossing(int p_i45610_1_, Random p_i45610_2_, StructureBoundingBox p_i45610_3_, EnumFacing p_i45610_4_) {
/*  352 */       super(p_i45610_1_);
/*  353 */       this.coordBaseMode = p_i45610_4_;
/*  354 */       this.boundingBox = p_i45610_3_;
/*      */     }
/*      */     
/*      */     public void buildComponent(StructureComponent componentIn, List<StructureComponent> listIn, Random rand) {
/*  358 */       getNextComponentNormal((StructureNetherBridgePieces.Start)componentIn, listIn, rand, 2, 0, false);
/*  359 */       getNextComponentX((StructureNetherBridgePieces.Start)componentIn, listIn, rand, 0, 2, false);
/*  360 */       getNextComponentZ((StructureNetherBridgePieces.Start)componentIn, listIn, rand, 0, 2, false);
/*      */     }
/*      */     
/*      */     public static Crossing func_175873_a(List<StructureComponent> p_175873_0_, Random p_175873_1_, int p_175873_2_, int p_175873_3_, int p_175873_4_, EnumFacing p_175873_5_, int p_175873_6_) {
/*  364 */       StructureBoundingBox structureboundingbox = StructureBoundingBox.getComponentToAddBoundingBox(p_175873_2_, p_175873_3_, p_175873_4_, -2, 0, 0, 7, 9, 7, p_175873_5_);
/*  365 */       return (isAboveGround(structureboundingbox) && StructureComponent.findIntersecting(p_175873_0_, structureboundingbox) == null) ? new Crossing(p_175873_6_, p_175873_1_, structureboundingbox, p_175873_5_) : null;
/*      */     }
/*      */     
/*      */     public boolean addComponentParts(World worldIn, Random randomIn, StructureBoundingBox structureBoundingBoxIn) {
/*  369 */       fillWithBlocks(worldIn, structureBoundingBoxIn, 0, 0, 0, 6, 1, 6, Blocks.nether_brick.getDefaultState(), Blocks.nether_brick.getDefaultState(), false);
/*  370 */       fillWithBlocks(worldIn, structureBoundingBoxIn, 0, 2, 0, 6, 7, 6, Blocks.air.getDefaultState(), Blocks.air.getDefaultState(), false);
/*  371 */       fillWithBlocks(worldIn, structureBoundingBoxIn, 0, 2, 0, 1, 6, 0, Blocks.nether_brick.getDefaultState(), Blocks.nether_brick.getDefaultState(), false);
/*  372 */       fillWithBlocks(worldIn, structureBoundingBoxIn, 0, 2, 6, 1, 6, 6, Blocks.nether_brick.getDefaultState(), Blocks.nether_brick.getDefaultState(), false);
/*  373 */       fillWithBlocks(worldIn, structureBoundingBoxIn, 5, 2, 0, 6, 6, 0, Blocks.nether_brick.getDefaultState(), Blocks.nether_brick.getDefaultState(), false);
/*  374 */       fillWithBlocks(worldIn, structureBoundingBoxIn, 5, 2, 6, 6, 6, 6, Blocks.nether_brick.getDefaultState(), Blocks.nether_brick.getDefaultState(), false);
/*  375 */       fillWithBlocks(worldIn, structureBoundingBoxIn, 0, 2, 0, 0, 6, 1, Blocks.nether_brick.getDefaultState(), Blocks.nether_brick.getDefaultState(), false);
/*  376 */       fillWithBlocks(worldIn, structureBoundingBoxIn, 0, 2, 5, 0, 6, 6, Blocks.nether_brick.getDefaultState(), Blocks.nether_brick.getDefaultState(), false);
/*  377 */       fillWithBlocks(worldIn, structureBoundingBoxIn, 6, 2, 0, 6, 6, 1, Blocks.nether_brick.getDefaultState(), Blocks.nether_brick.getDefaultState(), false);
/*  378 */       fillWithBlocks(worldIn, structureBoundingBoxIn, 6, 2, 5, 6, 6, 6, Blocks.nether_brick.getDefaultState(), Blocks.nether_brick.getDefaultState(), false);
/*  379 */       fillWithBlocks(worldIn, structureBoundingBoxIn, 2, 6, 0, 4, 6, 0, Blocks.nether_brick.getDefaultState(), Blocks.nether_brick.getDefaultState(), false);
/*  380 */       fillWithBlocks(worldIn, structureBoundingBoxIn, 2, 5, 0, 4, 5, 0, Blocks.nether_brick_fence.getDefaultState(), Blocks.nether_brick_fence.getDefaultState(), false);
/*  381 */       fillWithBlocks(worldIn, structureBoundingBoxIn, 2, 6, 6, 4, 6, 6, Blocks.nether_brick.getDefaultState(), Blocks.nether_brick.getDefaultState(), false);
/*  382 */       fillWithBlocks(worldIn, structureBoundingBoxIn, 2, 5, 6, 4, 5, 6, Blocks.nether_brick_fence.getDefaultState(), Blocks.nether_brick_fence.getDefaultState(), false);
/*  383 */       fillWithBlocks(worldIn, structureBoundingBoxIn, 0, 6, 2, 0, 6, 4, Blocks.nether_brick.getDefaultState(), Blocks.nether_brick.getDefaultState(), false);
/*  384 */       fillWithBlocks(worldIn, structureBoundingBoxIn, 0, 5, 2, 0, 5, 4, Blocks.nether_brick_fence.getDefaultState(), Blocks.nether_brick_fence.getDefaultState(), false);
/*  385 */       fillWithBlocks(worldIn, structureBoundingBoxIn, 6, 6, 2, 6, 6, 4, Blocks.nether_brick.getDefaultState(), Blocks.nether_brick.getDefaultState(), false);
/*  386 */       fillWithBlocks(worldIn, structureBoundingBoxIn, 6, 5, 2, 6, 5, 4, Blocks.nether_brick_fence.getDefaultState(), Blocks.nether_brick_fence.getDefaultState(), false);
/*      */       
/*  388 */       for (int i = 0; i <= 6; i++) {
/*  389 */         for (int j = 0; j <= 6; j++) {
/*  390 */           replaceAirAndLiquidDownwards(worldIn, Blocks.nether_brick.getDefaultState(), i, -1, j, structureBoundingBoxIn);
/*      */         }
/*      */       } 
/*      */       
/*  394 */       return true;
/*      */     }
/*      */   }
/*      */   
/*      */   public static class Crossing2
/*      */     extends Piece {
/*      */     public Crossing2() {}
/*      */     
/*      */     public Crossing2(int p_i45616_1_, Random p_i45616_2_, StructureBoundingBox p_i45616_3_, EnumFacing p_i45616_4_) {
/*  403 */       super(p_i45616_1_);
/*  404 */       this.coordBaseMode = p_i45616_4_;
/*  405 */       this.boundingBox = p_i45616_3_;
/*      */     }
/*      */     
/*      */     public void buildComponent(StructureComponent componentIn, List<StructureComponent> listIn, Random rand) {
/*  409 */       getNextComponentNormal((StructureNetherBridgePieces.Start)componentIn, listIn, rand, 1, 0, true);
/*  410 */       getNextComponentX((StructureNetherBridgePieces.Start)componentIn, listIn, rand, 0, 1, true);
/*  411 */       getNextComponentZ((StructureNetherBridgePieces.Start)componentIn, listIn, rand, 0, 1, true);
/*      */     }
/*      */     
/*      */     public static Crossing2 func_175878_a(List<StructureComponent> p_175878_0_, Random p_175878_1_, int p_175878_2_, int p_175878_3_, int p_175878_4_, EnumFacing p_175878_5_, int p_175878_6_) {
/*  415 */       StructureBoundingBox structureboundingbox = StructureBoundingBox.getComponentToAddBoundingBox(p_175878_2_, p_175878_3_, p_175878_4_, -1, 0, 0, 5, 7, 5, p_175878_5_);
/*  416 */       return (isAboveGround(structureboundingbox) && StructureComponent.findIntersecting(p_175878_0_, structureboundingbox) == null) ? new Crossing2(p_175878_6_, p_175878_1_, structureboundingbox, p_175878_5_) : null;
/*      */     }
/*      */     
/*      */     public boolean addComponentParts(World worldIn, Random randomIn, StructureBoundingBox structureBoundingBoxIn) {
/*  420 */       fillWithBlocks(worldIn, structureBoundingBoxIn, 0, 0, 0, 4, 1, 4, Blocks.nether_brick.getDefaultState(), Blocks.nether_brick.getDefaultState(), false);
/*  421 */       fillWithBlocks(worldIn, structureBoundingBoxIn, 0, 2, 0, 4, 5, 4, Blocks.air.getDefaultState(), Blocks.air.getDefaultState(), false);
/*  422 */       fillWithBlocks(worldIn, structureBoundingBoxIn, 0, 2, 0, 0, 5, 0, Blocks.nether_brick.getDefaultState(), Blocks.nether_brick.getDefaultState(), false);
/*  423 */       fillWithBlocks(worldIn, structureBoundingBoxIn, 4, 2, 0, 4, 5, 0, Blocks.nether_brick.getDefaultState(), Blocks.nether_brick.getDefaultState(), false);
/*  424 */       fillWithBlocks(worldIn, structureBoundingBoxIn, 0, 2, 4, 0, 5, 4, Blocks.nether_brick.getDefaultState(), Blocks.nether_brick.getDefaultState(), false);
/*  425 */       fillWithBlocks(worldIn, structureBoundingBoxIn, 4, 2, 4, 4, 5, 4, Blocks.nether_brick.getDefaultState(), Blocks.nether_brick.getDefaultState(), false);
/*  426 */       fillWithBlocks(worldIn, structureBoundingBoxIn, 0, 6, 0, 4, 6, 4, Blocks.nether_brick.getDefaultState(), Blocks.nether_brick.getDefaultState(), false);
/*      */       
/*  428 */       for (int i = 0; i <= 4; i++) {
/*  429 */         for (int j = 0; j <= 4; j++) {
/*  430 */           replaceAirAndLiquidDownwards(worldIn, Blocks.nether_brick.getDefaultState(), i, -1, j, structureBoundingBoxIn);
/*      */         }
/*      */       } 
/*      */       
/*  434 */       return true;
/*      */     }
/*      */   }
/*      */   
/*      */   public static class Crossing3
/*      */     extends Piece {
/*      */     public Crossing3() {}
/*      */     
/*      */     public Crossing3(int p_i45622_1_, Random p_i45622_2_, StructureBoundingBox p_i45622_3_, EnumFacing p_i45622_4_) {
/*  443 */       super(p_i45622_1_);
/*  444 */       this.coordBaseMode = p_i45622_4_;
/*  445 */       this.boundingBox = p_i45622_3_;
/*      */     }
/*      */     
/*      */     protected Crossing3(Random p_i2042_1_, int p_i2042_2_, int p_i2042_3_) {
/*  449 */       super(0);
/*  450 */       this.coordBaseMode = EnumFacing.Plane.HORIZONTAL.random(p_i2042_1_);
/*      */       
/*  452 */       switch (this.coordBaseMode) {
/*      */         case NORTH:
/*      */         case SOUTH:
/*  455 */           this.boundingBox = new StructureBoundingBox(p_i2042_2_, 64, p_i2042_3_, p_i2042_2_ + 19 - 1, 73, p_i2042_3_ + 19 - 1);
/*      */           return;
/*      */       } 
/*      */       
/*  459 */       this.boundingBox = new StructureBoundingBox(p_i2042_2_, 64, p_i2042_3_, p_i2042_2_ + 19 - 1, 73, p_i2042_3_ + 19 - 1);
/*      */     }
/*      */ 
/*      */     
/*      */     public void buildComponent(StructureComponent componentIn, List<StructureComponent> listIn, Random rand) {
/*  464 */       getNextComponentNormal((StructureNetherBridgePieces.Start)componentIn, listIn, rand, 8, 3, false);
/*  465 */       getNextComponentX((StructureNetherBridgePieces.Start)componentIn, listIn, rand, 3, 8, false);
/*  466 */       getNextComponentZ((StructureNetherBridgePieces.Start)componentIn, listIn, rand, 3, 8, false);
/*      */     }
/*      */     
/*      */     public static Crossing3 func_175885_a(List<StructureComponent> p_175885_0_, Random p_175885_1_, int p_175885_2_, int p_175885_3_, int p_175885_4_, EnumFacing p_175885_5_, int p_175885_6_) {
/*  470 */       StructureBoundingBox structureboundingbox = StructureBoundingBox.getComponentToAddBoundingBox(p_175885_2_, p_175885_3_, p_175885_4_, -8, -3, 0, 19, 10, 19, p_175885_5_);
/*  471 */       return (isAboveGround(structureboundingbox) && StructureComponent.findIntersecting(p_175885_0_, structureboundingbox) == null) ? new Crossing3(p_175885_6_, p_175885_1_, structureboundingbox, p_175885_5_) : null;
/*      */     }
/*      */     
/*      */     public boolean addComponentParts(World worldIn, Random randomIn, StructureBoundingBox structureBoundingBoxIn) {
/*  475 */       fillWithBlocks(worldIn, structureBoundingBoxIn, 7, 3, 0, 11, 4, 18, Blocks.nether_brick.getDefaultState(), Blocks.nether_brick.getDefaultState(), false);
/*  476 */       fillWithBlocks(worldIn, structureBoundingBoxIn, 0, 3, 7, 18, 4, 11, Blocks.nether_brick.getDefaultState(), Blocks.nether_brick.getDefaultState(), false);
/*  477 */       fillWithBlocks(worldIn, structureBoundingBoxIn, 8, 5, 0, 10, 7, 18, Blocks.air.getDefaultState(), Blocks.air.getDefaultState(), false);
/*  478 */       fillWithBlocks(worldIn, structureBoundingBoxIn, 0, 5, 8, 18, 7, 10, Blocks.air.getDefaultState(), Blocks.air.getDefaultState(), false);
/*  479 */       fillWithBlocks(worldIn, structureBoundingBoxIn, 7, 5, 0, 7, 5, 7, Blocks.nether_brick.getDefaultState(), Blocks.nether_brick.getDefaultState(), false);
/*  480 */       fillWithBlocks(worldIn, structureBoundingBoxIn, 7, 5, 11, 7, 5, 18, Blocks.nether_brick.getDefaultState(), Blocks.nether_brick.getDefaultState(), false);
/*  481 */       fillWithBlocks(worldIn, structureBoundingBoxIn, 11, 5, 0, 11, 5, 7, Blocks.nether_brick.getDefaultState(), Blocks.nether_brick.getDefaultState(), false);
/*  482 */       fillWithBlocks(worldIn, structureBoundingBoxIn, 11, 5, 11, 11, 5, 18, Blocks.nether_brick.getDefaultState(), Blocks.nether_brick.getDefaultState(), false);
/*  483 */       fillWithBlocks(worldIn, structureBoundingBoxIn, 0, 5, 7, 7, 5, 7, Blocks.nether_brick.getDefaultState(), Blocks.nether_brick.getDefaultState(), false);
/*  484 */       fillWithBlocks(worldIn, structureBoundingBoxIn, 11, 5, 7, 18, 5, 7, Blocks.nether_brick.getDefaultState(), Blocks.nether_brick.getDefaultState(), false);
/*  485 */       fillWithBlocks(worldIn, structureBoundingBoxIn, 0, 5, 11, 7, 5, 11, Blocks.nether_brick.getDefaultState(), Blocks.nether_brick.getDefaultState(), false);
/*  486 */       fillWithBlocks(worldIn, structureBoundingBoxIn, 11, 5, 11, 18, 5, 11, Blocks.nether_brick.getDefaultState(), Blocks.nether_brick.getDefaultState(), false);
/*  487 */       fillWithBlocks(worldIn, structureBoundingBoxIn, 7, 2, 0, 11, 2, 5, Blocks.nether_brick.getDefaultState(), Blocks.nether_brick.getDefaultState(), false);
/*  488 */       fillWithBlocks(worldIn, structureBoundingBoxIn, 7, 2, 13, 11, 2, 18, Blocks.nether_brick.getDefaultState(), Blocks.nether_brick.getDefaultState(), false);
/*  489 */       fillWithBlocks(worldIn, structureBoundingBoxIn, 7, 0, 0, 11, 1, 3, Blocks.nether_brick.getDefaultState(), Blocks.nether_brick.getDefaultState(), false);
/*  490 */       fillWithBlocks(worldIn, structureBoundingBoxIn, 7, 0, 15, 11, 1, 18, Blocks.nether_brick.getDefaultState(), Blocks.nether_brick.getDefaultState(), false);
/*      */       
/*  492 */       for (int i = 7; i <= 11; i++) {
/*  493 */         for (int j = 0; j <= 2; j++) {
/*  494 */           replaceAirAndLiquidDownwards(worldIn, Blocks.nether_brick.getDefaultState(), i, -1, j, structureBoundingBoxIn);
/*  495 */           replaceAirAndLiquidDownwards(worldIn, Blocks.nether_brick.getDefaultState(), i, -1, 18 - j, structureBoundingBoxIn);
/*      */         } 
/*      */       } 
/*      */       
/*  499 */       fillWithBlocks(worldIn, structureBoundingBoxIn, 0, 2, 7, 5, 2, 11, Blocks.nether_brick.getDefaultState(), Blocks.nether_brick.getDefaultState(), false);
/*  500 */       fillWithBlocks(worldIn, structureBoundingBoxIn, 13, 2, 7, 18, 2, 11, Blocks.nether_brick.getDefaultState(), Blocks.nether_brick.getDefaultState(), false);
/*  501 */       fillWithBlocks(worldIn, structureBoundingBoxIn, 0, 0, 7, 3, 1, 11, Blocks.nether_brick.getDefaultState(), Blocks.nether_brick.getDefaultState(), false);
/*  502 */       fillWithBlocks(worldIn, structureBoundingBoxIn, 15, 0, 7, 18, 1, 11, Blocks.nether_brick.getDefaultState(), Blocks.nether_brick.getDefaultState(), false);
/*      */       
/*  504 */       for (int k = 0; k <= 2; k++) {
/*  505 */         for (int l = 7; l <= 11; l++) {
/*  506 */           replaceAirAndLiquidDownwards(worldIn, Blocks.nether_brick.getDefaultState(), k, -1, l, structureBoundingBoxIn);
/*  507 */           replaceAirAndLiquidDownwards(worldIn, Blocks.nether_brick.getDefaultState(), 18 - k, -1, l, structureBoundingBoxIn);
/*      */         } 
/*      */       } 
/*      */       
/*  511 */       return true;
/*      */     }
/*      */   }
/*      */   
/*      */   public static class End
/*      */     extends Piece {
/*      */     private int fillSeed;
/*      */     
/*      */     public End() {}
/*      */     
/*      */     public End(int p_i45621_1_, Random p_i45621_2_, StructureBoundingBox p_i45621_3_, EnumFacing p_i45621_4_) {
/*  522 */       super(p_i45621_1_);
/*  523 */       this.coordBaseMode = p_i45621_4_;
/*  524 */       this.boundingBox = p_i45621_3_;
/*  525 */       this.fillSeed = p_i45621_2_.nextInt();
/*      */     }
/*      */     
/*      */     public static End func_175884_a(List<StructureComponent> p_175884_0_, Random p_175884_1_, int p_175884_2_, int p_175884_3_, int p_175884_4_, EnumFacing p_175884_5_, int p_175884_6_) {
/*  529 */       StructureBoundingBox structureboundingbox = StructureBoundingBox.getComponentToAddBoundingBox(p_175884_2_, p_175884_3_, p_175884_4_, -1, -3, 0, 5, 10, 8, p_175884_5_);
/*  530 */       return (isAboveGround(structureboundingbox) && StructureComponent.findIntersecting(p_175884_0_, structureboundingbox) == null) ? new End(p_175884_6_, p_175884_1_, structureboundingbox, p_175884_5_) : null;
/*      */     }
/*      */     
/*      */     protected void readStructureFromNBT(NBTTagCompound tagCompound) {
/*  534 */       super.readStructureFromNBT(tagCompound);
/*  535 */       this.fillSeed = tagCompound.getInteger("Seed");
/*      */     }
/*      */     
/*      */     protected void writeStructureToNBT(NBTTagCompound tagCompound) {
/*  539 */       super.writeStructureToNBT(tagCompound);
/*  540 */       tagCompound.setInteger("Seed", this.fillSeed);
/*      */     }
/*      */     
/*      */     public boolean addComponentParts(World worldIn, Random randomIn, StructureBoundingBox structureBoundingBoxIn) {
/*  544 */       Random random = new Random(this.fillSeed);
/*      */       
/*  546 */       for (int i = 0; i <= 4; i++) {
/*  547 */         for (int j = 3; j <= 4; j++) {
/*  548 */           int k = random.nextInt(8);
/*  549 */           fillWithBlocks(worldIn, structureBoundingBoxIn, i, j, 0, i, j, k, Blocks.nether_brick.getDefaultState(), Blocks.nether_brick.getDefaultState(), false);
/*      */         } 
/*      */       } 
/*      */       
/*  553 */       int l = random.nextInt(8);
/*  554 */       fillWithBlocks(worldIn, structureBoundingBoxIn, 0, 5, 0, 0, 5, l, Blocks.nether_brick.getDefaultState(), Blocks.nether_brick.getDefaultState(), false);
/*  555 */       l = random.nextInt(8);
/*  556 */       fillWithBlocks(worldIn, structureBoundingBoxIn, 4, 5, 0, 4, 5, l, Blocks.nether_brick.getDefaultState(), Blocks.nether_brick.getDefaultState(), false);
/*      */       
/*  558 */       for (l = 0; l <= 4; l++) {
/*  559 */         int i1 = random.nextInt(5);
/*  560 */         fillWithBlocks(worldIn, structureBoundingBoxIn, l, 2, 0, l, 2, i1, Blocks.nether_brick.getDefaultState(), Blocks.nether_brick.getDefaultState(), false);
/*      */       } 
/*      */       
/*  563 */       for (l = 0; l <= 4; l++) {
/*  564 */         for (int j1 = 0; j1 <= 1; j1++) {
/*  565 */           int k1 = random.nextInt(3);
/*  566 */           fillWithBlocks(worldIn, structureBoundingBoxIn, l, j1, 0, l, j1, k1, Blocks.nether_brick.getDefaultState(), Blocks.nether_brick.getDefaultState(), false);
/*      */         } 
/*      */       } 
/*      */       
/*  570 */       return true;
/*      */     }
/*      */   }
/*      */   
/*      */   public static class Entrance
/*      */     extends Piece {
/*      */     public Entrance() {}
/*      */     
/*      */     public Entrance(int p_i45617_1_, Random p_i45617_2_, StructureBoundingBox p_i45617_3_, EnumFacing p_i45617_4_) {
/*  579 */       super(p_i45617_1_);
/*  580 */       this.coordBaseMode = p_i45617_4_;
/*  581 */       this.boundingBox = p_i45617_3_;
/*      */     }
/*      */     
/*      */     public void buildComponent(StructureComponent componentIn, List<StructureComponent> listIn, Random rand) {
/*  585 */       getNextComponentNormal((StructureNetherBridgePieces.Start)componentIn, listIn, rand, 5, 3, true);
/*      */     }
/*      */     
/*      */     public static Entrance func_175881_a(List<StructureComponent> p_175881_0_, Random p_175881_1_, int p_175881_2_, int p_175881_3_, int p_175881_4_, EnumFacing p_175881_5_, int p_175881_6_) {
/*  589 */       StructureBoundingBox structureboundingbox = StructureBoundingBox.getComponentToAddBoundingBox(p_175881_2_, p_175881_3_, p_175881_4_, -5, -3, 0, 13, 14, 13, p_175881_5_);
/*  590 */       return (isAboveGround(structureboundingbox) && StructureComponent.findIntersecting(p_175881_0_, structureboundingbox) == null) ? new Entrance(p_175881_6_, p_175881_1_, structureboundingbox, p_175881_5_) : null;
/*      */     }
/*      */     
/*      */     public boolean addComponentParts(World worldIn, Random randomIn, StructureBoundingBox structureBoundingBoxIn) {
/*  594 */       fillWithBlocks(worldIn, structureBoundingBoxIn, 0, 3, 0, 12, 4, 12, Blocks.nether_brick.getDefaultState(), Blocks.nether_brick.getDefaultState(), false);
/*  595 */       fillWithBlocks(worldIn, structureBoundingBoxIn, 0, 5, 0, 12, 13, 12, Blocks.air.getDefaultState(), Blocks.air.getDefaultState(), false);
/*  596 */       fillWithBlocks(worldIn, structureBoundingBoxIn, 0, 5, 0, 1, 12, 12, Blocks.nether_brick.getDefaultState(), Blocks.nether_brick.getDefaultState(), false);
/*  597 */       fillWithBlocks(worldIn, structureBoundingBoxIn, 11, 5, 0, 12, 12, 12, Blocks.nether_brick.getDefaultState(), Blocks.nether_brick.getDefaultState(), false);
/*  598 */       fillWithBlocks(worldIn, structureBoundingBoxIn, 2, 5, 11, 4, 12, 12, Blocks.nether_brick.getDefaultState(), Blocks.nether_brick.getDefaultState(), false);
/*  599 */       fillWithBlocks(worldIn, structureBoundingBoxIn, 8, 5, 11, 10, 12, 12, Blocks.nether_brick.getDefaultState(), Blocks.nether_brick.getDefaultState(), false);
/*  600 */       fillWithBlocks(worldIn, structureBoundingBoxIn, 5, 9, 11, 7, 12, 12, Blocks.nether_brick.getDefaultState(), Blocks.nether_brick.getDefaultState(), false);
/*  601 */       fillWithBlocks(worldIn, structureBoundingBoxIn, 2, 5, 0, 4, 12, 1, Blocks.nether_brick.getDefaultState(), Blocks.nether_brick.getDefaultState(), false);
/*  602 */       fillWithBlocks(worldIn, structureBoundingBoxIn, 8, 5, 0, 10, 12, 1, Blocks.nether_brick.getDefaultState(), Blocks.nether_brick.getDefaultState(), false);
/*  603 */       fillWithBlocks(worldIn, structureBoundingBoxIn, 5, 9, 0, 7, 12, 1, Blocks.nether_brick.getDefaultState(), Blocks.nether_brick.getDefaultState(), false);
/*  604 */       fillWithBlocks(worldIn, structureBoundingBoxIn, 2, 11, 2, 10, 12, 10, Blocks.nether_brick.getDefaultState(), Blocks.nether_brick.getDefaultState(), false);
/*  605 */       fillWithBlocks(worldIn, structureBoundingBoxIn, 5, 8, 0, 7, 8, 0, Blocks.nether_brick_fence.getDefaultState(), Blocks.nether_brick_fence.getDefaultState(), false);
/*      */       
/*  607 */       for (int i = 1; i <= 11; i += 2) {
/*  608 */         fillWithBlocks(worldIn, structureBoundingBoxIn, i, 10, 0, i, 11, 0, Blocks.nether_brick_fence.getDefaultState(), Blocks.nether_brick_fence.getDefaultState(), false);
/*  609 */         fillWithBlocks(worldIn, structureBoundingBoxIn, i, 10, 12, i, 11, 12, Blocks.nether_brick_fence.getDefaultState(), Blocks.nether_brick_fence.getDefaultState(), false);
/*  610 */         fillWithBlocks(worldIn, structureBoundingBoxIn, 0, 10, i, 0, 11, i, Blocks.nether_brick_fence.getDefaultState(), Blocks.nether_brick_fence.getDefaultState(), false);
/*  611 */         fillWithBlocks(worldIn, structureBoundingBoxIn, 12, 10, i, 12, 11, i, Blocks.nether_brick_fence.getDefaultState(), Blocks.nether_brick_fence.getDefaultState(), false);
/*  612 */         setBlockState(worldIn, Blocks.nether_brick.getDefaultState(), i, 13, 0, structureBoundingBoxIn);
/*  613 */         setBlockState(worldIn, Blocks.nether_brick.getDefaultState(), i, 13, 12, structureBoundingBoxIn);
/*  614 */         setBlockState(worldIn, Blocks.nether_brick.getDefaultState(), 0, 13, i, structureBoundingBoxIn);
/*  615 */         setBlockState(worldIn, Blocks.nether_brick.getDefaultState(), 12, 13, i, structureBoundingBoxIn);
/*  616 */         setBlockState(worldIn, Blocks.nether_brick_fence.getDefaultState(), i + 1, 13, 0, structureBoundingBoxIn);
/*  617 */         setBlockState(worldIn, Blocks.nether_brick_fence.getDefaultState(), i + 1, 13, 12, structureBoundingBoxIn);
/*  618 */         setBlockState(worldIn, Blocks.nether_brick_fence.getDefaultState(), 0, 13, i + 1, structureBoundingBoxIn);
/*  619 */         setBlockState(worldIn, Blocks.nether_brick_fence.getDefaultState(), 12, 13, i + 1, structureBoundingBoxIn);
/*      */       } 
/*      */       
/*  622 */       setBlockState(worldIn, Blocks.nether_brick_fence.getDefaultState(), 0, 13, 0, structureBoundingBoxIn);
/*  623 */       setBlockState(worldIn, Blocks.nether_brick_fence.getDefaultState(), 0, 13, 12, structureBoundingBoxIn);
/*  624 */       setBlockState(worldIn, Blocks.nether_brick_fence.getDefaultState(), 0, 13, 0, structureBoundingBoxIn);
/*  625 */       setBlockState(worldIn, Blocks.nether_brick_fence.getDefaultState(), 12, 13, 0, structureBoundingBoxIn);
/*      */       
/*  627 */       for (int k = 3; k <= 9; k += 2) {
/*  628 */         fillWithBlocks(worldIn, structureBoundingBoxIn, 1, 7, k, 1, 8, k, Blocks.nether_brick_fence.getDefaultState(), Blocks.nether_brick_fence.getDefaultState(), false);
/*  629 */         fillWithBlocks(worldIn, structureBoundingBoxIn, 11, 7, k, 11, 8, k, Blocks.nether_brick_fence.getDefaultState(), Blocks.nether_brick_fence.getDefaultState(), false);
/*      */       } 
/*      */       
/*  632 */       fillWithBlocks(worldIn, structureBoundingBoxIn, 4, 2, 0, 8, 2, 12, Blocks.nether_brick.getDefaultState(), Blocks.nether_brick.getDefaultState(), false);
/*  633 */       fillWithBlocks(worldIn, structureBoundingBoxIn, 0, 2, 4, 12, 2, 8, Blocks.nether_brick.getDefaultState(), Blocks.nether_brick.getDefaultState(), false);
/*  634 */       fillWithBlocks(worldIn, structureBoundingBoxIn, 4, 0, 0, 8, 1, 3, Blocks.nether_brick.getDefaultState(), Blocks.nether_brick.getDefaultState(), false);
/*  635 */       fillWithBlocks(worldIn, structureBoundingBoxIn, 4, 0, 9, 8, 1, 12, Blocks.nether_brick.getDefaultState(), Blocks.nether_brick.getDefaultState(), false);
/*  636 */       fillWithBlocks(worldIn, structureBoundingBoxIn, 0, 0, 4, 3, 1, 8, Blocks.nether_brick.getDefaultState(), Blocks.nether_brick.getDefaultState(), false);
/*  637 */       fillWithBlocks(worldIn, structureBoundingBoxIn, 9, 0, 4, 12, 1, 8, Blocks.nether_brick.getDefaultState(), Blocks.nether_brick.getDefaultState(), false);
/*      */       
/*  639 */       for (int l = 4; l <= 8; l++) {
/*  640 */         for (int j = 0; j <= 2; j++) {
/*  641 */           replaceAirAndLiquidDownwards(worldIn, Blocks.nether_brick.getDefaultState(), l, -1, j, structureBoundingBoxIn);
/*  642 */           replaceAirAndLiquidDownwards(worldIn, Blocks.nether_brick.getDefaultState(), l, -1, 12 - j, structureBoundingBoxIn);
/*      */         } 
/*      */       } 
/*      */       
/*  646 */       for (int i1 = 0; i1 <= 2; i1++) {
/*  647 */         for (int j1 = 4; j1 <= 8; j1++) {
/*  648 */           replaceAirAndLiquidDownwards(worldIn, Blocks.nether_brick.getDefaultState(), i1, -1, j1, structureBoundingBoxIn);
/*  649 */           replaceAirAndLiquidDownwards(worldIn, Blocks.nether_brick.getDefaultState(), 12 - i1, -1, j1, structureBoundingBoxIn);
/*      */         } 
/*      */       } 
/*      */       
/*  653 */       fillWithBlocks(worldIn, structureBoundingBoxIn, 5, 5, 5, 7, 5, 7, Blocks.nether_brick.getDefaultState(), Blocks.nether_brick.getDefaultState(), false);
/*  654 */       fillWithBlocks(worldIn, structureBoundingBoxIn, 6, 1, 6, 6, 4, 6, Blocks.air.getDefaultState(), Blocks.air.getDefaultState(), false);
/*  655 */       setBlockState(worldIn, Blocks.nether_brick.getDefaultState(), 6, 0, 6, structureBoundingBoxIn);
/*  656 */       setBlockState(worldIn, Blocks.flowing_lava.getDefaultState(), 6, 5, 6, structureBoundingBoxIn);
/*  657 */       BlockPos blockpos = new BlockPos(getXWithOffset(6, 6), getYWithOffset(5), getZWithOffset(6, 6));
/*      */       
/*  659 */       if (structureBoundingBoxIn.isVecInside((Vec3i)blockpos)) {
/*  660 */         worldIn.forceBlockUpdateTick((Block)Blocks.flowing_lava, blockpos, randomIn);
/*      */       }
/*      */       
/*  663 */       return true;
/*      */     }
/*      */   }
/*      */   
/*      */   public static class NetherStalkRoom
/*      */     extends Piece {
/*      */     public NetherStalkRoom() {}
/*      */     
/*      */     public NetherStalkRoom(int p_i45612_1_, Random p_i45612_2_, StructureBoundingBox p_i45612_3_, EnumFacing p_i45612_4_) {
/*  672 */       super(p_i45612_1_);
/*  673 */       this.coordBaseMode = p_i45612_4_;
/*  674 */       this.boundingBox = p_i45612_3_;
/*      */     }
/*      */     
/*      */     public void buildComponent(StructureComponent componentIn, List<StructureComponent> listIn, Random rand) {
/*  678 */       getNextComponentNormal((StructureNetherBridgePieces.Start)componentIn, listIn, rand, 5, 3, true);
/*  679 */       getNextComponentNormal((StructureNetherBridgePieces.Start)componentIn, listIn, rand, 5, 11, true);
/*      */     }
/*      */     
/*      */     public static NetherStalkRoom func_175875_a(List<StructureComponent> p_175875_0_, Random p_175875_1_, int p_175875_2_, int p_175875_3_, int p_175875_4_, EnumFacing p_175875_5_, int p_175875_6_) {
/*  683 */       StructureBoundingBox structureboundingbox = StructureBoundingBox.getComponentToAddBoundingBox(p_175875_2_, p_175875_3_, p_175875_4_, -5, -3, 0, 13, 14, 13, p_175875_5_);
/*  684 */       return (isAboveGround(structureboundingbox) && StructureComponent.findIntersecting(p_175875_0_, structureboundingbox) == null) ? new NetherStalkRoom(p_175875_6_, p_175875_1_, structureboundingbox, p_175875_5_) : null;
/*      */     }
/*      */     
/*      */     public boolean addComponentParts(World worldIn, Random randomIn, StructureBoundingBox structureBoundingBoxIn) {
/*  688 */       fillWithBlocks(worldIn, structureBoundingBoxIn, 0, 3, 0, 12, 4, 12, Blocks.nether_brick.getDefaultState(), Blocks.nether_brick.getDefaultState(), false);
/*  689 */       fillWithBlocks(worldIn, structureBoundingBoxIn, 0, 5, 0, 12, 13, 12, Blocks.air.getDefaultState(), Blocks.air.getDefaultState(), false);
/*  690 */       fillWithBlocks(worldIn, structureBoundingBoxIn, 0, 5, 0, 1, 12, 12, Blocks.nether_brick.getDefaultState(), Blocks.nether_brick.getDefaultState(), false);
/*  691 */       fillWithBlocks(worldIn, structureBoundingBoxIn, 11, 5, 0, 12, 12, 12, Blocks.nether_brick.getDefaultState(), Blocks.nether_brick.getDefaultState(), false);
/*  692 */       fillWithBlocks(worldIn, structureBoundingBoxIn, 2, 5, 11, 4, 12, 12, Blocks.nether_brick.getDefaultState(), Blocks.nether_brick.getDefaultState(), false);
/*  693 */       fillWithBlocks(worldIn, structureBoundingBoxIn, 8, 5, 11, 10, 12, 12, Blocks.nether_brick.getDefaultState(), Blocks.nether_brick.getDefaultState(), false);
/*  694 */       fillWithBlocks(worldIn, structureBoundingBoxIn, 5, 9, 11, 7, 12, 12, Blocks.nether_brick.getDefaultState(), Blocks.nether_brick.getDefaultState(), false);
/*  695 */       fillWithBlocks(worldIn, structureBoundingBoxIn, 2, 5, 0, 4, 12, 1, Blocks.nether_brick.getDefaultState(), Blocks.nether_brick.getDefaultState(), false);
/*  696 */       fillWithBlocks(worldIn, structureBoundingBoxIn, 8, 5, 0, 10, 12, 1, Blocks.nether_brick.getDefaultState(), Blocks.nether_brick.getDefaultState(), false);
/*  697 */       fillWithBlocks(worldIn, structureBoundingBoxIn, 5, 9, 0, 7, 12, 1, Blocks.nether_brick.getDefaultState(), Blocks.nether_brick.getDefaultState(), false);
/*  698 */       fillWithBlocks(worldIn, structureBoundingBoxIn, 2, 11, 2, 10, 12, 10, Blocks.nether_brick.getDefaultState(), Blocks.nether_brick.getDefaultState(), false);
/*      */       
/*  700 */       for (int i = 1; i <= 11; i += 2) {
/*  701 */         fillWithBlocks(worldIn, structureBoundingBoxIn, i, 10, 0, i, 11, 0, Blocks.nether_brick_fence.getDefaultState(), Blocks.nether_brick_fence.getDefaultState(), false);
/*  702 */         fillWithBlocks(worldIn, structureBoundingBoxIn, i, 10, 12, i, 11, 12, Blocks.nether_brick_fence.getDefaultState(), Blocks.nether_brick_fence.getDefaultState(), false);
/*  703 */         fillWithBlocks(worldIn, structureBoundingBoxIn, 0, 10, i, 0, 11, i, Blocks.nether_brick_fence.getDefaultState(), Blocks.nether_brick_fence.getDefaultState(), false);
/*  704 */         fillWithBlocks(worldIn, structureBoundingBoxIn, 12, 10, i, 12, 11, i, Blocks.nether_brick_fence.getDefaultState(), Blocks.nether_brick_fence.getDefaultState(), false);
/*  705 */         setBlockState(worldIn, Blocks.nether_brick.getDefaultState(), i, 13, 0, structureBoundingBoxIn);
/*  706 */         setBlockState(worldIn, Blocks.nether_brick.getDefaultState(), i, 13, 12, structureBoundingBoxIn);
/*  707 */         setBlockState(worldIn, Blocks.nether_brick.getDefaultState(), 0, 13, i, structureBoundingBoxIn);
/*  708 */         setBlockState(worldIn, Blocks.nether_brick.getDefaultState(), 12, 13, i, structureBoundingBoxIn);
/*  709 */         setBlockState(worldIn, Blocks.nether_brick_fence.getDefaultState(), i + 1, 13, 0, structureBoundingBoxIn);
/*  710 */         setBlockState(worldIn, Blocks.nether_brick_fence.getDefaultState(), i + 1, 13, 12, structureBoundingBoxIn);
/*  711 */         setBlockState(worldIn, Blocks.nether_brick_fence.getDefaultState(), 0, 13, i + 1, structureBoundingBoxIn);
/*  712 */         setBlockState(worldIn, Blocks.nether_brick_fence.getDefaultState(), 12, 13, i + 1, structureBoundingBoxIn);
/*      */       } 
/*      */       
/*  715 */       setBlockState(worldIn, Blocks.nether_brick_fence.getDefaultState(), 0, 13, 0, structureBoundingBoxIn);
/*  716 */       setBlockState(worldIn, Blocks.nether_brick_fence.getDefaultState(), 0, 13, 12, structureBoundingBoxIn);
/*  717 */       setBlockState(worldIn, Blocks.nether_brick_fence.getDefaultState(), 0, 13, 0, structureBoundingBoxIn);
/*  718 */       setBlockState(worldIn, Blocks.nether_brick_fence.getDefaultState(), 12, 13, 0, structureBoundingBoxIn);
/*      */       
/*  720 */       for (int j1 = 3; j1 <= 9; j1 += 2) {
/*  721 */         fillWithBlocks(worldIn, structureBoundingBoxIn, 1, 7, j1, 1, 8, j1, Blocks.nether_brick_fence.getDefaultState(), Blocks.nether_brick_fence.getDefaultState(), false);
/*  722 */         fillWithBlocks(worldIn, structureBoundingBoxIn, 11, 7, j1, 11, 8, j1, Blocks.nether_brick_fence.getDefaultState(), Blocks.nether_brick_fence.getDefaultState(), false);
/*      */       } 
/*      */       
/*  725 */       int k1 = getMetadataWithOffset(Blocks.nether_brick_stairs, 3);
/*      */       
/*  727 */       for (int j = 0; j <= 6; j++) {
/*  728 */         int k = j + 4;
/*      */         
/*  730 */         for (int l = 5; l <= 7; l++) {
/*  731 */           setBlockState(worldIn, Blocks.nether_brick_stairs.getStateFromMeta(k1), l, 5 + j, k, structureBoundingBoxIn);
/*      */         }
/*      */         
/*  734 */         if (k >= 5 && k <= 8) {
/*  735 */           fillWithBlocks(worldIn, structureBoundingBoxIn, 5, 5, k, 7, j + 4, k, Blocks.nether_brick.getDefaultState(), Blocks.nether_brick.getDefaultState(), false);
/*  736 */         } else if (k >= 9 && k <= 10) {
/*  737 */           fillWithBlocks(worldIn, structureBoundingBoxIn, 5, 8, k, 7, j + 4, k, Blocks.nether_brick.getDefaultState(), Blocks.nether_brick.getDefaultState(), false);
/*      */         } 
/*      */         
/*  740 */         if (j >= 1) {
/*  741 */           fillWithBlocks(worldIn, structureBoundingBoxIn, 5, 6 + j, k, 7, 9 + j, k, Blocks.air.getDefaultState(), Blocks.air.getDefaultState(), false);
/*      */         }
/*      */       } 
/*      */       
/*  745 */       for (int l1 = 5; l1 <= 7; l1++) {
/*  746 */         setBlockState(worldIn, Blocks.nether_brick_stairs.getStateFromMeta(k1), l1, 12, 11, structureBoundingBoxIn);
/*      */       }
/*      */       
/*  749 */       fillWithBlocks(worldIn, structureBoundingBoxIn, 5, 6, 7, 5, 7, 7, Blocks.nether_brick_fence.getDefaultState(), Blocks.nether_brick_fence.getDefaultState(), false);
/*  750 */       fillWithBlocks(worldIn, structureBoundingBoxIn, 7, 6, 7, 7, 7, 7, Blocks.nether_brick_fence.getDefaultState(), Blocks.nether_brick_fence.getDefaultState(), false);
/*  751 */       fillWithBlocks(worldIn, structureBoundingBoxIn, 5, 13, 12, 7, 13, 12, Blocks.air.getDefaultState(), Blocks.air.getDefaultState(), false);
/*  752 */       fillWithBlocks(worldIn, structureBoundingBoxIn, 2, 5, 2, 3, 5, 3, Blocks.nether_brick.getDefaultState(), Blocks.nether_brick.getDefaultState(), false);
/*  753 */       fillWithBlocks(worldIn, structureBoundingBoxIn, 2, 5, 9, 3, 5, 10, Blocks.nether_brick.getDefaultState(), Blocks.nether_brick.getDefaultState(), false);
/*  754 */       fillWithBlocks(worldIn, structureBoundingBoxIn, 2, 5, 4, 2, 5, 8, Blocks.nether_brick.getDefaultState(), Blocks.nether_brick.getDefaultState(), false);
/*  755 */       fillWithBlocks(worldIn, structureBoundingBoxIn, 9, 5, 2, 10, 5, 3, Blocks.nether_brick.getDefaultState(), Blocks.nether_brick.getDefaultState(), false);
/*  756 */       fillWithBlocks(worldIn, structureBoundingBoxIn, 9, 5, 9, 10, 5, 10, Blocks.nether_brick.getDefaultState(), Blocks.nether_brick.getDefaultState(), false);
/*  757 */       fillWithBlocks(worldIn, structureBoundingBoxIn, 10, 5, 4, 10, 5, 8, Blocks.nether_brick.getDefaultState(), Blocks.nether_brick.getDefaultState(), false);
/*  758 */       int i2 = getMetadataWithOffset(Blocks.nether_brick_stairs, 0);
/*  759 */       int j2 = getMetadataWithOffset(Blocks.nether_brick_stairs, 1);
/*  760 */       setBlockState(worldIn, Blocks.nether_brick_stairs.getStateFromMeta(j2), 4, 5, 2, structureBoundingBoxIn);
/*  761 */       setBlockState(worldIn, Blocks.nether_brick_stairs.getStateFromMeta(j2), 4, 5, 3, structureBoundingBoxIn);
/*  762 */       setBlockState(worldIn, Blocks.nether_brick_stairs.getStateFromMeta(j2), 4, 5, 9, structureBoundingBoxIn);
/*  763 */       setBlockState(worldIn, Blocks.nether_brick_stairs.getStateFromMeta(j2), 4, 5, 10, structureBoundingBoxIn);
/*  764 */       setBlockState(worldIn, Blocks.nether_brick_stairs.getStateFromMeta(i2), 8, 5, 2, structureBoundingBoxIn);
/*  765 */       setBlockState(worldIn, Blocks.nether_brick_stairs.getStateFromMeta(i2), 8, 5, 3, structureBoundingBoxIn);
/*  766 */       setBlockState(worldIn, Blocks.nether_brick_stairs.getStateFromMeta(i2), 8, 5, 9, structureBoundingBoxIn);
/*  767 */       setBlockState(worldIn, Blocks.nether_brick_stairs.getStateFromMeta(i2), 8, 5, 10, structureBoundingBoxIn);
/*  768 */       fillWithBlocks(worldIn, structureBoundingBoxIn, 3, 4, 4, 4, 4, 8, Blocks.soul_sand.getDefaultState(), Blocks.soul_sand.getDefaultState(), false);
/*  769 */       fillWithBlocks(worldIn, structureBoundingBoxIn, 8, 4, 4, 9, 4, 8, Blocks.soul_sand.getDefaultState(), Blocks.soul_sand.getDefaultState(), false);
/*  770 */       fillWithBlocks(worldIn, structureBoundingBoxIn, 3, 5, 4, 4, 5, 8, Blocks.nether_wart.getDefaultState(), Blocks.nether_wart.getDefaultState(), false);
/*  771 */       fillWithBlocks(worldIn, structureBoundingBoxIn, 8, 5, 4, 9, 5, 8, Blocks.nether_wart.getDefaultState(), Blocks.nether_wart.getDefaultState(), false);
/*  772 */       fillWithBlocks(worldIn, structureBoundingBoxIn, 4, 2, 0, 8, 2, 12, Blocks.nether_brick.getDefaultState(), Blocks.nether_brick.getDefaultState(), false);
/*  773 */       fillWithBlocks(worldIn, structureBoundingBoxIn, 0, 2, 4, 12, 2, 8, Blocks.nether_brick.getDefaultState(), Blocks.nether_brick.getDefaultState(), false);
/*  774 */       fillWithBlocks(worldIn, structureBoundingBoxIn, 4, 0, 0, 8, 1, 3, Blocks.nether_brick.getDefaultState(), Blocks.nether_brick.getDefaultState(), false);
/*  775 */       fillWithBlocks(worldIn, structureBoundingBoxIn, 4, 0, 9, 8, 1, 12, Blocks.nether_brick.getDefaultState(), Blocks.nether_brick.getDefaultState(), false);
/*  776 */       fillWithBlocks(worldIn, structureBoundingBoxIn, 0, 0, 4, 3, 1, 8, Blocks.nether_brick.getDefaultState(), Blocks.nether_brick.getDefaultState(), false);
/*  777 */       fillWithBlocks(worldIn, structureBoundingBoxIn, 9, 0, 4, 12, 1, 8, Blocks.nether_brick.getDefaultState(), Blocks.nether_brick.getDefaultState(), false);
/*      */       
/*  779 */       for (int k2 = 4; k2 <= 8; k2++) {
/*  780 */         for (int i1 = 0; i1 <= 2; i1++) {
/*  781 */           replaceAirAndLiquidDownwards(worldIn, Blocks.nether_brick.getDefaultState(), k2, -1, i1, structureBoundingBoxIn);
/*  782 */           replaceAirAndLiquidDownwards(worldIn, Blocks.nether_brick.getDefaultState(), k2, -1, 12 - i1, structureBoundingBoxIn);
/*      */         } 
/*      */       } 
/*      */       
/*  786 */       for (int l2 = 0; l2 <= 2; l2++) {
/*  787 */         for (int i3 = 4; i3 <= 8; i3++) {
/*  788 */           replaceAirAndLiquidDownwards(worldIn, Blocks.nether_brick.getDefaultState(), l2, -1, i3, structureBoundingBoxIn);
/*  789 */           replaceAirAndLiquidDownwards(worldIn, Blocks.nether_brick.getDefaultState(), 12 - l2, -1, i3, structureBoundingBoxIn);
/*      */         } 
/*      */       } 
/*      */       
/*  793 */       return true;
/*      */     }
/*      */   }
/*      */   
/*      */   static abstract class Piece extends StructureComponent {
/*  798 */     protected static final List<WeightedRandomChestContent> field_111019_a = Lists.newArrayList((Object[])new WeightedRandomChestContent[] { new WeightedRandomChestContent(Items.diamond, 0, 1, 3, 5), new WeightedRandomChestContent(Items.iron_ingot, 0, 1, 5, 5), new WeightedRandomChestContent(Items.gold_ingot, 0, 1, 3, 15), new WeightedRandomChestContent(Items.golden_sword, 0, 1, 1, 5), new WeightedRandomChestContent((Item)Items.golden_chestplate, 0, 1, 1, 5), new WeightedRandomChestContent(Items.flint_and_steel, 0, 1, 1, 5), new WeightedRandomChestContent(Items.nether_wart, 0, 3, 7, 5), new WeightedRandomChestContent(Items.saddle, 0, 1, 1, 10), new WeightedRandomChestContent(Items.golden_horse_armor, 0, 1, 1, 8), new WeightedRandomChestContent(Items.iron_horse_armor, 0, 1, 1, 5), new WeightedRandomChestContent(Items.diamond_horse_armor, 0, 1, 1, 3), new WeightedRandomChestContent(Item.getItemFromBlock(Blocks.obsidian), 0, 2, 4, 2) });
/*      */ 
/*      */     
/*      */     public Piece() {}
/*      */     
/*      */     protected Piece(int p_i2054_1_) {
/*  804 */       super(p_i2054_1_);
/*      */     }
/*      */ 
/*      */     
/*      */     protected void readStructureFromNBT(NBTTagCompound tagCompound) {}
/*      */ 
/*      */     
/*      */     protected void writeStructureToNBT(NBTTagCompound tagCompound) {}
/*      */     
/*      */     private int getTotalWeight(List<StructureNetherBridgePieces.PieceWeight> p_74960_1_) {
/*  814 */       boolean flag = false;
/*  815 */       int i = 0;
/*      */       
/*  817 */       for (StructureNetherBridgePieces.PieceWeight structurenetherbridgepieces$pieceweight : p_74960_1_) {
/*  818 */         if (structurenetherbridgepieces$pieceweight.field_78824_d > 0 && structurenetherbridgepieces$pieceweight.field_78827_c < structurenetherbridgepieces$pieceweight.field_78824_d) {
/*  819 */           flag = true;
/*      */         }
/*      */         
/*  822 */         i += structurenetherbridgepieces$pieceweight.field_78826_b;
/*      */       } 
/*      */       
/*  825 */       return flag ? i : -1;
/*      */     }
/*      */     
/*      */     private Piece func_175871_a(StructureNetherBridgePieces.Start p_175871_1_, List<StructureNetherBridgePieces.PieceWeight> p_175871_2_, List<StructureComponent> p_175871_3_, Random p_175871_4_, int p_175871_5_, int p_175871_6_, int p_175871_7_, EnumFacing p_175871_8_, int p_175871_9_) {
/*  829 */       int i = getTotalWeight(p_175871_2_);
/*  830 */       boolean flag = (i > 0 && p_175871_9_ <= 30);
/*  831 */       int j = 0;
/*      */       
/*  833 */       while (j < 5 && flag) {
/*  834 */         j++;
/*  835 */         int k = p_175871_4_.nextInt(i);
/*      */         
/*  837 */         for (StructureNetherBridgePieces.PieceWeight structurenetherbridgepieces$pieceweight : p_175871_2_) {
/*  838 */           k -= structurenetherbridgepieces$pieceweight.field_78826_b;
/*      */           
/*  840 */           if (k < 0) {
/*  841 */             if (!structurenetherbridgepieces$pieceweight.func_78822_a(p_175871_9_) || (structurenetherbridgepieces$pieceweight == p_175871_1_.theNetherBridgePieceWeight && !structurenetherbridgepieces$pieceweight.field_78825_e)) {
/*      */               break;
/*      */             }
/*      */             
/*  845 */             Piece structurenetherbridgepieces$piece = StructureNetherBridgePieces.func_175887_b(structurenetherbridgepieces$pieceweight, p_175871_3_, p_175871_4_, p_175871_5_, p_175871_6_, p_175871_7_, p_175871_8_, p_175871_9_);
/*      */             
/*  847 */             if (structurenetherbridgepieces$piece != null) {
/*  848 */               structurenetherbridgepieces$pieceweight.field_78827_c++;
/*  849 */               p_175871_1_.theNetherBridgePieceWeight = structurenetherbridgepieces$pieceweight;
/*      */               
/*  851 */               if (!structurenetherbridgepieces$pieceweight.func_78823_a()) {
/*  852 */                 p_175871_2_.remove(structurenetherbridgepieces$pieceweight);
/*      */               }
/*      */               
/*  855 */               return structurenetherbridgepieces$piece;
/*      */             } 
/*      */           } 
/*      */         } 
/*      */       } 
/*      */       
/*  861 */       return StructureNetherBridgePieces.End.func_175884_a(p_175871_3_, p_175871_4_, p_175871_5_, p_175871_6_, p_175871_7_, p_175871_8_, p_175871_9_);
/*      */     }
/*      */     
/*      */     private StructureComponent func_175870_a(StructureNetherBridgePieces.Start p_175870_1_, List<StructureComponent> p_175870_2_, Random p_175870_3_, int p_175870_4_, int p_175870_5_, int p_175870_6_, EnumFacing p_175870_7_, int p_175870_8_, boolean p_175870_9_) {
/*  865 */       if (Math.abs(p_175870_4_ - (p_175870_1_.getBoundingBox()).minX) <= 112 && Math.abs(p_175870_6_ - (p_175870_1_.getBoundingBox()).minZ) <= 112) {
/*  866 */         List<StructureNetherBridgePieces.PieceWeight> list = p_175870_1_.primaryWeights;
/*      */         
/*  868 */         if (p_175870_9_) {
/*  869 */           list = p_175870_1_.secondaryWeights;
/*      */         }
/*      */         
/*  872 */         StructureComponent structurecomponent = func_175871_a(p_175870_1_, list, p_175870_2_, p_175870_3_, p_175870_4_, p_175870_5_, p_175870_6_, p_175870_7_, p_175870_8_ + 1);
/*      */         
/*  874 */         if (structurecomponent != null) {
/*  875 */           p_175870_2_.add(structurecomponent);
/*  876 */           p_175870_1_.field_74967_d.add(structurecomponent);
/*      */         } 
/*      */         
/*  879 */         return structurecomponent;
/*      */       } 
/*  881 */       return StructureNetherBridgePieces.End.func_175884_a(p_175870_2_, p_175870_3_, p_175870_4_, p_175870_5_, p_175870_6_, p_175870_7_, p_175870_8_);
/*      */     }
/*      */ 
/*      */     
/*      */     protected StructureComponent getNextComponentNormal(StructureNetherBridgePieces.Start p_74963_1_, List<StructureComponent> p_74963_2_, Random p_74963_3_, int p_74963_4_, int p_74963_5_, boolean p_74963_6_) {
/*  886 */       if (this.coordBaseMode != null) {
/*  887 */         switch (this.coordBaseMode) {
/*      */           case NORTH:
/*  889 */             return func_175870_a(p_74963_1_, p_74963_2_, p_74963_3_, this.boundingBox.minX + p_74963_4_, this.boundingBox.minY + p_74963_5_, this.boundingBox.minZ - 1, this.coordBaseMode, getComponentType(), p_74963_6_);
/*      */           
/*      */           case SOUTH:
/*  892 */             return func_175870_a(p_74963_1_, p_74963_2_, p_74963_3_, this.boundingBox.minX + p_74963_4_, this.boundingBox.minY + p_74963_5_, this.boundingBox.maxZ + 1, this.coordBaseMode, getComponentType(), p_74963_6_);
/*      */           
/*      */           case WEST:
/*  895 */             return func_175870_a(p_74963_1_, p_74963_2_, p_74963_3_, this.boundingBox.minX - 1, this.boundingBox.minY + p_74963_5_, this.boundingBox.minZ + p_74963_4_, this.coordBaseMode, getComponentType(), p_74963_6_);
/*      */           
/*      */           case EAST:
/*  898 */             return func_175870_a(p_74963_1_, p_74963_2_, p_74963_3_, this.boundingBox.maxX + 1, this.boundingBox.minY + p_74963_5_, this.boundingBox.minZ + p_74963_4_, this.coordBaseMode, getComponentType(), p_74963_6_);
/*      */         } 
/*      */       
/*      */       }
/*  902 */       return null;
/*      */     }
/*      */     
/*      */     protected StructureComponent getNextComponentX(StructureNetherBridgePieces.Start p_74961_1_, List<StructureComponent> p_74961_2_, Random p_74961_3_, int p_74961_4_, int p_74961_5_, boolean p_74961_6_) {
/*  906 */       if (this.coordBaseMode != null) {
/*  907 */         switch (this.coordBaseMode) {
/*      */           case NORTH:
/*  909 */             return func_175870_a(p_74961_1_, p_74961_2_, p_74961_3_, this.boundingBox.minX - 1, this.boundingBox.minY + p_74961_4_, this.boundingBox.minZ + p_74961_5_, EnumFacing.WEST, getComponentType(), p_74961_6_);
/*      */           
/*      */           case SOUTH:
/*  912 */             return func_175870_a(p_74961_1_, p_74961_2_, p_74961_3_, this.boundingBox.minX - 1, this.boundingBox.minY + p_74961_4_, this.boundingBox.minZ + p_74961_5_, EnumFacing.WEST, getComponentType(), p_74961_6_);
/*      */           
/*      */           case WEST:
/*  915 */             return func_175870_a(p_74961_1_, p_74961_2_, p_74961_3_, this.boundingBox.minX + p_74961_5_, this.boundingBox.minY + p_74961_4_, this.boundingBox.minZ - 1, EnumFacing.NORTH, getComponentType(), p_74961_6_);
/*      */           
/*      */           case EAST:
/*  918 */             return func_175870_a(p_74961_1_, p_74961_2_, p_74961_3_, this.boundingBox.minX + p_74961_5_, this.boundingBox.minY + p_74961_4_, this.boundingBox.minZ - 1, EnumFacing.NORTH, getComponentType(), p_74961_6_);
/*      */         } 
/*      */       
/*      */       }
/*  922 */       return null;
/*      */     }
/*      */     
/*      */     protected StructureComponent getNextComponentZ(StructureNetherBridgePieces.Start p_74965_1_, List<StructureComponent> p_74965_2_, Random p_74965_3_, int p_74965_4_, int p_74965_5_, boolean p_74965_6_) {
/*  926 */       if (this.coordBaseMode != null) {
/*  927 */         switch (this.coordBaseMode) {
/*      */           case NORTH:
/*  929 */             return func_175870_a(p_74965_1_, p_74965_2_, p_74965_3_, this.boundingBox.maxX + 1, this.boundingBox.minY + p_74965_4_, this.boundingBox.minZ + p_74965_5_, EnumFacing.EAST, getComponentType(), p_74965_6_);
/*      */           
/*      */           case SOUTH:
/*  932 */             return func_175870_a(p_74965_1_, p_74965_2_, p_74965_3_, this.boundingBox.maxX + 1, this.boundingBox.minY + p_74965_4_, this.boundingBox.minZ + p_74965_5_, EnumFacing.EAST, getComponentType(), p_74965_6_);
/*      */           
/*      */           case WEST:
/*  935 */             return func_175870_a(p_74965_1_, p_74965_2_, p_74965_3_, this.boundingBox.minX + p_74965_5_, this.boundingBox.minY + p_74965_4_, this.boundingBox.maxZ + 1, EnumFacing.SOUTH, getComponentType(), p_74965_6_);
/*      */           
/*      */           case EAST:
/*  938 */             return func_175870_a(p_74965_1_, p_74965_2_, p_74965_3_, this.boundingBox.minX + p_74965_5_, this.boundingBox.minY + p_74965_4_, this.boundingBox.maxZ + 1, EnumFacing.SOUTH, getComponentType(), p_74965_6_);
/*      */         } 
/*      */       
/*      */       }
/*  942 */       return null;
/*      */     }
/*      */     
/*      */     protected static boolean isAboveGround(StructureBoundingBox p_74964_0_) {
/*  946 */       return (p_74964_0_ != null && p_74964_0_.minY > 10);
/*      */     }
/*      */   }
/*      */   
/*      */   static class PieceWeight {
/*      */     public Class<? extends StructureNetherBridgePieces.Piece> weightClass;
/*      */     public final int field_78826_b;
/*      */     public int field_78827_c;
/*      */     public int field_78824_d;
/*      */     public boolean field_78825_e;
/*      */     
/*      */     public PieceWeight(Class<? extends StructureNetherBridgePieces.Piece> p_i2055_1_, int p_i2055_2_, int p_i2055_3_, boolean p_i2055_4_) {
/*  958 */       this.weightClass = p_i2055_1_;
/*  959 */       this.field_78826_b = p_i2055_2_;
/*  960 */       this.field_78824_d = p_i2055_3_;
/*  961 */       this.field_78825_e = p_i2055_4_;
/*      */     }
/*      */     
/*      */     public PieceWeight(Class<? extends StructureNetherBridgePieces.Piece> p_i2056_1_, int p_i2056_2_, int p_i2056_3_) {
/*  965 */       this(p_i2056_1_, p_i2056_2_, p_i2056_3_, false);
/*      */     }
/*      */     
/*      */     public boolean func_78822_a(int p_78822_1_) {
/*  969 */       return !(this.field_78824_d != 0 && this.field_78827_c >= this.field_78824_d);
/*      */     }
/*      */     
/*      */     public boolean func_78823_a() {
/*  973 */       return !(this.field_78824_d != 0 && this.field_78827_c >= this.field_78824_d);
/*      */     }
/*      */   }
/*      */   
/*      */   public static class Stairs
/*      */     extends Piece {
/*      */     public Stairs() {}
/*      */     
/*      */     public Stairs(int p_i45609_1_, Random p_i45609_2_, StructureBoundingBox p_i45609_3_, EnumFacing p_i45609_4_) {
/*  982 */       super(p_i45609_1_);
/*  983 */       this.coordBaseMode = p_i45609_4_;
/*  984 */       this.boundingBox = p_i45609_3_;
/*      */     }
/*      */     
/*      */     public void buildComponent(StructureComponent componentIn, List<StructureComponent> listIn, Random rand) {
/*  988 */       getNextComponentZ((StructureNetherBridgePieces.Start)componentIn, listIn, rand, 6, 2, false);
/*      */     }
/*      */     
/*      */     public static Stairs func_175872_a(List<StructureComponent> p_175872_0_, Random p_175872_1_, int p_175872_2_, int p_175872_3_, int p_175872_4_, int p_175872_5_, EnumFacing p_175872_6_) {
/*  992 */       StructureBoundingBox structureboundingbox = StructureBoundingBox.getComponentToAddBoundingBox(p_175872_2_, p_175872_3_, p_175872_4_, -2, 0, 0, 7, 11, 7, p_175872_6_);
/*  993 */       return (isAboveGround(structureboundingbox) && StructureComponent.findIntersecting(p_175872_0_, structureboundingbox) == null) ? new Stairs(p_175872_5_, p_175872_1_, structureboundingbox, p_175872_6_) : null;
/*      */     }
/*      */     
/*      */     public boolean addComponentParts(World worldIn, Random randomIn, StructureBoundingBox structureBoundingBoxIn) {
/*  997 */       fillWithBlocks(worldIn, structureBoundingBoxIn, 0, 0, 0, 6, 1, 6, Blocks.nether_brick.getDefaultState(), Blocks.nether_brick.getDefaultState(), false);
/*  998 */       fillWithBlocks(worldIn, structureBoundingBoxIn, 0, 2, 0, 6, 10, 6, Blocks.air.getDefaultState(), Blocks.air.getDefaultState(), false);
/*  999 */       fillWithBlocks(worldIn, structureBoundingBoxIn, 0, 2, 0, 1, 8, 0, Blocks.nether_brick.getDefaultState(), Blocks.nether_brick.getDefaultState(), false);
/* 1000 */       fillWithBlocks(worldIn, structureBoundingBoxIn, 5, 2, 0, 6, 8, 0, Blocks.nether_brick.getDefaultState(), Blocks.nether_brick.getDefaultState(), false);
/* 1001 */       fillWithBlocks(worldIn, structureBoundingBoxIn, 0, 2, 1, 0, 8, 6, Blocks.nether_brick.getDefaultState(), Blocks.nether_brick.getDefaultState(), false);
/* 1002 */       fillWithBlocks(worldIn, structureBoundingBoxIn, 6, 2, 1, 6, 8, 6, Blocks.nether_brick.getDefaultState(), Blocks.nether_brick.getDefaultState(), false);
/* 1003 */       fillWithBlocks(worldIn, structureBoundingBoxIn, 1, 2, 6, 5, 8, 6, Blocks.nether_brick.getDefaultState(), Blocks.nether_brick.getDefaultState(), false);
/* 1004 */       fillWithBlocks(worldIn, structureBoundingBoxIn, 0, 3, 2, 0, 5, 4, Blocks.nether_brick_fence.getDefaultState(), Blocks.nether_brick_fence.getDefaultState(), false);
/* 1005 */       fillWithBlocks(worldIn, structureBoundingBoxIn, 6, 3, 2, 6, 5, 2, Blocks.nether_brick_fence.getDefaultState(), Blocks.nether_brick_fence.getDefaultState(), false);
/* 1006 */       fillWithBlocks(worldIn, structureBoundingBoxIn, 6, 3, 4, 6, 5, 4, Blocks.nether_brick_fence.getDefaultState(), Blocks.nether_brick_fence.getDefaultState(), false);
/* 1007 */       setBlockState(worldIn, Blocks.nether_brick.getDefaultState(), 5, 2, 5, structureBoundingBoxIn);
/* 1008 */       fillWithBlocks(worldIn, structureBoundingBoxIn, 4, 2, 5, 4, 3, 5, Blocks.nether_brick.getDefaultState(), Blocks.nether_brick.getDefaultState(), false);
/* 1009 */       fillWithBlocks(worldIn, structureBoundingBoxIn, 3, 2, 5, 3, 4, 5, Blocks.nether_brick.getDefaultState(), Blocks.nether_brick.getDefaultState(), false);
/* 1010 */       fillWithBlocks(worldIn, structureBoundingBoxIn, 2, 2, 5, 2, 5, 5, Blocks.nether_brick.getDefaultState(), Blocks.nether_brick.getDefaultState(), false);
/* 1011 */       fillWithBlocks(worldIn, structureBoundingBoxIn, 1, 2, 5, 1, 6, 5, Blocks.nether_brick.getDefaultState(), Blocks.nether_brick.getDefaultState(), false);
/* 1012 */       fillWithBlocks(worldIn, structureBoundingBoxIn, 1, 7, 1, 5, 7, 4, Blocks.nether_brick.getDefaultState(), Blocks.nether_brick.getDefaultState(), false);
/* 1013 */       fillWithBlocks(worldIn, structureBoundingBoxIn, 6, 8, 2, 6, 8, 4, Blocks.air.getDefaultState(), Blocks.air.getDefaultState(), false);
/* 1014 */       fillWithBlocks(worldIn, structureBoundingBoxIn, 2, 6, 0, 4, 8, 0, Blocks.nether_brick.getDefaultState(), Blocks.nether_brick.getDefaultState(), false);
/* 1015 */       fillWithBlocks(worldIn, structureBoundingBoxIn, 2, 5, 0, 4, 5, 0, Blocks.nether_brick_fence.getDefaultState(), Blocks.nether_brick_fence.getDefaultState(), false);
/*      */       
/* 1017 */       for (int i = 0; i <= 6; i++) {
/* 1018 */         for (int j = 0; j <= 6; j++) {
/* 1019 */           replaceAirAndLiquidDownwards(worldIn, Blocks.nether_brick.getDefaultState(), i, -1, j, structureBoundingBoxIn);
/*      */         }
/*      */       } 
/*      */       
/* 1023 */       return true;
/*      */     }
/*      */   }
/*      */   
/*      */   public static class Start extends Crossing3 {
/*      */     public StructureNetherBridgePieces.PieceWeight theNetherBridgePieceWeight;
/*      */     public List<StructureNetherBridgePieces.PieceWeight> primaryWeights;
/*      */     public List<StructureNetherBridgePieces.PieceWeight> secondaryWeights;
/* 1031 */     public List<StructureComponent> field_74967_d = Lists.newArrayList();
/*      */ 
/*      */     
/*      */     public Start() {}
/*      */     
/*      */     public Start(Random p_i2059_1_, int p_i2059_2_, int p_i2059_3_) {
/* 1037 */       super(p_i2059_1_, p_i2059_2_, p_i2059_3_);
/* 1038 */       this.primaryWeights = Lists.newArrayList(); byte b; int i;
/*      */       StructureNetherBridgePieces.PieceWeight[] arrayOfPieceWeight;
/* 1040 */       for (i = (arrayOfPieceWeight = StructureNetherBridgePieces.primaryComponents).length, b = 0; b < i; ) { StructureNetherBridgePieces.PieceWeight structurenetherbridgepieces$pieceweight = arrayOfPieceWeight[b];
/* 1041 */         structurenetherbridgepieces$pieceweight.field_78827_c = 0;
/* 1042 */         this.primaryWeights.add(structurenetherbridgepieces$pieceweight);
/*      */         b++; }
/*      */       
/* 1045 */       this.secondaryWeights = Lists.newArrayList();
/*      */       
/* 1047 */       for (i = (arrayOfPieceWeight = StructureNetherBridgePieces.secondaryComponents).length, b = 0; b < i; ) { StructureNetherBridgePieces.PieceWeight structurenetherbridgepieces$pieceweight1 = arrayOfPieceWeight[b];
/* 1048 */         structurenetherbridgepieces$pieceweight1.field_78827_c = 0;
/* 1049 */         this.secondaryWeights.add(structurenetherbridgepieces$pieceweight1);
/*      */         b++; }
/*      */     
/*      */     }
/*      */     protected void readStructureFromNBT(NBTTagCompound tagCompound) {
/* 1054 */       super.readStructureFromNBT(tagCompound);
/*      */     }
/*      */     
/*      */     protected void writeStructureToNBT(NBTTagCompound tagCompound) {
/* 1058 */       super.writeStructureToNBT(tagCompound);
/*      */     }
/*      */   }
/*      */   
/*      */   public static class Straight
/*      */     extends Piece {
/*      */     public Straight() {}
/*      */     
/*      */     public Straight(int p_i45620_1_, Random p_i45620_2_, StructureBoundingBox p_i45620_3_, EnumFacing p_i45620_4_) {
/* 1067 */       super(p_i45620_1_);
/* 1068 */       this.coordBaseMode = p_i45620_4_;
/* 1069 */       this.boundingBox = p_i45620_3_;
/*      */     }
/*      */     
/*      */     public void buildComponent(StructureComponent componentIn, List<StructureComponent> listIn, Random rand) {
/* 1073 */       getNextComponentNormal((StructureNetherBridgePieces.Start)componentIn, listIn, rand, 1, 3, false);
/*      */     }
/*      */     
/*      */     public static Straight func_175882_a(List<StructureComponent> p_175882_0_, Random p_175882_1_, int p_175882_2_, int p_175882_3_, int p_175882_4_, EnumFacing p_175882_5_, int p_175882_6_) {
/* 1077 */       StructureBoundingBox structureboundingbox = StructureBoundingBox.getComponentToAddBoundingBox(p_175882_2_, p_175882_3_, p_175882_4_, -1, -3, 0, 5, 10, 19, p_175882_5_);
/* 1078 */       return (isAboveGround(structureboundingbox) && StructureComponent.findIntersecting(p_175882_0_, structureboundingbox) == null) ? new Straight(p_175882_6_, p_175882_1_, structureboundingbox, p_175882_5_) : null;
/*      */     }
/*      */     
/*      */     public boolean addComponentParts(World worldIn, Random randomIn, StructureBoundingBox structureBoundingBoxIn) {
/* 1082 */       fillWithBlocks(worldIn, structureBoundingBoxIn, 0, 3, 0, 4, 4, 18, Blocks.nether_brick.getDefaultState(), Blocks.nether_brick.getDefaultState(), false);
/* 1083 */       fillWithBlocks(worldIn, structureBoundingBoxIn, 1, 5, 0, 3, 7, 18, Blocks.air.getDefaultState(), Blocks.air.getDefaultState(), false);
/* 1084 */       fillWithBlocks(worldIn, structureBoundingBoxIn, 0, 5, 0, 0, 5, 18, Blocks.nether_brick.getDefaultState(), Blocks.nether_brick.getDefaultState(), false);
/* 1085 */       fillWithBlocks(worldIn, structureBoundingBoxIn, 4, 5, 0, 4, 5, 18, Blocks.nether_brick.getDefaultState(), Blocks.nether_brick.getDefaultState(), false);
/* 1086 */       fillWithBlocks(worldIn, structureBoundingBoxIn, 0, 2, 0, 4, 2, 5, Blocks.nether_brick.getDefaultState(), Blocks.nether_brick.getDefaultState(), false);
/* 1087 */       fillWithBlocks(worldIn, structureBoundingBoxIn, 0, 2, 13, 4, 2, 18, Blocks.nether_brick.getDefaultState(), Blocks.nether_brick.getDefaultState(), false);
/* 1088 */       fillWithBlocks(worldIn, structureBoundingBoxIn, 0, 0, 0, 4, 1, 3, Blocks.nether_brick.getDefaultState(), Blocks.nether_brick.getDefaultState(), false);
/* 1089 */       fillWithBlocks(worldIn, structureBoundingBoxIn, 0, 0, 15, 4, 1, 18, Blocks.nether_brick.getDefaultState(), Blocks.nether_brick.getDefaultState(), false);
/*      */       
/* 1091 */       for (int i = 0; i <= 4; i++) {
/* 1092 */         for (int j = 0; j <= 2; j++) {
/* 1093 */           replaceAirAndLiquidDownwards(worldIn, Blocks.nether_brick.getDefaultState(), i, -1, j, structureBoundingBoxIn);
/* 1094 */           replaceAirAndLiquidDownwards(worldIn, Blocks.nether_brick.getDefaultState(), i, -1, 18 - j, structureBoundingBoxIn);
/*      */         } 
/*      */       } 
/*      */       
/* 1098 */       fillWithBlocks(worldIn, structureBoundingBoxIn, 0, 1, 1, 0, 4, 1, Blocks.nether_brick_fence.getDefaultState(), Blocks.nether_brick_fence.getDefaultState(), false);
/* 1099 */       fillWithBlocks(worldIn, structureBoundingBoxIn, 0, 3, 4, 0, 4, 4, Blocks.nether_brick_fence.getDefaultState(), Blocks.nether_brick_fence.getDefaultState(), false);
/* 1100 */       fillWithBlocks(worldIn, structureBoundingBoxIn, 0, 3, 14, 0, 4, 14, Blocks.nether_brick_fence.getDefaultState(), Blocks.nether_brick_fence.getDefaultState(), false);
/* 1101 */       fillWithBlocks(worldIn, structureBoundingBoxIn, 0, 1, 17, 0, 4, 17, Blocks.nether_brick_fence.getDefaultState(), Blocks.nether_brick_fence.getDefaultState(), false);
/* 1102 */       fillWithBlocks(worldIn, structureBoundingBoxIn, 4, 1, 1, 4, 4, 1, Blocks.nether_brick_fence.getDefaultState(), Blocks.nether_brick_fence.getDefaultState(), false);
/* 1103 */       fillWithBlocks(worldIn, structureBoundingBoxIn, 4, 3, 4, 4, 4, 4, Blocks.nether_brick_fence.getDefaultState(), Blocks.nether_brick_fence.getDefaultState(), false);
/* 1104 */       fillWithBlocks(worldIn, structureBoundingBoxIn, 4, 3, 14, 4, 4, 14, Blocks.nether_brick_fence.getDefaultState(), Blocks.nether_brick_fence.getDefaultState(), false);
/* 1105 */       fillWithBlocks(worldIn, structureBoundingBoxIn, 4, 1, 17, 4, 4, 17, Blocks.nether_brick_fence.getDefaultState(), Blocks.nether_brick_fence.getDefaultState(), false);
/* 1106 */       return true;
/*      */     }
/*      */   }
/*      */   
/*      */   public static class Throne
/*      */     extends Piece {
/*      */     private boolean hasSpawner;
/*      */     
/*      */     public Throne() {}
/*      */     
/*      */     public Throne(int p_i45611_1_, Random p_i45611_2_, StructureBoundingBox p_i45611_3_, EnumFacing p_i45611_4_) {
/* 1117 */       super(p_i45611_1_);
/* 1118 */       this.coordBaseMode = p_i45611_4_;
/* 1119 */       this.boundingBox = p_i45611_3_;
/*      */     }
/*      */     
/*      */     protected void readStructureFromNBT(NBTTagCompound tagCompound) {
/* 1123 */       super.readStructureFromNBT(tagCompound);
/* 1124 */       this.hasSpawner = tagCompound.getBoolean("Mob");
/*      */     }
/*      */     
/*      */     protected void writeStructureToNBT(NBTTagCompound tagCompound) {
/* 1128 */       super.writeStructureToNBT(tagCompound);
/* 1129 */       tagCompound.setBoolean("Mob", this.hasSpawner);
/*      */     }
/*      */     
/*      */     public static Throne func_175874_a(List<StructureComponent> p_175874_0_, Random p_175874_1_, int p_175874_2_, int p_175874_3_, int p_175874_4_, int p_175874_5_, EnumFacing p_175874_6_) {
/* 1133 */       StructureBoundingBox structureboundingbox = StructureBoundingBox.getComponentToAddBoundingBox(p_175874_2_, p_175874_3_, p_175874_4_, -2, 0, 0, 7, 8, 9, p_175874_6_);
/* 1134 */       return (isAboveGround(structureboundingbox) && StructureComponent.findIntersecting(p_175874_0_, structureboundingbox) == null) ? new Throne(p_175874_5_, p_175874_1_, structureboundingbox, p_175874_6_) : null;
/*      */     }
/*      */     
/*      */     public boolean addComponentParts(World worldIn, Random randomIn, StructureBoundingBox structureBoundingBoxIn) {
/* 1138 */       fillWithBlocks(worldIn, structureBoundingBoxIn, 0, 2, 0, 6, 7, 7, Blocks.air.getDefaultState(), Blocks.air.getDefaultState(), false);
/* 1139 */       fillWithBlocks(worldIn, structureBoundingBoxIn, 1, 0, 0, 5, 1, 7, Blocks.nether_brick.getDefaultState(), Blocks.nether_brick.getDefaultState(), false);
/* 1140 */       fillWithBlocks(worldIn, structureBoundingBoxIn, 1, 2, 1, 5, 2, 7, Blocks.nether_brick.getDefaultState(), Blocks.nether_brick.getDefaultState(), false);
/* 1141 */       fillWithBlocks(worldIn, structureBoundingBoxIn, 1, 3, 2, 5, 3, 7, Blocks.nether_brick.getDefaultState(), Blocks.nether_brick.getDefaultState(), false);
/* 1142 */       fillWithBlocks(worldIn, structureBoundingBoxIn, 1, 4, 3, 5, 4, 7, Blocks.nether_brick.getDefaultState(), Blocks.nether_brick.getDefaultState(), false);
/* 1143 */       fillWithBlocks(worldIn, structureBoundingBoxIn, 1, 2, 0, 1, 4, 2, Blocks.nether_brick.getDefaultState(), Blocks.nether_brick.getDefaultState(), false);
/* 1144 */       fillWithBlocks(worldIn, structureBoundingBoxIn, 5, 2, 0, 5, 4, 2, Blocks.nether_brick.getDefaultState(), Blocks.nether_brick.getDefaultState(), false);
/* 1145 */       fillWithBlocks(worldIn, structureBoundingBoxIn, 1, 5, 2, 1, 5, 3, Blocks.nether_brick.getDefaultState(), Blocks.nether_brick.getDefaultState(), false);
/* 1146 */       fillWithBlocks(worldIn, structureBoundingBoxIn, 5, 5, 2, 5, 5, 3, Blocks.nether_brick.getDefaultState(), Blocks.nether_brick.getDefaultState(), false);
/* 1147 */       fillWithBlocks(worldIn, structureBoundingBoxIn, 0, 5, 3, 0, 5, 8, Blocks.nether_brick.getDefaultState(), Blocks.nether_brick.getDefaultState(), false);
/* 1148 */       fillWithBlocks(worldIn, structureBoundingBoxIn, 6, 5, 3, 6, 5, 8, Blocks.nether_brick.getDefaultState(), Blocks.nether_brick.getDefaultState(), false);
/* 1149 */       fillWithBlocks(worldIn, structureBoundingBoxIn, 1, 5, 8, 5, 5, 8, Blocks.nether_brick.getDefaultState(), Blocks.nether_brick.getDefaultState(), false);
/* 1150 */       setBlockState(worldIn, Blocks.nether_brick_fence.getDefaultState(), 1, 6, 3, structureBoundingBoxIn);
/* 1151 */       setBlockState(worldIn, Blocks.nether_brick_fence.getDefaultState(), 5, 6, 3, structureBoundingBoxIn);
/* 1152 */       fillWithBlocks(worldIn, structureBoundingBoxIn, 0, 6, 3, 0, 6, 8, Blocks.nether_brick_fence.getDefaultState(), Blocks.nether_brick_fence.getDefaultState(), false);
/* 1153 */       fillWithBlocks(worldIn, structureBoundingBoxIn, 6, 6, 3, 6, 6, 8, Blocks.nether_brick_fence.getDefaultState(), Blocks.nether_brick_fence.getDefaultState(), false);
/* 1154 */       fillWithBlocks(worldIn, structureBoundingBoxIn, 1, 6, 8, 5, 7, 8, Blocks.nether_brick_fence.getDefaultState(), Blocks.nether_brick_fence.getDefaultState(), false);
/* 1155 */       fillWithBlocks(worldIn, structureBoundingBoxIn, 2, 8, 8, 4, 8, 8, Blocks.nether_brick_fence.getDefaultState(), Blocks.nether_brick_fence.getDefaultState(), false);
/*      */       
/* 1157 */       if (!this.hasSpawner) {
/* 1158 */         BlockPos blockpos = new BlockPos(getXWithOffset(3, 5), getYWithOffset(5), getZWithOffset(3, 5));
/*      */         
/* 1160 */         if (structureBoundingBoxIn.isVecInside((Vec3i)blockpos)) {
/* 1161 */           this.hasSpawner = true;
/* 1162 */           worldIn.setBlockState(blockpos, Blocks.mob_spawner.getDefaultState(), 2);
/* 1163 */           TileEntity tileentity = worldIn.getTileEntity(blockpos);
/*      */           
/* 1165 */           if (tileentity instanceof TileEntityMobSpawner) {
/* 1166 */             ((TileEntityMobSpawner)tileentity).getSpawnerBaseLogic().setEntityName("Blaze");
/*      */           }
/*      */         } 
/*      */       } 
/*      */       
/* 1171 */       for (int i = 0; i <= 6; i++) {
/* 1172 */         for (int j = 0; j <= 6; j++) {
/* 1173 */           replaceAirAndLiquidDownwards(worldIn, Blocks.nether_brick.getDefaultState(), i, -1, j, structureBoundingBoxIn);
/*      */         }
/*      */       } 
/*      */       
/* 1177 */       return true;
/*      */     }
/*      */   }
/*      */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\minecraft\world\gen\structure\StructureNetherBridgePieces.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */