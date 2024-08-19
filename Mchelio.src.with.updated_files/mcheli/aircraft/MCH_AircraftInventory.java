package mcheli.aircraft;

import java.util.Arrays;
import java.util.Random;
import mcheli.wrapper.W_NBTTag;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentString;

public class MCH_AircraftInventory implements IInventory {
  public final int SLOT_FUEL0 = 0;
  
  public final int SLOT_FUEL1 = 1;
  
  public final int SLOT_FUEL2 = 2;
  
  public final int SLOT_PARACHUTE0 = 3;
  
  public final int SLOT_PARACHUTE1 = 4;
  
  private ItemStack[] containerItems;
  
  final MCH_EntityAircraft aircraft;
  
  public MCH_AircraftInventory(MCH_EntityAircraft ac) {
    this.containerItems = new ItemStack[getSizeInventory()];
    Arrays.fill((Object[])this.containerItems, ItemStack.field_190927_a);
    this.aircraft = ac;
  }
  
  public ItemStack getFuelSlotItemStack(int i) {
    return getStackInSlot(0 + i);
  }
  
  public ItemStack getParachuteSlotItemStack(int i) {
    return getStackInSlot(3 + i);
  }
  
  public boolean haveParachute() {
    for (int i = 0; i < 2; i++) {
      ItemStack item = getParachuteSlotItemStack(i);
      if (!item.func_190926_b() && item.getItem() instanceof mcheli.parachute.MCH_ItemParachute)
        return true; 
    } 
    return false;
  }
  
  public void consumeParachute() {
    for (int i = 0; i < 2; i++) {
      ItemStack item = getParachuteSlotItemStack(i);
      if (!item.func_190926_b() && item.getItem() instanceof mcheli.parachute.MCH_ItemParachute) {
        setInventorySlotContents(3 + i, ItemStack.field_190927_a);
        break;
      } 
    } 
  }
  
  public int getSizeInventory() {
    return 10;
  }
  
  public boolean func_191420_l() {
    for (ItemStack itemstack : this.containerItems) {
      if (!itemstack.func_190926_b())
        return false; 
    } 
    return true;
  }
  
  public ItemStack getStackInSlot(int var1) {
    return this.containerItems[var1];
  }
  
  public void setDead() {
    Random rand = new Random();
    if (this.aircraft.dropContentsWhenDead && !this.aircraft.world.isRemote)
      for (int i = 0; i < getSizeInventory(); i++) {
        ItemStack itemstack = getStackInSlot(i);
        if (!itemstack.func_190926_b()) {
          float x = rand.nextFloat() * 0.8F + 0.1F;
          float y = rand.nextFloat() * 0.8F + 0.1F;
          float z = rand.nextFloat() * 0.8F + 0.1F;
          while (itemstack.func_190916_E() > 0) {
            int j = rand.nextInt(21) + 10;
            if (j > itemstack.func_190916_E())
              j = itemstack.func_190916_E(); 
            itemstack.func_190918_g(j);
            EntityItem entityitem = new EntityItem(this.aircraft.world, this.aircraft.posX + x, this.aircraft.posY + y, this.aircraft.posZ + z, new ItemStack(itemstack.getItem(), j, itemstack.getMetadata()));
            if (itemstack.hasTagCompound())
              entityitem.getEntityItem().setTagCompound(itemstack.getTagCompound().copy()); 
            float f3 = 0.05F;
            entityitem.motionX = ((float)rand.nextGaussian() * f3);
            entityitem.motionY = ((float)rand.nextGaussian() * f3 + 0.2F);
            entityitem.motionZ = ((float)rand.nextGaussian() * f3);
            this.aircraft.world.spawnEntityInWorld((Entity)entityitem);
          } 
        } 
      }  
  }
  
  public ItemStack decrStackSize(int par1, int par2) {
    if (!this.containerItems[par1].func_190926_b()) {
      if (this.containerItems[par1].func_190916_E() <= par2) {
        ItemStack itemStack = this.containerItems[par1];
        this.containerItems[par1] = ItemStack.field_190927_a;
        return itemStack;
      } 
      ItemStack itemstack = this.containerItems[par1].splitStack(par2);
      if (this.containerItems[par1].func_190916_E() == 0)
        this.containerItems[par1] = ItemStack.field_190927_a; 
      return itemstack;
    } 
    return ItemStack.field_190927_a;
  }
  
  public ItemStack removeStackFromSlot(int par1) {
    if (!this.containerItems[par1].func_190926_b()) {
      ItemStack itemstack = this.containerItems[par1];
      this.containerItems[par1] = ItemStack.field_190927_a;
      return itemstack;
    } 
    return ItemStack.field_190927_a;
  }
  
  public void setInventorySlotContents(int par1, ItemStack par2ItemStack) {
    this.containerItems[par1] = par2ItemStack;
    if (!par2ItemStack.func_190926_b() && par2ItemStack.func_190916_E() > getInventoryStackLimit())
      par2ItemStack.func_190920_e(getInventoryStackLimit()); 
  }
  
  public String getInventoryName() {
    return getInvName();
  }
  
  public String getName() {
    return getInvName();
  }
  
  public String getInvName() {
    if (this.aircraft.getAcInfo() == null)
      return ""; 
    String s = (this.aircraft.getAcInfo()).displayName;
    return (s.length() <= 32) ? s : s.substring(0, 31);
  }
  
  public boolean isInvNameLocalized() {
    return (this.aircraft.getAcInfo() != null);
  }
  
  public ITextComponent getDisplayName() {
    return (ITextComponent)new TextComponentString(getInvName());
  }
  
  public boolean hasCustomName() {
    return isInvNameLocalized();
  }
  
  public int getInventoryStackLimit() {
    return 64;
  }
  
  public void markDirty() {}
  
  public boolean isUsableByPlayer(EntityPlayer player) {
    return (player.getDistanceSqToEntity((Entity)this.aircraft) <= 144.0D);
  }
  
  public boolean isItemValidForSlot(int par1, ItemStack par2ItemStack) {
    return true;
  }
  
  public boolean isStackValidForSlot(int par1, ItemStack par2ItemStack) {
    return true;
  }
  
  public void openInventory(EntityPlayer player) {}
  
  public void closeInventory(EntityPlayer player) {}
  
  protected void writeEntityToNBT(NBTTagCompound par1NBTTagCompound) {
    NBTTagList nbttaglist = new NBTTagList();
    for (int i = 0; i < this.containerItems.length; i++) {
      if (!this.containerItems[i].func_190926_b()) {
        NBTTagCompound nbttagcompound1 = new NBTTagCompound();
        nbttagcompound1.setByte("SlotAC", (byte)i);
        this.containerItems[i].writeToNBT(nbttagcompound1);
        nbttaglist.appendTag((NBTBase)nbttagcompound1);
      } 
    } 
    par1NBTTagCompound.setTag("ItemsAC", (NBTBase)nbttaglist);
  }
  
  protected void readEntityFromNBT(NBTTagCompound par1NBTTagCompound) {
    NBTTagList nbttaglist = W_NBTTag.getTagList(par1NBTTagCompound, "ItemsAC", 10);
    this.containerItems = new ItemStack[getSizeInventory()];
    Arrays.fill((Object[])this.containerItems, ItemStack.field_190927_a);
    for (int i = 0; i < nbttaglist.tagCount(); i++) {
      NBTTagCompound nbttagcompound1 = W_NBTTag.tagAt(nbttaglist, i);
      int j = nbttagcompound1.getByte("SlotAC") & 0xFF;
      if (j >= 0 && j < this.containerItems.length)
        this.containerItems[j] = new ItemStack(nbttagcompound1); 
    } 
  }
  
  public void onInventoryChanged() {}
  
  public int getField(int id) {
    return 0;
  }
  
  public void setField(int id, int value) {}
  
  public int getFieldCount() {
    return 0;
  }
  
  public void clear() {
    for (int i = 0; i < getSizeInventory(); i++)
      this.containerItems[i] = ItemStack.field_190927_a; 
  }
}
