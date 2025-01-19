/*    */ package net.minecraft.network.play.client;
/*    */ 
/*    */ import java.io.IOException;
/*    */ import net.minecraft.network.INetHandler;
/*    */ import net.minecraft.network.Packet;
/*    */ import net.minecraft.network.PacketBuffer;
/*    */ import net.minecraft.network.play.INetHandlerPlayServer;
/*    */ import net.minecraft.util.BlockPos;
/*    */ import net.minecraft.util.IChatComponent;
/*    */ 
/*    */ public class C12PacketUpdateSign
/*    */   implements Packet<INetHandlerPlayServer> {
/*    */   private BlockPos pos;
/*    */   private IChatComponent[] lines;
/*    */   
/*    */   public C12PacketUpdateSign() {}
/*    */   
/*    */   public C12PacketUpdateSign(BlockPos pos, IChatComponent[] lines) {
/* 19 */     this.pos = pos;
/* 20 */     this.lines = new IChatComponent[] { lines[0], lines[1], lines[2], lines[3] };
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void readPacketData(PacketBuffer buf) throws IOException {
/* 27 */     this.pos = buf.readBlockPos();
/* 28 */     this.lines = new IChatComponent[4];
/*    */     
/* 30 */     for (int i = 0; i < 4; i++) {
/* 31 */       String s = buf.readStringFromBuffer(384);
/* 32 */       IChatComponent ichatcomponent = IChatComponent.Serializer.jsonToComponent(s);
/* 33 */       this.lines[i] = ichatcomponent;
/*    */     } 
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void writePacketData(PacketBuffer buf) throws IOException {
/* 41 */     buf.writeBlockPos(this.pos);
/*    */     
/* 43 */     for (int i = 0; i < 4; i++) {
/* 44 */       IChatComponent ichatcomponent = this.lines[i];
/* 45 */       String s = IChatComponent.Serializer.componentToJson(ichatcomponent);
/* 46 */       buf.writeString(s);
/*    */     } 
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void processPacket(INetHandlerPlayServer handler) {
/* 54 */     handler.processUpdateSign(this);
/*    */   }
/*    */   
/*    */   public BlockPos getPosition() {
/* 58 */     return this.pos;
/*    */   }
/*    */   
/*    */   public IChatComponent[] getLines() {
/* 62 */     return this.lines;
/*    */   }
/*    */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\minecraft\network\play\client\C12PacketUpdateSign.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */