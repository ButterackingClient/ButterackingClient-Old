/*     */ package net.minecraft.client.renderer.entity;
/*     */ 
/*     */ import cosmtcs.CosmeticWings;
/*     */ import net.minecraft.client.entity.AbstractClientPlayer;
/*     */ import net.minecraft.client.model.ModelBase;
/*     */ import net.minecraft.client.model.ModelPlayer;
/*     */ import net.minecraft.client.renderer.GlStateManager;
/*     */ import net.minecraft.client.renderer.entity.layers.LayerArrow;
/*     */ import net.minecraft.client.renderer.entity.layers.LayerBipedArmor;
/*     */ import net.minecraft.client.renderer.entity.layers.LayerCape;
/*     */ import net.minecraft.client.renderer.entity.layers.LayerCustomHead;
/*     */ import net.minecraft.client.renderer.entity.layers.LayerDeadmau5Head;
/*     */ import net.minecraft.client.renderer.entity.layers.LayerHeldItem;
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.entity.EntityLivingBase;
/*     */ import net.minecraft.entity.player.EnumPlayerModelParts;
/*     */ import net.minecraft.item.EnumAction;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraft.scoreboard.Score;
/*     */ import net.minecraft.scoreboard.ScoreObjective;
/*     */ import net.minecraft.scoreboard.Scoreboard;
/*     */ import net.minecraft.util.ResourceLocation;
/*     */ 
/*     */ public class RenderPlayer
/*     */   extends RendererLivingEntity<AbstractClientPlayer> {
/*     */   private boolean smallArms;
/*     */   
/*     */   public RenderPlayer(RenderManager renderManager) {
/*  29 */     this(renderManager, false);
/*     */   }
/*     */   
/*     */   public RenderPlayer(RenderManager renderManager, boolean useSmallArms) {
/*  33 */     super(renderManager, (ModelBase)new ModelPlayer(0.0F, useSmallArms), 0.5F);
/*  34 */     this.smallArms = useSmallArms;
/*  35 */     addLayer(new LayerBipedArmor(this));
/*  36 */     addLayer(new LayerHeldItem(this));
/*  37 */     addLayer(new LayerArrow(this));
/*  38 */     addLayer(new LayerDeadmau5Head(this));
/*  39 */     addLayer(new LayerCape(this));
/*  40 */     addLayer(new CosmeticWings(this));
/*  41 */     addLayer(new LayerCustomHead((getMainModel()).bipedHead));
/*     */   }
/*     */   
/*     */   public ModelPlayer getMainModel() {
/*  45 */     return (ModelPlayer)super.getMainModel();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void doRender(AbstractClientPlayer entity, double x, double y, double z, float entityYaw, float partialTicks) {
/*  52 */     if (!entity.isUser() || this.renderManager.livingPlayer == entity) {
/*  53 */       double d0 = y;
/*     */       
/*  55 */       if (entity.isSneaking() && !(entity instanceof net.minecraft.client.entity.EntityPlayerSP)) {
/*  56 */         d0 = y - 0.125D;
/*     */       }
/*     */       
/*  59 */       setModelVisibilities(entity);
/*  60 */       super.doRender(entity, x, d0, z, entityYaw, partialTicks);
/*     */     } 
/*     */   }
/*     */   
/*     */   private void setModelVisibilities(AbstractClientPlayer clientPlayer) {
/*  65 */     ModelPlayer modelplayer = getMainModel();
/*     */     
/*  67 */     if (clientPlayer.isSpectator()) {
/*  68 */       modelplayer.setInvisible(false);
/*  69 */       modelplayer.bipedHead.showModel = true;
/*  70 */       modelplayer.bipedHeadwear.showModel = true;
/*     */     } else {
/*  72 */       ItemStack itemstack = clientPlayer.inventory.getCurrentItem();
/*  73 */       modelplayer.setInvisible(true);
/*  74 */       modelplayer.bipedHeadwear.showModel = clientPlayer.isWearing(EnumPlayerModelParts.HAT);
/*  75 */       modelplayer.bipedBodyWear.showModel = clientPlayer.isWearing(EnumPlayerModelParts.JACKET);
/*  76 */       modelplayer.bipedLeftLegwear.showModel = clientPlayer.isWearing(EnumPlayerModelParts.LEFT_PANTS_LEG);
/*  77 */       modelplayer.bipedRightLegwear.showModel = clientPlayer.isWearing(EnumPlayerModelParts.RIGHT_PANTS_LEG);
/*  78 */       modelplayer.bipedLeftArmwear.showModel = clientPlayer.isWearing(EnumPlayerModelParts.LEFT_SLEEVE);
/*  79 */       modelplayer.bipedRightArmwear.showModel = clientPlayer.isWearing(EnumPlayerModelParts.RIGHT_SLEEVE);
/*  80 */       modelplayer.heldItemLeft = 0;
/*  81 */       modelplayer.aimedBow = false;
/*  82 */       modelplayer.isSneak = clientPlayer.isSneaking();
/*     */       
/*  84 */       if (itemstack == null) {
/*  85 */         modelplayer.heldItemRight = 0;
/*     */       } else {
/*  87 */         modelplayer.heldItemRight = 1;
/*     */         
/*  89 */         if (clientPlayer.getItemInUseCount() > 0) {
/*  90 */           EnumAction enumaction = itemstack.getItemUseAction();
/*     */           
/*  92 */           if (enumaction == EnumAction.BLOCK) {
/*  93 */             modelplayer.heldItemRight = 3;
/*  94 */           } else if (enumaction == EnumAction.BOW) {
/*  95 */             modelplayer.aimedBow = true;
/*     */           } 
/*     */         } 
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected ResourceLocation getEntityTexture(AbstractClientPlayer entity) {
/* 106 */     return entity.getLocationSkin();
/*     */   }
/*     */   
/*     */   public void transformHeldFull3DItemLayer() {
/* 110 */     GlStateManager.translate(0.0F, 0.1875F, 0.0F);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void preRenderCallback(AbstractClientPlayer entitylivingbaseIn, float partialTickTime) {
/* 118 */     float f = 0.9375F;
/* 119 */     GlStateManager.scale(f, f, f);
/*     */   }
/*     */   
/*     */   protected void renderOffsetLivingLabel(AbstractClientPlayer entityIn, double x, double y, double z, String str, float p_177069_9_, double p_177069_10_) {
/* 123 */     if (p_177069_10_ < 100.0D) {
/* 124 */       Scoreboard scoreboard = entityIn.getWorldScoreboard();
/* 125 */       ScoreObjective scoreobjective = scoreboard.getObjectiveInDisplaySlot(2);
/*     */       
/* 127 */       if (scoreobjective != null) {
/* 128 */         Score score = scoreboard.getValueFromObjective(entityIn.getName(), scoreobjective);
/* 129 */         renderLivingLabel(entityIn, String.valueOf(score.getScorePoints()) + " " + scoreobjective.getDisplayName(), x, y, z, 64);
/* 130 */         y += ((getFontRendererFromRenderManager()).FONT_HEIGHT * 1.15F * p_177069_9_);
/*     */       } 
/*     */     } 
/*     */     
/* 134 */     super.renderOffsetLivingLabel(entityIn, x, y, z, str, p_177069_9_, p_177069_10_);
/*     */   }
/*     */   
/*     */   public void renderRightArm(AbstractClientPlayer clientPlayer) {
/* 138 */     float f = 1.0F;
/* 139 */     GlStateManager.color(f, f, f);
/* 140 */     ModelPlayer modelplayer = getMainModel();
/* 141 */     setModelVisibilities(clientPlayer);
/* 142 */     modelplayer.swingProgress = 0.0F;
/* 143 */     modelplayer.isSneak = false;
/* 144 */     modelplayer.setRotationAngles(0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0625F, (Entity)clientPlayer);
/* 145 */     modelplayer.renderRightArm();
/*     */   }
/*     */   
/*     */   public void renderLeftArm(AbstractClientPlayer clientPlayer) {
/* 149 */     float f = 1.0F;
/* 150 */     GlStateManager.color(f, f, f);
/* 151 */     ModelPlayer modelplayer = getMainModel();
/* 152 */     setModelVisibilities(clientPlayer);
/* 153 */     modelplayer.isSneak = false;
/* 154 */     modelplayer.swingProgress = 0.0F;
/* 155 */     modelplayer.setRotationAngles(0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0625F, (Entity)clientPlayer);
/* 156 */     modelplayer.renderLeftArm();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void renderLivingAt(AbstractClientPlayer entityLivingBaseIn, double x, double y, double z) {
/* 163 */     if (entityLivingBaseIn.isEntityAlive() && entityLivingBaseIn.isPlayerSleeping()) {
/* 164 */       super.renderLivingAt(entityLivingBaseIn, x + entityLivingBaseIn.renderOffsetX, y + entityLivingBaseIn.renderOffsetY, z + entityLivingBaseIn.renderOffsetZ);
/*     */     } else {
/* 166 */       super.renderLivingAt(entityLivingBaseIn, x, y, z);
/*     */     } 
/*     */   }
/*     */   
/*     */   protected void rotateCorpse(AbstractClientPlayer bat, float p_77043_2_, float p_77043_3_, float partialTicks) {
/* 171 */     if (bat.isEntityAlive() && bat.isPlayerSleeping()) {
/* 172 */       GlStateManager.rotate(bat.getBedOrientationInDegrees(), 0.0F, 1.0F, 0.0F);
/* 173 */       GlStateManager.rotate(getDeathMaxRotation(bat), 0.0F, 0.0F, 1.0F);
/* 174 */       GlStateManager.rotate(270.0F, 0.0F, 1.0F, 0.0F);
/*     */     } else {
/* 176 */       super.rotateCorpse(bat, p_77043_2_, p_77043_3_, partialTicks);
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\minecraft\client\renderer\entity\RenderPlayer.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */