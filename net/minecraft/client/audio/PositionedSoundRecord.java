/*    */ package net.minecraft.client.audio;
/*    */ 
/*    */ import net.minecraft.util.ResourceLocation;
/*    */ 
/*    */ public class PositionedSoundRecord extends PositionedSound {
/*    */   public static PositionedSoundRecord create(ResourceLocation soundResource, float pitch) {
/*  7 */     return new PositionedSoundRecord(soundResource, 0.25F, pitch, false, 0, ISound.AttenuationType.NONE, 0.0F, 0.0F, 0.0F);
/*    */   }
/*    */   
/*    */   public static PositionedSoundRecord create(ResourceLocation soundResource) {
/* 11 */     return new PositionedSoundRecord(soundResource, 1.0F, 1.0F, false, 0, ISound.AttenuationType.NONE, 0.0F, 0.0F, 0.0F);
/*    */   }
/*    */   
/*    */   public static PositionedSoundRecord create(ResourceLocation soundResource, float xPosition, float yPosition, float zPosition) {
/* 15 */     return new PositionedSoundRecord(soundResource, 4.0F, 1.0F, false, 0, ISound.AttenuationType.LINEAR, xPosition, yPosition, zPosition);
/*    */   }
/*    */   
/*    */   public PositionedSoundRecord(ResourceLocation soundResource, float volume, float pitch, float xPosition, float yPosition, float zPosition) {
/* 19 */     this(soundResource, volume, pitch, false, 0, ISound.AttenuationType.LINEAR, xPosition, yPosition, zPosition);
/*    */   }
/*    */   
/*    */   private PositionedSoundRecord(ResourceLocation soundResource, float volume, float pitch, boolean repeat, int repeatDelay, ISound.AttenuationType attenuationType, float xPosition, float yPosition, float zPosition) {
/* 23 */     super(soundResource);
/* 24 */     this.volume = volume;
/* 25 */     this.pitch = pitch;
/* 26 */     this.xPosF = xPosition;
/* 27 */     this.yPosF = yPosition;
/* 28 */     this.zPosF = zPosition;
/* 29 */     this.repeat = repeat;
/* 30 */     this.repeatDelay = repeatDelay;
/* 31 */     this.attenuationType = attenuationType;
/*    */   }
/*    */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\minecraft\client\audio\PositionedSoundRecord.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */