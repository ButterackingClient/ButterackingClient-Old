/*     */ package org.json;
/*     */ 
/*     */ import java.io.Reader;
/*     */ import java.io.StringReader;
/*     */ import java.math.BigDecimal;
/*     */ import java.math.BigInteger;
/*     */ import java.util.Iterator;
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
/*     */ public class XML
/*     */ {
/*  25 */   public static final Character AMP = Character.valueOf('&');
/*     */ 
/*     */   
/*  28 */   public static final Character APOS = Character.valueOf('\'');
/*     */ 
/*     */   
/*  31 */   public static final Character BANG = Character.valueOf('!');
/*     */ 
/*     */   
/*  34 */   public static final Character EQ = Character.valueOf('=');
/*     */ 
/*     */   
/*  37 */   public static final Character GT = Character.valueOf('>');
/*     */ 
/*     */   
/*  40 */   public static final Character LT = Character.valueOf('<');
/*     */ 
/*     */   
/*  43 */   public static final Character QUEST = Character.valueOf('?');
/*     */ 
/*     */   
/*  46 */   public static final Character QUOT = Character.valueOf('"');
/*     */ 
/*     */   
/*  49 */   public static final Character SLASH = Character.valueOf('/');
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static final String NULL_ATTR = "xsi:nil";
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static final String TYPE_ATTR = "xsi:type";
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static Iterable<Integer> codePointIterator(final String string) {
/*  70 */     return new Iterable<Integer>()
/*     */       {
/*     */         public Iterator<Integer> iterator() {
/*  73 */           return new Iterator<Integer>() {
/*  74 */               private int nextIndex = 0;
/*  75 */               private int length = string.length();
/*     */ 
/*     */               
/*     */               public boolean hasNext() {
/*  79 */                 return (this.nextIndex < this.length);
/*     */               }
/*     */ 
/*     */               
/*     */               public Integer next() {
/*  84 */                 int result = string.codePointAt(this.nextIndex);
/*  85 */                 this.nextIndex += Character.charCount(result);
/*  86 */                 return Integer.valueOf(result);
/*     */               }
/*     */ 
/*     */               
/*     */               public void remove() {
/*  91 */                 throw new UnsupportedOperationException();
/*     */               }
/*     */             };
/*     */         }
/*     */       };
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
/*     */   public static String escape(String string) {
/* 114 */     StringBuilder sb = new StringBuilder(string.length());
/* 115 */     for (Iterator<Integer> iterator = codePointIterator(string).iterator(); iterator.hasNext(); ) { int cp = ((Integer)iterator.next()).intValue();
/* 116 */       switch (cp) {
/*     */         case 38:
/* 118 */           sb.append("&amp;");
/*     */           continue;
/*     */         case 60:
/* 121 */           sb.append("&lt;");
/*     */           continue;
/*     */         case 62:
/* 124 */           sb.append("&gt;");
/*     */           continue;
/*     */         case 34:
/* 127 */           sb.append("&quot;");
/*     */           continue;
/*     */         case 39:
/* 130 */           sb.append("&apos;");
/*     */           continue;
/*     */       } 
/* 133 */       if (mustEscape(cp)) {
/* 134 */         sb.append("&#x");
/* 135 */         sb.append(Integer.toHexString(cp));
/* 136 */         sb.append(';'); continue;
/*     */       } 
/* 138 */       sb.appendCodePoint(cp); }
/*     */ 
/*     */ 
/*     */     
/* 142 */     return sb.toString();
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
/*     */   private static boolean mustEscape(int cp) {
/* 158 */     return ((Character.isISOControl(cp) && cp != 9 && cp != 10 && cp != 13) || ((cp < 32 || cp > 55295) && (cp < 57344 || cp > 65533) && (cp < 65536 || cp > 1114111)));
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
/*     */   
/*     */   public static String unescape(String string) {
/* 179 */     StringBuilder sb = new StringBuilder(string.length());
/* 180 */     for (int i = 0, length = string.length(); i < length; i++) {
/* 181 */       char c = string.charAt(i);
/* 182 */       if (c == '&') {
/* 183 */         int semic = string.indexOf(';', i);
/* 184 */         if (semic > i) {
/* 185 */           String entity = string.substring(i + 1, semic);
/* 186 */           sb.append(XMLTokener.unescapeEntity(entity));
/*     */           
/* 188 */           i += entity.length() + 1;
/*     */         }
/*     */         else {
/*     */           
/* 192 */           sb.append(c);
/*     */         } 
/*     */       } else {
/*     */         
/* 196 */         sb.append(c);
/*     */       } 
/*     */     } 
/* 199 */     return sb.toString();
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
/*     */   public static void noSpace(String string) throws JSONException {
/* 211 */     int length = string.length();
/* 212 */     if (length == 0) {
/* 213 */       throw new JSONException("Empty string.");
/*     */     }
/* 215 */     for (int i = 0; i < length; i++) {
/* 216 */       if (Character.isWhitespace(string.charAt(i))) {
/* 217 */         throw new JSONException("'" + string + "' contains a space character.");
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
/*     */   private static boolean parse(XMLTokener x, JSONObject context, String name, XMLParserConfiguration config) throws JSONException {
/* 239 */     JSONObject jsonObject = null;
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
/* 255 */     Object token = x.nextToken();
/*     */ 
/*     */ 
/*     */     
/* 259 */     if (token == BANG) {
/* 260 */       char c = x.next();
/* 261 */       if (c == '-') {
/* 262 */         if (x.next() == '-') {
/* 263 */           x.skipPast("-->");
/* 264 */           return false;
/*     */         } 
/* 266 */         x.back();
/* 267 */       } else if (c == '[') {
/* 268 */         token = x.nextToken();
/* 269 */         if ("CDATA".equals(token) && 
/* 270 */           x.next() == '[') {
/* 271 */           String string = x.nextCDATA();
/* 272 */           if (string.length() > 0) {
/* 273 */             context.accumulate(config.getcDataTagName(), string);
/*     */           }
/* 275 */           return false;
/*     */         } 
/*     */         
/* 278 */         throw x.syntaxError("Expected 'CDATA['");
/*     */       } 
/* 280 */       int i = 1;
/*     */       while (true)
/* 282 */       { token = x.nextMeta();
/* 283 */         if (token == null)
/* 284 */           throw x.syntaxError("Missing '>' after '<!'."); 
/* 285 */         if (token == LT) {
/* 286 */           i++;
/* 287 */         } else if (token == GT) {
/* 288 */           i--;
/*     */         } 
/* 290 */         if (i <= 0)
/* 291 */           return false;  } 
/* 292 */     }  if (token == QUEST) {
/*     */ 
/*     */       
/* 295 */       x.skipPast("?>");
/* 296 */       return false;
/* 297 */     }  if (token == SLASH) {
/*     */ 
/*     */ 
/*     */       
/* 301 */       token = x.nextToken();
/* 302 */       if (name == null) {
/* 303 */         throw x.syntaxError("Mismatched close tag " + token);
/*     */       }
/* 305 */       if (!token.equals(name)) {
/* 306 */         throw x.syntaxError("Mismatched " + name + " and " + token);
/*     */       }
/* 308 */       if (x.nextToken() != GT) {
/* 309 */         throw x.syntaxError("Misshaped close tag");
/*     */       }
/* 311 */       return true;
/*     */     } 
/* 313 */     if (token instanceof Character) {
/* 314 */       throw x.syntaxError("Misshaped tag");
/*     */     }
/*     */ 
/*     */ 
/*     */     
/* 319 */     String tagName = (String)token;
/* 320 */     token = null;
/* 321 */     jsonObject = new JSONObject();
/* 322 */     boolean nilAttributeFound = false;
/* 323 */     XMLXsiTypeConverter<?> xmlXsiTypeConverter = null;
/*     */     while (true) {
/* 325 */       if (token == null) {
/* 326 */         token = x.nextToken();
/*     */       }
/*     */       
/* 329 */       if (token instanceof String) {
/* 330 */         String string = (String)token;
/* 331 */         token = x.nextToken();
/* 332 */         if (token == EQ) {
/* 333 */           token = x.nextToken();
/* 334 */           if (!(token instanceof String)) {
/* 335 */             throw x.syntaxError("Missing value");
/*     */           }
/*     */           
/* 338 */           if (config.isConvertNilAttributeToNull() && "xsi:nil"
/* 339 */             .equals(string) && 
/* 340 */             Boolean.parseBoolean((String)token)) {
/* 341 */             nilAttributeFound = true;
/* 342 */           } else if (config.getXsiTypeMap() != null && !config.getXsiTypeMap().isEmpty() && "xsi:type"
/* 343 */             .equals(string)) {
/* 344 */             xmlXsiTypeConverter = config.getXsiTypeMap().get(token);
/* 345 */           } else if (!nilAttributeFound) {
/* 346 */             jsonObject.accumulate(string, 
/* 347 */                 config.isKeepStrings() ? token : 
/*     */                 
/* 349 */                 stringToValue((String)token));
/*     */           } 
/* 351 */           token = null; continue;
/*     */         } 
/* 353 */         jsonObject.accumulate(string, ""); continue;
/*     */       } 
/*     */       break;
/*     */     } 
/* 357 */     if (token == SLASH) {
/*     */       
/* 359 */       if (x.nextToken() != GT) {
/* 360 */         throw x.syntaxError("Misshaped tag");
/*     */       }
/* 362 */       if (config.getForceList().contains(tagName)) {
/*     */         
/* 364 */         if (nilAttributeFound) {
/* 365 */           context.append(tagName, JSONObject.NULL);
/* 366 */         } else if (jsonObject.length() > 0) {
/* 367 */           context.append(tagName, jsonObject);
/*     */         } else {
/* 369 */           context.put(tagName, new JSONArray());
/*     */         }
/*     */       
/* 372 */       } else if (nilAttributeFound) {
/* 373 */         context.accumulate(tagName, JSONObject.NULL);
/* 374 */       } else if (jsonObject.length() > 0) {
/* 375 */         context.accumulate(tagName, jsonObject);
/*     */       } else {
/* 377 */         context.accumulate(tagName, "");
/*     */       } 
/*     */       
/* 380 */       return false;
/*     */     } 
/* 382 */     if (token == GT) {
/*     */       while (true) {
/*     */         
/* 385 */         token = x.nextContent();
/* 386 */         if (token == null) {
/* 387 */           if (tagName != null) {
/* 388 */             throw x.syntaxError("Unclosed tag " + tagName);
/*     */           }
/* 390 */           return false;
/* 391 */         }  if (token instanceof String) {
/* 392 */           String string = (String)token;
/* 393 */           if (string.length() > 0) {
/* 394 */             if (xmlXsiTypeConverter != null) {
/* 395 */               jsonObject.accumulate(config.getcDataTagName(), 
/* 396 */                   stringToValue(string, xmlXsiTypeConverter)); continue;
/*     */             } 
/* 398 */             jsonObject.accumulate(config.getcDataTagName(), 
/* 399 */                 config.isKeepStrings() ? string : stringToValue(string));
/*     */           } 
/*     */           continue;
/*     */         } 
/* 403 */         if (token == LT)
/*     */         {
/* 405 */           if (parse(x, jsonObject, tagName, config)) {
/* 406 */             if (config.getForceList().contains(tagName)) {
/*     */               
/* 408 */               if (jsonObject.length() == 0) {
/* 409 */                 context.put(tagName, new JSONArray());
/* 410 */               } else if (jsonObject.length() == 1 && jsonObject
/* 411 */                 .opt(config.getcDataTagName()) != null) {
/* 412 */                 context.append(tagName, jsonObject.opt(config.getcDataTagName()));
/*     */               } else {
/* 414 */                 context.append(tagName, jsonObject);
/*     */               }
/*     */             
/* 417 */             } else if (jsonObject.length() == 0) {
/* 418 */               context.accumulate(tagName, "");
/* 419 */             } else if (jsonObject.length() == 1 && jsonObject
/* 420 */               .opt(config.getcDataTagName()) != null) {
/* 421 */               context.accumulate(tagName, jsonObject.opt(config.getcDataTagName()));
/*     */             } else {
/* 423 */               context.accumulate(tagName, jsonObject);
/*     */             } 
/*     */ 
/*     */             
/* 427 */             return false;
/*     */           } 
/*     */         }
/*     */       } 
/*     */     }
/* 432 */     throw x.syntaxError("Misshaped tag");
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
/*     */   public static Object stringToValue(String string, XMLXsiTypeConverter<?> typeConverter) {
/* 445 */     if (typeConverter != null) {
/* 446 */       return typeConverter.convert(string);
/*     */     }
/* 448 */     return stringToValue(string);
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
/*     */   public static Object stringToValue(String string) {
/* 461 */     if ("".equals(string)) {
/* 462 */       return string;
/*     */     }
/*     */ 
/*     */     
/* 466 */     if ("true".equalsIgnoreCase(string)) {
/* 467 */       return Boolean.TRUE;
/*     */     }
/* 469 */     if ("false".equalsIgnoreCase(string)) {
/* 470 */       return Boolean.FALSE;
/*     */     }
/* 472 */     if ("null".equalsIgnoreCase(string)) {
/* 473 */       return JSONObject.NULL;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 481 */     char initial = string.charAt(0);
/* 482 */     if ((initial >= '0' && initial <= '9') || initial == '-') {
/*     */       try {
/* 484 */         return stringToNumber(string);
/* 485 */       } catch (Exception exception) {}
/*     */     }
/*     */     
/* 488 */     return string;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static Number stringToNumber(String val) throws NumberFormatException {
/* 495 */     char initial = val.charAt(0);
/* 496 */     if ((initial >= '0' && initial <= '9') || initial == '-') {
/*     */       
/* 498 */       if (isDecimalNotation(val)) {
/*     */         
/*     */         try {
/*     */ 
/*     */           
/* 503 */           BigDecimal bd = new BigDecimal(val);
/* 504 */           if (initial == '-' && BigDecimal.ZERO.compareTo(bd) == 0) {
/* 505 */             return Double.valueOf(-0.0D);
/*     */           }
/* 507 */           return bd;
/* 508 */         } catch (NumberFormatException retryAsDouble) {
/*     */           
/*     */           try {
/* 511 */             Double d = Double.valueOf(val);
/* 512 */             if (d.isNaN() || d.isInfinite()) {
/* 513 */               throw new NumberFormatException("val [" + val + "] is not a valid number.");
/*     */             }
/* 515 */             return d;
/* 516 */           } catch (NumberFormatException ignore) {
/* 517 */             throw new NumberFormatException("val [" + val + "] is not a valid number.");
/*     */           } 
/*     */         } 
/*     */       }
/*     */       
/* 522 */       if (initial == '0' && val.length() > 1) {
/* 523 */         char at1 = val.charAt(1);
/* 524 */         if (at1 >= '0' && at1 <= '9') {
/* 525 */           throw new NumberFormatException("val [" + val + "] is not a valid number.");
/*     */         }
/* 527 */       } else if (initial == '-' && val.length() > 2) {
/* 528 */         char at1 = val.charAt(1);
/* 529 */         char at2 = val.charAt(2);
/* 530 */         if (at1 == '0' && at2 >= '0' && at2 <= '9') {
/* 531 */           throw new NumberFormatException("val [" + val + "] is not a valid number.");
/*     */         }
/*     */       } 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 542 */       BigInteger bi = new BigInteger(val);
/* 543 */       if (bi.bitLength() <= 31) {
/* 544 */         return Integer.valueOf(bi.intValue());
/*     */       }
/* 546 */       if (bi.bitLength() <= 63) {
/* 547 */         return Long.valueOf(bi.longValue());
/*     */       }
/* 549 */       return bi;
/*     */     } 
/* 551 */     throw new NumberFormatException("val [" + val + "] is not a valid number.");
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static boolean isDecimalNotation(String val) {
/* 558 */     return (val.indexOf('.') > -1 || val.indexOf('e') > -1 || val
/* 559 */       .indexOf('E') > -1 || "-0".equals(val));
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
/*     */ 
/*     */   
/*     */   public static JSONObject toJSONObject(String string) throws JSONException {
/* 581 */     return toJSONObject(string, XMLParserConfiguration.ORIGINAL);
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
/*     */   public static JSONObject toJSONObject(Reader reader) throws JSONException {
/* 601 */     return toJSONObject(reader, XMLParserConfiguration.ORIGINAL);
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static JSONObject toJSONObject(Reader reader, boolean keepStrings) throws JSONException {
/* 626 */     if (keepStrings) {
/* 627 */       return toJSONObject(reader, XMLParserConfiguration.KEEP_STRINGS);
/*     */     }
/* 629 */     return toJSONObject(reader, XMLParserConfiguration.ORIGINAL);
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
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static JSONObject toJSONObject(Reader reader, XMLParserConfiguration config) throws JSONException {
/* 653 */     JSONObject jo = new JSONObject();
/* 654 */     XMLTokener x = new XMLTokener(reader);
/* 655 */     while (x.more()) {
/* 656 */       x.skipPast("<");
/* 657 */       if (x.more()) {
/* 658 */         parse(x, jo, null, config);
/*     */       }
/*     */     } 
/* 661 */     return jo;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static JSONObject toJSONObject(String string, boolean keepStrings) throws JSONException {
/* 687 */     return toJSONObject(new StringReader(string), keepStrings);
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static JSONObject toJSONObject(String string, XMLParserConfiguration config) throws JSONException {
/* 712 */     return toJSONObject(new StringReader(string), config);
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
/*     */   public static String toString(Object object) throws JSONException {
/* 724 */     return toString(object, null, XMLParserConfiguration.ORIGINAL);
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
/*     */   public static String toString(Object object, String tagName) {
/* 738 */     return toString(object, tagName, XMLParserConfiguration.ORIGINAL);
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
/*     */   public static String toString(Object object, String tagName, XMLParserConfiguration config) throws JSONException {
/* 755 */     StringBuilder sb = new StringBuilder();
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 760 */     if (object instanceof JSONObject) {
/*     */ 
/*     */       
/* 763 */       if (tagName != null) {
/* 764 */         sb.append('<');
/* 765 */         sb.append(tagName);
/* 766 */         sb.append('>');
/*     */       } 
/*     */ 
/*     */ 
/*     */       
/* 771 */       JSONObject jo = (JSONObject)object;
/* 772 */       for (String key : jo.keySet()) {
/* 773 */         Object value = jo.opt(key);
/* 774 */         if (value == null) {
/* 775 */           value = "";
/* 776 */         } else if (value.getClass().isArray()) {
/* 777 */           value = new JSONArray(value);
/*     */         } 
/*     */ 
/*     */         
/* 781 */         if (key.equals(config.getcDataTagName())) {
/* 782 */           if (value instanceof JSONArray) {
/* 783 */             JSONArray ja = (JSONArray)value;
/* 784 */             int jaLength = ja.length();
/*     */             
/* 786 */             for (int i = 0; i < jaLength; i++) {
/* 787 */               if (i > 0) {
/* 788 */                 sb.append('\n');
/*     */               }
/* 790 */               Object val = ja.opt(i);
/* 791 */               sb.append(escape(val.toString()));
/*     */             }  continue;
/*     */           } 
/* 794 */           sb.append(escape(value.toString()));
/*     */           
/*     */           continue;
/*     */         } 
/*     */         
/* 799 */         if (value instanceof JSONArray) {
/* 800 */           JSONArray ja = (JSONArray)value;
/* 801 */           int jaLength = ja.length();
/*     */           
/* 803 */           for (int i = 0; i < jaLength; i++) {
/* 804 */             Object val = ja.opt(i);
/* 805 */             if (val instanceof JSONArray) {
/* 806 */               sb.append('<');
/* 807 */               sb.append(key);
/* 808 */               sb.append('>');
/* 809 */               sb.append(toString(val, null, config));
/* 810 */               sb.append("</");
/* 811 */               sb.append(key);
/* 812 */               sb.append('>');
/*     */             } else {
/* 814 */               sb.append(toString(val, key, config));
/*     */             } 
/*     */           }  continue;
/* 817 */         }  if ("".equals(value)) {
/* 818 */           sb.append('<');
/* 819 */           sb.append(key);
/* 820 */           sb.append("/>");
/*     */           
/*     */           continue;
/*     */         } 
/*     */         
/* 825 */         sb.append(toString(value, key, config));
/*     */       } 
/*     */       
/* 828 */       if (tagName != null) {
/*     */ 
/*     */         
/* 831 */         sb.append("</");
/* 832 */         sb.append(tagName);
/* 833 */         sb.append('>');
/*     */       } 
/* 835 */       return sb.toString();
/*     */     } 
/*     */ 
/*     */     
/* 839 */     if (object != null && (object instanceof JSONArray || object.getClass().isArray())) {
/* 840 */       JSONArray ja; if (object.getClass().isArray()) {
/* 841 */         ja = new JSONArray(object);
/*     */       } else {
/* 843 */         ja = (JSONArray)object;
/*     */       } 
/* 845 */       int jaLength = ja.length();
/*     */       
/* 847 */       for (int i = 0; i < jaLength; i++) {
/* 848 */         Object val = ja.opt(i);
/*     */ 
/*     */ 
/*     */         
/* 852 */         sb.append(toString(val, (tagName == null) ? "array" : tagName, config));
/*     */       } 
/* 854 */       return sb.toString();
/*     */     } 
/*     */     
/* 857 */     String string = (object == null) ? "null" : escape(object.toString());
/* 858 */     return (tagName == null) ? ("\"" + string + "\"") : (
/* 859 */       (string.length() == 0) ? ("<" + tagName + "/>") : ("<" + tagName + ">" + string + "</" + tagName + ">"));
/*     */   }
/*     */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\org\json\XML.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */