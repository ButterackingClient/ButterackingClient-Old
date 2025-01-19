package kp.keyboards;

public abstract class KeyboardArray {
  public static final String CHOSUNG = "ㄱㄲㄴㄷㄸㄹㅁㅂㅃㅅㅆㅇㅈㅉㅊㅋㅌㅍㅎ";
  
  public static final String JONGSUNG = " ㄱㄲㄳㄴㄵㄶㄷㄹㄺㄻㄼㄽㄾㄿㅀㅁㅂㅄㅅㅆㅇㅈㅊㅋㅌㅍㅎ";
  
  public abstract String getChosung();
  
  public abstract String getJungsung();
  
  public abstract String getJongsung();
  
  public abstract String keyboardName();
}


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\kp\keyboards\KeyboardArray.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */