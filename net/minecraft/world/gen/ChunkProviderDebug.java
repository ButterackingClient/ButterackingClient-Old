/*     */ package net.minecraft.world.gen;
/*     */ 
/*     */ import com.google.common.collect.Lists;
/*     */ import java.util.Collection;
/*     */ import java.util.List;
/*     */ import net.minecraft.block.Block;
/*     */ import net.minecraft.block.state.IBlockState;
/*     */ import net.minecraft.entity.EnumCreatureType;
/*     */ import net.minecraft.init.Blocks;
/*     */ import net.minecraft.util.BlockPos;
/*     */ import net.minecraft.util.IProgressUpdate;
/*     */ import net.minecraft.util.MathHelper;
/*     */ import net.minecraft.world.World;
/*     */ import net.minecraft.world.biome.BiomeGenBase;
/*     */ import net.minecraft.world.chunk.Chunk;
/*     */ import net.minecraft.world.chunk.ChunkPrimer;
/*     */ import net.minecraft.world.chunk.IChunkProvider;
/*     */ 
/*     */ public class ChunkProviderDebug
/*     */   implements IChunkProvider {
/*  21 */   private static final List<IBlockState> field_177464_a = Lists.newArrayList();
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ChunkProviderDebug(World worldIn) {
/*  27 */     this.world = worldIn;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Chunk provideChunk(int x, int z) {
/*  35 */     ChunkPrimer chunkprimer = new ChunkPrimer();
/*     */     
/*  37 */     for (int i = 0; i < 16; i++) {
/*  38 */       for (int j = 0; j < 16; j++) {
/*  39 */         int k = x * 16 + i;
/*  40 */         int l = z * 16 + j;
/*  41 */         chunkprimer.setBlockState(i, 60, j, Blocks.barrier.getDefaultState());
/*  42 */         IBlockState iblockstate = func_177461_b(k, l);
/*     */         
/*  44 */         if (iblockstate != null) {
/*  45 */           chunkprimer.setBlockState(i, 70, j, iblockstate);
/*     */         }
/*     */       } 
/*     */     } 
/*     */     
/*  50 */     Chunk chunk = new Chunk(this.world, chunkprimer, x, z);
/*  51 */     chunk.generateSkylightMap();
/*  52 */     BiomeGenBase[] abiomegenbase = this.world.getWorldChunkManager().loadBlockGeneratorData(null, x * 16, z * 16, 16, 16);
/*  53 */     byte[] abyte = chunk.getBiomeArray();
/*     */     
/*  55 */     for (int i1 = 0; i1 < abyte.length; i1++) {
/*  56 */       abyte[i1] = (byte)(abiomegenbase[i1]).biomeID;
/*     */     }
/*     */     
/*  59 */     chunk.generateSkylightMap();
/*  60 */     return chunk;
/*     */   }
/*     */   
/*     */   public static IBlockState func_177461_b(int p_177461_0_, int p_177461_1_) {
/*  64 */     IBlockState iblockstate = null;
/*     */     
/*  66 */     if (p_177461_0_ > 0 && p_177461_1_ > 0 && p_177461_0_ % 2 != 0 && p_177461_1_ % 2 != 0) {
/*  67 */       p_177461_0_ /= 2;
/*  68 */       p_177461_1_ /= 2;
/*     */       
/*  70 */       if (p_177461_0_ <= field_177462_b && p_177461_1_ <= field_181039_c) {
/*  71 */         int i = MathHelper.abs_int(p_177461_0_ * field_177462_b + p_177461_1_);
/*     */         
/*  73 */         if (i < field_177464_a.size()) {
/*  74 */           iblockstate = field_177464_a.get(i);
/*     */         }
/*     */       } 
/*     */     } 
/*     */     
/*  79 */     return iblockstate;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean chunkExists(int x, int z) {
/*  86 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void populate(IChunkProvider chunkProvider, int x, int z) {}
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean populateChunk(IChunkProvider chunkProvider, Chunk chunkIn, int x, int z) {
/*  96 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean saveChunks(boolean saveAllChunks, IProgressUpdate progressCallback) {
/* 104 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void saveExtraData() {}
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean unloadQueuedChunks() {
/* 118 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean canSave() {
/* 125 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String makeString() {
/* 132 */     return "DebugLevelSource";
/*     */   }
/*     */   
/*     */   public List<BiomeGenBase.SpawnListEntry> getPossibleCreatures(EnumCreatureType creatureType, BlockPos pos) {
/* 136 */     BiomeGenBase biomegenbase = this.world.getBiomeGenForCoords(pos);
/* 137 */     return biomegenbase.getSpawnableList(creatureType);
/*     */   }
/*     */   
/*     */   public BlockPos getStrongholdGen(World worldIn, String structureName, BlockPos position) {
/* 141 */     return null;
/*     */   }
/*     */   
/*     */   public int getLoadedChunkCount() {
/* 145 */     return 0;
/*     */   }
/*     */ 
/*     */   
/*     */   public void recreateStructures(Chunk chunkIn, int x, int z) {}
/*     */   
/*     */   public Chunk provideChunk(BlockPos blockPosIn) {
/* 152 */     return provideChunk(blockPosIn.getX() >> 4, blockPosIn.getZ() >> 4);
/*     */   }
/*     */   
/*     */   static {
/* 156 */     for (Block block : Block.blockRegistry)
/* 157 */       field_177464_a.addAll((Collection<? extends IBlockState>)block.getBlockState().getValidStates()); 
/*     */   }
/*     */   
/* 160 */   private static final int field_177462_b = MathHelper.ceiling_float_int(MathHelper.sqrt_float(field_177464_a.size()));
/* 161 */   private static final int field_181039_c = MathHelper.ceiling_float_int(field_177464_a.size() / field_177462_b);
/*     */   private final World world;
/*     */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\minecraft\world\gen\ChunkProviderDebug.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */