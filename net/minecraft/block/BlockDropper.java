/*    */ package net.minecraft.block;
/*    */ import net.minecraft.block.properties.IProperty;
/*    */ import net.minecraft.dispenser.BehaviorDefaultDispenseItem;
/*    */ import net.minecraft.dispenser.IBehaviorDispenseItem;
/*    */ import net.minecraft.inventory.IInventory;
/*    */ import net.minecraft.item.ItemStack;
/*    */ import net.minecraft.tileentity.TileEntity;
/*    */ import net.minecraft.tileentity.TileEntityDispenser;
/*    */ import net.minecraft.tileentity.TileEntityDropper;
/*    */ import net.minecraft.tileentity.TileEntityHopper;
/*    */ import net.minecraft.util.BlockPos;
/*    */ import net.minecraft.util.EnumFacing;
/*    */ import net.minecraft.world.World;
/*    */ 
/*    */ public class BlockDropper extends BlockDispenser {
/* 16 */   private final IBehaviorDispenseItem dropBehavior = (IBehaviorDispenseItem)new BehaviorDefaultDispenseItem();
/*    */   
/*    */   protected IBehaviorDispenseItem getBehavior(ItemStack stack) {
/* 19 */     return this.dropBehavior;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public TileEntity createNewTileEntity(World worldIn, int meta) {
/* 26 */     return (TileEntity)new TileEntityDropper();
/*    */   }
/*    */   
/*    */   protected void dispense(World worldIn, BlockPos pos) {
/* 30 */     BlockSourceImpl blocksourceimpl = new BlockSourceImpl(worldIn, pos);
/* 31 */     TileEntityDispenser tileentitydispenser = blocksourceimpl.<TileEntityDispenser>getBlockTileEntity();
/*    */     
/* 33 */     if (tileentitydispenser != null) {
/* 34 */       int i = tileentitydispenser.getDispenseSlot();
/*    */       
/* 36 */       if (i < 0) {
/* 37 */         worldIn.playAuxSFX(1001, pos, 0);
/*    */       } else {
/* 39 */         ItemStack itemstack = tileentitydispenser.getStackInSlot(i);
/*    */         
/* 41 */         if (itemstack != null) {
/* 42 */           ItemStack itemstack1; EnumFacing enumfacing = (EnumFacing)worldIn.getBlockState(pos).getValue((IProperty)FACING);
/* 43 */           BlockPos blockpos = pos.offset(enumfacing);
/* 44 */           IInventory iinventory = TileEntityHopper.getInventoryAtPosition(worldIn, blockpos.getX(), blockpos.getY(), blockpos.getZ());
/*    */ 
/*    */           
/* 47 */           if (iinventory == null) {
/* 48 */             itemstack1 = this.dropBehavior.dispense(blocksourceimpl, itemstack);
/*    */             
/* 50 */             if (itemstack1 != null && itemstack1.stackSize <= 0) {
/* 51 */               itemstack1 = null;
/*    */             }
/*    */           } else {
/* 54 */             itemstack1 = TileEntityHopper.putStackInInventoryAllSlots(iinventory, itemstack.copy().splitStack(1), enumfacing.getOpposite());
/*    */             
/* 56 */             if (itemstack1 == null) {
/* 57 */               itemstack1 = itemstack.copy();
/*    */               
/* 59 */               if (--itemstack1.stackSize <= 0) {
/* 60 */                 itemstack1 = null;
/*    */               }
/*    */             } else {
/* 63 */               itemstack1 = itemstack.copy();
/*    */             } 
/*    */           } 
/*    */           
/* 67 */           tileentitydispenser.setInventorySlotContents(i, itemstack1);
/*    */         } 
/*    */       } 
/*    */     } 
/*    */   }
/*    */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\minecraft\block\BlockDropper.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */