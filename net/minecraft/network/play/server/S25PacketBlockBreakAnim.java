/*    */ package net.minecraft.network.play.server;
/*    */ 
/*    */ import java.io.IOException;
/*    */ import net.minecraft.network.INetHandler;
/*    */ import net.minecraft.network.Packet;
/*    */ import net.minecraft.network.PacketBuffer;
/*    */ import net.minecraft.network.play.INetHandlerPlayClient;
/*    */ import net.minecraft.util.BlockPos;
/*    */ 
/*    */ public class S25PacketBlockBreakAnim
/*    */   implements Packet<INetHandlerPlayClient> {
/*    */   private int breakerId;
/*    */   private BlockPos position;
/*    */   private int progress;
/*    */   
/*    */   public S25PacketBlockBreakAnim() {}
/*    */   
/*    */   public S25PacketBlockBreakAnim(int breakerId, BlockPos pos, int progress) {
/* 19 */     this.breakerId = breakerId;
/* 20 */     this.position = pos;
/* 21 */     this.progress = progress;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void readPacketData(PacketBuffer buf) throws IOException {
/* 28 */     this.breakerId = buf.readVarIntFromBuffer();
/* 29 */     this.position = buf.readBlockPos();
/* 30 */     this.progress = buf.readUnsignedByte();
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void writePacketData(PacketBuffer buf) throws IOException {
/* 37 */     buf.writeVarIntToBuffer(this.breakerId);
/* 38 */     buf.writeBlockPos(this.position);
/* 39 */     buf.writeByte(this.progress);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void processPacket(INetHandlerPlayClient handler) {
/* 46 */     handler.handleBlockBreakAnim(this);
/*    */   }
/*    */   
/*    */   public int getBreakerId() {
/* 50 */     return this.breakerId;
/*    */   }
/*    */   
/*    */   public BlockPos getPosition() {
/* 54 */     return this.position;
/*    */   }
/*    */   
/*    */   public int getProgress() {
/* 58 */     return this.progress;
/*    */   }
/*    */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\minecraft\network\play\server\S25PacketBlockBreakAnim.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */