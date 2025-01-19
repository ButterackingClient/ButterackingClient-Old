/*    */ package net.minecraft.client.audio;
/*    */ 
/*    */ import net.minecraft.util.ResourceLocation;
/*    */ 
/*    */ public interface ISound {
/*    */   ResourceLocation getSoundLocation();
/*    */   
/*    */   boolean canRepeat();
/*    */   
/*    */   int getRepeatDelay();
/*    */   
/*    */   float getVolume();
/*    */   
/*    */   float getPitch();
/*    */   
/*    */   float getXPosF();
/*    */   
/*    */   float getYPosF();
/*    */   
/*    */   float getZPosF();
/*    */   
/*    */   AttenuationType getAttenuationType();
/*    */   
/*    */   public enum AttenuationType {
/* 25 */     NONE(0),
/* 26 */     LINEAR(2);
/*    */     
/*    */     private final int type;
/*    */     
/*    */     AttenuationType(int typeIn) {
/* 31 */       this.type = typeIn;
/*    */     }
/*    */     
/*    */     public int getTypeInt() {
/* 35 */       return this.type;
/*    */     }
/*    */   }
/*    */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\minecraft\client\audio\ISound.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */