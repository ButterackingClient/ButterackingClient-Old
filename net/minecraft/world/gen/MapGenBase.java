/*    */ package net.minecraft.world.gen;
/*    */ 
/*    */ import java.util.Random;
/*    */ import net.minecraft.world.World;
/*    */ import net.minecraft.world.chunk.ChunkPrimer;
/*    */ import net.minecraft.world.chunk.IChunkProvider;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class MapGenBase
/*    */ {
/* 13 */   protected int range = 8;
/*    */ 
/*    */ 
/*    */ 
/*    */   
/* 18 */   protected Random rand = new Random();
/*    */ 
/*    */   
/*    */   protected World worldObj;
/*    */ 
/*    */ 
/*    */   
/*    */   public void generate(IChunkProvider chunkProviderIn, World worldIn, int x, int z, ChunkPrimer chunkPrimerIn) {
/* 26 */     int i = this.range;
/* 27 */     this.worldObj = worldIn;
/* 28 */     this.rand.setSeed(worldIn.getSeed());
/* 29 */     long j = this.rand.nextLong();
/* 30 */     long k = this.rand.nextLong();
/*    */     
/* 32 */     for (int l = x - i; l <= x + i; l++) {
/* 33 */       for (int i1 = z - i; i1 <= z + i; i1++) {
/* 34 */         long j1 = l * j;
/* 35 */         long k1 = i1 * k;
/* 36 */         this.rand.setSeed(j1 ^ k1 ^ worldIn.getSeed());
/* 37 */         recursiveGenerate(worldIn, l, i1, x, z, chunkPrimerIn);
/*    */       } 
/*    */     } 
/*    */   }
/*    */   
/*    */   protected void recursiveGenerate(World worldIn, int chunkX, int chunkZ, int p_180701_4_, int p_180701_5_, ChunkPrimer chunkPrimerIn) {}
/*    */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\minecraft\world\gen\MapGenBase.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */