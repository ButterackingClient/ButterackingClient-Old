/*    */ package net.minecraft.item;
/*    */ import net.minecraft.creativetab.CreativeTabs;
/*    */ import net.minecraft.entity.Entity;
/*    */ import net.minecraft.entity.EntityLivingBase;
/*    */ import net.minecraft.entity.passive.EntityPig;
/*    */ import net.minecraft.entity.player.EntityPlayer;
/*    */ 
/*    */ public class ItemSaddle extends Item {
/*    */   public ItemSaddle() {
/* 10 */     this.maxStackSize = 1;
/* 11 */     setCreativeTab(CreativeTabs.tabTransport);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public boolean itemInteractionForEntity(ItemStack stack, EntityPlayer playerIn, EntityLivingBase target) {
/* 18 */     if (target instanceof EntityPig) {
/* 19 */       EntityPig entitypig = (EntityPig)target;
/*    */       
/* 21 */       if (!entitypig.getSaddled() && !entitypig.isChild()) {
/* 22 */         entitypig.setSaddled(true);
/* 23 */         entitypig.worldObj.playSoundAtEntity((Entity)entitypig, "mob.horse.leather", 0.5F, 1.0F);
/* 24 */         stack.stackSize--;
/*    */       } 
/*    */       
/* 27 */       return true;
/*    */     } 
/* 29 */     return false;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public boolean hitEntity(ItemStack stack, EntityLivingBase target, EntityLivingBase attacker) {
/* 38 */     itemInteractionForEntity(stack, (EntityPlayer)null, target);
/* 39 */     return true;
/*    */   }
/*    */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\minecraft\item\ItemSaddle.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */