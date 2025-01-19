/*    */ package net.minecraft.util;
/*    */ 
/*    */ import java.util.Random;
/*    */ 
/*    */ public class EnchantmentNameParts {
/*  6 */   private static final EnchantmentNameParts instance = new EnchantmentNameParts();
/*  7 */   private Random rand = new Random();
/*  8 */   private String[] namePartsArray = "the elder scrolls klaatu berata niktu xyzzy bless curse light darkness fire air earth water hot dry cold wet ignite snuff embiggen twist shorten stretch fiddle destroy imbue galvanize enchant free limited range of towards inside sphere cube self other ball mental physical grow shrink demon elemental spirit animal creature beast humanoid undead fresh stale ".split(" ");
/*    */   
/*    */   public static EnchantmentNameParts getInstance() {
/* 11 */     return instance;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public String generateNewRandomName() {
/* 18 */     int i = this.rand.nextInt(2) + 3;
/* 19 */     String s = "";
/*    */     
/* 21 */     for (int j = 0; j < i; j++) {
/* 22 */       if (j > 0) {
/* 23 */         s = String.valueOf(s) + " ";
/*    */       }
/*    */       
/* 26 */       s = String.valueOf(s) + this.namePartsArray[this.rand.nextInt(this.namePartsArray.length)];
/*    */     } 
/*    */     
/* 29 */     return s;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void reseedRandomGenerator(long seed) {
/* 36 */     this.rand.setSeed(seed);
/*    */   }
/*    */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\minecraf\\util\EnchantmentNameParts.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */