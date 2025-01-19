/*    */ package net.minecraft.util;
/*    */ 
/*    */ import com.google.common.collect.Maps;
/*    */ import com.mojang.authlib.GameProfile;
/*    */ import com.mojang.util.UUIDTypeAdapter;
/*    */ import java.util.Map;
/*    */ import java.util.UUID;
/*    */ 
/*    */ public class Session
/*    */ {
/*    */   private final String username;
/*    */   private final String playerID;
/*    */   private final String token;
/*    */   private final Type sessionType;
/*    */   
/*    */   public Session(String usernameIn, String playerIDIn, String tokenIn, String sessionTypeIn) {
/* 17 */     this.username = usernameIn;
/* 18 */     this.playerID = playerIDIn;
/* 19 */     this.token = tokenIn;
/* 20 */     this.sessionType = Type.setSessionType(sessionTypeIn);
/*    */   }
/*    */   
/*    */   public String getSessionID() {
/* 24 */     return "token:" + this.token + ":" + this.playerID;
/*    */   }
/*    */   
/*    */   public String getPlayerID() {
/* 28 */     return this.playerID;
/*    */   }
/*    */   
/*    */   public String getUsername() {
/* 32 */     return this.username;
/*    */   }
/*    */   
/*    */   public String getToken() {
/* 36 */     return this.token;
/*    */   }
/*    */   
/*    */   public GameProfile getProfile() {
/*    */     try {
/* 41 */       UUID uuid = UUIDTypeAdapter.fromString(getPlayerID());
/* 42 */       return new GameProfile(uuid, getUsername());
/* 43 */     } catch (IllegalArgumentException var2) {
/* 44 */       return new GameProfile(null, getUsername());
/*    */     } 
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public Type getSessionType() {
/* 52 */     return this.sessionType;
/*    */   }
/*    */   
/*    */   public enum Type {
/* 56 */     LEGACY("legacy"),
/* 57 */     MOJANG("mojang");
/*    */     
/* 59 */     private static final Map<String, Type> SESSION_TYPES = Maps.newHashMap();
/*    */ 
/*    */ 
/*    */     
/*    */     private final String sessionType;
/*    */ 
/*    */ 
/*    */     
/*    */     static {
/*    */       byte b;
/*    */       int i;
/*    */       Type[] arrayOfType;
/* 71 */       for (i = (arrayOfType = values()).length, b = 0; b < i; ) { Type session$type = arrayOfType[b];
/* 72 */         SESSION_TYPES.put(session$type.sessionType, session$type);
/*    */         b++; }
/*    */     
/*    */     }
/*    */     
/*    */     Type(String sessionTypeIn) {
/*    */       this.sessionType = sessionTypeIn;
/*    */     }
/*    */     
/*    */     public static Type setSessionType(String sessionTypeIn) {
/*    */       return SESSION_TYPES.get(sessionTypeIn.toLowerCase());
/*    */     }
/*    */   }
/*    */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\minecraf\\util\Session.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */