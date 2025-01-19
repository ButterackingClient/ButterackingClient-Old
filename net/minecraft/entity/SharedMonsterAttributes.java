/*     */ package net.minecraft.entity;
/*     */ 
/*     */ import java.util.Collection;
/*     */ import java.util.UUID;
/*     */ import net.minecraft.entity.ai.attributes.AttributeModifier;
/*     */ import net.minecraft.entity.ai.attributes.BaseAttributeMap;
/*     */ import net.minecraft.entity.ai.attributes.IAttribute;
/*     */ import net.minecraft.entity.ai.attributes.IAttributeInstance;
/*     */ import net.minecraft.entity.ai.attributes.RangedAttribute;
/*     */ import net.minecraft.nbt.NBTBase;
/*     */ import net.minecraft.nbt.NBTTagCompound;
/*     */ import net.minecraft.nbt.NBTTagList;
/*     */ import org.apache.logging.log4j.LogManager;
/*     */ import org.apache.logging.log4j.Logger;
/*     */ 
/*     */ public class SharedMonsterAttributes {
/*  17 */   private static final Logger logger = LogManager.getLogger();
/*  18 */   public static final IAttribute maxHealth = (IAttribute)(new RangedAttribute(null, "generic.maxHealth", 20.0D, 0.0D, 1024.0D)).setDescription("Max Health").setShouldWatch(true);
/*  19 */   public static final IAttribute followRange = (IAttribute)(new RangedAttribute(null, "generic.followRange", 32.0D, 0.0D, 2048.0D)).setDescription("Follow Range");
/*  20 */   public static final IAttribute knockbackResistance = (IAttribute)(new RangedAttribute(null, "generic.knockbackResistance", 0.0D, 0.0D, 1.0D)).setDescription("Knockback Resistance");
/*  21 */   public static final IAttribute movementSpeed = (IAttribute)(new RangedAttribute(null, "generic.movementSpeed", 0.699999988079071D, 0.0D, 1024.0D)).setDescription("Movement Speed").setShouldWatch(true);
/*  22 */   public static final IAttribute attackDamage = (IAttribute)new RangedAttribute(null, "generic.attackDamage", 2.0D, 0.0D, 2048.0D);
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static NBTTagList writeBaseAttributeMapToNBT(BaseAttributeMap map) {
/*  28 */     NBTTagList nbttaglist = new NBTTagList();
/*     */     
/*  30 */     for (IAttributeInstance iattributeinstance : map.getAllAttributes()) {
/*  31 */       nbttaglist.appendTag((NBTBase)writeAttributeInstanceToNBT(iattributeinstance));
/*     */     }
/*     */     
/*  34 */     return nbttaglist;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static NBTTagCompound writeAttributeInstanceToNBT(IAttributeInstance instance) {
/*  41 */     NBTTagCompound nbttagcompound = new NBTTagCompound();
/*  42 */     IAttribute iattribute = instance.getAttribute();
/*  43 */     nbttagcompound.setString("Name", iattribute.getAttributeUnlocalizedName());
/*  44 */     nbttagcompound.setDouble("Base", instance.getBaseValue());
/*  45 */     Collection<AttributeModifier> collection = instance.func_111122_c();
/*     */     
/*  47 */     if (collection != null && !collection.isEmpty()) {
/*  48 */       NBTTagList nbttaglist = new NBTTagList();
/*     */       
/*  50 */       for (AttributeModifier attributemodifier : collection) {
/*  51 */         if (attributemodifier.isSaved()) {
/*  52 */           nbttaglist.appendTag((NBTBase)writeAttributeModifierToNBT(attributemodifier));
/*     */         }
/*     */       } 
/*     */       
/*  56 */       nbttagcompound.setTag("Modifiers", (NBTBase)nbttaglist);
/*     */     } 
/*     */     
/*  59 */     return nbttagcompound;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static NBTTagCompound writeAttributeModifierToNBT(AttributeModifier modifier) {
/*  66 */     NBTTagCompound nbttagcompound = new NBTTagCompound();
/*  67 */     nbttagcompound.setString("Name", modifier.getName());
/*  68 */     nbttagcompound.setDouble("Amount", modifier.getAmount());
/*  69 */     nbttagcompound.setInteger("Operation", modifier.getOperation());
/*  70 */     nbttagcompound.setLong("UUIDMost", modifier.getID().getMostSignificantBits());
/*  71 */     nbttagcompound.setLong("UUIDLeast", modifier.getID().getLeastSignificantBits());
/*  72 */     return nbttagcompound;
/*     */   }
/*     */   
/*     */   public static void setAttributeModifiers(BaseAttributeMap map, NBTTagList list) {
/*  76 */     for (int i = 0; i < list.tagCount(); i++) {
/*  77 */       NBTTagCompound nbttagcompound = list.getCompoundTagAt(i);
/*  78 */       IAttributeInstance iattributeinstance = map.getAttributeInstanceByName(nbttagcompound.getString("Name"));
/*     */       
/*  80 */       if (iattributeinstance != null) {
/*  81 */         applyModifiersToAttributeInstance(iattributeinstance, nbttagcompound);
/*     */       } else {
/*  83 */         logger.warn("Ignoring unknown attribute '" + nbttagcompound.getString("Name") + "'");
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   private static void applyModifiersToAttributeInstance(IAttributeInstance instance, NBTTagCompound compound) {
/*  89 */     instance.setBaseValue(compound.getDouble("Base"));
/*     */     
/*  91 */     if (compound.hasKey("Modifiers", 9)) {
/*  92 */       NBTTagList nbttaglist = compound.getTagList("Modifiers", 10);
/*     */       
/*  94 */       for (int i = 0; i < nbttaglist.tagCount(); i++) {
/*  95 */         AttributeModifier attributemodifier = readAttributeModifierFromNBT(nbttaglist.getCompoundTagAt(i));
/*     */         
/*  97 */         if (attributemodifier != null) {
/*  98 */           AttributeModifier attributemodifier1 = instance.getModifier(attributemodifier.getID());
/*     */           
/* 100 */           if (attributemodifier1 != null) {
/* 101 */             instance.removeModifier(attributemodifier1);
/*     */           }
/*     */           
/* 104 */           instance.applyModifier(attributemodifier);
/*     */         } 
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static AttributeModifier readAttributeModifierFromNBT(NBTTagCompound compound) {
/* 114 */     UUID uuid = new UUID(compound.getLong("UUIDMost"), compound.getLong("UUIDLeast"));
/*     */     
/*     */     try {
/* 117 */       return new AttributeModifier(uuid, compound.getString("Name"), compound.getDouble("Amount"), compound.getInteger("Operation"));
/* 118 */     } catch (Exception exception) {
/* 119 */       logger.warn("Unable to create attribute: " + exception.getMessage());
/* 120 */       return null;
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\minecraft\entity\SharedMonsterAttributes.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */