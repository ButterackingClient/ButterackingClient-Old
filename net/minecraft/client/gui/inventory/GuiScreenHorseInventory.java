/*    */ package net.minecraft.client.gui.inventory;
/*    */ import net.minecraft.client.Minecraft;
/*    */ import net.minecraft.entity.passive.EntityHorse;
/*    */ import net.minecraft.entity.player.EntityPlayer;
/*    */ import net.minecraft.inventory.Container;
/*    */ import net.minecraft.inventory.ContainerHorseInventory;
/*    */ import net.minecraft.inventory.IInventory;
/*    */ import net.minecraft.util.ResourceLocation;
/*    */ 
/*    */ public class GuiScreenHorseInventory extends GuiContainer {
/* 11 */   private static final ResourceLocation horseGuiTextures = new ResourceLocation("textures/gui/container/horse.png");
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   private IInventory playerInventory;
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   private IInventory horseInventory;
/*    */ 
/*    */ 
/*    */   
/*    */   private EntityHorse horseEntity;
/*    */ 
/*    */ 
/*    */   
/*    */   private float mousePosx;
/*    */ 
/*    */ 
/*    */   
/*    */   private float mousePosY;
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public GuiScreenHorseInventory(IInventory playerInv, IInventory horseInv, EntityHorse horse) {
/* 39 */     super((Container)new ContainerHorseInventory(playerInv, horseInv, horse, (EntityPlayer)(Minecraft.getMinecraft()).thePlayer));
/* 40 */     this.playerInventory = playerInv;
/* 41 */     this.horseInventory = horseInv;
/* 42 */     this.horseEntity = horse;
/* 43 */     this.allowUserInput = false;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY) {
/* 50 */     this.fontRendererObj.drawString(this.horseInventory.getDisplayName().getUnformattedText(), 8, 6, 4210752);
/* 51 */     this.fontRendererObj.drawString(this.playerInventory.getDisplayName().getUnformattedText(), 8, this.ySize - 96 + 2, 4210752);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY) {
/* 58 */     GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
/* 59 */     this.mc.getTextureManager().bindTexture(horseGuiTextures);
/* 60 */     int i = (width - this.xSize) / 2;
/* 61 */     int j = (height - this.ySize) / 2;
/* 62 */     drawTexturedModalRect(i, j, 0, 0, this.xSize, this.ySize);
/*    */     
/* 64 */     if (this.horseEntity.isChested()) {
/* 65 */       drawTexturedModalRect(i + 79, j + 17, 0, this.ySize, 90, 54);
/*    */     }
/*    */     
/* 68 */     if (this.horseEntity.canWearArmor()) {
/* 69 */       drawTexturedModalRect(i + 7, j + 35, 0, this.ySize + 54, 18, 18);
/*    */     }
/*    */     
/* 72 */     GuiInventory.drawEntityOnScreen(i + 51, j + 60, 17, (i + 51) - this.mousePosx, (j + 75 - 50) - this.mousePosY, (EntityLivingBase)this.horseEntity);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void drawScreen(int mouseX, int mouseY, float partialTicks) {
/* 79 */     this.mousePosx = mouseX;
/* 80 */     this.mousePosY = mouseY;
/* 81 */     super.drawScreen(mouseX, mouseY, partialTicks);
/*    */   }
/*    */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\minecraft\client\gui\inventory\GuiScreenHorseInventory.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */