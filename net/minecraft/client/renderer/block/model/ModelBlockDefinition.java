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
/*     */ import java.lang.reflect.Type;
/*     */ import java.util.Collection;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import net.minecraft.client.resources.model.ModelRotation;
/*     */ import net.minecraft.util.JsonUtils;
/*     */ import net.minecraft.util.ResourceLocation;
/*     */ 
/*     */ 
/*     */ 
/*     */ public class ModelBlockDefinition
/*     */ {
/*  25 */   static final Gson GSON = (new GsonBuilder()).registerTypeAdapter(ModelBlockDefinition.class, new Deserializer()).registerTypeAdapter(Variant.class, new Variant.Deserializer()).create();
/*  26 */   private final Map<String, Variants> mapVariants = Maps.newHashMap();
/*     */   
/*     */   public static ModelBlockDefinition parseFromReader(Reader p_178331_0_) {
/*  29 */     return (ModelBlockDefinition)GSON.fromJson(p_178331_0_, ModelBlockDefinition.class);
/*     */   }
/*     */   
/*     */   public ModelBlockDefinition(Collection<Variants> p_i46221_1_) {
/*  33 */     for (Variants modelblockdefinition$variants : p_i46221_1_) {
/*  34 */       this.mapVariants.put(modelblockdefinition$variants.name, modelblockdefinition$variants);
/*     */     }
/*     */   }
/*     */   
/*     */   public ModelBlockDefinition(List<ModelBlockDefinition> p_i46222_1_) {
/*  39 */     for (ModelBlockDefinition modelblockdefinition : p_i46222_1_) {
/*  40 */       this.mapVariants.putAll(modelblockdefinition.mapVariants);
/*     */     }
/*     */   }
/*     */   
/*     */   public Variants getVariants(String p_178330_1_) {
/*  45 */     Variants modelblockdefinition$variants = this.mapVariants.get(p_178330_1_);
/*     */     
/*  47 */     if (modelblockdefinition$variants == null) {
/*  48 */       throw new MissingVariantException();
/*     */     }
/*  50 */     return modelblockdefinition$variants;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean equals(Object p_equals_1_) {
/*  55 */     if (this == p_equals_1_)
/*  56 */       return true; 
/*  57 */     if (p_equals_1_ instanceof ModelBlockDefinition) {
/*  58 */       ModelBlockDefinition modelblockdefinition = (ModelBlockDefinition)p_equals_1_;
/*  59 */       return this.mapVariants.equals(modelblockdefinition.mapVariants);
/*     */     } 
/*  61 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public int hashCode() {
/*  66 */     return this.mapVariants.hashCode();
/*     */   }
/*     */   
/*     */   public static class Deserializer implements JsonDeserializer<ModelBlockDefinition> {
/*     */     public ModelBlockDefinition deserialize(JsonElement p_deserialize_1_, Type p_deserialize_2_, JsonDeserializationContext p_deserialize_3_) throws JsonParseException {
/*  71 */       JsonObject jsonobject = p_deserialize_1_.getAsJsonObject();
/*  72 */       List<ModelBlockDefinition.Variants> list = parseVariantsList(p_deserialize_3_, jsonobject);
/*  73 */       return new ModelBlockDefinition(list);
/*     */     }
/*     */     
/*     */     protected List<ModelBlockDefinition.Variants> parseVariantsList(JsonDeserializationContext p_178334_1_, JsonObject p_178334_2_) {
/*  77 */       JsonObject jsonobject = JsonUtils.getJsonObject(p_178334_2_, "variants");
/*  78 */       List<ModelBlockDefinition.Variants> list = Lists.newArrayList();
/*     */       
/*  80 */       for (Map.Entry<String, JsonElement> entry : (Iterable<Map.Entry<String, JsonElement>>)jsonobject.entrySet()) {
/*  81 */         list.add(parseVariants(p_178334_1_, entry));
/*     */       }
/*     */       
/*  84 */       return list;
/*     */     }
/*     */     
/*     */     protected ModelBlockDefinition.Variants parseVariants(JsonDeserializationContext p_178335_1_, Map.Entry<String, JsonElement> p_178335_2_) {
/*  88 */       String s = p_178335_2_.getKey();
/*  89 */       List<ModelBlockDefinition.Variant> list = Lists.newArrayList();
/*  90 */       JsonElement jsonelement = p_178335_2_.getValue();
/*     */       
/*  92 */       if (jsonelement.isJsonArray()) {
/*  93 */         for (JsonElement jsonelement1 : jsonelement.getAsJsonArray()) {
/*  94 */           list.add((ModelBlockDefinition.Variant)p_178335_1_.deserialize(jsonelement1, ModelBlockDefinition.Variant.class));
/*     */         }
/*     */       } else {
/*  97 */         list.add((ModelBlockDefinition.Variant)p_178335_1_.deserialize(jsonelement, ModelBlockDefinition.Variant.class));
/*     */       } 
/*     */       
/* 100 */       return new ModelBlockDefinition.Variants(s, list);
/*     */     }
/*     */   }
/*     */   
/*     */   public class MissingVariantException
/*     */     extends RuntimeException {}
/*     */   
/*     */   public static class Variant {
/*     */     private final ResourceLocation modelLocation;
/*     */     private final ModelRotation modelRotation;
/*     */     private final boolean uvLock;
/*     */     private final int weight;
/*     */     
/*     */     public Variant(ResourceLocation modelLocationIn, ModelRotation modelRotationIn, boolean uvLockIn, int weightIn) {
/* 114 */       this.modelLocation = modelLocationIn;
/* 115 */       this.modelRotation = modelRotationIn;
/* 116 */       this.uvLock = uvLockIn;
/* 117 */       this.weight = weightIn;
/*     */     }
/*     */     
/*     */     public ResourceLocation getModelLocation() {
/* 121 */       return this.modelLocation;
/*     */     }
/*     */     
/*     */     public ModelRotation getRotation() {
/* 125 */       return this.modelRotation;
/*     */     }
/*     */     
/*     */     public boolean isUvLocked() {
/* 129 */       return this.uvLock;
/*     */     }
/*     */     
/*     */     public int getWeight() {
/* 133 */       return this.weight;
/*     */     }
/*     */     
/*     */     public boolean equals(Object p_equals_1_) {
/* 137 */       if (this == p_equals_1_)
/* 138 */         return true; 
/* 139 */       if (!(p_equals_1_ instanceof Variant)) {
/* 140 */         return false;
/*     */       }
/* 142 */       Variant modelblockdefinition$variant = (Variant)p_equals_1_;
/* 143 */       return (this.modelLocation.equals(modelblockdefinition$variant.modelLocation) && this.modelRotation == modelblockdefinition$variant.modelRotation && this.uvLock == modelblockdefinition$variant.uvLock);
/*     */     }
/*     */ 
/*     */     
/*     */     public int hashCode() {
/* 148 */       int i = this.modelLocation.hashCode();
/* 149 */       i = 31 * i + ((this.modelRotation != null) ? this.modelRotation.hashCode() : 0);
/* 150 */       i = 31 * i + (this.uvLock ? 1 : 0);
/* 151 */       return i;
/*     */     }
/*     */     
/*     */     public static class Deserializer implements JsonDeserializer<Variant> {
/*     */       public ModelBlockDefinition.Variant deserialize(JsonElement p_deserialize_1_, Type p_deserialize_2_, JsonDeserializationContext p_deserialize_3_) throws JsonParseException {
/* 156 */         JsonObject jsonobject = p_deserialize_1_.getAsJsonObject();
/* 157 */         String s = parseModel(jsonobject);
/* 158 */         ModelRotation modelrotation = parseRotation(jsonobject);
/* 159 */         boolean flag = parseUvLock(jsonobject);
/* 160 */         int i = parseWeight(jsonobject);
/* 161 */         return new ModelBlockDefinition.Variant(makeModelLocation(s), modelrotation, flag, i);
/*     */       }
/*     */       
/*     */       private ResourceLocation makeModelLocation(String p_178426_1_) {
/* 165 */         ResourceLocation resourcelocation = new ResourceLocation(p_178426_1_);
/* 166 */         resourcelocation = new ResourceLocation(resourcelocation.getResourceDomain(), "block/" + resourcelocation.getResourcePath());
/* 167 */         return resourcelocation;
/*     */       }
/*     */       
/*     */       private boolean parseUvLock(JsonObject p_178429_1_) {
/* 171 */         return JsonUtils.getBoolean(p_178429_1_, "uvlock", false);
/*     */       }
/*     */       
/*     */       protected ModelRotation parseRotation(JsonObject p_178428_1_) {
/* 175 */         int i = JsonUtils.getInt(p_178428_1_, "x", 0);
/* 176 */         int j = JsonUtils.getInt(p_178428_1_, "y", 0);
/* 177 */         ModelRotation modelrotation = ModelRotation.getModelRotation(i, j);
/*     */         
/* 179 */         if (modelrotation == null) {
/* 180 */           throw new JsonParseException("Invalid BlockModelRotation x: " + i + ", y: " + j);
/*     */         }
/* 182 */         return modelrotation;
/*     */       }
/*     */ 
/*     */       
/*     */       protected String parseModel(JsonObject p_178424_1_) {
/* 187 */         return JsonUtils.getString(p_178424_1_, "model");
/*     */       }
/*     */       
/*     */       protected int parseWeight(JsonObject p_178427_1_) {
/* 191 */         return JsonUtils.getInt(p_178427_1_, "weight", 1); } } } public static class Deserializer implements JsonDeserializer<Variant> { public ModelBlockDefinition.Variant deserialize(JsonElement p_deserialize_1_, Type p_deserialize_2_, JsonDeserializationContext p_deserialize_3_) throws JsonParseException { JsonObject jsonobject = p_deserialize_1_.getAsJsonObject(); String s = parseModel(jsonobject); ModelRotation modelrotation = parseRotation(jsonobject); boolean flag = parseUvLock(jsonobject); int i = parseWeight(jsonobject); return new ModelBlockDefinition.Variant(makeModelLocation(s), modelrotation, flag, i); } protected int parseWeight(JsonObject p_178427_1_) { return JsonUtils.getInt(p_178427_1_, "weight", 1); }
/*     */     private ResourceLocation makeModelLocation(String p_178426_1_) { ResourceLocation resourcelocation = new ResourceLocation(p_178426_1_); resourcelocation = new ResourceLocation(resourcelocation.getResourceDomain(), "block/" + resourcelocation.getResourcePath());
/*     */       return resourcelocation; } private boolean parseUvLock(JsonObject p_178429_1_) { return JsonUtils.getBoolean(p_178429_1_, "uvlock", false); } protected ModelRotation parseRotation(JsonObject p_178428_1_) { int i = JsonUtils.getInt(p_178428_1_, "x", 0);
/*     */       int j = JsonUtils.getInt(p_178428_1_, "y", 0);
/*     */       ModelRotation modelrotation = ModelRotation.getModelRotation(i, j);
/*     */       if (modelrotation == null)
/*     */         throw new JsonParseException("Invalid BlockModelRotation x: " + i + ", y: " + j); 
/*     */       return modelrotation; } protected String parseModel(JsonObject p_178424_1_) { return JsonUtils.getString(p_178424_1_, "model"); } }
/*     */    public static class Variants
/*     */   {
/* 201 */     private final String name; private final List<ModelBlockDefinition.Variant> listVariants; public Variants(String nameIn, List<ModelBlockDefinition.Variant> listVariantsIn) { this.name = nameIn;
/* 202 */       this.listVariants = listVariantsIn; }
/*     */ 
/*     */     
/*     */     public List<ModelBlockDefinition.Variant> getVariants() {
/* 206 */       return this.listVariants;
/*     */     }
/*     */     
/*     */     public boolean equals(Object p_equals_1_) {
/* 210 */       if (this == p_equals_1_)
/* 211 */         return true; 
/* 212 */       if (!(p_equals_1_ instanceof Variants)) {
/* 213 */         return false;
/*     */       }
/* 215 */       Variants modelblockdefinition$variants = (Variants)p_equals_1_;
/* 216 */       return !this.name.equals(modelblockdefinition$variants.name) ? false : this.listVariants.equals(modelblockdefinition$variants.listVariants);
/*     */     }
/*     */ 
/*     */     
/*     */     public int hashCode() {
/* 221 */       int i = this.name.hashCode();
/* 222 */       i = 31 * i + this.listVariants.hashCode();
/* 223 */       return i;
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\minecraft\client\renderer\block\model\ModelBlockDefinition.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */