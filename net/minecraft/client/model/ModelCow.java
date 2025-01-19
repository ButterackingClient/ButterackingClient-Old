/*    */ package net.minecraft.client.model;
/*    */ 
/*    */ public class ModelCow extends ModelQuadruped {
/*    */   public ModelCow() {
/*  5 */     super(12, 0.0F);
/*  6 */     this.head = new ModelRenderer(this, 0, 0);
/*  7 */     this.head.addBox(-4.0F, -4.0F, -6.0F, 8, 8, 6, 0.0F);
/*  8 */     this.head.setRotationPoint(0.0F, 4.0F, -8.0F);
/*  9 */     this.head.setTextureOffset(22, 0).addBox(-5.0F, -5.0F, -4.0F, 1, 3, 1, 0.0F);
/* 10 */     this.head.setTextureOffset(22, 0).addBox(4.0F, -5.0F, -4.0F, 1, 3, 1, 0.0F);
/* 11 */     this.body = new ModelRenderer(this, 18, 4);
/* 12 */     this.body.addBox(-6.0F, -10.0F, -7.0F, 12, 18, 10, 0.0F);
/* 13 */     this.body.setRotationPoint(0.0F, 5.0F, 2.0F);
/* 14 */     this.body.setTextureOffset(52, 0).addBox(-2.0F, 2.0F, -8.0F, 4, 6, 1);
/* 15 */     this.leg1.rotationPointX--;
/* 16 */     this.leg2.rotationPointX++;
/* 17 */     this.leg1.rotationPointZ += 0.0F;
/* 18 */     this.leg2.rotationPointZ += 0.0F;
/* 19 */     this.leg3.rotationPointX--;
/* 20 */     this.leg4.rotationPointX++;
/* 21 */     this.leg3.rotationPointZ--;
/* 22 */     this.leg4.rotationPointZ--;
/* 23 */     this.childZOffset += 2.0F;
/*    */   }
/*    */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\minecraft\client\model\ModelCow.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */