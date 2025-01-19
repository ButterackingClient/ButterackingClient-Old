/*     */ package net.minecraft.world;
/*     */ 
/*     */ 
/*     */ 
/*     */ public class WorldType
/*     */ {
/*   7 */   public static final WorldType[] worldTypes = new WorldType[16];
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  12 */   public static final WorldType DEFAULT = (new WorldType(0, "default", 1)).setVersioned();
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  17 */   public static final WorldType FLAT = new WorldType(1, "flat");
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  22 */   public static final WorldType LARGE_BIOMES = new WorldType(2, "largeBiomes");
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  27 */   public static final WorldType AMPLIFIED = (new WorldType(3, "amplified")).setNotificationData();
/*  28 */   public static final WorldType CUSTOMIZED = new WorldType(4, "customized");
/*  29 */   public static final WorldType DEBUG_WORLD = new WorldType(5, "debug_all_block_states");
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  34 */   public static final WorldType DEFAULT_1_1 = (new WorldType(8, "default_1_1", 0)).setCanBeCreated(false);
/*     */ 
/*     */ 
/*     */   
/*     */   private final int worldTypeId;
/*     */ 
/*     */ 
/*     */   
/*     */   private final String worldType;
/*     */ 
/*     */   
/*     */   private final int generatorVersion;
/*     */ 
/*     */   
/*     */   private boolean canBeCreated;
/*     */ 
/*     */   
/*     */   private boolean isWorldTypeVersioned;
/*     */ 
/*     */   
/*     */   private boolean hasNotificationData;
/*     */ 
/*     */ 
/*     */   
/*     */   private WorldType(int id, String name) {
/*  59 */     this(id, name, 0);
/*     */   }
/*     */   
/*     */   private WorldType(int id, String name, int version) {
/*  63 */     this.worldType = name;
/*  64 */     this.generatorVersion = version;
/*  65 */     this.canBeCreated = true;
/*  66 */     this.worldTypeId = id;
/*  67 */     worldTypes[id] = this;
/*     */   }
/*     */   
/*     */   public String getWorldTypeName() {
/*  71 */     return this.worldType;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getTranslateName() {
/*  78 */     return "generator." + this.worldType;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getTranslatedInfo() {
/*  85 */     return String.valueOf(getTranslateName()) + ".info";
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getGeneratorVersion() {
/*  92 */     return this.generatorVersion;
/*     */   }
/*     */   
/*     */   public WorldType getWorldTypeForGeneratorVersion(int version) {
/*  96 */     return (this == DEFAULT && version == 0) ? DEFAULT_1_1 : this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private WorldType setCanBeCreated(boolean enable) {
/* 103 */     this.canBeCreated = enable;
/* 104 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean getCanBeCreated() {
/* 111 */     return this.canBeCreated;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private WorldType setVersioned() {
/* 118 */     this.isWorldTypeVersioned = true;
/* 119 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isVersioned() {
/* 126 */     return this.isWorldTypeVersioned;
/*     */   }
/*     */   
/*     */   public static WorldType parseWorldType(String type) {
/* 130 */     for (int i = 0; i < worldTypes.length; i++) {
/* 131 */       if (worldTypes[i] != null && (worldTypes[i]).worldType.equalsIgnoreCase(type)) {
/* 132 */         return worldTypes[i];
/*     */       }
/*     */     } 
/*     */     
/* 136 */     return null;
/*     */   }
/*     */   
/*     */   public int getWorldTypeID() {
/* 140 */     return this.worldTypeId;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean showWorldInfoNotice() {
/* 148 */     return this.hasNotificationData;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private WorldType setNotificationData() {
/* 155 */     this.hasNotificationData = true;
/* 156 */     return this;
/*     */   }
/*     */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\minecraft\world\WorldType.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */