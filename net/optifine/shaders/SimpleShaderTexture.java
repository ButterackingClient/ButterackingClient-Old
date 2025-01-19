/*    */ package net.optifine.shaders;
/*    */ 
/*    */ import com.google.gson.JsonObject;
/*    */ import com.google.gson.JsonParser;
/*    */ import java.awt.image.BufferedImage;
/*    */ import java.io.BufferedReader;
/*    */ import java.io.FileNotFoundException;
/*    */ import java.io.IOException;
/*    */ import java.io.InputStream;
/*    */ import java.io.InputStreamReader;
/*    */ import java.util.ArrayList;
/*    */ import net.minecraft.client.renderer.texture.AbstractTexture;
/*    */ import net.minecraft.client.renderer.texture.TextureUtil;
/*    */ import net.minecraft.client.resources.IResourceManager;
/*    */ import net.minecraft.client.resources.data.AnimationMetadataSection;
/*    */ import net.minecraft.client.resources.data.AnimationMetadataSectionSerializer;
/*    */ import net.minecraft.client.resources.data.FontMetadataSection;
/*    */ import net.minecraft.client.resources.data.FontMetadataSectionSerializer;
/*    */ import net.minecraft.client.resources.data.IMetadataSectionSerializer;
/*    */ import net.minecraft.client.resources.data.IMetadataSerializer;
/*    */ import net.minecraft.client.resources.data.LanguageMetadataSection;
/*    */ import net.minecraft.client.resources.data.LanguageMetadataSectionSerializer;
/*    */ import net.minecraft.client.resources.data.PackMetadataSection;
/*    */ import net.minecraft.client.resources.data.PackMetadataSectionSerializer;
/*    */ import net.minecraft.client.resources.data.TextureMetadataSection;
/*    */ import net.minecraft.client.resources.data.TextureMetadataSectionSerializer;
/*    */ import org.apache.commons.io.IOUtils;
/*    */ 
/*    */ public class SimpleShaderTexture
/*    */   extends AbstractTexture
/*    */ {
/*    */   private String texturePath;
/* 33 */   private static final IMetadataSerializer METADATA_SERIALIZER = makeMetadataSerializer();
/*    */   
/*    */   public SimpleShaderTexture(String texturePath) {
/* 36 */     this.texturePath = texturePath;
/*    */   }
/*    */   
/*    */   public void loadTexture(IResourceManager resourceManager) throws IOException {
/* 40 */     deleteGlTexture();
/* 41 */     InputStream inputstream = Shaders.getShaderPackResourceStream(this.texturePath);
/*    */     
/* 43 */     if (inputstream == null) {
/* 44 */       throw new FileNotFoundException("Shader texture not found: " + this.texturePath);
/*    */     }
/*    */     try {
/* 47 */       BufferedImage bufferedimage = TextureUtil.readBufferedImage(inputstream);
/* 48 */       TextureMetadataSection texturemetadatasection = loadTextureMetadataSection(this.texturePath, new TextureMetadataSection(false, false, new ArrayList()));
/* 49 */       TextureUtil.uploadTextureImageAllocate(getGlTextureId(), bufferedimage, texturemetadatasection.getTextureBlur(), texturemetadatasection.getTextureClamp());
/*    */     } finally {
/* 51 */       IOUtils.closeQuietly(inputstream);
/*    */     } 
/*    */   }
/*    */ 
/*    */   
/*    */   public static TextureMetadataSection loadTextureMetadataSection(String texturePath, TextureMetadataSection def) {
/* 57 */     String s = String.valueOf(texturePath) + ".mcmeta";
/* 58 */     String s1 = "texture";
/* 59 */     InputStream inputstream = Shaders.getShaderPackResourceStream(s);
/*    */     
/* 61 */     if (inputstream != null) {
/* 62 */       TextureMetadataSection texturemetadatasection1; IMetadataSerializer imetadataserializer = METADATA_SERIALIZER;
/* 63 */       BufferedReader bufferedreader = new BufferedReader(new InputStreamReader(inputstream));
/*    */ 
/*    */ 
/*    */       
/* 67 */       try { JsonObject jsonobject = (new JsonParser()).parse(bufferedreader).getAsJsonObject();
/* 68 */         TextureMetadataSection texturemetadatasection = (TextureMetadataSection)imetadataserializer.parseMetadataSection(s1, jsonobject);
/*    */ 
/*    */ 
/*    */ 
/*    */         
/*    */          }
/*    */       
/* 75 */       catch (RuntimeException runtimeexception)
/* 76 */       { SMCLog.warning("Error reading metadata: " + s);
/* 77 */         SMCLog.warning(runtimeexception.getClass().getName() + ": " + runtimeexception.getMessage());
/* 78 */         return def; }
/*    */       finally
/* 80 */       { IOUtils.closeQuietly(bufferedreader);
/* 81 */         IOUtils.closeQuietly(inputstream); }  IOUtils.closeQuietly(bufferedreader); IOUtils.closeQuietly(inputstream);
/*    */ 
/*    */       
/* 84 */       return texturemetadatasection1;
/*    */     } 
/* 86 */     return def;
/*    */   }
/*    */ 
/*    */   
/*    */   private static IMetadataSerializer makeMetadataSerializer() {
/* 91 */     IMetadataSerializer imetadataserializer = new IMetadataSerializer();
/* 92 */     imetadataserializer.registerMetadataSectionType((IMetadataSectionSerializer)new TextureMetadataSectionSerializer(), TextureMetadataSection.class);
/* 93 */     imetadataserializer.registerMetadataSectionType((IMetadataSectionSerializer)new FontMetadataSectionSerializer(), FontMetadataSection.class);
/* 94 */     imetadataserializer.registerMetadataSectionType((IMetadataSectionSerializer)new AnimationMetadataSectionSerializer(), AnimationMetadataSection.class);
/* 95 */     imetadataserializer.registerMetadataSectionType((IMetadataSectionSerializer)new PackMetadataSectionSerializer(), PackMetadataSection.class);
/* 96 */     imetadataserializer.registerMetadataSectionType((IMetadataSectionSerializer)new LanguageMetadataSectionSerializer(), LanguageMetadataSection.class);
/* 97 */     return imetadataserializer;
/*    */   }
/*    */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\optifine\shaders\SimpleShaderTexture.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */