package mcheli.wrapper;

import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.world.World;

public abstract class W_EntityPlayer extends EntityPlayer {
  public W_EntityPlayer(World par1World, EntityPlayer player) {
    super(par1World, player.getGameProfile());
  }
  
  public static void closeScreen(Entity p) {
    if (p != null)
      if (p.world.isRemote) {
        W_EntityPlayerSP.closeScreen(p);
      } else if (p instanceof EntityPlayerMP) {
        ((EntityPlayerMP)p).closeScreen();
      }  
  }
  
  public static boolean hasItem(EntityPlayer player, Item item) {
    return (item != null && player.inventory.hasItemStack(new ItemStack(item)));
  }
  
  public static boolean consumeInventoryItem(EntityPlayer player, Item item) {
    int index = player.inventory.func_194014_c(new ItemStack(item));
    return (item != null && player.inventory.decrStackSize(index, 1).isEmpty());
  }
  
  public static void addChatMessage(EntityPlayer player, String s) {
    player.addChatMessage((ITextComponent)new TextComponentString(s));
  }
  
  public static EntityItem dropPlayerItemWithRandomChoice(EntityPlayer player, ItemStack item, boolean b1, boolean b2) {
    return player.dropItem(item, b1, b2);
  }
  
  public static boolean isPlayer(Entity entity) {
    if (entity instanceof EntityPlayer)
      return true; 
    return false;
  }
}
