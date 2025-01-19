package net.minecraftforge.client.model;

import com.google.common.base.Optional;

public interface IModelState {
  Optional<TRSRTransformation> apply(Optional<? extends IModelPart> paramOptional);
}


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\minecraftforge\client\model\IModelState.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */