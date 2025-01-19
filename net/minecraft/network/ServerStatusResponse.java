/*     */ package net.minecraft.network;
/*     */ 
/*     */ import com.google.gson.JsonArray;
/*     */ import com.google.gson.JsonDeserializationContext;
/*     */ import com.google.gson.JsonDeserializer;
/*     */ import com.google.gson.JsonElement;
/*     */ import com.google.gson.JsonObject;
/*     */ import com.google.gson.JsonParseException;
/*     */ import com.google.gson.JsonSerializationContext;
/*     */ import com.google.gson.JsonSerializer;
/*     */ import com.mojang.authlib.GameProfile;
/*     */ import java.lang.reflect.Type;
/*     */ import java.util.UUID;
/*     */ import net.minecraft.util.IChatComponent;
/*     */ import net.minecraft.util.JsonUtils;
/*     */ 
/*     */ 
/*     */ public class ServerStatusResponse
/*     */ {
/*     */   private IChatComponent serverMotd;
/*     */   private PlayerCountData playerCount;
/*     */   private MinecraftProtocolVersionIdentifier protocolVersion;
/*     */   private String favicon;
/*     */   
/*     */   public IChatComponent getServerDescription() {
/*  26 */     return this.serverMotd;
/*     */   }
/*     */   
/*     */   public void setServerDescription(IChatComponent motd) {
/*  30 */     this.serverMotd = motd;
/*     */   }
/*     */   
/*     */   public PlayerCountData getPlayerCountData() {
/*  34 */     return this.playerCount;
/*     */   }
/*     */   
/*     */   public void setPlayerCountData(PlayerCountData countData) {
/*  38 */     this.playerCount = countData;
/*     */   }
/*     */   
/*     */   public MinecraftProtocolVersionIdentifier getProtocolVersionInfo() {
/*  42 */     return this.protocolVersion;
/*     */   }
/*     */   
/*     */   public void setProtocolVersionInfo(MinecraftProtocolVersionIdentifier protocolVersionData) {
/*  46 */     this.protocolVersion = protocolVersionData;
/*     */   }
/*     */   
/*     */   public void setFavicon(String faviconBlob) {
/*  50 */     this.favicon = faviconBlob;
/*     */   }
/*     */   
/*     */   public String getFavicon() {
/*  54 */     return this.favicon;
/*     */   }
/*     */   
/*     */   public static class MinecraftProtocolVersionIdentifier {
/*     */     private final String name;
/*     */     private final int protocol;
/*     */     
/*     */     public MinecraftProtocolVersionIdentifier(String nameIn, int protocolIn) {
/*  62 */       this.name = nameIn;
/*  63 */       this.protocol = protocolIn;
/*     */     }
/*     */     
/*     */     public String getName() {
/*  67 */       return this.name;
/*     */     }
/*     */     
/*     */     public int getProtocol() {
/*  71 */       return this.protocol;
/*     */     }
/*     */     
/*     */     public static class Serializer implements JsonDeserializer<MinecraftProtocolVersionIdentifier>, JsonSerializer<MinecraftProtocolVersionIdentifier> {
/*     */       public ServerStatusResponse.MinecraftProtocolVersionIdentifier deserialize(JsonElement p_deserialize_1_, Type p_deserialize_2_, JsonDeserializationContext p_deserialize_3_) throws JsonParseException {
/*  76 */         JsonObject jsonobject = JsonUtils.getJsonObject(p_deserialize_1_, "version");
/*  77 */         return new ServerStatusResponse.MinecraftProtocolVersionIdentifier(JsonUtils.getString(jsonobject, "name"), JsonUtils.getInt(jsonobject, "protocol"));
/*     */       }
/*     */       
/*     */       public JsonElement serialize(ServerStatusResponse.MinecraftProtocolVersionIdentifier p_serialize_1_, Type p_serialize_2_, JsonSerializationContext p_serialize_3_) {
/*  81 */         JsonObject jsonobject = new JsonObject();
/*  82 */         jsonobject.addProperty("name", p_serialize_1_.getName());
/*  83 */         jsonobject.addProperty("protocol", Integer.valueOf(p_serialize_1_.getProtocol()));
/*  84 */         return (JsonElement)jsonobject;
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */   public static class PlayerCountData {
/*     */     private final int maxPlayers;
/*     */     private final int onlinePlayerCount;
/*     */     private GameProfile[] players;
/*     */     
/*     */     public PlayerCountData(int maxOnlinePlayers, int onlinePlayers) {
/*  95 */       this.maxPlayers = maxOnlinePlayers;
/*  96 */       this.onlinePlayerCount = onlinePlayers;
/*     */     }
/*     */     
/*     */     public int getMaxPlayers() {
/* 100 */       return this.maxPlayers;
/*     */     }
/*     */     
/*     */     public int getOnlinePlayerCount() {
/* 104 */       return this.onlinePlayerCount;
/*     */     }
/*     */     
/*     */     public GameProfile[] getPlayers() {
/* 108 */       return this.players;
/*     */     }
/*     */     
/*     */     public void setPlayers(GameProfile[] playersIn) {
/* 112 */       this.players = playersIn;
/*     */     }
/*     */     
/*     */     public static class Serializer implements JsonDeserializer<PlayerCountData>, JsonSerializer<PlayerCountData> {
/*     */       public ServerStatusResponse.PlayerCountData deserialize(JsonElement p_deserialize_1_, Type p_deserialize_2_, JsonDeserializationContext p_deserialize_3_) throws JsonParseException {
/* 117 */         JsonObject jsonobject = JsonUtils.getJsonObject(p_deserialize_1_, "players");
/* 118 */         ServerStatusResponse.PlayerCountData serverstatusresponse$playercountdata = new ServerStatusResponse.PlayerCountData(JsonUtils.getInt(jsonobject, "max"), JsonUtils.getInt(jsonobject, "online"));
/*     */         
/* 120 */         if (JsonUtils.isJsonArray(jsonobject, "sample")) {
/* 121 */           JsonArray jsonarray = JsonUtils.getJsonArray(jsonobject, "sample");
/*     */           
/* 123 */           if (jsonarray.size() > 0) {
/* 124 */             GameProfile[] agameprofile = new GameProfile[jsonarray.size()];
/*     */             
/* 126 */             for (int i = 0; i < agameprofile.length; i++) {
/* 127 */               JsonObject jsonobject1 = JsonUtils.getJsonObject(jsonarray.get(i), "player[" + i + "]");
/* 128 */               String s = JsonUtils.getString(jsonobject1, "id");
/* 129 */               agameprofile[i] = new GameProfile(UUID.fromString(s), JsonUtils.getString(jsonobject1, "name"));
/*     */             } 
/*     */             
/* 132 */             serverstatusresponse$playercountdata.setPlayers(agameprofile);
/*     */           } 
/*     */         } 
/*     */         
/* 136 */         return serverstatusresponse$playercountdata;
/*     */       }
/*     */       
/*     */       public JsonElement serialize(ServerStatusResponse.PlayerCountData p_serialize_1_, Type p_serialize_2_, JsonSerializationContext p_serialize_3_) {
/* 140 */         JsonObject jsonobject = new JsonObject();
/* 141 */         jsonobject.addProperty("max", Integer.valueOf(p_serialize_1_.getMaxPlayers()));
/* 142 */         jsonobject.addProperty("online", Integer.valueOf(p_serialize_1_.getOnlinePlayerCount()));
/*     */         
/* 144 */         if (p_serialize_1_.getPlayers() != null && (p_serialize_1_.getPlayers()).length > 0) {
/* 145 */           JsonArray jsonarray = new JsonArray();
/*     */           
/* 147 */           for (int i = 0; i < (p_serialize_1_.getPlayers()).length; i++) {
/* 148 */             JsonObject jsonobject1 = new JsonObject();
/* 149 */             UUID uuid = p_serialize_1_.getPlayers()[i].getId();
/* 150 */             jsonobject1.addProperty("id", (uuid == null) ? "" : uuid.toString());
/* 151 */             jsonobject1.addProperty("name", p_serialize_1_.getPlayers()[i].getName());
/* 152 */             jsonarray.add((JsonElement)jsonobject1);
/*     */           } 
/*     */           
/* 155 */           jsonobject.add("sample", (JsonElement)jsonarray);
/*     */         } 
/*     */         
/* 158 */         return (JsonElement)jsonobject;
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */   public static class Serializer implements JsonDeserializer<ServerStatusResponse>, JsonSerializer<ServerStatusResponse> {
/*     */     public ServerStatusResponse deserialize(JsonElement p_deserialize_1_, Type p_deserialize_2_, JsonDeserializationContext p_deserialize_3_) throws JsonParseException {
/* 165 */       JsonObject jsonobject = JsonUtils.getJsonObject(p_deserialize_1_, "status");
/* 166 */       ServerStatusResponse serverstatusresponse = new ServerStatusResponse();
/*     */       
/* 168 */       if (jsonobject.has("description")) {
/* 169 */         serverstatusresponse.setServerDescription((IChatComponent)p_deserialize_3_.deserialize(jsonobject.get("description"), IChatComponent.class));
/*     */       }
/*     */       
/* 172 */       if (jsonobject.has("players")) {
/* 173 */         serverstatusresponse.setPlayerCountData((ServerStatusResponse.PlayerCountData)p_deserialize_3_.deserialize(jsonobject.get("players"), ServerStatusResponse.PlayerCountData.class));
/*     */       }
/*     */       
/* 176 */       if (jsonobject.has("version")) {
/* 177 */         serverstatusresponse.setProtocolVersionInfo((ServerStatusResponse.MinecraftProtocolVersionIdentifier)p_deserialize_3_.deserialize(jsonobject.get("version"), ServerStatusResponse.MinecraftProtocolVersionIdentifier.class));
/*     */       }
/*     */       
/* 180 */       if (jsonobject.has("favicon")) {
/* 181 */         serverstatusresponse.setFavicon(JsonUtils.getString(jsonobject, "favicon"));
/*     */       }
/*     */       
/* 184 */       return serverstatusresponse;
/*     */     }
/*     */     
/*     */     public JsonElement serialize(ServerStatusResponse p_serialize_1_, Type p_serialize_2_, JsonSerializationContext p_serialize_3_) {
/* 188 */       JsonObject jsonobject = new JsonObject();
/*     */       
/* 190 */       if (p_serialize_1_.getServerDescription() != null) {
/* 191 */         jsonobject.add("description", p_serialize_3_.serialize(p_serialize_1_.getServerDescription()));
/*     */       }
/*     */       
/* 194 */       if (p_serialize_1_.getPlayerCountData() != null) {
/* 195 */         jsonobject.add("players", p_serialize_3_.serialize(p_serialize_1_.getPlayerCountData()));
/*     */       }
/*     */       
/* 198 */       if (p_serialize_1_.getProtocolVersionInfo() != null) {
/* 199 */         jsonobject.add("version", p_serialize_3_.serialize(p_serialize_1_.getProtocolVersionInfo()));
/*     */       }
/*     */       
/* 202 */       if (p_serialize_1_.getFavicon() != null) {
/* 203 */         jsonobject.addProperty("favicon", p_serialize_1_.getFavicon());
/*     */       }
/*     */       
/* 206 */       return (JsonElement)jsonobject;
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\minecraft\network\ServerStatusResponse.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */