/*    */ package net.minecraft.entity.item;
/*    */ import net.minecraft.entity.Entity;
/*    */ import net.minecraft.entity.EntityLivingBase;
/*    */ import net.minecraft.entity.projectile.EntityThrowable;
/*    */ import net.minecraft.util.BlockPos;
/*    */ import net.minecraft.util.MovingObjectPosition;
/*    */ import net.minecraft.world.World;
/*    */ 
/*    */ public class EntityExpBottle extends EntityThrowable {
/*    */   public EntityExpBottle(World worldIn) {
/* 11 */     super(worldIn);
/*    */   }
/*    */   
/*    */   public EntityExpBottle(World worldIn, EntityLivingBase p_i1786_2_) {
/* 15 */     super(worldIn, p_i1786_2_);
/*    */   }
/*    */   
/*    */   public EntityExpBottle(World worldIn, double x, double y, double z) {
/* 19 */     super(worldIn, x, y, z);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   protected float getGravityVelocity() {
/* 26 */     return 0.07F;
/*    */   }
/*    */   
/*    */   protected float getVelocity() {
/* 30 */     return 0.7F;
/*    */   }
/*    */   
/*    */   protected float getInaccuracy() {
/* 34 */     return -20.0F;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   protected void onImpact(MovingObjectPosition p_70184_1_) {
/* 41 */     if (!this.worldObj.isRemote) {
/* 42 */       this.worldObj.playAuxSFX(2002, new BlockPos((Entity)this), 0);
/* 43 */       int i = 3 + this.worldObj.rand.nextInt(5) + this.worldObj.rand.nextInt(5);
/*    */       
/* 45 */       while (i > 0) {
/* 46 */         int j = EntityXPOrb.getXPSplit(i);
/* 47 */         i -= j;
/* 48 */         this.worldObj.spawnEntityInWorld(new EntityXPOrb(this.worldObj, this.posX, this.posY, this.posZ, j));
/*    */       } 
/*    */       
/* 51 */       setDead();
/*    */     } 
/*    */   }
/*    */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\minecraft\entity\item\EntityExpBottle.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */