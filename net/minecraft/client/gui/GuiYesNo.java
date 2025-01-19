/*     */ package net.minecraft.client.gui;
/*     */ 
/*     */ import com.google.common.collect.Lists;
/*     */ import java.io.IOException;
/*     */ import java.util.List;
/*     */ import net.minecraft.client.resources.I18n;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class GuiYesNo
/*     */   extends GuiScreen
/*     */ {
/*     */   protected GuiYesNoCallback parentScreen;
/*     */   protected String messageLine1;
/*     */   private String messageLine2;
/*  17 */   private final List<String> field_175298_s = Lists.newArrayList();
/*     */ 
/*     */   
/*     */   protected String confirmButtonText;
/*     */ 
/*     */   
/*     */   protected String cancelButtonText;
/*     */ 
/*     */   
/*     */   protected int parentButtonClickedId;
/*     */   
/*     */   private int ticksUntilEnable;
/*     */ 
/*     */   
/*     */   public GuiYesNo(GuiYesNoCallback p_i1082_1_, String p_i1082_2_, String p_i1082_3_, int p_i1082_4_) {
/*  32 */     this.parentScreen = p_i1082_1_;
/*  33 */     this.messageLine1 = p_i1082_2_;
/*  34 */     this.messageLine2 = p_i1082_3_;
/*  35 */     this.parentButtonClickedId = p_i1082_4_;
/*  36 */     this.confirmButtonText = I18n.format("gui.yes", new Object[0]);
/*  37 */     this.cancelButtonText = I18n.format("gui.no", new Object[0]);
/*     */   }
/*     */   
/*     */   public GuiYesNo(GuiYesNoCallback p_i1083_1_, String p_i1083_2_, String p_i1083_3_, String p_i1083_4_, String p_i1083_5_, int p_i1083_6_) {
/*  41 */     this.parentScreen = p_i1083_1_;
/*  42 */     this.messageLine1 = p_i1083_2_;
/*  43 */     this.messageLine2 = p_i1083_3_;
/*  44 */     this.confirmButtonText = p_i1083_4_;
/*  45 */     this.cancelButtonText = p_i1083_5_;
/*  46 */     this.parentButtonClickedId = p_i1083_6_;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void initGui() {
/*  54 */     this.buttonList.add(new GuiOptionButton(0, width / 2 - 155, height / 6 + 96, this.confirmButtonText));
/*  55 */     this.buttonList.add(new GuiOptionButton(1, width / 2 - 155 + 160, height / 6 + 96, this.cancelButtonText));
/*  56 */     this.field_175298_s.clear();
/*  57 */     this.field_175298_s.addAll(this.fontRendererObj.listFormattedStringToWidth(this.messageLine2, width - 50));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void actionPerformed(GuiButton button) throws IOException {
/*  64 */     this.parentScreen.confirmClicked((button.id == 0), this.parentButtonClickedId);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void drawScreen(int mouseX, int mouseY, float partialTicks) {
/*  71 */     drawDefaultBackground();
/*  72 */     drawCenteredString(this.fontRendererObj, this.messageLine1, width / 2, 70, 16777215);
/*  73 */     int i = 90;
/*     */     
/*  75 */     for (String s : this.field_175298_s) {
/*  76 */       drawCenteredString(this.fontRendererObj, s, width / 2, i, 16777215);
/*  77 */       i += this.fontRendererObj.FONT_HEIGHT;
/*     */     } 
/*     */     
/*  80 */     super.drawScreen(mouseX, mouseY, partialTicks);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setButtonDelay(int p_146350_1_) {
/*  87 */     this.ticksUntilEnable = p_146350_1_;
/*     */     
/*  89 */     for (GuiButton guibutton : this.buttonList) {
/*  90 */       guibutton.enabled = false;
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void updateScreen() {
/*  98 */     super.updateScreen();
/*     */     
/* 100 */     if (--this.ticksUntilEnable == 0)
/* 101 */       for (GuiButton guibutton : this.buttonList)
/* 102 */         guibutton.enabled = true;  
/*     */   }
/*     */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\minecraft\client\gui\GuiYesNo.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */