/*    */ package client.mod.impl;
/*    */ import client.hud.HudMod;
/*    */ import client.mod.options.BasicOptions;
/*    */ import client.mod.options.impl.ButtonTimeChanger;
/*    */ 
/*    */ public class TimeChanger extends HudMod {
/*    */   public TimeChanger() {
/*  8 */     super("Time Changer", 500000, 5000000, false, (BasicOptions)new ButtonTimeChanger());
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


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\client\mod\impl\TimeChanger.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */