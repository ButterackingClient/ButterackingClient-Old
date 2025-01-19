/*    */ package net.optifine.shaders;
/*    */ 
/*    */ import net.minecraft.client.Minecraft;
/*    */ import net.minecraft.client.renderer.texture.AbstractTexture;
/*    */ import net.minecraft.client.renderer.texture.ITextureObject;
/*    */ import net.minecraft.client.renderer.texture.SimpleTexture;
/*    */ import net.minecraft.client.renderer.texture.TextureManager;
/*    */ import net.minecraft.util.ResourceLocation;
/*    */ 
/*    */ public class CustomTextureLocation implements ICustomTexture {
/* 11 */   private int textureUnit = -1;
/*    */   private ResourceLocation location;
/* 13 */   private int variant = 0;
/*    */   private ITextureObject texture;
/*    */   public static final int VARIANT_BASE = 0;
/*    */   public static final int VARIANT_NORMAL = 1;
/*    */   public static final int VARIANT_SPECULAR = 2;
/*    */   
/*    */   public CustomTextureLocation(int textureUnit, ResourceLocation location, int variant) {
/* 20 */     this.textureUnit = textureUnit;
/* 21 */     this.location = location;
/* 22 */     this.variant = variant;
/*    */   }
/*    */   
/*    */   public ITextureObject getTexture() {
/* 26 */     if (this.texture == null) {
/* 27 */       TextureManager texturemanager = Minecraft.getMinecraft().getTextureManager();
/* 28 */       this.texture = texturemanager.getTexture(this.location);
/*    */       
/* 30 */       if (this.texture == null) {
/* 31 */         this.texture = (ITextureObject)new SimpleTexture(this.location);
/* 32 */         texturemanager.loadTexture(this.location, this.texture);
/* 33 */         this.texture = texturemanager.getTexture(this.location);
/*    */       } 
/*    */     } 
/*    */     
/* 37 */     return this.texture;
/*    */   }
/*    */   
/*    */   public int getTextureId() {
/* 41 */     ITextureObject itextureobject = getTexture();
/*    */     
/* 43 */     if (this.variant != 0 && itextureobject instanceof AbstractTexture) {
/* 44 */       AbstractTexture abstracttexture = (AbstractTexture)itextureobject;
/* 45 */       MultiTexID multitexid = abstracttexture.multiTex;
/*    */       
/* 47 */       if (multitexid != null) {
/* 48 */         if (this.variant == 1) {
/* 49 */           return multitexid.norm;
/*    */         }
/*    */         
/* 52 */         if (this.variant == 2) {
/* 53 */           return multitexid.spec;
/*    */         }
/*    */       } 
/*    */     } 
/*    */     
/* 58 */     return itextureobject.getGlTextureId();
/*    */   }
/*    */   
/*    */   public int getTextureUnit() {
/* 62 */     return this.textureUnit;
/*    */   }
/*    */ 
/*    */   
/*    */   public void deleteTexture() {}
/*    */   
/*    */   public int getTarget() {
/* 69 */     return 3553;
/*    */   }
/*    */   
/*    */   public String toString() {
/* 73 */     return "textureUnit: " + this.textureUnit + ", location: " + this.location + ", glTextureId: " + ((this.texture != null) ? (String)Integer.valueOf(this.texture.getGlTextureId()) : "");
/*    */   }
/*    */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\optifine\shaders\CustomTextureLocation.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */