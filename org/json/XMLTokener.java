/*     */ package org.json;
/*     */ 
/*     */ import java.io.Reader;
/*     */ import java.util.HashMap;
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
/*     */ public class XMLTokener
/*     */   extends JSONTokener
/*     */ {
/*  24 */   public static final HashMap<String, Character> entity = new HashMap<String, Character>(8); static {
/*  25 */     entity.put("amp", XML.AMP);
/*  26 */     entity.put("apos", XML.APOS);
/*  27 */     entity.put("gt", XML.GT);
/*  28 */     entity.put("lt", XML.LT);
/*  29 */     entity.put("quot", XML.QUOT);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public XMLTokener(Reader r) {
/*  37 */     super(r);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public XMLTokener(String s) {
/*  45 */     super(s);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String nextCDATA() throws JSONException {
/*  56 */     StringBuilder sb = new StringBuilder();
/*  57 */     while (more()) {
/*  58 */       char c = next();
/*  59 */       sb.append(c);
/*  60 */       int i = sb.length() - 3;
/*  61 */       if (i >= 0 && sb.charAt(i) == ']' && sb
/*  62 */         .charAt(i + 1) == ']' && sb.charAt(i + 2) == '>') {
/*  63 */         sb.setLength(i);
/*  64 */         return sb.toString();
/*     */       } 
/*     */     } 
/*  67 */     throw syntaxError("Unclosed CDATA");
/*     */   }
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
/*     */   public Object nextContent() throws JSONException {
/*     */     while (true) {
/*  85 */       char c = next();
/*  86 */       if (!Character.isWhitespace(c)) {
/*  87 */         if (c == '\000') {
/*  88 */           return null;
/*     */         }
/*  90 */         if (c == '<') {
/*  91 */           return XML.LT;
/*     */         }
/*  93 */         StringBuilder sb = new StringBuilder();
/*     */         while (true) {
/*  95 */           if (c == '\000') {
/*  96 */             return sb.toString().trim();
/*     */           }
/*  98 */           if (c == '<') {
/*  99 */             back();
/* 100 */             return sb.toString().trim();
/*     */           } 
/* 102 */           if (c == '&') {
/* 103 */             sb.append(nextEntity(c));
/*     */           } else {
/* 105 */             sb.append(c);
/*     */           } 
/* 107 */           c = next();
/*     */         } 
/*     */         break;
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Object nextEntity(char ampersand) throws JSONException {
/*     */     char c;
/* 122 */     StringBuilder sb = new StringBuilder();
/*     */     while (true) {
/* 124 */       c = next();
/* 125 */       if (Character.isLetterOrDigit(c) || c == '#')
/* 126 */       { sb.append(Character.toLowerCase(c)); continue; }  break;
/* 127 */     }  if (c == ';') {
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 133 */       String string = sb.toString();
/* 134 */       return unescapeEntity(string);
/*     */     } 
/*     */     throw syntaxError("Missing ';' in XML entity: &" + sb);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   static String unescapeEntity(String e) {
/* 144 */     if (e == null || e.isEmpty()) {
/* 145 */       return "";
/*     */     }
/*     */     
/* 148 */     if (e.charAt(0) == '#') {
/*     */       int cp;
/* 150 */       if (e.charAt(1) == 'x' || e.charAt(1) == 'X') {
/*     */         
/* 152 */         cp = Integer.parseInt(e.substring(2), 16);
/*     */       } else {
/*     */         
/* 155 */         cp = Integer.parseInt(e.substring(1));
/*     */       } 
/* 157 */       return new String(new int[] { cp }, 0, 1);
/*     */     } 
/* 159 */     Character knownEntity = entity.get(e);
/* 160 */     if (knownEntity == null)
/*     */     {
/* 162 */       return '&' + e + ';';
/*     */     }
/* 164 */     return knownEntity.toString();
/*     */   }
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
/*     */   public Object nextMeta() throws JSONException {
/*     */     char c, q;
/*     */     do {
/* 184 */       c = next();
/* 185 */     } while (Character.isWhitespace(c));
/* 186 */     switch (c) {
/*     */       case '\000':
/* 188 */         throw syntaxError("Misshaped meta tag");
/*     */       case '<':
/* 190 */         return XML.LT;
/*     */       case '>':
/* 192 */         return XML.GT;
/*     */       case '/':
/* 194 */         return XML.SLASH;
/*     */       case '=':
/* 196 */         return XML.EQ;
/*     */       case '!':
/* 198 */         return XML.BANG;
/*     */       case '?':
/* 200 */         return XML.QUEST;
/*     */       case '"':
/*     */       case '\'':
/* 203 */         q = c;
/*     */         while (true) {
/* 205 */           c = next();
/* 206 */           if (c == '\000') {
/* 207 */             throw syntaxError("Unterminated string");
/*     */           }
/* 209 */           if (c == q) {
/* 210 */             return Boolean.TRUE;
/*     */           }
/*     */         } 
/*     */     } 
/*     */     while (true)
/* 215 */     { c = next();
/* 216 */       if (Character.isWhitespace(c)) {
/* 217 */         return Boolean.TRUE;
/*     */       }
/* 219 */       switch (c)
/*     */       { case '\000':
/* 221 */           throw syntaxError("Unterminated string");
/*     */         case '!':
/*     */         case '"':
/*     */         case '\'':
/*     */         case '/':
/*     */         case '<':
/*     */         case '=':
/*     */         case '>':
/*     */         case '?':
/* 230 */           break; }  }  back();
/* 231 */     return Boolean.TRUE;
/*     */   }
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
/*     */   public Object nextToken() throws JSONException {
/*     */     char c, q;
/*     */     do {
/* 253 */       c = next();
/* 254 */     } while (Character.isWhitespace(c));
/* 255 */     switch (c) {
/*     */       case '\000':
/* 257 */         throw syntaxError("Misshaped element");
/*     */       case '<':
/* 259 */         throw syntaxError("Misplaced '<'");
/*     */       case '>':
/* 261 */         return XML.GT;
/*     */       case '/':
/* 263 */         return XML.SLASH;
/*     */       case '=':
/* 265 */         return XML.EQ;
/*     */       case '!':
/* 267 */         return XML.BANG;
/*     */       case '?':
/* 269 */         return XML.QUEST;
/*     */ 
/*     */ 
/*     */       
/*     */       case '"':
/*     */       case '\'':
/* 275 */         q = c;
/* 276 */         sb = new StringBuilder();
/*     */         while (true) {
/* 278 */           c = next();
/* 279 */           if (c == '\000') {
/* 280 */             throw syntaxError("Unterminated string");
/*     */           }
/* 282 */           if (c == q) {
/* 283 */             return sb.toString();
/*     */           }
/* 285 */           if (c == '&') {
/* 286 */             sb.append(nextEntity(c)); continue;
/*     */           } 
/* 288 */           sb.append(c);
/*     */         } 
/*     */     } 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 295 */     StringBuilder sb = new StringBuilder();
/*     */     while (true)
/* 297 */     { sb.append(c);
/* 298 */       c = next();
/* 299 */       if (Character.isWhitespace(c)) {
/* 300 */         return sb.toString();
/*     */       }
/* 302 */       switch (c)
/*     */       { case '\000':
/* 304 */           return sb.toString();
/*     */         case '!':
/*     */         case '/':
/*     */         case '=':
/*     */         case '>':
/*     */         case '?':
/*     */         case '[':
/*     */         case ']':
/* 312 */           back();
/* 313 */           return sb.toString();
/*     */         case '"':
/*     */         case '\'':
/*     */         case '<':
/* 317 */           break; }  }  throw syntaxError("Bad character in a name");
/*     */   }
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
/*     */   public void skipPast(String to) {
/* 337 */     int offset = 0;
/* 338 */     int length = to.length();
/* 339 */     char[] circle = new char[length];
/*     */ 
/*     */ 
/*     */     
/*     */     int i;
/*     */ 
/*     */     
/* 346 */     for (i = 0; i < length; i++) {
/* 347 */       char c = next();
/* 348 */       if (c == '\000') {
/*     */         return;
/*     */       }
/* 351 */       circle[i] = c;
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/*     */     while (true) {
/* 357 */       int j = offset;
/* 358 */       boolean b = true;
/*     */ 
/*     */ 
/*     */       
/* 362 */       for (i = 0; i < length; i++) {
/* 363 */         if (circle[j] != to.charAt(i)) {
/* 364 */           b = false;
/*     */           break;
/*     */         } 
/* 367 */         j++;
/* 368 */         if (j >= length) {
/* 369 */           j -= length;
/*     */         }
/*     */       } 
/*     */ 
/*     */ 
/*     */       
/* 375 */       if (b) {
/*     */         return;
/*     */       }
/*     */ 
/*     */ 
/*     */       
/* 381 */       char c = next();
/* 382 */       if (c == '\000') {
/*     */         return;
/*     */       }
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 389 */       circle[offset] = c;
/* 390 */       offset++;
/* 391 */       if (offset >= length)
/* 392 */         offset -= length; 
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\org\json\XMLTokener.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */