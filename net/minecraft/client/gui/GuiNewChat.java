/*     */ package net.minecraft.client.gui;
/*     */ 
/*     */ import client.Client;
/*     */ import com.google.common.collect.Lists;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
/*     */ import net.minecraft.client.Minecraft;
/*     */ import net.minecraft.client.renderer.GlStateManager;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.util.ChatComponentText;
/*     */ import net.minecraft.util.IChatComponent;
/*     */ import net.minecraft.util.MathHelper;
/*     */ import org.apache.logging.log4j.LogManager;
/*     */ import org.apache.logging.log4j.Logger;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class GuiNewChat
/*     */   extends Gui
/*     */ {
/* 403 */   private static final Logger logger = LogManager.getLogger(); private final Minecraft mc; private final List<String> sentMessages;
/*     */   private final List<ChatLine> chatLines;
/*     */   
/*     */   public GuiNewChat(Minecraft mcIn) {
/* 407 */     this.sentMessages = Lists.newArrayList();
/* 408 */     this.chatLines = Lists.newArrayList();
/* 409 */     this.drawnChatLines = Lists.newArrayList();
/* 410 */     this.mc = mcIn;
/*     */   }
/*     */   private final List<ChatLine> drawnChatLines; private int scrollPos; private boolean isScrolled;
/*     */   public void drawChat(int updateCounter) {
/* 414 */     if (this.mc.gameSettings.chatVisibility != EntityPlayer.EnumChatVisibility.HIDDEN) {
/* 415 */       int i = getLineCount();
/* 416 */       boolean flag = false;
/* 417 */       int j = 0;
/* 418 */       int k = this.drawnChatLines.size();
/* 419 */       float f = this.mc.gameSettings.chatOpacity * 0.9F + 0.1F;
/* 420 */       if (k > 0) {
/* 421 */         if (getChatOpen()) {
/* 422 */           flag = true;
/*     */         }
/* 424 */         float f2 = getChatScale();
/* 425 */         int l = MathHelper.ceiling_float_int(getChatWidth() / f2);
/* 426 */         GlStateManager.pushMatrix();
/* 427 */         GlStateManager.translate(2.0F, 20.0F, 0.0F);
/* 428 */         GlStateManager.scale(f2, f2, 1.0F);
/* 429 */         for (int i2 = 0; i2 + this.scrollPos < this.drawnChatLines.size() && i2 < i; i2++) {
/* 430 */           ChatLine chatline = this.drawnChatLines.get(i2 + this.scrollPos);
/* 431 */           if (chatline != null) {
/* 432 */             int j2 = updateCounter - chatline.getUpdatedCounter();
/* 433 */             if (j2 < 200 || flag) {
/* 434 */               double d0 = j2 / 200.0D;
/* 435 */               d0 = 1.0D - d0;
/* 436 */               d0 *= 10.0D;
/* 437 */               d0 = MathHelper.clamp_double(d0, 0.0D, 1.0D);
/* 438 */               d0 *= d0;
/* 439 */               int l2 = (int)(255.0D * d0);
/* 440 */               if (flag) {
/* 441 */                 l2 = 255;
/*     */               }
/* 443 */               l2 *= (int)f;
/* 444 */               j++;
/* 445 */               if (l2 > 3) {
/* 446 */                 int i3 = 0;
/* 447 */                 int j3 = -i2 * 9;
/* 448 */                 if (!(Client.getInstance()).hudManager.clearChat.isEnabled()) {
/* 449 */                   Gui.drawRect(0, j3 - 9, 0 + l + 4, j3, l2 / 2 << 24);
/*     */                 }
/* 451 */                 String s = chatline.getChatComponent().getFormattedText();
/* 452 */                 GlStateManager.enableBlend();
/* 453 */                 this.mc.fontRendererObj.drawStringWithShadow(s, 0.0F, (j3 - 8), 16777215 + (l2 << 24));
/* 454 */                 GlStateManager.disableAlpha();
/* 455 */                 GlStateManager.disableBlend();
/*     */               } 
/*     */             } 
/*     */           } 
/*     */         } 
/* 460 */         if (flag) {
/* 461 */           int k2 = this.mc.fontRendererObj.FONT_HEIGHT;
/* 462 */           GlStateManager.translate(-3.0F, 0.0F, 0.0F);
/* 463 */           int l3 = k * k2 + k;
/* 464 */           int i4 = j * k2 + j;
/* 465 */           int j4 = this.scrollPos * i4 / k;
/* 466 */           int k3 = i4 * i4 / l3;
/* 467 */           if (l3 != i4) {
/* 468 */             int k4 = (j4 > 0) ? 170 : 96;
/* 469 */             int l4 = this.isScrolled ? 13382451 : 3355562;
/* 470 */             Gui.drawRect(0, -j4, 2, -j4 - k3, l4 + (k4 << 24));
/* 471 */             Gui.drawRect(2, -j4, 1, -j4 - k3, 13421772 + (k4 << 24));
/*     */           } 
/*     */         } 
/* 474 */         GlStateManager.popMatrix();
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   public void clearChatMessages() {
/* 480 */     this.drawnChatLines.clear();
/* 481 */     this.chatLines.clear();
/* 482 */     this.sentMessages.clear();
/*     */   }
/*     */   
/*     */   public void printChatMessage(IChatComponent chatComponent) {
/*     */     try {
/* 487 */       printChatMessageWithOptionalDeletion(chatComponent, 0);
/* 488 */       if ((Client.getInstance()).hudManager.autoGG.isEnabled()) {
/* 489 */         if (chatComponent.getUnformattedText().contains("WINNER !") || chatComponent.getUnformattedText().contains("1st Killer -") || chatComponent.getUnformattedText().contains("1st Place -") || chatComponent.getUnformattedText().contains("Winner: ") || chatComponent.getUnformattedText().contains("Winning Team: ") || chatComponent.getUnformattedText().contains("Top Seeker: ") || chatComponent.getUnformattedText().contains("Winner #1 (") || chatComponent.getUnformattedText().contains("Top Survivors")) {
/* 490 */           System.out.println("gg");
/* 491 */           (Minecraft.getMinecraft()).thePlayer.sendChatMessage("/achat gg");
/*     */         } 
/* 493 */         logger.info("[CHAT] " + chatComponent.getUnformattedText());
/*     */       } 
/* 495 */     } catch (Exception exception) {}
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void printChatMessageWithOptionalDeletion(IChatComponent chatComponent, int chatLineId) {
/* 502 */     setChatLine(chatComponent, chatLineId, this.mc.ingameGUI.getUpdateCounter(), false);
/* 503 */     logger.info("[CHAT] " + chatComponent.getUnformattedText());
/*     */   }
/*     */   
/*     */   private void setChatLine(IChatComponent chatComponent, int chatLineId, int updateCounter, boolean displayOnly) {
/* 507 */     if (chatLineId != 0) {
/* 508 */       deleteChatLine(chatLineId);
/*     */     }
/* 510 */     int i = MathHelper.floor_float(getChatWidth() / getChatScale());
/* 511 */     List<IChatComponent> list = GuiUtilRenderComponents.splitText(chatComponent, i, this.mc.fontRendererObj, false, false);
/* 512 */     boolean flag = getChatOpen();
/* 513 */     for (IChatComponent ichatcomponent : list) {
/* 514 */       if (flag && this.scrollPos > 0) {
/* 515 */         this.isScrolled = true;
/* 516 */         scroll(1);
/*     */       } 
/* 518 */       this.drawnChatLines.add(0, new ChatLine(updateCounter, ichatcomponent, chatLineId));
/*     */     } 
/* 520 */     while (this.drawnChatLines.size() > 100) {
/* 521 */       this.drawnChatLines.remove(this.drawnChatLines.size() - 1);
/*     */     }
/* 523 */     if (!displayOnly) {
/* 524 */       this.chatLines.add(0, new ChatLine(updateCounter, chatComponent, chatLineId));
/* 525 */       while (this.chatLines.size() > 100) {
/* 526 */         this.chatLines.remove(this.chatLines.size() - 1);
/*     */       }
/*     */     } 
/*     */   }
/*     */   
/*     */   public void refreshChat() {
/* 532 */     this.drawnChatLines.clear();
/* 533 */     resetScroll();
/* 534 */     for (int i = this.chatLines.size() - 1; i >= 0; i--) {
/* 535 */       ChatLine chatline = this.chatLines.get(i);
/* 536 */       setChatLine(chatline.getChatComponent(), chatline.getChatLineID(), chatline.getUpdatedCounter(), true);
/*     */     } 
/*     */   }
/*     */   
/*     */   public List<String> getSentMessages() {
/* 541 */     return this.sentMessages;
/*     */   }
/*     */   
/*     */   public void addToSentMessages(String message) {
/* 545 */     if (this.sentMessages.isEmpty() || !((String)this.sentMessages.get(this.sentMessages.size() - 1)).equals(message)) {
/* 546 */       this.sentMessages.add(message);
/*     */     }
/*     */   }
/*     */   
/*     */   public void resetScroll() {
/* 551 */     this.scrollPos = 0;
/* 552 */     this.isScrolled = false;
/*     */   }
/*     */   
/*     */   public void scroll(int amount) {
/* 556 */     this.scrollPos += amount;
/* 557 */     int i = this.drawnChatLines.size();
/* 558 */     if (this.scrollPos > i - getLineCount()) {
/* 559 */       this.scrollPos = i - getLineCount();
/*     */     }
/* 561 */     if (this.scrollPos <= 0) {
/* 562 */       this.scrollPos = 0;
/* 563 */       this.isScrolled = false;
/*     */     } 
/*     */   }
/*     */   
/*     */   public IChatComponent getChatComponent(int mouseX, int mouseY) {
/* 568 */     if (!getChatOpen()) {
/* 569 */       return null;
/*     */     }
/* 571 */     ScaledResolution scaledresolution = new ScaledResolution(this.mc);
/* 572 */     int i = scaledresolution.getScaleFactor();
/* 573 */     float f = getChatScale();
/* 574 */     int j = mouseX / i - 3;
/* 575 */     int k = mouseY / i - 27;
/* 576 */     j = MathHelper.floor_float(j / f);
/* 577 */     k = MathHelper.floor_float(k / f);
/* 578 */     if (j < 0 || k < 0) {
/* 579 */       return null;
/*     */     }
/* 581 */     int l = Math.min(getLineCount(), this.drawnChatLines.size());
/* 582 */     if (j <= MathHelper.floor_float(getChatWidth() / getChatScale()) && k < this.mc.fontRendererObj.FONT_HEIGHT * l + l) {
/* 583 */       int i2 = k / this.mc.fontRendererObj.FONT_HEIGHT + this.scrollPos;
/* 584 */       if (i2 >= 0 && i2 < this.drawnChatLines.size()) {
/* 585 */         ChatLine chatline = this.drawnChatLines.get(i2);
/* 586 */         int j2 = 0;
/* 587 */         for (IChatComponent ichatcomponent : chatline.getChatComponent()) {
/* 588 */           if (ichatcomponent instanceof ChatComponentText) {
/* 589 */             j2 += this.mc.fontRendererObj.getStringWidth(GuiUtilRenderComponents.func_178909_a(((ChatComponentText)ichatcomponent).getChatComponentText_TextValue(), false));
/* 590 */             if (j2 > j) {
/* 591 */               return ichatcomponent;
/*     */             }
/*     */           } 
/*     */         } 
/*     */       } 
/*     */       
/* 597 */       return null;
/*     */     } 
/* 599 */     return null;
/*     */   }
/*     */   
/*     */   public boolean getChatOpen() {
/* 603 */     return this.mc.currentScreen instanceof GuiChat;
/*     */   }
/*     */   
/*     */   public void deleteChatLine(int id) {
/* 607 */     Iterator<ChatLine> iterator = this.drawnChatLines.iterator();
/* 608 */     while (iterator.hasNext()) {
/* 609 */       ChatLine chatline = iterator.next();
/* 610 */       if (chatline.getChatLineID() == id) {
/* 611 */         iterator.remove();
/*     */       }
/*     */     } 
/* 614 */     iterator = this.chatLines.iterator();
/* 615 */     while (iterator.hasNext()) {
/* 616 */       ChatLine chatline2 = iterator.next();
/* 617 */       if (chatline2.getChatLineID() == id) {
/* 618 */         iterator.remove();
/*     */         break;
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   public int getChatWidth() {
/* 625 */     return calculateChatboxWidth(this.mc.gameSettings.chatWidth);
/*     */   }
/*     */   
/*     */   public int getChatHeight() {
/* 629 */     return calculateChatboxHeight(getChatOpen() ? this.mc.gameSettings.chatHeightFocused : this.mc.gameSettings.chatHeightUnfocused);
/*     */   }
/*     */   
/*     */   public float getChatScale() {
/* 633 */     return this.mc.gameSettings.chatScale;
/*     */   }
/*     */   
/*     */   public static int calculateChatboxWidth(float scale) {
/* 637 */     int i = 320;
/* 638 */     int j = 40;
/* 639 */     return MathHelper.floor_float(scale * 280.0F + 40.0F);
/*     */   }
/*     */   
/*     */   public static int calculateChatboxHeight(float scale) {
/* 643 */     int i = 180;
/* 644 */     int j = 20;
/* 645 */     return MathHelper.floor_float(scale * 160.0F + 20.0F);
/*     */   }
/*     */   
/*     */   public int getLineCount() {
/* 649 */     return getChatHeight() / 9;
/*     */   }
/*     */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\minecraft\client\gui\GuiNewChat.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */