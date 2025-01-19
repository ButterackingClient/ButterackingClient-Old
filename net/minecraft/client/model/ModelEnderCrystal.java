/*    */ package net.minecraft.client.model;
/*    */ 
/*    */ import net.minecraft.client.renderer.GlStateManager;
/*    */ import net.minecraft.entity.Entity;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class ModelEnderCrystal
/*    */   extends ModelBase
/*    */ {
/*    */   private ModelRenderer cube;
/* 15 */   private ModelRenderer glass = new ModelRenderer(this, "glass");
/*    */ 
/*    */   
/*    */   private ModelRenderer base;
/*    */ 
/*    */ 
/*    */   
/*    */   public ModelEnderCrystal(float p_i1170_1_, boolean p_i1170_2_) {
/* 23 */     this.glass.setTextureOffset(0, 0).addBox(-4.0F, -4.0F, -4.0F, 8, 8, 8);
/* 24 */     this.cube = new ModelRenderer(this, "cube");
/* 25 */     this.cube.setTextureOffset(32, 0).addBox(-4.0F, -4.0F, -4.0F, 8, 8, 8);
/*    */     
/* 27 */     if (p_i1170_2_) {
/* 28 */       this.base = new ModelRenderer(this, "base");
/* 29 */       this.base.setTextureOffset(0, 16).addBox(-6.0F, 0.0F, -6.0F, 12, 4, 12);
/*    */     } 
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void render(Entity entityIn, float p_78088_2_, float p_78088_3_, float p_78088_4_, float p_78088_5_, float p_78088_6_, float scale) {
/* 37 */     GlStateManager.pushMatrix();
/* 38 */     GlStateManager.scale(2.0F, 2.0F, 2.0F);
/* 39 */     GlStateManager.translate(0.0F, -0.5F, 0.0F);
/*    */     
/* 41 */     if (this.base != null) {
/* 42 */       this.base.render(scale);
/*    */     }
/*    */     
/* 45 */     GlStateManager.rotate(p_78088_3_, 0.0F, 1.0F, 0.0F);
/* 46 */     GlStateManager.translate(0.0F, 0.8F + p_78088_4_, 0.0F);
/* 47 */     GlStateManager.rotate(60.0F, 0.7071F, 0.0F, 0.7071F);
/* 48 */     this.glass.render(scale);
/* 49 */     float f = 0.875F;
/* 50 */     GlStateManager.scale(f, f, f);
/* 51 */     GlStateManager.rotate(60.0F, 0.7071F, 0.0F, 0.7071F);
/* 52 */     GlStateManager.rotate(p_78088_3_, 0.0F, 1.0F, 0.0F);
/* 53 */     this.glass.render(scale);
/* 54 */     GlStateManager.scale(f, f, f);
/* 55 */     GlStateManager.rotate(60.0F, 0.7071F, 0.0F, 0.7071F);
/* 56 */     GlStateManager.rotate(p_78088_3_, 0.0F, 1.0F, 0.0F);
/* 57 */     this.cube.render(scale);
/* 58 */     GlStateManager.popMatrix();
/*    */   }
/*    */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\minecraft\client\model\ModelEnderCrystal.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */