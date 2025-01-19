/*    */ package net.minecraft.util;
/*    */ 
/*    */ import java.util.Collection;
/*    */ import java.util.Random;
/*    */ 
/*    */ 
/*    */ 
/*    */ public class WeightedRandom
/*    */ {
/*    */   public static int getTotalWeight(Collection<? extends Item> collection) {
/* 11 */     int i = 0;
/*    */     
/* 13 */     for (Item weightedrandom$item : collection) {
/* 14 */       i += weightedrandom$item.itemWeight;
/*    */     }
/*    */     
/* 17 */     return i;
/*    */   }
/*    */   
/*    */   public static <T extends Item> T getRandomItem(Random random, Collection<T> collection, int totalWeight) {
/* 21 */     if (totalWeight <= 0) {
/* 22 */       throw new IllegalArgumentException();
/*    */     }
/* 24 */     int i = random.nextInt(totalWeight);
/* 25 */     return getRandomItem(collection, i);
/*    */   }
/*    */ 
/*    */   
/*    */   public static <T extends Item> T getRandomItem(Collection<T> collection, int weight) {
/* 30 */     for (Item item : collection) {
/* 31 */       weight -= item.itemWeight;
/*    */       
/* 33 */       if (weight < 0) {
/* 34 */         return (T)item;
/*    */       }
/*    */     } 
/*    */     
/* 38 */     return null;
/*    */   }
/*    */   
/*    */   public static <T extends Item> T getRandomItem(Random random, Collection<T> collection) {
/* 42 */     return getRandomItem(random, collection, getTotalWeight(collection));
/*    */   }
/*    */   
/*    */   public static class Item {
/*    */     protected int itemWeight;
/*    */     
/*    */     public Item(int itemWeightIn) {
/* 49 */       this.itemWeight = itemWeightIn;
/*    */     }
/*    */   }
/*    */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\minecraf\\util\WeightedRandom.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */