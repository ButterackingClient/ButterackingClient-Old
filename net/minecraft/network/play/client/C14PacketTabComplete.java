/*    */ package net.minecraft.network.play.client;
/*    */ 
/*    */ import java.io.IOException;
/*    */ import net.minecraft.network.INetHandler;
/*    */ import net.minecraft.network.Packet;
/*    */ import net.minecraft.network.PacketBuffer;
/*    */ import net.minecraft.network.play.INetHandlerPlayServer;
/*    */ import net.minecraft.util.BlockPos;
/*    */ import org.apache.commons.lang3.StringUtils;
/*    */ 
/*    */ public class C14PacketTabComplete
/*    */   implements Packet<INetHandlerPlayServer> {
/*    */   private String message;
/*    */   private BlockPos targetBlock;
/*    */   
/*    */   public C14PacketTabComplete() {}
/*    */   
/*    */   public C14PacketTabComplete(String msg) {
/* 19 */     this(msg, null);
/*    */   }
/*    */   
/*    */   public C14PacketTabComplete(String msg, BlockPos target) {
/* 23 */     this.message = msg;
/* 24 */     this.targetBlock = target;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void readPacketData(PacketBuffer buf) throws IOException {
/* 31 */     this.message = buf.readStringFromBuffer(32767);
/* 32 */     boolean flag = buf.readBoolean();
/*    */     
/* 34 */     if (flag) {
/* 35 */       this.targetBlock = buf.readBlockPos();
/*    */     }
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void writePacketData(PacketBuffer buf) throws IOException {
/* 43 */     buf.writeString(StringUtils.substring(this.message, 0, 32767));
/* 44 */     boolean flag = (this.targetBlock != null);
/* 45 */     buf.writeBoolean(flag);
/*    */     
/* 47 */     if (flag) {
/* 48 */       buf.writeBlockPos(this.targetBlock);
/*    */     }
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void processPacket(INetHandlerPlayServer handler) {
/* 56 */     handler.processTabComplete(this);
/*    */   }
/*    */   
/*    */   public String getMessage() {
/* 60 */     return this.message;
/*    */   }
/*    */   
/*    */   public BlockPos getTargetBlock() {
/* 64 */     return this.targetBlock;
/*    */   }
/*    */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\minecraft\network\play\client\C14PacketTabComplete.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */