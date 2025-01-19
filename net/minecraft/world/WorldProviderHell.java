/*     */ package net.minecraft.world;
/*     */ 
/*     */ import net.minecraft.util.Vec3;
/*     */ import net.minecraft.world.biome.BiomeGenBase;
/*     */ import net.minecraft.world.biome.WorldChunkManager;
/*     */ import net.minecraft.world.biome.WorldChunkManagerHell;
/*     */ import net.minecraft.world.border.WorldBorder;
/*     */ import net.minecraft.world.chunk.IChunkProvider;
/*     */ import net.minecraft.world.gen.ChunkProviderHell;
/*     */ 
/*     */ public class WorldProviderHell
/*     */   extends WorldProvider
/*     */ {
/*     */   public void registerWorldChunkManager() {
/*  15 */     this.worldChunkMgr = (WorldChunkManager)new WorldChunkManagerHell(BiomeGenBase.hell, 0.0F);
/*  16 */     this.isHellWorld = true;
/*  17 */     this.hasNoSky = true;
/*  18 */     this.dimensionId = -1;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Vec3 getFogColor(float p_76562_1_, float p_76562_2_) {
/*  25 */     return new Vec3(0.20000000298023224D, 0.029999999329447746D, 0.029999999329447746D);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void generateLightBrightnessTable() {
/*  32 */     float f = 0.1F;
/*     */     
/*  34 */     for (int i = 0; i <= 15; i++) {
/*  35 */       float f1 = 1.0F - i / 15.0F;
/*  36 */       this.lightBrightnessTable[i] = (1.0F - f1) / (f1 * 3.0F + 1.0F) * (1.0F - f) + f;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public IChunkProvider createChunkGenerator() {
/*  44 */     return (IChunkProvider)new ChunkProviderHell(this.worldObj, this.worldObj.getWorldInfo().isMapFeaturesEnabled(), this.worldObj.getSeed());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isSurfaceWorld() {
/*  51 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean canCoordinateBeSpawn(int x, int z) {
/*  58 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public float calculateCelestialAngle(long worldTime, float partialTicks) {
/*  65 */     return 0.5F;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean canRespawnHere() {
/*  72 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean doesXZShowFog(int x, int z) {
/*  79 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getDimensionName() {
/*  86 */     return "Nether";
/*     */   }
/*     */   
/*     */   public String getInternalNameSuffix() {
/*  90 */     return "_nether";
/*     */   }
/*     */   
/*     */   public WorldBorder getWorldBorder() {
/*  94 */     return new WorldBorder() {
/*     */         public double getCenterX() {
/*  96 */           return super.getCenterX() / 8.0D;
/*     */         }
/*     */         
/*     */         public double getCenterZ() {
/* 100 */           return super.getCenterZ() / 8.0D;
/*     */         }
/*     */       };
/*     */   }
/*     */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\minecraft\world\WorldProviderHell.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */