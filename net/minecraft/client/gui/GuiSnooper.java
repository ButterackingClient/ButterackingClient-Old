/*     */ package net.minecraft.client.gui;
/*     */ 
/*     */ import com.google.common.collect.Lists;
/*     */ import java.io.IOException;
/*     */ import java.util.Map;
/*     */ import java.util.TreeMap;
/*     */ import net.minecraft.client.resources.I18n;
/*     */ import net.minecraft.client.settings.GameSettings;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class GuiSnooper
/*     */   extends GuiScreen
/*     */ {
/*     */   private final GuiScreen field_146608_a;
/*     */   private final GameSettings game_settings_2;
/*  19 */   private final java.util.List<String> field_146604_g = Lists.newArrayList();
/*  20 */   private final java.util.List<String> field_146609_h = Lists.newArrayList();
/*     */   private String field_146610_i;
/*     */   private String[] field_146607_r;
/*     */   private List field_146606_s;
/*     */   private GuiButton field_146605_t;
/*     */   
/*     */   public GuiSnooper(GuiScreen p_i1061_1_, GameSettings p_i1061_2_) {
/*  27 */     this.field_146608_a = p_i1061_1_;
/*  28 */     this.game_settings_2 = p_i1061_2_;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void initGui() {
/*  36 */     this.field_146610_i = I18n.format("options.snooper.title", new Object[0]);
/*  37 */     String s = I18n.format("options.snooper.desc", new Object[0]);
/*  38 */     java.util.List<String> list = Lists.newArrayList();
/*     */     
/*  40 */     for (String s1 : this.fontRendererObj.listFormattedStringToWidth(s, width - 30)) {
/*  41 */       list.add(s1);
/*     */     }
/*     */     
/*  44 */     this.field_146607_r = list.<String>toArray(new String[list.size()]);
/*  45 */     this.field_146604_g.clear();
/*  46 */     this.field_146609_h.clear();
/*  47 */     this.buttonList.add(this.field_146605_t = new GuiButton(1, width / 2 - 152, height - 30, 150, 20, this.game_settings_2.getKeyBinding(GameSettings.Options.SNOOPER_ENABLED)));
/*  48 */     this.buttonList.add(new GuiButton(2, width / 2 + 2, height - 30, 150, 20, I18n.format("gui.done", new Object[0])));
/*  49 */     boolean flag = (this.mc.getIntegratedServer() != null && this.mc.getIntegratedServer().getPlayerUsageSnooper() != null);
/*     */     
/*  51 */     for (Map.Entry<String, String> entry : (new TreeMap<>(this.mc.getPlayerUsageSnooper().getCurrentStats())).entrySet()) {
/*  52 */       this.field_146604_g.add(String.valueOf(flag ? "C " : "") + (String)entry.getKey());
/*  53 */       this.field_146609_h.add(this.fontRendererObj.trimStringToWidth(entry.getValue(), width - 220));
/*     */     } 
/*     */     
/*  56 */     if (flag) {
/*  57 */       for (Map.Entry<String, String> entry1 : (new TreeMap<>(this.mc.getIntegratedServer().getPlayerUsageSnooper().getCurrentStats())).entrySet()) {
/*  58 */         this.field_146604_g.add("S " + (String)entry1.getKey());
/*  59 */         this.field_146609_h.add(this.fontRendererObj.trimStringToWidth(entry1.getValue(), width - 220));
/*     */       } 
/*     */     }
/*     */     
/*  63 */     this.field_146606_s = new List();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void handleMouseInput() throws IOException {
/*  70 */     super.handleMouseInput();
/*  71 */     this.field_146606_s.handleMouseInput();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void actionPerformed(GuiButton button) throws IOException {
/*  78 */     if (button.enabled) {
/*  79 */       if (button.id == 2) {
/*  80 */         this.game_settings_2.saveOptions();
/*  81 */         this.game_settings_2.saveOptions();
/*  82 */         this.mc.displayGuiScreen(this.field_146608_a);
/*     */       } 
/*     */       
/*  85 */       if (button.id == 1) {
/*  86 */         this.game_settings_2.setOptionValue(GameSettings.Options.SNOOPER_ENABLED, 1);
/*  87 */         this.field_146605_t.displayString = this.game_settings_2.getKeyBinding(GameSettings.Options.SNOOPER_ENABLED);
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void drawScreen(int mouseX, int mouseY, float partialTicks) {
/*  96 */     drawDefaultBackground();
/*  97 */     this.field_146606_s.drawScreen(mouseX, mouseY, partialTicks);
/*  98 */     drawCenteredString(this.fontRendererObj, this.field_146610_i, width / 2, 8, 16777215);
/*  99 */     int i = 22; byte b; int j;
/*     */     String[] arrayOfString;
/* 101 */     for (j = (arrayOfString = this.field_146607_r).length, b = 0; b < j; ) { String s = arrayOfString[b];
/* 102 */       drawCenteredString(this.fontRendererObj, s, width / 2, i, 8421504);
/* 103 */       i += this.fontRendererObj.FONT_HEIGHT;
/*     */       b++; }
/*     */     
/* 106 */     super.drawScreen(mouseX, mouseY, partialTicks);
/*     */   }
/*     */   
/*     */   class List extends GuiSlot {
/*     */     public List() {
/* 111 */       super(GuiSnooper.this.mc, GuiSnooper.width, GuiSnooper.height, 80, GuiSnooper.height - 40, GuiSnooper.this.fontRendererObj.FONT_HEIGHT + 1);
/*     */     }
/*     */     
/*     */     protected int getSize() {
/* 115 */       return GuiSnooper.this.field_146604_g.size();
/*     */     }
/*     */ 
/*     */     
/*     */     protected void elementClicked(int slotIndex, boolean isDoubleClick, int mouseX, int mouseY) {}
/*     */     
/*     */     protected boolean isSelected(int slotIndex) {
/* 122 */       return false;
/*     */     }
/*     */ 
/*     */     
/*     */     protected void drawBackground() {}
/*     */     
/*     */     protected void drawSlot(int entryID, int p_180791_2_, int p_180791_3_, int p_180791_4_, int mouseXIn, int mouseYIn) {
/* 129 */       GuiSnooper.this.fontRendererObj.drawString(GuiSnooper.this.field_146604_g.get(entryID), 10, p_180791_3_, 16777215);
/* 130 */       GuiSnooper.this.fontRendererObj.drawString(GuiSnooper.this.field_146609_h.get(entryID), 230, p_180791_3_, 16777215);
/*     */     }
/*     */     
/*     */     protected int getScrollBarX() {
/* 134 */       return this.width - 10;
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\minecraft\client\gui\GuiSnooper.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */