/*    */ package net.minecraft.client.gui;
/*    */ 
/*    */ import net.minecraft.client.Minecraft;
/*    */ import net.minecraft.util.MathHelper;
/*    */ 
/*    */ public class ScaledResolution {
/*    */   private final double scaledWidthD;
/*    */   private final double scaledHeightD;
/*    */   private int scaledWidth;
/*    */   private int scaledHeight;
/*    */   private int scaleFactor;
/*    */   
/*    */   public ScaledResolution(Minecraft p_i46445_1_) {
/* 14 */     this.scaledWidth = p_i46445_1_.displayWidth;
/* 15 */     this.scaledHeight = p_i46445_1_.displayHeight;
/* 16 */     this.scaleFactor = 1;
/* 17 */     boolean flag = p_i46445_1_.isUnicode();
/* 18 */     int i = p_i46445_1_.gameSettings.particleSetting;
/*    */     
/* 20 */     if (i == 0) {
/* 21 */       i = 1000;
/*    */     }
/*    */     
/* 24 */     while (this.scaleFactor < i && this.scaledWidth / (this.scaleFactor + 1) >= 320 && this.scaledHeight / (this.scaleFactor + 1) >= 240) {
/* 25 */       this.scaleFactor++;
/*    */     }
/*    */     
/* 28 */     if (flag && this.scaleFactor % 2 != 0 && this.scaleFactor != 1) {
/* 29 */       this.scaleFactor--;
/*    */     }
/*    */     
/* 32 */     this.scaledWidthD = this.scaledWidth / this.scaleFactor;
/* 33 */     this.scaledHeightD = this.scaledHeight / this.scaleFactor;
/* 34 */     this.scaledWidth = MathHelper.ceiling_double_int(this.scaledWidthD);
/* 35 */     this.scaledHeight = MathHelper.ceiling_double_int(this.scaledHeightD);
/*    */   }
/*    */   
/*    */   public int getScaledWidth() {
/* 39 */     return this.scaledWidth;
/*    */   }
/*    */   
/*    */   public int getScaledHeight() {
/* 43 */     return this.scaledHeight;
/*    */   }
/*    */   
/*    */   public double getScaledWidth_double() {
/* 47 */     return this.scaledWidthD;
/*    */   }
/*    */   
/*    */   public double getScaledHeight_double() {
/* 51 */     return this.scaledHeightD;
/*    */   }
/*    */   
/*    */   public int getScaleFactor() {
/* 55 */     return this.scaleFactor;
/*    */   }
/*    */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\minecraft\client\gui\ScaledResolution.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */