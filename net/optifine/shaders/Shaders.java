/*      */ package net.optifine.shaders;
/*      */ 
/*      */ import com.google.common.base.Charsets;
/*      */ import java.io.BufferedReader;
/*      */ import java.io.File;
/*      */ import java.io.FileInputStream;
/*      */ import java.io.FileOutputStream;
/*      */ import java.io.FileReader;
/*      */ import java.io.FileWriter;
/*      */ import java.io.IOException;
/*      */ import java.io.InputStream;
/*      */ import java.io.InputStreamReader;
/*      */ import java.io.Reader;
/*      */ import java.nio.ByteBuffer;
/*      */ import java.nio.FloatBuffer;
/*      */ import java.nio.IntBuffer;
/*      */ import java.util.ArrayDeque;
/*      */ import java.util.ArrayList;
/*      */ import java.util.Arrays;
/*      */ import java.util.Collections;
/*      */ import java.util.Deque;
/*      */ import java.util.HashMap;
/*      */ import java.util.HashSet;
/*      */ import java.util.IdentityHashMap;
/*      */ import java.util.List;
/*      */ import java.util.Map;
/*      */ import java.util.Properties;
/*      */ import java.util.Set;
/*      */ import java.util.regex.Matcher;
/*      */ import java.util.regex.Pattern;
/*      */ import net.minecraft.block.Block;
/*      */ import net.minecraft.block.material.Material;
/*      */ import net.minecraft.client.Minecraft;
/*      */ import net.minecraft.client.renderer.ActiveRenderInfo;
/*      */ import net.minecraft.client.renderer.EntityRenderer;
/*      */ import net.minecraft.client.renderer.GLAllocation;
/*      */ import net.minecraft.client.renderer.GlStateManager;
/*      */ import net.minecraft.client.renderer.OpenGlHelper;
/*      */ import net.minecraft.client.renderer.RenderHelper;
/*      */ import net.minecraft.client.renderer.Tessellator;
/*      */ import net.minecraft.client.renderer.WorldRenderer;
/*      */ import net.minecraft.client.renderer.texture.ITextureObject;
/*      */ import net.minecraft.client.renderer.texture.TextureMap;
/*      */ import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
/*      */ import net.minecraft.client.resources.I18n;
/*      */ import net.minecraft.client.resources.data.TextureMetadataSection;
/*      */ import net.minecraft.client.settings.GameSettings;
/*      */ import net.minecraft.entity.Entity;
/*      */ import net.minecraft.entity.EntityLivingBase;
/*      */ import net.minecraft.item.Item;
/*      */ import net.minecraft.item.ItemBlock;
/*      */ import net.minecraft.item.ItemStack;
/*      */ import net.minecraft.potion.Potion;
/*      */ import net.minecraft.src.Config;
/*      */ import net.minecraft.tileentity.TileEntity;
/*      */ import net.minecraft.util.BlockPos;
/*      */ import net.minecraft.util.ChatComponentText;
/*      */ import net.minecraft.util.EnumWorldBlockLayer;
/*      */ import net.minecraft.util.IChatComponent;
/*      */ import net.minecraft.util.ResourceLocation;
/*      */ import net.minecraft.util.Vec3;
/*      */ import net.minecraft.world.World;
/*      */ import net.optifine.CustomBlockLayers;
/*      */ import net.optifine.CustomColors;
/*      */ import net.optifine.GlErrors;
/*      */ import net.optifine.Lang;
/*      */ import net.optifine.config.ConnectedParser;
/*      */ import net.optifine.expr.IExpressionBool;
/*      */ import net.optifine.reflect.Reflector;
/*      */ import net.optifine.render.GlAlphaState;
/*      */ import net.optifine.render.GlBlendState;
/*      */ import net.optifine.shaders.config.EnumShaderOption;
/*      */ import net.optifine.shaders.config.MacroProcessor;
/*      */ import net.optifine.shaders.config.MacroState;
/*      */ import net.optifine.shaders.config.PropertyDefaultFastFancyOff;
/*      */ import net.optifine.shaders.config.PropertyDefaultTrueFalse;
/*      */ import net.optifine.shaders.config.RenderScale;
/*      */ import net.optifine.shaders.config.ScreenShaderOptions;
/*      */ import net.optifine.shaders.config.ShaderLine;
/*      */ import net.optifine.shaders.config.ShaderOption;
/*      */ import net.optifine.shaders.config.ShaderOptionProfile;
/*      */ import net.optifine.shaders.config.ShaderPackParser;
/*      */ import net.optifine.shaders.config.ShaderParser;
/*      */ import net.optifine.shaders.config.ShaderProfile;
/*      */ import net.optifine.shaders.uniform.CustomUniforms;
/*      */ import net.optifine.shaders.uniform.ShaderUniform1f;
/*      */ import net.optifine.shaders.uniform.ShaderUniform1i;
/*      */ import net.optifine.shaders.uniform.ShaderUniform2i;
/*      */ import net.optifine.shaders.uniform.ShaderUniform3f;
/*      */ import net.optifine.shaders.uniform.ShaderUniform4f;
/*      */ import net.optifine.shaders.uniform.ShaderUniform4i;
/*      */ import net.optifine.shaders.uniform.ShaderUniformM4;
/*      */ import net.optifine.shaders.uniform.ShaderUniforms;
/*      */ import net.optifine.shaders.uniform.Smoother;
/*      */ import net.optifine.texture.InternalFormat;
/*      */ import net.optifine.texture.PixelFormat;
/*      */ import net.optifine.texture.PixelType;
/*      */ import net.optifine.texture.TextureType;
/*      */ import net.optifine.util.EntityUtils;
/*      */ import net.optifine.util.PropertiesOrdered;
/*      */ import net.optifine.util.StrUtils;
/*      */ import net.optifine.util.TimedEvent;
/*      */ import org.apache.commons.io.IOUtils;
/*      */ import org.lwjgl.BufferUtils;
/*      */ import org.lwjgl.opengl.ARBGeometryShader4;
/*      */ import org.lwjgl.opengl.ARBShaderObjects;
/*      */ import org.lwjgl.opengl.ARBVertexShader;
/*      */ import org.lwjgl.opengl.ContextCapabilities;
/*      */ import org.lwjgl.opengl.EXTFramebufferObject;
/*      */ import org.lwjgl.opengl.GL11;
/*      */ import org.lwjgl.opengl.GL20;
/*      */ import org.lwjgl.opengl.GL30;
/*      */ import org.lwjgl.opengl.GLContext;
/*      */ import org.lwjgl.util.glu.GLU;
/*      */ import org.lwjgl.util.vector.Vector4f;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ public class Shaders
/*      */ {
/*      */   static Minecraft mc;
/*      */   static EntityRenderer entityRenderer;
/*      */   public static boolean isInitializedOnce = false;
/*      */   public static boolean isShaderPackInitialized = false;
/*      */   public static ContextCapabilities capabilities;
/*      */   public static String glVersionString;
/*      */   public static String glVendorString;
/*      */   public static String glRendererString;
/*      */   public static boolean hasGlGenMipmap = false;
/*  134 */   public static int countResetDisplayLists = 0;
/*  135 */   private static int renderDisplayWidth = 0;
/*  136 */   private static int renderDisplayHeight = 0;
/*  137 */   public static int renderWidth = 0;
/*  138 */   public static int renderHeight = 0;
/*      */   public static boolean isRenderingWorld = false;
/*      */   public static boolean isRenderingSky = false;
/*      */   public static boolean isCompositeRendered = false;
/*      */   public static boolean isRenderingDfb = false;
/*      */   public static boolean isShadowPass = false;
/*      */   public static boolean isEntitiesGlowing = false;
/*      */   public static boolean isSleeping;
/*      */   private static boolean isRenderingFirstPersonHand;
/*      */   private static boolean isHandRenderedMain;
/*      */   private static boolean isHandRenderedOff;
/*      */   private static boolean skipRenderHandMain;
/*      */   private static boolean skipRenderHandOff;
/*      */   public static boolean renderItemKeepDepthMask = false;
/*      */   public static boolean itemToRenderMainTranslucent = false;
/*      */   public static boolean itemToRenderOffTranslucent = false;
/*  154 */   static float[] sunPosition = new float[4];
/*  155 */   static float[] moonPosition = new float[4];
/*  156 */   static float[] shadowLightPosition = new float[4];
/*  157 */   static float[] upPosition = new float[4];
/*  158 */   static float[] shadowLightPositionVector = new float[4];
/*  159 */   static float[] upPosModelView = new float[] { 0.0F, 100.0F, 0.0F, 0.0F };
/*  160 */   static float[] sunPosModelView = new float[] { 0.0F, 100.0F, 0.0F, 0.0F };
/*  161 */   static float[] moonPosModelView = new float[] { 0.0F, -100.0F, 0.0F, 0.0F };
/*  162 */   private static float[] tempMat = new float[16];
/*      */   static float clearColorR;
/*      */   static float clearColorG;
/*      */   static float clearColorB;
/*      */   static float skyColorR;
/*      */   static float skyColorG;
/*      */   static float skyColorB;
/*  169 */   static long worldTime = 0L;
/*  170 */   static long lastWorldTime = 0L;
/*  171 */   static long diffWorldTime = 0L;
/*  172 */   static float celestialAngle = 0.0F;
/*  173 */   static float sunAngle = 0.0F;
/*  174 */   static float shadowAngle = 0.0F;
/*  175 */   static int moonPhase = 0;
/*  176 */   static long systemTime = 0L;
/*  177 */   static long lastSystemTime = 0L;
/*  178 */   static long diffSystemTime = 0L;
/*  179 */   static int frameCounter = 0;
/*  180 */   static float frameTime = 0.0F;
/*  181 */   static float frameTimeCounter = 0.0F;
/*  182 */   static int systemTimeInt32 = 0;
/*  183 */   static float rainStrength = 0.0F;
/*  184 */   static float wetness = 0.0F;
/*  185 */   public static float wetnessHalfLife = 600.0F;
/*  186 */   public static float drynessHalfLife = 200.0F;
/*  187 */   public static float eyeBrightnessHalflife = 10.0F;
/*      */   static boolean usewetness = false;
/*  189 */   static int isEyeInWater = 0;
/*  190 */   static int eyeBrightness = 0;
/*  191 */   static float eyeBrightnessFadeX = 0.0F;
/*  192 */   static float eyeBrightnessFadeY = 0.0F;
/*  193 */   static float eyePosY = 0.0F;
/*  194 */   static float centerDepth = 0.0F;
/*  195 */   static float centerDepthSmooth = 0.0F;
/*  196 */   static float centerDepthSmoothHalflife = 1.0F;
/*      */   static boolean centerDepthSmoothEnabled = false;
/*  198 */   static int superSamplingLevel = 1;
/*  199 */   static float nightVision = 0.0F;
/*  200 */   static float blindness = 0.0F;
/*      */   static boolean lightmapEnabled = false;
/*      */   static boolean fogEnabled = true;
/*  203 */   public static int entityAttrib = 10;
/*  204 */   public static int midTexCoordAttrib = 11;
/*  205 */   public static int tangentAttrib = 12;
/*      */   public static boolean useEntityAttrib = false;
/*      */   public static boolean useMidTexCoordAttrib = false;
/*      */   public static boolean useTangentAttrib = false;
/*      */   public static boolean progUseEntityAttrib = false;
/*      */   public static boolean progUseMidTexCoordAttrib = false;
/*      */   public static boolean progUseTangentAttrib = false;
/*      */   private static boolean progArbGeometryShader4 = false;
/*  213 */   private static int progMaxVerticesOut = 3;
/*      */   private static boolean hasGeometryShaders = false;
/*  215 */   public static int atlasSizeX = 0;
/*  216 */   public static int atlasSizeY = 0;
/*  217 */   private static ShaderUniforms shaderUniforms = new ShaderUniforms();
/*  218 */   public static ShaderUniform4f uniform_entityColor = shaderUniforms.make4f("entityColor");
/*  219 */   public static ShaderUniform1i uniform_entityId = shaderUniforms.make1i("entityId");
/*  220 */   public static ShaderUniform1i uniform_blockEntityId = shaderUniforms.make1i("blockEntityId");
/*  221 */   public static ShaderUniform1i uniform_texture = shaderUniforms.make1i("texture");
/*  222 */   public static ShaderUniform1i uniform_lightmap = shaderUniforms.make1i("lightmap");
/*  223 */   public static ShaderUniform1i uniform_normals = shaderUniforms.make1i("normals");
/*  224 */   public static ShaderUniform1i uniform_specular = shaderUniforms.make1i("specular");
/*  225 */   public static ShaderUniform1i uniform_shadow = shaderUniforms.make1i("shadow");
/*  226 */   public static ShaderUniform1i uniform_watershadow = shaderUniforms.make1i("watershadow");
/*  227 */   public static ShaderUniform1i uniform_shadowtex0 = shaderUniforms.make1i("shadowtex0");
/*  228 */   public static ShaderUniform1i uniform_shadowtex1 = shaderUniforms.make1i("shadowtex1");
/*  229 */   public static ShaderUniform1i uniform_depthtex0 = shaderUniforms.make1i("depthtex0");
/*  230 */   public static ShaderUniform1i uniform_depthtex1 = shaderUniforms.make1i("depthtex1");
/*  231 */   public static ShaderUniform1i uniform_shadowcolor = shaderUniforms.make1i("shadowcolor");
/*  232 */   public static ShaderUniform1i uniform_shadowcolor0 = shaderUniforms.make1i("shadowcolor0");
/*  233 */   public static ShaderUniform1i uniform_shadowcolor1 = shaderUniforms.make1i("shadowcolor1");
/*  234 */   public static ShaderUniform1i uniform_noisetex = shaderUniforms.make1i("noisetex");
/*  235 */   public static ShaderUniform1i uniform_gcolor = shaderUniforms.make1i("gcolor");
/*  236 */   public static ShaderUniform1i uniform_gdepth = shaderUniforms.make1i("gdepth");
/*  237 */   public static ShaderUniform1i uniform_gnormal = shaderUniforms.make1i("gnormal");
/*  238 */   public static ShaderUniform1i uniform_composite = shaderUniforms.make1i("composite");
/*  239 */   public static ShaderUniform1i uniform_gaux1 = shaderUniforms.make1i("gaux1");
/*  240 */   public static ShaderUniform1i uniform_gaux2 = shaderUniforms.make1i("gaux2");
/*  241 */   public static ShaderUniform1i uniform_gaux3 = shaderUniforms.make1i("gaux3");
/*  242 */   public static ShaderUniform1i uniform_gaux4 = shaderUniforms.make1i("gaux4");
/*  243 */   public static ShaderUniform1i uniform_colortex0 = shaderUniforms.make1i("colortex0");
/*  244 */   public static ShaderUniform1i uniform_colortex1 = shaderUniforms.make1i("colortex1");
/*  245 */   public static ShaderUniform1i uniform_colortex2 = shaderUniforms.make1i("colortex2");
/*  246 */   public static ShaderUniform1i uniform_colortex3 = shaderUniforms.make1i("colortex3");
/*  247 */   public static ShaderUniform1i uniform_colortex4 = shaderUniforms.make1i("colortex4");
/*  248 */   public static ShaderUniform1i uniform_colortex5 = shaderUniforms.make1i("colortex5");
/*  249 */   public static ShaderUniform1i uniform_colortex6 = shaderUniforms.make1i("colortex6");
/*  250 */   public static ShaderUniform1i uniform_colortex7 = shaderUniforms.make1i("colortex7");
/*  251 */   public static ShaderUniform1i uniform_gdepthtex = shaderUniforms.make1i("gdepthtex");
/*  252 */   public static ShaderUniform1i uniform_depthtex2 = shaderUniforms.make1i("depthtex2");
/*  253 */   public static ShaderUniform1i uniform_tex = shaderUniforms.make1i("tex");
/*  254 */   public static ShaderUniform1i uniform_heldItemId = shaderUniforms.make1i("heldItemId");
/*  255 */   public static ShaderUniform1i uniform_heldBlockLightValue = shaderUniforms.make1i("heldBlockLightValue");
/*  256 */   public static ShaderUniform1i uniform_heldItemId2 = shaderUniforms.make1i("heldItemId2");
/*  257 */   public static ShaderUniform1i uniform_heldBlockLightValue2 = shaderUniforms.make1i("heldBlockLightValue2");
/*  258 */   public static ShaderUniform1i uniform_fogMode = shaderUniforms.make1i("fogMode");
/*  259 */   public static ShaderUniform1f uniform_fogDensity = shaderUniforms.make1f("fogDensity");
/*  260 */   public static ShaderUniform3f uniform_fogColor = shaderUniforms.make3f("fogColor");
/*  261 */   public static ShaderUniform3f uniform_skyColor = shaderUniforms.make3f("skyColor");
/*  262 */   public static ShaderUniform1i uniform_worldTime = shaderUniforms.make1i("worldTime");
/*  263 */   public static ShaderUniform1i uniform_worldDay = shaderUniforms.make1i("worldDay");
/*  264 */   public static ShaderUniform1i uniform_moonPhase = shaderUniforms.make1i("moonPhase");
/*  265 */   public static ShaderUniform1i uniform_frameCounter = shaderUniforms.make1i("frameCounter");
/*  266 */   public static ShaderUniform1f uniform_frameTime = shaderUniforms.make1f("frameTime");
/*  267 */   public static ShaderUniform1f uniform_frameTimeCounter = shaderUniforms.make1f("frameTimeCounter");
/*  268 */   public static ShaderUniform1f uniform_sunAngle = shaderUniforms.make1f("sunAngle");
/*  269 */   public static ShaderUniform1f uniform_shadowAngle = shaderUniforms.make1f("shadowAngle");
/*  270 */   public static ShaderUniform1f uniform_rainStrength = shaderUniforms.make1f("rainStrength");
/*  271 */   public static ShaderUniform1f uniform_aspectRatio = shaderUniforms.make1f("aspectRatio");
/*  272 */   public static ShaderUniform1f uniform_viewWidth = shaderUniforms.make1f("viewWidth");
/*  273 */   public static ShaderUniform1f uniform_viewHeight = shaderUniforms.make1f("viewHeight");
/*  274 */   public static ShaderUniform1f uniform_near = shaderUniforms.make1f("near");
/*  275 */   public static ShaderUniform1f uniform_far = shaderUniforms.make1f("far");
/*  276 */   public static ShaderUniform3f uniform_sunPosition = shaderUniforms.make3f("sunPosition");
/*  277 */   public static ShaderUniform3f uniform_moonPosition = shaderUniforms.make3f("moonPosition");
/*  278 */   public static ShaderUniform3f uniform_shadowLightPosition = shaderUniforms.make3f("shadowLightPosition");
/*  279 */   public static ShaderUniform3f uniform_upPosition = shaderUniforms.make3f("upPosition");
/*  280 */   public static ShaderUniform3f uniform_previousCameraPosition = shaderUniforms.make3f("previousCameraPosition");
/*  281 */   public static ShaderUniform3f uniform_cameraPosition = shaderUniforms.make3f("cameraPosition");
/*  282 */   public static ShaderUniformM4 uniform_gbufferModelView = shaderUniforms.makeM4("gbufferModelView");
/*  283 */   public static ShaderUniformM4 uniform_gbufferModelViewInverse = shaderUniforms.makeM4("gbufferModelViewInverse");
/*  284 */   public static ShaderUniformM4 uniform_gbufferPreviousProjection = shaderUniforms.makeM4("gbufferPreviousProjection");
/*  285 */   public static ShaderUniformM4 uniform_gbufferProjection = shaderUniforms.makeM4("gbufferProjection");
/*  286 */   public static ShaderUniformM4 uniform_gbufferProjectionInverse = shaderUniforms.makeM4("gbufferProjectionInverse");
/*  287 */   public static ShaderUniformM4 uniform_gbufferPreviousModelView = shaderUniforms.makeM4("gbufferPreviousModelView");
/*  288 */   public static ShaderUniformM4 uniform_shadowProjection = shaderUniforms.makeM4("shadowProjection");
/*  289 */   public static ShaderUniformM4 uniform_shadowProjectionInverse = shaderUniforms.makeM4("shadowProjectionInverse");
/*  290 */   public static ShaderUniformM4 uniform_shadowModelView = shaderUniforms.makeM4("shadowModelView");
/*  291 */   public static ShaderUniformM4 uniform_shadowModelViewInverse = shaderUniforms.makeM4("shadowModelViewInverse");
/*  292 */   public static ShaderUniform1f uniform_wetness = shaderUniforms.make1f("wetness");
/*  293 */   public static ShaderUniform1f uniform_eyeAltitude = shaderUniforms.make1f("eyeAltitude");
/*  294 */   public static ShaderUniform2i uniform_eyeBrightness = shaderUniforms.make2i("eyeBrightness");
/*  295 */   public static ShaderUniform2i uniform_eyeBrightnessSmooth = shaderUniforms.make2i("eyeBrightnessSmooth");
/*  296 */   public static ShaderUniform2i uniform_terrainTextureSize = shaderUniforms.make2i("terrainTextureSize");
/*  297 */   public static ShaderUniform1i uniform_terrainIconSize = shaderUniforms.make1i("terrainIconSize");
/*  298 */   public static ShaderUniform1i uniform_isEyeInWater = shaderUniforms.make1i("isEyeInWater");
/*  299 */   public static ShaderUniform1f uniform_nightVision = shaderUniforms.make1f("nightVision");
/*  300 */   public static ShaderUniform1f uniform_blindness = shaderUniforms.make1f("blindness");
/*  301 */   public static ShaderUniform1f uniform_screenBrightness = shaderUniforms.make1f("screenBrightness");
/*  302 */   public static ShaderUniform1i uniform_hideGUI = shaderUniforms.make1i("hideGUI");
/*  303 */   public static ShaderUniform1f uniform_centerDepthSmooth = shaderUniforms.make1f("centerDepthSmooth");
/*  304 */   public static ShaderUniform2i uniform_atlasSize = shaderUniforms.make2i("atlasSize");
/*  305 */   public static ShaderUniform4i uniform_blendFunc = shaderUniforms.make4i("blendFunc");
/*  306 */   public static ShaderUniform1i uniform_instanceId = shaderUniforms.make1i("instanceId");
/*      */   static double previousCameraPositionX;
/*      */   static double previousCameraPositionY;
/*      */   static double previousCameraPositionZ;
/*      */   static double cameraPositionX;
/*      */   static double cameraPositionY;
/*      */   static double cameraPositionZ;
/*      */   static int cameraOffsetX;
/*      */   static int cameraOffsetZ;
/*  315 */   static int shadowPassInterval = 0;
/*      */   public static boolean needResizeShadow = false;
/*  317 */   static int shadowMapWidth = 1024;
/*  318 */   static int shadowMapHeight = 1024;
/*  319 */   static int spShadowMapWidth = 1024;
/*  320 */   static int spShadowMapHeight = 1024;
/*  321 */   static float shadowMapFOV = 90.0F;
/*  322 */   static float shadowMapHalfPlane = 160.0F;
/*      */   static boolean shadowMapIsOrtho = true;
/*  324 */   static float shadowDistanceRenderMul = -1.0F;
/*  325 */   static int shadowPassCounter = 0;
/*      */   static int preShadowPassThirdPersonView;
/*      */   public static boolean shouldSkipDefaultShadow = false;
/*      */   static boolean waterShadowEnabled = false;
/*      */   static final int MaxDrawBuffers = 8;
/*      */   static final int MaxColorBuffers = 8;
/*      */   static final int MaxDepthBuffers = 3;
/*      */   static final int MaxShadowColorBuffers = 8;
/*      */   static final int MaxShadowDepthBuffers = 2;
/*  334 */   static int usedColorBuffers = 0;
/*  335 */   static int usedDepthBuffers = 0;
/*  336 */   static int usedShadowColorBuffers = 0;
/*  337 */   static int usedShadowDepthBuffers = 0;
/*  338 */   static int usedColorAttachs = 0;
/*  339 */   static int usedDrawBuffers = 0;
/*  340 */   static int dfb = 0;
/*  341 */   static int sfb = 0;
/*  342 */   private static int[] gbuffersFormat = new int[8];
/*  343 */   public static boolean[] gbuffersClear = new boolean[8];
/*  344 */   public static Vector4f[] gbuffersClearColor = new Vector4f[8];
/*  345 */   private static Programs programs = new Programs();
/*  346 */   public static final Program ProgramNone = programs.getProgramNone();
/*  347 */   public static final Program ProgramShadow = programs.makeShadow("shadow", ProgramNone);
/*  348 */   public static final Program ProgramShadowSolid = programs.makeShadow("shadow_solid", ProgramShadow);
/*  349 */   public static final Program ProgramShadowCutout = programs.makeShadow("shadow_cutout", ProgramShadow);
/*  350 */   public static final Program ProgramBasic = programs.makeGbuffers("gbuffers_basic", ProgramNone);
/*  351 */   public static final Program ProgramTextured = programs.makeGbuffers("gbuffers_textured", ProgramBasic);
/*  352 */   public static final Program ProgramTexturedLit = programs.makeGbuffers("gbuffers_textured_lit", ProgramTextured);
/*  353 */   public static final Program ProgramSkyBasic = programs.makeGbuffers("gbuffers_skybasic", ProgramBasic);
/*  354 */   public static final Program ProgramSkyTextured = programs.makeGbuffers("gbuffers_skytextured", ProgramTextured);
/*  355 */   public static final Program ProgramClouds = programs.makeGbuffers("gbuffers_clouds", ProgramTextured);
/*  356 */   public static final Program ProgramTerrain = programs.makeGbuffers("gbuffers_terrain", ProgramTexturedLit);
/*  357 */   public static final Program ProgramTerrainSolid = programs.makeGbuffers("gbuffers_terrain_solid", ProgramTerrain);
/*  358 */   public static final Program ProgramTerrainCutoutMip = programs.makeGbuffers("gbuffers_terrain_cutout_mip", ProgramTerrain);
/*  359 */   public static final Program ProgramTerrainCutout = programs.makeGbuffers("gbuffers_terrain_cutout", ProgramTerrain);
/*  360 */   public static final Program ProgramDamagedBlock = programs.makeGbuffers("gbuffers_damagedblock", ProgramTerrain);
/*  361 */   public static final Program ProgramBlock = programs.makeGbuffers("gbuffers_block", ProgramTerrain);
/*  362 */   public static final Program ProgramBeaconBeam = programs.makeGbuffers("gbuffers_beaconbeam", ProgramTextured);
/*  363 */   public static final Program ProgramItem = programs.makeGbuffers("gbuffers_item", ProgramTexturedLit);
/*  364 */   public static final Program ProgramEntities = programs.makeGbuffers("gbuffers_entities", ProgramTexturedLit);
/*  365 */   public static final Program ProgramEntitiesGlowing = programs.makeGbuffers("gbuffers_entities_glowing", ProgramEntities);
/*  366 */   public static final Program ProgramArmorGlint = programs.makeGbuffers("gbuffers_armor_glint", ProgramTextured);
/*  367 */   public static final Program ProgramSpiderEyes = programs.makeGbuffers("gbuffers_spidereyes", ProgramTextured);
/*  368 */   public static final Program ProgramHand = programs.makeGbuffers("gbuffers_hand", ProgramTexturedLit);
/*  369 */   public static final Program ProgramWeather = programs.makeGbuffers("gbuffers_weather", ProgramTexturedLit);
/*  370 */   public static final Program ProgramDeferredPre = programs.makeVirtual("deferred_pre");
/*  371 */   public static final Program[] ProgramsDeferred = programs.makeDeferreds("deferred", 16);
/*  372 */   public static final Program ProgramDeferred = ProgramsDeferred[0];
/*  373 */   public static final Program ProgramWater = programs.makeGbuffers("gbuffers_water", ProgramTerrain);
/*  374 */   public static final Program ProgramHandWater = programs.makeGbuffers("gbuffers_hand_water", ProgramHand);
/*  375 */   public static final Program ProgramCompositePre = programs.makeVirtual("composite_pre");
/*  376 */   public static final Program[] ProgramsComposite = programs.makeComposites("composite", 16);
/*  377 */   public static final Program ProgramComposite = ProgramsComposite[0];
/*  378 */   public static final Program ProgramFinal = programs.makeComposite("final");
/*  379 */   public static final int ProgramCount = programs.getCount();
/*  380 */   public static final Program[] ProgramsAll = programs.getPrograms();
/*  381 */   public static Program activeProgram = ProgramNone;
/*  382 */   public static int activeProgramID = 0;
/*  383 */   private static ProgramStack programStack = new ProgramStack();
/*      */   private static boolean hasDeferredPrograms = false;
/*  385 */   static IntBuffer activeDrawBuffers = null;
/*  386 */   private static int activeCompositeMipmapSetting = 0;
/*  387 */   public static Properties loadedShaders = null;
/*  388 */   public static Properties shadersConfig = null;
/*  389 */   public static ITextureObject defaultTexture = null;
/*  390 */   public static boolean[] shadowHardwareFilteringEnabled = new boolean[2];
/*  391 */   public static boolean[] shadowMipmapEnabled = new boolean[2];
/*  392 */   public static boolean[] shadowFilterNearest = new boolean[2];
/*  393 */   public static boolean[] shadowColorMipmapEnabled = new boolean[8];
/*  394 */   public static boolean[] shadowColorFilterNearest = new boolean[8];
/*      */   public static boolean configTweakBlockDamage = false;
/*      */   public static boolean configCloudShadow = false;
/*  397 */   public static float configHandDepthMul = 0.125F;
/*  398 */   public static float configRenderResMul = 1.0F;
/*  399 */   public static float configShadowResMul = 1.0F;
/*  400 */   public static int configTexMinFilB = 0;
/*  401 */   public static int configTexMinFilN = 0;
/*  402 */   public static int configTexMinFilS = 0;
/*  403 */   public static int configTexMagFilB = 0;
/*  404 */   public static int configTexMagFilN = 0;
/*  405 */   public static int configTexMagFilS = 0;
/*      */   public static boolean configShadowClipFrustrum = true;
/*      */   public static boolean configNormalMap = true;
/*      */   public static boolean configSpecularMap = true;
/*  409 */   public static PropertyDefaultTrueFalse configOldLighting = new PropertyDefaultTrueFalse("oldLighting", "Classic Lighting", 0);
/*  410 */   public static PropertyDefaultTrueFalse configOldHandLight = new PropertyDefaultTrueFalse("oldHandLight", "Old Hand Light", 0);
/*  411 */   public static int configAntialiasingLevel = 0;
/*      */   public static final int texMinFilRange = 3;
/*      */   public static final int texMagFilRange = 2;
/*  414 */   public static final String[] texMinFilDesc = new String[] { "Nearest", "Nearest-Nearest", "Nearest-Linear" };
/*  415 */   public static final String[] texMagFilDesc = new String[] { "Nearest", "Linear" };
/*  416 */   public static final int[] texMinFilValue = new int[] { 9728, 9984, 9986 };
/*  417 */   public static final int[] texMagFilValue = new int[] { 9728, 9729 };
/*  418 */   private static IShaderPack shaderPack = null;
/*      */   public static boolean shaderPackLoaded = false;
/*      */   public static String currentShaderName;
/*      */   public static final String SHADER_PACK_NAME_NONE = "OFF";
/*      */   public static final String SHADER_PACK_NAME_DEFAULT = "(internal)";
/*      */   public static final String SHADER_PACKS_DIR_NAME = "shaderpacks";
/*      */   public static final String OPTIONS_FILE_NAME = "optionsshaders.txt";
/*      */   public static final File shaderPacksDir;
/*      */   static File configFile;
/*  427 */   private static ShaderOption[] shaderPackOptions = null;
/*  428 */   private static Set<String> shaderPackOptionSliders = null;
/*  429 */   static ShaderProfile[] shaderPackProfiles = null;
/*  430 */   static Map<String, ScreenShaderOptions> shaderPackGuiScreens = null;
/*  431 */   static Map<String, IExpressionBool> shaderPackProgramConditions = new HashMap<>();
/*      */   public static final String PATH_SHADERS_PROPERTIES = "/shaders/shaders.properties";
/*  433 */   public static PropertyDefaultFastFancyOff shaderPackClouds = new PropertyDefaultFastFancyOff("clouds", "Clouds", 0);
/*  434 */   public static PropertyDefaultTrueFalse shaderPackOldLighting = new PropertyDefaultTrueFalse("oldLighting", "Classic Lighting", 0);
/*  435 */   public static PropertyDefaultTrueFalse shaderPackOldHandLight = new PropertyDefaultTrueFalse("oldHandLight", "Old Hand Light", 0);
/*  436 */   public static PropertyDefaultTrueFalse shaderPackDynamicHandLight = new PropertyDefaultTrueFalse("dynamicHandLight", "Dynamic Hand Light", 0);
/*  437 */   public static PropertyDefaultTrueFalse shaderPackShadowTranslucent = new PropertyDefaultTrueFalse("shadowTranslucent", "Shadow Translucent", 0);
/*  438 */   public static PropertyDefaultTrueFalse shaderPackUnderwaterOverlay = new PropertyDefaultTrueFalse("underwaterOverlay", "Underwater Overlay", 0);
/*  439 */   public static PropertyDefaultTrueFalse shaderPackSun = new PropertyDefaultTrueFalse("sun", "Sun", 0);
/*  440 */   public static PropertyDefaultTrueFalse shaderPackMoon = new PropertyDefaultTrueFalse("moon", "Moon", 0);
/*  441 */   public static PropertyDefaultTrueFalse shaderPackVignette = new PropertyDefaultTrueFalse("vignette", "Vignette", 0);
/*  442 */   public static PropertyDefaultTrueFalse shaderPackBackFaceSolid = new PropertyDefaultTrueFalse("backFace.solid", "Back-face Solid", 0);
/*  443 */   public static PropertyDefaultTrueFalse shaderPackBackFaceCutout = new PropertyDefaultTrueFalse("backFace.cutout", "Back-face Cutout", 0);
/*  444 */   public static PropertyDefaultTrueFalse shaderPackBackFaceCutoutMipped = new PropertyDefaultTrueFalse("backFace.cutoutMipped", "Back-face Cutout Mipped", 0);
/*  445 */   public static PropertyDefaultTrueFalse shaderPackBackFaceTranslucent = new PropertyDefaultTrueFalse("backFace.translucent", "Back-face Translucent", 0);
/*  446 */   public static PropertyDefaultTrueFalse shaderPackRainDepth = new PropertyDefaultTrueFalse("rain.depth", "Rain Depth", 0);
/*  447 */   public static PropertyDefaultTrueFalse shaderPackBeaconBeamDepth = new PropertyDefaultTrueFalse("beacon.beam.depth", "Rain Depth", 0);
/*  448 */   public static PropertyDefaultTrueFalse shaderPackSeparateAo = new PropertyDefaultTrueFalse("separateAo", "Separate AO", 0);
/*  449 */   public static PropertyDefaultTrueFalse shaderPackFrustumCulling = new PropertyDefaultTrueFalse("frustum.culling", "Frustum Culling", 0);
/*  450 */   private static Map<String, String> shaderPackResources = new HashMap<>();
/*  451 */   private static World currentWorld = null;
/*  452 */   private static List<Integer> shaderPackDimensions = new ArrayList<>();
/*  453 */   private static ICustomTexture[] customTexturesGbuffers = null;
/*  454 */   private static ICustomTexture[] customTexturesComposite = null;
/*  455 */   private static ICustomTexture[] customTexturesDeferred = null;
/*  456 */   private static String noiseTexturePath = null;
/*  457 */   private static CustomUniforms customUniforms = null;
/*      */   private static final int STAGE_GBUFFERS = 0;
/*      */   private static final int STAGE_COMPOSITE = 1;
/*      */   private static final int STAGE_DEFERRED = 2;
/*  461 */   private static final String[] STAGE_NAMES = new String[] { "gbuffers", "composite", "deferred" };
/*      */   public static final boolean enableShadersOption = true;
/*      */   private static final boolean enableShadersDebug = true;
/*  464 */   public static final boolean saveFinalShaders = System.getProperty("shaders.debug.save", "false").equals("true");
/*  465 */   public static float blockLightLevel05 = 0.5F;
/*  466 */   public static float blockLightLevel06 = 0.6F;
/*  467 */   public static float blockLightLevel08 = 0.8F;
/*  468 */   public static float aoLevel = -1.0F;
/*  469 */   public static float sunPathRotation = 0.0F;
/*  470 */   public static float shadowAngleInterval = 0.0F;
/*  471 */   public static int fogMode = 0;
/*  472 */   public static float fogDensity = 0.0F;
/*      */   public static float fogColorR;
/*      */   public static float fogColorG;
/*      */   public static float fogColorB;
/*  476 */   public static float shadowIntervalSize = 2.0F;
/*  477 */   public static int terrainIconSize = 16;
/*  478 */   public static int[] terrainTextureSize = new int[2];
/*      */   private static ICustomTexture noiseTexture;
/*      */   private static boolean noiseTextureEnabled = false;
/*  481 */   private static int noiseTextureResolution = 256;
/*  482 */   static final int[] colorTextureImageUnit = new int[] { 0, 1, 2, 3, 7, 8, 9, 10 };
/*  483 */   private static final int bigBufferSize = (285 + 8 * ProgramCount) * 4;
/*  484 */   private static final ByteBuffer bigBuffer = (ByteBuffer)BufferUtils.createByteBuffer(bigBufferSize).limit(0);
/*  485 */   static final float[] faProjection = new float[16];
/*  486 */   static final float[] faProjectionInverse = new float[16];
/*  487 */   static final float[] faModelView = new float[16];
/*  488 */   static final float[] faModelViewInverse = new float[16];
/*  489 */   static final float[] faShadowProjection = new float[16];
/*  490 */   static final float[] faShadowProjectionInverse = new float[16];
/*  491 */   static final float[] faShadowModelView = new float[16];
/*  492 */   static final float[] faShadowModelViewInverse = new float[16];
/*  493 */   static final FloatBuffer projection = nextFloatBuffer(16);
/*  494 */   static final FloatBuffer projectionInverse = nextFloatBuffer(16);
/*  495 */   static final FloatBuffer modelView = nextFloatBuffer(16);
/*  496 */   static final FloatBuffer modelViewInverse = nextFloatBuffer(16);
/*  497 */   static final FloatBuffer shadowProjection = nextFloatBuffer(16);
/*  498 */   static final FloatBuffer shadowProjectionInverse = nextFloatBuffer(16);
/*  499 */   static final FloatBuffer shadowModelView = nextFloatBuffer(16);
/*  500 */   static final FloatBuffer shadowModelViewInverse = nextFloatBuffer(16);
/*  501 */   static final FloatBuffer previousProjection = nextFloatBuffer(16);
/*  502 */   static final FloatBuffer previousModelView = nextFloatBuffer(16);
/*  503 */   static final FloatBuffer tempMatrixDirectBuffer = nextFloatBuffer(16);
/*  504 */   static final FloatBuffer tempDirectFloatBuffer = nextFloatBuffer(16);
/*  505 */   static final IntBuffer dfbColorTextures = nextIntBuffer(16);
/*  506 */   static final IntBuffer dfbDepthTextures = nextIntBuffer(3);
/*  507 */   static final IntBuffer sfbColorTextures = nextIntBuffer(8);
/*  508 */   static final IntBuffer sfbDepthTextures = nextIntBuffer(2);
/*  509 */   static final IntBuffer dfbDrawBuffers = nextIntBuffer(8);
/*  510 */   static final IntBuffer sfbDrawBuffers = nextIntBuffer(8);
/*  511 */   static final IntBuffer drawBuffersNone = (IntBuffer)nextIntBuffer(8).limit(0);
/*  512 */   static final IntBuffer drawBuffersColorAtt0 = (IntBuffer)nextIntBuffer(8).put(36064).position(0).limit(1);
/*  513 */   static final FlipTextures dfbColorTexturesFlip = new FlipTextures(dfbColorTextures, 8);
/*      */   static Map<Block, Integer> mapBlockToEntityData;
/*  515 */   private static final String[] formatNames = new String[] { "R8", "RG8", "RGB8", "RGBA8", "R8_SNORM", "RG8_SNORM", "RGB8_SNORM", "RGBA8_SNORM", "R16", "RG16", "RGB16", "RGBA16", "R16_SNORM", "RG16_SNORM", "RGB16_SNORM", "RGBA16_SNORM", "R16F", "RG16F", "RGB16F", "RGBA16F", "R32F", "RG32F", "RGB32F", "RGBA32F", "R32I", "RG32I", "RGB32I", "RGBA32I", "R32UI", "RG32UI", "RGB32UI", "RGBA32UI", "R3_G3_B2", "RGB5_A1", "RGB10_A2", "R11F_G11F_B10F", "RGB9_E5" };
/*  516 */   private static final int[] formatIds = new int[] { 33321, 33323, 32849, 32856, 36756, 36757, 36758, 36759, 33322, 33324, 32852, 32859, 36760, 36761, 36762, 36763, 33325, 33327, 34843, 34842, 33326, 33328, 34837, 34836, 33333, 33339, 36227, 36226, 33334, 33340, 36209, 36208, 10768, 32855, 32857, 35898, 35901 };
/*  517 */   private static final Pattern patternLoadEntityDataMap = Pattern.compile("\\s*([\\w:]+)\\s*=\\s*([-]?\\d+)\\s*");
/*  518 */   public static int[] entityData = new int[32];
/*  519 */   public static int entityDataIndex = 0;
/*      */   
/*      */   private static ByteBuffer nextByteBuffer(int size) {
/*  522 */     ByteBuffer bytebuffer = bigBuffer;
/*  523 */     int i = bytebuffer.limit();
/*  524 */     bytebuffer.position(i).limit(i + size);
/*  525 */     return bytebuffer.slice();
/*      */   }
/*      */   
/*      */   public static IntBuffer nextIntBuffer(int size) {
/*  529 */     ByteBuffer bytebuffer = bigBuffer;
/*  530 */     int i = bytebuffer.limit();
/*  531 */     bytebuffer.position(i).limit(i + size * 4);
/*  532 */     return bytebuffer.asIntBuffer();
/*      */   }
/*      */   
/*      */   private static FloatBuffer nextFloatBuffer(int size) {
/*  536 */     ByteBuffer bytebuffer = bigBuffer;
/*  537 */     int i = bytebuffer.limit();
/*  538 */     bytebuffer.position(i).limit(i + size * 4);
/*  539 */     return bytebuffer.asFloatBuffer();
/*      */   }
/*      */   
/*      */   private static IntBuffer[] nextIntBufferArray(int count, int size) {
/*  543 */     IntBuffer[] aintbuffer = new IntBuffer[count];
/*      */     
/*  545 */     for (int i = 0; i < count; i++) {
/*  546 */       aintbuffer[i] = nextIntBuffer(size);
/*      */     }
/*      */     
/*  549 */     return aintbuffer;
/*      */   }
/*      */   
/*      */   public static void loadConfig() {
/*  553 */     SMCLog.info("Load shaders configuration.");
/*      */     
/*      */     try {
/*  556 */       if (!shaderPacksDir.exists()) {
/*  557 */         shaderPacksDir.mkdir();
/*      */       }
/*  559 */     } catch (Exception var8) {
/*  560 */       SMCLog.severe("Failed to open the shaderpacks directory: " + shaderPacksDir);
/*      */     } 
/*      */     
/*  563 */     shadersConfig = (Properties)new PropertiesOrdered();
/*  564 */     shadersConfig.setProperty(EnumShaderOption.SHADER_PACK.getPropertyKey(), "");
/*      */     
/*  566 */     if (configFile.exists()) {
/*      */       try {
/*  568 */         FileReader filereader = new FileReader(configFile);
/*  569 */         shadersConfig.load(filereader);
/*  570 */         filereader.close();
/*  571 */       } catch (Exception exception) {}
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*  576 */     if (!configFile.exists()) {
/*      */       try {
/*  578 */         storeConfig();
/*  579 */       } catch (Exception exception) {}
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*  584 */     EnumShaderOption[] aenumshaderoption = EnumShaderOption.values();
/*      */     
/*  586 */     for (int i = 0; i < aenumshaderoption.length; i++) {
/*  587 */       EnumShaderOption enumshaderoption = aenumshaderoption[i];
/*  588 */       String s = enumshaderoption.getPropertyKey();
/*  589 */       String s1 = enumshaderoption.getValueDefault();
/*  590 */       String s2 = shadersConfig.getProperty(s, s1);
/*  591 */       setEnumShaderOption(enumshaderoption, s2);
/*      */     } 
/*      */     
/*  594 */     loadShaderPack();
/*      */   }
/*      */   
/*      */   private static void setEnumShaderOption(EnumShaderOption eso, String str) {
/*  598 */     if (str == null) {
/*  599 */       str = eso.getValueDefault();
/*      */     }
/*      */     
/*  602 */     switch (eso) {
/*      */       case null:
/*  604 */         configAntialiasingLevel = Config.parseInt(str, 0);
/*      */         return;
/*      */       
/*      */       case NORMAL_MAP:
/*  608 */         configNormalMap = Config.parseBoolean(str, true);
/*      */         return;
/*      */       
/*      */       case SPECULAR_MAP:
/*  612 */         configSpecularMap = Config.parseBoolean(str, true);
/*      */         return;
/*      */       
/*      */       case RENDER_RES_MUL:
/*  616 */         configRenderResMul = Config.parseFloat(str, 1.0F);
/*      */         return;
/*      */       
/*      */       case SHADOW_RES_MUL:
/*  620 */         configShadowResMul = Config.parseFloat(str, 1.0F);
/*      */         return;
/*      */       
/*      */       case HAND_DEPTH_MUL:
/*  624 */         configHandDepthMul = Config.parseFloat(str, 0.125F);
/*      */         return;
/*      */       
/*      */       case CLOUD_SHADOW:
/*  628 */         configCloudShadow = Config.parseBoolean(str, true);
/*      */         return;
/*      */       
/*      */       case OLD_HAND_LIGHT:
/*  632 */         configOldHandLight.setPropertyValue(str);
/*      */         return;
/*      */       
/*      */       case OLD_LIGHTING:
/*  636 */         configOldLighting.setPropertyValue(str);
/*      */         return;
/*      */       
/*      */       case SHADER_PACK:
/*  640 */         currentShaderName = str;
/*      */         return;
/*      */       
/*      */       case TWEAK_BLOCK_DAMAGE:
/*  644 */         configTweakBlockDamage = Config.parseBoolean(str, true);
/*      */         return;
/*      */       
/*      */       case SHADOW_CLIP_FRUSTRUM:
/*  648 */         configShadowClipFrustrum = Config.parseBoolean(str, true);
/*      */         return;
/*      */       
/*      */       case TEX_MIN_FIL_B:
/*  652 */         configTexMinFilB = Config.parseInt(str, 0);
/*      */         return;
/*      */       
/*      */       case TEX_MIN_FIL_N:
/*  656 */         configTexMinFilN = Config.parseInt(str, 0);
/*      */         return;
/*      */       
/*      */       case TEX_MIN_FIL_S:
/*  660 */         configTexMinFilS = Config.parseInt(str, 0);
/*      */         return;
/*      */       
/*      */       case TEX_MAG_FIL_B:
/*  664 */         configTexMagFilB = Config.parseInt(str, 0);
/*      */         return;
/*      */       
/*      */       case TEX_MAG_FIL_N:
/*  668 */         configTexMagFilB = Config.parseInt(str, 0);
/*      */         return;
/*      */       
/*      */       case TEX_MAG_FIL_S:
/*  672 */         configTexMagFilB = Config.parseInt(str, 0);
/*      */         return;
/*      */     } 
/*      */     
/*  676 */     throw new IllegalArgumentException("Unknown option: " + eso);
/*      */   }
/*      */ 
/*      */   
/*      */   public static void storeConfig() {
/*  681 */     SMCLog.info("Save shaders configuration.");
/*      */     
/*  683 */     if (shadersConfig == null) {
/*  684 */       shadersConfig = (Properties)new PropertiesOrdered();
/*      */     }
/*      */     
/*  687 */     EnumShaderOption[] aenumshaderoption = EnumShaderOption.values();
/*      */     
/*  689 */     for (int i = 0; i < aenumshaderoption.length; i++) {
/*  690 */       EnumShaderOption enumshaderoption = aenumshaderoption[i];
/*  691 */       String s = enumshaderoption.getPropertyKey();
/*  692 */       String s1 = getEnumShaderOption(enumshaderoption);
/*  693 */       shadersConfig.setProperty(s, s1);
/*      */     } 
/*      */     
/*      */     try {
/*  697 */       FileWriter filewriter = new FileWriter(configFile);
/*  698 */       shadersConfig.store(filewriter, (String)null);
/*  699 */       filewriter.close();
/*  700 */     } catch (Exception exception) {
/*  701 */       SMCLog.severe("Error saving configuration: " + exception.getClass().getName() + ": " + exception.getMessage());
/*      */     } 
/*      */   }
/*      */   
/*      */   public static String getEnumShaderOption(EnumShaderOption eso) {
/*  706 */     switch (eso) {
/*      */       case null:
/*  708 */         return Integer.toString(configAntialiasingLevel);
/*      */       
/*      */       case NORMAL_MAP:
/*  711 */         return Boolean.toString(configNormalMap);
/*      */       
/*      */       case SPECULAR_MAP:
/*  714 */         return Boolean.toString(configSpecularMap);
/*      */       
/*      */       case RENDER_RES_MUL:
/*  717 */         return Float.toString(configRenderResMul);
/*      */       
/*      */       case SHADOW_RES_MUL:
/*  720 */         return Float.toString(configShadowResMul);
/*      */       
/*      */       case HAND_DEPTH_MUL:
/*  723 */         return Float.toString(configHandDepthMul);
/*      */       
/*      */       case CLOUD_SHADOW:
/*  726 */         return Boolean.toString(configCloudShadow);
/*      */       
/*      */       case OLD_HAND_LIGHT:
/*  729 */         return configOldHandLight.getPropertyValue();
/*      */       
/*      */       case OLD_LIGHTING:
/*  732 */         return configOldLighting.getPropertyValue();
/*      */       
/*      */       case SHADER_PACK:
/*  735 */         return currentShaderName;
/*      */       
/*      */       case TWEAK_BLOCK_DAMAGE:
/*  738 */         return Boolean.toString(configTweakBlockDamage);
/*      */       
/*      */       case SHADOW_CLIP_FRUSTRUM:
/*  741 */         return Boolean.toString(configShadowClipFrustrum);
/*      */       
/*      */       case TEX_MIN_FIL_B:
/*  744 */         return Integer.toString(configTexMinFilB);
/*      */       
/*      */       case TEX_MIN_FIL_N:
/*  747 */         return Integer.toString(configTexMinFilN);
/*      */       
/*      */       case TEX_MIN_FIL_S:
/*  750 */         return Integer.toString(configTexMinFilS);
/*      */       
/*      */       case TEX_MAG_FIL_B:
/*  753 */         return Integer.toString(configTexMagFilB);
/*      */       
/*      */       case TEX_MAG_FIL_N:
/*  756 */         return Integer.toString(configTexMagFilB);
/*      */       
/*      */       case TEX_MAG_FIL_S:
/*  759 */         return Integer.toString(configTexMagFilB);
/*      */     } 
/*      */     
/*  762 */     throw new IllegalArgumentException("Unknown option: " + eso);
/*      */   }
/*      */ 
/*      */   
/*      */   public static void setShaderPack(String par1name) {
/*  767 */     currentShaderName = par1name;
/*  768 */     shadersConfig.setProperty(EnumShaderOption.SHADER_PACK.getPropertyKey(), par1name);
/*  769 */     loadShaderPack();
/*      */   }
/*      */   
/*      */   public static void loadShaderPack() {
/*  773 */     boolean flag = shaderPackLoaded;
/*  774 */     boolean flag1 = isOldLighting();
/*      */     
/*  776 */     if (mc.renderGlobal != null) {
/*  777 */       mc.renderGlobal.pauseChunkUpdates();
/*      */     }
/*      */     
/*  780 */     shaderPackLoaded = false;
/*      */     
/*  782 */     if (shaderPack != null) {
/*  783 */       shaderPack.close();
/*  784 */       shaderPack = null;
/*  785 */       shaderPackResources.clear();
/*  786 */       shaderPackDimensions.clear();
/*  787 */       shaderPackOptions = null;
/*  788 */       shaderPackOptionSliders = null;
/*  789 */       shaderPackProfiles = null;
/*  790 */       shaderPackGuiScreens = null;
/*  791 */       shaderPackProgramConditions.clear();
/*  792 */       shaderPackClouds.resetValue();
/*  793 */       shaderPackOldHandLight.resetValue();
/*  794 */       shaderPackDynamicHandLight.resetValue();
/*  795 */       shaderPackOldLighting.resetValue();
/*  796 */       resetCustomTextures();
/*  797 */       noiseTexturePath = null;
/*      */     } 
/*      */     
/*  800 */     boolean flag2 = false;
/*      */     
/*  802 */     if (Config.isAntialiasing()) {
/*  803 */       SMCLog.info("Shaders can not be loaded, Antialiasing is enabled: " + Config.getAntialiasingLevel() + "x");
/*  804 */       flag2 = true;
/*      */     } 
/*      */     
/*  807 */     if (Config.isAnisotropicFiltering()) {
/*  808 */       SMCLog.info("Shaders can not be loaded, Anisotropic Filtering is enabled: " + Config.getAnisotropicFilterLevel() + "x");
/*  809 */       flag2 = true;
/*      */     } 
/*      */     
/*  812 */     if (Config.isFastRender()) {
/*  813 */       SMCLog.info("Shaders can not be loaded, Fast Render is enabled.");
/*  814 */       flag2 = true;
/*      */     } 
/*      */     
/*  817 */     String s = shadersConfig.getProperty(EnumShaderOption.SHADER_PACK.getPropertyKey(), "(internal)");
/*      */     
/*  819 */     if (!flag2) {
/*  820 */       shaderPack = getShaderPack(s);
/*  821 */       shaderPackLoaded = (shaderPack != null);
/*      */     } 
/*      */     
/*  824 */     if (shaderPackLoaded) {
/*  825 */       SMCLog.info("Loaded shaderpack: " + getShaderPackName());
/*      */     } else {
/*  827 */       SMCLog.info("No shaderpack loaded.");
/*  828 */       shaderPack = new ShaderPackNone();
/*      */     } 
/*      */     
/*  831 */     if (saveFinalShaders) {
/*  832 */       clearDirectory(new File(shaderPacksDir, "debug"));
/*      */     }
/*      */     
/*  835 */     loadShaderPackResources();
/*  836 */     loadShaderPackDimensions();
/*  837 */     shaderPackOptions = loadShaderPackOptions();
/*  838 */     loadShaderPackProperties();
/*  839 */     boolean flag3 = shaderPackLoaded ^ flag;
/*  840 */     boolean flag4 = isOldLighting() ^ flag1;
/*      */     
/*  842 */     if (flag3 || flag4) {
/*  843 */       DefaultVertexFormats.updateVertexFormats();
/*      */       
/*  845 */       if (Reflector.LightUtil.exists()) {
/*  846 */         Reflector.LightUtil_itemConsumer.setValue(null);
/*  847 */         Reflector.LightUtil_tessellator.setValue(null);
/*      */       } 
/*      */       
/*  850 */       updateBlockLightLevel();
/*      */     } 
/*      */     
/*  853 */     if (mc.getResourcePackRepository() != null) {
/*  854 */       CustomBlockLayers.update();
/*      */     }
/*      */     
/*  857 */     if (mc.renderGlobal != null) {
/*  858 */       mc.renderGlobal.resumeChunkUpdates();
/*      */     }
/*      */     
/*  861 */     if ((flag3 || flag4) && mc.getResourceManager() != null) {
/*  862 */       mc.scheduleResourcesRefresh();
/*      */     }
/*      */   }
/*      */   
/*      */   public static IShaderPack getShaderPack(String name) {
/*  867 */     if (name == null) {
/*  868 */       return null;
/*      */     }
/*  870 */     name = name.trim();
/*      */     
/*  872 */     if (!name.isEmpty() && !name.equals("OFF")) {
/*  873 */       if (name.equals("(internal)")) {
/*  874 */         return new ShaderPackDefault();
/*      */       }
/*      */       try {
/*  877 */         File file1 = new File(shaderPacksDir, name);
/*  878 */         return file1.isDirectory() ? new ShaderPackFolder(name, file1) : ((file1.isFile() && name.toLowerCase().endsWith(".zip")) ? new ShaderPackZip(name, file1) : null);
/*  879 */       } catch (Exception exception) {
/*  880 */         exception.printStackTrace();
/*  881 */         return null;
/*      */       } 
/*      */     } 
/*      */     
/*  885 */     return null;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public static IShaderPack getShaderPack() {
/*  891 */     return shaderPack;
/*      */   }
/*      */   
/*      */   private static void loadShaderPackDimensions() {
/*  895 */     shaderPackDimensions.clear();
/*      */     
/*  897 */     for (int i = -128; i <= 128; i++) {
/*  898 */       String s = "/shaders/world" + i;
/*      */       
/*  900 */       if (shaderPack.hasDirectory(s)) {
/*  901 */         shaderPackDimensions.add(Integer.valueOf(i));
/*      */       }
/*      */     } 
/*      */     
/*  905 */     if (shaderPackDimensions.size() > 0) {
/*  906 */       Integer[] ainteger = shaderPackDimensions.<Integer>toArray(new Integer[shaderPackDimensions.size()]);
/*  907 */       Config.dbg("[Shaders] Worlds: " + Config.arrayToString((Object[])ainteger));
/*      */     } 
/*      */   }
/*      */   
/*      */   private static void loadShaderPackProperties() {
/*  912 */     shaderPackClouds.resetValue();
/*  913 */     shaderPackOldHandLight.resetValue();
/*  914 */     shaderPackDynamicHandLight.resetValue();
/*  915 */     shaderPackOldLighting.resetValue();
/*  916 */     shaderPackShadowTranslucent.resetValue();
/*  917 */     shaderPackUnderwaterOverlay.resetValue();
/*  918 */     shaderPackSun.resetValue();
/*  919 */     shaderPackMoon.resetValue();
/*  920 */     shaderPackVignette.resetValue();
/*  921 */     shaderPackBackFaceSolid.resetValue();
/*  922 */     shaderPackBackFaceCutout.resetValue();
/*  923 */     shaderPackBackFaceCutoutMipped.resetValue();
/*  924 */     shaderPackBackFaceTranslucent.resetValue();
/*  925 */     shaderPackRainDepth.resetValue();
/*  926 */     shaderPackBeaconBeamDepth.resetValue();
/*  927 */     shaderPackSeparateAo.resetValue();
/*  928 */     shaderPackFrustumCulling.resetValue();
/*  929 */     BlockAliases.reset();
/*  930 */     ItemAliases.reset();
/*  931 */     EntityAliases.reset();
/*  932 */     customUniforms = null;
/*      */     
/*  934 */     for (int i = 0; i < ProgramsAll.length; i++) {
/*  935 */       Program program = ProgramsAll[i];
/*  936 */       program.resetProperties();
/*      */     } 
/*      */     
/*  939 */     if (shaderPack != null) {
/*  940 */       BlockAliases.update(shaderPack);
/*  941 */       ItemAliases.update(shaderPack);
/*  942 */       EntityAliases.update(shaderPack);
/*  943 */       String s = "/shaders/shaders.properties";
/*      */       
/*      */       try {
/*  946 */         InputStream inputstream = shaderPack.getResourceAsStream(s);
/*      */         
/*  948 */         if (inputstream == null) {
/*      */           return;
/*      */         }
/*      */         
/*  952 */         inputstream = MacroProcessor.process(inputstream, s);
/*  953 */         PropertiesOrdered propertiesOrdered = new PropertiesOrdered();
/*  954 */         propertiesOrdered.load(inputstream);
/*  955 */         inputstream.close();
/*  956 */         shaderPackClouds.loadFrom((Properties)propertiesOrdered);
/*  957 */         shaderPackOldHandLight.loadFrom((Properties)propertiesOrdered);
/*  958 */         shaderPackDynamicHandLight.loadFrom((Properties)propertiesOrdered);
/*  959 */         shaderPackOldLighting.loadFrom((Properties)propertiesOrdered);
/*  960 */         shaderPackShadowTranslucent.loadFrom((Properties)propertiesOrdered);
/*  961 */         shaderPackUnderwaterOverlay.loadFrom((Properties)propertiesOrdered);
/*  962 */         shaderPackSun.loadFrom((Properties)propertiesOrdered);
/*  963 */         shaderPackVignette.loadFrom((Properties)propertiesOrdered);
/*  964 */         shaderPackMoon.loadFrom((Properties)propertiesOrdered);
/*  965 */         shaderPackBackFaceSolid.loadFrom((Properties)propertiesOrdered);
/*  966 */         shaderPackBackFaceCutout.loadFrom((Properties)propertiesOrdered);
/*  967 */         shaderPackBackFaceCutoutMipped.loadFrom((Properties)propertiesOrdered);
/*  968 */         shaderPackBackFaceTranslucent.loadFrom((Properties)propertiesOrdered);
/*  969 */         shaderPackRainDepth.loadFrom((Properties)propertiesOrdered);
/*  970 */         shaderPackBeaconBeamDepth.loadFrom((Properties)propertiesOrdered);
/*  971 */         shaderPackSeparateAo.loadFrom((Properties)propertiesOrdered);
/*  972 */         shaderPackFrustumCulling.loadFrom((Properties)propertiesOrdered);
/*  973 */         shaderPackOptionSliders = ShaderPackParser.parseOptionSliders((Properties)propertiesOrdered, shaderPackOptions);
/*  974 */         shaderPackProfiles = ShaderPackParser.parseProfiles((Properties)propertiesOrdered, shaderPackOptions);
/*  975 */         shaderPackGuiScreens = ShaderPackParser.parseGuiScreens((Properties)propertiesOrdered, shaderPackProfiles, shaderPackOptions);
/*  976 */         shaderPackProgramConditions = ShaderPackParser.parseProgramConditions((Properties)propertiesOrdered, shaderPackOptions);
/*  977 */         customTexturesGbuffers = loadCustomTextures((Properties)propertiesOrdered, 0);
/*  978 */         customTexturesComposite = loadCustomTextures((Properties)propertiesOrdered, 1);
/*  979 */         customTexturesDeferred = loadCustomTextures((Properties)propertiesOrdered, 2);
/*  980 */         noiseTexturePath = propertiesOrdered.getProperty("texture.noise");
/*      */         
/*  982 */         if (noiseTexturePath != null) {
/*  983 */           noiseTextureEnabled = true;
/*      */         }
/*      */         
/*  986 */         customUniforms = ShaderPackParser.parseCustomUniforms((Properties)propertiesOrdered);
/*  987 */         ShaderPackParser.parseAlphaStates((Properties)propertiesOrdered);
/*  988 */         ShaderPackParser.parseBlendStates((Properties)propertiesOrdered);
/*  989 */         ShaderPackParser.parseRenderScales((Properties)propertiesOrdered);
/*  990 */         ShaderPackParser.parseBuffersFlip((Properties)propertiesOrdered);
/*  991 */       } catch (IOException var3) {
/*  992 */         Config.warn("[Shaders] Error reading: " + s);
/*      */       } 
/*      */     } 
/*      */   }
/*      */   
/*      */   private static ICustomTexture[] loadCustomTextures(Properties props, int stage) {
/*  998 */     String s = "texture." + STAGE_NAMES[stage] + ".";
/*  999 */     Set set = props.keySet();
/* 1000 */     List<ICustomTexture> list = new ArrayList<>();
/*      */     
/* 1002 */     for (Object e : set) {
/* 1003 */       String s1 = (String)e;
/* 1004 */       if (s1.startsWith(s)) {
/* 1005 */         String s2 = StrUtils.removePrefix(s1, s);
/* 1006 */         s2 = StrUtils.removeSuffix(s2, new String[] { ".0", ".1", ".2", ".3", ".4", ".5", ".6", ".7", ".8", ".9" });
/* 1007 */         String s3 = props.getProperty(s1).trim();
/* 1008 */         int i = getTextureIndex(stage, s2);
/*      */         
/* 1010 */         if (i < 0) {
/* 1011 */           SMCLog.warning("Invalid texture name: " + s1); continue;
/*      */         } 
/* 1013 */         ICustomTexture icustomtexture = loadCustomTexture(i, s3);
/*      */         
/* 1015 */         if (icustomtexture != null) {
/* 1016 */           SMCLog.info("Custom texture: " + s1 + " = " + s3);
/* 1017 */           list.add(icustomtexture);
/*      */         } 
/*      */       } 
/*      */     } 
/*      */ 
/*      */     
/* 1023 */     if (list.size() <= 0) {
/* 1024 */       return null;
/*      */     }
/* 1026 */     ICustomTexture[] aicustomtexture = list.<ICustomTexture>toArray(new ICustomTexture[list.size()]);
/* 1027 */     return aicustomtexture;
/*      */   }
/*      */ 
/*      */   
/*      */   private static ICustomTexture loadCustomTexture(int textureUnit, String path) {
/* 1032 */     if (path == null) {
/* 1033 */       return null;
/*      */     }
/* 1035 */     path = path.trim();
/* 1036 */     return (path.indexOf(':') >= 0) ? loadCustomTextureLocation(textureUnit, path) : ((path.indexOf(' ') >= 0) ? loadCustomTextureRaw(textureUnit, path) : loadCustomTextureShaders(textureUnit, path));
/*      */   }
/*      */ 
/*      */   
/*      */   private static ICustomTexture loadCustomTextureLocation(int textureUnit, String path) {
/* 1041 */     String s = path.trim();
/* 1042 */     int i = 0;
/*      */     
/* 1044 */     if (s.startsWith("minecraft:textures/")) {
/* 1045 */       s = StrUtils.addSuffixCheck(s, ".png");
/*      */       
/* 1047 */       if (s.endsWith("_n.png")) {
/* 1048 */         s = StrUtils.replaceSuffix(s, "_n.png", ".png");
/* 1049 */         i = 1;
/* 1050 */       } else if (s.endsWith("_s.png")) {
/* 1051 */         s = StrUtils.replaceSuffix(s, "_s.png", ".png");
/* 1052 */         i = 2;
/*      */       } 
/*      */     } 
/*      */     
/* 1056 */     ResourceLocation resourcelocation = new ResourceLocation(s);
/* 1057 */     CustomTextureLocation customtexturelocation = new CustomTextureLocation(textureUnit, resourcelocation, i);
/* 1058 */     return customtexturelocation;
/*      */   }
/*      */   
/*      */   private static ICustomTexture loadCustomTextureRaw(int textureUnit, String line) {
/* 1062 */     ConnectedParser connectedparser = new ConnectedParser("Shaders");
/* 1063 */     String[] astring = Config.tokenize(line, " ");
/* 1064 */     Deque<String> deque = new ArrayDeque<>(Arrays.asList(astring));
/* 1065 */     String s = deque.poll();
/* 1066 */     TextureType texturetype = (TextureType)connectedparser.parseEnum(deque.poll(), (Enum[])TextureType.values(), "texture type");
/*      */     
/* 1068 */     if (texturetype == null) {
/* 1069 */       SMCLog.warning("Invalid raw texture type: " + line);
/* 1070 */       return null;
/*      */     } 
/* 1072 */     InternalFormat internalformat = (InternalFormat)connectedparser.parseEnum(deque.poll(), (Enum[])InternalFormat.values(), "internal format");
/*      */     
/* 1074 */     if (internalformat == null) {
/* 1075 */       SMCLog.warning("Invalid raw texture internal format: " + line);
/* 1076 */       return null;
/*      */     } 
/* 1078 */     int i = 0;
/* 1079 */     int j = 0;
/* 1080 */     int k = 0;
/*      */     
/* 1082 */     switch (texturetype) {
/*      */       case null:
/* 1084 */         i = connectedparser.parseInt(deque.poll(), -1);
/*      */         break;
/*      */       
/*      */       case TEXTURE_2D:
/* 1088 */         i = connectedparser.parseInt(deque.poll(), -1);
/* 1089 */         j = connectedparser.parseInt(deque.poll(), -1);
/*      */         break;
/*      */       
/*      */       case TEXTURE_3D:
/* 1093 */         i = connectedparser.parseInt(deque.poll(), -1);
/* 1094 */         j = connectedparser.parseInt(deque.poll(), -1);
/* 1095 */         k = connectedparser.parseInt(deque.poll(), -1);
/*      */         break;
/*      */       
/*      */       case TEXTURE_RECTANGLE:
/* 1099 */         i = connectedparser.parseInt(deque.poll(), -1);
/* 1100 */         j = connectedparser.parseInt(deque.poll(), -1);
/*      */         break;
/*      */       
/*      */       default:
/* 1104 */         SMCLog.warning("Invalid raw texture type: " + texturetype);
/* 1105 */         return null;
/*      */     } 
/*      */     
/* 1108 */     if (i >= 0 && j >= 0 && k >= 0) {
/* 1109 */       PixelFormat pixelformat = (PixelFormat)connectedparser.parseEnum(deque.poll(), (Enum[])PixelFormat.values(), "pixel format");
/*      */       
/* 1111 */       if (pixelformat == null) {
/* 1112 */         SMCLog.warning("Invalid raw texture pixel format: " + line);
/* 1113 */         return null;
/*      */       } 
/* 1115 */       PixelType pixeltype = (PixelType)connectedparser.parseEnum(deque.poll(), (Enum[])PixelType.values(), "pixel type");
/*      */       
/* 1117 */       if (pixeltype == null) {
/* 1118 */         SMCLog.warning("Invalid raw texture pixel type: " + line);
/* 1119 */         return null;
/* 1120 */       }  if (!deque.isEmpty()) {
/* 1121 */         SMCLog.warning("Invalid raw texture, too many parameters: " + line);
/* 1122 */         return null;
/*      */       } 
/* 1124 */       return loadCustomTextureRaw(textureUnit, line, s, texturetype, internalformat, i, j, k, pixelformat, pixeltype);
/*      */     } 
/*      */ 
/*      */     
/* 1128 */     SMCLog.warning("Invalid raw texture size: " + line);
/* 1129 */     return null;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static ICustomTexture loadCustomTextureRaw(int textureUnit, String line, String path, TextureType type, InternalFormat internalFormat, int width, int height, int depth, PixelFormat pixelFormat, PixelType pixelType) {
/*      */     try {
/* 1137 */       String s = "shaders/" + StrUtils.removePrefix(path, "/");
/* 1138 */       InputStream inputstream = shaderPack.getResourceAsStream(s);
/*      */       
/* 1140 */       if (inputstream == null) {
/* 1141 */         SMCLog.warning("Raw texture not found: " + path);
/* 1142 */         return null;
/*      */       } 
/* 1144 */       byte[] abyte = Config.readAll(inputstream);
/* 1145 */       IOUtils.closeQuietly(inputstream);
/* 1146 */       ByteBuffer bytebuffer = GLAllocation.createDirectByteBuffer(abyte.length);
/* 1147 */       bytebuffer.put(abyte);
/* 1148 */       bytebuffer.flip();
/* 1149 */       TextureMetadataSection texturemetadatasection = SimpleShaderTexture.loadTextureMetadataSection(s, new TextureMetadataSection(true, true, new ArrayList()));
/* 1150 */       CustomTextureRaw customtextureraw = new CustomTextureRaw(type, internalFormat, width, height, depth, pixelFormat, pixelType, bytebuffer, textureUnit, texturemetadatasection.getTextureBlur(), texturemetadatasection.getTextureClamp());
/* 1151 */       return customtextureraw;
/*      */     }
/* 1153 */     catch (IOException ioexception) {
/* 1154 */       SMCLog.warning("Error loading raw texture: " + path);
/* 1155 */       SMCLog.warning(ioexception.getClass().getName() + ": " + ioexception.getMessage());
/* 1156 */       return null;
/*      */     } 
/*      */   }
/*      */   
/*      */   private static ICustomTexture loadCustomTextureShaders(int textureUnit, String path) {
/* 1161 */     path = path.trim();
/*      */     
/* 1163 */     if (path.indexOf('.') < 0) {
/* 1164 */       path = String.valueOf(path) + ".png";
/*      */     }
/*      */     
/*      */     try {
/* 1168 */       String s = "shaders/" + StrUtils.removePrefix(path, "/");
/* 1169 */       InputStream inputstream = shaderPack.getResourceAsStream(s);
/*      */       
/* 1171 */       if (inputstream == null) {
/* 1172 */         SMCLog.warning("Texture not found: " + path);
/* 1173 */         return null;
/*      */       } 
/* 1175 */       IOUtils.closeQuietly(inputstream);
/* 1176 */       SimpleShaderTexture simpleshadertexture = new SimpleShaderTexture(s);
/* 1177 */       simpleshadertexture.loadTexture(mc.getResourceManager());
/* 1178 */       CustomTexture customtexture = new CustomTexture(textureUnit, s, (ITextureObject)simpleshadertexture);
/* 1179 */       return customtexture;
/*      */     }
/* 1181 */     catch (IOException ioexception) {
/* 1182 */       SMCLog.warning("Error loading texture: " + path);
/* 1183 */       SMCLog.warning(ioexception.getClass().getName() + ": " + ioexception.getMessage());
/* 1184 */       return null;
/*      */     } 
/*      */   }
/*      */   
/*      */   private static int getTextureIndex(int stage, String name) {
/* 1189 */     if (stage == 0) {
/* 1190 */       if (name.equals("texture")) {
/* 1191 */         return 0;
/*      */       }
/*      */       
/* 1194 */       if (name.equals("lightmap")) {
/* 1195 */         return 1;
/*      */       }
/*      */       
/* 1198 */       if (name.equals("normals")) {
/* 1199 */         return 2;
/*      */       }
/*      */       
/* 1202 */       if (name.equals("specular")) {
/* 1203 */         return 3;
/*      */       }
/*      */       
/* 1206 */       if (name.equals("shadowtex0") || name.equals("watershadow")) {
/* 1207 */         return 4;
/*      */       }
/*      */       
/* 1210 */       if (name.equals("shadow")) {
/* 1211 */         return waterShadowEnabled ? 5 : 4;
/*      */       }
/*      */       
/* 1214 */       if (name.equals("shadowtex1")) {
/* 1215 */         return 5;
/*      */       }
/*      */       
/* 1218 */       if (name.equals("depthtex0")) {
/* 1219 */         return 6;
/*      */       }
/*      */       
/* 1222 */       if (name.equals("gaux1")) {
/* 1223 */         return 7;
/*      */       }
/*      */       
/* 1226 */       if (name.equals("gaux2")) {
/* 1227 */         return 8;
/*      */       }
/*      */       
/* 1230 */       if (name.equals("gaux3")) {
/* 1231 */         return 9;
/*      */       }
/*      */       
/* 1234 */       if (name.equals("gaux4")) {
/* 1235 */         return 10;
/*      */       }
/*      */       
/* 1238 */       if (name.equals("depthtex1")) {
/* 1239 */         return 12;
/*      */       }
/*      */       
/* 1242 */       if (name.equals("shadowcolor0") || name.equals("shadowcolor")) {
/* 1243 */         return 13;
/*      */       }
/*      */       
/* 1246 */       if (name.equals("shadowcolor1")) {
/* 1247 */         return 14;
/*      */       }
/*      */       
/* 1250 */       if (name.equals("noisetex")) {
/* 1251 */         return 15;
/*      */       }
/*      */     } 
/*      */     
/* 1255 */     if (stage == 1 || stage == 2) {
/* 1256 */       if (name.equals("colortex0") || name.equals("colortex0")) {
/* 1257 */         return 0;
/*      */       }
/*      */       
/* 1260 */       if (name.equals("colortex1") || name.equals("gdepth")) {
/* 1261 */         return 1;
/*      */       }
/*      */       
/* 1264 */       if (name.equals("colortex2") || name.equals("gnormal")) {
/* 1265 */         return 2;
/*      */       }
/*      */       
/* 1268 */       if (name.equals("colortex3") || name.equals("composite")) {
/* 1269 */         return 3;
/*      */       }
/*      */       
/* 1272 */       if (name.equals("shadowtex0") || name.equals("watershadow")) {
/* 1273 */         return 4;
/*      */       }
/*      */       
/* 1276 */       if (name.equals("shadow")) {
/* 1277 */         return waterShadowEnabled ? 5 : 4;
/*      */       }
/*      */       
/* 1280 */       if (name.equals("shadowtex1")) {
/* 1281 */         return 5;
/*      */       }
/*      */       
/* 1284 */       if (name.equals("depthtex0") || name.equals("gdepthtex")) {
/* 1285 */         return 6;
/*      */       }
/*      */       
/* 1288 */       if (name.equals("colortex4") || name.equals("gaux1")) {
/* 1289 */         return 7;
/*      */       }
/*      */       
/* 1292 */       if (name.equals("colortex5") || name.equals("gaux2")) {
/* 1293 */         return 8;
/*      */       }
/*      */       
/* 1296 */       if (name.equals("colortex6") || name.equals("gaux3")) {
/* 1297 */         return 9;
/*      */       }
/*      */       
/* 1300 */       if (name.equals("colortex7") || name.equals("gaux4")) {
/* 1301 */         return 10;
/*      */       }
/*      */       
/* 1304 */       if (name.equals("depthtex1")) {
/* 1305 */         return 11;
/*      */       }
/*      */       
/* 1308 */       if (name.equals("depthtex2")) {
/* 1309 */         return 12;
/*      */       }
/*      */       
/* 1312 */       if (name.equals("shadowcolor0") || name.equals("shadowcolor")) {
/* 1313 */         return 13;
/*      */       }
/*      */       
/* 1316 */       if (name.equals("shadowcolor1")) {
/* 1317 */         return 14;
/*      */       }
/*      */       
/* 1320 */       if (name.equals("noisetex")) {
/* 1321 */         return 15;
/*      */       }
/*      */     } 
/*      */     
/* 1325 */     return -1;
/*      */   }
/*      */   
/*      */   private static void bindCustomTextures(ICustomTexture[] cts) {
/* 1329 */     if (cts != null) {
/* 1330 */       for (int i = 0; i < cts.length; i++) {
/* 1331 */         ICustomTexture icustomtexture = cts[i];
/* 1332 */         GlStateManager.setActiveTexture(33984 + icustomtexture.getTextureUnit());
/* 1333 */         int j = icustomtexture.getTextureId();
/* 1334 */         int k = icustomtexture.getTarget();
/*      */         
/* 1336 */         if (k == 3553) {
/* 1337 */           GlStateManager.bindTexture(j);
/*      */         } else {
/* 1339 */           GL11.glBindTexture(k, j);
/*      */         } 
/*      */       } 
/*      */     }
/*      */   }
/*      */   
/*      */   private static void resetCustomTextures() {
/* 1346 */     deleteCustomTextures(customTexturesGbuffers);
/* 1347 */     deleteCustomTextures(customTexturesComposite);
/* 1348 */     deleteCustomTextures(customTexturesDeferred);
/* 1349 */     customTexturesGbuffers = null;
/* 1350 */     customTexturesComposite = null;
/* 1351 */     customTexturesDeferred = null;
/*      */   }
/*      */   
/*      */   private static void deleteCustomTextures(ICustomTexture[] cts) {
/* 1355 */     if (cts != null) {
/* 1356 */       for (int i = 0; i < cts.length; i++) {
/* 1357 */         ICustomTexture icustomtexture = cts[i];
/* 1358 */         icustomtexture.deleteTexture();
/*      */       } 
/*      */     }
/*      */   }
/*      */   
/*      */   public static ShaderOption[] getShaderPackOptions(String screenName) {
/* 1364 */     ShaderOption[] ashaderoption = (ShaderOption[])shaderPackOptions.clone();
/*      */     
/* 1366 */     if (shaderPackGuiScreens == null) {
/* 1367 */       if (shaderPackProfiles != null) {
/* 1368 */         ShaderOptionProfile shaderoptionprofile = new ShaderOptionProfile(shaderPackProfiles, ashaderoption);
/* 1369 */         ashaderoption = (ShaderOption[])Config.addObjectToArray((Object[])ashaderoption, shaderoptionprofile, 0);
/*      */       } 
/*      */       
/* 1372 */       ashaderoption = getVisibleOptions(ashaderoption);
/* 1373 */       return ashaderoption;
/*      */     } 
/* 1375 */     String s = (screenName != null) ? ("screen." + screenName) : "screen";
/* 1376 */     ScreenShaderOptions screenshaderoptions = shaderPackGuiScreens.get(s);
/*      */     
/* 1378 */     if (screenshaderoptions == null) {
/* 1379 */       return new ShaderOption[0];
/*      */     }
/* 1381 */     ShaderOption[] ashaderoption1 = screenshaderoptions.getShaderOptions();
/* 1382 */     List<ShaderOption> list = new ArrayList<>();
/*      */     
/* 1384 */     for (int i = 0; i < ashaderoption1.length; i++) {
/* 1385 */       ShaderOption shaderoption = ashaderoption1[i];
/*      */       
/* 1387 */       if (shaderoption == null) {
/* 1388 */         list.add(null);
/* 1389 */       } else if (shaderoption instanceof net.optifine.shaders.config.ShaderOptionRest) {
/* 1390 */         ShaderOption[] ashaderoption2 = getShaderOptionsRest(shaderPackGuiScreens, ashaderoption);
/* 1391 */         list.addAll(Arrays.asList(ashaderoption2));
/*      */       } else {
/* 1393 */         list.add(shaderoption);
/*      */       } 
/*      */     } 
/*      */     
/* 1397 */     ShaderOption[] ashaderoption3 = list.<ShaderOption>toArray(new ShaderOption[list.size()]);
/* 1398 */     return ashaderoption3;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public static int getShaderPackColumns(String screenName, int def) {
/* 1404 */     String s = (screenName != null) ? ("screen." + screenName) : "screen";
/*      */     
/* 1406 */     if (shaderPackGuiScreens == null) {
/* 1407 */       return def;
/*      */     }
/* 1409 */     ScreenShaderOptions screenshaderoptions = shaderPackGuiScreens.get(s);
/* 1410 */     return (screenshaderoptions == null) ? def : screenshaderoptions.getColumns();
/*      */   }
/*      */ 
/*      */   
/*      */   private static ShaderOption[] getShaderOptionsRest(Map<String, ScreenShaderOptions> mapScreens, ShaderOption[] ops) {
/* 1415 */     Set<String> set = new HashSet<>();
/*      */     
/* 1417 */     for (String s : mapScreens.keySet()) {
/* 1418 */       ScreenShaderOptions screenshaderoptions = mapScreens.get(s);
/* 1419 */       ShaderOption[] ashaderoption = screenshaderoptions.getShaderOptions();
/*      */       
/* 1421 */       for (int i = 0; i < ashaderoption.length; i++) {
/* 1422 */         ShaderOption shaderoption = ashaderoption[i];
/*      */         
/* 1424 */         if (shaderoption != null) {
/* 1425 */           set.add(shaderoption.getName());
/*      */         }
/*      */       } 
/*      */     } 
/*      */     
/* 1430 */     List<ShaderOption> list = new ArrayList<>();
/*      */     
/* 1432 */     for (int j = 0; j < ops.length; j++) {
/* 1433 */       ShaderOption shaderoption1 = ops[j];
/*      */       
/* 1435 */       if (shaderoption1.isVisible()) {
/* 1436 */         String s1 = shaderoption1.getName();
/*      */         
/* 1438 */         if (!set.contains(s1)) {
/* 1439 */           list.add(shaderoption1);
/*      */         }
/*      */       } 
/*      */     } 
/*      */     
/* 1444 */     ShaderOption[] ashaderoption1 = list.<ShaderOption>toArray(new ShaderOption[list.size()]);
/* 1445 */     return ashaderoption1;
/*      */   }
/*      */   
/*      */   public static ShaderOption getShaderOption(String name) {
/* 1449 */     return ShaderUtils.getShaderOption(name, shaderPackOptions);
/*      */   }
/*      */   
/*      */   public static ShaderOption[] getShaderPackOptions() {
/* 1453 */     return shaderPackOptions;
/*      */   }
/*      */   
/*      */   public static boolean isShaderPackOptionSlider(String name) {
/* 1457 */     return (shaderPackOptionSliders == null) ? false : shaderPackOptionSliders.contains(name);
/*      */   }
/*      */   
/*      */   private static ShaderOption[] getVisibleOptions(ShaderOption[] ops) {
/* 1461 */     List<ShaderOption> list = new ArrayList<>();
/*      */     
/* 1463 */     for (int i = 0; i < ops.length; i++) {
/* 1464 */       ShaderOption shaderoption = ops[i];
/*      */       
/* 1466 */       if (shaderoption.isVisible()) {
/* 1467 */         list.add(shaderoption);
/*      */       }
/*      */     } 
/*      */     
/* 1471 */     ShaderOption[] ashaderoption = list.<ShaderOption>toArray(new ShaderOption[list.size()]);
/* 1472 */     return ashaderoption;
/*      */   }
/*      */   
/*      */   public static void saveShaderPackOptions() {
/* 1476 */     saveShaderPackOptions(shaderPackOptions, shaderPack);
/*      */   }
/*      */   
/*      */   private static void saveShaderPackOptions(ShaderOption[] sos, IShaderPack sp) {
/* 1480 */     PropertiesOrdered propertiesOrdered = new PropertiesOrdered();
/*      */     
/* 1482 */     if (shaderPackOptions != null) {
/* 1483 */       for (int i = 0; i < sos.length; i++) {
/* 1484 */         ShaderOption shaderoption = sos[i];
/*      */         
/* 1486 */         if (shaderoption.isChanged() && shaderoption.isEnabled()) {
/* 1487 */           propertiesOrdered.setProperty(shaderoption.getName(), shaderoption.getValue());
/*      */         }
/*      */       } 
/*      */     }
/*      */     
/*      */     try {
/* 1493 */       saveOptionProperties(sp, (Properties)propertiesOrdered);
/* 1494 */     } catch (IOException ioexception) {
/* 1495 */       Config.warn("[Shaders] Error saving configuration for " + shaderPack.getName());
/* 1496 */       ioexception.printStackTrace();
/*      */     } 
/*      */   }
/*      */   
/*      */   private static void saveOptionProperties(IShaderPack sp, Properties props) throws IOException {
/* 1501 */     String s = "shaderpacks/" + sp.getName() + ".txt";
/* 1502 */     File file1 = new File((Minecraft.getMinecraft()).mcDataDir, s);
/*      */     
/* 1504 */     if (props.isEmpty()) {
/* 1505 */       file1.delete();
/*      */     } else {
/* 1507 */       FileOutputStream fileoutputstream = new FileOutputStream(file1);
/* 1508 */       props.store(fileoutputstream, (String)null);
/* 1509 */       fileoutputstream.flush();
/* 1510 */       fileoutputstream.close();
/*      */     } 
/*      */   }
/*      */   
/*      */   private static ShaderOption[] loadShaderPackOptions() {
/*      */     try {
/* 1516 */       String[] astring = programs.getProgramNames();
/* 1517 */       ShaderOption[] ashaderoption = ShaderPackParser.parseShaderPackOptions(shaderPack, astring, shaderPackDimensions);
/* 1518 */       Properties properties = loadOptionProperties(shaderPack);
/*      */       
/* 1520 */       for (int i = 0; i < ashaderoption.length; i++) {
/* 1521 */         ShaderOption shaderoption = ashaderoption[i];
/* 1522 */         String s = properties.getProperty(shaderoption.getName());
/*      */         
/* 1524 */         if (s != null) {
/* 1525 */           shaderoption.resetValue();
/*      */           
/* 1527 */           if (!shaderoption.setValue(s)) {
/* 1528 */             Config.warn("[Shaders] Invalid value, option: " + shaderoption.getName() + ", value: " + s);
/*      */           }
/*      */         } 
/*      */       } 
/*      */       
/* 1533 */       return ashaderoption;
/* 1534 */     } catch (IOException ioexception) {
/* 1535 */       Config.warn("[Shaders] Error reading configuration for " + shaderPack.getName());
/* 1536 */       ioexception.printStackTrace();
/* 1537 */       return null;
/*      */     } 
/*      */   }
/*      */   
/*      */   private static Properties loadOptionProperties(IShaderPack sp) throws IOException {
/* 1542 */     PropertiesOrdered propertiesOrdered = new PropertiesOrdered();
/* 1543 */     String s = "shaderpacks/" + sp.getName() + ".txt";
/* 1544 */     File file1 = new File((Minecraft.getMinecraft()).mcDataDir, s);
/*      */     
/* 1546 */     if (file1.exists() && file1.isFile() && file1.canRead()) {
/* 1547 */       FileInputStream fileinputstream = new FileInputStream(file1);
/* 1548 */       propertiesOrdered.load(fileinputstream);
/* 1549 */       fileinputstream.close();
/* 1550 */       return (Properties)propertiesOrdered;
/*      */     } 
/* 1552 */     return (Properties)propertiesOrdered;
/*      */   }
/*      */ 
/*      */   
/*      */   public static ShaderOption[] getChangedOptions(ShaderOption[] ops) {
/* 1557 */     List<ShaderOption> list = new ArrayList<>();
/*      */     
/* 1559 */     for (int i = 0; i < ops.length; i++) {
/* 1560 */       ShaderOption shaderoption = ops[i];
/*      */       
/* 1562 */       if (shaderoption.isEnabled() && shaderoption.isChanged()) {
/* 1563 */         list.add(shaderoption);
/*      */       }
/*      */     } 
/*      */     
/* 1567 */     ShaderOption[] ashaderoption = list.<ShaderOption>toArray(new ShaderOption[list.size()]);
/* 1568 */     return ashaderoption;
/*      */   }
/*      */   
/*      */   private static String applyOptions(String line, ShaderOption[] ops) {
/* 1572 */     if (ops != null && ops.length > 0) {
/* 1573 */       for (int i = 0; i < ops.length; i++) {
/* 1574 */         ShaderOption shaderoption = ops[i];
/*      */         
/* 1576 */         if (shaderoption.matchesLine(line)) {
/* 1577 */           line = shaderoption.getSourceLine();
/*      */           
/*      */           break;
/*      */         } 
/*      */       } 
/* 1582 */       return line;
/*      */     } 
/* 1584 */     return line;
/*      */   }
/*      */ 
/*      */   
/*      */   public static ArrayList listOfShaders() {
/* 1589 */     ArrayList<String> arraylist = new ArrayList<>();
/* 1590 */     arraylist.add("OFF");
/* 1591 */     arraylist.add("(internal)");
/* 1592 */     int i = arraylist.size();
/*      */     
/*      */     try {
/* 1595 */       if (!shaderPacksDir.exists()) {
/* 1596 */         shaderPacksDir.mkdir();
/*      */       }
/*      */       
/* 1599 */       File[] afile = shaderPacksDir.listFiles();
/*      */       
/* 1601 */       for (int j = 0; j < afile.length; j++) {
/* 1602 */         File file1 = afile[j];
/* 1603 */         String s = file1.getName();
/*      */         
/* 1605 */         if (file1.isDirectory()) {
/* 1606 */           if (!s.equals("debug")) {
/* 1607 */             File file2 = new File(file1, "shaders");
/*      */             
/* 1609 */             if (file2.exists() && file2.isDirectory()) {
/* 1610 */               arraylist.add(s);
/*      */             }
/*      */           } 
/* 1613 */         } else if (file1.isFile() && s.toLowerCase().endsWith(".zip")) {
/* 1614 */           arraylist.add(s);
/*      */         } 
/*      */       } 
/* 1617 */     } catch (Exception exception) {}
/*      */ 
/*      */ 
/*      */     
/* 1621 */     List<String> list = arraylist.subList(i, arraylist.size());
/* 1622 */     Collections.sort(list, String.CASE_INSENSITIVE_ORDER);
/* 1623 */     return arraylist;
/*      */   }
/*      */   
/*      */   public static int checkFramebufferStatus(String location) {
/* 1627 */     int i = EXTFramebufferObject.glCheckFramebufferStatusEXT(36160);
/*      */     
/* 1629 */     if (i != 36053) {
/* 1630 */       System.err.format("FramebufferStatus 0x%04X at %s\n", new Object[] { Integer.valueOf(i), location });
/*      */     }
/*      */     
/* 1633 */     return i;
/*      */   }
/*      */   
/*      */   public static int checkGLError(String location) {
/* 1637 */     int i = GlStateManager.glGetError();
/*      */     
/* 1639 */     if (i != 0 && GlErrors.isEnabled(i)) {
/* 1640 */       String s = Config.getGlErrorString(i);
/* 1641 */       String s1 = getErrorInfo(i, location);
/* 1642 */       String s2 = String.format("OpenGL error: %s (%s)%s, at: %s", new Object[] { Integer.valueOf(i), s, s1, location });
/* 1643 */       SMCLog.severe(s2);
/*      */       
/* 1645 */       if (Config.isShowGlErrors() && TimedEvent.isActive("ShowGlErrorShaders", 10000L)) {
/* 1646 */         String s3 = I18n.format("of.message.openglError", new Object[] { Integer.valueOf(i), s });
/* 1647 */         printChat(s3);
/*      */       } 
/*      */     } 
/*      */     
/* 1651 */     return i;
/*      */   }
/*      */   
/*      */   private static String getErrorInfo(int errorCode, String location) {
/* 1655 */     StringBuilder stringbuilder = new StringBuilder();
/*      */     
/* 1657 */     if (errorCode == 1286) {
/* 1658 */       int i = EXTFramebufferObject.glCheckFramebufferStatusEXT(36160);
/* 1659 */       String s = getFramebufferStatusText(i);
/* 1660 */       String s1 = ", fbStatus: " + i + " (" + s + ")";
/* 1661 */       stringbuilder.append(s1);
/*      */     } 
/*      */     
/* 1664 */     String s2 = activeProgram.getName();
/*      */     
/* 1666 */     if (s2.isEmpty()) {
/* 1667 */       s2 = "none";
/*      */     }
/*      */     
/* 1670 */     stringbuilder.append(", program: " + s2);
/* 1671 */     Program program = getProgramById(activeProgramID);
/*      */     
/* 1673 */     if (program != activeProgram) {
/* 1674 */       String s3 = program.getName();
/*      */       
/* 1676 */       if (s3.isEmpty()) {
/* 1677 */         s3 = "none";
/*      */       }
/*      */       
/* 1680 */       stringbuilder.append(" (" + s3 + ")");
/*      */     } 
/*      */     
/* 1683 */     if (location.equals("setDrawBuffers")) {
/* 1684 */       stringbuilder.append(", drawBuffers: " + activeProgram.getDrawBufSettings());
/*      */     }
/*      */     
/* 1687 */     return stringbuilder.toString();
/*      */   }
/*      */   
/*      */   private static Program getProgramById(int programID) {
/* 1691 */     for (int i = 0; i < ProgramsAll.length; i++) {
/* 1692 */       Program program = ProgramsAll[i];
/*      */       
/* 1694 */       if (program.getId() == programID) {
/* 1695 */         return program;
/*      */       }
/*      */     } 
/*      */     
/* 1699 */     return ProgramNone;
/*      */   }
/*      */   
/*      */   private static String getFramebufferStatusText(int fbStatusCode) {
/* 1703 */     switch (fbStatusCode) {
/*      */       case 33305:
/* 1705 */         return "Undefined";
/*      */       
/*      */       case 36053:
/* 1708 */         return "Complete";
/*      */       
/*      */       case 36054:
/* 1711 */         return "Incomplete attachment";
/*      */       
/*      */       case 36055:
/* 1714 */         return "Incomplete missing attachment";
/*      */       
/*      */       case 36059:
/* 1717 */         return "Incomplete draw buffer";
/*      */       
/*      */       case 36060:
/* 1720 */         return "Incomplete read buffer";
/*      */       
/*      */       case 36061:
/* 1723 */         return "Unsupported";
/*      */       
/*      */       case 36182:
/* 1726 */         return "Incomplete multisample";
/*      */       
/*      */       case 36264:
/* 1729 */         return "Incomplete layer targets";
/*      */     } 
/*      */     
/* 1732 */     return "Unknown";
/*      */   }
/*      */ 
/*      */   
/*      */   private static void printChat(String str) {
/* 1737 */     mc.ingameGUI.getChatGUI().printChatMessage((IChatComponent)new ChatComponentText(str));
/*      */   }
/*      */   
/*      */   private static void printChatAndLogError(String str) {
/* 1741 */     SMCLog.severe(str);
/* 1742 */     mc.ingameGUI.getChatGUI().printChatMessage((IChatComponent)new ChatComponentText(str));
/*      */   }
/*      */   
/*      */   public static void printIntBuffer(String title, IntBuffer buf) {
/* 1746 */     StringBuilder stringbuilder = new StringBuilder(128);
/* 1747 */     stringbuilder.append(title).append(" [pos ").append(buf.position()).append(" lim ").append(buf.limit()).append(" cap ").append(buf.capacity()).append(" :");
/* 1748 */     int i = buf.limit();
/*      */     
/* 1750 */     for (int j = 0; j < i; j++) {
/* 1751 */       stringbuilder.append(" ").append(buf.get(j));
/*      */     }
/*      */     
/* 1754 */     stringbuilder.append("]");
/* 1755 */     SMCLog.info(stringbuilder.toString());
/*      */   }
/*      */   
/*      */   public static void startup(Minecraft mcIn) {
/* 1759 */     checkShadersModInstalled();
/* 1760 */     mc = mcIn;
/* 1761 */     mc = Minecraft.getMinecraft();
/* 1762 */     capabilities = GLContext.getCapabilities();
/* 1763 */     glVersionString = GL11.glGetString(7938);
/* 1764 */     glVendorString = GL11.glGetString(7936);
/* 1765 */     glRendererString = GL11.glGetString(7937);
/* 1766 */     SMCLog.info("OpenGL Version: " + glVersionString);
/* 1767 */     SMCLog.info("Vendor:  " + glVendorString);
/* 1768 */     SMCLog.info("Renderer: " + glRendererString);
/* 1769 */     SMCLog.info("Capabilities: " + (capabilities.OpenGL20 ? " 2.0 " : " - ") + (capabilities.OpenGL21 ? " 2.1 " : " - ") + (capabilities.OpenGL30 ? " 3.0 " : " - ") + (capabilities.OpenGL32 ? " 3.2 " : " - ") + (capabilities.OpenGL40 ? " 4.0 " : " - "));
/* 1770 */     SMCLog.info("GL_MAX_DRAW_BUFFERS: " + GL11.glGetInteger(34852));
/* 1771 */     SMCLog.info("GL_MAX_COLOR_ATTACHMENTS_EXT: " + GL11.glGetInteger(36063));
/* 1772 */     SMCLog.info("GL_MAX_TEXTURE_IMAGE_UNITS: " + GL11.glGetInteger(34930));
/* 1773 */     hasGlGenMipmap = capabilities.OpenGL30;
/* 1774 */     loadConfig();
/*      */   }
/*      */   
/*      */   public static void updateBlockLightLevel() {
/* 1778 */     if (isOldLighting()) {
/* 1779 */       blockLightLevel05 = 0.5F;
/* 1780 */       blockLightLevel06 = 0.6F;
/* 1781 */       blockLightLevel08 = 0.8F;
/*      */     } else {
/* 1783 */       blockLightLevel05 = 1.0F;
/* 1784 */       blockLightLevel06 = 1.0F;
/* 1785 */       blockLightLevel08 = 1.0F;
/*      */     } 
/*      */   }
/*      */   
/*      */   public static boolean isOldHandLight() {
/* 1790 */     return !configOldHandLight.isDefault() ? configOldHandLight.isTrue() : (!shaderPackOldHandLight.isDefault() ? shaderPackOldHandLight.isTrue() : true);
/*      */   }
/*      */   
/*      */   public static boolean isDynamicHandLight() {
/* 1794 */     return !shaderPackDynamicHandLight.isDefault() ? shaderPackDynamicHandLight.isTrue() : true;
/*      */   }
/*      */   
/*      */   public static boolean isOldLighting() {
/* 1798 */     return !configOldLighting.isDefault() ? configOldLighting.isTrue() : (!shaderPackOldLighting.isDefault() ? shaderPackOldLighting.isTrue() : true);
/*      */   }
/*      */   
/*      */   public static boolean isRenderShadowTranslucent() {
/* 1802 */     return !shaderPackShadowTranslucent.isFalse();
/*      */   }
/*      */   
/*      */   public static boolean isUnderwaterOverlay() {
/* 1806 */     return !shaderPackUnderwaterOverlay.isFalse();
/*      */   }
/*      */   
/*      */   public static boolean isSun() {
/* 1810 */     return !shaderPackSun.isFalse();
/*      */   }
/*      */   
/*      */   public static boolean isMoon() {
/* 1814 */     return !shaderPackMoon.isFalse();
/*      */   }
/*      */   
/*      */   public static boolean isVignette() {
/* 1818 */     return !shaderPackVignette.isFalse();
/*      */   }
/*      */   
/*      */   public static boolean isRenderBackFace(EnumWorldBlockLayer blockLayerIn) {
/* 1822 */     switch (blockLayerIn) {
/*      */       case SOLID:
/* 1824 */         return shaderPackBackFaceSolid.isTrue();
/*      */       
/*      */       case null:
/* 1827 */         return shaderPackBackFaceCutout.isTrue();
/*      */       
/*      */       case CUTOUT_MIPPED:
/* 1830 */         return shaderPackBackFaceCutoutMipped.isTrue();
/*      */       
/*      */       case TRANSLUCENT:
/* 1833 */         return shaderPackBackFaceTranslucent.isTrue();
/*      */     } 
/*      */     
/* 1836 */     return false;
/*      */   }
/*      */ 
/*      */   
/*      */   public static boolean isRainDepth() {
/* 1841 */     return shaderPackRainDepth.isTrue();
/*      */   }
/*      */   
/*      */   public static boolean isBeaconBeamDepth() {
/* 1845 */     return shaderPackBeaconBeamDepth.isTrue();
/*      */   }
/*      */   
/*      */   public static boolean isSeparateAo() {
/* 1849 */     return shaderPackSeparateAo.isTrue();
/*      */   }
/*      */   
/*      */   public static boolean isFrustumCulling() {
/* 1853 */     return !shaderPackFrustumCulling.isFalse();
/*      */   }
/*      */ 
/*      */   
/*      */   public static void init() {
/*      */     boolean flag;
/* 1859 */     if (!isInitializedOnce) {
/* 1860 */       isInitializedOnce = true;
/* 1861 */       flag = true;
/*      */     } else {
/* 1863 */       flag = false;
/*      */     } 
/*      */     
/* 1866 */     if (!isShaderPackInitialized) {
/* 1867 */       checkGLError("Shaders.init pre");
/*      */       
/* 1869 */       if (getShaderPackName() != null);
/*      */ 
/*      */ 
/*      */       
/* 1873 */       if (!capabilities.OpenGL20) {
/* 1874 */         printChatAndLogError("No OpenGL 2.0");
/*      */       }
/*      */       
/* 1877 */       if (!capabilities.GL_EXT_framebuffer_object) {
/* 1878 */         printChatAndLogError("No EXT_framebuffer_object");
/*      */       }
/*      */       
/* 1881 */       dfbDrawBuffers.position(0).limit(8);
/* 1882 */       dfbColorTextures.position(0).limit(16);
/* 1883 */       dfbDepthTextures.position(0).limit(3);
/* 1884 */       sfbDrawBuffers.position(0).limit(8);
/* 1885 */       sfbDepthTextures.position(0).limit(2);
/* 1886 */       sfbColorTextures.position(0).limit(8);
/* 1887 */       usedColorBuffers = 4;
/* 1888 */       usedDepthBuffers = 1;
/* 1889 */       usedShadowColorBuffers = 0;
/* 1890 */       usedShadowDepthBuffers = 0;
/* 1891 */       usedColorAttachs = 1;
/* 1892 */       usedDrawBuffers = 1;
/* 1893 */       Arrays.fill(gbuffersFormat, 6408);
/* 1894 */       Arrays.fill(gbuffersClear, true);
/* 1895 */       Arrays.fill((Object[])gbuffersClearColor, (Object)null);
/* 1896 */       Arrays.fill(shadowHardwareFilteringEnabled, false);
/* 1897 */       Arrays.fill(shadowMipmapEnabled, false);
/* 1898 */       Arrays.fill(shadowFilterNearest, false);
/* 1899 */       Arrays.fill(shadowColorMipmapEnabled, false);
/* 1900 */       Arrays.fill(shadowColorFilterNearest, false);
/* 1901 */       centerDepthSmoothEnabled = false;
/* 1902 */       noiseTextureEnabled = false;
/* 1903 */       sunPathRotation = 0.0F;
/* 1904 */       shadowIntervalSize = 2.0F;
/* 1905 */       shadowMapWidth = 1024;
/* 1906 */       shadowMapHeight = 1024;
/* 1907 */       spShadowMapWidth = 1024;
/* 1908 */       spShadowMapHeight = 1024;
/* 1909 */       shadowMapFOV = 90.0F;
/* 1910 */       shadowMapHalfPlane = 160.0F;
/* 1911 */       shadowMapIsOrtho = true;
/* 1912 */       shadowDistanceRenderMul = -1.0F;
/* 1913 */       aoLevel = -1.0F;
/* 1914 */       useEntityAttrib = false;
/* 1915 */       useMidTexCoordAttrib = false;
/* 1916 */       useTangentAttrib = false;
/* 1917 */       waterShadowEnabled = false;
/* 1918 */       hasGeometryShaders = false;
/* 1919 */       updateBlockLightLevel();
/* 1920 */       Smoother.resetValues();
/* 1921 */       shaderUniforms.reset();
/*      */       
/* 1923 */       if (customUniforms != null) {
/* 1924 */         customUniforms.reset();
/*      */       }
/*      */       
/* 1927 */       ShaderProfile shaderprofile = ShaderUtils.detectProfile(shaderPackProfiles, shaderPackOptions, false);
/* 1928 */       String s = "";
/*      */       
/* 1930 */       if (currentWorld != null) {
/* 1931 */         int i = currentWorld.provider.getDimensionId();
/*      */         
/* 1933 */         if (shaderPackDimensions.contains(Integer.valueOf(i))) {
/* 1934 */           s = "world" + i + "/";
/*      */         }
/*      */       } 
/*      */       
/* 1938 */       for (int k = 0; k < ProgramsAll.length; k++) {
/* 1939 */         Program program = ProgramsAll[k];
/* 1940 */         program.resetId();
/* 1941 */         program.resetConfiguration();
/*      */         
/* 1943 */         if (program.getProgramStage() != ProgramStage.NONE) {
/* 1944 */           String s1 = program.getName();
/* 1945 */           String s2 = String.valueOf(s) + s1;
/* 1946 */           boolean flag1 = true;
/*      */           
/* 1948 */           if (shaderPackProgramConditions.containsKey(s2)) {
/* 1949 */             flag1 = (flag1 && ((IExpressionBool)shaderPackProgramConditions.get(s2)).eval());
/*      */           }
/*      */           
/* 1952 */           if (shaderprofile != null) {
/* 1953 */             flag1 = (flag1 && !shaderprofile.isProgramDisabled(s2));
/*      */           }
/*      */           
/* 1956 */           if (!flag1) {
/* 1957 */             SMCLog.info("Program disabled: " + s2);
/* 1958 */             s1 = "<disabled>";
/* 1959 */             s2 = String.valueOf(s) + s1;
/*      */           } 
/*      */           
/* 1962 */           String s3 = "/shaders/" + s2;
/* 1963 */           String s4 = String.valueOf(s3) + ".vsh";
/* 1964 */           String s5 = String.valueOf(s3) + ".gsh";
/* 1965 */           String s6 = String.valueOf(s3) + ".fsh";
/* 1966 */           setupProgram(program, s4, s5, s6);
/* 1967 */           int j = program.getId();
/*      */           
/* 1969 */           if (j > 0) {
/* 1970 */             SMCLog.info("Program loaded: " + s2);
/*      */           }
/*      */           
/* 1973 */           initDrawBuffers(program);
/* 1974 */           updateToggleBuffers(program);
/*      */         } 
/*      */       } 
/*      */       
/* 1978 */       hasDeferredPrograms = false;
/*      */       
/* 1980 */       for (int l = 0; l < ProgramsDeferred.length; l++) {
/* 1981 */         if (ProgramsDeferred[l].getId() != 0) {
/* 1982 */           hasDeferredPrograms = true;
/*      */           
/*      */           break;
/*      */         } 
/*      */       } 
/* 1987 */       usedColorAttachs = usedColorBuffers;
/* 1988 */       shadowPassInterval = (usedShadowDepthBuffers > 0) ? 1 : 0;
/* 1989 */       shouldSkipDefaultShadow = (usedShadowDepthBuffers > 0);
/* 1990 */       SMCLog.info("usedColorBuffers: " + usedColorBuffers);
/* 1991 */       SMCLog.info("usedDepthBuffers: " + usedDepthBuffers);
/* 1992 */       SMCLog.info("usedShadowColorBuffers: " + usedShadowColorBuffers);
/* 1993 */       SMCLog.info("usedShadowDepthBuffers: " + usedShadowDepthBuffers);
/* 1994 */       SMCLog.info("usedColorAttachs: " + usedColorAttachs);
/* 1995 */       SMCLog.info("usedDrawBuffers: " + usedDrawBuffers);
/* 1996 */       dfbDrawBuffers.position(0).limit(usedDrawBuffers);
/* 1997 */       dfbColorTextures.position(0).limit(usedColorBuffers * 2);
/* 1998 */       dfbColorTexturesFlip.reset();
/*      */       
/* 2000 */       for (int i1 = 0; i1 < usedDrawBuffers; i1++) {
/* 2001 */         dfbDrawBuffers.put(i1, 36064 + i1);
/*      */       }
/*      */       
/* 2004 */       int j1 = GL11.glGetInteger(34852);
/*      */       
/* 2006 */       if (usedDrawBuffers > j1) {
/* 2007 */         printChatAndLogError("[Shaders] Error: Not enough draw buffers, needed: " + usedDrawBuffers + ", available: " + j1);
/*      */       }
/*      */       
/* 2010 */       sfbDrawBuffers.position(0).limit(usedShadowColorBuffers);
/*      */       
/* 2012 */       for (int k1 = 0; k1 < usedShadowColorBuffers; k1++) {
/* 2013 */         sfbDrawBuffers.put(k1, 36064 + k1);
/*      */       }
/*      */       
/* 2016 */       for (int l1 = 0; l1 < ProgramsAll.length; l1++) {
/* 2017 */         Program program1 = ProgramsAll[l1];
/*      */         
/*      */         Program program2;
/* 2020 */         for (program2 = program1; program2.getId() == 0 && program2.getProgramBackup() != program2; program2 = program2.getProgramBackup());
/*      */ 
/*      */ 
/*      */         
/* 2024 */         if (program2 != program1 && program1 != ProgramShadow) {
/* 2025 */           program1.copyFrom(program2);
/*      */         }
/*      */       } 
/*      */       
/* 2029 */       resize();
/* 2030 */       resizeShadow();
/*      */       
/* 2032 */       if (noiseTextureEnabled) {
/* 2033 */         setupNoiseTexture();
/*      */       }
/*      */       
/* 2036 */       if (defaultTexture == null) {
/* 2037 */         defaultTexture = ShadersTex.createDefaultTexture();
/*      */       }
/*      */       
/* 2040 */       GlStateManager.pushMatrix();
/* 2041 */       GlStateManager.rotate(-90.0F, 0.0F, 1.0F, 0.0F);
/* 2042 */       preCelestialRotate();
/* 2043 */       postCelestialRotate();
/* 2044 */       GlStateManager.popMatrix();
/* 2045 */       isShaderPackInitialized = true;
/* 2046 */       loadEntityDataMap();
/* 2047 */       resetDisplayLists();
/*      */       
/* 2049 */       if (!flag);
/*      */ 
/*      */ 
/*      */       
/* 2053 */       checkGLError("Shaders.init");
/*      */     } 
/*      */   }
/*      */   
/*      */   private static void initDrawBuffers(Program p) {
/* 2058 */     int i = GL11.glGetInteger(34852);
/* 2059 */     Arrays.fill(p.getToggleColorTextures(), false);
/*      */     
/* 2061 */     if (p == ProgramFinal) {
/* 2062 */       p.setDrawBuffers(null);
/* 2063 */     } else if (p.getId() == 0) {
/* 2064 */       if (p == ProgramShadow) {
/* 2065 */         p.setDrawBuffers(drawBuffersNone);
/*      */       } else {
/* 2067 */         p.setDrawBuffers(drawBuffersColorAtt0);
/*      */       } 
/*      */     } else {
/* 2070 */       String s = p.getDrawBufSettings();
/*      */       
/* 2072 */       if (s == null) {
/* 2073 */         if (p != ProgramShadow && p != ProgramShadowSolid && p != ProgramShadowCutout) {
/* 2074 */           p.setDrawBuffers(dfbDrawBuffers);
/* 2075 */           usedDrawBuffers = usedColorBuffers;
/* 2076 */           Arrays.fill(p.getToggleColorTextures(), 0, usedColorBuffers, true);
/*      */         } else {
/* 2078 */           p.setDrawBuffers(sfbDrawBuffers);
/*      */         } 
/*      */       } else {
/* 2081 */         IntBuffer intbuffer = p.getDrawBuffersBuffer();
/* 2082 */         int j = s.length();
/* 2083 */         usedDrawBuffers = Math.max(usedDrawBuffers, j);
/* 2084 */         j = Math.min(j, i);
/* 2085 */         p.setDrawBuffers(intbuffer);
/* 2086 */         intbuffer.limit(j);
/*      */         
/* 2088 */         for (int k = 0; k < j; k++) {
/* 2089 */           int l = getDrawBuffer(p, s, k);
/* 2090 */           intbuffer.put(k, l);
/*      */         } 
/*      */       } 
/*      */     } 
/*      */   }
/*      */   
/*      */   private static int getDrawBuffer(Program p, String str, int ic) {
/* 2097 */     int i = 0;
/*      */     
/* 2099 */     if (ic >= str.length()) {
/* 2100 */       return i;
/*      */     }
/* 2102 */     int j = str.charAt(ic) - 48;
/*      */     
/* 2104 */     if (p == ProgramShadow) {
/* 2105 */       if (j >= 0 && j <= 1) {
/* 2106 */         i = j + 36064;
/* 2107 */         usedShadowColorBuffers = Math.max(usedShadowColorBuffers, j);
/*      */       } 
/*      */       
/* 2110 */       return i;
/*      */     } 
/* 2112 */     if (j >= 0 && j <= 7) {
/* 2113 */       p.getToggleColorTextures()[j] = true;
/* 2114 */       i = j + 36064;
/* 2115 */       usedColorAttachs = Math.max(usedColorAttachs, j);
/* 2116 */       usedColorBuffers = Math.max(usedColorBuffers, j);
/*      */     } 
/*      */     
/* 2119 */     return i;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private static void updateToggleBuffers(Program p) {
/* 2125 */     boolean[] aboolean = p.getToggleColorTextures();
/* 2126 */     Boolean[] aboolean1 = p.getBuffersFlip();
/*      */     
/* 2128 */     for (int i = 0; i < aboolean1.length; i++) {
/* 2129 */       Boolean obool = aboolean1[i];
/*      */       
/* 2131 */       if (obool != null) {
/* 2132 */         aboolean[i] = obool.booleanValue();
/*      */       }
/*      */     } 
/*      */   }
/*      */   
/*      */   public static void resetDisplayLists() {
/* 2138 */     SMCLog.info("Reset model renderers");
/* 2139 */     countResetDisplayLists++;
/* 2140 */     SMCLog.info("Reset world renderers");
/* 2141 */     mc.renderGlobal.loadRenderers();
/*      */   }
/*      */   
/*      */   private static void setupProgram(Program program, String vShaderPath, String gShaderPath, String fShaderPath) {
/* 2145 */     checkGLError("pre setupProgram");
/* 2146 */     int i = ARBShaderObjects.glCreateProgramObjectARB();
/* 2147 */     checkGLError("create");
/*      */     
/* 2149 */     if (i != 0) {
/* 2150 */       progUseEntityAttrib = false;
/* 2151 */       progUseMidTexCoordAttrib = false;
/* 2152 */       progUseTangentAttrib = false;
/* 2153 */       int j = createVertShader(program, vShaderPath);
/* 2154 */       int k = createGeomShader(program, gShaderPath);
/* 2155 */       int l = createFragShader(program, fShaderPath);
/* 2156 */       checkGLError("create");
/*      */       
/* 2158 */       if (j == 0 && k == 0 && l == 0) {
/* 2159 */         ARBShaderObjects.glDeleteObjectARB(i);
/* 2160 */         i = 0;
/* 2161 */         program.resetId();
/*      */       } else {
/* 2163 */         if (j != 0) {
/* 2164 */           ARBShaderObjects.glAttachObjectARB(i, j);
/* 2165 */           checkGLError("attach");
/*      */         } 
/*      */         
/* 2168 */         if (k != 0) {
/* 2169 */           ARBShaderObjects.glAttachObjectARB(i, k);
/* 2170 */           checkGLError("attach");
/*      */           
/* 2172 */           if (progArbGeometryShader4) {
/* 2173 */             ARBGeometryShader4.glProgramParameteriARB(i, 36315, 4);
/* 2174 */             ARBGeometryShader4.glProgramParameteriARB(i, 36316, 5);
/* 2175 */             ARBGeometryShader4.glProgramParameteriARB(i, 36314, progMaxVerticesOut);
/* 2176 */             checkGLError("arbGeometryShader4");
/*      */           } 
/*      */           
/* 2179 */           hasGeometryShaders = true;
/*      */         } 
/*      */         
/* 2182 */         if (l != 0) {
/* 2183 */           ARBShaderObjects.glAttachObjectARB(i, l);
/* 2184 */           checkGLError("attach");
/*      */         } 
/*      */         
/* 2187 */         if (progUseEntityAttrib) {
/* 2188 */           ARBVertexShader.glBindAttribLocationARB(i, entityAttrib, "mc_Entity");
/* 2189 */           checkGLError("mc_Entity");
/*      */         } 
/*      */         
/* 2192 */         if (progUseMidTexCoordAttrib) {
/* 2193 */           ARBVertexShader.glBindAttribLocationARB(i, midTexCoordAttrib, "mc_midTexCoord");
/* 2194 */           checkGLError("mc_midTexCoord");
/*      */         } 
/*      */         
/* 2197 */         if (progUseTangentAttrib) {
/* 2198 */           ARBVertexShader.glBindAttribLocationARB(i, tangentAttrib, "at_tangent");
/* 2199 */           checkGLError("at_tangent");
/*      */         } 
/*      */         
/* 2202 */         ARBShaderObjects.glLinkProgramARB(i);
/*      */         
/* 2204 */         if (GL20.glGetProgrami(i, 35714) != 1) {
/* 2205 */           SMCLog.severe("Error linking program: " + i + " (" + program.getName() + ")");
/*      */         }
/*      */         
/* 2208 */         printLogInfo(i, program.getName());
/*      */         
/* 2210 */         if (j != 0) {
/* 2211 */           ARBShaderObjects.glDetachObjectARB(i, j);
/* 2212 */           ARBShaderObjects.glDeleteObjectARB(j);
/*      */         } 
/*      */         
/* 2215 */         if (k != 0) {
/* 2216 */           ARBShaderObjects.glDetachObjectARB(i, k);
/* 2217 */           ARBShaderObjects.glDeleteObjectARB(k);
/*      */         } 
/*      */         
/* 2220 */         if (l != 0) {
/* 2221 */           ARBShaderObjects.glDetachObjectARB(i, l);
/* 2222 */           ARBShaderObjects.glDeleteObjectARB(l);
/*      */         } 
/*      */         
/* 2225 */         program.setId(i);
/* 2226 */         program.setRef(i);
/* 2227 */         useProgram(program);
/* 2228 */         ARBShaderObjects.glValidateProgramARB(i);
/* 2229 */         useProgram(ProgramNone);
/* 2230 */         printLogInfo(i, program.getName());
/* 2231 */         int i1 = GL20.glGetProgrami(i, 35715);
/*      */         
/* 2233 */         if (i1 != 1) {
/* 2234 */           String s = "\"";
/* 2235 */           printChatAndLogError("[Shaders] Error: Invalid program " + s + program.getName() + s);
/* 2236 */           ARBShaderObjects.glDeleteObjectARB(i);
/* 2237 */           i = 0;
/* 2238 */           program.resetId();
/*      */         } 
/*      */       } 
/*      */     } 
/*      */   }
/*      */   
/*      */   private static int createVertShader(Program program, String filename) {
/* 2245 */     int i = ARBShaderObjects.glCreateShaderObjectARB(35633);
/*      */     
/* 2247 */     if (i == 0) {
/* 2248 */       return 0;
/*      */     }
/* 2250 */     StringBuilder stringbuilder = new StringBuilder(131072);
/* 2251 */     BufferedReader bufferedreader = null;
/*      */     
/*      */     try {
/* 2254 */       bufferedreader = new BufferedReader(getShaderReader(filename));
/* 2255 */     } catch (Exception var10) {
/* 2256 */       ARBShaderObjects.glDeleteObjectARB(i);
/* 2257 */       return 0;
/*      */     } 
/*      */     
/* 2260 */     ShaderOption[] ashaderoption = getChangedOptions(shaderPackOptions);
/* 2261 */     List<String> list = new ArrayList<>();
/*      */     
/* 2263 */     if (bufferedreader != null) {
/*      */       try {
/* 2265 */         bufferedreader = ShaderPackParser.resolveIncludes(bufferedreader, filename, shaderPack, 0, list, 0);
/* 2266 */         MacroState macrostate = new MacroState();
/*      */         
/*      */         while (true) {
/* 2269 */           String s = bufferedreader.readLine();
/*      */           
/* 2271 */           if (s == null) {
/* 2272 */             bufferedreader.close();
/*      */             
/*      */             break;
/*      */           } 
/* 2276 */           s = applyOptions(s, ashaderoption);
/* 2277 */           stringbuilder.append(s).append('\n');
/*      */           
/* 2279 */           if (macrostate.processLine(s)) {
/* 2280 */             ShaderLine shaderline = ShaderParser.parseLine(s);
/*      */             
/* 2282 */             if (shaderline != null) {
/* 2283 */               if (shaderline.isAttribute("mc_Entity")) {
/* 2284 */                 useEntityAttrib = true;
/* 2285 */                 progUseEntityAttrib = true;
/* 2286 */               } else if (shaderline.isAttribute("mc_midTexCoord")) {
/* 2287 */                 useMidTexCoordAttrib = true;
/* 2288 */                 progUseMidTexCoordAttrib = true;
/* 2289 */               } else if (shaderline.isAttribute("at_tangent")) {
/* 2290 */                 useTangentAttrib = true;
/* 2291 */                 progUseTangentAttrib = true;
/*      */               } 
/*      */               
/* 2294 */               if (shaderline.isConstInt("countInstances")) {
/* 2295 */                 program.setCountInstances(shaderline.getValueInt());
/* 2296 */                 SMCLog.info("countInstances: " + program.getCountInstances());
/*      */               } 
/*      */             } 
/*      */           } 
/*      */         } 
/* 2301 */       } catch (Exception exception) {
/* 2302 */         SMCLog.severe("Couldn't read " + filename + "!");
/* 2303 */         exception.printStackTrace();
/* 2304 */         ARBShaderObjects.glDeleteObjectARB(i);
/* 2305 */         return 0;
/*      */       } 
/*      */     }
/*      */     
/* 2309 */     if (saveFinalShaders) {
/* 2310 */       saveShader(filename, stringbuilder.toString());
/*      */     }
/*      */     
/* 2313 */     ARBShaderObjects.glShaderSourceARB(i, stringbuilder);
/* 2314 */     ARBShaderObjects.glCompileShaderARB(i);
/*      */     
/* 2316 */     if (GL20.glGetShaderi(i, 35713) != 1) {
/* 2317 */       SMCLog.severe("Error compiling vertex shader: " + filename);
/*      */     }
/*      */     
/* 2320 */     printShaderLogInfo(i, filename, list);
/* 2321 */     return i;
/*      */   }
/*      */ 
/*      */   
/*      */   private static int createGeomShader(Program program, String filename) {
/* 2326 */     int i = ARBShaderObjects.glCreateShaderObjectARB(36313);
/*      */     
/* 2328 */     if (i == 0) {
/* 2329 */       return 0;
/*      */     }
/* 2331 */     StringBuilder stringbuilder = new StringBuilder(131072);
/* 2332 */     BufferedReader bufferedreader = null;
/*      */     
/*      */     try {
/* 2335 */       bufferedreader = new BufferedReader(getShaderReader(filename));
/* 2336 */     } catch (Exception var11) {
/* 2337 */       ARBShaderObjects.glDeleteObjectARB(i);
/* 2338 */       return 0;
/*      */     } 
/*      */     
/* 2341 */     ShaderOption[] ashaderoption = getChangedOptions(shaderPackOptions);
/* 2342 */     List<String> list = new ArrayList<>();
/* 2343 */     progArbGeometryShader4 = false;
/* 2344 */     progMaxVerticesOut = 3;
/*      */     
/* 2346 */     if (bufferedreader != null) {
/*      */       try {
/* 2348 */         bufferedreader = ShaderPackParser.resolveIncludes(bufferedreader, filename, shaderPack, 0, list, 0);
/* 2349 */         MacroState macrostate = new MacroState();
/*      */         
/*      */         while (true) {
/* 2352 */           String s = bufferedreader.readLine();
/*      */           
/* 2354 */           if (s == null) {
/* 2355 */             bufferedreader.close();
/*      */             
/*      */             break;
/*      */           } 
/* 2359 */           s = applyOptions(s, ashaderoption);
/* 2360 */           stringbuilder.append(s).append('\n');
/*      */           
/* 2362 */           if (macrostate.processLine(s)) {
/* 2363 */             ShaderLine shaderline = ShaderParser.parseLine(s);
/*      */             
/* 2365 */             if (shaderline != null) {
/* 2366 */               if (shaderline.isExtension("GL_ARB_geometry_shader4")) {
/* 2367 */                 String s1 = Config.normalize(shaderline.getValue());
/*      */                 
/* 2369 */                 if (s1.equals("enable") || s1.equals("require") || s1.equals("warn")) {
/* 2370 */                   progArbGeometryShader4 = true;
/*      */                 }
/*      */               } 
/*      */               
/* 2374 */               if (shaderline.isConstInt("maxVerticesOut")) {
/* 2375 */                 progMaxVerticesOut = shaderline.getValueInt();
/*      */               }
/*      */             } 
/*      */           } 
/*      */         } 
/* 2380 */       } catch (Exception exception) {
/* 2381 */         SMCLog.severe("Couldn't read " + filename + "!");
/* 2382 */         exception.printStackTrace();
/* 2383 */         ARBShaderObjects.glDeleteObjectARB(i);
/* 2384 */         return 0;
/*      */       } 
/*      */     }
/*      */     
/* 2388 */     if (saveFinalShaders) {
/* 2389 */       saveShader(filename, stringbuilder.toString());
/*      */     }
/*      */     
/* 2392 */     ARBShaderObjects.glShaderSourceARB(i, stringbuilder);
/* 2393 */     ARBShaderObjects.glCompileShaderARB(i);
/*      */     
/* 2395 */     if (GL20.glGetShaderi(i, 35713) != 1) {
/* 2396 */       SMCLog.severe("Error compiling geometry shader: " + filename);
/*      */     }
/*      */     
/* 2399 */     printShaderLogInfo(i, filename, list);
/* 2400 */     return i;
/*      */   }
/*      */ 
/*      */   
/*      */   private static int createFragShader(Program program, String filename) {
/* 2405 */     int i = ARBShaderObjects.glCreateShaderObjectARB(35632);
/*      */     
/* 2407 */     if (i == 0) {
/* 2408 */       return 0;
/*      */     }
/* 2410 */     StringBuilder stringbuilder = new StringBuilder(131072);
/* 2411 */     BufferedReader bufferedreader = null;
/*      */     
/*      */     try {
/* 2414 */       bufferedreader = new BufferedReader(getShaderReader(filename));
/* 2415 */     } catch (Exception var14) {
/* 2416 */       ARBShaderObjects.glDeleteObjectARB(i);
/* 2417 */       return 0;
/*      */     } 
/*      */     
/* 2420 */     ShaderOption[] ashaderoption = getChangedOptions(shaderPackOptions);
/* 2421 */     List<String> list = new ArrayList<>();
/*      */     
/* 2423 */     if (bufferedreader != null) {
/*      */       try {
/* 2425 */         bufferedreader = ShaderPackParser.resolveIncludes(bufferedreader, filename, shaderPack, 0, list, 0);
/* 2426 */         MacroState macrostate = new MacroState();
/*      */         
/*      */         while (true) {
/* 2429 */           String s = bufferedreader.readLine();
/*      */           
/* 2431 */           if (s == null) {
/* 2432 */             bufferedreader.close();
/*      */             
/*      */             break;
/*      */           } 
/* 2436 */           s = applyOptions(s, ashaderoption);
/* 2437 */           stringbuilder.append(s).append('\n');
/*      */           
/* 2439 */           if (macrostate.processLine(s)) {
/* 2440 */             ShaderLine shaderline = ShaderParser.parseLine(s);
/*      */             
/* 2442 */             if (shaderline != null) {
/* 2443 */               if (shaderline.isUniform()) {
/* 2444 */                 String s6 = shaderline.getName();
/*      */                 
/*      */                 int l1;
/* 2447 */                 if ((l1 = ShaderParser.getShadowDepthIndex(s6)) >= 0) {
/* 2448 */                   usedShadowDepthBuffers = Math.max(usedShadowDepthBuffers, l1 + 1); continue;
/* 2449 */                 }  if ((l1 = ShaderParser.getShadowColorIndex(s6)) >= 0) {
/* 2450 */                   usedShadowColorBuffers = Math.max(usedShadowColorBuffers, l1 + 1); continue;
/* 2451 */                 }  if ((l1 = ShaderParser.getDepthIndex(s6)) >= 0) {
/* 2452 */                   usedDepthBuffers = Math.max(usedDepthBuffers, l1 + 1); continue;
/* 2453 */                 }  if (s6.equals("gdepth") && gbuffersFormat[1] == 6408) {
/* 2454 */                   gbuffersFormat[1] = 34836; continue;
/* 2455 */                 }  if ((l1 = ShaderParser.getColorIndex(s6)) >= 0) {
/* 2456 */                   usedColorBuffers = Math.max(usedColorBuffers, l1 + 1); continue;
/* 2457 */                 }  if (s6.equals("centerDepthSmooth"))
/* 2458 */                   centerDepthSmoothEnabled = true;  continue;
/*      */               } 
/* 2460 */               if (!shaderline.isConstInt("shadowMapResolution") && !shaderline.isProperty("SHADOWRES")) {
/* 2461 */                 if (!shaderline.isConstFloat("shadowMapFov") && !shaderline.isProperty("SHADOWFOV")) {
/* 2462 */                   if (!shaderline.isConstFloat("shadowDistance") && !shaderline.isProperty("SHADOWHPL")) {
/* 2463 */                     if (shaderline.isConstFloat("shadowDistanceRenderMul")) {
/* 2464 */                       shadowDistanceRenderMul = shaderline.getValueFloat();
/* 2465 */                       SMCLog.info("Shadow distance render mul: " + shadowDistanceRenderMul); continue;
/* 2466 */                     }  if (shaderline.isConstFloat("shadowIntervalSize")) {
/* 2467 */                       shadowIntervalSize = shaderline.getValueFloat();
/* 2468 */                       SMCLog.info("Shadow map interval size: " + shadowIntervalSize); continue;
/* 2469 */                     }  if (shaderline.isConstBool("generateShadowMipmap", true)) {
/* 2470 */                       Arrays.fill(shadowMipmapEnabled, true);
/* 2471 */                       SMCLog.info("Generate shadow mipmap"); continue;
/* 2472 */                     }  if (shaderline.isConstBool("generateShadowColorMipmap", true)) {
/* 2473 */                       Arrays.fill(shadowColorMipmapEnabled, true);
/* 2474 */                       SMCLog.info("Generate shadow color mipmap"); continue;
/* 2475 */                     }  if (shaderline.isConstBool("shadowHardwareFiltering", true)) {
/* 2476 */                       Arrays.fill(shadowHardwareFilteringEnabled, true);
/* 2477 */                       SMCLog.info("Hardware shadow filtering enabled."); continue;
/* 2478 */                     }  if (shaderline.isConstBool("shadowHardwareFiltering0", true)) {
/* 2479 */                       shadowHardwareFilteringEnabled[0] = true;
/* 2480 */                       SMCLog.info("shadowHardwareFiltering0"); continue;
/* 2481 */                     }  if (shaderline.isConstBool("shadowHardwareFiltering1", true)) {
/* 2482 */                       shadowHardwareFilteringEnabled[1] = true;
/* 2483 */                       SMCLog.info("shadowHardwareFiltering1"); continue;
/* 2484 */                     }  if (shaderline.isConstBool("shadowtex0Mipmap", "shadowtexMipmap", true)) {
/* 2485 */                       shadowMipmapEnabled[0] = true;
/* 2486 */                       SMCLog.info("shadowtex0Mipmap"); continue;
/* 2487 */                     }  if (shaderline.isConstBool("shadowtex1Mipmap", true)) {
/* 2488 */                       shadowMipmapEnabled[1] = true;
/* 2489 */                       SMCLog.info("shadowtex1Mipmap"); continue;
/* 2490 */                     }  if (shaderline.isConstBool("shadowcolor0Mipmap", "shadowColor0Mipmap", true)) {
/* 2491 */                       shadowColorMipmapEnabled[0] = true;
/* 2492 */                       SMCLog.info("shadowcolor0Mipmap"); continue;
/* 2493 */                     }  if (shaderline.isConstBool("shadowcolor1Mipmap", "shadowColor1Mipmap", true)) {
/* 2494 */                       shadowColorMipmapEnabled[1] = true;
/* 2495 */                       SMCLog.info("shadowcolor1Mipmap"); continue;
/* 2496 */                     }  if (shaderline.isConstBool("shadowtex0Nearest", "shadowtexNearest", "shadow0MinMagNearest", true)) {
/* 2497 */                       shadowFilterNearest[0] = true;
/* 2498 */                       SMCLog.info("shadowtex0Nearest"); continue;
/* 2499 */                     }  if (shaderline.isConstBool("shadowtex1Nearest", "shadow1MinMagNearest", true)) {
/* 2500 */                       shadowFilterNearest[1] = true;
/* 2501 */                       SMCLog.info("shadowtex1Nearest"); continue;
/* 2502 */                     }  if (shaderline.isConstBool("shadowcolor0Nearest", "shadowColor0Nearest", "shadowColor0MinMagNearest", true)) {
/* 2503 */                       shadowColorFilterNearest[0] = true;
/* 2504 */                       SMCLog.info("shadowcolor0Nearest"); continue;
/* 2505 */                     }  if (shaderline.isConstBool("shadowcolor1Nearest", "shadowColor1Nearest", "shadowColor1MinMagNearest", true)) {
/* 2506 */                       shadowColorFilterNearest[1] = true;
/* 2507 */                       SMCLog.info("shadowcolor1Nearest"); continue;
/* 2508 */                     }  if (!shaderline.isConstFloat("wetnessHalflife") && !shaderline.isProperty("WETNESSHL")) {
/* 2509 */                       if (!shaderline.isConstFloat("drynessHalflife") && !shaderline.isProperty("DRYNESSHL")) {
/* 2510 */                         if (shaderline.isConstFloat("eyeBrightnessHalflife")) {
/* 2511 */                           eyeBrightnessHalflife = shaderline.getValueFloat();
/* 2512 */                           SMCLog.info("Eye brightness halflife: " + eyeBrightnessHalflife); continue;
/* 2513 */                         }  if (shaderline.isConstFloat("centerDepthHalflife")) {
/* 2514 */                           centerDepthSmoothHalflife = shaderline.getValueFloat();
/* 2515 */                           SMCLog.info("Center depth halflife: " + centerDepthSmoothHalflife); continue;
/* 2516 */                         }  if (shaderline.isConstFloat("sunPathRotation")) {
/* 2517 */                           sunPathRotation = shaderline.getValueFloat();
/* 2518 */                           SMCLog.info("Sun path rotation: " + sunPathRotation); continue;
/* 2519 */                         }  if (shaderline.isConstFloat("ambientOcclusionLevel")) {
/* 2520 */                           aoLevel = Config.limit(shaderline.getValueFloat(), 0.0F, 1.0F);
/* 2521 */                           SMCLog.info("AO Level: " + aoLevel); continue;
/* 2522 */                         }  if (shaderline.isConstInt("superSamplingLevel")) {
/* 2523 */                           int i1 = shaderline.getValueInt();
/*      */                           
/* 2525 */                           if (i1 > 1) {
/* 2526 */                             SMCLog.info("Super sampling level: " + i1 + "x");
/* 2527 */                             superSamplingLevel = i1; continue;
/*      */                           } 
/* 2529 */                           superSamplingLevel = 1; continue;
/*      */                         } 
/* 2531 */                         if (shaderline.isConstInt("noiseTextureResolution")) {
/* 2532 */                           noiseTextureResolution = shaderline.getValueInt();
/* 2533 */                           noiseTextureEnabled = true;
/* 2534 */                           SMCLog.info("Noise texture enabled");
/* 2535 */                           SMCLog.info("Noise texture resolution: " + noiseTextureResolution); continue;
/* 2536 */                         }  if (shaderline.isConstIntSuffix("Format")) {
/* 2537 */                           String s5 = StrUtils.removeSuffix(shaderline.getName(), "Format");
/* 2538 */                           String s7 = shaderline.getValue();
/* 2539 */                           int i2 = getBufferIndexFromString(s5);
/* 2540 */                           int l = getTextureFormatFromString(s7);
/*      */                           
/* 2542 */                           if (i2 >= 0 && l != 0) {
/* 2543 */                             gbuffersFormat[i2] = l;
/* 2544 */                             SMCLog.info("%s format: %s", new Object[] { s5, s7 });
/*      */                           }  continue;
/* 2546 */                         }  if (shaderline.isConstBoolSuffix("Clear", false)) {
/* 2547 */                           if (ShaderParser.isComposite(filename) || ShaderParser.isDeferred(filename)) {
/* 2548 */                             String s4 = StrUtils.removeSuffix(shaderline.getName(), "Clear");
/* 2549 */                             int k1 = getBufferIndexFromString(s4);
/*      */                             
/* 2551 */                             if (k1 >= 0) {
/* 2552 */                               gbuffersClear[k1] = false;
/* 2553 */                               SMCLog.info("%s clear disabled", new Object[] { s4 });
/*      */                             } 
/*      */                           }  continue;
/* 2556 */                         }  if (shaderline.isConstVec4Suffix("ClearColor")) {
/* 2557 */                           if (ShaderParser.isComposite(filename) || ShaderParser.isDeferred(filename)) {
/* 2558 */                             String s3 = StrUtils.removeSuffix(shaderline.getName(), "ClearColor");
/* 2559 */                             int j1 = getBufferIndexFromString(s3);
/*      */                             
/* 2561 */                             if (j1 >= 0) {
/* 2562 */                               Vector4f vector4f = shaderline.getValueVec4();
/*      */                               
/* 2564 */                               if (vector4f != null) {
/* 2565 */                                 gbuffersClearColor[j1] = vector4f;
/* 2566 */                                 SMCLog.info("%s clear color: %s %s %s %s", new Object[] { s3, Float.valueOf(vector4f.getX()), Float.valueOf(vector4f.getY()), Float.valueOf(vector4f.getZ()), Float.valueOf(vector4f.getW()) }); continue;
/*      */                               } 
/* 2568 */                               SMCLog.warning("Invalid color value: " + shaderline.getValue());
/*      */                             } 
/*      */                           }  continue;
/*      */                         } 
/* 2572 */                         if (shaderline.isProperty("GAUX4FORMAT", "RGBA32F")) {
/* 2573 */                           gbuffersFormat[7] = 34836;
/* 2574 */                           SMCLog.info("gaux4 format : RGB32AF"); continue;
/* 2575 */                         }  if (shaderline.isProperty("GAUX4FORMAT", "RGB32F")) {
/* 2576 */                           gbuffersFormat[7] = 34837;
/* 2577 */                           SMCLog.info("gaux4 format : RGB32F"); continue;
/* 2578 */                         }  if (shaderline.isProperty("GAUX4FORMAT", "RGB16")) {
/* 2579 */                           gbuffersFormat[7] = 32852;
/* 2580 */                           SMCLog.info("gaux4 format : RGB16"); continue;
/* 2581 */                         }  if (shaderline.isConstBoolSuffix("MipmapEnabled", true)) {
/* 2582 */                           if (ShaderParser.isComposite(filename) || ShaderParser.isDeferred(filename) || ShaderParser.isFinal(filename)) {
/* 2583 */                             String s2 = StrUtils.removeSuffix(shaderline.getName(), "MipmapEnabled");
/* 2584 */                             int j = getBufferIndexFromString(s2);
/*      */                             
/* 2586 */                             if (j >= 0) {
/* 2587 */                               int k = program.getCompositeMipmapSetting();
/* 2588 */                               k |= 1 << j;
/* 2589 */                               program.setCompositeMipmapSetting(k);
/* 2590 */                               SMCLog.info("%s mipmap enabled", new Object[] { s2 });
/*      */                             } 
/*      */                           }  continue;
/* 2593 */                         }  if (shaderline.isProperty("DRAWBUFFERS")) {
/* 2594 */                           String s1 = shaderline.getValue();
/*      */                           
/* 2596 */                           if (ShaderParser.isValidDrawBuffers(s1)) {
/* 2597 */                             program.setDrawBufSettings(s1); continue;
/*      */                           } 
/* 2599 */                           SMCLog.warning("Invalid draw buffers: " + s1);
/*      */                         } 
/*      */                         continue;
/*      */                       } 
/* 2603 */                       drynessHalfLife = shaderline.getValueFloat();
/* 2604 */                       SMCLog.info("Dryness halflife: " + drynessHalfLife);
/*      */                       continue;
/*      */                     } 
/* 2607 */                     wetnessHalfLife = shaderline.getValueFloat();
/* 2608 */                     SMCLog.info("Wetness halflife: " + wetnessHalfLife);
/*      */                     continue;
/*      */                   } 
/* 2611 */                   shadowMapHalfPlane = shaderline.getValueFloat();
/* 2612 */                   shadowMapIsOrtho = true;
/* 2613 */                   SMCLog.info("Shadow map distance: " + shadowMapHalfPlane);
/*      */                   continue;
/*      */                 } 
/* 2616 */                 shadowMapFOV = shaderline.getValueFloat();
/* 2617 */                 shadowMapIsOrtho = false;
/* 2618 */                 SMCLog.info("Shadow map field of view: " + shadowMapFOV);
/*      */                 continue;
/*      */               } 
/* 2621 */               spShadowMapWidth = spShadowMapHeight = shaderline.getValueInt();
/* 2622 */               shadowMapWidth = shadowMapHeight = Math.round(spShadowMapWidth * configShadowResMul);
/* 2623 */               SMCLog.info("Shadow map resolution: " + spShadowMapWidth);
/*      */             }
/*      */           
/*      */           } 
/*      */         } 
/* 2628 */       } catch (Exception exception) {
/* 2629 */         SMCLog.severe("Couldn't read " + filename + "!");
/* 2630 */         exception.printStackTrace();
/* 2631 */         ARBShaderObjects.glDeleteObjectARB(i);
/* 2632 */         return 0;
/*      */       } 
/*      */     }
/*      */     
/* 2636 */     if (saveFinalShaders) {
/* 2637 */       saveShader(filename, stringbuilder.toString());
/*      */     }
/*      */     
/* 2640 */     ARBShaderObjects.glShaderSourceARB(i, stringbuilder);
/* 2641 */     ARBShaderObjects.glCompileShaderARB(i);
/*      */     
/* 2643 */     if (GL20.glGetShaderi(i, 35713) != 1) {
/* 2644 */       SMCLog.severe("Error compiling fragment shader: " + filename);
/*      */     }
/*      */     
/* 2647 */     printShaderLogInfo(i, filename, list);
/* 2648 */     return i;
/*      */   }
/*      */ 
/*      */   
/*      */   private static Reader getShaderReader(String filename) {
/* 2653 */     return new InputStreamReader(shaderPack.getResourceAsStream(filename));
/*      */   }
/*      */   
/*      */   public static void saveShader(String filename, String code) {
/*      */     try {
/* 2658 */       File file1 = new File(shaderPacksDir, "debug/" + filename);
/* 2659 */       file1.getParentFile().mkdirs();
/* 2660 */       Config.writeFile(file1, code);
/* 2661 */     } catch (IOException ioexception) {
/* 2662 */       Config.warn("Error saving: " + filename);
/* 2663 */       ioexception.printStackTrace();
/*      */     } 
/*      */   }
/*      */   
/*      */   private static void clearDirectory(File dir) {
/* 2668 */     if (dir.exists() && 
/* 2669 */       dir.isDirectory()) {
/* 2670 */       File[] afile = dir.listFiles();
/*      */       
/* 2672 */       if (afile != null) {
/* 2673 */         for (int i = 0; i < afile.length; i++) {
/* 2674 */           File file1 = afile[i];
/*      */           
/* 2676 */           if (file1.isDirectory()) {
/* 2677 */             clearDirectory(file1);
/*      */           }
/*      */           
/* 2680 */           file1.delete();
/*      */         } 
/*      */       }
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   private static boolean printLogInfo(int obj, String name) {
/* 2688 */     IntBuffer intbuffer = BufferUtils.createIntBuffer(1);
/* 2689 */     ARBShaderObjects.glGetObjectParameterARB(obj, 35716, intbuffer);
/* 2690 */     int i = intbuffer.get();
/*      */     
/* 2692 */     if (i > 1) {
/* 2693 */       ByteBuffer bytebuffer = BufferUtils.createByteBuffer(i);
/* 2694 */       intbuffer.flip();
/* 2695 */       ARBShaderObjects.glGetInfoLogARB(obj, intbuffer, bytebuffer);
/* 2696 */       byte[] abyte = new byte[i];
/* 2697 */       bytebuffer.get(abyte);
/*      */       
/* 2699 */       if (abyte[i - 1] == 0) {
/* 2700 */         abyte[i - 1] = 10;
/*      */       }
/*      */       
/* 2703 */       String s = new String(abyte, Charsets.US_ASCII);
/* 2704 */       s = StrUtils.trim(s, " \n\r\t");
/* 2705 */       SMCLog.info("Info log: " + name + "\n" + s);
/* 2706 */       return false;
/*      */     } 
/* 2708 */     return true;
/*      */   }
/*      */ 
/*      */   
/*      */   private static boolean printShaderLogInfo(int shader, String name, List<String> listFiles) {
/* 2713 */     IntBuffer intbuffer = BufferUtils.createIntBuffer(1);
/* 2714 */     int i = GL20.glGetShaderi(shader, 35716);
/*      */     
/* 2716 */     if (i <= 1) {
/* 2717 */       return true;
/*      */     }
/* 2719 */     for (int j = 0; j < listFiles.size(); j++) {
/* 2720 */       String s = listFiles.get(j);
/* 2721 */       SMCLog.info("File: " + (j + 1) + " = " + s);
/*      */     } 
/*      */     
/* 2724 */     String s1 = GL20.glGetShaderInfoLog(shader, i);
/* 2725 */     s1 = StrUtils.trim(s1, " \n\r\t");
/* 2726 */     SMCLog.info("Shader info log: " + name + "\n" + s1);
/* 2727 */     return false;
/*      */   }
/*      */ 
/*      */   
/*      */   public static void setDrawBuffers(IntBuffer drawBuffers) {
/* 2732 */     if (drawBuffers == null) {
/* 2733 */       drawBuffers = drawBuffersNone;
/*      */     }
/*      */     
/* 2736 */     if (activeDrawBuffers != drawBuffers) {
/* 2737 */       activeDrawBuffers = drawBuffers;
/* 2738 */       GL20.glDrawBuffers(drawBuffers);
/* 2739 */       checkGLError("setDrawBuffers");
/*      */     } 
/*      */   }
/*      */   
/*      */   public static void useProgram(Program program) {
/* 2744 */     checkGLError("pre-useProgram");
/*      */     
/* 2746 */     if (isShadowPass) {
/* 2747 */       program = ProgramShadow;
/* 2748 */     } else if (isEntitiesGlowing) {
/* 2749 */       program = ProgramEntitiesGlowing;
/*      */     } 
/*      */     
/* 2752 */     if (activeProgram != program) {
/* 2753 */       updateAlphaBlend(activeProgram, program);
/* 2754 */       activeProgram = program;
/* 2755 */       int i = program.getId();
/* 2756 */       activeProgramID = i;
/* 2757 */       ARBShaderObjects.glUseProgramObjectARB(i);
/*      */       
/* 2759 */       if (checkGLError("useProgram") != 0) {
/* 2760 */         program.setId(0);
/* 2761 */         i = program.getId();
/* 2762 */         activeProgramID = i;
/* 2763 */         ARBShaderObjects.glUseProgramObjectARB(i);
/*      */       } 
/*      */       
/* 2766 */       shaderUniforms.setProgram(i);
/*      */       
/* 2768 */       if (customUniforms != null) {
/* 2769 */         customUniforms.setProgram(i);
/*      */       }
/*      */       
/* 2772 */       if (i != 0) {
/* 2773 */         IntBuffer intbuffer = program.getDrawBuffers();
/*      */         
/* 2775 */         if (isRenderingDfb) {
/* 2776 */           setDrawBuffers(intbuffer);
/*      */         }
/*      */         
/* 2779 */         activeCompositeMipmapSetting = program.getCompositeMipmapSetting();
/*      */         
/* 2781 */         switch (program.getProgramStage()) {
/*      */           case GBUFFERS:
/* 2783 */             setProgramUniform1i(uniform_texture, 0);
/* 2784 */             setProgramUniform1i(uniform_lightmap, 1);
/* 2785 */             setProgramUniform1i(uniform_normals, 2);
/* 2786 */             setProgramUniform1i(uniform_specular, 3);
/* 2787 */             setProgramUniform1i(uniform_shadow, waterShadowEnabled ? 5 : 4);
/* 2788 */             setProgramUniform1i(uniform_watershadow, 4);
/* 2789 */             setProgramUniform1i(uniform_shadowtex0, 4);
/* 2790 */             setProgramUniform1i(uniform_shadowtex1, 5);
/* 2791 */             setProgramUniform1i(uniform_depthtex0, 6);
/*      */             
/* 2793 */             if (customTexturesGbuffers != null || hasDeferredPrograms) {
/* 2794 */               setProgramUniform1i(uniform_gaux1, 7);
/* 2795 */               setProgramUniform1i(uniform_gaux2, 8);
/* 2796 */               setProgramUniform1i(uniform_gaux3, 9);
/* 2797 */               setProgramUniform1i(uniform_gaux4, 10);
/*      */             } 
/*      */             
/* 2800 */             setProgramUniform1i(uniform_depthtex1, 11);
/* 2801 */             setProgramUniform1i(uniform_shadowcolor, 13);
/* 2802 */             setProgramUniform1i(uniform_shadowcolor0, 13);
/* 2803 */             setProgramUniform1i(uniform_shadowcolor1, 14);
/* 2804 */             setProgramUniform1i(uniform_noisetex, 15);
/*      */             break;
/*      */           
/*      */           case DEFERRED:
/*      */           case null:
/* 2809 */             setProgramUniform1i(uniform_gcolor, 0);
/* 2810 */             setProgramUniform1i(uniform_gdepth, 1);
/* 2811 */             setProgramUniform1i(uniform_gnormal, 2);
/* 2812 */             setProgramUniform1i(uniform_composite, 3);
/* 2813 */             setProgramUniform1i(uniform_gaux1, 7);
/* 2814 */             setProgramUniform1i(uniform_gaux2, 8);
/* 2815 */             setProgramUniform1i(uniform_gaux3, 9);
/* 2816 */             setProgramUniform1i(uniform_gaux4, 10);
/* 2817 */             setProgramUniform1i(uniform_colortex0, 0);
/* 2818 */             setProgramUniform1i(uniform_colortex1, 1);
/* 2819 */             setProgramUniform1i(uniform_colortex2, 2);
/* 2820 */             setProgramUniform1i(uniform_colortex3, 3);
/* 2821 */             setProgramUniform1i(uniform_colortex4, 7);
/* 2822 */             setProgramUniform1i(uniform_colortex5, 8);
/* 2823 */             setProgramUniform1i(uniform_colortex6, 9);
/* 2824 */             setProgramUniform1i(uniform_colortex7, 10);
/* 2825 */             setProgramUniform1i(uniform_shadow, waterShadowEnabled ? 5 : 4);
/* 2826 */             setProgramUniform1i(uniform_watershadow, 4);
/* 2827 */             setProgramUniform1i(uniform_shadowtex0, 4);
/* 2828 */             setProgramUniform1i(uniform_shadowtex1, 5);
/* 2829 */             setProgramUniform1i(uniform_gdepthtex, 6);
/* 2830 */             setProgramUniform1i(uniform_depthtex0, 6);
/* 2831 */             setProgramUniform1i(uniform_depthtex1, 11);
/* 2832 */             setProgramUniform1i(uniform_depthtex2, 12);
/* 2833 */             setProgramUniform1i(uniform_shadowcolor, 13);
/* 2834 */             setProgramUniform1i(uniform_shadowcolor0, 13);
/* 2835 */             setProgramUniform1i(uniform_shadowcolor1, 14);
/* 2836 */             setProgramUniform1i(uniform_noisetex, 15);
/*      */             break;
/*      */           
/*      */           case SHADOW:
/* 2840 */             setProgramUniform1i(uniform_tex, 0);
/* 2841 */             setProgramUniform1i(uniform_texture, 0);
/* 2842 */             setProgramUniform1i(uniform_lightmap, 1);
/* 2843 */             setProgramUniform1i(uniform_normals, 2);
/* 2844 */             setProgramUniform1i(uniform_specular, 3);
/* 2845 */             setProgramUniform1i(uniform_shadow, waterShadowEnabled ? 5 : 4);
/* 2846 */             setProgramUniform1i(uniform_watershadow, 4);
/* 2847 */             setProgramUniform1i(uniform_shadowtex0, 4);
/* 2848 */             setProgramUniform1i(uniform_shadowtex1, 5);
/*      */             
/* 2850 */             if (customTexturesGbuffers != null) {
/* 2851 */               setProgramUniform1i(uniform_gaux1, 7);
/* 2852 */               setProgramUniform1i(uniform_gaux2, 8);
/* 2853 */               setProgramUniform1i(uniform_gaux3, 9);
/* 2854 */               setProgramUniform1i(uniform_gaux4, 10);
/*      */             } 
/*      */             
/* 2857 */             setProgramUniform1i(uniform_shadowcolor, 13);
/* 2858 */             setProgramUniform1i(uniform_shadowcolor0, 13);
/* 2859 */             setProgramUniform1i(uniform_shadowcolor1, 14);
/* 2860 */             setProgramUniform1i(uniform_noisetex, 15);
/*      */             break;
/*      */         } 
/* 2863 */         ItemStack itemstack = (mc.thePlayer != null) ? mc.thePlayer.getHeldItem() : null;
/* 2864 */         Item item = (itemstack != null) ? itemstack.getItem() : null;
/* 2865 */         int j = -1;
/* 2866 */         Block block = null;
/*      */         
/* 2868 */         if (item != null) {
/* 2869 */           j = Item.itemRegistry.getIDForObject(item);
/* 2870 */           block = (Block)Block.blockRegistry.getObjectById(j);
/* 2871 */           j = ItemAliases.getItemAliasId(j);
/*      */         } 
/*      */         
/* 2874 */         int k = (block != null) ? block.getLightValue() : 0;
/* 2875 */         setProgramUniform1i(uniform_heldItemId, j);
/* 2876 */         setProgramUniform1i(uniform_heldBlockLightValue, k);
/* 2877 */         setProgramUniform1i(uniform_fogMode, fogEnabled ? fogMode : 0);
/* 2878 */         setProgramUniform1f(uniform_fogDensity, fogEnabled ? fogDensity : 0.0F);
/* 2879 */         setProgramUniform3f(uniform_fogColor, fogColorR, fogColorG, fogColorB);
/* 2880 */         setProgramUniform3f(uniform_skyColor, skyColorR, skyColorG, skyColorB);
/* 2881 */         setProgramUniform1i(uniform_worldTime, (int)(worldTime % 24000L));
/* 2882 */         setProgramUniform1i(uniform_worldDay, (int)(worldTime / 24000L));
/* 2883 */         setProgramUniform1i(uniform_moonPhase, moonPhase);
/* 2884 */         setProgramUniform1i(uniform_frameCounter, frameCounter);
/* 2885 */         setProgramUniform1f(uniform_frameTime, frameTime);
/* 2886 */         setProgramUniform1f(uniform_frameTimeCounter, frameTimeCounter);
/* 2887 */         setProgramUniform1f(uniform_sunAngle, sunAngle);
/* 2888 */         setProgramUniform1f(uniform_shadowAngle, shadowAngle);
/* 2889 */         setProgramUniform1f(uniform_rainStrength, rainStrength);
/* 2890 */         setProgramUniform1f(uniform_aspectRatio, renderWidth / renderHeight);
/* 2891 */         setProgramUniform1f(uniform_viewWidth, renderWidth);
/* 2892 */         setProgramUniform1f(uniform_viewHeight, renderHeight);
/* 2893 */         setProgramUniform1f(uniform_near, 0.05F);
/* 2894 */         setProgramUniform1f(uniform_far, (mc.gameSettings.renderDistanceChunks * 16));
/* 2895 */         setProgramUniform3f(uniform_sunPosition, sunPosition[0], sunPosition[1], sunPosition[2]);
/* 2896 */         setProgramUniform3f(uniform_moonPosition, moonPosition[0], moonPosition[1], moonPosition[2]);
/* 2897 */         setProgramUniform3f(uniform_shadowLightPosition, shadowLightPosition[0], shadowLightPosition[1], shadowLightPosition[2]);
/* 2898 */         setProgramUniform3f(uniform_upPosition, upPosition[0], upPosition[1], upPosition[2]);
/* 2899 */         setProgramUniform3f(uniform_previousCameraPosition, (float)previousCameraPositionX, (float)previousCameraPositionY, (float)previousCameraPositionZ);
/* 2900 */         setProgramUniform3f(uniform_cameraPosition, (float)cameraPositionX, (float)cameraPositionY, (float)cameraPositionZ);
/* 2901 */         setProgramUniformMatrix4ARB(uniform_gbufferModelView, false, modelView);
/* 2902 */         setProgramUniformMatrix4ARB(uniform_gbufferModelViewInverse, false, modelViewInverse);
/* 2903 */         setProgramUniformMatrix4ARB(uniform_gbufferPreviousProjection, false, previousProjection);
/* 2904 */         setProgramUniformMatrix4ARB(uniform_gbufferProjection, false, projection);
/* 2905 */         setProgramUniformMatrix4ARB(uniform_gbufferProjectionInverse, false, projectionInverse);
/* 2906 */         setProgramUniformMatrix4ARB(uniform_gbufferPreviousModelView, false, previousModelView);
/*      */         
/* 2908 */         if (usedShadowDepthBuffers > 0) {
/* 2909 */           setProgramUniformMatrix4ARB(uniform_shadowProjection, false, shadowProjection);
/* 2910 */           setProgramUniformMatrix4ARB(uniform_shadowProjectionInverse, false, shadowProjectionInverse);
/* 2911 */           setProgramUniformMatrix4ARB(uniform_shadowModelView, false, shadowModelView);
/* 2912 */           setProgramUniformMatrix4ARB(uniform_shadowModelViewInverse, false, shadowModelViewInverse);
/*      */         } 
/*      */         
/* 2915 */         setProgramUniform1f(uniform_wetness, wetness);
/* 2916 */         setProgramUniform1f(uniform_eyeAltitude, eyePosY);
/* 2917 */         setProgramUniform2i(uniform_eyeBrightness, eyeBrightness & 0xFFFF, eyeBrightness >> 16);
/* 2918 */         setProgramUniform2i(uniform_eyeBrightnessSmooth, Math.round(eyeBrightnessFadeX), Math.round(eyeBrightnessFadeY));
/* 2919 */         setProgramUniform2i(uniform_terrainTextureSize, terrainTextureSize[0], terrainTextureSize[1]);
/* 2920 */         setProgramUniform1i(uniform_terrainIconSize, terrainIconSize);
/* 2921 */         setProgramUniform1i(uniform_isEyeInWater, isEyeInWater);
/* 2922 */         setProgramUniform1f(uniform_nightVision, nightVision);
/* 2923 */         setProgramUniform1f(uniform_blindness, blindness);
/* 2924 */         setProgramUniform1f(uniform_screenBrightness, mc.gameSettings.saturation);
/* 2925 */         setProgramUniform1i(uniform_hideGUI, mc.gameSettings.thirdPersonView ? 1 : 0);
/* 2926 */         setProgramUniform1f(uniform_centerDepthSmooth, centerDepthSmooth);
/* 2927 */         setProgramUniform2i(uniform_atlasSize, atlasSizeX, atlasSizeY);
/*      */         
/* 2929 */         if (customUniforms != null) {
/* 2930 */           customUniforms.update();
/*      */         }
/*      */         
/* 2933 */         checkGLError("end useProgram");
/*      */       } 
/*      */     } 
/*      */   }
/*      */   
/*      */   private static void updateAlphaBlend(Program programOld, Program programNew) {
/* 2939 */     if (programOld.getAlphaState() != null) {
/* 2940 */       GlStateManager.unlockAlpha();
/*      */     }
/*      */     
/* 2943 */     if (programOld.getBlendState() != null) {
/* 2944 */       GlStateManager.unlockBlend();
/*      */     }
/*      */     
/* 2947 */     GlAlphaState glalphastate = programNew.getAlphaState();
/*      */     
/* 2949 */     if (glalphastate != null) {
/* 2950 */       GlStateManager.lockAlpha(glalphastate);
/*      */     }
/*      */     
/* 2953 */     GlBlendState glblendstate = programNew.getBlendState();
/*      */     
/* 2955 */     if (glblendstate != null) {
/* 2956 */       GlStateManager.lockBlend(glblendstate);
/*      */     }
/*      */   }
/*      */   
/*      */   private static void setProgramUniform1i(ShaderUniform1i su, int value) {
/* 2961 */     su.setValue(value);
/*      */   }
/*      */   
/*      */   private static void setProgramUniform2i(ShaderUniform2i su, int i0, int i1) {
/* 2965 */     su.setValue(i0, i1);
/*      */   }
/*      */   
/*      */   private static void setProgramUniform1f(ShaderUniform1f su, float value) {
/* 2969 */     su.setValue(value);
/*      */   }
/*      */   
/*      */   private static void setProgramUniform3f(ShaderUniform3f su, float f0, float f1, float f2) {
/* 2973 */     su.setValue(f0, f1, f2);
/*      */   }
/*      */   
/*      */   private static void setProgramUniformMatrix4ARB(ShaderUniformM4 su, boolean transpose, FloatBuffer matrix) {
/* 2977 */     su.setValue(transpose, matrix);
/*      */   }
/*      */   
/*      */   public static int getBufferIndexFromString(String name) {
/* 2981 */     return (!name.equals("colortex0") && !name.equals("gcolor")) ? ((!name.equals("colortex1") && !name.equals("gdepth")) ? ((!name.equals("colortex2") && !name.equals("gnormal")) ? ((!name.equals("colortex3") && !name.equals("composite")) ? ((!name.equals("colortex4") && !name.equals("gaux1")) ? ((!name.equals("colortex5") && !name.equals("gaux2")) ? ((!name.equals("colortex6") && !name.equals("gaux3")) ? ((!name.equals("colortex7") && !name.equals("gaux4")) ? -1 : 7) : 6) : 5) : 4) : 3) : 2) : 1) : 0;
/*      */   }
/*      */   
/*      */   private static int getTextureFormatFromString(String par) {
/* 2985 */     par = par.trim();
/*      */     
/* 2987 */     for (int i = 0; i < formatNames.length; i++) {
/* 2988 */       String s = formatNames[i];
/*      */       
/* 2990 */       if (par.equals(s)) {
/* 2991 */         return formatIds[i];
/*      */       }
/*      */     } 
/*      */     
/* 2995 */     return 0;
/*      */   }
/*      */   
/*      */   private static void setupNoiseTexture() {
/* 2999 */     if (noiseTexture == null && noiseTexturePath != null) {
/* 3000 */       noiseTexture = loadCustomTexture(15, noiseTexturePath);
/*      */     }
/*      */     
/* 3003 */     if (noiseTexture == null) {
/* 3004 */       noiseTexture = new HFNoiseTexture(noiseTextureResolution, noiseTextureResolution);
/*      */     }
/*      */   }
/*      */   
/*      */   private static void loadEntityDataMap() {
/* 3009 */     mapBlockToEntityData = new IdentityHashMap<>(300);
/*      */     
/* 3011 */     if (mapBlockToEntityData.isEmpty()) {
/* 3012 */       for (ResourceLocation resourcelocation : Block.blockRegistry.getKeys()) {
/* 3013 */         Block block = (Block)Block.blockRegistry.getObject(resourcelocation);
/* 3014 */         int i = Block.blockRegistry.getIDForObject(block);
/* 3015 */         mapBlockToEntityData.put(block, Integer.valueOf(i));
/*      */       } 
/*      */     }
/*      */     
/* 3019 */     BufferedReader bufferedreader = null;
/*      */     
/*      */     try {
/* 3022 */       bufferedreader = new BufferedReader(new InputStreamReader(shaderPack.getResourceAsStream("/mc_Entity_x.txt")));
/* 3023 */     } catch (Exception exception) {}
/*      */ 
/*      */ 
/*      */     
/* 3027 */     if (bufferedreader != null) {
/*      */       try {
/*      */         String s1;
/*      */         
/* 3031 */         while ((s1 = bufferedreader.readLine()) != null) {
/* 3032 */           Matcher matcher = patternLoadEntityDataMap.matcher(s1);
/*      */           
/* 3034 */           if (matcher.matches()) {
/* 3035 */             String s2 = matcher.group(1);
/* 3036 */             String s = matcher.group(2);
/* 3037 */             int j = Integer.parseInt(s);
/* 3038 */             Block block1 = Block.getBlockFromName(s2);
/*      */             
/* 3040 */             if (block1 != null) {
/* 3041 */               mapBlockToEntityData.put(block1, Integer.valueOf(j)); continue;
/*      */             } 
/* 3043 */             SMCLog.warning("Unknown block name %s", new Object[] { s2 });
/*      */             continue;
/*      */           } 
/* 3046 */           SMCLog.warning("unmatched %s\n", new Object[] { s1 });
/*      */         }
/*      */       
/* 3049 */       } catch (Exception var9) {
/* 3050 */         SMCLog.warning("Error parsing mc_Entity_x.txt");
/*      */       } 
/*      */     }
/*      */     
/* 3054 */     if (bufferedreader != null) {
/*      */       try {
/* 3056 */         bufferedreader.close();
/* 3057 */       } catch (Exception s1) {}
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private static IntBuffer fillIntBufferZero(IntBuffer buf) {
/* 3064 */     int i = buf.limit();
/*      */     
/* 3066 */     for (int j = buf.position(); j < i; j++) {
/* 3067 */       buf.put(j, 0);
/*      */     }
/*      */     
/* 3070 */     return buf;
/*      */   }
/*      */   
/*      */   public static void uninit() {
/* 3074 */     if (isShaderPackInitialized) {
/* 3075 */       checkGLError("Shaders.uninit pre");
/*      */       
/* 3077 */       for (int i = 0; i < ProgramsAll.length; i++) {
/* 3078 */         Program program = ProgramsAll[i];
/*      */         
/* 3080 */         if (program.getRef() != 0) {
/* 3081 */           ARBShaderObjects.glDeleteObjectARB(program.getRef());
/* 3082 */           checkGLError("del programRef");
/*      */         } 
/*      */         
/* 3085 */         program.setRef(0);
/* 3086 */         program.setId(0);
/* 3087 */         program.setDrawBufSettings(null);
/* 3088 */         program.setDrawBuffers(null);
/* 3089 */         program.setCompositeMipmapSetting(0);
/*      */       } 
/*      */       
/* 3092 */       hasDeferredPrograms = false;
/*      */       
/* 3094 */       if (dfb != 0) {
/* 3095 */         EXTFramebufferObject.glDeleteFramebuffersEXT(dfb);
/* 3096 */         dfb = 0;
/* 3097 */         checkGLError("del dfb");
/*      */       } 
/*      */       
/* 3100 */       if (sfb != 0) {
/* 3101 */         EXTFramebufferObject.glDeleteFramebuffersEXT(sfb);
/* 3102 */         sfb = 0;
/* 3103 */         checkGLError("del sfb");
/*      */       } 
/*      */       
/* 3106 */       if (dfbDepthTextures != null) {
/* 3107 */         GlStateManager.deleteTextures(dfbDepthTextures);
/* 3108 */         fillIntBufferZero(dfbDepthTextures);
/* 3109 */         checkGLError("del dfbDepthTextures");
/*      */       } 
/*      */       
/* 3112 */       if (dfbColorTextures != null) {
/* 3113 */         GlStateManager.deleteTextures(dfbColorTextures);
/* 3114 */         fillIntBufferZero(dfbColorTextures);
/* 3115 */         checkGLError("del dfbTextures");
/*      */       } 
/*      */       
/* 3118 */       if (sfbDepthTextures != null) {
/* 3119 */         GlStateManager.deleteTextures(sfbDepthTextures);
/* 3120 */         fillIntBufferZero(sfbDepthTextures);
/* 3121 */         checkGLError("del shadow depth");
/*      */       } 
/*      */       
/* 3124 */       if (sfbColorTextures != null) {
/* 3125 */         GlStateManager.deleteTextures(sfbColorTextures);
/* 3126 */         fillIntBufferZero(sfbColorTextures);
/* 3127 */         checkGLError("del shadow color");
/*      */       } 
/*      */       
/* 3130 */       if (dfbDrawBuffers != null) {
/* 3131 */         fillIntBufferZero(dfbDrawBuffers);
/*      */       }
/*      */       
/* 3134 */       if (noiseTexture != null) {
/* 3135 */         noiseTexture.deleteTexture();
/* 3136 */         noiseTexture = null;
/*      */       } 
/*      */       
/* 3139 */       SMCLog.info("Uninit");
/* 3140 */       shadowPassInterval = 0;
/* 3141 */       shouldSkipDefaultShadow = false;
/* 3142 */       isShaderPackInitialized = false;
/* 3143 */       checkGLError("Shaders.uninit");
/*      */     } 
/*      */   }
/*      */   
/*      */   public static void scheduleResize() {
/* 3148 */     renderDisplayHeight = 0;
/*      */   }
/*      */   
/*      */   public static void scheduleResizeShadow() {
/* 3152 */     needResizeShadow = true;
/*      */   }
/*      */   
/*      */   private static void resize() {
/* 3156 */     renderDisplayWidth = mc.displayWidth;
/* 3157 */     renderDisplayHeight = mc.displayHeight;
/* 3158 */     renderWidth = Math.round(renderDisplayWidth * configRenderResMul);
/* 3159 */     renderHeight = Math.round(renderDisplayHeight * configRenderResMul);
/* 3160 */     setupFrameBuffer();
/*      */   }
/*      */   
/*      */   private static void resizeShadow() {
/* 3164 */     needResizeShadow = false;
/* 3165 */     shadowMapWidth = Math.round(spShadowMapWidth * configShadowResMul);
/* 3166 */     shadowMapHeight = Math.round(spShadowMapHeight * configShadowResMul);
/* 3167 */     setupShadowFrameBuffer();
/*      */   }
/*      */   
/*      */   private static void setupFrameBuffer() {
/* 3171 */     if (dfb != 0) {
/* 3172 */       EXTFramebufferObject.glDeleteFramebuffersEXT(dfb);
/* 3173 */       GlStateManager.deleteTextures(dfbDepthTextures);
/* 3174 */       GlStateManager.deleteTextures(dfbColorTextures);
/*      */     } 
/*      */     
/* 3177 */     dfb = EXTFramebufferObject.glGenFramebuffersEXT();
/* 3178 */     GL11.glGenTextures((IntBuffer)dfbDepthTextures.clear().limit(usedDepthBuffers));
/* 3179 */     GL11.glGenTextures((IntBuffer)dfbColorTextures.clear().limit(16));
/* 3180 */     dfbDepthTextures.position(0);
/* 3181 */     dfbColorTextures.position(0);
/* 3182 */     EXTFramebufferObject.glBindFramebufferEXT(36160, dfb);
/* 3183 */     GL20.glDrawBuffers(0);
/* 3184 */     GL11.glReadBuffer(0);
/*      */     
/* 3186 */     for (int i = 0; i < usedDepthBuffers; i++) {
/* 3187 */       GlStateManager.bindTexture(dfbDepthTextures.get(i));
/* 3188 */       GL11.glTexParameteri(3553, 10242, 33071);
/* 3189 */       GL11.glTexParameteri(3553, 10243, 33071);
/* 3190 */       GL11.glTexParameteri(3553, 10241, 9728);
/* 3191 */       GL11.glTexParameteri(3553, 10240, 9728);
/* 3192 */       GL11.glTexParameteri(3553, 34891, 6409);
/* 3193 */       GL11.glTexImage2D(3553, 0, 6402, renderWidth, renderHeight, 0, 6402, 5126, null);
/*      */     } 
/*      */     
/* 3196 */     EXTFramebufferObject.glFramebufferTexture2DEXT(36160, 36096, 3553, dfbDepthTextures.get(0), 0);
/* 3197 */     GL20.glDrawBuffers(dfbDrawBuffers);
/* 3198 */     GL11.glReadBuffer(0);
/* 3199 */     checkGLError("FT d");
/*      */     
/* 3201 */     for (int k = 0; k < usedColorBuffers; k++) {
/* 3202 */       GlStateManager.bindTexture(dfbColorTexturesFlip.getA(k));
/* 3203 */       GL11.glTexParameteri(3553, 10242, 33071);
/* 3204 */       GL11.glTexParameteri(3553, 10243, 33071);
/* 3205 */       GL11.glTexParameteri(3553, 10241, 9729);
/* 3206 */       GL11.glTexParameteri(3553, 10240, 9729);
/* 3207 */       GL11.glTexImage2D(3553, 0, gbuffersFormat[k], renderWidth, renderHeight, 0, getPixelFormat(gbuffersFormat[k]), 33639, null);
/* 3208 */       EXTFramebufferObject.glFramebufferTexture2DEXT(36160, 36064 + k, 3553, dfbColorTexturesFlip.getA(k), 0);
/* 3209 */       checkGLError("FT c");
/*      */     } 
/*      */     
/* 3212 */     for (int l = 0; l < usedColorBuffers; l++) {
/* 3213 */       GlStateManager.bindTexture(dfbColorTexturesFlip.getB(l));
/* 3214 */       GL11.glTexParameteri(3553, 10242, 33071);
/* 3215 */       GL11.glTexParameteri(3553, 10243, 33071);
/* 3216 */       GL11.glTexParameteri(3553, 10241, 9729);
/* 3217 */       GL11.glTexParameteri(3553, 10240, 9729);
/* 3218 */       GL11.glTexImage2D(3553, 0, gbuffersFormat[l], renderWidth, renderHeight, 0, getPixelFormat(gbuffersFormat[l]), 33639, null);
/* 3219 */       checkGLError("FT ca");
/*      */     } 
/*      */     
/* 3222 */     int i1 = EXTFramebufferObject.glCheckFramebufferStatusEXT(36160);
/*      */     
/* 3224 */     if (i1 == 36058) {
/* 3225 */       printChatAndLogError("[Shaders] Error: Failed framebuffer incomplete formats");
/*      */       
/* 3227 */       for (int j = 0; j < usedColorBuffers; j++) {
/* 3228 */         GlStateManager.bindTexture(dfbColorTexturesFlip.getA(j));
/* 3229 */         GL11.glTexImage2D(3553, 0, 6408, renderWidth, renderHeight, 0, 32993, 33639, null);
/* 3230 */         EXTFramebufferObject.glFramebufferTexture2DEXT(36160, 36064 + j, 3553, dfbColorTexturesFlip.getA(j), 0);
/* 3231 */         checkGLError("FT c");
/*      */       } 
/*      */       
/* 3234 */       i1 = EXTFramebufferObject.glCheckFramebufferStatusEXT(36160);
/*      */       
/* 3236 */       if (i1 == 36053) {
/* 3237 */         SMCLog.info("complete");
/*      */       }
/*      */     } 
/*      */     
/* 3241 */     GlStateManager.bindTexture(0);
/*      */     
/* 3243 */     if (i1 != 36053) {
/* 3244 */       printChatAndLogError("[Shaders] Error: Failed creating framebuffer! (Status " + i1 + ")");
/*      */     } else {
/* 3246 */       SMCLog.info("Framebuffer created.");
/*      */     } 
/*      */   }
/*      */   
/*      */   private static int getPixelFormat(int internalFormat) {
/* 3251 */     switch (internalFormat) {
/*      */       case 33333:
/*      */       case 33334:
/*      */       case 33339:
/*      */       case 33340:
/*      */       case 36208:
/*      */       case 36209:
/*      */       case 36226:
/*      */       case 36227:
/* 3260 */         return 36251;
/*      */     } 
/*      */     
/* 3263 */     return 32993;
/*      */   }
/*      */ 
/*      */   
/*      */   private static void setupShadowFrameBuffer() {
/* 3268 */     if (usedShadowDepthBuffers != 0) {
/* 3269 */       if (sfb != 0) {
/* 3270 */         EXTFramebufferObject.glDeleteFramebuffersEXT(sfb);
/* 3271 */         GlStateManager.deleteTextures(sfbDepthTextures);
/* 3272 */         GlStateManager.deleteTextures(sfbColorTextures);
/*      */       } 
/*      */       
/* 3275 */       sfb = EXTFramebufferObject.glGenFramebuffersEXT();
/* 3276 */       EXTFramebufferObject.glBindFramebufferEXT(36160, sfb);
/* 3277 */       GL11.glDrawBuffer(0);
/* 3278 */       GL11.glReadBuffer(0);
/* 3279 */       GL11.glGenTextures((IntBuffer)sfbDepthTextures.clear().limit(usedShadowDepthBuffers));
/* 3280 */       GL11.glGenTextures((IntBuffer)sfbColorTextures.clear().limit(usedShadowColorBuffers));
/* 3281 */       sfbDepthTextures.position(0);
/* 3282 */       sfbColorTextures.position(0);
/*      */       
/* 3284 */       for (int i = 0; i < usedShadowDepthBuffers; i++) {
/* 3285 */         GlStateManager.bindTexture(sfbDepthTextures.get(i));
/* 3286 */         GL11.glTexParameterf(3553, 10242, 33071.0F);
/* 3287 */         GL11.glTexParameterf(3553, 10243, 33071.0F);
/* 3288 */         int j = shadowFilterNearest[i] ? 9728 : 9729;
/* 3289 */         GL11.glTexParameteri(3553, 10241, j);
/* 3290 */         GL11.glTexParameteri(3553, 10240, j);
/*      */         
/* 3292 */         if (shadowHardwareFilteringEnabled[i]) {
/* 3293 */           GL11.glTexParameteri(3553, 34892, 34894);
/*      */         }
/*      */         
/* 3296 */         GL11.glTexImage2D(3553, 0, 6402, shadowMapWidth, shadowMapHeight, 0, 6402, 5126, null);
/*      */       } 
/*      */       
/* 3299 */       EXTFramebufferObject.glFramebufferTexture2DEXT(36160, 36096, 3553, sfbDepthTextures.get(0), 0);
/* 3300 */       checkGLError("FT sd");
/*      */       
/* 3302 */       for (int k = 0; k < usedShadowColorBuffers; k++) {
/* 3303 */         GlStateManager.bindTexture(sfbColorTextures.get(k));
/* 3304 */         GL11.glTexParameterf(3553, 10242, 33071.0F);
/* 3305 */         GL11.glTexParameterf(3553, 10243, 33071.0F);
/* 3306 */         int i1 = shadowColorFilterNearest[k] ? 9728 : 9729;
/* 3307 */         GL11.glTexParameteri(3553, 10241, i1);
/* 3308 */         GL11.glTexParameteri(3553, 10240, i1);
/* 3309 */         GL11.glTexImage2D(3553, 0, 6408, shadowMapWidth, shadowMapHeight, 0, 32993, 33639, null);
/* 3310 */         EXTFramebufferObject.glFramebufferTexture2DEXT(36160, 36064 + k, 3553, sfbColorTextures.get(k), 0);
/* 3311 */         checkGLError("FT sc");
/*      */       } 
/*      */       
/* 3314 */       GlStateManager.bindTexture(0);
/*      */       
/* 3316 */       if (usedShadowColorBuffers > 0) {
/* 3317 */         GL20.glDrawBuffers(sfbDrawBuffers);
/*      */       }
/*      */       
/* 3320 */       int l = EXTFramebufferObject.glCheckFramebufferStatusEXT(36160);
/*      */       
/* 3322 */       if (l != 36053) {
/* 3323 */         printChatAndLogError("[Shaders] Error: Failed creating shadow framebuffer! (Status " + l + ")");
/*      */       } else {
/* 3325 */         SMCLog.info("Shadow framebuffer created.");
/*      */       } 
/*      */     } 
/*      */   }
/*      */   
/*      */   public static void beginRender(Minecraft minecraft, float partialTicks, long finishTimeNano) {
/* 3331 */     checkGLError("pre beginRender");
/* 3332 */     checkWorldChanged((World)mc.theWorld);
/* 3333 */     mc = minecraft;
/* 3334 */     mc.mcProfiler.startSection("init");
/* 3335 */     entityRenderer = mc.entityRenderer;
/*      */     
/* 3337 */     if (!isShaderPackInitialized) {
/*      */       try {
/* 3339 */         init();
/* 3340 */       } catch (IllegalStateException illegalstateexception) {
/* 3341 */         if (Config.normalize(illegalstateexception.getMessage()).equals("Function is not supported")) {
/* 3342 */           printChatAndLogError("[Shaders] Error: " + illegalstateexception.getMessage());
/* 3343 */           illegalstateexception.printStackTrace();
/* 3344 */           setShaderPack("OFF");
/*      */           
/*      */           return;
/*      */         } 
/*      */       } 
/*      */     }
/* 3350 */     if (mc.displayWidth != renderDisplayWidth || mc.displayHeight != renderDisplayHeight) {
/* 3351 */       resize();
/*      */     }
/*      */     
/* 3354 */     if (needResizeShadow) {
/* 3355 */       resizeShadow();
/*      */     }
/*      */     
/* 3358 */     worldTime = mc.theWorld.getWorldTime();
/* 3359 */     diffWorldTime = (worldTime - lastWorldTime) % 24000L;
/*      */     
/* 3361 */     if (diffWorldTime < 0L) {
/* 3362 */       diffWorldTime += 24000L;
/*      */     }
/*      */     
/* 3365 */     lastWorldTime = worldTime;
/* 3366 */     moonPhase = mc.theWorld.getMoonPhase();
/* 3367 */     frameCounter++;
/*      */     
/* 3369 */     if (frameCounter >= 720720) {
/* 3370 */       frameCounter = 0;
/*      */     }
/*      */     
/* 3373 */     systemTime = System.currentTimeMillis();
/*      */     
/* 3375 */     if (lastSystemTime == 0L) {
/* 3376 */       lastSystemTime = systemTime;
/*      */     }
/*      */     
/* 3379 */     diffSystemTime = systemTime - lastSystemTime;
/* 3380 */     lastSystemTime = systemTime;
/* 3381 */     frameTime = (float)diffSystemTime / 1000.0F;
/* 3382 */     frameTimeCounter += frameTime;
/* 3383 */     frameTimeCounter %= 3600.0F;
/* 3384 */     rainStrength = minecraft.theWorld.getRainStrength(partialTicks);
/* 3385 */     float f = (float)diffSystemTime * 0.01F;
/* 3386 */     float f1 = (float)Math.exp(Math.log(0.5D) * f / ((wetness < rainStrength) ? drynessHalfLife : wetnessHalfLife));
/* 3387 */     wetness = wetness * f1 + rainStrength * (1.0F - f1);
/* 3388 */     Entity entity = mc.getRenderViewEntity();
/*      */     
/* 3390 */     if (entity != null) {
/* 3391 */       isSleeping = (entity instanceof EntityLivingBase && ((EntityLivingBase)entity).isPlayerSleeping());
/* 3392 */       eyePosY = (float)entity.posY * partialTicks + (float)entity.lastTickPosY * (1.0F - partialTicks);
/* 3393 */       eyeBrightness = entity.getBrightnessForRender(partialTicks);
/* 3394 */       f1 = (float)diffSystemTime * 0.01F;
/* 3395 */       float f2 = (float)Math.exp(Math.log(0.5D) * f1 / eyeBrightnessHalflife);
/* 3396 */       eyeBrightnessFadeX = eyeBrightnessFadeX * f2 + (eyeBrightness & 0xFFFF) * (1.0F - f2);
/* 3397 */       eyeBrightnessFadeY = eyeBrightnessFadeY * f2 + (eyeBrightness >> 16) * (1.0F - f2);
/* 3398 */       Block block = ActiveRenderInfo.getBlockAtEntityViewpoint((World)mc.theWorld, entity, partialTicks);
/* 3399 */       Material material = block.getMaterial();
/*      */       
/* 3401 */       if (material == Material.water) {
/* 3402 */         isEyeInWater = 1;
/* 3403 */       } else if (material == Material.lava) {
/* 3404 */         isEyeInWater = 2;
/*      */       } else {
/* 3406 */         isEyeInWater = 0;
/*      */       } 
/*      */       
/* 3409 */       if (mc.thePlayer != null) {
/* 3410 */         nightVision = 0.0F;
/*      */         
/* 3412 */         if (mc.thePlayer.isPotionActive(Potion.nightVision)) {
/* 3413 */           nightVision = (Config.getMinecraft()).entityRenderer.getNightVisionBrightness((EntityLivingBase)mc.thePlayer, partialTicks);
/*      */         }
/*      */         
/* 3416 */         blindness = 0.0F;
/*      */         
/* 3418 */         if (mc.thePlayer.isPotionActive(Potion.blindness)) {
/* 3419 */           int i = mc.thePlayer.getActivePotionEffect(Potion.blindness).getDuration();
/* 3420 */           blindness = Config.limit(i / 20.0F, 0.0F, 1.0F);
/*      */         } 
/*      */       } 
/*      */       
/* 3424 */       Vec3 vec3 = mc.theWorld.getSkyColor(entity, partialTicks);
/* 3425 */       vec3 = CustomColors.getWorldSkyColor(vec3, currentWorld, entity, partialTicks);
/* 3426 */       skyColorR = (float)vec3.xCoord;
/* 3427 */       skyColorG = (float)vec3.yCoord;
/* 3428 */       skyColorB = (float)vec3.zCoord;
/*      */     } 
/*      */     
/* 3431 */     isRenderingWorld = true;
/* 3432 */     isCompositeRendered = false;
/* 3433 */     isShadowPass = false;
/* 3434 */     isHandRenderedMain = false;
/* 3435 */     isHandRenderedOff = false;
/* 3436 */     skipRenderHandMain = false;
/* 3437 */     skipRenderHandOff = false;
/* 3438 */     bindGbuffersTextures();
/* 3439 */     previousCameraPositionX = cameraPositionX;
/* 3440 */     previousCameraPositionY = cameraPositionY;
/* 3441 */     previousCameraPositionZ = cameraPositionZ;
/* 3442 */     previousProjection.position(0);
/* 3443 */     projection.position(0);
/* 3444 */     previousProjection.put(projection);
/* 3445 */     previousProjection.position(0);
/* 3446 */     projection.position(0);
/* 3447 */     previousModelView.position(0);
/* 3448 */     modelView.position(0);
/* 3449 */     previousModelView.put(modelView);
/* 3450 */     previousModelView.position(0);
/* 3451 */     modelView.position(0);
/* 3452 */     checkGLError("beginRender");
/* 3453 */     ShadersRender.renderShadowMap(entityRenderer, 0, partialTicks, finishTimeNano);
/* 3454 */     mc.mcProfiler.endSection();
/* 3455 */     EXTFramebufferObject.glBindFramebufferEXT(36160, dfb);
/*      */     
/* 3457 */     for (int j = 0; j < usedColorBuffers; j++) {
/* 3458 */       EXTFramebufferObject.glFramebufferTexture2DEXT(36160, 36064 + j, 3553, dfbColorTexturesFlip.getA(j), 0);
/*      */     }
/*      */     
/* 3461 */     checkGLError("end beginRender");
/*      */   }
/*      */   
/*      */   private static void bindGbuffersTextures() {
/* 3465 */     if (usedShadowDepthBuffers >= 1) {
/* 3466 */       GlStateManager.setActiveTexture(33988);
/* 3467 */       GlStateManager.bindTexture(sfbDepthTextures.get(0));
/*      */       
/* 3469 */       if (usedShadowDepthBuffers >= 2) {
/* 3470 */         GlStateManager.setActiveTexture(33989);
/* 3471 */         GlStateManager.bindTexture(sfbDepthTextures.get(1));
/*      */       } 
/*      */     } 
/*      */     
/* 3475 */     GlStateManager.setActiveTexture(33984);
/*      */     
/* 3477 */     for (int i = 0; i < usedColorBuffers; i++) {
/* 3478 */       GlStateManager.bindTexture(dfbColorTexturesFlip.getA(i));
/* 3479 */       GL11.glTexParameteri(3553, 10240, 9729);
/* 3480 */       GL11.glTexParameteri(3553, 10241, 9729);
/* 3481 */       GlStateManager.bindTexture(dfbColorTexturesFlip.getB(i));
/* 3482 */       GL11.glTexParameteri(3553, 10240, 9729);
/* 3483 */       GL11.glTexParameteri(3553, 10241, 9729);
/*      */     } 
/*      */     
/* 3486 */     GlStateManager.bindTexture(0);
/*      */     
/* 3488 */     for (int j = 0; j < 4 && 4 + j < usedColorBuffers; j++) {
/* 3489 */       GlStateManager.setActiveTexture(33991 + j);
/* 3490 */       GlStateManager.bindTexture(dfbColorTexturesFlip.getA(4 + j));
/*      */     } 
/*      */     
/* 3493 */     GlStateManager.setActiveTexture(33990);
/* 3494 */     GlStateManager.bindTexture(dfbDepthTextures.get(0));
/*      */     
/* 3496 */     if (usedDepthBuffers >= 2) {
/* 3497 */       GlStateManager.setActiveTexture(33995);
/* 3498 */       GlStateManager.bindTexture(dfbDepthTextures.get(1));
/*      */       
/* 3500 */       if (usedDepthBuffers >= 3) {
/* 3501 */         GlStateManager.setActiveTexture(33996);
/* 3502 */         GlStateManager.bindTexture(dfbDepthTextures.get(2));
/*      */       } 
/*      */     } 
/*      */     
/* 3506 */     for (int k = 0; k < usedShadowColorBuffers; k++) {
/* 3507 */       GlStateManager.setActiveTexture(33997 + k);
/* 3508 */       GlStateManager.bindTexture(sfbColorTextures.get(k));
/*      */     } 
/*      */     
/* 3511 */     if (noiseTextureEnabled) {
/* 3512 */       GlStateManager.setActiveTexture(33984 + noiseTexture.getTextureUnit());
/* 3513 */       GlStateManager.bindTexture(noiseTexture.getTextureId());
/*      */     } 
/*      */     
/* 3516 */     bindCustomTextures(customTexturesGbuffers);
/* 3517 */     GlStateManager.setActiveTexture(33984);
/*      */   }
/*      */   
/*      */   public static void checkWorldChanged(World world) {
/* 3521 */     if (currentWorld != world) {
/* 3522 */       World oldworld = currentWorld;
/* 3523 */       currentWorld = world;
/* 3524 */       setCameraOffset(mc.getRenderViewEntity());
/* 3525 */       int i = getDimensionId(oldworld);
/* 3526 */       int j = getDimensionId(world);
/*      */       
/* 3528 */       if (j != i) {
/* 3529 */         boolean flag = shaderPackDimensions.contains(Integer.valueOf(i));
/* 3530 */         boolean flag1 = shaderPackDimensions.contains(Integer.valueOf(j));
/*      */         
/* 3532 */         if (flag || flag1) {
/* 3533 */           uninit();
/*      */         }
/*      */       } 
/*      */       
/* 3537 */       Smoother.resetValues();
/*      */     } 
/*      */   }
/*      */   
/*      */   private static int getDimensionId(World world) {
/* 3542 */     return (world == null) ? Integer.MIN_VALUE : world.provider.getDimensionId();
/*      */   }
/*      */   
/*      */   public static void beginRenderPass(int pass, float partialTicks, long finishTimeNano) {
/* 3546 */     if (!isShadowPass) {
/* 3547 */       EXTFramebufferObject.glBindFramebufferEXT(36160, dfb);
/* 3548 */       GL11.glViewport(0, 0, renderWidth, renderHeight);
/* 3549 */       activeDrawBuffers = null;
/* 3550 */       ShadersTex.bindNSTextures(defaultTexture.getMultiTexID());
/* 3551 */       useProgram(ProgramTextured);
/* 3552 */       checkGLError("end beginRenderPass");
/*      */     } 
/*      */   }
/*      */   
/*      */   public static void setViewport(int vx, int vy, int vw, int vh) {
/* 3557 */     GlStateManager.colorMask(true, true, true, true);
/*      */     
/* 3559 */     if (isShadowPass) {
/* 3560 */       GL11.glViewport(0, 0, shadowMapWidth, shadowMapHeight);
/*      */     } else {
/* 3562 */       GL11.glViewport(0, 0, renderWidth, renderHeight);
/* 3563 */       EXTFramebufferObject.glBindFramebufferEXT(36160, dfb);
/* 3564 */       isRenderingDfb = true;
/* 3565 */       GlStateManager.enableCull();
/* 3566 */       GlStateManager.enableDepth();
/* 3567 */       setDrawBuffers(drawBuffersNone);
/* 3568 */       useProgram(ProgramTextured);
/* 3569 */       checkGLError("beginRenderPass");
/*      */     } 
/*      */   }
/*      */   
/*      */   public static void setFogMode(int value) {
/* 3574 */     fogMode = value;
/*      */     
/* 3576 */     if (fogEnabled) {
/* 3577 */       setProgramUniform1i(uniform_fogMode, value);
/*      */     }
/*      */   }
/*      */   
/*      */   public static void setFogColor(float r, float g, float b) {
/* 3582 */     fogColorR = r;
/* 3583 */     fogColorG = g;
/* 3584 */     fogColorB = b;
/* 3585 */     setProgramUniform3f(uniform_fogColor, fogColorR, fogColorG, fogColorB);
/*      */   }
/*      */   
/*      */   public static void setClearColor(float red, float green, float blue, float alpha) {
/* 3589 */     GlStateManager.clearColor(red, green, blue, alpha);
/* 3590 */     clearColorR = red;
/* 3591 */     clearColorG = green;
/* 3592 */     clearColorB = blue;
/*      */   }
/*      */   
/*      */   public static void clearRenderBuffer() {
/* 3596 */     if (isShadowPass) {
/* 3597 */       checkGLError("shadow clear pre");
/* 3598 */       EXTFramebufferObject.glFramebufferTexture2DEXT(36160, 36096, 3553, sfbDepthTextures.get(0), 0);
/* 3599 */       GL11.glClearColor(1.0F, 1.0F, 1.0F, 1.0F);
/* 3600 */       GL20.glDrawBuffers(ProgramShadow.getDrawBuffers());
/* 3601 */       checkFramebufferStatus("shadow clear");
/* 3602 */       GL11.glClear(16640);
/* 3603 */       checkGLError("shadow clear");
/*      */     } else {
/* 3605 */       checkGLError("clear pre");
/*      */       
/* 3607 */       if (gbuffersClear[0]) {
/* 3608 */         Vector4f vector4f = gbuffersClearColor[0];
/*      */         
/* 3610 */         if (vector4f != null) {
/* 3611 */           GL11.glClearColor(vector4f.getX(), vector4f.getY(), vector4f.getZ(), vector4f.getW());
/*      */         }
/*      */         
/* 3614 */         if (dfbColorTexturesFlip.isChanged(0)) {
/* 3615 */           EXTFramebufferObject.glFramebufferTexture2DEXT(36160, 36064, 3553, dfbColorTexturesFlip.getB(0), 0);
/* 3616 */           GL20.glDrawBuffers(36064);
/* 3617 */           GL11.glClear(16384);
/* 3618 */           EXTFramebufferObject.glFramebufferTexture2DEXT(36160, 36064, 3553, dfbColorTexturesFlip.getA(0), 0);
/*      */         } 
/*      */         
/* 3621 */         GL20.glDrawBuffers(36064);
/* 3622 */         GL11.glClear(16384);
/*      */       } 
/*      */       
/* 3625 */       if (gbuffersClear[1]) {
/* 3626 */         GL11.glClearColor(1.0F, 1.0F, 1.0F, 1.0F);
/* 3627 */         Vector4f vector4f2 = gbuffersClearColor[1];
/*      */         
/* 3629 */         if (vector4f2 != null) {
/* 3630 */           GL11.glClearColor(vector4f2.getX(), vector4f2.getY(), vector4f2.getZ(), vector4f2.getW());
/*      */         }
/*      */         
/* 3633 */         if (dfbColorTexturesFlip.isChanged(1)) {
/* 3634 */           EXTFramebufferObject.glFramebufferTexture2DEXT(36160, 36065, 3553, dfbColorTexturesFlip.getB(1), 0);
/* 3635 */           GL20.glDrawBuffers(36065);
/* 3636 */           GL11.glClear(16384);
/* 3637 */           EXTFramebufferObject.glFramebufferTexture2DEXT(36160, 36065, 3553, dfbColorTexturesFlip.getA(1), 0);
/*      */         } 
/*      */         
/* 3640 */         GL20.glDrawBuffers(36065);
/* 3641 */         GL11.glClear(16384);
/*      */       } 
/*      */       
/* 3644 */       for (int i = 2; i < usedColorBuffers; i++) {
/* 3645 */         if (gbuffersClear[i]) {
/* 3646 */           GL11.glClearColor(0.0F, 0.0F, 0.0F, 0.0F);
/* 3647 */           Vector4f vector4f1 = gbuffersClearColor[i];
/*      */           
/* 3649 */           if (vector4f1 != null) {
/* 3650 */             GL11.glClearColor(vector4f1.getX(), vector4f1.getY(), vector4f1.getZ(), vector4f1.getW());
/*      */           }
/*      */           
/* 3653 */           if (dfbColorTexturesFlip.isChanged(i)) {
/* 3654 */             EXTFramebufferObject.glFramebufferTexture2DEXT(36160, 36064 + i, 3553, dfbColorTexturesFlip.getB(i), 0);
/* 3655 */             GL20.glDrawBuffers(36064 + i);
/* 3656 */             GL11.glClear(16384);
/* 3657 */             EXTFramebufferObject.glFramebufferTexture2DEXT(36160, 36064 + i, 3553, dfbColorTexturesFlip.getA(i), 0);
/*      */           } 
/*      */           
/* 3660 */           GL20.glDrawBuffers(36064 + i);
/* 3661 */           GL11.glClear(16384);
/*      */         } 
/*      */       } 
/*      */       
/* 3665 */       setDrawBuffers(dfbDrawBuffers);
/* 3666 */       checkFramebufferStatus("clear");
/* 3667 */       checkGLError("clear");
/*      */     } 
/*      */   }
/*      */   
/*      */   public static void setCamera(float partialTicks) {
/* 3672 */     Entity entity = mc.getRenderViewEntity();
/* 3673 */     double d0 = entity.lastTickPosX + (entity.posX - entity.lastTickPosX) * partialTicks;
/* 3674 */     double d1 = entity.lastTickPosY + (entity.posY - entity.lastTickPosY) * partialTicks;
/* 3675 */     double d2 = entity.lastTickPosZ + (entity.posZ - entity.lastTickPosZ) * partialTicks;
/* 3676 */     updateCameraOffset(entity);
/* 3677 */     cameraPositionX = d0 - cameraOffsetX;
/* 3678 */     cameraPositionY = d1;
/* 3679 */     cameraPositionZ = d2 - cameraOffsetZ;
/* 3680 */     GL11.glGetFloat(2983, (FloatBuffer)projection.position(0));
/* 3681 */     SMath.invertMat4FBFA((FloatBuffer)projectionInverse.position(0), (FloatBuffer)projection.position(0), faProjectionInverse, faProjection);
/* 3682 */     projection.position(0);
/* 3683 */     projectionInverse.position(0);
/* 3684 */     GL11.glGetFloat(2982, (FloatBuffer)modelView.position(0));
/* 3685 */     SMath.invertMat4FBFA((FloatBuffer)modelViewInverse.position(0), (FloatBuffer)modelView.position(0), faModelViewInverse, faModelView);
/* 3686 */     modelView.position(0);
/* 3687 */     modelViewInverse.position(0);
/* 3688 */     checkGLError("setCamera");
/*      */   }
/*      */   
/*      */   private static void updateCameraOffset(Entity viewEntity) {
/* 3692 */     double d0 = Math.abs(cameraPositionX - previousCameraPositionX);
/* 3693 */     double d1 = Math.abs(cameraPositionZ - previousCameraPositionZ);
/* 3694 */     double d2 = Math.abs(cameraPositionX);
/* 3695 */     double d3 = Math.abs(cameraPositionZ);
/*      */     
/* 3697 */     if (d0 > 1000.0D || d1 > 1000.0D || d2 > 1000000.0D || d3 > 1000000.0D) {
/* 3698 */       setCameraOffset(viewEntity);
/*      */     }
/*      */   }
/*      */   
/*      */   private static void setCameraOffset(Entity viewEntity) {
/* 3703 */     if (viewEntity == null) {
/* 3704 */       cameraOffsetX = 0;
/* 3705 */       cameraOffsetZ = 0;
/*      */     } else {
/* 3707 */       cameraOffsetX = (int)viewEntity.posX / 1000 * 1000;
/* 3708 */       cameraOffsetZ = (int)viewEntity.posZ / 1000 * 1000;
/*      */     } 
/*      */   }
/*      */   
/*      */   public static void setCameraShadow(float partialTicks) {
/* 3713 */     Entity entity = mc.getRenderViewEntity();
/* 3714 */     double d0 = entity.lastTickPosX + (entity.posX - entity.lastTickPosX) * partialTicks;
/* 3715 */     double d1 = entity.lastTickPosY + (entity.posY - entity.lastTickPosY) * partialTicks;
/* 3716 */     double d2 = entity.lastTickPosZ + (entity.posZ - entity.lastTickPosZ) * partialTicks;
/* 3717 */     updateCameraOffset(entity);
/* 3718 */     cameraPositionX = d0 - cameraOffsetX;
/* 3719 */     cameraPositionY = d1;
/* 3720 */     cameraPositionZ = d2 - cameraOffsetZ;
/* 3721 */     GL11.glGetFloat(2983, (FloatBuffer)projection.position(0));
/* 3722 */     SMath.invertMat4FBFA((FloatBuffer)projectionInverse.position(0), (FloatBuffer)projection.position(0), faProjectionInverse, faProjection);
/* 3723 */     projection.position(0);
/* 3724 */     projectionInverse.position(0);
/* 3725 */     GL11.glGetFloat(2982, (FloatBuffer)modelView.position(0));
/* 3726 */     SMath.invertMat4FBFA((FloatBuffer)modelViewInverse.position(0), (FloatBuffer)modelView.position(0), faModelViewInverse, faModelView);
/* 3727 */     modelView.position(0);
/* 3728 */     modelViewInverse.position(0);
/* 3729 */     GL11.glViewport(0, 0, shadowMapWidth, shadowMapHeight);
/* 3730 */     GL11.glMatrixMode(5889);
/* 3731 */     GL11.glLoadIdentity();
/*      */     
/* 3733 */     if (shadowMapIsOrtho) {
/* 3734 */       GL11.glOrtho(-shadowMapHalfPlane, shadowMapHalfPlane, -shadowMapHalfPlane, shadowMapHalfPlane, 0.05000000074505806D, 256.0D);
/*      */     } else {
/* 3736 */       GLU.gluPerspective(shadowMapFOV, shadowMapWidth / shadowMapHeight, 0.05F, 256.0F);
/*      */     } 
/*      */     
/* 3739 */     GL11.glMatrixMode(5888);
/* 3740 */     GL11.glLoadIdentity();
/* 3741 */     GL11.glTranslatef(0.0F, 0.0F, -100.0F);
/* 3742 */     GL11.glRotatef(90.0F, 1.0F, 0.0F, 0.0F);
/* 3743 */     celestialAngle = mc.theWorld.getCelestialAngle(partialTicks);
/* 3744 */     sunAngle = (celestialAngle < 0.75F) ? (celestialAngle + 0.25F) : (celestialAngle - 0.75F);
/* 3745 */     float f = celestialAngle * -360.0F;
/* 3746 */     float f1 = (shadowAngleInterval > 0.0F) ? (f % shadowAngleInterval - shadowAngleInterval * 0.5F) : 0.0F;
/*      */     
/* 3748 */     if (sunAngle <= 0.5D) {
/* 3749 */       GL11.glRotatef(f - f1, 0.0F, 0.0F, 1.0F);
/* 3750 */       GL11.glRotatef(sunPathRotation, 1.0F, 0.0F, 0.0F);
/* 3751 */       shadowAngle = sunAngle;
/*      */     } else {
/* 3753 */       GL11.glRotatef(f + 180.0F - f1, 0.0F, 0.0F, 1.0F);
/* 3754 */       GL11.glRotatef(sunPathRotation, 1.0F, 0.0F, 0.0F);
/* 3755 */       shadowAngle = sunAngle - 0.5F;
/*      */     } 
/*      */     
/* 3758 */     if (shadowMapIsOrtho) {
/* 3759 */       float f2 = shadowIntervalSize;
/* 3760 */       float f3 = f2 / 2.0F;
/* 3761 */       GL11.glTranslatef((float)d0 % f2 - f3, (float)d1 % f2 - f3, (float)d2 % f2 - f3);
/*      */     } 
/*      */     
/* 3764 */     float f9 = sunAngle * 6.2831855F;
/* 3765 */     float f10 = (float)Math.cos(f9);
/* 3766 */     float f4 = (float)Math.sin(f9);
/* 3767 */     float f5 = sunPathRotation * 6.2831855F;
/* 3768 */     float f6 = f10;
/* 3769 */     float f7 = f4 * (float)Math.cos(f5);
/* 3770 */     float f8 = f4 * (float)Math.sin(f5);
/*      */     
/* 3772 */     if (sunAngle > 0.5D) {
/* 3773 */       f6 = -f10;
/* 3774 */       f7 = -f7;
/* 3775 */       f8 = -f8;
/*      */     } 
/*      */     
/* 3778 */     shadowLightPositionVector[0] = f6;
/* 3779 */     shadowLightPositionVector[1] = f7;
/* 3780 */     shadowLightPositionVector[2] = f8;
/* 3781 */     shadowLightPositionVector[3] = 0.0F;
/* 3782 */     GL11.glGetFloat(2983, (FloatBuffer)shadowProjection.position(0));
/* 3783 */     SMath.invertMat4FBFA((FloatBuffer)shadowProjectionInverse.position(0), (FloatBuffer)shadowProjection.position(0), faShadowProjectionInverse, faShadowProjection);
/* 3784 */     shadowProjection.position(0);
/* 3785 */     shadowProjectionInverse.position(0);
/* 3786 */     GL11.glGetFloat(2982, (FloatBuffer)shadowModelView.position(0));
/* 3787 */     SMath.invertMat4FBFA((FloatBuffer)shadowModelViewInverse.position(0), (FloatBuffer)shadowModelView.position(0), faShadowModelViewInverse, faShadowModelView);
/* 3788 */     shadowModelView.position(0);
/* 3789 */     shadowModelViewInverse.position(0);
/* 3790 */     setProgramUniformMatrix4ARB(uniform_gbufferProjection, false, projection);
/* 3791 */     setProgramUniformMatrix4ARB(uniform_gbufferProjectionInverse, false, projectionInverse);
/* 3792 */     setProgramUniformMatrix4ARB(uniform_gbufferPreviousProjection, false, previousProjection);
/* 3793 */     setProgramUniformMatrix4ARB(uniform_gbufferModelView, false, modelView);
/* 3794 */     setProgramUniformMatrix4ARB(uniform_gbufferModelViewInverse, false, modelViewInverse);
/* 3795 */     setProgramUniformMatrix4ARB(uniform_gbufferPreviousModelView, false, previousModelView);
/* 3796 */     setProgramUniformMatrix4ARB(uniform_shadowProjection, false, shadowProjection);
/* 3797 */     setProgramUniformMatrix4ARB(uniform_shadowProjectionInverse, false, shadowProjectionInverse);
/* 3798 */     setProgramUniformMatrix4ARB(uniform_shadowModelView, false, shadowModelView);
/* 3799 */     setProgramUniformMatrix4ARB(uniform_shadowModelViewInverse, false, shadowModelViewInverse);
/* 3800 */     mc.gameSettings.showDebugInfo = 1;
/* 3801 */     checkGLError("setCamera");
/*      */   }
/*      */   
/*      */   public static void preCelestialRotate() {
/* 3805 */     GL11.glRotatef(sunPathRotation * 1.0F, 0.0F, 0.0F, 1.0F);
/* 3806 */     checkGLError("preCelestialRotate");
/*      */   }
/*      */   
/*      */   public static void postCelestialRotate() {
/* 3810 */     FloatBuffer floatbuffer = tempMatrixDirectBuffer;
/* 3811 */     floatbuffer.clear();
/* 3812 */     GL11.glGetFloat(2982, floatbuffer);
/* 3813 */     floatbuffer.get(tempMat, 0, 16);
/* 3814 */     SMath.multiplyMat4xVec4(sunPosition, tempMat, sunPosModelView);
/* 3815 */     SMath.multiplyMat4xVec4(moonPosition, tempMat, moonPosModelView);
/* 3816 */     System.arraycopy((shadowAngle == sunAngle) ? sunPosition : moonPosition, 0, shadowLightPosition, 0, 3);
/* 3817 */     setProgramUniform3f(uniform_sunPosition, sunPosition[0], sunPosition[1], sunPosition[2]);
/* 3818 */     setProgramUniform3f(uniform_moonPosition, moonPosition[0], moonPosition[1], moonPosition[2]);
/* 3819 */     setProgramUniform3f(uniform_shadowLightPosition, shadowLightPosition[0], shadowLightPosition[1], shadowLightPosition[2]);
/*      */     
/* 3821 */     if (customUniforms != null) {
/* 3822 */       customUniforms.update();
/*      */     }
/*      */     
/* 3825 */     checkGLError("postCelestialRotate");
/*      */   }
/*      */   
/*      */   public static void setUpPosition() {
/* 3829 */     FloatBuffer floatbuffer = tempMatrixDirectBuffer;
/* 3830 */     floatbuffer.clear();
/* 3831 */     GL11.glGetFloat(2982, floatbuffer);
/* 3832 */     floatbuffer.get(tempMat, 0, 16);
/* 3833 */     SMath.multiplyMat4xVec4(upPosition, tempMat, upPosModelView);
/* 3834 */     setProgramUniform3f(uniform_upPosition, upPosition[0], upPosition[1], upPosition[2]);
/*      */     
/* 3836 */     if (customUniforms != null) {
/* 3837 */       customUniforms.update();
/*      */     }
/*      */   }
/*      */   
/*      */   public static void genCompositeMipmap() {
/* 3842 */     if (hasGlGenMipmap) {
/* 3843 */       for (int i = 0; i < usedColorBuffers; i++) {
/* 3844 */         if ((activeCompositeMipmapSetting & 1 << i) != 0) {
/* 3845 */           GlStateManager.setActiveTexture(33984 + colorTextureImageUnit[i]);
/* 3846 */           GL11.glTexParameteri(3553, 10241, 9987);
/* 3847 */           GL30.glGenerateMipmap(3553);
/*      */         } 
/*      */       } 
/*      */       
/* 3851 */       GlStateManager.setActiveTexture(33984);
/*      */     } 
/*      */   }
/*      */   
/*      */   public static void drawComposite() {
/* 3856 */     GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
/* 3857 */     drawCompositeQuad();
/* 3858 */     int i = activeProgram.getCountInstances();
/*      */     
/* 3860 */     if (i > 1) {
/* 3861 */       for (int j = 1; j < i; j++) {
/* 3862 */         uniform_instanceId.setValue(j);
/* 3863 */         drawCompositeQuad();
/*      */       } 
/*      */       
/* 3866 */       uniform_instanceId.setValue(0);
/*      */     } 
/*      */   }
/*      */   
/*      */   private static void drawCompositeQuad() {
/* 3871 */     if (!canRenderQuads()) {
/* 3872 */       GL11.glBegin(5);
/* 3873 */       GL11.glTexCoord2f(0.0F, 0.0F);
/* 3874 */       GL11.glVertex3f(0.0F, 0.0F, 0.0F);
/* 3875 */       GL11.glTexCoord2f(1.0F, 0.0F);
/* 3876 */       GL11.glVertex3f(1.0F, 0.0F, 0.0F);
/* 3877 */       GL11.glTexCoord2f(0.0F, 1.0F);
/* 3878 */       GL11.glVertex3f(0.0F, 1.0F, 0.0F);
/* 3879 */       GL11.glTexCoord2f(1.0F, 1.0F);
/* 3880 */       GL11.glVertex3f(1.0F, 1.0F, 0.0F);
/* 3881 */       GL11.glEnd();
/*      */     } else {
/* 3883 */       GL11.glBegin(7);
/* 3884 */       GL11.glTexCoord2f(0.0F, 0.0F);
/* 3885 */       GL11.glVertex3f(0.0F, 0.0F, 0.0F);
/* 3886 */       GL11.glTexCoord2f(1.0F, 0.0F);
/* 3887 */       GL11.glVertex3f(1.0F, 0.0F, 0.0F);
/* 3888 */       GL11.glTexCoord2f(1.0F, 1.0F);
/* 3889 */       GL11.glVertex3f(1.0F, 1.0F, 0.0F);
/* 3890 */       GL11.glTexCoord2f(0.0F, 1.0F);
/* 3891 */       GL11.glVertex3f(0.0F, 1.0F, 0.0F);
/* 3892 */       GL11.glEnd();
/*      */     } 
/*      */   }
/*      */   
/*      */   public static void renderDeferred() {
/* 3897 */     if (!isShadowPass) {
/* 3898 */       boolean flag = checkBufferFlip(ProgramDeferredPre);
/*      */       
/* 3900 */       if (hasDeferredPrograms) {
/* 3901 */         checkGLError("pre-render Deferred");
/* 3902 */         renderComposites(ProgramsDeferred, false);
/* 3903 */         flag = true;
/*      */       } 
/*      */       
/* 3906 */       if (flag) {
/* 3907 */         bindGbuffersTextures();
/*      */         
/* 3909 */         for (int i = 0; i < usedColorBuffers; i++) {
/* 3910 */           EXTFramebufferObject.glFramebufferTexture2DEXT(36160, 36064 + i, 3553, dfbColorTexturesFlip.getA(i), 0);
/*      */         }
/*      */         
/* 3913 */         if (ProgramWater.getDrawBuffers() != null) {
/* 3914 */           setDrawBuffers(ProgramWater.getDrawBuffers());
/*      */         } else {
/* 3916 */           setDrawBuffers(dfbDrawBuffers);
/*      */         } 
/*      */         
/* 3919 */         GlStateManager.setActiveTexture(33984);
/* 3920 */         mc.getTextureManager().bindTexture(TextureMap.locationBlocksTexture);
/*      */       } 
/*      */     } 
/*      */   }
/*      */   
/*      */   public static void renderCompositeFinal() {
/* 3926 */     if (!isShadowPass) {
/* 3927 */       checkBufferFlip(ProgramCompositePre);
/* 3928 */       checkGLError("pre-render CompositeFinal");
/* 3929 */       renderComposites(ProgramsComposite, true);
/*      */     } 
/*      */   }
/*      */   
/*      */   private static boolean checkBufferFlip(Program program) {
/* 3934 */     boolean flag = false;
/* 3935 */     Boolean[] aboolean = program.getBuffersFlip();
/*      */     
/* 3937 */     for (int i = 0; i < usedColorBuffers; i++) {
/* 3938 */       if (Config.isTrue(aboolean[i])) {
/* 3939 */         dfbColorTexturesFlip.flip(i);
/* 3940 */         flag = true;
/*      */       } 
/*      */     } 
/*      */     
/* 3944 */     return flag;
/*      */   }
/*      */   
/*      */   private static void renderComposites(Program[] ps, boolean renderFinal) {
/* 3948 */     if (!isShadowPass) {
/* 3949 */       GL11.glPushMatrix();
/* 3950 */       GL11.glLoadIdentity();
/* 3951 */       GL11.glMatrixMode(5889);
/* 3952 */       GL11.glPushMatrix();
/* 3953 */       GL11.glLoadIdentity();
/* 3954 */       GL11.glOrtho(0.0D, 1.0D, 0.0D, 1.0D, 0.0D, 1.0D);
/* 3955 */       GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
/* 3956 */       GlStateManager.enableTexture2D();
/* 3957 */       GlStateManager.disableAlpha();
/* 3958 */       GlStateManager.disableBlend();
/* 3959 */       GlStateManager.enableDepth();
/* 3960 */       GlStateManager.depthFunc(519);
/* 3961 */       GlStateManager.depthMask(false);
/* 3962 */       GlStateManager.disableLighting();
/*      */       
/* 3964 */       if (usedShadowDepthBuffers >= 1) {
/* 3965 */         GlStateManager.setActiveTexture(33988);
/* 3966 */         GlStateManager.bindTexture(sfbDepthTextures.get(0));
/*      */         
/* 3968 */         if (usedShadowDepthBuffers >= 2) {
/* 3969 */           GlStateManager.setActiveTexture(33989);
/* 3970 */           GlStateManager.bindTexture(sfbDepthTextures.get(1));
/*      */         } 
/*      */       } 
/*      */       
/* 3974 */       for (int i = 0; i < usedColorBuffers; i++) {
/* 3975 */         GlStateManager.setActiveTexture(33984 + colorTextureImageUnit[i]);
/* 3976 */         GlStateManager.bindTexture(dfbColorTexturesFlip.getA(i));
/*      */       } 
/*      */       
/* 3979 */       GlStateManager.setActiveTexture(33990);
/* 3980 */       GlStateManager.bindTexture(dfbDepthTextures.get(0));
/*      */       
/* 3982 */       if (usedDepthBuffers >= 2) {
/* 3983 */         GlStateManager.setActiveTexture(33995);
/* 3984 */         GlStateManager.bindTexture(dfbDepthTextures.get(1));
/*      */         
/* 3986 */         if (usedDepthBuffers >= 3) {
/* 3987 */           GlStateManager.setActiveTexture(33996);
/* 3988 */           GlStateManager.bindTexture(dfbDepthTextures.get(2));
/*      */         } 
/*      */       } 
/*      */       
/* 3992 */       for (int k = 0; k < usedShadowColorBuffers; k++) {
/* 3993 */         GlStateManager.setActiveTexture(33997 + k);
/* 3994 */         GlStateManager.bindTexture(sfbColorTextures.get(k));
/*      */       } 
/*      */       
/* 3997 */       if (noiseTextureEnabled) {
/* 3998 */         GlStateManager.setActiveTexture(33984 + noiseTexture.getTextureUnit());
/* 3999 */         GlStateManager.bindTexture(noiseTexture.getTextureId());
/*      */       } 
/*      */       
/* 4002 */       if (renderFinal) {
/* 4003 */         bindCustomTextures(customTexturesComposite);
/*      */       } else {
/* 4005 */         bindCustomTextures(customTexturesDeferred);
/*      */       } 
/*      */       
/* 4008 */       GlStateManager.setActiveTexture(33984);
/*      */       
/* 4010 */       for (int l = 0; l < usedColorBuffers; l++) {
/* 4011 */         EXTFramebufferObject.glFramebufferTexture2DEXT(36160, 36064 + l, 3553, dfbColorTexturesFlip.getB(l), 0);
/*      */       }
/*      */       
/* 4014 */       EXTFramebufferObject.glFramebufferTexture2DEXT(36160, 36096, 3553, dfbDepthTextures.get(0), 0);
/* 4015 */       GL20.glDrawBuffers(dfbDrawBuffers);
/* 4016 */       checkGLError("pre-composite");
/*      */       
/* 4018 */       for (int i1 = 0; i1 < ps.length; i1++) {
/* 4019 */         Program program = ps[i1];
/*      */         
/* 4021 */         if (program.getId() != 0) {
/* 4022 */           useProgram(program);
/* 4023 */           checkGLError(program.getName());
/*      */           
/* 4025 */           if (activeCompositeMipmapSetting != 0) {
/* 4026 */             genCompositeMipmap();
/*      */           }
/*      */           
/* 4029 */           preDrawComposite();
/* 4030 */           drawComposite();
/* 4031 */           postDrawComposite();
/*      */           
/* 4033 */           for (int j = 0; j < usedColorBuffers; j++) {
/* 4034 */             if (program.getToggleColorTextures()[j]) {
/* 4035 */               dfbColorTexturesFlip.flip(j);
/* 4036 */               GlStateManager.setActiveTexture(33984 + colorTextureImageUnit[j]);
/* 4037 */               GlStateManager.bindTexture(dfbColorTexturesFlip.getA(j));
/* 4038 */               EXTFramebufferObject.glFramebufferTexture2DEXT(36160, 36064 + j, 3553, dfbColorTexturesFlip.getB(j), 0);
/*      */             } 
/*      */           } 
/*      */           
/* 4042 */           GlStateManager.setActiveTexture(33984);
/*      */         } 
/*      */       } 
/*      */       
/* 4046 */       checkGLError("composite");
/*      */       
/* 4048 */       if (renderFinal) {
/* 4049 */         renderFinal();
/* 4050 */         isCompositeRendered = true;
/*      */       } 
/*      */       
/* 4053 */       GlStateManager.enableLighting();
/* 4054 */       GlStateManager.enableTexture2D();
/* 4055 */       GlStateManager.enableAlpha();
/* 4056 */       GlStateManager.enableBlend();
/* 4057 */       GlStateManager.depthFunc(515);
/* 4058 */       GlStateManager.depthMask(true);
/* 4059 */       GL11.glPopMatrix();
/* 4060 */       GL11.glMatrixMode(5888);
/* 4061 */       GL11.glPopMatrix();
/* 4062 */       useProgram(ProgramNone);
/*      */     } 
/*      */   }
/*      */   
/*      */   private static void preDrawComposite() {
/* 4067 */     RenderScale renderscale = activeProgram.getRenderScale();
/*      */     
/* 4069 */     if (renderscale != null) {
/* 4070 */       int i = (int)(renderWidth * renderscale.getOffsetX());
/* 4071 */       int j = (int)(renderHeight * renderscale.getOffsetY());
/* 4072 */       int k = (int)(renderWidth * renderscale.getScale());
/* 4073 */       int l = (int)(renderHeight * renderscale.getScale());
/* 4074 */       GL11.glViewport(i, j, k, l);
/*      */     } 
/*      */   }
/*      */   
/*      */   private static void postDrawComposite() {
/* 4079 */     RenderScale renderscale = activeProgram.getRenderScale();
/*      */     
/* 4081 */     if (renderscale != null) {
/* 4082 */       GL11.glViewport(0, 0, renderWidth, renderHeight);
/*      */     }
/*      */   }
/*      */   
/*      */   private static void renderFinal() {
/* 4087 */     isRenderingDfb = false;
/* 4088 */     mc.getFramebuffer().bindFramebuffer(true);
/* 4089 */     OpenGlHelper.glFramebufferTexture2D(OpenGlHelper.GL_FRAMEBUFFER, OpenGlHelper.GL_COLOR_ATTACHMENT0, 3553, (mc.getFramebuffer()).framebufferTexture, 0);
/* 4090 */     GL11.glViewport(0, 0, mc.displayWidth, mc.displayHeight);
/*      */     
/* 4092 */     if (EntityRenderer.anaglyphEnable) {
/* 4093 */       boolean flag = (EntityRenderer.anaglyphField != 0);
/* 4094 */       GlStateManager.colorMask(flag, !flag, !flag, true);
/*      */     } 
/*      */     
/* 4097 */     GlStateManager.depthMask(true);
/* 4098 */     GL11.glClearColor(clearColorR, clearColorG, clearColorB, 1.0F);
/* 4099 */     GL11.glClear(16640);
/* 4100 */     GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
/* 4101 */     GlStateManager.enableTexture2D();
/* 4102 */     GlStateManager.disableAlpha();
/* 4103 */     GlStateManager.disableBlend();
/* 4104 */     GlStateManager.enableDepth();
/* 4105 */     GlStateManager.depthFunc(519);
/* 4106 */     GlStateManager.depthMask(false);
/* 4107 */     checkGLError("pre-final");
/* 4108 */     useProgram(ProgramFinal);
/* 4109 */     checkGLError("final");
/*      */     
/* 4111 */     if (activeCompositeMipmapSetting != 0) {
/* 4112 */       genCompositeMipmap();
/*      */     }
/*      */     
/* 4115 */     drawComposite();
/* 4116 */     checkGLError("renderCompositeFinal");
/*      */   }
/*      */   
/*      */   public static void endRender() {
/* 4120 */     if (isShadowPass) {
/* 4121 */       checkGLError("shadow endRender");
/*      */     } else {
/* 4123 */       if (!isCompositeRendered) {
/* 4124 */         renderCompositeFinal();
/*      */       }
/*      */       
/* 4127 */       isRenderingWorld = false;
/* 4128 */       GlStateManager.colorMask(true, true, true, true);
/* 4129 */       useProgram(ProgramNone);
/* 4130 */       RenderHelper.disableStandardItemLighting();
/* 4131 */       checkGLError("endRender end");
/*      */     } 
/*      */   }
/*      */   
/*      */   public static void beginSky() {
/* 4136 */     isRenderingSky = true;
/* 4137 */     fogEnabled = true;
/* 4138 */     setDrawBuffers(dfbDrawBuffers);
/* 4139 */     useProgram(ProgramSkyTextured);
/* 4140 */     pushEntity(-2, 0);
/*      */   }
/*      */   
/*      */   public static void setSkyColor(Vec3 v3color) {
/* 4144 */     skyColorR = (float)v3color.xCoord;
/* 4145 */     skyColorG = (float)v3color.yCoord;
/* 4146 */     skyColorB = (float)v3color.zCoord;
/* 4147 */     setProgramUniform3f(uniform_skyColor, skyColorR, skyColorG, skyColorB);
/*      */   }
/*      */   
/*      */   public static void drawHorizon() {
/* 4151 */     WorldRenderer worldrenderer = Tessellator.getInstance().getWorldRenderer();
/* 4152 */     float f = (mc.gameSettings.renderDistanceChunks * 16);
/* 4153 */     double d0 = f * 0.9238D;
/* 4154 */     double d1 = f * 0.3826D;
/* 4155 */     double d2 = -d1;
/* 4156 */     double d3 = -d0;
/* 4157 */     double d4 = 16.0D;
/* 4158 */     double d5 = -cameraPositionY;
/* 4159 */     worldrenderer.begin(7, DefaultVertexFormats.POSITION);
/* 4160 */     worldrenderer.pos(d2, d5, d3).endVertex();
/* 4161 */     worldrenderer.pos(d2, d4, d3).endVertex();
/* 4162 */     worldrenderer.pos(d3, d4, d2).endVertex();
/* 4163 */     worldrenderer.pos(d3, d5, d2).endVertex();
/* 4164 */     worldrenderer.pos(d3, d5, d2).endVertex();
/* 4165 */     worldrenderer.pos(d3, d4, d2).endVertex();
/* 4166 */     worldrenderer.pos(d3, d4, d1).endVertex();
/* 4167 */     worldrenderer.pos(d3, d5, d1).endVertex();
/* 4168 */     worldrenderer.pos(d3, d5, d1).endVertex();
/* 4169 */     worldrenderer.pos(d3, d4, d1).endVertex();
/* 4170 */     worldrenderer.pos(d2, d4, d0).endVertex();
/* 4171 */     worldrenderer.pos(d2, d5, d0).endVertex();
/* 4172 */     worldrenderer.pos(d2, d5, d0).endVertex();
/* 4173 */     worldrenderer.pos(d2, d4, d0).endVertex();
/* 4174 */     worldrenderer.pos(d1, d4, d0).endVertex();
/* 4175 */     worldrenderer.pos(d1, d5, d0).endVertex();
/* 4176 */     worldrenderer.pos(d1, d5, d0).endVertex();
/* 4177 */     worldrenderer.pos(d1, d4, d0).endVertex();
/* 4178 */     worldrenderer.pos(d0, d4, d1).endVertex();
/* 4179 */     worldrenderer.pos(d0, d5, d1).endVertex();
/* 4180 */     worldrenderer.pos(d0, d5, d1).endVertex();
/* 4181 */     worldrenderer.pos(d0, d4, d1).endVertex();
/* 4182 */     worldrenderer.pos(d0, d4, d2).endVertex();
/* 4183 */     worldrenderer.pos(d0, d5, d2).endVertex();
/* 4184 */     worldrenderer.pos(d0, d5, d2).endVertex();
/* 4185 */     worldrenderer.pos(d0, d4, d2).endVertex();
/* 4186 */     worldrenderer.pos(d1, d4, d3).endVertex();
/* 4187 */     worldrenderer.pos(d1, d5, d3).endVertex();
/* 4188 */     worldrenderer.pos(d1, d5, d3).endVertex();
/* 4189 */     worldrenderer.pos(d1, d4, d3).endVertex();
/* 4190 */     worldrenderer.pos(d2, d4, d3).endVertex();
/* 4191 */     worldrenderer.pos(d2, d5, d3).endVertex();
/* 4192 */     worldrenderer.pos(d3, d5, d3).endVertex();
/* 4193 */     worldrenderer.pos(d3, d5, d0).endVertex();
/* 4194 */     worldrenderer.pos(d0, d5, d0).endVertex();
/* 4195 */     worldrenderer.pos(d0, d5, d3).endVertex();
/* 4196 */     Tessellator.getInstance().draw();
/*      */   }
/*      */   
/*      */   public static void preSkyList() {
/* 4200 */     setUpPosition();
/* 4201 */     GL11.glColor3f(fogColorR, fogColorG, fogColorB);
/* 4202 */     drawHorizon();
/* 4203 */     GL11.glColor3f(skyColorR, skyColorG, skyColorB);
/*      */   }
/*      */   
/*      */   public static void endSky() {
/* 4207 */     isRenderingSky = false;
/* 4208 */     setDrawBuffers(dfbDrawBuffers);
/* 4209 */     useProgram(lightmapEnabled ? ProgramTexturedLit : ProgramTextured);
/* 4210 */     popEntity();
/*      */   }
/*      */   
/*      */   public static void beginUpdateChunks() {
/* 4214 */     checkGLError("beginUpdateChunks1");
/* 4215 */     checkFramebufferStatus("beginUpdateChunks1");
/*      */     
/* 4217 */     if (!isShadowPass) {
/* 4218 */       useProgram(ProgramTerrain);
/*      */     }
/*      */     
/* 4221 */     checkGLError("beginUpdateChunks2");
/* 4222 */     checkFramebufferStatus("beginUpdateChunks2");
/*      */   }
/*      */   
/*      */   public static void endUpdateChunks() {
/* 4226 */     checkGLError("endUpdateChunks1");
/* 4227 */     checkFramebufferStatus("endUpdateChunks1");
/*      */     
/* 4229 */     if (!isShadowPass) {
/* 4230 */       useProgram(ProgramTerrain);
/*      */     }
/*      */     
/* 4233 */     checkGLError("endUpdateChunks2");
/* 4234 */     checkFramebufferStatus("endUpdateChunks2");
/*      */   }
/*      */   
/*      */   public static boolean shouldRenderClouds(GameSettings gs) {
/* 4238 */     if (!shaderPackLoaded) {
/* 4239 */       return true;
/*      */     }
/* 4241 */     checkGLError("shouldRenderClouds");
/* 4242 */     return isShadowPass ? configCloudShadow : ((gs.clouds > 0));
/*      */   }
/*      */ 
/*      */   
/*      */   public static void beginClouds() {
/* 4247 */     fogEnabled = true;
/* 4248 */     pushEntity(-3, 0);
/* 4249 */     useProgram(ProgramClouds);
/*      */   }
/*      */   
/*      */   public static void endClouds() {
/* 4253 */     disableFog();
/* 4254 */     popEntity();
/* 4255 */     useProgram(lightmapEnabled ? ProgramTexturedLit : ProgramTextured);
/*      */   }
/*      */   
/*      */   public static void beginEntities() {
/* 4259 */     if (isRenderingWorld) {
/* 4260 */       useProgram(ProgramEntities);
/*      */     }
/*      */   }
/*      */   
/*      */   public static void nextEntity(Entity entity) {
/* 4265 */     if (isRenderingWorld) {
/* 4266 */       useProgram(ProgramEntities);
/* 4267 */       setEntityId(entity);
/*      */     } 
/*      */   }
/*      */   
/*      */   public static void setEntityId(Entity entity) {
/* 4272 */     if (uniform_entityId.isDefined()) {
/* 4273 */       int i = EntityUtils.getEntityIdByClass(entity);
/* 4274 */       int j = EntityAliases.getEntityAliasId(i);
/*      */       
/* 4276 */       if (j >= 0) {
/* 4277 */         i = j;
/*      */       }
/*      */       
/* 4280 */       uniform_entityId.setValue(i);
/*      */     } 
/*      */   }
/*      */   
/*      */   public static void beginSpiderEyes() {
/* 4285 */     if (isRenderingWorld && ProgramSpiderEyes.getId() != ProgramNone.getId()) {
/* 4286 */       useProgram(ProgramSpiderEyes);
/* 4287 */       GlStateManager.enableAlpha();
/* 4288 */       GlStateManager.alphaFunc(516, 0.0F);
/* 4289 */       GlStateManager.blendFunc(770, 771);
/*      */     } 
/*      */   }
/*      */   
/*      */   public static void endSpiderEyes() {
/* 4294 */     if (isRenderingWorld && ProgramSpiderEyes.getId() != ProgramNone.getId()) {
/* 4295 */       useProgram(ProgramEntities);
/* 4296 */       GlStateManager.disableAlpha();
/*      */     } 
/*      */   }
/*      */   
/*      */   public static void endEntities() {
/* 4301 */     if (isRenderingWorld) {
/* 4302 */       setEntityId(null);
/* 4303 */       useProgram(lightmapEnabled ? ProgramTexturedLit : ProgramTextured);
/*      */     } 
/*      */   }
/*      */   
/*      */   public static void beginEntitiesGlowing() {
/* 4308 */     if (isRenderingWorld) {
/* 4309 */       isEntitiesGlowing = true;
/*      */     }
/*      */   }
/*      */   
/*      */   public static void endEntitiesGlowing() {
/* 4314 */     if (isRenderingWorld) {
/* 4315 */       isEntitiesGlowing = false;
/*      */     }
/*      */   }
/*      */   
/*      */   public static void setEntityColor(float r, float g, float b, float a) {
/* 4320 */     if (isRenderingWorld && !isShadowPass) {
/* 4321 */       uniform_entityColor.setValue(r, g, b, a);
/*      */     }
/*      */   }
/*      */   
/*      */   public static void beginLivingDamage() {
/* 4326 */     if (isRenderingWorld) {
/* 4327 */       ShadersTex.bindTexture(defaultTexture);
/*      */       
/* 4329 */       if (!isShadowPass) {
/* 4330 */         setDrawBuffers(drawBuffersColorAtt0);
/*      */       }
/*      */     } 
/*      */   }
/*      */   
/*      */   public static void endLivingDamage() {
/* 4336 */     if (isRenderingWorld && !isShadowPass) {
/* 4337 */       setDrawBuffers(ProgramEntities.getDrawBuffers());
/*      */     }
/*      */   }
/*      */   
/*      */   public static void beginBlockEntities() {
/* 4342 */     if (isRenderingWorld) {
/* 4343 */       checkGLError("beginBlockEntities");
/* 4344 */       useProgram(ProgramBlock);
/*      */     } 
/*      */   }
/*      */   
/*      */   public static void nextBlockEntity(TileEntity tileEntity) {
/* 4349 */     if (isRenderingWorld) {
/* 4350 */       checkGLError("nextBlockEntity");
/* 4351 */       useProgram(ProgramBlock);
/* 4352 */       setBlockEntityId(tileEntity);
/*      */     } 
/*      */   }
/*      */   
/*      */   public static void setBlockEntityId(TileEntity tileEntity) {
/* 4357 */     if (uniform_blockEntityId.isDefined()) {
/* 4358 */       int i = getBlockEntityId(tileEntity);
/* 4359 */       uniform_blockEntityId.setValue(i);
/*      */     } 
/*      */   }
/*      */   
/*      */   private static int getBlockEntityId(TileEntity tileEntity) {
/* 4364 */     if (tileEntity == null) {
/* 4365 */       return -1;
/*      */     }
/* 4367 */     Block block = tileEntity.getBlockType();
/*      */     
/* 4369 */     if (block == null) {
/* 4370 */       return 0;
/*      */     }
/* 4372 */     int i = Block.getIdFromBlock(block);
/* 4373 */     int j = tileEntity.getBlockMetadata();
/* 4374 */     int k = BlockAliases.getBlockAliasId(i, j);
/*      */     
/* 4376 */     if (k >= 0) {
/* 4377 */       i = k;
/*      */     }
/*      */     
/* 4380 */     return i;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public static void endBlockEntities() {
/* 4386 */     if (isRenderingWorld) {
/* 4387 */       checkGLError("endBlockEntities");
/* 4388 */       setBlockEntityId(null);
/* 4389 */       useProgram(lightmapEnabled ? ProgramTexturedLit : ProgramTextured);
/* 4390 */       ShadersTex.bindNSTextures(defaultTexture.getMultiTexID());
/*      */     } 
/*      */   }
/*      */   
/*      */   public static void beginLitParticles() {
/* 4395 */     useProgram(ProgramTexturedLit);
/*      */   }
/*      */   
/*      */   public static void beginParticles() {
/* 4399 */     useProgram(ProgramTextured);
/*      */   }
/*      */   
/*      */   public static void endParticles() {
/* 4403 */     useProgram(ProgramTexturedLit);
/*      */   }
/*      */   
/*      */   public static void readCenterDepth() {
/* 4407 */     if (!isShadowPass && centerDepthSmoothEnabled) {
/* 4408 */       tempDirectFloatBuffer.clear();
/* 4409 */       GL11.glReadPixels(renderWidth / 2, renderHeight / 2, 1, 1, 6402, 5126, tempDirectFloatBuffer);
/* 4410 */       centerDepth = tempDirectFloatBuffer.get(0);
/* 4411 */       float f = (float)diffSystemTime * 0.01F;
/* 4412 */       float f1 = (float)Math.exp(Math.log(0.5D) * f / centerDepthSmoothHalflife);
/* 4413 */       centerDepthSmooth = centerDepthSmooth * f1 + centerDepth * (1.0F - f1);
/*      */     } 
/*      */   }
/*      */   
/*      */   public static void beginWeather() {
/* 4418 */     if (!isShadowPass) {
/* 4419 */       if (usedDepthBuffers >= 3) {
/* 4420 */         GlStateManager.setActiveTexture(33996);
/* 4421 */         GL11.glCopyTexSubImage2D(3553, 0, 0, 0, 0, 0, renderWidth, renderHeight);
/* 4422 */         GlStateManager.setActiveTexture(33984);
/*      */       } 
/*      */       
/* 4425 */       GlStateManager.enableDepth();
/* 4426 */       GlStateManager.enableBlend();
/* 4427 */       GlStateManager.blendFunc(770, 771);
/* 4428 */       GlStateManager.enableAlpha();
/* 4429 */       useProgram(ProgramWeather);
/*      */     } 
/*      */   }
/*      */   
/*      */   public static void endWeather() {
/* 4434 */     GlStateManager.disableBlend();
/* 4435 */     useProgram(ProgramTexturedLit);
/*      */   }
/*      */   
/*      */   public static void preWater() {
/* 4439 */     if (usedDepthBuffers >= 2) {
/* 4440 */       GlStateManager.setActiveTexture(33995);
/* 4441 */       checkGLError("pre copy depth");
/* 4442 */       GL11.glCopyTexSubImage2D(3553, 0, 0, 0, 0, 0, renderWidth, renderHeight);
/* 4443 */       checkGLError("copy depth");
/* 4444 */       GlStateManager.setActiveTexture(33984);
/*      */     } 
/*      */     
/* 4447 */     ShadersTex.bindNSTextures(defaultTexture.getMultiTexID());
/*      */   }
/*      */   
/*      */   public static void beginWater() {
/* 4451 */     if (isRenderingWorld) {
/* 4452 */       if (!isShadowPass) {
/* 4453 */         renderDeferred();
/* 4454 */         useProgram(ProgramWater);
/* 4455 */         GlStateManager.enableBlend();
/* 4456 */         GlStateManager.depthMask(true);
/*      */       } else {
/* 4458 */         GlStateManager.depthMask(true);
/*      */       } 
/*      */     }
/*      */   }
/*      */   
/*      */   public static void endWater() {
/* 4464 */     if (isRenderingWorld) {
/* 4465 */       if (isShadowPass);
/*      */ 
/*      */ 
/*      */       
/* 4469 */       useProgram(lightmapEnabled ? ProgramTexturedLit : ProgramTextured);
/*      */     } 
/*      */   }
/*      */   
/*      */   public static void applyHandDepth() {
/* 4474 */     if (configHandDepthMul != 1.0D) {
/* 4475 */       GL11.glScaled(1.0D, 1.0D, configHandDepthMul);
/*      */     }
/*      */   }
/*      */   
/*      */   public static void beginHand(boolean translucent) {
/* 4480 */     GL11.glMatrixMode(5888);
/* 4481 */     GL11.glPushMatrix();
/* 4482 */     GL11.glMatrixMode(5889);
/* 4483 */     GL11.glPushMatrix();
/* 4484 */     GL11.glMatrixMode(5888);
/*      */     
/* 4486 */     if (translucent) {
/* 4487 */       useProgram(ProgramHandWater);
/*      */     } else {
/* 4489 */       useProgram(ProgramHand);
/*      */     } 
/*      */     
/* 4492 */     checkGLError("beginHand");
/* 4493 */     checkFramebufferStatus("beginHand");
/*      */   }
/*      */   
/*      */   public static void endHand() {
/* 4497 */     checkGLError("pre endHand");
/* 4498 */     checkFramebufferStatus("pre endHand");
/* 4499 */     GL11.glMatrixMode(5889);
/* 4500 */     GL11.glPopMatrix();
/* 4501 */     GL11.glMatrixMode(5888);
/* 4502 */     GL11.glPopMatrix();
/* 4503 */     GlStateManager.blendFunc(770, 771);
/* 4504 */     checkGLError("endHand");
/*      */   }
/*      */   
/*      */   public static void beginFPOverlay() {
/* 4508 */     GlStateManager.disableLighting();
/* 4509 */     GlStateManager.disableBlend();
/*      */   }
/*      */ 
/*      */   
/*      */   public static void endFPOverlay() {}
/*      */   
/*      */   public static void glEnableWrapper(int cap) {
/* 4516 */     GL11.glEnable(cap);
/*      */     
/* 4518 */     if (cap == 3553) {
/* 4519 */       enableTexture2D();
/* 4520 */     } else if (cap == 2912) {
/* 4521 */       enableFog();
/*      */     } 
/*      */   }
/*      */   
/*      */   public static void glDisableWrapper(int cap) {
/* 4526 */     GL11.glDisable(cap);
/*      */     
/* 4528 */     if (cap == 3553) {
/* 4529 */       disableTexture2D();
/* 4530 */     } else if (cap == 2912) {
/* 4531 */       disableFog();
/*      */     } 
/*      */   }
/*      */   
/*      */   public static void sglEnableT2D(int cap) {
/* 4536 */     GL11.glEnable(cap);
/* 4537 */     enableTexture2D();
/*      */   }
/*      */   
/*      */   public static void sglDisableT2D(int cap) {
/* 4541 */     GL11.glDisable(cap);
/* 4542 */     disableTexture2D();
/*      */   }
/*      */   
/*      */   public static void sglEnableFog(int cap) {
/* 4546 */     GL11.glEnable(cap);
/* 4547 */     enableFog();
/*      */   }
/*      */   
/*      */   public static void sglDisableFog(int cap) {
/* 4551 */     GL11.glDisable(cap);
/* 4552 */     disableFog();
/*      */   }
/*      */   
/*      */   public static void enableTexture2D() {
/* 4556 */     if (isRenderingSky) {
/* 4557 */       useProgram(ProgramSkyTextured);
/* 4558 */     } else if (activeProgram == ProgramBasic) {
/* 4559 */       useProgram(lightmapEnabled ? ProgramTexturedLit : ProgramTextured);
/*      */     } 
/*      */   }
/*      */   
/*      */   public static void disableTexture2D() {
/* 4564 */     if (isRenderingSky) {
/* 4565 */       useProgram(ProgramSkyBasic);
/* 4566 */     } else if (activeProgram == ProgramTextured || activeProgram == ProgramTexturedLit) {
/* 4567 */       useProgram(ProgramBasic);
/*      */     } 
/*      */   }
/*      */   
/*      */   public static void pushProgram() {
/* 4572 */     programStack.push(activeProgram);
/*      */   }
/*      */   
/*      */   public static void popProgram() {
/* 4576 */     Program program = programStack.pop();
/* 4577 */     useProgram(program);
/*      */   }
/*      */   
/*      */   public static void beginLeash() {
/* 4581 */     pushProgram();
/* 4582 */     useProgram(ProgramBasic);
/*      */   }
/*      */   
/*      */   public static void endLeash() {
/* 4586 */     popProgram();
/*      */   }
/*      */   
/*      */   public static void enableFog() {
/* 4590 */     fogEnabled = true;
/* 4591 */     setProgramUniform1i(uniform_fogMode, fogMode);
/* 4592 */     setProgramUniform1f(uniform_fogDensity, fogDensity);
/*      */   }
/*      */   
/*      */   public static void disableFog() {
/* 4596 */     fogEnabled = false;
/* 4597 */     setProgramUniform1i(uniform_fogMode, 0);
/*      */   }
/*      */   
/*      */   public static void setFogDensity(float value) {
/* 4601 */     fogDensity = value;
/*      */     
/* 4603 */     if (fogEnabled) {
/* 4604 */       setProgramUniform1f(uniform_fogDensity, value);
/*      */     }
/*      */   }
/*      */   
/*      */   public static void sglFogi(int pname, int param) {
/* 4609 */     GL11.glFogi(pname, param);
/*      */     
/* 4611 */     if (pname == 2917) {
/* 4612 */       fogMode = param;
/*      */       
/* 4614 */       if (fogEnabled) {
/* 4615 */         setProgramUniform1i(uniform_fogMode, fogMode);
/*      */       }
/*      */     } 
/*      */   }
/*      */   
/*      */   public static void enableLightmap() {
/* 4621 */     lightmapEnabled = true;
/*      */     
/* 4623 */     if (activeProgram == ProgramTextured) {
/* 4624 */       useProgram(ProgramTexturedLit);
/*      */     }
/*      */   }
/*      */   
/*      */   public static void disableLightmap() {
/* 4629 */     lightmapEnabled = false;
/*      */     
/* 4631 */     if (activeProgram == ProgramTexturedLit) {
/* 4632 */       useProgram(ProgramTextured);
/*      */     }
/*      */   }
/*      */   
/*      */   public static int getEntityData() {
/* 4637 */     return entityData[entityDataIndex * 2];
/*      */   }
/*      */   
/*      */   public static int getEntityData2() {
/* 4641 */     return entityData[entityDataIndex * 2 + 1];
/*      */   }
/*      */   
/*      */   public static int setEntityData1(int data1) {
/* 4645 */     entityData[entityDataIndex * 2] = entityData[entityDataIndex * 2] & 0xFFFF | data1 << 16;
/* 4646 */     return data1;
/*      */   }
/*      */   
/*      */   public static int setEntityData2(int data2) {
/* 4650 */     entityData[entityDataIndex * 2 + 1] = entityData[entityDataIndex * 2 + 1] & 0xFFFF0000 | data2 & 0xFFFF;
/* 4651 */     return data2;
/*      */   }
/*      */   
/*      */   public static void pushEntity(int data0, int data1) {
/* 4655 */     entityDataIndex++;
/* 4656 */     entityData[entityDataIndex * 2] = data0 & 0xFFFF | data1 << 16;
/* 4657 */     entityData[entityDataIndex * 2 + 1] = 0;
/*      */   }
/*      */   
/*      */   public static void pushEntity(int data0) {
/* 4661 */     entityDataIndex++;
/* 4662 */     entityData[entityDataIndex * 2] = data0 & 0xFFFF;
/* 4663 */     entityData[entityDataIndex * 2 + 1] = 0;
/*      */   }
/*      */   
/*      */   public static void pushEntity(Block block) {
/* 4667 */     entityDataIndex++;
/* 4668 */     int i = block.getRenderType();
/* 4669 */     entityData[entityDataIndex * 2] = Block.blockRegistry.getIDForObject(block) & 0xFFFF | i << 16;
/* 4670 */     entityData[entityDataIndex * 2 + 1] = 0;
/*      */   }
/*      */   
/*      */   public static void popEntity() {
/* 4674 */     entityData[entityDataIndex * 2] = 0;
/* 4675 */     entityData[entityDataIndex * 2 + 1] = 0;
/* 4676 */     entityDataIndex--;
/*      */   }
/*      */   
/*      */   public static void mcProfilerEndSection() {
/* 4680 */     mc.mcProfiler.endSection();
/*      */   }
/*      */   
/*      */   public static String getShaderPackName() {
/* 4684 */     return (shaderPack == null) ? null : ((shaderPack instanceof ShaderPackNone) ? null : shaderPack.getName());
/*      */   }
/*      */   
/*      */   public static InputStream getShaderPackResourceStream(String path) {
/* 4688 */     return (shaderPack == null) ? null : shaderPack.getResourceAsStream(path);
/*      */   }
/*      */   
/*      */   public static void nextAntialiasingLevel(boolean forward) {
/* 4692 */     if (forward) {
/* 4693 */       configAntialiasingLevel += 2;
/*      */       
/* 4695 */       if (configAntialiasingLevel > 4) {
/* 4696 */         configAntialiasingLevel = 0;
/*      */       }
/*      */     } else {
/* 4699 */       configAntialiasingLevel -= 2;
/*      */       
/* 4701 */       if (configAntialiasingLevel < 0) {
/* 4702 */         configAntialiasingLevel = 4;
/*      */       }
/*      */     } 
/*      */     
/* 4706 */     configAntialiasingLevel = configAntialiasingLevel / 2 * 2;
/* 4707 */     configAntialiasingLevel = Config.limit(configAntialiasingLevel, 0, 4);
/*      */   }
/*      */   
/*      */   public static void checkShadersModInstalled() {
/*      */     try {
/* 4712 */       Class<?> clazz = Class.forName("shadersmod.transform.SMCClassTransformer");
/* 4713 */     } catch (Throwable var1) {
/*      */       return;
/*      */     } 
/*      */     
/* 4717 */     throw new RuntimeException("Shaders Mod detected. Please remove it, OptiFine has built-in support for shaders.");
/*      */   }
/*      */   
/*      */   public static void resourcesReloaded() {
/* 4721 */     loadShaderPackResources();
/*      */     
/* 4723 */     if (shaderPackLoaded) {
/* 4724 */       BlockAliases.resourcesReloaded();
/* 4725 */       ItemAliases.resourcesReloaded();
/* 4726 */       EntityAliases.resourcesReloaded();
/*      */     } 
/*      */   }
/*      */   
/*      */   private static void loadShaderPackResources() {
/* 4731 */     shaderPackResources = new HashMap<>();
/*      */     
/* 4733 */     if (shaderPackLoaded) {
/* 4734 */       List<String> list = new ArrayList<>();
/* 4735 */       String s = "/shaders/lang/";
/* 4736 */       String s1 = "en_US";
/* 4737 */       String s2 = ".lang";
/* 4738 */       list.add(String.valueOf(s) + s1 + s2);
/*      */       
/* 4740 */       if (!(Config.getGameSettings()).forceUnicodeFont.equals(s1)) {
/* 4741 */         list.add(String.valueOf(s) + (Config.getGameSettings()).forceUnicodeFont + s2);
/*      */       }
/*      */       
/*      */       try {
/* 4745 */         for (String s3 : list) {
/* 4746 */           InputStream inputstream = shaderPack.getResourceAsStream(s3);
/*      */           
/* 4748 */           if (inputstream != null) {
/* 4749 */             PropertiesOrdered propertiesOrdered = new PropertiesOrdered();
/* 4750 */             Lang.loadLocaleData(inputstream, (Map)propertiesOrdered);
/* 4751 */             inputstream.close();
/*      */             
/* 4753 */             for (Object o : propertiesOrdered.keySet()) {
/* 4754 */               String s4 = (String)o;
/* 4755 */               String s5 = propertiesOrdered.getProperty(s4);
/* 4756 */               shaderPackResources.put(s4, s5);
/*      */             } 
/*      */           } 
/*      */         } 
/* 4760 */       } catch (IOException ioexception) {
/* 4761 */         ioexception.printStackTrace();
/*      */       } 
/*      */     } 
/*      */   }
/*      */   
/*      */   public static String translate(String key, String def) {
/* 4767 */     String s = shaderPackResources.get(key);
/* 4768 */     return (s == null) ? def : s;
/*      */   }
/*      */   
/*      */   public static boolean isProgramPath(String path) {
/* 4772 */     if (path == null)
/* 4773 */       return false; 
/* 4774 */     if (path.length() <= 0) {
/* 4775 */       return false;
/*      */     }
/* 4777 */     int i = path.lastIndexOf("/");
/*      */     
/* 4779 */     if (i >= 0) {
/* 4780 */       path = path.substring(i + 1);
/*      */     }
/*      */     
/* 4783 */     Program program = getProgram(path);
/* 4784 */     return (program != null);
/*      */   }
/*      */ 
/*      */   
/*      */   public static Program getProgram(String name) {
/* 4789 */     return programs.getProgram(name);
/*      */   }
/*      */   
/*      */   public static void setItemToRenderMain(ItemStack itemToRenderMain) {
/* 4793 */     itemToRenderMainTranslucent = isTranslucentBlock(itemToRenderMain);
/*      */   }
/*      */   
/*      */   public static void setItemToRenderOff(ItemStack itemToRenderOff) {
/* 4797 */     itemToRenderOffTranslucent = isTranslucentBlock(itemToRenderOff);
/*      */   }
/*      */   
/*      */   public static boolean isItemToRenderMainTranslucent() {
/* 4801 */     return itemToRenderMainTranslucent;
/*      */   }
/*      */   
/*      */   public static boolean isItemToRenderOffTranslucent() {
/* 4805 */     return itemToRenderOffTranslucent;
/*      */   }
/*      */   
/*      */   public static boolean isBothHandsRendered() {
/* 4809 */     return (isHandRenderedMain && isHandRenderedOff);
/*      */   }
/*      */   
/*      */   private static boolean isTranslucentBlock(ItemStack stack) {
/* 4813 */     if (stack == null) {
/* 4814 */       return false;
/*      */     }
/* 4816 */     Item item = stack.getItem();
/*      */     
/* 4818 */     if (item == null)
/* 4819 */       return false; 
/* 4820 */     if (!(item instanceof ItemBlock)) {
/* 4821 */       return false;
/*      */     }
/* 4823 */     ItemBlock itemblock = (ItemBlock)item;
/* 4824 */     Block block = itemblock.getBlock();
/*      */     
/* 4826 */     if (block == null) {
/* 4827 */       return false;
/*      */     }
/* 4829 */     EnumWorldBlockLayer enumworldblocklayer = block.getBlockLayer();
/* 4830 */     return (enumworldblocklayer == EnumWorldBlockLayer.TRANSLUCENT);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static boolean isSkipRenderHand() {
/* 4837 */     return skipRenderHandMain;
/*      */   }
/*      */   
/*      */   public static boolean isRenderBothHands() {
/* 4841 */     return (!skipRenderHandMain && !skipRenderHandOff);
/*      */   }
/*      */   
/*      */   public static void setSkipRenderHands(boolean skipMain, boolean skipOff) {
/* 4845 */     skipRenderHandMain = skipMain;
/* 4846 */     skipRenderHandOff = skipOff;
/*      */   }
/*      */   
/*      */   public static void setHandsRendered(boolean handMain, boolean handOff) {
/* 4850 */     isHandRenderedMain = handMain;
/* 4851 */     isHandRenderedOff = handOff;
/*      */   }
/*      */   
/*      */   public static boolean isHandRenderedMain() {
/* 4855 */     return isHandRenderedMain;
/*      */   }
/*      */   
/*      */   public static boolean isHandRenderedOff() {
/* 4859 */     return isHandRenderedOff;
/*      */   }
/*      */   
/*      */   public static float getShadowRenderDistance() {
/* 4863 */     return (shadowDistanceRenderMul < 0.0F) ? -1.0F : (shadowMapHalfPlane * shadowDistanceRenderMul);
/*      */   }
/*      */   
/*      */   public static void setRenderingFirstPersonHand(boolean flag) {
/* 4867 */     isRenderingFirstPersonHand = flag;
/*      */   }
/*      */   
/*      */   public static boolean isRenderingFirstPersonHand() {
/* 4871 */     return isRenderingFirstPersonHand;
/*      */   }
/*      */   
/*      */   public static void beginBeacon() {
/* 4875 */     if (isRenderingWorld) {
/* 4876 */       useProgram(ProgramBeaconBeam);
/*      */     }
/*      */   }
/*      */   
/*      */   public static void endBeacon() {
/* 4881 */     if (isRenderingWorld) {
/* 4882 */       useProgram(ProgramBlock);
/*      */     }
/*      */   }
/*      */   
/*      */   public static World getCurrentWorld() {
/* 4887 */     return currentWorld;
/*      */   }
/*      */   
/*      */   public static BlockPos getCameraPosition() {
/* 4891 */     return new BlockPos(cameraPositionX, cameraPositionY, cameraPositionZ);
/*      */   }
/*      */   
/*      */   public static boolean isCustomUniforms() {
/* 4895 */     return (customUniforms != null);
/*      */   }
/*      */   
/*      */   public static boolean canRenderQuads() {
/* 4899 */     return hasGeometryShaders ? capabilities.GL_NV_geometry_shader4 : true;
/*      */   }
/*      */   
/*      */   static {
/* 4903 */     shaderPacksDir = new File((Minecraft.getMinecraft()).mcDataDir, "shaderpacks");
/* 4904 */     configFile = new File((Minecraft.getMinecraft()).mcDataDir, "optionsshaders.txt");
/*      */   }
/*      */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\optifine\shaders\Shaders.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */