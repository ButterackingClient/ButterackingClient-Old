package net.minecraftforge.fml.common.registry;

import net.minecraft.util.ResourceLocation;

public interface RegistryDelegate<T> {
  T get();
  
  ResourceLocation name();
  
  Class<T> type();
}


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\minecraftforge\fml\common\registry\RegistryDelegate.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */