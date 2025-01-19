/*    */ package net.minecraft.client.multiplayer;
/*    */ 
/*    */ import java.net.IDN;
/*    */ import java.util.Hashtable;
/*    */ import javax.naming.directory.Attributes;
/*    */ import javax.naming.directory.DirContext;
/*    */ import javax.naming.directory.InitialDirContext;
/*    */ 
/*    */ public class ServerAddress {
/*    */   private final String ipAddress;
/*    */   private final int serverPort;
/*    */   
/*    */   private ServerAddress(String address, int port) {
/* 14 */     this.ipAddress = address;
/* 15 */     this.serverPort = port;
/*    */   }
/*    */   
/*    */   public String getIP() {
/* 19 */     return IDN.toASCII(this.ipAddress);
/*    */   }
/*    */   
/*    */   public int getPort() {
/* 23 */     return this.serverPort;
/*    */   }
/*    */   
/*    */   public static ServerAddress fromString(String p_78860_0_) {
/* 27 */     if (p_78860_0_ == null) {
/* 28 */       return null;
/*    */     }
/* 30 */     String[] astring = p_78860_0_.split(":");
/*    */     
/* 32 */     if (p_78860_0_.startsWith("[")) {
/* 33 */       int i = p_78860_0_.indexOf("]");
/*    */       
/* 35 */       if (i > 0) {
/* 36 */         String s = p_78860_0_.substring(1, i);
/* 37 */         String s1 = p_78860_0_.substring(i + 1).trim();
/*    */         
/* 39 */         if (s1.startsWith(":") && s1.length() > 0) {
/* 40 */           s1 = s1.substring(1);
/* 41 */           astring = new String[] { s, s1 };
/*    */         } else {
/* 43 */           astring = new String[] { s };
/*    */         } 
/*    */       } 
/*    */     } 
/*    */     
/* 48 */     if (astring.length > 2) {
/* 49 */       astring = new String[] { p_78860_0_ };
/*    */     }
/*    */     
/* 52 */     String s2 = astring[0];
/* 53 */     int j = (astring.length > 1) ? parseIntWithDefault(astring[1], 25565) : 25565;
/*    */     
/* 55 */     if (j == 25565) {
/* 56 */       String[] astring1 = getServerAddress(s2);
/* 57 */       s2 = astring1[0];
/* 58 */       j = parseIntWithDefault(astring1[1], 25565);
/*    */     } 
/*    */     
/* 61 */     return new ServerAddress(s2, j);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   private static String[] getServerAddress(String p_78863_0_) {
/*    */     try {
/* 70 */       String s = "com.sun.jndi.dns.DnsContextFactory";
/* 71 */       Class.forName("com.sun.jndi.dns.DnsContextFactory");
/* 72 */       Hashtable<Object, Object> hashtable = new Hashtable<>();
/* 73 */       hashtable.put("java.naming.factory.initial", "com.sun.jndi.dns.DnsContextFactory");
/* 74 */       hashtable.put("java.naming.provider.url", "dns:");
/* 75 */       hashtable.put("com.sun.jndi.dns.timeout.retries", "1");
/* 76 */       DirContext dircontext = new InitialDirContext(hashtable);
/* 77 */       Attributes attributes = dircontext.getAttributes("_minecraft._tcp." + p_78863_0_, new String[] { "SRV" });
/* 78 */       String[] astring = attributes.get("srv").get().toString().split(" ", 4);
/* 79 */       return new String[] { astring[3], astring[2] };
/* 80 */     } catch (Throwable var6) {
/* 81 */       return new String[] { p_78863_0_, Integer.toString(25565) };
/*    */     } 
/*    */   }
/*    */   
/*    */   private static int parseIntWithDefault(String p_78862_0_, int p_78862_1_) {
/*    */     try {
/* 87 */       return Integer.parseInt(p_78862_0_.trim());
/* 88 */     } catch (Exception var3) {
/* 89 */       return p_78862_1_;
/*    */     } 
/*    */   }
/*    */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\minecraft\client\multiplayer\ServerAddress.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */