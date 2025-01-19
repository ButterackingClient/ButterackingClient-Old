/*     */ package net.minecraft.world.gen;
/*     */ 
/*     */ import com.google.common.collect.Lists;
/*     */ import com.google.common.collect.Maps;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import net.minecraft.block.Block;
/*     */ import net.minecraft.init.Blocks;
/*     */ import net.minecraft.util.MathHelper;
/*     */ import net.minecraft.world.biome.BiomeGenBase;
/*     */ 
/*     */ 
/*     */ 
/*     */ public class FlatGeneratorInfo
/*     */ {
/*  16 */   private final List<FlatLayerInfo> flatLayers = Lists.newArrayList();
/*  17 */   private final Map<String, Map<String, String>> worldFeatures = Maps.newHashMap();
/*     */ 
/*     */   
/*     */   private int biomeToUse;
/*     */ 
/*     */   
/*     */   public int getBiome() {
/*  24 */     return this.biomeToUse;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setBiome(int biome) {
/*  31 */     this.biomeToUse = biome;
/*     */   }
/*     */   
/*     */   public Map<String, Map<String, String>> getWorldFeatures() {
/*  35 */     return this.worldFeatures;
/*     */   }
/*     */   
/*     */   public List<FlatLayerInfo> getFlatLayers() {
/*  39 */     return this.flatLayers;
/*     */   }
/*     */   
/*     */   public void func_82645_d() {
/*  43 */     int i = 0;
/*     */     
/*  45 */     for (FlatLayerInfo flatlayerinfo : this.flatLayers) {
/*  46 */       flatlayerinfo.setMinY(i);
/*  47 */       i += flatlayerinfo.getLayerCount();
/*     */     } 
/*     */   }
/*     */   
/*     */   public String toString() {
/*  52 */     StringBuilder stringbuilder = new StringBuilder();
/*  53 */     stringbuilder.append(3);
/*  54 */     stringbuilder.append(";");
/*     */     
/*  56 */     for (int i = 0; i < this.flatLayers.size(); i++) {
/*  57 */       if (i > 0) {
/*  58 */         stringbuilder.append(",");
/*     */       }
/*     */       
/*  61 */       stringbuilder.append(((FlatLayerInfo)this.flatLayers.get(i)).toString());
/*     */     } 
/*     */     
/*  64 */     stringbuilder.append(";");
/*  65 */     stringbuilder.append(this.biomeToUse);
/*     */     
/*  67 */     if (!this.worldFeatures.isEmpty()) {
/*  68 */       stringbuilder.append(";");
/*  69 */       int k = 0;
/*     */       
/*  71 */       for (Map.Entry<String, Map<String, String>> entry : this.worldFeatures.entrySet()) {
/*  72 */         if (k++ > 0) {
/*  73 */           stringbuilder.append(",");
/*     */         }
/*     */         
/*  76 */         stringbuilder.append(((String)entry.getKey()).toLowerCase());
/*  77 */         Map<String, String> map = entry.getValue();
/*     */         
/*  79 */         if (!map.isEmpty()) {
/*  80 */           stringbuilder.append("(");
/*  81 */           int j = 0;
/*     */           
/*  83 */           for (Map.Entry<String, String> entry1 : map.entrySet()) {
/*  84 */             if (j++ > 0) {
/*  85 */               stringbuilder.append(" ");
/*     */             }
/*     */             
/*  88 */             stringbuilder.append(entry1.getKey());
/*  89 */             stringbuilder.append("=");
/*  90 */             stringbuilder.append(entry1.getValue());
/*     */           } 
/*     */           
/*  93 */           stringbuilder.append(")");
/*     */         } 
/*     */       } 
/*     */     } else {
/*  97 */       stringbuilder.append(";");
/*     */     } 
/*     */     
/* 100 */     return stringbuilder.toString();
/*     */   }
/*     */   
/*     */   private static FlatLayerInfo func_180715_a(int p_180715_0_, String p_180715_1_, int p_180715_2_) {
/* 104 */     String[] astring = (p_180715_0_ >= 3) ? p_180715_1_.split("\\*", 2) : p_180715_1_.split("x", 2);
/* 105 */     int i = 1;
/* 106 */     int j = 0;
/*     */     
/* 108 */     if (astring.length == 2) {
/*     */       try {
/* 110 */         i = Integer.parseInt(astring[0]);
/*     */         
/* 112 */         if (p_180715_2_ + i >= 256) {
/* 113 */           i = 256 - p_180715_2_;
/*     */         }
/*     */         
/* 116 */         if (i < 0) {
/* 117 */           i = 0;
/*     */         }
/* 119 */       } catch (Throwable var8) {
/* 120 */         return null;
/*     */       } 
/*     */     }
/*     */     
/* 124 */     Block block = null;
/*     */     
/*     */     try {
/* 127 */       String s = astring[astring.length - 1];
/*     */       
/* 129 */       if (p_180715_0_ < 3) {
/* 130 */         astring = s.split(":", 2);
/*     */         
/* 132 */         if (astring.length > 1) {
/* 133 */           j = Integer.parseInt(astring[1]);
/*     */         }
/*     */         
/* 136 */         block = Block.getBlockById(Integer.parseInt(astring[0]));
/*     */       } else {
/* 138 */         astring = s.split(":", 3);
/* 139 */         block = (astring.length > 1) ? Block.getBlockFromName(String.valueOf(astring[0]) + ":" + astring[1]) : null;
/*     */         
/* 141 */         if (block != null) {
/* 142 */           j = (astring.length > 2) ? Integer.parseInt(astring[2]) : 0;
/*     */         } else {
/* 144 */           block = Block.getBlockFromName(astring[0]);
/*     */           
/* 146 */           if (block != null) {
/* 147 */             j = (astring.length > 1) ? Integer.parseInt(astring[1]) : 0;
/*     */           }
/*     */         } 
/*     */         
/* 151 */         if (block == null) {
/* 152 */           return null;
/*     */         }
/*     */       } 
/*     */       
/* 156 */       if (block == Blocks.air) {
/* 157 */         j = 0;
/*     */       }
/*     */       
/* 160 */       if (j < 0 || j > 15) {
/* 161 */         j = 0;
/*     */       }
/* 163 */     } catch (Throwable var9) {
/* 164 */       return null;
/*     */     } 
/*     */     
/* 167 */     FlatLayerInfo flatlayerinfo = new FlatLayerInfo(p_180715_0_, i, block, j);
/* 168 */     flatlayerinfo.setMinY(p_180715_2_);
/* 169 */     return flatlayerinfo;
/*     */   }
/*     */   
/*     */   private static List<FlatLayerInfo> func_180716_a(int p_180716_0_, String p_180716_1_) {
/* 173 */     if (p_180716_1_ != null && p_180716_1_.length() >= 1) {
/* 174 */       List<FlatLayerInfo> list = Lists.newArrayList();
/* 175 */       String[] astring = p_180716_1_.split(",");
/* 176 */       int i = 0; byte b; int j;
/*     */       String[] arrayOfString1;
/* 178 */       for (j = (arrayOfString1 = astring).length, b = 0; b < j; ) { String s = arrayOfString1[b];
/* 179 */         FlatLayerInfo flatlayerinfo = func_180715_a(p_180716_0_, s, i);
/*     */         
/* 181 */         if (flatlayerinfo == null) {
/* 182 */           return null;
/*     */         }
/*     */         
/* 185 */         list.add(flatlayerinfo);
/* 186 */         i += flatlayerinfo.getLayerCount();
/*     */         b++; }
/*     */       
/* 189 */       return list;
/*     */     } 
/* 191 */     return null;
/*     */   }
/*     */ 
/*     */   
/*     */   public static FlatGeneratorInfo createFlatGeneratorFromString(String flatGeneratorSettings) {
/* 196 */     if (flatGeneratorSettings == null) {
/* 197 */       return getDefaultFlatGenerator();
/*     */     }
/* 199 */     String[] astring = flatGeneratorSettings.split(";", -1);
/* 200 */     int i = (astring.length == 1) ? 0 : MathHelper.parseIntWithDefault(astring[0], 0);
/*     */     
/* 202 */     if (i >= 0 && i <= 3) {
/* 203 */       FlatGeneratorInfo flatgeneratorinfo = new FlatGeneratorInfo();
/* 204 */       int j = (astring.length == 1) ? 0 : 1;
/* 205 */       List<FlatLayerInfo> list = func_180716_a(i, astring[j++]);
/*     */       
/* 207 */       if (list != null && !list.isEmpty()) {
/* 208 */         flatgeneratorinfo.getFlatLayers().addAll(list);
/* 209 */         flatgeneratorinfo.func_82645_d();
/* 210 */         int k = BiomeGenBase.plains.biomeID;
/*     */         
/* 212 */         if (i > 0 && astring.length > j) {
/* 213 */           k = MathHelper.parseIntWithDefault(astring[j++], k);
/*     */         }
/*     */         
/* 216 */         flatgeneratorinfo.setBiome(k);
/*     */         
/* 218 */         if (i > 0 && astring.length > j) {
/* 219 */           String[] astring1 = astring[j++].toLowerCase().split(","); byte b; int m;
/*     */           String[] arrayOfString1;
/* 221 */           for (m = (arrayOfString1 = astring1).length, b = 0; b < m; ) { String s = arrayOfString1[b];
/* 222 */             String[] astring2 = s.split("\\(", 2);
/* 223 */             Map<String, String> map = Maps.newHashMap();
/*     */             
/* 225 */             if (astring2[0].length() > 0) {
/* 226 */               flatgeneratorinfo.getWorldFeatures().put(astring2[0], map);
/*     */               
/* 228 */               if (astring2.length > 1 && astring2[1].endsWith(")") && astring2[1].length() > 1) {
/* 229 */                 String[] astring3 = astring2[1].substring(0, astring2[1].length() - 1).split(" ");
/*     */                 
/* 231 */                 for (int l = 0; l < astring3.length; l++) {
/* 232 */                   String[] astring4 = astring3[l].split("=", 2);
/*     */                   
/* 234 */                   if (astring4.length == 2)
/* 235 */                     map.put(astring4[0], astring4[1]); 
/*     */                 } 
/*     */               } 
/*     */             } 
/*     */             b++; }
/*     */         
/*     */         } else {
/* 242 */           flatgeneratorinfo.getWorldFeatures().put("village", Maps.newHashMap());
/*     */         } 
/*     */         
/* 245 */         return flatgeneratorinfo;
/*     */       } 
/* 247 */       return getDefaultFlatGenerator();
/*     */     } 
/*     */     
/* 250 */     return getDefaultFlatGenerator();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static FlatGeneratorInfo getDefaultFlatGenerator() {
/* 256 */     FlatGeneratorInfo flatgeneratorinfo = new FlatGeneratorInfo();
/* 257 */     flatgeneratorinfo.setBiome(BiomeGenBase.plains.biomeID);
/* 258 */     flatgeneratorinfo.getFlatLayers().add(new FlatLayerInfo(1, Blocks.bedrock));
/* 259 */     flatgeneratorinfo.getFlatLayers().add(new FlatLayerInfo(2, Blocks.dirt));
/* 260 */     flatgeneratorinfo.getFlatLayers().add(new FlatLayerInfo(1, (Block)Blocks.grass));
/* 261 */     flatgeneratorinfo.func_82645_d();
/* 262 */     flatgeneratorinfo.getWorldFeatures().put("village", Maps.newHashMap());
/* 263 */     return flatgeneratorinfo;
/*     */   }
/*     */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\minecraft\world\gen\FlatGeneratorInfo.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */