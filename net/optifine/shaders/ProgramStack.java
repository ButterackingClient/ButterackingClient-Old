/*    */ package net.optifine.shaders;
/*    */ 
/*    */ import java.util.ArrayDeque;
/*    */ import java.util.Deque;
/*    */ 
/*    */ public class ProgramStack {
/*  7 */   private Deque<Program> stack = new ArrayDeque<>();
/*    */   
/*    */   public void push(Program p) {
/* 10 */     this.stack.addLast(p);
/*    */     
/* 12 */     if (this.stack.size() > 100) {
/* 13 */       throw new RuntimeException("Program stack overflow: " + this.stack.size());
/*    */     }
/*    */   }
/*    */   
/*    */   public Program pop() {
/* 18 */     if (this.stack.isEmpty()) {
/* 19 */       throw new RuntimeException("Program stack empty");
/*    */     }
/* 21 */     Program program = this.stack.pollLast();
/* 22 */     return program;
/*    */   }
/*    */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\optifine\shaders\ProgramStack.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */