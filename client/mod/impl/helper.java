/*    */ package client.mod.impl;
/*    */ 
/*    */ import client.hud.HudMod;
/*    */ import net.minecraft.client.gui.GuiScreen;
/*    */ 
/*    */ public class helper
/*    */   extends HudMod
/*    */ {
/*    */   public helper() {
/* 10 */     super("helper", GuiScreen.width / 2, GuiScreen.height / 2, true);
/*    */   }
/*    */ 
/*    */   
/*    */   public int getWidth() {
/* 15 */     return ClientName.fr.getStringWidth("");
/*    */   }
/*    */ 
/*    */   
/*    */   public void draw() {
/* 20 */     fr.drawStringWithShadow(String.valueOf(""), x(), y(), -1);
/* 21 */     super.draw();
/*    */   }
/*    */ 
/*    */   
/*    */   public void renderDummy(int mouseX, int mouseY) {
/* 26 */     super.renderDummy(mouseX, mouseY);
/*    */   }
/*    */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\client\mod\impl\helper.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */