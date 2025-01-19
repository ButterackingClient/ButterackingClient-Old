/*    */ package net.optifine.gui;
/*    */ 
/*    */ import net.minecraft.client.gui.GuiOptionButton;
/*    */ import net.minecraft.client.settings.GameSettings;
/*    */ 
/*    */ public class GuiOptionButtonOF extends GuiOptionButton implements IOptionControl {
/*  7 */   private GameSettings.Options option = null;
/*    */   
/*    */   public GuiOptionButtonOF(int id, int x, int y, GameSettings.Options option, String text) {
/* 10 */     super(id, x, y, option, text);
/* 11 */     this.option = option;
/*    */   }
/*    */   
/*    */   public GameSettings.Options getOption() {
/* 15 */     return this.option;
/*    */   }
/*    */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\optifine\gui\GuiOptionButtonOF.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */