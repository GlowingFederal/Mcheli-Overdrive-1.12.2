/*     */ package mcheli;
/*     */ 
/*     */ import javax.annotation.Nullable;
/*     */ import mcheli.wrapper.W_ItemArmor;
/*     */ import net.minecraft.client.model.ModelBiped;
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.entity.EntityLivingBase;
/*     */ import net.minecraft.inventory.EntityEquipmentSlot;
/*     */ import net.minecraft.item.ItemStack;
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
/*     */ public class MCH_ItemArmor
/*     */   extends W_ItemArmor
/*     */ {
/*     */   public static final String HELMET_TEXTURE = "mcheli:textures/helicopters/ah-64.png";
/*     */   public static final String CHESTPLATE_TEXTURE = "mcheli:textures/armor/plate.png";
/*     */   public static final String LEGGINGS_TEXTURE = "mcheli:textures/armor/leg.png";
/*     */   public static final String BOOTS_TEXTURE = "mcheli:textures/armor/boots.png";
/*     */   
/*     */   public MCH_ItemArmor(int par1, int par3, int par4) {
/*  29 */     super(par1, par3, par4);
/*     */   }
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
/*     */   public String getArmorTexture(ItemStack stack, Entity entity, EntityEquipmentSlot slot, String type) {
/*  55 */     if (slot == EntityEquipmentSlot.HEAD)
/*     */     {
/*  57 */       return "mcheli:textures/helicopters/ah-64.png";
/*     */     }
/*  59 */     if (slot == EntityEquipmentSlot.CHEST)
/*     */     {
/*  61 */       return "mcheli:textures/armor/plate.png";
/*     */     }
/*  63 */     if (slot == EntityEquipmentSlot.LEGS)
/*     */     {
/*  65 */       return "mcheli:textures/armor/leg.png";
/*     */     }
/*  67 */     if (slot == EntityEquipmentSlot.FEET)
/*     */     {
/*  69 */       return "mcheli:textures/armor/boots.png";
/*     */     }
/*  71 */     return "none";
/*     */   }
/*     */   
/*  74 */   public static MCH_TEST_ModelBiped model = null;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   @SideOnly(Side.CLIENT)
/*     */   public ModelBiped getArmorModel(EntityLivingBase entityLiving, ItemStack itemStack, EntityEquipmentSlot armorSlot, ModelBiped _default) {
/*  96 */     if (model == null)
/*     */     {
/*  98 */       model = new MCH_TEST_ModelBiped();
/*     */     }
/* 100 */     if (armorSlot == EntityEquipmentSlot.HEAD)
/*     */     {
/* 102 */       return model;
/*     */     }
/* 104 */     return null;
/*     */   }
/*     */ }


/* Location:              C:\Users\leo\Downloads\Mchelioreal\!\mcheli\MCH_ItemArmor.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */