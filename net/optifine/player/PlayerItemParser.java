/*     */ package net.optifine.player;
/*     */ 
/*     */ import com.google.gson.JsonArray;
/*     */ import com.google.gson.JsonElement;
/*     */ import com.google.gson.JsonObject;
/*     */ import com.google.gson.JsonParseException;
/*     */ import com.google.gson.JsonParser;
/*     */ import java.awt.Dimension;
/*     */ import java.util.ArrayList;
/*     */ import java.util.HashMap;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import net.minecraft.client.model.ModelBase;
/*     */ import net.minecraft.client.model.ModelRenderer;
/*     */ import net.minecraft.src.Config;
/*     */ import net.minecraft.util.MathHelper;
/*     */ import net.minecraft.util.ResourceLocation;
/*     */ import net.optifine.entity.model.CustomEntityModelParser;
/*     */ import net.optifine.util.Json;
/*     */ 
/*     */ 
/*     */ 
/*     */ public class PlayerItemParser
/*     */ {
/*  25 */   private static JsonParser jsonParser = new JsonParser();
/*     */   public static final String ITEM_TYPE = "type";
/*     */   public static final String ITEM_TEXTURE_SIZE = "textureSize";
/*     */   public static final String ITEM_USE_PLAYER_TEXTURE = "usePlayerTexture";
/*     */   public static final String ITEM_MODELS = "models";
/*     */   public static final String MODEL_ID = "id";
/*     */   public static final String MODEL_BASE_ID = "baseId";
/*     */   public static final String MODEL_TYPE = "type";
/*     */   public static final String MODEL_TEXTURE = "texture";
/*     */   public static final String MODEL_TEXTURE_SIZE = "textureSize";
/*     */   public static final String MODEL_ATTACH_TO = "attachTo";
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
/*     */   public static final String BOX_UV_DOWN = "uvDown";
/*     */   public static final String BOX_UV_UP = "uvUp";
/*     */   public static final String BOX_UV_NORTH = "uvNorth";
/*     */   public static final String BOX_UV_SOUTH = "uvSouth";
/*     */   public static final String BOX_UV_WEST = "uvWest";
/*     */   public static final String BOX_UV_EAST = "uvEast";
/*     */   public static final String BOX_UV_FRONT = "uvFront";
/*     */   public static final String BOX_UV_BACK = "uvBack";
/*     */   public static final String BOX_UV_LEFT = "uvLeft";
/*     */   public static final String BOX_UV_RIGHT = "uvRight";
/*     */   public static final String ITEM_TYPE_MODEL = "PlayerItem";
/*     */   public static final String MODEL_TYPE_BOX = "ModelBox";
/*     */   
/*     */   public static PlayerItemModel parseItemModel(JsonObject obj) {
/*  62 */     String s = Json.getString(obj, "type");
/*     */     
/*  64 */     if (!Config.equals(s, "PlayerItem")) {
/*  65 */       throw new JsonParseException("Unknown model type: " + s);
/*     */     }
/*  67 */     int[] aint = Json.parseIntArray(obj.get("textureSize"), 2);
/*  68 */     checkNull(aint, "Missing texture size");
/*  69 */     Dimension dimension = new Dimension(aint[0], aint[1]);
/*  70 */     boolean flag = Json.getBoolean(obj, "usePlayerTexture", false);
/*  71 */     JsonArray jsonarray = (JsonArray)obj.get("models");
/*  72 */     checkNull(jsonarray, "Missing elements");
/*  73 */     Map<Object, Object> map = new HashMap<>();
/*  74 */     List list = new ArrayList();
/*     */ 
/*     */     
/*  77 */     for (int i = 0; i < jsonarray.size(); i++) {
/*  78 */       JsonObject jsonobject = (JsonObject)jsonarray.get(i);
/*  79 */       String s1 = Json.getString(jsonobject, "baseId");
/*     */       
/*  81 */       if (s1 != null)
/*  82 */       { JsonObject jsonobject1 = (JsonObject)map.get(s1);
/*     */         
/*  84 */         if (jsonobject1 == null)
/*  85 */         { Config.warn("BaseID not found: " + s1); }
/*     */         
/*     */         else
/*     */         
/*  89 */         { for (Map.Entry<String, JsonElement> entry : (Iterable<Map.Entry<String, JsonElement>>)jsonobject1.entrySet()) {
/*  90 */             if (!jsonobject.has(entry.getKey())) {
/*  91 */               jsonobject.add(entry.getKey(), entry.getValue());
/*     */             }
/*     */           } 
/*     */ 
/*     */           
/*  96 */           String s2 = Json.getString(jsonobject, "id"); }  continue; }  String str1 = Json.getString(jsonobject, "id");
/*     */     } 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 113 */     PlayerItemRenderer[] aplayeritemrenderer = (PlayerItemRenderer[])list.toArray((Object[])new PlayerItemRenderer[list.size()]);
/* 114 */     return new PlayerItemModel(dimension, flag, aplayeritemrenderer);
/*     */   }
/*     */ 
/*     */   
/*     */   private static void checkNull(Object obj, String msg) {
/* 119 */     if (obj == null) {
/* 120 */       throw new JsonParseException(msg);
/*     */     }
/*     */   }
/*     */   
/*     */   private static ResourceLocation makeResourceLocation(String texture) {
/* 125 */     int i = texture.indexOf(':');
/*     */     
/* 127 */     if (i < 0) {
/* 128 */       return new ResourceLocation(texture);
/*     */     }
/* 130 */     String s = texture.substring(0, i);
/* 131 */     String s1 = texture.substring(i + 1);
/* 132 */     return new ResourceLocation(s, s1);
/*     */   }
/*     */ 
/*     */   
/*     */   private static int parseAttachModel(String attachModelStr) {
/* 137 */     if (attachModelStr == null)
/* 138 */       return 0; 
/* 139 */     if (attachModelStr.equals("body"))
/* 140 */       return 0; 
/* 141 */     if (attachModelStr.equals("head"))
/* 142 */       return 1; 
/* 143 */     if (attachModelStr.equals("leftArm"))
/* 144 */       return 2; 
/* 145 */     if (attachModelStr.equals("rightArm"))
/* 146 */       return 3; 
/* 147 */     if (attachModelStr.equals("leftLeg"))
/* 148 */       return 4; 
/* 149 */     if (attachModelStr.equals("rightLeg"))
/* 150 */       return 5; 
/* 151 */     if (attachModelStr.equals("cape")) {
/* 152 */       return 6;
/*     */     }
/* 154 */     Config.warn("Unknown attachModel: " + attachModelStr);
/* 155 */     return 0;
/*     */   }
/*     */ 
/*     */   
/*     */   public static PlayerItemRenderer parseItemRenderer(JsonObject elem, Dimension textureDim) {
/* 160 */     String s = Json.getString(elem, "type");
/*     */     
/* 162 */     if (!Config.equals(s, "ModelBox")) {
/* 163 */       Config.warn("Unknown model type: " + s);
/* 164 */       return null;
/*     */     } 
/* 166 */     String s1 = Json.getString(elem, "attachTo");
/* 167 */     int i = parseAttachModel(s1);
/* 168 */     ModelBase modelbase = new ModelPlayerItem();
/* 169 */     modelbase.textureWidth = textureDim.width;
/* 170 */     modelbase.textureHeight = textureDim.height;
/* 171 */     ModelRenderer modelrenderer = parseModelRenderer(elem, modelbase, null, null);
/* 172 */     PlayerItemRenderer playeritemrenderer = new PlayerItemRenderer(i, modelrenderer);
/* 173 */     return playeritemrenderer;
/*     */   }
/*     */ 
/*     */   
/*     */   public static ModelRenderer parseModelRenderer(JsonObject elem, ModelBase modelBase, int[] parentTextureSize, String basePath) {
/* 178 */     ModelRenderer modelrenderer = new ModelRenderer(modelBase);
/* 179 */     String s = Json.getString(elem, "id");
/* 180 */     modelrenderer.setId(s);
/* 181 */     float f = Json.getFloat(elem, "scale", 1.0F);
/* 182 */     modelrenderer.scaleX = f;
/* 183 */     modelrenderer.scaleY = f;
/* 184 */     modelrenderer.scaleZ = f;
/* 185 */     String s1 = Json.getString(elem, "texture");
/*     */     
/* 187 */     if (s1 != null) {
/* 188 */       modelrenderer.setTextureLocation(CustomEntityModelParser.getResourceLocation(basePath, s1, ".png"));
/*     */     }
/*     */     
/* 191 */     int[] aint = Json.parseIntArray(elem.get("textureSize"), 2);
/*     */     
/* 193 */     if (aint == null) {
/* 194 */       aint = parentTextureSize;
/*     */     }
/*     */     
/* 197 */     if (aint != null) {
/* 198 */       modelrenderer.setTextureSize(aint[0], aint[1]);
/*     */     }
/*     */     
/* 201 */     String s2 = Json.getString(elem, "invertAxis", "").toLowerCase();
/* 202 */     boolean flag = s2.contains("x");
/* 203 */     boolean flag1 = s2.contains("y");
/* 204 */     boolean flag2 = s2.contains("z");
/* 205 */     float[] afloat = Json.parseFloatArray(elem.get("translate"), 3, new float[3]);
/*     */     
/* 207 */     if (flag) {
/* 208 */       afloat[0] = -afloat[0];
/*     */     }
/*     */     
/* 211 */     if (flag1) {
/* 212 */       afloat[1] = -afloat[1];
/*     */     }
/*     */     
/* 215 */     if (flag2) {
/* 216 */       afloat[2] = -afloat[2];
/*     */     }
/*     */     
/* 219 */     float[] afloat1 = Json.parseFloatArray(elem.get("rotate"), 3, new float[3]);
/*     */     
/* 221 */     for (int i = 0; i < afloat1.length; i++) {
/* 222 */       afloat1[i] = afloat1[i] / 180.0F * MathHelper.PI;
/*     */     }
/*     */     
/* 225 */     if (flag) {
/* 226 */       afloat1[0] = -afloat1[0];
/*     */     }
/*     */     
/* 229 */     if (flag1) {
/* 230 */       afloat1[1] = -afloat1[1];
/*     */     }
/*     */     
/* 233 */     if (flag2) {
/* 234 */       afloat1[2] = -afloat1[2];
/*     */     }
/*     */     
/* 237 */     modelrenderer.setRotationPoint(afloat[0], afloat[1], afloat[2]);
/* 238 */     modelrenderer.rotateAngleX = afloat1[0];
/* 239 */     modelrenderer.rotateAngleY = afloat1[1];
/* 240 */     modelrenderer.rotateAngleZ = afloat1[2];
/* 241 */     String s3 = Json.getString(elem, "mirrorTexture", "").toLowerCase();
/* 242 */     boolean flag3 = s3.contains("u");
/* 243 */     boolean flag4 = s3.contains("v");
/*     */     
/* 245 */     if (flag3) {
/* 246 */       modelrenderer.mirror = true;
/*     */     }
/*     */     
/* 249 */     if (flag4) {
/* 250 */       modelrenderer.mirrorV = true;
/*     */     }
/*     */     
/* 253 */     JsonArray jsonarray = elem.getAsJsonArray("boxes");
/*     */     
/* 255 */     if (jsonarray != null) {
/* 256 */       for (int j = 0; j < jsonarray.size(); j++) {
/* 257 */         JsonObject jsonobject = jsonarray.get(j).getAsJsonObject();
/* 258 */         int[] aint1 = Json.parseIntArray(jsonobject.get("textureOffset"), 2);
/* 259 */         int[][] aint2 = parseFaceUvs(jsonobject);
/*     */         
/* 261 */         if (aint1 == null && aint2 == null) {
/* 262 */           throw new JsonParseException("Texture offset not specified");
/*     */         }
/*     */         
/* 265 */         float[] afloat2 = Json.parseFloatArray(jsonobject.get("coordinates"), 6);
/*     */         
/* 267 */         if (afloat2 == null) {
/* 268 */           throw new JsonParseException("Coordinates not specified");
/*     */         }
/*     */         
/* 271 */         if (flag) {
/* 272 */           afloat2[0] = -afloat2[0] - afloat2[3];
/*     */         }
/*     */         
/* 275 */         if (flag1) {
/* 276 */           afloat2[1] = -afloat2[1] - afloat2[4];
/*     */         }
/*     */         
/* 279 */         if (flag2) {
/* 280 */           afloat2[2] = -afloat2[2] - afloat2[5];
/*     */         }
/*     */         
/* 283 */         float f1 = Json.getFloat(jsonobject, "sizeAdd", 0.0F);
/*     */         
/* 285 */         if (aint2 != null) {
/* 286 */           modelrenderer.addBox(aint2, afloat2[0], afloat2[1], afloat2[2], afloat2[3], afloat2[4], afloat2[5], f1);
/*     */         } else {
/* 288 */           modelrenderer.setTextureOffset(aint1[0], aint1[1]);
/* 289 */           modelrenderer.addBox(afloat2[0], afloat2[1], afloat2[2], (int)afloat2[3], (int)afloat2[4], (int)afloat2[5], f1);
/*     */         } 
/*     */       } 
/*     */     }
/*     */     
/* 294 */     JsonArray jsonarray1 = elem.getAsJsonArray("sprites");
/*     */     
/* 296 */     if (jsonarray1 != null) {
/* 297 */       for (int k = 0; k < jsonarray1.size(); k++) {
/* 298 */         JsonObject jsonobject2 = jsonarray1.get(k).getAsJsonObject();
/* 299 */         int[] aint3 = Json.parseIntArray(jsonobject2.get("textureOffset"), 2);
/*     */         
/* 301 */         if (aint3 == null) {
/* 302 */           throw new JsonParseException("Texture offset not specified");
/*     */         }
/*     */         
/* 305 */         float[] afloat3 = Json.parseFloatArray(jsonobject2.get("coordinates"), 6);
/*     */         
/* 307 */         if (afloat3 == null) {
/* 308 */           throw new JsonParseException("Coordinates not specified");
/*     */         }
/*     */         
/* 311 */         if (flag) {
/* 312 */           afloat3[0] = -afloat3[0] - afloat3[3];
/*     */         }
/*     */         
/* 315 */         if (flag1) {
/* 316 */           afloat3[1] = -afloat3[1] - afloat3[4];
/*     */         }
/*     */         
/* 319 */         if (flag2) {
/* 320 */           afloat3[2] = -afloat3[2] - afloat3[5];
/*     */         }
/*     */         
/* 323 */         float f2 = Json.getFloat(jsonobject2, "sizeAdd", 0.0F);
/* 324 */         modelrenderer.setTextureOffset(aint3[0], aint3[1]);
/* 325 */         modelrenderer.addSprite(afloat3[0], afloat3[1], afloat3[2], (int)afloat3[3], (int)afloat3[4], (int)afloat3[5], f2);
/*     */       } 
/*     */     }
/*     */     
/* 329 */     JsonObject jsonobject1 = (JsonObject)elem.get("submodel");
/*     */     
/* 331 */     if (jsonobject1 != null) {
/* 332 */       ModelRenderer modelrenderer2 = parseModelRenderer(jsonobject1, modelBase, aint, basePath);
/* 333 */       modelrenderer.addChild(modelrenderer2);
/*     */     } 
/*     */     
/* 336 */     JsonArray jsonarray2 = (JsonArray)elem.get("submodels");
/*     */     
/* 338 */     if (jsonarray2 != null) {
/* 339 */       for (int l = 0; l < jsonarray2.size(); l++) {
/* 340 */         JsonObject jsonobject3 = (JsonObject)jsonarray2.get(l);
/* 341 */         ModelRenderer modelrenderer3 = parseModelRenderer(jsonobject3, modelBase, aint, basePath);
/*     */         
/* 343 */         if (modelrenderer3.getId() != null) {
/* 344 */           ModelRenderer modelrenderer1 = modelrenderer.getChild(modelrenderer3.getId());
/*     */           
/* 346 */           if (modelrenderer1 != null) {
/* 347 */             Config.warn("Duplicate model ID: " + modelrenderer3.getId());
/*     */           }
/*     */         } 
/*     */         
/* 351 */         modelrenderer.addChild(modelrenderer3);
/*     */       } 
/*     */     }
/*     */     
/* 355 */     return modelrenderer;
/*     */   }
/*     */   
/*     */   private static int[][] parseFaceUvs(JsonObject box) {
/* 359 */     int[][] aint = { Json.parseIntArray(box.get("uvDown"), 4), Json.parseIntArray(box.get("uvUp"), 4), Json.parseIntArray(box.get("uvNorth"), 4), Json.parseIntArray(box.get("uvSouth"), 4), Json.parseIntArray(box.get("uvWest"), 4), Json.parseIntArray(box.get("uvEast"), 4) };
/*     */     
/* 361 */     if (aint[2] == null) {
/* 362 */       aint[2] = Json.parseIntArray(box.get("uvFront"), 4);
/*     */     }
/*     */     
/* 365 */     if (aint[3] == null) {
/* 366 */       aint[3] = Json.parseIntArray(box.get("uvBack"), 4);
/*     */     }
/*     */     
/* 369 */     if (aint[4] == null) {
/* 370 */       aint[4] = Json.parseIntArray(box.get("uvLeft"), 4);
/*     */     }
/*     */     
/* 373 */     if (aint[5] == null) {
/* 374 */       aint[5] = Json.parseIntArray(box.get("uvRight"), 4);
/*     */     }
/*     */     
/* 377 */     boolean flag = false;
/*     */     
/* 379 */     for (int i = 0; i < aint.length; i++) {
/* 380 */       if (aint[i] != null) {
/* 381 */         flag = true;
/*     */       }
/*     */     } 
/*     */     
/* 385 */     if (!flag) {
/* 386 */       return null;
/*     */     }
/* 388 */     return aint;
/*     */   }
/*     */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\optifine\player\PlayerItemParser.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */