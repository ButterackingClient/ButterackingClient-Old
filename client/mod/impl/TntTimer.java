/*    */ package client.mod.impl;
/*    */ 
/*    */ import client.hud.HudMod;
/*    */ import java.awt.Color;
/*    */ import net.minecraft.client.gui.FontRenderer;
/*    */ import net.minecraft.client.renderer.GlStateManager;
/*    */ import net.minecraft.client.renderer.Tessellator;
/*    */ import net.minecraft.client.renderer.WorldRenderer;
/*    */ import net.minecraft.client.renderer.entity.RenderTNTPrimed;
/*    */ import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
/*    */ import net.minecraft.entity.item.EntityTNTPrimed;
/*    */ import net.minecraft.scoreboard.ScoreObjective;
/*    */ import net.minecraft.scoreboard.Scoreboard;
/*    */ import net.minecraft.util.EnumChatFormatting;
/*    */ import org.lwjgl.opengl.GL11;
/*    */ 
/*    */ public class TntTimer
/*    */   extends HudMod
/*    */ {
/*    */   public TntTimer() {
/* 21 */     super("TNT Timer", 500000, 5000000, false);
/*    */   }
/*    */   
/*    */   private boolean isPlayingBedwars() {
/* 25 */     Scoreboard scoreboard = mc.theWorld.getScoreboard();
/* 26 */     ScoreObjective sidebarObjective = scoreboard.getObjectiveInDisplaySlot(1);
/* 27 */     if (sidebarObjective != null && EnumChatFormatting.getTextWithoutFormattingCodes(sidebarObjective.getDisplayName()).contains("BED WARS")) {
/* 28 */       return true;
/*    */     }
/* 30 */     return false;
/*    */   }
/*    */ 
/*    */   
/*    */   public void renderTag(RenderTNTPrimed tntRenderer, EntityTNTPrimed tntPrimed, double x, double y, double z, float partialTicks) {
/* 35 */     if (!isEnabled()) {
/*    */       return;
/*    */     }
/* 38 */     int fuseTimer = isPlayingBedwars() ? (tntPrimed.fuse - 28) : tntPrimed.fuse;
/* 39 */     if (fuseTimer < 1) {
/*    */       return;
/*    */     }
/* 42 */     double distance = tntPrimed.getDistanceSqToEntity((tntRenderer.getRenderManager()).livingPlayer);
/* 43 */     if (distance <= 4096.0D) {
/* 44 */       float number = (fuseTimer - partialTicks) / 20.0F;
/* 45 */       String time = String.format("%.2f", new Object[] { Float.valueOf(number) });
/* 46 */       FontRenderer fontrenderer = tntRenderer.getFontRendererFromRenderManager();
/* 47 */       GlStateManager.pushMatrix();
/* 48 */       GlStateManager.translate((float)x + 0.0F, (float)y + tntPrimed.height + 0.5F, (float)z);
/* 49 */       GL11.glNormal3f(0.0F, 1.0F, 0.0F);
/* 50 */       GlStateManager.rotate(-(tntRenderer.getRenderManager()).playerViewY, 0.0F, 1.0F, 0.0F);
/* 51 */       int xMultiplier = 1;
/* 52 */       if (mc.gameSettings.showDebugInfo == 2) {
/* 53 */         xMultiplier = -1;
/*    */       }
/* 55 */       float scale = 0.02666667F;
/* 56 */       GlStateManager.rotate((tntRenderer.getRenderManager()).playerViewX * xMultiplier, 1.0F, 0.0F, 0.0F);
/* 57 */       GlStateManager.scale(-0.02666667F, -0.02666667F, 0.02666667F);
/* 58 */       GlStateManager.disableLighting();
/* 59 */       GlStateManager.depthMask(false);
/* 60 */       GlStateManager.disableDepth();
/* 61 */       GlStateManager.enableBlend();
/* 62 */       GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
/* 63 */       Tessellator tessellator = Tessellator.getInstance();
/* 64 */       WorldRenderer worldrenderer = tessellator.getWorldRenderer();
/* 65 */       int stringWidth = fontrenderer.getStringWidth(time) >> 1;
/* 66 */       float green = Math.min(fuseTimer / (isPlayingBedwars() ? 52.0F : 80.0F), 1.0F);
/* 67 */       Color color = new Color(1.0F - green, green, 0.0F);
/* 68 */       GlStateManager.enableDepth();
/* 69 */       GlStateManager.depthMask(true);
/* 70 */       GlStateManager.disableTexture2D();
/* 71 */       worldrenderer.begin(7, DefaultVertexFormats.POSITION_COLOR);
/* 72 */       worldrenderer.pos((-stringWidth - 1), -1.0D, 0.0D).color(0.0F, 0.0F, 0.0F, 0.25F).endVertex();
/* 73 */       worldrenderer.pos((-stringWidth - 1), 8.0D, 0.0D).color(0.0F, 0.0F, 0.0F, 0.25F).endVertex();
/* 74 */       worldrenderer.pos((stringWidth + 1), 8.0D, 0.0D).color(0.0F, 0.0F, 0.0F, 0.25F).endVertex();
/* 75 */       worldrenderer.pos((stringWidth + 1), -1.0D, 0.0D).color(0.0F, 0.0F, 0.0F, 0.25F).endVertex();
/* 76 */       tessellator.draw();
/* 77 */       GlStateManager.enableTexture2D();
/* 78 */       fontrenderer.drawString(time, -fontrenderer.getStringWidth(time) >> 1, 0, color.getRGB());
/* 79 */       GlStateManager.enableLighting();
/* 80 */       GlStateManager.disableBlend();
/* 81 */       GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
/* 82 */       GlStateManager.popMatrix();
/*    */     } 
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public int getWidth() {
/* 89 */     return ClientName.fr.getStringWidth("");
/*    */   }
/*    */ 
/*    */   
/*    */   public void draw() {
/* 94 */     super.draw();
/*    */   }
/*    */ 
/*    */   
/*    */   public void renderDummy(int mouseX, int mouseY) {
/* 99 */     super.renderDummy(mouseX, mouseY);
/*    */   }
/*    */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\client\mod\impl\TntTimer.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */