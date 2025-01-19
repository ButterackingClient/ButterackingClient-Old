/*    */ package net.minecraft.dispenser;
/*    */ 
/*    */ import net.minecraft.block.BlockDispenser;
/*    */ import net.minecraft.entity.Entity;
/*    */ import net.minecraft.entity.item.EntityItem;
/*    */ import net.minecraft.item.ItemStack;
/*    */ import net.minecraft.util.EnumFacing;
/*    */ import net.minecraft.world.World;
/*    */ 
/*    */ public class BehaviorDefaultDispenseItem
/*    */   implements IBehaviorDispenseItem
/*    */ {
/*    */   public final ItemStack dispense(IBlockSource source, ItemStack stack) {
/* 14 */     ItemStack itemstack = dispenseStack(source, stack);
/* 15 */     playDispenseSound(source);
/* 16 */     spawnDispenseParticles(source, BlockDispenser.getFacing(source.getBlockMetadata()));
/* 17 */     return itemstack;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   protected ItemStack dispenseStack(IBlockSource source, ItemStack stack) {
/* 24 */     EnumFacing enumfacing = BlockDispenser.getFacing(source.getBlockMetadata());
/* 25 */     IPosition iposition = BlockDispenser.getDispensePosition(source);
/* 26 */     ItemStack itemstack = stack.splitStack(1);
/* 27 */     doDispense(source.getWorld(), itemstack, 6, enumfacing, iposition);
/* 28 */     return stack;
/*    */   }
/*    */   
/*    */   public static void doDispense(World worldIn, ItemStack stack, int speed, EnumFacing facing, IPosition position) {
/* 32 */     double d0 = position.getX();
/* 33 */     double d1 = position.getY();
/* 34 */     double d2 = position.getZ();
/*    */     
/* 36 */     if (facing.getAxis() == EnumFacing.Axis.Y) {
/* 37 */       d1 -= 0.125D;
/*    */     } else {
/* 39 */       d1 -= 0.15625D;
/*    */     } 
/*    */     
/* 42 */     EntityItem entityitem = new EntityItem(worldIn, d0, d1, d2, stack);
/* 43 */     double d3 = worldIn.rand.nextDouble() * 0.1D + 0.2D;
/* 44 */     entityitem.motionX = facing.getFrontOffsetX() * d3;
/* 45 */     entityitem.motionY = 0.20000000298023224D;
/* 46 */     entityitem.motionZ = facing.getFrontOffsetZ() * d3;
/* 47 */     entityitem.motionX += worldIn.rand.nextGaussian() * 0.007499999832361937D * speed;
/* 48 */     entityitem.motionY += worldIn.rand.nextGaussian() * 0.007499999832361937D * speed;
/* 49 */     entityitem.motionZ += worldIn.rand.nextGaussian() * 0.007499999832361937D * speed;
/* 50 */     worldIn.spawnEntityInWorld((Entity)entityitem);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   protected void playDispenseSound(IBlockSource source) {
/* 57 */     source.getWorld().playAuxSFX(1000, source.getBlockPos(), 0);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   protected void spawnDispenseParticles(IBlockSource source, EnumFacing facingIn) {
/* 64 */     source.getWorld().playAuxSFX(2000, source.getBlockPos(), func_82488_a(facingIn));
/*    */   }
/*    */   
/*    */   private int func_82488_a(EnumFacing facingIn) {
/* 68 */     return facingIn.getFrontOffsetX() + 1 + (facingIn.getFrontOffsetZ() + 1) * 3;
/*    */   }
/*    */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\minecraft\dispenser\BehaviorDefaultDispenseItem.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */