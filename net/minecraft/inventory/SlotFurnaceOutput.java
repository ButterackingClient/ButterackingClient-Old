/*    */ package net.minecraft.inventory;
/*    */ 
/*    */ import net.minecraft.entity.Entity;
/*    */ import net.minecraft.entity.item.EntityXPOrb;
/*    */ import net.minecraft.entity.player.EntityPlayer;
/*    */ import net.minecraft.init.Items;
/*    */ import net.minecraft.item.ItemStack;
/*    */ import net.minecraft.item.crafting.FurnaceRecipes;
/*    */ import net.minecraft.stats.AchievementList;
/*    */ import net.minecraft.stats.StatBase;
/*    */ import net.minecraft.util.MathHelper;
/*    */ 
/*    */ public class SlotFurnaceOutput
/*    */   extends Slot {
/*    */   private EntityPlayer thePlayer;
/*    */   private int field_75228_b;
/*    */   
/*    */   public SlotFurnaceOutput(EntityPlayer player, IInventory inventoryIn, int slotIndex, int xPosition, int yPosition) {
/* 19 */     super(inventoryIn, slotIndex, xPosition, yPosition);
/* 20 */     this.thePlayer = player;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public boolean isItemValid(ItemStack stack) {
/* 27 */     return false;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public ItemStack decrStackSize(int amount) {
/* 35 */     if (getHasStack()) {
/* 36 */       this.field_75228_b += Math.min(amount, (getStack()).stackSize);
/*    */     }
/*    */     
/* 39 */     return super.decrStackSize(amount);
/*    */   }
/*    */   
/*    */   public void onPickupFromSlot(EntityPlayer playerIn, ItemStack stack) {
/* 43 */     onCrafting(stack);
/* 44 */     super.onPickupFromSlot(playerIn, stack);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   protected void onCrafting(ItemStack stack, int amount) {
/* 52 */     this.field_75228_b += amount;
/* 53 */     onCrafting(stack);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   protected void onCrafting(ItemStack stack) {
/* 60 */     stack.onCrafting(this.thePlayer.worldObj, this.thePlayer, this.field_75228_b);
/*    */     
/* 62 */     if (!this.thePlayer.worldObj.isRemote) {
/* 63 */       int i = this.field_75228_b;
/* 64 */       float f = FurnaceRecipes.instance().getSmeltingExperience(stack);
/*    */       
/* 66 */       if (f == 0.0F) {
/* 67 */         i = 0;
/* 68 */       } else if (f < 1.0F) {
/* 69 */         int j = MathHelper.floor_float(i * f);
/*    */         
/* 71 */         if (j < MathHelper.ceiling_float_int(i * f) && Math.random() < (i * f - j)) {
/* 72 */           j++;
/*    */         }
/*    */         
/* 75 */         i = j;
/*    */       } 
/*    */       
/* 78 */       while (i > 0) {
/* 79 */         int k = EntityXPOrb.getXPSplit(i);
/* 80 */         i -= k;
/* 81 */         this.thePlayer.worldObj.spawnEntityInWorld((Entity)new EntityXPOrb(this.thePlayer.worldObj, this.thePlayer.posX, this.thePlayer.posY + 0.5D, this.thePlayer.posZ + 0.5D, k));
/*    */       } 
/*    */     } 
/*    */     
/* 85 */     this.field_75228_b = 0;
/*    */     
/* 87 */     if (stack.getItem() == Items.iron_ingot) {
/* 88 */       this.thePlayer.triggerAchievement((StatBase)AchievementList.acquireIron);
/*    */     }
/*    */     
/* 91 */     if (stack.getItem() == Items.cooked_fish)
/* 92 */       this.thePlayer.triggerAchievement((StatBase)AchievementList.cookFish); 
/*    */   }
/*    */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\minecraft\inventory\SlotFurnaceOutput.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */