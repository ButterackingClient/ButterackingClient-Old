/*    */ package client.mod.options.impl;
/*    */ 
/*    */ import client.mod.options.BasicOptions;
/*    */ import net.minecraft.client.gui.GuiOptionSlider;
/*    */ import net.minecraft.client.settings.GameSettings;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class ButtonTimeChanger
/*    */   extends BasicOptions
/*    */ {
/*    */   public ButtonTimeChanger() {
/* 14 */     super("Time");
/*    */   }
/*    */ 
/*    */   
/*    */   public void initGui() {
/* 19 */     super.initGui();
/* 20 */     this.buttonList.add(new GuiOptionSlider(10, 50, 100, GameSettings.Options.FAKE_TIME));
/*    */   }
/*    */ 
/*    */   
/*    */   public void drawScreen(int mouseX, int mouseY, float partialTicks) {
/* 25 */     super.drawScreen(mouseX, mouseY, partialTicks);
/*    */   }
/*    */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\client\mod\options\impl\ButtonTimeChanger.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */