/*    */ package net.minecraft.entity.monster;
/*    */ 
/*    */ import net.minecraft.entity.Entity;
/*    */ import net.minecraft.entity.EntityLivingBase;
/*    */ import net.minecraft.entity.IEntityLivingData;
/*    */ import net.minecraft.entity.SharedMonsterAttributes;
/*    */ import net.minecraft.potion.Potion;
/*    */ import net.minecraft.potion.PotionEffect;
/*    */ import net.minecraft.world.DifficultyInstance;
/*    */ import net.minecraft.world.EnumDifficulty;
/*    */ import net.minecraft.world.World;
/*    */ 
/*    */ public class EntityCaveSpider extends EntitySpider {
/*    */   public EntityCaveSpider(World worldIn) {
/* 15 */     super(worldIn);
/* 16 */     setSize(0.7F, 0.5F);
/*    */   }
/*    */   
/*    */   protected void applyEntityAttributes() {
/* 20 */     super.applyEntityAttributes();
/* 21 */     getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(12.0D);
/*    */   }
/*    */   
/*    */   public boolean attackEntityAsMob(Entity entityIn) {
/* 25 */     if (super.attackEntityAsMob(entityIn)) {
/* 26 */       if (entityIn instanceof EntityLivingBase) {
/* 27 */         int i = 0;
/*    */         
/* 29 */         if (this.worldObj.getDifficulty() == EnumDifficulty.NORMAL) {
/* 30 */           i = 7;
/* 31 */         } else if (this.worldObj.getDifficulty() == EnumDifficulty.HARD) {
/* 32 */           i = 15;
/*    */         } 
/*    */         
/* 35 */         if (i > 0) {
/* 36 */           ((EntityLivingBase)entityIn).addPotionEffect(new PotionEffect(Potion.poison.id, i * 20, 0));
/*    */         }
/*    */       } 
/*    */       
/* 40 */       return true;
/*    */     } 
/* 42 */     return false;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public IEntityLivingData onInitialSpawn(DifficultyInstance difficulty, IEntityLivingData livingdata) {
/* 51 */     return livingdata;
/*    */   }
/*    */   
/*    */   public float getEyeHeight() {
/* 55 */     return 0.45F;
/*    */   }
/*    */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\minecraft\entity\monster\EntityCaveSpider.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */