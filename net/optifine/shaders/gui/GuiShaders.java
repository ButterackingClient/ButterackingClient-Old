/*     */ package net.optifine.shaders.gui;
/*     */ 
/*     */ import java.io.File;
/*     */ import java.io.IOException;
/*     */ import java.net.URI;
/*     */ import net.minecraft.client.Minecraft;
/*     */ import net.minecraft.client.gui.GuiButton;
/*     */ import net.minecraft.client.gui.GuiScreen;
/*     */ import net.minecraft.client.resources.I18n;
/*     */ import net.minecraft.client.settings.GameSettings;
/*     */ import net.minecraft.src.Config;
/*     */ import net.optifine.Lang;
/*     */ import net.optifine.gui.GuiScreenOF;
/*     */ import net.optifine.gui.TooltipManager;
/*     */ import net.optifine.gui.TooltipProvider;
/*     */ import net.optifine.gui.TooltipProviderEnumShaderOptions;
/*     */ import net.optifine.shaders.Shaders;
/*     */ import net.optifine.shaders.ShadersTex;
/*     */ import net.optifine.shaders.config.EnumShaderOption;
/*     */ import org.lwjgl.Sys;
/*     */ 
/*     */ public class GuiShaders extends GuiScreenOF {
/*     */   protected GuiScreen parentGui;
/*  24 */   protected String screenTitle = "Shaders";
/*  25 */   private TooltipManager tooltipManager = new TooltipManager((GuiScreen)this, (TooltipProvider)new TooltipProviderEnumShaderOptions());
/*  26 */   private int updateTimer = -1;
/*     */   private GuiSlotShaders shaderList;
/*     */   private boolean saved = false;
/*  29 */   private static float[] QUALITY_MULTIPLIERS = new float[] { 0.5F, 0.6F, 0.6666667F, 0.75F, 0.8333333F, 0.9F, 1.0F, 1.1666666F, 1.3333334F, 1.5F, 1.6666666F, 1.8F, 2.0F };
/*  30 */   private static String[] QUALITY_MULTIPLIER_NAMES = new String[] { "0.5x", "0.6x", "0.66x", "0.75x", "0.83x", "0.9x", "1x", "1.16x", "1.33x", "1.5x", "1.66x", "1.8x", "2x" };
/*  31 */   private static float QUALITY_MULTIPLIER_DEFAULT = 1.0F;
/*  32 */   private static float[] HAND_DEPTH_VALUES = new float[] { 0.0625F, 0.125F, 0.25F };
/*  33 */   private static String[] HAND_DEPTH_NAMES = new String[] { "0.5x", "1x", "2x" };
/*  34 */   private static float HAND_DEPTH_DEFAULT = 0.125F;
/*     */   public static final int EnumOS_UNKNOWN = 0;
/*     */   public static final int EnumOS_WINDOWS = 1;
/*     */   public static final int EnumOS_OSX = 2;
/*     */   public static final int EnumOS_SOLARIS = 3;
/*     */   public static final int EnumOS_LINUX = 4;
/*     */   
/*     */   public GuiShaders(GuiScreen par1GuiScreen, GameSettings par2GameSettings) {
/*  42 */     this.parentGui = par1GuiScreen;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void initGui() {
/*  50 */     this.screenTitle = I18n.format("of.options.shadersTitle", new Object[0]);
/*     */     
/*  52 */     if (Shaders.shadersConfig == null) {
/*  53 */       Shaders.loadConfig();
/*     */     }
/*     */     
/*  56 */     int i = 120;
/*  57 */     int j = 20;
/*  58 */     int k = width - i - 10;
/*  59 */     int l = 30;
/*  60 */     int i1 = 20;
/*  61 */     int j1 = width - i - 20;
/*  62 */     this.shaderList = new GuiSlotShaders(this, j1, height, l, height - 50, 16);
/*  63 */     this.shaderList.registerScrollButtons(7, 8);
/*  64 */     this.buttonList.add(new GuiButtonEnumShaderOption(EnumShaderOption.ANTIALIASING, k, 0 * i1 + l, i, j));
/*  65 */     this.buttonList.add(new GuiButtonEnumShaderOption(EnumShaderOption.NORMAL_MAP, k, 1 * i1 + l, i, j));
/*  66 */     this.buttonList.add(new GuiButtonEnumShaderOption(EnumShaderOption.SPECULAR_MAP, k, 2 * i1 + l, i, j));
/*  67 */     this.buttonList.add(new GuiButtonEnumShaderOption(EnumShaderOption.RENDER_RES_MUL, k, 3 * i1 + l, i, j));
/*  68 */     this.buttonList.add(new GuiButtonEnumShaderOption(EnumShaderOption.SHADOW_RES_MUL, k, 4 * i1 + l, i, j));
/*  69 */     this.buttonList.add(new GuiButtonEnumShaderOption(EnumShaderOption.HAND_DEPTH_MUL, k, 5 * i1 + l, i, j));
/*  70 */     this.buttonList.add(new GuiButtonEnumShaderOption(EnumShaderOption.OLD_HAND_LIGHT, k, 6 * i1 + l, i, j));
/*  71 */     this.buttonList.add(new GuiButtonEnumShaderOption(EnumShaderOption.OLD_LIGHTING, k, 7 * i1 + l, i, j));
/*  72 */     int k1 = Math.min(150, j1 / 2 - 10);
/*  73 */     int l1 = j1 / 4 - k1 / 2;
/*  74 */     int i2 = height - 25;
/*  75 */     this.buttonList.add(new GuiButton(201, l1, i2, k1 - 22 + 1, j, Lang.get("of.options.shaders.shadersFolder")));
/*  76 */     this.buttonList.add(new GuiButtonDownloadShaders(210, l1 + k1 - 22 - 1, i2));
/*  77 */     this.buttonList.add(new GuiButton(202, j1 / 4 * 3 - k1 / 2, height - 25, k1, j, I18n.format("gui.done", new Object[0])));
/*  78 */     this.buttonList.add(new GuiButton(203, k, height - 25, i, j, Lang.get("of.options.shaders.shaderOptions")));
/*  79 */     updateButtons();
/*     */   }
/*     */   
/*     */   public void updateButtons() {
/*  83 */     boolean flag = Config.isShaders();
/*     */     
/*  85 */     for (GuiButton guibutton : this.buttonList) {
/*  86 */       if (guibutton.id != 201 && guibutton.id != 202 && guibutton.id != 210 && guibutton.id != EnumShaderOption.ANTIALIASING.ordinal()) {
/*  87 */         guibutton.enabled = flag;
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void handleMouseInput() throws IOException {
/*  96 */     super.handleMouseInput();
/*  97 */     this.shaderList.handleMouseInput();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void actionPerformed(GuiButton button) {
/* 104 */     actionPerformed(button, false);
/*     */   }
/*     */   
/*     */   protected void actionPerformedRightClick(GuiButton button) {
/* 108 */     actionPerformed(button, true);
/*     */   }
/*     */   
/*     */   private void actionPerformed(GuiButton button, boolean rightClick) {
/* 112 */     if (button.enabled) {
/* 113 */       if (!(button instanceof GuiButtonEnumShaderOption)) {
/* 114 */         if (!rightClick) {
/* 115 */           String s; boolean flag; GuiShaderOptions guishaderoptions; switch (button.id) {
/*     */             case 201:
/* 117 */               switch (getOSType()) {
/*     */                 case 1:
/* 119 */                   s = String.format("cmd.exe /C start \"Open file\" \"%s\"", new Object[] { Shaders.shaderPacksDir.getAbsolutePath() });
/*     */                   
/*     */                   try {
/* 122 */                     Runtime.getRuntime().exec(s);
/*     */                     return;
/* 124 */                   } catch (IOException ioexception) {
/* 125 */                     ioexception.printStackTrace();
/*     */                     break;
/*     */                   } 
/*     */                 
/*     */                 case 2:
/*     */                   try {
/* 131 */                     Runtime.getRuntime().exec(new String[] { "/usr/bin/open", Shaders.shaderPacksDir.getAbsolutePath() });
/*     */                     return;
/* 133 */                   } catch (IOException ioexception1) {
/* 134 */                     ioexception1.printStackTrace();
/*     */                     break;
/*     */                   } 
/*     */               } 
/* 138 */               flag = false;
/*     */               
/*     */               try {
/* 141 */                 Class<?> oclass1 = Class.forName("java.awt.Desktop");
/* 142 */                 Object object1 = oclass1.getMethod("getDesktop", new Class[0]).invoke(null, new Object[0]);
/* 143 */                 oclass1.getMethod("browse", new Class[] { URI.class }).invoke(object1, new Object[] { (new File(this.mc.mcDataDir, "shaderpacks")).toURI() });
/* 144 */               } catch (Throwable throwable1) {
/* 145 */                 throwable1.printStackTrace();
/* 146 */                 flag = true;
/*     */               } 
/*     */               
/* 149 */               if (flag) {
/* 150 */                 Config.dbg("Opening via system class!");
/* 151 */                 Sys.openURL("file://" + Shaders.shaderPacksDir.getAbsolutePath());
/*     */               } 
/*     */               return;
/*     */ 
/*     */             
/*     */             case 202:
/* 157 */               Shaders.storeConfig();
/* 158 */               this.saved = true;
/* 159 */               this.mc.displayGuiScreen(this.parentGui);
/*     */               return;
/*     */             
/*     */             case 203:
/* 163 */               guishaderoptions = new GuiShaderOptions((GuiScreen)this, Config.getGameSettings());
/* 164 */               Config.getMinecraft().displayGuiScreen((GuiScreen)guishaderoptions);
/*     */               return;
/*     */             
/*     */             case 210:
/*     */               try {
/* 169 */                 Class<?> oclass = Class.forName("java.awt.Desktop");
/* 170 */                 Object object = oclass.getMethod("getDesktop", new Class[0]).invoke(null, new Object[0]);
/* 171 */                 oclass.getMethod("browse", new Class[] { URI.class }).invoke(object, new Object[] { new URI("http://optifine.net/shaderPacks") });
/* 172 */               } catch (Throwable throwable) {
/* 173 */                 throwable.printStackTrace();
/*     */               } 
/*     */               break;
/*     */           } 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */           
/* 183 */           this.shaderList.actionPerformed(button);
/*     */         } 
/*     */       } else {
/*     */         
/* 187 */         GuiButtonEnumShaderOption guibuttonenumshaderoption = (GuiButtonEnumShaderOption)button;
/*     */         
/* 189 */         switch (guibuttonenumshaderoption.getEnumShaderOption()) {
/*     */           case null:
/* 191 */             Shaders.nextAntialiasingLevel(!rightClick);
/*     */             
/* 193 */             if (hasShiftDown()) {
/* 194 */               Shaders.configAntialiasingLevel = 0;
/*     */             }
/*     */             
/* 197 */             Shaders.uninit();
/*     */             break;
/*     */           
/*     */           case NORMAL_MAP:
/* 201 */             Shaders.configNormalMap = !Shaders.configNormalMap;
/*     */             
/* 203 */             if (hasShiftDown()) {
/* 204 */               Shaders.configNormalMap = true;
/*     */             }
/*     */             
/* 207 */             Shaders.uninit();
/* 208 */             this.mc.scheduleResourcesRefresh();
/*     */             break;
/*     */           
/*     */           case SPECULAR_MAP:
/* 212 */             Shaders.configSpecularMap = !Shaders.configSpecularMap;
/*     */             
/* 214 */             if (hasShiftDown()) {
/* 215 */               Shaders.configSpecularMap = true;
/*     */             }
/*     */             
/* 218 */             Shaders.uninit();
/* 219 */             this.mc.scheduleResourcesRefresh();
/*     */             break;
/*     */           
/*     */           case RENDER_RES_MUL:
/* 223 */             Shaders.configRenderResMul = getNextValue(Shaders.configRenderResMul, QUALITY_MULTIPLIERS, QUALITY_MULTIPLIER_DEFAULT, !rightClick, hasShiftDown());
/* 224 */             Shaders.uninit();
/* 225 */             Shaders.scheduleResize();
/*     */             break;
/*     */           
/*     */           case SHADOW_RES_MUL:
/* 229 */             Shaders.configShadowResMul = getNextValue(Shaders.configShadowResMul, QUALITY_MULTIPLIERS, QUALITY_MULTIPLIER_DEFAULT, !rightClick, hasShiftDown());
/* 230 */             Shaders.uninit();
/* 231 */             Shaders.scheduleResizeShadow();
/*     */             break;
/*     */           
/*     */           case HAND_DEPTH_MUL:
/* 235 */             Shaders.configHandDepthMul = getNextValue(Shaders.configHandDepthMul, HAND_DEPTH_VALUES, HAND_DEPTH_DEFAULT, !rightClick, hasShiftDown());
/* 236 */             Shaders.uninit();
/*     */             break;
/*     */           
/*     */           case OLD_HAND_LIGHT:
/* 240 */             Shaders.configOldHandLight.nextValue(!rightClick);
/*     */             
/* 242 */             if (hasShiftDown()) {
/* 243 */               Shaders.configOldHandLight.resetValue();
/*     */             }
/*     */             
/* 246 */             Shaders.uninit();
/*     */             break;
/*     */           
/*     */           case OLD_LIGHTING:
/* 250 */             Shaders.configOldLighting.nextValue(!rightClick);
/*     */             
/* 252 */             if (hasShiftDown()) {
/* 253 */               Shaders.configOldLighting.resetValue();
/*     */             }
/*     */             
/* 256 */             Shaders.updateBlockLightLevel();
/* 257 */             Shaders.uninit();
/* 258 */             this.mc.scheduleResourcesRefresh();
/*     */             break;
/*     */           
/*     */           case TWEAK_BLOCK_DAMAGE:
/* 262 */             Shaders.configTweakBlockDamage = !Shaders.configTweakBlockDamage;
/*     */             break;
/*     */           
/*     */           case CLOUD_SHADOW:
/* 266 */             Shaders.configCloudShadow = !Shaders.configCloudShadow;
/*     */             break;
/*     */           
/*     */           case TEX_MIN_FIL_B:
/* 270 */             Shaders.configTexMinFilB = (Shaders.configTexMinFilB + 1) % 3;
/* 271 */             Shaders.configTexMinFilN = Shaders.configTexMinFilS = Shaders.configTexMinFilB;
/* 272 */             button.displayString = "Tex Min: " + Shaders.texMinFilDesc[Shaders.configTexMinFilB];
/* 273 */             ShadersTex.updateTextureMinMagFilter();
/*     */             break;
/*     */           
/*     */           case TEX_MAG_FIL_N:
/* 277 */             Shaders.configTexMagFilN = (Shaders.configTexMagFilN + 1) % 2;
/* 278 */             button.displayString = "Tex_n Mag: " + Shaders.texMagFilDesc[Shaders.configTexMagFilN];
/* 279 */             ShadersTex.updateTextureMinMagFilter();
/*     */             break;
/*     */           
/*     */           case TEX_MAG_FIL_S:
/* 283 */             Shaders.configTexMagFilS = (Shaders.configTexMagFilS + 1) % 2;
/* 284 */             button.displayString = "Tex_s Mag: " + Shaders.texMagFilDesc[Shaders.configTexMagFilS];
/* 285 */             ShadersTex.updateTextureMinMagFilter();
/*     */             break;
/*     */           
/*     */           case SHADOW_CLIP_FRUSTRUM:
/* 289 */             Shaders.configShadowClipFrustrum = !Shaders.configShadowClipFrustrum;
/* 290 */             button.displayString = "ShadowClipFrustrum: " + toStringOnOff(Shaders.configShadowClipFrustrum);
/* 291 */             ShadersTex.updateTextureMinMagFilter();
/*     */             break;
/*     */         } 
/* 294 */         guibuttonenumshaderoption.updateButtonText();
/*     */       } 
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void onGuiClosed() {
/* 303 */     super.onGuiClosed();
/*     */     
/* 305 */     if (!this.saved) {
/* 306 */       Shaders.storeConfig();
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void drawScreen(int mouseX, int mouseY, float partialTicks) {
/* 314 */     drawDefaultBackground();
/* 315 */     this.shaderList.drawScreen(mouseX, mouseY, partialTicks);
/*     */     
/* 317 */     if (this.updateTimer <= 0) {
/* 318 */       this.shaderList.updateList();
/* 319 */       this.updateTimer += 20;
/*     */     } 
/*     */     
/* 322 */     drawCenteredString(this.fontRendererObj, String.valueOf(this.screenTitle) + " ", width / 2, 15, 16777215);
/* 323 */     String s = "OpenGL: " + Shaders.glVersionString + ", " + Shaders.glVendorString + ", " + Shaders.glRendererString;
/* 324 */     int i = this.fontRendererObj.getStringWidth(s);
/*     */     
/* 326 */     if (i < width - 5) {
/* 327 */       drawCenteredString(this.fontRendererObj, s, width / 2, height - 40, 8421504);
/*     */     } else {
/* 329 */       drawString(this.fontRendererObj, s, 5, height - 40, 8421504);
/*     */     } 
/*     */     
/* 332 */     super.drawScreen(mouseX, mouseY, partialTicks);
/* 333 */     this.tooltipManager.drawTooltips(mouseX, mouseY, this.buttonList);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void updateScreen() {
/* 340 */     super.updateScreen();
/* 341 */     this.updateTimer--;
/*     */   }
/*     */   
/*     */   public Minecraft getMc() {
/* 345 */     return this.mc;
/*     */   }
/*     */   
/*     */   public void drawCenteredString(String text, int x, int y, int color) {
/* 349 */     drawCenteredString(this.fontRendererObj, text, x, y, color);
/*     */   }
/*     */   
/*     */   public static String toStringOnOff(boolean value) {
/* 353 */     String s = Lang.getOn();
/* 354 */     String s1 = Lang.getOff();
/* 355 */     return value ? s : s1;
/*     */   }
/*     */   
/*     */   public static String toStringAa(int value) {
/* 359 */     return (value == 2) ? "FXAA 2x" : ((value == 4) ? "FXAA 4x" : Lang.getOff());
/*     */   }
/*     */   
/*     */   public static String toStringValue(float val, float[] values, String[] names) {
/* 363 */     int i = getValueIndex(val, values);
/* 364 */     return names[i];
/*     */   }
/*     */   
/*     */   private float getNextValue(float val, float[] values, float valDef, boolean forward, boolean reset) {
/* 368 */     if (reset) {
/* 369 */       return valDef;
/*     */     }
/* 371 */     int i = getValueIndex(val, values);
/*     */     
/* 373 */     if (forward) {
/* 374 */       i++;
/*     */       
/* 376 */       if (i >= values.length) {
/* 377 */         i = 0;
/*     */       }
/*     */     } else {
/* 380 */       i--;
/*     */       
/* 382 */       if (i < 0) {
/* 383 */         i = values.length - 1;
/*     */       }
/*     */     } 
/*     */     
/* 387 */     return values[i];
/*     */   }
/*     */ 
/*     */   
/*     */   public static int getValueIndex(float val, float[] values) {
/* 392 */     for (int i = 0; i < values.length; i++) {
/* 393 */       float f = values[i];
/*     */       
/* 395 */       if (f >= val) {
/* 396 */         return i;
/*     */       }
/*     */     } 
/*     */     
/* 400 */     return values.length - 1;
/*     */   }
/*     */   
/*     */   public static String toStringQuality(float val) {
/* 404 */     return toStringValue(val, QUALITY_MULTIPLIERS, QUALITY_MULTIPLIER_NAMES);
/*     */   }
/*     */   
/*     */   public static String toStringHandDepth(float val) {
/* 408 */     return toStringValue(val, HAND_DEPTH_VALUES, HAND_DEPTH_NAMES);
/*     */   }
/*     */   
/*     */   public static int getOSType() {
/* 412 */     String s = System.getProperty("os.name").toLowerCase();
/* 413 */     return s.contains("win") ? 1 : (s.contains("mac") ? 2 : (s.contains("solaris") ? 3 : (s.contains("sunos") ? 3 : (s.contains("linux") ? 4 : (s.contains("unix") ? 4 : 0)))));
/*     */   }
/*     */   
/*     */   public boolean hasShiftDown() {
/* 417 */     return isShiftKeyDown();
/*     */   }
/*     */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\optifine\shaders\gui\GuiShaders.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */