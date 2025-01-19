/*    */ package net.minecraft.client.particle;
/*    */ 
/*    */ import net.minecraft.client.Minecraft;
/*    */ import net.minecraft.client.renderer.GlStateManager;
/*    */ import net.minecraft.client.renderer.RenderHelper;
/*    */ import net.minecraft.client.renderer.Tessellator;
/*    */ import net.minecraft.client.renderer.WorldRenderer;
/*    */ import net.minecraft.client.renderer.texture.TextureManager;
/*    */ import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
/*    */ import net.minecraft.client.renderer.vertex.VertexFormat;
/*    */ import net.minecraft.entity.Entity;
/*    */ import net.minecraft.util.ResourceLocation;
/*    */ import net.minecraft.world.World;
/*    */ 
/*    */ public class EntityLargeExplodeFX extends EntityFX {
/* 16 */   private static final ResourceLocation EXPLOSION_TEXTURE = new ResourceLocation("textures/entity/explosion.png");
/* 17 */   private static final VertexFormat field_181549_az = (new VertexFormat()).addElement(DefaultVertexFormats.POSITION_3F).addElement(DefaultVertexFormats.TEX_2F).addElement(DefaultVertexFormats.COLOR_4UB).addElement(DefaultVertexFormats.TEX_2S).addElement(DefaultVertexFormats.NORMAL_3B).addElement(DefaultVertexFormats.PADDING_1B);
/*    */   
/*    */   private int field_70581_a;
/*    */   
/*    */   private int field_70584_aq;
/*    */   
/*    */   private TextureManager theRenderEngine;
/*    */   
/*    */   private float field_70582_as;
/*    */   
/*    */   protected EntityLargeExplodeFX(TextureManager renderEngine, World worldIn, double xCoordIn, double yCoordIn, double zCoordIn, double p_i1213_9_, double p_i1213_11_, double p_i1213_13_) {
/* 28 */     super(worldIn, xCoordIn, yCoordIn, zCoordIn, 0.0D, 0.0D, 0.0D);
/* 29 */     this.theRenderEngine = renderEngine;
/* 30 */     this.field_70584_aq = 6 + this.rand.nextInt(4);
/* 31 */     this.particleRed = this.particleGreen = this.particleBlue = this.rand.nextFloat() * 0.6F + 0.4F;
/* 32 */     this.field_70582_as = 1.0F - (float)p_i1213_9_ * 0.5F;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void renderParticle(WorldRenderer worldRendererIn, Entity entityIn, float partialTicks, float rotationX, float rotationZ, float rotationYZ, float rotationXY, float rotationXZ) {
/* 39 */     int i = (int)((this.field_70581_a + partialTicks) * 15.0F / this.field_70584_aq);
/*    */     
/* 41 */     if (i <= 15) {
/* 42 */       this.theRenderEngine.bindTexture(EXPLOSION_TEXTURE);
/* 43 */       float f = (i % 4) / 4.0F;
/* 44 */       float f1 = f + 0.24975F;
/* 45 */       float f2 = (i / 4) / 4.0F;
/* 46 */       float f3 = f2 + 0.24975F;
/* 47 */       float f4 = 2.0F * this.field_70582_as;
/* 48 */       float f5 = (float)(this.prevPosX + (this.posX - this.prevPosX) * partialTicks - interpPosX);
/* 49 */       float f6 = (float)(this.prevPosY + (this.posY - this.prevPosY) * partialTicks - interpPosY);
/* 50 */       float f7 = (float)(this.prevPosZ + (this.posZ - this.prevPosZ) * partialTicks - interpPosZ);
/* 51 */       GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
/* 52 */       GlStateManager.disableLighting();
/* 53 */       RenderHelper.disableStandardItemLighting();
/* 54 */       worldRendererIn.begin(7, field_181549_az);
/* 55 */       worldRendererIn.pos((f5 - rotationX * f4 - rotationXY * f4), (f6 - rotationZ * f4), (f7 - rotationYZ * f4 - rotationXZ * f4)).tex(f1, f3).color(this.particleRed, this.particleGreen, this.particleBlue, 1.0F).lightmap(0, 240).normal(0.0F, 1.0F, 0.0F).endVertex();
/* 56 */       worldRendererIn.pos((f5 - rotationX * f4 + rotationXY * f4), (f6 + rotationZ * f4), (f7 - rotationYZ * f4 + rotationXZ * f4)).tex(f1, f2).color(this.particleRed, this.particleGreen, this.particleBlue, 1.0F).lightmap(0, 240).normal(0.0F, 1.0F, 0.0F).endVertex();
/* 57 */       worldRendererIn.pos((f5 + rotationX * f4 + rotationXY * f4), (f6 + rotationZ * f4), (f7 + rotationYZ * f4 + rotationXZ * f4)).tex(f, f2).color(this.particleRed, this.particleGreen, this.particleBlue, 1.0F).lightmap(0, 240).normal(0.0F, 1.0F, 0.0F).endVertex();
/* 58 */       worldRendererIn.pos((f5 + rotationX * f4 - rotationXY * f4), (f6 - rotationZ * f4), (f7 + rotationYZ * f4 - rotationXZ * f4)).tex(f, f3).color(this.particleRed, this.particleGreen, this.particleBlue, 1.0F).lightmap(0, 240).normal(0.0F, 1.0F, 0.0F).endVertex();
/* 59 */       Tessellator.getInstance().draw();
/* 60 */       GlStateManager.enableLighting();
/*    */     } 
/*    */   }
/*    */   
/*    */   public int getBrightnessForRender(float partialTicks) {
/* 65 */     return 61680;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void onUpdate() {
/* 72 */     this.prevPosX = this.posX;
/* 73 */     this.prevPosY = this.posY;
/* 74 */     this.prevPosZ = this.posZ;
/* 75 */     this.field_70581_a++;
/*    */     
/* 77 */     if (this.field_70581_a == this.field_70584_aq) {
/* 78 */       setDead();
/*    */     }
/*    */   }
/*    */   
/*    */   public int getFXLayer() {
/* 83 */     return 3;
/*    */   }
/*    */   
/*    */   public static class Factory implements IParticleFactory {
/*    */     public EntityFX getEntityFX(int particleID, World worldIn, double xCoordIn, double yCoordIn, double zCoordIn, double xSpeedIn, double ySpeedIn, double zSpeedIn, int... p_178902_15_) {
/* 88 */       return new EntityLargeExplodeFX(Minecraft.getMinecraft().getTextureManager(), worldIn, xCoordIn, yCoordIn, zCoordIn, xSpeedIn, ySpeedIn, zSpeedIn);
/*    */     }
/*    */   }
/*    */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\minecraft\client\particle\EntityLargeExplodeFX.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */