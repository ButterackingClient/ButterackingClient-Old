/*    */ package net.minecraft.client.model;
/*    */ 
/*    */ import net.minecraft.entity.Entity;
/*    */ import net.minecraft.util.MathHelper;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class ModelVillager
/*    */   extends ModelBase
/*    */ {
/*    */   public ModelRenderer villagerHead;
/*    */   public ModelRenderer villagerBody;
/*    */   public ModelRenderer villagerArms;
/*    */   public ModelRenderer rightVillagerLeg;
/*    */   public ModelRenderer leftVillagerLeg;
/*    */   public ModelRenderer villagerNose;
/*    */   
/*    */   public ModelVillager(float p_i1163_1_) {
/* 34 */     this(p_i1163_1_, 0.0F, 64, 64);
/*    */   }
/*    */   
/*    */   public ModelVillager(float p_i1164_1_, float p_i1164_2_, int p_i1164_3_, int p_i1164_4_) {
/* 38 */     this.villagerHead = (new ModelRenderer(this)).setTextureSize(p_i1164_3_, p_i1164_4_);
/* 39 */     this.villagerHead.setRotationPoint(0.0F, 0.0F + p_i1164_2_, 0.0F);
/* 40 */     this.villagerHead.setTextureOffset(0, 0).addBox(-4.0F, -10.0F, -4.0F, 8, 10, 8, p_i1164_1_);
/* 41 */     this.villagerNose = (new ModelRenderer(this)).setTextureSize(p_i1164_3_, p_i1164_4_);
/* 42 */     this.villagerNose.setRotationPoint(0.0F, p_i1164_2_ - 2.0F, 0.0F);
/* 43 */     this.villagerNose.setTextureOffset(24, 0).addBox(-1.0F, -1.0F, -6.0F, 2, 4, 2, p_i1164_1_);
/* 44 */     this.villagerHead.addChild(this.villagerNose);
/* 45 */     this.villagerBody = (new ModelRenderer(this)).setTextureSize(p_i1164_3_, p_i1164_4_);
/* 46 */     this.villagerBody.setRotationPoint(0.0F, 0.0F + p_i1164_2_, 0.0F);
/* 47 */     this.villagerBody.setTextureOffset(16, 20).addBox(-4.0F, 0.0F, -3.0F, 8, 12, 6, p_i1164_1_);
/* 48 */     this.villagerBody.setTextureOffset(0, 38).addBox(-4.0F, 0.0F, -3.0F, 8, 18, 6, p_i1164_1_ + 0.5F);
/* 49 */     this.villagerArms = (new ModelRenderer(this)).setTextureSize(p_i1164_3_, p_i1164_4_);
/* 50 */     this.villagerArms.setRotationPoint(0.0F, 0.0F + p_i1164_2_ + 2.0F, 0.0F);
/* 51 */     this.villagerArms.setTextureOffset(44, 22).addBox(-8.0F, -2.0F, -2.0F, 4, 8, 4, p_i1164_1_);
/* 52 */     this.villagerArms.setTextureOffset(44, 22).addBox(4.0F, -2.0F, -2.0F, 4, 8, 4, p_i1164_1_);
/* 53 */     this.villagerArms.setTextureOffset(40, 38).addBox(-4.0F, 2.0F, -2.0F, 8, 4, 4, p_i1164_1_);
/* 54 */     this.rightVillagerLeg = (new ModelRenderer(this, 0, 22)).setTextureSize(p_i1164_3_, p_i1164_4_);
/* 55 */     this.rightVillagerLeg.setRotationPoint(-2.0F, 12.0F + p_i1164_2_, 0.0F);
/* 56 */     this.rightVillagerLeg.addBox(-2.0F, 0.0F, -2.0F, 4, 12, 4, p_i1164_1_);
/* 57 */     this.leftVillagerLeg = (new ModelRenderer(this, 0, 22)).setTextureSize(p_i1164_3_, p_i1164_4_);
/* 58 */     this.leftVillagerLeg.mirror = true;
/* 59 */     this.leftVillagerLeg.setRotationPoint(2.0F, 12.0F + p_i1164_2_, 0.0F);
/* 60 */     this.leftVillagerLeg.addBox(-2.0F, 0.0F, -2.0F, 4, 12, 4, p_i1164_1_);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void render(Entity entityIn, float p_78088_2_, float p_78088_3_, float p_78088_4_, float p_78088_5_, float p_78088_6_, float scale) {
/* 67 */     setRotationAngles(p_78088_2_, p_78088_3_, p_78088_4_, p_78088_5_, p_78088_6_, scale, entityIn);
/* 68 */     this.villagerHead.render(scale);
/* 69 */     this.villagerBody.render(scale);
/* 70 */     this.rightVillagerLeg.render(scale);
/* 71 */     this.leftVillagerLeg.render(scale);
/* 72 */     this.villagerArms.render(scale);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void setRotationAngles(float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float scaleFactor, Entity entityIn) {
/* 81 */     this.villagerHead.rotateAngleY = netHeadYaw / 57.295776F;
/* 82 */     this.villagerHead.rotateAngleX = headPitch / 57.295776F;
/* 83 */     this.villagerArms.rotationPointY = 3.0F;
/* 84 */     this.villagerArms.rotationPointZ = -1.0F;
/* 85 */     this.villagerArms.rotateAngleX = -0.75F;
/* 86 */     this.rightVillagerLeg.rotateAngleX = MathHelper.cos(limbSwing * 0.6662F) * 1.4F * limbSwingAmount * 0.5F;
/* 87 */     this.leftVillagerLeg.rotateAngleX = MathHelper.cos(limbSwing * 0.6662F + 3.1415927F) * 1.4F * limbSwingAmount * 0.5F;
/* 88 */     this.rightVillagerLeg.rotateAngleY = 0.0F;
/* 89 */     this.leftVillagerLeg.rotateAngleY = 0.0F;
/*    */   }
/*    */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\minecraft\client\model\ModelVillager.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */