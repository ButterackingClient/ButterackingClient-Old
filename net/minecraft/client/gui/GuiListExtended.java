/*    */ package net.minecraft.client.gui;
/*    */ 
/*    */ import net.minecraft.client.Minecraft;
/*    */ 
/*    */ public abstract class GuiListExtended extends GuiSlot {
/*    */   public GuiListExtended(Minecraft mcIn, int widthIn, int heightIn, int topIn, int bottomIn, int slotHeightIn) {
/*  7 */     super(mcIn, widthIn, heightIn, topIn, bottomIn, slotHeightIn);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   protected void elementClicked(int slotIndex, boolean isDoubleClick, int mouseX, int mouseY) {}
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   protected boolean isSelected(int slotIndex) {
/* 20 */     return false;
/*    */   }
/*    */ 
/*    */   
/*    */   protected void drawBackground() {}
/*    */   
/*    */   protected void drawSlot(int entryID, int p_180791_2_, int p_180791_3_, int p_180791_4_, int mouseXIn, int mouseYIn) {
/* 27 */     getListEntry(entryID).drawEntry(entryID, p_180791_2_, p_180791_3_, getListWidth(), p_180791_4_, mouseXIn, mouseYIn, (getSlotIndexFromScreenCoords(mouseXIn, mouseYIn) == entryID));
/*    */   }
/*    */   
/*    */   protected void func_178040_a(int p_178040_1_, int p_178040_2_, int p_178040_3_) {
/* 31 */     getListEntry(p_178040_1_).setSelected(p_178040_1_, p_178040_2_, p_178040_3_);
/*    */   }
/*    */   
/*    */   public boolean mouseClicked(int mouseX, int mouseY, int mouseEvent) {
/* 35 */     if (isMouseYWithinSlotBounds(mouseY)) {
/* 36 */       int i = getSlotIndexFromScreenCoords(mouseX, mouseY);
/*    */       
/* 38 */       if (i >= 0) {
/* 39 */         int j = this.left + this.width / 2 - getListWidth() / 2 + 2;
/* 40 */         int k = this.top + 4 - getAmountScrolled() + i * this.slotHeight + this.headerPadding;
/* 41 */         int l = mouseX - j;
/* 42 */         int i1 = mouseY - k;
/*    */         
/* 44 */         if (getListEntry(i).mousePressed(i, mouseX, mouseY, mouseEvent, l, i1)) {
/* 45 */           setEnabled(false);
/* 46 */           return true;
/*    */         } 
/*    */       } 
/*    */     } 
/*    */     
/* 51 */     return false;
/*    */   }
/*    */   
/*    */   public boolean mouseReleased(int p_148181_1_, int p_148181_2_, int p_148181_3_) {
/* 55 */     for (int i = 0; i < getSize(); i++) {
/* 56 */       int j = this.left + this.width / 2 - getListWidth() / 2 + 2;
/* 57 */       int k = this.top + 4 - getAmountScrolled() + i * this.slotHeight + this.headerPadding;
/* 58 */       int l = p_148181_1_ - j;
/* 59 */       int i1 = p_148181_2_ - k;
/* 60 */       getListEntry(i).mouseReleased(i, p_148181_1_, p_148181_2_, p_148181_3_, l, i1);
/*    */     } 
/*    */     
/* 63 */     setEnabled(true);
/* 64 */     return false;
/*    */   }
/*    */   
/*    */   public abstract IGuiListEntry getListEntry(int paramInt);
/*    */   
/*    */   public static interface IGuiListEntry {
/*    */     void setSelected(int param1Int1, int param1Int2, int param1Int3);
/*    */     
/*    */     void drawEntry(int param1Int1, int param1Int2, int param1Int3, int param1Int4, int param1Int5, int param1Int6, int param1Int7, boolean param1Boolean);
/*    */     
/*    */     boolean mousePressed(int param1Int1, int param1Int2, int param1Int3, int param1Int4, int param1Int5, int param1Int6);
/*    */     
/*    */     void mouseReleased(int param1Int1, int param1Int2, int param1Int3, int param1Int4, int param1Int5, int param1Int6);
/*    */   }
/*    */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\minecraft\client\gui\GuiListExtended.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */