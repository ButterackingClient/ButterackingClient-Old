/*    */ package net.minecraft.entity.ai.attributes;
/*    */ 
/*    */ import com.google.common.collect.Sets;
/*    */ import java.util.Collection;
/*    */ import java.util.Map;
/*    */ import java.util.Set;
/*    */ import net.minecraft.server.management.LowerStringMap;
/*    */ 
/*    */ public class ServersideAttributeMap
/*    */   extends BaseAttributeMap
/*    */ {
/* 12 */   private final Set<IAttributeInstance> attributeInstanceSet = Sets.newHashSet();
/* 13 */   protected final Map<String, IAttributeInstance> descriptionToAttributeInstanceMap = (Map<String, IAttributeInstance>)new LowerStringMap();
/*    */   
/*    */   public ModifiableAttributeInstance getAttributeInstance(IAttribute attribute) {
/* 16 */     return (ModifiableAttributeInstance)super.getAttributeInstance(attribute);
/*    */   }
/*    */   
/*    */   public ModifiableAttributeInstance getAttributeInstanceByName(String attributeName) {
/* 20 */     IAttributeInstance iattributeinstance = super.getAttributeInstanceByName(attributeName);
/*    */     
/* 22 */     if (iattributeinstance == null) {
/* 23 */       iattributeinstance = this.descriptionToAttributeInstanceMap.get(attributeName);
/*    */     }
/*    */     
/* 26 */     return (ModifiableAttributeInstance)iattributeinstance;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public IAttributeInstance registerAttribute(IAttribute attribute) {
/* 33 */     IAttributeInstance iattributeinstance = super.registerAttribute(attribute);
/*    */     
/* 35 */     if (attribute instanceof RangedAttribute && ((RangedAttribute)attribute).getDescription() != null) {
/* 36 */       this.descriptionToAttributeInstanceMap.put(((RangedAttribute)attribute).getDescription(), iattributeinstance);
/*    */     }
/*    */     
/* 39 */     return iattributeinstance;
/*    */   }
/*    */   
/*    */   protected IAttributeInstance func_180376_c(IAttribute attribute) {
/* 43 */     return new ModifiableAttributeInstance(this, attribute);
/*    */   }
/*    */   
/*    */   public void func_180794_a(IAttributeInstance instance) {
/* 47 */     if (instance.getAttribute().getShouldWatch()) {
/* 48 */       this.attributeInstanceSet.add(instance);
/*    */     }
/*    */     
/* 51 */     for (IAttribute iattribute : this.field_180377_c.get(instance.getAttribute())) {
/* 52 */       ModifiableAttributeInstance modifiableattributeinstance = getAttributeInstance(iattribute);
/*    */       
/* 54 */       if (modifiableattributeinstance != null) {
/* 55 */         modifiableattributeinstance.flagForUpdate();
/*    */       }
/*    */     } 
/*    */   }
/*    */   
/*    */   public Set<IAttributeInstance> getAttributeInstanceSet() {
/* 61 */     return this.attributeInstanceSet;
/*    */   }
/*    */   
/*    */   public Collection<IAttributeInstance> getWatchedAttributes() {
/* 65 */     Set<IAttributeInstance> set = Sets.newHashSet();
/*    */     
/* 67 */     for (IAttributeInstance iattributeinstance : getAllAttributes()) {
/* 68 */       if (iattributeinstance.getAttribute().getShouldWatch()) {
/* 69 */         set.add(iattributeinstance);
/*    */       }
/*    */     } 
/*    */     
/* 73 */     return set;
/*    */   }
/*    */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\minecraft\entity\ai\attributes\ServersideAttributeMap.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */