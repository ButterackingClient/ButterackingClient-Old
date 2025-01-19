/*     */ package net.minecraft.world;
/*     */ 
/*     */ import java.util.Set;
/*     */ import java.util.TreeMap;
/*     */ import net.minecraft.nbt.NBTTagCompound;
/*     */ 
/*     */ public class GameRules
/*     */ {
/*   9 */   private TreeMap<String, Value> theGameRules = new TreeMap<>();
/*     */   
/*     */   public GameRules() {
/*  12 */     addGameRule("doFireTick", "true", ValueType.BOOLEAN_VALUE);
/*  13 */     addGameRule("mobGriefing", "true", ValueType.BOOLEAN_VALUE);
/*  14 */     addGameRule("keepInventory", "false", ValueType.BOOLEAN_VALUE);
/*  15 */     addGameRule("doMobSpawning", "true", ValueType.BOOLEAN_VALUE);
/*  16 */     addGameRule("doMobLoot", "true", ValueType.BOOLEAN_VALUE);
/*  17 */     addGameRule("doTileDrops", "true", ValueType.BOOLEAN_VALUE);
/*  18 */     addGameRule("doEntityDrops", "true", ValueType.BOOLEAN_VALUE);
/*  19 */     addGameRule("commandBlockOutput", "true", ValueType.BOOLEAN_VALUE);
/*  20 */     addGameRule("naturalRegeneration", "true", ValueType.BOOLEAN_VALUE);
/*  21 */     addGameRule("doDaylightCycle", "true", ValueType.BOOLEAN_VALUE);
/*  22 */     addGameRule("logAdminCommands", "true", ValueType.BOOLEAN_VALUE);
/*  23 */     addGameRule("showDeathMessages", "true", ValueType.BOOLEAN_VALUE);
/*  24 */     addGameRule("randomTickSpeed", "3", ValueType.NUMERICAL_VALUE);
/*  25 */     addGameRule("sendCommandFeedback", "true", ValueType.BOOLEAN_VALUE);
/*  26 */     addGameRule("reducedDebugInfo", "false", ValueType.BOOLEAN_VALUE);
/*     */   }
/*     */   
/*     */   public void addGameRule(String key, String value, ValueType type) {
/*  30 */     this.theGameRules.put(key, new Value(value, type));
/*     */   }
/*     */   
/*     */   public void setOrCreateGameRule(String key, String ruleValue) {
/*  34 */     Value gamerules$value = this.theGameRules.get(key);
/*     */     
/*  36 */     if (gamerules$value != null) {
/*  37 */       gamerules$value.setValue(ruleValue);
/*     */     } else {
/*  39 */       addGameRule(key, ruleValue, ValueType.ANY_VALUE);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getString(String name) {
/*  47 */     Value gamerules$value = this.theGameRules.get(name);
/*  48 */     return (gamerules$value != null) ? gamerules$value.getString() : "";
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean getBoolean(String name) {
/*  55 */     Value gamerules$value = this.theGameRules.get(name);
/*  56 */     return (gamerules$value != null) ? gamerules$value.getBoolean() : false;
/*     */   }
/*     */   
/*     */   public int getInt(String name) {
/*  60 */     Value gamerules$value = this.theGameRules.get(name);
/*  61 */     return (gamerules$value != null) ? gamerules$value.getInt() : 0;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public NBTTagCompound writeToNBT() {
/*  68 */     NBTTagCompound nbttagcompound = new NBTTagCompound();
/*     */     
/*  70 */     for (String s : this.theGameRules.keySet()) {
/*  71 */       Value gamerules$value = this.theGameRules.get(s);
/*  72 */       nbttagcompound.setString(s, gamerules$value.getString());
/*     */     } 
/*     */     
/*  75 */     return nbttagcompound;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void readFromNBT(NBTTagCompound nbt) {
/*  82 */     for (String s : nbt.getKeySet()) {
/*  83 */       String s1 = nbt.getString(s);
/*  84 */       setOrCreateGameRule(s, s1);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String[] getRules() {
/*  92 */     Set<String> set = this.theGameRules.keySet();
/*  93 */     return set.<String>toArray(new String[set.size()]);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean hasRule(String name) {
/* 100 */     return this.theGameRules.containsKey(name);
/*     */   }
/*     */   
/*     */   public boolean areSameType(String key, ValueType otherValue) {
/* 104 */     Value gamerules$value = this.theGameRules.get(key);
/* 105 */     return (gamerules$value != null && (gamerules$value.getType() == otherValue || otherValue == ValueType.ANY_VALUE));
/*     */   }
/*     */   
/*     */   static class Value {
/*     */     private String valueString;
/*     */     private boolean valueBoolean;
/*     */     private int valueInteger;
/*     */     private double valueDouble;
/*     */     private final GameRules.ValueType type;
/*     */     
/*     */     public Value(String value, GameRules.ValueType type) {
/* 116 */       this.type = type;
/* 117 */       setValue(value);
/*     */     }
/*     */     
/*     */     public void setValue(String value) {
/* 121 */       this.valueString = value;
/*     */       
/* 123 */       if (value != null) {
/* 124 */         if (value.equals("false")) {
/* 125 */           this.valueBoolean = false;
/*     */           
/*     */           return;
/*     */         } 
/* 129 */         if (value.equals("true")) {
/* 130 */           this.valueBoolean = true;
/*     */           
/*     */           return;
/*     */         } 
/*     */       } 
/* 135 */       this.valueBoolean = Boolean.parseBoolean(value);
/* 136 */       this.valueInteger = this.valueBoolean ? 1 : 0;
/*     */       
/*     */       try {
/* 139 */         this.valueInteger = Integer.parseInt(value);
/* 140 */       } catch (NumberFormatException numberFormatException) {}
/*     */ 
/*     */ 
/*     */       
/*     */       try {
/* 145 */         this.valueDouble = Double.parseDouble(value);
/* 146 */       } catch (NumberFormatException numberFormatException) {}
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public String getString() {
/* 152 */       return this.valueString;
/*     */     }
/*     */     
/*     */     public boolean getBoolean() {
/* 156 */       return this.valueBoolean;
/*     */     }
/*     */     
/*     */     public int getInt() {
/* 160 */       return this.valueInteger;
/*     */     }
/*     */     
/*     */     public GameRules.ValueType getType() {
/* 164 */       return this.type;
/*     */     }
/*     */   }
/*     */   
/*     */   public enum ValueType {
/* 169 */     ANY_VALUE,
/* 170 */     BOOLEAN_VALUE,
/* 171 */     NUMERICAL_VALUE;
/*     */   }
/*     */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\minecraft\world\GameRules.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */