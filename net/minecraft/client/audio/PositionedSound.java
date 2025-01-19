/*    */ package net.minecraft.client.audio;
/*    */ 
/*    */ import net.minecraft.util.ResourceLocation;
/*    */ 
/*    */ public abstract class PositionedSound implements ISound {
/*    */   protected final ResourceLocation positionedSoundLocation;
/*  7 */   protected float volume = 1.0F;
/*  8 */   protected float pitch = 1.0F;
/*    */   
/*    */   protected float xPosF;
/*    */   
/*    */   protected float yPosF;
/*    */   
/*    */   protected float zPosF;
/*    */   
/*    */   protected boolean repeat = false;
/* 17 */   protected int repeatDelay = 0;
/* 18 */   protected ISound.AttenuationType attenuationType = ISound.AttenuationType.LINEAR;
/*    */   
/*    */   protected PositionedSound(ResourceLocation soundResource) {
/* 21 */     this.positionedSoundLocation = soundResource;
/*    */   }
/*    */   
/*    */   public ResourceLocation getSoundLocation() {
/* 25 */     return this.positionedSoundLocation;
/*    */   }
/*    */   
/*    */   public boolean canRepeat() {
/* 29 */     return this.repeat;
/*    */   }
/*    */   
/*    */   public int getRepeatDelay() {
/* 33 */     return this.repeatDelay;
/*    */   }
/*    */   
/*    */   public float getVolume() {
/* 37 */     return this.volume;
/*    */   }
/*    */   
/*    */   public float getPitch() {
/* 41 */     return this.pitch;
/*    */   }
/*    */   
/*    */   public float getXPosF() {
/* 45 */     return this.xPosF;
/*    */   }
/*    */   
/*    */   public float getYPosF() {
/* 49 */     return this.yPosF;
/*    */   }
/*    */   
/*    */   public float getZPosF() {
/* 53 */     return this.zPosF;
/*    */   }
/*    */   
/*    */   public ISound.AttenuationType getAttenuationType() {
/* 57 */     return this.attenuationType;
/*    */   }
/*    */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\minecraft\client\audio\PositionedSound.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */