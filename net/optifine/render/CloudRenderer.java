/*    */ package net.optifine.render;
/*    */ 
/*    */ import net.minecraft.client.Minecraft;
/*    */ import net.minecraft.client.renderer.GLAllocation;
/*    */ import net.minecraft.client.renderer.GlStateManager;
/*    */ import net.minecraft.entity.Entity;
/*    */ import net.minecraft.util.Vec3;
/*    */ import org.lwjgl.opengl.GL11;
/*    */ 
/*    */ public class CloudRenderer {
/*    */   private Minecraft mc;
/*    */   private boolean updated = false;
/*    */   private boolean renderFancy = false;
/*    */   int cloudTickCounter;
/*    */   private Vec3 cloudColor;
/*    */   float partialTicks;
/*    */   private boolean updateRenderFancy = false;
/* 18 */   private int updateCloudTickCounter = 0;
/* 19 */   private Vec3 updateCloudColor = new Vec3(-1.0D, -1.0D, -1.0D);
/* 20 */   private double updatePlayerX = 0.0D;
/* 21 */   private double updatePlayerY = 0.0D;
/* 22 */   private double updatePlayerZ = 0.0D;
/* 23 */   private int glListClouds = -1;
/*    */   
/*    */   public CloudRenderer(Minecraft mc) {
/* 26 */     this.mc = mc;
/* 27 */     this.glListClouds = GLAllocation.generateDisplayLists(1);
/*    */   }
/*    */   
/*    */   public void prepareToRender(boolean renderFancy, int cloudTickCounter, float partialTicks, Vec3 cloudColor) {
/* 31 */     this.renderFancy = renderFancy;
/* 32 */     this.cloudTickCounter = cloudTickCounter;
/* 33 */     this.partialTicks = partialTicks;
/* 34 */     this.cloudColor = cloudColor;
/*    */   }
/*    */   
/*    */   public boolean shouldUpdateGlList() {
/* 38 */     if (!this.updated)
/* 39 */       return true; 
/* 40 */     if (this.renderFancy != this.updateRenderFancy)
/* 41 */       return true; 
/* 42 */     if (this.cloudTickCounter >= this.updateCloudTickCounter + 20)
/* 43 */       return true; 
/* 44 */     if (Math.abs(this.cloudColor.xCoord - this.updateCloudColor.xCoord) > 0.003D)
/* 45 */       return true; 
/* 46 */     if (Math.abs(this.cloudColor.yCoord - this.updateCloudColor.yCoord) > 0.003D)
/* 47 */       return true; 
/* 48 */     if (Math.abs(this.cloudColor.zCoord - this.updateCloudColor.zCoord) > 0.003D) {
/* 49 */       return true;
/*    */     }
/* 51 */     Entity entity = this.mc.getRenderViewEntity();
/* 52 */     boolean flag = (this.updatePlayerY + entity.getEyeHeight() < 128.0D + (this.mc.gameSettings.ofCloudsHeight * 128.0F));
/* 53 */     boolean flag1 = (entity.prevPosY + entity.getEyeHeight() < 128.0D + (this.mc.gameSettings.ofCloudsHeight * 128.0F));
/* 54 */     return flag1 ^ flag;
/*    */   }
/*    */ 
/*    */   
/*    */   public void startUpdateGlList() {
/* 59 */     GL11.glNewList(this.glListClouds, 4864);
/*    */   }
/*    */   
/*    */   public void endUpdateGlList() {
/* 63 */     GL11.glEndList();
/* 64 */     this.updateRenderFancy = this.renderFancy;
/* 65 */     this.updateCloudTickCounter = this.cloudTickCounter;
/* 66 */     this.updateCloudColor = this.cloudColor;
/* 67 */     this.updatePlayerX = (this.mc.getRenderViewEntity()).prevPosX;
/* 68 */     this.updatePlayerY = (this.mc.getRenderViewEntity()).prevPosY;
/* 69 */     this.updatePlayerZ = (this.mc.getRenderViewEntity()).prevPosZ;
/* 70 */     this.updated = true;
/* 71 */     GlStateManager.resetColor();
/*    */   }
/*    */   
/*    */   public void renderGlList() {
/* 75 */     Entity entity = this.mc.getRenderViewEntity();
/* 76 */     double d0 = entity.prevPosX + (entity.posX - entity.prevPosX) * this.partialTicks;
/* 77 */     double d1 = entity.prevPosY + (entity.posY - entity.prevPosY) * this.partialTicks;
/* 78 */     double d2 = entity.prevPosZ + (entity.posZ - entity.prevPosZ) * this.partialTicks;
/* 79 */     double d3 = ((this.cloudTickCounter - this.updateCloudTickCounter) + this.partialTicks);
/* 80 */     float f = (float)(d0 - this.updatePlayerX + d3 * 0.03D);
/* 81 */     float f1 = (float)(d1 - this.updatePlayerY);
/* 82 */     float f2 = (float)(d2 - this.updatePlayerZ);
/* 83 */     GlStateManager.pushMatrix();
/*    */     
/* 85 */     if (this.renderFancy) {
/* 86 */       GlStateManager.translate(-f / 12.0F, -f1, -f2 / 12.0F);
/*    */     } else {
/* 88 */       GlStateManager.translate(-f, -f1, -f2);
/*    */     } 
/*    */     
/* 91 */     GlStateManager.callList(this.glListClouds);
/* 92 */     GlStateManager.popMatrix();
/* 93 */     GlStateManager.resetColor();
/*    */   }
/*    */   
/*    */   public void reset() {
/* 97 */     this.updated = false;
/*    */   }
/*    */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\optifine\render\CloudRenderer.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */