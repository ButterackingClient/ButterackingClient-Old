/*     */ package org.json;
/*     */ 
/*     */ import java.io.UnsupportedEncodingException;
/*     */ import java.net.URLDecoder;
/*     */ import java.net.URLEncoder;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Collections;
/*     */ import java.util.List;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class JSONPointer
/*     */ {
/*     */   private static final String ENCODING = "utf-8";
/*     */   private final List<String> refTokens;
/*     */   
/*     */   public static class Builder
/*     */   {
/*  46 */     private final List<String> refTokens = new ArrayList<String>();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public JSONPointer build() {
/*  54 */       return new JSONPointer(this.refTokens);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public Builder append(String token) {
/*  70 */       if (token == null) {
/*  71 */         throw new NullPointerException("token cannot be null");
/*     */       }
/*  73 */       this.refTokens.add(token);
/*  74 */       return this;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public Builder append(int arrayIndex) {
/*  85 */       this.refTokens.add(String.valueOf(arrayIndex));
/*  86 */       return this;
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
/*     */   public static Builder builder() {
/* 106 */     return new Builder();
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
/*     */   public JSONPointer(String pointer) {
/*     */     String refs;
/* 121 */     if (pointer == null) {
/* 122 */       throw new NullPointerException("pointer cannot be null");
/*     */     }
/* 124 */     if (pointer.isEmpty() || pointer.equals("#")) {
/* 125 */       this.refTokens = Collections.emptyList();
/*     */       
/*     */       return;
/*     */     } 
/* 129 */     if (pointer.startsWith("#/")) {
/* 130 */       refs = pointer.substring(2);
/*     */       try {
/* 132 */         refs = URLDecoder.decode(refs, "utf-8");
/* 133 */       } catch (UnsupportedEncodingException e) {
/* 134 */         throw new RuntimeException(e);
/*     */       } 
/* 136 */     } else if (pointer.startsWith("/")) {
/* 137 */       refs = pointer.substring(1);
/*     */     } else {
/* 139 */       throw new IllegalArgumentException("a JSON pointer should start with '/' or '#/'");
/*     */     } 
/* 141 */     this.refTokens = new ArrayList<String>();
/* 142 */     int slashIdx = -1;
/* 143 */     int prevSlashIdx = 0;
/*     */     do {
/* 145 */       prevSlashIdx = slashIdx + 1;
/* 146 */       slashIdx = refs.indexOf('/', prevSlashIdx);
/* 147 */       if (prevSlashIdx == slashIdx || prevSlashIdx == refs.length()) {
/*     */ 
/*     */         
/* 150 */         this.refTokens.add("");
/* 151 */       } else if (slashIdx >= 0) {
/* 152 */         String token = refs.substring(prevSlashIdx, slashIdx);
/* 153 */         this.refTokens.add(unescape(token));
/*     */       } else {
/*     */         
/* 156 */         String token = refs.substring(prevSlashIdx);
/* 157 */         this.refTokens.add(unescape(token));
/*     */       } 
/* 159 */     } while (slashIdx >= 0);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public JSONPointer(List<String> refTokens) {
/* 167 */     this.refTokens = new ArrayList<String>(refTokens);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static String unescape(String token) {
/* 174 */     return token.replace("~1", "/").replace("~0", "~");
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
/*     */   public Object queryFrom(Object document) throws JSONPointerException {
/* 188 */     if (this.refTokens.isEmpty()) {
/* 189 */       return document;
/*     */     }
/* 191 */     Object current = document;
/* 192 */     for (String token : this.refTokens) {
/* 193 */       if (current instanceof JSONObject) {
/* 194 */         current = ((JSONObject)current).opt(unescape(token)); continue;
/* 195 */       }  if (current instanceof JSONArray) {
/* 196 */         current = readByIndexToken(current, token); continue;
/*     */       } 
/* 198 */       throw new JSONPointerException(String.format("value [%s] is not an array or object therefore its key %s cannot be resolved", new Object[] { current, token }));
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 203 */     return current;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static Object readByIndexToken(Object current, String indexToken) throws JSONPointerException {
/*     */     try {
/* 215 */       int index = Integer.parseInt(indexToken);
/* 216 */       JSONArray currentArr = (JSONArray)current;
/* 217 */       if (index >= currentArr.length()) {
/* 218 */         throw new JSONPointerException(String.format("index %s is out of bounds - the array has %d elements", new Object[] { indexToken, 
/* 219 */                 Integer.valueOf(currentArr.length()) }));
/*     */       }
/*     */       try {
/* 222 */         return currentArr.get(index);
/* 223 */       } catch (JSONException e) {
/* 224 */         throw new JSONPointerException("Error reading value at index position " + index, e);
/*     */       } 
/* 226 */     } catch (NumberFormatException e) {
/* 227 */       throw new JSONPointerException(String.format("%s is not an array index", new Object[] { indexToken }), e);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String toString() {
/* 237 */     StringBuilder rval = new StringBuilder("");
/* 238 */     for (String token : this.refTokens) {
/* 239 */       rval.append('/').append(escape(token));
/*     */     }
/* 241 */     return rval.toString();
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
/*     */   private static String escape(String token) {
/* 254 */     return token.replace("~", "~0")
/* 255 */       .replace("/", "~1");
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String toURIFragment() {
/*     */     try {
/* 265 */       StringBuilder rval = new StringBuilder("#");
/* 266 */       for (String token : this.refTokens) {
/* 267 */         rval.append('/').append(URLEncoder.encode(token, "utf-8"));
/*     */       }
/* 269 */       return rval.toString();
/* 270 */     } catch (UnsupportedEncodingException e) {
/* 271 */       throw new RuntimeException(e);
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\org\json\JSONPointer.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */