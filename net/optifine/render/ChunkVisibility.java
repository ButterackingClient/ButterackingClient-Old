/*     */ package net.optifine.render;
/*     */ 
/*     */ import java.util.ArrayList;
/*     */ import java.util.ConcurrentModificationException;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.tileentity.TileEntity;
/*     */ import net.minecraft.util.BlockPos;
/*     */ import net.minecraft.util.ClassInheritanceMultiMap;
/*     */ import net.minecraft.util.EnumFacing;
/*     */ import net.minecraft.util.MathHelper;
/*     */ import net.minecraft.world.World;
/*     */ import net.minecraft.world.chunk.Chunk;
/*     */ import net.minecraft.world.chunk.storage.ExtendedBlockStorage;
/*     */ 
/*     */ public class ChunkVisibility
/*     */ {
/*     */   public static final int MASK_FACINGS = 63;
/*  20 */   public static final EnumFacing[][] enumFacingArrays = makeEnumFacingArrays(false);
/*  21 */   public static final EnumFacing[][] enumFacingOppositeArrays = makeEnumFacingArrays(true);
/*  22 */   private static int counter = 0;
/*  23 */   private static int iMaxStatic = -1;
/*  24 */   private static int iMaxStaticFinal = 16;
/*  25 */   private static World worldLast = null;
/*  26 */   private static int pcxLast = Integer.MIN_VALUE;
/*  27 */   private static int pczLast = Integer.MIN_VALUE;
/*     */   
/*     */   public static int getMaxChunkY(World world, Entity viewEntity, int renderDistanceChunks) {
/*  30 */     int i = MathHelper.floor_double(viewEntity.posX) >> 4;
/*  31 */     int j = MathHelper.floor_double(viewEntity.posY) >> 4;
/*  32 */     int k = MathHelper.floor_double(viewEntity.posZ) >> 4;
/*  33 */     Chunk chunk = world.getChunkFromChunkCoords(i, k);
/*  34 */     int l = i - renderDistanceChunks;
/*  35 */     int i1 = i + renderDistanceChunks;
/*  36 */     int j1 = k - renderDistanceChunks;
/*  37 */     int k1 = k + renderDistanceChunks;
/*     */     
/*  39 */     if (world != worldLast || i != pcxLast || k != pczLast) {
/*  40 */       counter = 0;
/*  41 */       iMaxStaticFinal = 16;
/*  42 */       worldLast = world;
/*  43 */       pcxLast = i;
/*  44 */       pczLast = k;
/*     */     } 
/*     */     
/*  47 */     if (counter == 0) {
/*  48 */       iMaxStatic = -1;
/*     */     }
/*     */     
/*  51 */     int l1 = iMaxStatic;
/*     */     
/*  53 */     switch (counter) {
/*     */       case 0:
/*  55 */         i1 = i;
/*  56 */         k1 = k;
/*     */         break;
/*     */       
/*     */       case 1:
/*  60 */         l = i;
/*  61 */         k1 = k;
/*     */         break;
/*     */       
/*     */       case 2:
/*  65 */         i1 = i;
/*  66 */         j1 = k;
/*     */         break;
/*     */       
/*     */       case 3:
/*  70 */         l = i;
/*  71 */         j1 = k;
/*     */         break;
/*     */     } 
/*  74 */     for (int i2 = l; i2 < i1; i2++) {
/*  75 */       for (int j2 = j1; j2 < k1; j2++) {
/*  76 */         Chunk chunk1 = world.getChunkFromChunkCoords(i2, j2);
/*     */         
/*  78 */         if (!chunk1.isEmpty()) {
/*  79 */           ExtendedBlockStorage[] aextendedblockstorage = chunk1.getBlockStorageArray();
/*     */           
/*  81 */           for (int k2 = aextendedblockstorage.length - 1; k2 > l1; k2--) {
/*  82 */             ExtendedBlockStorage extendedblockstorage = aextendedblockstorage[k2];
/*     */             
/*  84 */             if (extendedblockstorage != null && !extendedblockstorage.isEmpty()) {
/*  85 */               if (k2 > l1) {
/*  86 */                 l1 = k2;
/*     */               }
/*     */               
/*     */               break;
/*     */             } 
/*     */           } 
/*     */           
/*     */           try {
/*  94 */             Map<BlockPos, TileEntity> map = chunk1.getTileEntityMap();
/*     */             
/*  96 */             if (!map.isEmpty()) {
/*  97 */               for (BlockPos blockpos : map.keySet()) {
/*  98 */                 int l2 = blockpos.getY() >> 4;
/*     */                 
/* 100 */                 if (l2 > l1) {
/* 101 */                   l1 = l2;
/*     */                 }
/*     */               } 
/*     */             }
/* 105 */           } catch (ConcurrentModificationException concurrentModificationException) {}
/*     */ 
/*     */ 
/*     */           
/* 109 */           ClassInheritanceMultiMap[] classinheritancemultimap = chunk1.getEntityLists();
/*     */           
/* 111 */           for (int i3 = classinheritancemultimap.length - 1; i3 > l1; i3--) {
/* 112 */             ClassInheritanceMultiMap<Entity> classinheritancemultimap1 = classinheritancemultimap[i3];
/*     */             
/* 114 */             if (!classinheritancemultimap1.isEmpty() && (chunk1 != chunk || i3 != j || classinheritancemultimap1.size() != 1)) {
/* 115 */               if (i3 > l1) {
/* 116 */                 l1 = i3;
/*     */               }
/*     */               
/*     */               break;
/*     */             } 
/*     */           } 
/*     */         } 
/*     */       } 
/*     */     } 
/*     */     
/* 126 */     if (counter < 3) {
/* 127 */       iMaxStatic = l1;
/* 128 */       l1 = iMaxStaticFinal;
/*     */     } else {
/* 130 */       iMaxStaticFinal = l1;
/* 131 */       iMaxStatic = -1;
/*     */     } 
/*     */     
/* 134 */     counter = (counter + 1) % 4;
/* 135 */     return l1 << 4;
/*     */   }
/*     */   
/*     */   public static boolean isFinished() {
/* 139 */     return (counter == 0);
/*     */   }
/*     */   
/*     */   private static EnumFacing[][] makeEnumFacingArrays(boolean opposite) {
/* 143 */     int i = 64;
/* 144 */     EnumFacing[][] aenumfacing = new EnumFacing[i][];
/*     */     
/* 146 */     for (int j = 0; j < i; j++) {
/* 147 */       List<EnumFacing> list = new ArrayList<>();
/*     */       
/* 149 */       for (int k = 0; k < EnumFacing.VALUES.length; k++) {
/* 150 */         EnumFacing enumfacing = EnumFacing.VALUES[k];
/* 151 */         EnumFacing enumfacing1 = opposite ? enumfacing.getOpposite() : enumfacing;
/* 152 */         int l = 1 << enumfacing1.ordinal();
/*     */         
/* 154 */         if ((j & l) != 0) {
/* 155 */           list.add(enumfacing);
/*     */         }
/*     */       } 
/*     */       
/* 159 */       EnumFacing[] aenumfacing1 = list.<EnumFacing>toArray(new EnumFacing[list.size()]);
/* 160 */       aenumfacing[j] = aenumfacing1;
/*     */     } 
/*     */     
/* 163 */     return aenumfacing;
/*     */   }
/*     */   
/*     */   public static EnumFacing[] getFacingsNotOpposite(int setDisabled) {
/* 167 */     int i = (setDisabled ^ 0xFFFFFFFF) & 0x3F;
/* 168 */     return enumFacingOppositeArrays[i];
/*     */   }
/*     */   
/*     */   public static void reset() {
/* 172 */     worldLast = null;
/*     */   }
/*     */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\optifine\render\ChunkVisibility.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */