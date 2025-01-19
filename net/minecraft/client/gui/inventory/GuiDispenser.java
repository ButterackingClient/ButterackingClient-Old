/*    */ package net.minecraft.client.gui.inventory;
/*    */ import net.minecraft.client.renderer.GlStateManager;
/*    */ import net.minecraft.entity.player.InventoryPlayer;
/*    */ import net.minecraft.inventory.Container;
/*    */ import net.minecraft.inventory.ContainerDispenser;
/*    */ import net.minecraft.inventory.IInventory;
/*    */ import net.minecraft.util.ResourceLocation;
/*    */ 
/*    */ public class GuiDispenser extends GuiContainer {
/* 10 */   private static final ResourceLocation dispenserGuiTextures = new ResourceLocation("textures/gui/container/dispenser.png");
/*    */ 
/*    */ 
/*    */   
/*    */   private final InventoryPlayer playerInventory;
/*    */ 
/*    */ 
/*    */   
/*    */   public IInventory dispenserInventory;
/*    */ 
/*    */ 
/*    */   
/*    */   public GuiDispenser(InventoryPlayer playerInv, IInventory dispenserInv) {
/* 23 */     super((Container)new ContainerDispenser((IInventory)playerInv, dispenserInv));
/* 24 */     this.playerInventory = playerInv;
/* 25 */     this.dispenserInventory = dispenserInv;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY) {
/* 32 */     String s = this.dispenserInventory.getDisplayName().getUnformattedText();
/* 33 */     this.fontRendererObj.drawString(s, this.xSize / 2 - this.fontRendererObj.getStringWidth(s) / 2, 6, 4210752);
/* 34 */     this.fontRendererObj.drawString(this.playerInventory.getDisplayName().getUnformattedText(), 8, this.ySize - 96 + 2, 4210752);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY) {
/* 41 */     GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
/* 42 */     this.mc.getTextureManager().bindTexture(dispenserGuiTextures);
/* 43 */     int i = (width - this.xSize) / 2;
/* 44 */     int j = (height - this.ySize) / 2;
/* 45 */     drawTexturedModalRect(i, j, 0, 0, this.xSize, this.ySize);
/*    */   }
/*    */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\minecraft\client\gui\inventory\GuiDispenser.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */