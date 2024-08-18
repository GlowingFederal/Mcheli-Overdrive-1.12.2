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
    this.containerItems = new ItemStack[func_70302_i_()];
    Arrays.fill((Object[])this.containerItems, ItemStack.field_190927_a);
    this.aircraft = ac;
  }
  
  public ItemStack getFuelSlotItemStack(int i) {
    return func_70301_a(0 + i);
  }
  
  public ItemStack getParachuteSlotItemStack(int i) {
    return func_70301_a(3 + i);
  }
  
  public boolean haveParachute() {
    for (int i = 0; i < 2; i++) {
      ItemStack item = getParachuteSlotItemStack(i);
      if (!item.func_190926_b() && item.func_77973_b() instanceof mcheli.parachute.MCH_ItemParachute)
        return true; 
    } 
    return false;
  }
  
  public void consumeParachute() {
    for (int i = 0; i < 2; i++) {
      ItemStack item = getParachuteSlotItemStack(i);
      if (!item.func_190926_b() && item.func_77973_b() instanceof mcheli.parachute.MCH_ItemParachute) {
        func_70299_a(3 + i, ItemStack.field_190927_a);
        break;
      } 
    } 
  }
  
  public int func_70302_i_() {
    return 10;
  }
  
  public boolean func_191420_l() {
    for (ItemStack itemstack : this.containerItems) {
      if (!itemstack.func_190926_b())
        return false; 
    } 
    return true;
  }
  
  public ItemStack func_70301_a(int var1) {
    return this.containerItems[var1];
  }
  
  public void setDead() {
    Random rand = new Random();
    if (this.aircraft.dropContentsWhenDead && !this.aircraft.field_70170_p.field_72995_K)
      for (int i = 0; i < func_70302_i_(); i++) {
        ItemStack itemstack = func_70301_a(i);
        if (!itemstack.func_190926_b()) {
          float x = rand.nextFloat() * 0.8F + 0.1F;
          float y = rand.nextFloat() * 0.8F + 0.1F;
          float z = rand.nextFloat() * 0.8F + 0.1F;
          while (itemstack.func_190916_E() > 0) {
            int j = rand.nextInt(21) + 10;
            if (j > itemstack.func_190916_E())
              j = itemstack.func_190916_E(); 
            itemstack.func_190918_g(j);
            EntityItem entityitem = new EntityItem(this.aircraft.field_70170_p, this.aircraft.field_70165_t + x, this.aircraft.field_70163_u + y, this.aircraft.field_70161_v + z, new ItemStack(itemstack.func_77973_b(), j, itemstack.func_77960_j()));
            if (itemstack.func_77942_o())
              entityitem.func_92059_d().func_77982_d(itemstack.func_77978_p().func_74737_b()); 
            float f3 = 0.05F;
            entityitem.field_70159_w = ((float)rand.nextGaussian() * f3);
            entityitem.field_70181_x = ((float)rand.nextGaussian() * f3 + 0.2F);
            entityitem.field_70179_y = ((float)rand.nextGaussian() * f3);
            this.aircraft.field_70170_p.func_72838_d((Entity)entityitem);
          } 
        } 
      }  
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
    return ItemStack.field_190927_a;
  }
  
  public void func_70299_a(int par1, ItemStack par2ItemStack) {
    this.containerItems[par1] = par2ItemStack;
    if (!par2ItemStack.func_190926_b() && par2ItemStack.func_190916_E() > func_70297_j_())
      par2ItemStack.func_190920_e(func_70297_j_()); 
  }
  
  public String getInventoryName() {
    return getInvName();
  }
  
  public String func_70005_c_() {
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
  
  public ITextComponent func_145748_c_() {
    return (ITextComponent)new TextComponentString(getInvName());
  }
  
  public boolean func_145818_k_() {
    return isInvNameLocalized();
  }
  
  public int func_70297_j_() {
    return 64;
  }
  
  public void func_70296_d() {}
  
  public boolean func_70300_a(EntityPlayer player) {
    return (player.func_70068_e((Entity)this.aircraft) <= 144.0D);
  }
  
  public boolean func_94041_b(int par1, ItemStack par2ItemStack) {
    return true;
  }
  
  public boolean isStackValidForSlot(int par1, ItemStack par2ItemStack) {
    return true;
  }
  
  public void func_174889_b(EntityPlayer player) {}
  
  public void func_174886_c(EntityPlayer player) {}
  
  protected void writeEntityToNBT(NBTTagCompound par1NBTTagCompound) {
    NBTTagList nbttaglist = new NBTTagList();
    for (int i = 0; i < this.containerItems.length; i++) {
      if (!this.containerItems[i].func_190926_b()) {
        NBTTagCompound nbttagcompound1 = new NBTTagCompound();
        nbttagcompound1.func_74774_a("SlotAC", (byte)i);
        this.containerItems[i].func_77955_b(nbttagcompound1);
        nbttaglist.func_74742_a((NBTBase)nbttagcompound1);
      } 
    } 
    par1NBTTagCompound.func_74782_a("ItemsAC", (NBTBase)nbttaglist);
  }
  
  protected void readEntityFromNBT(NBTTagCompound par1NBTTagCompound) {
    NBTTagList nbttaglist = W_NBTTag.getTagList(par1NBTTagCompound, "ItemsAC", 10);
    this.containerItems = new ItemStack[func_70302_i_()];
    Arrays.fill((Object[])this.containerItems, ItemStack.field_190927_a);
    for (int i = 0; i < nbttaglist.func_74745_c(); i++) {
      NBTTagCompound nbttagcompound1 = W_NBTTag.tagAt(nbttaglist, i);
      int j = nbttagcompound1.func_74771_c("SlotAC") & 0xFF;
      if (j >= 0 && j < this.containerItems.length)
        this.containerItems[j] = new ItemStack(nbttagcompound1); 
    } 
  }
  
  public void onInventoryChanged() {}
  
  public int func_174887_a_(int id) {
    return 0;
  }
  
  public void func_174885_b(int id, int value) {}
  
  public int func_174890_g() {
    return 0;
  }
  
  public void func_174888_l() {
    for (int i = 0; i < func_70302_i_(); i++)
      this.containerItems[i] = ItemStack.field_190927_a; 
  }
}
