/*    */ package net.minecraft.entity.projectile;
/*    */ import net.minecraft.entity.Entity;
/*    */ import net.minecraft.entity.EntityLivingBase;
/*    */ import net.minecraft.entity.passive.EntityChicken;
/*    */ import net.minecraft.init.Items;
/*    */ import net.minecraft.item.Item;
/*    */ import net.minecraft.util.DamageSource;
/*    */ import net.minecraft.util.EnumParticleTypes;
/*    */ import net.minecraft.util.MovingObjectPosition;
/*    */ import net.minecraft.world.World;
/*    */ 
/*    */ public class EntityEgg extends EntityThrowable {
/*    */   public EntityEgg(World worldIn) {
/* 14 */     super(worldIn);
/*    */   }
/*    */   
/*    */   public EntityEgg(World worldIn, EntityLivingBase throwerIn) {
/* 18 */     super(worldIn, throwerIn);
/*    */   }
/*    */   
/*    */   public EntityEgg(World worldIn, double x, double y, double z) {
/* 22 */     super(worldIn, x, y, z);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   protected void onImpact(MovingObjectPosition p_70184_1_) {
/* 29 */     if (p_70184_1_.entityHit != null) {
/* 30 */       p_70184_1_.entityHit.attackEntityFrom(DamageSource.causeThrownDamage(this, (Entity)getThrower()), 0.0F);
/*    */     }
/*    */     
/* 33 */     if (!this.worldObj.isRemote && this.rand.nextInt(8) == 0) {
/* 34 */       int i = 1;
/*    */       
/* 36 */       if (this.rand.nextInt(32) == 0) {
/* 37 */         i = 4;
/*    */       }
/*    */       
/* 40 */       for (int j = 0; j < i; j++) {
/* 41 */         EntityChicken entitychicken = new EntityChicken(this.worldObj);
/* 42 */         entitychicken.setGrowingAge(-24000);
/* 43 */         entitychicken.setLocationAndAngles(this.posX, this.posY, this.posZ, this.rotationYaw, 0.0F);
/* 44 */         this.worldObj.spawnEntityInWorld((Entity)entitychicken);
/*    */       } 
/*    */     } 
/*    */     
/* 48 */     double d0 = 0.08D;
/*    */     
/* 50 */     for (int k = 0; k < 8; k++) {
/* 51 */       this.worldObj.spawnParticle(EnumParticleTypes.ITEM_CRACK, this.posX, this.posY, this.posZ, (this.rand.nextFloat() - 0.5D) * 0.08D, (this.rand.nextFloat() - 0.5D) * 0.08D, (this.rand.nextFloat() - 0.5D) * 0.08D, new int[] { Item.getIdFromItem(Items.egg) });
/*    */     } 
/*    */     
/* 54 */     if (!this.worldObj.isRemote)
/* 55 */       setDead(); 
/*    */   }
/*    */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\minecraft\entity\projectile\EntityEgg.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */