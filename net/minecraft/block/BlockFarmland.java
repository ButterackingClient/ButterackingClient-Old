/*     */ package net.minecraft.block;
/*     */ 
/*     */ import java.util.Random;
/*     */ import net.minecraft.block.material.Material;
/*     */ import net.minecraft.block.properties.IProperty;
/*     */ import net.minecraft.block.properties.PropertyInteger;
/*     */ import net.minecraft.block.state.BlockState;
/*     */ import net.minecraft.block.state.IBlockState;
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.init.Blocks;
/*     */ import net.minecraft.item.Item;
/*     */ import net.minecraft.util.AxisAlignedBB;
/*     */ import net.minecraft.util.BlockPos;
/*     */ import net.minecraft.util.EnumFacing;
/*     */ import net.minecraft.world.IBlockAccess;
/*     */ import net.minecraft.world.World;
/*     */ 
/*     */ 
/*     */ public class BlockFarmland
/*     */   extends Block
/*     */ {
/*  22 */   public static final PropertyInteger MOISTURE = PropertyInteger.create("moisture", 0, 7);
/*     */   
/*     */   protected BlockFarmland() {
/*  25 */     super(Material.ground);
/*  26 */     setDefaultState(this.blockState.getBaseState().withProperty((IProperty)MOISTURE, Integer.valueOf(0)));
/*  27 */     setTickRandomly(true);
/*  28 */     setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 0.9375F, 1.0F);
/*  29 */     setLightOpacity(255);
/*     */   }
/*     */   
/*     */   public AxisAlignedBB getCollisionBoundingBox(World worldIn, BlockPos pos, IBlockState state) {
/*  33 */     return new AxisAlignedBB(pos.getX(), pos.getY(), pos.getZ(), (pos.getX() + 1), (pos.getY() + 1), (pos.getZ() + 1));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isOpaqueCube() {
/*  40 */     return false;
/*     */   }
/*     */   
/*     */   public boolean isFullCube() {
/*  44 */     return false;
/*     */   }
/*     */   
/*     */   public void updateTick(World worldIn, BlockPos pos, IBlockState state, Random rand) {
/*  48 */     int i = ((Integer)state.getValue((IProperty)MOISTURE)).intValue();
/*     */     
/*  50 */     if (!hasWater(worldIn, pos) && !worldIn.isRainingAt(pos.up())) {
/*  51 */       if (i > 0) {
/*  52 */         worldIn.setBlockState(pos, state.withProperty((IProperty)MOISTURE, Integer.valueOf(i - 1)), 2);
/*  53 */       } else if (!hasCrops(worldIn, pos)) {
/*  54 */         worldIn.setBlockState(pos, Blocks.dirt.getDefaultState());
/*     */       } 
/*  56 */     } else if (i < 7) {
/*  57 */       worldIn.setBlockState(pos, state.withProperty((IProperty)MOISTURE, Integer.valueOf(7)), 2);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void onFallenUpon(World worldIn, BlockPos pos, Entity entityIn, float fallDistance) {
/*  65 */     if (entityIn instanceof net.minecraft.entity.EntityLivingBase) {
/*  66 */       if (!worldIn.isRemote && worldIn.rand.nextFloat() < fallDistance - 0.5F) {
/*  67 */         if (!(entityIn instanceof net.minecraft.entity.player.EntityPlayer) && !worldIn.getGameRules().getBoolean("mobGriefing")) {
/*     */           return;
/*     */         }
/*     */         
/*  71 */         worldIn.setBlockState(pos, Blocks.dirt.getDefaultState());
/*     */       } 
/*     */       
/*  74 */       super.onFallenUpon(worldIn, pos, entityIn, fallDistance);
/*     */     } 
/*     */   }
/*     */   
/*     */   private boolean hasCrops(World worldIn, BlockPos pos) {
/*  79 */     Block block = worldIn.getBlockState(pos.up()).getBlock();
/*  80 */     return !(!(block instanceof BlockCrops) && !(block instanceof BlockStem));
/*     */   }
/*     */   
/*     */   private boolean hasWater(World worldIn, BlockPos pos) {
/*  84 */     for (BlockPos.MutableBlockPos blockpos$mutableblockpos : BlockPos.getAllInBoxMutable(pos.add(-4, 0, -4), pos.add(4, 1, 4))) {
/*  85 */       if (worldIn.getBlockState((BlockPos)blockpos$mutableblockpos).getBlock().getMaterial() == Material.water) {
/*  86 */         return true;
/*     */       }
/*     */     } 
/*     */     
/*  90 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void onNeighborBlockChange(World worldIn, BlockPos pos, IBlockState state, Block neighborBlock) {
/*  97 */     super.onNeighborBlockChange(worldIn, pos, state, neighborBlock);
/*     */     
/*  99 */     if (worldIn.getBlockState(pos.up()).getBlock().getMaterial().isSolid())
/* 100 */       worldIn.setBlockState(pos, Blocks.dirt.getDefaultState()); 
/*     */   }
/*     */   
/*     */   public boolean shouldSideBeRendered(IBlockAccess worldIn, BlockPos pos, EnumFacing side) {
/*     */     Block block;
/* 105 */     switch (side) {
/*     */       case UP:
/* 107 */         return true;
/*     */       
/*     */       case NORTH:
/*     */       case SOUTH:
/*     */       case WEST:
/*     */       case EAST:
/* 113 */         block = worldIn.getBlockState(pos).getBlock();
/* 114 */         return (!block.isOpaqueCube() && block != Blocks.farmland);
/*     */     } 
/*     */     
/* 117 */     return super.shouldSideBeRendered(worldIn, pos, side);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Item getItemDropped(IBlockState state, Random rand, int fortune) {
/* 125 */     return Blocks.dirt.getItemDropped(Blocks.dirt.getDefaultState().withProperty((IProperty)BlockDirt.VARIANT, BlockDirt.DirtType.DIRT), rand, fortune);
/*     */   }
/*     */   
/*     */   public Item getItem(World worldIn, BlockPos pos) {
/* 129 */     return Item.getItemFromBlock(Blocks.dirt);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public IBlockState getStateFromMeta(int meta) {
/* 136 */     return getDefaultState().withProperty((IProperty)MOISTURE, Integer.valueOf(meta & 0x7));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getMetaFromState(IBlockState state) {
/* 143 */     return ((Integer)state.getValue((IProperty)MOISTURE)).intValue();
/*     */   }
/*     */   
/*     */   protected BlockState createBlockState() {
/* 147 */     return new BlockState(this, new IProperty[] { (IProperty)MOISTURE });
/*     */   }
/*     */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\minecraft\block\BlockFarmland.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */