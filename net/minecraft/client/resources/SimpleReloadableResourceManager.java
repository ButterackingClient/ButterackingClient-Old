/*    */ package net.minecraft.client.resources;
/*    */ 
/*    */ import com.google.common.base.Function;
/*    */ import com.google.common.base.Joiner;
/*    */ import com.google.common.collect.Iterables;
/*    */ import com.google.common.collect.Lists;
/*    */ import com.google.common.collect.Maps;
/*    */ import com.google.common.collect.Sets;
/*    */ import java.io.FileNotFoundException;
/*    */ import java.io.IOException;
/*    */ import java.util.List;
/*    */ import java.util.Map;
/*    */ import java.util.Set;
/*    */ import net.minecraft.client.resources.data.IMetadataSerializer;
/*    */ import net.minecraft.util.ResourceLocation;
/*    */ import org.apache.logging.log4j.LogManager;
/*    */ import org.apache.logging.log4j.Logger;
/*    */ 
/*    */ public class SimpleReloadableResourceManager
/*    */   implements IReloadableResourceManager
/*    */ {
/* 22 */   private static final Logger logger = LogManager.getLogger();
/* 23 */   private static final Joiner joinerResourcePacks = Joiner.on(", ");
/* 24 */   public final Map<String, FallbackResourceManager> domainResourceManagers = Maps.newHashMap();
/* 25 */   private final List<IResourceManagerReloadListener> reloadListeners = Lists.newArrayList();
/* 26 */   private final Set<String> setResourceDomains = Sets.newLinkedHashSet();
/*    */   private final IMetadataSerializer rmMetadataSerializer;
/*    */   
/*    */   public SimpleReloadableResourceManager(IMetadataSerializer rmMetadataSerializerIn) {
/* 30 */     this.rmMetadataSerializer = rmMetadataSerializerIn;
/*    */   }
/*    */   
/*    */   public void reloadResourcePack(IResourcePack resourcePack) {
/* 34 */     for (String s : resourcePack.getResourceDomains()) {
/* 35 */       this.setResourceDomains.add(s);
/* 36 */       FallbackResourceManager fallbackresourcemanager = this.domainResourceManagers.get(s);
/*    */       
/* 38 */       if (fallbackresourcemanager == null) {
/* 39 */         fallbackresourcemanager = new FallbackResourceManager(this.rmMetadataSerializer);
/* 40 */         this.domainResourceManagers.put(s, fallbackresourcemanager);
/*    */       } 
/*    */       
/* 43 */       fallbackresourcemanager.addResourcePack(resourcePack);
/*    */     } 
/*    */   }
/*    */   
/*    */   public Set<String> getResourceDomains() {
/* 48 */     return this.setResourceDomains;
/*    */   }
/*    */   
/*    */   public IResource getResource(ResourceLocation location) throws IOException {
/* 52 */     IResourceManager iresourcemanager = this.domainResourceManagers.get(location.getResourceDomain());
/*    */     
/* 54 */     if (iresourcemanager != null) {
/* 55 */       return iresourcemanager.getResource(location);
/*    */     }
/* 57 */     throw new FileNotFoundException(location.toString());
/*    */   }
/*    */ 
/*    */   
/*    */   public List<IResource> getAllResources(ResourceLocation location) throws IOException {
/* 62 */     IResourceManager iresourcemanager = this.domainResourceManagers.get(location.getResourceDomain());
/*    */     
/* 64 */     if (iresourcemanager != null) {
/* 65 */       return iresourcemanager.getAllResources(location);
/*    */     }
/* 67 */     throw new FileNotFoundException(location.toString());
/*    */   }
/*    */ 
/*    */   
/*    */   private void clearResources() {
/* 72 */     this.domainResourceManagers.clear();
/* 73 */     this.setResourceDomains.clear();
/*    */   }
/*    */   
/*    */   public void reloadResources(List<IResourcePack> resourcesPacksList) {
/* 77 */     clearResources();
/* 78 */     logger.info("Reloading ResourceManager: " + joinerResourcePacks.join(Iterables.transform(resourcesPacksList, new Function<IResourcePack, String>() {
/*    */               public String apply(IResourcePack p_apply_1_) {
/* 80 */                 return p_apply_1_.getPackName();
/*    */               }
/*    */             })));
/*    */     
/* 84 */     for (IResourcePack iresourcepack : resourcesPacksList) {
/* 85 */       reloadResourcePack(iresourcepack);
/*    */     }
/*    */     
/* 88 */     notifyReloadListeners();
/*    */   }
/*    */   
/*    */   public void registerReloadListener(IResourceManagerReloadListener reloadListener) {
/* 92 */     this.reloadListeners.add(reloadListener);
/* 93 */     reloadListener.onResourceManagerReload(this);
/*    */   }
/*    */   
/*    */   private void notifyReloadListeners() {
/* 97 */     for (IResourceManagerReloadListener iresourcemanagerreloadlistener : this.reloadListeners)
/* 98 */       iresourcemanagerreloadlistener.onResourceManagerReload(this); 
/*    */   }
/*    */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\minecraft\client\resources\SimpleReloadableResourceManager.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */