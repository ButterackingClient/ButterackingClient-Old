/*    */ package client.mod.impl;
/*    */ 
/*    */ import client.Client;
/*    */ import client.hud.HudMod;
/*    */ import net.minecraft.src.Config;
/*    */ 
/*    */ public class PackDisplay
/*    */   extends HudMod
/*    */ {
/*    */   public PackDisplay() {
/* 11 */     super("PackDisplay", 5, 75, false);
/*    */   }
/*    */ 
/*    */   
/*    */   public void draw() {
/* 16 */     String pack = "";
/* 17 */     if (!Config.getResourcePackNames().equalsIgnoreCase("Default")) {
/* 18 */       pack = Config.getResourcePackNames().split(",")[(Config.getResourcePacks()).length - 1];
/*    */     } else {
/* 20 */       pack = "Default";
/*    */     } 
/* 22 */     if (!(Client.getInstance()).isGuiCovered) {
/* 23 */       if (!Config.getResourcePackNames().equals("Default")) {
/* 24 */         fr.drawStringWithShadow(String.valueOf((Client.getInstance()).coverColor) + "[" + (Client.getInstance()).mainColor + "Pack" + (Client.getInstance()).coverColor + "] : " + (Client.getInstance()).white + pack, x(), y(), -1);
/*    */       } else {
/*    */         
/* 27 */         fr.drawStringWithShadow(String.valueOf((Client.getInstance()).coverColor) + "[" + (Client.getInstance()).mainColor + "Pack" + (Client.getInstance()).coverColor + "] : " + (Client.getInstance()).white + "Default", x(), y(), -1);
/*    */       }
/*    */     
/* 30 */     } else if (!Config.getResourcePackNames().equals("Default")) {
/* 31 */       fr.drawStringWithShadow(String.valueOf((Client.getInstance()).white) + pack, x(), y(), -1);
/*    */     } else {
/* 33 */       fr.drawStringWithShadow("Default", x(), y(), -1);
/*    */     } 
/*    */     
/* 36 */     super.draw();
/*    */   }
/*    */ 
/*    */   
/*    */   public void renderDummy(int mouseX, int mouseY) {
/* 41 */     draw();
/* 42 */     super.renderDummy(mouseX, mouseY);
/*    */   }
/*    */ 
/*    */   
/*    */   public int getWidth() {
/* 47 */     return fr.getStringWidth("[Pack] : Default");
/*    */   }
/*    */ 
/*    */   
/*    */   public int getHeight() {
/* 52 */     return fr.FONT_HEIGHT;
/*    */   }
/*    */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\client\mod\impl\PackDisplay.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */