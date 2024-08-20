package mcheli;

import javax.annotation.Nullable;
import mcheli.wrapper.W_ItemArmor;
import net.minecraft.client.model.ModelBiped;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class MCH_ItemArmor extends W_ItemArmor {
  public static final String HELMET_TEXTURE = "mcheli:textures/helicopters/ah-64.png";
  
  public static final String CHESTPLATE_TEXTURE = "mcheli:textures/armor/plate.png";
  
  public static final String LEGGINGS_TEXTURE = "mcheli:textures/armor/leg.png";
  
  public static final String BOOTS_TEXTURE = "mcheli:textures/armor/boots.png";
  
  public MCH_ItemArmor(int par1, int par3, int par4) {
    super(par1, par3, par4);
  }
  
  public String getArmorTexture(ItemStack stack, Entity entity, EntityEquipmentSlot slot, String type) {
    if (slot == EntityEquipmentSlot.HEAD)
      return "mcheli:textures/helicopters/ah-64.png"; 
    if (slot == EntityEquipmentSlot.CHEST)
      return "mcheli:textures/armor/plate.png"; 
    if (slot == EntityEquipmentSlot.LEGS)
      return "mcheli:textures/armor/leg.png"; 
    if (slot == EntityEquipmentSlot.FEET)
      return "mcheli:textures/armor/boots.png"; 
    return "none";
  }
  
  public static MCH_TEST_ModelBiped model = null;
  
  @Nullable
  @SideOnly(Side.CLIENT)
  public ModelBiped getArmorModel(EntityLivingBase entityLiving, ItemStack itemStack, EntityEquipmentSlot armorSlot, ModelBiped _default) {
    if (model == null)
      model = new MCH_TEST_ModelBiped(); 
    if (armorSlot == EntityEquipmentSlot.HEAD)
      return model; 
    return null;
  }
}
