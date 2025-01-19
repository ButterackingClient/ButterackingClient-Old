/*    */ package net.optifine.entity.model.anim;
/*    */ 
/*    */ public class ModelUpdater {
/*    */   private ModelVariableUpdater[] modelVariableUpdaters;
/*    */   
/*    */   public ModelUpdater(ModelVariableUpdater[] modelVariableUpdaters) {
/*  7 */     this.modelVariableUpdaters = modelVariableUpdaters;
/*    */   }
/*    */   
/*    */   public void update() {
/* 11 */     for (int i = 0; i < this.modelVariableUpdaters.length; i++) {
/* 12 */       ModelVariableUpdater modelvariableupdater = this.modelVariableUpdaters[i];
/* 13 */       modelvariableupdater.update();
/*    */     } 
/*    */   }
/*    */   
/*    */   public boolean initialize(IModelResolver mr) {
/* 18 */     for (int i = 0; i < this.modelVariableUpdaters.length; i++) {
/* 19 */       ModelVariableUpdater modelvariableupdater = this.modelVariableUpdaters[i];
/*    */       
/* 21 */       if (!modelvariableupdater.initialize(mr)) {
/* 22 */         return false;
/*    */       }
/*    */     } 
/*    */     
/* 26 */     return true;
/*    */   }
/*    */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\optifine\entity\model\anim\ModelUpdater.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */