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
    this.field_77777_bU = 1;
    func_77637_a(CreativeTabs.field_78029_e);
  }
  
  public ActionResult<ItemStack> func_77659_a(World world, EntityPlayer player, EnumHand handIn) {
    MCH_EntityGunner mCH_EntityGunner;
    MCH_EntitySeat mCH_EntitySeat;
    MCH_EntityAircraft mCH_EntityAircraft;
    ItemStack itemstack = player.func_184586_b(handIn);
    float f = 1.0F;
    float pitch = player.field_70127_C + (player.field_70125_A - player.field_70127_C) * f;
    float yaw = player.field_70126_B + (player.field_70177_z - player.field_70126_B) * f;
    double dx = player.field_70169_q + (player.field_70165_t - player.field_70169_q) * f;
    double dy = player.field_70167_r + (player.field_70163_u - player.field_70167_r) * f + player.func_70047_e();
    double dz = player.field_70166_s + (player.field_70161_v - player.field_70166_s) * f;
    Vec3d vec3 = new Vec3d(dx, dy, dz);
    float f3 = MathHelper.func_76134_b(-yaw * 0.017453292F - 3.1415927F);
    float f4 = MathHelper.func_76126_a(-yaw * 0.017453292F - 3.1415927F);
    float f5 = -MathHelper.func_76134_b(-pitch * 0.017453292F);
    float f6 = MathHelper.func_76126_a(-pitch * 0.017453292F);
    float f7 = f4 * f5;
    float f8 = f3 * f5;
    double d3 = 5.0D;
    Vec3d vec31 = vec3.func_72441_c(f7 * d3, f6 * d3, f8 * d3);
    List<MCH_EntityGunner> list = world.func_72872_a(MCH_EntityGunner.class, player
        .func_174813_aQ().func_72314_b(5.0D, 5.0D, 5.0D));
    Entity target = null;
    for (int i = 0; i < list.size(); i++) {
      MCH_EntityGunner gunner = list.get(i);
      if (gunner.func_174813_aQ().func_72327_a(vec3, vec31) != null)
        if (target == null || player.func_70068_e((Entity)gunner) < player.func_70068_e(target))
          mCH_EntityGunner = gunner;  
    } 
    if (mCH_EntityGunner == null) {
      List<MCH_EntitySeat> list1 = world.func_72872_a(MCH_EntitySeat.class, player
          .func_174813_aQ().func_72314_b(5.0D, 5.0D, 5.0D));
      for (int j = 0; j < list1.size(); j++) {
        MCH_EntitySeat seat = list1.get(j);
        if (seat.getParent() != null && seat.getParent().getAcInfo() != null && seat
          .func_174813_aQ().func_72327_a(vec3, vec31) != null)
          if (mCH_EntityGunner == null || player.func_70068_e((Entity)seat) < player.func_70068_e((Entity)mCH_EntityGunner))
            if (seat.getRiddenByEntity() instanceof MCH_EntityGunner) {
              Entity entity = seat.getRiddenByEntity();
            } else {
              mCH_EntitySeat = seat;
            }   
      } 
    } 
    if (mCH_EntitySeat == null) {
      List<MCH_EntityAircraft> list2 = world.func_72872_a(MCH_EntityAircraft.class, player
          .func_174813_aQ().func_72314_b(5.0D, 5.0D, 5.0D));
      for (int j = 0; j < list2.size(); j++) {
        MCH_EntityAircraft ac = list2.get(j);
        if (!ac.isUAV() && ac.getAcInfo() != null && ac
          .func_174813_aQ().func_72327_a(vec3, vec31) != null)
          if (mCH_EntitySeat == null || player.func_70068_e((Entity)ac) < player.func_70068_e((Entity)mCH_EntitySeat))
            if (ac.getRiddenByEntity() instanceof MCH_EntityGunner) {
              Entity entity = ac.getRiddenByEntity();
            } else {
              mCH_EntityAircraft = ac;
            }   
      } 
    } 
    if (mCH_EntityAircraft instanceof MCH_EntityGunner) {
      mCH_EntityAircraft.func_184230_a(player, handIn);
      return ActionResult.newResult(EnumActionResult.SUCCESS, itemstack);
    } 
    if (this.targetType == 1 && !world.field_72995_K && player.func_96124_cp() == null) {
      player.func_145747_a((ITextComponent)new TextComponentString("You are not on team."));
      return ActionResult.newResult(EnumActionResult.FAIL, itemstack);
    } 
    if (mCH_EntityAircraft == null) {
      if (!world.field_72995_K)
        player.func_145747_a((ITextComponent)new TextComponentString("Right click to seat.")); 
      return ActionResult.newResult(EnumActionResult.FAIL, itemstack);
    } 
    if (!world.field_72995_K) {
      MCH_EntityGunner gunner = new MCH_EntityGunner(world, ((Entity)mCH_EntityAircraft).field_70165_t, ((Entity)mCH_EntityAircraft).field_70163_u, ((Entity)mCH_EntityAircraft).field_70161_v);
      gunner.field_70177_z = (((MathHelper.func_76128_c((player.field_70177_z * 4.0F / 360.0F) + 0.5D) & 0x3) - 1) * 90);
      gunner.isCreative = player.field_71075_bZ.field_75098_d;
      gunner.targetType = this.targetType;
      gunner.ownerUUID = player.func_110124_au().toString();
      ScorePlayerTeam team = world.func_96441_U().func_96509_i(player.func_145748_c_().func_150254_d());
      if (team != null)
        gunner.setTeamName(team.func_96661_b()); 
      world.func_72838_d((Entity)gunner);
      gunner.func_184220_m((Entity)mCH_EntityAircraft);
      W_WorldFunc.MOD_playSoundAtEntity((Entity)gunner, "wrench", 1.0F, 3.0F);
      MCH_EntityAircraft ac = (mCH_EntityAircraft instanceof MCH_EntityAircraft) ? mCH_EntityAircraft : ((MCH_EntitySeat)mCH_EntityAircraft).getParent();
      String teamPlayerName = ScorePlayerTeam.func_96667_a(player.func_96124_cp(), player.func_145748_c_().func_150254_d());
      String displayName = TextFormatting.GOLD + (ac.getAcInfo()).displayName + TextFormatting.RESET;
      int seatNo = ac.getSeatIdByEntity((Entity)gunner) + 1;
      if (MCH_MOD.isTodaySep01()) {
        String msg = "Hi " + teamPlayerName + "! I sat in the " + seatNo + " seat of " + displayName + "!";
        player.func_145747_a((ITextComponent)new TextComponentTranslation("chat.type.text", new Object[] { "EMB4", new TextComponentString(msg) }));
      } else {
        player.func_145747_a((ITextComponent)new TextComponentString("The gunner was put on " + displayName + " seat " + seatNo + " by " + teamPlayerName));
      } 
    } 
    if (!player.field_71075_bZ.field_75098_d)
      itemstack.func_190918_g(1); 
    return ActionResult.newResult(EnumActionResult.SUCCESS, itemstack);
  }
  
  @SideOnly(Side.CLIENT)
  public static int getColorFromItemStack(ItemStack stack, int tintIndex) {
    MCH_ItemSpawnGunner item = (MCH_ItemSpawnGunner)stack.func_77973_b();
    return (tintIndex == 0) ? item.primaryColor : item.secondaryColor;
  }
}
