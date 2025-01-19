/*    */ package net.minecraft.entity.boss;
/*    */ 
/*    */ public final class BossStatus {
/*    */   public static float healthScale;
/*    */   public static int statusBarTime;
/*    */   public static String bossName;
/*    */   public static boolean hasColorModifier;
/*    */   
/*    */   public static void setBossStatus(IBossDisplayData displayData, boolean hasColorModifierIn) {
/* 10 */     healthScale = displayData.getHealth() / displayData.getMaxHealth();
/* 11 */     statusBarTime = 100;
/* 12 */     bossName = displayData.getDisplayName().getFormattedText();
/* 13 */     hasColorModifier = hasColorModifierIn;
/*    */   }
/*    */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\minecraft\entity\boss\BossStatus.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */