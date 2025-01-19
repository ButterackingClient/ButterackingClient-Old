/*    */ package net.optifine.shaders;
/*    */ 
/*    */ import java.nio.ByteBuffer;
/*    */ import net.minecraft.client.renderer.GlStateManager;
/*    */ import org.lwjgl.BufferUtils;
/*    */ import org.lwjgl.opengl.GL11;
/*    */ 
/*    */ public class HFNoiseTexture
/*    */   implements ICustomTexture {
/* 10 */   private int texID = GL11.glGenTextures();
/* 11 */   private int textureUnit = 15;
/*    */   
/*    */   public HFNoiseTexture(int width, int height) {
/* 14 */     byte[] abyte = genHFNoiseImage(width, height);
/* 15 */     ByteBuffer bytebuffer = BufferUtils.createByteBuffer(abyte.length);
/* 16 */     bytebuffer.put(abyte);
/* 17 */     bytebuffer.flip();
/* 18 */     GlStateManager.bindTexture(this.texID);
/* 19 */     GL11.glTexImage2D(3553, 0, 6407, width, height, 0, 6407, 5121, bytebuffer);
/* 20 */     GL11.glTexParameteri(3553, 10242, 10497);
/* 21 */     GL11.glTexParameteri(3553, 10243, 10497);
/* 22 */     GL11.glTexParameteri(3553, 10240, 9729);
/* 23 */     GL11.glTexParameteri(3553, 10241, 9729);
/* 24 */     GlStateManager.bindTexture(0);
/*    */   }
/*    */   
/*    */   public int getID() {
/* 28 */     return this.texID;
/*    */   }
/*    */   
/*    */   public void deleteTexture() {
/* 32 */     GlStateManager.deleteTexture(this.texID);
/* 33 */     this.texID = 0;
/*    */   }
/*    */   
/*    */   private int random(int seed) {
/* 37 */     seed ^= seed << 13;
/* 38 */     seed ^= seed >> 17;
/* 39 */     seed ^= seed << 5;
/* 40 */     return seed;
/*    */   }
/*    */   
/*    */   private byte random(int x, int y, int z) {
/* 44 */     int i = (random(x) + random(y * 19)) * random(z * 23) - z;
/* 45 */     return (byte)(random(i) % 128);
/*    */   }
/*    */   
/*    */   private byte[] genHFNoiseImage(int width, int height) {
/* 49 */     byte[] abyte = new byte[width * height * 3];
/* 50 */     int i = 0;
/*    */     
/* 52 */     for (int j = 0; j < height; j++) {
/* 53 */       for (int k = 0; k < width; k++) {
/* 54 */         for (int l = 1; l < 4; l++) {
/* 55 */           abyte[i++] = random(k, j, l);
/*    */         }
/*    */       } 
/*    */     } 
/*    */     
/* 60 */     return abyte;
/*    */   }
/*    */   
/*    */   public int getTextureId() {
/* 64 */     return this.texID;
/*    */   }
/*    */   
/*    */   public int getTextureUnit() {
/* 68 */     return this.textureUnit;
/*    */   }
/*    */   
/*    */   public int getTarget() {
/* 72 */     return 3553;
/*    */   }
/*    */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\optifine\shaders\HFNoiseTexture.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */