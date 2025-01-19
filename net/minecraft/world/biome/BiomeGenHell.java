/*    */ package net.minecraft.world.biome;
/*    */ 
/*    */ import net.minecraft.entity.monster.EntityGhast;
/*    */ import net.minecraft.entity.monster.EntityMagmaCube;
/*    */ import net.minecraft.entity.monster.EntityPigZombie;
/*    */ 
/*    */ public class BiomeGenHell extends BiomeGenBase {
/*    */   public BiomeGenHell(int id) {
/*  9 */     super(id);
/* 10 */     this.spawnableMonsterList.clear();
/* 11 */     this.spawnableCreatureList.clear();
/* 12 */     this.spawnableWaterCreatureList.clear();
/* 13 */     this.spawnableCaveCreatureList.clear();
/* 14 */     this.spawnableMonsterList.add(new BiomeGenBase.SpawnListEntry((Class)EntityGhast.class, 50, 4, 4));
/* 15 */     this.spawnableMonsterList.add(new BiomeGenBase.SpawnListEntry((Class)EntityPigZombie.class, 100, 4, 4));
/* 16 */     this.spawnableMonsterList.add(new BiomeGenBase.SpawnListEntry((Class)EntityMagmaCube.class, 1, 4, 4));
/*    */   }
/*    */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\minecraft\world\biome\BiomeGenHell.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */