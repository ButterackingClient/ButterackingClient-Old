/*     */ package net.optifine.shaders.config;
/*     */ 
/*     */ import net.minecraft.src.Config;
/*     */ import net.minecraft.util.Util;
/*     */ import net.optifine.shaders.Shaders;
/*     */ 
/*     */ public class ShaderMacros {
/*   8 */   private static String PREFIX_MACRO = "MC_";
/*     */   public static final String MC_VERSION = "MC_VERSION";
/*     */   public static final String MC_GL_VERSION = "MC_GL_VERSION";
/*     */   public static final String MC_GLSL_VERSION = "MC_GLSL_VERSION";
/*     */   public static final String MC_OS_WINDOWS = "MC_OS_WINDOWS";
/*     */   public static final String MC_OS_MAC = "MC_OS_MAC";
/*     */   public static final String MC_OS_LINUX = "MC_OS_LINUX";
/*     */   public static final String MC_OS_OTHER = "MC_OS_OTHER";
/*     */   public static final String MC_GL_VENDOR_ATI = "MC_GL_VENDOR_ATI";
/*     */   public static final String MC_GL_VENDOR_INTEL = "MC_GL_VENDOR_INTEL";
/*     */   public static final String MC_GL_VENDOR_NVIDIA = "MC_GL_VENDOR_NVIDIA";
/*     */   public static final String MC_GL_VENDOR_XORG = "MC_GL_VENDOR_XORG";
/*     */   public static final String MC_GL_VENDOR_OTHER = "MC_GL_VENDOR_OTHER";
/*     */   public static final String MC_GL_RENDERER_RADEON = "MC_GL_RENDERER_RADEON";
/*     */   public static final String MC_GL_RENDERER_GEFORCE = "MC_GL_RENDERER_GEFORCE";
/*     */   public static final String MC_GL_RENDERER_QUADRO = "MC_GL_RENDERER_QUADRO";
/*     */   public static final String MC_GL_RENDERER_INTEL = "MC_GL_RENDERER_INTEL";
/*     */   public static final String MC_GL_RENDERER_GALLIUM = "MC_GL_RENDERER_GALLIUM";
/*     */   public static final String MC_GL_RENDERER_MESA = "MC_GL_RENDERER_MESA";
/*     */   public static final String MC_GL_RENDERER_OTHER = "MC_GL_RENDERER_OTHER";
/*     */   public static final String MC_FXAA_LEVEL = "MC_FXAA_LEVEL";
/*     */   public static final String MC_NORMAL_MAP = "MC_NORMAL_MAP";
/*     */   public static final String MC_SPECULAR_MAP = "MC_SPECULAR_MAP";
/*     */   public static final String MC_RENDER_QUALITY = "MC_RENDER_QUALITY";
/*     */   public static final String MC_SHADOW_QUALITY = "MC_SHADOW_QUALITY";
/*     */   public static final String MC_HAND_DEPTH = "MC_HAND_DEPTH";
/*     */   public static final String MC_OLD_HAND_LIGHT = "MC_OLD_HAND_LIGHT";
/*     */   public static final String MC_OLD_LIGHTING = "MC_OLD_LIGHTING";
/*     */   private static ShaderMacro[] extensionMacros;
/*     */   
/*     */   public static String getOs() {
/*  39 */     Util.EnumOS util$enumos = Util.getOSType();
/*     */     
/*  41 */     switch (util$enumos) {
/*     */       case WINDOWS:
/*  43 */         return "MC_OS_WINDOWS";
/*     */       
/*     */       case OSX:
/*  46 */         return "MC_OS_MAC";
/*     */       
/*     */       case null:
/*  49 */         return "MC_OS_LINUX";
/*     */     } 
/*     */     
/*  52 */     return "MC_OS_OTHER";
/*     */   }
/*     */ 
/*     */   
/*     */   public static String getVendor() {
/*  57 */     String s = Config.openGlVendor;
/*     */     
/*  59 */     if (s == null) {
/*  60 */       return "MC_GL_VENDOR_OTHER";
/*     */     }
/*  62 */     s = s.toLowerCase();
/*  63 */     return s.startsWith("ati") ? "MC_GL_VENDOR_ATI" : (s.startsWith("intel") ? "MC_GL_VENDOR_INTEL" : (s.startsWith("nvidia") ? "MC_GL_VENDOR_NVIDIA" : (s.startsWith("x.org") ? "MC_GL_VENDOR_XORG" : "MC_GL_VENDOR_OTHER")));
/*     */   }
/*     */ 
/*     */   
/*     */   public static String getRenderer() {
/*  68 */     String s = Config.openGlRenderer;
/*     */     
/*  70 */     if (s == null) {
/*  71 */       return "MC_GL_RENDERER_OTHER";
/*     */     }
/*  73 */     s = s.toLowerCase();
/*  74 */     return s.startsWith("amd") ? "MC_GL_RENDERER_RADEON" : (s.startsWith("ati") ? "MC_GL_RENDERER_RADEON" : (s.startsWith("radeon") ? "MC_GL_RENDERER_RADEON" : (s.startsWith("gallium") ? "MC_GL_RENDERER_GALLIUM" : (s.startsWith("intel") ? "MC_GL_RENDERER_INTEL" : (s.startsWith("geforce") ? "MC_GL_RENDERER_GEFORCE" : (s.startsWith("nvidia") ? "MC_GL_RENDERER_GEFORCE" : (s.startsWith("quadro") ? "MC_GL_RENDERER_QUADRO" : (s.startsWith("nvs") ? "MC_GL_RENDERER_QUADRO" : (s.startsWith("mesa") ? "MC_GL_RENDERER_MESA" : "MC_GL_RENDERER_OTHER")))))))));
/*     */   }
/*     */ 
/*     */   
/*     */   public static String getPrefixMacro() {
/*  79 */     return PREFIX_MACRO;
/*     */   }
/*     */   
/*     */   public static ShaderMacro[] getExtensions() {
/*  83 */     if (extensionMacros == null) {
/*  84 */       String[] astring = Config.getOpenGlExtensions();
/*  85 */       ShaderMacro[] ashadermacro = new ShaderMacro[astring.length];
/*     */       
/*  87 */       for (int i = 0; i < astring.length; i++) {
/*  88 */         ashadermacro[i] = new ShaderMacro(String.valueOf(PREFIX_MACRO) + astring[i], "");
/*     */       }
/*     */       
/*  91 */       extensionMacros = ashadermacro;
/*     */     } 
/*     */     
/*  94 */     return extensionMacros;
/*     */   }
/*     */   
/*     */   public static String getFixedMacroLines() {
/*  98 */     StringBuilder stringbuilder = new StringBuilder();
/*  99 */     addMacroLine(stringbuilder, "MC_VERSION", Config.getMinecraftVersionInt());
/* 100 */     addMacroLine(stringbuilder, "MC_GL_VERSION " + Config.getGlVersion().toInt());
/* 101 */     addMacroLine(stringbuilder, "MC_GLSL_VERSION " + Config.getGlslVersion().toInt());
/* 102 */     addMacroLine(stringbuilder, getOs());
/* 103 */     addMacroLine(stringbuilder, getVendor());
/* 104 */     addMacroLine(stringbuilder, getRenderer());
/* 105 */     return stringbuilder.toString();
/*     */   }
/*     */   
/*     */   public static String getOptionMacroLines() {
/* 109 */     StringBuilder stringbuilder = new StringBuilder();
/*     */     
/* 111 */     if (Shaders.configAntialiasingLevel > 0) {
/* 112 */       addMacroLine(stringbuilder, "MC_FXAA_LEVEL", Shaders.configAntialiasingLevel);
/*     */     }
/*     */     
/* 115 */     if (Shaders.configNormalMap) {
/* 116 */       addMacroLine(stringbuilder, "MC_NORMAL_MAP");
/*     */     }
/*     */     
/* 119 */     if (Shaders.configSpecularMap) {
/* 120 */       addMacroLine(stringbuilder, "MC_SPECULAR_MAP");
/*     */     }
/*     */     
/* 123 */     addMacroLine(stringbuilder, "MC_RENDER_QUALITY", Shaders.configRenderResMul);
/* 124 */     addMacroLine(stringbuilder, "MC_SHADOW_QUALITY", Shaders.configShadowResMul);
/* 125 */     addMacroLine(stringbuilder, "MC_HAND_DEPTH", Shaders.configHandDepthMul);
/*     */     
/* 127 */     if (Shaders.isOldHandLight()) {
/* 128 */       addMacroLine(stringbuilder, "MC_OLD_HAND_LIGHT");
/*     */     }
/*     */     
/* 131 */     if (Shaders.isOldLighting()) {
/* 132 */       addMacroLine(stringbuilder, "MC_OLD_LIGHTING");
/*     */     }
/*     */     
/* 135 */     return stringbuilder.toString();
/*     */   }
/*     */   
/*     */   private static void addMacroLine(StringBuilder sb, String name, int value) {
/* 139 */     sb.append("#define ");
/* 140 */     sb.append(name);
/* 141 */     sb.append(" ");
/* 142 */     sb.append(value);
/* 143 */     sb.append("\n");
/*     */   }
/*     */   
/*     */   private static void addMacroLine(StringBuilder sb, String name, float value) {
/* 147 */     sb.append("#define ");
/* 148 */     sb.append(name);
/* 149 */     sb.append(" ");
/* 150 */     sb.append(value);
/* 151 */     sb.append("\n");
/*     */   }
/*     */   
/*     */   private static void addMacroLine(StringBuilder sb, String name) {
/* 155 */     sb.append("#define ");
/* 156 */     sb.append(name);
/* 157 */     sb.append("\n");
/*     */   }
/*     */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\optifine\shaders\config\ShaderMacros.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */