/*    */ package net.minecraft.item;
/*    */ 
/*    */ import net.minecraft.creativetab.CreativeTabs;
/*    */ import net.minecraft.entity.EntityLiving;
/*    */ import net.minecraft.entity.EntityLivingBase;
/*    */ import net.minecraft.entity.player.EntityPlayer;
/*    */ 
/*    */ public class ItemNameTag extends Item {
/*    */   public ItemNameTag() {
/* 10 */     setCreativeTab(CreativeTabs.tabTools);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public boolean itemInteractionForEntity(ItemStack stack, EntityPlayer playerIn, EntityLivingBase target) {
/* 17 */     if (!stack.hasDisplayName())
/* 18 */       return false; 
/* 19 */     if (target instanceof EntityLiving) {
/* 20 */       EntityLiving entityliving = (EntityLiving)target;
/* 21 */       entityliving.setCustomNameTag(stack.getDisplayName());
/* 22 */       entityliving.enablePersistence();
/* 23 */       stack.stackSize--;
/* 24 */       return true;
/*    */     } 
/* 26 */     return super.itemInteractionForEntity(stack, playerIn, target);
/*    */   }
/*    */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\minecraft\item\ItemNameTag.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */