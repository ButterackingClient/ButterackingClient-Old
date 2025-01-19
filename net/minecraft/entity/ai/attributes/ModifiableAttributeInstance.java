/*     */ package net.minecraft.entity.ai.attributes;
/*     */ 
/*     */ import com.google.common.collect.Lists;
/*     */ import com.google.common.collect.Maps;
/*     */ import com.google.common.collect.Sets;
/*     */ import java.util.Collection;
/*     */ import java.util.Map;
/*     */ import java.util.Set;
/*     */ import java.util.UUID;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class ModifiableAttributeInstance
/*     */   implements IAttributeInstance
/*     */ {
/*     */   private final BaseAttributeMap attributeMap;
/*     */   private final IAttribute genericAttribute;
/*  22 */   private final Map<Integer, Set<AttributeModifier>> mapByOperation = Maps.newHashMap();
/*  23 */   private final Map<String, Set<AttributeModifier>> mapByName = Maps.newHashMap();
/*  24 */   private final Map<UUID, AttributeModifier> mapByUUID = Maps.newHashMap();
/*     */   private double baseValue;
/*     */   private boolean needsUpdate = true;
/*     */   private double cachedValue;
/*     */   
/*     */   public ModifiableAttributeInstance(BaseAttributeMap attributeMapIn, IAttribute genericAttributeIn) {
/*  30 */     this.attributeMap = attributeMapIn;
/*  31 */     this.genericAttribute = genericAttributeIn;
/*  32 */     this.baseValue = genericAttributeIn.getDefaultValue();
/*     */     
/*  34 */     for (int i = 0; i < 3; i++) {
/*  35 */       this.mapByOperation.put(Integer.valueOf(i), Sets.newHashSet());
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public IAttribute getAttribute() {
/*  43 */     return this.genericAttribute;
/*     */   }
/*     */   
/*     */   public double getBaseValue() {
/*  47 */     return this.baseValue;
/*     */   }
/*     */   
/*     */   public void setBaseValue(double baseValue) {
/*  51 */     if (baseValue != getBaseValue()) {
/*  52 */       this.baseValue = baseValue;
/*  53 */       flagForUpdate();
/*     */     } 
/*     */   }
/*     */   
/*     */   public Collection<AttributeModifier> getModifiersByOperation(int operation) {
/*  58 */     return this.mapByOperation.get(Integer.valueOf(operation));
/*     */   }
/*     */   
/*     */   public Collection<AttributeModifier> func_111122_c() {
/*  62 */     Set<AttributeModifier> set = Sets.newHashSet();
/*     */     
/*  64 */     for (int i = 0; i < 3; i++) {
/*  65 */       set.addAll(getModifiersByOperation(i));
/*     */     }
/*     */     
/*  68 */     return set;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public AttributeModifier getModifier(UUID uuid) {
/*  75 */     return this.mapByUUID.get(uuid);
/*     */   }
/*     */   
/*     */   public boolean hasModifier(AttributeModifier modifier) {
/*  79 */     return (this.mapByUUID.get(modifier.getID()) != null);
/*     */   }
/*     */   
/*     */   public void applyModifier(AttributeModifier modifier) {
/*  83 */     if (getModifier(modifier.getID()) != null) {
/*  84 */       throw new IllegalArgumentException("Modifier is already applied on this attribute!");
/*     */     }
/*  86 */     Set<AttributeModifier> set = this.mapByName.get(modifier.getName());
/*     */     
/*  88 */     if (set == null) {
/*  89 */       set = Sets.newHashSet();
/*  90 */       this.mapByName.put(modifier.getName(), set);
/*     */     } 
/*     */     
/*  93 */     ((Set<AttributeModifier>)this.mapByOperation.get(Integer.valueOf(modifier.getOperation()))).add(modifier);
/*  94 */     set.add(modifier);
/*  95 */     this.mapByUUID.put(modifier.getID(), modifier);
/*  96 */     flagForUpdate();
/*     */   }
/*     */ 
/*     */   
/*     */   protected void flagForUpdate() {
/* 101 */     this.needsUpdate = true;
/* 102 */     this.attributeMap.func_180794_a(this);
/*     */   }
/*     */   
/*     */   public void removeModifier(AttributeModifier modifier) {
/* 106 */     for (int i = 0; i < 3; i++) {
/* 107 */       Set<AttributeModifier> set = this.mapByOperation.get(Integer.valueOf(i));
/* 108 */       set.remove(modifier);
/*     */     } 
/*     */     
/* 111 */     Set<AttributeModifier> set1 = this.mapByName.get(modifier.getName());
/*     */     
/* 113 */     if (set1 != null) {
/* 114 */       set1.remove(modifier);
/*     */       
/* 116 */       if (set1.isEmpty()) {
/* 117 */         this.mapByName.remove(modifier.getName());
/*     */       }
/*     */     } 
/*     */     
/* 121 */     this.mapByUUID.remove(modifier.getID());
/* 122 */     flagForUpdate();
/*     */   }
/*     */   
/*     */   public void removeAllModifiers() {
/* 126 */     Collection<AttributeModifier> collection = func_111122_c();
/*     */     
/* 128 */     if (collection != null) {
/* 129 */       for (AttributeModifier attributemodifier : Lists.newArrayList(collection)) {
/* 130 */         removeModifier(attributemodifier);
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */   public double getAttributeValue() {
/* 136 */     if (this.needsUpdate) {
/* 137 */       this.cachedValue = computeValue();
/* 138 */       this.needsUpdate = false;
/*     */     } 
/*     */     
/* 141 */     return this.cachedValue;
/*     */   }
/*     */   
/*     */   private double computeValue() {
/* 145 */     double d0 = getBaseValue();
/*     */     
/* 147 */     for (AttributeModifier attributemodifier : func_180375_b(0)) {
/* 148 */       d0 += attributemodifier.getAmount();
/*     */     }
/*     */     
/* 151 */     double d1 = d0;
/*     */     
/* 153 */     for (AttributeModifier attributemodifier1 : func_180375_b(1)) {
/* 154 */       d1 += d0 * attributemodifier1.getAmount();
/*     */     }
/*     */     
/* 157 */     for (AttributeModifier attributemodifier2 : func_180375_b(2)) {
/* 158 */       d1 *= 1.0D + attributemodifier2.getAmount();
/*     */     }
/*     */     
/* 161 */     return this.genericAttribute.clampValue(d1);
/*     */   }
/*     */   
/*     */   private Collection<AttributeModifier> func_180375_b(int operation) {
/* 165 */     Set<AttributeModifier> set = Sets.newHashSet(getModifiersByOperation(operation));
/*     */     
/* 167 */     for (IAttribute iattribute = this.genericAttribute.func_180372_d(); iattribute != null; iattribute = iattribute.func_180372_d()) {
/* 168 */       IAttributeInstance iattributeinstance = this.attributeMap.getAttributeInstance(iattribute);
/*     */       
/* 170 */       if (iattributeinstance != null) {
/* 171 */         set.addAll(iattributeinstance.getModifiersByOperation(operation));
/*     */       }
/*     */     } 
/*     */     
/* 175 */     return set;
/*     */   }
/*     */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\minecraft\entity\ai\attributes\ModifiableAttributeInstance.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */