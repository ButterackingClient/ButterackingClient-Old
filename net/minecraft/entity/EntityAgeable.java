/*     */ package net.minecraft.entity;
/*     */ 
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.init.Items;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraft.nbt.NBTTagCompound;
/*     */ import net.minecraft.util.EnumParticleTypes;
/*     */ import net.minecraft.util.MathHelper;
/*     */ import net.minecraft.world.World;
/*     */ 
/*     */ public abstract class EntityAgeable extends EntityCreature {
/*     */   protected int growingAge;
/*     */   protected int field_175502_b;
/*     */   protected int field_175503_c;
/*  15 */   private float ageWidth = -1.0F;
/*     */   private float ageHeight;
/*     */   
/*     */   public EntityAgeable(World worldIn) {
/*  19 */     super(worldIn);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public abstract EntityAgeable createChild(EntityAgeable paramEntityAgeable);
/*     */ 
/*     */   
/*     */   public boolean interact(EntityPlayer player) {
/*  28 */     ItemStack itemstack = player.inventory.getCurrentItem();
/*     */     
/*  30 */     if (itemstack != null && itemstack.getItem() == Items.spawn_egg) {
/*  31 */       if (!this.worldObj.isRemote) {
/*  32 */         Class<? extends Entity> oclass = EntityList.getClassFromID(itemstack.getMetadata());
/*     */         
/*  34 */         if (oclass != null && getClass() == oclass) {
/*  35 */           EntityAgeable entityageable = createChild(this);
/*     */           
/*  37 */           if (entityageable != null) {
/*  38 */             entityageable.setGrowingAge(-24000);
/*  39 */             entityageable.setLocationAndAngles(this.posX, this.posY, this.posZ, 0.0F, 0.0F);
/*  40 */             this.worldObj.spawnEntityInWorld(entityageable);
/*     */             
/*  42 */             if (itemstack.hasDisplayName()) {
/*  43 */               entityageable.setCustomNameTag(itemstack.getDisplayName());
/*     */             }
/*     */             
/*  46 */             if (!player.capabilities.isCreativeMode) {
/*  47 */               itemstack.stackSize--;
/*     */               
/*  49 */               if (itemstack.stackSize <= 0) {
/*  50 */                 player.inventory.setInventorySlotContents(player.inventory.currentItem, null);
/*     */               }
/*     */             } 
/*     */           } 
/*     */         } 
/*     */       } 
/*     */       
/*  57 */       return true;
/*     */     } 
/*  59 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   protected void entityInit() {
/*  64 */     super.entityInit();
/*  65 */     this.dataWatcher.addObject(12, Byte.valueOf((byte)0));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getGrowingAge() {
/*  74 */     return this.worldObj.isRemote ? this.dataWatcher.getWatchableObjectByte(12) : this.growingAge;
/*     */   }
/*     */   
/*     */   public void func_175501_a(int p_175501_1_, boolean p_175501_2_) {
/*  78 */     int i = getGrowingAge();
/*  79 */     int j = i;
/*  80 */     i += p_175501_1_ * 20;
/*     */     
/*  82 */     if (i > 0) {
/*  83 */       i = 0;
/*     */       
/*  85 */       if (j < 0) {
/*  86 */         onGrowingAdult();
/*     */       }
/*     */     } 
/*     */     
/*  90 */     int k = i - j;
/*  91 */     setGrowingAge(i);
/*     */     
/*  93 */     if (p_175501_2_) {
/*  94 */       this.field_175502_b += k;
/*     */       
/*  96 */       if (this.field_175503_c == 0) {
/*  97 */         this.field_175503_c = 40;
/*     */       }
/*     */     } 
/*     */     
/* 101 */     if (getGrowingAge() == 0) {
/* 102 */       setGrowingAge(this.field_175502_b);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void addGrowth(int growth) {
/* 111 */     func_175501_a(growth, false);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setGrowingAge(int age) {
/* 119 */     this.dataWatcher.updateObject(12, Byte.valueOf((byte)MathHelper.clamp_int(age, -1, 1)));
/* 120 */     this.growingAge = age;
/* 121 */     setScaleForAge(isChild());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void writeEntityToNBT(NBTTagCompound tagCompound) {
/* 128 */     super.writeEntityToNBT(tagCompound);
/* 129 */     tagCompound.setInteger("Age", getGrowingAge());
/* 130 */     tagCompound.setInteger("ForcedAge", this.field_175502_b);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void readEntityFromNBT(NBTTagCompound tagCompund) {
/* 137 */     super.readEntityFromNBT(tagCompund);
/* 138 */     setGrowingAge(tagCompund.getInteger("Age"));
/* 139 */     this.field_175502_b = tagCompund.getInteger("ForcedAge");
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void onLivingUpdate() {
/* 147 */     super.onLivingUpdate();
/*     */     
/* 149 */     if (this.worldObj.isRemote) {
/* 150 */       if (this.field_175503_c > 0) {
/* 151 */         if (this.field_175503_c % 4 == 0) {
/* 152 */           this.worldObj.spawnParticle(EnumParticleTypes.VILLAGER_HAPPY, this.posX + (this.rand.nextFloat() * this.width * 2.0F) - this.width, this.posY + 0.5D + (this.rand.nextFloat() * this.height), this.posZ + (this.rand.nextFloat() * this.width * 2.0F) - this.width, 0.0D, 0.0D, 0.0D, new int[0]);
/*     */         }
/*     */         
/* 155 */         this.field_175503_c--;
/*     */       } 
/*     */       
/* 158 */       setScaleForAge(isChild());
/*     */     } else {
/* 160 */       int i = getGrowingAge();
/*     */       
/* 162 */       if (i < 0) {
/* 163 */         i++;
/* 164 */         setGrowingAge(i);
/*     */         
/* 166 */         if (i == 0) {
/* 167 */           onGrowingAdult();
/*     */         }
/* 169 */       } else if (i > 0) {
/* 170 */         i--;
/* 171 */         setGrowingAge(i);
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void onGrowingAdult() {}
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isChild() {
/* 187 */     return (getGrowingAge() < 0);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setScaleForAge(boolean p_98054_1_) {
/* 194 */     setScale(p_98054_1_ ? 0.5F : 1.0F);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected final void setSize(float width, float height) {
/* 201 */     boolean flag = (this.ageWidth > 0.0F);
/* 202 */     this.ageWidth = width;
/* 203 */     this.ageHeight = height;
/*     */     
/* 205 */     if (!flag) {
/* 206 */       setScale(1.0F);
/*     */     }
/*     */   }
/*     */   
/*     */   protected final void setScale(float scale) {
/* 211 */     super.setSize(this.ageWidth * scale, this.ageHeight * scale);
/*     */   }
/*     */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\minecraft\entity\EntityAgeable.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */