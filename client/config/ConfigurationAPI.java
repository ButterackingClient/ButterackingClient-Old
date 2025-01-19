/*    */ package client.config;
/*    */ import java.io.File;
/*    */ import java.io.IOException;
/*    */ import org.apache.commons.io.Charsets;
/*    */ import org.apache.commons.io.FileUtils;
/*    */ import org.json.JSONObject;
/*    */ 
/*    */ public class ConfigurationAPI {
/*    */   public static Configuration loadExistingConfiguration(File file) throws IOException {
/* 10 */     JSONObject jsonObject = new JSONObject(FileUtils.readFileToString(file, Charsets.UTF_8));
/* 11 */     return new Configuration(file, jsonObject.toMap());
/*    */   }
/*    */   
/*    */   public static Configuration newConfiguration(File file) {
/* 15 */     return new Configuration(file);
/*    */   }
/*    */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\client\config\ConfigurationAPI.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */