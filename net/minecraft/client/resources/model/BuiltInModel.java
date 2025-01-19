/*    */ package net.minecraft.client.resources.model;
/*    */ 
/*    */ import java.util.List;
/*    */ import net.minecraft.client.renderer.block.model.BakedQuad;
/*    */ import net.minecraft.client.renderer.block.model.ItemCameraTransforms;
/*    */ import net.minecraft.client.renderer.texture.TextureAtlasSprite;
/*    */ import net.minecraft.util.EnumFacing;
/*    */ 
/*    */ public class BuiltInModel
/*    */   implements IBakedModel {
/*    */   private ItemCameraTransforms cameraTransforms;
/*    */   
/*    */   public BuiltInModel(ItemCameraTransforms p_i46086_1_) {
/* 14 */     this.cameraTransforms = p_i46086_1_;
/*    */   }
/*    */   
/*    */   public List<BakedQuad> getFaceQuads(EnumFacing facing) {
/* 18 */     return null;
/*    */   }
/*    */   
/*    */   public List<BakedQuad> getGeneralQuads() {
/* 22 */     return null;
/*    */   }
/*    */   
/*    */   public boolean isAmbientOcclusion() {
/* 26 */     return false;
/*    */   }
/*    */   
/*    */   public boolean isGui3d() {
/* 30 */     return true;
/*    */   }
/*    */   
/*    */   public boolean isBuiltInRenderer() {
/* 34 */     return true;
/*    */   }
/*    */   
/*    */   public TextureAtlasSprite getParticleTexture() {
/* 38 */     return null;
/*    */   }
/*    */   
/*    */   public ItemCameraTransforms getItemCameraTransforms() {
/* 42 */     return this.cameraTransforms;
/*    */   }
/*    */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\minecraft\client\resources\model\BuiltInModel.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */