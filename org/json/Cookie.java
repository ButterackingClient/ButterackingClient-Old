/*     */ package org.json;
/*     */ 
/*     */ import java.util.Locale;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class Cookie
/*     */ {
/*     */   public static String escape(String string) {
/*  32 */     String s = string.trim();
/*  33 */     int length = s.length();
/*  34 */     StringBuilder sb = new StringBuilder(length);
/*  35 */     for (int i = 0; i < length; i++) {
/*  36 */       char c = s.charAt(i);
/*  37 */       if (c < ' ' || c == '+' || c == '%' || c == '=' || c == ';') {
/*  38 */         sb.append('%');
/*  39 */         sb.append(Character.forDigit((char)(c >>> 4 & 0xF), 16));
/*  40 */         sb.append(Character.forDigit((char)(c & 0xF), 16));
/*     */       } else {
/*  42 */         sb.append(c);
/*     */       } 
/*     */     } 
/*  45 */     return sb.toString();
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
/*     */   public static JSONObject toJSONObject(String string) {
/*  70 */     JSONObject jo = new JSONObject();
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  75 */     JSONTokener x = new JSONTokener(string);
/*     */     
/*  77 */     String name = unescape(x.nextTo('=').trim());
/*     */     
/*  79 */     if ("".equals(name)) {
/*  80 */       throw new JSONException("Cookies must have a 'name'");
/*     */     }
/*  82 */     jo.put("name", name);
/*     */ 
/*     */     
/*  85 */     x.next('=');
/*  86 */     jo.put("value", unescape(x.nextTo(';')).trim());
/*     */     
/*  88 */     x.next();
/*     */     
/*  90 */     while (x.more()) {
/*  91 */       Object value; name = unescape(x.nextTo("=;")).trim().toLowerCase(Locale.ROOT);
/*     */       
/*  93 */       if ("name".equalsIgnoreCase(name)) {
/*  94 */         throw new JSONException("Illegal attribute name: 'name'");
/*     */       }
/*  96 */       if ("value".equalsIgnoreCase(name)) {
/*  97 */         throw new JSONException("Illegal attribute name: 'value'");
/*     */       }
/*     */       
/* 100 */       if (x.next() != '=') {
/* 101 */         value = Boolean.TRUE;
/*     */       } else {
/* 103 */         value = unescape(x.nextTo(';')).trim();
/* 104 */         x.next();
/*     */       } 
/*     */       
/* 107 */       if (!"".equals(name) && !"".equals(value)) {
/* 108 */         jo.put(name, value);
/*     */       }
/*     */     } 
/* 111 */     return jo;
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
/*     */   public static String toString(JSONObject jo) throws JSONException {
/* 127 */     StringBuilder sb = new StringBuilder();
/*     */     
/* 129 */     String name = null;
/* 130 */     Object value = null;
/* 131 */     for (String key : jo.keySet()) {
/* 132 */       if ("name".equalsIgnoreCase(key)) {
/* 133 */         name = jo.getString(key).trim();
/*     */       }
/* 135 */       if ("value".equalsIgnoreCase(key)) {
/* 136 */         value = jo.getString(key).trim();
/*     */       }
/* 138 */       if (name != null && value != null) {
/*     */         break;
/*     */       }
/*     */     } 
/*     */     
/* 143 */     if (name == null || "".equals(name.trim())) {
/* 144 */       throw new JSONException("Cookie does not have a name");
/*     */     }
/* 146 */     if (value == null) {
/* 147 */       value = "";
/*     */     }
/*     */     
/* 150 */     sb.append(escape(name));
/* 151 */     sb.append("=");
/* 152 */     sb.append(escape((String)value));
/*     */     
/* 154 */     for (String key : jo.keySet()) {
/* 155 */       if ("name".equalsIgnoreCase(key) || "value"
/* 156 */         .equalsIgnoreCase(key)) {
/*     */         continue;
/*     */       }
/*     */       
/* 160 */       value = jo.opt(key);
/* 161 */       if (value instanceof Boolean) {
/* 162 */         if (Boolean.TRUE.equals(value)) {
/* 163 */           sb.append(';').append(escape(key));
/*     */         }
/*     */         continue;
/*     */       } 
/* 167 */       sb.append(';')
/* 168 */         .append(escape(key))
/* 169 */         .append('=')
/* 170 */         .append(escape(value.toString()));
/*     */     } 
/*     */ 
/*     */     
/* 174 */     return sb.toString();
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
/*     */   public static String unescape(String string) {
/* 186 */     int length = string.length();
/* 187 */     StringBuilder sb = new StringBuilder(length);
/* 188 */     for (int i = 0; i < length; i++) {
/* 189 */       char c = string.charAt(i);
/* 190 */       if (c == '+') {
/* 191 */         c = ' ';
/* 192 */       } else if (c == '%' && i + 2 < length) {
/* 193 */         int d = JSONTokener.dehexchar(string.charAt(i + 1));
/* 194 */         int e = JSONTokener.dehexchar(string.charAt(i + 2));
/* 195 */         if (d >= 0 && e >= 0) {
/* 196 */           c = (char)(d * 16 + e);
/* 197 */           i += 2;
/*     */         } 
/*     */       } 
/* 200 */       sb.append(c);
/*     */     } 
/* 202 */     return sb.toString();
/*     */   }
/*     */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\org\json\Cookie.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */