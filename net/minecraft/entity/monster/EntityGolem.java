/*    */ package net.minecraft.entity.monster;
/*    */ 
/*    */ import net.minecraft.entity.EntityCreature;
/*    */ import net.minecraft.entity.passive.IAnimals;
/*    */ import net.minecraft.world.World;
/*    */ 
/*    */ public abstract class EntityGolem extends EntityCreature implements IAnimals {
/*    */   public EntityGolem(World worldIn) {
/*  9 */     super(worldIn);
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public void fall(float distance, float damageMultiplier) {}
/*    */ 
/*    */ 
/*    */   
/*    */   protected String getLivingSound() {
/* 19 */     return "none";
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   protected String getHurtSound() {
/* 26 */     return "none";
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   protected String getDeathSound() {
/* 33 */     return "none";
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public int getTalkInterval() {
/* 40 */     return 120;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   protected boolean canDespawn() {
/* 47 */     return false;
/*    */   }
/*    */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\minecraft\entity\monster\EntityGolem.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */