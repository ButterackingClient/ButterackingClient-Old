/*    */ package net.minecraft.client.renderer.block.model;
/*    */ 
/*    */ import com.google.gson.JsonDeserializationContext;
/*    */ import com.google.gson.JsonDeserializer;
/*    */ import com.google.gson.JsonElement;
/*    */ import com.google.gson.JsonObject;
/*    */ import com.google.gson.JsonParseException;
/*    */ import java.lang.reflect.Type;
/*    */ import net.minecraft.util.EnumFacing;
/*    */ import net.minecraft.util.JsonUtils;
/*    */ 
/*    */ 
/*    */ public class BlockPartFace
/*    */ {
/* 15 */   public static final EnumFacing FACING_DEFAULT = null;
/*    */   public final EnumFacing cullFace;
/*    */   public final int tintIndex;
/*    */   public final String texture;
/*    */   public final BlockFaceUV blockFaceUV;
/*    */   
/*    */   public BlockPartFace(EnumFacing cullFaceIn, int tintIndexIn, String textureIn, BlockFaceUV blockFaceUVIn) {
/* 22 */     this.cullFace = cullFaceIn;
/* 23 */     this.tintIndex = tintIndexIn;
/* 24 */     this.texture = textureIn;
/* 25 */     this.blockFaceUV = blockFaceUVIn;
/*    */   }
/*    */   
/*    */   static class Deserializer implements JsonDeserializer<BlockPartFace> {
/*    */     public BlockPartFace deserialize(JsonElement p_deserialize_1_, Type p_deserialize_2_, JsonDeserializationContext p_deserialize_3_) throws JsonParseException {
/* 30 */       JsonObject jsonobject = p_deserialize_1_.getAsJsonObject();
/* 31 */       EnumFacing enumfacing = parseCullFace(jsonobject);
/* 32 */       int i = parseTintIndex(jsonobject);
/* 33 */       String s = parseTexture(jsonobject);
/* 34 */       BlockFaceUV blockfaceuv = (BlockFaceUV)p_deserialize_3_.deserialize((JsonElement)jsonobject, BlockFaceUV.class);
/* 35 */       return new BlockPartFace(enumfacing, i, s, blockfaceuv);
/*    */     }
/*    */     
/*    */     protected int parseTintIndex(JsonObject p_178337_1_) {
/* 39 */       return JsonUtils.getInt(p_178337_1_, "tintindex", -1);
/*    */     }
/*    */     
/*    */     private String parseTexture(JsonObject p_178340_1_) {
/* 43 */       return JsonUtils.getString(p_178340_1_, "texture");
/*    */     }
/*    */     
/*    */     private EnumFacing parseCullFace(JsonObject p_178339_1_) {
/* 47 */       String s = JsonUtils.getString(p_178339_1_, "cullface", "");
/* 48 */       return EnumFacing.byName(s);
/*    */     }
/*    */   }
/*    */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\minecraft\client\renderer\block\model\BlockPartFace.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */