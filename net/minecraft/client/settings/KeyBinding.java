/*     */ package net.minecraft.client.settings;
/*     */ 
/*     */ import com.google.common.collect.Lists;
/*     */ import com.google.common.collect.Sets;
/*     */ import java.util.List;
/*     */ import java.util.Set;
/*     */ import net.minecraft.client.resources.I18n;
/*     */ import net.minecraft.util.IntHashMap;
/*     */ 
/*     */ public class KeyBinding
/*     */   implements Comparable<KeyBinding>
/*     */ {
/*  13 */   private static final List<KeyBinding> keybindArray = Lists.newArrayList();
/*  14 */   private static final IntHashMap<KeyBinding> hash = new IntHashMap();
/*  15 */   private static final Set<String> keybindSet = Sets.newHashSet();
/*     */   
/*     */   private final String keyDescription;
/*     */   
/*     */   private final int keyCodeDefault;
/*     */   
/*     */   private final String keyCategory;
/*     */   
/*     */   private int keyCode;
/*     */   private boolean pressed;
/*     */   private int pressTime;
/*     */   
/*     */   public static void onTick(int keyCode) {
/*  28 */     if (keyCode != 0) {
/*  29 */       KeyBinding keybinding = (KeyBinding)hash.lookup(keyCode);
/*     */       
/*  31 */       if (keybinding != null) {
/*  32 */         keybinding.pressTime++;
/*     */       }
/*     */     } 
/*     */   }
/*     */   
/*     */   public static void setKeyBindState(int keyCode, boolean pressed) {
/*  38 */     if (keyCode != 0) {
/*  39 */       KeyBinding keybinding = (KeyBinding)hash.lookup(keyCode);
/*     */       
/*  41 */       if (keybinding != null) {
/*  42 */         keybinding.pressed = pressed;
/*     */       }
/*     */     } 
/*     */   }
/*     */   
/*     */   public static void unPressAllKeys() {
/*  48 */     for (KeyBinding keybinding : keybindArray) {
/*  49 */       keybinding.unpressKey();
/*     */     }
/*     */   }
/*     */   
/*     */   public static void resetKeyBindingArrayAndHash() {
/*  54 */     hash.clearMap();
/*     */     
/*  56 */     for (KeyBinding keybinding : keybindArray) {
/*  57 */       hash.addKey(keybinding.keyCode, keybinding);
/*     */     }
/*     */   }
/*     */   
/*     */   public static Set<String> getKeybinds() {
/*  62 */     return keybindSet;
/*     */   }
/*     */   
/*     */   public KeyBinding(String description, int keyCode, String category) {
/*  66 */     this.keyDescription = description;
/*  67 */     this.keyCode = keyCode;
/*  68 */     this.keyCodeDefault = keyCode;
/*  69 */     this.keyCategory = category;
/*  70 */     keybindArray.add(this);
/*  71 */     hash.addKey(keyCode, this);
/*  72 */     keybindSet.add(category);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isKeyDown() {
/*  79 */     return this.pressed;
/*     */   }
/*     */   
/*     */   public String getKeyCategory() {
/*  83 */     return this.keyCategory;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isPressed() {
/*  91 */     if (this.pressTime == 0) {
/*  92 */       return false;
/*     */     }
/*  94 */     this.pressTime--;
/*  95 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   private void unpressKey() {
/* 100 */     this.pressTime = 0;
/* 101 */     this.pressed = false;
/*     */   }
/*     */   
/*     */   public void setPressed(boolean pressed) {
/* 105 */     this.pressed = pressed;
/*     */   }
/*     */   
/*     */   public String getKeyDescription() {
/* 109 */     return this.keyDescription;
/*     */   }
/*     */   
/*     */   public int getKeyCodeDefault() {
/* 113 */     return this.keyCodeDefault;
/*     */   }
/*     */   
/*     */   public int getKeyCode() {
/* 117 */     return this.keyCode;
/*     */   }
/*     */   
/*     */   public void setKeyCode(int keyCode) {
/* 121 */     this.keyCode = keyCode;
/*     */   }
/*     */   
/*     */   public int compareTo(KeyBinding p_compareTo_1_) {
/* 125 */     int i = I18n.format(this.keyCategory, new Object[0]).compareTo(I18n.format(p_compareTo_1_.keyCategory, new Object[0]));
/*     */     
/* 127 */     if (i == 0) {
/* 128 */       i = I18n.format(this.keyDescription, new Object[0]).compareTo(I18n.format(p_compareTo_1_.keyDescription, new Object[0]));
/*     */     }
/*     */     
/* 131 */     return i;
/*     */   }
/*     */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\minecraft\client\settings\KeyBinding.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */