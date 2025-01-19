/*    */ package net.minecraft.client.model;
/*    */ 
/*    */ import net.minecraft.entity.Entity;
/*    */ import net.minecraft.entity.item.EntityArmorStand;
/*    */ 
/*    */ public class ModelArmorStandArmor extends ModelBiped {
/*    */   public ModelArmorStandArmor() {
/*  8 */     this(0.0F);
/*    */   }
/*    */   
/*    */   public ModelArmorStandArmor(float modelSize) {
/* 12 */     this(modelSize, 64, 32);
/*    */   }
/*    */   
/*    */   protected ModelArmorStandArmor(float modelSize, int textureWidthIn, int textureHeightIn) {
/* 16 */     super(modelSize, 0.0F, textureWidthIn, textureHeightIn);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void setRotationAngles(float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float scaleFactor, Entity entityIn) {
/* 25 */     if (entityIn instanceof EntityArmorStand) {
/* 26 */       EntityArmorStand entityarmorstand = (EntityArmorStand)entityIn;
/* 27 */       this.bipedHead.rotateAngleX = 0.017453292F * entityarmorstand.getHeadRotation().getX();
/* 28 */       this.bipedHead.rotateAngleY = 0.017453292F * entityarmorstand.getHeadRotation().getY();
/* 29 */       this.bipedHead.rotateAngleZ = 0.017453292F * entityarmorstand.getHeadRotation().getZ();
/* 30 */       this.bipedHead.setRotationPoint(0.0F, 1.0F, 0.0F);
/* 31 */       this.bipedBody.rotateAngleX = 0.017453292F * entityarmorstand.getBodyRotation().getX();
/* 32 */       this.bipedBody.rotateAngleY = 0.017453292F * entityarmorstand.getBodyRotation().getY();
/* 33 */       this.bipedBody.rotateAngleZ = 0.017453292F * entityarmorstand.getBodyRotation().getZ();
/* 34 */       this.bipedLeftArm.rotateAngleX = 0.017453292F * entityarmorstand.getLeftArmRotation().getX();
/* 35 */       this.bipedLeftArm.rotateAngleY = 0.017453292F * entityarmorstand.getLeftArmRotation().getY();
/* 36 */       this.bipedLeftArm.rotateAngleZ = 0.017453292F * entityarmorstand.getLeftArmRotation().getZ();
/* 37 */       this.bipedRightArm.rotateAngleX = 0.017453292F * entityarmorstand.getRightArmRotation().getX();
/* 38 */       this.bipedRightArm.rotateAngleY = 0.017453292F * entityarmorstand.getRightArmRotation().getY();
/* 39 */       this.bipedRightArm.rotateAngleZ = 0.017453292F * entityarmorstand.getRightArmRotation().getZ();
/* 40 */       this.bipedLeftLeg.rotateAngleX = 0.017453292F * entityarmorstand.getLeftLegRotation().getX();
/* 41 */       this.bipedLeftLeg.rotateAngleY = 0.017453292F * entityarmorstand.getLeftLegRotation().getY();
/* 42 */       this.bipedLeftLeg.rotateAngleZ = 0.017453292F * entityarmorstand.getLeftLegRotation().getZ();
/* 43 */       this.bipedLeftLeg.setRotationPoint(1.9F, 11.0F, 0.0F);
/* 44 */       this.bipedRightLeg.rotateAngleX = 0.017453292F * entityarmorstand.getRightLegRotation().getX();
/* 45 */       this.bipedRightLeg.rotateAngleY = 0.017453292F * entityarmorstand.getRightLegRotation().getY();
/* 46 */       this.bipedRightLeg.rotateAngleZ = 0.017453292F * entityarmorstand.getRightLegRotation().getZ();
/* 47 */       this.bipedRightLeg.setRotationPoint(-1.9F, 11.0F, 0.0F);
/* 48 */       copyModelAngles(this.bipedHead, this.bipedHeadwear);
/*    */     } 
/*    */   }
/*    */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\minecraft\client\model\ModelArmorStandArmor.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */