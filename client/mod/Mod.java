/*    */ package client.mod;
/*    */ 
/*    */ import client.Client;
/*    */ import client.event.EventManager;
/*    */ import net.minecraft.client.Minecraft;
/*    */ 
/*    */ public class Mod {
/*    */   public Minecraft mc;
/*    */   public String name;
/*    */   public String description;
/*    */   public boolean enabled;
/*    */   public Category category;
/*    */   
/*    */   public Mod(String name, String description, Category category) {
/* 15 */     this.mc = Minecraft.getMinecraft();
/* 16 */     this.name = name;
/* 17 */     this.description = description;
/* 18 */     this.category = category;
/* 19 */     this.enabled = false;
/*    */   }
/*    */   
/*    */   public void onEnable() {
/* 23 */     EventManager eventManager = (Client.getInstance()).eventManager;
/* 24 */     EventManager.register(this);
/*    */   }
/*    */   
/*    */   public void onDisable() {
/* 28 */     EventManager eventManager = (Client.getInstance()).eventManager;
/* 29 */     EventManager.unregister(this);
/*    */   }
/*    */   
/*    */   public void setEnabled(boolean enabled) {
/* 33 */     this.enabled = enabled;
/* 34 */     if (enabled) {
/* 35 */       onEnable();
/*    */     } else {
/* 37 */       onDisable();
/*    */     } 
/*    */   }
/*    */   
/*    */   public void toggle() {
/* 42 */     setEnabled(!this.enabled);
/*    */   }
/*    */   
/*    */   public String getName() {
/* 46 */     return this.name;
/*    */   }
/*    */   
/*    */   public String getDescription() {
/* 50 */     return this.description;
/*    */   }
/*    */   
/*    */   public boolean isEnabled() {
/* 54 */     return this.enabled;
/*    */   }
/*    */   
/*    */   public Category getCategory() {
/* 58 */     return this.category;
/*    */   }
/*    */   
/*    */   public boolean isToggled() {
/* 62 */     return this.enabled;
/*    */   }
/*    */   
/*    */   public boolean isDisabled() {
/* 66 */     return !this.enabled;
/*    */   }
/*    */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\client\mod\Mod.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */