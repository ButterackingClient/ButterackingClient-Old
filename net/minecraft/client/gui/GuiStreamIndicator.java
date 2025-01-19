/*    */ package net.minecraft.client.gui;
/*    */ 
/*    */ import net.minecraft.client.Minecraft;
/*    */ import net.minecraft.client.renderer.GlStateManager;
/*    */ import net.minecraft.client.renderer.Tessellator;
/*    */ import net.minecraft.client.renderer.WorldRenderer;
/*    */ import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
/*    */ import net.minecraft.util.ResourceLocation;
/*    */ 
/*    */ public class GuiStreamIndicator {
/* 11 */   private static final ResourceLocation locationStreamIndicator = new ResourceLocation("textures/gui/stream_indicator.png");
/*    */   private final Minecraft mc;
/* 13 */   private float streamAlpha = 1.0F;
/* 14 */   private int streamAlphaDelta = 1;
/*    */   
/*    */   public GuiStreamIndicator(Minecraft mcIn) {
/* 17 */     this.mc = mcIn;
/*    */   }
/*    */   
/*    */   public void render(int p_152437_1_, int p_152437_2_) {
/* 21 */     if (this.mc.getTwitchStream().isBroadcasting()) {
/* 22 */       GlStateManager.enableBlend();
/* 23 */       int i = this.mc.getTwitchStream().func_152920_A();
/*    */       
/* 25 */       if (i > 0) {
/* 26 */         int m = i;
/* 27 */         int j = this.mc.fontRendererObj.getStringWidth(m);
/* 28 */         int k = 20;
/* 29 */         int l = p_152437_1_ - j - 1;
/* 30 */         int i1 = p_152437_2_ + 20 - 1;
/* 31 */         int j1 = p_152437_2_ + 20 + this.mc.fontRendererObj.FONT_HEIGHT - 1;
/* 32 */         GlStateManager.disableTexture2D();
/* 33 */         Tessellator tessellator = Tessellator.getInstance();
/* 34 */         WorldRenderer worldrenderer = tessellator.getWorldRenderer();
/* 35 */         GlStateManager.color(0.0F, 0.0F, 0.0F, (0.65F + 0.35000002F * this.streamAlpha) / 2.0F);
/* 36 */         worldrenderer.begin(7, DefaultVertexFormats.POSITION);
/* 37 */         worldrenderer.pos(l, j1, 0.0D).endVertex();
/* 38 */         worldrenderer.pos(p_152437_1_, j1, 0.0D).endVertex();
/* 39 */         worldrenderer.pos(p_152437_1_, i1, 0.0D).endVertex();
/* 40 */         worldrenderer.pos(l, i1, 0.0D).endVertex();
/* 41 */         tessellator.draw();
/* 42 */         GlStateManager.enableTexture2D();
/* 43 */         this.mc.fontRendererObj.drawString(m, p_152437_1_ - j, p_152437_2_ + 20, 16777215);
/*    */       } 
/*    */       
/* 46 */       render(p_152437_1_, p_152437_2_, func_152440_b(), 0);
/* 47 */       render(p_152437_1_, p_152437_2_, func_152438_c(), 17);
/*    */     } 
/*    */   }
/*    */   
/*    */   private void render(int p_152436_1_, int p_152436_2_, int p_152436_3_, int p_152436_4_) {
/* 52 */     GlStateManager.color(1.0F, 1.0F, 1.0F, 0.65F + 0.35000002F * this.streamAlpha);
/* 53 */     this.mc.getTextureManager().bindTexture(locationStreamIndicator);
/* 54 */     float f = 150.0F;
/* 55 */     float f1 = 0.0F;
/* 56 */     float f2 = p_152436_3_ * 0.015625F;
/* 57 */     float f3 = 1.0F;
/* 58 */     float f4 = (p_152436_3_ + 16) * 0.015625F;
/* 59 */     Tessellator tessellator = Tessellator.getInstance();
/* 60 */     WorldRenderer worldrenderer = tessellator.getWorldRenderer();
/* 61 */     worldrenderer.begin(7, DefaultVertexFormats.POSITION_TEX);
/* 62 */     worldrenderer.pos((p_152436_1_ - 16 - p_152436_4_), (p_152436_2_ + 16), f).tex(f1, f4).endVertex();
/* 63 */     worldrenderer.pos((p_152436_1_ - p_152436_4_), (p_152436_2_ + 16), f).tex(f3, f4).endVertex();
/* 64 */     worldrenderer.pos((p_152436_1_ - p_152436_4_), (p_152436_2_ + 0), f).tex(f3, f2).endVertex();
/* 65 */     worldrenderer.pos((p_152436_1_ - 16 - p_152436_4_), (p_152436_2_ + 0), f).tex(f1, f2).endVertex();
/* 66 */     tessellator.draw();
/* 67 */     GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
/*    */   }
/*    */   
/*    */   private int func_152440_b() {
/* 71 */     return this.mc.getTwitchStream().isPaused() ? 16 : 0;
/*    */   }
/*    */   
/*    */   private int func_152438_c() {
/* 75 */     return this.mc.getTwitchStream().func_152929_G() ? 48 : 32;
/*    */   }
/*    */   
/*    */   public void updateStreamAlpha() {
/* 79 */     if (this.mc.getTwitchStream().isBroadcasting()) {
/* 80 */       this.streamAlpha += 0.025F * this.streamAlphaDelta;
/*    */       
/* 82 */       if (this.streamAlpha < 0.0F) {
/* 83 */         this.streamAlphaDelta *= -1;
/* 84 */         this.streamAlpha = 0.0F;
/* 85 */       } else if (this.streamAlpha > 1.0F) {
/* 86 */         this.streamAlphaDelta *= -1;
/* 87 */         this.streamAlpha = 1.0F;
/*    */       } 
/*    */     } else {
/* 90 */       this.streamAlpha = 1.0F;
/* 91 */       this.streamAlphaDelta = 1;
/*    */     } 
/*    */   }
/*    */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\minecraft\client\gui\GuiStreamIndicator.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */