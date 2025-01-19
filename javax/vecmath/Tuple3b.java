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
/*     */ public abstract class Tuple3b
/*     */   implements Serializable, Cloneable
/*     */ {
/*     */   static final long serialVersionUID = -483782685323607044L;
/*     */   public byte x;
/*     */   public byte y;
/*     */   public byte z;
/*     */   
/*     */   public Tuple3b(byte b1, byte b2, byte b3) {
/*  72 */     this.x = b1;
/*  73 */     this.y = b2;
/*  74 */     this.z = b3;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Tuple3b(byte[] t) {
/*  84 */     this.x = t[0];
/*  85 */     this.y = t[1];
/*  86 */     this.z = t[2];
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Tuple3b(Tuple3b t1) {
/*  96 */     this.x = t1.x;
/*  97 */     this.y = t1.y;
/*  98 */     this.z = t1.z;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Tuple3b() {
/* 106 */     this.x = 0;
/* 107 */     this.y = 0;
/* 108 */     this.z = 0;
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
/* 119 */     return "(" + (this.x & 0xFF) + 
/* 120 */       ", " + (this.y & 0xFF) + 
/* 121 */       ", " + (this.z & 0xFF) + ")";
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
/*     */   public final void get(byte[] t) {
/* 133 */     t[0] = this.x;
/* 134 */     t[1] = this.y;
/* 135 */     t[2] = this.z;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final void get(Tuple3b t1) {
/* 146 */     t1.x = this.x;
/* 147 */     t1.y = this.y;
/* 148 */     t1.z = this.z;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final void set(Tuple3b t1) {
/* 159 */     this.x = t1.x;
/* 160 */     this.y = t1.y;
/* 161 */     this.z = t1.z;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final void set(byte[] t) {
/* 172 */     this.x = t[0];
/* 173 */     this.y = t[1];
/* 174 */     this.z = t[2];
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
/*     */   public boolean equals(Tuple3b t1) {
/*     */     try {
/* 187 */       return (this.x == t1.x && this.y == t1.y && this.z == t1.z);
/* 188 */     } catch (NullPointerException e2) {
/* 189 */       return false;
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
/* 204 */       Tuple3b t2 = (Tuple3b)t1;
/* 205 */       return (this.x == t2.x && this.y == t2.y && this.z == t2.z);
/* 206 */     } catch (NullPointerException e2) {
/* 207 */       return false;
/* 208 */     } catch (ClassCastException e1) {
/* 209 */       return false;
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
/*     */   public int hashCode() {
/* 225 */     return (this.x & 0xFF) << 0 | (
/* 226 */       this.y & 0xFF) << 8 | (
/* 227 */       this.z & 0xFF) << 16;
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
/* 242 */       return super.clone();
/* 243 */     } catch (CloneNotSupportedException e) {
/*     */       
/* 245 */       throw new InternalError();
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
/* 257 */     return this.x;
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
/* 268 */     this.x = x;
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
/* 279 */     return this.y;
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
/* 290 */     this.y = y;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final byte getZ() {
/* 300 */     return this.z;
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
/* 311 */     this.z = z;
/*     */   }
/*     */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\javax\vecmath\Tuple3b.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */