/*    */ package net.optifine.player;
/*    */ 
/*    */ import com.google.gson.JsonElement;
/*    */ import com.google.gson.JsonParser;
/*    */ import net.minecraft.src.Config;
/*    */ import net.optifine.http.IFileDownloadListener;
/*    */ 
/*    */ public class PlayerConfigurationReceiver implements IFileDownloadListener {
/*  9 */   private String player = null;
/*    */   
/*    */   public PlayerConfigurationReceiver(String player) {
/* 12 */     this.player = player;
/*    */   }
/*    */   
/*    */   public void fileDownloadFinished(String url, byte[] bytes, Throwable exception) {
/* 16 */     if (bytes != null)
/*    */       try {
/* 18 */         String s = new String(bytes, "ASCII");
/* 19 */         JsonParser jsonparser = new JsonParser();
/* 20 */         JsonElement jsonelement = jsonparser.parse(s);
/* 21 */         PlayerConfigurationParser playerconfigurationparser = new PlayerConfigurationParser(this.player);
/* 22 */         PlayerConfiguration playerconfiguration = playerconfigurationparser.parsePlayerConfiguration(jsonelement);
/*    */         
/* 24 */         if (playerconfiguration != null) {
/* 25 */           playerconfiguration.setInitialized(true);
/* 26 */           PlayerConfigurations.setPlayerConfiguration(this.player, playerconfiguration);
/*    */         } 
/* 28 */       } catch (Exception exception1) {
/* 29 */         Config.dbg("Error parsing configuration: " + url + ", " + exception1.getClass().getName() + ": " + exception1.getMessage());
/*    */       }  
/*    */   }
/*    */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\optifine\player\PlayerConfigurationReceiver.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */