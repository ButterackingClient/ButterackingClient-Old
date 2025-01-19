/*     */ package net.minecraft.client.gui;
/*     */ 
/*     */ import client.Client;
/*     */ import com.google.common.base.Splitter;
/*     */ import com.google.common.collect.Lists;
/*     */ import java.io.IOException;
/*     */ import java.util.List;
/*     */ import net.minecraft.client.multiplayer.GuiConnecting;
/*     */ import net.minecraft.client.multiplayer.ServerData;
/*     */ import net.minecraft.client.multiplayer.ServerList;
/*     */ import net.minecraft.client.network.LanServerDetector;
/*     */ import net.minecraft.client.network.OldServerPinger;
/*     */ import net.minecraft.client.resources.I18n;
/*     */ import org.apache.logging.log4j.LogManager;
/*     */ import org.apache.logging.log4j.Logger;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class GuiMultiplayer
/*     */   extends GuiScreen
/*     */   implements GuiYesNoCallback
/*     */ {
/* 528 */   private static final Logger logger = LogManager.getLogger(); private final OldServerPinger oldServerPinger; private GuiScreen parentScreen; private ServerSelectionList serverListSelector; private ServerList savedServerList; private GuiButton btnEditServer; private GuiButton btnSelectServer; private GuiButton btnDeleteServer;
/*     */   private boolean deletingServer;
/*     */   
/*     */   public GuiMultiplayer(GuiScreen parentScreen) {
/* 532 */     this.oldServerPinger = new OldServerPinger();
/* 533 */     this.parentScreen = parentScreen;
/*     */   }
/*     */   private boolean addingServer; private boolean editingServer; private boolean directConnect; private String hoveringText; private ServerData selectedServer; private LanServerDetector.LanServerList lanServerList; private LanServerDetector.ThreadLanServerFind lanServerDetector; private boolean initialized;
/*     */   
/*     */   public void initGui() {
/* 538 */     Client.getInstance().getDiscordrp().update("Idle", "Multiplayer Menu");
/* 539 */     Keyboard.enableRepeatEvents(true);
/* 540 */     this.buttonList.clear();
/* 541 */     if (!this.initialized) {
/* 542 */       this.initialized = true;
/* 543 */       (this.savedServerList = new ServerList(this.mc)).loadServerList();
/* 544 */       this.lanServerList = new LanServerDetector.LanServerList();
/*     */       try {
/* 546 */         (this.lanServerDetector = new LanServerDetector.ThreadLanServerFind(this.lanServerList)).start();
/* 547 */       } catch (Exception exception) {
/* 548 */         logger.warn("Unable to start LAN server detection: " + exception.getMessage());
/*     */       } 
/* 550 */       (this.serverListSelector = new ServerSelectionList(this, this.mc, width, height, 32, height - 64, 36)).func_148195_a(this.savedServerList);
/*     */     } else {
/* 552 */       this.serverListSelector.setDimensions(width, height, 32, height - 64);
/*     */     } 
/* 554 */     createButtons();
/*     */   }
/*     */ 
/*     */   
/*     */   public void handleMouseInput() throws IOException {
/* 559 */     super.handleMouseInput();
/* 560 */     this.serverListSelector.handleMouseInput();
/*     */   }
/*     */   
/*     */   public void createButtons() {
/* 564 */     this.buttonList.add(this.btnEditServer = new GuiButton(7, width / 2 - 154, height - 28, 70, 20, I18n.format("selectServer.edit", new Object[0])));
/* 565 */     this.buttonList.add(this.btnDeleteServer = new GuiButton(2, width / 2 - 74, height - 28, 70, 20, I18n.format("selectServer.delete", new Object[0])));
/* 566 */     this.buttonList.add(this.btnSelectServer = new GuiButton(1, width / 2 - 154, height - 52, 100, 20, I18n.format("selectServer.select", new Object[0])));
/* 567 */     this.buttonList.add(new GuiButton(4, width / 2 - 50, height - 52, 100, 20, I18n.format("selectServer.direct", new Object[0])));
/* 568 */     this.buttonList.add(new GuiButton(3, width / 2 + 4 + 50, height - 52, 100, 20, I18n.format("selectServer.add", new Object[0])));
/* 569 */     this.buttonList.add(new GuiButton(8, width / 2 + 4, height - 28, 70, 20, I18n.format("selectServer.refresh", new Object[0])));
/* 570 */     this.buttonList.add(new GuiButton(0, width / 2 + 4 + 76, height - 28, 75, 20, I18n.format("gui.cancel", new Object[0])));
/* 571 */     selectServer(this.serverListSelector.func_148193_k());
/*     */   }
/*     */ 
/*     */   
/*     */   public void updateScreen() {
/* 576 */     super.updateScreen();
/* 577 */     if (this.lanServerList.getWasUpdated()) {
/* 578 */       List<LanServerDetector.LanServer> list = this.lanServerList.getLanServers();
/* 579 */       this.lanServerList.setWasNotUpdated();
/* 580 */       this.serverListSelector.func_148194_a(list);
/*     */     } 
/* 582 */     this.oldServerPinger.pingPendingNetworks();
/*     */   }
/*     */ 
/*     */   
/*     */   public void onGuiClosed() {
/* 587 */     Keyboard.enableRepeatEvents(false);
/* 588 */     if (this.lanServerDetector != null) {
/* 589 */       this.lanServerDetector.interrupt();
/* 590 */       this.lanServerDetector = null;
/*     */     } 
/* 592 */     this.oldServerPinger.clearPendingNetworks();
/*     */   }
/*     */ 
/*     */   
/*     */   protected void actionPerformed(GuiButton button) throws IOException {
/* 597 */     if (button.enabled) {
/* 598 */       GuiListExtended.IGuiListEntry guilistextended$iguilistentry = (this.serverListSelector.func_148193_k() < 0) ? null : this.serverListSelector.getListEntry(this.serverListSelector.func_148193_k());
/* 599 */       if (button.id == 2 && guilistextended$iguilistentry instanceof ServerListEntryNormal) {
/* 600 */         String s4 = (((ServerListEntryNormal)guilistextended$iguilistentry).getServerData()).serverName;
/* 601 */         if (s4 != null) {
/* 602 */           this.deletingServer = true;
/* 603 */           String s5 = I18n.format("selectServer.deleteQuestion", new Object[0]);
/* 604 */           String s6 = "'" + s4 + "' " + I18n.format("selectServer.deleteWarning", new Object[0]);
/* 605 */           String s7 = I18n.format("selectServer.deleteButton", new Object[0]);
/* 606 */           String s8 = I18n.format("gui.cancel", new Object[0]);
/* 607 */           GuiYesNo guiyesno = new GuiYesNo(this, s5, s6, s7, s8, this.serverListSelector.func_148193_k());
/* 608 */           this.mc.displayGuiScreen(guiyesno);
/*     */         } 
/* 610 */       } else if (button.id == 1) {
/* 611 */         connectToSelected();
/* 612 */       } else if (button.id == 4) {
/* 613 */         this.directConnect = true;
/* 614 */         this.mc.displayGuiScreen(new GuiScreenServerList(this, this.selectedServer = new ServerData(I18n.format("selectServer.defaultName", new Object[0]), "", false)));
/* 615 */       } else if (button.id == 3) {
/* 616 */         this.addingServer = true;
/* 617 */         this.mc.displayGuiScreen(new GuiScreenAddServer(this, this.selectedServer = new ServerData(I18n.format("selectServer.defaultName", new Object[0]), "", false)));
/* 618 */       } else if (button.id == 7 && guilistextended$iguilistentry instanceof ServerListEntryNormal) {
/* 619 */         this.editingServer = true;
/* 620 */         ServerData serverdata = ((ServerListEntryNormal)guilistextended$iguilistentry).getServerData();
/* 621 */         (this.selectedServer = new ServerData(serverdata.serverName, serverdata.serverIP, false)).copyFrom(serverdata);
/* 622 */         this.mc.displayGuiScreen(new GuiScreenAddServer(this, this.selectedServer));
/* 623 */       } else if (button.id == 0) {
/* 624 */         this.mc.displayGuiScreen(this.parentScreen);
/* 625 */       } else if (button.id == 8) {
/* 626 */         refreshServerList();
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   private void refreshServerList() {
/* 632 */     this.mc.displayGuiScreen(new GuiMultiplayer(this.parentScreen));
/*     */   }
/*     */ 
/*     */   
/*     */   public void confirmClicked(boolean result, int id) {
/* 637 */     GuiListExtended.IGuiListEntry guilistextended$iguilistentry = (this.serverListSelector.func_148193_k() < 0) ? null : this.serverListSelector.getListEntry(this.serverListSelector.func_148193_k());
/* 638 */     if (this.deletingServer) {
/* 639 */       this.deletingServer = false;
/* 640 */       if (result && guilistextended$iguilistentry instanceof ServerListEntryNormal) {
/* 641 */         this.savedServerList.removeServerData(this.serverListSelector.func_148193_k());
/* 642 */         this.savedServerList.saveServerList();
/* 643 */         this.serverListSelector.setSelectedSlotIndex(-1);
/* 644 */         this.serverListSelector.func_148195_a(this.savedServerList);
/*     */       } 
/* 646 */       this.mc.displayGuiScreen(this);
/* 647 */     } else if (this.directConnect) {
/* 648 */       this.directConnect = false;
/* 649 */       if (result) {
/* 650 */         connectToServer(this.selectedServer);
/*     */       } else {
/* 652 */         this.mc.displayGuiScreen(this);
/*     */       } 
/* 654 */     } else if (this.addingServer) {
/* 655 */       this.addingServer = false;
/* 656 */       if (result) {
/* 657 */         this.savedServerList.addServerData(this.selectedServer);
/* 658 */         this.savedServerList.saveServerList();
/* 659 */         this.serverListSelector.setSelectedSlotIndex(-1);
/* 660 */         this.serverListSelector.func_148195_a(this.savedServerList);
/*     */       } 
/* 662 */       this.mc.displayGuiScreen(this);
/* 663 */     } else if (this.editingServer) {
/* 664 */       this.editingServer = false;
/* 665 */       if (result && guilistextended$iguilistentry instanceof ServerListEntryNormal) {
/* 666 */         ServerData serverdata = ((ServerListEntryNormal)guilistextended$iguilistentry).getServerData();
/* 667 */         serverdata.serverName = this.selectedServer.serverName;
/* 668 */         serverdata.serverIP = this.selectedServer.serverIP;
/* 669 */         serverdata.copyFrom(this.selectedServer);
/* 670 */         this.savedServerList.saveServerList();
/* 671 */         this.serverListSelector.func_148195_a(this.savedServerList);
/*     */       } 
/* 673 */       this.mc.displayGuiScreen(this);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   protected void keyTyped(char typedChar, int keyCode) throws IOException {
/* 679 */     int i = this.serverListSelector.func_148193_k();
/* 680 */     GuiListExtended.IGuiListEntry guilistextended$iguilistentry = (i < 0) ? null : this.serverListSelector.getListEntry(i);
/* 681 */     if (keyCode == 63) {
/* 682 */       refreshServerList();
/* 683 */     } else if (i >= 0) {
/* 684 */       if (keyCode == 200) {
/* 685 */         if (isShiftKeyDown()) {
/* 686 */           if (i > 0 && guilistextended$iguilistentry instanceof ServerListEntryNormal) {
/* 687 */             this.savedServerList.swapServers(i, i - 1);
/* 688 */             selectServer(this.serverListSelector.func_148193_k() - 1);
/* 689 */             this.serverListSelector.scrollBy(-this.serverListSelector.getSlotHeight());
/* 690 */             this.serverListSelector.func_148195_a(this.savedServerList);
/*     */           } 
/* 692 */         } else if (i > 0) {
/* 693 */           selectServer(this.serverListSelector.func_148193_k() - 1);
/* 694 */           this.serverListSelector.scrollBy(-this.serverListSelector.getSlotHeight());
/* 695 */           if (this.serverListSelector.getListEntry(this.serverListSelector.func_148193_k()) instanceof ServerListEntryLanScan) {
/* 696 */             if (this.serverListSelector.func_148193_k() > 0) {
/* 697 */               selectServer(this.serverListSelector.getSize() - 1);
/* 698 */               this.serverListSelector.scrollBy(-this.serverListSelector.getSlotHeight());
/*     */             } else {
/* 700 */               selectServer(-1);
/*     */             } 
/*     */           }
/*     */         } else {
/* 704 */           selectServer(-1);
/*     */         } 
/* 706 */       } else if (keyCode == 208) {
/* 707 */         if (isShiftKeyDown()) {
/* 708 */           if (i < this.savedServerList.countServers() - 1) {
/* 709 */             this.savedServerList.swapServers(i, i + 1);
/* 710 */             selectServer(i + 1);
/* 711 */             this.serverListSelector.scrollBy(this.serverListSelector.getSlotHeight());
/* 712 */             this.serverListSelector.func_148195_a(this.savedServerList);
/*     */           } 
/* 714 */         } else if (i < this.serverListSelector.getSize()) {
/* 715 */           selectServer(this.serverListSelector.func_148193_k() + 1);
/* 716 */           this.serverListSelector.scrollBy(this.serverListSelector.getSlotHeight());
/* 717 */           if (this.serverListSelector.getListEntry(this.serverListSelector.func_148193_k()) instanceof ServerListEntryLanScan) {
/* 718 */             if (this.serverListSelector.func_148193_k() < this.serverListSelector.getSize() - 1) {
/* 719 */               selectServer(this.serverListSelector.getSize() + 1);
/* 720 */               this.serverListSelector.scrollBy(this.serverListSelector.getSlotHeight());
/*     */             } else {
/* 722 */               selectServer(-1);
/*     */             } 
/*     */           }
/*     */         } else {
/* 726 */           selectServer(-1);
/*     */         } 
/* 728 */       } else if (keyCode != 28 && keyCode != 156) {
/* 729 */         super.keyTyped(typedChar, keyCode);
/*     */       } else {
/* 731 */         actionPerformed(this.buttonList.get(2));
/*     */       } 
/*     */     } else {
/* 734 */       super.keyTyped(typedChar, keyCode);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void drawScreen(int mouseX, int mouseY, float partialTicks) {
/* 740 */     this.hoveringText = null;
/* 741 */     drawDefaultBackground();
/* 742 */     this.serverListSelector.drawScreen(mouseX, mouseY, partialTicks);
/* 743 */     drawCenteredString(this.fontRendererObj, I18n.format("multiplayer.title", new Object[0]), width / 2, 20, 16777215);
/* 744 */     super.drawScreen(mouseX, mouseY, partialTicks);
/* 745 */     if (this.hoveringText != null) {
/* 746 */       drawHoveringText(Lists.newArrayList(Splitter.on("\n").split(this.hoveringText)), mouseX, mouseY);
/*     */     }
/*     */   }
/*     */   
/*     */   public void connectToSelected() {
/* 751 */     GuiListExtended.IGuiListEntry guilistextended$iguilistentry = (this.serverListSelector.func_148193_k() < 0) ? null : this.serverListSelector.getListEntry(this.serverListSelector.func_148193_k());
/* 752 */     if (guilistextended$iguilistentry instanceof ServerListEntryNormal) {
/* 753 */       connectToServer(((ServerListEntryNormal)guilistextended$iguilistentry).getServerData());
/* 754 */     } else if (guilistextended$iguilistentry instanceof ServerListEntryLanDetected) {
/* 755 */       LanServerDetector.LanServer lanserverdetector$lanserver = ((ServerListEntryLanDetected)guilistextended$iguilistentry).getLanServer();
/* 756 */       connectToServer(new ServerData(lanserverdetector$lanserver.getServerMotd(), lanserverdetector$lanserver.getServerIpPort(), true));
/*     */     } 
/*     */   }
/*     */   
/*     */   private void connectToServer(ServerData server) {
/* 761 */     this.mc.displayGuiScreen((GuiScreen)new GuiConnecting(this, this.mc, server));
/*     */   }
/*     */   
/*     */   public void selectServer(int index) {
/* 765 */     this.serverListSelector.setSelectedSlotIndex(index);
/* 766 */     GuiListExtended.IGuiListEntry guilistextended$iguilistentry = (index < 0) ? null : this.serverListSelector.getListEntry(index);
/* 767 */     this.btnSelectServer.enabled = false;
/* 768 */     this.btnEditServer.enabled = false;
/* 769 */     this.btnDeleteServer.enabled = false;
/* 770 */     if (guilistextended$iguilistentry != null && !(guilistextended$iguilistentry instanceof ServerListEntryLanScan)) {
/* 771 */       this.btnSelectServer.enabled = true;
/* 772 */       if (guilistextended$iguilistentry instanceof ServerListEntryNormal) {
/* 773 */         this.btnEditServer.enabled = true;
/* 774 */         this.btnDeleteServer.enabled = true;
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   public OldServerPinger getOldServerPinger() {
/* 780 */     return this.oldServerPinger;
/*     */   }
/*     */   
/*     */   public void setHoveringText(String p_146793_1_) {
/* 784 */     this.hoveringText = p_146793_1_;
/*     */   }
/*     */ 
/*     */   
/*     */   protected void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException {
/* 789 */     super.mouseClicked(mouseX, mouseY, mouseButton);
/* 790 */     this.serverListSelector.mouseClicked(mouseX, mouseY, mouseButton);
/*     */   }
/*     */ 
/*     */   
/*     */   protected void mouseReleased(int mouseX, int mouseY, int state) {
/* 795 */     super.mouseReleased(mouseX, mouseY, state);
/* 796 */     this.serverListSelector.mouseReleased(mouseX, mouseY, state);
/*     */   }
/*     */   
/*     */   public ServerList getServerList() {
/* 800 */     return this.savedServerList;
/*     */   }
/*     */   
/*     */   public boolean func_175392_a(ServerListEntryNormal p_175392_1_, int p_175392_2_) {
/* 804 */     return (p_175392_2_ > 0);
/*     */   }
/*     */   
/*     */   public boolean func_175394_b(ServerListEntryNormal p_175394_1_, int p_175394_2_) {
/* 808 */     return (p_175394_2_ < this.savedServerList.countServers() - 1);
/*     */   }
/*     */   
/*     */   public void func_175391_a(ServerListEntryNormal p_175391_1_, int p_175391_2_, boolean p_175391_3_) {
/* 812 */     int i = p_175391_3_ ? 0 : (p_175391_2_ - 1);
/* 813 */     this.savedServerList.swapServers(p_175391_2_, i);
/* 814 */     if (this.serverListSelector.func_148193_k() == p_175391_2_) {
/* 815 */       selectServer(i);
/*     */     }
/* 817 */     this.serverListSelector.func_148195_a(this.savedServerList);
/*     */   }
/*     */   
/*     */   public void func_175393_b(ServerListEntryNormal p_175393_1_, int p_175393_2_, boolean p_175393_3_) {
/* 821 */     int i = p_175393_3_ ? (this.savedServerList.countServers() - 1) : (p_175393_2_ + 1);
/* 822 */     this.savedServerList.swapServers(p_175393_2_, i);
/* 823 */     if (this.serverListSelector.func_148193_k() == p_175393_2_) {
/* 824 */       selectServer(i);
/*     */     }
/* 826 */     this.serverListSelector.func_148195_a(this.savedServerList);
/*     */   }
/*     */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\minecraft\client\gui\GuiMultiplayer.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */