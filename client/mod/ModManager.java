/*    */ package client.mod;
/*    */ 
/*    */ import client.mod.impl.ToggleSprint;
/*    */ import java.util.ArrayList;
/*    */ 
/*    */ public class ModManager
/*    */ {
/*    */   public ToggleSprint toggleSprint;
/*    */   public ArrayList<Mod> mods;
/*    */   
/*    */   public ModManager() {
/* 12 */     (this.mods = new ArrayList<>()).add(this.toggleSprint = new ToggleSprint());
/*    */   }
/*    */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\client\mod\ModManager.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */