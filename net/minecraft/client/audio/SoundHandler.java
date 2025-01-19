/*     */ package net.minecraft.client.audio;
/*     */ 
/*     */ import com.google.common.collect.Lists;
/*     */ import com.google.gson.Gson;
/*     */ import com.google.gson.GsonBuilder;
/*     */ import java.io.FileNotFoundException;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import java.io.InputStreamReader;
/*     */ import java.lang.reflect.ParameterizedType;
/*     */ import java.lang.reflect.Type;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import java.util.Random;
/*     */ import net.minecraft.client.resources.IResource;
/*     */ import net.minecraft.client.resources.IResourceManager;
/*     */ import net.minecraft.client.resources.IResourceManagerReloadListener;
/*     */ import net.minecraft.client.settings.GameSettings;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.util.ITickable;
/*     */ import net.minecraft.util.ResourceLocation;
/*     */ import org.apache.commons.io.IOUtils;
/*     */ import org.apache.commons.lang3.ArrayUtils;
/*     */ import org.apache.logging.log4j.LogManager;
/*     */ import org.apache.logging.log4j.Logger;
/*     */ 
/*     */ 
/*     */ 
/*     */ public class SoundHandler
/*     */   implements IResourceManagerReloadListener, ITickable
/*     */ {
/*  32 */   private static final Logger logger = LogManager.getLogger();
/*  33 */   private static final Gson GSON = (new GsonBuilder()).registerTypeAdapter(SoundList.class, new SoundListSerializer()).create();
/*  34 */   private static final ParameterizedType TYPE = new ParameterizedType() {
/*     */       public Type[] getActualTypeArguments() {
/*  36 */         return new Type[] { String.class, SoundList.class };
/*     */       }
/*     */       
/*     */       public Type getRawType() {
/*  40 */         return Map.class;
/*     */       }
/*     */       
/*     */       public Type getOwnerType() {
/*  44 */         return null;
/*     */       }
/*     */     };
/*  47 */   public static final SoundPoolEntry missing_sound = new SoundPoolEntry(new ResourceLocation("meta:missing_sound"), 0.0D, 0.0D, false);
/*  48 */   private final SoundRegistry sndRegistry = new SoundRegistry();
/*     */   private final SoundManager sndManager;
/*     */   private final IResourceManager mcResourceManager;
/*     */   
/*     */   public SoundHandler(IResourceManager manager, GameSettings gameSettingsIn) {
/*  53 */     this.mcResourceManager = manager;
/*  54 */     this.sndManager = new SoundManager(this, gameSettingsIn);
/*     */   }
/*     */   
/*     */   public void onResourceManagerReload(IResourceManager resourceManager) {
/*  58 */     this.sndManager.reloadSoundSystem();
/*  59 */     this.sndRegistry.clearMap();
/*     */     
/*  61 */     for (String s : resourceManager.getResourceDomains()) {
/*     */       try {
/*  63 */         for (IResource iresource : resourceManager.getAllResources(new ResourceLocation(s, "sounds.json"))) {
/*     */           try {
/*  65 */             Map<String, SoundList> map = getSoundMap(iresource.getInputStream());
/*     */             
/*  67 */             for (Map.Entry<String, SoundList> entry : map.entrySet()) {
/*  68 */               loadSoundResource(new ResourceLocation(s, entry.getKey()), entry.getValue());
/*     */             }
/*  70 */           } catch (RuntimeException runtimeexception) {
/*  71 */             logger.warn("Invalid sounds.json", runtimeexception);
/*     */           } 
/*     */         } 
/*  74 */       } catch (IOException iOException) {}
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected Map<String, SoundList> getSoundMap(InputStream stream) {
/*     */     Map<String, SoundList> map;
/*     */     try {
/*  84 */       map = (Map)GSON.fromJson(new InputStreamReader(stream), TYPE);
/*     */     } finally {
/*  86 */       IOUtils.closeQuietly(stream);
/*     */     } 
/*     */     
/*  89 */     return map;
/*     */   }
/*     */   private void loadSoundResource(ResourceLocation location, SoundList sounds) {
/*     */     SoundEventAccessorComposite soundeventaccessorcomposite;
/*  93 */     boolean flag = !this.sndRegistry.containsKey(location);
/*     */ 
/*     */     
/*  96 */     if (!flag && !sounds.canReplaceExisting()) {
/*  97 */       soundeventaccessorcomposite = (SoundEventAccessorComposite)this.sndRegistry.getObject(location);
/*     */     } else {
/*  99 */       if (!flag) {
/* 100 */         logger.debug("Replaced sound event location {}", new Object[] { location });
/*     */       }
/*     */       
/* 103 */       soundeventaccessorcomposite = new SoundEventAccessorComposite(location, 1.0D, 1.0D, sounds.getSoundCategory());
/* 104 */       this.sndRegistry.registerSound(soundeventaccessorcomposite);
/*     */     } 
/*     */     
/* 107 */     for (SoundList.SoundEntry soundlist$soundentry : sounds.getSoundList()) {
/* 108 */       ISoundEventAccessor<SoundPoolEntry> isoundeventaccessor; ResourceLocation resourcelocation1; InputStream inputstream; String s = soundlist$soundentry.getSoundEntryName();
/* 109 */       ResourceLocation resourcelocation = new ResourceLocation(s);
/* 110 */       String s1 = s.contains(":") ? resourcelocation.getResourceDomain() : location.getResourceDomain();
/*     */ 
/*     */       
/* 113 */       switch (soundlist$soundentry.getSoundEntryType()) {
/*     */         case null:
/* 115 */           resourcelocation1 = new ResourceLocation(s1, "sounds/" + resourcelocation.getResourcePath() + ".ogg");
/* 116 */           inputstream = null;
/*     */           
/*     */           try {
/* 119 */             inputstream = this.mcResourceManager.getResource(resourcelocation1).getInputStream();
/* 120 */           } catch (FileNotFoundException var18) {
/* 121 */             logger.warn("File {} does not exist, cannot add it to event {}", new Object[] { resourcelocation1, location });
/*     */             continue;
/* 123 */           } catch (IOException ioexception) {
/* 124 */             logger.warn("Could not load sound file " + resourcelocation1 + ", cannot add it to event " + location, ioexception);
/*     */             continue;
/*     */           } finally {
/* 127 */             IOUtils.closeQuietly(inputstream);
/*     */           } 
/*     */           
/* 130 */           isoundeventaccessor = new SoundEventAccessor(new SoundPoolEntry(resourcelocation1, soundlist$soundentry.getSoundEntryPitch(), soundlist$soundentry.getSoundEntryVolume(), soundlist$soundentry.isStreaming()), soundlist$soundentry.getSoundEntryWeight());
/*     */           break;
/*     */         
/*     */         case SOUND_EVENT:
/* 134 */           isoundeventaccessor = new ISoundEventAccessor<SoundPoolEntry>(s1, soundlist$soundentry) {
/*     */               final ResourceLocation field_148726_a;
/*     */               
/*     */               public int getWeight() {
/* 138 */                 SoundEventAccessorComposite soundeventaccessorcomposite1 = (SoundEventAccessorComposite)SoundHandler.this.sndRegistry.getObject(this.field_148726_a);
/* 139 */                 return (soundeventaccessorcomposite1 == null) ? 0 : soundeventaccessorcomposite1.getWeight();
/*     */               }
/*     */               
/*     */               public SoundPoolEntry cloneEntry() {
/* 143 */                 SoundEventAccessorComposite soundeventaccessorcomposite1 = (SoundEventAccessorComposite)SoundHandler.this.sndRegistry.getObject(this.field_148726_a);
/* 144 */                 return (soundeventaccessorcomposite1 == null) ? SoundHandler.missing_sound : soundeventaccessorcomposite1.cloneEntry();
/*     */               }
/*     */             };
/*     */           break;
/*     */         
/*     */         default:
/* 150 */           throw new IllegalStateException("IN YOU FACE");
/*     */       } 
/*     */       
/* 153 */       soundeventaccessorcomposite.addSoundToEventPool(isoundeventaccessor);
/*     */     } 
/*     */   }
/*     */   
/*     */   public SoundEventAccessorComposite getSound(ResourceLocation location) {
/* 158 */     return (SoundEventAccessorComposite)this.sndRegistry.getObject(location);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void playSound(ISound sound) {
/* 165 */     this.sndManager.playSound(sound);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void playDelayedSound(ISound sound, int delay) {
/* 172 */     this.sndManager.playDelayedSound(sound, delay);
/*     */   }
/*     */   
/*     */   public void setListener(EntityPlayer player, float p_147691_2_) {
/* 176 */     this.sndManager.setListener(player, p_147691_2_);
/*     */   }
/*     */   
/*     */   public void pauseSounds() {
/* 180 */     this.sndManager.pauseAllSounds();
/*     */   }
/*     */   
/*     */   public void stopSounds() {
/* 184 */     this.sndManager.stopAllSounds();
/*     */   }
/*     */   
/*     */   public void unloadSounds() {
/* 188 */     this.sndManager.unloadSoundSystem();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void update() {
/* 195 */     this.sndManager.updateAllSounds();
/*     */   }
/*     */   
/*     */   public void resumeSounds() {
/* 199 */     this.sndManager.resumeAllSounds();
/*     */   }
/*     */   
/*     */   public void setSoundLevel(SoundCategory category, float volume) {
/* 203 */     if (category == SoundCategory.MASTER && volume <= 0.0F) {
/* 204 */       stopSounds();
/*     */     }
/*     */     
/* 207 */     this.sndManager.setSoundCategoryVolume(category, volume);
/*     */   }
/*     */   
/*     */   public void stopSound(ISound p_147683_1_) {
/* 211 */     this.sndManager.stopSound(p_147683_1_);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public SoundEventAccessorComposite getRandomSoundFromCategories(SoundCategory... categories) {
/* 218 */     List<SoundEventAccessorComposite> list = Lists.newArrayList();
/*     */     
/* 220 */     for (ResourceLocation resourcelocation : this.sndRegistry.getKeys()) {
/* 221 */       SoundEventAccessorComposite soundeventaccessorcomposite = (SoundEventAccessorComposite)this.sndRegistry.getObject(resourcelocation);
/*     */       
/* 223 */       if (ArrayUtils.contains((Object[])categories, soundeventaccessorcomposite.getSoundCategory())) {
/* 224 */         list.add(soundeventaccessorcomposite);
/*     */       }
/*     */     } 
/*     */     
/* 228 */     if (list.isEmpty()) {
/* 229 */       return null;
/*     */     }
/* 231 */     return list.get((new Random()).nextInt(list.size()));
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isSoundPlaying(ISound sound) {
/* 236 */     return this.sndManager.isSoundPlaying(sound);
/*     */   }
/*     */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\minecraft\client\audio\SoundHandler.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */