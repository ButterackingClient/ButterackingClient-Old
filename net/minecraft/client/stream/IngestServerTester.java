/*     */ package net.minecraft.client.stream;
/*     */ 
/*     */ import com.google.common.collect.Lists;
/*     */ import java.util.List;
/*     */ import tv.twitch.AuthToken;
/*     */ import tv.twitch.ErrorCode;
/*     */ import tv.twitch.broadcast.ArchivingState;
/*     */ import tv.twitch.broadcast.AudioParams;
/*     */ import tv.twitch.broadcast.ChannelInfo;
/*     */ import tv.twitch.broadcast.EncodingCpuUsage;
/*     */ import tv.twitch.broadcast.FrameBuffer;
/*     */ import tv.twitch.broadcast.GameInfoList;
/*     */ import tv.twitch.broadcast.IStatCallbacks;
/*     */ import tv.twitch.broadcast.IStreamCallbacks;
/*     */ import tv.twitch.broadcast.IngestList;
/*     */ import tv.twitch.broadcast.IngestServer;
/*     */ import tv.twitch.broadcast.PixelFormat;
/*     */ import tv.twitch.broadcast.RTMPState;
/*     */ import tv.twitch.broadcast.StartFlags;
/*     */ import tv.twitch.broadcast.StatType;
/*     */ import tv.twitch.broadcast.Stream;
/*     */ import tv.twitch.broadcast.StreamInfo;
/*     */ import tv.twitch.broadcast.UserInfo;
/*     */ import tv.twitch.broadcast.VideoParams;
/*     */ 
/*     */ 
/*     */ public class IngestServerTester
/*     */ {
/*  29 */   protected IngestTestListener field_153044_b = null;
/*  30 */   protected Stream field_153045_c = null;
/*  31 */   protected IngestList field_153046_d = null;
/*  32 */   protected IngestTestState field_153047_e = IngestTestState.Uninitalized;
/*  33 */   protected long field_153048_f = 8000L;
/*  34 */   protected long field_153049_g = 2000L;
/*  35 */   protected long field_153050_h = 0L;
/*  36 */   protected RTMPState field_153051_i = RTMPState.Invalid;
/*  37 */   protected VideoParams field_153052_j = null;
/*  38 */   protected AudioParams audioParameters = null;
/*  39 */   protected long field_153054_l = 0L;
/*  40 */   protected List<FrameBuffer> field_153055_m = null;
/*     */   protected boolean field_153056_n = false;
/*  42 */   protected IStreamCallbacks field_153057_o = null;
/*  43 */   protected IStatCallbacks field_153058_p = null;
/*  44 */   protected IngestServer field_153059_q = null;
/*     */   protected boolean field_153060_r = false;
/*     */   protected boolean field_153061_s = false;
/*  47 */   protected int field_153062_t = -1;
/*  48 */   protected int field_153063_u = 0;
/*  49 */   protected long field_153064_v = 0L;
/*  50 */   protected float field_153065_w = 0.0F;
/*  51 */   protected float field_153066_x = 0.0F;
/*     */   
/*     */   protected boolean field_176009_x = false;
/*     */   
/*  55 */   protected IStreamCallbacks field_176005_A = new IStreamCallbacks()
/*     */     {
/*     */       public void requestAuthTokenCallback(ErrorCode p_requestAuthTokenCallback_1_, AuthToken p_requestAuthTokenCallback_2_) {}
/*     */ 
/*     */       
/*     */       public void loginCallback(ErrorCode p_loginCallback_1_, ChannelInfo p_loginCallback_2_) {}
/*     */ 
/*     */       
/*     */       public void getIngestServersCallback(ErrorCode p_getIngestServersCallback_1_, IngestList p_getIngestServersCallback_2_) {}
/*     */ 
/*     */       
/*     */       public void getUserInfoCallback(ErrorCode p_getUserInfoCallback_1_, UserInfo p_getUserInfoCallback_2_) {}
/*     */ 
/*     */       
/*     */       public void getStreamInfoCallback(ErrorCode p_getStreamInfoCallback_1_, StreamInfo p_getStreamInfoCallback_2_) {}
/*     */ 
/*     */       
/*     */       public void getArchivingStateCallback(ErrorCode p_getArchivingStateCallback_1_, ArchivingState p_getArchivingStateCallback_2_) {}
/*     */ 
/*     */       
/*     */       public void runCommercialCallback(ErrorCode p_runCommercialCallback_1_) {}
/*     */ 
/*     */       
/*     */       public void setStreamInfoCallback(ErrorCode p_setStreamInfoCallback_1_) {}
/*     */ 
/*     */       
/*     */       public void getGameNameListCallback(ErrorCode p_getGameNameListCallback_1_, GameInfoList p_getGameNameListCallback_2_) {}
/*     */ 
/*     */       
/*     */       public void bufferUnlockCallback(long p_bufferUnlockCallback_1_) {}
/*     */       
/*     */       public void startCallback(ErrorCode p_startCallback_1_) {
/*  87 */         IngestServerTester.this.field_176008_y = false;
/*     */         
/*  89 */         if (ErrorCode.succeeded(p_startCallback_1_)) {
/*  90 */           IngestServerTester.this.field_176009_x = true;
/*  91 */           IngestServerTester.this.field_153054_l = System.currentTimeMillis();
/*  92 */           IngestServerTester.this.func_153034_a(IngestServerTester.IngestTestState.ConnectingToServer);
/*     */         } else {
/*  94 */           IngestServerTester.this.field_153056_n = false;
/*  95 */           IngestServerTester.this.func_153034_a(IngestServerTester.IngestTestState.DoneTestingServer);
/*     */         } 
/*     */       }
/*     */       
/*     */       public void stopCallback(ErrorCode p_stopCallback_1_) {
/* 100 */         if (ErrorCode.failed(p_stopCallback_1_)) {
/* 101 */           System.out.println("IngestTester.stopCallback failed to stop - " + IngestServerTester.this.field_153059_q.serverName + ": " + p_stopCallback_1_.toString());
/*     */         }
/*     */         
/* 104 */         IngestServerTester.this.field_176007_z = false;
/* 105 */         IngestServerTester.this.field_176009_x = false;
/* 106 */         IngestServerTester.this.func_153034_a(IngestServerTester.IngestTestState.DoneTestingServer);
/* 107 */         IngestServerTester.this.field_153059_q = null;
/*     */         
/* 109 */         if (IngestServerTester.this.field_153060_r)
/* 110 */           IngestServerTester.this.func_153034_a(IngestServerTester.IngestTestState.Cancelling); 
/*     */       }
/*     */       
/*     */       public void sendActionMetaDataCallback(ErrorCode p_sendActionMetaDataCallback_1_) {}
/*     */       
/*     */       public void sendStartSpanMetaDataCallback(ErrorCode p_sendStartSpanMetaDataCallback_1_) {}
/*     */       
/*     */       public void sendEndSpanMetaDataCallback(ErrorCode p_sendEndSpanMetaDataCallback_1_) {}
/*     */     };
/*     */   
/*     */   protected boolean field_176008_y = false;
/*     */   protected boolean field_176007_z = false;
/*     */   
/* 123 */   protected IStatCallbacks field_176006_B = new IStatCallbacks() {
/*     */       public void statCallback(StatType p_statCallback_1_, long p_statCallback_2_) {
/* 125 */         switch (p_statCallback_1_) {
/*     */           case TTV_ST_RTMPSTATE:
/* 127 */             IngestServerTester.this.field_153051_i = RTMPState.lookupValue((int)p_statCallback_2_);
/*     */             break;
/*     */           
/*     */           case null:
/* 131 */             IngestServerTester.this.field_153050_h = p_statCallback_2_;
/*     */             break;
/*     */         } 
/*     */       }
/*     */     };
/*     */   public void func_153042_a(IngestTestListener p_153042_1_) {
/* 137 */     this.field_153044_b = p_153042_1_;
/*     */   }
/*     */   
/*     */   public IngestServer func_153040_c() {
/* 141 */     return this.field_153059_q;
/*     */   }
/*     */   
/*     */   public int func_153028_p() {
/* 145 */     return this.field_153062_t;
/*     */   }
/*     */   
/*     */   public boolean func_153032_e() {
/* 149 */     return !(this.field_153047_e != IngestTestState.Finished && this.field_153047_e != IngestTestState.Cancelled && this.field_153047_e != IngestTestState.Failed);
/*     */   }
/*     */   
/*     */   public float func_153030_h() {
/* 153 */     return this.field_153066_x;
/*     */   }
/*     */   
/*     */   public IngestServerTester(Stream p_i1019_1_, IngestList p_i1019_2_) {
/* 157 */     this.field_153045_c = p_i1019_1_;
/* 158 */     this.field_153046_d = p_i1019_2_;
/*     */   }
/*     */   
/*     */   public void func_176004_j() {
/* 162 */     if (this.field_153047_e == IngestTestState.Uninitalized) {
/* 163 */       this.field_153062_t = 0;
/* 164 */       this.field_153060_r = false;
/* 165 */       this.field_153061_s = false;
/* 166 */       this.field_176009_x = false;
/* 167 */       this.field_176008_y = false;
/* 168 */       this.field_176007_z = false;
/* 169 */       this.field_153058_p = this.field_153045_c.getStatCallbacks();
/* 170 */       this.field_153045_c.setStatCallbacks(this.field_176006_B);
/* 171 */       this.field_153057_o = this.field_153045_c.getStreamCallbacks();
/* 172 */       this.field_153045_c.setStreamCallbacks(this.field_176005_A);
/* 173 */       this.field_153052_j = new VideoParams();
/* 174 */       this.field_153052_j.targetFps = 60;
/* 175 */       this.field_153052_j.maxKbps = 3500;
/* 176 */       this.field_153052_j.outputWidth = 1280;
/* 177 */       this.field_153052_j.outputHeight = 720;
/* 178 */       this.field_153052_j.pixelFormat = PixelFormat.TTV_PF_BGRA;
/* 179 */       this.field_153052_j.encodingCpuUsage = EncodingCpuUsage.TTV_ECU_HIGH;
/* 180 */       this.field_153052_j.disableAdaptiveBitrate = true;
/* 181 */       this.field_153052_j.verticalFlip = false;
/* 182 */       this.field_153045_c.getDefaultParams(this.field_153052_j);
/* 183 */       this.audioParameters = new AudioParams();
/* 184 */       this.audioParameters.audioEnabled = false;
/* 185 */       this.audioParameters.enableMicCapture = false;
/* 186 */       this.audioParameters.enablePlaybackCapture = false;
/* 187 */       this.audioParameters.enablePassthroughAudio = false;
/* 188 */       this.field_153055_m = Lists.newArrayList();
/* 189 */       int i = 3;
/*     */       
/* 191 */       for (int j = 0; j < i; j++) {
/* 192 */         FrameBuffer framebuffer = this.field_153045_c.allocateFrameBuffer(this.field_153052_j.outputWidth * this.field_153052_j.outputHeight * 4);
/*     */         
/* 194 */         if (!framebuffer.getIsValid()) {
/* 195 */           func_153031_o();
/* 196 */           func_153034_a(IngestTestState.Failed);
/*     */           
/*     */           return;
/*     */         } 
/* 200 */         this.field_153055_m.add(framebuffer);
/* 201 */         this.field_153045_c.randomizeFrameBuffer(framebuffer);
/*     */       } 
/*     */       
/* 204 */       func_153034_a(IngestTestState.Starting);
/* 205 */       this.field_153054_l = System.currentTimeMillis();
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void func_153041_j() {
/* 211 */     if (!func_153032_e() && this.field_153047_e != IngestTestState.Uninitalized && 
/* 212 */       !this.field_176008_y && !this.field_176007_z) {
/* 213 */       switch (this.field_153047_e) {
/*     */         case Starting:
/*     */         case DoneTestingServer:
/* 216 */           if (this.field_153059_q != null) {
/* 217 */             if (this.field_153061_s || !this.field_153056_n) {
/* 218 */               this.field_153059_q.bitrateKbps = 0.0F;
/*     */             }
/*     */             
/* 221 */             func_153035_b(this.field_153059_q); break;
/*     */           } 
/* 223 */           this.field_153054_l = 0L;
/* 224 */           this.field_153061_s = false;
/* 225 */           this.field_153056_n = true;
/*     */           
/* 227 */           if (this.field_153047_e != IngestTestState.Starting) {
/* 228 */             this.field_153062_t++;
/*     */           }
/*     */           
/* 231 */           if (this.field_153062_t < (this.field_153046_d.getServers()).length) {
/* 232 */             this.field_153059_q = this.field_153046_d.getServers()[this.field_153062_t];
/* 233 */             func_153036_a(this.field_153059_q); break;
/*     */           } 
/* 235 */           func_153034_a(IngestTestState.Finished);
/*     */           break;
/*     */ 
/*     */ 
/*     */ 
/*     */         
/*     */         case ConnectingToServer:
/*     */         case TestingServer:
/* 243 */           func_153029_c(this.field_153059_q);
/*     */           break;
/*     */         
/*     */         case Cancelling:
/* 247 */           func_153034_a(IngestTestState.Cancelled);
/*     */           break;
/*     */       } 
/* 250 */       func_153038_n();
/*     */       
/* 252 */       if (this.field_153047_e == IngestTestState.Cancelled || this.field_153047_e == IngestTestState.Finished) {
/* 253 */         func_153031_o();
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void func_153039_l() {
/* 260 */     if (!func_153032_e() && !this.field_153060_r) {
/* 261 */       this.field_153060_r = true;
/*     */       
/* 263 */       if (this.field_153059_q != null) {
/* 264 */         this.field_153059_q.bitrateKbps = 0.0F;
/*     */       }
/*     */     } 
/*     */   }
/*     */   
/*     */   protected boolean func_153036_a(IngestServer p_153036_1_) {
/* 270 */     this.field_153056_n = true;
/* 271 */     this.field_153050_h = 0L;
/* 272 */     this.field_153051_i = RTMPState.Idle;
/* 273 */     this.field_153059_q = p_153036_1_;
/* 274 */     this.field_176008_y = true;
/* 275 */     func_153034_a(IngestTestState.ConnectingToServer);
/* 276 */     ErrorCode errorcode = this.field_153045_c.start(this.field_153052_j, this.audioParameters, p_153036_1_, StartFlags.TTV_Start_BandwidthTest, true);
/*     */     
/* 278 */     if (ErrorCode.failed(errorcode)) {
/* 279 */       this.field_176008_y = false;
/* 280 */       this.field_153056_n = false;
/* 281 */       func_153034_a(IngestTestState.DoneTestingServer);
/* 282 */       return false;
/*     */     } 
/* 284 */     this.field_153064_v = this.field_153050_h;
/* 285 */     p_153036_1_.bitrateKbps = 0.0F;
/* 286 */     this.field_153063_u = 0;
/* 287 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   protected void func_153035_b(IngestServer p_153035_1_) {
/* 292 */     if (this.field_176008_y) {
/* 293 */       this.field_153061_s = true;
/* 294 */     } else if (this.field_176009_x) {
/* 295 */       this.field_176007_z = true;
/* 296 */       ErrorCode errorcode = this.field_153045_c.stop(true);
/*     */       
/* 298 */       if (ErrorCode.failed(errorcode)) {
/* 299 */         this.field_176005_A.stopCallback(ErrorCode.TTV_EC_SUCCESS);
/* 300 */         System.out.println("Stop failed: " + errorcode.toString());
/*     */       } 
/*     */       
/* 303 */       this.field_153045_c.pollStats();
/*     */     } else {
/* 305 */       this.field_176005_A.stopCallback(ErrorCode.TTV_EC_SUCCESS);
/*     */     } 
/*     */   }
/*     */   
/*     */   protected long func_153037_m() {
/* 310 */     return System.currentTimeMillis() - this.field_153054_l;
/*     */   }
/*     */   
/*     */   protected void func_153038_n() {
/* 314 */     float f = (float)func_153037_m();
/*     */     
/* 316 */     switch (this.field_153047_e) {
/*     */       case Uninitalized:
/*     */       case Starting:
/*     */       case ConnectingToServer:
/*     */       case Finished:
/*     */       case null:
/*     */       case Failed:
/* 323 */         this.field_153066_x = 0.0F;
/*     */         break;
/*     */       
/*     */       case DoneTestingServer:
/* 327 */         this.field_153066_x = 1.0F;
/*     */         break;
/*     */ 
/*     */ 
/*     */       
/*     */       default:
/* 333 */         this.field_153066_x = f / (float)this.field_153048_f;
/*     */         break;
/*     */     } 
/* 336 */     switch (this.field_153047_e) {
/*     */       case Finished:
/*     */       case null:
/*     */       case Failed:
/* 340 */         this.field_153065_w = 1.0F;
/*     */         return;
/*     */     } 
/*     */     
/* 344 */     this.field_153065_w = this.field_153062_t / (this.field_153046_d.getServers()).length;
/* 345 */     this.field_153065_w += this.field_153066_x / (this.field_153046_d.getServers()).length;
/*     */   }
/*     */ 
/*     */   
/*     */   protected boolean func_153029_c(IngestServer p_153029_1_) {
/* 350 */     if (!this.field_153061_s && !this.field_153060_r && func_153037_m() < this.field_153048_f) {
/* 351 */       if (!this.field_176008_y && !this.field_176007_z) {
/* 352 */         ErrorCode errorcode = this.field_153045_c.submitVideoFrame(this.field_153055_m.get(this.field_153063_u));
/*     */         
/* 354 */         if (ErrorCode.failed(errorcode)) {
/* 355 */           this.field_153056_n = false;
/* 356 */           func_153034_a(IngestTestState.DoneTestingServer);
/* 357 */           return false;
/*     */         } 
/* 359 */         this.field_153063_u = (this.field_153063_u + 1) % this.field_153055_m.size();
/* 360 */         this.field_153045_c.pollStats();
/*     */         
/* 362 */         if (this.field_153051_i == RTMPState.SendVideo) {
/* 363 */           func_153034_a(IngestTestState.TestingServer);
/* 364 */           long i = func_153037_m();
/*     */           
/* 366 */           if (i > 0L && this.field_153050_h > this.field_153064_v) {
/* 367 */             p_153029_1_.bitrateKbps = (float)(this.field_153050_h * 8L) / (float)func_153037_m();
/* 368 */             this.field_153064_v = this.field_153050_h;
/*     */           } 
/*     */         } 
/*     */         
/* 372 */         return true;
/*     */       } 
/*     */       
/* 375 */       return true;
/*     */     } 
/*     */     
/* 378 */     func_153034_a(IngestTestState.DoneTestingServer);
/* 379 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   protected void func_153031_o() {
/* 384 */     this.field_153059_q = null;
/*     */     
/* 386 */     if (this.field_153055_m != null) {
/* 387 */       for (int i = 0; i < this.field_153055_m.size(); i++) {
/* 388 */         ((FrameBuffer)this.field_153055_m.get(i)).free();
/*     */       }
/*     */       
/* 391 */       this.field_153055_m = null;
/*     */     } 
/*     */     
/* 394 */     if (this.field_153045_c.getStatCallbacks() == this.field_176006_B) {
/* 395 */       this.field_153045_c.setStatCallbacks(this.field_153058_p);
/* 396 */       this.field_153058_p = null;
/*     */     } 
/*     */     
/* 399 */     if (this.field_153045_c.getStreamCallbacks() == this.field_176005_A) {
/* 400 */       this.field_153045_c.setStreamCallbacks(this.field_153057_o);
/* 401 */       this.field_153057_o = null;
/*     */     } 
/*     */   }
/*     */   
/*     */   protected void func_153034_a(IngestTestState p_153034_1_) {
/* 406 */     if (p_153034_1_ != this.field_153047_e) {
/* 407 */       this.field_153047_e = p_153034_1_;
/*     */       
/* 409 */       if (this.field_153044_b != null)
/* 410 */         this.field_153044_b.func_152907_a(this, p_153034_1_); 
/*     */     } 
/*     */   }
/*     */   
/*     */   public static interface IngestTestListener
/*     */   {
/*     */     void func_152907_a(IngestServerTester param1IngestServerTester, IngestServerTester.IngestTestState param1IngestTestState);
/*     */   }
/*     */   
/*     */   public enum IngestTestState {
/* 420 */     Uninitalized,
/* 421 */     Starting,
/* 422 */     ConnectingToServer,
/* 423 */     TestingServer,
/* 424 */     DoneTestingServer,
/* 425 */     Finished,
/* 426 */     Cancelling,
/* 427 */     Cancelled,
/* 428 */     Failed;
/*     */   }
/*     */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\minecraft\client\stream\IngestServerTester.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */