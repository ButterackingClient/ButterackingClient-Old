/*     */ package net.minecraft.world;
/*     */ 
/*     */ import net.minecraft.entity.player.PlayerCapabilities;
/*     */ import net.minecraft.world.storage.WorldInfo;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public final class WorldSettings
/*     */ {
/*     */   private final long seed;
/*     */   private final GameType theGameType;
/*     */   private final boolean mapFeaturesEnabled;
/*     */   private final boolean hardcoreEnabled;
/*     */   private final WorldType terrainType;
/*     */   private boolean commandsAllowed;
/*     */   private boolean bonusChestEnabled;
/*     */   private String worldName;
/*     */   
/*     */   public WorldSettings(long seedIn, GameType gameType, boolean enableMapFeatures, boolean hardcoreMode, WorldType worldTypeIn) {
/*  40 */     this.worldName = "";
/*  41 */     this.seed = seedIn;
/*  42 */     this.theGameType = gameType;
/*  43 */     this.mapFeaturesEnabled = enableMapFeatures;
/*  44 */     this.hardcoreEnabled = hardcoreMode;
/*  45 */     this.terrainType = worldTypeIn;
/*     */   }
/*     */   
/*     */   public WorldSettings(WorldInfo info) {
/*  49 */     this(info.getSeed(), info.getGameType(), info.isMapFeaturesEnabled(), info.isHardcoreModeEnabled(), info.getTerrainType());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public WorldSettings enableBonusChest() {
/*  56 */     this.bonusChestEnabled = true;
/*  57 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public WorldSettings enableCommands() {
/*  64 */     this.commandsAllowed = true;
/*  65 */     return this;
/*     */   }
/*     */   
/*     */   public WorldSettings setWorldName(String name) {
/*  69 */     this.worldName = name;
/*  70 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isBonusChestEnabled() {
/*  77 */     return this.bonusChestEnabled;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public long getSeed() {
/*  84 */     return this.seed;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public GameType getGameType() {
/*  91 */     return this.theGameType;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean getHardcoreEnabled() {
/*  98 */     return this.hardcoreEnabled;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isMapFeaturesEnabled() {
/* 105 */     return this.mapFeaturesEnabled;
/*     */   }
/*     */   
/*     */   public WorldType getTerrainType() {
/* 109 */     return this.terrainType;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean areCommandsAllowed() {
/* 116 */     return this.commandsAllowed;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static GameType getGameTypeById(int id) {
/* 123 */     return GameType.getByID(id);
/*     */   }
/*     */   
/*     */   public String getWorldName() {
/* 127 */     return this.worldName;
/*     */   }
/*     */   
/*     */   public enum GameType {
/* 131 */     NOT_SET(-1, ""),
/* 132 */     SURVIVAL(0, "survival"),
/* 133 */     CREATIVE(1, "creative"),
/* 134 */     ADVENTURE(2, "adventure"),
/* 135 */     SPECTATOR(3, "spectator");
/*     */     
/*     */     int id;
/*     */     String name;
/*     */     
/*     */     GameType(int typeId, String nameIn) {
/* 141 */       this.id = typeId;
/* 142 */       this.name = nameIn;
/*     */     }
/*     */     
/*     */     public int getID() {
/* 146 */       return this.id;
/*     */     }
/*     */     
/*     */     public String getName() {
/* 150 */       return this.name;
/*     */     }
/*     */     
/*     */     public void configurePlayerCapabilities(PlayerCapabilities capabilities) {
/* 154 */       if (this == CREATIVE) {
/* 155 */         capabilities.allowFlying = true;
/* 156 */         capabilities.isCreativeMode = true;
/* 157 */         capabilities.disableDamage = true;
/* 158 */       } else if (this == SPECTATOR) {
/* 159 */         capabilities.allowFlying = true;
/* 160 */         capabilities.isCreativeMode = false;
/* 161 */         capabilities.disableDamage = true;
/* 162 */         capabilities.isFlying = true;
/*     */       } else {
/* 164 */         capabilities.allowFlying = false;
/* 165 */         capabilities.isCreativeMode = false;
/* 166 */         capabilities.disableDamage = false;
/* 167 */         capabilities.isFlying = false;
/*     */       } 
/*     */       
/* 170 */       capabilities.allowEdit = !isAdventure();
/*     */     }
/*     */     
/*     */     public boolean isAdventure() {
/* 174 */       return !(this != ADVENTURE && this != SPECTATOR);
/*     */     }
/*     */     
/*     */     public boolean isCreative() {
/* 178 */       return (this == CREATIVE);
/*     */     }
/*     */     
/*     */     public boolean isSurvivalOrAdventure() {
/* 182 */       return !(this != SURVIVAL && this != ADVENTURE);
/*     */     } public static GameType getByID(int idIn) { byte b;
/*     */       int i;
/*     */       GameType[] arrayOfGameType;
/* 186 */       for (i = (arrayOfGameType = values()).length, b = 0; b < i; ) { GameType worldsettings$gametype = arrayOfGameType[b];
/* 187 */         if (worldsettings$gametype.id == idIn) {
/* 188 */           return worldsettings$gametype;
/*     */         }
/*     */         b++; }
/*     */       
/* 192 */       return SURVIVAL; } public static GameType getByName(String gamemodeName) {
/*     */       byte b;
/*     */       int i;
/*     */       GameType[] arrayOfGameType;
/* 196 */       for (i = (arrayOfGameType = values()).length, b = 0; b < i; ) { GameType worldsettings$gametype = arrayOfGameType[b];
/* 197 */         if (worldsettings$gametype.name.equals(gamemodeName)) {
/* 198 */           return worldsettings$gametype;
/*     */         }
/*     */         b++; }
/*     */       
/* 202 */       return SURVIVAL;
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\minecraft\world\WorldSettings.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */