/*    */ package net.minecraft.client.model;
/*    */ 
/*    */ import net.minecraft.entity.Entity;
/*    */ import net.minecraft.util.MathHelper;
/*    */ 
/*    */ public class ModelWitch extends ModelVillager {
/*    */   public boolean field_82900_g;
/*  8 */   private ModelRenderer field_82901_h = (new ModelRenderer(this)).setTextureSize(64, 128);
/*    */   private ModelRenderer witchHat;
/*    */   
/*    */   public ModelWitch(float p_i46361_1_) {
/* 12 */     super(p_i46361_1_, 0.0F, 64, 128);
/* 13 */     this.field_82901_h.setRotationPoint(0.0F, -2.0F, 0.0F);
/* 14 */     this.field_82901_h.setTextureOffset(0, 0).addBox(0.0F, 3.0F, -6.75F, 1, 1, 1, -0.25F);
/* 15 */     this.villagerNose.addChild(this.field_82901_h);
/* 16 */     this.witchHat = (new ModelRenderer(this)).setTextureSize(64, 128);
/* 17 */     this.witchHat.setRotationPoint(-5.0F, -10.03125F, -5.0F);
/* 18 */     this.witchHat.setTextureOffset(0, 64).addBox(0.0F, 0.0F, 0.0F, 10, 2, 10);
/* 19 */     this.villagerHead.addChild(this.witchHat);
/* 20 */     ModelRenderer modelrenderer = (new ModelRenderer(this)).setTextureSize(64, 128);
/* 21 */     modelrenderer.setRotationPoint(1.75F, -4.0F, 2.0F);
/* 22 */     modelrenderer.setTextureOffset(0, 76).addBox(0.0F, 0.0F, 0.0F, 7, 4, 7);
/* 23 */     modelrenderer.rotateAngleX = -0.05235988F;
/* 24 */     modelrenderer.rotateAngleZ = 0.02617994F;
/* 25 */     this.witchHat.addChild(modelrenderer);
/* 26 */     ModelRenderer modelrenderer1 = (new ModelRenderer(this)).setTextureSize(64, 128);
/* 27 */     modelrenderer1.setRotationPoint(1.75F, -4.0F, 2.0F);
/* 28 */     modelrenderer1.setTextureOffset(0, 87).addBox(0.0F, 0.0F, 0.0F, 4, 4, 4);
/* 29 */     modelrenderer1.rotateAngleX = -0.10471976F;
/* 30 */     modelrenderer1.rotateAngleZ = 0.05235988F;
/* 31 */     modelrenderer.addChild(modelrenderer1);
/* 32 */     ModelRenderer modelrenderer2 = (new ModelRenderer(this)).setTextureSize(64, 128);
/* 33 */     modelrenderer2.setRotationPoint(1.75F, -2.0F, 2.0F);
/* 34 */     modelrenderer2.setTextureOffset(0, 95).addBox(0.0F, 0.0F, 0.0F, 1, 2, 1, 0.25F);
/* 35 */     modelrenderer2.rotateAngleX = -0.20943952F;
/* 36 */     modelrenderer2.rotateAngleZ = 0.10471976F;
/* 37 */     modelrenderer1.addChild(modelrenderer2);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void setRotationAngles(float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float scaleFactor, Entity entityIn) {
/* 46 */     super.setRotationAngles(limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scaleFactor, entityIn);
/* 47 */     this.villagerNose.offsetX = this.villagerNose.offsetY = this.villagerNose.offsetZ = 0.0F;
/* 48 */     float f = 0.01F * (entityIn.getEntityId() % 10);
/* 49 */     this.villagerNose.rotateAngleX = MathHelper.sin(entityIn.ticksExisted * f) * 4.5F * 3.1415927F / 180.0F;
/* 50 */     this.villagerNose.rotateAngleY = 0.0F;
/* 51 */     this.villagerNose.rotateAngleZ = MathHelper.cos(entityIn.ticksExisted * f) * 2.5F * 3.1415927F / 180.0F;
/*    */     
/* 53 */     if (this.field_82900_g) {
/* 54 */       this.villagerNose.rotateAngleX = -0.9F;
/* 55 */       this.villagerNose.offsetZ = -0.09375F;
/* 56 */       this.villagerNose.offsetY = 0.1875F;
/*    */     } 
/*    */   }
/*    */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\minecraft\client\model\ModelWitch.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */