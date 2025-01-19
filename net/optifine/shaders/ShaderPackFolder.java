/*    */ package net.optifine.shaders;
/*    */ 
/*    */ import java.io.BufferedInputStream;
/*    */ import java.io.File;
/*    */ import java.io.FileInputStream;
/*    */ import java.io.InputStream;
/*    */ import net.optifine.util.StrUtils;
/*    */ 
/*    */ public class ShaderPackFolder
/*    */   implements IShaderPack {
/*    */   protected File packFile;
/*    */   
/*    */   public ShaderPackFolder(String name, File file) {
/* 14 */     this.packFile = file;
/*    */   }
/*    */ 
/*    */   
/*    */   public void close() {}
/*    */   
/*    */   public InputStream getResourceAsStream(String resName) {
/*    */     try {
/* 22 */       String s = StrUtils.removePrefixSuffix(resName, "/", "/");
/* 23 */       File file1 = new File(this.packFile, s);
/* 24 */       return !file1.exists() ? null : new BufferedInputStream(new FileInputStream(file1));
/* 25 */     } catch (Exception var4) {
/* 26 */       return null;
/*    */     } 
/*    */   }
/*    */   
/*    */   public boolean hasDirectory(String name) {
/* 31 */     File file1 = new File(this.packFile, name.substring(1));
/* 32 */     return !file1.exists() ? false : file1.isDirectory();
/*    */   }
/*    */   
/*    */   public String getName() {
/* 36 */     return this.packFile.getName();
/*    */   }
/*    */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\optifine\shaders\ShaderPackFolder.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */