/*     */ package net.minecraft.client.renderer.entity;
/*     */ import net.minecraft.block.state.IBlockState;
/*     */ import net.minecraft.client.Minecraft;
/*     */ import net.minecraft.client.model.ModelBase;
/*     */ import net.minecraft.client.model.ModelMinecart;
/*     */ import net.minecraft.client.renderer.GlStateManager;
/*     */ import net.minecraft.client.renderer.texture.TextureMap;
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.entity.item.EntityMinecart;
/*     */ import net.minecraft.util.MathHelper;
/*     */ import net.minecraft.util.ResourceLocation;
/*     */ import net.minecraft.util.Vec3;
/*     */ 
/*     */ public class RenderMinecart<T extends EntityMinecart> extends Render<T> {
/*  15 */   private static final ResourceLocation minecartTextures = new ResourceLocation("textures/entity/minecart.png");
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  20 */   protected ModelBase modelMinecart = (ModelBase)new ModelMinecart();
/*     */   
/*     */   public RenderMinecart(RenderManager renderManagerIn) {
/*  23 */     super(renderManagerIn);
/*  24 */     this.shadowSize = 0.5F;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void doRender(T entity, double x, double y, double z, float entityYaw, float partialTicks) {
/*  31 */     GlStateManager.pushMatrix();
/*  32 */     bindEntityTexture(entity);
/*  33 */     long i = entity.getEntityId() * 493286711L;
/*  34 */     i = i * i * 4392167121L + i * 98761L;
/*  35 */     float f = (((float)(i >> 16L & 0x7L) + 0.5F) / 8.0F - 0.5F) * 0.004F;
/*  36 */     float f1 = (((float)(i >> 20L & 0x7L) + 0.5F) / 8.0F - 0.5F) * 0.004F;
/*  37 */     float f2 = (((float)(i >> 24L & 0x7L) + 0.5F) / 8.0F - 0.5F) * 0.004F;
/*  38 */     GlStateManager.translate(f, f1, f2);
/*  39 */     double d0 = ((EntityMinecart)entity).lastTickPosX + (((EntityMinecart)entity).posX - ((EntityMinecart)entity).lastTickPosX) * partialTicks;
/*  40 */     double d1 = ((EntityMinecart)entity).lastTickPosY + (((EntityMinecart)entity).posY - ((EntityMinecart)entity).lastTickPosY) * partialTicks;
/*  41 */     double d2 = ((EntityMinecart)entity).lastTickPosZ + (((EntityMinecart)entity).posZ - ((EntityMinecart)entity).lastTickPosZ) * partialTicks;
/*  42 */     double d3 = 0.30000001192092896D;
/*  43 */     Vec3 vec3 = entity.func_70489_a(d0, d1, d2);
/*  44 */     float f3 = ((EntityMinecart)entity).prevRotationPitch + (((EntityMinecart)entity).rotationPitch - ((EntityMinecart)entity).prevRotationPitch) * partialTicks;
/*     */     
/*  46 */     if (vec3 != null) {
/*  47 */       Vec3 vec31 = entity.func_70495_a(d0, d1, d2, d3);
/*  48 */       Vec3 vec32 = entity.func_70495_a(d0, d1, d2, -d3);
/*     */       
/*  50 */       if (vec31 == null) {
/*  51 */         vec31 = vec3;
/*     */       }
/*     */       
/*  54 */       if (vec32 == null) {
/*  55 */         vec32 = vec3;
/*     */       }
/*     */       
/*  58 */       x += vec3.xCoord - d0;
/*  59 */       y += (vec31.yCoord + vec32.yCoord) / 2.0D - d1;
/*  60 */       z += vec3.zCoord - d2;
/*  61 */       Vec3 vec33 = vec32.addVector(-vec31.xCoord, -vec31.yCoord, -vec31.zCoord);
/*     */       
/*  63 */       if (vec33.lengthVector() != 0.0D) {
/*  64 */         vec33 = vec33.normalize();
/*  65 */         entityYaw = (float)(Math.atan2(vec33.zCoord, vec33.xCoord) * 180.0D / Math.PI);
/*  66 */         f3 = (float)(Math.atan(vec33.yCoord) * 73.0D);
/*     */       } 
/*     */     } 
/*     */     
/*  70 */     GlStateManager.translate((float)x, (float)y + 0.375F, (float)z);
/*  71 */     GlStateManager.rotate(180.0F - entityYaw, 0.0F, 1.0F, 0.0F);
/*  72 */     GlStateManager.rotate(-f3, 0.0F, 0.0F, 1.0F);
/*  73 */     float f5 = entity.getRollingAmplitude() - partialTicks;
/*  74 */     float f6 = entity.getDamage() - partialTicks;
/*     */     
/*  76 */     if (f6 < 0.0F) {
/*  77 */       f6 = 0.0F;
/*     */     }
/*     */     
/*  80 */     if (f5 > 0.0F) {
/*  81 */       GlStateManager.rotate(MathHelper.sin(f5) * f5 * f6 / 10.0F * entity.getRollingDirection(), 1.0F, 0.0F, 0.0F);
/*     */     }
/*     */     
/*  84 */     int j = entity.getDisplayTileOffset();
/*  85 */     IBlockState iblockstate = entity.getDisplayTile();
/*     */     
/*  87 */     if (iblockstate.getBlock().getRenderType() != -1) {
/*  88 */       GlStateManager.pushMatrix();
/*  89 */       bindTexture(TextureMap.locationBlocksTexture);
/*  90 */       float f4 = 0.75F;
/*  91 */       GlStateManager.scale(f4, f4, f4);
/*  92 */       GlStateManager.translate(-0.5F, (j - 8) / 16.0F, 0.5F);
/*  93 */       func_180560_a(entity, partialTicks, iblockstate);
/*  94 */       GlStateManager.popMatrix();
/*  95 */       GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
/*  96 */       bindEntityTexture(entity);
/*     */     } 
/*     */     
/*  99 */     GlStateManager.scale(-1.0F, -1.0F, 1.0F);
/* 100 */     this.modelMinecart.render((Entity)entity, 0.0F, 0.0F, -0.1F, 0.0F, 0.0F, 0.0625F);
/* 101 */     GlStateManager.popMatrix();
/* 102 */     super.doRender(entity, x, y, z, entityYaw, partialTicks);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected ResourceLocation getEntityTexture(T entity) {
/* 109 */     return minecartTextures;
/*     */   }
/*     */   
/*     */   protected void func_180560_a(T minecart, float partialTicks, IBlockState state) {
/* 113 */     GlStateManager.pushMatrix();
/* 114 */     Minecraft.getMinecraft().getBlockRendererDispatcher().renderBlockBrightness(state, minecart.getBrightness(partialTicks));
/* 115 */     GlStateManager.popMatrix();
/*     */   }
/*     */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\minecraft\client\renderer\entity\RenderMinecart.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */