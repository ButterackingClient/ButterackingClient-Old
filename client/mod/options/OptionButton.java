/*    */ package client.mod.options;
/*    */ 
/*    */ import java.awt.Color;
/*    */ import net.minecraft.client.Minecraft;
/*    */ import net.minecraft.client.gui.Gui;
/*    */ import net.minecraft.client.gui.GuiScreen;
/*    */ 
/*    */ 
/*    */ public class OptionButton
/*    */ {
/*    */   public int x;
/*    */   public int y;
/* 13 */   public int w = 8;
/* 14 */   public int h = 8;
/*    */   public GuiScreen m;
/*    */   public boolean warned;
/*    */   public boolean unclickable = false;
/*    */   
/*    */   public OptionButton(int x, int y, GuiScreen m) {
/* 20 */     this.x = x;
/* 21 */     this.y = y;
/* 22 */     this.m = m;
/*    */   }
/*    */   public OptionButton(int x, int y, GuiScreen m, boolean warned) {
/* 25 */     this.x = x;
/* 26 */     this.y = y;
/* 27 */     this.m = m;
/* 28 */     this.warned = warned;
/*    */   }
/*    */   
/*    */   public void draw(int mouseX, int mouseY) {
/* 32 */     drawEnableOption(mouseX, mouseY);
/*    */   }
/*    */   
/*    */   public void drawEnableOption(int mouseX, int mouseY) {
/* 36 */     if (mouseX >= this.x && mouseX <= this.x + this.w && mouseY >= this.y && mouseY <= this.y + this.h && !this.unclickable) {
/* 37 */       Gui.drawRect(this.x + 2, this.y + 2, this.x + this.w + 2, this.y + this.h + 2, (new Color(255, 255, 255)).getRGB());
/*    */     } else {
/* 39 */       Gui.drawRect(this.x + 2, this.y + 2, this.x + this.w + 2, this.y + this.h + 2, (new Color(200, 200, 200)).getRGB());
/*    */     } 
/*    */   }
/*    */   
/*    */   public void onClick(int mouseX, int mouseY, int button) {
/* 44 */     if (mouseX >= this.x && mouseX <= this.x + this.w && mouseY >= this.y && mouseY <= this.y + this.h && !this.unclickable) {
/* 45 */       Minecraft.getMinecraft().displayGuiScreen(this.m);
/*    */     } else {
/*    */       return;
/*    */     } 
/*    */   }
/*    */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\client\mod\options\OptionButton.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */