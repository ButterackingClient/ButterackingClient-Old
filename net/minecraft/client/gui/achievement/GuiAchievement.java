/*     */ package net.minecraft.client.gui.achievement;
/*     */ 
/*     */ import net.minecraft.client.Minecraft;
/*     */ import net.minecraft.client.gui.Gui;
/*     */ import net.minecraft.client.gui.ScaledResolution;
/*     */ import net.minecraft.client.renderer.GlStateManager;
/*     */ import net.minecraft.client.renderer.RenderHelper;
/*     */ import net.minecraft.client.renderer.entity.RenderItem;
/*     */ import net.minecraft.client.resources.I18n;
/*     */ import net.minecraft.stats.Achievement;
/*     */ import net.minecraft.util.ResourceLocation;
/*     */ 
/*     */ public class GuiAchievement extends Gui {
/*  14 */   private static final ResourceLocation achievementBg = new ResourceLocation("textures/gui/achievement/achievement_background.png");
/*     */   private Minecraft mc;
/*     */   private int width;
/*     */   private int height;
/*     */   private String achievementTitle;
/*     */   private String achievementDescription;
/*     */   private Achievement theAchievement;
/*     */   private long notificationTime;
/*     */   private RenderItem renderItem;
/*     */   private boolean permanentNotification;
/*     */   
/*     */   public GuiAchievement(Minecraft mc) {
/*  26 */     this.mc = mc;
/*  27 */     this.renderItem = mc.getRenderItem();
/*     */   }
/*     */   
/*     */   public void displayAchievement(Achievement ach) {
/*  31 */     this.achievementTitle = I18n.format("achievement.get", new Object[0]);
/*  32 */     this.achievementDescription = ach.getStatName().getUnformattedText();
/*  33 */     this.notificationTime = Minecraft.getSystemTime();
/*  34 */     this.theAchievement = ach;
/*  35 */     this.permanentNotification = false;
/*     */   }
/*     */   
/*     */   public void displayUnformattedAchievement(Achievement achievementIn) {
/*  39 */     this.achievementTitle = achievementIn.getStatName().getUnformattedText();
/*  40 */     this.achievementDescription = achievementIn.getDescription();
/*  41 */     this.notificationTime = Minecraft.getSystemTime() + 2500L;
/*  42 */     this.theAchievement = achievementIn;
/*  43 */     this.permanentNotification = true;
/*     */   }
/*     */   
/*     */   private void updateAchievementWindowScale() {
/*  47 */     GlStateManager.viewport(0, 0, this.mc.displayWidth, this.mc.displayHeight);
/*  48 */     GlStateManager.matrixMode(5889);
/*  49 */     GlStateManager.loadIdentity();
/*  50 */     GlStateManager.matrixMode(5888);
/*  51 */     GlStateManager.loadIdentity();
/*  52 */     this.width = this.mc.displayWidth;
/*  53 */     this.height = this.mc.displayHeight;
/*  54 */     ScaledResolution scaledresolution = new ScaledResolution(this.mc);
/*  55 */     this.width = scaledresolution.getScaledWidth();
/*  56 */     this.height = scaledresolution.getScaledHeight();
/*  57 */     GlStateManager.clear(256);
/*  58 */     GlStateManager.matrixMode(5889);
/*  59 */     GlStateManager.loadIdentity();
/*  60 */     GlStateManager.ortho(0.0D, this.width, this.height, 0.0D, 1000.0D, 3000.0D);
/*  61 */     GlStateManager.matrixMode(5888);
/*  62 */     GlStateManager.loadIdentity();
/*  63 */     GlStateManager.translate(0.0F, 0.0F, -2000.0F);
/*     */   }
/*     */   
/*     */   public void updateAchievementWindow() {
/*  67 */     if (this.theAchievement != null && this.notificationTime != 0L && (Minecraft.getMinecraft()).thePlayer != null) {
/*  68 */       double d0 = (Minecraft.getSystemTime() - this.notificationTime) / 3000.0D;
/*     */       
/*  70 */       if (!this.permanentNotification) {
/*  71 */         if (d0 < 0.0D || d0 > 1.0D) {
/*  72 */           this.notificationTime = 0L;
/*     */           return;
/*     */         } 
/*  75 */       } else if (d0 > 0.5D) {
/*  76 */         d0 = 0.5D;
/*     */       } 
/*     */       
/*  79 */       updateAchievementWindowScale();
/*  80 */       GlStateManager.disableDepth();
/*  81 */       GlStateManager.depthMask(false);
/*  82 */       double d1 = d0 * 2.0D;
/*     */       
/*  84 */       if (d1 > 1.0D) {
/*  85 */         d1 = 2.0D - d1;
/*     */       }
/*     */       
/*  88 */       d1 *= 4.0D;
/*  89 */       d1 = 1.0D - d1;
/*     */       
/*  91 */       if (d1 < 0.0D) {
/*  92 */         d1 = 0.0D;
/*     */       }
/*     */       
/*  95 */       d1 *= d1;
/*  96 */       d1 *= d1;
/*  97 */       int i = this.width - 160;
/*  98 */       int j = 0 - (int)(d1 * 36.0D);
/*  99 */       GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
/* 100 */       GlStateManager.enableTexture2D();
/* 101 */       this.mc.getTextureManager().bindTexture(achievementBg);
/* 102 */       GlStateManager.disableLighting();
/* 103 */       drawTexturedModalRect(i, j, 96, 202, 160, 32);
/*     */       
/* 105 */       if (this.permanentNotification) {
/* 106 */         this.mc.fontRendererObj.drawSplitString(this.achievementDescription, i + 30, j + 7, 120, -1);
/*     */       } else {
/* 108 */         this.mc.fontRendererObj.drawString(this.achievementTitle, i + 30, j + 7, -256);
/* 109 */         this.mc.fontRendererObj.drawString(this.achievementDescription, i + 30, j + 18, -1);
/*     */       } 
/*     */       
/* 112 */       RenderHelper.enableGUIStandardItemLighting();
/* 113 */       GlStateManager.disableLighting();
/* 114 */       GlStateManager.enableRescaleNormal();
/* 115 */       GlStateManager.enableColorMaterial();
/* 116 */       GlStateManager.enableLighting();
/* 117 */       this.renderItem.renderItemAndEffectIntoGUI(this.theAchievement.theItemStack, i + 8, j + 8);
/* 118 */       GlStateManager.disableLighting();
/* 119 */       GlStateManager.depthMask(true);
/* 120 */       GlStateManager.enableDepth();
/*     */     } 
/*     */   }
/*     */   
/*     */   public void clearAchievements() {
/* 125 */     this.theAchievement = null;
/* 126 */     this.notificationTime = 0L;
/*     */   }
/*     */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\minecraft\client\gui\achievement\GuiAchievement.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */