/*    */ package client.mod.impl;
/*    */ 
/*    */ import client.Client;
/*    */ import client.hud.HudMod;
/*    */ 
/*    */ public class ToggleSprintHud extends HudMod {
/*    */   public ToggleSprintHud() {
/*  8 */     super("ToggleSprint", 5, 85, false);
/*    */   }
/*    */ 
/*    */   
/*    */   public int getWidth() {
/* 13 */     return fr.getStringWidth("[Sprinting (toggled)]");
/*    */   }
/*    */ 
/*    */   
/*    */   public void draw() {
/* 18 */     if ((Client.getInstance()).modManager.toggleSprint.isToggled()) {
/* 19 */       if (!(Client.getInstance()).isGuiCovered) {
/* 20 */         fr.drawStringWithShadow(String.valueOf((Client.getInstance()).coverColor) + "[" + (Client.getInstance()).mainColor + "Sprinting" + (Client.getInstance()).subColor + " (toggled)" + (Client.getInstance()).coverColor + "]", x(), y(), -1);
/*    */       } else {
/* 22 */         fr.drawStringWithShadow(String.valueOf((Client.getInstance()).subColor) + (Client.getInstance()).mainColor + "Sprinting (toggled)" + (Client.getInstance()).subColor, x(), y(), -1);
/*    */       } 
/*    */     }
/* 25 */     super.draw();
/*    */   }
/*    */ 
/*    */   
/*    */   public void renderDummy(int mouseX, int mouseY) {
/* 30 */     if (!(Client.getInstance()).isGuiCovered) {
/* 31 */       fr.drawStringWithShadow(String.valueOf((Client.getInstance()).coverColor) + "[" + (Client.getInstance()).mainColor + "Sprinting" + (Client.getInstance()).subColor + " (toggled)" + (Client.getInstance()).coverColor + "]", x(), y(), -1);
/*    */     } else {
/* 33 */       fr.drawStringWithShadow(String.valueOf((Client.getInstance()).subColor) + (Client.getInstance()).mainColor + "Sprinting (toggled)" + (Client.getInstance()).subColor, x(), y(), -1);
/*    */     } 
/* 35 */     super.renderDummy(mouseX, mouseY);
/*    */   }
/*    */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\client\mod\impl\ToggleSprintHud.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */