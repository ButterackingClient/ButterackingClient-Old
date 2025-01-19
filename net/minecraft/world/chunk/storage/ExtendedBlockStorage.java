/*     */ package net.minecraft.world.chunk.storage;
/*     */ 
/*     */ import net.minecraft.block.Block;
/*     */ import net.minecraft.block.state.IBlockState;
/*     */ import net.minecraft.init.Blocks;
/*     */ import net.minecraft.world.chunk.NibbleArray;
/*     */ import net.optifine.reflect.Reflector;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class ExtendedBlockStorage
/*     */ {
/*     */   private int yBase;
/*     */   private int blockRefCount;
/*     */   private int tickRefCount;
/*     */   private char[] data;
/*     */   private NibbleArray blocklightArray;
/*     */   private NibbleArray skylightArray;
/*     */   
/*     */   public ExtendedBlockStorage(int y, boolean storeSkylight) {
/*  38 */     this.yBase = y;
/*  39 */     this.data = new char[4096];
/*  40 */     this.blocklightArray = new NibbleArray();
/*     */     
/*  42 */     if (storeSkylight) {
/*  43 */       this.skylightArray = new NibbleArray();
/*     */     }
/*     */   }
/*     */   
/*     */   public IBlockState get(int x, int y, int z) {
/*  48 */     IBlockState iblockstate = (IBlockState)Block.BLOCK_STATE_IDS.getByValue(this.data[y << 8 | z << 4 | x]);
/*  49 */     return (iblockstate != null) ? iblockstate : Blocks.air.getDefaultState();
/*     */   }
/*     */   
/*     */   public void set(int x, int y, int z, IBlockState state) {
/*  53 */     if (Reflector.IExtendedBlockState.isInstance(state)) {
/*  54 */       state = (IBlockState)Reflector.call(state, Reflector.IExtendedBlockState_getClean, new Object[0]);
/*     */     }
/*     */     
/*  57 */     IBlockState iblockstate = get(x, y, z);
/*  58 */     Block block = iblockstate.getBlock();
/*  59 */     Block block1 = state.getBlock();
/*     */     
/*  61 */     if (block != Blocks.air) {
/*  62 */       this.blockRefCount--;
/*     */       
/*  64 */       if (block.getTickRandomly()) {
/*  65 */         this.tickRefCount--;
/*     */       }
/*     */     } 
/*     */     
/*  69 */     if (block1 != Blocks.air) {
/*  70 */       this.blockRefCount++;
/*     */       
/*  72 */       if (block1.getTickRandomly()) {
/*  73 */         this.tickRefCount++;
/*     */       }
/*     */     } 
/*     */     
/*  77 */     this.data[y << 8 | z << 4 | x] = (char)Block.BLOCK_STATE_IDS.get(state);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Block getBlockByExtId(int x, int y, int z) {
/*  85 */     return get(x, y, z).getBlock();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getExtBlockMetadata(int x, int y, int z) {
/*  92 */     IBlockState iblockstate = get(x, y, z);
/*  93 */     return iblockstate.getBlock().getMetaFromState(iblockstate);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isEmpty() {
/* 100 */     return (this.blockRefCount == 0);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean getNeedsRandomTick() {
/* 108 */     return (this.tickRefCount > 0);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getYLocation() {
/* 115 */     return this.yBase;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setExtSkylightValue(int x, int y, int z, int value) {
/* 122 */     this.skylightArray.set(x, y, z, value);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getExtSkylightValue(int x, int y, int z) {
/* 129 */     return this.skylightArray.get(x, y, z);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setExtBlocklightValue(int x, int y, int z, int value) {
/* 136 */     this.blocklightArray.set(x, y, z, value);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getExtBlocklightValue(int x, int y, int z) {
/* 143 */     return this.blocklightArray.get(x, y, z);
/*     */   }
/*     */   
/*     */   public void removeInvalidBlocks() {
/* 147 */     IBlockState iblockstate = Blocks.air.getDefaultState();
/* 148 */     int i = 0;
/* 149 */     int j = 0;
/*     */     
/* 151 */     for (int k = 0; k < 16; k++) {
/* 152 */       for (int l = 0; l < 16; l++) {
/* 153 */         for (int i1 = 0; i1 < 16; i1++) {
/* 154 */           Block block = getBlockByExtId(i1, k, l);
/*     */           
/* 156 */           if (block != Blocks.air) {
/* 157 */             i++;
/*     */             
/* 159 */             if (block.getTickRandomly()) {
/* 160 */               j++;
/*     */             }
/*     */           } 
/*     */         } 
/*     */       } 
/*     */     } 
/*     */     
/* 167 */     this.blockRefCount = i;
/* 168 */     this.tickRefCount = j;
/*     */   }
/*     */   
/*     */   public char[] getData() {
/* 172 */     return this.data;
/*     */   }
/*     */   
/*     */   public void setData(char[] dataArray) {
/* 176 */     this.data = dataArray;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public NibbleArray getBlocklightArray() {
/* 183 */     return this.blocklightArray;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public NibbleArray getSkylightArray() {
/* 190 */     return this.skylightArray;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setBlocklightArray(NibbleArray newBlocklightArray) {
/* 197 */     this.blocklightArray = newBlocklightArray;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setSkylightArray(NibbleArray newSkylightArray) {
/* 204 */     this.skylightArray = newSkylightArray;
/*     */   }
/*     */   
/*     */   public int getBlockRefCount() {
/* 208 */     return this.blockRefCount;
/*     */   }
/*     */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\minecraft\world\chunk\storage\ExtendedBlockStorage.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */