/*    */ package net.minecraft.util;
/*    */ 
/*    */ import org.lwjgl.input.Mouse;
/*    */ import org.lwjgl.opengl.Display;
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
/*    */ public class MouseHelper
/*    */ {
/*    */   public int deltaX;
/*    */   public int deltaY;
/*    */   
/*    */   public void grabMouseCursor() {
/* 21 */     Mouse.setGrabbed(true);
/* 22 */     this.deltaX = 0;
/* 23 */     this.deltaY = 0;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void ungrabMouseCursor() {
/* 30 */     Mouse.setCursorPosition(Display.getWidth() / 2, Display.getHeight() / 2);
/* 31 */     Mouse.setGrabbed(false);
/*    */   }
/*    */   
/*    */   public void mouseXYChange() {
/* 35 */     this.deltaX = Mouse.getDX();
/* 36 */     this.deltaY = Mouse.getDY();
/*    */   }
/*    */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\minecraf\\util\MouseHelper.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */