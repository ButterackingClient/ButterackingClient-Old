/*    */ package client.gui.click;
/*    */ 
/*    */ import client.hud.HudMod;
/*    */ import java.awt.Color;
/*    */ import net.minecraft.client.Minecraft;
/*    */ import net.minecraft.client.gui.Gui;
/*    */ 
/*    */ 
/*    */ public class Modbutton
/*    */ {
/*    */   public int x;
/*    */   public int y;
/*    */   public int w;
/*    */   public int h;
/*    */   public HudMod m;
/*    */   public boolean warned;
/*    */   public boolean unclickable;
/*    */   
/*    */   public Modbutton(int x, int y, int w, int h, HudMod m) {
/* 20 */     this.x = x;
/* 21 */     this.y = y;
/* 22 */     this.w = w;
/* 23 */     this.h = h;
/* 24 */     this.m = m;
/*    */   }
/*    */   public Modbutton(int x, int y, HudMod m, boolean unclickable) {
/* 27 */     this.x = x;
/* 28 */     this.y = y;
/* 29 */     this.m = m;
/* 30 */     this.unclickable = unclickable;
/*    */   }
/*    */   public Modbutton(int x, int y, int w, int h, HudMod m, boolean warned) {
/* 33 */     this.x = x;
/* 34 */     this.y = y;
/* 35 */     this.w = w;
/* 36 */     this.h = h;
/* 37 */     this.m = m;
/* 38 */     this.warned = warned;
/*    */   }
/*    */   
/*    */   public void draw(int mouseX, int mouseY) {
/* 42 */     if (mouseX >= this.x && mouseX <= this.x + this.w && mouseY >= this.y && mouseY <= this.y + this.h && !this.unclickable) {
/* 43 */       Gui.drawRect(this.x, this.y, this.x + this.w, this.y + this.h, (new Color(100, 100, 100)).getRGB());
/*    */     }
/* 45 */     (Minecraft.getMinecraft()).fontRendererObj.drawStringWithShadow(this.m.name, (this.x + 2), (this.y + 2), getColor());
/*    */   }
/*    */   
/*    */   public int getColor() {
/* 49 */     if (this.m.isEnabled() && !this.unclickable) {
/* 50 */       if (this.warned) {
/* 51 */         return (new Color(200, 200, 0, 255)).getRGB();
/*    */       }
/* 53 */       return (new Color(0, 255, 0, 255)).getRGB();
/*    */     } 
/* 55 */     if (this.unclickable) {
/* 56 */       return (new Color(255, 255, 255, 255)).getRGB();
/*    */     }
/* 58 */     return (new Color(230, 0, 0, 255)).getRGB();
/*    */   }
/*    */ 
/*    */   
/*    */   public void onClick(int mouseX, int mouseY, int button) {
/* 63 */     if (mouseX >= this.x && mouseX <= this.x + this.w && mouseY >= this.y && mouseY <= this.y + this.h && !this.unclickable) {
/* 64 */       if (this.m.isEnabled()) {
/* 65 */         this.m.setEnabled(false);
/*    */       } else {
/* 67 */         this.m.setEnabled(true);
/*    */       } 
/*    */     } else {
/*    */       return;
/*    */     } 
/*    */   }
/*    */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\client\gui\click\Modbutton.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */