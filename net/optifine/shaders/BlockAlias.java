/*    */ package net.optifine.shaders;
/*    */ 
/*    */ import java.util.ArrayList;
/*    */ import java.util.HashSet;
/*    */ import java.util.List;
/*    */ import java.util.Set;
/*    */ import net.minecraft.src.Config;
/*    */ import net.optifine.config.MatchBlock;
/*    */ 
/*    */ public class BlockAlias
/*    */ {
/*    */   private int blockAliasId;
/*    */   private MatchBlock[] matchBlocks;
/*    */   
/*    */   public BlockAlias(int blockAliasId, MatchBlock[] matchBlocks) {
/* 16 */     this.blockAliasId = blockAliasId;
/* 17 */     this.matchBlocks = matchBlocks;
/*    */   }
/*    */   
/*    */   public int getBlockAliasId() {
/* 21 */     return this.blockAliasId;
/*    */   }
/*    */   
/*    */   public boolean matches(int id, int metadata) {
/* 25 */     for (int i = 0; i < this.matchBlocks.length; i++) {
/* 26 */       MatchBlock matchblock = this.matchBlocks[i];
/*    */       
/* 28 */       if (matchblock.matches(id, metadata)) {
/* 29 */         return true;
/*    */       }
/*    */     } 
/*    */     
/* 33 */     return false;
/*    */   }
/*    */   
/*    */   public int[] getMatchBlockIds() {
/* 37 */     Set<Integer> set = new HashSet<>();
/*    */     
/* 39 */     for (int i = 0; i < this.matchBlocks.length; i++) {
/* 40 */       MatchBlock matchblock = this.matchBlocks[i];
/* 41 */       int j = matchblock.getBlockId();
/* 42 */       set.add(Integer.valueOf(j));
/*    */     } 
/*    */     
/* 45 */     Integer[] ainteger = set.<Integer>toArray(new Integer[set.size()]);
/* 46 */     int[] aint = Config.toPrimitive(ainteger);
/* 47 */     return aint;
/*    */   }
/*    */   
/*    */   public MatchBlock[] getMatchBlocks(int matchBlockId) {
/* 51 */     List<MatchBlock> list = new ArrayList<>();
/*    */     
/* 53 */     for (int i = 0; i < this.matchBlocks.length; i++) {
/* 54 */       MatchBlock matchblock = this.matchBlocks[i];
/*    */       
/* 56 */       if (matchblock.getBlockId() == matchBlockId) {
/* 57 */         list.add(matchblock);
/*    */       }
/*    */     } 
/*    */     
/* 61 */     MatchBlock[] amatchblock = list.<MatchBlock>toArray(new MatchBlock[list.size()]);
/* 62 */     return amatchblock;
/*    */   }
/*    */   
/*    */   public String toString() {
/* 66 */     return "block." + this.blockAliasId + "=" + Config.arrayToString((Object[])this.matchBlocks);
/*    */   }
/*    */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\optifine\shaders\BlockAlias.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */