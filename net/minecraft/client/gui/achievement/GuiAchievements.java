/*     */ package net.minecraft.client.gui.achievement;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import java.util.Random;
/*     */ import net.minecraft.block.Block;
/*     */ import net.minecraft.client.Minecraft;
/*     */ import net.minecraft.client.gui.GuiButton;
/*     */ import net.minecraft.client.gui.GuiOptionButton;
/*     */ import net.minecraft.client.gui.GuiScreen;
/*     */ import net.minecraft.client.gui.IProgressMeter;
/*     */ import net.minecraft.client.renderer.GlStateManager;
/*     */ import net.minecraft.client.renderer.RenderHelper;
/*     */ import net.minecraft.client.renderer.texture.TextureAtlasSprite;
/*     */ import net.minecraft.client.renderer.texture.TextureMap;
/*     */ import net.minecraft.client.resources.I18n;
/*     */ import net.minecraft.init.Blocks;
/*     */ import net.minecraft.network.Packet;
/*     */ import net.minecraft.network.play.client.C16PacketClientStatus;
/*     */ import net.minecraft.stats.Achievement;
/*     */ import net.minecraft.stats.AchievementList;
/*     */ import net.minecraft.stats.StatFileWriter;
/*     */ import net.minecraft.util.ChatComponentTranslation;
/*     */ import net.minecraft.util.MathHelper;
/*     */ import net.minecraft.util.ResourceLocation;
/*     */ import org.lwjgl.input.Mouse;
/*     */ 
/*     */ public class GuiAchievements extends GuiScreen implements IProgressMeter {
/*  28 */   private static final int field_146572_y = AchievementList.minDisplayColumn * 24 - 112;
/*  29 */   private static final int field_146571_z = AchievementList.minDisplayRow * 24 - 112;
/*  30 */   private static final int field_146559_A = AchievementList.maxDisplayColumn * 24 - 77;
/*  31 */   private static final int field_146560_B = AchievementList.maxDisplayRow * 24 - 77;
/*  32 */   private static final ResourceLocation ACHIEVEMENT_BACKGROUND = new ResourceLocation("textures/gui/achievement/achievement_background.png");
/*     */   protected GuiScreen parentScreen;
/*  34 */   protected int field_146555_f = 256;
/*  35 */   protected int field_146557_g = 202;
/*     */   protected int field_146563_h;
/*     */   protected int field_146564_i;
/*  38 */   protected float field_146570_r = 1.0F;
/*     */   protected double field_146569_s;
/*     */   protected double field_146568_t;
/*     */   protected double field_146567_u;
/*     */   protected double field_146566_v;
/*     */   protected double field_146565_w;
/*     */   protected double field_146573_x;
/*     */   private int field_146554_D;
/*     */   private StatFileWriter statFileWriter;
/*     */   private boolean loadingAchievements = true;
/*     */   
/*     */   public GuiAchievements(GuiScreen parentScreenIn, StatFileWriter statFileWriterIn) {
/*  50 */     this.parentScreen = parentScreenIn;
/*  51 */     this.statFileWriter = statFileWriterIn;
/*  52 */     int i = 141;
/*  53 */     int j = 141;
/*  54 */     this.field_146569_s = this.field_146567_u = this.field_146565_w = (AchievementList.openInventory.displayColumn * 24 - i / 2 - 12);
/*  55 */     this.field_146568_t = this.field_146566_v = this.field_146573_x = (AchievementList.openInventory.displayRow * 24 - j / 2);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void initGui() {
/*  63 */     this.mc.getNetHandler().addToSendQueue((Packet)new C16PacketClientStatus(C16PacketClientStatus.EnumState.REQUEST_STATS));
/*  64 */     this.buttonList.clear();
/*  65 */     this.buttonList.add(new GuiOptionButton(1, width / 2 + 24, height / 2 + 74, 80, 20, I18n.format("gui.done", new Object[0])));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void actionPerformed(GuiButton button) throws IOException {
/*  72 */     if (!this.loadingAchievements && 
/*  73 */       button.id == 1) {
/*  74 */       this.mc.displayGuiScreen(this.parentScreen);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void keyTyped(char typedChar, int keyCode) throws IOException {
/*  84 */     if (keyCode == this.mc.gameSettings.keyBindUseItem.getKeyCode()) {
/*  85 */       this.mc.displayGuiScreen(null);
/*  86 */       this.mc.setIngameFocus();
/*     */     } else {
/*  88 */       super.keyTyped(typedChar, keyCode);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void drawScreen(int mouseX, int mouseY, float partialTicks) {
/*  96 */     if (this.loadingAchievements) {
/*  97 */       drawDefaultBackground();
/*  98 */       drawCenteredString(this.fontRendererObj, I18n.format("multiplayer.downloadingStats", new Object[0]), width / 2, height / 2, 16777215);
/*  99 */       drawCenteredString(this.fontRendererObj, lanSearchStates[(int)(Minecraft.getSystemTime() / 150L % lanSearchStates.length)], width / 2, height / 2 + this.fontRendererObj.FONT_HEIGHT * 2, 16777215);
/*     */     } else {
/* 101 */       if (Mouse.isButtonDown(0)) {
/* 102 */         int i = (width - this.field_146555_f) / 2;
/* 103 */         int j = (height - this.field_146557_g) / 2;
/* 104 */         int k = i + 8;
/* 105 */         int l = j + 17;
/*     */         
/* 107 */         if ((this.field_146554_D == 0 || this.field_146554_D == 1) && mouseX >= k && mouseX < k + 224 && mouseY >= l && mouseY < l + 155) {
/* 108 */           if (this.field_146554_D == 0) {
/* 109 */             this.field_146554_D = 1;
/*     */           } else {
/* 111 */             this.field_146567_u -= ((mouseX - this.field_146563_h) * this.field_146570_r);
/* 112 */             this.field_146566_v -= ((mouseY - this.field_146564_i) * this.field_146570_r);
/* 113 */             this.field_146565_w = this.field_146569_s = this.field_146567_u;
/* 114 */             this.field_146573_x = this.field_146568_t = this.field_146566_v;
/*     */           } 
/*     */           
/* 117 */           this.field_146563_h = mouseX;
/* 118 */           this.field_146564_i = mouseY;
/*     */         } 
/*     */       } else {
/* 121 */         this.field_146554_D = 0;
/*     */       } 
/*     */       
/* 124 */       int i1 = Mouse.getDWheel();
/* 125 */       float f3 = this.field_146570_r;
/*     */       
/* 127 */       if (i1 < 0) {
/* 128 */         this.field_146570_r += 0.25F;
/* 129 */       } else if (i1 > 0) {
/* 130 */         this.field_146570_r -= 0.25F;
/*     */       } 
/*     */       
/* 133 */       this.field_146570_r = MathHelper.clamp_float(this.field_146570_r, 1.0F, 2.0F);
/*     */       
/* 135 */       if (this.field_146570_r != f3) {
/* 136 */         float f5 = f3 - this.field_146570_r;
/* 137 */         float f4 = f3 * this.field_146555_f;
/* 138 */         float f = f3 * this.field_146557_g;
/* 139 */         float f1 = this.field_146570_r * this.field_146555_f;
/* 140 */         float f2 = this.field_146570_r * this.field_146557_g;
/* 141 */         this.field_146567_u -= ((f1 - f4) * 0.5F);
/* 142 */         this.field_146566_v -= ((f2 - f) * 0.5F);
/* 143 */         this.field_146565_w = this.field_146569_s = this.field_146567_u;
/* 144 */         this.field_146573_x = this.field_146568_t = this.field_146566_v;
/*     */       } 
/*     */       
/* 147 */       if (this.field_146565_w < field_146572_y) {
/* 148 */         this.field_146565_w = field_146572_y;
/*     */       }
/*     */       
/* 151 */       if (this.field_146573_x < field_146571_z) {
/* 152 */         this.field_146573_x = field_146571_z;
/*     */       }
/*     */       
/* 155 */       if (this.field_146565_w >= field_146559_A) {
/* 156 */         this.field_146565_w = (field_146559_A - 1);
/*     */       }
/*     */       
/* 159 */       if (this.field_146573_x >= field_146560_B) {
/* 160 */         this.field_146573_x = (field_146560_B - 1);
/*     */       }
/*     */       
/* 163 */       drawDefaultBackground();
/* 164 */       drawAchievementScreen(mouseX, mouseY, partialTicks);
/* 165 */       GlStateManager.disableLighting();
/* 166 */       GlStateManager.disableDepth();
/* 167 */       drawTitle();
/* 168 */       GlStateManager.enableLighting();
/* 169 */       GlStateManager.enableDepth();
/*     */     } 
/*     */   }
/*     */   
/*     */   public void doneLoading() {
/* 174 */     if (this.loadingAchievements) {
/* 175 */       this.loadingAchievements = false;
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void updateScreen() {
/* 183 */     if (!this.loadingAchievements) {
/* 184 */       this.field_146569_s = this.field_146567_u;
/* 185 */       this.field_146568_t = this.field_146566_v;
/* 186 */       double d0 = this.field_146565_w - this.field_146567_u;
/* 187 */       double d1 = this.field_146573_x - this.field_146566_v;
/*     */       
/* 189 */       if (d0 * d0 + d1 * d1 < 4.0D) {
/* 190 */         this.field_146567_u += d0;
/* 191 */         this.field_146566_v += d1;
/*     */       } else {
/* 193 */         this.field_146567_u += d0 * 0.85D;
/* 194 */         this.field_146566_v += d1 * 0.85D;
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   protected void drawTitle() {
/* 200 */     int i = (width - this.field_146555_f) / 2;
/* 201 */     int j = (height - this.field_146557_g) / 2;
/* 202 */     this.fontRendererObj.drawString(I18n.format("gui.achievements", new Object[0]), i + 15, j + 5, 4210752);
/*     */   }
/*     */   
/*     */   protected void drawAchievementScreen(int p_146552_1_, int p_146552_2_, float p_146552_3_) {
/* 206 */     int i = MathHelper.floor_double(this.field_146569_s + (this.field_146567_u - this.field_146569_s) * p_146552_3_);
/* 207 */     int j = MathHelper.floor_double(this.field_146568_t + (this.field_146566_v - this.field_146568_t) * p_146552_3_);
/*     */     
/* 209 */     if (i < field_146572_y) {
/* 210 */       i = field_146572_y;
/*     */     }
/*     */     
/* 213 */     if (j < field_146571_z) {
/* 214 */       j = field_146571_z;
/*     */     }
/*     */     
/* 217 */     if (i >= field_146559_A) {
/* 218 */       i = field_146559_A - 1;
/*     */     }
/*     */     
/* 221 */     if (j >= field_146560_B) {
/* 222 */       j = field_146560_B - 1;
/*     */     }
/*     */     
/* 225 */     int k = (width - this.field_146555_f) / 2;
/* 226 */     int l = (height - this.field_146557_g) / 2;
/* 227 */     int i1 = k + 16;
/* 228 */     int j1 = l + 17;
/* 229 */     this.zLevel = 0.0F;
/* 230 */     GlStateManager.depthFunc(518);
/* 231 */     GlStateManager.pushMatrix();
/* 232 */     GlStateManager.translate(i1, j1, -200.0F);
/* 233 */     GlStateManager.scale(1.0F / this.field_146570_r, 1.0F / this.field_146570_r, 0.0F);
/* 234 */     GlStateManager.enableTexture2D();
/* 235 */     GlStateManager.disableLighting();
/* 236 */     GlStateManager.enableRescaleNormal();
/* 237 */     GlStateManager.enableColorMaterial();
/* 238 */     int k1 = i + 288 >> 4;
/* 239 */     int l1 = j + 288 >> 4;
/* 240 */     int i2 = (i + 288) % 16;
/* 241 */     int j2 = (j + 288) % 16;
/* 242 */     int k2 = 4;
/* 243 */     int l2 = 8;
/* 244 */     int i3 = 10;
/* 245 */     int j3 = 22;
/* 246 */     int k3 = 37;
/* 247 */     Random random = new Random();
/* 248 */     float f = 16.0F / this.field_146570_r;
/* 249 */     float f1 = 16.0F / this.field_146570_r;
/*     */     
/* 251 */     for (int l3 = 0; l3 * f - j2 < 155.0F; l3++) {
/* 252 */       float f2 = 0.6F - (l1 + l3) / 25.0F * 0.3F;
/* 253 */       GlStateManager.color(f2, f2, f2, 1.0F);
/*     */       
/* 255 */       for (int i4 = 0; i4 * f1 - i2 < 224.0F; i4++) {
/* 256 */         random.setSeed((this.mc.getSession().getPlayerID().hashCode() + k1 + i4 + (l1 + l3) * 16));
/* 257 */         int j4 = random.nextInt(1 + l1 + l3) + (l1 + l3) / 2;
/* 258 */         TextureAtlasSprite textureatlassprite = func_175371_a((Block)Blocks.sand);
/*     */         
/* 260 */         if (j4 <= 37 && l1 + l3 != 35) {
/* 261 */           if (j4 == 22) {
/* 262 */             if (random.nextInt(2) == 0) {
/* 263 */               textureatlassprite = func_175371_a(Blocks.diamond_ore);
/*     */             } else {
/* 265 */               textureatlassprite = func_175371_a(Blocks.redstone_ore);
/*     */             } 
/* 267 */           } else if (j4 == 10) {
/* 268 */             textureatlassprite = func_175371_a(Blocks.iron_ore);
/* 269 */           } else if (j4 == 8) {
/* 270 */             textureatlassprite = func_175371_a(Blocks.coal_ore);
/* 271 */           } else if (j4 > 4) {
/* 272 */             textureatlassprite = func_175371_a(Blocks.stone);
/* 273 */           } else if (j4 > 0) {
/* 274 */             textureatlassprite = func_175371_a(Blocks.dirt);
/*     */           } 
/*     */         } else {
/* 277 */           Block block = Blocks.bedrock;
/* 278 */           textureatlassprite = func_175371_a(block);
/*     */         } 
/*     */         
/* 281 */         this.mc.getTextureManager().bindTexture(TextureMap.locationBlocksTexture);
/* 282 */         drawTexturedModalRect(i4 * 16 - i2, l3 * 16 - j2, textureatlassprite, 16, 16);
/*     */       } 
/*     */     } 
/*     */     
/* 286 */     GlStateManager.enableDepth();
/* 287 */     GlStateManager.depthFunc(515);
/* 288 */     this.mc.getTextureManager().bindTexture(ACHIEVEMENT_BACKGROUND);
/*     */     
/* 290 */     for (int j5 = 0; j5 < AchievementList.achievementList.size(); j5++) {
/* 291 */       Achievement achievement1 = AchievementList.achievementList.get(j5);
/*     */       
/* 293 */       if (achievement1.parentAchievement != null) {
/* 294 */         int k5 = achievement1.displayColumn * 24 - i + 11;
/* 295 */         int l5 = achievement1.displayRow * 24 - j + 11;
/* 296 */         int j6 = achievement1.parentAchievement.displayColumn * 24 - i + 11;
/* 297 */         int k6 = achievement1.parentAchievement.displayRow * 24 - j + 11;
/* 298 */         boolean flag = this.statFileWriter.hasAchievementUnlocked(achievement1);
/* 299 */         boolean flag1 = this.statFileWriter.canUnlockAchievement(achievement1);
/* 300 */         int k4 = this.statFileWriter.func_150874_c(achievement1);
/*     */         
/* 302 */         if (k4 <= 4) {
/* 303 */           int l4 = -16777216;
/*     */           
/* 305 */           if (flag) {
/* 306 */             l4 = -6250336;
/* 307 */           } else if (flag1) {
/* 308 */             l4 = -16711936;
/*     */           } 
/*     */           
/* 311 */           drawHorizontalLine(k5, j6, l5, l4);
/* 312 */           drawVerticalLine(j6, l5, k6, l4);
/*     */           
/* 314 */           if (k5 > j6) {
/* 315 */             drawTexturedModalRect(k5 - 11 - 7, l5 - 5, 114, 234, 7, 11);
/* 316 */           } else if (k5 < j6) {
/* 317 */             drawTexturedModalRect(k5 + 11, l5 - 5, 107, 234, 7, 11);
/* 318 */           } else if (l5 > k6) {
/* 319 */             drawTexturedModalRect(k5 - 5, l5 - 11 - 7, 96, 234, 11, 7);
/* 320 */           } else if (l5 < k6) {
/* 321 */             drawTexturedModalRect(k5 - 5, l5 + 11, 96, 241, 11, 7);
/*     */           } 
/*     */         } 
/*     */       } 
/*     */     } 
/*     */     
/* 327 */     Achievement achievement = null;
/* 328 */     float f3 = (p_146552_1_ - i1) * this.field_146570_r;
/* 329 */     float f4 = (p_146552_2_ - j1) * this.field_146570_r;
/* 330 */     RenderHelper.enableGUIStandardItemLighting();
/* 331 */     GlStateManager.disableLighting();
/* 332 */     GlStateManager.enableRescaleNormal();
/* 333 */     GlStateManager.enableColorMaterial();
/*     */     
/* 335 */     for (int i6 = 0; i6 < AchievementList.achievementList.size(); i6++) {
/* 336 */       Achievement achievement2 = AchievementList.achievementList.get(i6);
/* 337 */       int l6 = achievement2.displayColumn * 24 - i;
/* 338 */       int j7 = achievement2.displayRow * 24 - j;
/*     */       
/* 340 */       if (l6 >= -24 && j7 >= -24 && l6 <= 224.0F * this.field_146570_r && j7 <= 155.0F * this.field_146570_r) {
/* 341 */         int l7 = this.statFileWriter.func_150874_c(achievement2);
/*     */         
/* 343 */         if (this.statFileWriter.hasAchievementUnlocked(achievement2)) {
/* 344 */           float f5 = 0.75F;
/* 345 */           GlStateManager.color(f5, f5, f5, 1.0F);
/* 346 */         } else if (this.statFileWriter.canUnlockAchievement(achievement2)) {
/* 347 */           float f6 = 1.0F;
/* 348 */           GlStateManager.color(f6, f6, f6, 1.0F);
/* 349 */         } else if (l7 < 3) {
/* 350 */           float f7 = 0.3F;
/* 351 */           GlStateManager.color(f7, f7, f7, 1.0F);
/* 352 */         } else if (l7 == 3) {
/* 353 */           float f8 = 0.2F;
/* 354 */           GlStateManager.color(f8, f8, f8, 1.0F);
/*     */         } else {
/* 356 */           if (l7 != 4) {
/*     */             continue;
/*     */           }
/*     */           
/* 360 */           float f9 = 0.1F;
/* 361 */           GlStateManager.color(f9, f9, f9, 1.0F);
/*     */         } 
/*     */         
/* 364 */         this.mc.getTextureManager().bindTexture(ACHIEVEMENT_BACKGROUND);
/*     */         
/* 366 */         if (achievement2.getSpecial()) {
/* 367 */           drawTexturedModalRect(l6 - 2, j7 - 2, 26, 202, 26, 26);
/*     */         } else {
/* 369 */           drawTexturedModalRect(l6 - 2, j7 - 2, 0, 202, 26, 26);
/*     */         } 
/*     */         
/* 372 */         if (!this.statFileWriter.canUnlockAchievement(achievement2)) {
/* 373 */           float f10 = 0.1F;
/* 374 */           GlStateManager.color(f10, f10, f10, 1.0F);
/* 375 */           this.itemRender.isNotRenderingEffectsInGUI(false);
/*     */         } 
/*     */         
/* 378 */         GlStateManager.enableLighting();
/* 379 */         GlStateManager.enableCull();
/* 380 */         this.itemRender.renderItemAndEffectIntoGUI(achievement2.theItemStack, l6 + 3, j7 + 3);
/* 381 */         GlStateManager.blendFunc(770, 771);
/* 382 */         GlStateManager.disableLighting();
/*     */         
/* 384 */         if (!this.statFileWriter.canUnlockAchievement(achievement2)) {
/* 385 */           this.itemRender.isNotRenderingEffectsInGUI(true);
/*     */         }
/*     */         
/* 388 */         GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
/*     */         
/* 390 */         if (f3 >= l6 && f3 <= (l6 + 22) && f4 >= j7 && f4 <= (j7 + 22)) {
/* 391 */           achievement = achievement2;
/*     */         }
/*     */       } 
/*     */       continue;
/*     */     } 
/* 396 */     GlStateManager.disableDepth();
/* 397 */     GlStateManager.enableBlend();
/* 398 */     GlStateManager.popMatrix();
/* 399 */     GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
/* 400 */     this.mc.getTextureManager().bindTexture(ACHIEVEMENT_BACKGROUND);
/* 401 */     drawTexturedModalRect(k, l, 0, 0, this.field_146555_f, this.field_146557_g);
/* 402 */     this.zLevel = 0.0F;
/* 403 */     GlStateManager.depthFunc(515);
/* 404 */     GlStateManager.disableDepth();
/* 405 */     GlStateManager.enableTexture2D();
/* 406 */     super.drawScreen(p_146552_1_, p_146552_2_, p_146552_3_);
/*     */     
/* 408 */     if (achievement != null) {
/* 409 */       String s = achievement.getStatName().getUnformattedText();
/* 410 */       String s1 = achievement.getDescription();
/* 411 */       int i7 = p_146552_1_ + 12;
/* 412 */       int k7 = p_146552_2_ - 4;
/* 413 */       int i8 = this.statFileWriter.func_150874_c(achievement);
/*     */       
/* 415 */       if (this.statFileWriter.canUnlockAchievement(achievement)) {
/* 416 */         int j8 = Math.max(this.fontRendererObj.getStringWidth(s), 120);
/* 417 */         int i9 = this.fontRendererObj.splitStringWidth(s1, j8);
/*     */         
/* 419 */         if (this.statFileWriter.hasAchievementUnlocked(achievement)) {
/* 420 */           i9 += 12;
/*     */         }
/*     */         
/* 423 */         drawGradientRect(i7 - 3, k7 - 3, i7 + j8 + 3, k7 + i9 + 3 + 12, -1073741824, -1073741824);
/* 424 */         this.fontRendererObj.drawSplitString(s1, i7, k7 + 12, j8, -6250336);
/*     */         
/* 426 */         if (this.statFileWriter.hasAchievementUnlocked(achievement)) {
/* 427 */           this.fontRendererObj.drawStringWithShadow(I18n.format("achievement.taken", new Object[0]), i7, (k7 + i9 + 4), -7302913);
/*     */         }
/* 429 */       } else if (i8 == 3) {
/* 430 */         s = I18n.format("achievement.unknown", new Object[0]);
/* 431 */         int k8 = Math.max(this.fontRendererObj.getStringWidth(s), 120);
/* 432 */         String s2 = (new ChatComponentTranslation("achievement.requires", new Object[] { achievement.parentAchievement.getStatName() })).getUnformattedText();
/* 433 */         int i5 = this.fontRendererObj.splitStringWidth(s2, k8);
/* 434 */         drawGradientRect(i7 - 3, k7 - 3, i7 + k8 + 3, k7 + i5 + 12 + 3, -1073741824, -1073741824);
/* 435 */         this.fontRendererObj.drawSplitString(s2, i7, k7 + 12, k8, -9416624);
/* 436 */       } else if (i8 < 3) {
/* 437 */         int l8 = Math.max(this.fontRendererObj.getStringWidth(s), 120);
/* 438 */         String s3 = (new ChatComponentTranslation("achievement.requires", new Object[] { achievement.parentAchievement.getStatName() })).getUnformattedText();
/* 439 */         int j9 = this.fontRendererObj.splitStringWidth(s3, l8);
/* 440 */         drawGradientRect(i7 - 3, k7 - 3, i7 + l8 + 3, k7 + j9 + 12 + 3, -1073741824, -1073741824);
/* 441 */         this.fontRendererObj.drawSplitString(s3, i7, k7 + 12, l8, -9416624);
/*     */       } else {
/* 443 */         s = null;
/*     */       } 
/*     */       
/* 446 */       if (s != null) {
/* 447 */         this.fontRendererObj.drawStringWithShadow(s, i7, k7, this.statFileWriter.canUnlockAchievement(achievement) ? (achievement.getSpecial() ? -128 : -1) : (achievement.getSpecial() ? -8355776 : -8355712));
/*     */       }
/*     */     } 
/*     */     
/* 451 */     GlStateManager.enableDepth();
/* 452 */     GlStateManager.enableLighting();
/* 453 */     RenderHelper.disableStandardItemLighting();
/*     */   }
/*     */   
/*     */   private TextureAtlasSprite func_175371_a(Block p_175371_1_) {
/* 457 */     return Minecraft.getMinecraft().getBlockRendererDispatcher().getBlockModelShapes().getTexture(p_175371_1_.getDefaultState());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean doesGuiPauseGame() {
/* 464 */     return !this.loadingAchievements;
/*     */   }
/*     */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\minecraft\client\gui\achievement\GuiAchievements.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */