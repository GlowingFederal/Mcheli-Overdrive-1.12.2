package mcheli.tool.rangefinder;

import mcheli.aircraft.MCH_EntityAircraft;
import mcheli.aircraft.MCH_EntitySeat;
import mcheli.multiplay.MCH_PacketIndSpotEntity;
import mcheli.wrapper.W_Item;
import mcheli.wrapper.W_McClient;
import mcheli.wrapper.W_Reflection;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumAction;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class MCH_ItemRangeFinder extends W_Item {
  public static int rangeFinderUseCooldown = 0;
  
  public static boolean continueUsingItem = false;
  
  public static float zoom = 2.0F;
  
  public static int mode = 0;
  
  public MCH_ItemRangeFinder(int itemId) {
    super(itemId);
    this.maxStackSize = 1;
    setMaxDamage(10);
  }
  
  public static boolean canUse(EntityPlayer player) {
    if (player == null)
      return false; 
    if (player.world == null)
      return false; 
    if (player.getHeldItemMainhand().isEmpty())
      return false; 
    if (!(player.getHeldItemMainhand().getItem() instanceof MCH_ItemRangeFinder))
      return false; 
    if (player.getRidingEntity() instanceof MCH_EntityAircraft)
      return false; 
    if (player.getRidingEntity() instanceof MCH_EntitySeat) {
      MCH_EntityAircraft ac = ((MCH_EntitySeat)player.getRidingEntity()).getParent();
      if (ac != null && (ac.getIsGunnerMode((Entity)player) || ac.getWeaponIDBySeatID(ac.getSeatIdByEntity((Entity)player)) >= 0))
        return false; 
    } 
    return true;
  }
  
  public static boolean isUsingScope(EntityPlayer player) {
    return (player.getItemInUseMaxCount() > 8 || continueUsingItem);
  }
  
  public static void onStartUseItem() {
    zoom = 2.0F;
    W_Reflection.setCameraZoom(2.0F);
    continueUsingItem = true;
  }
  
  public static void onStopUseItem() {
    W_Reflection.restoreCameraZoom();
    continueUsingItem = false;
  }
  
  @SideOnly(Side.CLIENT)
  public void spotEntity(EntityPlayer player, ItemStack itemStack) {
    if (player != null && player.world.isRemote)
      if (rangeFinderUseCooldown == 0 && player.getItemInUseMaxCount() > 8)
        if (mode == 2) {
          rangeFinderUseCooldown = 60;
          MCH_PacketIndSpotEntity.send((EntityLivingBase)player, 0);
        } else if (itemStack.getMetadata() < itemStack.getMaxDamage()) {
          rangeFinderUseCooldown = 60;
          MCH_PacketIndSpotEntity.send((EntityLivingBase)player, (mode == 0) ? 60 : 3);
        } else {
          W_McClient.MOD_playSoundFX("ng", 1.0F, 1.0F);
        }   
  }
  
  public void onPlayerStoppedUsing(ItemStack stack, World worldIn, EntityLivingBase entityLiving, int timeLeft) {
    if (worldIn.isRemote)
      onStopUseItem(); 
  }
  
  public ItemStack onItemUseFinish(ItemStack stack, World worldIn, EntityLivingBase entityLiving) {
    return stack;
  }
  
  @SideOnly(Side.CLIENT)
  public boolean isFull3D() {
    return true;
  }
  
  public EnumAction getItemUseAction(ItemStack itemStack) {
    return EnumAction.BOW;
  }
  
  public int getMaxItemUseDuration(ItemStack itemStack) {
    return 72000;
  }
  
  public ActionResult<ItemStack> onItemRightClick(World world, EntityPlayer player, EnumHand handIn) {
    ItemStack itemstack = player.getHeldItem(handIn);
    if (canUse(player))
      player.setActiveHand(handIn); 
    return ActionResult.newResult(EnumActionResult.SUCCESS, itemstack);
  }
}
