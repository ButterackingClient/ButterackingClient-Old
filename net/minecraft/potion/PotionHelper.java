/*     */ package net.minecraft.potion;
/*     */ 
/*     */ import com.google.common.collect.Lists;
/*     */ import com.google.common.collect.Maps;
/*     */ import java.util.Collection;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import net.minecraft.src.Config;
/*     */ import net.minecraft.util.IntegerCache;
/*     */ import net.optifine.CustomColors;
/*     */ 
/*     */ 
/*     */ public class PotionHelper
/*     */ {
/*  15 */   public static final String unusedString = null;
/*     */   public static final String sugarEffect = "-0+1-2-3&4-4+13";
/*     */   public static final String ghastTearEffect = "+0-1-2-3&4-4+13";
/*     */   public static final String spiderEyeEffect = "-0-1+2-3&4-4+13";
/*     */   public static final String fermentedSpiderEyeEffect = "-0+3-4+13";
/*     */   public static final String speckledMelonEffect = "+0-1+2-3&4-4+13";
/*     */   public static final String blazePowderEffect = "+0-1-2+3&4-4+13";
/*     */   public static final String magmaCreamEffect = "+0+1-2-3&4-4+13";
/*     */   public static final String redstoneEffect = "-5+6-7";
/*     */   public static final String glowstoneEffect = "+5-6-7";
/*     */   public static final String gunpowderEffect = "+14&13-13";
/*     */   public static final String goldenCarrotEffect = "-0+1+2-3+13&4-4";
/*     */   public static final String pufferfishEffect = "+0-1+2+3+13&4-4";
/*     */   public static final String rabbitFootEffect = "+0+1-2+3&4-4+13";
/*  29 */   private static final Map<Integer, String> potionRequirements = Maps.newHashMap();
/*  30 */   private static final Map<Integer, String> potionAmplifiers = Maps.newHashMap();
/*  31 */   private static final Map<Integer, Integer> DATAVALUE_COLORS = Maps.newHashMap();
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  36 */   private static final String[] potionPrefixes = new String[] { "potion.prefix.mundane", "potion.prefix.uninteresting", "potion.prefix.bland", "potion.prefix.clear", "potion.prefix.milky", "potion.prefix.diffuse", "potion.prefix.artless", "potion.prefix.thin", "potion.prefix.awkward", "potion.prefix.flat", "potion.prefix.bulky", "potion.prefix.bungling", "potion.prefix.buttered", "potion.prefix.smooth", "potion.prefix.suave", "potion.prefix.debonair", "potion.prefix.thick", "potion.prefix.elegant", "potion.prefix.fancy", "potion.prefix.charming", "potion.prefix.dashing", "potion.prefix.refined", "potion.prefix.cordial", "potion.prefix.sparkling", "potion.prefix.potent", "potion.prefix.foul", "potion.prefix.odorless", "potion.prefix.rank", "potion.prefix.harsh", "potion.prefix.acrid", "potion.prefix.gross", "potion.prefix.stinky" };
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static boolean checkFlag(int p_77914_0_, int p_77914_1_) {
/*  42 */     return ((p_77914_0_ & 1 << p_77914_1_) != 0);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static int isFlagSet(int p_77910_0_, int p_77910_1_) {
/*  49 */     return checkFlag(p_77910_0_, p_77910_1_) ? 1 : 0;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static int isFlagUnset(int p_77916_0_, int p_77916_1_) {
/*  56 */     return checkFlag(p_77916_0_, p_77916_1_) ? 0 : 1;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static int getPotionPrefixIndex(int dataValue) {
/*  63 */     return getPotionPrefixIndexFlags(dataValue, 5, 4, 3, 2, 1);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static int calcPotionLiquidColor(Collection<PotionEffect> p_77911_0_) {
/*  70 */     int i = 3694022;
/*     */     
/*  72 */     if (p_77911_0_ != null && !p_77911_0_.isEmpty()) {
/*  73 */       float f = 0.0F;
/*  74 */       float f1 = 0.0F;
/*  75 */       float f2 = 0.0F;
/*  76 */       float f3 = 0.0F;
/*     */       
/*  78 */       for (PotionEffect potioneffect : p_77911_0_) {
/*  79 */         if (potioneffect.getIsShowParticles()) {
/*  80 */           int j = Potion.potionTypes[potioneffect.getPotionID()].getLiquidColor();
/*     */           
/*  82 */           if (Config.isCustomColors()) {
/*  83 */             j = CustomColors.getPotionColor(potioneffect.getPotionID(), j);
/*     */           }
/*     */           
/*  86 */           for (int k = 0; k <= potioneffect.getAmplifier(); k++) {
/*  87 */             f += (j >> 16 & 0xFF) / 255.0F;
/*  88 */             f1 += (j >> 8 & 0xFF) / 255.0F;
/*  89 */             f2 += (j >> 0 & 0xFF) / 255.0F;
/*  90 */             f3++;
/*     */           } 
/*     */         } 
/*     */       } 
/*     */       
/*  95 */       if (f3 == 0.0F) {
/*  96 */         return 0;
/*     */       }
/*  98 */       f = f / f3 * 255.0F;
/*  99 */       f1 = f1 / f3 * 255.0F;
/* 100 */       f2 = f2 / f3 * 255.0F;
/* 101 */       return (int)f << 16 | (int)f1 << 8 | (int)f2;
/*     */     } 
/*     */     
/* 104 */     return Config.isCustomColors() ? CustomColors.getPotionColor(0, i) : i;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static boolean getAreAmbient(Collection<PotionEffect> potionEffects) {
/* 112 */     for (PotionEffect potioneffect : potionEffects) {
/* 113 */       if (!potioneffect.getIsAmbient()) {
/* 114 */         return false;
/*     */       }
/*     */     } 
/*     */     
/* 118 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static int getLiquidColor(int dataValue, boolean bypassCache) {
/* 125 */     Integer integer = IntegerCache.getInteger(dataValue);
/*     */     
/* 127 */     if (!bypassCache) {
/* 128 */       if (DATAVALUE_COLORS.containsKey(integer)) {
/* 129 */         return ((Integer)DATAVALUE_COLORS.get(integer)).intValue();
/*     */       }
/* 131 */       int i = calcPotionLiquidColor(getPotionEffects(integer.intValue(), false));
/* 132 */       DATAVALUE_COLORS.put(integer, Integer.valueOf(i));
/* 133 */       return i;
/*     */     } 
/*     */     
/* 136 */     return calcPotionLiquidColor(getPotionEffects(integer.intValue(), true));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static String getPotionPrefix(int dataValue) {
/* 144 */     int i = getPotionPrefixIndex(dataValue);
/* 145 */     return potionPrefixes[i];
/*     */   }
/*     */   
/*     */   private static int getPotionEffect(boolean p_77904_0_, boolean p_77904_1_, boolean p_77904_2_, int p_77904_3_, int p_77904_4_, int p_77904_5_, int p_77904_6_) {
/* 149 */     int i = 0;
/*     */     
/* 151 */     if (p_77904_0_) {
/* 152 */       i = isFlagUnset(p_77904_6_, p_77904_4_);
/* 153 */     } else if (p_77904_3_ != -1) {
/* 154 */       if (p_77904_3_ == 0 && countSetFlags(p_77904_6_) == p_77904_4_) {
/* 155 */         i = 1;
/* 156 */       } else if (p_77904_3_ == 1 && countSetFlags(p_77904_6_) > p_77904_4_) {
/* 157 */         i = 1;
/* 158 */       } else if (p_77904_3_ == 2 && countSetFlags(p_77904_6_) < p_77904_4_) {
/* 159 */         i = 1;
/*     */       } 
/*     */     } else {
/* 162 */       i = isFlagSet(p_77904_6_, p_77904_4_);
/*     */     } 
/*     */     
/* 165 */     if (p_77904_1_) {
/* 166 */       i *= p_77904_5_;
/*     */     }
/*     */     
/* 169 */     if (p_77904_2_) {
/* 170 */       i *= -1;
/*     */     }
/*     */     
/* 173 */     return i;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static int countSetFlags(int p_77907_0_) {
/* 182 */     for (int i = 0; p_77907_0_ > 0; i++) {
/* 183 */       p_77907_0_ &= p_77907_0_ - 1;
/*     */     }
/*     */     
/* 186 */     return i;
/*     */   }
/*     */   
/*     */   private static int parsePotionEffects(String p_77912_0_, int p_77912_1_, int p_77912_2_, int p_77912_3_) {
/* 190 */     if (p_77912_1_ < p_77912_0_.length() && p_77912_2_ >= 0 && p_77912_1_ < p_77912_2_) {
/* 191 */       int i = p_77912_0_.indexOf('|', p_77912_1_);
/*     */       
/* 193 */       if (i >= 0 && i < p_77912_2_) {
/* 194 */         int l1 = parsePotionEffects(p_77912_0_, p_77912_1_, i - 1, p_77912_3_);
/*     */         
/* 196 */         if (l1 > 0) {
/* 197 */           return l1;
/*     */         }
/* 199 */         int j2 = parsePotionEffects(p_77912_0_, i + 1, p_77912_2_, p_77912_3_);
/* 200 */         return (j2 > 0) ? j2 : 0;
/*     */       } 
/*     */       
/* 203 */       int j = p_77912_0_.indexOf('&', p_77912_1_);
/*     */       
/* 205 */       if (j >= 0 && j < p_77912_2_) {
/* 206 */         int i2 = parsePotionEffects(p_77912_0_, p_77912_1_, j - 1, p_77912_3_);
/*     */         
/* 208 */         if (i2 <= 0) {
/* 209 */           return 0;
/*     */         }
/* 211 */         int k2 = parsePotionEffects(p_77912_0_, j + 1, p_77912_2_, p_77912_3_);
/* 212 */         return (k2 <= 0) ? 0 : ((i2 > k2) ? i2 : k2);
/*     */       } 
/*     */       
/* 215 */       boolean flag = false;
/* 216 */       boolean flag1 = false;
/* 217 */       boolean flag2 = false;
/* 218 */       boolean flag3 = false;
/* 219 */       boolean flag4 = false;
/* 220 */       int k = -1;
/* 221 */       int l = 0;
/* 222 */       int i1 = 0;
/* 223 */       int j1 = 0;
/*     */       
/* 225 */       for (int k1 = p_77912_1_; k1 < p_77912_2_; k1++) {
/* 226 */         char c0 = p_77912_0_.charAt(k1);
/*     */         
/* 228 */         if (c0 >= '0' && c0 <= '9') {
/* 229 */           if (flag) {
/* 230 */             i1 = c0 - 48;
/* 231 */             flag1 = true;
/*     */           } else {
/* 233 */             l *= 10;
/* 234 */             l += c0 - 48;
/* 235 */             flag2 = true;
/*     */           } 
/* 237 */         } else if (c0 == '*') {
/* 238 */           flag = true;
/* 239 */         } else if (c0 == '!') {
/* 240 */           if (flag2) {
/* 241 */             j1 += getPotionEffect(flag3, flag1, flag4, k, l, i1, p_77912_3_);
/* 242 */             flag3 = false;
/* 243 */             flag4 = false;
/* 244 */             flag = false;
/* 245 */             flag1 = false;
/* 246 */             flag2 = false;
/* 247 */             i1 = 0;
/* 248 */             l = 0;
/* 249 */             k = -1;
/*     */           } 
/*     */           
/* 252 */           flag3 = true;
/* 253 */         } else if (c0 == '-') {
/* 254 */           if (flag2) {
/* 255 */             j1 += getPotionEffect(flag3, flag1, flag4, k, l, i1, p_77912_3_);
/* 256 */             flag3 = false;
/* 257 */             flag4 = false;
/* 258 */             flag = false;
/* 259 */             flag1 = false;
/* 260 */             flag2 = false;
/* 261 */             i1 = 0;
/* 262 */             l = 0;
/* 263 */             k = -1;
/*     */           } 
/*     */           
/* 266 */           flag4 = true;
/* 267 */         } else if (c0 != '=' && c0 != '<' && c0 != '>') {
/* 268 */           if (c0 == '+' && flag2) {
/* 269 */             j1 += getPotionEffect(flag3, flag1, flag4, k, l, i1, p_77912_3_);
/* 270 */             flag3 = false;
/* 271 */             flag4 = false;
/* 272 */             flag = false;
/* 273 */             flag1 = false;
/* 274 */             flag2 = false;
/* 275 */             i1 = 0;
/* 276 */             l = 0;
/* 277 */             k = -1;
/*     */           } 
/*     */         } else {
/* 280 */           if (flag2) {
/* 281 */             j1 += getPotionEffect(flag3, flag1, flag4, k, l, i1, p_77912_3_);
/* 282 */             flag3 = false;
/* 283 */             flag4 = false;
/* 284 */             flag = false;
/* 285 */             flag1 = false;
/* 286 */             flag2 = false;
/* 287 */             i1 = 0;
/* 288 */             l = 0;
/* 289 */             k = -1;
/*     */           } 
/*     */           
/* 292 */           if (c0 == '=') {
/* 293 */             k = 0;
/* 294 */           } else if (c0 == '<') {
/* 295 */             k = 2;
/* 296 */           } else if (c0 == '>') {
/* 297 */             k = 1;
/*     */           } 
/*     */         } 
/*     */       } 
/*     */       
/* 302 */       if (flag2) {
/* 303 */         j1 += getPotionEffect(flag3, flag1, flag4, k, l, i1, p_77912_3_);
/*     */       }
/*     */       
/* 306 */       return j1;
/*     */     } 
/*     */ 
/*     */     
/* 310 */     return 0;
/*     */   }
/*     */ 
/*     */   
/*     */   public static List<PotionEffect> getPotionEffects(int p_77917_0_, boolean p_77917_1_) {
/* 315 */     List<PotionEffect> list = null; byte b; int i;
/*     */     Potion[] arrayOfPotion;
/* 317 */     for (i = (arrayOfPotion = Potion.potionTypes).length, b = 0; b < i; ) { Potion potion = arrayOfPotion[b];
/* 318 */       if (potion != null && (!potion.isUsable() || p_77917_1_)) {
/* 319 */         String s = potionRequirements.get(Integer.valueOf(potion.getId()));
/*     */         
/* 321 */         if (s != null) {
/* 322 */           int j = parsePotionEffects(s, 0, s.length(), p_77917_0_);
/*     */           
/* 324 */           if (j > 0) {
/* 325 */             int k = 0;
/* 326 */             String s1 = potionAmplifiers.get(Integer.valueOf(potion.getId()));
/*     */             
/* 328 */             if (s1 != null) {
/* 329 */               k = parsePotionEffects(s1, 0, s1.length(), p_77917_0_);
/*     */               
/* 331 */               if (k < 0) {
/* 332 */                 k = 0;
/*     */               }
/*     */             } 
/*     */             
/* 336 */             if (potion.isInstant()) {
/* 337 */               j = 1;
/*     */             } else {
/* 339 */               j = 1200 * (j * 3 + (j - 1) * 2);
/* 340 */               j >>= k;
/* 341 */               j = (int)Math.round(j * potion.getEffectiveness());
/*     */               
/* 343 */               if ((p_77917_0_ & 0x4000) != 0) {
/* 344 */                 j = (int)Math.round(j * 0.75D + 0.5D);
/*     */               }
/*     */             } 
/*     */             
/* 348 */             if (list == null) {
/* 349 */               list = Lists.newArrayList();
/*     */             }
/*     */             
/* 352 */             PotionEffect potioneffect = new PotionEffect(potion.getId(), j, k);
/*     */             
/* 354 */             if ((p_77917_0_ & 0x4000) != 0) {
/* 355 */               potioneffect.setSplashPotion(true);
/*     */             }
/*     */             
/* 358 */             list.add(potioneffect);
/*     */           } 
/*     */         } 
/*     */       } 
/*     */       b++; }
/*     */     
/* 364 */     return list;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static int brewBitOperations(int p_77906_0_, int p_77906_1_, boolean p_77906_2_, boolean p_77906_3_, boolean p_77906_4_) {
/* 371 */     if (p_77906_4_) {
/* 372 */       if (!checkFlag(p_77906_0_, p_77906_1_)) {
/* 373 */         return 0;
/*     */       }
/* 375 */     } else if (p_77906_2_) {
/* 376 */       p_77906_0_ &= 1 << p_77906_1_ ^ 0xFFFFFFFF;
/* 377 */     } else if (p_77906_3_) {
/* 378 */       if ((p_77906_0_ & 1 << p_77906_1_) == 0) {
/* 379 */         p_77906_0_ |= 1 << p_77906_1_;
/*     */       } else {
/* 381 */         p_77906_0_ &= 1 << p_77906_1_ ^ 0xFFFFFFFF;
/*     */       } 
/*     */     } else {
/* 384 */       p_77906_0_ |= 1 << p_77906_1_;
/*     */     } 
/*     */     
/* 387 */     return p_77906_0_;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static int applyIngredient(int p_77913_0_, String p_77913_1_) {
/* 394 */     int i = 0;
/* 395 */     int j = p_77913_1_.length();
/* 396 */     boolean flag = false;
/* 397 */     boolean flag1 = false;
/* 398 */     boolean flag2 = false;
/* 399 */     boolean flag3 = false;
/* 400 */     int k = 0;
/*     */     
/* 402 */     for (int l = i; l < j; l++) {
/* 403 */       char c0 = p_77913_1_.charAt(l);
/*     */       
/* 405 */       if (c0 >= '0' && c0 <= '9') {
/* 406 */         k *= 10;
/* 407 */         k += c0 - 48;
/* 408 */         flag = true;
/* 409 */       } else if (c0 == '!') {
/* 410 */         if (flag) {
/* 411 */           p_77913_0_ = brewBitOperations(p_77913_0_, k, flag2, flag1, flag3);
/* 412 */           flag3 = false;
/* 413 */           flag1 = false;
/* 414 */           flag2 = false;
/* 415 */           flag = false;
/* 416 */           k = 0;
/*     */         } 
/*     */         
/* 419 */         flag1 = true;
/* 420 */       } else if (c0 == '-') {
/* 421 */         if (flag) {
/* 422 */           p_77913_0_ = brewBitOperations(p_77913_0_, k, flag2, flag1, flag3);
/* 423 */           flag3 = false;
/* 424 */           flag1 = false;
/* 425 */           flag2 = false;
/* 426 */           flag = false;
/* 427 */           k = 0;
/*     */         } 
/*     */         
/* 430 */         flag2 = true;
/* 431 */       } else if (c0 == '+') {
/* 432 */         if (flag) {
/* 433 */           p_77913_0_ = brewBitOperations(p_77913_0_, k, flag2, flag1, flag3);
/* 434 */           flag3 = false;
/* 435 */           flag1 = false;
/* 436 */           flag2 = false;
/* 437 */           flag = false;
/* 438 */           k = 0;
/*     */         } 
/* 440 */       } else if (c0 == '&') {
/* 441 */         if (flag) {
/* 442 */           p_77913_0_ = brewBitOperations(p_77913_0_, k, flag2, flag1, flag3);
/* 443 */           flag3 = false;
/* 444 */           flag1 = false;
/* 445 */           flag2 = false;
/* 446 */           flag = false;
/* 447 */           k = 0;
/*     */         } 
/*     */         
/* 450 */         flag3 = true;
/*     */       } 
/*     */     } 
/*     */     
/* 454 */     if (flag) {
/* 455 */       p_77913_0_ = brewBitOperations(p_77913_0_, k, flag2, flag1, flag3);
/*     */     }
/*     */     
/* 458 */     return p_77913_0_ & 0x7FFF;
/*     */   }
/*     */   
/*     */   public static int getPotionPrefixIndexFlags(int p_77908_0_, int p_77908_1_, int p_77908_2_, int p_77908_3_, int p_77908_4_, int p_77908_5_) {
/* 462 */     return (checkFlag(p_77908_0_, p_77908_1_) ? 16 : 0) | (checkFlag(p_77908_0_, p_77908_2_) ? 8 : 0) | (checkFlag(p_77908_0_, p_77908_3_) ? 4 : 0) | (checkFlag(p_77908_0_, p_77908_4_) ? 2 : 0) | (checkFlag(p_77908_0_, p_77908_5_) ? 1 : 0);
/*     */   }
/*     */   
/*     */   static {
/* 466 */     potionRequirements.put(Integer.valueOf(Potion.regeneration.getId()), "0 & !1 & !2 & !3 & 0+6");
/* 467 */     potionRequirements.put(Integer.valueOf(Potion.moveSpeed.getId()), "!0 & 1 & !2 & !3 & 1+6");
/* 468 */     potionRequirements.put(Integer.valueOf(Potion.fireResistance.getId()), "0 & 1 & !2 & !3 & 0+6");
/* 469 */     potionRequirements.put(Integer.valueOf(Potion.heal.getId()), "0 & !1 & 2 & !3");
/* 470 */     potionRequirements.put(Integer.valueOf(Potion.poison.getId()), "!0 & !1 & 2 & !3 & 2+6");
/* 471 */     potionRequirements.put(Integer.valueOf(Potion.weakness.getId()), "!0 & !1 & !2 & 3 & 3+6");
/* 472 */     potionRequirements.put(Integer.valueOf(Potion.harm.getId()), "!0 & !1 & 2 & 3");
/* 473 */     potionRequirements.put(Integer.valueOf(Potion.moveSlowdown.getId()), "!0 & 1 & !2 & 3 & 3+6");
/* 474 */     potionRequirements.put(Integer.valueOf(Potion.damageBoost.getId()), "0 & !1 & !2 & 3 & 3+6");
/* 475 */     potionRequirements.put(Integer.valueOf(Potion.nightVision.getId()), "!0 & 1 & 2 & !3 & 2+6");
/* 476 */     potionRequirements.put(Integer.valueOf(Potion.invisibility.getId()), "!0 & 1 & 2 & 3 & 2+6");
/* 477 */     potionRequirements.put(Integer.valueOf(Potion.waterBreathing.getId()), "0 & !1 & 2 & 3 & 2+6");
/* 478 */     potionRequirements.put(Integer.valueOf(Potion.jump.getId()), "0 & 1 & !2 & 3 & 3+6");
/* 479 */     potionAmplifiers.put(Integer.valueOf(Potion.moveSpeed.getId()), "5");
/* 480 */     potionAmplifiers.put(Integer.valueOf(Potion.digSpeed.getId()), "5");
/* 481 */     potionAmplifiers.put(Integer.valueOf(Potion.damageBoost.getId()), "5");
/* 482 */     potionAmplifiers.put(Integer.valueOf(Potion.regeneration.getId()), "5");
/* 483 */     potionAmplifiers.put(Integer.valueOf(Potion.harm.getId()), "5");
/* 484 */     potionAmplifiers.put(Integer.valueOf(Potion.heal.getId()), "5");
/* 485 */     potionAmplifiers.put(Integer.valueOf(Potion.resistance.getId()), "5");
/* 486 */     potionAmplifiers.put(Integer.valueOf(Potion.poison.getId()), "5");
/* 487 */     potionAmplifiers.put(Integer.valueOf(Potion.jump.getId()), "5");
/*     */   }
/*     */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\minecraft\potion\PotionHelper.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */