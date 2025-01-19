/*    */ package net.minecraft.event;
/*    */ 
/*    */ import com.google.common.collect.Maps;
/*    */ import java.util.Map;
/*    */ 
/*    */ public class ClickEvent
/*    */ {
/*    */   private final Action action;
/*    */   private final String value;
/*    */   
/*    */   public ClickEvent(Action theAction, String theValue) {
/* 12 */     this.action = theAction;
/* 13 */     this.value = theValue;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public Action getAction() {
/* 20 */     return this.action;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public String getValue() {
/* 28 */     return this.value;
/*    */   }
/*    */   
/*    */   public boolean equals(Object p_equals_1_) {
/* 32 */     if (this == p_equals_1_)
/* 33 */       return true; 
/* 34 */     if (p_equals_1_ != null && getClass() == p_equals_1_.getClass()) {
/* 35 */       ClickEvent clickevent = (ClickEvent)p_equals_1_;
/*    */       
/* 37 */       if (this.action != clickevent.action) {
/* 38 */         return false;
/*    */       }
/* 40 */       if (this.value != null) {
/* 41 */         if (!this.value.equals(clickevent.value)) {
/* 42 */           return false;
/*    */         }
/* 44 */       } else if (clickevent.value != null) {
/* 45 */         return false;
/*    */       } 
/*    */       
/* 48 */       return true;
/*    */     } 
/*    */     
/* 51 */     return false;
/*    */   }
/*    */ 
/*    */   
/*    */   public String toString() {
/* 56 */     return "ClickEvent{action=" + this.action + ", value='" + this.value + '\'' + '}';
/*    */   }
/*    */   
/*    */   public int hashCode() {
/* 60 */     int i = this.action.hashCode();
/* 61 */     i = 31 * i + ((this.value != null) ? this.value.hashCode() : 0);
/* 62 */     return i;
/*    */   }
/*    */   
/*    */   public enum Action {
/* 66 */     OPEN_URL("open_url", true),
/* 67 */     OPEN_FILE("open_file", false),
/* 68 */     RUN_COMMAND("run_command", true),
/* 69 */     TWITCH_USER_INFO("twitch_user_info", false),
/* 70 */     SUGGEST_COMMAND("suggest_command", true),
/* 71 */     CHANGE_PAGE("change_page", true);
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
/* 95 */       for (i = (arrayOfAction = values()).length, b = 0; b < i; ) { Action clickevent$action = arrayOfAction[b];
/* 96 */         nameMapping.put(clickevent$action.getCanonicalName(), clickevent$action);
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


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\minecraft\event\ClickEvent.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */