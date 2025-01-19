/*    */ package client.mod.options;
/*    */ 
/*    */ import java.awt.Color;
/*    */ import net.minecraft.client.gui.Gui;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class OptionGuiButton
/*    */ {
/*    */   public int x;
/*    */   public int y;
/* 13 */   public int w = 8;
/* 14 */   public int h = 8;
/*    */   public boolean warned;
/*    */   public boolean unclickable = false;
/*    */   public static boolean enabled;
/*    */   
/*    */   public OptionGuiButton(int x, int y) {
/* 20 */     this.x = x;
/* 21 */     this.y = y;
/*    */   }
/*    */   public OptionGuiButton(int x, int y, boolean warned) {
/* 24 */     this.x = x;
/* 25 */     this.y = y;
/* 26 */     this.warned = warned;
/*    */   }
/*    */   
/*    */   public void draw(int mouseX, int mouseY) {
/* 30 */     if (mouseX >= this.x && mouseX <= this.x + this.w && mouseY >= this.y && mouseY <= this.y + this.h && !this.unclickable) {
/* 31 */       Gui.drawRect(this.x + 2, this.y + 2, this.x + this.w + 2, this.y + this.h + 2, (new Color(255, 255, 255)).getRGB());
/* 32 */     } else if (enabled) {
/* 33 */       Gui.drawRect(this.x + 2, this.y + 2, this.x + this.w + 2, this.y + this.h + 2, (new Color(0, 230, 10)).getRGB());
/*    */     } else {
/* 35 */       Gui.drawRect(this.x + 2, this.y + 2, this.x + this.w + 2, this.y + this.h + 2, (new Color(230, 0, 0)).getRGB());
/*    */     } 
/*    */   }
/*    */   
/*    */   public void onClick(int mouseX, int mouseY) {
/* 40 */     if (mouseX >= this.x + 2 && mouseX <= this.x + this.w + 2 && mouseY >= this.y + 2 && mouseY <= this.y + this.h + 2) {
/* 41 */       enabled = !enabled;
/*    */     } else {
/*    */       return;
/*    */     } 
/*    */   }
/*    */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\client\mod\options\OptionGuiButton.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */