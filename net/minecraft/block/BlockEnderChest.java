/*     */ package net.minecraft.block;
/*     */ import com.google.common.base.Predicate;
/*     */ import java.util.Random;
/*     */ import net.minecraft.block.material.Material;
/*     */ import net.minecraft.block.properties.IProperty;
/*     */ import net.minecraft.block.properties.PropertyDirection;
/*     */ import net.minecraft.block.state.BlockState;
/*     */ import net.minecraft.block.state.IBlockState;
/*     */ import net.minecraft.creativetab.CreativeTabs;
/*     */ import net.minecraft.entity.EntityLivingBase;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.init.Blocks;
/*     */ import net.minecraft.inventory.IInventory;
/*     */ import net.minecraft.inventory.InventoryEnderChest;
/*     */ import net.minecraft.item.Item;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraft.stats.StatList;
/*     */ import net.minecraft.tileentity.TileEntity;
/*     */ import net.minecraft.tileentity.TileEntityEnderChest;
/*     */ import net.minecraft.util.BlockPos;
/*     */ import net.minecraft.util.EnumFacing;
/*     */ import net.minecraft.util.EnumParticleTypes;
/*     */ import net.minecraft.world.World;
/*     */ 
/*     */ public class BlockEnderChest extends BlockContainer {
/*  26 */   public static final PropertyDirection FACING = PropertyDirection.create("facing", (Predicate)EnumFacing.Plane.HORIZONTAL);
/*     */   
/*     */   protected BlockEnderChest() {
/*  29 */     super(Material.rock);
/*  30 */     setDefaultState(this.blockState.getBaseState().withProperty((IProperty)FACING, (Comparable)EnumFacing.NORTH));
/*  31 */     setCreativeTab(CreativeTabs.tabDecorations);
/*  32 */     setBlockBounds(0.0625F, 0.0F, 0.0625F, 0.9375F, 0.875F, 0.9375F);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isOpaqueCube() {
/*  39 */     return false;
/*     */   }
/*     */   
/*     */   public boolean isFullCube() {
/*  43 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getRenderType() {
/*  50 */     return 2;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Item getItemDropped(IBlockState state, Random rand, int fortune) {
/*  57 */     return Item.getItemFromBlock(Blocks.obsidian);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int quantityDropped(Random random) {
/*  64 */     return 8;
/*     */   }
/*     */   
/*     */   protected boolean canSilkHarvest() {
/*  68 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public IBlockState onBlockPlaced(World worldIn, BlockPos pos, EnumFacing facing, float hitX, float hitY, float hitZ, int meta, EntityLivingBase placer) {
/*  76 */     return getDefaultState().withProperty((IProperty)FACING, (Comparable)placer.getHorizontalFacing().getOpposite());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void onBlockPlacedBy(World worldIn, BlockPos pos, IBlockState state, EntityLivingBase placer, ItemStack stack) {
/*  83 */     worldIn.setBlockState(pos, state.withProperty((IProperty)FACING, (Comparable)placer.getHorizontalFacing().getOpposite()), 2);
/*     */   }
/*     */   
/*     */   public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumFacing side, float hitX, float hitY, float hitZ) {
/*  87 */     InventoryEnderChest inventoryenderchest = playerIn.getInventoryEnderChest();
/*  88 */     TileEntity tileentity = worldIn.getTileEntity(pos);
/*     */     
/*  90 */     if (inventoryenderchest != null && tileentity instanceof TileEntityEnderChest) {
/*  91 */       if (worldIn.getBlockState(pos.up()).getBlock().isNormalCube())
/*  92 */         return true; 
/*  93 */       if (worldIn.isRemote) {
/*  94 */         return true;
/*     */       }
/*  96 */       inventoryenderchest.setChestTileEntity((TileEntityEnderChest)tileentity);
/*  97 */       playerIn.displayGUIChest((IInventory)inventoryenderchest);
/*  98 */       playerIn.triggerAchievement(StatList.field_181738_V);
/*  99 */       return true;
/*     */     } 
/*     */     
/* 102 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public TileEntity createNewTileEntity(World worldIn, int meta) {
/* 110 */     return (TileEntity)new TileEntityEnderChest();
/*     */   }
/*     */   
/*     */   public void randomDisplayTick(World worldIn, BlockPos pos, IBlockState state, Random rand) {
/* 114 */     for (int i = 0; i < 3; i++) {
/* 115 */       int j = rand.nextInt(2) * 2 - 1;
/* 116 */       int k = rand.nextInt(2) * 2 - 1;
/* 117 */       double d0 = pos.getX() + 0.5D + 0.25D * j;
/* 118 */       double d1 = (pos.getY() + rand.nextFloat());
/* 119 */       double d2 = pos.getZ() + 0.5D + 0.25D * k;
/* 120 */       double d3 = (rand.nextFloat() * j);
/* 121 */       double d4 = (rand.nextFloat() - 0.5D) * 0.125D;
/* 122 */       double d5 = (rand.nextFloat() * k);
/* 123 */       worldIn.spawnParticle(EnumParticleTypes.PORTAL, d0, d1, d2, d3, d4, d5, new int[0]);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public IBlockState getStateFromMeta(int meta) {
/* 131 */     EnumFacing enumfacing = EnumFacing.getFront(meta);
/*     */     
/* 133 */     if (enumfacing.getAxis() == EnumFacing.Axis.Y) {
/* 134 */       enumfacing = EnumFacing.NORTH;
/*     */     }
/*     */     
/* 137 */     return getDefaultState().withProperty((IProperty)FACING, (Comparable)enumfacing);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getMetaFromState(IBlockState state) {
/* 144 */     return ((EnumFacing)state.getValue((IProperty)FACING)).getIndex();
/*     */   }
/*     */   
/*     */   protected BlockState createBlockState() {
/* 148 */     return new BlockState(this, new IProperty[] { (IProperty)FACING });
/*     */   }
/*     */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\minecraft\block\BlockEnderChest.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */