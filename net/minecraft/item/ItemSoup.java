/*    */ package net.minecraft.item;
/*    */ 
/*    */ import net.minecraft.entity.player.EntityPlayer;
/*    */ import net.minecraft.init.Items;
/*    */ import net.minecraft.world.World;
/*    */ 
/*    */ public class ItemSoup extends ItemFood {
/*    */   public ItemSoup(int healAmount) {
/*  9 */     super(healAmount, false);
/* 10 */     setMaxStackSize(1);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public ItemStack onItemUseFinish(ItemStack stack, World worldIn, EntityPlayer playerIn) {
/* 18 */     super.onItemUseFinish(stack, worldIn, playerIn);
/* 19 */     return new ItemStack(Items.bowl);
/*    */   }
/*    */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\minecraft\item\ItemSoup.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */