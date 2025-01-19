/*    */ package net.minecraft.world.chunk;
/*    */ 
/*    */ import net.minecraft.block.Block;
/*    */ import net.minecraft.block.state.IBlockState;
/*    */ import net.minecraft.init.Blocks;
/*    */ 
/*    */ public class ChunkPrimer {
/*  8 */   private final short[] data = new short[65536];
/*  9 */   private final IBlockState defaultState = Blocks.air.getDefaultState();
/*    */   
/*    */   public IBlockState getBlockState(int x, int y, int z) {
/* 12 */     int i = x << 12 | z << 8 | y;
/* 13 */     return getBlockState(i);
/*    */   }
/*    */   
/*    */   public IBlockState getBlockState(int index) {
/* 17 */     if (index >= 0 && index < this.data.length) {
/* 18 */       IBlockState iblockstate = (IBlockState)Block.BLOCK_STATE_IDS.getByValue(this.data[index]);
/* 19 */       return (iblockstate != null) ? iblockstate : this.defaultState;
/*    */     } 
/* 21 */     throw new IndexOutOfBoundsException("The coordinate is out of range");
/*    */   }
/*    */ 
/*    */   
/*    */   public void setBlockState(int x, int y, int z, IBlockState state) {
/* 26 */     int i = x << 12 | z << 8 | y;
/* 27 */     setBlockState(i, state);
/*    */   }
/*    */   
/*    */   public void setBlockState(int index, IBlockState state) {
/* 31 */     if (index >= 0 && index < this.data.length) {
/* 32 */       this.data[index] = (short)Block.BLOCK_STATE_IDS.get(state);
/*    */     } else {
/* 34 */       throw new IndexOutOfBoundsException("The coordinate is out of range");
/*    */     } 
/*    */   }
/*    */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\minecraft\world\chunk\ChunkPrimer.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */