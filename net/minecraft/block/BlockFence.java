/*     */ package net.minecraft.block;
/*     */ 
/*     */ import java.util.List;
/*     */ import net.minecraft.block.material.MapColor;
/*     */ import net.minecraft.block.material.Material;
/*     */ import net.minecraft.block.properties.IProperty;
/*     */ import net.minecraft.block.properties.PropertyBool;
/*     */ import net.minecraft.block.state.BlockState;
/*     */ import net.minecraft.block.state.IBlockState;
/*     */ import net.minecraft.creativetab.CreativeTabs;
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.init.Blocks;
/*     */ import net.minecraft.item.ItemLead;
/*     */ import net.minecraft.util.AxisAlignedBB;
/*     */ import net.minecraft.util.BlockPos;
/*     */ import net.minecraft.util.EnumFacing;
/*     */ import net.minecraft.world.IBlockAccess;
/*     */ import net.minecraft.world.World;
/*     */ 
/*     */ 
/*     */ 
/*     */ public class BlockFence
/*     */   extends Block
/*     */ {
/*  26 */   public static final PropertyBool NORTH = PropertyBool.create("north");
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  31 */   public static final PropertyBool EAST = PropertyBool.create("east");
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  36 */   public static final PropertyBool SOUTH = PropertyBool.create("south");
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  41 */   public static final PropertyBool WEST = PropertyBool.create("west");
/*     */   
/*     */   public BlockFence(Material materialIn) {
/*  44 */     this(materialIn, materialIn.getMaterialMapColor());
/*     */   }
/*     */   
/*     */   public BlockFence(Material p_i46395_1_, MapColor p_i46395_2_) {
/*  48 */     super(p_i46395_1_, p_i46395_2_);
/*  49 */     setDefaultState(this.blockState.getBaseState().withProperty((IProperty)NORTH, Boolean.valueOf(false)).withProperty((IProperty)EAST, Boolean.valueOf(false)).withProperty((IProperty)SOUTH, Boolean.valueOf(false)).withProperty((IProperty)WEST, Boolean.valueOf(false)));
/*  50 */     setCreativeTab(CreativeTabs.tabDecorations);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void addCollisionBoxesToList(World worldIn, BlockPos pos, IBlockState state, AxisAlignedBB mask, List<AxisAlignedBB> list, Entity collidingEntity) {
/*  57 */     boolean flag = canConnectTo((IBlockAccess)worldIn, pos.north());
/*  58 */     boolean flag1 = canConnectTo((IBlockAccess)worldIn, pos.south());
/*  59 */     boolean flag2 = canConnectTo((IBlockAccess)worldIn, pos.west());
/*  60 */     boolean flag3 = canConnectTo((IBlockAccess)worldIn, pos.east());
/*  61 */     float f = 0.375F;
/*  62 */     float f1 = 0.625F;
/*  63 */     float f2 = 0.375F;
/*  64 */     float f3 = 0.625F;
/*     */     
/*  66 */     if (flag) {
/*  67 */       f2 = 0.0F;
/*     */     }
/*     */     
/*  70 */     if (flag1) {
/*  71 */       f3 = 1.0F;
/*     */     }
/*     */     
/*  74 */     if (flag || flag1) {
/*  75 */       setBlockBounds(f, 0.0F, f2, f1, 1.5F, f3);
/*  76 */       super.addCollisionBoxesToList(worldIn, pos, state, mask, list, collidingEntity);
/*     */     } 
/*     */     
/*  79 */     f2 = 0.375F;
/*  80 */     f3 = 0.625F;
/*     */     
/*  82 */     if (flag2) {
/*  83 */       f = 0.0F;
/*     */     }
/*     */     
/*  86 */     if (flag3) {
/*  87 */       f1 = 1.0F;
/*     */     }
/*     */     
/*  90 */     if (flag2 || flag3 || (!flag && !flag1)) {
/*  91 */       setBlockBounds(f, 0.0F, f2, f1, 1.5F, f3);
/*  92 */       super.addCollisionBoxesToList(worldIn, pos, state, mask, list, collidingEntity);
/*     */     } 
/*     */     
/*  95 */     if (flag) {
/*  96 */       f2 = 0.0F;
/*     */     }
/*     */     
/*  99 */     if (flag1) {
/* 100 */       f3 = 1.0F;
/*     */     }
/*     */     
/* 103 */     setBlockBounds(f, 0.0F, f2, f1, 1.0F, f3);
/*     */   }
/*     */   
/*     */   public void setBlockBoundsBasedOnState(IBlockAccess worldIn, BlockPos pos) {
/* 107 */     boolean flag = canConnectTo(worldIn, pos.north());
/* 108 */     boolean flag1 = canConnectTo(worldIn, pos.south());
/* 109 */     boolean flag2 = canConnectTo(worldIn, pos.west());
/* 110 */     boolean flag3 = canConnectTo(worldIn, pos.east());
/* 111 */     float f = 0.375F;
/* 112 */     float f1 = 0.625F;
/* 113 */     float f2 = 0.375F;
/* 114 */     float f3 = 0.625F;
/*     */     
/* 116 */     if (flag) {
/* 117 */       f2 = 0.0F;
/*     */     }
/*     */     
/* 120 */     if (flag1) {
/* 121 */       f3 = 1.0F;
/*     */     }
/*     */     
/* 124 */     if (flag2) {
/* 125 */       f = 0.0F;
/*     */     }
/*     */     
/* 128 */     if (flag3) {
/* 129 */       f1 = 1.0F;
/*     */     }
/*     */     
/* 132 */     setBlockBounds(f, 0.0F, f2, f1, 1.0F, f3);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isOpaqueCube() {
/* 139 */     return false;
/*     */   }
/*     */   
/*     */   public boolean isFullCube() {
/* 143 */     return false;
/*     */   }
/*     */   
/*     */   public boolean isPassable(IBlockAccess worldIn, BlockPos pos) {
/* 147 */     return false;
/*     */   }
/*     */   
/*     */   public boolean canConnectTo(IBlockAccess worldIn, BlockPos pos) {
/* 151 */     Block block = worldIn.getBlockState(pos).getBlock();
/* 152 */     return (block == Blocks.barrier) ? false : (((!(block instanceof BlockFence) || block.blockMaterial != this.blockMaterial) && !(block instanceof BlockFenceGate)) ? ((block.blockMaterial.isOpaque() && block.isFullCube()) ? ((block.blockMaterial != Material.gourd)) : false) : true);
/*     */   }
/*     */   
/*     */   public boolean shouldSideBeRendered(IBlockAccess worldIn, BlockPos pos, EnumFacing side) {
/* 156 */     return true;
/*     */   }
/*     */   
/*     */   public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumFacing side, float hitX, float hitY, float hitZ) {
/* 160 */     return worldIn.isRemote ? true : ItemLead.attachToFence(playerIn, worldIn, pos);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getMetaFromState(IBlockState state) {
/* 167 */     return 0;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public IBlockState getActualState(IBlockState state, IBlockAccess worldIn, BlockPos pos) {
/* 175 */     return state.withProperty((IProperty)NORTH, Boolean.valueOf(canConnectTo(worldIn, pos.north()))).withProperty((IProperty)EAST, Boolean.valueOf(canConnectTo(worldIn, pos.east()))).withProperty((IProperty)SOUTH, Boolean.valueOf(canConnectTo(worldIn, pos.south()))).withProperty((IProperty)WEST, Boolean.valueOf(canConnectTo(worldIn, pos.west())));
/*     */   }
/*     */   
/*     */   protected BlockState createBlockState() {
/* 179 */     return new BlockState(this, new IProperty[] { (IProperty)NORTH, (IProperty)EAST, (IProperty)WEST, (IProperty)SOUTH });
/*     */   }
/*     */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\minecraft\block\BlockFence.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */