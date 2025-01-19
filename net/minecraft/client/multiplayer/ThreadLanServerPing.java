/*    */ package net.minecraft.client.multiplayer;
/*    */ 
/*    */ import java.io.IOException;
/*    */ import java.net.DatagramPacket;
/*    */ import java.net.DatagramSocket;
/*    */ import java.net.InetAddress;
/*    */ import java.util.concurrent.atomic.AtomicInteger;
/*    */ import org.apache.logging.log4j.LogManager;
/*    */ import org.apache.logging.log4j.Logger;
/*    */ 
/*    */ public class ThreadLanServerPing
/*    */   extends Thread {
/* 13 */   private static final AtomicInteger field_148658_a = new AtomicInteger(0);
/* 14 */   private static final Logger logger = LogManager.getLogger();
/*    */   
/*    */   private final String motd;
/*    */   
/*    */   private final DatagramSocket socket;
/*    */   
/*    */   private boolean isStopping = true;
/*    */   
/*    */   private final String address;
/*    */   
/*    */   public ThreadLanServerPing(String p_i1321_1_, String p_i1321_2_) throws IOException {
/* 25 */     super("LanServerPinger #" + field_148658_a.incrementAndGet());
/* 26 */     this.motd = p_i1321_1_;
/* 27 */     this.address = p_i1321_2_;
/* 28 */     setDaemon(true);
/* 29 */     this.socket = new DatagramSocket();
/*    */   }
/*    */   
/*    */   public void run() {
/* 33 */     String s = getPingResponse(this.motd, this.address);
/* 34 */     byte[] abyte = s.getBytes();
/*    */     
/* 36 */     while (!isInterrupted() && this.isStopping) {
/*    */       try {
/* 38 */         InetAddress inetaddress = InetAddress.getByName("224.0.2.60");
/* 39 */         DatagramPacket datagrampacket = new DatagramPacket(abyte, abyte.length, inetaddress, 4445);
/* 40 */         this.socket.send(datagrampacket);
/* 41 */       } catch (IOException ioexception) {
/* 42 */         logger.warn("LanServerPinger: " + ioexception.getMessage());
/*    */         
/*    */         break;
/*    */       } 
/*    */       try {
/* 47 */         sleep(1500L);
/* 48 */       } catch (InterruptedException interruptedException) {}
/*    */     } 
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public void interrupt() {
/* 55 */     super.interrupt();
/* 56 */     this.isStopping = false;
/*    */   }
/*    */   
/*    */   public static String getPingResponse(String p_77525_0_, String p_77525_1_) {
/* 60 */     return "[MOTD]" + p_77525_0_ + "[/MOTD][AD]" + p_77525_1_ + "[/AD]";
/*    */   }
/*    */   
/*    */   public static String getMotdFromPingResponse(String p_77524_0_) {
/* 64 */     int i = p_77524_0_.indexOf("[MOTD]");
/*    */     
/* 66 */     if (i < 0) {
/* 67 */       return "missing no";
/*    */     }
/* 69 */     int j = p_77524_0_.indexOf("[/MOTD]", i + "[MOTD]".length());
/* 70 */     return (j < i) ? "missing no" : p_77524_0_.substring(i + "[MOTD]".length(), j);
/*    */   }
/*    */ 
/*    */   
/*    */   public static String getAdFromPingResponse(String p_77523_0_) {
/* 75 */     int i = p_77523_0_.indexOf("[/MOTD]");
/*    */     
/* 77 */     if (i < 0) {
/* 78 */       return null;
/*    */     }
/* 80 */     int j = p_77523_0_.indexOf("[/MOTD]", i + "[/MOTD]".length());
/*    */     
/* 82 */     if (j >= 0) {
/* 83 */       return null;
/*    */     }
/* 85 */     int k = p_77523_0_.indexOf("[AD]", i + "[/MOTD]".length());
/*    */     
/* 87 */     if (k < 0) {
/* 88 */       return null;
/*    */     }
/* 90 */     int l = p_77523_0_.indexOf("[/AD]", k + "[AD]".length());
/* 91 */     return (l < k) ? null : p_77523_0_.substring(k + "[AD]".length(), l);
/*    */   }
/*    */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\minecraft\client\multiplayer\ThreadLanServerPing.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */