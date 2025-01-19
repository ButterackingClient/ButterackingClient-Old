/*     */ package client.hud;
/*     */ 
/*     */ import client.Client;
/*     */ import client.mod.options.BasicOptions;
/*     */ import java.awt.Color;
/*     */ import net.minecraft.client.Minecraft;
/*     */ import net.minecraft.client.gui.FontRenderer;
/*     */ import net.minecraft.client.renderer.GlStateManager;
/*     */ import net.minecraft.client.renderer.Tessellator;
/*     */ import net.minecraft.client.renderer.WorldRenderer;
/*     */ import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class HudMod
/*     */ {
/*  18 */   public static Minecraft mc = Minecraft.getMinecraft();
/*     */   public static FontRenderer fr;
/*     */   public String name;
/*     */   public boolean enabled;
/*     */   public DraggableComponent drag;
/*     */   public BasicOptions optionGui;
/*     */   public int x;
/*     */   public int y;
/*     */   
/*     */   public HudMod(String name, int x, int y, boolean enabled) {
/*  28 */     mc = Minecraft.getMinecraft();
/*  29 */     fr = mc.fontRendererObj;
/*  30 */     this.name = name;
/*     */     try {
/*  32 */       setEnabled(((Boolean)(Client.getInstance()).config.config.get(String.valueOf(String.valueOf(name.toLowerCase())) + " enabled")).booleanValue());
/*  33 */       this.x = ((Integer)(Client.getInstance()).config.config.get(String.valueOf(name.toLowerCase()) + " x")).intValue();
/*  34 */       this.y = ((Integer)(Client.getInstance()).config.config.get(String.valueOf(name.toLowerCase()) + " y")).intValue();
/*     */     }
/*  36 */     catch (NullPointerException e) {
/*  37 */       System.out.println("file of " + name + " is not created yet!");
/*  38 */       this.x = x;
/*  39 */       this.y = y;
/*  40 */       this.enabled = enabled;
/*     */     } 
/*  42 */     this.drag = new DraggableComponent(this.x, this.y, getWidth(), getHeight(), (new Color(0, 0, 0, 0)).getRGB());
/*     */   }
/*     */   
/*     */   public HudMod(String name, int x, int y, boolean enabled, BasicOptions optionGui) {
/*  46 */     mc = Minecraft.getMinecraft();
/*  47 */     fr = mc.fontRendererObj;
/*  48 */     this.name = name;
/*  49 */     this.optionGui = optionGui;
/*     */     try {
/*  51 */       setEnabled(((Boolean)(Client.getInstance()).config.config.get(String.valueOf(String.valueOf(name.toLowerCase())) + " enabled")).booleanValue());
/*  52 */       this.x = ((Integer)(Client.getInstance()).config.config.get(String.valueOf(name.toLowerCase()) + " x")).intValue();
/*  53 */       this.y = ((Integer)(Client.getInstance()).config.config.get(String.valueOf(name.toLowerCase()) + " y")).intValue();
/*     */     }
/*  55 */     catch (NullPointerException e) {
/*  56 */       System.out.println("file of " + name + " is not created yet!");
/*  57 */       this.x = x;
/*  58 */       this.y = y;
/*  59 */       this.enabled = enabled;
/*     */     } 
/*  61 */     this.drag = new DraggableComponent(this.x, this.y, getWidth(), getHeight(), (new Color(0, 0, 0, 0)).getRGB());
/*     */   }
/*     */   
/*     */   public int getWidth() {
/*  65 */     return 80;
/*     */   }
/*     */   
/*     */   public int getHeight() {
/*  69 */     return 9;
/*     */   }
/*     */ 
/*     */   
/*     */   public void draw() {}
/*     */   
/*     */   public void renderDummy(int mouseX, int mouseY) {
/*  76 */     this.drag.mouseClicked(mouseX, mouseY, 0);
/*     */   }
/*     */   
/*     */   public static void drawRect(int left, int top, int right, int bottom, int color) {
/*  80 */     if (left < right) {
/*  81 */       int i = left;
/*  82 */       left = right;
/*  83 */       right = i;
/*     */     } 
/*  85 */     if (top < bottom) {
/*  86 */       int j = top;
/*  87 */       top = bottom;
/*  88 */       bottom = j;
/*     */     } 
/*  90 */     float f3 = (color >> 24 & 0xFF) / 255.0F;
/*  91 */     float f4 = (color >> 16 & 0xFF) / 255.0F;
/*  92 */     float f5 = (color >> 8 & 0xFF) / 255.0F;
/*  93 */     float f6 = (color & 0xFF) / 255.0F;
/*  94 */     Tessellator tessellator = Tessellator.getInstance();
/*  95 */     WorldRenderer worldrenderer = tessellator.getWorldRenderer();
/*  96 */     GlStateManager.enableBlend();
/*  97 */     GlStateManager.disableTexture2D();
/*  98 */     GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
/*  99 */     GlStateManager.color(f4, f5, f6, f3);
/* 100 */     worldrenderer.begin(7, DefaultVertexFormats.POSITION);
/* 101 */     worldrenderer.pos(left, bottom, 0.0D).endVertex();
/* 102 */     worldrenderer.pos(right, bottom, 0.0D).endVertex();
/* 103 */     worldrenderer.pos(right, top, 0.0D).endVertex();
/* 104 */     worldrenderer.pos(left, top, 0.0D).endVertex();
/* 105 */     tessellator.draw();
/* 106 */     GlStateManager.enableTexture2D();
/* 107 */     GlStateManager.disableBlend();
/*     */   }
/*     */   
/*     */   public int x() {
/* 111 */     return this.drag.getxPosition();
/*     */   }
/*     */   
/*     */   public int y() {
/* 115 */     return this.drag.getyPosition();
/*     */   }
/*     */   
/*     */   public void setEnabled(boolean enabled) {
/* 119 */     this.enabled = enabled;
/*     */   }
/*     */   
/*     */   public void toggle() {
/* 123 */     setEnabled(!this.enabled);
/*     */   }
/*     */   
/*     */   public boolean isEnabled() {
/* 127 */     return this.enabled;
/*     */   }
/*     */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\client\hud\HudMod.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */