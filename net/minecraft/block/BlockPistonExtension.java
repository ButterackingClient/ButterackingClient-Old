/*     */ package net.minecraft.block;
/*     */ 
/*     */ import java.util.List;
/*     */ import java.util.Random;
/*     */ import net.minecraft.block.material.Material;
/*     */ import net.minecraft.block.properties.IProperty;
/*     */ import net.minecraft.block.properties.PropertyBool;
/*     */ import net.minecraft.block.properties.PropertyDirection;
/*     */ import net.minecraft.block.properties.PropertyEnum;
/*     */ import net.minecraft.block.state.BlockState;
/*     */ import net.minecraft.block.state.IBlockState;
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.init.Blocks;
/*     */ import net.minecraft.item.Item;
/*     */ import net.minecraft.util.AxisAlignedBB;
/*     */ import net.minecraft.util.BlockPos;
/*     */ import net.minecraft.util.EnumFacing;
/*     */ import net.minecraft.util.IStringSerializable;
/*     */ import net.minecraft.world.IBlockAccess;
/*     */ import net.minecraft.world.World;
/*     */ 
/*     */ public class BlockPistonExtension
/*     */   extends Block {
/*  25 */   public static final PropertyDirection FACING = PropertyDirection.create("facing");
/*  26 */   public static final PropertyEnum<EnumPistonType> TYPE = PropertyEnum.create("type", EnumPistonType.class);
/*  27 */   public static final PropertyBool SHORT = PropertyBool.create("short");
/*     */   
/*     */   public BlockPistonExtension() {
/*  30 */     super(Material.piston);
/*  31 */     setDefaultState(this.blockState.getBaseState().withProperty((IProperty)FACING, (Comparable)EnumFacing.NORTH).withProperty((IProperty)TYPE, EnumPistonType.DEFAULT).withProperty((IProperty)SHORT, Boolean.valueOf(false)));
/*  32 */     setStepSound(soundTypePiston);
/*  33 */     setHardness(0.5F);
/*     */   }
/*     */   
/*     */   public void onBlockHarvested(World worldIn, BlockPos pos, IBlockState state, EntityPlayer player) {
/*  37 */     if (player.capabilities.isCreativeMode) {
/*  38 */       EnumFacing enumfacing = (EnumFacing)state.getValue((IProperty)FACING);
/*     */       
/*  40 */       if (enumfacing != null) {
/*  41 */         BlockPos blockpos = pos.offset(enumfacing.getOpposite());
/*  42 */         Block block = worldIn.getBlockState(blockpos).getBlock();
/*     */         
/*  44 */         if (block == Blocks.piston || block == Blocks.sticky_piston) {
/*  45 */           worldIn.setBlockToAir(blockpos);
/*     */         }
/*     */       } 
/*     */     } 
/*     */     
/*  50 */     super.onBlockHarvested(worldIn, pos, state, player);
/*     */   }
/*     */   
/*     */   public void breakBlock(World worldIn, BlockPos pos, IBlockState state) {
/*  54 */     super.breakBlock(worldIn, pos, state);
/*  55 */     EnumFacing enumfacing = ((EnumFacing)state.getValue((IProperty)FACING)).getOpposite();
/*  56 */     pos = pos.offset(enumfacing);
/*  57 */     IBlockState iblockstate = worldIn.getBlockState(pos);
/*     */     
/*  59 */     if ((iblockstate.getBlock() == Blocks.piston || iblockstate.getBlock() == Blocks.sticky_piston) && ((Boolean)iblockstate.getValue((IProperty)BlockPistonBase.EXTENDED)).booleanValue()) {
/*  60 */       iblockstate.getBlock().dropBlockAsItem(worldIn, pos, iblockstate, 0);
/*  61 */       worldIn.setBlockToAir(pos);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isOpaqueCube() {
/*  69 */     return false;
/*     */   }
/*     */   
/*     */   public boolean isFullCube() {
/*  73 */     return false;
/*     */   }
/*     */   
/*     */   public boolean canPlaceBlockAt(World worldIn, BlockPos pos) {
/*  77 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean canPlaceBlockOnSide(World worldIn, BlockPos pos, EnumFacing side) {
/*  84 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int quantityDropped(Random random) {
/*  91 */     return 0;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void addCollisionBoxesToList(World worldIn, BlockPos pos, IBlockState state, AxisAlignedBB mask, List<AxisAlignedBB> list, Entity collidingEntity) {
/*  98 */     applyHeadBounds(state);
/*  99 */     super.addCollisionBoxesToList(worldIn, pos, state, mask, list, collidingEntity);
/* 100 */     applyCoreBounds(state);
/* 101 */     super.addCollisionBoxesToList(worldIn, pos, state, mask, list, collidingEntity);
/* 102 */     setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F);
/*     */   }
/*     */   
/*     */   private void applyCoreBounds(IBlockState state) {
/* 106 */     float f = 0.25F;
/* 107 */     float f1 = 0.375F;
/* 108 */     float f2 = 0.625F;
/* 109 */     float f3 = 0.25F;
/* 110 */     float f4 = 0.75F;
/*     */     
/* 112 */     switch ((EnumFacing)state.getValue((IProperty)FACING)) {
/*     */       case null:
/* 114 */         setBlockBounds(0.375F, 0.25F, 0.375F, 0.625F, 1.0F, 0.625F);
/*     */         break;
/*     */       
/*     */       case UP:
/* 118 */         setBlockBounds(0.375F, 0.0F, 0.375F, 0.625F, 0.75F, 0.625F);
/*     */         break;
/*     */       
/*     */       case NORTH:
/* 122 */         setBlockBounds(0.25F, 0.375F, 0.25F, 0.75F, 0.625F, 1.0F);
/*     */         break;
/*     */       
/*     */       case SOUTH:
/* 126 */         setBlockBounds(0.25F, 0.375F, 0.0F, 0.75F, 0.625F, 0.75F);
/*     */         break;
/*     */       
/*     */       case WEST:
/* 130 */         setBlockBounds(0.375F, 0.25F, 0.25F, 0.625F, 0.75F, 1.0F);
/*     */         break;
/*     */       
/*     */       case EAST:
/* 134 */         setBlockBounds(0.0F, 0.375F, 0.25F, 0.75F, 0.625F, 0.75F);
/*     */         break;
/*     */     } 
/*     */   }
/*     */   public void setBlockBoundsBasedOnState(IBlockAccess worldIn, BlockPos pos) {
/* 139 */     applyHeadBounds(worldIn.getBlockState(pos));
/*     */   }
/*     */   
/*     */   public void applyHeadBounds(IBlockState state) {
/* 143 */     float f = 0.25F;
/* 144 */     EnumFacing enumfacing = (EnumFacing)state.getValue((IProperty)FACING);
/*     */     
/* 146 */     if (enumfacing != null) {
/* 147 */       switch (enumfacing) {
/*     */         case null:
/* 149 */           setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 0.25F, 1.0F);
/*     */           break;
/*     */         
/*     */         case UP:
/* 153 */           setBlockBounds(0.0F, 0.75F, 0.0F, 1.0F, 1.0F, 1.0F);
/*     */           break;
/*     */         
/*     */         case NORTH:
/* 157 */           setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, 0.25F);
/*     */           break;
/*     */         
/*     */         case SOUTH:
/* 161 */           setBlockBounds(0.0F, 0.0F, 0.75F, 1.0F, 1.0F, 1.0F);
/*     */           break;
/*     */         
/*     */         case WEST:
/* 165 */           setBlockBounds(0.0F, 0.0F, 0.0F, 0.25F, 1.0F, 1.0F);
/*     */           break;
/*     */         
/*     */         case EAST:
/* 169 */           setBlockBounds(0.75F, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F);
/*     */           break;
/*     */       } 
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void onNeighborBlockChange(World worldIn, BlockPos pos, IBlockState state, Block neighborBlock) {
/* 178 */     EnumFacing enumfacing = (EnumFacing)state.getValue((IProperty)FACING);
/* 179 */     BlockPos blockpos = pos.offset(enumfacing.getOpposite());
/* 180 */     IBlockState iblockstate = worldIn.getBlockState(blockpos);
/*     */     
/* 182 */     if (iblockstate.getBlock() != Blocks.piston && iblockstate.getBlock() != Blocks.sticky_piston) {
/* 183 */       worldIn.setBlockToAir(pos);
/*     */     } else {
/* 185 */       iblockstate.getBlock().onNeighborBlockChange(worldIn, blockpos, iblockstate, neighborBlock);
/*     */     } 
/*     */   }
/*     */   
/*     */   public boolean shouldSideBeRendered(IBlockAccess worldIn, BlockPos pos, EnumFacing side) {
/* 190 */     return true;
/*     */   }
/*     */   
/*     */   public static EnumFacing getFacing(int meta) {
/* 194 */     int i = meta & 0x7;
/* 195 */     return (i > 5) ? null : EnumFacing.getFront(i);
/*     */   }
/*     */   
/*     */   public Item getItem(World worldIn, BlockPos pos) {
/* 199 */     return (worldIn.getBlockState(pos).getValue((IProperty)TYPE) == EnumPistonType.STICKY) ? Item.getItemFromBlock(Blocks.sticky_piston) : Item.getItemFromBlock(Blocks.piston);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public IBlockState getStateFromMeta(int meta) {
/* 206 */     return getDefaultState().withProperty((IProperty)FACING, (Comparable)getFacing(meta)).withProperty((IProperty)TYPE, ((meta & 0x8) > 0) ? EnumPistonType.STICKY : EnumPistonType.DEFAULT);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getMetaFromState(IBlockState state) {
/* 213 */     int i = 0;
/* 214 */     i |= ((EnumFacing)state.getValue((IProperty)FACING)).getIndex();
/*     */     
/* 216 */     if (state.getValue((IProperty)TYPE) == EnumPistonType.STICKY) {
/* 217 */       i |= 0x8;
/*     */     }
/*     */     
/* 220 */     return i;
/*     */   }
/*     */   
/*     */   protected BlockState createBlockState() {
/* 224 */     return new BlockState(this, new IProperty[] { (IProperty)FACING, (IProperty)TYPE, (IProperty)SHORT });
/*     */   }
/*     */   
/*     */   public enum EnumPistonType implements IStringSerializable {
/* 228 */     DEFAULT("normal"),
/* 229 */     STICKY("sticky");
/*     */     
/*     */     private final String VARIANT;
/*     */     
/*     */     EnumPistonType(String name) {
/* 234 */       this.VARIANT = name;
/*     */     }
/*     */     
/*     */     public String toString() {
/* 238 */       return this.VARIANT;
/*     */     }
/*     */     
/*     */     public String getName() {
/* 242 */       return this.VARIANT;
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\minecraft\block\BlockPistonExtension.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */