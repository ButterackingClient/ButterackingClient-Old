/*     */ package net.minecraft.client.gui;
/*     */ 
/*     */ import net.minecraft.client.Minecraft;
/*     */ import net.minecraft.client.gui.spectator.ISpectatorMenuObject;
/*     */ import net.minecraft.client.gui.spectator.ISpectatorMenuRecipient;
/*     */ import net.minecraft.client.gui.spectator.SpectatorMenu;
/*     */ import net.minecraft.client.gui.spectator.categories.SpectatorDetails;
/*     */ import net.minecraft.client.renderer.GlStateManager;
/*     */ import net.minecraft.client.renderer.RenderHelper;
/*     */ import net.minecraft.client.settings.GameSettings;
/*     */ import net.minecraft.util.MathHelper;
/*     */ import net.minecraft.util.ResourceLocation;
/*     */ 
/*     */ public class GuiSpectator extends Gui implements ISpectatorMenuRecipient {
/*  15 */   private static final ResourceLocation field_175267_f = new ResourceLocation("textures/gui/widgets.png");
/*  16 */   public static final ResourceLocation field_175269_a = new ResourceLocation("textures/gui/spectator_widgets.png");
/*     */   private final Minecraft field_175268_g;
/*     */   private long field_175270_h;
/*     */   private SpectatorMenu field_175271_i;
/*     */   
/*     */   public GuiSpectator(Minecraft mcIn) {
/*  22 */     this.field_175268_g = mcIn;
/*     */   }
/*     */   
/*     */   public void func_175260_a(int p_175260_1_) {
/*  26 */     this.field_175270_h = Minecraft.getSystemTime();
/*     */     
/*  28 */     if (this.field_175271_i != null) {
/*  29 */       this.field_175271_i.func_178644_b(p_175260_1_);
/*     */     } else {
/*  31 */       this.field_175271_i = new SpectatorMenu(this);
/*     */     } 
/*     */   }
/*     */   
/*     */   private float func_175265_c() {
/*  36 */     long i = this.field_175270_h - Minecraft.getSystemTime() + 5000L;
/*  37 */     return MathHelper.clamp_float((float)i / 2000.0F, 0.0F, 1.0F);
/*     */   }
/*     */   
/*     */   public void renderTooltip(ScaledResolution p_175264_1_, float p_175264_2_) {
/*  41 */     if (this.field_175271_i != null) {
/*  42 */       float f = func_175265_c();
/*     */       
/*  44 */       if (f <= 0.0F) {
/*  45 */         this.field_175271_i.func_178641_d();
/*     */       } else {
/*  47 */         int i = p_175264_1_.getScaledWidth() / 2;
/*  48 */         float f1 = this.zLevel;
/*  49 */         this.zLevel = -90.0F;
/*  50 */         float f2 = p_175264_1_.getScaledHeight() - 22.0F * f;
/*  51 */         SpectatorDetails spectatordetails = this.field_175271_i.func_178646_f();
/*  52 */         func_175258_a(p_175264_1_, f, i, f2, spectatordetails);
/*  53 */         this.zLevel = f1;
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   protected void func_175258_a(ScaledResolution p_175258_1_, float p_175258_2_, int p_175258_3_, float p_175258_4_, SpectatorDetails p_175258_5_) {
/*  59 */     GlStateManager.enableRescaleNormal();
/*  60 */     GlStateManager.enableBlend();
/*  61 */     GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
/*  62 */     GlStateManager.color(1.0F, 1.0F, 1.0F, p_175258_2_);
/*  63 */     this.field_175268_g.getTextureManager().bindTexture(field_175267_f);
/*  64 */     drawTexturedModalRect((p_175258_3_ - 91), p_175258_4_, 0, 0, 182, 22);
/*     */     
/*  66 */     if (p_175258_5_.func_178681_b() >= 0) {
/*  67 */       drawTexturedModalRect((p_175258_3_ - 91 - 1 + p_175258_5_.func_178681_b() * 20), p_175258_4_ - 1.0F, 0, 22, 24, 22);
/*     */     }
/*     */     
/*  70 */     RenderHelper.enableGUIStandardItemLighting();
/*     */     
/*  72 */     for (int i = 0; i < 9; i++) {
/*  73 */       func_175266_a(i, p_175258_1_.getScaledWidth() / 2 - 90 + i * 20 + 2, p_175258_4_ + 3.0F, p_175258_2_, p_175258_5_.func_178680_a(i));
/*     */     }
/*     */     
/*  76 */     RenderHelper.disableStandardItemLighting();
/*  77 */     GlStateManager.disableRescaleNormal();
/*  78 */     GlStateManager.disableBlend();
/*     */   }
/*     */   
/*     */   private void func_175266_a(int p_175266_1_, int p_175266_2_, float p_175266_3_, float p_175266_4_, ISpectatorMenuObject p_175266_5_) {
/*  82 */     this.field_175268_g.getTextureManager().bindTexture(field_175269_a);
/*     */     
/*  84 */     if (p_175266_5_ != SpectatorMenu.field_178657_a) {
/*  85 */       int i = (int)(p_175266_4_ * 255.0F);
/*  86 */       GlStateManager.pushMatrix();
/*  87 */       GlStateManager.translate(p_175266_2_, p_175266_3_, 0.0F);
/*  88 */       float f = p_175266_5_.func_178662_A_() ? 1.0F : 0.25F;
/*  89 */       GlStateManager.color(f, f, f, p_175266_4_);
/*  90 */       p_175266_5_.func_178663_a(f, i);
/*  91 */       GlStateManager.popMatrix();
/*  92 */       String s = String.valueOf(GameSettings.getKeyDisplayString(this.field_175268_g.gameSettings.keyBindings[p_175266_1_].getKeyCode()));
/*     */       
/*  94 */       if (i > 3 && p_175266_5_.func_178662_A_()) {
/*  95 */         this.field_175268_g.fontRendererObj.drawStringWithShadow(s, (p_175266_2_ + 19 - 2 - this.field_175268_g.fontRendererObj.getStringWidth(s)), p_175266_3_ + 6.0F + 3.0F, 16777215 + (i << 24));
/*     */       }
/*     */     } 
/*     */   }
/*     */   
/*     */   public void renderSelectedItem(ScaledResolution p_175263_1_) {
/* 101 */     int i = (int)(func_175265_c() * 255.0F);
/*     */     
/* 103 */     if (i > 3 && this.field_175271_i != null) {
/* 104 */       ISpectatorMenuObject ispectatormenuobject = this.field_175271_i.func_178645_b();
/* 105 */       String s = (ispectatormenuobject != SpectatorMenu.field_178657_a) ? ispectatormenuobject.getSpectatorName().getFormattedText() : this.field_175271_i.func_178650_c().func_178670_b().getFormattedText();
/*     */       
/* 107 */       if (s != null) {
/* 108 */         int j = (p_175263_1_.getScaledWidth() - this.field_175268_g.fontRendererObj.getStringWidth(s)) / 2;
/* 109 */         int k = p_175263_1_.getScaledHeight() - 35;
/* 110 */         GlStateManager.pushMatrix();
/* 111 */         GlStateManager.enableBlend();
/* 112 */         GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
/* 113 */         this.field_175268_g.fontRendererObj.drawStringWithShadow(s, j, k, 16777215 + (i << 24));
/* 114 */         GlStateManager.disableBlend();
/* 115 */         GlStateManager.popMatrix();
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   public void func_175257_a(SpectatorMenu p_175257_1_) {
/* 121 */     this.field_175271_i = null;
/* 122 */     this.field_175270_h = 0L;
/*     */   }
/*     */   
/*     */   public boolean func_175262_a() {
/* 126 */     return (this.field_175271_i != null);
/*     */   }
/*     */ 
/*     */   
/*     */   public void func_175259_b(int p_175259_1_) {
/*     */     int i;
/* 132 */     for (i = this.field_175271_i.func_178648_e() + p_175259_1_; i >= 0 && i <= 8 && (this.field_175271_i.func_178643_a(i) == SpectatorMenu.field_178657_a || !this.field_175271_i.func_178643_a(i).func_178662_A_()); i += p_175259_1_);
/*     */ 
/*     */ 
/*     */     
/* 136 */     if (i >= 0 && i <= 8) {
/* 137 */       this.field_175271_i.func_178644_b(i);
/* 138 */       this.field_175270_h = Minecraft.getSystemTime();
/*     */     } 
/*     */   }
/*     */   
/*     */   public void func_175261_b() {
/* 143 */     this.field_175270_h = Minecraft.getSystemTime();
/*     */     
/* 145 */     if (func_175262_a()) {
/* 146 */       int i = this.field_175271_i.func_178648_e();
/*     */       
/* 148 */       if (i != -1) {
/* 149 */         this.field_175271_i.func_178644_b(i);
/*     */       }
/*     */     } else {
/* 152 */       this.field_175271_i = new SpectatorMenu(this);
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\minecraft\client\gui\GuiSpectator.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */