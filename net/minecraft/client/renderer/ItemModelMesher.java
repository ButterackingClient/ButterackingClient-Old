/*    */ package net.minecraft.client.renderer;
/*    */ 
/*    */ import com.google.common.collect.Maps;
/*    */ import java.util.Map;
/*    */ import net.minecraft.client.renderer.texture.TextureAtlasSprite;
/*    */ import net.minecraft.client.resources.model.IBakedModel;
/*    */ import net.minecraft.client.resources.model.ModelManager;
/*    */ import net.minecraft.client.resources.model.ModelResourceLocation;
/*    */ import net.minecraft.item.Item;
/*    */ import net.minecraft.item.ItemStack;
/*    */ import net.minecraft.src.Config;
/*    */ import net.minecraftforge.client.model.ISmartItemModel;
/*    */ import net.optifine.CustomItems;
/*    */ import net.optifine.reflect.Reflector;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class ItemModelMesher
/*    */ {
/* 21 */   private final Map<Integer, ModelResourceLocation> simpleShapes = Maps.newHashMap();
/* 22 */   private final Map<Integer, IBakedModel> simpleShapesCache = Maps.newHashMap();
/* 23 */   private final Map<Item, ItemMeshDefinition> shapers = Maps.newHashMap();
/*    */   private final ModelManager modelManager;
/*    */   
/*    */   public ItemModelMesher(ModelManager modelManager) {
/* 27 */     this.modelManager = modelManager;
/*    */   }
/*    */   
/*    */   public TextureAtlasSprite getParticleIcon(Item item) {
/* 31 */     return getParticleIcon(item, 0);
/*    */   }
/*    */   
/*    */   public TextureAtlasSprite getParticleIcon(Item item, int meta) {
/* 35 */     return getItemModel(new ItemStack(item, 1, meta)).getParticleTexture();
/*    */   }
/*    */   
/*    */   public IBakedModel getItemModel(ItemStack stack) {
/* 39 */     Item item = stack.getItem();
/* 40 */     IBakedModel ibakedmodel = getItemModel(item, getMetadata(stack));
/*    */     
/* 42 */     if (ibakedmodel == null) {
/* 43 */       ItemMeshDefinition itemmeshdefinition = this.shapers.get(item);
/*    */       
/* 45 */       if (itemmeshdefinition != null) {
/* 46 */         ibakedmodel = this.modelManager.getModel(itemmeshdefinition.getModelLocation(stack));
/*    */       }
/*    */     } 
/*    */     
/* 50 */     if (Reflector.ForgeHooksClient.exists() && ibakedmodel instanceof ISmartItemModel) {
/* 51 */       ibakedmodel = ((ISmartItemModel)ibakedmodel).handleItemState(stack);
/*    */     }
/*    */     
/* 54 */     if (ibakedmodel == null) {
/* 55 */       ibakedmodel = this.modelManager.getMissingModel();
/*    */     }
/*    */     
/* 58 */     if (Config.isCustomItems()) {
/* 59 */       ibakedmodel = CustomItems.getCustomItemModel(stack, ibakedmodel, null, true);
/*    */     }
/*    */     
/* 62 */     return ibakedmodel;
/*    */   }
/*    */   
/*    */   protected int getMetadata(ItemStack stack) {
/* 66 */     return stack.isItemStackDamageable() ? 0 : stack.getMetadata();
/*    */   }
/*    */   
/*    */   protected IBakedModel getItemModel(Item item, int meta) {
/* 70 */     return this.simpleShapesCache.get(Integer.valueOf(getIndex(item, meta)));
/*    */   }
/*    */   
/*    */   private int getIndex(Item item, int meta) {
/* 74 */     return Item.getIdFromItem(item) << 16 | meta;
/*    */   }
/*    */   
/*    */   public void register(Item item, int meta, ModelResourceLocation location) {
/* 78 */     this.simpleShapes.put(Integer.valueOf(getIndex(item, meta)), location);
/* 79 */     this.simpleShapesCache.put(Integer.valueOf(getIndex(item, meta)), this.modelManager.getModel(location));
/*    */   }
/*    */   
/*    */   public void register(Item item, ItemMeshDefinition definition) {
/* 83 */     this.shapers.put(item, definition);
/*    */   }
/*    */   
/*    */   public ModelManager getModelManager() {
/* 87 */     return this.modelManager;
/*    */   }
/*    */   
/*    */   public void rebuildCache() {
/* 91 */     this.simpleShapesCache.clear();
/*    */     
/* 93 */     for (Map.Entry<Integer, ModelResourceLocation> entry : this.simpleShapes.entrySet())
/* 94 */       this.simpleShapesCache.put(entry.getKey(), this.modelManager.getModel(entry.getValue())); 
/*    */   }
/*    */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\minecraft\client\renderer\ItemModelMesher.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */