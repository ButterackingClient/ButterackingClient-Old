/*    */ package net.minecraft.tileentity;
/*    */ 
/*    */ import net.minecraft.entity.player.EntityPlayer;
/*    */ import net.minecraft.init.Blocks;
/*    */ import net.minecraft.util.ITickable;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class TileEntityEnderChest
/*    */   extends TileEntity
/*    */   implements ITickable
/*    */ {
/*    */   public float lidAngle;
/*    */   public float prevLidAngle;
/*    */   public int numPlayersUsing;
/*    */   private int ticksSinceSync;
/*    */   
/*    */   public void update() {
/* 21 */     if (++this.ticksSinceSync % 20 * 4 == 0) {
/* 22 */       this.worldObj.addBlockEvent(this.pos, Blocks.ender_chest, 1, this.numPlayersUsing);
/*    */     }
/*    */     
/* 25 */     this.prevLidAngle = this.lidAngle;
/* 26 */     int i = this.pos.getX();
/* 27 */     int j = this.pos.getY();
/* 28 */     int k = this.pos.getZ();
/* 29 */     float f = 0.1F;
/*    */     
/* 31 */     if (this.numPlayersUsing > 0 && this.lidAngle == 0.0F) {
/* 32 */       double d0 = i + 0.5D;
/* 33 */       double d1 = k + 0.5D;
/* 34 */       this.worldObj.playSoundEffect(d0, j + 0.5D, d1, "random.chestopen", 0.5F, this.worldObj.rand.nextFloat() * 0.1F + 0.9F);
/*    */     } 
/*    */     
/* 37 */     if ((this.numPlayersUsing == 0 && this.lidAngle > 0.0F) || (this.numPlayersUsing > 0 && this.lidAngle < 1.0F)) {
/* 38 */       float f2 = this.lidAngle;
/*    */       
/* 40 */       if (this.numPlayersUsing > 0) {
/* 41 */         this.lidAngle += f;
/*    */       } else {
/* 43 */         this.lidAngle -= f;
/*    */       } 
/*    */       
/* 46 */       if (this.lidAngle > 1.0F) {
/* 47 */         this.lidAngle = 1.0F;
/*    */       }
/*    */       
/* 50 */       float f1 = 0.5F;
/*    */       
/* 52 */       if (this.lidAngle < f1 && f2 >= f1) {
/* 53 */         double d3 = i + 0.5D;
/* 54 */         double d2 = k + 0.5D;
/* 55 */         this.worldObj.playSoundEffect(d3, j + 0.5D, d2, "random.chestclosed", 0.5F, this.worldObj.rand.nextFloat() * 0.1F + 0.9F);
/*    */       } 
/*    */       
/* 58 */       if (this.lidAngle < 0.0F) {
/* 59 */         this.lidAngle = 0.0F;
/*    */       }
/*    */     } 
/*    */   }
/*    */   
/*    */   public boolean receiveClientEvent(int id, int type) {
/* 65 */     if (id == 1) {
/* 66 */       this.numPlayersUsing = type;
/* 67 */       return true;
/*    */     } 
/* 69 */     return super.receiveClientEvent(id, type);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void invalidate() {
/* 77 */     updateContainingBlockInfo();
/* 78 */     super.invalidate();
/*    */   }
/*    */   
/*    */   public void openChest() {
/* 82 */     this.numPlayersUsing++;
/* 83 */     this.worldObj.addBlockEvent(this.pos, Blocks.ender_chest, 1, this.numPlayersUsing);
/*    */   }
/*    */   
/*    */   public void closeChest() {
/* 87 */     this.numPlayersUsing--;
/* 88 */     this.worldObj.addBlockEvent(this.pos, Blocks.ender_chest, 1, this.numPlayersUsing);
/*    */   }
/*    */   
/*    */   public boolean canBeUsed(EntityPlayer p_145971_1_) {
/* 92 */     return (this.worldObj.getTileEntity(this.pos) != this) ? false : ((p_145971_1_.getDistanceSq(this.pos.getX() + 0.5D, this.pos.getY() + 0.5D, this.pos.getZ() + 0.5D) <= 64.0D));
/*    */   }
/*    */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\minecraft\tileentity\TileEntityEnderChest.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */