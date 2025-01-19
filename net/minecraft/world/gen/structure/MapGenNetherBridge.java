/*    */ package net.minecraft.world.gen.structure;
/*    */ 
/*    */ import com.google.common.collect.Lists;
/*    */ import java.util.List;
/*    */ import java.util.Random;
/*    */ import net.minecraft.entity.monster.EntityBlaze;
/*    */ import net.minecraft.entity.monster.EntityMagmaCube;
/*    */ import net.minecraft.entity.monster.EntityPigZombie;
/*    */ import net.minecraft.entity.monster.EntitySkeleton;
/*    */ import net.minecraft.world.World;
/*    */ import net.minecraft.world.biome.BiomeGenBase;
/*    */ 
/*    */ public class MapGenNetherBridge
/*    */   extends MapGenStructure
/*    */ {
/* 16 */   private List<BiomeGenBase.SpawnListEntry> spawnList = Lists.newArrayList();
/*    */   
/*    */   public MapGenNetherBridge() {
/* 19 */     this.spawnList.add(new BiomeGenBase.SpawnListEntry(EntityBlaze.class, 10, 2, 3));
/* 20 */     this.spawnList.add(new BiomeGenBase.SpawnListEntry(EntityPigZombie.class, 5, 4, 4));
/* 21 */     this.spawnList.add(new BiomeGenBase.SpawnListEntry(EntitySkeleton.class, 10, 4, 4));
/* 22 */     this.spawnList.add(new BiomeGenBase.SpawnListEntry(EntityMagmaCube.class, 3, 4, 4));
/*    */   }
/*    */   
/*    */   public String getStructureName() {
/* 26 */     return "Fortress";
/*    */   }
/*    */   
/*    */   public List<BiomeGenBase.SpawnListEntry> getSpawnList() {
/* 30 */     return this.spawnList;
/*    */   }
/*    */   
/*    */   protected boolean canSpawnStructureAtCoords(int chunkX, int chunkZ) {
/* 34 */     int i = chunkX >> 4;
/* 35 */     int j = chunkZ >> 4;
/* 36 */     this.rand.setSeed((i ^ j << 4) ^ this.worldObj.getSeed());
/* 37 */     this.rand.nextInt();
/* 38 */     return (this.rand.nextInt(3) != 0) ? false : ((chunkX != (i << 4) + 4 + this.rand.nextInt(8)) ? false : ((chunkZ == (j << 4) + 4 + this.rand.nextInt(8))));
/*    */   }
/*    */   
/*    */   protected StructureStart getStructureStart(int chunkX, int chunkZ) {
/* 42 */     return new Start(this.worldObj, this.rand, chunkX, chunkZ);
/*    */   }
/*    */   
/*    */   public static class Start
/*    */     extends StructureStart {
/*    */     public Start() {}
/*    */     
/*    */     public Start(World worldIn, Random p_i2040_2_, int p_i2040_3_, int p_i2040_4_) {
/* 50 */       super(p_i2040_3_, p_i2040_4_);
/* 51 */       StructureNetherBridgePieces.Start structurenetherbridgepieces$start = new StructureNetherBridgePieces.Start(p_i2040_2_, (p_i2040_3_ << 4) + 2, (p_i2040_4_ << 4) + 2);
/* 52 */       this.components.add(structurenetherbridgepieces$start);
/* 53 */       structurenetherbridgepieces$start.buildComponent(structurenetherbridgepieces$start, this.components, p_i2040_2_);
/* 54 */       List<StructureComponent> list = structurenetherbridgepieces$start.field_74967_d;
/*    */       
/* 56 */       while (!list.isEmpty()) {
/* 57 */         int i = p_i2040_2_.nextInt(list.size());
/* 58 */         StructureComponent structurecomponent = list.remove(i);
/* 59 */         structurecomponent.buildComponent(structurenetherbridgepieces$start, this.components, p_i2040_2_);
/*    */       } 
/*    */       
/* 62 */       updateBoundingBox();
/* 63 */       setRandomHeight(worldIn, p_i2040_2_, 48, 70);
/*    */     }
/*    */   }
/*    */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\minecraft\world\gen\structure\MapGenNetherBridge.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */