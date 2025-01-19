/*     */ package net.minecraft.client.multiplayer;
/*     */ 
/*     */ import client.Client;
/*     */ import java.io.IOException;
/*     */ import java.net.InetAddress;
/*     */ import java.net.UnknownHostException;
/*     */ import java.util.concurrent.atomic.AtomicInteger;
/*     */ import net.minecraft.client.Minecraft;
/*     */ import net.minecraft.client.gui.GuiButton;
/*     */ import net.minecraft.client.gui.GuiDisconnected;
/*     */ import net.minecraft.client.gui.GuiScreen;
/*     */ import net.minecraft.client.network.NetHandlerLoginClient;
/*     */ import net.minecraft.client.resources.I18n;
/*     */ import net.minecraft.network.EnumConnectionState;
/*     */ import net.minecraft.network.INetHandler;
/*     */ import net.minecraft.network.NetworkManager;
/*     */ import net.minecraft.network.Packet;
/*     */ import net.minecraft.network.handshake.client.C00Handshake;
/*     */ import net.minecraft.network.login.client.C00PacketLoginStart;
/*     */ import net.minecraft.util.ChatComponentText;
/*     */ import net.minecraft.util.ChatComponentTranslation;
/*     */ import net.minecraft.util.IChatComponent;
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
/*     */ public class GuiConnecting
/*     */   extends GuiScreen
/*     */ {
/* 203 */   private static final AtomicInteger CONNECTION_ID = new AtomicInteger(0);
/* 204 */   private static final Logger logger = LogManager.getLogger();
/*     */   private NetworkManager networkManager;
/*     */   
/*     */   public GuiConnecting(GuiScreen p_i1181_1_, Minecraft mcIn, ServerData p_i1181_3_) {
/* 208 */     this.f = false;
/* 209 */     this.mc = mcIn;
/* 210 */     this.previousGuiScreen = p_i1181_1_;
/* 211 */     ServerAddress serveraddress = ServerAddress.fromString(p_i1181_3_.serverIP);
/* 212 */     mcIn.loadWorld(null);
/* 213 */     mcIn.setServerData(p_i1181_3_);
/* 214 */     connect(serveraddress.getIP(), serveraddress.getPort());
/*     */   }
/*     */   private boolean cancel; private final GuiScreen previousGuiScreen;
/*     */   public GuiConnecting(GuiScreen p_i1182_1_, Minecraft mcIn, String hostName, int port) {
/* 218 */     this.mc = mcIn;
/* 219 */     this.previousGuiScreen = p_i1182_1_;
/* 220 */     mcIn.loadWorld(null);
/* 221 */     connect(hostName, port);
/*     */   }
/*     */   
/*     */   private void connect(final String ip, final int port) {
/* 225 */     logger.info("Connecting to " + ip + ", " + port);
/* 226 */     (new Thread("Server Connector #" + CONNECTION_ID.incrementAndGet())
/*     */       {
/*     */         public void run() {
/* 229 */           InetAddress inetaddress = null;
/*     */           try {
/* 231 */             if (GuiConnecting.this.cancel) {
/*     */               return;
/*     */             }
/* 234 */             inetaddress = InetAddress.getByName(ip);
/* 235 */             GuiConnecting.this.networkManager = NetworkManager.createNetworkManagerAndConnect(inetaddress, port, GuiConnecting.this.mc.gameSettings.isUsingNativeTransport());
/* 236 */             GuiConnecting.this.networkManager.setNetHandler((INetHandler)new NetHandlerLoginClient(GuiConnecting.this.networkManager, GuiConnecting.this.mc, GuiConnecting.this.previousGuiScreen));
/* 237 */             GuiConnecting.this.networkManager.sendPacket((Packet)new C00Handshake(47, ip, port, EnumConnectionState.LOGIN));
/* 238 */             GuiConnecting.this.networkManager.sendPacket((Packet)new C00PacketLoginStart(GuiConnecting.this.mc.getSession().getProfile()));
/* 239 */             Client.getInstance().enterMpWorld();
/* 240 */           } catch (UnknownHostException unknownhostexception) {
/* 241 */             if (GuiConnecting.this.cancel) {
/*     */               return;
/*     */             }
/* 244 */             GuiConnecting.logger.error("Couldn't connect to server", unknownhostexception);
/* 245 */             GuiConnecting.this.mc.displayGuiScreen((GuiScreen)new GuiDisconnected(GuiConnecting.this.previousGuiScreen, "connect.failed", (IChatComponent)new ChatComponentTranslation("disconnect.genericReason", new Object[] { "Unknown host" })));
/* 246 */           } catch (Exception exception) {
/* 247 */             if (GuiConnecting.this.cancel) {
/*     */               return;
/*     */             }
/* 250 */             GuiConnecting.logger.error("Couldn't connect to server", exception);
/* 251 */             String s = exception.toString();
/* 252 */             if (inetaddress != null) {
/* 253 */               String s2 = String.valueOf(String.valueOf(inetaddress.toString())) + ":" + port;
/* 254 */               s = s.replaceAll(s2, "");
/*     */             } 
/* 256 */             GuiConnecting.this.mc.displayGuiScreen((GuiScreen)new GuiDisconnected(GuiConnecting.this.previousGuiScreen, "connect.failed", (IChatComponent)new ChatComponentTranslation("disconnect.genericReason", new Object[] { s })));
/*     */           } 
/*     */         }
/* 259 */       }).start();
/*     */   }
/*     */ 
/*     */   
/*     */   public void updateScreen() {
/* 264 */     if (this.networkManager != null) {
/* 265 */       if (this.networkManager.isChannelOpen()) {
/* 266 */         this.networkManager.processReceivedPackets();
/*     */       } else {
/* 268 */         this.networkManager.checkDisconnected();
/*     */       } 
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected void keyTyped(char typedChar, int keyCode) throws IOException {}
/*     */ 
/*     */   
/*     */   public void initGui() {
/* 279 */     this.buttonList.clear();
/* 280 */     this.buttonList.add(new GuiButton(0, width / 2 - 100, height / 4 + 120 + 12, I18n.format("gui.cancel", new Object[0])));
/*     */   }
/*     */ 
/*     */   
/*     */   protected void actionPerformed(GuiButton button) throws IOException {
/* 285 */     if (button.id == 0) {
/* 286 */       this.cancel = true;
/* 287 */       if (this.networkManager != null) {
/* 288 */         this.networkManager.closeChannel((IChatComponent)new ChatComponentText("Aborted"));
/*     */       }
/* 290 */       this.mc.displayGuiScreen(this.previousGuiScreen);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void drawScreen(int mouseX, int mouseY, float partialTicks) {
/* 296 */     drawDefaultBackground();
/* 297 */     if (this.networkManager == null) {
/* 298 */       drawCenteredString(this.fontRendererObj, I18n.format("connect.connecting", new Object[0]), width / 2, height / 2 - 50, 16777215);
/*     */     } else {
/* 300 */       drawCenteredString(this.fontRendererObj, I18n.format("connect.authorizing", new Object[0]), width / 2, height / 2 - 50, 16777215);
/*     */     } 
/* 302 */     super.drawScreen(mouseX, mouseY, partialTicks);
/*     */   }
/*     */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\minecraft\client\multiplayer\GuiConnecting.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */