/*    */ package net.minecraft.world.biome;
/*    */ 
/*    */ import net.minecraft.entity.monster.EntityEnderman;
/*    */ import net.minecraft.init.Blocks;
/*    */ 
/*    */ public class BiomeGenEnd extends BiomeGenBase {
/*    */   public BiomeGenEnd(int id) {
/*  8 */     super(id);
/*  9 */     this.spawnableMonsterList.clear();
/* 10 */     this.spawnableCreatureList.clear();
/* 11 */     this.spawnableWaterCreatureList.clear();
/* 12 */     this.spawnableCaveCreatureList.clear();
/* 13 */     this.spawnableMonsterList.add(new BiomeGenBase.SpawnListEntry((Class)EntityEnderman.class, 10, 4, 4));
/* 14 */     this.topBlock = Blocks.dirt.getDefaultState();
/* 15 */     this.fillerBlock = Blocks.dirt.getDefaultState();
/* 16 */     this.theBiomeDecorator = new BiomeEndDecorator();
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public int getSkyColorByTemp(float p_76731_1_) {
/* 23 */     return 0;
/*    */   }
/*    */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\minecraft\world\biome\BiomeGenEnd.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */