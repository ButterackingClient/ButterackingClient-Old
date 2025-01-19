/*    */ package net.minecraft.client.model;
/*    */ 
/*    */ import java.util.Random;
/*    */ import net.minecraft.client.renderer.GlStateManager;
/*    */ import net.minecraft.entity.Entity;
/*    */ import net.minecraft.util.MathHelper;
/*    */ 
/*    */ public class ModelGhast
/*    */   extends ModelBase {
/*    */   ModelRenderer body;
/* 11 */   ModelRenderer[] tentacles = new ModelRenderer[9];
/*    */   
/*    */   public ModelGhast() {
/* 14 */     int i = -16;
/* 15 */     this.body = new ModelRenderer(this, 0, 0);
/* 16 */     this.body.addBox(-8.0F, -8.0F, -8.0F, 16, 16, 16);
/* 17 */     this.body.rotationPointY += (24 + i);
/* 18 */     Random random = new Random(1660L);
/*    */     
/* 20 */     for (int j = 0; j < this.tentacles.length; j++) {
/* 21 */       this.tentacles[j] = new ModelRenderer(this, 0, 0);
/* 22 */       float f = (((j % 3) - (j / 3 % 2) * 0.5F + 0.25F) / 2.0F * 2.0F - 1.0F) * 5.0F;
/* 23 */       float f1 = ((j / 3) / 2.0F * 2.0F - 1.0F) * 5.0F;
/* 24 */       int k = random.nextInt(7) + 8;
/* 25 */       this.tentacles[j].addBox(-1.0F, 0.0F, -1.0F, 2, k, 2);
/* 26 */       (this.tentacles[j]).rotationPointX = f;
/* 27 */       (this.tentacles[j]).rotationPointZ = f1;
/* 28 */       (this.tentacles[j]).rotationPointY = (31 + i);
/*    */     } 
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void setRotationAngles(float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float scaleFactor, Entity entityIn) {
/* 38 */     for (int i = 0; i < this.tentacles.length; i++) {
/* 39 */       (this.tentacles[i]).rotateAngleX = 0.2F * MathHelper.sin(ageInTicks * 0.3F + i) + 0.4F;
/*    */     }
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void render(Entity entityIn, float p_78088_2_, float p_78088_3_, float p_78088_4_, float p_78088_5_, float p_78088_6_, float scale) {
/* 47 */     setRotationAngles(p_78088_2_, p_78088_3_, p_78088_4_, p_78088_5_, p_78088_6_, scale, entityIn);
/* 48 */     GlStateManager.pushMatrix();
/* 49 */     GlStateManager.translate(0.0F, 0.6F, 0.0F);
/* 50 */     this.body.render(scale); byte b; int i;
/*    */     ModelRenderer[] arrayOfModelRenderer;
/* 52 */     for (i = (arrayOfModelRenderer = this.tentacles).length, b = 0; b < i; ) { ModelRenderer modelrenderer = arrayOfModelRenderer[b];
/* 53 */       modelrenderer.render(scale);
/*    */       b++; }
/*    */     
/* 56 */     GlStateManager.popMatrix();
/*    */   }
/*    */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\minecraft\client\model\ModelGhast.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */