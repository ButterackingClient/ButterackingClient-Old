/*     */ package net.minecraft.entity.projectile;
/*     */ 
/*     */ import java.util.List;
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.entity.EntityLivingBase;
/*     */ import net.minecraft.init.Items;
/*     */ import net.minecraft.item.Item;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraft.nbt.NBTBase;
/*     */ import net.minecraft.nbt.NBTTagCompound;
/*     */ import net.minecraft.potion.Potion;
/*     */ import net.minecraft.potion.PotionEffect;
/*     */ import net.minecraft.util.AxisAlignedBB;
/*     */ import net.minecraft.util.BlockPos;
/*     */ import net.minecraft.util.MovingObjectPosition;
/*     */ import net.minecraft.world.World;
/*     */ 
/*     */ public class EntityPotion
/*     */   extends EntityThrowable {
/*     */   private ItemStack potionDamage;
/*     */   
/*     */   public EntityPotion(World worldIn) {
/*  23 */     super(worldIn);
/*     */   }
/*     */   
/*     */   public EntityPotion(World worldIn, EntityLivingBase throwerIn, int meta) {
/*  27 */     this(worldIn, throwerIn, new ItemStack((Item)Items.potionitem, 1, meta));
/*     */   }
/*     */   
/*     */   public EntityPotion(World worldIn, EntityLivingBase throwerIn, ItemStack potionDamageIn) {
/*  31 */     super(worldIn, throwerIn);
/*  32 */     this.potionDamage = potionDamageIn;
/*     */   }
/*     */   
/*     */   public EntityPotion(World worldIn, double x, double y, double z, int p_i1791_8_) {
/*  36 */     this(worldIn, x, y, z, new ItemStack((Item)Items.potionitem, 1, p_i1791_8_));
/*     */   }
/*     */   
/*     */   public EntityPotion(World worldIn, double x, double y, double z, ItemStack potionDamageIn) {
/*  40 */     super(worldIn, x, y, z);
/*  41 */     this.potionDamage = potionDamageIn;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected float getGravityVelocity() {
/*  48 */     return 0.05F;
/*     */   }
/*     */   
/*     */   protected float getVelocity() {
/*  52 */     return 0.5F;
/*     */   }
/*     */   
/*     */   protected float getInaccuracy() {
/*  56 */     return -20.0F;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setPotionDamage(int potionId) {
/*  63 */     if (this.potionDamage == null) {
/*  64 */       this.potionDamage = new ItemStack((Item)Items.potionitem, 1, 0);
/*     */     }
/*     */     
/*  67 */     this.potionDamage.setItemDamage(potionId);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getPotionDamage() {
/*  74 */     if (this.potionDamage == null) {
/*  75 */       this.potionDamage = new ItemStack((Item)Items.potionitem, 1, 0);
/*     */     }
/*     */     
/*  78 */     return this.potionDamage.getMetadata();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void onImpact(MovingObjectPosition p_70184_1_) {
/*  85 */     if (!this.worldObj.isRemote) {
/*  86 */       List<PotionEffect> list = Items.potionitem.getEffects(this.potionDamage);
/*     */       
/*  88 */       if (list != null && !list.isEmpty()) {
/*  89 */         AxisAlignedBB axisalignedbb = getEntityBoundingBox().expand(4.0D, 2.0D, 4.0D);
/*  90 */         List<EntityLivingBase> list1 = this.worldObj.getEntitiesWithinAABB(EntityLivingBase.class, axisalignedbb);
/*     */         
/*  92 */         if (!list1.isEmpty()) {
/*  93 */           for (EntityLivingBase entitylivingbase : list1) {
/*  94 */             double d0 = getDistanceSqToEntity((Entity)entitylivingbase);
/*     */             
/*  96 */             if (d0 < 16.0D) {
/*  97 */               double d1 = 1.0D - Math.sqrt(d0) / 4.0D;
/*     */               
/*  99 */               if (entitylivingbase == p_70184_1_.entityHit) {
/* 100 */                 d1 = 1.0D;
/*     */               }
/*     */               
/* 103 */               for (PotionEffect potioneffect : list) {
/* 104 */                 int i = potioneffect.getPotionID();
/*     */                 
/* 106 */                 if (Potion.potionTypes[i].isInstant()) {
/* 107 */                   Potion.potionTypes[i].affectEntity(this, (Entity)getThrower(), entitylivingbase, potioneffect.getAmplifier(), d1); continue;
/*     */                 } 
/* 109 */                 int j = (int)(d1 * potioneffect.getDuration() + 0.5D);
/*     */                 
/* 111 */                 if (j > 20) {
/* 112 */                   entitylivingbase.addPotionEffect(new PotionEffect(i, j, potioneffect.getAmplifier()));
/*     */                 }
/*     */               } 
/*     */             } 
/*     */           } 
/*     */         }
/*     */       } 
/*     */ 
/*     */       
/* 121 */       this.worldObj.playAuxSFX(2002, new BlockPos(this), getPotionDamage());
/* 122 */       setDead();
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void readEntityFromNBT(NBTTagCompound tagCompund) {
/* 130 */     super.readEntityFromNBT(tagCompund);
/*     */     
/* 132 */     if (tagCompund.hasKey("Potion", 10)) {
/* 133 */       this.potionDamage = ItemStack.loadItemStackFromNBT(tagCompund.getCompoundTag("Potion"));
/*     */     } else {
/* 135 */       setPotionDamage(tagCompund.getInteger("potionValue"));
/*     */     } 
/*     */     
/* 138 */     if (this.potionDamage == null) {
/* 139 */       setDead();
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void writeEntityToNBT(NBTTagCompound tagCompound) {
/* 147 */     super.writeEntityToNBT(tagCompound);
/*     */     
/* 149 */     if (this.potionDamage != null)
/* 150 */       tagCompound.setTag("Potion", (NBTBase)this.potionDamage.writeToNBT(new NBTTagCompound())); 
/*     */   }
/*     */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\minecraft\entity\projectile\EntityPotion.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */