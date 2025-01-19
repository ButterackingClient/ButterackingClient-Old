/*    */ package net.minecraft.client.renderer.entity;
/*    */ import com.google.common.collect.Lists;
/*    */ import java.util.List;
/*    */ import net.minecraft.client.model.ModelBase;
/*    */ import net.minecraft.client.model.ModelBiped;
/*    */ import net.minecraft.client.model.ModelZombie;
/*    */ import net.minecraft.client.model.ModelZombieVillager;
/*    */ import net.minecraft.client.renderer.entity.layers.LayerBipedArmor;
/*    */ import net.minecraft.client.renderer.entity.layers.LayerCustomHead;
/*    */ import net.minecraft.client.renderer.entity.layers.LayerHeldItem;
/*    */ import net.minecraft.client.renderer.entity.layers.LayerRenderer;
/*    */ import net.minecraft.client.renderer.entity.layers.LayerVillagerArmor;
/*    */ import net.minecraft.entity.EntityLiving;
/*    */ import net.minecraft.entity.EntityLivingBase;
/*    */ import net.minecraft.entity.monster.EntityZombie;
/*    */ import net.minecraft.util.ResourceLocation;
/*    */ 
/*    */ public class RenderZombie extends RenderBiped<EntityZombie> {
/* 19 */   private static final ResourceLocation zombieTextures = new ResourceLocation("textures/entity/zombie/zombie.png");
/* 20 */   private static final ResourceLocation zombieVillagerTextures = new ResourceLocation("textures/entity/zombie/zombie_villager.png");
/*    */   private final ModelBiped field_82434_o;
/*    */   private final ModelZombieVillager zombieVillagerModel;
/*    */   private final List<LayerRenderer<EntityZombie>> field_177121_n;
/*    */   private final List<LayerRenderer<EntityZombie>> field_177122_o;
/*    */   
/*    */   public RenderZombie(RenderManager renderManagerIn) {
/* 27 */     super(renderManagerIn, (ModelBiped)new ModelZombie(), 0.5F, 1.0F);
/* 28 */     LayerRenderer layerrenderer = this.layerRenderers.get(0);
/* 29 */     this.field_82434_o = this.modelBipedMain;
/* 30 */     this.zombieVillagerModel = new ModelZombieVillager();
/* 31 */     addLayer(new LayerHeldItem(this));
/* 32 */     LayerBipedArmor layerbipedarmor = new LayerBipedArmor(this) {
/*    */         protected void initArmor() {
/* 34 */           this.modelLeggings = (ModelBase)new ModelZombie(0.5F, true);
/* 35 */           this.modelArmor = (ModelBase)new ModelZombie(1.0F, true);
/*    */         }
/*    */       };
/* 38 */     addLayer(layerbipedarmor);
/* 39 */     this.field_177122_o = Lists.newArrayList(this.layerRenderers);
/*    */     
/* 41 */     if (layerrenderer instanceof LayerCustomHead) {
/* 42 */       removeLayer(layerrenderer);
/* 43 */       addLayer(new LayerCustomHead(this.zombieVillagerModel.bipedHead));
/*    */     } 
/*    */     
/* 46 */     removeLayer(layerbipedarmor);
/* 47 */     addLayer(new LayerVillagerArmor(this));
/* 48 */     this.field_177121_n = Lists.newArrayList(this.layerRenderers);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void doRender(EntityZombie entity, double x, double y, double z, float entityYaw, float partialTicks) {
/* 55 */     func_82427_a(entity);
/* 56 */     super.doRender(entity, x, y, z, entityYaw, partialTicks);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   protected ResourceLocation getEntityTexture(EntityZombie entity) {
/* 63 */     return entity.isVillager() ? zombieVillagerTextures : zombieTextures;
/*    */   }
/*    */   
/*    */   private void func_82427_a(EntityZombie zombie) {
/* 67 */     if (zombie.isVillager()) {
/* 68 */       this.mainModel = (ModelBase)this.zombieVillagerModel;
/* 69 */       this.layerRenderers = this.field_177121_n;
/*    */     } else {
/* 71 */       this.mainModel = (ModelBase)this.field_82434_o;
/* 72 */       this.layerRenderers = this.field_177122_o;
/*    */     } 
/*    */     
/* 75 */     this.modelBipedMain = (ModelBiped)this.mainModel;
/*    */   }
/*    */   
/*    */   protected void rotateCorpse(EntityZombie bat, float p_77043_2_, float p_77043_3_, float partialTicks) {
/* 79 */     if (bat.isConverting()) {
/* 80 */       p_77043_3_ += (float)(Math.cos(bat.ticksExisted * 3.25D) * Math.PI * 0.25D);
/*    */     }
/*    */     
/* 83 */     super.rotateCorpse(bat, p_77043_2_, p_77043_3_, partialTicks);
/*    */   }
/*    */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\minecraft\client\renderer\entity\RenderZombie.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */