/*    */ package net.minecraft.client.renderer;
/*    */ 
/*    */ import java.nio.ByteBuffer;
/*    */ import java.nio.ByteOrder;
/*    */ import java.nio.FloatBuffer;
/*    */ import java.nio.IntBuffer;
/*    */ import org.lwjgl.opengl.GL11;
/*    */ import org.lwjgl.util.glu.GLU;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class GLAllocation
/*    */ {
/*    */   public static synchronized int generateDisplayLists(int range) {
/* 17 */     int i = GL11.glGenLists(range);
/*    */     
/* 19 */     if (i == 0) {
/* 20 */       int j = GL11.glGetError();
/* 21 */       String s = "No error code reported";
/*    */       
/* 23 */       if (j != 0) {
/* 24 */         s = GLU.gluErrorString(j);
/*    */       }
/*    */       
/* 27 */       throw new IllegalStateException("glGenLists returned an ID of 0 for a count of " + range + ", GL error (" + j + "): " + s);
/*    */     } 
/* 29 */     return i;
/*    */   }
/*    */ 
/*    */   
/*    */   public static synchronized void deleteDisplayLists(int list, int range) {
/* 34 */     GL11.glDeleteLists(list, range);
/*    */   }
/*    */   
/*    */   public static synchronized void deleteDisplayLists(int list) {
/* 38 */     GL11.glDeleteLists(list, 1);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public static synchronized ByteBuffer createDirectByteBuffer(int capacity) {
/* 45 */     return ByteBuffer.allocateDirect(capacity).order(ByteOrder.nativeOrder());
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public static IntBuffer createDirectIntBuffer(int capacity) {
/* 52 */     return createDirectByteBuffer(capacity << 2).asIntBuffer();
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public static FloatBuffer createDirectFloatBuffer(int capacity) {
/* 60 */     return createDirectByteBuffer(capacity << 2).asFloatBuffer();
/*    */   }
/*    */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\minecraft\client\renderer\GLAllocation.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */