/*    */ package net.minecraft.client.audio;
/*    */ 
/*    */ import net.minecraft.util.ResourceLocation;
/*    */ 
/*    */ public class SoundPoolEntry {
/*    */   private final ResourceLocation location;
/*    */   private final boolean streamingSound;
/*    */   private double pitch;
/*    */   private double volume;
/*    */   
/*    */   public SoundPoolEntry(ResourceLocation locationIn, double pitchIn, double volumeIn, boolean streamingSoundIn) {
/* 12 */     this.location = locationIn;
/* 13 */     this.pitch = pitchIn;
/* 14 */     this.volume = volumeIn;
/* 15 */     this.streamingSound = streamingSoundIn;
/*    */   }
/*    */   
/*    */   public SoundPoolEntry(SoundPoolEntry locationIn) {
/* 19 */     this.location = locationIn.location;
/* 20 */     this.pitch = locationIn.pitch;
/* 21 */     this.volume = locationIn.volume;
/* 22 */     this.streamingSound = locationIn.streamingSound;
/*    */   }
/*    */   
/*    */   public ResourceLocation getSoundPoolEntryLocation() {
/* 26 */     return this.location;
/*    */   }
/*    */   
/*    */   public double getPitch() {
/* 30 */     return this.pitch;
/*    */   }
/*    */   
/*    */   public void setPitch(double pitchIn) {
/* 34 */     this.pitch = pitchIn;
/*    */   }
/*    */   
/*    */   public double getVolume() {
/* 38 */     return this.volume;
/*    */   }
/*    */   
/*    */   public void setVolume(double volumeIn) {
/* 42 */     this.volume = volumeIn;
/*    */   }
/*    */   
/*    */   public boolean isStreamingSound() {
/* 46 */     return this.streamingSound;
/*    */   }
/*    */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\minecraft\client\audio\SoundPoolEntry.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */