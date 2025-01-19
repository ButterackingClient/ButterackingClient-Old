/*    */ package net.minecraft.realms;
/*    */ 
/*    */ import net.minecraft.client.multiplayer.ServerAddress;
/*    */ 
/*    */ public class RealmsServerAddress {
/*    */   private final String host;
/*    */   private final int port;
/*    */   
/*    */   protected RealmsServerAddress(String hostIn, int portIn) {
/* 10 */     this.host = hostIn;
/* 11 */     this.port = portIn;
/*    */   }
/*    */   
/*    */   public String getHost() {
/* 15 */     return this.host;
/*    */   }
/*    */   
/*    */   public int getPort() {
/* 19 */     return this.port;
/*    */   }
/*    */   
/*    */   public static RealmsServerAddress parseString(String p_parseString_0_) {
/* 23 */     ServerAddress serveraddress = ServerAddress.fromString(p_parseString_0_);
/* 24 */     return new RealmsServerAddress(serveraddress.getIP(), serveraddress.getPort());
/*    */   }
/*    */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\minecraft\realms\RealmsServerAddress.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */