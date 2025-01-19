/*     */ package net.minecraft.block.material;
/*     */ 
/*     */ public class Material {
/*   4 */   public static final Material air = new MaterialTransparent(MapColor.airColor);
/*   5 */   public static final Material grass = new Material(MapColor.grassColor);
/*   6 */   public static final Material ground = new Material(MapColor.dirtColor);
/*   7 */   public static final Material wood = (new Material(MapColor.woodColor)).setBurning();
/*   8 */   public static final Material rock = (new Material(MapColor.stoneColor)).setRequiresTool();
/*   9 */   public static final Material iron = (new Material(MapColor.ironColor)).setRequiresTool();
/*  10 */   public static final Material anvil = (new Material(MapColor.ironColor)).setRequiresTool().setImmovableMobility();
/*  11 */   public static final Material water = (new MaterialLiquid(MapColor.waterColor)).setNoPushMobility();
/*  12 */   public static final Material lava = (new MaterialLiquid(MapColor.tntColor)).setNoPushMobility();
/*  13 */   public static final Material leaves = (new Material(MapColor.foliageColor)).setBurning().setTranslucent().setNoPushMobility();
/*  14 */   public static final Material plants = (new MaterialLogic(MapColor.foliageColor)).setNoPushMobility();
/*  15 */   public static final Material vine = (new MaterialLogic(MapColor.foliageColor)).setBurning().setNoPushMobility().setReplaceable();
/*  16 */   public static final Material sponge = new Material(MapColor.yellowColor);
/*  17 */   public static final Material cloth = (new Material(MapColor.clothColor)).setBurning();
/*  18 */   public static final Material fire = (new MaterialTransparent(MapColor.airColor)).setNoPushMobility();
/*  19 */   public static final Material sand = new Material(MapColor.sandColor);
/*  20 */   public static final Material circuits = (new MaterialLogic(MapColor.airColor)).setNoPushMobility();
/*  21 */   public static final Material carpet = (new MaterialLogic(MapColor.clothColor)).setBurning();
/*  22 */   public static final Material glass = (new Material(MapColor.airColor)).setTranslucent().setAdventureModeExempt();
/*  23 */   public static final Material redstoneLight = (new Material(MapColor.airColor)).setAdventureModeExempt();
/*  24 */   public static final Material tnt = (new Material(MapColor.tntColor)).setBurning().setTranslucent();
/*  25 */   public static final Material coral = (new Material(MapColor.foliageColor)).setNoPushMobility();
/*  26 */   public static final Material ice = (new Material(MapColor.iceColor)).setTranslucent().setAdventureModeExempt();
/*  27 */   public static final Material packedIce = (new Material(MapColor.iceColor)).setAdventureModeExempt();
/*  28 */   public static final Material snow = (new MaterialLogic(MapColor.snowColor)).setReplaceable().setTranslucent().setRequiresTool().setNoPushMobility();
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  33 */   public static final Material craftedSnow = (new Material(MapColor.snowColor)).setRequiresTool();
/*  34 */   public static final Material cactus = (new Material(MapColor.foliageColor)).setTranslucent().setNoPushMobility();
/*  35 */   public static final Material clay = new Material(MapColor.clayColor);
/*  36 */   public static final Material gourd = (new Material(MapColor.foliageColor)).setNoPushMobility();
/*  37 */   public static final Material dragonEgg = (new Material(MapColor.foliageColor)).setNoPushMobility();
/*  38 */   public static final Material portal = (new MaterialPortal(MapColor.airColor)).setImmovableMobility();
/*  39 */   public static final Material cake = (new Material(MapColor.airColor)).setNoPushMobility();
/*  40 */   public static final Material web = (new Material(MapColor.clothColor) {
/*     */       public boolean blocksMovement() {
/*  42 */         return false;
/*     */       }
/*  44 */     }).setRequiresTool().setNoPushMobility();
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  49 */   public static final Material piston = (new Material(MapColor.stoneColor)).setImmovableMobility();
/*  50 */   public static final Material barrier = (new Material(MapColor.airColor)).setRequiresTool().setImmovableMobility();
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private boolean canBurn;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private boolean replaceable;
/*     */ 
/*     */ 
/*     */   
/*     */   private boolean isTranslucent;
/*     */ 
/*     */ 
/*     */   
/*     */   private final MapColor materialMapColor;
/*     */ 
/*     */ 
/*     */   
/*     */   private boolean requiresNoTool = true;
/*     */ 
/*     */ 
/*     */   
/*     */   private int mobilityFlag;
/*     */ 
/*     */ 
/*     */   
/*     */   private boolean isAdventureModeExempt;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Material(MapColor color) {
/*  86 */     this.materialMapColor = color;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isLiquid() {
/*  93 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isSolid() {
/* 100 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean blocksLight() {
/* 107 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean blocksMovement() {
/* 114 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private Material setTranslucent() {
/* 121 */     this.isTranslucent = true;
/* 122 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected Material setRequiresTool() {
/* 129 */     this.requiresNoTool = false;
/* 130 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected Material setBurning() {
/* 137 */     this.canBurn = true;
/* 138 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean getCanBurn() {
/* 145 */     return this.canBurn;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Material setReplaceable() {
/* 152 */     this.replaceable = true;
/* 153 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isReplaceable() {
/* 160 */     return this.replaceable;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isOpaque() {
/* 167 */     return this.isTranslucent ? false : blocksMovement();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isToolNotRequired() {
/* 174 */     return this.requiresNoTool;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getMaterialMobility() {
/* 182 */     return this.mobilityFlag;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected Material setNoPushMobility() {
/* 189 */     this.mobilityFlag = 1;
/* 190 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected Material setImmovableMobility() {
/* 197 */     this.mobilityFlag = 2;
/* 198 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected Material setAdventureModeExempt() {
/* 205 */     this.isAdventureModeExempt = true;
/* 206 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public MapColor getMaterialMapColor() {
/* 213 */     return this.materialMapColor;
/*     */   }
/*     */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\minecraft\block\material\Material.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */