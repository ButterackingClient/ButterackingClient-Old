/*    */ package net.optifine;
/*    */ 
/*    */ import java.util.ArrayList;
/*    */ import java.util.HashMap;
/*    */ import java.util.List;
/*    */ import java.util.Map;
/*    */ 
/*    */ public class DynamicLightsMap {
/*  9 */   private Map<Integer, DynamicLight> map = new HashMap<>();
/* 10 */   private List<DynamicLight> list = new ArrayList<>();
/*    */   private boolean dirty = false;
/*    */   
/*    */   public DynamicLight put(int id, DynamicLight dynamicLight) {
/* 14 */     DynamicLight dynamiclight = this.map.put(Integer.valueOf(id), dynamicLight);
/* 15 */     setDirty();
/* 16 */     return dynamiclight;
/*    */   }
/*    */   
/*    */   public DynamicLight get(int id) {
/* 20 */     return this.map.get(Integer.valueOf(id));
/*    */   }
/*    */   
/*    */   public int size() {
/* 24 */     return this.map.size();
/*    */   }
/*    */   
/*    */   public DynamicLight remove(int id) {
/* 28 */     DynamicLight dynamiclight = this.map.remove(Integer.valueOf(id));
/*    */     
/* 30 */     if (dynamiclight != null) {
/* 31 */       setDirty();
/*    */     }
/*    */     
/* 34 */     return dynamiclight;
/*    */   }
/*    */   
/*    */   public void clear() {
/* 38 */     this.map.clear();
/* 39 */     this.list.clear();
/* 40 */     setDirty();
/*    */   }
/*    */   
/*    */   private void setDirty() {
/* 44 */     this.dirty = true;
/*    */   }
/*    */   
/*    */   public List<DynamicLight> valueList() {
/* 48 */     if (this.dirty) {
/* 49 */       this.list.clear();
/* 50 */       this.list.addAll(this.map.values());
/* 51 */       this.dirty = false;
/*    */     } 
/*    */     
/* 54 */     return this.list;
/*    */   }
/*    */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\optifine\DynamicLightsMap.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */