/*     */ package mcheli.uav;
/*     */ 
/*     */ import java.util.List;
/*     */ import mcheli.MCH_Lib;
/*     */ import mcheli.wrapper.W_Item;
/*     */ import mcheli.wrapper.W_MovingObjectPosition;
/*     */ import mcheli.wrapper.W_WorldFunc;
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraft.util.ActionResult;
/*     */ import net.minecraft.util.EnumActionResult;
/*     */ import net.minecraft.util.EnumHand;
/*     */ import net.minecraft.util.math.AxisAlignedBB;
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
/*     */ public class MCH_ItemUavStation
/*     */   extends W_Item
/*     */ {
/*  30 */   public static int UAV_STATION_KIND_NUM = 2;
/*     */   
/*     */   public final int UavStationKind;
/*     */   
/*     */   public MCH_ItemUavStation(int par1, int kind) {
/*  35 */     super(par1);
/*  36 */     this.field_77777_bU = 1;
/*  37 */     this.UavStationKind = kind;
/*     */   }
/*     */ 
/*     */   
/*     */   public MCH_EntityUavStation createUavStation(World world, double x, double y, double z, int kind) {
/*  42 */     MCH_EntityUavStation uavst = new MCH_EntityUavStation(world);
/*     */ 
/*     */     
/*  45 */     uavst.func_70107_b(x, y, z);
/*  46 */     uavst.field_70169_q = x;
/*  47 */     uavst.field_70167_r = y;
/*  48 */     uavst.field_70166_s = z;
/*  49 */     uavst.setKind(kind);
/*     */     
/*  51 */     return uavst;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ActionResult<ItemStack> func_77659_a(World worldIn, EntityPlayer playerIn, EnumHand handIn) {
/*  58 */     ItemStack itemstack = playerIn.func_184586_b(handIn);
/*  59 */     float f = 1.0F;
/*  60 */     float f1 = playerIn.field_70127_C + (playerIn.field_70125_A - playerIn.field_70127_C) * f;
/*  61 */     float f2 = playerIn.field_70126_B + (playerIn.field_70177_z - playerIn.field_70126_B) * f;
/*  62 */     double d0 = playerIn.field_70169_q + (playerIn.field_70165_t - playerIn.field_70169_q) * f;
/*     */     
/*  64 */     double d1 = playerIn.field_70167_r + (playerIn.field_70163_u - playerIn.field_70167_r) * f + 1.62D;
/*  65 */     double d2 = playerIn.field_70166_s + (playerIn.field_70161_v - playerIn.field_70166_s) * f;
/*  66 */     Vec3d vec3 = W_WorldFunc.getWorldVec3(worldIn, d0, d1, d2);
/*  67 */     float f3 = MathHelper.func_76134_b(-f2 * 0.017453292F - 3.1415927F);
/*  68 */     float f4 = MathHelper.func_76126_a(-f2 * 0.017453292F - 3.1415927F);
/*  69 */     float f5 = -MathHelper.func_76134_b(-f1 * 0.017453292F);
/*  70 */     float f6 = MathHelper.func_76126_a(-f1 * 0.017453292F);
/*  71 */     float f7 = f4 * f5;
/*  72 */     float f8 = f3 * f5;
/*  73 */     double d3 = 5.0D;
/*  74 */     Vec3d vec31 = vec3.func_72441_c(f7 * d3, f6 * d3, f8 * d3);
/*     */     
/*  76 */     RayTraceResult movingobjectposition = W_WorldFunc.clip(worldIn, vec3, vec31, true);
/*     */     
/*  78 */     if (movingobjectposition == null)
/*     */     {
/*     */       
/*  81 */       return ActionResult.newResult(EnumActionResult.PASS, itemstack);
/*     */     }
/*     */     
/*  84 */     Vec3d vec32 = playerIn.func_70676_i(f);
/*  85 */     boolean flag = false;
/*  86 */     float f9 = 1.0F;
/*     */     
/*  88 */     List<Entity> list = worldIn.func_72839_b((Entity)playerIn, playerIn
/*  89 */         .func_174813_aQ().func_72321_a(vec32.field_72450_a * d3, vec32.field_72448_b * d3, vec32.field_72449_c * d3).func_72314_b(f9, f9, f9));
/*     */     int i;
/*  91 */     for (i = 0; i < list.size(); i++) {
/*     */       
/*  93 */       Entity entity = list.get(i);
/*     */       
/*  95 */       if (entity.func_70067_L()) {
/*     */         
/*  97 */         float f10 = entity.func_70111_Y();
/*     */         
/*  99 */         AxisAlignedBB axisalignedbb = entity.func_174813_aQ().func_72314_b(f10, f10, f10);
/*     */         
/* 101 */         if (axisalignedbb.func_72318_a(vec3))
/*     */         {
/* 103 */           flag = true;
/*     */         }
/*     */       } 
/*     */     } 
/*     */     
/* 108 */     if (flag)
/*     */     {
/*     */       
/* 111 */       return ActionResult.newResult(EnumActionResult.FAIL, itemstack);
/*     */     }
/*     */     
/* 114 */     if (W_MovingObjectPosition.isHitTypeTile(movingobjectposition)) {
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 119 */       i = movingobjectposition.func_178782_a().func_177958_n();
/* 120 */       int j = movingobjectposition.func_178782_a().func_177956_o();
/* 121 */       int k = movingobjectposition.func_178782_a().func_177952_p();
/*     */       
/* 123 */       MCH_EntityUavStation entityUavSt = createUavStation(worldIn, (i + 0.5F), (j + 1.0F), (k + 0.5F), this.UavStationKind);
/*     */       
/* 125 */       int rot = (int)(MCH_Lib.getRotate360(playerIn.field_70177_z) + 45.0D);
/*     */       
/* 127 */       entityUavSt.field_70177_z = (rot / 90 * 90 - 180);
/* 128 */       entityUavSt.initUavPostion();
/*     */ 
/*     */ 
/*     */       
/* 132 */       if (!worldIn.func_184144_a((Entity)entityUavSt, entityUavSt.func_174813_aQ().func_72314_b(-0.1D, -0.1D, -0.1D)).isEmpty())
/*     */       {
/*     */         
/* 135 */         return ActionResult.newResult(EnumActionResult.FAIL, itemstack);
/*     */       }
/*     */       
/* 138 */       if (!worldIn.field_72995_K)
/*     */       {
/* 140 */         worldIn.func_72838_d((Entity)entityUavSt);
/*     */       }
/*     */       
/* 143 */       if (!playerIn.field_71075_bZ.field_75098_d)
/*     */       {
/*     */         
/* 146 */         itemstack.func_190918_g(1);
/*     */       }
/*     */     } 
/*     */ 
/*     */     
/* 151 */     return ActionResult.newResult(EnumActionResult.SUCCESS, itemstack);
/*     */   }
/*     */ }


/* Location:              C:\Users\leo\Downloads\Mchelioreal\!\mchel\\uav\MCH_ItemUavStation.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */