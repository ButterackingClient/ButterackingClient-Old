/*      */ package net.minecraft.src;
/*      */ 
/*      */ import java.awt.Desktop;
/*      */ import java.awt.Dimension;
/*      */ import java.awt.image.BufferedImage;
/*      */ import java.io.BufferedReader;
/*      */ import java.io.ByteArrayOutputStream;
/*      */ import java.io.File;
/*      */ import java.io.FileInputStream;
/*      */ import java.io.FileOutputStream;
/*      */ import java.io.IOException;
/*      */ import java.io.InputStream;
/*      */ import java.io.InputStreamReader;
/*      */ import java.lang.reflect.Array;
/*      */ import java.net.URI;
/*      */ import java.nio.ByteBuffer;
/*      */ import java.nio.IntBuffer;
/*      */ import java.util.ArrayList;
/*      */ import java.util.Arrays;
/*      */ import java.util.Collection;
/*      */ import java.util.Comparator;
/*      */ import java.util.HashSet;
/*      */ import java.util.List;
/*      */ import java.util.Set;
/*      */ import java.util.StringTokenizer;
/*      */ import java.util.regex.Matcher;
/*      */ import java.util.regex.Pattern;
/*      */ import javax.imageio.ImageIO;
/*      */ import net.minecraft.client.LoadingScreenRenderer;
/*      */ import net.minecraft.client.Minecraft;
/*      */ import net.minecraft.client.gui.GuiScreen;
/*      */ import net.minecraft.client.gui.ScaledResolution;
/*      */ import net.minecraft.client.renderer.GLAllocation;
/*      */ import net.minecraft.client.renderer.GlStateManager;
/*      */ import net.minecraft.client.renderer.OpenGlHelper;
/*      */ import net.minecraft.client.renderer.RenderGlobal;
/*      */ import net.minecraft.client.renderer.texture.DynamicTexture;
/*      */ import net.minecraft.client.renderer.texture.TextureManager;
/*      */ import net.minecraft.client.renderer.texture.TextureMap;
/*      */ import net.minecraft.client.resources.DefaultResourcePack;
/*      */ import net.minecraft.client.resources.I18n;
/*      */ import net.minecraft.client.resources.IResource;
/*      */ import net.minecraft.client.resources.IResourceManager;
/*      */ import net.minecraft.client.resources.IResourcePack;
/*      */ import net.minecraft.client.resources.ResourcePackRepository;
/*      */ import net.minecraft.client.resources.model.ModelManager;
/*      */ import net.minecraft.client.settings.GameSettings;
/*      */ import net.minecraft.util.BlockPos;
/*      */ import net.minecraft.util.ChatComponentText;
/*      */ import net.minecraft.util.FrameTimer;
/*      */ import net.minecraft.util.IChatComponent;
/*      */ import net.minecraft.util.MathHelper;
/*      */ import net.minecraft.util.ResourceLocation;
/*      */ import net.optifine.DynamicLights;
/*      */ import net.optifine.GlErrors;
/*      */ import net.optifine.VersionCheckThread;
/*      */ import net.optifine.config.GlVersion;
/*      */ import net.optifine.gui.GuiMessage;
/*      */ import net.optifine.reflect.Reflector;
/*      */ import net.optifine.reflect.ReflectorForge;
/*      */ import net.optifine.shaders.Shaders;
/*      */ import net.optifine.util.DisplayModeComparator;
/*      */ import net.optifine.util.PropertiesOrdered;
/*      */ import net.optifine.util.TextureUtils;
/*      */ import net.optifine.util.TimedEvent;
/*      */ import org.apache.commons.io.IOUtils;
/*      */ import org.apache.logging.log4j.LogManager;
/*      */ import org.apache.logging.log4j.Logger;
/*      */ import org.lwjgl.LWJGLException;
/*      */ import org.lwjgl.Sys;
/*      */ import org.lwjgl.opengl.Display;
/*      */ import org.lwjgl.opengl.DisplayMode;
/*      */ import org.lwjgl.opengl.GL11;
/*      */ import org.lwjgl.opengl.GL30;
/*      */ import org.lwjgl.opengl.GLContext;
/*      */ import org.lwjgl.opengl.PixelFormat;
/*      */ 
/*      */ public class Config
/*      */ {
/*      */   public static final String OF_NAME = "OptiFine";
/*      */   public static final String MC_VERSION = "1.8.9";
/*      */   public static final String OF_EDITION = "HD_U";
/*      */   public static final String OF_RELEASE = "M5";
/*      */   public static final String VERSION = "OptiFine_1.8.9_HD_U_M5";
/*   85 */   private static String build = null;
/*   86 */   private static String newRelease = null;
/*      */   private static boolean notify64BitJava = false;
/*   88 */   public static String openGlVersion = null;
/*   89 */   public static String openGlRenderer = null;
/*   90 */   public static String openGlVendor = null;
/*   91 */   public static String[] openGlExtensions = null;
/*   92 */   public static GlVersion glVersion = null;
/*   93 */   public static GlVersion glslVersion = null;
/*   94 */   public static int minecraftVersionInt = -1;
/*      */   public static boolean fancyFogAvailable = false;
/*      */   public static boolean occlusionAvailable = false;
/*   97 */   private static GameSettings gameSettings = null;
/*   98 */   private static Minecraft minecraft = Minecraft.getMinecraft();
/*      */   private static boolean initialized = false;
/*  100 */   private static Thread minecraftThread = null;
/*  101 */   private static DisplayMode desktopDisplayMode = null;
/*  102 */   private static DisplayMode[] displayModes = null;
/*  103 */   private static int antialiasingLevel = 0;
/*  104 */   private static int availableProcessors = 0;
/*      */   public static boolean zoomMode = false;
/*      */   public static boolean zoomSmoothCamera = false;
/*  107 */   private static int texturePackClouds = 0;
/*      */   public static boolean waterOpacityChanged = false;
/*      */   private static boolean fullscreenModeChecked = false;
/*      */   private static boolean desktopModeChecked = false;
/*  111 */   private static DefaultResourcePack defaultResourcePackLazy = null;
/*  112 */   public static final Float DEF_ALPHA_FUNC_LEVEL = Float.valueOf(0.1F);
/*  113 */   private static final Logger LOGGER = LogManager.getLogger();
/*  114 */   public static final boolean logDetail = System.getProperty("log.detail", "false").equals("true");
/*  115 */   private static String mcDebugLast = null;
/*  116 */   private static int fpsMinLast = 0;
/*      */   public static float renderPartialTicks;
/*      */   
/*      */   public static String getVersion() {
/*  120 */     return "OptiFine_1.8.9_HD_U_M5";
/*      */   }
/*      */   
/*      */   public static String getVersionDebug() {
/*  124 */     StringBuffer stringbuffer = new StringBuffer(32);
/*      */     
/*  126 */     if (isDynamicLights()) {
/*  127 */       stringbuffer.append("DL: ");
/*  128 */       stringbuffer.append(String.valueOf(DynamicLights.getCount()));
/*  129 */       stringbuffer.append(", ");
/*      */     } 
/*      */     
/*  132 */     stringbuffer.append("OptiFine_1.8.9_HD_U_M5");
/*  133 */     String s = Shaders.getShaderPackName();
/*      */     
/*  135 */     if (s != null) {
/*  136 */       stringbuffer.append(", ");
/*  137 */       stringbuffer.append(s);
/*      */     } 
/*      */     
/*  140 */     return stringbuffer.toString();
/*      */   }
/*      */   
/*      */   public static void initGameSettings(GameSettings p_initGameSettings_0_) {
/*  144 */     if (gameSettings == null) {
/*  145 */       gameSettings = p_initGameSettings_0_;
/*  146 */       desktopDisplayMode = Display.getDesktopDisplayMode();
/*  147 */       updateAvailableProcessors();
/*  148 */       ReflectorForge.putLaunchBlackboard("optifine.ForgeSplashCompatible", Boolean.TRUE);
/*      */     } 
/*      */   }
/*      */   
/*      */   public static void initDisplay() {
/*  153 */     checkInitialized();
/*  154 */     antialiasingLevel = gameSettings.ofAaLevel;
/*  155 */     checkDisplaySettings();
/*  156 */     checkDisplayMode();
/*  157 */     minecraftThread = Thread.currentThread();
/*  158 */     updateThreadPriorities();
/*  159 */     Shaders.startup(Minecraft.getMinecraft());
/*      */   }
/*      */   
/*      */   public static void checkInitialized() {
/*  163 */     if (!initialized && 
/*  164 */       Display.isCreated()) {
/*  165 */       initialized = true;
/*  166 */       checkOpenGlCaps();
/*  167 */       startVersionCheckThread();
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   private static void checkOpenGlCaps() {
/*  173 */     log("");
/*  174 */     log(getVersion());
/*  175 */     log("Build: " + getBuild());
/*  176 */     log("OS: " + System.getProperty("os.name") + " (" + System.getProperty("os.arch") + ") version " + System.getProperty("os.version"));
/*  177 */     log("Java: " + System.getProperty("java.version") + ", " + System.getProperty("java.vendor"));
/*  178 */     log("VM: " + System.getProperty("java.vm.name") + " (" + System.getProperty("java.vm.info") + "), " + System.getProperty("java.vm.vendor"));
/*  179 */     log("LWJGL: " + Sys.getVersion());
/*  180 */     openGlVersion = GL11.glGetString(7938);
/*  181 */     openGlRenderer = GL11.glGetString(7937);
/*  182 */     openGlVendor = GL11.glGetString(7936);
/*  183 */     log("OpenGL: " + openGlRenderer + ", version " + openGlVersion + ", " + openGlVendor);
/*  184 */     log("OpenGL Version: " + getOpenGlVersionString());
/*      */     
/*  186 */     if (!(GLContext.getCapabilities()).OpenGL12) {
/*  187 */       log("OpenGL Mipmap levels: Not available (GL12.GL_TEXTURE_MAX_LEVEL)");
/*      */     }
/*      */     
/*  190 */     fancyFogAvailable = (GLContext.getCapabilities()).GL_NV_fog_distance;
/*      */     
/*  192 */     if (!fancyFogAvailable) {
/*  193 */       log("OpenGL Fancy fog: Not available (GL_NV_fog_distance)");
/*      */     }
/*      */     
/*  196 */     occlusionAvailable = (GLContext.getCapabilities()).GL_ARB_occlusion_query;
/*      */     
/*  198 */     if (!occlusionAvailable) {
/*  199 */       log("OpenGL Occlussion culling: Not available (GL_ARB_occlusion_query)");
/*      */     }
/*      */     
/*  202 */     int i = TextureUtils.getGLMaximumTextureSize();
/*  203 */     dbg("Maximum texture size: " + i + "x" + i);
/*      */   }
/*      */   
/*      */   public static String getBuild() {
/*  207 */     if (build == null) {
/*      */       try {
/*  209 */         InputStream inputstream = Config.class.getResourceAsStream("/buildof.txt");
/*      */         
/*  211 */         if (inputstream == null) {
/*  212 */           return null;
/*      */         }
/*      */         
/*  215 */         build = readLines(inputstream)[0];
/*  216 */       } catch (Exception exception) {
/*  217 */         warn(exception.getClass().getName() + ": " + exception.getMessage());
/*  218 */         build = "";
/*      */       } 
/*      */     }
/*      */     
/*  222 */     return build;
/*      */   }
/*      */   
/*      */   public static boolean isFancyFogAvailable() {
/*  226 */     return fancyFogAvailable;
/*      */   }
/*      */   
/*      */   public static boolean isOcclusionAvailable() {
/*  230 */     return occlusionAvailable;
/*      */   }
/*      */   
/*      */   public static int getMinecraftVersionInt() {
/*  234 */     if (minecraftVersionInt < 0) {
/*  235 */       String[] astring = tokenize("1.8.9", ".");
/*  236 */       int i = 0;
/*      */       
/*  238 */       if (astring.length > 0) {
/*  239 */         i += 10000 * parseInt(astring[0], 0);
/*      */       }
/*      */       
/*  242 */       if (astring.length > 1) {
/*  243 */         i += 100 * parseInt(astring[1], 0);
/*      */       }
/*      */       
/*  246 */       if (astring.length > 2) {
/*  247 */         i += 1 * parseInt(astring[2], 0);
/*      */       }
/*      */       
/*  250 */       minecraftVersionInt = i;
/*      */     } 
/*      */     
/*  253 */     return minecraftVersionInt;
/*      */   }
/*      */   
/*      */   public static String getOpenGlVersionString() {
/*  257 */     GlVersion glversion = getGlVersion();
/*  258 */     String s = glversion.getMajor() + "." + glversion.getMinor() + "." + glversion.getRelease();
/*  259 */     return s;
/*      */   }
/*      */   
/*      */   private static GlVersion getGlVersionLwjgl() {
/*  263 */     return (GLContext.getCapabilities()).OpenGL44 ? new GlVersion(4, 4) : ((GLContext.getCapabilities()).OpenGL43 ? new GlVersion(4, 3) : ((GLContext.getCapabilities()).OpenGL42 ? new GlVersion(4, 2) : ((GLContext.getCapabilities()).OpenGL41 ? new GlVersion(4, 1) : ((GLContext.getCapabilities()).OpenGL40 ? new GlVersion(4, 0) : ((GLContext.getCapabilities()).OpenGL33 ? new GlVersion(3, 3) : ((GLContext.getCapabilities()).OpenGL32 ? new GlVersion(3, 2) : ((GLContext.getCapabilities()).OpenGL31 ? new GlVersion(3, 1) : ((GLContext.getCapabilities()).OpenGL30 ? new GlVersion(3, 0) : ((GLContext.getCapabilities()).OpenGL21 ? new GlVersion(2, 1) : ((GLContext.getCapabilities()).OpenGL20 ? new GlVersion(2, 0) : ((GLContext.getCapabilities()).OpenGL15 ? new GlVersion(1, 5) : ((GLContext.getCapabilities()).OpenGL14 ? new GlVersion(1, 4) : ((GLContext.getCapabilities()).OpenGL13 ? new GlVersion(1, 3) : ((GLContext.getCapabilities()).OpenGL12 ? new GlVersion(1, 2) : ((GLContext.getCapabilities()).OpenGL11 ? new GlVersion(1, 1) : new GlVersion(1, 0))))))))))))))));
/*      */   }
/*      */   
/*      */   public static GlVersion getGlVersion() {
/*  267 */     if (glVersion == null) {
/*  268 */       String s = GL11.glGetString(7938);
/*  269 */       glVersion = parseGlVersion(s, null);
/*      */       
/*  271 */       if (glVersion == null) {
/*  272 */         glVersion = getGlVersionLwjgl();
/*      */       }
/*      */       
/*  275 */       if (glVersion == null) {
/*  276 */         glVersion = new GlVersion(1, 0);
/*      */       }
/*      */     } 
/*      */     
/*  280 */     return glVersion;
/*      */   }
/*      */   
/*      */   public static GlVersion getGlslVersion() {
/*  284 */     if (glslVersion == null) {
/*  285 */       String s = GL11.glGetString(35724);
/*  286 */       glslVersion = parseGlVersion(s, null);
/*      */       
/*  288 */       if (glslVersion == null) {
/*  289 */         glslVersion = new GlVersion(1, 10);
/*      */       }
/*      */     } 
/*      */     
/*  293 */     return glslVersion;
/*      */   }
/*      */   
/*      */   public static GlVersion parseGlVersion(String p_parseGlVersion_0_, GlVersion p_parseGlVersion_1_) {
/*      */     try {
/*  298 */       if (p_parseGlVersion_0_ == null) {
/*  299 */         return p_parseGlVersion_1_;
/*      */       }
/*  301 */       Pattern pattern = Pattern.compile("([0-9]+)\\.([0-9]+)(\\.([0-9]+))?(.+)?");
/*  302 */       Matcher matcher = pattern.matcher(p_parseGlVersion_0_);
/*      */       
/*  304 */       if (!matcher.matches()) {
/*  305 */         return p_parseGlVersion_1_;
/*      */       }
/*  307 */       int i = Integer.parseInt(matcher.group(1));
/*  308 */       int j = Integer.parseInt(matcher.group(2));
/*  309 */       int k = (matcher.group(4) != null) ? Integer.parseInt(matcher.group(4)) : 0;
/*  310 */       String s = matcher.group(5);
/*  311 */       return new GlVersion(i, j, k, s);
/*      */     
/*      */     }
/*  314 */     catch (Exception exception) {
/*  315 */       exception.printStackTrace();
/*  316 */       return p_parseGlVersion_1_;
/*      */     } 
/*      */   }
/*      */   
/*      */   public static String[] getOpenGlExtensions() {
/*  321 */     if (openGlExtensions == null) {
/*  322 */       openGlExtensions = detectOpenGlExtensions();
/*      */     }
/*      */     
/*  325 */     return openGlExtensions;
/*      */   }
/*      */   
/*      */   private static String[] detectOpenGlExtensions() {
/*      */     try {
/*  330 */       GlVersion glversion = getGlVersion();
/*      */       
/*  332 */       if (glversion.getMajor() >= 3) {
/*  333 */         int i = GL11.glGetInteger(33309);
/*      */         
/*  335 */         if (i > 0) {
/*  336 */           String[] astring = new String[i];
/*      */           
/*  338 */           for (int j = 0; j < i; j++) {
/*  339 */             astring[j] = GL30.glGetStringi(7939, j);
/*      */           }
/*      */           
/*  342 */           return astring;
/*      */         } 
/*      */       } 
/*  345 */     } catch (Exception exception1) {
/*  346 */       exception1.printStackTrace();
/*      */     } 
/*      */     
/*      */     try {
/*  350 */       String s = GL11.glGetString(7939);
/*  351 */       String[] astring1 = s.split(" ");
/*  352 */       return astring1;
/*  353 */     } catch (Exception exception) {
/*  354 */       exception.printStackTrace();
/*  355 */       return new String[0];
/*      */     } 
/*      */   }
/*      */   
/*      */   public static void updateThreadPriorities() {
/*  360 */     updateAvailableProcessors();
/*  361 */     int i = 8;
/*      */     
/*  363 */     if (isSingleProcessor()) {
/*  364 */       if (isSmoothWorld()) {
/*  365 */         minecraftThread.setPriority(10);
/*  366 */         setThreadPriority("Server thread", 1);
/*      */       } else {
/*  368 */         minecraftThread.setPriority(5);
/*  369 */         setThreadPriority("Server thread", 5);
/*      */       } 
/*      */     } else {
/*  372 */       minecraftThread.setPriority(10);
/*  373 */       setThreadPriority("Server thread", 5);
/*      */     } 
/*      */   }
/*      */   
/*      */   private static void setThreadPriority(String p_setThreadPriority_0_, int p_setThreadPriority_1_) {
/*      */     try {
/*  379 */       ThreadGroup threadgroup = Thread.currentThread().getThreadGroup();
/*      */       
/*  381 */       if (threadgroup == null) {
/*      */         return;
/*      */       }
/*      */       
/*  385 */       int i = (threadgroup.activeCount() + 10) * 2;
/*  386 */       Thread[] athread = new Thread[i];
/*  387 */       threadgroup.enumerate(athread, false);
/*      */       
/*  389 */       for (int j = 0; j < athread.length; j++) {
/*  390 */         Thread thread = athread[j];
/*      */         
/*  392 */         if (thread != null && thread.getName().startsWith(p_setThreadPriority_0_)) {
/*  393 */           thread.setPriority(p_setThreadPriority_1_);
/*      */         }
/*      */       } 
/*  396 */     } catch (Throwable throwable) {
/*  397 */       warn(String.valueOf(throwable.getClass().getName()) + ": " + throwable.getMessage());
/*      */     } 
/*      */   }
/*      */   
/*      */   public static boolean isMinecraftThread() {
/*  402 */     return (Thread.currentThread() == minecraftThread);
/*      */   }
/*      */   
/*      */   private static void startVersionCheckThread() {
/*  406 */     VersionCheckThread versioncheckthread = new VersionCheckThread();
/*  407 */     versioncheckthread.start();
/*      */   }
/*      */   
/*      */   public static boolean isMipmaps() {
/*  411 */     return (gameSettings.mipmapLevels > 0);
/*      */   }
/*      */   
/*      */   public static int getMipmapLevels() {
/*  415 */     return gameSettings.mipmapLevels;
/*      */   }
/*      */   
/*      */   public static int getMipmapType() {
/*  419 */     switch (gameSettings.ofMipmapType) {
/*      */       case 0:
/*  421 */         return 9986;
/*      */       
/*      */       case 1:
/*  424 */         return 9986;
/*      */       
/*      */       case 2:
/*  427 */         if (isMultiTexture()) {
/*  428 */           return 9985;
/*      */         }
/*      */         
/*  431 */         return 9986;
/*      */       
/*      */       case 3:
/*  434 */         if (isMultiTexture()) {
/*  435 */           return 9987;
/*      */         }
/*      */         
/*  438 */         return 9986;
/*      */     } 
/*      */     
/*  441 */     return 9986;
/*      */   }
/*      */ 
/*      */   
/*      */   public static boolean isUseAlphaFunc() {
/*  446 */     float f = getAlphaFuncLevel();
/*  447 */     return (f > DEF_ALPHA_FUNC_LEVEL.floatValue() + 1.0E-5F);
/*      */   }
/*      */   
/*      */   public static float getAlphaFuncLevel() {
/*  451 */     return DEF_ALPHA_FUNC_LEVEL.floatValue();
/*      */   }
/*      */   
/*      */   public static boolean isFogFancy() {
/*  455 */     return !isFancyFogAvailable() ? false : ((gameSettings.ofFogType == 2));
/*      */   }
/*      */   
/*      */   public static boolean isFogFast() {
/*  459 */     return (gameSettings.ofFogType == 1);
/*      */   }
/*      */   
/*      */   public static boolean isFogOff() {
/*  463 */     return (gameSettings.ofFogType == 3);
/*      */   }
/*      */   
/*      */   public static boolean isFogOn() {
/*  467 */     return (gameSettings.ofFogType != 3);
/*      */   }
/*      */   
/*      */   public static float getFogStart() {
/*  471 */     return gameSettings.ofFogStart;
/*      */   }
/*      */   
/*      */   public static void detail(String p_detail_0_) {
/*  475 */     if (logDetail) {
/*  476 */       LOGGER.info("[OptiFine] " + p_detail_0_);
/*      */     }
/*      */   }
/*      */   
/*      */   public static void dbg(String p_dbg_0_) {
/*  481 */     LOGGER.info("[OptiFine] " + p_dbg_0_);
/*      */   }
/*      */   
/*      */   public static void warn(String p_warn_0_) {
/*  485 */     LOGGER.warn("[OptiFine] " + p_warn_0_);
/*      */   }
/*      */   
/*      */   public static void error(String p_error_0_) {
/*  489 */     LOGGER.error("[OptiFine] " + p_error_0_);
/*      */   }
/*      */   
/*      */   public static void log(String p_log_0_) {
/*  493 */     dbg(p_log_0_);
/*      */   }
/*      */   
/*      */   public static int getUpdatesPerFrame() {
/*  497 */     return gameSettings.ofChunkUpdates;
/*      */   }
/*      */   
/*      */   public static boolean isDynamicUpdates() {
/*  501 */     return gameSettings.ofChunkUpdatesDynamic;
/*      */   }
/*      */   
/*      */   public static boolean isRainFancy() {
/*  505 */     return (gameSettings.ofRain == 0) ? gameSettings.fancyGraphics : ((gameSettings.ofRain == 2));
/*      */   }
/*      */   
/*      */   public static boolean isRainOff() {
/*  509 */     return (gameSettings.ofRain == 3);
/*      */   }
/*      */   
/*      */   public static boolean isCloudsFancy() {
/*  513 */     return (gameSettings.ofClouds != 0) ? ((gameSettings.ofClouds == 2)) : ((isShaders() && !Shaders.shaderPackClouds.isDefault()) ? Shaders.shaderPackClouds.isFancy() : ((texturePackClouds != 0) ? ((texturePackClouds == 2)) : gameSettings.fancyGraphics));
/*      */   }
/*      */   
/*      */   public static boolean isCloudsOff() {
/*  517 */     return (gameSettings.ofClouds != 0) ? ((gameSettings.ofClouds == 3)) : ((isShaders() && !Shaders.shaderPackClouds.isDefault()) ? Shaders.shaderPackClouds.isOff() : ((texturePackClouds != 0) ? ((texturePackClouds == 3)) : false));
/*      */   }
/*      */   
/*      */   public static void updateTexturePackClouds() {
/*  521 */     texturePackClouds = 0;
/*  522 */     IResourceManager iresourcemanager = getResourceManager();
/*      */     
/*  524 */     if (iresourcemanager != null) {
/*      */       try {
/*  526 */         InputStream inputstream = iresourcemanager.getResource(new ResourceLocation("mcpatcher/color.properties")).getInputStream();
/*      */         
/*  528 */         if (inputstream == null) {
/*      */           return;
/*      */         }
/*      */         
/*  532 */         PropertiesOrdered propertiesOrdered = new PropertiesOrdered();
/*  533 */         propertiesOrdered.load(inputstream);
/*  534 */         inputstream.close();
/*  535 */         String s = propertiesOrdered.getProperty("clouds");
/*      */         
/*  537 */         if (s == null) {
/*      */           return;
/*      */         }
/*      */         
/*  541 */         dbg("Texture pack clouds: " + s);
/*  542 */         s = s.toLowerCase();
/*      */         
/*  544 */         if (s.equals("fast")) {
/*  545 */           texturePackClouds = 1;
/*      */         }
/*      */         
/*  548 */         if (s.equals("fancy")) {
/*  549 */           texturePackClouds = 2;
/*      */         }
/*      */         
/*  552 */         if (s.equals("off")) {
/*  553 */           texturePackClouds = 3;
/*      */         }
/*  555 */       } catch (Exception exception) {}
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public static ModelManager getModelManager() {
/*  562 */     return (minecraft.getRenderItem()).modelManager;
/*      */   }
/*      */   
/*      */   public static boolean isTreesFancy() {
/*  566 */     return (gameSettings.ofTrees == 0) ? gameSettings.fancyGraphics : ((gameSettings.ofTrees != 1));
/*      */   }
/*      */   
/*      */   public static boolean isTreesSmart() {
/*  570 */     return (gameSettings.ofTrees == 4);
/*      */   }
/*      */   
/*      */   public static boolean isCullFacesLeaves() {
/*  574 */     return (gameSettings.ofTrees == 0) ? (!gameSettings.fancyGraphics) : ((gameSettings.ofTrees == 4));
/*      */   }
/*      */   
/*      */   public static boolean isDroppedItemsFancy() {
/*  578 */     return (gameSettings.ofDroppedItems == 0) ? gameSettings.fancyGraphics : ((gameSettings.ofDroppedItems == 2));
/*      */   }
/*      */   
/*      */   public static int limit(int p_limit_0_, int p_limit_1_, int p_limit_2_) {
/*  582 */     return (p_limit_0_ < p_limit_1_) ? p_limit_1_ : ((p_limit_0_ > p_limit_2_) ? p_limit_2_ : p_limit_0_);
/*      */   }
/*      */   
/*      */   public static float limit(float p_limit_0_, float p_limit_1_, float p_limit_2_) {
/*  586 */     return (p_limit_0_ < p_limit_1_) ? p_limit_1_ : ((p_limit_0_ > p_limit_2_) ? p_limit_2_ : p_limit_0_);
/*      */   }
/*      */   
/*      */   public static double limit(double p_limit_0_, double p_limit_2_, double p_limit_4_) {
/*  590 */     return (p_limit_0_ < p_limit_2_) ? p_limit_2_ : ((p_limit_0_ > p_limit_4_) ? p_limit_4_ : p_limit_0_);
/*      */   }
/*      */   
/*      */   public static float limitTo1(float p_limitTo1_0_) {
/*  594 */     return (p_limitTo1_0_ < 0.0F) ? 0.0F : ((p_limitTo1_0_ > 1.0F) ? 1.0F : p_limitTo1_0_);
/*      */   }
/*      */   
/*      */   public static boolean isAnimatedWater() {
/*  598 */     return (gameSettings.ofAnimatedWater != 2);
/*      */   }
/*      */   
/*      */   public static boolean isGeneratedWater() {
/*  602 */     return (gameSettings.ofAnimatedWater == 1);
/*      */   }
/*      */   
/*      */   public static boolean isAnimatedPortal() {
/*  606 */     return gameSettings.ofAnimatedPortal;
/*      */   }
/*      */   
/*      */   public static boolean isAnimatedLava() {
/*  610 */     return (gameSettings.ofAnimatedLava != 2);
/*      */   }
/*      */   
/*      */   public static boolean isGeneratedLava() {
/*  614 */     return (gameSettings.ofAnimatedLava == 1);
/*      */   }
/*      */   
/*      */   public static boolean isAnimatedFire() {
/*  618 */     return gameSettings.ofAnimatedFire;
/*      */   }
/*      */   
/*      */   public static boolean isAnimatedRedstone() {
/*  622 */     return gameSettings.ofAnimatedRedstone;
/*      */   }
/*      */   
/*      */   public static boolean isAnimatedExplosion() {
/*  626 */     return gameSettings.ofAnimatedExplosion;
/*      */   }
/*      */   
/*      */   public static boolean isAnimatedFlame() {
/*  630 */     return gameSettings.ofAnimatedFlame;
/*      */   }
/*      */   
/*      */   public static boolean isAnimatedSmoke() {
/*  634 */     return gameSettings.ofAnimatedSmoke;
/*      */   }
/*      */   
/*      */   public static boolean isVoidParticles() {
/*  638 */     return gameSettings.ofVoidParticles;
/*      */   }
/*      */   
/*      */   public static boolean isWaterParticles() {
/*  642 */     return gameSettings.ofWaterParticles;
/*      */   }
/*      */   
/*      */   public static boolean isRainSplash() {
/*  646 */     return gameSettings.ofRainSplash;
/*      */   }
/*      */   
/*      */   public static boolean isPortalParticles() {
/*  650 */     return gameSettings.ofPortalParticles;
/*      */   }
/*      */   
/*      */   public static boolean isPotionParticles() {
/*  654 */     return gameSettings.ofPotionParticles;
/*      */   }
/*      */   
/*      */   public static boolean isFireworkParticles() {
/*  658 */     return gameSettings.ofFireworkParticles;
/*      */   }
/*      */   
/*      */   public static float getAmbientOcclusionLevel() {
/*  662 */     return (isShaders() && Shaders.aoLevel >= 0.0F) ? Shaders.aoLevel : gameSettings.ofAoLevel;
/*      */   }
/*      */   
/*      */   public static String listToString(List p_listToString_0_) {
/*  666 */     return listToString(p_listToString_0_, ", ");
/*      */   }
/*      */   
/*      */   public static String listToString(List p_listToString_0_, String p_listToString_1_) {
/*  670 */     if (p_listToString_0_ == null) {
/*  671 */       return "";
/*      */     }
/*  673 */     StringBuffer stringbuffer = new StringBuffer(p_listToString_0_.size() * 5);
/*      */     
/*  675 */     for (int i = 0; i < p_listToString_0_.size(); i++) {
/*  676 */       Object object = p_listToString_0_.get(i);
/*      */       
/*  678 */       if (i > 0) {
/*  679 */         stringbuffer.append(p_listToString_1_);
/*      */       }
/*      */       
/*  682 */       stringbuffer.append(String.valueOf(object));
/*      */     } 
/*      */     
/*  685 */     return stringbuffer.toString();
/*      */   }
/*      */ 
/*      */   
/*      */   public static String arrayToString(Object[] p_arrayToString_0_) {
/*  690 */     return arrayToString(p_arrayToString_0_, ", ");
/*      */   }
/*      */   
/*      */   public static String arrayToString(Object[] p_arrayToString_0_, String p_arrayToString_1_) {
/*  694 */     if (p_arrayToString_0_ == null) {
/*  695 */       return "";
/*      */     }
/*  697 */     StringBuffer stringbuffer = new StringBuffer(p_arrayToString_0_.length * 5);
/*      */     
/*  699 */     for (int i = 0; i < p_arrayToString_0_.length; i++) {
/*  700 */       Object object = p_arrayToString_0_[i];
/*      */       
/*  702 */       if (i > 0) {
/*  703 */         stringbuffer.append(p_arrayToString_1_);
/*      */       }
/*      */       
/*  706 */       stringbuffer.append(String.valueOf(object));
/*      */     } 
/*      */     
/*  709 */     return stringbuffer.toString();
/*      */   }
/*      */ 
/*      */   
/*      */   public static String arrayToString(int[] p_arrayToString_0_) {
/*  714 */     return arrayToString(p_arrayToString_0_, ", ");
/*      */   }
/*      */   
/*      */   public static String arrayToString(int[] p_arrayToString_0_, String p_arrayToString_1_) {
/*  718 */     if (p_arrayToString_0_ == null) {
/*  719 */       return "";
/*      */     }
/*  721 */     StringBuffer stringbuffer = new StringBuffer(p_arrayToString_0_.length * 5);
/*      */     
/*  723 */     for (int i = 0; i < p_arrayToString_0_.length; i++) {
/*  724 */       int j = p_arrayToString_0_[i];
/*      */       
/*  726 */       if (i > 0) {
/*  727 */         stringbuffer.append(p_arrayToString_1_);
/*      */       }
/*      */       
/*  730 */       stringbuffer.append(String.valueOf(j));
/*      */     } 
/*      */     
/*  733 */     return stringbuffer.toString();
/*      */   }
/*      */ 
/*      */   
/*      */   public static String arrayToString(float[] p_arrayToString_0_) {
/*  738 */     return arrayToString(p_arrayToString_0_, ", ");
/*      */   }
/*      */   
/*      */   public static String arrayToString(float[] p_arrayToString_0_, String p_arrayToString_1_) {
/*  742 */     if (p_arrayToString_0_ == null) {
/*  743 */       return "";
/*      */     }
/*  745 */     StringBuffer stringbuffer = new StringBuffer(p_arrayToString_0_.length * 5);
/*      */     
/*  747 */     for (int i = 0; i < p_arrayToString_0_.length; i++) {
/*  748 */       float f = p_arrayToString_0_[i];
/*      */       
/*  750 */       if (i > 0) {
/*  751 */         stringbuffer.append(p_arrayToString_1_);
/*      */       }
/*      */       
/*  754 */       stringbuffer.append(String.valueOf(f));
/*      */     } 
/*      */     
/*  757 */     return stringbuffer.toString();
/*      */   }
/*      */ 
/*      */   
/*      */   public static Minecraft getMinecraft() {
/*  762 */     return minecraft;
/*      */   }
/*      */   
/*      */   public static TextureManager getTextureManager() {
/*  766 */     return minecraft.getTextureManager();
/*      */   }
/*      */   
/*      */   public static IResourceManager getResourceManager() {
/*  770 */     return minecraft.getResourceManager();
/*      */   }
/*      */   
/*      */   public static InputStream getResourceStream(ResourceLocation p_getResourceStream_0_) throws IOException {
/*  774 */     return getResourceStream(minecraft.getResourceManager(), p_getResourceStream_0_);
/*      */   }
/*      */   
/*      */   public static InputStream getResourceStream(IResourceManager p_getResourceStream_0_, ResourceLocation p_getResourceStream_1_) throws IOException {
/*  778 */     IResource iresource = p_getResourceStream_0_.getResource(p_getResourceStream_1_);
/*  779 */     return (iresource == null) ? null : iresource.getInputStream();
/*      */   }
/*      */   
/*      */   public static IResource getResource(ResourceLocation p_getResource_0_) throws IOException {
/*  783 */     return minecraft.getResourceManager().getResource(p_getResource_0_);
/*      */   }
/*      */   
/*      */   public static boolean hasResource(ResourceLocation p_hasResource_0_) {
/*  787 */     if (p_hasResource_0_ == null) {
/*  788 */       return false;
/*      */     }
/*  790 */     IResourcePack iresourcepack = getDefiningResourcePack(p_hasResource_0_);
/*  791 */     return (iresourcepack != null);
/*      */   }
/*      */ 
/*      */   
/*      */   public static boolean hasResource(IResourceManager p_hasResource_0_, ResourceLocation p_hasResource_1_) {
/*      */     try {
/*  797 */       IResource iresource = p_hasResource_0_.getResource(p_hasResource_1_);
/*  798 */       return (iresource != null);
/*  799 */     } catch (IOException var3) {
/*  800 */       return false;
/*      */     } 
/*      */   }
/*      */   
/*      */   public static IResourcePack[] getResourcePacks() {
/*  805 */     ResourcePackRepository resourcepackrepository = minecraft.getResourcePackRepository();
/*  806 */     List list = resourcepackrepository.getRepositoryEntries();
/*  807 */     List<IResourcePack> list1 = new ArrayList();
/*      */     
/*  809 */     for (Object resourcepackrepository$entry0 : list) {
/*  810 */       ResourcePackRepository.Entry resourcepackrepository$entry = (ResourcePackRepository.Entry)resourcepackrepository$entry0;
/*  811 */       list1.add(resourcepackrepository$entry.getResourcePack());
/*      */     } 
/*      */     
/*  814 */     if (resourcepackrepository.getResourcePackInstance() != null) {
/*  815 */       list1.add(resourcepackrepository.getResourcePackInstance());
/*      */     }
/*      */     
/*  818 */     IResourcePack[] airesourcepack = list1.<IResourcePack>toArray(new IResourcePack[list1.size()]);
/*  819 */     return airesourcepack;
/*      */   }
/*      */   
/*      */   public static String getResourcePackNames() {
/*  823 */     if (minecraft.getResourcePackRepository() == null) {
/*  824 */       return "";
/*      */     }
/*  826 */     IResourcePack[] airesourcepack = getResourcePacks();
/*      */     
/*  828 */     if (airesourcepack.length <= 0) {
/*  829 */       return getDefaultResourcePack().getPackName();
/*      */     }
/*  831 */     String[] astring = new String[airesourcepack.length];
/*      */     
/*  833 */     for (int i = 0; i < airesourcepack.length; i++) {
/*  834 */       astring[i] = airesourcepack[i].getPackName();
/*      */     }
/*      */     
/*  837 */     String s = arrayToString((Object[])astring);
/*  838 */     return s;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public static DefaultResourcePack getDefaultResourcePack() {
/*  844 */     if (defaultResourcePackLazy == null) {
/*  845 */       Minecraft minecraft = Minecraft.getMinecraft();
/*  846 */       defaultResourcePackLazy = (DefaultResourcePack)Reflector.getFieldValue(minecraft, Reflector.Minecraft_defaultResourcePack);
/*      */       
/*  848 */       if (defaultResourcePackLazy == null) {
/*  849 */         ResourcePackRepository resourcepackrepository = minecraft.getResourcePackRepository();
/*      */         
/*  851 */         if (resourcepackrepository != null) {
/*  852 */           defaultResourcePackLazy = (DefaultResourcePack)resourcepackrepository.rprDefaultResourcePack;
/*      */         }
/*      */       } 
/*      */     } 
/*      */     
/*  857 */     return defaultResourcePackLazy;
/*      */   }
/*      */   
/*      */   public static boolean isFromDefaultResourcePack(ResourceLocation p_isFromDefaultResourcePack_0_) {
/*  861 */     IResourcePack iresourcepack = getDefiningResourcePack(p_isFromDefaultResourcePack_0_);
/*  862 */     return (iresourcepack == getDefaultResourcePack());
/*      */   }
/*      */   
/*      */   public static IResourcePack getDefiningResourcePack(ResourceLocation p_getDefiningResourcePack_0_) {
/*  866 */     ResourcePackRepository resourcepackrepository = minecraft.getResourcePackRepository();
/*  867 */     IResourcePack iresourcepack = resourcepackrepository.getResourcePackInstance();
/*      */     
/*  869 */     if (iresourcepack != null && iresourcepack.resourceExists(p_getDefiningResourcePack_0_)) {
/*  870 */       return iresourcepack;
/*      */     }
/*  872 */     List<ResourcePackRepository.Entry> list = resourcepackrepository.repositoryEntries;
/*      */     
/*  874 */     for (int i = list.size() - 1; i >= 0; i--) {
/*  875 */       ResourcePackRepository.Entry resourcepackrepository$entry = list.get(i);
/*  876 */       IResourcePack iresourcepack1 = resourcepackrepository$entry.getResourcePack();
/*      */       
/*  878 */       if (iresourcepack1.resourceExists(p_getDefiningResourcePack_0_)) {
/*  879 */         return iresourcepack1;
/*      */       }
/*      */     } 
/*      */     
/*  883 */     if (getDefaultResourcePack().resourceExists(p_getDefiningResourcePack_0_)) {
/*  884 */       return (IResourcePack)getDefaultResourcePack();
/*      */     }
/*  886 */     return null;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public static RenderGlobal getRenderGlobal() {
/*  892 */     return minecraft.renderGlobal;
/*      */   }
/*      */   
/*      */   public static boolean isBetterGrass() {
/*  896 */     return (gameSettings.ofBetterGrass != 3);
/*      */   }
/*      */   
/*      */   public static boolean isBetterGrassFancy() {
/*  900 */     return (gameSettings.ofBetterGrass == 2);
/*      */   }
/*      */   
/*      */   public static boolean isWeatherEnabled() {
/*  904 */     return gameSettings.ofWeather;
/*      */   }
/*      */   
/*      */   public static boolean isSkyEnabled() {
/*  908 */     return gameSettings.ofSky;
/*      */   }
/*      */   
/*      */   public static boolean isSunMoonEnabled() {
/*  912 */     return gameSettings.ofSunMoon;
/*      */   }
/*      */   
/*      */   public static boolean isSunTexture() {
/*  916 */     return !isSunMoonEnabled() ? false : (!(isShaders() && !Shaders.isSun()));
/*      */   }
/*      */   
/*      */   public static boolean isMoonTexture() {
/*  920 */     return !isSunMoonEnabled() ? false : (!(isShaders() && !Shaders.isMoon()));
/*      */   }
/*      */   
/*      */   public static boolean isVignetteEnabled() {
/*  924 */     return (isShaders() && !Shaders.isVignette()) ? false : ((gameSettings.ofVignette == 0) ? gameSettings.fancyGraphics : ((gameSettings.ofVignette == 2)));
/*      */   }
/*      */   
/*      */   public static boolean isStarsEnabled() {
/*  928 */     return gameSettings.ofStars;
/*      */   }
/*      */   
/*      */   public static void sleep(long p_sleep_0_) {
/*      */     try {
/*  933 */       Thread.sleep(p_sleep_0_);
/*  934 */     } catch (InterruptedException interruptedexception) {
/*  935 */       interruptedexception.printStackTrace();
/*      */     } 
/*      */   }
/*      */   
/*      */   public static boolean isTimeDayOnly() {
/*  940 */     return (gameSettings.ofTime == 1);
/*      */   }
/*      */   
/*      */   public static boolean isTimeDefault() {
/*  944 */     return (gameSettings.ofTime == 0);
/*      */   }
/*      */   
/*      */   public static boolean isTimeNightOnly() {
/*  948 */     return (gameSettings.ofTime == 2);
/*      */   }
/*      */   
/*      */   public static boolean isClearWater() {
/*  952 */     return gameSettings.ofClearWater;
/*      */   }
/*      */   
/*      */   public static int getAnisotropicFilterLevel() {
/*  956 */     return gameSettings.ofAfLevel;
/*      */   }
/*      */   
/*      */   public static boolean isAnisotropicFiltering() {
/*  960 */     return (getAnisotropicFilterLevel() > 1);
/*      */   }
/*      */   
/*      */   public static int getAntialiasingLevel() {
/*  964 */     return antialiasingLevel;
/*      */   }
/*      */   
/*      */   public static boolean isAntialiasing() {
/*  968 */     return (getAntialiasingLevel() > 0);
/*      */   }
/*      */   
/*      */   public static boolean isAntialiasingConfigured() {
/*  972 */     return ((getGameSettings()).ofAaLevel > 0);
/*      */   }
/*      */   
/*      */   public static boolean isMultiTexture() {
/*  976 */     return (getAnisotropicFilterLevel() > 1) ? true : ((getAntialiasingLevel() > 0));
/*      */   }
/*      */   
/*      */   public static boolean between(int p_between_0_, int p_between_1_, int p_between_2_) {
/*  980 */     return (p_between_0_ >= p_between_1_ && p_between_0_ <= p_between_2_);
/*      */   }
/*      */   
/*      */   public static boolean between(float p_between_0_, float p_between_1_, float p_between_2_) {
/*  984 */     return (p_between_0_ >= p_between_1_ && p_between_0_ <= p_between_2_);
/*      */   }
/*      */   
/*      */   public static boolean isDrippingWaterLava() {
/*  988 */     return gameSettings.ofDrippingWaterLava;
/*      */   }
/*      */   
/*      */   public static boolean isBetterSnow() {
/*  992 */     return gameSettings.ofBetterSnow;
/*      */   }
/*      */   
/*      */   public static Dimension getFullscreenDimension() {
/*  996 */     if (desktopDisplayMode == null)
/*  997 */       return null; 
/*  998 */     if (gameSettings == null) {
/*  999 */       return new Dimension(desktopDisplayMode.getWidth(), desktopDisplayMode.getHeight());
/*      */     }
/* 1001 */     String s = gameSettings.ofFullscreenMode;
/*      */     
/* 1003 */     if (s.equals("Default")) {
/* 1004 */       return new Dimension(desktopDisplayMode.getWidth(), desktopDisplayMode.getHeight());
/*      */     }
/* 1006 */     String[] astring = tokenize(s, " x");
/* 1007 */     return (astring.length < 2) ? new Dimension(desktopDisplayMode.getWidth(), desktopDisplayMode.getHeight()) : new Dimension(parseInt(astring[0], -1), parseInt(astring[1], -1));
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public static int parseInt(String p_parseInt_0_, int p_parseInt_1_) {
/*      */     try {
/* 1014 */       if (p_parseInt_0_ == null) {
/* 1015 */         return p_parseInt_1_;
/*      */       }
/* 1017 */       p_parseInt_0_ = p_parseInt_0_.trim();
/* 1018 */       return Integer.parseInt(p_parseInt_0_);
/*      */     }
/* 1020 */     catch (NumberFormatException var3) {
/* 1021 */       return p_parseInt_1_;
/*      */     } 
/*      */   }
/*      */   
/*      */   public static float parseFloat(String p_parseFloat_0_, float p_parseFloat_1_) {
/*      */     try {
/* 1027 */       if (p_parseFloat_0_ == null) {
/* 1028 */         return p_parseFloat_1_;
/*      */       }
/* 1030 */       p_parseFloat_0_ = p_parseFloat_0_.trim();
/* 1031 */       return Float.parseFloat(p_parseFloat_0_);
/*      */     }
/* 1033 */     catch (NumberFormatException var3) {
/* 1034 */       return p_parseFloat_1_;
/*      */     } 
/*      */   }
/*      */   
/*      */   public static boolean parseBoolean(String p_parseBoolean_0_, boolean p_parseBoolean_1_) {
/*      */     try {
/* 1040 */       if (p_parseBoolean_0_ == null) {
/* 1041 */         return p_parseBoolean_1_;
/*      */       }
/* 1043 */       p_parseBoolean_0_ = p_parseBoolean_0_.trim();
/* 1044 */       return Boolean.parseBoolean(p_parseBoolean_0_);
/*      */     }
/* 1046 */     catch (NumberFormatException var3) {
/* 1047 */       return p_parseBoolean_1_;
/*      */     } 
/*      */   }
/*      */   
/*      */   public static Boolean parseBoolean(String p_parseBoolean_0_, Boolean p_parseBoolean_1_) {
/*      */     try {
/* 1053 */       if (p_parseBoolean_0_ == null) {
/* 1054 */         return p_parseBoolean_1_;
/*      */       }
/* 1056 */       p_parseBoolean_0_ = p_parseBoolean_0_.trim().toLowerCase();
/* 1057 */       return p_parseBoolean_0_.equals("true") ? Boolean.TRUE : (p_parseBoolean_0_.equals("false") ? Boolean.FALSE : p_parseBoolean_1_);
/*      */     }
/* 1059 */     catch (NumberFormatException var3) {
/* 1060 */       return p_parseBoolean_1_;
/*      */     } 
/*      */   }
/*      */   
/*      */   public static String[] tokenize(String p_tokenize_0_, String p_tokenize_1_) {
/* 1065 */     StringTokenizer stringtokenizer = new StringTokenizer(p_tokenize_0_, p_tokenize_1_);
/* 1066 */     List<String> list = new ArrayList();
/*      */     
/* 1068 */     while (stringtokenizer.hasMoreTokens()) {
/* 1069 */       String s = stringtokenizer.nextToken();
/* 1070 */       list.add(s);
/*      */     } 
/*      */     
/* 1073 */     String[] astring = list.<String>toArray(new String[list.size()]);
/* 1074 */     return astring;
/*      */   }
/*      */   
/*      */   public static DisplayMode getDesktopDisplayMode() {
/* 1078 */     return desktopDisplayMode;
/*      */   }
/*      */   
/*      */   public static DisplayMode[] getDisplayModes() {
/* 1082 */     if (displayModes == null) {
/*      */       try {
/* 1084 */         DisplayMode[] adisplaymode = Display.getAvailableDisplayModes();
/* 1085 */         Set<Dimension> set = getDisplayModeDimensions(adisplaymode);
/* 1086 */         List<DisplayMode> list = new ArrayList();
/*      */         
/* 1088 */         for (Dimension dimension : set) {
/* 1089 */           DisplayMode[] adisplaymode1 = getDisplayModes(adisplaymode, dimension);
/* 1090 */           DisplayMode displaymode = getDisplayMode(adisplaymode1, desktopDisplayMode);
/*      */           
/* 1092 */           if (displaymode != null) {
/* 1093 */             list.add(displaymode);
/*      */           }
/*      */         } 
/*      */         
/* 1097 */         DisplayMode[] adisplaymode2 = list.<DisplayMode>toArray(new DisplayMode[list.size()]);
/* 1098 */         Arrays.sort(adisplaymode2, (Comparator<? super DisplayMode>)new DisplayModeComparator());
/* 1099 */         return adisplaymode2;
/* 1100 */       } catch (Exception exception) {
/* 1101 */         exception.printStackTrace();
/* 1102 */         displayModes = new DisplayMode[] { desktopDisplayMode };
/*      */       } 
/*      */     }
/*      */     
/* 1106 */     return displayModes;
/*      */   }
/*      */   
/*      */   public static DisplayMode getLargestDisplayMode() {
/* 1110 */     DisplayMode[] adisplaymode = getDisplayModes();
/*      */     
/* 1112 */     if (adisplaymode != null && adisplaymode.length >= 1) {
/* 1113 */       DisplayMode displaymode = adisplaymode[adisplaymode.length - 1];
/* 1114 */       return (desktopDisplayMode.getWidth() > displaymode.getWidth()) ? desktopDisplayMode : ((desktopDisplayMode.getWidth() == displaymode.getWidth() && desktopDisplayMode.getHeight() > displaymode.getHeight()) ? desktopDisplayMode : displaymode);
/*      */     } 
/* 1116 */     return desktopDisplayMode;
/*      */   }
/*      */ 
/*      */   
/*      */   private static Set<Dimension> getDisplayModeDimensions(DisplayMode[] p_getDisplayModeDimensions_0_) {
/* 1121 */     Set<Dimension> set = new HashSet<>();
/*      */     
/* 1123 */     for (int i = 0; i < p_getDisplayModeDimensions_0_.length; i++) {
/* 1124 */       DisplayMode displaymode = p_getDisplayModeDimensions_0_[i];
/* 1125 */       Dimension dimension = new Dimension(displaymode.getWidth(), displaymode.getHeight());
/* 1126 */       set.add(dimension);
/*      */     } 
/*      */     
/* 1129 */     return set;
/*      */   }
/*      */   
/*      */   private static DisplayMode[] getDisplayModes(DisplayMode[] p_getDisplayModes_0_, Dimension p_getDisplayModes_1_) {
/* 1133 */     List<DisplayMode> list = new ArrayList();
/*      */     
/* 1135 */     for (int i = 0; i < p_getDisplayModes_0_.length; i++) {
/* 1136 */       DisplayMode displaymode = p_getDisplayModes_0_[i];
/*      */       
/* 1138 */       if (displaymode.getWidth() == p_getDisplayModes_1_.getWidth() && displaymode.getHeight() == p_getDisplayModes_1_.getHeight()) {
/* 1139 */         list.add(displaymode);
/*      */       }
/*      */     } 
/*      */     
/* 1143 */     DisplayMode[] adisplaymode = list.<DisplayMode>toArray(new DisplayMode[list.size()]);
/* 1144 */     return adisplaymode;
/*      */   }
/*      */   
/*      */   private static DisplayMode getDisplayMode(DisplayMode[] p_getDisplayMode_0_, DisplayMode p_getDisplayMode_1_) {
/* 1148 */     if (p_getDisplayMode_1_ != null) {
/* 1149 */       for (int i = 0; i < p_getDisplayMode_0_.length; i++) {
/* 1150 */         DisplayMode displaymode = p_getDisplayMode_0_[i];
/*      */         
/* 1152 */         if (displaymode.getBitsPerPixel() == p_getDisplayMode_1_.getBitsPerPixel() && displaymode.getFrequency() == p_getDisplayMode_1_.getFrequency()) {
/* 1153 */           return displaymode;
/*      */         }
/*      */       } 
/*      */     }
/*      */     
/* 1158 */     if (p_getDisplayMode_0_.length <= 0) {
/* 1159 */       return null;
/*      */     }
/* 1161 */     Arrays.sort(p_getDisplayMode_0_, (Comparator<? super DisplayMode>)new DisplayModeComparator());
/* 1162 */     return p_getDisplayMode_0_[p_getDisplayMode_0_.length - 1];
/*      */   }
/*      */ 
/*      */   
/*      */   public static String[] getDisplayModeNames() {
/* 1167 */     DisplayMode[] adisplaymode = getDisplayModes();
/* 1168 */     String[] astring = new String[adisplaymode.length];
/*      */     
/* 1170 */     for (int i = 0; i < adisplaymode.length; i++) {
/* 1171 */       DisplayMode displaymode = adisplaymode[i];
/* 1172 */       String s = displaymode.getWidth() + "x" + displaymode.getHeight();
/* 1173 */       astring[i] = s;
/*      */     } 
/*      */     
/* 1176 */     return astring;
/*      */   }
/*      */   
/*      */   public static DisplayMode getDisplayMode(Dimension p_getDisplayMode_0_) throws LWJGLException {
/* 1180 */     DisplayMode[] adisplaymode = getDisplayModes();
/*      */     
/* 1182 */     for (int i = 0; i < adisplaymode.length; i++) {
/* 1183 */       DisplayMode displaymode = adisplaymode[i];
/*      */       
/* 1185 */       if (displaymode.getWidth() == p_getDisplayMode_0_.width && displaymode.getHeight() == p_getDisplayMode_0_.height) {
/* 1186 */         return displaymode;
/*      */       }
/*      */     } 
/*      */     
/* 1190 */     return desktopDisplayMode;
/*      */   }
/*      */   
/*      */   public static boolean isAnimatedTerrain() {
/* 1194 */     return gameSettings.ofAnimatedTerrain;
/*      */   }
/*      */   
/*      */   public static boolean isAnimatedTextures() {
/* 1198 */     return gameSettings.ofAnimatedTextures;
/*      */   }
/*      */   
/*      */   public static boolean isSwampColors() {
/* 1202 */     return gameSettings.ofSwampColors;
/*      */   }
/*      */   
/*      */   public static boolean isRandomEntities() {
/* 1206 */     return gameSettings.ofRandomEntities;
/*      */   }
/*      */   
/*      */   public static void checkGlError(String p_checkGlError_0_) {
/* 1210 */     int i = GlStateManager.glGetError();
/*      */     
/* 1212 */     if (i != 0 && GlErrors.isEnabled(i)) {
/* 1213 */       String s = getGlErrorString(i);
/* 1214 */       String s1 = String.format("OpenGL error: %s (%s), at: %s", new Object[] { Integer.valueOf(i), s, p_checkGlError_0_ });
/* 1215 */       error(s1);
/*      */       
/* 1217 */       if (isShowGlErrors() && TimedEvent.isActive("ShowGlError", 10000L)) {
/* 1218 */         String s2 = I18n.format("of.message.openglError", new Object[] { Integer.valueOf(i), s });
/* 1219 */         minecraft.ingameGUI.getChatGUI().printChatMessage((IChatComponent)new ChatComponentText(s2));
/*      */       } 
/*      */     } 
/*      */   }
/*      */   
/*      */   public static boolean isSmoothBiomes() {
/* 1225 */     return gameSettings.ofSmoothBiomes;
/*      */   }
/*      */   
/*      */   public static boolean isCustomColors() {
/* 1229 */     return gameSettings.ofCustomColors;
/*      */   }
/*      */   
/*      */   public static boolean isCustomSky() {
/* 1233 */     return gameSettings.ofCustomSky;
/*      */   }
/*      */   
/*      */   public static boolean isCustomFonts() {
/* 1237 */     return gameSettings.ofCustomFonts;
/*      */   }
/*      */   
/*      */   public static boolean isShowCapes() {
/* 1241 */     return gameSettings.ofShowCapes;
/*      */   }
/*      */   
/*      */   public static boolean isConnectedTextures() {
/* 1245 */     return (gameSettings.ofConnectedTextures != 3);
/*      */   }
/*      */   
/*      */   public static boolean isNaturalTextures() {
/* 1249 */     return gameSettings.ofNaturalTextures;
/*      */   }
/*      */   
/*      */   public static boolean isEmissiveTextures() {
/* 1253 */     return gameSettings.ofEmissiveTextures;
/*      */   }
/*      */   
/*      */   public static boolean isConnectedTexturesFancy() {
/* 1257 */     return (gameSettings.ofConnectedTextures == 2);
/*      */   }
/*      */   
/*      */   public static boolean isFastRender() {
/* 1261 */     return gameSettings.ofFastRender;
/*      */   }
/*      */   
/*      */   public static boolean isTranslucentBlocksFancy() {
/* 1265 */     return (gameSettings.ofTranslucentBlocks == 0) ? gameSettings.fancyGraphics : ((gameSettings.ofTranslucentBlocks == 2));
/*      */   }
/*      */   
/*      */   public static boolean isShaders() {
/* 1269 */     return Shaders.shaderPackLoaded;
/*      */   }
/*      */   
/*      */   public static String[] readLines(File p_readLines_0_) throws IOException {
/* 1273 */     FileInputStream fileinputstream = new FileInputStream(p_readLines_0_);
/* 1274 */     return readLines(fileinputstream);
/*      */   }
/*      */   
/*      */   public static String[] readLines(InputStream p_readLines_0_) throws IOException {
/* 1278 */     List<String> list = new ArrayList();
/* 1279 */     InputStreamReader inputstreamreader = new InputStreamReader(p_readLines_0_, "ASCII");
/* 1280 */     BufferedReader bufferedreader = new BufferedReader(inputstreamreader);
/*      */     
/*      */     while (true) {
/* 1283 */       String s = bufferedreader.readLine();
/*      */       
/* 1285 */       if (s == null) {
/* 1286 */         String[] astring = (String[])list.toArray((Object[])new String[list.size()]);
/* 1287 */         return astring;
/*      */       } 
/*      */       
/* 1290 */       list.add(s);
/*      */     } 
/*      */   }
/*      */   
/*      */   public static String readFile(File p_readFile_0_) throws IOException {
/* 1295 */     FileInputStream fileinputstream = new FileInputStream(p_readFile_0_);
/* 1296 */     return readInputStream(fileinputstream, "ASCII");
/*      */   }
/*      */   
/*      */   public static String readInputStream(InputStream p_readInputStream_0_) throws IOException {
/* 1300 */     return readInputStream(p_readInputStream_0_, "ASCII");
/*      */   }
/*      */   
/*      */   public static String readInputStream(InputStream p_readInputStream_0_, String p_readInputStream_1_) throws IOException {
/* 1304 */     InputStreamReader inputstreamreader = new InputStreamReader(p_readInputStream_0_, p_readInputStream_1_);
/* 1305 */     BufferedReader bufferedreader = new BufferedReader(inputstreamreader);
/* 1306 */     StringBuffer stringbuffer = new StringBuffer();
/*      */     
/*      */     while (true) {
/* 1309 */       String s = bufferedreader.readLine();
/*      */       
/* 1311 */       if (s == null) {
/* 1312 */         return stringbuffer.toString();
/*      */       }
/*      */       
/* 1315 */       stringbuffer.append(s);
/* 1316 */       stringbuffer.append("\n");
/*      */     } 
/*      */   }
/*      */   
/*      */   public static byte[] readAll(InputStream p_readAll_0_) throws IOException {
/* 1321 */     ByteArrayOutputStream bytearrayoutputstream = new ByteArrayOutputStream();
/* 1322 */     byte[] abyte = new byte[1024];
/*      */     
/*      */     while (true) {
/* 1325 */       int i = p_readAll_0_.read(abyte);
/*      */       
/* 1327 */       if (i < 0) {
/* 1328 */         p_readAll_0_.close();
/* 1329 */         byte[] abyte1 = bytearrayoutputstream.toByteArray();
/* 1330 */         return abyte1;
/*      */       } 
/*      */       
/* 1333 */       bytearrayoutputstream.write(abyte, 0, i);
/*      */     } 
/*      */   }
/*      */   
/*      */   public static GameSettings getGameSettings() {
/* 1338 */     return gameSettings;
/*      */   }
/*      */   
/*      */   public static String getNewRelease() {
/* 1342 */     return newRelease;
/*      */   }
/*      */   
/*      */   public static void setNewRelease(String p_setNewRelease_0_) {
/* 1346 */     newRelease = p_setNewRelease_0_;
/*      */   }
/*      */   
/*      */   public static int compareRelease(String p_compareRelease_0_, String p_compareRelease_1_) {
/* 1350 */     String[] astring = splitRelease(p_compareRelease_0_);
/* 1351 */     String[] astring1 = splitRelease(p_compareRelease_1_);
/* 1352 */     String s = astring[0];
/* 1353 */     String s1 = astring1[0];
/*      */     
/* 1355 */     if (!s.equals(s1)) {
/* 1356 */       return s.compareTo(s1);
/*      */     }
/* 1358 */     int i = parseInt(astring[1], -1);
/* 1359 */     int j = parseInt(astring1[1], -1);
/*      */     
/* 1361 */     if (i != j) {
/* 1362 */       return i - j;
/*      */     }
/* 1364 */     String s2 = astring[2];
/* 1365 */     String s3 = astring1[2];
/*      */     
/* 1367 */     if (!s2.equals(s3)) {
/* 1368 */       if (s2.isEmpty()) {
/* 1369 */         return 1;
/*      */       }
/*      */       
/* 1372 */       if (s3.isEmpty()) {
/* 1373 */         return -1;
/*      */       }
/*      */     } 
/*      */     
/* 1377 */     return s2.compareTo(s3);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private static String[] splitRelease(String p_splitRelease_0_) {
/* 1383 */     if (p_splitRelease_0_ != null && p_splitRelease_0_.length() > 0) {
/* 1384 */       Pattern pattern = Pattern.compile("([A-Z])([0-9]+)(.*)");
/* 1385 */       Matcher matcher = pattern.matcher(p_splitRelease_0_);
/*      */       
/* 1387 */       if (!matcher.matches()) {
/* 1388 */         return new String[] { "", "", "" };
/*      */       }
/* 1390 */       String s = normalize(matcher.group(1));
/* 1391 */       String s1 = normalize(matcher.group(2));
/* 1392 */       String s2 = normalize(matcher.group(3));
/* 1393 */       return new String[] { s, s1, s2 };
/*      */     } 
/*      */     
/* 1396 */     return new String[] { "", "", "" };
/*      */   }
/*      */ 
/*      */   
/*      */   public static int intHash(int p_intHash_0_) {
/* 1401 */     p_intHash_0_ = p_intHash_0_ ^ 0x3D ^ p_intHash_0_ >> 16;
/* 1402 */     p_intHash_0_ += p_intHash_0_ << 3;
/* 1403 */     p_intHash_0_ ^= p_intHash_0_ >> 4;
/* 1404 */     p_intHash_0_ *= 668265261;
/* 1405 */     p_intHash_0_ ^= p_intHash_0_ >> 15;
/* 1406 */     return p_intHash_0_;
/*      */   }
/*      */   
/*      */   public static int getRandom(BlockPos p_getRandom_0_, int p_getRandom_1_) {
/* 1410 */     int i = intHash(p_getRandom_1_ + 37);
/* 1411 */     i = intHash(i + p_getRandom_0_.getX());
/* 1412 */     i = intHash(i + p_getRandom_0_.getZ());
/* 1413 */     i = intHash(i + p_getRandom_0_.getY());
/* 1414 */     return i;
/*      */   }
/*      */   
/*      */   public static int getAvailableProcessors() {
/* 1418 */     return availableProcessors;
/*      */   }
/*      */   
/*      */   public static void updateAvailableProcessors() {
/* 1422 */     availableProcessors = Runtime.getRuntime().availableProcessors();
/*      */   }
/*      */   
/*      */   public static boolean isSingleProcessor() {
/* 1426 */     return (getAvailableProcessors() <= 1);
/*      */   }
/*      */   
/*      */   public static boolean isSmoothWorld() {
/* 1430 */     return gameSettings.ofSmoothWorld;
/*      */   }
/*      */   
/*      */   public static boolean isLazyChunkLoading() {
/* 1434 */     return gameSettings.ofLazyChunkLoading;
/*      */   }
/*      */   
/*      */   public static boolean isDynamicFov() {
/* 1438 */     return gameSettings.ofDynamicFov;
/*      */   }
/*      */   
/*      */   public static boolean isAlternateBlocks() {
/* 1442 */     return gameSettings.ofAlternateBlocks;
/*      */   }
/*      */   
/*      */   public static int getChunkViewDistance() {
/* 1446 */     if (gameSettings == null) {
/* 1447 */       return 10;
/*      */     }
/* 1449 */     int i = gameSettings.renderDistanceChunks;
/* 1450 */     return i;
/*      */   }
/*      */ 
/*      */   
/*      */   public static boolean equals(Object p_equals_0_, Object p_equals_1_) {
/* 1455 */     return (p_equals_0_ == p_equals_1_) ? true : ((p_equals_0_ == null) ? false : p_equals_0_.equals(p_equals_1_));
/*      */   }
/*      */   
/*      */   public static boolean equalsOne(Object p_equalsOne_0_, Object[] p_equalsOne_1_) {
/* 1459 */     if (p_equalsOne_1_ == null) {
/* 1460 */       return false;
/*      */     }
/* 1462 */     for (int i = 0; i < p_equalsOne_1_.length; i++) {
/* 1463 */       Object object = p_equalsOne_1_[i];
/*      */       
/* 1465 */       if (equals(p_equalsOne_0_, object)) {
/* 1466 */         return true;
/*      */       }
/*      */     } 
/*      */     
/* 1470 */     return false;
/*      */   }
/*      */ 
/*      */   
/*      */   public static boolean equalsOne(int p_equalsOne_0_, int[] p_equalsOne_1_) {
/* 1475 */     for (int i = 0; i < p_equalsOne_1_.length; i++) {
/* 1476 */       if (p_equalsOne_1_[i] == p_equalsOne_0_) {
/* 1477 */         return true;
/*      */       }
/*      */     } 
/*      */     
/* 1481 */     return false;
/*      */   }
/*      */   
/*      */   public static boolean isSameOne(Object p_isSameOne_0_, Object[] p_isSameOne_1_) {
/* 1485 */     if (p_isSameOne_1_ == null) {
/* 1486 */       return false;
/*      */     }
/* 1488 */     for (int i = 0; i < p_isSameOne_1_.length; i++) {
/* 1489 */       Object object = p_isSameOne_1_[i];
/*      */       
/* 1491 */       if (p_isSameOne_0_ == object) {
/* 1492 */         return true;
/*      */       }
/*      */     } 
/*      */     
/* 1496 */     return false;
/*      */   }
/*      */ 
/*      */   
/*      */   public static String normalize(String p_normalize_0_) {
/* 1501 */     return (p_normalize_0_ == null) ? "" : p_normalize_0_;
/*      */   }
/*      */   
/*      */   public static void checkDisplaySettings() {
/* 1505 */     int i = getAntialiasingLevel();
/*      */     
/* 1507 */     if (i > 0) {
/* 1508 */       DisplayMode displaymode = Display.getDisplayMode();
/* 1509 */       dbg("FSAA Samples: " + i);
/*      */       
/*      */       try {
/* 1512 */         Display.destroy();
/* 1513 */         Display.setDisplayMode(displaymode);
/* 1514 */         Display.create((new PixelFormat()).withDepthBits(24).withSamples(i));
/* 1515 */         Display.setResizable(false);
/* 1516 */         Display.setResizable(true);
/* 1517 */       } catch (LWJGLException lwjglexception2) {
/* 1518 */         warn("Error setting FSAA: " + i + "x");
/* 1519 */         lwjglexception2.printStackTrace();
/*      */         
/*      */         try {
/* 1522 */           Display.setDisplayMode(displaymode);
/* 1523 */           Display.create((new PixelFormat()).withDepthBits(24));
/* 1524 */           Display.setResizable(false);
/* 1525 */           Display.setResizable(true);
/* 1526 */         } catch (LWJGLException lwjglexception1) {
/* 1527 */           lwjglexception1.printStackTrace();
/*      */           
/*      */           try {
/* 1530 */             Display.setDisplayMode(displaymode);
/* 1531 */             Display.create();
/* 1532 */             Display.setResizable(false);
/* 1533 */             Display.setResizable(true);
/* 1534 */           } catch (LWJGLException lwjglexception) {
/* 1535 */             lwjglexception.printStackTrace();
/*      */           } 
/*      */         } 
/*      */       } 
/*      */       
/* 1540 */       if (!Minecraft.isRunningOnMac && getDefaultResourcePack() != null) {
/* 1541 */         InputStream inputstream = null;
/* 1542 */         InputStream inputstream1 = null;
/*      */         
/*      */         try {
/* 1545 */           inputstream = getDefaultResourcePack().getInputStreamAssets(new ResourceLocation("icons/icon_16x16.png"));
/* 1546 */           inputstream1 = getDefaultResourcePack().getInputStreamAssets(new ResourceLocation("icons/icon_32x32.png"));
/*      */           
/* 1548 */           if (inputstream != null && inputstream1 != null) {
/* 1549 */             Display.setIcon(new ByteBuffer[] { readIconImage(inputstream), readIconImage(inputstream1) });
/*      */           }
/* 1551 */         } catch (IOException ioexception) {
/* 1552 */           warn("Error setting window icon: " + ioexception.getClass().getName() + ": " + ioexception.getMessage());
/*      */         } finally {
/* 1554 */           IOUtils.closeQuietly(inputstream);
/* 1555 */           IOUtils.closeQuietly(inputstream1);
/*      */         } 
/*      */       } 
/*      */     } 
/*      */   }
/*      */   
/*      */   private static ByteBuffer readIconImage(InputStream p_readIconImage_0_) throws IOException {
/* 1562 */     BufferedImage bufferedimage = ImageIO.read(p_readIconImage_0_);
/* 1563 */     int[] aint = bufferedimage.getRGB(0, 0, bufferedimage.getWidth(), bufferedimage.getHeight(), null, 0, bufferedimage.getWidth());
/* 1564 */     ByteBuffer bytebuffer = ByteBuffer.allocate(4 * aint.length); byte b;
/*      */     int i, arrayOfInt1[];
/* 1566 */     for (i = (arrayOfInt1 = aint).length, b = 0; b < i; ) { int j = arrayOfInt1[b];
/* 1567 */       bytebuffer.putInt(j << 8 | j >> 24 & 0xFF);
/*      */       b++; }
/*      */     
/* 1570 */     bytebuffer.flip();
/* 1571 */     return bytebuffer;
/*      */   }
/*      */   
/*      */   public static void checkDisplayMode() {
/*      */     try {
/* 1576 */       if (minecraft.isFullScreen()) {
/* 1577 */         if (fullscreenModeChecked) {
/*      */           return;
/*      */         }
/*      */         
/* 1581 */         fullscreenModeChecked = true;
/* 1582 */         desktopModeChecked = false;
/* 1583 */         DisplayMode displaymode = Display.getDisplayMode();
/* 1584 */         Dimension dimension = getFullscreenDimension();
/*      */         
/* 1586 */         if (dimension == null) {
/*      */           return;
/*      */         }
/*      */         
/* 1590 */         if (displaymode.getWidth() == dimension.width && displaymode.getHeight() == dimension.height) {
/*      */           return;
/*      */         }
/*      */         
/* 1594 */         DisplayMode displaymode1 = getDisplayMode(dimension);
/*      */         
/* 1596 */         if (displaymode1 == null) {
/*      */           return;
/*      */         }
/*      */         
/* 1600 */         Display.setDisplayMode(displaymode1);
/* 1601 */         minecraft.displayWidth = Display.getDisplayMode().getWidth();
/* 1602 */         minecraft.displayHeight = Display.getDisplayMode().getHeight();
/*      */         
/* 1604 */         if (minecraft.displayWidth <= 0) {
/* 1605 */           minecraft.displayWidth = 1;
/*      */         }
/*      */         
/* 1608 */         if (minecraft.displayHeight <= 0) {
/* 1609 */           minecraft.displayHeight = 1;
/*      */         }
/*      */         
/* 1612 */         if (minecraft.currentScreen != null) {
/* 1613 */           ScaledResolution scaledresolution = new ScaledResolution(minecraft);
/* 1614 */           int i = scaledresolution.getScaledWidth();
/* 1615 */           int j = scaledresolution.getScaledHeight();
/* 1616 */           minecraft.currentScreen.setWorldAndResolution(minecraft, i, j);
/*      */         } 
/*      */         
/* 1619 */         updateFramebufferSize();
/* 1620 */         Display.setFullscreen(true);
/* 1621 */         minecraft.gameSettings.updateVSync();
/* 1622 */         GlStateManager.enableTexture2D();
/*      */       } else {
/* 1624 */         if (desktopModeChecked) {
/*      */           return;
/*      */         }
/*      */         
/* 1628 */         desktopModeChecked = true;
/* 1629 */         fullscreenModeChecked = false;
/* 1630 */         minecraft.gameSettings.updateVSync();
/* 1631 */         Display.update();
/* 1632 */         GlStateManager.enableTexture2D();
/* 1633 */         Display.setResizable(false);
/* 1634 */         Display.setResizable(true);
/*      */       } 
/* 1636 */     } catch (Exception exception) {
/* 1637 */       exception.printStackTrace();
/* 1638 */       gameSettings.ofFullscreenMode = "Default";
/* 1639 */       gameSettings.saveOfOptions();
/*      */     } 
/*      */   }
/*      */   
/*      */   public static void updateFramebufferSize() {
/* 1644 */     minecraft.getFramebuffer().createBindFramebuffer(minecraft.displayWidth, minecraft.displayHeight);
/*      */     
/* 1646 */     if (minecraft.entityRenderer != null) {
/* 1647 */       minecraft.entityRenderer.updateShaderGroupSize(minecraft.displayWidth, minecraft.displayHeight);
/*      */     }
/*      */     
/* 1650 */     minecraft.loadingScreen = new LoadingScreenRenderer(minecraft);
/*      */   }
/*      */   
/*      */   public static Object[] addObjectToArray(Object[] p_addObjectToArray_0_, Object p_addObjectToArray_1_) {
/* 1654 */     if (p_addObjectToArray_0_ == null) {
/* 1655 */       throw new NullPointerException("The given array is NULL");
/*      */     }
/* 1657 */     int i = p_addObjectToArray_0_.length;
/* 1658 */     int j = i + 1;
/* 1659 */     Object[] aobject = (Object[])Array.newInstance(p_addObjectToArray_0_.getClass().getComponentType(), j);
/* 1660 */     System.arraycopy(p_addObjectToArray_0_, 0, aobject, 0, i);
/* 1661 */     aobject[i] = p_addObjectToArray_1_;
/* 1662 */     return aobject;
/*      */   }
/*      */ 
/*      */   
/*      */   public static Object[] addObjectToArray(Object[] p_addObjectToArray_0_, Object p_addObjectToArray_1_, int p_addObjectToArray_2_) {
/* 1667 */     List<Object> list = new ArrayList(Arrays.asList(p_addObjectToArray_0_));
/* 1668 */     list.add(p_addObjectToArray_2_, p_addObjectToArray_1_);
/* 1669 */     Object[] aobject = (Object[])Array.newInstance(p_addObjectToArray_0_.getClass().getComponentType(), list.size());
/* 1670 */     return list.toArray(aobject);
/*      */   }
/*      */   
/*      */   public static Object[] addObjectsToArray(Object[] p_addObjectsToArray_0_, Object[] p_addObjectsToArray_1_) {
/* 1674 */     if (p_addObjectsToArray_0_ == null)
/* 1675 */       throw new NullPointerException("The given array is NULL"); 
/* 1676 */     if (p_addObjectsToArray_1_.length == 0) {
/* 1677 */       return p_addObjectsToArray_0_;
/*      */     }
/* 1679 */     int i = p_addObjectsToArray_0_.length;
/* 1680 */     int j = i + p_addObjectsToArray_1_.length;
/* 1681 */     Object[] aobject = (Object[])Array.newInstance(p_addObjectsToArray_0_.getClass().getComponentType(), j);
/* 1682 */     System.arraycopy(p_addObjectsToArray_0_, 0, aobject, 0, i);
/* 1683 */     System.arraycopy(p_addObjectsToArray_1_, 0, aobject, i, p_addObjectsToArray_1_.length);
/* 1684 */     return aobject;
/*      */   }
/*      */ 
/*      */   
/*      */   public static Object[] removeObjectFromArray(Object[] p_removeObjectFromArray_0_, Object p_removeObjectFromArray_1_) {
/* 1689 */     List list = new ArrayList(Arrays.asList(p_removeObjectFromArray_0_));
/* 1690 */     list.remove(p_removeObjectFromArray_1_);
/* 1691 */     Object[] aobject = collectionToArray(list, p_removeObjectFromArray_0_.getClass().getComponentType());
/* 1692 */     return aobject;
/*      */   }
/*      */   
/*      */   public static Object[] collectionToArray(Collection p_collectionToArray_0_, Class<?> p_collectionToArray_1_) {
/* 1696 */     if (p_collectionToArray_0_ == null)
/* 1697 */       return null; 
/* 1698 */     if (p_collectionToArray_1_ == null)
/* 1699 */       return null; 
/* 1700 */     if (p_collectionToArray_1_.isPrimitive()) {
/* 1701 */       throw new IllegalArgumentException("Can not make arrays with primitive elements (int, double), element class: " + p_collectionToArray_1_);
/*      */     }
/* 1703 */     Object[] aobject = (Object[])Array.newInstance(p_collectionToArray_1_, p_collectionToArray_0_.size());
/* 1704 */     return p_collectionToArray_0_.toArray(aobject);
/*      */   }
/*      */ 
/*      */   
/*      */   public static boolean isCustomItems() {
/* 1709 */     return gameSettings.ofCustomItems;
/*      */   }
/*      */   
/*      */   public static void drawFps() {
/* 1713 */     int i = Minecraft.getDebugFPS();
/* 1714 */     String s = getUpdates(minecraft.debug);
/* 1715 */     int j = minecraft.renderGlobal.getCountActiveRenderers();
/* 1716 */     int k = minecraft.renderGlobal.getCountEntitiesRendered();
/* 1717 */     int l = minecraft.renderGlobal.getCountTileEntitiesRendered();
/* 1718 */     String s1 = i + "/" + getFpsMin() + " fps, C: " + j + ", E: " + k + "+" + l + ", U: " + s;
/* 1719 */     minecraft.fontRendererObj.drawString(s1, 2, 2, -2039584);
/*      */   }
/*      */   
/*      */   public static int getFpsMin() {
/* 1723 */     if (minecraft.debug == mcDebugLast) {
/* 1724 */       return fpsMinLast;
/*      */     }
/* 1726 */     mcDebugLast = minecraft.debug;
/* 1727 */     FrameTimer frametimer = minecraft.getFrameTimer();
/* 1728 */     long[] along = frametimer.getFrames();
/* 1729 */     int i = frametimer.getIndex();
/* 1730 */     int j = frametimer.getLastIndex();
/*      */     
/* 1732 */     if (i == j) {
/* 1733 */       return fpsMinLast;
/*      */     }
/* 1735 */     int k = Minecraft.getDebugFPS();
/*      */     
/* 1737 */     if (k <= 0) {
/* 1738 */       k = 1;
/*      */     }
/*      */     
/* 1741 */     long l = (long)(1.0D / k * 1.0E9D);
/* 1742 */     long i1 = l;
/* 1743 */     long j1 = 0L;
/*      */     
/* 1745 */     for (int k1 = MathHelper.normalizeAngle(i - 1, along.length); k1 != j && j1 < 1.0E9D; k1 = MathHelper.normalizeAngle(k1 - 1, along.length)) {
/* 1746 */       long l1 = along[k1];
/*      */       
/* 1748 */       if (l1 > i1) {
/* 1749 */         i1 = l1;
/*      */       }
/*      */       
/* 1752 */       j1 += l1;
/*      */     } 
/*      */     
/* 1755 */     double d0 = i1 / 1.0E9D;
/* 1756 */     fpsMinLast = (int)(1.0D / d0);
/* 1757 */     return fpsMinLast;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private static String getUpdates(String p_getUpdates_0_) {
/* 1763 */     int i = p_getUpdates_0_.indexOf('(');
/*      */     
/* 1765 */     if (i < 0) {
/* 1766 */       return "";
/*      */     }
/* 1768 */     int j = p_getUpdates_0_.indexOf(' ', i);
/* 1769 */     return (j < 0) ? "" : p_getUpdates_0_.substring(i + 1, j);
/*      */   }
/*      */ 
/*      */   
/*      */   public static int getBitsOs() {
/* 1774 */     String s = System.getenv("ProgramFiles(X86)");
/* 1775 */     return (s != null) ? 64 : 32;
/*      */   }
/*      */   
/*      */   public static int getBitsJre() {
/* 1779 */     String[] astring = { "sun.arch.data.model", "com.ibm.vm.bitmode", "os.arch" };
/*      */     
/* 1781 */     for (int i = 0; i < astring.length; i++) {
/* 1782 */       String s = astring[i];
/* 1783 */       String s1 = System.getProperty(s);
/*      */       
/* 1785 */       if (s1 != null && s1.contains("64")) {
/* 1786 */         return 64;
/*      */       }
/*      */     } 
/*      */     
/* 1790 */     return 32;
/*      */   }
/*      */   
/*      */   public static boolean isNotify64BitJava() {
/* 1794 */     return notify64BitJava;
/*      */   }
/*      */   
/*      */   public static void setNotify64BitJava(boolean p_setNotify64BitJava_0_) {
/* 1798 */     notify64BitJava = p_setNotify64BitJava_0_;
/*      */   }
/*      */   
/*      */   public static boolean isConnectedModels() {
/* 1802 */     return false;
/*      */   }
/*      */   
/*      */   public static void showGuiMessage(String p_showGuiMessage_0_, String p_showGuiMessage_1_) {
/* 1806 */     GuiMessage guimessage = new GuiMessage(minecraft.currentScreen, p_showGuiMessage_0_, p_showGuiMessage_1_);
/* 1807 */     minecraft.displayGuiScreen((GuiScreen)guimessage);
/*      */   }
/*      */   
/*      */   public static int[] addIntToArray(int[] p_addIntToArray_0_, int p_addIntToArray_1_) {
/* 1811 */     return addIntsToArray(p_addIntToArray_0_, new int[] { p_addIntToArray_1_ });
/*      */   }
/*      */   
/*      */   public static int[] addIntsToArray(int[] p_addIntsToArray_0_, int[] p_addIntsToArray_1_) {
/* 1815 */     if (p_addIntsToArray_0_ != null && p_addIntsToArray_1_ != null) {
/* 1816 */       int i = p_addIntsToArray_0_.length;
/* 1817 */       int j = i + p_addIntsToArray_1_.length;
/* 1818 */       int[] aint = new int[j];
/* 1819 */       System.arraycopy(p_addIntsToArray_0_, 0, aint, 0, i);
/*      */       
/* 1821 */       for (int k = 0; k < p_addIntsToArray_1_.length; k++) {
/* 1822 */         aint[k + i] = p_addIntsToArray_1_[k];
/*      */       }
/*      */       
/* 1825 */       return aint;
/*      */     } 
/* 1827 */     throw new NullPointerException("The given array is NULL");
/*      */   }
/*      */ 
/*      */   
/*      */   public static DynamicTexture getMojangLogoTexture(DynamicTexture p_getMojangLogoTexture_0_) {
/*      */     try {
/* 1833 */       ResourceLocation resourcelocation = new ResourceLocation("textures/gui/title/mojang.png");
/* 1834 */       InputStream inputstream = getResourceStream(resourcelocation);
/*      */       
/* 1836 */       if (inputstream == null) {
/* 1837 */         return p_getMojangLogoTexture_0_;
/*      */       }
/* 1839 */       BufferedImage bufferedimage = ImageIO.read(inputstream);
/*      */       
/* 1841 */       if (bufferedimage == null) {
/* 1842 */         return p_getMojangLogoTexture_0_;
/*      */       }
/* 1844 */       DynamicTexture dynamictexture = new DynamicTexture(bufferedimage);
/* 1845 */       return dynamictexture;
/*      */     
/*      */     }
/* 1848 */     catch (Exception exception) {
/* 1849 */       warn(String.valueOf(exception.getClass().getName()) + ": " + exception.getMessage());
/* 1850 */       return p_getMojangLogoTexture_0_;
/*      */     } 
/*      */   }
/*      */   
/*      */   public static void writeFile(File p_writeFile_0_, String p_writeFile_1_) throws IOException {
/* 1855 */     FileOutputStream fileoutputstream = new FileOutputStream(p_writeFile_0_);
/* 1856 */     byte[] abyte = p_writeFile_1_.getBytes("ASCII");
/* 1857 */     fileoutputstream.write(abyte);
/* 1858 */     fileoutputstream.close();
/*      */   }
/*      */   
/*      */   public static TextureMap getTextureMap() {
/* 1862 */     return getMinecraft().getTextureMapBlocks();
/*      */   }
/*      */   
/*      */   public static boolean isDynamicLights() {
/* 1866 */     return (gameSettings.ofDynamicLights != 3);
/*      */   }
/*      */   
/*      */   public static boolean isDynamicLightsFast() {
/* 1870 */     return (gameSettings.ofDynamicLights == 1);
/*      */   }
/*      */   
/*      */   public static boolean isDynamicHandLight() {
/* 1874 */     return !isDynamicLights() ? false : (isShaders() ? Shaders.isDynamicHandLight() : true);
/*      */   }
/*      */   
/*      */   public static boolean isCustomEntityModels() {
/* 1878 */     return gameSettings.ofCustomEntityModels;
/*      */   }
/*      */   
/*      */   public static boolean isCustomGuis() {
/* 1882 */     return gameSettings.ofCustomGuis;
/*      */   }
/*      */   
/*      */   public static int getScreenshotSize() {
/* 1886 */     return gameSettings.ofScreenshotSize;
/*      */   }
/*      */   
/*      */   public static int[] toPrimitive(Integer[] p_toPrimitive_0_) {
/* 1890 */     if (p_toPrimitive_0_ == null)
/* 1891 */       return null; 
/* 1892 */     if (p_toPrimitive_0_.length == 0) {
/* 1893 */       return new int[0];
/*      */     }
/* 1895 */     int[] aint = new int[p_toPrimitive_0_.length];
/*      */     
/* 1897 */     for (int i = 0; i < aint.length; i++) {
/* 1898 */       aint[i] = p_toPrimitive_0_[i].intValue();
/*      */     }
/*      */     
/* 1901 */     return aint;
/*      */   }
/*      */ 
/*      */   
/*      */   public static boolean isRenderRegions() {
/* 1906 */     return gameSettings.ofRenderRegions;
/*      */   }
/*      */   
/*      */   public static boolean isVbo() {
/* 1910 */     return OpenGlHelper.useVbo();
/*      */   }
/*      */   
/*      */   public static boolean isSmoothFps() {
/* 1914 */     return gameSettings.ofSmoothFps;
/*      */   }
/*      */   
/*      */   public static boolean openWebLink(URI p_openWebLink_0_) {
/*      */     try {
/* 1919 */       Desktop.getDesktop().browse(p_openWebLink_0_);
/* 1920 */       return true;
/* 1921 */     } catch (Exception exception) {
/* 1922 */       warn("Error opening link: " + p_openWebLink_0_);
/* 1923 */       warn(String.valueOf(exception.getClass().getName()) + ": " + exception.getMessage());
/* 1924 */       return false;
/*      */     } 
/*      */   }
/*      */   
/*      */   public static boolean isShowGlErrors() {
/* 1929 */     return gameSettings.ofShowGlErrors;
/*      */   }
/*      */   
/*      */   public static String arrayToString(boolean[] p_arrayToString_0_, String p_arrayToString_1_) {
/* 1933 */     if (p_arrayToString_0_ == null) {
/* 1934 */       return "";
/*      */     }
/* 1936 */     StringBuffer stringbuffer = new StringBuffer(p_arrayToString_0_.length * 5);
/*      */     
/* 1938 */     for (int i = 0; i < p_arrayToString_0_.length; i++) {
/* 1939 */       boolean flag = p_arrayToString_0_[i];
/*      */       
/* 1941 */       if (i > 0) {
/* 1942 */         stringbuffer.append(p_arrayToString_1_);
/*      */       }
/*      */       
/* 1945 */       stringbuffer.append(String.valueOf(flag));
/*      */     } 
/*      */     
/* 1948 */     return stringbuffer.toString();
/*      */   }
/*      */ 
/*      */   
/*      */   public static boolean isIntegratedServerRunning() {
/* 1953 */     return (minecraft.getIntegratedServer() == null) ? false : minecraft.isIntegratedServerRunning();
/*      */   }
/*      */   
/*      */   public static IntBuffer createDirectIntBuffer(int p_createDirectIntBuffer_0_) {
/* 1957 */     return GLAllocation.createDirectByteBuffer(p_createDirectIntBuffer_0_ << 2).asIntBuffer();
/*      */   }
/*      */   
/*      */   public static String getGlErrorString(int p_getGlErrorString_0_) {
/* 1961 */     switch (p_getGlErrorString_0_) {
/*      */       case 0:
/* 1963 */         return "No error";
/*      */       
/*      */       case 1280:
/* 1966 */         return "Invalid enum";
/*      */       
/*      */       case 1281:
/* 1969 */         return "Invalid value";
/*      */       
/*      */       case 1282:
/* 1972 */         return "Invalid operation";
/*      */       
/*      */       case 1283:
/* 1975 */         return "Stack overflow";
/*      */       
/*      */       case 1284:
/* 1978 */         return "Stack underflow";
/*      */       
/*      */       case 1285:
/* 1981 */         return "Out of memory";
/*      */       
/*      */       case 1286:
/* 1984 */         return "Invalid framebuffer operation";
/*      */     } 
/*      */     
/* 1987 */     return "Unknown";
/*      */   }
/*      */ 
/*      */   
/*      */   public static boolean isTrue(Boolean p_isTrue_0_) {
/* 1992 */     return (p_isTrue_0_ != null && p_isTrue_0_.booleanValue());
/*      */   }
/*      */   
/*      */   public static boolean isQuadsToTriangles() {
/* 1996 */     return !isShaders() ? false : (!Shaders.canRenderQuads());
/*      */   }
/*      */   
/*      */   public static void checkNull(Object p_checkNull_0_, String p_checkNull_1_) throws NullPointerException {
/* 2000 */     if (p_checkNull_0_ == null)
/* 2001 */       throw new NullPointerException(p_checkNull_1_); 
/*      */   }
/*      */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\minecraft\src\Config.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */