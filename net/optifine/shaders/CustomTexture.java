/*    */ package net.optifine.shaders;
/*    */ 
/*    */ import net.minecraft.client.renderer.texture.ITextureObject;
/*    */ import net.minecraft.client.renderer.texture.TextureUtil;
/*    */ 
/*    */ public class CustomTexture implements ICustomTexture {
/*  7 */   private int textureUnit = -1;
/*  8 */   private String path = null;
/*  9 */   private ITextureObject texture = null;
/*    */   
/*    */   public CustomTexture(int textureUnit, String path, ITextureObject texture) {
/* 12 */     this.textureUnit = textureUnit;
/* 13 */     this.path = path;
/* 14 */     this.texture = texture;
/*    */   }
/*    */   
/*    */   public int getTextureUnit() {
/* 18 */     return this.textureUnit;
/*    */   }
/*    */   
/*    */   public String getPath() {
/* 22 */     return this.path;
/*    */   }
/*    */   
/*    */   public ITextureObject getTexture() {
/* 26 */     return this.texture;
/*    */   }
/*    */   
/*    */   public int getTextureId() {
/* 30 */     return this.texture.getGlTextureId();
/*    */   }
/*    */   
/*    */   public void deleteTexture() {
/* 34 */     TextureUtil.deleteTexture(this.texture.getGlTextureId());
/*    */   }
/*    */   
/*    */   public int getTarget() {
/* 38 */     return 3553;
/*    */   }
/*    */   
/*    */   public String toString() {
/* 42 */     return "textureUnit: " + this.textureUnit + ", path: " + this.path + ", glTextureId: " + getTextureId();
/*    */   }
/*    */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\optifine\shaders\CustomTexture.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */