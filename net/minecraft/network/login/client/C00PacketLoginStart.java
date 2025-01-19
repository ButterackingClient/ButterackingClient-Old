/*    */ package net.minecraft.network.login.client;
/*    */ 
/*    */ import com.mojang.authlib.GameProfile;
/*    */ import java.io.IOException;
/*    */ import net.minecraft.network.INetHandler;
/*    */ import net.minecraft.network.Packet;
/*    */ import net.minecraft.network.PacketBuffer;
/*    */ import net.minecraft.network.login.INetHandlerLoginServer;
/*    */ 
/*    */ 
/*    */ public class C00PacketLoginStart
/*    */   implements Packet<INetHandlerLoginServer>
/*    */ {
/*    */   private GameProfile profile;
/*    */   
/*    */   public C00PacketLoginStart() {}
/*    */   
/*    */   public C00PacketLoginStart(GameProfile profileIn) {
/* 19 */     this.profile = profileIn;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void readPacketData(PacketBuffer buf) throws IOException {
/* 26 */     this.profile = new GameProfile(null, buf.readStringFromBuffer(16));
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void writePacketData(PacketBuffer buf) throws IOException {
/* 33 */     buf.writeString(this.profile.getName());
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void processPacket(INetHandlerLoginServer handler) {
/* 40 */     handler.processLoginStart(this);
/*    */   }
/*    */   
/*    */   public GameProfile getProfile() {
/* 44 */     return this.profile;
/*    */   }
/*    */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\minecraft\network\login\client\C00PacketLoginStart.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */