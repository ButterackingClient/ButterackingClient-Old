/*     */ package net.minecraft.world.gen.structure;
/*     */ 
/*     */ import com.google.common.collect.Lists;
/*     */ import java.util.Arrays;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import java.util.Random;
/*     */ import net.minecraft.entity.monster.EntityWitch;
/*     */ import net.minecraft.util.BlockPos;
/*     */ import net.minecraft.util.MathHelper;
/*     */ import net.minecraft.world.World;
/*     */ import net.minecraft.world.biome.BiomeGenBase;
/*     */ 
/*     */ 
/*     */ public class MapGenScatteredFeature
/*     */   extends MapGenStructure
/*     */ {
/*  18 */   private static final List<BiomeGenBase> biomelist = Arrays.asList(new BiomeGenBase[] { BiomeGenBase.desert, BiomeGenBase.desertHills, BiomeGenBase.jungle, BiomeGenBase.jungleHills, BiomeGenBase.swampland });
/*     */ 
/*     */   
/*     */   private List<BiomeGenBase.SpawnListEntry> scatteredFeatureSpawnList;
/*     */ 
/*     */   
/*     */   private int maxDistanceBetweenScatteredFeatures;
/*     */ 
/*     */   
/*     */   private int minDistanceBetweenScatteredFeatures;
/*     */ 
/*     */ 
/*     */   
/*     */   public MapGenScatteredFeature() {
/*  32 */     this.scatteredFeatureSpawnList = Lists.newArrayList();
/*  33 */     this.maxDistanceBetweenScatteredFeatures = 32;
/*  34 */     this.minDistanceBetweenScatteredFeatures = 8;
/*  35 */     this.scatteredFeatureSpawnList.add(new BiomeGenBase.SpawnListEntry(EntityWitch.class, 1, 1, 1));
/*     */   }
/*     */   
/*     */   public MapGenScatteredFeature(Map<String, String> p_i2061_1_) {
/*  39 */     this();
/*     */     
/*  41 */     for (Map.Entry<String, String> entry : p_i2061_1_.entrySet()) {
/*  42 */       if (((String)entry.getKey()).equals("distance")) {
/*  43 */         this.maxDistanceBetweenScatteredFeatures = MathHelper.parseIntWithDefaultAndMax(entry.getValue(), this.maxDistanceBetweenScatteredFeatures, this.minDistanceBetweenScatteredFeatures + 1);
/*     */       }
/*     */     } 
/*     */   }
/*     */   
/*     */   public String getStructureName() {
/*  49 */     return "Temple";
/*     */   }
/*     */   
/*     */   protected boolean canSpawnStructureAtCoords(int chunkX, int chunkZ) {
/*  53 */     int i = chunkX;
/*  54 */     int j = chunkZ;
/*     */     
/*  56 */     if (chunkX < 0) {
/*  57 */       chunkX -= this.maxDistanceBetweenScatteredFeatures - 1;
/*     */     }
/*     */     
/*  60 */     if (chunkZ < 0) {
/*  61 */       chunkZ -= this.maxDistanceBetweenScatteredFeatures - 1;
/*     */     }
/*     */     
/*  64 */     int k = chunkX / this.maxDistanceBetweenScatteredFeatures;
/*  65 */     int l = chunkZ / this.maxDistanceBetweenScatteredFeatures;
/*  66 */     Random random = this.worldObj.setRandomSeed(k, l, 14357617);
/*  67 */     k *= this.maxDistanceBetweenScatteredFeatures;
/*  68 */     l *= this.maxDistanceBetweenScatteredFeatures;
/*  69 */     k += random.nextInt(this.maxDistanceBetweenScatteredFeatures - this.minDistanceBetweenScatteredFeatures);
/*  70 */     l += random.nextInt(this.maxDistanceBetweenScatteredFeatures - this.minDistanceBetweenScatteredFeatures);
/*     */     
/*  72 */     if (i == k && j == l) {
/*  73 */       BiomeGenBase biomegenbase = this.worldObj.getWorldChunkManager().getBiomeGenerator(new BlockPos(i * 16 + 8, 0, j * 16 + 8));
/*     */       
/*  75 */       if (biomegenbase == null) {
/*  76 */         return false;
/*     */       }
/*     */       
/*  79 */       for (BiomeGenBase biomegenbase1 : biomelist) {
/*  80 */         if (biomegenbase == biomegenbase1) {
/*  81 */           return true;
/*     */         }
/*     */       } 
/*     */     } 
/*     */     
/*  86 */     return false;
/*     */   }
/*     */   
/*     */   protected StructureStart getStructureStart(int chunkX, int chunkZ) {
/*  90 */     return new Start(this.worldObj, this.rand, chunkX, chunkZ);
/*     */   }
/*     */   
/*     */   public boolean func_175798_a(BlockPos p_175798_1_) {
/*  94 */     StructureStart structurestart = func_175797_c(p_175798_1_);
/*     */     
/*  96 */     if (structurestart != null && structurestart instanceof Start && !structurestart.components.isEmpty()) {
/*  97 */       StructureComponent structurecomponent = structurestart.components.getFirst();
/*  98 */       return structurecomponent instanceof ComponentScatteredFeaturePieces.SwampHut;
/*     */     } 
/* 100 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public List<BiomeGenBase.SpawnListEntry> getScatteredFeatureSpawnList() {
/* 105 */     return this.scatteredFeatureSpawnList;
/*     */   }
/*     */   
/*     */   public static class Start
/*     */     extends StructureStart {
/*     */     public Start() {}
/*     */     
/*     */     public Start(World worldIn, Random p_i2060_2_, int p_i2060_3_, int p_i2060_4_) {
/* 113 */       super(p_i2060_3_, p_i2060_4_);
/* 114 */       BiomeGenBase biomegenbase = worldIn.getBiomeGenForCoords(new BlockPos(p_i2060_3_ * 16 + 8, 0, p_i2060_4_ * 16 + 8));
/*     */       
/* 116 */       if (biomegenbase != BiomeGenBase.jungle && biomegenbase != BiomeGenBase.jungleHills) {
/* 117 */         if (biomegenbase == BiomeGenBase.swampland) {
/* 118 */           ComponentScatteredFeaturePieces.SwampHut componentscatteredfeaturepieces$swamphut = new ComponentScatteredFeaturePieces.SwampHut(p_i2060_2_, p_i2060_3_ * 16, p_i2060_4_ * 16);
/* 119 */           this.components.add(componentscatteredfeaturepieces$swamphut);
/* 120 */         } else if (biomegenbase == BiomeGenBase.desert || biomegenbase == BiomeGenBase.desertHills) {
/* 121 */           ComponentScatteredFeaturePieces.DesertPyramid componentscatteredfeaturepieces$desertpyramid = new ComponentScatteredFeaturePieces.DesertPyramid(p_i2060_2_, p_i2060_3_ * 16, p_i2060_4_ * 16);
/* 122 */           this.components.add(componentscatteredfeaturepieces$desertpyramid);
/*     */         } 
/*     */       } else {
/* 125 */         ComponentScatteredFeaturePieces.JunglePyramid componentscatteredfeaturepieces$junglepyramid = new ComponentScatteredFeaturePieces.JunglePyramid(p_i2060_2_, p_i2060_3_ * 16, p_i2060_4_ * 16);
/* 126 */         this.components.add(componentscatteredfeaturepieces$junglepyramid);
/*     */       } 
/*     */       
/* 129 */       updateBoundingBox();
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\minecraft\world\gen\structure\MapGenScatteredFeature.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */