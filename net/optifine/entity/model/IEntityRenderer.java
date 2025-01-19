package net.optifine.entity.model;

import net.minecraft.util.ResourceLocation;

public interface IEntityRenderer {
  Class getEntityClass();
  
  void setEntityClass(Class paramClass);
  
  ResourceLocation getLocationTextureCustom();
  
  void setLocationTextureCustom(ResourceLocation paramResourceLocation);
}


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\optifine\entity\model\IEntityRenderer.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */