package mcheli.gltd;

import java.util.List;
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
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

public class MCH_ItemGLTD extends W_Item {
  public MCH_ItemGLTD(int par1) {
    super(par1);
    this.maxStackSize = 1;
  }
  
  public ActionResult<ItemStack> onItemRightClick(World par2World, EntityPlayer par3EntityPlayer, EnumHand handIn) {
    ItemStack itemstack = par3EntityPlayer.getHeldItem(handIn);
    float f = 1.0F;
    float f1 = par3EntityPlayer.prevRotationPitch + (par3EntityPlayer.rotationPitch - par3EntityPlayer.prevRotationPitch) * f;
    float f2 = par3EntityPlayer.prevRotationYaw + (par3EntityPlayer.rotationYaw - par3EntityPlayer.prevRotationYaw) * f;
    double d0 = par3EntityPlayer.prevPosX + (par3EntityPlayer.posX - par3EntityPlayer.prevPosX) * f;
    double d1 = par3EntityPlayer.prevPosY + (par3EntityPlayer.posY - par3EntityPlayer.prevPosY) * f + par3EntityPlayer.getEyeHeight();
    double d2 = par3EntityPlayer.prevPosZ + (par3EntityPlayer.posZ - par3EntityPlayer.prevPosZ) * f;
    Vec3d vec3 = W_WorldFunc.getWorldVec3(par2World, d0, d1, d2);
    float f3 = MathHelper.cos(-f2 * 0.017453292F - 3.1415927F);
    float f4 = MathHelper.sin(-f2 * 0.017453292F - 3.1415927F);
    float f5 = -MathHelper.cos(-f1 * 0.017453292F);
    float f6 = MathHelper.sin(-f1 * 0.017453292F);
    float f7 = f4 * f5;
    float f8 = f3 * f5;
    double d3 = 5.0D;
    Vec3d vec31 = vec3.addVector(f7 * d3, f6 * d3, f8 * d3);
    RayTraceResult movingobjectposition = W_WorldFunc.clip(par2World, vec3, vec31, true);
    if (movingobjectposition == null)
      return ActionResult.newResult(EnumActionResult.PASS, itemstack); 
    Vec3d vec32 = par3EntityPlayer.getLook(f);
    boolean flag = false;
    float f9 = 1.0F;
    List<Entity> list = par2World.getEntitiesWithinAABBExcludingEntity((Entity)par3EntityPlayer, par3EntityPlayer
        .getEntityBoundingBox().addCoord(vec32.xCoord * d3, vec32.yCoord * d3, vec32.zCoord * d3).expand(f9, f9, f9));
    for (int i = 0; i < list.size(); i++) {
      Entity entity = list.get(i);
      if (entity.canBeCollidedWith()) {
        float f10 = entity.getCollisionBorderSize();
        AxisAlignedBB axisalignedbb = entity.getEntityBoundingBox().expand(f10, f10, f10);
        if (axisalignedbb.isVecInside(vec3))
          flag = true; 
      } 
    } 
    if (flag)
      return ActionResult.newResult(EnumActionResult.FAIL, itemstack); 
    if (W_MovingObjectPosition.isHitTypeTile(movingobjectposition)) {
      BlockPos blockpos = movingobjectposition.getBlockPos();
      int m = blockpos.getX();
      int j = blockpos.getY();
      int k = blockpos.getZ();
      MCH_EntityGLTD entityboat = new MCH_EntityGLTD(par2World, (m + 0.5F), (j + 1.0F), (k + 0.5F));
      entityboat.rotationYaw = par3EntityPlayer.rotationYaw;
      if (!par2World.getCollisionBoxes((Entity)entityboat, entityboat.getEntityBoundingBox().expand(-0.1D, -0.1D, -0.1D)).isEmpty())
        return ActionResult.newResult(EnumActionResult.FAIL, itemstack); 
      if (!par2World.isRemote)
        par2World.spawnEntityInWorld((Entity)entityboat); 
      if (!par3EntityPlayer.capabilities.isCreativeMode)
        itemstack.func_190918_g(1); 
    } 
    return ActionResult.newResult(EnumActionResult.SUCCESS, itemstack);
  }
}
