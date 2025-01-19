/*    */ package net.minecraft.client.model;
/*    */ 
/*    */ import net.minecraft.entity.Entity;
/*    */ 
/*    */ public class ModelSkeletonHead extends ModelBase {
/*    */   public ModelRenderer skeletonHead;
/*    */   
/*    */   public ModelSkeletonHead() {
/*  9 */     this(0, 35, 64, 64);
/*    */   }
/*    */   
/*    */   public ModelSkeletonHead(int p_i1155_1_, int p_i1155_2_, int p_i1155_3_, int p_i1155_4_) {
/* 13 */     this.textureWidth = p_i1155_3_;
/* 14 */     this.textureHeight = p_i1155_4_;
/* 15 */     this.skeletonHead = new ModelRenderer(this, p_i1155_1_, p_i1155_2_);
/* 16 */     this.skeletonHead.addBox(-4.0F, -8.0F, -4.0F, 8, 8, 8, 0.0F);
/* 17 */     this.skeletonHead.setRotationPoint(0.0F, 0.0F, 0.0F);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void render(Entity entityIn, float p_78088_2_, float p_78088_3_, float p_78088_4_, float p_78088_5_, float p_78088_6_, float scale) {
/* 24 */     setRotationAngles(p_78088_2_, p_78088_3_, p_78088_4_, p_78088_5_, p_78088_6_, scale, entityIn);
/* 25 */     this.skeletonHead.render(scale);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void setRotationAngles(float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float scaleFactor, Entity entityIn) {
/* 34 */     super.setRotationAngles(limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scaleFactor, entityIn);
/* 35 */     this.skeletonHead.rotateAngleY = netHeadYaw / 57.295776F;
/* 36 */     this.skeletonHead.rotateAngleX = headPitch / 57.295776F;
/*    */   }
/*    */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\minecraft\client\model\ModelSkeletonHead.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */