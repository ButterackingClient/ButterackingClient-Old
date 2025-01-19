/*    */ package net.optifine.shaders;
/*    */ 
/*    */ import java.nio.ByteBuffer;
/*    */ import net.optifine.texture.InternalFormat;
/*    */ import net.optifine.texture.PixelFormat;
/*    */ import net.optifine.texture.PixelType;
/*    */ import net.optifine.texture.TextureType;
/*    */ import org.lwjgl.opengl.GL11;
/*    */ import org.lwjgl.opengl.GL12;
/*    */ 
/*    */ public class CustomTextureRaw
/*    */   implements ICustomTexture {
/*    */   private TextureType type;
/*    */   private int textureUnit;
/*    */   private int textureId;
/*    */   
/*    */   public CustomTextureRaw(TextureType type, InternalFormat internalFormat, int width, int height, int depth, PixelFormat pixelFormat, PixelType pixelType, ByteBuffer data, int textureUnit, boolean blur, boolean clamp) {
/* 18 */     this.type = type;
/* 19 */     this.textureUnit = textureUnit;
/* 20 */     this.textureId = GL11.glGenTextures();
/* 21 */     GL11.glBindTexture(getTarget(), this.textureId);
/* 22 */     int i = clamp ? 33071 : 10497;
/* 23 */     int j = blur ? 9729 : 9728;
/*    */     
/* 25 */     switch (type) {
/*    */       case null:
/* 27 */         GL11.glTexImage1D(3552, 0, internalFormat.getId(), width, 0, pixelFormat.getId(), pixelType.getId(), data);
/* 28 */         GL11.glTexParameteri(3552, 10242, i);
/* 29 */         GL11.glTexParameteri(3552, 10240, j);
/* 30 */         GL11.glTexParameteri(3552, 10241, j);
/*    */         break;
/*    */       
/*    */       case TEXTURE_2D:
/* 34 */         GL11.glTexImage2D(3553, 0, internalFormat.getId(), width, height, 0, pixelFormat.getId(), pixelType.getId(), data);
/* 35 */         GL11.glTexParameteri(3553, 10242, i);
/* 36 */         GL11.glTexParameteri(3553, 10243, i);
/* 37 */         GL11.glTexParameteri(3553, 10240, j);
/* 38 */         GL11.glTexParameteri(3553, 10241, j);
/*    */         break;
/*    */       
/*    */       case TEXTURE_3D:
/* 42 */         GL12.glTexImage3D(32879, 0, internalFormat.getId(), width, height, depth, 0, pixelFormat.getId(), pixelType.getId(), data);
/* 43 */         GL11.glTexParameteri(32879, 10242, i);
/* 44 */         GL11.glTexParameteri(32879, 10243, i);
/* 45 */         GL11.glTexParameteri(32879, 32882, i);
/* 46 */         GL11.glTexParameteri(32879, 10240, j);
/* 47 */         GL11.glTexParameteri(32879, 10241, j);
/*    */         break;
/*    */       
/*    */       case TEXTURE_RECTANGLE:
/* 51 */         GL11.glTexImage2D(34037, 0, internalFormat.getId(), width, height, 0, pixelFormat.getId(), pixelType.getId(), data);
/* 52 */         GL11.glTexParameteri(34037, 10242, i);
/* 53 */         GL11.glTexParameteri(34037, 10243, i);
/* 54 */         GL11.glTexParameteri(34037, 10240, j);
/* 55 */         GL11.glTexParameteri(34037, 10241, j);
/*    */         break;
/*    */     } 
/* 58 */     GL11.glBindTexture(getTarget(), 0);
/*    */   }
/*    */   
/*    */   public int getTarget() {
/* 62 */     return this.type.getId();
/*    */   }
/*    */   
/*    */   public int getTextureId() {
/* 66 */     return this.textureId;
/*    */   }
/*    */   
/*    */   public int getTextureUnit() {
/* 70 */     return this.textureUnit;
/*    */   }
/*    */   
/*    */   public void deleteTexture() {
/* 74 */     if (this.textureId > 0) {
/* 75 */       GL11.glDeleteTextures(this.textureId);
/* 76 */       this.textureId = 0;
/*    */     } 
/*    */   }
/*    */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\optifine\shaders\CustomTextureRaw.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */