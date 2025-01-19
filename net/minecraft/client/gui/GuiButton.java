/*     */ package net.minecraft.client.gui;
/*     */ 
/*     */ import client.Client;
/*     */ import net.minecraft.client.Minecraft;
/*     */ import net.minecraft.client.audio.ISound;
/*     */ import net.minecraft.client.audio.PositionedSoundRecord;
/*     */ import net.minecraft.client.audio.SoundHandler;
/*     */ import net.minecraft.client.renderer.GlStateManager;
/*     */ import net.minecraft.util.ResourceLocation;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class GuiButton
/*     */   extends Gui
/*     */ {
/* 180 */   protected static final ResourceLocation buttonTextures = new ResourceLocation("textures/gui/widgets.png");
/* 181 */   protected static final ResourceLocation cbuttonTextures = new ResourceLocation("client/widgets.png"); protected int width; protected int height; public int xPosition;
/*     */   public int yPosition;
/*     */   
/*     */   public GuiButton(int buttonId, int x, int y, String buttonText) {
/* 185 */     this(buttonId, x, y, 200, 20, buttonText);
/*     */   }
/*     */   public String displayString; public int id; public boolean enabled; public boolean visible; protected boolean hovered;
/*     */   public GuiButton(int buttonId, int x, int y, int widthIn, int heightIn, String buttonText) {
/* 189 */     this.width = 200;
/* 190 */     this.height = 20;
/* 191 */     this.enabled = true;
/* 192 */     this.visible = true;
/* 193 */     this.id = buttonId;
/* 194 */     this.xPosition = x;
/* 195 */     this.yPosition = y;
/* 196 */     this.width = widthIn;
/* 197 */     this.height = heightIn;
/* 198 */     this.displayString = buttonText;
/*     */   }
/*     */   
/*     */   protected int getHoverState(boolean mouseOver) {
/* 202 */     int i = 1;
/* 203 */     if (!this.enabled) {
/* 204 */       i = 0;
/* 205 */     } else if (mouseOver) {
/* 206 */       i = 2;
/*     */     } 
/* 208 */     return i;
/*     */   }
/*     */   
/*     */   public void drawButton(Minecraft mc, int mouseX, int mouseY) {
/* 212 */     if (this.visible) {
/* 213 */       FontRenderer fontrenderer = mc.fontRendererObj;
/* 214 */       if (!(Client.getInstance()).hudManager.customGui.isEnabled()) {
/* 215 */         mc.getTextureManager().bindTexture(buttonTextures);
/*     */       }
/* 217 */       if ((Client.getInstance()).hudManager.customGui.isEnabled()) {
/* 218 */         mc.getTextureManager().bindTexture(cbuttonTextures);
/*     */       }
/* 220 */       GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
/* 221 */       this.hovered = (mouseX >= this.xPosition && mouseY >= this.yPosition && mouseX < this.xPosition + this.width && mouseY < this.yPosition + this.height);
/* 222 */       int i = getHoverState(this.hovered);
/* 223 */       GlStateManager.enableBlend();
/* 224 */       GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
/* 225 */       GlStateManager.blendFunc(770, 771);
/* 226 */       drawTexturedModalRect(this.xPosition, this.yPosition, 0, 46 + i * 20, this.width / 2, this.height);
/* 227 */       drawTexturedModalRect(this.xPosition + this.width / 2, this.yPosition, 200 - this.width / 2, 46 + i * 20, this.width / 2, this.height);
/* 228 */       mouseDragged(mc, mouseX, mouseY);
/* 229 */       int j = 14737632;
/* 230 */       if (!this.enabled) {
/* 231 */         j = 10526880;
/* 232 */       } else if (this.hovered) {
/* 233 */         j = 16777120;
/*     */       } 
/* 235 */       drawCenteredString(fontrenderer, this.displayString, this.xPosition + this.width / 2, this.yPosition + (this.height - 8) / 2, j);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   protected void mouseDragged(Minecraft mc, int mouseX, int mouseY) {}
/*     */ 
/*     */   
/*     */   public void mouseReleased(int mouseX, int mouseY) {}
/*     */   
/*     */   public boolean mousePressed(Minecraft mc, int mouseX, int mouseY) {
/* 246 */     return (this.enabled && this.visible && mouseX >= this.xPosition && mouseY >= this.yPosition && mouseX < this.xPosition + this.width && mouseY < this.yPosition + this.height);
/*     */   }
/*     */   
/*     */   public boolean isMouseOver() {
/* 250 */     return this.hovered;
/*     */   }
/*     */ 
/*     */   
/*     */   public void drawButtonForegroundLayer(int mouseX, int mouseY) {}
/*     */   
/*     */   public void playPressSound(SoundHandler soundHandlerIn) {
/* 257 */     soundHandlerIn.playSound((ISound)PositionedSoundRecord.create(new ResourceLocation("gui.button.press"), 1.0F));
/*     */   }
/*     */   
/*     */   public int getButtonWidth() {
/* 261 */     return this.width;
/*     */   }
/*     */   
/*     */   public void setWidth(int width) {
/* 265 */     this.width = width;
/*     */   }
/*     */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\minecraft\client\gui\GuiButton.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */