package kp.keyboards;

public class KeyboardQwerty extends KeyboardArray
{
    @Override
    public String getChosung() {
        return "r R s e E f a q Q t T d w W c z x v g ";
    }
    
    @Override
    public String getJungsung() {
        return "k o i O j p u P h hkhohly n njnpnlb m mll ";
    }
    
    @Override
    public String getJongsung() {
        return "  r R rts swsge f frfafqftfxfvfga q qtt T d w c z x v g ";
    }
    
    @Override
    public String keyboardName() {
        return "\ub450\ubc8c\uc2dd";
    }
}
