/*    */ package net.minecraft.world;
/*    */ 
/*    */ import net.minecraft.util.MathHelper;
/*    */ 
/*    */ public class DifficultyInstance {
/*    */   private final EnumDifficulty worldDifficulty;
/*    */   private final float additionalDifficulty;
/*    */   
/*    */   public DifficultyInstance(EnumDifficulty worldDifficulty, long worldTime, long chunkInhabitedTime, float moonPhaseFactor) {
/* 10 */     this.worldDifficulty = worldDifficulty;
/* 11 */     this.additionalDifficulty = calculateAdditionalDifficulty(worldDifficulty, worldTime, chunkInhabitedTime, moonPhaseFactor);
/*    */   }
/*    */   
/*    */   public float getAdditionalDifficulty() {
/* 15 */     return this.additionalDifficulty;
/*    */   }
/*    */   
/*    */   public float getClampedAdditionalDifficulty() {
/* 19 */     return (this.additionalDifficulty < 2.0F) ? 0.0F : ((this.additionalDifficulty > 4.0F) ? 1.0F : ((this.additionalDifficulty - 2.0F) / 2.0F));
/*    */   }
/*    */   
/*    */   private float calculateAdditionalDifficulty(EnumDifficulty difficulty, long worldTime, long chunkInhabitedTime, float moonPhaseFactor) {
/* 23 */     if (difficulty == EnumDifficulty.PEACEFUL) {
/* 24 */       return 0.0F;
/*    */     }
/* 26 */     boolean flag = (difficulty == EnumDifficulty.HARD);
/* 27 */     float f = 0.75F;
/* 28 */     float f1 = MathHelper.clamp_float(((float)worldTime + -72000.0F) / 1440000.0F, 0.0F, 1.0F) * 0.25F;
/* 29 */     f += f1;
/* 30 */     float f2 = 0.0F;
/* 31 */     f2 += MathHelper.clamp_float((float)chunkInhabitedTime / 3600000.0F, 0.0F, 1.0F) * (flag ? 1.0F : 0.75F);
/* 32 */     f2 += MathHelper.clamp_float(moonPhaseFactor * 0.25F, 0.0F, f1);
/*    */     
/* 34 */     if (difficulty == EnumDifficulty.EASY) {
/* 35 */       f2 *= 0.5F;
/*    */     }
/*    */     
/* 38 */     f += f2;
/* 39 */     return difficulty.getDifficultyId() * f;
/*    */   }
/*    */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\minecraft\world\DifficultyInstance.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */