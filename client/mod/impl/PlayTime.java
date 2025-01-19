/*    */ package client.mod.impl;
/*    */ 
/*    */ import client.Client;
/*    */ import client.hud.HudMod;
/*    */ import client.timer.DeltaTimer;
/*    */ 
/*    */ public class PlayTime extends HudMod {
/*    */   public PlayTime() {
/*  9 */     super("PlayTime", 5, 55, false);
/*    */   }
/*    */ 
/*    */   
/*    */   public int getWidth() {
/* 14 */     return fr.getStringWidth("Playing 01 : 00 : 00");
/*    */   }
/*    */ 
/*    */   
/*    */   public void draw() {
/*    */     String seconds, minutes, hours;
/* 20 */     if (DeltaTimer.D.seconds < 10) {
/* 21 */       seconds = "0" + String.valueOf(DeltaTimer.D.seconds);
/*    */     } else {
/* 23 */       seconds = String.valueOf(DeltaTimer.D.seconds);
/*    */     } 
/*    */     
/* 26 */     if (DeltaTimer.D.minutes < 10) {
/* 27 */       minutes = "0" + String.valueOf(DeltaTimer.D.minutes);
/*    */     } else {
/* 29 */       minutes = String.valueOf(DeltaTimer.D.minutes);
/*    */     } 
/*    */     
/* 32 */     if (DeltaTimer.D.hours < 10) {
/* 33 */       hours = "0" + String.valueOf(DeltaTimer.D.hours);
/*    */     } else {
/* 35 */       hours = String.valueOf(DeltaTimer.D.hours);
/*    */     } 
/* 37 */     if (!(Client.getInstance()).isGuiCovered) {
/* 38 */       fr.drawStringWithShadow(String.valueOf((Client.getInstance()).coverColor) + "[" + (Client.getInstance()).mainColor + "PlayTime" + (Client.getInstance()).coverColor + "] : " + (Client.getInstance()).subColor + hours + (Client.getInstance()).coverColor + " : " + (Client.getInstance()).subColor + minutes + (Client.getInstance()).coverColor + " : " + (Client.getInstance()).subColor + seconds, x(), y(), -1);
/*    */     } else {
/* 40 */       fr.drawStringWithShadow(String.valueOf((Client.getInstance()).mainColor) + hours + (Client.getInstance()).subColor + " : " + (Client.getInstance()).mainColor + minutes + (Client.getInstance()).subColor + " : " + (Client.getInstance()).mainColor + seconds, x(), y(), -1);
/*    */     } 
/* 42 */     super.draw();
/*    */   }
/*    */ 
/*    */   
/*    */   public void renderDummy(int mouseX, int mouseY) {
/* 47 */     draw();
/* 48 */     super.renderDummy(mouseX, mouseY);
/*    */   }
/*    */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\client\mod\impl\PlayTime.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */