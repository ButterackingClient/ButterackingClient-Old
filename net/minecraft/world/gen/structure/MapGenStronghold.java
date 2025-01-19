/*     */ package net.minecraft.world.gen.structure;
/*     */ 
/*     */ import com.google.common.collect.Lists;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import java.util.Random;
/*     */ import net.minecraft.util.BlockPos;
/*     */ import net.minecraft.util.MathHelper;
/*     */ import net.minecraft.world.ChunkCoordIntPair;
/*     */ import net.minecraft.world.World;
/*     */ import net.minecraft.world.biome.BiomeGenBase;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class MapGenStronghold
/*     */   extends MapGenStructure
/*     */ {
/*  28 */   private ChunkCoordIntPair[] structureCoords = new ChunkCoordIntPair[3];
/*  29 */   private double field_82671_h = 32.0D;
/*  30 */   private int field_82672_i = 3;
/*  31 */   private List<BiomeGenBase> field_151546_e = Lists.newArrayList(); public MapGenStronghold() { byte b; int i;
/*     */     BiomeGenBase[] arrayOfBiomeGenBase;
/*  33 */     for (i = (arrayOfBiomeGenBase = BiomeGenBase.getBiomeGenArray()).length, b = 0; b < i; ) { BiomeGenBase biomegenbase = arrayOfBiomeGenBase[b];
/*  34 */       if (biomegenbase != null && biomegenbase.minHeight > 0.0F)
/*  35 */         this.field_151546_e.add(biomegenbase); 
/*     */       b++; }
/*     */      }
/*     */   
/*     */   private boolean ranBiomeCheck;
/*     */   public MapGenStronghold(Map<String, String> p_i2068_1_) {
/*  41 */     this();
/*     */     
/*  43 */     for (Map.Entry<String, String> entry : p_i2068_1_.entrySet()) {
/*  44 */       if (((String)entry.getKey()).equals("distance")) {
/*  45 */         this.field_82671_h = MathHelper.parseDoubleWithDefaultAndMax(entry.getValue(), this.field_82671_h, 1.0D); continue;
/*  46 */       }  if (((String)entry.getKey()).equals("count")) {
/*  47 */         this.structureCoords = new ChunkCoordIntPair[MathHelper.parseIntWithDefaultAndMax((String)entry.getValue(), this.structureCoords.length, 1)]; continue;
/*  48 */       }  if (((String)entry.getKey()).equals("spread")) {
/*  49 */         this.field_82672_i = MathHelper.parseIntWithDefaultAndMax(entry.getValue(), this.field_82672_i, 1);
/*     */       }
/*     */     } 
/*     */   }
/*     */   
/*     */   public String getStructureName() {
/*  55 */     return "Stronghold";
/*     */   }
/*     */   
/*     */   protected boolean canSpawnStructureAtCoords(int chunkX, int chunkZ) {
/*  59 */     if (!this.ranBiomeCheck) {
/*  60 */       Random random = new Random();
/*  61 */       random.setSeed(this.worldObj.getSeed());
/*  62 */       double d0 = random.nextDouble() * Math.PI * 2.0D;
/*  63 */       int k = 1;
/*     */       
/*  65 */       for (int j = 0; j < this.structureCoords.length; j++) {
/*  66 */         double d1 = (1.25D * k + random.nextDouble()) * this.field_82671_h * k;
/*  67 */         int m = (int)Math.round(Math.cos(d0) * d1);
/*  68 */         int l = (int)Math.round(Math.sin(d0) * d1);
/*  69 */         BlockPos blockpos = this.worldObj.getWorldChunkManager().findBiomePosition((m << 4) + 8, (l << 4) + 8, 112, this.field_151546_e, random);
/*     */         
/*  71 */         if (blockpos != null) {
/*  72 */           m = blockpos.getX() >> 4;
/*  73 */           l = blockpos.getZ() >> 4;
/*     */         } 
/*     */         
/*  76 */         this.structureCoords[j] = new ChunkCoordIntPair(m, l);
/*  77 */         d0 += 6.283185307179586D * k / this.field_82672_i;
/*     */         
/*  79 */         if (j == this.field_82672_i) {
/*  80 */           k += 2 + random.nextInt(5);
/*  81 */           this.field_82672_i += 1 + random.nextInt(2);
/*     */         } 
/*     */       } 
/*     */       
/*  85 */       this.ranBiomeCheck = true;
/*     */     }  byte b; int i;
/*     */     ChunkCoordIntPair[] arrayOfChunkCoordIntPair;
/*  88 */     for (i = (arrayOfChunkCoordIntPair = this.structureCoords).length, b = 0; b < i; ) { ChunkCoordIntPair chunkcoordintpair = arrayOfChunkCoordIntPair[b];
/*  89 */       if (chunkX == chunkcoordintpair.chunkXPos && chunkZ == chunkcoordintpair.chunkZPos) {
/*  90 */         return true;
/*     */       }
/*     */       b++; }
/*     */     
/*  94 */     return false;
/*     */   }
/*     */   
/*     */   protected List<BlockPos> getCoordList() {
/*  98 */     List<BlockPos> list = Lists.newArrayList(); byte b; int i;
/*     */     ChunkCoordIntPair[] arrayOfChunkCoordIntPair;
/* 100 */     for (i = (arrayOfChunkCoordIntPair = this.structureCoords).length, b = 0; b < i; ) { ChunkCoordIntPair chunkcoordintpair = arrayOfChunkCoordIntPair[b];
/* 101 */       if (chunkcoordintpair != null) {
/* 102 */         list.add(chunkcoordintpair.getCenterBlock(64));
/*     */       }
/*     */       b++; }
/*     */     
/* 106 */     return list;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected StructureStart getStructureStart(int chunkX, int chunkZ) {
/* 112 */     for (Start mapgenstronghold$start = new Start(this.worldObj, this.rand, chunkX, chunkZ); mapgenstronghold$start.getComponents().isEmpty() || ((StructureStrongholdPieces.Stairs2)mapgenstronghold$start.getComponents().get(0)).strongholdPortalRoom == null; mapgenstronghold$start = new Start(this.worldObj, this.rand, chunkX, chunkZ));
/*     */ 
/*     */ 
/*     */     
/* 116 */     return mapgenstronghold$start;
/*     */   }
/*     */   
/*     */   public static class Start
/*     */     extends StructureStart {
/*     */     public Start() {}
/*     */     
/*     */     public Start(World worldIn, Random p_i2067_2_, int p_i2067_3_, int p_i2067_4_) {
/* 124 */       super(p_i2067_3_, p_i2067_4_);
/* 125 */       StructureStrongholdPieces.prepareStructurePieces();
/* 126 */       StructureStrongholdPieces.Stairs2 structurestrongholdpieces$stairs2 = new StructureStrongholdPieces.Stairs2(0, p_i2067_2_, (p_i2067_3_ << 4) + 2, (p_i2067_4_ << 4) + 2);
/* 127 */       this.components.add(structurestrongholdpieces$stairs2);
/* 128 */       structurestrongholdpieces$stairs2.buildComponent(structurestrongholdpieces$stairs2, this.components, p_i2067_2_);
/* 129 */       List<StructureComponent> list = structurestrongholdpieces$stairs2.field_75026_c;
/*     */       
/* 131 */       while (!list.isEmpty()) {
/* 132 */         int i = p_i2067_2_.nextInt(list.size());
/* 133 */         StructureComponent structurecomponent = list.remove(i);
/* 134 */         structurecomponent.buildComponent(structurestrongholdpieces$stairs2, this.components, p_i2067_2_);
/*     */       } 
/*     */       
/* 137 */       updateBoundingBox();
/* 138 */       markAvailableHeight(worldIn, p_i2067_2_, 10);
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\minecraft\world\gen\structure\MapGenStronghold.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */