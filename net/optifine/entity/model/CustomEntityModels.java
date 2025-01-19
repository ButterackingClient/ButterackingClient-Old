/*     */ package net.optifine.entity.model;
/*     */ 
/*     */ import com.google.gson.JsonObject;
/*     */ import com.google.gson.JsonParseException;
/*     */ import java.io.IOException;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Arrays;
/*     */ import java.util.Collections;
/*     */ import java.util.HashMap;
/*     */ import java.util.IdentityHashMap;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import java.util.Set;
/*     */ import net.minecraft.client.Minecraft;
/*     */ import net.minecraft.client.model.ModelBase;
/*     */ import net.minecraft.client.model.ModelRenderer;
/*     */ import net.minecraft.client.renderer.entity.Render;
/*     */ import net.minecraft.client.renderer.entity.RenderManager;
/*     */ import net.minecraft.client.renderer.tileentity.TileEntityRendererDispatcher;
/*     */ import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
/*     */ import net.minecraft.src.Config;
/*     */ import net.minecraft.util.ResourceLocation;
/*     */ import net.optifine.entity.model.anim.IModelResolver;
/*     */ import net.optifine.entity.model.anim.ModelResolver;
/*     */ import net.optifine.entity.model.anim.ModelUpdater;
/*     */ 
/*     */ public class CustomEntityModels
/*     */ {
/*     */   private static boolean active = false;
/*  31 */   private static Map<Class, Render> originalEntityRenderMap = null;
/*  32 */   private static Map<Class, TileEntitySpecialRenderer> originalTileEntityRenderMap = null;
/*     */   
/*     */   public static void update() {
/*  35 */     Map<Class<?>, Render> map = getEntityRenderMap();
/*  36 */     Map<Class<?>, TileEntitySpecialRenderer> map1 = getTileEntityRenderMap();
/*     */     
/*  38 */     if (map == null) {
/*  39 */       Config.warn("Entity render map not found, custom entity models are DISABLED.");
/*  40 */     } else if (map1 == null) {
/*  41 */       Config.warn("Tile entity render map not found, custom entity models are DISABLED.");
/*     */     } else {
/*  43 */       active = false;
/*  44 */       map.clear();
/*  45 */       map1.clear();
/*  46 */       map.putAll((Map)originalEntityRenderMap);
/*  47 */       map1.putAll((Map)originalTileEntityRenderMap);
/*     */       
/*  49 */       if (Config.isCustomEntityModels()) {
/*  50 */         ResourceLocation[] aresourcelocation = getModelLocations();
/*     */         
/*  52 */         for (int i = 0; i < aresourcelocation.length; i++) {
/*  53 */           ResourceLocation resourcelocation = aresourcelocation[i];
/*  54 */           Config.dbg("CustomEntityModel: " + resourcelocation.getResourcePath());
/*  55 */           IEntityRenderer ientityrenderer = parseEntityRender(resourcelocation);
/*     */           
/*  57 */           if (ientityrenderer != null) {
/*  58 */             Class<?> oclass = ientityrenderer.getEntityClass();
/*     */             
/*  60 */             if (oclass != null) {
/*  61 */               if (ientityrenderer instanceof Render) {
/*  62 */                 map.put(oclass, (Render)ientityrenderer);
/*  63 */               } else if (ientityrenderer instanceof TileEntitySpecialRenderer) {
/*  64 */                 map1.put(oclass, (TileEntitySpecialRenderer)ientityrenderer);
/*     */               } else {
/*  66 */                 Config.warn("Unknown renderer type: " + ientityrenderer.getClass().getName());
/*     */               } 
/*     */               
/*  69 */               active = true;
/*     */             } 
/*     */           } 
/*     */         } 
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   private static Map<Class, Render> getEntityRenderMap() {
/*  78 */     RenderManager rendermanager = Minecraft.getMinecraft().getRenderManager();
/*  79 */     Map<Class<?>, Render> map = rendermanager.getEntityRenderMap();
/*     */     
/*  81 */     if (map == null) {
/*  82 */       return null;
/*     */     }
/*  84 */     if (originalEntityRenderMap == null) {
/*  85 */       originalEntityRenderMap = (Map)new HashMap<>(map);
/*     */     }
/*     */     
/*  88 */     return map;
/*     */   }
/*     */ 
/*     */   
/*     */   private static Map<Class, TileEntitySpecialRenderer> getTileEntityRenderMap() {
/*  93 */     Map<Class<?>, TileEntitySpecialRenderer> map = TileEntityRendererDispatcher.instance.mapSpecialRenderers;
/*     */     
/*  95 */     if (originalTileEntityRenderMap == null) {
/*  96 */       originalTileEntityRenderMap = (Map)new HashMap<>(map);
/*     */     }
/*     */     
/*  99 */     return map;
/*     */   }
/*     */   
/*     */   private static ResourceLocation[] getModelLocations() {
/* 103 */     String s = "optifine/cem/";
/* 104 */     String s1 = ".jem";
/* 105 */     List<ResourceLocation> list = new ArrayList<>();
/* 106 */     String[] astring = CustomModelRegistry.getModelNames();
/*     */     
/* 108 */     for (int i = 0; i < astring.length; i++) {
/* 109 */       String s2 = astring[i];
/* 110 */       String s3 = String.valueOf(s) + s2 + s1;
/* 111 */       ResourceLocation resourcelocation = new ResourceLocation(s3);
/*     */       
/* 113 */       if (Config.hasResource(resourcelocation)) {
/* 114 */         list.add(resourcelocation);
/*     */       }
/*     */     } 
/*     */     
/* 118 */     ResourceLocation[] aresourcelocation = list.<ResourceLocation>toArray(new ResourceLocation[list.size()]);
/* 119 */     return aresourcelocation;
/*     */   }
/*     */   
/*     */   private static IEntityRenderer parseEntityRender(ResourceLocation location) {
/*     */     try {
/* 124 */       JsonObject jsonobject = CustomEntityModelParser.loadJson(location);
/* 125 */       IEntityRenderer ientityrenderer = parseEntityRender(jsonobject, location.getResourcePath());
/* 126 */       return ientityrenderer;
/* 127 */     } catch (IOException ioexception) {
/* 128 */       Config.error(ioexception.getClass().getName() + ": " + ioexception.getMessage());
/* 129 */       return null;
/* 130 */     } catch (JsonParseException jsonparseexception) {
/* 131 */       Config.error(jsonparseexception.getClass().getName() + ": " + jsonparseexception.getMessage());
/* 132 */       return null;
/* 133 */     } catch (Exception exception) {
/* 134 */       exception.printStackTrace();
/* 135 */       return null;
/*     */     } 
/*     */   }
/*     */   
/*     */   private static IEntityRenderer parseEntityRender(JsonObject obj, String path) {
/* 140 */     CustomEntityRenderer customentityrenderer = CustomEntityModelParser.parseEntityRender(obj, path);
/* 141 */     String s = customentityrenderer.getName();
/* 142 */     ModelAdapter modeladapter = CustomModelRegistry.getModelAdapter(s);
/* 143 */     checkNull(modeladapter, "Entity not found: " + s);
/* 144 */     Class oclass = modeladapter.getEntityClass();
/* 145 */     checkNull(oclass, "Entity class not found: " + s);
/* 146 */     IEntityRenderer ientityrenderer = makeEntityRender(modeladapter, customentityrenderer);
/*     */     
/* 148 */     if (ientityrenderer == null) {
/* 149 */       return null;
/*     */     }
/* 151 */     ientityrenderer.setEntityClass(oclass);
/* 152 */     return ientityrenderer;
/*     */   }
/*     */ 
/*     */   
/*     */   private static IEntityRenderer makeEntityRender(ModelAdapter modelAdapter, CustomEntityRenderer cer) {
/* 157 */     ResourceLocation resourcelocation = cer.getTextureLocation();
/* 158 */     CustomModelRenderer[] acustommodelrenderer = cer.getCustomModelRenderers();
/* 159 */     float f = cer.getShadowSize();
/*     */     
/* 161 */     if (f < 0.0F) {
/* 162 */       f = modelAdapter.getShadowSize();
/*     */     }
/*     */     
/* 165 */     ModelBase modelbase = modelAdapter.makeModel();
/*     */     
/* 167 */     if (modelbase == null) {
/* 168 */       return null;
/*     */     }
/* 170 */     ModelResolver modelresolver = new ModelResolver(modelAdapter, modelbase, acustommodelrenderer);
/*     */     
/* 172 */     if (!modifyModel(modelAdapter, modelbase, acustommodelrenderer, modelresolver)) {
/* 173 */       return null;
/*     */     }
/* 175 */     IEntityRenderer ientityrenderer = modelAdapter.makeEntityRender(modelbase, f);
/*     */     
/* 177 */     if (ientityrenderer == null) {
/* 178 */       throw new JsonParseException("Entity renderer is null, model: " + modelAdapter.getName() + ", adapter: " + modelAdapter.getClass().getName());
/*     */     }
/* 180 */     if (resourcelocation != null) {
/* 181 */       ientityrenderer.setLocationTextureCustom(resourcelocation);
/*     */     }
/*     */     
/* 184 */     return ientityrenderer;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static boolean modifyModel(ModelAdapter modelAdapter, ModelBase model, CustomModelRenderer[] modelRenderers, ModelResolver mr) {
/* 191 */     for (int i = 0; i < modelRenderers.length; i++) {
/* 192 */       CustomModelRenderer custommodelrenderer = modelRenderers[i];
/*     */       
/* 194 */       if (!modifyModel(modelAdapter, model, custommodelrenderer, mr)) {
/* 195 */         return false;
/*     */       }
/*     */     } 
/*     */     
/* 199 */     return true;
/*     */   }
/*     */   
/*     */   private static boolean modifyModel(ModelAdapter modelAdapter, ModelBase model, CustomModelRenderer customModelRenderer, ModelResolver modelResolver) {
/* 203 */     String s = customModelRenderer.getModelPart();
/* 204 */     ModelRenderer modelrenderer = modelAdapter.getModelRenderer(model, s);
/*     */     
/* 206 */     if (modelrenderer == null) {
/* 207 */       Config.warn("Model part not found: " + s + ", model: " + model);
/* 208 */       return false;
/*     */     } 
/* 210 */     if (!customModelRenderer.isAttach()) {
/* 211 */       if (modelrenderer.cubeList != null) {
/* 212 */         modelrenderer.cubeList.clear();
/*     */       }
/*     */       
/* 215 */       if (modelrenderer.spriteList != null) {
/* 216 */         modelrenderer.spriteList.clear();
/*     */       }
/*     */       
/* 219 */       if (modelrenderer.childModels != null) {
/* 220 */         ModelRenderer[] amodelrenderer = modelAdapter.getModelRenderers(model);
/* 221 */         Set<ModelRenderer> set = Collections.newSetFromMap(new IdentityHashMap<>());
/* 222 */         set.addAll(Arrays.asList(amodelrenderer));
/* 223 */         List<ModelRenderer> list = modelrenderer.childModels;
/* 224 */         Iterator<ModelRenderer> iterator = list.iterator();
/*     */         
/* 226 */         while (iterator.hasNext()) {
/* 227 */           ModelRenderer modelrenderer1 = iterator.next();
/*     */           
/* 229 */           if (!set.contains(modelrenderer1)) {
/* 230 */             iterator.remove();
/*     */           }
/*     */         } 
/*     */       } 
/*     */     } 
/*     */     
/* 236 */     modelrenderer.addChild(customModelRenderer.getModelRenderer());
/* 237 */     ModelUpdater modelupdater = customModelRenderer.getModelUpdater();
/*     */     
/* 239 */     if (modelupdater != null) {
/* 240 */       modelResolver.setThisModelRenderer(customModelRenderer.getModelRenderer());
/* 241 */       modelResolver.setPartModelRenderer(modelrenderer);
/*     */       
/* 243 */       if (!modelupdater.initialize((IModelResolver)modelResolver)) {
/* 244 */         return false;
/*     */       }
/*     */       
/* 247 */       customModelRenderer.getModelRenderer().setModelUpdater(modelupdater);
/*     */     } 
/*     */     
/* 250 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   private static void checkNull(Object obj, String msg) {
/* 255 */     if (obj == null) {
/* 256 */       throw new JsonParseException(msg);
/*     */     }
/*     */   }
/*     */   
/*     */   public static boolean isActive() {
/* 261 */     return active;
/*     */   }
/*     */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\optifine\entity\model\CustomEntityModels.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */