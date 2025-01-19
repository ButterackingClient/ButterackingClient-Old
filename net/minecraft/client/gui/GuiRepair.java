/*     */ package net.minecraft.client.gui;
/*     */ 
/*     */ import io.netty.buffer.Unpooled;
/*     */ import java.io.IOException;
/*     */ import java.util.List;
/*     */ import net.minecraft.client.Minecraft;
/*     */ import net.minecraft.client.gui.inventory.GuiContainer;
/*     */ import net.minecraft.client.renderer.GlStateManager;
/*     */ import net.minecraft.client.resources.I18n;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.entity.player.InventoryPlayer;
/*     */ import net.minecraft.inventory.Container;
/*     */ import net.minecraft.inventory.ContainerRepair;
/*     */ import net.minecraft.inventory.ICrafting;
/*     */ import net.minecraft.inventory.IInventory;
/*     */ import net.minecraft.inventory.Slot;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraft.network.Packet;
/*     */ import net.minecraft.network.PacketBuffer;
/*     */ import net.minecraft.network.play.client.C17PacketCustomPayload;
/*     */ import net.minecraft.util.ResourceLocation;
/*     */ import net.minecraft.world.World;
/*     */ import org.lwjgl.input.Keyboard;
/*     */ 
/*     */ public class GuiRepair extends GuiContainer implements ICrafting {
/*  26 */   private static final ResourceLocation anvilResource = new ResourceLocation("textures/gui/container/anvil.png");
/*     */   private ContainerRepair anvil;
/*     */   private GuiTextField nameField;
/*     */   private InventoryPlayer playerInventory;
/*     */   
/*     */   public GuiRepair(InventoryPlayer inventoryIn, World worldIn) {
/*  32 */     super((Container)new ContainerRepair(inventoryIn, worldIn, (EntityPlayer)(Minecraft.getMinecraft()).thePlayer));
/*  33 */     this.playerInventory = inventoryIn;
/*  34 */     this.anvil = (ContainerRepair)this.inventorySlots;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void initGui() {
/*  42 */     super.initGui();
/*  43 */     Keyboard.enableRepeatEvents(true);
/*  44 */     int i = (width - this.xSize) / 2;
/*  45 */     int j = (height - this.ySize) / 2;
/*  46 */     this.nameField = new GuiTextField(0, this.fontRendererObj, i + 62, j + 24, 103, 12);
/*  47 */     this.nameField.setTextColor(-1);
/*  48 */     this.nameField.setDisabledTextColour(-1);
/*  49 */     this.nameField.setEnableBackgroundDrawing(false);
/*  50 */     this.nameField.setMaxStringLength(30);
/*  51 */     this.inventorySlots.removeCraftingFromCrafters(this);
/*  52 */     this.inventorySlots.onCraftGuiOpened(this);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void onGuiClosed() {
/*  59 */     super.onGuiClosed();
/*  60 */     Keyboard.enableRepeatEvents(false);
/*  61 */     this.inventorySlots.removeCraftingFromCrafters(this);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY) {
/*  68 */     GlStateManager.disableLighting();
/*  69 */     GlStateManager.disableBlend();
/*  70 */     this.fontRendererObj.drawString(I18n.format("container.repair", new Object[0]), 60, 6, 4210752);
/*     */     
/*  72 */     if (this.anvil.maximumCost > 0) {
/*  73 */       int i = 8453920;
/*  74 */       boolean flag = true;
/*  75 */       String s = I18n.format("container.repair.cost", new Object[] { Integer.valueOf(this.anvil.maximumCost) });
/*     */       
/*  77 */       if (this.anvil.maximumCost >= 40 && !this.mc.thePlayer.capabilities.isCreativeMode) {
/*  78 */         s = I18n.format("container.repair.expensive", new Object[0]);
/*  79 */         i = 16736352;
/*  80 */       } else if (!this.anvil.getSlot(2).getHasStack()) {
/*  81 */         flag = false;
/*  82 */       } else if (!this.anvil.getSlot(2).canTakeStack(this.playerInventory.player)) {
/*  83 */         i = 16736352;
/*     */       } 
/*     */       
/*  86 */       if (flag) {
/*  87 */         int j = 0xFF000000 | (i & 0xFCFCFC) >> 2 | i & 0xFF000000;
/*  88 */         int k = this.xSize - 8 - this.fontRendererObj.getStringWidth(s);
/*  89 */         int l = 67;
/*     */         
/*  91 */         if (this.fontRendererObj.getUnicodeFlag()) {
/*  92 */           drawRect(k - 3, l - 2, this.xSize - 7, l + 10, -16777216);
/*  93 */           drawRect(k - 2, l - 1, this.xSize - 8, l + 9, -12895429);
/*     */         } else {
/*  95 */           this.fontRendererObj.drawString(s, k, l + 1, j);
/*  96 */           this.fontRendererObj.drawString(s, k + 1, l, j);
/*  97 */           this.fontRendererObj.drawString(s, k + 1, l + 1, j);
/*     */         } 
/*     */         
/* 100 */         this.fontRendererObj.drawString(s, k, l, i);
/*     */       } 
/*     */     } 
/*     */     
/* 104 */     GlStateManager.enableLighting();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void keyTyped(char typedChar, int keyCode) throws IOException {
/* 112 */     if (this.nameField.textboxKeyTyped(typedChar, keyCode)) {
/* 113 */       renameItem();
/*     */     } else {
/* 115 */       super.keyTyped(typedChar, keyCode);
/*     */     } 
/*     */   }
/*     */   
/*     */   private void renameItem() {
/* 120 */     String s = this.nameField.getText();
/* 121 */     Slot slot = this.anvil.getSlot(0);
/*     */     
/* 123 */     if (slot != null && slot.getHasStack() && !slot.getStack().hasDisplayName() && s.equals(slot.getStack().getDisplayName())) {
/* 124 */       s = "";
/*     */     }
/*     */     
/* 127 */     this.anvil.updateItemName(s);
/* 128 */     this.mc.thePlayer.sendQueue.addToSendQueue((Packet)new C17PacketCustomPayload("MC|ItemName", (new PacketBuffer(Unpooled.buffer())).writeString(s)));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException {
/* 135 */     super.mouseClicked(mouseX, mouseY, mouseButton);
/* 136 */     this.nameField.mouseClicked(mouseX, mouseY, mouseButton);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void drawScreen(int mouseX, int mouseY, float partialTicks) {
/* 143 */     super.drawScreen(mouseX, mouseY, partialTicks);
/* 144 */     GlStateManager.disableLighting();
/* 145 */     GlStateManager.disableBlend();
/* 146 */     this.nameField.drawTextBox();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY) {
/* 153 */     GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
/* 154 */     this.mc.getTextureManager().bindTexture(anvilResource);
/* 155 */     int i = (width - this.xSize) / 2;
/* 156 */     int j = (height - this.ySize) / 2;
/* 157 */     drawTexturedModalRect(i, j, 0, 0, this.xSize, this.ySize);
/* 158 */     drawTexturedModalRect(i + 59, j + 20, 0, this.ySize + (this.anvil.getSlot(0).getHasStack() ? 0 : 16), 110, 16);
/*     */     
/* 160 */     if ((this.anvil.getSlot(0).getHasStack() || this.anvil.getSlot(1).getHasStack()) && !this.anvil.getSlot(2).getHasStack()) {
/* 161 */       drawTexturedModalRect(i + 99, j + 45, this.xSize, 0, 28, 21);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void updateCraftingInventory(Container containerToSend, List<ItemStack> itemsList) {
/* 169 */     sendSlotContents(containerToSend, 0, containerToSend.getSlot(0).getStack());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void sendSlotContents(Container containerToSend, int slotInd, ItemStack stack) {
/* 177 */     if (slotInd == 0) {
/* 178 */       this.nameField.setText((stack == null) ? "" : stack.getDisplayName());
/* 179 */       this.nameField.setEnabled((stack != null));
/*     */       
/* 181 */       if (stack != null)
/* 182 */         renameItem(); 
/*     */     } 
/*     */   }
/*     */   
/*     */   public void sendProgressBarUpdate(Container containerIn, int varToUpdate, int newValue) {}
/*     */   
/*     */   public void sendAllWindowProperties(Container p_175173_1_, IInventory p_175173_2_) {}
/*     */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\minecraft\client\gui\GuiRepair.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */