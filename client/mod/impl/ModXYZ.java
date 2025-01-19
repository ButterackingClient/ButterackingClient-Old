/*    */ package client.mod.impl;
/*    */ 
/*    */ import client.Client;
/*    */ import client.hud.HudMod;
/*    */ 
/*    */ public class ModXYZ extends HudMod {
/*    */   public ModXYZ() {
/*  8 */     super("XYZ", 5, 25, false);
/*    */   }
/*    */ 
/*    */   
/*    */   public int getWidth() {
/* 13 */     return fr.getStringWidth("[XYZ] : 100 / 100 / 100");
/*    */   }
/*    */ 
/*    */   
/*    */   public void draw() {
/* 18 */     fr.drawStringWithShadow(getXYZString(), x(), y(), -1);
/* 19 */     super.draw();
/*    */   }
/*    */   
/*    */   private String getXYZString() {
/* 23 */     if (!(Client.getInstance()).isGuiCovered) {
/* 24 */       return String.format(String.valueOf((Client.getInstance()).coverColor) + "[" + (Client.getInstance()).mainColor + "XYZ" + (Client.getInstance()).coverColor + "] : " + (Client.getInstance()).subColor + "%.0f " + (Client.getInstance()).coverColor + "/" + (Client.getInstance()).subColor + " %.0f " + (Client.getInstance()).coverColor + "/" + (Client.getInstance()).subColor + " %.0f", new Object[] { Double.valueOf((mc.getRenderViewEntity()).posX), Double.valueOf((mc.getRenderViewEntity().getEntityBoundingBox()).minY), Double.valueOf((mc.getRenderViewEntity()).posZ) });
/*    */     }
/* 26 */     return String.format(String.valueOf((Client.getInstance()).mainColor) + "%.0f " + (Client.getInstance()).subColor + "/" + (Client.getInstance()).mainColor + " %.0f " + (Client.getInstance()).subColor + "/" + (Client.getInstance()).mainColor + " %.0f", new Object[] { Double.valueOf((mc.getRenderViewEntity()).posX), Double.valueOf((mc.getRenderViewEntity().getEntityBoundingBox()).minY), Double.valueOf((mc.getRenderViewEntity()).posZ) });
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public void renderDummy(int mouseX, int mouseY) {
/* 32 */     fr.drawStringWithShadow(getXYZString(), x(), y(), -1);
/* 33 */     super.renderDummy(mouseX, mouseY);
/*    */   }
/*    */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\client\mod\impl\ModXYZ.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */