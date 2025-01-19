/*    */ package net.optifine;
/*    */ 
/*    */ import net.minecraft.src.Config;
/*    */ import net.minecraft.util.Vec3;
/*    */ 
/*    */ public class CustomColorFader {
/*  7 */   private Vec3 color = null;
/*  8 */   private long timeUpdate = System.currentTimeMillis();
/*    */   
/*    */   public Vec3 getColor(double x, double y, double z) {
/* 11 */     if (this.color == null) {
/* 12 */       this.color = new Vec3(x, y, z);
/* 13 */       return this.color;
/*    */     } 
/* 15 */     long i = System.currentTimeMillis();
/* 16 */     long j = i - this.timeUpdate;
/*    */     
/* 18 */     if (j == 0L) {
/* 19 */       return this.color;
/*    */     }
/* 21 */     this.timeUpdate = i;
/*    */     
/* 23 */     if (Math.abs(x - this.color.xCoord) < 0.004D && Math.abs(y - this.color.yCoord) < 0.004D && Math.abs(z - this.color.zCoord) < 0.004D) {
/* 24 */       return this.color;
/*    */     }
/* 26 */     double d0 = j * 0.001D;
/* 27 */     d0 = Config.limit(d0, 0.0D, 1.0D);
/* 28 */     double d1 = x - this.color.xCoord;
/* 29 */     double d2 = y - this.color.yCoord;
/* 30 */     double d3 = z - this.color.zCoord;
/* 31 */     double d4 = this.color.xCoord + d1 * d0;
/* 32 */     double d5 = this.color.yCoord + d2 * d0;
/* 33 */     double d6 = this.color.zCoord + d3 * d0;
/* 34 */     this.color = new Vec3(d4, d5, d6);
/* 35 */     return this.color;
/*    */   }
/*    */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\optifine\CustomColorFader.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */