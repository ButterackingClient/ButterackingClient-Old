/*    */ package net.minecraft.client.model;
/*    */ 
/*    */ import net.minecraft.util.Vec3;
/*    */ 
/*    */ public class PositionTextureVertex {
/*    */   public Vec3 vector3D;
/*    */   public float texturePositionX;
/*    */   public float texturePositionY;
/*    */   
/*    */   public PositionTextureVertex(float p_i1158_1_, float p_i1158_2_, float p_i1158_3_, float p_i1158_4_, float p_i1158_5_) {
/* 11 */     this(new Vec3(p_i1158_1_, p_i1158_2_, p_i1158_3_), p_i1158_4_, p_i1158_5_);
/*    */   }
/*    */   
/*    */   public PositionTextureVertex setTexturePosition(float p_78240_1_, float p_78240_2_) {
/* 15 */     return new PositionTextureVertex(this, p_78240_1_, p_78240_2_);
/*    */   }
/*    */   
/*    */   public PositionTextureVertex(PositionTextureVertex textureVertex, float texturePositionXIn, float texturePositionYIn) {
/* 19 */     this.vector3D = textureVertex.vector3D;
/* 20 */     this.texturePositionX = texturePositionXIn;
/* 21 */     this.texturePositionY = texturePositionYIn;
/*    */   }
/*    */   
/*    */   public PositionTextureVertex(Vec3 vector3DIn, float texturePositionXIn, float texturePositionYIn) {
/* 25 */     this.vector3D = vector3DIn;
/* 26 */     this.texturePositionX = texturePositionXIn;
/* 27 */     this.texturePositionY = texturePositionYIn;
/*    */   }
/*    */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\minecraft\client\model\PositionTextureVertex.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */