/*    */ package net.optifine.shaders.gui;
/*    */ 
/*    */ import net.minecraft.client.gui.GuiButton;
/*    */ import net.minecraft.client.resources.I18n;
/*    */ import net.optifine.shaders.Shaders;
/*    */ import net.optifine.shaders.config.EnumShaderOption;
/*    */ 
/*    */ public class GuiButtonEnumShaderOption extends GuiButton {
/*  9 */   private EnumShaderOption enumShaderOption = null;
/*    */   
/*    */   public GuiButtonEnumShaderOption(EnumShaderOption enumShaderOption, int x, int y, int widthIn, int heightIn) {
/* 12 */     super(enumShaderOption.ordinal(), x, y, widthIn, heightIn, getButtonText(enumShaderOption));
/* 13 */     this.enumShaderOption = enumShaderOption;
/*    */   }
/*    */   
/*    */   public EnumShaderOption getEnumShaderOption() {
/* 17 */     return this.enumShaderOption;
/*    */   }
/*    */   
/*    */   private static String getButtonText(EnumShaderOption eso) {
/* 21 */     String s = String.valueOf(I18n.format(eso.getResourceKey(), new Object[0])) + ": ";
/*    */     
/* 23 */     switch (eso) {
/*    */       case null:
/* 25 */         return String.valueOf(s) + GuiShaders.toStringAa(Shaders.configAntialiasingLevel);
/*    */       
/*    */       case NORMAL_MAP:
/* 28 */         return String.valueOf(s) + GuiShaders.toStringOnOff(Shaders.configNormalMap);
/*    */       
/*    */       case SPECULAR_MAP:
/* 31 */         return String.valueOf(s) + GuiShaders.toStringOnOff(Shaders.configSpecularMap);
/*    */       
/*    */       case RENDER_RES_MUL:
/* 34 */         return String.valueOf(s) + GuiShaders.toStringQuality(Shaders.configRenderResMul);
/*    */       
/*    */       case SHADOW_RES_MUL:
/* 37 */         return String.valueOf(s) + GuiShaders.toStringQuality(Shaders.configShadowResMul);
/*    */       
/*    */       case HAND_DEPTH_MUL:
/* 40 */         return String.valueOf(s) + GuiShaders.toStringHandDepth(Shaders.configHandDepthMul);
/*    */       
/*    */       case CLOUD_SHADOW:
/* 43 */         return String.valueOf(s) + GuiShaders.toStringOnOff(Shaders.configCloudShadow);
/*    */       
/*    */       case OLD_HAND_LIGHT:
/* 46 */         return String.valueOf(s) + Shaders.configOldHandLight.getUserValue();
/*    */       
/*    */       case OLD_LIGHTING:
/* 49 */         return String.valueOf(s) + Shaders.configOldLighting.getUserValue();
/*    */       
/*    */       case SHADOW_CLIP_FRUSTRUM:
/* 52 */         return String.valueOf(s) + GuiShaders.toStringOnOff(Shaders.configShadowClipFrustrum);
/*    */       
/*    */       case TWEAK_BLOCK_DAMAGE:
/* 55 */         return String.valueOf(s) + GuiShaders.toStringOnOff(Shaders.configTweakBlockDamage);
/*    */     } 
/*    */     
/* 58 */     return String.valueOf(s) + Shaders.getEnumShaderOption(eso);
/*    */   }
/*    */ 
/*    */   
/*    */   public void updateButtonText() {
/* 63 */     this.displayString = getButtonText(this.enumShaderOption);
/*    */   }
/*    */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\optifine\shaders\gui\GuiButtonEnumShaderOption.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */