/*     */ package net.optifine.gui;
/*     */ 
/*     */ import java.util.ArrayList;
/*     */ import java.util.Arrays;
/*     */ import java.util.List;
/*     */ import net.minecraft.client.gui.FontRenderer;
/*     */ import net.minecraft.client.gui.GuiButton;
/*     */ import net.minecraft.client.settings.GameSettings;
/*     */ import net.minecraft.src.Config;
/*     */ import net.optifine.Lang;
/*     */ import net.optifine.shaders.config.ShaderOption;
/*     */ import net.optifine.shaders.gui.GuiButtonShaderOption;
/*     */ import net.optifine.util.StrUtils;
/*     */ 
/*     */ public class TooltipProviderShaderOptions
/*     */   extends TooltipProviderOptions {
/*     */   public String[] getTooltipLines(GuiButton btn, int width) {
/*  18 */     if (!(btn instanceof GuiButtonShaderOption)) {
/*  19 */       return null;
/*     */     }
/*  21 */     GuiButtonShaderOption guibuttonshaderoption = (GuiButtonShaderOption)btn;
/*  22 */     ShaderOption shaderoption = guibuttonshaderoption.getShaderOption();
/*  23 */     String[] astring = makeTooltipLines(shaderoption, width);
/*  24 */     return astring;
/*     */   }
/*     */ 
/*     */   
/*     */   private String[] makeTooltipLines(ShaderOption so, int width) {
/*  29 */     String s = so.getNameText();
/*  30 */     String s1 = Config.normalize(so.getDescriptionText()).trim();
/*  31 */     String[] astring = splitDescription(s1);
/*  32 */     GameSettings gamesettings = Config.getGameSettings();
/*  33 */     String s2 = null;
/*     */     
/*  35 */     if (!s.equals(so.getName()) && gamesettings.advancedItemTooltips) {
/*  36 */       s2 = "§8" + Lang.get("of.general.id") + ": " + so.getName();
/*     */     }
/*     */     
/*  39 */     String s3 = null;
/*     */     
/*  41 */     if (so.getPaths() != null && gamesettings.advancedItemTooltips) {
/*  42 */       s3 = "§8" + Lang.get("of.general.from") + ": " + Config.arrayToString((Object[])so.getPaths());
/*     */     }
/*     */     
/*  45 */     String s4 = null;
/*     */     
/*  47 */     if (so.getValueDefault() != null && gamesettings.advancedItemTooltips) {
/*  48 */       String s5 = so.isEnabled() ? so.getValueText(so.getValueDefault()) : Lang.get("of.general.ambiguous");
/*  49 */       s4 = "§8" + Lang.getDefault() + ": " + s5;
/*     */     } 
/*     */     
/*  52 */     List<String> list = new ArrayList<>();
/*  53 */     list.add(s);
/*  54 */     list.addAll(Arrays.asList(astring));
/*     */     
/*  56 */     if (s2 != null) {
/*  57 */       list.add(s2);
/*     */     }
/*     */     
/*  60 */     if (s3 != null) {
/*  61 */       list.add(s3);
/*     */     }
/*     */     
/*  64 */     if (s4 != null) {
/*  65 */       list.add(s4);
/*     */     }
/*     */     
/*  68 */     String[] astring1 = makeTooltipLines(width, list);
/*  69 */     return astring1;
/*     */   }
/*     */   
/*     */   private String[] splitDescription(String desc) {
/*  73 */     if (desc.length() <= 0) {
/*  74 */       return new String[0];
/*     */     }
/*  76 */     desc = StrUtils.removePrefix(desc, "//");
/*  77 */     String[] astring = desc.split("\\. ");
/*     */     
/*  79 */     for (int i = 0; i < astring.length; i++) {
/*  80 */       astring[i] = "- " + astring[i].trim();
/*  81 */       astring[i] = StrUtils.removeSuffix(astring[i], ".");
/*     */     } 
/*     */     
/*  84 */     return astring;
/*     */   }
/*     */ 
/*     */   
/*     */   private String[] makeTooltipLines(int width, List<String> args) {
/*  89 */     FontRenderer fontrenderer = (Config.getMinecraft()).fontRendererObj;
/*  90 */     List<String> list = new ArrayList<>();
/*     */     
/*  92 */     for (int i = 0; i < args.size(); i++) {
/*  93 */       String s = args.get(i);
/*     */       
/*  95 */       if (s != null && s.length() > 0) {
/*  96 */         for (String s1 : fontrenderer.listFormattedStringToWidth(s, width)) {
/*  97 */           list.add(s1);
/*     */         }
/*     */       }
/*     */     } 
/*     */     
/* 102 */     String[] astring = list.<String>toArray(new String[list.size()]);
/* 103 */     return astring;
/*     */   }
/*     */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\optifine\gui\TooltipProviderShaderOptions.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */