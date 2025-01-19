/*    */ package net.minecraft.network.play.server;
/*    */ 
/*    */ import java.io.IOException;
/*    */ import net.minecraft.network.INetHandler;
/*    */ import net.minecraft.network.Packet;
/*    */ import net.minecraft.network.PacketBuffer;
/*    */ import net.minecraft.network.play.INetHandlerPlayClient;
/*    */ import net.minecraft.util.BlockPos;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class S28PacketEffect
/*    */   implements Packet<INetHandlerPlayClient>
/*    */ {
/*    */   private int soundType;
/*    */   private BlockPos soundPos;
/*    */   private int soundData;
/*    */   private boolean serverWide;
/*    */   
/*    */   public S28PacketEffect() {}
/*    */   
/*    */   public S28PacketEffect(int soundTypeIn, BlockPos soundPosIn, int soundDataIn, boolean serverWideIn) {
/* 28 */     this.soundType = soundTypeIn;
/* 29 */     this.soundPos = soundPosIn;
/* 30 */     this.soundData = soundDataIn;
/* 31 */     this.serverWide = serverWideIn;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void readPacketData(PacketBuffer buf) throws IOException {
/* 38 */     this.soundType = buf.readInt();
/* 39 */     this.soundPos = buf.readBlockPos();
/* 40 */     this.soundData = buf.readInt();
/* 41 */     this.serverWide = buf.readBoolean();
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void writePacketData(PacketBuffer buf) throws IOException {
/* 48 */     buf.writeInt(this.soundType);
/* 49 */     buf.writeBlockPos(this.soundPos);
/* 50 */     buf.writeInt(this.soundData);
/* 51 */     buf.writeBoolean(this.serverWide);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void processPacket(INetHandlerPlayClient handler) {
/* 58 */     handler.handleEffect(this);
/*    */   }
/*    */   
/*    */   public boolean isSoundServerwide() {
/* 62 */     return this.serverWide;
/*    */   }
/*    */   
/*    */   public int getSoundType() {
/* 66 */     return this.soundType;
/*    */   }
/*    */   
/*    */   public int getSoundData() {
/* 70 */     return this.soundData;
/*    */   }
/*    */   
/*    */   public BlockPos getSoundPos() {
/* 74 */     return this.soundPos;
/*    */   }
/*    */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\minecraft\network\play\server\S28PacketEffect.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */