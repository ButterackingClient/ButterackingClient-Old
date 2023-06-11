package kp.keyboards;

import java.util.*;
import kp.*;

public class KeyLoader
{
    private final ArrayList<KeyboardArray> keyboards;
    private int currentKeyboard;
    
    public KeyLoader() {
        this.keyboards = new ArrayList<KeyboardArray>();
        this.currentKeyboard = 0;
        this.loadKeyboards();
    }
    
    public void loadKeyboards() {
        this.keyboards.clear();
        this.keyboards.add(new KeyboardQwerty());
        this.keyboards.add(new KeyboardThird390());
        this.keyboards.add(new KeyboardThird391());
    }
    
    public int getKeyboardArrayIndex() {
        return this.currentKeyboard;
    }
    
    public String applySetIterate() {
        this.currentKeyboard = (this.currentKeyboard + 1) % this.keyboards.size();
        return this.getKeyboard().keyboardName();
    }
    
    public KeyboardArray getKeyboard() {
        return this.keyboards.get(this.currentKeyboard);
    }
    
    public String[] getKeyboards() {
        final String[] names = new String[this.keyboards.size()];
        for (int a = 0; a < names.length; ++a) {
            names[a] = this.keyboards.get(a).keyboardName();
        }
        return names;
    }
    
    public String getKeyboardsToString() {
        final StringBuilder sb = new StringBuilder();
        int i = 0;
        final String[] keyboards = this.getKeyboards();
        String[] array;
        for (int length = (array = keyboards).length, j = 0; j < length; ++j) {
            final String str = array[j];
            sb.append(str);
            if (i++ < keyboards.length) {
                sb.append(",");
            }
        }
        return sb.toString();
    }
    
    public void setKeyboard(final int keyboardIndex) {
        if (this.currentKeyboard != keyboardIndex) {
            if (keyboardIndex >= 0 && keyboardIndex < this.keyboards.size()) {
                this.currentKeyboard = keyboardIndex;
                Log.i("Set keyboard to " + this.keyboards.get(keyboardIndex).keyboardName());
            }
            else {
                Log.error("Keyboard index " + keyboardIndex + " is out of array!");
            }
        }
    }
}
