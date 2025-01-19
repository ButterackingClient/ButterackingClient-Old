/*    */ package net.optifine.gui;
/*    */ 
/*    */ import java.awt.Rectangle;
/*    */ import net.minecraft.client.gui.GuiButton;
/*    */ import net.minecraft.client.gui.GuiScreen;
/*    */ import net.optifine.shaders.config.EnumShaderOption;
/*    */ import net.optifine.shaders.gui.GuiButtonEnumShaderOption;
/*    */ 
/*    */ public class TooltipProviderEnumShaderOptions
/*    */   implements TooltipProvider
/*    */ {
/*    */   public Rectangle getTooltipBounds(GuiScreen guiScreen, int x, int y) {
/* 13 */     int i = GuiScreen.width - 450;
/* 14 */     int j = 35;
/*    */     
/* 16 */     if (i < 10) {
/* 17 */       i = 10;
/*    */     }
/*    */     
/* 20 */     if (y <= j + 94) {
/* 21 */       j += 100;
/*    */     }
/*    */     
/* 24 */     int k = i + 150 + 150;
/* 25 */     int l = j + 84 + 10;
/* 26 */     return new Rectangle(i, j, k - i, l - j);
/*    */   }
/*    */   
/*    */   public boolean isRenderBorder() {
/* 30 */     return true;
/*    */   }
/*    */   
/*    */   public String[] getTooltipLines(GuiButton btn, int width) {
/* 34 */     if (btn instanceof net.optifine.shaders.gui.GuiButtonDownloadShaders)
/* 35 */       return TooltipProviderOptions.getTooltipLines("of.options.shaders.DOWNLOAD"); 
/* 36 */     if (!(btn instanceof GuiButtonEnumShaderOption)) {
/* 37 */       return null;
/*    */     }
/* 39 */     GuiButtonEnumShaderOption guibuttonenumshaderoption = (GuiButtonEnumShaderOption)btn;
/* 40 */     EnumShaderOption enumshaderoption = guibuttonenumshaderoption.getEnumShaderOption();
/* 41 */     String[] astring = getTooltipLines(enumshaderoption);
/* 42 */     return astring;
/*    */   }
/*    */ 
/*    */   
/*    */   private String[] getTooltipLines(EnumShaderOption option) {
/* 47 */     return TooltipProviderOptions.getTooltipLines(option.getResourceKey());
/*    */   }
/*    */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\optifine\gui\TooltipProviderEnumShaderOptions.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */