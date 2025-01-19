/*    */ package kp.input;
/*    */ 
/*    */ import kp.Config;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class KoreanIME
/*    */   extends InputMethod
/*    */ {
/* 12 */   public static String lastGuiChatStr = "";
/*    */   public static boolean enabled = false;
/*    */   private boolean editing;
/*    */   
/*    */   public KoreanIME(IInputTarget target) {
/* 17 */     super(target);
/* 18 */     this.editing = true;
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean onTyped(int i, char c) {
/* 23 */     boolean ret = super.onTyped(i, c);
/* 24 */     if (!ret && i == 29) {
/* 25 */       enabled = !enabled;
/*    */     }
/* 27 */     if (enabled && (i == 203 || i == 205)) {
/* 28 */       this.editing = false;
/*    */     }
/* 30 */     if (InputMethod.getMinecraftInterface().isOnGuiChat()) {
/* 31 */       lastGuiChatStr = getTextFromTarget();
/*    */     }
/* 33 */     return ret;
/*    */   }
/*    */ 
/*    */   
/*    */   public void onBackspace(IInputTarget target) {
/* 38 */     if (this.selectionFuncPtr.getCursor() > 0) {
/* 39 */       if (this.editing && enabled && Config.DELETE_JASO) {
/* 40 */         String text = HangulAssembler.disassemble(target.getTargetText().charAt(this.selectionFuncPtr.getCursor() - 1));
/* 41 */         text = text.substring(0, text.length() - 1);
/* 42 */         text = HangulAssembler.make(text);
/* 43 */         replaceStrAtCursor(target, text);
/*    */       } else {
/*    */         
/* 46 */         replaceStrAtCursor(target, "");
/*    */       } 
/*    */     }
/*    */   }
/*    */ 
/*    */   
/*    */   public void onAppend(IInputTarget target, int code, char c) {
/* 53 */     if (enabled) {
/* 54 */       if (!HangulAssembler.isAllowed(String.valueOf(c))) {
/* 55 */         c = Character.toLowerCase(c);
/*    */       }
/* 57 */       if (this.selectionFuncPtr.getCursor() > 0 && this.editing) {
/* 58 */         char end = target.getTargetText().charAt(this.selectionFuncPtr.getCursor() - 1);
/* 59 */         if (HangulAssembler.isKorean(end) || HangulAssembler.getJamoTier(end) > 0) {
/* 60 */           String text = String.valueOf(String.valueOf(HangulAssembler.disassemble(end))) + c;
/* 61 */           text = HangulAssembler.make(text);
/* 62 */           if (this.identifier.apply(text)) {
/* 63 */             replaceStrAtCursor(target, text);
/*    */           }
/*    */         } else {
/*    */           
/* 67 */           writeTextFunc(HangulAssembler.convertKrEn(c, false));
/*    */         } 
/* 69 */         this.editing = true;
/*    */       } else {
/*    */         
/* 72 */         writeTextFunc(HangulAssembler.convertKrEn(c, false));
/* 73 */         this.editing = true;
/*    */       } 
/*    */     } else {
/*    */       
/* 77 */       writeTextFunc(String.valueOf(c));
/*    */     } 
/*    */   }
/*    */   
/*    */   private void replaceStrAtCursor(IInputTarget target, String c) {
/* 82 */     String text = target.getTargetText();
/* 83 */     int cur = this.selectionFuncPtr.getCursor();
/* 84 */     int last = text.length();
/* 85 */     String a = text.substring(0, cur);
/* 86 */     String b = text.substring(cur);
/* 87 */     text = String.valueOf(String.valueOf(a.substring(0, a.length() - 1))) + c + b;
/* 88 */     int dCursor = text.length() - last;
/* 89 */     if (target.setTargetText(text)) {
/* 90 */       this.selectionFuncPtr.setCursor(cur + dCursor);
/*    */     }
/* 92 */     if (dCursor != 0)
/* 93 */       this.editing = false; 
/*    */   }
/*    */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\kp\input\KoreanIME.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */