/*    */ package net.minecraft.client.gui;
/*    */ 
/*    */ import net.minecraft.client.Minecraft;
/*    */ import net.minecraft.client.resources.I18n;
/*    */ 
/*    */ public class ServerListEntryLanScan implements GuiListExtended.IGuiListEntry {
/*  7 */   private final Minecraft mc = Minecraft.getMinecraft();
/*    */   public void drawEntry(int slotIndex, int x, int y, int listWidth, int slotHeight, int mouseX, int mouseY, boolean isSelected) {
/*    */     String s;
/* 10 */     int i = y + slotHeight / 2 - this.mc.fontRendererObj.FONT_HEIGHT / 2;
/* 11 */     this.mc.fontRendererObj.drawString(I18n.format("lanServer.scanning", new Object[0]), GuiScreen.width / 2 - this.mc.fontRendererObj.getStringWidth(I18n.format("lanServer.scanning", new Object[0])) / 2, i, 16777215);
/*    */ 
/*    */     
/* 14 */     switch ((int)(Minecraft.getSystemTime() / 300L % 4L)) {
/*    */       
/*    */       default:
/* 17 */         s = "O o o";
/*    */         break;
/*    */       
/*    */       case 1:
/*    */       case 3:
/* 22 */         s = "o O o";
/*    */         break;
/*    */       
/*    */       case 2:
/* 26 */         s = "o o O";
/*    */         break;
/*    */     } 
/* 29 */     this.mc.fontRendererObj.drawString(s, GuiScreen.width / 2 - this.mc.fontRendererObj.getStringWidth(s) / 2, i + this.mc.fontRendererObj.FONT_HEIGHT, 8421504);
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public void setSelected(int p_178011_1_, int p_178011_2_, int p_178011_3_) {}
/*    */ 
/*    */ 
/*    */   
/*    */   public boolean mousePressed(int slotIndex, int p_148278_2_, int p_148278_3_, int p_148278_4_, int p_148278_5_, int p_148278_6_) {
/* 39 */     return false;
/*    */   }
/*    */   
/*    */   public void mouseReleased(int slotIndex, int x, int y, int mouseEvent, int relativeX, int relativeY) {}
/*    */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\minecraft\client\gui\ServerListEntryLanScan.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */