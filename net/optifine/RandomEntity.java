/*    */ package net.optifine;
/*    */ 
/*    */ import java.util.UUID;
/*    */ import net.minecraft.entity.Entity;
/*    */ import net.minecraft.entity.EntityLiving;
/*    */ import net.minecraft.util.BlockPos;
/*    */ import net.minecraft.world.biome.BiomeGenBase;
/*    */ 
/*    */ public class RandomEntity
/*    */   implements IRandomEntity {
/*    */   private Entity entity;
/*    */   
/*    */   public int getId() {
/* 14 */     UUID uuid = this.entity.getUniqueID();
/* 15 */     long i = uuid.getLeastSignificantBits();
/* 16 */     int j = (int)(i & 0x7FFFFFFFL);
/* 17 */     return j;
/*    */   }
/*    */   
/*    */   public BlockPos getSpawnPosition() {
/* 21 */     return (this.entity.getDataWatcher()).spawnPosition;
/*    */   }
/*    */   
/*    */   public BiomeGenBase getSpawnBiome() {
/* 25 */     return (this.entity.getDataWatcher()).spawnBiome;
/*    */   }
/*    */   
/*    */   public String getName() {
/* 29 */     return this.entity.hasCustomName() ? this.entity.getCustomNameTag() : null;
/*    */   }
/*    */   
/*    */   public int getHealth() {
/* 33 */     if (!(this.entity instanceof EntityLiving)) {
/* 34 */       return 0;
/*    */     }
/* 36 */     EntityLiving entityliving = (EntityLiving)this.entity;
/* 37 */     return (int)entityliving.getHealth();
/*    */   }
/*    */ 
/*    */   
/*    */   public int getMaxHealth() {
/* 42 */     if (!(this.entity instanceof EntityLiving)) {
/* 43 */       return 0;
/*    */     }
/* 45 */     EntityLiving entityliving = (EntityLiving)this.entity;
/* 46 */     return (int)entityliving.getMaxHealth();
/*    */   }
/*    */ 
/*    */   
/*    */   public Entity getEntity() {
/* 51 */     return this.entity;
/*    */   }
/*    */   
/*    */   public void setEntity(Entity entity) {
/* 55 */     this.entity = entity;
/*    */   }
/*    */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\optifine\RandomEntity.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */