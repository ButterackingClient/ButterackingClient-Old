/*     */ package net.minecraft.world.storage;
/*     */ 
/*     */ import com.google.common.collect.Lists;
/*     */ import com.google.common.collect.Maps;
/*     */ import java.io.DataInputStream;
/*     */ import java.io.DataOutputStream;
/*     */ import java.io.File;
/*     */ import java.io.FileInputStream;
/*     */ import java.io.FileOutputStream;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import net.minecraft.nbt.CompressedStreamTools;
/*     */ import net.minecraft.nbt.NBTBase;
/*     */ import net.minecraft.nbt.NBTTagCompound;
/*     */ import net.minecraft.nbt.NBTTagShort;
/*     */ import net.minecraft.world.WorldSavedData;
/*     */ 
/*     */ 
/*     */ public class MapStorage
/*     */ {
/*     */   private ISaveHandler saveHandler;
/*  22 */   protected Map<String, WorldSavedData> loadedDataMap = Maps.newHashMap();
/*  23 */   private List<WorldSavedData> loadedDataList = Lists.newArrayList();
/*  24 */   private Map<String, Short> idCounts = Maps.newHashMap();
/*     */   
/*     */   public MapStorage(ISaveHandler saveHandlerIn) {
/*  27 */     this.saveHandler = saveHandlerIn;
/*  28 */     loadIdCounts();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public WorldSavedData loadData(Class<? extends WorldSavedData> clazz, String dataIdentifier) {
/*  36 */     WorldSavedData worldsaveddata = this.loadedDataMap.get(dataIdentifier);
/*     */     
/*  38 */     if (worldsaveddata != null) {
/*  39 */       return worldsaveddata;
/*     */     }
/*  41 */     if (this.saveHandler != null) {
/*     */       try {
/*  43 */         File file1 = this.saveHandler.getMapFileFromName(dataIdentifier);
/*     */         
/*  45 */         if (file1 != null && file1.exists()) {
/*     */           try {
/*  47 */             worldsaveddata = clazz.getConstructor(new Class[] { String.class }).newInstance(new Object[] { dataIdentifier });
/*  48 */           } catch (Exception exception) {
/*  49 */             throw new RuntimeException("Failed to instantiate " + clazz.toString(), exception);
/*     */           } 
/*     */           
/*  52 */           FileInputStream fileinputstream = new FileInputStream(file1);
/*  53 */           NBTTagCompound nbttagcompound = CompressedStreamTools.readCompressed(fileinputstream);
/*  54 */           fileinputstream.close();
/*  55 */           worldsaveddata.readFromNBT(nbttagcompound.getCompoundTag("data"));
/*     */         } 
/*  57 */       } catch (Exception exception1) {
/*  58 */         exception1.printStackTrace();
/*     */       } 
/*     */     }
/*     */     
/*  62 */     if (worldsaveddata != null) {
/*  63 */       this.loadedDataMap.put(dataIdentifier, worldsaveddata);
/*  64 */       this.loadedDataList.add(worldsaveddata);
/*     */     } 
/*     */     
/*  67 */     return worldsaveddata;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setData(String dataIdentifier, WorldSavedData data) {
/*  75 */     if (this.loadedDataMap.containsKey(dataIdentifier)) {
/*  76 */       this.loadedDataList.remove(this.loadedDataMap.remove(dataIdentifier));
/*     */     }
/*     */     
/*  79 */     this.loadedDataMap.put(dataIdentifier, data);
/*  80 */     this.loadedDataList.add(data);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void saveAllData() {
/*  87 */     for (int i = 0; i < this.loadedDataList.size(); i++) {
/*  88 */       WorldSavedData worldsaveddata = this.loadedDataList.get(i);
/*     */       
/*  90 */       if (worldsaveddata.isDirty()) {
/*  91 */         saveData(worldsaveddata);
/*  92 */         worldsaveddata.setDirty(false);
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void saveData(WorldSavedData p_75747_1_) {
/* 101 */     if (this.saveHandler != null) {
/*     */       try {
/* 103 */         File file1 = this.saveHandler.getMapFileFromName(p_75747_1_.mapName);
/*     */         
/* 105 */         if (file1 != null) {
/* 106 */           NBTTagCompound nbttagcompound = new NBTTagCompound();
/* 107 */           p_75747_1_.writeToNBT(nbttagcompound);
/* 108 */           NBTTagCompound nbttagcompound1 = new NBTTagCompound();
/* 109 */           nbttagcompound1.setTag("data", (NBTBase)nbttagcompound);
/* 110 */           FileOutputStream fileoutputstream = new FileOutputStream(file1);
/* 111 */           CompressedStreamTools.writeCompressed(nbttagcompound1, fileoutputstream);
/* 112 */           fileoutputstream.close();
/*     */         } 
/* 114 */       } catch (Exception exception) {
/* 115 */         exception.printStackTrace();
/*     */       } 
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void loadIdCounts() {
/*     */     try {
/* 125 */       this.idCounts.clear();
/*     */       
/* 127 */       if (this.saveHandler == null) {
/*     */         return;
/*     */       }
/*     */       
/* 131 */       File file1 = this.saveHandler.getMapFileFromName("idcounts");
/*     */       
/* 133 */       if (file1 != null && file1.exists()) {
/* 134 */         DataInputStream datainputstream = new DataInputStream(new FileInputStream(file1));
/* 135 */         NBTTagCompound nbttagcompound = CompressedStreamTools.read(datainputstream);
/* 136 */         datainputstream.close();
/*     */         
/* 138 */         for (String s : nbttagcompound.getKeySet()) {
/* 139 */           NBTBase nbtbase = nbttagcompound.getTag(s);
/*     */           
/* 141 */           if (nbtbase instanceof NBTTagShort) {
/* 142 */             NBTTagShort nbttagshort = (NBTTagShort)nbtbase;
/* 143 */             short short1 = nbttagshort.getShort();
/* 144 */             this.idCounts.put(s, Short.valueOf(short1));
/*     */           } 
/*     */         } 
/*     */       } 
/* 148 */     } catch (Exception exception) {
/* 149 */       exception.printStackTrace();
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getUniqueDataId(String key) {
/* 157 */     Short oshort = this.idCounts.get(key);
/*     */     
/* 159 */     if (oshort == null) {
/* 160 */       oshort = Short.valueOf((short)0);
/*     */     } else {
/* 162 */       oshort = Short.valueOf((short)(oshort.shortValue() + 1));
/*     */     } 
/*     */     
/* 165 */     this.idCounts.put(key, oshort);
/*     */     
/* 167 */     if (this.saveHandler == null) {
/* 168 */       return oshort.shortValue();
/*     */     }
/*     */     try {
/* 171 */       File file1 = this.saveHandler.getMapFileFromName("idcounts");
/*     */       
/* 173 */       if (file1 != null) {
/* 174 */         NBTTagCompound nbttagcompound = new NBTTagCompound();
/*     */         
/* 176 */         for (String s : this.idCounts.keySet()) {
/* 177 */           short short1 = ((Short)this.idCounts.get(s)).shortValue();
/* 178 */           nbttagcompound.setShort(s, short1);
/*     */         } 
/*     */         
/* 181 */         DataOutputStream dataoutputstream = new DataOutputStream(new FileOutputStream(file1));
/* 182 */         CompressedStreamTools.write(nbttagcompound, dataoutputstream);
/* 183 */         dataoutputstream.close();
/*     */       } 
/* 185 */     } catch (Exception exception) {
/* 186 */       exception.printStackTrace();
/*     */     } 
/*     */     
/* 189 */     return oshort.shortValue();
/*     */   }
/*     */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\minecraft\world\storage\MapStorage.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */