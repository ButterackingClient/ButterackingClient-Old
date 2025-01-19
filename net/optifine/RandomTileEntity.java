/*    */ package net.optifine;
/*    */ 
/*    */ import net.minecraft.src.Config;
/*    */ import net.minecraft.tileentity.TileEntity;
/*    */ import net.minecraft.util.BlockPos;
/*    */ import net.minecraft.world.biome.BiomeGenBase;
/*    */ import net.optifine.util.TileEntityUtils;
/*    */ 
/*    */ public class RandomTileEntity implements IRandomEntity {
/*    */   private TileEntity tileEntity;
/*    */   
/*    */   public int getId() {
/* 13 */     return Config.getRandom(this.tileEntity.getPos(), this.tileEntity.getBlockMetadata());
/*    */   }
/*    */   
/*    */   public BlockPos getSpawnPosition() {
/* 17 */     return this.tileEntity.getPos();
/*    */   }
/*    */   
/*    */   public String getName() {
/* 21 */     String s = TileEntityUtils.getTileEntityName(this.tileEntity);
/* 22 */     return s;
/*    */   }
/*    */   
/*    */   public BiomeGenBase getSpawnBiome() {
/* 26 */     return this.tileEntity.getWorld().getBiomeGenForCoords(this.tileEntity.getPos());
/*    */   }
/*    */   
/*    */   public int getHealth() {
/* 30 */     return -1;
/*    */   }
/*    */   
/*    */   public int getMaxHealth() {
/* 34 */     return -1;
/*    */   }
/*    */   
/*    */   public TileEntity getTileEntity() {
/* 38 */     return this.tileEntity;
/*    */   }
/*    */   
/*    */   public void setTileEntity(TileEntity tileEntity) {
/* 42 */     this.tileEntity = tileEntity;
/*    */   }
/*    */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\optifine\RandomTileEntity.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */