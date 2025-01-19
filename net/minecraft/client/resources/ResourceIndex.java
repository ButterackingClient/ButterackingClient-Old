/*    */ package net.minecraft.client.resources;
/*    */ 
/*    */ import com.google.common.base.Charsets;
/*    */ import com.google.common.collect.Maps;
/*    */ import com.google.common.io.Files;
/*    */ import com.google.gson.JsonElement;
/*    */ import com.google.gson.JsonObject;
/*    */ import com.google.gson.JsonParseException;
/*    */ import com.google.gson.JsonParser;
/*    */ import java.io.BufferedReader;
/*    */ import java.io.File;
/*    */ import java.io.FileNotFoundException;
/*    */ import java.util.Map;
/*    */ import net.minecraft.util.JsonUtils;
/*    */ import org.apache.commons.io.IOUtils;
/*    */ import org.apache.logging.log4j.LogManager;
/*    */ import org.apache.logging.log4j.Logger;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class ResourceIndex
/*    */ {
/* 24 */   private static final Logger logger = LogManager.getLogger();
/* 25 */   private final Map<String, File> resourceMap = Maps.newHashMap();
/*    */   
/*    */   public ResourceIndex(File p_i1047_1_, String p_i1047_2_) {
/* 28 */     if (p_i1047_2_ != null) {
/* 29 */       File file1 = new File(p_i1047_1_, "objects");
/* 30 */       File file2 = new File(p_i1047_1_, "indexes/" + p_i1047_2_ + ".json");
/* 31 */       BufferedReader bufferedreader = null;
/*    */       
/*    */       try {
/* 34 */         bufferedreader = Files.newReader(file2, Charsets.UTF_8);
/* 35 */         JsonObject jsonobject = (new JsonParser()).parse(bufferedreader).getAsJsonObject();
/* 36 */         JsonObject jsonobject1 = JsonUtils.getJsonObject(jsonobject, "objects", null);
/*    */         
/* 38 */         if (jsonobject1 != null) {
/* 39 */           for (Map.Entry<String, JsonElement> entry : (Iterable<Map.Entry<String, JsonElement>>)jsonobject1.entrySet()) {
/* 40 */             JsonObject jsonobject2 = (JsonObject)entry.getValue();
/* 41 */             String s = entry.getKey();
/* 42 */             String[] astring = s.split("/", 2);
/* 43 */             String s1 = (astring.length == 1) ? astring[0] : (String.valueOf(astring[0]) + ":" + astring[1]);
/* 44 */             String s2 = JsonUtils.getString(jsonobject2, "hash");
/* 45 */             File file3 = new File(file1, String.valueOf(s2.substring(0, 2)) + "/" + s2);
/* 46 */             this.resourceMap.put(s1, file3);
/*    */           } 
/*    */         }
/* 49 */       } catch (JsonParseException var20) {
/* 50 */         logger.error("Unable to parse resource index file: " + file2);
/* 51 */       } catch (FileNotFoundException var21) {
/* 52 */         logger.error("Can't find the resource index file: " + file2);
/*    */       } finally {
/* 54 */         IOUtils.closeQuietly(bufferedreader);
/*    */       } 
/*    */     } 
/*    */   }
/*    */   
/*    */   public Map<String, File> getResourceMap() {
/* 60 */     return this.resourceMap;
/*    */   }
/*    */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\minecraft\client\resources\ResourceIndex.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */