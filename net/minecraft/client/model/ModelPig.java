/*    */ package net.minecraft.client.model;
/*    */ 
/*    */ public class ModelPig extends ModelQuadruped {
/*    */   public ModelPig() {
/*  5 */     this(0.0F);
/*    */   }
/*    */   
/*    */   public ModelPig(float p_i1151_1_) {
/*  9 */     super(6, p_i1151_1_);
/* 10 */     this.head.setTextureOffset(16, 16).addBox(-2.0F, 0.0F, -9.0F, 4, 3, 1, p_i1151_1_);
/* 11 */     this.childYOffset = 4.0F;
/*    */   }
/*    */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\minecraft\client\model\ModelPig.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */