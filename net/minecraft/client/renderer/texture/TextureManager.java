/*     */ package net.minecraft.client.renderer.texture;
/*     */ 
/*     */ import com.google.common.collect.Lists;
/*     */ import com.google.common.collect.Maps;
/*     */ import java.io.IOException;
/*     */ import java.util.HashSet;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import java.util.concurrent.Callable;
/*     */ import net.minecraft.client.resources.IResourceManager;
/*     */ import net.minecraft.client.resources.IResourceManagerReloadListener;
/*     */ import net.minecraft.crash.CrashReport;
/*     */ import net.minecraft.crash.CrashReportCategory;
/*     */ import net.minecraft.src.Config;
/*     */ import net.minecraft.util.ReportedException;
/*     */ import net.minecraft.util.ResourceLocation;
/*     */ import net.optifine.CustomGuis;
/*     */ import net.optifine.EmissiveTextures;
/*     */ import net.optifine.RandomEntities;
/*     */ import net.optifine.shaders.ShadersTex;
/*     */ import org.apache.logging.log4j.LogManager;
/*     */ import org.apache.logging.log4j.Logger;
/*     */ 
/*     */ 
/*     */ public class TextureManager
/*     */   implements ITickable, IResourceManagerReloadListener
/*     */ {
/*  29 */   private static final Logger logger = LogManager.getLogger();
/*  30 */   private final Map<ResourceLocation, ITextureObject> mapTextureObjects = Maps.newHashMap();
/*  31 */   private final List<ITickable> listTickables = Lists.newArrayList();
/*  32 */   private final Map<String, Integer> mapTextureCounters = Maps.newHashMap();
/*     */   private IResourceManager theResourceManager;
/*     */   private ITextureObject boundTexture;
/*     */   private ResourceLocation boundTextureLocation;
/*     */   
/*     */   public TextureManager(IResourceManager resourceManager) {
/*  38 */     this.theResourceManager = resourceManager;
/*     */   }
/*     */   
/*     */   public void bindTexture(ResourceLocation resource) {
/*  42 */     if (Config.isRandomEntities()) {
/*  43 */       resource = RandomEntities.getTextureLocation(resource);
/*     */     }
/*     */     
/*  46 */     if (Config.isCustomGuis()) {
/*  47 */       resource = CustomGuis.getTextureLocation(resource);
/*     */     }
/*     */     
/*  50 */     ITextureObject itextureobject = this.mapTextureObjects.get(resource);
/*     */     
/*  52 */     if (EmissiveTextures.isActive()) {
/*  53 */       itextureobject = EmissiveTextures.getEmissiveTexture(itextureobject, this.mapTextureObjects);
/*     */     }
/*     */     
/*  56 */     if (itextureobject == null) {
/*  57 */       itextureobject = new SimpleTexture(resource);
/*  58 */       loadTexture(resource, itextureobject);
/*     */     } 
/*     */     
/*  61 */     if (Config.isShaders()) {
/*  62 */       ShadersTex.bindTexture(itextureobject);
/*     */     } else {
/*  64 */       TextureUtil.bindTexture(itextureobject.getGlTextureId());
/*     */     } 
/*     */     
/*  67 */     this.boundTexture = itextureobject;
/*  68 */     this.boundTextureLocation = resource;
/*     */   }
/*     */   
/*     */   public boolean loadTickableTexture(ResourceLocation textureLocation, ITickableTextureObject textureObj) {
/*  72 */     if (loadTexture(textureLocation, textureObj)) {
/*  73 */       this.listTickables.add(textureObj);
/*  74 */       return true;
/*     */     } 
/*  76 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean loadTexture(ResourceLocation textureLocation, ITextureObject textureObj) {
/*  81 */     boolean flag = true;
/*     */     
/*     */     try {
/*  84 */       textureObj.loadTexture(this.theResourceManager);
/*  85 */     } catch (IOException ioexception) {
/*  86 */       logger.warn("Failed to load texture: " + textureLocation, ioexception);
/*  87 */       textureObj = TextureUtil.missingTexture;
/*  88 */       this.mapTextureObjects.put(textureLocation, textureObj);
/*  89 */       flag = false;
/*  90 */     } catch (Throwable throwable) {
/*  91 */       final ITextureObject textureObjf = textureObj;
/*  92 */       CrashReport crashreport = CrashReport.makeCrashReport(throwable, "Registering texture");
/*  93 */       CrashReportCategory crashreportcategory = crashreport.makeCategory("Resource location being registered");
/*  94 */       crashreportcategory.addCrashSection("Resource location", textureLocation);
/*  95 */       crashreportcategory.addCrashSectionCallable("Texture object class", new Callable<String>() {
/*     */             public String call() throws Exception {
/*  97 */               return textureObjf.getClass().getName();
/*     */             }
/*     */           });
/* 100 */       throw new ReportedException(crashreport);
/*     */     } 
/*     */     
/* 103 */     this.mapTextureObjects.put(textureLocation, textureObj);
/* 104 */     return flag;
/*     */   }
/*     */   
/*     */   public ITextureObject getTexture(ResourceLocation textureLocation) {
/* 108 */     return this.mapTextureObjects.get(textureLocation);
/*     */   }
/*     */   
/*     */   public ResourceLocation getDynamicTextureLocation(String name, DynamicTexture texture) {
/* 112 */     if (name.equals("logo")) {
/* 113 */       texture = Config.getMojangLogoTexture(texture);
/*     */     }
/*     */     
/* 116 */     Integer integer = this.mapTextureCounters.get(name);
/*     */     
/* 118 */     if (integer == null) {
/* 119 */       integer = Integer.valueOf(1);
/*     */     } else {
/* 121 */       integer = Integer.valueOf(integer.intValue() + 1);
/*     */     } 
/*     */     
/* 124 */     this.mapTextureCounters.put(name, integer);
/* 125 */     ResourceLocation resourcelocation = new ResourceLocation(String.format("dynamic/%s_%d", new Object[] { name, integer }));
/* 126 */     loadTexture(resourcelocation, texture);
/* 127 */     return resourcelocation;
/*     */   }
/*     */   
/*     */   public void tick() {
/* 131 */     for (ITickable itickable : this.listTickables) {
/* 132 */       itickable.tick();
/*     */     }
/*     */   }
/*     */   
/*     */   public void deleteTexture(ResourceLocation textureLocation) {
/* 137 */     ITextureObject itextureobject = getTexture(textureLocation);
/*     */     
/* 139 */     if (itextureobject != null) {
/* 140 */       this.mapTextureObjects.remove(textureLocation);
/* 141 */       TextureUtil.deleteTexture(itextureobject.getGlTextureId());
/*     */     } 
/*     */   }
/*     */   
/*     */   public void onResourceManagerReload(IResourceManager resourceManager) {
/* 146 */     Config.dbg("*** Reloading textures ***");
/* 147 */     Config.log("Resource packs: " + Config.getResourcePackNames());
/* 148 */     Iterator<ResourceLocation> iterator = this.mapTextureObjects.keySet().iterator();
/*     */     
/* 150 */     while (iterator.hasNext()) {
/* 151 */       ResourceLocation resourcelocation = iterator.next();
/* 152 */       String s = resourcelocation.getResourcePath();
/*     */       
/* 154 */       if (s.startsWith("mcpatcher/") || s.startsWith("optifine/") || EmissiveTextures.isEmissive(resourcelocation)) {
/* 155 */         ITextureObject itextureobject = this.mapTextureObjects.get(resourcelocation);
/*     */         
/* 157 */         if (itextureobject instanceof AbstractTexture) {
/* 158 */           AbstractTexture abstracttexture = (AbstractTexture)itextureobject;
/* 159 */           abstracttexture.deleteGlTexture();
/*     */         } 
/*     */         
/* 162 */         iterator.remove();
/*     */       } 
/*     */     } 
/*     */     
/* 166 */     EmissiveTextures.update();
/*     */     
/* 168 */     for (Object entry0 : new HashSet(this.mapTextureObjects.entrySet())) {
/* 169 */       Map.Entry<ResourceLocation, ITextureObject> entry = (Map.Entry<ResourceLocation, ITextureObject>)entry0;
/* 170 */       loadTexture(entry.getKey(), entry.getValue());
/*     */     } 
/*     */   }
/*     */   
/*     */   public void reloadBannerTextures() {
/* 175 */     for (Object entry0 : new HashSet(this.mapTextureObjects.entrySet())) {
/* 176 */       Map.Entry<ResourceLocation, ITextureObject> entry = (Map.Entry<ResourceLocation, ITextureObject>)entry0;
/* 177 */       ResourceLocation resourcelocation = entry.getKey();
/* 178 */       ITextureObject itextureobject = entry.getValue();
/*     */       
/* 180 */       if (itextureobject instanceof LayeredColorMaskTexture) {
/* 181 */         loadTexture(resourcelocation, itextureobject);
/*     */       }
/*     */     } 
/*     */   }
/*     */   
/*     */   public ITextureObject getBoundTexture() {
/* 187 */     return this.boundTexture;
/*     */   }
/*     */   
/*     */   public ResourceLocation getBoundTextureLocation() {
/* 191 */     return this.boundTextureLocation;
/*     */   }
/*     */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\minecraft\client\renderer\texture\TextureManager.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */