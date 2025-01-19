/*    */ package net.minecraft.client.gui.inventory;
/*    */ import net.minecraft.client.renderer.GlStateManager;
/*    */ import net.minecraft.entity.player.InventoryPlayer;
/*    */ import net.minecraft.inventory.Container;
/*    */ import net.minecraft.inventory.ContainerFurnace;
/*    */ import net.minecraft.inventory.IInventory;
/*    */ import net.minecraft.tileentity.TileEntityFurnace;
/*    */ import net.minecraft.util.ResourceLocation;
/*    */ 
/*    */ public class GuiFurnace extends GuiContainer {
/* 11 */   private static final ResourceLocation furnaceGuiTextures = new ResourceLocation("textures/gui/container/furnace.png");
/*    */ 
/*    */   
/*    */   private final InventoryPlayer playerInventory;
/*    */   
/*    */   private IInventory tileFurnace;
/*    */ 
/*    */   
/*    */   public GuiFurnace(InventoryPlayer playerInv, IInventory furnaceInv) {
/* 20 */     super((Container)new ContainerFurnace(playerInv, furnaceInv));
/* 21 */     this.playerInventory = playerInv;
/* 22 */     this.tileFurnace = furnaceInv;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY) {
/* 29 */     String s = this.tileFurnace.getDisplayName().getUnformattedText();
/* 30 */     this.fontRendererObj.drawString(s, this.xSize / 2 - this.fontRendererObj.getStringWidth(s) / 2, 6, 4210752);
/* 31 */     this.fontRendererObj.drawString(this.playerInventory.getDisplayName().getUnformattedText(), 8, this.ySize - 96 + 2, 4210752);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY) {
/* 38 */     GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
/* 39 */     this.mc.getTextureManager().bindTexture(furnaceGuiTextures);
/* 40 */     int i = (width - this.xSize) / 2;
/* 41 */     int j = (height - this.ySize) / 2;
/* 42 */     drawTexturedModalRect(i, j, 0, 0, this.xSize, this.ySize);
/*    */     
/* 44 */     if (TileEntityFurnace.isBurning(this.tileFurnace)) {
/* 45 */       int k = getBurnLeftScaled(13);
/* 46 */       drawTexturedModalRect(i + 56, j + 36 + 12 - k, 176, 12 - k, 14, k + 1);
/*    */     } 
/*    */     
/* 49 */     int l = getCookProgressScaled(24);
/* 50 */     drawTexturedModalRect(i + 79, j + 34, 176, 14, l + 1, 16);
/*    */   }
/*    */   
/*    */   private int getCookProgressScaled(int pixels) {
/* 54 */     int i = this.tileFurnace.getField(2);
/* 55 */     int j = this.tileFurnace.getField(3);
/* 56 */     return (j != 0 && i != 0) ? (i * pixels / j) : 0;
/*    */   }
/*    */   
/*    */   private int getBurnLeftScaled(int pixels) {
/* 60 */     int i = this.tileFurnace.getField(1);
/*    */     
/* 62 */     if (i == 0) {
/* 63 */       i = 200;
/*    */     }
/*    */     
/* 66 */     return this.tileFurnace.getField(0) * pixels / i;
/*    */   }
/*    */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\minecraft\client\gui\inventory\GuiFurnace.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */