package kp.input;

import kp.*;
import kp.keyboards.*;

public class HangulAssembler
{
    public static String disassemble(final char c) {
        final StringBuilder sb = new StringBuilder();
        if (isKorean(c)) {
            final Hangul ch = new Hangul(c);
            sb.append(Code.CHO.getAt(ch.cho));
            sb.append(Code.JUNG.getAt(ch.jung));
            sb.append(Code.JONG.getAt(ch.jong));
        }
        else if (getJamoTier(c) > 0) {
            sb.append(convertKrEn(c, true));
        }
        else {
            sb.append(c);
        }
        return sb.toString();
    }
    
    public static String convertKrEn(final char c, final boolean toEn) {
        if (toEn) {
            if (c >= '\u3131' && c <= '\u314e') {
                int i = "\u3131\u3132\u3134\u3137\u3138\u3139\u3141\u3142\u3143\u3145\u3146\u3147\u3148\u3149\u314a\u314b\u314c\u314d\u314e".indexOf(c);
                if (i != -1) {
                    return Code.CHO.getAt(i);
                }
                i = " \u3131\u3132\u3133\u3134\u3135\u3136\u3137\u3139\u313a\u313b\u313c\u313d\u313e\u313f\u3140\u3141\u3142\u3144\u3145\u3146\u3147\u3148\u314a\u314b\u314c\u314d\u314e".indexOf(c);
                if (i != -1) {
                    return Code.JONG.getAt(i);
                }
            }
            else if (c >= '\u314f' && c <= '\u3163') {
                return Code.JUNG.getAt(c - '\u314f');
            }
        }
        else {
            int idx = Code.CHO.getIndex(c);
            if (idx != -1) {
                return String.valueOf("\u3131\u3132\u3134\u3137\u3138\u3139\u3141\u3142\u3143\u3145\u3146\u3147\u3148\u3149\u314a\u314b\u314c\u314d\u314e".charAt(idx));
            }
            idx = Code.JUNG.getIndex(c);
            if (idx != -1) {
                return String.valueOf((char)(12623 + idx));
            }
            idx = Code.JONG.getIndex(c);
            if (idx != -1) {
                return String.valueOf(" \u3131\u3132\u3133\u3134\u3135\u3136\u3137\u3139\u313a\u313b\u313c\u313d\u313e\u313f\u3140\u3141\u3142\u3144\u3145\u3146\u3147\u3148\u314a\u314b\u314c\u314d\u314e".charAt(idx - 1));
            }
        }
        return String.valueOf(c);
    }
    
    public static String make(final String text) {
        final StringBuilder buf = new StringBuilder();
        final int len = text.length();
        final Hangul ch = new Hangul();
        boolean isDouble = false;
        char c = '\0';
        for (int i = 0; i < len; ++i) {
            c = text.charAt(i);
            if (ch.cho == -1) {
                if (text.length() >= i + 2) {
                    ch.cho = Code.CHO.getIndex(text.substring(i, i + 2));
                    if (ch.cho != -1) {
                        ++i;
                    }
                }
                if (ch.cho == -1) {
                    ch.cho = Code.CHO.getIndex(c);
                }
                if (ch.cho == -1) {
                    ch.flush(buf);
                    buf.append(convertKrEn(c, false));
                }
            }
            else if (ch.jung == -1) {
                if (text.length() >= i + 2) {
                    ch.jung = Code.JUNG.getIndex(text.substring(i, i + 2));
                    if (ch.jung != -1) {
                        ++i;
                    }
                }
                if (ch.jung == -1) {
                    ch.jung = Code.JUNG.getIndex(c);
                }
                if (ch.jung == -1) {
                    ch.flush(buf);
                    buf.append(convertKrEn(c, false));
                }
            }
            else if (ch.jong == -1) {
                if (text.length() >= i + 2) {
                    ch.jong = Code.JONG.getIndex(text.substring(i, i + 2));
                    if (ch.jong != -1) {
                        isDouble = true;
                        ++i;
                    }
                }
                if (ch.jong == -1) {
                    ch.jong = Code.JONG.getIndex(c);
                }
                if (ch.jong == -1) {
                    ch.flush(buf);
                    buf.append(convertKrEn(c, false));
                }
                else {
                    if (text.length() > i + 1 && Code.JUNG.getIndex(text.charAt(i + 1)) != -1) {
                        if (isDouble) {
                            ch.jong = Code.JONG.getIndex(c);
                        }
                        else {
                            ch.jong = -1;
                        }
                        --i;
                    }
                    ch.flush(buf);
                }
            }
        }
        ch.flush(buf);
        return buf.toString();
    }
    
    public static boolean isKorean(final char c) {
        return c >= '\uac00' && c <= '\ud7a3';
    }
    
    public static int getJamoTier(final char c) {
        if (c >= '\u3131' && c <= '\u314e') {
            return 1;
        }
        if (c >= '\u314f' && c <= '\u3163') {
            return 2;
        }
        return 0;
    }
    
    public static boolean isAllowed(final String s) {
        return Code.CHO.getIndex(s) != -1 || Code.JUNG.getIndex(s) != -1 || Code.JONG.getIndex(s) != -1;
    }
    
    public enum Code
    {
        CHO("CHO", 0), 
        JUNG("JUNG", 1), 
        JONG("JONG", 2);
        
        private Code(final String s, final int n) {
        }
        
        private String getData() {
            final KeyboardArray keyboard = Config.keyloader.getKeyboard();
            switch (this) {
                case CHO: {
                    return keyboard.getChosung();
                }
                case JUNG: {
                    return keyboard.getJungsung();
                }
                case JONG: {
                    return keyboard.getJongsung();
                }
                default: {
                    return null;
                }
            }
        }
        
        public int getIndex(final String cs) {
            final int idx = this.getData().indexOf((cs.length() == 1) ? (String.valueOf(cs) + " ") : cs);
            if (cs.charAt(0) == ' ' || idx == -1 || idx % 2 == 1) {
                return -1;
            }
            return idx / 2;
        }
        
        public int getIndex(final char c) {
            return this.getIndex(String.valueOf(c));
        }
        
        public String getAt(final int x) {
            return this.getData().substring(x * 2, x * 2 + 2).replaceAll(" ", "");
        }
    }
    
    public static class Hangul
    {
        public int cho;
        public int jung;
        public int jong;
        
        public Hangul() {
            this.clear();
        }
        
        public Hangul(final char c) {
            this.clear();
            if (HangulAssembler.isKorean(c)) {
                final int pureKr = c - '\uac00';
                this.jong = pureKr % 28;
                this.jung = (pureKr - this.jong) / 28 % 21;
                this.cho = ((pureKr - this.jong) / 28 - this.jung) / 21;
            }
            else if (c >= '\u3131' && c <= '\u314e') {
                this.cho = Code.CHO.getIndex(String.valueOf(c));
            }
            else if (c >= '\u314f' && c <= '\u3163') {
                this.jung = Code.JUNG.getIndex(String.valueOf(c));
            }
        }
        
        public void flush(final StringBuilder sb) {
            if (this.cho != -1 && this.jung != -1) {
                sb.append((char)(44032 + (this.cho * 21 + this.jung) * 28 + Math.max(this.jong, 0)));
            }
            else {
                if (this.cho != -1) {
                    sb.append("\u3131\u3132\u3134\u3137\u3138\u3139\u3141\u3142\u3143\u3145\u3146\u3147\u3148\u3149\u314a\u314b\u314c\u314d\u314e".charAt(this.cho));
                }
                if (this.jung != -1) {
                    sb.append(12623 + this.jung);
                }
                if (this.jong != -1) {
                    sb.append(" \u3131\u3132\u3133\u3134\u3135\u3136\u3137\u3139\u313a\u313b\u313c\u313d\u313e\u313f\u3140\u3141\u3142\u3144\u3145\u3146\u3147\u3148\u314a\u314b\u314c\u314d\u314e".charAt(this.jong));
                }
            }
            this.clear();
        }
        
        public void clear() {
            this.cho = -1;
            this.jung = -1;
            this.jong = -1;
        }
    }
}
