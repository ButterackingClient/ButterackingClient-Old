/*      */ package net.minecraft.client.renderer.entity;
/*      */ 
/*      */ import client.Client;
/*      */ import com.google.common.collect.Lists;
/*      */ import java.nio.FloatBuffer;
/*      */ import java.util.List;
/*      */ import net.minecraft.client.Minecraft;
/*      */ import net.minecraft.client.entity.EntityPlayerSP;
/*      */ import net.minecraft.client.gui.FontRenderer;
/*      */ import net.minecraft.client.model.ModelBase;
/*      */ import net.minecraft.client.renderer.GLAllocation;
/*      */ import net.minecraft.client.renderer.GlStateManager;
/*      */ import net.minecraft.client.renderer.OpenGlHelper;
/*      */ import net.minecraft.client.renderer.Tessellator;
/*      */ import net.minecraft.client.renderer.WorldRenderer;
/*      */ import net.minecraft.client.renderer.entity.layers.LayerRenderer;
/*      */ import net.minecraft.client.renderer.texture.DynamicTexture;
/*      */ import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
/*      */ import net.minecraft.entity.Entity;
/*      */ import net.minecraft.entity.EntityLivingBase;
/*      */ import net.minecraft.entity.player.EntityPlayer;
/*      */ import net.minecraft.entity.player.EnumPlayerModelParts;
/*      */ import net.minecraft.scoreboard.ScorePlayerTeam;
/*      */ import net.minecraft.scoreboard.Team;
/*      */ import net.minecraft.src.Config;
/*      */ import net.minecraft.util.EnumChatFormatting;
/*      */ import net.minecraft.util.MathHelper;
/*      */ import net.optifine.EmissiveTextures;
/*      */ import net.optifine.entity.model.CustomEntityModels;
/*      */ import net.optifine.reflect.Reflector;
/*      */ import net.optifine.shaders.Shaders;
/*      */ import org.apache.logging.log4j.LogManager;
/*      */ import org.apache.logging.log4j.Logger;
/*      */ import org.lwjgl.opengl.GL11;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ public abstract class RendererLivingEntity<T extends EntityLivingBase>
/*      */   extends Render<T>
/*      */ {
/*  800 */   private static final Logger logger = LogManager.getLogger();
/*  801 */   private static final DynamicTexture textureBrightness = new DynamicTexture(16, 16); public ModelBase mainModel; protected FloatBuffer brightnessBuffer; protected List<LayerRenderer<T>> layerRenderers; protected boolean renderOutlines;
/*  802 */   public static float NAME_TAG_RANGE = 64.0F; public EntityLivingBase renderEntity; public float renderLimbSwing; public float renderLimbSwingAmount; public float renderAgeInTicks; public float renderHeadYaw;
/*  803 */   public static float NAME_TAG_RANGE_SNEAK = 32.0F; public float renderHeadPitch; public float renderScaleFactor; public float renderPartialTicks; private boolean renderModelPushMatrix; private boolean renderLayersPushMatrix;
/*  804 */   public static final boolean animateModelLiving = Boolean.getBoolean("animate.model.living"); static {
/*  805 */     int[] aint = textureBrightness.getTextureData();
/*  806 */     for (int i = 0; i < 256; i++) {
/*  807 */       aint[i] = -1;
/*      */     }
/*  809 */     textureBrightness.updateDynamicTexture();
/*      */   }
/*      */   
/*      */   public RendererLivingEntity(RenderManager renderManagerIn, ModelBase modelBaseIn, float shadowSizeIn) {
/*  813 */     super(renderManagerIn);
/*  814 */     this.brightnessBuffer = GLAllocation.createDirectFloatBuffer(4);
/*  815 */     this.layerRenderers = Lists.newArrayList();
/*  816 */     this.renderOutlines = false;
/*  817 */     this.mainModel = modelBaseIn;
/*  818 */     this.shadowSize = shadowSizeIn;
/*  819 */     this.renderModelPushMatrix = this.mainModel instanceof net.minecraft.client.model.ModelSpider;
/*      */   }
/*      */   
/*      */   public <V extends EntityLivingBase, U extends LayerRenderer<V>> boolean addLayer(U layer) {
/*  823 */     return this.layerRenderers.add((LayerRenderer<T>)layer);
/*      */   }
/*      */   
/*      */   protected <V extends EntityLivingBase, U extends LayerRenderer<V>> boolean removeLayer(U layer) {
/*  827 */     return this.layerRenderers.remove(layer);
/*      */   }
/*      */   
/*      */   public ModelBase getMainModel() {
/*  831 */     return this.mainModel;
/*      */   }
/*      */   
/*      */   protected float interpolateRotation(float par1, float par2, float par3) {
/*      */     float f;
/*  836 */     for (f = par2 - par1; f < -180.0F; f += 360.0F);
/*      */     
/*  838 */     while (f >= 180.0F) {
/*  839 */       f -= 360.0F;
/*      */     }
/*  841 */     return par1 + par3 * f;
/*      */   }
/*      */ 
/*      */   
/*      */   public void transformHeldFull3DItemLayer() {}
/*      */ 
/*      */   
/*      */   public void doRender(T entity, double x, double y, double z, float entityYaw, float partialTicks) {
/*  849 */     if (!Reflector.RenderLivingEvent_Pre_Constructor.exists() || !Reflector.postForgeBusEvent(Reflector.RenderLivingEvent_Pre_Constructor, new Object[] { entity, this, Double.valueOf(x), Double.valueOf(y), Double.valueOf(z) })) {
/*  850 */       if (animateModelLiving) {
/*  851 */         ((EntityLivingBase)entity).limbSwingAmount = 1.0F;
/*      */       }
/*  853 */       GlStateManager.pushMatrix();
/*  854 */       GlStateManager.disableCull();
/*  855 */       this.mainModel.swingProgress = getSwingProgress(entity, partialTicks);
/*  856 */       this.mainModel.isRiding = entity.isRiding();
/*  857 */       if (Reflector.ForgeEntity_shouldRiderSit.exists()) {
/*  858 */         this.mainModel.isRiding = (entity.isRiding() && ((EntityLivingBase)entity).ridingEntity != null && Reflector.callBoolean(((EntityLivingBase)entity).ridingEntity, Reflector.ForgeEntity_shouldRiderSit, new Object[0]));
/*      */       }
/*  860 */       this.mainModel.isChild = entity.isChild();
/*      */       try {
/*  862 */         float f = interpolateRotation(((EntityLivingBase)entity).prevRenderYawOffset, ((EntityLivingBase)entity).renderYawOffset, partialTicks);
/*  863 */         float f2 = interpolateRotation(((EntityLivingBase)entity).prevRotationYawHead, ((EntityLivingBase)entity).rotationYawHead, partialTicks);
/*  864 */         float f3 = f2 - f;
/*  865 */         if (this.mainModel.isRiding && ((EntityLivingBase)entity).ridingEntity instanceof EntityLivingBase) {
/*  866 */           EntityLivingBase entitylivingbase = (EntityLivingBase)((EntityLivingBase)entity).ridingEntity;
/*  867 */           f = interpolateRotation(entitylivingbase.prevRenderYawOffset, entitylivingbase.renderYawOffset, partialTicks);
/*  868 */           f3 = f2 - f;
/*  869 */           float f4 = MathHelper.wrapAngleTo180_float(f3);
/*  870 */           if (f4 < -85.0F) {
/*  871 */             f4 = -85.0F;
/*      */           }
/*  873 */           if (f4 >= 85.0F) {
/*  874 */             f4 = 85.0F;
/*      */           }
/*  876 */           f = f2 - f4;
/*  877 */           if (f4 * f4 > 2500.0F) {
/*  878 */             f += f4 * 0.2F;
/*      */           }
/*  880 */           f3 = f2 - f;
/*      */         } 
/*  882 */         float f5 = ((EntityLivingBase)entity).prevRotationPitch + (((EntityLivingBase)entity).rotationPitch - ((EntityLivingBase)entity).prevRotationPitch) * partialTicks;
/*  883 */         renderLivingAt(entity, x, y, z);
/*  884 */         float f6 = handleRotationFloat(entity, partialTicks);
/*  885 */         rotateCorpse(entity, f6, f, partialTicks);
/*  886 */         GlStateManager.enableRescaleNormal();
/*  887 */         GlStateManager.scale(-1.0F, -1.0F, 1.0F);
/*  888 */         preRenderCallback(entity, partialTicks);
/*  889 */         float f7 = 0.0625F;
/*  890 */         GlStateManager.translate(0.0F, -1.5078125F, 0.0F);
/*  891 */         float f8 = ((EntityLivingBase)entity).prevLimbSwingAmount + (((EntityLivingBase)entity).limbSwingAmount - ((EntityLivingBase)entity).prevLimbSwingAmount) * partialTicks;
/*  892 */         float f9 = ((EntityLivingBase)entity).limbSwing - ((EntityLivingBase)entity).limbSwingAmount * (1.0F - partialTicks);
/*  893 */         if (entity.isChild()) {
/*  894 */           f9 *= 3.0F;
/*      */         }
/*  896 */         if (f8 > 1.0F) {
/*  897 */           f8 = 1.0F;
/*      */         }
/*  899 */         GlStateManager.enableAlpha();
/*  900 */         this.mainModel.setLivingAnimations((EntityLivingBase)entity, f9, f8, partialTicks);
/*  901 */         this.mainModel.setRotationAngles(f9, f8, f6, f3, f5, 0.0625F, (Entity)entity);
/*  902 */         if (CustomEntityModels.isActive()) {
/*  903 */           this.renderEntity = (EntityLivingBase)entity;
/*  904 */           this.renderLimbSwing = f9;
/*  905 */           this.renderLimbSwingAmount = f8;
/*  906 */           this.renderAgeInTicks = f6;
/*  907 */           this.renderHeadYaw = f3;
/*  908 */           this.renderHeadPitch = f5;
/*  909 */           this.renderScaleFactor = 0.0625F;
/*  910 */           this.renderPartialTicks = partialTicks;
/*      */         } 
/*  912 */         if (this.renderOutlines) {
/*  913 */           boolean flag1 = setScoreTeamColor(entity);
/*  914 */           renderModel(entity, f9, f8, f6, f3, f5, 0.0625F);
/*  915 */           if (flag1) {
/*  916 */             unsetScoreTeamColor();
/*      */           }
/*      */         } else {
/*  919 */           boolean flag2 = setDoRenderBrightness(entity, partialTicks);
/*  920 */           if (EmissiveTextures.isActive()) {
/*  921 */             EmissiveTextures.beginRender();
/*      */           }
/*  923 */           if (this.renderModelPushMatrix) {
/*  924 */             GlStateManager.pushMatrix();
/*      */           }
/*  926 */           renderModel(entity, f9, f8, f6, f3, f5, 0.0625F);
/*  927 */           if (this.renderModelPushMatrix) {
/*  928 */             GlStateManager.popMatrix();
/*      */           }
/*  930 */           if (EmissiveTextures.isActive()) {
/*  931 */             if (EmissiveTextures.hasEmissive()) {
/*  932 */               this.renderModelPushMatrix = true;
/*  933 */               EmissiveTextures.beginRenderEmissive();
/*  934 */               GlStateManager.pushMatrix();
/*  935 */               renderModel(entity, f9, f8, f6, f3, f5, 0.0625F);
/*  936 */               GlStateManager.popMatrix();
/*  937 */               EmissiveTextures.endRenderEmissive();
/*      */             } 
/*  939 */             EmissiveTextures.endRender();
/*      */           } 
/*  941 */           if (flag2) {
/*  942 */             unsetBrightness();
/*      */           }
/*  944 */           GlStateManager.depthMask(true);
/*  945 */           if (!(entity instanceof EntityPlayer) || !((EntityPlayer)entity).isSpectator()) {
/*  946 */             renderLayers(entity, f9, f8, partialTicks, f6, f3, f5, 0.0625F);
/*      */           }
/*      */         } 
/*  949 */         if (CustomEntityModels.isActive()) {
/*  950 */           this.renderEntity = null;
/*      */         }
/*  952 */         GlStateManager.disableRescaleNormal();
/*  953 */       } catch (Exception exception) {
/*  954 */         logger.error("Couldn't render entity", exception);
/*      */       } 
/*  956 */       GlStateManager.setActiveTexture(OpenGlHelper.lightmapTexUnit);
/*  957 */       GlStateManager.enableTexture2D();
/*  958 */       GlStateManager.setActiveTexture(OpenGlHelper.defaultTexUnit);
/*  959 */       GlStateManager.enableCull();
/*  960 */       GlStateManager.popMatrix();
/*  961 */       if (!this.renderOutlines) {
/*  962 */         super.doRender(entity, x, y, z, entityYaw, partialTicks);
/*      */       }
/*  964 */       if (Reflector.RenderLivingEvent_Post_Constructor.exists()) {
/*  965 */         Reflector.postForgeBusEvent(Reflector.RenderLivingEvent_Post_Constructor, new Object[] { entity, this, Double.valueOf(x), Double.valueOf(y), Double.valueOf(z) });
/*      */       }
/*      */     } 
/*      */   }
/*      */   
/*      */   protected boolean setScoreTeamColor(T entityLivingBaseIn) {
/*  971 */     int i = 16777215;
/*  972 */     if (entityLivingBaseIn instanceof EntityPlayer) {
/*  973 */       ScorePlayerTeam scoreplayerteam = (ScorePlayerTeam)entityLivingBaseIn.getTeam();
/*  974 */       if (scoreplayerteam != null) {
/*  975 */         String s = FontRenderer.getFormatFromString(scoreplayerteam.getColorPrefix());
/*  976 */         if (s.length() >= 2) {
/*  977 */           i = getFontRendererFromRenderManager().getColorCode(s.charAt(1));
/*      */         }
/*      */       } 
/*      */     } 
/*  981 */     float f1 = (i >> 16 & 0xFF) / 255.0F;
/*  982 */     float f2 = (i >> 8 & 0xFF) / 255.0F;
/*  983 */     float f3 = (i & 0xFF) / 255.0F;
/*  984 */     GlStateManager.disableLighting();
/*  985 */     GlStateManager.setActiveTexture(OpenGlHelper.defaultTexUnit);
/*  986 */     GlStateManager.color(f1, f2, f3, 1.0F);
/*  987 */     GlStateManager.disableTexture2D();
/*  988 */     GlStateManager.setActiveTexture(OpenGlHelper.lightmapTexUnit);
/*  989 */     GlStateManager.disableTexture2D();
/*  990 */     GlStateManager.setActiveTexture(OpenGlHelper.defaultTexUnit);
/*  991 */     return true;
/*      */   }
/*      */   
/*      */   protected void unsetScoreTeamColor() {
/*  995 */     GlStateManager.enableLighting();
/*  996 */     GlStateManager.setActiveTexture(OpenGlHelper.defaultTexUnit);
/*  997 */     GlStateManager.enableTexture2D();
/*  998 */     GlStateManager.setActiveTexture(OpenGlHelper.lightmapTexUnit);
/*  999 */     GlStateManager.enableTexture2D();
/* 1000 */     GlStateManager.setActiveTexture(OpenGlHelper.defaultTexUnit);
/*      */   }
/*      */   
/*      */   protected void renderModel(T entitylivingbaseIn, float p_77036_2_, float p_77036_3_, float p_77036_4_, float p_77036_5_, float p_77036_6_, float scaleFactor) {
/* 1004 */     boolean flag = !entitylivingbaseIn.isInvisible();
/* 1005 */     boolean flag2 = (!flag && !entitylivingbaseIn.isInvisibleToPlayer((EntityPlayer)(Minecraft.getMinecraft()).thePlayer));
/* 1006 */     if (flag || flag2) {
/* 1007 */       if (!bindEntityTexture(entitylivingbaseIn)) {
/*      */         return;
/*      */       }
/* 1010 */       if (flag2) {
/* 1011 */         GlStateManager.pushMatrix();
/* 1012 */         GlStateManager.color(1.0F, 1.0F, 1.0F, 0.15F);
/* 1013 */         GlStateManager.depthMask(false);
/* 1014 */         GlStateManager.enableBlend();
/* 1015 */         GlStateManager.blendFunc(770, 771);
/* 1016 */         GlStateManager.alphaFunc(516, 0.003921569F);
/*      */       } 
/* 1018 */       this.mainModel.render((Entity)entitylivingbaseIn, p_77036_2_, p_77036_3_, p_77036_4_, p_77036_5_, p_77036_6_, scaleFactor);
/* 1019 */       if (flag2) {
/* 1020 */         GlStateManager.disableBlend();
/* 1021 */         GlStateManager.alphaFunc(516, 0.1F);
/* 1022 */         GlStateManager.popMatrix();
/* 1023 */         GlStateManager.depthMask(true);
/*      */       } 
/*      */     } 
/*      */   }
/*      */   
/*      */   protected boolean setDoRenderBrightness(T entityLivingBaseIn, float partialTicks) {
/* 1029 */     return setBrightness(entityLivingBaseIn, partialTicks, true);
/*      */   }
/*      */   
/*      */   protected boolean setBrightness(T entitylivingbaseIn, float partialTicks, boolean combineTextures) {
/* 1033 */     float f = entitylivingbaseIn.getBrightness(partialTicks);
/* 1034 */     int i = getColorMultiplier(entitylivingbaseIn, f, partialTicks);
/* 1035 */     boolean flag = ((i >> 24 & 0xFF) > 0);
/* 1036 */     boolean flag2 = !(((EntityLivingBase)entitylivingbaseIn).hurtTime <= 0 && ((EntityLivingBase)entitylivingbaseIn).deathTime <= 0);
/* 1037 */     if (!flag && !flag2) {
/* 1038 */       return false;
/*      */     }
/* 1040 */     if (!flag && !combineTextures) {
/* 1041 */       return false;
/*      */     }
/* 1043 */     GlStateManager.setActiveTexture(OpenGlHelper.defaultTexUnit);
/* 1044 */     GlStateManager.enableTexture2D();
/* 1045 */     GL11.glTexEnvi(8960, 8704, OpenGlHelper.GL_COMBINE);
/* 1046 */     GL11.glTexEnvi(8960, OpenGlHelper.GL_COMBINE_RGB, 8448);
/* 1047 */     GL11.glTexEnvi(8960, OpenGlHelper.GL_SOURCE0_RGB, OpenGlHelper.defaultTexUnit);
/* 1048 */     GL11.glTexEnvi(8960, OpenGlHelper.GL_SOURCE1_RGB, OpenGlHelper.GL_PRIMARY_COLOR);
/* 1049 */     GL11.glTexEnvi(8960, OpenGlHelper.GL_OPERAND0_RGB, 768);
/* 1050 */     GL11.glTexEnvi(8960, OpenGlHelper.GL_OPERAND1_RGB, 768);
/* 1051 */     GL11.glTexEnvi(8960, OpenGlHelper.GL_COMBINE_ALPHA, 7681);
/* 1052 */     GL11.glTexEnvi(8960, OpenGlHelper.GL_SOURCE0_ALPHA, OpenGlHelper.defaultTexUnit);
/* 1053 */     GL11.glTexEnvi(8960, OpenGlHelper.GL_OPERAND0_ALPHA, 770);
/* 1054 */     GlStateManager.setActiveTexture(OpenGlHelper.lightmapTexUnit);
/* 1055 */     GlStateManager.enableTexture2D();
/* 1056 */     GL11.glTexEnvi(8960, 8704, OpenGlHelper.GL_COMBINE);
/* 1057 */     GL11.glTexEnvi(8960, OpenGlHelper.GL_COMBINE_RGB, OpenGlHelper.GL_INTERPOLATE);
/* 1058 */     GL11.glTexEnvi(8960, OpenGlHelper.GL_SOURCE0_RGB, OpenGlHelper.GL_CONSTANT);
/* 1059 */     GL11.glTexEnvi(8960, OpenGlHelper.GL_SOURCE1_RGB, OpenGlHelper.GL_PREVIOUS);
/* 1060 */     GL11.glTexEnvi(8960, OpenGlHelper.GL_SOURCE2_RGB, OpenGlHelper.GL_CONSTANT);
/* 1061 */     GL11.glTexEnvi(8960, OpenGlHelper.GL_OPERAND0_RGB, 768);
/* 1062 */     GL11.glTexEnvi(8960, OpenGlHelper.GL_OPERAND1_RGB, 768);
/* 1063 */     GL11.glTexEnvi(8960, OpenGlHelper.GL_OPERAND2_RGB, 770);
/* 1064 */     GL11.glTexEnvi(8960, OpenGlHelper.GL_COMBINE_ALPHA, 7681);
/* 1065 */     GL11.glTexEnvi(8960, OpenGlHelper.GL_SOURCE0_ALPHA, OpenGlHelper.GL_PREVIOUS);
/* 1066 */     GL11.glTexEnvi(8960, OpenGlHelper.GL_OPERAND0_ALPHA, 770);
/* 1067 */     this.brightnessBuffer.position(0);
/* 1068 */     if (flag2) {
/* 1069 */       if (!(Client.getInstance()).hudManager.hitColor.isEnabled()) {
/* 1070 */         this.brightnessBuffer.put(1.0F);
/* 1071 */         this.brightnessBuffer.put(0.0F);
/* 1072 */         this.brightnessBuffer.put(0.0F);
/* 1073 */         this.brightnessBuffer.put(0.3F);
/* 1074 */         if (Config.isShaders()) {
/* 1075 */           Shaders.setEntityColor(1.0F, 0.0F, 0.0F, 0.3F);
/*      */         }
/*      */       } else {
/* 1078 */         this.brightnessBuffer.put((Client.getInstance()).hitColorRed);
/* 1079 */         this.brightnessBuffer.put((Client.getInstance()).hitColorGreen);
/* 1080 */         this.brightnessBuffer.put((Client.getInstance()).hitColorBlue);
/* 1081 */         this.brightnessBuffer.put((Client.getInstance()).hitColorAlpha);
/* 1082 */         if (Config.isShaders()) {
/* 1083 */           Shaders.setEntityColor((Client.getInstance()).hitColorRed, (Client.getInstance()).hitColorGreen, (Client.getInstance()).hitColorBlue, (Client.getInstance()).hitColorAlpha);
/*      */         }
/*      */       } 
/*      */     } else {
/* 1087 */       float f2 = (i >> 24 & 0xFF) / 255.0F;
/* 1088 */       float f3 = (i >> 16 & 0xFF) / 255.0F;
/* 1089 */       float f4 = (i >> 8 & 0xFF) / 255.0F;
/* 1090 */       float f5 = (i & 0xFF) / 255.0F;
/* 1091 */       this.brightnessBuffer.put(f3);
/* 1092 */       this.brightnessBuffer.put(f4);
/* 1093 */       this.brightnessBuffer.put(f5);
/* 1094 */       this.brightnessBuffer.put(1.0F - f2);
/* 1095 */       if (Config.isShaders()) {
/* 1096 */         Shaders.setEntityColor(f3, f4, f5, 1.0F - f2);
/*      */       }
/*      */     } 
/* 1099 */     this.brightnessBuffer.flip();
/* 1100 */     GL11.glTexEnv(8960, 8705, this.brightnessBuffer);
/* 1101 */     GlStateManager.setActiveTexture(OpenGlHelper.GL_TEXTURE2);
/* 1102 */     GlStateManager.enableTexture2D();
/* 1103 */     GlStateManager.bindTexture(textureBrightness.getGlTextureId());
/* 1104 */     GL11.glTexEnvi(8960, 8704, OpenGlHelper.GL_COMBINE);
/* 1105 */     GL11.glTexEnvi(8960, OpenGlHelper.GL_COMBINE_RGB, 8448);
/* 1106 */     GL11.glTexEnvi(8960, OpenGlHelper.GL_SOURCE0_RGB, OpenGlHelper.GL_PREVIOUS);
/* 1107 */     GL11.glTexEnvi(8960, OpenGlHelper.GL_SOURCE1_RGB, OpenGlHelper.lightmapTexUnit);
/* 1108 */     GL11.glTexEnvi(8960, OpenGlHelper.GL_OPERAND0_RGB, 768);
/* 1109 */     GL11.glTexEnvi(8960, OpenGlHelper.GL_OPERAND1_RGB, 768);
/* 1110 */     GL11.glTexEnvi(8960, OpenGlHelper.GL_COMBINE_ALPHA, 7681);
/* 1111 */     GL11.glTexEnvi(8960, OpenGlHelper.GL_SOURCE0_ALPHA, OpenGlHelper.GL_PREVIOUS);
/* 1112 */     GL11.glTexEnvi(8960, OpenGlHelper.GL_OPERAND0_ALPHA, 770);
/* 1113 */     GlStateManager.setActiveTexture(OpenGlHelper.defaultTexUnit);
/* 1114 */     return true;
/*      */   }
/*      */   
/*      */   protected void unsetBrightness() {
/* 1118 */     GlStateManager.setActiveTexture(OpenGlHelper.defaultTexUnit);
/* 1119 */     GlStateManager.enableTexture2D();
/* 1120 */     GL11.glTexEnvi(8960, 8704, OpenGlHelper.GL_COMBINE);
/* 1121 */     GL11.glTexEnvi(8960, OpenGlHelper.GL_COMBINE_RGB, 8448);
/* 1122 */     GL11.glTexEnvi(8960, OpenGlHelper.GL_SOURCE0_RGB, OpenGlHelper.defaultTexUnit);
/* 1123 */     GL11.glTexEnvi(8960, OpenGlHelper.GL_SOURCE1_RGB, OpenGlHelper.GL_PRIMARY_COLOR);
/* 1124 */     GL11.glTexEnvi(8960, OpenGlHelper.GL_OPERAND0_RGB, 768);
/* 1125 */     GL11.glTexEnvi(8960, OpenGlHelper.GL_OPERAND1_RGB, 768);
/* 1126 */     GL11.glTexEnvi(8960, OpenGlHelper.GL_COMBINE_ALPHA, 8448);
/* 1127 */     GL11.glTexEnvi(8960, OpenGlHelper.GL_SOURCE0_ALPHA, OpenGlHelper.defaultTexUnit);
/* 1128 */     GL11.glTexEnvi(8960, OpenGlHelper.GL_SOURCE1_ALPHA, OpenGlHelper.GL_PRIMARY_COLOR);
/* 1129 */     GL11.glTexEnvi(8960, OpenGlHelper.GL_OPERAND0_ALPHA, 770);
/* 1130 */     GL11.glTexEnvi(8960, OpenGlHelper.GL_OPERAND1_ALPHA, 770);
/* 1131 */     GlStateManager.setActiveTexture(OpenGlHelper.lightmapTexUnit);
/* 1132 */     GL11.glTexEnvi(8960, 8704, OpenGlHelper.GL_COMBINE);
/* 1133 */     GL11.glTexEnvi(8960, OpenGlHelper.GL_COMBINE_RGB, 8448);
/* 1134 */     GL11.glTexEnvi(8960, OpenGlHelper.GL_OPERAND0_RGB, 768);
/* 1135 */     GL11.glTexEnvi(8960, OpenGlHelper.GL_OPERAND1_RGB, 768);
/* 1136 */     GL11.glTexEnvi(8960, OpenGlHelper.GL_SOURCE0_RGB, 5890);
/* 1137 */     GL11.glTexEnvi(8960, OpenGlHelper.GL_SOURCE1_RGB, OpenGlHelper.GL_PREVIOUS);
/* 1138 */     GL11.glTexEnvi(8960, OpenGlHelper.GL_COMBINE_ALPHA, 8448);
/* 1139 */     GL11.glTexEnvi(8960, OpenGlHelper.GL_OPERAND0_ALPHA, 770);
/* 1140 */     GL11.glTexEnvi(8960, OpenGlHelper.GL_SOURCE0_ALPHA, 5890);
/* 1141 */     GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
/* 1142 */     GlStateManager.setActiveTexture(OpenGlHelper.GL_TEXTURE2);
/* 1143 */     GlStateManager.disableTexture2D();
/* 1144 */     GlStateManager.bindTexture(0);
/* 1145 */     GL11.glTexEnvi(8960, 8704, OpenGlHelper.GL_COMBINE);
/* 1146 */     GL11.glTexEnvi(8960, OpenGlHelper.GL_COMBINE_RGB, 8448);
/* 1147 */     GL11.glTexEnvi(8960, OpenGlHelper.GL_OPERAND0_RGB, 768);
/* 1148 */     GL11.glTexEnvi(8960, OpenGlHelper.GL_OPERAND1_RGB, 768);
/* 1149 */     GL11.glTexEnvi(8960, OpenGlHelper.GL_SOURCE0_RGB, 5890);
/* 1150 */     GL11.glTexEnvi(8960, OpenGlHelper.GL_SOURCE1_RGB, OpenGlHelper.GL_PREVIOUS);
/* 1151 */     GL11.glTexEnvi(8960, OpenGlHelper.GL_COMBINE_ALPHA, 8448);
/* 1152 */     GL11.glTexEnvi(8960, OpenGlHelper.GL_OPERAND0_ALPHA, 770);
/* 1153 */     GL11.glTexEnvi(8960, OpenGlHelper.GL_SOURCE0_ALPHA, 5890);
/* 1154 */     GlStateManager.setActiveTexture(OpenGlHelper.defaultTexUnit);
/* 1155 */     if (Config.isShaders()) {
/* 1156 */       Shaders.setEntityColor(0.0F, 0.0F, 0.0F, 0.0F);
/*      */     }
/*      */   }
/*      */   
/*      */   protected void renderLivingAt(T entityLivingBaseIn, double x, double y, double z) {
/* 1161 */     GlStateManager.translate((float)x, (float)y, (float)z);
/*      */   }
/*      */   
/*      */   protected void rotateCorpse(T bat, float p_77043_2_, float p_77043_3_, float partialTicks) {
/* 1165 */     GlStateManager.rotate(180.0F - p_77043_3_, 0.0F, 1.0F, 0.0F);
/* 1166 */     if (((EntityLivingBase)bat).deathTime > 0) {
/* 1167 */       float f = (((EntityLivingBase)bat).deathTime + partialTicks - 1.0F) / 20.0F * 1.6F;
/* 1168 */       f = MathHelper.sqrt_float(f);
/* 1169 */       if (f > 1.0F) {
/* 1170 */         f = 1.0F;
/*      */       }
/* 1172 */       GlStateManager.rotate(f * getDeathMaxRotation(bat), 0.0F, 0.0F, 1.0F);
/*      */     } else {
/* 1174 */       String s = EnumChatFormatting.getTextWithoutFormattingCodes(bat.getName());
/* 1175 */       if (s != null && (s.equals("Dinnerbone") || s.equals("Grumm")) && (!(bat instanceof EntityPlayer) || ((EntityPlayer)bat).isWearing(EnumPlayerModelParts.CAPE))) {
/* 1176 */         GlStateManager.translate(0.0F, ((EntityLivingBase)bat).height + 0.1F, 0.0F);
/* 1177 */         GlStateManager.rotate(180.0F, 0.0F, 0.0F, 1.0F);
/*      */       } 
/*      */     } 
/*      */   }
/*      */   
/*      */   protected float getSwingProgress(T livingBase, float partialTickTime) {
/* 1183 */     return livingBase.getSwingProgress(partialTickTime);
/*      */   }
/*      */   
/*      */   protected float handleRotationFloat(T livingBase, float partialTicks) {
/* 1187 */     return ((EntityLivingBase)livingBase).ticksExisted + partialTicks;
/*      */   }
/*      */   
/*      */   protected void renderLayers(T entitylivingbaseIn, float p_177093_2_, float p_177093_3_, float partialTicks, float p_177093_5_, float p_177093_6_, float p_177093_7_, float p_177093_8_) {
/* 1191 */     for (LayerRenderer<T> layerrenderer : this.layerRenderers) {
/* 1192 */       boolean flag = setBrightness(entitylivingbaseIn, partialTicks, layerrenderer.shouldCombineTextures());
/* 1193 */       if (EmissiveTextures.isActive()) {
/* 1194 */         EmissiveTextures.beginRender();
/*      */       }
/* 1196 */       if (this.renderLayersPushMatrix) {
/* 1197 */         GlStateManager.pushMatrix();
/*      */       }
/* 1199 */       layerrenderer.doRenderLayer((EntityLivingBase)entitylivingbaseIn, p_177093_2_, p_177093_3_, partialTicks, p_177093_5_, p_177093_6_, p_177093_7_, p_177093_8_);
/* 1200 */       if (this.renderLayersPushMatrix) {
/* 1201 */         GlStateManager.popMatrix();
/*      */       }
/* 1203 */       if (EmissiveTextures.isActive()) {
/* 1204 */         if (EmissiveTextures.hasEmissive()) {
/* 1205 */           this.renderLayersPushMatrix = true;
/* 1206 */           EmissiveTextures.beginRenderEmissive();
/* 1207 */           GlStateManager.pushMatrix();
/* 1208 */           layerrenderer.doRenderLayer((EntityLivingBase)entitylivingbaseIn, p_177093_2_, p_177093_3_, partialTicks, p_177093_5_, p_177093_6_, p_177093_7_, p_177093_8_);
/* 1209 */           GlStateManager.popMatrix();
/* 1210 */           EmissiveTextures.endRenderEmissive();
/*      */         } 
/* 1212 */         EmissiveTextures.endRender();
/*      */       } 
/* 1214 */       if (flag) {
/* 1215 */         unsetBrightness();
/*      */       }
/*      */     } 
/*      */   }
/*      */   
/*      */   protected float getDeathMaxRotation(T entityLivingBaseIn) {
/* 1221 */     return 90.0F;
/*      */   }
/*      */   
/*      */   protected int getColorMultiplier(T entitylivingbaseIn, float lightBrightness, float partialTickTime) {
/* 1225 */     return 0;
/*      */   }
/*      */ 
/*      */   
/*      */   protected void preRenderCallback(T entitylivingbaseIn, float partialTickTime) {}
/*      */   
/*      */   public void renderName(T entity, double x, double y, double z) {
/* 1232 */     if (!Reflector.RenderLivingEvent_Specials_Pre_Constructor.exists() || !Reflector.postForgeBusEvent(Reflector.RenderLivingEvent_Specials_Pre_Constructor, new Object[] { entity, this, Double.valueOf(x), Double.valueOf(y), Double.valueOf(z) })) {
/* 1233 */       if (canRenderName(entity)) {
/* 1234 */         double d0 = entity.getDistanceSqToEntity(this.renderManager.livingPlayer);
/* 1235 */         float f = entity.isSneaking() ? NAME_TAG_RANGE_SNEAK : NAME_TAG_RANGE;
/* 1236 */         if (d0 < (f * f)) {
/* 1237 */           String s = entity.getDisplayName().getFormattedText();
/* 1238 */           float f2 = 0.02666667F;
/* 1239 */           GlStateManager.alphaFunc(516, 0.1F);
/* 1240 */           if (entity.isSneaking()) {
/* 1241 */             FontRenderer fontrenderer = getFontRendererFromRenderManager();
/* 1242 */             GlStateManager.pushMatrix();
/* 1243 */             GlStateManager.translate((float)x, (float)y + ((EntityLivingBase)entity).height + 0.5F - (entity.isChild() ? (((EntityLivingBase)entity).height / 2.0F) : 0.0F), (float)z);
/* 1244 */             GL11.glNormal3f(0.0F, 1.0F, 0.0F);
/* 1245 */             GlStateManager.rotate(-this.renderManager.playerViewY, 0.0F, 1.0F, 0.0F);
/* 1246 */             GlStateManager.rotate(this.renderManager.playerViewX, 1.0F, 0.0F, 0.0F);
/* 1247 */             GlStateManager.scale(-0.02666667F, -0.02666667F, 0.02666667F);
/* 1248 */             GlStateManager.translate(0.0F, 9.374999F, 0.0F);
/* 1249 */             GlStateManager.disableLighting();
/* 1250 */             GlStateManager.depthMask(false);
/* 1251 */             GlStateManager.enableBlend();
/* 1252 */             GlStateManager.disableTexture2D();
/* 1253 */             GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
/* 1254 */             int i = fontrenderer.getStringWidth(s) / 2;
/* 1255 */             Tessellator tessellator = Tessellator.getInstance();
/* 1256 */             WorldRenderer worldrenderer = tessellator.getWorldRenderer();
/* 1257 */             worldrenderer.begin(7, DefaultVertexFormats.POSITION_COLOR);
/* 1258 */             worldrenderer.pos((-i - 1), -1.0D, 0.0D).color(0.0F, 0.0F, 0.0F, 0.25F).endVertex();
/* 1259 */             worldrenderer.pos((-i - 1), 8.0D, 0.0D).color(0.0F, 0.0F, 0.0F, 0.25F).endVertex();
/* 1260 */             worldrenderer.pos((i + 1), 8.0D, 0.0D).color(0.0F, 0.0F, 0.0F, 0.25F).endVertex();
/* 1261 */             worldrenderer.pos((i + 1), -1.0D, 0.0D).color(0.0F, 0.0F, 0.0F, 0.25F).endVertex();
/* 1262 */             tessellator.draw();
/* 1263 */             GlStateManager.enableTexture2D();
/* 1264 */             GlStateManager.depthMask(true);
/* 1265 */             fontrenderer.drawString(s, -fontrenderer.getStringWidth(s) / 2, 0, 553648127);
/* 1266 */             GlStateManager.enableLighting();
/* 1267 */             GlStateManager.disableBlend();
/* 1268 */             GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
/* 1269 */             GlStateManager.popMatrix();
/*      */           } else {
/* 1271 */             renderOffsetLivingLabel(entity, x, y - (entity.isChild() ? (((EntityLivingBase)entity).height / 2.0F) : 0.0D), z, s, 0.02666667F, d0);
/*      */           } 
/*      */         } 
/*      */       } 
/* 1275 */       if (Reflector.RenderLivingEvent_Specials_Post_Constructor.exists()) {
/* 1276 */         Reflector.postForgeBusEvent(Reflector.RenderLivingEvent_Specials_Post_Constructor, new Object[] { entity, this, Double.valueOf(x), Double.valueOf(y), Double.valueOf(z) });
/*      */       }
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   protected boolean canRenderName(T entity) {
/* 1283 */     EntityPlayerSP entityplayersp = (Minecraft.getMinecraft()).thePlayer;
/* 1284 */     if (entity instanceof EntityPlayer && entity != entityplayersp) {
/* 1285 */       Team team = entity.getTeam();
/* 1286 */       Team team2 = entityplayersp.getTeam();
/* 1287 */       if (team != null) {
/* 1288 */         Team.EnumVisible team$enumvisible = team.getNameTagVisibility();
/* 1289 */         switch (team$enumvisible) {
/*      */           case null:
/* 1291 */             return true;
/*      */           
/*      */           case NEVER:
/* 1294 */             return false;
/*      */           
/*      */           case HIDE_FOR_OTHER_TEAMS:
/* 1297 */             return !(team2 != null && !team.isSameTeam(team2));
/*      */           
/*      */           case HIDE_FOR_OWN_TEAM:
/* 1300 */             return !(team2 != null && team.isSameTeam(team2));
/*      */         } 
/*      */         
/* 1303 */         return true;
/*      */       } 
/*      */     } 
/*      */ 
/*      */     
/* 1308 */     return (Minecraft.isGuiEnabled() && entity != this.renderManager.livingPlayer && !entity.isInvisibleToPlayer((EntityPlayer)entityplayersp) && ((EntityLivingBase)entity).riddenByEntity == null);
/*      */   }
/*      */   
/*      */   public void setRenderOutlines(boolean renderOutlinesIn) {
/* 1312 */     this.renderOutlines = renderOutlinesIn;
/*      */   }
/*      */   
/*      */   public List<LayerRenderer<T>> getLayerRenderers() {
/* 1316 */     return this.layerRenderers;
/*      */   }
/*      */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\minecraft\client\renderer\entity\RendererLivingEntity.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */