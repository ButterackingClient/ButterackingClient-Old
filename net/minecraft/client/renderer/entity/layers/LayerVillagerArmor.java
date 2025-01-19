/*    */ package net.minecraft.client.renderer.entity.layers;
/*    */ import net.minecraft.client.model.ModelBiped;
/*    */ import net.minecraft.client.model.ModelZombieVillager;
/*    */ import net.minecraft.client.renderer.entity.RendererLivingEntity;
/*    */ 
/*    */ public class LayerVillagerArmor extends LayerBipedArmor {
/*    */   public LayerVillagerArmor(RendererLivingEntity<?> rendererIn) {
/*  8 */     super(rendererIn);
/*    */   }
/*    */   
/*    */   protected void initArmor() {
/* 12 */     this.modelLeggings = (ModelBiped)new ModelZombieVillager(0.5F, 0.0F, true);
/* 13 */     this.modelArmor = (ModelBiped)new ModelZombieVillager(1.0F, 0.0F, true);
/*    */   }
/*    */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\minecraft\client\renderer\entity\layers\LayerVillagerArmor.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */