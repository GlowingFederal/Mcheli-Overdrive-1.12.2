package mcheli.aircraft;

import mcheli.MCH_Lib;
import mcheli.wrapper.W_WorldFunc;
import net.minecraft.entity.Entity;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.EntityDataManager;

public class MCH_Parts {
  public final Entity parent;
  
  public final EntityDataManager dataManager;
  
  public final int shift;
  
  public final DataParameter<Integer> dataKey;
  
  public final String partName;
  
  public float prevRotation = 0.0F;
  
  public float rotation = 0.0F;
  
  public float rotationMax = 90.0F;
  
  public float rotationInv = 1.0F;
  
  public Sound soundStartSwichOn = new Sound(this);
  
  public Sound soundEndSwichOn = new Sound(this);
  
  public Sound soundSwitching = new Sound(this);
  
  public Sound soundStartSwichOff = new Sound(this);
  
  public Sound soundEndSwichOff = new Sound(this);
  
  private boolean status = false;
  
  public MCH_Parts(Entity parent, int shiftBit, DataParameter<Integer> dataKey, String name) {
    this.parent = parent;
    this.dataManager = parent.func_184212_Q();
    this.shift = shiftBit;
    this.dataKey = dataKey;
    this.status = ((getDataWatcherValue() & 1 << this.shift) != 0);
    this.partName = name;
  }
  
  public int getDataWatcherValue() {
    return ((Integer)this.dataManager.func_187225_a(this.dataKey)).intValue();
  }
  
  public void setStatusServer(boolean stat) {
    setStatusServer(stat, true);
  }
  
  public void setStatusServer(boolean stat, boolean playSound) {
    if (!this.parent.field_70170_p.field_72995_K)
      if (getStatus() != stat) {
        MCH_Lib.DbgLog(false, "setStatusServer(ID=%d %s :%s -> %s)", new Object[] { Integer.valueOf(this.shift), this.partName, getStatus() ? "ON" : "OFF", stat ? "ON" : "OFF" });
        updateDataWatcher(stat);
        playSound(this.soundSwitching);
        if (!stat) {
          playSound(this.soundStartSwichOff);
        } else {
          playSound(this.soundStartSwichOn);
        } 
        update();
      }  
  }
  
  protected void updateDataWatcher(boolean stat) {
    int currentStatus = getDataWatcherValue();
    int mask = 1 << this.shift;
    if (!stat) {
      this.dataManager.func_187227_b(this.dataKey, Integer.valueOf(currentStatus & (mask ^ 0xFFFFFFFF)));
    } else {
      this.dataManager.func_187227_b(this.dataKey, Integer.valueOf(currentStatus | mask));
    } 
    this.status = stat;
  }
  
  public boolean getStatus() {
    return this.status;
  }
  
  public boolean isOFF() {
    return (!this.status && this.rotation <= 0.02F);
  }
  
  public boolean isON() {
    return (this.status == true && this.rotation >= this.rotationMax - 0.02F);
  }
  
  public void updateStatusClient(int statFromDataWatcher) {
    if (this.parent.field_70170_p.field_72995_K)
      this.status = ((statFromDataWatcher & 1 << this.shift) != 0); 
  }
  
  public void update() {
    this.prevRotation = this.rotation;
    if (getStatus()) {
      if (this.rotation < this.rotationMax) {
        this.rotation += this.rotationInv;
        if (this.rotation >= this.rotationMax)
          playSound(this.soundEndSwichOn); 
      } 
    } else if (this.rotation > 0.0F) {
      this.rotation -= this.rotationInv;
      if (this.rotation <= 0.0F)
        playSound(this.soundEndSwichOff); 
    } 
  }
  
  public void forceSwitch(boolean onoff) {
    updateDataWatcher(onoff);
    this.rotation = this.prevRotation = this.rotationMax;
  }
  
  public float getFactor() {
    if (this.rotationMax > 0.0F)
      return this.rotation / this.rotationMax; 
    return 0.0F;
  }
  
  public void playSound(Sound snd) {
    if (!snd.name.isEmpty() && !this.parent.field_70170_p.field_72995_K)
      W_WorldFunc.MOD_playSoundAtEntity(this.parent, snd.name, snd.volume, snd.pitch); 
  }
  
  public class Sound {
    public String name = "";
    
    public float volume = 1.0F;
    
    public float pitch = 1.0F;
    
    public Sound(MCH_Parts paramMCH_Parts) {}
    
    public void setPrm(String n, float v, float p) {
      this.name = n;
      this.volume = v;
      this.pitch = p;
    }
  }
}
