/*    */ package net.minecraft.client.renderer.texture;
/*    */ 
/*    */ import net.minecraft.client.renderer.GlStateManager;
/*    */ import net.optifine.shaders.MultiTexID;
/*    */ import net.optifine.shaders.ShadersTex;
/*    */ import org.lwjgl.opengl.GL11;
/*    */ 
/*    */ public abstract class AbstractTexture implements ITextureObject {
/*  9 */   protected int glTextureId = -1;
/*    */   protected boolean blur;
/*    */   protected boolean mipmap;
/*    */   protected boolean blurLast;
/*    */   protected boolean mipmapLast;
/*    */   public MultiTexID multiTex;
/*    */   
/*    */   public void setBlurMipmapDirect(boolean p_174937_1_, boolean p_174937_2_) {
/* 17 */     this.blur = p_174937_1_;
/* 18 */     this.mipmap = p_174937_2_;
/* 19 */     int i = -1;
/* 20 */     int j = -1;
/*    */     
/* 22 */     if (p_174937_1_) {
/* 23 */       i = p_174937_2_ ? 9987 : 9729;
/* 24 */       j = 9729;
/*    */     } else {
/* 26 */       i = p_174937_2_ ? 9986 : 9728;
/* 27 */       j = 9728;
/*    */     } 
/*    */     
/* 30 */     GlStateManager.bindTexture(getGlTextureId());
/* 31 */     GL11.glTexParameteri(3553, 10241, i);
/* 32 */     GL11.glTexParameteri(3553, 10240, j);
/*    */   }
/*    */   
/*    */   public void setBlurMipmap(boolean p_174936_1_, boolean p_174936_2_) {
/* 36 */     this.blurLast = this.blur;
/* 37 */     this.mipmapLast = this.mipmap;
/* 38 */     setBlurMipmapDirect(p_174936_1_, p_174936_2_);
/*    */   }
/*    */   
/*    */   public void restoreLastBlurMipmap() {
/* 42 */     setBlurMipmapDirect(this.blurLast, this.mipmapLast);
/*    */   }
/*    */   
/*    */   public int getGlTextureId() {
/* 46 */     if (this.glTextureId == -1) {
/* 47 */       this.glTextureId = TextureUtil.glGenTextures();
/*    */     }
/*    */     
/* 50 */     return this.glTextureId;
/*    */   }
/*    */   
/*    */   public void deleteGlTexture() {
/* 54 */     ShadersTex.deleteTextures(this, this.glTextureId);
/*    */     
/* 56 */     if (this.glTextureId != -1) {
/* 57 */       TextureUtil.deleteTexture(this.glTextureId);
/* 58 */       this.glTextureId = -1;
/*    */     } 
/*    */   }
/*    */   
/*    */   public MultiTexID getMultiTexID() {
/* 63 */     return ShadersTex.getMultiTexID(this);
/*    */   }
/*    */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\minecraft\client\renderer\texture\AbstractTexture.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */