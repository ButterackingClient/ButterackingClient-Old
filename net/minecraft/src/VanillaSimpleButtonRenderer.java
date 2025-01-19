/*    */ package net.minecraft.src;
/*    */ 
/*    */ import kp.ui.SimpleButton;
/*    */ import net.minecraft.client.Minecraft;
/*    */ import net.minecraft.client.gui.FontRenderer;
/*    */ import net.minecraft.client.gui.Gui;
/*    */ import net.minecraft.client.renderer.GlStateManager;
/*    */ 
/*    */ public class VanillaSimpleButtonRenderer implements SimpleButton.IButtonRenderer {
/*    */   public void render(SimpleButton p_render_1_) {
/* 11 */     FontRenderer fontrenderer = (Minecraft.getMinecraft()).fontRendererObj;
/* 12 */     int i = fontrenderer.FONT_HEIGHT;
/* 13 */     int j = fontrenderer.getStringWidth(p_render_1_.text);
/* 14 */     int k = p_render_1_.hover ? -2144128205 : Integer.MIN_VALUE;
/* 15 */     int l = p_render_1_.useState ? 12 : 0;
/* 16 */     Gui.drawRect(p_render_1_.x, p_render_1_.y, p_render_1_.x + p_render_1_.width + l, p_render_1_.y + p_render_1_.height, k);
/* 17 */     fontrenderer.drawString(p_render_1_.text, p_render_1_.x + (p_render_1_.width - j) / 2 + l, p_render_1_.y + (p_render_1_.height - i) / 2, -1);
/* 18 */     if (p_render_1_.useState) {
/* 19 */       IconManager.drawIcon(p_render_1_.x, p_render_1_.y, p_render_1_.height, p_render_1_.height, 1, 0);
/* 20 */       if (p_render_1_.state) {
/* 21 */         GlStateManager.color(0.0F, 1.0F, 0.0F);
/* 22 */         IconManager.drawIcon(p_render_1_.x, p_render_1_.y, p_render_1_.height, p_render_1_.height, 0, 0);
/*    */       } 
/*    */     } 
/*    */   }
/*    */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\minecraft\src\VanillaSimpleButtonRenderer.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */