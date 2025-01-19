/*     */ package net.minecraft.nbt;
/*     */ 
/*     */ import com.google.common.collect.Lists;
/*     */ import java.io.DataInput;
/*     */ import java.io.DataOutput;
/*     */ import java.io.IOException;
/*     */ import java.util.List;
/*     */ import org.apache.logging.log4j.LogManager;
/*     */ import org.apache.logging.log4j.Logger;
/*     */ 
/*     */ public class NBTTagList
/*     */   extends NBTBase
/*     */ {
/*  14 */   private static final Logger LOGGER = LogManager.getLogger();
/*  15 */   private List<NBTBase> tagList = Lists.newArrayList();
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  20 */   private byte tagType = 0;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   void write(DataOutput output) throws IOException {
/*  26 */     if (!this.tagList.isEmpty()) {
/*  27 */       this.tagType = ((NBTBase)this.tagList.get(0)).getId();
/*     */     } else {
/*  29 */       this.tagType = 0;
/*     */     } 
/*     */     
/*  32 */     output.writeByte(this.tagType);
/*  33 */     output.writeInt(this.tagList.size());
/*     */     
/*  35 */     for (int i = 0; i < this.tagList.size(); i++) {
/*  36 */       ((NBTBase)this.tagList.get(i)).write(output);
/*     */     }
/*     */   }
/*     */   
/*     */   void read(DataInput input, int depth, NBTSizeTracker sizeTracker) throws IOException {
/*  41 */     sizeTracker.read(296L);
/*     */     
/*  43 */     if (depth > 512) {
/*  44 */       throw new RuntimeException("Tried to read NBT tag with too high complexity, depth > 512");
/*     */     }
/*  46 */     this.tagType = input.readByte();
/*  47 */     int i = input.readInt();
/*     */     
/*  49 */     if (this.tagType == 0 && i > 0) {
/*  50 */       throw new RuntimeException("Missing type on ListTag");
/*     */     }
/*  52 */     sizeTracker.read(32L * i);
/*  53 */     this.tagList = Lists.newArrayListWithCapacity(i);
/*     */     
/*  55 */     for (int j = 0; j < i; j++) {
/*  56 */       NBTBase nbtbase = NBTBase.createNewByType(this.tagType);
/*  57 */       nbtbase.read(input, depth + 1, sizeTracker);
/*  58 */       this.tagList.add(nbtbase);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public byte getId() {
/*  68 */     return 9;
/*     */   }
/*     */   
/*     */   public String toString() {
/*  72 */     StringBuilder stringbuilder = new StringBuilder("[");
/*     */     
/*  74 */     for (int i = 0; i < this.tagList.size(); i++) {
/*  75 */       if (i != 0) {
/*  76 */         stringbuilder.append(',');
/*     */       }
/*     */       
/*  79 */       stringbuilder.append(i).append(':').append(this.tagList.get(i));
/*     */     } 
/*     */     
/*  82 */     return stringbuilder.append(']').toString();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void appendTag(NBTBase nbt) {
/*  90 */     if (nbt.getId() == 0) {
/*  91 */       LOGGER.warn("Invalid TagEnd added to ListTag");
/*     */     } else {
/*  93 */       if (this.tagType == 0) {
/*  94 */         this.tagType = nbt.getId();
/*  95 */       } else if (this.tagType != nbt.getId()) {
/*  96 */         LOGGER.warn("Adding mismatching tag types to tag list");
/*     */         
/*     */         return;
/*     */       } 
/* 100 */       this.tagList.add(nbt);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void set(int idx, NBTBase nbt) {
/* 108 */     if (nbt.getId() == 0) {
/* 109 */       LOGGER.warn("Invalid TagEnd added to ListTag");
/* 110 */     } else if (idx >= 0 && idx < this.tagList.size()) {
/* 111 */       if (this.tagType == 0) {
/* 112 */         this.tagType = nbt.getId();
/* 113 */       } else if (this.tagType != nbt.getId()) {
/* 114 */         LOGGER.warn("Adding mismatching tag types to tag list");
/*     */         
/*     */         return;
/*     */       } 
/* 118 */       this.tagList.set(idx, nbt);
/*     */     } else {
/* 120 */       LOGGER.warn("index out of bounds to set tag in tag list");
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public NBTBase removeTag(int i) {
/* 128 */     return this.tagList.remove(i);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean hasNoTags() {
/* 135 */     return this.tagList.isEmpty();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public NBTTagCompound getCompoundTagAt(int i) {
/* 142 */     if (i >= 0 && i < this.tagList.size()) {
/* 143 */       NBTBase nbtbase = this.tagList.get(i);
/* 144 */       return (nbtbase.getId() == 10) ? (NBTTagCompound)nbtbase : new NBTTagCompound();
/*     */     } 
/* 146 */     return new NBTTagCompound();
/*     */   }
/*     */ 
/*     */   
/*     */   public int[] getIntArrayAt(int i) {
/* 151 */     if (i >= 0 && i < this.tagList.size()) {
/* 152 */       NBTBase nbtbase = this.tagList.get(i);
/* 153 */       return (nbtbase.getId() == 11) ? ((NBTTagIntArray)nbtbase).getIntArray() : new int[0];
/*     */     } 
/* 155 */     return new int[0];
/*     */   }
/*     */ 
/*     */   
/*     */   public double getDoubleAt(int i) {
/* 160 */     if (i >= 0 && i < this.tagList.size()) {
/* 161 */       NBTBase nbtbase = this.tagList.get(i);
/* 162 */       return (nbtbase.getId() == 6) ? ((NBTTagDouble)nbtbase).getDouble() : 0.0D;
/*     */     } 
/* 164 */     return 0.0D;
/*     */   }
/*     */ 
/*     */   
/*     */   public float getFloatAt(int i) {
/* 169 */     if (i >= 0 && i < this.tagList.size()) {
/* 170 */       NBTBase nbtbase = this.tagList.get(i);
/* 171 */       return (nbtbase.getId() == 5) ? ((NBTTagFloat)nbtbase).getFloat() : 0.0F;
/*     */     } 
/* 173 */     return 0.0F;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getStringTagAt(int i) {
/* 181 */     if (i >= 0 && i < this.tagList.size()) {
/* 182 */       NBTBase nbtbase = this.tagList.get(i);
/* 183 */       return (nbtbase.getId() == 8) ? nbtbase.getString() : nbtbase.toString();
/*     */     } 
/* 185 */     return "";
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public NBTBase get(int idx) {
/* 193 */     return (idx >= 0 && idx < this.tagList.size()) ? this.tagList.get(idx) : new NBTTagEnd();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int tagCount() {
/* 200 */     return this.tagList.size();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public NBTBase copy() {
/* 207 */     NBTTagList nbttaglist = new NBTTagList();
/* 208 */     nbttaglist.tagType = this.tagType;
/*     */     
/* 210 */     for (NBTBase nbtbase : this.tagList) {
/* 211 */       NBTBase nbtbase1 = nbtbase.copy();
/* 212 */       nbttaglist.tagList.add(nbtbase1);
/*     */     } 
/*     */     
/* 215 */     return nbttaglist;
/*     */   }
/*     */   
/*     */   public boolean equals(Object p_equals_1_) {
/* 219 */     if (super.equals(p_equals_1_)) {
/* 220 */       NBTTagList nbttaglist = (NBTTagList)p_equals_1_;
/*     */       
/* 222 */       if (this.tagType == nbttaglist.tagType) {
/* 223 */         return this.tagList.equals(nbttaglist.tagList);
/*     */       }
/*     */     } 
/*     */     
/* 227 */     return false;
/*     */   }
/*     */   
/*     */   public int hashCode() {
/* 231 */     return super.hashCode() ^ this.tagList.hashCode();
/*     */   }
/*     */   
/*     */   public int getTagType() {
/* 235 */     return this.tagType;
/*     */   }
/*     */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\minecraft\nbt\NBTTagList.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */