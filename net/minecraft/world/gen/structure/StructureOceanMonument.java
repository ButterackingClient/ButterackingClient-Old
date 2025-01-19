/*     */ package net.minecraft.world.gen.structure;
/*     */ 
/*     */ import com.google.common.collect.Lists;
/*     */ import com.google.common.collect.Sets;
/*     */ import java.util.Arrays;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import java.util.Random;
/*     */ import java.util.Set;
/*     */ import net.minecraft.entity.monster.EntityGuardian;
/*     */ import net.minecraft.nbt.NBTBase;
/*     */ import net.minecraft.nbt.NBTTagCompound;
/*     */ import net.minecraft.nbt.NBTTagList;
/*     */ import net.minecraft.util.BlockPos;
/*     */ import net.minecraft.util.EnumFacing;
/*     */ import net.minecraft.util.MathHelper;
/*     */ import net.minecraft.world.ChunkCoordIntPair;
/*     */ import net.minecraft.world.World;
/*     */ import net.minecraft.world.biome.BiomeGenBase;
/*     */ 
/*     */ 
/*     */ 
/*     */ public class StructureOceanMonument
/*     */   extends MapGenStructure
/*     */ {
/*  26 */   public static final List<BiomeGenBase> field_175802_d = Arrays.asList(new BiomeGenBase[] { BiomeGenBase.ocean, BiomeGenBase.deepOcean, BiomeGenBase.river, BiomeGenBase.frozenOcean, BiomeGenBase.frozenRiver });
/*  27 */   private static final List<BiomeGenBase.SpawnListEntry> field_175803_h = Lists.newArrayList();
/*     */ 
/*     */   
/*  30 */   private int field_175800_f = 32;
/*  31 */   private int field_175801_g = 5;
/*     */ 
/*     */   
/*     */   public StructureOceanMonument(Map<String, String> p_i45608_1_) {
/*  35 */     this();
/*     */     
/*  37 */     for (Map.Entry<String, String> entry : p_i45608_1_.entrySet()) {
/*  38 */       if (((String)entry.getKey()).equals("spacing")) {
/*  39 */         this.field_175800_f = MathHelper.parseIntWithDefaultAndMax(entry.getValue(), this.field_175800_f, 1); continue;
/*  40 */       }  if (((String)entry.getKey()).equals("separation")) {
/*  41 */         this.field_175801_g = MathHelper.parseIntWithDefaultAndMax(entry.getValue(), this.field_175801_g, 1);
/*     */       }
/*     */     } 
/*     */   }
/*     */   
/*     */   public String getStructureName() {
/*  47 */     return "Monument";
/*     */   }
/*     */   
/*     */   protected boolean canSpawnStructureAtCoords(int chunkX, int chunkZ) {
/*  51 */     int i = chunkX;
/*  52 */     int j = chunkZ;
/*     */     
/*  54 */     if (chunkX < 0) {
/*  55 */       chunkX -= this.field_175800_f - 1;
/*     */     }
/*     */     
/*  58 */     if (chunkZ < 0) {
/*  59 */       chunkZ -= this.field_175800_f - 1;
/*     */     }
/*     */     
/*  62 */     int k = chunkX / this.field_175800_f;
/*  63 */     int l = chunkZ / this.field_175800_f;
/*  64 */     Random random = this.worldObj.setRandomSeed(k, l, 10387313);
/*  65 */     k *= this.field_175800_f;
/*  66 */     l *= this.field_175800_f;
/*  67 */     k += (random.nextInt(this.field_175800_f - this.field_175801_g) + random.nextInt(this.field_175800_f - this.field_175801_g)) / 2;
/*  68 */     l += (random.nextInt(this.field_175800_f - this.field_175801_g) + random.nextInt(this.field_175800_f - this.field_175801_g)) / 2;
/*     */     
/*  70 */     if (i == k && j == l) {
/*  71 */       if (this.worldObj.getWorldChunkManager().getBiomeGenerator(new BlockPos(i * 16 + 8, 64, j * 16 + 8), null) != BiomeGenBase.deepOcean) {
/*  72 */         return false;
/*     */       }
/*     */       
/*  75 */       boolean flag = this.worldObj.getWorldChunkManager().areBiomesViable(i * 16 + 8, j * 16 + 8, 29, field_175802_d);
/*     */       
/*  77 */       if (flag) {
/*  78 */         return true;
/*     */       }
/*     */     } 
/*     */     
/*  82 */     return false;
/*     */   }
/*     */   
/*     */   protected StructureStart getStructureStart(int chunkX, int chunkZ) {
/*  86 */     return new StartMonument(this.worldObj, this.rand, chunkX, chunkZ);
/*     */   }
/*     */   
/*     */   public List<BiomeGenBase.SpawnListEntry> getScatteredFeatureSpawnList() {
/*  90 */     return field_175803_h;
/*     */   }
/*     */   
/*     */   static {
/*  94 */     field_175803_h.add(new BiomeGenBase.SpawnListEntry(EntityGuardian.class, 1, 2, 4));
/*     */   }
/*     */   public StructureOceanMonument() {}
/*     */   
/*  98 */   public static class StartMonument extends StructureStart { private Set<ChunkCoordIntPair> field_175791_c = Sets.newHashSet();
/*     */     
/*     */     private boolean field_175790_d;
/*     */     
/*     */     public StartMonument() {}
/*     */     
/*     */     public StartMonument(World worldIn, Random p_i45607_2_, int p_i45607_3_, int p_i45607_4_) {
/* 105 */       super(p_i45607_3_, p_i45607_4_);
/* 106 */       func_175789_b(worldIn, p_i45607_2_, p_i45607_3_, p_i45607_4_);
/*     */     }
/*     */     
/*     */     private void func_175789_b(World worldIn, Random p_175789_2_, int p_175789_3_, int p_175789_4_) {
/* 110 */       p_175789_2_.setSeed(worldIn.getSeed());
/* 111 */       long i = p_175789_2_.nextLong();
/* 112 */       long j = p_175789_2_.nextLong();
/* 113 */       long k = p_175789_3_ * i;
/* 114 */       long l = p_175789_4_ * j;
/* 115 */       p_175789_2_.setSeed(k ^ l ^ worldIn.getSeed());
/* 116 */       int i1 = p_175789_3_ * 16 + 8 - 29;
/* 117 */       int j1 = p_175789_4_ * 16 + 8 - 29;
/* 118 */       EnumFacing enumfacing = EnumFacing.Plane.HORIZONTAL.random(p_175789_2_);
/* 119 */       this.components.add(new StructureOceanMonumentPieces.MonumentBuilding(p_175789_2_, i1, j1, enumfacing));
/* 120 */       updateBoundingBox();
/* 121 */       this.field_175790_d = true;
/*     */     }
/*     */     
/*     */     public void generateStructure(World worldIn, Random rand, StructureBoundingBox structurebb) {
/* 125 */       if (!this.field_175790_d) {
/* 126 */         this.components.clear();
/* 127 */         func_175789_b(worldIn, rand, getChunkPosX(), getChunkPosZ());
/*     */       } 
/*     */       
/* 130 */       super.generateStructure(worldIn, rand, structurebb);
/*     */     }
/*     */     
/*     */     public boolean func_175788_a(ChunkCoordIntPair pair) {
/* 134 */       return this.field_175791_c.contains(pair) ? false : super.func_175788_a(pair);
/*     */     }
/*     */     
/*     */     public void func_175787_b(ChunkCoordIntPair pair) {
/* 138 */       super.func_175787_b(pair);
/* 139 */       this.field_175791_c.add(pair);
/*     */     }
/*     */     
/*     */     public void writeToNBT(NBTTagCompound tagCompound) {
/* 143 */       super.writeToNBT(tagCompound);
/* 144 */       NBTTagList nbttaglist = new NBTTagList();
/*     */       
/* 146 */       for (ChunkCoordIntPair chunkcoordintpair : this.field_175791_c) {
/* 147 */         NBTTagCompound nbttagcompound = new NBTTagCompound();
/* 148 */         nbttagcompound.setInteger("X", chunkcoordintpair.chunkXPos);
/* 149 */         nbttagcompound.setInteger("Z", chunkcoordintpair.chunkZPos);
/* 150 */         nbttaglist.appendTag((NBTBase)nbttagcompound);
/*     */       } 
/*     */       
/* 153 */       tagCompound.setTag("Processed", (NBTBase)nbttaglist);
/*     */     }
/*     */     
/*     */     public void readFromNBT(NBTTagCompound tagCompound) {
/* 157 */       super.readFromNBT(tagCompound);
/*     */       
/* 159 */       if (tagCompound.hasKey("Processed", 9)) {
/* 160 */         NBTTagList nbttaglist = tagCompound.getTagList("Processed", 10);
/*     */         
/* 162 */         for (int i = 0; i < nbttaglist.tagCount(); i++) {
/* 163 */           NBTTagCompound nbttagcompound = nbttaglist.getCompoundTagAt(i);
/* 164 */           this.field_175791_c.add(new ChunkCoordIntPair(nbttagcompound.getInteger("X"), nbttagcompound.getInteger("Z")));
/*     */         } 
/*     */       } 
/*     */     } }
/*     */ 
/*     */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\minecraft\world\gen\structure\StructureOceanMonument.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */