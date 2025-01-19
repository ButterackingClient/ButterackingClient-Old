package net.minecraft.util;

import com.google.common.util.concurrent.ListenableFuture;

public interface IThreadListener {
  ListenableFuture<Object> addScheduledTask(Runnable paramRunnable);
  
  boolean isCallingFromMinecraftThread();
}


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\minecraf\\util\IThreadListener.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */