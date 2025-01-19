/*     */ package net.minecraft.client.network;
/*     */ 
/*     */ import com.google.common.collect.Lists;
/*     */ import java.io.IOException;
/*     */ import java.net.DatagramPacket;
/*     */ import java.net.InetAddress;
/*     */ import java.net.MulticastSocket;
/*     */ import java.net.SocketTimeoutException;
/*     */ import java.util.Collections;
/*     */ import java.util.List;
/*     */ import java.util.concurrent.atomic.AtomicInteger;
/*     */ import net.minecraft.client.Minecraft;
/*     */ import net.minecraft.client.multiplayer.ThreadLanServerPing;
/*     */ import org.apache.logging.log4j.LogManager;
/*     */ import org.apache.logging.log4j.Logger;
/*     */ 
/*     */ 
/*     */ public class LanServerDetector
/*     */ {
/*  20 */   private static final AtomicInteger field_148551_a = new AtomicInteger(0);
/*  21 */   private static final Logger logger = LogManager.getLogger();
/*     */   
/*     */   public static class LanServer {
/*     */     private String lanServerMotd;
/*     */     private String lanServerIpPort;
/*     */     private long timeLastSeen;
/*     */     
/*     */     public LanServer(String motd, String address) {
/*  29 */       this.lanServerMotd = motd;
/*  30 */       this.lanServerIpPort = address;
/*  31 */       this.timeLastSeen = Minecraft.getSystemTime();
/*     */     }
/*     */     
/*     */     public String getServerMotd() {
/*  35 */       return this.lanServerMotd;
/*     */     }
/*     */     
/*     */     public String getServerIpPort() {
/*  39 */       return this.lanServerIpPort;
/*     */     }
/*     */     
/*     */     public void updateLastSeen() {
/*  43 */       this.timeLastSeen = Minecraft.getSystemTime();
/*     */     } }
/*     */   public static class LanServerList { private List<LanServerDetector.LanServer> listOfLanServers;
/*     */     
/*     */     public LanServerList() {
/*  48 */       this.listOfLanServers = Lists.newArrayList();
/*     */     }
/*     */     boolean wasUpdated;
/*     */     public synchronized boolean getWasUpdated() {
/*  52 */       return this.wasUpdated;
/*     */     }
/*     */     
/*     */     public synchronized void setWasNotUpdated() {
/*  56 */       this.wasUpdated = false;
/*     */     }
/*     */     
/*     */     public synchronized List<LanServerDetector.LanServer> getLanServers() {
/*  60 */       return Collections.unmodifiableList(this.listOfLanServers);
/*     */     }
/*     */     
/*     */     public synchronized void func_77551_a(String p_77551_1_, InetAddress p_77551_2_) {
/*  64 */       String s = ThreadLanServerPing.getMotdFromPingResponse(p_77551_1_);
/*  65 */       String s1 = ThreadLanServerPing.getAdFromPingResponse(p_77551_1_);
/*     */       
/*  67 */       if (s1 != null) {
/*  68 */         s1 = String.valueOf(p_77551_2_.getHostAddress()) + ":" + s1;
/*  69 */         boolean flag = false;
/*     */         
/*  71 */         for (LanServerDetector.LanServer lanserverdetector$lanserver : this.listOfLanServers) {
/*  72 */           if (lanserverdetector$lanserver.getServerIpPort().equals(s1)) {
/*  73 */             lanserverdetector$lanserver.updateLastSeen();
/*  74 */             flag = true;
/*     */             
/*     */             break;
/*     */           } 
/*     */         } 
/*  79 */         if (!flag) {
/*  80 */           this.listOfLanServers.add(new LanServerDetector.LanServer(s, s1));
/*  81 */           this.wasUpdated = true;
/*     */         } 
/*     */       } 
/*     */     } }
/*     */ 
/*     */   
/*     */   public static class ThreadLanServerFind extends Thread {
/*     */     private final LanServerDetector.LanServerList localServerList;
/*     */     private final InetAddress broadcastAddress;
/*     */     private final MulticastSocket socket;
/*     */     
/*     */     public ThreadLanServerFind(LanServerDetector.LanServerList p_i1320_1_) throws IOException {
/*  93 */       super("LanServerDetector #" + LanServerDetector.field_148551_a.incrementAndGet());
/*  94 */       this.localServerList = p_i1320_1_;
/*  95 */       setDaemon(true);
/*  96 */       this.socket = new MulticastSocket(4445);
/*  97 */       this.broadcastAddress = InetAddress.getByName("224.0.2.60");
/*  98 */       this.socket.setSoTimeout(5000);
/*  99 */       this.socket.joinGroup(this.broadcastAddress);
/*     */     }
/*     */     
/*     */     public void run() {
/* 103 */       byte[] abyte = new byte[1024];
/*     */       
/* 105 */       while (!isInterrupted()) {
/* 106 */         DatagramPacket datagrampacket = new DatagramPacket(abyte, abyte.length);
/*     */         
/*     */         try {
/* 109 */           this.socket.receive(datagrampacket);
/* 110 */         } catch (SocketTimeoutException var5) {
/*     */           continue;
/* 112 */         } catch (IOException ioexception) {
/* 113 */           LanServerDetector.logger.error("Couldn't ping server", ioexception);
/*     */           
/*     */           break;
/*     */         } 
/* 117 */         String s = new String(datagrampacket.getData(), datagrampacket.getOffset(), datagrampacket.getLength());
/* 118 */         LanServerDetector.logger.debug(datagrampacket.getAddress() + ": " + s);
/* 119 */         this.localServerList.func_77551_a(s, datagrampacket.getAddress());
/*     */       } 
/*     */       
/*     */       try {
/* 123 */         this.socket.leaveGroup(this.broadcastAddress);
/* 124 */       } catch (IOException iOException) {}
/*     */ 
/*     */ 
/*     */       
/* 128 */       this.socket.close();
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\minecraft\client\network\LanServerDetector.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */