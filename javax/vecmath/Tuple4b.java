/*     */ package javax.vecmath;
/*     */ 
/*     */ import java.io.Serializable;
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
/*     */ public abstract class Tuple4b
/*     */   implements Serializable, Cloneable
/*     */ {
/*     */   static final long serialVersionUID = -8226727741811898211L;
/*     */   public byte x;
/*     */   public byte y;
/*     */   public byte z;
/*     */   public byte w;
/*     */   
/*     */   public Tuple4b(byte b1, byte b2, byte b3, byte b4) {
/*  78 */     this.x = b1;
/*  79 */     this.y = b2;
/*  80 */     this.z = b3;
/*  81 */     this.w = b4;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Tuple4b(byte[] t) {
/*  91 */     this.x = t[0];
/*  92 */     this.y = t[1];
/*  93 */     this.z = t[2];
/*  94 */     this.w = t[3];
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Tuple4b(Tuple4b t1) {
/* 104 */     this.x = t1.x;
/* 105 */     this.y = t1.y;
/* 106 */     this.z = t1.z;
/* 107 */     this.w = t1.w;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Tuple4b() {
/* 115 */     this.x = 0;
/* 116 */     this.y = 0;
/* 117 */     this.z = 0;
/* 118 */     this.w = 0;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String toString() {
/* 129 */     return "(" + (this.x & 0xFF) + 
/* 130 */       ", " + (this.y & 0xFF) + 
/* 131 */       ", " + (this.z & 0xFF) + 
/* 132 */       ", " + (this.w & 0xFF) + ")";
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final void get(byte[] b) {
/* 143 */     b[0] = this.x;
/* 144 */     b[1] = this.y;
/* 145 */     b[2] = this.z;
/* 146 */     b[3] = this.w;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final void get(Tuple4b t1) {
/* 157 */     t1.x = this.x;
/* 158 */     t1.y = this.y;
/* 159 */     t1.z = this.z;
/* 160 */     t1.w = this.w;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final void set(Tuple4b t1) {
/* 171 */     this.x = t1.x;
/* 172 */     this.y = t1.y;
/* 173 */     this.z = t1.z;
/* 174 */     this.w = t1.w;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final void set(byte[] b) {
/* 185 */     this.x = b[0];
/* 186 */     this.y = b[1];
/* 187 */     this.z = b[2];
/* 188 */     this.w = b[3];
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean equals(Tuple4b t1) {
/*     */     try {
/* 200 */       return (this.x == t1.x && this.y == t1.y && 
/* 201 */         this.z == t1.z && this.w == t1.w);
/* 202 */     } catch (NullPointerException e2) {
/* 203 */       return false;
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
/*     */   public boolean equals(Object t1) {
/*     */     try {
/* 218 */       Tuple4b t2 = (Tuple4b)t1;
/* 219 */       return (this.x == t2.x && this.y == t2.y && 
/* 220 */         this.z == t2.z && this.w == t2.w);
/* 221 */     } catch (NullPointerException e2) {
/* 222 */       return false;
/* 223 */     } catch (ClassCastException e1) {
/* 224 */       return false;
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
/*     */   public int hashCode() {
/* 241 */     return (this.x & 0xFF) << 0 | (
/* 242 */       this.y & 0xFF) << 8 | (
/* 243 */       this.z & 0xFF) << 16 | (
/* 244 */       this.w & 0xFF) << 24;
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
/*     */   public Object clone() {
/*     */     try {
/* 259 */       return super.clone();
/* 260 */     } catch (CloneNotSupportedException e) {
/*     */       
/* 262 */       throw new InternalError();
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
/*     */   public final byte getX() {
/* 274 */     return this.x;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final void setX(byte x) {
/* 285 */     this.x = x;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final byte getY() {
/* 296 */     return this.y;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final void setY(byte y) {
/* 307 */     this.y = y;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final byte getZ() {
/* 317 */     return this.z;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final void setZ(byte z) {
/* 328 */     this.z = z;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final byte getW() {
/* 339 */     return this.w;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final void setW(byte w) {
/* 350 */     this.w = w;
/*     */   }
/*     */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\javax\vecmath\Tuple4b.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */