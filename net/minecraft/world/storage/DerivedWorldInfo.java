/*     */ package net.minecraft.world.storage;
/*     */ 
/*     */ import net.minecraft.nbt.NBTTagCompound;
/*     */ import net.minecraft.util.BlockPos;
/*     */ import net.minecraft.world.EnumDifficulty;
/*     */ import net.minecraft.world.GameRules;
/*     */ import net.minecraft.world.WorldSettings;
/*     */ import net.minecraft.world.WorldType;
/*     */ 
/*     */ 
/*     */ public class DerivedWorldInfo
/*     */   extends WorldInfo
/*     */ {
/*     */   private final WorldInfo theWorldInfo;
/*     */   
/*     */   public DerivedWorldInfo(WorldInfo p_i2145_1_) {
/*  17 */     this.theWorldInfo = p_i2145_1_;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public NBTTagCompound getNBTTagCompound() {
/*  24 */     return this.theWorldInfo.getNBTTagCompound();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public NBTTagCompound cloneNBTCompound(NBTTagCompound nbt) {
/*  31 */     return this.theWorldInfo.cloneNBTCompound(nbt);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public long getSeed() {
/*  38 */     return this.theWorldInfo.getSeed();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getSpawnX() {
/*  45 */     return this.theWorldInfo.getSpawnX();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getSpawnY() {
/*  52 */     return this.theWorldInfo.getSpawnY();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getSpawnZ() {
/*  59 */     return this.theWorldInfo.getSpawnZ();
/*     */   }
/*     */   
/*     */   public long getWorldTotalTime() {
/*  63 */     return this.theWorldInfo.getWorldTotalTime();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public long getWorldTime() {
/*  70 */     return this.theWorldInfo.getWorldTime();
/*     */   }
/*     */   
/*     */   public long getSizeOnDisk() {
/*  74 */     return this.theWorldInfo.getSizeOnDisk();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public NBTTagCompound getPlayerNBTTagCompound() {
/*  81 */     return this.theWorldInfo.getPlayerNBTTagCompound();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getWorldName() {
/*  88 */     return this.theWorldInfo.getWorldName();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getSaveVersion() {
/*  95 */     return this.theWorldInfo.getSaveVersion();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public long getLastTimePlayed() {
/* 102 */     return this.theWorldInfo.getLastTimePlayed();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isThundering() {
/* 109 */     return this.theWorldInfo.isThundering();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getThunderTime() {
/* 116 */     return this.theWorldInfo.getThunderTime();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isRaining() {
/* 123 */     return this.theWorldInfo.isRaining();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getRainTime() {
/* 130 */     return this.theWorldInfo.getRainTime();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public WorldSettings.GameType getGameType() {
/* 137 */     return this.theWorldInfo.getGameType();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setSpawnX(int x) {}
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setSpawnY(int y) {}
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setSpawnZ(int z) {}
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setWorldTotalTime(long time) {}
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setWorldTime(long time) {}
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setSpawn(BlockPos spawnPoint) {}
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setWorldName(String worldName) {}
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setSaveVersion(int version) {}
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setThundering(boolean thunderingIn) {}
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setThunderTime(int time) {}
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setRaining(boolean isRaining) {}
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setRainTime(int time) {}
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isMapFeaturesEnabled() {
/* 207 */     return this.theWorldInfo.isMapFeaturesEnabled();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isHardcoreModeEnabled() {
/* 214 */     return this.theWorldInfo.isHardcoreModeEnabled();
/*     */   }
/*     */   
/*     */   public WorldType getTerrainType() {
/* 218 */     return this.theWorldInfo.getTerrainType();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void setTerrainType(WorldType type) {}
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean areCommandsAllowed() {
/* 228 */     return this.theWorldInfo.areCommandsAllowed();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void setAllowCommands(boolean allow) {}
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isInitialized() {
/* 238 */     return this.theWorldInfo.isInitialized();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setServerInitialized(boolean initializedIn) {}
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public GameRules getGameRulesInstance() {
/* 251 */     return this.theWorldInfo.getGameRulesInstance();
/*     */   }
/*     */   
/*     */   public EnumDifficulty getDifficulty() {
/* 255 */     return this.theWorldInfo.getDifficulty();
/*     */   }
/*     */ 
/*     */   
/*     */   public void setDifficulty(EnumDifficulty newDifficulty) {}
/*     */   
/*     */   public boolean isDifficultyLocked() {
/* 262 */     return this.theWorldInfo.isDifficultyLocked();
/*     */   }
/*     */   
/*     */   public void setDifficultyLocked(boolean locked) {}
/*     */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\minecraft\world\storage\DerivedWorldInfo.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */