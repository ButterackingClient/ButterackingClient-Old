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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class HTTP
/*     */ {
/*     */   public static final String CRLF = "\r\n";
/*     */   
/*     */   public static JSONObject toJSONObject(String string) throws JSONException {
/*  52 */     JSONObject jo = new JSONObject();
/*  53 */     HTTPTokener x = new HTTPTokener(string);
/*     */ 
/*     */     
/*  56 */     String token = x.nextToken();
/*  57 */     if (token.toUpperCase(Locale.ROOT).startsWith("HTTP")) {
/*     */ 
/*     */ 
/*     */       
/*  61 */       jo.put("HTTP-Version", token);
/*  62 */       jo.put("Status-Code", x.nextToken());
/*  63 */       jo.put("Reason-Phrase", x.nextTo(false));
/*  64 */       x.next();
/*     */     
/*     */     }
/*     */     else {
/*     */ 
/*     */       
/*  70 */       jo.put("Method", token);
/*  71 */       jo.put("Request-URI", x.nextToken());
/*  72 */       jo.put("HTTP-Version", x.nextToken());
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/*  77 */     while (x.more()) {
/*  78 */       String name = x.nextTo(':');
/*  79 */       x.next(':');
/*  80 */       jo.put(name, x.nextTo(false));
/*  81 */       x.next();
/*     */     } 
/*  83 */     return jo;
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
/*     */   public static String toString(JSONObject jo) throws JSONException {
/* 108 */     StringBuilder sb = new StringBuilder();
/* 109 */     if (jo.has("Status-Code") && jo.has("Reason-Phrase")) {
/* 110 */       sb.append(jo.getString("HTTP-Version"));
/* 111 */       sb.append(' ');
/* 112 */       sb.append(jo.getString("Status-Code"));
/* 113 */       sb.append(' ');
/* 114 */       sb.append(jo.getString("Reason-Phrase"));
/* 115 */     } else if (jo.has("Method") && jo.has("Request-URI")) {
/* 116 */       sb.append(jo.getString("Method"));
/* 117 */       sb.append(' ');
/* 118 */       sb.append('"');
/* 119 */       sb.append(jo.getString("Request-URI"));
/* 120 */       sb.append('"');
/* 121 */       sb.append(' ');
/* 122 */       sb.append(jo.getString("HTTP-Version"));
/*     */     } else {
/* 124 */       throw new JSONException("Not enough material for an HTTP header.");
/*     */     } 
/* 126 */     sb.append("\r\n");
/*     */     
/* 128 */     for (String key : jo.keySet()) {
/* 129 */       String value = jo.optString(key);
/* 130 */       if (!"HTTP-Version".equals(key) && !"Status-Code".equals(key) && 
/* 131 */         !"Reason-Phrase".equals(key) && !"Method".equals(key) && 
/* 132 */         !"Request-URI".equals(key) && !JSONObject.NULL.equals(value)) {
/* 133 */         sb.append(key);
/* 134 */         sb.append(": ");
/* 135 */         sb.append(jo.optString(key));
/* 136 */         sb.append("\r\n");
/*     */       } 
/*     */     } 
/* 139 */     sb.append("\r\n");
/* 140 */     return sb.toString();
/*     */   }
/*     */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\org\json\HTTP.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */