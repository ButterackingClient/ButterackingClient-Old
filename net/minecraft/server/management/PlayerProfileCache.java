/*     */ package net.minecraft.server.management;
/*     */ 
/*     */ import com.google.common.base.Charsets;
/*     */ import com.google.common.collect.Iterators;
/*     */ import com.google.common.collect.Lists;
/*     */ import com.google.common.collect.Maps;
/*     */ import com.google.common.io.Files;
/*     */ import com.google.gson.Gson;
/*     */ import com.google.gson.GsonBuilder;
/*     */ import com.google.gson.JsonDeserializationContext;
/*     */ import com.google.gson.JsonDeserializer;
/*     */ import com.google.gson.JsonElement;
/*     */ import com.google.gson.JsonObject;
/*     */ import com.google.gson.JsonParseException;
/*     */ import com.google.gson.JsonSerializationContext;
/*     */ import com.google.gson.JsonSerializer;
/*     */ import com.mojang.authlib.Agent;
/*     */ import com.mojang.authlib.GameProfile;
/*     */ import com.mojang.authlib.ProfileLookupCallback;
/*     */ import java.io.BufferedReader;
/*     */ import java.io.BufferedWriter;
/*     */ import java.io.File;
/*     */ import java.io.FileNotFoundException;
/*     */ import java.io.IOException;
/*     */ import java.lang.reflect.ParameterizedType;
/*     */ import java.lang.reflect.Type;
/*     */ import java.text.ParseException;
/*     */ import java.text.SimpleDateFormat;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Calendar;
/*     */ import java.util.Date;
/*     */ import java.util.LinkedList;
/*     */ import java.util.List;
/*     */ import java.util.Locale;
/*     */ import java.util.Map;
/*     */ import java.util.UUID;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.server.MinecraftServer;
/*     */ import org.apache.commons.io.IOUtils;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class PlayerProfileCache
/*     */ {
/*  46 */   public static final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss Z");
/*  47 */   private final Map<String, ProfileEntry> usernameToProfileEntryMap = Maps.newHashMap();
/*  48 */   private final Map<UUID, ProfileEntry> uuidToProfileEntryMap = Maps.newHashMap();
/*  49 */   private final LinkedList<GameProfile> gameProfiles = Lists.newLinkedList();
/*     */   
/*     */   private final MinecraftServer mcServer;
/*     */   
/*  53 */   private static final ParameterizedType TYPE = new ParameterizedType() {
/*     */       public Type[] getActualTypeArguments() {
/*  55 */         return new Type[] { PlayerProfileCache.ProfileEntry.class };
/*     */       }
/*     */       
/*     */       public Type getRawType() {
/*  59 */         return List.class;
/*     */       }
/*     */       
/*     */       public Type getOwnerType() {
/*  63 */         return null;
/*     */       }
/*     */     };
/*     */   protected final Gson gson; private final File usercacheFile;
/*     */   public PlayerProfileCache(MinecraftServer server, File cacheFile) {
/*  68 */     this.mcServer = server;
/*  69 */     this.usercacheFile = cacheFile;
/*  70 */     GsonBuilder gsonbuilder = new GsonBuilder();
/*  71 */     gsonbuilder.registerTypeHierarchyAdapter(ProfileEntry.class, new Serializer(null));
/*  72 */     this.gson = gsonbuilder.create();
/*  73 */     load();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static GameProfile getGameProfile(MinecraftServer server, String username) {
/*  83 */     final GameProfile[] agameprofile = new GameProfile[1];
/*  84 */     ProfileLookupCallback profilelookupcallback = new ProfileLookupCallback() {
/*     */         public void onProfileLookupSucceeded(GameProfile p_onProfileLookupSucceeded_1_) {
/*  86 */           agameprofile[0] = p_onProfileLookupSucceeded_1_;
/*     */         }
/*     */         
/*     */         public void onProfileLookupFailed(GameProfile p_onProfileLookupFailed_1_, Exception p_onProfileLookupFailed_2_) {
/*  90 */           agameprofile[0] = null;
/*     */         }
/*     */       };
/*  93 */     server.getGameProfileRepository().findProfilesByNames(new String[] { username }, Agent.MINECRAFT, profilelookupcallback);
/*     */     
/*  95 */     if (!server.isServerInOnlineMode() && agameprofile[0] == null) {
/*  96 */       UUID uuid = EntityPlayer.getUUID(new GameProfile(null, username));
/*  97 */       GameProfile gameprofile = new GameProfile(uuid, username);
/*  98 */       profilelookupcallback.onProfileLookupSucceeded(gameprofile);
/*     */     } 
/*     */     
/* 101 */     return agameprofile[0];
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void addEntry(GameProfile gameProfile) {
/* 108 */     addEntry(gameProfile, null);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void addEntry(GameProfile gameProfile, Date expirationDate) {
/* 115 */     UUID uuid = gameProfile.getId();
/*     */     
/* 117 */     if (expirationDate == null) {
/* 118 */       Calendar calendar = Calendar.getInstance();
/* 119 */       calendar.setTime(new Date());
/* 120 */       calendar.add(2, 1);
/* 121 */       expirationDate = calendar.getTime();
/*     */     } 
/*     */     
/* 124 */     String s = gameProfile.getName().toLowerCase(Locale.ROOT);
/* 125 */     ProfileEntry playerprofilecache$profileentry = new ProfileEntry(gameProfile, expirationDate, null);
/*     */     
/* 127 */     if (this.uuidToProfileEntryMap.containsKey(uuid)) {
/* 128 */       ProfileEntry playerprofilecache$profileentry1 = this.uuidToProfileEntryMap.get(uuid);
/* 129 */       this.usernameToProfileEntryMap.remove(playerprofilecache$profileentry1.getGameProfile().getName().toLowerCase(Locale.ROOT));
/* 130 */       this.gameProfiles.remove(gameProfile);
/*     */     } 
/*     */     
/* 133 */     this.usernameToProfileEntryMap.put(gameProfile.getName().toLowerCase(Locale.ROOT), playerprofilecache$profileentry);
/* 134 */     this.uuidToProfileEntryMap.put(uuid, playerprofilecache$profileentry);
/* 135 */     this.gameProfiles.addFirst(gameProfile);
/* 136 */     save();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public GameProfile getGameProfileForUsername(String username) {
/* 144 */     String s = username.toLowerCase(Locale.ROOT);
/* 145 */     ProfileEntry playerprofilecache$profileentry = this.usernameToProfileEntryMap.get(s);
/*     */     
/* 147 */     if (playerprofilecache$profileentry != null && (new Date()).getTime() >= playerprofilecache$profileentry.expirationDate.getTime()) {
/* 148 */       this.uuidToProfileEntryMap.remove(playerprofilecache$profileentry.getGameProfile().getId());
/* 149 */       this.usernameToProfileEntryMap.remove(playerprofilecache$profileentry.getGameProfile().getName().toLowerCase(Locale.ROOT));
/* 150 */       this.gameProfiles.remove(playerprofilecache$profileentry.getGameProfile());
/* 151 */       playerprofilecache$profileentry = null;
/*     */     } 
/*     */     
/* 154 */     if (playerprofilecache$profileentry != null) {
/* 155 */       GameProfile gameprofile = playerprofilecache$profileentry.getGameProfile();
/* 156 */       this.gameProfiles.remove(gameprofile);
/* 157 */       this.gameProfiles.addFirst(gameprofile);
/*     */     } else {
/* 159 */       GameProfile gameprofile1 = getGameProfile(this.mcServer, s);
/*     */       
/* 161 */       if (gameprofile1 != null) {
/* 162 */         addEntry(gameprofile1);
/* 163 */         playerprofilecache$profileentry = this.usernameToProfileEntryMap.get(s);
/*     */       } 
/*     */     } 
/*     */     
/* 167 */     save();
/* 168 */     return (playerprofilecache$profileentry == null) ? null : playerprofilecache$profileentry.getGameProfile();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String[] getUsernames() {
/* 175 */     List<String> list = Lists.newArrayList(this.usernameToProfileEntryMap.keySet());
/* 176 */     return list.<String>toArray(new String[list.size()]);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public GameProfile getProfileByUUID(UUID uuid) {
/* 183 */     ProfileEntry playerprofilecache$profileentry = this.uuidToProfileEntryMap.get(uuid);
/* 184 */     return (playerprofilecache$profileentry == null) ? null : playerprofilecache$profileentry.getGameProfile();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private ProfileEntry getByUUID(UUID uuid) {
/* 191 */     ProfileEntry playerprofilecache$profileentry = this.uuidToProfileEntryMap.get(uuid);
/*     */     
/* 193 */     if (playerprofilecache$profileentry != null) {
/* 194 */       GameProfile gameprofile = playerprofilecache$profileentry.getGameProfile();
/* 195 */       this.gameProfiles.remove(gameprofile);
/* 196 */       this.gameProfiles.addFirst(gameprofile);
/*     */     } 
/*     */     
/* 199 */     return playerprofilecache$profileentry;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void load() {
/* 206 */     BufferedReader bufferedreader = null;
/*     */     
/*     */     try {
/* 209 */       bufferedreader = Files.newReader(this.usercacheFile, Charsets.UTF_8);
/* 210 */       List<ProfileEntry> list = (List<ProfileEntry>)this.gson.fromJson(bufferedreader, TYPE);
/* 211 */       this.usernameToProfileEntryMap.clear();
/* 212 */       this.uuidToProfileEntryMap.clear();
/* 213 */       this.gameProfiles.clear();
/*     */       
/* 215 */       for (ProfileEntry playerprofilecache$profileentry : Lists.reverse(list)) {
/* 216 */         if (playerprofilecache$profileentry != null) {
/* 217 */           addEntry(playerprofilecache$profileentry.getGameProfile(), playerprofilecache$profileentry.getExpirationDate());
/*     */         }
/*     */       } 
/* 220 */     } catch (FileNotFoundException fileNotFoundException) {
/*     */     
/* 222 */     } catch (JsonParseException jsonParseException) {
/*     */     
/*     */     } finally {
/* 225 */       IOUtils.closeQuietly(bufferedreader);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void save() {
/* 233 */     String s = this.gson.toJson(getEntriesWithLimit(1000));
/* 234 */     BufferedWriter bufferedwriter = null;
/*     */     
/*     */     try {
/* 237 */       bufferedwriter = Files.newWriter(this.usercacheFile, Charsets.UTF_8);
/* 238 */       bufferedwriter.write(s);
/*     */       return;
/* 240 */     } catch (FileNotFoundException fileNotFoundException) {
/*     */     
/* 242 */     } catch (IOException var9) {
/*     */       return;
/*     */     } finally {
/* 245 */       IOUtils.closeQuietly(bufferedwriter);
/*     */     } 
/*     */   }
/*     */   
/*     */   private List<ProfileEntry> getEntriesWithLimit(int limitSize) {
/* 250 */     ArrayList<ProfileEntry> arraylist = Lists.newArrayList();
/*     */     
/* 252 */     for (GameProfile gameprofile : Lists.newArrayList(Iterators.limit(this.gameProfiles.iterator(), limitSize))) {
/* 253 */       ProfileEntry playerprofilecache$profileentry = getByUUID(gameprofile.getId());
/*     */       
/* 255 */       if (playerprofilecache$profileentry != null) {
/* 256 */         arraylist.add(playerprofilecache$profileentry);
/*     */       }
/*     */     } 
/*     */     
/* 260 */     return arraylist;
/*     */   }
/*     */   
/*     */   class ProfileEntry {
/*     */     private final GameProfile gameProfile;
/*     */     private final Date expirationDate;
/*     */     
/*     */     private ProfileEntry(GameProfile gameProfileIn, Date expirationDateIn) {
/* 268 */       this.gameProfile = gameProfileIn;
/* 269 */       this.expirationDate = expirationDateIn;
/*     */     }
/*     */     
/*     */     public GameProfile getGameProfile() {
/* 273 */       return this.gameProfile;
/*     */     }
/*     */     
/*     */     public Date getExpirationDate() {
/* 277 */       return this.expirationDate;
/*     */     }
/*     */   }
/*     */   
/*     */   class Serializer
/*     */     implements JsonDeserializer<ProfileEntry>, JsonSerializer<ProfileEntry> {
/*     */     private Serializer() {}
/*     */     
/*     */     public JsonElement serialize(PlayerProfileCache.ProfileEntry p_serialize_1_, Type p_serialize_2_, JsonSerializationContext p_serialize_3_) {
/* 286 */       JsonObject jsonobject = new JsonObject();
/* 287 */       jsonobject.addProperty("name", p_serialize_1_.getGameProfile().getName());
/* 288 */       UUID uuid = p_serialize_1_.getGameProfile().getId();
/* 289 */       jsonobject.addProperty("uuid", (uuid == null) ? "" : uuid.toString());
/* 290 */       jsonobject.addProperty("expiresOn", PlayerProfileCache.dateFormat.format(p_serialize_1_.getExpirationDate()));
/* 291 */       return (JsonElement)jsonobject;
/*     */     }
/*     */     
/*     */     public PlayerProfileCache.ProfileEntry deserialize(JsonElement p_deserialize_1_, Type p_deserialize_2_, JsonDeserializationContext p_deserialize_3_) throws JsonParseException {
/* 295 */       if (p_deserialize_1_.isJsonObject()) {
/* 296 */         JsonObject jsonobject = p_deserialize_1_.getAsJsonObject();
/* 297 */         JsonElement jsonelement = jsonobject.get("name");
/* 298 */         JsonElement jsonelement1 = jsonobject.get("uuid");
/* 299 */         JsonElement jsonelement2 = jsonobject.get("expiresOn");
/*     */         
/* 301 */         if (jsonelement != null && jsonelement1 != null) {
/* 302 */           String s = jsonelement1.getAsString();
/* 303 */           String s1 = jsonelement.getAsString();
/* 304 */           Date date = null;
/*     */           
/* 306 */           if (jsonelement2 != null) {
/*     */             try {
/* 308 */               date = PlayerProfileCache.dateFormat.parse(jsonelement2.getAsString());
/* 309 */             } catch (ParseException var14) {
/* 310 */               date = null;
/*     */             } 
/*     */           }
/*     */           
/* 314 */           if (s1 != null && s != null) {
/*     */             UUID uuid;
/*     */             
/*     */             try {
/* 318 */               uuid = UUID.fromString(s);
/* 319 */             } catch (Throwable var13) {
/* 320 */               return null;
/*     */             } 
/*     */             
/* 323 */             PlayerProfileCache.this.getClass(); PlayerProfileCache.ProfileEntry playerprofilecache$profileentry = new PlayerProfileCache.ProfileEntry(new GameProfile(uuid, s1), date, null);
/* 324 */             return playerprofilecache$profileentry;
/*     */           } 
/* 326 */           return null;
/*     */         } 
/*     */         
/* 329 */         return null;
/*     */       } 
/*     */       
/* 332 */       return null;
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\minecraft\server\management\PlayerProfileCache.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */