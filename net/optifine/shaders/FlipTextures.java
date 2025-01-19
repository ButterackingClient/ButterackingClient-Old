/*    */ package net.optifine.shaders;
/*    */ 
/*    */ import java.nio.IntBuffer;
/*    */ import java.util.Arrays;
/*    */ 
/*    */ public class FlipTextures {
/*    */   private IntBuffer textures;
/*    */   private int indexFlipped;
/*    */   private boolean[] flips;
/*    */   private boolean[] changed;
/*    */   
/*    */   public FlipTextures(IntBuffer textures, int indexFlipped) {
/* 13 */     this.textures = textures;
/* 14 */     this.indexFlipped = indexFlipped;
/* 15 */     this.flips = new boolean[textures.capacity()];
/* 16 */     this.changed = new boolean[textures.capacity()];
/*    */   }
/*    */   
/*    */   public int getA(int index) {
/* 20 */     return get(index, this.flips[index]);
/*    */   }
/*    */   
/*    */   public int getB(int index) {
/* 24 */     return get(index, !this.flips[index]);
/*    */   }
/*    */   
/*    */   private int get(int index, boolean flipped) {
/* 28 */     int i = flipped ? this.indexFlipped : 0;
/* 29 */     return this.textures.get(i + index);
/*    */   }
/*    */   
/*    */   public void flip(int index) {
/* 33 */     this.flips[index] = !this.flips[index];
/* 34 */     this.changed[index] = true;
/*    */   }
/*    */   
/*    */   public boolean isChanged(int index) {
/* 38 */     return this.changed[index];
/*    */   }
/*    */   
/*    */   public void reset() {
/* 42 */     Arrays.fill(this.flips, false);
/* 43 */     Arrays.fill(this.changed, false);
/*    */   }
/*    */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\optifine\shaders\FlipTextures.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */