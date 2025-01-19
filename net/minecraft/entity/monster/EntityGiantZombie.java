/*    */ package net.minecraft.entity.monster;
/*    */ 
/*    */ import net.minecraft.entity.SharedMonsterAttributes;
/*    */ import net.minecraft.util.BlockPos;
/*    */ import net.minecraft.world.World;
/*    */ 
/*    */ public class EntityGiantZombie extends EntityMob {
/*    */   public EntityGiantZombie(World worldIn) {
/*  9 */     super(worldIn);
/* 10 */     setSize(this.width * 6.0F, this.height * 6.0F);
/*    */   }
/*    */   
/*    */   public float getEyeHeight() {
/* 14 */     return 10.440001F;
/*    */   }
/*    */   
/*    */   protected void applyEntityAttributes() {
/* 18 */     super.applyEntityAttributes();
/* 19 */     getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(100.0D);
/* 20 */     getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(0.5D);
/* 21 */     getEntityAttribute(SharedMonsterAttributes.attackDamage).setBaseValue(50.0D);
/*    */   }
/*    */   
/*    */   public float getBlockPathWeight(BlockPos pos) {
/* 25 */     return this.worldObj.getLightBrightness(pos) - 0.5F;
/*    */   }
/*    */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\minecraft\entity\monster\EntityGiantZombie.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */