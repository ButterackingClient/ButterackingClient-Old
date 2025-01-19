/*    */ package net.minecraft.tileentity;
/*    */ 
/*    */ import io.netty.buffer.ByteBuf;
/*    */ import net.minecraft.command.CommandResultStats;
/*    */ import net.minecraft.command.server.CommandBlockLogic;
/*    */ import net.minecraft.entity.Entity;
/*    */ import net.minecraft.nbt.NBTTagCompound;
/*    */ import net.minecraft.network.Packet;
/*    */ import net.minecraft.network.play.server.S35PacketUpdateTileEntity;
/*    */ import net.minecraft.util.BlockPos;
/*    */ import net.minecraft.util.Vec3;
/*    */ import net.minecraft.world.World;
/*    */ 
/*    */ public class TileEntityCommandBlock extends TileEntity {
/* 15 */   private final CommandBlockLogic commandBlockLogic = new CommandBlockLogic() {
/*    */       public BlockPos getPosition() {
/* 17 */         return TileEntityCommandBlock.this.pos;
/*    */       }
/*    */       
/*    */       public Vec3 getPositionVector() {
/* 21 */         return new Vec3(TileEntityCommandBlock.this.pos.getX() + 0.5D, TileEntityCommandBlock.this.pos.getY() + 0.5D, TileEntityCommandBlock.this.pos.getZ() + 0.5D);
/*    */       }
/*    */       
/*    */       public World getEntityWorld() {
/* 25 */         return TileEntityCommandBlock.this.getWorld();
/*    */       }
/*    */       
/*    */       public void setCommand(String command) {
/* 29 */         super.setCommand(command);
/* 30 */         TileEntityCommandBlock.this.markDirty();
/*    */       }
/*    */       
/*    */       public void updateCommand() {
/* 34 */         TileEntityCommandBlock.this.getWorld().markBlockForUpdate(TileEntityCommandBlock.this.pos);
/*    */       }
/*    */       
/*    */       public int func_145751_f() {
/* 38 */         return 0;
/*    */       }
/*    */       
/*    */       public void func_145757_a(ByteBuf p_145757_1_) {
/* 42 */         p_145757_1_.writeInt(TileEntityCommandBlock.this.pos.getX());
/* 43 */         p_145757_1_.writeInt(TileEntityCommandBlock.this.pos.getY());
/* 44 */         p_145757_1_.writeInt(TileEntityCommandBlock.this.pos.getZ());
/*    */       }
/*    */       
/*    */       public Entity getCommandSenderEntity() {
/* 48 */         return null;
/*    */       }
/*    */     };
/*    */   
/*    */   public void writeToNBT(NBTTagCompound compound) {
/* 53 */     super.writeToNBT(compound);
/* 54 */     this.commandBlockLogic.writeDataToNBT(compound);
/*    */   }
/*    */   
/*    */   public void readFromNBT(NBTTagCompound compound) {
/* 58 */     super.readFromNBT(compound);
/* 59 */     this.commandBlockLogic.readDataFromNBT(compound);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public Packet getDescriptionPacket() {
/* 67 */     NBTTagCompound nbttagcompound = new NBTTagCompound();
/* 68 */     writeToNBT(nbttagcompound);
/* 69 */     return (Packet)new S35PacketUpdateTileEntity(this.pos, 2, nbttagcompound);
/*    */   }
/*    */   
/*    */   public boolean func_183000_F() {
/* 73 */     return true;
/*    */   }
/*    */   
/*    */   public CommandBlockLogic getCommandBlockLogic() {
/* 77 */     return this.commandBlockLogic;
/*    */   }
/*    */   
/*    */   public CommandResultStats getCommandResultStats() {
/* 81 */     return this.commandBlockLogic.getCommandResultStats();
/*    */   }
/*    */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\minecraft\tileentity\TileEntityCommandBlock.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */