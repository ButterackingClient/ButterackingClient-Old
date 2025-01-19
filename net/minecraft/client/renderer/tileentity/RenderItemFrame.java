/*     */ package net.minecraft.client.renderer.tileentity;
/*     */ 
/*     */ import net.minecraft.client.Minecraft;
/*     */ import net.minecraft.client.entity.EntityPlayerSP;
/*     */ import net.minecraft.client.gui.FontRenderer;
/*     */ import net.minecraft.client.renderer.BlockRendererDispatcher;
/*     */ import net.minecraft.client.renderer.GlStateManager;
/*     */ import net.minecraft.client.renderer.RenderHelper;
/*     */ import net.minecraft.client.renderer.Tessellator;
/*     */ import net.minecraft.client.renderer.WorldRenderer;
/*     */ import net.minecraft.client.renderer.block.model.ItemCameraTransforms;
/*     */ import net.minecraft.client.renderer.entity.Render;
/*     */ import net.minecraft.client.renderer.entity.RenderItem;
/*     */ import net.minecraft.client.renderer.entity.RenderManager;
/*     */ import net.minecraft.client.renderer.texture.TextureAtlasSprite;
/*     */ import net.minecraft.client.renderer.texture.TextureCompass;
/*     */ import net.minecraft.client.renderer.texture.TextureMap;
/*     */ import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
/*     */ import net.minecraft.client.resources.model.IBakedModel;
/*     */ import net.minecraft.client.resources.model.ModelManager;
/*     */ import net.minecraft.client.resources.model.ModelResourceLocation;
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.entity.item.EntityItem;
/*     */ import net.minecraft.entity.item.EntityItemFrame;
/*     */ import net.minecraft.init.Items;
/*     */ import net.minecraft.item.Item;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraft.src.Config;
/*     */ import net.minecraft.util.BlockPos;
/*     */ import net.minecraft.util.MathHelper;
/*     */ import net.minecraft.util.ResourceLocation;
/*     */ import net.minecraft.world.storage.MapData;
/*     */ import net.optifine.reflect.Reflector;
/*     */ import net.optifine.shaders.Shaders;
/*     */ import org.lwjgl.opengl.GL11;
/*     */ 
/*     */ public class RenderItemFrame
/*     */   extends Render<EntityItemFrame> {
/*  39 */   private static final ResourceLocation mapBackgroundTextures = new ResourceLocation("textures/map/map_background.png");
/*  40 */   private final Minecraft mc = Minecraft.getMinecraft();
/*  41 */   private final ModelResourceLocation itemFrameModel = new ModelResourceLocation("item_frame", "normal");
/*  42 */   private final ModelResourceLocation mapModel = new ModelResourceLocation("item_frame", "map");
/*     */   private RenderItem itemRenderer;
/*  44 */   private static double itemRenderDistanceSq = 4096.0D;
/*     */   
/*     */   public RenderItemFrame(RenderManager renderManagerIn, RenderItem itemRendererIn) {
/*  47 */     super(renderManagerIn);
/*  48 */     this.itemRenderer = itemRendererIn;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void doRender(EntityItemFrame entity, double x, double y, double z, float entityYaw, float partialTicks) {
/*     */     IBakedModel ibakedmodel;
/*  55 */     GlStateManager.pushMatrix();
/*  56 */     BlockPos blockpos = entity.getHangingPosition();
/*  57 */     double d0 = blockpos.getX() - entity.posX + x;
/*  58 */     double d1 = blockpos.getY() - entity.posY + y;
/*  59 */     double d2 = blockpos.getZ() - entity.posZ + z;
/*  60 */     GlStateManager.translate(d0 + 0.5D, d1 + 0.5D, d2 + 0.5D);
/*  61 */     GlStateManager.rotate(180.0F - entity.rotationYaw, 0.0F, 1.0F, 0.0F);
/*  62 */     this.renderManager.renderEngine.bindTexture(TextureMap.locationBlocksTexture);
/*  63 */     BlockRendererDispatcher blockrendererdispatcher = this.mc.getBlockRendererDispatcher();
/*  64 */     ModelManager modelmanager = blockrendererdispatcher.getBlockModelShapes().getModelManager();
/*     */ 
/*     */     
/*  67 */     if (entity.getDisplayedItem() != null && entity.getDisplayedItem().getItem() == Items.filled_map) {
/*  68 */       ibakedmodel = modelmanager.getModel(this.mapModel);
/*     */     } else {
/*  70 */       ibakedmodel = modelmanager.getModel(this.itemFrameModel);
/*     */     } 
/*     */     
/*  73 */     GlStateManager.pushMatrix();
/*  74 */     GlStateManager.translate(-0.5F, -0.5F, -0.5F);
/*  75 */     blockrendererdispatcher.getBlockModelRenderer().renderModelBrightnessColor(ibakedmodel, 1.0F, 1.0F, 1.0F, 1.0F);
/*  76 */     GlStateManager.popMatrix();
/*  77 */     GlStateManager.translate(0.0F, 0.0F, 0.4375F);
/*  78 */     renderItem(entity);
/*  79 */     GlStateManager.popMatrix();
/*  80 */     renderName(entity, x + (entity.facingDirection.getFrontOffsetX() * 0.3F), y - 0.25D, z + (entity.facingDirection.getFrontOffsetZ() * 0.3F));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected ResourceLocation getEntityTexture(EntityItemFrame entity) {
/*  87 */     return null;
/*     */   }
/*     */   
/*     */   private void renderItem(EntityItemFrame itemFrame) {
/*  91 */     ItemStack itemstack = itemFrame.getDisplayedItem();
/*     */     
/*  93 */     if (itemstack != null) {
/*  94 */       if (!isRenderItem(itemFrame)) {
/*     */         return;
/*     */       }
/*     */       
/*  98 */       if (!Config.zoomMode) {
/*  99 */         EntityPlayerSP entityPlayerSP = this.mc.thePlayer;
/* 100 */         double d0 = itemFrame.getDistanceSq(((Entity)entityPlayerSP).posX, ((Entity)entityPlayerSP).posY, ((Entity)entityPlayerSP).posZ);
/*     */         
/* 102 */         if (d0 > 4096.0D) {
/*     */           return;
/*     */         }
/*     */       } 
/*     */       
/* 107 */       EntityItem entityitem = new EntityItem(itemFrame.worldObj, 0.0D, 0.0D, 0.0D, itemstack);
/* 108 */       Item item = entityitem.getEntityItem().getItem();
/* 109 */       (entityitem.getEntityItem()).stackSize = 1;
/* 110 */       entityitem.hoverStart = 0.0F;
/* 111 */       GlStateManager.pushMatrix();
/* 112 */       GlStateManager.disableLighting();
/* 113 */       int i = itemFrame.getRotation();
/*     */       
/* 115 */       if (item instanceof net.minecraft.item.ItemMap) {
/* 116 */         i = i % 4 * 2;
/*     */       }
/*     */       
/* 119 */       GlStateManager.rotate(i * 360.0F / 8.0F, 0.0F, 0.0F, 1.0F);
/*     */       
/* 121 */       if (!Reflector.postForgeBusEvent(Reflector.RenderItemInFrameEvent_Constructor, new Object[] { itemFrame, this })) {
/* 122 */         if (item instanceof net.minecraft.item.ItemMap) {
/* 123 */           this.renderManager.renderEngine.bindTexture(mapBackgroundTextures);
/* 124 */           GlStateManager.rotate(180.0F, 0.0F, 0.0F, 1.0F);
/* 125 */           float f = 0.0078125F;
/* 126 */           GlStateManager.scale(f, f, f);
/* 127 */           GlStateManager.translate(-64.0F, -64.0F, 0.0F);
/* 128 */           MapData mapdata = Items.filled_map.getMapData(entityitem.getEntityItem(), itemFrame.worldObj);
/* 129 */           GlStateManager.translate(0.0F, 0.0F, -1.0F);
/*     */           
/* 131 */           if (mapdata != null) {
/* 132 */             this.mc.entityRenderer.getMapItemRenderer().renderMap(mapdata, true);
/*     */           }
/*     */         } else {
/* 135 */           TextureAtlasSprite textureatlassprite = null;
/*     */           
/* 137 */           if (item == Items.compass) {
/* 138 */             textureatlassprite = this.mc.getTextureMapBlocks().getAtlasSprite(TextureCompass.locationSprite);
/* 139 */             this.mc.getTextureManager().bindTexture(TextureMap.locationBlocksTexture);
/*     */             
/* 141 */             if (textureatlassprite instanceof TextureCompass) {
/* 142 */               TextureCompass texturecompass = (TextureCompass)textureatlassprite;
/* 143 */               double d1 = texturecompass.currentAngle;
/* 144 */               double d2 = texturecompass.angleDelta;
/* 145 */               texturecompass.currentAngle = 0.0D;
/* 146 */               texturecompass.angleDelta = 0.0D;
/* 147 */               texturecompass.updateCompass(itemFrame.worldObj, itemFrame.posX, itemFrame.posZ, MathHelper.wrapAngleTo180_float((180 + itemFrame.facingDirection.getHorizontalIndex() * 90)), false, true);
/* 148 */               texturecompass.currentAngle = d1;
/* 149 */               texturecompass.angleDelta = d2;
/*     */             } else {
/* 151 */               textureatlassprite = null;
/*     */             } 
/*     */           } 
/*     */           
/* 155 */           GlStateManager.scale(0.5F, 0.5F, 0.5F);
/*     */           
/* 157 */           if (!this.itemRenderer.shouldRenderItemIn3D(entityitem.getEntityItem()) || item instanceof net.minecraft.item.ItemSkull) {
/* 158 */             GlStateManager.rotate(180.0F, 0.0F, 1.0F, 0.0F);
/*     */           }
/*     */           
/* 161 */           GlStateManager.pushAttrib();
/* 162 */           RenderHelper.enableStandardItemLighting();
/* 163 */           this.itemRenderer.renderItem(entityitem.getEntityItem(), ItemCameraTransforms.TransformType.FIXED);
/* 164 */           RenderHelper.disableStandardItemLighting();
/* 165 */           GlStateManager.popAttrib();
/*     */           
/* 167 */           if (textureatlassprite != null && textureatlassprite.getFrameCount() > 0) {
/* 168 */             textureatlassprite.updateAnimation();
/*     */           }
/*     */         } 
/*     */       }
/* 172 */       GlStateManager.enableLighting();
/* 173 */       GlStateManager.popMatrix();
/*     */     } 
/*     */   }
/*     */   
/*     */   protected void renderName(EntityItemFrame entity, double x, double y, double z) {
/* 178 */     if (Minecraft.isGuiEnabled() && entity.getDisplayedItem() != null && entity.getDisplayedItem().hasDisplayName() && this.renderManager.pointedEntity == entity) {
/* 179 */       float f = 1.6F;
/* 180 */       float f1 = 0.016666668F * f;
/* 181 */       double d0 = entity.getDistanceSqToEntity(this.renderManager.livingPlayer);
/* 182 */       float f2 = entity.isSneaking() ? 32.0F : 64.0F;
/*     */       
/* 184 */       if (d0 < (f2 * f2)) {
/* 185 */         String s = entity.getDisplayedItem().getDisplayName();
/*     */         
/* 187 */         if (entity.isSneaking()) {
/* 188 */           FontRenderer fontrenderer = getFontRendererFromRenderManager();
/* 189 */           GlStateManager.pushMatrix();
/* 190 */           GlStateManager.translate((float)x + 0.0F, (float)y + entity.height + 0.5F, (float)z);
/* 191 */           GL11.glNormal3f(0.0F, 1.0F, 0.0F);
/* 192 */           GlStateManager.rotate(-this.renderManager.playerViewY, 0.0F, 1.0F, 0.0F);
/* 193 */           GlStateManager.rotate(this.renderManager.playerViewX, 1.0F, 0.0F, 0.0F);
/* 194 */           GlStateManager.scale(-f1, -f1, f1);
/* 195 */           GlStateManager.disableLighting();
/* 196 */           GlStateManager.translate(0.0F, 0.25F / f1, 0.0F);
/* 197 */           GlStateManager.depthMask(false);
/* 198 */           GlStateManager.enableBlend();
/* 199 */           GlStateManager.blendFunc(770, 771);
/* 200 */           Tessellator tessellator = Tessellator.getInstance();
/* 201 */           WorldRenderer worldrenderer = tessellator.getWorldRenderer();
/* 202 */           int i = fontrenderer.getStringWidth(s) / 2;
/* 203 */           GlStateManager.disableTexture2D();
/* 204 */           worldrenderer.begin(7, DefaultVertexFormats.POSITION_COLOR);
/* 205 */           worldrenderer.pos((-i - 1), -1.0D, 0.0D).color(0.0F, 0.0F, 0.0F, 0.25F).endVertex();
/* 206 */           worldrenderer.pos((-i - 1), 8.0D, 0.0D).color(0.0F, 0.0F, 0.0F, 0.25F).endVertex();
/* 207 */           worldrenderer.pos((i + 1), 8.0D, 0.0D).color(0.0F, 0.0F, 0.0F, 0.25F).endVertex();
/* 208 */           worldrenderer.pos((i + 1), -1.0D, 0.0D).color(0.0F, 0.0F, 0.0F, 0.25F).endVertex();
/* 209 */           tessellator.draw();
/* 210 */           GlStateManager.enableTexture2D();
/* 211 */           GlStateManager.depthMask(true);
/* 212 */           fontrenderer.drawString(s, -fontrenderer.getStringWidth(s) / 2, 0, 553648127);
/* 213 */           GlStateManager.enableLighting();
/* 214 */           GlStateManager.disableBlend();
/* 215 */           GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
/* 216 */           GlStateManager.popMatrix();
/*     */         } else {
/* 218 */           renderLivingLabel((Entity)entity, s, x, y, z, 64);
/*     */         } 
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   private boolean isRenderItem(EntityItemFrame p_isRenderItem_1_) {
/* 225 */     if (Shaders.isShadowPass) {
/* 226 */       return false;
/*     */     }
/* 228 */     if (!Config.zoomMode) {
/* 229 */       Entity entity = this.mc.getRenderViewEntity();
/* 230 */       double d0 = p_isRenderItem_1_.getDistanceSq(entity.posX, entity.posY, entity.posZ);
/*     */       
/* 232 */       if (d0 > itemRenderDistanceSq) {
/* 233 */         return false;
/*     */       }
/*     */     } 
/*     */     
/* 237 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   public static void updateItemRenderDistance() {
/* 242 */     Minecraft minecraft = Config.getMinecraft();
/* 243 */     double d0 = Config.limit(minecraft.gameSettings.gammaSetting, 1.0F, 120.0F);
/* 244 */     double d1 = Math.max(6.0D * minecraft.displayHeight / d0, 16.0D);
/* 245 */     itemRenderDistanceSq = d1 * d1;
/*     */   }
/*     */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\minecraft\client\renderer\tileentity\RenderItemFrame.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */