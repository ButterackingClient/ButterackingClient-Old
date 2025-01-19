/*     */ package net.minecraft.client.renderer.tileentity;
/*     */ 
/*     */ import java.util.List;
/*     */ import net.minecraft.block.Block;
/*     */ import net.minecraft.client.Minecraft;
/*     */ import net.minecraft.client.gui.FontRenderer;
/*     */ import net.minecraft.client.gui.GuiUtilRenderComponents;
/*     */ import net.minecraft.client.model.ModelSign;
/*     */ import net.minecraft.client.renderer.GlStateManager;
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.init.Blocks;
/*     */ import net.minecraft.src.Config;
/*     */ import net.minecraft.tileentity.TileEntity;
/*     */ import net.minecraft.tileentity.TileEntitySign;
/*     */ import net.minecraft.util.IChatComponent;
/*     */ import net.minecraft.util.ResourceLocation;
/*     */ import net.optifine.CustomColors;
/*     */ import net.optifine.shaders.Shaders;
/*     */ import org.lwjgl.opengl.GL11;
/*     */ 
/*     */ public class TileEntitySignRenderer
/*     */   extends TileEntitySpecialRenderer<TileEntitySign> {
/*  23 */   private static final ResourceLocation SIGN_TEXTURE = new ResourceLocation("textures/entity/sign.png");
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  28 */   private final ModelSign model = new ModelSign();
/*  29 */   private static double textRenderDistanceSq = 4096.0D;
/*     */   
/*     */   public void renderTileEntityAt(TileEntitySign te, double x, double y, double z, float partialTicks, int destroyStage) {
/*  32 */     Block block = te.getBlockType();
/*  33 */     GlStateManager.pushMatrix();
/*  34 */     float f = 0.6666667F;
/*     */     
/*  36 */     if (block == Blocks.standing_sign) {
/*  37 */       GlStateManager.translate((float)x + 0.5F, (float)y + 0.75F * f, (float)z + 0.5F);
/*  38 */       float f1 = (te.getBlockMetadata() * 360) / 16.0F;
/*  39 */       GlStateManager.rotate(-f1, 0.0F, 1.0F, 0.0F);
/*  40 */       this.model.signStick.showModel = true;
/*     */     } else {
/*  42 */       int k = te.getBlockMetadata();
/*  43 */       float f2 = 0.0F;
/*     */       
/*  45 */       if (k == 2) {
/*  46 */         f2 = 180.0F;
/*     */       }
/*     */       
/*  49 */       if (k == 4) {
/*  50 */         f2 = 90.0F;
/*     */       }
/*     */       
/*  53 */       if (k == 5) {
/*  54 */         f2 = -90.0F;
/*     */       }
/*     */       
/*  57 */       GlStateManager.translate((float)x + 0.5F, (float)y + 0.75F * f, (float)z + 0.5F);
/*  58 */       GlStateManager.rotate(-f2, 0.0F, 1.0F, 0.0F);
/*  59 */       GlStateManager.translate(0.0F, -0.3125F, -0.4375F);
/*  60 */       this.model.signStick.showModel = false;
/*     */     } 
/*     */     
/*  63 */     if (destroyStage >= 0) {
/*  64 */       bindTexture(DESTROY_STAGES[destroyStage]);
/*  65 */       GlStateManager.matrixMode(5890);
/*  66 */       GlStateManager.pushMatrix();
/*  67 */       GlStateManager.scale(4.0F, 2.0F, 1.0F);
/*  68 */       GlStateManager.translate(0.0625F, 0.0625F, 0.0625F);
/*  69 */       GlStateManager.matrixMode(5888);
/*     */     } else {
/*  71 */       bindTexture(SIGN_TEXTURE);
/*     */     } 
/*     */     
/*  74 */     GlStateManager.enableRescaleNormal();
/*  75 */     GlStateManager.pushMatrix();
/*  76 */     GlStateManager.scale(f, -f, -f);
/*  77 */     this.model.renderSign();
/*  78 */     GlStateManager.popMatrix();
/*     */     
/*  80 */     if (isRenderText(te)) {
/*  81 */       FontRenderer fontrenderer = getFontRenderer();
/*  82 */       float f3 = 0.015625F * f;
/*  83 */       GlStateManager.translate(0.0F, 0.5F * f, 0.07F * f);
/*  84 */       GlStateManager.scale(f3, -f3, f3);
/*  85 */       GL11.glNormal3f(0.0F, 0.0F, -1.0F * f3);
/*  86 */       GlStateManager.depthMask(false);
/*  87 */       int i = 0;
/*     */       
/*  89 */       if (Config.isCustomColors()) {
/*  90 */         i = CustomColors.getSignTextColor(i);
/*     */       }
/*     */       
/*  93 */       if (destroyStage < 0) {
/*  94 */         for (int j = 0; j < te.signText.length; j++) {
/*  95 */           if (te.signText[j] != null) {
/*  96 */             IChatComponent ichatcomponent = te.signText[j];
/*  97 */             List<IChatComponent> list = GuiUtilRenderComponents.splitText(ichatcomponent, 90, fontrenderer, false, true);
/*  98 */             String s = (list != null && list.size() > 0) ? ((IChatComponent)list.get(0)).getFormattedText() : "";
/*     */             
/* 100 */             if (j == te.lineBeingEdited) {
/* 101 */               s = "> " + s + " <";
/* 102 */               fontrenderer.drawString(s, -fontrenderer.getStringWidth(s) / 2, j * 10 - te.signText.length * 5, i);
/*     */             } else {
/* 104 */               fontrenderer.drawString(s, -fontrenderer.getStringWidth(s) / 2, j * 10 - te.signText.length * 5, i);
/*     */             } 
/*     */           } 
/*     */         } 
/*     */       }
/*     */     } 
/*     */     
/* 111 */     GlStateManager.depthMask(true);
/* 112 */     GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
/* 113 */     GlStateManager.popMatrix();
/*     */     
/* 115 */     if (destroyStage >= 0) {
/* 116 */       GlStateManager.matrixMode(5890);
/* 117 */       GlStateManager.popMatrix();
/* 118 */       GlStateManager.matrixMode(5888);
/*     */     } 
/*     */   }
/*     */   
/*     */   private static boolean isRenderText(TileEntitySign p_isRenderText_0_) {
/* 123 */     if (Shaders.isShadowPass)
/* 124 */       return false; 
/* 125 */     if ((Config.getMinecraft()).currentScreen instanceof net.minecraft.client.gui.inventory.GuiEditSign) {
/* 126 */       return true;
/*     */     }
/* 128 */     if (!Config.zoomMode && p_isRenderText_0_.lineBeingEdited < 0) {
/* 129 */       Entity entity = Config.getMinecraft().getRenderViewEntity();
/* 130 */       double d0 = p_isRenderText_0_.getDistanceSq(entity.posX, entity.posY, entity.posZ);
/*     */       
/* 132 */       if (d0 > textRenderDistanceSq) {
/* 133 */         return false;
/*     */       }
/*     */     } 
/*     */     
/* 137 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   public static void updateTextRenderDistance() {
/* 142 */     Minecraft minecraft = Config.getMinecraft();
/* 143 */     double d0 = Config.limit(minecraft.gameSettings.gammaSetting, 1.0F, 120.0F);
/* 144 */     double d1 = Math.max(1.5D * minecraft.displayHeight / d0, 16.0D);
/* 145 */     textRenderDistanceSq = d1 * d1;
/*     */   }
/*     */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\minecraft\client\renderer\tileentity\TileEntitySignRenderer.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */