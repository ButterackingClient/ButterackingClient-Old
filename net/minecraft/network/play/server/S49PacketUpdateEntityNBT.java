/*    */ package net.minecraft.network.play.server;
/*    */ 
/*    */ import java.io.IOException;
/*    */ import net.minecraft.entity.Entity;
/*    */ import net.minecraft.nbt.NBTTagCompound;
/*    */ import net.minecraft.network.INetHandler;
/*    */ import net.minecraft.network.Packet;
/*    */ import net.minecraft.network.PacketBuffer;
/*    */ import net.minecraft.network.play.INetHandlerPlayClient;
/*    */ import net.minecraft.world.World;
/*    */ 
/*    */ public class S49PacketUpdateEntityNBT
/*    */   implements Packet<INetHandlerPlayClient> {
/*    */   private int entityId;
/*    */   private NBTTagCompound tagCompound;
/*    */   
/*    */   public S49PacketUpdateEntityNBT() {}
/*    */   
/*    */   public S49PacketUpdateEntityNBT(int entityIdIn, NBTTagCompound tagCompoundIn) {
/* 20 */     this.entityId = entityIdIn;
/* 21 */     this.tagCompound = tagCompoundIn;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void readPacketData(PacketBuffer buf) throws IOException {
/* 28 */     this.entityId = buf.readVarIntFromBuffer();
/* 29 */     this.tagCompound = buf.readNBTTagCompoundFromBuffer();
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void writePacketData(PacketBuffer buf) throws IOException {
/* 36 */     buf.writeVarIntToBuffer(this.entityId);
/* 37 */     buf.writeNBTTagCompoundToBuffer(this.tagCompound);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void processPacket(INetHandlerPlayClient handler) {
/* 44 */     handler.handleEntityNBT(this);
/*    */   }
/*    */   
/*    */   public NBTTagCompound getTagCompound() {
/* 48 */     return this.tagCompound;
/*    */   }
/*    */   
/*    */   public Entity getEntity(World worldIn) {
/* 52 */     return worldIn.getEntityByID(this.entityId);
/*    */   }
/*    */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\minecraft\network\play\server\S49PacketUpdateEntityNBT.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */