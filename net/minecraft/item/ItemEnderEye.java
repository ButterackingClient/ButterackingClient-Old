/*     */ package net.minecraft.item;
/*     */ import net.minecraft.block.BlockEndPortalFrame;
/*     */ import net.minecraft.block.properties.IProperty;
/*     */ import net.minecraft.block.state.IBlockState;
/*     */ import net.minecraft.creativetab.CreativeTabs;
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.entity.item.EntityEnderEye;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.init.Blocks;
/*     */ import net.minecraft.util.BlockPos;
/*     */ import net.minecraft.util.EnumFacing;
/*     */ import net.minecraft.util.EnumParticleTypes;
/*     */ import net.minecraft.util.MovingObjectPosition;
/*     */ import net.minecraft.world.World;
/*     */ 
/*     */ public class ItemEnderEye extends Item {
/*     */   public ItemEnderEye() {
/*  18 */     setCreativeTab(CreativeTabs.tabMisc);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean onItemUse(ItemStack stack, EntityPlayer playerIn, World worldIn, BlockPos pos, EnumFacing side, float hitX, float hitY, float hitZ) {
/*  25 */     IBlockState iblockstate = worldIn.getBlockState(pos);
/*     */     
/*  27 */     if (playerIn.canPlayerEdit(pos.offset(side), side, stack) && iblockstate.getBlock() == Blocks.end_portal_frame && !((Boolean)iblockstate.getValue((IProperty)BlockEndPortalFrame.EYE)).booleanValue()) {
/*  28 */       if (worldIn.isRemote) {
/*  29 */         return true;
/*     */       }
/*  31 */       worldIn.setBlockState(pos, iblockstate.withProperty((IProperty)BlockEndPortalFrame.EYE, Boolean.valueOf(true)), 2);
/*  32 */       worldIn.updateComparatorOutputLevel(pos, Blocks.end_portal_frame);
/*  33 */       stack.stackSize--;
/*     */       
/*  35 */       for (int i = 0; i < 16; i++) {
/*  36 */         double d0 = (pos.getX() + (5.0F + itemRand.nextFloat() * 6.0F) / 16.0F);
/*  37 */         double d1 = (pos.getY() + 0.8125F);
/*  38 */         double d2 = (pos.getZ() + (5.0F + itemRand.nextFloat() * 6.0F) / 16.0F);
/*  39 */         double d3 = 0.0D;
/*  40 */         double d4 = 0.0D;
/*  41 */         double d5 = 0.0D;
/*  42 */         worldIn.spawnParticle(EnumParticleTypes.SMOKE_NORMAL, d0, d1, d2, d3, d4, d5, new int[0]);
/*     */       } 
/*     */       
/*  45 */       EnumFacing enumfacing = (EnumFacing)iblockstate.getValue((IProperty)BlockEndPortalFrame.FACING);
/*  46 */       int l = 0;
/*  47 */       int j = 0;
/*  48 */       boolean flag1 = false;
/*  49 */       boolean flag = true;
/*  50 */       EnumFacing enumfacing1 = enumfacing.rotateY();
/*     */       
/*  52 */       for (int k = -2; k <= 2; k++) {
/*  53 */         BlockPos blockpos1 = pos.offset(enumfacing1, k);
/*  54 */         IBlockState iblockstate1 = worldIn.getBlockState(blockpos1);
/*     */         
/*  56 */         if (iblockstate1.getBlock() == Blocks.end_portal_frame) {
/*  57 */           if (!((Boolean)iblockstate1.getValue((IProperty)BlockEndPortalFrame.EYE)).booleanValue()) {
/*  58 */             flag = false;
/*     */             
/*     */             break;
/*     */           } 
/*  62 */           j = k;
/*     */           
/*  64 */           if (!flag1) {
/*  65 */             l = k;
/*  66 */             flag1 = true;
/*     */           } 
/*     */         } 
/*     */       } 
/*     */       
/*  71 */       if (flag && j == l + 2) {
/*  72 */         BlockPos blockpos = pos.offset(enumfacing, 4);
/*     */         
/*  74 */         for (int i1 = l; i1 <= j; i1++) {
/*  75 */           BlockPos blockpos2 = blockpos.offset(enumfacing1, i1);
/*  76 */           IBlockState iblockstate3 = worldIn.getBlockState(blockpos2);
/*     */           
/*  78 */           if (iblockstate3.getBlock() != Blocks.end_portal_frame || !((Boolean)iblockstate3.getValue((IProperty)BlockEndPortalFrame.EYE)).booleanValue()) {
/*  79 */             flag = false;
/*     */             
/*     */             break;
/*     */           } 
/*     */         } 
/*  84 */         for (int j1 = l - 1; j1 <= j + 1; j1 += 4) {
/*  85 */           blockpos = pos.offset(enumfacing1, j1);
/*     */           
/*  87 */           for (int l1 = 1; l1 <= 3; l1++) {
/*  88 */             BlockPos blockpos3 = blockpos.offset(enumfacing, l1);
/*  89 */             IBlockState iblockstate2 = worldIn.getBlockState(blockpos3);
/*     */             
/*  91 */             if (iblockstate2.getBlock() != Blocks.end_portal_frame || !((Boolean)iblockstate2.getValue((IProperty)BlockEndPortalFrame.EYE)).booleanValue()) {
/*  92 */               flag = false;
/*     */               
/*     */               break;
/*     */             } 
/*     */           } 
/*     */         } 
/*  98 */         if (flag) {
/*  99 */           for (int k1 = l; k1 <= j; k1++) {
/* 100 */             blockpos = pos.offset(enumfacing1, k1);
/*     */             
/* 102 */             for (int i2 = 1; i2 <= 3; i2++) {
/* 103 */               BlockPos blockpos4 = blockpos.offset(enumfacing, i2);
/* 104 */               worldIn.setBlockState(blockpos4, Blocks.end_portal.getDefaultState(), 2);
/*     */             } 
/*     */           } 
/*     */         }
/*     */       } 
/*     */       
/* 110 */       return true;
/*     */     } 
/*     */     
/* 113 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ItemStack onItemRightClick(ItemStack itemStackIn, World worldIn, EntityPlayer playerIn) {
/* 121 */     MovingObjectPosition movingobjectposition = getMovingObjectPositionFromPlayer(worldIn, playerIn, false);
/*     */     
/* 123 */     if (movingobjectposition != null && movingobjectposition.typeOfHit == MovingObjectPosition.MovingObjectType.BLOCK && worldIn.getBlockState(movingobjectposition.getBlockPos()).getBlock() == Blocks.end_portal_frame) {
/* 124 */       return itemStackIn;
/*     */     }
/* 126 */     if (!worldIn.isRemote) {
/* 127 */       BlockPos blockpos = worldIn.getStrongholdPos("Stronghold", new BlockPos((Entity)playerIn));
/*     */       
/* 129 */       if (blockpos != null) {
/* 130 */         EntityEnderEye entityendereye = new EntityEnderEye(worldIn, playerIn.posX, playerIn.posY, playerIn.posZ);
/* 131 */         entityendereye.moveTowards(blockpos);
/* 132 */         worldIn.spawnEntityInWorld((Entity)entityendereye);
/* 133 */         worldIn.playSoundAtEntity((Entity)playerIn, "random.bow", 0.5F, 0.4F / (itemRand.nextFloat() * 0.4F + 0.8F));
/* 134 */         worldIn.playAuxSFXAtEntity(null, 1002, new BlockPos((Entity)playerIn), 0);
/*     */         
/* 136 */         if (!playerIn.capabilities.isCreativeMode) {
/* 137 */           itemStackIn.stackSize--;
/*     */         }
/*     */         
/* 140 */         playerIn.triggerAchievement(StatList.objectUseStats[Item.getIdFromItem(this)]);
/*     */       } 
/*     */     } 
/*     */     
/* 144 */     return itemStackIn;
/*     */   }
/*     */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\minecraft\item\ItemEnderEye.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */