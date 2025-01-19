/*    */ package net.minecraft.client.model;
/*    */ 
/*    */ public class ModelBanner extends ModelBase {
/*    */   public ModelRenderer bannerSlate;
/*    */   public ModelRenderer bannerStand;
/*    */   public ModelRenderer bannerTop;
/*    */   
/*    */   public ModelBanner() {
/*  9 */     this.textureWidth = 64;
/* 10 */     this.textureHeight = 64;
/* 11 */     this.bannerSlate = new ModelRenderer(this, 0, 0);
/* 12 */     this.bannerSlate.addBox(-10.0F, 0.0F, -2.0F, 20, 40, 1, 0.0F);
/* 13 */     this.bannerStand = new ModelRenderer(this, 44, 0);
/* 14 */     this.bannerStand.addBox(-1.0F, -30.0F, -1.0F, 2, 42, 2, 0.0F);
/* 15 */     this.bannerTop = new ModelRenderer(this, 0, 42);
/* 16 */     this.bannerTop.addBox(-10.0F, -32.0F, -1.0F, 20, 2, 2, 0.0F);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void renderBanner() {
/* 23 */     this.bannerSlate.rotationPointY = -32.0F;
/* 24 */     this.bannerSlate.render(0.0625F);
/* 25 */     this.bannerStand.render(0.0625F);
/* 26 */     this.bannerTop.render(0.0625F);
/*    */   }
/*    */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\minecraft\client\model\ModelBanner.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */