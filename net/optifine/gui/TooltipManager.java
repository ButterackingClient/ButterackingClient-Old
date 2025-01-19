/*    */ package net.optifine.gui;
/*    */ 
/*    */ import java.awt.Rectangle;
/*    */ import java.util.Arrays;
/*    */ import java.util.List;
/*    */ import net.minecraft.client.Minecraft;
/*    */ import net.minecraft.client.gui.FontRenderer;
/*    */ import net.minecraft.client.gui.Gui;
/*    */ import net.minecraft.client.gui.GuiButton;
/*    */ import net.minecraft.client.gui.GuiScreen;
/*    */ 
/*    */ public class TooltipManager
/*    */ {
/*    */   private GuiScreen guiScreen;
/*    */   private TooltipProvider tooltipProvider;
/* 16 */   private int lastMouseX = 0;
/* 17 */   private int lastMouseY = 0;
/* 18 */   private long mouseStillTime = 0L;
/*    */   
/*    */   public TooltipManager(GuiScreen guiScreen, TooltipProvider tooltipProvider) {
/* 21 */     this.guiScreen = guiScreen;
/* 22 */     this.tooltipProvider = tooltipProvider;
/*    */   }
/*    */   
/*    */   public void drawTooltips(int x, int y, List<GuiButton> buttonList) {
/* 26 */     if (Math.abs(x - this.lastMouseX) <= 5 && Math.abs(y - this.lastMouseY) <= 5) {
/* 27 */       int i = 700;
/*    */       
/* 29 */       if (System.currentTimeMillis() >= this.mouseStillTime + i) {
/* 30 */         GuiButton guibutton = GuiScreenOF.getSelectedButton(x, y, buttonList);
/*    */         
/* 32 */         if (guibutton != null) {
/* 33 */           Rectangle rectangle = this.tooltipProvider.getTooltipBounds(this.guiScreen, x, y);
/* 34 */           String[] astring = this.tooltipProvider.getTooltipLines(guibutton, rectangle.width);
/*    */           
/* 36 */           if (astring != null) {
/* 37 */             if (astring.length > 8) {
/* 38 */               astring = Arrays.<String>copyOf(astring, 8);
/* 39 */               astring[astring.length - 1] = String.valueOf(astring[astring.length - 1]) + " ...";
/*    */             } 
/*    */             
/* 42 */             if (this.tooltipProvider.isRenderBorder()) {
/* 43 */               int j = -528449408;
/* 44 */               drawRectBorder(rectangle.x, rectangle.y, rectangle.x + rectangle.width, rectangle.y + rectangle.height, j);
/*    */             } 
/*    */             
/* 47 */             Gui.drawRect(rectangle.x, rectangle.y, rectangle.x + rectangle.width, rectangle.y + rectangle.height, -536870912);
/*    */             
/* 49 */             for (int l = 0; l < astring.length; l++) {
/* 50 */               String s = astring[l];
/* 51 */               int k = 14540253;
/*    */               
/* 53 */               if (s.endsWith("!")) {
/* 54 */                 k = 16719904;
/*    */               }
/*    */               
/* 57 */               FontRenderer fontrenderer = (Minecraft.getMinecraft()).fontRendererObj;
/* 58 */               fontrenderer.drawStringWithShadow(s, (rectangle.x + 5), (rectangle.y + 5 + l * 11), k);
/*    */             } 
/*    */           } 
/*    */         } 
/*    */       } 
/*    */     } else {
/* 64 */       this.lastMouseX = x;
/* 65 */       this.lastMouseY = y;
/* 66 */       this.mouseStillTime = System.currentTimeMillis();
/*    */     } 
/*    */   }
/*    */   
/*    */   private void drawRectBorder(int x1, int y1, int x2, int y2, int col) {
/* 71 */     Gui.drawRect(x1, y1 - 1, x2, y1, col);
/* 72 */     Gui.drawRect(x1, y2, x2, y2 + 1, col);
/* 73 */     Gui.drawRect(x1 - 1, y1, x1, y2, col);
/* 74 */     Gui.drawRect(x2, y1, x2 + 1, y2, col);
/*    */   }
/*    */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\optifine\gui\TooltipManager.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */