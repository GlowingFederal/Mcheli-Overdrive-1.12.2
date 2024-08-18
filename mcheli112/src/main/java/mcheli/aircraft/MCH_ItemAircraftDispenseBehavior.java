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
  public ItemStack func_82487_b(IBlockSource bs, ItemStack itemStack) {
    EnumFacing enumfacing = (EnumFacing)bs.func_189992_e().func_177229_b((IProperty)BlockDispenser.field_176441_a);
    double x = bs.func_82615_a() + enumfacing.func_82601_c() * 2.0D;
    double y = bs.func_82617_b() + enumfacing.func_96559_d() * 2.0D;
    double z = bs.func_82616_c() + enumfacing.func_82599_e() * 2.0D;
    if (itemStack.func_77973_b() instanceof MCH_ItemAircraft) {
      MCH_EntityAircraft ac = ((MCH_ItemAircraft)itemStack.func_77973_b()).onTileClick(itemStack, bs.func_82618_k(), 0.0F, (int)x, (int)y, (int)z);
      if (ac != null && ac.getAcInfo() != null && !(ac.getAcInfo()).creativeOnly)
        if (!ac.isUAV()) {
          if (!(bs.func_82618_k()).field_72995_K) {
            ac.getAcDataFromItem(itemStack);
            bs.func_82618_k().func_72838_d((Entity)ac);
          } 
          itemStack.func_77979_a(1);
          MCH_Lib.DbgLog(bs.func_82618_k(), "dispenseStack:x=%.1f,y=%.1f,z=%.1f;dir=%s:item=" + itemStack
              .func_82833_r(), new Object[] { Double.valueOf(x), Double.valueOf(y), Double.valueOf(z), enumfacing.toString() });
        }  
    } 
    return itemStack;
  }
}
