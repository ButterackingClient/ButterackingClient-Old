/*    */ package net.minecraft.entity;
/*    */ 
/*    */ import net.minecraft.entity.player.EntityPlayer;
/*    */ import net.minecraft.inventory.InventoryMerchant;
/*    */ import net.minecraft.item.ItemStack;
/*    */ import net.minecraft.util.ChatComponentTranslation;
/*    */ import net.minecraft.util.IChatComponent;
/*    */ import net.minecraft.village.MerchantRecipe;
/*    */ import net.minecraft.village.MerchantRecipeList;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class NpcMerchant
/*    */   implements IMerchant
/*    */ {
/*    */   private InventoryMerchant theMerchantInventory;
/*    */   private EntityPlayer customer;
/*    */   private MerchantRecipeList recipeList;
/*    */   private IChatComponent field_175548_d;
/*    */   
/*    */   public NpcMerchant(EntityPlayer p_i45817_1_, IChatComponent p_i45817_2_) {
/* 29 */     this.customer = p_i45817_1_;
/* 30 */     this.field_175548_d = p_i45817_2_;
/* 31 */     this.theMerchantInventory = new InventoryMerchant(p_i45817_1_, this);
/*    */   }
/*    */   
/*    */   public EntityPlayer getCustomer() {
/* 35 */     return this.customer;
/*    */   }
/*    */ 
/*    */   
/*    */   public void setCustomer(EntityPlayer p_70932_1_) {}
/*    */   
/*    */   public MerchantRecipeList getRecipes(EntityPlayer p_70934_1_) {
/* 42 */     return this.recipeList;
/*    */   }
/*    */   
/*    */   public void setRecipes(MerchantRecipeList recipeList) {
/* 46 */     this.recipeList = recipeList;
/*    */   }
/*    */   
/*    */   public void useRecipe(MerchantRecipe recipe) {
/* 50 */     recipe.incrementToolUses();
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void verifySellingItem(ItemStack stack) {}
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public IChatComponent getDisplayName() {
/* 64 */     return (this.field_175548_d != null) ? this.field_175548_d : (IChatComponent)new ChatComponentTranslation("entity.Villager.name", new Object[0]);
/*    */   }
/*    */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\minecraft\entity\NpcMerchant.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */