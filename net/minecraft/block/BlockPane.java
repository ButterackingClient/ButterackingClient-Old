/*     */ package net.minecraft.block;
/*     */ 
/*     */ import java.util.List;
/*     */ import java.util.Random;
/*     */ import net.minecraft.block.material.Material;
/*     */ import net.minecraft.block.properties.IProperty;
/*     */ import net.minecraft.block.properties.PropertyBool;
/*     */ import net.minecraft.block.state.BlockState;
/*     */ import net.minecraft.block.state.IBlockState;
/*     */ import net.minecraft.creativetab.CreativeTabs;
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.init.Blocks;
/*     */ import net.minecraft.item.Item;
/*     */ import net.minecraft.util.AxisAlignedBB;
/*     */ import net.minecraft.util.BlockPos;
/*     */ import net.minecraft.util.EnumFacing;
/*     */ import net.minecraft.util.EnumWorldBlockLayer;
/*     */ import net.minecraft.world.IBlockAccess;
/*     */ import net.minecraft.world.World;
/*     */ 
/*     */ public class BlockPane
/*     */   extends Block {
/*  23 */   public static final PropertyBool NORTH = PropertyBool.create("north");
/*  24 */   public static final PropertyBool EAST = PropertyBool.create("east");
/*  25 */   public static final PropertyBool SOUTH = PropertyBool.create("south");
/*  26 */   public static final PropertyBool WEST = PropertyBool.create("west");
/*     */   private final boolean canDrop;
/*     */   
/*     */   protected BlockPane(Material materialIn, boolean canDrop) {
/*  30 */     super(materialIn);
/*  31 */     setDefaultState(this.blockState.getBaseState().withProperty((IProperty)NORTH, Boolean.valueOf(false)).withProperty((IProperty)EAST, Boolean.valueOf(false)).withProperty((IProperty)SOUTH, Boolean.valueOf(false)).withProperty((IProperty)WEST, Boolean.valueOf(false)));
/*  32 */     this.canDrop = canDrop;
/*  33 */     setCreativeTab(CreativeTabs.tabDecorations);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public IBlockState getActualState(IBlockState state, IBlockAccess worldIn, BlockPos pos) {
/*  41 */     return state.withProperty((IProperty)NORTH, Boolean.valueOf(canPaneConnectToBlock(worldIn.getBlockState(pos.north()).getBlock()))).withProperty((IProperty)SOUTH, Boolean.valueOf(canPaneConnectToBlock(worldIn.getBlockState(pos.south()).getBlock()))).withProperty((IProperty)WEST, Boolean.valueOf(canPaneConnectToBlock(worldIn.getBlockState(pos.west()).getBlock()))).withProperty((IProperty)EAST, Boolean.valueOf(canPaneConnectToBlock(worldIn.getBlockState(pos.east()).getBlock())));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Item getItemDropped(IBlockState state, Random rand, int fortune) {
/*  48 */     return !this.canDrop ? null : super.getItemDropped(state, rand, fortune);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isOpaqueCube() {
/*  55 */     return false;
/*     */   }
/*     */   
/*     */   public boolean isFullCube() {
/*  59 */     return false;
/*     */   }
/*     */   
/*     */   public boolean shouldSideBeRendered(IBlockAccess worldIn, BlockPos pos, EnumFacing side) {
/*  63 */     return (worldIn.getBlockState(pos).getBlock() == this) ? false : super.shouldSideBeRendered(worldIn, pos, side);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void addCollisionBoxesToList(World worldIn, BlockPos pos, IBlockState state, AxisAlignedBB mask, List<AxisAlignedBB> list, Entity collidingEntity) {
/*  70 */     boolean flag = canPaneConnectToBlock(worldIn.getBlockState(pos.north()).getBlock());
/*  71 */     boolean flag1 = canPaneConnectToBlock(worldIn.getBlockState(pos.south()).getBlock());
/*  72 */     boolean flag2 = canPaneConnectToBlock(worldIn.getBlockState(pos.west()).getBlock());
/*  73 */     boolean flag3 = canPaneConnectToBlock(worldIn.getBlockState(pos.east()).getBlock());
/*     */     
/*  75 */     if ((!flag2 || !flag3) && (flag2 || flag3 || flag || flag1)) {
/*  76 */       if (flag2) {
/*  77 */         setBlockBounds(0.0F, 0.0F, 0.4375F, 0.5F, 1.0F, 0.5625F);
/*  78 */         super.addCollisionBoxesToList(worldIn, pos, state, mask, list, collidingEntity);
/*  79 */       } else if (flag3) {
/*  80 */         setBlockBounds(0.5F, 0.0F, 0.4375F, 1.0F, 1.0F, 0.5625F);
/*  81 */         super.addCollisionBoxesToList(worldIn, pos, state, mask, list, collidingEntity);
/*     */       } 
/*     */     } else {
/*  84 */       setBlockBounds(0.0F, 0.0F, 0.4375F, 1.0F, 1.0F, 0.5625F);
/*  85 */       super.addCollisionBoxesToList(worldIn, pos, state, mask, list, collidingEntity);
/*     */     } 
/*     */     
/*  88 */     if ((!flag || !flag1) && (flag2 || flag3 || flag || flag1)) {
/*  89 */       if (flag) {
/*  90 */         setBlockBounds(0.4375F, 0.0F, 0.0F, 0.5625F, 1.0F, 0.5F);
/*  91 */         super.addCollisionBoxesToList(worldIn, pos, state, mask, list, collidingEntity);
/*  92 */       } else if (flag1) {
/*  93 */         setBlockBounds(0.4375F, 0.0F, 0.5F, 0.5625F, 1.0F, 1.0F);
/*  94 */         super.addCollisionBoxesToList(worldIn, pos, state, mask, list, collidingEntity);
/*     */       } 
/*     */     } else {
/*  97 */       setBlockBounds(0.4375F, 0.0F, 0.0F, 0.5625F, 1.0F, 1.0F);
/*  98 */       super.addCollisionBoxesToList(worldIn, pos, state, mask, list, collidingEntity);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setBlockBoundsForItemRender() {
/* 106 */     setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F);
/*     */   }
/*     */   
/*     */   public void setBlockBoundsBasedOnState(IBlockAccess worldIn, BlockPos pos) {
/* 110 */     float f = 0.4375F;
/* 111 */     float f1 = 0.5625F;
/* 112 */     float f2 = 0.4375F;
/* 113 */     float f3 = 0.5625F;
/* 114 */     boolean flag = canPaneConnectToBlock(worldIn.getBlockState(pos.north()).getBlock());
/* 115 */     boolean flag1 = canPaneConnectToBlock(worldIn.getBlockState(pos.south()).getBlock());
/* 116 */     boolean flag2 = canPaneConnectToBlock(worldIn.getBlockState(pos.west()).getBlock());
/* 117 */     boolean flag3 = canPaneConnectToBlock(worldIn.getBlockState(pos.east()).getBlock());
/*     */     
/* 119 */     if ((!flag2 || !flag3) && (flag2 || flag3 || flag || flag1)) {
/* 120 */       if (flag2) {
/* 121 */         f = 0.0F;
/* 122 */       } else if (flag3) {
/* 123 */         f1 = 1.0F;
/*     */       } 
/*     */     } else {
/* 126 */       f = 0.0F;
/* 127 */       f1 = 1.0F;
/*     */     } 
/*     */     
/* 130 */     if ((!flag || !flag1) && (flag2 || flag3 || flag || flag1)) {
/* 131 */       if (flag) {
/* 132 */         f2 = 0.0F;
/* 133 */       } else if (flag1) {
/* 134 */         f3 = 1.0F;
/*     */       } 
/*     */     } else {
/* 137 */       f2 = 0.0F;
/* 138 */       f3 = 1.0F;
/*     */     } 
/*     */     
/* 141 */     setBlockBounds(f, 0.0F, f2, f1, 1.0F, f3);
/*     */   }
/*     */   
/*     */   public final boolean canPaneConnectToBlock(Block blockIn) {
/* 145 */     return !(!blockIn.isFullBlock() && blockIn != this && blockIn != Blocks.glass && blockIn != Blocks.stained_glass && blockIn != Blocks.stained_glass_pane && !(blockIn instanceof BlockPane));
/*     */   }
/*     */   
/*     */   protected boolean canSilkHarvest() {
/* 149 */     return true;
/*     */   }
/*     */   
/*     */   public EnumWorldBlockLayer getBlockLayer() {
/* 153 */     return EnumWorldBlockLayer.CUTOUT_MIPPED;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getMetaFromState(IBlockState state) {
/* 160 */     return 0;
/*     */   }
/*     */   
/*     */   protected BlockState createBlockState() {
/* 164 */     return new BlockState(this, new IProperty[] { (IProperty)NORTH, (IProperty)EAST, (IProperty)WEST, (IProperty)SOUTH });
/*     */   }
/*     */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\minecraft\block\BlockPane.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */