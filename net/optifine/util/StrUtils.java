/*     */ package net.optifine.util;
/*     */ 
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
/*     */ import java.util.StringTokenizer;
/*     */ 
/*     */ public class StrUtils {
/*     */   public static boolean equalsMask(String str, String mask, char wildChar, char wildCharSingle) {
/*   9 */     if (mask != null && str != null) {
/*  10 */       if (mask.indexOf(wildChar) < 0) {
/*  11 */         return (mask.indexOf(wildCharSingle) < 0) ? mask.equals(str) : equalsMaskSingle(str, mask, wildCharSingle);
/*     */       }
/*  13 */       List<String> list = new ArrayList();
/*  14 */       char c = wildChar;
/*     */       
/*  16 */       if (mask.startsWith(c)) {
/*  17 */         list.add("");
/*     */       }
/*     */       
/*  20 */       StringTokenizer stringtokenizer = new StringTokenizer(mask, c);
/*     */       
/*  22 */       while (stringtokenizer.hasMoreElements()) {
/*  23 */         list.add(stringtokenizer.nextToken());
/*     */       }
/*     */       
/*  26 */       if (mask.endsWith(c)) {
/*  27 */         list.add("");
/*     */       }
/*     */       
/*  30 */       String s1 = list.get(0);
/*     */       
/*  32 */       if (!startsWithMaskSingle(str, s1, wildCharSingle)) {
/*  33 */         return false;
/*     */       }
/*  35 */       String s2 = list.get(list.size() - 1);
/*     */       
/*  37 */       if (!endsWithMaskSingle(str, s2, wildCharSingle)) {
/*  38 */         return false;
/*     */       }
/*  40 */       int i = 0;
/*     */       
/*  42 */       for (int j = 0; j < list.size(); j++) {
/*  43 */         String s3 = list.get(j);
/*     */         
/*  45 */         if (s3.length() > 0) {
/*  46 */           int k = indexOfMaskSingle(str, s3, i, wildCharSingle);
/*     */           
/*  48 */           if (k < 0) {
/*  49 */             return false;
/*     */           }
/*     */           
/*  52 */           i = k + s3.length();
/*     */         } 
/*     */       } 
/*     */       
/*  56 */       return true;
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/*  61 */     return (mask == str);
/*     */   }
/*     */ 
/*     */   
/*     */   private static boolean equalsMaskSingle(String str, String mask, char wildCharSingle) {
/*  66 */     if (str != null && mask != null) {
/*  67 */       if (str.length() != mask.length()) {
/*  68 */         return false;
/*     */       }
/*  70 */       for (int i = 0; i < mask.length(); i++) {
/*  71 */         char c0 = mask.charAt(i);
/*     */         
/*  73 */         if (c0 != wildCharSingle && str.charAt(i) != c0) {
/*  74 */           return false;
/*     */         }
/*     */       } 
/*     */       
/*  78 */       return true;
/*     */     } 
/*     */     
/*  81 */     return (str == mask);
/*     */   }
/*     */ 
/*     */   
/*     */   private static int indexOfMaskSingle(String str, String mask, int startPos, char wildCharSingle) {
/*  86 */     if (str != null && mask != null) {
/*  87 */       if (startPos >= 0 && startPos <= str.length()) {
/*  88 */         if (str.length() < startPos + mask.length()) {
/*  89 */           return -1;
/*     */         }
/*  91 */         for (int i = startPos; i + mask.length() <= str.length(); i++) {
/*  92 */           String s = str.substring(i, i + mask.length());
/*     */           
/*  94 */           if (equalsMaskSingle(s, mask, wildCharSingle)) {
/*  95 */             return i;
/*     */           }
/*     */         } 
/*     */         
/*  99 */         return -1;
/*     */       } 
/*     */       
/* 102 */       return -1;
/*     */     } 
/*     */     
/* 105 */     return -1;
/*     */   }
/*     */ 
/*     */   
/*     */   private static boolean endsWithMaskSingle(String str, String mask, char wildCharSingle) {
/* 110 */     if (str != null && mask != null) {
/* 111 */       if (str.length() < mask.length()) {
/* 112 */         return false;
/*     */       }
/* 114 */       String s = str.substring(str.length() - mask.length(), str.length());
/* 115 */       return equalsMaskSingle(s, mask, wildCharSingle);
/*     */     } 
/*     */     
/* 118 */     return (str == mask);
/*     */   }
/*     */ 
/*     */   
/*     */   private static boolean startsWithMaskSingle(String str, String mask, char wildCharSingle) {
/* 123 */     if (str != null && mask != null) {
/* 124 */       if (str.length() < mask.length()) {
/* 125 */         return false;
/*     */       }
/* 127 */       String s = str.substring(0, mask.length());
/* 128 */       return equalsMaskSingle(s, mask, wildCharSingle);
/*     */     } 
/*     */     
/* 131 */     return (str == mask);
/*     */   }
/*     */ 
/*     */   
/*     */   public static boolean equalsMask(String str, String[] masks, char wildChar) {
/* 136 */     for (int i = 0; i < masks.length; i++) {
/* 137 */       String s = masks[i];
/*     */       
/* 139 */       if (equalsMask(str, s, wildChar)) {
/* 140 */         return true;
/*     */       }
/*     */     } 
/*     */     
/* 144 */     return false;
/*     */   }
/*     */   
/*     */   public static boolean equalsMask(String str, String mask, char wildChar) {
/* 148 */     if (mask != null && str != null) {
/* 149 */       if (mask.indexOf(wildChar) < 0) {
/* 150 */         return mask.equals(str);
/*     */       }
/* 152 */       List<String> list = new ArrayList();
/* 153 */       char c = wildChar;
/*     */       
/* 155 */       if (mask.startsWith(c)) {
/* 156 */         list.add("");
/*     */       }
/*     */       
/* 159 */       StringTokenizer stringtokenizer = new StringTokenizer(mask, c);
/*     */       
/* 161 */       while (stringtokenizer.hasMoreElements()) {
/* 162 */         list.add(stringtokenizer.nextToken());
/*     */       }
/*     */       
/* 165 */       if (mask.endsWith(c)) {
/* 166 */         list.add("");
/*     */       }
/*     */       
/* 169 */       String s1 = list.get(0);
/*     */       
/* 171 */       if (!str.startsWith(s1)) {
/* 172 */         return false;
/*     */       }
/* 174 */       String s2 = list.get(list.size() - 1);
/*     */       
/* 176 */       if (!str.endsWith(s2)) {
/* 177 */         return false;
/*     */       }
/* 179 */       int i = 0;
/*     */       
/* 181 */       for (int j = 0; j < list.size(); j++) {
/* 182 */         String s3 = list.get(j);
/*     */         
/* 184 */         if (s3.length() > 0) {
/* 185 */           int k = str.indexOf(s3, i);
/*     */           
/* 187 */           if (k < 0) {
/* 188 */             return false;
/*     */           }
/*     */           
/* 191 */           i = k + s3.length();
/*     */         } 
/*     */       } 
/*     */       
/* 195 */       return true;
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 200 */     return (mask == str);
/*     */   }
/*     */ 
/*     */   
/*     */   public static String[] split(String str, String separators) {
/* 205 */     if (str != null && str.length() > 0) {
/* 206 */       if (separators == null) {
/* 207 */         return new String[] { str };
/*     */       }
/* 209 */       List<String> list = new ArrayList();
/* 210 */       int i = 0;
/*     */       
/* 212 */       for (int j = 0; j < str.length(); j++) {
/* 213 */         char c0 = str.charAt(j);
/*     */         
/* 215 */         if (equals(c0, separators)) {
/* 216 */           list.add(str.substring(i, j));
/* 217 */           i = j + 1;
/*     */         } 
/*     */       } 
/*     */       
/* 221 */       list.add(str.substring(i, str.length()));
/* 222 */       return list.<String>toArray(new String[list.size()]);
/*     */     } 
/*     */     
/* 225 */     return new String[0];
/*     */   }
/*     */ 
/*     */   
/*     */   private static boolean equals(char ch, String matches) {
/* 230 */     for (int i = 0; i < matches.length(); i++) {
/* 231 */       if (matches.charAt(i) == ch) {
/* 232 */         return true;
/*     */       }
/*     */     } 
/*     */     
/* 236 */     return false;
/*     */   }
/*     */   
/*     */   public static boolean equalsTrim(String a, String b) {
/* 240 */     if (a != null) {
/* 241 */       a = a.trim();
/*     */     }
/*     */     
/* 244 */     if (b != null) {
/* 245 */       b = b.trim();
/*     */     }
/*     */     
/* 248 */     return equals(a, b);
/*     */   }
/*     */   
/*     */   public static boolean isEmpty(String string) {
/* 252 */     return (string == null) ? true : ((string.trim().length() <= 0));
/*     */   }
/*     */   
/*     */   public static String stringInc(String str) {
/* 256 */     int i = parseInt(str, -1);
/*     */     
/* 258 */     if (i == -1) {
/* 259 */       return "";
/*     */     }
/*     */     
/* 262 */     int j = ++i;
/* 263 */     return (j.length() > str.length()) ? "" : fillLeft(i, str.length(), '0');
/*     */   }
/*     */ 
/*     */   
/*     */   public static int parseInt(String s, int defVal) {
/* 268 */     if (s == null) {
/* 269 */       return defVal;
/*     */     }
/*     */     try {
/* 272 */       return Integer.parseInt(s);
/* 273 */     } catch (NumberFormatException var3) {
/* 274 */       return defVal;
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public static boolean isFilled(String string) {
/* 280 */     return !isEmpty(string);
/*     */   }
/*     */   
/*     */   public static String addIfNotContains(String target, String source) {
/* 284 */     for (int i = 0; i < source.length(); i++) {
/* 285 */       if (target.indexOf(source.charAt(i)) < 0) {
/* 286 */         target = String.valueOf(target) + source.charAt(i);
/*     */       }
/*     */     } 
/*     */     
/* 290 */     return target;
/*     */   }
/*     */   
/*     */   public static String fillLeft(String s, int len, char fillChar) {
/* 294 */     if (s == null) {
/* 295 */       s = "";
/*     */     }
/*     */     
/* 298 */     if (s.length() >= len) {
/* 299 */       return s;
/*     */     }
/* 301 */     StringBuffer stringbuffer = new StringBuffer();
/* 302 */     int i = len - s.length();
/*     */     
/* 304 */     while (stringbuffer.length() < i) {
/* 305 */       stringbuffer.append(fillChar);
/*     */     }
/*     */     
/* 308 */     return String.valueOf(stringbuffer.toString()) + s;
/*     */   }
/*     */ 
/*     */   
/*     */   public static String fillRight(String s, int len, char fillChar) {
/* 313 */     if (s == null) {
/* 314 */       s = "";
/*     */     }
/*     */     
/* 317 */     if (s.length() >= len) {
/* 318 */       return s;
/*     */     }
/* 320 */     StringBuffer stringbuffer = new StringBuffer(s);
/*     */     
/* 322 */     while (stringbuffer.length() < len) {
/* 323 */       stringbuffer.append(fillChar);
/*     */     }
/*     */     
/* 326 */     return stringbuffer.toString();
/*     */   }
/*     */ 
/*     */   
/*     */   public static boolean equals(Object a, Object b) {
/* 331 */     return (a == b) ? true : ((a != null && a.equals(b)) ? true : ((b != null && b.equals(a))));
/*     */   }
/*     */   
/*     */   public static boolean startsWith(String str, String[] prefixes) {
/* 335 */     if (str == null)
/* 336 */       return false; 
/* 337 */     if (prefixes == null) {
/* 338 */       return false;
/*     */     }
/* 340 */     for (int i = 0; i < prefixes.length; i++) {
/* 341 */       String s = prefixes[i];
/*     */       
/* 343 */       if (str.startsWith(s)) {
/* 344 */         return true;
/*     */       }
/*     */     } 
/*     */     
/* 348 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public static boolean endsWith(String str, String[] suffixes) {
/* 353 */     if (str == null)
/* 354 */       return false; 
/* 355 */     if (suffixes == null) {
/* 356 */       return false;
/*     */     }
/* 358 */     for (int i = 0; i < suffixes.length; i++) {
/* 359 */       String s = suffixes[i];
/*     */       
/* 361 */       if (str.endsWith(s)) {
/* 362 */         return true;
/*     */       }
/*     */     } 
/*     */     
/* 366 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public static String removePrefix(String str, String prefix) {
/* 371 */     if (str != null && prefix != null) {
/* 372 */       if (str.startsWith(prefix)) {
/* 373 */         str = str.substring(prefix.length());
/*     */       }
/*     */       
/* 376 */       return str;
/*     */     } 
/* 378 */     return str;
/*     */   }
/*     */ 
/*     */   
/*     */   public static String removeSuffix(String str, String suffix) {
/* 383 */     if (str != null && suffix != null) {
/* 384 */       if (str.endsWith(suffix)) {
/* 385 */         str = str.substring(0, str.length() - suffix.length());
/*     */       }
/*     */       
/* 388 */       return str;
/*     */     } 
/* 390 */     return str;
/*     */   }
/*     */ 
/*     */   
/*     */   public static String replaceSuffix(String str, String suffix, String suffixNew) {
/* 395 */     if (str != null && suffix != null) {
/* 396 */       if (!str.endsWith(suffix)) {
/* 397 */         return str;
/*     */       }
/* 399 */       if (suffixNew == null) {
/* 400 */         suffixNew = "";
/*     */       }
/*     */       
/* 403 */       str = str.substring(0, str.length() - suffix.length());
/* 404 */       return String.valueOf(str) + suffixNew;
/*     */     } 
/*     */     
/* 407 */     return str;
/*     */   }
/*     */ 
/*     */   
/*     */   public static String replacePrefix(String str, String prefix, String prefixNew) {
/* 412 */     if (str != null && prefix != null) {
/* 413 */       if (!str.startsWith(prefix)) {
/* 414 */         return str;
/*     */       }
/* 416 */       if (prefixNew == null) {
/* 417 */         prefixNew = "";
/*     */       }
/*     */       
/* 420 */       str = str.substring(prefix.length());
/* 421 */       return String.valueOf(prefixNew) + str;
/*     */     } 
/*     */     
/* 424 */     return str;
/*     */   }
/*     */ 
/*     */   
/*     */   public static int findPrefix(String[] strs, String prefix) {
/* 429 */     if (strs != null && prefix != null) {
/* 430 */       for (int i = 0; i < strs.length; i++) {
/* 431 */         String s = strs[i];
/*     */         
/* 433 */         if (s.startsWith(prefix)) {
/* 434 */           return i;
/*     */         }
/*     */       } 
/*     */       
/* 438 */       return -1;
/*     */     } 
/* 440 */     return -1;
/*     */   }
/*     */ 
/*     */   
/*     */   public static int findSuffix(String[] strs, String suffix) {
/* 445 */     if (strs != null && suffix != null) {
/* 446 */       for (int i = 0; i < strs.length; i++) {
/* 447 */         String s = strs[i];
/*     */         
/* 449 */         if (s.endsWith(suffix)) {
/* 450 */           return i;
/*     */         }
/*     */       } 
/*     */       
/* 454 */       return -1;
/*     */     } 
/* 456 */     return -1;
/*     */   }
/*     */ 
/*     */   
/*     */   public static String[] remove(String[] strs, int start, int end) {
/* 461 */     if (strs == null)
/* 462 */       return strs; 
/* 463 */     if (end > 0 && start < strs.length) {
/* 464 */       if (start >= end) {
/* 465 */         return strs;
/*     */       }
/* 467 */       List<String> list = new ArrayList<>(strs.length);
/*     */       
/* 469 */       for (int i = 0; i < strs.length; i++) {
/* 470 */         String s = strs[i];
/*     */         
/* 472 */         if (i < start || i >= end) {
/* 473 */           list.add(s);
/*     */         }
/*     */       } 
/*     */       
/* 477 */       String[] astring = list.<String>toArray(new String[list.size()]);
/* 478 */       return astring;
/*     */     } 
/*     */     
/* 481 */     return strs;
/*     */   }
/*     */ 
/*     */   
/*     */   public static String removeSuffix(String str, String[] suffixes) {
/* 486 */     if (str != null && suffixes != null) {
/* 487 */       int i = str.length();
/*     */       
/* 489 */       for (int j = 0; j < suffixes.length; j++) {
/* 490 */         String s = suffixes[j];
/* 491 */         str = removeSuffix(str, s);
/*     */         
/* 493 */         if (str.length() != i) {
/*     */           break;
/*     */         }
/*     */       } 
/*     */       
/* 498 */       return str;
/*     */     } 
/* 500 */     return str;
/*     */   }
/*     */ 
/*     */   
/*     */   public static String removePrefix(String str, String[] prefixes) {
/* 505 */     if (str != null && prefixes != null) {
/* 506 */       int i = str.length();
/*     */       
/* 508 */       for (int j = 0; j < prefixes.length; j++) {
/* 509 */         String s = prefixes[j];
/* 510 */         str = removePrefix(str, s);
/*     */         
/* 512 */         if (str.length() != i) {
/*     */           break;
/*     */         }
/*     */       } 
/*     */       
/* 517 */       return str;
/*     */     } 
/* 519 */     return str;
/*     */   }
/*     */ 
/*     */   
/*     */   public static String removePrefixSuffix(String str, String[] prefixes, String[] suffixes) {
/* 524 */     str = removePrefix(str, prefixes);
/* 525 */     str = removeSuffix(str, suffixes);
/* 526 */     return str;
/*     */   }
/*     */   
/*     */   public static String removePrefixSuffix(String str, String prefix, String suffix) {
/* 530 */     return removePrefixSuffix(str, new String[] { prefix }, new String[] { suffix });
/*     */   }
/*     */   
/*     */   public static String getSegment(String str, String start, String end) {
/* 534 */     if (str != null && start != null && end != null) {
/* 535 */       int i = str.indexOf(start);
/*     */       
/* 537 */       if (i < 0) {
/* 538 */         return null;
/*     */       }
/* 540 */       int j = str.indexOf(end, i);
/* 541 */       return (j < 0) ? null : str.substring(i, j + end.length());
/*     */     } 
/*     */     
/* 544 */     return null;
/*     */   }
/*     */ 
/*     */   
/*     */   public static String addSuffixCheck(String str, String suffix) {
/* 549 */     return (str != null && suffix != null) ? (str.endsWith(suffix) ? str : (String.valueOf(str) + suffix)) : str;
/*     */   }
/*     */   
/*     */   public static String addPrefixCheck(String str, String prefix) {
/* 553 */     return (str != null && prefix != null) ? (str.endsWith(prefix) ? str : (String.valueOf(prefix) + str)) : str;
/*     */   }
/*     */   
/*     */   public static String trim(String str, String chars) {
/* 557 */     if (str != null && chars != null) {
/* 558 */       str = trimLeading(str, chars);
/* 559 */       str = trimTrailing(str, chars);
/* 560 */       return str;
/*     */     } 
/* 562 */     return str;
/*     */   }
/*     */ 
/*     */   
/*     */   public static String trimLeading(String str, String chars) {
/* 567 */     if (str != null && chars != null) {
/* 568 */       int i = str.length();
/*     */       
/* 570 */       for (int j = 0; j < i; j++) {
/* 571 */         char c0 = str.charAt(j);
/*     */         
/* 573 */         if (chars.indexOf(c0) < 0) {
/* 574 */           return str.substring(j);
/*     */         }
/*     */       } 
/*     */       
/* 578 */       return "";
/*     */     } 
/* 580 */     return str;
/*     */   }
/*     */ 
/*     */   
/*     */   public static String trimTrailing(String str, String chars) {
/* 585 */     if (str != null && chars != null) {
/* 586 */       int i = str.length();
/*     */       
/*     */       int j;
/* 589 */       for (j = i; j > 0; j--) {
/* 590 */         char c0 = str.charAt(j - 1);
/*     */         
/* 592 */         if (chars.indexOf(c0) < 0) {
/*     */           break;
/*     */         }
/*     */       } 
/*     */       
/* 597 */       return (j == i) ? str : str.substring(0, j);
/*     */     } 
/* 599 */     return str;
/*     */   }
/*     */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\optifin\\util\StrUtils.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */