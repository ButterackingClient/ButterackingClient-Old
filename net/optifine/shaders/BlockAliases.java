/*     */ package net.optifine.shaders;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
/*     */ import net.minecraft.client.Minecraft;
/*     */ import net.minecraft.src.Config;
/*     */ import net.minecraft.util.ResourceLocation;
/*     */ import net.optifine.config.ConnectedParser;
/*     */ import net.optifine.config.MatchBlock;
/*     */ import net.optifine.reflect.Reflector;
/*     */ import net.optifine.reflect.ReflectorForge;
/*     */ import net.optifine.shaders.config.MacroProcessor;
/*     */ import net.optifine.util.PropertiesOrdered;
/*     */ import net.optifine.util.StrUtils;
/*     */ 
/*     */ 
/*     */ public class BlockAliases
/*     */ {
/*  21 */   private static BlockAlias[][] blockAliases = null;
/*  22 */   private static PropertiesOrdered blockLayerPropertes = null;
/*     */   private static boolean updateOnResourcesReloaded;
/*     */   
/*     */   public static int getBlockAliasId(int blockId, int metadata) {
/*  26 */     if (blockAliases == null)
/*  27 */       return blockId; 
/*  28 */     if (blockId >= 0 && blockId < blockAliases.length) {
/*  29 */       BlockAlias[] ablockalias = blockAliases[blockId];
/*     */       
/*  31 */       if (ablockalias == null) {
/*  32 */         return blockId;
/*     */       }
/*  34 */       for (int i = 0; i < ablockalias.length; i++) {
/*  35 */         BlockAlias blockalias = ablockalias[i];
/*     */         
/*  37 */         if (blockalias.matches(blockId, metadata)) {
/*  38 */           return blockalias.getBlockAliasId();
/*     */         }
/*     */       } 
/*     */       
/*  42 */       return blockId;
/*     */     } 
/*     */     
/*  45 */     return blockId;
/*     */   }
/*     */ 
/*     */   
/*     */   public static void resourcesReloaded() {
/*  50 */     if (updateOnResourcesReloaded) {
/*  51 */       updateOnResourcesReloaded = false;
/*  52 */       update(Shaders.getShaderPack());
/*     */     } 
/*     */   }
/*     */   
/*     */   public static void update(IShaderPack shaderPack) {
/*  57 */     reset();
/*     */     
/*  59 */     if (shaderPack != null) {
/*  60 */       if (Reflector.Loader_getActiveModList.exists() && Minecraft.getMinecraft().getResourcePackRepository() == null) {
/*  61 */         Config.dbg("[Shaders] Delayed loading of block mappings after resources are loaded");
/*  62 */         updateOnResourcesReloaded = true;
/*     */       } else {
/*  64 */         List<List<BlockAlias>> list = new ArrayList<>();
/*  65 */         String s = "/shaders/block.properties";
/*  66 */         InputStream inputstream = shaderPack.getResourceAsStream(s);
/*     */         
/*  68 */         if (inputstream != null) {
/*  69 */           loadBlockAliases(inputstream, s, list);
/*     */         }
/*     */         
/*  72 */         loadModBlockAliases(list);
/*     */         
/*  74 */         if (list.size() > 0) {
/*  75 */           blockAliases = toArrays(list);
/*     */         }
/*     */       } 
/*     */     }
/*     */   }
/*     */   
/*     */   private static void loadModBlockAliases(List<List<BlockAlias>> listBlockAliases) {
/*  82 */     String[] astring = ReflectorForge.getForgeModIds();
/*     */     
/*  84 */     for (int i = 0; i < astring.length; i++) {
/*  85 */       String s = astring[i];
/*     */       
/*     */       try {
/*  88 */         ResourceLocation resourcelocation = new ResourceLocation(s, "shaders/block.properties");
/*  89 */         InputStream inputstream = Config.getResourceStream(resourcelocation);
/*  90 */         loadBlockAliases(inputstream, resourcelocation.toString(), listBlockAliases);
/*  91 */       } catch (IOException iOException) {}
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private static void loadBlockAliases(InputStream in, String path, List<List<BlockAlias>> listBlockAliases) {
/*  98 */     if (in != null) {
/*     */       try {
/* 100 */         in = MacroProcessor.process(in, path);
/* 101 */         PropertiesOrdered propertiesOrdered = new PropertiesOrdered();
/* 102 */         propertiesOrdered.load(in);
/* 103 */         in.close();
/* 104 */         Config.dbg("[Shaders] Parsing block mappings: " + path);
/* 105 */         ConnectedParser connectedparser = new ConnectedParser("Shaders");
/*     */         
/* 107 */         for (Object s0 : propertiesOrdered.keySet()) {
/* 108 */           String s = (String)s0;
/* 109 */           String s1 = propertiesOrdered.getProperty(s);
/*     */           
/* 111 */           if (s.startsWith("layer.")) {
/* 112 */             if (blockLayerPropertes == null) {
/* 113 */               blockLayerPropertes = new PropertiesOrdered();
/*     */             }
/*     */             
/* 116 */             blockLayerPropertes.put(s, s1); continue;
/*     */           } 
/* 118 */           String s2 = "block.";
/*     */           
/* 120 */           if (!s.startsWith(s2)) {
/* 121 */             Config.warn("[Shaders] Invalid block ID: " + s); continue;
/*     */           } 
/* 123 */           String s3 = StrUtils.removePrefix(s, s2);
/* 124 */           int i = Config.parseInt(s3, -1);
/*     */           
/* 126 */           if (i < 0) {
/* 127 */             Config.warn("[Shaders] Invalid block ID: " + s); continue;
/*     */           } 
/* 129 */           MatchBlock[] amatchblock = connectedparser.parseMatchBlocks(s1);
/*     */           
/* 131 */           if (amatchblock != null && amatchblock.length >= 1) {
/* 132 */             BlockAlias blockalias = new BlockAlias(i, amatchblock);
/* 133 */             addToList(listBlockAliases, blockalias); continue;
/*     */           } 
/* 135 */           Config.warn("[Shaders] Invalid block ID mapping: " + s + "=" + s1);
/*     */         
/*     */         }
/*     */ 
/*     */       
/*     */       }
/* 141 */       catch (IOException var14) {
/* 142 */         Config.warn("[Shaders] Error reading: " + path);
/*     */       } 
/*     */     }
/*     */   }
/*     */   
/*     */   private static void addToList(List<List<BlockAlias>> blocksAliases, BlockAlias ba) {
/* 148 */     int[] aint = ba.getMatchBlockIds();
/*     */     
/* 150 */     for (int i = 0; i < aint.length; i++) {
/* 151 */       int j = aint[i];
/*     */       
/* 153 */       while (j >= blocksAliases.size()) {
/* 154 */         blocksAliases.add(null);
/*     */       }
/*     */       
/* 157 */       List<BlockAlias> list = blocksAliases.get(j);
/*     */       
/* 159 */       if (list == null) {
/* 160 */         list = new ArrayList<>();
/* 161 */         blocksAliases.set(j, list);
/*     */       } 
/*     */       
/* 164 */       BlockAlias blockalias = new BlockAlias(ba.getBlockAliasId(), ba.getMatchBlocks(j));
/* 165 */       list.add(blockalias);
/*     */     } 
/*     */   }
/*     */   
/*     */   private static BlockAlias[][] toArrays(List<List<BlockAlias>> listBlocksAliases) {
/* 170 */     BlockAlias[][] ablockalias = new BlockAlias[listBlocksAliases.size()][];
/*     */     
/* 172 */     for (int i = 0; i < ablockalias.length; i++) {
/* 173 */       List<BlockAlias> list = listBlocksAliases.get(i);
/*     */       
/* 175 */       if (list != null) {
/* 176 */         ablockalias[i] = list.<BlockAlias>toArray(new BlockAlias[list.size()]);
/*     */       }
/*     */     } 
/*     */     
/* 180 */     return ablockalias;
/*     */   }
/*     */   
/*     */   public static PropertiesOrdered getBlockLayerPropertes() {
/* 184 */     return blockLayerPropertes;
/*     */   }
/*     */   
/*     */   public static void reset() {
/* 188 */     blockAliases = null;
/* 189 */     blockLayerPropertes = null;
/*     */   }
/*     */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\optifine\shaders\BlockAliases.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */