/*     */ package mcheli.aircraft;
/*     */ 
/*     */ import java.util.List;
/*     */ import javax.annotation.Nullable;
/*     */ import mcheli.MCH_Config;
/*     */ import mcheli.__helper.MCH_CriteriaTriggers;
/*     */ import mcheli.wrapper.W_EntityPlayer;
/*     */ import mcheli.wrapper.W_Item;
/*     */ import mcheli.wrapper.W_MovingObjectPosition;
/*     */ import mcheli.wrapper.W_WorldFunc;
/*     */ import net.minecraft.block.Block;
/*     */ import net.minecraft.block.BlockDispenser;
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.entity.player.EntityPlayerMP;
/*     */ import net.minecraft.init.Blocks;
/*     */ import net.minecraft.item.Item;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraft.util.ActionResult;
/*     */ import net.minecraft.util.EnumActionResult;
/*     */ import net.minecraft.util.EnumHand;
/*     */ import net.minecraft.util.math.AxisAlignedBB;
/*     */ import net.minecraft.util.math.BlockPos;
/*     */ import net.minecraft.util.math.MathHelper;
/*     */ import net.minecraft.util.math.RayTraceResult;
/*     */ import net.minecraft.util.math.Vec3d;
/*     */ import net.minecraft.world.World;
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
/*     */ 
/*     */ 
/*     */ public abstract class MCH_ItemAircraft
/*     */   extends W_Item
/*     */ {
/*     */   public MCH_ItemAircraft(int i) {
/*  44 */     super(i);
/*     */   }
/*     */ 
/*     */   
/*     */   private static boolean isRegistedDispenseBehavior = false;
/*     */   
/*     */   public static void registerDispenseBehavior(Item item) {
/*  51 */     if (isRegistedDispenseBehavior == true) {
/*     */       return;
/*     */     }
/*     */ 
/*     */     
/*  56 */     BlockDispenser.field_149943_a.func_82595_a(item, new MCH_ItemAircraftDispenseBehavior());
/*     */   }
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   public abstract MCH_AircraftInfo getAircraftInfo();
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   public abstract MCH_EntityAircraft createAircraft(World paramWorld, double paramDouble1, double paramDouble2, double paramDouble3, ItemStack paramItemStack);
/*     */   
/*     */   public MCH_EntityAircraft onTileClick(ItemStack itemStack, World world, float rotationYaw, int x, int y, int z) {
/*  68 */     MCH_EntityAircraft ac = createAircraft(world, (x + 0.5F), (y + 1.0F), (z + 0.5F), itemStack);
/*     */     
/*  70 */     if (ac == null)
/*     */     {
/*  72 */       return null;
/*     */     }
/*     */     
/*  75 */     ac.initRotationYaw((((MathHelper.func_76128_c((rotationYaw * 4.0F / 360.0F) + 0.5D) & 0x3) - 1) * 90));
/*     */ 
/*     */     
/*  78 */     if (!world.func_184144_a((Entity)ac, ac.func_174813_aQ().func_72314_b(-0.1D, -0.1D, -0.1D)).isEmpty())
/*     */     {
/*  80 */       return null;
/*     */     }
/*     */     
/*  83 */     return ac;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public String toString() {
/*  89 */     MCH_AircraftInfo info = getAircraftInfo();
/*  90 */     if (info != null)
/*     */     {
/*  92 */       return super.toString() + "(" + info.getDirectoryName() + ":" + info.name + ")";
/*     */     }
/*     */     
/*  95 */     return super.toString() + "(null)";
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ActionResult<ItemStack> func_77659_a(World world, EntityPlayer player, EnumHand handIn) {
/* 102 */     ItemStack itemstack = player.func_184586_b(handIn);
/* 103 */     float f = 1.0F;
/* 104 */     float f1 = player.field_70127_C + (player.field_70125_A - player.field_70127_C) * f;
/* 105 */     float f2 = player.field_70126_B + (player.field_70177_z - player.field_70126_B) * f;
/* 106 */     double d0 = player.field_70169_q + (player.field_70165_t - player.field_70169_q) * f;
/*     */     
/* 108 */     double d1 = player.field_70167_r + (player.field_70163_u - player.field_70167_r) * f + 1.62D;
/* 109 */     double d2 = player.field_70166_s + (player.field_70161_v - player.field_70166_s) * f;
/* 110 */     Vec3d vec3 = W_WorldFunc.getWorldVec3(world, d0, d1, d2);
/* 111 */     float f3 = MathHelper.func_76134_b(-f2 * 0.017453292F - 3.1415927F);
/* 112 */     float f4 = MathHelper.func_76126_a(-f2 * 0.017453292F - 3.1415927F);
/* 113 */     float f5 = -MathHelper.func_76134_b(-f1 * 0.017453292F);
/* 114 */     float f6 = MathHelper.func_76126_a(-f1 * 0.017453292F);
/* 115 */     float f7 = f4 * f5;
/* 116 */     float f8 = f3 * f5;
/* 117 */     double d3 = 5.0D;
/* 118 */     Vec3d vec31 = vec3.func_72441_c(f7 * d3, f6 * d3, f8 * d3);
/*     */     
/* 120 */     RayTraceResult mop = W_WorldFunc.clip(world, vec3, vec31, true);
/*     */     
/* 122 */     if (mop == null)
/*     */     {
/*     */       
/* 125 */       return ActionResult.newResult(EnumActionResult.PASS, itemstack);
/*     */     }
/*     */     
/* 128 */     Vec3d vec32 = player.func_70676_i(f);
/* 129 */     boolean flag = false;
/* 130 */     float f9 = 1.0F;
/*     */ 
/*     */     
/* 133 */     List<Entity> list = world.func_72839_b((Entity)player, player
/* 134 */         .func_174813_aQ().func_72321_a(vec32.field_72450_a * d3, vec32.field_72448_b * d3, vec32.field_72449_c * d3).func_72314_b(f9, f9, f9));
/*     */     
/* 136 */     for (int i = 0; i < list.size(); i++) {
/*     */       
/* 138 */       Entity entity = list.get(i);
/*     */       
/* 140 */       if (entity.func_70067_L()) {
/*     */         
/* 142 */         float f10 = entity.func_70111_Y();
/*     */         
/* 144 */         AxisAlignedBB axisalignedbb = entity.func_174813_aQ().func_72314_b(f10, f10, f10);
/*     */         
/* 146 */         if (axisalignedbb.func_72318_a(vec3))
/*     */         {
/* 148 */           flag = true;
/*     */         }
/*     */       } 
/*     */     } 
/*     */     
/* 153 */     if (flag)
/*     */     {
/*     */       
/* 156 */       return ActionResult.newResult(EnumActionResult.PASS, itemstack);
/*     */     }
/*     */     
/* 159 */     if (W_MovingObjectPosition.isHitTypeTile(mop)) {
/*     */       
/* 161 */       if (MCH_Config.PlaceableOnSpongeOnly.prmBool) {
/*     */         
/* 163 */         MCH_AircraftInfo acInfo = getAircraftInfo();
/*     */         
/* 165 */         if (acInfo != null && acInfo.isFloat && !acInfo.canMoveOnGround) {
/*     */ 
/*     */           
/* 168 */           if (world.func_180495_p(mop.func_178782_a().func_177977_b()).func_177230_c() != Blocks.field_150360_v)
/*     */           {
/*     */             
/* 171 */             return ActionResult.newResult(EnumActionResult.FAIL, itemstack);
/*     */           }
/*     */           
/* 174 */           for (int x = -1; x <= 1; x++)
/*     */           {
/* 176 */             for (int z = -1; z <= 1; z++)
/*     */             {
/*     */               
/* 179 */               if (world.func_180495_p(mop.func_178782_a().func_177982_a(x, 0, z)).func_177230_c() != Blocks.field_150355_j)
/*     */               {
/* 181 */                 return ActionResult.newResult(EnumActionResult.FAIL, itemstack);
/*     */               }
/*     */             }
/*     */           
/*     */           }
/*     */         
/*     */         } else {
/*     */           
/* 189 */           Block block = world.func_180495_p(mop.func_178782_a()).func_177230_c();
/*     */           
/* 191 */           if (!(block instanceof net.minecraft.block.BlockSponge))
/*     */           {
/*     */             
/* 194 */             return ActionResult.newResult(EnumActionResult.FAIL, itemstack);
/*     */           }
/*     */         } 
/*     */       } 
/*     */       
/* 199 */       spawnAircraft(itemstack, world, player, mop.func_178782_a());
/*     */     } 
/*     */ 
/*     */     
/* 203 */     return ActionResult.newResult(EnumActionResult.SUCCESS, itemstack);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public MCH_EntityAircraft spawnAircraft(ItemStack itemStack, World world, EntityPlayer player, BlockPos blockpos) {
/* 210 */     MCH_EntityAircraft ac = onTileClick(itemStack, world, player.field_70177_z, blockpos.func_177958_n(), blockpos.func_177956_o(), blockpos
/* 211 */         .func_177952_p());
/*     */     
/* 213 */     if (ac != null) {
/*     */       
/* 215 */       if (ac.getAcInfo() != null && (ac.getAcInfo()).creativeOnly && !player.field_71075_bZ.field_75098_d)
/*     */       {
/* 217 */         return null;
/*     */       }
/*     */       
/* 220 */       if (ac.isUAV()) {
/*     */         
/* 222 */         if (world.field_72995_K)
/*     */         {
/* 224 */           if (ac.isSmallUAV()) {
/*     */             
/* 226 */             W_EntityPlayer.addChatMessage(player, "Please use the UAV station OR Portable Controller");
/*     */           }
/*     */           else {
/*     */             
/* 230 */             W_EntityPlayer.addChatMessage(player, "Please use the UAV station");
/*     */           } 
/*     */         }
/* 233 */         ac = null;
/*     */       }
/*     */       else {
/*     */         
/* 237 */         if (!world.field_72995_K) {
/*     */           
/* 239 */           ac.getAcDataFromItem(itemStack);
/* 240 */           world.func_72838_d((Entity)ac);
/*     */ 
/*     */           
/* 243 */           MCH_CriteriaTriggers.PUT_AIRCRAFT.trigger((EntityPlayerMP)player);
/*     */         } 
/*     */         
/* 246 */         if (!player.field_71075_bZ.field_75098_d)
/*     */         {
/*     */           
/* 249 */           itemStack.func_190918_g(1);
/*     */         }
/*     */       } 
/*     */     } 
/*     */     
/* 254 */     return ac;
/*     */   }
/*     */ 
/*     */   
/*     */   public void rideEntity(ItemStack item, Entity target, EntityPlayer player) {
/* 259 */     if (!MCH_Config.PlaceableOnSpongeOnly.prmBool)
/*     */     {
/*     */       
/* 262 */       if (target instanceof net.minecraft.entity.item.EntityMinecartEmpty && target.func_184188_bt().isEmpty()) {
/*     */ 
/*     */         
/* 265 */         BlockPos blockpos = new BlockPos((int)target.field_70165_t, (int)target.field_70163_u + 2, (int)target.field_70161_v);
/* 266 */         MCH_EntityAircraft ac = spawnAircraft(item, player.field_70170_p, player, blockpos);
/*     */         
/* 268 */         if (!player.field_70170_p.field_72995_K && ac != null)
/*     */         {
/*     */           
/* 271 */           ac.func_184220_m(target);
/*     */         }
/*     */       } 
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\leo\Downloads\Mchelioreal\!\mcheli\aircraft\MCH_ItemAircraft.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */