/*     */ package org.json;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class JSONML
/*     */ {
/*     */   private static Object parse(XMLTokener x, boolean arrayForm, JSONArray ja, boolean keepStrings) throws JSONException {
/*  34 */     String closeTag = null;
/*     */     
/*  36 */     JSONArray newja = null;
/*  37 */     JSONObject newjo = null;
/*     */     
/*  39 */     String tagName = null;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     label116: while (true) {
/*  48 */       if (!x.more()) {
/*  49 */         throw x.syntaxError("Bad XML");
/*     */       }
/*  51 */       Object token = x.nextContent();
/*  52 */       if (token == XML.LT) {
/*  53 */         token = x.nextToken();
/*  54 */         if (token instanceof Character) {
/*  55 */           if (token == XML.SLASH) {
/*     */ 
/*     */ 
/*     */             
/*  59 */             token = x.nextToken();
/*  60 */             if (!(token instanceof String)) {
/*  61 */               throw new JSONException("Expected a closing name instead of '" + token + "'.");
/*     */             }
/*     */ 
/*     */             
/*  65 */             if (x.nextToken() != XML.GT) {
/*  66 */               throw x.syntaxError("Misshaped close tag");
/*     */             }
/*  68 */             return token;
/*  69 */           }  if (token == XML.BANG) {
/*     */ 
/*     */ 
/*     */             
/*  73 */             char c = x.next();
/*  74 */             if (c == '-') {
/*  75 */               if (x.next() == '-') {
/*  76 */                 x.skipPast("-->"); continue;
/*     */               } 
/*  78 */               x.back(); continue;
/*     */             } 
/*  80 */             if (c == '[') {
/*  81 */               token = x.nextToken();
/*  82 */               if (token.equals("CDATA") && x.next() == '[') {
/*  83 */                 if (ja != null)
/*  84 */                   ja.put(x.nextCDATA()); 
/*     */                 continue;
/*     */               } 
/*  87 */               throw x.syntaxError("Expected 'CDATA['");
/*     */             } 
/*     */             
/*  90 */             int i = 1;
/*     */             while (true)
/*  92 */             { token = x.nextMeta();
/*  93 */               if (token == null)
/*  94 */                 throw x.syntaxError("Missing '>' after '<!'."); 
/*  95 */               if (token == XML.LT) {
/*  96 */                 i++;
/*  97 */               } else if (token == XML.GT) {
/*  98 */                 i--;
/*     */               } 
/* 100 */               if (i <= 0)
/*     */                 continue label116;  }  break;
/* 102 */           }  if (token == XML.QUEST) {
/*     */ 
/*     */ 
/*     */             
/* 106 */             x.skipPast("?>"); continue;
/*     */           } 
/* 108 */           throw x.syntaxError("Misshaped tag");
/*     */         } 
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 114 */         if (!(token instanceof String)) {
/* 115 */           throw x.syntaxError("Bad tagName '" + token + "'.");
/*     */         }
/* 117 */         tagName = (String)token;
/* 118 */         newja = new JSONArray();
/* 119 */         newjo = new JSONObject();
/* 120 */         if (arrayForm) {
/* 121 */           newja.put(tagName);
/* 122 */           if (ja != null) {
/* 123 */             ja.put(newja);
/*     */           }
/*     */         } else {
/* 126 */           newjo.put("tagName", tagName);
/* 127 */           if (ja != null) {
/* 128 */             ja.put(newjo);
/*     */           }
/*     */         } 
/* 131 */         token = null;
/*     */         while (true) {
/* 133 */           if (token == null) {
/* 134 */             token = x.nextToken();
/*     */           }
/* 136 */           if (token == null) {
/* 137 */             throw x.syntaxError("Misshaped tag");
/*     */           }
/* 139 */           if (!(token instanceof String)) {
/*     */             break;
/*     */           }
/*     */ 
/*     */ 
/*     */           
/* 145 */           String attribute = (String)token;
/* 146 */           if (!arrayForm && ("tagName".equals(attribute) || "childNode".equals(attribute))) {
/* 147 */             throw x.syntaxError("Reserved attribute.");
/*     */           }
/* 149 */           token = x.nextToken();
/* 150 */           if (token == XML.EQ) {
/* 151 */             token = x.nextToken();
/* 152 */             if (!(token instanceof String)) {
/* 153 */               throw x.syntaxError("Missing value");
/*     */             }
/* 155 */             newjo.accumulate(attribute, keepStrings ? token : XML.stringToValue((String)token));
/* 156 */             token = null; continue;
/*     */           } 
/* 158 */           newjo.accumulate(attribute, "");
/*     */         } 
/*     */         
/* 161 */         if (arrayForm && newjo.length() > 0) {
/* 162 */           newja.put(newjo);
/*     */         }
/*     */ 
/*     */ 
/*     */         
/* 167 */         if (token == XML.SLASH) {
/* 168 */           if (x.nextToken() != XML.GT) {
/* 169 */             throw x.syntaxError("Misshaped tag");
/*     */           }
/* 171 */           if (ja == null) {
/* 172 */             if (arrayForm) {
/* 173 */               return newja;
/*     */             }
/* 175 */             return newjo;
/*     */           } 
/*     */           
/*     */           continue;
/*     */         } 
/*     */         
/* 181 */         if (token != XML.GT) {
/* 182 */           throw x.syntaxError("Misshaped tag");
/*     */         }
/* 184 */         closeTag = (String)parse(x, arrayForm, newja, keepStrings);
/* 185 */         if (closeTag != null) {
/* 186 */           if (!closeTag.equals(tagName)) {
/* 187 */             throw x.syntaxError("Mismatched '" + tagName + "' and '" + closeTag + "'");
/*     */           }
/*     */           
/* 190 */           tagName = null;
/* 191 */           if (!arrayForm && newja.length() > 0) {
/* 192 */             newjo.put("childNodes", newja);
/*     */           }
/* 194 */           if (ja == null) {
/* 195 */             if (arrayForm) {
/* 196 */               return newja;
/*     */             }
/* 198 */             return newjo;
/*     */           } 
/*     */         } 
/*     */         
/*     */         continue;
/*     */       } 
/* 204 */       if (ja != null) {
/* 205 */         ja.put((token instanceof String) ? (keepStrings ? 
/* 206 */             XML.unescape((String)token) : XML.stringToValue((String)token)) : token);
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
/*     */   public static JSONArray toJSONArray(String string) throws JSONException {
/* 227 */     return (JSONArray)parse(new XMLTokener(string), true, null, false);
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
/*     */   public static JSONArray toJSONArray(String string, boolean keepStrings) throws JSONException {
/* 249 */     return (JSONArray)parse(new XMLTokener(string), true, null, keepStrings);
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
/*     */   public static JSONArray toJSONArray(XMLTokener x, boolean keepStrings) throws JSONException {
/* 271 */     return (JSONArray)parse(x, true, null, keepStrings);
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
/*     */   public static JSONArray toJSONArray(XMLTokener x) throws JSONException {
/* 288 */     return (JSONArray)parse(x, true, null, false);
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
/*     */   public static JSONObject toJSONObject(String string) throws JSONException {
/* 306 */     return (JSONObject)parse(new XMLTokener(string), false, null, false);
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
/*     */   public static JSONObject toJSONObject(String string, boolean keepStrings) throws JSONException {
/* 326 */     return (JSONObject)parse(new XMLTokener(string), false, null, keepStrings);
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
/*     */   public static JSONObject toJSONObject(XMLTokener x) throws JSONException {
/* 344 */     return (JSONObject)parse(x, false, null, false);
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
/*     */   public static JSONObject toJSONObject(XMLTokener x, boolean keepStrings) throws JSONException {
/* 364 */     return (JSONObject)parse(x, false, null, keepStrings);
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
/*     */   public static String toString(JSONArray ja) throws JSONException {
/*     */     int i;
/* 379 */     StringBuilder sb = new StringBuilder();
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 384 */     String tagName = ja.getString(0);
/* 385 */     XML.noSpace(tagName);
/* 386 */     tagName = XML.escape(tagName);
/* 387 */     sb.append('<');
/* 388 */     sb.append(tagName);
/*     */     
/* 390 */     Object object = ja.opt(1);
/* 391 */     if (object instanceof JSONObject) {
/* 392 */       i = 2;
/* 393 */       JSONObject jo = (JSONObject)object;
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 398 */       for (String key : jo.keySet()) {
/* 399 */         Object value = jo.opt(key);
/* 400 */         XML.noSpace(key);
/* 401 */         if (value != null) {
/* 402 */           sb.append(' ');
/* 403 */           sb.append(XML.escape(key));
/* 404 */           sb.append('=');
/* 405 */           sb.append('"');
/* 406 */           sb.append(XML.escape(value.toString()));
/* 407 */           sb.append('"');
/*     */         } 
/*     */       } 
/*     */     } else {
/* 411 */       i = 1;
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 416 */     int length = ja.length();
/* 417 */     if (i >= length)
/* 418 */     { sb.append('/');
/* 419 */       sb.append('>'); }
/*     */     else
/* 421 */     { sb.append('>');
/*     */       while (true)
/* 423 */       { object = ja.get(i);
/* 424 */         i++;
/* 425 */         if (object != null) {
/* 426 */           if (object instanceof String) {
/* 427 */             sb.append(XML.escape(object.toString()));
/* 428 */           } else if (object instanceof JSONObject) {
/* 429 */             sb.append(toString((JSONObject)object));
/* 430 */           } else if (object instanceof JSONArray) {
/* 431 */             sb.append(toString((JSONArray)object));
/*     */           } else {
/* 433 */             sb.append(object.toString());
/*     */           } 
/*     */         }
/* 436 */         if (i >= length)
/* 437 */         { sb.append('<');
/* 438 */           sb.append('/');
/* 439 */           sb.append(tagName);
/* 440 */           sb.append('>');
/*     */           
/* 442 */           return sb.toString(); }  }  }  return sb.toString();
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
/*     */   public static String toString(JSONObject jo) throws JSONException {
/* 455 */     StringBuilder sb = new StringBuilder();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 465 */     String tagName = jo.optString("tagName");
/* 466 */     if (tagName == null) {
/* 467 */       return XML.escape(jo.toString());
/*     */     }
/* 469 */     XML.noSpace(tagName);
/* 470 */     tagName = XML.escape(tagName);
/* 471 */     sb.append('<');
/* 472 */     sb.append(tagName);
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 477 */     for (String key : jo.keySet()) {
/* 478 */       if (!"tagName".equals(key) && !"childNodes".equals(key)) {
/* 479 */         XML.noSpace(key);
/* 480 */         Object value = jo.opt(key);
/* 481 */         if (value != null) {
/* 482 */           sb.append(' ');
/* 483 */           sb.append(XML.escape(key));
/* 484 */           sb.append('=');
/* 485 */           sb.append('"');
/* 486 */           sb.append(XML.escape(value.toString()));
/* 487 */           sb.append('"');
/*     */         } 
/*     */       } 
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 494 */     JSONArray ja = jo.optJSONArray("childNodes");
/* 495 */     if (ja == null) {
/* 496 */       sb.append('/');
/* 497 */       sb.append('>');
/*     */     } else {
/* 499 */       sb.append('>');
/* 500 */       int length = ja.length();
/* 501 */       for (int i = 0; i < length; i++) {
/* 502 */         Object object = ja.get(i);
/* 503 */         if (object != null) {
/* 504 */           if (object instanceof String) {
/* 505 */             sb.append(XML.escape(object.toString()));
/* 506 */           } else if (object instanceof JSONObject) {
/* 507 */             sb.append(toString((JSONObject)object));
/* 508 */           } else if (object instanceof JSONArray) {
/* 509 */             sb.append(toString((JSONArray)object));
/*     */           } else {
/* 511 */             sb.append(object.toString());
/*     */           } 
/*     */         }
/*     */       } 
/* 515 */       sb.append('<');
/* 516 */       sb.append('/');
/* 517 */       sb.append(tagName);
/* 518 */       sb.append('>');
/*     */     } 
/* 520 */     return sb.toString();
/*     */   }
/*     */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\org\json\JSONML.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */