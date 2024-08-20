package mcheli.flare;

import java.util.Random;
import mcheli.MCH_Lib;
import mcheli.aircraft.MCH_EntityAircraft;
import mcheli.particles.MCH_ParticleParam;
import mcheli.particles.MCH_ParticlesUtil;
import mcheli.wrapper.W_McClient;
import net.minecraft.entity.Entity;
import net.minecraft.init.SoundEvents;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

public class MCH_Flare {
  public final World worldObj;
  
  public final MCH_EntityAircraft aircraft;
  
  public final Random rand;
  
  public int numFlare;
  
  public int tick;
  
  private int flareType;
  
  private static FlareParam[] FLARE_DATA = null;
  
  public MCH_Flare(World w, MCH_EntityAircraft ac) {
    this.worldObj = w;
    this.aircraft = ac;
    this.rand = new Random();
    this.tick = 0;
    this.numFlare = 0;
    this.flareType = 0;
    if (FLARE_DATA == null) {
      int delay = w.isRemote ? 50 : 0;
      FLARE_DATA = new FlareParam[11];
      FLARE_DATA[1] = new FlareParam(1, 3, 200 + delay, 100, 16);
      FLARE_DATA[2] = new FlareParam(3, 5, 300 + delay, 200, 16);
      FLARE_DATA[3] = new FlareParam(2, 3, 200 + delay, 100, 16);
      FLARE_DATA[4] = new FlareParam(1, 3, 200 + delay, 100, 16);
      FLARE_DATA[5] = new FlareParam(2, 3, 200 + delay, 100, 16);
      FLARE_DATA[10] = new FlareParam(8, 1, 250 + delay, 60, 1);
      FLARE_DATA[0] = FLARE_DATA[1];
      FLARE_DATA[6] = FLARE_DATA[1];
      FLARE_DATA[7] = FLARE_DATA[1];
      FLARE_DATA[8] = FLARE_DATA[1];
      FLARE_DATA[9] = FLARE_DATA[1];
    } 
  }
  
  public boolean isInPreparation() {
    return (this.tick != 0);
  }
  
  public boolean isUsing() {
    int type = getFlareType();
    return (this.tick != 0 && type < FLARE_DATA.length && this.tick > (FLARE_DATA[type]).tickWait - (FLARE_DATA[type]).tickEnable);
  }
  
  public int getFlareType() {
    return this.flareType;
  }
  
  public void spawnParticle(String name, int num, float size) {
    if (this.worldObj.isRemote) {
      if (name.isEmpty() || num < 1 || num > 50)
        return; 
      double x = (this.aircraft.posX - this.aircraft.prevPosX) / num;
      double y = (this.aircraft.posY - this.aircraft.prevPosY) / num;
      double z = (this.aircraft.posZ - this.aircraft.prevPosZ) / num;
      for (int i = 0; i < num; i++) {
        MCH_ParticleParam prm = new MCH_ParticleParam(this.worldObj, "smoke", this.aircraft.prevPosX + x * i, this.aircraft.prevPosY + y * i, this.aircraft.prevPosZ + z * i);
        prm.size = size + this.rand.nextFloat();
        MCH_ParticlesUtil.spawnParticle(prm);
      } 
    } 
  }
  
  public boolean use(int type) {
    boolean result = false;
    MCH_Lib.DbgLog(this.aircraft.world, "MCH_Flare.use type = %d", new Object[] { Integer.valueOf(type) });
    this.flareType = type;
    if (type <= 0 && type >= FLARE_DATA.length)
      return false; 
    if (this.worldObj.isRemote) {
      if (this.tick == 0) {
        this.tick = (FLARE_DATA[getFlareType()]).tickWait;
        result = true;
        this.numFlare = 0;
        W_McClient.playSoundClick(1.0F, 1.0F);
      } 
    } else {
      result = true;
      this.numFlare = 0;
      this.tick = (FLARE_DATA[getFlareType()]).tickWait;
      this.aircraft.getEntityData().setBoolean("FlareUsing", true);
    } 
    return result;
  }
  
  public void update() {
    int type = getFlareType();
    if (this.aircraft == null || this.aircraft.isDead || type <= 0 || type > FLARE_DATA.length)
      return; 
    if (this.tick > 0)
      this.tick--; 
    if (!this.worldObj.isRemote)
      if (this.tick > 0 && this.tick % (FLARE_DATA[type]).interval == 0 && this.numFlare < (FLARE_DATA[type]).numFlareMax) {
        Vec3d v = (this.aircraft.getAcInfo()).flare.pos;
        v = this.aircraft.getTransformedPosition(v.x, v.y, v.z, this.aircraft.prevPosX, this.aircraft.prevPosY, this.aircraft.prevPosZ);
        spawnFlare(v);
      }  
    if (!isUsing() && this.aircraft.getEntityData().getBoolean("FlareUsing"))
      this.aircraft.getEntityData().setBoolean("FlareUsing", false); 
  }
  
  private void spawnFlare(Vec3d v) {
    this.numFlare++;
    int type = getFlareType();
    int num = (FLARE_DATA[type]).num;
    double x = v.x - this.aircraft.motionX * 2.0D;
    double y = v.y - this.aircraft.motionY * 2.0D - 1.0D;
    double z = v.z - this.aircraft.motionZ * 2.0D;
    this.worldObj.playSound(null, new BlockPos(x, y, z), SoundEvents.BLOCK_FIRE_EXTINGUISH, SoundCategory.BLOCKS, 0.5F, 2.6F + (this.worldObj.rand
        .nextFloat() - this.worldObj.rand.nextFloat()) * 0.8F);
    for (int i = 0; i < num; i++) {
      x = v.x - this.aircraft.motionX * 2.0D;
      y = v.y - this.aircraft.motionY * 2.0D - 1.0D;
      z = v.z - this.aircraft.motionZ * 2.0D;
      double tx = 0.0D;
      double ty = this.aircraft.motionY;
      double tz = 0.0D;
      int fuseCount = 0;
      double r = this.aircraft.rotationYaw;
      if (type == 1) {
        tx = MathHelper.sin(this.rand.nextFloat() * 360.0F);
        tz = MathHelper.cos(this.rand.nextFloat() * 360.0F);
      } else if (type == 2 || type == 3) {
        if (i == 0)
          r += 90.0D; 
        if (i == 1)
          r -= 90.0D; 
        if (i == 2)
          r += 180.0D; 
        r *= 0.017453292519943295D;
        tx = -Math.sin(r) + (this.rand.nextFloat() - 0.5D) * 0.6D;
        tz = Math.cos(r) + (this.rand.nextFloat() - 0.5D) * 0.6D;
      } else if (type == 4) {
        r *= 0.017453292519943295D;
        tx = -Math.sin(r) + (this.rand.nextFloat() - 0.5D) * 1.3D;
        tz = Math.cos(r) + (this.rand.nextFloat() - 0.5D) * 1.3D;
      } else if (type == 5) {
        r *= 0.017453292519943295D;
        tx = -Math.sin(r) + (this.rand.nextFloat() - 0.5D) * 0.9D;
        tz = Math.cos(r) + (this.rand.nextFloat() - 0.5D) * 0.9D;
        tx *= 0.3D;
        tz *= 0.3D;
      } 
      tx += this.aircraft.motionX;
      ty += this.aircraft.motionY / 2.0D;
      tz += this.aircraft.motionZ;
      if (type == 10) {
        r += (360 / num / 2 + i * 360 / num);
        r *= 0.017453292519943295D;
        tx = -Math.sin(r) * 2.0D;
        tz = Math.cos(r) * 2.0D;
        ty = 0.7D;
        y += 2.0D;
        fuseCount = 10;
      } 
      MCH_EntityFlare e = new MCH_EntityFlare(this.worldObj, x, y, z, tx * 0.5D, ty * 0.5D, tz * 0.5D, 6.0F, fuseCount);
      e.rotationPitch = this.rand.nextFloat() * 360.0F;
      e.rotationYaw = this.rand.nextFloat() * 360.0F;
      e.prevRotationPitch = this.rand.nextFloat() * 360.0F;
      e.prevRotationYaw = this.rand.nextFloat() * 360.0F;
      if (type == 4) {
        e.gravity *= 0.6D;
        e.airResistance = 0.995D;
      } 
      this.worldObj.spawnEntity((Entity)e);
    } 
  }
  
  class FlareParam {
    public final int num;
    
    public final int interval;
    
    public final int tickWait;
    
    public final int tickEnable;
    
    public final int numFlareMax;
    
    public FlareParam(int num, int interval, int tickWait, int tickEnable, int numFlareMax) {
      this.num = num;
      this.interval = interval;
      this.tickWait = tickWait;
      this.tickEnable = tickEnable;
      this.numFlareMax = numFlareMax;
    }
  }
}
