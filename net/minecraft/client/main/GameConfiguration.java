/*    */ package net.minecraft.client.main;
/*    */ 
/*    */ import com.mojang.authlib.properties.PropertyMap;
/*    */ import java.io.File;
/*    */ import java.net.Proxy;
/*    */ import net.minecraft.util.Session;
/*    */ 
/*    */ 
/*    */ public class GameConfiguration
/*    */ {
/*    */   public final UserInformation userInfo;
/*    */   public final DisplayInformation displayInfo;
/*    */   public final FolderInformation folderInfo;
/*    */   public final GameInformation gameInfo;
/*    */   public final ServerInformation serverInfo;
/*    */   
/*    */   public GameConfiguration(UserInformation userInfoIn, DisplayInformation displayInfoIn, FolderInformation folderInfoIn, GameInformation gameInfoIn, ServerInformation serverInfoIn) {
/* 18 */     this.userInfo = userInfoIn;
/* 19 */     this.displayInfo = displayInfoIn;
/* 20 */     this.folderInfo = folderInfoIn;
/* 21 */     this.gameInfo = gameInfoIn;
/* 22 */     this.serverInfo = serverInfoIn;
/*    */   }
/*    */   
/*    */   public static class DisplayInformation {
/*    */     public final int width;
/*    */     public final int height;
/*    */     public final boolean fullscreen;
/*    */     public final boolean checkGlErrors;
/*    */     
/*    */     public DisplayInformation(int widthIn, int heightIn, boolean fullscreenIn, boolean checkGlErrorsIn) {
/* 32 */       this.width = widthIn;
/* 33 */       this.height = heightIn;
/* 34 */       this.fullscreen = fullscreenIn;
/* 35 */       this.checkGlErrors = checkGlErrorsIn;
/*    */     }
/*    */   }
/*    */   
/*    */   public static class FolderInformation {
/*    */     public final File mcDataDir;
/*    */     public final File resourcePacksDir;
/*    */     public final File assetsDir;
/*    */     public final String assetIndex;
/*    */     
/*    */     public FolderInformation(File mcDataDirIn, File resourcePacksDirIn, File assetsDirIn, String assetIndexIn) {
/* 46 */       this.mcDataDir = mcDataDirIn;
/* 47 */       this.resourcePacksDir = resourcePacksDirIn;
/* 48 */       this.assetsDir = assetsDirIn;
/* 49 */       this.assetIndex = assetIndexIn;
/*    */     }
/*    */   }
/*    */   
/*    */   public static class GameInformation {
/*    */     public final boolean isDemo;
/*    */     public final String version;
/*    */     
/*    */     public GameInformation(boolean isDemoIn, String versionIn) {
/* 58 */       this.isDemo = isDemoIn;
/* 59 */       this.version = versionIn;
/*    */     }
/*    */   }
/*    */   
/*    */   public static class ServerInformation {
/*    */     public final String serverName;
/*    */     public final int serverPort;
/*    */     
/*    */     public ServerInformation(String serverNameIn, int serverPortIn) {
/* 68 */       this.serverName = serverNameIn;
/* 69 */       this.serverPort = serverPortIn;
/*    */     }
/*    */   }
/*    */   
/*    */   public static class UserInformation {
/*    */     public final Session session;
/*    */     public final PropertyMap userProperties;
/*    */     public final PropertyMap profileProperties;
/*    */     public final Proxy proxy;
/*    */     
/*    */     public UserInformation(Session sessionIn, PropertyMap userPropertiesIn, PropertyMap profilePropertiesIn, Proxy proxyIn) {
/* 80 */       this.session = sessionIn;
/* 81 */       this.userProperties = userPropertiesIn;
/* 82 */       this.profileProperties = profilePropertiesIn;
/* 83 */       this.proxy = proxyIn;
/*    */     }
/*    */   }
/*    */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\minecraft\client\main\GameConfiguration.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */