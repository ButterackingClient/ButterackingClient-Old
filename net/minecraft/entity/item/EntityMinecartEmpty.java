/*    */ package net.minecraft.entity.item;
/*    */ 
/*    */ import net.minecraft.entity.player.EntityPlayer;
/*    */ import net.minecraft.world.World;
/*    */ 
/*    */ public class EntityMinecartEmpty
/*    */   extends EntityMinecart {
/*    */   public EntityMinecartEmpty(World worldIn) {
/*  9 */     super(worldIn);
/*    */   }
/*    */   
/*    */   public EntityMinecartEmpty(World worldIn, double x, double y, double z) {
/* 13 */     super(worldIn, x, y, z);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public boolean interactFirst(EntityPlayer playerIn) {
/* 20 */     if (this.riddenByEntity != null && this.riddenByEntity instanceof EntityPlayer && this.riddenByEntity != playerIn)
/* 21 */       return true; 
/* 22 */     if (this.riddenByEntity != null && this.riddenByEntity != playerIn) {
/* 23 */       return false;
/*    */     }
/* 25 */     if (!this.worldObj.isRemote) {
/* 26 */       playerIn.mountEntity(this);
/*    */     }
/*    */     
/* 29 */     return true;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void onActivatorRailPass(int x, int y, int z, boolean receivingPower) {
/* 37 */     if (receivingPower) {
/* 38 */       if (this.riddenByEntity != null) {
/* 39 */         this.riddenByEntity.mountEntity(null);
/*    */       }
/*    */       
/* 42 */       if (getRollingAmplitude() == 0) {
/* 43 */         setRollingDirection(-getRollingDirection());
/* 44 */         setRollingAmplitude(10);
/* 45 */         setDamage(50.0F);
/* 46 */         setBeenAttacked();
/*    */       } 
/*    */     } 
/*    */   }
/*    */   
/*    */   public EntityMinecart.EnumMinecartType getMinecartType() {
/* 52 */     return EntityMinecart.EnumMinecartType.RIDEABLE;
/*    */   }
/*    */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\minecraft\entity\item\EntityMinecartEmpty.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */