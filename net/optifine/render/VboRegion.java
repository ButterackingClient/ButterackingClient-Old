/*     */ package net.optifine.render;
/*     */ 
/*     */ import java.nio.ByteBuffer;
/*     */ import java.nio.IntBuffer;
/*     */ import net.minecraft.client.renderer.GlStateManager;
/*     */ import net.minecraft.client.renderer.OpenGlHelper;
/*     */ import net.minecraft.client.renderer.VboRenderList;
/*     */ import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
/*     */ import net.minecraft.src.Config;
/*     */ import net.minecraft.util.EnumWorldBlockLayer;
/*     */ import net.optifine.util.LinkedList;
/*     */ 
/*     */ public class VboRegion
/*     */ {
/*  15 */   private EnumWorldBlockLayer layer = null;
/*  16 */   private int glBufferId = OpenGlHelper.glGenBuffers();
/*  17 */   private int capacity = 4096;
/*  18 */   private int positionTop = 0;
/*     */   private int sizeUsed;
/*  20 */   private LinkedList<VboRange> rangeList = new LinkedList();
/*  21 */   private VboRange compactRangeLast = null;
/*     */   private IntBuffer bufferIndexVertex;
/*     */   private IntBuffer bufferCountVertex;
/*     */   private int drawMode;
/*     */   private final int vertexBytes;
/*     */   
/*     */   public VboRegion(EnumWorldBlockLayer layer) {
/*  28 */     this.bufferIndexVertex = Config.createDirectIntBuffer(this.capacity);
/*  29 */     this.bufferCountVertex = Config.createDirectIntBuffer(this.capacity);
/*  30 */     this.drawMode = 7;
/*  31 */     this.vertexBytes = DefaultVertexFormats.BLOCK.getNextOffset();
/*  32 */     this.layer = layer;
/*  33 */     bindBuffer();
/*  34 */     long i = toBytes(this.capacity);
/*  35 */     OpenGlHelper.glBufferData(OpenGlHelper.GL_ARRAY_BUFFER, i, OpenGlHelper.GL_STATIC_DRAW);
/*  36 */     unbindBuffer();
/*     */   }
/*     */   
/*     */   public void bufferData(ByteBuffer data, VboRange range) {
/*  40 */     int i = range.getPosition();
/*  41 */     int j = range.getSize();
/*  42 */     int k = toVertex(data.limit());
/*     */     
/*  44 */     if (k <= 0) {
/*  45 */       if (i >= 0) {
/*  46 */         range.setPosition(-1);
/*  47 */         range.setSize(0);
/*  48 */         this.rangeList.remove(range.getNode());
/*  49 */         this.sizeUsed -= j;
/*     */       } 
/*     */     } else {
/*  52 */       if (k > j) {
/*  53 */         range.setPosition(this.positionTop);
/*  54 */         range.setSize(k);
/*  55 */         this.positionTop += k;
/*     */         
/*  57 */         if (i >= 0) {
/*  58 */           this.rangeList.remove(range.getNode());
/*     */         }
/*     */         
/*  61 */         this.rangeList.addLast(range.getNode());
/*     */       } 
/*     */       
/*  64 */       range.setSize(k);
/*  65 */       this.sizeUsed += k - j;
/*  66 */       checkVboSize(range.getPositionNext());
/*  67 */       long l = toBytes(range.getPosition());
/*  68 */       bindBuffer();
/*  69 */       OpenGlHelper.glBufferSubData(OpenGlHelper.GL_ARRAY_BUFFER, l, data);
/*  70 */       unbindBuffer();
/*     */       
/*  72 */       if (this.positionTop > this.sizeUsed * 11 / 10) {
/*  73 */         compactRanges(1);
/*     */       }
/*     */     } 
/*     */   }
/*     */   
/*     */   private void compactRanges(int countMax) {
/*  79 */     if (!this.rangeList.isEmpty()) {
/*  80 */       VboRange vborange = this.compactRangeLast;
/*     */       
/*  82 */       if (vborange == null || !this.rangeList.contains(vborange.getNode())) {
/*  83 */         vborange = (VboRange)this.rangeList.getFirst().getItem();
/*     */       }
/*     */       
/*  86 */       int i = vborange.getPosition();
/*  87 */       VboRange vborange1 = vborange.getPrev();
/*     */       
/*  89 */       if (vborange1 == null) {
/*  90 */         i = 0;
/*     */       } else {
/*  92 */         i = vborange1.getPositionNext();
/*     */       } 
/*     */       
/*  95 */       int j = 0;
/*     */       
/*  97 */       while (vborange != null && j < countMax) {
/*  98 */         j++;
/*     */         
/* 100 */         if (vborange.getPosition() == i) {
/* 101 */           i += vborange.getSize();
/* 102 */           vborange = vborange.getNext(); continue;
/*     */         } 
/* 104 */         int k = vborange.getPosition() - i;
/*     */         
/* 106 */         if (vborange.getSize() <= k) {
/* 107 */           copyVboData(vborange.getPosition(), i, vborange.getSize());
/* 108 */           vborange.setPosition(i);
/* 109 */           i += vborange.getSize();
/* 110 */           vborange = vborange.getNext(); continue;
/*     */         } 
/* 112 */         checkVboSize(this.positionTop + vborange.getSize());
/* 113 */         copyVboData(vborange.getPosition(), this.positionTop, vborange.getSize());
/* 114 */         vborange.setPosition(this.positionTop);
/* 115 */         this.positionTop += vborange.getSize();
/* 116 */         VboRange vborange2 = vborange.getNext();
/* 117 */         this.rangeList.remove(vborange.getNode());
/* 118 */         this.rangeList.addLast(vborange.getNode());
/* 119 */         vborange = vborange2;
/*     */       } 
/*     */ 
/*     */ 
/*     */       
/* 124 */       if (vborange == null) {
/* 125 */         this.positionTop = ((VboRange)this.rangeList.getLast().getItem()).getPositionNext();
/*     */       }
/*     */       
/* 128 */       this.compactRangeLast = vborange;
/*     */     } 
/*     */   }
/*     */   
/*     */   private void checkRanges() {
/* 133 */     int i = 0;
/* 134 */     int j = 0;
/*     */     
/* 136 */     for (VboRange vborange = (VboRange)this.rangeList.getFirst().getItem(); vborange != null; vborange = vborange.getNext()) {
/* 137 */       i++;
/* 138 */       j += vborange.getSize();
/*     */       
/* 140 */       if (vborange.getPosition() < 0 || vborange.getSize() <= 0 || vborange.getPositionNext() > this.positionTop) {
/* 141 */         throw new RuntimeException("Invalid range: " + vborange);
/*     */       }
/*     */       
/* 144 */       VboRange vborange1 = vborange.getPrev();
/*     */       
/* 146 */       if (vborange1 != null && vborange.getPosition() < vborange1.getPositionNext()) {
/* 147 */         throw new RuntimeException("Invalid range: " + vborange);
/*     */       }
/*     */       
/* 150 */       VboRange vborange2 = vborange.getNext();
/*     */       
/* 152 */       if (vborange2 != null && vborange.getPositionNext() > vborange2.getPosition()) {
/* 153 */         throw new RuntimeException("Invalid range: " + vborange);
/*     */       }
/*     */     } 
/*     */     
/* 157 */     if (i != this.rangeList.getSize())
/* 158 */       throw new RuntimeException("Invalid count: " + i + " <> " + this.rangeList.getSize()); 
/* 159 */     if (j != this.sizeUsed) {
/* 160 */       throw new RuntimeException("Invalid size: " + j + " <> " + this.sizeUsed);
/*     */     }
/*     */   }
/*     */   
/*     */   private void checkVboSize(int sizeMin) {
/* 165 */     if (this.capacity < sizeMin) {
/* 166 */       expandVbo(sizeMin);
/*     */     }
/*     */   }
/*     */   
/*     */   private void copyVboData(int posFrom, int posTo, int size) {
/* 171 */     long i = toBytes(posFrom);
/* 172 */     long j = toBytes(posTo);
/* 173 */     long k = toBytes(size);
/* 174 */     OpenGlHelper.glBindBuffer(OpenGlHelper.GL_COPY_READ_BUFFER, this.glBufferId);
/* 175 */     OpenGlHelper.glBindBuffer(OpenGlHelper.GL_COPY_WRITE_BUFFER, this.glBufferId);
/* 176 */     OpenGlHelper.glCopyBufferSubData(OpenGlHelper.GL_COPY_READ_BUFFER, OpenGlHelper.GL_COPY_WRITE_BUFFER, i, j, k);
/* 177 */     Config.checkGlError("Copy VBO range");
/* 178 */     OpenGlHelper.glBindBuffer(OpenGlHelper.GL_COPY_READ_BUFFER, 0);
/* 179 */     OpenGlHelper.glBindBuffer(OpenGlHelper.GL_COPY_WRITE_BUFFER, 0);
/*     */   }
/*     */ 
/*     */   
/*     */   private void expandVbo(int sizeMin) {
/*     */     int i;
/* 185 */     for (i = this.capacity * 6 / 4; i < sizeMin; i = i * 6 / 4);
/*     */ 
/*     */ 
/*     */     
/* 189 */     long j = toBytes(this.capacity);
/* 190 */     long k = toBytes(i);
/* 191 */     int l = OpenGlHelper.glGenBuffers();
/* 192 */     OpenGlHelper.glBindBuffer(OpenGlHelper.GL_ARRAY_BUFFER, l);
/* 193 */     OpenGlHelper.glBufferData(OpenGlHelper.GL_ARRAY_BUFFER, k, OpenGlHelper.GL_STATIC_DRAW);
/* 194 */     Config.checkGlError("Expand VBO");
/* 195 */     OpenGlHelper.glBindBuffer(OpenGlHelper.GL_ARRAY_BUFFER, 0);
/* 196 */     OpenGlHelper.glBindBuffer(OpenGlHelper.GL_COPY_READ_BUFFER, this.glBufferId);
/* 197 */     OpenGlHelper.glBindBuffer(OpenGlHelper.GL_COPY_WRITE_BUFFER, l);
/* 198 */     OpenGlHelper.glCopyBufferSubData(OpenGlHelper.GL_COPY_READ_BUFFER, OpenGlHelper.GL_COPY_WRITE_BUFFER, 0L, 0L, j);
/* 199 */     Config.checkGlError("Copy VBO: " + k);
/* 200 */     OpenGlHelper.glBindBuffer(OpenGlHelper.GL_COPY_READ_BUFFER, 0);
/* 201 */     OpenGlHelper.glBindBuffer(OpenGlHelper.GL_COPY_WRITE_BUFFER, 0);
/* 202 */     OpenGlHelper.glDeleteBuffers(this.glBufferId);
/* 203 */     this.bufferIndexVertex = Config.createDirectIntBuffer(i);
/* 204 */     this.bufferCountVertex = Config.createDirectIntBuffer(i);
/* 205 */     this.glBufferId = l;
/* 206 */     this.capacity = i;
/*     */   }
/*     */   
/*     */   public void bindBuffer() {
/* 210 */     OpenGlHelper.glBindBuffer(OpenGlHelper.GL_ARRAY_BUFFER, this.glBufferId);
/*     */   }
/*     */   
/*     */   public void drawArrays(int drawMode, VboRange range) {
/* 214 */     if (this.drawMode != drawMode) {
/* 215 */       if (this.bufferIndexVertex.position() > 0) {
/* 216 */         throw new IllegalArgumentException("Mixed region draw modes: " + this.drawMode + " != " + drawMode);
/*     */       }
/*     */       
/* 219 */       this.drawMode = drawMode;
/*     */     } 
/*     */     
/* 222 */     this.bufferIndexVertex.put(range.getPosition());
/* 223 */     this.bufferCountVertex.put(range.getSize());
/*     */   }
/*     */   
/*     */   public void finishDraw(VboRenderList vboRenderList) {
/* 227 */     bindBuffer();
/* 228 */     vboRenderList.setupArrayPointers();
/* 229 */     this.bufferIndexVertex.flip();
/* 230 */     this.bufferCountVertex.flip();
/* 231 */     GlStateManager.glMultiDrawArrays(this.drawMode, this.bufferIndexVertex, this.bufferCountVertex);
/* 232 */     this.bufferIndexVertex.limit(this.bufferIndexVertex.capacity());
/* 233 */     this.bufferCountVertex.limit(this.bufferCountVertex.capacity());
/*     */     
/* 235 */     if (this.positionTop > this.sizeUsed * 11 / 10) {
/* 236 */       compactRanges(1);
/*     */     }
/*     */   }
/*     */   
/*     */   public void unbindBuffer() {
/* 241 */     OpenGlHelper.glBindBuffer(OpenGlHelper.GL_ARRAY_BUFFER, 0);
/*     */   }
/*     */   
/*     */   public void deleteGlBuffers() {
/* 245 */     if (this.glBufferId >= 0) {
/* 246 */       OpenGlHelper.glDeleteBuffers(this.glBufferId);
/* 247 */       this.glBufferId = -1;
/*     */     } 
/*     */   }
/*     */   
/*     */   private long toBytes(int vertex) {
/* 252 */     return vertex * this.vertexBytes;
/*     */   }
/*     */   
/*     */   private int toVertex(long bytes) {
/* 256 */     return (int)(bytes / this.vertexBytes);
/*     */   }
/*     */   
/*     */   public int getPositionTop() {
/* 260 */     return this.positionTop;
/*     */   }
/*     */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\optifine\render\VboRegion.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */