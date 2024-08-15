/*     */ package mcheli.throwable;
/*     */ 
/*     */ import mcheli.MCH_Lib;
/*     */ import mcheli.wrapper.W_Item;
/*     */ import net.minecraft.block.BlockDispenser;
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.entity.EntityLivingBase;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.init.SoundEvents;
/*     */ import net.minecraft.item.EnumAction;
/*     */ import net.minecraft.item.Item;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraft.util.ActionResult;
/*     */ import net.minecraft.util.EnumActionResult;
/*     */ import net.minecraft.util.EnumHand;
/*     */ import net.minecraft.util.SoundCategory;
/*     */ import net.minecraft.world.World;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class MCH_ItemThrowable
/*     */   extends W_Item
/*     */ {
/*     */   public MCH_ItemThrowable(int par1) {
/*  28 */     super(par1);
/*  29 */     func_77625_d(1);
/*     */   }
/*     */ 
/*     */   
/*     */   public static void registerDispenseBehavior(Item item) {
/*  34 */     BlockDispenser.field_149943_a.func_82595_a(item, new MCH_ItemThrowableDispenseBehavior());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ActionResult<ItemStack> func_77659_a(World world, EntityPlayer player, EnumHand handIn) {
/*  41 */     ItemStack itemstack = player.func_184586_b(handIn);
/*     */     
/*  43 */     player.func_184598_c(handIn);
/*     */     
/*  45 */     return ActionResult.newResult(EnumActionResult.SUCCESS, itemstack);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void func_77615_a(ItemStack itemStack, World world, EntityLivingBase entityLiving, int par4) {
/*  52 */     if (!(entityLiving instanceof EntityPlayer)) {
/*     */       return;
/*     */     }
/*     */ 
/*     */     
/*  57 */     EntityPlayer player = (EntityPlayer)entityLiving;
/*     */ 
/*     */     
/*  60 */     if (!itemStack.func_190926_b() && itemStack.func_190916_E() > 0) {
/*     */       
/*  62 */       MCH_ThrowableInfo info = MCH_ThrowableInfoManager.get(itemStack.func_77973_b());
/*     */       
/*  64 */       if (info != null) {
/*     */         
/*  66 */         if (!player.field_71075_bZ.field_75098_d) {
/*     */ 
/*     */           
/*  69 */           itemStack.func_190918_g(1);
/*     */ 
/*     */           
/*  72 */           if (itemStack.func_190916_E() <= 0)
/*     */           {
/*     */             
/*  75 */             player.field_71071_by.func_70299_a(player.field_71071_by.field_70461_c, ItemStack.field_190927_a);
/*     */           }
/*     */         } 
/*     */ 
/*     */         
/*  80 */         world.func_184148_a((EntityPlayer)null, player.field_70165_t, player.field_70163_u, player.field_70161_v, SoundEvents.field_187737_v, SoundCategory.PLAYERS, 0.5F, 0.4F / (field_77697_d
/*     */             
/*  82 */             .nextFloat() * 0.4F + 0.8F));
/*     */         
/*  84 */         if (!world.field_72995_K) {
/*     */           
/*  86 */           float acceleration = 1.0F;
/*  87 */           par4 = itemStack.func_77988_m() - par4;
/*     */           
/*  89 */           if (par4 <= 35) {
/*     */             
/*  91 */             if (par4 < 5) {
/*  92 */               par4 = 5;
/*     */             }
/*  94 */             acceleration = par4 / 25.0F;
/*     */           } 
/*     */           
/*  97 */           MCH_Lib.DbgLog(world, "MCH_ItemThrowable.onPlayerStoppedUsing(%d)", new Object[] {
/*     */                 
/*  99 */                 Integer.valueOf(par4)
/*     */               });
/*     */           
/* 102 */           MCH_EntityThrowable entity = new MCH_EntityThrowable(world, (EntityLivingBase)player, acceleration);
/*     */           
/* 104 */           entity.func_184538_a((Entity)player, player.field_70125_A, player.field_70177_z, 0.0F, acceleration, 1.0F);
/* 105 */           entity.setInfo(info);
/* 106 */           world.func_72838_d((Entity)entity);
/*     */         } 
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public int func_77626_a(ItemStack par1ItemStack) {
/* 115 */     return 72000;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public EnumAction func_77661_b(ItemStack par1ItemStack) {
/* 122 */     return EnumAction.BOW;
/*     */   }
/*     */ }


/* Location:              C:\Users\leo\Downloads\Mchelioreal\!\mcheli\throwable\MCH_ItemThrowable.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */