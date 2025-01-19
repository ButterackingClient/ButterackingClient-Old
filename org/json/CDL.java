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
/*     */ 
/*     */ 
/*     */ 
/*     */ public class CDL
/*     */ {
/*     */   private static String getValue(JSONTokener x) throws JSONException {
/*     */     char c, q;
/*     */     StringBuilder sb;
/*     */     do {
/*  40 */       c = x.next();
/*  41 */     } while (c == ' ' || c == '\t');
/*  42 */     switch (c) {
/*     */       case '\000':
/*  44 */         return null;
/*     */       case '"':
/*     */       case '\'':
/*  47 */         q = c;
/*  48 */         sb = new StringBuilder();
/*     */         while (true) {
/*  50 */           c = x.next();
/*  51 */           if (c == q) {
/*     */             
/*  53 */             char nextC = x.next();
/*  54 */             if (nextC != '"') {
/*     */               
/*  56 */               if (nextC > '\000') {
/*  57 */                 x.back();
/*     */               }
/*     */               break;
/*     */             } 
/*     */           } 
/*  62 */           if (c == '\000' || c == '\n' || c == '\r') {
/*  63 */             throw x.syntaxError("Missing close quote '" + q + "'.");
/*     */           }
/*  65 */           sb.append(c);
/*     */         } 
/*  67 */         return sb.toString();
/*     */       case ',':
/*  69 */         x.back();
/*  70 */         return "";
/*     */     } 
/*  72 */     x.back();
/*  73 */     return x.nextTo(',');
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static JSONArray rowToJSONArray(JSONTokener x) throws JSONException {
/*  84 */     JSONArray ja = new JSONArray();
/*     */     while (true) {
/*  86 */       String value = getValue(x);
/*  87 */       char c = x.next();
/*  88 */       if (value == null || (ja
/*  89 */         .length() == 0 && value.length() == 0 && c != ',')) {
/*  90 */         return null;
/*     */       }
/*  92 */       ja.put(value);
/*     */       
/*  94 */       while (c != ',') {
/*     */ 
/*     */         
/*  97 */         if (c != ' ') {
/*  98 */           if (c == '\n' || c == '\r' || c == '\000') {
/*  99 */             return ja;
/*     */           }
/* 101 */           throw x.syntaxError("Bad character '" + c + "' (" + c + ").");
/*     */         } 
/*     */         
/* 104 */         c = x.next();
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
/*     */   public static JSONObject rowToJSONObject(JSONArray names, JSONTokener x) throws JSONException {
/* 121 */     JSONArray ja = rowToJSONArray(x);
/* 122 */     return (ja != null) ? ja.toJSONObject(names) : null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static String rowToString(JSONArray ja) {
/* 133 */     StringBuilder sb = new StringBuilder();
/* 134 */     for (int i = 0; i < ja.length(); i++) {
/* 135 */       if (i > 0) {
/* 136 */         sb.append(',');
/*     */       }
/* 138 */       Object object = ja.opt(i);
/* 139 */       if (object != null) {
/* 140 */         String string = object.toString();
/* 141 */         if (string.length() > 0 && (string.indexOf(',') >= 0 || string
/* 142 */           .indexOf('\n') >= 0 || string.indexOf('\r') >= 0 || string
/* 143 */           .indexOf(false) >= 0 || string.charAt(0) == '"')) {
/* 144 */           sb.append('"');
/* 145 */           int length = string.length();
/* 146 */           for (int j = 0; j < length; j++) {
/* 147 */             char c = string.charAt(j);
/* 148 */             if (c >= ' ' && c != '"') {
/* 149 */               sb.append(c);
/*     */             }
/*     */           } 
/* 152 */           sb.append('"');
/*     */         } else {
/* 154 */           sb.append(string);
/*     */         } 
/*     */       } 
/*     */     } 
/* 158 */     sb.append('\n');
/* 159 */     return sb.toString();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static JSONArray toJSONArray(String string) throws JSONException {
/* 170 */     return toJSONArray(new JSONTokener(string));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static JSONArray toJSONArray(JSONTokener x) throws JSONException {
/* 181 */     return toJSONArray(rowToJSONArray(x), x);
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
/*     */   public static JSONArray toJSONArray(JSONArray names, String string) throws JSONException {
/* 194 */     return toJSONArray(names, new JSONTokener(string));
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
/*     */   public static JSONArray toJSONArray(JSONArray names, JSONTokener x) throws JSONException {
/* 207 */     if (names == null || names.length() == 0) {
/* 208 */       return null;
/*     */     }
/* 210 */     JSONArray ja = new JSONArray();
/*     */     while (true) {
/* 212 */       JSONObject jo = rowToJSONObject(names, x);
/* 213 */       if (jo == null) {
/*     */         break;
/*     */       }
/* 216 */       ja.put(jo);
/*     */     } 
/* 218 */     if (ja.length() == 0) {
/* 219 */       return null;
/*     */     }
/* 221 */     return ja;
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
/*     */   public static String toString(JSONArray ja) throws JSONException {
/* 234 */     JSONObject jo = ja.optJSONObject(0);
/* 235 */     if (jo != null) {
/* 236 */       JSONArray names = jo.names();
/* 237 */       if (names != null) {
/* 238 */         return rowToString(names) + toString(names, ja);
/*     */       }
/*     */     } 
/* 241 */     return null;
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
/*     */   public static String toString(JSONArray names, JSONArray ja) throws JSONException {
/* 255 */     if (names == null || names.length() == 0) {
/* 256 */       return null;
/*     */     }
/* 258 */     StringBuilder sb = new StringBuilder();
/* 259 */     for (int i = 0; i < ja.length(); i++) {
/* 260 */       JSONObject jo = ja.optJSONObject(i);
/* 261 */       if (jo != null) {
/* 262 */         sb.append(rowToString(jo.toJSONArray(names)));
/*     */       }
/*     */     } 
/* 265 */     return sb.toString();
/*     */   }
/*     */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\org\json\CDL.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */