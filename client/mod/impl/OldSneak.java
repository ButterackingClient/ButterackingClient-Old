/*    */ package client.mod.impl;
/*    */ 
/*    */ import client.hud.HudMod;
/*    */ 
/*    */ public class OldSneak extends HudMod {
/*    */   public OldSneak() {
/*  7 */     super("1.7 Sneaking", 500000, 5000000, false);
/*    */   }
/*    */ 
/*    */   
/*    */   public int getWidth() {
/* 12 */     return ClientName.fr.getStringWidth("");
/*    */   }
/*    */ 
/*    */   
/*    */   public void draw() {
/* 17 */     super.draw();
/*    */   }
/*    */ 
/*    */   
/*    */   public void renderDummy(int mouseX, int mouseY) {
/* 22 */     super.renderDummy(mouseX, mouseY);
/*    */   }
/*    */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\client\mod\impl\OldSneak.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */