/*    */ package net.optifine.config;
/*    */ 
/*    */ import net.minecraft.block.state.BlockStateBase;
/*    */ import net.minecraft.src.Config;
/*    */ 
/*    */ public class MatchBlock {
/*  7 */   private int blockId = -1;
/*  8 */   private int[] metadatas = null;
/*    */   
/*    */   public MatchBlock(int blockId) {
/* 11 */     this.blockId = blockId;
/*    */   }
/*    */   
/*    */   public MatchBlock(int blockId, int metadata) {
/* 15 */     this.blockId = blockId;
/*    */     
/* 17 */     if (metadata >= 0 && metadata <= 15) {
/* 18 */       this.metadatas = new int[] { metadata };
/*    */     }
/*    */   }
/*    */   
/*    */   public MatchBlock(int blockId, int[] metadatas) {
/* 23 */     this.blockId = blockId;
/* 24 */     this.metadatas = metadatas;
/*    */   }
/*    */   
/*    */   public int getBlockId() {
/* 28 */     return this.blockId;
/*    */   }
/*    */   
/*    */   public int[] getMetadatas() {
/* 32 */     return this.metadatas;
/*    */   }
/*    */   
/*    */   public boolean matches(BlockStateBase blockState) {
/* 36 */     return (blockState.getBlockId() != this.blockId) ? false : Matches.metadata(blockState.getMetadata(), this.metadatas);
/*    */   }
/*    */   
/*    */   public boolean matches(int id, int metadata) {
/* 40 */     return (id != this.blockId) ? false : Matches.metadata(metadata, this.metadatas);
/*    */   }
/*    */   
/*    */   public void addMetadata(int metadata) {
/* 44 */     if (this.metadatas != null && 
/* 45 */       metadata >= 0 && metadata <= 15) {
/* 46 */       for (int i = 0; i < this.metadatas.length; i++) {
/* 47 */         if (this.metadatas[i] == metadata) {
/*    */           return;
/*    */         }
/*    */       } 
/*    */       
/* 52 */       this.metadatas = Config.addIntToArray(this.metadatas, metadata);
/*    */     } 
/*    */   }
/*    */ 
/*    */   
/*    */   public String toString() {
/* 58 */     return this.blockId + ":" + Config.arrayToString(this.metadatas);
/*    */   }
/*    */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\optifine\config\MatchBlock.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */