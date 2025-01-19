/*     */ package net.minecraft.client.renderer.entity.layers;
/*     */ 
/*     */ import client.Client;
/*     */ import net.minecraft.client.Minecraft;
/*     */ import net.minecraft.client.entity.AbstractClientPlayer;
/*     */ import net.minecraft.client.renderer.GlStateManager;
/*     */ import net.minecraft.client.renderer.entity.RenderPlayer;
/*     */ import net.minecraft.entity.EntityLivingBase;
/*     */ import net.minecraft.entity.player.EnumPlayerModelParts;
/*     */ import net.minecraft.util.MathHelper;
/*     */ import net.minecraft.util.ResourceLocation;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class LayerCape
/*     */   implements LayerRenderer<AbstractClientPlayer>
/*     */ {
/*     */   private final RenderPlayer playerRenderer;
/*     */   
/*     */   public LayerCape(RenderPlayer playerRendererIn) {
/*  90 */     this.playerRenderer = playerRendererIn;
/*     */   }
/*     */ 
/*     */   
/*     */   public void doRenderLayer(AbstractClientPlayer entitylivingbaseIn, float p_177141_2_, float p_177141_3_, float partialTicks, float p_177141_5_, float p_177141_6_, float p_177141_7_, float scale) {
/*  95 */     if (entitylivingbaseIn.hasPlayerInfo() && !entitylivingbaseIn.isInvisible() && entitylivingbaseIn.isWearing(EnumPlayerModelParts.CAPE) && entitylivingbaseIn.getLocationCape() != null) {
/*  96 */       GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
/*  97 */       this.playerRenderer.bindTexture(entitylivingbaseIn.getLocationCape());
/*  98 */       GlStateManager.pushMatrix();
/*  99 */       GlStateManager.translate(0.0F, 0.0F, 0.125F);
/* 100 */       double d0 = entitylivingbaseIn.prevChasingPosX + (entitylivingbaseIn.chasingPosX - entitylivingbaseIn.prevChasingPosX) * partialTicks - entitylivingbaseIn.prevPosX + (entitylivingbaseIn.posX - entitylivingbaseIn.prevPosX) * partialTicks;
/* 101 */       double d2 = entitylivingbaseIn.prevChasingPosY + (entitylivingbaseIn.chasingPosY - entitylivingbaseIn.prevChasingPosY) * partialTicks - entitylivingbaseIn.prevPosY + (entitylivingbaseIn.posY - entitylivingbaseIn.prevPosY) * partialTicks;
/* 102 */       double d3 = entitylivingbaseIn.prevChasingPosZ + (entitylivingbaseIn.chasingPosZ - entitylivingbaseIn.prevChasingPosZ) * partialTicks - entitylivingbaseIn.prevPosZ + (entitylivingbaseIn.posZ - entitylivingbaseIn.prevPosZ) * partialTicks;
/* 103 */       float f = entitylivingbaseIn.prevRenderYawOffset + (entitylivingbaseIn.renderYawOffset - entitylivingbaseIn.prevRenderYawOffset) * partialTicks;
/* 104 */       double d4 = MathHelper.sin(f * 3.1415927F / 180.0F);
/* 105 */       double d5 = -MathHelper.cos(f * 3.1415927F / 180.0F);
/* 106 */       float f2 = (float)d2 * 10.0F;
/* 107 */       f2 = MathHelper.clamp_float(f2, -6.0F, 32.0F);
/* 108 */       float f3 = (float)(d0 * d4 + d3 * d5) * 100.0F;
/* 109 */       float f4 = (float)(d0 * d5 - d3 * d4) * 100.0F;
/* 110 */       if (f3 < 0.0F) {
/* 111 */         f3 = 0.0F;
/*     */       }
/* 113 */       if (f3 > 165.0F) {
/* 114 */         f3 = 165.0F;
/*     */       }
/* 116 */       if (f2 < -5.0F) {
/* 117 */         f2 = -5.0F;
/*     */       }
/* 119 */       float f5 = entitylivingbaseIn.prevCameraYaw + (entitylivingbaseIn.cameraYaw - entitylivingbaseIn.prevCameraYaw) * partialTicks;
/* 120 */       f2 += MathHelper.sin((entitylivingbaseIn.prevDistanceWalkedModified + (entitylivingbaseIn.distanceWalkedModified - entitylivingbaseIn.prevDistanceWalkedModified) * partialTicks) * 6.0F) * 32.0F * f5;
/* 121 */       if (entitylivingbaseIn.isSneaking()) {
/* 122 */         f2 += 25.0F;
/* 123 */         GlStateManager.translate(0.0F, 0.142F, -0.0178F);
/*     */       } 
/* 125 */       GlStateManager.rotate(6.0F + f3 / 2.0F + f2, 1.0F, 0.0F, 0.0F);
/* 126 */       GlStateManager.rotate(f4 / 2.0F, 0.0F, 0.0F, 1.0F);
/* 127 */       GlStateManager.rotate(-f4 / 2.0F, 0.0F, 1.0F, 0.0F);
/* 128 */       GlStateManager.rotate(180.0F, 0.0F, 1.0F, 0.0F);
/* 129 */       this.playerRenderer.getMainModel().renderCape(0.0625F);
/* 130 */       GlStateManager.popMatrix();
/*     */     } 
/* 132 */     if ((Client.getInstance()).hudManager.cape.isEnabled() && entitylivingbaseIn.hasPlayerInfo() && !entitylivingbaseIn.isInvisible() && entitylivingbaseIn.isWearing(EnumPlayerModelParts.CAPE) && entitylivingbaseIn.getName().equals(Minecraft.getMinecraft().getSession().getUsername())) {
/* 133 */       GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
/* 134 */       this.playerRenderer.bindTexture(new ResourceLocation("client/cape.png"));
/* 135 */       GlStateManager.pushMatrix();
/* 136 */       GlStateManager.translate(0.0F, 0.0F, 0.125F);
/* 137 */       double d0 = entitylivingbaseIn.prevChasingPosX + (entitylivingbaseIn.chasingPosX - entitylivingbaseIn.prevChasingPosX) * partialTicks - entitylivingbaseIn.prevPosX + (entitylivingbaseIn.posX - entitylivingbaseIn.prevPosX) * partialTicks;
/* 138 */       double d2 = entitylivingbaseIn.prevChasingPosY + (entitylivingbaseIn.chasingPosY - entitylivingbaseIn.prevChasingPosY) * partialTicks - entitylivingbaseIn.prevPosY + (entitylivingbaseIn.posY - entitylivingbaseIn.prevPosY) * partialTicks;
/* 139 */       double d3 = entitylivingbaseIn.prevChasingPosZ + (entitylivingbaseIn.chasingPosZ - entitylivingbaseIn.prevChasingPosZ) * partialTicks - entitylivingbaseIn.prevPosZ + (entitylivingbaseIn.posZ - entitylivingbaseIn.prevPosZ) * partialTicks;
/* 140 */       float f = entitylivingbaseIn.prevRenderYawOffset + (entitylivingbaseIn.renderYawOffset - entitylivingbaseIn.prevRenderYawOffset) * partialTicks;
/* 141 */       double d4 = MathHelper.sin(f * 3.1415927F / 180.0F);
/* 142 */       double d5 = -MathHelper.cos(f * 3.1415927F / 180.0F);
/* 143 */       float f2 = (float)d2 * 10.0F;
/* 144 */       f2 = MathHelper.clamp_float(f2, -6.0F, 32.0F);
/* 145 */       float f3 = (float)(d0 * d4 + d3 * d5) * 100.0F;
/* 146 */       float f4 = (float)(d0 * d5 - d3 * d4) * 100.0F;
/* 147 */       if (f3 < 0.0F) {
/* 148 */         f3 = 0.0F;
/*     */       }
/* 150 */       if (f3 > 165.0F) {
/* 151 */         f3 = 165.0F;
/*     */       }
/* 153 */       if (f2 < -5.0F) {
/* 154 */         f2 = -5.0F;
/*     */       }
/* 156 */       float f5 = entitylivingbaseIn.prevCameraYaw + (entitylivingbaseIn.cameraYaw - entitylivingbaseIn.prevCameraYaw) * partialTicks;
/* 157 */       f2 += MathHelper.sin((entitylivingbaseIn.prevDistanceWalkedModified + (entitylivingbaseIn.distanceWalkedModified - entitylivingbaseIn.prevDistanceWalkedModified) * partialTicks) * 6.0F) * 32.0F * f5;
/* 158 */       if (entitylivingbaseIn.isSneaking()) {
/* 159 */         f2 += 25.0F;
/* 160 */         GlStateManager.translate(0.0F, 0.142F, -0.0178F);
/*     */       } 
/* 162 */       GlStateManager.rotate(6.0F + f3 / 2.0F + f2, 1.0F, 0.0F, 0.0F);
/* 163 */       GlStateManager.rotate(f4 / 2.0F, 0.0F, 0.0F, 1.0F);
/* 164 */       GlStateManager.rotate(-f4 / 2.0F, 0.0F, 1.0F, 0.0F);
/* 165 */       GlStateManager.rotate(180.0F, 0.0F, 1.0F, 0.0F);
/* 166 */       this.playerRenderer.getMainModel().renderCape(0.0625F);
/* 167 */       GlStateManager.popMatrix();
/*     */     } 
/* 169 */     if (!(Client.getInstance()).hudManager.cape.isEnabled() && (Client.getInstance()).hudManager.cape2.isEnabled() && entitylivingbaseIn.hasPlayerInfo() && !entitylivingbaseIn.isInvisible() && entitylivingbaseIn.isWearing(EnumPlayerModelParts.CAPE) && entitylivingbaseIn.getName().equals(Minecraft.getMinecraft().getSession().getUsername())) {
/* 170 */       GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
/* 171 */       this.playerRenderer.bindTexture(new ResourceLocation("client/cape2.png"));
/* 172 */       GlStateManager.pushMatrix();
/* 173 */       GlStateManager.translate(0.0F, 0.0F, 0.125F);
/* 174 */       double d0 = entitylivingbaseIn.prevChasingPosX + (entitylivingbaseIn.chasingPosX - entitylivingbaseIn.prevChasingPosX) * partialTicks - entitylivingbaseIn.prevPosX + (entitylivingbaseIn.posX - entitylivingbaseIn.prevPosX) * partialTicks;
/* 175 */       double d2 = entitylivingbaseIn.prevChasingPosY + (entitylivingbaseIn.chasingPosY - entitylivingbaseIn.prevChasingPosY) * partialTicks - entitylivingbaseIn.prevPosY + (entitylivingbaseIn.posY - entitylivingbaseIn.prevPosY) * partialTicks;
/* 176 */       double d3 = entitylivingbaseIn.prevChasingPosZ + (entitylivingbaseIn.chasingPosZ - entitylivingbaseIn.prevChasingPosZ) * partialTicks - entitylivingbaseIn.prevPosZ + (entitylivingbaseIn.posZ - entitylivingbaseIn.prevPosZ) * partialTicks;
/* 177 */       float f = entitylivingbaseIn.prevRenderYawOffset + (entitylivingbaseIn.renderYawOffset - entitylivingbaseIn.prevRenderYawOffset) * partialTicks;
/* 178 */       double d4 = MathHelper.sin(f * 3.1415927F / 180.0F);
/* 179 */       double d5 = -MathHelper.cos(f * 3.1415927F / 180.0F);
/* 180 */       float f2 = (float)d2 * 10.0F;
/* 181 */       f2 = MathHelper.clamp_float(f2, -6.0F, 32.0F);
/* 182 */       float f3 = (float)(d0 * d4 + d3 * d5) * 100.0F;
/* 183 */       float f4 = (float)(d0 * d5 - d3 * d4) * 100.0F;
/* 184 */       if (f3 < 0.0F) {
/* 185 */         f3 = 0.0F;
/*     */       }
/* 187 */       if (f3 > 165.0F) {
/* 188 */         f3 = 165.0F;
/*     */       }
/* 190 */       if (f2 < -5.0F) {
/* 191 */         f2 = -5.0F;
/*     */       }
/* 193 */       float f5 = entitylivingbaseIn.prevCameraYaw + (entitylivingbaseIn.cameraYaw - entitylivingbaseIn.prevCameraYaw) * partialTicks;
/* 194 */       f2 += MathHelper.sin((entitylivingbaseIn.prevDistanceWalkedModified + (entitylivingbaseIn.distanceWalkedModified - entitylivingbaseIn.prevDistanceWalkedModified) * partialTicks) * 6.0F) * 32.0F * f5;
/* 195 */       if (entitylivingbaseIn.isSneaking()) {
/* 196 */         f2 += 25.0F;
/* 197 */         GlStateManager.translate(0.0F, 0.142F, -0.0178F);
/*     */       } 
/* 199 */       GlStateManager.rotate(6.0F + f3 / 2.0F + f2, 1.0F, 0.0F, 0.0F);
/* 200 */       GlStateManager.rotate(f4 / 2.0F, 0.0F, 0.0F, 1.0F);
/* 201 */       GlStateManager.rotate(-f4 / 2.0F, 0.0F, 1.0F, 0.0F);
/* 202 */       GlStateManager.rotate(180.0F, 0.0F, 1.0F, 0.0F);
/* 203 */       this.playerRenderer.getMainModel().renderCape(0.0625F);
/* 204 */       GlStateManager.popMatrix();
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean shouldCombineTextures() {
/* 213 */     return (Client.getInstance()).hudManager.hitColor.isEnabled();
/*     */   }
/*     */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\minecraft\client\renderer\entity\layers\LayerCape.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */