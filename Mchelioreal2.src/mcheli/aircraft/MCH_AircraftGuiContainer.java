package mcheli.aircraft;

import mcheli.MCH_Lib;
import mcheli.uav.MCH_EntityUavStation;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

public class MCH_AircraftGuiContainer extends Container {
  public final EntityPlayer player;
  
  public final MCH_EntityAircraft aircraft;
  
  public MCH_AircraftGuiContainer(EntityPlayer player, MCH_EntityAircraft ac) {
    this.player = player;
    this.aircraft = ac;
    MCH_AircraftInventory iv = this.aircraft.getGuiInventory();
    func_75146_a(new Slot(iv, 0, 10, 30));
    func_75146_a(new Slot(iv, 1, 10, 48));
    func_75146_a(new Slot(iv, 2, 10, 66));
    int num = this.aircraft.getNumEjectionSeat();
    for (int i = 0; i < num; i++)
      func_75146_a(new Slot(iv, 3 + i, 10 + 18 * i, 105)); 
    for (int y = 0; y < 3; y++) {
      for (int j = 0; j < 9; j++)
        func_75146_a(new Slot((IInventory)player.field_71071_by, 9 + j + y * 9, 25 + j * 18, 135 + y * 18)); 
    } 
    for (int x = 0; x < 9; x++)
      func_75146_a(new Slot((IInventory)player.field_71071_by, x, 25 + x * 18, 195)); 
  }
  
  public int getInventoryStartIndex() {
    if (this.aircraft == null)
      return 3; 
    return 3 + this.aircraft.getNumEjectionSeat();
  }
  
  public void func_75142_b() {
    super.func_75142_b();
  }
  
  public boolean func_75145_c(EntityPlayer player) {
    if (this.aircraft.getGuiInventory().func_70300_a(player))
      return true; 
    if (this.aircraft.isUAV()) {
      MCH_EntityUavStation us = this.aircraft.getUavStation();
      if (us != null) {
        double x = us.field_70165_t + us.posUavX;
        double z = us.field_70161_v + us.posUavZ;
        if (this.aircraft.field_70165_t < x + 10.0D && this.aircraft.field_70165_t > x - 10.0D && this.aircraft.field_70161_v < z + 10.0D && this.aircraft.field_70161_v > z - 10.0D)
          return true; 
      } 
    } 
    return false;
  }
  
  public ItemStack func_82846_b(EntityPlayer player, int slotIndex) {
    MCH_AircraftInventory iv = this.aircraft.getGuiInventory();
    Slot slot = this.field_75151_b.get(slotIndex);
    if (slot == null)
      return null; 
    ItemStack itemStack = slot.func_75211_c();
    MCH_Lib.DbgLog(player.field_70170_p, "transferStackInSlot : %d :" + itemStack, new Object[] { Integer.valueOf(slotIndex) });
    if (itemStack.func_190926_b())
      return ItemStack.field_190927_a; 
    if (slotIndex < getInventoryStartIndex()) {
      for (int i = getInventoryStartIndex(); i < this.field_75151_b.size(); i++) {
        Slot playerSlot = this.field_75151_b.get(i);
        if (playerSlot.func_75211_c().func_190926_b()) {
          playerSlot.func_75215_d(itemStack);
          slot.func_75215_d(ItemStack.field_190927_a);
          return itemStack;
        } 
      } 
    } else if (itemStack.func_77973_b() instanceof MCH_ItemFuel) {
      for (int i = 0; i < 3; i++) {
        if (iv.getFuelSlotItemStack(i).func_190926_b()) {
          iv.func_70299_a(0 + i, itemStack);
          slot.func_75215_d(ItemStack.field_190927_a);
          return itemStack;
        } 
      } 
    } else if (itemStack.func_77973_b() instanceof mcheli.parachute.MCH_ItemParachute) {
      int num = this.aircraft.getNumEjectionSeat();
      for (int i = 0; i < num; i++) {
        if (iv.getParachuteSlotItemStack(i).func_190926_b()) {
          iv.func_70299_a(3 + i, itemStack);
          slot.func_75215_d(ItemStack.field_190927_a);
          return itemStack;
        } 
      } 
    } 
    return ItemStack.field_190927_a;
  }
  
  public void func_75134_a(EntityPlayer player) {
    super.func_75134_a(player);
    if (!player.field_70170_p.field_72995_K) {
      MCH_AircraftInventory iv = this.aircraft.getGuiInventory();
      int i;
      for (i = 0; i < 3; i++) {
        ItemStack is = iv.getFuelSlotItemStack(i);
        if (!is.func_190926_b() && !(is.func_77973_b() instanceof MCH_ItemFuel))
          dropPlayerItem(player, 0 + i); 
      } 
      for (i = 0; i < 2; i++) {
        ItemStack is = iv.getParachuteSlotItemStack(i);
        if (!is.func_190926_b() && !(is.func_77973_b() instanceof mcheli.parachute.MCH_ItemParachute))
          dropPlayerItem(player, 3 + i); 
      } 
    } 
  }
  
  public void dropPlayerItem(EntityPlayer player, int slotID) {
    if (!player.field_70170_p.field_72995_K) {
      ItemStack itemstack = this.aircraft.getGuiInventory().func_70304_b(slotID);
      if (!itemstack.func_190926_b())
        player.func_71019_a(itemstack, false); 
    } 
  }
}
