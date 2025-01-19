/*    */ package net.optifine;
/*    */ 
/*    */ import java.util.BitSet;
/*    */ import net.minecraft.src.Config;
/*    */ import net.optifine.shaders.Shaders;
/*    */ 
/*    */ public class SmartAnimations
/*    */ {
/*    */   private static boolean active;
/* 10 */   private static BitSet spritesRendered = new BitSet();
/* 11 */   private static BitSet texturesRendered = new BitSet();
/*    */   
/*    */   public static boolean isActive() {
/* 14 */     return (active && !Shaders.isShadowPass);
/*    */   }
/*    */   
/*    */   public static void update() {
/* 18 */     active = (Config.getGameSettings()).ofSmartAnimations;
/*    */   }
/*    */   
/*    */   public static void spriteRendered(int animationIndex) {
/* 22 */     if (animationIndex >= 0) {
/* 23 */       spritesRendered.set(animationIndex);
/*    */     }
/*    */   }
/*    */   
/*    */   public static void spritesRendered(BitSet animationIndexes) {
/* 28 */     if (animationIndexes != null) {
/* 29 */       spritesRendered.or(animationIndexes);
/*    */     }
/*    */   }
/*    */   
/*    */   public static boolean isSpriteRendered(int animationIndex) {
/* 34 */     return (animationIndex < 0) ? false : spritesRendered.get(animationIndex);
/*    */   }
/*    */   
/*    */   public static void resetSpritesRendered() {
/* 38 */     spritesRendered.clear();
/*    */   }
/*    */   
/*    */   public static void textureRendered(int textureId) {
/* 42 */     if (textureId >= 0) {
/* 43 */       texturesRendered.set(textureId);
/*    */     }
/*    */   }
/*    */   
/*    */   public static boolean isTextureRendered(int texId) {
/* 48 */     return (texId < 0) ? false : texturesRendered.get(texId);
/*    */   }
/*    */   
/*    */   public static void resetTexturesRendered() {
/* 52 */     texturesRendered.clear();
/*    */   }
/*    */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\optifine\SmartAnimations.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */