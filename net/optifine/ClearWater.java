/*    */ package net.optifine;
/*    */ 
/*    */ import net.minecraft.block.Block;
/*    */ import net.minecraft.block.BlockAir;
/*    */ import net.minecraft.block.material.Material;
/*    */ import net.minecraft.block.state.IBlockState;
/*    */ import net.minecraft.client.settings.GameSettings;
/*    */ import net.minecraft.entity.Entity;
/*    */ import net.minecraft.init.Blocks;
/*    */ import net.minecraft.src.Config;
/*    */ import net.minecraft.util.BlockPos;
/*    */ import net.minecraft.world.World;
/*    */ import net.minecraft.world.chunk.Chunk;
/*    */ import net.minecraft.world.chunk.IChunkProvider;
/*    */ 
/*    */ public class ClearWater {
/*    */   public static void updateWaterOpacity(GameSettings settings, World world) {
/* 18 */     if (settings != null) {
/* 19 */       int i = 3;
/*    */       
/* 21 */       if (settings.ofClearWater) {
/* 22 */         i = 1;
/*    */       }
/*    */       
/* 25 */       BlockAir.setLightOpacity((Block)Blocks.water, i);
/* 26 */       BlockAir.setLightOpacity((Block)Blocks.flowing_water, i);
/*    */     } 
/*    */     
/* 29 */     if (world != null) {
/* 30 */       IChunkProvider ichunkprovider = world.getChunkProvider();
/*    */       
/* 32 */       if (ichunkprovider != null) {
/* 33 */         Entity entity = Config.getMinecraft().getRenderViewEntity();
/*    */         
/* 35 */         if (entity != null) {
/* 36 */           int j = (int)entity.posX / 16;
/* 37 */           int k = (int)entity.posZ / 16;
/* 38 */           int l = j - 512;
/* 39 */           int i1 = j + 512;
/* 40 */           int j1 = k - 512;
/* 41 */           int k1 = k + 512;
/* 42 */           int l1 = 0;
/*    */           
/* 44 */           for (int i2 = l; i2 < i1; i2++) {
/* 45 */             for (int j2 = j1; j2 < k1; j2++) {
/* 46 */               if (ichunkprovider.chunkExists(i2, j2)) {
/* 47 */                 Chunk chunk = ichunkprovider.provideChunk(i2, j2);
/*    */                 
/* 49 */                 if (chunk != null && !(chunk instanceof net.minecraft.world.chunk.EmptyChunk)) {
/* 50 */                   int k2 = i2 << 4;
/* 51 */                   int l2 = j2 << 4;
/* 52 */                   int i3 = k2 + 16;
/* 53 */                   int j3 = l2 + 16;
/* 54 */                   BlockPosM blockposm = new BlockPosM(0, 0, 0);
/* 55 */                   BlockPosM blockposm1 = new BlockPosM(0, 0, 0);
/*    */                   
/* 57 */                   for (int k3 = k2; k3 < i3; k3++) {
/* 58 */                     for (int l3 = l2; l3 < j3; l3++) {
/* 59 */                       blockposm.setXyz(k3, 0, l3);
/* 60 */                       BlockPos blockpos = world.getPrecipitationHeight(blockposm);
/*    */                       
/* 62 */                       for (int i4 = 0; i4 < blockpos.getY(); i4++) {
/* 63 */                         blockposm1.setXyz(k3, i4, l3);
/* 64 */                         IBlockState iblockstate = world.getBlockState(blockposm1);
/*    */                         
/* 66 */                         if (iblockstate.getBlock().getMaterial() == Material.water) {
/* 67 */                           world.markBlocksDirtyVertical(k3, l3, blockposm1.getY(), blockpos.getY());
/* 68 */                           l1++;
/*    */                           
/*    */                           break;
/*    */                         } 
/*    */                       } 
/*    */                     } 
/*    */                   } 
/*    */                 } 
/*    */               } 
/*    */             } 
/*    */           } 
/* 79 */           if (l1 > 0) {
/* 80 */             String s = "server";
/*    */             
/* 82 */             if (Config.isMinecraftThread()) {
/* 83 */               s = "client";
/*    */             }
/*    */             
/* 86 */             Config.dbg("ClearWater (" + s + ") relighted " + l1 + " chunks");
/*    */           } 
/*    */         } 
/*    */       } 
/*    */     } 
/*    */   }
/*    */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\optifine\ClearWater.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */