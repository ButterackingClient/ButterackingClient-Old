/*     */ package net.minecraft.block;
/*     */ 
/*     */ import java.util.List;
/*     */ import java.util.Random;
/*     */ import net.minecraft.block.material.Material;
/*     */ import net.minecraft.block.properties.IProperty;
/*     */ import net.minecraft.block.properties.PropertyInteger;
/*     */ import net.minecraft.block.state.BlockState;
/*     */ import net.minecraft.block.state.IBlockState;
/*     */ import net.minecraft.creativetab.CreativeTabs;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.init.Blocks;
/*     */ import net.minecraft.item.Item;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraft.tileentity.TileEntity;
/*     */ import net.minecraft.tileentity.TileEntityDaylightDetector;
/*     */ import net.minecraft.util.BlockPos;
/*     */ import net.minecraft.util.EnumFacing;
/*     */ import net.minecraft.util.MathHelper;
/*     */ import net.minecraft.world.EnumSkyBlock;
/*     */ import net.minecraft.world.IBlockAccess;
/*     */ import net.minecraft.world.World;
/*     */ 
/*     */ public class BlockDaylightDetector
/*     */   extends BlockContainer {
/*  26 */   public static final PropertyInteger POWER = PropertyInteger.create("power", 0, 15);
/*     */   private final boolean inverted;
/*     */   
/*     */   public BlockDaylightDetector(boolean inverted) {
/*  30 */     super(Material.wood);
/*  31 */     this.inverted = inverted;
/*  32 */     setDefaultState(this.blockState.getBaseState().withProperty((IProperty)POWER, Integer.valueOf(0)));
/*  33 */     setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 0.375F, 1.0F);
/*  34 */     setCreativeTab(CreativeTabs.tabRedstone);
/*  35 */     setHardness(0.2F);
/*  36 */     setStepSound(soundTypeWood);
/*  37 */     setUnlocalizedName("daylightDetector");
/*     */   }
/*     */   
/*     */   public void setBlockBoundsBasedOnState(IBlockAccess worldIn, BlockPos pos) {
/*  41 */     setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 0.375F, 1.0F);
/*     */   }
/*     */   
/*     */   public int getWeakPower(IBlockAccess worldIn, BlockPos pos, IBlockState state, EnumFacing side) {
/*  45 */     return ((Integer)state.getValue((IProperty)POWER)).intValue();
/*     */   }
/*     */   
/*     */   public void updatePower(World worldIn, BlockPos pos) {
/*  49 */     if (!worldIn.provider.getHasNoSky()) {
/*  50 */       IBlockState iblockstate = worldIn.getBlockState(pos);
/*  51 */       int i = worldIn.getLightFor(EnumSkyBlock.SKY, pos) - worldIn.getSkylightSubtracted();
/*  52 */       float f = worldIn.getCelestialAngleRadians(1.0F);
/*  53 */       float f1 = (f < 3.1415927F) ? 0.0F : 6.2831855F;
/*  54 */       f += (f1 - f) * 0.2F;
/*  55 */       i = Math.round(i * MathHelper.cos(f));
/*  56 */       i = MathHelper.clamp_int(i, 0, 15);
/*     */       
/*  58 */       if (this.inverted) {
/*  59 */         i = 15 - i;
/*     */       }
/*     */       
/*  62 */       if (((Integer)iblockstate.getValue((IProperty)POWER)).intValue() != i) {
/*  63 */         worldIn.setBlockState(pos, iblockstate.withProperty((IProperty)POWER, Integer.valueOf(i)), 3);
/*     */       }
/*     */     } 
/*     */   }
/*     */   
/*     */   public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumFacing side, float hitX, float hitY, float hitZ) {
/*  69 */     if (playerIn.isAllowEdit()) {
/*  70 */       if (worldIn.isRemote) {
/*  71 */         return true;
/*     */       }
/*  73 */       if (this.inverted) {
/*  74 */         worldIn.setBlockState(pos, Blocks.daylight_detector.getDefaultState().withProperty((IProperty)POWER, state.getValue((IProperty)POWER)), 4);
/*  75 */         Blocks.daylight_detector.updatePower(worldIn, pos);
/*     */       } else {
/*  77 */         worldIn.setBlockState(pos, Blocks.daylight_detector_inverted.getDefaultState().withProperty((IProperty)POWER, state.getValue((IProperty)POWER)), 4);
/*  78 */         Blocks.daylight_detector_inverted.updatePower(worldIn, pos);
/*     */       } 
/*     */       
/*  81 */       return true;
/*     */     } 
/*     */     
/*  84 */     return super.onBlockActivated(worldIn, pos, state, playerIn, side, hitX, hitY, hitZ);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Item getItemDropped(IBlockState state, Random rand, int fortune) {
/*  92 */     return Item.getItemFromBlock(Blocks.daylight_detector);
/*     */   }
/*     */   
/*     */   public Item getItem(World worldIn, BlockPos pos) {
/*  96 */     return Item.getItemFromBlock(Blocks.daylight_detector);
/*     */   }
/*     */   
/*     */   public boolean isFullCube() {
/* 100 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isOpaqueCube() {
/* 107 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getRenderType() {
/* 114 */     return 3;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean canProvidePower() {
/* 121 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public TileEntity createNewTileEntity(World worldIn, int meta) {
/* 128 */     return (TileEntity)new TileEntityDaylightDetector();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public IBlockState getStateFromMeta(int meta) {
/* 135 */     return getDefaultState().withProperty((IProperty)POWER, Integer.valueOf(meta));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getMetaFromState(IBlockState state) {
/* 142 */     return ((Integer)state.getValue((IProperty)POWER)).intValue();
/*     */   }
/*     */   
/*     */   protected BlockState createBlockState() {
/* 146 */     return new BlockState(this, new IProperty[] { (IProperty)POWER });
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void getSubBlocks(Item itemIn, CreativeTabs tab, List<ItemStack> list) {
/* 153 */     if (!this.inverted)
/* 154 */       super.getSubBlocks(itemIn, tab, list); 
/*     */   }
/*     */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\minecraft\block\BlockDaylightDetector.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */