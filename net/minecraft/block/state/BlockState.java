/*     */ package net.minecraft.block.state;
/*     */ 
/*     */ import com.google.common.base.Function;
/*     */ import com.google.common.base.Joiner;
/*     */ import com.google.common.base.Objects;
/*     */ import com.google.common.collect.HashBasedTable;
/*     */ import com.google.common.collect.ImmutableList;
/*     */ import com.google.common.collect.ImmutableMap;
/*     */ import com.google.common.collect.ImmutableTable;
/*     */ import com.google.common.collect.Iterables;
/*     */ import com.google.common.collect.Lists;
/*     */ import com.google.common.collect.Maps;
/*     */ import com.google.common.collect.Table;
/*     */ import java.util.Arrays;
/*     */ import java.util.Collection;
/*     */ import java.util.Collections;
/*     */ import java.util.Comparator;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import net.minecraft.block.Block;
/*     */ import net.minecraft.block.properties.IProperty;
/*     */ import net.minecraft.util.Cartesian;
/*     */ import net.minecraft.util.MapPopulator;
/*     */ 
/*     */ 
/*     */ public class BlockState
/*     */ {
/*  28 */   private static final Joiner COMMA_JOINER = Joiner.on(", ");
/*  29 */   private static final Function<IProperty, String> GET_NAME_FUNC = new Function<IProperty, String>() {
/*     */       public String apply(IProperty p_apply_1_) {
/*  31 */         return (p_apply_1_ == null) ? "<NULL>" : p_apply_1_.getName();
/*     */       }
/*     */     };
/*     */   private final Block block;
/*     */   private final ImmutableList<IProperty> properties;
/*     */   private final ImmutableList<IBlockState> validStates;
/*     */   
/*     */   public BlockState(Block blockIn, IProperty... properties) {
/*  39 */     this.block = blockIn;
/*  40 */     Arrays.sort(properties, new Comparator<IProperty>() {
/*     */           public int compare(IProperty p_compare_1_, IProperty p_compare_2_) {
/*  42 */             return p_compare_1_.getName().compareTo(p_compare_2_.getName());
/*     */           }
/*     */         });
/*  45 */     this.properties = ImmutableList.copyOf((Object[])properties);
/*  46 */     Map<Map<IProperty, Comparable>, StateImplementation> map = Maps.newLinkedHashMap();
/*  47 */     List<StateImplementation> list = Lists.newArrayList();
/*     */     
/*  49 */     for (List<Comparable> list1 : (Iterable<List<Comparable>>)Cartesian.cartesianProduct(getAllowedValues())) {
/*  50 */       Map<IProperty, Comparable> map1 = MapPopulator.createMap((Iterable)this.properties, list1);
/*  51 */       StateImplementation blockstate$stateimplementation = new StateImplementation(blockIn, ImmutableMap.copyOf(map1), null);
/*  52 */       map.put(map1, blockstate$stateimplementation);
/*  53 */       list.add(blockstate$stateimplementation);
/*     */     } 
/*     */     
/*  56 */     for (StateImplementation blockstate$stateimplementation1 : list) {
/*  57 */       blockstate$stateimplementation1.buildPropertyValueTable(map);
/*     */     }
/*     */     
/*  60 */     this.validStates = ImmutableList.copyOf(list);
/*     */   }
/*     */   
/*     */   public ImmutableList<IBlockState> getValidStates() {
/*  64 */     return this.validStates;
/*     */   }
/*     */   
/*     */   private List<Iterable<Comparable>> getAllowedValues() {
/*  68 */     List<Iterable<Comparable>> list = Lists.newArrayList();
/*     */     
/*  70 */     for (int i = 0; i < this.properties.size(); i++) {
/*  71 */       list.add(((IProperty)this.properties.get(i)).getAllowedValues());
/*     */     }
/*     */     
/*  74 */     return list;
/*     */   }
/*     */   
/*     */   public IBlockState getBaseState() {
/*  78 */     return (IBlockState)this.validStates.get(0);
/*     */   }
/*     */   
/*     */   public Block getBlock() {
/*  82 */     return this.block;
/*     */   }
/*     */   
/*     */   public Collection<IProperty> getProperties() {
/*  86 */     return (Collection<IProperty>)this.properties;
/*     */   }
/*     */   
/*     */   public String toString() {
/*  90 */     return Objects.toStringHelper(this).add("block", Block.blockRegistry.getNameForObject(this.block)).add("properties", Iterables.transform((Iterable)this.properties, GET_NAME_FUNC)).toString();
/*     */   }
/*     */   
/*     */   static class StateImplementation extends BlockStateBase {
/*     */     private final Block block;
/*     */     private final ImmutableMap<IProperty, Comparable> properties;
/*     */     private ImmutableTable<IProperty, Comparable, IBlockState> propertyValueTable;
/*     */     
/*     */     private StateImplementation(Block blockIn, ImmutableMap<IProperty, Comparable> propertiesIn) {
/*  99 */       this.block = blockIn;
/* 100 */       this.properties = propertiesIn;
/*     */     }
/*     */     
/*     */     public Collection<IProperty> getPropertyNames() {
/* 104 */       return Collections.unmodifiableCollection((Collection<? extends IProperty>)this.properties.keySet());
/*     */     }
/*     */     
/*     */     public <T extends Comparable<T>> T getValue(IProperty<T> property) {
/* 108 */       if (!this.properties.containsKey(property)) {
/* 109 */         throw new IllegalArgumentException("Cannot get property " + property + " as it does not exist in " + this.block.getBlockState());
/*     */       }
/* 111 */       return (T)property.getValueClass().cast(this.properties.get(property));
/*     */     }
/*     */ 
/*     */     
/*     */     public <T extends Comparable<T>, V extends T> IBlockState withProperty(IProperty<T> property, V value) {
/* 116 */       if (!this.properties.containsKey(property))
/* 117 */         throw new IllegalArgumentException("Cannot set property " + property + " as it does not exist in " + this.block.getBlockState()); 
/* 118 */       if (!property.getAllowedValues().contains(value)) {
/* 119 */         throw new IllegalArgumentException("Cannot set property " + property + " to " + value + " on block " + Block.blockRegistry.getNameForObject(this.block) + ", it is not an allowed value");
/*     */       }
/* 121 */       return (this.properties.get(property) == value) ? this : (IBlockState)this.propertyValueTable.get(property, value);
/*     */     }
/*     */ 
/*     */     
/*     */     public ImmutableMap<IProperty, Comparable> getProperties() {
/* 126 */       return this.properties;
/*     */     }
/*     */     
/*     */     public Block getBlock() {
/* 130 */       return this.block;
/*     */     }
/*     */     
/*     */     public boolean equals(Object p_equals_1_) {
/* 134 */       return (this == p_equals_1_);
/*     */     }
/*     */     
/*     */     public int hashCode() {
/* 138 */       return this.properties.hashCode();
/*     */     }
/*     */     
/*     */     public void buildPropertyValueTable(Map<Map<IProperty, Comparable>, StateImplementation> map) {
/* 142 */       if (this.propertyValueTable != null) {
/* 143 */         throw new IllegalStateException();
/*     */       }
/* 145 */       HashBasedTable hashBasedTable = HashBasedTable.create();
/*     */       
/* 147 */       for (IProperty<? extends Comparable> iproperty : (Iterable<IProperty<? extends Comparable>>)this.properties.keySet()) {
/* 148 */         for (Comparable comparable : iproperty.getAllowedValues()) {
/* 149 */           if (comparable != this.properties.get(iproperty)) {
/* 150 */             hashBasedTable.put(iproperty, comparable, map.get(getPropertiesWithValue(iproperty, comparable)));
/*     */           }
/*     */         } 
/*     */       } 
/*     */       
/* 155 */       this.propertyValueTable = ImmutableTable.copyOf((Table)hashBasedTable);
/*     */     }
/*     */ 
/*     */     
/*     */     private Map<IProperty, Comparable> getPropertiesWithValue(IProperty property, Comparable value) {
/* 160 */       Map<IProperty, Comparable> map = Maps.newHashMap((Map)this.properties);
/* 161 */       map.put(property, value);
/* 162 */       return map;
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\minecraft\block\state\BlockState.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */