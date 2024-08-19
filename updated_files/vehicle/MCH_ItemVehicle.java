package mcheli.vehicle;

import javax.annotation.Nullable;
import mcheli.MCH_Lib;
import mcheli.aircraft.MCH_AircraftInfo;
import mcheli.aircraft.MCH_EntityAircraft;
import mcheli.aircraft.MCH_ItemAircraft;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class MCH_ItemVehicle extends MCH_ItemAircraft {
  public MCH_ItemVehicle(int par1) {
    super(par1);
    this.maxStackSize = 1;
  }
  
  @Nullable
  public MCH_AircraftInfo getAircraftInfo() {
    return MCH_VehicleInfoManager.getFromItem((Item)this);
  }
  
  @Nullable
  public MCH_EntityVehicle createAircraft(World world, double x, double y, double z, ItemStack item) {
    MCH_VehicleInfo info = MCH_VehicleInfoManager.getFromItem((Item)this);
    if (info == null) {
      MCH_Lib.Log(world, "##### MCH_ItemVehicle Vehicle info null %s", new Object[] { getUnlocalizedName() });
      return null;
    } 
    MCH_EntityVehicle vehicle = new MCH_EntityVehicle(world);
    vehicle.setPosition(x, y, z);
    vehicle.prevPosX = x;
    vehicle.prevPosY = y;
    vehicle.prevPosZ = z;
    vehicle.camera.setPosition(x, y, z);
    vehicle.setTypeName(info.name);
    if (!world.isRemote)
      vehicle.setTextureName(info.getTextureName()); 
    return vehicle;
  }
}
