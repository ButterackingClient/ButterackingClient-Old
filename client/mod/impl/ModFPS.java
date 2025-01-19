/*    */ package client.mod.impl;
/*    */ 
/*    */ import client.Client;
/*    */ import client.hud.HudMod;
/*    */ import net.minecraft.client.Minecraft;
/*    */ import net.minecraft.client.gui.FontRenderer;
/*    */ 
/*    */ public class ModFPS extends HudMod {
/*    */   public ModFPS() {
/* 10 */     super("Fps", 5, 35, false);
/*    */   }
/*    */ 
/*    */   
/*    */   public int getWidth() {
/* 15 */     FontRenderer fr = ModFPS.fr;
/* 16 */     StringBuilder sb = new StringBuilder("[Fps] : ");
/* 17 */     Minecraft mc = ModFPS.mc;
/* 18 */     return fr.getStringWidth(sb.append(Minecraft.getDebugFPS()).toString());
/*    */   }
/*    */ 
/*    */   
/*    */   public void draw() {
/* 23 */     if (!(Client.getInstance()).isGuiCovered) {
/* 24 */       fr.drawStringWithShadow(String.valueOf((Client.getInstance()).coverColor) + "[" + (Client.getInstance()).mainColor + "Fps" + (Client.getInstance()).coverColor + "] : " + (Client.getInstance()).subColor + Minecraft.getDebugFPS(), x(), y(), -1);
/*    */     } else {
/* 26 */       fr.drawStringWithShadow(String.valueOf((Client.getInstance()).mainColor) + Minecraft.getDebugFPS() + (Client.getInstance()).subColor + " fps", x(), y(), -1);
/*    */     } 
/* 28 */     super.draw();
/*    */   }
/*    */ 
/*    */   
/*    */   public void renderDummy(int mouseX, int mouseY) {
/* 33 */     draw();
/* 34 */     super.renderDummy(mouseX, mouseY);
/*    */   }
/*    */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\client\mod\impl\ModFPS.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */