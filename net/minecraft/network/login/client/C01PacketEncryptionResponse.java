/*    */ package net.minecraft.network.login.client;
/*    */ 
/*    */ import java.io.IOException;
/*    */ import java.security.PrivateKey;
/*    */ import java.security.PublicKey;
/*    */ import javax.crypto.SecretKey;
/*    */ import net.minecraft.network.INetHandler;
/*    */ import net.minecraft.network.Packet;
/*    */ import net.minecraft.network.PacketBuffer;
/*    */ import net.minecraft.network.login.INetHandlerLoginServer;
/*    */ import net.minecraft.util.CryptManager;
/*    */ 
/*    */ public class C01PacketEncryptionResponse implements Packet<INetHandlerLoginServer> {
/* 14 */   private byte[] secretKeyEncrypted = new byte[0];
/* 15 */   private byte[] verifyTokenEncrypted = new byte[0];
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public C01PacketEncryptionResponse(SecretKey secretKey, PublicKey publicKey, byte[] verifyToken) {
/* 21 */     this.secretKeyEncrypted = CryptManager.encryptData(publicKey, secretKey.getEncoded());
/* 22 */     this.verifyTokenEncrypted = CryptManager.encryptData(publicKey, verifyToken);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void readPacketData(PacketBuffer buf) throws IOException {
/* 29 */     this.secretKeyEncrypted = buf.readByteArray();
/* 30 */     this.verifyTokenEncrypted = buf.readByteArray();
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void writePacketData(PacketBuffer buf) throws IOException {
/* 37 */     buf.writeByteArray(this.secretKeyEncrypted);
/* 38 */     buf.writeByteArray(this.verifyTokenEncrypted);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void processPacket(INetHandlerLoginServer handler) {
/* 45 */     handler.processEncryptionResponse(this);
/*    */   }
/*    */   
/*    */   public SecretKey getSecretKey(PrivateKey key) {
/* 49 */     return CryptManager.decryptSharedKey(key, this.secretKeyEncrypted);
/*    */   }
/*    */   
/*    */   public byte[] getVerifyToken(PrivateKey key) {
/* 53 */     return (key == null) ? this.verifyTokenEncrypted : CryptManager.decryptData(key, this.verifyTokenEncrypted);
/*    */   }
/*    */   
/*    */   public C01PacketEncryptionResponse() {}
/*    */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\minecraft\network\login\client\C01PacketEncryptionResponse.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */