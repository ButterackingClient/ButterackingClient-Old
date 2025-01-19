/*     */ package net.minecraft.client.stream;
/*     */ 
/*     */ import com.google.common.collect.Lists;
/*     */ import java.util.Arrays;
/*     */ import java.util.List;
/*     */ import net.minecraft.crash.CrashReport;
/*     */ import net.minecraft.crash.CrashReportCategory;
/*     */ import net.minecraft.util.ReportedException;
/*     */ import net.minecraft.util.ThreadSafeBoundList;
/*     */ import org.apache.logging.log4j.LogManager;
/*     */ import org.apache.logging.log4j.Logger;
/*     */ import tv.twitch.AuthToken;
/*     */ import tv.twitch.Core;
/*     */ import tv.twitch.CoreAPI;
/*     */ import tv.twitch.ErrorCode;
/*     */ import tv.twitch.MessageLevel;
/*     */ import tv.twitch.StandardCoreAPI;
/*     */ import tv.twitch.broadcast.ArchivingState;
/*     */ import tv.twitch.broadcast.AudioDeviceType;
/*     */ import tv.twitch.broadcast.AudioParams;
/*     */ import tv.twitch.broadcast.ChannelInfo;
/*     */ import tv.twitch.broadcast.DesktopStreamAPI;
/*     */ import tv.twitch.broadcast.EncodingCpuUsage;
/*     */ import tv.twitch.broadcast.FrameBuffer;
/*     */ import tv.twitch.broadcast.GameInfo;
/*     */ import tv.twitch.broadcast.GameInfoList;
/*     */ import tv.twitch.broadcast.IStatCallbacks;
/*     */ import tv.twitch.broadcast.IStreamCallbacks;
/*     */ import tv.twitch.broadcast.IngestList;
/*     */ import tv.twitch.broadcast.IngestServer;
/*     */ import tv.twitch.broadcast.PixelFormat;
/*     */ import tv.twitch.broadcast.StartFlags;
/*     */ import tv.twitch.broadcast.StatType;
/*     */ import tv.twitch.broadcast.Stream;
/*     */ import tv.twitch.broadcast.StreamAPI;
/*     */ import tv.twitch.broadcast.StreamInfo;
/*     */ import tv.twitch.broadcast.StreamInfoForSetting;
/*     */ import tv.twitch.broadcast.UserInfo;
/*     */ import tv.twitch.broadcast.VideoParams;
/*     */ 
/*     */ public class BroadcastController {
/*  42 */   private static final Logger logger = LogManager.getLogger();
/*  43 */   protected final int field_152865_a = 30;
/*  44 */   protected final int field_152866_b = 3;
/*  45 */   private static final ThreadSafeBoundList<String> field_152862_C = new ThreadSafeBoundList(String.class, 50);
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  50 */   private String lastError = null;
/*  51 */   protected BroadcastListener broadcastListener = null;
/*  52 */   protected String field_152868_d = "";
/*  53 */   protected String field_152869_e = "";
/*  54 */   protected String field_152870_f = "";
/*     */ 
/*     */   
/*     */   protected boolean field_152871_g = true;
/*     */ 
/*     */   
/*  60 */   protected Core streamCore = null;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  65 */   protected Stream theStream = null;
/*  66 */   protected List<FrameBuffer> field_152874_j = Lists.newArrayList();
/*  67 */   protected List<FrameBuffer> field_152875_k = Lists.newArrayList();
/*     */   protected boolean field_152876_l = false;
/*     */   protected boolean field_152877_m = false;
/*     */   protected boolean field_152878_n = false;
/*  71 */   protected BroadcastState broadcastState = BroadcastState.Uninitialized;
/*  72 */   protected String field_152880_p = null;
/*  73 */   protected VideoParams videoParamaters = null;
/*  74 */   protected AudioParams audioParamaters = null;
/*  75 */   protected IngestList ingestList = new IngestList(new IngestServer[0]);
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  80 */   protected IngestServer ingestServ = null;
/*  81 */   protected AuthToken authenticationToken = new AuthToken();
/*  82 */   protected ChannelInfo channelInfo = new ChannelInfo();
/*  83 */   protected UserInfo userInfo = new UserInfo();
/*  84 */   protected StreamInfo streamInfo = new StreamInfo();
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  89 */   protected ArchivingState archivingState = new ArchivingState();
/*  90 */   protected long field_152890_z = 0L;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  95 */   protected IngestServerTester ingestServTester = null;
/*     */ 
/*     */   
/*     */   private ErrorCode errorCode;
/*     */ 
/*     */   
/* 101 */   protected IStreamCallbacks streamCallback = new IStreamCallbacks() {
/*     */       public void requestAuthTokenCallback(ErrorCode p_requestAuthTokenCallback_1_, AuthToken p_requestAuthTokenCallback_2_) {
/* 103 */         if (ErrorCode.succeeded(p_requestAuthTokenCallback_1_)) {
/* 104 */           BroadcastController.this.authenticationToken = p_requestAuthTokenCallback_2_;
/* 105 */           BroadcastController.this.func_152827_a(BroadcastController.BroadcastState.Authenticated);
/*     */         } else {
/* 107 */           BroadcastController.this.authenticationToken.data = "";
/* 108 */           BroadcastController.this.func_152827_a(BroadcastController.BroadcastState.Initialized);
/* 109 */           String s = ErrorCode.getString(p_requestAuthTokenCallback_1_);
/* 110 */           BroadcastController.this.logError(String.format("RequestAuthTokenDoneCallback got failure: %s", new Object[] { s }));
/*     */         } 
/*     */         
/*     */         try {
/* 114 */           if (BroadcastController.this.broadcastListener != null) {
/* 115 */             BroadcastController.this.broadcastListener.func_152900_a(p_requestAuthTokenCallback_1_, p_requestAuthTokenCallback_2_);
/*     */           }
/* 117 */         } catch (Exception exception) {
/* 118 */           BroadcastController.this.logError(exception.toString());
/*     */         } 
/*     */       }
/*     */       
/*     */       public void loginCallback(ErrorCode p_loginCallback_1_, ChannelInfo p_loginCallback_2_) {
/* 123 */         if (ErrorCode.succeeded(p_loginCallback_1_)) {
/* 124 */           BroadcastController.this.channelInfo = p_loginCallback_2_;
/* 125 */           BroadcastController.this.func_152827_a(BroadcastController.BroadcastState.LoggedIn);
/* 126 */           BroadcastController.this.field_152877_m = true;
/*     */         } else {
/* 128 */           BroadcastController.this.func_152827_a(BroadcastController.BroadcastState.Initialized);
/* 129 */           BroadcastController.this.field_152877_m = false;
/* 130 */           String s = ErrorCode.getString(p_loginCallback_1_);
/* 131 */           BroadcastController.this.logError(String.format("LoginCallback got failure: %s", new Object[] { s }));
/*     */         } 
/*     */         
/*     */         try {
/* 135 */           if (BroadcastController.this.broadcastListener != null) {
/* 136 */             BroadcastController.this.broadcastListener.func_152897_a(p_loginCallback_1_);
/*     */           }
/* 138 */         } catch (Exception exception) {
/* 139 */           BroadcastController.this.logError(exception.toString());
/*     */         } 
/*     */       }
/*     */       
/*     */       public void getIngestServersCallback(ErrorCode p_getIngestServersCallback_1_, IngestList p_getIngestServersCallback_2_) {
/* 144 */         if (ErrorCode.succeeded(p_getIngestServersCallback_1_)) {
/* 145 */           BroadcastController.this.ingestList = p_getIngestServersCallback_2_;
/* 146 */           BroadcastController.this.ingestServ = BroadcastController.this.ingestList.getDefaultServer();
/* 147 */           BroadcastController.this.func_152827_a(BroadcastController.BroadcastState.ReceivedIngestServers);
/*     */           
/*     */           try {
/* 150 */             if (BroadcastController.this.broadcastListener != null) {
/* 151 */               BroadcastController.this.broadcastListener.func_152896_a(p_getIngestServersCallback_2_);
/*     */             }
/* 153 */           } catch (Exception exception) {
/* 154 */             BroadcastController.this.logError(exception.toString());
/*     */           } 
/*     */         } else {
/* 157 */           String s = ErrorCode.getString(p_getIngestServersCallback_1_);
/* 158 */           BroadcastController.this.logError(String.format("IngestListCallback got failure: %s", new Object[] { s }));
/* 159 */           BroadcastController.this.func_152827_a(BroadcastController.BroadcastState.LoggingIn);
/*     */         } 
/*     */       }
/*     */       
/*     */       public void getUserInfoCallback(ErrorCode p_getUserInfoCallback_1_, UserInfo p_getUserInfoCallback_2_) {
/* 164 */         BroadcastController.this.userInfo = p_getUserInfoCallback_2_;
/*     */         
/* 166 */         if (ErrorCode.failed(p_getUserInfoCallback_1_)) {
/* 167 */           String s = ErrorCode.getString(p_getUserInfoCallback_1_);
/* 168 */           BroadcastController.this.logError(String.format("UserInfoDoneCallback got failure: %s", new Object[] { s }));
/*     */         } 
/*     */       }
/*     */       
/*     */       public void getStreamInfoCallback(ErrorCode p_getStreamInfoCallback_1_, StreamInfo p_getStreamInfoCallback_2_) {
/* 173 */         if (ErrorCode.succeeded(p_getStreamInfoCallback_1_)) {
/* 174 */           BroadcastController.this.streamInfo = p_getStreamInfoCallback_2_;
/*     */           
/*     */           try {
/* 177 */             if (BroadcastController.this.broadcastListener != null) {
/* 178 */               BroadcastController.this.broadcastListener.func_152894_a(p_getStreamInfoCallback_2_);
/*     */             }
/* 180 */           } catch (Exception exception) {
/* 181 */             BroadcastController.this.logError(exception.toString());
/*     */           } 
/*     */         } else {
/* 184 */           String s = ErrorCode.getString(p_getStreamInfoCallback_1_);
/* 185 */           BroadcastController.this.logWarning(String.format("StreamInfoDoneCallback got failure: %s", new Object[] { s }));
/*     */         } 
/*     */       }
/*     */       
/*     */       public void getArchivingStateCallback(ErrorCode p_getArchivingStateCallback_1_, ArchivingState p_getArchivingStateCallback_2_) {
/* 190 */         BroadcastController.this.archivingState = p_getArchivingStateCallback_2_;
/*     */         
/* 192 */         if (ErrorCode.failed(p_getArchivingStateCallback_1_));
/*     */       }
/*     */ 
/*     */ 
/*     */       
/*     */       public void runCommercialCallback(ErrorCode p_runCommercialCallback_1_) {
/* 198 */         if (ErrorCode.failed(p_runCommercialCallback_1_)) {
/* 199 */           String s = ErrorCode.getString(p_runCommercialCallback_1_);
/* 200 */           BroadcastController.this.logWarning(String.format("RunCommercialCallback got failure: %s", new Object[] { s }));
/*     */         } 
/*     */       }
/*     */       
/*     */       public void setStreamInfoCallback(ErrorCode p_setStreamInfoCallback_1_) {
/* 205 */         if (ErrorCode.failed(p_setStreamInfoCallback_1_)) {
/* 206 */           String s = ErrorCode.getString(p_setStreamInfoCallback_1_);
/* 207 */           BroadcastController.this.logWarning(String.format("SetStreamInfoCallback got failure: %s", new Object[] { s }));
/*     */         } 
/*     */       }
/*     */       
/*     */       public void getGameNameListCallback(ErrorCode p_getGameNameListCallback_1_, GameInfoList p_getGameNameListCallback_2_) {
/* 212 */         if (ErrorCode.failed(p_getGameNameListCallback_1_)) {
/* 213 */           String s = ErrorCode.getString(p_getGameNameListCallback_1_);
/* 214 */           BroadcastController.this.logError(String.format("GameNameListCallback got failure: %s", new Object[] { s }));
/*     */         } 
/*     */         
/*     */         try {
/* 218 */           if (BroadcastController.this.broadcastListener != null) {
/* 219 */             BroadcastController.this.broadcastListener.func_152898_a(p_getGameNameListCallback_1_, (p_getGameNameListCallback_2_ == null) ? new GameInfo[0] : p_getGameNameListCallback_2_.list);
/*     */           }
/* 221 */         } catch (Exception exception) {
/* 222 */           BroadcastController.this.logError(exception.toString());
/*     */         } 
/*     */       }
/*     */       
/*     */       public void bufferUnlockCallback(long p_bufferUnlockCallback_1_) {
/* 227 */         FrameBuffer framebuffer = FrameBuffer.lookupBuffer(p_bufferUnlockCallback_1_);
/* 228 */         BroadcastController.this.field_152875_k.add(framebuffer);
/*     */       }
/*     */       
/*     */       public void startCallback(ErrorCode p_startCallback_1_) {
/* 232 */         if (ErrorCode.succeeded(p_startCallback_1_)) {
/*     */           try {
/* 234 */             if (BroadcastController.this.broadcastListener != null) {
/* 235 */               BroadcastController.this.broadcastListener.func_152899_b();
/*     */             }
/* 237 */           } catch (Exception exception1) {
/* 238 */             BroadcastController.this.logError(exception1.toString());
/*     */           } 
/*     */           
/* 241 */           BroadcastController.this.func_152827_a(BroadcastController.BroadcastState.Broadcasting);
/*     */         } else {
/* 243 */           BroadcastController.this.videoParamaters = null;
/* 244 */           BroadcastController.this.audioParamaters = null;
/* 245 */           BroadcastController.this.func_152827_a(BroadcastController.BroadcastState.ReadyToBroadcast);
/*     */           
/*     */           try {
/* 248 */             if (BroadcastController.this.broadcastListener != null) {
/* 249 */               BroadcastController.this.broadcastListener.func_152892_c(p_startCallback_1_);
/*     */             }
/* 251 */           } catch (Exception exception) {
/* 252 */             BroadcastController.this.logError(exception.toString());
/*     */           } 
/*     */           
/* 255 */           String s = ErrorCode.getString(p_startCallback_1_);
/* 256 */           BroadcastController.this.logError(String.format("startCallback got failure: %s", new Object[] { s }));
/*     */         } 
/*     */       }
/*     */       
/*     */       public void stopCallback(ErrorCode p_stopCallback_1_) {
/* 261 */         if (ErrorCode.succeeded(p_stopCallback_1_)) {
/* 262 */           BroadcastController.this.videoParamaters = null;
/* 263 */           BroadcastController.this.audioParamaters = null;
/* 264 */           BroadcastController.this.func_152831_M();
/*     */           
/*     */           try {
/* 267 */             if (BroadcastController.this.broadcastListener != null) {
/* 268 */               BroadcastController.this.broadcastListener.func_152901_c();
/*     */             }
/* 270 */           } catch (Exception exception) {
/* 271 */             BroadcastController.this.logError(exception.toString());
/*     */           } 
/*     */           
/* 274 */           if (BroadcastController.this.field_152877_m) {
/* 275 */             BroadcastController.this.func_152827_a(BroadcastController.BroadcastState.ReadyToBroadcast);
/*     */           } else {
/* 277 */             BroadcastController.this.func_152827_a(BroadcastController.BroadcastState.Initialized);
/*     */           } 
/*     */         } else {
/* 280 */           BroadcastController.this.func_152827_a(BroadcastController.BroadcastState.ReadyToBroadcast);
/* 281 */           String s = ErrorCode.getString(p_stopCallback_1_);
/* 282 */           BroadcastController.this.logError(String.format("stopCallback got failure: %s", new Object[] { s }));
/*     */         } 
/*     */       }
/*     */       
/*     */       public void sendActionMetaDataCallback(ErrorCode p_sendActionMetaDataCallback_1_) {
/* 287 */         if (ErrorCode.failed(p_sendActionMetaDataCallback_1_)) {
/* 288 */           String s = ErrorCode.getString(p_sendActionMetaDataCallback_1_);
/* 289 */           BroadcastController.this.logError(String.format("sendActionMetaDataCallback got failure: %s", new Object[] { s }));
/*     */         } 
/*     */       }
/*     */       
/*     */       public void sendStartSpanMetaDataCallback(ErrorCode p_sendStartSpanMetaDataCallback_1_) {
/* 294 */         if (ErrorCode.failed(p_sendStartSpanMetaDataCallback_1_)) {
/* 295 */           String s = ErrorCode.getString(p_sendStartSpanMetaDataCallback_1_);
/* 296 */           BroadcastController.this.logError(String.format("sendStartSpanMetaDataCallback got failure: %s", new Object[] { s }));
/*     */         } 
/*     */       }
/*     */       
/*     */       public void sendEndSpanMetaDataCallback(ErrorCode p_sendEndSpanMetaDataCallback_1_) {
/* 301 */         if (ErrorCode.failed(p_sendEndSpanMetaDataCallback_1_)) {
/* 302 */           String s = ErrorCode.getString(p_sendEndSpanMetaDataCallback_1_);
/* 303 */           BroadcastController.this.logError(String.format("sendEndSpanMetaDataCallback got failure: %s", new Object[] { s }));
/*     */         } 
/*     */       }
/*     */     };
/* 307 */   protected IStatCallbacks field_177949_C = new IStatCallbacks()
/*     */     {
/*     */       public void statCallback(StatType p_statCallback_1_, long p_statCallback_2_) {}
/*     */     };
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setBroadcastListener(BroadcastListener broadcastListenerIn) {
/* 318 */     this.broadcastListener = broadcastListenerIn;
/*     */   }
/*     */   
/*     */   public boolean func_152858_b() {
/* 322 */     return this.field_152876_l;
/*     */   }
/*     */   
/*     */   public void func_152842_a(String p_152842_1_) {
/* 326 */     this.field_152868_d = p_152842_1_;
/*     */   }
/*     */   
/*     */   public StreamInfo getStreamInfo() {
/* 330 */     return this.streamInfo;
/*     */   }
/*     */   
/*     */   public ChannelInfo getChannelInfo() {
/* 334 */     return this.channelInfo;
/*     */   }
/*     */   
/*     */   public boolean isBroadcasting() {
/* 338 */     return !(this.broadcastState != BroadcastState.Broadcasting && this.broadcastState != BroadcastState.Paused);
/*     */   }
/*     */   
/*     */   public boolean isReadyToBroadcast() {
/* 342 */     return (this.broadcastState == BroadcastState.ReadyToBroadcast);
/*     */   }
/*     */   
/*     */   public boolean isIngestTesting() {
/* 346 */     return (this.broadcastState == BroadcastState.IngestTesting);
/*     */   }
/*     */   
/*     */   public boolean isBroadcastPaused() {
/* 350 */     return (this.broadcastState == BroadcastState.Paused);
/*     */   }
/*     */   
/*     */   public boolean func_152849_q() {
/* 354 */     return this.field_152877_m;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public IngestServer getIngestServer() {
/* 361 */     return this.ingestServ;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setIngestServer(IngestServer ingestServerSet) {
/* 370 */     this.ingestServ = ingestServerSet;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public IngestList getIngestList() {
/* 377 */     return this.ingestList;
/*     */   }
/*     */   
/*     */   public void setRecordingDeviceVolume(float volume) {
/* 381 */     this.theStream.setVolume(AudioDeviceType.TTV_RECORDER_DEVICE, volume);
/*     */   }
/*     */   
/*     */   public void setPlaybackDeviceVolume(float volume) {
/* 385 */     this.theStream.setVolume(AudioDeviceType.TTV_PLAYBACK_DEVICE, volume);
/*     */   }
/*     */   
/*     */   public IngestServerTester isReady() {
/* 389 */     return this.ingestServTester;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public long getStreamTime() {
/* 396 */     return this.theStream.getStreamTime();
/*     */   }
/*     */   
/*     */   protected boolean func_152848_y() {
/* 400 */     return true;
/*     */   }
/*     */   
/*     */   public ErrorCode getErrorCode() {
/* 404 */     return this.errorCode;
/*     */   }
/*     */   
/*     */   public BroadcastController() {
/* 408 */     this.streamCore = Core.getInstance();
/*     */     
/* 410 */     if (Core.getInstance() == null) {
/* 411 */       this.streamCore = new Core((CoreAPI)new StandardCoreAPI());
/*     */     }
/*     */     
/* 414 */     this.theStream = new Stream((StreamAPI)new DesktopStreamAPI());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected PixelFormat getPixelFormat() {
/* 421 */     return PixelFormat.TTV_PF_RGBA;
/*     */   }
/*     */   
/*     */   public boolean func_152817_A() {
/* 425 */     if (this.field_152876_l) {
/* 426 */       return false;
/*     */     }
/* 428 */     this.theStream.setStreamCallbacks(this.streamCallback);
/* 429 */     ErrorCode errorcode = this.streamCore.initialize(this.field_152868_d, System.getProperty("java.library.path"));
/*     */     
/* 431 */     if (!func_152853_a(errorcode)) {
/* 432 */       this.theStream.setStreamCallbacks(null);
/* 433 */       this.errorCode = errorcode;
/* 434 */       return false;
/*     */     } 
/* 436 */     errorcode = this.streamCore.setTraceLevel(MessageLevel.TTV_ML_ERROR);
/*     */     
/* 438 */     if (!func_152853_a(errorcode)) {
/* 439 */       this.theStream.setStreamCallbacks(null);
/* 440 */       this.streamCore.shutdown();
/* 441 */       this.errorCode = errorcode;
/* 442 */       return false;
/* 443 */     }  if (ErrorCode.succeeded(errorcode)) {
/* 444 */       this.field_152876_l = true;
/* 445 */       func_152827_a(BroadcastState.Initialized);
/* 446 */       return true;
/*     */     } 
/* 448 */     this.errorCode = errorcode;
/* 449 */     this.streamCore.shutdown();
/* 450 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean func_152851_B() {
/* 457 */     if (!this.field_152876_l)
/* 458 */       return true; 
/* 459 */     if (isIngestTesting()) {
/* 460 */       return false;
/*     */     }
/* 462 */     this.field_152878_n = true;
/* 463 */     func_152845_C();
/* 464 */     this.theStream.setStreamCallbacks(null);
/* 465 */     this.theStream.setStatCallbacks(null);
/* 466 */     ErrorCode errorcode = this.streamCore.shutdown();
/* 467 */     func_152853_a(errorcode);
/* 468 */     this.field_152876_l = false;
/* 469 */     this.field_152878_n = false;
/* 470 */     func_152827_a(BroadcastState.Uninitialized);
/* 471 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   public void statCallback() {
/* 476 */     if (this.broadcastState != BroadcastState.Uninitialized) {
/* 477 */       if (this.ingestServTester != null) {
/* 478 */         this.ingestServTester.func_153039_l();
/*     */       }
/*     */       
/* 481 */       for (; this.ingestServTester != null; func_152821_H()) {
/*     */         try {
/* 483 */           Thread.sleep(200L);
/* 484 */         } catch (Exception exception) {
/* 485 */           logError(exception.toString());
/*     */         } 
/*     */       } 
/*     */       
/* 489 */       func_152851_B();
/*     */     } 
/*     */   }
/*     */   
/*     */   public boolean func_152818_a(String p_152818_1_, AuthToken p_152818_2_) {
/* 494 */     if (isIngestTesting()) {
/* 495 */       return false;
/*     */     }
/* 497 */     func_152845_C();
/*     */     
/* 499 */     if (p_152818_1_ != null && !p_152818_1_.isEmpty()) {
/* 500 */       if (p_152818_2_ != null && p_152818_2_.data != null && !p_152818_2_.data.isEmpty()) {
/* 501 */         this.field_152880_p = p_152818_1_;
/* 502 */         this.authenticationToken = p_152818_2_;
/*     */         
/* 504 */         if (func_152858_b()) {
/* 505 */           func_152827_a(BroadcastState.Authenticated);
/*     */         }
/*     */         
/* 508 */         return true;
/*     */       } 
/* 510 */       logError("Auth token must be valid");
/* 511 */       return false;
/*     */     } 
/*     */     
/* 514 */     logError("Username must be valid");
/* 515 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean func_152845_C() {
/* 521 */     if (isIngestTesting()) {
/* 522 */       return false;
/*     */     }
/* 524 */     if (isBroadcasting()) {
/* 525 */       this.theStream.stop(false);
/*     */     }
/*     */     
/* 528 */     this.field_152880_p = "";
/* 529 */     this.authenticationToken = new AuthToken();
/*     */     
/* 531 */     if (!this.field_152877_m) {
/* 532 */       return false;
/*     */     }
/* 534 */     this.field_152877_m = false;
/*     */     
/* 536 */     if (!this.field_152878_n) {
/*     */       try {
/* 538 */         if (this.broadcastListener != null) {
/* 539 */           this.broadcastListener.func_152895_a();
/*     */         }
/* 541 */       } catch (Exception exception) {
/* 542 */         logError(exception.toString());
/*     */       } 
/*     */     }
/*     */     
/* 546 */     func_152827_a(BroadcastState.Initialized);
/* 547 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean func_152828_a(String p_152828_1_, String p_152828_2_, String p_152828_3_) {
/* 553 */     if (!this.field_152877_m) {
/* 554 */       return false;
/*     */     }
/* 556 */     if (p_152828_1_ == null || p_152828_1_.equals("")) {
/* 557 */       p_152828_1_ = this.field_152880_p;
/*     */     }
/*     */     
/* 560 */     if (p_152828_2_ == null) {
/* 561 */       p_152828_2_ = "";
/*     */     }
/*     */     
/* 564 */     if (p_152828_3_ == null) {
/* 565 */       p_152828_3_ = "";
/*     */     }
/*     */     
/* 568 */     StreamInfoForSetting streaminfoforsetting = new StreamInfoForSetting();
/* 569 */     streaminfoforsetting.streamTitle = p_152828_3_;
/* 570 */     streaminfoforsetting.gameName = p_152828_2_;
/* 571 */     ErrorCode errorcode = this.theStream.setStreamInfo(this.authenticationToken, p_152828_1_, streaminfoforsetting);
/* 572 */     func_152853_a(errorcode);
/* 573 */     return ErrorCode.succeeded(errorcode);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean requestCommercial() {
/* 578 */     if (!isBroadcasting()) {
/* 579 */       return false;
/*     */     }
/* 581 */     ErrorCode errorcode = this.theStream.runCommercial(this.authenticationToken);
/* 582 */     func_152853_a(errorcode);
/* 583 */     return ErrorCode.succeeded(errorcode);
/*     */   }
/*     */ 
/*     */   
/*     */   public VideoParams func_152834_a(int maxKbps, int p_152834_2_, float p_152834_3_, float p_152834_4_) {
/* 588 */     int[] aint = this.theStream.getMaxResolution(maxKbps, p_152834_2_, p_152834_3_, p_152834_4_);
/* 589 */     VideoParams videoparams = new VideoParams();
/* 590 */     videoparams.maxKbps = maxKbps;
/* 591 */     videoparams.encodingCpuUsage = EncodingCpuUsage.TTV_ECU_HIGH;
/* 592 */     videoparams.pixelFormat = getPixelFormat();
/* 593 */     videoparams.targetFps = p_152834_2_;
/* 594 */     videoparams.outputWidth = aint[0];
/* 595 */     videoparams.outputHeight = aint[1];
/* 596 */     videoparams.disableAdaptiveBitrate = false;
/* 597 */     videoparams.verticalFlip = false;
/* 598 */     return videoparams;
/*     */   }
/*     */   
/*     */   public boolean func_152836_a(VideoParams p_152836_1_) {
/* 602 */     if (p_152836_1_ != null && isReadyToBroadcast()) {
/* 603 */       this.videoParamaters = p_152836_1_.clone();
/* 604 */       this.audioParamaters = new AudioParams();
/* 605 */       this.audioParamaters.audioEnabled = (this.field_152871_g && func_152848_y());
/* 606 */       this.audioParamaters.enableMicCapture = this.audioParamaters.audioEnabled;
/* 607 */       this.audioParamaters.enablePlaybackCapture = this.audioParamaters.audioEnabled;
/* 608 */       this.audioParamaters.enablePassthroughAudio = false;
/*     */       
/* 610 */       if (!func_152823_L()) {
/* 611 */         this.videoParamaters = null;
/* 612 */         this.audioParamaters = null;
/* 613 */         return false;
/*     */       } 
/* 615 */       ErrorCode errorcode = this.theStream.start(p_152836_1_, this.audioParamaters, this.ingestServ, StartFlags.None, true);
/*     */       
/* 617 */       if (ErrorCode.failed(errorcode)) {
/* 618 */         func_152831_M();
/* 619 */         String s = ErrorCode.getString(errorcode);
/* 620 */         logError(String.format("Error while starting to broadcast: %s", new Object[] { s }));
/* 621 */         this.videoParamaters = null;
/* 622 */         this.audioParamaters = null;
/* 623 */         return false;
/*     */       } 
/* 625 */       func_152827_a(BroadcastState.Starting);
/* 626 */       return true;
/*     */     } 
/*     */ 
/*     */     
/* 630 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean stopBroadcasting() {
/* 635 */     if (!isBroadcasting()) {
/* 636 */       return false;
/*     */     }
/* 638 */     ErrorCode errorcode = this.theStream.stop(true);
/*     */     
/* 640 */     if (ErrorCode.failed(errorcode)) {
/* 641 */       String s = ErrorCode.getString(errorcode);
/* 642 */       logError(String.format("Error while stopping the broadcast: %s", new Object[] { s }));
/* 643 */       return false;
/*     */     } 
/* 645 */     func_152827_a(BroadcastState.Stopping);
/* 646 */     return ErrorCode.succeeded(errorcode);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean func_152847_F() {
/* 652 */     if (!isBroadcasting()) {
/* 653 */       return false;
/*     */     }
/* 655 */     ErrorCode errorcode = this.theStream.pauseVideo();
/*     */     
/* 657 */     if (ErrorCode.failed(errorcode)) {
/* 658 */       stopBroadcasting();
/* 659 */       String s = ErrorCode.getString(errorcode);
/* 660 */       logError(String.format("Error pausing stream: %s\n", new Object[] { s }));
/*     */     } else {
/* 662 */       func_152827_a(BroadcastState.Paused);
/*     */     } 
/*     */     
/* 665 */     return ErrorCode.succeeded(errorcode);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean func_152854_G() {
/* 670 */     if (!isBroadcastPaused()) {
/* 671 */       return false;
/*     */     }
/* 673 */     func_152827_a(BroadcastState.Broadcasting);
/* 674 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean func_152840_a(String p_152840_1_, long p_152840_2_, String p_152840_4_, String p_152840_5_) {
/* 679 */     ErrorCode errorcode = this.theStream.sendActionMetaData(this.authenticationToken, p_152840_1_, p_152840_2_, p_152840_4_, p_152840_5_);
/*     */     
/* 681 */     if (ErrorCode.failed(errorcode)) {
/* 682 */       String s = ErrorCode.getString(errorcode);
/* 683 */       logError(String.format("Error while sending meta data: %s\n", new Object[] { s }));
/* 684 */       return false;
/*     */     } 
/* 686 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   public long func_177946_b(String p_177946_1_, long p_177946_2_, String p_177946_4_, String p_177946_5_) {
/* 691 */     long i = this.theStream.sendStartSpanMetaData(this.authenticationToken, p_177946_1_, p_177946_2_, p_177946_4_, p_177946_5_);
/*     */     
/* 693 */     if (i == -1L) {
/* 694 */       logError(String.format("Error in SendStartSpanMetaData\n", new Object[0]));
/*     */     }
/*     */     
/* 697 */     return i;
/*     */   }
/*     */   
/*     */   public boolean func_177947_a(String p_177947_1_, long p_177947_2_, long p_177947_4_, String p_177947_6_, String p_177947_7_) {
/* 701 */     if (p_177947_4_ == -1L) {
/* 702 */       logError(String.format("Invalid sequence id: %d\n", new Object[] { Long.valueOf(p_177947_4_) }));
/* 703 */       return false;
/*     */     } 
/* 705 */     ErrorCode errorcode = this.theStream.sendEndSpanMetaData(this.authenticationToken, p_177947_1_, p_177947_2_, p_177947_4_, p_177947_6_, p_177947_7_);
/*     */     
/* 707 */     if (ErrorCode.failed(errorcode)) {
/* 708 */       String s = ErrorCode.getString(errorcode);
/* 709 */       logError(String.format("Error in SendStopSpanMetaData: %s\n", new Object[] { s }));
/* 710 */       return false;
/*     */     } 
/* 712 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected void func_152827_a(BroadcastState p_152827_1_) {
/* 718 */     if (p_152827_1_ != this.broadcastState) {
/* 719 */       this.broadcastState = p_152827_1_;
/*     */       
/*     */       try {
/* 722 */         if (this.broadcastListener != null) {
/* 723 */           this.broadcastListener.func_152891_a(p_152827_1_);
/*     */         }
/* 725 */       } catch (Exception exception) {
/* 726 */         logError(exception.toString());
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   public void func_152821_H() {
/* 732 */     if (this.theStream != null && this.field_152876_l) {
/* 733 */       ErrorCode errorcode = this.theStream.pollTasks();
/* 734 */       func_152853_a(errorcode);
/*     */       
/* 736 */       if (isIngestTesting()) {
/* 737 */         this.ingestServTester.func_153041_j();
/*     */         
/* 739 */         if (this.ingestServTester.func_153032_e()) {
/* 740 */           this.ingestServTester = null;
/* 741 */           func_152827_a(BroadcastState.ReadyToBroadcast);
/*     */         } 
/*     */       } 
/*     */       
/* 745 */       switch (this.broadcastState) {
/*     */         case null:
/* 747 */           func_152827_a(BroadcastState.LoggingIn);
/* 748 */           errorcode = this.theStream.login(this.authenticationToken);
/*     */           
/* 750 */           if (ErrorCode.failed(errorcode)) {
/* 751 */             String s3 = ErrorCode.getString(errorcode);
/* 752 */             logError(String.format("Error in TTV_Login: %s\n", new Object[] { s3 }));
/*     */           } 
/*     */ 
/*     */ 
/*     */         
/*     */         case LoggedIn:
/* 758 */           func_152827_a(BroadcastState.FindingIngestServer);
/* 759 */           errorcode = this.theStream.getIngestServers(this.authenticationToken);
/*     */           
/* 761 */           if (ErrorCode.failed(errorcode)) {
/* 762 */             func_152827_a(BroadcastState.LoggedIn);
/* 763 */             String s2 = ErrorCode.getString(errorcode);
/* 764 */             logError(String.format("Error in TTV_GetIngestServers: %s\n", new Object[] { s2 }));
/*     */           } 
/*     */ 
/*     */ 
/*     */         
/*     */         case ReceivedIngestServers:
/* 770 */           func_152827_a(BroadcastState.ReadyToBroadcast);
/* 771 */           errorcode = this.theStream.getUserInfo(this.authenticationToken);
/*     */           
/* 773 */           if (ErrorCode.failed(errorcode)) {
/* 774 */             String s = ErrorCode.getString(errorcode);
/* 775 */             logError(String.format("Error in TTV_GetUserInfo: %s\n", new Object[] { s }));
/*     */           } 
/*     */           
/* 778 */           func_152835_I();
/* 779 */           errorcode = this.theStream.getArchivingState(this.authenticationToken);
/*     */           
/* 781 */           if (ErrorCode.failed(errorcode)) {
/* 782 */             String s1 = ErrorCode.getString(errorcode);
/* 783 */             logError(String.format("Error in TTV_GetArchivingState: %s\n", new Object[] { s1 }));
/*     */           } 
/*     */ 
/*     */ 
/*     */         
/*     */         default:
/*     */           return;
/*     */ 
/*     */         
/*     */         case Broadcasting:
/*     */         case Paused:
/*     */           break;
/*     */       } 
/*     */ 
/*     */       
/* 798 */       func_152835_I();
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   protected void func_152835_I() {
/* 804 */     long i = System.nanoTime();
/* 805 */     long j = (i - this.field_152890_z) / 1000000000L;
/*     */     
/* 807 */     if (j >= 30L) {
/* 808 */       this.field_152890_z = i;
/* 809 */       ErrorCode errorcode = this.theStream.getStreamInfo(this.authenticationToken, this.field_152880_p);
/*     */       
/* 811 */       if (ErrorCode.failed(errorcode)) {
/* 812 */         String s = ErrorCode.getString(errorcode);
/* 813 */         logError(String.format("Error in TTV_GetStreamInfo: %s", new Object[] { s }));
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   public IngestServerTester func_152838_J() {
/* 819 */     if (isReadyToBroadcast() && this.ingestList != null) {
/* 820 */       if (isIngestTesting()) {
/* 821 */         return null;
/*     */       }
/* 823 */       this.ingestServTester = new IngestServerTester(this.theStream, this.ingestList);
/* 824 */       this.ingestServTester.func_176004_j();
/* 825 */       func_152827_a(BroadcastState.IngestTesting);
/* 826 */       return this.ingestServTester;
/*     */     } 
/*     */     
/* 829 */     return null;
/*     */   }
/*     */ 
/*     */   
/*     */   protected boolean func_152823_L() {
/* 834 */     for (int i = 0; i < 3; i++) {
/* 835 */       FrameBuffer framebuffer = this.theStream.allocateFrameBuffer(this.videoParamaters.outputWidth * this.videoParamaters.outputHeight * 4);
/*     */       
/* 837 */       if (!framebuffer.getIsValid()) {
/* 838 */         logError(String.format("Error while allocating frame buffer", new Object[0]));
/* 839 */         return false;
/*     */       } 
/*     */       
/* 842 */       this.field_152874_j.add(framebuffer);
/* 843 */       this.field_152875_k.add(framebuffer);
/*     */     } 
/*     */     
/* 846 */     return true;
/*     */   }
/*     */   
/*     */   protected void func_152831_M() {
/* 850 */     for (int i = 0; i < this.field_152874_j.size(); i++) {
/* 851 */       FrameBuffer framebuffer = this.field_152874_j.get(i);
/* 852 */       framebuffer.free();
/*     */     } 
/*     */     
/* 855 */     this.field_152875_k.clear();
/* 856 */     this.field_152874_j.clear();
/*     */   }
/*     */   
/*     */   public FrameBuffer func_152822_N() {
/* 860 */     if (this.field_152875_k.size() == 0) {
/* 861 */       logError(String.format("Out of free buffers, this should never happen", new Object[0]));
/* 862 */       return null;
/*     */     } 
/* 864 */     FrameBuffer framebuffer = this.field_152875_k.get(this.field_152875_k.size() - 1);
/* 865 */     this.field_152875_k.remove(this.field_152875_k.size() - 1);
/* 866 */     return framebuffer;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void captureFramebuffer(FrameBuffer p_152846_1_) {
/*     */     try {
/* 875 */       this.theStream.captureFrameBuffer_ReadPixels(p_152846_1_);
/* 876 */     } catch (Throwable throwable) {
/* 877 */       CrashReport crashreport = CrashReport.makeCrashReport(throwable, "Trying to submit a frame to Twitch");
/* 878 */       CrashReportCategory crashreportcategory = crashreport.makeCategory("Broadcast State");
/* 879 */       crashreportcategory.addCrashSection("Last reported errors", Arrays.toString(field_152862_C.func_152756_c()));
/* 880 */       crashreportcategory.addCrashSection("Buffer", p_152846_1_);
/* 881 */       crashreportcategory.addCrashSection("Free buffer count", Integer.valueOf(this.field_152875_k.size()));
/* 882 */       crashreportcategory.addCrashSection("Capture buffer count", Integer.valueOf(this.field_152874_j.size()));
/* 883 */       throw new ReportedException(crashreport);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ErrorCode submitStreamFrame(FrameBuffer frame) {
/* 893 */     if (isBroadcastPaused()) {
/* 894 */       func_152854_G();
/* 895 */     } else if (!isBroadcasting()) {
/* 896 */       return ErrorCode.TTV_EC_STREAM_NOT_STARTED;
/*     */     } 
/*     */     
/* 899 */     ErrorCode errorcode = this.theStream.submitVideoFrame(frame);
/*     */     
/* 901 */     if (errorcode != ErrorCode.TTV_EC_SUCCESS) {
/* 902 */       String s = ErrorCode.getString(errorcode);
/*     */       
/* 904 */       if (ErrorCode.succeeded(errorcode)) {
/* 905 */         logWarning(String.format("Warning in SubmitTexturePointer: %s\n", new Object[] { s }));
/*     */       } else {
/* 907 */         logError(String.format("Error in SubmitTexturePointer: %s\n", new Object[] { s }));
/* 908 */         stopBroadcasting();
/*     */       } 
/*     */       
/* 911 */       if (this.broadcastListener != null) {
/* 912 */         this.broadcastListener.func_152893_b(errorcode);
/*     */       }
/*     */     } 
/*     */     
/* 916 */     return errorcode;
/*     */   }
/*     */   
/*     */   protected boolean func_152853_a(ErrorCode p_152853_1_) {
/* 920 */     if (ErrorCode.failed(p_152853_1_)) {
/* 921 */       logError(ErrorCode.getString(p_152853_1_));
/* 922 */       return false;
/*     */     } 
/* 924 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void logError(String error) {
/* 934 */     this.lastError = error;
/* 935 */     field_152862_C.func_152757_a("<Error> " + error);
/* 936 */     logger.error(TwitchStream.STREAM_MARKER, "[Broadcast controller] {}", new Object[] { error });
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void logWarning(String warning) {
/* 945 */     field_152862_C.func_152757_a("<Warning> " + warning);
/* 946 */     logger.warn(TwitchStream.STREAM_MARKER, "[Broadcast controller] {}", new Object[] { warning });
/*     */   }
/*     */   
/*     */   public static interface BroadcastListener {
/*     */     void func_152900_a(ErrorCode param1ErrorCode, AuthToken param1AuthToken);
/*     */     
/*     */     void func_152897_a(ErrorCode param1ErrorCode);
/*     */     
/*     */     void func_152898_a(ErrorCode param1ErrorCode, GameInfo[] param1ArrayOfGameInfo);
/*     */     
/*     */     void func_152891_a(BroadcastController.BroadcastState param1BroadcastState);
/*     */     
/*     */     void func_152895_a();
/*     */     
/*     */     void func_152894_a(StreamInfo param1StreamInfo);
/*     */     
/*     */     void func_152896_a(IngestList param1IngestList);
/*     */     
/*     */     void func_152893_b(ErrorCode param1ErrorCode);
/*     */     
/*     */     void func_152899_b();
/*     */     
/*     */     void func_152901_c();
/*     */     
/*     */     void func_152892_c(ErrorCode param1ErrorCode);
/*     */   }
/*     */   
/*     */   public enum BroadcastState {
/* 974 */     Uninitialized,
/* 975 */     Initialized,
/* 976 */     Authenticating,
/* 977 */     Authenticated,
/* 978 */     LoggingIn,
/* 979 */     LoggedIn,
/* 980 */     FindingIngestServer,
/* 981 */     ReceivedIngestServers,
/* 982 */     ReadyToBroadcast,
/* 983 */     Starting,
/* 984 */     Broadcasting,
/* 985 */     Stopping,
/* 986 */     Paused,
/* 987 */     IngestTesting;
/*     */   }
/*     */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\minecraft\client\stream\BroadcastController.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */