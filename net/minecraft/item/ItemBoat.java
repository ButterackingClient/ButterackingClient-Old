/*    */ package net.minecraft.item;
/*    */ 
/*    */ import java.util.List;
/*    */ import net.minecraft.creativetab.CreativeTabs;
/*    */ import net.minecraft.entity.Entity;
/*    */ import net.minecraft.entity.item.EntityBoat;
/*    */ import net.minecraft.entity.player.EntityPlayer;
/*    */ import net.minecraft.init.Blocks;
/*    */ import net.minecraft.stats.StatList;
/*    */ import net.minecraft.util.AxisAlignedBB;
/*    */ import net.minecraft.util.BlockPos;
/*    */ import net.minecraft.util.MathHelper;
/*    */ import net.minecraft.util.MovingObjectPosition;
/*    */ import net.minecraft.util.Vec3;
/*    */ import net.minecraft.world.World;
/*    */ 
/*    */ public class ItemBoat
/*    */   extends Item {
/*    */   public ItemBoat() {
/* 20 */     this.maxStackSize = 1;
/* 21 */     setCreativeTab(CreativeTabs.tabTransport);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public ItemStack onItemRightClick(ItemStack itemStackIn, World worldIn, EntityPlayer playerIn) {
/* 28 */     float f = 1.0F;
/* 29 */     float f1 = playerIn.prevRotationPitch + (playerIn.rotationPitch - playerIn.prevRotationPitch) * f;
/* 30 */     float f2 = playerIn.prevRotationYaw + (playerIn.rotationYaw - playerIn.prevRotationYaw) * f;
/* 31 */     double d0 = playerIn.prevPosX + (playerIn.posX - playerIn.prevPosX) * f;
/* 32 */     double d1 = playerIn.prevPosY + (playerIn.posY - playerIn.prevPosY) * f + playerIn.getEyeHeight();
/* 33 */     double d2 = playerIn.prevPosZ + (playerIn.posZ - playerIn.prevPosZ) * f;
/* 34 */     Vec3 vec3 = new Vec3(d0, d1, d2);
/* 35 */     float f3 = MathHelper.cos(-f2 * 0.017453292F - 3.1415927F);
/* 36 */     float f4 = MathHelper.sin(-f2 * 0.017453292F - 3.1415927F);
/* 37 */     float f5 = -MathHelper.cos(-f1 * 0.017453292F);
/* 38 */     float f6 = MathHelper.sin(-f1 * 0.017453292F);
/* 39 */     float f7 = f4 * f5;
/* 40 */     float f8 = f3 * f5;
/* 41 */     double d3 = 5.0D;
/* 42 */     Vec3 vec31 = vec3.addVector(f7 * d3, f6 * d3, f8 * d3);
/* 43 */     MovingObjectPosition movingobjectposition = worldIn.rayTraceBlocks(vec3, vec31, true);
/*    */     
/* 45 */     if (movingobjectposition == null) {
/* 46 */       return itemStackIn;
/*    */     }
/* 48 */     Vec3 vec32 = playerIn.getLook(f);
/* 49 */     boolean flag = false;
/* 50 */     float f9 = 1.0F;
/* 51 */     List<Entity> list = worldIn.getEntitiesWithinAABBExcludingEntity((Entity)playerIn, playerIn.getEntityBoundingBox().addCoord(vec32.xCoord * d3, vec32.yCoord * d3, vec32.zCoord * d3).expand(f9, f9, f9));
/*    */     
/* 53 */     for (int i = 0; i < list.size(); i++) {
/* 54 */       Entity entity = list.get(i);
/*    */       
/* 56 */       if (entity.canBeCollidedWith()) {
/* 57 */         float f10 = entity.getCollisionBorderSize();
/* 58 */         AxisAlignedBB axisalignedbb = entity.getEntityBoundingBox().expand(f10, f10, f10);
/*    */         
/* 60 */         if (axisalignedbb.isVecInside(vec3)) {
/* 61 */           flag = true;
/*    */         }
/*    */       } 
/*    */     } 
/*    */     
/* 66 */     if (flag) {
/* 67 */       return itemStackIn;
/*    */     }
/* 69 */     if (movingobjectposition.typeOfHit == MovingObjectPosition.MovingObjectType.BLOCK) {
/* 70 */       BlockPos blockpos = movingobjectposition.getBlockPos();
/*    */       
/* 72 */       if (worldIn.getBlockState(blockpos).getBlock() == Blocks.snow_layer) {
/* 73 */         blockpos = blockpos.down();
/*    */       }
/*    */       
/* 76 */       EntityBoat entityboat = new EntityBoat(worldIn, (blockpos.getX() + 0.5F), (blockpos.getY() + 1.0F), (blockpos.getZ() + 0.5F));
/* 77 */       entityboat.rotationYaw = (((MathHelper.floor_double((playerIn.rotationYaw * 4.0F / 360.0F) + 0.5D) & 0x3) - 1) * 90);
/*    */       
/* 79 */       if (!worldIn.getCollidingBoundingBoxes((Entity)entityboat, entityboat.getEntityBoundingBox().expand(-0.1D, -0.1D, -0.1D)).isEmpty()) {
/* 80 */         return itemStackIn;
/*    */       }
/*    */       
/* 83 */       if (!worldIn.isRemote) {
/* 84 */         worldIn.spawnEntityInWorld((Entity)entityboat);
/*    */       }
/*    */       
/* 87 */       if (!playerIn.capabilities.isCreativeMode) {
/* 88 */         itemStackIn.stackSize--;
/*    */       }
/*    */       
/* 91 */       playerIn.triggerAchievement(StatList.objectUseStats[Item.getIdFromItem(this)]);
/*    */     } 
/*    */     
/* 94 */     return itemStackIn;
/*    */   }
/*    */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\minecraft\item\ItemBoat.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */