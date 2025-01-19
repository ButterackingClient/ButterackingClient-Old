/*     */ package net.minecraft.client.resources;
/*     */ 
/*     */ import com.google.common.collect.Lists;
/*     */ import java.io.ByteArrayOutputStream;
/*     */ import java.io.FileNotFoundException;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import java.io.PrintStream;
/*     */ import java.util.List;
/*     */ import java.util.Set;
/*     */ import net.minecraft.client.resources.data.IMetadataSerializer;
/*     */ import net.minecraft.util.ResourceLocation;
/*     */ import org.apache.logging.log4j.LogManager;
/*     */ import org.apache.logging.log4j.Logger;
/*     */ 
/*     */ public class FallbackResourceManager
/*     */   implements IResourceManager
/*     */ {
/*  19 */   private static final Logger logger = LogManager.getLogger();
/*  20 */   protected final List<IResourcePack> resourcePacks = Lists.newArrayList();
/*     */   private final IMetadataSerializer frmMetadataSerializer;
/*     */   
/*     */   public FallbackResourceManager(IMetadataSerializer frmMetadataSerializerIn) {
/*  24 */     this.frmMetadataSerializer = frmMetadataSerializerIn;
/*     */   }
/*     */   
/*     */   public void addResourcePack(IResourcePack resourcePack) {
/*  28 */     this.resourcePacks.add(resourcePack);
/*     */   }
/*     */   
/*     */   public Set<String> getResourceDomains() {
/*  32 */     return null;
/*     */   }
/*     */   
/*     */   public IResource getResource(ResourceLocation location) throws IOException {
/*  36 */     IResourcePack iresourcepack = null;
/*  37 */     ResourceLocation resourcelocation = getLocationMcmeta(location);
/*     */     
/*  39 */     for (int i = this.resourcePacks.size() - 1; i >= 0; i--) {
/*  40 */       IResourcePack iresourcepack1 = this.resourcePacks.get(i);
/*     */       
/*  42 */       if (iresourcepack == null && iresourcepack1.resourceExists(resourcelocation)) {
/*  43 */         iresourcepack = iresourcepack1;
/*     */       }
/*     */       
/*  46 */       if (iresourcepack1.resourceExists(location)) {
/*  47 */         InputStream inputstream = null;
/*     */         
/*  49 */         if (iresourcepack != null) {
/*  50 */           inputstream = getInputStream(resourcelocation, iresourcepack);
/*     */         }
/*     */         
/*  53 */         return new SimpleResource(iresourcepack1.getPackName(), location, getInputStream(location, iresourcepack1), inputstream, this.frmMetadataSerializer);
/*     */       } 
/*     */     } 
/*     */     
/*  57 */     throw new FileNotFoundException(location.toString());
/*     */   }
/*     */   
/*     */   protected InputStream getInputStream(ResourceLocation location, IResourcePack resourcePack) throws IOException {
/*  61 */     InputStream inputstream = resourcePack.getInputStream(location);
/*  62 */     return logger.isDebugEnabled() ? new InputStreamLeakedResourceLogger(inputstream, location, resourcePack.getPackName()) : inputstream;
/*     */   }
/*     */   
/*     */   public List<IResource> getAllResources(ResourceLocation location) throws IOException {
/*  66 */     List<IResource> list = Lists.newArrayList();
/*  67 */     ResourceLocation resourcelocation = getLocationMcmeta(location);
/*     */     
/*  69 */     for (IResourcePack iresourcepack : this.resourcePacks) {
/*  70 */       if (iresourcepack.resourceExists(location)) {
/*  71 */         InputStream inputstream = iresourcepack.resourceExists(resourcelocation) ? getInputStream(resourcelocation, iresourcepack) : null;
/*  72 */         list.add(new SimpleResource(iresourcepack.getPackName(), location, getInputStream(location, iresourcepack), inputstream, this.frmMetadataSerializer));
/*     */       } 
/*     */     } 
/*     */     
/*  76 */     if (list.isEmpty()) {
/*  77 */       throw new FileNotFoundException(location.toString());
/*     */     }
/*  79 */     return list;
/*     */   }
/*     */ 
/*     */   
/*     */   static ResourceLocation getLocationMcmeta(ResourceLocation location) {
/*  84 */     return new ResourceLocation(location.getResourceDomain(), String.valueOf(location.getResourcePath()) + ".mcmeta");
/*     */   }
/*     */   
/*     */   static class InputStreamLeakedResourceLogger extends InputStream {
/*     */     private final InputStream inputStream;
/*     */     private final String message;
/*     */     private boolean isClosed = false;
/*     */     
/*     */     public InputStreamLeakedResourceLogger(InputStream p_i46093_1_, ResourceLocation location, String resourcePack) {
/*  93 */       this.inputStream = p_i46093_1_;
/*  94 */       ByteArrayOutputStream bytearrayoutputstream = new ByteArrayOutputStream();
/*  95 */       (new Exception()).printStackTrace(new PrintStream(bytearrayoutputstream));
/*  96 */       this.message = "Leaked resource: '" + location + "' loaded from pack: '" + resourcePack + "'\n" + bytearrayoutputstream.toString();
/*     */     }
/*     */     
/*     */     public void close() throws IOException {
/* 100 */       this.inputStream.close();
/* 101 */       this.isClosed = true;
/*     */     }
/*     */     
/*     */     protected void finalize() throws Throwable {
/* 105 */       if (!this.isClosed) {
/* 106 */         FallbackResourceManager.logger.warn(this.message);
/*     */       }
/*     */       
/* 109 */       super.finalize();
/*     */     }
/*     */     
/*     */     public int read() throws IOException {
/* 113 */       return this.inputStream.read();
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\minecraft\client\resources\FallbackResourceManager.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */