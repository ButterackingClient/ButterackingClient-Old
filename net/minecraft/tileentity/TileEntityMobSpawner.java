/*    */ package net.minecraft.tileentity;
/*    */ 
/*    */ import net.minecraft.init.Blocks;
/*    */ import net.minecraft.nbt.NBTTagCompound;
/*    */ import net.minecraft.network.Packet;
/*    */ import net.minecraft.network.play.server.S35PacketUpdateTileEntity;
/*    */ import net.minecraft.util.BlockPos;
/*    */ import net.minecraft.util.ITickable;
/*    */ import net.minecraft.world.World;
/*    */ 
/*    */ public class TileEntityMobSpawner extends TileEntity implements ITickable {
/* 12 */   private final MobSpawnerBaseLogic spawnerLogic = new MobSpawnerBaseLogic() {
/*    */       public void func_98267_a(int id) {
/* 14 */         TileEntityMobSpawner.this.worldObj.addBlockEvent(TileEntityMobSpawner.this.pos, Blocks.mob_spawner, id, 0);
/*    */       }
/*    */       
/*    */       public World getSpawnerWorld() {
/* 18 */         return TileEntityMobSpawner.this.worldObj;
/*    */       }
/*    */       
/*    */       public BlockPos getSpawnerPosition() {
/* 22 */         return TileEntityMobSpawner.this.pos;
/*    */       }
/*    */       
/*    */       public void setRandomEntity(MobSpawnerBaseLogic.WeightedRandomMinecart p_98277_1_) {
/* 26 */         super.setRandomEntity(p_98277_1_);
/*    */         
/* 28 */         if (getSpawnerWorld() != null) {
/* 29 */           getSpawnerWorld().markBlockForUpdate(TileEntityMobSpawner.this.pos);
/*    */         }
/*    */       }
/*    */     };
/*    */   
/*    */   public void readFromNBT(NBTTagCompound compound) {
/* 35 */     super.readFromNBT(compound);
/* 36 */     this.spawnerLogic.readFromNBT(compound);
/*    */   }
/*    */   
/*    */   public void writeToNBT(NBTTagCompound compound) {
/* 40 */     super.writeToNBT(compound);
/* 41 */     this.spawnerLogic.writeToNBT(compound);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void update() {
/* 48 */     this.spawnerLogic.updateSpawner();
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public Packet getDescriptionPacket() {
/* 56 */     NBTTagCompound nbttagcompound = new NBTTagCompound();
/* 57 */     writeToNBT(nbttagcompound);
/* 58 */     nbttagcompound.removeTag("SpawnPotentials");
/* 59 */     return (Packet)new S35PacketUpdateTileEntity(this.pos, 1, nbttagcompound);
/*    */   }
/*    */   
/*    */   public boolean receiveClientEvent(int id, int type) {
/* 63 */     return this.spawnerLogic.setDelayToMin(id) ? true : super.receiveClientEvent(id, type);
/*    */   }
/*    */   
/*    */   public boolean func_183000_F() {
/* 67 */     return true;
/*    */   }
/*    */   
/*    */   public MobSpawnerBaseLogic getSpawnerBaseLogic() {
/* 71 */     return this.spawnerLogic;
/*    */   }
/*    */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\minecraft\tileentity\TileEntityMobSpawner.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */