/*    */ package client.mod.impl;
/*    */ 
/*    */ import client.hud.HudMod;
/*    */ import net.minecraft.client.gui.GuiScreen;
/*    */ import net.minecraft.potion.Potion;
/*    */ import net.minecraft.potion.PotionEffect;
/*    */ 
/*    */ 
/*    */ public class StrengthAim
/*    */   extends HudMod
/*    */ {
/*    */   public StrengthAim() {
/* 13 */     super("StrengthAim", GuiScreen.width / 2, GuiScreen.height / 2 + 6, false);
/*    */   }
/*    */ 
/*    */   
/*    */   public int getWidth() {
/* 18 */     return ClientName.fr.getStringWidth("");
/*    */   }
/*    */ 
/*    */   
/*    */   public void draw() {
/* 23 */     if (!mc.thePlayer.isPotionActive(5)) {
/*    */       return;
/*    */     }
/* 26 */     PotionEffect effect = mc.thePlayer.getActivePotionEffect(Potion.damageBoost);
/* 27 */     if (effect != null && effect.getAmplifier() >= 0) {
/* 28 */       int duration = effect.getDuration() / 2;
/* 29 */       String tt = String.valueOf(duration / 10.0D);
/* 30 */       fr.drawStringWithShadow(tt, (GuiScreen.width / 2 - fr.getStringWidth(tt) / 2), (GuiScreen.height / 2 + 6), 14483456);
/*    */     } 
/* 32 */     super.draw();
/*    */   }
/*    */ 
/*    */   
/*    */   public void renderDummy(int mouseX, int mouseY) {
/* 37 */     fr.drawStringWithShadow("60.0", (GuiScreen.width / 2 - fr.getStringWidth("60.0") / 2), (GuiScreen.height / 2 + 6), 14483456);
/* 38 */     super.renderDummy(mouseX, mouseY);
/*    */   }
/*    */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\client\mod\impl\StrengthAim.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */