/*     */ package net.optifine.util;
/*     */ 
/*     */ import java.lang.reflect.Field;
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
/*     */ import net.minecraft.src.Config;
/*     */ import net.minecraft.util.BlockPos;
/*     */ import net.minecraft.world.chunk.Chunk;
/*     */ import net.optifine.reflect.Reflector;
/*     */ import net.optifine.reflect.ReflectorClass;
/*     */ import net.optifine.reflect.ReflectorField;
/*     */ 
/*     */ 
/*     */ public class ChunkUtils
/*     */ {
/*  16 */   private static ReflectorClass chunkClass = new ReflectorClass(Chunk.class);
/*  17 */   private static ReflectorField fieldHasEntities = findFieldHasEntities();
/*  18 */   private static ReflectorField fieldPrecipitationHeightMap = new ReflectorField(chunkClass, int[].class, 0);
/*     */   
/*     */   public static boolean hasEntities(Chunk chunk) {
/*  21 */     return Reflector.getFieldValueBoolean(chunk, fieldHasEntities, true);
/*     */   }
/*     */   
/*     */   public static int getPrecipitationHeight(Chunk chunk, BlockPos pos) {
/*  25 */     int[] aint = (int[])Reflector.getFieldValue(chunk, fieldPrecipitationHeightMap);
/*     */     
/*  27 */     if (aint != null && aint.length == 256) {
/*  28 */       int i = pos.getX() & 0xF;
/*  29 */       int j = pos.getZ() & 0xF;
/*  30 */       int k = i | j << 4;
/*  31 */       int l = aint[k];
/*     */       
/*  33 */       if (l >= 0) {
/*  34 */         return l;
/*     */       }
/*  36 */       BlockPos blockpos = chunk.getPrecipitationHeight(pos);
/*  37 */       return blockpos.getY();
/*     */     } 
/*     */     
/*  40 */     return -1;
/*     */   }
/*     */ 
/*     */   
/*     */   private static ReflectorField findFieldHasEntities() {
/*     */     try {
/*  46 */       Chunk chunk = new Chunk(null, 0, 0);
/*  47 */       List<Field> list = new ArrayList();
/*  48 */       List<Object> list1 = new ArrayList();
/*  49 */       Field[] afield = Chunk.class.getDeclaredFields();
/*     */       
/*  51 */       for (int i = 0; i < afield.length; i++) {
/*  52 */         Field field = afield[i];
/*     */         
/*  54 */         if (field.getType() == boolean.class) {
/*  55 */           field.setAccessible(true);
/*  56 */           list.add(field);
/*  57 */           list1.add(field.get(chunk));
/*     */         } 
/*     */       } 
/*     */       
/*  61 */       chunk.setHasEntities(false);
/*  62 */       List<Object> list2 = new ArrayList();
/*     */       
/*  64 */       for (Field field10 : list) {
/*  65 */         Field field1 = field10;
/*  66 */         list2.add(field1.get(chunk));
/*     */       } 
/*     */       
/*  69 */       chunk.setHasEntities(true);
/*  70 */       List<Object> list3 = new ArrayList();
/*     */       
/*  72 */       for (Field field20 : list) {
/*  73 */         Field field2 = field20;
/*  74 */         list3.add(field2.get(chunk));
/*     */       } 
/*     */       
/*  77 */       List<Field> list4 = new ArrayList();
/*     */       
/*  79 */       for (int j = 0; j < list.size(); j++) {
/*  80 */         Field field3 = list.get(j);
/*  81 */         Boolean obool = (Boolean)list2.get(j);
/*  82 */         Boolean obool1 = (Boolean)list3.get(j);
/*     */         
/*  84 */         if (!obool.booleanValue() && obool1.booleanValue()) {
/*  85 */           list4.add(field3);
/*  86 */           Boolean obool2 = (Boolean)list1.get(j);
/*  87 */           field3.set(chunk, obool2);
/*     */         } 
/*     */       } 
/*     */       
/*  91 */       if (list4.size() == 1) {
/*  92 */         Field field4 = list4.get(0);
/*  93 */         return new ReflectorField(field4);
/*     */       } 
/*  95 */     } catch (Exception exception) {
/*  96 */       Config.warn(String.valueOf(exception.getClass().getName()) + " " + exception.getMessage());
/*     */     } 
/*     */     
/*  99 */     Config.warn("Error finding Chunk.hasEntities");
/* 100 */     return new ReflectorField(new ReflectorClass(Chunk.class), "hasEntities");
/*     */   }
/*     */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\optifin\\util\ChunkUtils.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */