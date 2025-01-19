/*    */ package net.minecraft.event;
/*    */ 
/*    */ import com.google.common.collect.Maps;
/*    */ import java.util.Map;
/*    */ import net.minecraft.util.IChatComponent;
/*    */ 
/*    */ 
/*    */ public class HoverEvent
/*    */ {
/*    */   private final Action action;
/*    */   private final IChatComponent value;
/*    */   
/*    */   public HoverEvent(Action actionIn, IChatComponent valueIn) {
/* 14 */     this.action = actionIn;
/* 15 */     this.value = valueIn;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public Action getAction() {
/* 22 */     return this.action;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public IChatComponent getValue() {
/* 30 */     return this.value;
/*    */   }
/*    */   
/*    */   public boolean equals(Object p_equals_1_) {
/* 34 */     if (this == p_equals_1_)
/* 35 */       return true; 
/* 36 */     if (p_equals_1_ != null && getClass() == p_equals_1_.getClass()) {
/* 37 */       HoverEvent hoverevent = (HoverEvent)p_equals_1_;
/*    */       
/* 39 */       if (this.action != hoverevent.action) {
/* 40 */         return false;
/*    */       }
/* 42 */       if (this.value != null) {
/* 43 */         if (!this.value.equals(hoverevent.value)) {
/* 44 */           return false;
/*    */         }
/* 46 */       } else if (hoverevent.value != null) {
/* 47 */         return false;
/*    */       } 
/*    */       
/* 50 */       return true;
/*    */     } 
/*    */     
/* 53 */     return false;
/*    */   }
/*    */ 
/*    */   
/*    */   public String toString() {
/* 58 */     return "HoverEvent{action=" + this.action + ", value='" + this.value + '\'' + '}';
/*    */   }
/*    */   
/*    */   public int hashCode() {
/* 62 */     int i = this.action.hashCode();
/* 63 */     i = 31 * i + ((this.value != null) ? this.value.hashCode() : 0);
/* 64 */     return i;
/*    */   }
/*    */   
/*    */   public enum Action {
/* 68 */     SHOW_TEXT("show_text", true),
/* 69 */     SHOW_ACHIEVEMENT("show_achievement", true),
/* 70 */     SHOW_ITEM("show_item", true),
/* 71 */     SHOW_ENTITY("show_entity", true);
/*    */     
/* 73 */     private static final Map<String, Action> nameMapping = Maps.newHashMap();
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */     
/*    */     private final boolean allowedInChat;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */     
/*    */     private final String canonicalName;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */     
/*    */     static {
/*    */       byte b;
/*    */       int i;
/*    */       Action[] arrayOfAction;
/* 95 */       for (i = (arrayOfAction = values()).length, b = 0; b < i; ) { Action hoverevent$action = arrayOfAction[b];
/* 96 */         nameMapping.put(hoverevent$action.getCanonicalName(), hoverevent$action);
/*    */         b++; }
/*    */     
/*    */     }
/*    */     
/*    */     Action(String canonicalNameIn, boolean allowedInChatIn) {
/*    */       this.canonicalName = canonicalNameIn;
/*    */       this.allowedInChat = allowedInChatIn;
/*    */     }
/*    */     
/*    */     public boolean shouldAllowInChat() {
/*    */       return this.allowedInChat;
/*    */     }
/*    */     
/*    */     public String getCanonicalName() {
/*    */       return this.canonicalName;
/*    */     }
/*    */     
/*    */     public static Action getValueByCanonicalName(String canonicalNameIn) {
/*    */       return nameMapping.get(canonicalNameIn);
/*    */     }
/*    */   }
/*    */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\minecraft\event\HoverEvent.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */