package mcheli.mob;

import java.util.List;
import mcheli.MCH_MOD;
import mcheli.aircraft.MCH_EntityAircraft;
import mcheli.aircraft.MCH_EntitySeat;
import mcheli.wrapper.W_Item;
import mcheli.wrapper.W_WorldFunc;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.scoreboard.ScorePlayerTeam;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class MCH_ItemSpawnGunner extends W_Item {
  public int primaryColor = 16777215;
  
  public int secondaryColor = 16777215;
  
  public int targetType = 0;
  
  public MCH_ItemSpawnGunner() {
    this.maxStackSize = 1;
    setCreativeTab(CreativeTabs.TRANSPORTATION);
  }
  
  public ActionResult<ItemStack> onItemRightClick(World world, EntityPlayer player, EnumHand handIn) {
    MCH_EntityGunner mCH_EntityGunner;
    MCH_EntitySeat mCH_EntitySeat;
    MCH_EntityAircraft mCH_EntityAircraft;
    ItemStack itemstack = player.getHeldItem(handIn);
    float f = 1.0F;
    float pitch = player.prevRotationPitch + (player.rotationPitch - player.prevRotationPitch) * f;
    float yaw = player.prevRotationYaw + (player.rotationYaw - player.prevRotationYaw) * f;
    double dx = player.prevPosX + (player.posX - player.prevPosX) * f;
    double dy = player.prevPosY + (player.posY - player.prevPosY) * f + player.getEyeHeight();
    double dz = player.prevPosZ + (player.posZ - player.prevPosZ) * f;
    Vec3d vec3 = new Vec3d(dx, dy, dz);
    float f3 = MathHelper.cos(-yaw * 0.017453292F - 3.1415927F);
    float f4 = MathHelper.sin(-yaw * 0.017453292F - 3.1415927F);
    float f5 = -MathHelper.cos(-pitch * 0.017453292F);
    float f6 = MathHelper.sin(-pitch * 0.017453292F);
    float f7 = f4 * f5;
    float f8 = f3 * f5;
    double d3 = 5.0D;
    Vec3d vec31 = vec3.addVector(f7 * d3, f6 * d3, f8 * d3);
    List<MCH_EntityGunner> list = world.getEntitiesWithinAABB(MCH_EntityGunner.class, player
        .getEntityBoundingBox().grow(5.0D, 5.0D, 5.0D));
    Entity target = null;
    for (int i = 0; i < list.size(); i++) {
      MCH_EntityGunner gunner = list.get(i);
      if (gunner.getEntityBoundingBox().calculateIntercept(vec3, vec31) != null)
        if (target == null || player.getDistanceSq((Entity)gunner) < player.getDistanceSq(target))
          mCH_EntityGunner = gunner;  
    } 
    if (mCH_EntityGunner == null) {
      List<MCH_EntitySeat> list1 = world.getEntitiesWithinAABB(MCH_EntitySeat.class, player
          .getEntityBoundingBox().grow(5.0D, 5.0D, 5.0D));
      for (int j = 0; j < list1.size(); j++) {
        MCH_EntitySeat seat = list1.get(j);
        if (seat.getParent() != null && seat.getParent().getAcInfo() != null && seat
          .getEntityBoundingBox().calculateIntercept(vec3, vec31) != null)
          if (mCH_EntityGunner == null || player.getDistanceSq((Entity)seat) < player.getDistanceSq((Entity)mCH_EntityGunner))
            if (seat.getRiddenByEntity() instanceof MCH_EntityGunner) {
              Entity entity = seat.getRiddenByEntity();
            } else {
              mCH_EntitySeat = seat;
            }   
      } 
    } 
    if (mCH_EntitySeat == null) {
      List<MCH_EntityAircraft> list2 = world.getEntitiesWithinAABB(MCH_EntityAircraft.class, player
          .getEntityBoundingBox().grow(5.0D, 5.0D, 5.0D));
      for (int j = 0; j < list2.size(); j++) {
        MCH_EntityAircraft ac = list2.get(j);
        if (!ac.isUAV() && ac.getAcInfo() != null && ac
          .getEntityBoundingBox().calculateIntercept(vec3, vec31) != null)
          if (mCH_EntitySeat == null || player.getDistanceSq((Entity)ac) < player.getDistanceSq((Entity)mCH_EntitySeat))
            if (ac.getRiddenByEntity() instanceof MCH_EntityGunner) {
              Entity entity = ac.getRiddenByEntity();
            } else {
              mCH_EntityAircraft = ac;
            }   
      } 
    } 
    if (mCH_EntityAircraft instanceof MCH_EntityGunner) {
      mCH_EntityAircraft.processInitialInteract(player, handIn);
      return ActionResult.newResult(EnumActionResult.SUCCESS, itemstack);
    } 
    if (this.targetType == 1 && !world.isRemote && player.getTeam() == null) {
      player.sendMessage((ITextComponent)new TextComponentString("You are not on team."));
      return ActionResult.newResult(EnumActionResult.FAIL, itemstack);
    } 
    if (mCH_EntityAircraft == null) {
      if (!world.isRemote)
        player.sendMessage((ITextComponent)new TextComponentString("Right click to seat.")); 
      return ActionResult.newResult(EnumActionResult.FAIL, itemstack);
    } 
    if (!world.isRemote) {
      MCH_EntityGunner gunner = new MCH_EntityGunner(world, ((Entity)mCH_EntityAircraft).posX, ((Entity)mCH_EntityAircraft).posY, ((Entity)mCH_EntityAircraft).posZ);
      gunner.rotationYaw = (((MathHelper.floor((player.rotationYaw * 4.0F / 360.0F) + 0.5D) & 0x3) - 1) * 90);
      gunner.isCreative = player.capabilities.isCreativeMode;
      gunner.targetType = this.targetType;
      gunner.ownerUUID = player.getUniqueID().toString();
      ScorePlayerTeam team = world.getScoreboard().getPlayersTeam(player.getDisplayName().getFormattedText());
      if (team != null)
        gunner.setTeamName(team.getName()); 
      world.spawnEntity((Entity)gunner);
      gunner.startRiding((Entity)mCH_EntityAircraft);
      W_WorldFunc.MOD_playSoundAtEntity((Entity)gunner, "wrench", 1.0F, 3.0F);
      MCH_EntityAircraft ac = (mCH_EntityAircraft instanceof MCH_EntityAircraft) ? mCH_EntityAircraft : ((MCH_EntitySeat)mCH_EntityAircraft).getParent();
      String teamPlayerName = ScorePlayerTeam.formatPlayerName(player.getTeam(), player.getDisplayName().getFormattedText());
      String displayName = TextFormatting.GOLD + (ac.getAcInfo()).displayName + TextFormatting.RESET;
      int seatNo = ac.getSeatIdByEntity((Entity)gunner) + 1;
      if (MCH_MOD.isTodaySep01()) {
        String msg = "Hi " + teamPlayerName + "! I sat in the " + seatNo + " seat of " + displayName + "!";
        player.sendMessage((ITextComponent)new TextComponentTranslation("chat.type.text", new Object[] { "EMB4", new TextComponentString(msg) }));
      } else {
        player.sendMessage((ITextComponent)new TextComponentString("The gunner was put on " + displayName + " seat " + seatNo + " by " + teamPlayerName));
      } 
    } 
    if (!player.capabilities.isCreativeMode)
      itemstack.shrink(1); 
    return ActionResult.newResult(EnumActionResult.SUCCESS, itemstack);
  }
  
  @SideOnly(Side.CLIENT)
  public static int getColorFromItemStack(ItemStack stack, int tintIndex) {
    MCH_ItemSpawnGunner item = (MCH_ItemSpawnGunner)stack.getItem();
    return (tintIndex == 0) ? item.primaryColor : item.secondaryColor;
  }
}
