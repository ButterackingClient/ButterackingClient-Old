/*     */ package net.optifine.entity.model;
/*     */ 
/*     */ import com.google.gson.JsonArray;
/*     */ import com.google.gson.JsonElement;
/*     */ import com.google.gson.JsonObject;
/*     */ import com.google.gson.JsonParseException;
/*     */ import com.google.gson.JsonParser;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import java.util.ArrayList;
/*     */ import java.util.HashMap;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import net.minecraft.client.model.ModelBase;
/*     */ import net.minecraft.client.model.ModelRenderer;
/*     */ import net.minecraft.src.Config;
/*     */ import net.minecraft.util.ResourceLocation;
/*     */ import net.optifine.config.ConnectedParser;
/*     */ import net.optifine.entity.model.anim.ModelUpdater;
/*     */ import net.optifine.entity.model.anim.ModelVariableUpdater;
/*     */ import net.optifine.player.PlayerItemParser;
/*     */ import net.optifine.util.Json;
/*     */ 
/*     */ 
/*     */ 
/*     */ public class CustomEntityModelParser
/*     */ {
/*     */   public static final String ENTITY = "entity";
/*     */   public static final String TEXTURE = "texture";
/*     */   public static final String SHADOW_SIZE = "shadowSize";
/*     */   public static final String ITEM_TYPE = "type";
/*     */   public static final String ITEM_TEXTURE_SIZE = "textureSize";
/*     */   public static final String ITEM_USE_PLAYER_TEXTURE = "usePlayerTexture";
/*     */   public static final String ITEM_MODELS = "models";
/*     */   public static final String ITEM_ANIMATIONS = "animations";
/*     */   public static final String MODEL_ID = "id";
/*     */   public static final String MODEL_BASE_ID = "baseId";
/*     */   public static final String MODEL_MODEL = "model";
/*     */   public static final String MODEL_TYPE = "type";
/*     */   public static final String MODEL_PART = "part";
/*     */   public static final String MODEL_ATTACH = "attach";
/*     */   public static final String MODEL_INVERT_AXIS = "invertAxis";
/*     */   public static final String MODEL_MIRROR_TEXTURE = "mirrorTexture";
/*     */   public static final String MODEL_TRANSLATE = "translate";
/*     */   public static final String MODEL_ROTATE = "rotate";
/*     */   public static final String MODEL_SCALE = "scale";
/*     */   public static final String MODEL_BOXES = "boxes";
/*     */   public static final String MODEL_SPRITES = "sprites";
/*     */   public static final String MODEL_SUBMODEL = "submodel";
/*     */   public static final String MODEL_SUBMODELS = "submodels";
/*     */   public static final String BOX_TEXTURE_OFFSET = "textureOffset";
/*     */   public static final String BOX_COORDINATES = "coordinates";
/*     */   public static final String BOX_SIZE_ADD = "sizeAdd";
/*     */   public static final String ENTITY_MODEL = "EntityModel";
/*     */   public static final String ENTITY_MODEL_PART = "EntityModelPart";
/*     */   
/*     */   public static CustomEntityRenderer parseEntityRender(JsonObject obj, String path) {
/*  58 */     ConnectedParser connectedparser = new ConnectedParser("CustomEntityModels");
/*  59 */     String s = connectedparser.parseName(path);
/*  60 */     String s1 = connectedparser.parseBasePath(path);
/*  61 */     String s2 = Json.getString(obj, "texture");
/*  62 */     int[] aint = Json.parseIntArray(obj.get("textureSize"), 2);
/*  63 */     float f = Json.getFloat(obj, "shadowSize", -1.0F);
/*  64 */     JsonArray jsonarray = (JsonArray)obj.get("models");
/*  65 */     checkNull(jsonarray, "Missing models");
/*  66 */     Map<Object, Object> map = new HashMap<>();
/*  67 */     List<CustomModelRenderer> list = new ArrayList();
/*     */     
/*  69 */     for (int i = 0; i < jsonarray.size(); i++) {
/*  70 */       JsonObject jsonobject = (JsonObject)jsonarray.get(i);
/*  71 */       processBaseId(jsonobject, map);
/*  72 */       processExternalModel(jsonobject, map, s1);
/*  73 */       processId(jsonobject, map);
/*  74 */       CustomModelRenderer custommodelrenderer = parseCustomModelRenderer(jsonobject, aint, s1);
/*     */       
/*  76 */       if (custommodelrenderer != null) {
/*  77 */         list.add(custommodelrenderer);
/*     */       }
/*     */     } 
/*     */     
/*  81 */     CustomModelRenderer[] acustommodelrenderer = list.<CustomModelRenderer>toArray(new CustomModelRenderer[list.size()]);
/*  82 */     ResourceLocation resourcelocation = null;
/*     */     
/*  84 */     if (s2 != null) {
/*  85 */       resourcelocation = getResourceLocation(s1, s2, ".png");
/*     */     }
/*     */     
/*  88 */     CustomEntityRenderer customentityrenderer = new CustomEntityRenderer(s, s1, resourcelocation, acustommodelrenderer, f);
/*  89 */     return customentityrenderer;
/*     */   }
/*     */   
/*     */   private static void processBaseId(JsonObject elem, Map mapModelJsons) {
/*  93 */     String s = Json.getString(elem, "baseId");
/*     */     
/*  95 */     if (s != null) {
/*  96 */       JsonObject jsonobject = (JsonObject)mapModelJsons.get(s);
/*     */       
/*  98 */       if (jsonobject == null) {
/*  99 */         Config.warn("BaseID not found: " + s);
/*     */       } else {
/* 101 */         copyJsonElements(jsonobject, elem);
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   private static void processExternalModel(JsonObject elem, Map mapModelJsons, String basePath) {
/* 107 */     String s = Json.getString(elem, "model");
/*     */     
/* 109 */     if (s != null) {
/* 110 */       ResourceLocation resourcelocation = getResourceLocation(basePath, s, ".jpm");
/*     */       
/*     */       try {
/* 113 */         JsonObject jsonobject = loadJson(resourcelocation);
/*     */         
/* 115 */         if (jsonobject == null) {
/* 116 */           Config.warn("Model not found: " + resourcelocation);
/*     */           
/*     */           return;
/*     */         } 
/* 120 */         copyJsonElements(jsonobject, elem);
/* 121 */       } catch (IOException ioexception) {
/* 122 */         Config.error(ioexception.getClass().getName() + ": " + ioexception.getMessage());
/* 123 */       } catch (JsonParseException jsonparseexception) {
/* 124 */         Config.error(jsonparseexception.getClass().getName() + ": " + jsonparseexception.getMessage());
/* 125 */       } catch (Exception exception) {
/* 126 */         exception.printStackTrace();
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   private static void copyJsonElements(JsonObject objFrom, JsonObject objTo) {
/* 132 */     for (Map.Entry<String, JsonElement> entry : (Iterable<Map.Entry<String, JsonElement>>)objFrom.entrySet()) {
/* 133 */       if (!((String)entry.getKey()).equals("id") && !objTo.has(entry.getKey())) {
/* 134 */         objTo.add(entry.getKey(), entry.getValue());
/*     */       }
/*     */     } 
/*     */   }
/*     */   
/*     */   public static ResourceLocation getResourceLocation(String basePath, String path, String extension) {
/* 140 */     if (!path.endsWith(extension)) {
/* 141 */       path = String.valueOf(path) + extension;
/*     */     }
/*     */     
/* 144 */     if (!path.contains("/")) {
/* 145 */       path = String.valueOf(basePath) + "/" + path;
/* 146 */     } else if (path.startsWith("./")) {
/* 147 */       path = String.valueOf(basePath) + "/" + path.substring(2);
/* 148 */     } else if (path.startsWith("~/")) {
/* 149 */       path = "optifine/" + path.substring(2);
/*     */     } 
/*     */     
/* 152 */     return new ResourceLocation(path);
/*     */   }
/*     */   
/*     */   private static void processId(JsonObject elem, Map<String, JsonObject> mapModelJsons) {
/* 156 */     String s = Json.getString(elem, "id");
/*     */     
/* 158 */     if (s != null) {
/* 159 */       if (s.length() < 1) {
/* 160 */         Config.warn("Empty model ID: " + s);
/* 161 */       } else if (mapModelJsons.containsKey(s)) {
/* 162 */         Config.warn("Duplicate model ID: " + s);
/*     */       } else {
/* 164 */         mapModelJsons.put(s, elem);
/*     */       } 
/*     */     }
/*     */   }
/*     */   
/*     */   public static CustomModelRenderer parseCustomModelRenderer(JsonObject elem, int[] textureSize, String basePath) {
/* 170 */     String s = Json.getString(elem, "part");
/* 171 */     checkNull(s, "Model part not specified, missing \"replace\" or \"attachTo\".");
/* 172 */     boolean flag = Json.getBoolean(elem, "attach", false);
/* 173 */     ModelBase modelbase = new CustomEntityModel();
/*     */     
/* 175 */     if (textureSize != null) {
/* 176 */       modelbase.textureWidth = textureSize[0];
/* 177 */       modelbase.textureHeight = textureSize[1];
/*     */     } 
/*     */     
/* 180 */     ModelUpdater modelupdater = null;
/* 181 */     JsonArray jsonarray = (JsonArray)elem.get("animations");
/*     */     
/* 183 */     if (jsonarray != null) {
/* 184 */       List<ModelVariableUpdater> list = new ArrayList<>();
/*     */       
/* 186 */       for (int i = 0; i < jsonarray.size(); i++) {
/* 187 */         JsonObject jsonobject = (JsonObject)jsonarray.get(i);
/*     */         
/* 189 */         for (Map.Entry<String, JsonElement> entry : (Iterable<Map.Entry<String, JsonElement>>)jsonobject.entrySet()) {
/* 190 */           String s1 = entry.getKey();
/* 191 */           String s2 = ((JsonElement)entry.getValue()).getAsString();
/* 192 */           ModelVariableUpdater modelvariableupdater = new ModelVariableUpdater(s1, s2);
/* 193 */           list.add(modelvariableupdater);
/*     */         } 
/*     */       } 
/*     */       
/* 197 */       if (list.size() > 0) {
/* 198 */         ModelVariableUpdater[] amodelvariableupdater = list.<ModelVariableUpdater>toArray(new ModelVariableUpdater[list.size()]);
/* 199 */         modelupdater = new ModelUpdater(amodelvariableupdater);
/*     */       } 
/*     */     } 
/*     */     
/* 203 */     ModelRenderer modelrenderer = PlayerItemParser.parseModelRenderer(elem, modelbase, textureSize, basePath);
/* 204 */     CustomModelRenderer custommodelrenderer = new CustomModelRenderer(s, flag, modelrenderer, modelupdater);
/* 205 */     return custommodelrenderer;
/*     */   }
/*     */   
/*     */   private static void checkNull(Object obj, String msg) {
/* 209 */     if (obj == null) {
/* 210 */       throw new JsonParseException(msg);
/*     */     }
/*     */   }
/*     */   
/*     */   public static JsonObject loadJson(ResourceLocation location) throws IOException, JsonParseException {
/* 215 */     InputStream inputstream = Config.getResourceStream(location);
/*     */     
/* 217 */     if (inputstream == null) {
/* 218 */       return null;
/*     */     }
/* 220 */     String s = Config.readInputStream(inputstream, "ASCII");
/* 221 */     inputstream.close();
/* 222 */     JsonParser jsonparser = new JsonParser();
/* 223 */     JsonObject jsonobject = (JsonObject)jsonparser.parse(s);
/* 224 */     return jsonobject;
/*     */   }
/*     */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\optifine\entity\model\CustomEntityModelParser.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */