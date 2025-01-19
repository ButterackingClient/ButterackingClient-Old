/*    */ package net.minecraft.inventory;
/*    */ 
/*    */ import java.util.Random;
/*    */ import net.minecraft.entity.Entity;
/*    */ import net.minecraft.entity.item.EntityItem;
/*    */ import net.minecraft.item.ItemStack;
/*    */ import net.minecraft.nbt.NBTTagCompound;
/*    */ import net.minecraft.util.BlockPos;
/*    */ import net.minecraft.world.World;
/*    */ 
/*    */ public class InventoryHelper
/*    */ {
/* 13 */   private static final Random RANDOM = new Random();
/*    */   
/*    */   public static void dropInventoryItems(World worldIn, BlockPos pos, IInventory inventory) {
/* 16 */     dropInventoryItems(worldIn, pos.getX(), pos.getY(), pos.getZ(), inventory);
/*    */   }
/*    */   
/*    */   public static void dropInventoryItems(World worldIn, Entity entityAt, IInventory inventory) {
/* 20 */     dropInventoryItems(worldIn, entityAt.posX, entityAt.posY, entityAt.posZ, inventory);
/*    */   }
/*    */   
/*    */   private static void dropInventoryItems(World worldIn, double x, double y, double z, IInventory inventory) {
/* 24 */     for (int i = 0; i < inventory.getSizeInventory(); i++) {
/* 25 */       ItemStack itemstack = inventory.getStackInSlot(i);
/*    */       
/* 27 */       if (itemstack != null) {
/* 28 */         spawnItemStack(worldIn, x, y, z, itemstack);
/*    */       }
/*    */     } 
/*    */   }
/*    */   
/*    */   private static void spawnItemStack(World worldIn, double x, double y, double z, ItemStack stack) {
/* 34 */     float f = RANDOM.nextFloat() * 0.8F + 0.1F;
/* 35 */     float f1 = RANDOM.nextFloat() * 0.8F + 0.1F;
/* 36 */     float f2 = RANDOM.nextFloat() * 0.8F + 0.1F;
/*    */     
/* 38 */     while (stack.stackSize > 0) {
/* 39 */       int i = RANDOM.nextInt(21) + 10;
/*    */       
/* 41 */       if (i > stack.stackSize) {
/* 42 */         i = stack.stackSize;
/*    */       }
/*    */       
/* 45 */       stack.stackSize -= i;
/* 46 */       EntityItem entityitem = new EntityItem(worldIn, x + f, y + f1, z + f2, new ItemStack(stack.getItem(), i, stack.getMetadata()));
/*    */       
/* 48 */       if (stack.hasTagCompound()) {
/* 49 */         entityitem.getEntityItem().setTagCompound((NBTTagCompound)stack.getTagCompound().copy());
/*    */       }
/*    */       
/* 52 */       float f3 = 0.05F;
/* 53 */       entityitem.motionX = RANDOM.nextGaussian() * f3;
/* 54 */       entityitem.motionY = RANDOM.nextGaussian() * f3 + 0.20000000298023224D;
/* 55 */       entityitem.motionZ = RANDOM.nextGaussian() * f3;
/* 56 */       worldIn.spawnEntityInWorld((Entity)entityitem);
/*    */     } 
/*    */   }
/*    */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\minecraft\inventory\InventoryHelper.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */