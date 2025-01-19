/*      */ package org.json;
/*      */ 
/*      */ import java.io.Closeable;
/*      */ import java.io.IOException;
/*      */ import java.io.StringWriter;
/*      */ import java.io.Writer;
/*      */ import java.lang.annotation.Annotation;
/*      */ import java.lang.reflect.Field;
/*      */ import java.lang.reflect.InvocationTargetException;
/*      */ import java.lang.reflect.Method;
/*      */ import java.lang.reflect.Modifier;
/*      */ import java.math.BigDecimal;
/*      */ import java.math.BigInteger;
/*      */ import java.util.Collection;
/*      */ import java.util.Collections;
/*      */ import java.util.Enumeration;
/*      */ import java.util.HashMap;
/*      */ import java.util.IdentityHashMap;
/*      */ import java.util.Iterator;
/*      */ import java.util.Locale;
/*      */ import java.util.Map;
/*      */ import java.util.ResourceBundle;
/*      */ import java.util.Set;
/*      */ import java.util.regex.Pattern;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ public class JSONObject
/*      */ {
/*      */   private static final class Null
/*      */   {
/*      */     private Null() {}
/*      */     
/*      */     protected final Object clone() {
/*  100 */       return this;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public boolean equals(Object object) {
/*  114 */       return (object == null || object == this);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public int hashCode() {
/*  123 */       return 0;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public String toString() {
/*  133 */       return "null";
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  141 */   static final Pattern NUMBER_PATTERN = Pattern.compile("-?(?:0|[1-9]\\d*)(?:\\.\\d+)?(?:[eE][+-]?\\d+)?");
/*      */ 
/*      */   
/*      */   private final Map<String, Object> map;
/*      */ 
/*      */ 
/*      */   
/*      */   public Class<? extends Map> getMapType() {
/*  149 */     return (Class)this.map.getClass();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  158 */   public static final Object NULL = new Null();
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public JSONObject() {
/*  170 */     this.map = new HashMap<String, Object>();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public JSONObject(JSONObject jo, String... names) {
/*  184 */     this(names.length);
/*  185 */     for (int i = 0; i < names.length; i++) {
/*      */       try {
/*  187 */         putOnce(names[i], jo.opt(names[i]));
/*  188 */       } catch (Exception exception) {}
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public JSONObject(JSONTokener x) throws JSONException {
/*  203 */     this();
/*      */ 
/*      */ 
/*      */     
/*  207 */     if (x.nextClean() != '{') {
/*  208 */       throw x.syntaxError("A JSONObject text must begin with '{'");
/*      */     }
/*      */     while (true) {
/*  211 */       char prev = x.getPrevious();
/*  212 */       char c = x.nextClean();
/*  213 */       switch (c) {
/*      */         case '\000':
/*  215 */           throw x.syntaxError("A JSONObject text must end with '}'");
/*      */         case '}':
/*      */           return;
/*      */         case '[':
/*      */         case '{':
/*  220 */           if (prev == '{') {
/*  221 */             throw x.syntaxError("A JSON Object can not directly nest another JSON Object or JSON Array.");
/*      */           }
/*      */           break;
/*      */       } 
/*  225 */       x.back();
/*  226 */       String key = x.nextValue().toString();
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*  231 */       c = x.nextClean();
/*  232 */       if (c != ':') {
/*  233 */         throw x.syntaxError("Expected a ':' after a key");
/*      */       }
/*      */ 
/*      */ 
/*      */       
/*  238 */       if (key != null) {
/*      */         
/*  240 */         if (opt(key) != null)
/*      */         {
/*  242 */           throw x.syntaxError("Duplicate key \"" + key + "\"");
/*      */         }
/*      */         
/*  245 */         Object value = x.nextValue();
/*  246 */         if (value != null) {
/*  247 */           put(key, value);
/*      */         }
/*      */       } 
/*      */ 
/*      */ 
/*      */       
/*  253 */       switch (x.nextClean()) {
/*      */         case ',':
/*      */         case ';':
/*  256 */           if (x.nextClean() == '}') {
/*      */             return;
/*      */           }
/*  259 */           x.back(); continue;
/*      */         case '}':
/*      */           return;
/*      */       }  break;
/*      */     } 
/*  264 */     throw x.syntaxError("Expected a ',' or '}'");
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public JSONObject(Map<?, ?> m) {
/*  281 */     if (m == null) {
/*  282 */       this.map = new HashMap<String, Object>();
/*      */     } else {
/*  284 */       this.map = new HashMap<String, Object>(m.size());
/*  285 */       for (Map.Entry<?, ?> e : m.entrySet()) {
/*  286 */         if (e.getKey() == null) {
/*  287 */           throw new NullPointerException("Null key.");
/*      */         }
/*  289 */         Object value = e.getValue();
/*  290 */         if (value != null) {
/*  291 */           this.map.put(String.valueOf(e.getKey()), wrap(value));
/*      */         }
/*      */       } 
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public JSONObject(Object bean) {
/*  356 */     this();
/*  357 */     populateMap(bean);
/*      */   }
/*      */   
/*      */   private JSONObject(Object bean, Set<Object> objectsRecord) {
/*  361 */     this();
/*  362 */     populateMap(bean, objectsRecord);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public JSONObject(Object object, String... names) {
/*  380 */     this(names.length);
/*  381 */     Class<?> c = object.getClass();
/*  382 */     for (int i = 0; i < names.length; i++) {
/*  383 */       String name = names[i];
/*      */       try {
/*  385 */         putOpt(name, c.getField(name).get(object));
/*  386 */       } catch (Exception exception) {}
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public JSONObject(String source) throws JSONException {
/*  404 */     this(new JSONTokener(source));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public JSONObject(String baseName, Locale locale) throws JSONException {
/*  418 */     this();
/*  419 */     ResourceBundle bundle = ResourceBundle.getBundle(baseName, locale, 
/*  420 */         Thread.currentThread().getContextClassLoader());
/*      */ 
/*      */ 
/*      */     
/*  424 */     Enumeration<String> keys = bundle.getKeys();
/*  425 */     while (keys.hasMoreElements()) {
/*  426 */       Object key = keys.nextElement();
/*  427 */       if (key != null) {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */         
/*  433 */         String[] path = ((String)key).split("\\.");
/*  434 */         int last = path.length - 1;
/*  435 */         JSONObject target = this;
/*  436 */         for (int i = 0; i < last; i++) {
/*  437 */           String segment = path[i];
/*  438 */           JSONObject nextTarget = target.optJSONObject(segment);
/*  439 */           if (nextTarget == null) {
/*  440 */             nextTarget = new JSONObject();
/*  441 */             target.put(segment, nextTarget);
/*      */           } 
/*  443 */           target = nextTarget;
/*      */         } 
/*  445 */         target.put(path[last], bundle.getString((String)key));
/*      */       } 
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected JSONObject(int initialCapacity) {
/*  458 */     this.map = new HashMap<String, Object>(initialCapacity);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public JSONObject accumulate(String key, Object value) throws JSONException {
/*  483 */     testValidity(value);
/*  484 */     Object object = opt(key);
/*  485 */     if (object == null) {
/*  486 */       put(key, (value instanceof JSONArray) ? (new JSONArray())
/*  487 */           .put(value) : value);
/*      */     }
/*  489 */     else if (object instanceof JSONArray) {
/*  490 */       ((JSONArray)object).put(value);
/*      */     } else {
/*  492 */       put(key, (new JSONArray()).put(object).put(value));
/*      */     } 
/*  494 */     return this;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public JSONObject append(String key, Object value) throws JSONException {
/*  515 */     testValidity(value);
/*  516 */     Object object = opt(key);
/*  517 */     if (object == null) {
/*  518 */       put(key, (new JSONArray()).put(value));
/*  519 */     } else if (object instanceof JSONArray) {
/*  520 */       put(key, ((JSONArray)object).put(value));
/*      */     } else {
/*  522 */       throw wrongValueFormatException(key, "JSONArray", null, null);
/*      */     } 
/*  524 */     return this;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static String doubleToString(double d) {
/*  536 */     if (Double.isInfinite(d) || Double.isNaN(d)) {
/*  537 */       return "null";
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*  542 */     String string = Double.toString(d);
/*  543 */     if (string.indexOf('.') > 0 && string.indexOf('e') < 0 && string
/*  544 */       .indexOf('E') < 0) {
/*  545 */       while (string.endsWith("0")) {
/*  546 */         string = string.substring(0, string.length() - 1);
/*      */       }
/*  548 */       if (string.endsWith(".")) {
/*  549 */         string = string.substring(0, string.length() - 1);
/*      */       }
/*      */     } 
/*  552 */     return string;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Object get(String key) throws JSONException {
/*  565 */     if (key == null) {
/*  566 */       throw new JSONException("Null key.");
/*      */     }
/*  568 */     Object object = opt(key);
/*  569 */     if (object == null) {
/*  570 */       throw new JSONException("JSONObject[" + quote(key) + "] not found.");
/*      */     }
/*  572 */     return object;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public <E extends Enum<E>> E getEnum(Class<E> clazz, String key) throws JSONException {
/*  590 */     E val = optEnum(clazz, key);
/*  591 */     if (val == null)
/*      */     {
/*      */ 
/*      */       
/*  595 */       throw wrongValueFormatException(key, "enum of type " + quote(clazz.getSimpleName()), opt(key), null);
/*      */     }
/*  597 */     return val;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean getBoolean(String key) throws JSONException {
/*  611 */     Object object = get(key);
/*  612 */     if (object.equals(Boolean.FALSE) || (object instanceof String && ((String)object)
/*      */       
/*  614 */       .equalsIgnoreCase("false")))
/*  615 */       return false; 
/*  616 */     if (object.equals(Boolean.TRUE) || (object instanceof String && ((String)object)
/*      */       
/*  618 */       .equalsIgnoreCase("true"))) {
/*  619 */       return true;
/*      */     }
/*  621 */     throw wrongValueFormatException(key, "Boolean", object, null);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public BigInteger getBigInteger(String key) throws JSONException {
/*  635 */     Object object = get(key);
/*  636 */     BigInteger ret = objectToBigInteger(object, null);
/*  637 */     if (ret != null) {
/*  638 */       return ret;
/*      */     }
/*  640 */     throw wrongValueFormatException(key, "BigInteger", object, null);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public BigDecimal getBigDecimal(String key) throws JSONException {
/*  657 */     Object object = get(key);
/*  658 */     BigDecimal ret = objectToBigDecimal(object, null);
/*  659 */     if (ret != null) {
/*  660 */       return ret;
/*      */     }
/*  662 */     throw wrongValueFormatException(key, "BigDecimal", object, null);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public double getDouble(String key) throws JSONException {
/*  676 */     Object object = get(key);
/*  677 */     if (object instanceof Number) {
/*  678 */       return ((Number)object).doubleValue();
/*      */     }
/*      */     try {
/*  681 */       return Double.parseDouble(object.toString());
/*  682 */     } catch (Exception e) {
/*  683 */       throw wrongValueFormatException(key, "double", object, e);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public float getFloat(String key) throws JSONException {
/*  698 */     Object object = get(key);
/*  699 */     if (object instanceof Number) {
/*  700 */       return ((Number)object).floatValue();
/*      */     }
/*      */     try {
/*  703 */       return Float.parseFloat(object.toString());
/*  704 */     } catch (Exception e) {
/*  705 */       throw wrongValueFormatException(key, "float", object, e);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Number getNumber(String key) throws JSONException {
/*  720 */     Object object = get(key);
/*      */     try {
/*  722 */       if (object instanceof Number) {
/*  723 */         return (Number)object;
/*      */       }
/*  725 */       return stringToNumber(object.toString());
/*  726 */     } catch (Exception e) {
/*  727 */       throw wrongValueFormatException(key, "number", object, e);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int getInt(String key) throws JSONException {
/*  742 */     Object object = get(key);
/*  743 */     if (object instanceof Number) {
/*  744 */       return ((Number)object).intValue();
/*      */     }
/*      */     try {
/*  747 */       return Integer.parseInt(object.toString());
/*  748 */     } catch (Exception e) {
/*  749 */       throw wrongValueFormatException(key, "int", object, e);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public JSONArray getJSONArray(String key) throws JSONException {
/*  763 */     Object object = get(key);
/*  764 */     if (object instanceof JSONArray) {
/*  765 */       return (JSONArray)object;
/*      */     }
/*  767 */     throw wrongValueFormatException(key, "JSONArray", object, null);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public JSONObject getJSONObject(String key) throws JSONException {
/*  780 */     Object object = get(key);
/*  781 */     if (object instanceof JSONObject) {
/*  782 */       return (JSONObject)object;
/*      */     }
/*  784 */     throw wrongValueFormatException(key, "JSONObject", object, null);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public long getLong(String key) throws JSONException {
/*  798 */     Object object = get(key);
/*  799 */     if (object instanceof Number) {
/*  800 */       return ((Number)object).longValue();
/*      */     }
/*      */     try {
/*  803 */       return Long.parseLong(object.toString());
/*  804 */     } catch (Exception e) {
/*  805 */       throw wrongValueFormatException(key, "long", object, e);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static String[] getNames(JSONObject jo) {
/*  817 */     if (jo.isEmpty()) {
/*  818 */       return null;
/*      */     }
/*  820 */     return jo.keySet().<String>toArray(new String[jo.length()]);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static String[] getNames(Object object) {
/*  831 */     if (object == null) {
/*  832 */       return null;
/*      */     }
/*  834 */     Class<?> klass = object.getClass();
/*  835 */     Field[] fields = klass.getFields();
/*  836 */     int length = fields.length;
/*  837 */     if (length == 0) {
/*  838 */       return null;
/*      */     }
/*  840 */     String[] names = new String[length];
/*  841 */     for (int i = 0; i < length; i++) {
/*  842 */       names[i] = fields[i].getName();
/*      */     }
/*  844 */     return names;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public String getString(String key) throws JSONException {
/*  857 */     Object object = get(key);
/*  858 */     if (object instanceof String) {
/*  859 */       return (String)object;
/*      */     }
/*  861 */     throw wrongValueFormatException(key, "string", object, null);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean has(String key) {
/*  872 */     return this.map.containsKey(key);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public JSONObject increment(String key) throws JSONException {
/*  891 */     Object value = opt(key);
/*  892 */     if (value == null) {
/*  893 */       put(key, 1);
/*  894 */     } else if (value instanceof Integer) {
/*  895 */       put(key, ((Integer)value).intValue() + 1);
/*  896 */     } else if (value instanceof Long) {
/*  897 */       put(key, ((Long)value).longValue() + 1L);
/*  898 */     } else if (value instanceof BigInteger) {
/*  899 */       put(key, ((BigInteger)value).add(BigInteger.ONE));
/*  900 */     } else if (value instanceof Float) {
/*  901 */       put(key, ((Float)value).floatValue() + 1.0F);
/*  902 */     } else if (value instanceof Double) {
/*  903 */       put(key, ((Double)value).doubleValue() + 1.0D);
/*  904 */     } else if (value instanceof BigDecimal) {
/*  905 */       put(key, ((BigDecimal)value).add(BigDecimal.ONE));
/*      */     } else {
/*  907 */       throw new JSONException("Unable to increment [" + quote(key) + "].");
/*      */     } 
/*  909 */     return this;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean isNull(String key) {
/*  922 */     return NULL.equals(opt(key));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Iterator<String> keys() {
/*  934 */     return keySet().iterator();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Set<String> keySet() {
/*  946 */     return this.map.keySet();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected Set<Map.Entry<String, Object>> entrySet() {
/*  962 */     return this.map.entrySet();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int length() {
/*  971 */     return this.map.size();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void clear() {
/*  979 */     this.map.clear();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean isEmpty() {
/*  988 */     return this.map.isEmpty();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public JSONArray names() {
/*  999 */     if (this.map.isEmpty()) {
/* 1000 */       return null;
/*      */     }
/* 1002 */     return new JSONArray(this.map.keySet());
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static String numberToString(Number number) throws JSONException {
/* 1015 */     if (number == null) {
/* 1016 */       throw new JSONException("Null pointer");
/*      */     }
/* 1018 */     testValidity(number);
/*      */ 
/*      */ 
/*      */     
/* 1022 */     String string = number.toString();
/* 1023 */     if (string.indexOf('.') > 0 && string.indexOf('e') < 0 && string
/* 1024 */       .indexOf('E') < 0) {
/* 1025 */       while (string.endsWith("0")) {
/* 1026 */         string = string.substring(0, string.length() - 1);
/*      */       }
/* 1028 */       if (string.endsWith(".")) {
/* 1029 */         string = string.substring(0, string.length() - 1);
/*      */       }
/*      */     } 
/* 1032 */     return string;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Object opt(String key) {
/* 1043 */     return (key == null) ? null : this.map.get(key);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public <E extends Enum<E>> E optEnum(Class<E> clazz, String key) {
/* 1058 */     return optEnum(clazz, key, null);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public <E extends Enum<E>> E optEnum(Class<E> clazz, String key, E defaultValue) {
/*      */     try {
/* 1077 */       Object val = opt(key);
/* 1078 */       if (NULL.equals(val)) {
/* 1079 */         return defaultValue;
/*      */       }
/* 1081 */       if (clazz.isAssignableFrom(val.getClass()))
/*      */       {
/*      */         
/* 1084 */         return (E)val;
/*      */       }
/*      */       
/* 1087 */       return Enum.valueOf(clazz, val.toString());
/* 1088 */     } catch (IllegalArgumentException e) {
/* 1089 */       return defaultValue;
/* 1090 */     } catch (NullPointerException e) {
/* 1091 */       return defaultValue;
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean optBoolean(String key) {
/* 1104 */     return optBoolean(key, false);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean optBoolean(String key, boolean defaultValue) {
/* 1119 */     Object val = opt(key);
/* 1120 */     if (NULL.equals(val)) {
/* 1121 */       return defaultValue;
/*      */     }
/* 1123 */     if (val instanceof Boolean) {
/* 1124 */       return ((Boolean)val).booleanValue();
/*      */     }
/*      */     
/*      */     try {
/* 1128 */       return getBoolean(key);
/* 1129 */     } catch (Exception e) {
/* 1130 */       return defaultValue;
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public BigDecimal optBigDecimal(String key, BigDecimal defaultValue) {
/* 1149 */     Object val = opt(key);
/* 1150 */     return objectToBigDecimal(val, defaultValue);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   static BigDecimal objectToBigDecimal(Object val, BigDecimal defaultValue) {
/* 1160 */     return objectToBigDecimal(val, defaultValue, true);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   static BigDecimal objectToBigDecimal(Object val, BigDecimal defaultValue, boolean exact) {
/* 1172 */     if (NULL.equals(val)) {
/* 1173 */       return defaultValue;
/*      */     }
/* 1175 */     if (val instanceof BigDecimal) {
/* 1176 */       return (BigDecimal)val;
/*      */     }
/* 1178 */     if (val instanceof BigInteger) {
/* 1179 */       return new BigDecimal((BigInteger)val);
/*      */     }
/* 1181 */     if (val instanceof Double || val instanceof Float) {
/* 1182 */       if (!numberIsFinite((Number)val)) {
/* 1183 */         return defaultValue;
/*      */       }
/* 1185 */       if (exact) {
/* 1186 */         return new BigDecimal(((Number)val).doubleValue());
/*      */       }
/*      */ 
/*      */ 
/*      */       
/* 1191 */       return new BigDecimal(val.toString());
/*      */     } 
/* 1193 */     if (val instanceof Long || val instanceof Integer || val instanceof Short || val instanceof Byte)
/*      */     {
/* 1195 */       return new BigDecimal(((Number)val).longValue());
/*      */     }
/*      */     
/*      */     try {
/* 1199 */       return new BigDecimal(val.toString());
/* 1200 */     } catch (Exception e) {
/* 1201 */       return defaultValue;
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public BigInteger optBigInteger(String key, BigInteger defaultValue) {
/* 1217 */     Object val = opt(key);
/* 1218 */     return objectToBigInteger(val, defaultValue);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   static BigInteger objectToBigInteger(Object val, BigInteger defaultValue) {
/* 1228 */     if (NULL.equals(val)) {
/* 1229 */       return defaultValue;
/*      */     }
/* 1231 */     if (val instanceof BigInteger) {
/* 1232 */       return (BigInteger)val;
/*      */     }
/* 1234 */     if (val instanceof BigDecimal) {
/* 1235 */       return ((BigDecimal)val).toBigInteger();
/*      */     }
/* 1237 */     if (val instanceof Double || val instanceof Float) {
/* 1238 */       if (!numberIsFinite((Number)val)) {
/* 1239 */         return defaultValue;
/*      */       }
/* 1241 */       return (new BigDecimal(((Number)val).doubleValue())).toBigInteger();
/*      */     } 
/* 1243 */     if (val instanceof Long || val instanceof Integer || val instanceof Short || val instanceof Byte)
/*      */     {
/* 1245 */       return BigInteger.valueOf(((Number)val).longValue());
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     try {
/* 1254 */       String valStr = val.toString();
/* 1255 */       if (isDecimalNotation(valStr)) {
/* 1256 */         return (new BigDecimal(valStr)).toBigInteger();
/*      */       }
/* 1258 */       return new BigInteger(valStr);
/* 1259 */     } catch (Exception e) {
/* 1260 */       return defaultValue;
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public double optDouble(String key) {
/* 1274 */     return optDouble(key, Double.NaN);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public double optDouble(String key, double defaultValue) {
/* 1289 */     Number val = optNumber(key);
/* 1290 */     if (val == null) {
/* 1291 */       return defaultValue;
/*      */     }
/* 1293 */     double doubleValue = val.doubleValue();
/*      */ 
/*      */ 
/*      */     
/* 1297 */     return doubleValue;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public float optFloat(String key) {
/* 1310 */     return optFloat(key, Float.NaN);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public float optFloat(String key, float defaultValue) {
/* 1325 */     Number val = optNumber(key);
/* 1326 */     if (val == null) {
/* 1327 */       return defaultValue;
/*      */     }
/* 1329 */     float floatValue = val.floatValue();
/*      */ 
/*      */ 
/*      */     
/* 1333 */     return floatValue;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int optInt(String key) {
/* 1346 */     return optInt(key, 0);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int optInt(String key, int defaultValue) {
/* 1361 */     Number val = optNumber(key, null);
/* 1362 */     if (val == null) {
/* 1363 */       return defaultValue;
/*      */     }
/* 1365 */     return val.intValue();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public JSONArray optJSONArray(String key) {
/* 1377 */     Object o = opt(key);
/* 1378 */     return (o instanceof JSONArray) ? (JSONArray)o : null;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public JSONObject optJSONObject(String key) {
/* 1389 */     return optJSONObject(key, null);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public JSONObject optJSONObject(String key, JSONObject defaultValue) {
/* 1402 */     Object object = opt(key);
/* 1403 */     return (object instanceof JSONObject) ? (JSONObject)object : defaultValue;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public long optLong(String key) {
/* 1416 */     return optLong(key, 0L);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public long optLong(String key, long defaultValue) {
/* 1431 */     Number val = optNumber(key, null);
/* 1432 */     if (val == null) {
/* 1433 */       return defaultValue;
/*      */     }
/*      */     
/* 1436 */     return val.longValue();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Number optNumber(String key) {
/* 1450 */     return optNumber(key, null);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Number optNumber(String key, Number defaultValue) {
/* 1466 */     Object val = opt(key);
/* 1467 */     if (NULL.equals(val)) {
/* 1468 */       return defaultValue;
/*      */     }
/* 1470 */     if (val instanceof Number) {
/* 1471 */       return (Number)val;
/*      */     }
/*      */     
/*      */     try {
/* 1475 */       return stringToNumber(val.toString());
/* 1476 */     } catch (Exception e) {
/* 1477 */       return defaultValue;
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public String optString(String key) {
/* 1491 */     return optString(key, "");
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public String optString(String key, String defaultValue) {
/* 1505 */     Object object = opt(key);
/* 1506 */     return NULL.equals(object) ? defaultValue : object.toString();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void populateMap(Object bean) {
/* 1519 */     populateMap(bean, Collections.newSetFromMap(new IdentityHashMap<Object, Boolean>()));
/*      */   }
/*      */   
/*      */   private void populateMap(Object bean, Set<Object> objectsRecord) {
/* 1523 */     Class<?> klass = bean.getClass();
/*      */ 
/*      */ 
/*      */     
/* 1527 */     boolean includeSuperClass = (klass.getClassLoader() != null);
/*      */     
/* 1529 */     Method[] methods = includeSuperClass ? klass.getMethods() : klass.getDeclaredMethods();
/* 1530 */     for (Method method : methods) {
/* 1531 */       int modifiers = method.getModifiers();
/* 1532 */       if (Modifier.isPublic(modifiers) && 
/* 1533 */         !Modifier.isStatic(modifiers) && (method
/* 1534 */         .getParameterTypes()).length == 0 && 
/* 1535 */         !method.isBridge() && method
/* 1536 */         .getReturnType() != void.class && 
/* 1537 */         isValidMethodName(method.getName())) {
/* 1538 */         String key = getKeyNameFromMethod(method);
/* 1539 */         if (key != null && !key.isEmpty()) {
/*      */           
/* 1541 */           try { Object result = method.invoke(bean, new Object[0]);
/* 1542 */             if (result != null)
/*      */             {
/*      */ 
/*      */               
/* 1546 */               if (objectsRecord.contains(result)) {
/* 1547 */                 throw recursivelyDefinedObjectException(key);
/*      */               }
/*      */               
/* 1550 */               objectsRecord.add(result);
/*      */               
/* 1552 */               this.map.put(key, wrap(result, objectsRecord));
/*      */               
/* 1554 */               objectsRecord.remove(result);
/*      */ 
/*      */ 
/*      */ 
/*      */               
/* 1559 */               if (result instanceof Closeable) {
/*      */                 try {
/* 1561 */                   ((Closeable)result).close();
/* 1562 */                 } catch (IOException iOException) {}
/*      */               }
/*      */             }
/*      */              }
/* 1566 */           catch (IllegalAccessException illegalAccessException) {  }
/* 1567 */           catch (IllegalArgumentException illegalArgumentException) {  }
/* 1568 */           catch (InvocationTargetException invocationTargetException) {}
/*      */         }
/*      */       } 
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   private static boolean isValidMethodName(String name) {
/* 1576 */     return (!"getClass".equals(name) && !"getDeclaringClass".equals(name));
/*      */   }
/*      */   private static String getKeyNameFromMethod(Method method) {
/*      */     String key;
/* 1580 */     int ignoreDepth = getAnnotationDepth(method, (Class)JSONPropertyIgnore.class);
/* 1581 */     if (ignoreDepth > 0) {
/* 1582 */       int forcedNameDepth = getAnnotationDepth(method, (Class)JSONPropertyName.class);
/* 1583 */       if (forcedNameDepth < 0 || ignoreDepth <= forcedNameDepth)
/*      */       {
/*      */         
/* 1586 */         return null;
/*      */       }
/*      */     } 
/* 1589 */     JSONPropertyName annotation = getAnnotation(method, JSONPropertyName.class);
/* 1590 */     if (annotation != null && annotation.value() != null && !annotation.value().isEmpty()) {
/* 1591 */       return annotation.value();
/*      */     }
/*      */     
/* 1594 */     String name = method.getName();
/* 1595 */     if (name.startsWith("get") && name.length() > 3) {
/* 1596 */       key = name.substring(3);
/* 1597 */     } else if (name.startsWith("is") && name.length() > 2) {
/* 1598 */       key = name.substring(2);
/*      */     } else {
/* 1600 */       return null;
/*      */     } 
/*      */ 
/*      */ 
/*      */     
/* 1605 */     if (key.length() == 0 || Character.isLowerCase(key.charAt(0))) {
/* 1606 */       return null;
/*      */     }
/* 1608 */     if (key.length() == 1) {
/* 1609 */       key = key.toLowerCase(Locale.ROOT);
/* 1610 */     } else if (!Character.isUpperCase(key.charAt(1))) {
/* 1611 */       key = key.substring(0, 1).toLowerCase(Locale.ROOT) + key.substring(1);
/*      */     } 
/* 1613 */     return key;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static <A extends Annotation> A getAnnotation(Method m, Class<A> annotationClass) {
/* 1632 */     if (m == null || annotationClass == null) {
/* 1633 */       return null;
/*      */     }
/*      */     
/* 1636 */     if (m.isAnnotationPresent(annotationClass)) {
/* 1637 */       return m.getAnnotation(annotationClass);
/*      */     }
/*      */ 
/*      */     
/* 1641 */     Class<?> c = m.getDeclaringClass();
/* 1642 */     if (c.getSuperclass() == null) {
/* 1643 */       return null;
/*      */     }
/*      */ 
/*      */     
/* 1647 */     for (Class<?> i : c.getInterfaces()) {
/*      */       try {
/* 1649 */         Method im = i.getMethod(m.getName(), m.getParameterTypes());
/* 1650 */         return getAnnotation(im, annotationClass);
/* 1651 */       } catch (SecurityException ex) {
/*      */       
/* 1653 */       } catch (NoSuchMethodException ex) {}
/*      */     } 
/*      */ 
/*      */ 
/*      */     
/*      */     try {
/* 1659 */       return getAnnotation(c
/* 1660 */           .getSuperclass().getMethod(m.getName(), m.getParameterTypes()), annotationClass);
/*      */     }
/* 1662 */     catch (SecurityException ex) {
/* 1663 */       return null;
/* 1664 */     } catch (NoSuchMethodException ex) {
/* 1665 */       return null;
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static int getAnnotationDepth(Method m, Class<? extends Annotation> annotationClass) {
/* 1682 */     if (m == null || annotationClass == null) {
/* 1683 */       return -1;
/*      */     }
/*      */     
/* 1686 */     if (m.isAnnotationPresent(annotationClass)) {
/* 1687 */       return 1;
/*      */     }
/*      */ 
/*      */     
/* 1691 */     Class<?> c = m.getDeclaringClass();
/* 1692 */     if (c.getSuperclass() == null) {
/* 1693 */       return -1;
/*      */     }
/*      */ 
/*      */     
/* 1697 */     for (Class<?> i : c.getInterfaces()) {
/*      */       try {
/* 1699 */         Method im = i.getMethod(m.getName(), m.getParameterTypes());
/* 1700 */         int d = getAnnotationDepth(im, annotationClass);
/* 1701 */         if (d > 0)
/*      */         {
/* 1703 */           return d + 1;
/*      */         }
/* 1705 */       } catch (SecurityException ex) {
/*      */       
/* 1707 */       } catch (NoSuchMethodException ex) {}
/*      */     } 
/*      */ 
/*      */ 
/*      */     
/*      */     try {
/* 1713 */       int d = getAnnotationDepth(c
/* 1714 */           .getSuperclass().getMethod(m.getName(), m.getParameterTypes()), annotationClass);
/*      */       
/* 1716 */       if (d > 0)
/*      */       {
/* 1718 */         return d + 1;
/*      */       }
/* 1720 */       return -1;
/* 1721 */     } catch (SecurityException ex) {
/* 1722 */       return -1;
/* 1723 */     } catch (NoSuchMethodException ex) {
/* 1724 */       return -1;
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public JSONObject put(String key, boolean value) throws JSONException {
/* 1742 */     return put(key, value ? Boolean.TRUE : Boolean.FALSE);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public JSONObject put(String key, Collection<?> value) throws JSONException {
/* 1760 */     return put(key, new JSONArray(value));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public JSONObject put(String key, double value) throws JSONException {
/* 1777 */     return put(key, Double.valueOf(value));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public JSONObject put(String key, float value) throws JSONException {
/* 1794 */     return put(key, Float.valueOf(value));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public JSONObject put(String key, int value) throws JSONException {
/* 1811 */     return put(key, Integer.valueOf(value));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public JSONObject put(String key, long value) throws JSONException {
/* 1828 */     return put(key, Long.valueOf(value));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public JSONObject put(String key, Map<?, ?> value) throws JSONException {
/* 1846 */     return put(key, new JSONObject(value));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public JSONObject put(String key, Object value) throws JSONException {
/* 1866 */     if (key == null) {
/* 1867 */       throw new NullPointerException("Null key.");
/*      */     }
/* 1869 */     if (value != null) {
/* 1870 */       testValidity(value);
/* 1871 */       this.map.put(key, value);
/*      */     } else {
/* 1873 */       remove(key);
/*      */     } 
/* 1875 */     return this;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public JSONObject putOnce(String key, Object value) throws JSONException {
/* 1892 */     if (key != null && value != null) {
/* 1893 */       if (opt(key) != null) {
/* 1894 */         throw new JSONException("Duplicate key \"" + key + "\"");
/*      */       }
/* 1896 */       return put(key, value);
/*      */     } 
/* 1898 */     return this;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public JSONObject putOpt(String key, Object value) throws JSONException {
/* 1916 */     if (key != null && value != null) {
/* 1917 */       return put(key, value);
/*      */     }
/* 1919 */     return this;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Object query(String jsonPointer) {
/* 1942 */     return query(new JSONPointer(jsonPointer));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Object query(JSONPointer jsonPointer) {
/* 1964 */     return jsonPointer.queryFrom(this);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Object optQuery(String jsonPointer) {
/* 1976 */     return optQuery(new JSONPointer(jsonPointer));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Object optQuery(JSONPointer jsonPointer) {
/*      */     try {
/* 1989 */       return jsonPointer.queryFrom(this);
/* 1990 */     } catch (JSONPointerException e) {
/* 1991 */       return null;
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static String quote(String string) {
/* 2008 */     StringWriter sw = new StringWriter();
/* 2009 */     synchronized (sw.getBuffer()) {
/*      */       
/* 2011 */       return quote(string, sw).toString();
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static Writer quote(String string, Writer w) throws IOException {
/* 2020 */     if (string == null || string.isEmpty()) {
/* 2021 */       w.write("\"\"");
/* 2022 */       return w;
/*      */     } 
/*      */ 
/*      */     
/* 2026 */     char c = Character.MIN_VALUE;
/*      */ 
/*      */     
/* 2029 */     int len = string.length();
/*      */     
/* 2031 */     w.write(34);
/* 2032 */     for (int i = 0; i < len; i++) {
/* 2033 */       char b = c;
/* 2034 */       c = string.charAt(i);
/* 2035 */       switch (c) {
/*      */         case '"':
/*      */         case '\\':
/* 2038 */           w.write(92);
/* 2039 */           w.write(c);
/*      */           break;
/*      */         case '/':
/* 2042 */           if (b == '<') {
/* 2043 */             w.write(92);
/*      */           }
/* 2045 */           w.write(c);
/*      */           break;
/*      */         case '\b':
/* 2048 */           w.write("\\b");
/*      */           break;
/*      */         case '\t':
/* 2051 */           w.write("\\t");
/*      */           break;
/*      */         case '\n':
/* 2054 */           w.write("\\n");
/*      */           break;
/*      */         case '\f':
/* 2057 */           w.write("\\f");
/*      */           break;
/*      */         case '\r':
/* 2060 */           w.write("\\r");
/*      */           break;
/*      */         default:
/* 2063 */           if (c < ' ' || (c >= '' && c < ' ') || (c >= ' ' && c < '℀')) {
/*      */             
/* 2065 */             w.write("\\u");
/* 2066 */             String hhhh = Integer.toHexString(c);
/* 2067 */             w.write("0000", 0, 4 - hhhh.length());
/* 2068 */             w.write(hhhh); break;
/*      */           } 
/* 2070 */           w.write(c);
/*      */           break;
/*      */       } 
/*      */     } 
/* 2074 */     w.write(34);
/* 2075 */     return w;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Object remove(String key) {
/* 2087 */     return this.map.remove(key);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean similar(Object other) {
/*      */     try {
/* 2100 */       if (!(other instanceof JSONObject)) {
/* 2101 */         return false;
/*      */       }
/* 2103 */       if (!keySet().equals(((JSONObject)other).keySet())) {
/* 2104 */         return false;
/*      */       }
/* 2106 */       for (Map.Entry<String, ?> entry : entrySet()) {
/* 2107 */         String name = entry.getKey();
/* 2108 */         Object valueThis = entry.getValue();
/* 2109 */         Object valueOther = ((JSONObject)other).get(name);
/* 2110 */         if (valueThis == valueOther) {
/*      */           continue;
/*      */         }
/* 2113 */         if (valueThis == null) {
/* 2114 */           return false;
/*      */         }
/* 2116 */         if (valueThis instanceof JSONObject) {
/* 2117 */           if (!((JSONObject)valueThis).similar(valueOther))
/* 2118 */             return false;  continue;
/*      */         } 
/* 2120 */         if (valueThis instanceof JSONArray) {
/* 2121 */           if (!((JSONArray)valueThis).similar(valueOther))
/* 2122 */             return false;  continue;
/*      */         } 
/* 2124 */         if (valueThis instanceof Number && valueOther instanceof Number) {
/* 2125 */           if (!isNumberSimilar((Number)valueThis, (Number)valueOther))
/* 2126 */             return false;  continue;
/*      */         } 
/* 2128 */         if (valueThis instanceof JSONString && valueOther instanceof JSONString) {
/* 2129 */           if (!((JSONString)valueThis).toJSONString().equals(((JSONString)valueOther).toJSONString()))
/* 2130 */             return false;  continue;
/*      */         } 
/* 2132 */         if (!valueThis.equals(valueOther)) {
/* 2133 */           return false;
/*      */         }
/*      */       } 
/* 2136 */       return true;
/* 2137 */     } catch (Throwable exception) {
/* 2138 */       return false;
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   static boolean isNumberSimilar(Number l, Number r) {
/* 2158 */     if (!numberIsFinite(l) || !numberIsFinite(r))
/*      */     {
/* 2160 */       return false;
/*      */     }
/*      */ 
/*      */ 
/*      */     
/* 2165 */     if (l.getClass().equals(r.getClass()) && l instanceof Comparable) {
/*      */       
/* 2167 */       int compareTo = ((Comparable<Number>)l).compareTo(r);
/* 2168 */       return (compareTo == 0);
/*      */     } 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 2174 */     BigDecimal lBigDecimal = objectToBigDecimal(l, null, false);
/* 2175 */     BigDecimal rBigDecimal = objectToBigDecimal(r, null, false);
/* 2176 */     if (lBigDecimal == null || rBigDecimal == null) {
/* 2177 */       return false;
/*      */     }
/* 2179 */     return (lBigDecimal.compareTo(rBigDecimal) == 0);
/*      */   }
/*      */   
/*      */   private static boolean numberIsFinite(Number n) {
/* 2183 */     if (n instanceof Double && (((Double)n).isInfinite() || ((Double)n).isNaN()))
/* 2184 */       return false; 
/* 2185 */     if (n instanceof Float && (((Float)n).isInfinite() || ((Float)n).isNaN())) {
/* 2186 */       return false;
/*      */     }
/* 2188 */     return true;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected static boolean isDecimalNotation(String val) {
/* 2198 */     return (val.indexOf('.') > -1 || val.indexOf('e') > -1 || val
/* 2199 */       .indexOf('E') > -1 || "-0".equals(val));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected static Number stringToNumber(String val) throws NumberFormatException {
/* 2213 */     char initial = val.charAt(0);
/* 2214 */     if ((initial >= '0' && initial <= '9') || initial == '-') {
/*      */       
/* 2216 */       if (isDecimalNotation(val)) {
/*      */         
/*      */         try {
/*      */ 
/*      */           
/* 2221 */           BigDecimal bd = new BigDecimal(val);
/* 2222 */           if (initial == '-' && BigDecimal.ZERO.compareTo(bd) == 0) {
/* 2223 */             return Double.valueOf(-0.0D);
/*      */           }
/* 2225 */           return bd;
/* 2226 */         } catch (NumberFormatException retryAsDouble) {
/*      */           
/*      */           try {
/* 2229 */             Double d = Double.valueOf(val);
/* 2230 */             if (d.isNaN() || d.isInfinite()) {
/* 2231 */               throw new NumberFormatException("val [" + val + "] is not a valid number.");
/*      */             }
/* 2233 */             return d;
/* 2234 */           } catch (NumberFormatException ignore) {
/* 2235 */             throw new NumberFormatException("val [" + val + "] is not a valid number.");
/*      */           } 
/*      */         } 
/*      */       }
/*      */       
/* 2240 */       if (initial == '0' && val.length() > 1) {
/* 2241 */         char at1 = val.charAt(1);
/* 2242 */         if (at1 >= '0' && at1 <= '9') {
/* 2243 */           throw new NumberFormatException("val [" + val + "] is not a valid number.");
/*      */         }
/* 2245 */       } else if (initial == '-' && val.length() > 2) {
/* 2246 */         char at1 = val.charAt(1);
/* 2247 */         char at2 = val.charAt(2);
/* 2248 */         if (at1 == '0' && at2 >= '0' && at2 <= '9') {
/* 2249 */           throw new NumberFormatException("val [" + val + "] is not a valid number.");
/*      */         }
/*      */       } 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 2260 */       BigInteger bi = new BigInteger(val);
/* 2261 */       if (bi.bitLength() <= 31) {
/* 2262 */         return Integer.valueOf(bi.intValue());
/*      */       }
/* 2264 */       if (bi.bitLength() <= 63) {
/* 2265 */         return Long.valueOf(bi.longValue());
/*      */       }
/* 2267 */       return bi;
/*      */     } 
/* 2269 */     throw new NumberFormatException("val [" + val + "] is not a valid number.");
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static Object stringToValue(String string) {
/* 2285 */     if ("".equals(string)) {
/* 2286 */       return string;
/*      */     }
/*      */ 
/*      */     
/* 2290 */     if ("true".equalsIgnoreCase(string)) {
/* 2291 */       return Boolean.TRUE;
/*      */     }
/* 2293 */     if ("false".equalsIgnoreCase(string)) {
/* 2294 */       return Boolean.FALSE;
/*      */     }
/* 2296 */     if ("null".equalsIgnoreCase(string)) {
/* 2297 */       return NULL;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 2305 */     char initial = string.charAt(0);
/* 2306 */     if ((initial >= '0' && initial <= '9') || initial == '-') {
/*      */       try {
/* 2308 */         return stringToNumber(string);
/* 2309 */       } catch (Exception exception) {}
/*      */     }
/*      */     
/* 2312 */     return string;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void testValidity(Object o) throws JSONException {
/* 2324 */     if (o instanceof Number && !numberIsFinite((Number)o)) {
/* 2325 */       throw new JSONException("JSON does not allow non-finite numbers.");
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public JSONArray toJSONArray(JSONArray names) throws JSONException {
/* 2341 */     if (names == null || names.isEmpty()) {
/* 2342 */       return null;
/*      */     }
/* 2344 */     JSONArray ja = new JSONArray();
/* 2345 */     for (int i = 0; i < names.length(); i++) {
/* 2346 */       ja.put(opt(names.getString(i)));
/*      */     }
/* 2348 */     return ja;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public String toString() {
/*      */     try {
/* 2367 */       return toString(0);
/* 2368 */     } catch (Exception e) {
/* 2369 */       return null;
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public String toString(int indentFactor) throws JSONException {
/* 2401 */     StringWriter w = new StringWriter();
/* 2402 */     synchronized (w.getBuffer()) {
/* 2403 */       return write(w, indentFactor, 0).toString();
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static String valueToString(Object value) throws JSONException {
/* 2436 */     return JSONWriter.valueToString(value);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static Object wrap(Object object) {
/* 2452 */     return wrap(object, null);
/*      */   }
/*      */   
/*      */   private static Object wrap(Object object, Set<Object> objectsRecord) {
/*      */     try {
/* 2457 */       if (NULL.equals(object)) {
/* 2458 */         return NULL;
/*      */       }
/* 2460 */       if (object instanceof JSONObject || object instanceof JSONArray || NULL
/* 2461 */         .equals(object) || object instanceof JSONString || object instanceof Byte || object instanceof Character || object instanceof Short || object instanceof Integer || object instanceof Long || object instanceof Boolean || object instanceof Float || object instanceof Double || object instanceof String || object instanceof BigInteger || object instanceof BigDecimal || object instanceof Enum)
/*      */       {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */         
/* 2468 */         return object;
/*      */       }
/*      */       
/* 2471 */       if (object instanceof Collection) {
/* 2472 */         Collection<?> coll = (Collection)object;
/* 2473 */         return new JSONArray(coll);
/*      */       } 
/* 2475 */       if (object.getClass().isArray()) {
/* 2476 */         return new JSONArray(object);
/*      */       }
/* 2478 */       if (object instanceof Map) {
/* 2479 */         Map<?, ?> map = (Map<?, ?>)object;
/* 2480 */         return new JSONObject(map);
/*      */       } 
/* 2482 */       Package objectPackage = object.getClass().getPackage();
/*      */       
/* 2484 */       String objectPackageName = (objectPackage != null) ? objectPackage.getName() : "";
/* 2485 */       if (objectPackageName.startsWith("java.") || objectPackageName
/* 2486 */         .startsWith("javax.") || object
/* 2487 */         .getClass().getClassLoader() == null) {
/* 2488 */         return object.toString();
/*      */       }
/* 2490 */       if (objectsRecord != null) {
/* 2491 */         return new JSONObject(object, objectsRecord);
/*      */       }
/* 2493 */       return new JSONObject(object);
/*      */     }
/* 2495 */     catch (JSONException exception) {
/* 2496 */       throw exception;
/* 2497 */     } catch (Exception exception) {
/* 2498 */       return null;
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Writer write(Writer writer) throws JSONException {
/* 2513 */     return write(writer, 0, 0);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   static final Writer writeValue(Writer writer, Object value, int indentFactor, int indent) throws JSONException, IOException {
/* 2519 */     if (value == null || value.equals(null)) {
/* 2520 */       writer.write("null");
/* 2521 */     } else if (value instanceof JSONString) {
/*      */       Object o;
/*      */       try {
/* 2524 */         o = ((JSONString)value).toJSONString();
/* 2525 */       } catch (Exception e) {
/* 2526 */         throw new JSONException(e);
/*      */       } 
/* 2528 */       writer.write((o != null) ? o.toString() : quote(value.toString()));
/* 2529 */     } else if (value instanceof Number) {
/*      */       
/* 2531 */       String numberAsString = numberToString((Number)value);
/* 2532 */       if (NUMBER_PATTERN.matcher(numberAsString).matches()) {
/* 2533 */         writer.write(numberAsString);
/*      */       }
/*      */       else {
/*      */         
/* 2537 */         quote(numberAsString, writer);
/*      */       } 
/* 2539 */     } else if (value instanceof Boolean) {
/* 2540 */       writer.write(value.toString());
/* 2541 */     } else if (value instanceof Enum) {
/* 2542 */       writer.write(quote(((Enum)value).name()));
/* 2543 */     } else if (value instanceof JSONObject) {
/* 2544 */       ((JSONObject)value).write(writer, indentFactor, indent);
/* 2545 */     } else if (value instanceof JSONArray) {
/* 2546 */       ((JSONArray)value).write(writer, indentFactor, indent);
/* 2547 */     } else if (value instanceof Map) {
/* 2548 */       Map<?, ?> map = (Map<?, ?>)value;
/* 2549 */       (new JSONObject(map)).write(writer, indentFactor, indent);
/* 2550 */     } else if (value instanceof Collection) {
/* 2551 */       Collection<?> coll = (Collection)value;
/* 2552 */       (new JSONArray(coll)).write(writer, indentFactor, indent);
/* 2553 */     } else if (value.getClass().isArray()) {
/* 2554 */       (new JSONArray(value)).write(writer, indentFactor, indent);
/*      */     } else {
/* 2556 */       quote(value.toString(), writer);
/*      */     } 
/* 2558 */     return writer;
/*      */   }
/*      */   
/*      */   static final void indent(Writer writer, int indent) throws IOException {
/* 2562 */     for (int i = 0; i < indent; i++) {
/* 2563 */       writer.write(32);
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Writer write(Writer writer, int indentFactor, int indent) throws JSONException {
/*      */     try {
/* 2598 */       boolean needsComma = false;
/* 2599 */       int length = length();
/* 2600 */       writer.write(123);
/*      */       
/* 2602 */       if (length == 1) {
/* 2603 */         Map.Entry<String, ?> entry = entrySet().iterator().next();
/* 2604 */         String key = entry.getKey();
/* 2605 */         writer.write(quote(key));
/* 2606 */         writer.write(58);
/* 2607 */         if (indentFactor > 0) {
/* 2608 */           writer.write(32);
/*      */         }
/*      */         try {
/* 2611 */           writeValue(writer, entry.getValue(), indentFactor, indent);
/* 2612 */         } catch (Exception e) {
/* 2613 */           throw new JSONException("Unable to write JSONObject value for key: " + key, e);
/*      */         } 
/* 2615 */       } else if (length != 0) {
/* 2616 */         int newIndent = indent + indentFactor;
/* 2617 */         for (Map.Entry<String, ?> entry : entrySet()) {
/* 2618 */           if (needsComma) {
/* 2619 */             writer.write(44);
/*      */           }
/* 2621 */           if (indentFactor > 0) {
/* 2622 */             writer.write(10);
/*      */           }
/* 2624 */           indent(writer, newIndent);
/* 2625 */           String key = entry.getKey();
/* 2626 */           writer.write(quote(key));
/* 2627 */           writer.write(58);
/* 2628 */           if (indentFactor > 0) {
/* 2629 */             writer.write(32);
/*      */           }
/*      */           try {
/* 2632 */             writeValue(writer, entry.getValue(), indentFactor, newIndent);
/* 2633 */           } catch (Exception e) {
/* 2634 */             throw new JSONException("Unable to write JSONObject value for key: " + key, e);
/*      */           } 
/* 2636 */           needsComma = true;
/*      */         } 
/* 2638 */         if (indentFactor > 0) {
/* 2639 */           writer.write(10);
/*      */         }
/* 2641 */         indent(writer, indent);
/*      */       } 
/* 2643 */       writer.write(125);
/* 2644 */       return writer;
/* 2645 */     } catch (IOException exception) {
/* 2646 */       throw new JSONException(exception);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Map<String, Object> toMap() {
/* 2660 */     Map<String, Object> results = new HashMap<String, Object>();
/* 2661 */     for (Map.Entry<String, Object> entry : entrySet()) {
/*      */       Object value;
/* 2663 */       if (entry.getValue() == null || NULL.equals(entry.getValue())) {
/* 2664 */         value = null;
/* 2665 */       } else if (entry.getValue() instanceof JSONObject) {
/* 2666 */         value = ((JSONObject)entry.getValue()).toMap();
/* 2667 */       } else if (entry.getValue() instanceof JSONArray) {
/* 2668 */         value = ((JSONArray)entry.getValue()).toList();
/*      */       } else {
/* 2670 */         value = entry.getValue();
/*      */       } 
/* 2672 */       results.put(entry.getKey(), value);
/*      */     } 
/* 2674 */     return results;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static JSONException wrongValueFormatException(String key, String valueType, Object value, Throwable cause) {
/* 2689 */     if (value == null)
/*      */     {
/* 2691 */       return new JSONException("JSONObject[" + 
/* 2692 */           quote(key) + "] is not a " + valueType + " (null).", cause);
/*      */     }
/*      */ 
/*      */     
/* 2696 */     if (value instanceof Map || value instanceof Iterable || value instanceof JSONObject) {
/* 2697 */       return new JSONException("JSONObject[" + 
/* 2698 */           quote(key) + "] is not a " + valueType + " (" + value.getClass() + ").", cause);
/*      */     }
/*      */     
/* 2701 */     return new JSONException("JSONObject[" + 
/* 2702 */         quote(key) + "] is not a " + valueType + " (" + value.getClass() + " : " + value + ").", cause);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static JSONException recursivelyDefinedObjectException(String key) {
/* 2712 */     return new JSONException("JavaBean object contains recursively defined member variable of key " + 
/* 2713 */         quote(key));
/*      */   }
/*      */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\org\json\JSONObject.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */