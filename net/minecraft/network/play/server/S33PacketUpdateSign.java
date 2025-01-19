/*    */ package net.minecraft.network.play.server;
/*    */ 
/*    */ import java.io.IOException;
/*    */ import net.minecraft.network.INetHandler;
/*    */ import net.minecraft.network.Packet;
/*    */ import net.minecraft.network.PacketBuffer;
/*    */ import net.minecraft.network.play.INetHandlerPlayClient;
/*    */ import net.minecraft.util.BlockPos;
/*    */ import net.minecraft.util.IChatComponent;
/*    */ import net.minecraft.world.World;
/*    */ 
/*    */ public class S33PacketUpdateSign
/*    */   implements Packet<INetHandlerPlayClient> {
/*    */   private World world;
/*    */   private BlockPos blockPos;
/*    */   private IChatComponent[] lines;
/*    */   
/*    */   public S33PacketUpdateSign() {}
/*    */   
/*    */   public S33PacketUpdateSign(World worldIn, BlockPos blockPosIn, IChatComponent[] linesIn) {
/* 21 */     this.world = worldIn;
/* 22 */     this.blockPos = blockPosIn;
/* 23 */     this.lines = new IChatComponent[] { linesIn[0], linesIn[1], linesIn[2], linesIn[3] };
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void readPacketData(PacketBuffer buf) throws IOException {
/* 30 */     this.blockPos = buf.readBlockPos();
/* 31 */     this.lines = new IChatComponent[4];
/*    */     
/* 33 */     for (int i = 0; i < 4; i++) {
/* 34 */       this.lines[i] = buf.readChatComponent();
/*    */     }
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void writePacketData(PacketBuffer buf) throws IOException {
/* 42 */     buf.writeBlockPos(this.blockPos);
/*    */     
/* 44 */     for (int i = 0; i < 4; i++) {
/* 45 */       buf.writeChatComponent(this.lines[i]);
/*    */     }
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void processPacket(INetHandlerPlayClient handler) {
/* 53 */     handler.handleUpdateSign(this);
/*    */   }
/*    */   
/*    */   public BlockPos getPos() {
/* 57 */     return this.blockPos;
/*    */   }
/*    */   
/*    */   public IChatComponent[] getLines() {
/* 61 */     return this.lines;
/*    */   }
/*    */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\minecraft\network\play\server\S33PacketUpdateSign.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */