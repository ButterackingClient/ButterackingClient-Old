/*    */ package net.minecraft.world.gen.layer;
/*    */ 
/*    */ import net.minecraft.world.biome.BiomeGenBase;
/*    */ 
/*    */ public class GenLayerRiverMix extends GenLayer {
/*    */   private GenLayer biomePatternGeneratorChain;
/*    */   private GenLayer riverPatternGeneratorChain;
/*    */   
/*    */   public GenLayerRiverMix(long p_i2129_1_, GenLayer p_i2129_3_, GenLayer p_i2129_4_) {
/* 10 */     super(p_i2129_1_);
/* 11 */     this.biomePatternGeneratorChain = p_i2129_3_;
/* 12 */     this.riverPatternGeneratorChain = p_i2129_4_;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void initWorldGenSeed(long seed) {
/* 20 */     this.biomePatternGeneratorChain.initWorldGenSeed(seed);
/* 21 */     this.riverPatternGeneratorChain.initWorldGenSeed(seed);
/* 22 */     super.initWorldGenSeed(seed);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public int[] getInts(int areaX, int areaY, int areaWidth, int areaHeight) {
/* 30 */     int[] aint = this.biomePatternGeneratorChain.getInts(areaX, areaY, areaWidth, areaHeight);
/* 31 */     int[] aint1 = this.riverPatternGeneratorChain.getInts(areaX, areaY, areaWidth, areaHeight);
/* 32 */     int[] aint2 = IntCache.getIntCache(areaWidth * areaHeight);
/*    */     
/* 34 */     for (int i = 0; i < areaWidth * areaHeight; i++) {
/* 35 */       if (aint[i] != BiomeGenBase.ocean.biomeID && aint[i] != BiomeGenBase.deepOcean.biomeID) {
/* 36 */         if (aint1[i] == BiomeGenBase.river.biomeID) {
/* 37 */           if (aint[i] == BiomeGenBase.icePlains.biomeID) {
/* 38 */             aint2[i] = BiomeGenBase.frozenRiver.biomeID;
/* 39 */           } else if (aint[i] != BiomeGenBase.mushroomIsland.biomeID && aint[i] != BiomeGenBase.mushroomIslandShore.biomeID) {
/* 40 */             aint2[i] = aint1[i] & 0xFF;
/*    */           } else {
/* 42 */             aint2[i] = BiomeGenBase.mushroomIslandShore.biomeID;
/*    */           } 
/*    */         } else {
/* 45 */           aint2[i] = aint[i];
/*    */         } 
/*    */       } else {
/* 48 */         aint2[i] = aint[i];
/*    */       } 
/*    */     } 
/*    */     
/* 52 */     return aint2;
/*    */   }
/*    */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\minecraft\world\gen\layer\GenLayerRiverMix.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */