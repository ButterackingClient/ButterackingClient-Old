/*    */ package net.minecraft.client.resources;
/*    */ 
/*    */ import com.google.common.collect.ImmutableSet;
/*    */ import java.awt.image.BufferedImage;
/*    */ import java.io.File;
/*    */ import java.io.FileInputStream;
/*    */ import java.io.FileNotFoundException;
/*    */ import java.io.IOException;
/*    */ import java.io.InputStream;
/*    */ import java.util.Map;
/*    */ import java.util.Set;
/*    */ import net.minecraft.client.renderer.texture.TextureUtil;
/*    */ import net.minecraft.client.resources.data.IMetadataSerializer;
/*    */ import net.minecraft.util.ResourceLocation;
/*    */ import net.optifine.reflect.ReflectorForge;
/*    */ 
/*    */ 
/*    */ public class DefaultResourcePack
/*    */   implements IResourcePack
/*    */ {
/* 21 */   public static final Set<String> defaultResourceDomains = (Set<String>)ImmutableSet.of("minecraft", "realms");
/*    */   private final Map<String, File> mapAssets;
/*    */   
/*    */   public DefaultResourcePack(Map<String, File> mapAssetsIn) {
/* 25 */     this.mapAssets = mapAssetsIn;
/*    */   }
/*    */   
/*    */   public InputStream getInputStream(ResourceLocation location) throws IOException {
/* 29 */     InputStream inputstream = getResourceStream(location);
/*    */     
/* 31 */     if (inputstream != null) {
/* 32 */       return inputstream;
/*    */     }
/* 34 */     InputStream inputstream1 = getInputStreamAssets(location);
/*    */     
/* 36 */     if (inputstream1 != null) {
/* 37 */       return inputstream1;
/*    */     }
/* 39 */     throw new FileNotFoundException(location.getResourcePath());
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public InputStream getInputStreamAssets(ResourceLocation location) throws IOException, FileNotFoundException {
/* 45 */     File file1 = this.mapAssets.get(location.toString());
/* 46 */     return (file1 != null && file1.isFile()) ? new FileInputStream(file1) : null;
/*    */   }
/*    */   
/*    */   private InputStream getResourceStream(ResourceLocation location) {
/* 50 */     String s = "/assets/" + location.getResourceDomain() + "/" + location.getResourcePath();
/* 51 */     InputStream inputstream = ReflectorForge.getOptiFineResourceStream(s);
/* 52 */     return (inputstream != null) ? inputstream : DefaultResourcePack.class.getResourceAsStream(s);
/*    */   }
/*    */   
/*    */   public boolean resourceExists(ResourceLocation location) {
/* 56 */     return !(getResourceStream(location) == null && !this.mapAssets.containsKey(location.toString()));
/*    */   }
/*    */   
/*    */   public Set<String> getResourceDomains() {
/* 60 */     return defaultResourceDomains;
/*    */   }
/*    */   
/*    */   public <T extends net.minecraft.client.resources.data.IMetadataSection> T getPackMetadata(IMetadataSerializer metadataSerializer, String metadataSectionName) throws IOException {
/*    */     try {
/* 65 */       InputStream inputstream = new FileInputStream(this.mapAssets.get("pack.mcmeta"));
/* 66 */       return AbstractResourcePack.readMetadata(metadataSerializer, inputstream, metadataSectionName);
/* 67 */     } catch (RuntimeException var4) {
/* 68 */       return null;
/* 69 */     } catch (FileNotFoundException var5) {
/* 70 */       return null;
/*    */     } 
/*    */   }
/*    */   
/*    */   public BufferedImage getPackImage() throws IOException {
/* 75 */     return TextureUtil.readBufferedImage(DefaultResourcePack.class.getResourceAsStream("/" + (new ResourceLocation("pack.png")).getResourcePath()));
/*    */   }
/*    */   
/*    */   public String getPackName() {
/* 79 */     return "Default";
/*    */   }
/*    */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\minecraft\client\resources\DefaultResourcePack.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */