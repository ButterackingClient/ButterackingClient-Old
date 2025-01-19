/*     */ package org.json;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import java.util.Collection;
/*     */ import java.util.Map;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class JSONWriter
/*     */ {
/*     */   private static final int maxdepth = 200;
/*     */   private boolean comma;
/*     */   protected char mode;
/*     */   private final JSONObject[] stack;
/*     */   private int top;
/*     */   protected Appendable writer;
/*     */   
/*     */   public JSONWriter(Appendable w) {
/*  79 */     this.comma = false;
/*  80 */     this.mode = 'i';
/*  81 */     this.stack = new JSONObject[200];
/*  82 */     this.top = 0;
/*  83 */     this.writer = w;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private JSONWriter append(String string) throws JSONException {
/*  93 */     if (string == null) {
/*  94 */       throw new JSONException("Null pointer");
/*     */     }
/*  96 */     if (this.mode == 'o' || this.mode == 'a') {
/*     */       try {
/*  98 */         if (this.comma && this.mode == 'a') {
/*  99 */           this.writer.append(',');
/*     */         }
/* 101 */         this.writer.append(string);
/* 102 */       } catch (IOException e) {
/*     */ 
/*     */ 
/*     */         
/* 106 */         throw new JSONException(e);
/*     */       } 
/* 108 */       if (this.mode == 'o') {
/* 109 */         this.mode = 'k';
/*     */       }
/* 111 */       this.comma = true;
/* 112 */       return this;
/*     */     } 
/* 114 */     throw new JSONException("Value out of sequence.");
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
/*     */   public JSONWriter array() throws JSONException {
/* 127 */     if (this.mode == 'i' || this.mode == 'o' || this.mode == 'a') {
/* 128 */       push(null);
/* 129 */       append("[");
/* 130 */       this.comma = false;
/* 131 */       return this;
/*     */     } 
/* 133 */     throw new JSONException("Misplaced array.");
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private JSONWriter end(char m, char c) throws JSONException {
/* 144 */     if (this.mode != m) {
/* 145 */       throw new JSONException((m == 'a') ? "Misplaced endArray." : "Misplaced endObject.");
/*     */     }
/*     */ 
/*     */     
/* 149 */     pop(m);
/*     */     try {
/* 151 */       this.writer.append(c);
/* 152 */     } catch (IOException e) {
/*     */ 
/*     */ 
/*     */       
/* 156 */       throw new JSONException(e);
/*     */     } 
/* 158 */     this.comma = true;
/* 159 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public JSONWriter endArray() throws JSONException {
/* 169 */     return end('a', ']');
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public JSONWriter endObject() throws JSONException {
/* 179 */     return end('k', '}');
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
/*     */   public JSONWriter key(String string) throws JSONException {
/* 191 */     if (string == null) {
/* 192 */       throw new JSONException("Null key.");
/*     */     }
/* 194 */     if (this.mode == 'k') {
/*     */       try {
/* 196 */         JSONObject topObject = this.stack[this.top - 1];
/*     */         
/* 198 */         if (topObject.has(string)) {
/* 199 */           throw new JSONException("Duplicate key \"" + string + "\"");
/*     */         }
/* 201 */         topObject.put(string, true);
/* 202 */         if (this.comma) {
/* 203 */           this.writer.append(',');
/*     */         }
/* 205 */         this.writer.append(JSONObject.quote(string));
/* 206 */         this.writer.append(':');
/* 207 */         this.comma = false;
/* 208 */         this.mode = 'o';
/* 209 */         return this;
/* 210 */       } catch (IOException e) {
/*     */ 
/*     */ 
/*     */         
/* 214 */         throw new JSONException(e);
/*     */       } 
/*     */     }
/* 217 */     throw new JSONException("Misplaced key.");
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
/*     */   public JSONWriter object() throws JSONException {
/* 231 */     if (this.mode == 'i') {
/* 232 */       this.mode = 'o';
/*     */     }
/* 234 */     if (this.mode == 'o' || this.mode == 'a') {
/* 235 */       append("{");
/* 236 */       push(new JSONObject());
/* 237 */       this.comma = false;
/* 238 */       return this;
/*     */     } 
/* 240 */     throw new JSONException("Misplaced object.");
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void pop(char c) throws JSONException {
/* 251 */     if (this.top <= 0) {
/* 252 */       throw new JSONException("Nesting error.");
/*     */     }
/* 254 */     char m = (this.stack[this.top - 1] == null) ? 'a' : 'k';
/* 255 */     if (m != c) {
/* 256 */       throw new JSONException("Nesting error.");
/*     */     }
/* 258 */     this.top--;
/* 259 */     this.mode = (this.top == 0) ? 'd' : ((this.stack[this.top - 1] == null) ? 'a' : 'k');
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
/*     */   private void push(JSONObject jo) throws JSONException {
/* 272 */     if (this.top >= 200) {
/* 273 */       throw new JSONException("Nesting too deep.");
/*     */     }
/* 275 */     this.stack[this.top] = jo;
/* 276 */     this.mode = (jo == null) ? 'a' : 'k';
/* 277 */     this.top++;
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
/*     */ 
/*     */   
/*     */   public static String valueToString(Object value) throws JSONException {
/* 305 */     if (value == null || value.equals(null)) {
/* 306 */       return "null";
/*     */     }
/* 308 */     if (value instanceof JSONString) {
/*     */       String object;
/*     */       try {
/* 311 */         object = ((JSONString)value).toJSONString();
/* 312 */       } catch (Exception e) {
/* 313 */         throw new JSONException(e);
/*     */       } 
/* 315 */       if (object != null) {
/* 316 */         return object;
/*     */       }
/* 318 */       throw new JSONException("Bad value from toJSONString: " + object);
/*     */     } 
/* 320 */     if (value instanceof Number) {
/*     */       
/* 322 */       String numberAsString = JSONObject.numberToString((Number)value);
/* 323 */       if (JSONObject.NUMBER_PATTERN.matcher(numberAsString).matches())
/*     */       {
/* 325 */         return numberAsString;
/*     */       }
/*     */ 
/*     */       
/* 329 */       return JSONObject.quote(numberAsString);
/*     */     } 
/* 331 */     if (value instanceof Boolean || value instanceof JSONObject || value instanceof JSONArray)
/*     */     {
/* 333 */       return value.toString();
/*     */     }
/* 335 */     if (value instanceof Map) {
/* 336 */       Map<?, ?> map = (Map<?, ?>)value;
/* 337 */       return (new JSONObject(map)).toString();
/*     */     } 
/* 339 */     if (value instanceof Collection) {
/* 340 */       Collection<?> coll = (Collection)value;
/* 341 */       return (new JSONArray(coll)).toString();
/*     */     } 
/* 343 */     if (value.getClass().isArray()) {
/* 344 */       return (new JSONArray(value)).toString();
/*     */     }
/* 346 */     if (value instanceof Enum) {
/* 347 */       return JSONObject.quote(((Enum)value).name());
/*     */     }
/* 349 */     return JSONObject.quote(value.toString());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public JSONWriter value(boolean b) throws JSONException {
/* 360 */     return append(b ? "true" : "false");
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public JSONWriter value(double d) throws JSONException {
/* 370 */     return value(Double.valueOf(d));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public JSONWriter value(long l) throws JSONException {
/* 380 */     return append(Long.toString(l));
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
/*     */   public JSONWriter value(Object object) throws JSONException {
/* 392 */     return append(valueToString(object));
/*     */   }
/*     */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\org\json\JSONWriter.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */