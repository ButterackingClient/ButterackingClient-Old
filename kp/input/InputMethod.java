package kp.input;

import kp.mcinterface.*;
import java.awt.*;
import java.awt.datatransfer.*;

public class InputMethod implements IInputTarget.WriteTextFunc, IInputTarget.CursorSelectionFunc, IInputTarget.InputIdentifier
{
    private int inner_cursor;
    private int inner_selection;
    private IInputTarget target;
    protected IInputTarget.WriteTextFunc writeTextPtr;
    protected IInputTarget.CursorSelectionFunc selectionFuncPtr;
    protected IInputTarget.InputIdentifier identifier;
    private static IMinecraftInterface mcinterface;
    
    static {
        InputMethod.mcinterface = new DefaultMCInterface();
    }
    
    public InputMethod(final IInputTarget target) {
        this.inner_cursor = 0;
        this.inner_selection = 0;
        this.target = target;
        this.writeTextPtr = this;
        this.selectionFuncPtr = this;
        this.identifier = this;
    }
    
    public void setWriteTextFunc(final IInputTarget.WriteTextFunc funcCls) {
        this.writeTextPtr = funcCls;
    }
    
    public void setCursorSelectionFunc(final IInputTarget.CursorSelectionFunc funcCls) {
        this.selectionFuncPtr = funcCls;
    }
    
    public void setIdentifier(final IInputTarget.InputIdentifier ii) {
        this.identifier = ii;
    }
    
    public boolean onTyped(final int i, final char c) {
        final int cur = this.selectionFuncPtr.getCursor();
        final int sel = this.selectionFuncPtr.getSelection();
        final String text = this.target.getTargetText();
        if (this.isAlpha(c)) {
            if (cur != sel) {
                this.writeTextPtr.writeTextFunc("");
            }
            this.onAppend(this.target, i, c);
        }
        else if (i == 14) {
            if (cur != sel) {
                this.writeTextPtr.writeTextFunc("");
            }
            else if (InputMethod.mcinterface.isCtrlKeyDown()) {
                this.selectionFuncPtr.setSelection(this.getWordPosFromCursor(false));
                this.writeTextPtr.writeTextFunc("");
            }
            else {
                this.onBackspace(this.target);
            }
        }
        else if (i == 203) {
            if (InputMethod.mcinterface.isShiftKeyDown()) {
                if (sel <= 0) {
                    return false;
                }
                if (InputMethod.mcinterface.isCtrlKeyDown()) {
                    this.selectionFuncPtr.setSelection(this.getWordPosFromCursor(false));
                }
                else {
                    this.selectionFuncPtr.setSelection(sel - 1);
                }
            }
            else {
                if (cur <= 0) {
                    return false;
                }
                if (InputMethod.mcinterface.isCtrlKeyDown()) {
                    this.selectionFuncPtr.setCursor(this.getWordPosFromCursor(false));
                }
                else {
                    this.selectionFuncPtr.setCursor(cur - 1);
                }
            }
        }
        else if (i == 205) {
            if (InputMethod.mcinterface.isShiftKeyDown()) {
                if (sel >= text.length()) {
                    return false;
                }
                if (InputMethod.mcinterface.isCtrlKeyDown()) {
                    this.selectionFuncPtr.setSelection(this.getWordPosFromCursor(true));
                }
                else {
                    this.selectionFuncPtr.setSelection(sel + 1);
                }
            }
            else {
                if (cur >= text.length()) {
                    return false;
                }
                if (InputMethod.mcinterface.isCtrlKeyDown()) {
                    this.selectionFuncPtr.setCursor(this.getWordPosFromCursor(true));
                }
                else {
                    this.selectionFuncPtr.setCursor(cur + 1);
                }
            }
        }
        else if (i == 199) {
            if (InputMethod.mcinterface.isShiftKeyDown()) {
                this.selectionFuncPtr.setSelection(0);
            }
            else {
                this.selectionFuncPtr.setCursor(0);
            }
        }
        else if (i == 207) {
            if (InputMethod.mcinterface.isShiftKeyDown()) {
                this.selectionFuncPtr.setSelection(text.length());
            }
            else {
                this.selectionFuncPtr.setCursor(text.length());
            }
        }
        else if (i == 211) {
            if (cur != sel) {
                this.writeTextPtr.writeTextFunc("");
            }
            else {
                if (cur >= text.length()) {
                    return false;
                }
                if (InputMethod.mcinterface.isCtrlKeyDown()) {
                    this.selectionFuncPtr.setSelection(this.getWordPosFromCursor(true));
                    this.writeTextPtr.writeTextFunc("");
                }
                else {
                    final String s1 = text.substring(0, cur);
                    final String s2 = text.substring(cur + 1);
                    this.target.setTargetText(String.valueOf(s1) + s2);
                }
            }
        }
        else if (isKeyComboCtrlKey(i, 30)) {
            this.selectionFuncPtr.setCursor(text.length());
            this.selectionFuncPtr.setSelection(0);
        }
        else if (isKeyComboCtrlKey(i, 46)) {
            setClipboardString(text.substring(Math.min(cur, sel), Math.max(cur, sel)));
        }
        else if (isKeyComboCtrlKey(i, 47)) {
            this.writeTextPtr.writeTextFunc(getClipboardString());
        }
        else if (isKeyComboCtrlKey(i, 45)) {
            setClipboardString(text.substring(Math.min(cur, sel), Math.max(cur, sel)));
            this.writeTextPtr.writeTextFunc("");
        }
        else {
            if (!InputMethod.mcinterface.isAllowedCharacter(c)) {
                return false;
            }
            this.writeTextPtr.writeTextFunc(String.valueOf(c));
        }
        return true;
    }
    
    public String getTextFromTarget() {
        return this.target.getTargetText();
    }
    
    protected int getWordPosFromCursor(final boolean right) {
        int i = this.selectionFuncPtr.getSelection();
        final int len = this.target.getTargetText().length();
        final char[] strs = this.target.getTargetText().toCharArray();
        if (i == 0 && !right) {
            return 0;
        }
        i += (right ? 1 : -1);
        if (right) {
            while (i < len) {
                if (strs[i] != ' ') {
                    break;
                }
                ++i;
            }
            while (i < len) {
                if (strs[i] == ' ') {
                    break;
                }
                ++i;
            }
        }
        else {
            while (i > 0) {
                if (strs[i] != ' ') {
                    break;
                }
                --i;
            }
            while (i > 0 && strs[i] != ' ') {
                --i;
            }
        }
        return i;
    }
    
    @Override
    public void writeTextFunc(final String str) {
        if (this.identifier.apply(str)) {
            final int cursor = this.selectionFuncPtr.getCursor();
            final String splited1 = this.target.getTargetText().substring(0, cursor);
            final String splited2 = this.target.getTargetText().substring(cursor);
            this.target.setTargetText(String.valueOf(splited1) + str + splited2);
            this.selectionFuncPtr.setCursor(cursor + 1);
        }
    }
    
    @Override
    public int getCursor() {
        return this.inner_cursor;
    }
    
    @Override
    public void setCursor(final int cur) {
        this.inner_cursor = cur;
        this.inner_selection = cur;
    }
    
    @Override
    public int getSelection() {
        return this.inner_selection;
    }
    
    @Override
    public void setSelection(final int selectionPos) {
        this.inner_selection = selectionPos;
    }
    
    public boolean isAlpha(final char c) {
        return (c >= 'a' && c <= 'z') || (c >= 'A' && c <= 'Z');
    }
    
    public static void setInterface(final IMinecraftInterface itf) {
        InputMethod.mcinterface = itf;
    }
    
    public static IMinecraftInterface getMinecraftInterface() {
        return InputMethod.mcinterface;
    }
    
    public static boolean isKeyComboCtrlKey(final int key, final int test) {
        return key == test && InputMethod.mcinterface.isCtrlKeyDown() && !InputMethod.mcinterface.isShiftKeyDown() && !InputMethod.mcinterface.isAltKeyDown();
    }
    
    public static String getClipboardString() {
        try {
            final Transferable transferable = Toolkit.getDefaultToolkit().getSystemClipboard().getContents(null);
            if (transferable != null && transferable.isDataFlavorSupported(DataFlavor.stringFlavor)) {
                return (String)transferable.getTransferData(DataFlavor.stringFlavor);
            }
        }
        catch (Exception var1) {
            var1.printStackTrace();
        }
        return "";
    }
    
    public static void setClipboardString(final String copyText) {
        if (!copyText.isEmpty()) {
            try {
                final StringSelection sel = new StringSelection(copyText);
                Toolkit.getDefaultToolkit().getSystemClipboard().setContents(sel, null);
            }
            catch (Exception var2) {
                var2.printStackTrace();
            }
        }
    }
    
    public void onBackspace(final IInputTarget target) {
    }
    
    public void onAppend(final IInputTarget target, final int i, final char c) {
    }
    
    @Override
    public boolean apply(final String append) {
        return true;
    }
}
