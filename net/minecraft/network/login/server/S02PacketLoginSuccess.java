/*    */ package net.minecraft.network.login.server;
/*    */ 
/*    */ import com.mojang.authlib.GameProfile;
/*    */ import java.io.IOException;
/*    */ import java.util.UUID;
/*    */ import net.minecraft.network.INetHandler;
/*    */ import net.minecraft.network.Packet;
/*    */ import net.minecraft.network.PacketBuffer;
/*    */ import net.minecraft.network.login.INetHandlerLoginClient;
/*    */ 
/*    */ public class S02PacketLoginSuccess
/*    */   implements Packet<INetHandlerLoginClient>
/*    */ {
/*    */   private GameProfile profile;
/*    */   
/*    */   public S02PacketLoginSuccess() {}
/*    */   
/*    */   public S02PacketLoginSuccess(GameProfile profileIn) {
/* 19 */     this.profile = profileIn;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void readPacketData(PacketBuffer buf) throws IOException {
/* 26 */     String s = buf.readStringFromBuffer(36);
/* 27 */     String s1 = buf.readStringFromBuffer(16);
/* 28 */     UUID uuid = UUID.fromString(s);
/* 29 */     this.profile = new GameProfile(uuid, s1);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void writePacketData(PacketBuffer buf) throws IOException {
/* 36 */     UUID uuid = this.profile.getId();
/* 37 */     buf.writeString((uuid == null) ? "" : uuid.toString());
/* 38 */     buf.writeString(this.profile.getName());
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void processPacket(INetHandlerLoginClient handler) {
/* 45 */     handler.handleLoginSuccess(this);
/*    */   }
/*    */   
/*    */   public GameProfile getProfile() {
/* 49 */     return this.profile;
/*    */   }
/*    */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\minecraft\network\login\server\S02PacketLoginSuccess.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */