/*     */ package net.minecraft.client.stream;
/*     */ 
/*     */ import tv.twitch.ErrorCode;
/*     */ import tv.twitch.broadcast.IngestServer;
/*     */ import tv.twitch.chat.ChatUserInfo;
/*     */ 
/*     */ public class NullStream implements IStream {
/*     */   private final Throwable field_152938_a;
/*     */   
/*     */   public NullStream(Throwable p_i1006_1_) {
/*  11 */     this.field_152938_a = p_i1006_1_;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void shutdownStream() {}
/*     */ 
/*     */ 
/*     */   
/*     */   public void func_152935_j() {}
/*     */ 
/*     */   
/*     */   public void func_152922_k() {}
/*     */ 
/*     */   
/*     */   public boolean func_152936_l() {
/*  27 */     return false;
/*     */   }
/*     */   
/*     */   public boolean isReadyToBroadcast() {
/*  31 */     return false;
/*     */   }
/*     */   
/*     */   public boolean isBroadcasting() {
/*  35 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public void func_152911_a(Metadata p_152911_1_, long p_152911_2_) {}
/*     */ 
/*     */   
/*     */   public void func_176026_a(Metadata p_176026_1_, long p_176026_2_, long p_176026_4_) {}
/*     */   
/*     */   public boolean isPaused() {
/*  45 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void requestCommercial() {}
/*     */ 
/*     */ 
/*     */   
/*     */   public void pause() {}
/*     */ 
/*     */ 
/*     */   
/*     */   public void unpause() {}
/*     */ 
/*     */ 
/*     */   
/*     */   public void updateStreamVolume() {}
/*     */ 
/*     */ 
/*     */   
/*     */   public void func_152930_t() {}
/*     */ 
/*     */   
/*     */   public void stopBroadcasting() {}
/*     */ 
/*     */   
/*     */   public IngestServer[] func_152925_v() {
/*  73 */     return new IngestServer[0];
/*     */   }
/*     */ 
/*     */   
/*     */   public void func_152909_x() {}
/*     */   
/*     */   public IngestServerTester func_152932_y() {
/*  80 */     return null;
/*     */   }
/*     */   
/*     */   public boolean func_152908_z() {
/*  84 */     return false;
/*     */   }
/*     */   
/*     */   public int func_152920_A() {
/*  88 */     return 0;
/*     */   }
/*     */   
/*     */   public boolean func_152927_B() {
/*  92 */     return false;
/*     */   }
/*     */   
/*     */   public String func_152921_C() {
/*  96 */     return null;
/*     */   }
/*     */   
/*     */   public ChatUserInfo func_152926_a(String p_152926_1_) {
/* 100 */     return null;
/*     */   }
/*     */ 
/*     */   
/*     */   public void func_152917_b(String p_152917_1_) {}
/*     */   
/*     */   public boolean func_152928_D() {
/* 107 */     return false;
/*     */   }
/*     */   
/*     */   public ErrorCode func_152912_E() {
/* 111 */     return null;
/*     */   }
/*     */   
/*     */   public boolean func_152913_F() {
/* 115 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void muteMicrophone(boolean p_152910_1_) {}
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean func_152929_G() {
/* 125 */     return false;
/*     */   }
/*     */   
/*     */   public IStream.AuthFailureReason func_152918_H() {
/* 129 */     return IStream.AuthFailureReason.ERROR;
/*     */   }
/*     */   
/*     */   public Throwable func_152937_a() {
/* 133 */     return this.field_152938_a;
/*     */   }
/*     */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\minecraft\client\stream\NullStream.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */