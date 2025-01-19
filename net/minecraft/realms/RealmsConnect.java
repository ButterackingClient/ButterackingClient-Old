/*    */ package net.minecraft.realms;
/*    */ import java.net.InetAddress;
/*    */ import java.net.UnknownHostException;
/*    */ import net.minecraft.client.Minecraft;
/*    */ import net.minecraft.client.gui.GuiScreen;
/*    */ import net.minecraft.client.network.NetHandlerLoginClient;
/*    */ import net.minecraft.network.EnumConnectionState;
/*    */ import net.minecraft.network.INetHandler;
/*    */ import net.minecraft.network.NetworkManager;
/*    */ import net.minecraft.network.Packet;
/*    */ import net.minecraft.network.login.client.C00PacketLoginStart;
/*    */ import net.minecraft.util.ChatComponentTranslation;
/*    */ import net.minecraft.util.IChatComponent;
/*    */ import org.apache.logging.log4j.Logger;
/*    */ 
/*    */ public class RealmsConnect {
/* 17 */   private static final Logger LOGGER = LogManager.getLogger();
/*    */   private final RealmsScreen onlineScreen;
/*    */   private volatile boolean aborted = false;
/*    */   private NetworkManager connection;
/*    */   
/*    */   public RealmsConnect(RealmsScreen p_i1079_1_) {
/* 23 */     this.onlineScreen = p_i1079_1_;
/*    */   }
/*    */   
/*    */   public void connect(final String p_connect_1_, final int p_connect_2_) {
/* 27 */     Realms.setConnectedToRealms(true);
/* 28 */     (new Thread("Realms-connect-task") {
/*    */         public void run() {
/* 30 */           InetAddress inetaddress = null;
/*    */           
/*    */           try {
/* 33 */             inetaddress = InetAddress.getByName(p_connect_1_);
/*    */             
/* 35 */             if (RealmsConnect.this.aborted) {
/*    */               return;
/*    */             }
/*    */             
/* 39 */             RealmsConnect.this.connection = NetworkManager.createNetworkManagerAndConnect(inetaddress, p_connect_2_, (Minecraft.getMinecraft()).gameSettings.isUsingNativeTransport());
/*    */             
/* 41 */             if (RealmsConnect.this.aborted) {
/*    */               return;
/*    */             }
/*    */             
/* 45 */             RealmsConnect.this.connection.setNetHandler((INetHandler)new NetHandlerLoginClient(RealmsConnect.this.connection, Minecraft.getMinecraft(), (GuiScreen)RealmsConnect.this.onlineScreen.getProxy()));
/*    */             
/* 47 */             if (RealmsConnect.this.aborted) {
/*    */               return;
/*    */             }
/*    */             
/* 51 */             RealmsConnect.this.connection.sendPacket((Packet)new C00Handshake(47, p_connect_1_, p_connect_2_, EnumConnectionState.LOGIN));
/*    */             
/* 53 */             if (RealmsConnect.this.aborted) {
/*    */               return;
/*    */             }
/*    */             
/* 57 */             RealmsConnect.this.connection.sendPacket((Packet)new C00PacketLoginStart(Minecraft.getMinecraft().getSession().getProfile()));
/* 58 */           } catch (UnknownHostException unknownhostexception) {
/* 59 */             Realms.clearResourcePack();
/*    */             
/* 61 */             if (RealmsConnect.this.aborted) {
/*    */               return;
/*    */             }
/*    */             
/* 65 */             RealmsConnect.LOGGER.error("Couldn't connect to world", unknownhostexception);
/* 66 */             Minecraft.getMinecraft().getResourcePackRepository().clearResourcePack();
/* 67 */             Realms.setScreen(new DisconnectedRealmsScreen(RealmsConnect.this.onlineScreen, "connect.failed", (IChatComponent)new ChatComponentTranslation("disconnect.genericReason", new Object[] { "Unknown host '" + this.val$p_connect_1_ + "'" })));
/* 68 */           } catch (Exception exception) {
/* 69 */             Realms.clearResourcePack();
/*    */             
/* 71 */             if (RealmsConnect.this.aborted) {
/*    */               return;
/*    */             }
/*    */             
/* 75 */             RealmsConnect.LOGGER.error("Couldn't connect to world", exception);
/* 76 */             String s = exception.toString();
/*    */             
/* 78 */             if (inetaddress != null) {
/* 79 */               String s1 = String.valueOf(inetaddress.toString()) + ":" + p_connect_2_;
/* 80 */               s = s.replaceAll(s1, "");
/*    */             } 
/*    */             
/* 83 */             Realms.setScreen(new DisconnectedRealmsScreen(RealmsConnect.this.onlineScreen, "connect.failed", (IChatComponent)new ChatComponentTranslation("disconnect.genericReason", new Object[] { s })));
/*    */           } 
/*    */         }
/* 86 */       }).start();
/*    */   }
/*    */   
/*    */   public void abort() {
/* 90 */     this.aborted = true;
/*    */   }
/*    */   
/*    */   public void tick() {
/* 94 */     if (this.connection != null)
/* 95 */       if (this.connection.isChannelOpen()) {
/* 96 */         this.connection.processReceivedPackets();
/*    */       } else {
/* 98 */         this.connection.checkDisconnected();
/*    */       }  
/*    */   }
/*    */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\minecraft\realms\RealmsConnect.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */