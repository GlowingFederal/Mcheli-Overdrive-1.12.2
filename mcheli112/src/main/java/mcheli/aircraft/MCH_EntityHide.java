package mcheli.aircraft;

import java.util.ArrayList;
import java.util.List;
import javax.annotation.Nullable;
import mcheli.__helper.entity.IEntitySinglePassenger;
import mcheli.wrapper.W_Entity;
import mcheli.wrapper.W_Lib;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.crash.CrashReport;
import net.minecraft.crash.CrashReportCategory;
import net.minecraft.entity.Entity;
import net.minecraft.entity.MoverType;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumHand;
import net.minecraft.util.ReportedException;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraft.world.border.WorldBorder;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class MCH_EntityHide extends W_Entity implements IEntitySinglePassenger {
  private static final DataParameter<Integer> ROPE_INDEX = EntityDataManager.func_187226_a(MCH_EntityHide.class, DataSerializers.field_187192_b);
  
  private static final DataParameter<Integer> AC_ID = EntityDataManager.func_187226_a(MCH_EntityHide.class, DataSerializers.field_187192_b);
  
  private MCH_EntityAircraft ac;
  
  private Entity user;
  
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
  
  public MCH_EntityHide(World par1World) {
    super(par1World);
    func_70105_a(1.0F, 1.0F);
    this.field_70156_m = true;
    this.user = null;
    this.field_70159_w = this.field_70181_x = this.field_70179_y = 0.0D;
  }
  
  public MCH_EntityHide(World par1World, double x, double y, double z) {
    this(par1World);
    this.field_70165_t = x;
    this.field_70163_u = y;
    this.field_70161_v = z;
  }
  
  protected void func_70088_a() {
    super.func_70088_a();
    createRopeIndex(-1);
    this.field_70180_af.func_187214_a(AC_ID, new Integer(0));
  }
  
  public void setParent(MCH_EntityAircraft ac, Entity user, int ropeIdx) {
    this.ac = ac;
    setRopeIndex(ropeIdx);
    this.user = user;
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
    return true;
  }
  
  public double func_70042_X() {
    return this.field_70131_O * 0.0D - 0.3D;
  }
  
  public boolean func_70097_a(DamageSource par1DamageSource, float par2) {
    return false;
  }
  
  public boolean func_70067_L() {
    return !this.field_70128_L;
  }
  
  protected void func_70014_b(NBTTagCompound nbt) {}
  
  protected void func_70037_a(NBTTagCompound nbt) {}
  
  @SideOnly(Side.CLIENT)
  public float getShadowSize() {
    return 0.0F;
  }
  
  public boolean func_184230_a(EntityPlayer player, EnumHand hand) {
    return false;
  }
  
  public void createRopeIndex(int defaultValue) {
    this.field_70180_af.func_187214_a(ROPE_INDEX, new Integer(defaultValue));
  }
  
  public int getRopeIndex() {
    return ((Integer)this.field_70180_af.func_187225_a(ROPE_INDEX)).intValue();
  }
  
  public void setRopeIndex(int value) {
    this.field_70180_af.func_187227_b(ROPE_INDEX, new Integer(value));
  }
  
  @SideOnly(Side.CLIENT)
  public void func_180426_a(double par1, double par3, double par5, float par7, float par8, int par9, boolean teleport) {
    this.paraPosRotInc = par9 + 10;
    this.paraX = par1;
    this.paraY = par3;
    this.paraZ = par5;
    this.paraYaw = par7;
    this.paraPitch = par8;
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
    if (this.user != null && !this.field_70170_p.field_72995_K) {
      if (this.ac != null)
        this.field_70180_af.func_187227_b(AC_ID, new Integer(this.ac.func_145782_y())); 
      this.user.func_184205_a((Entity)this, true);
      this.user = null;
    } 
    if (this.ac == null && this.field_70170_p.field_72995_K) {
      int id = ((Integer)this.field_70180_af.func_187225_a(AC_ID)).intValue();
      if (id > 0) {
        Entity entity = this.field_70170_p.func_73045_a(id);
        if (entity instanceof MCH_EntityAircraft)
          this.ac = (MCH_EntityAircraft)entity; 
      } 
    } 
    this.field_70169_q = this.field_70165_t;
    this.field_70167_r = this.field_70163_u;
    this.field_70166_s = this.field_70161_v;
    this.field_70143_R = 0.0F;
    Entity riddenByEntity = getRiddenByEntity();
    if (riddenByEntity != null)
      riddenByEntity.field_70143_R = 0.0F; 
    if (this.ac != null) {
      if (!this.ac.isRepelling())
        func_70106_y(); 
      int id = getRopeIndex();
      if (id >= 0) {
        Vec3d v = this.ac.getRopePos(id);
        this.field_70165_t = v.field_72450_a;
        this.field_70161_v = v.field_72449_c;
      } 
    } 
    func_70107_b(this.field_70165_t, this.field_70163_u, this.field_70161_v);
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
      Entity riddenByEntity = getRiddenByEntity();
      if (riddenByEntity != null)
        func_70101_b(riddenByEntity.field_70126_B, this.field_70125_A); 
    } else {
      func_70107_b(this.field_70165_t + this.field_70159_w, this.field_70163_u + this.field_70181_x, this.field_70161_v + this.field_70179_y);
      this.field_70159_w *= 0.99D;
      this.field_70181_x *= 0.95D;
      this.field_70179_y *= 0.99D;
    } 
  }
  
  public void onUpdateServer() {
    this.field_70181_x -= this.field_70122_E ? 0.01D : 0.03D;
    if (this.field_70122_E) {
      onGroundAndDead();
      return;
    } 
    func_70091_d(MoverType.SELF, this.field_70159_w, this.field_70181_x, this.field_70179_y);
    this.field_70181_x *= 0.9D;
    this.field_70159_w *= 0.95D;
    this.field_70179_y *= 0.95D;
    int id = getRopeIndex();
    if (this.ac != null && id >= 0) {
      Vec3d v = this.ac.getRopePos(id);
      if (Math.abs(this.field_70163_u - v.field_72448_b) > (Math.abs(this.ac.ropesLength) + 5.0F))
        onGroundAndDead(); 
    } 
    Entity riddenByEntity = getRiddenByEntity();
    if (riddenByEntity != null && riddenByEntity.field_70128_L)
      func_70106_y(); 
  }
  
  private boolean getCollisionBoxes(@Nullable Entity entityIn, AxisAlignedBB aabb, List<AxisAlignedBB> outList) {
    int i = MathHelper.func_76128_c(aabb.field_72340_a) - 1;
    int j = MathHelper.func_76143_f(aabb.field_72336_d) + 1;
    int k = MathHelper.func_76128_c(aabb.field_72338_b) - 1;
    int l = MathHelper.func_76143_f(aabb.field_72337_e) + 1;
    int i1 = MathHelper.func_76128_c(aabb.field_72339_c) - 1;
    int j1 = MathHelper.func_76143_f(aabb.field_72334_f) + 1;
    WorldBorder worldborder = this.field_70170_p.func_175723_af();
    boolean flag = (entityIn != null && entityIn.func_174832_aS());
    boolean flag1 = (entityIn != null && this.field_70170_p.func_191503_g(entityIn));
    IBlockState iblockstate = Blocks.field_150348_b.func_176223_P();
    BlockPos.PooledMutableBlockPos blockpos$pooledmutableblockpos = BlockPos.PooledMutableBlockPos.func_185346_s();
    try {
      for (int k1 = i; k1 < j; k1++) {
        for (int l1 = i1; l1 < j1; l1++) {
          boolean flag2 = (k1 == i || k1 == j - 1);
          boolean flag3 = (l1 == i1 || l1 == j1 - 1);
          if ((!flag2 || !flag3) && this.field_70170_p
            .func_175667_e((BlockPos)blockpos$pooledmutableblockpos.func_181079_c(k1, 64, l1)))
            for (int i2 = k; i2 < l; i2++) {
              if ((!flag2 && !flag3) || i2 != l - 1) {
                IBlockState iblockstate1;
                if (entityIn != null && flag == flag1)
                  entityIn.func_174821_h(!flag1); 
                blockpos$pooledmutableblockpos.func_181079_c(k1, i2, l1);
                if (!worldborder.func_177746_a((BlockPos)blockpos$pooledmutableblockpos) && flag1) {
                  iblockstate1 = iblockstate;
                } else {
                  iblockstate1 = this.field_70170_p.func_180495_p((BlockPos)blockpos$pooledmutableblockpos);
                } 
                iblockstate1.func_185908_a(this.field_70170_p, (BlockPos)blockpos$pooledmutableblockpos, aabb, outList, entityIn, false);
              } 
            }  
        } 
      } 
    } finally {
      blockpos$pooledmutableblockpos.func_185344_t();
    } 
    return !outList.isEmpty();
  }
  
  public List<AxisAlignedBB> getCollidingBoundingBoxes(Entity par1Entity, AxisAlignedBB par2AxisAlignedBB) {
    List<AxisAlignedBB> list = new ArrayList<>();
    getCollisionBoxes(par1Entity, par2AxisAlignedBB, list);
    if (par1Entity != null) {
      List<Entity> list1 = this.field_70170_p.func_72839_b(par1Entity, par2AxisAlignedBB
          .func_186662_g(0.25D));
      for (int i = 0; i < list1.size(); i++) {
        Entity entity = list1.get(i);
        if (!W_Lib.isEntityLivingBase(entity) && !(entity instanceof MCH_EntitySeat) && !(entity instanceof MCH_EntityHitBox)) {
          AxisAlignedBB axisalignedbb = entity.func_70046_E();
          if (axisalignedbb != null && axisalignedbb.func_72326_a(par2AxisAlignedBB))
            list.add(axisalignedbb); 
          axisalignedbb = par1Entity.func_70114_g(entity);
          if (axisalignedbb != null && axisalignedbb.func_72326_a(par2AxisAlignedBB))
            list.add(axisalignedbb); 
        } 
      } 
    } 
    return list;
  }
  
  public void func_70091_d(MoverType type, double x, double y, double z) {
    this.field_70170_p.field_72984_F.func_76320_a("move");
    double d2 = x;
    double d3 = y;
    double d4 = z;
    List<AxisAlignedBB> list1 = getCollidingBoundingBoxes((Entity)this, func_174813_aQ().func_72321_a(x, y, z));
    AxisAlignedBB axisalignedbb = func_174813_aQ();
    if (y != 0.0D) {
      int k = 0;
      for (int l = list1.size(); k < l; k++)
        y = ((AxisAlignedBB)list1.get(k)).func_72323_b(func_174813_aQ(), y); 
      func_174826_a(func_174813_aQ().func_72317_d(0.0D, y, 0.0D));
    } 
    boolean flag = (this.field_70122_E || (d3 != y && d3 < 0.0D));
    if (x != 0.0D) {
      int j5 = 0;
      for (int l5 = list1.size(); j5 < l5; j5++)
        x = ((AxisAlignedBB)list1.get(j5)).func_72316_a(func_174813_aQ(), x); 
      if (x != 0.0D)
        func_174826_a(func_174813_aQ().func_72317_d(x, 0.0D, 0.0D)); 
    } 
    if (z != 0.0D) {
      int k5 = 0;
      for (int i6 = list1.size(); k5 < i6; k5++)
        z = ((AxisAlignedBB)list1.get(k5)).func_72322_c(func_174813_aQ(), z); 
      if (z != 0.0D)
        func_174826_a(func_174813_aQ().func_72317_d(0.0D, 0.0D, z)); 
    } 
    if (this.field_70138_W > 0.0F && flag && (d2 != x || d4 != z)) {
      double d14 = x;
      double d6 = y;
      double d7 = z;
      y = this.field_70138_W;
      AxisAlignedBB axisalignedbb1 = func_174813_aQ();
      func_174826_a(axisalignedbb);
      List<AxisAlignedBB> list = getCollidingBoundingBoxes((Entity)this, 
          func_174813_aQ().func_72321_a(d2, y, d4));
      AxisAlignedBB axisalignedbb2 = func_174813_aQ();
      AxisAlignedBB axisalignedbb3 = axisalignedbb2.func_72321_a(d2, 0.0D, d4);
      double d8 = y;
      for (int j1 = 0; j1 < list.size(); j1++)
        d8 = ((AxisAlignedBB)list.get(j1)).func_72323_b(axisalignedbb3, d8); 
      axisalignedbb2 = axisalignedbb2.func_72317_d(0.0D, d8, 0.0D);
      double d18 = d2;
      for (int l1 = 0; l1 < list.size(); l1++)
        d18 = ((AxisAlignedBB)list.get(l1)).func_72316_a(axisalignedbb2, d18); 
      axisalignedbb2 = axisalignedbb2.func_72317_d(d18, 0.0D, 0.0D);
      double d19 = d4;
      for (int j2 = 0; j2 < list.size(); j2++)
        d19 = ((AxisAlignedBB)list.get(j2)).func_72322_c(axisalignedbb2, d19); 
      axisalignedbb2 = axisalignedbb2.func_72317_d(0.0D, 0.0D, d19);
      AxisAlignedBB axisalignedbb4 = func_174813_aQ();
      double d20 = y;
      for (int l2 = 0; l2 < list.size(); l2++)
        d20 = ((AxisAlignedBB)list.get(l2)).func_72323_b(axisalignedbb4, d20); 
      axisalignedbb4 = axisalignedbb4.func_72317_d(0.0D, d20, 0.0D);
      double d21 = d2;
      for (int j3 = 0; j3 < list.size(); j3++)
        d21 = ((AxisAlignedBB)list.get(j3)).func_72316_a(axisalignedbb4, d21); 
      axisalignedbb4 = axisalignedbb4.func_72317_d(d21, 0.0D, 0.0D);
      double d22 = d4;
      for (int l3 = 0; l3 < list.size(); l3++)
        d22 = ((AxisAlignedBB)list.get(l3)).func_72322_c(axisalignedbb4, d22); 
      axisalignedbb4 = axisalignedbb4.func_72317_d(0.0D, 0.0D, d22);
      double d23 = d18 * d18 + d19 * d19;
      double d9 = d21 * d21 + d22 * d22;
      if (d23 > d9) {
        x = d18;
        z = d19;
        y = -d8;
        func_174826_a(axisalignedbb2);
      } else {
        x = d21;
        z = d22;
        y = -d20;
        func_174826_a(axisalignedbb4);
      } 
      for (int j4 = 0; j4 < list.size(); j4++)
        y = ((AxisAlignedBB)list.get(j4)).func_72323_b(func_174813_aQ(), y); 
      func_174826_a(func_174813_aQ().func_72317_d(0.0D, y, 0.0D));
      if (d14 * d14 + d7 * d7 >= x * x + z * z) {
        x = d14;
        y = d6;
        z = d7;
        func_174826_a(axisalignedbb1);
      } 
    } 
    this.field_70170_p.field_72984_F.func_76319_b();
    this.field_70170_p.field_72984_F.func_76320_a("rest");
    func_174829_m();
    this.field_70123_F = (d2 != x || d4 != z);
    this.field_70124_G = (d3 != y);
    this.field_70122_E = (this.field_70124_G && d3 < 0.0D);
    this.field_70132_H = (this.field_70123_F || this.field_70124_G);
    int j6 = MathHelper.func_76128_c(this.field_70165_t);
    int i1 = MathHelper.func_76128_c(this.field_70163_u - 0.20000000298023224D);
    int k6 = MathHelper.func_76128_c(this.field_70161_v);
    BlockPos blockpos = new BlockPos(j6, i1, k6);
    IBlockState iblockstate = this.field_70170_p.func_180495_p(blockpos);
    if (iblockstate.func_185904_a() == Material.field_151579_a) {
      BlockPos blockpos1 = blockpos.func_177977_b();
      IBlockState iblockstate1 = this.field_70170_p.func_180495_p(blockpos1);
      Block block1 = iblockstate1.func_177230_c();
      if (block1 instanceof net.minecraft.block.BlockFence || block1 instanceof net.minecraft.block.BlockWall || block1 instanceof net.minecraft.block.BlockFenceGate) {
        iblockstate = iblockstate1;
        blockpos = blockpos1;
      } 
    } 
    func_184231_a(y, this.field_70122_E, iblockstate, blockpos);
    if (d2 != x)
      this.field_70159_w = 0.0D; 
    if (d4 != z)
      this.field_70179_y = 0.0D; 
    Block block = iblockstate.func_177230_c();
    if (d3 != y)
      block.func_176216_a(this.field_70170_p, (Entity)this); 
    try {
      func_145775_I();
    } catch (Throwable throwable) {
      CrashReport crashreport = CrashReport.func_85055_a(throwable, "Checking entity block collision");
      CrashReportCategory crashreportcategory = crashreport.func_85058_a("Entity being checked for collision");
      func_85029_a(crashreportcategory);
      throw new ReportedException(crashreport);
    } 
    this.field_70170_p.field_72984_F.func_76319_b();
  }
  
  public void onGroundAndDead() {
    this.field_70163_u += 0.5D;
    func_184232_k(getRiddenByEntity());
    func_70106_y();
  }
  
  @Nullable
  public Entity getRiddenByEntity() {
    List<Entity> passengers = func_184188_bt();
    return passengers.isEmpty() ? null : passengers.get(0);
  }
}
