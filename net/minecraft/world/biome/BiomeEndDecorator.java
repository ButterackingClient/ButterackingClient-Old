/*    */ package net.minecraft.world.biome;
/*    */ import net.minecraft.entity.Entity;
/*    */ import net.minecraft.entity.boss.EntityDragon;
/*    */ import net.minecraft.init.Blocks;
/*    */ import net.minecraft.world.gen.feature.WorldGenSpikes;
/*    */ import net.minecraft.world.gen.feature.WorldGenerator;
/*    */ 
/*    */ public class BiomeEndDecorator extends BiomeDecorator {
/*  9 */   protected WorldGenerator spikeGen = (WorldGenerator)new WorldGenSpikes(Blocks.end_stone);
/*    */   
/*    */   protected void genDecorations(BiomeGenBase biomeGenBaseIn) {
/* 12 */     generateOres();
/*    */     
/* 14 */     if (this.randomGenerator.nextInt(5) == 0) {
/* 15 */       int i = this.randomGenerator.nextInt(16) + 8;
/* 16 */       int j = this.randomGenerator.nextInt(16) + 8;
/* 17 */       this.spikeGen.generate(this.currentWorld, this.randomGenerator, this.currentWorld.getTopSolidOrLiquidBlock(this.field_180294_c.add(i, 0, j)));
/*    */     } 
/*    */     
/* 20 */     if (this.field_180294_c.getX() == 0 && this.field_180294_c.getZ() == 0) {
/* 21 */       EntityDragon entitydragon = new EntityDragon(this.currentWorld);
/* 22 */       entitydragon.setLocationAndAngles(0.0D, 128.0D, 0.0D, this.randomGenerator.nextFloat() * 360.0F, 0.0F);
/* 23 */       this.currentWorld.spawnEntityInWorld((Entity)entitydragon);
/*    */     } 
/*    */   }
/*    */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\minecraft\world\biome\BiomeEndDecorator.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */