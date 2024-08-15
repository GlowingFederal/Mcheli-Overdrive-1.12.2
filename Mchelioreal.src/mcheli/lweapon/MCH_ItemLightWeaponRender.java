/*     */ package mcheli.lweapon;
/*     */ 
/*     */ import mcheli.MCH_Config;
/*     */ import mcheli.MCH_ModelManager;
/*     */ import mcheli.__helper.client._IItemRenderer;
/*     */ import mcheli.wrapper.W_Lib;
/*     */ import mcheli.wrapper.W_McClient;
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraftforge.fml.relauncher.Side;
/*     */ import net.minecraftforge.fml.relauncher.SideOnly;
/*     */ import org.lwjgl.opengl.GL11;
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
/*     */ @Deprecated
/*     */ public class MCH_ItemLightWeaponRender
/*     */   implements _IItemRenderer
/*     */ {
/*     */   public boolean handleRenderType(ItemStack item, _IItemRenderer.ItemRenderType type) {
/*  33 */     return (type == _IItemRenderer.ItemRenderType.EQUIPPED || type == _IItemRenderer.ItemRenderType.EQUIPPED_FIRST_PERSON);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean shouldUseRenderHelper(_IItemRenderer.ItemRenderType type, ItemStack item, _IItemRenderer.ItemRendererHelper helper) {
/*  42 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean useCurrentWeapon() {
/*  47 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void renderItem(_IItemRenderer.ItemRenderType type, ItemStack item, Object... data) {
/*  54 */     boolean isRender = false;
/*     */ 
/*     */     
/*  57 */     if (type == _IItemRenderer.ItemRenderType.EQUIPPED_FIRST_PERSON || type == _IItemRenderer.ItemRenderType.EQUIPPED) {
/*     */ 
/*     */       
/*  60 */       isRender = true;
/*     */       
/*  62 */       if (data[1] instanceof EntityPlayer) {
/*     */         
/*  64 */         EntityPlayer player = (EntityPlayer)data[1];
/*     */         
/*  66 */         if (MCH_ItemLightWeaponBase.isHeld(player) && W_Lib.isFirstPerson() && W_Lib.isClientPlayer((Entity)player))
/*     */         {
/*  68 */           isRender = false;
/*     */         }
/*     */       } 
/*     */     } 
/*     */     
/*  73 */     if (isRender)
/*     */     {
/*     */       
/*  76 */       renderItem(item, (Entity)W_Lib.castEntityLivingBase(data[1]), (type == _IItemRenderer.ItemRenderType.EQUIPPED_FIRST_PERSON));
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   @SideOnly(Side.CLIENT)
/*     */   public static void renderItem(ItemStack pitem, Entity entity, boolean isFirstPerson) {
/*  84 */     if (pitem == null || pitem.func_77973_b() == null) {
/*     */       return;
/*     */     }
/*     */ 
/*     */     
/*  89 */     String name = MCH_ItemLightWeaponBase.getName(pitem);
/*     */     
/*  91 */     GL11.glEnable(32826);
/*  92 */     GL11.glEnable(2903);
/*  93 */     GL11.glPushMatrix();
/*     */     
/*  95 */     if (MCH_Config.SmoothShading.prmBool)
/*     */     {
/*  97 */       GL11.glShadeModel(7425);
/*     */     }
/*     */     
/* 100 */     GL11.glEnable(2884);
/*     */     
/* 102 */     W_McClient.MOD_bindTexture("textures/lweapon/" + name + ".png");
/*     */     
/* 104 */     if (isFirstPerson) {
/*     */       
/* 106 */       GL11.glTranslatef(0.0F, 0.005F, -0.165F);
/* 107 */       GL11.glScalef(2.0F, 2.0F, 2.0F);
/* 108 */       GL11.glRotatef(-10.0F, 0.0F, 0.0F, 1.0F);
/* 109 */       GL11.glRotatef(90.0F, 0.0F, 1.0F, 0.0F);
/* 110 */       GL11.glRotatef(-50.0F, 1.0F, 0.0F, 0.0F);
/*     */     }
/*     */     else {
/*     */       
/* 114 */       GL11.glTranslatef(0.3F, 0.3F, 0.0F);
/* 115 */       GL11.glScalef(2.0F, 2.0F, 2.0F);
/* 116 */       GL11.glRotatef(20.0F, 0.0F, 0.0F, 1.0F);
/* 117 */       GL11.glRotatef(10.0F, 0.0F, 1.0F, 0.0F);
/* 118 */       GL11.glRotatef(15.0F, 1.0F, 0.0F, 0.0F);
/*     */     } 
/*     */     
/* 121 */     MCH_ModelManager.render("lweapons", name);
/*     */     
/* 123 */     GL11.glShadeModel(7424);
/* 124 */     GL11.glPopMatrix();
/* 125 */     GL11.glDisable(32826);
/*     */   }
/*     */ }


/* Location:              C:\Users\leo\Downloads\Mchelioreal\!\mcheli\lweapon\MCH_ItemLightWeaponRender.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */