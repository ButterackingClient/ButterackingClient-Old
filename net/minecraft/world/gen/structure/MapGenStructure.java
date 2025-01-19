/*     */ package net.minecraft.world.gen.structure;
/*     */ 
/*     */ import com.google.common.collect.Maps;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import java.util.Random;
/*     */ import java.util.concurrent.Callable;
/*     */ import net.minecraft.crash.CrashReport;
/*     */ import net.minecraft.crash.CrashReportCategory;
/*     */ import net.minecraft.nbt.NBTBase;
/*     */ import net.minecraft.nbt.NBTTagCompound;
/*     */ import net.minecraft.util.BlockPos;
/*     */ import net.minecraft.util.ReportedException;
/*     */ import net.minecraft.util.Vec3i;
/*     */ import net.minecraft.world.ChunkCoordIntPair;
/*     */ import net.minecraft.world.World;
/*     */ import net.minecraft.world.chunk.ChunkPrimer;
/*     */ import net.minecraft.world.gen.MapGenBase;
/*     */ 
/*     */ public abstract class MapGenStructure
/*     */   extends MapGenBase {
/*     */   public MapGenStructure() {
/*  24 */     this.structureMap = Maps.newHashMap();
/*     */   }
/*     */   private MapGenStructureData structureData;
/*     */   protected Map<Long, StructureStart> structureMap;
/*     */   
/*     */   public abstract String getStructureName();
/*     */   
/*     */   protected final void recursiveGenerate(World worldIn, final int chunkX, final int chunkZ, int p_180701_4_, int p_180701_5_, ChunkPrimer chunkPrimerIn) {
/*  32 */     initializeStructureData(worldIn);
/*     */     
/*  34 */     if (!this.structureMap.containsKey(Long.valueOf(ChunkCoordIntPair.chunkXZ2Int(chunkX, chunkZ)))) {
/*  35 */       this.rand.nextInt();
/*     */       
/*     */       try {
/*  38 */         if (canSpawnStructureAtCoords(chunkX, chunkZ)) {
/*  39 */           StructureStart structurestart = getStructureStart(chunkX, chunkZ);
/*  40 */           this.structureMap.put(Long.valueOf(ChunkCoordIntPair.chunkXZ2Int(chunkX, chunkZ)), structurestart);
/*  41 */           setStructureStart(chunkX, chunkZ, structurestart);
/*     */         } 
/*  43 */       } catch (Throwable throwable) {
/*  44 */         CrashReport crashreport = CrashReport.makeCrashReport(throwable, "Exception preparing structure feature");
/*  45 */         CrashReportCategory crashreportcategory = crashreport.makeCategory("Feature being prepared");
/*  46 */         crashreportcategory.addCrashSectionCallable("Is feature chunk", new Callable<String>() {
/*     */               public String call() throws Exception {
/*  48 */                 return MapGenStructure.this.canSpawnStructureAtCoords(chunkX, chunkZ) ? "True" : "False";
/*     */               }
/*     */             });
/*  51 */         crashreportcategory.addCrashSection("Chunk location", String.format("%d,%d", new Object[] { Integer.valueOf(chunkX), Integer.valueOf(chunkZ) }));
/*  52 */         crashreportcategory.addCrashSectionCallable("Chunk pos hash", new Callable<String>() {
/*     */               public String call() throws Exception {
/*  54 */                 return String.valueOf(ChunkCoordIntPair.chunkXZ2Int(chunkX, chunkZ));
/*     */               }
/*     */             });
/*  57 */         crashreportcategory.addCrashSectionCallable("Structure type", new Callable<String>() {
/*     */               public String call() throws Exception {
/*  59 */                 return MapGenStructure.this.getClass().getCanonicalName();
/*     */               }
/*     */             });
/*  62 */         throw new ReportedException(crashreport);
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   public boolean generateStructure(World worldIn, Random randomIn, ChunkCoordIntPair chunkCoord) {
/*  68 */     initializeStructureData(worldIn);
/*  69 */     int i = (chunkCoord.chunkXPos << 4) + 8;
/*  70 */     int j = (chunkCoord.chunkZPos << 4) + 8;
/*  71 */     boolean flag = false;
/*     */     
/*  73 */     for (StructureStart structurestart : this.structureMap.values()) {
/*  74 */       if (structurestart.isSizeableStructure() && structurestart.func_175788_a(chunkCoord) && structurestart.getBoundingBox().intersectsWith(i, j, i + 15, j + 15)) {
/*  75 */         structurestart.generateStructure(worldIn, randomIn, new StructureBoundingBox(i, j, i + 15, j + 15));
/*  76 */         structurestart.func_175787_b(chunkCoord);
/*  77 */         flag = true;
/*  78 */         setStructureStart(structurestart.getChunkPosX(), structurestart.getChunkPosZ(), structurestart);
/*     */       } 
/*     */     } 
/*     */     
/*  82 */     return flag;
/*     */   }
/*     */   
/*     */   public boolean func_175795_b(BlockPos pos) {
/*  86 */     initializeStructureData(this.worldObj);
/*  87 */     return (func_175797_c(pos) != null);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected StructureStart func_175797_c(BlockPos pos) {
/*  93 */     for (StructureStart structurestart : this.structureMap.values()) {
/*  94 */       if (structurestart.isSizeableStructure() && structurestart.getBoundingBox().isVecInside((Vec3i)pos)) {
/*  95 */         Iterator<StructureComponent> iterator = structurestart.getComponents().iterator();
/*     */ 
/*     */         
/*  98 */         while (iterator.hasNext()) {
/*     */ 
/*     */ 
/*     */           
/* 102 */           StructureComponent structurecomponent = iterator.next();
/*     */           
/* 104 */           if (structurecomponent.getBoundingBox().isVecInside((Vec3i)pos))
/*     */           {
/*     */ 
/*     */ 
/*     */             
/* 109 */             return structurestart; } 
/*     */         } 
/*     */       } 
/*     */     } 
/* 113 */     return null;
/*     */   }
/*     */   
/*     */   public boolean isPositionInStructure(World worldIn, BlockPos pos) {
/* 117 */     initializeStructureData(worldIn);
/*     */     
/* 119 */     for (StructureStart structurestart : this.structureMap.values()) {
/* 120 */       if (structurestart.isSizeableStructure() && structurestart.getBoundingBox().isVecInside((Vec3i)pos)) {
/* 121 */         return true;
/*     */       }
/*     */     } 
/*     */     
/* 125 */     return false;
/*     */   }
/*     */   
/*     */   public BlockPos getClosestStrongholdPos(World worldIn, BlockPos pos) {
/* 129 */     this.worldObj = worldIn;
/* 130 */     initializeStructureData(worldIn);
/* 131 */     this.rand.setSeed(worldIn.getSeed());
/* 132 */     long i = this.rand.nextLong();
/* 133 */     long j = this.rand.nextLong();
/* 134 */     long k = (pos.getX() >> 4) * i;
/* 135 */     long l = (pos.getZ() >> 4) * j;
/* 136 */     this.rand.setSeed(k ^ l ^ worldIn.getSeed());
/* 137 */     recursiveGenerate(worldIn, pos.getX() >> 4, pos.getZ() >> 4, 0, 0, (ChunkPrimer)null);
/* 138 */     double d0 = Double.MAX_VALUE;
/* 139 */     BlockPos blockpos = null;
/*     */     
/* 141 */     for (StructureStart structurestart : this.structureMap.values()) {
/* 142 */       if (structurestart.isSizeableStructure()) {
/* 143 */         StructureComponent structurecomponent = structurestart.getComponents().get(0);
/* 144 */         BlockPos blockpos1 = structurecomponent.getBoundingBoxCenter();
/* 145 */         double d1 = blockpos1.distanceSq((Vec3i)pos);
/*     */         
/* 147 */         if (d1 < d0) {
/* 148 */           d0 = d1;
/* 149 */           blockpos = blockpos1;
/*     */         } 
/*     */       } 
/*     */     } 
/*     */     
/* 154 */     if (blockpos != null) {
/* 155 */       return blockpos;
/*     */     }
/* 157 */     List<BlockPos> list = getCoordList();
/*     */     
/* 159 */     if (list != null) {
/* 160 */       BlockPos blockpos2 = null;
/*     */       
/* 162 */       for (BlockPos blockpos3 : list) {
/* 163 */         double d2 = blockpos3.distanceSq((Vec3i)pos);
/*     */         
/* 165 */         if (d2 < d0) {
/* 166 */           d0 = d2;
/* 167 */           blockpos2 = blockpos3;
/*     */         } 
/*     */       } 
/*     */       
/* 171 */       return blockpos2;
/*     */     } 
/* 173 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected List<BlockPos> getCoordList() {
/* 179 */     return null;
/*     */   }
/*     */   
/*     */   private void initializeStructureData(World worldIn) {
/* 183 */     if (this.structureData == null) {
/* 184 */       this.structureData = (MapGenStructureData)worldIn.loadItemData(MapGenStructureData.class, getStructureName());
/*     */       
/* 186 */       if (this.structureData == null) {
/* 187 */         this.structureData = new MapGenStructureData(getStructureName());
/* 188 */         worldIn.setItemData(getStructureName(), this.structureData);
/*     */       } else {
/* 190 */         NBTTagCompound nbttagcompound = this.structureData.getTagCompound();
/*     */         
/* 192 */         for (String s : nbttagcompound.getKeySet()) {
/* 193 */           NBTBase nbtbase = nbttagcompound.getTag(s);
/*     */           
/* 195 */           if (nbtbase.getId() == 10) {
/* 196 */             NBTTagCompound nbttagcompound1 = (NBTTagCompound)nbtbase;
/*     */             
/* 198 */             if (nbttagcompound1.hasKey("ChunkX") && nbttagcompound1.hasKey("ChunkZ")) {
/* 199 */               int i = nbttagcompound1.getInteger("ChunkX");
/* 200 */               int j = nbttagcompound1.getInteger("ChunkZ");
/* 201 */               StructureStart structurestart = MapGenStructureIO.getStructureStart(nbttagcompound1, worldIn);
/*     */               
/* 203 */               if (structurestart != null) {
/* 204 */                 this.structureMap.put(Long.valueOf(ChunkCoordIntPair.chunkXZ2Int(i, j)), structurestart);
/*     */               }
/*     */             } 
/*     */           } 
/*     */         } 
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   private void setStructureStart(int chunkX, int chunkZ, StructureStart start) {
/* 214 */     this.structureData.writeInstance(start.writeStructureComponentsToNBT(chunkX, chunkZ), chunkX, chunkZ);
/* 215 */     this.structureData.markDirty();
/*     */   }
/*     */   
/*     */   protected abstract boolean canSpawnStructureAtCoords(int paramInt1, int paramInt2);
/*     */   
/*     */   protected abstract StructureStart getStructureStart(int paramInt1, int paramInt2);
/*     */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\minecraft\world\gen\structure\MapGenStructure.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */