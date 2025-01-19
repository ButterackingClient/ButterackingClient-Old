/*    */ package net.minecraft.world.biome;
/*    */ 
/*    */ import net.minecraft.init.Blocks;
/*    */ 
/*    */ public class BiomeGenBeach extends BiomeGenBase {
/*    */   public BiomeGenBeach(int id) {
/*  7 */     super(id);
/*  8 */     this.spawnableCreatureList.clear();
/*  9 */     this.topBlock = Blocks.sand.getDefaultState();
/* 10 */     this.fillerBlock = Blocks.sand.getDefaultState();
/* 11 */     this.theBiomeDecorator.treesPerChunk = -999;
/* 12 */     this.theBiomeDecorator.deadBushPerChunk = 0;
/* 13 */     this.theBiomeDecorator.reedsPerChunk = 0;
/* 14 */     this.theBiomeDecorator.cactiPerChunk = 0;
/*    */   }
/*    */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\minecraft\world\biome\BiomeGenBeach.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */