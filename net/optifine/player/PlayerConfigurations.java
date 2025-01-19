/*    */ package net.optifine.player;
/*    */ 
/*    */ import java.util.HashMap;
/*    */ import java.util.Map;
/*    */ import net.minecraft.client.Minecraft;
/*    */ import net.minecraft.client.entity.AbstractClientPlayer;
/*    */ import net.minecraft.client.entity.EntityPlayerSP;
/*    */ import net.minecraft.client.model.ModelBiped;
/*    */ import net.optifine.http.FileDownloadThread;
/*    */ import net.optifine.http.HttpUtils;
/*    */ 
/*    */ public class PlayerConfigurations {
/* 13 */   private static Map mapConfigurations = null;
/* 14 */   private static boolean reloadPlayerItems = Boolean.getBoolean("player.models.reload");
/* 15 */   private static long timeReloadPlayerItemsMs = System.currentTimeMillis();
/*    */   
/*    */   public static void renderPlayerItems(ModelBiped modelBiped, AbstractClientPlayer player, float scale, float partialTicks) {
/* 18 */     PlayerConfiguration playerconfiguration = getPlayerConfiguration(player);
/*    */     
/* 20 */     if (playerconfiguration != null) {
/* 21 */       playerconfiguration.renderPlayerItems(modelBiped, player, scale, partialTicks);
/*    */     }
/*    */   }
/*    */   
/*    */   public static synchronized PlayerConfiguration getPlayerConfiguration(AbstractClientPlayer player) {
/* 26 */     if (reloadPlayerItems && System.currentTimeMillis() > timeReloadPlayerItemsMs + 5000L) {
/* 27 */       EntityPlayerSP entityPlayerSP = (Minecraft.getMinecraft()).thePlayer;
/*    */       
/* 29 */       if (entityPlayerSP != null) {
/* 30 */         setPlayerConfiguration(entityPlayerSP.getNameClear(), null);
/* 31 */         timeReloadPlayerItemsMs = System.currentTimeMillis();
/*    */       } 
/*    */     } 
/*    */     
/* 35 */     String s1 = player.getNameClear();
/*    */     
/* 37 */     if (s1 == null) {
/* 38 */       return null;
/*    */     }
/* 40 */     PlayerConfiguration playerconfiguration = (PlayerConfiguration)getMapConfigurations().get(s1);
/*    */     
/* 42 */     if (playerconfiguration == null) {
/* 43 */       playerconfiguration = new PlayerConfiguration();
/* 44 */       getMapConfigurations().put(s1, playerconfiguration);
/* 45 */       PlayerConfigurationReceiver playerconfigurationreceiver = new PlayerConfigurationReceiver(s1);
/* 46 */       String s = String.valueOf(HttpUtils.getPlayerItemsUrl()) + "/users/" + s1 + ".cfg";
/* 47 */       FileDownloadThread filedownloadthread = new FileDownloadThread(s, playerconfigurationreceiver);
/* 48 */       filedownloadthread.start();
/*    */     } 
/*    */     
/* 51 */     return playerconfiguration;
/*    */   }
/*    */ 
/*    */   
/*    */   public static synchronized void setPlayerConfiguration(String player, PlayerConfiguration pc) {
/* 56 */     getMapConfigurations().put(player, pc);
/*    */   }
/*    */   
/*    */   private static Map getMapConfigurations() {
/* 60 */     if (mapConfigurations == null) {
/* 61 */       mapConfigurations = new HashMap<>();
/*    */     }
/*    */     
/* 64 */     return mapConfigurations;
/*    */   }
/*    */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\optifine\player\PlayerConfigurations.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */