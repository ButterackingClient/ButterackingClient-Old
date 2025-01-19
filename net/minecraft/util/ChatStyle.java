/*     */ package net.minecraft.util;
/*     */ 
/*     */ import com.google.gson.JsonDeserializationContext;
/*     */ import com.google.gson.JsonDeserializer;
/*     */ import com.google.gson.JsonElement;
/*     */ import com.google.gson.JsonObject;
/*     */ import com.google.gson.JsonParseException;
/*     */ import com.google.gson.JsonPrimitive;
/*     */ import com.google.gson.JsonSerializationContext;
/*     */ import com.google.gson.JsonSerializer;
/*     */ import java.lang.reflect.Type;
/*     */ import net.minecraft.event.ClickEvent;
/*     */ import net.minecraft.event.HoverEvent;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class ChatStyle
/*     */ {
/*     */   private ChatStyle parentStyle;
/*     */   private EnumChatFormatting color;
/*     */   private Boolean bold;
/*     */   private Boolean italic;
/*     */   private Boolean underlined;
/*     */   private Boolean strikethrough;
/*     */   private Boolean obfuscated;
/*     */   private ClickEvent chatClickEvent;
/*     */   private HoverEvent chatHoverEvent;
/*     */   private String insertion;
/*     */   
/*  35 */   private static final ChatStyle rootStyle = new ChatStyle() {
/*     */       public EnumChatFormatting getColor() {
/*  37 */         return null;
/*     */       }
/*     */       
/*     */       public boolean getBold() {
/*  41 */         return false;
/*     */       }
/*     */       
/*     */       public boolean getItalic() {
/*  45 */         return false;
/*     */       }
/*     */       
/*     */       public boolean getStrikethrough() {
/*  49 */         return false;
/*     */       }
/*     */       
/*     */       public boolean getUnderlined() {
/*  53 */         return false;
/*     */       }
/*     */       
/*     */       public boolean getObfuscated() {
/*  57 */         return false;
/*     */       }
/*     */       
/*     */       public ClickEvent getChatClickEvent() {
/*  61 */         return null;
/*     */       }
/*     */       
/*     */       public HoverEvent getChatHoverEvent() {
/*  65 */         return null;
/*     */       }
/*     */       
/*     */       public String getInsertion() {
/*  69 */         return null;
/*     */       }
/*     */       
/*     */       public ChatStyle setColor(EnumChatFormatting color) {
/*  73 */         throw new UnsupportedOperationException();
/*     */       }
/*     */       
/*     */       public ChatStyle setBold(Boolean boldIn) {
/*  77 */         throw new UnsupportedOperationException();
/*     */       }
/*     */       
/*     */       public ChatStyle setItalic(Boolean italic) {
/*  81 */         throw new UnsupportedOperationException();
/*     */       }
/*     */       
/*     */       public ChatStyle setStrikethrough(Boolean strikethrough) {
/*  85 */         throw new UnsupportedOperationException();
/*     */       }
/*     */       
/*     */       public ChatStyle setUnderlined(Boolean underlined) {
/*  89 */         throw new UnsupportedOperationException();
/*     */       }
/*     */       
/*     */       public ChatStyle setObfuscated(Boolean obfuscated) {
/*  93 */         throw new UnsupportedOperationException();
/*     */       }
/*     */       
/*     */       public ChatStyle setChatClickEvent(ClickEvent event) {
/*  97 */         throw new UnsupportedOperationException();
/*     */       }
/*     */       
/*     */       public ChatStyle setChatHoverEvent(HoverEvent event) {
/* 101 */         throw new UnsupportedOperationException();
/*     */       }
/*     */       
/*     */       public ChatStyle setParentStyle(ChatStyle parent) {
/* 105 */         throw new UnsupportedOperationException();
/*     */       }
/*     */       
/*     */       public String toString() {
/* 109 */         return "Style.ROOT";
/*     */       }
/*     */       
/*     */       public ChatStyle createShallowCopy() {
/* 113 */         return this;
/*     */       }
/*     */       
/*     */       public ChatStyle createDeepCopy() {
/* 117 */         return this;
/*     */       }
/*     */       
/*     */       public String getFormattingCode() {
/* 121 */         return "";
/*     */       }
/*     */     };
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public EnumChatFormatting getColor() {
/* 129 */     return (this.color == null) ? getParent().getColor() : this.color;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean getBold() {
/* 136 */     return (this.bold == null) ? getParent().getBold() : this.bold.booleanValue();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean getItalic() {
/* 143 */     return (this.italic == null) ? getParent().getItalic() : this.italic.booleanValue();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean getStrikethrough() {
/* 150 */     return (this.strikethrough == null) ? getParent().getStrikethrough() : this.strikethrough.booleanValue();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean getUnderlined() {
/* 157 */     return (this.underlined == null) ? getParent().getUnderlined() : this.underlined.booleanValue();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean getObfuscated() {
/* 164 */     return (this.obfuscated == null) ? getParent().getObfuscated() : this.obfuscated.booleanValue();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isEmpty() {
/* 171 */     return (this.bold == null && this.italic == null && this.strikethrough == null && this.underlined == null && this.obfuscated == null && this.color == null && this.chatClickEvent == null && this.chatHoverEvent == null);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ClickEvent getChatClickEvent() {
/* 178 */     return (this.chatClickEvent == null) ? getParent().getChatClickEvent() : this.chatClickEvent;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public HoverEvent getChatHoverEvent() {
/* 185 */     return (this.chatHoverEvent == null) ? getParent().getChatHoverEvent() : this.chatHoverEvent;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getInsertion() {
/* 192 */     return (this.insertion == null) ? getParent().getInsertion() : this.insertion;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ChatStyle setColor(EnumChatFormatting color) {
/* 200 */     this.color = color;
/* 201 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ChatStyle setBold(Boolean boldIn) {
/* 209 */     this.bold = boldIn;
/* 210 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ChatStyle setItalic(Boolean italic) {
/* 218 */     this.italic = italic;
/* 219 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ChatStyle setStrikethrough(Boolean strikethrough) {
/* 227 */     this.strikethrough = strikethrough;
/* 228 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ChatStyle setUnderlined(Boolean underlined) {
/* 236 */     this.underlined = underlined;
/* 237 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ChatStyle setObfuscated(Boolean obfuscated) {
/* 245 */     this.obfuscated = obfuscated;
/* 246 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ChatStyle setChatClickEvent(ClickEvent event) {
/* 253 */     this.chatClickEvent = event;
/* 254 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ChatStyle setChatHoverEvent(HoverEvent event) {
/* 261 */     this.chatHoverEvent = event;
/* 262 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ChatStyle setInsertion(String insertion) {
/* 269 */     this.insertion = insertion;
/* 270 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ChatStyle setParentStyle(ChatStyle parent) {
/* 278 */     this.parentStyle = parent;
/* 279 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getFormattingCode() {
/* 286 */     if (isEmpty()) {
/* 287 */       return (this.parentStyle != null) ? this.parentStyle.getFormattingCode() : "";
/*     */     }
/* 289 */     StringBuilder stringbuilder = new StringBuilder();
/*     */     
/* 291 */     if (getColor() != null) {
/* 292 */       stringbuilder.append(getColor());
/*     */     }
/*     */     
/* 295 */     if (getBold()) {
/* 296 */       stringbuilder.append(EnumChatFormatting.BOLD);
/*     */     }
/*     */     
/* 299 */     if (getItalic()) {
/* 300 */       stringbuilder.append(EnumChatFormatting.ITALIC);
/*     */     }
/*     */     
/* 303 */     if (getUnderlined()) {
/* 304 */       stringbuilder.append(EnumChatFormatting.UNDERLINE);
/*     */     }
/*     */     
/* 307 */     if (getObfuscated()) {
/* 308 */       stringbuilder.append(EnumChatFormatting.OBFUSCATED);
/*     */     }
/*     */     
/* 311 */     if (getStrikethrough()) {
/* 312 */       stringbuilder.append(EnumChatFormatting.STRIKETHROUGH);
/*     */     }
/*     */     
/* 315 */     return stringbuilder.toString();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private ChatStyle getParent() {
/* 323 */     return (this.parentStyle == null) ? rootStyle : this.parentStyle;
/*     */   }
/*     */   
/*     */   public String toString() {
/* 327 */     return "Style{hasParent=" + ((this.parentStyle != null) ? 1 : 0) + ", color=" + this.color + ", bold=" + this.bold + ", italic=" + this.italic + ", underlined=" + this.underlined + ", obfuscated=" + this.obfuscated + ", clickEvent=" + getChatClickEvent() + ", hoverEvent=" + getChatHoverEvent() + ", insertion=" + getInsertion() + '}';
/*     */   }
/*     */   
/*     */   public boolean equals(Object p_equals_1_) {
/* 331 */     if (this == p_equals_1_)
/* 332 */       return true; 
/* 333 */     if (!(p_equals_1_ instanceof ChatStyle)) {
/* 334 */       return false;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/* 339 */     ChatStyle chatstyle = (ChatStyle)p_equals_1_;
/*     */     
/* 341 */     if (getBold() == chatstyle.getBold() && getColor() == chatstyle.getColor() && getItalic() == chatstyle.getItalic() && getObfuscated() == chatstyle.getObfuscated() && getStrikethrough() == chatstyle.getStrikethrough() && getUnderlined() == chatstyle.getUnderlined())
/*     */     {
/*     */       
/* 344 */       if ((getChatClickEvent() != null) ? 
/* 345 */         !getChatClickEvent().equals(chatstyle.getChatClickEvent()) : (
/*     */ 
/*     */         
/* 348 */         chatstyle.getChatClickEvent() != null))
/*     */       {
/*     */ 
/*     */         
/* 352 */         if ((getChatHoverEvent() != null) ? 
/* 353 */           !getChatHoverEvent().equals(chatstyle.getChatHoverEvent()) : (
/*     */ 
/*     */           
/* 356 */           chatstyle.getChatHoverEvent() != null))
/*     */         {
/*     */ 
/*     */           
/* 360 */           if ((getInsertion() != null) ? 
/* 361 */             getInsertion().equals(chatstyle.getInsertion()) : (
/*     */ 
/*     */             
/* 364 */             chatstyle.getInsertion() == null)) {
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */             
/* 373 */             boolean bool = true;
/* 374 */             return bool;
/*     */           }  }  }  } 
/*     */     boolean flag = false;
/*     */     return flag;
/*     */   } public int hashCode() {
/* 379 */     int i = this.color.hashCode();
/* 380 */     i = 31 * i + this.bold.hashCode();
/* 381 */     i = 31 * i + this.italic.hashCode();
/* 382 */     i = 31 * i + this.underlined.hashCode();
/* 383 */     i = 31 * i + this.strikethrough.hashCode();
/* 384 */     i = 31 * i + this.obfuscated.hashCode();
/* 385 */     i = 31 * i + this.chatClickEvent.hashCode();
/* 386 */     i = 31 * i + this.chatHoverEvent.hashCode();
/* 387 */     i = 31 * i + this.insertion.hashCode();
/* 388 */     return i;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ChatStyle createShallowCopy() {
/* 397 */     ChatStyle chatstyle = new ChatStyle();
/* 398 */     chatstyle.bold = this.bold;
/* 399 */     chatstyle.italic = this.italic;
/* 400 */     chatstyle.strikethrough = this.strikethrough;
/* 401 */     chatstyle.underlined = this.underlined;
/* 402 */     chatstyle.obfuscated = this.obfuscated;
/* 403 */     chatstyle.color = this.color;
/* 404 */     chatstyle.chatClickEvent = this.chatClickEvent;
/* 405 */     chatstyle.chatHoverEvent = this.chatHoverEvent;
/* 406 */     chatstyle.parentStyle = this.parentStyle;
/* 407 */     chatstyle.insertion = this.insertion;
/* 408 */     return chatstyle;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ChatStyle createDeepCopy() {
/* 416 */     ChatStyle chatstyle = new ChatStyle();
/* 417 */     chatstyle.setBold(Boolean.valueOf(getBold()));
/* 418 */     chatstyle.setItalic(Boolean.valueOf(getItalic()));
/* 419 */     chatstyle.setStrikethrough(Boolean.valueOf(getStrikethrough()));
/* 420 */     chatstyle.setUnderlined(Boolean.valueOf(getUnderlined()));
/* 421 */     chatstyle.setObfuscated(Boolean.valueOf(getObfuscated()));
/* 422 */     chatstyle.setColor(getColor());
/* 423 */     chatstyle.setChatClickEvent(getChatClickEvent());
/* 424 */     chatstyle.setChatHoverEvent(getChatHoverEvent());
/* 425 */     chatstyle.setInsertion(getInsertion());
/* 426 */     return chatstyle;
/*     */   }
/*     */   
/*     */   public static class Serializer implements JsonDeserializer<ChatStyle>, JsonSerializer<ChatStyle> {
/*     */     public ChatStyle deserialize(JsonElement p_deserialize_1_, Type p_deserialize_2_, JsonDeserializationContext p_deserialize_3_) throws JsonParseException {
/* 431 */       if (p_deserialize_1_.isJsonObject()) {
/* 432 */         ChatStyle chatstyle = new ChatStyle();
/* 433 */         JsonObject jsonobject = p_deserialize_1_.getAsJsonObject();
/*     */         
/* 435 */         if (jsonobject == null) {
/* 436 */           return null;
/*     */         }
/* 438 */         if (jsonobject.has("bold")) {
/* 439 */           chatstyle.bold = Boolean.valueOf(jsonobject.get("bold").getAsBoolean());
/*     */         }
/*     */         
/* 442 */         if (jsonobject.has("italic")) {
/* 443 */           chatstyle.italic = Boolean.valueOf(jsonobject.get("italic").getAsBoolean());
/*     */         }
/*     */         
/* 446 */         if (jsonobject.has("underlined")) {
/* 447 */           chatstyle.underlined = Boolean.valueOf(jsonobject.get("underlined").getAsBoolean());
/*     */         }
/*     */         
/* 450 */         if (jsonobject.has("strikethrough")) {
/* 451 */           chatstyle.strikethrough = Boolean.valueOf(jsonobject.get("strikethrough").getAsBoolean());
/*     */         }
/*     */         
/* 454 */         if (jsonobject.has("obfuscated")) {
/* 455 */           chatstyle.obfuscated = Boolean.valueOf(jsonobject.get("obfuscated").getAsBoolean());
/*     */         }
/*     */         
/* 458 */         if (jsonobject.has("color")) {
/* 459 */           chatstyle.color = (EnumChatFormatting)p_deserialize_3_.deserialize(jsonobject.get("color"), EnumChatFormatting.class);
/*     */         }
/*     */         
/* 462 */         if (jsonobject.has("insertion")) {
/* 463 */           chatstyle.insertion = jsonobject.get("insertion").getAsString();
/*     */         }
/*     */         
/* 466 */         if (jsonobject.has("clickEvent")) {
/* 467 */           JsonObject jsonobject1 = jsonobject.getAsJsonObject("clickEvent");
/*     */           
/* 469 */           if (jsonobject1 != null) {
/* 470 */             JsonPrimitive jsonprimitive = jsonobject1.getAsJsonPrimitive("action");
/* 471 */             ClickEvent.Action clickevent$action = (jsonprimitive == null) ? null : ClickEvent.Action.getValueByCanonicalName(jsonprimitive.getAsString());
/* 472 */             JsonPrimitive jsonprimitive1 = jsonobject1.getAsJsonPrimitive("value");
/* 473 */             String s = (jsonprimitive1 == null) ? null : jsonprimitive1.getAsString();
/*     */             
/* 475 */             if (clickevent$action != null && s != null && clickevent$action.shouldAllowInChat()) {
/* 476 */               chatstyle.chatClickEvent = new ClickEvent(clickevent$action, s);
/*     */             }
/*     */           } 
/*     */         } 
/*     */         
/* 481 */         if (jsonobject.has("hoverEvent")) {
/* 482 */           JsonObject jsonobject2 = jsonobject.getAsJsonObject("hoverEvent");
/*     */           
/* 484 */           if (jsonobject2 != null) {
/* 485 */             JsonPrimitive jsonprimitive2 = jsonobject2.getAsJsonPrimitive("action");
/* 486 */             HoverEvent.Action hoverevent$action = (jsonprimitive2 == null) ? null : HoverEvent.Action.getValueByCanonicalName(jsonprimitive2.getAsString());
/* 487 */             IChatComponent ichatcomponent = (IChatComponent)p_deserialize_3_.deserialize(jsonobject2.get("value"), IChatComponent.class);
/*     */             
/* 489 */             if (hoverevent$action != null && ichatcomponent != null && hoverevent$action.shouldAllowInChat()) {
/* 490 */               chatstyle.chatHoverEvent = new HoverEvent(hoverevent$action, ichatcomponent);
/*     */             }
/*     */           } 
/*     */         } 
/*     */         
/* 495 */         return chatstyle;
/*     */       } 
/*     */       
/* 498 */       return null;
/*     */     }
/*     */ 
/*     */     
/*     */     public JsonElement serialize(ChatStyle p_serialize_1_, Type p_serialize_2_, JsonSerializationContext p_serialize_3_) {
/* 503 */       if (p_serialize_1_.isEmpty()) {
/* 504 */         return null;
/*     */       }
/* 506 */       JsonObject jsonobject = new JsonObject();
/*     */       
/* 508 */       if (p_serialize_1_.bold != null) {
/* 509 */         jsonobject.addProperty("bold", p_serialize_1_.bold);
/*     */       }
/*     */       
/* 512 */       if (p_serialize_1_.italic != null) {
/* 513 */         jsonobject.addProperty("italic", p_serialize_1_.italic);
/*     */       }
/*     */       
/* 516 */       if (p_serialize_1_.underlined != null) {
/* 517 */         jsonobject.addProperty("underlined", p_serialize_1_.underlined);
/*     */       }
/*     */       
/* 520 */       if (p_serialize_1_.strikethrough != null) {
/* 521 */         jsonobject.addProperty("strikethrough", p_serialize_1_.strikethrough);
/*     */       }
/*     */       
/* 524 */       if (p_serialize_1_.obfuscated != null) {
/* 525 */         jsonobject.addProperty("obfuscated", p_serialize_1_.obfuscated);
/*     */       }
/*     */       
/* 528 */       if (p_serialize_1_.color != null) {
/* 529 */         jsonobject.add("color", p_serialize_3_.serialize(p_serialize_1_.color));
/*     */       }
/*     */       
/* 532 */       if (p_serialize_1_.insertion != null) {
/* 533 */         jsonobject.add("insertion", p_serialize_3_.serialize(p_serialize_1_.insertion));
/*     */       }
/*     */       
/* 536 */       if (p_serialize_1_.chatClickEvent != null) {
/* 537 */         JsonObject jsonobject1 = new JsonObject();
/* 538 */         jsonobject1.addProperty("action", p_serialize_1_.chatClickEvent.getAction().getCanonicalName());
/* 539 */         jsonobject1.addProperty("value", p_serialize_1_.chatClickEvent.getValue());
/* 540 */         jsonobject.add("clickEvent", (JsonElement)jsonobject1);
/*     */       } 
/*     */       
/* 543 */       if (p_serialize_1_.chatHoverEvent != null) {
/* 544 */         JsonObject jsonobject2 = new JsonObject();
/* 545 */         jsonobject2.addProperty("action", p_serialize_1_.chatHoverEvent.getAction().getCanonicalName());
/* 546 */         jsonobject2.add("value", p_serialize_3_.serialize(p_serialize_1_.chatHoverEvent.getValue()));
/* 547 */         jsonobject.add("hoverEvent", (JsonElement)jsonobject2);
/*     */       } 
/*     */       
/* 550 */       return (JsonElement)jsonobject;
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\minecraf\\util\ChatStyle.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */