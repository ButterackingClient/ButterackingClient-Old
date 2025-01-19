/*     */ package net.minecraft.village;
/*     */ 
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.entity.EntityLiving;
/*     */ import net.minecraft.entity.monster.EntityZombie;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.util.BlockPos;
/*     */ import net.minecraft.util.MathHelper;
/*     */ import net.minecraft.util.Vec3;
/*     */ import net.minecraft.world.SpawnerAnimals;
/*     */ import net.minecraft.world.World;
/*     */ 
/*     */ public class VillageSiege
/*     */ {
/*     */   private World worldObj;
/*     */   private boolean field_75535_b;
/*  19 */   private int field_75536_c = -1;
/*     */   
/*     */   private int field_75533_d;
/*     */   
/*     */   private int field_75534_e;
/*     */   
/*     */   private Village theVillage;
/*     */   
/*     */   private int field_75532_g;
/*     */   private int field_75538_h;
/*     */   private int field_75539_i;
/*     */   
/*     */   public VillageSiege(World worldIn) {
/*  32 */     this.worldObj = worldIn;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void tick() {
/*  39 */     if (this.worldObj.isDaytime()) {
/*  40 */       this.field_75536_c = 0;
/*  41 */     } else if (this.field_75536_c != 2) {
/*  42 */       if (this.field_75536_c == 0) {
/*  43 */         float f = this.worldObj.getCelestialAngle(0.0F);
/*     */         
/*  45 */         if (f < 0.5D || f > 0.501D) {
/*     */           return;
/*     */         }
/*     */         
/*  49 */         this.field_75536_c = (this.worldObj.rand.nextInt(10) == 0) ? 1 : 2;
/*  50 */         this.field_75535_b = false;
/*     */         
/*  52 */         if (this.field_75536_c == 2) {
/*     */           return;
/*     */         }
/*     */       } 
/*     */       
/*  57 */       if (this.field_75536_c != -1) {
/*  58 */         if (!this.field_75535_b) {
/*  59 */           if (!func_75529_b()) {
/*     */             return;
/*     */           }
/*     */           
/*  63 */           this.field_75535_b = true;
/*     */         } 
/*     */         
/*  66 */         if (this.field_75534_e > 0) {
/*  67 */           this.field_75534_e--;
/*     */         } else {
/*  69 */           this.field_75534_e = 2;
/*     */           
/*  71 */           if (this.field_75533_d > 0) {
/*  72 */             spawnZombie();
/*  73 */             this.field_75533_d--;
/*     */           } else {
/*  75 */             this.field_75536_c = 2;
/*     */           } 
/*     */         } 
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   private boolean func_75529_b() {
/*  83 */     List<EntityPlayer> list = this.worldObj.playerEntities;
/*  84 */     Iterator<EntityPlayer> iterator = list.iterator();
/*     */     
/*     */     while (true) {
/*  87 */       if (!iterator.hasNext()) {
/*  88 */         return false;
/*     */       }
/*     */       
/*  91 */       EntityPlayer entityplayer = iterator.next();
/*     */       
/*  93 */       if (!entityplayer.isSpectator()) {
/*  94 */         this.theVillage = this.worldObj.getVillageCollection().getNearestVillage(new BlockPos((Entity)entityplayer), 1);
/*     */         
/*  96 */         if (this.theVillage != null && this.theVillage.getNumVillageDoors() >= 10 && this.theVillage.getTicksSinceLastDoorAdding() >= 20 && this.theVillage.getNumVillagers() >= 20) {
/*  97 */           BlockPos blockpos = this.theVillage.getCenter();
/*  98 */           float f = this.theVillage.getVillageRadius();
/*  99 */           boolean flag = false;
/*     */           
/* 101 */           for (int i = 0; i < 10; i++) {
/* 102 */             float f1 = this.worldObj.rand.nextFloat() * 3.1415927F * 2.0F;
/* 103 */             this.field_75532_g = blockpos.getX() + (int)((MathHelper.cos(f1) * f) * 0.9D);
/* 104 */             this.field_75538_h = blockpos.getY();
/* 105 */             this.field_75539_i = blockpos.getZ() + (int)((MathHelper.sin(f1) * f) * 0.9D);
/* 106 */             flag = false;
/*     */             
/* 108 */             for (Village village : this.worldObj.getVillageCollection().getVillageList()) {
/* 109 */               if (village != this.theVillage && village.func_179866_a(new BlockPos(this.field_75532_g, this.field_75538_h, this.field_75539_i))) {
/* 110 */                 flag = true;
/*     */                 
/*     */                 break;
/*     */               } 
/*     */             } 
/* 115 */             if (!flag) {
/*     */               break;
/*     */             }
/*     */           } 
/*     */           
/* 120 */           if (flag) {
/* 121 */             return false;
/*     */           }
/*     */           
/* 124 */           Vec3 vec3 = func_179867_a(new BlockPos(this.field_75532_g, this.field_75538_h, this.field_75539_i));
/*     */           
/* 126 */           if (vec3 != null) {
/*     */             break;
/*     */           }
/*     */         } 
/*     */       } 
/*     */     } 
/*     */     
/* 133 */     this.field_75534_e = 0;
/* 134 */     this.field_75533_d = 20;
/* 135 */     return true;
/*     */   }
/*     */   private boolean spawnZombie() {
/*     */     EntityZombie entityzombie;
/* 139 */     Vec3 vec3 = func_179867_a(new BlockPos(this.field_75532_g, this.field_75538_h, this.field_75539_i));
/*     */     
/* 141 */     if (vec3 == null) {
/* 142 */       return false;
/*     */     }
/*     */ 
/*     */     
/*     */     try {
/* 147 */       entityzombie = new EntityZombie(this.worldObj);
/* 148 */       entityzombie.onInitialSpawn(this.worldObj.getDifficultyForLocation(new BlockPos((Entity)entityzombie)), null);
/* 149 */       entityzombie.setVillager(false);
/* 150 */     } catch (Exception exception) {
/* 151 */       exception.printStackTrace();
/* 152 */       return false;
/*     */     } 
/*     */     
/* 155 */     entityzombie.setLocationAndAngles(vec3.xCoord, vec3.yCoord, vec3.zCoord, this.worldObj.rand.nextFloat() * 360.0F, 0.0F);
/* 156 */     this.worldObj.spawnEntityInWorld((Entity)entityzombie);
/* 157 */     BlockPos blockpos = this.theVillage.getCenter();
/* 158 */     entityzombie.setHomePosAndDistance(blockpos, this.theVillage.getVillageRadius());
/* 159 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   private Vec3 func_179867_a(BlockPos p_179867_1_) {
/* 164 */     for (int i = 0; i < 10; i++) {
/* 165 */       BlockPos blockpos = p_179867_1_.add(this.worldObj.rand.nextInt(16) - 8, this.worldObj.rand.nextInt(6) - 3, this.worldObj.rand.nextInt(16) - 8);
/*     */       
/* 167 */       if (this.theVillage.func_179866_a(blockpos) && SpawnerAnimals.canCreatureTypeSpawnAtLocation(EntityLiving.SpawnPlacementType.ON_GROUND, this.worldObj, blockpos)) {
/* 168 */         return new Vec3(blockpos.getX(), blockpos.getY(), blockpos.getZ());
/*     */       }
/*     */     } 
/*     */     
/* 172 */     return null;
/*     */   }
/*     */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\minecraft\village\VillageSiege.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */