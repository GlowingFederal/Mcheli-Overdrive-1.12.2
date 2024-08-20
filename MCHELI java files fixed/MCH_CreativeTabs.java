package mcheli;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import mcheli.aircraft.MCH_AircraftInfo;
import mcheli.aircraft.MCH_ItemAircraft;
import mcheli.wrapper.W_Item;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class MCH_CreativeTabs extends CreativeTabs {
  private List<ItemStack> iconItems;
  
  private ItemStack lastItem;
  
  private int currentIconIndex;
  
  private int switchItemWait;
  
  private Item fixedItem = null;
  
  public MCH_CreativeTabs(String label) {
    super(label);
    this.iconItems = new ArrayList<>();
    this.currentIconIndex = 0;
    this.switchItemWait = 0;
    this.lastItem = ItemStack.EMPTY;
  }
  
  public void setFixedIconItem(String itemName) {
    if (itemName.indexOf(':') >= 0) {
      this.fixedItem = W_Item.getItemByName(itemName);
    } else {
      this.fixedItem = W_Item.getItemByName("mcheli:" + itemName);
      if (this.fixedItem != null);
    } 
  }
  
  public ItemStack getTabIconItem() {
    if (this.iconItems.size() <= 0)
      return ItemStack.EMPTY; 
    this.currentIconIndex = (this.currentIconIndex + 1) % this.iconItems.size();
    return this.iconItems.get(this.currentIconIndex);
  }
  
  public ItemStack getIconItemStack() {
    if (this.fixedItem != null)
      return new ItemStack(this.fixedItem, 1, 0); 
    if (this.switchItemWait > 0) {
      this.switchItemWait--;
    } else {
      this.lastItem = getTabIconItem();
      this.switchItemWait = 60;
    } 
    if (this.lastItem.isEmpty())
      this.lastItem = new ItemStack(W_Item.getItemByName("iron_block")); 
    return this.lastItem;
  }
  
  @SideOnly(Side.CLIENT)
  public void displayAllRelevantItems(NonNullList<ItemStack> list) {
    super.displayAllRelevantItems(list);
    Comparator<ItemStack> cmp = new Comparator<ItemStack>() {
        public int compare(ItemStack i1, ItemStack i2) {
          if (i1.getItem() instanceof MCH_ItemAircraft && i2.getItem() instanceof MCH_ItemAircraft) {
            MCH_AircraftInfo info1 = ((MCH_ItemAircraft)i1.getItem()).getAircraftInfo();
            MCH_AircraftInfo info2 = ((MCH_ItemAircraft)i2.getItem()).getAircraftInfo();
            if (info1 != null && info2 != null) {
              String s1 = info1.category + "." + info1.name;
              String s2 = info2.category + "." + info2.name;
              return s1.compareTo(s2);
            } 
          } 
          return i1.getItem().getUnlocalizedName().compareTo(i2.getItem().getUnlocalizedName());
        }
      };
    Collections.sort((List<ItemStack>)list, cmp);
  }
  
  public void addIconItem(Item i) {
    if (i != null)
      this.iconItems.add(new ItemStack(i)); 
  }
  
  public String getTranslatedTabLabel() {
    return "MC Heli";
  }
}
