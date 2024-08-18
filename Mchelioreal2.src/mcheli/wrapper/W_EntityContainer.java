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
  
  protected void func_70088_a() {}
  
  public ItemStack func_70301_a(int par1) {
    return this.containerItems[par1];
  }
  
  public int func_70297_j_() {
    return 64;
  }
  
  public int getUsingSlotNum() {
    int numUsingSlot = 0;
    if (this.containerItems == null) {
      numUsingSlot = 0;
    } else {
      int n = func_70302_i_();
      numUsingSlot = 0;
      for (int i = 0; i < n && i < this.containerItems.length; i++) {
        if (!func_70301_a(i).func_190926_b())
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
  
  public ItemStack func_70298_a(int par1, int par2) {
    if (!this.containerItems[par1].func_190926_b()) {
      if (this.containerItems[par1].func_190916_E() <= par2) {
        ItemStack itemStack = this.containerItems[par1];
        this.containerItems[par1] = ItemStack.field_190927_a;
        return itemStack;
      } 
      ItemStack itemstack = this.containerItems[par1].func_77979_a(par2);
      if (this.containerItems[par1].func_190916_E() == 0)
        this.containerItems[par1] = ItemStack.field_190927_a; 
      return itemstack;
    } 
    return ItemStack.field_190927_a;
  }
  
  public ItemStack func_70304_b(int par1) {
    if (!this.containerItems[par1].func_190926_b()) {
      ItemStack itemstack = this.containerItems[par1];
      this.containerItems[par1] = ItemStack.field_190927_a;
      return itemstack;
    } 
    return null;
  }
  
  public void func_70299_a(int par1, ItemStack par2ItemStack) {
    this.containerItems[par1] = par2ItemStack;
    if (!par2ItemStack.func_190926_b() && par2ItemStack.func_190916_E() > func_70297_j_())
      par2ItemStack.func_190920_e(func_70297_j_()); 
    func_70296_d();
  }
  
  public void onInventoryChanged() {}
  
  public boolean func_70300_a(EntityPlayer par1EntityPlayer) {
    return !this.field_70128_L;
  }
  
  public boolean func_94041_b(int par1, ItemStack par2ItemStack) {
    return true;
  }
  
  public boolean isStackValidForSlot(int par1, ItemStack par2ItemStack) {
    return true;
  }
  
  public String getInvName() {
    return "Inventory";
  }
  
  public String func_70005_c_() {
    return getInvName();
  }
  
  public String getInventoryName() {
    return getInvName();
  }
  
  public ITextComponent func_145748_c_() {
    return (ITextComponent)new TextComponentString(getInventoryName());
  }
  
  public boolean isInvNameLocalized() {
    return false;
  }
  
  public boolean func_145818_k_() {
    return isInvNameLocalized();
  }
  
  public void func_70106_y() {
    if (this.dropContentsWhenDead && !this.field_70170_p.field_72995_K)
      for (int i = 0; i < func_70302_i_(); i++) {
        ItemStack itemstack = func_70301_a(i);
        if (!itemstack.func_190926_b()) {
          float x = this.field_70146_Z.nextFloat() * 0.8F + 0.1F;
          float y = this.field_70146_Z.nextFloat() * 0.8F + 0.1F;
          float z = this.field_70146_Z.nextFloat() * 0.8F + 0.1F;
          while (itemstack.func_190916_E() > 0) {
            int j = this.field_70146_Z.nextInt(21) + 10;
            if (j > itemstack.func_190916_E())
              j = itemstack.func_190916_E(); 
            itemstack.func_190918_g(j);
            EntityItem entityitem = new EntityItem(this.field_70170_p, this.field_70165_t + x, this.field_70163_u + y, this.field_70161_v + z, new ItemStack(itemstack.func_77973_b(), j, itemstack.func_77960_j()));
            if (itemstack.func_77942_o())
              entityitem.func_92059_d().func_77982_d(itemstack.func_77978_p().func_74737_b()); 
            float f3 = 0.05F;
            entityitem.field_70159_w = ((float)this.field_70146_Z.nextGaussian() * f3);
            entityitem.field_70181_x = ((float)this.field_70146_Z.nextGaussian() * f3 + 0.2F);
            entityitem.field_70179_y = ((float)this.field_70146_Z.nextGaussian() * f3);
            this.field_70170_p.func_72838_d((Entity)entityitem);
          } 
        } 
      }  
    super.func_70106_y();
  }
  
  protected void func_70014_b(NBTTagCompound par1NBTTagCompound) {
    NBTTagList nbttaglist = new NBTTagList();
    for (int i = 0; i < this.containerItems.length; i++) {
      if (!this.containerItems[i].func_190926_b()) {
        NBTTagCompound nbttagcompound1 = new NBTTagCompound();
        nbttagcompound1.func_74774_a("Slot", (byte)i);
        this.containerItems[i].func_77955_b(nbttagcompound1);
        nbttaglist.func_74742_a((NBTBase)nbttagcompound1);
      } 
    } 
    par1NBTTagCompound.func_74782_a("Items", (NBTBase)nbttaglist);
  }
  
  protected void func_70037_a(NBTTagCompound par1NBTTagCompound) {
    NBTTagList nbttaglist = W_NBTTag.getTagList(par1NBTTagCompound, "Items", 10);
    this.containerItems = new ItemStack[func_70302_i_()];
    Arrays.fill((Object[])this.containerItems, ItemStack.field_190927_a);
    MCH_Lib.DbgLog(this.field_70170_p, "W_EntityContainer.readEntityFromNBT.InventorySize = %d", new Object[] { Integer.valueOf(func_70302_i_()) });
    for (int i = 0; i < nbttaglist.func_74745_c(); i++) {
      NBTTagCompound nbttagcompound1 = W_NBTTag.tagAt(nbttaglist, i);
      int j = nbttagcompound1.func_74771_c("Slot") & 0xFF;
      if (j >= 0 && j < this.containerItems.length)
        this.containerItems[j] = new ItemStack(nbttagcompound1); 
    } 
  }
  
  public Entity changeDimension(int dimensionIn, ITeleporter teleporter) {
    this.dropContentsWhenDead = false;
    return super.changeDimension(dimensionIn, teleporter);
  }
  
  public boolean displayInventory(EntityPlayer player) {
    if (!this.field_70170_p.field_72995_K && func_70302_i_() > 0) {
      player.func_71007_a(this);
      return true;
    } 
    return false;
  }
  
  public void func_174889_b(EntityPlayer player) {}
  
  public void func_174886_c(EntityPlayer player) {}
  
  public void func_70296_d() {}
  
  public int func_70302_i_() {
    return 0;
  }
  
  public void func_174888_l() {}
  
  public int func_174887_a_(int id) {
    return 0;
  }
  
  public void func_174885_b(int id, int value) {}
  
  public int func_174890_g() {
    return 0;
  }
}
