package net.minecraft.util;

public interface IProgressUpdate {
  void displaySavingString(String paramString);
  
  void resetProgressAndMessage(String paramString);
  
  void displayLoadingString(String paramString);
  
  void setLoadingProgress(int paramInt);
  
  void setDoneWorking();
}


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\minecraf\\util\IProgressUpdate.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */