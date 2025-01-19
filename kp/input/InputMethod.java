/*     */ package kp.input;
/*     */ 
/*     */ import java.awt.Toolkit;
/*     */ import java.awt.datatransfer.DataFlavor;
/*     */ import java.awt.datatransfer.StringSelection;
/*     */ import java.awt.datatransfer.Transferable;
/*     */ import kp.mcinterface.DefaultMCInterface;
/*     */ import kp.mcinterface.IMinecraftInterface;
/*     */ 
/*     */ public class InputMethod
/*     */   implements IInputTarget.WriteTextFunc, IInputTarget.CursorSelectionFunc, IInputTarget.InputIdentifier {
/*     */   private int inner_cursor;
/*     */   private int inner_selection;
/*     */   private IInputTarget target;
/*     */   protected IInputTarget.WriteTextFunc writeTextPtr;
/*     */   protected IInputTarget.CursorSelectionFunc selectionFuncPtr;
/*     */   protected IInputTarget.InputIdentifier identifier;
/*  18 */   private static IMinecraftInterface mcinterface = (IMinecraftInterface)new DefaultMCInterface();
/*     */ 
/*     */   
/*     */   public InputMethod(IInputTarget target) {
/*  22 */     this.inner_cursor = 0;
/*  23 */     this.inner_selection = 0;
/*  24 */     this.target = target;
/*  25 */     this.writeTextPtr = this;
/*  26 */     this.selectionFuncPtr = this;
/*  27 */     this.identifier = this;
/*     */   }
/*     */   
/*     */   public void setWriteTextFunc(IInputTarget.WriteTextFunc funcCls) {
/*  31 */     this.writeTextPtr = funcCls;
/*     */   }
/*     */   
/*     */   public void setCursorSelectionFunc(IInputTarget.CursorSelectionFunc funcCls) {
/*  35 */     this.selectionFuncPtr = funcCls;
/*     */   }
/*     */   
/*     */   public void setIdentifier(IInputTarget.InputIdentifier ii) {
/*  39 */     this.identifier = ii;
/*     */   }
/*     */   
/*     */   public boolean onTyped(int i, char c) {
/*  43 */     int cur = this.selectionFuncPtr.getCursor();
/*  44 */     int sel = this.selectionFuncPtr.getSelection();
/*  45 */     String text = this.target.getTargetText();
/*  46 */     if (isAlpha(c)) {
/*  47 */       if (cur != sel) {
/*  48 */         this.writeTextPtr.writeTextFunc("");
/*     */       }
/*  50 */       onAppend(this.target, i, c);
/*     */     }
/*  52 */     else if (i == 14) {
/*  53 */       if (cur != sel) {
/*  54 */         this.writeTextPtr.writeTextFunc("");
/*     */       }
/*  56 */       else if (mcinterface.isCtrlKeyDown()) {
/*  57 */         this.selectionFuncPtr.setSelection(getWordPosFromCursor(false));
/*  58 */         this.writeTextPtr.writeTextFunc("");
/*     */       } else {
/*     */         
/*  61 */         onBackspace(this.target);
/*     */       }
/*     */     
/*  64 */     } else if (i == 203) {
/*  65 */       if (mcinterface.isShiftKeyDown()) {
/*  66 */         if (sel <= 0) {
/*  67 */           return false;
/*     */         }
/*  69 */         if (mcinterface.isCtrlKeyDown()) {
/*  70 */           this.selectionFuncPtr.setSelection(getWordPosFromCursor(false));
/*     */         } else {
/*     */           
/*  73 */           this.selectionFuncPtr.setSelection(sel - 1);
/*     */         } 
/*     */       } else {
/*     */         
/*  77 */         if (cur <= 0) {
/*  78 */           return false;
/*     */         }
/*  80 */         if (mcinterface.isCtrlKeyDown()) {
/*  81 */           this.selectionFuncPtr.setCursor(getWordPosFromCursor(false));
/*     */         } else {
/*     */           
/*  84 */           this.selectionFuncPtr.setCursor(cur - 1);
/*     */         }
/*     */       
/*     */       } 
/*  88 */     } else if (i == 205) {
/*  89 */       if (mcinterface.isShiftKeyDown()) {
/*  90 */         if (sel >= text.length()) {
/*  91 */           return false;
/*     */         }
/*  93 */         if (mcinterface.isCtrlKeyDown()) {
/*  94 */           this.selectionFuncPtr.setSelection(getWordPosFromCursor(true));
/*     */         } else {
/*     */           
/*  97 */           this.selectionFuncPtr.setSelection(sel + 1);
/*     */         } 
/*     */       } else {
/*     */         
/* 101 */         if (cur >= text.length()) {
/* 102 */           return false;
/*     */         }
/* 104 */         if (mcinterface.isCtrlKeyDown()) {
/* 105 */           this.selectionFuncPtr.setCursor(getWordPosFromCursor(true));
/*     */         } else {
/*     */           
/* 108 */           this.selectionFuncPtr.setCursor(cur + 1);
/*     */         }
/*     */       
/*     */       } 
/* 112 */     } else if (i == 199) {
/* 113 */       if (mcinterface.isShiftKeyDown()) {
/* 114 */         this.selectionFuncPtr.setSelection(0);
/*     */       } else {
/*     */         
/* 117 */         this.selectionFuncPtr.setCursor(0);
/*     */       }
/*     */     
/* 120 */     } else if (i == 207) {
/* 121 */       if (mcinterface.isShiftKeyDown()) {
/* 122 */         this.selectionFuncPtr.setSelection(text.length());
/*     */       } else {
/*     */         
/* 125 */         this.selectionFuncPtr.setCursor(text.length());
/*     */       }
/*     */     
/* 128 */     } else if (i == 211) {
/* 129 */       if (cur != sel) {
/* 130 */         this.writeTextPtr.writeTextFunc("");
/*     */       } else {
/*     */         
/* 133 */         if (cur >= text.length()) {
/* 134 */           return false;
/*     */         }
/* 136 */         if (mcinterface.isCtrlKeyDown()) {
/* 137 */           this.selectionFuncPtr.setSelection(getWordPosFromCursor(true));
/* 138 */           this.writeTextPtr.writeTextFunc("");
/*     */         } else {
/*     */           
/* 141 */           String s1 = text.substring(0, cur);
/* 142 */           String s2 = text.substring(cur + 1);
/* 143 */           this.target.setTargetText(String.valueOf(String.valueOf(s1)) + s2);
/*     */         }
/*     */       
/*     */       } 
/* 147 */     } else if (isKeyComboCtrlKey(i, 30)) {
/* 148 */       this.selectionFuncPtr.setCursor(text.length());
/* 149 */       this.selectionFuncPtr.setSelection(0);
/*     */     }
/* 151 */     else if (isKeyComboCtrlKey(i, 46)) {
/* 152 */       setClipboardString(text.substring(Math.min(cur, sel), Math.max(cur, sel)));
/*     */     }
/* 154 */     else if (isKeyComboCtrlKey(i, 47)) {
/* 155 */       this.writeTextPtr.writeTextFunc(getClipboardString());
/*     */     }
/* 157 */     else if (isKeyComboCtrlKey(i, 45)) {
/* 158 */       setClipboardString(text.substring(Math.min(cur, sel), Math.max(cur, sel)));
/* 159 */       this.writeTextPtr.writeTextFunc("");
/*     */     } else {
/*     */       
/* 162 */       if (!mcinterface.isAllowedCharacter(c)) {
/* 163 */         return false;
/*     */       }
/* 165 */       this.writeTextPtr.writeTextFunc(String.valueOf(c));
/*     */     } 
/* 167 */     return true;
/*     */   }
/*     */   
/*     */   public String getTextFromTarget() {
/* 171 */     return this.target.getTargetText();
/*     */   }
/*     */   
/*     */   protected int getWordPosFromCursor(boolean right) {
/* 175 */     int i = this.selectionFuncPtr.getSelection();
/* 176 */     int len = this.target.getTargetText().length();
/* 177 */     char[] strs = this.target.getTargetText().toCharArray();
/* 178 */     if (i == 0 && !right) {
/* 179 */       return 0;
/*     */     }
/* 181 */     i += right ? 1 : -1;
/* 182 */     if (right) {
/* 183 */       while (i < len && 
/* 184 */         strs[i] == ' ')
/*     */       {
/*     */         
/* 187 */         i++;
/*     */       }
/* 189 */       while (i < len && 
/* 190 */         strs[i] != ' ')
/*     */       {
/*     */         
/* 193 */         i++;
/*     */       }
/*     */     } else {
/*     */       
/* 197 */       while (i > 0 && 
/* 198 */         strs[i] == ' ')
/*     */       {
/*     */         
/* 201 */         i--;
/*     */       }
/* 203 */       while (i > 0 && strs[i] != ' ') {
/* 204 */         i--;
/*     */       }
/*     */     } 
/* 207 */     return i;
/*     */   }
/*     */ 
/*     */   
/*     */   public void writeTextFunc(String str) {
/* 212 */     if (this.identifier.apply(str)) {
/* 213 */       int cursor = this.selectionFuncPtr.getCursor();
/* 214 */       String splited1 = this.target.getTargetText().substring(0, cursor);
/* 215 */       String splited2 = this.target.getTargetText().substring(cursor);
/* 216 */       this.target.setTargetText(String.valueOf(String.valueOf(splited1)) + str + splited2);
/* 217 */       this.selectionFuncPtr.setCursor(cursor + 1);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public int getCursor() {
/* 223 */     return this.inner_cursor;
/*     */   }
/*     */ 
/*     */   
/*     */   public void setCursor(int cur) {
/* 228 */     this.inner_cursor = cur;
/* 229 */     this.inner_selection = cur;
/*     */   }
/*     */ 
/*     */   
/*     */   public int getSelection() {
/* 234 */     return this.inner_selection;
/*     */   }
/*     */ 
/*     */   
/*     */   public void setSelection(int selectionPos) {
/* 239 */     this.inner_selection = selectionPos;
/*     */   }
/*     */   
/*     */   public boolean isAlpha(char c) {
/* 243 */     return !((c < 'a' || c > 'z') && (c < 'A' || c > 'Z'));
/*     */   }
/*     */   
/*     */   public static void setInterface(IMinecraftInterface itf) {
/* 247 */     mcinterface = itf;
/*     */   }
/*     */   
/*     */   public static IMinecraftInterface getMinecraftInterface() {
/* 251 */     return mcinterface;
/*     */   }
/*     */   
/*     */   public static boolean isKeyComboCtrlKey(int key, int test) {
/* 255 */     return (key == test && mcinterface.isCtrlKeyDown() && !mcinterface.isShiftKeyDown() && !mcinterface.isAltKeyDown());
/*     */   }
/*     */   
/*     */   public static String getClipboardString() {
/*     */     try {
/* 260 */       Transferable transferable = Toolkit.getDefaultToolkit().getSystemClipboard().getContents(null);
/* 261 */       if (transferable != null && transferable.isDataFlavorSupported(DataFlavor.stringFlavor)) {
/* 262 */         return (String)transferable.getTransferData(DataFlavor.stringFlavor);
/*     */       }
/*     */     }
/* 265 */     catch (Exception var1) {
/* 266 */       var1.printStackTrace();
/*     */     } 
/* 268 */     return "";
/*     */   }
/*     */   
/*     */   public static void setClipboardString(String copyText) {
/* 272 */     if (!copyText.isEmpty()) {
/*     */       try {
/* 274 */         StringSelection sel = new StringSelection(copyText);
/* 275 */         Toolkit.getDefaultToolkit().getSystemClipboard().setContents(sel, null);
/*     */       }
/* 277 */       catch (Exception var2) {
/* 278 */         var2.printStackTrace();
/*     */       } 
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public void onBackspace(IInputTarget target) {}
/*     */ 
/*     */   
/*     */   public void onAppend(IInputTarget target, int i, char c) {}
/*     */ 
/*     */   
/*     */   public boolean apply(String append) {
/* 291 */     return true;
/*     */   }
/*     */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\kp\input\InputMethod.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */