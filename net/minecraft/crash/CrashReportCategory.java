/*     */ package net.minecraft.crash;
/*     */ 
/*     */ import com.google.common.collect.Lists;
/*     */ import java.util.List;
/*     */ import java.util.concurrent.Callable;
/*     */ import net.minecraft.block.Block;
/*     */ import net.minecraft.block.state.IBlockState;
/*     */ import net.minecraft.util.BlockPos;
/*     */ 
/*     */ 
/*     */ public class CrashReportCategory
/*     */ {
/*     */   private final CrashReport crashReport;
/*     */   private final String name;
/*  15 */   private final List<Entry> children = Lists.newArrayList();
/*  16 */   private StackTraceElement[] stackTrace = new StackTraceElement[0];
/*     */   
/*     */   public CrashReportCategory(CrashReport report, String name) {
/*  19 */     this.crashReport = report;
/*  20 */     this.name = name;
/*     */   }
/*     */   
/*     */   public static String getCoordinateInfo(double x, double y, double z) {
/*  24 */     return String.format("%.2f,%.2f,%.2f - %s", new Object[] { Double.valueOf(x), Double.valueOf(y), Double.valueOf(z), getCoordinateInfo(new BlockPos(x, y, z)) });
/*     */   }
/*     */   
/*     */   public static String getCoordinateInfo(BlockPos pos) {
/*  28 */     int i = pos.getX();
/*  29 */     int j = pos.getY();
/*  30 */     int k = pos.getZ();
/*  31 */     StringBuilder stringbuilder = new StringBuilder();
/*     */     
/*     */     try {
/*  34 */       stringbuilder.append(String.format("World: (%d,%d,%d)", new Object[] { Integer.valueOf(i), Integer.valueOf(j), Integer.valueOf(k) }));
/*  35 */     } catch (Throwable var17) {
/*  36 */       stringbuilder.append("(Error finding world loc)");
/*     */     } 
/*     */     
/*  39 */     stringbuilder.append(", ");
/*     */     
/*     */     try {
/*  42 */       int l = i >> 4;
/*  43 */       int i1 = k >> 4;
/*  44 */       int j1 = i & 0xF;
/*  45 */       int k1 = j >> 4;
/*  46 */       int l1 = k & 0xF;
/*  47 */       int i2 = l << 4;
/*  48 */       int j2 = i1 << 4;
/*  49 */       int k2 = (l + 1 << 4) - 1;
/*  50 */       int l2 = (i1 + 1 << 4) - 1;
/*  51 */       stringbuilder.append(String.format("Chunk: (at %d,%d,%d in %d,%d; contains blocks %d,0,%d to %d,255,%d)", new Object[] { Integer.valueOf(j1), Integer.valueOf(k1), Integer.valueOf(l1), Integer.valueOf(l), Integer.valueOf(i1), Integer.valueOf(i2), Integer.valueOf(j2), Integer.valueOf(k2), Integer.valueOf(l2) }));
/*  52 */     } catch (Throwable var16) {
/*  53 */       stringbuilder.append("(Error finding chunk loc)");
/*     */     } 
/*     */     
/*  56 */     stringbuilder.append(", ");
/*     */     
/*     */     try {
/*  59 */       int j3 = i >> 9;
/*  60 */       int k3 = k >> 9;
/*  61 */       int l3 = j3 << 5;
/*  62 */       int i4 = k3 << 5;
/*  63 */       int j4 = (j3 + 1 << 5) - 1;
/*  64 */       int k4 = (k3 + 1 << 5) - 1;
/*  65 */       int l4 = j3 << 9;
/*  66 */       int i5 = k3 << 9;
/*  67 */       int j5 = (j3 + 1 << 9) - 1;
/*  68 */       int i3 = (k3 + 1 << 9) - 1;
/*  69 */       stringbuilder.append(String.format("Region: (%d,%d; contains chunks %d,%d to %d,%d, blocks %d,0,%d to %d,255,%d)", new Object[] { Integer.valueOf(j3), Integer.valueOf(k3), Integer.valueOf(l3), Integer.valueOf(i4), Integer.valueOf(j4), Integer.valueOf(k4), Integer.valueOf(l4), Integer.valueOf(i5), Integer.valueOf(j5), Integer.valueOf(i3) }));
/*  70 */     } catch (Throwable var15) {
/*  71 */       stringbuilder.append("(Error finding world loc)");
/*     */     } 
/*     */     
/*  74 */     return stringbuilder.toString();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void addCrashSectionCallable(String sectionName, Callable<String> callable) {
/*     */     try {
/*  82 */       addCrashSection(sectionName, callable.call());
/*  83 */     } catch (Throwable throwable) {
/*  84 */       addCrashSectionThrowable(sectionName, throwable);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void addCrashSection(String sectionName, Object value) {
/*  92 */     this.children.add(new Entry(sectionName, value));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void addCrashSectionThrowable(String sectionName, Throwable throwable) {
/*  99 */     addCrashSection(sectionName, throwable);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getPrunedStackTrace(int size) {
/* 107 */     StackTraceElement[] astacktraceelement = Thread.currentThread().getStackTrace();
/*     */     
/* 109 */     if (astacktraceelement.length <= 0) {
/* 110 */       return 0;
/*     */     }
/* 112 */     this.stackTrace = new StackTraceElement[astacktraceelement.length - 3 - size];
/* 113 */     System.arraycopy(astacktraceelement, 3 + size, this.stackTrace, 0, this.stackTrace.length);
/* 114 */     return this.stackTrace.length;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean firstTwoElementsOfStackTraceMatch(StackTraceElement s1, StackTraceElement s2) {
/* 122 */     if (this.stackTrace.length != 0 && s1 != null) {
/* 123 */       StackTraceElement stacktraceelement = this.stackTrace[0];
/*     */       
/* 125 */       if (stacktraceelement.isNativeMethod() == s1.isNativeMethod() && stacktraceelement.getClassName().equals(s1.getClassName()) && stacktraceelement.getFileName().equals(s1.getFileName()) && stacktraceelement.getMethodName().equals(s1.getMethodName())) {
/* 126 */         if (((s2 != null) ? true : false) != ((this.stackTrace.length > 1) ? true : false))
/* 127 */           return false; 
/* 128 */         if (s2 != null && !this.stackTrace[1].equals(s2)) {
/* 129 */           return false;
/*     */         }
/* 131 */         this.stackTrace[0] = s1;
/* 132 */         return true;
/*     */       } 
/*     */       
/* 135 */       return false;
/*     */     } 
/*     */     
/* 138 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void trimStackTraceEntriesFromBottom(int amount) {
/* 146 */     StackTraceElement[] astacktraceelement = new StackTraceElement[this.stackTrace.length - amount];
/* 147 */     System.arraycopy(this.stackTrace, 0, astacktraceelement, 0, astacktraceelement.length);
/* 148 */     this.stackTrace = astacktraceelement;
/*     */   }
/*     */   
/*     */   public void appendToStringBuilder(StringBuilder builder) {
/* 152 */     builder.append("-- ").append(this.name).append(" --\n");
/* 153 */     builder.append("Details:");
/*     */     
/* 155 */     for (Entry crashreportcategory$entry : this.children) {
/* 156 */       builder.append("\n\t");
/* 157 */       builder.append(crashreportcategory$entry.getKey());
/* 158 */       builder.append(": ");
/* 159 */       builder.append(crashreportcategory$entry.getValue());
/*     */     } 
/*     */     
/* 162 */     if (this.stackTrace != null && this.stackTrace.length > 0) {
/* 163 */       builder.append("\nStacktrace:"); byte b; int i;
/*     */       StackTraceElement[] arrayOfStackTraceElement;
/* 165 */       for (i = (arrayOfStackTraceElement = this.stackTrace).length, b = 0; b < i; ) { StackTraceElement stacktraceelement = arrayOfStackTraceElement[b];
/* 166 */         builder.append("\n\tat ");
/* 167 */         builder.append(stacktraceelement.toString());
/*     */         b++; }
/*     */     
/*     */     } 
/*     */   }
/*     */   public StackTraceElement[] getStackTrace() {
/* 173 */     return this.stackTrace;
/*     */   }
/*     */   
/*     */   public static void addBlockInfo(CrashReportCategory category, final BlockPos pos, final Block blockIn, final int blockData) {
/* 177 */     final int i = Block.getIdFromBlock(blockIn);
/* 178 */     category.addCrashSectionCallable("Block type", new Callable<String>() {
/*     */           public String call() throws Exception {
/*     */             try {
/* 181 */               return String.format("ID #%d (%s // %s)", new Object[] { Integer.valueOf(this.val$i), this.val$blockIn.getUnlocalizedName(), this.val$blockIn.getClass().getCanonicalName() });
/* 182 */             } catch (Throwable var2) {
/* 183 */               return "ID #" + i;
/*     */             } 
/*     */           }
/*     */         });
/* 187 */     category.addCrashSectionCallable("Block data value", new Callable<String>() {
/*     */           public String call() throws Exception {
/* 189 */             if (blockData < 0) {
/* 190 */               return "Unknown? (Got " + blockData + ")";
/*     */             }
/* 192 */             String s = String.format("%4s", new Object[] { Integer.toBinaryString(this.val$blockData) }).replace(" ", "0");
/* 193 */             return String.format("%1$d / 0x%1$X / 0b%2$s", new Object[] { Integer.valueOf(this.val$blockData), s });
/*     */           }
/*     */         });
/*     */     
/* 197 */     category.addCrashSectionCallable("Block location", new Callable<String>() {
/*     */           public String call() throws Exception {
/* 199 */             return CrashReportCategory.getCoordinateInfo(pos);
/*     */           }
/*     */         });
/*     */   }
/*     */   
/*     */   public static void addBlockInfo(CrashReportCategory category, final BlockPos pos, final IBlockState state) {
/* 205 */     category.addCrashSectionCallable("Block", new Callable<String>() {
/*     */           public String call() throws Exception {
/* 207 */             return state.toString();
/*     */           }
/*     */         });
/* 210 */     category.addCrashSectionCallable("Block location", new Callable<String>() {
/*     */           public String call() throws Exception {
/* 212 */             return CrashReportCategory.getCoordinateInfo(pos);
/*     */           }
/*     */         });
/*     */   }
/*     */   
/*     */   static class Entry {
/*     */     private final String key;
/*     */     private final String value;
/*     */     
/*     */     public Entry(String key, Object value) {
/* 222 */       this.key = key;
/*     */       
/* 224 */       if (value == null) {
/* 225 */         this.value = "~~NULL~~";
/* 226 */       } else if (value instanceof Throwable) {
/* 227 */         Throwable throwable = (Throwable)value;
/* 228 */         this.value = "~~ERROR~~ " + throwable.getClass().getSimpleName() + ": " + throwable.getMessage();
/*     */       } else {
/* 230 */         this.value = value.toString();
/*     */       } 
/*     */     }
/*     */     
/*     */     public String getKey() {
/* 235 */       return this.key;
/*     */     }
/*     */     
/*     */     public String getValue() {
/* 239 */       return this.value;
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\minecraft\crash\CrashReportCategory.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */