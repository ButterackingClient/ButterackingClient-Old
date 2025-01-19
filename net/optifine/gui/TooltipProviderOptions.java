/*    */ package net.optifine.gui;
/*    */ 
/*    */ import java.awt.Rectangle;
/*    */ import java.util.ArrayList;
/*    */ import java.util.List;
/*    */ import net.minecraft.client.gui.GuiButton;
/*    */ import net.minecraft.client.gui.GuiScreen;
/*    */ import net.minecraft.client.settings.GameSettings;
/*    */ import net.optifine.Lang;
/*    */ 
/*    */ public class TooltipProviderOptions
/*    */   implements TooltipProvider {
/*    */   public Rectangle getTooltipBounds(GuiScreen guiScreen, int x, int y) {
/* 14 */     int i = GuiScreen.width / 2 - 150;
/* 15 */     int j = GuiScreen.height / 6 - 7;
/*    */     
/* 17 */     if (y <= j + 98) {
/* 18 */       j += 105;
/*    */     }
/*    */     
/* 21 */     int k = i + 150 + 150;
/* 22 */     int l = j + 84 + 10;
/* 23 */     return new Rectangle(i, j, k - i, l - j);
/*    */   }
/*    */   
/*    */   public boolean isRenderBorder() {
/* 27 */     return false;
/*    */   }
/*    */   
/*    */   public String[] getTooltipLines(GuiButton btn, int width) {
/* 31 */     if (!(btn instanceof IOptionControl)) {
/* 32 */       return null;
/*    */     }
/* 34 */     IOptionControl ioptioncontrol = (IOptionControl)btn;
/* 35 */     GameSettings.Options gamesettings$options = ioptioncontrol.getOption();
/* 36 */     String[] astring = getTooltipLines(gamesettings$options.getEnumString());
/* 37 */     return astring;
/*    */   }
/*    */ 
/*    */   
/*    */   public static String[] getTooltipLines(String key) {
/* 42 */     List<String> list = new ArrayList<>();
/*    */     
/* 44 */     for (int i = 0; i < 10; i++) {
/* 45 */       String s = String.valueOf(key) + ".tooltip." + (i + 1);
/* 46 */       String s1 = Lang.get(s, null);
/*    */       
/* 48 */       if (s1 == null) {
/*    */         break;
/*    */       }
/*    */       
/* 52 */       list.add(s1);
/*    */     } 
/*    */     
/* 55 */     if (list.size() <= 0) {
/* 56 */       return null;
/*    */     }
/* 58 */     String[] astring = list.<String>toArray(new String[list.size()]);
/* 59 */     return astring;
/*    */   }
/*    */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\optifine\gui\TooltipProviderOptions.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */