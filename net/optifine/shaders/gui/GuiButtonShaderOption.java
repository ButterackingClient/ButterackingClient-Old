/*    */ package net.optifine.shaders.gui;
/*    */ 
/*    */ import net.minecraft.client.gui.GuiButton;
/*    */ import net.optifine.shaders.config.ShaderOption;
/*    */ 
/*    */ public class GuiButtonShaderOption extends GuiButton {
/*  7 */   private ShaderOption shaderOption = null;
/*    */   
/*    */   public GuiButtonShaderOption(int buttonId, int x, int y, int widthIn, int heightIn, ShaderOption shaderOption, String text) {
/* 10 */     super(buttonId, x, y, widthIn, heightIn, text);
/* 11 */     this.shaderOption = shaderOption;
/*    */   }
/*    */   
/*    */   public ShaderOption getShaderOption() {
/* 15 */     return this.shaderOption;
/*    */   }
/*    */ 
/*    */   
/*    */   public void valueChanged() {}
/*    */   
/*    */   public boolean isSwitchable() {
/* 22 */     return true;
/*    */   }
/*    */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\optifine\shaders\gui\GuiButtonShaderOption.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */