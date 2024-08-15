/*     */ package mcheli.lweapon;
/*     */ 
/*     */ import javax.annotation.Nullable;
/*     */ import mcheli.wrapper.W_Item;
/*     */ import net.minecraft.entity.EntityLivingBase;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.init.MobEffects;
/*     */ import net.minecraft.item.EnumAction;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraft.potion.PotionEffect;
/*     */ import net.minecraft.util.ActionResult;
/*     */ import net.minecraft.util.EnumActionResult;
/*     */ import net.minecraft.util.EnumHand;
/*     */ import net.minecraft.world.World;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class MCH_ItemLightWeaponBase
/*     */   extends W_Item
/*     */ {
/*     */   public final MCH_ItemLightWeaponBullet bullet;
/*     */   
/*     */   public MCH_ItemLightWeaponBase(int par1, MCH_ItemLightWeaponBullet bullet) {
/*  29 */     super(par1);
/*     */     
/*  31 */     func_77656_e(10);
/*  32 */     func_77625_d(1);
/*  33 */     this.bullet = bullet;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static String getName(ItemStack itemStack) {
/*  39 */     if (!itemStack.func_190926_b() && itemStack.func_77973_b() instanceof MCH_ItemLightWeaponBase) {
/*     */       
/*  41 */       String name = itemStack.func_77977_a();
/*  42 */       int li = name.lastIndexOf(":");
/*     */       
/*  44 */       if (li >= 0)
/*     */       {
/*  46 */         name = name.substring(li + 1);
/*     */       }
/*     */       
/*  49 */       return name;
/*     */     } 
/*  51 */     return "";
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static boolean isHeld(@Nullable EntityPlayer player) {
/*  57 */     ItemStack is = (player != null) ? player.func_184614_ca() : ItemStack.field_190927_a;
/*     */ 
/*     */     
/*  60 */     if (!is.func_190926_b() && is.func_77973_b() instanceof MCH_ItemLightWeaponBase)
/*     */     {
/*     */       
/*  63 */       return (player.func_184612_cw() > 10);
/*     */     }
/*     */     
/*  66 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void onUsingTick(ItemStack stack, EntityLivingBase player, int count) {
/*  74 */     PotionEffect pe = player.func_70660_b(MobEffects.field_76439_r);
/*     */     
/*  76 */     if (pe != null && pe.func_76459_b() < 220)
/*     */     {
/*     */       
/*  79 */       player.func_70690_d(new PotionEffect(MobEffects.field_76439_r, 250, 0, false, false));
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean onEntitySwing(EntityLivingBase entityLiving, ItemStack stack) {
/*  86 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public EnumAction func_77661_b(ItemStack par1ItemStack) {
/*  93 */     return EnumAction.BOW;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public int func_77626_a(ItemStack par1ItemStack) {
/*  99 */     return 72000;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ActionResult<ItemStack> func_77659_a(World worldIn, EntityPlayer playerIn, EnumHand handIn) {
/* 106 */     ItemStack itemstack = playerIn.func_184586_b(handIn);
/*     */ 
/*     */     
/* 109 */     if (!itemstack.func_190926_b())
/*     */     {
/*     */       
/* 112 */       playerIn.func_184598_c(handIn);
/*     */     }
/*     */ 
/*     */     
/* 116 */     return ActionResult.newResult(EnumActionResult.SUCCESS, itemstack);
/*     */   }
/*     */ }


/* Location:              C:\Users\leo\Downloads\Mchelioreal\!\mcheli\lweapon\MCH_ItemLightWeaponBase.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */