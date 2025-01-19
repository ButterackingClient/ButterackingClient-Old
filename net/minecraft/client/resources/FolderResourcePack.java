/*    */ package net.minecraft.client.resources;
/*    */ 
/*    */ import com.google.common.collect.Sets;
/*    */ import java.io.BufferedInputStream;
/*    */ import java.io.File;
/*    */ import java.io.FileFilter;
/*    */ import java.io.FileInputStream;
/*    */ import java.io.IOException;
/*    */ import java.io.InputStream;
/*    */ import java.util.Set;
/*    */ import org.apache.commons.io.filefilter.DirectoryFileFilter;
/*    */ 
/*    */ public class FolderResourcePack
/*    */   extends AbstractResourcePack
/*    */ {
/*    */   public FolderResourcePack(File resourcePackFileIn) {
/* 17 */     super(resourcePackFileIn);
/*    */   }
/*    */   
/*    */   protected InputStream getInputStreamByName(String name) throws IOException {
/* 21 */     return new BufferedInputStream(new FileInputStream(new File(this.resourcePackFile, name)));
/*    */   }
/*    */   
/*    */   protected boolean hasResourceName(String name) {
/* 25 */     return (new File(this.resourcePackFile, name)).isFile();
/*    */   }
/*    */   
/*    */   public Set<String> getResourceDomains() {
/* 29 */     Set<String> set = Sets.newHashSet();
/* 30 */     File file1 = new File(this.resourcePackFile, "assets/");
/*    */     
/* 32 */     if (file1.isDirectory()) {
/* 33 */       byte b; int i; File[] arrayOfFile; for (i = (arrayOfFile = file1.listFiles((FileFilter)DirectoryFileFilter.DIRECTORY)).length, b = 0; b < i; ) { File file2 = arrayOfFile[b];
/* 34 */         String s = getRelativeName(file1, file2);
/*    */         
/* 36 */         if (!s.equals(s.toLowerCase())) {
/* 37 */           logNameNotLowercase(s);
/*    */         } else {
/* 39 */           set.add(s.substring(0, s.length() - 1));
/*    */         } 
/*    */         b++; }
/*    */     
/*    */     } 
/* 44 */     return set;
/*    */   }
/*    */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\minecraft\client\resources\FolderResourcePack.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */