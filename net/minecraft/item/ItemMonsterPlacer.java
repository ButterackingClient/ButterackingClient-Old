/*     */ package net.minecraft.item;
/*     */ 
/*     */ import java.util.List;
/*     */ import net.minecraft.block.state.IBlockState;
/*     */ import net.minecraft.creativetab.CreativeTabs;
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.entity.EntityList;
/*     */ import net.minecraft.entity.EntityLiving;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.init.Blocks;
/*     */ import net.minecraft.stats.StatList;
/*     */ import net.minecraft.tileentity.MobSpawnerBaseLogic;
/*     */ import net.minecraft.tileentity.TileEntity;
/*     */ import net.minecraft.tileentity.TileEntityMobSpawner;
/*     */ import net.minecraft.util.BlockPos;
/*     */ import net.minecraft.util.EnumFacing;
/*     */ import net.minecraft.util.MathHelper;
/*     */ import net.minecraft.util.MovingObjectPosition;
/*     */ import net.minecraft.util.StatCollector;
/*     */ import net.minecraft.world.World;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class ItemMonsterPlacer
/*     */   extends Item
/*     */ {
/*     */   public ItemMonsterPlacer() {
/*  29 */     setHasSubtypes(true);
/*  30 */     setCreativeTab(CreativeTabs.tabMisc);
/*     */   }
/*     */   
/*     */   public String getItemStackDisplayName(ItemStack stack) {
/*  34 */     String s = StatCollector.translateToLocal(String.valueOf(getUnlocalizedName()) + ".name").trim();
/*  35 */     String s1 = EntityList.getStringFromID(stack.getMetadata());
/*     */     
/*  37 */     if (s1 != null) {
/*  38 */       s = String.valueOf(s) + " " + StatCollector.translateToLocal("entity." + s1 + ".name");
/*     */     }
/*     */     
/*  41 */     return s;
/*     */   }
/*     */   
/*     */   public int getColorFromItemStack(ItemStack stack, int renderPass) {
/*  45 */     EntityList.EntityEggInfo entitylist$entityegginfo = (EntityList.EntityEggInfo)EntityList.entityEggs.get(Integer.valueOf(stack.getMetadata()));
/*  46 */     return (entitylist$entityegginfo != null) ? ((renderPass == 0) ? entitylist$entityegginfo.primaryColor : entitylist$entityegginfo.secondaryColor) : 16777215;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean onItemUse(ItemStack stack, EntityPlayer playerIn, World worldIn, BlockPos pos, EnumFacing side, float hitX, float hitY, float hitZ) {
/*  53 */     if (worldIn.isRemote)
/*  54 */       return true; 
/*  55 */     if (!playerIn.canPlayerEdit(pos.offset(side), side, stack)) {
/*  56 */       return false;
/*     */     }
/*  58 */     IBlockState iblockstate = worldIn.getBlockState(pos);
/*     */     
/*  60 */     if (iblockstate.getBlock() == Blocks.mob_spawner) {
/*  61 */       TileEntity tileentity = worldIn.getTileEntity(pos);
/*     */       
/*  63 */       if (tileentity instanceof TileEntityMobSpawner) {
/*  64 */         MobSpawnerBaseLogic mobspawnerbaselogic = ((TileEntityMobSpawner)tileentity).getSpawnerBaseLogic();
/*  65 */         mobspawnerbaselogic.setEntityName(EntityList.getStringFromID(stack.getMetadata()));
/*  66 */         tileentity.markDirty();
/*  67 */         worldIn.markBlockForUpdate(pos);
/*     */         
/*  69 */         if (!playerIn.capabilities.isCreativeMode) {
/*  70 */           stack.stackSize--;
/*     */         }
/*     */         
/*  73 */         return true;
/*     */       } 
/*     */     } 
/*     */     
/*  77 */     pos = pos.offset(side);
/*  78 */     double d0 = 0.0D;
/*     */     
/*  80 */     if (side == EnumFacing.UP && iblockstate instanceof net.minecraft.block.BlockFence) {
/*  81 */       d0 = 0.5D;
/*     */     }
/*     */     
/*  84 */     Entity entity = spawnCreature(worldIn, stack.getMetadata(), pos.getX() + 0.5D, pos.getY() + d0, pos.getZ() + 0.5D);
/*     */     
/*  86 */     if (entity != null) {
/*  87 */       if (entity instanceof net.minecraft.entity.EntityLivingBase && stack.hasDisplayName()) {
/*  88 */         entity.setCustomNameTag(stack.getDisplayName());
/*     */       }
/*     */       
/*  91 */       if (!playerIn.capabilities.isCreativeMode) {
/*  92 */         stack.stackSize--;
/*     */       }
/*     */     } 
/*     */     
/*  96 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ItemStack onItemRightClick(ItemStack itemStackIn, World worldIn, EntityPlayer playerIn) {
/* 104 */     if (worldIn.isRemote) {
/* 105 */       return itemStackIn;
/*     */     }
/* 107 */     MovingObjectPosition movingobjectposition = getMovingObjectPositionFromPlayer(worldIn, playerIn, true);
/*     */     
/* 109 */     if (movingobjectposition == null) {
/* 110 */       return itemStackIn;
/*     */     }
/* 112 */     if (movingobjectposition.typeOfHit == MovingObjectPosition.MovingObjectType.BLOCK) {
/* 113 */       BlockPos blockpos = movingobjectposition.getBlockPos();
/*     */       
/* 115 */       if (!worldIn.isBlockModifiable(playerIn, blockpos)) {
/* 116 */         return itemStackIn;
/*     */       }
/*     */       
/* 119 */       if (!playerIn.canPlayerEdit(blockpos, movingobjectposition.sideHit, itemStackIn)) {
/* 120 */         return itemStackIn;
/*     */       }
/*     */       
/* 123 */       if (worldIn.getBlockState(blockpos).getBlock() instanceof net.minecraft.block.BlockLiquid) {
/* 124 */         Entity entity = spawnCreature(worldIn, itemStackIn.getMetadata(), blockpos.getX() + 0.5D, blockpos.getY() + 0.5D, blockpos.getZ() + 0.5D);
/*     */         
/* 126 */         if (entity != null) {
/* 127 */           if (entity instanceof net.minecraft.entity.EntityLivingBase && itemStackIn.hasDisplayName()) {
/* 128 */             ((EntityLiving)entity).setCustomNameTag(itemStackIn.getDisplayName());
/*     */           }
/*     */           
/* 131 */           if (!playerIn.capabilities.isCreativeMode) {
/* 132 */             itemStackIn.stackSize--;
/*     */           }
/*     */           
/* 135 */           playerIn.triggerAchievement(StatList.objectUseStats[Item.getIdFromItem(this)]);
/*     */         } 
/*     */       } 
/*     */     } 
/*     */     
/* 140 */     return itemStackIn;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static Entity spawnCreature(World worldIn, int entityID, double x, double y, double z) {
/* 150 */     if (!EntityList.entityEggs.containsKey(Integer.valueOf(entityID))) {
/* 151 */       return null;
/*     */     }
/* 153 */     Entity entity = null;
/*     */     
/* 155 */     for (int i = 0; i < 1; i++) {
/* 156 */       entity = EntityList.createEntityByID(entityID, worldIn);
/*     */       
/* 158 */       if (entity instanceof net.minecraft.entity.EntityLivingBase) {
/* 159 */         EntityLiving entityliving = (EntityLiving)entity;
/* 160 */         entity.setLocationAndAngles(x, y, z, MathHelper.wrapAngleTo180_float(worldIn.rand.nextFloat() * 360.0F), 0.0F);
/* 161 */         entityliving.rotationYawHead = entityliving.rotationYaw;
/* 162 */         entityliving.renderYawOffset = entityliving.rotationYaw;
/* 163 */         entityliving.onInitialSpawn(worldIn.getDifficultyForLocation(new BlockPos((Entity)entityliving)), null);
/* 164 */         worldIn.spawnEntityInWorld(entity);
/* 165 */         entityliving.playLivingSound();
/*     */       } 
/*     */     } 
/*     */     
/* 169 */     return entity;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void getSubItems(Item itemIn, CreativeTabs tab, List<ItemStack> subItems) {
/* 177 */     for (EntityList.EntityEggInfo entitylist$entityegginfo : EntityList.entityEggs.values())
/* 178 */       subItems.add(new ItemStack(itemIn, 1, entitylist$entityegginfo.spawnedID)); 
/*     */   }
/*     */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\minecraft\item\ItemMonsterPlacer.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */