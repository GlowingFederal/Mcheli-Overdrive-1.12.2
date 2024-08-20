package mcheli.aircraft;

import mcheli.MCH_Lib;
import net.minecraft.block.BlockDispenser;
import net.minecraft.block.properties.IProperty;
import net.minecraft.dispenser.BehaviorDefaultDispenseItem;
import net.minecraft.dispenser.IBlockSource;
import net.minecraft.entity.Entity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;

public class MCH_ItemAircraftDispenseBehavior extends BehaviorDefaultDispenseItem {
  public ItemStack dispenseStack(IBlockSource bs, ItemStack itemStack) {
    EnumFacing enumfacing = (EnumFacing)bs.getBlockState().getValue((IProperty)BlockDispenser.FACING);
    double x = bs.getX() + enumfacing.getFrontOffsetX() * 2.0D;
    double y = bs.getY() + enumfacing.getFrontOffsetY() * 2.0D;
    double z = bs.getZ() + enumfacing.getFrontOffsetZ() * 2.0D;
    if (itemStack.getItem() instanceof MCH_ItemAircraft) {
      MCH_EntityAircraft ac = ((MCH_ItemAircraft)itemStack.getItem()).onTileClick(itemStack, bs.getWorld(), 0.0F, (int)x, (int)y, (int)z);
      if (ac != null && ac.getAcInfo() != null && !(ac.getAcInfo()).creativeOnly)
        if (!ac.isUAV()) {
          if (!(bs.getWorld()).isRemote) {
            ac.getAcDataFromItem(itemStack);
            bs.getWorld().spawnEntity((Entity)ac);
          } 
          itemStack.splitStack(1);
          MCH_Lib.DbgLog(bs.getWorld(), "dispenseStack:x=%.1f,y=%.1f,z=%.1f;dir=%s:item=" + itemStack
              .getDisplayName(), new Object[] { Double.valueOf(x), Double.valueOf(y), Double.valueOf(z), enumfacing.toString() });
        }  
    } 
    return itemStack;
  }
}
