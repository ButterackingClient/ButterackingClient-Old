/*     */ package net.minecraft.client.gui;
/*     */ 
/*     */ import com.google.common.collect.Lists;
/*     */ import java.io.IOException;
/*     */ import java.util.Collections;
/*     */ import java.util.List;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraft.realms.RealmsButton;
/*     */ import net.minecraft.realms.RealmsScreen;
/*     */ 
/*     */ public class GuiScreenRealmsProxy
/*     */   extends GuiScreen
/*     */ {
/*     */   private RealmsScreen field_154330_a;
/*     */   
/*     */   public GuiScreenRealmsProxy(RealmsScreen p_i1087_1_) {
/*  17 */     this.field_154330_a = p_i1087_1_;
/*  18 */     this.buttonList = Collections.synchronizedList(Lists.newArrayList());
/*     */   }
/*     */   
/*     */   public RealmsScreen func_154321_a() {
/*  22 */     return this.field_154330_a;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void initGui() {
/*  30 */     this.field_154330_a.init();
/*  31 */     super.initGui();
/*     */   }
/*     */   
/*     */   public void func_154325_a(String p_154325_1_, int p_154325_2_, int p_154325_3_, int p_154325_4_) {
/*  35 */     drawCenteredString(this.fontRendererObj, p_154325_1_, p_154325_2_, p_154325_3_, p_154325_4_);
/*     */   }
/*     */   
/*     */   public void a(String p_a_1_, int p_a_2_, int p_a_3_, int p_a_4_, boolean p_a_5_) {
/*  39 */     if (p_a_5_) {
/*  40 */       drawString(this.fontRendererObj, p_a_1_, p_a_2_, p_a_3_, p_a_4_);
/*     */     } else {
/*  42 */       this.fontRendererObj.drawString(p_a_1_, p_a_2_, p_a_3_, p_a_4_);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void drawTexturedModalRect(int x, int y, int textureX, int textureY, int width, int height) {
/*  50 */     this.field_154330_a.blit(x, y, textureX, textureY, width, height);
/*  51 */     super.drawTexturedModalRect(x, y, textureX, textureY, width, height);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void drawGradientRect(int left, int top, int right, int bottom, int startColor, int endColor) {
/*  59 */     super.drawGradientRect(left, top, right, bottom, startColor, endColor);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void drawDefaultBackground() {
/*  66 */     super.drawDefaultBackground();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean doesGuiPauseGame() {
/*  73 */     return super.doesGuiPauseGame();
/*     */   }
/*     */   
/*     */   public void drawWorldBackground(int tint) {
/*  77 */     super.drawWorldBackground(tint);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void drawScreen(int mouseX, int mouseY, float partialTicks) {
/*  84 */     this.field_154330_a.render(mouseX, mouseY, partialTicks);
/*     */   }
/*     */   
/*     */   public void renderToolTip(ItemStack stack, int x, int y) {
/*  88 */     super.renderToolTip(stack, x, y);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void drawCreativeTabHoveringText(String tabName, int mouseX, int mouseY) {
/*  96 */     super.drawCreativeTabHoveringText(tabName, mouseX, mouseY);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void drawHoveringText(List<String> textLines, int x, int y) {
/* 103 */     super.drawHoveringText(textLines, x, y);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void updateScreen() {
/* 110 */     this.field_154330_a.tick();
/* 111 */     super.updateScreen();
/*     */   }
/*     */   
/*     */   public int func_154329_h() {
/* 115 */     return this.fontRendererObj.FONT_HEIGHT;
/*     */   }
/*     */   
/*     */   public int func_154326_c(String p_154326_1_) {
/* 119 */     return this.fontRendererObj.getStringWidth(p_154326_1_);
/*     */   }
/*     */   
/*     */   public void func_154322_b(String p_154322_1_, int p_154322_2_, int p_154322_3_, int p_154322_4_) {
/* 123 */     this.fontRendererObj.drawStringWithShadow(p_154322_1_, p_154322_2_, p_154322_3_, p_154322_4_);
/*     */   }
/*     */   
/*     */   public List<String> func_154323_a(String p_154323_1_, int p_154323_2_) {
/* 127 */     return this.fontRendererObj.listFormattedStringToWidth(p_154323_1_, p_154323_2_);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final void actionPerformed(GuiButton button) throws IOException {
/* 134 */     this.field_154330_a.buttonClicked(((GuiButtonRealmsProxy)button).getRealmsButton());
/*     */   }
/*     */   
/*     */   public void func_154324_i() {
/* 138 */     this.buttonList.clear();
/*     */   }
/*     */   
/*     */   public void func_154327_a(RealmsButton p_154327_1_) {
/* 142 */     this.buttonList.add(p_154327_1_.getProxy());
/*     */   }
/*     */   
/*     */   public List<RealmsButton> func_154320_j() {
/* 146 */     List<RealmsButton> list = Lists.newArrayListWithExpectedSize(this.buttonList.size());
/*     */     
/* 148 */     for (GuiButton guibutton : this.buttonList) {
/* 149 */       list.add(((GuiButtonRealmsProxy)guibutton).getRealmsButton());
/*     */     }
/*     */     
/* 152 */     return list;
/*     */   }
/*     */   
/*     */   public void func_154328_b(RealmsButton p_154328_1_) {
/* 156 */     this.buttonList.remove(p_154328_1_.getProxy());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException {
/* 163 */     this.field_154330_a.mouseClicked(mouseX, mouseY, mouseButton);
/* 164 */     super.mouseClicked(mouseX, mouseY, mouseButton);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void handleMouseInput() throws IOException {
/* 171 */     this.field_154330_a.mouseEvent();
/* 172 */     super.handleMouseInput();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void handleKeyboardInput() throws IOException {
/* 179 */     this.field_154330_a.keyboardEvent();
/* 180 */     super.handleKeyboardInput();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void mouseReleased(int mouseX, int mouseY, int state) {
/* 187 */     this.field_154330_a.mouseReleased(mouseX, mouseY, state);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void mouseClickMove(int mouseX, int mouseY, int clickedMouseButton, long timeSinceLastClick) {
/* 195 */     this.field_154330_a.mouseDragged(mouseX, mouseY, clickedMouseButton, timeSinceLastClick);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void keyTyped(char typedChar, int keyCode) throws IOException {
/* 203 */     this.field_154330_a.keyPressed(typedChar, keyCode);
/*     */   }
/*     */   
/*     */   public void confirmClicked(boolean result, int id) {
/* 207 */     this.field_154330_a.confirmResult(result, id);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void onGuiClosed() {
/* 214 */     this.field_154330_a.removed();
/* 215 */     super.onGuiClosed();
/*     */   }
/*     */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\minecraft\client\gui\GuiScreenRealmsProxy.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */