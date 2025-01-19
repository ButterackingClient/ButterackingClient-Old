/*    */ package net.minecraft.realms;
/*    */ 
/*    */ import java.lang.reflect.Constructor;
/*    */ import net.minecraft.client.Minecraft;
/*    */ import net.minecraft.client.gui.GuiScreen;
/*    */ import net.minecraft.client.gui.GuiScreenRealmsProxy;
/*    */ import org.apache.logging.log4j.LogManager;
/*    */ import org.apache.logging.log4j.Logger;
/*    */ 
/*    */ public class RealmsBridge
/*    */   extends RealmsScreen {
/* 12 */   private static final Logger LOGGER = LogManager.getLogger();
/*    */   private GuiScreen previousScreen;
/*    */   
/*    */   public void switchToRealms(GuiScreen p_switchToRealms_1_) {
/* 16 */     this.previousScreen = p_switchToRealms_1_;
/*    */     
/*    */     try {
/* 19 */       Class<?> oclass = Class.forName("com.mojang.realmsclient.RealmsMainScreen");
/* 20 */       Constructor<?> constructor = oclass.getDeclaredConstructor(new Class[] { RealmsScreen.class });
/* 21 */       constructor.setAccessible(true);
/* 22 */       Object object = constructor.newInstance(new Object[] { this });
/* 23 */       Minecraft.getMinecraft().displayGuiScreen((GuiScreen)((RealmsScreen)object).getProxy());
/* 24 */     } catch (Exception exception) {
/* 25 */       LOGGER.error("Realms module missing", exception);
/*    */     } 
/*    */   }
/*    */   
/*    */   public GuiScreenRealmsProxy getNotificationScreen(GuiScreen p_getNotificationScreen_1_) {
/*    */     try {
/* 31 */       this.previousScreen = p_getNotificationScreen_1_;
/* 32 */       Class<?> oclass = Class.forName("com.mojang.realmsclient.gui.screens.RealmsNotificationsScreen");
/* 33 */       Constructor<?> constructor = oclass.getDeclaredConstructor(new Class[] { RealmsScreen.class });
/* 34 */       constructor.setAccessible(true);
/* 35 */       Object object = constructor.newInstance(new Object[] { this });
/* 36 */       return ((RealmsScreen)object).getProxy();
/* 37 */     } catch (Exception exception) {
/* 38 */       LOGGER.error("Realms module missing", exception);
/* 39 */       return null;
/*    */     } 
/*    */   }
/*    */   
/*    */   public void init() {
/* 44 */     Minecraft.getMinecraft().displayGuiScreen(this.previousScreen);
/*    */   }
/*    */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\minecraft\realms\RealmsBridge.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */