/*    */ package net.minecraft.network.play.server;
/*    */ 
/*    */ import java.io.IOException;
/*    */ import net.minecraft.network.INetHandler;
/*    */ import net.minecraft.network.Packet;
/*    */ import net.minecraft.network.PacketBuffer;
/*    */ import net.minecraft.network.play.INetHandlerPlayClient;
/*    */ import net.minecraft.util.BlockPos;
/*    */ 
/*    */ public class S05PacketSpawnPosition
/*    */   implements Packet<INetHandlerPlayClient> {
/*    */   private BlockPos spawnBlockPos;
/*    */   
/*    */   public S05PacketSpawnPosition() {}
/*    */   
/*    */   public S05PacketSpawnPosition(BlockPos spawnBlockPosIn) {
/* 17 */     this.spawnBlockPos = spawnBlockPosIn;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void readPacketData(PacketBuffer buf) throws IOException {
/* 24 */     this.spawnBlockPos = buf.readBlockPos();
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void writePacketData(PacketBuffer buf) throws IOException {
/* 31 */     buf.writeBlockPos(this.spawnBlockPos);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void processPacket(INetHandlerPlayClient handler) {
/* 38 */     handler.handleSpawnPosition(this);
/*    */   }
/*    */   
/*    */   public BlockPos getSpawnPos() {
/* 42 */     return this.spawnBlockPos;
/*    */   }
/*    */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\minecraft\network\play\server\S05PacketSpawnPosition.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */