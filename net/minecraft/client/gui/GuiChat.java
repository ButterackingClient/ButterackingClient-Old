/*     */ package net.minecraft.client.gui;
/*     */ 
/*     */ import client.Client;
/*     */ import com.google.common.collect.Lists;
/*     */ import java.io.IOException;
/*     */ import java.util.List;
/*     */ import kp.Config;
/*     */ import kp.input.KoreanIME;
/*     */ import kp.ui.SimpleButton;
/*     */ import net.minecraft.client.Minecraft;
/*     */ import net.minecraft.network.Packet;
/*     */ import net.minecraft.network.play.client.C14PacketTabComplete;
/*     */ import net.minecraft.src.IconManager;
/*     */ import net.minecraft.util.BlockPos;
/*     */ import net.minecraft.util.ChatComponentText;
/*     */ import net.minecraft.util.IChatComponent;
/*     */ import net.minecraft.util.MathHelper;
/*     */ import net.minecraft.util.MovingObjectPosition;
/*     */ import org.apache.commons.lang3.StringUtils;
/*     */ import org.apache.logging.log4j.LogManager;
/*     */ import org.apache.logging.log4j.Logger;
/*     */ import org.lwjgl.input.Keyboard;
/*     */ import org.lwjgl.input.Mouse;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class GuiChat
/*     */   extends GuiScreen
/*     */   implements SimpleButton.ButtonActionEvent
/*     */ {
/* 396 */   private static final Logger logger = LogManager.getLogger(); private String historyBuffer; private int sentHistoryCursor; private boolean playerNamesFound; private boolean waitingOnAutocomplete; private int autocompleteIndex; private List foundPlayerNames;
/*     */   protected GuiTextField inputField;
/*     */   
/*     */   public GuiChat() {
/* 400 */     this.historyBuffer = "";
/* 401 */     this.sentHistoryCursor = -1;
/* 402 */     this.foundPlayerNames = Lists.newArrayList();
/* 403 */     this.defaultInputFieldText = "";
/* 404 */     this.guiChatlastX = 0;
/* 405 */     this.guiChatHover = false;
/* 406 */     this.isOpen = false;
/* 407 */     this.renderAmount = 0.0D;
/* 408 */     this.deleteJaso = new SimpleButton(0, "한글 자소 단위로 지우기", 0, 0, 100, 12);
/* 409 */     this.keyArrayType = new SimpleButton(1, "", 0, 0, 50, 12);
/*     */   }
/*     */   private String defaultInputFieldText; private int guiChatlastX; private boolean guiChatHover; private boolean isOpen; private double renderAmount; private SimpleButton deleteJaso; private SimpleButton keyArrayType;
/*     */   public GuiChat(String defaultText) {
/* 413 */     this.historyBuffer = "";
/* 414 */     this.sentHistoryCursor = -1;
/* 415 */     this.foundPlayerNames = Lists.newArrayList();
/* 416 */     this.defaultInputFieldText = "";
/* 417 */     this.guiChatlastX = 0;
/* 418 */     this.guiChatHover = false;
/* 419 */     this.isOpen = false;
/* 420 */     this.renderAmount = 0.0D;
/* 421 */     this.deleteJaso = new SimpleButton(0, "한글 자소 단위로 지우기", 0, 0, 100, 12);
/* 422 */     this.keyArrayType = new SimpleButton(1, "", 0, 0, 50, 12);
/* 423 */     this.defaultInputFieldText = defaultText;
/*     */   }
/*     */ 
/*     */   
/*     */   public void initGui() {
/* 428 */     Keyboard.enableRepeatEvents(true);
/* 429 */     this.sentHistoryCursor = this.mc.ingameGUI.getChatGUI().getSentMessages().size();
/* 430 */     (this.inputField = new GuiTextField(0, this.fontRendererObj, 4, height - 12, width - 4, 12)).setMaxStringLength(100);
/* 431 */     this.inputField.setEnableBackgroundDrawing(false);
/* 432 */     this.inputField.setFocused(true);
/* 433 */     this.inputField.setText(this.defaultInputFieldText);
/* 434 */     this.inputField.setCanLoseFocus(false);
/* 435 */     this.deleteJaso.setActionEvent(this);
/* 436 */     this.deleteJaso.useState = true;
/* 437 */     this.deleteJaso.state = Config.DELETE_JASO;
/* 438 */     this.keyArrayType.setActionEvent(this);
/* 439 */     this.keyArrayType.text = Config.keyloader.getKeyboard().keyboardName();
/* 440 */     KoreanIME.lastGuiChatStr = "";
/* 441 */     this.isOpen = false;
/*     */   }
/*     */ 
/*     */   
/*     */   public void onGuiClosed() {
/* 446 */     Keyboard.enableRepeatEvents(false);
/* 447 */     this.mc.ingameGUI.getChatGUI().resetScroll();
/*     */   }
/*     */ 
/*     */   
/*     */   public void updateScreen() {
/* 452 */     this.inputField.updateCursorCounter();
/*     */   }
/*     */ 
/*     */   
/*     */   protected void keyTyped(char typedChar, int keyCode) throws IOException {
/* 457 */     this.waitingOnAutocomplete = false;
/* 458 */     if (keyCode == 15) {
/* 459 */       autocompletePlayerNames();
/*     */     } else {
/* 461 */       this.playerNamesFound = false;
/*     */     } 
/* 463 */     if (keyCode == 1) {
/* 464 */       this.mc.displayGuiScreen(null);
/* 465 */     } else if (keyCode != 28 && keyCode != 156) {
/* 466 */       if (keyCode == 200) {
/* 467 */         getSentHistory(-1);
/* 468 */       } else if (keyCode == 208) {
/* 469 */         getSentHistory(1);
/* 470 */       } else if (keyCode == 201) {
/* 471 */         this.mc.ingameGUI.getChatGUI().scroll(this.mc.ingameGUI.getChatGUI().getLineCount() - 1);
/* 472 */       } else if (keyCode == 209) {
/* 473 */         this.mc.ingameGUI.getChatGUI().scroll(-this.mc.ingameGUI.getChatGUI().getLineCount() + 1);
/*     */       } else {
/* 475 */         this.inputField.textboxKeyTyped(typedChar, keyCode);
/*     */       } 
/*     */     } else {
/* 478 */       String s = this.inputField.getText().trim();
/* 479 */       if (s.length() > 0) {
/* 480 */         sendChatMessage(s);
/*     */       }
/* 482 */       this.mc.displayGuiScreen(null);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void handleMouseInput() throws IOException {
/* 488 */     super.handleMouseInput();
/* 489 */     Minecraft minecraft = Minecraft.getMinecraft();
/* 490 */     int i = Mouse.getEventButton();
/* 491 */     int j = minecraft.fontRendererObj.FONT_HEIGHT + 2;
/* 492 */     int k = Mouse.getEventX() * width / minecraft.displayWidth;
/* 493 */     int l = height - Mouse.getEventY() * height / minecraft.displayHeight - 1;
/* 494 */     int i2 = height - 15;
/* 495 */     boolean flag = (k > this.guiChatlastX && l > i2 - j && k < this.guiChatlastX + j && l < i2);
/* 496 */     this.deleteJaso.mouseInput(k, l, i);
/* 497 */     this.keyArrayType.mouseInput(k, l, i);
/* 498 */     if (i == -1) {
/* 499 */       if (flag) {
/* 500 */         this.guiChatHover = true;
/*     */       } else {
/* 502 */         this.guiChatHover = false;
/*     */       } 
/* 504 */     } else if (Mouse.getEventButtonState() && flag) {
/* 505 */       this.isOpen = !this.isOpen;
/*     */     } 
/* 507 */     int j2 = Mouse.getEventDWheel();
/* 508 */     if (j2 != 0) {
/* 509 */       if (j2 > 1) {
/* 510 */         j2 = 1;
/*     */       }
/* 512 */       if (j2 < -1) {
/* 513 */         j2 = -1;
/*     */       }
/* 515 */       if (!GuiScreen.isShiftKeyDown()) {
/* 516 */         j2 *= 7;
/*     */       }
/* 518 */       this.mc.ingameGUI.getChatGUI().scroll(j2);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   protected void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException {
/* 524 */     if (mouseButton == 0) {
/* 525 */       IChatComponent ichatcomponent = this.mc.ingameGUI.getChatGUI().getChatComponent(Mouse.getX(), Mouse.getY());
/* 526 */       if (handleComponentClick(ichatcomponent)) {
/*     */         return;
/*     */       }
/*     */     } 
/* 530 */     this.inputField.mouseClicked(mouseX, mouseY, mouseButton);
/* 531 */     super.mouseClicked(mouseX, mouseY, mouseButton);
/*     */   }
/*     */ 
/*     */   
/*     */   protected void setText(String newChatText, boolean shouldOverwrite) {
/* 536 */     if (shouldOverwrite) {
/* 537 */       this.inputField.setText(newChatText);
/*     */     } else {
/* 539 */       this.inputField.writeText(newChatText);
/*     */     } 
/*     */   }
/*     */   
/*     */   public void autocompletePlayerNames() {
/* 544 */     if (this.playerNamesFound) {
/* 545 */       this.inputField.deleteFromCursor(this.inputField.func_146197_a(-1, this.inputField.getCursorPosition(), false) - this.inputField.getCursorPosition());
/* 546 */       if (this.autocompleteIndex >= this.foundPlayerNames.size()) {
/* 547 */         this.autocompleteIndex = 0;
/*     */       }
/*     */     } else {
/* 550 */       int i = this.inputField.func_146197_a(-1, this.inputField.getCursorPosition(), false);
/* 551 */       this.foundPlayerNames.clear();
/* 552 */       this.autocompleteIndex = 0;
/* 553 */       String s = this.inputField.getText().substring(i).toLowerCase();
/* 554 */       String s2 = this.inputField.getText().substring(0, this.inputField.getCursorPosition());
/* 555 */       sendAutocompleteRequest(s2, s);
/* 556 */       if (this.foundPlayerNames.isEmpty()) {
/*     */         return;
/*     */       }
/* 559 */       this.playerNamesFound = true;
/* 560 */       this.inputField.deleteFromCursor(i - this.inputField.getCursorPosition());
/*     */     } 
/* 562 */     if (this.foundPlayerNames.size() > 1) {
/* 563 */       StringBuilder stringbuilder = new StringBuilder();
/* 564 */       for (Object object : this.foundPlayerNames) {
/* 565 */         if (object instanceof String) {
/* 566 */           if (stringbuilder.length() > 0) {
/* 567 */             stringbuilder.append(", ");
/*     */           }
/* 569 */           stringbuilder.append((String)object);
/*     */         } 
/*     */       } 
/* 572 */       this.mc.ingameGUI.getChatGUI().printChatMessageWithOptionalDeletion((IChatComponent)new ChatComponentText(stringbuilder.toString()), 1);
/*     */     } 
/* 574 */     this.inputField.writeText(this.foundPlayerNames.get(this.autocompleteIndex++));
/*     */   }
/*     */   
/*     */   private void sendAutocompleteRequest(String p_146405_1_, String p_146405_2_) {
/* 578 */     if (p_146405_1_.length() >= 1) {
/* 579 */       BlockPos blockpos = null;
/* 580 */       if (this.mc.objectMouseOver != null && this.mc.objectMouseOver.typeOfHit == ((MovingObjectPosition.MovingObjectType[])MovingObjectPosition.MovingObjectType.class.getEnumConstants())[1]) {
/* 581 */         blockpos = this.mc.objectMouseOver.getBlockPos();
/*     */       }
/* 583 */       this.mc.thePlayer.sendQueue.addToSendQueue((Packet)new C14PacketTabComplete(p_146405_1_, blockpos));
/* 584 */       this.waitingOnAutocomplete = true;
/*     */     } 
/*     */   }
/*     */   
/*     */   public void getSentHistory(int msgPos) {
/* 589 */     int i = this.sentHistoryCursor + msgPos;
/* 590 */     int j = this.mc.ingameGUI.getChatGUI().getSentMessages().size();
/* 591 */     i = MathHelper.clamp_int(i, 0, j);
/* 592 */     if (i != this.sentHistoryCursor) {
/* 593 */       if (i == j) {
/* 594 */         this.sentHistoryCursor = j;
/* 595 */         this.inputField.setText(this.historyBuffer);
/*     */       } else {
/* 597 */         if (this.sentHistoryCursor == j) {
/* 598 */           this.historyBuffer = this.inputField.getText();
/*     */         }
/* 600 */         this.inputField.setText(this.mc.ingameGUI.getChatGUI().getSentMessages().get(i));
/* 601 */         this.sentHistoryCursor = i;
/*     */       } 
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public void drawScreen(int mouseX, int mouseY, float partialTicks) {
/* 608 */     if (this.isOpen) {
/* 609 */       if (this.renderAmount < 119.0D) {
/* 610 */         this.renderAmount += (120.0D - this.renderAmount) * 0.6D * partialTicks;
/*     */       }
/* 612 */     } else if (this.renderAmount > 1.0D) {
/* 613 */       this.renderAmount -= this.renderAmount * 0.6D * partialTicks;
/*     */     } 
/* 615 */     FontRenderer fontrenderer = (Minecraft.getMinecraft()).fontRendererObj;
/* 616 */     int i = KoreanIME.lastGuiChatStr.length();
/* 617 */     String s = String.valueOf(String.valueOf((Client.getInstance()).mainColor)) + (KoreanIME.enabled ? "한글" : "English") + (Client.getInstance()).white + "[" + i + "]";
/* 618 */     int j = fontrenderer.FONT_HEIGHT + 2;
/* 619 */     int k = height - 15;
/* 620 */     int l = fontrenderer.getStringWidth(s);
/* 621 */     IconManager.drawIcon(this.guiChatlastX = l + 7, k - j, j, j, 2, 0);
/* 622 */     Gui.drawRect(2, k - j, 6 + l, k, -2147483648);
/* 623 */     if ((Client.getInstance()).krPatchEnabled) {
/* 624 */       Gui.drawRect(this.guiChatlastX, k - j, this.guiChatlastX + j, k, this.guiChatHover ? 1627389951 : Integer.MIN_VALUE);
/* 625 */       Gui.drawRect(2, k - j - 1, (int)(2.0D + this.renderAmount), k - j - 30, -2147483648);
/*     */     } 
/* 627 */     fontrenderer.drawString(s, 4, height - 25, -1);
/* 628 */     if ((Client.getInstance()).krPatchEnabled) {
/* 629 */       drawCenteredString(fontrenderer, this.isOpen ? "←" : "→", this.guiChatlastX + j / 2, k - j + 1, -1);
/* 630 */       if (this.isOpen) {
/* 631 */         i = fontrenderer.drawString("자판 배열: ", 4, height - 40, -1);
/* 632 */         this.deleteJaso.setPosition(4, height - 54);
/* 633 */         this.deleteJaso.draw();
/* 634 */         this.keyArrayType.setPosition(4 + i, height - 41);
/* 635 */         this.keyArrayType.draw();
/*     */       } 
/*     */     } 
/* 638 */     Gui.drawRect(2, height - 14, width - 2, height - 2, -2147483648);
/* 639 */     this.inputField.drawTextBox();
/* 640 */     IChatComponent ichatcomponent = this.mc.ingameGUI.getChatGUI().getChatComponent(Mouse.getX(), Mouse.getY());
/* 641 */     if (ichatcomponent != null && ichatcomponent.getChatStyle().getChatHoverEvent() != null) {
/* 642 */       handleComponentHover(ichatcomponent, mouseX, mouseY);
/*     */     }
/* 644 */     super.drawScreen(mouseX, mouseY, partialTicks);
/*     */   }
/*     */ 
/*     */   
/*     */   public void onAutocompleteResponse(String[] p_146406_1_) {
/* 649 */     if (this.waitingOnAutocomplete) {
/* 650 */       this.playerNamesFound = false;
/* 651 */       this.foundPlayerNames.clear(); byte b; int i; String[] arrayOfString;
/* 652 */       for (i = (arrayOfString = p_146406_1_).length, b = 0; b < i; ) { String s = arrayOfString[b];
/* 653 */         if (s.length() > 0)
/* 654 */           this.foundPlayerNames.add(s); 
/*     */         b++; }
/*     */       
/* 657 */       String s2 = this.inputField.getText().substring(this.inputField.func_146197_a(-1, this.inputField.getCursorPosition(), false));
/* 658 */       String s3 = StringUtils.getCommonPrefix(p_146406_1_);
/* 659 */       if (s3.length() > 0 && !s2.equalsIgnoreCase(s3)) {
/* 660 */         this.inputField.deleteFromCursor(this.inputField.func_146197_a(-1, this.inputField.getCursorPosition(), false) - this.inputField.getCursorPosition());
/* 661 */         this.inputField.writeText(s3);
/* 662 */       } else if (this.foundPlayerNames.size() > 0) {
/* 663 */         this.playerNamesFound = true;
/* 664 */         autocompletePlayerNames();
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean doesGuiPauseGame() {
/* 671 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public void onClick(int p_onClick_1_) {
/* 676 */     switch (p_onClick_1_) {
/*     */       case 0:
/* 678 */         Config.DELETE_JASO = this.deleteJaso.state;
/*     */         break;
/*     */       
/*     */       case 1:
/* 682 */         this.keyArrayType.text = Config.keyloader.applySetIterate();
/*     */         break;
/*     */     } 
/*     */     
/* 686 */     Config.save();
/*     */   }
/*     */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\minecraft\client\gui\GuiChat.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */