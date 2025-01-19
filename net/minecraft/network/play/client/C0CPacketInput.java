/*    */ package net.minecraft.network.play.client;
/*    */ 
/*    */ import java.io.IOException;
/*    */ import net.minecraft.network.INetHandler;
/*    */ import net.minecraft.network.Packet;
/*    */ import net.minecraft.network.PacketBuffer;
/*    */ import net.minecraft.network.play.INetHandlerPlayServer;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class C0CPacketInput
/*    */   implements Packet<INetHandlerPlayServer>
/*    */ {
/*    */   private float strafeSpeed;
/*    */   private float forwardSpeed;
/*    */   private boolean jumping;
/*    */   private boolean sneaking;
/*    */   
/*    */   public C0CPacketInput() {}
/*    */   
/*    */   public C0CPacketInput(float strafeSpeed, float forwardSpeed, boolean jumping, boolean sneaking) {
/* 26 */     this.strafeSpeed = strafeSpeed;
/* 27 */     this.forwardSpeed = forwardSpeed;
/* 28 */     this.jumping = jumping;
/* 29 */     this.sneaking = sneaking;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void readPacketData(PacketBuffer buf) throws IOException {
/* 36 */     this.strafeSpeed = buf.readFloat();
/* 37 */     this.forwardSpeed = buf.readFloat();
/* 38 */     byte b0 = buf.readByte();
/* 39 */     this.jumping = ((b0 & 0x1) > 0);
/* 40 */     this.sneaking = ((b0 & 0x2) > 0);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void writePacketData(PacketBuffer buf) throws IOException {
/* 47 */     buf.writeFloat(this.strafeSpeed);
/* 48 */     buf.writeFloat(this.forwardSpeed);
/* 49 */     byte b0 = 0;
/*    */     
/* 51 */     if (this.jumping) {
/* 52 */       b0 = (byte)(b0 | 0x1);
/*    */     }
/*    */     
/* 55 */     if (this.sneaking) {
/* 56 */       b0 = (byte)(b0 | 0x2);
/*    */     }
/*    */     
/* 59 */     buf.writeByte(b0);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void processPacket(INetHandlerPlayServer handler) {
/* 66 */     handler.processInput(this);
/*    */   }
/*    */   
/*    */   public float getStrafeSpeed() {
/* 70 */     return this.strafeSpeed;
/*    */   }
/*    */   
/*    */   public float getForwardSpeed() {
/* 74 */     return this.forwardSpeed;
/*    */   }
/*    */   
/*    */   public boolean isJumping() {
/* 78 */     return this.jumping;
/*    */   }
/*    */   
/*    */   public boolean isSneaking() {
/* 82 */     return this.sneaking;
/*    */   }
/*    */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\minecraft\network\play\client\C0CPacketInput.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */