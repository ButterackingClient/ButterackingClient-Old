/*    */ package net.minecraft.network.play.server;
/*    */ 
/*    */ import java.io.IOException;
/*    */ import net.minecraft.nbt.NBTTagCompound;
/*    */ import net.minecraft.network.INetHandler;
/*    */ import net.minecraft.network.Packet;
/*    */ import net.minecraft.network.PacketBuffer;
/*    */ import net.minecraft.network.play.INetHandlerPlayClient;
/*    */ import net.minecraft.util.BlockPos;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class S35PacketUpdateTileEntity
/*    */   implements Packet<INetHandlerPlayClient>
/*    */ {
/*    */   private BlockPos blockPos;
/*    */   private int metadata;
/*    */   private NBTTagCompound nbt;
/*    */   
/*    */   public S35PacketUpdateTileEntity() {}
/*    */   
/*    */   public S35PacketUpdateTileEntity(BlockPos blockPosIn, int metadataIn, NBTTagCompound nbtIn) {
/* 24 */     this.blockPos = blockPosIn;
/* 25 */     this.metadata = metadataIn;
/* 26 */     this.nbt = nbtIn;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void readPacketData(PacketBuffer buf) throws IOException {
/* 33 */     this.blockPos = buf.readBlockPos();
/* 34 */     this.metadata = buf.readUnsignedByte();
/* 35 */     this.nbt = buf.readNBTTagCompoundFromBuffer();
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void writePacketData(PacketBuffer buf) throws IOException {
/* 42 */     buf.writeBlockPos(this.blockPos);
/* 43 */     buf.writeByte((byte)this.metadata);
/* 44 */     buf.writeNBTTagCompoundToBuffer(this.nbt);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void processPacket(INetHandlerPlayClient handler) {
/* 51 */     handler.handleUpdateTileEntity(this);
/*    */   }
/*    */   
/*    */   public BlockPos getPos() {
/* 55 */     return this.blockPos;
/*    */   }
/*    */   
/*    */   public int getTileEntityType() {
/* 59 */     return this.metadata;
/*    */   }
/*    */   
/*    */   public NBTTagCompound getNbtCompound() {
/* 63 */     return this.nbt;
/*    */   }
/*    */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\minecraft\network\play\server\S35PacketUpdateTileEntity.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */