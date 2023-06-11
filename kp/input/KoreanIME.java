package kp.input;

import kp.*;

public class KoreanIME extends InputMethod
{
    public static String lastGuiChatStr;
    public static boolean enabled;
    private boolean editing;
    
    static {
        KoreanIME.lastGuiChatStr = "";
        KoreanIME.enabled = false;
    }
    
    public KoreanIME(final IInputTarget target) {
        super(target);
        this.editing = true;
    }
    
    @Override
    public boolean onTyped(final int i, final char c) {
        final boolean ret = super.onTyped(i, c);
        if (!ret && i == 29) {
            KoreanIME.enabled = !KoreanIME.enabled;
        }
        if (KoreanIME.enabled && (i == 203 || i == 205)) {
            this.editing = false;
        }
        if (InputMethod.getMinecraftInterface().isOnGuiChat()) {
            KoreanIME.lastGuiChatStr = this.getTextFromTarget();
        }
        return ret;
    }
    
    @Override
    public void onBackspace(final IInputTarget target) {
        if (this.selectionFuncPtr.getCursor() > 0) {
            if (this.editing && KoreanIME.enabled && Config.DELETE_JASO) {
                String text = HangulAssembler.disassemble(target.getTargetText().charAt(this.selectionFuncPtr.getCursor() - 1));
                text = text.substring(0, text.length() - 1);
                text = HangulAssembler.make(text);
                this.replaceStrAtCursor(target, text);
            }
            else {
                this.replaceStrAtCursor(target, "");
            }
        }
    }
    
    @Override
    public void onAppend(final IInputTarget target, final int code, char c) {
        if (KoreanIME.enabled) {
            if (!HangulAssembler.isAllowed(String.valueOf(c))) {
                c = Character.toLowerCase(c);
            }
            if (this.selectionFuncPtr.getCursor() > 0 && this.editing) {
                final char end = target.getTargetText().charAt(this.selectionFuncPtr.getCursor() - 1);
                if (HangulAssembler.isKorean(end) || HangulAssembler.getJamoTier(end) > 0) {
                    String text = String.valueOf(HangulAssembler.disassemble(end)) + c;
                    text = HangulAssembler.make(text);
                    if (this.identifier.apply(text)) {
                        this.replaceStrAtCursor(target, text);
                    }
                }
                else {
                    this.writeTextFunc(HangulAssembler.convertKrEn(c, false));
                }
                this.editing = true;
            }
            else {
                this.writeTextFunc(HangulAssembler.convertKrEn(c, false));
                this.editing = true;
            }
        }
        else {
            this.writeTextFunc(String.valueOf(c));
        }
    }
    
    private void replaceStrAtCursor(final IInputTarget target, final String c) {
        String text = target.getTargetText();
        final int cur = this.selectionFuncPtr.getCursor();
        final int last = text.length();
        final String a = text.substring(0, cur);
        final String b = text.substring(cur);
        text = String.valueOf(a.substring(0, a.length() - 1)) + c + b;
        final int dCursor = text.length() - last;
        if (target.setTargetText(text)) {
            this.selectionFuncPtr.setCursor(cur + dCursor);
        }
        if (dCursor != 0) {
            this.editing = false;
        }
    }
}
