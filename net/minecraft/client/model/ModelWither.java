/*    */ package net.minecraft.client.model;
/*    */ 
/*    */ import net.minecraft.entity.Entity;
/*    */ import net.minecraft.entity.EntityLivingBase;
/*    */ import net.minecraft.entity.boss.EntityWither;
/*    */ import net.minecraft.util.MathHelper;
/*    */ 
/*    */ public class ModelWither extends ModelBase {
/*    */   private ModelRenderer[] field_82905_a;
/*    */   private ModelRenderer[] field_82904_b;
/*    */   
/*    */   public ModelWither(float p_i46302_1_) {
/* 13 */     this.textureWidth = 64;
/* 14 */     this.textureHeight = 64;
/* 15 */     this.field_82905_a = new ModelRenderer[3];
/* 16 */     this.field_82905_a[0] = new ModelRenderer(this, 0, 16);
/* 17 */     this.field_82905_a[0].addBox(-10.0F, 3.9F, -0.5F, 20, 3, 3, p_i46302_1_);
/* 18 */     this.field_82905_a[1] = (new ModelRenderer(this)).setTextureSize(this.textureWidth, this.textureHeight);
/* 19 */     this.field_82905_a[1].setRotationPoint(-2.0F, 6.9F, -0.5F);
/* 20 */     this.field_82905_a[1].setTextureOffset(0, 22).addBox(0.0F, 0.0F, 0.0F, 3, 10, 3, p_i46302_1_);
/* 21 */     this.field_82905_a[1].setTextureOffset(24, 22).addBox(-4.0F, 1.5F, 0.5F, 11, 2, 2, p_i46302_1_);
/* 22 */     this.field_82905_a[1].setTextureOffset(24, 22).addBox(-4.0F, 4.0F, 0.5F, 11, 2, 2, p_i46302_1_);
/* 23 */     this.field_82905_a[1].setTextureOffset(24, 22).addBox(-4.0F, 6.5F, 0.5F, 11, 2, 2, p_i46302_1_);
/* 24 */     this.field_82905_a[2] = new ModelRenderer(this, 12, 22);
/* 25 */     this.field_82905_a[2].addBox(0.0F, 0.0F, 0.0F, 3, 6, 3, p_i46302_1_);
/* 26 */     this.field_82904_b = new ModelRenderer[3];
/* 27 */     this.field_82904_b[0] = new ModelRenderer(this, 0, 0);
/* 28 */     this.field_82904_b[0].addBox(-4.0F, -4.0F, -4.0F, 8, 8, 8, p_i46302_1_);
/* 29 */     this.field_82904_b[1] = new ModelRenderer(this, 32, 0);
/* 30 */     this.field_82904_b[1].addBox(-4.0F, -4.0F, -4.0F, 6, 6, 6, p_i46302_1_);
/* 31 */     (this.field_82904_b[1]).rotationPointX = -8.0F;
/* 32 */     (this.field_82904_b[1]).rotationPointY = 4.0F;
/* 33 */     this.field_82904_b[2] = new ModelRenderer(this, 32, 0);
/* 34 */     this.field_82904_b[2].addBox(-4.0F, -4.0F, -4.0F, 6, 6, 6, p_i46302_1_);
/* 35 */     (this.field_82904_b[2]).rotationPointX = 10.0F;
/* 36 */     (this.field_82904_b[2]).rotationPointY = 4.0F;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void render(Entity entityIn, float p_78088_2_, float p_78088_3_, float p_78088_4_, float p_78088_5_, float p_78088_6_, float scale) {
/* 43 */     setRotationAngles(p_78088_2_, p_78088_3_, p_78088_4_, p_78088_5_, p_78088_6_, scale, entityIn); byte b; int i;
/*    */     ModelRenderer[] arrayOfModelRenderer;
/* 45 */     for (i = (arrayOfModelRenderer = this.field_82904_b).length, b = 0; b < i; ) { ModelRenderer modelrenderer = arrayOfModelRenderer[b];
/* 46 */       modelrenderer.render(scale);
/*    */       b++; }
/*    */     
/* 49 */     for (i = (arrayOfModelRenderer = this.field_82905_a).length, b = 0; b < i; ) { ModelRenderer modelrenderer1 = arrayOfModelRenderer[b];
/* 50 */       modelrenderer1.render(scale);
/*    */       b++; }
/*    */   
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void setRotationAngles(float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float scaleFactor, Entity entityIn) {
/* 60 */     float f = MathHelper.cos(ageInTicks * 0.1F);
/* 61 */     (this.field_82905_a[1]).rotateAngleX = (0.065F + 0.05F * f) * 3.1415927F;
/* 62 */     this.field_82905_a[2].setRotationPoint(-2.0F, 6.9F + MathHelper.cos((this.field_82905_a[1]).rotateAngleX) * 10.0F, -0.5F + MathHelper.sin((this.field_82905_a[1]).rotateAngleX) * 10.0F);
/* 63 */     (this.field_82905_a[2]).rotateAngleX = (0.265F + 0.1F * f) * 3.1415927F;
/* 64 */     (this.field_82904_b[0]).rotateAngleY = netHeadYaw / 57.295776F;
/* 65 */     (this.field_82904_b[0]).rotateAngleX = headPitch / 57.295776F;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void setLivingAnimations(EntityLivingBase entitylivingbaseIn, float p_78086_2_, float p_78086_3_, float partialTickTime) {
/* 73 */     EntityWither entitywither = (EntityWither)entitylivingbaseIn;
/*    */     
/* 75 */     for (int i = 1; i < 3; i++) {
/* 76 */       (this.field_82904_b[i]).rotateAngleY = (entitywither.func_82207_a(i - 1) - entitylivingbaseIn.renderYawOffset) / 57.295776F;
/* 77 */       (this.field_82904_b[i]).rotateAngleX = entitywither.func_82210_r(i - 1) / 57.295776F;
/*    */     } 
/*    */   }
/*    */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\minecraft\client\model\ModelWither.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */