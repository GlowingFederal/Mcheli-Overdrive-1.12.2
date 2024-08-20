package mcheli.uav;

import java.util.List;
import mcheli.MCH_Lib;
import mcheli.wrapper.W_Item;
import mcheli.wrapper.W_MovingObjectPosition;
import mcheli.wrapper.W_WorldFunc;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

public class MCH_ItemUavStation extends W_Item {
  public static int UAV_STATION_KIND_NUM = 2;
  
  public final int UavStationKind;
  
  public MCH_ItemUavStation(int par1, int kind) {
    super(par1);
    this.maxStackSize = 1;
    this.UavStationKind = kind;
  }
  
  public MCH_EntityUavStation createUavStation(World world, double x, double y, double z, int kind) {
    MCH_EntityUavStation uavst = new MCH_EntityUavStation(world);
    uavst.setPosition(x, y, z);
    uavst.prevPosX = x;
    uavst.prevPosY = y;
    uavst.prevPosZ = z;
    uavst.setKind(kind);
    return uavst;
  }
  
  public ActionResult<ItemStack> onItemRightClick(World worldIn, EntityPlayer playerIn, EnumHand handIn) {
    ItemStack itemstack = playerIn.getHeldItem(handIn);
    float f = 1.0F;
    float f1 = playerIn.prevRotationPitch + (playerIn.rotationPitch - playerIn.prevRotationPitch) * f;
    float f2 = playerIn.prevRotationYaw + (playerIn.rotationYaw - playerIn.prevRotationYaw) * f;
    double d0 = playerIn.prevPosX + (playerIn.posX - playerIn.prevPosX) * f;
    double d1 = playerIn.prevPosY + (playerIn.posY - playerIn.prevPosY) * f + 1.62D;
    double d2 = playerIn.prevPosZ + (playerIn.posZ - playerIn.prevPosZ) * f;
    Vec3d vec3 = W_WorldFunc.getWorldVec3(worldIn, d0, d1, d2);
    float f3 = MathHelper.cos(-f2 * 0.017453292F - 3.1415927F);
    float f4 = MathHelper.sin(-f2 * 0.017453292F - 3.1415927F);
    float f5 = -MathHelper.cos(-f1 * 0.017453292F);
    float f6 = MathHelper.sin(-f1 * 0.017453292F);
    float f7 = f4 * f5;
    float f8 = f3 * f5;
    double d3 = 5.0D;
    Vec3d vec31 = vec3.addVector(f7 * d3, f6 * d3, f8 * d3);
    RayTraceResult movingobjectposition = W_WorldFunc.clip(worldIn, vec3, vec31, true);
    if (movingobjectposition == null)
      return ActionResult.newResult(EnumActionResult.PASS, itemstack); 
    Vec3d vec32 = playerIn.getLook(f);
    boolean flag = false;
    float f9 = 1.0F;
    List<Entity> list = worldIn.getEntitiesWithinAABBExcludingEntity((Entity)playerIn, playerIn
        .getEntityBoundingBox().expand(vec32.x * d3, vec32.y * d3, vec32.z * d3).grow(f9, f9, f9));
    int i;
    for (i = 0; i < list.size(); i++) {
      Entity entity = list.get(i);
      if (entity.canBeCollidedWith()) {
        float f10 = entity.getCollisionBorderSize();
        AxisAlignedBB axisalignedbb = entity.getEntityBoundingBox().grow(f10, f10, f10);
        if (axisalignedbb.contains(vec3))
          flag = true; 
      } 
    } 
    if (flag)
      return ActionResult.newResult(EnumActionResult.FAIL, itemstack); 
    if (W_MovingObjectPosition.isHitTypeTile(movingobjectposition)) {
      i = movingobjectposition.getBlockPos().getX();
      int j = movingobjectposition.getBlockPos().getY();
      int k = movingobjectposition.getBlockPos().getZ();
      MCH_EntityUavStation entityUavSt = createUavStation(worldIn, (i + 0.5F), (j + 1.0F), (k + 0.5F), this.UavStationKind);
      int rot = (int)(MCH_Lib.getRotate360(playerIn.rotationYaw) + 45.0D);
      entityUavSt.rotationYaw = (rot / 90 * 90 - 180);
      entityUavSt.initUavPostion();
      if (!worldIn.getCollisionBoxes((Entity)entityUavSt, entityUavSt.getEntityBoundingBox().grow(-0.1D, -0.1D, -0.1D)).isEmpty())
        return ActionResult.newResult(EnumActionResult.FAIL, itemstack); 
      if (!worldIn.isRemote)
        worldIn.spawnEntity((Entity)entityUavSt); 
      if (!playerIn.capabilities.isCreativeMode)
        itemstack.shrink(1); 
    } 
    return ActionResult.newResult(EnumActionResult.SUCCESS, itemstack);
  }
}
