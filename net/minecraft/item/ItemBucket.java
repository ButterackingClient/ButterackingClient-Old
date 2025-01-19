/*     */ package net.minecraft.item;
/*     */ 
/*     */ import net.minecraft.block.Block;
/*     */ import net.minecraft.block.BlockLiquid;
/*     */ import net.minecraft.block.material.Material;
/*     */ import net.minecraft.block.properties.IProperty;
/*     */ import net.minecraft.block.state.IBlockState;
/*     */ import net.minecraft.creativetab.CreativeTabs;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.init.Blocks;
/*     */ import net.minecraft.init.Items;
/*     */ import net.minecraft.stats.StatList;
/*     */ import net.minecraft.util.BlockPos;
/*     */ import net.minecraft.util.EnumParticleTypes;
/*     */ import net.minecraft.util.MovingObjectPosition;
/*     */ import net.minecraft.world.World;
/*     */ 
/*     */ public class ItemBucket
/*     */   extends Item
/*     */ {
/*     */   private Block isFull;
/*     */   
/*     */   public ItemBucket(Block containedBlock) {
/*  24 */     this.maxStackSize = 1;
/*  25 */     this.isFull = containedBlock;
/*  26 */     setCreativeTab(CreativeTabs.tabMisc);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ItemStack onItemRightClick(ItemStack itemStackIn, World worldIn, EntityPlayer playerIn) {
/*  33 */     boolean flag = (this.isFull == Blocks.air);
/*  34 */     MovingObjectPosition movingobjectposition = getMovingObjectPositionFromPlayer(worldIn, playerIn, flag);
/*     */     
/*  36 */     if (movingobjectposition == null) {
/*  37 */       return itemStackIn;
/*     */     }
/*  39 */     if (movingobjectposition.typeOfHit == MovingObjectPosition.MovingObjectType.BLOCK) {
/*  40 */       BlockPos blockpos = movingobjectposition.getBlockPos();
/*     */       
/*  42 */       if (!worldIn.isBlockModifiable(playerIn, blockpos)) {
/*  43 */         return itemStackIn;
/*     */       }
/*     */       
/*  46 */       if (flag) {
/*  47 */         if (!playerIn.canPlayerEdit(blockpos.offset(movingobjectposition.sideHit), movingobjectposition.sideHit, itemStackIn)) {
/*  48 */           return itemStackIn;
/*     */         }
/*     */         
/*  51 */         IBlockState iblockstate = worldIn.getBlockState(blockpos);
/*  52 */         Material material = iblockstate.getBlock().getMaterial();
/*     */         
/*  54 */         if (material == Material.water && ((Integer)iblockstate.getValue((IProperty)BlockLiquid.LEVEL)).intValue() == 0) {
/*  55 */           worldIn.setBlockToAir(blockpos);
/*  56 */           playerIn.triggerAchievement(StatList.objectUseStats[Item.getIdFromItem(this)]);
/*  57 */           return fillBucket(itemStackIn, playerIn, Items.water_bucket);
/*     */         } 
/*     */         
/*  60 */         if (material == Material.lava && ((Integer)iblockstate.getValue((IProperty)BlockLiquid.LEVEL)).intValue() == 0) {
/*  61 */           worldIn.setBlockToAir(blockpos);
/*  62 */           playerIn.triggerAchievement(StatList.objectUseStats[Item.getIdFromItem(this)]);
/*  63 */           return fillBucket(itemStackIn, playerIn, Items.lava_bucket);
/*     */         } 
/*     */       } else {
/*  66 */         if (this.isFull == Blocks.air) {
/*  67 */           return new ItemStack(Items.bucket);
/*     */         }
/*     */         
/*  70 */         BlockPos blockpos1 = blockpos.offset(movingobjectposition.sideHit);
/*     */         
/*  72 */         if (!playerIn.canPlayerEdit(blockpos1, movingobjectposition.sideHit, itemStackIn)) {
/*  73 */           return itemStackIn;
/*     */         }
/*     */         
/*  76 */         if (tryPlaceContainedLiquid(worldIn, blockpos1) && !playerIn.capabilities.isCreativeMode) {
/*  77 */           playerIn.triggerAchievement(StatList.objectUseStats[Item.getIdFromItem(this)]);
/*  78 */           return new ItemStack(Items.bucket);
/*     */         } 
/*     */       } 
/*     */     } 
/*     */     
/*  83 */     return itemStackIn;
/*     */   }
/*     */ 
/*     */   
/*     */   private ItemStack fillBucket(ItemStack emptyBuckets, EntityPlayer player, Item fullBucket) {
/*  88 */     if (player.capabilities.isCreativeMode)
/*  89 */       return emptyBuckets; 
/*  90 */     if (--emptyBuckets.stackSize <= 0) {
/*  91 */       return new ItemStack(fullBucket);
/*     */     }
/*  93 */     if (!player.inventory.addItemStackToInventory(new ItemStack(fullBucket))) {
/*  94 */       player.dropPlayerItemWithRandomChoice(new ItemStack(fullBucket, 1, 0), false);
/*     */     }
/*     */     
/*  97 */     return emptyBuckets;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean tryPlaceContainedLiquid(World worldIn, BlockPos pos) {
/* 102 */     if (this.isFull == Blocks.air) {
/* 103 */       return false;
/*     */     }
/* 105 */     Material material = worldIn.getBlockState(pos).getBlock().getMaterial();
/* 106 */     boolean flag = !material.isSolid();
/*     */     
/* 108 */     if (!worldIn.isAirBlock(pos) && !flag) {
/* 109 */       return false;
/*     */     }
/* 111 */     if (worldIn.provider.doesWaterVaporize() && this.isFull == Blocks.flowing_water) {
/* 112 */       int i = pos.getX();
/* 113 */       int j = pos.getY();
/* 114 */       int k = pos.getZ();
/* 115 */       worldIn.playSoundEffect((i + 0.5F), (j + 0.5F), (k + 0.5F), "random.fizz", 0.5F, 2.6F + (worldIn.rand.nextFloat() - worldIn.rand.nextFloat()) * 0.8F);
/*     */       
/* 117 */       for (int l = 0; l < 8; l++) {
/* 118 */         worldIn.spawnParticle(EnumParticleTypes.SMOKE_LARGE, i + Math.random(), j + Math.random(), k + Math.random(), 0.0D, 0.0D, 0.0D, new int[0]);
/*     */       }
/*     */     } else {
/* 121 */       if (!worldIn.isRemote && flag && !material.isLiquid()) {
/* 122 */         worldIn.destroyBlock(pos, true);
/*     */       }
/*     */       
/* 125 */       worldIn.setBlockState(pos, this.isFull.getDefaultState(), 3);
/*     */     } 
/*     */     
/* 128 */     return true;
/*     */   }
/*     */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\minecraft\item\ItemBucket.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */