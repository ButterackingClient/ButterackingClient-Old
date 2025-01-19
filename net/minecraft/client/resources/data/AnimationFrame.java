/*    */ package net.minecraft.client.resources.data;
/*    */ 
/*    */ public class AnimationFrame {
/*    */   private final int frameIndex;
/*    */   private final int frameTime;
/*    */   
/*    */   public AnimationFrame(int p_i1307_1_) {
/*  8 */     this(p_i1307_1_, -1);
/*    */   }
/*    */   
/*    */   public AnimationFrame(int p_i1308_1_, int p_i1308_2_) {
/* 12 */     this.frameIndex = p_i1308_1_;
/* 13 */     this.frameTime = p_i1308_2_;
/*    */   }
/*    */   
/*    */   public boolean hasNoTime() {
/* 17 */     return (this.frameTime == -1);
/*    */   }
/*    */   
/*    */   public int getFrameTime() {
/* 21 */     return this.frameTime;
/*    */   }
/*    */   
/*    */   public int getFrameIndex() {
/* 25 */     return this.frameIndex;
/*    */   }
/*    */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\minecraft\client\resources\data\AnimationFrame.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */