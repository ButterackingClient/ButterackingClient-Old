/*    */ package net.minecraft.src;
/*    */ 
/*    */ import java.io.File;
/*    */ import kp.mcinterface.IMinecraftInterface;
/*    */ import net.minecraft.client.Minecraft;
/*    */ import net.minecraft.client.gui.GuiScreen;
/*    */ import net.minecraft.util.ChatAllowedCharacters;
/*    */ import org.lwjgl.input.Keyboard;
/*    */ import org.lwjgl.input.Mouse;
/*    */ 
/*    */ public class VanillaInterface
/*    */   implements IMinecraftInterface
/*    */ {
/*    */   public boolean isAllowedCharacter(char p_isAllowedCharacter_1_) {
/* 15 */     return ChatAllowedCharacters.isAllowedCharacter(p_isAllowedCharacter_1_);
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean isKeyDown(int p_isKeyDown_1_) {
/* 20 */     return Keyboard.isKeyDown(p_isKeyDown_1_);
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean isCtrlKeyDown() {
/* 25 */     return GuiScreen.isCtrlKeyDown();
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean isShiftKeyDown() {
/* 30 */     return GuiScreen.isShiftKeyDown();
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean isAltKeyDown() {
/* 35 */     return GuiScreen.isAltKeyDown();
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean isOnGuiChat() {
/* 40 */     return (Minecraft.getMinecraft()).currentScreen instanceof net.minecraft.client.gui.GuiChat;
/*    */   }
/*    */ 
/*    */   
/*    */   public File getMinecraftDir() {
/* 45 */     return (Minecraft.getMinecraft()).mcDataDir;
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean getEventButtonState() {
/* 50 */     return Mouse.getEventButtonState();
/*    */   }
/*    */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\minecraft\src\VanillaInterface.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */