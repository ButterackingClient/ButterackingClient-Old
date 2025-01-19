/*    */ package net.optifine.util;
/*    */ 
/*    */ import net.minecraft.client.renderer.chunk.RenderChunk;
/*    */ import net.minecraft.util.MathHelper;
/*    */ import net.minecraft.world.chunk.storage.ExtendedBlockStorage;
/*    */ 
/*    */ public class RenderChunkUtils {
/*    */   public static int getCountBlocks(RenderChunk renderChunk) {
/*  9 */     ExtendedBlockStorage[] aextendedblockstorage = renderChunk.getChunk().getBlockStorageArray();
/*    */     
/* 11 */     if (aextendedblockstorage == null) {
/* 12 */       return 0;
/*    */     }
/* 14 */     int i = renderChunk.getPosition().getY() >> 4;
/* 15 */     ExtendedBlockStorage extendedblockstorage = aextendedblockstorage[i];
/* 16 */     return (extendedblockstorage == null) ? 0 : extendedblockstorage.getBlockRefCount();
/*    */   }
/*    */ 
/*    */   
/*    */   public static double getRelativeBufferSize(RenderChunk renderChunk) {
/* 21 */     int i = getCountBlocks(renderChunk);
/* 22 */     double d0 = getRelativeBufferSize(i);
/* 23 */     return d0;
/*    */   }
/*    */   
/*    */   public static double getRelativeBufferSize(int blockCount) {
/* 27 */     double d0 = blockCount / 4096.0D;
/* 28 */     d0 *= 0.995D;
/* 29 */     double d1 = d0 * 2.0D - 1.0D;
/* 30 */     d1 = MathHelper.clamp_double(d1, -1.0D, 1.0D);
/* 31 */     return MathHelper.sqrt_double(1.0D - d1 * d1);
/*    */   }
/*    */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\optifin\\util\RenderChunkUtils.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */