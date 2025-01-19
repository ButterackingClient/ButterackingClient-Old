/*    */ package net.minecraft.client.audio;
/*    */ 
/*    */ import com.google.common.collect.Lists;
/*    */ import java.util.List;
/*    */ import java.util.Random;
/*    */ import net.minecraft.util.ResourceLocation;
/*    */ 
/*    */ public class SoundEventAccessorComposite
/*    */   implements ISoundEventAccessor<SoundPoolEntry>
/*    */ {
/* 11 */   private final List<ISoundEventAccessor<SoundPoolEntry>> soundPool = Lists.newArrayList();
/* 12 */   private final Random rnd = new Random();
/*    */   private final ResourceLocation soundLocation;
/*    */   private final SoundCategory category;
/*    */   private double eventPitch;
/*    */   private double eventVolume;
/*    */   
/*    */   public SoundEventAccessorComposite(ResourceLocation soundLocation, double pitch, double volume, SoundCategory category) {
/* 19 */     this.soundLocation = soundLocation;
/* 20 */     this.eventVolume = volume;
/* 21 */     this.eventPitch = pitch;
/* 22 */     this.category = category;
/*    */   }
/*    */   
/*    */   public int getWeight() {
/* 26 */     int i = 0;
/*    */     
/* 28 */     for (ISoundEventAccessor<SoundPoolEntry> isoundeventaccessor : this.soundPool) {
/* 29 */       i += isoundeventaccessor.getWeight();
/*    */     }
/*    */     
/* 32 */     return i;
/*    */   }
/*    */   
/*    */   public SoundPoolEntry cloneEntry() {
/* 36 */     int i = getWeight();
/*    */     
/* 38 */     if (!this.soundPool.isEmpty() && i != 0) {
/* 39 */       int j = this.rnd.nextInt(i);
/*    */       
/* 41 */       for (ISoundEventAccessor<SoundPoolEntry> isoundeventaccessor : this.soundPool) {
/* 42 */         j -= isoundeventaccessor.getWeight();
/*    */         
/* 44 */         if (j < 0) {
/* 45 */           SoundPoolEntry soundpoolentry = isoundeventaccessor.cloneEntry();
/* 46 */           soundpoolentry.setPitch(soundpoolentry.getPitch() * this.eventPitch);
/* 47 */           soundpoolentry.setVolume(soundpoolentry.getVolume() * this.eventVolume);
/* 48 */           return soundpoolentry;
/*    */         } 
/*    */       } 
/*    */       
/* 52 */       return SoundHandler.missing_sound;
/*    */     } 
/* 54 */     return SoundHandler.missing_sound;
/*    */   }
/*    */ 
/*    */   
/*    */   public void addSoundToEventPool(ISoundEventAccessor<SoundPoolEntry> sound) {
/* 59 */     this.soundPool.add(sound);
/*    */   }
/*    */   
/*    */   public ResourceLocation getSoundEventLocation() {
/* 63 */     return this.soundLocation;
/*    */   }
/*    */   
/*    */   public SoundCategory getSoundCategory() {
/* 67 */     return this.category;
/*    */   }
/*    */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\minecraft\client\audio\SoundEventAccessorComposite.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */