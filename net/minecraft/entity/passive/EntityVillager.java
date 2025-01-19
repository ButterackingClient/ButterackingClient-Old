/*     */ package net.minecraft.entity.passive;
/*     */ 
/*     */ import java.util.Random;
/*     */ import net.minecraft.enchantment.Enchantment;
/*     */ import net.minecraft.enchantment.EnchantmentData;
/*     */ import net.minecraft.enchantment.EnchantmentHelper;
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.entity.EntityAgeable;
/*     */ import net.minecraft.entity.EntityCreature;
/*     */ import net.minecraft.entity.EntityLiving;
/*     */ import net.minecraft.entity.EntityLivingBase;
/*     */ import net.minecraft.entity.IEntityLivingData;
/*     */ import net.minecraft.entity.IMerchant;
/*     */ import net.minecraft.entity.INpc;
/*     */ import net.minecraft.entity.SharedMonsterAttributes;
/*     */ import net.minecraft.entity.ai.EntityAIAvoidEntity;
/*     */ import net.minecraft.entity.ai.EntityAIBase;
/*     */ import net.minecraft.entity.ai.EntityAIFollowGolem;
/*     */ import net.minecraft.entity.ai.EntityAIHarvestFarmland;
/*     */ import net.minecraft.entity.ai.EntityAILookAtTradePlayer;
/*     */ import net.minecraft.entity.ai.EntityAIMoveIndoors;
/*     */ import net.minecraft.entity.ai.EntityAIMoveTowardsRestriction;
/*     */ import net.minecraft.entity.ai.EntityAIOpenDoor;
/*     */ import net.minecraft.entity.ai.EntityAIPlay;
/*     */ import net.minecraft.entity.ai.EntityAIRestrictOpenDoor;
/*     */ import net.minecraft.entity.ai.EntityAISwimming;
/*     */ import net.minecraft.entity.ai.EntityAITradePlayer;
/*     */ import net.minecraft.entity.ai.EntityAIVillagerInteract;
/*     */ import net.minecraft.entity.ai.EntityAIVillagerMate;
/*     */ import net.minecraft.entity.ai.EntityAIWander;
/*     */ import net.minecraft.entity.ai.EntityAIWatchClosest;
/*     */ import net.minecraft.entity.ai.EntityAIWatchClosest2;
/*     */ import net.minecraft.entity.effect.EntityLightningBolt;
/*     */ import net.minecraft.entity.item.EntityItem;
/*     */ import net.minecraft.entity.item.EntityXPOrb;
/*     */ import net.minecraft.entity.monster.EntityWitch;
/*     */ import net.minecraft.entity.monster.EntityZombie;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.init.Blocks;
/*     */ import net.minecraft.init.Items;
/*     */ import net.minecraft.inventory.InventoryBasic;
/*     */ import net.minecraft.item.EnumDyeColor;
/*     */ import net.minecraft.item.Item;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraft.nbt.NBTBase;
/*     */ import net.minecraft.nbt.NBTTagCompound;
/*     */ import net.minecraft.nbt.NBTTagList;
/*     */ import net.minecraft.pathfinding.PathNavigateGround;
/*     */ import net.minecraft.potion.Potion;
/*     */ import net.minecraft.potion.PotionEffect;
/*     */ import net.minecraft.stats.StatList;
/*     */ import net.minecraft.util.BlockPos;
/*     */ import net.minecraft.util.ChatComponentText;
/*     */ import net.minecraft.util.ChatComponentTranslation;
/*     */ import net.minecraft.util.DamageSource;
/*     */ import net.minecraft.util.EnumParticleTypes;
/*     */ import net.minecraft.util.IChatComponent;
/*     */ import net.minecraft.util.MathHelper;
/*     */ import net.minecraft.util.Tuple;
/*     */ import net.minecraft.village.MerchantRecipe;
/*     */ import net.minecraft.village.MerchantRecipeList;
/*     */ import net.minecraft.village.Village;
/*     */ import net.minecraft.world.DifficultyInstance;
/*     */ import net.minecraft.world.World;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class EntityVillager
/*     */   extends EntityAgeable
/*     */   implements IMerchant, INpc
/*     */ {
/*     */   private int randomTickDivider;
/*     */   private boolean isMating;
/*     */   private boolean isPlaying;
/*     */   Village villageObj;
/*     */   private EntityPlayer buyingPlayer;
/*     */   private MerchantRecipeList buyingList;
/*     */   private int timeUntilReset;
/*     */   private boolean needsInitilization;
/*     */   private boolean isWillingToMate;
/*     */   private int wealth;
/*     */   private String lastBuyingPlayer;
/*     */   private int careerId;
/*     */   private int careerLevel;
/*     */   private boolean isLookingForHome;
/*     */   private boolean areAdditionalTasksSet;
/*     */   private InventoryBasic villagerInventory;
/* 106 */   private static final ITradeList[][][][] DEFAULT_TRADE_LIST_MAP = new ITradeList[][][][] { { { { new EmeraldForItems(Items.wheat, new PriceInfo(18, 22)), new EmeraldForItems(Items.potato, new PriceInfo(15, 19)), new EmeraldForItems(Items.carrot, new PriceInfo(15, 19)), new ListItemForEmeralds(Items.bread, new PriceInfo(-4, -2)) }, { new EmeraldForItems(Item.getItemFromBlock(Blocks.pumpkin), new PriceInfo(8, 13)), new ListItemForEmeralds(Items.pumpkin_pie, new PriceInfo(-3, -2)) }, { new EmeraldForItems(Item.getItemFromBlock(Blocks.melon_block), new PriceInfo(7, 12)), new ListItemForEmeralds(Items.apple, new PriceInfo(-5, -7)) }, { new ListItemForEmeralds(Items.cookie, new PriceInfo(-6, -10)), new ListItemForEmeralds(Items.cake, new PriceInfo(1, 1)) } }, { { new EmeraldForItems(Items.string, new PriceInfo(15, 20)), new EmeraldForItems(Items.coal, new PriceInfo(16, 24)), new ItemAndEmeraldToItem(Items.fish, new PriceInfo(6, 6), Items.cooked_fish, new PriceInfo(6, 6)) }, { new ListEnchantedItemForEmeralds((Item)Items.fishing_rod, new PriceInfo(7, 8)) } }, { { new EmeraldForItems(Item.getItemFromBlock(Blocks.wool), new PriceInfo(16, 22)), new ListItemForEmeralds((Item)Items.shears, new PriceInfo(3, 4)) }, { new ListItemForEmeralds(new ItemStack(Item.getItemFromBlock(Blocks.wool), 1, 0), new PriceInfo(1, 2)), new ListItemForEmeralds(new ItemStack(Item.getItemFromBlock(Blocks.wool), 1, 1), new PriceInfo(1, 2)), new ListItemForEmeralds(new ItemStack(Item.getItemFromBlock(Blocks.wool), 1, 2), new PriceInfo(1, 2)), new ListItemForEmeralds(new ItemStack(Item.getItemFromBlock(Blocks.wool), 1, 3), new PriceInfo(1, 2)), new ListItemForEmeralds(new ItemStack(Item.getItemFromBlock(Blocks.wool), 1, 4), new PriceInfo(1, 2)), new ListItemForEmeralds(new ItemStack(Item.getItemFromBlock(Blocks.wool), 1, 5), new PriceInfo(1, 2)), new ListItemForEmeralds(new ItemStack(Item.getItemFromBlock(Blocks.wool), 1, 6), new PriceInfo(1, 2)), new ListItemForEmeralds(new ItemStack(Item.getItemFromBlock(Blocks.wool), 1, 7), new PriceInfo(1, 2)), new ListItemForEmeralds(new ItemStack(Item.getItemFromBlock(Blocks.wool), 1, 8), new PriceInfo(1, 2)), new ListItemForEmeralds(new ItemStack(Item.getItemFromBlock(Blocks.wool), 1, 9), new PriceInfo(1, 2)), new ListItemForEmeralds(new ItemStack(Item.getItemFromBlock(Blocks.wool), 1, 10), new PriceInfo(1, 2)), new ListItemForEmeralds(new ItemStack(Item.getItemFromBlock(Blocks.wool), 1, 11), new PriceInfo(1, 2)), new ListItemForEmeralds(new ItemStack(Item.getItemFromBlock(Blocks.wool), 1, 12), new PriceInfo(1, 2)), new ListItemForEmeralds(new ItemStack(Item.getItemFromBlock(Blocks.wool), 1, 13), new PriceInfo(1, 2)), new ListItemForEmeralds(new ItemStack(Item.getItemFromBlock(Blocks.wool), 1, 14), new PriceInfo(1, 2)), new ListItemForEmeralds(new ItemStack(Item.getItemFromBlock(Blocks.wool), 1, 15), new PriceInfo(1, 2)) } }, { { new EmeraldForItems(Items.string, new PriceInfo(15, 20)), new ListItemForEmeralds(Items.arrow, new PriceInfo(-12, -8)) }, { new ListItemForEmeralds((Item)Items.bow, new PriceInfo(2, 3)), new ItemAndEmeraldToItem(Item.getItemFromBlock(Blocks.gravel), new PriceInfo(10, 10), Items.flint, new PriceInfo(6, 10)) } } }, { { { new EmeraldForItems(Items.paper, new PriceInfo(24, 36)), new ListEnchantedBookForEmeralds() }, { new EmeraldForItems(Items.book, new PriceInfo(8, 10)), new ListItemForEmeralds(Items.compass, new PriceInfo(10, 12)), new ListItemForEmeralds(Item.getItemFromBlock(Blocks.bookshelf), new PriceInfo(3, 4)) }, { new EmeraldForItems(Items.written_book, new PriceInfo(2, 2)), new ListItemForEmeralds(Items.clock, new PriceInfo(10, 12)), new ListItemForEmeralds(Item.getItemFromBlock(Blocks.glass), new PriceInfo(-5, -3)) }, { new ListEnchantedBookForEmeralds() }, { new ListEnchantedBookForEmeralds() }, { new ListItemForEmeralds(Items.name_tag, new PriceInfo(20, 22)) } } }, { { { new EmeraldForItems(Items.rotten_flesh, new PriceInfo(36, 40)), new EmeraldForItems(Items.gold_ingot, new PriceInfo(8, 10)) }, { new ListItemForEmeralds(Items.redstone, new PriceInfo(-4, -1)), new ListItemForEmeralds(new ItemStack(Items.dye, 1, EnumDyeColor.BLUE.getDyeDamage()), new PriceInfo(-2, -1)) }, { new ListItemForEmeralds(Items.ender_eye, new PriceInfo(7, 11)), new ListItemForEmeralds(Item.getItemFromBlock(Blocks.glowstone), new PriceInfo(-3, -1)) }, { new ListItemForEmeralds(Items.experience_bottle, new PriceInfo(3, 11)) } } }, { { { new EmeraldForItems(Items.coal, new PriceInfo(16, 24)), new ListItemForEmeralds((Item)Items.iron_helmet, new PriceInfo(4, 6)) }, { new EmeraldForItems(Items.iron_ingot, new PriceInfo(7, 9)), new ListItemForEmeralds((Item)Items.iron_chestplate, new PriceInfo(10, 14)) }, { new EmeraldForItems(Items.diamond, new PriceInfo(3, 4)), new ListEnchantedItemForEmeralds((Item)Items.diamond_chestplate, new PriceInfo(16, 19)) }, { new ListItemForEmeralds((Item)Items.chainmail_boots, new PriceInfo(5, 7)), new ListItemForEmeralds((Item)Items.chainmail_leggings, new PriceInfo(9, 11)), new ListItemForEmeralds((Item)Items.chainmail_helmet, new PriceInfo(5, 7)), new ListItemForEmeralds((Item)Items.chainmail_chestplate, new PriceInfo(11, 15)) } }, { { new EmeraldForItems(Items.coal, new PriceInfo(16, 24)), new ListItemForEmeralds(Items.iron_axe, new PriceInfo(6, 8)) }, { new EmeraldForItems(Items.iron_ingot, new PriceInfo(7, 9)), new ListEnchantedItemForEmeralds(Items.iron_sword, new PriceInfo(9, 10)) }, { new EmeraldForItems(Items.diamond, new PriceInfo(3, 4)), new ListEnchantedItemForEmeralds(Items.diamond_sword, new PriceInfo(12, 15)), new ListEnchantedItemForEmeralds(Items.diamond_axe, new PriceInfo(9, 12)) } }, { { new EmeraldForItems(Items.coal, new PriceInfo(16, 24)), new ListEnchantedItemForEmeralds(Items.iron_shovel, new PriceInfo(5, 7)) }, { new EmeraldForItems(Items.iron_ingot, new PriceInfo(7, 9)), new ListEnchantedItemForEmeralds(Items.iron_pickaxe, new PriceInfo(9, 11)) }, { new EmeraldForItems(Items.diamond, new PriceInfo(3, 4)), new ListEnchantedItemForEmeralds(Items.diamond_pickaxe, new PriceInfo(12, 15)) } } }, { { { new EmeraldForItems(Items.porkchop, new PriceInfo(14, 18)), new EmeraldForItems(Items.chicken, new PriceInfo(14, 18)) }, { new EmeraldForItems(Items.coal, new PriceInfo(16, 24)), new ListItemForEmeralds(Items.cooked_porkchop, new PriceInfo(-7, -5)), new ListItemForEmeralds(Items.cooked_chicken, new PriceInfo(-8, -6)) } }, { { new EmeraldForItems(Items.leather, new PriceInfo(9, 12)), new ListItemForEmeralds((Item)Items.leather_leggings, new PriceInfo(2, 4)) }, { new ListEnchantedItemForEmeralds((Item)Items.leather_chestplate, new PriceInfo(7, 12)) }, { new ListItemForEmeralds(Items.saddle, new PriceInfo(8, 10)) } } } };
/*     */   
/*     */   public EntityVillager(World worldIn) {
/* 109 */     this(worldIn, 0);
/*     */   }
/*     */   
/*     */   public EntityVillager(World worldIn, int professionId) {
/* 113 */     super(worldIn);
/* 114 */     this.villagerInventory = new InventoryBasic("Items", false, 8);
/* 115 */     setProfession(professionId);
/* 116 */     setSize(0.6F, 1.8F);
/* 117 */     ((PathNavigateGround)getNavigator()).setBreakDoors(true);
/* 118 */     ((PathNavigateGround)getNavigator()).setAvoidsWater(true);
/* 119 */     this.tasks.addTask(0, (EntityAIBase)new EntityAISwimming((EntityLiving)this));
/* 120 */     this.tasks.addTask(1, (EntityAIBase)new EntityAIAvoidEntity((EntityCreature)this, EntityZombie.class, 8.0F, 0.6D, 0.6D));
/* 121 */     this.tasks.addTask(1, (EntityAIBase)new EntityAITradePlayer(this));
/* 122 */     this.tasks.addTask(1, (EntityAIBase)new EntityAILookAtTradePlayer(this));
/* 123 */     this.tasks.addTask(2, (EntityAIBase)new EntityAIMoveIndoors((EntityCreature)this));
/* 124 */     this.tasks.addTask(3, (EntityAIBase)new EntityAIRestrictOpenDoor((EntityCreature)this));
/* 125 */     this.tasks.addTask(4, (EntityAIBase)new EntityAIOpenDoor((EntityLiving)this, true));
/* 126 */     this.tasks.addTask(5, (EntityAIBase)new EntityAIMoveTowardsRestriction((EntityCreature)this, 0.6D));
/* 127 */     this.tasks.addTask(6, (EntityAIBase)new EntityAIVillagerMate(this));
/* 128 */     this.tasks.addTask(7, (EntityAIBase)new EntityAIFollowGolem(this));
/* 129 */     this.tasks.addTask(9, (EntityAIBase)new EntityAIWatchClosest2((EntityLiving)this, EntityPlayer.class, 3.0F, 1.0F));
/* 130 */     this.tasks.addTask(9, (EntityAIBase)new EntityAIVillagerInteract(this));
/* 131 */     this.tasks.addTask(9, (EntityAIBase)new EntityAIWander((EntityCreature)this, 0.6D));
/* 132 */     this.tasks.addTask(10, (EntityAIBase)new EntityAIWatchClosest((EntityLiving)this, EntityLiving.class, 8.0F));
/* 133 */     setCanPickUpLoot(true);
/*     */   }
/*     */   
/*     */   private void setAdditionalAItasks() {
/* 137 */     if (!this.areAdditionalTasksSet) {
/* 138 */       this.areAdditionalTasksSet = true;
/*     */       
/* 140 */       if (isChild()) {
/* 141 */         this.tasks.addTask(8, (EntityAIBase)new EntityAIPlay(this, 0.32D));
/* 142 */       } else if (getProfession() == 0) {
/* 143 */         this.tasks.addTask(6, (EntityAIBase)new EntityAIHarvestFarmland(this, 0.6D));
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void onGrowingAdult() {
/* 153 */     if (getProfession() == 0) {
/* 154 */       this.tasks.addTask(8, (EntityAIBase)new EntityAIHarvestFarmland(this, 0.6D));
/*     */     }
/*     */     
/* 157 */     super.onGrowingAdult();
/*     */   }
/*     */   
/*     */   protected void applyEntityAttributes() {
/* 161 */     super.applyEntityAttributes();
/* 162 */     getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(0.5D);
/*     */   }
/*     */   
/*     */   protected void updateAITasks() {
/* 166 */     if (--this.randomTickDivider <= 0) {
/* 167 */       BlockPos blockpos = new BlockPos((Entity)this);
/* 168 */       this.worldObj.getVillageCollection().addToVillagerPositionList(blockpos);
/* 169 */       this.randomTickDivider = 70 + this.rand.nextInt(50);
/* 170 */       this.villageObj = this.worldObj.getVillageCollection().getNearestVillage(blockpos, 32);
/*     */       
/* 172 */       if (this.villageObj == null) {
/* 173 */         detachHome();
/*     */       } else {
/* 175 */         BlockPos blockpos1 = this.villageObj.getCenter();
/* 176 */         setHomePosAndDistance(blockpos1, (int)(this.villageObj.getVillageRadius() * 1.0F));
/*     */         
/* 178 */         if (this.isLookingForHome) {
/* 179 */           this.isLookingForHome = false;
/* 180 */           this.villageObj.setDefaultPlayerReputation(5);
/*     */         } 
/*     */       } 
/*     */     } 
/*     */     
/* 185 */     if (!isTrading() && this.timeUntilReset > 0) {
/* 186 */       this.timeUntilReset--;
/*     */       
/* 188 */       if (this.timeUntilReset <= 0) {
/* 189 */         if (this.needsInitilization) {
/* 190 */           for (MerchantRecipe merchantrecipe : this.buyingList) {
/* 191 */             if (merchantrecipe.isRecipeDisabled()) {
/* 192 */               merchantrecipe.increaseMaxTradeUses(this.rand.nextInt(6) + this.rand.nextInt(6) + 2);
/*     */             }
/*     */           } 
/*     */           
/* 196 */           populateBuyingList();
/* 197 */           this.needsInitilization = false;
/*     */           
/* 199 */           if (this.villageObj != null && this.lastBuyingPlayer != null) {
/* 200 */             this.worldObj.setEntityState((Entity)this, (byte)14);
/* 201 */             this.villageObj.setReputationForPlayer(this.lastBuyingPlayer, 1);
/*     */           } 
/*     */         } 
/*     */         
/* 205 */         addPotionEffect(new PotionEffect(Potion.regeneration.id, 200, 0));
/*     */       } 
/*     */     } 
/*     */     
/* 209 */     super.updateAITasks();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean interact(EntityPlayer player) {
/* 216 */     ItemStack itemstack = player.inventory.getCurrentItem();
/* 217 */     boolean flag = (itemstack != null && itemstack.getItem() == Items.spawn_egg);
/*     */     
/* 219 */     if (!flag && isEntityAlive() && !isTrading() && !isChild()) {
/* 220 */       if (!this.worldObj.isRemote && (this.buyingList == null || this.buyingList.size() > 0)) {
/* 221 */         setCustomer(player);
/* 222 */         player.displayVillagerTradeGui(this);
/*     */       } 
/*     */       
/* 225 */       player.triggerAchievement(StatList.timesTalkedToVillagerStat);
/* 226 */       return true;
/*     */     } 
/* 228 */     return super.interact(player);
/*     */   }
/*     */ 
/*     */   
/*     */   protected void entityInit() {
/* 233 */     super.entityInit();
/* 234 */     this.dataWatcher.addObject(16, Integer.valueOf(0));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void writeEntityToNBT(NBTTagCompound tagCompound) {
/* 241 */     super.writeEntityToNBT(tagCompound);
/* 242 */     tagCompound.setInteger("Profession", getProfession());
/* 243 */     tagCompound.setInteger("Riches", this.wealth);
/* 244 */     tagCompound.setInteger("Career", this.careerId);
/* 245 */     tagCompound.setInteger("CareerLevel", this.careerLevel);
/* 246 */     tagCompound.setBoolean("Willing", this.isWillingToMate);
/*     */     
/* 248 */     if (this.buyingList != null) {
/* 249 */       tagCompound.setTag("Offers", (NBTBase)this.buyingList.getRecipiesAsTags());
/*     */     }
/*     */     
/* 252 */     NBTTagList nbttaglist = new NBTTagList();
/*     */     
/* 254 */     for (int i = 0; i < this.villagerInventory.getSizeInventory(); i++) {
/* 255 */       ItemStack itemstack = this.villagerInventory.getStackInSlot(i);
/*     */       
/* 257 */       if (itemstack != null) {
/* 258 */         nbttaglist.appendTag((NBTBase)itemstack.writeToNBT(new NBTTagCompound()));
/*     */       }
/*     */     } 
/*     */     
/* 262 */     tagCompound.setTag("Inventory", (NBTBase)nbttaglist);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void readEntityFromNBT(NBTTagCompound tagCompund) {
/* 269 */     super.readEntityFromNBT(tagCompund);
/* 270 */     setProfession(tagCompund.getInteger("Profession"));
/* 271 */     this.wealth = tagCompund.getInteger("Riches");
/* 272 */     this.careerId = tagCompund.getInteger("Career");
/* 273 */     this.careerLevel = tagCompund.getInteger("CareerLevel");
/* 274 */     this.isWillingToMate = tagCompund.getBoolean("Willing");
/*     */     
/* 276 */     if (tagCompund.hasKey("Offers", 10)) {
/* 277 */       NBTTagCompound nbttagcompound = tagCompund.getCompoundTag("Offers");
/* 278 */       this.buyingList = new MerchantRecipeList(nbttagcompound);
/*     */     } 
/*     */     
/* 281 */     NBTTagList nbttaglist = tagCompund.getTagList("Inventory", 10);
/*     */     
/* 283 */     for (int i = 0; i < nbttaglist.tagCount(); i++) {
/* 284 */       ItemStack itemstack = ItemStack.loadItemStackFromNBT(nbttaglist.getCompoundTagAt(i));
/*     */       
/* 286 */       if (itemstack != null) {
/* 287 */         this.villagerInventory.func_174894_a(itemstack);
/*     */       }
/*     */     } 
/*     */     
/* 291 */     setCanPickUpLoot(true);
/* 292 */     setAdditionalAItasks();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected boolean canDespawn() {
/* 299 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected String getLivingSound() {
/* 306 */     return isTrading() ? "mob.villager.haggle" : "mob.villager.idle";
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected String getHurtSound() {
/* 313 */     return "mob.villager.hit";
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected String getDeathSound() {
/* 320 */     return "mob.villager.death";
/*     */   }
/*     */   
/*     */   public void setProfession(int professionId) {
/* 324 */     this.dataWatcher.updateObject(16, Integer.valueOf(professionId));
/*     */   }
/*     */   
/*     */   public int getProfession() {
/* 328 */     return Math.max(this.dataWatcher.getWatchableObjectInt(16) % 5, 0);
/*     */   }
/*     */   
/*     */   public boolean isMating() {
/* 332 */     return this.isMating;
/*     */   }
/*     */   
/*     */   public void setMating(boolean mating) {
/* 336 */     this.isMating = mating;
/*     */   }
/*     */   
/*     */   public void setPlaying(boolean playing) {
/* 340 */     this.isPlaying = playing;
/*     */   }
/*     */   
/*     */   public boolean isPlaying() {
/* 344 */     return this.isPlaying;
/*     */   }
/*     */   
/*     */   public void setRevengeTarget(EntityLivingBase livingBase) {
/* 348 */     super.setRevengeTarget(livingBase);
/*     */     
/* 350 */     if (this.villageObj != null && livingBase != null) {
/* 351 */       this.villageObj.addOrRenewAgressor(livingBase);
/*     */       
/* 353 */       if (livingBase instanceof EntityPlayer) {
/* 354 */         int i = -1;
/*     */         
/* 356 */         if (isChild()) {
/* 357 */           i = -3;
/*     */         }
/*     */         
/* 360 */         this.villageObj.setReputationForPlayer(livingBase.getName(), i);
/*     */         
/* 362 */         if (isEntityAlive()) {
/* 363 */           this.worldObj.setEntityState((Entity)this, (byte)13);
/*     */         }
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void onDeath(DamageSource cause) {
/* 373 */     if (this.villageObj != null) {
/* 374 */       Entity entity = cause.getEntity();
/*     */       
/* 376 */       if (entity != null) {
/* 377 */         if (entity instanceof EntityPlayer) {
/* 378 */           this.villageObj.setReputationForPlayer(entity.getName(), -2);
/* 379 */         } else if (entity instanceof net.minecraft.entity.monster.IMob) {
/* 380 */           this.villageObj.endMatingSeason();
/*     */         } 
/*     */       } else {
/* 383 */         EntityPlayer entityplayer = this.worldObj.getClosestPlayerToEntity((Entity)this, 16.0D);
/*     */         
/* 385 */         if (entityplayer != null) {
/* 386 */           this.villageObj.endMatingSeason();
/*     */         }
/*     */       } 
/*     */     } 
/*     */     
/* 391 */     super.onDeath(cause);
/*     */   }
/*     */   
/*     */   public void setCustomer(EntityPlayer p_70932_1_) {
/* 395 */     this.buyingPlayer = p_70932_1_;
/*     */   }
/*     */   
/*     */   public EntityPlayer getCustomer() {
/* 399 */     return this.buyingPlayer;
/*     */   }
/*     */   
/*     */   public boolean isTrading() {
/* 403 */     return (this.buyingPlayer != null);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean getIsWillingToMate(boolean updateFirst) {
/* 410 */     if (!this.isWillingToMate && updateFirst && func_175553_cp()) {
/* 411 */       boolean flag = false;
/*     */       
/* 413 */       for (int i = 0; i < this.villagerInventory.getSizeInventory(); i++) {
/* 414 */         ItemStack itemstack = this.villagerInventory.getStackInSlot(i);
/*     */         
/* 416 */         if (itemstack != null) {
/* 417 */           if (itemstack.getItem() == Items.bread && itemstack.stackSize >= 3) {
/* 418 */             flag = true;
/* 419 */             this.villagerInventory.decrStackSize(i, 3);
/* 420 */           } else if ((itemstack.getItem() == Items.potato || itemstack.getItem() == Items.carrot) && itemstack.stackSize >= 12) {
/* 421 */             flag = true;
/* 422 */             this.villagerInventory.decrStackSize(i, 12);
/*     */           } 
/*     */         }
/*     */         
/* 426 */         if (flag) {
/* 427 */           this.worldObj.setEntityState((Entity)this, (byte)18);
/* 428 */           this.isWillingToMate = true;
/*     */           
/*     */           break;
/*     */         } 
/*     */       } 
/*     */     } 
/* 434 */     return this.isWillingToMate;
/*     */   }
/*     */   
/*     */   public void setIsWillingToMate(boolean willingToTrade) {
/* 438 */     this.isWillingToMate = willingToTrade;
/*     */   }
/*     */   
/*     */   public void useRecipe(MerchantRecipe recipe) {
/* 442 */     recipe.incrementToolUses();
/* 443 */     this.livingSoundTime = -getTalkInterval();
/* 444 */     playSound("mob.villager.yes", getSoundVolume(), getSoundPitch());
/* 445 */     int i = 3 + this.rand.nextInt(4);
/*     */     
/* 447 */     if (recipe.getToolUses() == 1 || this.rand.nextInt(5) == 0) {
/* 448 */       this.timeUntilReset = 40;
/* 449 */       this.needsInitilization = true;
/* 450 */       this.isWillingToMate = true;
/*     */       
/* 452 */       if (this.buyingPlayer != null) {
/* 453 */         this.lastBuyingPlayer = this.buyingPlayer.getName();
/*     */       } else {
/* 455 */         this.lastBuyingPlayer = null;
/*     */       } 
/*     */       
/* 458 */       i += 5;
/*     */     } 
/*     */     
/* 461 */     if (recipe.getItemToBuy().getItem() == Items.emerald) {
/* 462 */       this.wealth += (recipe.getItemToBuy()).stackSize;
/*     */     }
/*     */     
/* 465 */     if (recipe.getRewardsExp()) {
/* 466 */       this.worldObj.spawnEntityInWorld((Entity)new EntityXPOrb(this.worldObj, this.posX, this.posY + 0.5D, this.posZ, i));
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void verifySellingItem(ItemStack stack) {
/* 475 */     if (!this.worldObj.isRemote && this.livingSoundTime > -getTalkInterval() + 20) {
/* 476 */       this.livingSoundTime = -getTalkInterval();
/*     */       
/* 478 */       if (stack != null) {
/* 479 */         playSound("mob.villager.yes", getSoundVolume(), getSoundPitch());
/*     */       } else {
/* 481 */         playSound("mob.villager.no", getSoundVolume(), getSoundPitch());
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   public MerchantRecipeList getRecipes(EntityPlayer p_70934_1_) {
/* 487 */     if (this.buyingList == null) {
/* 488 */       populateBuyingList();
/*     */     }
/*     */     
/* 491 */     return this.buyingList;
/*     */   }
/*     */   
/*     */   private void populateBuyingList() {
/* 495 */     ITradeList[][][] aentityvillager$itradelist = DEFAULT_TRADE_LIST_MAP[getProfession()];
/*     */     
/* 497 */     if (this.careerId != 0 && this.careerLevel != 0) {
/* 498 */       this.careerLevel++;
/*     */     } else {
/* 500 */       this.careerId = this.rand.nextInt(aentityvillager$itradelist.length) + 1;
/* 501 */       this.careerLevel = 1;
/*     */     } 
/*     */     
/* 504 */     if (this.buyingList == null) {
/* 505 */       this.buyingList = new MerchantRecipeList();
/*     */     }
/*     */     
/* 508 */     int i = this.careerId - 1;
/* 509 */     int j = this.careerLevel - 1;
/* 510 */     ITradeList[][] aentityvillager$itradelist1 = aentityvillager$itradelist[i];
/*     */     
/* 512 */     if (j >= 0 && j < aentityvillager$itradelist1.length) {
/* 513 */       ITradeList[] aentityvillager$itradelist2 = aentityvillager$itradelist1[j]; byte b; int k;
/*     */       ITradeList[] arrayOfITradeList1;
/* 515 */       for (k = (arrayOfITradeList1 = aentityvillager$itradelist2).length, b = 0; b < k; ) { ITradeList entityvillager$itradelist = arrayOfITradeList1[b];
/* 516 */         entityvillager$itradelist.modifyMerchantRecipeList(this.buyingList, this.rand);
/*     */         b++; }
/*     */     
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void setRecipes(MerchantRecipeList recipeList) {}
/*     */ 
/*     */   
/*     */   public IChatComponent getDisplayName() {
/* 528 */     String s = getCustomNameTag();
/*     */     
/* 530 */     if (s != null && s.length() > 0) {
/* 531 */       ChatComponentText chatcomponenttext = new ChatComponentText(s);
/* 532 */       chatcomponenttext.getChatStyle().setChatHoverEvent(getHoverEvent());
/* 533 */       chatcomponenttext.getChatStyle().setInsertion(getUniqueID().toString());
/* 534 */       return (IChatComponent)chatcomponenttext;
/*     */     } 
/* 536 */     if (this.buyingList == null) {
/* 537 */       populateBuyingList();
/*     */     }
/*     */     
/* 540 */     String s1 = null;
/*     */     
/* 542 */     switch (getProfession()) {
/*     */       case 0:
/* 544 */         if (this.careerId == 1) {
/* 545 */           s1 = "farmer"; break;
/* 546 */         }  if (this.careerId == 2) {
/* 547 */           s1 = "fisherman"; break;
/* 548 */         }  if (this.careerId == 3) {
/* 549 */           s1 = "shepherd"; break;
/* 550 */         }  if (this.careerId == 4) {
/* 551 */           s1 = "fletcher";
/*     */         }
/*     */         break;
/*     */ 
/*     */       
/*     */       case 1:
/* 557 */         s1 = "librarian";
/*     */         break;
/*     */       
/*     */       case 2:
/* 561 */         s1 = "cleric";
/*     */         break;
/*     */       
/*     */       case 3:
/* 565 */         if (this.careerId == 1) {
/* 566 */           s1 = "armor"; break;
/* 567 */         }  if (this.careerId == 2) {
/* 568 */           s1 = "weapon"; break;
/* 569 */         }  if (this.careerId == 3) {
/* 570 */           s1 = "tool";
/*     */         }
/*     */         break;
/*     */ 
/*     */       
/*     */       case 4:
/* 576 */         if (this.careerId == 1) {
/* 577 */           s1 = "butcher"; break;
/* 578 */         }  if (this.careerId == 2) {
/* 579 */           s1 = "leather";
/*     */         }
/*     */         break;
/*     */     } 
/* 583 */     if (s1 != null) {
/* 584 */       ChatComponentTranslation chatcomponenttranslation = new ChatComponentTranslation("entity.Villager." + s1, new Object[0]);
/* 585 */       chatcomponenttranslation.getChatStyle().setChatHoverEvent(getHoverEvent());
/* 586 */       chatcomponenttranslation.getChatStyle().setInsertion(getUniqueID().toString());
/* 587 */       return (IChatComponent)chatcomponenttranslation;
/*     */     } 
/* 589 */     return super.getDisplayName();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public float getEyeHeight() {
/* 595 */     float f = 1.62F;
/*     */     
/* 597 */     if (isChild()) {
/* 598 */       f = (float)(f - 0.81D);
/*     */     }
/*     */     
/* 601 */     return f;
/*     */   }
/*     */   
/*     */   public void handleStatusUpdate(byte id) {
/* 605 */     if (id == 12) {
/* 606 */       spawnParticles(EnumParticleTypes.HEART);
/* 607 */     } else if (id == 13) {
/* 608 */       spawnParticles(EnumParticleTypes.VILLAGER_ANGRY);
/* 609 */     } else if (id == 14) {
/* 610 */       spawnParticles(EnumParticleTypes.VILLAGER_HAPPY);
/*     */     } else {
/* 612 */       super.handleStatusUpdate(id);
/*     */     } 
/*     */   }
/*     */   
/*     */   private void spawnParticles(EnumParticleTypes particleType) {
/* 617 */     for (int i = 0; i < 5; i++) {
/* 618 */       double d0 = this.rand.nextGaussian() * 0.02D;
/* 619 */       double d1 = this.rand.nextGaussian() * 0.02D;
/* 620 */       double d2 = this.rand.nextGaussian() * 0.02D;
/* 621 */       this.worldObj.spawnParticle(particleType, this.posX + (this.rand.nextFloat() * this.width * 2.0F) - this.width, this.posY + 1.0D + (this.rand.nextFloat() * this.height), this.posZ + (this.rand.nextFloat() * this.width * 2.0F) - this.width, d0, d1, d2, new int[0]);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public IEntityLivingData onInitialSpawn(DifficultyInstance difficulty, IEntityLivingData livingdata) {
/* 630 */     livingdata = super.onInitialSpawn(difficulty, livingdata);
/* 631 */     setProfession(this.worldObj.rand.nextInt(5));
/* 632 */     setAdditionalAItasks();
/* 633 */     return livingdata;
/*     */   }
/*     */   
/*     */   public void setLookingForHome() {
/* 637 */     this.isLookingForHome = true;
/*     */   }
/*     */   
/*     */   public EntityVillager createChild(EntityAgeable ageable) {
/* 641 */     EntityVillager entityvillager = new EntityVillager(this.worldObj);
/* 642 */     entityvillager.onInitialSpawn(this.worldObj.getDifficultyForLocation(new BlockPos((Entity)entityvillager)), (IEntityLivingData)null);
/* 643 */     return entityvillager;
/*     */   }
/*     */   
/*     */   public boolean allowLeashing() {
/* 647 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void onStruckByLightning(EntityLightningBolt lightningBolt) {
/* 654 */     if (!this.worldObj.isRemote && !this.isDead) {
/* 655 */       EntityWitch entitywitch = new EntityWitch(this.worldObj);
/* 656 */       entitywitch.setLocationAndAngles(this.posX, this.posY, this.posZ, this.rotationYaw, this.rotationPitch);
/* 657 */       entitywitch.onInitialSpawn(this.worldObj.getDifficultyForLocation(new BlockPos((Entity)entitywitch)), null);
/* 658 */       entitywitch.setNoAI(isAIDisabled());
/*     */       
/* 660 */       if (hasCustomName()) {
/* 661 */         entitywitch.setCustomNameTag(getCustomNameTag());
/* 662 */         entitywitch.setAlwaysRenderNameTag(getAlwaysRenderNameTag());
/*     */       } 
/*     */       
/* 665 */       this.worldObj.spawnEntityInWorld((Entity)entitywitch);
/* 666 */       setDead();
/*     */     } 
/*     */   }
/*     */   
/*     */   public InventoryBasic getVillagerInventory() {
/* 671 */     return this.villagerInventory;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void updateEquipmentIfNeeded(EntityItem itemEntity) {
/* 679 */     ItemStack itemstack = itemEntity.getEntityItem();
/* 680 */     Item item = itemstack.getItem();
/*     */     
/* 682 */     if (canVillagerPickupItem(item)) {
/* 683 */       ItemStack itemstack1 = this.villagerInventory.func_174894_a(itemstack);
/*     */       
/* 685 */       if (itemstack1 == null) {
/* 686 */         itemEntity.setDead();
/*     */       } else {
/* 688 */         itemstack.stackSize = itemstack1.stackSize;
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   private boolean canVillagerPickupItem(Item itemIn) {
/* 694 */     return !(itemIn != Items.bread && itemIn != Items.potato && itemIn != Items.carrot && itemIn != Items.wheat && itemIn != Items.wheat_seeds);
/*     */   }
/*     */   
/*     */   public boolean func_175553_cp() {
/* 698 */     return hasEnoughItems(1);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean canAbondonItems() {
/* 706 */     return hasEnoughItems(2);
/*     */   }
/*     */   
/*     */   public boolean func_175557_cr() {
/* 710 */     boolean flag = (getProfession() == 0);
/* 711 */     return flag ? (!hasEnoughItems(5)) : (!hasEnoughItems(1));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private boolean hasEnoughItems(int multiplier) {
/* 718 */     boolean flag = (getProfession() == 0);
/*     */     
/* 720 */     for (int i = 0; i < this.villagerInventory.getSizeInventory(); i++) {
/* 721 */       ItemStack itemstack = this.villagerInventory.getStackInSlot(i);
/*     */       
/* 723 */       if (itemstack != null) {
/* 724 */         if ((itemstack.getItem() == Items.bread && itemstack.stackSize >= 3 * multiplier) || (itemstack.getItem() == Items.potato && itemstack.stackSize >= 12 * multiplier) || (itemstack.getItem() == Items.carrot && itemstack.stackSize >= 12 * multiplier)) {
/* 725 */           return true;
/*     */         }
/*     */         
/* 728 */         if (flag && itemstack.getItem() == Items.wheat && itemstack.stackSize >= 9 * multiplier) {
/* 729 */           return true;
/*     */         }
/*     */       } 
/*     */     } 
/*     */     
/* 734 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isFarmItemInInventory() {
/* 741 */     for (int i = 0; i < this.villagerInventory.getSizeInventory(); i++) {
/* 742 */       ItemStack itemstack = this.villagerInventory.getStackInSlot(i);
/*     */       
/* 744 */       if (itemstack != null && (itemstack.getItem() == Items.wheat_seeds || itemstack.getItem() == Items.potato || itemstack.getItem() == Items.carrot)) {
/* 745 */         return true;
/*     */       }
/*     */     } 
/*     */     
/* 749 */     return false;
/*     */   }
/*     */   
/*     */   public boolean replaceItemInInventory(int inventorySlot, ItemStack itemStackIn) {
/* 753 */     if (super.replaceItemInInventory(inventorySlot, itemStackIn)) {
/* 754 */       return true;
/*     */     }
/* 756 */     int i = inventorySlot - 300;
/*     */     
/* 758 */     if (i >= 0 && i < this.villagerInventory.getSizeInventory()) {
/* 759 */       this.villagerInventory.setInventorySlotContents(i, itemStackIn);
/* 760 */       return true;
/*     */     } 
/* 762 */     return false;
/*     */   }
/*     */   
/*     */   static class EmeraldForItems
/*     */     implements ITradeList
/*     */   {
/*     */     public Item sellItem;
/*     */     public EntityVillager.PriceInfo price;
/*     */     
/*     */     public EmeraldForItems(Item itemIn, EntityVillager.PriceInfo priceIn) {
/* 772 */       this.sellItem = itemIn;
/* 773 */       this.price = priceIn;
/*     */     }
/*     */     
/*     */     public void modifyMerchantRecipeList(MerchantRecipeList recipeList, Random random) {
/* 777 */       int i = 1;
/*     */       
/* 779 */       if (this.price != null) {
/* 780 */         i = this.price.getPrice(random);
/*     */       }
/*     */       
/* 783 */       recipeList.add(new MerchantRecipe(new ItemStack(this.sellItem, i, 0), Items.emerald));
/*     */     }
/*     */   }
/*     */   
/*     */   static interface ITradeList {
/*     */     void modifyMerchantRecipeList(MerchantRecipeList param1MerchantRecipeList, Random param1Random);
/*     */   }
/*     */   
/*     */   static class ItemAndEmeraldToItem implements ITradeList {
/*     */     public ItemStack buyingItemStack;
/*     */     public EntityVillager.PriceInfo buyingPriceInfo;
/*     */     public ItemStack sellingItemstack;
/*     */     public EntityVillager.PriceInfo field_179408_d;
/*     */     
/*     */     public ItemAndEmeraldToItem(Item p_i45813_1_, EntityVillager.PriceInfo p_i45813_2_, Item p_i45813_3_, EntityVillager.PriceInfo p_i45813_4_) {
/* 798 */       this.buyingItemStack = new ItemStack(p_i45813_1_);
/* 799 */       this.buyingPriceInfo = p_i45813_2_;
/* 800 */       this.sellingItemstack = new ItemStack(p_i45813_3_);
/* 801 */       this.field_179408_d = p_i45813_4_;
/*     */     }
/*     */     
/*     */     public void modifyMerchantRecipeList(MerchantRecipeList recipeList, Random random) {
/* 805 */       int i = 1;
/*     */       
/* 807 */       if (this.buyingPriceInfo != null) {
/* 808 */         i = this.buyingPriceInfo.getPrice(random);
/*     */       }
/*     */       
/* 811 */       int j = 1;
/*     */       
/* 813 */       if (this.field_179408_d != null) {
/* 814 */         j = this.field_179408_d.getPrice(random);
/*     */       }
/*     */       
/* 817 */       recipeList.add(new MerchantRecipe(new ItemStack(this.buyingItemStack.getItem(), i, this.buyingItemStack.getMetadata()), new ItemStack(Items.emerald), new ItemStack(this.sellingItemstack.getItem(), j, this.sellingItemstack.getMetadata())));
/*     */     }
/*     */   }
/*     */   
/*     */   static class ListEnchantedBookForEmeralds implements ITradeList {
/*     */     public void modifyMerchantRecipeList(MerchantRecipeList recipeList, Random random) {
/* 823 */       Enchantment enchantment = Enchantment.enchantmentsBookList[random.nextInt(Enchantment.enchantmentsBookList.length)];
/* 824 */       int i = MathHelper.getRandomIntegerInRange(random, enchantment.getMinLevel(), enchantment.getMaxLevel());
/* 825 */       ItemStack itemstack = Items.enchanted_book.getEnchantedItemStack(new EnchantmentData(enchantment, i));
/* 826 */       int j = 2 + random.nextInt(5 + i * 10) + 3 * i;
/*     */       
/* 828 */       if (j > 64) {
/* 829 */         j = 64;
/*     */       }
/*     */       
/* 832 */       recipeList.add(new MerchantRecipe(new ItemStack(Items.book), new ItemStack(Items.emerald, j), itemstack));
/*     */     }
/*     */   }
/*     */   
/*     */   static class ListEnchantedItemForEmeralds implements ITradeList {
/*     */     public ItemStack enchantedItemStack;
/*     */     public EntityVillager.PriceInfo priceInfo;
/*     */     
/*     */     public ListEnchantedItemForEmeralds(Item p_i45814_1_, EntityVillager.PriceInfo p_i45814_2_) {
/* 841 */       this.enchantedItemStack = new ItemStack(p_i45814_1_);
/* 842 */       this.priceInfo = p_i45814_2_;
/*     */     }
/*     */     
/*     */     public void modifyMerchantRecipeList(MerchantRecipeList recipeList, Random random) {
/* 846 */       int i = 1;
/*     */       
/* 848 */       if (this.priceInfo != null) {
/* 849 */         i = this.priceInfo.getPrice(random);
/*     */       }
/*     */       
/* 852 */       ItemStack itemstack = new ItemStack(Items.emerald, i, 0);
/* 853 */       ItemStack itemstack1 = new ItemStack(this.enchantedItemStack.getItem(), 1, this.enchantedItemStack.getMetadata());
/* 854 */       itemstack1 = EnchantmentHelper.addRandomEnchantment(random, itemstack1, 5 + random.nextInt(15));
/* 855 */       recipeList.add(new MerchantRecipe(itemstack, itemstack1));
/*     */     }
/*     */   }
/*     */   
/*     */   static class ListItemForEmeralds implements ITradeList {
/*     */     public ItemStack itemToBuy;
/*     */     public EntityVillager.PriceInfo priceInfo;
/*     */     
/*     */     public ListItemForEmeralds(Item par1Item, EntityVillager.PriceInfo priceInfo) {
/* 864 */       this.itemToBuy = new ItemStack(par1Item);
/* 865 */       this.priceInfo = priceInfo;
/*     */     }
/*     */     
/*     */     public ListItemForEmeralds(ItemStack stack, EntityVillager.PriceInfo priceInfo) {
/* 869 */       this.itemToBuy = stack;
/* 870 */       this.priceInfo = priceInfo;
/*     */     }
/*     */     public void modifyMerchantRecipeList(MerchantRecipeList recipeList, Random random) {
/*     */       ItemStack itemstack, itemstack1;
/* 874 */       int i = 1;
/*     */       
/* 876 */       if (this.priceInfo != null) {
/* 877 */         i = this.priceInfo.getPrice(random);
/*     */       }
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 883 */       if (i < 0) {
/* 884 */         itemstack = new ItemStack(Items.emerald, 1, 0);
/* 885 */         itemstack1 = new ItemStack(this.itemToBuy.getItem(), -i, this.itemToBuy.getMetadata());
/*     */       } else {
/* 887 */         itemstack = new ItemStack(Items.emerald, i, 0);
/* 888 */         itemstack1 = new ItemStack(this.itemToBuy.getItem(), 1, this.itemToBuy.getMetadata());
/*     */       } 
/*     */       
/* 891 */       recipeList.add(new MerchantRecipe(itemstack, itemstack1));
/*     */     }
/*     */   }
/*     */   
/*     */   static class PriceInfo extends Tuple<Integer, Integer> {
/*     */     public PriceInfo(int p_i45810_1_, int p_i45810_2_) {
/* 897 */       super(Integer.valueOf(p_i45810_1_), Integer.valueOf(p_i45810_2_));
/*     */     }
/*     */     
/*     */     public int getPrice(Random rand) {
/* 901 */       return (((Integer)getFirst()).intValue() >= ((Integer)getSecond()).intValue()) ? ((Integer)getFirst()).intValue() : (((Integer)getFirst()).intValue() + rand.nextInt(((Integer)getSecond()).intValue() - ((Integer)getFirst()).intValue() + 1));
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\minecraft\entity\passive\EntityVillager.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */