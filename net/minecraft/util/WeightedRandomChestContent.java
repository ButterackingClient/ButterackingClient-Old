/*    */ package net.minecraft.util;
/*    */ 
/*    */ import com.google.common.collect.Lists;
/*    */ import java.util.Collections;
/*    */ import java.util.List;
/*    */ import java.util.Random;
/*    */ import net.minecraft.inventory.IInventory;
/*    */ import net.minecraft.item.Item;
/*    */ import net.minecraft.item.ItemStack;
/*    */ import net.minecraft.tileentity.TileEntityDispenser;
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
/*    */ 
/*    */ 
/*    */ public class WeightedRandomChestContent
/*    */   extends WeightedRandom.Item
/*    */ {
/*    */   private ItemStack theItemId;
/*    */   private int minStackSize;
/*    */   private int maxStackSize;
/*    */   
/*    */   public WeightedRandomChestContent(Item p_i45311_1_, int p_i45311_2_, int minimumChance, int maximumChance, int itemWeightIn) {
/* 31 */     super(itemWeightIn);
/* 32 */     this.theItemId = new ItemStack(p_i45311_1_, 1, p_i45311_2_);
/* 33 */     this.minStackSize = minimumChance;
/* 34 */     this.maxStackSize = maximumChance;
/*    */   }
/*    */   
/*    */   public WeightedRandomChestContent(ItemStack stack, int minimumChance, int maximumChance, int itemWeightIn) {
/* 38 */     super(itemWeightIn);
/* 39 */     this.theItemId = stack;
/* 40 */     this.minStackSize = minimumChance;
/* 41 */     this.maxStackSize = maximumChance;
/*    */   }
/*    */   
/*    */   public static void generateChestContents(Random random, List<WeightedRandomChestContent> listIn, IInventory inv, int max) {
/* 45 */     for (int i = 0; i < max; i++) {
/* 46 */       WeightedRandomChestContent weightedrandomchestcontent = WeightedRandom.<WeightedRandomChestContent>getRandomItem(random, listIn);
/* 47 */       int j = weightedrandomchestcontent.minStackSize + random.nextInt(weightedrandomchestcontent.maxStackSize - weightedrandomchestcontent.minStackSize + 1);
/*    */       
/* 49 */       if (weightedrandomchestcontent.theItemId.getMaxStackSize() >= j) {
/* 50 */         ItemStack itemstack1 = weightedrandomchestcontent.theItemId.copy();
/* 51 */         itemstack1.stackSize = j;
/* 52 */         inv.setInventorySlotContents(random.nextInt(inv.getSizeInventory()), itemstack1);
/*    */       } else {
/* 54 */         for (int k = 0; k < j; k++) {
/* 55 */           ItemStack itemstack = weightedrandomchestcontent.theItemId.copy();
/* 56 */           itemstack.stackSize = 1;
/* 57 */           inv.setInventorySlotContents(random.nextInt(inv.getSizeInventory()), itemstack);
/*    */         } 
/*    */       } 
/*    */     } 
/*    */   }
/*    */   
/*    */   public static void generateDispenserContents(Random random, List<WeightedRandomChestContent> listIn, TileEntityDispenser dispenser, int max) {
/* 64 */     for (int i = 0; i < max; i++) {
/* 65 */       WeightedRandomChestContent weightedrandomchestcontent = WeightedRandom.<WeightedRandomChestContent>getRandomItem(random, listIn);
/* 66 */       int j = weightedrandomchestcontent.minStackSize + random.nextInt(weightedrandomchestcontent.maxStackSize - weightedrandomchestcontent.minStackSize + 1);
/*    */       
/* 68 */       if (weightedrandomchestcontent.theItemId.getMaxStackSize() >= j) {
/* 69 */         ItemStack itemstack1 = weightedrandomchestcontent.theItemId.copy();
/* 70 */         itemstack1.stackSize = j;
/* 71 */         dispenser.setInventorySlotContents(random.nextInt(dispenser.getSizeInventory()), itemstack1);
/*    */       } else {
/* 73 */         for (int k = 0; k < j; k++) {
/* 74 */           ItemStack itemstack = weightedrandomchestcontent.theItemId.copy();
/* 75 */           itemstack.stackSize = 1;
/* 76 */           dispenser.setInventorySlotContents(random.nextInt(dispenser.getSizeInventory()), itemstack);
/*    */         } 
/*    */       } 
/*    */     } 
/*    */   }
/*    */   
/*    */   public static List<WeightedRandomChestContent> func_177629_a(List<WeightedRandomChestContent> p_177629_0_, WeightedRandomChestContent... p_177629_1_) {
/* 83 */     List<WeightedRandomChestContent> list = Lists.newArrayList(p_177629_0_);
/* 84 */     Collections.addAll(list, p_177629_1_);
/* 85 */     return list;
/*    */   }
/*    */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\minecraf\\util\WeightedRandomChestContent.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */