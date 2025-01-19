/*     */ package org.json;
/*     */ 
/*     */ import java.io.BufferedReader;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import java.io.InputStreamReader;
/*     */ import java.io.Reader;
/*     */ import java.io.StringReader;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class JSONTokener
/*     */ {
/*     */   private long character;
/*     */   private boolean eof;
/*     */   private long index;
/*     */   private long line;
/*     */   private char previous;
/*     */   private final Reader reader;
/*     */   private boolean usePrevious;
/*     */   private long characterPreviousLine;
/*     */   
/*     */   public JSONTokener(Reader reader) {
/*  46 */     this.reader = reader.markSupported() ? reader : new BufferedReader(reader);
/*     */ 
/*     */     
/*  49 */     this.eof = false;
/*  50 */     this.usePrevious = false;
/*  51 */     this.previous = Character.MIN_VALUE;
/*  52 */     this.index = 0L;
/*  53 */     this.character = 1L;
/*  54 */     this.characterPreviousLine = 0L;
/*  55 */     this.line = 1L;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public JSONTokener(InputStream inputStream) {
/*  64 */     this(new InputStreamReader(inputStream));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public JSONTokener(String s) {
/*  74 */     this(new StringReader(s));
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
/*     */   public void back() throws JSONException {
/*  86 */     if (this.usePrevious || this.index <= 0L) {
/*  87 */       throw new JSONException("Stepping back two steps is not supported");
/*     */     }
/*  89 */     decrementIndexes();
/*  90 */     this.usePrevious = true;
/*  91 */     this.eof = false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void decrementIndexes() {
/*  98 */     this.index--;
/*  99 */     if (this.previous == '\r' || this.previous == '\n') {
/* 100 */       this.line--;
/* 101 */       this.character = this.characterPreviousLine;
/* 102 */     } else if (this.character > 0L) {
/* 103 */       this.character--;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static int dehexchar(char c) {
/* 114 */     if (c >= '0' && c <= '9') {
/* 115 */       return c - 48;
/*     */     }
/* 117 */     if (c >= 'A' && c <= 'F') {
/* 118 */       return c - 55;
/*     */     }
/* 120 */     if (c >= 'a' && c <= 'f') {
/* 121 */       return c - 87;
/*     */     }
/* 123 */     return -1;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean end() {
/* 132 */     return (this.eof && !this.usePrevious);
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
/*     */   public boolean more() throws JSONException {
/* 144 */     if (this.usePrevious) {
/* 145 */       return true;
/*     */     }
/*     */     try {
/* 148 */       this.reader.mark(1);
/* 149 */     } catch (IOException e) {
/* 150 */       throw new JSONException("Unable to preserve stream position", e);
/*     */     } 
/*     */     
/*     */     try {
/* 154 */       if (this.reader.read() <= 0) {
/* 155 */         this.eof = true;
/* 156 */         return false;
/*     */       } 
/* 158 */       this.reader.reset();
/* 159 */     } catch (IOException e) {
/* 160 */       throw new JSONException("Unable to read the next character from the stream", e);
/*     */     } 
/* 162 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public char next() throws JSONException {
/*     */     int c;
/* 174 */     if (this.usePrevious) {
/* 175 */       this.usePrevious = false;
/* 176 */       c = this.previous;
/*     */     } else {
/*     */       try {
/* 179 */         c = this.reader.read();
/* 180 */       } catch (IOException exception) {
/* 181 */         throw new JSONException(exception);
/*     */       } 
/*     */     } 
/* 184 */     if (c <= 0) {
/* 185 */       this.eof = true;
/* 186 */       return Character.MIN_VALUE;
/*     */     } 
/* 188 */     incrementIndexes(c);
/* 189 */     this.previous = (char)c;
/* 190 */     return this.previous;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected char getPrevious() {
/* 197 */     return this.previous;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void incrementIndexes(int c) {
/* 205 */     if (c > 0) {
/* 206 */       this.index++;
/* 207 */       if (c == 13) {
/* 208 */         this.line++;
/* 209 */         this.characterPreviousLine = this.character;
/* 210 */         this.character = 0L;
/* 211 */       } else if (c == 10) {
/* 212 */         if (this.previous != '\r') {
/* 213 */           this.line++;
/* 214 */           this.characterPreviousLine = this.character;
/*     */         } 
/* 216 */         this.character = 0L;
/*     */       } else {
/* 218 */         this.character++;
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
/*     */   public char next(char c) throws JSONException {
/* 231 */     char n = next();
/* 232 */     if (n != c) {
/* 233 */       if (n > '\000') {
/* 234 */         throw syntaxError("Expected '" + c + "' and instead saw '" + n + "'");
/*     */       }
/*     */       
/* 237 */       throw syntaxError("Expected '" + c + "' and instead saw ''");
/*     */     } 
/* 239 */     return n;
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
/*     */   public String next(int n) throws JSONException {
/* 253 */     if (n == 0) {
/* 254 */       return "";
/*     */     }
/*     */     
/* 257 */     char[] chars = new char[n];
/* 258 */     int pos = 0;
/*     */     
/* 260 */     while (pos < n) {
/* 261 */       chars[pos] = next();
/* 262 */       if (end()) {
/* 263 */         throw syntaxError("Substring bounds error");
/*     */       }
/* 265 */       pos++;
/*     */     } 
/* 267 */     return new String(chars);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public char nextClean() throws JSONException {
/*     */     char c;
/*     */     do {
/* 278 */       c = next();
/* 279 */     } while (c != '\000' && c <= ' ');
/* 280 */     return c;
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
/*     */   public String nextString(char quote) throws JSONException {
/* 299 */     StringBuilder sb = new StringBuilder();
/*     */     while (true) {
/* 301 */       char c = next();
/* 302 */       switch (c) {
/*     */         case '\000':
/*     */         case '\n':
/*     */         case '\r':
/* 306 */           throw syntaxError("Unterminated string");
/*     */         case '\\':
/* 308 */           c = next();
/* 309 */           switch (c) {
/*     */             case 'b':
/* 311 */               sb.append('\b');
/*     */               continue;
/*     */             case 't':
/* 314 */               sb.append('\t');
/*     */               continue;
/*     */             case 'n':
/* 317 */               sb.append('\n');
/*     */               continue;
/*     */             case 'f':
/* 320 */               sb.append('\f');
/*     */               continue;
/*     */             case 'r':
/* 323 */               sb.append('\r');
/*     */               continue;
/*     */             case 'u':
/*     */               try {
/* 327 */                 sb.append((char)Integer.parseInt(next(4), 16));
/* 328 */               } catch (NumberFormatException e) {
/* 329 */                 throw syntaxError("Illegal escape.", e);
/*     */               } 
/*     */               continue;
/*     */             case '"':
/*     */             case '\'':
/*     */             case '/':
/*     */             case '\\':
/* 336 */               sb.append(c);
/*     */               continue;
/*     */           } 
/* 339 */           throw syntaxError("Illegal escape.");
/*     */       } 
/*     */ 
/*     */       
/* 343 */       if (c == quote) {
/* 344 */         return sb.toString();
/*     */       }
/* 346 */       sb.append(c);
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
/*     */   public String nextTo(char delimiter) throws JSONException {
/* 361 */     StringBuilder sb = new StringBuilder();
/*     */     while (true) {
/* 363 */       char c = next();
/* 364 */       if (c == delimiter || c == '\000' || c == '\n' || c == '\r') {
/* 365 */         if (c != '\000') {
/* 366 */           back();
/*     */         }
/* 368 */         return sb.toString().trim();
/*     */       } 
/* 370 */       sb.append(c);
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
/*     */   public String nextTo(String delimiters) throws JSONException {
/* 385 */     StringBuilder sb = new StringBuilder();
/*     */     while (true) {
/* 387 */       char c = next();
/* 388 */       if (delimiters.indexOf(c) >= 0 || c == '\000' || c == '\n' || c == '\r') {
/*     */         
/* 390 */         if (c != '\000') {
/* 391 */           back();
/*     */         }
/* 393 */         return sb.toString().trim();
/*     */       } 
/* 395 */       sb.append(c);
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
/*     */   public Object nextValue() throws JSONException {
/* 408 */     char c = nextClean();
/*     */ 
/*     */     
/* 411 */     switch (c) {
/*     */       case '"':
/*     */       case '\'':
/* 414 */         return nextString(c);
/*     */       case '{':
/* 416 */         back();
/*     */         try {
/* 418 */           return new JSONObject(this);
/* 419 */         } catch (StackOverflowError e) {
/* 420 */           throw new JSONException("JSON Array or Object depth too large to process.", e);
/*     */         } 
/*     */       case '[':
/* 423 */         back();
/*     */         try {
/* 425 */           return new JSONArray(this);
/* 426 */         } catch (StackOverflowError e) {
/* 427 */           throw new JSONException("JSON Array or Object depth too large to process.", e);
/*     */         } 
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
/* 440 */     StringBuilder sb = new StringBuilder();
/* 441 */     while (c >= ' ' && ",:]}/\\\"[{;=#".indexOf(c) < 0) {
/* 442 */       sb.append(c);
/* 443 */       c = next();
/*     */     } 
/* 445 */     if (!this.eof) {
/* 446 */       back();
/*     */     }
/*     */     
/* 449 */     String string = sb.toString().trim();
/* 450 */     if ("".equals(string)) {
/* 451 */       throw syntaxError("Missing value");
/*     */     }
/* 453 */     return JSONObject.stringToValue(string);
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
/*     */   public char skipTo(char to) throws JSONException {
/*     */     try {
/* 469 */       long startIndex = this.index;
/* 470 */       long startCharacter = this.character;
/* 471 */       long startLine = this.line;
/* 472 */       this.reader.mark(1000000);
/*     */       while (true) {
/* 474 */         char c = next();
/* 475 */         if (c == '\000') {
/*     */ 
/*     */ 
/*     */           
/* 479 */           this.reader.reset();
/* 480 */           this.index = startIndex;
/* 481 */           this.character = startCharacter;
/* 482 */           this.line = startLine;
/* 483 */           return Character.MIN_VALUE;
/*     */         } 
/* 485 */         if (c == to) {
/* 486 */           this.reader.mark(1);
/*     */ 
/*     */ 
/*     */           
/* 490 */           back();
/* 491 */           return c;
/*     */         } 
/*     */       } 
/*     */     } catch (IOException exception) {
/*     */       throw new JSONException(exception);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public JSONException syntaxError(String message) {
/* 501 */     return new JSONException(message + toString());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public JSONException syntaxError(String message, Throwable causedBy) {
/* 512 */     return new JSONException(message + toString(), causedBy);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String toString() {
/* 522 */     return " at " + this.index + " [character " + this.character + " line " + this.line + "]";
/*     */   }
/*     */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\org\json\JSONTokener.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */