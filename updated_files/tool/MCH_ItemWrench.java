package mcheli.tool;

import com.google.common.collect.Multimap;
import java.util.List;
import java.util.Random;
import mcheli.MCH_MOD;
import mcheli.aircraft.MCH_EntityAircraft;
import mcheli.aircraft.MCH_EntitySeat;
import mcheli.wrapper.W_Item;
import mcheli.wrapper.W_WorldFunc;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.EnumAction;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraftforge.oredict.OreDictionary;

public class MCH_ItemWrench extends W_Item {
  private float damageVsEntity;
  
  private final Item.ToolMaterial toolMaterial;
  
  private static Random rand = new Random();
  
  public MCH_ItemWrench(int itemId, Item.ToolMaterial material) {
    super(itemId);
    this.toolMaterial = material;
    this.maxStackSize = 1;
    setMaxDamage(material.getMaxUses());
    this.damageVsEntity = 4.0F + material.getDamageVsEntity();
  }
  
  public boolean canHarvestBlock(IBlockState blockIn) {
    Material material = blockIn.getMaterial();
    if (material == Material.IRON)
      return true; 
    if (material instanceof net.minecraft.block.material.MaterialLogic)
      return true; 
    return false;
  }
  
  public float getStrVsBlock(ItemStack stack, IBlockState state) {
    Material material = state.getMaterial();
    if (material == Material.IRON)
      return 20.5F; 
    if (material instanceof net.minecraft.block.material.MaterialLogic)
      return 5.5F; 
    return 2.0F;
  }
  
  public static float getUseAnimSmooth(ItemStack stack, float partialTicks) {
    int i = Math.abs(getUseAnimCount(stack) - 8);
    int j = Math.abs(getUseAnimPrevCount(stack) - 8);
    return j + (i - j) * partialTicks;
  }
  
  public static int getUseAnimPrevCount(ItemStack stack) {
    return getAnimCount(stack, "MCH_WrenchAnimPrev");
  }
  
  public static int getUseAnimCount(ItemStack stack) {
    return getAnimCount(stack, "MCH_WrenchAnim");
  }
  
  public static void setUseAnimCount(ItemStack stack, int n, int prev) {
    setAnimCount(stack, "MCH_WrenchAnim", n);
    setAnimCount(stack, "MCH_WrenchAnimPrev", prev);
  }
  
  public static int getAnimCount(ItemStack stack, String name) {
    if (!stack.hasTagCompound())
      stack.setTagCompound(new NBTTagCompound()); 
    if (stack.getTagCompound().hasKey(name))
      return stack.getTagCompound().getInteger(name); 
    stack.getTagCompound().setInteger(name, 0);
    return 0;
  }
  
  public static void setAnimCount(ItemStack stack, String name, int n) {
    if (!stack.hasTagCompound())
      stack.setTagCompound(new NBTTagCompound()); 
    stack.getTagCompound().setInteger(name, n);
  }
  
  public boolean hitEntity(ItemStack itemStack, EntityLivingBase entity, EntityLivingBase player) {
    if (!player.world.isRemote)
      if (rand.nextInt(40) == 0) {
        entity.entityDropItem(new ItemStack(W_Item.getItemByName("iron_ingot"), 1, 0), 0.0F);
      } else if (rand.nextInt(20) == 0) {
        entity.entityDropItem(new ItemStack(W_Item.getItemByName("gunpowder"), 1, 0), 0.0F);
      }  
    itemStack.damageItem(2, player);
    return true;
  }
  
  public void onPlayerStoppedUsing(ItemStack stack, World worldIn, EntityLivingBase entityLiving, int timeLeft) {
    setUseAnimCount(stack, 0, 0);
  }
  
  public void onUsingTick(ItemStack stack, EntityLivingBase player, int count) {
    if (player.world.isRemote) {
      MCH_EntityAircraft ac = getMouseOverAircraft(player);
      if (ac != null) {
        int cnt = getUseAnimCount(stack);
        int prev = cnt;
        if (cnt <= 0) {
          cnt = 16;
        } else {
          cnt--;
        } 
        setUseAnimCount(stack, cnt, prev);
      } 
    } 
    if (!player.world.isRemote && count < getMaxItemUseDuration(stack) && count % 20 == 0) {
      MCH_EntityAircraft ac = getMouseOverAircraft(player);
      if (ac != null && ac.getHP() > 0 && ac.repair(10)) {
        stack.damageItem(1, player);
        W_WorldFunc.MOD_playSoundEffect(player.world, (int)ac.posX, (int)ac.posY, (int)ac.posZ, "wrench", 1.0F, 0.9F + rand
            .nextFloat() * 0.2F);
      } 
    } 
  }
  
  public void onUpdate(ItemStack item, World world, Entity entity, int n, boolean b) {
    if (entity instanceof EntityPlayer) {
      EntityPlayer player = (EntityPlayer)entity;
      ItemStack itemStack = player.getHeldItemMainhand();
      if (itemStack == item)
        MCH_MOD.proxy.setCreativeDigDelay(0); 
    } 
  }
  
  public MCH_EntityAircraft getMouseOverAircraft(EntityLivingBase entity) {
    RayTraceResult m = getMouseOver(entity, 1.0F);
    MCH_EntityAircraft ac = null;
    if (m != null)
      if (m.entityHit instanceof MCH_EntityAircraft) {
        ac = (MCH_EntityAircraft)m.entityHit;
      } else if (m.entityHit instanceof MCH_EntitySeat) {
        MCH_EntitySeat seat = (MCH_EntitySeat)m.entityHit;
        if (seat.getParent() != null)
          ac = seat.getParent(); 
      }  
    return ac;
  }
  
  private static RayTraceResult rayTrace(EntityLivingBase entity, double dist, float tick) {
    Vec3d vec3 = new Vec3d(entity.posX, entity.posY + entity.getEyeHeight(), entity.posZ);
    Vec3d vec31 = entity.getLook(tick);
    Vec3d vec32 = vec3.addVector(vec31.xCoord * dist, vec31.yCoord * dist, vec31.zCoord * dist);
    return entity.world.rayTraceBlocks(vec3, vec32, false, false, true);
  }
  
  private RayTraceResult getMouseOver(EntityLivingBase user, float tick) {
    Entity pointedEntity = null;
    double d0 = 4.0D;
    RayTraceResult objectMouseOver = rayTrace(user, d0, tick);
    double d1 = d0;
    Vec3d vec3 = new Vec3d(user.posX, user.posY + user.getEyeHeight(), user.posZ);
    if (objectMouseOver != null)
      d1 = objectMouseOver.hitVec.distanceTo(vec3); 
    Vec3d vec31 = user.getLook(tick);
    Vec3d vec32 = vec3.addVector(vec31.xCoord * d0, vec31.yCoord * d0, vec31.zCoord * d0);
    pointedEntity = null;
    Vec3d vec33 = null;
    float f1 = 1.0F;
    List<Entity> list = user.world.getEntitiesWithinAABBExcludingEntity((Entity)user, user
        .getEntityBoundingBox().addCoord(vec31.xCoord * d0, vec31.yCoord * d0, vec31.zCoord * d0).expand(f1, f1, f1));
    double d2 = d1;
    for (int i = 0; i < list.size(); i++) {
      Entity entity = list.get(i);
      if (entity.canBeCollidedWith()) {
        float f2 = entity.getCollisionBorderSize();
        AxisAlignedBB axisalignedbb = entity.getEntityBoundingBox().expand(f2, f2, f2);
        RayTraceResult movingobjectposition = axisalignedbb.calculateIntercept(vec3, vec32);
        if (axisalignedbb.isVecInside(vec3)) {
          if (0.0D < d2 || d2 == 0.0D) {
            pointedEntity = entity;
            vec33 = (movingobjectposition == null) ? vec3 : movingobjectposition.hitVec;
            d2 = 0.0D;
          } 
        } else if (movingobjectposition != null) {
          double d3 = vec3.distanceTo(movingobjectposition.hitVec);
          if (d3 < d2 || d2 == 0.0D)
            if (entity == user.getRidingEntity() && !entity.canRiderInteract()) {
              if (d2 == 0.0D) {
                pointedEntity = entity;
                vec33 = movingobjectposition.hitVec;
              } 
            } else {
              pointedEntity = entity;
              vec33 = movingobjectposition.hitVec;
              d2 = d3;
            }  
        } 
      } 
    } 
    if (pointedEntity != null && (d2 < d1 || objectMouseOver == null))
      objectMouseOver = new RayTraceResult(pointedEntity, vec33); 
    return objectMouseOver;
  }
  
  public boolean onBlockDestroyed(ItemStack itemStack, World world, IBlockState state, BlockPos pos, EntityLivingBase entity) {
    if (state.getBlockHardness(world, pos) != 0.0D)
      itemStack.damageItem(2, entity); 
    return true;
  }
  
  @SideOnly(Side.CLIENT)
  public boolean isFull3D() {
    return true;
  }
  
  public EnumAction getItemUseAction(ItemStack itemStack) {
    return EnumAction.BLOCK;
  }
  
  public int getMaxItemUseDuration(ItemStack itemStack) {
    return 72000;
  }
  
  public ActionResult<ItemStack> onItemRightClick(World world, EntityPlayer player, EnumHand handIn) {
    player.setActiveHand(handIn);
    return ActionResult.newResult(EnumActionResult.SUCCESS, player.getHeldItem(handIn));
  }
  
  public int getItemEnchantability() {
    return this.toolMaterial.getEnchantability();
  }
  
  public String getToolMaterialName() {
    return this.toolMaterial.toString();
  }
  
  public boolean getIsRepairable(ItemStack item1, ItemStack item2) {
    ItemStack mat = this.toolMaterial.getRepairItemStack();
    if (!mat.func_190926_b() && OreDictionary.itemMatches(mat, item2, false))
      return true; 
    return super.getIsRepairable(item1, item2);
  }
  
  public Multimap<String, AttributeModifier> getItemAttributeModifiers(EntityEquipmentSlot equipmentSlot) {
    Multimap<String, AttributeModifier> multimap = super.getItemAttributeModifiers(equipmentSlot);
    if (equipmentSlot == EntityEquipmentSlot.MAINHAND)
      multimap.put(SharedMonsterAttributes.ATTACK_DAMAGE.getAttributeUnlocalizedName(), new AttributeModifier(ATTACK_DAMAGE_MODIFIER, "Weapon modifier", this.damageVsEntity, 0)); 
    return multimap;
  }
}
