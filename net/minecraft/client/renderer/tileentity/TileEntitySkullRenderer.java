/*     */ package net.minecraft.client.renderer.tileentity;
/*     */ 
/*     */ import com.mojang.authlib.GameProfile;
/*     */ import com.mojang.authlib.minecraft.MinecraftProfileTexture;
/*     */ import java.util.Map;
/*     */ import java.util.UUID;
/*     */ import net.minecraft.client.Minecraft;
/*     */ import net.minecraft.client.model.ModelHumanoidHead;
/*     */ import net.minecraft.client.model.ModelSkeletonHead;
/*     */ import net.minecraft.client.renderer.GlStateManager;
/*     */ import net.minecraft.client.resources.DefaultPlayerSkin;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.tileentity.TileEntity;
/*     */ import net.minecraft.tileentity.TileEntitySkull;
/*     */ import net.minecraft.util.EnumFacing;
/*     */ import net.minecraft.util.ResourceLocation;
/*     */ 
/*     */ 
/*     */ 
/*     */ public class TileEntitySkullRenderer
/*     */   extends TileEntitySpecialRenderer<TileEntitySkull>
/*     */ {
/*  23 */   private static final ResourceLocation SKELETON_TEXTURES = new ResourceLocation("textures/entity/skeleton/skeleton.png");
/*  24 */   private static final ResourceLocation WITHER_SKELETON_TEXTURES = new ResourceLocation("textures/entity/skeleton/wither_skeleton.png");
/*  25 */   private static final ResourceLocation ZOMBIE_TEXTURES = new ResourceLocation("textures/entity/zombie/zombie.png");
/*  26 */   private static final ResourceLocation CREEPER_TEXTURES = new ResourceLocation("textures/entity/creeper/creeper.png");
/*     */   public static TileEntitySkullRenderer instance;
/*  28 */   private final ModelSkeletonHead skeletonHead = new ModelSkeletonHead(0, 0, 64, 32);
/*  29 */   private final ModelSkeletonHead humanoidHead = (ModelSkeletonHead)new ModelHumanoidHead();
/*     */   
/*     */   public void renderTileEntityAt(TileEntitySkull te, double x, double y, double z, float partialTicks, int destroyStage) {
/*  32 */     EnumFacing enumfacing = EnumFacing.getFront(te.getBlockMetadata() & 0x7);
/*  33 */     renderSkull((float)x, (float)y, (float)z, enumfacing, (te.getSkullRotation() * 360) / 16.0F, te.getSkullType(), te.getPlayerProfile(), destroyStage);
/*     */   }
/*     */   
/*     */   public void setRendererDispatcher(TileEntityRendererDispatcher rendererDispatcherIn) {
/*  37 */     super.setRendererDispatcher(rendererDispatcherIn);
/*  38 */     instance = this;
/*     */   }
/*     */   
/*     */   public void renderSkull(float p_180543_1_, float p_180543_2_, float p_180543_3_, EnumFacing p_180543_4_, float p_180543_5_, int p_180543_6_, GameProfile p_180543_7_, int p_180543_8_) {
/*  42 */     ModelSkeletonHead modelSkeletonHead = this.skeletonHead;
/*     */     
/*  44 */     if (p_180543_8_ >= 0) {
/*  45 */       bindTexture(DESTROY_STAGES[p_180543_8_]);
/*  46 */       GlStateManager.matrixMode(5890);
/*  47 */       GlStateManager.pushMatrix();
/*  48 */       GlStateManager.scale(4.0F, 2.0F, 1.0F);
/*  49 */       GlStateManager.translate(0.0625F, 0.0625F, 0.0625F);
/*  50 */       GlStateManager.matrixMode(5888);
/*     */     } else {
/*  52 */       ResourceLocation resourcelocation; switch (p_180543_6_) {
/*     */         
/*     */         default:
/*  55 */           bindTexture(SKELETON_TEXTURES);
/*     */           break;
/*     */         
/*     */         case 1:
/*  59 */           bindTexture(WITHER_SKELETON_TEXTURES);
/*     */           break;
/*     */         
/*     */         case 2:
/*  63 */           bindTexture(ZOMBIE_TEXTURES);
/*  64 */           modelSkeletonHead = this.humanoidHead;
/*     */           break;
/*     */         
/*     */         case 3:
/*  68 */           modelSkeletonHead = this.humanoidHead;
/*  69 */           resourcelocation = DefaultPlayerSkin.getDefaultSkinLegacy();
/*     */           
/*  71 */           if (p_180543_7_ != null) {
/*  72 */             Minecraft minecraft = Minecraft.getMinecraft();
/*  73 */             Map<MinecraftProfileTexture.Type, MinecraftProfileTexture> map = minecraft.getSkinManager().loadSkinFromCache(p_180543_7_);
/*     */             
/*  75 */             if (map.containsKey(MinecraftProfileTexture.Type.SKIN)) {
/*  76 */               resourcelocation = minecraft.getSkinManager().loadSkin(map.get(MinecraftProfileTexture.Type.SKIN), MinecraftProfileTexture.Type.SKIN);
/*     */             } else {
/*  78 */               UUID uuid = EntityPlayer.getUUID(p_180543_7_);
/*  79 */               resourcelocation = DefaultPlayerSkin.getDefaultSkin(uuid);
/*     */             } 
/*     */           } 
/*     */           
/*  83 */           bindTexture(resourcelocation);
/*     */           break;
/*     */         
/*     */         case 4:
/*  87 */           bindTexture(CREEPER_TEXTURES);
/*     */           break;
/*     */       } 
/*     */     } 
/*  91 */     GlStateManager.pushMatrix();
/*  92 */     GlStateManager.disableCull();
/*     */     
/*  94 */     if (p_180543_4_ != EnumFacing.UP) {
/*  95 */       switch (p_180543_4_) {
/*     */         case NORTH:
/*  97 */           GlStateManager.translate(p_180543_1_ + 0.5F, p_180543_2_ + 0.25F, p_180543_3_ + 0.74F);
/*     */           break;
/*     */         
/*     */         case SOUTH:
/* 101 */           GlStateManager.translate(p_180543_1_ + 0.5F, p_180543_2_ + 0.25F, p_180543_3_ + 0.26F);
/* 102 */           p_180543_5_ = 180.0F;
/*     */           break;
/*     */         
/*     */         case WEST:
/* 106 */           GlStateManager.translate(p_180543_1_ + 0.74F, p_180543_2_ + 0.25F, p_180543_3_ + 0.5F);
/* 107 */           p_180543_5_ = 270.0F;
/*     */           break;
/*     */ 
/*     */         
/*     */         default:
/* 112 */           GlStateManager.translate(p_180543_1_ + 0.26F, p_180543_2_ + 0.25F, p_180543_3_ + 0.5F);
/* 113 */           p_180543_5_ = 90.0F; break;
/*     */       } 
/*     */     } else {
/* 116 */       GlStateManager.translate(p_180543_1_ + 0.5F, p_180543_2_, p_180543_3_ + 0.5F);
/*     */     } 
/*     */     
/* 119 */     float f = 0.0625F;
/* 120 */     GlStateManager.enableRescaleNormal();
/* 121 */     GlStateManager.scale(-1.0F, -1.0F, 1.0F);
/* 122 */     GlStateManager.enableAlpha();
/* 123 */     modelSkeletonHead.render(null, 0.0F, 0.0F, 0.0F, p_180543_5_, 0.0F, f);
/* 124 */     GlStateManager.popMatrix();
/*     */     
/* 126 */     if (p_180543_8_ >= 0) {
/* 127 */       GlStateManager.matrixMode(5890);
/* 128 */       GlStateManager.popMatrix();
/* 129 */       GlStateManager.matrixMode(5888);
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\minecraft\client\renderer\tileentity\TileEntitySkullRenderer.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */