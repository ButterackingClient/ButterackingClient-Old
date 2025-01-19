/*    */ package net.minecraft.network.play.client;
/*    */ 
/*    */ import java.io.IOException;
/*    */ import net.minecraft.item.ItemStack;
/*    */ import net.minecraft.network.INetHandler;
/*    */ import net.minecraft.network.Packet;
/*    */ import net.minecraft.network.PacketBuffer;
/*    */ import net.minecraft.network.play.INetHandlerPlayServer;
/*    */ import net.minecraft.util.BlockPos;
/*    */ 
/*    */ public class C08PacketPlayerBlockPlacement implements Packet<INetHandlerPlayServer> {
/* 12 */   private static final BlockPos field_179726_a = new BlockPos(-1, -1, -1);
/*    */   
/*    */   private BlockPos position;
/*    */   private int placedBlockDirection;
/*    */   private ItemStack stack;
/*    */   private float facingX;
/*    */   private float facingY;
/*    */   private float facingZ;
/*    */   
/*    */   public C08PacketPlayerBlockPlacement() {}
/*    */   
/*    */   public C08PacketPlayerBlockPlacement(ItemStack stackIn) {
/* 24 */     this(field_179726_a, 255, stackIn, 0.0F, 0.0F, 0.0F);
/*    */   }
/*    */   
/*    */   public C08PacketPlayerBlockPlacement(BlockPos positionIn, int placedBlockDirectionIn, ItemStack stackIn, float facingXIn, float facingYIn, float facingZIn) {
/* 28 */     this.position = positionIn;
/* 29 */     this.placedBlockDirection = placedBlockDirectionIn;
/* 30 */     this.stack = (stackIn != null) ? stackIn.copy() : null;
/* 31 */     this.facingX = facingXIn;
/* 32 */     this.facingY = facingYIn;
/* 33 */     this.facingZ = facingZIn;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void readPacketData(PacketBuffer buf) throws IOException {
/* 40 */     this.position = buf.readBlockPos();
/* 41 */     this.placedBlockDirection = buf.readUnsignedByte();
/* 42 */     this.stack = buf.readItemStackFromBuffer();
/* 43 */     this.facingX = buf.readUnsignedByte() / 16.0F;
/* 44 */     this.facingY = buf.readUnsignedByte() / 16.0F;
/* 45 */     this.facingZ = buf.readUnsignedByte() / 16.0F;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void writePacketData(PacketBuffer buf) throws IOException {
/* 52 */     buf.writeBlockPos(this.position);
/* 53 */     buf.writeByte(this.placedBlockDirection);
/* 54 */     buf.writeItemStackToBuffer(this.stack);
/* 55 */     buf.writeByte((int)(this.facingX * 16.0F));
/* 56 */     buf.writeByte((int)(this.facingY * 16.0F));
/* 57 */     buf.writeByte((int)(this.facingZ * 16.0F));
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void processPacket(INetHandlerPlayServer handler) {
/* 64 */     handler.processPlayerBlockPlacement(this);
/*    */   }
/*    */   
/*    */   public BlockPos getPosition() {
/* 68 */     return this.position;
/*    */   }
/*    */   
/*    */   public int getPlacedBlockDirection() {
/* 72 */     return this.placedBlockDirection;
/*    */   }
/*    */   
/*    */   public ItemStack getStack() {
/* 76 */     return this.stack;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public float getPlacedBlockOffsetX() {
/* 83 */     return this.facingX;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public float getPlacedBlockOffsetY() {
/* 90 */     return this.facingY;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public float getPlacedBlockOffsetZ() {
/* 97 */     return this.facingZ;
/*    */   }
/*    */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\minecraft\network\play\client\C08PacketPlayerBlockPlacement.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */