/*     */ package net.minecraft.entity;
/*     */ 
/*     */ import com.google.common.collect.Lists;
/*     */ import com.google.common.collect.Maps;
/*     */ import java.io.IOException;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import java.util.concurrent.locks.ReadWriteLock;
/*     */ import java.util.concurrent.locks.ReentrantReadWriteLock;
/*     */ import net.minecraft.crash.CrashReport;
/*     */ import net.minecraft.crash.CrashReportCategory;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraft.network.PacketBuffer;
/*     */ import net.minecraft.util.BlockPos;
/*     */ import net.minecraft.util.ReportedException;
/*     */ import net.minecraft.util.Rotations;
/*     */ import net.minecraft.world.biome.BiomeGenBase;
/*     */ import org.apache.commons.lang3.ObjectUtils;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class DataWatcher
/*     */ {
/*     */   private final Entity owner;
/*     */   private boolean isBlank = true;
/*  29 */   private static final Map<Class<?>, Integer> dataTypes = Maps.newHashMap();
/*  30 */   private final Map<Integer, WatchableObject> watchedObjects = Maps.newHashMap();
/*     */ 
/*     */   
/*     */   private boolean objectChanged;
/*     */ 
/*     */   
/*  36 */   private ReadWriteLock lock = new ReentrantReadWriteLock();
/*  37 */   public BiomeGenBase spawnBiome = BiomeGenBase.plains;
/*  38 */   public BlockPos spawnPosition = BlockPos.ORIGIN;
/*     */   
/*     */   public DataWatcher(Entity owner) {
/*  41 */     this.owner = owner;
/*     */   }
/*     */   
/*     */   public <T> void addObject(int id, T object) {
/*  45 */     Integer integer = dataTypes.get(object.getClass());
/*     */     
/*  47 */     if (integer == null)
/*  48 */       throw new IllegalArgumentException("Unknown data type: " + object.getClass()); 
/*  49 */     if (id > 31)
/*  50 */       throw new IllegalArgumentException("Data value id is too big with " + id + "! (Max is " + '\037' + ")"); 
/*  51 */     if (this.watchedObjects.containsKey(Integer.valueOf(id))) {
/*  52 */       throw new IllegalArgumentException("Duplicate id value for " + id + "!");
/*     */     }
/*  54 */     WatchableObject datawatcher$watchableobject = new WatchableObject(integer.intValue(), id, object);
/*  55 */     this.lock.writeLock().lock();
/*  56 */     this.watchedObjects.put(Integer.valueOf(id), datawatcher$watchableobject);
/*  57 */     this.lock.writeLock().unlock();
/*  58 */     this.isBlank = false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void addObjectByDataType(int id, int type) {
/*  66 */     WatchableObject datawatcher$watchableobject = new WatchableObject(type, id, null);
/*  67 */     this.lock.writeLock().lock();
/*  68 */     this.watchedObjects.put(Integer.valueOf(id), datawatcher$watchableobject);
/*  69 */     this.lock.writeLock().unlock();
/*  70 */     this.isBlank = false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public byte getWatchableObjectByte(int id) {
/*  77 */     return ((Byte)getWatchedObject(id).getObject()).byteValue();
/*     */   }
/*     */   
/*     */   public short getWatchableObjectShort(int id) {
/*  81 */     return ((Short)getWatchedObject(id).getObject()).shortValue();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getWatchableObjectInt(int id) {
/*  88 */     return ((Integer)getWatchedObject(id).getObject()).intValue();
/*     */   }
/*     */   
/*     */   public float getWatchableObjectFloat(int id) {
/*  92 */     return ((Float)getWatchedObject(id).getObject()).floatValue();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getWatchableObjectString(int id) {
/*  99 */     return (String)getWatchedObject(id).getObject();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ItemStack getWatchableObjectItemStack(int id) {
/* 106 */     return (ItemStack)getWatchedObject(id).getObject();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private WatchableObject getWatchedObject(int id) {
/*     */     WatchableObject datawatcher$watchableobject;
/* 113 */     this.lock.readLock().lock();
/*     */ 
/*     */     
/*     */     try {
/* 117 */       datawatcher$watchableobject = this.watchedObjects.get(Integer.valueOf(id));
/* 118 */     } catch (Throwable throwable) {
/* 119 */       CrashReport crashreport = CrashReport.makeCrashReport(throwable, "Getting synched entity data");
/* 120 */       CrashReportCategory crashreportcategory = crashreport.makeCategory("Synched entity data");
/* 121 */       crashreportcategory.addCrashSection("Data ID", Integer.valueOf(id));
/* 122 */       throw new ReportedException(crashreport);
/*     */     } 
/*     */     
/* 125 */     this.lock.readLock().unlock();
/* 126 */     return datawatcher$watchableobject;
/*     */   }
/*     */   
/*     */   public Rotations getWatchableObjectRotations(int id) {
/* 130 */     return (Rotations)getWatchedObject(id).getObject();
/*     */   }
/*     */   
/*     */   public <T> void updateObject(int id, T newData) {
/* 134 */     WatchableObject datawatcher$watchableobject = getWatchedObject(id);
/*     */     
/* 136 */     if (ObjectUtils.notEqual(newData, datawatcher$watchableobject.getObject())) {
/* 137 */       datawatcher$watchableobject.setObject(newData);
/* 138 */       this.owner.onDataWatcherUpdate(id);
/* 139 */       datawatcher$watchableobject.setWatched(true);
/* 140 */       this.objectChanged = true;
/*     */     } 
/*     */   }
/*     */   
/*     */   public void setObjectWatched(int id) {
/* 145 */     (getWatchedObject(id)).watched = true;
/* 146 */     this.objectChanged = true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean hasObjectChanged() {
/* 153 */     return this.objectChanged;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void writeWatchedListToPacketBuffer(List<WatchableObject> objectsList, PacketBuffer buffer) throws IOException {
/* 161 */     if (objectsList != null) {
/* 162 */       for (WatchableObject datawatcher$watchableobject : objectsList) {
/* 163 */         writeWatchableObjectToPacketBuffer(buffer, datawatcher$watchableobject);
/*     */       }
/*     */     }
/*     */     
/* 167 */     buffer.writeByte(127);
/*     */   }
/*     */   
/*     */   public List<WatchableObject> getChanged() {
/* 171 */     List<WatchableObject> list = null;
/*     */     
/* 173 */     if (this.objectChanged) {
/* 174 */       this.lock.readLock().lock();
/*     */       
/* 176 */       for (WatchableObject datawatcher$watchableobject : this.watchedObjects.values()) {
/* 177 */         if (datawatcher$watchableobject.isWatched()) {
/* 178 */           datawatcher$watchableobject.setWatched(false);
/*     */           
/* 180 */           if (list == null) {
/* 181 */             list = Lists.newArrayList();
/*     */           }
/*     */           
/* 184 */           list.add(datawatcher$watchableobject);
/*     */         } 
/*     */       } 
/*     */       
/* 188 */       this.lock.readLock().unlock();
/*     */     } 
/*     */     
/* 191 */     this.objectChanged = false;
/* 192 */     return list;
/*     */   }
/*     */   
/*     */   public void writeTo(PacketBuffer buffer) throws IOException {
/* 196 */     this.lock.readLock().lock();
/*     */     
/* 198 */     for (WatchableObject datawatcher$watchableobject : this.watchedObjects.values()) {
/* 199 */       writeWatchableObjectToPacketBuffer(buffer, datawatcher$watchableobject);
/*     */     }
/*     */     
/* 202 */     this.lock.readLock().unlock();
/* 203 */     buffer.writeByte(127);
/*     */   }
/*     */   
/*     */   public List<WatchableObject> getAllWatched() {
/* 207 */     List<WatchableObject> list = null;
/* 208 */     this.lock.readLock().lock();
/*     */     
/* 210 */     for (WatchableObject datawatcher$watchableobject : this.watchedObjects.values()) {
/* 211 */       if (list == null) {
/* 212 */         list = Lists.newArrayList();
/*     */       }
/*     */       
/* 215 */       list.add(datawatcher$watchableobject);
/*     */     } 
/*     */     
/* 218 */     this.lock.readLock().unlock();
/* 219 */     return list;
/*     */   }
/*     */ 
/*     */   
/*     */   private static void writeWatchableObjectToPacketBuffer(PacketBuffer buffer, WatchableObject object) throws IOException {
/*     */     ItemStack itemstack;
/*     */     BlockPos blockpos;
/*     */     Rotations rotations;
/* 227 */     int i = (object.getObjectType() << 5 | object.getDataValueId() & 0x1F) & 0xFF;
/* 228 */     buffer.writeByte(i);
/*     */     
/* 230 */     switch (object.getObjectType()) {
/*     */       case 0:
/* 232 */         buffer.writeByte(((Byte)object.getObject()).byteValue());
/*     */         break;
/*     */       
/*     */       case 1:
/* 236 */         buffer.writeShort(((Short)object.getObject()).shortValue());
/*     */         break;
/*     */       
/*     */       case 2:
/* 240 */         buffer.writeInt(((Integer)object.getObject()).intValue());
/*     */         break;
/*     */       
/*     */       case 3:
/* 244 */         buffer.writeFloat(((Float)object.getObject()).floatValue());
/*     */         break;
/*     */       
/*     */       case 4:
/* 248 */         buffer.writeString((String)object.getObject());
/*     */         break;
/*     */       
/*     */       case 5:
/* 252 */         itemstack = (ItemStack)object.getObject();
/* 253 */         buffer.writeItemStackToBuffer(itemstack);
/*     */         break;
/*     */       
/*     */       case 6:
/* 257 */         blockpos = (BlockPos)object.getObject();
/* 258 */         buffer.writeInt(blockpos.getX());
/* 259 */         buffer.writeInt(blockpos.getY());
/* 260 */         buffer.writeInt(blockpos.getZ());
/*     */         break;
/*     */       
/*     */       case 7:
/* 264 */         rotations = (Rotations)object.getObject();
/* 265 */         buffer.writeFloat(rotations.getX());
/* 266 */         buffer.writeFloat(rotations.getY());
/* 267 */         buffer.writeFloat(rotations.getZ());
/*     */         break;
/*     */     } 
/*     */   }
/*     */   public static List<WatchableObject> readWatchedListFromPacketBuffer(PacketBuffer buffer) throws IOException {
/* 272 */     List<WatchableObject> list = null;
/*     */     
/* 274 */     for (int i = buffer.readByte(); i != 127; i = buffer.readByte()) {
/* 275 */       int l, i1, j1; float f, f1, f2; if (list == null) {
/* 276 */         list = Lists.newArrayList();
/*     */       }
/*     */       
/* 279 */       int j = (i & 0xE0) >> 5;
/* 280 */       int k = i & 0x1F;
/* 281 */       WatchableObject datawatcher$watchableobject = null;
/*     */       
/* 283 */       switch (j) {
/*     */         case 0:
/* 285 */           datawatcher$watchableobject = new WatchableObject(j, k, Byte.valueOf(buffer.readByte()));
/*     */           break;
/*     */         
/*     */         case 1:
/* 289 */           datawatcher$watchableobject = new WatchableObject(j, k, Short.valueOf(buffer.readShort()));
/*     */           break;
/*     */         
/*     */         case 2:
/* 293 */           datawatcher$watchableobject = new WatchableObject(j, k, Integer.valueOf(buffer.readInt()));
/*     */           break;
/*     */         
/*     */         case 3:
/* 297 */           datawatcher$watchableobject = new WatchableObject(j, k, Float.valueOf(buffer.readFloat()));
/*     */           break;
/*     */         
/*     */         case 4:
/* 301 */           datawatcher$watchableobject = new WatchableObject(j, k, buffer.readStringFromBuffer(32767));
/*     */           break;
/*     */         
/*     */         case 5:
/* 305 */           datawatcher$watchableobject = new WatchableObject(j, k, buffer.readItemStackFromBuffer());
/*     */           break;
/*     */         
/*     */         case 6:
/* 309 */           l = buffer.readInt();
/* 310 */           i1 = buffer.readInt();
/* 311 */           j1 = buffer.readInt();
/* 312 */           datawatcher$watchableobject = new WatchableObject(j, k, new BlockPos(l, i1, j1));
/*     */           break;
/*     */         
/*     */         case 7:
/* 316 */           f = buffer.readFloat();
/* 317 */           f1 = buffer.readFloat();
/* 318 */           f2 = buffer.readFloat();
/* 319 */           datawatcher$watchableobject = new WatchableObject(j, k, new Rotations(f, f1, f2));
/*     */           break;
/*     */       } 
/* 322 */       list.add(datawatcher$watchableobject);
/*     */     } 
/*     */     
/* 325 */     return list;
/*     */   }
/*     */   
/*     */   public void updateWatchedObjectsFromList(List<WatchableObject> p_75687_1_) {
/* 329 */     this.lock.writeLock().lock();
/*     */     
/* 331 */     for (WatchableObject datawatcher$watchableobject : p_75687_1_) {
/* 332 */       WatchableObject datawatcher$watchableobject1 = this.watchedObjects.get(Integer.valueOf(datawatcher$watchableobject.getDataValueId()));
/*     */       
/* 334 */       if (datawatcher$watchableobject1 != null) {
/* 335 */         datawatcher$watchableobject1.setObject(datawatcher$watchableobject.getObject());
/* 336 */         this.owner.onDataWatcherUpdate(datawatcher$watchableobject.getDataValueId());
/*     */       } 
/*     */     } 
/*     */     
/* 340 */     this.lock.writeLock().unlock();
/* 341 */     this.objectChanged = true;
/*     */   }
/*     */   
/*     */   public boolean getIsBlank() {
/* 345 */     return this.isBlank;
/*     */   }
/*     */   
/*     */   public void func_111144_e() {
/* 349 */     this.objectChanged = false;
/*     */   }
/*     */   
/*     */   static {
/* 353 */     dataTypes.put(Byte.class, Integer.valueOf(0));
/* 354 */     dataTypes.put(Short.class, Integer.valueOf(1));
/* 355 */     dataTypes.put(Integer.class, Integer.valueOf(2));
/* 356 */     dataTypes.put(Float.class, Integer.valueOf(3));
/* 357 */     dataTypes.put(String.class, Integer.valueOf(4));
/* 358 */     dataTypes.put(ItemStack.class, Integer.valueOf(5));
/* 359 */     dataTypes.put(BlockPos.class, Integer.valueOf(6));
/* 360 */     dataTypes.put(Rotations.class, Integer.valueOf(7));
/*     */   }
/*     */   
/*     */   public static class WatchableObject {
/*     */     private final int objectType;
/*     */     private final int dataValueId;
/*     */     private Object watchedObject;
/*     */     private boolean watched;
/*     */     
/*     */     public WatchableObject(int type, int id, Object object) {
/* 370 */       this.dataValueId = id;
/* 371 */       this.watchedObject = object;
/* 372 */       this.objectType = type;
/* 373 */       this.watched = true;
/*     */     }
/*     */     
/*     */     public int getDataValueId() {
/* 377 */       return this.dataValueId;
/*     */     }
/*     */     
/*     */     public void setObject(Object object) {
/* 381 */       this.watchedObject = object;
/*     */     }
/*     */     
/*     */     public Object getObject() {
/* 385 */       return this.watchedObject;
/*     */     }
/*     */     
/*     */     public int getObjectType() {
/* 389 */       return this.objectType;
/*     */     }
/*     */     
/*     */     public boolean isWatched() {
/* 393 */       return this.watched;
/*     */     }
/*     */     
/*     */     public void setWatched(boolean watched) {
/* 397 */       this.watched = watched;
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\minecraft\entity\DataWatcher.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */