package mcheli.wrapper;

import mcheli.__helper.MCH_SoundEvents;
import net.minecraft.client.audio.MovingSound;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvent;

public class W_Sound extends MovingSound {
  protected W_Sound(ResourceLocation r, float volume, float pitch, double x, double y, double z) {
    super(MCH_SoundEvents.getSound(r), SoundCategory.MASTER);
    setVolumeAndPitch(volume, pitch);
    setPosition(x, y, z);
  }
  
  protected W_Sound(ResourceLocation r, float volume, float pitch) {
    this(MCH_SoundEvents.getSound(r), volume, pitch);
  }
  
  protected W_Sound(SoundEvent soundEvent, float volume, float pitch) {
    super(soundEvent, SoundCategory.MASTER);
    setVolumeAndPitch(volume, pitch);
    Entity entity = W_McClient.getRenderEntity();
    if (entity != null)
      setPosition(entity.posX, entity.posY, entity.posZ); 
  }
  
  public void setRepeat(boolean b) {
    this.repeat = b;
  }
  
  public void setSoundParam(Entity e, float v, float p) {
    setPosition(e);
    setVolumeAndPitch(v, p);
  }
  
  public void setVolumeAndPitch(float v, float p) {
    setVolume(v);
    setPitch(p);
  }
  
  public void setVolume(float v) {
    this.volume = v;
  }
  
  public void setPitch(float p) {
    this.pitch = p;
  }
  
  public void setPosition(double x, double y, double z) {
    this.xPosF = (float)x;
    this.yPosF = (float)y;
    this.zPosF = (float)z;
  }
  
  public void setPosition(Entity e) {
    setPosition(e.posX, e.posY, e.posZ);
  }
  
  public void update() {}
}
