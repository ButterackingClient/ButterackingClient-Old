/*     */ package net.minecraft.client.gui;
/*     */ 
/*     */ import client.Client;
/*     */ import com.google.common.base.Splitter;
/*     */ import com.google.common.collect.Lists;
/*     */ import com.google.common.collect.Sets;
/*     */ import java.awt.Toolkit;
/*     */ import java.awt.datatransfer.DataFlavor;
/*     */ import java.awt.datatransfer.StringSelection;
/*     */ import java.awt.datatransfer.Transferable;
/*     */ import java.io.File;
/*     */ import java.io.IOException;
/*     */ import java.net.URI;
/*     */ import java.net.URISyntaxException;
/*     */ import java.util.Arrays;
/*     */ import java.util.List;
/*     */ import java.util.Set;
/*     */ import net.minecraft.client.Minecraft;
/*     */ import net.minecraft.client.gui.stream.GuiTwitchUserMode;
/*     */ import net.minecraft.client.renderer.GlStateManager;
/*     */ import net.minecraft.client.renderer.RenderHelper;
/*     */ import net.minecraft.client.renderer.Tessellator;
/*     */ import net.minecraft.client.renderer.WorldRenderer;
/*     */ import net.minecraft.client.renderer.entity.RenderItem;
/*     */ import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
/*     */ import net.minecraft.entity.EntityList;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.event.ClickEvent;
/*     */ import net.minecraft.event.HoverEvent;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraft.nbt.JsonToNBT;
/*     */ import net.minecraft.nbt.NBTException;
/*     */ import net.minecraft.nbt.NBTTagCompound;
/*     */ import net.minecraft.stats.Achievement;
/*     */ import net.minecraft.stats.StatBase;
/*     */ import net.minecraft.stats.StatList;
/*     */ import net.minecraft.util.ChatComponentTranslation;
/*     */ import net.minecraft.util.EnumChatFormatting;
/*     */ import net.minecraft.util.IChatComponent;
/*     */ import org.apache.commons.lang3.StringUtils;
/*     */ import org.apache.logging.log4j.LogManager;
/*     */ import org.apache.logging.log4j.Logger;
/*     */ import org.lwjgl.input.Keyboard;
/*     */ import org.lwjgl.input.Mouse;
/*     */ import tv.twitch.chat.ChatUserInfo;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public abstract class GuiScreen
/*     */   extends Gui
/*     */   implements GuiYesNoCallback
/*     */ {
/*  54 */   private static final Logger LOGGER = LogManager.getLogger();
/*  55 */   private static final Set<String> PROTOCOLS = Sets.newHashSet((Object[])new String[] { "http", "https" });
/*  56 */   private static final Splitter NEWLINE_SPLITTER = Splitter.on('\n');
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean f = true;
/*     */ 
/*     */ 
/*     */   
/*     */   protected Minecraft mc;
/*     */ 
/*     */ 
/*     */   
/*     */   protected RenderItem itemRender;
/*     */ 
/*     */   
/*     */   public static int width;
/*     */ 
/*     */   
/*     */   public static int height;
/*     */ 
/*     */   
/*  77 */   protected List<GuiButton> buttonList = Lists.newArrayList();
/*  78 */   protected List<GuiLabel> labelList = Lists.newArrayList();
/*     */ 
/*     */   
/*     */   public boolean allowUserInput;
/*     */ 
/*     */   
/*     */   protected FontRenderer fontRendererObj;
/*     */ 
/*     */   
/*     */   private GuiButton selectedButton;
/*     */ 
/*     */   
/*     */   private int eventButton;
/*     */ 
/*     */   
/*     */   private long lastMouseEvent;
/*     */ 
/*     */   
/*     */   private int touchValue;
/*     */ 
/*     */   
/*     */   private URI clickedLinkURI;
/*     */ 
/*     */   
/*     */   public void drawScreen(int mouseX, int mouseY, float partialTicks) {
/* 103 */     for (int i = 0; i < this.buttonList.size(); i++) {
/* 104 */       ((GuiButton)this.buttonList.get(i)).drawButton(this.mc, mouseX, mouseY);
/*     */     }
/*     */ 
/*     */     
/* 108 */     for (int j = 0; j < this.labelList.size(); j++) {
/* 109 */       ((GuiLabel)this.labelList.get(j)).drawLabel(this.mc, mouseX, mouseY);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void keyTyped(char typedChar, int keyCode) throws IOException {
/* 118 */     if (keyCode == 1) {
/* 119 */       this.mc.displayGuiScreen(null);
/*     */       
/* 121 */       if (this.mc.currentScreen == null) {
/* 122 */         this.mc.setIngameFocus();
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static String getClipboardString() {
/*     */     try {
/* 132 */       Transferable transferable = Toolkit.getDefaultToolkit().getSystemClipboard().getContents(null);
/*     */       
/* 134 */       if (transferable != null && transferable.isDataFlavorSupported(DataFlavor.stringFlavor)) {
/* 135 */         return (String)transferable.getTransferData(DataFlavor.stringFlavor);
/*     */       }
/* 137 */     } catch (Exception exception) {}
/*     */ 
/*     */ 
/*     */     
/* 141 */     return "";
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void setClipboardString(String copyText) {
/* 148 */     if (!StringUtils.isEmpty(copyText)) {
/*     */       try {
/* 150 */         StringSelection stringselection = new StringSelection(copyText);
/* 151 */         Toolkit.getDefaultToolkit().getSystemClipboard().setContents(stringselection, null);
/* 152 */       } catch (Exception exception) {}
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected void renderToolTip(ItemStack stack, int x, int y) {
/* 159 */     List<String> list = stack.getTooltip((EntityPlayer)this.mc.thePlayer, this.mc.gameSettings.advancedItemTooltips);
/*     */     
/* 161 */     for (int i = 0; i < list.size(); i++) {
/* 162 */       if (i == 0) {
/* 163 */         list.set(i, (stack.getRarity()).rarityColor + (String)list.get(i));
/*     */       } else {
/* 165 */         list.set(i, EnumChatFormatting.GRAY + (String)list.get(i));
/*     */       } 
/*     */     } 
/*     */     
/* 169 */     drawHoveringText(list, x, y);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void drawCreativeTabHoveringText(String tabName, int mouseX, int mouseY) {
/* 177 */     drawHoveringText(Arrays.asList(new String[] { tabName }, ), mouseX, mouseY);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void drawHoveringText(List<String> textLines, int x, int y) {
/* 184 */     if (!textLines.isEmpty()) {
/* 185 */       GlStateManager.disableRescaleNormal();
/* 186 */       RenderHelper.disableStandardItemLighting();
/* 187 */       GlStateManager.disableLighting();
/* 188 */       GlStateManager.disableDepth();
/* 189 */       int i = 0;
/*     */       
/* 191 */       for (String s : textLines) {
/* 192 */         int j = this.fontRendererObj.getStringWidth(s);
/*     */         
/* 194 */         if (j > i) {
/* 195 */           i = j;
/*     */         }
/*     */       } 
/*     */       
/* 199 */       int l1 = x + 12;
/* 200 */       int i2 = y - 12;
/* 201 */       int k = 8;
/*     */       
/* 203 */       if (textLines.size() > 1) {
/* 204 */         k += 2 + (textLines.size() - 1) * 10;
/*     */       }
/*     */       
/* 207 */       if (l1 + i > width) {
/* 208 */         l1 -= 28 + i;
/*     */       }
/*     */       
/* 211 */       if (i2 + k + 6 > height) {
/* 212 */         i2 = height - k - 6;
/*     */       }
/*     */       
/* 215 */       this.zLevel = 300.0F;
/* 216 */       this.itemRender.zLevel = 300.0F;
/* 217 */       int l = -267386864;
/* 218 */       drawGradientRect(l1 - 3, i2 - 4, l1 + i + 3, i2 - 3, l, l);
/* 219 */       drawGradientRect(l1 - 3, i2 + k + 3, l1 + i + 3, i2 + k + 4, l, l);
/* 220 */       drawGradientRect(l1 - 3, i2 - 3, l1 + i + 3, i2 + k + 3, l, l);
/* 221 */       drawGradientRect(l1 - 4, i2 - 3, l1 - 3, i2 + k + 3, l, l);
/* 222 */       drawGradientRect(l1 + i + 3, i2 - 3, l1 + i + 4, i2 + k + 3, l, l);
/* 223 */       int i1 = 1347420415;
/* 224 */       int j1 = (i1 & 0xFEFEFE) >> 1 | i1 & 0xFF000000;
/* 225 */       drawGradientRect(l1 - 3, i2 - 3 + 1, l1 - 3 + 1, i2 + k + 3 - 1, i1, j1);
/* 226 */       drawGradientRect(l1 + i + 2, i2 - 3 + 1, l1 + i + 3, i2 + k + 3 - 1, i1, j1);
/* 227 */       drawGradientRect(l1 - 3, i2 - 3, l1 + i + 3, i2 - 3 + 1, i1, i1);
/* 228 */       drawGradientRect(l1 - 3, i2 + k + 2, l1 + i + 3, i2 + k + 3, j1, j1);
/*     */       
/* 230 */       for (int k1 = 0; k1 < textLines.size(); k1++) {
/* 231 */         String s1 = textLines.get(k1);
/* 232 */         this.fontRendererObj.drawStringWithShadow(s1, l1, i2, -1);
/*     */         
/* 234 */         if (k1 == 0) {
/* 235 */           i2 += 2;
/*     */         }
/*     */         
/* 238 */         i2 += 10;
/*     */       } 
/*     */       
/* 241 */       this.zLevel = 0.0F;
/* 242 */       this.itemRender.zLevel = 0.0F;
/* 243 */       GlStateManager.enableLighting();
/* 244 */       GlStateManager.enableDepth();
/* 245 */       RenderHelper.enableStandardItemLighting();
/* 246 */       GlStateManager.enableRescaleNormal();
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void handleComponentHover(IChatComponent component, int x, int y) {
/* 258 */     if (component != null && component.getChatStyle().getChatHoverEvent() != null) {
/* 259 */       HoverEvent hoverevent = component.getChatStyle().getChatHoverEvent();
/*     */       
/* 261 */       if (hoverevent.getAction() == HoverEvent.Action.SHOW_ITEM) {
/* 262 */         ItemStack itemstack = null;
/*     */         
/*     */         try {
/* 265 */           NBTTagCompound nBTTagCompound = JsonToNBT.getTagFromJson(hoverevent.getValue().getUnformattedText());
/*     */           
/* 267 */           if (nBTTagCompound instanceof NBTTagCompound) {
/* 268 */             itemstack = ItemStack.loadItemStackFromNBT(nBTTagCompound);
/*     */           }
/* 270 */         } catch (NBTException nBTException) {}
/*     */ 
/*     */ 
/*     */         
/* 274 */         if (itemstack != null) {
/* 275 */           renderToolTip(itemstack, x, y);
/*     */         } else {
/* 277 */           drawCreativeTabHoveringText(EnumChatFormatting.RED + "Invalid Item!", x, y);
/*     */         } 
/* 279 */       } else if (hoverevent.getAction() == HoverEvent.Action.SHOW_ENTITY) {
/* 280 */         if (this.mc.gameSettings.advancedItemTooltips) {
/*     */           try {
/* 282 */             NBTTagCompound nBTTagCompound = JsonToNBT.getTagFromJson(hoverevent.getValue().getUnformattedText());
/*     */             
/* 284 */             if (nBTTagCompound instanceof NBTTagCompound) {
/* 285 */               List<String> list1 = Lists.newArrayList();
/* 286 */               NBTTagCompound nbttagcompound = nBTTagCompound;
/* 287 */               list1.add(nbttagcompound.getString("name"));
/*     */               
/* 289 */               if (nbttagcompound.hasKey("type", 8)) {
/* 290 */                 String s = nbttagcompound.getString("type");
/* 291 */                 list1.add("Type: " + s + " (" + EntityList.getIDFromString(s) + ")");
/*     */               } 
/*     */               
/* 294 */               list1.add(nbttagcompound.getString("id"));
/* 295 */               drawHoveringText(list1, x, y);
/*     */             } else {
/* 297 */               drawCreativeTabHoveringText(EnumChatFormatting.RED + "Invalid Entity!", x, y);
/*     */             } 
/* 299 */           } catch (NBTException var10) {
/* 300 */             drawCreativeTabHoveringText(EnumChatFormatting.RED + "Invalid Entity!", x, y);
/*     */           } 
/*     */         }
/* 303 */       } else if (hoverevent.getAction() == HoverEvent.Action.SHOW_TEXT) {
/* 304 */         drawHoveringText(NEWLINE_SPLITTER.splitToList(hoverevent.getValue().getFormattedText()), x, y);
/* 305 */       } else if (hoverevent.getAction() == HoverEvent.Action.SHOW_ACHIEVEMENT) {
/* 306 */         StatBase statbase = StatList.getOneShotStat(hoverevent.getValue().getUnformattedText());
/*     */         
/* 308 */         if (statbase != null) {
/* 309 */           IChatComponent ichatcomponent = statbase.getStatName();
/* 310 */           ChatComponentTranslation chatComponentTranslation = new ChatComponentTranslation("stats.tooltip.type." + (statbase.isAchievement() ? "achievement" : "statistic"), new Object[0]);
/* 311 */           chatComponentTranslation.getChatStyle().setItalic(Boolean.valueOf(true));
/* 312 */           String s1 = (statbase instanceof Achievement) ? ((Achievement)statbase).getDescription() : null;
/* 313 */           List<String> list = Lists.newArrayList((Object[])new String[] { ichatcomponent.getFormattedText(), chatComponentTranslation.getFormattedText() });
/*     */           
/* 315 */           if (s1 != null) {
/* 316 */             list.addAll(this.fontRendererObj.listFormattedStringToWidth(s1, 150));
/*     */           }
/*     */           
/* 319 */           drawHoveringText(list, x, y);
/*     */         } else {
/* 321 */           drawCreativeTabHoveringText(EnumChatFormatting.RED + "Invalid statistic/achievement!", x, y);
/*     */         } 
/*     */       } 
/*     */       
/* 325 */       GlStateManager.disableLighting();
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void setText(String newChatText, boolean shouldOverwrite) {}
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected boolean handleComponentClick(IChatComponent component) {
/* 341 */     if (component == null) {
/* 342 */       return false;
/*     */     }
/* 344 */     ClickEvent clickevent = component.getChatStyle().getChatClickEvent();
/*     */     
/* 346 */     if (isShiftKeyDown()) {
/* 347 */       if (component.getChatStyle().getInsertion() != null) {
/* 348 */         setText(component.getChatStyle().getInsertion(), false);
/*     */       }
/* 350 */     } else if (clickevent != null) {
/* 351 */       if (clickevent.getAction() == ClickEvent.Action.OPEN_URL) {
/* 352 */         if (!this.mc.gameSettings.chatLinks) {
/* 353 */           return false;
/*     */         }
/*     */         
/*     */         try {
/* 357 */           URI uri = new URI(clickevent.getValue());
/* 358 */           String s = uri.getScheme();
/*     */           
/* 360 */           if (s == null) {
/* 361 */             throw new URISyntaxException(clickevent.getValue(), "Missing protocol");
/*     */           }
/*     */           
/* 364 */           if (!PROTOCOLS.contains(s.toLowerCase())) {
/* 365 */             throw new URISyntaxException(clickevent.getValue(), "Unsupported protocol: " + s.toLowerCase());
/*     */           }
/*     */           
/* 368 */           if (this.mc.gameSettings.chatLinksPrompt) {
/* 369 */             this.clickedLinkURI = uri;
/* 370 */             this.mc.displayGuiScreen(new GuiConfirmOpenLink(this, clickevent.getValue(), 31102009, false));
/*     */           } else {
/* 372 */             openWebLink(uri);
/*     */           } 
/* 374 */         } catch (URISyntaxException urisyntaxexception) {
/* 375 */           LOGGER.error("Can't open url for " + clickevent, urisyntaxexception);
/*     */         } 
/* 377 */       } else if (clickevent.getAction() == ClickEvent.Action.OPEN_FILE) {
/* 378 */         URI uri1 = (new File(clickevent.getValue())).toURI();
/* 379 */         openWebLink(uri1);
/* 380 */       } else if (clickevent.getAction() == ClickEvent.Action.SUGGEST_COMMAND) {
/* 381 */         setText(clickevent.getValue(), true);
/* 382 */       } else if (clickevent.getAction() == ClickEvent.Action.RUN_COMMAND) {
/* 383 */         sendChatMessage(clickevent.getValue(), false);
/* 384 */       } else if (clickevent.getAction() == ClickEvent.Action.TWITCH_USER_INFO) {
/* 385 */         ChatUserInfo chatuserinfo = this.mc.getTwitchStream().func_152926_a(clickevent.getValue());
/*     */         
/* 387 */         if (chatuserinfo != null) {
/* 388 */           this.mc.displayGuiScreen((GuiScreen)new GuiTwitchUserMode(this.mc.getTwitchStream(), chatuserinfo));
/*     */         } else {
/* 390 */           LOGGER.error("Tried to handle twitch user but couldn't find them!");
/*     */         } 
/*     */       } else {
/* 393 */         LOGGER.error("Don't know how to handle " + clickevent);
/*     */       } 
/*     */       
/* 396 */       return true;
/*     */     } 
/*     */     
/* 399 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void sendChatMessage(String msg) {
/* 407 */     sendChatMessage(msg, true);
/*     */   }
/*     */   
/*     */   public void sendChatMessage(String msg, boolean addToChat) {
/* 411 */     if (addToChat) {
/* 412 */       this.mc.ingameGUI.getChatGUI().addToSentMessages(msg);
/*     */     }
/*     */     
/* 415 */     this.mc.thePlayer.sendChatMessage(msg);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException {
/* 422 */     if (mouseButton == 0) {
/* 423 */       for (int i = 0; i < this.buttonList.size(); i++) {
/* 424 */         GuiButton guibutton = this.buttonList.get(i);
/*     */         
/* 426 */         if (guibutton.mousePressed(this.mc, mouseX, mouseY)) {
/* 427 */           this.selectedButton = guibutton;
/* 428 */           guibutton.playPressSound(this.mc.getSoundHandler());
/* 429 */           actionPerformed(guibutton);
/*     */         } 
/*     */       } 
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void mouseReleased(int mouseX, int mouseY, int state) {
/* 439 */     if (this.selectedButton != null && state == 0) {
/* 440 */       this.selectedButton.mouseReleased(mouseX, mouseY);
/* 441 */       this.selectedButton = null;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void mouseClickMove(int mouseX, int mouseY, int clickedMouseButton, long timeSinceLastClick) {}
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void actionPerformed(GuiButton button) throws IOException {}
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setWorldAndResolution(Minecraft mc, int width, int height) {
/* 463 */     this.mc = mc;
/* 464 */     this.itemRender = mc.getRenderItem();
/* 465 */     this.fontRendererObj = mc.fontRendererObj;
/* 466 */     GuiScreen.width = width;
/* 467 */     GuiScreen.height = height;
/* 468 */     this.buttonList.clear();
/* 469 */     initGui();
/*     */   }
/*     */   
/*     */   public void a(int p_a_1_, int p_a_2_) {
/* 473 */     width = p_a_1_;
/* 474 */     height = p_a_2_;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void initGui() {}
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void handleInput() throws IOException {
/* 488 */     if (Mouse.isCreated()) {
/* 489 */       while (Mouse.next()) {
/* 490 */         handleMouseInput();
/*     */       }
/*     */     }
/*     */     
/* 494 */     if (Keyboard.isCreated()) {
/* 495 */       while (Keyboard.next()) {
/* 496 */         handleKeyboardInput();
/*     */       }
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void handleMouseInput() throws IOException {
/* 505 */     int i = Mouse.getEventX() * width / this.mc.displayWidth;
/* 506 */     int j = height - Mouse.getEventY() * height / this.mc.displayHeight - 1;
/* 507 */     int k = Mouse.getEventButton();
/*     */     
/* 509 */     if (Mouse.getEventButtonState()) {
/* 510 */       if (this.mc.gameSettings.touchscreen && this.touchValue++ > 0) {
/*     */         return;
/*     */       }
/*     */       
/* 514 */       this.eventButton = k;
/* 515 */       this.lastMouseEvent = Minecraft.getSystemTime();
/* 516 */       mouseClicked(i, j, this.eventButton);
/* 517 */     } else if (k != -1) {
/* 518 */       if (this.mc.gameSettings.touchscreen && --this.touchValue > 0) {
/*     */         return;
/*     */       }
/*     */       
/* 522 */       this.eventButton = -1;
/* 523 */       mouseReleased(i, j, k);
/* 524 */     } else if (this.eventButton != -1 && this.lastMouseEvent > 0L) {
/* 525 */       long l = Minecraft.getSystemTime() - this.lastMouseEvent;
/* 526 */       mouseClickMove(i, j, this.eventButton, l);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void handleKeyboardInput() throws IOException {
/* 534 */     if (Keyboard.getEventKeyState()) {
/* 535 */       keyTyped(Keyboard.getEventCharacter(), Keyboard.getEventKey());
/*     */     }
/*     */     
/* 538 */     this.mc.dispatchKeypresses();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void updateScreen() {}
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void onGuiClosed() {}
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void drawDefaultBackground() {
/* 557 */     drawWorldBackground(0);
/*     */   }
/*     */   
/*     */   public void drawWorldBackground(int tint) {
/* 561 */     if ((Client.getInstance()).hudManager.NoInventoryBG.isEnabled() && 
/* 562 */       this.f) {
/*     */       return;
/*     */     }
/*     */     
/* 566 */     if (this.mc.theWorld != null) {
/* 567 */       drawGradientRect(0, 0, width, height, -1072689136, -804253680);
/*     */     } else {
/* 569 */       drawBackground(tint);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void drawBackground(int tint) {
/* 577 */     GlStateManager.disableLighting();
/* 578 */     GlStateManager.disableFog();
/* 579 */     Tessellator tessellator = Tessellator.getInstance();
/* 580 */     WorldRenderer worldrenderer = tessellator.getWorldRenderer();
/* 581 */     this.mc.getTextureManager().bindTexture(optionsBackground);
/* 582 */     GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
/* 583 */     float f = 32.0F;
/* 584 */     worldrenderer.begin(7, DefaultVertexFormats.POSITION_TEX_COLOR);
/* 585 */     worldrenderer.pos(0.0D, height, 0.0D).tex(0.0D, (height / 32.0F + tint)).color(64, 64, 64, 255).endVertex();
/* 586 */     worldrenderer.pos(width, height, 0.0D).tex((width / 32.0F), (height / 32.0F + tint)).color(64, 64, 64, 255).endVertex();
/* 587 */     worldrenderer.pos(width, 0.0D, 0.0D).tex((width / 32.0F), tint).color(64, 64, 64, 255).endVertex();
/* 588 */     worldrenderer.pos(0.0D, 0.0D, 0.0D).tex(0.0D, tint).color(64, 64, 64, 255).endVertex();
/* 589 */     tessellator.draw();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean doesGuiPauseGame() {
/* 596 */     return true;
/*     */   }
/*     */   
/*     */   public void confirmClicked(boolean result, int id) {
/* 600 */     if (id == 31102009) {
/* 601 */       if (result) {
/* 602 */         openWebLink(this.clickedLinkURI);
/*     */       }
/*     */       
/* 605 */       this.clickedLinkURI = null;
/* 606 */       this.mc.displayGuiScreen(this);
/*     */     } 
/*     */   }
/*     */   
/*     */   private void openWebLink(URI url) {
/*     */     try {
/* 612 */       Class<?> oclass = Class.forName("java.awt.Desktop");
/* 613 */       Object object = oclass.getMethod("getDesktop", new Class[0]).invoke(null, new Object[0]);
/* 614 */       oclass.getMethod("browse", new Class[] { URI.class }).invoke(object, new Object[] { url });
/* 615 */     } catch (Throwable throwable) {
/* 616 */       LOGGER.error("Couldn't open link", throwable);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static boolean isCtrlKeyDown() {
/* 624 */     return Minecraft.isRunningOnMac ? (!(!Keyboard.isKeyDown(219) && !Keyboard.isKeyDown(220))) : (!(!Keyboard.isKeyDown(29) && !Keyboard.isKeyDown(157)));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static boolean isShiftKeyDown() {
/* 631 */     return !(!Keyboard.isKeyDown(42) && !Keyboard.isKeyDown(54));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static boolean isAltKeyDown() {
/* 638 */     return !(!Keyboard.isKeyDown(56) && !Keyboard.isKeyDown(184));
/*     */   }
/*     */   
/*     */   public static boolean isKeyComboCtrlX(int keyID) {
/* 642 */     return (keyID == 45 && isCtrlKeyDown() && !isShiftKeyDown() && !isAltKeyDown());
/*     */   }
/*     */   
/*     */   public static boolean isKeyComboCtrlV(int keyID) {
/* 646 */     return (keyID == 47 && isCtrlKeyDown() && !isShiftKeyDown() && !isAltKeyDown());
/*     */   }
/*     */   
/*     */   public static boolean isKeyComboCtrlC(int keyID) {
/* 650 */     return (keyID == 46 && isCtrlKeyDown() && !isShiftKeyDown() && !isAltKeyDown());
/*     */   }
/*     */   
/*     */   public static boolean isKeyComboCtrlA(int keyID) {
/* 654 */     return (keyID == 30 && isCtrlKeyDown() && !isShiftKeyDown() && !isAltKeyDown());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void onResize(Minecraft mcIn, int w, int h) {
/* 664 */     setWorldAndResolution(mcIn, w, h);
/*     */   }
/*     */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\minecraft\client\gui\GuiScreen.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */