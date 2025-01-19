/*     */ package net.minecraft.util;
/*     */ 
/*     */ import com.google.common.collect.Iterators;
/*     */ import com.google.common.collect.Lists;
/*     */ import java.util.Arrays;
/*     */ import java.util.IllegalFormatException;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
/*     */ import java.util.regex.Matcher;
/*     */ import java.util.regex.Pattern;
/*     */ 
/*     */ public class ChatComponentTranslation
/*     */   extends ChatComponentStyle {
/*     */   private final String key;
/*     */   private final Object[] formatArgs;
/*  16 */   private final Object syncLock = new Object();
/*  17 */   private long lastTranslationUpdateTimeInMilliseconds = -1L;
/*  18 */   List<IChatComponent> children = Lists.newArrayList();
/*  19 */   public static final Pattern stringVariablePattern = Pattern.compile("%(?:(\\d+)\\$)?([A-Za-z%]|$)");
/*     */   
/*     */   public ChatComponentTranslation(String translationKey, Object... args) {
/*  22 */     this.key = translationKey;
/*  23 */     this.formatArgs = args; byte b; int i;
/*     */     Object[] arrayOfObject;
/*  25 */     for (i = (arrayOfObject = args).length, b = 0; b < i; ) { Object object = arrayOfObject[b];
/*  26 */       if (object instanceof IChatComponent) {
/*  27 */         ((IChatComponent)object).getChatStyle().setParentStyle(getChatStyle());
/*     */       }
/*     */       b++; }
/*     */   
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   synchronized void ensureInitialized() {
/*  36 */     synchronized (this.syncLock) {
/*  37 */       long i = StatCollector.getLastTranslationUpdateTimeInMilliseconds();
/*     */       
/*  39 */       if (i == this.lastTranslationUpdateTimeInMilliseconds) {
/*     */         return;
/*     */       }
/*     */       
/*  43 */       this.lastTranslationUpdateTimeInMilliseconds = i;
/*  44 */       this.children.clear();
/*     */     } 
/*     */     
/*     */     try {
/*  48 */       initializeFromFormat(StatCollector.translateToLocal(this.key));
/*  49 */     } catch (ChatComponentTranslationFormatException chatcomponenttranslationformatexception) {
/*  50 */       this.children.clear();
/*     */       
/*     */       try {
/*  53 */         initializeFromFormat(StatCollector.translateToFallback(this.key));
/*  54 */       } catch (ChatComponentTranslationFormatException var5) {
/*  55 */         throw chatcomponenttranslationformatexception;
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void initializeFromFormat(String format) {
/*  64 */     boolean flag = false;
/*  65 */     Matcher matcher = stringVariablePattern.matcher(format);
/*  66 */     int i = 0;
/*  67 */     int j = 0;
/*     */ 
/*     */ 
/*     */     
/*     */     try {
/*  72 */       for (; matcher.find(j); j = l) {
/*  73 */         int k = matcher.start();
/*  74 */         int l = matcher.end();
/*     */         
/*  76 */         if (k > j) {
/*  77 */           ChatComponentText chatcomponenttext = new ChatComponentText(String.format(format.substring(j, k), new Object[0]));
/*  78 */           chatcomponenttext.getChatStyle().setParentStyle(getChatStyle());
/*  79 */           this.children.add(chatcomponenttext);
/*     */         } 
/*     */         
/*  82 */         String s2 = matcher.group(2);
/*  83 */         String s = format.substring(k, l);
/*     */         
/*  85 */         if ("%".equals(s2) && "%%".equals(s)) {
/*  86 */           ChatComponentText chatcomponenttext2 = new ChatComponentText("%");
/*  87 */           chatcomponenttext2.getChatStyle().setParentStyle(getChatStyle());
/*  88 */           this.children.add(chatcomponenttext2);
/*     */         } else {
/*  90 */           if (!"s".equals(s2)) {
/*  91 */             throw new ChatComponentTranslationFormatException(this, "Unsupported format: '" + s + "'");
/*     */           }
/*     */           
/*  94 */           String s1 = matcher.group(1);
/*  95 */           int i1 = (s1 != null) ? (Integer.parseInt(s1) - 1) : i++;
/*     */           
/*  97 */           if (i1 < this.formatArgs.length) {
/*  98 */             this.children.add(getFormatArgumentAsComponent(i1));
/*     */           }
/*     */         } 
/*     */       } 
/*     */       
/* 103 */       if (j < format.length()) {
/* 104 */         ChatComponentText chatcomponenttext1 = new ChatComponentText(String.format(format.substring(j), new Object[0]));
/* 105 */         chatcomponenttext1.getChatStyle().setParentStyle(getChatStyle());
/* 106 */         this.children.add(chatcomponenttext1);
/*     */       } 
/* 108 */     } catch (IllegalFormatException illegalformatexception) {
/* 109 */       throw new ChatComponentTranslationFormatException(this, illegalformatexception);
/*     */     } 
/*     */   }
/*     */   private IChatComponent getFormatArgumentAsComponent(int index) {
/*     */     IChatComponent ichatcomponent;
/* 114 */     if (index >= this.formatArgs.length) {
/* 115 */       throw new ChatComponentTranslationFormatException(this, index);
/*     */     }
/* 117 */     Object object = this.formatArgs[index];
/*     */ 
/*     */     
/* 120 */     if (object instanceof IChatComponent) {
/* 121 */       ichatcomponent = (IChatComponent)object;
/*     */     } else {
/* 123 */       ichatcomponent = new ChatComponentText((object == null) ? "null" : object.toString());
/* 124 */       ichatcomponent.getChatStyle().setParentStyle(getChatStyle());
/*     */     } 
/*     */     
/* 127 */     return ichatcomponent;
/*     */   }
/*     */ 
/*     */   
/*     */   public IChatComponent setChatStyle(ChatStyle style) {
/* 132 */     super.setChatStyle(style); byte b; int i;
/*     */     Object[] arrayOfObject;
/* 134 */     for (i = (arrayOfObject = this.formatArgs).length, b = 0; b < i; ) { Object object = arrayOfObject[b];
/* 135 */       if (object instanceof IChatComponent) {
/* 136 */         ((IChatComponent)object).getChatStyle().setParentStyle(getChatStyle());
/*     */       }
/*     */       b++; }
/*     */     
/* 140 */     if (this.lastTranslationUpdateTimeInMilliseconds > -1L) {
/* 141 */       for (IChatComponent ichatcomponent : this.children) {
/* 142 */         ichatcomponent.getChatStyle().setParentStyle(style);
/*     */       }
/*     */     }
/*     */     
/* 146 */     return this;
/*     */   }
/*     */   
/*     */   public Iterator<IChatComponent> iterator() {
/* 150 */     ensureInitialized();
/* 151 */     return Iterators.concat(createDeepCopyIterator(this.children), createDeepCopyIterator(this.siblings));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getUnformattedTextForChat() {
/* 159 */     ensureInitialized();
/* 160 */     StringBuilder stringbuilder = new StringBuilder();
/*     */     
/* 162 */     for (IChatComponent ichatcomponent : this.children) {
/* 163 */       stringbuilder.append(ichatcomponent.getUnformattedTextForChat());
/*     */     }
/*     */     
/* 166 */     return stringbuilder.toString();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ChatComponentTranslation createCopy() {
/* 173 */     Object[] aobject = new Object[this.formatArgs.length];
/*     */     
/* 175 */     for (int i = 0; i < this.formatArgs.length; i++) {
/* 176 */       if (this.formatArgs[i] instanceof IChatComponent) {
/* 177 */         aobject[i] = ((IChatComponent)this.formatArgs[i]).createCopy();
/*     */       } else {
/* 179 */         aobject[i] = this.formatArgs[i];
/*     */       } 
/*     */     } 
/*     */     
/* 183 */     ChatComponentTranslation chatcomponenttranslation = new ChatComponentTranslation(this.key, aobject);
/* 184 */     chatcomponenttranslation.setChatStyle(getChatStyle().createShallowCopy());
/*     */     
/* 186 */     for (IChatComponent ichatcomponent : getSiblings()) {
/* 187 */       chatcomponenttranslation.appendSibling(ichatcomponent.createCopy());
/*     */     }
/*     */     
/* 190 */     return chatcomponenttranslation;
/*     */   }
/*     */   
/*     */   public boolean equals(Object p_equals_1_) {
/* 194 */     if (this == p_equals_1_)
/* 195 */       return true; 
/* 196 */     if (!(p_equals_1_ instanceof ChatComponentTranslation)) {
/* 197 */       return false;
/*     */     }
/* 199 */     ChatComponentTranslation chatcomponenttranslation = (ChatComponentTranslation)p_equals_1_;
/* 200 */     return (Arrays.equals(this.formatArgs, chatcomponenttranslation.formatArgs) && this.key.equals(chatcomponenttranslation.key) && super.equals(p_equals_1_));
/*     */   }
/*     */ 
/*     */   
/*     */   public int hashCode() {
/* 205 */     int i = super.hashCode();
/* 206 */     i = 31 * i + this.key.hashCode();
/* 207 */     i = 31 * i + Arrays.hashCode(this.formatArgs);
/* 208 */     return i;
/*     */   }
/*     */   
/*     */   public String toString() {
/* 212 */     return "TranslatableComponent{key='" + this.key + '\'' + ", args=" + Arrays.toString(this.formatArgs) + ", siblings=" + this.siblings + ", style=" + getChatStyle() + '}';
/*     */   }
/*     */   
/*     */   public String getKey() {
/* 216 */     return this.key;
/*     */   }
/*     */   
/*     */   public Object[] getFormatArgs() {
/* 220 */     return this.formatArgs;
/*     */   }
/*     */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\minecraf\\util\ChatComponentTranslation.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */