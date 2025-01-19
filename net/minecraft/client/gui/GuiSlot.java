/*     */ package net.minecraft.client.gui;
/*     */ 
/*     */ import net.minecraft.client.Minecraft;
/*     */ import net.minecraft.client.renderer.GlStateManager;
/*     */ import net.minecraft.client.renderer.Tessellator;
/*     */ import net.minecraft.client.renderer.WorldRenderer;
/*     */ import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
/*     */ import net.minecraft.util.MathHelper;
/*     */ import org.lwjgl.input.Mouse;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public abstract class GuiSlot
/*     */ {
/*     */   protected final Minecraft mc;
/*     */   protected int width;
/*     */   protected int height;
/*     */   protected int top;
/*     */   protected int bottom;
/*     */   protected int right;
/*     */   protected int left;
/*     */   protected final int slotHeight;
/*     */   private int scrollUpButtonID;
/*     */   private int scrollDownButtonID;
/*     */   protected int mouseX;
/*     */   protected int mouseY;
/*     */   protected boolean field_148163_i = true;
/*  49 */   protected int initialClickY = -2;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected float scrollMultiplier;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected float amountScrolled;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  65 */   protected int selectedElement = -1;
/*     */ 
/*     */   
/*     */   protected long lastClicked;
/*     */   
/*     */   protected boolean field_178041_q = true;
/*     */   
/*     */   protected boolean showSelectionBox = true;
/*     */   
/*     */   protected boolean hasListHeader;
/*     */   
/*     */   protected int headerPadding;
/*     */   
/*     */   private boolean enabled = true;
/*     */ 
/*     */   
/*     */   public GuiSlot(Minecraft mcIn, int width, int height, int topIn, int bottomIn, int slotHeightIn) {
/*  82 */     this.mc = mcIn;
/*  83 */     this.width = width;
/*  84 */     this.height = height;
/*  85 */     this.top = topIn;
/*  86 */     this.bottom = bottomIn;
/*  87 */     this.slotHeight = slotHeightIn;
/*  88 */     this.left = 0;
/*  89 */     this.right = width;
/*     */   }
/*     */   
/*     */   public void setDimensions(int widthIn, int heightIn, int topIn, int bottomIn) {
/*  93 */     this.width = widthIn;
/*  94 */     this.height = heightIn;
/*  95 */     this.top = topIn;
/*  96 */     this.bottom = bottomIn;
/*  97 */     this.left = 0;
/*  98 */     this.right = widthIn;
/*     */   }
/*     */   
/*     */   public void setShowSelectionBox(boolean showSelectionBoxIn) {
/* 102 */     this.showSelectionBox = showSelectionBoxIn;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void setHasListHeader(boolean hasListHeaderIn, int headerPaddingIn) {
/* 110 */     this.hasListHeader = hasListHeaderIn;
/* 111 */     this.headerPadding = headerPaddingIn;
/*     */     
/* 113 */     if (!hasListHeaderIn) {
/* 114 */       this.headerPadding = 0;
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected abstract int getSize();
/*     */ 
/*     */ 
/*     */   
/*     */   protected abstract void elementClicked(int paramInt1, boolean paramBoolean, int paramInt2, int paramInt3);
/*     */ 
/*     */ 
/*     */   
/*     */   protected abstract boolean isSelected(int paramInt);
/*     */ 
/*     */ 
/*     */   
/*     */   protected int getContentHeight() {
/* 134 */     return getSize() * this.slotHeight + this.headerPadding;
/*     */   }
/*     */ 
/*     */   
/*     */   protected abstract void drawBackground();
/*     */ 
/*     */   
/*     */   protected void func_178040_a(int p_178040_1_, int p_178040_2_, int p_178040_3_) {}
/*     */ 
/*     */   
/*     */   protected abstract void drawSlot(int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5, int paramInt6);
/*     */ 
/*     */   
/*     */   protected void drawListHeader(int p_148129_1_, int p_148129_2_, Tessellator p_148129_3_) {}
/*     */ 
/*     */   
/*     */   protected void func_148132_a(int p_148132_1_, int p_148132_2_) {}
/*     */ 
/*     */   
/*     */   protected void func_148142_b(int p_148142_1_, int p_148142_2_) {}
/*     */ 
/*     */   
/*     */   public int getSlotIndexFromScreenCoords(int p_148124_1_, int p_148124_2_) {
/* 157 */     int i = this.left + this.width / 2 - getListWidth() / 2;
/* 158 */     int j = this.left + this.width / 2 + getListWidth() / 2;
/* 159 */     int k = p_148124_2_ - this.top - this.headerPadding + (int)this.amountScrolled - 4;
/* 160 */     int l = k / this.slotHeight;
/* 161 */     return (p_148124_1_ < getScrollBarX() && p_148124_1_ >= i && p_148124_1_ <= j && l >= 0 && k >= 0 && l < getSize()) ? l : -1;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void registerScrollButtons(int scrollUpButtonIDIn, int scrollDownButtonIDIn) {
/* 168 */     this.scrollUpButtonID = scrollUpButtonIDIn;
/* 169 */     this.scrollDownButtonID = scrollDownButtonIDIn;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void bindAmountScrolled() {
/* 176 */     this.amountScrolled = MathHelper.clamp_float(this.amountScrolled, 0.0F, func_148135_f());
/*     */   }
/*     */   
/*     */   public int func_148135_f() {
/* 180 */     return Math.max(0, getContentHeight() - this.bottom - this.top - 4);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getAmountScrolled() {
/* 187 */     return (int)this.amountScrolled;
/*     */   }
/*     */   
/*     */   public boolean isMouseYWithinSlotBounds(int p_148141_1_) {
/* 191 */     return (p_148141_1_ >= this.top && p_148141_1_ <= this.bottom && this.mouseX >= this.left && this.mouseX <= this.right);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void scrollBy(int amount) {
/* 198 */     this.amountScrolled += amount;
/* 199 */     bindAmountScrolled();
/* 200 */     this.initialClickY = -2;
/*     */   }
/*     */   
/*     */   public void actionPerformed(GuiButton button) {
/* 204 */     if (button.enabled) {
/* 205 */       if (button.id == this.scrollUpButtonID) {
/* 206 */         this.amountScrolled -= (this.slotHeight * 2 / 3);
/* 207 */         this.initialClickY = -2;
/* 208 */         bindAmountScrolled();
/* 209 */       } else if (button.id == this.scrollDownButtonID) {
/* 210 */         this.amountScrolled += (this.slotHeight * 2 / 3);
/* 211 */         this.initialClickY = -2;
/* 212 */         bindAmountScrolled();
/*     */       } 
/*     */     }
/*     */   }
/*     */   
/*     */   public void drawScreen(int mouseXIn, int mouseYIn, float p_148128_3_) {
/* 218 */     if (this.field_178041_q) {
/* 219 */       this.mouseX = mouseXIn;
/* 220 */       this.mouseY = mouseYIn;
/* 221 */       drawBackground();
/* 222 */       int i = getScrollBarX();
/* 223 */       int j = i + 6;
/* 224 */       bindAmountScrolled();
/* 225 */       GlStateManager.disableLighting();
/* 226 */       GlStateManager.disableFog();
/* 227 */       Tessellator tessellator = Tessellator.getInstance();
/* 228 */       WorldRenderer worldrenderer = tessellator.getWorldRenderer();
/* 229 */       drawContainerBackground(tessellator);
/* 230 */       int k = this.left + this.width / 2 - getListWidth() / 2 + 2;
/* 231 */       int l = this.top + 4 - (int)this.amountScrolled;
/*     */       
/* 233 */       if (this.hasListHeader) {
/* 234 */         drawListHeader(k, l, tessellator);
/*     */       }
/*     */       
/* 237 */       drawSelectionBox(k, l, mouseXIn, mouseYIn);
/* 238 */       GlStateManager.disableDepth();
/* 239 */       int i1 = 4;
/* 240 */       overlayBackground(0, this.top, 255, 255);
/* 241 */       overlayBackground(this.bottom, this.height, 255, 255);
/* 242 */       GlStateManager.enableBlend();
/* 243 */       GlStateManager.tryBlendFuncSeparate(770, 771, 0, 1);
/* 244 */       GlStateManager.disableAlpha();
/* 245 */       GlStateManager.shadeModel(7425);
/* 246 */       GlStateManager.disableTexture2D();
/* 247 */       worldrenderer.begin(7, DefaultVertexFormats.POSITION_TEX_COLOR);
/* 248 */       worldrenderer.pos(this.left, (this.top + i1), 0.0D).tex(0.0D, 1.0D).color(0, 0, 0, 0).endVertex();
/* 249 */       worldrenderer.pos(this.right, (this.top + i1), 0.0D).tex(1.0D, 1.0D).color(0, 0, 0, 0).endVertex();
/* 250 */       worldrenderer.pos(this.right, this.top, 0.0D).tex(1.0D, 0.0D).color(0, 0, 0, 255).endVertex();
/* 251 */       worldrenderer.pos(this.left, this.top, 0.0D).tex(0.0D, 0.0D).color(0, 0, 0, 255).endVertex();
/* 252 */       tessellator.draw();
/* 253 */       worldrenderer.begin(7, DefaultVertexFormats.POSITION_TEX_COLOR);
/* 254 */       worldrenderer.pos(this.left, this.bottom, 0.0D).tex(0.0D, 1.0D).color(0, 0, 0, 255).endVertex();
/* 255 */       worldrenderer.pos(this.right, this.bottom, 0.0D).tex(1.0D, 1.0D).color(0, 0, 0, 255).endVertex();
/* 256 */       worldrenderer.pos(this.right, (this.bottom - i1), 0.0D).tex(1.0D, 0.0D).color(0, 0, 0, 0).endVertex();
/* 257 */       worldrenderer.pos(this.left, (this.bottom - i1), 0.0D).tex(0.0D, 0.0D).color(0, 0, 0, 0).endVertex();
/* 258 */       tessellator.draw();
/* 259 */       int j1 = func_148135_f();
/*     */       
/* 261 */       if (j1 > 0) {
/* 262 */         int k1 = (this.bottom - this.top) * (this.bottom - this.top) / getContentHeight();
/* 263 */         k1 = MathHelper.clamp_int(k1, 32, this.bottom - this.top - 8);
/* 264 */         int l1 = (int)this.amountScrolled * (this.bottom - this.top - k1) / j1 + this.top;
/*     */         
/* 266 */         if (l1 < this.top) {
/* 267 */           l1 = this.top;
/*     */         }
/*     */         
/* 270 */         worldrenderer.begin(7, DefaultVertexFormats.POSITION_TEX_COLOR);
/* 271 */         worldrenderer.pos(i, this.bottom, 0.0D).tex(0.0D, 1.0D).color(0, 0, 0, 255).endVertex();
/* 272 */         worldrenderer.pos(j, this.bottom, 0.0D).tex(1.0D, 1.0D).color(0, 0, 0, 255).endVertex();
/* 273 */         worldrenderer.pos(j, this.top, 0.0D).tex(1.0D, 0.0D).color(0, 0, 0, 255).endVertex();
/* 274 */         worldrenderer.pos(i, this.top, 0.0D).tex(0.0D, 0.0D).color(0, 0, 0, 255).endVertex();
/* 275 */         tessellator.draw();
/* 276 */         worldrenderer.begin(7, DefaultVertexFormats.POSITION_TEX_COLOR);
/* 277 */         worldrenderer.pos(i, (l1 + k1), 0.0D).tex(0.0D, 1.0D).color(128, 128, 128, 255).endVertex();
/* 278 */         worldrenderer.pos(j, (l1 + k1), 0.0D).tex(1.0D, 1.0D).color(128, 128, 128, 255).endVertex();
/* 279 */         worldrenderer.pos(j, l1, 0.0D).tex(1.0D, 0.0D).color(128, 128, 128, 255).endVertex();
/* 280 */         worldrenderer.pos(i, l1, 0.0D).tex(0.0D, 0.0D).color(128, 128, 128, 255).endVertex();
/* 281 */         tessellator.draw();
/* 282 */         worldrenderer.begin(7, DefaultVertexFormats.POSITION_TEX_COLOR);
/* 283 */         worldrenderer.pos(i, (l1 + k1 - 1), 0.0D).tex(0.0D, 1.0D).color(192, 192, 192, 255).endVertex();
/* 284 */         worldrenderer.pos((j - 1), (l1 + k1 - 1), 0.0D).tex(1.0D, 1.0D).color(192, 192, 192, 255).endVertex();
/* 285 */         worldrenderer.pos((j - 1), l1, 0.0D).tex(1.0D, 0.0D).color(192, 192, 192, 255).endVertex();
/* 286 */         worldrenderer.pos(i, l1, 0.0D).tex(0.0D, 0.0D).color(192, 192, 192, 255).endVertex();
/* 287 */         tessellator.draw();
/*     */       } 
/*     */       
/* 290 */       func_148142_b(mouseXIn, mouseYIn);
/* 291 */       GlStateManager.enableTexture2D();
/* 292 */       GlStateManager.shadeModel(7424);
/* 293 */       GlStateManager.enableAlpha();
/* 294 */       GlStateManager.disableBlend();
/*     */     } 
/*     */   }
/*     */   
/*     */   public void handleMouseInput() {
/* 299 */     if (isMouseYWithinSlotBounds(this.mouseY)) {
/* 300 */       if (Mouse.getEventButton() == 0 && Mouse.getEventButtonState() && this.mouseY >= this.top && this.mouseY <= this.bottom) {
/* 301 */         int i = (this.width - getListWidth()) / 2;
/* 302 */         int j = (this.width + getListWidth()) / 2;
/* 303 */         int k = this.mouseY - this.top - this.headerPadding + (int)this.amountScrolled - 4;
/* 304 */         int l = k / this.slotHeight;
/*     */         
/* 306 */         if (l < getSize() && this.mouseX >= i && this.mouseX <= j && l >= 0 && k >= 0) {
/* 307 */           elementClicked(l, false, this.mouseX, this.mouseY);
/* 308 */           this.selectedElement = l;
/* 309 */         } else if (this.mouseX >= i && this.mouseX <= j && k < 0) {
/* 310 */           func_148132_a(this.mouseX - i, this.mouseY - this.top + (int)this.amountScrolled - 4);
/*     */         } 
/*     */       } 
/*     */       
/* 314 */       if (Mouse.isButtonDown(0) && getEnabled()) {
/* 315 */         if (this.initialClickY != -1) {
/* 316 */           if (this.initialClickY >= 0) {
/* 317 */             this.amountScrolled -= (this.mouseY - this.initialClickY) * this.scrollMultiplier;
/* 318 */             this.initialClickY = this.mouseY;
/*     */           } 
/*     */         } else {
/* 321 */           boolean flag1 = true;
/*     */           
/* 323 */           if (this.mouseY >= this.top && this.mouseY <= this.bottom) {
/* 324 */             int j2 = (this.width - getListWidth()) / 2;
/* 325 */             int k2 = (this.width + getListWidth()) / 2;
/* 326 */             int l2 = this.mouseY - this.top - this.headerPadding + (int)this.amountScrolled - 4;
/* 327 */             int i1 = l2 / this.slotHeight;
/*     */             
/* 329 */             if (i1 < getSize() && this.mouseX >= j2 && this.mouseX <= k2 && i1 >= 0 && l2 >= 0) {
/* 330 */               boolean flag = (i1 == this.selectedElement && Minecraft.getSystemTime() - this.lastClicked < 250L);
/* 331 */               elementClicked(i1, flag, this.mouseX, this.mouseY);
/* 332 */               this.selectedElement = i1;
/* 333 */               this.lastClicked = Minecraft.getSystemTime();
/* 334 */             } else if (this.mouseX >= j2 && this.mouseX <= k2 && l2 < 0) {
/* 335 */               func_148132_a(this.mouseX - j2, this.mouseY - this.top + (int)this.amountScrolled - 4);
/* 336 */               flag1 = false;
/*     */             } 
/*     */             
/* 339 */             int i3 = getScrollBarX();
/* 340 */             int j1 = i3 + 6;
/*     */             
/* 342 */             if (this.mouseX >= i3 && this.mouseX <= j1) {
/* 343 */               this.scrollMultiplier = -1.0F;
/* 344 */               int k1 = func_148135_f();
/*     */               
/* 346 */               if (k1 < 1) {
/* 347 */                 k1 = 1;
/*     */               }
/*     */               
/* 350 */               int l1 = (int)(((this.bottom - this.top) * (this.bottom - this.top)) / getContentHeight());
/* 351 */               l1 = MathHelper.clamp_int(l1, 32, this.bottom - this.top - 8);
/* 352 */               this.scrollMultiplier /= (this.bottom - this.top - l1) / k1;
/*     */             } else {
/* 354 */               this.scrollMultiplier = 1.0F;
/*     */             } 
/*     */             
/* 357 */             if (flag1) {
/* 358 */               this.initialClickY = this.mouseY;
/*     */             } else {
/* 360 */               this.initialClickY = -2;
/*     */             } 
/*     */           } else {
/* 363 */             this.initialClickY = -2;
/*     */           } 
/*     */         } 
/*     */       } else {
/* 367 */         this.initialClickY = -1;
/*     */       } 
/*     */       
/* 370 */       int i2 = Mouse.getEventDWheel();
/*     */       
/* 372 */       if (i2 != 0) {
/* 373 */         if (i2 > 0) {
/* 374 */           i2 = -1;
/* 375 */         } else if (i2 < 0) {
/* 376 */           i2 = 1;
/*     */         } 
/*     */         
/* 379 */         this.amountScrolled += (i2 * this.slotHeight / 2);
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   public void setEnabled(boolean enabledIn) {
/* 385 */     this.enabled = enabledIn;
/*     */   }
/*     */   
/*     */   public boolean getEnabled() {
/* 389 */     return this.enabled;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getListWidth() {
/* 396 */     return 220;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void drawSelectionBox(int p_148120_1_, int p_148120_2_, int mouseXIn, int mouseYIn) {
/* 403 */     int i = getSize();
/* 404 */     Tessellator tessellator = Tessellator.getInstance();
/* 405 */     WorldRenderer worldrenderer = tessellator.getWorldRenderer();
/*     */     
/* 407 */     for (int j = 0; j < i; j++) {
/* 408 */       int k = p_148120_2_ + j * this.slotHeight + this.headerPadding;
/* 409 */       int l = this.slotHeight - 4;
/*     */       
/* 411 */       if (k > this.bottom || k + l < this.top) {
/* 412 */         func_178040_a(j, p_148120_1_, k);
/*     */       }
/*     */       
/* 415 */       if (this.showSelectionBox && isSelected(j)) {
/* 416 */         int i1 = this.left + this.width / 2 - getListWidth() / 2;
/* 417 */         int j1 = this.left + this.width / 2 + getListWidth() / 2;
/* 418 */         GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
/* 419 */         GlStateManager.disableTexture2D();
/* 420 */         worldrenderer.begin(7, DefaultVertexFormats.POSITION_TEX_COLOR);
/* 421 */         worldrenderer.pos(i1, (k + l + 2), 0.0D).tex(0.0D, 1.0D).color(128, 128, 128, 255).endVertex();
/* 422 */         worldrenderer.pos(j1, (k + l + 2), 0.0D).tex(1.0D, 1.0D).color(128, 128, 128, 255).endVertex();
/* 423 */         worldrenderer.pos(j1, (k - 2), 0.0D).tex(1.0D, 0.0D).color(128, 128, 128, 255).endVertex();
/* 424 */         worldrenderer.pos(i1, (k - 2), 0.0D).tex(0.0D, 0.0D).color(128, 128, 128, 255).endVertex();
/* 425 */         worldrenderer.pos((i1 + 1), (k + l + 1), 0.0D).tex(0.0D, 1.0D).color(0, 0, 0, 255).endVertex();
/* 426 */         worldrenderer.pos((j1 - 1), (k + l + 1), 0.0D).tex(1.0D, 1.0D).color(0, 0, 0, 255).endVertex();
/* 427 */         worldrenderer.pos((j1 - 1), (k - 1), 0.0D).tex(1.0D, 0.0D).color(0, 0, 0, 255).endVertex();
/* 428 */         worldrenderer.pos((i1 + 1), (k - 1), 0.0D).tex(0.0D, 0.0D).color(0, 0, 0, 255).endVertex();
/* 429 */         tessellator.draw();
/* 430 */         GlStateManager.enableTexture2D();
/*     */       } 
/*     */       
/* 433 */       if (!(this instanceof GuiResourcePackList) || (k >= this.top - this.slotHeight && k <= this.bottom)) {
/* 434 */         drawSlot(j, p_148120_1_, k, l, mouseXIn, mouseYIn);
/*     */       }
/*     */     } 
/*     */   }
/*     */   
/*     */   protected int getScrollBarX() {
/* 440 */     return this.width / 2 + 124;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void overlayBackground(int startY, int endY, int startAlpha, int endAlpha) {
/* 447 */     Tessellator tessellator = Tessellator.getInstance();
/* 448 */     WorldRenderer worldrenderer = tessellator.getWorldRenderer();
/* 449 */     this.mc.getTextureManager().bindTexture(Gui.optionsBackground);
/* 450 */     GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
/* 451 */     float f = 32.0F;
/* 452 */     worldrenderer.begin(7, DefaultVertexFormats.POSITION_TEX_COLOR);
/* 453 */     worldrenderer.pos(this.left, endY, 0.0D).tex(0.0D, (endY / 32.0F)).color(64, 64, 64, endAlpha).endVertex();
/* 454 */     worldrenderer.pos((this.left + this.width), endY, 0.0D).tex((this.width / 32.0F), (endY / 32.0F)).color(64, 64, 64, endAlpha).endVertex();
/* 455 */     worldrenderer.pos((this.left + this.width), startY, 0.0D).tex((this.width / 32.0F), (startY / 32.0F)).color(64, 64, 64, startAlpha).endVertex();
/* 456 */     worldrenderer.pos(this.left, startY, 0.0D).tex(0.0D, (startY / 32.0F)).color(64, 64, 64, startAlpha).endVertex();
/* 457 */     tessellator.draw();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setSlotXBoundsFromLeft(int leftIn) {
/* 464 */     this.left = leftIn;
/* 465 */     this.right = leftIn + this.width;
/*     */   }
/*     */   
/*     */   public int getSlotHeight() {
/* 469 */     return this.slotHeight;
/*     */   }
/*     */   
/*     */   protected void drawContainerBackground(Tessellator p_drawContainerBackground_1_) {
/* 473 */     WorldRenderer worldrenderer = p_drawContainerBackground_1_.getWorldRenderer();
/* 474 */     this.mc.getTextureManager().bindTexture(Gui.optionsBackground);
/* 475 */     GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
/* 476 */     float f = 32.0F;
/* 477 */     worldrenderer.begin(7, DefaultVertexFormats.POSITION_TEX_COLOR);
/* 478 */     worldrenderer.pos(this.left, this.bottom, 0.0D).tex((this.left / f), ((this.bottom + (int)this.amountScrolled) / f)).color(32, 32, 32, 255).endVertex();
/* 479 */     worldrenderer.pos(this.right, this.bottom, 0.0D).tex((this.right / f), ((this.bottom + (int)this.amountScrolled) / f)).color(32, 32, 32, 255).endVertex();
/* 480 */     worldrenderer.pos(this.right, this.top, 0.0D).tex((this.right / f), ((this.top + (int)this.amountScrolled) / f)).color(32, 32, 32, 255).endVertex();
/* 481 */     worldrenderer.pos(this.left, this.top, 0.0D).tex((this.left / f), ((this.top + (int)this.amountScrolled) / f)).color(32, 32, 32, 255).endVertex();
/* 482 */     p_drawContainerBackground_1_.draw();
/*     */   }
/*     */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\minecraft\client\gui\GuiSlot.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */