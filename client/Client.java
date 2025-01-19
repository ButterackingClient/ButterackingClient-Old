/*     */ package client;
/*     */ 
/*     */ import client.config.Config;
/*     */ import client.event.EventManager;
/*     */ import client.event.SubscribeEvent;
/*     */ import client.event.impl.ClientTickEvent;
/*     */ import client.gui.DiscordRp;
/*     */ import client.gui.SplashProgress;
/*     */ import client.gui.click.ClickGui;
/*     */ import client.hud.HUDConfigScreen;
/*     */ import client.hud.HudManager;
/*     */ import client.mod.ModManager;
/*     */ import client.texfix.TextureFix;
/*     */ import client.timer.DeltaTimer;
/*     */ import kp.input.KoreanIME;
/*     */ import net.minecraft.client.Minecraft;
/*     */ import net.minecraft.client.gui.GuiScreen;
/*     */ import org.lwjgl.input.Keyboard;
/*     */ 
/*     */ 
/*     */ 
/*     */ public class Client
/*     */ {
/*  24 */   public String discordl = "large";
/*  25 */   public String discordid = "1075979966019276890";
/*  26 */   public char chars = '§';
/*  27 */   public String colorbase = String.valueOf(this.chars);
/*  28 */   public String mainColor = String.valueOf(this.colorbase) + "s";
/*  29 */   public String subColor = String.valueOf(this.colorbase) + "f";
/*  30 */   public String coverColor = String.valueOf(this.colorbase) + "7";
/*  31 */   public String white = String.valueOf(this.colorbase) + "f";
/*  32 */   public String check = "!_!";
/*  33 */   public String clientName = "Butteracking Client";
/*  34 */   private static Client instance = new Client();
/*  35 */   public Minecraft mc = Minecraft.getMinecraft();
/*     */   public EventManager eventManager;
/*     */   public ModManager modManager;
/*     */   public HudManager hudManager;
/*  39 */   public DiscordRp discordRp = new DiscordRp();
/*     */   public Config config;
/*  41 */   public int splashRed = 150;
/*  42 */   public int splashGreen = 39;
/*  43 */   public int splashBlue = 229;
/*  44 */   public float blockOutLineRed = this.splashRed / 255.0F;
/*  45 */   public float blockOutLineGreen = this.splashGreen / 255.0F;
/*  46 */   public float blockOutLineBlue = this.splashBlue / 255.0F;
/*  47 */   public float hitColorRed = this.blockOutLineRed;
/*  48 */   public float hitColorGreen = this.blockOutLineGreen;
/*  49 */   public float hitColorBlue = this.blockOutLineBlue;
/*  50 */   public float hitColorAlpha = 0.5F;
/*     */   public boolean krPatchEnabled = true;
/*     */   public boolean isGuiCovered = true;
/*     */   
/*     */   public static Client getInstance() {
/*  55 */     return instance;
/*     */   }
/*     */   
/*     */   public void init() {
/*  59 */     this.discordRp.start();
/*     */   }
/*     */   
/*     */   public void splashstart() {
/*  63 */     SplashProgress.setProgress(1, "Client - Starting");
/*     */   }
/*     */   
/*     */   public void startup() {
/*  67 */     TextureFix.INSTANCE.onPostInit();
/*  68 */     DeltaTimer.D.stop();
/*  69 */     this.eventManager = new EventManager();
/*  70 */     (this.config = new Config()).loadModConfig();
/*  71 */     this.modManager = new ModManager();
/*  72 */     this.hudManager = new HudManager();
/*  73 */     System.out.println(this.check);
/*  74 */     EventManager.register(this);
/*     */   }
/*     */   
/*     */   public void shutdown() {
/*  78 */     System.out.println(this.check);
/*  79 */     this.config.saveModConfig();
/*  80 */     EventManager.unregister(this);
/*  81 */     this.discordRp.shutdown();
/*     */   }
/*     */   public DiscordRp getDiscordrp() {
/*  84 */     return this.discordRp;
/*     */   }
/*     */   
/*     */   public void enterSpWorld() {
/*  88 */     getInstance().getDiscordrp().update("SinglePlayer", "In Game");
/*     */   }
/*     */   
/*     */   public void enterMpWorld() {
/*  92 */     DeltaTimer.D.start();
/*  93 */     getInstance().getDiscordrp().update("MultiPlayer", "In Game");
/*     */   }
/*     */   
/*     */   @SubscribeEvent
/*     */   public void onTick(ClientTickEvent event) {
/*  98 */     this.isGuiCovered = this.hudManager.simpleGui.isEnabled();
/*  99 */     if (!this.krPatchEnabled) {
/* 100 */       KoreanIME.enabled = false;
/*     */     }
/* 102 */     if (this.mc.gameSettings.keyBindInventory.isPressed()) {
/* 103 */       this.modManager.toggleSprint.toggle();
/*     */     }
/* 105 */     if (this.mc.gameSettings.keyBindDragHud.isPressed()) {
/* 106 */       this.mc.displayGuiScreen((GuiScreen)new HUDConfigScreen());
/*     */     }
/* 108 */     if ((getInstance()).hudManager.fullbrightmod.isEnabled()) {
/* 109 */       if (this.mc.gameSettings.saturation < 15.0F) {
/* 110 */         this.mc.gameSettings.saturation = 15.0F;
/*     */       }
/*     */     } else {
/* 113 */       this.mc.gameSettings.saturation = 0.0F;
/*     */     } 
/* 115 */     if (this.mc.gameSettings.keyBindClickGui.isKeyDown())
/* 116 */       this.mc.displayGuiScreen((GuiScreen)new ClickGui()); 
/*     */   }
/*     */   
/*     */   @SubscribeEvent
/*     */   public void onKey(KeyEvent e) {
/* 121 */     if (this.hudManager.snaplook.isEnabled()) {
/* 122 */       boolean snaplook = false;
/* 123 */       boolean returnOnRelease = true;
/* 124 */       int previousePrespective = 0;
/* 125 */       Minecraft mc = Minecraft.getMinecraft();
/* 126 */       if (e.getKey() == mc.gameSettings.keyBindSnaplook.getKeyCode()) {
/* 127 */         if (mc.gameSettings.keyBindSnaplook.isKeyDown()) {
/* 128 */           snaplook = !snaplook;
/* 129 */           if (snaplook) {
/* 130 */             previousePrespective = mc.gameSettings.showDebugInfo;
/* 131 */             mc.gameSettings.showDebugInfo++;
/*     */           } else {
/*     */             
/* 134 */             mc.gameSettings.showDebugInfo--;
/*     */           }
/*     */         
/* 137 */         } else if (returnOnRelease) {
/* 138 */           snaplook = false;
/* 139 */           mc.gameSettings.showDebugInfo--;
/*     */         } 
/*     */       }
/* 142 */       if (Keyboard.getEventKey() == mc.gameSettings.keyBindSmoothCamera.getKeyCode())
/* 143 */         snaplook = false; 
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\client\Client.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */