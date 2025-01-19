/*     */ package net.optifine.shaders.gui;
/*     */ 
/*     */ import java.io.InputStream;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Properties;
/*     */ import net.minecraft.client.Minecraft;
/*     */ import net.minecraft.client.gui.GuiScreen;
/*     */ import net.minecraft.client.gui.GuiSlot;
/*     */ import net.minecraft.client.gui.GuiYesNo;
/*     */ import net.minecraft.client.gui.GuiYesNoCallback;
/*     */ import net.minecraft.client.resources.I18n;
/*     */ import net.minecraft.src.Config;
/*     */ import net.optifine.Lang;
/*     */ import net.optifine.shaders.IShaderPack;
/*     */ import net.optifine.shaders.Shaders;
/*     */ import net.optifine.util.ResUtils;
/*     */ 
/*     */ class GuiSlotShaders extends GuiSlot {
/*     */   private ArrayList shaderslist;
/*  20 */   private long lastClickedCached = 0L; private int selectedIndex;
/*     */   final GuiShaders shadersGui;
/*     */   
/*     */   public GuiSlotShaders(GuiShaders par1GuiShaders, int width, int height, int top, int bottom, int slotHeight) {
/*  24 */     super(par1GuiShaders.getMc(), width, height, top, bottom, slotHeight);
/*  25 */     this.shadersGui = par1GuiShaders;
/*  26 */     updateList();
/*  27 */     this.amountScrolled = 0.0F;
/*  28 */     int i = this.selectedIndex * slotHeight;
/*  29 */     int j = (bottom - top) / 2;
/*     */     
/*  31 */     if (i > j) {
/*  32 */       scrollBy(i - j);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getListWidth() {
/*  40 */     return this.width - 20;
/*     */   }
/*     */   
/*     */   public void updateList() {
/*  44 */     this.shaderslist = Shaders.listOfShaders();
/*  45 */     this.selectedIndex = 0;
/*  46 */     int i = 0;
/*     */     
/*  48 */     for (int j = this.shaderslist.size(); i < j; i++) {
/*  49 */       if (((String)this.shaderslist.get(i)).equals(Shaders.currentShaderName)) {
/*  50 */         this.selectedIndex = i;
/*     */         break;
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   protected int getSize() {
/*  57 */     return this.shaderslist.size();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void elementClicked(int index, boolean doubleClicked, int mouseX, int mouseY) {
/*  64 */     if (index != this.selectedIndex || this.lastClicked != this.lastClickedCached) {
/*  65 */       String s = this.shaderslist.get(index);
/*  66 */       IShaderPack ishaderpack = Shaders.getShaderPack(s);
/*     */       
/*  68 */       if (checkCompatible(ishaderpack, index)) {
/*  69 */         selectIndex(index);
/*     */       }
/*     */     } 
/*     */   }
/*     */   
/*     */   private void selectIndex(int index) {
/*  75 */     this.selectedIndex = index;
/*  76 */     this.lastClickedCached = this.lastClicked;
/*  77 */     Shaders.setShaderPack(this.shaderslist.get(index));
/*  78 */     Shaders.uninit();
/*  79 */     this.shadersGui.updateButtons();
/*     */   }
/*     */   
/*     */   private boolean checkCompatible(IShaderPack sp, final int index) {
/*  83 */     if (sp == null) {
/*  84 */       return true;
/*     */     }
/*  86 */     InputStream inputstream = sp.getResourceAsStream("/shaders/shaders.properties");
/*  87 */     Properties properties = ResUtils.readProperties(inputstream, "Shaders");
/*     */     
/*  89 */     if (properties == null) {
/*  90 */       return true;
/*     */     }
/*  92 */     String s = "version.1.8.9";
/*  93 */     String s1 = properties.getProperty(s);
/*     */     
/*  95 */     if (s1 == null) {
/*  96 */       return true;
/*     */     }
/*  98 */     s1 = s1.trim();
/*  99 */     String s2 = "M5";
/* 100 */     int i = Config.compareRelease(s2, s1);
/*     */     
/* 102 */     if (i >= 0) {
/* 103 */       return true;
/*     */     }
/* 105 */     String s3 = ("HD_U_" + s1).replace('_', ' ');
/* 106 */     String s4 = I18n.format("of.message.shaders.nv1", new Object[] { s3 });
/* 107 */     String s5 = I18n.format("of.message.shaders.nv2", new Object[0]);
/* 108 */     GuiYesNoCallback guiyesnocallback = new GuiYesNoCallback() {
/*     */         public void confirmClicked(boolean result, int id) {
/* 110 */           if (result) {
/* 111 */             GuiSlotShaders.this.selectIndex(index);
/*     */           }
/*     */           
/* 114 */           GuiSlotShaders.this.mc.displayGuiScreen((GuiScreen)GuiSlotShaders.this.shadersGui);
/*     */         }
/*     */       };
/* 117 */     GuiYesNo guiyesno = new GuiYesNo(guiyesnocallback, s4, s5, 0);
/* 118 */     this.mc.displayGuiScreen((GuiScreen)guiyesno);
/* 119 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected boolean isSelected(int index) {
/* 130 */     return (index == this.selectedIndex);
/*     */   }
/*     */   
/*     */   protected int getScrollBarX() {
/* 134 */     return this.width - 6;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected int getContentHeight() {
/* 141 */     return getSize() * 18;
/*     */   }
/*     */ 
/*     */   
/*     */   protected void drawBackground() {}
/*     */   
/*     */   protected void drawSlot(int index, int posX, int posY, int contentY, int mouseX, int mouseY) {
/* 148 */     String s = this.shaderslist.get(index);
/*     */     
/* 150 */     if (s.equals("OFF")) {
/* 151 */       s = Lang.get("of.options.shaders.packNone");
/* 152 */     } else if (s.equals("(internal)")) {
/* 153 */       s = Lang.get("of.options.shaders.packDefault");
/*     */     } 
/*     */     
/* 156 */     this.shadersGui.drawCenteredString(s, this.width / 2, posY + 1, 14737632);
/*     */   }
/*     */   
/*     */   public int getSelectedIndex() {
/* 160 */     return this.selectedIndex;
/*     */   }
/*     */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\optifine\shaders\gui\GuiSlotShaders.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */