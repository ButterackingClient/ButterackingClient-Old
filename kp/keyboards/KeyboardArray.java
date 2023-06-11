package kp.keyboards;

public abstract class KeyboardArray
{
    public static final String CHOSUNG = "\u3131\u3132\u3134\u3137\u3138\u3139\u3141\u3142\u3143\u3145\u3146\u3147\u3148\u3149\u314a\u314b\u314c\u314d\u314e";
    public static final String JONGSUNG = " \u3131\u3132\u3133\u3134\u3135\u3136\u3137\u3139\u313a\u313b\u313c\u313d\u313e\u313f\u3140\u3141\u3142\u3144\u3145\u3146\u3147\u3148\u314a\u314b\u314c\u314d\u314e";
    
    public abstract String getChosung();
    
    public abstract String getJungsung();
    
    public abstract String getJongsung();
    
    public abstract String keyboardName();
}
