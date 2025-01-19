package net.minecraft.client.renderer.chunk;

import net.minecraft.client.renderer.RenderGlobal;
import net.minecraft.util.BlockPos;
import net.minecraft.world.World;

public interface IRenderChunkFactory {
  RenderChunk makeRenderChunk(World paramWorld, RenderGlobal paramRenderGlobal, BlockPos paramBlockPos, int paramInt);
}


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\minecraft\client\renderer\chunk\IRenderChunkFactory.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */