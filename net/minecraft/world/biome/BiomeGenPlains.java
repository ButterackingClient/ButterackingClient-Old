/*    */ package net.minecraft.world.biome;
/*    */ 
/*    */ import java.util.Random;
/*    */ import net.minecraft.block.BlockDoublePlant;
/*    */ import net.minecraft.block.BlockFlower;
/*    */ import net.minecraft.entity.passive.EntityHorse;
/*    */ import net.minecraft.util.BlockPos;
/*    */ import net.minecraft.world.World;
/*    */ 
/*    */ public class BiomeGenPlains
/*    */   extends BiomeGenBase {
/*    */   protected boolean field_150628_aC;
/*    */   
/*    */   protected BiomeGenPlains(int id) {
/* 15 */     super(id);
/* 16 */     setTemperatureRainfall(0.8F, 0.4F);
/* 17 */     setHeight(height_LowPlains);
/* 18 */     this.spawnableCreatureList.add(new BiomeGenBase.SpawnListEntry((Class)EntityHorse.class, 5, 2, 6));
/* 19 */     this.theBiomeDecorator.treesPerChunk = -999;
/* 20 */     this.theBiomeDecorator.flowersPerChunk = 4;
/* 21 */     this.theBiomeDecorator.grassPerChunk = 10;
/*    */   }
/*    */   
/*    */   public BlockFlower.EnumFlowerType pickRandomFlower(Random rand, BlockPos pos) {
/* 25 */     double d0 = GRASS_COLOR_NOISE.func_151601_a(pos.getX() / 200.0D, pos.getZ() / 200.0D);
/*    */     
/* 27 */     if (d0 < -0.8D) {
/* 28 */       int j = rand.nextInt(4);
/*    */       
/* 30 */       switch (j) {
/*    */         case 0:
/* 32 */           return BlockFlower.EnumFlowerType.ORANGE_TULIP;
/*    */         
/*    */         case 1:
/* 35 */           return BlockFlower.EnumFlowerType.RED_TULIP;
/*    */         
/*    */         case 2:
/* 38 */           return BlockFlower.EnumFlowerType.PINK_TULIP;
/*    */       } 
/*    */ 
/*    */       
/* 42 */       return BlockFlower.EnumFlowerType.WHITE_TULIP;
/*    */     } 
/* 44 */     if (rand.nextInt(3) > 0) {
/* 45 */       int i = rand.nextInt(3);
/* 46 */       return (i == 0) ? BlockFlower.EnumFlowerType.POPPY : ((i == 1) ? BlockFlower.EnumFlowerType.HOUSTONIA : BlockFlower.EnumFlowerType.OXEYE_DAISY);
/*    */     } 
/* 48 */     return BlockFlower.EnumFlowerType.DANDELION;
/*    */   }
/*    */ 
/*    */   
/*    */   public void decorate(World worldIn, Random rand, BlockPos pos) {
/* 53 */     double d0 = GRASS_COLOR_NOISE.func_151601_a((pos.getX() + 8) / 200.0D, (pos.getZ() + 8) / 200.0D);
/*    */     
/* 55 */     if (d0 < -0.8D) {
/* 56 */       this.theBiomeDecorator.flowersPerChunk = 15;
/* 57 */       this.theBiomeDecorator.grassPerChunk = 5;
/*    */     } else {
/* 59 */       this.theBiomeDecorator.flowersPerChunk = 4;
/* 60 */       this.theBiomeDecorator.grassPerChunk = 10;
/* 61 */       DOUBLE_PLANT_GENERATOR.setPlantType(BlockDoublePlant.EnumPlantType.GRASS);
/*    */       
/* 63 */       for (int i = 0; i < 7; i++) {
/* 64 */         int j = rand.nextInt(16) + 8;
/* 65 */         int k = rand.nextInt(16) + 8;
/* 66 */         int l = rand.nextInt(worldIn.getHeight(pos.add(j, 0, k)).getY() + 32);
/* 67 */         DOUBLE_PLANT_GENERATOR.generate(worldIn, rand, pos.add(j, l, k));
/*    */       } 
/*    */     } 
/*    */     
/* 71 */     if (this.field_150628_aC) {
/* 72 */       DOUBLE_PLANT_GENERATOR.setPlantType(BlockDoublePlant.EnumPlantType.SUNFLOWER);
/*    */       
/* 74 */       for (int i1 = 0; i1 < 10; i1++) {
/* 75 */         int j1 = rand.nextInt(16) + 8;
/* 76 */         int k1 = rand.nextInt(16) + 8;
/* 77 */         int l1 = rand.nextInt(worldIn.getHeight(pos.add(j1, 0, k1)).getY() + 32);
/* 78 */         DOUBLE_PLANT_GENERATOR.generate(worldIn, rand, pos.add(j1, l1, k1));
/*    */       } 
/*    */     } 
/*    */     
/* 82 */     super.decorate(worldIn, rand, pos);
/*    */   }
/*    */   
/*    */   protected BiomeGenBase createMutatedBiome(int p_180277_1_) {
/* 86 */     BiomeGenPlains biomegenplains = new BiomeGenPlains(p_180277_1_);
/* 87 */     biomegenplains.setBiomeName("Sunflower Plains");
/* 88 */     biomegenplains.field_150628_aC = true;
/* 89 */     biomegenplains.setColor(9286496);
/* 90 */     biomegenplains.field_150609_ah = 14273354;
/* 91 */     return biomegenplains;
/*    */   }
/*    */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\minecraft\world\biome\BiomeGenPlains.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */