package kp.input;

public interface IInputTarget
{
    String getTargetText();
    
    boolean setTargetText(final String p0);
    
    public interface CursorSelectionFunc
    {
        int getCursor();
        
        void setCursor(final int p0);
        
        int getSelection();
        
        void setSelection(final int p0);
    }
    
    public interface InputIdentifier
    {
        boolean apply(final String p0);
    }
    
    public interface WriteTextFunc
    {
        void writeTextFunc(final String p0);
    }
}
