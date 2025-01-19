/*     */ package net.optifine.shaders;
/*     */ 
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
/*     */ 
/*     */ public class Programs {
/*   7 */   private List<Program> programs = new ArrayList<>();
/*   8 */   private Program programNone = make("", ProgramStage.NONE, true);
/*     */   
/*     */   public Program make(String name, ProgramStage programStage, Program backupProgram) {
/*  11 */     int i = this.programs.size();
/*  12 */     Program program = new Program(i, name, programStage, backupProgram);
/*  13 */     this.programs.add(program);
/*  14 */     return program;
/*     */   }
/*     */   
/*     */   private Program make(String name, ProgramStage programStage, boolean ownBackup) {
/*  18 */     int i = this.programs.size();
/*  19 */     Program program = new Program(i, name, programStage, ownBackup);
/*  20 */     this.programs.add(program);
/*  21 */     return program;
/*     */   }
/*     */   
/*     */   public Program makeGbuffers(String name, Program backupProgram) {
/*  25 */     return make(name, ProgramStage.GBUFFERS, backupProgram);
/*     */   }
/*     */   
/*     */   public Program makeComposite(String name) {
/*  29 */     return make(name, ProgramStage.COMPOSITE, this.programNone);
/*     */   }
/*     */   
/*     */   public Program makeDeferred(String name) {
/*  33 */     return make(name, ProgramStage.DEFERRED, this.programNone);
/*     */   }
/*     */   
/*     */   public Program makeShadow(String name, Program backupProgram) {
/*  37 */     return make(name, ProgramStage.SHADOW, backupProgram);
/*     */   }
/*     */   
/*     */   public Program makeVirtual(String name) {
/*  41 */     return make(name, ProgramStage.NONE, true);
/*     */   }
/*     */   
/*     */   public Program[] makeComposites(String prefix, int count) {
/*  45 */     Program[] aprogram = new Program[count];
/*     */     
/*  47 */     for (int i = 0; i < count; i++) {
/*  48 */       String s = (i == 0) ? prefix : (String.valueOf(prefix) + i);
/*  49 */       aprogram[i] = makeComposite(s);
/*     */     } 
/*     */     
/*  52 */     return aprogram;
/*     */   }
/*     */   
/*     */   public Program[] makeDeferreds(String prefix, int count) {
/*  56 */     Program[] aprogram = new Program[count];
/*     */     
/*  58 */     for (int i = 0; i < count; i++) {
/*  59 */       String s = (i == 0) ? prefix : (String.valueOf(prefix) + i);
/*  60 */       aprogram[i] = makeDeferred(s);
/*     */     } 
/*     */     
/*  63 */     return aprogram;
/*     */   }
/*     */   
/*     */   public Program getProgramNone() {
/*  67 */     return this.programNone;
/*     */   }
/*     */   
/*     */   public int getCount() {
/*  71 */     return this.programs.size();
/*     */   }
/*     */   
/*     */   public Program getProgram(String name) {
/*  75 */     if (name == null) {
/*  76 */       return null;
/*     */     }
/*  78 */     for (int i = 0; i < this.programs.size(); i++) {
/*  79 */       Program program = this.programs.get(i);
/*  80 */       String s = program.getName();
/*     */       
/*  82 */       if (s.equals(name)) {
/*  83 */         return program;
/*     */       }
/*     */     } 
/*     */     
/*  87 */     return null;
/*     */   }
/*     */ 
/*     */   
/*     */   public String[] getProgramNames() {
/*  92 */     String[] astring = new String[this.programs.size()];
/*     */     
/*  94 */     for (int i = 0; i < astring.length; i++) {
/*  95 */       astring[i] = ((Program)this.programs.get(i)).getName();
/*     */     }
/*     */     
/*  98 */     return astring;
/*     */   }
/*     */   
/*     */   public Program[] getPrograms() {
/* 102 */     Program[] aprogram = this.programs.<Program>toArray(new Program[this.programs.size()]);
/* 103 */     return aprogram;
/*     */   }
/*     */   
/*     */   public Program[] getPrograms(Program programFrom, Program programTo) {
/* 107 */     int i = programFrom.getIndex();
/* 108 */     int j = programTo.getIndex();
/*     */     
/* 110 */     if (i > j) {
/* 111 */       int k = i;
/* 112 */       i = j;
/* 113 */       j = k;
/*     */     } 
/*     */     
/* 116 */     Program[] aprogram = new Program[j - i + 1];
/*     */     
/* 118 */     for (int l = 0; l < aprogram.length; l++) {
/* 119 */       aprogram[l] = this.programs.get(i + l);
/*     */     }
/*     */     
/* 122 */     return aprogram;
/*     */   }
/*     */   
/*     */   public String toString() {
/* 126 */     return this.programs.toString();
/*     */   }
/*     */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\optifine\shaders\Programs.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */