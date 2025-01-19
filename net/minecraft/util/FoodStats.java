/*     */ package net.minecraft.util;
/*     */ 
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.item.ItemFood;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraft.nbt.NBTTagCompound;
/*     */ import net.minecraft.world.EnumDifficulty;
/*     */ 
/*     */ 
/*     */ 
/*     */ public class FoodStats
/*     */ {
/*  13 */   private int foodLevel = 20;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  18 */   private float foodSaturationLevel = 5.0F;
/*     */ 
/*     */ 
/*     */   
/*     */   private float foodExhaustionLevel;
/*     */ 
/*     */ 
/*     */   
/*     */   private int foodTimer;
/*     */ 
/*     */   
/*  29 */   private int prevFoodLevel = 20;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void addStats(int foodLevelIn, float foodSaturationModifier) {
/*  35 */     this.foodLevel = Math.min(foodLevelIn + this.foodLevel, 20);
/*  36 */     this.foodSaturationLevel = Math.min(this.foodSaturationLevel + foodLevelIn * foodSaturationModifier * 2.0F, this.foodLevel);
/*     */   }
/*     */   
/*     */   public void addStats(ItemFood foodItem, ItemStack p_151686_2_) {
/*  40 */     addStats(foodItem.getHealAmount(p_151686_2_), foodItem.getSaturationModifier(p_151686_2_));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void onUpdate(EntityPlayer player) {
/*  47 */     EnumDifficulty enumdifficulty = player.worldObj.getDifficulty();
/*  48 */     this.prevFoodLevel = this.foodLevel;
/*     */     
/*  50 */     if (this.foodExhaustionLevel > 4.0F) {
/*  51 */       this.foodExhaustionLevel -= 4.0F;
/*     */       
/*  53 */       if (this.foodSaturationLevel > 0.0F) {
/*  54 */         this.foodSaturationLevel = Math.max(this.foodSaturationLevel - 1.0F, 0.0F);
/*  55 */       } else if (enumdifficulty != EnumDifficulty.PEACEFUL) {
/*  56 */         this.foodLevel = Math.max(this.foodLevel - 1, 0);
/*     */       } 
/*     */     } 
/*     */     
/*  60 */     if (player.worldObj.getGameRules().getBoolean("naturalRegeneration") && this.foodLevel >= 18 && player.shouldHeal()) {
/*  61 */       this.foodTimer++;
/*     */       
/*  63 */       if (this.foodTimer >= 80) {
/*  64 */         player.heal(1.0F);
/*  65 */         addExhaustion(3.0F);
/*  66 */         this.foodTimer = 0;
/*     */       } 
/*  68 */     } else if (this.foodLevel <= 0) {
/*  69 */       this.foodTimer++;
/*     */       
/*  71 */       if (this.foodTimer >= 80) {
/*  72 */         if (player.getHealth() > 10.0F || enumdifficulty == EnumDifficulty.HARD || (player.getHealth() > 1.0F && enumdifficulty == EnumDifficulty.NORMAL)) {
/*  73 */           player.attackEntityFrom(DamageSource.starve, 1.0F);
/*     */         }
/*     */         
/*  76 */         this.foodTimer = 0;
/*     */       } 
/*     */     } else {
/*  79 */       this.foodTimer = 0;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void readNBT(NBTTagCompound p_75112_1_) {
/*  87 */     if (p_75112_1_.hasKey("foodLevel", 99)) {
/*  88 */       this.foodLevel = p_75112_1_.getInteger("foodLevel");
/*  89 */       this.foodTimer = p_75112_1_.getInteger("foodTickTimer");
/*  90 */       this.foodSaturationLevel = p_75112_1_.getFloat("foodSaturationLevel");
/*  91 */       this.foodExhaustionLevel = p_75112_1_.getFloat("foodExhaustionLevel");
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void writeNBT(NBTTagCompound p_75117_1_) {
/*  99 */     p_75117_1_.setInteger("foodLevel", this.foodLevel);
/* 100 */     p_75117_1_.setInteger("foodTickTimer", this.foodTimer);
/* 101 */     p_75117_1_.setFloat("foodSaturationLevel", this.foodSaturationLevel);
/* 102 */     p_75117_1_.setFloat("foodExhaustionLevel", this.foodExhaustionLevel);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getFoodLevel() {
/* 109 */     return this.foodLevel;
/*     */   }
/*     */   
/*     */   public int getPrevFoodLevel() {
/* 113 */     return this.prevFoodLevel;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean needFood() {
/* 120 */     return (this.foodLevel < 20);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void addExhaustion(float p_75113_1_) {
/* 127 */     this.foodExhaustionLevel = Math.min(this.foodExhaustionLevel + p_75113_1_, 40.0F);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public float getSaturationLevel() {
/* 134 */     return this.foodSaturationLevel;
/*     */   }
/*     */   
/*     */   public void setFoodLevel(int foodLevelIn) {
/* 138 */     this.foodLevel = foodLevelIn;
/*     */   }
/*     */   
/*     */   public void setFoodSaturationLevel(float foodSaturationLevelIn) {
/* 142 */     this.foodSaturationLevel = foodSaturationLevelIn;
/*     */   }
/*     */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\minecraf\\util\FoodStats.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */