/*    */ package net.minecraft.client.model;
/*    */ 
/*    */ import net.minecraft.entity.Entity;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class ModelSquid
/*    */   extends ModelBase
/*    */ {
/*    */   ModelRenderer squidBody;
/* 14 */   ModelRenderer[] squidTentacles = new ModelRenderer[8];
/*    */   
/*    */   public ModelSquid() {
/* 17 */     int i = -16;
/* 18 */     this.squidBody = new ModelRenderer(this, 0, 0);
/* 19 */     this.squidBody.addBox(-6.0F, -8.0F, -6.0F, 12, 16, 12);
/* 20 */     this.squidBody.rotationPointY += (24 + i);
/*    */     
/* 22 */     for (int j = 0; j < this.squidTentacles.length; j++) {
/* 23 */       this.squidTentacles[j] = new ModelRenderer(this, 48, 0);
/* 24 */       double d0 = j * Math.PI * 2.0D / this.squidTentacles.length;
/* 25 */       float f = (float)Math.cos(d0) * 5.0F;
/* 26 */       float f1 = (float)Math.sin(d0) * 5.0F;
/* 27 */       this.squidTentacles[j].addBox(-1.0F, 0.0F, -1.0F, 2, 18, 2);
/* 28 */       (this.squidTentacles[j]).rotationPointX = f;
/* 29 */       (this.squidTentacles[j]).rotationPointZ = f1;
/* 30 */       (this.squidTentacles[j]).rotationPointY = (31 + i);
/* 31 */       d0 = j * Math.PI * -2.0D / this.squidTentacles.length + 1.5707963267948966D;
/* 32 */       (this.squidTentacles[j]).rotateAngleY = (float)d0;
/*    */     } 
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public void setRotationAngles(float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float scaleFactor, Entity entityIn) {
/*    */     byte b;
/*    */     int i;
/*    */     ModelRenderer[] arrayOfModelRenderer;
/* 42 */     for (i = (arrayOfModelRenderer = this.squidTentacles).length, b = 0; b < i; ) { ModelRenderer modelrenderer = arrayOfModelRenderer[b];
/* 43 */       modelrenderer.rotateAngleX = ageInTicks;
/*    */       b++; }
/*    */   
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public void render(Entity entityIn, float p_78088_2_, float p_78088_3_, float p_78088_4_, float p_78088_5_, float p_78088_6_, float scale) {
/* 51 */     setRotationAngles(p_78088_2_, p_78088_3_, p_78088_4_, p_78088_5_, p_78088_6_, scale, entityIn);
/* 52 */     this.squidBody.render(scale);
/*    */     
/* 54 */     for (int i = 0; i < this.squidTentacles.length; i++)
/* 55 */       this.squidTentacles[i].render(scale); 
/*    */   }
/*    */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\minecraft\client\model\ModelSquid.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */