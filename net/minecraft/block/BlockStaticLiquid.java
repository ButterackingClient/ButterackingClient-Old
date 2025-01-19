/*    */ package net.minecraft.block;
/*    */ 
/*    */ import java.util.Random;
/*    */ import net.minecraft.block.material.Material;
/*    */ import net.minecraft.block.properties.IProperty;
/*    */ import net.minecraft.block.state.IBlockState;
/*    */ import net.minecraft.init.Blocks;
/*    */ import net.minecraft.util.BlockPos;
/*    */ import net.minecraft.util.EnumFacing;
/*    */ import net.minecraft.world.World;
/*    */ 
/*    */ public class BlockStaticLiquid extends BlockLiquid {
/*    */   protected BlockStaticLiquid(Material materialIn) {
/* 14 */     super(materialIn);
/* 15 */     setTickRandomly(false);
/*    */     
/* 17 */     if (materialIn == Material.lava) {
/* 18 */       setTickRandomly(true);
/*    */     }
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void onNeighborBlockChange(World worldIn, BlockPos pos, IBlockState state, Block neighborBlock) {
/* 26 */     if (!checkForMixing(worldIn, pos, state)) {
/* 27 */       updateLiquid(worldIn, pos, state);
/*    */     }
/*    */   }
/*    */   
/*    */   private void updateLiquid(World worldIn, BlockPos pos, IBlockState state) {
/* 32 */     BlockDynamicLiquid blockdynamicliquid = getFlowingBlock(this.blockMaterial);
/* 33 */     worldIn.setBlockState(pos, blockdynamicliquid.getDefaultState().withProperty((IProperty)LEVEL, state.getValue((IProperty)LEVEL)), 2);
/* 34 */     worldIn.scheduleUpdate(pos, blockdynamicliquid, tickRate(worldIn));
/*    */   }
/*    */   
/*    */   public void updateTick(World worldIn, BlockPos pos, IBlockState state, Random rand) {
/* 38 */     if (this.blockMaterial == Material.lava && 
/* 39 */       worldIn.getGameRules().getBoolean("doFireTick")) {
/* 40 */       int i = rand.nextInt(3);
/*    */       
/* 42 */       if (i > 0) {
/* 43 */         BlockPos blockpos = pos;
/*    */         
/* 45 */         for (int j = 0; j < i; j++) {
/* 46 */           blockpos = blockpos.add(rand.nextInt(3) - 1, 1, rand.nextInt(3) - 1);
/* 47 */           Block block = worldIn.getBlockState(blockpos).getBlock();
/*    */           
/* 49 */           if (block.blockMaterial == Material.air) {
/* 50 */             if (isSurroundingBlockFlammable(worldIn, blockpos)) {
/* 51 */               worldIn.setBlockState(blockpos, Blocks.fire.getDefaultState());
/*    */               return;
/*    */             } 
/* 54 */           } else if (block.blockMaterial.blocksMovement()) {
/*    */             return;
/*    */           } 
/*    */         } 
/*    */       } else {
/* 59 */         for (int k = 0; k < 3; k++) {
/* 60 */           BlockPos blockpos1 = pos.add(rand.nextInt(3) - 1, 0, rand.nextInt(3) - 1);
/*    */           
/* 62 */           if (worldIn.isAirBlock(blockpos1.up()) && getCanBlockBurn(worldIn, blockpos1))
/* 63 */             worldIn.setBlockState(blockpos1.up(), Blocks.fire.getDefaultState()); 
/*    */         } 
/*    */       } 
/*    */     } 
/*    */   }
/*    */   protected boolean isSurroundingBlockFlammable(World worldIn, BlockPos pos) {
/*    */     byte b;
/*    */     int i;
/*    */     EnumFacing[] arrayOfEnumFacing;
/* 72 */     for (i = (arrayOfEnumFacing = EnumFacing.values()).length, b = 0; b < i; ) { EnumFacing enumfacing = arrayOfEnumFacing[b];
/* 73 */       if (getCanBlockBurn(worldIn, pos.offset(enumfacing))) {
/* 74 */         return true;
/*    */       }
/*    */       b++; }
/*    */     
/* 78 */     return false;
/*    */   }
/*    */   
/*    */   private boolean getCanBlockBurn(World worldIn, BlockPos pos) {
/* 82 */     return worldIn.getBlockState(pos).getBlock().getMaterial().getCanBurn();
/*    */   }
/*    */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\minecraft\block\BlockStaticLiquid.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */