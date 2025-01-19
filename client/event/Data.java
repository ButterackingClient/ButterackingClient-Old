/*    */ package client.event;
/*    */ 
/*    */ import java.lang.reflect.Method;
/*    */ 
/*    */ public class Data
/*    */ {
/*    */   public final Object source;
/*    */   public final Method target;
/*    */   public final byte priority;
/*    */   
/*    */   Data(Object source, Method target, byte priority) {
/* 12 */     this.source = source;
/* 13 */     this.target = target;
/* 14 */     this.priority = priority;
/*    */   }
/*    */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\client\event\Data.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */