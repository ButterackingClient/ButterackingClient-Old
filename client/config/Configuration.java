/*    */ package client.config;
/*    */ 
/*    */ import java.io.File;
/*    */ import java.io.FileWriter;
/*    */ import java.io.IOException;
/*    */ import java.util.HashMap;
/*    */ import java.util.Map;
/*    */ import org.json.JSONObject;
/*    */ 
/*    */ public class Configuration {
/*    */   private File file;
/*    */   
/*    */   public Configuration(File file, Map<String, Object> options) {
/* 14 */     this.file = file;
/* 15 */     this.options = options;
/*    */   }
/*    */   public Map<String, Object> options;
/*    */   public Configuration(File file) {
/* 19 */     this.file = file;
/* 20 */     this.options = new HashMap<>();
/*    */   }
/*    */   
/*    */   public Object get(String key) {
/* 24 */     return this.options.get(key);
/*    */   }
/*    */   
/*    */   public void set(String key, Object value) {
/* 28 */     this.options.put(key, value);
/*    */   }
/*    */   
/*    */   public void save() throws IOException {
/* 32 */     JSONObject jsonObject = new JSONObject(this.options);
/* 33 */     this.file.createNewFile();
/* 34 */     FileWriter fileWriter = new FileWriter(this.file);
/* 35 */     fileWriter.write(jsonObject.toString());
/* 36 */     fileWriter.flush();
/* 37 */     fileWriter.close();
/*    */   }
/*    */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\client\config\Configuration.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */