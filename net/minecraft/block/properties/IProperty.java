package net.minecraft.block.properties;

import java.util.Collection;

public interface IProperty<T extends Comparable<T>> {
  String getName();
  
  Collection<T> getAllowedValues();
  
  Class<T> getValueClass();
  
  String getName(T paramT);
}


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\minecraft\block\properties\IProperty.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */