/*    */ package io.netty.channel.local;
/*    */ 
/*    */ import io.netty.channel.EventLoopGroup;
/*    */ import io.netty.channel.SingleThreadEventLoop;
/*    */ import java.util.concurrent.ThreadFactory;
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
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ final class LocalEventLoop
/*    */   extends SingleThreadEventLoop
/*    */ {
/*    */   LocalEventLoop(LocalEventLoopGroup parent, ThreadFactory threadFactory) {
/* 25 */     super((EventLoopGroup)parent, threadFactory, true);
/*    */   }
/*    */ 
/*    */   
/*    */   protected void run() {
/*    */     do {
/* 31 */       Runnable task = takeTask();
/* 32 */       if (task == null)
/* 33 */         continue;  task.run();
/* 34 */       updateLastExecutionTime();
/*    */     
/*    */     }
/* 37 */     while (!confirmShutdown());
/*    */   }
/*    */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\io\netty\channel\local\LocalEventLoop.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */