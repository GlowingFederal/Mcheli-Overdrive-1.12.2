/*     */ package mcheli.tool.rangefinder;
/*     */ 
/*     */ import mcheli.aircraft.MCH_EntityAircraft;
/*     */ import mcheli.aircraft.MCH_EntitySeat;
/*     */ import mcheli.multiplay.MCH_PacketIndSpotEntity;
/*     */ import mcheli.wrapper.W_Item;
/*     */ import mcheli.wrapper.W_McClient;
/*     */ import mcheli.wrapper.W_Reflection;
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.entity.EntityLivingBase;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.item.EnumAction;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraft.util.ActionResult;
/*     */ import net.minecraft.util.EnumActionResult;
/*     */ import net.minecraft.util.EnumHand;
/*     */ import net.minecraft.world.World;
/*     */ import net.minecraftforge.fml.relauncher.Side;
/*     */ import net.minecraftforge.fml.relauncher.SideOnly;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class MCH_ItemRangeFinder
/*     */   extends W_Item
/*     */ {
/*  28 */   public static int rangeFinderUseCooldown = 0;
/*     */   public static boolean continueUsingItem = false;
/*  30 */   public static float zoom = 2.0F;
/*     */   
/*  32 */   public static int mode = 0;
/*     */ 
/*     */   
/*     */   public MCH_ItemRangeFinder(int itemId) {
/*  36 */     super(itemId);
/*  37 */     this.field_77777_bU = 1;
/*  38 */     func_77656_e(10);
/*     */   }
/*     */ 
/*     */   
/*     */   public static boolean canUse(EntityPlayer player) {
/*  43 */     if (player == null) {
/*  44 */       return false;
/*     */     }
/*  46 */     if (player.field_70170_p == null)
/*     */     {
/*  48 */       return false;
/*     */     }
/*     */ 
/*     */     
/*  52 */     if (player.func_184614_ca().func_190926_b()) {
/*  53 */       return false;
/*     */     }
/*     */     
/*  56 */     if (!(player.func_184614_ca().func_77973_b() instanceof MCH_ItemRangeFinder))
/*     */     {
/*  58 */       return false;
/*     */     }
/*     */     
/*  61 */     if (player.func_184187_bx() instanceof MCH_EntityAircraft) {
/*  62 */       return false;
/*     */     }
/*  64 */     if (player.func_184187_bx() instanceof MCH_EntitySeat) {
/*     */       
/*  66 */       MCH_EntityAircraft ac = ((MCH_EntitySeat)player.func_184187_bx()).getParent();
/*     */       
/*  68 */       if (ac != null && (ac.getIsGunnerMode((Entity)player) || ac.getWeaponIDBySeatID(ac.getSeatIdByEntity((Entity)player)) >= 0))
/*     */       {
/*  70 */         return false;
/*     */       }
/*     */     } 
/*     */     
/*  74 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static boolean isUsingScope(EntityPlayer player) {
/*  80 */     return (player.func_184612_cw() > 8 || continueUsingItem);
/*     */   }
/*     */ 
/*     */   
/*     */   public static void onStartUseItem() {
/*  85 */     zoom = 2.0F;
/*  86 */     W_Reflection.setCameraZoom(2.0F);
/*  87 */     continueUsingItem = true;
/*     */   }
/*     */ 
/*     */   
/*     */   public static void onStopUseItem() {
/*  92 */     W_Reflection.restoreCameraZoom();
/*  93 */     continueUsingItem = false;
/*     */   }
/*     */ 
/*     */   
/*     */   @SideOnly(Side.CLIENT)
/*     */   public void spotEntity(EntityPlayer player, ItemStack itemStack) {
/*  99 */     if (player != null && player.field_70170_p.field_72995_K)
/*     */     {
/*     */       
/* 102 */       if (rangeFinderUseCooldown == 0 && player.func_184612_cw() > 8)
/*     */       {
/* 104 */         if (mode == 2) {
/*     */           
/* 106 */           rangeFinderUseCooldown = 60;
/* 107 */           MCH_PacketIndSpotEntity.send((EntityLivingBase)player, 0);
/*     */         }
/* 109 */         else if (itemStack.func_77960_j() < itemStack.func_77958_k()) {
/*     */           
/* 111 */           rangeFinderUseCooldown = 60;
/* 112 */           MCH_PacketIndSpotEntity.send((EntityLivingBase)player, (mode == 0) ? 60 : 3);
/*     */         
/*     */         }
/*     */         else {
/*     */           
/* 117 */           W_McClient.MOD_playSoundFX("ng", 1.0F, 1.0F);
/*     */         } 
/*     */       }
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void func_77615_a(ItemStack stack, World worldIn, EntityLivingBase entityLiving, int timeLeft) {
/* 127 */     if (worldIn.field_72995_K)
/*     */     {
/* 129 */       onStopUseItem();
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ItemStack func_77654_b(ItemStack stack, World worldIn, EntityLivingBase entityLiving) {
/* 137 */     return stack;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   @SideOnly(Side.CLIENT)
/*     */   public boolean func_77662_d() {
/* 144 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public EnumAction func_77661_b(ItemStack itemStack) {
/* 151 */     return EnumAction.BOW;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public int func_77626_a(ItemStack itemStack) {
/* 157 */     return 72000;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ActionResult<ItemStack> func_77659_a(World world, EntityPlayer player, EnumHand handIn) {
/* 164 */     ItemStack itemstack = player.func_184586_b(handIn);
/*     */     
/* 166 */     if (canUse(player))
/*     */     {
/*     */       
/* 169 */       player.func_184598_c(handIn);
/*     */     }
/*     */     
/* 172 */     return ActionResult.newResult(EnumActionResult.SUCCESS, itemstack);
/*     */   }
/*     */ }


/* Location:              C:\Users\leo\Downloads\Mchelioreal\!\mcheli\tool\rangefinder\MCH_ItemRangeFinder.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */