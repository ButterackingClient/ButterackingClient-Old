/*     */ package net.minecraft.world;
/*     */ 
/*     */ import com.google.common.collect.Lists;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
/*     */ import java.util.Random;
/*     */ import net.minecraft.block.BlockPortal;
/*     */ import net.minecraft.block.properties.IProperty;
/*     */ import net.minecraft.block.state.IBlockState;
/*     */ import net.minecraft.block.state.pattern.BlockPattern;
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.init.Blocks;
/*     */ import net.minecraft.util.BlockPos;
/*     */ import net.minecraft.util.EnumFacing;
/*     */ import net.minecraft.util.LongHashMap;
/*     */ import net.minecraft.util.MathHelper;
/*     */ import net.minecraft.util.Vec3i;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class Teleporter
/*     */ {
/*     */   private final WorldServer worldServerInstance;
/*     */   private final Random random;
/*  26 */   private final LongHashMap<PortalPosition> destinationCoordinateCache = new LongHashMap();
/*  27 */   private final List<Long> destinationCoordinateKeys = Lists.newArrayList();
/*     */   
/*     */   public Teleporter(WorldServer worldIn) {
/*  30 */     this.worldServerInstance = worldIn;
/*  31 */     this.random = new Random(worldIn.getSeed());
/*     */   }
/*     */   
/*     */   public void placeInPortal(Entity entityIn, float rotationYaw) {
/*  35 */     if (this.worldServerInstance.provider.getDimensionId() != 1) {
/*  36 */       if (!placeInExistingPortal(entityIn, rotationYaw)) {
/*  37 */         makePortal(entityIn);
/*  38 */         placeInExistingPortal(entityIn, rotationYaw);
/*     */       } 
/*     */     } else {
/*  41 */       int i = MathHelper.floor_double(entityIn.posX);
/*  42 */       int j = MathHelper.floor_double(entityIn.posY) - 1;
/*  43 */       int k = MathHelper.floor_double(entityIn.posZ);
/*  44 */       int l = 1;
/*  45 */       int i1 = 0;
/*     */       
/*  47 */       for (int j1 = -2; j1 <= 2; j1++) {
/*  48 */         for (int k1 = -2; k1 <= 2; k1++) {
/*  49 */           for (int l1 = -1; l1 < 3; l1++) {
/*  50 */             int i2 = i + k1 * l + j1 * i1;
/*  51 */             int j2 = j + l1;
/*  52 */             int k2 = k + k1 * i1 - j1 * l;
/*  53 */             boolean flag = (l1 < 0);
/*  54 */             this.worldServerInstance.setBlockState(new BlockPos(i2, j2, k2), flag ? Blocks.obsidian.getDefaultState() : Blocks.air.getDefaultState());
/*     */           } 
/*     */         } 
/*     */       } 
/*     */       
/*  59 */       entityIn.setLocationAndAngles(i, j, k, entityIn.rotationYaw, 0.0F);
/*  60 */       entityIn.motionX = entityIn.motionY = entityIn.motionZ = 0.0D;
/*     */     } 
/*     */   }
/*     */   
/*     */   public boolean placeInExistingPortal(Entity entityIn, float rotationYaw) {
/*  65 */     int i = 128;
/*  66 */     double d0 = -1.0D;
/*  67 */     int j = MathHelper.floor_double(entityIn.posX);
/*  68 */     int k = MathHelper.floor_double(entityIn.posZ);
/*  69 */     boolean flag = true;
/*  70 */     BlockPos blockpos = BlockPos.ORIGIN;
/*  71 */     long l = ChunkCoordIntPair.chunkXZ2Int(j, k);
/*     */     
/*  73 */     if (this.destinationCoordinateCache.containsItem(l)) {
/*  74 */       PortalPosition teleporter$portalposition = (PortalPosition)this.destinationCoordinateCache.getValueByKey(l);
/*  75 */       d0 = 0.0D;
/*  76 */       blockpos = teleporter$portalposition;
/*  77 */       teleporter$portalposition.lastUpdateTime = this.worldServerInstance.getTotalWorldTime();
/*  78 */       flag = false;
/*     */     } else {
/*  80 */       BlockPos blockpos3 = new BlockPos(entityIn);
/*     */       
/*  82 */       for (int i1 = -128; i1 <= 128; i1++) {
/*     */ 
/*     */         
/*  85 */         for (int j1 = -128; j1 <= 128; j1++) {
/*  86 */           for (BlockPos blockpos1 = blockpos3.add(i1, this.worldServerInstance.getActualHeight() - 1 - blockpos3.getY(), j1); blockpos1.getY() >= 0; blockpos1 = blockpos2) {
/*  87 */             BlockPos blockpos2 = blockpos1.down();
/*     */             
/*  89 */             if (this.worldServerInstance.getBlockState(blockpos1).getBlock() == Blocks.portal) {
/*  90 */               while (this.worldServerInstance.getBlockState(blockpos2 = blockpos1.down()).getBlock() == Blocks.portal) {
/*  91 */                 blockpos1 = blockpos2;
/*     */               }
/*     */               
/*  94 */               double d1 = blockpos1.distanceSq((Vec3i)blockpos3);
/*     */               
/*  96 */               if (d0 < 0.0D || d1 < d0) {
/*  97 */                 d0 = d1;
/*  98 */                 blockpos = blockpos1;
/*     */               } 
/*     */             } 
/*     */           } 
/*     */         } 
/*     */       } 
/*     */     } 
/*     */     
/* 106 */     if (d0 >= 0.0D) {
/* 107 */       if (flag) {
/* 108 */         this.destinationCoordinateCache.add(l, new PortalPosition(blockpos, this.worldServerInstance.getTotalWorldTime()));
/* 109 */         this.destinationCoordinateKeys.add(Long.valueOf(l));
/*     */       } 
/*     */       
/* 112 */       double d5 = blockpos.getX() + 0.5D;
/* 113 */       double d6 = blockpos.getY() + 0.5D;
/* 114 */       double d7 = blockpos.getZ() + 0.5D;
/* 115 */       BlockPattern.PatternHelper blockpattern$patternhelper = Blocks.portal.func_181089_f(this.worldServerInstance, blockpos);
/* 116 */       boolean flag1 = (blockpattern$patternhelper.getFinger().rotateY().getAxisDirection() == EnumFacing.AxisDirection.NEGATIVE);
/* 117 */       double d2 = (blockpattern$patternhelper.getFinger().getAxis() == EnumFacing.Axis.X) ? blockpattern$patternhelper.getPos().getZ() : blockpattern$patternhelper.getPos().getX();
/* 118 */       d6 = (blockpattern$patternhelper.getPos().getY() + 1) - (entityIn.func_181014_aG()).yCoord * blockpattern$patternhelper.func_181119_e();
/*     */       
/* 120 */       if (flag1) {
/* 121 */         d2++;
/*     */       }
/*     */       
/* 124 */       if (blockpattern$patternhelper.getFinger().getAxis() == EnumFacing.Axis.X) {
/* 125 */         d7 = d2 + (1.0D - (entityIn.func_181014_aG()).xCoord) * blockpattern$patternhelper.func_181118_d() * blockpattern$patternhelper.getFinger().rotateY().getAxisDirection().getOffset();
/*     */       } else {
/* 127 */         d5 = d2 + (1.0D - (entityIn.func_181014_aG()).xCoord) * blockpattern$patternhelper.func_181118_d() * blockpattern$patternhelper.getFinger().rotateY().getAxisDirection().getOffset();
/*     */       } 
/*     */       
/* 130 */       float f = 0.0F;
/* 131 */       float f1 = 0.0F;
/* 132 */       float f2 = 0.0F;
/* 133 */       float f3 = 0.0F;
/*     */       
/* 135 */       if (blockpattern$patternhelper.getFinger().getOpposite() == entityIn.getTeleportDirection()) {
/* 136 */         f = 1.0F;
/* 137 */         f1 = 1.0F;
/* 138 */       } else if (blockpattern$patternhelper.getFinger().getOpposite() == entityIn.getTeleportDirection().getOpposite()) {
/* 139 */         f = -1.0F;
/* 140 */         f1 = -1.0F;
/* 141 */       } else if (blockpattern$patternhelper.getFinger().getOpposite() == entityIn.getTeleportDirection().rotateY()) {
/* 142 */         f2 = 1.0F;
/* 143 */         f3 = -1.0F;
/*     */       } else {
/* 145 */         f2 = -1.0F;
/* 146 */         f3 = 1.0F;
/*     */       } 
/*     */       
/* 149 */       double d3 = entityIn.motionX;
/* 150 */       double d4 = entityIn.motionZ;
/* 151 */       entityIn.motionX = d3 * f + d4 * f3;
/* 152 */       entityIn.motionZ = d3 * f2 + d4 * f1;
/* 153 */       entityIn.rotationYaw = rotationYaw - (entityIn.getTeleportDirection().getOpposite().getHorizontalIndex() * 90) + (blockpattern$patternhelper.getFinger().getHorizontalIndex() * 90);
/* 154 */       entityIn.setLocationAndAngles(d5, d6, d7, entityIn.rotationYaw, entityIn.rotationPitch);
/* 155 */       return true;
/*     */     } 
/* 157 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean makePortal(Entity entityIn) {
/* 162 */     int i = 16;
/* 163 */     double d0 = -1.0D;
/* 164 */     int j = MathHelper.floor_double(entityIn.posX);
/* 165 */     int k = MathHelper.floor_double(entityIn.posY);
/* 166 */     int l = MathHelper.floor_double(entityIn.posZ);
/* 167 */     int i1 = j;
/* 168 */     int j1 = k;
/* 169 */     int k1 = l;
/* 170 */     int l1 = 0;
/* 171 */     int i2 = this.random.nextInt(4);
/* 172 */     BlockPos.MutableBlockPos blockpos$mutableblockpos = new BlockPos.MutableBlockPos();
/*     */     
/* 174 */     for (int j2 = j - i; j2 <= j + i; j2++) {
/* 175 */       double d1 = j2 + 0.5D - entityIn.posX;
/*     */       
/* 177 */       for (int l2 = l - i; l2 <= l + i; l2++) {
/* 178 */         double d2 = l2 + 0.5D - entityIn.posZ;
/*     */ 
/*     */         
/* 181 */         for (int j3 = this.worldServerInstance.getActualHeight() - 1; j3 >= 0; j3--) {
/* 182 */           if (this.worldServerInstance.isAirBlock((BlockPos)blockpos$mutableblockpos.set(j2, j3, l2))) {
/* 183 */             while (j3 > 0 && this.worldServerInstance.isAirBlock((BlockPos)blockpos$mutableblockpos.set(j2, j3 - 1, l2))) {
/* 184 */               j3--;
/*     */             }
/*     */             int k3;
/* 187 */             label168: for (k3 = i2; k3 < i2 + 4; k3++) {
/* 188 */               int l3 = k3 % 2;
/* 189 */               int i4 = 1 - l3;
/*     */               
/* 191 */               if (k3 % 4 >= 2) {
/* 192 */                 l3 = -l3;
/* 193 */                 i4 = -i4;
/*     */               } 
/*     */               
/* 196 */               for (int j4 = 0; j4 < 3; j4++) {
/* 197 */                 for (int k4 = 0; k4 < 4; k4++) {
/* 198 */                   for (int l4 = -1; l4 < 4; ) {
/* 199 */                     int i5 = j2 + (k4 - 1) * l3 + j4 * i4;
/* 200 */                     int j5 = j3 + l4;
/* 201 */                     int k5 = l2 + (k4 - 1) * i4 - j4 * l3;
/* 202 */                     blockpos$mutableblockpos.set(i5, j5, k5);
/*     */                     
/* 204 */                     if (l4 >= 0 || this.worldServerInstance.getBlockState((BlockPos)blockpos$mutableblockpos).getBlock().getMaterial().isSolid()) { if (l4 >= 0 && !this.worldServerInstance.isAirBlock((BlockPos)blockpos$mutableblockpos))
/*     */                         break label168;  l4++; }
/*     */                     
/*     */                     break label168;
/*     */                   } 
/*     */                 } 
/*     */               } 
/* 211 */               double d5 = j3 + 0.5D - entityIn.posY;
/* 212 */               double d7 = d1 * d1 + d5 * d5 + d2 * d2;
/*     */               
/* 214 */               if (d0 < 0.0D || d7 < d0) {
/* 215 */                 d0 = d7;
/* 216 */                 i1 = j2;
/* 217 */                 j1 = j3;
/* 218 */                 k1 = l2;
/* 219 */                 l1 = k3 % 4;
/*     */               } 
/*     */             } 
/*     */           } 
/*     */         } 
/*     */       } 
/*     */     } 
/*     */     
/* 227 */     if (d0 < 0.0D) {
/* 228 */       for (int l5 = j - i; l5 <= j + i; l5++) {
/* 229 */         double d3 = l5 + 0.5D - entityIn.posX;
/*     */         
/* 231 */         for (int j6 = l - i; j6 <= l + i; j6++) {
/* 232 */           double d4 = j6 + 0.5D - entityIn.posZ;
/*     */ 
/*     */           
/* 235 */           for (int i7 = this.worldServerInstance.getActualHeight() - 1; i7 >= 0; i7--) {
/* 236 */             if (this.worldServerInstance.isAirBlock((BlockPos)blockpos$mutableblockpos.set(l5, i7, j6))) {
/* 237 */               while (i7 > 0 && this.worldServerInstance.isAirBlock((BlockPos)blockpos$mutableblockpos.set(l5, i7 - 1, j6))) {
/* 238 */                 i7--;
/*     */               }
/*     */               int k7;
/* 241 */               label167: for (k7 = i2; k7 < i2 + 2; k7++) {
/* 242 */                 int j8 = k7 % 2;
/* 243 */                 int j9 = 1 - j8;
/*     */                 
/* 245 */                 for (int j10 = 0; j10 < 4; j10++) {
/* 246 */                   for (int j11 = -1; j11 < 4; ) {
/* 247 */                     int j12 = l5 + (j10 - 1) * j8;
/* 248 */                     int i13 = i7 + j11;
/* 249 */                     int j13 = j6 + (j10 - 1) * j9;
/* 250 */                     blockpos$mutableblockpos.set(j12, i13, j13);
/*     */                     
/* 252 */                     if (j11 >= 0 || this.worldServerInstance.getBlockState((BlockPos)blockpos$mutableblockpos).getBlock().getMaterial().isSolid()) { if (j11 >= 0 && !this.worldServerInstance.isAirBlock((BlockPos)blockpos$mutableblockpos))
/*     */                         break label167;  j11++; }
/*     */                     
/*     */                     break label167;
/*     */                   } 
/*     */                 } 
/* 258 */                 double d6 = i7 + 0.5D - entityIn.posY;
/* 259 */                 double d8 = d3 * d3 + d6 * d6 + d4 * d4;
/*     */                 
/* 261 */                 if (d0 < 0.0D || d8 < d0) {
/* 262 */                   d0 = d8;
/* 263 */                   i1 = l5;
/* 264 */                   j1 = i7;
/* 265 */                   k1 = j6;
/* 266 */                   l1 = k7 % 2;
/*     */                 } 
/*     */               } 
/*     */             } 
/*     */           } 
/*     */         } 
/*     */       } 
/*     */     }
/*     */     
/* 275 */     int i6 = i1;
/* 276 */     int k2 = j1;
/* 277 */     int k6 = k1;
/* 278 */     int l6 = l1 % 2;
/* 279 */     int i3 = 1 - l6;
/*     */     
/* 281 */     if (l1 % 4 >= 2) {
/* 282 */       l6 = -l6;
/* 283 */       i3 = -i3;
/*     */     } 
/*     */     
/* 286 */     if (d0 < 0.0D) {
/* 287 */       j1 = MathHelper.clamp_int(j1, 70, this.worldServerInstance.getActualHeight() - 10);
/* 288 */       k2 = j1;
/*     */       
/* 290 */       for (int j7 = -1; j7 <= 1; j7++) {
/* 291 */         for (int l7 = 1; l7 < 3; l7++) {
/* 292 */           for (int k8 = -1; k8 < 3; k8++) {
/* 293 */             int k9 = i6 + (l7 - 1) * l6 + j7 * i3;
/* 294 */             int k10 = k2 + k8;
/* 295 */             int k11 = k6 + (l7 - 1) * i3 - j7 * l6;
/* 296 */             boolean flag = (k8 < 0);
/* 297 */             this.worldServerInstance.setBlockState(new BlockPos(k9, k10, k11), flag ? Blocks.obsidian.getDefaultState() : Blocks.air.getDefaultState());
/*     */           } 
/*     */         } 
/*     */       } 
/*     */     } 
/*     */     
/* 303 */     IBlockState iblockstate = Blocks.portal.getDefaultState().withProperty((IProperty)BlockPortal.AXIS, (l6 != 0) ? (Comparable)EnumFacing.Axis.X : (Comparable)EnumFacing.Axis.Z);
/*     */     
/* 305 */     for (int i8 = 0; i8 < 4; i8++) {
/* 306 */       for (int l8 = 0; l8 < 4; l8++) {
/* 307 */         for (int l9 = -1; l9 < 4; l9++) {
/* 308 */           int l10 = i6 + (l8 - 1) * l6;
/* 309 */           int l11 = k2 + l9;
/* 310 */           int k12 = k6 + (l8 - 1) * i3;
/* 311 */           boolean flag1 = !(l8 != 0 && l8 != 3 && l9 != -1 && l9 != 3);
/* 312 */           this.worldServerInstance.setBlockState(new BlockPos(l10, l11, k12), flag1 ? Blocks.obsidian.getDefaultState() : iblockstate, 2);
/*     */         } 
/*     */       } 
/*     */       
/* 316 */       for (int i9 = 0; i9 < 4; i9++) {
/* 317 */         for (int i10 = -1; i10 < 4; i10++) {
/* 318 */           int i11 = i6 + (i9 - 1) * l6;
/* 319 */           int i12 = k2 + i10;
/* 320 */           int l12 = k6 + (i9 - 1) * i3;
/* 321 */           BlockPos blockpos = new BlockPos(i11, i12, l12);
/* 322 */           this.worldServerInstance.notifyNeighborsOfStateChange(blockpos, this.worldServerInstance.getBlockState(blockpos).getBlock());
/*     */         } 
/*     */       } 
/*     */     } 
/*     */     
/* 327 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void removeStalePortalLocations(long worldTime) {
/* 335 */     if (worldTime % 100L == 0L) {
/* 336 */       Iterator<Long> iterator = this.destinationCoordinateKeys.iterator();
/* 337 */       long i = worldTime - 300L;
/*     */       
/* 339 */       while (iterator.hasNext()) {
/* 340 */         Long olong = iterator.next();
/* 341 */         PortalPosition teleporter$portalposition = (PortalPosition)this.destinationCoordinateCache.getValueByKey(olong.longValue());
/*     */         
/* 343 */         if (teleporter$portalposition == null || teleporter$portalposition.lastUpdateTime < i) {
/* 344 */           iterator.remove();
/* 345 */           this.destinationCoordinateCache.remove(olong.longValue());
/*     */         } 
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   public class PortalPosition extends BlockPos {
/*     */     public long lastUpdateTime;
/*     */     
/*     */     public PortalPosition(BlockPos pos, long lastUpdate) {
/* 355 */       super(pos.getX(), pos.getY(), pos.getZ());
/* 356 */       this.lastUpdateTime = lastUpdate;
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\minecraft\world\Teleporter.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */