/*     */ package net.minecraft.client.renderer.tileentity;
/*     */ 
/*     */ import com.google.common.collect.Lists;
/*     */ import com.google.common.collect.Maps;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import net.minecraft.client.Minecraft;
/*     */ import net.minecraft.client.model.ModelBanner;
/*     */ import net.minecraft.client.renderer.GlStateManager;
/*     */ import net.minecraft.client.renderer.texture.ITextureObject;
/*     */ import net.minecraft.client.renderer.texture.LayeredColorMaskTexture;
/*     */ import net.minecraft.init.Blocks;
/*     */ import net.minecraft.item.EnumDyeColor;
/*     */ import net.minecraft.tileentity.TileEntity;
/*     */ import net.minecraft.tileentity.TileEntityBanner;
/*     */ import net.minecraft.util.BlockPos;
/*     */ import net.minecraft.util.MathHelper;
/*     */ import net.minecraft.util.ResourceLocation;
/*     */ 
/*     */ public class TileEntityBannerRenderer extends TileEntitySpecialRenderer<TileEntityBanner> {
/*  22 */   private static final Map<String, TimedBannerTexture> DESIGNS = Maps.newHashMap();
/*  23 */   private static final ResourceLocation BANNERTEXTURES = new ResourceLocation("textures/entity/banner_base.png");
/*  24 */   private ModelBanner bannerModel = new ModelBanner();
/*     */   
/*     */   public void renderTileEntityAt(TileEntityBanner te, double x, double y, double z, float partialTicks, int destroyStage) {
/*  27 */     boolean flag = (te.getWorld() != null);
/*  28 */     boolean flag1 = !(flag && te.getBlockType() != Blocks.standing_banner);
/*  29 */     int i = flag ? te.getBlockMetadata() : 0;
/*  30 */     long j = flag ? te.getWorld().getTotalWorldTime() : 0L;
/*  31 */     GlStateManager.pushMatrix();
/*  32 */     float f = 0.6666667F;
/*     */     
/*  34 */     if (flag1) {
/*  35 */       GlStateManager.translate((float)x + 0.5F, (float)y + 0.75F * f, (float)z + 0.5F);
/*  36 */       float f1 = (i * 360) / 16.0F;
/*  37 */       GlStateManager.rotate(-f1, 0.0F, 1.0F, 0.0F);
/*  38 */       this.bannerModel.bannerStand.showModel = true;
/*     */     } else {
/*  40 */       float f2 = 0.0F;
/*     */       
/*  42 */       if (i == 2) {
/*  43 */         f2 = 180.0F;
/*     */       }
/*     */       
/*  46 */       if (i == 4) {
/*  47 */         f2 = 90.0F;
/*     */       }
/*     */       
/*  50 */       if (i == 5) {
/*  51 */         f2 = -90.0F;
/*     */       }
/*     */       
/*  54 */       GlStateManager.translate((float)x + 0.5F, (float)y - 0.25F * f, (float)z + 0.5F);
/*  55 */       GlStateManager.rotate(-f2, 0.0F, 1.0F, 0.0F);
/*  56 */       GlStateManager.translate(0.0F, -0.3125F, -0.4375F);
/*  57 */       this.bannerModel.bannerStand.showModel = false;
/*     */     } 
/*     */     
/*  60 */     BlockPos blockpos = te.getPos();
/*  61 */     float f3 = (blockpos.getX() * 7 + blockpos.getY() * 9 + blockpos.getZ() * 13) + (float)j + partialTicks;
/*  62 */     this.bannerModel.bannerSlate.rotateAngleX = (-0.0125F + 0.01F * MathHelper.cos(f3 * 3.1415927F * 0.02F)) * 3.1415927F;
/*  63 */     GlStateManager.enableRescaleNormal();
/*  64 */     ResourceLocation resourcelocation = func_178463_a(te);
/*     */     
/*  66 */     if (resourcelocation != null) {
/*  67 */       bindTexture(resourcelocation);
/*  68 */       GlStateManager.pushMatrix();
/*  69 */       GlStateManager.scale(f, -f, -f);
/*  70 */       this.bannerModel.renderBanner();
/*  71 */       GlStateManager.popMatrix();
/*     */     } 
/*     */     
/*  74 */     GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
/*  75 */     GlStateManager.popMatrix();
/*     */   }
/*     */   
/*     */   private ResourceLocation func_178463_a(TileEntityBanner bannerObj) {
/*  79 */     String s = bannerObj.getPatternResourceLocation();
/*     */     
/*  81 */     if (s.isEmpty()) {
/*  82 */       return null;
/*     */     }
/*  84 */     TimedBannerTexture tileentitybannerrenderer$timedbannertexture = DESIGNS.get(s);
/*     */     
/*  86 */     if (tileentitybannerrenderer$timedbannertexture == null) {
/*  87 */       if (DESIGNS.size() >= 256) {
/*  88 */         long i = System.currentTimeMillis();
/*  89 */         Iterator<String> iterator = DESIGNS.keySet().iterator();
/*     */         
/*  91 */         while (iterator.hasNext()) {
/*  92 */           String s1 = iterator.next();
/*  93 */           TimedBannerTexture tileentitybannerrenderer$timedbannertexture1 = DESIGNS.get(s1);
/*     */           
/*  95 */           if (i - tileentitybannerrenderer$timedbannertexture1.systemTime > 60000L) {
/*  96 */             Minecraft.getMinecraft().getTextureManager().deleteTexture(tileentitybannerrenderer$timedbannertexture1.bannerTexture);
/*  97 */             iterator.remove();
/*     */           } 
/*     */         } 
/*     */         
/* 101 */         if (DESIGNS.size() >= 256) {
/* 102 */           return null;
/*     */         }
/*     */       } 
/*     */       
/* 106 */       List<TileEntityBanner.EnumBannerPattern> list1 = bannerObj.getPatternList();
/* 107 */       List<EnumDyeColor> list = bannerObj.getColorList();
/* 108 */       List<String> list2 = Lists.newArrayList();
/*     */       
/* 110 */       for (TileEntityBanner.EnumBannerPattern tileentitybanner$enumbannerpattern : list1) {
/* 111 */         list2.add("textures/entity/banner/" + tileentitybanner$enumbannerpattern.getPatternName() + ".png");
/*     */       }
/*     */       
/* 114 */       tileentitybannerrenderer$timedbannertexture = new TimedBannerTexture(null);
/* 115 */       tileentitybannerrenderer$timedbannertexture.bannerTexture = new ResourceLocation(s);
/* 116 */       Minecraft.getMinecraft().getTextureManager().loadTexture(tileentitybannerrenderer$timedbannertexture.bannerTexture, (ITextureObject)new LayeredColorMaskTexture(BANNERTEXTURES, list2, list));
/* 117 */       DESIGNS.put(s, tileentitybannerrenderer$timedbannertexture);
/*     */     } 
/*     */     
/* 120 */     tileentitybannerrenderer$timedbannertexture.systemTime = System.currentTimeMillis();
/* 121 */     return tileentitybannerrenderer$timedbannertexture.bannerTexture;
/*     */   }
/*     */   
/*     */   static class TimedBannerTexture {
/*     */     public long systemTime;
/*     */     public ResourceLocation bannerTexture;
/*     */     
/*     */     private TimedBannerTexture() {}
/*     */   }
/*     */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\minecraft\client\renderer\tileentity\TileEntityBannerRenderer.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */