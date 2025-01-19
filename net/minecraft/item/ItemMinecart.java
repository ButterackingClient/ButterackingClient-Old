/*     */ package net.minecraft.item;
/*     */ import net.minecraft.block.BlockDispenser;
/*     */ import net.minecraft.block.BlockRailBase;
/*     */ import net.minecraft.block.material.Material;
/*     */ import net.minecraft.block.state.IBlockState;
/*     */ import net.minecraft.creativetab.CreativeTabs;
/*     */ import net.minecraft.dispenser.BehaviorDefaultDispenseItem;
/*     */ import net.minecraft.dispenser.IBehaviorDispenseItem;
/*     */ import net.minecraft.dispenser.IBlockSource;
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.entity.item.EntityMinecart;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.util.BlockPos;
/*     */ import net.minecraft.util.EnumFacing;
/*     */ import net.minecraft.world.World;
/*     */ 
/*     */ public class ItemMinecart extends Item {
/*  18 */   private static final IBehaviorDispenseItem dispenserMinecartBehavior = (IBehaviorDispenseItem)new BehaviorDefaultDispenseItem() {
/*  19 */       private final BehaviorDefaultDispenseItem behaviourDefaultDispenseItem = new BehaviorDefaultDispenseItem();
/*     */       public ItemStack dispenseStack(IBlockSource source, ItemStack stack) {
/*     */         double d3;
/*  22 */         EnumFacing enumfacing = BlockDispenser.getFacing(source.getBlockMetadata());
/*  23 */         World world = source.getWorld();
/*  24 */         double d0 = source.getX() + enumfacing.getFrontOffsetX() * 1.125D;
/*  25 */         double d1 = Math.floor(source.getY()) + enumfacing.getFrontOffsetY();
/*  26 */         double d2 = source.getZ() + enumfacing.getFrontOffsetZ() * 1.125D;
/*  27 */         BlockPos blockpos = source.getBlockPos().offset(enumfacing);
/*  28 */         IBlockState iblockstate = world.getBlockState(blockpos);
/*  29 */         BlockRailBase.EnumRailDirection blockrailbase$enumraildirection = (iblockstate.getBlock() instanceof BlockRailBase) ? (BlockRailBase.EnumRailDirection)iblockstate.getValue(((BlockRailBase)iblockstate.getBlock()).getShapeProperty()) : BlockRailBase.EnumRailDirection.NORTH_SOUTH;
/*     */ 
/*     */         
/*  32 */         if (BlockRailBase.isRailBlock(iblockstate)) {
/*  33 */           if (blockrailbase$enumraildirection.isAscending()) {
/*  34 */             d3 = 0.6D;
/*     */           } else {
/*  36 */             d3 = 0.1D;
/*     */           } 
/*     */         } else {
/*  39 */           if (iblockstate.getBlock().getMaterial() != Material.air || !BlockRailBase.isRailBlock(world.getBlockState(blockpos.down()))) {
/*  40 */             return this.behaviourDefaultDispenseItem.dispense(source, stack);
/*     */           }
/*     */           
/*  43 */           IBlockState iblockstate1 = world.getBlockState(blockpos.down());
/*  44 */           BlockRailBase.EnumRailDirection blockrailbase$enumraildirection1 = (iblockstate1.getBlock() instanceof BlockRailBase) ? (BlockRailBase.EnumRailDirection)iblockstate1.getValue(((BlockRailBase)iblockstate1.getBlock()).getShapeProperty()) : BlockRailBase.EnumRailDirection.NORTH_SOUTH;
/*     */           
/*  46 */           if (enumfacing != EnumFacing.DOWN && blockrailbase$enumraildirection1.isAscending()) {
/*  47 */             d3 = -0.4D;
/*     */           } else {
/*  49 */             d3 = -0.9D;
/*     */           } 
/*     */         } 
/*     */         
/*  53 */         EntityMinecart entityminecart = EntityMinecart.getMinecart(world, d0, d1 + d3, d2, ((ItemMinecart)stack.getItem()).minecartType);
/*     */         
/*  55 */         if (stack.hasDisplayName()) {
/*  56 */           entityminecart.setCustomNameTag(stack.getDisplayName());
/*     */         }
/*     */         
/*  59 */         world.spawnEntityInWorld((Entity)entityminecart);
/*  60 */         stack.splitStack(1);
/*  61 */         return stack;
/*     */       }
/*     */       
/*     */       protected void playDispenseSound(IBlockSource source) {
/*  65 */         source.getWorld().playAuxSFX(1000, source.getBlockPos(), 0);
/*     */       }
/*     */     };
/*     */   private final EntityMinecart.EnumMinecartType minecartType;
/*     */   
/*     */   public ItemMinecart(EntityMinecart.EnumMinecartType type) {
/*  71 */     this.maxStackSize = 1;
/*  72 */     this.minecartType = type;
/*  73 */     setCreativeTab(CreativeTabs.tabTransport);
/*  74 */     BlockDispenser.dispenseBehaviorRegistry.putObject(this, dispenserMinecartBehavior);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean onItemUse(ItemStack stack, EntityPlayer playerIn, World worldIn, BlockPos pos, EnumFacing side, float hitX, float hitY, float hitZ) {
/*  81 */     IBlockState iblockstate = worldIn.getBlockState(pos);
/*     */     
/*  83 */     if (BlockRailBase.isRailBlock(iblockstate)) {
/*  84 */       if (!worldIn.isRemote) {
/*  85 */         BlockRailBase.EnumRailDirection blockrailbase$enumraildirection = (iblockstate.getBlock() instanceof BlockRailBase) ? (BlockRailBase.EnumRailDirection)iblockstate.getValue(((BlockRailBase)iblockstate.getBlock()).getShapeProperty()) : BlockRailBase.EnumRailDirection.NORTH_SOUTH;
/*  86 */         double d0 = 0.0D;
/*     */         
/*  88 */         if (blockrailbase$enumraildirection.isAscending()) {
/*  89 */           d0 = 0.5D;
/*     */         }
/*     */         
/*  92 */         EntityMinecart entityminecart = EntityMinecart.getMinecart(worldIn, pos.getX() + 0.5D, pos.getY() + 0.0625D + d0, pos.getZ() + 0.5D, this.minecartType);
/*     */         
/*  94 */         if (stack.hasDisplayName()) {
/*  95 */           entityminecart.setCustomNameTag(stack.getDisplayName());
/*     */         }
/*     */         
/*  98 */         worldIn.spawnEntityInWorld((Entity)entityminecart);
/*     */       } 
/*     */       
/* 101 */       stack.stackSize--;
/* 102 */       return true;
/*     */     } 
/* 104 */     return false;
/*     */   }
/*     */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\minecraft\item\ItemMinecart.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */