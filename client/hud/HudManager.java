/*     */ package client.hud;
/*     */ import client.mod.impl.ArrowCounter;
/*     */ import client.mod.impl.Blockoutline;
/*     */ import client.mod.impl.Cape;
/*     */ import client.mod.impl.ClearChat;
/*     */ import client.mod.impl.CustomGui;
/*     */ import client.mod.impl.Fullbrightmod;
/*     */ import client.mod.impl.HitDelayFix;
/*     */ import client.mod.impl.ItemHeld;
/*     */ import client.mod.impl.ModBiom;
/*     */ import client.mod.impl.ModCPS;
/*     */ import client.mod.impl.ModFPS;
/*     */ import client.mod.impl.MotionBlur;
/*     */ import client.mod.impl.NewNausea;
/*     */ import client.mod.impl.NoCinematicZoom;
/*     */ import client.mod.impl.OldHeadPoint;
/*     */ import client.mod.impl.OldSneak;
/*     */ import client.mod.impl.OreCounter;
/*     */ import client.mod.impl.PackDisplay;
/*     */ import client.mod.impl.PingMod;
/*     */ import client.mod.impl.SimpleGui;
/*     */ import client.mod.impl.Snaplook;
/*     */ import client.mod.impl.TexFix;
/*     */ import client.mod.impl.TntTimer;
/*     */ import client.mod.impl.ToggleSprintHud;
/*     */ import client.mod.impl.Wings;
/*     */ import client.mod.impl.Wings2;
/*     */ import java.util.ArrayList;
/*     */ 
/*     */ public class HudManager {
/*     */   public ArrayList<HudMod> hudMods;
/*     */   public ClientName clientName;
/*     */   public ModBiom modBiom;
/*     */   public ModCPS modCps;
/*     */   public ModFPS modFps;
/*     */   public ModKeystrokes modKeyStrokes;
/*     */   public ModPotionStatus modPotionStatus;
/*     */   public ItemHeld itemHeld;
/*     */   public ModXYZ modXYZ;
/*     */   public PackDisplay packDisplay;
/*     */   public ToggleSprintHud toggleSprintHud;
/*     */   public ModArmorStatus modArmorStatus;
/*     */   public Cape cape;
/*     */   public PlayTime timer;
/*     */   public Cape2 cape2;
/*     */   public Blockoutline blockoutline;
/*     */   public HitColor hitColor;
/*     */   public AutoGG autoGG;
/*     */   public CustomGui customGui;
/*     */   public OldSneak oldSneak;
/*     */   public ModPerspective modPerspective;
/*     */   public Snaplook snaplook;
/*     */   
/*     */   public <T extends HudMod> T getMod(Class<T> clazz) {
/*  55 */     return (T)this.hudMods.stream().filter(mod -> (mod.getClass() == paramClass)).findFirst().orElse(null);
/*     */   }
/*     */   public Wings wings; public Wings2 wings2; public PingMod pingMod; public Oldanimations oldanimations; public ClearChat clearChat; public NoHurtCam noHeartCam; public Hitbox hitbox; public Fullbrightmod fullbrightmod; public HitDelayFix hitDelayFix; public SimpleGui simpleGui; public NoInvBG NoInventoryBG; public BowDistance bowdistance; public StrengthAim strengthAim; public OldHeadPoint oldHeadPoint; public TexFix texFix; public NoCinematicZoom noCinematicZoom; public NewNausea newNausea; public TntTimer tntTimer; public MotionBlur motionBlur; public OreCounter oreCounter; public ArrowCounter arrowCounter; public TimeChanger timeChanger;
/*     */   public HudManager() {
/*  59 */     (this.hudMods = new ArrayList<>()).add(this.toggleSprintHud = new ToggleSprintHud());
/*  60 */     this.hudMods.add(this.clientName = new ClientName());
/*  61 */     this.hudMods.add(this.modArmorStatus = new ModArmorStatus());
/*  62 */     this.hudMods.add(this.itemHeld = new ItemHeld());
/*  63 */     this.hudMods.add(this.modBiom = new ModBiom());
/*  64 */     this.hudMods.add(this.modCps = new ModCPS());
/*  65 */     this.hudMods.add(this.modFps = new ModFPS());
/*  66 */     this.hudMods.add(this.modKeyStrokes = new ModKeystrokes());
/*  67 */     this.hudMods.add(this.modPotionStatus = new ModPotionStatus());
/*  68 */     this.hudMods.add(this.modXYZ = new ModXYZ());
/*  69 */     this.hudMods.add(this.packDisplay = new PackDisplay());
/*  70 */     this.hudMods.add(this.cape = new Cape());
/*  71 */     this.hudMods.add(this.timer = new PlayTime());
/*  72 */     this.hudMods.add(this.cape2 = new Cape2());
/*  73 */     this.hudMods.add(this.blockoutline = new Blockoutline());
/*  74 */     this.hudMods.add(this.hitColor = new HitColor());
/*  75 */     this.hudMods.add(this.autoGG = new AutoGG());
/*  76 */     this.hudMods.add(this.customGui = new CustomGui());
/*  77 */     this.hudMods.add(this.oldSneak = new OldSneak());
/*  78 */     this.hudMods.add(this.snaplook = new Snaplook());
/*  79 */     this.hudMods.add(this.wings = new Wings());
/*  80 */     this.hudMods.add(this.wings2 = new Wings2());
/*  81 */     this.hudMods.add(this.pingMod = new PingMod());
/*  82 */     this.hudMods.add(this.oldanimations = new Oldanimations());
/*  83 */     this.hudMods.add(this.clearChat = new ClearChat());
/*  84 */     this.hudMods.add(this.noHeartCam = new NoHurtCam());
/*  85 */     this.hudMods.add(this.hitbox = new Hitbox());
/*  86 */     this.hudMods.add(this.fullbrightmod = new Fullbrightmod());
/*  87 */     this.hudMods.add(this.hitDelayFix = new HitDelayFix());
/*  88 */     this.hudMods.add(this.simpleGui = new SimpleGui());
/*  89 */     this.hudMods.add(this.NoInventoryBG = new NoInvBG());
/*  90 */     this.hudMods.add(this.bowdistance = new BowDistance());
/*  91 */     this.hudMods.add(this.strengthAim = new StrengthAim());
/*  92 */     this.hudMods.add(this.oldHeadPoint = new OldHeadPoint());
/*  93 */     this.hudMods.add(this.texFix = new TexFix());
/*  94 */     this.hudMods.add(this.noCinematicZoom = new NoCinematicZoom());
/*  95 */     this.hudMods.add(this.newNausea = new NewNausea());
/*  96 */     this.hudMods.add(this.tntTimer = new TntTimer());
/*  97 */     this.hudMods.add(this.motionBlur = new MotionBlur());
/*  98 */     this.hudMods.add(this.oreCounter = new OreCounter());
/*  99 */     this.hudMods.add(this.arrowCounter = new ArrowCounter());
/* 100 */     this.hudMods.add(this.timeChanger = new TimeChanger());
/* 101 */     this.hudMods.add(new helper());
/*     */   }
/*     */   
/*     */   public void addHudmod(HudMod m) {
/* 105 */     this.hudMods.add(m);
/*     */   }
/*     */   
/*     */   public void renderMods() {
/* 109 */     for (HudMod m : this.hudMods) {
/* 110 */       if (m.isEnabled())
/* 111 */         m.draw(); 
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\client\hud\HudManager.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */