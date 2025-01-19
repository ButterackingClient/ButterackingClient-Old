package org.newdawn.slick.openal;

public interface Audio {
  void stop();
  
  int getBufferID();
  
  boolean isPlaying();
  
  int playAsSoundEffect(float paramFloat1, float paramFloat2, boolean paramBoolean);
  
  int playAsSoundEffect(float paramFloat1, float paramFloat2, boolean paramBoolean, float paramFloat3, float paramFloat4, float paramFloat5);
  
  int playAsMusic(float paramFloat1, float paramFloat2, boolean paramBoolean);
  
  boolean setPosition(float paramFloat);
  
  float getPosition();
}


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\org\newdawn\slick\openal\Audio.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */