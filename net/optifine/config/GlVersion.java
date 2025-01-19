/*    */ package net.optifine.config;
/*    */ 
/*    */ public class GlVersion {
/*    */   private int major;
/*    */   private int minor;
/*    */   private int release;
/*    */   private String suffix;
/*    */   
/*    */   public GlVersion(int major, int minor) {
/* 10 */     this(major, minor, 0);
/*    */   }
/*    */   
/*    */   public GlVersion(int major, int minor, int release) {
/* 14 */     this(major, minor, release, null);
/*    */   }
/*    */   
/*    */   public GlVersion(int major, int minor, int release, String suffix) {
/* 18 */     this.major = major;
/* 19 */     this.minor = minor;
/* 20 */     this.release = release;
/* 21 */     this.suffix = suffix;
/*    */   }
/*    */   
/*    */   public int getMajor() {
/* 25 */     return this.major;
/*    */   }
/*    */   
/*    */   public int getMinor() {
/* 29 */     return this.minor;
/*    */   }
/*    */   
/*    */   public int getRelease() {
/* 33 */     return this.release;
/*    */   }
/*    */   
/*    */   public int toInt() {
/* 37 */     return (this.minor > 9) ? (this.major * 100 + this.minor) : ((this.release > 9) ? (this.major * 100 + this.minor * 10 + 9) : (this.major * 100 + this.minor * 10 + this.release));
/*    */   }
/*    */   
/*    */   public String toString() {
/* 41 */     return (this.suffix == null) ? (this.major + "." + this.minor + "." + this.release) : (this.major + "." + this.minor + "." + this.release + this.suffix);
/*    */   }
/*    */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\optifine\config\GlVersion.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */