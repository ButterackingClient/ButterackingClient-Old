/*     */ package net.minecraft.block;
/*     */ 
/*     */ import java.util.Random;
/*     */ import net.minecraft.block.properties.IProperty;
/*     */ import net.minecraft.block.state.IBlockState;
/*     */ import net.minecraft.init.Blocks;
/*     */ import net.minecraft.util.BlockPos;
/*     */ import net.minecraft.world.World;
/*     */ import net.minecraft.world.gen.feature.WorldGenBigMushroom;
/*     */ import net.minecraft.world.gen.feature.WorldGenerator;
/*     */ 
/*     */ public class BlockMushroom extends BlockBush implements IGrowable {
/*     */   protected BlockMushroom() {
/*  14 */     float f = 0.2F;
/*  15 */     setBlockBounds(0.5F - f, 0.0F, 0.5F - f, 0.5F + f, f * 2.0F, 0.5F + f);
/*  16 */     setTickRandomly(true);
/*     */   }
/*     */   
/*     */   public void updateTick(World worldIn, BlockPos pos, IBlockState state, Random rand) {
/*  20 */     if (rand.nextInt(25) == 0) {
/*  21 */       int i = 5;
/*  22 */       int j = 4;
/*     */       
/*  24 */       for (BlockPos blockpos : BlockPos.getAllInBoxMutable(pos.add(-4, -1, -4), pos.add(4, 1, 4))) {
/*     */         
/*  26 */         i--;
/*     */         
/*  28 */         if (worldIn.getBlockState(blockpos).getBlock() == this && i <= 0) {
/*     */           return;
/*     */         }
/*     */       } 
/*     */ 
/*     */       
/*  34 */       BlockPos blockpos1 = pos.add(rand.nextInt(3) - 1, rand.nextInt(2) - rand.nextInt(2), rand.nextInt(3) - 1);
/*     */       
/*  36 */       for (int k = 0; k < 4; k++) {
/*  37 */         if (worldIn.isAirBlock(blockpos1) && canBlockStay(worldIn, blockpos1, getDefaultState())) {
/*  38 */           pos = blockpos1;
/*     */         }
/*     */         
/*  41 */         blockpos1 = pos.add(rand.nextInt(3) - 1, rand.nextInt(2) - rand.nextInt(2), rand.nextInt(3) - 1);
/*     */       } 
/*     */       
/*  44 */       if (worldIn.isAirBlock(blockpos1) && canBlockStay(worldIn, blockpos1, getDefaultState())) {
/*  45 */         worldIn.setBlockState(blockpos1, getDefaultState(), 2);
/*     */       }
/*     */     } 
/*     */   }
/*     */   
/*     */   public boolean canPlaceBlockAt(World worldIn, BlockPos pos) {
/*  51 */     return (super.canPlaceBlockAt(worldIn, pos) && canBlockStay(worldIn, pos, getDefaultState()));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected boolean canPlaceBlockOn(Block ground) {
/*  58 */     return ground.isFullBlock();
/*     */   }
/*     */   
/*     */   public boolean canBlockStay(World worldIn, BlockPos pos, IBlockState state) {
/*  62 */     if (pos.getY() >= 0 && pos.getY() < 256) {
/*  63 */       IBlockState iblockstate = worldIn.getBlockState(pos.down());
/*  64 */       return (iblockstate.getBlock() == Blocks.mycelium) ? true : ((iblockstate.getBlock() == Blocks.dirt && iblockstate.getValue((IProperty)BlockDirt.VARIANT) == BlockDirt.DirtType.PODZOL) ? true : ((worldIn.getLight(pos) < 13 && canPlaceBlockOn(iblockstate.getBlock()))));
/*     */     } 
/*  66 */     return false;
/*     */   }
/*     */   
/*     */   public boolean generateBigMushroom(World worldIn, BlockPos pos, IBlockState state, Random rand) {
/*     */     WorldGenBigMushroom worldGenBigMushroom;
/*  71 */     worldIn.setBlockToAir(pos);
/*  72 */     WorldGenerator worldgenerator = null;
/*     */     
/*  74 */     if (this == Blocks.brown_mushroom) {
/*  75 */       worldGenBigMushroom = new WorldGenBigMushroom(Blocks.brown_mushroom_block);
/*  76 */     } else if (this == Blocks.red_mushroom) {
/*  77 */       worldGenBigMushroom = new WorldGenBigMushroom(Blocks.red_mushroom_block);
/*     */     } 
/*     */     
/*  80 */     if (worldGenBigMushroom != null && worldGenBigMushroom.generate(worldIn, rand, pos)) {
/*  81 */       return true;
/*     */     }
/*  83 */     worldIn.setBlockState(pos, state, 3);
/*  84 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean canGrow(World worldIn, BlockPos pos, IBlockState state, boolean isClient) {
/*  92 */     return true;
/*     */   }
/*     */   
/*     */   public boolean canUseBonemeal(World worldIn, Random rand, BlockPos pos, IBlockState state) {
/*  96 */     return (rand.nextFloat() < 0.4D);
/*     */   }
/*     */   
/*     */   public void grow(World worldIn, Random rand, BlockPos pos, IBlockState state) {
/* 100 */     generateBigMushroom(worldIn, pos, state, rand);
/*     */   }
/*     */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\minecraft\block\BlockMushroom.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */