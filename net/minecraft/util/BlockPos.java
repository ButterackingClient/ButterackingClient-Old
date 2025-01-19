/*     */ package net.minecraft.util;
/*     */ 
/*     */ import com.google.common.collect.AbstractIterator;
/*     */ import java.util.Iterator;
/*     */ import net.minecraft.entity.Entity;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class BlockPos
/*     */   extends Vec3i
/*     */ {
/*  13 */   public static final BlockPos ORIGIN = new BlockPos(0, 0, 0);
/*  14 */   private static final int NUM_X_BITS = 1 + MathHelper.calculateLogBaseTwo(MathHelper.roundUpToPowerOfTwo(30000000));
/*  15 */   private static final int NUM_Z_BITS = NUM_X_BITS;
/*  16 */   private static final int NUM_Y_BITS = 64 - NUM_X_BITS - NUM_Z_BITS;
/*  17 */   private static final int Y_SHIFT = 0 + NUM_Z_BITS;
/*  18 */   private static final int X_SHIFT = Y_SHIFT + NUM_Y_BITS;
/*  19 */   private static final long X_MASK = (1L << NUM_X_BITS) - 1L;
/*  20 */   private static final long Y_MASK = (1L << NUM_Y_BITS) - 1L;
/*  21 */   private static final long Z_MASK = (1L << NUM_Z_BITS) - 1L;
/*     */   
/*     */   public BlockPos(int x, int y, int z) {
/*  24 */     super(x, y, z);
/*     */   }
/*     */   
/*     */   public BlockPos(double x, double y, double z) {
/*  28 */     super(x, y, z);
/*     */   }
/*     */   
/*     */   public BlockPos(Entity source) {
/*  32 */     this(source.posX, source.posY, source.posZ);
/*     */   }
/*     */   
/*     */   public BlockPos(Vec3 source) {
/*  36 */     this(source.xCoord, source.yCoord, source.zCoord);
/*     */   }
/*     */   
/*     */   public BlockPos(Vec3i source) {
/*  40 */     this(source.getX(), source.getY(), source.getZ());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public BlockPos add(double x, double y, double z) {
/*  47 */     return (x == 0.0D && y == 0.0D && z == 0.0D) ? this : new BlockPos(getX() + x, getY() + y, getZ() + z);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public BlockPos add(int x, int y, int z) {
/*  54 */     return (x == 0 && y == 0 && z == 0) ? this : new BlockPos(getX() + x, getY() + y, getZ() + z);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public BlockPos add(Vec3i vec) {
/*  61 */     return (vec.getX() == 0 && vec.getY() == 0 && vec.getZ() == 0) ? this : new BlockPos(getX() + vec.getX(), getY() + vec.getY(), getZ() + vec.getZ());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public BlockPos subtract(Vec3i vec) {
/*  68 */     return (vec.getX() == 0 && vec.getY() == 0 && vec.getZ() == 0) ? this : new BlockPos(getX() - vec.getX(), getY() - vec.getY(), getZ() - vec.getZ());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public BlockPos up() {
/*  75 */     return up(1);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public BlockPos up(int n) {
/*  82 */     return offset(EnumFacing.UP, n);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public BlockPos down() {
/*  89 */     return down(1);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public BlockPos down(int n) {
/*  96 */     return offset(EnumFacing.DOWN, n);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public BlockPos north() {
/* 103 */     return north(1);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public BlockPos north(int n) {
/* 110 */     return offset(EnumFacing.NORTH, n);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public BlockPos south() {
/* 117 */     return south(1);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public BlockPos south(int n) {
/* 124 */     return offset(EnumFacing.SOUTH, n);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public BlockPos west() {
/* 131 */     return west(1);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public BlockPos west(int n) {
/* 138 */     return offset(EnumFacing.WEST, n);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public BlockPos east() {
/* 145 */     return east(1);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public BlockPos east(int n) {
/* 152 */     return offset(EnumFacing.EAST, n);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public BlockPos offset(EnumFacing facing) {
/* 159 */     return offset(facing, 1);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public BlockPos offset(EnumFacing facing, int n) {
/* 166 */     return (n == 0) ? this : new BlockPos(getX() + facing.getFrontOffsetX() * n, getY() + facing.getFrontOffsetY() * n, getZ() + facing.getFrontOffsetZ() * n);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public BlockPos crossProduct(Vec3i vec) {
/* 173 */     return new BlockPos(getY() * vec.getZ() - getZ() * vec.getY(), getZ() * vec.getX() - getX() * vec.getZ(), getX() * vec.getY() - getY() * vec.getX());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public long toLong() {
/* 180 */     return (getX() & X_MASK) << X_SHIFT | (getY() & Y_MASK) << Y_SHIFT | (getZ() & Z_MASK) << 0L;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static BlockPos fromLong(long serialized) {
/* 187 */     int i = (int)(serialized << 64 - X_SHIFT - NUM_X_BITS >> 64 - NUM_X_BITS);
/* 188 */     int j = (int)(serialized << 64 - Y_SHIFT - NUM_Y_BITS >> 64 - NUM_Y_BITS);
/* 189 */     int k = (int)(serialized << 64 - NUM_Z_BITS >> 64 - NUM_Z_BITS);
/* 190 */     return new BlockPos(i, j, k);
/*     */   }
/*     */   
/*     */   public static Iterable<BlockPos> getAllInBox(BlockPos from, BlockPos to) {
/* 194 */     final BlockPos blockpos = new BlockPos(Math.min(from.getX(), to.getX()), Math.min(from.getY(), to.getY()), Math.min(from.getZ(), to.getZ()));
/* 195 */     final BlockPos blockpos1 = new BlockPos(Math.max(from.getX(), to.getX()), Math.max(from.getY(), to.getY()), Math.max(from.getZ(), to.getZ()));
/* 196 */     return new Iterable<BlockPos>() {
/*     */         public Iterator<BlockPos> iterator() {
/* 198 */           return (Iterator<BlockPos>)new AbstractIterator<BlockPos>() {
/* 199 */               private BlockPos lastReturned = null;
/*     */               
/*     */               protected BlockPos computeNext() {
/* 202 */                 if (this.lastReturned == null) {
/* 203 */                   this.lastReturned = blockpos;
/* 204 */                   return this.lastReturned;
/* 205 */                 }  if (this.lastReturned.equals(blockpos1)) {
/* 206 */                   return (BlockPos)endOfData();
/*     */                 }
/* 208 */                 int i = this.lastReturned.getX();
/* 209 */                 int j = this.lastReturned.getY();
/* 210 */                 int k = this.lastReturned.getZ();
/*     */                 
/* 212 */                 if (i < blockpos1.getX()) {
/* 213 */                   i++;
/* 214 */                 } else if (j < blockpos1.getY()) {
/* 215 */                   i = blockpos.getX();
/* 216 */                   j++;
/* 217 */                 } else if (k < blockpos1.getZ()) {
/* 218 */                   i = blockpos.getX();
/* 219 */                   j = blockpos.getY();
/* 220 */                   k++;
/*     */                 } 
/*     */                 
/* 223 */                 this.lastReturned = new BlockPos(i, j, k);
/* 224 */                 return this.lastReturned;
/*     */               }
/*     */             };
/*     */         }
/*     */       };
/*     */   }
/*     */ 
/*     */   
/*     */   public static Iterable<MutableBlockPos> getAllInBoxMutable(BlockPos from, BlockPos to) {
/* 233 */     final BlockPos blockpos = new BlockPos(Math.min(from.getX(), to.getX()), Math.min(from.getY(), to.getY()), Math.min(from.getZ(), to.getZ()));
/* 234 */     final BlockPos blockpos1 = new BlockPos(Math.max(from.getX(), to.getX()), Math.max(from.getY(), to.getY()), Math.max(from.getZ(), to.getZ()));
/* 235 */     return new Iterable<MutableBlockPos>() {
/*     */         public Iterator<BlockPos.MutableBlockPos> iterator() {
/* 237 */           return (Iterator<BlockPos.MutableBlockPos>)new AbstractIterator<BlockPos.MutableBlockPos>() {
/* 238 */               private BlockPos.MutableBlockPos theBlockPos = null;
/*     */               
/*     */               protected BlockPos.MutableBlockPos computeNext() {
/* 241 */                 if (this.theBlockPos == null) {
/* 242 */                   this.theBlockPos = new BlockPos.MutableBlockPos(blockpos.getX(), blockpos.getY(), blockpos.getZ());
/* 243 */                   return this.theBlockPos;
/* 244 */                 }  if (this.theBlockPos.equals(blockpos1)) {
/* 245 */                   return (BlockPos.MutableBlockPos)endOfData();
/*     */                 }
/* 247 */                 int i = this.theBlockPos.getX();
/* 248 */                 int j = this.theBlockPos.getY();
/* 249 */                 int k = this.theBlockPos.getZ();
/*     */                 
/* 251 */                 if (i < blockpos1.getX()) {
/* 252 */                   i++;
/* 253 */                 } else if (j < blockpos1.getY()) {
/* 254 */                   i = blockpos.getX();
/* 255 */                   j++;
/* 256 */                 } else if (k < blockpos1.getZ()) {
/* 257 */                   i = blockpos.getX();
/* 258 */                   j = blockpos.getY();
/* 259 */                   k++;
/*     */                 } 
/*     */                 
/* 262 */                 this.theBlockPos.x = i;
/* 263 */                 this.theBlockPos.y = j;
/* 264 */                 this.theBlockPos.z = k;
/* 265 */                 return this.theBlockPos;
/*     */               }
/*     */             };
/*     */         }
/*     */       };
/*     */   }
/*     */   
/*     */   public static final class MutableBlockPos
/*     */     extends BlockPos {
/*     */     private int x;
/*     */     private int y;
/*     */     private int z;
/*     */     
/*     */     public MutableBlockPos() {
/* 279 */       this(0, 0, 0);
/*     */     }
/*     */     
/*     */     public MutableBlockPos(int x_, int y_, int z_) {
/* 283 */       super(0, 0, 0);
/* 284 */       this.x = x_;
/* 285 */       this.y = y_;
/* 286 */       this.z = z_;
/*     */     }
/*     */     
/*     */     public int getX() {
/* 290 */       return this.x;
/*     */     }
/*     */     
/*     */     public int getY() {
/* 294 */       return this.y;
/*     */     }
/*     */     
/*     */     public int getZ() {
/* 298 */       return this.z;
/*     */     }
/*     */     
/*     */     public MutableBlockPos set(int xIn, int yIn, int zIn) {
/* 302 */       this.x = xIn;
/* 303 */       this.y = yIn;
/* 304 */       this.z = zIn;
/* 305 */       return this;
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\minecraf\\util\BlockPos.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */