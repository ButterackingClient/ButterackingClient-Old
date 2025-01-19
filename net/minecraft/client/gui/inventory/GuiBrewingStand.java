/*    */ package net.minecraft.client.gui.inventory;
/*    */ import net.minecraft.client.renderer.GlStateManager;
/*    */ import net.minecraft.entity.player.InventoryPlayer;
/*    */ import net.minecraft.inventory.Container;
/*    */ import net.minecraft.inventory.ContainerBrewingStand;
/*    */ import net.minecraft.inventory.IInventory;
/*    */ import net.minecraft.util.ResourceLocation;
/*    */ 
/*    */ public class GuiBrewingStand extends GuiContainer {
/* 10 */   private static final ResourceLocation brewingStandGuiTextures = new ResourceLocation("textures/gui/container/brewing_stand.png");
/*    */ 
/*    */   
/*    */   private final InventoryPlayer playerInventory;
/*    */   
/*    */   private IInventory tileBrewingStand;
/*    */ 
/*    */   
/*    */   public GuiBrewingStand(InventoryPlayer playerInv, IInventory p_i45506_2_) {
/* 19 */     super((Container)new ContainerBrewingStand(playerInv, p_i45506_2_));
/* 20 */     this.playerInventory = playerInv;
/* 21 */     this.tileBrewingStand = p_i45506_2_;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY) {
/* 28 */     String s = this.tileBrewingStand.getDisplayName().getUnformattedText();
/* 29 */     this.fontRendererObj.drawString(s, this.xSize / 2 - this.fontRendererObj.getStringWidth(s) / 2, 6, 4210752);
/* 30 */     this.fontRendererObj.drawString(this.playerInventory.getDisplayName().getUnformattedText(), 8, this.ySize - 96 + 2, 4210752);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY) {
/* 37 */     GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
/* 38 */     this.mc.getTextureManager().bindTexture(brewingStandGuiTextures);
/* 39 */     int i = (width - this.xSize) / 2;
/* 40 */     int j = (height - this.ySize) / 2;
/* 41 */     drawTexturedModalRect(i, j, 0, 0, this.xSize, this.ySize);
/* 42 */     int k = this.tileBrewingStand.getField(0);
/*    */     
/* 44 */     if (k > 0) {
/* 45 */       int l = (int)(28.0F * (1.0F - k / 400.0F));
/*    */       
/* 47 */       if (l > 0) {
/* 48 */         drawTexturedModalRect(i + 97, j + 16, 176, 0, 9, l);
/*    */       }
/*    */       
/* 51 */       int i1 = k / 2 % 7;
/*    */       
/* 53 */       switch (i1) {
/*    */         case 0:
/* 55 */           l = 29;
/*    */           break;
/*    */         
/*    */         case 1:
/* 59 */           l = 24;
/*    */           break;
/*    */         
/*    */         case 2:
/* 63 */           l = 20;
/*    */           break;
/*    */         
/*    */         case 3:
/* 67 */           l = 16;
/*    */           break;
/*    */         
/*    */         case 4:
/* 71 */           l = 11;
/*    */           break;
/*    */         
/*    */         case 5:
/* 75 */           l = 6;
/*    */           break;
/*    */         
/*    */         case 6:
/* 79 */           l = 0;
/*    */           break;
/*    */       } 
/* 82 */       if (l > 0)
/* 83 */         drawTexturedModalRect(i + 65, j + 14 + 29 - l, 185, 29 - l, 12, l); 
/*    */     } 
/*    */   }
/*    */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\minecraft\client\gui\inventory\GuiBrewingStand.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */