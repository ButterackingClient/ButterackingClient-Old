/*    */ package net.minecraft.client.renderer.block.model;
/*    */ 
/*    */ import com.google.gson.JsonArray;
/*    */ import com.google.gson.JsonDeserializationContext;
/*    */ import com.google.gson.JsonDeserializer;
/*    */ import com.google.gson.JsonElement;
/*    */ import com.google.gson.JsonObject;
/*    */ import com.google.gson.JsonParseException;
/*    */ import java.lang.reflect.Type;
/*    */ import net.minecraft.util.JsonUtils;
/*    */ 
/*    */ 
/*    */ public class BlockFaceUV
/*    */ {
/*    */   public float[] uvs;
/*    */   public final int rotation;
/*    */   
/*    */   public BlockFaceUV(float[] uvsIn, int rotationIn) {
/* 19 */     this.uvs = uvsIn;
/* 20 */     this.rotation = rotationIn;
/*    */   }
/*    */   
/*    */   public float func_178348_a(int p_178348_1_) {
/* 24 */     if (this.uvs == null) {
/* 25 */       throw new NullPointerException("uvs");
/*    */     }
/* 27 */     int i = func_178347_d(p_178348_1_);
/* 28 */     return (i != 0 && i != 1) ? this.uvs[2] : this.uvs[0];
/*    */   }
/*    */ 
/*    */   
/*    */   public float func_178346_b(int p_178346_1_) {
/* 33 */     if (this.uvs == null) {
/* 34 */       throw new NullPointerException("uvs");
/*    */     }
/* 36 */     int i = func_178347_d(p_178346_1_);
/* 37 */     return (i != 0 && i != 3) ? this.uvs[3] : this.uvs[1];
/*    */   }
/*    */ 
/*    */   
/*    */   private int func_178347_d(int p_178347_1_) {
/* 42 */     return (p_178347_1_ + this.rotation / 90) % 4;
/*    */   }
/*    */   
/*    */   public int func_178345_c(int p_178345_1_) {
/* 46 */     return (p_178345_1_ + 4 - this.rotation / 90) % 4;
/*    */   }
/*    */   
/*    */   public void setUvs(float[] uvsIn) {
/* 50 */     if (this.uvs == null)
/* 51 */       this.uvs = uvsIn; 
/*    */   }
/*    */   
/*    */   static class Deserializer
/*    */     implements JsonDeserializer<BlockFaceUV> {
/*    */     public BlockFaceUV deserialize(JsonElement p_deserialize_1_, Type p_deserialize_2_, JsonDeserializationContext p_deserialize_3_) throws JsonParseException {
/* 57 */       JsonObject jsonobject = p_deserialize_1_.getAsJsonObject();
/* 58 */       float[] afloat = parseUV(jsonobject);
/* 59 */       int i = parseRotation(jsonobject);
/* 60 */       return new BlockFaceUV(afloat, i);
/*    */     }
/*    */     
/*    */     protected int parseRotation(JsonObject p_178291_1_) {
/* 64 */       int i = JsonUtils.getInt(p_178291_1_, "rotation", 0);
/*    */       
/* 66 */       if (i >= 0 && i % 90 == 0 && i / 90 <= 3) {
/* 67 */         return i;
/*    */       }
/* 69 */       throw new JsonParseException("Invalid rotation " + i + " found, only 0/90/180/270 allowed");
/*    */     }
/*    */ 
/*    */     
/*    */     private float[] parseUV(JsonObject p_178292_1_) {
/* 74 */       if (!p_178292_1_.has("uv")) {
/* 75 */         return null;
/*    */       }
/* 77 */       JsonArray jsonarray = JsonUtils.getJsonArray(p_178292_1_, "uv");
/*    */       
/* 79 */       if (jsonarray.size() != 4) {
/* 80 */         throw new JsonParseException("Expected 4 uv values, found: " + jsonarray.size());
/*    */       }
/* 82 */       float[] afloat = new float[4];
/*    */       
/* 84 */       for (int i = 0; i < afloat.length; i++) {
/* 85 */         afloat[i] = JsonUtils.getFloat(jsonarray.get(i), "uv[" + i + "]");
/*    */       }
/*    */       
/* 88 */       return afloat;
/*    */     }
/*    */   }
/*    */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\minecraft\client\renderer\block\model\BlockFaceUV.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */