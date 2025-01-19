/*    */ package net.minecraft.client.particle;
/*    */ 
/*    */ import net.minecraft.client.Minecraft;
/*    */ import net.minecraft.client.renderer.GlStateManager;
/*    */ import net.minecraft.client.renderer.OpenGlHelper;
/*    */ import net.minecraft.client.renderer.WorldRenderer;
/*    */ import net.minecraft.client.renderer.entity.RenderManager;
/*    */ import net.minecraft.entity.Entity;
/*    */ import net.minecraft.src.Config;
/*    */ import net.minecraft.world.World;
/*    */ import net.optifine.shaders.Program;
/*    */ import net.optifine.shaders.Shaders;
/*    */ 
/*    */ public class EntityPickupFX extends EntityFX {
/*    */   private Entity field_174840_a;
/*    */   private Entity field_174843_ax;
/*    */   private int age;
/*    */   private int maxAge;
/*    */   private float field_174841_aA;
/* 20 */   private RenderManager field_174842_aB = Minecraft.getMinecraft().getRenderManager();
/*    */   
/*    */   public EntityPickupFX(World worldIn, Entity p_i1233_2_, Entity p_i1233_3_, float p_i1233_4_) {
/* 23 */     super(worldIn, p_i1233_2_.posX, p_i1233_2_.posY, p_i1233_2_.posZ, p_i1233_2_.motionX, p_i1233_2_.motionY, p_i1233_2_.motionZ);
/* 24 */     this.field_174840_a = p_i1233_2_;
/* 25 */     this.field_174843_ax = p_i1233_3_;
/* 26 */     this.maxAge = 3;
/* 27 */     this.field_174841_aA = p_i1233_4_;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void renderParticle(WorldRenderer worldRendererIn, Entity entityIn, float partialTicks, float rotationX, float rotationZ, float rotationYZ, float rotationXY, float rotationXZ) {
/* 34 */     Program program = null;
/*    */     
/* 36 */     if (Config.isShaders()) {
/* 37 */       program = Shaders.activeProgram;
/* 38 */       Shaders.nextEntity(this.field_174840_a);
/*    */     } 
/*    */     
/* 41 */     float f = (this.age + partialTicks) / this.maxAge;
/* 42 */     f *= f;
/* 43 */     double d0 = this.field_174840_a.posX;
/* 44 */     double d1 = this.field_174840_a.posY;
/* 45 */     double d2 = this.field_174840_a.posZ;
/* 46 */     double d3 = this.field_174843_ax.lastTickPosX + (this.field_174843_ax.posX - this.field_174843_ax.lastTickPosX) * partialTicks;
/* 47 */     double d4 = this.field_174843_ax.lastTickPosY + (this.field_174843_ax.posY - this.field_174843_ax.lastTickPosY) * partialTicks + this.field_174841_aA;
/* 48 */     double d5 = this.field_174843_ax.lastTickPosZ + (this.field_174843_ax.posZ - this.field_174843_ax.lastTickPosZ) * partialTicks;
/* 49 */     double d6 = d0 + (d3 - d0) * f;
/* 50 */     double d7 = d1 + (d4 - d1) * f;
/* 51 */     double d8 = d2 + (d5 - d2) * f;
/* 52 */     int i = getBrightnessForRender(partialTicks);
/* 53 */     int j = i % 65536;
/* 54 */     int k = i / 65536;
/* 55 */     OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, j / 1.0F, k / 1.0F);
/* 56 */     GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
/* 57 */     d6 -= interpPosX;
/* 58 */     d7 -= interpPosY;
/* 59 */     d8 -= interpPosZ;
/* 60 */     this.field_174842_aB.renderEntityWithPosYaw(this.field_174840_a, (float)d6, (float)d7, (float)d8, this.field_174840_a.rotationYaw, partialTicks);
/*    */     
/* 62 */     if (Config.isShaders()) {
/* 63 */       Shaders.setEntityId(null);
/* 64 */       Shaders.useProgram(program);
/*    */     } 
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void onUpdate() {
/* 72 */     this.age++;
/*    */     
/* 74 */     if (this.age == this.maxAge) {
/* 75 */       setDead();
/*    */     }
/*    */   }
/*    */   
/*    */   public int getFXLayer() {
/* 80 */     return 3;
/*    */   }
/*    */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\minecraft\client\particle\EntityPickupFX.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */