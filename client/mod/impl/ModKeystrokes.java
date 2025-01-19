/*     */ package client.mod.impl;
/*     */ 
/*     */ import client.Client;
/*     */ import client.hud.HudMod;
/*     */ import java.awt.Color;
/*     */ import net.minecraft.client.Minecraft;
/*     */ import net.minecraft.client.gui.Gui;
/*     */ import net.minecraft.client.settings.KeyBinding;
/*     */ import org.lwjgl.opengl.GL11;
/*     */ 
/*     */ public class ModKeystrokes
/*     */   extends HudMod
/*     */ {
/*     */   private KeystokesMode mode;
/*     */   
/*     */   public ModKeystrokes() {
/*  17 */     super("KeyStrokes", 78, 35, false);
/*  18 */     this.mode = KeystokesMode.WASD_JUMP_MOUSE;
/*     */   }
/*     */ 
/*     */   
/*     */   public int getHeight() {
/*  23 */     return 80;
/*     */   }
/*     */ 
/*     */   
/*     */   public int getWidth() {
/*  28 */     return 60;
/*     */   }
/*     */ 
/*     */   
/*     */   public void draw() {
/*  33 */     GL11.glPushMatrix();
/*     */     Key[] keys;
/*  35 */     for (int length = (keys = this.mode.getKeys()).length, i = 0; i < length; i++) {
/*  36 */       Key key = keys[i];
/*  37 */       int textWidth = fr.getStringWidth(key.getName());
/*  38 */       Gui.drawRect(x() + key.getX(), y() + key.getY(), x() + key.getX() + key.getWidth(), y() + key.getY() + key.getHeight(), key.isDown() ? (new Color(255, 255, 255, 102)).getRGB() : (new Color(0, 0, 0, 120)).getRGB());
/*  39 */       fr.drawStringWithShadow(key.getName(), (x() + key.getX() + key.getWidth() / 2 - textWidth / 2), (y() + key.getY() + key.getHeight() / 2 - 4), key.isDown() ? (new Color(0, 0, 0, 255)).getRGB() : -1);
/*     */     } 
/*  41 */     GL11.glPopMatrix();
/*     */   }
/*     */ 
/*     */   
/*     */   public void renderDummy(int mouseX, int mouseY) {
/*  46 */     GL11.glPushMatrix();
/*     */     Key[] keys;
/*  48 */     for (int length = (keys = this.mode.getKeys()).length, i = 0; i < length; i++) {
/*  49 */       Key key = keys[i];
/*  50 */       int textWidth = fr.getStringWidth(key.getName());
/*  51 */       Gui.drawRect(x() + key.getX(), y() + key.getY(), x() + key.getX() + key.getWidth(), y() + key.getY() + key.getHeight(), key.isDown() ? (new Color(255, 255, 255, 102)).getRGB() : (new Color(0, 0, 0, 120)).getRGB());
/*  52 */       fr.drawStringWithShadow(key.getName(), (x() + key.getX() + key.getWidth() / 2 - textWidth / 2), (y() + key.getY() + key.getHeight() / 2 - 4), key.isDown() ? (new Color(0, 0, 0, 255)).getRGB() : -1);
/*     */     } 
/*  54 */     GL11.glPopMatrix();
/*  55 */     super.renderDummy(mouseX, mouseY);
/*     */   }
/*     */   
/*     */   public enum KeystokesMode {
/*  59 */     WASD("WASD", 0, "WASD", 0, "WASD", 0, "WASD", 0, (String)new ModKeystrokes.Key[] { ModKeystrokes.Key.access$0(), ModKeystrokes.Key.access$1(), ModKeystrokes.Key.access$2(), ModKeystrokes.Key.access$3() }),
/*  60 */     WASD_MOUSE("WASD_MOUSE", 1, "WASD_MOUSE", 1, "WASD_MOUSE", 1, "WASD_MOUSE", 1, (String)new ModKeystrokes.Key[] { ModKeystrokes.Key.access$0(), ModKeystrokes.Key.access$1(), ModKeystrokes.Key.access$2(), ModKeystrokes.Key.access$3(), ModKeystrokes.Key.access$4(), ModKeystrokes.Key.access$5() }),
/*  61 */     WASD_JUMP("WASD_JUMP", 2, "WASD_JUMP", 2, "WASD_JUMP", 2, "WASD_JUMP", 2, (String)new ModKeystrokes.Key[] { ModKeystrokes.Key.access$0(), ModKeystrokes.Key.access$1(), ModKeystrokes.Key.access$2(), ModKeystrokes.Key.access$3(), ModKeystrokes.Key.access$6() }),
/*  62 */     WASD_JUMP_MOUSE("WASD_JUMP_MOUSE", 3, "WASD_JUMP_MOUSE", 3, "WASD_JUMP_MOUSE", 3, "WASD_JUMP_MOUSE", 3, (String)new ModKeystrokes.Key[] { ModKeystrokes.Key.access$0(), ModKeystrokes.Key.access$1(), ModKeystrokes.Key.access$2(), ModKeystrokes.Key.access$3(), ModKeystrokes.Key.access$4(), ModKeystrokes.Key.access$5(), ModKeystrokes.Key.access$7() });
/*     */     
/*     */     private final ModKeystrokes.Key[] keys;
/*     */     private int width;
/*     */     private int height;
/*     */     
/*     */     KeystokesMode(String s4, int n4, String s3, int n3, String s2, int n2, String s, int n, ModKeystrokes.Key... keysIn) {
/*  69 */       this.keys = keysIn;
/*     */       ModKeystrokes.Key[] keys;
/*  71 */       for (int length = (keys = this.keys).length, i = 0; i < length; i++) {
/*  72 */         ModKeystrokes.Key key = keys[i];
/*  73 */         this.width = Math.max(this.width, key.getX() + key.getWidth());
/*  74 */         this.height = Math.max(this.height, key.getY() + key.getHeight());
/*     */       } 
/*     */     }
/*     */     
/*     */     public int getHeight() {
/*  79 */       return this.height;
/*     */     }
/*     */     
/*     */     public int getWidth() {
/*  83 */       return this.width;
/*     */     }
/*     */     
/*     */     public ModKeystrokes.Key[] getKeys() {
/*  87 */       return this.keys;
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static class Key
/*     */   {
/* 109 */     public static Minecraft mc = Minecraft.getMinecraft();
/* 110 */     private static final Key W = new Key(String.valueOf((Client.getInstance()).mainColor) + "W", mc.gameSettings.keyBindLeft, 21, 1, 18, 18);
/* 111 */     private static final Key A = new Key(String.valueOf((Client.getInstance()).mainColor) + "A", mc.gameSettings.keyBindBack, 1, 21, 18, 18);
/* 112 */     private static final Key S = new Key(String.valueOf((Client.getInstance()).mainColor) + "S", mc.gameSettings.keyBindRight, 21, 21, 18, 18);
/* 113 */     private static final Key D = new Key(String.valueOf((Client.getInstance()).mainColor) + "D", mc.gameSettings.keyBindJump, 41, 21, 18, 18);
/* 114 */     private static final Key LMB = new Key(String.valueOf((Client.getInstance()).mainColor) + "LMB", mc.gameSettings.keyBindPickBlock, 1, 41, 28, 18);
/* 115 */     private static final Key RMB = new Key(String.valueOf((Client.getInstance()).mainColor) + "RMB", mc.gameSettings.keyBindDrop, 31, 41, 28, 18);
/* 116 */     private static final Key Jump1 = new Key(String.valueOf((Client.getInstance()).mainColor) + "----", mc.gameSettings.keyBindSneak, 1, 41, 58, 18);
/* 117 */     private static final Key Jump2 = new Key(String.valueOf((Client.getInstance()).mainColor) + "----", mc.gameSettings.keyBindSneak, 1, 61, 58, 18); private final String name; private final KeyBinding keyBind;
/*     */     private final int x;
/*     */     
/*     */     public Key(String name, KeyBinding keyBind, int x, int y, int w, int h) {
/* 121 */       this.name = name;
/* 122 */       this.keyBind = keyBind;
/* 123 */       this.x = x;
/* 124 */       this.y = y;
/* 125 */       this.w = w;
/* 126 */       this.h = h;
/*     */     }
/*     */     private final int y; private final int w; private final int h;
/*     */     public boolean isDown() {
/* 130 */       return this.keyBind.isKeyDown();
/*     */     }
/*     */     
/*     */     public int getHeight() {
/* 134 */       return this.h;
/*     */     }
/*     */     
/*     */     public int getWidth() {
/* 138 */       return this.w;
/*     */     }
/*     */     
/*     */     public String getName() {
/* 142 */       return this.name;
/*     */     }
/*     */     
/*     */     public int getX() {
/* 146 */       return this.x;
/*     */     }
/*     */     
/*     */     public int getY() {
/* 150 */       return this.y;
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\client\mod\impl\ModKeystrokes.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */