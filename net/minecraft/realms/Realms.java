/*    */ package net.minecraft.realms;
/*    */ 
/*    */ import com.google.common.util.concurrent.ListenableFuture;
/*    */ import com.mojang.authlib.GameProfile;
/*    */ import com.mojang.util.UUIDTypeAdapter;
/*    */ import java.net.Proxy;
/*    */ import net.minecraft.client.Minecraft;
/*    */ import net.minecraft.client.gui.GuiScreen;
/*    */ import net.minecraft.client.settings.GameSettings;
/*    */ import net.minecraft.util.Session;
/*    */ import net.minecraft.world.WorldSettings;
/*    */ 
/*    */ 
/*    */ public class Realms
/*    */ {
/*    */   public static boolean isTouchScreen() {
/* 17 */     return (Minecraft.getMinecraft()).gameSettings.touchscreen;
/*    */   }
/*    */   
/*    */   public static Proxy getProxy() {
/* 21 */     return Minecraft.getMinecraft().getProxy();
/*    */   }
/*    */   
/*    */   public static String sessionId() {
/* 25 */     Session session = Minecraft.getMinecraft().getSession();
/* 26 */     return (session == null) ? null : session.getSessionID();
/*    */   }
/*    */   
/*    */   public static String userName() {
/* 30 */     Session session = Minecraft.getMinecraft().getSession();
/* 31 */     return (session == null) ? null : session.getUsername();
/*    */   }
/*    */   
/*    */   public static long currentTimeMillis() {
/* 35 */     return Minecraft.getSystemTime();
/*    */   }
/*    */   
/*    */   public static String getSessionId() {
/* 39 */     return Minecraft.getMinecraft().getSession().getSessionID();
/*    */   }
/*    */   
/*    */   public static String getUUID() {
/* 43 */     return Minecraft.getMinecraft().getSession().getPlayerID();
/*    */   }
/*    */   
/*    */   public static String getName() {
/* 47 */     return Minecraft.getMinecraft().getSession().getUsername();
/*    */   }
/*    */   
/*    */   public static String uuidToName(String p_uuidToName_0_) {
/* 51 */     return Minecraft.getMinecraft().getSessionService().fillProfileProperties(new GameProfile(UUIDTypeAdapter.fromString(p_uuidToName_0_), null), false).getName();
/*    */   }
/*    */   
/*    */   public static void setScreen(RealmsScreen p_setScreen_0_) {
/* 55 */     Minecraft.getMinecraft().displayGuiScreen((GuiScreen)p_setScreen_0_.getProxy());
/*    */   }
/*    */   
/*    */   public static String getGameDirectoryPath() {
/* 59 */     return (Minecraft.getMinecraft()).mcDataDir.getAbsolutePath();
/*    */   }
/*    */   
/*    */   public static int survivalId() {
/* 63 */     return WorldSettings.GameType.SURVIVAL.getID();
/*    */   }
/*    */   
/*    */   public static int creativeId() {
/* 67 */     return WorldSettings.GameType.CREATIVE.getID();
/*    */   }
/*    */   
/*    */   public static int adventureId() {
/* 71 */     return WorldSettings.GameType.ADVENTURE.getID();
/*    */   }
/*    */   
/*    */   public static int spectatorId() {
/* 75 */     return WorldSettings.GameType.SPECTATOR.getID();
/*    */   }
/*    */   
/*    */   public static void setConnectedToRealms(boolean p_setConnectedToRealms_0_) {
/* 79 */     Minecraft.getMinecraft().setConnectedToRealms(p_setConnectedToRealms_0_);
/*    */   }
/*    */   
/*    */   public static ListenableFuture<Object> downloadResourcePack(String p_downloadResourcePack_0_, String p_downloadResourcePack_1_) {
/* 83 */     ListenableFuture<Object> listenablefuture = Minecraft.getMinecraft().getResourcePackRepository().downloadResourcePack(p_downloadResourcePack_0_, p_downloadResourcePack_1_);
/* 84 */     return listenablefuture;
/*    */   }
/*    */   
/*    */   public static void clearResourcePack() {
/* 88 */     Minecraft.getMinecraft().getResourcePackRepository().clearResourcePack();
/*    */   }
/*    */   
/*    */   public static boolean getRealmsNotificationsEnabled() {
/* 92 */     return (Minecraft.getMinecraft()).gameSettings.getOptionOrdinalValue(GameSettings.Options.enumFloat);
/*    */   }
/*    */   
/*    */   public static boolean inTitleScreen() {
/* 96 */     return ((Minecraft.getMinecraft()).currentScreen != null && (Minecraft.getMinecraft()).currentScreen instanceof net.minecraft.client.gui.GuiMainMenu);
/*    */   }
/*    */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\minecraft\realms\Realms.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */