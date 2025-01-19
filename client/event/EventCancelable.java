/*    */ package client.event;
/*    */ 
/*    */ public class EventCancelable
/*    */   extends Event
/*    */ {
/*    */   private boolean cancelled;
/*    */   
/*    */   public boolean isCancelled() {
/*  9 */     return this.cancelled;
/*    */   }
/*    */ 
/*    */   
/*    */   public void setCancelled(boolean cancelled) {
/* 14 */     this.cancelled = cancelled;
/*    */   }
/*    */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\client\event\EventCancelable.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */