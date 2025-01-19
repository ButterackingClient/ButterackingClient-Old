/*     */ package kp.input;
/*     */ 
/*     */ import kp.Config;
/*     */ import kp.keyboards.KeyboardArray;
/*     */ 
/*     */ public class HangulAssembler
/*     */ {
/*     */   public static String disassemble(char c) {
/*   9 */     StringBuilder sb = new StringBuilder();
/*  10 */     if (isKorean(c)) {
/*  11 */       Hangul ch = new Hangul(c);
/*  12 */       sb.append(Code.CHO.getAt(ch.cho));
/*  13 */       sb.append(Code.JUNG.getAt(ch.jung));
/*  14 */       sb.append(Code.JONG.getAt(ch.jong));
/*     */     }
/*  16 */     else if (getJamoTier(c) > 0) {
/*  17 */       sb.append(convertKrEn(c, true));
/*     */     } else {
/*     */       
/*  20 */       sb.append(c);
/*     */     } 
/*  22 */     return sb.toString();
/*     */   }
/*     */   
/*     */   public static String convertKrEn(char c, boolean toEn) {
/*  26 */     if (toEn) {
/*  27 */       if (c >= 'ㄱ' && c <= 'ㅎ') {
/*  28 */         int i = "ㄱㄲㄴㄷㄸㄹㅁㅂㅃㅅㅆㅇㅈㅉㅊㅋㅌㅍㅎ".indexOf(c);
/*  29 */         if (i != -1) {
/*  30 */           return Code.CHO.getAt(i);
/*     */         }
/*  32 */         i = " ㄱㄲㄳㄴㄵㄶㄷㄹㄺㄻㄼㄽㄾㄿㅀㅁㅂㅄㅅㅆㅇㅈㅊㅋㅌㅍㅎ".indexOf(c);
/*  33 */         if (i != -1) {
/*  34 */           return Code.JONG.getAt(i);
/*     */         }
/*     */       }
/*  37 */       else if (c >= 'ㅏ' && c <= 'ㅣ') {
/*  38 */         return Code.JUNG.getAt(c - 12623);
/*     */       } 
/*     */     } else {
/*     */       
/*  42 */       int idx = Code.CHO.getIndex(c);
/*  43 */       if (idx != -1) {
/*  44 */         return String.valueOf("ㄱㄲㄴㄷㄸㄹㅁㅂㅃㅅㅆㅇㅈㅉㅊㅋㅌㅍㅎ".charAt(idx));
/*     */       }
/*  46 */       idx = Code.JUNG.getIndex(c);
/*  47 */       if (idx != -1) {
/*  48 */         return String.valueOf((char)(12623 + idx));
/*     */       }
/*  50 */       idx = Code.JONG.getIndex(c);
/*  51 */       if (idx != -1) {
/*  52 */         return String.valueOf(" ㄱㄲㄳㄴㄵㄶㄷㄹㄺㄻㄼㄽㄾㄿㅀㅁㅂㅄㅅㅆㅇㅈㅊㅋㅌㅍㅎ".charAt(idx - 1));
/*     */       }
/*     */     } 
/*  55 */     return String.valueOf(c);
/*     */   }
/*     */   
/*     */   public static String make(String text) {
/*  59 */     StringBuilder buf = new StringBuilder();
/*  60 */     int len = text.length();
/*  61 */     Hangul ch = new Hangul();
/*  62 */     boolean isDouble = false;
/*  63 */     char c = Character.MIN_VALUE;
/*  64 */     for (int i = 0; i < len; i++) {
/*  65 */       c = text.charAt(i);
/*  66 */       if (ch.cho == -1) {
/*  67 */         if (text.length() >= i + 2) {
/*  68 */           ch.cho = Code.CHO.getIndex(text.substring(i, i + 2));
/*  69 */           if (ch.cho != -1) {
/*  70 */             i++;
/*     */           }
/*     */         } 
/*  73 */         if (ch.cho == -1) {
/*  74 */           ch.cho = Code.CHO.getIndex(c);
/*     */         }
/*  76 */         if (ch.cho == -1) {
/*  77 */           ch.flush(buf);
/*  78 */           buf.append(convertKrEn(c, false));
/*     */         }
/*     */       
/*  81 */       } else if (ch.jung == -1) {
/*  82 */         if (text.length() >= i + 2) {
/*  83 */           ch.jung = Code.JUNG.getIndex(text.substring(i, i + 2));
/*  84 */           if (ch.jung != -1) {
/*  85 */             i++;
/*     */           }
/*     */         } 
/*  88 */         if (ch.jung == -1) {
/*  89 */           ch.jung = Code.JUNG.getIndex(c);
/*     */         }
/*  91 */         if (ch.jung == -1) {
/*  92 */           ch.flush(buf);
/*  93 */           buf.append(convertKrEn(c, false));
/*     */         }
/*     */       
/*  96 */       } else if (ch.jong == -1) {
/*  97 */         if (text.length() >= i + 2) {
/*  98 */           ch.jong = Code.JONG.getIndex(text.substring(i, i + 2));
/*  99 */           if (ch.jong != -1) {
/* 100 */             isDouble = true;
/* 101 */             i++;
/*     */           } 
/*     */         } 
/* 104 */         if (ch.jong == -1) {
/* 105 */           ch.jong = Code.JONG.getIndex(c);
/*     */         }
/* 107 */         if (ch.jong == -1) {
/* 108 */           ch.flush(buf);
/* 109 */           buf.append(convertKrEn(c, false));
/*     */         } else {
/*     */           
/* 112 */           if (text.length() > i + 1 && Code.JUNG.getIndex(text.charAt(i + 1)) != -1) {
/* 113 */             if (isDouble) {
/* 114 */               ch.jong = Code.JONG.getIndex(c);
/*     */             } else {
/*     */               
/* 117 */               ch.jong = -1;
/*     */             } 
/* 119 */             i--;
/*     */           } 
/* 121 */           ch.flush(buf);
/*     */         } 
/*     */       } 
/*     */     } 
/* 125 */     ch.flush(buf);
/* 126 */     return buf.toString();
/*     */   }
/*     */   
/*     */   public static boolean isKorean(char c) {
/* 130 */     return (c >= '가' && c <= '힣');
/*     */   }
/*     */   
/*     */   public static int getJamoTier(char c) {
/* 134 */     if (c >= 'ㄱ' && c <= 'ㅎ') {
/* 135 */       return 1;
/*     */     }
/* 137 */     if (c >= 'ㅏ' && c <= 'ㅣ') {
/* 138 */       return 2;
/*     */     }
/* 140 */     return 0;
/*     */   }
/*     */   
/*     */   public static boolean isAllowed(String s) {
/* 144 */     return !(Code.CHO.getIndex(s) == -1 && Code.JUNG.getIndex(s) == -1 && Code.JONG.getIndex(s) == -1);
/*     */   }
/*     */   
/*     */   public enum Code
/*     */   {
/* 149 */     CHO("CHO", 0),
/* 150 */     JUNG("JUNG", 1),
/* 151 */     JONG("JONG", 2);
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     private String getData() {
/* 157 */       KeyboardArray keyboard = Config.keyloader.getKeyboard();
/* 158 */       switch (this) {
/*     */         case null:
/* 160 */           return keyboard.getChosung();
/*     */         
/*     */         case JUNG:
/* 163 */           return keyboard.getJungsung();
/*     */         
/*     */         case JONG:
/* 166 */           return keyboard.getJongsung();
/*     */       } 
/*     */       
/* 169 */       return null;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public int getIndex(String cs) {
/* 175 */       int idx = getData().indexOf((cs.length() == 1) ? (String.valueOf(String.valueOf(cs)) + " ") : cs);
/* 176 */       if (cs.charAt(0) == ' ' || idx == -1 || idx % 2 == 1) {
/* 177 */         return -1;
/*     */       }
/* 179 */       return idx / 2;
/*     */     }
/*     */     
/*     */     public int getIndex(char c) {
/* 183 */       return getIndex(String.valueOf(c));
/*     */     }
/*     */     
/*     */     public String getAt(int x) {
/* 187 */       return getData().substring(x * 2, x * 2 + 2).replaceAll(" ", "");
/*     */     }
/*     */   }
/*     */   
/*     */   public static class Hangul
/*     */   {
/*     */     public int cho;
/*     */     public int jung;
/*     */     public int jong;
/*     */     
/*     */     public Hangul() {
/* 198 */       clear();
/*     */     }
/*     */     
/*     */     public Hangul(char c) {
/* 202 */       clear();
/* 203 */       if (HangulAssembler.isKorean(c)) {
/* 204 */         int pureKr = c - 44032;
/* 205 */         this.jong = pureKr % 28;
/* 206 */         this.jung = (pureKr - this.jong) / 28 % 21;
/* 207 */         this.cho = ((pureKr - this.jong) / 28 - this.jung) / 21;
/*     */       }
/* 209 */       else if (c >= 'ㄱ' && c <= 'ㅎ') {
/* 210 */         this.cho = HangulAssembler.Code.CHO.getIndex(String.valueOf(c));
/*     */       }
/* 212 */       else if (c >= 'ㅏ' && c <= 'ㅣ') {
/* 213 */         this.jung = HangulAssembler.Code.JUNG.getIndex(String.valueOf(c));
/*     */       } 
/*     */     }
/*     */     
/*     */     public void flush(StringBuilder sb) {
/* 218 */       if (this.cho != -1 && this.jung != -1) {
/* 219 */         sb.append((char)(44032 + (this.cho * 21 + this.jung) * 28 + Math.max(this.jong, 0)));
/*     */       } else {
/*     */         
/* 222 */         if (this.cho != -1) {
/* 223 */           sb.append("ㄱㄲㄴㄷㄸㄹㅁㅂㅃㅅㅆㅇㅈㅉㅊㅋㅌㅍㅎ".charAt(this.cho));
/*     */         }
/* 225 */         if (this.jung != -1) {
/* 226 */           sb.append(12623 + this.jung);
/*     */         }
/* 228 */         if (this.jong != -1) {
/* 229 */           sb.append(" ㄱㄲㄳㄴㄵㄶㄷㄹㄺㄻㄼㄽㄾㄿㅀㅁㅂㅄㅅㅆㅇㅈㅊㅋㅌㅍㅎ".charAt(this.jong));
/*     */         }
/*     */       } 
/* 232 */       clear();
/*     */     }
/*     */     
/*     */     public void clear() {
/* 236 */       this.cho = -1;
/* 237 */       this.jung = -1;
/* 238 */       this.jong = -1;
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\kp\input\HangulAssembler.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */