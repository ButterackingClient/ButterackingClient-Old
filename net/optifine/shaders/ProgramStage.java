/*    */ package net.optifine.shaders;
/*    */ 
/*    */ public enum ProgramStage {
/*  4 */   NONE(""),
/*  5 */   SHADOW("shadow"),
/*  6 */   GBUFFERS("gbuffers"),
/*  7 */   DEFERRED("deferred"),
/*  8 */   COMPOSITE("composite");
/*    */   
/*    */   private String name;
/*    */   
/*    */   ProgramStage(String name) {
/* 13 */     this.name = name;
/*    */   }
/*    */   
/*    */   public String getName() {
/* 17 */     return this.name;
/*    */   }
/*    */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\optifine\shaders\ProgramStage.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */