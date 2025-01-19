/*    */ package net.optifine.gui;
/*    */ 
/*    */ import net.minecraft.client.gui.GuiOptionSlider;
/*    */ import net.minecraft.client.settings.GameSettings;
/*    */ 
/*    */ public class GuiOptionSliderOF extends GuiOptionSlider implements IOptionControl {
/*  7 */   private GameSettings.Options option = null;
/*    */   
/*    */   public GuiOptionSliderOF(int id, int x, int y, GameSettings.Options option) {
/* 10 */     super(id, x, y, option);
/* 11 */     this.option = option;
/*    */   }
/*    */   
/*    */   public GameSettings.Options getOption() {
/* 15 */     return this.option;
/*    */   }
/*    */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\optifine\gui\GuiOptionSliderOF.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */