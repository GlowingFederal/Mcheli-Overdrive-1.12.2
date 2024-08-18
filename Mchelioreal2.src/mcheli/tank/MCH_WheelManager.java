package mcheli.tank;

import java.util.List;
import java.util.Random;
import mcheli.MCH_Config;
import mcheli.MCH_Lib;
import mcheli.aircraft.MCH_AircraftInfo;
import mcheli.aircraft.MCH_EntityAircraft;
import mcheli.particles.MCH_ParticlesUtil;
import mcheli.wrapper.W_Block;
import mcheli.wrapper.W_Blocks;
import mcheli.wrapper.W_Lib;
import mcheli.wrapper.W_WorldFunc;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.MoverType;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

public class MCH_WheelManager {
  public final MCH_EntityAircraft parent;
  
  public MCH_EntityWheel[] wheels;
  
  private double minZ;
  
  private double maxZ;
  
  private double avgZ;
  
  public Vec3d weightedCenter;
  
  public float targetPitch;
  
  public float targetRoll;
  
  public float prevYaw;
  
  private static Random rand = new Random();
  
  public MCH_WheelManager(MCH_EntityAircraft ac) {
    this.parent = ac;
    this.wheels = new MCH_EntityWheel[0];
    this.weightedCenter = Vec3d.field_186680_a;
  }
  
  public void createWheels(World w, List<MCH_AircraftInfo.Wheel> list, Vec3d weightedCenter) {
    this.wheels = new MCH_EntityWheel[list.size() * 2];
    this.minZ = 999999.0D;
    this.maxZ = -999999.0D;
    this.weightedCenter = weightedCenter;
    for (int i = 0; i < this.wheels.length; i++) {
      MCH_EntityWheel wheel = new MCH_EntityWheel(w);
      wheel.setParents(this.parent);
      Vec3d wp = ((MCH_AircraftInfo.Wheel)list.get(i / 2)).pos;
      wheel.setWheelPos(new Vec3d((i % 2 == 0) ? wp.field_72450_a : -wp.field_72450_a, wp.field_72448_b, wp.field_72449_c), this.weightedCenter);
      Vec3d v = this.parent.getTransformedPosition(wheel.pos.field_72450_a, wheel.pos.field_72448_b, wheel.pos.field_72449_c);
      wheel.func_70012_b(v.field_72450_a, v.field_72448_b + 1.0D, v.field_72449_c, 0.0F, 0.0F);
      this.wheels[i] = wheel;
      if (wheel.pos.field_72449_c <= this.minZ)
        this.minZ = wheel.pos.field_72449_c; 
      if (wheel.pos.field_72449_c >= this.maxZ)
        this.maxZ = wheel.pos.field_72449_c; 
    } 
    this.avgZ = this.maxZ - this.minZ;
  }
  
  public void move(double x, double y, double z) {
    MCH_EntityAircraft ac = this.parent;
    if (ac.getAcInfo() == null)
      return; 
    boolean showLog = (ac.field_70173_aa % 1 == 1);
    if (showLog)
      MCH_Lib.DbgLog(ac.field_70170_p, "[" + (ac.field_70170_p.field_72995_K ? "Client" : "Server") + "] ==============================", new Object[0]); 
    for (MCH_EntityWheel wheel : this.wheels) {
      wheel.field_70169_q = wheel.field_70165_t;
      wheel.field_70167_r = wheel.field_70163_u;
      wheel.field_70166_s = wheel.field_70161_v;
      Vec3d v = ac.getTransformedPosition(wheel.pos.field_72450_a, wheel.pos.field_72448_b, wheel.pos.field_72449_c);
      wheel.field_70159_w = v.field_72450_a - wheel.field_70165_t + x;
      wheel.field_70181_x = v.field_72448_b - wheel.field_70163_u;
      wheel.field_70179_y = v.field_72449_c - wheel.field_70161_v + z;
    } 
    for (MCH_EntityWheel wheel : this.wheels) {
      wheel.field_70181_x *= 0.15D;
      wheel.func_70091_d(MoverType.SELF, wheel.field_70159_w, wheel.field_70181_x, wheel.field_70179_y);
      double f = 1.0D;
      wheel.func_70091_d(MoverType.SELF, 0.0D, -0.1D * f, 0.0D);
    } 
    int zmog = -1;
    int i;
    for (i = 0; i < this.wheels.length / 2; i++) {
      zmog = i;
      MCH_EntityWheel w1 = this.wheels[i * 2 + 0];
      MCH_EntityWheel w2 = this.wheels[i * 2 + 1];
      if (!w1.isPlus)
        if (w1.field_70122_E || w2.field_70122_E) {
          zmog = -1;
          break;
        }  
    } 
    if (zmog >= 0) {
      (this.wheels[zmog * 2 + 0]).field_70122_E = true;
      (this.wheels[zmog * 2 + 1]).field_70122_E = true;
    } 
    zmog = -1;
    for (i = this.wheels.length / 2 - 1; i >= 0; i--) {
      zmog = i;
      MCH_EntityWheel w1 = this.wheels[i * 2 + 0];
      MCH_EntityWheel w2 = this.wheels[i * 2 + 1];
      if (w1.isPlus)
        if (w1.field_70122_E || w2.field_70122_E) {
          zmog = -1;
          break;
        }  
    } 
    if (zmog >= 0) {
      (this.wheels[zmog * 2 + 0]).field_70122_E = true;
      (this.wheels[zmog * 2 + 1]).field_70122_E = true;
    } 
    Vec3d rv = Vec3d.field_186680_a;
    Vec3d wc = ac.getTransformedPosition(this.weightedCenter);
    wc = new Vec3d(wc.field_72450_a - ac.field_70165_t, this.weightedCenter.field_72448_b, wc.field_72449_c - ac.field_70161_v);
    for (int j = 0; j < this.wheels.length / 2; j++) {
      MCH_EntityWheel w1 = this.wheels[j * 2 + 0];
      MCH_EntityWheel w2 = this.wheels[j * 2 + 1];
      Vec3d v1 = new Vec3d(w1.field_70165_t - ac.field_70165_t + wc.field_72450_a, w1.field_70163_u - ac.field_70163_u + wc.field_72448_b, w1.field_70161_v - ac.field_70161_v + wc.field_72449_c);
      Vec3d v2 = new Vec3d(w2.field_70165_t - ac.field_70165_t + wc.field_72450_a, w2.field_70163_u - ac.field_70163_u + wc.field_72448_b, w2.field_70161_v - ac.field_70161_v + wc.field_72449_c);
      Vec3d v = (w1.pos.field_72449_c >= 0.0D) ? v2.func_72431_c(v1) : v1.func_72431_c(v2);
      v = v.func_72432_b();
      double f = Math.abs(w1.pos.field_72449_c / this.avgZ);
      if (!w1.field_70122_E && !w2.field_70122_E)
        f = 0.0D; 
      rv = rv.func_72441_c(v.field_72450_a * f, v.field_72448_b * f, v.field_72449_c * f);
      if (showLog) {
        v = v.func_178785_b((float)(ac.getRotYaw() * Math.PI / 180.0D));
        MCH_Lib.DbgLog(ac.field_70170_p, "%2d : %.2f :[%+.1f, %+.1f, %+.1f][%s %d %d][%+.2f(%+.2f), %+.2f(%+.2f)][%+.1f, %+.1f, %+.1f]", new Object[] { 
              Integer.valueOf(j), Double.valueOf(f), Double.valueOf(v.field_72450_a), Double.valueOf(v.field_72448_b), 
              Double.valueOf(v.field_72449_c), w1.isPlus ? "+" : "-", Integer.valueOf(w1.field_70122_E ? 1 : 0), 
              Integer.valueOf(w2.field_70122_E ? 1 : 0), Double.valueOf(w1.field_70163_u - w1.field_70167_r), 
              Double.valueOf(w1.field_70181_x), 
              Double.valueOf(w2.field_70163_u - w2.field_70167_r), 
              Double.valueOf(w2.field_70181_x), Double.valueOf(v.field_72450_a), Double.valueOf(v.field_72448_b), 
              Double.valueOf(v.field_72449_c) });
      } 
    } 
    rv = rv.func_72432_b();
    if (rv.field_72448_b > 0.01D && rv.field_72448_b < 0.7D) {
      ac.field_70159_w += rv.field_72450_a / 50.0D;
      ac.field_70179_y += rv.field_72449_c / 50.0D;
    } 
    rv = rv.func_178785_b((float)(ac.getRotYaw() * Math.PI / 180.0D));
    float pitch = (float)(90.0D - Math.atan2(rv.field_72448_b, rv.field_72449_c) * 180.0D / Math.PI);
    float roll = -((float)(90.0D - Math.atan2(rv.field_72448_b, rv.field_72450_a) * 180.0D / Math.PI));
    float ogpf = (ac.getAcInfo()).onGroundPitchFactor;
    if (pitch - ac.getRotPitch() > ogpf)
      pitch = ac.getRotPitch() + ogpf; 
    if (pitch - ac.getRotPitch() < -ogpf)
      pitch = ac.getRotPitch() - ogpf; 
    float ogrf = (ac.getAcInfo()).onGroundRollFactor;
    if (roll - ac.getRotRoll() > ogrf)
      roll = ac.getRotRoll() + ogrf; 
    if (roll - ac.getRotRoll() < -ogrf)
      roll = ac.getRotRoll() - ogrf; 
    this.targetPitch = pitch;
    this.targetRoll = roll;
    if (!W_Lib.isClientPlayer(ac.getRiddenByEntity())) {
      ac.setRotPitch(pitch);
      ac.setRotRoll(roll);
    } 
    if (showLog)
      MCH_Lib.DbgLog(ac.field_70170_p, "%+03d, %+03d :[%.2f, %.2f, %.2f] yaw=%.2f, pitch=%.2f, roll=%.2f", new Object[] { Integer.valueOf((int)pitch), Integer.valueOf((int)roll), Double.valueOf(rv.field_72450_a), 
            Double.valueOf(rv.field_72448_b), Double.valueOf(rv.field_72449_c), Float.valueOf(ac.getRotYaw()), 
            Float.valueOf(this.targetPitch), Float.valueOf(this.targetRoll) }); 
    for (MCH_EntityWheel wheel : this.wheels) {
      Vec3d v = getTransformedPosition(wheel.pos.field_72450_a, wheel.pos.field_72448_b, wheel.pos.field_72449_c, ac, ac.getRotYaw(), this.targetPitch, this.targetRoll);
      double rangeH = 2.0D;
      double poy = (wheel.field_70138_W / 2.0F);
      if (wheel.field_70165_t > v.field_72450_a + rangeH) {
        wheel.field_70165_t = v.field_72450_a + rangeH;
        wheel.field_70163_u = v.field_72448_b + poy;
      } 
      if (wheel.field_70165_t < v.field_72450_a - rangeH) {
        wheel.field_70165_t = v.field_72450_a - rangeH;
        wheel.field_70163_u = v.field_72448_b + poy;
      } 
      if (wheel.field_70161_v > v.field_72449_c + rangeH) {
        wheel.field_70161_v = v.field_72449_c + rangeH;
        wheel.field_70163_u = v.field_72448_b + poy;
      } 
      if (wheel.field_70161_v < v.field_72449_c - rangeH) {
        wheel.field_70161_v = v.field_72449_c - rangeH;
        wheel.field_70163_u = v.field_72448_b + poy;
      } 
      wheel.func_70080_a(wheel.field_70165_t, wheel.field_70163_u, wheel.field_70161_v, 0.0F, 0.0F);
    } 
  }
  
  public Vec3d getTransformedPosition(double x, double y, double z, MCH_EntityAircraft ac, float yaw, float pitch, float roll) {
    Vec3d v = MCH_Lib.RotVec3(x, y, z, -yaw, -pitch, -roll);
    return v.func_72441_c(ac.field_70165_t, ac.field_70163_u, ac.field_70161_v);
  }
  
  public void updateBlock() {
    if (!MCH_Config.Collision_DestroyBlock.prmBool)
      return; 
    MCH_EntityAircraft ac = this.parent;
    for (MCH_EntityWheel w : this.wheels) {
      Vec3d v = ac.getTransformedPosition(w.pos);
      int x = (int)(v.field_72450_a + 0.5D);
      int y = (int)(v.field_72448_b + 0.5D);
      int z = (int)(v.field_72449_c + 0.5D);
      BlockPos blockpos = new BlockPos(x, y, z);
      IBlockState iblockstate = ac.field_70170_p.func_180495_p(blockpos);
      if (iblockstate.func_177230_c() == W_Block.getSnowLayer())
        ac.field_70170_p.func_175698_g(blockpos); 
      if (iblockstate.func_177230_c() == W_Blocks.field_150392_bi || iblockstate.func_177230_c() == W_Blocks.field_150414_aQ)
        W_WorldFunc.destroyBlock(ac.field_70170_p, x, y, z, false); 
    } 
  }
  
  public void particleLandingGear() {
    if (this.wheels.length <= 0)
      return; 
    MCH_EntityAircraft ac = this.parent;
    double d = ac.field_70159_w * ac.field_70159_w + ac.field_70179_y * ac.field_70179_y + Math.abs(this.prevYaw - ac.getRotYaw());
    this.prevYaw = ac.getRotYaw();
    if (d > 0.001D)
      for (int i = 0; i < 2; i++) {
        MCH_EntityWheel w = this.wheels[rand.nextInt(this.wheels.length)];
        Vec3d v = ac.getTransformedPosition(w.pos);
        int x = MathHelper.func_76128_c(v.field_72450_a + 0.5D);
        int y = MathHelper.func_76128_c(v.field_72448_b - 0.5D);
        int z = MathHelper.func_76128_c(v.field_72449_c + 0.5D);
        BlockPos blockpos = new BlockPos(x, y, z);
        IBlockState iblockstate = ac.field_70170_p.func_180495_p(blockpos);
        if (Block.func_149680_a(iblockstate.func_177230_c(), Blocks.field_150350_a)) {
          y = MathHelper.func_76128_c(v.field_72448_b + 0.5D);
          blockpos = new BlockPos(x, y, z);
          iblockstate = ac.field_70170_p.func_180495_p(blockpos);
        } 
        if (!Block.func_149680_a(iblockstate.func_177230_c(), Blocks.field_150350_a))
          MCH_ParticlesUtil.spawnParticleTileCrack(ac.field_70170_p, x, y, z, v.field_72450_a + rand.nextFloat() - 0.5D, v.field_72448_b + 0.1D, v.field_72449_c + rand
              .nextFloat() - 0.5D, -ac.field_70159_w * 4.0D + (rand
              .nextFloat() - 0.5D) * 0.1D, rand.nextFloat() * 0.5D, -ac.field_70179_y * 4.0D + (rand
              .nextFloat() - 0.5D) * 0.1D); 
      }  
  }
}
