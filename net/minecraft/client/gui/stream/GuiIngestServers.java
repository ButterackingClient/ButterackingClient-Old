/*     */ package net.minecraft.client.gui.stream;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import net.minecraft.client.Minecraft;
/*     */ import net.minecraft.client.gui.FontRenderer;
/*     */ import net.minecraft.client.gui.GuiButton;
/*     */ import net.minecraft.client.gui.GuiScreen;
/*     */ import net.minecraft.client.gui.GuiSlot;
/*     */ import net.minecraft.client.resources.I18n;
/*     */ import net.minecraft.client.stream.IngestServerTester;
/*     */ import net.minecraft.util.EnumChatFormatting;
/*     */ import tv.twitch.broadcast.IngestServer;
/*     */ 
/*     */ public class GuiIngestServers extends GuiScreen {
/*     */   private final GuiScreen field_152309_a;
/*     */   private String field_152310_f;
/*     */   private ServerList field_152311_g;
/*     */   
/*     */   public GuiIngestServers(GuiScreen p_i46312_1_) {
/*  20 */     this.field_152309_a = p_i46312_1_;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void initGui() {
/*  28 */     this.field_152310_f = I18n.format("options.stream.ingest.title", new Object[0]);
/*  29 */     this.field_152311_g = new ServerList(this.mc);
/*     */     
/*  31 */     if (!this.mc.getTwitchStream().func_152908_z()) {
/*  32 */       this.mc.getTwitchStream().func_152909_x();
/*     */     }
/*     */     
/*  35 */     this.buttonList.add(new GuiButton(1, width / 2 - 155, height - 24 - 6, 150, 20, I18n.format("gui.done", new Object[0])));
/*  36 */     this.buttonList.add(new GuiButton(2, width / 2 + 5, height - 24 - 6, 150, 20, I18n.format("options.stream.ingest.reset", new Object[0])));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void handleMouseInput() throws IOException {
/*  43 */     super.handleMouseInput();
/*  44 */     this.field_152311_g.handleMouseInput();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void onGuiClosed() {
/*  51 */     if (this.mc.getTwitchStream().func_152908_z()) {
/*  52 */       this.mc.getTwitchStream().func_152932_y().func_153039_l();
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void actionPerformed(GuiButton button) throws IOException {
/*  60 */     if (button.enabled) {
/*  61 */       if (button.id == 1) {
/*  62 */         this.mc.displayGuiScreen(this.field_152309_a);
/*     */       } else {
/*  64 */         this.mc.gameSettings.streamPreferredServer = "";
/*  65 */         this.mc.gameSettings.saveOptions();
/*     */       } 
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void drawScreen(int mouseX, int mouseY, float partialTicks) {
/*  74 */     drawDefaultBackground();
/*  75 */     this.field_152311_g.drawScreen(mouseX, mouseY, partialTicks);
/*  76 */     drawCenteredString(this.fontRendererObj, this.field_152310_f, width / 2, 20, 16777215);
/*  77 */     super.drawScreen(mouseX, mouseY, partialTicks);
/*     */   }
/*     */   
/*     */   class ServerList extends GuiSlot {
/*     */     public ServerList(Minecraft mcIn) {
/*  82 */       super(mcIn, GuiIngestServers.width, GuiIngestServers.height, 32, GuiIngestServers.height - 35, (int)(mcIn.fontRendererObj.FONT_HEIGHT * 3.5D));
/*  83 */       setShowSelectionBox(false);
/*     */     }
/*     */     
/*     */     protected int getSize() {
/*  87 */       return (this.mc.getTwitchStream().func_152925_v()).length;
/*     */     }
/*     */     
/*     */     protected void elementClicked(int slotIndex, boolean isDoubleClick, int mouseX, int mouseY) {
/*  91 */       this.mc.gameSettings.streamPreferredServer = (this.mc.getTwitchStream().func_152925_v()[slotIndex]).serverUrl;
/*  92 */       this.mc.gameSettings.saveOptions();
/*     */     }
/*     */     
/*     */     protected boolean isSelected(int slotIndex) {
/*  96 */       return (this.mc.getTwitchStream().func_152925_v()[slotIndex]).serverUrl.equals(this.mc.gameSettings.streamPreferredServer);
/*     */     }
/*     */ 
/*     */     
/*     */     protected void drawBackground() {}
/*     */     
/*     */     protected void drawSlot(int entryID, int p_180791_2_, int p_180791_3_, int p_180791_4_, int mouseXIn, int mouseYIn) {
/* 103 */       IngestServer ingestserver = this.mc.getTwitchStream().func_152925_v()[entryID];
/* 104 */       String s = ingestserver.serverUrl.replaceAll("\\{stream_key\\}", "");
/* 105 */       String s1 = String.valueOf((int)ingestserver.bitrateKbps) + " kbps";
/* 106 */       String s2 = null;
/* 107 */       IngestServerTester ingestservertester = this.mc.getTwitchStream().func_152932_y();
/*     */       
/* 109 */       if (ingestservertester != null) {
/* 110 */         if (ingestserver == ingestservertester.func_153040_c()) {
/* 111 */           s = EnumChatFormatting.GREEN + s;
/* 112 */           s1 = String.valueOf((int)(ingestservertester.func_153030_h() * 100.0F)) + "%";
/* 113 */         } else if (entryID < ingestservertester.func_153028_p()) {
/* 114 */           if (ingestserver.bitrateKbps == 0.0F) {
/* 115 */             s1 = EnumChatFormatting.RED + "Down!";
/*     */           }
/*     */         } else {
/* 118 */           s1 = EnumChatFormatting.OBFUSCATED + "1234" + EnumChatFormatting.RESET + " kbps";
/*     */         } 
/* 120 */       } else if (ingestserver.bitrateKbps == 0.0F) {
/* 121 */         s1 = EnumChatFormatting.RED + "Down!";
/*     */       } 
/*     */       
/* 124 */       p_180791_2_ -= 15;
/*     */       
/* 126 */       if (isSelected(entryID)) {
/* 127 */         s2 = EnumChatFormatting.BLUE + "(Preferred)";
/* 128 */       } else if (ingestserver.defaultServer) {
/* 129 */         s2 = EnumChatFormatting.GREEN + "(Default)";
/*     */       } 
/*     */       
/* 132 */       GuiIngestServers.this.drawString(GuiIngestServers.this.fontRendererObj, ingestserver.serverName, p_180791_2_ + 2, p_180791_3_ + 5, 16777215);
/* 133 */       GuiIngestServers.this.drawString(GuiIngestServers.this.fontRendererObj, s, p_180791_2_ + 2, p_180791_3_ + GuiIngestServers.this.fontRendererObj.FONT_HEIGHT + 5 + 3, 3158064);
/* 134 */       GuiIngestServers.this.drawString(GuiIngestServers.this.fontRendererObj, s1, getScrollBarX() - 5 - GuiIngestServers.this.fontRendererObj.getStringWidth(s1), p_180791_3_ + 5, 8421504);
/*     */       
/* 136 */       if (s2 != null) {
/* 137 */         GuiIngestServers.this.drawString(GuiIngestServers.this.fontRendererObj, s2, getScrollBarX() - 5 - GuiIngestServers.this.fontRendererObj.getStringWidth(s2), p_180791_3_ + 5 + 3 + GuiIngestServers.this.fontRendererObj.FONT_HEIGHT, 8421504);
/*     */       }
/*     */     }
/*     */     
/*     */     protected int getScrollBarX() {
/* 142 */       return super.getScrollBarX() + 15;
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\minecraft\client\gui\stream\GuiIngestServers.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */