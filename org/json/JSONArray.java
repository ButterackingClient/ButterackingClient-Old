/*      */ package org.json;
/*      */ 
/*      */ import java.io.IOException;
/*      */ import java.io.StringWriter;
/*      */ import java.io.Writer;
/*      */ import java.lang.reflect.Array;
/*      */ import java.math.BigDecimal;
/*      */ import java.math.BigInteger;
/*      */ import java.util.ArrayList;
/*      */ import java.util.Collection;
/*      */ import java.util.Iterator;
/*      */ import java.util.List;
/*      */ import java.util.Map;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ public class JSONArray
/*      */   implements Iterable<Object>
/*      */ {
/*      */   private final ArrayList<Object> myArrayList;
/*      */   
/*      */   public JSONArray() {
/*   75 */     this.myArrayList = new ArrayList();
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
/*      */   public JSONArray(JSONTokener x) throws JSONException {
/*   87 */     this();
/*   88 */     if (x.nextClean() != '[') {
/*   89 */       throw x.syntaxError("A JSONArray text must start with '['");
/*      */     }
/*      */     
/*   92 */     char nextChar = x.nextClean();
/*   93 */     if (nextChar == '\000')
/*      */     {
/*   95 */       throw x.syntaxError("Expected a ',' or ']'");
/*      */     }
/*   97 */     if (nextChar != ']') {
/*   98 */       x.back();
/*      */       while (true) {
/*  100 */         if (x.nextClean() == ',') {
/*  101 */           x.back();
/*  102 */           this.myArrayList.add(JSONObject.NULL);
/*      */         } else {
/*  104 */           x.back();
/*  105 */           this.myArrayList.add(x.nextValue());
/*      */         } 
/*  107 */         switch (x.nextClean()) {
/*      */           
/*      */           case '\000':
/*  110 */             throw x.syntaxError("Expected a ',' or ']'");
/*      */           case ',':
/*  112 */             nextChar = x.nextClean();
/*  113 */             if (nextChar == '\000')
/*      */             {
/*  115 */               throw x.syntaxError("Expected a ',' or ']'");
/*      */             }
/*  117 */             if (nextChar == ']') {
/*      */               return;
/*      */             }
/*  120 */             x.back(); continue;
/*      */           case ']':
/*      */             return;
/*      */         }  break;
/*      */       } 
/*  125 */       throw x.syntaxError("Expected a ',' or ']'");
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
/*      */   public JSONArray(String source) throws JSONException {
/*  142 */     this(new JSONTokener(source));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public JSONArray(Collection<?> collection) {
/*  152 */     if (collection == null) {
/*  153 */       this.myArrayList = new ArrayList();
/*      */     } else {
/*  155 */       this.myArrayList = new ArrayList(collection.size());
/*  156 */       addAll(collection, true);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public JSONArray(Iterable<?> iter) {
/*  167 */     this();
/*  168 */     if (iter == null) {
/*      */       return;
/*      */     }
/*  171 */     addAll(iter, true);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public JSONArray(JSONArray array) {
/*  181 */     if (array == null) {
/*  182 */       this.myArrayList = new ArrayList();
/*      */     }
/*      */     else {
/*      */       
/*  186 */       this.myArrayList = new ArrayList(array.myArrayList);
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
/*      */   public JSONArray(Object array) throws JSONException {
/*  203 */     this();
/*  204 */     if (!array.getClass().isArray()) {
/*  205 */       throw new JSONException("JSONArray initial value should be a string or collection or array.");
/*      */     }
/*      */     
/*  208 */     addAll(array, true);
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
/*      */   public JSONArray(int initialCapacity) throws JSONException {
/*  220 */     if (initialCapacity < 0) {
/*  221 */       throw new JSONException("JSONArray initial capacity cannot be negative.");
/*      */     }
/*      */     
/*  224 */     this.myArrayList = new ArrayList(initialCapacity);
/*      */   }
/*      */ 
/*      */   
/*      */   public Iterator<Object> iterator() {
/*  229 */     return this.myArrayList.iterator();
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
/*      */   public Object get(int index) throws JSONException {
/*  242 */     Object object = opt(index);
/*  243 */     if (object == null) {
/*  244 */       throw new JSONException("JSONArray[" + index + "] not found.");
/*      */     }
/*  246 */     return object;
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
/*      */   public boolean getBoolean(int index) throws JSONException {
/*  261 */     Object object = get(index);
/*  262 */     if (object.equals(Boolean.FALSE) || (object instanceof String && ((String)object)
/*      */       
/*  264 */       .equalsIgnoreCase("false")))
/*  265 */       return false; 
/*  266 */     if (object.equals(Boolean.TRUE) || (object instanceof String && ((String)object)
/*      */       
/*  268 */       .equalsIgnoreCase("true"))) {
/*  269 */       return true;
/*      */     }
/*  271 */     throw wrongValueFormatException(index, "boolean", object, null);
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
/*      */   public double getDouble(int index) throws JSONException {
/*  285 */     Object object = get(index);
/*  286 */     if (object instanceof Number) {
/*  287 */       return ((Number)object).doubleValue();
/*      */     }
/*      */     try {
/*  290 */       return Double.parseDouble(object.toString());
/*  291 */     } catch (Exception e) {
/*  292 */       throw wrongValueFormatException(index, "double", object, e);
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
/*      */   public float getFloat(int index) throws JSONException {
/*  307 */     Object object = get(index);
/*  308 */     if (object instanceof Number) {
/*  309 */       return ((Number)object).floatValue();
/*      */     }
/*      */     try {
/*  312 */       return Float.parseFloat(object.toString());
/*  313 */     } catch (Exception e) {
/*  314 */       throw wrongValueFormatException(index, "float", object, e);
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
/*      */   public Number getNumber(int index) throws JSONException {
/*  329 */     Object object = get(index);
/*      */     try {
/*  331 */       if (object instanceof Number) {
/*  332 */         return (Number)object;
/*      */       }
/*  334 */       return JSONObject.stringToNumber(object.toString());
/*  335 */     } catch (Exception e) {
/*  336 */       throw wrongValueFormatException(index, "number", object, e);
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
/*      */   public <E extends Enum<E>> E getEnum(Class<E> clazz, int index) throws JSONException {
/*  355 */     E val = optEnum(clazz, index);
/*  356 */     if (val == null)
/*      */     {
/*      */ 
/*      */       
/*  360 */       throw wrongValueFormatException(index, "enum of type " + 
/*  361 */           JSONObject.quote(clazz.getSimpleName()), opt(index), null);
/*      */     }
/*  363 */     return val;
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
/*      */   public BigDecimal getBigDecimal(int index) throws JSONException {
/*  380 */     Object object = get(index);
/*  381 */     BigDecimal val = JSONObject.objectToBigDecimal(object, null);
/*  382 */     if (val == null) {
/*  383 */       throw wrongValueFormatException(index, "BigDecimal", object, null);
/*      */     }
/*  385 */     return val;
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
/*      */   public BigInteger getBigInteger(int index) throws JSONException {
/*  399 */     Object object = get(index);
/*  400 */     BigInteger val = JSONObject.objectToBigInteger(object, null);
/*  401 */     if (val == null) {
/*  402 */       throw wrongValueFormatException(index, "BigInteger", object, null);
/*      */     }
/*  404 */     return val;
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
/*      */   public int getInt(int index) throws JSONException {
/*  417 */     Object object = get(index);
/*  418 */     if (object instanceof Number) {
/*  419 */       return ((Number)object).intValue();
/*      */     }
/*      */     try {
/*  422 */       return Integer.parseInt(object.toString());
/*  423 */     } catch (Exception e) {
/*  424 */       throw wrongValueFormatException(index, "int", object, e);
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
/*      */   public JSONArray getJSONArray(int index) throws JSONException {
/*  439 */     Object object = get(index);
/*  440 */     if (object instanceof JSONArray) {
/*  441 */       return (JSONArray)object;
/*      */     }
/*  443 */     throw wrongValueFormatException(index, "JSONArray", object, null);
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
/*      */   public JSONObject getJSONObject(int index) throws JSONException {
/*  457 */     Object object = get(index);
/*  458 */     if (object instanceof JSONObject) {
/*  459 */       return (JSONObject)object;
/*      */     }
/*  461 */     throw wrongValueFormatException(index, "JSONObject", object, null);
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
/*      */   public long getLong(int index) throws JSONException {
/*  475 */     Object object = get(index);
/*  476 */     if (object instanceof Number) {
/*  477 */       return ((Number)object).longValue();
/*      */     }
/*      */     try {
/*  480 */       return Long.parseLong(object.toString());
/*  481 */     } catch (Exception e) {
/*  482 */       throw wrongValueFormatException(index, "long", object, e);
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
/*      */   public String getString(int index) throws JSONException {
/*  496 */     Object object = get(index);
/*  497 */     if (object instanceof String) {
/*  498 */       return (String)object;
/*      */     }
/*  500 */     throw wrongValueFormatException(index, "String", object, null);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean isNull(int index) {
/*  511 */     return JSONObject.NULL.equals(opt(index));
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
/*      */   public String join(String separator) throws JSONException {
/*  526 */     int len = length();
/*  527 */     if (len == 0) {
/*  528 */       return "";
/*      */     }
/*      */ 
/*      */     
/*  532 */     StringBuilder sb = new StringBuilder(JSONObject.valueToString(this.myArrayList.get(0)));
/*      */     
/*  534 */     for (int i = 1; i < len; i++) {
/*  535 */       sb.append(separator)
/*  536 */         .append(JSONObject.valueToString(this.myArrayList.get(i)));
/*      */     }
/*  538 */     return sb.toString();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int length() {
/*  547 */     return this.myArrayList.size();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void clear() {
/*  555 */     this.myArrayList.clear();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Object opt(int index) {
/*  566 */     return (index < 0 || index >= length()) ? null : this.myArrayList
/*  567 */       .get(index);
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
/*      */   public boolean optBoolean(int index) {
/*  580 */     return optBoolean(index, false);
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
/*      */   public boolean optBoolean(int index, boolean defaultValue) {
/*      */     try {
/*  596 */       return getBoolean(index);
/*  597 */     } catch (Exception e) {
/*  598 */       return defaultValue;
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
/*      */   public double optDouble(int index) {
/*  612 */     return optDouble(index, Double.NaN);
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
/*      */   public double optDouble(int index, double defaultValue) {
/*  627 */     Number val = optNumber(index, null);
/*  628 */     if (val == null) {
/*  629 */       return defaultValue;
/*      */     }
/*  631 */     double doubleValue = val.doubleValue();
/*      */ 
/*      */ 
/*      */     
/*  635 */     return doubleValue;
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
/*      */   public float optFloat(int index) {
/*  648 */     return optFloat(index, Float.NaN);
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
/*      */   public float optFloat(int index, float defaultValue) {
/*  663 */     Number val = optNumber(index, null);
/*  664 */     if (val == null) {
/*  665 */       return defaultValue;
/*      */     }
/*  667 */     float floatValue = val.floatValue();
/*      */ 
/*      */ 
/*      */     
/*  671 */     return floatValue;
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
/*      */   public int optInt(int index) {
/*  684 */     return optInt(index, 0);
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
/*      */   public int optInt(int index, int defaultValue) {
/*  699 */     Number val = optNumber(index, null);
/*  700 */     if (val == null) {
/*  701 */       return defaultValue;
/*      */     }
/*  703 */     return val.intValue();
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
/*      */   public <E extends Enum<E>> E optEnum(Class<E> clazz, int index) {
/*  718 */     return optEnum(clazz, index, null);
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
/*      */   public <E extends Enum<E>> E optEnum(Class<E> clazz, int index, E defaultValue) {
/*      */     try {
/*  737 */       Object val = opt(index);
/*  738 */       if (JSONObject.NULL.equals(val)) {
/*  739 */         return defaultValue;
/*      */       }
/*  741 */       if (clazz.isAssignableFrom(val.getClass()))
/*      */       {
/*      */         
/*  744 */         return (E)val;
/*      */       }
/*      */       
/*  747 */       return Enum.valueOf(clazz, val.toString());
/*  748 */     } catch (IllegalArgumentException e) {
/*  749 */       return defaultValue;
/*  750 */     } catch (NullPointerException e) {
/*  751 */       return defaultValue;
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
/*      */   public BigInteger optBigInteger(int index, BigInteger defaultValue) {
/*  767 */     Object val = opt(index);
/*  768 */     return JSONObject.objectToBigInteger(val, defaultValue);
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
/*      */   public BigDecimal optBigDecimal(int index, BigDecimal defaultValue) {
/*  786 */     Object val = opt(index);
/*  787 */     return JSONObject.objectToBigDecimal(val, defaultValue);
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
/*      */   public JSONArray optJSONArray(int index) {
/*  799 */     Object o = opt(index);
/*  800 */     return (o instanceof JSONArray) ? (JSONArray)o : null;
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
/*      */   public JSONObject optJSONObject(int index) {
/*  813 */     Object o = opt(index);
/*  814 */     return (o instanceof JSONObject) ? (JSONObject)o : null;
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
/*      */   public long optLong(int index) {
/*  827 */     return optLong(index, 0L);
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
/*      */   public long optLong(int index, long defaultValue) {
/*  842 */     Number val = optNumber(index, null);
/*  843 */     if (val == null) {
/*  844 */       return defaultValue;
/*      */     }
/*  846 */     return val.longValue();
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
/*      */   public Number optNumber(int index) {
/*  860 */     return optNumber(index, null);
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
/*      */   public Number optNumber(int index, Number defaultValue) {
/*  876 */     Object val = opt(index);
/*  877 */     if (JSONObject.NULL.equals(val)) {
/*  878 */       return defaultValue;
/*      */     }
/*  880 */     if (val instanceof Number) {
/*  881 */       return (Number)val;
/*      */     }
/*      */     
/*  884 */     if (val instanceof String) {
/*      */       try {
/*  886 */         return JSONObject.stringToNumber((String)val);
/*  887 */       } catch (Exception e) {
/*  888 */         return defaultValue;
/*      */       } 
/*      */     }
/*  891 */     return defaultValue;
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
/*      */   public String optString(int index) {
/*  904 */     return optString(index, "");
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
/*      */   public String optString(int index, String defaultValue) {
/*  918 */     Object object = opt(index);
/*  919 */     return JSONObject.NULL.equals(object) ? defaultValue : object
/*  920 */       .toString();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public JSONArray put(boolean value) {
/*  931 */     return put(value ? Boolean.TRUE : Boolean.FALSE);
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
/*      */   public JSONArray put(Collection<?> value) {
/*  945 */     return put(new JSONArray(value));
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
/*      */   public JSONArray put(double value) throws JSONException {
/*  958 */     return put(Double.valueOf(value));
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
/*      */   public JSONArray put(float value) throws JSONException {
/*  971 */     return put(Float.valueOf(value));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public JSONArray put(int value) {
/*  982 */     return put(Integer.valueOf(value));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public JSONArray put(long value) {
/*  993 */     return put(Long.valueOf(value));
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
/*      */   public JSONArray put(Map<?, ?> value) {
/* 1009 */     return put(new JSONObject(value));
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
/*      */   public JSONArray put(Object value) {
/* 1024 */     JSONObject.testValidity(value);
/* 1025 */     this.myArrayList.add(value);
/* 1026 */     return this;
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
/*      */   public JSONArray put(int index, boolean value) throws JSONException {
/* 1043 */     return put(index, value ? Boolean.TRUE : Boolean.FALSE);
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
/*      */   public JSONArray put(int index, Collection<?> value) throws JSONException {
/* 1059 */     return put(index, new JSONArray(value));
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
/*      */   public JSONArray put(int index, double value) throws JSONException {
/* 1076 */     return put(index, Double.valueOf(value));
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
/*      */   public JSONArray put(int index, float value) throws JSONException {
/* 1093 */     return put(index, Float.valueOf(value));
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
/*      */   public JSONArray put(int index, int value) throws JSONException {
/* 1110 */     return put(index, Integer.valueOf(value));
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
/*      */   public JSONArray put(int index, long value) throws JSONException {
/* 1127 */     return put(index, Long.valueOf(value));
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
/*      */   public JSONArray put(int index, Map<?, ?> value) throws JSONException {
/* 1146 */     put(index, new JSONObject(value));
/* 1147 */     return this;
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
/*      */   public JSONArray put(int index, Object value) throws JSONException {
/* 1167 */     if (index < 0) {
/* 1168 */       throw new JSONException("JSONArray[" + index + "] not found.");
/*      */     }
/* 1170 */     if (index < length()) {
/* 1171 */       JSONObject.testValidity(value);
/* 1172 */       this.myArrayList.set(index, value);
/* 1173 */       return this;
/*      */     } 
/* 1175 */     if (index == length())
/*      */     {
/* 1177 */       return put(value);
/*      */     }
/*      */ 
/*      */     
/* 1181 */     this.myArrayList.ensureCapacity(index + 1);
/* 1182 */     while (index != length())
/*      */     {
/* 1184 */       this.myArrayList.add(JSONObject.NULL);
/*      */     }
/* 1186 */     return put(value);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public JSONArray putAll(Collection<?> collection) {
/* 1197 */     addAll(collection, false);
/* 1198 */     return this;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public JSONArray putAll(Iterable<?> iter) {
/* 1209 */     addAll(iter, false);
/* 1210 */     return this;
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
/*      */   public JSONArray putAll(JSONArray array) {
/* 1223 */     this.myArrayList.addAll(array.myArrayList);
/* 1224 */     return this;
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
/*      */   public JSONArray putAll(Object array) throws JSONException {
/* 1241 */     addAll(array, false);
/* 1242 */     return this;
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
/* 1265 */     return query(new JSONPointer(jsonPointer));
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
/*      */   public Object query(JSONPointer jsonPointer) {
/* 1288 */     return jsonPointer.queryFrom(this);
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
/* 1300 */     return optQuery(new JSONPointer(jsonPointer));
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
/* 1313 */       return jsonPointer.queryFrom(this);
/* 1314 */     } catch (JSONPointerException e) {
/* 1315 */       return null;
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
/*      */   public Object remove(int index) {
/* 1328 */     return (index >= 0 && index < length()) ? this.myArrayList
/* 1329 */       .remove(index) : null;
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
/* 1341 */     if (!(other instanceof JSONArray)) {
/* 1342 */       return false;
/*      */     }
/* 1344 */     int len = length();
/* 1345 */     if (len != ((JSONArray)other).length()) {
/* 1346 */       return false;
/*      */     }
/* 1348 */     for (int i = 0; i < len; i++) {
/* 1349 */       Object valueThis = this.myArrayList.get(i);
/* 1350 */       Object valueOther = ((JSONArray)other).myArrayList.get(i);
/* 1351 */       if (valueThis != valueOther) {
/*      */ 
/*      */         
/* 1354 */         if (valueThis == null) {
/* 1355 */           return false;
/*      */         }
/* 1357 */         if (valueThis instanceof JSONObject) {
/* 1358 */           if (!((JSONObject)valueThis).similar(valueOther)) {
/* 1359 */             return false;
/*      */           }
/* 1361 */         } else if (valueThis instanceof JSONArray) {
/* 1362 */           if (!((JSONArray)valueThis).similar(valueOther)) {
/* 1363 */             return false;
/*      */           }
/* 1365 */         } else if (valueThis instanceof Number && valueOther instanceof Number) {
/* 1366 */           if (!JSONObject.isNumberSimilar((Number)valueThis, (Number)valueOther)) {
/* 1367 */             return false;
/*      */           }
/* 1369 */         } else if (valueThis instanceof JSONString && valueOther instanceof JSONString) {
/* 1370 */           if (!((JSONString)valueThis).toJSONString().equals(((JSONString)valueOther).toJSONString())) {
/* 1371 */             return false;
/*      */           }
/* 1373 */         } else if (!valueThis.equals(valueOther)) {
/* 1374 */           return false;
/*      */         } 
/*      */       } 
/* 1377 */     }  return true;
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
/*      */   public JSONObject toJSONObject(JSONArray names) throws JSONException {
/* 1393 */     if (names == null || names.isEmpty() || isEmpty()) {
/* 1394 */       return null;
/*      */     }
/* 1396 */     JSONObject jo = new JSONObject(names.length());
/* 1397 */     for (int i = 0; i < names.length(); i++) {
/* 1398 */       jo.put(names.getString(i), opt(i));
/*      */     }
/* 1400 */     return jo;
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
/*      */   public String toString() {
/*      */     try {
/* 1418 */       return toString(0);
/* 1419 */     } catch (Exception e) {
/* 1420 */       return null;
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
/*      */   public String toString(int indentFactor) throws JSONException {
/* 1453 */     StringWriter sw = new StringWriter();
/* 1454 */     synchronized (sw.getBuffer()) {
/* 1455 */       return write(sw, indentFactor, 0).toString();
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
/* 1470 */     return write(writer, 0, 0);
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
/*      */   public Writer write(Writer writer, int indentFactor, int indent) throws JSONException {
/*      */     try {
/* 1505 */       boolean needsComma = false;
/* 1506 */       int length = length();
/* 1507 */       writer.write(91);
/*      */       
/* 1509 */       if (length == 1) {
/*      */         try {
/* 1511 */           JSONObject.writeValue(writer, this.myArrayList.get(0), indentFactor, indent);
/*      */         }
/* 1513 */         catch (Exception e) {
/* 1514 */           throw new JSONException("Unable to write JSONArray value at index: 0", e);
/*      */         } 
/* 1516 */       } else if (length != 0) {
/* 1517 */         int newIndent = indent + indentFactor;
/*      */         
/* 1519 */         for (int i = 0; i < length; i++) {
/* 1520 */           if (needsComma) {
/* 1521 */             writer.write(44);
/*      */           }
/* 1523 */           if (indentFactor > 0) {
/* 1524 */             writer.write(10);
/*      */           }
/* 1526 */           JSONObject.indent(writer, newIndent);
/*      */           try {
/* 1528 */             JSONObject.writeValue(writer, this.myArrayList.get(i), indentFactor, newIndent);
/*      */           }
/* 1530 */           catch (Exception e) {
/* 1531 */             throw new JSONException("Unable to write JSONArray value at index: " + i, e);
/*      */           } 
/* 1533 */           needsComma = true;
/*      */         } 
/* 1535 */         if (indentFactor > 0) {
/* 1536 */           writer.write(10);
/*      */         }
/* 1538 */         JSONObject.indent(writer, indent);
/*      */       } 
/* 1540 */       writer.write(93);
/* 1541 */       return writer;
/* 1542 */     } catch (IOException e) {
/* 1543 */       throw new JSONException(e);
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
/*      */   public List<Object> toList() {
/* 1557 */     List<Object> results = new ArrayList(this.myArrayList.size());
/* 1558 */     for (Object element : this.myArrayList) {
/* 1559 */       if (element == null || JSONObject.NULL.equals(element)) {
/* 1560 */         results.add(null); continue;
/* 1561 */       }  if (element instanceof JSONArray) {
/* 1562 */         results.add(((JSONArray)element).toList()); continue;
/* 1563 */       }  if (element instanceof JSONObject) {
/* 1564 */         results.add(((JSONObject)element).toMap()); continue;
/*      */       } 
/* 1566 */       results.add(element);
/*      */     } 
/*      */     
/* 1569 */     return results;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean isEmpty() {
/* 1578 */     return this.myArrayList.isEmpty();
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
/*      */   private void addAll(Collection<?> collection, boolean wrap) {
/* 1592 */     this.myArrayList.ensureCapacity(this.myArrayList.size() + collection.size());
/* 1593 */     if (wrap) {
/* 1594 */       for (Object o : collection) {
/* 1595 */         put(JSONObject.wrap(o));
/*      */       }
/*      */     } else {
/* 1598 */       for (Object o : collection) {
/* 1599 */         put(o);
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
/*      */   private void addAll(Iterable<?> iter, boolean wrap) {
/* 1614 */     if (wrap) {
/* 1615 */       for (Object o : iter) {
/* 1616 */         put(JSONObject.wrap(o));
/*      */       }
/*      */     } else {
/* 1619 */       for (Object o : iter) {
/* 1620 */         put(o);
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
/*      */   private void addAll(Object array, boolean wrap) throws JSONException {
/* 1642 */     if (array.getClass().isArray()) {
/* 1643 */       int length = Array.getLength(array);
/* 1644 */       this.myArrayList.ensureCapacity(this.myArrayList.size() + length);
/* 1645 */       if (wrap) {
/* 1646 */         for (int i = 0; i < length; i++) {
/* 1647 */           put(JSONObject.wrap(Array.get(array, i)));
/*      */         }
/*      */       } else {
/* 1650 */         for (int i = 0; i < length; i++) {
/* 1651 */           put(Array.get(array, i));
/*      */         }
/*      */       } 
/* 1654 */     } else if (array instanceof JSONArray) {
/*      */ 
/*      */ 
/*      */       
/* 1658 */       this.myArrayList.addAll(((JSONArray)array).myArrayList);
/* 1659 */     } else if (array instanceof Collection) {
/* 1660 */       addAll((Collection)array, wrap);
/* 1661 */     } else if (array instanceof Iterable) {
/* 1662 */       addAll((Iterable)array, wrap);
/*      */     } else {
/* 1664 */       throw new JSONException("JSONArray initial value should be a string or collection or array.");
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
/*      */   private static JSONException wrongValueFormatException(int idx, String valueType, Object value, Throwable cause) {
/* 1681 */     if (value == null) {
/* 1682 */       return new JSONException("JSONArray[" + idx + "] is not a " + valueType + " (null).", cause);
/*      */     }
/*      */ 
/*      */ 
/*      */     
/* 1687 */     if (value instanceof Map || value instanceof Iterable || value instanceof JSONObject) {
/* 1688 */       return new JSONException("JSONArray[" + idx + "] is not a " + valueType + " (" + value
/* 1689 */           .getClass() + ").", cause);
/*      */     }
/*      */     
/* 1692 */     return new JSONException("JSONArray[" + idx + "] is not a " + valueType + " (" + value
/* 1693 */         .getClass() + " : " + value + ").", cause);
/*      */   }
/*      */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\org\json\JSONArray.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */