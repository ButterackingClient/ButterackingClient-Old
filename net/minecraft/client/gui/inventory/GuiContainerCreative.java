/*     */ package net.minecraft.client.gui.inventory;
/*     */ 
/*     */ import com.google.common.collect.Lists;
/*     */ import java.io.IOException;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import net.minecraft.client.gui.GuiButton;
/*     */ import net.minecraft.client.gui.GuiScreen;
/*     */ import net.minecraft.client.gui.GuiTextField;
/*     */ import net.minecraft.client.gui.achievement.GuiAchievements;
/*     */ import net.minecraft.client.gui.achievement.GuiStats;
/*     */ import net.minecraft.client.renderer.GlStateManager;
/*     */ import net.minecraft.client.renderer.InventoryEffectRenderer;
/*     */ import net.minecraft.client.renderer.RenderHelper;
/*     */ import net.minecraft.client.resources.I18n;
/*     */ import net.minecraft.client.settings.GameSettings;
/*     */ import net.minecraft.creativetab.CreativeTabs;
/*     */ import net.minecraft.enchantment.Enchantment;
/*     */ import net.minecraft.enchantment.EnchantmentHelper;
/*     */ import net.minecraft.entity.EntityLivingBase;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.entity.player.InventoryPlayer;
/*     */ import net.minecraft.init.Items;
/*     */ import net.minecraft.inventory.Container;
/*     */ import net.minecraft.inventory.IInventory;
/*     */ import net.minecraft.inventory.InventoryBasic;
/*     */ import net.minecraft.inventory.Slot;
/*     */ import net.minecraft.item.Item;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraft.util.EnumChatFormatting;
/*     */ import net.minecraft.util.MathHelper;
/*     */ import net.minecraft.util.ResourceLocation;
/*     */ import org.lwjgl.input.Keyboard;
/*     */ import org.lwjgl.input.Mouse;
/*     */ 
/*     */ 
/*     */ public class GuiContainerCreative
/*     */   extends InventoryEffectRenderer
/*     */ {
/*  41 */   private static final ResourceLocation creativeInventoryTabs = new ResourceLocation("textures/gui/container/creative_inventory/tabs.png");
/*  42 */   private static InventoryBasic field_147060_v = new InventoryBasic("tmp", true, 45);
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  47 */   private static int selectedTabIndex = CreativeTabs.tabBlock.getTabIndex();
/*     */ 
/*     */   
/*     */   private float currentScroll;
/*     */ 
/*     */   
/*     */   private boolean isScrolling;
/*     */ 
/*     */   
/*     */   private boolean wasClicking;
/*     */   
/*     */   private GuiTextField searchField;
/*     */   
/*     */   private List<Slot> field_147063_B;
/*     */   
/*     */   private Slot field_147064_C;
/*     */   
/*     */   private boolean field_147057_D;
/*     */   
/*     */   private CreativeCrafting field_147059_E;
/*     */ 
/*     */   
/*     */   public GuiContainerCreative(EntityPlayer p_i1088_1_) {
/*  70 */     super(new ContainerCreative(p_i1088_1_));
/*  71 */     p_i1088_1_.openContainer = this.inventorySlots;
/*  72 */     this.allowUserInput = true;
/*  73 */     this.ySize = 136;
/*  74 */     this.xSize = 195;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void updateScreen() {
/*  81 */     if (!this.mc.playerController.isInCreativeMode()) {
/*  82 */       this.mc.displayGuiScreen((GuiScreen)new GuiInventory((EntityPlayer)this.mc.thePlayer));
/*     */     }
/*     */     
/*  85 */     updateActivePotionEffects();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void handleMouseClick(Slot slotIn, int slotId, int clickedButton, int clickType) {
/*  92 */     this.field_147057_D = true;
/*  93 */     boolean flag = (clickType == 1);
/*  94 */     clickType = (slotId == -999 && clickType == 0) ? 4 : clickType;
/*     */     
/*  96 */     if (slotIn == null && selectedTabIndex != CreativeTabs.tabInventory.getTabIndex() && clickType != 5) {
/*  97 */       InventoryPlayer inventoryplayer1 = this.mc.thePlayer.inventory;
/*     */       
/*  99 */       if (inventoryplayer1.getItemStack() != null) {
/* 100 */         if (clickedButton == 0) {
/* 101 */           this.mc.thePlayer.dropPlayerItemWithRandomChoice(inventoryplayer1.getItemStack(), true);
/* 102 */           this.mc.playerController.sendPacketDropItem(inventoryplayer1.getItemStack());
/* 103 */           inventoryplayer1.setItemStack(null);
/*     */         } 
/*     */         
/* 106 */         if (clickedButton == 1) {
/* 107 */           ItemStack itemstack5 = inventoryplayer1.getItemStack().splitStack(1);
/* 108 */           this.mc.thePlayer.dropPlayerItemWithRandomChoice(itemstack5, true);
/* 109 */           this.mc.playerController.sendPacketDropItem(itemstack5);
/*     */           
/* 111 */           if ((inventoryplayer1.getItemStack()).stackSize == 0) {
/* 112 */             inventoryplayer1.setItemStack(null);
/*     */           }
/*     */         } 
/*     */       } 
/* 116 */     } else if (slotIn == this.field_147064_C && flag) {
/* 117 */       for (int j = 0; j < this.mc.thePlayer.inventoryContainer.getInventory().size(); j++) {
/* 118 */         this.mc.playerController.sendSlotPacket(null, j);
/*     */       }
/* 120 */     } else if (selectedTabIndex == CreativeTabs.tabInventory.getTabIndex()) {
/* 121 */       if (slotIn == this.field_147064_C) {
/* 122 */         this.mc.thePlayer.inventory.setItemStack(null);
/* 123 */       } else if (clickType == 4 && slotIn != null && slotIn.getHasStack()) {
/* 124 */         ItemStack itemstack = slotIn.decrStackSize((clickedButton == 0) ? 1 : slotIn.getStack().getMaxStackSize());
/* 125 */         this.mc.thePlayer.dropPlayerItemWithRandomChoice(itemstack, true);
/* 126 */         this.mc.playerController.sendPacketDropItem(itemstack);
/* 127 */       } else if (clickType == 4 && this.mc.thePlayer.inventory.getItemStack() != null) {
/* 128 */         this.mc.thePlayer.dropPlayerItemWithRandomChoice(this.mc.thePlayer.inventory.getItemStack(), true);
/* 129 */         this.mc.playerController.sendPacketDropItem(this.mc.thePlayer.inventory.getItemStack());
/* 130 */         this.mc.thePlayer.inventory.setItemStack(null);
/*     */       } else {
/* 132 */         this.mc.thePlayer.inventoryContainer.slotClick((slotIn == null) ? slotId : ((CreativeSlot)slotIn).slot.slotNumber, clickedButton, clickType, (EntityPlayer)this.mc.thePlayer);
/* 133 */         this.mc.thePlayer.inventoryContainer.detectAndSendChanges();
/*     */       } 
/* 135 */     } else if (clickType != 5 && slotIn.inventory == field_147060_v) {
/* 136 */       InventoryPlayer inventoryplayer = this.mc.thePlayer.inventory;
/* 137 */       ItemStack itemstack1 = inventoryplayer.getItemStack();
/* 138 */       ItemStack itemstack2 = slotIn.getStack();
/*     */       
/* 140 */       if (clickType == 2) {
/* 141 */         if (itemstack2 != null && clickedButton >= 0 && clickedButton < 9) {
/* 142 */           ItemStack itemstack7 = itemstack2.copy();
/* 143 */           itemstack7.stackSize = itemstack7.getMaxStackSize();
/* 144 */           this.mc.thePlayer.inventory.setInventorySlotContents(clickedButton, itemstack7);
/* 145 */           this.mc.thePlayer.inventoryContainer.detectAndSendChanges();
/*     */         } 
/*     */         
/*     */         return;
/*     */       } 
/*     */       
/* 151 */       if (clickType == 3) {
/* 152 */         if (inventoryplayer.getItemStack() == null && slotIn.getHasStack()) {
/* 153 */           ItemStack itemstack6 = slotIn.getStack().copy();
/* 154 */           itemstack6.stackSize = itemstack6.getMaxStackSize();
/* 155 */           inventoryplayer.setItemStack(itemstack6);
/*     */         } 
/*     */         
/*     */         return;
/*     */       } 
/*     */       
/* 161 */       if (clickType == 4) {
/* 162 */         if (itemstack2 != null) {
/* 163 */           ItemStack itemstack3 = itemstack2.copy();
/* 164 */           itemstack3.stackSize = (clickedButton == 0) ? 1 : itemstack3.getMaxStackSize();
/* 165 */           this.mc.thePlayer.dropPlayerItemWithRandomChoice(itemstack3, true);
/* 166 */           this.mc.playerController.sendPacketDropItem(itemstack3);
/*     */         } 
/*     */         
/*     */         return;
/*     */       } 
/*     */       
/* 172 */       if (itemstack1 != null && itemstack2 != null && itemstack1.isItemEqual(itemstack2)) {
/* 173 */         if (clickedButton == 0) {
/* 174 */           if (flag) {
/* 175 */             itemstack1.stackSize = itemstack1.getMaxStackSize();
/* 176 */           } else if (itemstack1.stackSize < itemstack1.getMaxStackSize()) {
/* 177 */             itemstack1.stackSize++;
/*     */           } 
/* 179 */         } else if (itemstack1.stackSize <= 1) {
/* 180 */           inventoryplayer.setItemStack(null);
/*     */         } else {
/* 182 */           itemstack1.stackSize--;
/*     */         } 
/* 184 */       } else if (itemstack2 != null && itemstack1 == null) {
/* 185 */         inventoryplayer.setItemStack(ItemStack.copyItemStack(itemstack2));
/* 186 */         itemstack1 = inventoryplayer.getItemStack();
/*     */         
/* 188 */         if (flag) {
/* 189 */           itemstack1.stackSize = itemstack1.getMaxStackSize();
/*     */         }
/*     */       } else {
/* 192 */         inventoryplayer.setItemStack(null);
/*     */       } 
/*     */     } else {
/* 195 */       this.inventorySlots.slotClick((slotIn == null) ? slotId : slotIn.slotNumber, clickedButton, clickType, (EntityPlayer)this.mc.thePlayer);
/*     */       
/* 197 */       if (Container.getDragEvent(clickedButton) == 2) {
/* 198 */         for (int i = 0; i < 9; i++) {
/* 199 */           this.mc.playerController.sendSlotPacket(this.inventorySlots.getSlot(45 + i).getStack(), 36 + i);
/*     */         }
/* 201 */       } else if (slotIn != null) {
/* 202 */         ItemStack itemstack4 = this.inventorySlots.getSlot(slotIn.slotNumber).getStack();
/* 203 */         this.mc.playerController.sendSlotPacket(itemstack4, slotIn.slotNumber - this.inventorySlots.inventorySlots.size() + 9 + 36);
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   protected void updateActivePotionEffects() {
/* 209 */     int i = this.guiLeft;
/* 210 */     super.updateActivePotionEffects();
/*     */     
/* 212 */     if (this.searchField != null && this.guiLeft != i) {
/* 213 */       this.searchField.xPosition = this.guiLeft + 82;
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void initGui() {
/* 222 */     if (this.mc.playerController.isInCreativeMode()) {
/* 223 */       super.initGui();
/* 224 */       this.buttonList.clear();
/* 225 */       Keyboard.enableRepeatEvents(true);
/* 226 */       this.searchField = new GuiTextField(0, this.fontRendererObj, this.guiLeft + 82, this.guiTop + 6, 89, this.fontRendererObj.FONT_HEIGHT);
/* 227 */       this.searchField.setMaxStringLength(15);
/* 228 */       this.searchField.setEnableBackgroundDrawing(false);
/* 229 */       this.searchField.setVisible(false);
/* 230 */       this.searchField.setTextColor(16777215);
/* 231 */       int i = selectedTabIndex;
/* 232 */       selectedTabIndex = -1;
/* 233 */       setCurrentCreativeTab(CreativeTabs.creativeTabArray[i]);
/* 234 */       this.field_147059_E = new CreativeCrafting(this.mc);
/* 235 */       this.mc.thePlayer.inventoryContainer.onCraftGuiOpened(this.field_147059_E);
/*     */     } else {
/* 237 */       this.mc.displayGuiScreen((GuiScreen)new GuiInventory((EntityPlayer)this.mc.thePlayer));
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void onGuiClosed() {
/* 245 */     super.onGuiClosed();
/*     */     
/* 247 */     if (this.mc.thePlayer != null && this.mc.thePlayer.inventory != null) {
/* 248 */       this.mc.thePlayer.inventoryContainer.removeCraftingFromCrafters(this.field_147059_E);
/*     */     }
/*     */     
/* 251 */     Keyboard.enableRepeatEvents(false);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void keyTyped(char typedChar, int keyCode) throws IOException {
/* 259 */     if (selectedTabIndex != CreativeTabs.tabAllSearch.getTabIndex()) {
/* 260 */       if (GameSettings.isKeyDown(this.mc.gameSettings.keyBindPlayerList)) {
/* 261 */         setCurrentCreativeTab(CreativeTabs.tabAllSearch);
/*     */       } else {
/* 263 */         super.keyTyped(typedChar, keyCode);
/*     */       } 
/*     */     } else {
/* 266 */       if (this.field_147057_D) {
/* 267 */         this.field_147057_D = false;
/* 268 */         this.searchField.setText("");
/*     */       } 
/*     */       
/* 271 */       if (!checkHotbarKeys(keyCode)) {
/* 272 */         if (this.searchField.textboxKeyTyped(typedChar, keyCode)) {
/* 273 */           updateCreativeSearch();
/*     */         } else {
/* 275 */           super.keyTyped(typedChar, keyCode);
/*     */         } 
/*     */       }
/*     */     } 
/*     */   }
/*     */   
/*     */   private void updateCreativeSearch() {
/* 282 */     ContainerCreative guicontainercreative$containercreative = (ContainerCreative)this.inventorySlots;
/* 283 */     guicontainercreative$containercreative.itemList.clear();
/*     */     
/* 285 */     for (Item item : Item.itemRegistry) {
/* 286 */       if (item != null && item.getCreativeTab() != null)
/* 287 */         item.getSubItems(item, null, guicontainercreative$containercreative.itemList); 
/*     */     }  byte b;
/*     */     int i;
/*     */     Enchantment[] arrayOfEnchantment;
/* 291 */     for (i = (arrayOfEnchantment = Enchantment.enchantmentsBookList).length, b = 0; b < i; ) { Enchantment enchantment = arrayOfEnchantment[b];
/* 292 */       if (enchantment != null && enchantment.type != null) {
/* 293 */         Items.enchanted_book.getAll(enchantment, guicontainercreative$containercreative.itemList);
/*     */       }
/*     */       b++; }
/*     */     
/* 297 */     Iterator<ItemStack> iterator = guicontainercreative$containercreative.itemList.iterator();
/* 298 */     String s1 = this.searchField.getText().toLowerCase();
/*     */     
/* 300 */     while (iterator.hasNext()) {
/* 301 */       ItemStack itemstack = iterator.next();
/* 302 */       boolean flag = false;
/*     */       
/* 304 */       for (String s : itemstack.getTooltip((EntityPlayer)this.mc.thePlayer, this.mc.gameSettings.advancedItemTooltips)) {
/* 305 */         if (EnumChatFormatting.getTextWithoutFormattingCodes(s).toLowerCase().contains(s1)) {
/* 306 */           flag = true;
/*     */           
/*     */           break;
/*     */         } 
/*     */       } 
/* 311 */       if (!flag) {
/* 312 */         iterator.remove();
/*     */       }
/*     */     } 
/*     */     
/* 316 */     this.currentScroll = 0.0F;
/* 317 */     guicontainercreative$containercreative.scrollTo(0.0F);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY) {
/* 324 */     CreativeTabs creativetabs = CreativeTabs.creativeTabArray[selectedTabIndex];
/*     */     
/* 326 */     if (creativetabs.drawInForegroundOfTab()) {
/* 327 */       GlStateManager.disableBlend();
/* 328 */       this.fontRendererObj.drawString(I18n.format(creativetabs.getTranslatedTabLabel(), new Object[0]), 8, 6, 4210752);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException {
/* 336 */     if (mouseButton == 0) {
/* 337 */       int i = mouseX - this.guiLeft;
/* 338 */       int j = mouseY - this.guiTop; byte b; int k;
/*     */       CreativeTabs[] arrayOfCreativeTabs;
/* 340 */       for (k = (arrayOfCreativeTabs = CreativeTabs.creativeTabArray).length, b = 0; b < k; ) { CreativeTabs creativetabs = arrayOfCreativeTabs[b];
/* 341 */         if (func_147049_a(creativetabs, i, j)) {
/*     */           return;
/*     */         }
/*     */         b++; }
/*     */     
/*     */     } 
/* 347 */     super.mouseClicked(mouseX, mouseY, mouseButton);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void mouseReleased(int mouseX, int mouseY, int state) {
/* 354 */     if (state == 0) {
/* 355 */       int i = mouseX - this.guiLeft;
/* 356 */       int j = mouseY - this.guiTop; byte b; int k;
/*     */       CreativeTabs[] arrayOfCreativeTabs;
/* 358 */       for (k = (arrayOfCreativeTabs = CreativeTabs.creativeTabArray).length, b = 0; b < k; ) { CreativeTabs creativetabs = arrayOfCreativeTabs[b];
/* 359 */         if (func_147049_a(creativetabs, i, j)) {
/* 360 */           setCurrentCreativeTab(creativetabs);
/*     */           return;
/*     */         } 
/*     */         b++; }
/*     */     
/*     */     } 
/* 366 */     super.mouseReleased(mouseX, mouseY, state);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private boolean needsScrollBars() {
/* 373 */     return (selectedTabIndex != CreativeTabs.tabInventory.getTabIndex() && CreativeTabs.creativeTabArray[selectedTabIndex].shouldHidePlayerInventory() && ((ContainerCreative)this.inventorySlots).func_148328_e());
/*     */   }
/*     */   
/*     */   private void setCurrentCreativeTab(CreativeTabs p_147050_1_) {
/* 377 */     int i = selectedTabIndex;
/* 378 */     selectedTabIndex = p_147050_1_.getTabIndex();
/* 379 */     ContainerCreative guicontainercreative$containercreative = (ContainerCreative)this.inventorySlots;
/* 380 */     this.dragSplittingSlots.clear();
/* 381 */     guicontainercreative$containercreative.itemList.clear();
/* 382 */     p_147050_1_.displayAllReleventItems(guicontainercreative$containercreative.itemList);
/*     */     
/* 384 */     if (p_147050_1_ == CreativeTabs.tabInventory) {
/* 385 */       Container container = this.mc.thePlayer.inventoryContainer;
/*     */       
/* 387 */       if (this.field_147063_B == null) {
/* 388 */         this.field_147063_B = guicontainercreative$containercreative.inventorySlots;
/*     */       }
/*     */       
/* 391 */       guicontainercreative$containercreative.inventorySlots = Lists.newArrayList();
/*     */       
/* 393 */       for (int j = 0; j < container.inventorySlots.size(); j++) {
/* 394 */         Slot slot = new CreativeSlot(container.inventorySlots.get(j), j);
/* 395 */         guicontainercreative$containercreative.inventorySlots.add(slot);
/*     */         
/* 397 */         if (j >= 5 && j < 9) {
/* 398 */           int j1 = j - 5;
/* 399 */           int k1 = j1 / 2;
/* 400 */           int l1 = j1 % 2;
/* 401 */           slot.xDisplayPosition = 9 + k1 * 54;
/* 402 */           slot.yDisplayPosition = 6 + l1 * 27;
/* 403 */         } else if (j >= 0 && j < 5) {
/* 404 */           slot.yDisplayPosition = -2000;
/* 405 */           slot.xDisplayPosition = -2000;
/* 406 */         } else if (j < container.inventorySlots.size()) {
/* 407 */           int k = j - 9;
/* 408 */           int l = k % 9;
/* 409 */           int i1 = k / 9;
/* 410 */           slot.xDisplayPosition = 9 + l * 18;
/*     */           
/* 412 */           if (j >= 36) {
/* 413 */             slot.yDisplayPosition = 112;
/*     */           } else {
/* 415 */             slot.yDisplayPosition = 54 + i1 * 18;
/*     */           } 
/*     */         } 
/*     */       } 
/*     */       
/* 420 */       this.field_147064_C = new Slot((IInventory)field_147060_v, 0, 173, 112);
/* 421 */       guicontainercreative$containercreative.inventorySlots.add(this.field_147064_C);
/* 422 */     } else if (i == CreativeTabs.tabInventory.getTabIndex()) {
/* 423 */       guicontainercreative$containercreative.inventorySlots = this.field_147063_B;
/* 424 */       this.field_147063_B = null;
/*     */     } 
/*     */     
/* 427 */     if (this.searchField != null) {
/* 428 */       if (p_147050_1_ == CreativeTabs.tabAllSearch) {
/* 429 */         this.searchField.setVisible(true);
/* 430 */         this.searchField.setCanLoseFocus(false);
/* 431 */         this.searchField.setFocused(true);
/* 432 */         this.searchField.setText("");
/* 433 */         updateCreativeSearch();
/*     */       } else {
/* 435 */         this.searchField.setVisible(false);
/* 436 */         this.searchField.setCanLoseFocus(true);
/* 437 */         this.searchField.setFocused(false);
/*     */       } 
/*     */     }
/*     */     
/* 441 */     this.currentScroll = 0.0F;
/* 442 */     guicontainercreative$containercreative.scrollTo(0.0F);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void handleMouseInput() throws IOException {
/* 449 */     super.handleMouseInput();
/* 450 */     int i = Mouse.getEventDWheel();
/*     */     
/* 452 */     if (i != 0 && needsScrollBars()) {
/* 453 */       int j = ((ContainerCreative)this.inventorySlots).itemList.size() / 9 - 5;
/*     */       
/* 455 */       if (i > 0) {
/* 456 */         i = 1;
/*     */       }
/*     */       
/* 459 */       if (i < 0) {
/* 460 */         i = -1;
/*     */       }
/*     */       
/* 463 */       this.currentScroll = (float)(this.currentScroll - i / j);
/* 464 */       this.currentScroll = MathHelper.clamp_float(this.currentScroll, 0.0F, 1.0F);
/* 465 */       ((ContainerCreative)this.inventorySlots).scrollTo(this.currentScroll);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void drawScreen(int mouseX, int mouseY, float partialTicks) {
/* 473 */     boolean flag = Mouse.isButtonDown(0);
/* 474 */     int i = this.guiLeft;
/* 475 */     int j = this.guiTop;
/* 476 */     int k = i + 175;
/* 477 */     int l = j + 18;
/* 478 */     int i1 = k + 14;
/* 479 */     int j1 = l + 112;
/*     */     
/* 481 */     if (!this.wasClicking && flag && mouseX >= k && mouseY >= l && mouseX < i1 && mouseY < j1) {
/* 482 */       this.isScrolling = needsScrollBars();
/*     */     }
/*     */     
/* 485 */     if (!flag) {
/* 486 */       this.isScrolling = false;
/*     */     }
/*     */     
/* 489 */     this.wasClicking = flag;
/*     */     
/* 491 */     if (this.isScrolling) {
/* 492 */       this.currentScroll = ((mouseY - l) - 7.5F) / ((j1 - l) - 15.0F);
/* 493 */       this.currentScroll = MathHelper.clamp_float(this.currentScroll, 0.0F, 1.0F);
/* 494 */       ((ContainerCreative)this.inventorySlots).scrollTo(this.currentScroll);
/*     */     } 
/*     */     
/* 497 */     super.drawScreen(mouseX, mouseY, partialTicks); byte b; int m;
/*     */     CreativeTabs[] arrayOfCreativeTabs;
/* 499 */     for (m = (arrayOfCreativeTabs = CreativeTabs.creativeTabArray).length, b = 0; b < m; ) { CreativeTabs creativetabs = arrayOfCreativeTabs[b];
/* 500 */       if (renderCreativeInventoryHoveringText(creativetabs, mouseX, mouseY)) {
/*     */         break;
/*     */       }
/*     */       b++; }
/*     */     
/* 505 */     if (this.field_147064_C != null && selectedTabIndex == CreativeTabs.tabInventory.getTabIndex() && isPointInRegion(this.field_147064_C.xDisplayPosition, this.field_147064_C.yDisplayPosition, 16, 16, mouseX, mouseY)) {
/* 506 */       drawCreativeTabHoveringText(I18n.format("inventory.binSlot", new Object[0]), mouseX, mouseY);
/*     */     }
/*     */     
/* 509 */     GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
/* 510 */     GlStateManager.disableLighting();
/*     */   }
/*     */   
/*     */   protected void renderToolTip(ItemStack stack, int x, int y) {
/* 514 */     if (selectedTabIndex == CreativeTabs.tabAllSearch.getTabIndex()) {
/* 515 */       List<String> list = stack.getTooltip((EntityPlayer)this.mc.thePlayer, this.mc.gameSettings.advancedItemTooltips);
/* 516 */       CreativeTabs creativetabs = stack.getItem().getCreativeTab();
/*     */       
/* 518 */       if (creativetabs == null && stack.getItem() == Items.enchanted_book) {
/* 519 */         Map<Integer, Integer> map = EnchantmentHelper.getEnchantments(stack);
/*     */         
/* 521 */         if (map.size() == 1) {
/* 522 */           Enchantment enchantment = Enchantment.getEnchantmentById(((Integer)map.keySet().iterator().next()).intValue()); byte b; int j;
/*     */           CreativeTabs[] arrayOfCreativeTabs;
/* 524 */           for (j = (arrayOfCreativeTabs = CreativeTabs.creativeTabArray).length, b = 0; b < j; ) { CreativeTabs creativetabs1 = arrayOfCreativeTabs[b];
/* 525 */             if (creativetabs1.hasRelevantEnchantmentType(enchantment.type)) {
/* 526 */               creativetabs = creativetabs1;
/*     */               break;
/*     */             } 
/*     */             b++; }
/*     */         
/*     */         } 
/*     */       } 
/* 533 */       if (creativetabs != null) {
/* 534 */         list.add(1, EnumChatFormatting.BOLD + EnumChatFormatting.BLUE + I18n.format(creativetabs.getTranslatedTabLabel(), new Object[0]));
/*     */       }
/*     */       
/* 537 */       for (int i = 0; i < list.size(); i++) {
/* 538 */         if (i == 0) {
/* 539 */           list.set(i, (stack.getRarity()).rarityColor + (String)list.get(i));
/*     */         } else {
/* 541 */           list.set(i, EnumChatFormatting.GRAY + (String)list.get(i));
/*     */         } 
/*     */       } 
/*     */       
/* 545 */       drawHoveringText(list, x, y);
/*     */     } else {
/* 547 */       super.renderToolTip(stack, x, y);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY) {
/* 555 */     GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
/* 556 */     RenderHelper.enableGUIStandardItemLighting();
/* 557 */     CreativeTabs creativetabs = CreativeTabs.creativeTabArray[selectedTabIndex]; byte b; int m;
/*     */     CreativeTabs[] arrayOfCreativeTabs;
/* 559 */     for (m = (arrayOfCreativeTabs = CreativeTabs.creativeTabArray).length, b = 0; b < m; ) { CreativeTabs creativetabs1 = arrayOfCreativeTabs[b];
/* 560 */       this.mc.getTextureManager().bindTexture(creativeInventoryTabs);
/*     */       
/* 562 */       if (creativetabs1.getTabIndex() != selectedTabIndex) {
/* 563 */         func_147051_a(creativetabs1);
/*     */       }
/*     */       b++; }
/*     */     
/* 567 */     this.mc.getTextureManager().bindTexture(new ResourceLocation("textures/gui/container/creative_inventory/tab_" + creativetabs.getBackgroundImageName()));
/* 568 */     drawTexturedModalRect(this.guiLeft, this.guiTop, 0, 0, this.xSize, this.ySize);
/* 569 */     this.searchField.drawTextBox();
/* 570 */     GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
/* 571 */     int i = this.guiLeft + 175;
/* 572 */     int j = this.guiTop + 18;
/* 573 */     int k = j + 112;
/* 574 */     this.mc.getTextureManager().bindTexture(creativeInventoryTabs);
/*     */     
/* 576 */     if (creativetabs.shouldHidePlayerInventory()) {
/* 577 */       drawTexturedModalRect(i, j + (int)((k - j - 17) * this.currentScroll), 232 + (needsScrollBars() ? 0 : 12), 0, 12, 15);
/*     */     }
/*     */     
/* 580 */     func_147051_a(creativetabs);
/*     */     
/* 582 */     if (creativetabs == CreativeTabs.tabInventory) {
/* 583 */       GuiInventory.drawEntityOnScreen(this.guiLeft + 43, this.guiTop + 45, 20, (this.guiLeft + 43 - mouseX), (this.guiTop + 45 - 30 - mouseY), (EntityLivingBase)this.mc.thePlayer);
/*     */     }
/*     */   }
/*     */   
/*     */   protected boolean func_147049_a(CreativeTabs p_147049_1_, int p_147049_2_, int p_147049_3_) {
/* 588 */     int i = p_147049_1_.getTabColumn();
/* 589 */     int j = 28 * i;
/* 590 */     int k = 0;
/*     */     
/* 592 */     if (i == 5) {
/* 593 */       j = this.xSize - 28 + 2;
/* 594 */     } else if (i > 0) {
/* 595 */       j += i;
/*     */     } 
/*     */     
/* 598 */     if (p_147049_1_.isTabInFirstRow()) {
/* 599 */       k -= 32;
/*     */     } else {
/* 601 */       k += this.ySize;
/*     */     } 
/*     */     
/* 604 */     return (p_147049_2_ >= j && p_147049_2_ <= j + 28 && p_147049_3_ >= k && p_147049_3_ <= k + 32);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected boolean renderCreativeInventoryHoveringText(CreativeTabs p_147052_1_, int p_147052_2_, int p_147052_3_) {
/* 612 */     int i = p_147052_1_.getTabColumn();
/* 613 */     int j = 28 * i;
/* 614 */     int k = 0;
/*     */     
/* 616 */     if (i == 5) {
/* 617 */       j = this.xSize - 28 + 2;
/* 618 */     } else if (i > 0) {
/* 619 */       j += i;
/*     */     } 
/*     */     
/* 622 */     if (p_147052_1_.isTabInFirstRow()) {
/* 623 */       k -= 32;
/*     */     } else {
/* 625 */       k += this.ySize;
/*     */     } 
/*     */     
/* 628 */     if (isPointInRegion(j + 3, k + 3, 23, 27, p_147052_2_, p_147052_3_)) {
/* 629 */       drawCreativeTabHoveringText(I18n.format(p_147052_1_.getTranslatedTabLabel(), new Object[0]), p_147052_2_, p_147052_3_);
/* 630 */       return true;
/*     */     } 
/* 632 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   protected void func_147051_a(CreativeTabs p_147051_1_) {
/* 637 */     boolean flag = (p_147051_1_.getTabIndex() == selectedTabIndex);
/* 638 */     boolean flag1 = p_147051_1_.isTabInFirstRow();
/* 639 */     int i = p_147051_1_.getTabColumn();
/* 640 */     int j = i * 28;
/* 641 */     int k = 0;
/* 642 */     int l = this.guiLeft + 28 * i;
/* 643 */     int i1 = this.guiTop;
/* 644 */     int j1 = 32;
/*     */     
/* 646 */     if (flag) {
/* 647 */       k += 32;
/*     */     }
/*     */     
/* 650 */     if (i == 5) {
/* 651 */       l = this.guiLeft + this.xSize - 28;
/* 652 */     } else if (i > 0) {
/* 653 */       l += i;
/*     */     } 
/*     */     
/* 656 */     if (flag1) {
/* 657 */       i1 -= 28;
/*     */     } else {
/* 659 */       k += 64;
/* 660 */       i1 += this.ySize - 4;
/*     */     } 
/*     */     
/* 663 */     GlStateManager.disableLighting();
/* 664 */     drawTexturedModalRect(l, i1, j, k, 28, j1);
/* 665 */     this.zLevel = 100.0F;
/* 666 */     this.itemRender.zLevel = 100.0F;
/* 667 */     l += 6;
/* 668 */     i1 = i1 + 8 + (flag1 ? 1 : -1);
/* 669 */     GlStateManager.enableLighting();
/* 670 */     GlStateManager.enableRescaleNormal();
/* 671 */     ItemStack itemstack = p_147051_1_.getIconItemStack();
/* 672 */     this.itemRender.renderItemAndEffectIntoGUI(itemstack, l, i1);
/* 673 */     this.itemRender.renderItemOverlays(this.fontRendererObj, itemstack, l, i1);
/* 674 */     GlStateManager.disableLighting();
/* 675 */     this.itemRender.zLevel = 0.0F;
/* 676 */     this.zLevel = 0.0F;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void actionPerformed(GuiButton button) throws IOException {
/* 683 */     if (button.id == 0) {
/* 684 */       this.mc.displayGuiScreen((GuiScreen)new GuiAchievements((GuiScreen)this, this.mc.thePlayer.getStatFileWriter()));
/*     */     }
/*     */     
/* 687 */     if (button.id == 1) {
/* 688 */       this.mc.displayGuiScreen((GuiScreen)new GuiStats((GuiScreen)this, this.mc.thePlayer.getStatFileWriter()));
/*     */     }
/*     */   }
/*     */   
/*     */   public int getSelectedTabIndex() {
/* 693 */     return selectedTabIndex;
/*     */   }
/*     */   
/*     */   static class ContainerCreative extends Container {
/* 697 */     public List<ItemStack> itemList = Lists.newArrayList();
/*     */     
/*     */     public ContainerCreative(EntityPlayer p_i1086_1_) {
/* 700 */       InventoryPlayer inventoryplayer = p_i1086_1_.inventory;
/*     */       
/* 702 */       for (int i = 0; i < 5; i++) {
/* 703 */         for (int j = 0; j < 9; j++) {
/* 704 */           addSlotToContainer(new Slot((IInventory)GuiContainerCreative.field_147060_v, i * 9 + j, 9 + j * 18, 18 + i * 18));
/*     */         }
/*     */       } 
/*     */       
/* 708 */       for (int k = 0; k < 9; k++) {
/* 709 */         addSlotToContainer(new Slot((IInventory)inventoryplayer, k, 9 + k * 18, 112));
/*     */       }
/*     */       
/* 712 */       scrollTo(0.0F);
/*     */     }
/*     */     
/*     */     public boolean canInteractWith(EntityPlayer playerIn) {
/* 716 */       return true;
/*     */     }
/*     */     
/*     */     public void scrollTo(float p_148329_1_) {
/* 720 */       int i = (this.itemList.size() + 9 - 1) / 9 - 5;
/* 721 */       int j = (int)((p_148329_1_ * i) + 0.5D);
/*     */       
/* 723 */       if (j < 0) {
/* 724 */         j = 0;
/*     */       }
/*     */       
/* 727 */       for (int k = 0; k < 5; k++) {
/* 728 */         for (int l = 0; l < 9; l++) {
/* 729 */           int i1 = l + (k + j) * 9;
/*     */           
/* 731 */           if (i1 >= 0 && i1 < this.itemList.size()) {
/* 732 */             GuiContainerCreative.field_147060_v.setInventorySlotContents(l + k * 9, this.itemList.get(i1));
/*     */           } else {
/* 734 */             GuiContainerCreative.field_147060_v.setInventorySlotContents(l + k * 9, null);
/*     */           } 
/*     */         } 
/*     */       } 
/*     */     }
/*     */     
/*     */     public boolean func_148328_e() {
/* 741 */       return (this.itemList.size() > 45);
/*     */     }
/*     */ 
/*     */     
/*     */     protected void retrySlotClick(int slotId, int clickedButton, boolean mode, EntityPlayer playerIn) {}
/*     */     
/*     */     public ItemStack transferStackInSlot(EntityPlayer playerIn, int index) {
/* 748 */       if (index >= this.inventorySlots.size() - 9 && index < this.inventorySlots.size()) {
/* 749 */         Slot slot = this.inventorySlots.get(index);
/*     */         
/* 751 */         if (slot != null && slot.getHasStack()) {
/* 752 */           slot.putStack(null);
/*     */         }
/*     */       } 
/*     */       
/* 756 */       return null;
/*     */     }
/*     */     
/*     */     public boolean canMergeSlot(ItemStack stack, Slot slotIn) {
/* 760 */       return (slotIn.yDisplayPosition > 90);
/*     */     }
/*     */     
/*     */     public boolean canDragIntoSlot(Slot p_94531_1_) {
/* 764 */       return !(!(p_94531_1_.inventory instanceof InventoryPlayer) && (p_94531_1_.yDisplayPosition <= 90 || p_94531_1_.xDisplayPosition > 162));
/*     */     }
/*     */   }
/*     */   
/*     */   class CreativeSlot extends Slot {
/*     */     private final Slot slot;
/*     */     
/*     */     public CreativeSlot(Slot p_i46313_2_, int p_i46313_3_) {
/* 772 */       super(p_i46313_2_.inventory, p_i46313_3_, 0, 0);
/* 773 */       this.slot = p_i46313_2_;
/*     */     }
/*     */     
/*     */     public void onPickupFromSlot(EntityPlayer playerIn, ItemStack stack) {
/* 777 */       this.slot.onPickupFromSlot(playerIn, stack);
/*     */     }
/*     */     
/*     */     public boolean isItemValid(ItemStack stack) {
/* 781 */       return this.slot.isItemValid(stack);
/*     */     }
/*     */     
/*     */     public ItemStack getStack() {
/* 785 */       return this.slot.getStack();
/*     */     }
/*     */     
/*     */     public boolean getHasStack() {
/* 789 */       return this.slot.getHasStack();
/*     */     }
/*     */     
/*     */     public void putStack(ItemStack stack) {
/* 793 */       this.slot.putStack(stack);
/*     */     }
/*     */     
/*     */     public void onSlotChanged() {
/* 797 */       this.slot.onSlotChanged();
/*     */     }
/*     */     
/*     */     public int getSlotStackLimit() {
/* 801 */       return this.slot.getSlotStackLimit();
/*     */     }
/*     */     
/*     */     public int getItemStackLimit(ItemStack stack) {
/* 805 */       return this.slot.getItemStackLimit(stack);
/*     */     }
/*     */     
/*     */     public String getSlotTexture() {
/* 809 */       return this.slot.getSlotTexture();
/*     */     }
/*     */     
/*     */     public ItemStack decrStackSize(int amount) {
/* 813 */       return this.slot.decrStackSize(amount);
/*     */     }
/*     */     
/*     */     public boolean isHere(IInventory inv, int slotIn) {
/* 817 */       return this.slot.isHere(inv, slotIn);
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\minecraft\client\gui\inventory\GuiContainerCreative.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */