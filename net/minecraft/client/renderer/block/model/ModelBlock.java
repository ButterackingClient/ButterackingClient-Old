/*     */ package net.minecraft.client.renderer.block.model;
/*     */ 
/*     */ import com.google.common.collect.Lists;
/*     */ import com.google.common.collect.Maps;
/*     */ import com.google.gson.Gson;
/*     */ import com.google.gson.GsonBuilder;
/*     */ import com.google.gson.JsonDeserializationContext;
/*     */ import com.google.gson.JsonDeserializer;
/*     */ import com.google.gson.JsonElement;
/*     */ import com.google.gson.JsonObject;
/*     */ import com.google.gson.JsonParseException;
/*     */ import java.io.Reader;
/*     */ import java.io.StringReader;
/*     */ import java.lang.reflect.Type;
/*     */ import java.util.Collections;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import net.minecraft.util.JsonUtils;
/*     */ import net.minecraft.util.ResourceLocation;
/*     */ import org.apache.commons.lang3.StringUtils;
/*     */ import org.apache.logging.log4j.LogManager;
/*     */ import org.apache.logging.log4j.Logger;
/*     */ 
/*     */ 
/*     */ 
/*     */ public class ModelBlock
/*     */ {
/*  28 */   private static final Logger LOGGER = LogManager.getLogger();
/*  29 */   static final Gson SERIALIZER = (new GsonBuilder()).registerTypeAdapter(ModelBlock.class, new Deserializer()).registerTypeAdapter(BlockPart.class, new BlockPart.Deserializer()).registerTypeAdapter(BlockPartFace.class, new BlockPartFace.Deserializer()).registerTypeAdapter(BlockFaceUV.class, new BlockFaceUV.Deserializer()).registerTypeAdapter(ItemTransformVec3f.class, new ItemTransformVec3f.Deserializer()).registerTypeAdapter(ItemCameraTransforms.class, new ItemCameraTransforms.Deserializer()).create();
/*     */   private final List<BlockPart> elements;
/*     */   private final boolean gui3d;
/*     */   private final boolean ambientOcclusion;
/*     */   private ItemCameraTransforms cameraTransforms;
/*     */   public String name;
/*     */   protected final Map<String, String> textures;
/*     */   protected ModelBlock parent;
/*     */   protected ResourceLocation parentLocation;
/*     */   
/*     */   public static ModelBlock deserialize(Reader readerIn) {
/*  40 */     return (ModelBlock)SERIALIZER.fromJson(readerIn, ModelBlock.class);
/*     */   }
/*     */   
/*     */   public static ModelBlock deserialize(String jsonString) {
/*  44 */     return deserialize(new StringReader(jsonString));
/*     */   }
/*     */   
/*     */   protected ModelBlock(List<BlockPart> elementsIn, Map<String, String> texturesIn, boolean ambientOcclusionIn, boolean gui3dIn, ItemCameraTransforms cameraTransformsIn) {
/*  48 */     this(null, elementsIn, texturesIn, ambientOcclusionIn, gui3dIn, cameraTransformsIn);
/*     */   }
/*     */   
/*     */   protected ModelBlock(ResourceLocation parentLocationIn, Map<String, String> texturesIn, boolean ambientOcclusionIn, boolean gui3dIn, ItemCameraTransforms cameraTransformsIn) {
/*  52 */     this(parentLocationIn, Collections.emptyList(), texturesIn, ambientOcclusionIn, gui3dIn, cameraTransformsIn);
/*     */   }
/*     */   
/*     */   private ModelBlock(ResourceLocation parentLocationIn, List<BlockPart> elementsIn, Map<String, String> texturesIn, boolean ambientOcclusionIn, boolean gui3dIn, ItemCameraTransforms cameraTransformsIn) {
/*  56 */     this.name = "";
/*  57 */     this.elements = elementsIn;
/*  58 */     this.ambientOcclusion = ambientOcclusionIn;
/*  59 */     this.gui3d = gui3dIn;
/*  60 */     this.textures = texturesIn;
/*  61 */     this.parentLocation = parentLocationIn;
/*  62 */     this.cameraTransforms = cameraTransformsIn;
/*     */   }
/*     */   
/*     */   public List<BlockPart> getElements() {
/*  66 */     return hasParent() ? this.parent.getElements() : this.elements;
/*     */   }
/*     */   
/*     */   private boolean hasParent() {
/*  70 */     return (this.parent != null);
/*     */   }
/*     */   
/*     */   public boolean isAmbientOcclusion() {
/*  74 */     return hasParent() ? this.parent.isAmbientOcclusion() : this.ambientOcclusion;
/*     */   }
/*     */   
/*     */   public boolean isGui3d() {
/*  78 */     return this.gui3d;
/*     */   }
/*     */   
/*     */   public boolean isResolved() {
/*  82 */     return !(this.parentLocation != null && (this.parent == null || !this.parent.isResolved()));
/*     */   }
/*     */   
/*     */   public void getParentFromMap(Map<ResourceLocation, ModelBlock> p_178299_1_) {
/*  86 */     if (this.parentLocation != null) {
/*  87 */       this.parent = p_178299_1_.get(this.parentLocation);
/*     */     }
/*     */   }
/*     */   
/*     */   public boolean isTexturePresent(String textureName) {
/*  92 */     return !"missingno".equals(resolveTextureName(textureName));
/*     */   }
/*     */   
/*     */   public String resolveTextureName(String textureName) {
/*  96 */     if (!startsWithHash(textureName)) {
/*  97 */       textureName = String.valueOf('#') + textureName;
/*     */     }
/*     */     
/* 100 */     return resolveTextureName(textureName, new Bookkeep(this, null));
/*     */   }
/*     */   
/*     */   private String resolveTextureName(String textureName, Bookkeep p_178302_2_) {
/* 104 */     if (startsWithHash(textureName)) {
/* 105 */       if (this == p_178302_2_.modelExt) {
/* 106 */         LOGGER.warn("Unable to resolve texture due to upward reference: " + textureName + " in " + this.name);
/* 107 */         return "missingno";
/*     */       } 
/* 109 */       String s = this.textures.get(textureName.substring(1));
/*     */       
/* 111 */       if (s == null && hasParent()) {
/* 112 */         s = this.parent.resolveTextureName(textureName, p_178302_2_);
/*     */       }
/*     */       
/* 115 */       p_178302_2_.modelExt = this;
/*     */       
/* 117 */       if (s != null && startsWithHash(s)) {
/* 118 */         s = p_178302_2_.model.resolveTextureName(s, p_178302_2_);
/*     */       }
/*     */       
/* 121 */       return (s != null && !startsWithHash(s)) ? s : "missingno";
/*     */     } 
/*     */     
/* 124 */     return textureName;
/*     */   }
/*     */ 
/*     */   
/*     */   private boolean startsWithHash(String hash) {
/* 129 */     return (hash.charAt(0) == '#');
/*     */   }
/*     */   
/*     */   public ResourceLocation getParentLocation() {
/* 133 */     return this.parentLocation;
/*     */   }
/*     */   
/*     */   public ModelBlock getRootModel() {
/* 137 */     return hasParent() ? this.parent.getRootModel() : this;
/*     */   }
/*     */   
/*     */   public ItemCameraTransforms getAllTransforms() {
/* 141 */     ItemTransformVec3f itemtransformvec3f = getTransform(ItemCameraTransforms.TransformType.THIRD_PERSON);
/* 142 */     ItemTransformVec3f itemtransformvec3f1 = getTransform(ItemCameraTransforms.TransformType.FIRST_PERSON);
/* 143 */     ItemTransformVec3f itemtransformvec3f2 = getTransform(ItemCameraTransforms.TransformType.HEAD);
/* 144 */     ItemTransformVec3f itemtransformvec3f3 = getTransform(ItemCameraTransforms.TransformType.GUI);
/* 145 */     ItemTransformVec3f itemtransformvec3f4 = getTransform(ItemCameraTransforms.TransformType.GROUND);
/* 146 */     ItemTransformVec3f itemtransformvec3f5 = getTransform(ItemCameraTransforms.TransformType.FIXED);
/* 147 */     return new ItemCameraTransforms(itemtransformvec3f, itemtransformvec3f1, itemtransformvec3f2, itemtransformvec3f3, itemtransformvec3f4, itemtransformvec3f5);
/*     */   }
/*     */   
/*     */   private ItemTransformVec3f getTransform(ItemCameraTransforms.TransformType type) {
/* 151 */     return (this.parent != null && !this.cameraTransforms.func_181687_c(type)) ? this.parent.getTransform(type) : this.cameraTransforms.getTransform(type);
/*     */   }
/*     */   
/*     */   public static void checkModelHierarchy(Map<ResourceLocation, ModelBlock> p_178312_0_) {
/* 155 */     for (ModelBlock modelblock : p_178312_0_.values()) {
/*     */       try {
/* 157 */         ModelBlock modelblock1 = modelblock.parent;
/*     */         
/* 159 */         for (ModelBlock modelblock2 = modelblock1.parent; modelblock1 != modelblock2; modelblock2 = modelblock2.parent.parent) {
/* 160 */           modelblock1 = modelblock1.parent;
/*     */         }
/*     */         
/* 163 */         throw new LoopException();
/* 164 */       } catch (NullPointerException nullPointerException) {}
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   static final class Bookkeep
/*     */   {
/*     */     public final ModelBlock model;
/*     */     public ModelBlock modelExt;
/*     */     
/*     */     private Bookkeep(ModelBlock p_i46223_1_) {
/* 175 */       this.model = p_i46223_1_;
/*     */     }
/*     */   }
/*     */   
/*     */   public static class Deserializer implements JsonDeserializer<ModelBlock> {
/*     */     public ModelBlock deserialize(JsonElement p_deserialize_1_, Type p_deserialize_2_, JsonDeserializationContext p_deserialize_3_) throws JsonParseException {
/* 181 */       JsonObject jsonobject = p_deserialize_1_.getAsJsonObject();
/* 182 */       List<BlockPart> list = getModelElements(p_deserialize_3_, jsonobject);
/* 183 */       String s = getParent(jsonobject);
/* 184 */       boolean flag = StringUtils.isEmpty(s);
/* 185 */       boolean flag1 = list.isEmpty();
/*     */       
/* 187 */       if (flag1 && flag)
/* 188 */         throw new JsonParseException("BlockModel requires either elements or parent, found neither"); 
/* 189 */       if (!flag && !flag1) {
/* 190 */         throw new JsonParseException("BlockModel requires either elements or parent, found both");
/*     */       }
/* 192 */       Map<String, String> map = getTextures(jsonobject);
/* 193 */       boolean flag2 = getAmbientOcclusionEnabled(jsonobject);
/* 194 */       ItemCameraTransforms itemcameratransforms = ItemCameraTransforms.DEFAULT;
/*     */       
/* 196 */       if (jsonobject.has("display")) {
/* 197 */         JsonObject jsonobject1 = JsonUtils.getJsonObject(jsonobject, "display");
/* 198 */         itemcameratransforms = (ItemCameraTransforms)p_deserialize_3_.deserialize((JsonElement)jsonobject1, ItemCameraTransforms.class);
/*     */       } 
/*     */       
/* 201 */       return flag1 ? new ModelBlock(new ResourceLocation(s), map, flag2, true, itemcameratransforms) : new ModelBlock(list, map, flag2, true, itemcameratransforms);
/*     */     }
/*     */ 
/*     */     
/*     */     private Map<String, String> getTextures(JsonObject p_178329_1_) {
/* 206 */       Map<String, String> map = Maps.newHashMap();
/*     */       
/* 208 */       if (p_178329_1_.has("textures")) {
/* 209 */         JsonObject jsonobject = p_178329_1_.getAsJsonObject("textures");
/*     */         
/* 211 */         for (Map.Entry<String, JsonElement> entry : (Iterable<Map.Entry<String, JsonElement>>)jsonobject.entrySet()) {
/* 212 */           map.put(entry.getKey(), ((JsonElement)entry.getValue()).getAsString());
/*     */         }
/*     */       } 
/*     */       
/* 216 */       return map;
/*     */     }
/*     */     
/*     */     private String getParent(JsonObject p_178326_1_) {
/* 220 */       return JsonUtils.getString(p_178326_1_, "parent", "");
/*     */     }
/*     */     
/*     */     protected boolean getAmbientOcclusionEnabled(JsonObject p_178328_1_) {
/* 224 */       return JsonUtils.getBoolean(p_178328_1_, "ambientocclusion", true);
/*     */     }
/*     */     
/*     */     protected List<BlockPart> getModelElements(JsonDeserializationContext p_178325_1_, JsonObject p_178325_2_) {
/* 228 */       List<BlockPart> list = Lists.newArrayList();
/*     */       
/* 230 */       if (p_178325_2_.has("elements")) {
/* 231 */         for (JsonElement jsonelement : JsonUtils.getJsonArray(p_178325_2_, "elements")) {
/* 232 */           list.add((BlockPart)p_178325_1_.deserialize(jsonelement, BlockPart.class));
/*     */         }
/*     */       }
/*     */       
/* 236 */       return list;
/*     */     }
/*     */   }
/*     */   
/*     */   public static class LoopException extends RuntimeException {}
/*     */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\minecraft\client\renderer\block\model\ModelBlock.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */