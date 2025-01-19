/*     */ package net.optifine.shaders;
/*     */ 
/*     */ import java.nio.IntBuffer;
/*     */ import java.util.Arrays;
/*     */ import net.optifine.render.GlAlphaState;
/*     */ import net.optifine.render.GlBlendState;
/*     */ import net.optifine.shaders.config.RenderScale;
/*     */ 
/*     */ public class Program
/*     */ {
/*     */   private final int index;
/*     */   private final String name;
/*     */   private final ProgramStage programStage;
/*     */   private final Program programBackup;
/*     */   private GlAlphaState alphaState;
/*     */   private GlBlendState blendState;
/*     */   private RenderScale renderScale;
/*  18 */   private final Boolean[] buffersFlip = new Boolean[8];
/*     */   private int id;
/*     */   private int ref;
/*     */   private String drawBufSettings;
/*     */   private IntBuffer drawBuffers;
/*     */   private IntBuffer drawBuffersBuffer;
/*     */   private int compositeMipmapSetting;
/*     */   private int countInstances;
/*  26 */   private final boolean[] toggleColorTextures = new boolean[8];
/*     */   
/*     */   public Program(int index, String name, ProgramStage programStage, Program programBackup) {
/*  29 */     this.index = index;
/*  30 */     this.name = name;
/*  31 */     this.programStage = programStage;
/*  32 */     this.programBackup = programBackup;
/*     */   }
/*     */   
/*     */   public Program(int index, String name, ProgramStage programStage, boolean ownBackup) {
/*  36 */     this.index = index;
/*  37 */     this.name = name;
/*  38 */     this.programStage = programStage;
/*  39 */     this.programBackup = ownBackup ? this : null;
/*     */   }
/*     */   
/*     */   public void resetProperties() {
/*  43 */     this.alphaState = null;
/*  44 */     this.blendState = null;
/*  45 */     this.renderScale = null;
/*  46 */     Arrays.fill((Object[])this.buffersFlip, (Object)null);
/*     */   }
/*     */   
/*     */   public void resetId() {
/*  50 */     this.id = 0;
/*  51 */     this.ref = 0;
/*     */   }
/*     */   
/*     */   public void resetConfiguration() {
/*  55 */     this.drawBufSettings = null;
/*  56 */     this.compositeMipmapSetting = 0;
/*  57 */     this.countInstances = 0;
/*     */     
/*  59 */     if (this.drawBuffersBuffer == null) {
/*  60 */       this.drawBuffersBuffer = Shaders.nextIntBuffer(8);
/*     */     }
/*     */   }
/*     */   
/*     */   public void copyFrom(Program p) {
/*  65 */     this.id = p.getId();
/*  66 */     this.alphaState = p.getAlphaState();
/*  67 */     this.blendState = p.getBlendState();
/*  68 */     this.renderScale = p.getRenderScale();
/*  69 */     System.arraycopy(p.getBuffersFlip(), 0, this.buffersFlip, 0, this.buffersFlip.length);
/*  70 */     this.drawBufSettings = p.getDrawBufSettings();
/*  71 */     this.drawBuffers = p.getDrawBuffers();
/*  72 */     this.compositeMipmapSetting = p.getCompositeMipmapSetting();
/*  73 */     this.countInstances = p.getCountInstances();
/*  74 */     System.arraycopy(p.getToggleColorTextures(), 0, this.toggleColorTextures, 0, this.toggleColorTextures.length);
/*     */   }
/*     */   
/*     */   public int getIndex() {
/*  78 */     return this.index;
/*     */   }
/*     */   
/*     */   public String getName() {
/*  82 */     return this.name;
/*     */   }
/*     */   
/*     */   public ProgramStage getProgramStage() {
/*  86 */     return this.programStage;
/*     */   }
/*     */   
/*     */   public Program getProgramBackup() {
/*  90 */     return this.programBackup;
/*     */   }
/*     */   
/*     */   public int getId() {
/*  94 */     return this.id;
/*     */   }
/*     */   
/*     */   public int getRef() {
/*  98 */     return this.ref;
/*     */   }
/*     */   
/*     */   public String getDrawBufSettings() {
/* 102 */     return this.drawBufSettings;
/*     */   }
/*     */   
/*     */   public IntBuffer getDrawBuffers() {
/* 106 */     return this.drawBuffers;
/*     */   }
/*     */   
/*     */   public IntBuffer getDrawBuffersBuffer() {
/* 110 */     return this.drawBuffersBuffer;
/*     */   }
/*     */   
/*     */   public int getCompositeMipmapSetting() {
/* 114 */     return this.compositeMipmapSetting;
/*     */   }
/*     */   
/*     */   public int getCountInstances() {
/* 118 */     return this.countInstances;
/*     */   }
/*     */   
/*     */   public GlAlphaState getAlphaState() {
/* 122 */     return this.alphaState;
/*     */   }
/*     */   
/*     */   public GlBlendState getBlendState() {
/* 126 */     return this.blendState;
/*     */   }
/*     */   
/*     */   public RenderScale getRenderScale() {
/* 130 */     return this.renderScale;
/*     */   }
/*     */   
/*     */   public Boolean[] getBuffersFlip() {
/* 134 */     return this.buffersFlip;
/*     */   }
/*     */   
/*     */   public boolean[] getToggleColorTextures() {
/* 138 */     return this.toggleColorTextures;
/*     */   }
/*     */   
/*     */   public void setId(int id) {
/* 142 */     this.id = id;
/*     */   }
/*     */   
/*     */   public void setRef(int ref) {
/* 146 */     this.ref = ref;
/*     */   }
/*     */   
/*     */   public void setDrawBufSettings(String drawBufSettings) {
/* 150 */     this.drawBufSettings = drawBufSettings;
/*     */   }
/*     */   
/*     */   public void setDrawBuffers(IntBuffer drawBuffers) {
/* 154 */     this.drawBuffers = drawBuffers;
/*     */   }
/*     */   
/*     */   public void setCompositeMipmapSetting(int compositeMipmapSetting) {
/* 158 */     this.compositeMipmapSetting = compositeMipmapSetting;
/*     */   }
/*     */   
/*     */   public void setCountInstances(int countInstances) {
/* 162 */     this.countInstances = countInstances;
/*     */   }
/*     */   
/*     */   public void setAlphaState(GlAlphaState alphaState) {
/* 166 */     this.alphaState = alphaState;
/*     */   }
/*     */   
/*     */   public void setBlendState(GlBlendState blendState) {
/* 170 */     this.blendState = blendState;
/*     */   }
/*     */   
/*     */   public void setRenderScale(RenderScale renderScale) {
/* 174 */     this.renderScale = renderScale;
/*     */   }
/*     */   
/*     */   public String getRealProgramName() {
/* 178 */     if (this.id == 0) {
/* 179 */       return "none";
/*     */     }
/*     */ 
/*     */     
/* 183 */     for (Program program = this; program.getRef() != this.id; program = program.getProgramBackup()) {
/* 184 */       if (program.getProgramBackup() == null || program.getProgramBackup() == program) {
/* 185 */         return "unknown";
/*     */       }
/*     */     } 
/*     */     
/* 189 */     return program.getName();
/*     */   }
/*     */ 
/*     */   
/*     */   public String toString() {
/* 194 */     return "name: " + this.name + ", id: " + this.id + ", ref: " + this.ref + ", real: " + getRealProgramName();
/*     */   }
/*     */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\optifine\shaders\Program.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */