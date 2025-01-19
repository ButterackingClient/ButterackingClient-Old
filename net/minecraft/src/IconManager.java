/*    */ package net.minecraft.src;
/*    */ 
/*    */ import net.minecraft.client.Minecraft;
/*    */ import net.minecraft.client.renderer.GlStateManager;
/*    */ import net.minecraft.client.renderer.Tessellator;
/*    */ import net.minecraft.client.renderer.WorldRenderer;
/*    */ import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
/*    */ import net.minecraft.util.ResourceLocation;
/*    */ 
/*    */ public class IconManager
/*    */ {
/* 12 */   public static final ResourceLocation ICON = new ResourceLocation("textures/kpicons.png");
/*    */ 
/*    */   
/*    */   public static void drawIcon(int p_drawIcon_0_, int p_drawIcon_1_, int p_drawIcon_2_, int p_drawIcon_3_, int p_drawIcon_4_, int p_drawIcon_5_) {
/* 16 */     Tessellator tessellator = Tessellator.getInstance();
/* 17 */     WorldRenderer worldrenderer = tessellator.getWorldRenderer();
/* 18 */     double d0 = 0.125D;
/* 19 */     double d2 = 0.125D * p_drawIcon_4_;
/* 20 */     double d3 = 0.125D * p_drawIcon_5_;
/* 21 */     Minecraft.getMinecraft().getTextureManager().bindTexture(ICON);
/* 22 */     GlStateManager.enableBlend();
/* 23 */     GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
/* 24 */     GlStateManager.enableTexture2D();
/* 25 */     worldrenderer.begin(7, DefaultVertexFormats.POSITION_TEX);
/* 26 */     worldrenderer.pos(p_drawIcon_0_, p_drawIcon_1_, 0.0D).tex(d2, d3).endVertex();
/* 27 */     worldrenderer.pos(p_drawIcon_0_, (p_drawIcon_1_ + p_drawIcon_3_), 0.0D).tex(d2, d3 + 0.125D).endVertex();
/* 28 */     worldrenderer.pos(p_drawIcon_0_ + p_drawIcon_2_, (p_drawIcon_1_ + p_drawIcon_3_), 0.0D).tex(d2 + 0.125D, d3 + 0.125D).endVertex();
/* 29 */     worldrenderer.pos(p_drawIcon_0_ + p_drawIcon_2_, p_drawIcon_1_, 0.0D).tex(d2 + 0.125D, d3).endVertex();
/* 30 */     tessellator.draw();
/* 31 */     GlStateManager.disableBlend();
/*    */   }
/*    */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\minecraft\src\IconManager.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */