/*    */ package net.minecraft.network.play.server;
/*    */ 
/*    */ import java.io.IOException;
/*    */ import net.minecraft.network.INetHandler;
/*    */ import net.minecraft.network.Packet;
/*    */ import net.minecraft.network.PacketBuffer;
/*    */ import net.minecraft.network.play.INetHandlerPlayClient;
/*    */ 
/*    */ public class S03PacketTimeUpdate
/*    */   implements Packet<INetHandlerPlayClient> {
/*    */   private long totalWorldTime;
/*    */   private long worldTime;
/*    */   
/*    */   public S03PacketTimeUpdate() {}
/*    */   
/*    */   public S03PacketTimeUpdate(long totalWorldTimeIn, long totalTimeIn, boolean doDayLightCycle) {
/* 17 */     this.totalWorldTime = totalWorldTimeIn;
/* 18 */     this.worldTime = totalTimeIn;
/*    */     
/* 20 */     if (!doDayLightCycle) {
/* 21 */       this.worldTime = -this.worldTime;
/*    */       
/* 23 */       if (this.worldTime == 0L) {
/* 24 */         this.worldTime = -1L;
/*    */       }
/*    */     } 
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void readPacketData(PacketBuffer buf) throws IOException {
/* 33 */     this.totalWorldTime = buf.readLong();
/* 34 */     this.worldTime = buf.readLong();
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void writePacketData(PacketBuffer buf) throws IOException {
/* 41 */     buf.writeLong(this.totalWorldTime);
/* 42 */     buf.writeLong(this.worldTime);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void processPacket(INetHandlerPlayClient handler) {
/* 49 */     handler.handleTimeUpdate(this);
/*    */   }
/*    */   
/*    */   public long getTotalWorldTime() {
/* 53 */     return this.totalWorldTime;
/*    */   }
/*    */   
/*    */   public long getWorldTime() {
/* 57 */     return this.worldTime;
/*    */   }
/*    */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\minecraft\network\play\server\S03PacketTimeUpdate.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */