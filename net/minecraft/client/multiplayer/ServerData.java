/*     */ package net.minecraft.client.multiplayer;
/*     */ 
/*     */ import net.minecraft.nbt.NBTTagCompound;
/*     */ import net.minecraft.util.ChatComponentTranslation;
/*     */ import net.minecraft.util.IChatComponent;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class ServerData
/*     */ {
/*     */   public String serverName;
/*     */   public String serverIP;
/*     */   public String populationInfo;
/*     */   public String serverMOTD;
/*     */   public long pingToServer;
/*  27 */   public int version = 47;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  32 */   public String gameVersion = "1.8.9";
/*     */   public boolean field_78841_f;
/*     */   public String playerList;
/*  35 */   private ServerResourceMode resourceMode = ServerResourceMode.PROMPT;
/*     */ 
/*     */   
/*     */   private String serverIcon;
/*     */   
/*     */   private boolean lanServer;
/*     */ 
/*     */   
/*     */   public ServerData(String name, String ip, boolean isLan) {
/*  44 */     this.serverName = name;
/*  45 */     this.serverIP = ip;
/*  46 */     this.lanServer = isLan;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public NBTTagCompound getNBTCompound() {
/*  53 */     NBTTagCompound nbttagcompound = new NBTTagCompound();
/*  54 */     nbttagcompound.setString("name", this.serverName);
/*  55 */     nbttagcompound.setString("ip", this.serverIP);
/*     */     
/*  57 */     if (this.serverIcon != null) {
/*  58 */       nbttagcompound.setString("icon", this.serverIcon);
/*     */     }
/*     */     
/*  61 */     if (this.resourceMode == ServerResourceMode.ENABLED) {
/*  62 */       nbttagcompound.setBoolean("acceptTextures", true);
/*  63 */     } else if (this.resourceMode == ServerResourceMode.DISABLED) {
/*  64 */       nbttagcompound.setBoolean("acceptTextures", false);
/*     */     } 
/*     */     
/*  67 */     return nbttagcompound;
/*     */   }
/*     */   
/*     */   public ServerResourceMode getResourceMode() {
/*  71 */     return this.resourceMode;
/*     */   }
/*     */   
/*     */   public void setResourceMode(ServerResourceMode mode) {
/*  75 */     this.resourceMode = mode;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static ServerData getServerDataFromNBTCompound(NBTTagCompound nbtCompound) {
/*  82 */     ServerData serverdata = new ServerData(nbtCompound.getString("name"), nbtCompound.getString("ip"), false);
/*     */     
/*  84 */     if (nbtCompound.hasKey("icon", 8)) {
/*  85 */       serverdata.setBase64EncodedIconData(nbtCompound.getString("icon"));
/*     */     }
/*     */     
/*  88 */     if (nbtCompound.hasKey("acceptTextures", 1)) {
/*  89 */       if (nbtCompound.getBoolean("acceptTextures")) {
/*  90 */         serverdata.setResourceMode(ServerResourceMode.ENABLED);
/*     */       } else {
/*  92 */         serverdata.setResourceMode(ServerResourceMode.DISABLED);
/*     */       } 
/*     */     } else {
/*  95 */       serverdata.setResourceMode(ServerResourceMode.PROMPT);
/*     */     } 
/*     */     
/*  98 */     return serverdata;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getBase64EncodedIconData() {
/* 105 */     return this.serverIcon;
/*     */   }
/*     */   
/*     */   public void setBase64EncodedIconData(String icon) {
/* 109 */     this.serverIcon = icon;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isOnLAN() {
/* 116 */     return this.lanServer;
/*     */   }
/*     */   
/*     */   public void copyFrom(ServerData serverDataIn) {
/* 120 */     this.serverIP = serverDataIn.serverIP;
/* 121 */     this.serverName = serverDataIn.serverName;
/* 122 */     setResourceMode(serverDataIn.getResourceMode());
/* 123 */     this.serverIcon = serverDataIn.serverIcon;
/* 124 */     this.lanServer = serverDataIn.lanServer;
/*     */   }
/*     */   
/*     */   public enum ServerResourceMode {
/* 128 */     ENABLED("enabled"),
/* 129 */     DISABLED("disabled"),
/* 130 */     PROMPT("prompt");
/*     */     
/*     */     private final IChatComponent motd;
/*     */     
/*     */     ServerResourceMode(String name) {
/* 135 */       this.motd = (IChatComponent)new ChatComponentTranslation("addServer.resourcePack." + name, new Object[0]);
/*     */     }
/*     */     
/*     */     public IChatComponent getMotd() {
/* 139 */       return this.motd;
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\minecraft\client\multiplayer\ServerData.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */