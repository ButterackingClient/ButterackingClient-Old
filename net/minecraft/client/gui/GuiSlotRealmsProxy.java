/*    */ package net.minecraft.client.gui;
/*    */ 
/*    */ import net.minecraft.client.Minecraft;
/*    */ import net.minecraft.realms.RealmsScrolledSelectionList;
/*    */ 
/*    */ public class GuiSlotRealmsProxy extends GuiSlot {
/*    */   private final RealmsScrolledSelectionList selectionList;
/*    */   
/*    */   public GuiSlotRealmsProxy(RealmsScrolledSelectionList selectionListIn, int widthIn, int heightIn, int topIn, int bottomIn, int slotHeightIn) {
/* 10 */     super(Minecraft.getMinecraft(), widthIn, heightIn, topIn, bottomIn, slotHeightIn);
/* 11 */     this.selectionList = selectionListIn;
/*    */   }
/*    */   
/*    */   protected int getSize() {
/* 15 */     return this.selectionList.getItemCount();
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   protected void elementClicked(int slotIndex, boolean isDoubleClick, int mouseX, int mouseY) {
/* 22 */     this.selectionList.selectItem(slotIndex, isDoubleClick, mouseX, mouseY);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   protected boolean isSelected(int slotIndex) {
/* 29 */     return this.selectionList.isSelectedItem(slotIndex);
/*    */   }
/*    */   
/*    */   protected void drawBackground() {
/* 33 */     this.selectionList.renderBackground();
/*    */   }
/*    */   
/*    */   protected void drawSlot(int entryID, int p_180791_2_, int p_180791_3_, int p_180791_4_, int mouseXIn, int mouseYIn) {
/* 37 */     this.selectionList.renderItem(entryID, p_180791_2_, p_180791_3_, p_180791_4_, mouseXIn, mouseYIn);
/*    */   }
/*    */   
/*    */   public int getWidth() {
/* 41 */     return this.width;
/*    */   }
/*    */   
/*    */   public int getMouseY() {
/* 45 */     return this.mouseY;
/*    */   }
/*    */   
/*    */   public int getMouseX() {
/* 49 */     return this.mouseX;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   protected int getContentHeight() {
/* 56 */     return this.selectionList.getMaxPosition();
/*    */   }
/*    */   
/*    */   protected int getScrollBarX() {
/* 60 */     return this.selectionList.getScrollbarPosition();
/*    */   }
/*    */   
/*    */   public void handleMouseInput() {
/* 64 */     super.handleMouseInput();
/*    */   }
/*    */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\minecraft\client\gui\GuiSlotRealmsProxy.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */