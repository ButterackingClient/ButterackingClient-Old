/*     */ package client.mod.impl;
/*     */ 
/*     */ import client.hud.HudMod;
/*     */ import java.util.Collection;
/*     */ import net.minecraft.client.Minecraft;
/*     */ import net.minecraft.client.renderer.GlStateManager;
/*     */ import net.minecraft.client.renderer.Tessellator;
/*     */ import net.minecraft.client.renderer.WorldRenderer;
/*     */ import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
/*     */ import net.minecraft.client.resources.I18n;
/*     */ import net.minecraft.potion.Potion;
/*     */ import net.minecraft.potion.PotionEffect;
/*     */ import net.minecraft.util.ResourceLocation;
/*     */ 
/*     */ public class ModPotionStatus
/*     */   extends HudMod {
/*     */   public ModPotionStatus() {
/*  18 */     super("PotionsStatus", 5, 95, false);
/*     */   }
/*     */   protected float zLevelFloat;
/*     */   
/*     */   public int getWidth() {
/*  23 */     return 100;
/*     */   }
/*     */ 
/*     */   
/*     */   public int getHeight() {
/*  28 */     return 100;
/*     */   }
/*     */ 
/*     */   
/*     */   public void draw() {
/*  33 */     int offsetX = 21;
/*  34 */     int offsetY = 14;
/*  35 */     int i = 80;
/*  36 */     int i2 = 16;
/*  37 */     Collection<PotionEffect> collection = (Minecraft.getMinecraft()).thePlayer.getActivePotionEffects();
/*  38 */     if (!collection.isEmpty()) {
/*  39 */       GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
/*  40 */       GlStateManager.disableLighting();
/*  41 */       int l = 33;
/*  42 */       if (collection.size() > 5) {
/*  43 */         l = 132 / (collection.size() - 1);
/*     */       }
/*  45 */       for (PotionEffect potioneffect : (Minecraft.getMinecraft()).thePlayer.getActivePotionEffects()) {
/*  46 */         Potion potion = Potion.potionTypes[potioneffect.getPotionID()];
/*  47 */         GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
/*  48 */         if (potion.hasStatusIcon()) {
/*  49 */           Minecraft.getMinecraft().getTextureManager().bindTexture(new ResourceLocation("textures/gui/container/inventory.png"));
/*  50 */           int i3 = potion.getStatusIconIndex();
/*  51 */           drawTexturedModalRect(x() + 21 - 20, y() + i2 - 14, 0 + i3 % 8 * 18, 198 + i3 / 8 * 18, 18, 18);
/*     */         } 
/*  53 */         String s1 = I18n.format(potion.getName(), new Object[0]);
/*  54 */         if (potioneffect.getAmplifier() == 1) {
/*  55 */           s1 = String.valueOf(s1) + " " + I18n.format("enchantment.level.2", new Object[0]);
/*  56 */         } else if (potioneffect.getAmplifier() == 2) {
/*  57 */           s1 = String.valueOf(s1) + " " + I18n.format("enchantment.level.3", new Object[0]);
/*  58 */         } else if (potioneffect.getAmplifier() == 3) {
/*  59 */           s1 = String.valueOf(s1) + " " + I18n.format("enchantment.level.4", new Object[0]);
/*     */         } 
/*  61 */         fr.drawString(s1, (x() + 21), (y() + i2 - 14), 16777215, true);
/*  62 */         String s2 = Potion.getDurationString(potioneffect);
/*  63 */         fr.drawString(s2, (x() + 21), (y() + i2 + 10 - 14), 8355711, true);
/*  64 */         i2 += l;
/*     */       } 
/*     */     } 
/*  67 */     super.draw();
/*     */   }
/*     */ 
/*     */   
/*     */   public void renderDummy(int mouseX, int mouseY) {
/*  72 */     int offsetX = 21;
/*  73 */     int offsetY = 14;
/*  74 */     int i = 80;
/*  75 */     int i2 = 16;
/*  76 */     PotionEffect[] potionEffects = { new PotionEffect(Potion.moveSpeed.id, 1200, 0), new PotionEffect(Potion.damageBoost.id, 1200, 0), new PotionEffect(Potion.fireResistance.id, 1200, 0) };
/*  77 */     int l = 33;
/*  78 */     if (potionEffects.length > 5) {
/*  79 */       l = 132 / (potionEffects.length - 1);
/*     */     }
/*     */     PotionEffect[] array;
/*  82 */     for (int length = (array = potionEffects).length, j = 0; j < length; j++) {
/*  83 */       PotionEffect potioneffect = array[j];
/*  84 */       Potion potion = Potion.potionTypes[potioneffect.getPotionID()];
/*  85 */       GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
/*  86 */       if (potion.hasStatusIcon()) {
/*  87 */         Minecraft.getMinecraft().getTextureManager().bindTexture(new ResourceLocation("textures/gui/container/inventory.png"));
/*  88 */         int i3 = potion.getStatusIconIndex();
/*  89 */         drawTexturedModalRect(x() + 21 - 20, y() + i2 - 14, 0 + i3 % 8 * 18, 198 + i3 / 8 * 18, 18, 18);
/*     */       } 
/*  91 */       String s1 = I18n.format(potion.getName(), new Object[0]);
/*  92 */       if (potioneffect.getAmplifier() == 1) {
/*  93 */         s1 = String.valueOf(s1) + " " + I18n.format("enchantment.level.2", new Object[0]);
/*  94 */       } else if (potioneffect.getAmplifier() == 2) {
/*  95 */         s1 = String.valueOf(s1) + " " + I18n.format("enchantment.level.3", new Object[0]);
/*  96 */       } else if (potioneffect.getAmplifier() == 3) {
/*  97 */         s1 = String.valueOf(s1) + " " + I18n.format("enchantment.level.4", new Object[0]);
/*     */       } 
/*  99 */       fr.drawString(s1, (x() + 21), (y() + i2 - 14), 16777215, true);
/* 100 */       String s2 = Potion.getDurationString(potioneffect);
/* 101 */       fr.drawString(s2, (x() + 21), (y() + i2 + 10 - 14), 8355711, true);
/* 102 */       i2 += l;
/*     */     } 
/* 104 */     super.renderDummy(mouseX, mouseY);
/*     */   }
/*     */   
/*     */   public void drawTexturedModalRect(int x, int y, int textureX, int textureY, int width, int height) {
/* 108 */     float f = 0.00390625F;
/* 109 */     float f2 = 0.00390625F;
/* 110 */     Tessellator tessellator = Tessellator.getInstance();
/* 111 */     WorldRenderer worldrenderer = tessellator.getWorldRenderer();
/* 112 */     worldrenderer.begin(7, DefaultVertexFormats.POSITION_TEX);
/* 113 */     worldrenderer.pos((x + 0), (y + height), this.zLevelFloat).tex(((textureX + 0) * 0.00390625F), ((textureY + height) * 0.00390625F)).endVertex();
/* 114 */     worldrenderer.pos((x + width), (y + height), this.zLevelFloat).tex(((textureX + width) * 0.00390625F), ((textureY + height) * 0.00390625F)).endVertex();
/* 115 */     worldrenderer.pos((x + width), (y + 0), this.zLevelFloat).tex(((textureX + width) * 0.00390625F), ((textureY + 0) * 0.00390625F)).endVertex();
/* 116 */     worldrenderer.pos((x + 0), (y + 0), this.zLevelFloat).tex(((textureX + 0) * 0.00390625F), ((textureY + 0) * 0.00390625F)).endVertex();
/* 117 */     tessellator.draw();
/*     */   }
/*     */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\client\mod\impl\ModPotionStatus.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */