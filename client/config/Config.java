/*    */ package client.config;
/*    */ 
/*    */ import client.Client;
/*    */ import client.hud.HudMod;
/*    */ import java.io.File;
/*    */ import java.io.IOException;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class Config
/*    */ {
/* 16 */   public File configFolder = new File("Client");
/* 17 */   public Configuration configToSave = ConfigurationAPI.newConfiguration(new File(this.configFolder + "/Config.json"));
/*    */   
/*    */   public Configuration config;
/*    */   
/*    */   public void saveModConfig() {
/* 22 */     if (!this.configFolder.exists()) {
/* 23 */       this.configFolder.mkdirs();
/*    */     }
/* 25 */     System.out.println("Saving Mods Config");
/* 26 */     for (HudMod m : (Client.getInstance()).hudManager.hudMods) {
/* 27 */       this.configToSave.set(String.valueOf(m.name.toLowerCase()) + " x", Integer.valueOf(m.x()));
/* 28 */       this.configToSave.set(String.valueOf(m.name.toLowerCase()) + " y", Integer.valueOf(m.y()));
/* 29 */       this.configToSave.set(String.valueOf(m.name.toLowerCase()) + " enabled", Boolean.valueOf(m.isEnabled()));
/*    */     } 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */     
/*    */     try {
/* 38 */       this.configToSave.save();
/* 39 */     } catch (IOException e) {
/* 40 */       e.printStackTrace();
/*    */     } 
/*    */   }
/*    */   
/*    */   public void loadModConfig() {
/*    */     try {
/* 46 */       this.config = ConfigurationAPI.loadExistingConfiguration(new File(this.configFolder + "/Config.json"));
/* 47 */     } catch (IOException e) {
/* 48 */       e.printStackTrace();
/*    */     } 
/*    */   }
/*    */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\client\config\Config.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */