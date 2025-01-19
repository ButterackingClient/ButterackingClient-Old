/*    */ package net.optifine.config;
/*    */ 
/*    */ import net.minecraft.block.state.BlockStateBase;
/*    */ import net.minecraft.client.renderer.texture.TextureAtlasSprite;
/*    */ import net.minecraft.world.biome.BiomeGenBase;
/*    */ 
/*    */ public class Matches {
/*    */   public static boolean block(BlockStateBase blockStateBase, MatchBlock[] matchBlocks) {
/*  9 */     if (matchBlocks == null) {
/* 10 */       return true;
/*    */     }
/* 12 */     for (int i = 0; i < matchBlocks.length; i++) {
/* 13 */       MatchBlock matchblock = matchBlocks[i];
/*    */       
/* 15 */       if (matchblock.matches(blockStateBase)) {
/* 16 */         return true;
/*    */       }
/*    */     } 
/*    */     
/* 20 */     return false;
/*    */   }
/*    */ 
/*    */   
/*    */   public static boolean block(int blockId, int metadata, MatchBlock[] matchBlocks) {
/* 25 */     if (matchBlocks == null) {
/* 26 */       return true;
/*    */     }
/* 28 */     for (int i = 0; i < matchBlocks.length; i++) {
/* 29 */       MatchBlock matchblock = matchBlocks[i];
/*    */       
/* 31 */       if (matchblock.matches(blockId, metadata)) {
/* 32 */         return true;
/*    */       }
/*    */     } 
/*    */     
/* 36 */     return false;
/*    */   }
/*    */ 
/*    */   
/*    */   public static boolean blockId(int blockId, MatchBlock[] matchBlocks) {
/* 41 */     if (matchBlocks == null) {
/* 42 */       return true;
/*    */     }
/* 44 */     for (int i = 0; i < matchBlocks.length; i++) {
/* 45 */       MatchBlock matchblock = matchBlocks[i];
/*    */       
/* 47 */       if (matchblock.getBlockId() == blockId) {
/* 48 */         return true;
/*    */       }
/*    */     } 
/*    */     
/* 52 */     return false;
/*    */   }
/*    */ 
/*    */   
/*    */   public static boolean metadata(int metadata, int[] metadatas) {
/* 57 */     if (metadatas == null) {
/* 58 */       return true;
/*    */     }
/* 60 */     for (int i = 0; i < metadatas.length; i++) {
/* 61 */       if (metadatas[i] == metadata) {
/* 62 */         return true;
/*    */       }
/*    */     } 
/*    */     
/* 66 */     return false;
/*    */   }
/*    */ 
/*    */   
/*    */   public static boolean sprite(TextureAtlasSprite sprite, TextureAtlasSprite[] sprites) {
/* 71 */     if (sprites == null) {
/* 72 */       return true;
/*    */     }
/* 74 */     for (int i = 0; i < sprites.length; i++) {
/* 75 */       if (sprites[i] == sprite) {
/* 76 */         return true;
/*    */       }
/*    */     } 
/*    */     
/* 80 */     return false;
/*    */   }
/*    */ 
/*    */   
/*    */   public static boolean biome(BiomeGenBase biome, BiomeGenBase[] biomes) {
/* 85 */     if (biomes == null) {
/* 86 */       return true;
/*    */     }
/* 88 */     for (int i = 0; i < biomes.length; i++) {
/* 89 */       if (biomes[i] == biome) {
/* 90 */         return true;
/*    */       }
/*    */     } 
/*    */     
/* 94 */     return false;
/*    */   }
/*    */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\optifine\config\Matches.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */