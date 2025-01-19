package net.minecraft.util;

import com.google.gson.JsonElement;

public interface IJsonSerializable {
  void fromJson(JsonElement paramJsonElement);
  
  JsonElement getSerializableElement();
}


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\minecraf\\util\IJsonSerializable.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */