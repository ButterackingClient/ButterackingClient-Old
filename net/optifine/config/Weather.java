/*    */ package net.optifine.config;
/*    */ 
/*    */ import net.minecraft.world.World;
/*    */ 
/*    */ public enum Weather {
/*  6 */   CLEAR,
/*  7 */   RAIN,
/*  8 */   THUNDER;
/*    */   
/*    */   public static Weather getWeather(World world, float partialTicks) {
/* 11 */     float f = world.getThunderStrength(partialTicks);
/*    */     
/* 13 */     if (f > 0.5F) {
/* 14 */       return THUNDER;
/*    */     }
/* 16 */     float f1 = world.getRainStrength(partialTicks);
/* 17 */     return (f1 > 0.5F) ? RAIN : CLEAR;
/*    */   }
/*    */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\optifine\config\Weather.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */