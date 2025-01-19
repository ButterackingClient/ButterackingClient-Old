/*     */ package net.minecraft.client.renderer.block.model;
/*     */ 
/*     */ import com.google.common.collect.Maps;
/*     */ import com.google.gson.JsonArray;
/*     */ import com.google.gson.JsonDeserializationContext;
/*     */ import com.google.gson.JsonDeserializer;
/*     */ import com.google.gson.JsonElement;
/*     */ import com.google.gson.JsonObject;
/*     */ import com.google.gson.JsonParseException;
/*     */ import java.lang.reflect.Type;
/*     */ import java.util.Map;
/*     */ import net.minecraft.util.EnumFacing;
/*     */ import net.minecraft.util.JsonUtils;
/*     */ import net.minecraft.util.MathHelper;
/*     */ import org.lwjgl.util.vector.Vector3f;
/*     */ 
/*     */ 
/*     */ 
/*     */ public class BlockPart
/*     */ {
/*     */   public final Vector3f positionFrom;
/*     */   public final Vector3f positionTo;
/*     */   public final Map<EnumFacing, BlockPartFace> mapFaces;
/*     */   public final BlockPartRotation partRotation;
/*     */   public final boolean shade;
/*     */   
/*     */   public BlockPart(Vector3f positionFromIn, Vector3f positionToIn, Map<EnumFacing, BlockPartFace> mapFacesIn, BlockPartRotation partRotationIn, boolean shadeIn) {
/*  28 */     this.positionFrom = positionFromIn;
/*  29 */     this.positionTo = positionToIn;
/*  30 */     this.mapFaces = mapFacesIn;
/*  31 */     this.partRotation = partRotationIn;
/*  32 */     this.shade = shadeIn;
/*  33 */     setDefaultUvs();
/*     */   }
/*     */   
/*     */   private void setDefaultUvs() {
/*  37 */     for (Map.Entry<EnumFacing, BlockPartFace> entry : this.mapFaces.entrySet()) {
/*  38 */       float[] afloat = getFaceUvs(entry.getKey());
/*  39 */       ((BlockPartFace)entry.getValue()).blockFaceUV.setUvs(afloat);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private float[] getFaceUvs(EnumFacing p_178236_1_) {
/*     */     float[] afloat;
/*  46 */     switch (p_178236_1_) {
/*     */       case null:
/*     */       case UP:
/*  49 */         afloat = new float[] { this.positionFrom.x, this.positionFrom.z, this.positionTo.x, this.positionTo.z };
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
/*  63 */         return afloat;case NORTH: case SOUTH: afloat = new float[] { this.positionFrom.x, 16.0F - this.positionTo.y, this.positionTo.x, 16.0F - this.positionFrom.y }; return afloat;case WEST: case EAST: afloat = new float[] { this.positionFrom.z, 16.0F - this.positionTo.y, this.positionTo.z, 16.0F - this.positionFrom.y }; return afloat;
/*     */     } 
/*     */     throw new NullPointerException();
/*     */   }
/*     */   static class Deserializer implements JsonDeserializer<BlockPart> { public BlockPart deserialize(JsonElement p_deserialize_1_, Type p_deserialize_2_, JsonDeserializationContext p_deserialize_3_) throws JsonParseException {
/*  68 */       JsonObject jsonobject = p_deserialize_1_.getAsJsonObject();
/*  69 */       Vector3f vector3f = parsePositionFrom(jsonobject);
/*  70 */       Vector3f vector3f1 = parsePositionTo(jsonobject);
/*  71 */       BlockPartRotation blockpartrotation = parseRotation(jsonobject);
/*  72 */       Map<EnumFacing, BlockPartFace> map = parseFacesCheck(p_deserialize_3_, jsonobject);
/*     */       
/*  74 */       if (jsonobject.has("shade") && !JsonUtils.isBoolean(jsonobject, "shade")) {
/*  75 */         throw new JsonParseException("Expected shade to be a Boolean");
/*     */       }
/*  77 */       boolean flag = JsonUtils.getBoolean(jsonobject, "shade", true);
/*  78 */       return new BlockPart(vector3f, vector3f1, map, blockpartrotation, flag);
/*     */     }
/*     */ 
/*     */     
/*     */     private BlockPartRotation parseRotation(JsonObject p_178256_1_) {
/*  83 */       BlockPartRotation blockpartrotation = null;
/*     */       
/*  85 */       if (p_178256_1_.has("rotation")) {
/*  86 */         JsonObject jsonobject = JsonUtils.getJsonObject(p_178256_1_, "rotation");
/*  87 */         Vector3f vector3f = parsePosition(jsonobject, "origin");
/*  88 */         vector3f.scale(0.0625F);
/*  89 */         EnumFacing.Axis enumfacing$axis = parseAxis(jsonobject);
/*  90 */         float f = parseAngle(jsonobject);
/*  91 */         boolean flag = JsonUtils.getBoolean(jsonobject, "rescale", false);
/*  92 */         blockpartrotation = new BlockPartRotation(vector3f, enumfacing$axis, f, flag);
/*     */       } 
/*     */       
/*  95 */       return blockpartrotation;
/*     */     }
/*     */     
/*     */     private float parseAngle(JsonObject p_178255_1_) {
/*  99 */       float f = JsonUtils.getFloat(p_178255_1_, "angle");
/*     */       
/* 101 */       if (f != 0.0F && MathHelper.abs(f) != 22.5F && MathHelper.abs(f) != 45.0F) {
/* 102 */         throw new JsonParseException("Invalid rotation " + f + " found, only -45/-22.5/0/22.5/45 allowed");
/*     */       }
/* 104 */       return f;
/*     */     }
/*     */ 
/*     */     
/*     */     private EnumFacing.Axis parseAxis(JsonObject p_178252_1_) {
/* 109 */       String s = JsonUtils.getString(p_178252_1_, "axis");
/* 110 */       EnumFacing.Axis enumfacing$axis = EnumFacing.Axis.byName(s.toLowerCase());
/*     */       
/* 112 */       if (enumfacing$axis == null) {
/* 113 */         throw new JsonParseException("Invalid rotation axis: " + s);
/*     */       }
/* 115 */       return enumfacing$axis;
/*     */     }
/*     */ 
/*     */     
/*     */     private Map<EnumFacing, BlockPartFace> parseFacesCheck(JsonDeserializationContext p_178250_1_, JsonObject p_178250_2_) {
/* 120 */       Map<EnumFacing, BlockPartFace> map = parseFaces(p_178250_1_, p_178250_2_);
/*     */       
/* 122 */       if (map.isEmpty()) {
/* 123 */         throw new JsonParseException("Expected between 1 and 6 unique faces, got 0");
/*     */       }
/* 125 */       return map;
/*     */     }
/*     */ 
/*     */     
/*     */     private Map<EnumFacing, BlockPartFace> parseFaces(JsonDeserializationContext p_178253_1_, JsonObject p_178253_2_) {
/* 130 */       Map<EnumFacing, BlockPartFace> map = Maps.newEnumMap(EnumFacing.class);
/* 131 */       JsonObject jsonobject = JsonUtils.getJsonObject(p_178253_2_, "faces");
/*     */       
/* 133 */       for (Map.Entry<String, JsonElement> entry : (Iterable<Map.Entry<String, JsonElement>>)jsonobject.entrySet()) {
/* 134 */         EnumFacing enumfacing = parseEnumFacing(entry.getKey());
/* 135 */         map.put(enumfacing, (BlockPartFace)p_178253_1_.deserialize(entry.getValue(), BlockPartFace.class));
/*     */       } 
/*     */       
/* 138 */       return map;
/*     */     }
/*     */     
/*     */     private EnumFacing parseEnumFacing(String name) {
/* 142 */       EnumFacing enumfacing = EnumFacing.byName(name);
/*     */       
/* 144 */       if (enumfacing == null) {
/* 145 */         throw new JsonParseException("Unknown facing: " + name);
/*     */       }
/* 147 */       return enumfacing;
/*     */     }
/*     */ 
/*     */     
/*     */     private Vector3f parsePositionTo(JsonObject p_178247_1_) {
/* 152 */       Vector3f vector3f = parsePosition(p_178247_1_, "to");
/*     */       
/* 154 */       if (vector3f.x >= -16.0F && vector3f.y >= -16.0F && vector3f.z >= -16.0F && vector3f.x <= 32.0F && vector3f.y <= 32.0F && vector3f.z <= 32.0F) {
/* 155 */         return vector3f;
/*     */       }
/* 157 */       throw new JsonParseException("'to' specifier exceeds the allowed boundaries: " + vector3f);
/*     */     }
/*     */ 
/*     */     
/*     */     private Vector3f parsePositionFrom(JsonObject p_178249_1_) {
/* 162 */       Vector3f vector3f = parsePosition(p_178249_1_, "from");
/*     */       
/* 164 */       if (vector3f.x >= -16.0F && vector3f.y >= -16.0F && vector3f.z >= -16.0F && vector3f.x <= 32.0F && vector3f.y <= 32.0F && vector3f.z <= 32.0F) {
/* 165 */         return vector3f;
/*     */       }
/* 167 */       throw new JsonParseException("'from' specifier exceeds the allowed boundaries: " + vector3f);
/*     */     }
/*     */ 
/*     */     
/*     */     private Vector3f parsePosition(JsonObject p_178251_1_, String p_178251_2_) {
/* 172 */       JsonArray jsonarray = JsonUtils.getJsonArray(p_178251_1_, p_178251_2_);
/*     */       
/* 174 */       if (jsonarray.size() != 3) {
/* 175 */         throw new JsonParseException("Expected 3 " + p_178251_2_ + " values, found: " + jsonarray.size());
/*     */       }
/* 177 */       float[] afloat = new float[3];
/*     */       
/* 179 */       for (int i = 0; i < afloat.length; i++) {
/* 180 */         afloat[i] = JsonUtils.getFloat(jsonarray.get(i), String.valueOf(p_178251_2_) + "[" + i + "]");
/*     */       }
/*     */       
/* 183 */       return new Vector3f(afloat[0], afloat[1], afloat[2]);
/*     */     } }
/*     */ 
/*     */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\minecraft\client\renderer\block\model\BlockPart.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */