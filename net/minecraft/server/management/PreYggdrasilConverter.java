/*    */ package net.minecraft.server.management;
/*    */ 
/*    */ import com.google.common.base.Predicate;
/*    */ import com.google.common.collect.Iterators;
/*    */ import com.google.common.collect.Lists;
/*    */ import com.mojang.authlib.Agent;
/*    */ import com.mojang.authlib.GameProfile;
/*    */ import com.mojang.authlib.ProfileLookupCallback;
/*    */ import java.io.File;
/*    */ import java.util.Collection;
/*    */ import java.util.Iterator;
/*    */ import java.util.List;
/*    */ import java.util.UUID;
/*    */ import net.minecraft.entity.player.EntityPlayer;
/*    */ import net.minecraft.server.MinecraftServer;
/*    */ import net.minecraft.util.StringUtils;
/*    */ import org.apache.logging.log4j.LogManager;
/*    */ import org.apache.logging.log4j.Logger;
/*    */ 
/*    */ public class PreYggdrasilConverter
/*    */ {
/* 22 */   private static final Logger LOGGER = LogManager.getLogger();
/* 23 */   public static final File OLD_IPBAN_FILE = new File("banned-ips.txt");
/* 24 */   public static final File OLD_PLAYERBAN_FILE = new File("banned-players.txt");
/* 25 */   public static final File OLD_OPS_FILE = new File("ops.txt");
/* 26 */   public static final File OLD_WHITELIST_FILE = new File("white-list.txt");
/*    */   
/*    */   private static void lookupNames(MinecraftServer server, Collection<String> names, ProfileLookupCallback callback) {
/* 29 */     String[] astring = (String[])Iterators.toArray((Iterator)Iterators.filter(names.iterator(), new Predicate<String>() {
/*    */             public boolean apply(String p_apply_1_) {
/* 31 */               return !StringUtils.isNullOrEmpty(p_apply_1_);
/*    */             }
/* 33 */           }), String.class);
/*    */     
/* 35 */     if (server.isServerInOnlineMode()) {
/* 36 */       server.getGameProfileRepository().findProfilesByNames(astring, Agent.MINECRAFT, callback);
/*    */     } else {
/* 38 */       byte b; int i; String[] arrayOfString; for (i = (arrayOfString = astring).length, b = 0; b < i; ) { String s = arrayOfString[b];
/* 39 */         UUID uuid = EntityPlayer.getUUID(new GameProfile(null, s));
/* 40 */         GameProfile gameprofile = new GameProfile(uuid, s);
/* 41 */         callback.onProfileLookupSucceeded(gameprofile);
/*    */         b++; }
/*    */     
/*    */     } 
/*    */   }
/*    */   public static String getStringUUIDFromName(String p_152719_0_) {
/* 47 */     if (!StringUtils.isNullOrEmpty(p_152719_0_) && p_152719_0_.length() <= 16) {
/* 48 */       final MinecraftServer minecraftserver = MinecraftServer.getServer();
/* 49 */       GameProfile gameprofile = minecraftserver.getPlayerProfileCache().getGameProfileForUsername(p_152719_0_);
/*    */       
/* 51 */       if (gameprofile != null && gameprofile.getId() != null)
/* 52 */         return gameprofile.getId().toString(); 
/* 53 */       if (!minecraftserver.isSinglePlayer() && minecraftserver.isServerInOnlineMode()) {
/* 54 */         final List<GameProfile> list = Lists.newArrayList();
/* 55 */         ProfileLookupCallback profilelookupcallback = new ProfileLookupCallback() {
/*    */             public void onProfileLookupSucceeded(GameProfile p_onProfileLookupSucceeded_1_) {
/* 57 */               minecraftserver.getPlayerProfileCache().addEntry(p_onProfileLookupSucceeded_1_);
/* 58 */               list.add(p_onProfileLookupSucceeded_1_);
/*    */             }
/*    */             
/*    */             public void onProfileLookupFailed(GameProfile p_onProfileLookupFailed_1_, Exception p_onProfileLookupFailed_2_) {
/* 62 */               PreYggdrasilConverter.LOGGER.warn("Could not lookup user whitelist entry for " + p_onProfileLookupFailed_1_.getName(), p_onProfileLookupFailed_2_);
/*    */             }
/*    */           };
/* 65 */         lookupNames(minecraftserver, Lists.newArrayList((Object[])new String[] { p_152719_0_ }, ), profilelookupcallback);
/* 66 */         return (list.size() > 0 && ((GameProfile)list.get(0)).getId() != null) ? ((GameProfile)list.get(0)).getId().toString() : "";
/*    */       } 
/* 68 */       return EntityPlayer.getUUID(new GameProfile(null, p_152719_0_)).toString();
/*    */     } 
/*    */     
/* 71 */     return p_152719_0_;
/*    */   }
/*    */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\minecraft\server\management\PreYggdrasilConverter.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */