/*    */ package net.minecraft.world;
/*    */ 
/*    */ import net.minecraft.block.Block;
/*    */ import net.minecraft.util.BlockPos;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class NextTickListEntry
/*    */   implements Comparable<NextTickListEntry>
/*    */ {
/*    */   private static long nextTickEntryID;
/*    */   private final Block block;
/*    */   public final BlockPos position;
/*    */   public long scheduledTime;
/*    */   public int priority;
/*    */   private long tickEntryID;
/*    */   
/*    */   public NextTickListEntry(BlockPos positionIn, Block blockIn) {
/* 26 */     this.tickEntryID = nextTickEntryID++;
/* 27 */     this.position = positionIn;
/* 28 */     this.block = blockIn;
/*    */   }
/*    */   
/*    */   public boolean equals(Object p_equals_1_) {
/* 32 */     if (!(p_equals_1_ instanceof NextTickListEntry)) {
/* 33 */       return false;
/*    */     }
/* 35 */     NextTickListEntry nextticklistentry = (NextTickListEntry)p_equals_1_;
/* 36 */     return (this.position.equals(nextticklistentry.position) && Block.isEqualTo(this.block, nextticklistentry.block));
/*    */   }
/*    */ 
/*    */   
/*    */   public int hashCode() {
/* 41 */     return this.position.hashCode();
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public NextTickListEntry setScheduledTime(long scheduledTimeIn) {
/* 48 */     this.scheduledTime = scheduledTimeIn;
/* 49 */     return this;
/*    */   }
/*    */   
/*    */   public void setPriority(int priorityIn) {
/* 53 */     this.priority = priorityIn;
/*    */   }
/*    */   
/*    */   public int compareTo(NextTickListEntry p_compareTo_1_) {
/* 57 */     return (this.scheduledTime < p_compareTo_1_.scheduledTime) ? -1 : ((this.scheduledTime > p_compareTo_1_.scheduledTime) ? 1 : ((this.priority != p_compareTo_1_.priority) ? (this.priority - p_compareTo_1_.priority) : ((this.tickEntryID < p_compareTo_1_.tickEntryID) ? -1 : ((this.tickEntryID > p_compareTo_1_.tickEntryID) ? 1 : 0))));
/*    */   }
/*    */   
/*    */   public String toString() {
/* 61 */     return String.valueOf(Block.getIdFromBlock(this.block)) + ": " + this.position + ", " + this.scheduledTime + ", " + this.priority + ", " + this.tickEntryID;
/*    */   }
/*    */   
/*    */   public Block getBlock() {
/* 65 */     return this.block;
/*    */   }
/*    */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\minecraft\world\NextTickListEntry.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */