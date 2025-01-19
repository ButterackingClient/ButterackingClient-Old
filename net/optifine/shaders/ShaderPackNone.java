/*    */ package net.optifine.shaders;
/*    */ 
/*    */ import java.io.InputStream;
/*    */ 
/*    */ public class ShaderPackNone
/*    */   implements IShaderPack {
/*    */   public void close() {}
/*    */   
/*    */   public InputStream getResourceAsStream(String resName) {
/* 10 */     return null;
/*    */   }
/*    */   
/*    */   public boolean hasDirectory(String name) {
/* 14 */     return false;
/*    */   }
/*    */   
/*    */   public String getName() {
/* 18 */     return "OFF";
/*    */   }
/*    */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\optifine\shaders\ShaderPackNone.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */