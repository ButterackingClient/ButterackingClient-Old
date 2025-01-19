/*     */ package net.minecraft.nbt;
/*     */ 
/*     */ import com.google.common.collect.Maps;
/*     */ import java.io.DataInput;
/*     */ import java.io.DataOutput;
/*     */ import java.io.IOException;
/*     */ import java.util.Map;
/*     */ import java.util.Set;
/*     */ import java.util.concurrent.Callable;
/*     */ import net.minecraft.crash.CrashReport;
/*     */ import net.minecraft.crash.CrashReportCategory;
/*     */ import net.minecraft.util.ReportedException;
/*     */ 
/*     */ public class NBTTagCompound extends NBTBase {
/*     */   private Map<String, NBTBase> tagMap;
/*     */   
/*     */   public NBTTagCompound() {
/*  18 */     this.tagMap = Maps.newHashMap();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   void write(DataOutput output) throws IOException {
/*  24 */     for (String s : this.tagMap.keySet()) {
/*  25 */       NBTBase nbtbase = this.tagMap.get(s);
/*  26 */       writeEntry(s, nbtbase, output);
/*     */     } 
/*     */     
/*  29 */     output.writeByte(0);
/*     */   }
/*     */   
/*     */   void read(DataInput input, int depth, NBTSizeTracker sizeTracker) throws IOException {
/*  33 */     sizeTracker.read(384L);
/*     */     
/*  35 */     if (depth > 512) {
/*  36 */       throw new RuntimeException("Tried to read NBT tag with too high complexity, depth > 512");
/*     */     }
/*  38 */     this.tagMap.clear();
/*     */     
/*     */     byte b0;
/*  41 */     while ((b0 = readType(input, sizeTracker)) != 0) {
/*  42 */       String s = readKey(input, sizeTracker);
/*  43 */       sizeTracker.read((224 + 16 * s.length()));
/*  44 */       NBTBase nbtbase = readNBT(b0, s, input, depth + 1, sizeTracker);
/*     */       
/*  46 */       if (this.tagMap.put(s, nbtbase) != null) {
/*  47 */         sizeTracker.read(288L);
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public Set<String> getKeySet() {
/*  54 */     return this.tagMap.keySet();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public byte getId() {
/*  61 */     return 10;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setTag(String key, NBTBase value) {
/*  68 */     this.tagMap.put(key, value);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setByte(String key, byte value) {
/*  75 */     this.tagMap.put(key, new NBTTagByte(value));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setShort(String key, short value) {
/*  82 */     this.tagMap.put(key, new NBTTagShort(value));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setInteger(String key, int value) {
/*  89 */     this.tagMap.put(key, new NBTTagInt(value));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setLong(String key, long value) {
/*  96 */     this.tagMap.put(key, new NBTTagLong(value));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setFloat(String key, float value) {
/* 103 */     this.tagMap.put(key, new NBTTagFloat(value));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setDouble(String key, double value) {
/* 110 */     this.tagMap.put(key, new NBTTagDouble(value));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setString(String key, String value) {
/* 117 */     this.tagMap.put(key, new NBTTagString(value));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setByteArray(String key, byte[] value) {
/* 124 */     this.tagMap.put(key, new NBTTagByteArray(value));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setIntArray(String key, int[] value) {
/* 131 */     this.tagMap.put(key, new NBTTagIntArray(value));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setBoolean(String key, boolean value) {
/* 138 */     setByte(key, (byte)(value ? 1 : 0));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public NBTBase getTag(String key) {
/* 145 */     return this.tagMap.get(key);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public byte getTagId(String key) {
/* 152 */     NBTBase nbtbase = this.tagMap.get(key);
/* 153 */     return (nbtbase != null) ? nbtbase.getId() : 0;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean hasKey(String key) {
/* 160 */     return this.tagMap.containsKey(key);
/*     */   }
/*     */   
/*     */   public boolean hasKey(String key, int type) {
/* 164 */     int i = getTagId(key);
/*     */     
/* 166 */     if (i == type)
/* 167 */       return true; 
/* 168 */     if (type != 99) {
/* 169 */       if (i > 0);
/*     */ 
/*     */ 
/*     */       
/* 173 */       return false;
/*     */     } 
/* 175 */     return !(i != 1 && i != 2 && i != 3 && i != 4 && i != 5 && i != 6);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public byte getByte(String key) {
/*     */     try {
/* 184 */       return !hasKey(key, 99) ? 0 : ((NBTBase.NBTPrimitive)this.tagMap.get(key)).getByte();
/* 185 */     } catch (ClassCastException var3) {
/* 186 */       return 0;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public short getShort(String key) {
/*     */     try {
/* 195 */       return !hasKey(key, 99) ? 0 : ((NBTBase.NBTPrimitive)this.tagMap.get(key)).getShort();
/* 196 */     } catch (ClassCastException var3) {
/* 197 */       return 0;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getInteger(String key) {
/*     */     try {
/* 206 */       return !hasKey(key, 99) ? 0 : ((NBTBase.NBTPrimitive)this.tagMap.get(key)).getInt();
/* 207 */     } catch (ClassCastException var3) {
/* 208 */       return 0;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public long getLong(String key) {
/*     */     try {
/* 217 */       return !hasKey(key, 99) ? 0L : ((NBTBase.NBTPrimitive)this.tagMap.get(key)).getLong();
/* 218 */     } catch (ClassCastException var3) {
/* 219 */       return 0L;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public float getFloat(String key) {
/*     */     try {
/* 228 */       return !hasKey(key, 99) ? 0.0F : ((NBTBase.NBTPrimitive)this.tagMap.get(key)).getFloat();
/* 229 */     } catch (ClassCastException var3) {
/* 230 */       return 0.0F;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public double getDouble(String key) {
/*     */     try {
/* 239 */       return !hasKey(key, 99) ? 0.0D : ((NBTBase.NBTPrimitive)this.tagMap.get(key)).getDouble();
/* 240 */     } catch (ClassCastException var3) {
/* 241 */       return 0.0D;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getString(String key) {
/*     */     try {
/* 250 */       return !hasKey(key, 8) ? "" : ((NBTBase)this.tagMap.get(key)).getString();
/* 251 */     } catch (ClassCastException var3) {
/* 252 */       return "";
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public byte[] getByteArray(String key) {
/*     */     try {
/* 261 */       return !hasKey(key, 7) ? new byte[0] : ((NBTTagByteArray)this.tagMap.get(key)).getByteArray();
/* 262 */     } catch (ClassCastException classcastexception) {
/* 263 */       throw new ReportedException(createCrashReport(key, 7, classcastexception));
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int[] getIntArray(String key) {
/*     */     try {
/* 272 */       return !hasKey(key, 11) ? new int[0] : ((NBTTagIntArray)this.tagMap.get(key)).getIntArray();
/* 273 */     } catch (ClassCastException classcastexception) {
/* 274 */       throw new ReportedException(createCrashReport(key, 11, classcastexception));
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public NBTTagCompound getCompoundTag(String key) {
/*     */     try {
/* 284 */       return !hasKey(key, 10) ? new NBTTagCompound() : (NBTTagCompound)this.tagMap.get(key);
/* 285 */     } catch (ClassCastException classcastexception) {
/* 286 */       throw new ReportedException(createCrashReport(key, 10, classcastexception));
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public NBTTagList getTagList(String key, int type) {
/*     */     try {
/* 295 */       if (getTagId(key) != 9) {
/* 296 */         return new NBTTagList();
/*     */       }
/* 298 */       NBTTagList nbttaglist = (NBTTagList)this.tagMap.get(key);
/* 299 */       return (nbttaglist.tagCount() > 0 && nbttaglist.getTagType() != type) ? new NBTTagList() : nbttaglist;
/*     */     }
/* 301 */     catch (ClassCastException classcastexception) {
/* 302 */       throw new ReportedException(createCrashReport(key, 9, classcastexception));
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean getBoolean(String key) {
/* 311 */     return (getByte(key) != 0);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void removeTag(String key) {
/* 318 */     this.tagMap.remove(key);
/*     */   }
/*     */   
/*     */   public String toString() {
/* 322 */     StringBuilder stringbuilder = new StringBuilder("{");
/*     */     
/* 324 */     for (Map.Entry<String, NBTBase> entry : this.tagMap.entrySet()) {
/* 325 */       if (stringbuilder.length() != 1) {
/* 326 */         stringbuilder.append(',');
/*     */       }
/*     */       
/* 329 */       stringbuilder.append(entry.getKey()).append(':').append(entry.getValue());
/*     */     } 
/*     */     
/* 332 */     return stringbuilder.append('}').toString();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean hasNoTags() {
/* 339 */     return this.tagMap.isEmpty();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private CrashReport createCrashReport(final String key, final int expectedType, ClassCastException ex) {
/* 346 */     CrashReport crashreport = CrashReport.makeCrashReport(ex, "Reading NBT data");
/* 347 */     CrashReportCategory crashreportcategory = crashreport.makeCategoryDepth("Corrupt NBT tag", 1);
/* 348 */     crashreportcategory.addCrashSectionCallable("Tag type found", new Callable<String>() {
/*     */           public String call() throws Exception {
/* 350 */             return NBTBase.NBT_TYPES[((NBTBase)NBTTagCompound.this.tagMap.get(key)).getId()];
/*     */           }
/*     */         });
/* 353 */     crashreportcategory.addCrashSectionCallable("Tag type expected", new Callable<String>() {
/*     */           public String call() throws Exception {
/* 355 */             return NBTBase.NBT_TYPES[expectedType];
/*     */           }
/*     */         });
/* 358 */     crashreportcategory.addCrashSection("Tag name", key);
/* 359 */     return crashreport;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public NBTBase copy() {
/* 366 */     NBTTagCompound nbttagcompound = new NBTTagCompound();
/*     */     
/* 368 */     for (String s : this.tagMap.keySet()) {
/* 369 */       nbttagcompound.setTag(s, ((NBTBase)this.tagMap.get(s)).copy());
/*     */     }
/*     */     
/* 372 */     return nbttagcompound;
/*     */   }
/*     */   
/*     */   public boolean equals(Object p_equals_1_) {
/* 376 */     if (super.equals(p_equals_1_)) {
/* 377 */       NBTTagCompound nbttagcompound = (NBTTagCompound)p_equals_1_;
/* 378 */       return this.tagMap.entrySet().equals(nbttagcompound.tagMap.entrySet());
/*     */     } 
/* 380 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public int hashCode() {
/* 385 */     return super.hashCode() ^ this.tagMap.hashCode();
/*     */   }
/*     */   
/*     */   private static void writeEntry(String name, NBTBase data, DataOutput output) throws IOException {
/* 389 */     output.writeByte(data.getId());
/*     */     
/* 391 */     if (data.getId() != 0) {
/* 392 */       output.writeUTF(name);
/* 393 */       data.write(output);
/*     */     } 
/*     */   }
/*     */   
/*     */   private static byte readType(DataInput input, NBTSizeTracker sizeTracker) throws IOException {
/* 398 */     return input.readByte();
/*     */   }
/*     */   
/*     */   private static String readKey(DataInput input, NBTSizeTracker sizeTracker) throws IOException {
/* 402 */     return input.readUTF();
/*     */   }
/*     */   
/*     */   static NBTBase readNBT(byte id, String key, DataInput input, int depth, NBTSizeTracker sizeTracker) throws IOException {
/* 406 */     NBTBase nbtbase = NBTBase.createNewByType(id);
/*     */     
/*     */     try {
/* 409 */       nbtbase.read(input, depth, sizeTracker);
/* 410 */       return nbtbase;
/* 411 */     } catch (IOException ioexception) {
/* 412 */       CrashReport crashreport = CrashReport.makeCrashReport(ioexception, "Loading NBT data");
/* 413 */       CrashReportCategory crashreportcategory = crashreport.makeCategory("NBT Tag");
/* 414 */       crashreportcategory.addCrashSection("Tag name", key);
/* 415 */       crashreportcategory.addCrashSection("Tag type", Byte.valueOf(id));
/* 416 */       throw new ReportedException(crashreport);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void merge(NBTTagCompound other) {
/* 425 */     for (String s : other.tagMap.keySet()) {
/* 426 */       NBTBase nbtbase = other.tagMap.get(s);
/*     */       
/* 428 */       if (nbtbase.getId() == 10) {
/* 429 */         if (hasKey(s, 10)) {
/* 430 */           NBTTagCompound nbttagcompound = getCompoundTag(s);
/* 431 */           nbttagcompound.merge((NBTTagCompound)nbtbase); continue;
/*     */         } 
/* 433 */         setTag(s, nbtbase.copy());
/*     */         continue;
/*     */       } 
/* 436 */       setTag(s, nbtbase.copy());
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\minecraft\nbt\NBTTagCompound.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */