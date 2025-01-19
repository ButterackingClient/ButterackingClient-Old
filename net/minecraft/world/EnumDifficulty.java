/*    */ package net.minecraft.world;
/*    */ 
/*    */ public enum EnumDifficulty {
/*  4 */   PEACEFUL(0, "options.difficulty.peaceful"),
/*  5 */   EASY(1, "options.difficulty.easy"),
/*  6 */   NORMAL(2, "options.difficulty.normal"),
/*  7 */   HARD(3, "options.difficulty.hard");
/*    */   static {
/*  9 */     difficultyEnums = new EnumDifficulty[(values()).length];
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */     
/*    */     byte b;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */     
/*    */     int i;
/*    */ 
/*    */ 
/*    */ 
/*    */     
/*    */     EnumDifficulty[] arrayOfEnumDifficulty;
/*    */ 
/*    */ 
/*    */ 
/*    */     
/* 31 */     for (i = (arrayOfEnumDifficulty = values()).length, b = 0; b < i; ) { EnumDifficulty enumdifficulty = arrayOfEnumDifficulty[b];
/* 32 */       difficultyEnums[enumdifficulty.difficultyId] = enumdifficulty;
/*    */       b++; }
/*    */   
/*    */   }
/*    */   
/*    */   private static final EnumDifficulty[] difficultyEnums;
/*    */   private final int difficultyId;
/*    */   private final String difficultyResourceKey;
/*    */   
/*    */   EnumDifficulty(int difficultyIdIn, String difficultyResourceKeyIn) {
/*    */     this.difficultyId = difficultyIdIn;
/*    */     this.difficultyResourceKey = difficultyResourceKeyIn;
/*    */   }
/*    */   
/*    */   public int getDifficultyId() {
/*    */     return this.difficultyId;
/*    */   }
/*    */   
/*    */   public static EnumDifficulty getDifficultyEnum(int p_151523_0_) {
/*    */     return difficultyEnums[p_151523_0_ % difficultyEnums.length];
/*    */   }
/*    */   
/*    */   public String getDifficultyResourceKey() {
/*    */     return this.difficultyResourceKey;
/*    */   }
/*    */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\minecraft\world\EnumDifficulty.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */