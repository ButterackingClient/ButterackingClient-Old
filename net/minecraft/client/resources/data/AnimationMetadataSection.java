/*    */ package net.minecraft.client.resources.data;
/*    */ 
/*    */ import com.google.common.collect.Sets;
/*    */ import java.util.List;
/*    */ import java.util.Set;
/*    */ 
/*    */ public class AnimationMetadataSection
/*    */   implements IMetadataSection {
/*    */   private final List<AnimationFrame> animationFrames;
/*    */   private final int frameWidth;
/*    */   private final int frameHeight;
/*    */   private final int frameTime;
/*    */   private final boolean interpolate;
/*    */   
/*    */   public AnimationMetadataSection(List<AnimationFrame> p_i46088_1_, int p_i46088_2_, int p_i46088_3_, int p_i46088_4_, boolean p_i46088_5_) {
/* 16 */     this.animationFrames = p_i46088_1_;
/* 17 */     this.frameWidth = p_i46088_2_;
/* 18 */     this.frameHeight = p_i46088_3_;
/* 19 */     this.frameTime = p_i46088_4_;
/* 20 */     this.interpolate = p_i46088_5_;
/*    */   }
/*    */   
/*    */   public int getFrameHeight() {
/* 24 */     return this.frameHeight;
/*    */   }
/*    */   
/*    */   public int getFrameWidth() {
/* 28 */     return this.frameWidth;
/*    */   }
/*    */   
/*    */   public int getFrameCount() {
/* 32 */     return this.animationFrames.size();
/*    */   }
/*    */   
/*    */   public int getFrameTime() {
/* 36 */     return this.frameTime;
/*    */   }
/*    */   
/*    */   public boolean isInterpolate() {
/* 40 */     return this.interpolate;
/*    */   }
/*    */   
/*    */   private AnimationFrame getAnimationFrame(int p_130072_1_) {
/* 44 */     return this.animationFrames.get(p_130072_1_);
/*    */   }
/*    */   
/*    */   public int getFrameTimeSingle(int p_110472_1_) {
/* 48 */     AnimationFrame animationframe = getAnimationFrame(p_110472_1_);
/* 49 */     return animationframe.hasNoTime() ? this.frameTime : animationframe.getFrameTime();
/*    */   }
/*    */   
/*    */   public boolean frameHasTime(int p_110470_1_) {
/* 53 */     return !((AnimationFrame)this.animationFrames.get(p_110470_1_)).hasNoTime();
/*    */   }
/*    */   
/*    */   public int getFrameIndex(int p_110468_1_) {
/* 57 */     return ((AnimationFrame)this.animationFrames.get(p_110468_1_)).getFrameIndex();
/*    */   }
/*    */   
/*    */   public Set<Integer> getFrameIndexSet() {
/* 61 */     Set<Integer> set = Sets.newHashSet();
/*    */     
/* 63 */     for (AnimationFrame animationframe : this.animationFrames) {
/* 64 */       set.add(Integer.valueOf(animationframe.getFrameIndex()));
/*    */     }
/*    */     
/* 67 */     return set;
/*    */   }
/*    */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\minecraft\client\resources\data\AnimationMetadataSection.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */