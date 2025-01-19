/*    */ package client.mod.impl;
/*    */ 
/*    */ import client.hud.HudMod;
/*    */ 
/*    */ public class AutoGG
/*    */   extends HudMod {
/*    */   public AutoGG() {
/*  8 */     super("Auto GG", 500000, 5000000, false);
/*    */   }
/*    */ 
/*    */   
/*    */   public int getWidth() {
/* 13 */     return ClientName.fr.getStringWidth("");
/*    */   }
/*    */ 
/*    */   
/*    */   public void draw() {
/* 18 */     super.draw();
/*    */   }
/*    */ 
/*    */   
/*    */   public void renderDummy(int mouseX, int mouseY) {
/* 23 */     super.renderDummy(mouseX, mouseY);
/*    */   }
/*    */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\client\mod\impl\AutoGG.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */