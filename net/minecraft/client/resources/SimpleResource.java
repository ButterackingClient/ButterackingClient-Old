/*     */ package net.minecraft.client.resources;
/*     */ 
/*     */ import com.google.common.collect.Maps;
/*     */ import com.google.gson.JsonObject;
/*     */ import com.google.gson.JsonParser;
/*     */ import java.io.BufferedReader;
/*     */ import java.io.InputStream;
/*     */ import java.io.InputStreamReader;
/*     */ import java.util.Map;
/*     */ import net.minecraft.client.resources.data.IMetadataSection;
/*     */ import net.minecraft.client.resources.data.IMetadataSerializer;
/*     */ import net.minecraft.util.ResourceLocation;
/*     */ import org.apache.commons.io.IOUtils;
/*     */ 
/*     */ 
/*     */ public class SimpleResource
/*     */   implements IResource
/*     */ {
/*  19 */   private final Map<String, IMetadataSection> mapMetadataSections = Maps.newHashMap();
/*     */   private final String resourcePackName;
/*     */   private final ResourceLocation srResourceLocation;
/*     */   private final InputStream resourceInputStream;
/*     */   private final InputStream mcmetaInputStream;
/*     */   private final IMetadataSerializer srMetadataSerializer;
/*     */   private boolean mcmetaJsonChecked;
/*     */   private JsonObject mcmetaJson;
/*     */   
/*     */   public SimpleResource(String resourcePackNameIn, ResourceLocation srResourceLocationIn, InputStream resourceInputStreamIn, InputStream mcmetaInputStreamIn, IMetadataSerializer srMetadataSerializerIn) {
/*  29 */     this.resourcePackName = resourcePackNameIn;
/*  30 */     this.srResourceLocation = srResourceLocationIn;
/*  31 */     this.resourceInputStream = resourceInputStreamIn;
/*  32 */     this.mcmetaInputStream = mcmetaInputStreamIn;
/*  33 */     this.srMetadataSerializer = srMetadataSerializerIn;
/*     */   }
/*     */   
/*     */   public ResourceLocation getResourceLocation() {
/*  37 */     return this.srResourceLocation;
/*     */   }
/*     */   
/*     */   public InputStream getInputStream() {
/*  41 */     return this.resourceInputStream;
/*     */   }
/*     */   
/*     */   public boolean hasMetadata() {
/*  45 */     return (this.mcmetaInputStream != null);
/*     */   }
/*     */   
/*     */   public <T extends IMetadataSection> T getMetadata(String p_110526_1_) {
/*  49 */     if (!hasMetadata()) {
/*  50 */       return null;
/*     */     }
/*  52 */     if (this.mcmetaJson == null && !this.mcmetaJsonChecked) {
/*  53 */       this.mcmetaJsonChecked = true;
/*  54 */       BufferedReader bufferedreader = null;
/*     */       
/*     */       try {
/*  57 */         bufferedreader = new BufferedReader(new InputStreamReader(this.mcmetaInputStream));
/*  58 */         this.mcmetaJson = (new JsonParser()).parse(bufferedreader).getAsJsonObject();
/*     */       } finally {
/*  60 */         IOUtils.closeQuietly(bufferedreader);
/*     */       } 
/*     */     } 
/*     */     
/*  64 */     IMetadataSection iMetadataSection = this.mapMetadataSections.get(p_110526_1_);
/*     */     
/*  66 */     if (iMetadataSection == null) {
/*  67 */       iMetadataSection = this.srMetadataSerializer.parseMetadataSection(p_110526_1_, this.mcmetaJson);
/*     */     }
/*     */     
/*  70 */     return (T)iMetadataSection;
/*     */   }
/*     */ 
/*     */   
/*     */   public String getResourcePackName() {
/*  75 */     return this.resourcePackName;
/*     */   }
/*     */   
/*     */   public boolean equals(Object p_equals_1_) {
/*  79 */     if (this == p_equals_1_)
/*  80 */       return true; 
/*  81 */     if (!(p_equals_1_ instanceof SimpleResource)) {
/*  82 */       return false;
/*     */     }
/*  84 */     SimpleResource simpleresource = (SimpleResource)p_equals_1_;
/*     */     
/*  86 */     if (this.srResourceLocation != null) {
/*  87 */       if (!this.srResourceLocation.equals(simpleresource.srResourceLocation)) {
/*  88 */         return false;
/*     */       }
/*  90 */     } else if (simpleresource.srResourceLocation != null) {
/*  91 */       return false;
/*     */     } 
/*     */     
/*  94 */     if (this.resourcePackName != null) {
/*  95 */       if (!this.resourcePackName.equals(simpleresource.resourcePackName)) {
/*  96 */         return false;
/*     */       }
/*  98 */     } else if (simpleresource.resourcePackName != null) {
/*  99 */       return false;
/*     */     } 
/*     */     
/* 102 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   public int hashCode() {
/* 107 */     int i = (this.resourcePackName != null) ? this.resourcePackName.hashCode() : 0;
/* 108 */     i = 31 * i + ((this.srResourceLocation != null) ? this.srResourceLocation.hashCode() : 0);
/* 109 */     return i;
/*     */   }
/*     */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\minecraft\client\resources\SimpleResource.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */