/*    */ package net.optifine.render;
/*    */ 
/*    */ public class GlAlphaState {
/*    */   private boolean enabled;
/*    */   private int func;
/*    */   private float ref;
/*    */   
/*    */   public GlAlphaState() {
/*  9 */     this(false, 519, 0.0F);
/*    */   }
/*    */   
/*    */   public GlAlphaState(boolean enabled) {
/* 13 */     this(enabled, 519, 0.0F);
/*    */   }
/*    */   
/*    */   public GlAlphaState(boolean enabled, int func, float ref) {
/* 17 */     this.enabled = enabled;
/* 18 */     this.func = func;
/* 19 */     this.ref = ref;
/*    */   }
/*    */   
/*    */   public void setState(boolean enabled, int func, float ref) {
/* 23 */     this.enabled = enabled;
/* 24 */     this.func = func;
/* 25 */     this.ref = ref;
/*    */   }
/*    */   
/*    */   public void setState(GlAlphaState state) {
/* 29 */     this.enabled = state.enabled;
/* 30 */     this.func = state.func;
/* 31 */     this.ref = state.ref;
/*    */   }
/*    */   
/*    */   public void setFuncRef(int func, float ref) {
/* 35 */     this.func = func;
/* 36 */     this.ref = ref;
/*    */   }
/*    */   
/*    */   public void setEnabled(boolean enabled) {
/* 40 */     this.enabled = enabled;
/*    */   }
/*    */   
/*    */   public void setEnabled() {
/* 44 */     this.enabled = true;
/*    */   }
/*    */   
/*    */   public void setDisabled() {
/* 48 */     this.enabled = false;
/*    */   }
/*    */   
/*    */   public boolean isEnabled() {
/* 52 */     return this.enabled;
/*    */   }
/*    */   
/*    */   public int getFunc() {
/* 56 */     return this.func;
/*    */   }
/*    */   
/*    */   public float getRef() {
/* 60 */     return this.ref;
/*    */   }
/*    */   
/*    */   public String toString() {
/* 64 */     return "enabled: " + this.enabled + ", func: " + this.func + ", ref: " + this.ref;
/*    */   }
/*    */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\optifine\render\GlAlphaState.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */