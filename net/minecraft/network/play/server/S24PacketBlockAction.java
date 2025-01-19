/*    */ package net.minecraft.network.play.server;
/*    */ 
/*    */ import java.io.IOException;
/*    */ import net.minecraft.block.Block;
/*    */ import net.minecraft.network.INetHandler;
/*    */ import net.minecraft.network.Packet;
/*    */ import net.minecraft.network.PacketBuffer;
/*    */ import net.minecraft.network.play.INetHandlerPlayClient;
/*    */ import net.minecraft.util.BlockPos;
/*    */ 
/*    */ public class S24PacketBlockAction
/*    */   implements Packet<INetHandlerPlayClient> {
/*    */   private BlockPos blockPosition;
/*    */   private int instrument;
/*    */   private int pitch;
/*    */   private Block block;
/*    */   
/*    */   public S24PacketBlockAction() {}
/*    */   
/*    */   public S24PacketBlockAction(BlockPos blockPositionIn, Block blockIn, int instrumentIn, int pitchIn) {
/* 21 */     this.blockPosition = blockPositionIn;
/* 22 */     this.instrument = instrumentIn;
/* 23 */     this.pitch = pitchIn;
/* 24 */     this.block = blockIn;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void readPacketData(PacketBuffer buf) throws IOException {
/* 31 */     this.blockPosition = buf.readBlockPos();
/* 32 */     this.instrument = buf.readUnsignedByte();
/* 33 */     this.pitch = buf.readUnsignedByte();
/* 34 */     this.block = Block.getBlockById(buf.readVarIntFromBuffer() & 0xFFF);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void writePacketData(PacketBuffer buf) throws IOException {
/* 41 */     buf.writeBlockPos(this.blockPosition);
/* 42 */     buf.writeByte(this.instrument);
/* 43 */     buf.writeByte(this.pitch);
/* 44 */     buf.writeVarIntToBuffer(Block.getIdFromBlock(this.block) & 0xFFF);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void processPacket(INetHandlerPlayClient handler) {
/* 51 */     handler.handleBlockAction(this);
/*    */   }
/*    */   
/*    */   public BlockPos getBlockPosition() {
/* 55 */     return this.blockPosition;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public int getData1() {
/* 62 */     return this.instrument;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public int getData2() {
/* 69 */     return this.pitch;
/*    */   }
/*    */   
/*    */   public Block getBlockType() {
/* 73 */     return this.block;
/*    */   }
/*    */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\minecraft\network\play\server\S24PacketBlockAction.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */