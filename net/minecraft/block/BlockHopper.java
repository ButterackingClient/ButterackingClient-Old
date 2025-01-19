/*     */ package net.minecraft.block;
/*     */ 
/*     */ import com.google.common.base.Predicate;
/*     */ import java.util.List;
/*     */ import net.minecraft.block.material.MapColor;
/*     */ import net.minecraft.block.material.Material;
/*     */ import net.minecraft.block.properties.IProperty;
/*     */ import net.minecraft.block.properties.PropertyBool;
/*     */ import net.minecraft.block.properties.PropertyDirection;
/*     */ import net.minecraft.block.state.BlockState;
/*     */ import net.minecraft.block.state.IBlockState;
/*     */ import net.minecraft.creativetab.CreativeTabs;
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.entity.EntityLivingBase;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.inventory.Container;
/*     */ import net.minecraft.inventory.IInventory;
/*     */ import net.minecraft.inventory.InventoryHelper;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraft.stats.StatList;
/*     */ import net.minecraft.tileentity.TileEntity;
/*     */ import net.minecraft.tileentity.TileEntityHopper;
/*     */ import net.minecraft.util.AxisAlignedBB;
/*     */ import net.minecraft.util.BlockPos;
/*     */ import net.minecraft.util.EnumFacing;
/*     */ import net.minecraft.util.EnumWorldBlockLayer;
/*     */ import net.minecraft.world.IBlockAccess;
/*     */ import net.minecraft.world.World;
/*     */ 
/*     */ public class BlockHopper
/*     */   extends BlockContainer {
/*  32 */   public static final PropertyDirection FACING = PropertyDirection.create("facing", new Predicate<EnumFacing>() {
/*     */         public boolean apply(EnumFacing p_apply_1_) {
/*  34 */           return (p_apply_1_ != EnumFacing.UP);
/*     */         }
/*     */       });
/*  37 */   public static final PropertyBool ENABLED = PropertyBool.create("enabled");
/*     */   
/*     */   public BlockHopper() {
/*  40 */     super(Material.iron, MapColor.stoneColor);
/*  41 */     setDefaultState(this.blockState.getBaseState().withProperty((IProperty)FACING, (Comparable)EnumFacing.DOWN).withProperty((IProperty)ENABLED, Boolean.valueOf(true)));
/*  42 */     setCreativeTab(CreativeTabs.tabRedstone);
/*  43 */     setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F);
/*     */   }
/*     */   
/*     */   public void setBlockBoundsBasedOnState(IBlockAccess worldIn, BlockPos pos) {
/*  47 */     setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void addCollisionBoxesToList(World worldIn, BlockPos pos, IBlockState state, AxisAlignedBB mask, List<AxisAlignedBB> list, Entity collidingEntity) {
/*  54 */     setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 0.625F, 1.0F);
/*  55 */     super.addCollisionBoxesToList(worldIn, pos, state, mask, list, collidingEntity);
/*  56 */     float f = 0.125F;
/*  57 */     setBlockBounds(0.0F, 0.0F, 0.0F, f, 1.0F, 1.0F);
/*  58 */     super.addCollisionBoxesToList(worldIn, pos, state, mask, list, collidingEntity);
/*  59 */     setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, f);
/*  60 */     super.addCollisionBoxesToList(worldIn, pos, state, mask, list, collidingEntity);
/*  61 */     setBlockBounds(1.0F - f, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F);
/*  62 */     super.addCollisionBoxesToList(worldIn, pos, state, mask, list, collidingEntity);
/*  63 */     setBlockBounds(0.0F, 0.0F, 1.0F - f, 1.0F, 1.0F, 1.0F);
/*  64 */     super.addCollisionBoxesToList(worldIn, pos, state, mask, list, collidingEntity);
/*  65 */     setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public IBlockState onBlockPlaced(World worldIn, BlockPos pos, EnumFacing facing, float hitX, float hitY, float hitZ, int meta, EntityLivingBase placer) {
/*  73 */     EnumFacing enumfacing = facing.getOpposite();
/*     */     
/*  75 */     if (enumfacing == EnumFacing.UP) {
/*  76 */       enumfacing = EnumFacing.DOWN;
/*     */     }
/*     */     
/*  79 */     return getDefaultState().withProperty((IProperty)FACING, (Comparable)enumfacing).withProperty((IProperty)ENABLED, Boolean.valueOf(true));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public TileEntity createNewTileEntity(World worldIn, int meta) {
/*  86 */     return (TileEntity)new TileEntityHopper();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void onBlockPlacedBy(World worldIn, BlockPos pos, IBlockState state, EntityLivingBase placer, ItemStack stack) {
/*  93 */     super.onBlockPlacedBy(worldIn, pos, state, placer, stack);
/*     */     
/*  95 */     if (stack.hasDisplayName()) {
/*  96 */       TileEntity tileentity = worldIn.getTileEntity(pos);
/*     */       
/*  98 */       if (tileentity instanceof TileEntityHopper) {
/*  99 */         ((TileEntityHopper)tileentity).setCustomName(stack.getDisplayName());
/*     */       }
/*     */     } 
/*     */   }
/*     */   
/*     */   public void onBlockAdded(World worldIn, BlockPos pos, IBlockState state) {
/* 105 */     updateState(worldIn, pos, state);
/*     */   }
/*     */   
/*     */   public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumFacing side, float hitX, float hitY, float hitZ) {
/* 109 */     if (worldIn.isRemote) {
/* 110 */       return true;
/*     */     }
/* 112 */     TileEntity tileentity = worldIn.getTileEntity(pos);
/*     */     
/* 114 */     if (tileentity instanceof TileEntityHopper) {
/* 115 */       playerIn.displayGUIChest((IInventory)tileentity);
/* 116 */       playerIn.triggerAchievement(StatList.field_181732_P);
/*     */     } 
/*     */     
/* 119 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void onNeighborBlockChange(World worldIn, BlockPos pos, IBlockState state, Block neighborBlock) {
/* 127 */     updateState(worldIn, pos, state);
/*     */   }
/*     */   
/*     */   private void updateState(World worldIn, BlockPos pos, IBlockState state) {
/* 131 */     boolean flag = !worldIn.isBlockPowered(pos);
/*     */     
/* 133 */     if (flag != ((Boolean)state.getValue((IProperty)ENABLED)).booleanValue()) {
/* 134 */       worldIn.setBlockState(pos, state.withProperty((IProperty)ENABLED, Boolean.valueOf(flag)), 4);
/*     */     }
/*     */   }
/*     */   
/*     */   public void breakBlock(World worldIn, BlockPos pos, IBlockState state) {
/* 139 */     TileEntity tileentity = worldIn.getTileEntity(pos);
/*     */     
/* 141 */     if (tileentity instanceof TileEntityHopper) {
/* 142 */       InventoryHelper.dropInventoryItems(worldIn, pos, (IInventory)tileentity);
/* 143 */       worldIn.updateComparatorOutputLevel(pos, this);
/*     */     } 
/*     */     
/* 146 */     super.breakBlock(worldIn, pos, state);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getRenderType() {
/* 153 */     return 3;
/*     */   }
/*     */   
/*     */   public boolean isFullCube() {
/* 157 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isOpaqueCube() {
/* 164 */     return false;
/*     */   }
/*     */   
/*     */   public boolean shouldSideBeRendered(IBlockAccess worldIn, BlockPos pos, EnumFacing side) {
/* 168 */     return true;
/*     */   }
/*     */   
/*     */   public static EnumFacing getFacing(int meta) {
/* 172 */     return EnumFacing.getFront(meta & 0x7);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static boolean isEnabled(int meta) {
/* 180 */     return ((meta & 0x8) != 8);
/*     */   }
/*     */   
/*     */   public boolean hasComparatorInputOverride() {
/* 184 */     return true;
/*     */   }
/*     */   
/*     */   public int getComparatorInputOverride(World worldIn, BlockPos pos) {
/* 188 */     return Container.calcRedstone(worldIn.getTileEntity(pos));
/*     */   }
/*     */   
/*     */   public EnumWorldBlockLayer getBlockLayer() {
/* 192 */     return EnumWorldBlockLayer.CUTOUT_MIPPED;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public IBlockState getStateFromMeta(int meta) {
/* 199 */     return getDefaultState().withProperty((IProperty)FACING, (Comparable)getFacing(meta)).withProperty((IProperty)ENABLED, Boolean.valueOf(isEnabled(meta)));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getMetaFromState(IBlockState state) {
/* 206 */     int i = 0;
/* 207 */     i |= ((EnumFacing)state.getValue((IProperty)FACING)).getIndex();
/*     */     
/* 209 */     if (!((Boolean)state.getValue((IProperty)ENABLED)).booleanValue()) {
/* 210 */       i |= 0x8;
/*     */     }
/*     */     
/* 213 */     return i;
/*     */   }
/*     */   
/*     */   protected BlockState createBlockState() {
/* 217 */     return new BlockState(this, new IProperty[] { (IProperty)FACING, (IProperty)ENABLED });
/*     */   }
/*     */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\minecraft\block\BlockHopper.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */