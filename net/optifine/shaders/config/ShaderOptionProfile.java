/*     */ package net.optifine.shaders.config;
/*     */ 
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
/*     */ import net.optifine.Lang;
/*     */ import net.optifine.shaders.ShaderUtils;
/*     */ import net.optifine.shaders.Shaders;
/*     */ 
/*     */ public class ShaderOptionProfile
/*     */   extends ShaderOption {
/*  11 */   private ShaderProfile[] profiles = null;
/*  12 */   private ShaderOption[] options = null;
/*     */   private static final String NAME_PROFILE = "<profile>";
/*     */   private static final String VALUE_CUSTOM = "<custom>";
/*     */   
/*     */   public ShaderOptionProfile(ShaderProfile[] profiles, ShaderOption[] options) {
/*  17 */     super("<profile>", "", detectProfileName(profiles, options), getProfileNames(profiles), detectProfileName(profiles, options, true), null);
/*  18 */     this.profiles = profiles;
/*  19 */     this.options = options;
/*     */   }
/*     */   
/*     */   public void nextValue() {
/*  23 */     super.nextValue();
/*     */     
/*  25 */     if (getValue().equals("<custom>")) {
/*  26 */       super.nextValue();
/*     */     }
/*     */     
/*  29 */     applyProfileOptions();
/*     */   }
/*     */   
/*     */   public void updateProfile() {
/*  33 */     ShaderProfile shaderprofile = getProfile(getValue());
/*     */     
/*  35 */     if (shaderprofile == null || !ShaderUtils.matchProfile(shaderprofile, this.options, false)) {
/*  36 */       String s = detectProfileName(this.profiles, this.options);
/*  37 */       setValue(s);
/*     */     } 
/*     */   }
/*     */   
/*     */   private void applyProfileOptions() {
/*  42 */     ShaderProfile shaderprofile = getProfile(getValue());
/*     */     
/*  44 */     if (shaderprofile != null) {
/*  45 */       String[] astring = shaderprofile.getOptions();
/*     */       
/*  47 */       for (int i = 0; i < astring.length; i++) {
/*  48 */         String s = astring[i];
/*  49 */         ShaderOption shaderoption = getOption(s);
/*     */         
/*  51 */         if (shaderoption != null) {
/*  52 */           String s1 = shaderprofile.getValue(s);
/*  53 */           shaderoption.setValue(s1);
/*     */         } 
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   private ShaderOption getOption(String name) {
/*  60 */     for (int i = 0; i < this.options.length; i++) {
/*  61 */       ShaderOption shaderoption = this.options[i];
/*     */       
/*  63 */       if (shaderoption.getName().equals(name)) {
/*  64 */         return shaderoption;
/*     */       }
/*     */     } 
/*     */     
/*  68 */     return null;
/*     */   }
/*     */   
/*     */   private ShaderProfile getProfile(String name) {
/*  72 */     for (int i = 0; i < this.profiles.length; i++) {
/*  73 */       ShaderProfile shaderprofile = this.profiles[i];
/*     */       
/*  75 */       if (shaderprofile.getName().equals(name)) {
/*  76 */         return shaderprofile;
/*     */       }
/*     */     } 
/*     */     
/*  80 */     return null;
/*     */   }
/*     */   
/*     */   public String getNameText() {
/*  84 */     return Lang.get("of.shaders.profile");
/*     */   }
/*     */   
/*     */   public String getValueText(String val) {
/*  88 */     return val.equals("<custom>") ? Lang.get("of.general.custom", "<custom>") : Shaders.translate("profile." + val, val);
/*     */   }
/*     */   
/*     */   public String getValueColor(String val) {
/*  92 */     return val.equals("<custom>") ? "§c" : "§a";
/*     */   }
/*     */   
/*     */   public String getDescriptionText() {
/*  96 */     String s = Shaders.translate("profile.comment", null);
/*     */     
/*  98 */     if (s != null) {
/*  99 */       return s;
/*     */     }
/* 101 */     StringBuffer stringbuffer = new StringBuffer();
/*     */     
/* 103 */     for (int i = 0; i < this.profiles.length; i++) {
/* 104 */       String s1 = this.profiles[i].getName();
/*     */       
/* 106 */       if (s1 != null) {
/* 107 */         String s2 = Shaders.translate("profile." + s1 + ".comment", null);
/*     */         
/* 109 */         if (s2 != null) {
/* 110 */           stringbuffer.append(s2);
/*     */           
/* 112 */           if (!s2.endsWith(". ")) {
/* 113 */             stringbuffer.append(". ");
/*     */           }
/*     */         } 
/*     */       } 
/*     */     } 
/*     */     
/* 119 */     return stringbuffer.toString();
/*     */   }
/*     */ 
/*     */   
/*     */   private static String detectProfileName(ShaderProfile[] profs, ShaderOption[] opts) {
/* 124 */     return detectProfileName(profs, opts, false);
/*     */   }
/*     */   
/*     */   private static String detectProfileName(ShaderProfile[] profs, ShaderOption[] opts, boolean def) {
/* 128 */     ShaderProfile shaderprofile = ShaderUtils.detectProfile(profs, opts, def);
/* 129 */     return (shaderprofile == null) ? "<custom>" : shaderprofile.getName();
/*     */   }
/*     */   
/*     */   private static String[] getProfileNames(ShaderProfile[] profs) {
/* 133 */     List<String> list = new ArrayList<>();
/*     */     
/* 135 */     for (int i = 0; i < profs.length; i++) {
/* 136 */       ShaderProfile shaderprofile = profs[i];
/* 137 */       list.add(shaderprofile.getName());
/*     */     } 
/*     */     
/* 140 */     list.add("<custom>");
/* 141 */     String[] astring = list.<String>toArray(new String[list.size()]);
/* 142 */     return astring;
/*     */   }
/*     */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\optifine\shaders\config\ShaderOptionProfile.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */