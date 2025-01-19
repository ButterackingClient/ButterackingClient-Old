/*    */ package net.minecraft.block;
/*    */ 
/*    */ import net.minecraft.util.BlockPos;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class BlockEventData
/*    */ {
/*    */   private BlockPos position;
/*    */   private Block blockType;
/*    */   private int eventID;
/*    */   private int eventParameter;
/*    */   
/*    */   public BlockEventData(BlockPos pos, Block blockType, int eventId, int p_i45756_4_) {
/* 16 */     this.position = pos;
/* 17 */     this.eventID = eventId;
/* 18 */     this.eventParameter = p_i45756_4_;
/* 19 */     this.blockType = blockType;
/*    */   }
/*    */   
/*    */   public BlockPos getPosition() {
/* 23 */     return this.position;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public int getEventID() {
/* 30 */     return this.eventID;
/*    */   }
/*    */   
/*    */   public int getEventParameter() {
/* 34 */     return this.eventParameter;
/*    */   }
/*    */   
/*    */   public Block getBlock() {
/* 38 */     return this.blockType;
/*    */   }
/*    */   
/*    */   public boolean equals(Object p_equals_1_) {
/* 42 */     if (!(p_equals_1_ instanceof BlockEventData)) {
/* 43 */       return false;
/*    */     }
/* 45 */     BlockEventData blockeventdata = (BlockEventData)p_equals_1_;
/* 46 */     return (this.position.equals(blockeventdata.position) && this.eventID == blockeventdata.eventID && this.eventParameter == blockeventdata.eventParameter && this.blockType == blockeventdata.blockType);
/*    */   }
/*    */ 
/*    */   
/*    */   public String toString() {
/* 51 */     return "TE(" + this.position + ")," + this.eventID + "," + this.eventParameter + "," + this.blockType;
/*    */   }
/*    */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\minecraft\block\BlockEventData.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */