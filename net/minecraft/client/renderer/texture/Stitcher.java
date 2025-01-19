/*     */ package net.minecraft.client.renderer.texture;
/*     */ 
/*     */ import com.google.common.collect.Lists;
/*     */ import com.google.common.collect.Sets;
/*     */ import java.util.Arrays;
/*     */ import java.util.List;
/*     */ import java.util.Set;
/*     */ import net.minecraft.client.renderer.StitcherException;
/*     */ import net.minecraft.util.MathHelper;
/*     */ 
/*     */ 
/*     */ public class Stitcher
/*     */ {
/*     */   private final int mipmapLevelStitcher;
/*  15 */   private final Set<Holder> setStitchHolders = Sets.newHashSetWithExpectedSize(256);
/*  16 */   private final List<Slot> stitchSlots = Lists.newArrayListWithCapacity(256);
/*     */   
/*     */   private int currentWidth;
/*     */   
/*     */   private int currentHeight;
/*     */   
/*     */   private final int maxWidth;
/*     */   
/*     */   private final int maxHeight;
/*     */   private final boolean forcePowerOf2;
/*     */   private final int maxTileDimension;
/*     */   
/*     */   public Stitcher(int maxTextureWidth, int maxTextureHeight, boolean p_i45095_3_, int p_i45095_4_, int mipmapLevel) {
/*  29 */     this.mipmapLevelStitcher = mipmapLevel;
/*  30 */     this.maxWidth = maxTextureWidth;
/*  31 */     this.maxHeight = maxTextureHeight;
/*  32 */     this.forcePowerOf2 = p_i45095_3_;
/*  33 */     this.maxTileDimension = p_i45095_4_;
/*     */   }
/*     */   
/*     */   public int getCurrentWidth() {
/*  37 */     return this.currentWidth;
/*     */   }
/*     */   
/*     */   public int getCurrentHeight() {
/*  41 */     return this.currentHeight;
/*     */   }
/*     */   
/*     */   public void addSprite(TextureAtlasSprite p_110934_1_) {
/*  45 */     Holder stitcher$holder = new Holder(p_110934_1_, this.mipmapLevelStitcher);
/*     */     
/*  47 */     if (this.maxTileDimension > 0) {
/*  48 */       stitcher$holder.setNewDimension(this.maxTileDimension);
/*     */     }
/*     */     
/*  51 */     this.setStitchHolders.add(stitcher$holder);
/*     */   }
/*     */   
/*     */   public void doStitch() {
/*  55 */     Holder[] astitcher$holder = this.setStitchHolders.<Holder>toArray(new Holder[this.setStitchHolders.size()]);
/*  56 */     Arrays.sort((Object[])astitcher$holder); byte b; int i;
/*     */     Holder[] arrayOfHolder1;
/*  58 */     for (i = (arrayOfHolder1 = astitcher$holder).length, b = 0; b < i; ) { Holder stitcher$holder = arrayOfHolder1[b];
/*  59 */       if (!allocateSlot(stitcher$holder)) {
/*  60 */         String s = String.format("Unable to fit: %s, size: %dx%d, atlas: %dx%d, atlasMax: %dx%d - Maybe try a lower resolution resourcepack?", new Object[] { stitcher$holder.getAtlasSprite().getIconName(), Integer.valueOf(stitcher$holder.getAtlasSprite().getIconWidth()), Integer.valueOf(stitcher$holder.getAtlasSprite().getIconHeight()), Integer.valueOf(this.currentWidth), Integer.valueOf(this.currentHeight), Integer.valueOf(this.maxWidth), Integer.valueOf(this.maxHeight) });
/*  61 */         throw new StitcherException(stitcher$holder, s);
/*     */       } 
/*     */       b++; }
/*     */     
/*  65 */     if (this.forcePowerOf2) {
/*  66 */       this.currentWidth = MathHelper.roundUpToPowerOfTwo(this.currentWidth);
/*  67 */       this.currentHeight = MathHelper.roundUpToPowerOfTwo(this.currentHeight);
/*     */     } 
/*     */   }
/*     */   
/*     */   public List<TextureAtlasSprite> getStichSlots() {
/*  72 */     List<Slot> list = Lists.newArrayList();
/*     */     
/*  74 */     for (Slot stitcher$slot : this.stitchSlots) {
/*  75 */       stitcher$slot.getAllStitchSlots(list);
/*     */     }
/*     */     
/*  78 */     List<TextureAtlasSprite> list1 = Lists.newArrayList();
/*     */     
/*  80 */     for (Slot stitcher$slot1 : list) {
/*  81 */       Holder stitcher$holder = stitcher$slot1.getStitchHolder();
/*  82 */       TextureAtlasSprite textureatlassprite = stitcher$holder.getAtlasSprite();
/*  83 */       textureatlassprite.initSprite(this.currentWidth, this.currentHeight, stitcher$slot1.getOriginX(), stitcher$slot1.getOriginY(), stitcher$holder.isRotated());
/*  84 */       list1.add(textureatlassprite);
/*     */     } 
/*     */     
/*  87 */     return list1;
/*     */   }
/*     */   
/*     */   private static int getMipmapDimension(int p_147969_0_, int p_147969_1_) {
/*  91 */     return (p_147969_0_ >> p_147969_1_) + (((p_147969_0_ & (1 << p_147969_1_) - 1) == 0) ? 0 : 1) << p_147969_1_;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private boolean allocateSlot(Holder p_94310_1_) {
/*  98 */     for (int i = 0; i < this.stitchSlots.size(); i++) {
/*  99 */       if (((Slot)this.stitchSlots.get(i)).addSlot(p_94310_1_)) {
/* 100 */         return true;
/*     */       }
/*     */       
/* 103 */       p_94310_1_.rotate();
/*     */       
/* 105 */       if (((Slot)this.stitchSlots.get(i)).addSlot(p_94310_1_)) {
/* 106 */         return true;
/*     */       }
/*     */       
/* 109 */       p_94310_1_.rotate();
/*     */     } 
/*     */     
/* 112 */     return expandAndAllocateSlot(p_94310_1_);
/*     */   }
/*     */ 
/*     */   
/*     */   private boolean expandAndAllocateSlot(Holder p_94311_1_) {
/*     */     boolean flag1;
/*     */     Slot stitcher$slot;
/* 119 */     int i = Math.min(p_94311_1_.getWidth(), p_94311_1_.getHeight());
/* 120 */     boolean flag = (this.currentWidth == 0 && this.currentHeight == 0);
/*     */ 
/*     */     
/* 123 */     if (this.forcePowerOf2) {
/* 124 */       int j = MathHelper.roundUpToPowerOfTwo(this.currentWidth);
/* 125 */       int k = MathHelper.roundUpToPowerOfTwo(this.currentHeight);
/* 126 */       int l = MathHelper.roundUpToPowerOfTwo(this.currentWidth + i);
/* 127 */       int i1 = MathHelper.roundUpToPowerOfTwo(this.currentHeight + i);
/* 128 */       boolean flag2 = (l <= this.maxWidth);
/* 129 */       boolean flag3 = (i1 <= this.maxHeight);
/*     */       
/* 131 */       if (!flag2 && !flag3) {
/* 132 */         return false;
/*     */       }
/*     */       
/* 135 */       boolean flag4 = (j != l);
/* 136 */       boolean flag5 = (k != i1);
/*     */       
/* 138 */       if (flag4 ^ flag5) {
/* 139 */         flag1 = !flag4;
/*     */       } else {
/* 141 */         flag1 = (flag2 && j <= k);
/*     */       } 
/*     */     } else {
/* 144 */       boolean flag6 = (this.currentWidth + i <= this.maxWidth);
/* 145 */       boolean flag7 = (this.currentHeight + i <= this.maxHeight);
/*     */       
/* 147 */       if (!flag6 && !flag7) {
/* 148 */         return false;
/*     */       }
/*     */       
/* 151 */       flag1 = (flag6 && (flag || this.currentWidth <= this.currentHeight));
/*     */     } 
/*     */     
/* 154 */     int j1 = Math.max(p_94311_1_.getWidth(), p_94311_1_.getHeight());
/*     */     
/* 156 */     if (MathHelper.roundUpToPowerOfTwo((!flag1 ? this.currentHeight : this.currentWidth) + j1) > (!flag1 ? this.maxHeight : this.maxWidth)) {
/* 157 */       return false;
/*     */     }
/*     */ 
/*     */     
/* 161 */     if (flag1) {
/* 162 */       if (p_94311_1_.getWidth() > p_94311_1_.getHeight()) {
/* 163 */         p_94311_1_.rotate();
/*     */       }
/*     */       
/* 166 */       if (this.currentHeight == 0) {
/* 167 */         this.currentHeight = p_94311_1_.getHeight();
/*     */       }
/*     */       
/* 170 */       stitcher$slot = new Slot(this.currentWidth, 0, p_94311_1_.getWidth(), this.currentHeight);
/* 171 */       this.currentWidth += p_94311_1_.getWidth();
/*     */     } else {
/* 173 */       stitcher$slot = new Slot(0, this.currentHeight, this.currentWidth, p_94311_1_.getHeight());
/* 174 */       this.currentHeight += p_94311_1_.getHeight();
/*     */     } 
/*     */     
/* 177 */     stitcher$slot.addSlot(p_94311_1_);
/* 178 */     this.stitchSlots.add(stitcher$slot);
/* 179 */     return true;
/*     */   }
/*     */   
/*     */   public static class Holder
/*     */     implements Comparable<Holder> {
/*     */     private final TextureAtlasSprite theTexture;
/*     */     private final int width;
/*     */     private final int height;
/*     */     private final int mipmapLevelHolder;
/*     */     private boolean rotated;
/* 189 */     private float scaleFactor = 1.0F;
/*     */     
/*     */     public Holder(TextureAtlasSprite p_i45094_1_, int p_i45094_2_) {
/* 192 */       this.theTexture = p_i45094_1_;
/* 193 */       this.width = p_i45094_1_.getIconWidth();
/* 194 */       this.height = p_i45094_1_.getIconHeight();
/* 195 */       this.mipmapLevelHolder = p_i45094_2_;
/* 196 */       this.rotated = (Stitcher.getMipmapDimension(this.height, p_i45094_2_) > Stitcher.getMipmapDimension(this.width, p_i45094_2_));
/*     */     }
/*     */     
/*     */     public TextureAtlasSprite getAtlasSprite() {
/* 200 */       return this.theTexture;
/*     */     }
/*     */     
/*     */     public int getWidth() {
/* 204 */       return this.rotated ? Stitcher.getMipmapDimension((int)(this.height * this.scaleFactor), this.mipmapLevelHolder) : Stitcher.getMipmapDimension((int)(this.width * this.scaleFactor), this.mipmapLevelHolder);
/*     */     }
/*     */     
/*     */     public int getHeight() {
/* 208 */       return this.rotated ? Stitcher.getMipmapDimension((int)(this.width * this.scaleFactor), this.mipmapLevelHolder) : Stitcher.getMipmapDimension((int)(this.height * this.scaleFactor), this.mipmapLevelHolder);
/*     */     }
/*     */     
/*     */     public void rotate() {
/* 212 */       this.rotated = !this.rotated;
/*     */     }
/*     */     
/*     */     public boolean isRotated() {
/* 216 */       return this.rotated;
/*     */     }
/*     */     
/*     */     public void setNewDimension(int p_94196_1_) {
/* 220 */       if (this.width > p_94196_1_ && this.height > p_94196_1_) {
/* 221 */         this.scaleFactor = p_94196_1_ / Math.min(this.width, this.height);
/*     */       }
/*     */     }
/*     */     
/*     */     public String toString() {
/* 226 */       return "Holder{width=" + this.width + ", height=" + this.height + '}';
/*     */     }
/*     */ 
/*     */     
/*     */     public int compareTo(Holder p_compareTo_1_) {
/*     */       int i;
/* 232 */       if (getHeight() == p_compareTo_1_.getHeight()) {
/* 233 */         if (getWidth() == p_compareTo_1_.getWidth()) {
/* 234 */           if (this.theTexture.getIconName() == null) {
/* 235 */             return (p_compareTo_1_.theTexture.getIconName() == null) ? 0 : -1;
/*     */           }
/*     */           
/* 238 */           return this.theTexture.getIconName().compareTo(p_compareTo_1_.theTexture.getIconName());
/*     */         } 
/*     */         
/* 241 */         i = (getWidth() < p_compareTo_1_.getWidth()) ? 1 : -1;
/*     */       } else {
/* 243 */         i = (getHeight() < p_compareTo_1_.getHeight()) ? 1 : -1;
/*     */       } 
/*     */       
/* 246 */       return i;
/*     */     }
/*     */   }
/*     */   
/*     */   public static class Slot {
/*     */     private final int originX;
/*     */     private final int originY;
/*     */     private final int width;
/*     */     private final int height;
/*     */     private List<Slot> subSlots;
/*     */     private Stitcher.Holder holder;
/*     */     
/*     */     public Slot(int p_i1277_1_, int p_i1277_2_, int widthIn, int heightIn) {
/* 259 */       this.originX = p_i1277_1_;
/* 260 */       this.originY = p_i1277_2_;
/* 261 */       this.width = widthIn;
/* 262 */       this.height = heightIn;
/*     */     }
/*     */     
/*     */     public Stitcher.Holder getStitchHolder() {
/* 266 */       return this.holder;
/*     */     }
/*     */     
/*     */     public int getOriginX() {
/* 270 */       return this.originX;
/*     */     }
/*     */     
/*     */     public int getOriginY() {
/* 274 */       return this.originY;
/*     */     }
/*     */     
/*     */     public boolean addSlot(Stitcher.Holder holderIn) {
/* 278 */       if (this.holder != null) {
/* 279 */         return false;
/*     */       }
/* 281 */       int i = holderIn.getWidth();
/* 282 */       int j = holderIn.getHeight();
/*     */       
/* 284 */       if (i <= this.width && j <= this.height) {
/* 285 */         if (i == this.width && j == this.height) {
/* 286 */           this.holder = holderIn;
/* 287 */           return true;
/*     */         } 
/* 289 */         if (this.subSlots == null) {
/* 290 */           this.subSlots = Lists.newArrayListWithCapacity(1);
/* 291 */           this.subSlots.add(new Slot(this.originX, this.originY, i, j));
/* 292 */           int k = this.width - i;
/* 293 */           int l = this.height - j;
/*     */           
/* 295 */           if (l > 0 && k > 0) {
/* 296 */             int i1 = Math.max(this.height, k);
/* 297 */             int j1 = Math.max(this.width, l);
/*     */             
/* 299 */             if (i1 >= j1) {
/* 300 */               this.subSlots.add(new Slot(this.originX, this.originY + j, i, l));
/* 301 */               this.subSlots.add(new Slot(this.originX + i, this.originY, k, this.height));
/*     */             } else {
/* 303 */               this.subSlots.add(new Slot(this.originX + i, this.originY, k, j));
/* 304 */               this.subSlots.add(new Slot(this.originX, this.originY + j, this.width, l));
/*     */             } 
/* 306 */           } else if (k == 0) {
/* 307 */             this.subSlots.add(new Slot(this.originX, this.originY + j, i, l));
/* 308 */           } else if (l == 0) {
/* 309 */             this.subSlots.add(new Slot(this.originX + i, this.originY, k, j));
/*     */           } 
/*     */         } 
/*     */         
/* 313 */         for (Slot stitcher$slot : this.subSlots) {
/* 314 */           if (stitcher$slot.addSlot(holderIn)) {
/* 315 */             return true;
/*     */           }
/*     */         } 
/*     */         
/* 319 */         return false;
/*     */       } 
/*     */       
/* 322 */       return false;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public void getAllStitchSlots(List<Slot> p_94184_1_) {
/* 328 */       if (this.holder != null) {
/* 329 */         p_94184_1_.add(this);
/* 330 */       } else if (this.subSlots != null) {
/* 331 */         for (Slot stitcher$slot : this.subSlots) {
/* 332 */           stitcher$slot.getAllStitchSlots(p_94184_1_);
/*     */         }
/*     */       } 
/*     */     }
/*     */     
/*     */     public String toString() {
/* 338 */       return "Slot{originX=" + this.originX + ", originY=" + this.originY + ", width=" + this.width + ", height=" + this.height + ", texture=" + this.holder + ", subSlots=" + this.subSlots + '}';
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\minecraft\client\renderer\texture\Stitcher.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */