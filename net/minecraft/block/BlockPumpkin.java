/*     */ package net.minecraft.block;
/*     */ import com.google.common.base.Predicate;
/*     */ import net.minecraft.block.material.MapColor;
/*     */ import net.minecraft.block.material.Material;
/*     */ import net.minecraft.block.properties.IProperty;
/*     */ import net.minecraft.block.state.BlockState;
/*     */ import net.minecraft.block.state.BlockWorldState;
/*     */ import net.minecraft.block.state.IBlockState;
/*     */ import net.minecraft.block.state.pattern.BlockPattern;
/*     */ import net.minecraft.block.state.pattern.BlockStateHelper;
/*     */ import net.minecraft.block.state.pattern.FactoryBlockPattern;
/*     */ import net.minecraft.creativetab.CreativeTabs;
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.entity.EntityLivingBase;
/*     */ import net.minecraft.entity.monster.EntityIronGolem;
/*     */ import net.minecraft.entity.monster.EntitySnowman;
/*     */ import net.minecraft.init.Blocks;
/*     */ import net.minecraft.util.BlockPos;
/*     */ import net.minecraft.util.EnumFacing;
/*     */ import net.minecraft.util.EnumParticleTypes;
/*     */ import net.minecraft.world.IBlockAccess;
/*     */ import net.minecraft.world.World;
/*     */ 
/*     */ public class BlockPumpkin extends BlockDirectional {
/*     */   private BlockPattern snowmanBasePattern;
/*     */   private BlockPattern snowmanPattern;
/*     */   
/*  28 */   private static final Predicate<IBlockState> field_181085_Q = new Predicate<IBlockState>() {
/*     */       public boolean apply(IBlockState p_apply_1_) {
/*  30 */         return (p_apply_1_ != null && (p_apply_1_.getBlock() == Blocks.pumpkin || p_apply_1_.getBlock() == Blocks.lit_pumpkin));
/*     */       }
/*     */     };
/*     */   private BlockPattern golemBasePattern; private BlockPattern golemPattern;
/*     */   protected BlockPumpkin() {
/*  35 */     super(Material.gourd, MapColor.adobeColor);
/*  36 */     setDefaultState(this.blockState.getBaseState().withProperty((IProperty)FACING, (Comparable)EnumFacing.NORTH));
/*  37 */     setTickRandomly(true);
/*  38 */     setCreativeTab(CreativeTabs.tabBlock);
/*     */   }
/*     */   
/*     */   public void onBlockAdded(World worldIn, BlockPos pos, IBlockState state) {
/*  42 */     super.onBlockAdded(worldIn, pos, state);
/*  43 */     trySpawnGolem(worldIn, pos);
/*     */   }
/*     */   
/*     */   public boolean canDispenserPlace(World worldIn, BlockPos pos) {
/*  47 */     return !(getSnowmanBasePattern().match(worldIn, pos) == null && getGolemBasePattern().match(worldIn, pos) == null);
/*     */   }
/*     */ 
/*     */   
/*     */   private void trySpawnGolem(World worldIn, BlockPos pos) {
/*     */     BlockPattern.PatternHelper blockpattern$patternhelper;
/*  53 */     if ((blockpattern$patternhelper = getSnowmanPattern().match(worldIn, pos)) != null) {
/*  54 */       for (int i = 0; i < getSnowmanPattern().getThumbLength(); i++) {
/*  55 */         BlockWorldState blockworldstate = blockpattern$patternhelper.translateOffset(0, i, 0);
/*  56 */         worldIn.setBlockState(blockworldstate.getPos(), Blocks.air.getDefaultState(), 2);
/*     */       } 
/*     */       
/*  59 */       EntitySnowman entitysnowman = new EntitySnowman(worldIn);
/*  60 */       BlockPos blockpos1 = blockpattern$patternhelper.translateOffset(0, 2, 0).getPos();
/*  61 */       entitysnowman.setLocationAndAngles(blockpos1.getX() + 0.5D, blockpos1.getY() + 0.05D, blockpos1.getZ() + 0.5D, 0.0F, 0.0F);
/*  62 */       worldIn.spawnEntityInWorld((Entity)entitysnowman);
/*     */       
/*  64 */       for (int j = 0; j < 120; j++) {
/*  65 */         worldIn.spawnParticle(EnumParticleTypes.SNOW_SHOVEL, blockpos1.getX() + worldIn.rand.nextDouble(), blockpos1.getY() + worldIn.rand.nextDouble() * 2.5D, blockpos1.getZ() + worldIn.rand.nextDouble(), 0.0D, 0.0D, 0.0D, new int[0]);
/*     */       }
/*     */       
/*  68 */       for (int i1 = 0; i1 < getSnowmanPattern().getThumbLength(); i1++) {
/*  69 */         BlockWorldState blockworldstate1 = blockpattern$patternhelper.translateOffset(0, i1, 0);
/*  70 */         worldIn.notifyNeighborsRespectDebug(blockworldstate1.getPos(), Blocks.air);
/*     */       } 
/*  72 */     } else if ((blockpattern$patternhelper = getGolemPattern().match(worldIn, pos)) != null) {
/*  73 */       for (int k = 0; k < getGolemPattern().getPalmLength(); k++) {
/*  74 */         for (int l = 0; l < getGolemPattern().getThumbLength(); l++) {
/*  75 */           worldIn.setBlockState(blockpattern$patternhelper.translateOffset(k, l, 0).getPos(), Blocks.air.getDefaultState(), 2);
/*     */         }
/*     */       } 
/*     */       
/*  79 */       BlockPos blockpos = blockpattern$patternhelper.translateOffset(1, 2, 0).getPos();
/*  80 */       EntityIronGolem entityirongolem = new EntityIronGolem(worldIn);
/*  81 */       entityirongolem.setPlayerCreated(true);
/*  82 */       entityirongolem.setLocationAndAngles(blockpos.getX() + 0.5D, blockpos.getY() + 0.05D, blockpos.getZ() + 0.5D, 0.0F, 0.0F);
/*  83 */       worldIn.spawnEntityInWorld((Entity)entityirongolem);
/*     */       
/*  85 */       for (int j1 = 0; j1 < 120; j1++) {
/*  86 */         worldIn.spawnParticle(EnumParticleTypes.SNOWBALL, blockpos.getX() + worldIn.rand.nextDouble(), blockpos.getY() + worldIn.rand.nextDouble() * 3.9D, blockpos.getZ() + worldIn.rand.nextDouble(), 0.0D, 0.0D, 0.0D, new int[0]);
/*     */       }
/*     */       
/*  89 */       for (int k1 = 0; k1 < getGolemPattern().getPalmLength(); k1++) {
/*  90 */         for (int l1 = 0; l1 < getGolemPattern().getThumbLength(); l1++) {
/*  91 */           BlockWorldState blockworldstate2 = blockpattern$patternhelper.translateOffset(k1, l1, 0);
/*  92 */           worldIn.notifyNeighborsRespectDebug(blockworldstate2.getPos(), Blocks.air);
/*     */         } 
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   public boolean canPlaceBlockAt(World worldIn, BlockPos pos) {
/*  99 */     return ((worldIn.getBlockState(pos).getBlock()).blockMaterial.isReplaceable() && World.doesBlockHaveSolidTopSurface((IBlockAccess)worldIn, pos.down()));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public IBlockState onBlockPlaced(World worldIn, BlockPos pos, EnumFacing facing, float hitX, float hitY, float hitZ, int meta, EntityLivingBase placer) {
/* 107 */     return getDefaultState().withProperty((IProperty)FACING, (Comparable)placer.getHorizontalFacing().getOpposite());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public IBlockState getStateFromMeta(int meta) {
/* 114 */     return getDefaultState().withProperty((IProperty)FACING, (Comparable)EnumFacing.getHorizontal(meta));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getMetaFromState(IBlockState state) {
/* 121 */     return ((EnumFacing)state.getValue((IProperty)FACING)).getHorizontalIndex();
/*     */   }
/*     */   
/*     */   protected BlockState createBlockState() {
/* 125 */     return new BlockState(this, new IProperty[] { (IProperty)FACING });
/*     */   }
/*     */   
/*     */   protected BlockPattern getSnowmanBasePattern() {
/* 129 */     if (this.snowmanBasePattern == null) {
/* 130 */       this.snowmanBasePattern = FactoryBlockPattern.start().aisle(new String[] { " ", "#", "#" }).where('#', BlockWorldState.hasState((Predicate)BlockStateHelper.forBlock(Blocks.snow))).build();
/*     */     }
/*     */     
/* 133 */     return this.snowmanBasePattern;
/*     */   }
/*     */   
/*     */   protected BlockPattern getSnowmanPattern() {
/* 137 */     if (this.snowmanPattern == null) {
/* 138 */       this.snowmanPattern = FactoryBlockPattern.start().aisle(new String[] { "^", "#", "#" }).where('^', BlockWorldState.hasState(field_181085_Q)).where('#', BlockWorldState.hasState((Predicate)BlockStateHelper.forBlock(Blocks.snow))).build();
/*     */     }
/*     */     
/* 141 */     return this.snowmanPattern;
/*     */   }
/*     */   
/*     */   protected BlockPattern getGolemBasePattern() {
/* 145 */     if (this.golemBasePattern == null) {
/* 146 */       this.golemBasePattern = FactoryBlockPattern.start().aisle(new String[] { "~ ~", "###", "~#~" }).where('#', BlockWorldState.hasState((Predicate)BlockStateHelper.forBlock(Blocks.iron_block))).where('~', BlockWorldState.hasState((Predicate)BlockStateHelper.forBlock(Blocks.air))).build();
/*     */     }
/*     */     
/* 149 */     return this.golemBasePattern;
/*     */   }
/*     */   
/*     */   protected BlockPattern getGolemPattern() {
/* 153 */     if (this.golemPattern == null) {
/* 154 */       this.golemPattern = FactoryBlockPattern.start().aisle(new String[] { "~^~", "###", "~#~" }).where('^', BlockWorldState.hasState(field_181085_Q)).where('#', BlockWorldState.hasState((Predicate)BlockStateHelper.forBlock(Blocks.iron_block))).where('~', BlockWorldState.hasState((Predicate)BlockStateHelper.forBlock(Blocks.air))).build();
/*     */     }
/*     */     
/* 157 */     return this.golemPattern;
/*     */   }
/*     */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\minecraft\block\BlockPumpkin.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */