/*    */ package kp.keyboards;
/*    */ 
/*    */ import java.util.ArrayList;
/*    */ import kp.Log;
/*    */ 
/*    */ public class KeyLoader
/*    */ {
/*    */   private final ArrayList<KeyboardArray> keyboards;
/*    */   private int currentKeyboard;
/*    */   
/*    */   public KeyLoader() {
/* 12 */     this.keyboards = new ArrayList<>();
/* 13 */     this.currentKeyboard = 0;
/* 14 */     loadKeyboards();
/*    */   }
/*    */   
/*    */   public void loadKeyboards() {
/* 18 */     this.keyboards.clear();
/* 19 */     this.keyboards.add(new KeyboardQwerty());
/* 20 */     this.keyboards.add(new KeyboardThird390());
/* 21 */     this.keyboards.add(new KeyboardThird391());
/*    */   }
/*    */   
/*    */   public int getKeyboardArrayIndex() {
/* 25 */     return this.currentKeyboard;
/*    */   }
/*    */   
/*    */   public String applySetIterate() {
/* 29 */     this.currentKeyboard = (this.currentKeyboard + 1) % this.keyboards.size();
/* 30 */     return getKeyboard().keyboardName();
/*    */   }
/*    */   
/*    */   public KeyboardArray getKeyboard() {
/* 34 */     return this.keyboards.get(this.currentKeyboard);
/*    */   }
/*    */   
/*    */   public String[] getKeyboards() {
/* 38 */     String[] names = new String[this.keyboards.size()];
/* 39 */     for (int a = 0; a < names.length; a++) {
/* 40 */       names[a] = ((KeyboardArray)this.keyboards.get(a)).keyboardName();
/*    */     }
/* 42 */     return names;
/*    */   }
/*    */   
/*    */   public String getKeyboardsToString() {
/* 46 */     StringBuilder sb = new StringBuilder();
/* 47 */     int i = 0;
/* 48 */     String[] keyboards = getKeyboards();
/*    */     String[] array;
/* 50 */     for (int length = (array = keyboards).length, j = 0; j < length; j++) {
/* 51 */       String str = array[j];
/* 52 */       sb.append(str);
/* 53 */       if (i++ < keyboards.length) {
/* 54 */         sb.append(",");
/*    */       }
/*    */     } 
/* 57 */     return sb.toString();
/*    */   }
/*    */   
/*    */   public void setKeyboard(int keyboardIndex) {
/* 61 */     if (this.currentKeyboard != keyboardIndex)
/* 62 */       if (keyboardIndex >= 0 && keyboardIndex < this.keyboards.size()) {
/* 63 */         this.currentKeyboard = keyboardIndex;
/* 64 */         Log.i("Set keyboard to " + ((KeyboardArray)this.keyboards.get(keyboardIndex)).keyboardName());
/*    */       } else {
/*    */         
/* 67 */         Log.error("Keyboard index " + keyboardIndex + " is out of array!");
/*    */       }  
/*    */   }
/*    */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\kp\keyboards\KeyLoader.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */