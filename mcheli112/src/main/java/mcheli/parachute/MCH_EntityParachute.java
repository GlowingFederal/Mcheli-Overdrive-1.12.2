package mcheli.parachute;

import java.util.List;
import javax.annotation.Nullable;
import mcheli.MCH_Lib;
import mcheli.__helper.entity.IEntitySinglePassenger;
import mcheli.particles.MCH_ParticleParam;
import mcheli.particles.MCH_ParticlesUtil;
import mcheli.wrapper.W_AxisAlignedBB;
import mcheli.wrapper.W_Block;
import mcheli.wrapper.W_Entity;
import mcheli.wrapper.W_Lib;
import mcheli.wrapper.W_WorldFunc;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.entity.Entity;
import net.minecraft.entity.MoverType;
import net.minecraft.entity.item.EntityBoat;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class MCH_EntityParachute extends W_Entity implements IEntitySinglePassenger {
  private static final DataParameter<Byte> TYPE = EntityDataManager.func_187226_a(MCH_EntityParachute.class, DataSerializers.field_187191_a);
  
  private double speedMultiplier;
  
  private int paraPosRotInc;
  
  private double paraX;
  
  private double paraY;
  
  private double paraZ;
  
  private double paraYaw;
  
  private double paraPitch;
  
  @SideOnly(Side.CLIENT)
  private double velocityX;
  
  @SideOnly(Side.CLIENT)
  private double velocityY;
  
  @SideOnly(Side.CLIENT)
  private double velocityZ;
  
  public Entity user;
  
  public int onGroundCount;
  
  public MCH_EntityParachute(World par1World) {
    super(par1World);
    this.speedMultiplier = 0.07D;
    this.field_70156_m = true;
    func_70105_a(1.5F, 0.6F);
    this.user = null;
    this.onGroundCount = 0;
  }
  
  public MCH_EntityParachute(World par1World, double par2, double par4, double par6) {
    this(par1World);
    func_70107_b(par2, par4, par6);
    this.field_70159_w = 0.0D;
    this.field_70181_x = 0.0D;
    this.field_70179_y = 0.0D;
    this.field_70169_q = par2;
    this.field_70167_r = par4;
    this.field_70166_s = par6;
  }
  
  protected boolean func_70041_e_() {
    return false;
  }
  
  protected void func_70088_a() {
    this.field_70180_af.func_187214_a(TYPE, Byte.valueOf((byte)0));
  }
  
  public void setType(int n) {
    this.field_70180_af.func_187227_b(TYPE, Byte.valueOf((byte)n));
  }
  
  public int getType() {
    return ((Byte)this.field_70180_af.func_187225_a(TYPE)).byteValue();
  }
  
  public AxisAlignedBB func_70114_g(Entity par1Entity) {
    return par1Entity.func_174813_aQ();
  }
  
  public AxisAlignedBB func_70046_E() {
    return func_174813_aQ();
  }
  
  public boolean func_70104_M() {
    return true;
  }
  
  public double func_70042_X() {
    return this.field_70131_O * 0.0D - 0.30000001192092896D;
  }
  
  public boolean func_70097_a(DamageSource par1DamageSource, float par2) {
    return false;
  }
  
  public boolean func_70067_L() {
    return !this.field_70128_L;
  }
  
  @SideOnly(Side.CLIENT)
  public void func_180426_a(double x, double y, double z, float yaw, float pitch, int posRotationIncrements, boolean teleport) {
    this.paraPosRotInc = posRotationIncrements + 10;
    this.paraX = x;
    this.paraY = y;
    this.paraZ = z;
    this.paraYaw = yaw;
    this.paraPitch = pitch;
    this.field_70159_w = this.velocityX;
    this.field_70181_x = this.velocityY;
    this.field_70179_y = this.velocityZ;
  }
  
  @SideOnly(Side.CLIENT)
  public void func_70016_h(double par1, double par3, double par5) {
    this.velocityX = this.field_70159_w = par1;
    this.velocityY = this.field_70181_x = par3;
    this.velocityZ = this.field_70179_y = par5;
  }
  
  public void func_70106_y() {
    super.func_70106_y();
  }
  
  public void func_70071_h_() {
    super.func_70071_h_();
    if (!this.field_70170_p.field_72995_K && this.field_70173_aa % 10 == 0)
      MCH_Lib.DbgLog(this.field_70170_p, "MCH_EntityParachute.onUpdate %d, %.3f", new Object[] { Integer.valueOf(this.field_70173_aa), Double.valueOf(this.field_70181_x) }); 
    if (isOpenParachute() && this.field_70181_x > -0.3D && this.field_70173_aa > 20)
      this.field_70143_R = (float)(this.field_70143_R * 0.85D); 
    if (!this.field_70170_p.field_72995_K && this.user != null && this.user.func_184187_bx() == null) {
      this.user.func_184220_m((Entity)this);
      this.field_70177_z = this.field_70126_B = this.user.field_70177_z;
      this.user = null;
    } 
    this.field_70169_q = this.field_70165_t;
    this.field_70167_r = this.field_70163_u;
    this.field_70166_s = this.field_70161_v;
    double d1 = (func_174813_aQ()).field_72338_b + ((func_174813_aQ()).field_72337_e - (func_174813_aQ()).field_72338_b) * 0.0D / 5.0D - 0.125D;
    double d2 = (func_174813_aQ()).field_72338_b + ((func_174813_aQ()).field_72337_e - (func_174813_aQ()).field_72338_b) * 1.0D / 5.0D - 0.125D;
    AxisAlignedBB axisalignedbb = W_AxisAlignedBB.getAABB((func_174813_aQ()).field_72340_a, d1, 
        (func_174813_aQ()).field_72339_c, (func_174813_aQ()).field_72336_d, d2, 
        (func_174813_aQ()).field_72334_f);
    if (this.field_70170_p.func_72875_a(axisalignedbb, Material.field_151586_h)) {
      onWaterSetBoat();
      func_70106_y();
    } 
    if (this.field_70170_p.field_72995_K) {
      onUpdateClient();
    } else {
      onUpdateServer();
    } 
  }
  
  public void onUpdateClient() {
    if (this.paraPosRotInc > 0) {
      double x = this.field_70165_t + (this.paraX - this.field_70165_t) / this.paraPosRotInc;
      double y = this.field_70163_u + (this.paraY - this.field_70163_u) / this.paraPosRotInc;
      double z = this.field_70161_v + (this.paraZ - this.field_70161_v) / this.paraPosRotInc;
      double yaw = MathHelper.func_76138_g(this.paraYaw - this.field_70177_z);
      this.field_70177_z = (float)(this.field_70177_z + yaw / this.paraPosRotInc);
      this.field_70125_A = (float)(this.field_70125_A + (this.paraPitch - this.field_70125_A) / this.paraPosRotInc);
      this.paraPosRotInc--;
      func_70107_b(x, y, z);
      func_70101_b(this.field_70177_z, this.field_70125_A);
      if (getRiddenByEntity() != null)
        func_70101_b((getRiddenByEntity()).field_70126_B, this.field_70125_A); 
    } else {
      func_70107_b(this.field_70165_t + this.field_70159_w, this.field_70163_u + this.field_70181_x, this.field_70161_v + this.field_70179_y);
      if (this.field_70122_E);
      this.field_70159_w *= 0.99D;
      this.field_70181_x *= 0.95D;
      this.field_70179_y *= 0.99D;
    } 
    if (!isOpenParachute() && this.field_70181_x > 0.01D) {
      float color = 0.6F + this.field_70146_Z.nextFloat() * 0.2F;
      double dx = this.field_70169_q - this.field_70165_t;
      double dy = this.field_70167_r - this.field_70163_u;
      double dz = this.field_70166_s - this.field_70161_v;
      int num = 1 + (int)(MathHelper.func_76133_a(dx * dx + dy * dy + dz * dz) * 2.0D);
      double i;
      for (i = 0.0D; i < num; i++) {
        MCH_ParticleParam prm = new MCH_ParticleParam(this.field_70170_p, "smoke", this.field_70169_q + (this.field_70165_t - this.field_70169_q) * i / num * 0.8D, this.field_70167_r + (this.field_70163_u - this.field_70167_r) * i / num * 0.8D, this.field_70166_s + (this.field_70161_v - this.field_70166_s) * i / num * 0.8D);
        prm.motionX = this.field_70159_w * 0.5D + (this.field_70146_Z.nextDouble() - 0.5D) * 0.5D;
        prm.motionX = this.field_70181_x * -0.5D + (this.field_70146_Z.nextDouble() - 0.5D) * 0.5D;
        prm.motionX = this.field_70179_y * 0.5D + (this.field_70146_Z.nextDouble() - 0.5D) * 0.5D;
        prm.size = 5.0F;
        prm.setColor(0.8F + this.field_70146_Z.nextFloat(), color, color, color);
        MCH_ParticlesUtil.spawnParticle(prm);
      } 
    } 
  }
  
  public void onUpdateServer() {
    double prevSpeed = Math.sqrt(this.field_70159_w * this.field_70159_w + this.field_70179_y * this.field_70179_y);
    double gravity = this.field_70122_E ? 0.01D : 0.03D;
    if (getType() == 2 && this.field_70173_aa < 20)
      gravity = 0.01D; 
    this.field_70181_x -= gravity;
    if (isOpenParachute()) {
      if (W_Lib.isEntityLivingBase(getRiddenByEntity())) {
        double mv = W_Lib.getEntityMoveDist(getRiddenByEntity());
        if (!isOpenParachute())
          mv = 0.0D; 
        if (mv > 0.0D) {
          double mx = -Math.sin(((getRiddenByEntity()).field_70177_z * 3.1415927F / 180.0F));
          double mz = Math.cos(((getRiddenByEntity()).field_70177_z * 3.1415927F / 180.0F));
          this.field_70159_w += mx * this.speedMultiplier * 0.05D;
          this.field_70179_y += mz * this.speedMultiplier * 0.05D;
        } 
      } 
      double speed = Math.sqrt(this.field_70159_w * this.field_70159_w + this.field_70179_y * this.field_70179_y);
      if (speed > 0.35D) {
        this.field_70159_w *= 0.35D / speed;
        this.field_70179_y *= 0.35D / speed;
        speed = 0.35D;
      } 
      if (speed > prevSpeed && this.speedMultiplier < 0.35D) {
        this.speedMultiplier += (0.35D - this.speedMultiplier) / 35.0D;
        if (this.speedMultiplier > 0.35D)
          this.speedMultiplier = 0.35D; 
      } else {
        this.speedMultiplier -= (this.speedMultiplier - 0.07D) / 35.0D;
        if (this.speedMultiplier < 0.07D)
          this.speedMultiplier = 0.07D; 
      } 
    } 
    if (this.field_70122_E) {
      this.onGroundCount++;
      if (this.onGroundCount > 5) {
        onGroundAndDead();
        return;
      } 
    } 
    func_70091_d(MoverType.SELF, this.field_70159_w, this.field_70181_x, this.field_70179_y);
    if (getType() == 2 && this.field_70173_aa < 20) {
      this.field_70181_x *= 0.95D;
    } else {
      this.field_70181_x *= 0.9D;
    } 
    if (isOpenParachute()) {
      this.field_70159_w *= 0.95D;
      this.field_70179_y *= 0.95D;
    } else {
      this.field_70159_w *= 0.99D;
      this.field_70179_y *= 0.99D;
    } 
    this.field_70125_A = 0.0F;
    double yaw = this.field_70177_z;
    double dx = this.field_70169_q - this.field_70165_t;
    double dz = this.field_70166_s - this.field_70161_v;
    if (dx * dx + dz * dz > 0.001D)
      yaw = (float)(Math.atan2(dx, dz) * 180.0D / Math.PI); 
    double yawDiff = MathHelper.func_76138_g(yaw - this.field_70177_z);
    if (yawDiff > 20.0D)
      yawDiff = 20.0D; 
    if (yawDiff < -20.0D)
      yawDiff = -20.0D; 
    if (getRiddenByEntity() != null) {
      func_70101_b((getRiddenByEntity()).field_70177_z, this.field_70125_A);
    } else {
      this.field_70177_z = (float)(this.field_70177_z + yawDiff);
      func_70101_b(this.field_70177_z, this.field_70125_A);
    } 
    List<Entity> list = this.field_70170_p.func_72839_b((Entity)this, 
        func_174813_aQ().func_72314_b(0.2D, 0.0D, 0.2D));
    if (list != null && !list.isEmpty())
      for (int l = 0; l < list.size(); l++) {
        Entity entity = list.get(l);
        if (entity != getRiddenByEntity() && entity.func_70104_M() && entity instanceof MCH_EntityParachute)
          entity.func_70108_f((Entity)this); 
      }  
    if (getRiddenByEntity() != null && (getRiddenByEntity()).field_70128_L)
      func_70106_y(); 
  }
  
  public void onGroundAndDead() {
    this.field_70163_u += 1.2D;
    func_184232_k(getRiddenByEntity());
    func_70106_y();
  }
  
  public void onWaterSetBoat() {
    if (this.field_70170_p.field_72995_K)
      return; 
    if (getType() != 2)
      return; 
    if (getRiddenByEntity() == null)
      return; 
    int px = (int)(this.field_70165_t + 0.5D);
    int py = (int)(this.field_70163_u + 0.5D);
    int pz = (int)(this.field_70161_v + 0.5D);
    boolean foundBlock = false;
    for (int y = 0; y < 5; y++) {
      if (py + y < 0 || py + y > 255)
        break; 
      Block block = W_WorldFunc.getBlock(this.field_70170_p, px, py - y, pz);
      if (block == W_Block.getWater()) {
        py -= y;
        foundBlock = true;
        break;
      } 
    } 
    if (!foundBlock)
      return; 
    int countWater = 0;
    for (int i = 0; i < 3; i++) {
      if (py + i < 0 || py + i > 255)
        break; 
      for (int x = -2; x <= 2; x++) {
        for (int z = -2; z <= 2; z++) {
          Block block = W_WorldFunc.getBlock(this.field_70170_p, px + x, py - i, pz + z);
          if (block == W_Block.getWater()) {
            countWater++;
            if (countWater > 37)
              break; 
          } 
        } 
      } 
    } 
    if (countWater > 37) {
      EntityBoat entityboat = new EntityBoat(this.field_70170_p, px, (py + 1.0F), pz);
      entityboat.field_70177_z = this.field_70177_z - 90.0F;
      this.field_70170_p.func_72838_d((Entity)entityboat);
      getRiddenByEntity().func_184220_m((Entity)entityboat);
    } 
  }
  
  public boolean isOpenParachute() {
    return (getType() != 2 || this.field_70181_x < -0.1D);
  }
  
  public void func_184232_k(Entity passenger) {
    if (func_184196_w(passenger)) {
      double x = -Math.sin(this.field_70177_z * Math.PI / 180.0D) * 0.1D;
      double z = Math.cos(this.field_70177_z * Math.PI / 180.0D) * 0.1D;
      passenger.func_70107_b(this.field_70165_t + x, this.field_70163_u + func_70042_X() + passenger.func_70033_W(), this.field_70161_v + z);
    } 
  }
  
  protected void func_70014_b(NBTTagCompound nbt) {
    nbt.func_74774_a("ParachuteModelType", (byte)getType());
  }
  
  protected void func_70037_a(NBTTagCompound nbt) {
    setType(nbt.func_74771_c("ParachuteModelType"));
  }
  
  @SideOnly(Side.CLIENT)
  public float getShadowSize() {
    return 4.0F;
  }
  
  public boolean func_184230_a(EntityPlayer player, EnumHand hand) {
    return false;
  }
  
  @Nullable
  public Entity getRiddenByEntity() {
    List<Entity> passengers = func_184188_bt();
    return passengers.isEmpty() ? null : passengers.get(0);
  }
}
