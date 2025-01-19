/*    */ package kp.ui;
/*    */ 
/*    */ import kp.input.InputMethod;
/*    */ 
/*    */ 
/*    */ 
/*    */ public class SimpleButton
/*    */ {
/*    */   private int id;
/*    */   private ButtonActionEvent event;
/*    */   public boolean state;
/*    */   public int x;
/*    */   public int y;
/*    */   public int width;
/*    */   public int height;
/*    */   public String text;
/*    */   public boolean useState;
/*    */   public boolean hover;
/*    */   
/* 20 */   private static IButtonRenderer renderer = new IButtonRenderer()
/*    */     {
/*    */       public void render(SimpleButton button) {}
/*    */     };
/*    */ 
/*    */ 
/*    */   
/*    */   public SimpleButton(int id, String text, int x, int y, int w, int h) {
/* 28 */     this.state = true;
/* 29 */     this.useState = false;
/* 30 */     this.hover = false;
/* 31 */     this.id = id;
/* 32 */     this.text = text;
/* 33 */     this.x = x;
/* 34 */     this.y = y;
/* 35 */     this.width = w;
/* 36 */     this.height = h;
/*    */   }
/*    */   
/*    */   public void setActionEvent(ButtonActionEvent e) {
/* 40 */     this.event = e;
/*    */   }
/*    */   
/*    */   public void draw() {
/* 44 */     renderer.render(this);
/*    */   }
/*    */   
/*    */   public void mouseInput(int x, int y, int ev) {
/* 48 */     if (ev == -1) {
/* 49 */       if (isInside(x, y)) {
/* 50 */         this.hover = true;
/*    */       } else {
/*    */         
/* 53 */         this.hover = false;
/*    */       }
/*    */     
/* 56 */     } else if (isInside(x, y) && InputMethod.getMinecraftInterface().getEventButtonState() && this.event != null) {
/* 57 */       this.state = !this.state;
/* 58 */       this.event.onClick(this.id);
/*    */     } 
/*    */   }
/*    */   
/*    */   public boolean isInside(int x, int y) {
/* 63 */     return (x > this.x && x < this.x + this.width && y > this.y && y < this.y + this.height);
/*    */   }
/*    */   
/*    */   public void setPosition(int x, int y) {
/* 67 */     this.x = x;
/* 68 */     this.y = y;
/*    */   }
/*    */   
/*    */   public static void setRenderInterface(IButtonRenderer e) {
/* 72 */     renderer = e;
/*    */   }
/*    */   
/*    */   public static interface ButtonActionEvent {
/*    */     void onClick(int param1Int);
/*    */   }
/*    */   
/*    */   public static interface IButtonRenderer {
/*    */     void render(SimpleButton param1SimpleButton);
/*    */   }
/*    */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\k\\ui\SimpleButton.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */