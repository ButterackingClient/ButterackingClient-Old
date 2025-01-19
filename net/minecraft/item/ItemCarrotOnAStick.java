/*    */ package net.minecraft.item;
/*    */ import net.minecraft.creativetab.CreativeTabs;
/*    */ import net.minecraft.entity.EntityLivingBase;
/*    */ import net.minecraft.entity.passive.EntityPig;
/*    */ import net.minecraft.entity.player.EntityPlayer;
/*    */ import net.minecraft.init.Items;
/*    */ import net.minecraft.stats.StatList;
/*    */ import net.minecraft.world.World;
/*    */ 
/*    */ public class ItemCarrotOnAStick extends Item {
/*    */   public ItemCarrotOnAStick() {
/* 12 */     setCreativeTab(CreativeTabs.tabTransport);
/* 13 */     setMaxStackSize(1);
/* 14 */     setMaxDamage(25);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public boolean isFull3D() {
/* 21 */     return true;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public boolean shouldRotateAroundWhenRendering() {
/* 29 */     return true;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public ItemStack onItemRightClick(ItemStack itemStackIn, World worldIn, EntityPlayer playerIn) {
/* 36 */     if (playerIn.isRiding() && playerIn.ridingEntity instanceof EntityPig) {
/* 37 */       EntityPig entitypig = (EntityPig)playerIn.ridingEntity;
/*    */       
/* 39 */       if (entitypig.getAIControlledByPlayer().isControlledByPlayer() && itemStackIn.getMaxDamage() - itemStackIn.getMetadata() >= 7) {
/* 40 */         entitypig.getAIControlledByPlayer().boostSpeed();
/* 41 */         itemStackIn.damageItem(7, (EntityLivingBase)playerIn);
/*    */         
/* 43 */         if (itemStackIn.stackSize == 0) {
/* 44 */           ItemStack itemstack = new ItemStack(Items.fishing_rod);
/* 45 */           itemstack.setTagCompound(itemStackIn.getTagCompound());
/* 46 */           return itemstack;
/*    */         } 
/*    */       } 
/*    */     } 
/*    */     
/* 51 */     playerIn.triggerAchievement(StatList.objectUseStats[Item.getIdFromItem(this)]);
/* 52 */     return itemStackIn;
/*    */   }
/*    */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\minecraft\item\ItemCarrotOnAStick.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */