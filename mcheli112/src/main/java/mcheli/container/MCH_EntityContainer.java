package mcheli.container;

import java.util.List;
import mcheli.MCH_Config;
import mcheli.MCH_Lib;
import mcheli.MCH_MOD;
import mcheli.__helper.entity.IEntityItemStackPickable;
import mcheli.aircraft.MCH_EntityAircraft;
import mcheli.aircraft.MCH_IEntityCanRideAircraft;
import mcheli.aircraft.MCH_SeatRackInfo;
import mcheli.multiplay.MCH_Multiplay;
import mcheli.wrapper.W_AxisAlignedBB;
import mcheli.wrapper.W_Block;
import mcheli.wrapper.W_Blocks;
import mcheli.wrapper.W_EntityContainer;
import mcheli.wrapper.W_WorldFunc;
import net.minecraft.block.material.Material;
import net.minecraft.entity.Entity;
import net.minecraft.entity.MoverType;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class MCH_EntityContainer extends W_EntityContainer implements MCH_IEntityCanRideAircraft, IEntityItemStackPickable {
  public static final float Y_OFFSET = 0.5F;
  
  private static final DataParameter<Integer> TIME_SINCE_HIT = EntityDataManager.func_187226_a(MCH_EntityContainer.class, DataSerializers.field_187192_b);
  
  private static final DataParameter<Integer> FORWARD_DIR = EntityDataManager.func_187226_a(MCH_EntityContainer.class, DataSerializers.field_187192_b);
  
  private static final DataParameter<Integer> DAMAGE_TAKEN = EntityDataManager.func_187226_a(MCH_EntityContainer.class, DataSerializers.field_187192_b);
  
  private double speedMultiplier;
  
  private int boatPosRotationIncrements;
  
  private double boatX;
  
  private double boatY;
  
  private double boatZ;
  
  private double boatYaw;
  
  private double boatPitch;
  
  @SideOnly(Side.CLIENT)
  private double velocityX;
  
  @SideOnly(Side.CLIENT)
  private double velocityY;
  
  @SideOnly(Side.CLIENT)
  private double velocityZ;
  
  public MCH_EntityContainer(World par1World) {
    super(par1World);
    this.speedMultiplier = 0.07D;
    this.field_70156_m = true;
    func_70105_a(2.0F, 1.0F);
    this.field_70138_W = 0.6F;
    this.field_70178_ae = true;
    this._renderDistanceWeight = 2.0D;
  }
  
  public MCH_EntityContainer(World par1World, double par2, double par4, double par6) {
    this(par1World);
    func_70107_b(par2, par4 + 0.5D, par6);
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
    this.field_70180_af.func_187214_a(TIME_SINCE_HIT, Integer.valueOf(0));
    this.field_70180_af.func_187214_a(FORWARD_DIR, Integer.valueOf(1));
    this.field_70180_af.func_187214_a(DAMAGE_TAKEN, Integer.valueOf(0));
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
  
  public int func_70302_i_() {
    return 54;
  }
  
  public String getInvName() {
    return "Container " + super.getInvName();
  }
  
  public double func_70042_X() {
    return -0.3D;
  }
  
  public boolean func_70097_a(DamageSource ds, float damage) {
    if (func_180431_b(ds))
      return false; 
    if (this.field_70170_p.field_72995_K || this.field_70128_L)
      return false; 
    damage = MCH_Config.applyDamageByExternal((Entity)this, ds, damage);
    if (!MCH_Multiplay.canAttackEntity(ds, (Entity)this))
      return false; 
    if (ds.func_76346_g() instanceof EntityPlayer && ds.func_76355_l().equalsIgnoreCase("player")) {
      MCH_Lib.DbgLog(this.field_70170_p, "MCH_EntityContainer.attackEntityFrom:damage=%.1f:%s", new Object[] { Float.valueOf(damage), ds.func_76355_l() });
      W_WorldFunc.MOD_playSoundAtEntity((Entity)this, "hit", 1.0F, 1.3F);
      setDamageTaken(getDamageTaken() + (int)(damage * 20.0F));
    } else {
      return false;
    } 
    setForwardDirection(-getForwardDirection());
    setTimeSinceHit(10);
    func_70018_K();
    boolean flag = (ds.func_76346_g() instanceof EntityPlayer && ((EntityPlayer)ds.func_76346_g()).field_71075_bZ.field_75098_d);
    if (flag || getDamageTaken() > 40.0F) {
      if (!flag)
        func_145778_a((Item)MCH_MOD.itemContainer, 1, 0.0F); 
      func_70106_y();
    } 
    return true;
  }
  
  @SideOnly(Side.CLIENT)
  public void func_70057_ab() {
    setForwardDirection(-getForwardDirection());
    setTimeSinceHit(10);
    setDamageTaken(getDamageTaken() * 11);
  }
  
  public boolean func_70067_L() {
    return !this.field_70128_L;
  }
  
  @SideOnly(Side.CLIENT)
  public void func_180426_a(double x, double y, double z, float yaw, float pitch, int posRotationIncrements, boolean teleport) {
    this.boatPosRotationIncrements = posRotationIncrements + 10;
    this.boatX = x;
    this.boatY = y;
    this.boatZ = z;
    this.boatYaw = yaw;
    this.boatPitch = pitch;
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
  
  public void func_70071_h_() {
    super.func_70071_h_();
    if (getTimeSinceHit() > 0)
      setTimeSinceHit(getTimeSinceHit() - 1); 
    if (getDamageTaken() > 0.0F)
      setDamageTaken(getDamageTaken() - 1); 
    this.field_70169_q = this.field_70165_t;
    this.field_70167_r = this.field_70163_u;
    this.field_70166_s = this.field_70161_v;
    byte b0 = 5;
    double d0 = 0.0D;
    for (int i = 0; i < b0; i++) {
      AxisAlignedBB boundingBox = func_174813_aQ();
      double d1 = boundingBox.field_72338_b + (boundingBox.field_72337_e - boundingBox.field_72338_b) * (i + 0) / b0 - 0.125D;
      double d2 = boundingBox.field_72338_b + (boundingBox.field_72337_e - boundingBox.field_72338_b) * (i + 1) / b0 - 0.125D;
      AxisAlignedBB axisalignedbb = W_AxisAlignedBB.getAABB(boundingBox.field_72340_a, d1, boundingBox.field_72339_c, boundingBox.field_72336_d, d2, boundingBox.field_72334_f);
      if (this.field_70170_p.func_72875_a(axisalignedbb, Material.field_151586_h)) {
        d0 += 1.0D / b0;
      } else if (this.field_70170_p.func_72875_a(axisalignedbb, Material.field_151587_i)) {
        d0 += 1.0D / b0;
      } 
    } 
    double d3 = Math.sqrt(this.field_70159_w * this.field_70159_w + this.field_70179_y * this.field_70179_y);
    if (d3 > 0.2625D);
    if (this.field_70170_p.field_72995_K) {
      if (this.boatPosRotationIncrements > 0) {
        double d4 = this.field_70165_t + (this.boatX - this.field_70165_t) / this.boatPosRotationIncrements;
        double d5 = this.field_70163_u + (this.boatY - this.field_70163_u) / this.boatPosRotationIncrements;
        double d11 = this.field_70161_v + (this.boatZ - this.field_70161_v) / this.boatPosRotationIncrements;
        double d10 = MathHelper.func_76138_g(this.boatYaw - this.field_70177_z);
        this.field_70177_z = (float)(this.field_70177_z + d10 / this.boatPosRotationIncrements);
        this.field_70125_A = (float)(this.field_70125_A + (this.boatPitch - this.field_70125_A) / this.boatPosRotationIncrements);
        this.boatPosRotationIncrements--;
        func_70107_b(d4, d5, d11);
        func_70101_b(this.field_70177_z, this.field_70125_A);
      } else {
        double d4 = this.field_70165_t + this.field_70159_w;
        double d5 = this.field_70163_u + this.field_70181_x;
        double d11 = this.field_70161_v + this.field_70179_y;
        func_70107_b(d4, d5, d11);
        if (this.field_70122_E) {
          this.field_70159_w *= 0.8999999761581421D;
          this.field_70179_y *= 0.8999999761581421D;
        } 
        this.field_70159_w *= 0.99D;
        this.field_70181_x *= 0.95D;
        this.field_70179_y *= 0.99D;
      } 
    } else {
      if (d0 < 1.0D) {
        double d = d0 * 2.0D - 1.0D;
        this.field_70181_x += 0.04D * d;
      } else {
        if (this.field_70181_x < 0.0D)
          this.field_70181_x /= 2.0D; 
        this.field_70181_x += 0.007D;
      } 
      double d4 = Math.sqrt(this.field_70159_w * this.field_70159_w + this.field_70179_y * this.field_70179_y);
      if (d4 > 0.35D) {
        double d = 0.35D / d4;
        this.field_70159_w *= d;
        this.field_70179_y *= d;
        d4 = 0.35D;
      } 
      if (d4 > d3 && this.speedMultiplier < 0.35D) {
        this.speedMultiplier += (0.35D - this.speedMultiplier) / 35.0D;
        if (this.speedMultiplier > 0.35D)
          this.speedMultiplier = 0.35D; 
      } else {
        this.speedMultiplier -= (this.speedMultiplier - 0.07D) / 35.0D;
        if (this.speedMultiplier < 0.07D)
          this.speedMultiplier = 0.07D; 
      } 
      if (this.field_70122_E) {
        this.field_70159_w *= 0.8999999761581421D;
        this.field_70179_y *= 0.8999999761581421D;
      } 
      func_70091_d(MoverType.SELF, this.field_70159_w, this.field_70181_x, this.field_70179_y);
      this.field_70159_w *= 0.99D;
      this.field_70181_x *= 0.95D;
      this.field_70179_y *= 0.99D;
      this.field_70125_A = 0.0F;
      double d5 = this.field_70177_z;
      double d11 = this.field_70169_q - this.field_70165_t;
      double d10 = this.field_70166_s - this.field_70161_v;
      if (d11 * d11 + d10 * d10 > 0.001D)
        d5 = (float)(Math.atan2(d10, d11) * 180.0D / Math.PI); 
      double d12 = MathHelper.func_76138_g(d5 - this.field_70177_z);
      if (d12 > 5.0D)
        d12 = 5.0D; 
      if (d12 < -5.0D)
        d12 = -5.0D; 
      this.field_70177_z = (float)(this.field_70177_z + d12);
      func_70101_b(this.field_70177_z, this.field_70125_A);
      if (!this.field_70170_p.field_72995_K) {
        List<Entity> list = this.field_70170_p.func_72839_b((Entity)this, 
            func_174813_aQ().func_72314_b(0.2D, 0.0D, 0.2D));
        if (list != null && !list.isEmpty())
          for (int l = 0; l < list.size(); l++) {
            Entity entity = list.get(l);
            if (entity.func_70104_M() && entity instanceof MCH_EntityContainer)
              entity.func_70108_f((Entity)this); 
          }  
        if (MCH_Config.Collision_DestroyBlock.prmBool)
          for (int l = 0; l < 4; l++) {
            int i1 = MathHelper.func_76128_c(this.field_70165_t + ((l % 2) - 0.5D) * 0.8D);
            int j1 = MathHelper.func_76128_c(this.field_70161_v + ((l / 2) - 0.5D) * 0.8D);
            for (int k1 = 0; k1 < 2; k1++) {
              int l1 = MathHelper.func_76128_c(this.field_70163_u) + k1;
              if (W_WorldFunc.isEqualBlock(this.field_70170_p, i1, l1, j1, W_Block.getSnowLayer())) {
                this.field_70170_p.func_175698_g(new BlockPos(i1, l1, j1));
              } else if (W_WorldFunc.isEqualBlock(this.field_70170_p, i1, l1, j1, W_Blocks.field_150392_bi)) {
                W_WorldFunc.destroyBlock(this.field_70170_p, i1, l1, j1, true);
              } 
            } 
          }  
      } 
    } 
  }
  
  protected void func_70014_b(NBTTagCompound par1NBTTagCompound) {
    super.func_70014_b(par1NBTTagCompound);
  }
  
  protected void func_70037_a(NBTTagCompound par1NBTTagCompound) {
    super.func_70037_a(par1NBTTagCompound);
  }
  
  @SideOnly(Side.CLIENT)
  public float getShadowSize() {
    return 2.0F;
  }
  
  public boolean func_184230_a(EntityPlayer player, EnumHand hand) {
    if (player != null)
      displayInventory(player); 
    return true;
  }
  
  public void setDamageTaken(int par1) {
    this.field_70180_af.func_187227_b(DAMAGE_TAKEN, Integer.valueOf(par1));
  }
  
  public int getDamageTaken() {
    return ((Integer)this.field_70180_af.func_187225_a(DAMAGE_TAKEN)).intValue();
  }
  
  public void setTimeSinceHit(int par1) {
    this.field_70180_af.func_187227_b(TIME_SINCE_HIT, Integer.valueOf(par1));
  }
  
  public int getTimeSinceHit() {
    return ((Integer)this.field_70180_af.func_187225_a(TIME_SINCE_HIT)).intValue();
  }
  
  public void setForwardDirection(int par1) {
    this.field_70180_af.func_187227_b(FORWARD_DIR, Integer.valueOf(par1));
  }
  
  public int getForwardDirection() {
    return ((Integer)this.field_70180_af.func_187225_a(FORWARD_DIR)).intValue();
  }
  
  public boolean canRideAircraft(MCH_EntityAircraft ac, int seatID, MCH_SeatRackInfo info) {
    for (String s : info.names) {
      if (s.equalsIgnoreCase("container"))
        return (ac.func_184187_bx() == null && func_184187_bx() == null); 
    } 
    return false;
  }
  
  public boolean isSkipNormalRender() {
    return func_184187_bx() instanceof mcheli.aircraft.MCH_EntitySeat;
  }
  
  public ItemStack getPickedResult(RayTraceResult target) {
    return new ItemStack((Item)MCH_MOD.itemContainer);
  }
}
