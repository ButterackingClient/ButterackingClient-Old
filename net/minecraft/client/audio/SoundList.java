/*     */ package net.minecraft.client.audio;
/*     */ 
/*     */ import com.google.common.collect.Lists;
/*     */ import java.util.List;
/*     */ 
/*     */ public class SoundList
/*     */ {
/*   8 */   private final List<SoundEntry> soundList = Lists.newArrayList();
/*     */ 
/*     */   
/*     */   private boolean replaceExisting;
/*     */   
/*     */   private SoundCategory category;
/*     */ 
/*     */   
/*     */   public List<SoundEntry> getSoundList() {
/*  17 */     return this.soundList;
/*     */   }
/*     */   
/*     */   public boolean canReplaceExisting() {
/*  21 */     return this.replaceExisting;
/*     */   }
/*     */   
/*     */   public void setReplaceExisting(boolean p_148572_1_) {
/*  25 */     this.replaceExisting = p_148572_1_;
/*     */   }
/*     */   
/*     */   public SoundCategory getSoundCategory() {
/*  29 */     return this.category;
/*     */   }
/*     */   
/*     */   public void setSoundCategory(SoundCategory soundCat) {
/*  33 */     this.category = soundCat;
/*     */   }
/*     */   public static class SoundEntry { private String name; private float volume; private float pitch;
/*     */     
/*     */     public SoundEntry() {
/*  38 */       this.volume = 1.0F;
/*  39 */       this.pitch = 1.0F;
/*  40 */       this.weight = 1;
/*  41 */       this.type = Type.FILE;
/*  42 */       this.streaming = false;
/*     */     } private int weight; private Type type; private boolean streaming;
/*     */     public String getSoundEntryName() {
/*  45 */       return this.name;
/*     */     }
/*     */     
/*     */     public void setSoundEntryName(String nameIn) {
/*  49 */       this.name = nameIn;
/*     */     }
/*     */     
/*     */     public float getSoundEntryVolume() {
/*  53 */       return this.volume;
/*     */     }
/*     */     
/*     */     public void setSoundEntryVolume(float volumeIn) {
/*  57 */       this.volume = volumeIn;
/*     */     }
/*     */     
/*     */     public float getSoundEntryPitch() {
/*  61 */       return this.pitch;
/*     */     }
/*     */     
/*     */     public void setSoundEntryPitch(float pitchIn) {
/*  65 */       this.pitch = pitchIn;
/*     */     }
/*     */     
/*     */     public int getSoundEntryWeight() {
/*  69 */       return this.weight;
/*     */     }
/*     */     
/*     */     public void setSoundEntryWeight(int weightIn) {
/*  73 */       this.weight = weightIn;
/*     */     }
/*     */     
/*     */     public Type getSoundEntryType() {
/*  77 */       return this.type;
/*     */     }
/*     */     
/*     */     public void setSoundEntryType(Type typeIn) {
/*  81 */       this.type = typeIn;
/*     */     }
/*     */     
/*     */     public boolean isStreaming() {
/*  85 */       return this.streaming;
/*     */     }
/*     */     
/*     */     public void setStreaming(boolean isStreaming) {
/*  89 */       this.streaming = isStreaming;
/*     */     }
/*     */     
/*     */     public enum Type {
/*  93 */       FILE("file"),
/*  94 */       SOUND_EVENT("event");
/*     */       private final String field_148583_c;
/*     */       
/*     */       Type(String p_i45109_3_)
/*     */       {
/*  99 */         this.field_148583_c = p_i45109_3_; } public static Type getType(String p_148580_0_) {
/*     */         byte b;
/*     */         int i;
/*     */         Type[] arrayOfType;
/* 103 */         for (i = (arrayOfType = values()).length, b = 0; b < i; ) { Type soundlist$soundentry$type = arrayOfType[b];
/* 104 */           if (soundlist$soundentry$type.field_148583_c.equals(p_148580_0_)) {
/* 105 */             return soundlist$soundentry$type;
/*     */           }
/*     */           b++; }
/*     */         
/* 109 */         return null;
/*     */       }
/*     */     } }
/*     */ 
/*     */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\minecraft\client\audio\SoundList.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */