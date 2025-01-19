package net.minecraftforge.client.model;

import javax.vecmath.Matrix4f;
import net.minecraft.util.EnumFacing;

public interface ITransformation {
  Matrix4f getMatrix();
  
  EnumFacing rotate(EnumFacing paramEnumFacing);
  
  int rotate(EnumFacing paramEnumFacing, int paramInt);
}


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\minecraftforge\client\model\ITransformation.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */