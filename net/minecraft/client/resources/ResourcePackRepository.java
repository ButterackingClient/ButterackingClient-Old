/*     */ package net.minecraft.client.resources;
/*     */ 
/*     */ import com.google.common.collect.ImmutableList;
/*     */ import com.google.common.collect.Lists;
/*     */ import com.google.common.hash.Hashing;
/*     */ import com.google.common.io.Files;
/*     */ import com.google.common.util.concurrent.FutureCallback;
/*     */ import com.google.common.util.concurrent.Futures;
/*     */ import com.google.common.util.concurrent.ListenableFuture;
/*     */ import com.google.common.util.concurrent.SettableFuture;
/*     */ import java.awt.image.BufferedImage;
/*     */ import java.io.Closeable;
/*     */ import java.io.File;
/*     */ import java.io.FileFilter;
/*     */ import java.io.IOException;
/*     */ import java.util.Arrays;
/*     */ import java.util.Collections;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import java.util.concurrent.Future;
/*     */ import java.util.concurrent.locks.ReentrantLock;
/*     */ import net.minecraft.client.Minecraft;
/*     */ import net.minecraft.client.gui.GuiScreen;
/*     */ import net.minecraft.client.gui.GuiScreenWorking;
/*     */ import net.minecraft.client.renderer.texture.DynamicTexture;
/*     */ import net.minecraft.client.renderer.texture.TextureManager;
/*     */ import net.minecraft.client.resources.data.IMetadataSerializer;
/*     */ import net.minecraft.client.resources.data.PackMetadataSection;
/*     */ import net.minecraft.client.settings.GameSettings;
/*     */ import net.minecraft.util.EnumChatFormatting;
/*     */ import net.minecraft.util.HttpUtil;
/*     */ import net.minecraft.util.IProgressUpdate;
/*     */ import net.minecraft.util.ResourceLocation;
/*     */ import org.apache.commons.io.FileUtils;
/*     */ import org.apache.commons.io.IOUtils;
/*     */ import org.apache.commons.io.comparator.LastModifiedFileComparator;
/*     */ import org.apache.commons.io.filefilter.TrueFileFilter;
/*     */ import org.apache.logging.log4j.LogManager;
/*     */ import org.apache.logging.log4j.Logger;
/*     */ 
/*     */ public class ResourcePackRepository {
/*  43 */   private static final Logger logger = LogManager.getLogger();
/*  44 */   private static final FileFilter resourcePackFilter = new FileFilter() {
/*     */       public boolean accept(File p_accept_1_) {
/*  46 */         boolean flag = (p_accept_1_.isFile() && p_accept_1_.getName().endsWith(".zip"));
/*  47 */         boolean flag1 = (p_accept_1_.isDirectory() && (new File(p_accept_1_, "pack.mcmeta")).isFile());
/*  48 */         return !(!flag && !flag1);
/*     */       }
/*     */     };
/*     */   private final File dirResourcepacks;
/*     */   public final IResourcePack rprDefaultResourcePack;
/*     */   private final File dirServerResourcepacks;
/*     */   public final IMetadataSerializer rprMetadataSerializer;
/*     */   private IResourcePack resourcePackInstance;
/*  56 */   private final ReentrantLock lock = new ReentrantLock();
/*     */   private ListenableFuture<Object> downloadingPacks;
/*  58 */   private List<Entry> repositoryEntriesAll = Lists.newArrayList();
/*  59 */   public List<Entry> repositoryEntries = Lists.newArrayList();
/*     */   
/*     */   public ResourcePackRepository(File dirResourcepacksIn, File dirServerResourcepacksIn, IResourcePack rprDefaultResourcePackIn, IMetadataSerializer rprMetadataSerializerIn, GameSettings settings) {
/*  62 */     this.dirResourcepacks = dirResourcepacksIn;
/*  63 */     this.dirServerResourcepacks = dirServerResourcepacksIn;
/*  64 */     this.rprDefaultResourcePack = rprDefaultResourcePackIn;
/*  65 */     this.rprMetadataSerializer = rprMetadataSerializerIn;
/*  66 */     fixDirResourcepacks();
/*  67 */     updateRepositoryEntriesAll();
/*  68 */     Iterator<String> iterator = settings.resourcePacks.iterator();
/*     */     
/*  70 */     while (iterator.hasNext()) {
/*  71 */       String s = iterator.next();
/*     */       
/*  73 */       for (Entry resourcepackrepository$entry : this.repositoryEntriesAll) {
/*  74 */         if (resourcepackrepository$entry.getResourcePackName().equals(s)) {
/*  75 */           if (resourcepackrepository$entry.func_183027_f() == 1 || settings.incompatibleResourcePacks.contains(resourcepackrepository$entry.getResourcePackName())) {
/*  76 */             this.repositoryEntries.add(resourcepackrepository$entry);
/*     */             
/*     */             break;
/*     */           } 
/*  80 */           iterator.remove();
/*  81 */           logger.warn("Removed selected resource pack {} because it's no longer compatible", new Object[] { resourcepackrepository$entry.getResourcePackName() });
/*     */         } 
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   private void fixDirResourcepacks() {
/*  88 */     if (this.dirResourcepacks.exists()) {
/*  89 */       if (!this.dirResourcepacks.isDirectory() && (!this.dirResourcepacks.delete() || !this.dirResourcepacks.mkdirs())) {
/*  90 */         logger.warn("Unable to recreate resourcepack folder, it exists but is not a directory: " + this.dirResourcepacks);
/*     */       }
/*  92 */     } else if (!this.dirResourcepacks.mkdirs()) {
/*  93 */       logger.warn("Unable to create resourcepack folder: " + this.dirResourcepacks);
/*     */     } 
/*     */   }
/*     */   
/*     */   private List<File> getResourcePackFiles() {
/*  98 */     return this.dirResourcepacks.isDirectory() ? Arrays.<File>asList(this.dirResourcepacks.listFiles(resourcePackFilter)) : Collections.<File>emptyList();
/*     */   }
/*     */   
/*     */   public void updateRepositoryEntriesAll() {
/* 102 */     List<Entry> list = Lists.newArrayList();
/*     */     
/* 104 */     for (File file1 : getResourcePackFiles()) {
/* 105 */       Entry resourcepackrepository$entry = new Entry(file1, null);
/*     */       
/* 107 */       if (!this.repositoryEntriesAll.contains(resourcepackrepository$entry)) {
/*     */         try {
/* 109 */           resourcepackrepository$entry.updateResourcePack();
/* 110 */           list.add(resourcepackrepository$entry);
/* 111 */         } catch (Exception var61) {
/* 112 */           list.remove(resourcepackrepository$entry);
/*     */         }  continue;
/*     */       } 
/* 115 */       int i = this.repositoryEntriesAll.indexOf(resourcepackrepository$entry);
/*     */       
/* 117 */       if (i > -1 && i < this.repositoryEntriesAll.size()) {
/* 118 */         list.add(this.repositoryEntriesAll.get(i));
/*     */       }
/*     */     } 
/*     */ 
/*     */     
/* 123 */     this.repositoryEntriesAll.removeAll(list);
/*     */     
/* 125 */     for (Entry resourcepackrepository$entry1 : this.repositoryEntriesAll) {
/* 126 */       resourcepackrepository$entry1.closeResourcePack();
/*     */     }
/*     */     
/* 129 */     this.repositoryEntriesAll = list;
/*     */   }
/*     */   
/*     */   public List<Entry> getRepositoryEntriesAll() {
/* 133 */     return (List<Entry>)ImmutableList.copyOf(this.repositoryEntriesAll);
/*     */   }
/*     */   
/*     */   public List<Entry> getRepositoryEntries() {
/* 137 */     return (List<Entry>)ImmutableList.copyOf(this.repositoryEntries);
/*     */   }
/*     */   
/*     */   public void setRepositories(List<Entry> repositories) {
/* 141 */     this.repositoryEntries.clear();
/* 142 */     this.repositoryEntries.addAll(repositories);
/*     */   }
/*     */   
/*     */   public File getDirResourcepacks() {
/* 146 */     return this.dirResourcepacks;
/*     */   }
/*     */ 
/*     */   
/*     */   public ListenableFuture<Object> downloadResourcePack(String url, String hash) {
/*     */     String s;
/* 152 */     if (hash.matches("^[a-f0-9]{40}$")) {
/* 153 */       s = hash;
/*     */     } else {
/* 155 */       s = "legacy";
/*     */     } 
/*     */     
/* 158 */     final File file1 = new File(this.dirServerResourcepacks, s);
/* 159 */     this.lock.lock();
/*     */     
/*     */     try {
/* 162 */       clearResourcePack();
/*     */       
/* 164 */       if (file1.exists() && hash.length() == 40) {
/*     */         try {
/* 166 */           String s1 = Hashing.sha1().hashBytes(Files.toByteArray(file1)).toString();
/*     */           
/* 168 */           if (s1.equals(hash)) {
/* 169 */             ListenableFuture<Object> listenablefuture2 = setResourcePackInstance(file1);
/* 170 */             ListenableFuture<Object> listenablefuture3 = listenablefuture2;
/* 171 */             return listenablefuture3;
/*     */           } 
/*     */           
/* 174 */           logger.warn("File " + file1 + " had wrong hash (expected " + hash + ", found " + s1 + "). Deleting it.");
/* 175 */           FileUtils.deleteQuietly(file1);
/* 176 */         } catch (IOException ioexception) {
/* 177 */           logger.warn("File " + file1 + " couldn't be hashed. Deleting it.", ioexception);
/* 178 */           FileUtils.deleteQuietly(file1);
/*     */         } 
/*     */       }
/*     */       
/* 182 */       deleteOldServerResourcesPacks();
/* 183 */       final GuiScreenWorking guiscreenworking = new GuiScreenWorking();
/* 184 */       Map<String, String> map = Minecraft.getSessionInfo();
/* 185 */       final Minecraft minecraft = Minecraft.getMinecraft();
/* 186 */       Futures.getUnchecked((Future)minecraft.addScheduledTask(new Runnable() {
/*     */               public void run() {
/* 188 */                 minecraft.displayGuiScreen((GuiScreen)guiscreenworking);
/*     */               }
/*     */             }));
/* 191 */       final SettableFuture<Object> settablefuture = SettableFuture.create();
/* 192 */       this.downloadingPacks = HttpUtil.downloadResourcePack(file1, url, map, 52428800, (IProgressUpdate)guiscreenworking, minecraft.getProxy());
/* 193 */       Futures.addCallback(this.downloadingPacks, new FutureCallback<Object>() {
/*     */             public void onSuccess(Object p_onSuccess_1_) {
/* 195 */               ResourcePackRepository.this.setResourcePackInstance(file1);
/* 196 */               settablefuture.set(null);
/*     */             }
/*     */             
/*     */             public void onFailure(Throwable p_onFailure_1_) {
/* 200 */               settablefuture.setException(p_onFailure_1_);
/*     */             }
/*     */           });
/* 203 */       ListenableFuture<Object> listenablefuture = this.downloadingPacks;
/* 204 */       ListenableFuture<Object> listenablefuture11 = listenablefuture;
/* 205 */       return listenablefuture11;
/*     */     } finally {
/* 207 */       this.lock.unlock();
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void deleteOldServerResourcesPacks() {
/* 215 */     List<File> list = Lists.newArrayList(FileUtils.listFiles(this.dirServerResourcepacks, TrueFileFilter.TRUE, null));
/* 216 */     Collections.sort(list, LastModifiedFileComparator.LASTMODIFIED_REVERSE);
/* 217 */     int i = 0;
/*     */     
/* 219 */     for (File file1 : list) {
/* 220 */       if (i++ >= 10) {
/* 221 */         logger.info("Deleting old server resource pack " + file1.getName());
/* 222 */         FileUtils.deleteQuietly(file1);
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   public ListenableFuture<Object> setResourcePackInstance(File resourceFile) {
/* 228 */     this.resourcePackInstance = new FileResourcePack(resourceFile);
/* 229 */     return Minecraft.getMinecraft().scheduleResourcesRefresh();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public IResourcePack getResourcePackInstance() {
/* 236 */     return this.resourcePackInstance;
/*     */   }
/*     */   
/*     */   public void clearResourcePack() {
/* 240 */     this.lock.lock();
/*     */     
/*     */     try {
/* 243 */       if (this.downloadingPacks != null) {
/* 244 */         this.downloadingPacks.cancel(true);
/*     */       }
/*     */       
/* 247 */       this.downloadingPacks = null;
/*     */       
/* 249 */       if (this.resourcePackInstance != null) {
/* 250 */         this.resourcePackInstance = null;
/* 251 */         Minecraft.getMinecraft().scheduleResourcesRefresh();
/*     */       } 
/*     */     } finally {
/* 254 */       this.lock.unlock();
/*     */     } 
/*     */   }
/*     */   
/*     */   public class Entry {
/*     */     private final File resourcePackFile;
/*     */     private IResourcePack reResourcePack;
/*     */     private PackMetadataSection rePackMetadataSection;
/*     */     private BufferedImage texturePackIcon;
/*     */     private ResourceLocation locationTexturePackIcon;
/*     */     
/*     */     private Entry(File resourcePackFileIn) {
/* 266 */       this.resourcePackFile = resourcePackFileIn;
/*     */     }
/*     */     
/*     */     public void updateResourcePack() throws IOException {
/* 270 */       this.reResourcePack = this.resourcePackFile.isDirectory() ? new FolderResourcePack(this.resourcePackFile) : new FileResourcePack(this.resourcePackFile);
/* 271 */       this.rePackMetadataSection = this.reResourcePack.<PackMetadataSection>getPackMetadata(ResourcePackRepository.this.rprMetadataSerializer, "pack");
/*     */       
/*     */       try {
/* 274 */         this.texturePackIcon = this.reResourcePack.getPackImage();
/* 275 */       } catch (IOException iOException) {}
/*     */ 
/*     */ 
/*     */       
/* 279 */       if (this.texturePackIcon == null) {
/* 280 */         this.texturePackIcon = ResourcePackRepository.this.rprDefaultResourcePack.getPackImage();
/*     */       }
/*     */       
/* 283 */       closeResourcePack();
/*     */     }
/*     */     
/*     */     public void bindTexturePackIcon(TextureManager textureManagerIn) {
/* 287 */       if (this.locationTexturePackIcon == null) {
/* 288 */         this.locationTexturePackIcon = textureManagerIn.getDynamicTextureLocation("texturepackicon", new DynamicTexture(this.texturePackIcon));
/*     */       }
/*     */       
/* 291 */       textureManagerIn.bindTexture(this.locationTexturePackIcon);
/*     */     }
/*     */     
/*     */     public void closeResourcePack() {
/* 295 */       if (this.reResourcePack instanceof Closeable) {
/* 296 */         IOUtils.closeQuietly((Closeable)this.reResourcePack);
/*     */       }
/*     */     }
/*     */     
/*     */     public IResourcePack getResourcePack() {
/* 301 */       return this.reResourcePack;
/*     */     }
/*     */     
/*     */     public String getResourcePackName() {
/* 305 */       return this.reResourcePack.getPackName();
/*     */     }
/*     */     
/*     */     public String getTexturePackDescription() {
/* 309 */       return (this.rePackMetadataSection == null) ? (EnumChatFormatting.RED + "Invalid pack.mcmeta (or missing 'pack' section)") : this.rePackMetadataSection.getPackDescription().getFormattedText();
/*     */     }
/*     */     
/*     */     public int func_183027_f() {
/* 313 */       return this.rePackMetadataSection.getPackFormat();
/*     */     }
/*     */     
/*     */     public boolean equals(Object p_equals_1_) {
/* 317 */       return (this == p_equals_1_) ? true : ((p_equals_1_ instanceof Entry) ? toString().equals(p_equals_1_.toString()) : false);
/*     */     }
/*     */     
/*     */     public int hashCode() {
/* 321 */       return toString().hashCode();
/*     */     }
/*     */     
/*     */     public String toString() {
/* 325 */       return String.format("%s:%s:%d", new Object[] { this.resourcePackFile.getName(), this.resourcePackFile.isDirectory() ? "folder" : "zip", Long.valueOf(this.resourcePackFile.lastModified()) });
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\minecraft\client\resources\ResourcePackRepository.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */