/*    */ package net.minecraft.network.play.server;
/*    */ 
/*    */ import java.io.IOException;
/*    */ import net.minecraft.network.INetHandler;
/*    */ import net.minecraft.network.Packet;
/*    */ import net.minecraft.network.PacketBuffer;
/*    */ import net.minecraft.network.play.INetHandlerPlayClient;
/*    */ 
/*    */ public class S1FPacketSetExperience
/*    */   implements Packet<INetHandlerPlayClient> {
/*    */   private float field_149401_a;
/*    */   private int totalExperience;
/*    */   private int level;
/*    */   
/*    */   public S1FPacketSetExperience() {}
/*    */   
/*    */   public S1FPacketSetExperience(float p_i45222_1_, int totalExperienceIn, int levelIn) {
/* 18 */     this.field_149401_a = p_i45222_1_;
/* 19 */     this.totalExperience = totalExperienceIn;
/* 20 */     this.level = levelIn;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void readPacketData(PacketBuffer buf) throws IOException {
/* 27 */     this.field_149401_a = buf.readFloat();
/* 28 */     this.level = buf.readVarIntFromBuffer();
/* 29 */     this.totalExperience = buf.readVarIntFromBuffer();
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void writePacketData(PacketBuffer buf) throws IOException {
/* 36 */     buf.writeFloat(this.field_149401_a);
/* 37 */     buf.writeVarIntToBuffer(this.level);
/* 38 */     buf.writeVarIntToBuffer(this.totalExperience);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void processPacket(INetHandlerPlayClient handler) {
/* 45 */     handler.handleSetExperience(this);
/*    */   }
/*    */   
/*    */   public float func_149397_c() {
/* 49 */     return this.field_149401_a;
/*    */   }
/*    */   
/*    */   public int getTotalExperience() {
/* 53 */     return this.totalExperience;
/*    */   }
/*    */   
/*    */   public int getLevel() {
/* 57 */     return this.level;
/*    */   }
/*    */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\minecraft\network\play\server\S1FPacketSetExperience.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */