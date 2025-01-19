/*    */ package client;
/*    */ 
/*    */ import client.event.EventCancelable;
/*    */ 
/*    */ public class KeyEvent
/*    */   extends EventCancelable {
/*    */   private final int key;
/*    */   
/*    */   public KeyEvent(int key) {
/* 10 */     this.key = key;
/*    */   }
/*    */   
/*    */   public int getKey() {
/* 14 */     return this.key;
/*    */   }
/*    */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\client\KeyEvent.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */