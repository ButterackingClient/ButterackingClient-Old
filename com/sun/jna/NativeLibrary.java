/*     */ package com.sun.jna;
/*     */ 
/*     */ import java.io.BufferedReader;
/*     */ import java.io.File;
/*     */ import java.io.FilenameFilter;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStreamReader;
/*     */ import java.lang.ref.Reference;
/*     */ import java.lang.ref.WeakReference;
/*     */ import java.lang.reflect.Method;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Arrays;
/*     */ import java.util.Collection;
/*     */ import java.util.Collections;
/*     */ import java.util.HashMap;
/*     */ import java.util.HashSet;
/*     */ import java.util.LinkedHashSet;
/*     */ import java.util.LinkedList;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import java.util.Set;
/*     */ import java.util.StringTokenizer;
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
/*     */ 
/*     */ public class NativeLibrary
/*     */ {
/*     */   private long handle;
/*     */   private final String libraryName;
/*     */   private final String libraryPath;
/*  85 */   private final Map<String, Function> functions = new HashMap<String, Function>();
/*     */   
/*     */   final int callFlags;
/*     */   private String encoding;
/*     */   final Map<String, ?> options;
/*  90 */   private static final Map<String, Reference<NativeLibrary>> libraries = new HashMap<String, Reference<NativeLibrary>>();
/*  91 */   private static final Map<String, List<String>> searchPaths = Collections.synchronizedMap(new HashMap<String, List<String>>());
/*  92 */   private static final List<String> librarySearchPath = new ArrayList<String>();
/*     */   private static final int DEFAULT_OPEN_OPTIONS = -1;
/*     */   private static String functionKey(String name, int flags, String encoding) { return name + "|" + flags + "|" + encoding; } private NativeLibrary(String libraryName, String libraryPath, long handle, Map<String, ?> options) { this.libraryName = getLibraryName(libraryName); this.libraryPath = libraryPath; this.handle = handle; Object option = options.get("calling-convention"); int callingConvention = (option instanceof Number) ? ((Number)option).intValue() : 0; this.callFlags = callingConvention; this.options = options; this.encoding = (String)options.get("string-encoding"); if (this.encoding == null) this.encoding = Native.getDefaultStringEncoding();  if (Platform.isWindows() && "kernel32".equals(this.libraryName.toLowerCase())) synchronized (this.functions) { Function f = new Function(this, "GetLastError", 63, this.encoding) {
/*     */             Object invoke(Object[] args, Class<?> returnType, boolean b, int fixedArgs) { return Integer.valueOf(Native.getLastError()); } Object invoke(Method invokingMethod, Class<?>[] paramTypes, Class<?> returnType, Object[] inArgs, Map<String, ?> options) { return Integer.valueOf(Native.getLastError()); }
/*  96 */           }; this.functions.put(functionKey("GetLastError", this.callFlags, this.encoding), f); }   } private static int openFlags(Map<String, ?> options) { Object opt = options.get("open-flags"); if (opt instanceof Number) return ((Number)opt).intValue();  return -1; } private static NativeLibrary loadLibrary(String libraryName, Map<String, ?> options) { if (Native.DEBUG_LOAD) System.out.println("Looking for library '" + libraryName + "'");  boolean isAbsolutePath = (new File(libraryName)).isAbsolute(); List<String> searchPath = new ArrayList<String>(); int openFlags = openFlags(options); String webstartPath = Native.getWebStartLibraryPath(libraryName); if (webstartPath != null) { if (Native.DEBUG_LOAD) System.out.println("Adding web start path " + webstartPath);  searchPath.add(webstartPath); }  List<String> customPaths = searchPaths.get(libraryName); if (customPaths != null) synchronized (customPaths) { searchPath.addAll(0, customPaths); }   if (Native.DEBUG_LOAD) System.out.println("Adding paths from jna.library.path: " + System.getProperty("jna.library.path"));  searchPath.addAll(initPaths("jna.library.path")); String libraryPath = findLibraryPath(libraryName, searchPath); long handle = 0L; try { if (Native.DEBUG_LOAD) System.out.println("Trying " + libraryPath);  handle = Native.open(libraryPath, openFlags); } catch (UnsatisfiedLinkError e) { if (Native.DEBUG_LOAD) System.out.println("Adding system paths: " + librarySearchPath);  searchPath.addAll(librarySearchPath); }  try { if (handle == 0L) { libraryPath = findLibraryPath(libraryName, searchPath); if (Native.DEBUG_LOAD) System.out.println("Trying " + libraryPath);  handle = Native.open(libraryPath, openFlags); if (handle == 0L) throw new UnsatisfiedLinkError("Failed to load library '" + libraryName + "'");  }  } catch (UnsatisfiedLinkError e) { if (Platform.isAndroid()) { try { if (Native.DEBUG_LOAD) System.out.println("Preload (via System.loadLibrary) " + libraryName);  System.loadLibrary(libraryName); handle = Native.open(libraryPath, openFlags); } catch (UnsatisfiedLinkError e2) { e = e2; }  } else if (Platform.isLinux() || Platform.isFreeBSD()) { if (Native.DEBUG_LOAD) System.out.println("Looking for version variants");  libraryPath = matchLibrary(libraryName, searchPath); if (libraryPath != null) { if (Native.DEBUG_LOAD) System.out.println("Trying " + libraryPath);  try { handle = Native.open(libraryPath, openFlags); } catch (UnsatisfiedLinkError e2) { e = e2; }  }  } else if (Platform.isMac() && !libraryName.endsWith(".dylib")) { if (Native.DEBUG_LOAD) System.out.println("Looking for matching frameworks");  libraryPath = matchFramework(libraryName); if (libraryPath != null) try { if (Native.DEBUG_LOAD) System.out.println("Trying " + libraryPath);  handle = Native.open(libraryPath, openFlags); } catch (UnsatisfiedLinkError e2) { e = e2; }   } else if (Platform.isWindows() && !isAbsolutePath) { if (Native.DEBUG_LOAD) System.out.println("Looking for lib- prefix");  libraryPath = findLibraryPath("lib" + libraryName, searchPath); if (libraryPath != null) { if (Native.DEBUG_LOAD) System.out.println("Trying " + libraryPath);  try { handle = Native.open(libraryPath, openFlags); } catch (UnsatisfiedLinkError e2) { e = e2; }  }  }  if (handle == 0L) try { File embedded = Native.extractFromResourcePath(libraryName, (ClassLoader)options.get("classloader")); try { handle = Native.open(embedded.getAbsolutePath(), openFlags); libraryPath = embedded.getAbsolutePath(); } finally { if (Native.isUnpacked(embedded)) Native.deleteLibrary(embedded);  }  } catch (IOException e2) { e = new UnsatisfiedLinkError(e2.getMessage()); }   if (handle == 0L) throw new UnsatisfiedLinkError("Unable to load library '" + libraryName + "': " + e.getMessage());  }  if (Native.DEBUG_LOAD) System.out.println("Found library '" + libraryName + "' at " + libraryPath);  return new NativeLibrary(libraryName, libraryPath, handle, options); } static String matchFramework(String libraryName) { File framework = new File(libraryName); if (framework.isAbsolute()) { if (libraryName.indexOf(".framework") != -1 && framework.exists()) return framework.getAbsolutePath();  framework = new File(new File(framework.getParentFile(), framework.getName() + ".framework"), framework.getName()); if (framework.exists()) return framework.getAbsolutePath();  } else { String[] PREFIXES = { System.getProperty("user.home"), "", "/System" }; String suffix = (libraryName.indexOf(".framework") == -1) ? (libraryName + ".framework/" + libraryName) : libraryName; for (int i = 0; i < PREFIXES.length; i++) { String libraryPath = PREFIXES[i] + "/Library/Frameworks/" + suffix; if ((new File(libraryPath)).exists()) return libraryPath;  }  }  return null; } private String getLibraryName(String libraryName) { String simplified = libraryName; String BASE = "---"; String template = mapSharedLibraryName("---"); int prefixEnd = template.indexOf("---"); if (prefixEnd > 0 && simplified.startsWith(template.substring(0, prefixEnd))) simplified = simplified.substring(prefixEnd);  String suffix = template.substring(prefixEnd + "---".length()); int suffixStart = simplified.indexOf(suffix); if (suffixStart != -1) simplified = simplified.substring(0, suffixStart);  return simplified; } public static final NativeLibrary getInstance(String libraryName) { return getInstance(libraryName, Collections.emptyMap()); } public static final NativeLibrary getInstance(String libraryName, ClassLoader classLoader) { return getInstance(libraryName, Collections.singletonMap("classloader", classLoader)); } static { if (Native.POINTER_SIZE == 0) {
/*  97 */       throw new Error("Native library not initialized");
/*     */     }
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 848 */     String webstartPath = Native.getWebStartLibraryPath("jnidispatch");
/* 849 */     if (webstartPath != null) {
/* 850 */       librarySearchPath.add(webstartPath);
/*     */     }
/* 852 */     if (System.getProperty("jna.platform.library.path") == null && 
/* 853 */       !Platform.isWindows()) {
/*     */       
/* 855 */       String platformPath = "";
/* 856 */       String sep = "";
/* 857 */       String archPath = "";
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
/* 870 */       if (Platform.isLinux() || Platform.isSolaris() || 
/* 871 */         Platform.isFreeBSD() || Platform.iskFreeBSD())
/*     */       {
/* 873 */         archPath = (Platform.isSolaris() ? "/" : "") + (Pointer.SIZE * 8);
/*     */       }
/* 875 */       String[] paths = { "/usr/lib" + archPath, "/lib" + archPath, "/usr/lib", "/lib" };
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
/* 886 */       if (Platform.isLinux() || Platform.iskFreeBSD() || Platform.isGNU()) {
/* 887 */         String multiArchPath = getMultiArchPath();
/*     */ 
/*     */         
/* 890 */         paths = new String[] { "/usr/lib/" + multiArchPath, "/lib/" + multiArchPath, "/usr/lib" + archPath, "/lib" + archPath, "/usr/lib", "/lib" };
/*     */       } 
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
/* 906 */       if (Platform.isLinux()) {
/* 907 */         ArrayList<String> ldPaths = getLinuxLdPaths();
/*     */         
/* 909 */         for (int j = paths.length - 1; 0 <= j; j--) {
/* 910 */           int found = ldPaths.indexOf(paths[j]);
/* 911 */           if (found != -1) {
/* 912 */             ldPaths.remove(found);
/*     */           }
/* 914 */           ldPaths.add(0, paths[j]);
/*     */         } 
/* 916 */         paths = ldPaths.<String>toArray(new String[ldPaths.size()]);
/*     */       } 
/*     */       
/* 919 */       for (int i = 0; i < paths.length; i++) {
/* 920 */         File dir = new File(paths[i]);
/* 921 */         if (dir.exists() && dir.isDirectory()) {
/* 922 */           platformPath = platformPath + sep + paths[i];
/* 923 */           sep = File.pathSeparator;
/*     */         } 
/*     */       } 
/* 926 */       if (!"".equals(platformPath)) {
/* 927 */         System.setProperty("jna.platform.library.path", platformPath);
/*     */       }
/*     */     } 
/* 930 */     librarySearchPath.addAll(initPaths("jna.platform.library.path")); }
/*     */   public static final NativeLibrary getInstance(String libraryName, Map<String, ?> libraryOptions) { Map<String, Object> options = new HashMap<String, Object>(libraryOptions); if (options.get("calling-convention") == null) options.put("calling-convention", Integer.valueOf(0));  if ((Platform.isLinux() || Platform.isFreeBSD() || Platform.isAIX()) && Platform.C_LIBRARY_NAME.equals(libraryName)) libraryName = null;  synchronized (libraries) { Reference<NativeLibrary> ref = libraries.get(libraryName + options); NativeLibrary library = (ref != null) ? ref.get() : null; if (library == null) { if (libraryName == null) { library = new NativeLibrary("<process>", null, Native.open(null, openFlags(options)), options); } else { library = loadLibrary(libraryName, options); }  ref = new WeakReference<NativeLibrary>(library); libraries.put(library.getName() + options, ref); File file = library.getFile(); if (file != null) { libraries.put(file.getAbsolutePath() + options, ref); libraries.put(file.getName() + options, ref); }  }  return library; }  }
/*     */   public static final synchronized NativeLibrary getProcess() { return getInstance(null); }
/*     */   public static final synchronized NativeLibrary getProcess(Map<String, ?> options) { return getInstance((String)null, options); }
/* 934 */   public static final void addSearchPath(String libraryName, String path) { synchronized (searchPaths) { List<String> customPaths = searchPaths.get(libraryName); if (customPaths == null) { customPaths = Collections.synchronizedList(new ArrayList<String>()); searchPaths.put(libraryName, customPaths); }  customPaths.add(path); }  } public Function getFunction(String functionName) { return getFunction(functionName, this.callFlags); } Function getFunction(String name, Method method) { FunctionMapper mapper = (FunctionMapper)this.options.get("function-mapper"); if (mapper != null) name = mapper.getFunctionName(this, method);  String prefix = System.getProperty("jna.profiler.prefix", "$$YJP$$"); if (name.startsWith(prefix)) name = name.substring(prefix.length());  int flags = this.callFlags; Class<?>[] etypes = method.getExceptionTypes(); for (int i = 0; i < etypes.length; i++) { if (LastErrorException.class.isAssignableFrom(etypes[i])) flags |= 0x40;  }  return getFunction(name, flags); } public Function getFunction(String functionName, int callFlags) { return getFunction(functionName, callFlags, this.encoding); } private static String getMultiArchPath() { String cpu = Platform.ARCH;
/*     */ 
/*     */     
/* 937 */     String kernel = Platform.iskFreeBSD() ? "-kfreebsd" : (Platform.isGNU() ? "" : "-linux");
/* 938 */     String libc = "-gnu";
/*     */     
/* 940 */     if (Platform.isIntel()) {
/* 941 */       cpu = Platform.is64Bit() ? "x86_64" : "i386";
/*     */     }
/* 943 */     else if (Platform.isPPC()) {
/* 944 */       cpu = Platform.is64Bit() ? "powerpc64" : "powerpc";
/*     */     }
/* 946 */     else if (Platform.isARM()) {
/* 947 */       cpu = "arm";
/* 948 */       libc = "-gnueabi";
/*     */     }
/* 950 */     else if (Platform.ARCH.equals("mips64el")) {
/* 951 */       libc = "-gnuabi64";
/*     */     } 
/*     */     
/* 954 */     return cpu + kernel + libc; } public Function getFunction(String functionName, int callFlags, String encoding) { if (functionName == null) throw new NullPointerException("Function name may not be null");  synchronized (this.functions) { String key = functionKey(functionName, callFlags, encoding); Function function = this.functions.get(key); if (function == null) { function = new Function(this, functionName, callFlags, encoding); this.functions.put(key, function); }  return function; }  } public Map<String, ?> getOptions() { return this.options; } public Pointer getGlobalVariableAddress(String symbolName) { try { return new Pointer(getSymbolAddress(symbolName)); } catch (UnsatisfiedLinkError e) { throw new UnsatisfiedLinkError("Error looking up '" + symbolName + "': " + e.getMessage()); }  } long getSymbolAddress(String name) { if (this.handle == 0L) throw new UnsatisfiedLinkError("Library has been unloaded");  return Native.findSymbol(this.handle, name); } public String toString() { return "Native Library <" + this.libraryPath + "@" + this.handle + ">"; } public String getName() { return this.libraryName; } public File getFile() { if (this.libraryPath == null) return null;  return new File(this.libraryPath); } protected void finalize() { dispose(); } static void disposeAll() { Set<Reference<NativeLibrary>> values; synchronized (libraries) { values = new LinkedHashSet<Reference<NativeLibrary>>(libraries.values()); }  for (Reference<NativeLibrary> ref : values) { NativeLibrary lib = ref.get(); if (lib != null) lib.dispose();  }  } public void dispose() { Set<String> keys = new HashSet<String>(); synchronized (libraries) { for (Map.Entry<String, Reference<NativeLibrary>> e : libraries.entrySet()) { Reference<NativeLibrary> ref = e.getValue(); if (ref.get() == this) keys.add(e.getKey());  }  for (String k : keys) libraries.remove(k);  }  synchronized (this) { if (this.handle != 0L) { Native.close(this.handle); this.handle = 0L; }  }  } private static List<String> initPaths(String key) { String value = System.getProperty(key, ""); if ("".equals(value)) return Collections.emptyList();  StringTokenizer st = new StringTokenizer(value, File.pathSeparator); List<String> list = new ArrayList<String>(); while (st.hasMoreTokens()) { String path = st.nextToken(); if (!"".equals(path)) list.add(path);  }  return list; }
/*     */   private static String findLibraryPath(String libName, List<String> searchPath) { if ((new File(libName)).isAbsolute()) return libName;  String name = mapSharedLibraryName(libName); for (String path : searchPath) { File file = new File(path, name); if (file.exists()) return file.getAbsolutePath();  if (Platform.isMac()) if (name.endsWith(".dylib")) { file = new File(path, name.substring(0, name.lastIndexOf(".dylib")) + ".jnilib"); if (file.exists()) return file.getAbsolutePath();  }   }  return name; }
/*     */   static String mapSharedLibraryName(String libName) { if (Platform.isMac()) { if (libName.startsWith("lib") && (libName.endsWith(".dylib") || libName.endsWith(".jnilib"))) return libName;  String name = System.mapLibraryName(libName); if (name.endsWith(".jnilib")) return name.substring(0, name.lastIndexOf(".jnilib")) + ".dylib";  return name; }  if (Platform.isLinux() || Platform.isFreeBSD()) { if (isVersionedName(libName) || libName.endsWith(".so")) return libName;  } else if (Platform.isAIX()) { if (libName.startsWith("lib")) return libName;  } else if (Platform.isWindows() && (libName.endsWith(".drv") || libName.endsWith(".dll"))) { return libName; }  return System.mapLibraryName(libName); }
/*     */   private static boolean isVersionedName(String name) { if (name.startsWith("lib")) { int so = name.lastIndexOf(".so."); if (so != -1 && so + 4 < name.length()) { for (int i = so + 4; i < name.length(); i++) { char ch = name.charAt(i); if (!Character.isDigit(ch) && ch != '.') return false;  }  return true; }  }  return false; }
/*     */   static String matchLibrary(final String libName, List<String> searchPath) { File lib = new File(libName); if (lib.isAbsolute()) searchPath = Arrays.asList(new String[] { lib.getParent() });  FilenameFilter filter = new FilenameFilter() { public boolean accept(File dir, String filename) { return ((filename.startsWith("lib" + libName + ".so") || (filename.startsWith(libName + ".so") && libName.startsWith("lib"))) && NativeLibrary.isVersionedName(filename)); } }
/*     */       ; Collection<File> matches = new LinkedList<File>(); for (String path : searchPath) { File[] files = (new File(path)).listFiles(filter); if (files != null && files.length > 0) matches.addAll(Arrays.asList(files));  }  double bestVersion = -1.0D; String bestMatch = null; for (File f : matches) { String path = f.getAbsolutePath(); String ver = path.substring(path.lastIndexOf(".so.") + 4); double version = parseVersion(ver); if (version > bestVersion) { bestVersion = version; bestMatch = path; }  }  return bestMatch; }
/*     */   static double parseVersion(String ver) { double v = 0.0D; double divisor = 1.0D; int dot = ver.indexOf("."); while (ver != null) { String num; if (dot != -1) { num = ver.substring(0, dot); ver = ver.substring(dot + 1); dot = ver.indexOf("."); } else { num = ver; ver = null; }  try { v += Integer.parseInt(num) / divisor; } catch (NumberFormatException e) { return 0.0D; }  divisor *= 100.0D; }  return v; }
/* 961 */   private static ArrayList<String> getLinuxLdPaths() { ArrayList<String> ldPaths = new ArrayList<String>();
/*     */     try {
/* 963 */       Process process = Runtime.getRuntime().exec("/sbin/ldconfig -p");
/* 964 */       BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
/* 965 */       String buffer = "";
/* 966 */       while ((buffer = reader.readLine()) != null) {
/* 967 */         int startPath = buffer.indexOf(" => ");
/* 968 */         int endPath = buffer.lastIndexOf('/');
/* 969 */         if (startPath != -1 && endPath != -1 && startPath < endPath) {
/* 970 */           String path = buffer.substring(startPath + 4, endPath);
/* 971 */           if (!ldPaths.contains(path)) {
/* 972 */             ldPaths.add(path);
/*     */           }
/*     */         } 
/*     */       } 
/* 976 */       reader.close();
/* 977 */     } catch (Exception exception) {}
/*     */     
/* 979 */     return ldPaths; }
/*     */ 
/*     */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\com\sun\jna\NativeLibrary.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */