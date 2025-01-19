/*    */ package net.minecraft.client.model;
/*    */ 
/*    */ import net.minecraft.entity.Entity;
/*    */ import net.minecraft.entity.EntityLivingBase;
/*    */ import net.minecraft.entity.monster.EntityMagmaCube;
/*    */ 
/*    */ public class ModelMagmaCube extends ModelBase {
/*  8 */   ModelRenderer[] segments = new ModelRenderer[8];
/*    */   ModelRenderer core;
/*    */   
/*    */   public ModelMagmaCube() {
/* 12 */     for (int i = 0; i < this.segments.length; i++) {
/* 13 */       int j = 0;
/* 14 */       int k = i;
/*    */       
/* 16 */       if (i == 2) {
/* 17 */         j = 24;
/* 18 */         k = 10;
/* 19 */       } else if (i == 3) {
/* 20 */         j = 24;
/* 21 */         k = 19;
/*    */       } 
/*    */       
/* 24 */       this.segments[i] = new ModelRenderer(this, j, k);
/* 25 */       this.segments[i].addBox(-4.0F, (16 + i), -4.0F, 8, 1, 8);
/*    */     } 
/*    */     
/* 28 */     this.core = new ModelRenderer(this, 0, 16);
/* 29 */     this.core.addBox(-2.0F, 18.0F, -2.0F, 4, 4, 4);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void setLivingAnimations(EntityLivingBase entitylivingbaseIn, float p_78086_2_, float p_78086_3_, float partialTickTime) {
/* 37 */     EntityMagmaCube entitymagmacube = (EntityMagmaCube)entitylivingbaseIn;
/* 38 */     float f = entitymagmacube.prevSquishFactor + (entitymagmacube.squishFactor - entitymagmacube.prevSquishFactor) * partialTickTime;
/*    */     
/* 40 */     if (f < 0.0F) {
/* 41 */       f = 0.0F;
/*    */     }
/*    */     
/* 44 */     for (int i = 0; i < this.segments.length; i++) {
/* 45 */       (this.segments[i]).rotationPointY = -(4 - i) * f * 1.7F;
/*    */     }
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void render(Entity entityIn, float p_78088_2_, float p_78088_3_, float p_78088_4_, float p_78088_5_, float p_78088_6_, float scale) {
/* 53 */     setRotationAngles(p_78088_2_, p_78088_3_, p_78088_4_, p_78088_5_, p_78088_6_, scale, entityIn);
/* 54 */     this.core.render(scale);
/*    */     
/* 56 */     for (int i = 0; i < this.segments.length; i++)
/* 57 */       this.segments[i].render(scale); 
/*    */   }
/*    */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\minecraft\client\model\ModelMagmaCube.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */