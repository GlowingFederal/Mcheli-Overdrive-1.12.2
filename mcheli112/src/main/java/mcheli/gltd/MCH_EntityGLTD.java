package mcheli.gltd;

import java.util.List;
import javax.annotation.Nullable;
import mcheli.MCH_Camera;
import mcheli.MCH_Config;
import mcheli.MCH_MOD;
import mcheli.__helper.entity.IEntityItemStackPickable;
import mcheli.__helper.entity.IEntitySinglePassenger;
import mcheli.multiplay.MCH_Multiplay;
import mcheli.weapon.MCH_WeaponCAS;
import mcheli.weapon.MCH_WeaponInfo;
import mcheli.weapon.MCH_WeaponInfoManager;
import mcheli.wrapper.W_Block;
import mcheli.wrapper.W_Entity;
import mcheli.wrapper.W_WorldFunc;
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
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class MCH_EntityGLTD extends W_Entity implements IEntitySinglePassenger, IEntityItemStackPickable {
  public static final float Y_OFFSET = 0.25F;
  
  private static final DataParameter<Integer> TIME_SINCE_HIT = EntityDataManager.func_187226_a(MCH_EntityGLTD.class, DataSerializers.field_187192_b);
  
  private static final DataParameter<Integer> FORWARD_DIR = EntityDataManager.func_187226_a(MCH_EntityGLTD.class, DataSerializers.field_187192_b);
  
  private static final DataParameter<Integer> DAMAGE_TAKEN = EntityDataManager.func_187226_a(MCH_EntityGLTD.class, DataSerializers.field_187192_b);
  
  private boolean isBoatEmpty;
  
  private double speedMultiplier;
  
  private int gltdPosRotInc;
  
  private double gltdX;
  
  private double gltdY;
  
  private double gltdZ;
  
  private double gltdYaw;
  
  private double gltdPitch;
  
  @SideOnly(Side.CLIENT)
  private double velocityX;
  
  @SideOnly(Side.CLIENT)
  private double velocityY;
  
  @SideOnly(Side.CLIENT)
  private double velocityZ;
  
  public final MCH_Camera camera;
  
  public boolean zoomDir;
  
  public final MCH_WeaponCAS weaponCAS;
  
  public int countWait;
  
  public boolean isUsedPlayer;
  
  public float renderRotaionYaw;
  
  public float renderRotaionPitch;
  
  public int retryRiddenByEntityCheck;
  
  public Entity lastRiddenByEntity;
  
  public MCH_EntityGLTD(World world) {
    super(world);
    this.isBoatEmpty = true;
    this.speedMultiplier = 0.07D;
    this.field_70156_m = true;
    func_70105_a(0.5F, 0.5F);
    this.camera = new MCH_Camera(world, (Entity)this);
    MCH_WeaponInfo wi = MCH_WeaponInfoManager.get("a10gau8");
    this.weaponCAS = new MCH_WeaponCAS(world, Vec3d.field_186680_a, 0.0F, 0.0F, "a10gau8", wi);
    this.weaponCAS.interval += (this.weaponCAS.interval > 0) ? 150 : 65386;
    this.weaponCAS.displayName = "A-10 GAU-8 Avenger";
    this.field_70158_ak = true;
    this.countWait = 0;
    this.retryRiddenByEntityCheck = 0;
    this.lastRiddenByEntity = null;
    this.isUsedPlayer = false;
    this.renderRotaionYaw = 0.0F;
    this.renderRotaionYaw = 0.0F;
    this.renderRotaionPitch = 0.0F;
    this.zoomDir = true;
    this._renderDistanceWeight = 2.0D;
  }
  
  public MCH_EntityGLTD(World par1World, double x, double y, double z) {
    this(par1World);
    func_70107_b(x, y, z);
    this.field_70159_w = 0.0D;
    this.field_70181_x = 0.0D;
    this.field_70179_y = 0.0D;
    this.field_70169_q = x;
    this.field_70167_r = y;
    this.field_70166_s = z;
    this.camera.setPosition(x, y, z);
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
    return false;
  }
  
  public double func_70042_X() {
    return this.field_70131_O * 0.0D - 0.3D;
  }
  
  public boolean func_70097_a(DamageSource ds, float damage) {
    if (func_180431_b(ds))
      return false; 
    if (!this.field_70170_p.field_72995_K && !this.field_70128_L) {
      damage = MCH_Config.applyDamageByExternal((Entity)this, ds, damage);
      if (!MCH_Multiplay.canAttackEntity(ds, (Entity)this))
        return false; 
      setForwardDirection(-getForwardDirection());
      setTimeSinceHit(10);
      setDamageTaken((int)(getDamageTaken() + damage * 100.0F));
      func_70018_K();
      boolean flag = (ds.func_76346_g() instanceof EntityPlayer && ((EntityPlayer)ds.func_76346_g()).field_71075_bZ.field_75098_d);
      if (flag || getDamageTaken() > 40.0F) {
        Entity riddenByEntity = getRiddenByEntity();
        this.camera.initCamera(0, riddenByEntity);
        if (riddenByEntity != null)
          riddenByEntity.func_184220_m((Entity)this); 
        if (!flag)
          func_145778_a((Item)MCH_MOD.itemGLTD, 1, 0.0F); 
        W_WorldFunc.MOD_playSoundEffect(this.field_70170_p, this.field_70165_t, this.field_70163_u, this.field_70161_v, "hit", 1.0F, 1.0F);
        func_70106_y();
      } 
      return true;
    } 
    return true;
  }
  
  @SideOnly(Side.CLIENT)
  public void func_70057_ab() {}
  
  public boolean func_70067_L() {
    return !this.field_70128_L;
  }
  
  @SideOnly(Side.CLIENT)
  public void setPositionAndRotation2(double par1, double par3, double par5, float par7, float par8, int par9) {
    if (this.isBoatEmpty) {
      this.gltdPosRotInc = par9 + 5;
    } else {
      double x = par1 - this.field_70165_t;
      double y = par3 - this.field_70163_u;
      double z = par5 - this.field_70161_v;
      if (x * x + y * y + z * z <= 1.0D)
        return; 
      this.gltdPosRotInc = 3;
    } 
    this.gltdX = par1;
    this.gltdY = par3;
    this.gltdZ = par5;
    this.gltdYaw = par7;
    this.gltdPitch = par8;
    this.field_70159_w = this.velocityX;
    this.field_70181_x = this.velocityY;
    this.field_70179_y = this.velocityZ;
  }
  
  @SideOnly(Side.CLIENT)
  public void func_70016_h(double x, double y, double z) {
    this.velocityX = this.field_70159_w = x;
    this.velocityY = this.field_70181_x = y;
    this.velocityZ = this.field_70179_y = z;
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
    double d3 = Math.sqrt(this.field_70159_w * this.field_70159_w + this.field_70179_y * this.field_70179_y);
    Entity riddenByEntity = getRiddenByEntity();
    if (riddenByEntity != null)
      this.camera.updateViewer(0, riddenByEntity); 
    if (this.field_70170_p.field_72995_K && this.isBoatEmpty) {
      if (this.gltdPosRotInc > 0) {
        double d4 = this.field_70165_t + (this.gltdX - this.field_70165_t) / this.gltdPosRotInc;
        double d5 = this.field_70163_u + (this.gltdY - this.field_70163_u) / this.gltdPosRotInc;
        double d11 = this.field_70161_v + (this.gltdZ - this.field_70161_v) / this.gltdPosRotInc;
        double d10 = MathHelper.func_76138_g(this.gltdYaw - this.field_70177_z);
        this.field_70177_z = (float)(this.field_70177_z + d10 / this.gltdPosRotInc);
        this.field_70125_A = (float)(this.field_70125_A + (this.gltdPitch - this.field_70125_A) / this.gltdPosRotInc);
        this.gltdPosRotInc--;
        func_70107_b(d4, d5, d11);
        func_70101_b(this.field_70177_z, this.field_70125_A);
      } else {
        double d4 = this.field_70165_t + this.field_70159_w;
        double d5 = this.field_70163_u + this.field_70181_x;
        double d11 = this.field_70161_v + this.field_70179_y;
        func_70107_b(d4, d5, d11);
        if (this.field_70122_E) {
          this.field_70159_w *= 0.5D;
          this.field_70181_x *= 0.5D;
          this.field_70179_y *= 0.5D;
        } 
        this.field_70159_w *= 0.99D;
        this.field_70181_x *= 0.95D;
        this.field_70179_y *= 0.99D;
      } 
    } else {
      this.field_70181_x -= 0.04D;
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
        this.field_70159_w *= 0.5D;
        this.field_70181_x *= 0.5D;
        this.field_70179_y *= 0.5D;
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
      if (d12 > 20.0D)
        d12 = 20.0D; 
      if (d12 < -20.0D)
        d12 = -20.0D; 
      this.field_70177_z = (float)(this.field_70177_z + d12);
      func_70101_b(this.field_70177_z, this.field_70125_A);
      if (!this.field_70170_p.field_72995_K) {
        if (MCH_Config.Collision_DestroyBlock.prmBool)
          for (int l = 0; l < 4; l++) {
            int i1 = MathHelper.func_76128_c(this.field_70165_t + ((l % 2) - 0.5D) * 0.8D);
            int j1 = MathHelper.func_76128_c(this.field_70161_v + ((l / 2) - 0.5D) * 0.8D);
            for (int k1 = 0; k1 < 2; k1++) {
              int l1 = MathHelper.func_76128_c(this.field_70163_u) + k1;
              if (W_WorldFunc.isEqualBlock(this.field_70170_p, i1, l1, j1, W_Block.getSnowLayer()))
                this.field_70170_p.func_175698_g(new BlockPos(i1, l1, j1)); 
            } 
          }  
        riddenByEntity = getRiddenByEntity();
        if (riddenByEntity != null && riddenByEntity.field_70128_L)
          riddenByEntity.func_184210_p(); 
      } 
    } 
    updateCameraPosition(false);
    if (this.countWait > 0)
      this.countWait--; 
    if (this.countWait < 0)
      this.countWait++; 
    this.weaponCAS.update(this.countWait);
    riddenByEntity = getRiddenByEntity();
    if (this.lastRiddenByEntity != null && riddenByEntity == null) {
      if (this.retryRiddenByEntityCheck < 3) {
        this.retryRiddenByEntityCheck++;
        setUnmoundPosition(this.lastRiddenByEntity);
      } else {
        unmountEntity();
      } 
    } else {
      this.retryRiddenByEntityCheck = 0;
    } 
    riddenByEntity = getRiddenByEntity();
    if (riddenByEntity != null)
      this.lastRiddenByEntity = riddenByEntity; 
  }
  
  public void setUnmoundPosition(Entity e) {
    if (e == null)
      return; 
    float yaw = this.field_70177_z;
    double d0 = Math.sin(yaw * Math.PI / 180.0D) * 1.2D;
    double d1 = -Math.cos(yaw * Math.PI / 180.0D) * 1.2D;
    e.func_70107_b(this.field_70165_t + d0, this.field_70163_u + func_70042_X() + e.func_70033_W() + 1.0D, this.field_70161_v + d1);
    e.field_70142_S = e.field_70169_q = e.field_70165_t;
    e.field_70137_T = e.field_70167_r = e.field_70163_u;
    e.field_70136_U = e.field_70166_s = e.field_70161_v;
  }
  
  public void unmountEntity() {
    this.camera.setMode(0, 0);
    this.camera.setCameraZoom(1.0F);
    if (!this.field_70170_p.field_72995_K) {
      Entity riddenByEntity = getRiddenByEntity();
      if (riddenByEntity != null) {
        if (!riddenByEntity.field_70128_L)
          riddenByEntity.func_184210_p(); 
      } else if (this.lastRiddenByEntity != null && !this.lastRiddenByEntity.field_70128_L) {
        this.camera.updateViewer(0, this.lastRiddenByEntity);
        setUnmoundPosition(this.lastRiddenByEntity);
      } 
    } 
    this.lastRiddenByEntity = null;
  }
  
  public void updateCameraPosition(boolean foreceUpdate) {
    Entity riddenByEntity = getRiddenByEntity();
    if (foreceUpdate || (riddenByEntity != null && this.camera != null)) {
      double x = -Math.sin(this.field_70177_z * Math.PI / 180.0D) * 0.6D;
      double z = Math.cos(this.field_70177_z * Math.PI / 180.0D) * 0.6D;
      this.camera.setPosition(this.field_70165_t + x, this.field_70163_u + 0.7D, this.field_70161_v + z);
    } 
  }
  
  @SideOnly(Side.CLIENT)
  public void zoomCamera(float f) {
    float z = this.camera.getCameraZoom();
    z += f;
    if (z < 1.0F)
      z = 1.0F; 
    if (z > 10.0F)
      z = 10.0F; 
    this.camera.setCameraZoom(z);
  }
  
  public void func_184232_k(Entity passenger) {
    if (func_184196_w(passenger)) {
      double x = Math.sin(this.field_70177_z * Math.PI / 180.0D) * 0.5D;
      double z = -Math.cos(this.field_70177_z * Math.PI / 180.0D) * 0.5D;
      passenger.func_70107_b(this.field_70165_t + x, this.field_70163_u + func_70042_X() + passenger.func_70033_W(), this.field_70161_v + z);
    } 
  }
  
  public void switchWeapon(int id) {}
  
  public boolean useCurrentWeapon(int option1, int option2) {
    Entity riddenByEntity = getRiddenByEntity();
    if (this.countWait == 0 && riddenByEntity != null)
      if (this.weaponCAS.shot(riddenByEntity, this.camera.posX, this.camera.posY, this.camera.posZ, option1, option2)) {
        this.countWait = this.weaponCAS.interval;
        if (this.field_70170_p.field_72995_K) {
          this.countWait += (this.countWait > 0) ? 10 : -10;
        } else {
          W_WorldFunc.MOD_playSoundEffect(this.field_70170_p, this.field_70165_t, this.field_70163_u, this.field_70161_v, "gltd", 0.5F, 1.0F);
        } 
        return true;
      }  
    return false;
  }
  
  protected void func_70014_b(NBTTagCompound par1NBTTagCompound) {}
  
  protected void func_70037_a(NBTTagCompound par1NBTTagCompound) {}
  
  @SideOnly(Side.CLIENT)
  public float getShadowSize() {
    return 0.0F;
  }
  
  public boolean func_184230_a(EntityPlayer player, EnumHand hand) {
    Entity riddenByEntity = getRiddenByEntity();
    if (riddenByEntity != null && riddenByEntity instanceof EntityPlayer && riddenByEntity != player)
      return true; 
    player.field_70177_z = MathHelper.func_76142_g(this.field_70177_z);
    player.field_70125_A = MathHelper.func_76142_g(this.field_70125_A);
    if (!this.field_70170_p.field_72995_K) {
      player.func_184220_m((Entity)this);
    } else {
      this.zoomDir = true;
      this.camera.setCameraZoom(1.0F);
      if (this.countWait > 0)
        this.countWait = -this.countWait; 
      if (this.countWait > -60)
        this.countWait = -60; 
    } 
    updateCameraPosition(true);
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
    return 0;
  }
  
  @SideOnly(Side.CLIENT)
  public void setIsBoatEmpty(boolean par1) {
    this.isBoatEmpty = par1;
  }
  
  @Nullable
  public Entity getRiddenByEntity() {
    List<Entity> passengers = func_184188_bt();
    return passengers.isEmpty() ? null : passengers.get(0);
  }
  
  public ItemStack getPickedResult(RayTraceResult target) {
    return new ItemStack((Item)MCH_MOD.itemGLTD);
  }
}
