/*    */ package net.minecraft.network.play.server;
/*    */ 
/*    */ import java.io.IOException;
/*    */ import net.minecraft.entity.player.EntityPlayer;
/*    */ import net.minecraft.network.INetHandler;
/*    */ import net.minecraft.network.Packet;
/*    */ import net.minecraft.network.PacketBuffer;
/*    */ import net.minecraft.network.play.INetHandlerPlayClient;
/*    */ import net.minecraft.util.BlockPos;
/*    */ import net.minecraft.world.World;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class S0APacketUseBed
/*    */   implements Packet<INetHandlerPlayClient>
/*    */ {
/*    */   private int playerID;
/*    */   private BlockPos bedPos;
/*    */   
/*    */   public S0APacketUseBed() {}
/*    */   
/*    */   public S0APacketUseBed(EntityPlayer player, BlockPos bedPosIn) {
/* 24 */     this.playerID = player.getEntityId();
/* 25 */     this.bedPos = bedPosIn;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void readPacketData(PacketBuffer buf) throws IOException {
/* 32 */     this.playerID = buf.readVarIntFromBuffer();
/* 33 */     this.bedPos = buf.readBlockPos();
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void writePacketData(PacketBuffer buf) throws IOException {
/* 40 */     buf.writeVarIntToBuffer(this.playerID);
/* 41 */     buf.writeBlockPos(this.bedPos);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void processPacket(INetHandlerPlayClient handler) {
/* 48 */     handler.handleUseBed(this);
/*    */   }
/*    */   
/*    */   public EntityPlayer getPlayer(World worldIn) {
/* 52 */     return (EntityPlayer)worldIn.getEntityByID(this.playerID);
/*    */   }
/*    */   
/*    */   public BlockPos getBedPosition() {
/* 56 */     return this.bedPos;
/*    */   }
/*    */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\minecraft\network\play\server\S0APacketUseBed.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */