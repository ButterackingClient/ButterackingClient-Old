/*    */ package net.minecraft.item;
/*    */ 
/*    */ import net.minecraft.entity.player.EntityPlayer;
/*    */ import net.minecraft.nbt.NBTTagCompound;
/*    */ import net.minecraft.nbt.NBTTagList;
/*    */ import net.minecraft.stats.StatList;
/*    */ import net.minecraft.world.World;
/*    */ 
/*    */ public class ItemWritableBook extends Item {
/*    */   public ItemWritableBook() {
/* 11 */     setMaxStackSize(1);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public ItemStack onItemRightClick(ItemStack itemStackIn, World worldIn, EntityPlayer playerIn) {
/* 18 */     playerIn.displayGUIBook(itemStackIn);
/* 19 */     playerIn.triggerAchievement(StatList.objectUseStats[Item.getIdFromItem(this)]);
/* 20 */     return itemStackIn;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public static boolean isNBTValid(NBTTagCompound nbt) {
/* 27 */     if (nbt == null)
/* 28 */       return false; 
/* 29 */     if (!nbt.hasKey("pages", 9)) {
/* 30 */       return false;
/*    */     }
/* 32 */     NBTTagList nbttaglist = nbt.getTagList("pages", 8);
/*    */     
/* 34 */     for (int i = 0; i < nbttaglist.tagCount(); i++) {
/* 35 */       String s = nbttaglist.getStringTagAt(i);
/*    */       
/* 37 */       if (s == null) {
/* 38 */         return false;
/*    */       }
/*    */       
/* 41 */       if (s.length() > 32767) {
/* 42 */         return false;
/*    */       }
/*    */     } 
/*    */     
/* 46 */     return true;
/*    */   }
/*    */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\minecraft\item\ItemWritableBook.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */