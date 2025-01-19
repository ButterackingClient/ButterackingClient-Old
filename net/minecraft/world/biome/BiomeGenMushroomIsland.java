/*    */ package net.minecraft.world.biome;
/*    */ 
/*    */ import net.minecraft.entity.passive.EntityMooshroom;
/*    */ import net.minecraft.init.Blocks;
/*    */ 
/*    */ public class BiomeGenMushroomIsland extends BiomeGenBase {
/*    */   public BiomeGenMushroomIsland(int id) {
/*  8 */     super(id);
/*  9 */     this.theBiomeDecorator.treesPerChunk = -100;
/* 10 */     this.theBiomeDecorator.flowersPerChunk = -100;
/* 11 */     this.theBiomeDecorator.grassPerChunk = -100;
/* 12 */     this.theBiomeDecorator.mushroomsPerChunk = 1;
/* 13 */     this.theBiomeDecorator.bigMushroomsPerChunk = 1;
/* 14 */     this.topBlock = Blocks.mycelium.getDefaultState();
/* 15 */     this.spawnableMonsterList.clear();
/* 16 */     this.spawnableCreatureList.clear();
/* 17 */     this.spawnableWaterCreatureList.clear();
/* 18 */     this.spawnableCreatureList.add(new BiomeGenBase.SpawnListEntry((Class)EntityMooshroom.class, 8, 4, 8));
/*    */   }
/*    */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\minecraft\world\biome\BiomeGenMushroomIsland.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */