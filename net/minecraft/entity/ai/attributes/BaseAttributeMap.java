/*    */ package net.minecraft.entity.ai.attributes;
/*    */ 
/*    */ import com.google.common.collect.HashMultimap;
/*    */ import com.google.common.collect.Maps;
/*    */ import com.google.common.collect.Multimap;
/*    */ import java.util.Collection;
/*    */ import java.util.Map;
/*    */ import net.minecraft.server.management.LowerStringMap;
/*    */ 
/*    */ 
/*    */ 
/*    */ public abstract class BaseAttributeMap
/*    */ {
/* 14 */   protected final Map<IAttribute, IAttributeInstance> attributes = Maps.newHashMap();
/* 15 */   protected final Map<String, IAttributeInstance> attributesByName = (Map<String, IAttributeInstance>)new LowerStringMap();
/* 16 */   protected final Multimap<IAttribute, IAttribute> field_180377_c = (Multimap<IAttribute, IAttribute>)HashMultimap.create();
/*    */   
/*    */   public IAttributeInstance getAttributeInstance(IAttribute attribute) {
/* 19 */     return this.attributes.get(attribute);
/*    */   }
/*    */   
/*    */   public IAttributeInstance getAttributeInstanceByName(String attributeName) {
/* 23 */     return this.attributesByName.get(attributeName);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public IAttributeInstance registerAttribute(IAttribute attribute) {
/* 30 */     if (this.attributesByName.containsKey(attribute.getAttributeUnlocalizedName())) {
/* 31 */       throw new IllegalArgumentException("Attribute is already registered!");
/*    */     }
/* 33 */     IAttributeInstance iattributeinstance = func_180376_c(attribute);
/* 34 */     this.attributesByName.put(attribute.getAttributeUnlocalizedName(), iattributeinstance);
/* 35 */     this.attributes.put(attribute, iattributeinstance);
/*    */     
/* 37 */     for (IAttribute iattribute = attribute.func_180372_d(); iattribute != null; iattribute = iattribute.func_180372_d()) {
/* 38 */       this.field_180377_c.put(iattribute, attribute);
/*    */     }
/*    */     
/* 41 */     return iattributeinstance;
/*    */   }
/*    */ 
/*    */   
/*    */   protected abstract IAttributeInstance func_180376_c(IAttribute paramIAttribute);
/*    */   
/*    */   public Collection<IAttributeInstance> getAllAttributes() {
/* 48 */     return this.attributesByName.values();
/*    */   }
/*    */ 
/*    */   
/*    */   public void func_180794_a(IAttributeInstance instance) {}
/*    */   
/*    */   public void removeAttributeModifiers(Multimap<String, AttributeModifier> modifiers) {
/* 55 */     for (Map.Entry<String, AttributeModifier> entry : (Iterable<Map.Entry<String, AttributeModifier>>)modifiers.entries()) {
/* 56 */       IAttributeInstance iattributeinstance = getAttributeInstanceByName(entry.getKey());
/*    */       
/* 58 */       if (iattributeinstance != null) {
/* 59 */         iattributeinstance.removeModifier(entry.getValue());
/*    */       }
/*    */     } 
/*    */   }
/*    */   
/*    */   public void applyAttributeModifiers(Multimap<String, AttributeModifier> modifiers) {
/* 65 */     for (Map.Entry<String, AttributeModifier> entry : (Iterable<Map.Entry<String, AttributeModifier>>)modifiers.entries()) {
/* 66 */       IAttributeInstance iattributeinstance = getAttributeInstanceByName(entry.getKey());
/*    */       
/* 68 */       if (iattributeinstance != null) {
/* 69 */         iattributeinstance.removeModifier(entry.getValue());
/* 70 */         iattributeinstance.applyModifier(entry.getValue());
/*    */       } 
/*    */     } 
/*    */   }
/*    */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\minecraft\entity\ai\attributes\BaseAttributeMap.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */