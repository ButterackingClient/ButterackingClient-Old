/*    */ package net.minecraft.client.resources;
/*    */ 
/*    */ import com.google.common.base.Splitter;
/*    */ import com.google.common.collect.Lists;
/*    */ import com.google.common.collect.Sets;
/*    */ import java.io.Closeable;
/*    */ import java.io.File;
/*    */ import java.io.IOException;
/*    */ import java.io.InputStream;
/*    */ import java.util.Collections;
/*    */ import java.util.Enumeration;
/*    */ import java.util.List;
/*    */ import java.util.Set;
/*    */ import java.util.zip.ZipEntry;
/*    */ import java.util.zip.ZipFile;
/*    */ 
/*    */ public class FileResourcePack
/*    */   extends AbstractResourcePack implements Closeable {
/* 19 */   public static final Splitter entryNameSplitter = Splitter.on('/').omitEmptyStrings().limit(3);
/*    */   private ZipFile resourcePackZipFile;
/*    */   
/*    */   public FileResourcePack(File resourcePackFileIn) {
/* 23 */     super(resourcePackFileIn);
/*    */   }
/*    */   
/*    */   private ZipFile getResourcePackZipFile() throws IOException {
/* 27 */     if (this.resourcePackZipFile == null) {
/* 28 */       this.resourcePackZipFile = new ZipFile(this.resourcePackFile);
/*    */     }
/*    */     
/* 31 */     return this.resourcePackZipFile;
/*    */   }
/*    */   
/*    */   protected InputStream getInputStreamByName(String name) throws IOException {
/* 35 */     ZipFile zipfile = getResourcePackZipFile();
/* 36 */     ZipEntry zipentry = zipfile.getEntry(name);
/*    */     
/* 38 */     if (zipentry == null) {
/* 39 */       throw new ResourcePackFileNotFoundException(this.resourcePackFile, name);
/*    */     }
/* 41 */     return zipfile.getInputStream(zipentry);
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean hasResourceName(String name) {
/*    */     try {
/* 47 */       return (getResourcePackZipFile().getEntry(name) != null);
/* 48 */     } catch (IOException var3) {
/* 49 */       return false;
/*    */     } 
/*    */   }
/*    */ 
/*    */   
/*    */   public Set<String> getResourceDomains() {
/*    */     ZipFile zipfile;
/*    */     try {
/* 57 */       zipfile = getResourcePackZipFile();
/* 58 */     } catch (IOException var8) {
/* 59 */       return Collections.emptySet();
/*    */     } 
/*    */     
/* 62 */     Enumeration<? extends ZipEntry> enumeration = zipfile.entries();
/* 63 */     Set<String> set = Sets.newHashSet();
/*    */     
/* 65 */     while (enumeration.hasMoreElements()) {
/* 66 */       ZipEntry zipentry = enumeration.nextElement();
/* 67 */       String s = zipentry.getName();
/*    */       
/* 69 */       if (s.startsWith("assets/")) {
/* 70 */         List<String> list = Lists.newArrayList(entryNameSplitter.split(s));
/*    */         
/* 72 */         if (list.size() > 1) {
/* 73 */           String s1 = list.get(1);
/*    */           
/* 75 */           if (!s1.equals(s1.toLowerCase())) {
/* 76 */             logNameNotLowercase(s1); continue;
/*    */           } 
/* 78 */           set.add(s1);
/*    */         } 
/*    */       } 
/*    */     } 
/*    */ 
/*    */     
/* 84 */     return set;
/*    */   }
/*    */   
/*    */   protected void finalize() throws Throwable {
/* 88 */     close();
/* 89 */     super.finalize();
/*    */   }
/*    */   
/*    */   public void close() throws IOException {
/* 93 */     if (this.resourcePackZipFile != null) {
/* 94 */       this.resourcePackZipFile.close();
/* 95 */       this.resourcePackZipFile = null;
/*    */     } 
/*    */   }
/*    */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\minecraft\client\resources\FileResourcePack.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */