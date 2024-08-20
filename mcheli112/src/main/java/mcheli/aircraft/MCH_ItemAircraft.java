package mcheli.aircraft;

import java.util.List;
import javax.annotation.Nullable;
import mcheli.MCH_Config;
import mcheli.__helper.MCH_CriteriaTriggers;
import mcheli.wrapper.W_EntityPlayer;
import mcheli.wrapper.W_Item;
import mcheli.wrapper.W_MovingObjectPosition;
import mcheli.wrapper.W_WorldFunc;
import net.minecraft.block.Block;
import net.minecraft.block.BlockDispenser;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
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

public abstract class MCH_ItemAircraft extends W_Item {
  public MCH_ItemAircraft(int i) {
    super(i);
  }
  
  private static boolean isRegistedDispenseBehavior = false;
  
  public static void registerDispenseBehavior(Item item) {
    if (isRegistedDispenseBehavior == true)
      return; 
    BlockDispenser.DISPENSE_BEHAVIOR_REGISTRY.putObject(item, new MCH_ItemAircraftDispenseBehavior());
  }
  
  @Nullable
  public abstract MCH_AircraftInfo getAircraftInfo();
  
  @Nullable
  public abstract MCH_EntityAircraft createAircraft(World paramWorld, double paramDouble1, double paramDouble2, double paramDouble3, ItemStack paramItemStack);
  
  public MCH_EntityAircraft onTileClick(ItemStack itemStack, World world, float rotationYaw, int x, int y, int z) {
    MCH_EntityAircraft ac = createAircraft(world, (x + 0.5F), (y + 1.0F), (z + 0.5F), itemStack);
    if (ac == null)
      return null; 
    ac.initRotationYaw((((MathHelper.floor((rotationYaw * 4.0F / 360.0F) + 0.5D) & 0x3) - 1) * 90));
    if (!world.getCollisionBoxes((Entity)ac, ac.getEntityBoundingBox().expand(-0.1D, -0.1D, -0.1D)).isEmpty())
      return null; 
    return ac;
  }
  
  public String toString() {
    MCH_AircraftInfo info = getAircraftInfo();
    if (info != null)
      return super.toString() + "(" + info.getDirectoryName() + ":" + info.name + ")"; 
    return super.toString() + "(null)";
  }
  
  public ActionResult<ItemStack> onItemRightClick(World world, EntityPlayer player, EnumHand handIn) {
    ItemStack itemstack = player.getHeldItem(handIn);
    float f = 1.0F;
    float f1 = player.prevRotationPitch + (player.rotationPitch - player.prevRotationPitch) * f;
    float f2 = player.prevRotationYaw + (player.rotationYaw - player.prevRotationYaw) * f;
    double d0 = player.prevPosX + (player.posX - player.prevPosX) * f;
    double d1 = player.prevPosY + (player.posY - player.prevPosY) * f + 1.62D;
    double d2 = player.prevPosZ + (player.posZ - player.prevPosZ) * f;
    Vec3d vec3 = W_WorldFunc.getWorldVec3(world, d0, d1, d2);
    float f3 = MathHelper.cos(-f2 * 0.017453292F - 3.1415927F);
    float f4 = MathHelper.sin(-f2 * 0.017453292F - 3.1415927F);
    float f5 = -MathHelper.cos(-f1 * 0.017453292F);
    float f6 = MathHelper.sin(-f1 * 0.017453292F);
    float f7 = f4 * f5;
    float f8 = f3 * f5;
    double d3 = 5.0D;
    Vec3d vec31 = vec3.addVector(f7 * d3, f6 * d3, f8 * d3);
    RayTraceResult mop = W_WorldFunc.clip(world, vec3, vec31, true);
    if (mop == null)
      return ActionResult.newResult(EnumActionResult.PASS, itemstack); 
    Vec3d vec32 = player.getLook(f);
    boolean flag = false;
    float f9 = 1.0F;
    List<Entity> list = world.getEntitiesWithinAABBExcludingEntity((Entity)player, player
        .getEntityBoundingBox().addCoord(vec32.x * d3, vec32.y * d3, vec32.z * d3).expand(f9, f9, f9));
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
      return ActionResult.newResult(EnumActionResult.PASS, itemstack); 
    if (W_MovingObjectPosition.isHitTypeTile(mop)) {
      if (MCH_Config.PlaceableOnSpongeOnly.prmBool) {
        MCH_AircraftInfo acInfo = getAircraftInfo();
        if (acInfo != null && acInfo.isFloat && !acInfo.canMoveOnGround) {
          if (world.getBlockState(mop.getBlockPos().down()).getBlock() != Blocks.SPONGE)
            return ActionResult.newResult(EnumActionResult.FAIL, itemstack); 
          for (int x = -1; x <= 1; x++) {
            for (int z = -1; z <= 1; z++) {
              if (world.getBlockState(mop.getBlockPos().add(x, 0, z)).getBlock() != Blocks.WATER)
                return ActionResult.newResult(EnumActionResult.FAIL, itemstack); 
            } 
          } 
        } else {
          Block block = world.getBlockState(mop.getBlockPos()).getBlock();
          if (!(block instanceof net.minecraft.block.BlockSponge))
            return ActionResult.newResult(EnumActionResult.FAIL, itemstack); 
        } 
      } 
      spawnAircraft(itemstack, world, player, mop.getBlockPos());
    } 
    return ActionResult.newResult(EnumActionResult.SUCCESS, itemstack);
  }
  
  public MCH_EntityAircraft spawnAircraft(ItemStack itemStack, World world, EntityPlayer player, BlockPos blockpos) {
    MCH_EntityAircraft ac = onTileClick(itemStack, world, player.rotationYaw, blockpos.getX(), blockpos.getY(), blockpos
        .getZ());
    if (ac != null) {
      if (ac.getAcInfo() != null && (ac.getAcInfo()).creativeOnly && !player.capabilities.isCreativeMode)
        return null; 
      if (ac.isUAV()) {
        if (world.isRemote)
          if (ac.isSmallUAV()) {
            W_EntityPlayer.addChatMessage(player, "Please use the UAV station OR Portable Controller");
          } else {
            W_EntityPlayer.addChatMessage(player, "Please use the UAV station");
          }  
        ac = null;
      } else {
        if (!world.isRemote) {
          ac.getAcDataFromItem(itemStack);
          world.spawnEntityInWorld((Entity)ac);
          MCH_CriteriaTriggers.PUT_AIRCRAFT.trigger((EntityPlayerMP)player);
        } 
        if (!player.capabilities.isCreativeMode)
          itemStack.shrink(1); 
      } 
    } 
    return ac;
  }
  
  public void rideEntity(ItemStack item, Entity target, EntityPlayer player) {
    if (!MCH_Config.PlaceableOnSpongeOnly.prmBool)
      if (target instanceof net.minecraft.entity.item.EntityMinecartEmpty && target.getPassengers().isEmpty()) {
        BlockPos blockpos = new BlockPos((int)target.posX, (int)target.posY + 2, (int)target.posZ);
        MCH_EntityAircraft ac = spawnAircraft(item, player.world, player, blockpos);
        if (!player.world.isRemote && ac != null)
          ac.startRiding(target); 
      }  
  }
}
