/*    */ package net.optifine.render;
/*    */ 
/*    */ public class GlBlendState {
/*    */   private boolean enabled;
/*    */   private int srcFactor;
/*    */   private int dstFactor;
/*    */   private int srcFactorAlpha;
/*    */   private int dstFactorAlpha;
/*    */   
/*    */   public GlBlendState() {
/* 11 */     this(false, 1, 0);
/*    */   }
/*    */   
/*    */   public GlBlendState(boolean enabled) {
/* 15 */     this(enabled, 1, 0);
/*    */   }
/*    */   
/*    */   public GlBlendState(boolean enabled, int srcFactor, int dstFactor, int srcFactorAlpha, int dstFactorAlpha) {
/* 19 */     this.enabled = enabled;
/* 20 */     this.srcFactor = srcFactor;
/* 21 */     this.dstFactor = dstFactor;
/* 22 */     this.srcFactorAlpha = srcFactorAlpha;
/* 23 */     this.dstFactorAlpha = dstFactorAlpha;
/*    */   }
/*    */   
/*    */   public GlBlendState(boolean enabled, int srcFactor, int dstFactor) {
/* 27 */     this(enabled, srcFactor, dstFactor, srcFactor, dstFactor);
/*    */   }
/*    */   
/*    */   public void setState(boolean enabled, int srcFactor, int dstFactor, int srcFactorAlpha, int dstFactorAlpha) {
/* 31 */     this.enabled = enabled;
/* 32 */     this.srcFactor = srcFactor;
/* 33 */     this.dstFactor = dstFactor;
/* 34 */     this.srcFactorAlpha = srcFactorAlpha;
/* 35 */     this.dstFactorAlpha = dstFactorAlpha;
/*    */   }
/*    */   
/*    */   public void setState(GlBlendState state) {
/* 39 */     this.enabled = state.enabled;
/* 40 */     this.srcFactor = state.srcFactor;
/* 41 */     this.dstFactor = state.dstFactor;
/* 42 */     this.srcFactorAlpha = state.srcFactorAlpha;
/* 43 */     this.dstFactorAlpha = state.dstFactorAlpha;
/*    */   }
/*    */   
/*    */   public void setEnabled(boolean enabled) {
/* 47 */     this.enabled = enabled;
/*    */   }
/*    */   
/*    */   public void setEnabled() {
/* 51 */     this.enabled = true;
/*    */   }
/*    */   
/*    */   public void setDisabled() {
/* 55 */     this.enabled = false;
/*    */   }
/*    */   
/*    */   public void setFactors(int srcFactor, int dstFactor) {
/* 59 */     this.srcFactor = srcFactor;
/* 60 */     this.dstFactor = dstFactor;
/* 61 */     this.srcFactorAlpha = srcFactor;
/* 62 */     this.dstFactorAlpha = dstFactor;
/*    */   }
/*    */   
/*    */   public void setFactors(int srcFactor, int dstFactor, int srcFactorAlpha, int dstFactorAlpha) {
/* 66 */     this.srcFactor = srcFactor;
/* 67 */     this.dstFactor = dstFactor;
/* 68 */     this.srcFactorAlpha = srcFactorAlpha;
/* 69 */     this.dstFactorAlpha = dstFactorAlpha;
/*    */   }
/*    */   
/*    */   public boolean isEnabled() {
/* 73 */     return this.enabled;
/*    */   }
/*    */   
/*    */   public int getSrcFactor() {
/* 77 */     return this.srcFactor;
/*    */   }
/*    */   
/*    */   public int getDstFactor() {
/* 81 */     return this.dstFactor;
/*    */   }
/*    */   
/*    */   public int getSrcFactorAlpha() {
/* 85 */     return this.srcFactorAlpha;
/*    */   }
/*    */   
/*    */   public int getDstFactorAlpha() {
/* 89 */     return this.dstFactorAlpha;
/*    */   }
/*    */   
/*    */   public boolean isSeparate() {
/* 93 */     return !(this.srcFactor == this.srcFactorAlpha && this.dstFactor == this.dstFactorAlpha);
/*    */   }
/*    */   
/*    */   public String toString() {
/* 97 */     return "enabled: " + this.enabled + ", src: " + this.srcFactor + ", dst: " + this.dstFactor + ", srcAlpha: " + this.srcFactorAlpha + ", dstAlpha: " + this.dstFactorAlpha;
/*    */   }
/*    */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\optifine\render\GlBlendState.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */