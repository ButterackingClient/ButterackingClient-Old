/*    */ package net.minecraft.client.model;
/*    */ 
/*    */ import net.minecraft.entity.Entity;
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
/*    */ public class ModelSlime
/*    */   extends ModelBase
/*    */ {
/*    */   ModelRenderer slimeBodies;
/*    */   ModelRenderer slimeRightEye;
/*    */   ModelRenderer slimeLeftEye;
/*    */   ModelRenderer slimeMouth;
/*    */   
/*    */   public ModelSlime(int p_i1157_1_) {
/* 27 */     this.slimeBodies = new ModelRenderer(this, 0, p_i1157_1_);
/* 28 */     this.slimeBodies.addBox(-4.0F, 16.0F, -4.0F, 8, 8, 8);
/*    */     
/* 30 */     if (p_i1157_1_ > 0) {
/* 31 */       this.slimeBodies = new ModelRenderer(this, 0, p_i1157_1_);
/* 32 */       this.slimeBodies.addBox(-3.0F, 17.0F, -3.0F, 6, 6, 6);
/* 33 */       this.slimeRightEye = new ModelRenderer(this, 32, 0);
/* 34 */       this.slimeRightEye.addBox(-3.25F, 18.0F, -3.5F, 2, 2, 2);
/* 35 */       this.slimeLeftEye = new ModelRenderer(this, 32, 4);
/* 36 */       this.slimeLeftEye.addBox(1.25F, 18.0F, -3.5F, 2, 2, 2);
/* 37 */       this.slimeMouth = new ModelRenderer(this, 32, 8);
/* 38 */       this.slimeMouth.addBox(0.0F, 21.0F, -3.5F, 1, 1, 1);
/*    */     } 
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void render(Entity entityIn, float p_78088_2_, float p_78088_3_, float p_78088_4_, float p_78088_5_, float p_78088_6_, float scale) {
/* 46 */     setRotationAngles(p_78088_2_, p_78088_3_, p_78088_4_, p_78088_5_, p_78088_6_, scale, entityIn);
/* 47 */     this.slimeBodies.render(scale);
/*    */     
/* 49 */     if (this.slimeRightEye != null) {
/* 50 */       this.slimeRightEye.render(scale);
/* 51 */       this.slimeLeftEye.render(scale);
/* 52 */       this.slimeMouth.render(scale);
/*    */     } 
/*    */   }
/*    */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\minecraft\client\model\ModelSlime.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */