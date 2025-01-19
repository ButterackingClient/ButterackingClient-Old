/*     */ package net.optifine;
/*     */ 
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
/*     */ import java.util.Properties;
/*     */ import net.minecraft.src.Config;
/*     */ import net.minecraft.util.ResourceLocation;
/*     */ import net.optifine.config.ConnectedParser;
/*     */ 
/*     */ public class RandomEntityProperties
/*     */ {
/*  12 */   public String name = null;
/*  13 */   public String basePath = null;
/*  14 */   public ResourceLocation[] resourceLocations = null;
/*  15 */   public RandomEntityRule[] rules = null;
/*     */   
/*     */   public RandomEntityProperties(String path, ResourceLocation[] variants) {
/*  18 */     ConnectedParser connectedparser = new ConnectedParser("RandomEntities");
/*  19 */     this.name = connectedparser.parseName(path);
/*  20 */     this.basePath = connectedparser.parseBasePath(path);
/*  21 */     this.resourceLocations = variants;
/*     */   }
/*     */   
/*     */   public RandomEntityProperties(Properties props, String path, ResourceLocation baseResLoc) {
/*  25 */     ConnectedParser connectedparser = new ConnectedParser("RandomEntities");
/*  26 */     this.name = connectedparser.parseName(path);
/*  27 */     this.basePath = connectedparser.parseBasePath(path);
/*  28 */     this.rules = parseRules(props, path, baseResLoc, connectedparser);
/*     */   }
/*     */   
/*     */   public ResourceLocation getTextureLocation(ResourceLocation loc, IRandomEntity randomEntity) {
/*  32 */     if (this.rules != null) {
/*  33 */       for (int i = 0; i < this.rules.length; i++) {
/*  34 */         RandomEntityRule randomentityrule = this.rules[i];
/*     */         
/*  36 */         if (randomentityrule.matches(randomEntity)) {
/*  37 */           return randomentityrule.getTextureLocation(loc, randomEntity.getId());
/*     */         }
/*     */       } 
/*     */     }
/*     */     
/*  42 */     if (this.resourceLocations != null) {
/*  43 */       int j = randomEntity.getId();
/*  44 */       int k = j % this.resourceLocations.length;
/*  45 */       return this.resourceLocations[k];
/*     */     } 
/*  47 */     return loc;
/*     */   }
/*     */ 
/*     */   
/*     */   private RandomEntityRule[] parseRules(Properties props, String pathProps, ResourceLocation baseResLoc, ConnectedParser cp) {
/*  52 */     List<RandomEntityRule> list = new ArrayList();
/*  53 */     int i = props.size();
/*     */     
/*  55 */     for (int j = 0; j < i; j++) {
/*  56 */       int k = j + 1;
/*  57 */       String s = props.getProperty("textures." + k);
/*     */       
/*  59 */       if (s == null) {
/*  60 */         s = props.getProperty("skins." + k);
/*     */       }
/*     */       
/*  63 */       if (s != null) {
/*  64 */         RandomEntityRule randomentityrule = new RandomEntityRule(props, pathProps, baseResLoc, k, s, cp);
/*     */         
/*  66 */         if (randomentityrule.isValid(pathProps)) {
/*  67 */           list.add(randomentityrule);
/*     */         }
/*     */       } 
/*     */     } 
/*     */     
/*  72 */     RandomEntityRule[] arandomentityrule = list.<RandomEntityRule>toArray(new RandomEntityRule[list.size()]);
/*  73 */     return arandomentityrule;
/*     */   }
/*     */   
/*     */   public boolean isValid(String path) {
/*  77 */     if (this.resourceLocations == null && this.rules == null) {
/*  78 */       Config.warn("No skins specified: " + path);
/*  79 */       return false;
/*     */     } 
/*  81 */     if (this.rules != null) {
/*  82 */       for (int i = 0; i < this.rules.length; i++) {
/*  83 */         RandomEntityRule randomentityrule = this.rules[i];
/*     */         
/*  85 */         if (!randomentityrule.isValid(path)) {
/*  86 */           return false;
/*     */         }
/*     */       } 
/*     */     }
/*     */     
/*  91 */     if (this.resourceLocations != null) {
/*  92 */       for (int j = 0; j < this.resourceLocations.length; j++) {
/*  93 */         ResourceLocation resourcelocation = this.resourceLocations[j];
/*     */         
/*  95 */         if (!Config.hasResource(resourcelocation)) {
/*  96 */           Config.warn("Texture not found: " + resourcelocation.getResourcePath());
/*  97 */           return false;
/*     */         } 
/*     */       } 
/*     */     }
/*     */     
/* 102 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isDefault() {
/* 107 */     return (this.rules != null) ? false : ((this.resourceLocations == null));
/*     */   }
/*     */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\optifine\RandomEntityProperties.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */