/*    */ package client.event;
/*    */ 
/*    */ import client.Client;
/*    */ import java.lang.reflect.InvocationTargetException;
/*    */ 
/*    */ 
/*    */ public abstract class Event
/*    */ {
/*    */   private boolean cancelled;
/*    */   
/*    */   public Event call() {
/* 12 */     this.cancelled = false;
/* 13 */     call(this);
/* 14 */     return this;
/*    */   }
/*    */   
/*    */   public boolean isCancelled() {
/* 18 */     return this.cancelled;
/*    */   }
/*    */   
/*    */   public void setCancelled(boolean cancelled) {
/* 22 */     this.cancelled = cancelled;
/*    */   }
/*    */   
/*    */   private static final void call(Event event) {
/* 26 */     EventManager eventManager = (Client.getInstance()).eventManager;
/* 27 */     ArrayHelper<Data> dataList = EventManager.get((Class)event.getClass());
/* 28 */     if (dataList != null) {
/* 29 */       for (Data data : dataList) {
/*    */         try {
/* 31 */           data.target.invoke(data.source, new Object[] { event });
/*    */         }
/* 33 */         catch (IllegalAccessException e) {
/* 34 */           e.printStackTrace();
/*    */         }
/* 36 */         catch (InvocationTargetException e2) {
/* 37 */           e2.printStackTrace();
/*    */         } 
/*    */       } 
/*    */     }
/*    */   }
/*    */   
/*    */   public enum State
/*    */   {
/* 45 */     PRE("PRE", 0, "PRE", 0, "PRE", 0),
/* 46 */     POST("POST", 1, "POST", 1, "POST", 1);
/*    */   }
/*    */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\client\event\Event.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */