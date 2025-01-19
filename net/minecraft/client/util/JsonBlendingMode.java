/*     */ package net.minecraft.client.util;
/*     */ 
/*     */ import com.google.gson.JsonObject;
/*     */ import net.minecraft.client.renderer.GlStateManager;
/*     */ import net.minecraft.util.JsonUtils;
/*     */ import org.lwjgl.opengl.GL14;
/*     */ 
/*     */ public class JsonBlendingMode {
/*   9 */   private static JsonBlendingMode field_148118_a = null;
/*     */   private final int field_148116_b;
/*     */   private final int field_148117_c;
/*     */   private final int field_148114_d;
/*     */   private final int field_148115_e;
/*     */   private final int field_148112_f;
/*     */   private final boolean field_148113_g;
/*     */   private final boolean field_148119_h;
/*     */   
/*     */   private JsonBlendingMode(boolean p_i45084_1_, boolean p_i45084_2_, int p_i45084_3_, int p_i45084_4_, int p_i45084_5_, int p_i45084_6_, int p_i45084_7_) {
/*  19 */     this.field_148113_g = p_i45084_1_;
/*  20 */     this.field_148116_b = p_i45084_3_;
/*  21 */     this.field_148114_d = p_i45084_4_;
/*  22 */     this.field_148117_c = p_i45084_5_;
/*  23 */     this.field_148115_e = p_i45084_6_;
/*  24 */     this.field_148119_h = p_i45084_2_;
/*  25 */     this.field_148112_f = p_i45084_7_;
/*     */   }
/*     */   
/*     */   public JsonBlendingMode() {
/*  29 */     this(false, true, 1, 0, 1, 0, 32774);
/*     */   }
/*     */   
/*     */   public JsonBlendingMode(int p_i45085_1_, int p_i45085_2_, int p_i45085_3_) {
/*  33 */     this(false, false, p_i45085_1_, p_i45085_2_, p_i45085_1_, p_i45085_2_, p_i45085_3_);
/*     */   }
/*     */   
/*     */   public JsonBlendingMode(int p_i45086_1_, int p_i45086_2_, int p_i45086_3_, int p_i45086_4_, int p_i45086_5_) {
/*  37 */     this(true, false, p_i45086_1_, p_i45086_2_, p_i45086_3_, p_i45086_4_, p_i45086_5_);
/*     */   }
/*     */   
/*     */   public void func_148109_a() {
/*  41 */     if (!equals(field_148118_a)) {
/*  42 */       if (field_148118_a == null || this.field_148119_h != field_148118_a.func_148111_b()) {
/*  43 */         field_148118_a = this;
/*     */         
/*  45 */         if (this.field_148119_h) {
/*  46 */           GlStateManager.disableBlend();
/*     */           
/*     */           return;
/*     */         } 
/*  50 */         GlStateManager.enableBlend();
/*     */       } 
/*     */       
/*  53 */       GL14.glBlendEquation(this.field_148112_f);
/*     */       
/*  55 */       if (this.field_148113_g) {
/*  56 */         GlStateManager.tryBlendFuncSeparate(this.field_148116_b, this.field_148114_d, this.field_148117_c, this.field_148115_e);
/*     */       } else {
/*  58 */         GlStateManager.blendFunc(this.field_148116_b, this.field_148114_d);
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   public boolean equals(Object p_equals_1_) {
/*  64 */     if (this == p_equals_1_)
/*  65 */       return true; 
/*  66 */     if (!(p_equals_1_ instanceof JsonBlendingMode)) {
/*  67 */       return false;
/*     */     }
/*  69 */     JsonBlendingMode jsonblendingmode = (JsonBlendingMode)p_equals_1_;
/*  70 */     return (this.field_148112_f != jsonblendingmode.field_148112_f) ? false : ((this.field_148115_e != jsonblendingmode.field_148115_e) ? false : ((this.field_148114_d != jsonblendingmode.field_148114_d) ? false : ((this.field_148119_h != jsonblendingmode.field_148119_h) ? false : ((this.field_148113_g != jsonblendingmode.field_148113_g) ? false : ((this.field_148117_c != jsonblendingmode.field_148117_c) ? false : ((this.field_148116_b == jsonblendingmode.field_148116_b)))))));
/*     */   }
/*     */ 
/*     */   
/*     */   public int hashCode() {
/*  75 */     int i = this.field_148116_b;
/*  76 */     i = 31 * i + this.field_148117_c;
/*  77 */     i = 31 * i + this.field_148114_d;
/*  78 */     i = 31 * i + this.field_148115_e;
/*  79 */     i = 31 * i + this.field_148112_f;
/*  80 */     i = 31 * i + (this.field_148113_g ? 1 : 0);
/*  81 */     i = 31 * i + (this.field_148119_h ? 1 : 0);
/*  82 */     return i;
/*     */   }
/*     */   
/*     */   public boolean func_148111_b() {
/*  86 */     return this.field_148119_h;
/*     */   }
/*     */   
/*     */   public static JsonBlendingMode func_148110_a(JsonObject p_148110_0_) {
/*  90 */     if (p_148110_0_ == null) {
/*  91 */       return new JsonBlendingMode();
/*     */     }
/*  93 */     int i = 32774;
/*  94 */     int j = 1;
/*  95 */     int k = 0;
/*  96 */     int l = 1;
/*  97 */     int i1 = 0;
/*  98 */     boolean flag = true;
/*  99 */     boolean flag1 = false;
/*     */     
/* 101 */     if (JsonUtils.isString(p_148110_0_, "func")) {
/* 102 */       i = func_148108_a(p_148110_0_.get("func").getAsString());
/*     */       
/* 104 */       if (i != 32774) {
/* 105 */         flag = false;
/*     */       }
/*     */     } 
/*     */     
/* 109 */     if (JsonUtils.isString(p_148110_0_, "srcrgb")) {
/* 110 */       j = func_148107_b(p_148110_0_.get("srcrgb").getAsString());
/*     */       
/* 112 */       if (j != 1) {
/* 113 */         flag = false;
/*     */       }
/*     */     } 
/*     */     
/* 117 */     if (JsonUtils.isString(p_148110_0_, "dstrgb")) {
/* 118 */       k = func_148107_b(p_148110_0_.get("dstrgb").getAsString());
/*     */       
/* 120 */       if (k != 0) {
/* 121 */         flag = false;
/*     */       }
/*     */     } 
/*     */     
/* 125 */     if (JsonUtils.isString(p_148110_0_, "srcalpha")) {
/* 126 */       l = func_148107_b(p_148110_0_.get("srcalpha").getAsString());
/*     */       
/* 128 */       if (l != 1) {
/* 129 */         flag = false;
/*     */       }
/*     */       
/* 132 */       flag1 = true;
/*     */     } 
/*     */     
/* 135 */     if (JsonUtils.isString(p_148110_0_, "dstalpha")) {
/* 136 */       i1 = func_148107_b(p_148110_0_.get("dstalpha").getAsString());
/*     */       
/* 138 */       if (i1 != 0) {
/* 139 */         flag = false;
/*     */       }
/*     */       
/* 142 */       flag1 = true;
/*     */     } 
/*     */     
/* 145 */     return flag ? new JsonBlendingMode() : (flag1 ? new JsonBlendingMode(j, k, l, i1, i) : new JsonBlendingMode(j, k, i));
/*     */   }
/*     */ 
/*     */   
/*     */   private static int func_148108_a(String p_148108_0_) {
/* 150 */     String s = p_148108_0_.trim().toLowerCase();
/* 151 */     return s.equals("add") ? 32774 : (s.equals("subtract") ? 32778 : (s.equals("reversesubtract") ? 32779 : (s.equals("reverse_subtract") ? 32779 : (s.equals("min") ? 32775 : (s.equals("max") ? 32776 : 32774)))));
/*     */   }
/*     */   
/*     */   private static int func_148107_b(String p_148107_0_) {
/* 155 */     String s = p_148107_0_.trim().toLowerCase();
/* 156 */     s = s.replaceAll("_", "");
/* 157 */     s = s.replaceAll("one", "1");
/* 158 */     s = s.replaceAll("zero", "0");
/* 159 */     s = s.replaceAll("minus", "-");
/* 160 */     return s.equals("0") ? 0 : (s.equals("1") ? 1 : (s.equals("srccolor") ? 768 : (s.equals("1-srccolor") ? 769 : (s.equals("dstcolor") ? 774 : (s.equals("1-dstcolor") ? 775 : (s.equals("srcalpha") ? 770 : (s.equals("1-srcalpha") ? 771 : (s.equals("dstalpha") ? 772 : (s.equals("1-dstalpha") ? 773 : -1)))))))));
/*     */   }
/*     */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\minecraft\clien\\util\JsonBlendingMode.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */