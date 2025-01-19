/*     */ package net.minecraft.entity.passive;
/*     */ 
/*     */ import com.google.common.collect.Maps;
/*     */ import java.util.Map;
/*     */ import java.util.Random;
/*     */ import net.minecraft.block.Block;
/*     */ import net.minecraft.entity.EntityAgeable;
/*     */ import net.minecraft.entity.EntityCreature;
/*     */ import net.minecraft.entity.EntityLiving;
/*     */ import net.minecraft.entity.EntityLivingBase;
/*     */ import net.minecraft.entity.IEntityLivingData;
/*     */ import net.minecraft.entity.SharedMonsterAttributes;
/*     */ import net.minecraft.entity.ai.EntityAIBase;
/*     */ import net.minecraft.entity.ai.EntityAIEatGrass;
/*     */ import net.minecraft.entity.ai.EntityAIFollowParent;
/*     */ import net.minecraft.entity.ai.EntityAILookIdle;
/*     */ import net.minecraft.entity.ai.EntityAIMate;
/*     */ import net.minecraft.entity.ai.EntityAIPanic;
/*     */ import net.minecraft.entity.ai.EntityAISwimming;
/*     */ import net.minecraft.entity.ai.EntityAITempt;
/*     */ import net.minecraft.entity.ai.EntityAIWander;
/*     */ import net.minecraft.entity.ai.EntityAIWatchClosest;
/*     */ import net.minecraft.entity.item.EntityItem;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.init.Blocks;
/*     */ import net.minecraft.init.Items;
/*     */ import net.minecraft.inventory.Container;
/*     */ import net.minecraft.inventory.InventoryCrafting;
/*     */ import net.minecraft.item.EnumDyeColor;
/*     */ import net.minecraft.item.Item;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraft.item.crafting.CraftingManager;
/*     */ import net.minecraft.nbt.NBTTagCompound;
/*     */ import net.minecraft.pathfinding.PathNavigateGround;
/*     */ import net.minecraft.util.BlockPos;
/*     */ import net.minecraft.util.MathHelper;
/*     */ import net.minecraft.world.DifficultyInstance;
/*     */ import net.minecraft.world.World;
/*     */ 
/*     */ public class EntitySheep
/*     */   extends EntityAnimal
/*     */ {
/*  43 */   private final InventoryCrafting inventoryCrafting = new InventoryCrafting(new Container() {
/*     */         public boolean canInteractWith(EntityPlayer playerIn) {
/*  45 */           return false;
/*     */         }
/*  47 */       },  2, 1);
/*  48 */   private static final Map<EnumDyeColor, float[]> DYE_TO_RGB = Maps.newEnumMap(EnumDyeColor.class);
/*     */ 
/*     */ 
/*     */   
/*     */   private int sheepTimer;
/*     */ 
/*     */   
/*  55 */   private EntityAIEatGrass entityAIEatGrass = new EntityAIEatGrass((EntityLiving)this);
/*     */   
/*     */   public static float[] getDyeRgb(EnumDyeColor dyeColor) {
/*  58 */     return DYE_TO_RGB.get(dyeColor);
/*     */   }
/*     */   
/*     */   public EntitySheep(World worldIn) {
/*  62 */     super(worldIn);
/*  63 */     setSize(0.9F, 1.3F);
/*  64 */     ((PathNavigateGround)getNavigator()).setAvoidsWater(true);
/*  65 */     this.tasks.addTask(0, (EntityAIBase)new EntityAISwimming((EntityLiving)this));
/*  66 */     this.tasks.addTask(1, (EntityAIBase)new EntityAIPanic((EntityCreature)this, 1.25D));
/*  67 */     this.tasks.addTask(2, (EntityAIBase)new EntityAIMate(this, 1.0D));
/*  68 */     this.tasks.addTask(3, (EntityAIBase)new EntityAITempt((EntityCreature)this, 1.1D, Items.wheat, false));
/*  69 */     this.tasks.addTask(4, (EntityAIBase)new EntityAIFollowParent(this, 1.1D));
/*  70 */     this.tasks.addTask(5, (EntityAIBase)this.entityAIEatGrass);
/*  71 */     this.tasks.addTask(6, (EntityAIBase)new EntityAIWander((EntityCreature)this, 1.0D));
/*  72 */     this.tasks.addTask(7, (EntityAIBase)new EntityAIWatchClosest((EntityLiving)this, EntityPlayer.class, 6.0F));
/*  73 */     this.tasks.addTask(8, (EntityAIBase)new EntityAILookIdle((EntityLiving)this));
/*  74 */     this.inventoryCrafting.setInventorySlotContents(0, new ItemStack(Items.dye, 1, 0));
/*  75 */     this.inventoryCrafting.setInventorySlotContents(1, new ItemStack(Items.dye, 1, 0));
/*     */   }
/*     */   
/*     */   protected void updateAITasks() {
/*  79 */     this.sheepTimer = this.entityAIEatGrass.getEatingGrassTimer();
/*  80 */     super.updateAITasks();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void onLivingUpdate() {
/*  88 */     if (this.worldObj.isRemote) {
/*  89 */       this.sheepTimer = Math.max(0, this.sheepTimer - 1);
/*     */     }
/*     */     
/*  92 */     super.onLivingUpdate();
/*     */   }
/*     */   
/*     */   protected void applyEntityAttributes() {
/*  96 */     super.applyEntityAttributes();
/*  97 */     getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(8.0D);
/*  98 */     getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(0.23000000417232513D);
/*     */   }
/*     */   
/*     */   protected void entityInit() {
/* 102 */     super.entityInit();
/* 103 */     this.dataWatcher.addObject(16, new Byte((byte)0));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void dropFewItems(boolean wasRecentlyHit, int lootingModifier) {
/* 114 */     if (!getSheared()) {
/* 115 */       entityDropItem(new ItemStack(Item.getItemFromBlock(Blocks.wool), 1, getFleeceColor().getMetadata()), 0.0F);
/*     */     }
/*     */     
/* 118 */     int i = this.rand.nextInt(2) + 1 + this.rand.nextInt(1 + lootingModifier);
/*     */     
/* 120 */     for (int j = 0; j < i; j++) {
/* 121 */       if (isBurning()) {
/* 122 */         dropItem(Items.cooked_mutton, 1);
/*     */       } else {
/* 124 */         dropItem(Items.mutton, 1);
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   protected Item getDropItem() {
/* 130 */     return Item.getItemFromBlock(Blocks.wool);
/*     */   }
/*     */   
/*     */   public void handleStatusUpdate(byte id) {
/* 134 */     if (id == 10) {
/* 135 */       this.sheepTimer = 40;
/*     */     } else {
/* 137 */       super.handleStatusUpdate(id);
/*     */     } 
/*     */   }
/*     */   
/*     */   public float getHeadRotationPointY(float p_70894_1_) {
/* 142 */     return (this.sheepTimer <= 0) ? 0.0F : ((this.sheepTimer >= 4 && this.sheepTimer <= 36) ? 1.0F : ((this.sheepTimer < 4) ? ((this.sheepTimer - p_70894_1_) / 4.0F) : (-((this.sheepTimer - 40) - p_70894_1_) / 4.0F)));
/*     */   }
/*     */   
/*     */   public float getHeadRotationAngleX(float p_70890_1_) {
/* 146 */     if (this.sheepTimer > 4 && this.sheepTimer <= 36) {
/* 147 */       float f = ((this.sheepTimer - 4) - p_70890_1_) / 32.0F;
/* 148 */       return 0.62831855F + 0.2199115F * MathHelper.sin(f * 28.7F);
/*     */     } 
/* 150 */     return (this.sheepTimer > 0) ? 0.62831855F : (this.rotationPitch / 57.295776F);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean interact(EntityPlayer player) {
/* 158 */     ItemStack itemstack = player.inventory.getCurrentItem();
/*     */     
/* 160 */     if (itemstack != null && itemstack.getItem() == Items.shears && !getSheared() && !isChild()) {
/* 161 */       if (!this.worldObj.isRemote) {
/* 162 */         setSheared(true);
/* 163 */         int i = 1 + this.rand.nextInt(3);
/*     */         
/* 165 */         for (int j = 0; j < i; j++) {
/* 166 */           EntityItem entityitem = entityDropItem(new ItemStack(Item.getItemFromBlock(Blocks.wool), 1, getFleeceColor().getMetadata()), 1.0F);
/* 167 */           entityitem.motionY += (this.rand.nextFloat() * 0.05F);
/* 168 */           entityitem.motionX += ((this.rand.nextFloat() - this.rand.nextFloat()) * 0.1F);
/* 169 */           entityitem.motionZ += ((this.rand.nextFloat() - this.rand.nextFloat()) * 0.1F);
/*     */         } 
/*     */       } 
/*     */       
/* 173 */       itemstack.damageItem(1, (EntityLivingBase)player);
/* 174 */       playSound("mob.sheep.shear", 1.0F, 1.0F);
/*     */     } 
/*     */     
/* 177 */     return super.interact(player);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void writeEntityToNBT(NBTTagCompound tagCompound) {
/* 184 */     super.writeEntityToNBT(tagCompound);
/* 185 */     tagCompound.setBoolean("Sheared", getSheared());
/* 186 */     tagCompound.setByte("Color", (byte)getFleeceColor().getMetadata());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void readEntityFromNBT(NBTTagCompound tagCompund) {
/* 193 */     super.readEntityFromNBT(tagCompund);
/* 194 */     setSheared(tagCompund.getBoolean("Sheared"));
/* 195 */     setFleeceColor(EnumDyeColor.byMetadata(tagCompund.getByte("Color")));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected String getLivingSound() {
/* 202 */     return "mob.sheep.say";
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected String getHurtSound() {
/* 209 */     return "mob.sheep.say";
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected String getDeathSound() {
/* 216 */     return "mob.sheep.say";
/*     */   }
/*     */   
/*     */   protected void playStepSound(BlockPos pos, Block blockIn) {
/* 220 */     playSound("mob.sheep.step", 0.15F, 1.0F);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public EnumDyeColor getFleeceColor() {
/* 227 */     return EnumDyeColor.byMetadata(this.dataWatcher.getWatchableObjectByte(16) & 0xF);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setFleeceColor(EnumDyeColor color) {
/* 234 */     byte b0 = this.dataWatcher.getWatchableObjectByte(16);
/* 235 */     this.dataWatcher.updateObject(16, Byte.valueOf((byte)(b0 & 0xF0 | color.getMetadata() & 0xF)));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean getSheared() {
/* 242 */     return ((this.dataWatcher.getWatchableObjectByte(16) & 0x10) != 0);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setSheared(boolean sheared) {
/* 249 */     byte b0 = this.dataWatcher.getWatchableObjectByte(16);
/*     */     
/* 251 */     if (sheared) {
/* 252 */       this.dataWatcher.updateObject(16, Byte.valueOf((byte)(b0 | 0x10)));
/*     */     } else {
/* 254 */       this.dataWatcher.updateObject(16, Byte.valueOf((byte)(b0 & 0xFFFFFFEF)));
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static EnumDyeColor getRandomSheepColor(Random random) {
/* 262 */     int i = random.nextInt(100);
/* 263 */     return (i < 5) ? EnumDyeColor.BLACK : ((i < 10) ? EnumDyeColor.GRAY : ((i < 15) ? EnumDyeColor.SILVER : ((i < 18) ? EnumDyeColor.BROWN : ((random.nextInt(500) == 0) ? EnumDyeColor.PINK : EnumDyeColor.WHITE))));
/*     */   }
/*     */   
/*     */   public EntitySheep createChild(EntityAgeable ageable) {
/* 267 */     EntitySheep entitysheep = (EntitySheep)ageable;
/* 268 */     EntitySheep entitysheep1 = new EntitySheep(this.worldObj);
/* 269 */     entitysheep1.setFleeceColor(getDyeColorMixFromParents(this, entitysheep));
/* 270 */     return entitysheep1;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void eatGrassBonus() {
/* 278 */     setSheared(false);
/*     */     
/* 280 */     if (isChild()) {
/* 281 */       addGrowth(60);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public IEntityLivingData onInitialSpawn(DifficultyInstance difficulty, IEntityLivingData livingdata) {
/* 290 */     livingdata = super.onInitialSpawn(difficulty, livingdata);
/* 291 */     setFleeceColor(getRandomSheepColor(this.worldObj.rand));
/* 292 */     return livingdata;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private EnumDyeColor getDyeColorMixFromParents(EntityAnimal father, EntityAnimal mother) {
/* 299 */     int k, i = ((EntitySheep)father).getFleeceColor().getDyeDamage();
/* 300 */     int j = ((EntitySheep)mother).getFleeceColor().getDyeDamage();
/* 301 */     this.inventoryCrafting.getStackInSlot(0).setItemDamage(i);
/* 302 */     this.inventoryCrafting.getStackInSlot(1).setItemDamage(j);
/* 303 */     ItemStack itemstack = CraftingManager.getInstance().findMatchingRecipe(this.inventoryCrafting, ((EntitySheep)father).worldObj);
/*     */ 
/*     */     
/* 306 */     if (itemstack != null && itemstack.getItem() == Items.dye) {
/* 307 */       k = itemstack.getMetadata();
/*     */     } else {
/* 309 */       k = this.worldObj.rand.nextBoolean() ? i : j;
/*     */     } 
/*     */     
/* 312 */     return EnumDyeColor.byDyeDamage(k);
/*     */   }
/*     */   
/*     */   public float getEyeHeight() {
/* 316 */     return 0.95F * this.height;
/*     */   }
/*     */   
/*     */   static {
/* 320 */     DYE_TO_RGB.put(EnumDyeColor.WHITE, new float[] { 1.0F, 1.0F, 1.0F });
/* 321 */     DYE_TO_RGB.put(EnumDyeColor.ORANGE, new float[] { 0.85F, 0.5F, 0.2F });
/* 322 */     DYE_TO_RGB.put(EnumDyeColor.MAGENTA, new float[] { 0.7F, 0.3F, 0.85F });
/* 323 */     DYE_TO_RGB.put(EnumDyeColor.LIGHT_BLUE, new float[] { 0.4F, 0.6F, 0.85F });
/* 324 */     DYE_TO_RGB.put(EnumDyeColor.YELLOW, new float[] { 0.9F, 0.9F, 0.2F });
/* 325 */     DYE_TO_RGB.put(EnumDyeColor.LIME, new float[] { 0.5F, 0.8F, 0.1F });
/* 326 */     DYE_TO_RGB.put(EnumDyeColor.PINK, new float[] { 0.95F, 0.5F, 0.65F });
/* 327 */     DYE_TO_RGB.put(EnumDyeColor.GRAY, new float[] { 0.3F, 0.3F, 0.3F });
/* 328 */     DYE_TO_RGB.put(EnumDyeColor.SILVER, new float[] { 0.6F, 0.6F, 0.6F });
/* 329 */     DYE_TO_RGB.put(EnumDyeColor.CYAN, new float[] { 0.3F, 0.5F, 0.6F });
/* 330 */     DYE_TO_RGB.put(EnumDyeColor.PURPLE, new float[] { 0.5F, 0.25F, 0.7F });
/* 331 */     DYE_TO_RGB.put(EnumDyeColor.BLUE, new float[] { 0.2F, 0.3F, 0.7F });
/* 332 */     DYE_TO_RGB.put(EnumDyeColor.BROWN, new float[] { 0.4F, 0.3F, 0.2F });
/* 333 */     DYE_TO_RGB.put(EnumDyeColor.GREEN, new float[] { 0.4F, 0.5F, 0.2F });
/* 334 */     DYE_TO_RGB.put(EnumDyeColor.RED, new float[] { 0.6F, 0.2F, 0.2F });
/* 335 */     DYE_TO_RGB.put(EnumDyeColor.BLACK, new float[] { 0.1F, 0.1F, 0.1F });
/*     */   }
/*     */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\minecraft\entity\passive\EntitySheep.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */