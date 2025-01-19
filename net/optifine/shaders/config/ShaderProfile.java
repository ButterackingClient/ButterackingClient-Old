/*    */ package net.optifine.shaders.config;
/*    */ 
/*    */ import java.util.Collection;
/*    */ import java.util.LinkedHashMap;
/*    */ import java.util.LinkedHashSet;
/*    */ import java.util.Map;
/*    */ import java.util.Set;
/*    */ 
/*    */ public class ShaderProfile {
/* 10 */   private String name = null;
/* 11 */   private Map<String, String> mapOptionValues = new LinkedHashMap<>();
/* 12 */   private Set<String> disabledPrograms = new LinkedHashSet<>();
/*    */   
/*    */   public ShaderProfile(String name) {
/* 15 */     this.name = name;
/*    */   }
/*    */   
/*    */   public String getName() {
/* 19 */     return this.name;
/*    */   }
/*    */   
/*    */   public void addOptionValue(String option, String value) {
/* 23 */     this.mapOptionValues.put(option, value);
/*    */   }
/*    */   
/*    */   public void addOptionValues(ShaderProfile prof) {
/* 27 */     if (prof != null) {
/* 28 */       this.mapOptionValues.putAll(prof.mapOptionValues);
/*    */     }
/*    */   }
/*    */   
/*    */   public void applyOptionValues(ShaderOption[] options) {
/* 33 */     for (int i = 0; i < options.length; i++) {
/* 34 */       ShaderOption shaderoption = options[i];
/* 35 */       String s = shaderoption.getName();
/* 36 */       String s1 = this.mapOptionValues.get(s);
/*    */       
/* 38 */       if (s1 != null) {
/* 39 */         shaderoption.setValue(s1);
/*    */       }
/*    */     } 
/*    */   }
/*    */   
/*    */   public String[] getOptions() {
/* 45 */     Set<String> set = this.mapOptionValues.keySet();
/* 46 */     String[] astring = set.<String>toArray(new String[set.size()]);
/* 47 */     return astring;
/*    */   }
/*    */   
/*    */   public String getValue(String key) {
/* 51 */     return this.mapOptionValues.get(key);
/*    */   }
/*    */   
/*    */   public void addDisabledProgram(String program) {
/* 55 */     this.disabledPrograms.add(program);
/*    */   }
/*    */   
/*    */   public void removeDisabledProgram(String program) {
/* 59 */     this.disabledPrograms.remove(program);
/*    */   }
/*    */   
/*    */   public Collection<String> getDisabledPrograms() {
/* 63 */     return new LinkedHashSet<>(this.disabledPrograms);
/*    */   }
/*    */   
/*    */   public void addDisabledPrograms(Collection<String> programs) {
/* 67 */     this.disabledPrograms.addAll(programs);
/*    */   }
/*    */   
/*    */   public boolean isProgramDisabled(String program) {
/* 71 */     return this.disabledPrograms.contains(program);
/*    */   }
/*    */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\optifine\shaders\config\ShaderProfile.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */