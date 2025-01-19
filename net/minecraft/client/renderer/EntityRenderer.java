/*      */ package net.minecraft.client.renderer;
/*      */ 
/*      */ import client.Client;
/*      */ import com.google.common.base.Predicate;
/*      */ import com.google.common.base.Predicates;
/*      */ import com.google.gson.JsonSyntaxException;
/*      */ import java.io.IOException;
/*      */ import java.nio.FloatBuffer;
/*      */ import java.util.Calendar;
/*      */ import java.util.Date;
/*      */ import java.util.List;
/*      */ import java.util.Random;
/*      */ import java.util.concurrent.Callable;
/*      */ import net.minecraft.block.Block;
/*      */ import net.minecraft.block.BlockBed;
/*      */ import net.minecraft.block.material.Material;
/*      */ import net.minecraft.block.properties.IProperty;
/*      */ import net.minecraft.block.state.IBlockState;
/*      */ import net.minecraft.client.Minecraft;
/*      */ import net.minecraft.client.entity.AbstractClientPlayer;
/*      */ import net.minecraft.client.gui.GuiChat;
/*      */ import net.minecraft.client.gui.GuiMainMenu;
/*      */ import net.minecraft.client.gui.GuiScreen;
/*      */ import net.minecraft.client.gui.MapItemRenderer;
/*      */ import net.minecraft.client.gui.ScaledResolution;
/*      */ import net.minecraft.client.multiplayer.WorldClient;
/*      */ import net.minecraft.client.particle.EffectRenderer;
/*      */ import net.minecraft.client.renderer.chunk.RenderChunk;
/*      */ import net.minecraft.client.renderer.culling.ClippingHelper;
/*      */ import net.minecraft.client.renderer.culling.ClippingHelperImpl;
/*      */ import net.minecraft.client.renderer.culling.Frustum;
/*      */ import net.minecraft.client.renderer.culling.ICamera;
/*      */ import net.minecraft.client.renderer.texture.DynamicTexture;
/*      */ import net.minecraft.client.renderer.texture.TextureMap;
/*      */ import net.minecraft.client.renderer.tileentity.TileEntityRendererDispatcher;
/*      */ import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
/*      */ import net.minecraft.client.resources.I18n;
/*      */ import net.minecraft.client.resources.IResourceManager;
/*      */ import net.minecraft.client.resources.IResourceManagerReloadListener;
/*      */ import net.minecraft.client.settings.GameSettings;
/*      */ import net.minecraft.client.shader.ShaderGroup;
/*      */ import net.minecraft.client.shader.ShaderLinkHelper;
/*      */ import net.minecraft.crash.CrashReport;
/*      */ import net.minecraft.crash.CrashReportCategory;
/*      */ import net.minecraft.enchantment.EnchantmentHelper;
/*      */ import net.minecraft.entity.Entity;
/*      */ import net.minecraft.entity.EntityLivingBase;
/*      */ import net.minecraft.entity.boss.BossStatus;
/*      */ import net.minecraft.entity.passive.EntityAnimal;
/*      */ import net.minecraft.entity.player.EntityPlayer;
/*      */ import net.minecraft.event.ClickEvent;
/*      */ import net.minecraft.init.Blocks;
/*      */ import net.minecraft.item.ItemStack;
/*      */ import net.minecraft.potion.Potion;
/*      */ import net.minecraft.server.integrated.IntegratedServer;
/*      */ import net.minecraft.src.Config;
/*      */ import net.minecraft.util.AxisAlignedBB;
/*      */ import net.minecraft.util.BlockPos;
/*      */ import net.minecraft.util.ChatComponentText;
/*      */ import net.minecraft.util.ChatStyle;
/*      */ import net.minecraft.util.EntitySelectors;
/*      */ import net.minecraft.util.EnumFacing;
/*      */ import net.minecraft.util.EnumParticleTypes;
/*      */ import net.minecraft.util.EnumWorldBlockLayer;
/*      */ import net.minecraft.util.IChatComponent;
/*      */ import net.minecraft.util.MathHelper;
/*      */ import net.minecraft.util.MouseFilter;
/*      */ import net.minecraft.util.MovingObjectPosition;
/*      */ import net.minecraft.util.ReportedException;
/*      */ import net.minecraft.util.ResourceLocation;
/*      */ import net.minecraft.util.Vec3;
/*      */ import net.minecraft.world.IBlockAccess;
/*      */ import net.minecraft.world.World;
/*      */ import net.minecraft.world.WorldProvider;
/*      */ import net.minecraft.world.WorldSettings;
/*      */ import net.minecraft.world.biome.BiomeGenBase;
/*      */ import net.optifine.CustomColors;
/*      */ import net.optifine.GlErrors;
/*      */ import net.optifine.Lagometer;
/*      */ import net.optifine.RandomEntities;
/*      */ import net.optifine.gui.GuiChatOF;
/*      */ import net.optifine.reflect.Reflector;
/*      */ import net.optifine.reflect.ReflectorForge;
/*      */ import net.optifine.reflect.ReflectorResolver;
/*      */ import net.optifine.shaders.Shaders;
/*      */ import net.optifine.shaders.ShadersRender;
/*      */ import net.optifine.util.MemoryMonitor;
/*      */ import net.optifine.util.TextureUtils;
/*      */ import net.optifine.util.TimedEvent;
/*      */ import org.apache.logging.log4j.LogManager;
/*      */ import org.apache.logging.log4j.Logger;
/*      */ import org.lwjgl.input.Keyboard;
/*      */ import org.lwjgl.input.Mouse;
/*      */ import org.lwjgl.opengl.Display;
/*      */ import org.lwjgl.opengl.GL11;
/*      */ import org.lwjgl.opengl.GLContext;
/*      */ import org.lwjgl.util.glu.Project;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ public class EntityRenderer
/*      */   implements IResourceManagerReloadListener
/*      */ {
/*  107 */   private static final Logger logger = LogManager.getLogger();
/*  108 */   private static final ResourceLocation locationRainPng = new ResourceLocation("textures/environment/rain.png");
/*  109 */   private static final ResourceLocation locationSnowPng = new ResourceLocation("textures/environment/snow.png");
/*      */ 
/*      */   
/*      */   public static boolean anaglyphEnable;
/*      */ 
/*      */   
/*      */   public static int anaglyphField;
/*      */ 
/*      */   
/*      */   private Minecraft mc;
/*      */   
/*      */   private final IResourceManager resourceManager;
/*      */   
/*  122 */   private Random random = new Random();
/*      */ 
/*      */   
/*      */   private float farPlaneDistance;
/*      */ 
/*      */   
/*      */   public ItemRenderer itemRenderer;
/*      */   
/*      */   private final MapItemRenderer theMapItemRenderer;
/*      */   
/*      */   private int rendererUpdateCount;
/*      */   
/*      */   private Entity pointedEntity;
/*      */   
/*  136 */   private MouseFilter mouseFilterXAxis = new MouseFilter();
/*  137 */   private MouseFilter mouseFilterYAxis = new MouseFilter();
/*  138 */   private float thirdPersonDistance = 4.0F;
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  143 */   private float thirdPersonDistanceTemp = 4.0F;
/*      */ 
/*      */ 
/*      */   
/*      */   private float smoothCamYaw;
/*      */ 
/*      */ 
/*      */   
/*      */   private float smoothCamPitch;
/*      */ 
/*      */ 
/*      */   
/*      */   private float smoothCamFilterX;
/*      */ 
/*      */ 
/*      */   
/*      */   private float smoothCamFilterY;
/*      */ 
/*      */ 
/*      */   
/*      */   private float smoothCamPartialTicks;
/*      */ 
/*      */ 
/*      */   
/*      */   private float fovModifierHand;
/*      */ 
/*      */ 
/*      */   
/*      */   private float fovModifierHandPrev;
/*      */ 
/*      */ 
/*      */   
/*      */   private float bossColorModifier;
/*      */ 
/*      */ 
/*      */   
/*      */   private float bossColorModifierPrev;
/*      */ 
/*      */ 
/*      */   
/*      */   private boolean cloudFog;
/*      */ 
/*      */   
/*      */   private boolean renderHand = true;
/*      */ 
/*      */   
/*      */   private boolean drawBlockOutline = true;
/*      */ 
/*      */   
/*  192 */   private long prevFrameTime = Minecraft.getSystemTime();
/*      */ 
/*      */ 
/*      */   
/*      */   private long renderEndNanoTime;
/*      */ 
/*      */ 
/*      */   
/*      */   private final DynamicTexture lightmapTexture;
/*      */ 
/*      */ 
/*      */   
/*      */   private final int[] lightmapColors;
/*      */ 
/*      */ 
/*      */   
/*      */   private final ResourceLocation locationLightMap;
/*      */ 
/*      */ 
/*      */   
/*      */   private boolean lightmapUpdateNeeded;
/*      */ 
/*      */ 
/*      */   
/*      */   private float torchFlickerX;
/*      */ 
/*      */   
/*      */   private float torchFlickerDX;
/*      */ 
/*      */   
/*      */   private int rainSoundCounter;
/*      */ 
/*      */   
/*  225 */   private float[] rainXCoords = new float[1024];
/*  226 */   private float[] rainYCoords = new float[1024];
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  231 */   private FloatBuffer fogColorBuffer = GLAllocation.createDirectFloatBuffer(16);
/*      */ 
/*      */   
/*      */   public float fogColorRed;
/*      */ 
/*      */   
/*      */   public float fogColorGreen;
/*      */   
/*      */   public float fogColorBlue;
/*      */   
/*      */   private float fogColor2;
/*      */   
/*      */   private float fogColor1;
/*      */   
/*  245 */   private int debugViewDirection = 0;
/*      */   private boolean debugView = false;
/*  247 */   private double cameraZoom = 1.0D;
/*      */   private double cameraYaw;
/*      */   private double cameraPitch;
/*      */   private ShaderGroup theShaderGroup;
/*  251 */   private static final ResourceLocation[] shaderResourceLocations = new ResourceLocation[] { new ResourceLocation("shaders/post/notch.json"), new ResourceLocation("shaders/post/fxaa.json"), new ResourceLocation("shaders/post/art.json"), new ResourceLocation("shaders/post/bumpy.json"), new ResourceLocation("shaders/post/blobs2.json"), new ResourceLocation("shaders/post/pencil.json"), new ResourceLocation("shaders/post/color_convolve.json"), new ResourceLocation("shaders/post/deconverge.json"), new ResourceLocation("shaders/post/flip.json"), new ResourceLocation("shaders/post/invert.json"), new ResourceLocation("shaders/post/ntsc.json"), new ResourceLocation("shaders/post/outline.json"), new ResourceLocation("shaders/post/phosphor.json"), new ResourceLocation("shaders/post/scan_pincushion.json"), new ResourceLocation("shaders/post/sobel.json"), new ResourceLocation("shaders/post/bits.json"), new ResourceLocation("shaders/post/desaturate.json"), new ResourceLocation("shaders/post/green.json"), new ResourceLocation("shaders/post/blur.json"), new ResourceLocation("shaders/post/wobble.json"), new ResourceLocation("shaders/post/blobs.json"), new ResourceLocation("shaders/post/antialias.json"), new ResourceLocation("shaders/post/creeper.json"), new ResourceLocation("shaders/post/spider.json") };
/*  252 */   public static final int shaderCount = shaderResourceLocations.length;
/*      */   private int shaderIndex;
/*      */   private boolean useShader;
/*      */   public int frameCount;
/*      */   private boolean initialized = false;
/*  257 */   private World updatedWorld = null;
/*      */   private boolean showDebugInfo = false;
/*      */   public boolean fogStandard = false;
/*  260 */   private float clipDistance = 128.0F;
/*  261 */   private long lastServerTime = 0L;
/*  262 */   private int lastServerTicks = 0;
/*  263 */   private int serverWaitTime = 0;
/*  264 */   private int serverWaitTimeCurrent = 0;
/*  265 */   private float avgServerTimeDiff = 0.0F;
/*  266 */   private float avgServerTickDiff = 0.0F;
/*  267 */   private ShaderGroup[] fxaaShaders = new ShaderGroup[10];
/*      */   private boolean loadVisibleChunks = false;
/*      */   
/*      */   public EntityRenderer(Minecraft mcIn, IResourceManager resourceManagerIn) {
/*  271 */     this.shaderIndex = shaderCount;
/*  272 */     this.useShader = false;
/*  273 */     this.frameCount = 0;
/*  274 */     this.mc = mcIn;
/*  275 */     this.resourceManager = resourceManagerIn;
/*  276 */     this.itemRenderer = mcIn.getItemRenderer();
/*  277 */     this.theMapItemRenderer = new MapItemRenderer(mcIn.getTextureManager());
/*  278 */     this.lightmapTexture = new DynamicTexture(16, 16);
/*  279 */     this.locationLightMap = mcIn.getTextureManager().getDynamicTextureLocation("lightMap", this.lightmapTexture);
/*  280 */     this.lightmapColors = this.lightmapTexture.getTextureData();
/*  281 */     this.theShaderGroup = null;
/*      */     
/*  283 */     for (int i = 0; i < 32; i++) {
/*  284 */       for (int j = 0; j < 32; j++) {
/*  285 */         float f = (j - 16);
/*  286 */         float f1 = (i - 16);
/*  287 */         float f2 = MathHelper.sqrt_float(f * f + f1 * f1);
/*  288 */         this.rainXCoords[i << 5 | j] = -f1 / f2;
/*  289 */         this.rainYCoords[i << 5 | j] = f / f2;
/*      */       } 
/*      */     } 
/*      */   }
/*      */   
/*      */   public boolean isShaderActive() {
/*  295 */     return (OpenGlHelper.shadersSupported && this.theShaderGroup != null);
/*      */   }
/*      */   
/*      */   public void stopUseShader() {
/*  299 */     if (this.theShaderGroup != null) {
/*  300 */       this.theShaderGroup.deleteShaderGroup();
/*      */     }
/*      */     
/*  303 */     this.theShaderGroup = null;
/*  304 */     this.shaderIndex = shaderCount;
/*      */   }
/*      */   
/*      */   public void switchUseShader() {
/*  308 */     this.useShader = !this.useShader;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void loadEntityShader(Entity entityIn) {
/*  315 */     if (OpenGlHelper.shadersSupported) {
/*  316 */       if (this.theShaderGroup != null) {
/*  317 */         this.theShaderGroup.deleteShaderGroup();
/*      */       }
/*      */       
/*  320 */       this.theShaderGroup = null;
/*      */       
/*  322 */       if (entityIn instanceof net.minecraft.entity.monster.EntityCreeper) {
/*  323 */         loadShader(new ResourceLocation("shaders/post/creeper.json"));
/*  324 */       } else if (entityIn instanceof net.minecraft.entity.monster.EntitySpider) {
/*  325 */         loadShader(new ResourceLocation("shaders/post/spider.json"));
/*  326 */       } else if (entityIn instanceof net.minecraft.entity.monster.EntityEnderman) {
/*  327 */         loadShader(new ResourceLocation("shaders/post/invert.json"));
/*  328 */       } else if (Reflector.ForgeHooksClient_loadEntityShader.exists()) {
/*  329 */         Reflector.call(Reflector.ForgeHooksClient_loadEntityShader, new Object[] { entityIn, this });
/*      */       } 
/*      */     } 
/*      */   }
/*      */   
/*      */   public void activateNextShader() {
/*  335 */     if (OpenGlHelper.shadersSupported && this.mc.getRenderViewEntity() instanceof EntityPlayer) {
/*  336 */       if (this.theShaderGroup != null) {
/*  337 */         this.theShaderGroup.deleteShaderGroup();
/*      */       }
/*      */       
/*  340 */       this.shaderIndex = (this.shaderIndex + 1) % (shaderResourceLocations.length + 1);
/*      */       
/*  342 */       if (this.shaderIndex != shaderCount) {
/*  343 */         loadShader(shaderResourceLocations[this.shaderIndex]);
/*      */       } else {
/*  345 */         this.theShaderGroup = null;
/*      */       } 
/*      */     } 
/*      */   }
/*      */   
/*      */   public void loadShader(ResourceLocation resourceLocationIn) {
/*  351 */     if (OpenGlHelper.isFramebufferEnabled()) {
/*      */       try {
/*  353 */         this.theShaderGroup = new ShaderGroup(this.mc.getTextureManager(), this.resourceManager, this.mc.getFramebuffer(), resourceLocationIn);
/*  354 */         this.theShaderGroup.createBindFramebuffers(this.mc.displayWidth, this.mc.displayHeight);
/*  355 */         this.useShader = true;
/*  356 */         System.out.println("asdfsus");
/*  357 */       } catch (IOException ioexception) {
/*  358 */         logger.warn("Failed to load shader: " + resourceLocationIn, ioexception);
/*  359 */         this.shaderIndex = shaderCount;
/*  360 */         this.useShader = false;
/*  361 */         System.out.println("asdf");
/*  362 */       } catch (JsonSyntaxException jsonsyntaxexception) {
/*  363 */         logger.warn("Failed to load shader: " + resourceLocationIn, (Throwable)jsonsyntaxexception);
/*  364 */         this.shaderIndex = shaderCount;
/*  365 */         this.useShader = false;
/*  366 */         System.out.println("asdf1");
/*      */       } 
/*      */     }
/*      */   }
/*      */   
/*      */   public void onResourceManagerReload(IResourceManager resourceManager) {
/*  372 */     if (this.theShaderGroup != null) {
/*  373 */       this.theShaderGroup.deleteShaderGroup();
/*      */     }
/*      */     
/*  376 */     this.theShaderGroup = null;
/*      */     
/*  378 */     if (this.shaderIndex != shaderCount) {
/*  379 */       loadShader(shaderResourceLocations[this.shaderIndex]);
/*      */     } else {
/*  381 */       loadEntityShader(this.mc.getRenderViewEntity());
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void updateRenderer() {
/*  389 */     if (OpenGlHelper.shadersSupported && ShaderLinkHelper.getStaticShaderLinkHelper() == null) {
/*  390 */       ShaderLinkHelper.setNewStaticShaderLinkHelper();
/*      */     }
/*      */     
/*  393 */     updateFovModifierHand();
/*  394 */     updateTorchFlicker();
/*  395 */     this.fogColor2 = this.fogColor1;
/*  396 */     this.thirdPersonDistanceTemp = this.thirdPersonDistance;
/*      */     
/*  398 */     if (this.mc.gameSettings.debugCamEnable) {
/*  399 */       float f = this.mc.gameSettings.mouseSensitivity * 0.6F + 0.2F;
/*  400 */       float f1 = f * f * f * 8.0F;
/*  401 */       this.smoothCamFilterX = this.mouseFilterXAxis.smooth(this.smoothCamYaw, 0.05F * f1);
/*  402 */       this.smoothCamFilterY = this.mouseFilterYAxis.smooth(this.smoothCamPitch, 0.05F * f1);
/*  403 */       this.smoothCamPartialTicks = 0.0F;
/*  404 */       this.smoothCamYaw = 0.0F;
/*  405 */       this.smoothCamPitch = 0.0F;
/*      */     } else {
/*  407 */       this.smoothCamFilterX = 0.0F;
/*  408 */       this.smoothCamFilterY = 0.0F;
/*  409 */       this.mouseFilterXAxis.reset();
/*  410 */       this.mouseFilterYAxis.reset();
/*      */     } 
/*      */     
/*  413 */     if (this.mc.getRenderViewEntity() == null) {
/*  414 */       this.mc.setRenderViewEntity((Entity)this.mc.thePlayer);
/*      */     }
/*      */     
/*  417 */     Entity entity = this.mc.getRenderViewEntity();
/*  418 */     double d2 = entity.posX;
/*  419 */     double d0 = entity.posY + entity.getEyeHeight();
/*  420 */     double d1 = entity.posZ;
/*  421 */     float f2 = this.mc.theWorld.getLightBrightness(new BlockPos(d2, d0, d1));
/*  422 */     float f3 = this.mc.gameSettings.renderDistanceChunks / 16.0F;
/*  423 */     f3 = MathHelper.clamp_float(f3, 0.0F, 1.0F);
/*  424 */     float f4 = f2 * (1.0F - f3) + f3;
/*  425 */     this.fogColor1 += (f4 - this.fogColor1) * 0.1F;
/*  426 */     this.rendererUpdateCount++;
/*  427 */     this.itemRenderer.updateEquippedItem();
/*  428 */     addRainParticles();
/*  429 */     this.bossColorModifierPrev = this.bossColorModifier;
/*      */     
/*  431 */     if (BossStatus.hasColorModifier) {
/*  432 */       this.bossColorModifier += 0.05F;
/*      */       
/*  434 */       if (this.bossColorModifier > 1.0F) {
/*  435 */         this.bossColorModifier = 1.0F;
/*      */       }
/*      */       
/*  438 */       BossStatus.hasColorModifier = false;
/*  439 */     } else if (this.bossColorModifier > 0.0F) {
/*  440 */       this.bossColorModifier -= 0.0125F;
/*      */     } 
/*      */   }
/*      */   
/*      */   public ShaderGroup getShaderGroup() {
/*  445 */     return this.theShaderGroup;
/*      */   }
/*      */   
/*      */   public void updateShaderGroupSize(int width, int height) {
/*  449 */     if (OpenGlHelper.shadersSupported) {
/*  450 */       if (this.theShaderGroup != null) {
/*  451 */         this.theShaderGroup.createBindFramebuffers(width, height);
/*      */       }
/*  453 */       this.mc.renderGlobal.createBindEntityOutlineFbs(width, height);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void getMouseOver(float partialTicks) {
/*  461 */     Entity entity = this.mc.getRenderViewEntity();
/*      */     
/*  463 */     if (entity != null && this.mc.theWorld != null) {
/*  464 */       this.mc.mcProfiler.startSection("pick");
/*  465 */       this.mc.pointedEntity = null;
/*  466 */       double d0 = this.mc.playerController.getBlockReachDistance();
/*  467 */       this.mc.objectMouseOver = entity.rayTrace(d0, partialTicks);
/*  468 */       double d1 = d0;
/*  469 */       Vec3 vec3 = entity.getPositionEyes(partialTicks);
/*  470 */       boolean flag = false;
/*  471 */       int i = 3;
/*      */       
/*  473 */       if (this.mc.playerController.extendedReach()) {
/*  474 */         d0 = 6.0D;
/*  475 */         d1 = 6.0D;
/*  476 */       } else if (d0 > 3.0D) {
/*  477 */         flag = true;
/*      */       } 
/*      */       
/*  480 */       if (this.mc.objectMouseOver != null) {
/*  481 */         d1 = this.mc.objectMouseOver.hitVec.distanceTo(vec3);
/*      */       }
/*      */       
/*  484 */       Vec3 vec31 = entity.getLook(partialTicks);
/*  485 */       Vec3 vec32 = vec3.addVector(vec31.xCoord * d0, vec31.yCoord * d0, vec31.zCoord * d0);
/*  486 */       this.pointedEntity = null;
/*  487 */       Vec3 vec33 = null;
/*  488 */       float f = 1.0F;
/*  489 */       List<Entity> list = this.mc.theWorld.getEntitiesInAABBexcluding(entity, entity.getEntityBoundingBox().addCoord(vec31.xCoord * d0, vec31.yCoord * d0, vec31.zCoord * d0).expand(f, f, f), Predicates.and(EntitySelectors.NOT_SPECTATING, new Predicate<Entity>() {
/*      */               public boolean apply(Entity p_apply_1_) {
/*  491 */                 return p_apply_1_.canBeCollidedWith();
/*      */               }
/*      */             }));
/*  494 */       double d2 = d1;
/*      */       
/*  496 */       for (int j = 0; j < list.size(); j++) {
/*  497 */         Entity entity1 = list.get(j);
/*  498 */         float f1 = entity1.getCollisionBorderSize();
/*  499 */         AxisAlignedBB axisalignedbb = entity1.getEntityBoundingBox().expand(f1, f1, f1);
/*  500 */         MovingObjectPosition movingobjectposition = axisalignedbb.calculateIntercept(vec3, vec32);
/*      */         
/*  502 */         if (axisalignedbb.isVecInside(vec3)) {
/*  503 */           if (d2 >= 0.0D) {
/*  504 */             this.pointedEntity = entity1;
/*  505 */             vec33 = (movingobjectposition == null) ? vec3 : movingobjectposition.hitVec;
/*  506 */             d2 = 0.0D;
/*      */           } 
/*  508 */         } else if (movingobjectposition != null) {
/*  509 */           double d3 = vec3.distanceTo(movingobjectposition.hitVec);
/*      */           
/*  511 */           if (d3 < d2 || d2 == 0.0D) {
/*  512 */             boolean flag1 = false;
/*      */             
/*  514 */             if (Reflector.ForgeEntity_canRiderInteract.exists()) {
/*  515 */               flag1 = Reflector.callBoolean(entity1, Reflector.ForgeEntity_canRiderInteract, new Object[0]);
/*      */             }
/*      */             
/*  518 */             if (!flag1 && entity1 == entity.ridingEntity) {
/*  519 */               if (d2 == 0.0D) {
/*  520 */                 this.pointedEntity = entity1;
/*  521 */                 vec33 = movingobjectposition.hitVec;
/*      */               } 
/*      */             } else {
/*  524 */               this.pointedEntity = entity1;
/*  525 */               vec33 = movingobjectposition.hitVec;
/*  526 */               d2 = d3;
/*      */             } 
/*      */           } 
/*      */         } 
/*      */       } 
/*      */       
/*  532 */       if (this.pointedEntity != null && flag && vec3.distanceTo(vec33) > 3.0D) {
/*  533 */         this.pointedEntity = null;
/*  534 */         this.mc.objectMouseOver = new MovingObjectPosition(MovingObjectPosition.MovingObjectType.MISS, vec33, null, new BlockPos(vec33));
/*      */       } 
/*      */       
/*  537 */       if (this.pointedEntity != null && (d2 < d1 || this.mc.objectMouseOver == null)) {
/*  538 */         this.mc.objectMouseOver = new MovingObjectPosition(this.pointedEntity, vec33);
/*      */         
/*  540 */         if (this.pointedEntity instanceof EntityLivingBase || this.pointedEntity instanceof net.minecraft.entity.item.EntityItemFrame) {
/*  541 */           this.mc.pointedEntity = this.pointedEntity;
/*      */         }
/*      */       } 
/*      */       
/*  545 */       this.mc.mcProfiler.endSection();
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void updateFovModifierHand() {
/*  553 */     float f = 1.0F;
/*      */     
/*  555 */     if (this.mc.getRenderViewEntity() instanceof AbstractClientPlayer) {
/*  556 */       AbstractClientPlayer abstractclientplayer = (AbstractClientPlayer)this.mc.getRenderViewEntity();
/*  557 */       f = abstractclientplayer.getFovModifier();
/*      */     } 
/*      */     
/*  560 */     this.fovModifierHandPrev = this.fovModifierHand;
/*  561 */     this.fovModifierHand += (f - this.fovModifierHand) * 0.5F;
/*      */     
/*  563 */     if (this.fovModifierHand > 1.5F) {
/*  564 */       this.fovModifierHand = 1.5F;
/*      */     }
/*      */     
/*  567 */     if (this.fovModifierHand < 0.1F) {
/*  568 */       this.fovModifierHand = 0.1F;
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private float getFOVModifier(float partialTicks, boolean useFOVSetting) {
/*  578 */     if (this.debugView) {
/*  579 */       return 90.0F;
/*      */     }
/*  581 */     Entity entity = this.mc.getRenderViewEntity();
/*  582 */     float f = 70.0F;
/*      */     
/*  584 */     if (useFOVSetting) {
/*  585 */       f = this.mc.gameSettings.gammaSetting;
/*      */       
/*  587 */       if (Config.isDynamicFov()) {
/*  588 */         f *= this.fovModifierHandPrev + (this.fovModifierHand - this.fovModifierHandPrev) * partialTicks;
/*      */       }
/*      */     } 
/*      */     
/*  592 */     boolean flag = false;
/*      */     
/*  594 */     if (this.mc.currentScreen == null) {
/*  595 */       GameSettings gamesettings = this.mc.gameSettings;
/*  596 */       flag = GameSettings.isKeyDown(this.mc.gameSettings.ofKeyBindZoom);
/*      */     } 
/*      */     
/*  599 */     if (flag) {
/*  600 */       if (!Config.zoomMode) {
/*  601 */         Config.zoomMode = true;
/*  602 */         if (!(Client.getInstance()).hudManager.noCinematicZoom.isEnabled()) {
/*  603 */           Config.zoomSmoothCamera = this.mc.gameSettings.debugCamEnable;
/*  604 */           this.mc.gameSettings.debugCamEnable = true;
/*      */         } 
/*  606 */         this.mc.renderGlobal.displayListEntitiesDirty = true;
/*      */       } 
/*      */       
/*  609 */       if (Config.zoomMode) {
/*  610 */         f /= 4.0F;
/*      */       }
/*  612 */     } else if (Config.zoomMode) {
/*  613 */       Config.zoomMode = false;
/*  614 */       this.mc.gameSettings.debugCamEnable = Config.zoomSmoothCamera;
/*  615 */       this.mouseFilterXAxis = new MouseFilter();
/*  616 */       this.mouseFilterYAxis = new MouseFilter();
/*  617 */       this.mc.renderGlobal.displayListEntitiesDirty = true;
/*      */     } 
/*      */     
/*  620 */     if (entity instanceof EntityLivingBase && ((EntityLivingBase)entity).getHealth() <= 0.0F) {
/*  621 */       float f1 = ((EntityLivingBase)entity).deathTime + partialTicks;
/*  622 */       f /= (1.0F - 500.0F / (f1 + 500.0F)) * 2.0F + 1.0F;
/*      */     } 
/*      */     
/*  625 */     Block block = ActiveRenderInfo.getBlockAtEntityViewpoint((World)this.mc.theWorld, entity, partialTicks);
/*      */     
/*  627 */     if (block.getMaterial() == Material.water) {
/*  628 */       f = f * 60.0F / 70.0F;
/*      */     }
/*      */     
/*  631 */     return Reflector.ForgeHooksClient_getFOVModifier.exists() ? Reflector.callFloat(Reflector.ForgeHooksClient_getFOVModifier, new Object[] { this, entity, block, Float.valueOf(partialTicks), Float.valueOf(f) }) : f;
/*      */   }
/*      */ 
/*      */   
/*      */   private void hurtCameraEffect(float partialTicks) {
/*  636 */     if (this.mc.getRenderViewEntity() instanceof EntityLivingBase) {
/*  637 */       if ((Client.getInstance()).hudManager.noHeartCam.isEnabled()) {
/*      */         return;
/*      */       }
/*  640 */       EntityLivingBase entitylivingbase = (EntityLivingBase)this.mc.getRenderViewEntity();
/*  641 */       float f = entitylivingbase.hurtTime - partialTicks;
/*      */       
/*  643 */       if (entitylivingbase.getHealth() <= 0.0F) {
/*  644 */         float f1 = entitylivingbase.deathTime + partialTicks;
/*  645 */         GlStateManager.rotate(40.0F - 8000.0F / (f1 + 200.0F), 0.0F, 0.0F, 1.0F);
/*      */       } 
/*      */       
/*  648 */       if (f < 0.0F) {
/*      */         return;
/*      */       }
/*      */       
/*  652 */       f /= entitylivingbase.maxHurtTime;
/*  653 */       f = MathHelper.sin(f * f * f * f * 3.1415927F);
/*  654 */       float f2 = entitylivingbase.attackedAtYaw;
/*  655 */       GlStateManager.rotate(-f2, 0.0F, 1.0F, 0.0F);
/*  656 */       GlStateManager.rotate(-f * 14.0F, 0.0F, 0.0F, 1.0F);
/*  657 */       GlStateManager.rotate(f2, 0.0F, 1.0F, 0.0F);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void setupViewBobbing(float partialTicks) {
/*  665 */     if (this.mc.getRenderViewEntity() instanceof EntityPlayer) {
/*  666 */       EntityPlayer entityplayer = (EntityPlayer)this.mc.getRenderViewEntity();
/*  667 */       float f = entityplayer.distanceWalkedModified - entityplayer.prevDistanceWalkedModified;
/*  668 */       float f1 = -(entityplayer.distanceWalkedModified + f * partialTicks);
/*  669 */       float f2 = entityplayer.prevCameraYaw + (entityplayer.cameraYaw - entityplayer.prevCameraYaw) * partialTicks;
/*  670 */       float f3 = entityplayer.prevCameraPitch + (entityplayer.cameraPitch - entityplayer.prevCameraPitch) * partialTicks;
/*  671 */       GlStateManager.translate(MathHelper.sin(f1 * 3.1415927F) * f2 * 0.5F, -Math.abs(MathHelper.cos(f1 * 3.1415927F) * f2), 0.0F);
/*  672 */       GlStateManager.rotate(MathHelper.sin(f1 * 3.1415927F) * f2 * 3.0F, 0.0F, 0.0F, 1.0F);
/*  673 */       GlStateManager.rotate(Math.abs(MathHelper.cos(f1 * 3.1415927F - 0.2F) * f2) * 5.0F, 1.0F, 0.0F, 0.0F);
/*  674 */       GlStateManager.rotate(f3, 1.0F, 0.0F, 0.0F);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void orientCamera(float partialTicks) {
/*  682 */     Entity entity = this.mc.getRenderViewEntity();
/*  683 */     float f = entity.getEyeHeight();
/*  684 */     double d0 = entity.prevPosX + (entity.posX - entity.prevPosX) * partialTicks;
/*  685 */     double d1 = entity.prevPosY + (entity.posY - entity.prevPosY) * partialTicks + f;
/*  686 */     double d2 = entity.prevPosZ + (entity.posZ - entity.prevPosZ) * partialTicks;
/*      */     
/*  688 */     if (entity instanceof EntityLivingBase && ((EntityLivingBase)entity).isPlayerSleeping()) {
/*  689 */       f = (float)(f + 1.0D);
/*  690 */       GlStateManager.translate(0.0F, 0.3F, 0.0F);
/*      */       
/*  692 */       if (!this.mc.gameSettings.fovSetting) {
/*  693 */         BlockPos blockpos = new BlockPos(entity);
/*  694 */         IBlockState iblockstate = this.mc.theWorld.getBlockState(blockpos);
/*  695 */         Block block = iblockstate.getBlock();
/*      */         
/*  697 */         if (Reflector.ForgeHooksClient_orientBedCamera.exists()) {
/*  698 */           Reflector.callVoid(Reflector.ForgeHooksClient_orientBedCamera, new Object[] { this.mc.theWorld, blockpos, iblockstate, entity });
/*  699 */         } else if (block == Blocks.bed) {
/*  700 */           int j = ((EnumFacing)iblockstate.getValue((IProperty)BlockBed.FACING)).getHorizontalIndex();
/*  701 */           GlStateManager.rotate((j * 90), 0.0F, 1.0F, 0.0F);
/*      */         } 
/*      */         
/*  704 */         GlStateManager.rotate(entity.prevRotationYaw + (entity.rotationYaw - entity.prevRotationYaw) * partialTicks + 180.0F, 0.0F, -1.0F, 0.0F);
/*  705 */         GlStateManager.rotate(entity.prevRotationPitch + (entity.rotationPitch - entity.prevRotationPitch) * partialTicks, -1.0F, 0.0F, 0.0F);
/*      */       } 
/*  707 */     } else if (this.mc.gameSettings.showDebugInfo > 0) {
/*  708 */       double d3 = (this.thirdPersonDistanceTemp + (this.thirdPersonDistance - this.thirdPersonDistanceTemp) * partialTicks);
/*      */       
/*  710 */       if (this.mc.gameSettings.fovSetting) {
/*  711 */         GlStateManager.translate(0.0F, 0.0F, (float)-d3);
/*      */       } else {
/*  713 */         float f1 = entity.rotationYaw;
/*  714 */         float f2 = entity.rotationPitch;
/*      */         
/*  716 */         if (this.mc.gameSettings.showDebugInfo == 2) {
/*  717 */           f2 += 180.0F;
/*      */         }
/*      */         
/*  720 */         double d4 = (-MathHelper.sin(f1 / 180.0F * 3.1415927F) * MathHelper.cos(f2 / 180.0F * 3.1415927F)) * d3;
/*  721 */         double d5 = (MathHelper.cos(f1 / 180.0F * 3.1415927F) * MathHelper.cos(f2 / 180.0F * 3.1415927F)) * d3;
/*  722 */         double d6 = -MathHelper.sin(f2 / 180.0F * 3.1415927F) * d3;
/*      */         
/*  724 */         for (int i = 0; i < 8; i++) {
/*  725 */           float f3 = ((i & 0x1) * 2 - 1);
/*  726 */           float f4 = ((i >> 1 & 0x1) * 2 - 1);
/*  727 */           float f5 = ((i >> 2 & 0x1) * 2 - 1);
/*  728 */           f3 *= 0.1F;
/*  729 */           f4 *= 0.1F;
/*  730 */           f5 *= 0.1F;
/*  731 */           MovingObjectPosition movingobjectposition = this.mc.theWorld.rayTraceBlocks(new Vec3(d0 + f3, d1 + f4, d2 + f5), new Vec3(d0 - d4 + f3 + f5, d1 - d6 + f4, d2 - d5 + f5));
/*      */           
/*  733 */           if (movingobjectposition != null) {
/*  734 */             double d7 = movingobjectposition.hitVec.distanceTo(new Vec3(d0, d1, d2));
/*      */             
/*  736 */             if (d7 < d3) {
/*  737 */               d3 = d7;
/*      */             }
/*      */           } 
/*      */         } 
/*      */         
/*  742 */         if (this.mc.gameSettings.showDebugInfo == 2) {
/*  743 */           GlStateManager.rotate(180.0F, 0.0F, 1.0F, 0.0F);
/*      */         }
/*      */         
/*  746 */         GlStateManager.rotate(entity.rotationPitch - f2, 1.0F, 0.0F, 0.0F);
/*  747 */         GlStateManager.rotate(entity.rotationYaw - f1, 0.0F, 1.0F, 0.0F);
/*  748 */         GlStateManager.translate(0.0F, 0.0F, (float)-d3);
/*  749 */         GlStateManager.rotate(f1 - entity.rotationYaw, 0.0F, 1.0F, 0.0F);
/*  750 */         GlStateManager.rotate(f2 - entity.rotationPitch, 1.0F, 0.0F, 0.0F);
/*      */       } 
/*      */     } else {
/*  753 */       GlStateManager.translate(0.0F, 0.0F, -0.1F);
/*      */     } 
/*      */     
/*  756 */     if (Reflector.EntityViewRenderEvent_CameraSetup_Constructor.exists()) {
/*  757 */       if (!this.mc.gameSettings.fovSetting) {
/*  758 */         float f6 = entity.prevRotationYaw + (entity.rotationYaw - entity.prevRotationYaw) * partialTicks + 180.0F;
/*  759 */         float f7 = entity.prevRotationPitch + (entity.rotationPitch - entity.prevRotationPitch) * partialTicks;
/*  760 */         float f8 = 0.0F;
/*      */         
/*  762 */         if (entity instanceof EntityAnimal) {
/*  763 */           EntityAnimal entityanimal1 = (EntityAnimal)entity;
/*  764 */           f6 = entityanimal1.prevRotationYawHead + (entityanimal1.rotationYawHead - entityanimal1.prevRotationYawHead) * partialTicks + 180.0F;
/*      */         } 
/*      */         
/*  767 */         Block block1 = ActiveRenderInfo.getBlockAtEntityViewpoint((World)this.mc.theWorld, entity, partialTicks);
/*  768 */         Object object = Reflector.newInstance(Reflector.EntityViewRenderEvent_CameraSetup_Constructor, new Object[] { this, entity, block1, Float.valueOf(partialTicks), Float.valueOf(f6), Float.valueOf(f7), Float.valueOf(f8) });
/*  769 */         Reflector.postForgeBusEvent(object);
/*  770 */         f8 = Reflector.getFieldValueFloat(object, Reflector.EntityViewRenderEvent_CameraSetup_roll, f8);
/*  771 */         f7 = Reflector.getFieldValueFloat(object, Reflector.EntityViewRenderEvent_CameraSetup_pitch, f7);
/*  772 */         f6 = Reflector.getFieldValueFloat(object, Reflector.EntityViewRenderEvent_CameraSetup_yaw, f6);
/*  773 */         GlStateManager.rotate(f8, 0.0F, 0.0F, 1.0F);
/*  774 */         GlStateManager.rotate(f7, 1.0F, 0.0F, 0.0F);
/*  775 */         GlStateManager.rotate(f6, 0.0F, 1.0F, 0.0F);
/*      */       } 
/*  777 */     } else if (!this.mc.gameSettings.fovSetting) {
/*  778 */       GlStateManager.rotate(entity.prevRotationPitch + (entity.rotationPitch - entity.prevRotationPitch) * partialTicks, 1.0F, 0.0F, 0.0F);
/*      */       
/*  780 */       if (entity instanceof EntityAnimal) {
/*  781 */         EntityAnimal entityanimal = (EntityAnimal)entity;
/*  782 */         GlStateManager.rotate(entityanimal.prevRotationYawHead + (entityanimal.rotationYawHead - entityanimal.prevRotationYawHead) * partialTicks + 180.0F, 0.0F, 1.0F, 0.0F);
/*      */       } else {
/*  784 */         GlStateManager.rotate(entity.prevRotationYaw + (entity.rotationYaw - entity.prevRotationYaw) * partialTicks + 180.0F, 0.0F, 1.0F, 0.0F);
/*      */       } 
/*      */     } 
/*      */     
/*  788 */     GlStateManager.translate(0.0F, -f, 0.0F);
/*  789 */     d0 = entity.prevPosX + (entity.posX - entity.prevPosX) * partialTicks;
/*  790 */     d1 = entity.prevPosY + (entity.posY - entity.prevPosY) * partialTicks + f;
/*  791 */     d2 = entity.prevPosZ + (entity.posZ - entity.prevPosZ) * partialTicks;
/*  792 */     this.cloudFog = this.mc.renderGlobal.hasCloudFog(d0, d1, d2, partialTicks);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setupCameraTransform(float partialTicks, int pass) {
/*  799 */     this.farPlaneDistance = (this.mc.gameSettings.renderDistanceChunks * 16);
/*      */     
/*  801 */     if (Config.isFogFancy()) {
/*  802 */       this.farPlaneDistance *= 0.95F;
/*      */     }
/*      */     
/*  805 */     if (Config.isFogFast()) {
/*  806 */       this.farPlaneDistance *= 0.83F;
/*      */     }
/*      */     
/*  809 */     GlStateManager.matrixMode(5889);
/*  810 */     GlStateManager.loadIdentity();
/*  811 */     float f = 0.07F;
/*      */     
/*  813 */     if (this.mc.gameSettings.anaglyph) {
/*  814 */       GlStateManager.translate(-(pass * 2 - 1) * f, 0.0F, 0.0F);
/*      */     }
/*      */     
/*  817 */     this.clipDistance = this.farPlaneDistance * 2.0F;
/*      */     
/*  819 */     if (this.clipDistance < 173.0F) {
/*  820 */       this.clipDistance = 173.0F;
/*      */     }
/*      */     
/*  823 */     if (this.cameraZoom != 1.0D) {
/*  824 */       GlStateManager.translate((float)this.cameraYaw, (float)-this.cameraPitch, 0.0F);
/*  825 */       GlStateManager.scale(this.cameraZoom, this.cameraZoom, 1.0D);
/*      */     } 
/*      */     
/*  828 */     Project.gluPerspective(getFOVModifier(partialTicks, true), this.mc.displayWidth / this.mc.displayHeight, 0.05F, this.clipDistance);
/*  829 */     GlStateManager.matrixMode(5888);
/*  830 */     GlStateManager.loadIdentity();
/*      */     
/*  832 */     if (this.mc.gameSettings.anaglyph) {
/*  833 */       GlStateManager.translate((pass * 2 - 1) * 0.1F, 0.0F, 0.0F);
/*      */     }
/*      */     
/*  836 */     hurtCameraEffect(partialTicks);
/*      */     
/*  838 */     if (this.mc.gameSettings.viewBobbing) {
/*  839 */       setupViewBobbing(partialTicks);
/*      */     }
/*      */     
/*  842 */     float f1 = this.mc.thePlayer.prevTimeInPortal + (this.mc.thePlayer.timeInPortal - this.mc.thePlayer.prevTimeInPortal) * partialTicks;
/*      */     
/*  844 */     if (f1 > 0.0F) {
/*  845 */       int i = 20;
/*      */       
/*  847 */       if (this.mc.thePlayer.isPotionActive(Potion.confusion)) {
/*  848 */         i = 7;
/*      */       }
/*      */       
/*  851 */       if (!(Client.getInstance()).hudManager.newNausea.isEnabled()) {
/*  852 */         float f2 = 5.0F / (f1 * f1 + 5.0F) - f1 * 0.04F;
/*  853 */         f2 *= f2;
/*  854 */         GlStateManager.rotate((this.rendererUpdateCount + partialTicks) * i, 0.0F, 1.0F, 1.0F);
/*  855 */         GlStateManager.scale(1.0F / f2, 1.0F, 1.0F);
/*  856 */         GlStateManager.rotate(-(this.rendererUpdateCount + partialTicks) * i, 0.0F, 1.0F, 1.0F);
/*      */       } 
/*      */     } 
/*      */     
/*  860 */     orientCamera(partialTicks);
/*      */     
/*  862 */     if (this.debugView) {
/*  863 */       switch (this.debugViewDirection) {
/*      */         case 0:
/*  865 */           GlStateManager.rotate(90.0F, 0.0F, 1.0F, 0.0F);
/*      */           break;
/*      */         
/*      */         case 1:
/*  869 */           GlStateManager.rotate(180.0F, 0.0F, 1.0F, 0.0F);
/*      */           break;
/*      */         
/*      */         case 2:
/*  873 */           GlStateManager.rotate(-90.0F, 0.0F, 1.0F, 0.0F);
/*      */           break;
/*      */         
/*      */         case 3:
/*  877 */           GlStateManager.rotate(90.0F, 1.0F, 0.0F, 0.0F);
/*      */           break;
/*      */         
/*      */         case 4:
/*  881 */           GlStateManager.rotate(-90.0F, 1.0F, 0.0F, 0.0F);
/*      */           break;
/*      */       } 
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private void renderHand(float partialTicks, int xOffset) {
/*  890 */     renderHand(partialTicks, xOffset, true, true, false);
/*      */   }
/*      */   
/*      */   public void renderHand(float p_renderHand_1_, int p_renderHand_2_, boolean p_renderHand_3_, boolean p_renderHand_4_, boolean p_renderHand_5_) {
/*  894 */     if (!this.debugView) {
/*  895 */       GlStateManager.matrixMode(5889);
/*  896 */       GlStateManager.loadIdentity();
/*  897 */       float f = 0.07F;
/*      */       
/*  899 */       if (this.mc.gameSettings.anaglyph) {
/*  900 */         GlStateManager.translate(-(p_renderHand_2_ * 2 - 1) * f, 0.0F, 0.0F);
/*      */       }
/*      */       
/*  903 */       if (Config.isShaders()) {
/*  904 */         Shaders.applyHandDepth();
/*      */       }
/*      */       
/*  907 */       Project.gluPerspective(getFOVModifier(p_renderHand_1_, false), this.mc.displayWidth / this.mc.displayHeight, 0.05F, this.farPlaneDistance * 2.0F);
/*  908 */       GlStateManager.matrixMode(5888);
/*  909 */       GlStateManager.loadIdentity();
/*      */       
/*  911 */       if (this.mc.gameSettings.anaglyph) {
/*  912 */         GlStateManager.translate((p_renderHand_2_ * 2 - 1) * 0.1F, 0.0F, 0.0F);
/*      */       }
/*      */       
/*  915 */       boolean flag = false;
/*      */       
/*  917 */       if (p_renderHand_3_) {
/*  918 */         GlStateManager.pushMatrix();
/*  919 */         hurtCameraEffect(p_renderHand_1_);
/*      */         
/*  921 */         if (this.mc.gameSettings.viewBobbing) {
/*  922 */           setupViewBobbing(p_renderHand_1_);
/*      */         }
/*      */         
/*  925 */         flag = (this.mc.getRenderViewEntity() instanceof EntityLivingBase && ((EntityLivingBase)this.mc.getRenderViewEntity()).isPlayerSleeping());
/*  926 */         boolean flag1 = !ReflectorForge.renderFirstPersonHand(this.mc.renderGlobal, p_renderHand_1_, p_renderHand_2_);
/*      */         
/*  928 */         if (flag1 && this.mc.gameSettings.showDebugInfo == 0 && !flag && !this.mc.gameSettings.thirdPersonView && !this.mc.playerController.isSpectator()) {
/*  929 */           enableLightmap();
/*      */           
/*  931 */           if (Config.isShaders()) {
/*  932 */             ShadersRender.renderItemFP(this.itemRenderer, p_renderHand_1_, p_renderHand_5_);
/*      */           } else {
/*  934 */             this.itemRenderer.renderItemInFirstPerson(p_renderHand_1_);
/*      */           } 
/*      */           
/*  937 */           disableLightmap();
/*      */         } 
/*      */         
/*  940 */         GlStateManager.popMatrix();
/*      */       } 
/*      */       
/*  943 */       if (!p_renderHand_4_) {
/*      */         return;
/*      */       }
/*      */       
/*  947 */       disableLightmap();
/*      */       
/*  949 */       if (this.mc.gameSettings.showDebugInfo == 0 && !flag) {
/*  950 */         this.itemRenderer.renderOverlays(p_renderHand_1_);
/*  951 */         hurtCameraEffect(p_renderHand_1_);
/*      */       } 
/*      */       
/*  954 */       if (this.mc.gameSettings.viewBobbing) {
/*  955 */         setupViewBobbing(p_renderHand_1_);
/*      */       }
/*      */     } 
/*      */   }
/*      */   
/*      */   public void disableLightmap() {
/*  961 */     GlStateManager.setActiveTexture(OpenGlHelper.lightmapTexUnit);
/*  962 */     GlStateManager.disableTexture2D();
/*  963 */     GlStateManager.setActiveTexture(OpenGlHelper.defaultTexUnit);
/*      */     
/*  965 */     if (Config.isShaders()) {
/*  966 */       Shaders.disableLightmap();
/*      */     }
/*      */   }
/*      */   
/*      */   public void enableLightmap() {
/*  971 */     GlStateManager.setActiveTexture(OpenGlHelper.lightmapTexUnit);
/*  972 */     GlStateManager.matrixMode(5890);
/*  973 */     GlStateManager.loadIdentity();
/*  974 */     float f = 0.00390625F;
/*  975 */     GlStateManager.scale(f, f, f);
/*  976 */     GlStateManager.translate(8.0F, 8.0F, 8.0F);
/*  977 */     GlStateManager.matrixMode(5888);
/*  978 */     this.mc.getTextureManager().bindTexture(this.locationLightMap);
/*  979 */     GL11.glTexParameteri(3553, 10241, 9729);
/*  980 */     GL11.glTexParameteri(3553, 10240, 9729);
/*  981 */     GL11.glTexParameteri(3553, 10242, 33071);
/*  982 */     GL11.glTexParameteri(3553, 10243, 33071);
/*  983 */     GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
/*  984 */     GlStateManager.enableTexture2D();
/*  985 */     GlStateManager.setActiveTexture(OpenGlHelper.defaultTexUnit);
/*      */     
/*  987 */     if (Config.isShaders()) {
/*  988 */       Shaders.enableLightmap();
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void updateTorchFlicker() {
/*  996 */     this.torchFlickerDX = (float)(this.torchFlickerDX + (Math.random() - Math.random()) * Math.random() * Math.random());
/*  997 */     this.torchFlickerDX = (float)(this.torchFlickerDX * 0.9D);
/*  998 */     this.torchFlickerX += (this.torchFlickerDX - this.torchFlickerX) * 1.0F;
/*  999 */     this.lightmapUpdateNeeded = true;
/*      */   }
/*      */   
/*      */   private void updateLightmap(float partialTicks) {
/* 1003 */     if (this.lightmapUpdateNeeded) {
/* 1004 */       this.mc.mcProfiler.startSection("lightTex");
/* 1005 */       WorldClient worldClient = this.mc.theWorld;
/*      */       
/* 1007 */       if (worldClient != null) {
/* 1008 */         if (Config.isCustomColors() && CustomColors.updateLightmap((World)worldClient, this.torchFlickerX, this.lightmapColors, this.mc.thePlayer.isPotionActive(Potion.nightVision), partialTicks)) {
/* 1009 */           this.lightmapTexture.updateDynamicTexture();
/* 1010 */           this.lightmapUpdateNeeded = false;
/* 1011 */           this.mc.mcProfiler.endSection();
/*      */           
/*      */           return;
/*      */         } 
/* 1015 */         float f = worldClient.getSunBrightness(1.0F);
/* 1016 */         float f1 = f * 0.95F + 0.05F;
/*      */         
/* 1018 */         for (int i = 0; i < 256; i++) {
/* 1019 */           float f2 = ((World)worldClient).provider.getLightBrightnessTable()[i / 16] * f1;
/* 1020 */           float f3 = ((World)worldClient).provider.getLightBrightnessTable()[i % 16] * (this.torchFlickerX * 0.1F + 1.5F);
/*      */           
/* 1022 */           if (worldClient.getLastLightningBolt() > 0) {
/* 1023 */             f2 = ((World)worldClient).provider.getLightBrightnessTable()[i / 16];
/*      */           }
/*      */           
/* 1026 */           float f4 = f2 * (f * 0.65F + 0.35F);
/* 1027 */           float f5 = f2 * (f * 0.65F + 0.35F);
/* 1028 */           float f6 = f3 * ((f3 * 0.6F + 0.4F) * 0.6F + 0.4F);
/* 1029 */           float f7 = f3 * (f3 * f3 * 0.6F + 0.4F);
/* 1030 */           float f8 = f4 + f3;
/* 1031 */           float f9 = f5 + f6;
/* 1032 */           float f10 = f2 + f7;
/* 1033 */           f8 = f8 * 0.96F + 0.03F;
/* 1034 */           f9 = f9 * 0.96F + 0.03F;
/* 1035 */           f10 = f10 * 0.96F + 0.03F;
/*      */           
/* 1037 */           if (this.bossColorModifier > 0.0F) {
/* 1038 */             float f11 = this.bossColorModifierPrev + (this.bossColorModifier - this.bossColorModifierPrev) * partialTicks;
/* 1039 */             f8 = f8 * (1.0F - f11) + f8 * 0.7F * f11;
/* 1040 */             f9 = f9 * (1.0F - f11) + f9 * 0.6F * f11;
/* 1041 */             f10 = f10 * (1.0F - f11) + f10 * 0.6F * f11;
/*      */           } 
/*      */           
/* 1044 */           if (((World)worldClient).provider.getDimensionId() == 1) {
/* 1045 */             f8 = 0.22F + f3 * 0.75F;
/* 1046 */             f9 = 0.28F + f6 * 0.75F;
/* 1047 */             f10 = 0.25F + f7 * 0.75F;
/*      */           } 
/*      */           
/* 1050 */           if (this.mc.thePlayer.isPotionActive(Potion.nightVision)) {
/* 1051 */             float f15 = getNightVisionBrightness((EntityLivingBase)this.mc.thePlayer, partialTicks);
/* 1052 */             float f12 = 1.0F / f8;
/*      */             
/* 1054 */             if (f12 > 1.0F / f9) {
/* 1055 */               f12 = 1.0F / f9;
/*      */             }
/*      */             
/* 1058 */             if (f12 > 1.0F / f10) {
/* 1059 */               f12 = 1.0F / f10;
/*      */             }
/*      */             
/* 1062 */             f8 = f8 * (1.0F - f15) + f8 * f12 * f15;
/* 1063 */             f9 = f9 * (1.0F - f15) + f9 * f12 * f15;
/* 1064 */             f10 = f10 * (1.0F - f15) + f10 * f12 * f15;
/*      */           } 
/*      */           
/* 1067 */           if (f8 > 1.0F) {
/* 1068 */             f8 = 1.0F;
/*      */           }
/*      */           
/* 1071 */           if (f9 > 1.0F) {
/* 1072 */             f9 = 1.0F;
/*      */           }
/*      */           
/* 1075 */           if (f10 > 1.0F) {
/* 1076 */             f10 = 1.0F;
/*      */           }
/*      */           
/* 1079 */           float f16 = this.mc.gameSettings.saturation;
/* 1080 */           float f17 = 1.0F - f8;
/* 1081 */           float f13 = 1.0F - f9;
/* 1082 */           float f14 = 1.0F - f10;
/* 1083 */           f17 = 1.0F - f17 * f17 * f17 * f17;
/* 1084 */           f13 = 1.0F - f13 * f13 * f13 * f13;
/* 1085 */           f14 = 1.0F - f14 * f14 * f14 * f14;
/* 1086 */           f8 = f8 * (1.0F - f16) + f17 * f16;
/* 1087 */           f9 = f9 * (1.0F - f16) + f13 * f16;
/* 1088 */           f10 = f10 * (1.0F - f16) + f14 * f16;
/* 1089 */           f8 = f8 * 0.96F + 0.03F;
/* 1090 */           f9 = f9 * 0.96F + 0.03F;
/* 1091 */           f10 = f10 * 0.96F + 0.03F;
/*      */           
/* 1093 */           if (f8 > 1.0F) {
/* 1094 */             f8 = 1.0F;
/*      */           }
/*      */           
/* 1097 */           if (f9 > 1.0F) {
/* 1098 */             f9 = 1.0F;
/*      */           }
/*      */           
/* 1101 */           if (f10 > 1.0F) {
/* 1102 */             f10 = 1.0F;
/*      */           }
/*      */           
/* 1105 */           if (f8 < 0.0F) {
/* 1106 */             f8 = 0.0F;
/*      */           }
/*      */           
/* 1109 */           if (f9 < 0.0F) {
/* 1110 */             f9 = 0.0F;
/*      */           }
/*      */           
/* 1113 */           if (f10 < 0.0F) {
/* 1114 */             f10 = 0.0F;
/*      */           }
/*      */           
/* 1117 */           int j = 255;
/* 1118 */           int k = (int)(f8 * 255.0F);
/* 1119 */           int l = (int)(f9 * 255.0F);
/* 1120 */           int i1 = (int)(f10 * 255.0F);
/* 1121 */           this.lightmapColors[i] = j << 24 | k << 16 | l << 8 | i1;
/*      */         } 
/*      */         
/* 1124 */         this.lightmapTexture.updateDynamicTexture();
/* 1125 */         this.lightmapUpdateNeeded = false;
/* 1126 */         this.mc.mcProfiler.endSection();
/*      */       } 
/*      */     } 
/*      */   }
/*      */   
/*      */   public float getNightVisionBrightness(EntityLivingBase entitylivingbaseIn, float partialTicks) {
/* 1132 */     int i = entitylivingbaseIn.getActivePotionEffect(Potion.nightVision).getDuration();
/* 1133 */     return (i > 200) ? 1.0F : (0.7F + MathHelper.sin((i - partialTicks) * 3.1415927F * 0.2F) * 0.3F);
/*      */   }
/*      */   
/*      */   public void updateCameraAndRender(float partialTicks, long nanoTime) {
/* 1137 */     Config.renderPartialTicks = partialTicks;
/* 1138 */     frameInit();
/* 1139 */     boolean flag = Display.isActive();
/*      */     
/* 1141 */     if (!flag && this.mc.gameSettings.pauseOnLostFocus && (!this.mc.gameSettings.touchscreen || !Mouse.isButtonDown(1))) {
/* 1142 */       if (Minecraft.getSystemTime() - this.prevFrameTime > 500L) {
/* 1143 */         this.mc.displayInGameMenu();
/*      */       }
/*      */     } else {
/* 1146 */       this.prevFrameTime = Minecraft.getSystemTime();
/*      */     } 
/*      */     
/* 1149 */     this.mc.mcProfiler.startSection("mouse");
/*      */     
/* 1151 */     if (flag && Minecraft.isRunningOnMac && this.mc.inGameHasFocus && !Mouse.isInsideWindow()) {
/* 1152 */       Mouse.setGrabbed(false);
/* 1153 */       Mouse.setCursorPosition(Display.getWidth() / 2, Display.getHeight() / 2);
/* 1154 */       Mouse.setGrabbed(true);
/*      */     } 
/*      */     
/* 1157 */     if (this.mc.inGameHasFocus && flag) {
/* 1158 */       this.mc.mouseHelper.mouseXYChange();
/* 1159 */       float f = this.mc.gameSettings.mouseSensitivity * 0.6F + 0.2F;
/* 1160 */       float f1 = f * f * f * 8.0F;
/* 1161 */       float f2 = this.mc.mouseHelper.deltaX * f1;
/* 1162 */       float f3 = this.mc.mouseHelper.deltaY * f1;
/* 1163 */       int i = 1;
/*      */       
/* 1165 */       if (this.mc.gameSettings.invertMouse) {
/* 1166 */         i = -1;
/*      */       }
/*      */       
/* 1169 */       if (this.mc.gameSettings.debugCamEnable) {
/* 1170 */         this.smoothCamYaw += f2;
/* 1171 */         this.smoothCamPitch += f3;
/* 1172 */         float f4 = partialTicks - this.smoothCamPartialTicks;
/* 1173 */         this.smoothCamPartialTicks = partialTicks;
/* 1174 */         f2 = this.smoothCamFilterX * f4;
/* 1175 */         f3 = this.smoothCamFilterY * f4;
/* 1176 */         this.mc.thePlayer.setAngles(f2, f3 * i);
/*      */       } else {
/* 1178 */         this.smoothCamYaw = 0.0F;
/* 1179 */         this.smoothCamPitch = 0.0F;
/* 1180 */         this.mc.thePlayer.setAngles(f2, f3 * i);
/*      */       } 
/*      */     } 
/*      */     
/* 1184 */     this.mc.mcProfiler.endSection();
/*      */     
/* 1186 */     if (!this.mc.skipRenderWorld) {
/* 1187 */       anaglyphEnable = this.mc.gameSettings.anaglyph;
/* 1188 */       final ScaledResolution scaledresolution = new ScaledResolution(this.mc);
/* 1189 */       int i1 = scaledresolution.getScaledWidth();
/* 1190 */       int j1 = scaledresolution.getScaledHeight();
/* 1191 */       final int k1 = Mouse.getX() * i1 / this.mc.displayWidth;
/* 1192 */       final int l1 = j1 - Mouse.getY() * j1 / this.mc.displayHeight - 1;
/* 1193 */       int i2 = this.mc.gameSettings.limitFramerate;
/*      */       
/* 1195 */       if (this.mc.theWorld != null) {
/* 1196 */         this.mc.mcProfiler.startSection("level");
/* 1197 */         int j = Math.min(Minecraft.getDebugFPS(), i2);
/* 1198 */         j = Math.max(j, 60);
/* 1199 */         long k = System.nanoTime() - nanoTime;
/* 1200 */         long l = Math.max((1000000000 / j / 4) - k, 0L);
/* 1201 */         renderWorld(partialTicks, System.nanoTime() + l);
/*      */         
/* 1203 */         if (OpenGlHelper.shadersSupported) {
/* 1204 */           this.mc.renderGlobal.renderEntityOutlineFramebuffer();
/*      */           
/* 1206 */           if (this.theShaderGroup != null && this.useShader) {
/* 1207 */             GlStateManager.matrixMode(5890);
/* 1208 */             GlStateManager.pushMatrix();
/* 1209 */             GlStateManager.loadIdentity();
/* 1210 */             this.theShaderGroup.loadShaderGroup(partialTicks);
/* 1211 */             GlStateManager.popMatrix();
/*      */           } 
/*      */           
/* 1214 */           this.mc.getFramebuffer().bindFramebuffer(true);
/*      */         } 
/*      */         
/* 1217 */         this.renderEndNanoTime = System.nanoTime();
/* 1218 */         this.mc.mcProfiler.endStartSection("gui");
/*      */         
/* 1220 */         if (!this.mc.gameSettings.thirdPersonView || this.mc.currentScreen != null) {
/* 1221 */           GlStateManager.alphaFunc(516, 0.1F);
/* 1222 */           this.mc.ingameGUI.renderGameOverlay(partialTicks);
/*      */           
/* 1224 */           if (this.mc.gameSettings.ofShowFps && !this.mc.gameSettings.showDebugProfilerChart) {
/* 1225 */             Config.drawFps();
/*      */           }
/*      */           
/* 1228 */           if (this.mc.gameSettings.showDebugProfilerChart) {
/* 1229 */             Lagometer.showLagometer(scaledresolution);
/*      */           }
/*      */         } 
/*      */         
/* 1233 */         this.mc.mcProfiler.endSection();
/*      */       } else {
/* 1235 */         GlStateManager.viewport(0, 0, this.mc.displayWidth, this.mc.displayHeight);
/* 1236 */         GlStateManager.matrixMode(5889);
/* 1237 */         GlStateManager.loadIdentity();
/* 1238 */         GlStateManager.matrixMode(5888);
/* 1239 */         GlStateManager.loadIdentity();
/* 1240 */         setupOverlayRendering();
/* 1241 */         this.renderEndNanoTime = System.nanoTime();
/* 1242 */         TileEntityRendererDispatcher.instance.renderEngine = this.mc.getTextureManager();
/* 1243 */         TileEntityRendererDispatcher.instance.fontRenderer = this.mc.fontRendererObj;
/*      */       } 
/*      */       
/* 1246 */       if (this.mc.currentScreen != null) {
/* 1247 */         GlStateManager.clear(256);
/*      */         
/*      */         try {
/* 1250 */           if (Reflector.ForgeHooksClient_drawScreen.exists()) {
/* 1251 */             Reflector.callVoid(Reflector.ForgeHooksClient_drawScreen, new Object[] { this.mc.currentScreen, Integer.valueOf(k1), Integer.valueOf(l1), Float.valueOf(partialTicks) });
/*      */           } else {
/* 1253 */             this.mc.currentScreen.drawScreen(k1, l1, partialTicks);
/*      */           } 
/* 1255 */         } catch (Throwable throwable) {
/* 1256 */           CrashReport crashreport = CrashReport.makeCrashReport(throwable, "Rendering screen");
/* 1257 */           CrashReportCategory crashreportcategory = crashreport.makeCategory("Screen render details");
/* 1258 */           crashreportcategory.addCrashSectionCallable("Screen name", new Callable<String>() {
/*      */                 public String call() throws Exception {
/* 1260 */                   return EntityRenderer.this.mc.currentScreen.getClass().getCanonicalName();
/*      */                 }
/*      */               });
/* 1263 */           crashreportcategory.addCrashSectionCallable("Mouse location", new Callable<String>() {
/*      */                 public String call() throws Exception {
/* 1265 */                   return String.format("Scaled: (%d, %d). Absolute: (%d, %d)", new Object[] { Integer.valueOf(this.val$k1), Integer.valueOf(this.val$l1), Integer.valueOf(Mouse.getX()), Integer.valueOf(Mouse.getY()) });
/*      */                 }
/*      */               });
/* 1268 */           crashreportcategory.addCrashSectionCallable("Screen size", new Callable<String>() {
/*      */                 public String call() throws Exception {
/* 1270 */                   return String.format("Scaled: (%d, %d). Absolute: (%d, %d). Scale factor of %d", new Object[] { Integer.valueOf(this.val$scaledresolution.getScaledWidth()), Integer.valueOf(this.val$scaledresolution.getScaledHeight()), Integer.valueOf((EntityRenderer.access$0(this.this$0)).displayWidth), Integer.valueOf((EntityRenderer.access$0(this.this$0)).displayHeight), Integer.valueOf(this.val$scaledresolution.getScaleFactor()) });
/*      */                 }
/*      */               });
/* 1273 */           throw new ReportedException(crashreport);
/*      */         } 
/*      */       } 
/*      */     } 
/*      */     
/* 1278 */     frameFinish();
/* 1279 */     waitForServerThread();
/* 1280 */     MemoryMonitor.update();
/* 1281 */     Lagometer.updateLagometer();
/*      */     
/* 1283 */     if (this.mc.gameSettings.ofProfiler) {
/* 1284 */       this.mc.gameSettings.showLagometer = true;
/*      */     }
/*      */   }
/*      */   
/*      */   public void renderStreamIndicator(float partialTicks) {
/* 1289 */     setupOverlayRendering();
/* 1290 */     this.mc.ingameGUI.renderStreamIndicator(new ScaledResolution(this.mc));
/*      */   }
/*      */   
/*      */   private boolean isDrawBlockOutline() {
/* 1294 */     if (!this.drawBlockOutline) {
/* 1295 */       return false;
/*      */     }
/* 1297 */     Entity entity = this.mc.getRenderViewEntity();
/* 1298 */     boolean flag = (entity instanceof EntityPlayer && !this.mc.gameSettings.thirdPersonView);
/*      */     
/* 1300 */     if (flag && !((EntityPlayer)entity).capabilities.allowEdit) {
/* 1301 */       ItemStack itemstack = ((EntityPlayer)entity).getCurrentEquippedItem();
/*      */       
/* 1303 */       if (this.mc.objectMouseOver != null && this.mc.objectMouseOver.typeOfHit == MovingObjectPosition.MovingObjectType.BLOCK) {
/* 1304 */         BlockPos blockpos = this.mc.objectMouseOver.getBlockPos();
/* 1305 */         IBlockState iblockstate = this.mc.theWorld.getBlockState(blockpos);
/* 1306 */         Block block = iblockstate.getBlock();
/*      */         
/* 1308 */         if (this.mc.playerController.getCurrentGameType() == WorldSettings.GameType.SPECTATOR) {
/* 1309 */           flag = (ReflectorForge.blockHasTileEntity(iblockstate) && this.mc.theWorld.getTileEntity(blockpos) instanceof net.minecraft.inventory.IInventory);
/*      */         } else {
/* 1311 */           flag = (itemstack != null && (itemstack.canDestroy(block) || itemstack.canPlaceOn(block)));
/*      */         } 
/*      */       } 
/*      */     } 
/*      */     
/* 1316 */     return flag;
/*      */   }
/*      */ 
/*      */   
/*      */   private void renderWorldDirections(float partialTicks) {
/* 1321 */     if (this.mc.gameSettings.showDebugProfilerChart && !this.mc.gameSettings.thirdPersonView && !this.mc.thePlayer.hasReducedDebug() && !this.mc.gameSettings.reducedDebugInfo) {
/* 1322 */       Entity entity = this.mc.getRenderViewEntity();
/* 1323 */       GlStateManager.enableBlend();
/* 1324 */       GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
/* 1325 */       GL11.glLineWidth(1.0F);
/* 1326 */       GlStateManager.disableTexture2D();
/* 1327 */       GlStateManager.depthMask(false);
/* 1328 */       GlStateManager.pushMatrix();
/* 1329 */       GlStateManager.matrixMode(5888);
/* 1330 */       GlStateManager.loadIdentity();
/* 1331 */       orientCamera(partialTicks);
/* 1332 */       GlStateManager.translate(0.0F, entity.getEyeHeight(), 0.0F);
/* 1333 */       RenderGlobal.drawOutlinedBoundingBox(new AxisAlignedBB(0.0D, 0.0D, 0.0D, 0.005D, 1.0E-4D, 1.0E-4D), 255, 0, 0, 255);
/* 1334 */       RenderGlobal.drawOutlinedBoundingBox(new AxisAlignedBB(0.0D, 0.0D, 0.0D, 1.0E-4D, 1.0E-4D, 0.005D), 0, 0, 255, 255);
/* 1335 */       RenderGlobal.drawOutlinedBoundingBox(new AxisAlignedBB(0.0D, 0.0D, 0.0D, 1.0E-4D, 0.0033D, 1.0E-4D), 0, 255, 0, 255);
/* 1336 */       GlStateManager.popMatrix();
/* 1337 */       GlStateManager.depthMask(true);
/* 1338 */       GlStateManager.enableTexture2D();
/* 1339 */       GlStateManager.disableBlend();
/*      */     } 
/*      */   }
/*      */   
/*      */   public void renderWorld(float partialTicks, long finishTimeNano) {
/* 1344 */     updateLightmap(partialTicks);
/*      */     
/* 1346 */     if (this.mc.getRenderViewEntity() == null) {
/* 1347 */       this.mc.setRenderViewEntity((Entity)this.mc.thePlayer);
/*      */     }
/*      */     
/* 1350 */     getMouseOver(partialTicks);
/*      */     
/* 1352 */     if (Config.isShaders()) {
/* 1353 */       Shaders.beginRender(this.mc, partialTicks, finishTimeNano);
/*      */     }
/*      */     
/* 1356 */     GlStateManager.enableDepth();
/* 1357 */     GlStateManager.enableAlpha();
/* 1358 */     GlStateManager.alphaFunc(516, 0.1F);
/* 1359 */     this.mc.mcProfiler.startSection("center");
/*      */     
/* 1361 */     if (this.mc.gameSettings.anaglyph) {
/* 1362 */       anaglyphField = 0;
/* 1363 */       GlStateManager.colorMask(false, true, true, false);
/* 1364 */       renderWorldPass(0, partialTicks, finishTimeNano);
/* 1365 */       anaglyphField = 1;
/* 1366 */       GlStateManager.colorMask(true, false, false, false);
/* 1367 */       renderWorldPass(1, partialTicks, finishTimeNano);
/* 1368 */       GlStateManager.colorMask(true, true, true, false);
/*      */     } else {
/* 1370 */       renderWorldPass(2, partialTicks, finishTimeNano);
/*      */     } 
/*      */     
/* 1373 */     this.mc.mcProfiler.endSection();
/*      */   }
/*      */   
/*      */   private void renderWorldPass(int pass, float partialTicks, long finishTimeNano) {
/* 1377 */     boolean flag = Config.isShaders();
/*      */     
/* 1379 */     if (flag) {
/* 1380 */       Shaders.beginRenderPass(pass, partialTicks, finishTimeNano);
/*      */     }
/*      */     
/* 1383 */     RenderGlobal renderglobal = this.mc.renderGlobal;
/* 1384 */     EffectRenderer effectrenderer = this.mc.effectRenderer;
/* 1385 */     boolean flag1 = isDrawBlockOutline();
/* 1386 */     GlStateManager.enableCull();
/* 1387 */     this.mc.mcProfiler.endStartSection("clear");
/*      */     
/* 1389 */     if (flag) {
/* 1390 */       Shaders.setViewport(0, 0, this.mc.displayWidth, this.mc.displayHeight);
/*      */     } else {
/* 1392 */       GlStateManager.viewport(0, 0, this.mc.displayWidth, this.mc.displayHeight);
/*      */     } 
/*      */     
/* 1395 */     updateFogColor(partialTicks);
/* 1396 */     GlStateManager.clear(16640);
/*      */     
/* 1398 */     if (flag) {
/* 1399 */       Shaders.clearRenderBuffer();
/*      */     }
/*      */     
/* 1402 */     this.mc.mcProfiler.endStartSection("camera");
/* 1403 */     setupCameraTransform(partialTicks, pass);
/*      */     
/* 1405 */     if (flag) {
/* 1406 */       Shaders.setCamera(partialTicks);
/*      */     }
/*      */     
/* 1409 */     ActiveRenderInfo.updateRenderInfo((EntityPlayer)this.mc.thePlayer, (this.mc.gameSettings.showDebugInfo == 2));
/* 1410 */     this.mc.mcProfiler.endStartSection("frustum");
/* 1411 */     ClippingHelper clippinghelper = ClippingHelperImpl.getInstance();
/* 1412 */     this.mc.mcProfiler.endStartSection("culling");
/* 1413 */     clippinghelper.disabled = (Config.isShaders() && !Shaders.isFrustumCulling());
/* 1414 */     Frustum frustum = new Frustum(clippinghelper);
/* 1415 */     Entity entity = this.mc.getRenderViewEntity();
/* 1416 */     double d0 = entity.lastTickPosX + (entity.posX - entity.lastTickPosX) * partialTicks;
/* 1417 */     double d1 = entity.lastTickPosY + (entity.posY - entity.lastTickPosY) * partialTicks;
/* 1418 */     double d2 = entity.lastTickPosZ + (entity.posZ - entity.lastTickPosZ) * partialTicks;
/*      */     
/* 1420 */     if (flag) {
/* 1421 */       ShadersRender.setFrustrumPosition((ICamera)frustum, d0, d1, d2);
/*      */     } else {
/* 1423 */       frustum.setPosition(d0, d1, d2);
/*      */     } 
/*      */     
/* 1426 */     if ((Config.isSkyEnabled() || Config.isSunMoonEnabled() || Config.isStarsEnabled()) && !Shaders.isShadowPass) {
/* 1427 */       setupFog(-1, partialTicks);
/* 1428 */       this.mc.mcProfiler.endStartSection("sky");
/* 1429 */       GlStateManager.matrixMode(5889);
/* 1430 */       GlStateManager.loadIdentity();
/* 1431 */       Project.gluPerspective(getFOVModifier(partialTicks, true), this.mc.displayWidth / this.mc.displayHeight, 0.05F, this.clipDistance);
/* 1432 */       GlStateManager.matrixMode(5888);
/*      */       
/* 1434 */       if (flag) {
/* 1435 */         Shaders.beginSky();
/*      */       }
/*      */       
/* 1438 */       renderglobal.renderSky(partialTicks, pass);
/*      */       
/* 1440 */       if (flag) {
/* 1441 */         Shaders.endSky();
/*      */       }
/*      */       
/* 1444 */       GlStateManager.matrixMode(5889);
/* 1445 */       GlStateManager.loadIdentity();
/* 1446 */       Project.gluPerspective(getFOVModifier(partialTicks, true), this.mc.displayWidth / this.mc.displayHeight, 0.05F, this.clipDistance);
/* 1447 */       GlStateManager.matrixMode(5888);
/*      */     } else {
/* 1449 */       GlStateManager.disableBlend();
/*      */     } 
/*      */     
/* 1452 */     setupFog(0, partialTicks);
/* 1453 */     GlStateManager.shadeModel(7425);
/*      */     
/* 1455 */     if (entity.posY + entity.getEyeHeight() < 128.0D + (this.mc.gameSettings.ofCloudsHeight * 128.0F)) {
/* 1456 */       renderCloudsCheck(renderglobal, partialTicks, pass);
/*      */     }
/*      */     
/* 1459 */     this.mc.mcProfiler.endStartSection("prepareterrain");
/* 1460 */     setupFog(0, partialTicks);
/* 1461 */     this.mc.getTextureManager().bindTexture(TextureMap.locationBlocksTexture);
/* 1462 */     RenderHelper.disableStandardItemLighting();
/* 1463 */     this.mc.mcProfiler.endStartSection("terrain_setup");
/* 1464 */     checkLoadVisibleChunks(entity, partialTicks, (ICamera)frustum, this.mc.thePlayer.isSpectator());
/*      */     
/* 1466 */     if (flag) {
/* 1467 */       ShadersRender.setupTerrain(renderglobal, entity, partialTicks, (ICamera)frustum, this.frameCount++, this.mc.thePlayer.isSpectator());
/*      */     } else {
/* 1469 */       renderglobal.setupTerrain(entity, partialTicks, (ICamera)frustum, this.frameCount++, this.mc.thePlayer.isSpectator());
/*      */     } 
/*      */     
/* 1472 */     if (pass == 0 || pass == 2) {
/* 1473 */       this.mc.mcProfiler.endStartSection("updatechunks");
/* 1474 */       Lagometer.timerChunkUpload.start();
/* 1475 */       this.mc.renderGlobal.updateChunks(finishTimeNano);
/* 1476 */       Lagometer.timerChunkUpload.end();
/*      */     } 
/*      */     
/* 1479 */     this.mc.mcProfiler.endStartSection("terrain");
/* 1480 */     Lagometer.timerTerrain.start();
/*      */     
/* 1482 */     if (this.mc.gameSettings.ofSmoothFps && pass > 0) {
/* 1483 */       this.mc.mcProfiler.endStartSection("finish");
/* 1484 */       GL11.glFinish();
/* 1485 */       this.mc.mcProfiler.endStartSection("terrain");
/*      */     } 
/*      */     
/* 1488 */     GlStateManager.matrixMode(5888);
/* 1489 */     GlStateManager.pushMatrix();
/* 1490 */     GlStateManager.disableAlpha();
/*      */     
/* 1492 */     if (flag) {
/* 1493 */       ShadersRender.beginTerrainSolid();
/*      */     }
/*      */     
/* 1496 */     renderglobal.renderBlockLayer(EnumWorldBlockLayer.SOLID, partialTicks, pass, entity);
/* 1497 */     GlStateManager.enableAlpha();
/*      */     
/* 1499 */     if (flag) {
/* 1500 */       ShadersRender.beginTerrainCutoutMipped();
/*      */     }
/*      */     
/* 1503 */     this.mc.getTextureManager().getTexture(TextureMap.locationBlocksTexture).setBlurMipmap(false, (this.mc.gameSettings.mipmapLevels > 0));
/* 1504 */     renderglobal.renderBlockLayer(EnumWorldBlockLayer.CUTOUT_MIPPED, partialTicks, pass, entity);
/* 1505 */     this.mc.getTextureManager().getTexture(TextureMap.locationBlocksTexture).restoreLastBlurMipmap();
/* 1506 */     this.mc.getTextureManager().getTexture(TextureMap.locationBlocksTexture).setBlurMipmap(false, false);
/*      */     
/* 1508 */     if (flag) {
/* 1509 */       ShadersRender.beginTerrainCutout();
/*      */     }
/*      */     
/* 1512 */     renderglobal.renderBlockLayer(EnumWorldBlockLayer.CUTOUT, partialTicks, pass, entity);
/* 1513 */     this.mc.getTextureManager().getTexture(TextureMap.locationBlocksTexture).restoreLastBlurMipmap();
/*      */     
/* 1515 */     if (flag) {
/* 1516 */       ShadersRender.endTerrain();
/*      */     }
/*      */     
/* 1519 */     Lagometer.timerTerrain.end();
/* 1520 */     GlStateManager.shadeModel(7424);
/* 1521 */     GlStateManager.alphaFunc(516, 0.1F);
/*      */     
/* 1523 */     if (!this.debugView) {
/* 1524 */       GlStateManager.matrixMode(5888);
/* 1525 */       GlStateManager.popMatrix();
/* 1526 */       GlStateManager.pushMatrix();
/* 1527 */       RenderHelper.enableStandardItemLighting();
/* 1528 */       this.mc.mcProfiler.endStartSection("entities");
/*      */       
/* 1530 */       if (Reflector.ForgeHooksClient_setRenderPass.exists()) {
/* 1531 */         Reflector.callVoid(Reflector.ForgeHooksClient_setRenderPass, new Object[] { Integer.valueOf(0) });
/*      */       }
/*      */       
/* 1534 */       renderglobal.renderEntities(entity, (ICamera)frustum, partialTicks);
/*      */       
/* 1536 */       if (Reflector.ForgeHooksClient_setRenderPass.exists()) {
/* 1537 */         Reflector.callVoid(Reflector.ForgeHooksClient_setRenderPass, new Object[] { Integer.valueOf(-1) });
/*      */       }
/*      */       
/* 1540 */       RenderHelper.disableStandardItemLighting();
/* 1541 */       disableLightmap();
/* 1542 */       GlStateManager.matrixMode(5888);
/* 1543 */       GlStateManager.popMatrix();
/* 1544 */       GlStateManager.pushMatrix();
/*      */       
/* 1546 */       if (this.mc.objectMouseOver != null && entity.isInsideOfMaterial(Material.water) && flag1) {
/* 1547 */         EntityPlayer entityplayer = (EntityPlayer)entity;
/* 1548 */         GlStateManager.disableAlpha();
/* 1549 */         this.mc.mcProfiler.endStartSection("outline");
/* 1550 */         renderglobal.drawSelectionBox(entityplayer, this.mc.objectMouseOver, 0, partialTicks);
/* 1551 */         GlStateManager.enableAlpha();
/*      */       } 
/*      */     } 
/*      */     
/* 1555 */     GlStateManager.matrixMode(5888);
/* 1556 */     GlStateManager.popMatrix();
/*      */     
/* 1558 */     if (flag1 && this.mc.objectMouseOver != null && !entity.isInsideOfMaterial(Material.water)) {
/* 1559 */       EntityPlayer entityplayer1 = (EntityPlayer)entity;
/* 1560 */       GlStateManager.disableAlpha();
/* 1561 */       this.mc.mcProfiler.endStartSection("outline");
/*      */       
/* 1563 */       if ((!Reflector.ForgeHooksClient_onDrawBlockHighlight.exists() || !Reflector.callBoolean(Reflector.ForgeHooksClient_onDrawBlockHighlight, new Object[] { renderglobal, entityplayer1, this.mc.objectMouseOver, Integer.valueOf(0), entityplayer1.getHeldItem(), Float.valueOf(partialTicks) })) && !this.mc.gameSettings.thirdPersonView) {
/* 1564 */         renderglobal.drawSelectionBox(entityplayer1, this.mc.objectMouseOver, 0, partialTicks);
/*      */       }
/* 1566 */       GlStateManager.enableAlpha();
/*      */     } 
/*      */     
/* 1569 */     if (!renderglobal.damagedBlocks.isEmpty()) {
/* 1570 */       this.mc.mcProfiler.endStartSection("destroyProgress");
/* 1571 */       GlStateManager.enableBlend();
/* 1572 */       GlStateManager.tryBlendFuncSeparate(770, 1, 1, 0);
/* 1573 */       this.mc.getTextureManager().getTexture(TextureMap.locationBlocksTexture).setBlurMipmap(false, false);
/* 1574 */       renderglobal.drawBlockDamageTexture(Tessellator.getInstance(), Tessellator.getInstance().getWorldRenderer(), entity, partialTicks);
/* 1575 */       this.mc.getTextureManager().getTexture(TextureMap.locationBlocksTexture).restoreLastBlurMipmap();
/* 1576 */       GlStateManager.disableBlend();
/*      */     } 
/*      */     
/* 1579 */     GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
/* 1580 */     GlStateManager.disableBlend();
/*      */     
/* 1582 */     if (!this.debugView) {
/* 1583 */       enableLightmap();
/* 1584 */       this.mc.mcProfiler.endStartSection("litParticles");
/*      */       
/* 1586 */       if (flag) {
/* 1587 */         Shaders.beginLitParticles();
/*      */       }
/*      */       
/* 1590 */       effectrenderer.renderLitParticles(entity, partialTicks);
/* 1591 */       RenderHelper.disableStandardItemLighting();
/* 1592 */       setupFog(0, partialTicks);
/* 1593 */       this.mc.mcProfiler.endStartSection("particles");
/*      */       
/* 1595 */       if (flag) {
/* 1596 */         Shaders.beginParticles();
/*      */       }
/*      */       
/* 1599 */       effectrenderer.renderParticles(entity, partialTicks);
/*      */       
/* 1601 */       if (flag) {
/* 1602 */         Shaders.endParticles();
/*      */       }
/*      */       
/* 1605 */       disableLightmap();
/*      */     } 
/*      */     
/* 1608 */     GlStateManager.depthMask(false);
/*      */     
/* 1610 */     if (Config.isShaders()) {
/* 1611 */       GlStateManager.depthMask(Shaders.isRainDepth());
/*      */     }
/*      */     
/* 1614 */     GlStateManager.enableCull();
/* 1615 */     this.mc.mcProfiler.endStartSection("weather");
/*      */     
/* 1617 */     if (flag) {
/* 1618 */       Shaders.beginWeather();
/*      */     }
/*      */     
/* 1621 */     renderRainSnow(partialTicks);
/*      */     
/* 1623 */     if (flag) {
/* 1624 */       Shaders.endWeather();
/*      */     }
/*      */     
/* 1627 */     GlStateManager.depthMask(true);
/* 1628 */     renderglobal.renderWorldBorder(entity, partialTicks);
/*      */     
/* 1630 */     if (flag) {
/* 1631 */       ShadersRender.renderHand0(this, partialTicks, pass);
/* 1632 */       Shaders.preWater();
/*      */     } 
/*      */     
/* 1635 */     GlStateManager.disableBlend();
/* 1636 */     GlStateManager.enableCull();
/* 1637 */     GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
/* 1638 */     GlStateManager.alphaFunc(516, 0.1F);
/* 1639 */     setupFog(0, partialTicks);
/* 1640 */     GlStateManager.enableBlend();
/* 1641 */     GlStateManager.depthMask(false);
/* 1642 */     this.mc.getTextureManager().bindTexture(TextureMap.locationBlocksTexture);
/* 1643 */     GlStateManager.shadeModel(7425);
/* 1644 */     this.mc.mcProfiler.endStartSection("translucent");
/*      */     
/* 1646 */     if (flag) {
/* 1647 */       Shaders.beginWater();
/*      */     }
/*      */     
/* 1650 */     renderglobal.renderBlockLayer(EnumWorldBlockLayer.TRANSLUCENT, partialTicks, pass, entity);
/*      */     
/* 1652 */     if (flag) {
/* 1653 */       Shaders.endWater();
/*      */     }
/*      */     
/* 1656 */     if (Reflector.ForgeHooksClient_setRenderPass.exists() && !this.debugView) {
/* 1657 */       RenderHelper.enableStandardItemLighting();
/* 1658 */       this.mc.mcProfiler.endStartSection("entities");
/* 1659 */       Reflector.callVoid(Reflector.ForgeHooksClient_setRenderPass, new Object[] { Integer.valueOf(1) });
/* 1660 */       this.mc.renderGlobal.renderEntities(entity, (ICamera)frustum, partialTicks);
/* 1661 */       GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
/* 1662 */       Reflector.callVoid(Reflector.ForgeHooksClient_setRenderPass, new Object[] { Integer.valueOf(-1) });
/* 1663 */       RenderHelper.disableStandardItemLighting();
/*      */     } 
/*      */     
/* 1666 */     GlStateManager.shadeModel(7424);
/* 1667 */     GlStateManager.depthMask(true);
/* 1668 */     GlStateManager.enableCull();
/* 1669 */     GlStateManager.disableBlend();
/* 1670 */     GlStateManager.disableFog();
/*      */     
/* 1672 */     if (entity.posY + entity.getEyeHeight() >= 128.0D + (this.mc.gameSettings.ofCloudsHeight * 128.0F)) {
/* 1673 */       this.mc.mcProfiler.endStartSection("aboveClouds");
/* 1674 */       renderCloudsCheck(renderglobal, partialTicks, pass);
/*      */     } 
/*      */     
/* 1677 */     if (Reflector.ForgeHooksClient_dispatchRenderLast.exists()) {
/* 1678 */       this.mc.mcProfiler.endStartSection("forge_render_last");
/* 1679 */       Reflector.callVoid(Reflector.ForgeHooksClient_dispatchRenderLast, new Object[] { renderglobal, Float.valueOf(partialTicks) });
/*      */     } 
/*      */     
/* 1682 */     this.mc.mcProfiler.endStartSection("hand");
/*      */     
/* 1684 */     if (this.renderHand && !Shaders.isShadowPass) {
/* 1685 */       if (flag) {
/* 1686 */         ShadersRender.renderHand1(this, partialTicks, pass);
/* 1687 */         Shaders.renderCompositeFinal();
/*      */       } 
/*      */       
/* 1690 */       GlStateManager.clear(256);
/*      */       
/* 1692 */       if (flag) {
/* 1693 */         ShadersRender.renderFPOverlay(this, partialTicks, pass);
/*      */       } else {
/* 1695 */         renderHand(partialTicks, pass);
/*      */       } 
/*      */       
/* 1698 */       renderWorldDirections(partialTicks);
/*      */     } 
/*      */     
/* 1701 */     if (flag) {
/* 1702 */       Shaders.endRender();
/*      */     }
/*      */   }
/*      */   
/*      */   private void renderCloudsCheck(RenderGlobal renderGlobalIn, float partialTicks, int pass) {
/* 1707 */     if (this.mc.gameSettings.renderDistanceChunks >= 4 && !Config.isCloudsOff() && Shaders.shouldRenderClouds(this.mc.gameSettings)) {
/* 1708 */       this.mc.mcProfiler.endStartSection("clouds");
/* 1709 */       GlStateManager.matrixMode(5889);
/* 1710 */       GlStateManager.loadIdentity();
/* 1711 */       Project.gluPerspective(getFOVModifier(partialTicks, true), this.mc.displayWidth / this.mc.displayHeight, 0.05F, this.clipDistance * 4.0F);
/* 1712 */       GlStateManager.matrixMode(5888);
/* 1713 */       GlStateManager.pushMatrix();
/* 1714 */       setupFog(0, partialTicks);
/* 1715 */       renderGlobalIn.renderClouds(partialTicks, pass);
/* 1716 */       GlStateManager.disableFog();
/* 1717 */       GlStateManager.popMatrix();
/* 1718 */       GlStateManager.matrixMode(5889);
/* 1719 */       GlStateManager.loadIdentity();
/* 1720 */       Project.gluPerspective(getFOVModifier(partialTicks, true), this.mc.displayWidth / this.mc.displayHeight, 0.05F, this.clipDistance);
/* 1721 */       GlStateManager.matrixMode(5888);
/*      */     } 
/*      */   }
/*      */   
/*      */   private void addRainParticles() {
/* 1726 */     float f = this.mc.theWorld.getRainStrength(1.0F);
/*      */     
/* 1728 */     if (!Config.isRainFancy()) {
/* 1729 */       f /= 2.0F;
/*      */     }
/*      */     
/* 1732 */     if (f != 0.0F && Config.isRainSplash()) {
/* 1733 */       this.random.setSeed(this.rendererUpdateCount * 312987231L);
/* 1734 */       Entity entity = this.mc.getRenderViewEntity();
/* 1735 */       WorldClient worldClient = this.mc.theWorld;
/* 1736 */       BlockPos blockpos = new BlockPos(entity);
/* 1737 */       int i = 10;
/* 1738 */       double d0 = 0.0D;
/* 1739 */       double d1 = 0.0D;
/* 1740 */       double d2 = 0.0D;
/* 1741 */       int j = 0;
/* 1742 */       int k = (int)(100.0F * f * f);
/*      */       
/* 1744 */       if (this.mc.gameSettings.language == 1) {
/* 1745 */         k >>= 1;
/* 1746 */       } else if (this.mc.gameSettings.language == 2) {
/* 1747 */         k = 0;
/*      */       } 
/*      */       
/* 1750 */       for (int l = 0; l < k; l++) {
/* 1751 */         BlockPos blockpos1 = worldClient.getPrecipitationHeight(blockpos.add(this.random.nextInt(i) - this.random.nextInt(i), 0, this.random.nextInt(i) - this.random.nextInt(i)));
/* 1752 */         BiomeGenBase biomegenbase = worldClient.getBiomeGenForCoords(blockpos1);
/* 1753 */         BlockPos blockpos2 = blockpos1.down();
/* 1754 */         Block block = worldClient.getBlockState(blockpos2).getBlock();
/*      */         
/* 1756 */         if (blockpos1.getY() <= blockpos.getY() + i && blockpos1.getY() >= blockpos.getY() - i && biomegenbase.canRain() && biomegenbase.getFloatTemperature(blockpos1) >= 0.15F) {
/* 1757 */           double d3 = this.random.nextDouble();
/* 1758 */           double d4 = this.random.nextDouble();
/*      */           
/* 1760 */           if (block.getMaterial() == Material.lava) {
/* 1761 */             this.mc.theWorld.spawnParticle(EnumParticleTypes.SMOKE_NORMAL, blockpos1.getX() + d3, (blockpos1.getY() + 0.1F) - block.getBlockBoundsMinY(), blockpos1.getZ() + d4, 0.0D, 0.0D, 0.0D, new int[0]);
/* 1762 */           } else if (block.getMaterial() != Material.air) {
/* 1763 */             block.setBlockBoundsBasedOnState((IBlockAccess)worldClient, blockpos2);
/* 1764 */             j++;
/*      */             
/* 1766 */             if (this.random.nextInt(j) == 0) {
/* 1767 */               d0 = blockpos2.getX() + d3;
/* 1768 */               d1 = (blockpos2.getY() + 0.1F) + block.getBlockBoundsMaxY() - 1.0D;
/* 1769 */               d2 = blockpos2.getZ() + d4;
/*      */             } 
/*      */             
/* 1772 */             this.mc.theWorld.spawnParticle(EnumParticleTypes.WATER_DROP, blockpos2.getX() + d3, (blockpos2.getY() + 0.1F) + block.getBlockBoundsMaxY(), blockpos2.getZ() + d4, 0.0D, 0.0D, 0.0D, new int[0]);
/*      */           } 
/*      */         } 
/*      */       } 
/*      */       
/* 1777 */       if (j > 0 && this.random.nextInt(3) < this.rainSoundCounter++) {
/* 1778 */         this.rainSoundCounter = 0;
/*      */         
/* 1780 */         if (d1 > (blockpos.getY() + 1) && worldClient.getPrecipitationHeight(blockpos).getY() > MathHelper.floor_float(blockpos.getY())) {
/* 1781 */           this.mc.theWorld.playSound(d0, d1, d2, "ambient.weather.rain", 0.1F, 0.5F, false);
/*      */         } else {
/* 1783 */           this.mc.theWorld.playSound(d0, d1, d2, "ambient.weather.rain", 0.2F, 1.0F, false);
/*      */         } 
/*      */       } 
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void renderRainSnow(float partialTicks) {
/* 1793 */     if (Reflector.ForgeWorldProvider_getWeatherRenderer.exists()) {
/* 1794 */       WorldProvider worldprovider = this.mc.theWorld.provider;
/* 1795 */       Object object = Reflector.call(worldprovider, Reflector.ForgeWorldProvider_getWeatherRenderer, new Object[0]);
/*      */       
/* 1797 */       if (object != null) {
/* 1798 */         Reflector.callVoid(object, Reflector.IRenderHandler_render, new Object[] { Float.valueOf(partialTicks), this.mc.theWorld, this.mc });
/*      */         
/*      */         return;
/*      */       } 
/*      */     } 
/* 1803 */     float f5 = this.mc.theWorld.getRainStrength(partialTicks);
/*      */     
/* 1805 */     if (f5 > 0.0F) {
/* 1806 */       if (Config.isRainOff()) {
/*      */         return;
/*      */       }
/*      */       
/* 1810 */       enableLightmap();
/* 1811 */       Entity entity = this.mc.getRenderViewEntity();
/* 1812 */       WorldClient worldClient = this.mc.theWorld;
/* 1813 */       int i = MathHelper.floor_double(entity.posX);
/* 1814 */       int j = MathHelper.floor_double(entity.posY);
/* 1815 */       int k = MathHelper.floor_double(entity.posZ);
/* 1816 */       Tessellator tessellator = Tessellator.getInstance();
/* 1817 */       WorldRenderer worldrenderer = tessellator.getWorldRenderer();
/* 1818 */       GlStateManager.disableCull();
/* 1819 */       GL11.glNormal3f(0.0F, 1.0F, 0.0F);
/* 1820 */       GlStateManager.enableBlend();
/* 1821 */       GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
/* 1822 */       GlStateManager.alphaFunc(516, 0.1F);
/* 1823 */       double d0 = entity.lastTickPosX + (entity.posX - entity.lastTickPosX) * partialTicks;
/* 1824 */       double d1 = entity.lastTickPosY + (entity.posY - entity.lastTickPosY) * partialTicks;
/* 1825 */       double d2 = entity.lastTickPosZ + (entity.posZ - entity.lastTickPosZ) * partialTicks;
/* 1826 */       int l = MathHelper.floor_double(d1);
/* 1827 */       int i1 = 5;
/*      */       
/* 1829 */       if (Config.isRainFancy()) {
/* 1830 */         i1 = 10;
/*      */       }
/*      */       
/* 1833 */       int j1 = -1;
/* 1834 */       float f = this.rendererUpdateCount + partialTicks;
/* 1835 */       worldrenderer.setTranslation(-d0, -d1, -d2);
/* 1836 */       GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
/* 1837 */       BlockPos.MutableBlockPos blockpos$mutableblockpos = new BlockPos.MutableBlockPos();
/*      */       
/* 1839 */       for (int k1 = k - i1; k1 <= k + i1; k1++) {
/* 1840 */         for (int l1 = i - i1; l1 <= i + i1; l1++) {
/* 1841 */           int i2 = (k1 - k + 16) * 32 + l1 - i + 16;
/* 1842 */           double d3 = this.rainXCoords[i2] * 0.5D;
/* 1843 */           double d4 = this.rainYCoords[i2] * 0.5D;
/* 1844 */           blockpos$mutableblockpos.set(l1, 0, k1);
/* 1845 */           BiomeGenBase biomegenbase = worldClient.getBiomeGenForCoords((BlockPos)blockpos$mutableblockpos);
/*      */           
/* 1847 */           if (biomegenbase.canRain() || biomegenbase.getEnableSnow()) {
/* 1848 */             int j2 = worldClient.getPrecipitationHeight((BlockPos)blockpos$mutableblockpos).getY();
/* 1849 */             int k2 = j - i1;
/* 1850 */             int l2 = j + i1;
/*      */             
/* 1852 */             if (k2 < j2) {
/* 1853 */               k2 = j2;
/*      */             }
/*      */             
/* 1856 */             if (l2 < j2) {
/* 1857 */               l2 = j2;
/*      */             }
/*      */             
/* 1860 */             int i3 = j2;
/*      */             
/* 1862 */             if (j2 < l) {
/* 1863 */               i3 = l;
/*      */             }
/*      */             
/* 1866 */             if (k2 != l2) {
/* 1867 */               this.random.setSeed((l1 * l1 * 3121 + l1 * 45238971 ^ k1 * k1 * 418711 + k1 * 13761));
/* 1868 */               blockpos$mutableblockpos.set(l1, k2, k1);
/* 1869 */               float f1 = biomegenbase.getFloatTemperature((BlockPos)blockpos$mutableblockpos);
/*      */               
/* 1871 */               if (worldClient.getWorldChunkManager().getTemperatureAtHeight(f1, j2) >= 0.15F) {
/* 1872 */                 if (j1 != 0) {
/* 1873 */                   if (j1 >= 0) {
/* 1874 */                     tessellator.draw();
/*      */                   }
/*      */                   
/* 1877 */                   j1 = 0;
/* 1878 */                   this.mc.getTextureManager().bindTexture(locationRainPng);
/* 1879 */                   worldrenderer.begin(7, DefaultVertexFormats.PARTICLE_POSITION_TEX_COLOR_LMAP);
/*      */                 } 
/*      */                 
/* 1882 */                 double d5 = ((this.rendererUpdateCount + l1 * l1 * 3121 + l1 * 45238971 + k1 * k1 * 418711 + k1 * 13761 & 0x1F) + partialTicks) / 32.0D * (3.0D + this.random.nextDouble());
/* 1883 */                 double d6 = (l1 + 0.5F) - entity.posX;
/* 1884 */                 double d7 = (k1 + 0.5F) - entity.posZ;
/* 1885 */                 float f2 = MathHelper.sqrt_double(d6 * d6 + d7 * d7) / i1;
/* 1886 */                 float f3 = ((1.0F - f2 * f2) * 0.5F + 0.5F) * f5;
/* 1887 */                 blockpos$mutableblockpos.set(l1, i3, k1);
/* 1888 */                 int j3 = worldClient.getCombinedLight((BlockPos)blockpos$mutableblockpos, 0);
/* 1889 */                 int k3 = j3 >> 16 & 0xFFFF;
/* 1890 */                 int l3 = j3 & 0xFFFF;
/* 1891 */                 worldrenderer.pos(l1 - d3 + 0.5D, k2, k1 - d4 + 0.5D).tex(0.0D, k2 * 0.25D + d5).color(1.0F, 1.0F, 1.0F, f3).lightmap(k3, l3).endVertex();
/* 1892 */                 worldrenderer.pos(l1 + d3 + 0.5D, k2, k1 + d4 + 0.5D).tex(1.0D, k2 * 0.25D + d5).color(1.0F, 1.0F, 1.0F, f3).lightmap(k3, l3).endVertex();
/* 1893 */                 worldrenderer.pos(l1 + d3 + 0.5D, l2, k1 + d4 + 0.5D).tex(1.0D, l2 * 0.25D + d5).color(1.0F, 1.0F, 1.0F, f3).lightmap(k3, l3).endVertex();
/* 1894 */                 worldrenderer.pos(l1 - d3 + 0.5D, l2, k1 - d4 + 0.5D).tex(0.0D, l2 * 0.25D + d5).color(1.0F, 1.0F, 1.0F, f3).lightmap(k3, l3).endVertex();
/*      */               } else {
/* 1896 */                 if (j1 != 1) {
/* 1897 */                   if (j1 >= 0) {
/* 1898 */                     tessellator.draw();
/*      */                   }
/*      */                   
/* 1901 */                   j1 = 1;
/* 1902 */                   this.mc.getTextureManager().bindTexture(locationSnowPng);
/* 1903 */                   worldrenderer.begin(7, DefaultVertexFormats.PARTICLE_POSITION_TEX_COLOR_LMAP);
/*      */                 } 
/*      */                 
/* 1906 */                 double d8 = (((this.rendererUpdateCount & 0x1FF) + partialTicks) / 512.0F);
/* 1907 */                 double d9 = this.random.nextDouble() + f * 0.01D * (float)this.random.nextGaussian();
/* 1908 */                 double d10 = this.random.nextDouble() + (f * (float)this.random.nextGaussian()) * 0.001D;
/* 1909 */                 double d11 = (l1 + 0.5F) - entity.posX;
/* 1910 */                 double d12 = (k1 + 0.5F) - entity.posZ;
/* 1911 */                 float f6 = MathHelper.sqrt_double(d11 * d11 + d12 * d12) / i1;
/* 1912 */                 float f4 = ((1.0F - f6 * f6) * 0.3F + 0.5F) * f5;
/* 1913 */                 blockpos$mutableblockpos.set(l1, i3, k1);
/* 1914 */                 int i4 = (worldClient.getCombinedLight((BlockPos)blockpos$mutableblockpos, 0) * 3 + 15728880) / 4;
/* 1915 */                 int j4 = i4 >> 16 & 0xFFFF;
/* 1916 */                 int k4 = i4 & 0xFFFF;
/* 1917 */                 worldrenderer.pos(l1 - d3 + 0.5D, k2, k1 - d4 + 0.5D).tex(0.0D + d9, k2 * 0.25D + d8 + d10).color(1.0F, 1.0F, 1.0F, f4).lightmap(j4, k4).endVertex();
/* 1918 */                 worldrenderer.pos(l1 + d3 + 0.5D, k2, k1 + d4 + 0.5D).tex(1.0D + d9, k2 * 0.25D + d8 + d10).color(1.0F, 1.0F, 1.0F, f4).lightmap(j4, k4).endVertex();
/* 1919 */                 worldrenderer.pos(l1 + d3 + 0.5D, l2, k1 + d4 + 0.5D).tex(1.0D + d9, l2 * 0.25D + d8 + d10).color(1.0F, 1.0F, 1.0F, f4).lightmap(j4, k4).endVertex();
/* 1920 */                 worldrenderer.pos(l1 - d3 + 0.5D, l2, k1 - d4 + 0.5D).tex(0.0D + d9, l2 * 0.25D + d8 + d10).color(1.0F, 1.0F, 1.0F, f4).lightmap(j4, k4).endVertex();
/*      */               } 
/*      */             } 
/*      */           } 
/*      */         } 
/*      */       } 
/*      */       
/* 1927 */       if (j1 >= 0) {
/* 1928 */         tessellator.draw();
/*      */       }
/*      */       
/* 1931 */       worldrenderer.setTranslation(0.0D, 0.0D, 0.0D);
/* 1932 */       GlStateManager.enableCull();
/* 1933 */       GlStateManager.disableBlend();
/* 1934 */       GlStateManager.alphaFunc(516, 0.1F);
/* 1935 */       disableLightmap();
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setupOverlayRendering() {
/* 1943 */     ScaledResolution scaledresolution = new ScaledResolution(this.mc);
/* 1944 */     GlStateManager.clear(256);
/* 1945 */     GlStateManager.matrixMode(5889);
/* 1946 */     GlStateManager.loadIdentity();
/* 1947 */     GlStateManager.ortho(0.0D, scaledresolution.getScaledWidth_double(), scaledresolution.getScaledHeight_double(), 0.0D, 1000.0D, 3000.0D);
/* 1948 */     GlStateManager.matrixMode(5888);
/* 1949 */     GlStateManager.loadIdentity();
/* 1950 */     GlStateManager.translate(0.0F, 0.0F, -2000.0F);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void updateFogColor(float partialTicks) {
/* 1957 */     WorldClient worldClient = this.mc.theWorld;
/* 1958 */     Entity entity = this.mc.getRenderViewEntity();
/* 1959 */     float f = 0.25F + 0.75F * this.mc.gameSettings.renderDistanceChunks / 32.0F;
/* 1960 */     f = 1.0F - (float)Math.pow(f, 0.25D);
/* 1961 */     Vec3 vec3 = worldClient.getSkyColor(this.mc.getRenderViewEntity(), partialTicks);
/* 1962 */     vec3 = CustomColors.getWorldSkyColor(vec3, (World)worldClient, this.mc.getRenderViewEntity(), partialTicks);
/* 1963 */     float f1 = (float)vec3.xCoord;
/* 1964 */     float f2 = (float)vec3.yCoord;
/* 1965 */     float f3 = (float)vec3.zCoord;
/* 1966 */     Vec3 vec31 = worldClient.getFogColor(partialTicks);
/* 1967 */     vec31 = CustomColors.getWorldFogColor(vec31, (World)worldClient, this.mc.getRenderViewEntity(), partialTicks);
/* 1968 */     this.fogColorRed = (float)vec31.xCoord;
/* 1969 */     this.fogColorGreen = (float)vec31.yCoord;
/* 1970 */     this.fogColorBlue = (float)vec31.zCoord;
/*      */     
/* 1972 */     if (this.mc.gameSettings.renderDistanceChunks >= 4) {
/* 1973 */       double d0 = -1.0D;
/* 1974 */       Vec3 vec32 = (MathHelper.sin(worldClient.getCelestialAngleRadians(partialTicks)) > 0.0F) ? new Vec3(d0, 0.0D, 0.0D) : new Vec3(1.0D, 0.0D, 0.0D);
/* 1975 */       float f5 = (float)entity.getLook(partialTicks).dotProduct(vec32);
/*      */       
/* 1977 */       if (f5 < 0.0F) {
/* 1978 */         f5 = 0.0F;
/*      */       }
/*      */       
/* 1981 */       if (f5 > 0.0F) {
/* 1982 */         float[] afloat = ((World)worldClient).provider.calcSunriseSunsetColors(worldClient.getCelestialAngle(partialTicks), partialTicks);
/*      */         
/* 1984 */         if (afloat != null) {
/* 1985 */           f5 *= afloat[3];
/* 1986 */           this.fogColorRed = this.fogColorRed * (1.0F - f5) + afloat[0] * f5;
/* 1987 */           this.fogColorGreen = this.fogColorGreen * (1.0F - f5) + afloat[1] * f5;
/* 1988 */           this.fogColorBlue = this.fogColorBlue * (1.0F - f5) + afloat[2] * f5;
/*      */         } 
/*      */       } 
/*      */     } 
/*      */     
/* 1993 */     this.fogColorRed += (f1 - this.fogColorRed) * f;
/* 1994 */     this.fogColorGreen += (f2 - this.fogColorGreen) * f;
/* 1995 */     this.fogColorBlue += (f3 - this.fogColorBlue) * f;
/* 1996 */     float f8 = worldClient.getRainStrength(partialTicks);
/*      */     
/* 1998 */     if (f8 > 0.0F) {
/* 1999 */       float f4 = 1.0F - f8 * 0.5F;
/* 2000 */       float f10 = 1.0F - f8 * 0.4F;
/* 2001 */       this.fogColorRed *= f4;
/* 2002 */       this.fogColorGreen *= f4;
/* 2003 */       this.fogColorBlue *= f10;
/*      */     } 
/*      */     
/* 2006 */     float f9 = worldClient.getThunderStrength(partialTicks);
/*      */     
/* 2008 */     if (f9 > 0.0F) {
/* 2009 */       float f11 = 1.0F - f9 * 0.5F;
/* 2010 */       this.fogColorRed *= f11;
/* 2011 */       this.fogColorGreen *= f11;
/* 2012 */       this.fogColorBlue *= f11;
/*      */     } 
/*      */     
/* 2015 */     Block block = ActiveRenderInfo.getBlockAtEntityViewpoint((World)this.mc.theWorld, entity, partialTicks);
/*      */     
/* 2017 */     if (this.cloudFog) {
/* 2018 */       Vec3 vec33 = worldClient.getCloudColour(partialTicks);
/* 2019 */       this.fogColorRed = (float)vec33.xCoord;
/* 2020 */       this.fogColorGreen = (float)vec33.yCoord;
/* 2021 */       this.fogColorBlue = (float)vec33.zCoord;
/* 2022 */     } else if (block.getMaterial() == Material.water) {
/* 2023 */       float f12 = EnchantmentHelper.getRespiration(entity) * 0.2F;
/* 2024 */       f12 = Config.limit(f12, 0.0F, 0.6F);
/*      */       
/* 2026 */       if (entity instanceof EntityLivingBase && ((EntityLivingBase)entity).isPotionActive(Potion.waterBreathing)) {
/* 2027 */         f12 = f12 * 0.3F + 0.6F;
/*      */       }
/*      */       
/* 2030 */       this.fogColorRed = 0.02F + f12;
/* 2031 */       this.fogColorGreen = 0.02F + f12;
/* 2032 */       this.fogColorBlue = 0.2F + f12;
/* 2033 */       Vec3 vec35 = CustomColors.getUnderwaterColor((IBlockAccess)this.mc.theWorld, (this.mc.getRenderViewEntity()).posX, (this.mc.getRenderViewEntity()).posY + 1.0D, (this.mc.getRenderViewEntity()).posZ);
/*      */       
/* 2035 */       if (vec35 != null) {
/* 2036 */         this.fogColorRed = (float)vec35.xCoord;
/* 2037 */         this.fogColorGreen = (float)vec35.yCoord;
/* 2038 */         this.fogColorBlue = (float)vec35.zCoord;
/*      */       } 
/* 2040 */     } else if (block.getMaterial() == Material.lava) {
/* 2041 */       this.fogColorRed = 0.6F;
/* 2042 */       this.fogColorGreen = 0.1F;
/* 2043 */       this.fogColorBlue = 0.0F;
/* 2044 */       Vec3 vec34 = CustomColors.getUnderlavaColor((IBlockAccess)this.mc.theWorld, (this.mc.getRenderViewEntity()).posX, (this.mc.getRenderViewEntity()).posY + 1.0D, (this.mc.getRenderViewEntity()).posZ);
/*      */       
/* 2046 */       if (vec34 != null) {
/* 2047 */         this.fogColorRed = (float)vec34.xCoord;
/* 2048 */         this.fogColorGreen = (float)vec34.yCoord;
/* 2049 */         this.fogColorBlue = (float)vec34.zCoord;
/*      */       } 
/*      */     } 
/*      */     
/* 2053 */     float f13 = this.fogColor2 + (this.fogColor1 - this.fogColor2) * partialTicks;
/* 2054 */     this.fogColorRed *= f13;
/* 2055 */     this.fogColorGreen *= f13;
/* 2056 */     this.fogColorBlue *= f13;
/* 2057 */     double d1 = (entity.lastTickPosY + (entity.posY - entity.lastTickPosY) * partialTicks) * ((World)worldClient).provider.getVoidFogYFactor();
/*      */     
/* 2059 */     if (entity instanceof EntityLivingBase && ((EntityLivingBase)entity).isPotionActive(Potion.blindness)) {
/* 2060 */       int i = ((EntityLivingBase)entity).getActivePotionEffect(Potion.blindness).getDuration();
/*      */       
/* 2062 */       if (i < 20) {
/* 2063 */         d1 *= (1.0F - i / 20.0F);
/*      */       } else {
/* 2065 */         d1 = 0.0D;
/*      */       } 
/*      */     } 
/*      */     
/* 2069 */     if (d1 < 1.0D) {
/* 2070 */       if (d1 < 0.0D) {
/* 2071 */         d1 = 0.0D;
/*      */       }
/*      */       
/* 2074 */       d1 *= d1;
/* 2075 */       this.fogColorRed = (float)(this.fogColorRed * d1);
/* 2076 */       this.fogColorGreen = (float)(this.fogColorGreen * d1);
/* 2077 */       this.fogColorBlue = (float)(this.fogColorBlue * d1);
/*      */     } 
/*      */     
/* 2080 */     if (this.bossColorModifier > 0.0F) {
/* 2081 */       float f14 = this.bossColorModifierPrev + (this.bossColorModifier - this.bossColorModifierPrev) * partialTicks;
/* 2082 */       this.fogColorRed = this.fogColorRed * (1.0F - f14) + this.fogColorRed * 0.7F * f14;
/* 2083 */       this.fogColorGreen = this.fogColorGreen * (1.0F - f14) + this.fogColorGreen * 0.6F * f14;
/* 2084 */       this.fogColorBlue = this.fogColorBlue * (1.0F - f14) + this.fogColorBlue * 0.6F * f14;
/*      */     } 
/*      */     
/* 2087 */     if (entity instanceof EntityLivingBase && ((EntityLivingBase)entity).isPotionActive(Potion.nightVision)) {
/* 2088 */       float f15 = getNightVisionBrightness((EntityLivingBase)entity, partialTicks);
/* 2089 */       float f6 = 1.0F / this.fogColorRed;
/*      */       
/* 2091 */       if (f6 > 1.0F / this.fogColorGreen) {
/* 2092 */         f6 = 1.0F / this.fogColorGreen;
/*      */       }
/*      */       
/* 2095 */       if (f6 > 1.0F / this.fogColorBlue) {
/* 2096 */         f6 = 1.0F / this.fogColorBlue;
/*      */       }
/*      */       
/* 2099 */       if (Float.isInfinite(f6)) {
/* 2100 */         f6 = Math.nextAfter(f6, 0.0D);
/*      */       }
/*      */       
/* 2103 */       this.fogColorRed = this.fogColorRed * (1.0F - f15) + this.fogColorRed * f6 * f15;
/* 2104 */       this.fogColorGreen = this.fogColorGreen * (1.0F - f15) + this.fogColorGreen * f6 * f15;
/* 2105 */       this.fogColorBlue = this.fogColorBlue * (1.0F - f15) + this.fogColorBlue * f6 * f15;
/*      */     } 
/*      */     
/* 2108 */     if (this.mc.gameSettings.anaglyph) {
/* 2109 */       float f16 = (this.fogColorRed * 30.0F + this.fogColorGreen * 59.0F + this.fogColorBlue * 11.0F) / 100.0F;
/* 2110 */       float f17 = (this.fogColorRed * 30.0F + this.fogColorGreen * 70.0F) / 100.0F;
/* 2111 */       float f7 = (this.fogColorRed * 30.0F + this.fogColorBlue * 70.0F) / 100.0F;
/* 2112 */       this.fogColorRed = f16;
/* 2113 */       this.fogColorGreen = f17;
/* 2114 */       this.fogColorBlue = f7;
/*      */     } 
/*      */     
/* 2117 */     if (Reflector.EntityViewRenderEvent_FogColors_Constructor.exists()) {
/* 2118 */       Object object = Reflector.newInstance(Reflector.EntityViewRenderEvent_FogColors_Constructor, new Object[] { this, entity, block, Float.valueOf(partialTicks), Float.valueOf(this.fogColorRed), Float.valueOf(this.fogColorGreen), Float.valueOf(this.fogColorBlue) });
/* 2119 */       Reflector.postForgeBusEvent(object);
/* 2120 */       this.fogColorRed = Reflector.getFieldValueFloat(object, Reflector.EntityViewRenderEvent_FogColors_red, this.fogColorRed);
/* 2121 */       this.fogColorGreen = Reflector.getFieldValueFloat(object, Reflector.EntityViewRenderEvent_FogColors_green, this.fogColorGreen);
/* 2122 */       this.fogColorBlue = Reflector.getFieldValueFloat(object, Reflector.EntityViewRenderEvent_FogColors_blue, this.fogColorBlue);
/*      */     } 
/*      */     
/* 2125 */     Shaders.setClearColor(this.fogColorRed, this.fogColorGreen, this.fogColorBlue, 0.0F);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void setupFog(int startCoords, float partialTicks) {
/* 2135 */     this.fogStandard = false;
/* 2136 */     Entity entity = this.mc.getRenderViewEntity();
/* 2137 */     boolean flag = false;
/*      */     
/* 2139 */     if (entity instanceof EntityPlayer) {
/* 2140 */       flag = ((EntityPlayer)entity).capabilities.isCreativeMode;
/*      */     }
/*      */     
/* 2143 */     GL11.glFog(2918, setFogColorBuffer(this.fogColorRed, this.fogColorGreen, this.fogColorBlue, 1.0F));
/* 2144 */     GL11.glNormal3f(0.0F, -1.0F, 0.0F);
/* 2145 */     GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
/* 2146 */     Block block = ActiveRenderInfo.getBlockAtEntityViewpoint((World)this.mc.theWorld, entity, partialTicks);
/* 2147 */     float f = -1.0F;
/*      */     
/* 2149 */     if (Reflector.ForgeHooksClient_getFogDensity.exists()) {
/* 2150 */       f = Reflector.callFloat(Reflector.ForgeHooksClient_getFogDensity, new Object[] { this, entity, block, Float.valueOf(partialTicks), Float.valueOf(0.1F) });
/*      */     }
/*      */     
/* 2153 */     if (f >= 0.0F) {
/* 2154 */       GlStateManager.setFogDensity(f);
/* 2155 */     } else if (entity instanceof EntityLivingBase && ((EntityLivingBase)entity).isPotionActive(Potion.blindness)) {
/* 2156 */       float f4 = 5.0F;
/* 2157 */       int i = ((EntityLivingBase)entity).getActivePotionEffect(Potion.blindness).getDuration();
/*      */       
/* 2159 */       if (i < 20) {
/* 2160 */         f4 = 5.0F + (this.farPlaneDistance - 5.0F) * (1.0F - i / 20.0F);
/*      */       }
/*      */       
/* 2163 */       GlStateManager.setFog(9729);
/*      */       
/* 2165 */       if (startCoords == -1) {
/* 2166 */         GlStateManager.setFogStart(0.0F);
/* 2167 */         GlStateManager.setFogEnd(f4 * 0.8F);
/*      */       } else {
/* 2169 */         GlStateManager.setFogStart(f4 * 0.25F);
/* 2170 */         GlStateManager.setFogEnd(f4);
/*      */       } 
/*      */       
/* 2173 */       if ((GLContext.getCapabilities()).GL_NV_fog_distance && Config.isFogFancy()) {
/* 2174 */         GL11.glFogi(34138, 34139);
/*      */       }
/* 2176 */     } else if (this.cloudFog) {
/* 2177 */       GlStateManager.setFog(2048);
/* 2178 */       GlStateManager.setFogDensity(0.1F);
/* 2179 */     } else if (block.getMaterial() == Material.water) {
/* 2180 */       GlStateManager.setFog(2048);
/* 2181 */       float f1 = Config.isClearWater() ? 0.02F : 0.1F;
/*      */       
/* 2183 */       if (entity instanceof EntityLivingBase && ((EntityLivingBase)entity).isPotionActive(Potion.waterBreathing)) {
/* 2184 */         GlStateManager.setFogDensity(0.01F);
/*      */       } else {
/* 2186 */         float f2 = 0.1F - EnchantmentHelper.getRespiration(entity) * 0.03F;
/* 2187 */         GlStateManager.setFogDensity(Config.limit(f2, 0.0F, f1));
/*      */       } 
/* 2189 */     } else if (block.getMaterial() == Material.lava) {
/* 2190 */       GlStateManager.setFog(2048);
/* 2191 */       GlStateManager.setFogDensity(2.0F);
/*      */     } else {
/* 2193 */       float f3 = this.farPlaneDistance;
/* 2194 */       this.fogStandard = true;
/* 2195 */       GlStateManager.setFog(9729);
/*      */       
/* 2197 */       if (startCoords == -1) {
/* 2198 */         GlStateManager.setFogStart(0.0F);
/* 2199 */         GlStateManager.setFogEnd(f3);
/*      */       } else {
/* 2201 */         GlStateManager.setFogStart(f3 * Config.getFogStart());
/* 2202 */         GlStateManager.setFogEnd(f3);
/*      */       } 
/*      */       
/* 2205 */       if ((GLContext.getCapabilities()).GL_NV_fog_distance) {
/* 2206 */         if (Config.isFogFancy()) {
/* 2207 */           GL11.glFogi(34138, 34139);
/*      */         }
/*      */         
/* 2210 */         if (Config.isFogFast()) {
/* 2211 */           GL11.glFogi(34138, 34140);
/*      */         }
/*      */       } 
/*      */       
/* 2215 */       if (this.mc.theWorld.provider.doesXZShowFog((int)entity.posX, (int)entity.posZ)) {
/* 2216 */         GlStateManager.setFogStart(f3 * 0.05F);
/* 2217 */         GlStateManager.setFogEnd(f3);
/*      */       } 
/*      */       
/* 2220 */       if (Reflector.ForgeHooksClient_onFogRender.exists()) {
/* 2221 */         Reflector.callVoid(Reflector.ForgeHooksClient_onFogRender, new Object[] { this, entity, block, Float.valueOf(partialTicks), Integer.valueOf(startCoords), Float.valueOf(f3) });
/*      */       }
/*      */     } 
/*      */     
/* 2225 */     GlStateManager.enableColorMaterial();
/* 2226 */     GlStateManager.enableFog();
/* 2227 */     GlStateManager.colorMaterial(1028, 4608);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private FloatBuffer setFogColorBuffer(float red, float green, float blue, float alpha) {
/* 2234 */     if (Config.isShaders()) {
/* 2235 */       Shaders.setFogColor(red, green, blue);
/*      */     }
/*      */     
/* 2238 */     this.fogColorBuffer.clear();
/* 2239 */     this.fogColorBuffer.put(red).put(green).put(blue).put(alpha);
/* 2240 */     this.fogColorBuffer.flip();
/* 2241 */     return this.fogColorBuffer;
/*      */   }
/*      */   
/*      */   public MapItemRenderer getMapItemRenderer() {
/* 2245 */     return this.theMapItemRenderer;
/*      */   }
/*      */   
/*      */   private void waitForServerThread() {
/* 2249 */     this.serverWaitTimeCurrent = 0;
/*      */     
/* 2251 */     if (Config.isSmoothWorld() && Config.isSingleProcessor()) {
/* 2252 */       if (this.mc.isIntegratedServerRunning()) {
/* 2253 */         IntegratedServer integratedserver = this.mc.getIntegratedServer();
/*      */         
/* 2255 */         if (integratedserver != null) {
/* 2256 */           boolean flag = this.mc.isGamePaused();
/*      */           
/* 2258 */           if (!flag && !(this.mc.currentScreen instanceof net.minecraft.client.gui.GuiDownloadTerrain)) {
/* 2259 */             if (this.serverWaitTime > 0) {
/* 2260 */               Lagometer.timerServer.start();
/* 2261 */               Config.sleep(this.serverWaitTime);
/* 2262 */               Lagometer.timerServer.end();
/* 2263 */               this.serverWaitTimeCurrent = this.serverWaitTime;
/*      */             } 
/*      */             
/* 2266 */             long i = System.nanoTime() / 1000000L;
/*      */             
/* 2268 */             if (this.lastServerTime != 0L && this.lastServerTicks != 0) {
/* 2269 */               long j = i - this.lastServerTime;
/*      */               
/* 2271 */               if (j < 0L) {
/* 2272 */                 this.lastServerTime = i;
/* 2273 */                 j = 0L;
/*      */               } 
/*      */               
/* 2276 */               if (j >= 50L) {
/* 2277 */                 this.lastServerTime = i;
/* 2278 */                 int k = integratedserver.getTickCounter();
/* 2279 */                 int l = k - this.lastServerTicks;
/*      */                 
/* 2281 */                 if (l < 0) {
/* 2282 */                   this.lastServerTicks = k;
/* 2283 */                   l = 0;
/*      */                 } 
/*      */                 
/* 2286 */                 if (l < 1 && this.serverWaitTime < 100) {
/* 2287 */                   this.serverWaitTime += 2;
/*      */                 }
/*      */                 
/* 2290 */                 if (l > 1 && this.serverWaitTime > 0) {
/* 2291 */                   this.serverWaitTime--;
/*      */                 }
/*      */                 
/* 2294 */                 this.lastServerTicks = k;
/*      */               } 
/*      */             } else {
/* 2297 */               this.lastServerTime = i;
/* 2298 */               this.lastServerTicks = integratedserver.getTickCounter();
/* 2299 */               this.avgServerTickDiff = 1.0F;
/* 2300 */               this.avgServerTimeDiff = 50.0F;
/*      */             } 
/*      */           } else {
/* 2303 */             if (this.mc.currentScreen instanceof net.minecraft.client.gui.GuiDownloadTerrain) {
/* 2304 */               Config.sleep(20L);
/*      */             }
/*      */             
/* 2307 */             this.lastServerTime = 0L;
/* 2308 */             this.lastServerTicks = 0;
/*      */           } 
/*      */         } 
/*      */       } 
/*      */     } else {
/* 2313 */       this.lastServerTime = 0L;
/* 2314 */       this.lastServerTicks = 0;
/*      */     } 
/*      */   }
/*      */   
/*      */   private void frameInit() {
/* 2319 */     GlErrors.frameStart();
/*      */     
/* 2321 */     if (!this.initialized) {
/* 2322 */       ReflectorResolver.resolve();
/* 2323 */       TextureUtils.registerResourceListener();
/*      */       
/* 2325 */       if (Config.getBitsOs() == 64 && Config.getBitsJre() == 32) {
/* 2326 */         Config.setNotify64BitJava(true);
/*      */       }
/*      */       
/* 2329 */       this.initialized = true;
/*      */     } 
/*      */     
/* 2332 */     Config.checkDisplayMode();
/* 2333 */     WorldClient worldClient = this.mc.theWorld;
/*      */     
/* 2335 */     if (worldClient != null) {
/* 2336 */       if (Config.getNewRelease() != null) {
/* 2337 */         String s = "HD_U".replace("HD_U", "HD Ultra").replace("L", "Light");
/* 2338 */         String s1 = String.valueOf(s) + " " + Config.getNewRelease();
/* 2339 */         ChatComponentText chatcomponenttext = new ChatComponentText(I18n.format("of.message.newVersion", new Object[] { "§n" + s1 + "§r" }));
/* 2340 */         chatcomponenttext.setChatStyle((new ChatStyle()).setChatClickEvent(new ClickEvent(ClickEvent.Action.OPEN_URL, "https://optifine.net/downloads")));
/* 2341 */         this.mc.ingameGUI.getChatGUI().printChatMessage((IChatComponent)chatcomponenttext);
/* 2342 */         Config.setNewRelease(null);
/*      */       } 
/*      */       
/* 2345 */       if (Config.isNotify64BitJava()) {
/* 2346 */         Config.setNotify64BitJava(false);
/* 2347 */         ChatComponentText chatcomponenttext1 = new ChatComponentText(I18n.format("of.message.java64Bit", new Object[0]));
/* 2348 */         this.mc.ingameGUI.getChatGUI().printChatMessage((IChatComponent)chatcomponenttext1);
/*      */       } 
/*      */     } 
/*      */     
/* 2352 */     if (this.mc.currentScreen instanceof GuiMainMenu) {
/* 2353 */       updateMainMenu((GuiMainMenu)this.mc.currentScreen);
/*      */     }
/*      */     
/* 2356 */     if (this.updatedWorld != worldClient) {
/* 2357 */       RandomEntities.worldChanged(this.updatedWorld, (World)worldClient);
/* 2358 */       Config.updateThreadPriorities();
/* 2359 */       this.lastServerTime = 0L;
/* 2360 */       this.lastServerTicks = 0;
/* 2361 */       this.updatedWorld = (World)worldClient;
/*      */     } 
/*      */     
/* 2364 */     if (!setFxaaShader(Shaders.configAntialiasingLevel)) {
/* 2365 */       Shaders.configAntialiasingLevel = 0;
/*      */     }
/*      */     
/* 2368 */     if (this.mc.currentScreen != null && this.mc.currentScreen.getClass() == GuiChat.class) {
/* 2369 */       this.mc.displayGuiScreen((GuiScreen)new GuiChatOF((GuiChat)this.mc.currentScreen));
/*      */     }
/*      */   }
/*      */   
/*      */   private void frameFinish() {
/* 2374 */     if (this.mc.theWorld != null && Config.isShowGlErrors() && TimedEvent.isActive("CheckGlErrorFrameFinish", 10000L)) {
/* 2375 */       int i = GlStateManager.glGetError();
/*      */       
/* 2377 */       if (i != 0 && GlErrors.isEnabled(i)) {
/* 2378 */         String s = Config.getGlErrorString(i);
/* 2379 */         ChatComponentText chatcomponenttext = new ChatComponentText(I18n.format("of.message.openglError", new Object[] { Integer.valueOf(i), s }));
/* 2380 */         this.mc.ingameGUI.getChatGUI().printChatMessage((IChatComponent)chatcomponenttext);
/*      */       } 
/*      */     } 
/*      */   }
/*      */   
/*      */   private void updateMainMenu(GuiMainMenu p_updateMainMenu_1_) {
/*      */     try {
/* 2387 */       String s = null;
/* 2388 */       Calendar calendar = Calendar.getInstance();
/* 2389 */       calendar.setTime(new Date());
/* 2390 */       int i = calendar.get(5);
/* 2391 */       int j = calendar.get(2) + 1;
/*      */       
/* 2393 */       if (i == 8 && j == 4) {
/* 2394 */         s = "Happy birthday, OptiFine!";
/*      */       }
/*      */       
/* 2397 */       if (i == 14 && j == 8) {
/* 2398 */         s = "Happy birthday, sp614x!";
/*      */       }
/*      */       
/* 2401 */       if (s == null) {
/*      */         return;
/*      */       }
/*      */       
/* 2405 */       Reflector.setFieldValue(p_updateMainMenu_1_, Reflector.GuiMainMenu_splashText, s);
/* 2406 */     } catch (Throwable throwable) {}
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean setFxaaShader(int p_setFxaaShader_1_) {
/* 2412 */     if (!OpenGlHelper.isFramebufferEnabled())
/* 2413 */       return false; 
/* 2414 */     if (this.theShaderGroup != null && this.theShaderGroup != this.fxaaShaders[2] && this.theShaderGroup != this.fxaaShaders[4])
/* 2415 */       return true; 
/* 2416 */     if (p_setFxaaShader_1_ != 2 && p_setFxaaShader_1_ != 4) {
/* 2417 */       if (this.theShaderGroup == null) {
/* 2418 */         return true;
/*      */       }
/* 2420 */       this.theShaderGroup.deleteShaderGroup();
/* 2421 */       this.theShaderGroup = null;
/* 2422 */       return true;
/*      */     } 
/* 2424 */     if (this.theShaderGroup != null && this.theShaderGroup == this.fxaaShaders[p_setFxaaShader_1_])
/* 2425 */       return true; 
/* 2426 */     if (this.mc.theWorld == null) {
/* 2427 */       return true;
/*      */     }
/* 2429 */     loadShader(new ResourceLocation("shaders/post/fxaa_of_" + p_setFxaaShader_1_ + "x.json"));
/* 2430 */     this.fxaaShaders[p_setFxaaShader_1_] = this.theShaderGroup;
/* 2431 */     return this.useShader;
/*      */   }
/*      */ 
/*      */   
/*      */   private void checkLoadVisibleChunks(Entity p_checkLoadVisibleChunks_1_, float p_checkLoadVisibleChunks_2_, ICamera p_checkLoadVisibleChunks_3_, boolean p_checkLoadVisibleChunks_4_) {
/* 2436 */     int i = 201435902;
/*      */     
/* 2438 */     if (this.loadVisibleChunks) {
/* 2439 */       this.loadVisibleChunks = false;
/* 2440 */       loadAllVisibleChunks(p_checkLoadVisibleChunks_1_, p_checkLoadVisibleChunks_2_, p_checkLoadVisibleChunks_3_, p_checkLoadVisibleChunks_4_);
/* 2441 */       this.mc.ingameGUI.getChatGUI().deleteChatLine(i);
/*      */     } 
/*      */     
/* 2444 */     if (Keyboard.isKeyDown(61) && Keyboard.isKeyDown(38)) {
/* 2445 */       if (this.mc.currentScreen != null) {
/*      */         return;
/*      */       }
/*      */       
/* 2449 */       this.loadVisibleChunks = true;
/* 2450 */       ChatComponentText chatcomponenttext = new ChatComponentText(I18n.format("of.message.loadingVisibleChunks", new Object[0]));
/* 2451 */       this.mc.ingameGUI.getChatGUI().printChatMessageWithOptionalDeletion((IChatComponent)chatcomponenttext, i);
/*      */     } 
/*      */   }
/*      */   
/*      */   private void loadAllVisibleChunks(Entity p_loadAllVisibleChunks_1_, double p_loadAllVisibleChunks_2_, ICamera p_loadAllVisibleChunks_4_, boolean p_loadAllVisibleChunks_5_) {
/* 2456 */     int i = this.mc.gameSettings.ofChunkUpdates;
/* 2457 */     boolean flag = this.mc.gameSettings.ofLazyChunkLoading;
/*      */     
/*      */     try {
/* 2460 */       this.mc.gameSettings.ofChunkUpdates = 1000;
/* 2461 */       this.mc.gameSettings.ofLazyChunkLoading = false;
/* 2462 */       RenderGlobal renderglobal = Config.getRenderGlobal();
/* 2463 */       int j = renderglobal.getCountLoadedChunks();
/* 2464 */       long k = System.currentTimeMillis();
/* 2465 */       Config.dbg("Loading visible chunks");
/* 2466 */       long l = System.currentTimeMillis() + 5000L;
/* 2467 */       int i1 = 0;
/* 2468 */       boolean flag1 = false;
/*      */       
/*      */       do {
/* 2471 */         flag1 = false;
/*      */         
/* 2473 */         for (int j1 = 0; j1 < 100; j1++) {
/* 2474 */           renderglobal.displayListEntitiesDirty = true;
/* 2475 */           renderglobal.setupTerrain(p_loadAllVisibleChunks_1_, p_loadAllVisibleChunks_2_, p_loadAllVisibleChunks_4_, this.frameCount++, p_loadAllVisibleChunks_5_);
/*      */           
/* 2477 */           if (!renderglobal.hasNoChunkUpdates()) {
/* 2478 */             flag1 = true;
/*      */           }
/*      */           
/* 2481 */           i1 += renderglobal.getCountChunksToUpdate();
/*      */           
/* 2483 */           while (!renderglobal.hasNoChunkUpdates()) {
/* 2484 */             renderglobal.updateChunks(System.nanoTime() + 1000000000L);
/*      */           }
/*      */           
/* 2487 */           i1 -= renderglobal.getCountChunksToUpdate();
/*      */           
/* 2489 */           if (!flag1) {
/*      */             break;
/*      */           }
/*      */         } 
/*      */         
/* 2494 */         if (renderglobal.getCountLoadedChunks() != j) {
/* 2495 */           flag1 = true;
/* 2496 */           j = renderglobal.getCountLoadedChunks();
/*      */         } 
/*      */         
/* 2499 */         if (System.currentTimeMillis() <= l)
/* 2500 */           continue;  Config.log("Chunks loaded: " + i1);
/* 2501 */         l = System.currentTimeMillis() + 5000L;
/*      */       
/*      */       }
/* 2504 */       while (flag1);
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 2509 */       Config.log("Chunks loaded: " + i1);
/* 2510 */       Config.log("Finished loading visible chunks");
/* 2511 */       RenderChunk.renderChunksUpdated = 0;
/*      */     } finally {
/* 2513 */       this.mc.gameSettings.ofChunkUpdates = i;
/* 2514 */       this.mc.gameSettings.ofLazyChunkLoading = flag;
/*      */     } 
/*      */   }
/*      */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\minecraft\client\renderer\EntityRenderer.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */