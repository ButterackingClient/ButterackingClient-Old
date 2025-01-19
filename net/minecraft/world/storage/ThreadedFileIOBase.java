/*    */ package net.minecraft.world.storage;
/*    */ 
/*    */ import com.google.common.collect.Lists;
/*    */ import java.util.Collections;
/*    */ import java.util.List;
/*    */ 
/*    */ 
/*    */ 
/*    */ public class ThreadedFileIOBase
/*    */   implements Runnable
/*    */ {
/* 12 */   private static final ThreadedFileIOBase threadedIOInstance = new ThreadedFileIOBase();
/* 13 */   private List<IThreadedFileIO> threadedIOQueue = Collections.synchronizedList(Lists.newArrayList());
/*    */   private volatile long writeQueuedCounter;
/*    */   private volatile long savedIOCounter;
/*    */   private volatile boolean isThreadWaiting;
/*    */   
/*    */   private ThreadedFileIOBase() {
/* 19 */     Thread thread = new Thread(this, "File IO Thread");
/* 20 */     thread.setPriority(1);
/* 21 */     thread.start();
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public static ThreadedFileIOBase getThreadedIOInstance() {
/* 28 */     return threadedIOInstance;
/*    */   }
/*    */   
/*    */   public void run() {
/*    */     while (true) {
/* 33 */       processQueue();
/*    */     }
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   private void processQueue() {
/* 41 */     for (int i = 0; i < this.threadedIOQueue.size(); i++) {
/* 42 */       IThreadedFileIO ithreadedfileio = this.threadedIOQueue.get(i);
/* 43 */       boolean flag = ithreadedfileio.writeNextIO();
/*    */       
/* 45 */       if (!flag) {
/* 46 */         this.threadedIOQueue.remove(i--);
/* 47 */         this.savedIOCounter++;
/*    */       } 
/*    */       
/*    */       try {
/* 51 */         Thread.sleep(this.isThreadWaiting ? 0L : 10L);
/* 52 */       } catch (InterruptedException interruptedexception1) {
/* 53 */         interruptedexception1.printStackTrace();
/*    */       } 
/*    */     } 
/*    */     
/* 57 */     if (this.threadedIOQueue.isEmpty()) {
/*    */       try {
/* 59 */         Thread.sleep(25L);
/* 60 */       } catch (InterruptedException interruptedexception) {
/* 61 */         interruptedexception.printStackTrace();
/*    */       } 
/*    */     }
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void queueIO(IThreadedFileIO p_75735_1_) {
/* 70 */     if (!this.threadedIOQueue.contains(p_75735_1_)) {
/* 71 */       this.writeQueuedCounter++;
/* 72 */       this.threadedIOQueue.add(p_75735_1_);
/*    */     } 
/*    */   }
/*    */   
/*    */   public void waitForFinish() throws InterruptedException {
/* 77 */     this.isThreadWaiting = true;
/*    */     
/* 79 */     while (this.writeQueuedCounter != this.savedIOCounter) {
/* 80 */       Thread.sleep(10L);
/*    */     }
/*    */     
/* 83 */     this.isThreadWaiting = false;
/*    */   }
/*    */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\minecraft\world\storage\ThreadedFileIOBase.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */