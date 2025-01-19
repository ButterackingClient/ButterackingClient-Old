/*    */ package client.mod.impl;
/*    */ 
/*    */ import client.Client;
/*    */ import client.hud.HudMod;
/*    */ import net.minecraft.client.Minecraft;
/*    */ 
/*    */ public class PingMod
/*    */   extends HudMod {
/*    */   public PingMod() {
/* 10 */     super("Ping", 5, 65, false);
/*    */   }
/*    */ 
/*    */   
/*    */   public int getWidth() {
/* 15 */     return fr.getStringWidth("10 ms");
/*    */   }
/*    */ 
/*    */   
/*    */   public int getHeight() {
/* 20 */     return fr.FONT_HEIGHT;
/*    */   }
/*    */ 
/*    */   
/*    */   public void draw() {
/* 25 */     if (mc.getNetHandler() != null && (Minecraft.getMinecraft()).thePlayer != null && mc.getNetHandler().getPlayerInfo((Minecraft.getMinecraft()).thePlayer.getUniqueID()) != null) {
/* 26 */       if (!(Client.getInstance()).isGuiCovered) {
/* 27 */         fr.drawStringWithShadow(String.valueOf((Client.getInstance()).coverColor) + "[" + (Client.getInstance()).mainColor + "Ping" + (Client.getInstance()).coverColor + "] : " + (Client.getInstance()).subColor + Minecraft.getMinecraft().getNetHandler().getPlayerInfo((Minecraft.getMinecraft()).thePlayer.getUniqueID()).getResponseTime(), x(), y(), -1);
/*    */       } else {
/* 29 */         fr.drawStringWithShadow(String.valueOf((Client.getInstance()).mainColor) + Minecraft.getMinecraft().getNetHandler().getPlayerInfo((Minecraft.getMinecraft()).thePlayer.getUniqueID()).getResponseTime() + (Client.getInstance()).subColor + " ms", x(), y(), -1);
/*    */       } 
/*    */     }
/* 32 */     super.draw();
/*    */   }
/*    */ 
/*    */   
/*    */   public void renderDummy(int mouseX, int mouseY) {
/* 37 */     draw();
/* 38 */     super.renderDummy(mouseX, mouseY);
/*    */   }
/*    */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\client\mod\impl\PingMod.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */