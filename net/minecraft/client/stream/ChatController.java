/*     */ package net.minecraft.client.stream;
/*     */ 
/*     */ import com.google.common.collect.Lists;
/*     */ import java.util.HashMap;
/*     */ import java.util.HashSet;
/*     */ import java.util.LinkedList;
/*     */ import java.util.List;
/*     */ import java.util.ListIterator;
/*     */ import org.apache.logging.log4j.LogManager;
/*     */ import org.apache.logging.log4j.Logger;
/*     */ import tv.twitch.AuthToken;
/*     */ import tv.twitch.Core;
/*     */ import tv.twitch.CoreAPI;
/*     */ import tv.twitch.ErrorCode;
/*     */ import tv.twitch.StandardCoreAPI;
/*     */ import tv.twitch.chat.Chat;
/*     */ import tv.twitch.chat.ChatAPI;
/*     */ import tv.twitch.chat.ChatBadgeData;
/*     */ import tv.twitch.chat.ChatChannelInfo;
/*     */ import tv.twitch.chat.ChatEmoticonData;
/*     */ import tv.twitch.chat.ChatEvent;
/*     */ import tv.twitch.chat.ChatRawMessage;
/*     */ import tv.twitch.chat.ChatTokenizationOption;
/*     */ import tv.twitch.chat.ChatTokenizedMessage;
/*     */ import tv.twitch.chat.ChatUserInfo;
/*     */ import tv.twitch.chat.IChatAPIListener;
/*     */ import tv.twitch.chat.IChatChannelListener;
/*     */ import tv.twitch.chat.StandardChatAPI;
/*     */ 
/*     */ public class ChatController {
/*  31 */   private static final Logger LOGGER = LogManager.getLogger();
/*  32 */   protected ChatListener field_153003_a = null;
/*  33 */   protected String field_153004_b = "";
/*  34 */   protected String field_153006_d = "";
/*  35 */   protected String field_153007_e = "";
/*  36 */   protected Core field_175992_e = null;
/*  37 */   protected Chat field_153008_f = null;
/*  38 */   protected ChatState field_153011_i = ChatState.Uninitialized;
/*  39 */   protected AuthToken field_153012_j = new AuthToken();
/*  40 */   protected HashMap<String, ChatChannelListener> field_175998_i = new HashMap<>();
/*  41 */   protected int field_153015_m = 128;
/*  42 */   protected EnumEmoticonMode field_175997_k = EnumEmoticonMode.None;
/*  43 */   protected EnumEmoticonMode field_175995_l = EnumEmoticonMode.None;
/*  44 */   protected ChatEmoticonData field_175996_m = null;
/*  45 */   protected int field_175993_n = 500;
/*  46 */   protected int field_175994_o = 2000;
/*  47 */   protected IChatAPIListener field_175999_p = new IChatAPIListener() {
/*     */       public void chatInitializationCallback(ErrorCode p_chatInitializationCallback_1_) {
/*  49 */         if (ErrorCode.succeeded(p_chatInitializationCallback_1_)) {
/*  50 */           ChatController.this.field_153008_f.setMessageFlushInterval(ChatController.this.field_175993_n);
/*  51 */           ChatController.this.field_153008_f.setUserChangeEventInterval(ChatController.this.field_175994_o);
/*  52 */           ChatController.this.func_153001_r();
/*  53 */           ChatController.this.func_175985_a(ChatController.ChatState.Initialized);
/*     */         } else {
/*  55 */           ChatController.this.func_175985_a(ChatController.ChatState.Uninitialized);
/*     */         } 
/*     */         
/*     */         try {
/*  59 */           if (ChatController.this.field_153003_a != null) {
/*  60 */             ChatController.this.field_153003_a.func_176023_d(p_chatInitializationCallback_1_);
/*     */           }
/*  62 */         } catch (Exception exception) {
/*  63 */           ChatController.this.func_152995_h(exception.toString());
/*     */         } 
/*     */       }
/*     */       
/*     */       public void chatShutdownCallback(ErrorCode p_chatShutdownCallback_1_) {
/*  68 */         if (ErrorCode.succeeded(p_chatShutdownCallback_1_)) {
/*  69 */           ErrorCode errorcode = ChatController.this.field_175992_e.shutdown();
/*     */           
/*  71 */           if (ErrorCode.failed(errorcode)) {
/*  72 */             String s = ErrorCode.getString(errorcode);
/*  73 */             ChatController.this.func_152995_h(String.format("Error shutting down the Twitch sdk: %s", new Object[] { s }));
/*     */           } 
/*     */           
/*  76 */           ChatController.this.func_175985_a(ChatController.ChatState.Uninitialized);
/*     */         } else {
/*  78 */           ChatController.this.func_175985_a(ChatController.ChatState.Initialized);
/*  79 */           ChatController.this.func_152995_h(String.format("Error shutting down Twith chat: %s", new Object[] { p_chatShutdownCallback_1_ }));
/*     */         } 
/*     */         
/*     */         try {
/*  83 */           if (ChatController.this.field_153003_a != null) {
/*  84 */             ChatController.this.field_153003_a.func_176022_e(p_chatShutdownCallback_1_);
/*     */           }
/*  86 */         } catch (Exception exception) {
/*  87 */           ChatController.this.func_152995_h(exception.toString());
/*     */         } 
/*     */       }
/*     */       
/*     */       public void chatEmoticonDataDownloadCallback(ErrorCode p_chatEmoticonDataDownloadCallback_1_) {
/*  92 */         if (ErrorCode.succeeded(p_chatEmoticonDataDownloadCallback_1_)) {
/*  93 */           ChatController.this.func_152988_s();
/*     */         }
/*     */       }
/*     */     };
/*     */   
/*     */   public void func_152990_a(ChatListener p_152990_1_) {
/*  99 */     this.field_153003_a = p_152990_1_;
/*     */   }
/*     */   
/*     */   public void func_152994_a(AuthToken p_152994_1_) {
/* 103 */     this.field_153012_j = p_152994_1_;
/*     */   }
/*     */   
/*     */   public void func_152984_a(String p_152984_1_) {
/* 107 */     this.field_153006_d = p_152984_1_;
/*     */   }
/*     */   
/*     */   public void func_152998_c(String p_152998_1_) {
/* 111 */     this.field_153004_b = p_152998_1_;
/*     */   }
/*     */   
/*     */   public ChatState func_153000_j() {
/* 115 */     return this.field_153011_i;
/*     */   }
/*     */   
/*     */   public boolean func_175990_d(String p_175990_1_) {
/* 119 */     if (!this.field_175998_i.containsKey(p_175990_1_)) {
/* 120 */       return false;
/*     */     }
/* 122 */     ChatChannelListener chatcontroller$chatchannellistener = this.field_175998_i.get(p_175990_1_);
/* 123 */     return (chatcontroller$chatchannellistener.func_176040_a() == EnumChannelState.Connected);
/*     */   }
/*     */ 
/*     */   
/*     */   public EnumChannelState func_175989_e(String p_175989_1_) {
/* 128 */     if (!this.field_175998_i.containsKey(p_175989_1_)) {
/* 129 */       return EnumChannelState.Disconnected;
/*     */     }
/* 131 */     ChatChannelListener chatcontroller$chatchannellistener = this.field_175998_i.get(p_175989_1_);
/* 132 */     return chatcontroller$chatchannellistener.func_176040_a();
/*     */   }
/*     */ 
/*     */   
/*     */   public ChatController() {
/* 137 */     this.field_175992_e = Core.getInstance();
/*     */     
/* 139 */     if (this.field_175992_e == null) {
/* 140 */       this.field_175992_e = new Core((CoreAPI)new StandardCoreAPI());
/*     */     }
/*     */     
/* 143 */     this.field_153008_f = new Chat((ChatAPI)new StandardChatAPI());
/*     */   }
/*     */   
/*     */   public boolean func_175984_n() {
/* 147 */     if (this.field_153011_i != ChatState.Uninitialized) {
/* 148 */       return false;
/*     */     }
/* 150 */     func_175985_a(ChatState.Initializing);
/* 151 */     ErrorCode errorcode = this.field_175992_e.initialize(this.field_153006_d, null);
/*     */     
/* 153 */     if (ErrorCode.failed(errorcode)) {
/* 154 */       func_175985_a(ChatState.Uninitialized);
/* 155 */       String s1 = ErrorCode.getString(errorcode);
/* 156 */       func_152995_h(String.format("Error initializing Twitch sdk: %s", new Object[] { s1 }));
/* 157 */       return false;
/*     */     } 
/* 159 */     this.field_175995_l = this.field_175997_k;
/* 160 */     HashSet<ChatTokenizationOption> hashset = new HashSet<>();
/*     */     
/* 162 */     switch (this.field_175997_k) {
/*     */       case null:
/* 164 */         hashset.add(ChatTokenizationOption.TTV_CHAT_TOKENIZATION_OPTION_NONE);
/*     */         break;
/*     */       
/*     */       case Url:
/* 168 */         hashset.add(ChatTokenizationOption.TTV_CHAT_TOKENIZATION_OPTION_EMOTICON_URLS);
/*     */         break;
/*     */       
/*     */       case TextureAtlas:
/* 172 */         hashset.add(ChatTokenizationOption.TTV_CHAT_TOKENIZATION_OPTION_EMOTICON_TEXTURES);
/*     */         break;
/*     */     } 
/* 175 */     errorcode = this.field_153008_f.initialize(hashset, this.field_175999_p);
/*     */     
/* 177 */     if (ErrorCode.failed(errorcode)) {
/* 178 */       this.field_175992_e.shutdown();
/* 179 */       func_175985_a(ChatState.Uninitialized);
/* 180 */       String s = ErrorCode.getString(errorcode);
/* 181 */       func_152995_h(String.format("Error initializing Twitch chat: %s", new Object[] { s }));
/* 182 */       return false;
/*     */     } 
/* 184 */     func_175985_a(ChatState.Initialized);
/* 185 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean func_152986_d(String p_152986_1_) {
/* 192 */     return func_175987_a(p_152986_1_, false);
/*     */   }
/*     */   
/*     */   protected boolean func_175987_a(String p_175987_1_, boolean p_175987_2_) {
/* 196 */     if (this.field_153011_i != ChatState.Initialized)
/* 197 */       return false; 
/* 198 */     if (this.field_175998_i.containsKey(p_175987_1_)) {
/* 199 */       func_152995_h("Already in channel: " + p_175987_1_);
/* 200 */       return false;
/* 201 */     }  if (p_175987_1_ != null && !p_175987_1_.equals("")) {
/* 202 */       ChatChannelListener chatcontroller$chatchannellistener = new ChatChannelListener(p_175987_1_);
/* 203 */       this.field_175998_i.put(p_175987_1_, chatcontroller$chatchannellistener);
/* 204 */       boolean flag = chatcontroller$chatchannellistener.func_176038_a(p_175987_2_);
/*     */       
/* 206 */       if (!flag) {
/* 207 */         this.field_175998_i.remove(p_175987_1_);
/*     */       }
/*     */       
/* 210 */       return flag;
/*     */     } 
/* 212 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean func_175991_l(String p_175991_1_) {
/* 217 */     if (this.field_153011_i != ChatState.Initialized)
/* 218 */       return false; 
/* 219 */     if (!this.field_175998_i.containsKey(p_175991_1_)) {
/* 220 */       func_152995_h("Not in channel: " + p_175991_1_);
/* 221 */       return false;
/*     */     } 
/* 223 */     ChatChannelListener chatcontroller$chatchannellistener = this.field_175998_i.get(p_175991_1_);
/* 224 */     return chatcontroller$chatchannellistener.func_176034_g();
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean func_152993_m() {
/* 229 */     if (this.field_153011_i != ChatState.Initialized) {
/* 230 */       return false;
/*     */     }
/* 232 */     ErrorCode errorcode = this.field_153008_f.shutdown();
/*     */     
/* 234 */     if (ErrorCode.failed(errorcode)) {
/* 235 */       String s = ErrorCode.getString(errorcode);
/* 236 */       func_152995_h(String.format("Error shutting down chat: %s", new Object[] { s }));
/* 237 */       return false;
/*     */     } 
/* 239 */     func_152996_t();
/* 240 */     func_175985_a(ChatState.ShuttingDown);
/* 241 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void func_175988_p() {
/* 247 */     if (func_153000_j() != ChatState.Uninitialized) {
/* 248 */       func_152993_m();
/*     */       
/* 250 */       if (func_153000_j() == ChatState.ShuttingDown) {
/* 251 */         while (func_153000_j() != ChatState.Uninitialized) {
/*     */           try {
/* 253 */             Thread.sleep(200L);
/* 254 */             func_152997_n();
/* 255 */           } catch (InterruptedException interruptedException) {}
/*     */         } 
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void func_152997_n() {
/* 264 */     if (this.field_153011_i != ChatState.Uninitialized) {
/* 265 */       ErrorCode errorcode = this.field_153008_f.flushEvents();
/*     */       
/* 267 */       if (ErrorCode.failed(errorcode)) {
/* 268 */         String s = ErrorCode.getString(errorcode);
/* 269 */         func_152995_h(String.format("Error flushing chat events: %s", new Object[] { s }));
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   public boolean func_175986_a(String p_175986_1_, String p_175986_2_) {
/* 275 */     if (this.field_153011_i != ChatState.Initialized)
/* 276 */       return false; 
/* 277 */     if (!this.field_175998_i.containsKey(p_175986_1_)) {
/* 278 */       func_152995_h("Not in channel: " + p_175986_1_);
/* 279 */       return false;
/*     */     } 
/* 281 */     ChatChannelListener chatcontroller$chatchannellistener = this.field_175998_i.get(p_175986_1_);
/* 282 */     return chatcontroller$chatchannellistener.func_176037_b(p_175986_2_);
/*     */   }
/*     */ 
/*     */   
/*     */   protected void func_175985_a(ChatState p_175985_1_) {
/* 287 */     if (p_175985_1_ != this.field_153011_i) {
/* 288 */       this.field_153011_i = p_175985_1_;
/*     */       
/*     */       try {
/* 291 */         if (this.field_153003_a != null) {
/* 292 */           this.field_153003_a.func_176017_a(p_175985_1_);
/*     */         }
/* 294 */       } catch (Exception exception) {
/* 295 */         func_152995_h(exception.toString());
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   protected void func_153001_r() {
/* 301 */     if (this.field_175995_l != EnumEmoticonMode.None && 
/* 302 */       this.field_175996_m == null) {
/* 303 */       ErrorCode errorcode = this.field_153008_f.downloadEmoticonData();
/*     */       
/* 305 */       if (ErrorCode.failed(errorcode)) {
/* 306 */         String s = ErrorCode.getString(errorcode);
/* 307 */         func_152995_h(String.format("Error trying to download emoticon data: %s", new Object[] { s }));
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   protected void func_152988_s() {
/* 314 */     if (this.field_175996_m == null) {
/* 315 */       this.field_175996_m = new ChatEmoticonData();
/* 316 */       ErrorCode errorcode = this.field_153008_f.getEmoticonData(this.field_175996_m);
/*     */       
/* 318 */       if (ErrorCode.succeeded(errorcode)) {
/*     */         try {
/* 320 */           if (this.field_153003_a != null) {
/* 321 */             this.field_153003_a.func_176021_d();
/*     */           }
/* 323 */         } catch (Exception exception) {
/* 324 */           func_152995_h(exception.toString());
/*     */         } 
/*     */       } else {
/* 327 */         func_152995_h("Error preparing emoticon data: " + ErrorCode.getString(errorcode));
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   protected void func_152996_t() {
/* 333 */     if (this.field_175996_m != null) {
/* 334 */       ErrorCode errorcode = this.field_153008_f.clearEmoticonData();
/*     */       
/* 336 */       if (ErrorCode.succeeded(errorcode)) {
/* 337 */         this.field_175996_m = null;
/*     */         
/*     */         try {
/* 340 */           if (this.field_153003_a != null) {
/* 341 */             this.field_153003_a.func_176024_e();
/*     */           }
/* 343 */         } catch (Exception exception) {
/* 344 */           func_152995_h(exception.toString());
/*     */         } 
/*     */       } else {
/* 347 */         func_152995_h("Error clearing emoticon data: " + ErrorCode.getString(errorcode));
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   protected void func_152995_h(String p_152995_1_) {
/* 353 */     LOGGER.error(TwitchStream.STREAM_MARKER, "[Chat controller] {}", new Object[] { p_152995_1_ });
/*     */   }
/*     */   
/*     */   public class ChatChannelListener implements IChatChannelListener {
/* 357 */     protected String field_176048_a = null;
/*     */     protected boolean field_176046_b = false;
/* 359 */     protected ChatController.EnumChannelState field_176047_c = ChatController.EnumChannelState.Created;
/* 360 */     protected List<ChatUserInfo> field_176044_d = Lists.newArrayList();
/* 361 */     protected LinkedList<ChatRawMessage> field_176045_e = new LinkedList<>();
/* 362 */     protected LinkedList<ChatTokenizedMessage> field_176042_f = new LinkedList<>();
/* 363 */     protected ChatBadgeData field_176043_g = null;
/*     */     
/*     */     public ChatChannelListener(String p_i46061_2_) {
/* 366 */       this.field_176048_a = p_i46061_2_;
/*     */     }
/*     */     
/*     */     public ChatController.EnumChannelState func_176040_a() {
/* 370 */       return this.field_176047_c;
/*     */     }
/*     */     
/*     */     public boolean func_176038_a(boolean p_176038_1_) {
/* 374 */       this.field_176046_b = p_176038_1_;
/* 375 */       ErrorCode errorcode = ErrorCode.TTV_EC_SUCCESS;
/*     */       
/* 377 */       if (p_176038_1_) {
/* 378 */         errorcode = ChatController.this.field_153008_f.connectAnonymous(this.field_176048_a, this);
/*     */       } else {
/* 380 */         errorcode = ChatController.this.field_153008_f.connect(this.field_176048_a, ChatController.this.field_153004_b, ChatController.this.field_153012_j.data, this);
/*     */       } 
/*     */       
/* 383 */       if (ErrorCode.failed(errorcode)) {
/* 384 */         String s = ErrorCode.getString(errorcode);
/* 385 */         ChatController.this.func_152995_h(String.format("Error connecting: %s", new Object[] { s }));
/* 386 */         func_176036_d(this.field_176048_a);
/* 387 */         return false;
/*     */       } 
/* 389 */       func_176035_a(ChatController.EnumChannelState.Connecting);
/* 390 */       func_176041_h();
/* 391 */       return true;
/*     */     }
/*     */     
/*     */     public boolean func_176034_g() {
/*     */       ErrorCode errorcode;
/* 396 */       switch (this.field_176047_c) {
/*     */         case Connecting:
/*     */         case null:
/* 399 */           errorcode = ChatController.this.field_153008_f.disconnect(this.field_176048_a);
/*     */           
/* 401 */           if (ErrorCode.failed(errorcode)) {
/* 402 */             String s = ErrorCode.getString(errorcode);
/* 403 */             ChatController.this.func_152995_h(String.format("Error disconnecting: %s", new Object[] { s }));
/* 404 */             return false;
/*     */           } 
/*     */           
/* 407 */           func_176035_a(ChatController.EnumChannelState.Disconnecting);
/* 408 */           return true;
/*     */       } 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 414 */       return false;
/*     */     }
/*     */ 
/*     */     
/*     */     protected void func_176035_a(ChatController.EnumChannelState p_176035_1_) {
/* 419 */       if (p_176035_1_ != this.field_176047_c) {
/* 420 */         this.field_176047_c = p_176035_1_;
/*     */       }
/*     */     }
/*     */     
/*     */     public void func_176032_a(String p_176032_1_) {
/* 425 */       if (ChatController.this.field_175995_l == ChatController.EnumEmoticonMode.None) {
/* 426 */         this.field_176045_e.clear();
/* 427 */         this.field_176042_f.clear();
/*     */       } else {
/* 429 */         if (this.field_176045_e.size() > 0) {
/* 430 */           ListIterator<ChatRawMessage> listiterator = this.field_176045_e.listIterator();
/*     */           
/* 432 */           while (listiterator.hasNext()) {
/* 433 */             ChatRawMessage chatrawmessage = listiterator.next();
/*     */             
/* 435 */             if (chatrawmessage.userName.equals(p_176032_1_)) {
/* 436 */               listiterator.remove();
/*     */             }
/*     */           } 
/*     */         } 
/*     */         
/* 441 */         if (this.field_176042_f.size() > 0) {
/* 442 */           ListIterator<ChatTokenizedMessage> listiterator1 = this.field_176042_f.listIterator();
/*     */           
/* 444 */           while (listiterator1.hasNext()) {
/* 445 */             ChatTokenizedMessage chattokenizedmessage = listiterator1.next();
/*     */             
/* 447 */             if (chattokenizedmessage.displayName.equals(p_176032_1_)) {
/* 448 */               listiterator1.remove();
/*     */             }
/*     */           } 
/*     */         } 
/*     */       } 
/*     */       
/*     */       try {
/* 455 */         if (ChatController.this.field_153003_a != null) {
/* 456 */           ChatController.this.field_153003_a.func_176019_a(this.field_176048_a, p_176032_1_);
/*     */         }
/* 458 */       } catch (Exception exception) {
/* 459 */         ChatController.this.func_152995_h(exception.toString());
/*     */       } 
/*     */     }
/*     */     
/*     */     public boolean func_176037_b(String p_176037_1_) {
/* 464 */       if (this.field_176047_c != ChatController.EnumChannelState.Connected) {
/* 465 */         return false;
/*     */       }
/* 467 */       ErrorCode errorcode = ChatController.this.field_153008_f.sendMessage(this.field_176048_a, p_176037_1_);
/*     */       
/* 469 */       if (ErrorCode.failed(errorcode)) {
/* 470 */         String s = ErrorCode.getString(errorcode);
/* 471 */         ChatController.this.func_152995_h(String.format("Error sending chat message: %s", new Object[] { s }));
/* 472 */         return false;
/*     */       } 
/* 474 */       return true;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     protected void func_176041_h() {
/* 480 */       if (ChatController.this.field_175995_l != ChatController.EnumEmoticonMode.None && 
/* 481 */         this.field_176043_g == null) {
/* 482 */         ErrorCode errorcode = ChatController.this.field_153008_f.downloadBadgeData(this.field_176048_a);
/*     */         
/* 484 */         if (ErrorCode.failed(errorcode)) {
/* 485 */           String s = ErrorCode.getString(errorcode);
/* 486 */           ChatController.this.func_152995_h(String.format("Error trying to download badge data: %s", new Object[] { s }));
/*     */         } 
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/*     */     protected void func_176039_i() {
/* 493 */       if (this.field_176043_g == null) {
/* 494 */         this.field_176043_g = new ChatBadgeData();
/* 495 */         ErrorCode errorcode = ChatController.this.field_153008_f.getBadgeData(this.field_176048_a, this.field_176043_g);
/*     */         
/* 497 */         if (ErrorCode.succeeded(errorcode)) {
/*     */           try {
/* 499 */             if (ChatController.this.field_153003_a != null) {
/* 500 */               ChatController.this.field_153003_a.func_176016_c(this.field_176048_a);
/*     */             }
/* 502 */           } catch (Exception exception) {
/* 503 */             ChatController.this.func_152995_h(exception.toString());
/*     */           } 
/*     */         } else {
/* 506 */           ChatController.this.func_152995_h("Error preparing badge data: " + ErrorCode.getString(errorcode));
/*     */         } 
/*     */       } 
/*     */     }
/*     */     
/*     */     protected void func_176033_j() {
/* 512 */       if (this.field_176043_g != null) {
/* 513 */         ErrorCode errorcode = ChatController.this.field_153008_f.clearBadgeData(this.field_176048_a);
/*     */         
/* 515 */         if (ErrorCode.succeeded(errorcode)) {
/* 516 */           this.field_176043_g = null;
/*     */           
/*     */           try {
/* 519 */             if (ChatController.this.field_153003_a != null) {
/* 520 */               ChatController.this.field_153003_a.func_176020_d(this.field_176048_a);
/*     */             }
/* 522 */           } catch (Exception exception) {
/* 523 */             ChatController.this.func_152995_h(exception.toString());
/*     */           } 
/*     */         } else {
/* 526 */           ChatController.this.func_152995_h("Error releasing badge data: " + ErrorCode.getString(errorcode));
/*     */         } 
/*     */       } 
/*     */     }
/*     */     
/*     */     protected void func_176031_c(String p_176031_1_) {
/*     */       try {
/* 533 */         if (ChatController.this.field_153003_a != null) {
/* 534 */           ChatController.this.field_153003_a.func_180606_a(p_176031_1_);
/*     */         }
/* 536 */       } catch (Exception exception) {
/* 537 */         ChatController.this.func_152995_h(exception.toString());
/*     */       } 
/*     */     }
/*     */     
/*     */     protected void func_176036_d(String p_176036_1_) {
/*     */       try {
/* 543 */         if (ChatController.this.field_153003_a != null) {
/* 544 */           ChatController.this.field_153003_a.func_180607_b(p_176036_1_);
/*     */         }
/* 546 */       } catch (Exception exception) {
/* 547 */         ChatController.this.func_152995_h(exception.toString());
/*     */       } 
/*     */     }
/*     */     
/*     */     private void func_176030_k() {
/* 552 */       if (this.field_176047_c != ChatController.EnumChannelState.Disconnected) {
/* 553 */         func_176035_a(ChatController.EnumChannelState.Disconnected);
/* 554 */         func_176036_d(this.field_176048_a);
/* 555 */         func_176033_j();
/*     */       } 
/*     */     }
/*     */     
/*     */     public void chatStatusCallback(String p_chatStatusCallback_1_, ErrorCode p_chatStatusCallback_2_) {
/* 560 */       if (!ErrorCode.succeeded(p_chatStatusCallback_2_)) {
/* 561 */         ChatController.this.field_175998_i.remove(p_chatStatusCallback_1_);
/* 562 */         func_176030_k();
/*     */       } 
/*     */     }
/*     */     
/*     */     public void chatChannelMembershipCallback(String p_chatChannelMembershipCallback_1_, ChatEvent p_chatChannelMembershipCallback_2_, ChatChannelInfo p_chatChannelMembershipCallback_3_) {
/* 567 */       switch (p_chatChannelMembershipCallback_2_) {
/*     */         case null:
/* 569 */           func_176035_a(ChatController.EnumChannelState.Connected);
/* 570 */           func_176031_c(p_chatChannelMembershipCallback_1_);
/*     */           break;
/*     */         
/*     */         case TTV_CHAT_LEFT_CHANNEL:
/* 574 */           func_176030_k();
/*     */           break;
/*     */       } 
/*     */     }
/*     */     public void chatChannelUserChangeCallback(String p_chatChannelUserChangeCallback_1_, ChatUserInfo[] p_chatChannelUserChangeCallback_2_, ChatUserInfo[] p_chatChannelUserChangeCallback_3_, ChatUserInfo[] p_chatChannelUserChangeCallback_4_) {
/* 579 */       for (int i = 0; i < p_chatChannelUserChangeCallback_3_.length; i++) {
/* 580 */         int j = this.field_176044_d.indexOf(p_chatChannelUserChangeCallback_3_[i]);
/*     */         
/* 582 */         if (j >= 0) {
/* 583 */           this.field_176044_d.remove(j);
/*     */         }
/*     */       } 
/*     */       
/* 587 */       for (int k = 0; k < p_chatChannelUserChangeCallback_4_.length; k++) {
/* 588 */         int i1 = this.field_176044_d.indexOf(p_chatChannelUserChangeCallback_4_[k]);
/*     */         
/* 590 */         if (i1 >= 0) {
/* 591 */           this.field_176044_d.remove(i1);
/*     */         }
/*     */         
/* 594 */         this.field_176044_d.add(p_chatChannelUserChangeCallback_4_[k]);
/*     */       } 
/*     */       
/* 597 */       for (int l = 0; l < p_chatChannelUserChangeCallback_2_.length; l++) {
/* 598 */         this.field_176044_d.add(p_chatChannelUserChangeCallback_2_[l]);
/*     */       }
/*     */       
/*     */       try {
/* 602 */         if (ChatController.this.field_153003_a != null) {
/* 603 */           ChatController.this.field_153003_a.func_176018_a(this.field_176048_a, p_chatChannelUserChangeCallback_2_, p_chatChannelUserChangeCallback_3_, p_chatChannelUserChangeCallback_4_);
/*     */         }
/* 605 */       } catch (Exception exception) {
/* 606 */         ChatController.this.func_152995_h(exception.toString());
/*     */       } 
/*     */     }
/*     */     
/*     */     public void chatChannelRawMessageCallback(String p_chatChannelRawMessageCallback_1_, ChatRawMessage[] p_chatChannelRawMessageCallback_2_) {
/* 611 */       for (int i = 0; i < p_chatChannelRawMessageCallback_2_.length; i++) {
/* 612 */         this.field_176045_e.addLast(p_chatChannelRawMessageCallback_2_[i]);
/*     */       }
/*     */       
/*     */       try {
/* 616 */         if (ChatController.this.field_153003_a != null) {
/* 617 */           ChatController.this.field_153003_a.func_180605_a(this.field_176048_a, p_chatChannelRawMessageCallback_2_);
/*     */         }
/* 619 */       } catch (Exception exception) {
/* 620 */         ChatController.this.func_152995_h(exception.toString());
/*     */       } 
/*     */       
/* 623 */       while (this.field_176045_e.size() > ChatController.this.field_153015_m) {
/* 624 */         this.field_176045_e.removeFirst();
/*     */       }
/*     */     }
/*     */     
/*     */     public void chatChannelTokenizedMessageCallback(String p_chatChannelTokenizedMessageCallback_1_, ChatTokenizedMessage[] p_chatChannelTokenizedMessageCallback_2_) {
/* 629 */       for (int i = 0; i < p_chatChannelTokenizedMessageCallback_2_.length; i++) {
/* 630 */         this.field_176042_f.addLast(p_chatChannelTokenizedMessageCallback_2_[i]);
/*     */       }
/*     */       
/*     */       try {
/* 634 */         if (ChatController.this.field_153003_a != null) {
/* 635 */           ChatController.this.field_153003_a.func_176025_a(this.field_176048_a, p_chatChannelTokenizedMessageCallback_2_);
/*     */         }
/* 637 */       } catch (Exception exception) {
/* 638 */         ChatController.this.func_152995_h(exception.toString());
/*     */       } 
/*     */       
/* 641 */       while (this.field_176042_f.size() > ChatController.this.field_153015_m) {
/* 642 */         this.field_176042_f.removeFirst();
/*     */       }
/*     */     }
/*     */     
/*     */     public void chatClearCallback(String p_chatClearCallback_1_, String p_chatClearCallback_2_) {
/* 647 */       func_176032_a(p_chatClearCallback_2_);
/*     */     }
/*     */     
/*     */     public void chatBadgeDataDownloadCallback(String p_chatBadgeDataDownloadCallback_1_, ErrorCode p_chatBadgeDataDownloadCallback_2_) {
/* 651 */       if (ErrorCode.succeeded(p_chatBadgeDataDownloadCallback_2_))
/* 652 */         func_176039_i(); 
/*     */     }
/*     */   }
/*     */   
/*     */   public static interface ChatListener
/*     */   {
/*     */     void func_176023_d(ErrorCode param1ErrorCode);
/*     */     
/*     */     void func_176022_e(ErrorCode param1ErrorCode);
/*     */     
/*     */     void func_176021_d();
/*     */     
/*     */     void func_176024_e();
/*     */     
/*     */     void func_176017_a(ChatController.ChatState param1ChatState);
/*     */     
/*     */     void func_176025_a(String param1String, ChatTokenizedMessage[] param1ArrayOfChatTokenizedMessage);
/*     */     
/*     */     void func_180605_a(String param1String, ChatRawMessage[] param1ArrayOfChatRawMessage);
/*     */     
/*     */     void func_176018_a(String param1String, ChatUserInfo[] param1ArrayOfChatUserInfo1, ChatUserInfo[] param1ArrayOfChatUserInfo2, ChatUserInfo[] param1ArrayOfChatUserInfo3);
/*     */     
/*     */     void func_180606_a(String param1String);
/*     */     
/*     */     void func_180607_b(String param1String);
/*     */     
/*     */     void func_176019_a(String param1String1, String param1String2);
/*     */     
/*     */     void func_176016_c(String param1String);
/*     */     
/*     */     void func_176020_d(String param1String);
/*     */   }
/*     */   
/*     */   public enum ChatState {
/* 686 */     Uninitialized,
/* 687 */     Initializing,
/* 688 */     Initialized,
/* 689 */     ShuttingDown;
/*     */   }
/*     */   
/*     */   public enum EnumChannelState {
/* 693 */     Created,
/* 694 */     Connecting,
/* 695 */     Connected,
/* 696 */     Disconnecting,
/* 697 */     Disconnected;
/*     */   }
/*     */   
/*     */   public enum EnumEmoticonMode {
/* 701 */     None,
/* 702 */     Url,
/* 703 */     TextureAtlas;
/*     */   }
/*     */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\minecraft\client\stream\ChatController.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */