/*     */ package net.minecraft.client.gui;
/*     */ 
/*     */ import net.minecraft.client.Minecraft;
/*     */ import net.minecraft.client.renderer.GlStateManager;
/*     */ import net.minecraft.client.renderer.Tessellator;
/*     */ import net.minecraft.client.renderer.WorldRenderer;
/*     */ import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
/*     */ import net.minecraft.realms.RealmsSimpleScrolledSelectionList;
/*     */ import net.minecraft.util.MathHelper;
/*     */ 
/*     */ public class GuiSimpleScrolledSelectionListProxy extends GuiSlot {
/*     */   private final RealmsSimpleScrolledSelectionList field_178050_u;
/*     */   
/*     */   public GuiSimpleScrolledSelectionListProxy(RealmsSimpleScrolledSelectionList p_i45525_1_, int widthIn, int heightIn, int topIn, int bottomIn, int slotHeightIn) {
/*  15 */     super(Minecraft.getMinecraft(), widthIn, heightIn, topIn, bottomIn, slotHeightIn);
/*  16 */     this.field_178050_u = p_i45525_1_;
/*     */   }
/*     */   
/*     */   protected int getSize() {
/*  20 */     return this.field_178050_u.getItemCount();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void elementClicked(int slotIndex, boolean isDoubleClick, int mouseX, int mouseY) {
/*  27 */     this.field_178050_u.selectItem(slotIndex, isDoubleClick, mouseX, mouseY);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected boolean isSelected(int slotIndex) {
/*  34 */     return this.field_178050_u.isSelectedItem(slotIndex);
/*     */   }
/*     */   
/*     */   protected void drawBackground() {
/*  38 */     this.field_178050_u.renderBackground();
/*     */   }
/*     */   
/*     */   protected void drawSlot(int entryID, int p_180791_2_, int p_180791_3_, int p_180791_4_, int mouseXIn, int mouseYIn) {
/*  42 */     this.field_178050_u.renderItem(entryID, p_180791_2_, p_180791_3_, p_180791_4_, mouseXIn, mouseYIn);
/*     */   }
/*     */   
/*     */   public int getWidth() {
/*  46 */     return this.width;
/*     */   }
/*     */   
/*     */   public int getMouseY() {
/*  50 */     return this.mouseY;
/*     */   }
/*     */   
/*     */   public int getMouseX() {
/*  54 */     return this.mouseX;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected int getContentHeight() {
/*  61 */     return this.field_178050_u.getMaxPosition();
/*     */   }
/*     */   
/*     */   protected int getScrollBarX() {
/*  65 */     return this.field_178050_u.getScrollbarPosition();
/*     */   }
/*     */   
/*     */   public void handleMouseInput() {
/*  69 */     super.handleMouseInput();
/*     */   }
/*     */   
/*     */   public void drawScreen(int mouseXIn, int mouseYIn, float p_148128_3_) {
/*  73 */     if (this.field_178041_q) {
/*  74 */       this.mouseX = mouseXIn;
/*  75 */       this.mouseY = mouseYIn;
/*  76 */       drawBackground();
/*  77 */       int i = getScrollBarX();
/*  78 */       int j = i + 6;
/*  79 */       bindAmountScrolled();
/*  80 */       GlStateManager.disableLighting();
/*  81 */       GlStateManager.disableFog();
/*  82 */       Tessellator tessellator = Tessellator.getInstance();
/*  83 */       WorldRenderer worldrenderer = tessellator.getWorldRenderer();
/*  84 */       int k = this.left + this.width / 2 - getListWidth() / 2 + 2;
/*  85 */       int l = this.top + 4 - (int)this.amountScrolled;
/*     */       
/*  87 */       if (this.hasListHeader) {
/*  88 */         drawListHeader(k, l, tessellator);
/*     */       }
/*     */       
/*  91 */       drawSelectionBox(k, l, mouseXIn, mouseYIn);
/*  92 */       GlStateManager.disableDepth();
/*  93 */       int i1 = 4;
/*  94 */       overlayBackground(0, this.top, 255, 255);
/*  95 */       overlayBackground(this.bottom, this.height, 255, 255);
/*  96 */       GlStateManager.enableBlend();
/*  97 */       GlStateManager.tryBlendFuncSeparate(770, 771, 0, 1);
/*  98 */       GlStateManager.disableAlpha();
/*  99 */       GlStateManager.shadeModel(7425);
/* 100 */       GlStateManager.disableTexture2D();
/* 101 */       int j1 = func_148135_f();
/*     */       
/* 103 */       if (j1 > 0) {
/* 104 */         int k1 = (this.bottom - this.top) * (this.bottom - this.top) / getContentHeight();
/* 105 */         k1 = MathHelper.clamp_int(k1, 32, this.bottom - this.top - 8);
/* 106 */         int l1 = (int)this.amountScrolled * (this.bottom - this.top - k1) / j1 + this.top;
/*     */         
/* 108 */         if (l1 < this.top) {
/* 109 */           l1 = this.top;
/*     */         }
/*     */         
/* 112 */         worldrenderer.begin(7, DefaultVertexFormats.POSITION_TEX_COLOR);
/* 113 */         worldrenderer.pos(i, this.bottom, 0.0D).tex(0.0D, 1.0D).color(0, 0, 0, 255).endVertex();
/* 114 */         worldrenderer.pos(j, this.bottom, 0.0D).tex(1.0D, 1.0D).color(0, 0, 0, 255).endVertex();
/* 115 */         worldrenderer.pos(j, this.top, 0.0D).tex(1.0D, 0.0D).color(0, 0, 0, 255).endVertex();
/* 116 */         worldrenderer.pos(i, this.top, 0.0D).tex(0.0D, 0.0D).color(0, 0, 0, 255).endVertex();
/* 117 */         tessellator.draw();
/* 118 */         worldrenderer.begin(7, DefaultVertexFormats.POSITION_TEX_COLOR);
/* 119 */         worldrenderer.pos(i, (l1 + k1), 0.0D).tex(0.0D, 1.0D).color(128, 128, 128, 255).endVertex();
/* 120 */         worldrenderer.pos(j, (l1 + k1), 0.0D).tex(1.0D, 1.0D).color(128, 128, 128, 255).endVertex();
/* 121 */         worldrenderer.pos(j, l1, 0.0D).tex(1.0D, 0.0D).color(128, 128, 128, 255).endVertex();
/* 122 */         worldrenderer.pos(i, l1, 0.0D).tex(0.0D, 0.0D).color(128, 128, 128, 255).endVertex();
/* 123 */         tessellator.draw();
/* 124 */         worldrenderer.begin(7, DefaultVertexFormats.POSITION_TEX_COLOR);
/* 125 */         worldrenderer.pos(i, (l1 + k1 - 1), 0.0D).tex(0.0D, 1.0D).color(192, 192, 192, 255).endVertex();
/* 126 */         worldrenderer.pos((j - 1), (l1 + k1 - 1), 0.0D).tex(1.0D, 1.0D).color(192, 192, 192, 255).endVertex();
/* 127 */         worldrenderer.pos((j - 1), l1, 0.0D).tex(1.0D, 0.0D).color(192, 192, 192, 255).endVertex();
/* 128 */         worldrenderer.pos(i, l1, 0.0D).tex(0.0D, 0.0D).color(192, 192, 192, 255).endVertex();
/* 129 */         tessellator.draw();
/*     */       } 
/*     */       
/* 132 */       func_148142_b(mouseXIn, mouseYIn);
/* 133 */       GlStateManager.enableTexture2D();
/* 134 */       GlStateManager.shadeModel(7424);
/* 135 */       GlStateManager.enableAlpha();
/* 136 */       GlStateManager.disableBlend();
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\minecraft\client\gui\GuiSimpleScrolledSelectionListProxy.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */