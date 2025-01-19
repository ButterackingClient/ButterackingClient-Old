/*    */ package net.minecraft.world.biome;
/*    */ 
/*    */ import java.util.Random;
/*    */ import net.minecraft.init.Blocks;
/*    */ import net.minecraft.util.BlockPos;
/*    */ import net.minecraft.world.World;
/*    */ import net.minecraft.world.gen.feature.WorldGenAbstractTree;
/*    */ import net.minecraft.world.gen.feature.WorldGenIcePath;
/*    */ import net.minecraft.world.gen.feature.WorldGenIceSpike;
/*    */ import net.minecraft.world.gen.feature.WorldGenTaiga2;
/*    */ 
/*    */ public class BiomeGenSnow
/*    */   extends BiomeGenBase {
/*    */   private boolean field_150615_aC;
/* 15 */   private WorldGenIceSpike field_150616_aD = new WorldGenIceSpike();
/* 16 */   private WorldGenIcePath field_150617_aE = new WorldGenIcePath(4);
/*    */   
/*    */   public BiomeGenSnow(int id, boolean p_i45378_2_) {
/* 19 */     super(id);
/* 20 */     this.field_150615_aC = p_i45378_2_;
/*    */     
/* 22 */     if (p_i45378_2_) {
/* 23 */       this.topBlock = Blocks.snow.getDefaultState();
/*    */     }
/*    */     
/* 26 */     this.spawnableCreatureList.clear();
/*    */   }
/*    */   
/*    */   public void decorate(World worldIn, Random rand, BlockPos pos) {
/* 30 */     if (this.field_150615_aC) {
/* 31 */       for (int i = 0; i < 3; i++) {
/* 32 */         int j = rand.nextInt(16) + 8;
/* 33 */         int k = rand.nextInt(16) + 8;
/* 34 */         this.field_150616_aD.generate(worldIn, rand, worldIn.getHeight(pos.add(j, 0, k)));
/*    */       } 
/*    */       
/* 37 */       for (int l = 0; l < 2; l++) {
/* 38 */         int i1 = rand.nextInt(16) + 8;
/* 39 */         int j1 = rand.nextInt(16) + 8;
/* 40 */         this.field_150617_aE.generate(worldIn, rand, worldIn.getHeight(pos.add(i1, 0, j1)));
/*    */       } 
/*    */     } 
/*    */     
/* 44 */     super.decorate(worldIn, rand, pos);
/*    */   }
/*    */   
/*    */   public WorldGenAbstractTree genBigTreeChance(Random rand) {
/* 48 */     return (WorldGenAbstractTree)new WorldGenTaiga2(false);
/*    */   }
/*    */   
/*    */   protected BiomeGenBase createMutatedBiome(int p_180277_1_) {
/* 52 */     BiomeGenBase biomegenbase = (new BiomeGenSnow(p_180277_1_, true)).func_150557_a(13828095, true).setBiomeName(String.valueOf(this.biomeName) + " Spikes").setEnableSnow().setTemperatureRainfall(0.0F, 0.5F).setHeight(new BiomeGenBase.Height(this.minHeight + 0.1F, this.maxHeight + 0.1F));
/* 53 */     biomegenbase.minHeight = this.minHeight + 0.3F;
/* 54 */     biomegenbase.maxHeight = this.maxHeight + 0.4F;
/* 55 */     return biomegenbase;
/*    */   }
/*    */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\minecraft\world\biome\BiomeGenSnow.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */