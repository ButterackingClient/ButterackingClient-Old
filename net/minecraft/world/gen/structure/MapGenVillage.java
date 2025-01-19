/*     */ package net.minecraft.world.gen.structure;
/*     */ 
/*     */ import java.util.Arrays;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import java.util.Random;
/*     */ import net.minecraft.nbt.NBTTagCompound;
/*     */ import net.minecraft.util.MathHelper;
/*     */ import net.minecraft.world.World;
/*     */ import net.minecraft.world.biome.BiomeGenBase;
/*     */ 
/*     */ public class MapGenVillage
/*     */   extends MapGenStructure
/*     */ {
/*  15 */   public static final List<BiomeGenBase> villageSpawnBiomes = Arrays.asList(new BiomeGenBase[] { BiomeGenBase.plains, BiomeGenBase.desert, BiomeGenBase.savanna });
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private int terrainType;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  25 */   private int field_82665_g = 32;
/*  26 */   private int field_82666_h = 8;
/*     */   public MapGenVillage() {}
/*     */   
/*     */   public MapGenVillage(Map<String, String> p_i2093_1_) {
/*  30 */     this();
/*     */     
/*  32 */     for (Map.Entry<String, String> entry : p_i2093_1_.entrySet()) {
/*  33 */       if (((String)entry.getKey()).equals("size")) {
/*  34 */         this.terrainType = MathHelper.parseIntWithDefaultAndMax(entry.getValue(), this.terrainType, 0); continue;
/*  35 */       }  if (((String)entry.getKey()).equals("distance")) {
/*  36 */         this.field_82665_g = MathHelper.parseIntWithDefaultAndMax(entry.getValue(), this.field_82665_g, this.field_82666_h + 1);
/*     */       }
/*     */     } 
/*     */   }
/*     */   
/*     */   public String getStructureName() {
/*  42 */     return "Village";
/*     */   }
/*     */   
/*     */   protected boolean canSpawnStructureAtCoords(int chunkX, int chunkZ) {
/*  46 */     int i = chunkX;
/*  47 */     int j = chunkZ;
/*     */     
/*  49 */     if (chunkX < 0) {
/*  50 */       chunkX -= this.field_82665_g - 1;
/*     */     }
/*     */     
/*  53 */     if (chunkZ < 0) {
/*  54 */       chunkZ -= this.field_82665_g - 1;
/*     */     }
/*     */     
/*  57 */     int k = chunkX / this.field_82665_g;
/*  58 */     int l = chunkZ / this.field_82665_g;
/*  59 */     Random random = this.worldObj.setRandomSeed(k, l, 10387312);
/*  60 */     k *= this.field_82665_g;
/*  61 */     l *= this.field_82665_g;
/*  62 */     k += random.nextInt(this.field_82665_g - this.field_82666_h);
/*  63 */     l += random.nextInt(this.field_82665_g - this.field_82666_h);
/*     */     
/*  65 */     if (i == k && j == l) {
/*  66 */       boolean flag = this.worldObj.getWorldChunkManager().areBiomesViable(i * 16 + 8, j * 16 + 8, 0, villageSpawnBiomes);
/*     */       
/*  68 */       if (flag) {
/*  69 */         return true;
/*     */       }
/*     */     } 
/*     */     
/*  73 */     return false;
/*     */   }
/*     */   
/*     */   protected StructureStart getStructureStart(int chunkX, int chunkZ) {
/*  77 */     return new Start(this.worldObj, this.rand, chunkX, chunkZ, this.terrainType);
/*     */   }
/*     */   
/*     */   public static class Start
/*     */     extends StructureStart {
/*     */     private boolean hasMoreThanTwoComponents;
/*     */     
/*     */     public Start() {}
/*     */     
/*     */     public Start(World worldIn, Random rand, int x, int z, int size) {
/*  87 */       super(x, z);
/*  88 */       List<StructureVillagePieces.PieceWeight> list = StructureVillagePieces.getStructureVillageWeightedPieceList(rand, size);
/*  89 */       StructureVillagePieces.Start structurevillagepieces$start = new StructureVillagePieces.Start(worldIn.getWorldChunkManager(), 0, rand, (x << 4) + 2, (z << 4) + 2, list, size);
/*  90 */       this.components.add(structurevillagepieces$start);
/*  91 */       structurevillagepieces$start.buildComponent(structurevillagepieces$start, this.components, rand);
/*  92 */       List<StructureComponent> list1 = structurevillagepieces$start.field_74930_j;
/*  93 */       List<StructureComponent> list2 = structurevillagepieces$start.field_74932_i;
/*     */       
/*  95 */       while (!list1.isEmpty() || !list2.isEmpty()) {
/*  96 */         if (list1.isEmpty()) {
/*  97 */           int i = rand.nextInt(list2.size());
/*  98 */           StructureComponent structurecomponent = list2.remove(i);
/*  99 */           structurecomponent.buildComponent(structurevillagepieces$start, this.components, rand); continue;
/*     */         } 
/* 101 */         int j = rand.nextInt(list1.size());
/* 102 */         StructureComponent structurecomponent2 = list1.remove(j);
/* 103 */         structurecomponent2.buildComponent(structurevillagepieces$start, this.components, rand);
/*     */       } 
/*     */ 
/*     */       
/* 107 */       updateBoundingBox();
/* 108 */       int k = 0;
/*     */       
/* 110 */       for (StructureComponent structurecomponent1 : this.components) {
/* 111 */         if (!(structurecomponent1 instanceof StructureVillagePieces.Road)) {
/* 112 */           k++;
/*     */         }
/*     */       } 
/*     */       
/* 116 */       this.hasMoreThanTwoComponents = (k > 2);
/*     */     }
/*     */     
/*     */     public boolean isSizeableStructure() {
/* 120 */       return this.hasMoreThanTwoComponents;
/*     */     }
/*     */     
/*     */     public void writeToNBT(NBTTagCompound tagCompound) {
/* 124 */       super.writeToNBT(tagCompound);
/* 125 */       tagCompound.setBoolean("Valid", this.hasMoreThanTwoComponents);
/*     */     }
/*     */     
/*     */     public void readFromNBT(NBTTagCompound tagCompound) {
/* 129 */       super.readFromNBT(tagCompound);
/* 130 */       this.hasMoreThanTwoComponents = tagCompound.getBoolean("Valid");
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\minecraft\world\gen\structure\MapGenVillage.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */