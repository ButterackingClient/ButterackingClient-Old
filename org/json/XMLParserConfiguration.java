/*     */ package org.json;
/*     */ 
/*     */ import java.util.Collections;
/*     */ import java.util.HashMap;
/*     */ import java.util.HashSet;
/*     */ import java.util.Map;
/*     */ import java.util.Set;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class XMLParserConfiguration
/*     */ {
/*  20 */   public static final XMLParserConfiguration ORIGINAL = new XMLParserConfiguration();
/*     */ 
/*     */   
/*  23 */   public static final XMLParserConfiguration KEEP_STRINGS = (new XMLParserConfiguration())
/*  24 */     .withKeepStrings(true);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private boolean keepStrings;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private String cDataTagName;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private boolean convertNilAttributeToNull;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private Map<String, XMLXsiTypeConverter<?>> xsiTypeMap;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private Set<String> forceList;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public XMLParserConfiguration() {
/*  62 */     this.keepStrings = false;
/*  63 */     this.cDataTagName = "content";
/*  64 */     this.convertNilAttributeToNull = false;
/*  65 */     this.xsiTypeMap = Collections.emptyMap();
/*  66 */     this.forceList = Collections.emptySet();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Deprecated
/*     */   public XMLParserConfiguration(boolean keepStrings) {
/*  79 */     this(keepStrings, "content", false);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Deprecated
/*     */   public XMLParserConfiguration(String cDataTagName) {
/*  94 */     this(false, cDataTagName, false);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Deprecated
/*     */   public XMLParserConfiguration(boolean keepStrings, String cDataTagName) {
/* 109 */     this.keepStrings = keepStrings;
/* 110 */     this.cDataTagName = cDataTagName;
/* 111 */     this.convertNilAttributeToNull = false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Deprecated
/*     */   public XMLParserConfiguration(boolean keepStrings, String cDataTagName, boolean convertNilAttributeToNull) {
/* 128 */     this.keepStrings = keepStrings;
/* 129 */     this.cDataTagName = cDataTagName;
/* 130 */     this.convertNilAttributeToNull = convertNilAttributeToNull;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private XMLParserConfiguration(boolean keepStrings, String cDataTagName, boolean convertNilAttributeToNull, Map<String, XMLXsiTypeConverter<?>> xsiTypeMap, Set<String> forceList) {
/* 147 */     this.keepStrings = keepStrings;
/* 148 */     this.cDataTagName = cDataTagName;
/* 149 */     this.convertNilAttributeToNull = convertNilAttributeToNull;
/* 150 */     this.xsiTypeMap = Collections.unmodifiableMap(xsiTypeMap);
/* 151 */     this.forceList = Collections.unmodifiableSet(forceList);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected XMLParserConfiguration clone() {
/* 164 */     return new XMLParserConfiguration(this.keepStrings, this.cDataTagName, this.convertNilAttributeToNull, this.xsiTypeMap, this.forceList);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isKeepStrings() {
/* 180 */     return this.keepStrings;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public XMLParserConfiguration withKeepStrings(boolean newVal) {
/* 193 */     XMLParserConfiguration newConfig = clone();
/* 194 */     newConfig.keepStrings = newVal;
/* 195 */     return newConfig;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getcDataTagName() {
/* 206 */     return this.cDataTagName;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public XMLParserConfiguration withcDataTagName(String newVal) {
/* 220 */     XMLParserConfiguration newConfig = clone();
/* 221 */     newConfig.cDataTagName = newVal;
/* 222 */     return newConfig;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isConvertNilAttributeToNull() {
/* 233 */     return this.convertNilAttributeToNull;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public XMLParserConfiguration withConvertNilAttributeToNull(boolean newVal) {
/* 247 */     XMLParserConfiguration newConfig = clone();
/* 248 */     newConfig.convertNilAttributeToNull = newVal;
/* 249 */     return newConfig;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Map<String, XMLXsiTypeConverter<?>> getXsiTypeMap() {
/* 260 */     return this.xsiTypeMap;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public XMLParserConfiguration withXsiTypeMap(Map<String, XMLXsiTypeConverter<?>> xsiTypeMap) {
/* 273 */     XMLParserConfiguration newConfig = clone();
/* 274 */     Map<String, XMLXsiTypeConverter<?>> cloneXsiTypeMap = new HashMap<String, XMLXsiTypeConverter<?>>(xsiTypeMap);
/* 275 */     newConfig.xsiTypeMap = Collections.unmodifiableMap(cloneXsiTypeMap);
/* 276 */     return newConfig;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Set<String> getForceList() {
/* 285 */     return this.forceList;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public XMLParserConfiguration withForceList(Set<String> forceList) {
/* 295 */     XMLParserConfiguration newConfig = clone();
/* 296 */     Set<String> cloneForceList = new HashSet<String>(forceList);
/* 297 */     newConfig.forceList = Collections.unmodifiableSet(cloneForceList);
/* 298 */     return newConfig;
/*     */   }
/*     */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\org\json\XMLParserConfiguration.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */