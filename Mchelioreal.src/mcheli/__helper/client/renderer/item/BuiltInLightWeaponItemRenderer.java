/*    */ package mcheli.__helper.client.renderer.item;
/*    */ 
/*    */ import mcheli.MCH_Config;
/*    */ import mcheli.MCH_ModelManager;
/*    */ import mcheli.lweapon.MCH_ItemLightWeaponBase;
/*    */ import mcheli.wrapper.W_Lib;
/*    */ import mcheli.wrapper.W_McClient;
/*    */ import net.minecraft.client.renderer.block.model.ItemCameraTransforms;
/*    */ import net.minecraft.entity.Entity;
/*    */ import net.minecraft.entity.EntityLivingBase;
/*    */ import net.minecraft.entity.player.EntityPlayer;
/*    */ import net.minecraft.item.ItemStack;
/*    */ import net.minecraft.util.EnumHand;
/*    */ import net.minecraftforge.fml.relauncher.Side;
/*    */ import net.minecraftforge.fml.relauncher.SideOnly;
/*    */ import org.lwjgl.opengl.GL11;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ @SideOnly(Side.CLIENT)
/*    */ public class BuiltInLightWeaponItemRenderer
/*    */   implements IItemModelRenderer
/*    */ {
/*    */   public boolean shouldRenderer(ItemStack itemStack, ItemCameraTransforms.TransformType transformType) {
/* 30 */     return (IItemModelRenderer.isFirstPerson(transformType) || IItemModelRenderer.isThirdPerson(transformType));
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void renderItem(ItemStack itemStack, EntityLivingBase entityLivingBase, ItemCameraTransforms.TransformType transformType, float partialTicks) {
/* 37 */     boolean isRender = false;
/*    */     
/* 39 */     if (IItemModelRenderer.isFirstPerson(transformType) || IItemModelRenderer.isThirdPerson(transformType)) {
/*    */       
/* 41 */       isRender = true;
/*    */       
/* 43 */       if (entityLivingBase instanceof EntityPlayer) {
/*    */         
/* 45 */         EntityPlayer player = (EntityPlayer)entityLivingBase;
/*    */         
/* 47 */         if (MCH_ItemLightWeaponBase.isHeld(player) && W_Lib.isFirstPerson() && W_Lib.isClientPlayer((Entity)player))
/*    */         {
/* 49 */           isRender = false;
/*    */         }
/*    */       } 
/*    */     } 
/*    */     
/* 54 */     if (isRender)
/*    */     {
/* 56 */       renderItem(itemStack, IItemModelRenderer.isFirstPerson(transformType), entityLivingBase);
/*    */     }
/*    */   }
/*    */ 
/*    */   
/*    */   private void renderItem(ItemStack itemStack, boolean isFirstPerson, EntityLivingBase entity) {
/* 62 */     String name = MCH_ItemLightWeaponBase.getName(itemStack);
/*    */     
/* 64 */     GL11.glEnable(32826);
/* 65 */     GL11.glEnable(2903);
/* 66 */     GL11.glPushMatrix();
/*    */     
/* 68 */     if (MCH_Config.SmoothShading.prmBool)
/*    */     {
/* 70 */       GL11.glShadeModel(7425);
/*    */     }
/*    */     
/* 73 */     GL11.glEnable(2884);
/*    */     
/* 75 */     W_McClient.MOD_bindTexture("textures/lweapon/" + name + ".png");
/*    */     
/* 77 */     if (isFirstPerson && entity.func_184587_cr() && entity.func_184600_cs() == EnumHand.MAIN_HAND)
/*    */     {
/* 79 */       GL11.glTranslated(0.12999999523162842D, 0.27000001072883606D, 0.009999999776482582D);
/*    */     }
/*    */     
/* 82 */     MCH_ModelManager.render("lweapons", name);
/*    */     
/* 84 */     GL11.glShadeModel(7424);
/* 85 */     GL11.glPopMatrix();
/* 86 */     GL11.glDisable(32826);
/*    */   }
/*    */ }


/* Location:              C:\Users\leo\Downloads\Mchelioreal\!\mcheli\__helper\client\renderer\item\BuiltInLightWeaponItemRenderer.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */