/*     */ package net.optifine.reflect;
/*     */ 
/*     */ import com.google.common.base.Optional;
/*     */ import java.lang.reflect.Constructor;
/*     */ import java.lang.reflect.Field;
/*     */ import java.lang.reflect.Method;
/*     */ import java.util.Map;
/*     */ import javax.vecmath.Matrix4f;
/*     */ import net.minecraft.block.Block;
/*     */ import net.minecraft.block.properties.IProperty;
/*     */ import net.minecraft.block.state.IBlockState;
/*     */ import net.minecraft.client.Minecraft;
/*     */ import net.minecraft.client.gui.GuiEnchantment;
/*     */ import net.minecraft.client.gui.GuiHopper;
/*     */ import net.minecraft.client.gui.GuiMainMenu;
/*     */ import net.minecraft.client.gui.GuiScreen;
/*     */ import net.minecraft.client.gui.inventory.GuiBeacon;
/*     */ import net.minecraft.client.gui.inventory.GuiBrewingStand;
/*     */ import net.minecraft.client.gui.inventory.GuiChest;
/*     */ import net.minecraft.client.gui.inventory.GuiFurnace;
/*     */ import net.minecraft.client.model.ModelBanner;
/*     */ import net.minecraft.client.model.ModelBase;
/*     */ import net.minecraft.client.model.ModelBat;
/*     */ import net.minecraft.client.model.ModelBlaze;
/*     */ import net.minecraft.client.model.ModelBook;
/*     */ import net.minecraft.client.model.ModelChest;
/*     */ import net.minecraft.client.model.ModelDragon;
/*     */ import net.minecraft.client.model.ModelEnderCrystal;
/*     */ import net.minecraft.client.model.ModelEnderMite;
/*     */ import net.minecraft.client.model.ModelGhast;
/*     */ import net.minecraft.client.model.ModelGuardian;
/*     */ import net.minecraft.client.model.ModelHorse;
/*     */ import net.minecraft.client.model.ModelHumanoidHead;
/*     */ import net.minecraft.client.model.ModelLeashKnot;
/*     */ import net.minecraft.client.model.ModelMagmaCube;
/*     */ import net.minecraft.client.model.ModelOcelot;
/*     */ import net.minecraft.client.model.ModelRabbit;
/*     */ import net.minecraft.client.model.ModelRenderer;
/*     */ import net.minecraft.client.model.ModelSign;
/*     */ import net.minecraft.client.model.ModelSilverfish;
/*     */ import net.minecraft.client.model.ModelSkeletonHead;
/*     */ import net.minecraft.client.model.ModelSlime;
/*     */ import net.minecraft.client.model.ModelSquid;
/*     */ import net.minecraft.client.model.ModelWitch;
/*     */ import net.minecraft.client.model.ModelWither;
/*     */ import net.minecraft.client.model.ModelWolf;
/*     */ import net.minecraft.client.multiplayer.ChunkProviderClient;
/*     */ import net.minecraft.client.renderer.EntityRenderer;
/*     */ import net.minecraft.client.renderer.block.model.ModelBlock;
/*     */ import net.minecraft.client.renderer.entity.RenderBoat;
/*     */ import net.minecraft.client.renderer.entity.RenderLeashKnot;
/*     */ import net.minecraft.client.renderer.entity.RenderManager;
/*     */ import net.minecraft.client.renderer.entity.RenderMinecart;
/*     */ import net.minecraft.client.renderer.entity.RendererLivingEntity;
/*     */ import net.minecraft.client.renderer.tileentity.RenderEnderCrystal;
/*     */ import net.minecraft.client.renderer.tileentity.RenderItemFrame;
/*     */ import net.minecraft.client.renderer.tileentity.RenderWitherSkull;
/*     */ import net.minecraft.client.renderer.tileentity.TileEntityBannerRenderer;
/*     */ import net.minecraft.client.renderer.tileentity.TileEntityChestRenderer;
/*     */ import net.minecraft.client.renderer.tileentity.TileEntityEnchantmentTableRenderer;
/*     */ import net.minecraft.client.renderer.tileentity.TileEntityEnderChestRenderer;
/*     */ import net.minecraft.client.renderer.tileentity.TileEntitySignRenderer;
/*     */ import net.minecraft.client.renderer.tileentity.TileEntitySkullRenderer;
/*     */ import net.minecraft.client.renderer.vertex.VertexFormatElement;
/*     */ import net.minecraft.client.resources.DefaultResourcePack;
/*     */ import net.minecraft.client.resources.model.ModelManager;
/*     */ import net.minecraft.client.resources.model.ModelRotation;
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.entity.EntityLiving;
/*     */ import net.minecraft.entity.EntityLivingBase;
/*     */ import net.minecraft.entity.EnumCreatureType;
/*     */ import net.minecraft.entity.item.EntityItemFrame;
/*     */ import net.minecraft.entity.passive.EntityVillager;
/*     */ import net.minecraft.entity.player.EntityPlayerMP;
/*     */ import net.minecraft.inventory.IInventory;
/*     */ import net.minecraft.inventory.InventoryBasic;
/*     */ import net.minecraft.item.Item;
/*     */ import net.minecraft.potion.PotionEffect;
/*     */ import net.minecraft.tileentity.TileEntity;
/*     */ import net.minecraft.tileentity.TileEntityBeacon;
/*     */ import net.minecraft.tileentity.TileEntityBrewingStand;
/*     */ import net.minecraft.tileentity.TileEntityEnchantmentTable;
/*     */ import net.minecraft.tileentity.TileEntityFurnace;
/*     */ import net.minecraft.util.BlockPos;
/*     */ import net.minecraft.util.EnumWorldBlockLayer;
/*     */ import net.minecraft.util.LongHashMap;
/*     */ import net.minecraft.util.ResourceLocation;
/*     */ import net.minecraft.world.ChunkCache;
/*     */ import net.minecraft.world.ChunkCoordIntPair;
/*     */ import net.minecraft.world.IBlockAccess;
/*     */ import net.minecraft.world.IWorldNameable;
/*     */ import net.minecraft.world.World;
/*     */ import net.minecraft.world.WorldProvider;
/*     */ import net.minecraft.world.biome.BiomeGenBase;
/*     */ import net.minecraftforge.common.property.IUnlistedProperty;
/*     */ import net.optifine.Log;
/*     */ import net.optifine.util.ArrayUtils;
/*     */ import org.apache.logging.log4j.LogManager;
/*     */ import org.apache.logging.log4j.Logger;
/*     */ 
/*     */ 
/*     */ 
/*     */ public class Reflector
/*     */ {
/* 105 */   private static final Logger LOGGER = LogManager.getLogger();
/* 106 */   private static boolean logForge = logEntry("*** Reflector Forge ***");
/* 107 */   public static ReflectorClass BetterFoliageClient = new ReflectorClass("mods.betterfoliage.client.BetterFoliageClient");
/* 108 */   public static ReflectorClass BlamingTransformer = new ReflectorClass("net.minecraftforge.fml.common.asm.transformers.BlamingTransformer");
/* 109 */   public static ReflectorMethod BlamingTransformer_onCrash = new ReflectorMethod(BlamingTransformer, "onCrash");
/* 110 */   public static ReflectorClass ChunkWatchEvent_UnWatch = new ReflectorClass("net.minecraftforge.event.world.ChunkWatchEvent$UnWatch");
/* 111 */   public static ReflectorConstructor ChunkWatchEvent_UnWatch_Constructor = new ReflectorConstructor(ChunkWatchEvent_UnWatch, new Class[] { ChunkCoordIntPair.class, EntityPlayerMP.class });
/* 112 */   public static ReflectorClass CoreModManager = new ReflectorClass("net.minecraftforge.fml.relauncher.CoreModManager");
/* 113 */   public static ReflectorMethod CoreModManager_onCrash = new ReflectorMethod(CoreModManager, "onCrash");
/* 114 */   public static ReflectorClass DimensionManager = new ReflectorClass("net.minecraftforge.common.DimensionManager");
/* 115 */   public static ReflectorMethod DimensionManager_createProviderFor = new ReflectorMethod(DimensionManager, "createProviderFor");
/* 116 */   public static ReflectorMethod DimensionManager_getStaticDimensionIDs = new ReflectorMethod(DimensionManager, "getStaticDimensionIDs");
/* 117 */   public static ReflectorClass DrawScreenEvent_Pre = new ReflectorClass("net.minecraftforge.client.event.GuiScreenEvent$DrawScreenEvent$Pre");
/* 118 */   public static ReflectorConstructor DrawScreenEvent_Pre_Constructor = new ReflectorConstructor(DrawScreenEvent_Pre, new Class[] { GuiScreen.class, int.class, int.class, float.class });
/* 119 */   public static ReflectorClass DrawScreenEvent_Post = new ReflectorClass("net.minecraftforge.client.event.GuiScreenEvent$DrawScreenEvent$Post");
/* 120 */   public static ReflectorConstructor DrawScreenEvent_Post_Constructor = new ReflectorConstructor(DrawScreenEvent_Post, new Class[] { GuiScreen.class, int.class, int.class, float.class });
/* 121 */   public static ReflectorClass EntityViewRenderEvent_CameraSetup = new ReflectorClass("net.minecraftforge.client.event.EntityViewRenderEvent$CameraSetup");
/* 122 */   public static ReflectorConstructor EntityViewRenderEvent_CameraSetup_Constructor = new ReflectorConstructor(EntityViewRenderEvent_CameraSetup, new Class[] { EntityRenderer.class, Entity.class, Block.class, double.class, float.class, float.class, float.class });
/* 123 */   public static ReflectorField EntityViewRenderEvent_CameraSetup_yaw = new ReflectorField(EntityViewRenderEvent_CameraSetup, "yaw");
/* 124 */   public static ReflectorField EntityViewRenderEvent_CameraSetup_pitch = new ReflectorField(EntityViewRenderEvent_CameraSetup, "pitch");
/* 125 */   public static ReflectorField EntityViewRenderEvent_CameraSetup_roll = new ReflectorField(EntityViewRenderEvent_CameraSetup, "roll");
/* 126 */   public static ReflectorClass EntityViewRenderEvent_FogColors = new ReflectorClass("net.minecraftforge.client.event.EntityViewRenderEvent$FogColors");
/* 127 */   public static ReflectorConstructor EntityViewRenderEvent_FogColors_Constructor = new ReflectorConstructor(EntityViewRenderEvent_FogColors, new Class[] { EntityRenderer.class, Entity.class, Block.class, double.class, float.class, float.class, float.class });
/* 128 */   public static ReflectorField EntityViewRenderEvent_FogColors_red = new ReflectorField(EntityViewRenderEvent_FogColors, "red");
/* 129 */   public static ReflectorField EntityViewRenderEvent_FogColors_green = new ReflectorField(EntityViewRenderEvent_FogColors, "green");
/* 130 */   public static ReflectorField EntityViewRenderEvent_FogColors_blue = new ReflectorField(EntityViewRenderEvent_FogColors, "blue");
/* 131 */   public static ReflectorClass Event = new ReflectorClass("net.minecraftforge.fml.common.eventhandler.Event");
/* 132 */   public static ReflectorMethod Event_isCanceled = new ReflectorMethod(Event, "isCanceled");
/* 133 */   public static ReflectorClass EventBus = new ReflectorClass("net.minecraftforge.fml.common.eventhandler.EventBus");
/* 134 */   public static ReflectorMethod EventBus_post = new ReflectorMethod(EventBus, "post");
/* 135 */   public static ReflectorClass Event_Result = new ReflectorClass("net.minecraftforge.fml.common.eventhandler.Event$Result");
/* 136 */   public static ReflectorField Event_Result_DENY = new ReflectorField(Event_Result, "DENY");
/* 137 */   public static ReflectorField Event_Result_ALLOW = new ReflectorField(Event_Result, "ALLOW");
/* 138 */   public static ReflectorField Event_Result_DEFAULT = new ReflectorField(Event_Result, "DEFAULT");
/* 139 */   public static ReflectorClass ExtendedBlockState = new ReflectorClass("net.minecraftforge.common.property.ExtendedBlockState");
/* 140 */   public static ReflectorConstructor ExtendedBlockState_Constructor = new ReflectorConstructor(ExtendedBlockState, new Class[] { Block.class, IProperty[].class, IUnlistedProperty[].class });
/* 141 */   public static ReflectorClass FMLClientHandler = new ReflectorClass("net.minecraftforge.fml.client.FMLClientHandler");
/* 142 */   public static ReflectorMethod FMLClientHandler_instance = new ReflectorMethod(FMLClientHandler, "instance");
/* 143 */   public static ReflectorMethod FMLClientHandler_handleLoadingScreen = new ReflectorMethod(FMLClientHandler, "handleLoadingScreen");
/* 144 */   public static ReflectorMethod FMLClientHandler_isLoading = new ReflectorMethod(FMLClientHandler, "isLoading");
/* 145 */   public static ReflectorMethod FMLClientHandler_trackBrokenTexture = new ReflectorMethod(FMLClientHandler, "trackBrokenTexture");
/* 146 */   public static ReflectorMethod FMLClientHandler_trackMissingTexture = new ReflectorMethod(FMLClientHandler, "trackMissingTexture");
/* 147 */   public static ReflectorClass FMLCommonHandler = new ReflectorClass("net.minecraftforge.fml.common.FMLCommonHandler");
/* 148 */   public static ReflectorMethod FMLCommonHandler_callFuture = new ReflectorMethod(FMLCommonHandler, "callFuture");
/* 149 */   public static ReflectorMethod FMLCommonHandler_enhanceCrashReport = new ReflectorMethod(FMLCommonHandler, "enhanceCrashReport");
/* 150 */   public static ReflectorMethod FMLCommonHandler_getBrandings = new ReflectorMethod(FMLCommonHandler, "getBrandings");
/* 151 */   public static ReflectorMethod FMLCommonHandler_handleServerAboutToStart = new ReflectorMethod(FMLCommonHandler, "handleServerAboutToStart");
/* 152 */   public static ReflectorMethod FMLCommonHandler_handleServerStarting = new ReflectorMethod(FMLCommonHandler, "handleServerStarting");
/* 153 */   public static ReflectorMethod FMLCommonHandler_instance = new ReflectorMethod(FMLCommonHandler, "instance");
/* 154 */   public static ReflectorClass ForgeBiome = new ReflectorClass(BiomeGenBase.class);
/* 155 */   public static ReflectorMethod ForgeBiome_getWaterColorMultiplier = new ReflectorMethod(ForgeBiome, "getWaterColorMultiplier");
/* 156 */   public static ReflectorClass ForgeBlock = new ReflectorClass(Block.class);
/* 157 */   public static ReflectorMethod ForgeBlock_addDestroyEffects = new ReflectorMethod(ForgeBlock, "addDestroyEffects");
/* 158 */   public static ReflectorMethod ForgeBlock_addHitEffects = new ReflectorMethod(ForgeBlock, "addHitEffects");
/* 159 */   public static ReflectorMethod ForgeBlock_canCreatureSpawn = new ReflectorMethod(ForgeBlock, "canCreatureSpawn");
/* 160 */   public static ReflectorMethod ForgeBlock_canRenderInLayer = new ReflectorMethod(ForgeBlock, "canRenderInLayer", new Class[] { EnumWorldBlockLayer.class });
/* 161 */   public static ReflectorMethod ForgeBlock_doesSideBlockRendering = new ReflectorMethod(ForgeBlock, "doesSideBlockRendering");
/* 162 */   public static ReflectorMethod ForgeBlock_getBedDirection = new ReflectorMethod(ForgeBlock, "getBedDirection");
/* 163 */   public static ReflectorMethod ForgeBlock_getExtendedState = new ReflectorMethod(ForgeBlock, "getExtendedState");
/* 164 */   public static ReflectorMethod ForgeBlock_getLightOpacity = new ReflectorMethod(ForgeBlock, "getLightOpacity", new Class[] { IBlockAccess.class, BlockPos.class });
/* 165 */   public static ReflectorMethod ForgeBlock_getLightValue = new ReflectorMethod(ForgeBlock, "getLightValue", new Class[] { IBlockAccess.class, BlockPos.class });
/* 166 */   public static ReflectorMethod ForgeBlock_hasTileEntity = new ReflectorMethod(ForgeBlock, "hasTileEntity", new Class[] { IBlockState.class });
/* 167 */   public static ReflectorMethod ForgeBlock_isAir = new ReflectorMethod(ForgeBlock, "isAir");
/* 168 */   public static ReflectorMethod ForgeBlock_isBed = new ReflectorMethod(ForgeBlock, "isBed");
/* 169 */   public static ReflectorMethod ForgeBlock_isBedFoot = new ReflectorMethod(ForgeBlock, "isBedFoot");
/* 170 */   public static ReflectorMethod ForgeBlock_isSideSolid = new ReflectorMethod(ForgeBlock, "isSideSolid");
/* 171 */   public static ReflectorClass ForgeChunkCache = new ReflectorClass(ChunkCache.class);
/* 172 */   public static ReflectorMethod ForgeChunkCache_isSideSolid = new ReflectorMethod(ForgeChunkCache, "isSideSolid");
/* 173 */   public static ReflectorClass ForgeEntity = new ReflectorClass(Entity.class);
/* 174 */   public static ReflectorMethod ForgeEntity_canRiderInteract = new ReflectorMethod(ForgeEntity, "canRiderInteract");
/* 175 */   public static ReflectorField ForgeEntity_captureDrops = new ReflectorField(ForgeEntity, "captureDrops");
/* 176 */   public static ReflectorField ForgeEntity_capturedDrops = new ReflectorField(ForgeEntity, "capturedDrops");
/* 177 */   public static ReflectorMethod ForgeEntity_shouldRenderInPass = new ReflectorMethod(ForgeEntity, "shouldRenderInPass");
/* 178 */   public static ReflectorMethod ForgeEntity_shouldRiderSit = new ReflectorMethod(ForgeEntity, "shouldRiderSit");
/* 179 */   public static ReflectorClass ForgeEventFactory = new ReflectorClass("net.minecraftforge.event.ForgeEventFactory");
/* 180 */   public static ReflectorMethod ForgeEventFactory_canEntityDespawn = new ReflectorMethod(ForgeEventFactory, "canEntityDespawn");
/* 181 */   public static ReflectorMethod ForgeEventFactory_canEntitySpawn = new ReflectorMethod(ForgeEventFactory, "canEntitySpawn");
/* 182 */   public static ReflectorMethod ForgeEventFactory_doSpecialSpawn = new ReflectorMethod(ForgeEventFactory, "doSpecialSpawn", new Class[] { EntityLiving.class, World.class, float.class, float.class, float.class });
/* 183 */   public static ReflectorMethod ForgeEventFactory_getMaxSpawnPackSize = new ReflectorMethod(ForgeEventFactory, "getMaxSpawnPackSize");
/* 184 */   public static ReflectorMethod ForgeEventFactory_renderBlockOverlay = new ReflectorMethod(ForgeEventFactory, "renderBlockOverlay");
/* 185 */   public static ReflectorMethod ForgeEventFactory_renderFireOverlay = new ReflectorMethod(ForgeEventFactory, "renderFireOverlay");
/* 186 */   public static ReflectorMethod ForgeEventFactory_renderWaterOverlay = new ReflectorMethod(ForgeEventFactory, "renderWaterOverlay");
/* 187 */   public static ReflectorClass ForgeHooks = new ReflectorClass("net.minecraftforge.common.ForgeHooks");
/* 188 */   public static ReflectorMethod ForgeHooks_onLivingAttack = new ReflectorMethod(ForgeHooks, "onLivingAttack");
/* 189 */   public static ReflectorMethod ForgeHooks_onLivingDeath = new ReflectorMethod(ForgeHooks, "onLivingDeath");
/* 190 */   public static ReflectorMethod ForgeHooks_onLivingDrops = new ReflectorMethod(ForgeHooks, "onLivingDrops");
/* 191 */   public static ReflectorMethod ForgeHooks_onLivingFall = new ReflectorMethod(ForgeHooks, "onLivingFall");
/* 192 */   public static ReflectorMethod ForgeHooks_onLivingHurt = new ReflectorMethod(ForgeHooks, "onLivingHurt");
/* 193 */   public static ReflectorMethod ForgeHooks_onLivingJump = new ReflectorMethod(ForgeHooks, "onLivingJump");
/* 194 */   public static ReflectorMethod ForgeHooks_onLivingSetAttackTarget = new ReflectorMethod(ForgeHooks, "onLivingSetAttackTarget");
/* 195 */   public static ReflectorMethod ForgeHooks_onLivingUpdate = new ReflectorMethod(ForgeHooks, "onLivingUpdate");
/* 196 */   public static ReflectorClass ForgeHooksClient = new ReflectorClass("net.minecraftforge.client.ForgeHooksClient");
/* 197 */   public static ReflectorMethod ForgeHooksClient_applyTransform = new ReflectorMethod(ForgeHooksClient, "applyTransform", new Class[] { Matrix4f.class, Optional.class });
/* 198 */   public static ReflectorMethod ForgeHooksClient_dispatchRenderLast = new ReflectorMethod(ForgeHooksClient, "dispatchRenderLast");
/* 199 */   public static ReflectorMethod ForgeHooksClient_drawScreen = new ReflectorMethod(ForgeHooksClient, "drawScreen");
/* 200 */   public static ReflectorMethod ForgeHooksClient_fillNormal = new ReflectorMethod(ForgeHooksClient, "fillNormal");
/* 201 */   public static ReflectorMethod ForgeHooksClient_handleCameraTransforms = new ReflectorMethod(ForgeHooksClient, "handleCameraTransforms");
/* 202 */   public static ReflectorMethod ForgeHooksClient_getArmorModel = new ReflectorMethod(ForgeHooksClient, "getArmorModel");
/* 203 */   public static ReflectorMethod ForgeHooksClient_getArmorTexture = new ReflectorMethod(ForgeHooksClient, "getArmorTexture");
/* 204 */   public static ReflectorMethod ForgeHooksClient_getFogDensity = new ReflectorMethod(ForgeHooksClient, "getFogDensity");
/* 205 */   public static ReflectorMethod ForgeHooksClient_getFOVModifier = new ReflectorMethod(ForgeHooksClient, "getFOVModifier");
/* 206 */   public static ReflectorMethod ForgeHooksClient_getMatrix = new ReflectorMethod(ForgeHooksClient, "getMatrix", new Class[] { ModelRotation.class });
/* 207 */   public static ReflectorMethod ForgeHooksClient_getOffsetFOV = new ReflectorMethod(ForgeHooksClient, "getOffsetFOV");
/* 208 */   public static ReflectorMethod ForgeHooksClient_loadEntityShader = new ReflectorMethod(ForgeHooksClient, "loadEntityShader");
/* 209 */   public static ReflectorMethod ForgeHooksClient_onDrawBlockHighlight = new ReflectorMethod(ForgeHooksClient, "onDrawBlockHighlight");
/* 210 */   public static ReflectorMethod ForgeHooksClient_onFogRender = new ReflectorMethod(ForgeHooksClient, "onFogRender");
/* 211 */   public static ReflectorMethod ForgeHooksClient_onTextureStitchedPre = new ReflectorMethod(ForgeHooksClient, "onTextureStitchedPre");
/* 212 */   public static ReflectorMethod ForgeHooksClient_onTextureStitchedPost = new ReflectorMethod(ForgeHooksClient, "onTextureStitchedPost");
/* 213 */   public static ReflectorMethod ForgeHooksClient_orientBedCamera = new ReflectorMethod(ForgeHooksClient, "orientBedCamera");
/* 214 */   public static ReflectorMethod ForgeHooksClient_putQuadColor = new ReflectorMethod(ForgeHooksClient, "putQuadColor");
/* 215 */   public static ReflectorMethod ForgeHooksClient_renderFirstPersonHand = new ReflectorMethod(ForgeHooksClient, "renderFirstPersonHand");
/* 216 */   public static ReflectorMethod ForgeHooksClient_renderMainMenu = new ReflectorMethod(ForgeHooksClient, "renderMainMenu");
/* 217 */   public static ReflectorMethod ForgeHooksClient_setRenderLayer = new ReflectorMethod(ForgeHooksClient, "setRenderLayer");
/* 218 */   public static ReflectorMethod ForgeHooksClient_setRenderPass = new ReflectorMethod(ForgeHooksClient, "setRenderPass");
/* 219 */   public static ReflectorMethod ForgeHooksClient_transform = new ReflectorMethod(ForgeHooksClient, "transform");
/* 220 */   public static ReflectorClass ForgeItem = new ReflectorClass(Item.class);
/* 221 */   public static ReflectorField ForgeItem_delegate = new ReflectorField(ForgeItem, "delegate");
/* 222 */   public static ReflectorMethod ForgeItem_getDurabilityForDisplay = new ReflectorMethod(ForgeItem, "getDurabilityForDisplay");
/* 223 */   public static ReflectorMethod ForgeItem_getModel = new ReflectorMethod(ForgeItem, "getModel");
/* 224 */   public static ReflectorMethod ForgeItem_onEntitySwing = new ReflectorMethod(ForgeItem, "onEntitySwing");
/* 225 */   public static ReflectorMethod ForgeItem_shouldCauseReequipAnimation = new ReflectorMethod(ForgeItem, "shouldCauseReequipAnimation");
/* 226 */   public static ReflectorMethod ForgeItem_showDurabilityBar = new ReflectorMethod(ForgeItem, "showDurabilityBar");
/* 227 */   public static ReflectorClass ForgeModContainer = new ReflectorClass("net.minecraftforge.common.ForgeModContainer");
/* 228 */   public static ReflectorField ForgeModContainer_forgeLightPipelineEnabled = new ReflectorField(ForgeModContainer, "forgeLightPipelineEnabled");
/* 229 */   public static ReflectorClass ForgePotionEffect = new ReflectorClass(PotionEffect.class);
/* 230 */   public static ReflectorMethod ForgePotionEffect_isCurativeItem = new ReflectorMethod(ForgePotionEffect, "isCurativeItem");
/* 231 */   public static ReflectorClass ForgeTileEntity = new ReflectorClass(TileEntity.class);
/* 232 */   public static ReflectorMethod ForgeTileEntity_canRenderBreaking = new ReflectorMethod(ForgeTileEntity, "canRenderBreaking");
/* 233 */   public static ReflectorMethod ForgeTileEntity_getRenderBoundingBox = new ReflectorMethod(ForgeTileEntity, "getRenderBoundingBox");
/* 234 */   public static ReflectorMethod ForgeTileEntity_hasFastRenderer = new ReflectorMethod(ForgeTileEntity, "hasFastRenderer");
/* 235 */   public static ReflectorMethod ForgeTileEntity_shouldRenderInPass = new ReflectorMethod(ForgeTileEntity, "shouldRenderInPass");
/* 236 */   public static ReflectorClass ForgeVertexFormatElementEnumUseage = new ReflectorClass(VertexFormatElement.EnumUsage.class);
/* 237 */   public static ReflectorMethod ForgeVertexFormatElementEnumUseage_preDraw = new ReflectorMethod(ForgeVertexFormatElementEnumUseage, "preDraw");
/* 238 */   public static ReflectorMethod ForgeVertexFormatElementEnumUseage_postDraw = new ReflectorMethod(ForgeVertexFormatElementEnumUseage, "postDraw");
/* 239 */   public static ReflectorClass ForgeWorld = new ReflectorClass(World.class);
/* 240 */   public static ReflectorMethod ForgeWorld_countEntities = new ReflectorMethod(ForgeWorld, "countEntities", new Class[] { EnumCreatureType.class, boolean.class });
/* 241 */   public static ReflectorMethod ForgeWorld_getPerWorldStorage = new ReflectorMethod(ForgeWorld, "getPerWorldStorage");
/* 242 */   public static ReflectorClass ForgeWorldProvider = new ReflectorClass(WorldProvider.class);
/* 243 */   public static ReflectorMethod ForgeWorldProvider_getCloudRenderer = new ReflectorMethod(ForgeWorldProvider, "getCloudRenderer");
/* 244 */   public static ReflectorMethod ForgeWorldProvider_getSkyRenderer = new ReflectorMethod(ForgeWorldProvider, "getSkyRenderer");
/* 245 */   public static ReflectorMethod ForgeWorldProvider_getWeatherRenderer = new ReflectorMethod(ForgeWorldProvider, "getWeatherRenderer");
/* 246 */   public static ReflectorMethod ForgeWorldProvider_getSaveFolder = new ReflectorMethod(ForgeWorldProvider, "getSaveFolder");
/* 247 */   public static ReflectorClass GuiModList = new ReflectorClass("net.minecraftforge.fml.client.GuiModList");
/* 248 */   public static ReflectorConstructor GuiModList_Constructor = new ReflectorConstructor(GuiModList, new Class[] { GuiScreen.class });
/* 249 */   public static ReflectorClass IColoredBakedQuad = new ReflectorClass("net.minecraftforge.client.model.IColoredBakedQuad");
/* 250 */   public static ReflectorClass IExtendedBlockState = new ReflectorClass("net.minecraftforge.common.property.IExtendedBlockState");
/* 251 */   public static ReflectorMethod IExtendedBlockState_getClean = new ReflectorMethod(IExtendedBlockState, "getClean");
/* 252 */   public static ReflectorClass IModel = new ReflectorClass("net.minecraftforge.client.model.IModel");
/* 253 */   public static ReflectorMethod IModel_getTextures = new ReflectorMethod(IModel, "getTextures");
/* 254 */   public static ReflectorClass IRenderHandler = new ReflectorClass("net.minecraftforge.Client.getInstance()RenderHandler");
/* 255 */   public static ReflectorMethod IRenderHandler_render = new ReflectorMethod(IRenderHandler, "render");
/* 256 */   public static ReflectorClass ItemModelMesherForge = new ReflectorClass("net.minecraftforge.Client.getInstance()temModelMesherForge");
/* 257 */   public static ReflectorConstructor ItemModelMesherForge_Constructor = new ReflectorConstructor(ItemModelMesherForge, new Class[] { ModelManager.class });
/* 258 */   public static ReflectorClass Launch = new ReflectorClass("net.minecraft.launchwrapper.Launch");
/* 259 */   public static ReflectorField Launch_blackboard = new ReflectorField(Launch, "blackboard");
/* 260 */   public static ReflectorClass LightUtil = new ReflectorClass("net.minecraftforge.client.model.pipeline.LightUtil");
/* 261 */   public static ReflectorField LightUtil_itemConsumer = new ReflectorField(LightUtil, "itemConsumer");
/* 262 */   public static ReflectorMethod LightUtil_putBakedQuad = new ReflectorMethod(LightUtil, "putBakedQuad");
/* 263 */   public static ReflectorMethod LightUtil_renderQuadColor = new ReflectorMethod(LightUtil, "renderQuadColor");
/* 264 */   public static ReflectorField LightUtil_tessellator = new ReflectorField(LightUtil, "tessellator");
/* 265 */   public static ReflectorClass Loader = new ReflectorClass("net.minecraftforge.fml.common.Loader");
/* 266 */   public static ReflectorMethod Loader_getActiveModList = new ReflectorMethod(Loader, "getActiveModList");
/* 267 */   public static ReflectorMethod Loader_instance = new ReflectorMethod(Loader, "instance");
/* 268 */   public static ReflectorClass MinecraftForge = new ReflectorClass("net.minecraftforge.common.MinecraftForge");
/* 269 */   public static ReflectorField MinecraftForge_EVENT_BUS = new ReflectorField(MinecraftForge, "EVENT_BUS");
/* 270 */   public static ReflectorClass MinecraftForgeClient = new ReflectorClass("net.minecraftforge.client.MinecraftForgeClient");
/* 271 */   public static ReflectorMethod MinecraftForgeClient_getRenderPass = new ReflectorMethod(MinecraftForgeClient, "getRenderPass");
/* 272 */   public static ReflectorMethod MinecraftForgeClient_onRebuildChunk = new ReflectorMethod(MinecraftForgeClient, "onRebuildChunk");
/* 273 */   public static ReflectorClass ModContainer = new ReflectorClass("net.minecraftforge.fml.common.ModContainer");
/* 274 */   public static ReflectorMethod ModContainer_getModId = new ReflectorMethod(ModContainer, "getModId");
/* 275 */   public static ReflectorClass ModelLoader = new ReflectorClass("net.minecraftforge.client.model.ModelLoader");
/* 276 */   public static ReflectorField ModelLoader_stateModels = new ReflectorField(ModelLoader, "stateModels");
/* 277 */   public static ReflectorMethod ModelLoader_onRegisterItems = new ReflectorMethod(ModelLoader, "onRegisterItems");
/* 278 */   public static ReflectorMethod ModelLoader_getInventoryVariant = new ReflectorMethod(ModelLoader, "getInventoryVariant");
/* 279 */   public static ReflectorField ModelLoader_textures = new ReflectorField(ModelLoader, "textures");
/* 280 */   public static ReflectorClass ModelLoader_VanillaLoader = new ReflectorClass("net.minecraftforge.client.model.ModelLoader$VanillaLoader");
/* 281 */   public static ReflectorField ModelLoader_VanillaLoader_INSTANCE = new ReflectorField(ModelLoader_VanillaLoader, "instance");
/* 282 */   public static ReflectorMethod ModelLoader_VanillaLoader_loadModel = new ReflectorMethod(ModelLoader_VanillaLoader, "loadModel");
/* 283 */   public static ReflectorClass RenderBlockOverlayEvent_OverlayType = new ReflectorClass("net.minecraftforge.client.event.RenderBlockOverlayEvent$OverlayType");
/* 284 */   public static ReflectorField RenderBlockOverlayEvent_OverlayType_BLOCK = new ReflectorField(RenderBlockOverlayEvent_OverlayType, "BLOCK");
/* 285 */   public static ReflectorClass RenderingRegistry = new ReflectorClass("net.minecraftforge.fml.client.registry.RenderingRegistry");
/* 286 */   public static ReflectorMethod RenderingRegistry_loadEntityRenderers = new ReflectorMethod(RenderingRegistry, "loadEntityRenderers", new Class[] { RenderManager.class, Map.class });
/* 287 */   public static ReflectorClass RenderItemInFrameEvent = new ReflectorClass("net.minecraftforge.client.event.RenderItemInFrameEvent");
/* 288 */   public static ReflectorConstructor RenderItemInFrameEvent_Constructor = new ReflectorConstructor(RenderItemInFrameEvent, new Class[] { EntityItemFrame.class, RenderItemFrame.class });
/* 289 */   public static ReflectorClass RenderLivingEvent_Pre = new ReflectorClass("net.minecraftforge.client.event.RenderLivingEvent$Pre");
/* 290 */   public static ReflectorConstructor RenderLivingEvent_Pre_Constructor = new ReflectorConstructor(RenderLivingEvent_Pre, new Class[] { EntityLivingBase.class, RendererLivingEntity.class, double.class, double.class, double.class });
/* 291 */   public static ReflectorClass RenderLivingEvent_Post = new ReflectorClass("net.minecraftforge.client.event.RenderLivingEvent$Post");
/* 292 */   public static ReflectorConstructor RenderLivingEvent_Post_Constructor = new ReflectorConstructor(RenderLivingEvent_Post, new Class[] { EntityLivingBase.class, RendererLivingEntity.class, double.class, double.class, double.class });
/* 293 */   public static ReflectorClass RenderLivingEvent_Specials_Pre = new ReflectorClass("net.minecraftforge.client.event.RenderLivingEvent$Specials$Pre");
/* 294 */   public static ReflectorConstructor RenderLivingEvent_Specials_Pre_Constructor = new ReflectorConstructor(RenderLivingEvent_Specials_Pre, new Class[] { EntityLivingBase.class, RendererLivingEntity.class, double.class, double.class, double.class });
/* 295 */   public static ReflectorClass RenderLivingEvent_Specials_Post = new ReflectorClass("net.minecraftforge.client.event.RenderLivingEvent$Specials$Post");
/* 296 */   public static ReflectorConstructor RenderLivingEvent_Specials_Post_Constructor = new ReflectorConstructor(RenderLivingEvent_Specials_Post, new Class[] { EntityLivingBase.class, RendererLivingEntity.class, double.class, double.class, double.class });
/* 297 */   public static ReflectorClass SplashScreen = new ReflectorClass("net.minecraftforge.fml.client.SplashProgress");
/* 298 */   public static ReflectorClass WorldEvent_Load = new ReflectorClass("net.minecraftforge.event.world.WorldEvent$Load");
/* 299 */   public static ReflectorConstructor WorldEvent_Load_Constructor = new ReflectorConstructor(WorldEvent_Load, new Class[] { World.class });
/* 300 */   private static boolean logVanilla = logEntry("*** Reflector Vanilla ***");
/* 301 */   public static ReflectorClass ChunkProviderClient = new ReflectorClass(ChunkProviderClient.class);
/* 302 */   public static ReflectorField ChunkProviderClient_chunkMapping = new ReflectorField(ChunkProviderClient, LongHashMap.class);
/* 303 */   public static ReflectorClass EntityVillager = new ReflectorClass(EntityVillager.class);
/* 304 */   public static ReflectorField EntityVillager_careerId = new ReflectorField(new FieldLocatorTypes(EntityVillager.class, new Class[0], int.class, new Class[] { int.class, boolean.class, boolean.class, InventoryBasic.class }, "EntityVillager.careerId"));
/* 305 */   public static ReflectorField EntityVillager_careerLevel = new ReflectorField(new FieldLocatorTypes(EntityVillager.class, new Class[] { int.class }, int.class, new Class[] { boolean.class, boolean.class, InventoryBasic.class }, "EntityVillager.careerLevel"));
/* 306 */   public static ReflectorClass GuiBeacon = new ReflectorClass(GuiBeacon.class);
/* 307 */   public static ReflectorField GuiBeacon_tileBeacon = new ReflectorField(GuiBeacon, IInventory.class);
/* 308 */   public static ReflectorClass GuiBrewingStand = new ReflectorClass(GuiBrewingStand.class);
/* 309 */   public static ReflectorField GuiBrewingStand_tileBrewingStand = new ReflectorField(GuiBrewingStand, IInventory.class);
/* 310 */   public static ReflectorClass GuiChest = new ReflectorClass(GuiChest.class);
/* 311 */   public static ReflectorField GuiChest_lowerChestInventory = new ReflectorField(GuiChest, IInventory.class, 1);
/* 312 */   public static ReflectorClass GuiEnchantment = new ReflectorClass(GuiEnchantment.class);
/* 313 */   public static ReflectorField GuiEnchantment_nameable = new ReflectorField(GuiEnchantment, IWorldNameable.class);
/* 314 */   public static ReflectorClass GuiFurnace = new ReflectorClass(GuiFurnace.class);
/* 315 */   public static ReflectorField GuiFurnace_tileFurnace = new ReflectorField(GuiFurnace, IInventory.class);
/* 316 */   public static ReflectorClass GuiHopper = new ReflectorClass(GuiHopper.class);
/* 317 */   public static ReflectorField GuiHopper_hopperInventory = new ReflectorField(GuiHopper, IInventory.class, 1);
/* 318 */   public static ReflectorClass GuiMainMenu = new ReflectorClass(GuiMainMenu.class);
/* 319 */   public static ReflectorField GuiMainMenu_splashText = new ReflectorField(GuiMainMenu, String.class);
/* 320 */   public static ReflectorClass Minecraft = new ReflectorClass(Minecraft.class);
/* 321 */   public static ReflectorField Minecraft_defaultResourcePack = new ReflectorField(Minecraft, DefaultResourcePack.class);
/* 322 */   public static ReflectorClass ModelHumanoidHead = new ReflectorClass(ModelHumanoidHead.class);
/* 323 */   public static ReflectorField ModelHumanoidHead_head = new ReflectorField(ModelHumanoidHead, ModelRenderer.class);
/* 324 */   public static ReflectorClass ModelBat = new ReflectorClass(ModelBat.class);
/* 325 */   public static ReflectorFields ModelBat_ModelRenderers = new ReflectorFields(ModelBat, ModelRenderer.class, 6);
/* 326 */   public static ReflectorClass ModelBlaze = new ReflectorClass(ModelBlaze.class);
/* 327 */   public static ReflectorField ModelBlaze_blazeHead = new ReflectorField(ModelBlaze, ModelRenderer.class);
/* 328 */   public static ReflectorField ModelBlaze_blazeSticks = new ReflectorField(ModelBlaze, ModelRenderer[].class);
/* 329 */   public static ReflectorClass ModelBlock = new ReflectorClass(ModelBlock.class);
/* 330 */   public static ReflectorField ModelBlock_parentLocation = new ReflectorField(ModelBlock, ResourceLocation.class);
/* 331 */   public static ReflectorField ModelBlock_textures = new ReflectorField(ModelBlock, Map.class);
/* 332 */   public static ReflectorClass ModelDragon = new ReflectorClass(ModelDragon.class);
/* 333 */   public static ReflectorFields ModelDragon_ModelRenderers = new ReflectorFields(ModelDragon, ModelRenderer.class, 12);
/* 334 */   public static ReflectorClass ModelEnderCrystal = new ReflectorClass(ModelEnderCrystal.class);
/* 335 */   public static ReflectorFields ModelEnderCrystal_ModelRenderers = new ReflectorFields(ModelEnderCrystal, ModelRenderer.class, 3);
/* 336 */   public static ReflectorClass RenderEnderCrystal = new ReflectorClass(RenderEnderCrystal.class);
/* 337 */   public static ReflectorField RenderEnderCrystal_modelEnderCrystal = new ReflectorField(RenderEnderCrystal, ModelBase.class, 0);
/* 338 */   public static ReflectorClass ModelEnderMite = new ReflectorClass(ModelEnderMite.class);
/* 339 */   public static ReflectorField ModelEnderMite_bodyParts = new ReflectorField(ModelEnderMite, ModelRenderer[].class);
/* 340 */   public static ReflectorClass ModelGhast = new ReflectorClass(ModelGhast.class);
/* 341 */   public static ReflectorField ModelGhast_body = new ReflectorField(ModelGhast, ModelRenderer.class);
/* 342 */   public static ReflectorField ModelGhast_tentacles = new ReflectorField(ModelGhast, ModelRenderer[].class);
/* 343 */   public static ReflectorClass ModelGuardian = new ReflectorClass(ModelGuardian.class);
/* 344 */   public static ReflectorField ModelGuardian_body = new ReflectorField(ModelGuardian, ModelRenderer.class, 0);
/* 345 */   public static ReflectorField ModelGuardian_eye = new ReflectorField(ModelGuardian, ModelRenderer.class, 1);
/* 346 */   public static ReflectorField ModelGuardian_spines = new ReflectorField(ModelGuardian, ModelRenderer[].class, 0);
/* 347 */   public static ReflectorField ModelGuardian_tail = new ReflectorField(ModelGuardian, ModelRenderer[].class, 1);
/* 348 */   public static ReflectorClass ModelHorse = new ReflectorClass(ModelHorse.class);
/* 349 */   public static ReflectorFields ModelHorse_ModelRenderers = new ReflectorFields(ModelHorse, ModelRenderer.class, 39);
/* 350 */   public static ReflectorClass RenderLeashKnot = new ReflectorClass(RenderLeashKnot.class);
/* 351 */   public static ReflectorField RenderLeashKnot_leashKnotModel = new ReflectorField(RenderLeashKnot, ModelLeashKnot.class);
/* 352 */   public static ReflectorClass ModelMagmaCube = new ReflectorClass(ModelMagmaCube.class);
/* 353 */   public static ReflectorField ModelMagmaCube_core = new ReflectorField(ModelMagmaCube, ModelRenderer.class);
/* 354 */   public static ReflectorField ModelMagmaCube_segments = new ReflectorField(ModelMagmaCube, ModelRenderer[].class);
/* 355 */   public static ReflectorClass ModelOcelot = new ReflectorClass(ModelOcelot.class);
/* 356 */   public static ReflectorFields ModelOcelot_ModelRenderers = new ReflectorFields(ModelOcelot, ModelRenderer.class, 8);
/* 357 */   public static ReflectorClass ModelRabbit = new ReflectorClass(ModelRabbit.class);
/* 358 */   public static ReflectorFields ModelRabbit_renderers = new ReflectorFields(ModelRabbit, ModelRenderer.class, 12);
/* 359 */   public static ReflectorClass ModelSilverfish = new ReflectorClass(ModelSilverfish.class);
/* 360 */   public static ReflectorField ModelSilverfish_bodyParts = new ReflectorField(ModelSilverfish, ModelRenderer[].class, 0);
/* 361 */   public static ReflectorField ModelSilverfish_wingParts = new ReflectorField(ModelSilverfish, ModelRenderer[].class, 1);
/* 362 */   public static ReflectorClass ModelSlime = new ReflectorClass(ModelSlime.class);
/* 363 */   public static ReflectorFields ModelSlime_ModelRenderers = new ReflectorFields(ModelSlime, ModelRenderer.class, 4);
/* 364 */   public static ReflectorClass ModelSquid = new ReflectorClass(ModelSquid.class);
/* 365 */   public static ReflectorField ModelSquid_body = new ReflectorField(ModelSquid, ModelRenderer.class);
/* 366 */   public static ReflectorField ModelSquid_tentacles = new ReflectorField(ModelSquid, ModelRenderer[].class);
/* 367 */   public static ReflectorClass ModelWitch = new ReflectorClass(ModelWitch.class);
/* 368 */   public static ReflectorField ModelWitch_mole = new ReflectorField(ModelWitch, ModelRenderer.class, 0);
/* 369 */   public static ReflectorField ModelWitch_hat = new ReflectorField(ModelWitch, ModelRenderer.class, 1);
/* 370 */   public static ReflectorClass ModelWither = new ReflectorClass(ModelWither.class);
/* 371 */   public static ReflectorField ModelWither_bodyParts = new ReflectorField(ModelWither, ModelRenderer[].class, 0);
/* 372 */   public static ReflectorField ModelWither_heads = new ReflectorField(ModelWither, ModelRenderer[].class, 1);
/* 373 */   public static ReflectorClass ModelWolf = new ReflectorClass(ModelWolf.class);
/* 374 */   public static ReflectorField ModelWolf_tail = new ReflectorField(ModelWolf, ModelRenderer.class, 6);
/* 375 */   public static ReflectorField ModelWolf_mane = new ReflectorField(ModelWolf, ModelRenderer.class, 7);
/* 376 */   public static ReflectorClass OptiFineClassTransformer = new ReflectorClass("optifine.OptiFineClassTransformer");
/* 377 */   public static ReflectorField OptiFineClassTransformer_instance = new ReflectorField(OptiFineClassTransformer, "instance");
/* 378 */   public static ReflectorMethod OptiFineClassTransformer_getOptiFineResource = new ReflectorMethod(OptiFineClassTransformer, "getOptiFineResource");
/* 379 */   public static ReflectorClass RenderBoat = new ReflectorClass(RenderBoat.class);
/* 380 */   public static ReflectorField RenderBoat_modelBoat = new ReflectorField(RenderBoat, ModelBase.class);
/* 381 */   public static ReflectorClass RenderMinecart = new ReflectorClass(RenderMinecart.class);
/* 382 */   public static ReflectorField RenderMinecart_modelMinecart = new ReflectorField(RenderMinecart, ModelBase.class);
/* 383 */   public static ReflectorClass RenderWitherSkull = new ReflectorClass(RenderWitherSkull.class);
/* 384 */   public static ReflectorField RenderWitherSkull_model = new ReflectorField(RenderWitherSkull, ModelSkeletonHead.class);
/* 385 */   public static ReflectorClass TileEntityBannerRenderer = new ReflectorClass(TileEntityBannerRenderer.class);
/* 386 */   public static ReflectorField TileEntityBannerRenderer_bannerModel = new ReflectorField(TileEntityBannerRenderer, ModelBanner.class);
/* 387 */   public static ReflectorClass TileEntityBeacon = new ReflectorClass(TileEntityBeacon.class);
/* 388 */   public static ReflectorField TileEntityBeacon_customName = new ReflectorField(TileEntityBeacon, String.class);
/* 389 */   public static ReflectorClass TileEntityBrewingStand = new ReflectorClass(TileEntityBrewingStand.class);
/* 390 */   public static ReflectorField TileEntityBrewingStand_customName = new ReflectorField(TileEntityBrewingStand, String.class);
/* 391 */   public static ReflectorClass TileEntityChestRenderer = new ReflectorClass(TileEntityChestRenderer.class);
/* 392 */   public static ReflectorField TileEntityChestRenderer_simpleChest = new ReflectorField(TileEntityChestRenderer, ModelChest.class, 0);
/* 393 */   public static ReflectorField TileEntityChestRenderer_largeChest = new ReflectorField(TileEntityChestRenderer, ModelChest.class, 1);
/* 394 */   public static ReflectorClass TileEntityEnchantmentTable = new ReflectorClass(TileEntityEnchantmentTable.class);
/* 395 */   public static ReflectorField TileEntityEnchantmentTable_customName = new ReflectorField(TileEntityEnchantmentTable, String.class);
/* 396 */   public static ReflectorClass TileEntityEnchantmentTableRenderer = new ReflectorClass(TileEntityEnchantmentTableRenderer.class);
/* 397 */   public static ReflectorField TileEntityEnchantmentTableRenderer_modelBook = new ReflectorField(TileEntityEnchantmentTableRenderer, ModelBook.class);
/* 398 */   public static ReflectorClass TileEntityEnderChestRenderer = new ReflectorClass(TileEntityEnderChestRenderer.class);
/* 399 */   public static ReflectorField TileEntityEnderChestRenderer_modelChest = new ReflectorField(TileEntityEnderChestRenderer, ModelChest.class);
/* 400 */   public static ReflectorClass TileEntityFurnace = new ReflectorClass(TileEntityFurnace.class);
/* 401 */   public static ReflectorField TileEntityFurnace_customName = new ReflectorField(TileEntityFurnace, String.class);
/* 402 */   public static ReflectorClass TileEntitySignRenderer = new ReflectorClass(TileEntitySignRenderer.class);
/* 403 */   public static ReflectorField TileEntitySignRenderer_model = new ReflectorField(TileEntitySignRenderer, ModelSign.class);
/* 404 */   public static ReflectorClass TileEntitySkullRenderer = new ReflectorClass(TileEntitySkullRenderer.class);
/* 405 */   public static ReflectorField TileEntitySkullRenderer_skeletonHead = new ReflectorField(TileEntitySkullRenderer, ModelSkeletonHead.class, 0);
/* 406 */   public static ReflectorField TileEntitySkullRenderer_humanoidHead = new ReflectorField(TileEntitySkullRenderer, ModelSkeletonHead.class, 1);
/*     */   
/*     */   public static void callVoid(ReflectorMethod refMethod, Object... params) {
/*     */     try {
/* 410 */       Method method = refMethod.getTargetMethod();
/*     */       
/* 412 */       if (method == null) {
/*     */         return;
/*     */       }
/*     */       
/* 416 */       method.invoke(null, params);
/* 417 */     } catch (Throwable throwable) {
/* 418 */       handleException(throwable, null, refMethod, params);
/*     */     } 
/*     */   }
/*     */   
/*     */   public static boolean callBoolean(ReflectorMethod refMethod, Object... params) {
/*     */     try {
/* 424 */       Method method = refMethod.getTargetMethod();
/*     */       
/* 426 */       if (method == null) {
/* 427 */         return false;
/*     */       }
/* 429 */       Boolean obool = (Boolean)method.invoke(null, params);
/* 430 */       return obool.booleanValue();
/*     */     }
/* 432 */     catch (Throwable throwable) {
/* 433 */       handleException(throwable, null, refMethod, params);
/* 434 */       return false;
/*     */     } 
/*     */   }
/*     */   
/*     */   public static int callInt(ReflectorMethod refMethod, Object... params) {
/*     */     try {
/* 440 */       Method method = refMethod.getTargetMethod();
/*     */       
/* 442 */       if (method == null) {
/* 443 */         return 0;
/*     */       }
/* 445 */       Integer integer = (Integer)method.invoke(null, params);
/* 446 */       return integer.intValue();
/*     */     }
/* 448 */     catch (Throwable throwable) {
/* 449 */       handleException(throwable, null, refMethod, params);
/* 450 */       return 0;
/*     */     } 
/*     */   }
/*     */   
/*     */   public static float callFloat(ReflectorMethod refMethod, Object... params) {
/*     */     try {
/* 456 */       Method method = refMethod.getTargetMethod();
/*     */       
/* 458 */       if (method == null) {
/* 459 */         return 0.0F;
/*     */       }
/* 461 */       Float f = (Float)method.invoke(null, params);
/* 462 */       return f.floatValue();
/*     */     }
/* 464 */     catch (Throwable throwable) {
/* 465 */       handleException(throwable, null, refMethod, params);
/* 466 */       return 0.0F;
/*     */     } 
/*     */   }
/*     */   
/*     */   public static double callDouble(ReflectorMethod refMethod, Object... params) {
/*     */     try {
/* 472 */       Method method = refMethod.getTargetMethod();
/*     */       
/* 474 */       if (method == null) {
/* 475 */         return 0.0D;
/*     */       }
/* 477 */       Double d0 = (Double)method.invoke(null, params);
/* 478 */       return d0.doubleValue();
/*     */     }
/* 480 */     catch (Throwable throwable) {
/* 481 */       handleException(throwable, null, refMethod, params);
/* 482 */       return 0.0D;
/*     */     } 
/*     */   }
/*     */   
/*     */   public static String callString(ReflectorMethod refMethod, Object... params) {
/*     */     try {
/* 488 */       Method method = refMethod.getTargetMethod();
/*     */       
/* 490 */       if (method == null) {
/* 491 */         return null;
/*     */       }
/* 493 */       String s = (String)method.invoke(null, params);
/* 494 */       return s;
/*     */     }
/* 496 */     catch (Throwable throwable) {
/* 497 */       handleException(throwable, null, refMethod, params);
/* 498 */       return null;
/*     */     } 
/*     */   }
/*     */   
/*     */   public static Object call(ReflectorMethod refMethod, Object... params) {
/*     */     try {
/* 504 */       Method method = refMethod.getTargetMethod();
/*     */       
/* 506 */       if (method == null) {
/* 507 */         return null;
/*     */       }
/* 509 */       Object object = method.invoke(null, params);
/* 510 */       return object;
/*     */     }
/* 512 */     catch (Throwable throwable) {
/* 513 */       handleException(throwable, null, refMethod, params);
/* 514 */       return null;
/*     */     } 
/*     */   }
/*     */   
/*     */   public static void callVoid(Object obj, ReflectorMethod refMethod, Object... params) {
/*     */     try {
/* 520 */       if (obj == null) {
/*     */         return;
/*     */       }
/*     */       
/* 524 */       Method method = refMethod.getTargetMethod();
/*     */       
/* 526 */       if (method == null) {
/*     */         return;
/*     */       }
/*     */       
/* 530 */       method.invoke(obj, params);
/* 531 */     } catch (Throwable throwable) {
/* 532 */       handleException(throwable, obj, refMethod, params);
/*     */     } 
/*     */   }
/*     */   
/*     */   public static boolean callBoolean(Object obj, ReflectorMethod refMethod, Object... params) {
/*     */     try {
/* 538 */       Method method = refMethod.getTargetMethod();
/*     */       
/* 540 */       if (method == null) {
/* 541 */         return false;
/*     */       }
/* 543 */       Boolean obool = (Boolean)method.invoke(obj, params);
/* 544 */       return obool.booleanValue();
/*     */     }
/* 546 */     catch (Throwable throwable) {
/* 547 */       handleException(throwable, obj, refMethod, params);
/* 548 */       return false;
/*     */     } 
/*     */   }
/*     */   
/*     */   public static int callInt(Object obj, ReflectorMethod refMethod, Object... params) {
/*     */     try {
/* 554 */       Method method = refMethod.getTargetMethod();
/*     */       
/* 556 */       if (method == null) {
/* 557 */         return 0;
/*     */       }
/* 559 */       Integer integer = (Integer)method.invoke(obj, params);
/* 560 */       return integer.intValue();
/*     */     }
/* 562 */     catch (Throwable throwable) {
/* 563 */       handleException(throwable, obj, refMethod, params);
/* 564 */       return 0;
/*     */     } 
/*     */   }
/*     */   
/*     */   public static float callFloat(Object obj, ReflectorMethod refMethod, Object... params) {
/*     */     try {
/* 570 */       Method method = refMethod.getTargetMethod();
/*     */       
/* 572 */       if (method == null) {
/* 573 */         return 0.0F;
/*     */       }
/* 575 */       Float f = (Float)method.invoke(obj, params);
/* 576 */       return f.floatValue();
/*     */     }
/* 578 */     catch (Throwable throwable) {
/* 579 */       handleException(throwable, obj, refMethod, params);
/* 580 */       return 0.0F;
/*     */     } 
/*     */   }
/*     */   
/*     */   public static double callDouble(Object obj, ReflectorMethod refMethod, Object... params) {
/*     */     try {
/* 586 */       Method method = refMethod.getTargetMethod();
/*     */       
/* 588 */       if (method == null) {
/* 589 */         return 0.0D;
/*     */       }
/* 591 */       Double d0 = (Double)method.invoke(obj, params);
/* 592 */       return d0.doubleValue();
/*     */     }
/* 594 */     catch (Throwable throwable) {
/* 595 */       handleException(throwable, obj, refMethod, params);
/* 596 */       return 0.0D;
/*     */     } 
/*     */   }
/*     */   
/*     */   public static String callString(Object obj, ReflectorMethod refMethod, Object... params) {
/*     */     try {
/* 602 */       Method method = refMethod.getTargetMethod();
/*     */       
/* 604 */       if (method == null) {
/* 605 */         return null;
/*     */       }
/* 607 */       String s = (String)method.invoke(obj, params);
/* 608 */       return s;
/*     */     }
/* 610 */     catch (Throwable throwable) {
/* 611 */       handleException(throwable, obj, refMethod, params);
/* 612 */       return null;
/*     */     } 
/*     */   }
/*     */   
/*     */   public static Object call(Object obj, ReflectorMethod refMethod, Object... params) {
/*     */     try {
/* 618 */       Method method = refMethod.getTargetMethod();
/*     */       
/* 620 */       if (method == null) {
/* 621 */         return null;
/*     */       }
/* 623 */       Object object = method.invoke(obj, params);
/* 624 */       return object;
/*     */     }
/* 626 */     catch (Throwable throwable) {
/* 627 */       handleException(throwable, obj, refMethod, params);
/* 628 */       return null;
/*     */     } 
/*     */   }
/*     */   
/*     */   public static Object getFieldValue(ReflectorField refField) {
/* 633 */     return getFieldValue((Object)null, refField);
/*     */   }
/*     */   
/*     */   public static Object getFieldValue(Object obj, ReflectorField refField) {
/*     */     try {
/* 638 */       Field field = refField.getTargetField();
/*     */       
/* 640 */       if (field == null) {
/* 641 */         return null;
/*     */       }
/* 643 */       Object object = field.get(obj);
/* 644 */       return object;
/*     */     }
/* 646 */     catch (Throwable throwable) {
/* 647 */       Log.error("", throwable);
/* 648 */       return null;
/*     */     } 
/*     */   }
/*     */   
/*     */   public static boolean getFieldValueBoolean(ReflectorField refField, boolean def) {
/*     */     try {
/* 654 */       Field field = refField.getTargetField();
/*     */       
/* 656 */       if (field == null) {
/* 657 */         return def;
/*     */       }
/* 659 */       boolean flag = field.getBoolean(null);
/* 660 */       return flag;
/*     */     }
/* 662 */     catch (Throwable throwable) {
/* 663 */       Log.error("", throwable);
/* 664 */       return def;
/*     */     } 
/*     */   }
/*     */   
/*     */   public static boolean getFieldValueBoolean(Object obj, ReflectorField refField, boolean def) {
/*     */     try {
/* 670 */       Field field = refField.getTargetField();
/*     */       
/* 672 */       if (field == null) {
/* 673 */         return def;
/*     */       }
/* 675 */       boolean flag = field.getBoolean(obj);
/* 676 */       return flag;
/*     */     }
/* 678 */     catch (Throwable throwable) {
/* 679 */       Log.error("", throwable);
/* 680 */       return def;
/*     */     } 
/*     */   }
/*     */   
/*     */   public static Object getFieldValue(ReflectorFields refFields, int index) {
/* 685 */     ReflectorField reflectorfield = refFields.getReflectorField(index);
/* 686 */     return (reflectorfield == null) ? null : getFieldValue(reflectorfield);
/*     */   }
/*     */   
/*     */   public static Object getFieldValue(Object obj, ReflectorFields refFields, int index) {
/* 690 */     ReflectorField reflectorfield = refFields.getReflectorField(index);
/* 691 */     return (reflectorfield == null) ? null : getFieldValue(obj, reflectorfield);
/*     */   }
/*     */   
/*     */   public static float getFieldValueFloat(Object obj, ReflectorField refField, float def) {
/*     */     try {
/* 696 */       Field field = refField.getTargetField();
/*     */       
/* 698 */       if (field == null) {
/* 699 */         return def;
/*     */       }
/* 701 */       float f = field.getFloat(obj);
/* 702 */       return f;
/*     */     }
/* 704 */     catch (Throwable throwable) {
/* 705 */       Log.error("", throwable);
/* 706 */       return def;
/*     */     } 
/*     */   }
/*     */   
/*     */   public static int getFieldValueInt(Object obj, ReflectorField refField, int def) {
/*     */     try {
/* 712 */       Field field = refField.getTargetField();
/*     */       
/* 714 */       if (field == null) {
/* 715 */         return def;
/*     */       }
/* 717 */       int i = field.getInt(obj);
/* 718 */       return i;
/*     */     }
/* 720 */     catch (Throwable throwable) {
/* 721 */       Log.error("", throwable);
/* 722 */       return def;
/*     */     } 
/*     */   }
/*     */   
/*     */   public static long getFieldValueLong(Object obj, ReflectorField refField, long def) {
/*     */     try {
/* 728 */       Field field = refField.getTargetField();
/*     */       
/* 730 */       if (field == null) {
/* 731 */         return def;
/*     */       }
/* 733 */       long i = field.getLong(obj);
/* 734 */       return i;
/*     */     }
/* 736 */     catch (Throwable throwable) {
/* 737 */       Log.error("", throwable);
/* 738 */       return def;
/*     */     } 
/*     */   }
/*     */   
/*     */   public static boolean setFieldValue(ReflectorField refField, Object value) {
/* 743 */     return setFieldValue(null, refField, value);
/*     */   }
/*     */   
/*     */   public static boolean setFieldValue(Object obj, ReflectorField refField, Object value) {
/*     */     try {
/* 748 */       Field field = refField.getTargetField();
/*     */       
/* 750 */       if (field == null) {
/* 751 */         return false;
/*     */       }
/* 753 */       field.set(obj, value);
/* 754 */       return true;
/*     */     }
/* 756 */     catch (Throwable throwable) {
/* 757 */       Log.error("", throwable);
/* 758 */       return false;
/*     */     } 
/*     */   }
/*     */   
/*     */   public static boolean setFieldValueInt(ReflectorField refField, int value) {
/* 763 */     return setFieldValueInt(null, refField, value);
/*     */   }
/*     */   
/*     */   public static boolean setFieldValueInt(Object obj, ReflectorField refField, int value) {
/*     */     try {
/* 768 */       Field field = refField.getTargetField();
/*     */       
/* 770 */       if (field == null) {
/* 771 */         return false;
/*     */       }
/* 773 */       field.setInt(obj, value);
/* 774 */       return true;
/*     */     }
/* 776 */     catch (Throwable throwable) {
/* 777 */       Log.error("", throwable);
/* 778 */       return false;
/*     */     } 
/*     */   }
/*     */   
/*     */   public static boolean postForgeBusEvent(ReflectorConstructor constr, Object... params) {
/* 783 */     Object object = newInstance(constr, params);
/* 784 */     return (object == null) ? false : postForgeBusEvent(object);
/*     */   }
/*     */   
/*     */   public static boolean postForgeBusEvent(Object event) {
/* 788 */     if (event == null) {
/* 789 */       return false;
/*     */     }
/* 791 */     Object object = getFieldValue(MinecraftForge_EVENT_BUS);
/*     */     
/* 793 */     if (object == null) {
/* 794 */       return false;
/*     */     }
/* 796 */     Object object1 = call(object, EventBus_post, new Object[] { event });
/*     */     
/* 798 */     if (!(object1 instanceof Boolean)) {
/* 799 */       return false;
/*     */     }
/* 801 */     Boolean obool = (Boolean)object1;
/* 802 */     return obool.booleanValue();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static Object newInstance(ReflectorConstructor constr, Object... params) {
/* 809 */     Constructor constructor = constr.getTargetConstructor();
/*     */     
/* 811 */     if (constructor == null) {
/* 812 */       return null;
/*     */     }
/*     */     try {
/* 815 */       Object object = constructor.newInstance(params);
/* 816 */       return object;
/* 817 */     } catch (Throwable throwable) {
/* 818 */       handleException(throwable, constr, params);
/* 819 */       return null;
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public static boolean matchesTypes(Class[] pTypes, Class[] cTypes) {
/* 825 */     if (pTypes.length != cTypes.length) {
/* 826 */       return false;
/*     */     }
/* 828 */     for (int i = 0; i < cTypes.length; i++) {
/* 829 */       Class oclass = pTypes[i];
/* 830 */       Class oclass1 = cTypes[i];
/*     */       
/* 832 */       if (oclass != oclass1) {
/* 833 */         return false;
/*     */       }
/*     */     } 
/*     */     
/* 837 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   private static void dbgCall(boolean isStatic, String callType, ReflectorMethod refMethod, Object[] params, Object retVal) {
/* 842 */     String s = refMethod.getTargetMethod().getDeclaringClass().getName();
/* 843 */     String s1 = refMethod.getTargetMethod().getName();
/* 844 */     String s2 = "";
/*     */     
/* 846 */     if (isStatic) {
/* 847 */       s2 = " static";
/*     */     }
/*     */     
/* 850 */     Log.dbg(String.valueOf(callType) + s2 + " " + s + "." + s1 + "(" + ArrayUtils.arrayToString(params) + ") => " + retVal);
/*     */   }
/*     */   
/*     */   private static void dbgCallVoid(boolean isStatic, String callType, ReflectorMethod refMethod, Object[] params) {
/* 854 */     String s = refMethod.getTargetMethod().getDeclaringClass().getName();
/* 855 */     String s1 = refMethod.getTargetMethod().getName();
/* 856 */     String s2 = "";
/*     */     
/* 858 */     if (isStatic) {
/* 859 */       s2 = " static";
/*     */     }
/*     */     
/* 862 */     Log.dbg(String.valueOf(callType) + s2 + " " + s + "." + s1 + "(" + ArrayUtils.arrayToString(params) + ")");
/*     */   }
/*     */   
/*     */   private static void dbgFieldValue(boolean isStatic, String accessType, ReflectorField refField, Object val) {
/* 866 */     String s = refField.getTargetField().getDeclaringClass().getName();
/* 867 */     String s1 = refField.getTargetField().getName();
/* 868 */     String s2 = "";
/*     */     
/* 870 */     if (isStatic) {
/* 871 */       s2 = " static";
/*     */     }
/*     */     
/* 874 */     Log.dbg(String.valueOf(accessType) + s2 + " " + s + "." + s1 + " => " + val);
/*     */   }
/*     */   
/*     */   private static void handleException(Throwable e, Object obj, ReflectorMethod refMethod, Object[] params) {
/* 878 */     if (e instanceof java.lang.reflect.InvocationTargetException) {
/* 879 */       Throwable throwable = e.getCause();
/*     */       
/* 881 */       if (throwable instanceof RuntimeException) {
/* 882 */         RuntimeException runtimeexception = (RuntimeException)throwable;
/* 883 */         throw runtimeexception;
/*     */       } 
/* 885 */       Log.error("", e);
/*     */     } else {
/*     */       
/* 888 */       Log.warn("*** Exception outside of method ***");
/* 889 */       Log.warn("Method deactivated: " + refMethod.getTargetMethod());
/* 890 */       refMethod.deactivate();
/*     */       
/* 892 */       if (e instanceof IllegalArgumentException) {
/* 893 */         Log.warn("*** IllegalArgumentException ***");
/* 894 */         Log.warn("Method: " + refMethod.getTargetMethod());
/* 895 */         Log.warn("Object: " + obj);
/* 896 */         Log.warn("Parameter classes: " + ArrayUtils.arrayToString(getClasses(params)));
/* 897 */         Log.warn("Parameters: " + ArrayUtils.arrayToString(params));
/*     */       } 
/*     */       
/* 900 */       Log.warn("", e);
/*     */     } 
/*     */   }
/*     */   
/*     */   private static void handleException(Throwable e, ReflectorConstructor refConstr, Object[] params) {
/* 905 */     if (e instanceof java.lang.reflect.InvocationTargetException) {
/* 906 */       Log.error("", e);
/*     */     } else {
/* 908 */       Log.warn("*** Exception outside of constructor ***");
/* 909 */       Log.warn("Constructor deactivated: " + refConstr.getTargetConstructor());
/* 910 */       refConstr.deactivate();
/*     */       
/* 912 */       if (e instanceof IllegalArgumentException) {
/* 913 */         Log.warn("*** IllegalArgumentException ***");
/* 914 */         Log.warn("Constructor: " + refConstr.getTargetConstructor());
/* 915 */         Log.warn("Parameter classes: " + ArrayUtils.arrayToString(getClasses(params)));
/* 916 */         Log.warn("Parameters: " + ArrayUtils.arrayToString(params));
/*     */       } 
/*     */       
/* 919 */       Log.warn("", e);
/*     */     } 
/*     */   }
/*     */   
/*     */   private static Object[] getClasses(Object[] objs) {
/* 924 */     if (objs == null) {
/* 925 */       return (Object[])new Class[0];
/*     */     }
/* 927 */     Class[] aclass = new Class[objs.length];
/*     */     
/* 929 */     for (int i = 0; i < aclass.length; i++) {
/* 930 */       Object object = objs[i];
/*     */       
/* 932 */       if (object != null) {
/* 933 */         aclass[i] = object.getClass();
/*     */       }
/*     */     } 
/*     */     
/* 937 */     return (Object[])aclass;
/*     */   }
/*     */ 
/*     */   
/*     */   private static ReflectorField[] getReflectorFields(ReflectorClass parentClass, Class fieldType, int count) {
/* 942 */     ReflectorField[] areflectorfield = new ReflectorField[count];
/*     */     
/* 944 */     for (int i = 0; i < areflectorfield.length; i++) {
/* 945 */       areflectorfield[i] = new ReflectorField(parentClass, fieldType, i);
/*     */     }
/*     */     
/* 948 */     return areflectorfield;
/*     */   }
/*     */   
/*     */   private static boolean logEntry(String str) {
/* 952 */     LOGGER.info("[OptiFine] " + str);
/* 953 */     return true;
/*     */   }
/*     */   
/*     */   private static boolean registerResolvable(final String str) {
/* 957 */     IResolvable iresolvable = new IResolvable() {
/*     */         public void resolve() {
/* 959 */           Reflector.LOGGER.info("[OptiFine] " + str);
/*     */         }
/*     */       };
/* 962 */     ReflectorResolver.register(iresolvable);
/* 963 */     return true;
/*     */   }
/*     */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\optifine\reflect\Reflector.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */