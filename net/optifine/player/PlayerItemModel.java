/*     */ package net.optifine.player;
/*     */ 
/*     */ import java.awt.Dimension;
/*     */ import java.awt.image.BufferedImage;
/*     */ import net.minecraft.client.Minecraft;
/*     */ import net.minecraft.client.entity.AbstractClientPlayer;
/*     */ import net.minecraft.client.model.ModelBiped;
/*     */ import net.minecraft.client.model.ModelRenderer;
/*     */ import net.minecraft.client.renderer.GlStateManager;
/*     */ import net.minecraft.client.renderer.texture.DynamicTexture;
/*     */ import net.minecraft.client.renderer.texture.ITextureObject;
/*     */ import net.minecraft.client.renderer.texture.TextureManager;
/*     */ import net.minecraft.src.Config;
/*     */ import net.minecraft.util.ResourceLocation;
/*     */ 
/*     */ public class PlayerItemModel {
/*  17 */   private Dimension textureSize = null;
/*     */   private boolean usePlayerTexture = false;
/*  19 */   private PlayerItemRenderer[] modelRenderers = new PlayerItemRenderer[0];
/*  20 */   private ResourceLocation textureLocation = null;
/*  21 */   private BufferedImage textureImage = null;
/*  22 */   private DynamicTexture texture = null;
/*  23 */   private ResourceLocation locationMissing = new ResourceLocation("textures/blocks/wool_colored_red.png");
/*     */   public static final int ATTACH_BODY = 0;
/*     */   public static final int ATTACH_HEAD = 1;
/*     */   public static final int ATTACH_LEFT_ARM = 2;
/*     */   public static final int ATTACH_RIGHT_ARM = 3;
/*     */   public static final int ATTACH_LEFT_LEG = 4;
/*     */   public static final int ATTACH_RIGHT_LEG = 5;
/*     */   public static final int ATTACH_CAPE = 6;
/*     */   
/*     */   public PlayerItemModel(Dimension textureSize, boolean usePlayerTexture, PlayerItemRenderer[] modelRenderers) {
/*  33 */     this.textureSize = textureSize;
/*  34 */     this.usePlayerTexture = usePlayerTexture;
/*  35 */     this.modelRenderers = modelRenderers;
/*     */   }
/*     */   
/*     */   public void render(ModelBiped modelBiped, AbstractClientPlayer player, float scale, float partialTicks) {
/*  39 */     TextureManager texturemanager = Config.getTextureManager();
/*     */     
/*  41 */     if (this.usePlayerTexture) {
/*  42 */       texturemanager.bindTexture(player.getLocationSkin());
/*  43 */     } else if (this.textureLocation != null) {
/*  44 */       if (this.texture == null && this.textureImage != null) {
/*  45 */         this.texture = new DynamicTexture(this.textureImage);
/*  46 */         Minecraft.getMinecraft().getTextureManager().loadTexture(this.textureLocation, (ITextureObject)this.texture);
/*     */       } 
/*     */       
/*  49 */       texturemanager.bindTexture(this.textureLocation);
/*     */     } else {
/*  51 */       texturemanager.bindTexture(this.locationMissing);
/*     */     } 
/*     */     
/*  54 */     for (int i = 0; i < this.modelRenderers.length; i++) {
/*  55 */       PlayerItemRenderer playeritemrenderer = this.modelRenderers[i];
/*  56 */       GlStateManager.pushMatrix();
/*     */       
/*  58 */       if (player.isSneaking()) {
/*  59 */         GlStateManager.translate(0.0F, 0.2F, 0.0F);
/*     */       }
/*     */       
/*  62 */       playeritemrenderer.render(modelBiped, scale);
/*  63 */       GlStateManager.popMatrix();
/*     */     } 
/*     */   }
/*     */   
/*     */   public static ModelRenderer getAttachModel(ModelBiped modelBiped, int attachTo) {
/*  68 */     switch (attachTo) {
/*     */       case 0:
/*  70 */         return modelBiped.bipedBody;
/*     */       
/*     */       case 1:
/*  73 */         return modelBiped.bipedHead;
/*     */       
/*     */       case 2:
/*  76 */         return modelBiped.bipedLeftArm;
/*     */       
/*     */       case 3:
/*  79 */         return modelBiped.bipedRightArm;
/*     */       
/*     */       case 4:
/*  82 */         return modelBiped.bipedLeftLeg;
/*     */       
/*     */       case 5:
/*  85 */         return modelBiped.bipedRightLeg;
/*     */     } 
/*     */     
/*  88 */     return null;
/*     */   }
/*     */ 
/*     */   
/*     */   public BufferedImage getTextureImage() {
/*  93 */     return this.textureImage;
/*     */   }
/*     */   
/*     */   public void setTextureImage(BufferedImage textureImage) {
/*  97 */     this.textureImage = textureImage;
/*     */   }
/*     */   
/*     */   public DynamicTexture getTexture() {
/* 101 */     return this.texture;
/*     */   }
/*     */   
/*     */   public ResourceLocation getTextureLocation() {
/* 105 */     return this.textureLocation;
/*     */   }
/*     */   
/*     */   public void setTextureLocation(ResourceLocation textureLocation) {
/* 109 */     this.textureLocation = textureLocation;
/*     */   }
/*     */   
/*     */   public boolean isUsePlayerTexture() {
/* 113 */     return this.usePlayerTexture;
/*     */   }
/*     */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\optifine\player\PlayerItemModel.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */