/*     */ package net.minecraft.item;
/*     */ 
/*     */ import java.util.List;
/*     */ import net.minecraft.block.Block;
/*     */ import net.minecraft.block.state.IBlockState;
/*     */ import net.minecraft.creativetab.CreativeTabs;
/*     */ import net.minecraft.entity.EntityLivingBase;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.init.Blocks;
/*     */ import net.minecraft.nbt.NBTTagCompound;
/*     */ import net.minecraft.server.MinecraftServer;
/*     */ import net.minecraft.tileentity.TileEntity;
/*     */ import net.minecraft.util.BlockPos;
/*     */ import net.minecraft.util.EnumFacing;
/*     */ import net.minecraft.world.World;
/*     */ 
/*     */ public class ItemBlock
/*     */   extends Item {
/*     */   protected final Block block;
/*     */   
/*     */   public ItemBlock(Block block) {
/*  22 */     this.block = block;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ItemBlock setUnlocalizedName(String unlocalizedName) {
/*  29 */     super.setUnlocalizedName(unlocalizedName);
/*  30 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean onItemUse(ItemStack stack, EntityPlayer playerIn, World worldIn, BlockPos pos, EnumFacing side, float hitX, float hitY, float hitZ) {
/*  37 */     IBlockState iblockstate = worldIn.getBlockState(pos);
/*  38 */     Block block = iblockstate.getBlock();
/*     */     
/*  40 */     if (!block.isReplaceable(worldIn, pos)) {
/*  41 */       pos = pos.offset(side);
/*     */     }
/*     */     
/*  44 */     if (stack.stackSize == 0)
/*  45 */       return false; 
/*  46 */     if (!playerIn.canPlayerEdit(pos, side, stack))
/*  47 */       return false; 
/*  48 */     if (worldIn.canBlockBePlaced(this.block, pos, false, side, null, stack)) {
/*  49 */       int i = getMetadata(stack.getMetadata());
/*  50 */       IBlockState iblockstate1 = this.block.onBlockPlaced(worldIn, pos, side, hitX, hitY, hitZ, i, (EntityLivingBase)playerIn);
/*     */       
/*  52 */       if (worldIn.setBlockState(pos, iblockstate1, 3)) {
/*  53 */         iblockstate1 = worldIn.getBlockState(pos);
/*     */         
/*  55 */         if (iblockstate1.getBlock() == this.block) {
/*  56 */           setTileEntityNBT(worldIn, playerIn, pos, stack);
/*  57 */           this.block.onBlockPlacedBy(worldIn, pos, iblockstate1, (EntityLivingBase)playerIn, stack);
/*     */         } 
/*     */         
/*  60 */         worldIn.playSoundEffect((pos.getX() + 0.5F), (pos.getY() + 0.5F), (pos.getZ() + 0.5F), this.block.stepSound.getPlaceSound(), (this.block.stepSound.getVolume() + 1.0F) / 2.0F, this.block.stepSound.getFrequency() * 0.8F);
/*  61 */         stack.stackSize--;
/*     */       } 
/*     */       
/*  64 */       return true;
/*     */     } 
/*  66 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public static boolean setTileEntityNBT(World worldIn, EntityPlayer pos, BlockPos stack, ItemStack p_179224_3_) {
/*  71 */     MinecraftServer minecraftserver = MinecraftServer.getServer();
/*     */     
/*  73 */     if (minecraftserver == null) {
/*  74 */       return false;
/*     */     }
/*  76 */     if (p_179224_3_.hasTagCompound() && p_179224_3_.getTagCompound().hasKey("BlockEntityTag", 10)) {
/*  77 */       TileEntity tileentity = worldIn.getTileEntity(stack);
/*     */       
/*  79 */       if (tileentity != null) {
/*  80 */         if (!worldIn.isRemote && tileentity.func_183000_F() && !minecraftserver.getConfigurationManager().canSendCommands(pos.getGameProfile())) {
/*  81 */           return false;
/*     */         }
/*     */         
/*  84 */         NBTTagCompound nbttagcompound = new NBTTagCompound();
/*  85 */         NBTTagCompound nbttagcompound1 = (NBTTagCompound)nbttagcompound.copy();
/*  86 */         tileentity.writeToNBT(nbttagcompound);
/*  87 */         NBTTagCompound nbttagcompound2 = (NBTTagCompound)p_179224_3_.getTagCompound().getTag("BlockEntityTag");
/*  88 */         nbttagcompound.merge(nbttagcompound2);
/*  89 */         nbttagcompound.setInteger("x", stack.getX());
/*  90 */         nbttagcompound.setInteger("y", stack.getY());
/*  91 */         nbttagcompound.setInteger("z", stack.getZ());
/*     */         
/*  93 */         if (!nbttagcompound.equals(nbttagcompound1)) {
/*  94 */           tileentity.readFromNBT(nbttagcompound);
/*  95 */           tileentity.markDirty();
/*  96 */           return true;
/*     */         } 
/*     */       } 
/*     */     } 
/*     */     
/* 101 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean canPlaceBlockOnSide(World worldIn, BlockPos pos, EnumFacing side, EntityPlayer player, ItemStack stack) {
/* 106 */     Block block = worldIn.getBlockState(pos).getBlock();
/*     */     
/* 108 */     if (block == Blocks.snow_layer) {
/* 109 */       side = EnumFacing.UP;
/* 110 */     } else if (!block.isReplaceable(worldIn, pos)) {
/* 111 */       pos = pos.offset(side);
/*     */     } 
/*     */     
/* 114 */     return worldIn.canBlockBePlaced(this.block, pos, false, side, null, stack);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getUnlocalizedName(ItemStack stack) {
/* 122 */     return this.block.getUnlocalizedName();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getUnlocalizedName() {
/* 129 */     return this.block.getUnlocalizedName();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public CreativeTabs getCreativeTab() {
/* 136 */     return this.block.getCreativeTabToDisplayOn();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void getSubItems(Item itemIn, CreativeTabs tab, List<ItemStack> subItems) {
/* 143 */     this.block.getSubBlocks(itemIn, tab, subItems);
/*     */   }
/*     */   
/*     */   public Block getBlock() {
/* 147 */     return this.block;
/*     */   }
/*     */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\minecraft\item\ItemBlock.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */