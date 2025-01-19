/*     */ package net.minecraft.client;
/*     */ 
/*     */ import net.minecraft.client.gui.Gui;
/*     */ import net.minecraft.client.gui.ScaledResolution;
/*     */ import net.minecraft.client.renderer.GlStateManager;
/*     */ import net.minecraft.client.renderer.OpenGlHelper;
/*     */ import net.minecraft.client.renderer.Tessellator;
/*     */ import net.minecraft.client.renderer.WorldRenderer;
/*     */ import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
/*     */ import net.minecraft.client.shader.Framebuffer;
/*     */ import net.minecraft.util.IProgressUpdate;
/*     */ import net.minecraft.util.MinecraftError;
/*     */ import net.optifine.CustomLoadingScreen;
/*     */ import net.optifine.CustomLoadingScreens;
/*     */ import net.optifine.reflect.Reflector;
/*     */ 
/*     */ public class LoadingScreenRenderer implements IProgressUpdate {
/*  18 */   private String message = "";
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private Minecraft mc;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  28 */   private String currentlyDisplayedText = "";
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  33 */   private long systemTime = Minecraft.getSystemTime();
/*     */   
/*     */   private boolean loadingSuccess;
/*     */   
/*     */   private ScaledResolution scaledResolution;
/*     */   
/*     */   private Framebuffer framebuffer;
/*     */ 
/*     */   
/*     */   public LoadingScreenRenderer(Minecraft mcIn) {
/*  43 */     this.mc = mcIn;
/*  44 */     this.scaledResolution = new ScaledResolution(mcIn);
/*  45 */     this.framebuffer = new Framebuffer(mcIn.displayWidth, mcIn.displayHeight, false);
/*  46 */     this.framebuffer.setFramebufferFilter(9728);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void resetProgressAndMessage(String message) {
/*  54 */     this.loadingSuccess = false;
/*  55 */     displayString(message);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void displaySavingString(String message) {
/*  62 */     this.loadingSuccess = true;
/*  63 */     displayString(message);
/*     */   }
/*     */   
/*     */   private void displayString(String message) {
/*  67 */     this.currentlyDisplayedText = message;
/*     */     
/*  69 */     if (!this.mc.running) {
/*  70 */       if (!this.loadingSuccess) {
/*  71 */         throw new MinecraftError();
/*     */       }
/*     */     } else {
/*  74 */       GlStateManager.clear(256);
/*  75 */       GlStateManager.matrixMode(5889);
/*  76 */       GlStateManager.loadIdentity();
/*     */       
/*  78 */       if (OpenGlHelper.isFramebufferEnabled()) {
/*  79 */         int i = this.scaledResolution.getScaleFactor();
/*  80 */         GlStateManager.ortho(0.0D, (this.scaledResolution.getScaledWidth() * i), (this.scaledResolution.getScaledHeight() * i), 0.0D, 100.0D, 300.0D);
/*     */       } else {
/*  82 */         ScaledResolution scaledresolution = new ScaledResolution(this.mc);
/*  83 */         GlStateManager.ortho(0.0D, scaledresolution.getScaledWidth_double(), scaledresolution.getScaledHeight_double(), 0.0D, 100.0D, 300.0D);
/*     */       } 
/*     */       
/*  86 */       GlStateManager.matrixMode(5888);
/*  87 */       GlStateManager.loadIdentity();
/*  88 */       GlStateManager.translate(0.0F, 0.0F, -200.0F);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void displayLoadingString(String message) {
/*  96 */     if (!this.mc.running) {
/*  97 */       if (!this.loadingSuccess) {
/*  98 */         throw new MinecraftError();
/*     */       }
/*     */     } else {
/* 101 */       this.systemTime = 0L;
/* 102 */       this.message = message;
/* 103 */       setLoadingProgress(-1);
/* 104 */       this.systemTime = 0L;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setLoadingProgress(int progress) {
/* 112 */     if (!this.mc.running) {
/* 113 */       if (!this.loadingSuccess) {
/* 114 */         throw new MinecraftError();
/*     */       }
/*     */     } else {
/* 117 */       long i = Minecraft.getSystemTime();
/*     */       
/* 119 */       if (i - this.systemTime >= 100L) {
/* 120 */         this.systemTime = i;
/* 121 */         ScaledResolution scaledresolution = new ScaledResolution(this.mc);
/* 122 */         int j = scaledresolution.getScaleFactor();
/* 123 */         int k = scaledresolution.getScaledWidth();
/* 124 */         int l = scaledresolution.getScaledHeight();
/*     */         
/* 126 */         if (OpenGlHelper.isFramebufferEnabled()) {
/* 127 */           this.framebuffer.framebufferClear();
/*     */         } else {
/* 129 */           GlStateManager.clear(256);
/*     */         } 
/*     */         
/* 132 */         this.framebuffer.bindFramebuffer(false);
/* 133 */         GlStateManager.matrixMode(5889);
/* 134 */         GlStateManager.loadIdentity();
/* 135 */         GlStateManager.ortho(0.0D, scaledresolution.getScaledWidth_double(), scaledresolution.getScaledHeight_double(), 0.0D, 100.0D, 300.0D);
/* 136 */         GlStateManager.matrixMode(5888);
/* 137 */         GlStateManager.loadIdentity();
/* 138 */         GlStateManager.translate(0.0F, 0.0F, -200.0F);
/*     */         
/* 140 */         if (!OpenGlHelper.isFramebufferEnabled()) {
/* 141 */           GlStateManager.clear(16640);
/*     */         }
/*     */         
/* 144 */         boolean flag = true;
/*     */         
/* 146 */         if (Reflector.FMLClientHandler_handleLoadingScreen.exists()) {
/* 147 */           Object object = Reflector.call(Reflector.FMLClientHandler_instance, new Object[0]);
/*     */           
/* 149 */           if (object != null) {
/* 150 */             flag = !Reflector.callBoolean(object, Reflector.FMLClientHandler_handleLoadingScreen, new Object[] { scaledresolution });
/*     */           }
/*     */         } 
/*     */         
/* 154 */         if (flag) {
/* 155 */           Tessellator tessellator = Tessellator.getInstance();
/* 156 */           WorldRenderer worldrenderer = tessellator.getWorldRenderer();
/* 157 */           CustomLoadingScreen customloadingscreen = CustomLoadingScreens.getCustomLoadingScreen();
/*     */           
/* 159 */           if (customloadingscreen != null) {
/* 160 */             customloadingscreen.drawBackground(scaledresolution.getScaledWidth(), scaledresolution.getScaledHeight());
/*     */           } else {
/* 162 */             this.mc.getTextureManager().bindTexture(Gui.optionsBackground);
/* 163 */             float f = 32.0F;
/* 164 */             worldrenderer.begin(7, DefaultVertexFormats.POSITION_TEX_COLOR);
/* 165 */             worldrenderer.pos(0.0D, l, 0.0D).tex(0.0D, (l / f)).color(64, 64, 64, 255).endVertex();
/* 166 */             worldrenderer.pos(k, l, 0.0D).tex((k / f), (l / f)).color(64, 64, 64, 255).endVertex();
/* 167 */             worldrenderer.pos(k, 0.0D, 0.0D).tex((k / f), 0.0D).color(64, 64, 64, 255).endVertex();
/* 168 */             worldrenderer.pos(0.0D, 0.0D, 0.0D).tex(0.0D, 0.0D).color(64, 64, 64, 255).endVertex();
/* 169 */             tessellator.draw();
/*     */           } 
/*     */           
/* 172 */           if (progress >= 0) {
/* 173 */             int l1 = 100;
/* 174 */             int i1 = 2;
/* 175 */             int j1 = k / 2 - l1 / 2;
/* 176 */             int k1 = l / 2 + 16;
/* 177 */             GlStateManager.disableTexture2D();
/* 178 */             worldrenderer.begin(7, DefaultVertexFormats.POSITION_COLOR);
/* 179 */             worldrenderer.pos(j1, k1, 0.0D).color(128, 128, 128, 255).endVertex();
/* 180 */             worldrenderer.pos(j1, (k1 + i1), 0.0D).color(128, 128, 128, 255).endVertex();
/* 181 */             worldrenderer.pos((j1 + l1), (k1 + i1), 0.0D).color(128, 128, 128, 255).endVertex();
/* 182 */             worldrenderer.pos((j1 + l1), k1, 0.0D).color(128, 128, 128, 255).endVertex();
/* 183 */             worldrenderer.pos(j1, k1, 0.0D).color(128, 255, 128, 255).endVertex();
/* 184 */             worldrenderer.pos(j1, (k1 + i1), 0.0D).color(128, 255, 128, 255).endVertex();
/* 185 */             worldrenderer.pos((j1 + progress), (k1 + i1), 0.0D).color(128, 255, 128, 255).endVertex();
/* 186 */             worldrenderer.pos((j1 + progress), k1, 0.0D).color(128, 255, 128, 255).endVertex();
/* 187 */             tessellator.draw();
/* 188 */             GlStateManager.enableTexture2D();
/*     */           } 
/*     */           
/* 191 */           GlStateManager.enableBlend();
/* 192 */           GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
/* 193 */           this.mc.fontRendererObj.drawStringWithShadow(this.currentlyDisplayedText, ((k - this.mc.fontRendererObj.getStringWidth(this.currentlyDisplayedText)) / 2), (l / 2 - 4 - 16), 16777215);
/* 194 */           this.mc.fontRendererObj.drawStringWithShadow(this.message, ((k - this.mc.fontRendererObj.getStringWidth(this.message)) / 2), (l / 2 - 4 + 8), 16777215);
/*     */         } 
/*     */         
/* 197 */         this.framebuffer.unbindFramebuffer();
/*     */         
/* 199 */         if (OpenGlHelper.isFramebufferEnabled()) {
/* 200 */           this.framebuffer.framebufferRender(k * j, l * j);
/*     */         }
/*     */         
/* 203 */         this.mc.updateDisplay();
/*     */         
/*     */         try {
/* 206 */           Thread.yield();
/* 207 */         } catch (Exception exception) {}
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   public void setDoneWorking() {}
/*     */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\minecraft\client\LoadingScreenRenderer.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */