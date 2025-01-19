/*    */ package net.minecraft.client.audio;
/*    */ 
/*    */ public class SoundEventAccessor implements ISoundEventAccessor<SoundPoolEntry> {
/*    */   private final SoundPoolEntry entry;
/*    */   private final int weight;
/*    */   
/*    */   SoundEventAccessor(SoundPoolEntry entry, int weight) {
/*  8 */     this.entry = entry;
/*  9 */     this.weight = weight;
/*    */   }
/*    */   
/*    */   public int getWeight() {
/* 13 */     return this.weight;
/*    */   }
/*    */   
/*    */   public SoundPoolEntry cloneEntry() {
/* 17 */     return new SoundPoolEntry(this.entry);
/*    */   }
/*    */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\minecraft\client\audio\SoundEventAccessor.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */