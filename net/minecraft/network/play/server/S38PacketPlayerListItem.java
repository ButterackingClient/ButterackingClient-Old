/*     */ package net.minecraft.network.play.server;
/*     */ 
/*     */ import com.google.common.base.Objects;
/*     */ import com.google.common.collect.Lists;
/*     */ import com.mojang.authlib.GameProfile;
/*     */ import com.mojang.authlib.properties.Property;
/*     */ import java.io.IOException;
/*     */ import java.util.List;
/*     */ import net.minecraft.entity.player.EntityPlayerMP;
/*     */ import net.minecraft.network.INetHandler;
/*     */ import net.minecraft.network.Packet;
/*     */ import net.minecraft.network.PacketBuffer;
/*     */ import net.minecraft.network.play.INetHandlerPlayClient;
/*     */ import net.minecraft.util.IChatComponent;
/*     */ import net.minecraft.world.WorldSettings;
/*     */ 
/*     */ public class S38PacketPlayerListItem
/*     */   implements Packet<INetHandlerPlayClient> {
/*     */   private Action action;
/*  20 */   private final List<AddPlayerData> players = Lists.newArrayList();
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public S38PacketPlayerListItem(Action actionIn, EntityPlayerMP... players) {
/*  26 */     this.action = actionIn; byte b; int i;
/*     */     EntityPlayerMP[] arrayOfEntityPlayerMP;
/*  28 */     for (i = (arrayOfEntityPlayerMP = players).length, b = 0; b < i; ) { EntityPlayerMP entityplayermp = arrayOfEntityPlayerMP[b];
/*  29 */       this.players.add(new AddPlayerData(entityplayermp.getGameProfile(), entityplayermp.ping, entityplayermp.theItemInWorldManager.getGameType(), entityplayermp.getTabListDisplayName()));
/*     */       b++; }
/*     */   
/*     */   }
/*     */   public S38PacketPlayerListItem(Action actionIn, Iterable<EntityPlayerMP> players) {
/*  34 */     this.action = actionIn;
/*     */     
/*  36 */     for (EntityPlayerMP entityplayermp : players) {
/*  37 */       this.players.add(new AddPlayerData(entityplayermp.getGameProfile(), entityplayermp.ping, entityplayermp.theItemInWorldManager.getGameType(), entityplayermp.getTabListDisplayName()));
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void readPacketData(PacketBuffer buf) throws IOException {
/*  45 */     this.action = (Action)buf.readEnumValue(Action.class);
/*  46 */     int i = buf.readVarIntFromBuffer();
/*     */     
/*  48 */     for (int j = 0; j < i; j++) {
/*  49 */       int l, i1; GameProfile gameprofile = null;
/*  50 */       int k = 0;
/*  51 */       WorldSettings.GameType worldsettings$gametype = null;
/*  52 */       IChatComponent ichatcomponent = null;
/*     */       
/*  54 */       switch (this.action) {
/*     */         case null:
/*  56 */           gameprofile = new GameProfile(buf.readUuid(), buf.readStringFromBuffer(16));
/*  57 */           l = buf.readVarIntFromBuffer();
/*  58 */           i1 = 0;
/*     */           
/*  60 */           for (; i1 < l; i1++) {
/*  61 */             String s = buf.readStringFromBuffer(32767);
/*  62 */             String s1 = buf.readStringFromBuffer(32767);
/*     */             
/*  64 */             if (buf.readBoolean()) {
/*  65 */               gameprofile.getProperties().put(s, new Property(s, s1, buf.readStringFromBuffer(32767)));
/*     */             } else {
/*  67 */               gameprofile.getProperties().put(s, new Property(s, s1));
/*     */             } 
/*     */           } 
/*     */           
/*  71 */           worldsettings$gametype = WorldSettings.GameType.getByID(buf.readVarIntFromBuffer());
/*  72 */           k = buf.readVarIntFromBuffer();
/*     */           
/*  74 */           if (buf.readBoolean()) {
/*  75 */             ichatcomponent = buf.readChatComponent();
/*     */           }
/*     */           break;
/*     */ 
/*     */         
/*     */         case UPDATE_GAME_MODE:
/*  81 */           gameprofile = new GameProfile(buf.readUuid(), null);
/*  82 */           worldsettings$gametype = WorldSettings.GameType.getByID(buf.readVarIntFromBuffer());
/*     */           break;
/*     */         
/*     */         case UPDATE_LATENCY:
/*  86 */           gameprofile = new GameProfile(buf.readUuid(), null);
/*  87 */           k = buf.readVarIntFromBuffer();
/*     */           break;
/*     */         
/*     */         case UPDATE_DISPLAY_NAME:
/*  91 */           gameprofile = new GameProfile(buf.readUuid(), null);
/*     */           
/*  93 */           if (buf.readBoolean()) {
/*  94 */             ichatcomponent = buf.readChatComponent();
/*     */           }
/*     */           break;
/*     */ 
/*     */         
/*     */         case REMOVE_PLAYER:
/* 100 */           gameprofile = new GameProfile(buf.readUuid(), null);
/*     */           break;
/*     */       } 
/* 103 */       this.players.add(new AddPlayerData(gameprofile, k, worldsettings$gametype, ichatcomponent));
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void writePacketData(PacketBuffer buf) throws IOException {
/* 111 */     buf.writeEnumValue(this.action);
/* 112 */     buf.writeVarIntToBuffer(this.players.size());
/*     */     
/* 114 */     for (AddPlayerData s38packetplayerlistitem$addplayerdata : this.players) {
/* 115 */       switch (this.action) {
/*     */         case null:
/* 117 */           buf.writeUuid(s38packetplayerlistitem$addplayerdata.getProfile().getId());
/* 118 */           buf.writeString(s38packetplayerlistitem$addplayerdata.getProfile().getName());
/* 119 */           buf.writeVarIntToBuffer(s38packetplayerlistitem$addplayerdata.getProfile().getProperties().size());
/*     */           
/* 121 */           for (Property property : s38packetplayerlistitem$addplayerdata.getProfile().getProperties().values()) {
/* 122 */             buf.writeString(property.getName());
/* 123 */             buf.writeString(property.getValue());
/*     */             
/* 125 */             if (property.hasSignature()) {
/* 126 */               buf.writeBoolean(true);
/* 127 */               buf.writeString(property.getSignature()); continue;
/*     */             } 
/* 129 */             buf.writeBoolean(false);
/*     */           } 
/*     */ 
/*     */           
/* 133 */           buf.writeVarIntToBuffer(s38packetplayerlistitem$addplayerdata.getGameMode().getID());
/* 134 */           buf.writeVarIntToBuffer(s38packetplayerlistitem$addplayerdata.getPing());
/*     */           
/* 136 */           if (s38packetplayerlistitem$addplayerdata.getDisplayName() == null) {
/* 137 */             buf.writeBoolean(false); continue;
/*     */           } 
/* 139 */           buf.writeBoolean(true);
/* 140 */           buf.writeChatComponent(s38packetplayerlistitem$addplayerdata.getDisplayName());
/*     */ 
/*     */ 
/*     */ 
/*     */         
/*     */         case UPDATE_GAME_MODE:
/* 146 */           buf.writeUuid(s38packetplayerlistitem$addplayerdata.getProfile().getId());
/* 147 */           buf.writeVarIntToBuffer(s38packetplayerlistitem$addplayerdata.getGameMode().getID());
/*     */ 
/*     */         
/*     */         case UPDATE_LATENCY:
/* 151 */           buf.writeUuid(s38packetplayerlistitem$addplayerdata.getProfile().getId());
/* 152 */           buf.writeVarIntToBuffer(s38packetplayerlistitem$addplayerdata.getPing());
/*     */ 
/*     */         
/*     */         case UPDATE_DISPLAY_NAME:
/* 156 */           buf.writeUuid(s38packetplayerlistitem$addplayerdata.getProfile().getId());
/*     */           
/* 158 */           if (s38packetplayerlistitem$addplayerdata.getDisplayName() == null) {
/* 159 */             buf.writeBoolean(false); continue;
/*     */           } 
/* 161 */           buf.writeBoolean(true);
/* 162 */           buf.writeChatComponent(s38packetplayerlistitem$addplayerdata.getDisplayName());
/*     */ 
/*     */ 
/*     */ 
/*     */         
/*     */         case REMOVE_PLAYER:
/* 168 */           buf.writeUuid(s38packetplayerlistitem$addplayerdata.getProfile().getId());
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void processPacket(INetHandlerPlayClient handler) {
/* 177 */     handler.handlePlayerListItem(this);
/*     */   }
/*     */   
/*     */   public List<AddPlayerData> getEntries() {
/* 181 */     return this.players;
/*     */   }
/*     */   
/*     */   public Action getAction() {
/* 185 */     return this.action;
/*     */   }
/*     */   
/*     */   public String toString() {
/* 189 */     return Objects.toStringHelper(this).add("action", this.action).add("entries", this.players).toString();
/*     */   }
/*     */   public S38PacketPlayerListItem() {}
/*     */   
/* 193 */   public enum Action { ADD_PLAYER,
/* 194 */     UPDATE_GAME_MODE,
/* 195 */     UPDATE_LATENCY,
/* 196 */     UPDATE_DISPLAY_NAME,
/* 197 */     REMOVE_PLAYER; }
/*     */ 
/*     */   
/*     */   public class AddPlayerData {
/*     */     private final int ping;
/*     */     private final WorldSettings.GameType gamemode;
/*     */     private final GameProfile profile;
/*     */     private final IChatComponent displayName;
/*     */     
/*     */     public AddPlayerData(GameProfile profile, int pingIn, WorldSettings.GameType gamemodeIn, IChatComponent displayNameIn) {
/* 207 */       this.profile = profile;
/* 208 */       this.ping = pingIn;
/* 209 */       this.gamemode = gamemodeIn;
/* 210 */       this.displayName = displayNameIn;
/*     */     }
/*     */     
/*     */     public GameProfile getProfile() {
/* 214 */       return this.profile;
/*     */     }
/*     */     
/*     */     public int getPing() {
/* 218 */       return this.ping;
/*     */     }
/*     */     
/*     */     public WorldSettings.GameType getGameMode() {
/* 222 */       return this.gamemode;
/*     */     }
/*     */     
/*     */     public IChatComponent getDisplayName() {
/* 226 */       return this.displayName;
/*     */     }
/*     */     
/*     */     public String toString() {
/* 230 */       return Objects.toStringHelper(this).add("latency", this.ping).add("gameMode", this.gamemode).add("profile", this.profile).add("displayName", (this.displayName == null) ? null : IChatComponent.Serializer.componentToJson(this.displayName)).toString();
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\minecraft\network\play\server\S38PacketPlayerListItem.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */