/*     */ package client.texfix;
/*     */ 
/*     */ import client.Client;
/*     */ import client.event.EventManager;
/*     */ import client.event.SubscribeEvent;
/*     */ import client.event.impl.ClientTickEvent;
/*     */ import java.awt.image.BufferedImage;
/*     */ import java.lang.reflect.Field;
/*     */ import java.text.DecimalFormat;
/*     */ import java.util.LinkedList;
/*     */ import java.util.Map;
/*     */ import net.minecraft.client.Minecraft;
/*     */ import net.minecraft.client.renderer.texture.TextureAtlasSprite;
/*     */ import net.minecraft.client.renderer.texture.TextureMap;
/*     */ import net.minecraft.client.renderer.texture.TextureUtil;
/*     */ import net.minecraft.client.resources.IReloadableResourceManager;
/*     */ import net.minecraft.client.resources.IResource;
/*     */ import net.minecraft.client.resources.IResourceManager;
/*     */ import net.minecraft.client.resources.IResourceManagerReloadListener;
/*     */ import net.minecraft.util.ResourceLocation;
/*     */ import org.apache.logging.log4j.LogManager;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class TextureFix
/*     */ {
/*     */   public static final DecimalFormat DECIMALFORMAT;
/*     */   public static LinkedList<UnloadEntry> toUnload;
/*  32 */   public static TextureFix INSTANCE = new TextureFix();
/*     */ 
/*     */ 
/*     */   
/*     */   public void onPostInit() {
/*  37 */     ((IReloadableResourceManager)Minecraft.getMinecraft().getResourceManager()).registerReloadListener(new IResourceManagerReloadListener() {
/*     */           public void onResourceManagerReload(IResourceManager resourceManager) {
/*  39 */             TextureMap map = Minecraft.getMinecraft().getTextureMapBlocks();
/*  40 */             if (map == null) {
/*  41 */               System.out.println("map is null");
/*     */               return;
/*     */             } 
/*  44 */             long bytes = 0L;
/*  45 */             int fixed = 0;
/*  46 */             for (TextureAtlasSprite sprite : TextureFix.this.getData(map).values()) {
/*  47 */               if (!sprite.hasAnimationMetadata()) {
/*  48 */                 fixed++;
/*  49 */                 bytes += (sprite.getIconWidth() * sprite.getIconHeight() * 4);
/*  50 */                 sprite.setFramesTextureData(new FixList(sprite));
/*     */               } 
/*     */             } 
/*  53 */             bytes *= (1 + (Minecraft.getMinecraft()).gameSettings.mipmapLevels);
/*  54 */             LogManager.getLogger("TexFix").info("Fixed Textures: " + fixed + " Saved: " + TextureFix.DECIMALFORMAT.format(TextureFix.this.toMB(bytes)) + "MB (" + bytes + " bytes)");
/*     */           }
/*     */         });
/*     */     
/*  58 */     EventManager.register(this);
/*     */   }
/*     */   
/*     */   public static void markForUnload(TextureAtlasSprite sprite) {
/*  62 */     toUnload.add(new UnloadEntry(sprite));
/*     */   }
/*     */ 
/*     */   
/*     */   @SubscribeEvent
/*     */   public void onClientTick(ClientTickEvent evt) {
/*  68 */     if ((Client.getInstance()).hudManager.texFix.isEnabled() && 
/*  69 */       toUnload.size() > 0 && ((UnloadEntry)toUnload.getFirst()).unload()) {
/*  70 */       toUnload.removeFirst();
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   private long toMB(long data) {
/*  76 */     return data / 1024L / 1024L;
/*     */   }
/*     */   
/*     */   Map<String, TextureAtlasSprite> getData(TextureMap map) {
/*     */     try {
/*  81 */       Field[] fields = map.getClass().getDeclaredFields();
/*  82 */       for (int i = 0; i < fields.length; i++) {
/*  83 */         Field field = fields[i];
/*  84 */         if (field.getType() == Map.class) {
/*  85 */           field.setAccessible(true);
/*  86 */           return (Map<String, TextureAtlasSprite>)field.get(map);
/*     */         }
/*     */       
/*     */       } 
/*  90 */     } catch (Exception e) {
/*  91 */       e.printStackTrace();
/*     */     } 
/*  93 */     return null;
/*     */   }
/*     */   
/*     */   public static void reloadTextureData(TextureAtlasSprite sprite) {
/*  97 */     Minecraft mc = Minecraft.getMinecraft();
/*  98 */     reloadTextureData(sprite, mc.getResourceManager(), mc.getTextureMapBlocks());
/*     */   }
/*     */   
/*     */   private static void reloadTextureData(TextureAtlasSprite sprite, IResourceManager manager, TextureMap map) {
/* 102 */     ResourceLocation location = getResourceLocation(sprite, map);
/* 103 */     if (sprite.hasCustomLoader(manager, location)) {
/* 104 */       sprite.load(manager, location);
/*     */     } else {
/*     */       
/* 107 */       IResource resource = null;
/*     */       try {
/* 109 */         resource = manager.getResource(location);
/* 110 */         BufferedImage[] images = new BufferedImage[1 + (Minecraft.getMinecraft()).gameSettings.mipmapLevels];
/* 111 */         images[0] = TextureUtil.readBufferedImage(resource.getInputStream());
/* 112 */         sprite.loadSprite(images, null);
/*     */       }
/* 114 */       catch (Exception e) {
/* 115 */         e.printStackTrace();
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   private static ResourceLocation getResourceLocation(TextureAtlasSprite p_184396_1_, TextureMap map) {
/* 121 */     ResourceLocation resourcelocation = new ResourceLocation(p_184396_1_.getIconName());
/* 122 */     return new ResourceLocation(resourcelocation.getResourceDomain(), String.format("%s/%s%s", new Object[] { "texures", resourcelocation.getResourcePath(), ".png" }));
/*     */   }
/*     */   
/*     */   static {
/* 126 */     DECIMALFORMAT = new DecimalFormat("#.###");
/* 127 */     toUnload = new LinkedList<>();
/*     */   }
/*     */   
/*     */   public static class UnloadEntry
/*     */   {
/*     */     int count;
/*     */     TextureAtlasSprite sprite;
/*     */     
/*     */     public UnloadEntry(TextureAtlasSprite entry) {
/* 136 */       this.count = 2;
/* 137 */       this.sprite = entry;
/*     */     }
/*     */     
/*     */     public boolean unload() {
/* 141 */       this.count--;
/* 142 */       if (this.count <= 0) {
/* 143 */         this.sprite.clearFramesTextureData();
/* 144 */         return true;
/*     */       } 
/* 146 */       return false;
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\client\texfix\TextureFix.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */