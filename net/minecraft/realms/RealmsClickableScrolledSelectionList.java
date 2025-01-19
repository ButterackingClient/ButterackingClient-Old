/*    */ package net.minecraft.realms;
/*    */ 
/*    */ import net.minecraft.client.gui.GuiClickableScrolledSelectionListProxy;
/*    */ 
/*    */ public class RealmsClickableScrolledSelectionList {
/*    */   private final GuiClickableScrolledSelectionListProxy proxy;
/*    */   
/*    */   public RealmsClickableScrolledSelectionList(int p_i46052_1_, int p_i46052_2_, int p_i46052_3_, int p_i46052_4_, int p_i46052_5_) {
/*  9 */     this.proxy = new GuiClickableScrolledSelectionListProxy(this, p_i46052_1_, p_i46052_2_, p_i46052_3_, p_i46052_4_, p_i46052_5_);
/*    */   }
/*    */   
/*    */   public void render(int p_render_1_, int p_render_2_, float p_render_3_) {
/* 13 */     this.proxy.drawScreen(p_render_1_, p_render_2_, p_render_3_);
/*    */   }
/*    */   
/*    */   public int width() {
/* 17 */     return this.proxy.func_178044_e();
/*    */   }
/*    */   
/*    */   public int ym() {
/* 21 */     return this.proxy.func_178042_f();
/*    */   }
/*    */   
/*    */   public int xm() {
/* 25 */     return this.proxy.func_178045_g();
/*    */   }
/*    */ 
/*    */   
/*    */   protected void renderItem(int p_renderItem_1_, int p_renderItem_2_, int p_renderItem_3_, int p_renderItem_4_, Tezzelator p_renderItem_5_, int p_renderItem_6_, int p_renderItem_7_) {}
/*    */   
/*    */   public void renderItem(int p_renderItem_1_, int p_renderItem_2_, int p_renderItem_3_, int p_renderItem_4_, int p_renderItem_5_, int p_renderItem_6_) {
/* 32 */     renderItem(p_renderItem_1_, p_renderItem_2_, p_renderItem_3_, p_renderItem_4_, Tezzelator.instance, p_renderItem_5_, p_renderItem_6_);
/*    */   }
/*    */   
/*    */   public int getItemCount() {
/* 36 */     return 0;
/*    */   }
/*    */ 
/*    */   
/*    */   public void selectItem(int p_selectItem_1_, boolean p_selectItem_2_, int p_selectItem_3_, int p_selectItem_4_) {}
/*    */   
/*    */   public boolean isSelectedItem(int p_isSelectedItem_1_) {
/* 43 */     return false;
/*    */   }
/*    */ 
/*    */   
/*    */   public void renderBackground() {}
/*    */   
/*    */   public int getMaxPosition() {
/* 50 */     return 0;
/*    */   }
/*    */   
/*    */   public int getScrollbarPosition() {
/* 54 */     return this.proxy.func_178044_e() / 2 + 124;
/*    */   }
/*    */   
/*    */   public void mouseEvent() {
/* 58 */     this.proxy.handleMouseInput();
/*    */   }
/*    */ 
/*    */   
/*    */   public void customMouseEvent(int p_customMouseEvent_1_, int p_customMouseEvent_2_, int p_customMouseEvent_3_, float p_customMouseEvent_4_, int p_customMouseEvent_5_) {}
/*    */   
/*    */   public void scroll(int p_scroll_1_) {
/* 65 */     this.proxy.scrollBy(p_scroll_1_);
/*    */   }
/*    */   
/*    */   public int getScroll() {
/* 69 */     return this.proxy.getAmountScrolled();
/*    */   }
/*    */ 
/*    */   
/*    */   protected void renderList(int p_renderList_1_, int p_renderList_2_, int p_renderList_3_, int p_renderList_4_) {}
/*    */ 
/*    */   
/*    */   public void itemClicked(int p_itemClicked_1_, int p_itemClicked_2_, int p_itemClicked_3_, int p_itemClicked_4_, int p_itemClicked_5_) {}
/*    */ 
/*    */   
/*    */   public void renderSelected(int p_renderSelected_1_, int p_renderSelected_2_, int p_renderSelected_3_, Tezzelator p_renderSelected_4_) {}
/*    */   
/*    */   public void setLeftPos(int p_setLeftPos_1_) {
/* 82 */     this.proxy.setSlotXBoundsFromLeft(p_setLeftPos_1_);
/*    */   }
/*    */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\minecraft\realms\RealmsClickableScrolledSelectionList.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */