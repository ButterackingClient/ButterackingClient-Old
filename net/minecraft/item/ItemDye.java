/*     */ package net.minecraft.item;
/*     */ import java.util.List;
/*     */ import net.minecraft.block.Block;
/*     */ import net.minecraft.block.BlockPlanks;
/*     */ import net.minecraft.block.IGrowable;
/*     */ import net.minecraft.block.material.Material;
/*     */ import net.minecraft.block.properties.IProperty;
/*     */ import net.minecraft.block.state.IBlockState;
/*     */ import net.minecraft.creativetab.CreativeTabs;
/*     */ import net.minecraft.entity.EntityLivingBase;
/*     */ import net.minecraft.entity.passive.EntitySheep;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.init.Blocks;
/*     */ import net.minecraft.util.BlockPos;
/*     */ import net.minecraft.util.EnumFacing;
/*     */ import net.minecraft.util.EnumParticleTypes;
/*     */ import net.minecraft.world.IBlockAccess;
/*     */ import net.minecraft.world.World;
/*     */ 
/*     */ public class ItemDye extends Item {
/*  21 */   public static final int[] dyeColors = new int[] { 1973019, 11743532, 3887386, 5320730, 2437522, 8073150, 2651799, 11250603, 4408131, 14188952, 4312372, 14602026, 6719955, 12801229, 15435844, 15790320 };
/*     */   
/*     */   public ItemDye() {
/*  24 */     setHasSubtypes(true);
/*  25 */     setMaxDamage(0);
/*  26 */     setCreativeTab(CreativeTabs.tabMaterials);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getUnlocalizedName(ItemStack stack) {
/*  34 */     int i = stack.getMetadata();
/*  35 */     return String.valueOf(getUnlocalizedName()) + "." + EnumDyeColor.byDyeDamage(i).getUnlocalizedName();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean onItemUse(ItemStack stack, EntityPlayer playerIn, World worldIn, BlockPos pos, EnumFacing side, float hitX, float hitY, float hitZ) {
/*  42 */     if (!playerIn.canPlayerEdit(pos.offset(side), side, stack)) {
/*  43 */       return false;
/*     */     }
/*  45 */     EnumDyeColor enumdyecolor = EnumDyeColor.byDyeDamage(stack.getMetadata());
/*     */     
/*  47 */     if (enumdyecolor == EnumDyeColor.WHITE) {
/*  48 */       if (applyBonemeal(stack, worldIn, pos)) {
/*  49 */         if (!worldIn.isRemote) {
/*  50 */           worldIn.playAuxSFX(2005, pos, 0);
/*     */         }
/*     */         
/*  53 */         return true;
/*     */       } 
/*  55 */     } else if (enumdyecolor == EnumDyeColor.BROWN) {
/*  56 */       IBlockState iblockstate = worldIn.getBlockState(pos);
/*  57 */       Block block = iblockstate.getBlock();
/*     */       
/*  59 */       if (block == Blocks.log && iblockstate.getValue((IProperty)BlockPlanks.VARIANT) == BlockPlanks.EnumType.JUNGLE) {
/*  60 */         if (side == EnumFacing.DOWN) {
/*  61 */           return false;
/*     */         }
/*     */         
/*  64 */         if (side == EnumFacing.UP) {
/*  65 */           return false;
/*     */         }
/*     */         
/*  68 */         pos = pos.offset(side);
/*     */         
/*  70 */         if (worldIn.isAirBlock(pos)) {
/*  71 */           IBlockState iblockstate1 = Blocks.cocoa.onBlockPlaced(worldIn, pos, side, hitX, hitY, hitZ, 0, (EntityLivingBase)playerIn);
/*  72 */           worldIn.setBlockState(pos, iblockstate1, 2);
/*     */           
/*  74 */           if (!playerIn.capabilities.isCreativeMode) {
/*  75 */             stack.stackSize--;
/*     */           }
/*     */         } 
/*     */         
/*  79 */         return true;
/*     */       } 
/*     */     } 
/*     */     
/*  83 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public static boolean applyBonemeal(ItemStack stack, World worldIn, BlockPos target) {
/*  88 */     IBlockState iblockstate = worldIn.getBlockState(target);
/*     */     
/*  90 */     if (iblockstate.getBlock() instanceof IGrowable) {
/*  91 */       IGrowable igrowable = (IGrowable)iblockstate.getBlock();
/*     */       
/*  93 */       if (igrowable.canGrow(worldIn, target, iblockstate, worldIn.isRemote)) {
/*  94 */         if (!worldIn.isRemote) {
/*  95 */           if (igrowable.canUseBonemeal(worldIn, worldIn.rand, target, iblockstate)) {
/*  96 */             igrowable.grow(worldIn, worldIn.rand, target, iblockstate);
/*     */           }
/*     */           
/*  99 */           stack.stackSize--;
/*     */         } 
/*     */         
/* 102 */         return true;
/*     */       } 
/*     */     } 
/*     */     
/* 106 */     return false;
/*     */   }
/*     */   
/*     */   public static void spawnBonemealParticles(World worldIn, BlockPos pos, int amount) {
/* 110 */     if (amount == 0) {
/* 111 */       amount = 15;
/*     */     }
/*     */     
/* 114 */     Block block = worldIn.getBlockState(pos).getBlock();
/*     */     
/* 116 */     if (block.getMaterial() != Material.air) {
/* 117 */       block.setBlockBoundsBasedOnState((IBlockAccess)worldIn, pos);
/*     */       
/* 119 */       for (int i = 0; i < amount; i++) {
/* 120 */         double d0 = itemRand.nextGaussian() * 0.02D;
/* 121 */         double d1 = itemRand.nextGaussian() * 0.02D;
/* 122 */         double d2 = itemRand.nextGaussian() * 0.02D;
/* 123 */         worldIn.spawnParticle(EnumParticleTypes.VILLAGER_HAPPY, (pos.getX() + itemRand.nextFloat()), pos.getY() + itemRand.nextFloat() * block.getBlockBoundsMaxY(), (pos.getZ() + itemRand.nextFloat()), d0, d1, d2, new int[0]);
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean itemInteractionForEntity(ItemStack stack, EntityPlayer playerIn, EntityLivingBase target) {
/* 132 */     if (target instanceof EntitySheep) {
/* 133 */       EntitySheep entitysheep = (EntitySheep)target;
/* 134 */       EnumDyeColor enumdyecolor = EnumDyeColor.byDyeDamage(stack.getMetadata());
/*     */       
/* 136 */       if (!entitysheep.getSheared() && entitysheep.getFleeceColor() != enumdyecolor) {
/* 137 */         entitysheep.setFleeceColor(enumdyecolor);
/* 138 */         stack.stackSize--;
/*     */       } 
/*     */       
/* 141 */       return true;
/*     */     } 
/* 143 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void getSubItems(Item itemIn, CreativeTabs tab, List<ItemStack> subItems) {
/* 151 */     for (int i = 0; i < 16; i++)
/* 152 */       subItems.add(new ItemStack(itemIn, 1, i)); 
/*     */   }
/*     */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\minecraft\item\ItemDye.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */