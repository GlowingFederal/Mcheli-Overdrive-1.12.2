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
    addSlotToContainer(new Slot(iv, 0, 10, 30));
    addSlotToContainer(new Slot(iv, 1, 10, 48));
    addSlotToContainer(new Slot(iv, 2, 10, 66));
    int num = this.aircraft.getNumEjectionSeat();
    for (int i = 0; i < num; i++)
      addSlotToContainer(new Slot(iv, 3 + i, 10 + 18 * i, 105)); 
    for (int y = 0; y < 3; y++) {
      for (int j = 0; j < 9; j++)
        addSlotToContainer(new Slot((IInventory)player.inventory, 9 + j + y * 9, 25 + j * 18, 135 + y * 18)); 
    } 
    for (int x = 0; x < 9; x++)
      addSlotToContainer(new Slot((IInventory)player.inventory, x, 25 + x * 18, 195)); 
  }
  
  public int getInventoryStartIndex() {
    if (this.aircraft == null)
      return 3; 
    return 3 + this.aircraft.getNumEjectionSeat();
  }
  
  public void detectAndSendChanges() {
    super.detectAndSendChanges();
  }
  
  public boolean canInteractWith(EntityPlayer player) {
    if (this.aircraft.getGuiInventory().isUsableByPlayer(player))
      return true; 
    if (this.aircraft.isUAV()) {
      MCH_EntityUavStation us = this.aircraft.getUavStation();
      if (us != null) {
        double x = us.posX + us.posUavX;
        double z = us.posZ + us.posUavZ;
        if (this.aircraft.posX < x + 10.0D && this.aircraft.posX > x - 10.0D && this.aircraft.posZ < z + 10.0D && this.aircraft.posZ > z - 10.0D)
          return true; 
      } 
    } 
    return false;
  }
  
  public ItemStack transferStackInSlot(EntityPlayer player, int slotIndex) {
    MCH_AircraftInventory iv = this.aircraft.getGuiInventory();
    Slot slot = this.inventorySlots.get(slotIndex);
    if (slot == null)
      return null; 
    ItemStack itemStack = slot.getStack();
    MCH_Lib.DbgLog(player.world, "transferStackInSlot : %d :" + itemStack, new Object[] { Integer.valueOf(slotIndex) });
    if (itemStack.isEmpty())
      return ItemStack.EMPTY; 
    if (slotIndex < getInventoryStartIndex()) {
      for (int i = getInventoryStartIndex(); i < this.inventorySlots.size(); i++) {
        Slot playerSlot = this.inventorySlots.get(i);
        if (playerSlot.getStack().isEmpty()) {
          playerSlot.putStack(itemStack);
          slot.putStack(ItemStack.EMPTY);
          return itemStack;
        } 
      } 
    } else if (itemStack.getItem() instanceof MCH_ItemFuel) {
      for (int i = 0; i < 3; i++) {
        if (iv.getFuelSlotItemStack(i).isEmpty()) {
          iv.setInventorySlotContents(0 + i, itemStack);
          slot.putStack(ItemStack.EMPTY);
          return itemStack;
        } 
      } 
    } else if (itemStack.getItem() instanceof mcheli.parachute.MCH_ItemParachute) {
      int num = this.aircraft.getNumEjectionSeat();
      for (int i = 0; i < num; i++) {
        if (iv.getParachuteSlotItemStack(i).isEmpty()) {
          iv.setInventorySlotContents(3 + i, itemStack);
          slot.putStack(ItemStack.EMPTY);
          return itemStack;
        } 
      } 
    } 
    return ItemStack.EMPTY;
  }
  
  public void onContainerClosed(EntityPlayer player) {
    super.onContainerClosed(player);
    if (!player.world.isRemote) {
      MCH_AircraftInventory iv = this.aircraft.getGuiInventory();
      int i;
      for (i = 0; i < 3; i++) {
        ItemStack is = iv.getFuelSlotItemStack(i);
        if (!is.isEmpty() && !(is.getItem() instanceof MCH_ItemFuel))
          dropPlayerItem(player, 0 + i); 
      } 
      for (i = 0; i < 2; i++) {
        ItemStack is = iv.getParachuteSlotItemStack(i);
        if (!is.isEmpty() && !(is.getItem() instanceof mcheli.parachute.MCH_ItemParachute))
          dropPlayerItem(player, 3 + i); 
      } 
    } 
  }
  
  public void dropPlayerItem(EntityPlayer player, int slotID) {
    if (!player.world.isRemote) {
      ItemStack itemstack = this.aircraft.getGuiInventory().removeStackFromSlot(slotID);
      if (!itemstack.isEmpty())
        player.dropItem(itemstack, false); 
    } 
  }
}
