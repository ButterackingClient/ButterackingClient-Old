/*     */ package net.minecraft.client.entity;
/*     */ 
/*     */ import com.mojang.authlib.GameProfile;
/*     */ import net.minecraft.client.Minecraft;
/*     */ import net.minecraft.client.network.NetworkPlayerInfo;
/*     */ import net.minecraft.client.renderer.IImageBuffer;
/*     */ import net.minecraft.client.renderer.ImageBufferDownload;
/*     */ import net.minecraft.client.renderer.ThreadDownloadImageData;
/*     */ import net.minecraft.client.renderer.texture.ITextureObject;
/*     */ import net.minecraft.client.renderer.texture.TextureManager;
/*     */ import net.minecraft.client.resources.DefaultPlayerSkin;
/*     */ import net.minecraft.entity.SharedMonsterAttributes;
/*     */ import net.minecraft.entity.ai.attributes.IAttributeInstance;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.init.Items;
/*     */ import net.minecraft.src.Config;
/*     */ import net.minecraft.util.ResourceLocation;
/*     */ import net.minecraft.util.StringUtils;
/*     */ import net.minecraft.util.Vec3;
/*     */ import net.minecraft.world.World;
/*     */ import net.minecraft.world.WorldSettings;
/*     */ import net.optifine.player.CapeUtils;
/*     */ import net.optifine.player.PlayerConfigurations;
/*     */ import net.optifine.reflect.Reflector;
/*     */ 
/*     */ public abstract class AbstractClientPlayer
/*     */   extends EntityPlayer
/*     */ {
/*     */   private NetworkPlayerInfo playerInfo;
/*  30 */   private ResourceLocation locationOfCape = null;
/*  31 */   private long reloadCapeTimeMs = 0L;
/*     */   private boolean elytraOfCape = false;
/*  33 */   private String nameClear = null;
/*  34 */   private static final ResourceLocation TEXTURE_ELYTRA = new ResourceLocation("textures/entity/elytra.png");
/*     */   
/*     */   public AbstractClientPlayer(World worldIn, GameProfile playerProfile) {
/*  37 */     super(worldIn, playerProfile);
/*  38 */     this.nameClear = playerProfile.getName();
/*     */     
/*  40 */     if (this.nameClear != null && !this.nameClear.isEmpty()) {
/*  41 */       this.nameClear = StringUtils.stripControlCodes(this.nameClear);
/*     */     }
/*     */     
/*  44 */     CapeUtils.downloadCape(this);
/*  45 */     PlayerConfigurations.getPlayerConfiguration(this);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isSpectator() {
/*  52 */     NetworkPlayerInfo networkplayerinfo = Minecraft.getMinecraft().getNetHandler().getPlayerInfo(getGameProfile().getId());
/*  53 */     return (networkplayerinfo != null && networkplayerinfo.getGameType() == WorldSettings.GameType.SPECTATOR);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean hasPlayerInfo() {
/*  60 */     return (getPlayerInfo() != null);
/*     */   }
/*     */   
/*     */   protected NetworkPlayerInfo getPlayerInfo() {
/*  64 */     if (this.playerInfo == null) {
/*  65 */       this.playerInfo = Minecraft.getMinecraft().getNetHandler().getPlayerInfo(getUniqueID());
/*     */     }
/*     */     
/*  68 */     return this.playerInfo;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean hasSkin() {
/*  75 */     NetworkPlayerInfo networkplayerinfo = getPlayerInfo();
/*  76 */     return (networkplayerinfo != null && networkplayerinfo.hasLocationSkin());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ResourceLocation getLocationSkin() {
/*  83 */     NetworkPlayerInfo networkplayerinfo = getPlayerInfo();
/*  84 */     return (networkplayerinfo == null) ? DefaultPlayerSkin.getDefaultSkin(getUniqueID()) : networkplayerinfo.getLocationSkin();
/*     */   }
/*     */   
/*     */   public ResourceLocation getLocationCape() {
/*  88 */     if (!Config.isShowCapes()) {
/*  89 */       return null;
/*     */     }
/*  91 */     if (this.reloadCapeTimeMs != 0L && System.currentTimeMillis() > this.reloadCapeTimeMs) {
/*  92 */       CapeUtils.reloadCape(this);
/*  93 */       this.reloadCapeTimeMs = 0L;
/*     */     } 
/*     */     
/*  96 */     if (this.locationOfCape != null) {
/*  97 */       return this.locationOfCape;
/*     */     }
/*  99 */     NetworkPlayerInfo networkplayerinfo = getPlayerInfo();
/* 100 */     return (networkplayerinfo == null) ? null : networkplayerinfo.getLocationCape();
/*     */   }
/*     */ 
/*     */   
/*     */   public static ThreadDownloadImageData getDownloadImageSkin(ResourceLocation resourceLocationIn, String username) {
/*     */     ThreadDownloadImageData threadDownloadImageData;
/* 106 */     TextureManager texturemanager = Minecraft.getMinecraft().getTextureManager();
/* 107 */     ITextureObject itextureobject = texturemanager.getTexture(resourceLocationIn);
/*     */     
/* 109 */     if (itextureobject == null) {
/* 110 */       threadDownloadImageData = new ThreadDownloadImageData(null, String.format("http://skins.minecraft.net/MinecraftSkins/%s.png", new Object[] { StringUtils.stripControlCodes(username) }), DefaultPlayerSkin.getDefaultSkin(getOfflineUUID(username)), (IImageBuffer)new ImageBufferDownload());
/* 111 */       texturemanager.loadTexture(resourceLocationIn, (ITextureObject)threadDownloadImageData);
/*     */     } 
/*     */     
/* 114 */     return threadDownloadImageData;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static ResourceLocation getLocationSkin(String username) {
/* 121 */     return new ResourceLocation("skins/" + StringUtils.stripControlCodes(username));
/*     */   }
/*     */   
/*     */   public String getSkinType() {
/* 125 */     NetworkPlayerInfo networkplayerinfo = getPlayerInfo();
/* 126 */     return (networkplayerinfo == null) ? DefaultPlayerSkin.getSkinType(getUniqueID()) : networkplayerinfo.getSkinType();
/*     */   }
/*     */   
/*     */   public float getFovModifier() {
/* 130 */     float f = 1.0F;
/*     */     
/* 132 */     if (this.capabilities.isFlying) {
/* 133 */       f *= 1.1F;
/*     */     }
/*     */     
/* 136 */     IAttributeInstance iattributeinstance = getEntityAttribute(SharedMonsterAttributes.movementSpeed);
/* 137 */     f = (float)(f * (iattributeinstance.getAttributeValue() / this.capabilities.getWalkSpeed() + 1.0D) / 2.0D);
/*     */     
/* 139 */     if (this.capabilities.getWalkSpeed() == 0.0F || Float.isNaN(f) || Float.isInfinite(f)) {
/* 140 */       f = 1.0F;
/*     */     }
/*     */     
/* 143 */     if (isUsingItem() && getItemInUse().getItem() == Items.bow) {
/* 144 */       int i = getItemInUseDuration();
/* 145 */       float f1 = i / 20.0F;
/*     */       
/* 147 */       if (f1 > 1.0F) {
/* 148 */         f1 = 1.0F;
/*     */       } else {
/* 150 */         f1 *= f1;
/*     */       } 
/*     */       
/* 153 */       f *= 1.0F - f1 * 0.15F;
/*     */     } 
/*     */     
/* 156 */     return Reflector.ForgeHooksClient_getOffsetFOV.exists() ? Reflector.callFloat(Reflector.ForgeHooksClient_getOffsetFOV, new Object[] { this, Float.valueOf(f) }) : f;
/*     */   }
/*     */   
/*     */   public String getNameClear() {
/* 160 */     return this.nameClear;
/*     */   }
/*     */   
/*     */   public ResourceLocation getLocationOfCape() {
/* 164 */     return this.locationOfCape;
/*     */   }
/*     */   
/*     */   public void setLocationOfCape(ResourceLocation p_setLocationOfCape_1_) {
/* 168 */     this.locationOfCape = p_setLocationOfCape_1_;
/*     */   }
/*     */   
/*     */   public boolean hasElytraCape() {
/* 172 */     ResourceLocation resourcelocation = getLocationCape();
/* 173 */     return (resourcelocation == null) ? false : ((resourcelocation == this.locationOfCape) ? this.elytraOfCape : true);
/*     */   }
/*     */   
/*     */   public void setElytraOfCape(boolean p_setElytraOfCape_1_) {
/* 177 */     this.elytraOfCape = p_setElytraOfCape_1_;
/*     */   }
/*     */   
/*     */   public boolean isElytraOfCape() {
/* 181 */     return this.elytraOfCape;
/*     */   }
/*     */   
/*     */   public long getReloadCapeTimeMs() {
/* 185 */     return this.reloadCapeTimeMs;
/*     */   }
/*     */   
/*     */   public void setReloadCapeTimeMs(long p_setReloadCapeTimeMs_1_) {
/* 189 */     this.reloadCapeTimeMs = p_setReloadCapeTimeMs_1_;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Vec3 getLook(float partialTicks) {
/* 196 */     return getVectorForRotation(this.rotationPitch, this.rotationYaw);
/*     */   }
/*     */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\minecraft\client\entity\AbstractClientPlayer.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */