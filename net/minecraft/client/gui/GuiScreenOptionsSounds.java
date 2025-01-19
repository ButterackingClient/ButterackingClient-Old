/*     */ package net.minecraft.client.gui;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import net.minecraft.client.Minecraft;
/*     */ import net.minecraft.client.audio.ISound;
/*     */ import net.minecraft.client.audio.PositionedSoundRecord;
/*     */ import net.minecraft.client.audio.SoundCategory;
/*     */ import net.minecraft.client.audio.SoundHandler;
/*     */ import net.minecraft.client.renderer.GlStateManager;
/*     */ import net.minecraft.client.resources.I18n;
/*     */ import net.minecraft.client.settings.GameSettings;
/*     */ import net.minecraft.util.MathHelper;
/*     */ import net.minecraft.util.ResourceLocation;
/*     */ 
/*     */ 
/*     */ 
/*     */ public class GuiScreenOptionsSounds
/*     */   extends GuiScreen
/*     */ {
/*     */   private final GuiScreen field_146505_f;
/*     */   private final GameSettings game_settings_4;
/*  22 */   protected String field_146507_a = "Options";
/*     */   private String field_146508_h;
/*     */   
/*     */   public GuiScreenOptionsSounds(GuiScreen p_i45025_1_, GameSettings p_i45025_2_) {
/*  26 */     this.field_146505_f = p_i45025_1_;
/*  27 */     this.game_settings_4 = p_i45025_2_;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void initGui() {
/*  35 */     int i = 0;
/*  36 */     this.field_146507_a = I18n.format("options.sounds.title", new Object[0]);
/*  37 */     this.field_146508_h = I18n.format("options.off", new Object[0]);
/*  38 */     this.buttonList.add(new Button(SoundCategory.MASTER.getCategoryId(), width / 2 - 155 + i % 2 * 160, height / 6 - 12 + 24 * (i >> 1), SoundCategory.MASTER, true));
/*  39 */     i += 2; byte b; int j;
/*     */     SoundCategory[] arrayOfSoundCategory;
/*  41 */     for (j = (arrayOfSoundCategory = SoundCategory.values()).length, b = 0; b < j; ) { SoundCategory soundcategory = arrayOfSoundCategory[b];
/*  42 */       if (soundcategory != SoundCategory.MASTER) {
/*  43 */         this.buttonList.add(new Button(soundcategory.getCategoryId(), width / 2 - 155 + i % 2 * 160, height / 6 - 12 + 24 * (i >> 1), soundcategory, false));
/*  44 */         i++;
/*     */       } 
/*     */       b++; }
/*     */     
/*  48 */     this.buttonList.add(new GuiButton(200, width / 2 - 100, height / 6 + 168, I18n.format("gui.done", new Object[0])));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void actionPerformed(GuiButton button) throws IOException {
/*  55 */     if (button.enabled && 
/*  56 */       button.id == 200) {
/*  57 */       this.mc.gameSettings.saveOptions();
/*  58 */       this.mc.displayGuiScreen(this.field_146505_f);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void drawScreen(int mouseX, int mouseY, float partialTicks) {
/*  67 */     drawDefaultBackground();
/*  68 */     drawCenteredString(this.fontRendererObj, this.field_146507_a, width / 2, 15, 16777215);
/*  69 */     super.drawScreen(mouseX, mouseY, partialTicks);
/*     */   }
/*     */   
/*     */   protected String getSoundVolume(SoundCategory p_146504_1_) {
/*  73 */     float f = this.game_settings_4.getSoundLevel(p_146504_1_);
/*  74 */     return (f == 0.0F) ? this.field_146508_h : (String.valueOf((int)(f * 100.0F)) + "%");
/*     */   }
/*     */   
/*     */   class Button extends GuiButton {
/*     */     private final SoundCategory field_146153_r;
/*     */     private final String field_146152_s;
/*  80 */     public float field_146156_o = 1.0F;
/*     */     public boolean field_146155_p;
/*     */     
/*     */     public Button(int p_i45024_2_, int p_i45024_3_, int p_i45024_4_, SoundCategory p_i45024_5_, boolean p_i45024_6_) {
/*  84 */       super(p_i45024_2_, p_i45024_3_, p_i45024_4_, p_i45024_6_ ? 310 : 150, 20, "");
/*  85 */       this.field_146153_r = p_i45024_5_;
/*  86 */       this.field_146152_s = I18n.format("soundCategory." + p_i45024_5_.getCategoryName(), new Object[0]);
/*  87 */       this.displayString = String.valueOf(this.field_146152_s) + ": " + GuiScreenOptionsSounds.this.getSoundVolume(p_i45024_5_);
/*  88 */       this.field_146156_o = GuiScreenOptionsSounds.this.game_settings_4.getSoundLevel(p_i45024_5_);
/*     */     }
/*     */     
/*     */     protected int getHoverState(boolean mouseOver) {
/*  92 */       return 0;
/*     */     }
/*     */     
/*     */     protected void mouseDragged(Minecraft mc, int mouseX, int mouseY) {
/*  96 */       if (this.visible) {
/*  97 */         if (this.field_146155_p) {
/*  98 */           this.field_146156_o = (mouseX - this.xPosition + 4) / (this.width - 8);
/*  99 */           this.field_146156_o = MathHelper.clamp_float(this.field_146156_o, 0.0F, 1.0F);
/* 100 */           mc.gameSettings.setSoundLevel(this.field_146153_r, this.field_146156_o);
/* 101 */           mc.gameSettings.saveOptions();
/* 102 */           this.displayString = String.valueOf(this.field_146152_s) + ": " + GuiScreenOptionsSounds.this.getSoundVolume(this.field_146153_r);
/*     */         } 
/*     */         
/* 105 */         GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
/* 106 */         drawTexturedModalRect(this.xPosition + (int)(this.field_146156_o * (this.width - 8)), this.yPosition, 0, 66, 4, 20);
/* 107 */         drawTexturedModalRect(this.xPosition + (int)(this.field_146156_o * (this.width - 8)) + 4, this.yPosition, 196, 66, 4, 20);
/*     */       } 
/*     */     }
/*     */     
/*     */     public boolean mousePressed(Minecraft mc, int mouseX, int mouseY) {
/* 112 */       if (super.mousePressed(mc, mouseX, mouseY)) {
/* 113 */         this.field_146156_o = (mouseX - this.xPosition + 4) / (this.width - 8);
/* 114 */         this.field_146156_o = MathHelper.clamp_float(this.field_146156_o, 0.0F, 1.0F);
/* 115 */         mc.gameSettings.setSoundLevel(this.field_146153_r, this.field_146156_o);
/* 116 */         mc.gameSettings.saveOptions();
/* 117 */         this.displayString = String.valueOf(this.field_146152_s) + ": " + GuiScreenOptionsSounds.this.getSoundVolume(this.field_146153_r);
/* 118 */         this.field_146155_p = true;
/* 119 */         return true;
/*     */       } 
/* 121 */       return false;
/*     */     }
/*     */ 
/*     */     
/*     */     public void playPressSound(SoundHandler soundHandlerIn) {}
/*     */ 
/*     */     
/*     */     public void mouseReleased(int mouseX, int mouseY) {
/* 129 */       if (this.field_146155_p) {
/* 130 */         if (this.field_146153_r == SoundCategory.MASTER) {
/* 131 */           float f = 1.0F;
/*     */         } else {
/* 133 */           GuiScreenOptionsSounds.this.game_settings_4.getSoundLevel(this.field_146153_r);
/*     */         } 
/*     */         
/* 136 */         GuiScreenOptionsSounds.this.mc.getSoundHandler().playSound((ISound)PositionedSoundRecord.create(new ResourceLocation("gui.button.press"), 1.0F));
/*     */       } 
/*     */       
/* 139 */       this.field_146155_p = false;
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\minecraft\client\gui\GuiScreenOptionsSounds.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */