/*    */ package net.minecraft.network.play.client;
/*    */ 
/*    */ import java.io.IOException;
/*    */ import net.minecraft.network.INetHandler;
/*    */ import net.minecraft.network.Packet;
/*    */ import net.minecraft.network.PacketBuffer;
/*    */ import net.minecraft.network.play.INetHandlerPlayServer;
/*    */ import net.minecraft.util.BlockPos;
/*    */ import net.minecraft.util.EnumFacing;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class C07PacketPlayerDigging
/*    */   implements Packet<INetHandlerPlayServer>
/*    */ {
/*    */   private BlockPos position;
/*    */   private EnumFacing facing;
/*    */   private Action status;
/*    */   
/*    */   public C07PacketPlayerDigging() {}
/*    */   
/*    */   public C07PacketPlayerDigging(Action statusIn, BlockPos posIn, EnumFacing facingIn) {
/* 24 */     this.status = statusIn;
/* 25 */     this.position = posIn;
/* 26 */     this.facing = facingIn;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void readPacketData(PacketBuffer buf) throws IOException {
/* 33 */     this.status = (Action)buf.readEnumValue(Action.class);
/* 34 */     this.position = buf.readBlockPos();
/* 35 */     this.facing = EnumFacing.getFront(buf.readUnsignedByte());
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void writePacketData(PacketBuffer buf) throws IOException {
/* 42 */     buf.writeEnumValue(this.status);
/* 43 */     buf.writeBlockPos(this.position);
/* 44 */     buf.writeByte(this.facing.getIndex());
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void processPacket(INetHandlerPlayServer handler) {
/* 51 */     handler.processPlayerDigging(this);
/*    */   }
/*    */   
/*    */   public BlockPos getPosition() {
/* 55 */     return this.position;
/*    */   }
/*    */   
/*    */   public EnumFacing getFacing() {
/* 59 */     return this.facing;
/*    */   }
/*    */   
/*    */   public Action getStatus() {
/* 63 */     return this.status;
/*    */   }
/*    */   
/*    */   public enum Action {
/* 67 */     START_DESTROY_BLOCK,
/* 68 */     ABORT_DESTROY_BLOCK,
/* 69 */     STOP_DESTROY_BLOCK,
/* 70 */     DROP_ALL_ITEMS,
/* 71 */     DROP_ITEM,
/* 72 */     RELEASE_USE_ITEM;
/*    */   }
/*    */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\minecraft\network\play\client\C07PacketPlayerDigging.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */