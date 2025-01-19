/*     */ package net.minecraft.server.network;
/*     */ 
/*     */ import com.google.common.base.Charsets;
/*     */ import com.mojang.authlib.GameProfile;
/*     */ import com.mojang.authlib.exceptions.AuthenticationUnavailableException;
/*     */ import io.netty.channel.ChannelFuture;
/*     */ import io.netty.channel.ChannelFutureListener;
/*     */ import io.netty.util.concurrent.Future;
/*     */ import io.netty.util.concurrent.GenericFutureListener;
/*     */ import java.math.BigInteger;
/*     */ import java.security.PrivateKey;
/*     */ import java.util.Arrays;
/*     */ import java.util.Random;
/*     */ import java.util.UUID;
/*     */ import java.util.concurrent.atomic.AtomicInteger;
/*     */ import javax.crypto.SecretKey;
/*     */ import net.minecraft.entity.player.EntityPlayerMP;
/*     */ import net.minecraft.network.NetworkManager;
/*     */ import net.minecraft.network.Packet;
/*     */ import net.minecraft.network.login.INetHandlerLoginServer;
/*     */ import net.minecraft.network.login.client.C00PacketLoginStart;
/*     */ import net.minecraft.network.login.client.C01PacketEncryptionResponse;
/*     */ import net.minecraft.network.login.server.S00PacketDisconnect;
/*     */ import net.minecraft.network.login.server.S01PacketEncryptionRequest;
/*     */ import net.minecraft.network.login.server.S02PacketLoginSuccess;
/*     */ import net.minecraft.network.login.server.S03PacketEnableCompression;
/*     */ import net.minecraft.server.MinecraftServer;
/*     */ import net.minecraft.util.ChatComponentText;
/*     */ import net.minecraft.util.CryptManager;
/*     */ import net.minecraft.util.IChatComponent;
/*     */ import net.minecraft.util.ITickable;
/*     */ import org.apache.commons.lang3.Validate;
/*     */ import org.apache.logging.log4j.LogManager;
/*     */ import org.apache.logging.log4j.Logger;
/*     */ 
/*     */ public class NetHandlerLoginServer implements INetHandlerLoginServer, ITickable {
/*  37 */   private static final AtomicInteger AUTHENTICATOR_THREAD_ID = new AtomicInteger(0);
/*  38 */   private static final Logger logger = LogManager.getLogger();
/*  39 */   private static final Random RANDOM = new Random();
/*  40 */   private final byte[] verifyToken = new byte[4];
/*     */   private final MinecraftServer server;
/*     */   public final NetworkManager networkManager;
/*  43 */   private LoginState currentLoginState = LoginState.HELLO;
/*     */ 
/*     */   
/*     */   private int connectionTimer;
/*     */   
/*     */   private GameProfile loginGameProfile;
/*     */   
/*  50 */   private String serverId = "";
/*     */   private SecretKey secretKey;
/*     */   private EntityPlayerMP player;
/*     */   
/*     */   public NetHandlerLoginServer(MinecraftServer serverIn, NetworkManager networkManagerIn) {
/*  55 */     this.server = serverIn;
/*  56 */     this.networkManager = networkManagerIn;
/*  57 */     RANDOM.nextBytes(this.verifyToken);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void update() {
/*  64 */     if (this.currentLoginState == LoginState.READY_TO_ACCEPT) {
/*  65 */       tryAcceptPlayer();
/*  66 */     } else if (this.currentLoginState == LoginState.DELAY_ACCEPT) {
/*  67 */       EntityPlayerMP entityplayermp = this.server.getConfigurationManager().getPlayerByUUID(this.loginGameProfile.getId());
/*     */       
/*  69 */       if (entityplayermp == null) {
/*  70 */         this.currentLoginState = LoginState.READY_TO_ACCEPT;
/*  71 */         this.server.getConfigurationManager().initializeConnectionToPlayer(this.networkManager, this.player);
/*  72 */         this.player = null;
/*     */       } 
/*     */     } 
/*     */     
/*  76 */     if (this.connectionTimer++ == 600) {
/*  77 */       closeConnection("Took too long to log in");
/*     */     }
/*     */   }
/*     */   
/*     */   public void closeConnection(String reason) {
/*     */     try {
/*  83 */       logger.info("Disconnecting " + getConnectionInfo() + ": " + reason);
/*  84 */       ChatComponentText chatcomponenttext = new ChatComponentText(reason);
/*  85 */       this.networkManager.sendPacket((Packet)new S00PacketDisconnect((IChatComponent)chatcomponenttext));
/*  86 */       this.networkManager.closeChannel((IChatComponent)chatcomponenttext);
/*  87 */     } catch (Exception exception) {
/*  88 */       logger.error("Error whilst disconnecting player", exception);
/*     */     } 
/*     */   }
/*     */   
/*     */   public void tryAcceptPlayer() {
/*  93 */     if (!this.loginGameProfile.isComplete()) {
/*  94 */       this.loginGameProfile = getOfflineProfile(this.loginGameProfile);
/*     */     }
/*     */     
/*  97 */     String s = this.server.getConfigurationManager().allowUserToConnect(this.networkManager.getRemoteAddress(), this.loginGameProfile);
/*     */     
/*  99 */     if (s != null) {
/* 100 */       closeConnection(s);
/*     */     } else {
/* 102 */       this.currentLoginState = LoginState.ACCEPTED;
/*     */       
/* 104 */       if (this.server.getNetworkCompressionTreshold() >= 0 && !this.networkManager.isLocalChannel()) {
/* 105 */         this.networkManager.sendPacket((Packet)new S03PacketEnableCompression(this.server.getNetworkCompressionTreshold()), (GenericFutureListener)new ChannelFutureListener() {
/*     */               public void operationComplete(ChannelFuture p_operationComplete_1_) throws Exception {
/* 107 */                 NetHandlerLoginServer.this.networkManager.setCompressionTreshold(NetHandlerLoginServer.this.server.getNetworkCompressionTreshold());
/*     */               }
/* 109 */             },  new GenericFutureListener[0]);
/*     */       }
/*     */       
/* 112 */       this.networkManager.sendPacket((Packet)new S02PacketLoginSuccess(this.loginGameProfile));
/* 113 */       EntityPlayerMP entityplayermp = this.server.getConfigurationManager().getPlayerByUUID(this.loginGameProfile.getId());
/*     */       
/* 115 */       if (entityplayermp != null) {
/* 116 */         this.currentLoginState = LoginState.DELAY_ACCEPT;
/* 117 */         this.player = this.server.getConfigurationManager().createPlayerForUser(this.loginGameProfile);
/*     */       } else {
/* 119 */         this.server.getConfigurationManager().initializeConnectionToPlayer(this.networkManager, this.server.getConfigurationManager().createPlayerForUser(this.loginGameProfile));
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void onDisconnect(IChatComponent reason) {
/* 128 */     logger.info(String.valueOf(getConnectionInfo()) + " lost connection: " + reason.getUnformattedText());
/*     */   }
/*     */   
/*     */   public String getConnectionInfo() {
/* 132 */     return (this.loginGameProfile != null) ? (String.valueOf(this.loginGameProfile.toString()) + " (" + this.networkManager.getRemoteAddress().toString() + ")") : String.valueOf(this.networkManager.getRemoteAddress());
/*     */   }
/*     */   
/*     */   public void processLoginStart(C00PacketLoginStart packetIn) {
/* 136 */     Validate.validState((this.currentLoginState == LoginState.HELLO), "Unexpected hello packet", new Object[0]);
/* 137 */     this.loginGameProfile = packetIn.getProfile();
/*     */     
/* 139 */     if (this.server.isServerInOnlineMode() && !this.networkManager.isLocalChannel()) {
/* 140 */       this.currentLoginState = LoginState.KEY;
/* 141 */       this.networkManager.sendPacket((Packet)new S01PacketEncryptionRequest(this.serverId, this.server.getKeyPair().getPublic(), this.verifyToken));
/*     */     } else {
/* 143 */       this.currentLoginState = LoginState.READY_TO_ACCEPT;
/*     */     } 
/*     */   }
/*     */   
/*     */   public void processEncryptionResponse(C01PacketEncryptionResponse packetIn) {
/* 148 */     Validate.validState((this.currentLoginState == LoginState.KEY), "Unexpected key packet", new Object[0]);
/* 149 */     PrivateKey privatekey = this.server.getKeyPair().getPrivate();
/*     */     
/* 151 */     if (!Arrays.equals(this.verifyToken, packetIn.getVerifyToken(privatekey))) {
/* 152 */       throw new IllegalStateException("Invalid nonce!");
/*     */     }
/* 154 */     this.secretKey = packetIn.getSecretKey(privatekey);
/* 155 */     this.currentLoginState = LoginState.AUTHENTICATING;
/* 156 */     this.networkManager.enableEncryption(this.secretKey);
/* 157 */     (new Thread("User Authenticator #" + AUTHENTICATOR_THREAD_ID.incrementAndGet()) {
/*     */         public void run() {
/* 159 */           GameProfile gameprofile = NetHandlerLoginServer.this.loginGameProfile;
/*     */           
/*     */           try {
/* 162 */             String s = (new BigInteger(CryptManager.getServerIdHash(NetHandlerLoginServer.this.serverId, NetHandlerLoginServer.this.server.getKeyPair().getPublic(), NetHandlerLoginServer.this.secretKey))).toString(16);
/* 163 */             NetHandlerLoginServer.this.loginGameProfile = NetHandlerLoginServer.this.server.getMinecraftSessionService().hasJoinedServer(new GameProfile(null, gameprofile.getName()), s);
/*     */             
/* 165 */             if (NetHandlerLoginServer.this.loginGameProfile != null) {
/* 166 */               NetHandlerLoginServer.logger.info("UUID of player " + NetHandlerLoginServer.this.loginGameProfile.getName() + " is " + NetHandlerLoginServer.this.loginGameProfile.getId());
/* 167 */               NetHandlerLoginServer.this.currentLoginState = NetHandlerLoginServer.LoginState.READY_TO_ACCEPT;
/* 168 */             } else if (NetHandlerLoginServer.this.server.isSinglePlayer()) {
/* 169 */               NetHandlerLoginServer.logger.warn("Failed to verify username but will let them in anyway!");
/* 170 */               NetHandlerLoginServer.this.loginGameProfile = NetHandlerLoginServer.this.getOfflineProfile(gameprofile);
/* 171 */               NetHandlerLoginServer.this.currentLoginState = NetHandlerLoginServer.LoginState.READY_TO_ACCEPT;
/*     */             } else {
/* 173 */               NetHandlerLoginServer.this.closeConnection("Failed to verify username!");
/* 174 */               NetHandlerLoginServer.logger.error("Username '" + NetHandlerLoginServer.this.loginGameProfile.getName() + "' tried to join with an invalid session");
/*     */             } 
/* 176 */           } catch (AuthenticationUnavailableException var3) {
/* 177 */             if (NetHandlerLoginServer.this.server.isSinglePlayer()) {
/* 178 */               NetHandlerLoginServer.logger.warn("Authentication servers are down but will let them in anyway!");
/* 179 */               NetHandlerLoginServer.this.loginGameProfile = NetHandlerLoginServer.this.getOfflineProfile(gameprofile);
/* 180 */               NetHandlerLoginServer.this.currentLoginState = NetHandlerLoginServer.LoginState.READY_TO_ACCEPT;
/*     */             } else {
/* 182 */               NetHandlerLoginServer.this.closeConnection("Authentication servers are down. Please try again later, sorry!");
/* 183 */               NetHandlerLoginServer.logger.error("Couldn't verify username because servers are unavailable");
/*     */             } 
/*     */           } 
/*     */         }
/* 187 */       }).start();
/*     */   }
/*     */ 
/*     */   
/*     */   protected GameProfile getOfflineProfile(GameProfile original) {
/* 192 */     UUID uuid = UUID.nameUUIDFromBytes(("OfflinePlayer:" + original.getName()).getBytes(Charsets.UTF_8));
/* 193 */     return new GameProfile(uuid, original.getName());
/*     */   }
/*     */   
/*     */   enum LoginState {
/* 197 */     HELLO,
/* 198 */     KEY,
/* 199 */     AUTHENTICATING,
/* 200 */     READY_TO_ACCEPT,
/* 201 */     DELAY_ACCEPT,
/* 202 */     ACCEPTED;
/*     */   }
/*     */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\minecraft\server\network\NetHandlerLoginServer.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */