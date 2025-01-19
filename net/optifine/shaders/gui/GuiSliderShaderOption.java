/*    */ package net.optifine.shaders.gui;
/*    */ 
/*    */ import net.minecraft.client.Minecraft;
/*    */ import net.minecraft.client.gui.GuiScreen;
/*    */ import net.minecraft.client.renderer.GlStateManager;
/*    */ import net.minecraft.util.MathHelper;
/*    */ import net.optifine.shaders.config.ShaderOption;
/*    */ 
/*    */ public class GuiSliderShaderOption extends GuiButtonShaderOption {
/* 10 */   private float sliderValue = 1.0F;
/*    */   public boolean dragging;
/* 12 */   private ShaderOption shaderOption = null;
/*    */   
/*    */   public GuiSliderShaderOption(int buttonId, int x, int y, int w, int h, ShaderOption shaderOption, String text) {
/* 15 */     super(buttonId, x, y, w, h, shaderOption, text);
/* 16 */     this.shaderOption = shaderOption;
/* 17 */     this.sliderValue = shaderOption.getIndexNormalized();
/* 18 */     this.displayString = GuiShaderOptions.getButtonText(shaderOption, this.width);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   protected int getHoverState(boolean mouseOver) {
/* 26 */     return 0;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   protected void mouseDragged(Minecraft mc, int mouseX, int mouseY) {
/* 33 */     if (this.visible) {
/* 34 */       if (this.dragging && !GuiScreen.isShiftKeyDown()) {
/* 35 */         this.sliderValue = (mouseX - this.xPosition + 4) / (this.width - 8);
/* 36 */         this.sliderValue = MathHelper.clamp_float(this.sliderValue, 0.0F, 1.0F);
/* 37 */         this.shaderOption.setIndexNormalized(this.sliderValue);
/* 38 */         this.sliderValue = this.shaderOption.getIndexNormalized();
/* 39 */         this.displayString = GuiShaderOptions.getButtonText(this.shaderOption, this.width);
/*    */       } 
/*    */       
/* 42 */       mc.getTextureManager().bindTexture(buttonTextures);
/* 43 */       GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
/* 44 */       drawTexturedModalRect(this.xPosition + (int)(this.sliderValue * (this.width - 8)), this.yPosition, 0, 66, 4, 20);
/* 45 */       drawTexturedModalRect(this.xPosition + (int)(this.sliderValue * (this.width - 8)) + 4, this.yPosition, 196, 66, 4, 20);
/*    */     } 
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public boolean mousePressed(Minecraft mc, int mouseX, int mouseY) {
/* 54 */     if (super.mousePressed(mc, mouseX, mouseY)) {
/* 55 */       this.sliderValue = (mouseX - this.xPosition + 4) / (this.width - 8);
/* 56 */       this.sliderValue = MathHelper.clamp_float(this.sliderValue, 0.0F, 1.0F);
/* 57 */       this.shaderOption.setIndexNormalized(this.sliderValue);
/* 58 */       this.displayString = GuiShaderOptions.getButtonText(this.shaderOption, this.width);
/* 59 */       this.dragging = true;
/* 60 */       return true;
/*    */     } 
/* 62 */     return false;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void mouseReleased(int mouseX, int mouseY) {
/* 70 */     this.dragging = false;
/*    */   }
/*    */   
/*    */   public void valueChanged() {
/* 74 */     this.sliderValue = this.shaderOption.getIndexNormalized();
/*    */   }
/*    */   
/*    */   public boolean isSwitchable() {
/* 78 */     return false;
/*    */   }
/*    */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\optifine\shaders\gui\GuiSliderShaderOption.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */