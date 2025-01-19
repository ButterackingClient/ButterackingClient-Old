/*     */ package net.minecraft.item;
/*     */ 
/*     */ import net.minecraft.block.Block;
/*     */ import net.minecraft.block.BlockSlab;
/*     */ import net.minecraft.block.properties.IProperty;
/*     */ import net.minecraft.block.state.IBlockState;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.util.BlockPos;
/*     */ import net.minecraft.util.EnumFacing;
/*     */ import net.minecraft.world.World;
/*     */ 
/*     */ public class ItemSlab extends ItemBlock {
/*     */   private final BlockSlab singleSlab;
/*     */   private final BlockSlab doubleSlab;
/*     */   
/*     */   public ItemSlab(Block block, BlockSlab singleSlab, BlockSlab doubleSlab) {
/*  17 */     super(block);
/*  18 */     this.singleSlab = singleSlab;
/*  19 */     this.doubleSlab = doubleSlab;
/*  20 */     setMaxDamage(0);
/*  21 */     setHasSubtypes(true);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getMetadata(int damage) {
/*  29 */     return damage;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getUnlocalizedName(ItemStack stack) {
/*  37 */     return this.singleSlab.getUnlocalizedName(stack.getMetadata());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean onItemUse(ItemStack stack, EntityPlayer playerIn, World worldIn, BlockPos pos, EnumFacing side, float hitX, float hitY, float hitZ) {
/*  44 */     if (stack.stackSize == 0)
/*  45 */       return false; 
/*  46 */     if (!playerIn.canPlayerEdit(pos.offset(side), side, stack)) {
/*  47 */       return false;
/*     */     }
/*  49 */     Object object = this.singleSlab.getVariant(stack);
/*  50 */     IBlockState iblockstate = worldIn.getBlockState(pos);
/*     */     
/*  52 */     if (iblockstate.getBlock() == this.singleSlab) {
/*  53 */       IProperty iproperty = this.singleSlab.getVariantProperty();
/*  54 */       Comparable comparable = iblockstate.getValue(iproperty);
/*  55 */       BlockSlab.EnumBlockHalf blockslab$enumblockhalf = (BlockSlab.EnumBlockHalf)iblockstate.getValue((IProperty)BlockSlab.HALF);
/*     */       
/*  57 */       if (((side == EnumFacing.UP && blockslab$enumblockhalf == BlockSlab.EnumBlockHalf.BOTTOM) || (side == EnumFacing.DOWN && blockslab$enumblockhalf == BlockSlab.EnumBlockHalf.TOP)) && comparable == object) {
/*  58 */         IBlockState iblockstate1 = this.doubleSlab.getDefaultState().withProperty(iproperty, comparable);
/*     */         
/*  60 */         if (worldIn.checkNoEntityCollision(this.doubleSlab.getCollisionBoundingBox(worldIn, pos, iblockstate1)) && worldIn.setBlockState(pos, iblockstate1, 3)) {
/*  61 */           worldIn.playSoundEffect((pos.getX() + 0.5F), (pos.getY() + 0.5F), (pos.getZ() + 0.5F), this.doubleSlab.stepSound.getPlaceSound(), (this.doubleSlab.stepSound.getVolume() + 1.0F) / 2.0F, this.doubleSlab.stepSound.getFrequency() * 0.8F);
/*  62 */           stack.stackSize--;
/*     */         } 
/*     */         
/*  65 */         return true;
/*     */       } 
/*     */     } 
/*     */     
/*  69 */     return tryPlace(stack, worldIn, pos.offset(side), object) ? true : super.onItemUse(stack, playerIn, worldIn, pos, side, hitX, hitY, hitZ);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean canPlaceBlockOnSide(World worldIn, BlockPos pos, EnumFacing side, EntityPlayer player, ItemStack stack) {
/*  74 */     BlockPos blockpos = pos;
/*  75 */     IProperty iproperty = this.singleSlab.getVariantProperty();
/*  76 */     Object object = this.singleSlab.getVariant(stack);
/*  77 */     IBlockState iblockstate = worldIn.getBlockState(pos);
/*     */     
/*  79 */     if (iblockstate.getBlock() == this.singleSlab) {
/*  80 */       boolean flag = (iblockstate.getValue((IProperty)BlockSlab.HALF) == BlockSlab.EnumBlockHalf.TOP);
/*     */       
/*  82 */       if (((side == EnumFacing.UP && !flag) || (side == EnumFacing.DOWN && flag)) && object == iblockstate.getValue(iproperty)) {
/*  83 */         return true;
/*     */       }
/*     */     } 
/*     */     
/*  87 */     pos = pos.offset(side);
/*  88 */     IBlockState iblockstate1 = worldIn.getBlockState(pos);
/*  89 */     return (iblockstate1.getBlock() == this.singleSlab && object == iblockstate1.getValue(iproperty)) ? true : super.canPlaceBlockOnSide(worldIn, blockpos, side, player, stack);
/*     */   }
/*     */   
/*     */   private boolean tryPlace(ItemStack stack, World worldIn, BlockPos pos, Object variantInStack) {
/*  93 */     IBlockState iblockstate = worldIn.getBlockState(pos);
/*     */     
/*  95 */     if (iblockstate.getBlock() == this.singleSlab) {
/*  96 */       Comparable comparable = iblockstate.getValue(this.singleSlab.getVariantProperty());
/*     */       
/*  98 */       if (comparable == variantInStack) {
/*  99 */         IBlockState iblockstate1 = this.doubleSlab.getDefaultState().withProperty(this.singleSlab.getVariantProperty(), comparable);
/*     */         
/* 101 */         if (worldIn.checkNoEntityCollision(this.doubleSlab.getCollisionBoundingBox(worldIn, pos, iblockstate1)) && worldIn.setBlockState(pos, iblockstate1, 3)) {
/* 102 */           worldIn.playSoundEffect((pos.getX() + 0.5F), (pos.getY() + 0.5F), (pos.getZ() + 0.5F), this.doubleSlab.stepSound.getPlaceSound(), (this.doubleSlab.stepSound.getVolume() + 1.0F) / 2.0F, this.doubleSlab.stepSound.getFrequency() * 0.8F);
/* 103 */           stack.stackSize--;
/*     */         } 
/*     */         
/* 106 */         return true;
/*     */       } 
/*     */     } 
/*     */     
/* 110 */     return false;
/*     */   }
/*     */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\minecraft\item\ItemSlab.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */