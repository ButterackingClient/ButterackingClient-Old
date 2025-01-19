/*    */ package net.minecraft.item;
/*    */ 
/*    */ import java.util.List;
/*    */ import net.minecraft.creativetab.CreativeTabs;
/*    */ 
/*    */ public class ItemCoal
/*    */   extends Item {
/*    */   public ItemCoal() {
/*  9 */     setHasSubtypes(true);
/* 10 */     setMaxDamage(0);
/* 11 */     setCreativeTab(CreativeTabs.tabMaterials);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public String getUnlocalizedName(ItemStack stack) {
/* 19 */     return (stack.getMetadata() == 1) ? "item.charcoal" : "item.coal";
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void getSubItems(Item itemIn, CreativeTabs tab, List<ItemStack> subItems) {
/* 26 */     subItems.add(new ItemStack(itemIn, 1, 0));
/* 27 */     subItems.add(new ItemStack(itemIn, 1, 1));
/*    */   }
/*    */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\minecraft\item\ItemCoal.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */