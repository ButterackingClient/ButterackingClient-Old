/*    */ package net.minecraft.client.gui;
/*    */ 
/*    */ import net.minecraft.client.Minecraft;
/*    */ import net.minecraft.client.gui.inventory.GuiContainer;
/*    */ import net.minecraft.client.renderer.GlStateManager;
/*    */ import net.minecraft.entity.player.EntityPlayer;
/*    */ import net.minecraft.entity.player.InventoryPlayer;
/*    */ import net.minecraft.inventory.Container;
/*    */ import net.minecraft.inventory.ContainerHopper;
/*    */ import net.minecraft.inventory.IInventory;
/*    */ import net.minecraft.util.ResourceLocation;
/*    */ 
/*    */ public class GuiHopper
/*    */   extends GuiContainer {
/* 15 */   private static final ResourceLocation HOPPER_GUI_TEXTURE = new ResourceLocation("textures/gui/container/hopper.png");
/*    */ 
/*    */ 
/*    */   
/*    */   private IInventory playerInventory;
/*    */ 
/*    */ 
/*    */   
/*    */   private IInventory hopperInventory;
/*    */ 
/*    */ 
/*    */   
/*    */   public GuiHopper(InventoryPlayer playerInv, IInventory hopperInv) {
/* 28 */     super((Container)new ContainerHopper(playerInv, hopperInv, (EntityPlayer)(Minecraft.getMinecraft()).thePlayer));
/* 29 */     this.playerInventory = (IInventory)playerInv;
/* 30 */     this.hopperInventory = hopperInv;
/* 31 */     this.allowUserInput = false;
/* 32 */     this.ySize = 133;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY) {
/* 39 */     this.fontRendererObj.drawString(this.hopperInventory.getDisplayName().getUnformattedText(), 8, 6, 4210752);
/* 40 */     this.fontRendererObj.drawString(this.playerInventory.getDisplayName().getUnformattedText(), 8, this.ySize - 96 + 2, 4210752);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY) {
/* 47 */     GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
/* 48 */     this.mc.getTextureManager().bindTexture(HOPPER_GUI_TEXTURE);
/* 49 */     int i = (width - this.xSize) / 2;
/* 50 */     int j = (height - this.ySize) / 2;
/* 51 */     drawTexturedModalRect(i, j, 0, 0, this.xSize, this.ySize);
/*    */   }
/*    */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\minecraft\client\gui\GuiHopper.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */