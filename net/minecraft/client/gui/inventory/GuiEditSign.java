/*     */ package net.minecraft.client.gui.inventory;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import kp.input.IInputTarget;
/*     */ import kp.input.KoreanIME;
/*     */ import net.minecraft.block.Block;
/*     */ import net.minecraft.client.gui.GuiButton;
/*     */ import net.minecraft.client.gui.GuiScreen;
/*     */ import net.minecraft.client.network.NetHandlerPlayClient;
/*     */ import net.minecraft.client.renderer.GlStateManager;
/*     */ import net.minecraft.client.renderer.tileentity.TileEntityRendererDispatcher;
/*     */ import net.minecraft.client.resources.I18n;
/*     */ import net.minecraft.init.Blocks;
/*     */ import net.minecraft.network.Packet;
/*     */ import net.minecraft.network.play.client.C12PacketUpdateSign;
/*     */ import net.minecraft.tileentity.TileEntity;
/*     */ import net.minecraft.tileentity.TileEntitySign;
/*     */ import net.minecraft.util.ChatComponentText;
/*     */ import net.minecraft.util.IChatComponent;
/*     */ import org.lwjgl.input.Keyboard;
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
/*     */ public class GuiEditSign
/*     */   extends GuiScreen
/*     */   implements IInputTarget, IInputTarget.CursorSelectionFunc, IInputTarget.InputIdentifier
/*     */ {
/*     */   private TileEntitySign tileSign;
/*     */   private int f2;
/*     */   private int editLine;
/*     */   private GuiButton doneBtn;
/* 394 */   private KoreanIME ime = new KoreanIME(this);
/*     */   
/*     */   public GuiEditSign(TileEntitySign teSign) {
/* 397 */     this.tileSign = teSign;
/* 398 */     this.ime.setCursorSelectionFunc(this);
/* 399 */     this.ime.setIdentifier(this);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void initGui() {
/* 407 */     this.buttonList.clear();
/* 408 */     Keyboard.enableRepeatEvents(true);
/* 409 */     this.buttonList.add(this.doneBtn = new GuiButton(0, width / 2 - 100, height / 4 + 120, I18n.format("gui.done", new Object[0])));
/* 410 */     this.tileSign.setEditable(false);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void onGuiClosed() {
/* 417 */     Keyboard.enableRepeatEvents(false);
/* 418 */     NetHandlerPlayClient nethandlerplayclient = this.mc.getNetHandler();
/*     */     
/* 420 */     if (nethandlerplayclient != null) {
/* 421 */       nethandlerplayclient.addToSendQueue((Packet)new C12PacketUpdateSign(this.tileSign.getPos(), this.tileSign.signText));
/*     */     }
/*     */     
/* 424 */     this.tileSign.setEditable(true);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void updateScreen() {
/* 431 */     this.f2++;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void actionPerformed(GuiButton button) throws IOException {
/* 438 */     if (button.enabled && button.id == 0) {
/* 439 */       this.tileSign.markDirty();
/* 440 */       this.mc.displayGuiScreen(null);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void keyTyped(char typedChar, int keyCode) throws IOException {
/* 449 */     if (keyCode == 200 || keyCode == 208 || keyCode == 28 || keyCode == 156) {
/* 450 */       if (keyCode == 200) {
/* 451 */         this.editLine = this.editLine - 1 & 0x3;
/*     */       } else {
/* 453 */         this.editLine = this.editLine + 1 & 0x3;
/*     */       } 
/*     */       
/* 456 */       this.ime.setCursor(getTargetText().length());
/* 457 */       this.ime.setSelection(getTargetText().length());
/*     */     } 
/*     */     
/* 460 */     this.ime.onTyped(keyCode, typedChar);
/*     */     
/* 462 */     if (keyCode == 1) {
/* 463 */       actionPerformed(this.doneBtn);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void drawScreen(int mouseX, int mouseY, float partialTicks) {
/* 471 */     drawDefaultBackground();
/* 472 */     drawCenteredString(this.fontRendererObj, I18n.format("sign.edit", new Object[0]), width / 2, 40, 16777215);
/* 473 */     GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
/* 474 */     GlStateManager.pushMatrix();
/* 475 */     GlStateManager.translate((width / 2), 0.0F, 50.0F);
/* 476 */     float f = 93.75F;
/* 477 */     GlStateManager.scale(-f, -f, -f);
/* 478 */     GlStateManager.rotate(180.0F, 0.0F, 1.0F, 0.0F);
/* 479 */     Block block = this.tileSign.getBlockType();
/*     */     
/* 481 */     if (block == Blocks.standing_sign) {
/* 482 */       float f1 = (this.tileSign.getBlockMetadata() * 360) / 16.0F;
/* 483 */       GlStateManager.rotate(f1, 0.0F, 1.0F, 0.0F);
/* 484 */       GlStateManager.translate(0.0F, -1.0625F, 0.0F);
/*     */     } else {
/* 486 */       int i = this.tileSign.getBlockMetadata();
/* 487 */       float f2 = 0.0F;
/*     */       
/* 489 */       if (i == 2) {
/* 490 */         f2 = 180.0F;
/*     */       }
/*     */       
/* 493 */       if (i == 4) {
/* 494 */         f2 = 90.0F;
/*     */       }
/*     */       
/* 497 */       if (i == 5) {
/* 498 */         f2 = -90.0F;
/*     */       }
/*     */       
/* 501 */       GlStateManager.rotate(f2, 0.0F, 1.0F, 0.0F);
/* 502 */       GlStateManager.translate(0.0F, -1.0625F, 0.0F);
/*     */     } 
/*     */     
/* 505 */     if (this.f2 / 6 % 2 == 0) {
/* 506 */       this.tileSign.lineBeingEdited = this.editLine;
/*     */     }
/*     */     
/* 509 */     String s1 = getTargetText();
/*     */     
/* 511 */     if (this.ime.getCursor() < s1.length()) {
/* 512 */       String s2 = s1.substring(0, this.ime.getCursor());
/* 513 */       String s = s1.substring(this.ime.getCursor());
/* 514 */       setTargetText(String.valueOf(s2) + "_" + s);
/*     */     } 
/*     */     
/* 517 */     TileEntityRendererDispatcher.instance.renderTileEntityAt((TileEntity)this.tileSign, -0.5D, -0.75D, -0.5D, 0.0F);
/* 518 */     setTargetText(s1);
/* 519 */     this.tileSign.lineBeingEdited = -1;
/* 520 */     GlStateManager.popMatrix();
/* 521 */     super.drawScreen(mouseX, mouseY, partialTicks);
/*     */   }
/*     */   
/*     */   public String getTargetText() {
/* 525 */     return this.tileSign.signText[this.editLine].getUnformattedText();
/*     */   }
/*     */   
/*     */   public boolean setTargetText(String p_setTargetText_1_) {
/* 529 */     this.tileSign.signText[this.editLine] = (IChatComponent)new ChatComponentText(p_setTargetText_1_);
/* 530 */     return true;
/*     */   }
/*     */   
/*     */   public int getCursor() {
/* 534 */     return this.ime.getCursor();
/*     */   }
/*     */   
/*     */   public void setCursor(int p_setCursor_1_) {
/* 538 */     this.ime.setCursor(p_setCursor_1_);
/*     */   }
/*     */   
/*     */   public int getSelection() {
/* 542 */     return getCursor();
/*     */   }
/*     */ 
/*     */   
/*     */   public void setSelection(int p_setSelection_1_) {}
/*     */   
/*     */   public boolean apply(String p_apply_1_) {
/* 549 */     return (this.fontRendererObj.getStringWidth(String.valueOf(getTargetText()) + p_apply_1_) <= 90);
/*     */   }
/*     */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\minecraft\client\gui\inventory\GuiEditSign.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */