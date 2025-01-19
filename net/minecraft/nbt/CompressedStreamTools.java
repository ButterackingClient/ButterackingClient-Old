/*     */ package net.minecraft.nbt;
/*     */ 
/*     */ import java.io.BufferedInputStream;
/*     */ import java.io.BufferedOutputStream;
/*     */ import java.io.DataInput;
/*     */ import java.io.DataInputStream;
/*     */ import java.io.DataOutput;
/*     */ import java.io.DataOutputStream;
/*     */ import java.io.File;
/*     */ import java.io.FileInputStream;
/*     */ import java.io.FileOutputStream;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import java.io.OutputStream;
/*     */ import java.util.zip.GZIPInputStream;
/*     */ import java.util.zip.GZIPOutputStream;
/*     */ import net.minecraft.crash.CrashReport;
/*     */ import net.minecraft.crash.CrashReportCategory;
/*     */ import net.minecraft.util.ReportedException;
/*     */ 
/*     */ 
/*     */ 
/*     */ public class CompressedStreamTools
/*     */ {
/*     */   public static NBTTagCompound readCompressed(InputStream is) throws IOException {
/*     */     NBTTagCompound nbttagcompound;
/*  27 */     DataInputStream datainputstream = new DataInputStream(new BufferedInputStream(new GZIPInputStream(is)));
/*     */ 
/*     */     
/*     */     try {
/*  31 */       nbttagcompound = read(datainputstream, NBTSizeTracker.INFINITE);
/*     */     } finally {
/*  33 */       datainputstream.close();
/*     */     } 
/*     */     
/*  36 */     return nbttagcompound;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void writeCompressed(NBTTagCompound p_74799_0_, OutputStream outputStream) throws IOException {
/*  43 */     DataOutputStream dataoutputstream = new DataOutputStream(new BufferedOutputStream(new GZIPOutputStream(outputStream)));
/*     */     
/*     */     try {
/*  46 */       write(p_74799_0_, dataoutputstream);
/*     */     } finally {
/*  48 */       dataoutputstream.close();
/*     */     } 
/*     */   }
/*     */   
/*     */   public static void safeWrite(NBTTagCompound p_74793_0_, File p_74793_1_) throws IOException {
/*  53 */     File file1 = new File(String.valueOf(p_74793_1_.getAbsolutePath()) + "_tmp");
/*     */     
/*  55 */     if (file1.exists()) {
/*  56 */       file1.delete();
/*     */     }
/*     */     
/*  59 */     write(p_74793_0_, file1);
/*     */     
/*  61 */     if (p_74793_1_.exists()) {
/*  62 */       p_74793_1_.delete();
/*     */     }
/*     */     
/*  65 */     if (p_74793_1_.exists()) {
/*  66 */       throw new IOException("Failed to delete " + p_74793_1_);
/*     */     }
/*  68 */     file1.renameTo(p_74793_1_);
/*     */   }
/*     */ 
/*     */   
/*     */   public static void write(NBTTagCompound p_74795_0_, File p_74795_1_) throws IOException {
/*  73 */     DataOutputStream dataoutputstream = new DataOutputStream(new FileOutputStream(p_74795_1_));
/*     */     
/*     */     try {
/*  76 */       write(p_74795_0_, dataoutputstream);
/*     */     } finally {
/*  78 */       dataoutputstream.close();
/*     */     } 
/*     */   }
/*     */   public static NBTTagCompound read(File p_74797_0_) throws IOException {
/*     */     NBTTagCompound nbttagcompound;
/*  83 */     if (!p_74797_0_.exists()) {
/*  84 */       return null;
/*     */     }
/*  86 */     DataInputStream datainputstream = new DataInputStream(new FileInputStream(p_74797_0_));
/*     */ 
/*     */     
/*     */     try {
/*  90 */       nbttagcompound = read(datainputstream, NBTSizeTracker.INFINITE);
/*     */     } finally {
/*  92 */       datainputstream.close();
/*     */     } 
/*     */     
/*  95 */     return nbttagcompound;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static NBTTagCompound read(DataInputStream inputStream) throws IOException {
/* 103 */     return read(inputStream, NBTSizeTracker.INFINITE);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static NBTTagCompound read(DataInput p_152456_0_, NBTSizeTracker p_152456_1_) throws IOException {
/* 110 */     NBTBase nbtbase = func_152455_a(p_152456_0_, 0, p_152456_1_);
/*     */     
/* 112 */     if (nbtbase instanceof NBTTagCompound) {
/* 113 */       return (NBTTagCompound)nbtbase;
/*     */     }
/* 115 */     throw new IOException("Root tag must be a named compound tag");
/*     */   }
/*     */ 
/*     */   
/*     */   public static void write(NBTTagCompound p_74800_0_, DataOutput p_74800_1_) throws IOException {
/* 120 */     writeTag(p_74800_0_, p_74800_1_);
/*     */   }
/*     */   
/*     */   private static void writeTag(NBTBase p_150663_0_, DataOutput p_150663_1_) throws IOException {
/* 124 */     p_150663_1_.writeByte(p_150663_0_.getId());
/*     */     
/* 126 */     if (p_150663_0_.getId() != 0) {
/* 127 */       p_150663_1_.writeUTF("");
/* 128 */       p_150663_0_.write(p_150663_1_);
/*     */     } 
/*     */   }
/*     */   
/*     */   private static NBTBase func_152455_a(DataInput p_152455_0_, int p_152455_1_, NBTSizeTracker p_152455_2_) throws IOException {
/* 133 */     byte b0 = p_152455_0_.readByte();
/*     */     
/* 135 */     if (b0 == 0) {
/* 136 */       return new NBTTagEnd();
/*     */     }
/* 138 */     p_152455_0_.readUTF();
/* 139 */     NBTBase nbtbase = NBTBase.createNewByType(b0);
/*     */     
/*     */     try {
/* 142 */       nbtbase.read(p_152455_0_, p_152455_1_, p_152455_2_);
/* 143 */       return nbtbase;
/* 144 */     } catch (IOException ioexception) {
/* 145 */       CrashReport crashreport = CrashReport.makeCrashReport(ioexception, "Loading NBT data");
/* 146 */       CrashReportCategory crashreportcategory = crashreport.makeCategory("NBT Tag");
/* 147 */       crashreportcategory.addCrashSection("Tag name", "[UNNAMED TAG]");
/* 148 */       crashreportcategory.addCrashSection("Tag type", Byte.valueOf(b0));
/* 149 */       throw new ReportedException(crashreport);
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\minecraft\nbt\CompressedStreamTools.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */