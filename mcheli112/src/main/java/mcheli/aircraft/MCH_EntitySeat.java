package mcheli.aircraft;

import java.util.List;
import javax.annotation.Nullable;
import mcheli.MCH_Lib;
import mcheli.__helper.entity.IEntitySinglePassenger;
import mcheli.wrapper.W_Entity;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class MCH_EntitySeat extends W_Entity implements IEntitySinglePassenger {
  public String parentUniqueID;
  
  private MCH_EntityAircraft parent;
  
  public int seatID;
  
  public int parentSearchCount;
  
  protected Entity lastRiddenByEntity;
  
  public static final float BB_SIZE = 1.0F;
  
  public MCH_EntitySeat(World world) {
    super(world);
    func_70105_a(1.0F, 1.0F);
    this.field_70159_w = 0.0D;
    this.field_70181_x = 0.0D;
    this.field_70179_y = 0.0D;
    this.seatID = -1;
    setParent((MCH_EntityAircraft)null);
    this.parentSearchCount = 0;
    this.lastRiddenByEntity = null;
    this.field_70158_ak = true;
    this.field_70178_ae = true;
  }
  
  public MCH_EntitySeat(World world, double x, double y, double z) {
    this(world);
    func_70107_b(x, y + 1.0D, z);
    this.field_70169_q = x;
    this.field_70167_r = y + 1.0D;
    this.field_70166_s = z;
  }
  
  protected boolean func_70041_e_() {
    return false;
  }
  
  public AxisAlignedBB func_70114_g(Entity par1Entity) {
    return par1Entity.func_174813_aQ();
  }
  
  public AxisAlignedBB func_70046_E() {
    return func_174813_aQ();
  }
  
  public boolean func_70104_M() {
    return false;
  }
  
  public double func_70042_X() {
    return -0.3D;
  }
  
  public boolean func_70097_a(DamageSource par1DamageSource, float par2) {
    if (getParent() != null)
      return getParent().func_70097_a(par1DamageSource, par2); 
    return false;
  }
  
  public boolean func_70067_L() {
    return !this.field_70128_L;
  }
  
  @SideOnly(Side.CLIENT)
  public void func_180426_a(double x, double y, double z, float yaw, float pitch, int posRotationIncrements, boolean teleport) {}
  
  public void func_70106_y() {
    super.func_70106_y();
  }
  
  public void func_70071_h_() {
    super.func_70071_h_();
    this.field_70143_R = 0.0F;
    Entity riddenByEntity = getRiddenByEntity();
    if (riddenByEntity != null)
      riddenByEntity.field_70143_R = 0.0F; 
    if (this.lastRiddenByEntity == null && riddenByEntity != null) {
      if (getParent() != null) {
        MCH_Lib.DbgLog(this.field_70170_p, "MCH_EntitySeat.onUpdate:SeatID=%d", new Object[] { Integer.valueOf(this.seatID), riddenByEntity.toString() });
        getParent().onMountPlayerSeat(this, riddenByEntity);
      } 
    } else if (this.lastRiddenByEntity != null && riddenByEntity == null) {
      if (getParent() != null) {
        MCH_Lib.DbgLog(this.field_70170_p, "MCH_EntitySeat.onUpdate:SeatID=%d", new Object[] { Integer.valueOf(this.seatID), this.lastRiddenByEntity.toString() });
        getParent().onUnmountPlayerSeat(this, this.lastRiddenByEntity);
      } 
    } 
    if (this.field_70170_p.field_72995_K) {
      onUpdate_Client();
    } else {
      onUpdate_Server();
    } 
    this.lastRiddenByEntity = getRiddenByEntity();
  }
  
  private void onUpdate_Client() {
    checkDetachmentAndDelete();
  }
  
  private void onUpdate_Server() {
    checkDetachmentAndDelete();
    Entity riddenByEntity = getRiddenByEntity();
    if (riddenByEntity == null || riddenByEntity.field_70128_L);
  }
  
  public void func_184232_k(Entity passenger) {
    updatePosition(passenger);
  }
  
  public void updatePosition(@Nullable Entity ridEnt) {
    if (ridEnt != null) {
      ridEnt.func_70107_b(this.field_70165_t, this.field_70163_u, this.field_70161_v);
      ridEnt.field_70159_w = ridEnt.field_70181_x = ridEnt.field_70179_y = 0.0D;
    } 
  }
  
  public void updateRotation(@Nullable Entity ridEnt, float yaw, float pitch) {
    if (ridEnt != null) {
      ridEnt.field_70177_z = yaw;
      ridEnt.field_70125_A = pitch;
    } 
  }
  
  protected void checkDetachmentAndDelete() {
    if (!this.field_70128_L && (this.seatID < 0 || getParent() == null || (getParent()).field_70128_L)) {
      if (getParent() != null && (getParent()).field_70128_L)
        this.parentSearchCount = 100000000; 
      if (this.parentSearchCount >= 1200) {
        func_70106_y();
        if (!this.field_70170_p.field_72995_K) {
          Entity riddenByEntity = getRiddenByEntity();
          if (riddenByEntity != null)
            riddenByEntity.func_184210_p(); 
        } 
        setParent((MCH_EntityAircraft)null);
        MCH_Lib.DbgLog(this.field_70170_p, "[Error]座席エンティティは本体が見つからないため削除 seat=%d, parentUniqueID=%s", new Object[] { Integer.valueOf(this.seatID), this.parentUniqueID });
      } else {
        this.parentSearchCount++;
      } 
    } else {
      this.parentSearchCount = 0;
    } 
  }
  
  protected void func_70014_b(NBTTagCompound par1NBTTagCompound) {
    par1NBTTagCompound.func_74768_a("SeatID", this.seatID);
    par1NBTTagCompound.func_74778_a("ParentUniqueID", this.parentUniqueID);
  }
  
  protected void func_70037_a(NBTTagCompound par1NBTTagCompound) {
    this.seatID = par1NBTTagCompound.func_74762_e("SeatID");
    this.parentUniqueID = par1NBTTagCompound.func_74779_i("ParentUniqueID");
  }
  
  @SideOnly(Side.CLIENT)
  public float getShadowSize() {
    return 0.0F;
  }
  
  public boolean canRideMob(Entity entity) {
    if (getParent() == null || this.seatID < 0)
      return false; 
    if (getParent().getSeatInfo(this.seatID + 1) instanceof MCH_SeatRackInfo)
      return false; 
    return true;
  }
  
  public boolean isGunnerMode() {
    Entity riddenByEntity = getRiddenByEntity();
    if (riddenByEntity != null)
      if (getParent() != null)
        return getParent().getIsGunnerMode(riddenByEntity);  
    return false;
  }
  
  public boolean func_184230_a(EntityPlayer player, EnumHand hand) {
    if (getParent() == null || getParent().isDestroyed())
      return false; 
    if (!getParent().checkTeam(player))
      return false; 
    ItemStack itemStack = player.func_184586_b(hand);
    if (!itemStack.func_190926_b() && itemStack.func_77973_b() instanceof mcheli.tool.MCH_ItemWrench)
      return getParent().func_184230_a(player, hand); 
    if (!itemStack.func_190926_b() && itemStack.func_77973_b() instanceof mcheli.mob.MCH_ItemSpawnGunner)
      return getParent().func_184230_a(player, hand); 
    Entity riddenByEntity = getRiddenByEntity();
    if (riddenByEntity != null)
      return false; 
    if (player.func_184187_bx() != null)
      return false; 
    if (!canRideMob((Entity)player))
      return false; 
    player.func_184220_m((Entity)this);
    return true;
  }
  
  @Nullable
  public MCH_EntityAircraft getParent() {
    return this.parent;
  }
  
  public void setParent(MCH_EntityAircraft parent) {
    this.parent = parent;
    Entity riddenByEntity = getRiddenByEntity();
    if (riddenByEntity != null) {
      MCH_Lib.DbgLog(this.field_70170_p, "MCH_EntitySeat.setParent:SeatID=%d %s : " + getParent(), new Object[] { Integer.valueOf(this.seatID), riddenByEntity.toString() });
      if (getParent() != null)
        getParent().onMountPlayerSeat(this, riddenByEntity); 
    } 
  }
  
  @Nullable
  public Entity getRiddenByEntity() {
    List<Entity> passengers = func_184188_bt();
    return passengers.isEmpty() ? null : passengers.get(0);
  }
}
