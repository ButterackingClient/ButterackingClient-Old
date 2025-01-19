/*     */ package net.minecraft.client.gui.inventory;
/*     */ 
/*     */ import com.google.common.collect.Sets;
/*     */ import java.io.IOException;
/*     */ import java.util.Set;
/*     */ import net.minecraft.client.Minecraft;
/*     */ import net.minecraft.client.gui.GuiScreen;
/*     */ import net.minecraft.client.renderer.GlStateManager;
/*     */ import net.minecraft.client.renderer.OpenGlHelper;
/*     */ import net.minecraft.client.renderer.RenderHelper;
/*     */ import net.minecraft.client.renderer.texture.TextureAtlasSprite;
/*     */ import net.minecraft.client.renderer.texture.TextureMap;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.entity.player.InventoryPlayer;
/*     */ import net.minecraft.inventory.Container;
/*     */ import net.minecraft.inventory.Slot;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraft.util.EnumChatFormatting;
/*     */ import net.minecraft.util.MathHelper;
/*     */ import net.minecraft.util.ResourceLocation;
/*     */ import org.lwjgl.input.Keyboard;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public abstract class GuiContainer
/*     */   extends GuiScreen
/*     */ {
/*  30 */   protected static final ResourceLocation inventoryBackground = new ResourceLocation("textures/gui/container/inventory.png");
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  35 */   protected int xSize = 176;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  40 */   protected int ySize = 166;
/*     */ 
/*     */ 
/*     */   
/*     */   public Container inventorySlots;
/*     */ 
/*     */ 
/*     */   
/*     */   protected int guiLeft;
/*     */ 
/*     */   
/*     */   protected int guiTop;
/*     */ 
/*     */   
/*     */   private Slot theSlot;
/*     */ 
/*     */   
/*     */   private Slot clickedSlot;
/*     */ 
/*     */   
/*     */   private boolean isRightMouseClick;
/*     */ 
/*     */   
/*     */   private ItemStack draggedStack;
/*     */ 
/*     */   
/*     */   private int touchUpX;
/*     */ 
/*     */   
/*     */   private int touchUpY;
/*     */ 
/*     */   
/*     */   private Slot returningStackDestSlot;
/*     */ 
/*     */   
/*     */   private long returningStackTime;
/*     */ 
/*     */   
/*     */   private ItemStack returningStack;
/*     */ 
/*     */   
/*     */   private Slot currentDragTargetSlot;
/*     */ 
/*     */   
/*     */   private long dragItemDropDelay;
/*     */ 
/*     */   
/*  87 */   protected final Set<Slot> dragSplittingSlots = Sets.newHashSet();
/*     */   protected boolean dragSplitting;
/*     */   private int dragSplittingLimit;
/*     */   private int dragSplittingButton;
/*     */   private boolean ignoreMouseUp;
/*     */   private int dragSplittingRemnant;
/*     */   private long lastClickTime;
/*     */   private Slot lastClickSlot;
/*     */   private int lastClickButton;
/*     */   private boolean doubleClick;
/*     */   private ItemStack shiftClickedSlot;
/*     */   
/*     */   public GuiContainer(Container inventorySlotsIn) {
/* 100 */     this.inventorySlots = inventorySlotsIn;
/* 101 */     this.ignoreMouseUp = true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void initGui() {
/* 109 */     super.initGui();
/* 110 */     this.mc.thePlayer.openContainer = this.inventorySlots;
/* 111 */     this.guiLeft = (width - this.xSize) / 2;
/* 112 */     this.guiTop = (height - this.ySize) / 2;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void drawScreen(int mouseX, int mouseY, float partialTicks) {
/* 119 */     drawDefaultBackground();
/* 120 */     int i = this.guiLeft;
/* 121 */     int j = this.guiTop;
/* 122 */     drawGuiContainerBackgroundLayer(partialTicks, mouseX, mouseY);
/* 123 */     GlStateManager.disableRescaleNormal();
/* 124 */     RenderHelper.disableStandardItemLighting();
/* 125 */     GlStateManager.disableLighting();
/* 126 */     GlStateManager.disableDepth();
/* 127 */     super.drawScreen(mouseX, mouseY, partialTicks);
/* 128 */     RenderHelper.enableGUIStandardItemLighting();
/* 129 */     GlStateManager.pushMatrix();
/* 130 */     GlStateManager.translate(i, j, 0.0F);
/* 131 */     GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
/* 132 */     GlStateManager.enableRescaleNormal();
/* 133 */     this.theSlot = null;
/* 134 */     int k = 240;
/* 135 */     int l = 240;
/* 136 */     OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, k / 1.0F, l / 1.0F);
/* 137 */     GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
/*     */     
/* 139 */     for (int i1 = 0; i1 < this.inventorySlots.inventorySlots.size(); i1++) {
/* 140 */       Slot slot = this.inventorySlots.inventorySlots.get(i1);
/* 141 */       drawSlot(slot);
/*     */       
/* 143 */       if (isMouseOverSlot(slot, mouseX, mouseY) && slot.canBeHovered()) {
/* 144 */         this.theSlot = slot;
/* 145 */         GlStateManager.disableLighting();
/* 146 */         GlStateManager.disableDepth();
/* 147 */         int j1 = slot.xDisplayPosition;
/* 148 */         int k1 = slot.yDisplayPosition;
/* 149 */         GlStateManager.colorMask(true, true, true, false);
/* 150 */         drawGradientRect(j1, k1, j1 + 16, k1 + 16, -2130706433, -2130706433);
/* 151 */         GlStateManager.colorMask(true, true, true, true);
/* 152 */         GlStateManager.enableLighting();
/* 153 */         GlStateManager.enableDepth();
/*     */       } 
/*     */     } 
/*     */     
/* 157 */     RenderHelper.disableStandardItemLighting();
/* 158 */     drawGuiContainerForegroundLayer(mouseX, mouseY);
/* 159 */     RenderHelper.enableGUIStandardItemLighting();
/* 160 */     InventoryPlayer inventoryplayer = this.mc.thePlayer.inventory;
/* 161 */     ItemStack itemstack = (this.draggedStack == null) ? inventoryplayer.getItemStack() : this.draggedStack;
/*     */     
/* 163 */     if (itemstack != null) {
/* 164 */       int j2 = 8;
/* 165 */       int k2 = (this.draggedStack == null) ? 8 : 16;
/* 166 */       String s = null;
/*     */       
/* 168 */       if (this.draggedStack != null && this.isRightMouseClick) {
/* 169 */         itemstack = itemstack.copy();
/* 170 */         itemstack.stackSize = MathHelper.ceiling_float_int(itemstack.stackSize / 2.0F);
/* 171 */       } else if (this.dragSplitting && this.dragSplittingSlots.size() > 1) {
/* 172 */         itemstack = itemstack.copy();
/* 173 */         itemstack.stackSize = this.dragSplittingRemnant;
/*     */         
/* 175 */         if (itemstack.stackSize == 0) {
/* 176 */           s = EnumChatFormatting.YELLOW + "0";
/*     */         }
/*     */       } 
/*     */       
/* 180 */       drawItemStack(itemstack, mouseX - i - j2, mouseY - j - k2, s);
/*     */     } 
/*     */     
/* 183 */     if (this.returningStack != null) {
/* 184 */       float f = (float)(Minecraft.getSystemTime() - this.returningStackTime) / 100.0F;
/*     */       
/* 186 */       if (f >= 1.0F) {
/* 187 */         f = 1.0F;
/* 188 */         this.returningStack = null;
/*     */       } 
/*     */       
/* 191 */       int l2 = this.returningStackDestSlot.xDisplayPosition - this.touchUpX;
/* 192 */       int i3 = this.returningStackDestSlot.yDisplayPosition - this.touchUpY;
/* 193 */       int l1 = this.touchUpX + (int)(l2 * f);
/* 194 */       int i2 = this.touchUpY + (int)(i3 * f);
/* 195 */       drawItemStack(this.returningStack, l1, i2, (String)null);
/*     */     } 
/*     */     
/* 198 */     GlStateManager.popMatrix();
/*     */     
/* 200 */     if (inventoryplayer.getItemStack() == null && this.theSlot != null && this.theSlot.getHasStack()) {
/* 201 */       ItemStack itemstack1 = this.theSlot.getStack();
/* 202 */       renderToolTip(itemstack1, mouseX, mouseY);
/*     */     } 
/*     */     
/* 205 */     GlStateManager.enableLighting();
/* 206 */     GlStateManager.enableDepth();
/* 207 */     RenderHelper.enableStandardItemLighting();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void drawItemStack(ItemStack stack, int x, int y, String altText) {
/* 214 */     GlStateManager.translate(0.0F, 0.0F, 32.0F);
/* 215 */     this.zLevel = 200.0F;
/* 216 */     this.itemRender.zLevel = 200.0F;
/* 217 */     this.itemRender.renderItemAndEffectIntoGUI(stack, x, y);
/* 218 */     this.itemRender.renderItemOverlayIntoGUI(this.fontRendererObj, stack, x, y - ((this.draggedStack == null) ? 0 : 8), altText);
/* 219 */     this.zLevel = 0.0F;
/* 220 */     this.itemRender.zLevel = 0.0F;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY) {}
/*     */ 
/*     */ 
/*     */   
/*     */   protected abstract void drawGuiContainerBackgroundLayer(float paramFloat, int paramInt1, int paramInt2);
/*     */ 
/*     */ 
/*     */   
/*     */   private void drawSlot(Slot slotIn) {
/* 235 */     int i = slotIn.xDisplayPosition;
/* 236 */     int j = slotIn.yDisplayPosition;
/* 237 */     ItemStack itemstack = slotIn.getStack();
/* 238 */     boolean flag = false;
/* 239 */     boolean flag1 = (slotIn == this.clickedSlot && this.draggedStack != null && !this.isRightMouseClick);
/* 240 */     ItemStack itemstack1 = this.mc.thePlayer.inventory.getItemStack();
/* 241 */     String s = null;
/*     */     
/* 243 */     if (slotIn == this.clickedSlot && this.draggedStack != null && this.isRightMouseClick && itemstack != null) {
/* 244 */       itemstack = itemstack.copy();
/* 245 */       itemstack.stackSize /= 2;
/* 246 */     } else if (this.dragSplitting && this.dragSplittingSlots.contains(slotIn) && itemstack1 != null) {
/* 247 */       if (this.dragSplittingSlots.size() == 1) {
/*     */         return;
/*     */       }
/*     */       
/* 251 */       if (Container.canAddItemToSlot(slotIn, itemstack1, true) && this.inventorySlots.canDragIntoSlot(slotIn)) {
/* 252 */         itemstack = itemstack1.copy();
/* 253 */         flag = true;
/* 254 */         Container.computeStackSize(this.dragSplittingSlots, this.dragSplittingLimit, itemstack, (slotIn.getStack() == null) ? 0 : (slotIn.getStack()).stackSize);
/*     */         
/* 256 */         if (itemstack.stackSize > itemstack.getMaxStackSize()) {
/* 257 */           s = EnumChatFormatting.YELLOW + itemstack.getMaxStackSize();
/* 258 */           itemstack.stackSize = itemstack.getMaxStackSize();
/*     */         } 
/*     */         
/* 261 */         if (itemstack.stackSize > slotIn.getItemStackLimit(itemstack)) {
/* 262 */           s = EnumChatFormatting.YELLOW + slotIn.getItemStackLimit(itemstack);
/* 263 */           itemstack.stackSize = slotIn.getItemStackLimit(itemstack);
/*     */         } 
/*     */       } else {
/* 266 */         this.dragSplittingSlots.remove(slotIn);
/* 267 */         updateDragSplitting();
/*     */       } 
/*     */     } 
/*     */     
/* 271 */     this.zLevel = 100.0F;
/* 272 */     this.itemRender.zLevel = 100.0F;
/*     */     
/* 274 */     if (itemstack == null) {
/* 275 */       String s1 = slotIn.getSlotTexture();
/*     */       
/* 277 */       if (s1 != null) {
/* 278 */         TextureAtlasSprite textureatlassprite = this.mc.getTextureMapBlocks().getAtlasSprite(s1);
/* 279 */         GlStateManager.disableLighting();
/* 280 */         this.mc.getTextureManager().bindTexture(TextureMap.locationBlocksTexture);
/* 281 */         drawTexturedModalRect(i, j, textureatlassprite, 16, 16);
/* 282 */         GlStateManager.enableLighting();
/* 283 */         flag1 = true;
/*     */       } 
/*     */     } 
/*     */     
/* 287 */     if (!flag1) {
/* 288 */       if (flag) {
/* 289 */         drawRect(i, j, i + 16, j + 16, -2130706433);
/*     */       }
/*     */       
/* 292 */       GlStateManager.enableDepth();
/* 293 */       this.itemRender.renderItemAndEffectIntoGUI(itemstack, i, j);
/* 294 */       this.itemRender.renderItemOverlayIntoGUI(this.fontRendererObj, itemstack, i, j, s);
/*     */     } 
/*     */     
/* 297 */     this.itemRender.zLevel = 0.0F;
/* 298 */     this.zLevel = 0.0F;
/*     */   }
/*     */   
/*     */   private void updateDragSplitting() {
/* 302 */     ItemStack itemstack = this.mc.thePlayer.inventory.getItemStack();
/*     */     
/* 304 */     if (itemstack != null && this.dragSplitting) {
/* 305 */       this.dragSplittingRemnant = itemstack.stackSize;
/*     */       
/* 307 */       for (Slot slot : this.dragSplittingSlots) {
/* 308 */         ItemStack itemstack1 = itemstack.copy();
/* 309 */         int i = (slot.getStack() == null) ? 0 : (slot.getStack()).stackSize;
/* 310 */         Container.computeStackSize(this.dragSplittingSlots, this.dragSplittingLimit, itemstack1, i);
/*     */         
/* 312 */         if (itemstack1.stackSize > itemstack1.getMaxStackSize()) {
/* 313 */           itemstack1.stackSize = itemstack1.getMaxStackSize();
/*     */         }
/*     */         
/* 316 */         if (itemstack1.stackSize > slot.getItemStackLimit(itemstack1)) {
/* 317 */           itemstack1.stackSize = slot.getItemStackLimit(itemstack1);
/*     */         }
/*     */         
/* 320 */         this.dragSplittingRemnant -= itemstack1.stackSize - i;
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private Slot getSlotAtPosition(int x, int y) {
/* 329 */     for (int i = 0; i < this.inventorySlots.inventorySlots.size(); i++) {
/* 330 */       Slot slot = this.inventorySlots.inventorySlots.get(i);
/*     */       
/* 332 */       if (isMouseOverSlot(slot, x, y)) {
/* 333 */         return slot;
/*     */       }
/*     */     } 
/*     */     
/* 337 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException {
/* 344 */     super.mouseClicked(mouseX, mouseY, mouseButton);
/* 345 */     boolean flag = (mouseButton == this.mc.gameSettings.keyBindChat.getKeyCode() + 100);
/* 346 */     Slot slot = getSlotAtPosition(mouseX, mouseY);
/* 347 */     long i = Minecraft.getSystemTime();
/* 348 */     this.doubleClick = (this.lastClickSlot == slot && i - this.lastClickTime < 250L && this.lastClickButton == mouseButton);
/* 349 */     this.ignoreMouseUp = false;
/*     */     
/* 351 */     if (mouseButton == 0 || mouseButton == 1 || flag) {
/* 352 */       int j = this.guiLeft;
/* 353 */       int k = this.guiTop;
/* 354 */       boolean flag1 = !(mouseX >= j && mouseY >= k && mouseX < j + this.xSize && mouseY < k + this.ySize);
/* 355 */       int l = -1;
/*     */       
/* 357 */       if (slot != null) {
/* 358 */         l = slot.slotNumber;
/*     */       }
/*     */       
/* 361 */       if (flag1) {
/* 362 */         l = -999;
/*     */       }
/*     */       
/* 365 */       if (this.mc.gameSettings.touchscreen && flag1 && this.mc.thePlayer.inventory.getItemStack() == null) {
/* 366 */         this.mc.displayGuiScreen(null);
/*     */         
/*     */         return;
/*     */       } 
/* 370 */       if (l != -1) {
/* 371 */         if (this.mc.gameSettings.touchscreen) {
/* 372 */           if (slot != null && slot.getHasStack()) {
/* 373 */             this.clickedSlot = slot;
/* 374 */             this.draggedStack = null;
/* 375 */             this.isRightMouseClick = (mouseButton == 1);
/*     */           } else {
/* 377 */             this.clickedSlot = null;
/*     */           } 
/* 379 */         } else if (!this.dragSplitting) {
/* 380 */           if (this.mc.thePlayer.inventory.getItemStack() == null) {
/* 381 */             if (mouseButton == this.mc.gameSettings.keyBindChat.getKeyCode() + 100) {
/* 382 */               handleMouseClick(slot, l, mouseButton, 3);
/*     */             } else {
/* 384 */               boolean flag2 = (l != -999 && (Keyboard.isKeyDown(42) || Keyboard.isKeyDown(54)));
/* 385 */               int i1 = 0;
/*     */               
/* 387 */               if (flag2) {
/* 388 */                 this.shiftClickedSlot = (slot != null && slot.getHasStack()) ? slot.getStack() : null;
/* 389 */                 i1 = 1;
/* 390 */               } else if (l == -999) {
/* 391 */                 i1 = 4;
/*     */               } 
/*     */               
/* 394 */               handleMouseClick(slot, l, mouseButton, i1);
/*     */             } 
/*     */             
/* 397 */             this.ignoreMouseUp = true;
/*     */           } else {
/* 399 */             this.dragSplitting = true;
/* 400 */             this.dragSplittingButton = mouseButton;
/* 401 */             this.dragSplittingSlots.clear();
/*     */             
/* 403 */             if (mouseButton == 0) {
/* 404 */               this.dragSplittingLimit = 0;
/* 405 */             } else if (mouseButton == 1) {
/* 406 */               this.dragSplittingLimit = 1;
/* 407 */             } else if (mouseButton == this.mc.gameSettings.keyBindChat.getKeyCode() + 100) {
/* 408 */               this.dragSplittingLimit = 2;
/*     */             } 
/*     */           } 
/*     */         } 
/*     */       }
/*     */     } 
/*     */     
/* 415 */     this.lastClickSlot = slot;
/* 416 */     this.lastClickTime = i;
/* 417 */     this.lastClickButton = mouseButton;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void mouseClickMove(int mouseX, int mouseY, int clickedMouseButton, long timeSinceLastClick) {
/* 425 */     Slot slot = getSlotAtPosition(mouseX, mouseY);
/* 426 */     ItemStack itemstack = this.mc.thePlayer.inventory.getItemStack();
/*     */     
/* 428 */     if (this.clickedSlot != null && this.mc.gameSettings.touchscreen) {
/* 429 */       if (clickedMouseButton == 0 || clickedMouseButton == 1) {
/* 430 */         if (this.draggedStack == null) {
/* 431 */           if (slot != this.clickedSlot && this.clickedSlot.getStack() != null) {
/* 432 */             this.draggedStack = this.clickedSlot.getStack().copy();
/*     */           }
/* 434 */         } else if (this.draggedStack.stackSize > 1 && slot != null && Container.canAddItemToSlot(slot, this.draggedStack, false)) {
/* 435 */           long i = Minecraft.getSystemTime();
/*     */           
/* 437 */           if (this.currentDragTargetSlot == slot) {
/* 438 */             if (i - this.dragItemDropDelay > 500L) {
/* 439 */               handleMouseClick(this.clickedSlot, this.clickedSlot.slotNumber, 0, 0);
/* 440 */               handleMouseClick(slot, slot.slotNumber, 1, 0);
/* 441 */               handleMouseClick(this.clickedSlot, this.clickedSlot.slotNumber, 0, 0);
/* 442 */               this.dragItemDropDelay = i + 750L;
/* 443 */               this.draggedStack.stackSize--;
/*     */             } 
/*     */           } else {
/* 446 */             this.currentDragTargetSlot = slot;
/* 447 */             this.dragItemDropDelay = i;
/*     */           } 
/*     */         } 
/*     */       }
/* 451 */     } else if (this.dragSplitting && slot != null && itemstack != null && itemstack.stackSize > this.dragSplittingSlots.size() && Container.canAddItemToSlot(slot, itemstack, true) && slot.isItemValid(itemstack) && this.inventorySlots.canDragIntoSlot(slot)) {
/* 452 */       this.dragSplittingSlots.add(slot);
/* 453 */       updateDragSplitting();
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void mouseReleased(int mouseX, int mouseY, int state) {
/* 461 */     Slot slot = getSlotAtPosition(mouseX, mouseY);
/* 462 */     int i = this.guiLeft;
/* 463 */     int j = this.guiTop;
/* 464 */     boolean flag = !(mouseX >= i && mouseY >= j && mouseX < i + this.xSize && mouseY < j + this.ySize);
/* 465 */     int k = -1;
/*     */     
/* 467 */     if (slot != null) {
/* 468 */       k = slot.slotNumber;
/*     */     }
/*     */     
/* 471 */     if (flag) {
/* 472 */       k = -999;
/*     */     }
/*     */     
/* 475 */     if (this.doubleClick && slot != null && state == 0 && this.inventorySlots.canMergeSlot(null, slot)) {
/* 476 */       if (isShiftKeyDown()) {
/* 477 */         if (slot != null && slot.inventory != null && this.shiftClickedSlot != null) {
/* 478 */           for (Slot slot2 : this.inventorySlots.inventorySlots) {
/* 479 */             if (slot2 != null && slot2.canTakeStack((EntityPlayer)this.mc.thePlayer) && slot2.getHasStack() && slot2.inventory == slot.inventory && Container.canAddItemToSlot(slot2, this.shiftClickedSlot, true)) {
/* 480 */               handleMouseClick(slot2, slot2.slotNumber, state, 1);
/*     */             }
/*     */           } 
/*     */         }
/*     */       } else {
/* 485 */         handleMouseClick(slot, k, state, 6);
/*     */       } 
/*     */       
/* 488 */       this.doubleClick = false;
/* 489 */       this.lastClickTime = 0L;
/*     */     } else {
/* 491 */       if (this.dragSplitting && this.dragSplittingButton != state) {
/* 492 */         this.dragSplitting = false;
/* 493 */         this.dragSplittingSlots.clear();
/* 494 */         this.ignoreMouseUp = true;
/*     */         
/*     */         return;
/*     */       } 
/* 498 */       if (this.ignoreMouseUp) {
/* 499 */         this.ignoreMouseUp = false;
/*     */         
/*     */         return;
/*     */       } 
/* 503 */       if (this.clickedSlot != null && this.mc.gameSettings.touchscreen) {
/* 504 */         if (state == 0 || state == 1) {
/* 505 */           if (this.draggedStack == null && slot != this.clickedSlot) {
/* 506 */             this.draggedStack = this.clickedSlot.getStack();
/*     */           }
/*     */           
/* 509 */           boolean flag2 = Container.canAddItemToSlot(slot, this.draggedStack, false);
/*     */           
/* 511 */           if (k != -1 && this.draggedStack != null && flag2) {
/* 512 */             handleMouseClick(this.clickedSlot, this.clickedSlot.slotNumber, state, 0);
/* 513 */             handleMouseClick(slot, k, 0, 0);
/*     */             
/* 515 */             if (this.mc.thePlayer.inventory.getItemStack() != null) {
/* 516 */               handleMouseClick(this.clickedSlot, this.clickedSlot.slotNumber, state, 0);
/* 517 */               this.touchUpX = mouseX - i;
/* 518 */               this.touchUpY = mouseY - j;
/* 519 */               this.returningStackDestSlot = this.clickedSlot;
/* 520 */               this.returningStack = this.draggedStack;
/* 521 */               this.returningStackTime = Minecraft.getSystemTime();
/*     */             } else {
/* 523 */               this.returningStack = null;
/*     */             } 
/* 525 */           } else if (this.draggedStack != null) {
/* 526 */             this.touchUpX = mouseX - i;
/* 527 */             this.touchUpY = mouseY - j;
/* 528 */             this.returningStackDestSlot = this.clickedSlot;
/* 529 */             this.returningStack = this.draggedStack;
/* 530 */             this.returningStackTime = Minecraft.getSystemTime();
/*     */           } 
/*     */           
/* 533 */           this.draggedStack = null;
/* 534 */           this.clickedSlot = null;
/*     */         } 
/* 536 */       } else if (this.dragSplitting && !this.dragSplittingSlots.isEmpty()) {
/* 537 */         handleMouseClick((Slot)null, -999, Container.func_94534_d(0, this.dragSplittingLimit), 5);
/*     */         
/* 539 */         for (Slot slot1 : this.dragSplittingSlots) {
/* 540 */           handleMouseClick(slot1, slot1.slotNumber, Container.func_94534_d(1, this.dragSplittingLimit), 5);
/*     */         }
/*     */         
/* 543 */         handleMouseClick((Slot)null, -999, Container.func_94534_d(2, this.dragSplittingLimit), 5);
/* 544 */       } else if (this.mc.thePlayer.inventory.getItemStack() != null) {
/* 545 */         if (state == this.mc.gameSettings.keyBindChat.getKeyCode() + 100) {
/* 546 */           handleMouseClick(slot, k, state, 3);
/*     */         } else {
/* 548 */           boolean flag1 = (k != -999 && (Keyboard.isKeyDown(42) || Keyboard.isKeyDown(54)));
/*     */           
/* 550 */           if (flag1) {
/* 551 */             this.shiftClickedSlot = (slot != null && slot.getHasStack()) ? slot.getStack() : null;
/*     */           }
/*     */           
/* 554 */           handleMouseClick(slot, k, state, flag1 ? 1 : 0);
/*     */         } 
/*     */       } 
/*     */     } 
/*     */     
/* 559 */     if (this.mc.thePlayer.inventory.getItemStack() == null) {
/* 560 */       this.lastClickTime = 0L;
/*     */     }
/*     */     
/* 563 */     this.dragSplitting = false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private boolean isMouseOverSlot(Slot slotIn, int mouseX, int mouseY) {
/* 570 */     return isPointInRegion(slotIn.xDisplayPosition, slotIn.yDisplayPosition, 16, 16, mouseX, mouseY);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected boolean isPointInRegion(int left, int top, int right, int bottom, int pointX, int pointY) {
/* 578 */     int i = this.guiLeft;
/* 579 */     int j = this.guiTop;
/* 580 */     pointX -= i;
/* 581 */     pointY -= j;
/* 582 */     return (pointX >= left - 1 && pointX < left + right + 1 && pointY >= top - 1 && pointY < top + bottom + 1);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void handleMouseClick(Slot slotIn, int slotId, int clickedButton, int clickType) {
/* 589 */     if (slotIn != null) {
/* 590 */       slotId = slotIn.slotNumber;
/*     */     }
/*     */     
/* 593 */     this.mc.playerController.windowClick(this.inventorySlots.windowId, slotId, clickedButton, clickType, (EntityPlayer)this.mc.thePlayer);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void keyTyped(char typedChar, int keyCode) throws IOException {
/* 601 */     if (keyCode == 1 || keyCode == this.mc.gameSettings.keyBindUseItem.getKeyCode()) {
/* 602 */       this.mc.thePlayer.closeScreen();
/*     */     }
/*     */     
/* 605 */     checkHotbarKeys(keyCode);
/*     */     
/* 607 */     if (this.theSlot != null && this.theSlot.getHasStack()) {
/* 608 */       if (keyCode == this.mc.gameSettings.keyBindChat.getKeyCode()) {
/* 609 */         handleMouseClick(this.theSlot, this.theSlot.slotNumber, 0, 3);
/* 610 */       } else if (keyCode == this.mc.gameSettings.keyBindAttack.getKeyCode()) {
/* 611 */         handleMouseClick(this.theSlot, this.theSlot.slotNumber, isCtrlKeyDown() ? 1 : 0, 4);
/*     */       } 
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected boolean checkHotbarKeys(int keyCode) {
/* 621 */     if (this.mc.thePlayer.inventory.getItemStack() == null && this.theSlot != null) {
/* 622 */       for (int i = 0; i < 9; i++) {
/* 623 */         if (keyCode == this.mc.gameSettings.keyBindings[i].getKeyCode()) {
/* 624 */           handleMouseClick(this.theSlot, this.theSlot.slotNumber, i, 2);
/* 625 */           return true;
/*     */         } 
/*     */       } 
/*     */     }
/*     */     
/* 630 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void onGuiClosed() {
/* 637 */     if (this.mc.thePlayer != null) {
/* 638 */       this.inventorySlots.onContainerClosed((EntityPlayer)this.mc.thePlayer);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean doesGuiPauseGame() {
/* 646 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void updateScreen() {
/* 653 */     super.updateScreen();
/*     */     
/* 655 */     if (!this.mc.thePlayer.isEntityAlive() || this.mc.thePlayer.isDead)
/* 656 */       this.mc.thePlayer.closeScreen(); 
/*     */   }
/*     */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\minecraft\client\gui\inventory\GuiContainer.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */