package kp.input;

public interface IInputTarget {
  String getTargetText();
  
  boolean setTargetText(String paramString);
  
  public static interface CursorSelectionFunc {
    int getCursor();
    
    void setCursor(int param1Int);
    
    int getSelection();
    
    void setSelection(int param1Int);
  }
  
  public static interface InputIdentifier {
    boolean apply(String param1String);
  }
  
  public static interface WriteTextFunc {
    void writeTextFunc(String param1String);
  }
}


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\kp\input\IInputTarget.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */