package mcheli.lweapon;

import javax.annotation.Nullable;
import mcheli.wrapper.W_Item;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.MobEffects;
import net.minecraft.item.EnumAction;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.world.World;

public class MCH_ItemLightWeaponBase extends W_Item {
  public final MCH_ItemLightWeaponBullet bullet;
  
  public MCH_ItemLightWeaponBase(int par1, MCH_ItemLightWeaponBullet bullet) {
    super(par1);
    setMaxDamage(10);
    setMaxStackSize(1);
    this.bullet = bullet;
  }
  
  public static String getName(ItemStack itemStack) {
    if (!itemStack.func_190926_b() && itemStack.getItem() instanceof MCH_ItemLightWeaponBase) {
      String name = itemStack.getUnlocalizedName();
      int li = name.lastIndexOf(":");
      if (li >= 0)
        name = name.substring(li + 1); 
      return name;
    } 
    return "";
  }
  
  public static boolean isHeld(@Nullable EntityPlayer player) {
    ItemStack is = (player != null) ? player.getHeldItemMainhand() : ItemStack.field_190927_a;
    if (!is.func_190926_b() && is.getItem() instanceof MCH_ItemLightWeaponBase)
      return (player.getItemInUseMaxCount() > 10); 
    return false;
  }
  
  public void onUsingTick(ItemStack stack, EntityLivingBase player, int count) {
    PotionEffect pe = player.getActivePotionEffect(MobEffects.NIGHT_VISION);
    if (pe != null && pe.getDuration() < 220)
      player.addPotionEffect(new PotionEffect(MobEffects.NIGHT_VISION, 250, 0, false, false)); 
  }
  
  public boolean onEntitySwing(EntityLivingBase entityLiving, ItemStack stack) {
    return true;
  }
  
  public EnumAction getItemUseAction(ItemStack par1ItemStack) {
    return EnumAction.BOW;
  }
  
  public int getMaxItemUseDuration(ItemStack par1ItemStack) {
    return 72000;
  }
  
  public ActionResult<ItemStack> onItemRightClick(World worldIn, EntityPlayer playerIn, EnumHand handIn) {
    ItemStack itemstack = playerIn.getHeldItem(handIn);
    if (!itemstack.func_190926_b())
      playerIn.setActiveHand(handIn); 
    return ActionResult.newResult(EnumActionResult.SUCCESS, itemstack);
  }
}
