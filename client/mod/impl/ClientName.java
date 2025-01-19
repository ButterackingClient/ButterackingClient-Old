/*    */ package client.mod.impl;
/*    */ 
/*    */ import client.Client;
/*    */ import client.hud.HudMod;
/*    */ 
/*    */ public class ClientName
/*    */   extends HudMod {
/*    */   public ClientName() {
/*  9 */     super("ClientName", 5, 5, false);
/*    */   }
/*    */ 
/*    */   
/*    */   public int getWidth() {
/* 14 */     return fr.getStringWidth("[" + (Client.getInstance()).clientName + "]");
/*    */   }
/*    */ 
/*    */   
/*    */   public void draw() {
/* 19 */     if (!(Client.getInstance()).isGuiCovered) {
/* 20 */       fr.drawStringWithShadow(String.valueOf((Client.getInstance()).coverColor) + "[" + (Client.getInstance()).mainColor + (Client.getInstance()).clientName + (Client.getInstance()).coverColor + "]", x(), y(), 0);
/*    */     } else {
/* 22 */       fr.drawStringWithShadow(String.valueOf((Client.getInstance()).mainColor) + (Client.getInstance()).clientName, x(), y(), -1);
/*    */     } 
/* 24 */     super.draw();
/*    */   }
/*    */ 
/*    */   
/*    */   public void renderDummy(int mouseX, int mouseY) {
/* 29 */     draw();
/* 30 */     super.renderDummy(mouseX, mouseY);
/*    */   }
/*    */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\client\mod\impl\ClientName.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */