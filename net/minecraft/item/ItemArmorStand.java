/*    */ package net.minecraft.item;
/*    */ 
/*    */ import java.util.List;
/*    */ import java.util.Random;
/*    */ import net.minecraft.creativetab.CreativeTabs;
/*    */ import net.minecraft.entity.Entity;
/*    */ import net.minecraft.entity.item.EntityArmorStand;
/*    */ import net.minecraft.entity.player.EntityPlayer;
/*    */ import net.minecraft.nbt.NBTTagCompound;
/*    */ import net.minecraft.util.AxisAlignedBB;
/*    */ import net.minecraft.util.BlockPos;
/*    */ import net.minecraft.util.EnumFacing;
/*    */ import net.minecraft.util.MathHelper;
/*    */ import net.minecraft.util.Rotations;
/*    */ import net.minecraft.world.World;
/*    */ 
/*    */ public class ItemArmorStand
/*    */   extends Item {
/*    */   public ItemArmorStand() {
/* 20 */     setCreativeTab(CreativeTabs.tabDecorations);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public boolean onItemUse(ItemStack stack, EntityPlayer playerIn, World worldIn, BlockPos pos, EnumFacing side, float hitX, float hitY, float hitZ) {
/* 27 */     if (side == EnumFacing.DOWN) {
/* 28 */       return false;
/*    */     }
/* 30 */     boolean flag = worldIn.getBlockState(pos).getBlock().isReplaceable(worldIn, pos);
/* 31 */     BlockPos blockpos = flag ? pos : pos.offset(side);
/*    */     
/* 33 */     if (!playerIn.canPlayerEdit(blockpos, side, stack)) {
/* 34 */       return false;
/*    */     }
/* 36 */     BlockPos blockpos1 = blockpos.up();
/* 37 */     boolean flag1 = (!worldIn.isAirBlock(blockpos) && !worldIn.getBlockState(blockpos).getBlock().isReplaceable(worldIn, blockpos));
/* 38 */     int i = flag1 | ((!worldIn.isAirBlock(blockpos1) && !worldIn.getBlockState(blockpos1).getBlock().isReplaceable(worldIn, blockpos1)) ? 1 : 0);
/*    */     
/* 40 */     if (i != 0) {
/* 41 */       return false;
/*    */     }
/* 43 */     double d0 = blockpos.getX();
/* 44 */     double d1 = blockpos.getY();
/* 45 */     double d2 = blockpos.getZ();
/* 46 */     List<Entity> list = worldIn.getEntitiesWithinAABBExcludingEntity(null, AxisAlignedBB.fromBounds(d0, d1, d2, d0 + 1.0D, d1 + 2.0D, d2 + 1.0D));
/*    */     
/* 48 */     if (list.size() > 0) {
/* 49 */       return false;
/*    */     }
/* 51 */     if (!worldIn.isRemote) {
/* 52 */       worldIn.setBlockToAir(blockpos);
/* 53 */       worldIn.setBlockToAir(blockpos1);
/* 54 */       EntityArmorStand entityarmorstand = new EntityArmorStand(worldIn, d0 + 0.5D, d1, d2 + 0.5D);
/* 55 */       float f = MathHelper.floor_float((MathHelper.wrapAngleTo180_float(playerIn.rotationYaw - 180.0F) + 22.5F) / 45.0F) * 45.0F;
/* 56 */       entityarmorstand.setLocationAndAngles(d0 + 0.5D, d1, d2 + 0.5D, f, 0.0F);
/* 57 */       applyRandomRotations(entityarmorstand, worldIn.rand);
/* 58 */       NBTTagCompound nbttagcompound = stack.getTagCompound();
/*    */       
/* 60 */       if (nbttagcompound != null && nbttagcompound.hasKey("EntityTag", 10)) {
/* 61 */         NBTTagCompound nbttagcompound1 = new NBTTagCompound();
/* 62 */         entityarmorstand.writeToNBTOptional(nbttagcompound1);
/* 63 */         nbttagcompound1.merge(nbttagcompound.getCompoundTag("EntityTag"));
/* 64 */         entityarmorstand.readFromNBT(nbttagcompound1);
/*    */       } 
/*    */       
/* 67 */       worldIn.spawnEntityInWorld((Entity)entityarmorstand);
/*    */     } 
/*    */     
/* 70 */     stack.stackSize--;
/* 71 */     return true;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   private void applyRandomRotations(EntityArmorStand armorStand, Random rand) {
/* 79 */     Rotations rotations = armorStand.getHeadRotation();
/* 80 */     float f = rand.nextFloat() * 5.0F;
/* 81 */     float f1 = rand.nextFloat() * 20.0F - 10.0F;
/* 82 */     Rotations rotations1 = new Rotations(rotations.getX() + f, rotations.getY() + f1, rotations.getZ());
/* 83 */     armorStand.setHeadRotation(rotations1);
/* 84 */     rotations = armorStand.getBodyRotation();
/* 85 */     f = rand.nextFloat() * 10.0F - 5.0F;
/* 86 */     rotations1 = new Rotations(rotations.getX(), rotations.getY() + f, rotations.getZ());
/* 87 */     armorStand.setBodyRotation(rotations1);
/*    */   }
/*    */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\minecraft\item\ItemArmorStand.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */