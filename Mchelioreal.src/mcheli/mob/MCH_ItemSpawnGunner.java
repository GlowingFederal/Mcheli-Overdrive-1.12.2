/*     */ package mcheli.mob;
/*     */ 
/*     */ import java.util.List;
/*     */ import mcheli.MCH_MOD;
/*     */ import mcheli.aircraft.MCH_EntityAircraft;
/*     */ import mcheli.aircraft.MCH_EntitySeat;
/*     */ import mcheli.wrapper.W_Item;
/*     */ import mcheli.wrapper.W_WorldFunc;
/*     */ import net.minecraft.creativetab.CreativeTabs;
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraft.scoreboard.ScorePlayerTeam;
/*     */ import net.minecraft.util.ActionResult;
/*     */ import net.minecraft.util.EnumActionResult;
/*     */ import net.minecraft.util.EnumHand;
/*     */ import net.minecraft.util.math.MathHelper;
/*     */ import net.minecraft.util.math.Vec3d;
/*     */ import net.minecraft.util.text.ITextComponent;
/*     */ import net.minecraft.util.text.TextComponentString;
/*     */ import net.minecraft.util.text.TextComponentTranslation;
/*     */ import net.minecraft.util.text.TextFormatting;
/*     */ import net.minecraft.world.World;
/*     */ import net.minecraftforge.fml.relauncher.Side;
/*     */ import net.minecraftforge.fml.relauncher.SideOnly;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class MCH_ItemSpawnGunner
/*     */   extends W_Item
/*     */ {
/*  39 */   public int primaryColor = 16777215;
/*  40 */   public int secondaryColor = 16777215;
/*  41 */   public int targetType = 0;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public MCH_ItemSpawnGunner() {
/*  48 */     this.field_77777_bU = 1;
/*  49 */     func_77637_a(CreativeTabs.field_78029_e);
/*     */   }
/*     */   
/*     */   public ActionResult<ItemStack> func_77659_a(World world, EntityPlayer player, EnumHand handIn) {
/*     */     MCH_EntityGunner mCH_EntityGunner;
/*     */     MCH_EntitySeat mCH_EntitySeat;
/*     */     MCH_EntityAircraft mCH_EntityAircraft;
/*  56 */     ItemStack itemstack = player.func_184586_b(handIn);
/*  57 */     float f = 1.0F;
/*  58 */     float pitch = player.field_70127_C + (player.field_70125_A - player.field_70127_C) * f;
/*  59 */     float yaw = player.field_70126_B + (player.field_70177_z - player.field_70126_B) * f;
/*  60 */     double dx = player.field_70169_q + (player.field_70165_t - player.field_70169_q) * f;
/*     */     
/*  62 */     double dy = player.field_70167_r + (player.field_70163_u - player.field_70167_r) * f + player.func_70047_e();
/*  63 */     double dz = player.field_70166_s + (player.field_70161_v - player.field_70166_s) * f;
/*  64 */     Vec3d vec3 = new Vec3d(dx, dy, dz);
/*  65 */     float f3 = MathHelper.func_76134_b(-yaw * 0.017453292F - 3.1415927F);
/*  66 */     float f4 = MathHelper.func_76126_a(-yaw * 0.017453292F - 3.1415927F);
/*  67 */     float f5 = -MathHelper.func_76134_b(-pitch * 0.017453292F);
/*  68 */     float f6 = MathHelper.func_76126_a(-pitch * 0.017453292F);
/*  69 */     float f7 = f4 * f5;
/*  70 */     float f8 = f3 * f5;
/*  71 */     double d3 = 5.0D;
/*  72 */     Vec3d vec31 = vec3.func_72441_c(f7 * d3, f6 * d3, f8 * d3);
/*     */     
/*  74 */     List<MCH_EntityGunner> list = world.func_72872_a(MCH_EntityGunner.class, player
/*  75 */         .func_174813_aQ().func_72314_b(5.0D, 5.0D, 5.0D));
/*  76 */     Entity target = null;
/*     */     
/*  78 */     for (int i = 0; i < list.size(); i++) {
/*     */       
/*  80 */       MCH_EntityGunner gunner = list.get(i);
/*     */ 
/*     */       
/*  83 */       if (gunner.func_174813_aQ().func_72327_a(vec3, vec31) != null)
/*     */       {
/*  85 */         if (target == null || player.func_70068_e((Entity)gunner) < player.func_70068_e(target))
/*     */         {
/*  87 */           mCH_EntityGunner = gunner;
/*     */         }
/*     */       }
/*     */     } 
/*     */     
/*  92 */     if (mCH_EntityGunner == null) {
/*     */ 
/*     */       
/*  95 */       List<MCH_EntitySeat> list1 = world.func_72872_a(MCH_EntitySeat.class, player
/*  96 */           .func_174813_aQ().func_72314_b(5.0D, 5.0D, 5.0D));
/*     */ 
/*     */       
/*  99 */       for (int j = 0; j < list1.size(); j++) {
/*     */ 
/*     */         
/* 102 */         MCH_EntitySeat seat = list1.get(j);
/*     */ 
/*     */         
/* 105 */         if (seat.getParent() != null && seat.getParent().getAcInfo() != null && seat
/* 106 */           .func_174813_aQ().func_72327_a(vec3, vec31) != null)
/*     */         {
/* 108 */           if (mCH_EntityGunner == null || player.func_70068_e((Entity)seat) < player.func_70068_e((Entity)mCH_EntityGunner))
/*     */           {
/*     */             
/* 111 */             if (seat.getRiddenByEntity() instanceof MCH_EntityGunner) {
/*     */ 
/*     */               
/* 114 */               Entity entity = seat.getRiddenByEntity();
/*     */             }
/*     */             else {
/*     */               
/* 118 */               mCH_EntitySeat = seat;
/*     */             } 
/*     */           }
/*     */         }
/*     */       } 
/*     */     } 
/*     */     
/* 125 */     if (mCH_EntitySeat == null) {
/*     */ 
/*     */       
/* 128 */       List<MCH_EntityAircraft> list2 = world.func_72872_a(MCH_EntityAircraft.class, player
/* 129 */           .func_174813_aQ().func_72314_b(5.0D, 5.0D, 5.0D));
/*     */ 
/*     */       
/* 132 */       for (int j = 0; j < list2.size(); j++) {
/*     */ 
/*     */         
/* 135 */         MCH_EntityAircraft ac = list2.get(j);
/*     */ 
/*     */         
/* 138 */         if (!ac.isUAV() && ac.getAcInfo() != null && ac
/* 139 */           .func_174813_aQ().func_72327_a(vec3, vec31) != null)
/*     */         {
/* 141 */           if (mCH_EntitySeat == null || player.func_70068_e((Entity)ac) < player.func_70068_e((Entity)mCH_EntitySeat))
/*     */           {
/* 143 */             if (ac.getRiddenByEntity() instanceof MCH_EntityGunner) {
/*     */               
/* 145 */               Entity entity = ac.getRiddenByEntity();
/*     */             }
/*     */             else {
/*     */               
/* 149 */               mCH_EntityAircraft = ac;
/*     */             } 
/*     */           }
/*     */         }
/*     */       } 
/*     */     } 
/*     */     
/* 156 */     if (mCH_EntityAircraft instanceof MCH_EntityGunner) {
/*     */ 
/*     */       
/* 159 */       mCH_EntityAircraft.func_184230_a(player, handIn);
/*     */       
/* 161 */       return ActionResult.newResult(EnumActionResult.SUCCESS, itemstack);
/*     */     } 
/*     */     
/* 164 */     if (this.targetType == 1 && !world.field_72995_K && player.func_96124_cp() == null) {
/*     */ 
/*     */       
/* 167 */       player.func_145747_a((ITextComponent)new TextComponentString("You are not on team."));
/*     */       
/* 169 */       return ActionResult.newResult(EnumActionResult.FAIL, itemstack);
/*     */     } 
/*     */     
/* 172 */     if (mCH_EntityAircraft == null) {
/*     */       
/* 174 */       if (!world.field_72995_K)
/*     */       {
/*     */         
/* 177 */         player.func_145747_a((ITextComponent)new TextComponentString("Right click to seat."));
/*     */       }
/*     */       
/* 180 */       return ActionResult.newResult(EnumActionResult.FAIL, itemstack);
/*     */     } 
/*     */     
/* 183 */     if (!world.field_72995_K) {
/*     */       
/* 185 */       MCH_EntityGunner gunner = new MCH_EntityGunner(world, ((Entity)mCH_EntityAircraft).field_70165_t, ((Entity)mCH_EntityAircraft).field_70163_u, ((Entity)mCH_EntityAircraft).field_70161_v);
/*     */       
/* 187 */       gunner.field_70177_z = (((MathHelper.func_76128_c((player.field_70177_z * 4.0F / 360.0F) + 0.5D) & 0x3) - 1) * 90);
/* 188 */       gunner.isCreative = player.field_71075_bZ.field_75098_d;
/* 189 */       gunner.targetType = this.targetType;
/* 190 */       gunner.ownerUUID = player.func_110124_au().toString();
/*     */ 
/*     */       
/* 193 */       ScorePlayerTeam team = world.func_96441_U().func_96509_i(player.func_145748_c_().func_150254_d());
/*     */       
/* 195 */       if (team != null)
/*     */       {
/* 197 */         gunner.setTeamName(team.func_96661_b());
/*     */       }
/*     */       
/* 200 */       world.func_72838_d((Entity)gunner);
/*     */       
/* 202 */       gunner.func_184220_m((Entity)mCH_EntityAircraft);
/*     */       
/* 204 */       W_WorldFunc.MOD_playSoundAtEntity((Entity)gunner, "wrench", 1.0F, 3.0F);
/*     */       
/* 206 */       MCH_EntityAircraft ac = (mCH_EntityAircraft instanceof MCH_EntityAircraft) ? mCH_EntityAircraft : ((MCH_EntitySeat)mCH_EntityAircraft).getParent();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 212 */       String teamPlayerName = ScorePlayerTeam.func_96667_a(player.func_96124_cp(), player.func_145748_c_().func_150254_d());
/* 213 */       String displayName = TextFormatting.GOLD + (ac.getAcInfo()).displayName + TextFormatting.RESET;
/* 214 */       int seatNo = ac.getSeatIdByEntity((Entity)gunner) + 1;
/* 215 */       if (MCH_MOD.isTodaySep01()) {
/*     */         
/* 217 */         String msg = "Hi " + teamPlayerName + "! I sat in the " + seatNo + " seat of " + displayName + "!";
/* 218 */         player.func_145747_a((ITextComponent)new TextComponentTranslation("chat.type.text", new Object[] { "EMB4", new TextComponentString(msg) }));
/*     */       }
/*     */       else {
/*     */         
/* 222 */         player.func_145747_a((ITextComponent)new TextComponentString("The gunner was put on " + displayName + " seat " + seatNo + " by " + teamPlayerName));
/*     */       } 
/*     */     } 
/*     */ 
/*     */     
/* 227 */     if (!player.field_71075_bZ.field_75098_d)
/*     */     {
/*     */       
/* 230 */       itemstack.func_190918_g(1);
/*     */     }
/*     */ 
/*     */     
/* 234 */     return ActionResult.newResult(EnumActionResult.SUCCESS, itemstack);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @SideOnly(Side.CLIENT)
/*     */   public static int getColorFromItemStack(ItemStack stack, int tintIndex) {
/* 245 */     MCH_ItemSpawnGunner item = (MCH_ItemSpawnGunner)stack.func_77973_b();
/* 246 */     return (tintIndex == 0) ? item.primaryColor : item.secondaryColor;
/*     */   }
/*     */ }


/* Location:              C:\Users\leo\Downloads\Mchelioreal\!\mcheli\mob\MCH_ItemSpawnGunner.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */