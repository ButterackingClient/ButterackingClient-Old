/*    */ package net.minecraft.network.play.server;
/*    */ 
/*    */ import java.io.IOException;
/*    */ import net.minecraft.network.INetHandler;
/*    */ import net.minecraft.network.Packet;
/*    */ import net.minecraft.network.PacketBuffer;
/*    */ import net.minecraft.network.play.INetHandlerPlayClient;
/*    */ 
/*    */ public class S3APacketTabComplete
/*    */   implements Packet<INetHandlerPlayClient> {
/*    */   private String[] matches;
/*    */   
/*    */   public S3APacketTabComplete() {}
/*    */   
/*    */   public S3APacketTabComplete(String[] matchesIn) {
/* 16 */     this.matches = matchesIn;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void readPacketData(PacketBuffer buf) throws IOException {
/* 23 */     this.matches = new String[buf.readVarIntFromBuffer()];
/*    */     
/* 25 */     for (int i = 0; i < this.matches.length; i++) {
/* 26 */       this.matches[i] = buf.readStringFromBuffer(32767);
/*    */     }
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void writePacketData(PacketBuffer buf) throws IOException {
/* 34 */     buf.writeVarIntToBuffer(this.matches.length); byte b; int i;
/*    */     String[] arrayOfString;
/* 36 */     for (i = (arrayOfString = this.matches).length, b = 0; b < i; ) { String s = arrayOfString[b];
/* 37 */       buf.writeString(s);
/*    */       b++; }
/*    */   
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public void processPacket(INetHandlerPlayClient handler) {
/* 45 */     handler.handleTabComplete(this);
/*    */   }
/*    */   
/*    */   public String[] func_149630_c() {
/* 49 */     return this.matches;
/*    */   }
/*    */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\minecraft\network\play\server\S3APacketTabComplete.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */