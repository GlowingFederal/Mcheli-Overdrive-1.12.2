package mcheli.wrapper;

import java.util.Arrays;
import mcheli.MCH_Lib;
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
import net.minecraft.world.World;
import net.minecraftforge.common.util.ITeleporter;

public abstract class W_EntityContainer extends W_Entity implements IInventory {
  public static final int MAX_INVENTORY_SIZE = 54;
  
  private ItemStack[] containerItems;
  
  public boolean dropContentsWhenDead = true;
  
  public W_EntityContainer(World par1World) {
    super(par1World);
    this.containerItems = new ItemStack[54];
    Arrays.fill((Object[])this.containerItems, ItemStack.field_190927_a);
  }
  
  protected void entityInit() {}
  
  public ItemStack getStackInSlot(int par1) {
    return this.containerItems[par1];
  }
  
  public int getInventoryStackLimit() {
    return 64;
  }
  
  public int getUsingSlotNum() {
    int numUsingSlot = 0;
    if (this.containerItems == null) {
      numUsingSlot = 0;
    } else {
      int n = getSizeInventory();
      numUsingSlot = 0;
      for (int i = 0; i < n && i < this.containerItems.length; i++) {
        if (!getStackInSlot(i).func_190926_b())
          numUsingSlot++; 
      } 
    } 
    return numUsingSlot;
  }
  
  public boolean func_191420_l() {
    for (ItemStack itemstack : this.containerItems) {
      if (!itemstack.func_190926_b())
        return false; 
    } 
    return true;
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
    return null;
  }
  
  public void setInventorySlotContents(int par1, ItemStack par2ItemStack) {
    this.containerItems[par1] = par2ItemStack;
    if (!par2ItemStack.func_190926_b() && par2ItemStack.func_190916_E() > getInventoryStackLimit())
      par2ItemStack.func_190920_e(getInventoryStackLimit()); 
    markDirty();
  }
  
  public void onInventoryChanged() {}
  
  public boolean isUsableByPlayer(EntityPlayer par1EntityPlayer) {
    return !this.isDead;
  }
  
  public boolean isItemValidForSlot(int par1, ItemStack par2ItemStack) {
    return true;
  }
  
  public boolean isStackValidForSlot(int par1, ItemStack par2ItemStack) {
    return true;
  }
  
  public String getInvName() {
    return "Inventory";
  }
  
  public String getName() {
    return getInvName();
  }
  
  public String getInventoryName() {
    return getInvName();
  }
  
  public ITextComponent getDisplayName() {
    return (ITextComponent)new TextComponentString(getInventoryName());
  }
  
  public boolean isInvNameLocalized() {
    return false;
  }
  
  public boolean hasCustomName() {
    return isInvNameLocalized();
  }
  
  public void setDead() {
    if (this.dropContentsWhenDead && !this.world.isRemote)
      for (int i = 0; i < getSizeInventory(); i++) {
        ItemStack itemstack = getStackInSlot(i);
        if (!itemstack.func_190926_b()) {
          float x = this.rand.nextFloat() * 0.8F + 0.1F;
          float y = this.rand.nextFloat() * 0.8F + 0.1F;
          float z = this.rand.nextFloat() * 0.8F + 0.1F;
          while (itemstack.func_190916_E() > 0) {
            int j = this.rand.nextInt(21) + 10;
            if (j > itemstack.func_190916_E())
              j = itemstack.func_190916_E(); 
            itemstack.func_190918_g(j);
            EntityItem entityitem = new EntityItem(this.world, this.posX + x, this.posY + y, this.posZ + z, new ItemStack(itemstack.getItem(), j, itemstack.getMetadata()));
            if (itemstack.hasTagCompound())
              entityitem.getEntityItem().setTagCompound(itemstack.getTagCompound().copy()); 
            float f3 = 0.05F;
            entityitem.motionX = ((float)this.rand.nextGaussian() * f3);
            entityitem.motionY = ((float)this.rand.nextGaussian() * f3 + 0.2F);
            entityitem.motionZ = ((float)this.rand.nextGaussian() * f3);
            this.world.spawnEntityInWorld((Entity)entityitem);
          } 
        } 
      }  
    super.setDead();
  }
  
  protected void writeEntityToNBT(NBTTagCompound par1NBTTagCompound) {
    NBTTagList nbttaglist = new NBTTagList();
    for (int i = 0; i < this.containerItems.length; i++) {
      if (!this.containerItems[i].func_190926_b()) {
        NBTTagCompound nbttagcompound1 = new NBTTagCompound();
        nbttagcompound1.setByte("Slot", (byte)i);
        this.containerItems[i].writeToNBT(nbttagcompound1);
        nbttaglist.appendTag((NBTBase)nbttagcompound1);
      } 
    } 
    par1NBTTagCompound.setTag("Items", (NBTBase)nbttaglist);
  }
  
  protected void readEntityFromNBT(NBTTagCompound par1NBTTagCompound) {
    NBTTagList nbttaglist = W_NBTTag.getTagList(par1NBTTagCompound, "Items", 10);
    this.containerItems = new ItemStack[getSizeInventory()];
    Arrays.fill((Object[])this.containerItems, ItemStack.field_190927_a);
    MCH_Lib.DbgLog(this.world, "W_EntityContainer.readEntityFromNBT.InventorySize = %d", new Object[] { Integer.valueOf(getSizeInventory()) });
    for (int i = 0; i < nbttaglist.tagCount(); i++) {
      NBTTagCompound nbttagcompound1 = W_NBTTag.tagAt(nbttaglist, i);
      int j = nbttagcompound1.getByte("Slot") & 0xFF;
      if (j >= 0 && j < this.containerItems.length)
        this.containerItems[j] = new ItemStack(nbttagcompound1); 
    } 
  }
  
  public Entity changeDimension(int dimensionIn, ITeleporter teleporter) {
    this.dropContentsWhenDead = false;
    return super.changeDimension(dimensionIn, teleporter);
  }
  
  public boolean displayInventory(EntityPlayer player) {
    if (!this.world.isRemote && getSizeInventory() > 0) {
      player.displayGUIChest(this);
      return true;
    } 
    return false;
  }
  
  public void openInventory(EntityPlayer player) {}
  
  public void closeInventory(EntityPlayer player) {}
  
  public void markDirty() {}
  
  public int getSizeInventory() {
    return 0;
  }
  
  public void clear() {}
  
  public int getField(int id) {
    return 0;
  }
  
  public void setField(int id, int value) {}
  
  public int getFieldCount() {
    return 0;
  }
}
