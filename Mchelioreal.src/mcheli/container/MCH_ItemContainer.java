/*     */ package mcheli.container;
/*     */ 
/*     */ import java.util.List;
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
/*     */ 
/*     */ public class MCH_ItemContainer
/*     */   extends W_Item
/*     */ {
/*     */   public MCH_ItemContainer(int par1) {
/*  31 */     super(par1);
/*     */     
/*  33 */     func_77625_d(1);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ActionResult<ItemStack> func_77659_a(World worldIn, EntityPlayer playerIn, EnumHand handIn) {
/*  40 */     ItemStack itemstack = playerIn.func_184586_b(handIn);
/*  41 */     float f = 1.0F;
/*  42 */     float f1 = playerIn.field_70127_C + (playerIn.field_70125_A - playerIn.field_70127_C) * f;
/*  43 */     float f2 = playerIn.field_70126_B + (playerIn.field_70177_z - playerIn.field_70126_B) * f;
/*  44 */     double d0 = playerIn.field_70169_q + (playerIn.field_70165_t - playerIn.field_70169_q) * f;
/*     */     
/*  46 */     double d1 = playerIn.field_70167_r + (playerIn.field_70163_u - playerIn.field_70167_r) * f + playerIn.func_70047_e();
/*  47 */     double d2 = playerIn.field_70166_s + (playerIn.field_70161_v - playerIn.field_70166_s) * f;
/*  48 */     Vec3d vec3 = W_WorldFunc.getWorldVec3(worldIn, d0, d1, d2);
/*  49 */     float f3 = MathHelper.func_76134_b(-f2 * 0.017453292F - 3.1415927F);
/*  50 */     float f4 = MathHelper.func_76126_a(-f2 * 0.017453292F - 3.1415927F);
/*  51 */     float f5 = -MathHelper.func_76134_b(-f1 * 0.017453292F);
/*  52 */     float f6 = MathHelper.func_76126_a(-f1 * 0.017453292F);
/*  53 */     float f7 = f4 * f5;
/*  54 */     float f8 = f3 * f5;
/*  55 */     double d3 = 5.0D;
/*  56 */     Vec3d vec31 = vec3.func_72441_c(f7 * d3, f6 * d3, f8 * d3);
/*     */     
/*  58 */     RayTraceResult movingobjectposition = W_WorldFunc.clip(worldIn, vec3, vec31, true);
/*     */     
/*  60 */     if (movingobjectposition == null)
/*     */     {
/*     */       
/*  63 */       return ActionResult.newResult(EnumActionResult.PASS, itemstack);
/*     */     }
/*     */     
/*  66 */     Vec3d vec32 = playerIn.func_70676_i(f);
/*  67 */     boolean flag = false;
/*  68 */     float f9 = 1.0F;
/*     */ 
/*     */     
/*  71 */     List<Entity> list = worldIn.func_72839_b((Entity)playerIn, playerIn
/*  72 */         .func_174813_aQ().func_72321_a(vec32.field_72450_a * d3, vec32.field_72448_b * d3, vec32.field_72449_c * d3).func_72314_b(f9, f9, f9));
/*     */     int i;
/*  74 */     for (i = 0; i < list.size(); i++) {
/*     */       
/*  76 */       Entity entity = list.get(i);
/*     */       
/*  78 */       if (entity.func_70067_L()) {
/*     */         
/*  80 */         float f10 = entity.func_70111_Y();
/*     */         
/*  82 */         AxisAlignedBB axisalignedbb = entity.func_174813_aQ().func_72314_b(f10, f10, f10);
/*     */         
/*  84 */         if (axisalignedbb.func_72318_a(vec3))
/*     */         {
/*  86 */           flag = true;
/*     */         }
/*     */       } 
/*     */     } 
/*     */     
/*  91 */     if (flag)
/*     */     {
/*     */       
/*  94 */       return ActionResult.newResult(EnumActionResult.FAIL, itemstack);
/*     */     }
/*     */     
/*  97 */     if (W_MovingObjectPosition.isHitTypeTile(movingobjectposition)) {
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 102 */       i = movingobjectposition.func_178782_a().func_177958_n();
/* 103 */       int j = movingobjectposition.func_178782_a().func_177956_o();
/* 104 */       int k = movingobjectposition.func_178782_a().func_177952_p();
/*     */       
/* 106 */       MCH_EntityContainer entityboat = new MCH_EntityContainer(worldIn, (i + 0.5F), (j + 1.0F), (k + 0.5F));
/* 107 */       entityboat.field_70177_z = (((MathHelper.func_76128_c((playerIn.field_70177_z * 4.0F / 360.0F) + 0.5D) & 0x3) - 1) * 90);
/*     */ 
/*     */ 
/*     */       
/* 111 */       if (!worldIn.func_184144_a((Entity)entityboat, entityboat.func_174813_aQ().func_72314_b(-0.1D, -0.1D, -0.1D)).isEmpty())
/*     */       {
/*     */         
/* 114 */         return ActionResult.newResult(EnumActionResult.FAIL, itemstack);
/*     */       }
/*     */       
/* 117 */       if (!worldIn.field_72995_K)
/*     */       {
/* 119 */         worldIn.func_72838_d((Entity)entityboat);
/*     */       }
/*     */       
/* 122 */       if (!playerIn.field_71075_bZ.field_75098_d)
/*     */       {
/*     */         
/* 125 */         itemstack.func_190918_g(1);
/*     */       }
/*     */     } 
/*     */ 
/*     */     
/* 130 */     return ActionResult.newResult(EnumActionResult.SUCCESS, itemstack);
/*     */   }
/*     */ }


/* Location:              C:\Users\leo\Downloads\Mchelioreal\!\mcheli\container\MCH_ItemContainer.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */