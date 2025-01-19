/*     */ package net.minecraft.client.gui.inventory;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import net.minecraft.client.Minecraft;
/*     */ import net.minecraft.client.gui.GuiButton;
/*     */ import net.minecraft.client.gui.GuiScreen;
/*     */ import net.minecraft.client.gui.achievement.GuiAchievements;
/*     */ import net.minecraft.client.gui.achievement.GuiStats;
/*     */ import net.minecraft.client.renderer.GlStateManager;
/*     */ import net.minecraft.client.renderer.InventoryEffectRenderer;
/*     */ import net.minecraft.client.renderer.OpenGlHelper;
/*     */ import net.minecraft.client.renderer.RenderHelper;
/*     */ import net.minecraft.client.renderer.entity.RenderManager;
/*     */ import net.minecraft.client.resources.I18n;
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.entity.EntityLivingBase;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class GuiInventory
/*     */   extends InventoryEffectRenderer
/*     */ {
/*     */   private float oldMouseX;
/*     */   private float oldMouseY;
/*     */   
/*     */   public GuiInventory(EntityPlayer p_i1094_1_) {
/*  31 */     super(p_i1094_1_.inventoryContainer);
/*  32 */     this.allowUserInput = true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void updateScreen() {
/*  39 */     if (this.mc.playerController.isInCreativeMode()) {
/*  40 */       this.mc.displayGuiScreen((GuiScreen)new GuiContainerCreative((EntityPlayer)this.mc.thePlayer));
/*     */     }
/*     */     
/*  43 */     updateActivePotionEffects();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void initGui() {
/*  51 */     this.buttonList.clear();
/*     */     
/*  53 */     if (this.mc.playerController.isInCreativeMode()) {
/*  54 */       this.mc.displayGuiScreen((GuiScreen)new GuiContainerCreative((EntityPlayer)this.mc.thePlayer));
/*     */     } else {
/*  56 */       super.initGui();
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY) {
/*  65 */     this.fontRendererObj.drawString(I18n.format("container.crafting", new Object[0]), 86, 16, 4210752);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void drawScreen(int mouseX, int mouseY, float partialTicks) {
/*  72 */     super.drawScreen(mouseX, mouseY, partialTicks);
/*  73 */     this.oldMouseX = mouseX;
/*  74 */     this.oldMouseY = mouseY;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY) {
/*  81 */     GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
/*  82 */     this.mc.getTextureManager().bindTexture(inventoryBackground);
/*  83 */     int i = this.guiLeft;
/*  84 */     int j = this.guiTop;
/*  85 */     drawTexturedModalRect(i, j, 0, 0, this.xSize, this.ySize);
/*  86 */     drawEntityOnScreen(i + 51, j + 75, 30, (i + 51) - this.oldMouseX, (j + 75 - 50) - this.oldMouseY, (EntityLivingBase)this.mc.thePlayer);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void drawEntityOnScreen(int posX, int posY, int scale, float mouseX, float mouseY, EntityLivingBase ent) {
/*  93 */     GlStateManager.enableColorMaterial();
/*  94 */     GlStateManager.pushMatrix();
/*  95 */     GlStateManager.translate(posX, posY, 50.0F);
/*  96 */     GlStateManager.scale(-scale, scale, scale);
/*  97 */     GlStateManager.rotate(180.0F, 0.0F, 0.0F, 1.0F);
/*  98 */     float f = ent.renderYawOffset;
/*  99 */     float f1 = ent.rotationYaw;
/* 100 */     float f2 = ent.rotationPitch;
/* 101 */     float f3 = ent.prevRotationYawHead;
/* 102 */     float f4 = ent.rotationYawHead;
/* 103 */     GlStateManager.rotate(135.0F, 0.0F, 1.0F, 0.0F);
/* 104 */     RenderHelper.enableStandardItemLighting();
/* 105 */     GlStateManager.rotate(-135.0F, 0.0F, 1.0F, 0.0F);
/* 106 */     GlStateManager.rotate(-((float)Math.atan((mouseY / 40.0F))) * 20.0F, 1.0F, 0.0F, 0.0F);
/* 107 */     ent.renderYawOffset = (float)Math.atan((mouseX / 40.0F)) * 20.0F;
/* 108 */     ent.rotationYaw = (float)Math.atan((mouseX / 40.0F)) * 40.0F;
/* 109 */     ent.rotationPitch = -((float)Math.atan((mouseY / 40.0F))) * 20.0F;
/* 110 */     ent.rotationYawHead = ent.rotationYaw;
/* 111 */     ent.prevRotationYawHead = ent.rotationYaw;
/* 112 */     GlStateManager.translate(0.0F, 0.0F, 0.0F);
/* 113 */     RenderManager rendermanager = Minecraft.getMinecraft().getRenderManager();
/* 114 */     rendermanager.setPlayerViewY(180.0F);
/* 115 */     rendermanager.setRenderShadow(false);
/* 116 */     rendermanager.renderEntityWithPosYaw((Entity)ent, 0.0D, 0.0D, 0.0D, 0.0F, 1.0F);
/* 117 */     rendermanager.setRenderShadow(true);
/* 118 */     ent.renderYawOffset = f;
/* 119 */     ent.rotationYaw = f1;
/* 120 */     ent.rotationPitch = f2;
/* 121 */     ent.prevRotationYawHead = f3;
/* 122 */     ent.rotationYawHead = f4;
/* 123 */     GlStateManager.popMatrix();
/* 124 */     RenderHelper.disableStandardItemLighting();
/* 125 */     GlStateManager.disableRescaleNormal();
/* 126 */     GlStateManager.setActiveTexture(OpenGlHelper.lightmapTexUnit);
/* 127 */     GlStateManager.disableTexture2D();
/* 128 */     GlStateManager.setActiveTexture(OpenGlHelper.defaultTexUnit);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void actionPerformed(GuiButton button) throws IOException {
/* 135 */     if (button.id == 0) {
/* 136 */       this.mc.displayGuiScreen((GuiScreen)new GuiAchievements((GuiScreen)this, this.mc.thePlayer.getStatFileWriter()));
/*     */     }
/*     */     
/* 139 */     if (button.id == 1)
/* 140 */       this.mc.displayGuiScreen((GuiScreen)new GuiStats((GuiScreen)this, this.mc.thePlayer.getStatFileWriter())); 
/*     */   }
/*     */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\minecraft\client\gui\inventory\GuiInventory.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */