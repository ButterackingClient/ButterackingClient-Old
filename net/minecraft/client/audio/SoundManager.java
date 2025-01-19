/*     */ package net.minecraft.client.audio;
/*     */ 
/*     */ import com.google.common.collect.BiMap;
/*     */ import com.google.common.collect.HashBiMap;
/*     */ import com.google.common.collect.HashMultimap;
/*     */ import com.google.common.collect.Lists;
/*     */ import com.google.common.collect.Maps;
/*     */ import com.google.common.collect.Multimap;
/*     */ import io.netty.util.internal.ThreadLocalRandom;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import java.net.MalformedURLException;
/*     */ import java.net.URL;
/*     */ import java.net.URLConnection;
/*     */ import java.net.URLStreamHandler;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import java.util.Random;
/*     */ import net.minecraft.client.Minecraft;
/*     */ import net.minecraft.client.settings.GameSettings;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.util.MathHelper;
/*     */ import net.minecraft.util.ResourceLocation;
/*     */ import org.apache.logging.log4j.LogManager;
/*     */ import org.apache.logging.log4j.Logger;
/*     */ import org.apache.logging.log4j.Marker;
/*     */ import org.apache.logging.log4j.MarkerManager;
/*     */ import paulscode.sound.SoundSystem;
/*     */ import paulscode.sound.SoundSystemConfig;
/*     */ import paulscode.sound.SoundSystemException;
/*     */ import paulscode.sound.SoundSystemLogger;
/*     */ import paulscode.sound.Source;
/*     */ import paulscode.sound.codecs.CodecJOrbis;
/*     */ import paulscode.sound.libraries.LibraryLWJGLOpenAL;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class SoundManager
/*     */ {
/*  43 */   private static final Marker LOG_MARKER = MarkerManager.getMarker("SOUNDS");
/*  44 */   private static final Logger logger = LogManager.getLogger();
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private final SoundHandler sndHandler;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private final GameSettings options;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private SoundSystemStarterThread sndSystem;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private boolean loaded;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  69 */   private int playTime = 0;
/*  70 */   private final Map<String, ISound> playingSounds = (Map<String, ISound>)HashBiMap.create();
/*     */   private final Map<ISound, String> invPlayingSounds;
/*     */   private Map<ISound, SoundPoolEntry> playingSoundPoolEntries;
/*     */   private final Multimap<SoundCategory, String> categorySounds;
/*     */   private final List<ITickableSound> tickableSounds;
/*     */   private final Map<ISound, Integer> delayedSounds;
/*     */   private final Map<String, Integer> playingSoundsStopTime;
/*     */   
/*     */   public SoundManager(SoundHandler p_i45119_1_, GameSettings p_i45119_2_) {
/*  79 */     this.invPlayingSounds = (Map<ISound, String>)((BiMap)this.playingSounds).inverse();
/*  80 */     this.playingSoundPoolEntries = Maps.newHashMap();
/*  81 */     this.categorySounds = (Multimap<SoundCategory, String>)HashMultimap.create();
/*  82 */     this.tickableSounds = Lists.newArrayList();
/*  83 */     this.delayedSounds = Maps.newHashMap();
/*  84 */     this.playingSoundsStopTime = Maps.newHashMap();
/*  85 */     this.sndHandler = p_i45119_1_;
/*  86 */     this.options = p_i45119_2_;
/*     */     
/*     */     try {
/*  89 */       SoundSystemConfig.addLibrary(LibraryLWJGLOpenAL.class);
/*  90 */       SoundSystemConfig.setCodec("ogg", CodecJOrbis.class);
/*  91 */     } catch (SoundSystemException soundsystemexception) {
/*  92 */       logger.error(LOG_MARKER, "Error linking with the LibraryJavaSound plug-in", (Throwable)soundsystemexception);
/*     */     } 
/*     */   }
/*     */   
/*     */   public void reloadSoundSystem() {
/*  97 */     unloadSoundSystem();
/*  98 */     loadSoundSystem();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private synchronized void loadSoundSystem() {
/* 105 */     if (!this.loaded) {
/*     */       try {
/* 107 */         (new Thread(new Runnable() {
/*     */               public void run() {
/* 109 */                 SoundSystemConfig.setLogger(new SoundSystemLogger() {
/*     */                       public void message(String p_message_1_, int p_message_2_) {
/* 111 */                         if (!p_message_1_.isEmpty()) {
/* 112 */                           SoundManager.logger.info(p_message_1_);
/*     */                         }
/*     */                       }
/*     */                       
/*     */                       public void importantMessage(String p_importantMessage_1_, int p_importantMessage_2_) {
/* 117 */                         if (!p_importantMessage_1_.isEmpty()) {
/* 118 */                           SoundManager.logger.warn(p_importantMessage_1_);
/*     */                         }
/*     */                       }
/*     */                       
/*     */                       public void errorMessage(String p_errorMessage_1_, String p_errorMessage_2_, int p_errorMessage_3_) {
/* 123 */                         if (!p_errorMessage_2_.isEmpty()) {
/* 124 */                           SoundManager.logger.error("Error in class '" + p_errorMessage_1_ + "'");
/* 125 */                           SoundManager.logger.error(p_errorMessage_2_);
/*     */                         } 
/*     */                       }
/*     */                     },  );
/* 129 */                 SoundManager.this.getClass(); SoundManager.this.sndSystem = new SoundManager.SoundSystemStarterThread(null);
/* 130 */                 SoundManager.this.loaded = true;
/* 131 */                 SoundManager.this.sndSystem.setMasterVolume(SoundManager.this.options.getSoundLevel(SoundCategory.MASTER));
/* 132 */                 SoundManager.logger.info(SoundManager.LOG_MARKER, "Sound engine started");
/*     */               }
/* 134 */             }"Sound Library Loader")).start();
/* 135 */       } catch (RuntimeException runtimeexception) {
/* 136 */         logger.error(LOG_MARKER, "Error starting SoundSystem. Turning off sounds & music", runtimeexception);
/* 137 */         this.options.setSoundLevel(SoundCategory.MASTER, 0.0F);
/* 138 */         this.options.saveOptions();
/*     */       } 
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private float getSoundCategoryVolume(SoundCategory category) {
/* 147 */     return (category != null && category != SoundCategory.MASTER) ? this.options.getSoundLevel(category) : 1.0F;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setSoundCategoryVolume(SoundCategory category, float volume) {
/* 154 */     if (this.loaded) {
/* 155 */       if (category == SoundCategory.MASTER) {
/* 156 */         this.sndSystem.setMasterVolume(volume);
/*     */       } else {
/* 158 */         for (String s : this.categorySounds.get(category)) {
/* 159 */           ISound isound = this.playingSounds.get(s);
/* 160 */           float f = getNormalizedVolume(isound, this.playingSoundPoolEntries.get(isound), category);
/*     */           
/* 162 */           if (f <= 0.0F) {
/* 163 */             stopSound(isound); continue;
/*     */           } 
/* 165 */           this.sndSystem.setVolume(s, f);
/*     */         } 
/*     */       } 
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void unloadSoundSystem() {
/* 176 */     if (this.loaded) {
/* 177 */       stopAllSounds();
/* 178 */       this.sndSystem.cleanup();
/* 179 */       this.loaded = false;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void stopAllSounds() {
/* 187 */     if (this.loaded) {
/* 188 */       for (String s : this.playingSounds.keySet()) {
/* 189 */         this.sndSystem.stop(s);
/*     */       }
/*     */       
/* 192 */       this.playingSounds.clear();
/* 193 */       this.delayedSounds.clear();
/* 194 */       this.tickableSounds.clear();
/* 195 */       this.categorySounds.clear();
/* 196 */       this.playingSoundPoolEntries.clear();
/* 197 */       this.playingSoundsStopTime.clear();
/*     */     } 
/*     */   }
/*     */   
/*     */   public void updateAllSounds() {
/* 202 */     this.playTime++;
/*     */     
/* 204 */     for (ITickableSound itickablesound : this.tickableSounds) {
/* 205 */       itickablesound.update();
/*     */       
/* 207 */       if (itickablesound.isDonePlaying()) {
/* 208 */         stopSound(itickablesound); continue;
/*     */       } 
/* 210 */       String s = this.invPlayingSounds.get(itickablesound);
/* 211 */       this.sndSystem.setVolume(s, getNormalizedVolume(itickablesound, this.playingSoundPoolEntries.get(itickablesound), this.sndHandler.getSound(itickablesound.getSoundLocation()).getSoundCategory()));
/* 212 */       this.sndSystem.setPitch(s, getNormalizedPitch(itickablesound, this.playingSoundPoolEntries.get(itickablesound)));
/* 213 */       this.sndSystem.setPosition(s, itickablesound.getXPosF(), itickablesound.getYPosF(), itickablesound.getZPosF());
/*     */     } 
/*     */ 
/*     */     
/* 217 */     Iterator<Map.Entry<String, ISound>> iterator = this.playingSounds.entrySet().iterator();
/*     */     
/* 219 */     while (iterator.hasNext()) {
/* 220 */       Map.Entry<String, ISound> entry = iterator.next();
/* 221 */       String s1 = entry.getKey();
/* 222 */       ISound isound = entry.getValue();
/*     */       
/* 224 */       if (!this.sndSystem.playing(s1)) {
/* 225 */         int i = ((Integer)this.playingSoundsStopTime.get(s1)).intValue();
/*     */         
/* 227 */         if (i <= this.playTime) {
/* 228 */           int j = isound.getRepeatDelay();
/*     */           
/* 230 */           if (isound.canRepeat() && j > 0) {
/* 231 */             this.delayedSounds.put(isound, Integer.valueOf(this.playTime + j));
/*     */           }
/*     */           
/* 234 */           iterator.remove();
/* 235 */           logger.debug(LOG_MARKER, "Removed channel {} because it's not playing anymore", new Object[] { s1 });
/* 236 */           this.sndSystem.removeSource(s1);
/* 237 */           this.playingSoundsStopTime.remove(s1);
/* 238 */           this.playingSoundPoolEntries.remove(isound);
/*     */           
/*     */           try {
/* 241 */             this.categorySounds.remove(this.sndHandler.getSound(isound.getSoundLocation()).getSoundCategory(), s1);
/* 242 */           } catch (RuntimeException runtimeException) {}
/*     */ 
/*     */ 
/*     */           
/* 246 */           if (isound instanceof ITickableSound) {
/* 247 */             this.tickableSounds.remove(isound);
/*     */           }
/*     */         } 
/*     */       } 
/*     */     } 
/*     */     
/* 253 */     Iterator<Map.Entry<ISound, Integer>> iterator1 = this.delayedSounds.entrySet().iterator();
/*     */     
/* 255 */     while (iterator1.hasNext()) {
/* 256 */       Map.Entry<ISound, Integer> entry1 = iterator1.next();
/*     */       
/* 258 */       if (this.playTime >= ((Integer)entry1.getValue()).intValue()) {
/* 259 */         ISound isound1 = entry1.getKey();
/*     */         
/* 261 */         if (isound1 instanceof ITickableSound) {
/* 262 */           ((ITickableSound)isound1).update();
/*     */         }
/*     */         
/* 265 */         playSound(isound1);
/* 266 */         iterator1.remove();
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isSoundPlaying(ISound sound) {
/* 275 */     if (!this.loaded) {
/* 276 */       return false;
/*     */     }
/* 278 */     String s = this.invPlayingSounds.get(sound);
/* 279 */     return (s == null) ? false : (!(!this.sndSystem.playing(s) && (!this.playingSoundsStopTime.containsKey(s) || ((Integer)this.playingSoundsStopTime.get(s)).intValue() > this.playTime)));
/*     */   }
/*     */ 
/*     */   
/*     */   public void stopSound(ISound sound) {
/* 284 */     if (this.loaded) {
/* 285 */       String s = this.invPlayingSounds.get(sound);
/*     */       
/* 287 */       if (s != null) {
/* 288 */         this.sndSystem.stop(s);
/*     */       }
/*     */     } 
/*     */   }
/*     */   
/*     */   public void playSound(ISound p_sound) {
/* 294 */     if (this.loaded) {
/* 295 */       if (this.sndSystem.getMasterVolume() <= 0.0F) {
/* 296 */         logger.debug(LOG_MARKER, "Skipped playing soundEvent: {}, master volume was zero", new Object[] { p_sound.getSoundLocation() });
/*     */       } else {
/* 298 */         SoundEventAccessorComposite soundeventaccessorcomposite = this.sndHandler.getSound(p_sound.getSoundLocation());
/*     */         
/* 300 */         if (soundeventaccessorcomposite == null) {
/* 301 */           logger.warn(LOG_MARKER, "Unable to play unknown soundEvent: {}", new Object[] { p_sound.getSoundLocation() });
/*     */         } else {
/* 303 */           SoundPoolEntry soundpoolentry = soundeventaccessorcomposite.cloneEntry();
/*     */           
/* 305 */           if (soundpoolentry == SoundHandler.missing_sound) {
/* 306 */             logger.warn(LOG_MARKER, "Unable to play empty soundEvent: {}", new Object[] { soundeventaccessorcomposite.getSoundEventLocation() });
/*     */           } else {
/* 308 */             float f = p_sound.getVolume();
/* 309 */             float f1 = 16.0F;
/*     */             
/* 311 */             if (f > 1.0F) {
/* 312 */               f1 *= f;
/*     */             }
/*     */             
/* 315 */             SoundCategory soundcategory = soundeventaccessorcomposite.getSoundCategory();
/* 316 */             float f2 = getNormalizedVolume(p_sound, soundpoolentry, soundcategory);
/* 317 */             double d0 = getNormalizedPitch(p_sound, soundpoolentry);
/* 318 */             ResourceLocation resourcelocation = soundpoolentry.getSoundPoolEntryLocation();
/*     */             
/* 320 */             if (f2 == 0.0F) {
/* 321 */               logger.debug(LOG_MARKER, "Skipped playing sound {}, volume was zero.", new Object[] { resourcelocation });
/*     */             } else {
/* 323 */               boolean flag = (p_sound.canRepeat() && p_sound.getRepeatDelay() == 0);
/* 324 */               String s = MathHelper.getRandomUuid((Random)ThreadLocalRandom.current()).toString();
/*     */               
/* 326 */               if (soundpoolentry.isStreamingSound()) {
/* 327 */                 this.sndSystem.newStreamingSource(false, s, getURLForSoundResource(resourcelocation), resourcelocation.toString(), flag, p_sound.getXPosF(), p_sound.getYPosF(), p_sound.getZPosF(), p_sound.getAttenuationType().getTypeInt(), f1);
/*     */               } else {
/* 329 */                 this.sndSystem.newSource(false, s, getURLForSoundResource(resourcelocation), resourcelocation.toString(), flag, p_sound.getXPosF(), p_sound.getYPosF(), p_sound.getZPosF(), p_sound.getAttenuationType().getTypeInt(), f1);
/*     */               } 
/*     */               
/* 332 */               logger.debug(LOG_MARKER, "Playing sound {} for event {} as channel {}", new Object[] { soundpoolentry.getSoundPoolEntryLocation(), soundeventaccessorcomposite.getSoundEventLocation(), s });
/* 333 */               this.sndSystem.setPitch(s, (float)d0);
/* 334 */               this.sndSystem.setVolume(s, f2);
/* 335 */               this.sndSystem.play(s);
/* 336 */               this.playingSoundsStopTime.put(s, Integer.valueOf(this.playTime + 20));
/* 337 */               this.playingSounds.put(s, p_sound);
/* 338 */               this.playingSoundPoolEntries.put(p_sound, soundpoolentry);
/*     */               
/* 340 */               if (soundcategory != SoundCategory.MASTER) {
/* 341 */                 this.categorySounds.put(soundcategory, s);
/*     */               }
/*     */               
/* 344 */               if (p_sound instanceof ITickableSound) {
/* 345 */                 this.tickableSounds.add((ITickableSound)p_sound);
/*     */               }
/*     */             } 
/*     */           } 
/*     */         } 
/*     */       } 
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private float getNormalizedPitch(ISound sound, SoundPoolEntry entry) {
/* 358 */     return (float)MathHelper.clamp_double(sound.getPitch() * entry.getPitch(), 0.5D, 2.0D);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private float getNormalizedVolume(ISound sound, SoundPoolEntry entry, SoundCategory category) {
/* 365 */     return (float)MathHelper.clamp_double(sound.getVolume() * entry.getVolume(), 0.0D, 1.0D) * getSoundCategoryVolume(category);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void pauseAllSounds() {
/* 372 */     for (String s : this.playingSounds.keySet()) {
/* 373 */       logger.debug(LOG_MARKER, "Pausing channel {}", new Object[] { s });
/* 374 */       this.sndSystem.pause(s);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void resumeAllSounds() {
/* 382 */     for (String s : this.playingSounds.keySet()) {
/* 383 */       logger.debug(LOG_MARKER, "Resuming channel {}", new Object[] { s });
/* 384 */       this.sndSystem.play(s);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void playDelayedSound(ISound sound, int delay) {
/* 392 */     this.delayedSounds.put(sound, Integer.valueOf(this.playTime + delay));
/*     */   }
/*     */   
/*     */   private static URL getURLForSoundResource(final ResourceLocation p_148612_0_) {
/* 396 */     String s = String.format("%s:%s:%s", new Object[] { "mcsounddomain", p_148612_0_.getResourceDomain(), p_148612_0_.getResourcePath() });
/* 397 */     URLStreamHandler urlstreamhandler = new URLStreamHandler() {
/*     */         protected URLConnection openConnection(URL p_openConnection_1_) {
/* 399 */           return new URLConnection(p_openConnection_1_)
/*     */             {
/*     */               public void connect() throws IOException {}
/*     */               
/*     */               public InputStream getInputStream() throws IOException {
/* 404 */                 return Minecraft.getMinecraft().getResourceManager().getResource(p_148612_0_).getInputStream();
/*     */               }
/*     */             };
/*     */         }
/*     */       };
/*     */     
/*     */     try {
/* 411 */       return new URL(null, s, urlstreamhandler);
/* 412 */     } catch (MalformedURLException var4) {
/* 413 */       throw new Error("TODO: Sanely handle url exception! :D");
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setListener(EntityPlayer player, float p_148615_2_) {
/* 421 */     if (this.loaded && player != null) {
/* 422 */       float f = player.prevRotationPitch + (player.rotationPitch - player.prevRotationPitch) * p_148615_2_;
/* 423 */       float f1 = player.prevRotationYaw + (player.rotationYaw - player.prevRotationYaw) * p_148615_2_;
/* 424 */       double d0 = player.prevPosX + (player.posX - player.prevPosX) * p_148615_2_;
/* 425 */       double d1 = player.prevPosY + (player.posY - player.prevPosY) * p_148615_2_ + player.getEyeHeight();
/* 426 */       double d2 = player.prevPosZ + (player.posZ - player.prevPosZ) * p_148615_2_;
/* 427 */       float f2 = MathHelper.cos((f1 + 90.0F) * 0.017453292F);
/* 428 */       float f3 = MathHelper.sin((f1 + 90.0F) * 0.017453292F);
/* 429 */       float f4 = MathHelper.cos(-f * 0.017453292F);
/* 430 */       float f5 = MathHelper.sin(-f * 0.017453292F);
/* 431 */       float f6 = MathHelper.cos((-f + 90.0F) * 0.017453292F);
/* 432 */       float f7 = MathHelper.sin((-f + 90.0F) * 0.017453292F);
/* 433 */       float f8 = f2 * f4;
/* 434 */       float f9 = f3 * f4;
/* 435 */       float f10 = f2 * f6;
/* 436 */       float f11 = f3 * f6;
/* 437 */       this.sndSystem.setListenerPosition((float)d0, (float)d1, (float)d2);
/* 438 */       this.sndSystem.setListenerOrientation(f8, f5, f9, f10, f7, f11);
/*     */     } 
/*     */   }
/*     */   
/*     */   class SoundSystemStarterThread
/*     */     extends SoundSystem {
/*     */     private SoundSystemStarterThread() {}
/*     */     
/*     */     public boolean playing(String p_playing_1_) {
/* 447 */       synchronized (SoundSystemConfig.THREAD_SYNC) {
/* 448 */         if (this.soundLibrary == null) {
/* 449 */           return false;
/*     */         }
/* 451 */         Source source = (Source)this.soundLibrary.getSources().get(p_playing_1_);
/* 452 */         return (source == null) ? false : (!(!source.playing() && !source.paused() && !source.preLoad));
/*     */       } 
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\minecraft\client\audio\SoundManager.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */