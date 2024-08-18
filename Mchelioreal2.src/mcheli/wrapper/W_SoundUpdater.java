package mcheli.wrapper;

import mcheli.__helper.MCH_Utils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.audio.ISound;
import net.minecraft.client.audio.SoundHandler;
import net.minecraft.entity.Entity;

public class W_SoundUpdater {
  protected final SoundHandler theSoundHnadler;
  
  protected W_Sound es;
  
  public W_SoundUpdater(Minecraft minecraft, Entity entity) {
    this.theSoundHnadler = minecraft.func_147118_V();
  }
  
  public void initEntitySound(String name) {
    this.es = new W_Sound(MCH_Utils.suffix(name), 1.0F, 1.0F);
    this.es.setRepeat(true);
  }
  
  public boolean isValidSound() {
    return (this.es != null);
  }
  
  public void playEntitySound(String name, Entity entity, float volume, float pitch, boolean par5) {
    if (isValidSound()) {
      this.es.setSoundParam(entity, volume, pitch);
      this.theSoundHnadler.func_147682_a((ISound)this.es);
    } 
  }
  
  public void stopEntitySound(Entity entity) {
    if (isValidSound())
      this.theSoundHnadler.func_147683_b((ISound)this.es); 
  }
  
  public boolean isEntitySoundPlaying(Entity entity) {
    return isValidSound() ? this.theSoundHnadler.func_147692_c((ISound)this.es) : false;
  }
  
  public void setEntitySoundPitch(Entity entity, float pitch) {
    if (isValidSound())
      this.es.setPitch(pitch); 
  }
  
  public void setEntitySoundVolume(Entity entity, float volume) {
    if (isValidSound())
      this.es.setVolume(volume); 
  }
  
  public void updateSoundLocation(Entity entity) {
    if (isValidSound())
      this.es.setPosition(entity); 
  }
  
  public void updateSoundLocation(double x, double y, double z) {
    if (isValidSound())
      this.es.setPosition(x, y, z); 
  }
  
  public void _updateSoundLocation(Entity entityListener, Entity entity) {
    if (isValidSound())
      this.es.setPosition(entity); 
  }
}
