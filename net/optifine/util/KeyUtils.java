/*    */ package net.optifine.util;
/*    */ 
/*    */ import java.util.Arrays;
/*    */ import java.util.HashSet;
/*    */ import java.util.Set;
/*    */ import net.minecraft.client.settings.KeyBinding;
/*    */ 
/*    */ public class KeyUtils
/*    */ {
/*    */   public static void fixKeyConflicts(KeyBinding[] keys, KeyBinding[] keysPrio) {
/* 11 */     Set<Integer> set = new HashSet<>();
/*    */     
/* 13 */     for (int i = 0; i < keysPrio.length; i++) {
/* 14 */       KeyBinding keybinding = keysPrio[i];
/* 15 */       set.add(Integer.valueOf(keybinding.getKeyCode()));
/*    */     } 
/*    */     
/* 18 */     Set<KeyBinding> set1 = new HashSet<>(Arrays.asList(keys));
/* 19 */     set1.removeAll(Arrays.asList((Object[])keysPrio));
/*    */     
/* 21 */     for (KeyBinding keybinding1 : set1) {
/* 22 */       Integer integer = Integer.valueOf(keybinding1.getKeyCode());
/*    */       
/* 24 */       if (set.contains(integer))
/* 25 */         keybinding1.setKeyCode(0); 
/*    */     } 
/*    */   }
/*    */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\optifin\\util\KeyUtils.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */