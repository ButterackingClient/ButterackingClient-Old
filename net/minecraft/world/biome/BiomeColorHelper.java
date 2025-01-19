/*    */ package net.minecraft.world.biome;
/*    */ 
/*    */ import net.minecraft.util.BlockPos;
/*    */ import net.minecraft.world.IBlockAccess;
/*    */ 
/*    */ public class BiomeColorHelper {
/*  7 */   private static final ColorResolver GRASS_COLOR = new ColorResolver() {
/*    */       public int getColorAtPos(BiomeGenBase biome, BlockPos blockPosition) {
/*  9 */         return biome.getGrassColorAtPos(blockPosition);
/*    */       }
/*    */     };
/* 12 */   private static final ColorResolver FOLIAGE_COLOR = new ColorResolver() {
/*    */       public int getColorAtPos(BiomeGenBase biome, BlockPos blockPosition) {
/* 14 */         return biome.getFoliageColorAtPos(blockPosition);
/*    */       }
/*    */     };
/* 17 */   private static final ColorResolver WATER_COLOR_MULTIPLIER = new ColorResolver() {
/*    */       public int getColorAtPos(BiomeGenBase biome, BlockPos blockPosition) {
/* 19 */         return biome.waterColorMultiplier;
/*    */       }
/*    */     };
/*    */   
/*    */   private static int getColorAtPos(IBlockAccess blockAccess, BlockPos pos, ColorResolver colorResolver) {
/* 24 */     int i = 0;
/* 25 */     int j = 0;
/* 26 */     int k = 0;
/*    */     
/* 28 */     for (BlockPos.MutableBlockPos blockpos$mutableblockpos : BlockPos.getAllInBoxMutable(pos.add(-1, 0, -1), pos.add(1, 0, 1))) {
/* 29 */       int l = colorResolver.getColorAtPos(blockAccess.getBiomeGenForCoords((BlockPos)blockpos$mutableblockpos), (BlockPos)blockpos$mutableblockpos);
/* 30 */       i += (l & 0xFF0000) >> 16;
/* 31 */       j += (l & 0xFF00) >> 8;
/* 32 */       k += l & 0xFF;
/*    */     } 
/*    */     
/* 35 */     return (i / 9 & 0xFF) << 16 | (j / 9 & 0xFF) << 8 | k / 9 & 0xFF;
/*    */   }
/*    */   
/*    */   public static int getGrassColorAtPos(IBlockAccess p_180286_0_, BlockPos p_180286_1_) {
/* 39 */     return getColorAtPos(p_180286_0_, p_180286_1_, GRASS_COLOR);
/*    */   }
/*    */   
/*    */   public static int getFoliageColorAtPos(IBlockAccess p_180287_0_, BlockPos p_180287_1_) {
/* 43 */     return getColorAtPos(p_180287_0_, p_180287_1_, FOLIAGE_COLOR);
/*    */   }
/*    */   
/*    */   public static int getWaterColorAtPos(IBlockAccess p_180288_0_, BlockPos p_180288_1_) {
/* 47 */     return getColorAtPos(p_180288_0_, p_180288_1_, WATER_COLOR_MULTIPLIER);
/*    */   }
/*    */   
/*    */   static interface ColorResolver {
/*    */     int getColorAtPos(BiomeGenBase param1BiomeGenBase, BlockPos param1BlockPos);
/*    */   }
/*    */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\minecraft\world\biome\BiomeColorHelper.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */