/*    */ package net.optifine.shaders;
/*    */ 
/*    */ import java.io.InputStream;
/*    */ 
/*    */ public class ShaderPackDefault
/*    */   implements IShaderPack {
/*    */   public void close() {}
/*    */   
/*    */   public InputStream getResourceAsStream(String resName) {
/* 10 */     return ShaderPackDefault.class.getResourceAsStream(resName);
/*    */   }
/*    */   
/*    */   public String getName() {
/* 14 */     return "(internal)";
/*    */   }
/*    */   
/*    */   public boolean hasDirectory(String name) {
/* 18 */     return false;
/*    */   }
/*    */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\optifine\shaders\ShaderPackDefault.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */