/*    */ package net.minecraft.client.model;
/*    */ 
/*    */ import net.minecraft.client.renderer.GlStateManager;
/*    */ import net.minecraft.entity.Entity;
/*    */ import net.minecraft.util.MathHelper;
/*    */ 
/*    */ public class ModelChicken extends ModelBase {
/*    */   public ModelRenderer head;
/*    */   public ModelRenderer body;
/*    */   public ModelRenderer rightLeg;
/*    */   public ModelRenderer leftLeg;
/*    */   public ModelRenderer rightWing;
/*    */   public ModelRenderer leftWing;
/*    */   public ModelRenderer bill;
/*    */   public ModelRenderer chin;
/*    */   
/*    */   public ModelChicken() {
/* 18 */     int i = 16;
/* 19 */     this.head = new ModelRenderer(this, 0, 0);
/* 20 */     this.head.addBox(-2.0F, -6.0F, -2.0F, 4, 6, 3, 0.0F);
/* 21 */     this.head.setRotationPoint(0.0F, (-1 + i), -4.0F);
/* 22 */     this.bill = new ModelRenderer(this, 14, 0);
/* 23 */     this.bill.addBox(-2.0F, -4.0F, -4.0F, 4, 2, 2, 0.0F);
/* 24 */     this.bill.setRotationPoint(0.0F, (-1 + i), -4.0F);
/* 25 */     this.chin = new ModelRenderer(this, 14, 4);
/* 26 */     this.chin.addBox(-1.0F, -2.0F, -3.0F, 2, 2, 2, 0.0F);
/* 27 */     this.chin.setRotationPoint(0.0F, (-1 + i), -4.0F);
/* 28 */     this.body = new ModelRenderer(this, 0, 9);
/* 29 */     this.body.addBox(-3.0F, -4.0F, -3.0F, 6, 8, 6, 0.0F);
/* 30 */     this.body.setRotationPoint(0.0F, i, 0.0F);
/* 31 */     this.rightLeg = new ModelRenderer(this, 26, 0);
/* 32 */     this.rightLeg.addBox(-1.0F, 0.0F, -3.0F, 3, 5, 3);
/* 33 */     this.rightLeg.setRotationPoint(-2.0F, (3 + i), 1.0F);
/* 34 */     this.leftLeg = new ModelRenderer(this, 26, 0);
/* 35 */     this.leftLeg.addBox(-1.0F, 0.0F, -3.0F, 3, 5, 3);
/* 36 */     this.leftLeg.setRotationPoint(1.0F, (3 + i), 1.0F);
/* 37 */     this.rightWing = new ModelRenderer(this, 24, 13);
/* 38 */     this.rightWing.addBox(0.0F, 0.0F, -3.0F, 1, 4, 6);
/* 39 */     this.rightWing.setRotationPoint(-4.0F, (-3 + i), 0.0F);
/* 40 */     this.leftWing = new ModelRenderer(this, 24, 13);
/* 41 */     this.leftWing.addBox(-1.0F, 0.0F, -3.0F, 1, 4, 6);
/* 42 */     this.leftWing.setRotationPoint(4.0F, (-3 + i), 0.0F);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void render(Entity entityIn, float p_78088_2_, float p_78088_3_, float p_78088_4_, float p_78088_5_, float p_78088_6_, float scale) {
/* 49 */     setRotationAngles(p_78088_2_, p_78088_3_, p_78088_4_, p_78088_5_, p_78088_6_, scale, entityIn);
/*    */     
/* 51 */     if (this.isChild) {
/* 52 */       float f = 2.0F;
/* 53 */       GlStateManager.pushMatrix();
/* 54 */       GlStateManager.translate(0.0F, 5.0F * scale, 2.0F * scale);
/* 55 */       this.head.render(scale);
/* 56 */       this.bill.render(scale);
/* 57 */       this.chin.render(scale);
/* 58 */       GlStateManager.popMatrix();
/* 59 */       GlStateManager.pushMatrix();
/* 60 */       GlStateManager.scale(1.0F / f, 1.0F / f, 1.0F / f);
/* 61 */       GlStateManager.translate(0.0F, 24.0F * scale, 0.0F);
/* 62 */       this.body.render(scale);
/* 63 */       this.rightLeg.render(scale);
/* 64 */       this.leftLeg.render(scale);
/* 65 */       this.rightWing.render(scale);
/* 66 */       this.leftWing.render(scale);
/* 67 */       GlStateManager.popMatrix();
/*    */     } else {
/* 69 */       this.head.render(scale);
/* 70 */       this.bill.render(scale);
/* 71 */       this.chin.render(scale);
/* 72 */       this.body.render(scale);
/* 73 */       this.rightLeg.render(scale);
/* 74 */       this.leftLeg.render(scale);
/* 75 */       this.rightWing.render(scale);
/* 76 */       this.leftWing.render(scale);
/*    */     } 
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void setRotationAngles(float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float scaleFactor, Entity entityIn) {
/* 86 */     this.head.rotateAngleX = headPitch / 57.295776F;
/* 87 */     this.head.rotateAngleY = netHeadYaw / 57.295776F;
/* 88 */     this.bill.rotateAngleX = this.head.rotateAngleX;
/* 89 */     this.bill.rotateAngleY = this.head.rotateAngleY;
/* 90 */     this.chin.rotateAngleX = this.head.rotateAngleX;
/* 91 */     this.chin.rotateAngleY = this.head.rotateAngleY;
/* 92 */     this.body.rotateAngleX = 1.5707964F;
/* 93 */     this.rightLeg.rotateAngleX = MathHelper.cos(limbSwing * 0.6662F) * 1.4F * limbSwingAmount;
/* 94 */     this.leftLeg.rotateAngleX = MathHelper.cos(limbSwing * 0.6662F + 3.1415927F) * 1.4F * limbSwingAmount;
/* 95 */     this.rightWing.rotateAngleZ = ageInTicks;
/* 96 */     this.leftWing.rotateAngleZ = -ageInTicks;
/*    */   }
/*    */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\minecraft\client\model\ModelChicken.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */