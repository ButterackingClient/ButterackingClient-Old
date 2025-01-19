/*     */ package net.minecraft.util;
/*     */ 
/*     */ import com.google.common.base.Function;
/*     */ import com.google.common.collect.Iterators;
/*     */ import com.google.common.collect.Lists;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
/*     */ 
/*     */ public abstract class ChatComponentStyle implements IChatComponent {
/*     */   public ChatComponentStyle() {
/*  11 */     this.siblings = Lists.newArrayList();
/*     */   }
/*     */   
/*     */   protected List<IChatComponent> siblings;
/*     */   private ChatStyle style;
/*     */   
/*     */   public IChatComponent appendSibling(IChatComponent component) {
/*  18 */     component.getChatStyle().setParentStyle(getChatStyle());
/*  19 */     this.siblings.add(component);
/*  20 */     return this;
/*     */   }
/*     */   
/*     */   public List<IChatComponent> getSiblings() {
/*  24 */     return this.siblings;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public IChatComponent appendText(String text) {
/*  31 */     return appendSibling(new ChatComponentText(text));
/*     */   }
/*     */   
/*     */   public IChatComponent setChatStyle(ChatStyle style) {
/*  35 */     this.style = style;
/*     */     
/*  37 */     for (IChatComponent ichatcomponent : this.siblings) {
/*  38 */       ichatcomponent.getChatStyle().setParentStyle(getChatStyle());
/*     */     }
/*     */     
/*  41 */     return this;
/*     */   }
/*     */   
/*     */   public ChatStyle getChatStyle() {
/*  45 */     if (this.style == null) {
/*  46 */       this.style = new ChatStyle();
/*     */       
/*  48 */       for (IChatComponent ichatcomponent : this.siblings) {
/*  49 */         ichatcomponent.getChatStyle().setParentStyle(this.style);
/*     */       }
/*     */     } 
/*     */     
/*  53 */     return this.style;
/*     */   }
/*     */   
/*     */   public Iterator<IChatComponent> iterator() {
/*  57 */     return Iterators.concat((Iterator)Iterators.forArray((Object[])new ChatComponentStyle[] { this }, ), createDeepCopyIterator(this.siblings));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final String getUnformattedText() {
/*  64 */     StringBuilder stringbuilder = new StringBuilder();
/*     */     
/*  66 */     for (IChatComponent ichatcomponent : this) {
/*  67 */       stringbuilder.append(ichatcomponent.getUnformattedTextForChat());
/*     */     }
/*     */     
/*  70 */     return stringbuilder.toString();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final String getFormattedText() {
/*  77 */     StringBuilder stringbuilder = new StringBuilder();
/*     */     
/*  79 */     for (IChatComponent ichatcomponent : this) {
/*  80 */       stringbuilder.append(ichatcomponent.getChatStyle().getFormattingCode());
/*  81 */       stringbuilder.append(ichatcomponent.getUnformattedTextForChat());
/*  82 */       stringbuilder.append(EnumChatFormatting.RESET);
/*     */     } 
/*     */     
/*  85 */     return stringbuilder.toString();
/*     */   }
/*     */   
/*     */   public static Iterator<IChatComponent> createDeepCopyIterator(Iterable<IChatComponent> components) {
/*  89 */     Iterator<IChatComponent> iterator = Iterators.concat(Iterators.transform(components.iterator(), new Function<IChatComponent, Iterator<IChatComponent>>() {
/*     */             public Iterator<IChatComponent> apply(IChatComponent p_apply_1_) {
/*  91 */               return p_apply_1_.iterator();
/*     */             }
/*     */           }));
/*  94 */     iterator = Iterators.transform(iterator, new Function<IChatComponent, IChatComponent>() {
/*     */           public IChatComponent apply(IChatComponent p_apply_1_) {
/*  96 */             IChatComponent ichatcomponent = p_apply_1_.createCopy();
/*  97 */             ichatcomponent.setChatStyle(ichatcomponent.getChatStyle().createDeepCopy());
/*  98 */             return ichatcomponent;
/*     */           }
/*     */         });
/* 101 */     return iterator;
/*     */   }
/*     */   
/*     */   public boolean equals(Object p_equals_1_) {
/* 105 */     if (this == p_equals_1_)
/* 106 */       return true; 
/* 107 */     if (!(p_equals_1_ instanceof ChatComponentStyle)) {
/* 108 */       return false;
/*     */     }
/* 110 */     ChatComponentStyle chatcomponentstyle = (ChatComponentStyle)p_equals_1_;
/* 111 */     return (this.siblings.equals(chatcomponentstyle.siblings) && getChatStyle().equals(chatcomponentstyle.getChatStyle()));
/*     */   }
/*     */ 
/*     */   
/*     */   public int hashCode() {
/* 116 */     return 31 * this.style.hashCode() + this.siblings.hashCode();
/*     */   }
/*     */   
/*     */   public String toString() {
/* 120 */     return "BaseComponent{style=" + this.style + ", siblings=" + this.siblings + '}';
/*     */   }
/*     */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\minecraf\\util\ChatComponentStyle.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */