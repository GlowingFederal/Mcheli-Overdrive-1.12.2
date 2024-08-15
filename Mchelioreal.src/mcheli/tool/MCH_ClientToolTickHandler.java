/*     */ package mcheli.tool;
/*     */ 
/*     */ import mcheli.MCH_ClientTickHandlerBase;
/*     */ import mcheli.MCH_Config;
/*     */ import mcheli.MCH_Key;
/*     */ import mcheli.tool.rangefinder.MCH_ItemRangeFinder;
/*     */ import mcheli.wrapper.W_McClient;
/*     */ import mcheli.wrapper.W_Reflection;
/*     */ import net.minecraft.client.Minecraft;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.item.ItemStack;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class MCH_ClientToolTickHandler
/*     */   extends MCH_ClientTickHandlerBase
/*     */ {
/*     */   public MCH_Key KeyUseItem;
/*     */   public MCH_Key KeyZoomIn;
/*     */   public MCH_Key KeyZoomOut;
/*     */   public MCH_Key KeySwitchMode;
/*     */   public MCH_Key[] Keys;
/*     */   
/*     */   public MCH_ClientToolTickHandler(Minecraft minecraft, MCH_Config config) {
/*  30 */     super(minecraft);
/*  31 */     updateKeybind(config);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void updateKeybind(MCH_Config config) {
/*  37 */     this.KeyUseItem = new MCH_Key(MCH_Config.KeyAttack.prmInt);
/*  38 */     this.KeyZoomIn = new MCH_Key(MCH_Config.KeyZoom.prmInt);
/*  39 */     this.KeyZoomOut = new MCH_Key(MCH_Config.KeySwWeaponMode.prmInt);
/*  40 */     this.KeySwitchMode = new MCH_Key(MCH_Config.KeyFlare.prmInt);
/*  41 */     this.Keys = new MCH_Key[] { this.KeyUseItem, this.KeyZoomIn, this.KeyZoomOut, this.KeySwitchMode };
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void onTick(boolean inGUI) {
/*  50 */     for (MCH_Key k : this.Keys)
/*     */     {
/*  52 */       k.update();
/*     */     }
/*     */     
/*  55 */     onTick_ItemWrench(inGUI, (EntityPlayer)this.mc.field_71439_g);
/*  56 */     onTick_ItemRangeFinder(inGUI, (EntityPlayer)this.mc.field_71439_g);
/*     */   }
/*     */ 
/*     */   
/*     */   private void onTick_ItemRangeFinder(boolean inGUI, EntityPlayer player) {
/*  61 */     if (MCH_ItemRangeFinder.rangeFinderUseCooldown > 0)
/*     */     {
/*  63 */       MCH_ItemRangeFinder.rangeFinderUseCooldown--;
/*     */     }
/*     */ 
/*     */     
/*  67 */     ItemStack itemStack = ItemStack.field_190927_a;
/*     */     
/*  69 */     if (player != null) {
/*     */ 
/*     */       
/*  72 */       itemStack = player.func_184614_ca();
/*     */ 
/*     */       
/*  75 */       if (!itemStack.func_190926_b() && itemStack.func_77973_b() instanceof MCH_ItemRangeFinder) {
/*     */ 
/*     */         
/*  78 */         boolean usingItem = (player.func_184612_cw() > 8 && MCH_ItemRangeFinder.canUse(player));
/*     */         
/*  80 */         if (!MCH_ItemRangeFinder.continueUsingItem && usingItem)
/*     */         {
/*  82 */           MCH_ItemRangeFinder.onStartUseItem();
/*     */         }
/*     */         
/*  85 */         if (usingItem) {
/*     */           
/*  87 */           if (this.KeyUseItem.isKeyDown())
/*     */           {
/*  89 */             ((MCH_ItemRangeFinder)itemStack.func_77973_b()).spotEntity(player, itemStack);
/*     */           }
/*     */           
/*  92 */           if (this.KeyZoomIn.isKeyPress() && MCH_ItemRangeFinder.zoom < 10.0F) {
/*     */             
/*  94 */             MCH_ItemRangeFinder.zoom += MCH_ItemRangeFinder.zoom / 10.0F;
/*     */             
/*  96 */             if (MCH_ItemRangeFinder.zoom > 10.0F)
/*     */             {
/*  98 */               MCH_ItemRangeFinder.zoom = 10.0F;
/*     */             }
/*     */             
/* 101 */             W_McClient.MOD_playSoundFX("zoom", 0.05F, 1.0F);
/* 102 */             W_Reflection.setCameraZoom(MCH_ItemRangeFinder.zoom);
/*     */           } 
/*     */           
/* 105 */           if (this.KeyZoomOut.isKeyPress() && MCH_ItemRangeFinder.zoom > 1.2F) {
/*     */             
/* 107 */             MCH_ItemRangeFinder.zoom -= MCH_ItemRangeFinder.zoom / 10.0F;
/*     */             
/* 109 */             if (MCH_ItemRangeFinder.zoom < 1.2F)
/*     */             {
/* 111 */               MCH_ItemRangeFinder.zoom = 1.2F;
/*     */             }
/*     */             
/* 114 */             W_McClient.MOD_playSoundFX("zoom", 0.05F, 0.9F);
/* 115 */             W_Reflection.setCameraZoom(MCH_ItemRangeFinder.zoom);
/*     */           } 
/*     */           
/* 118 */           if (this.KeySwitchMode.isKeyDown()) {
/*     */             
/* 120 */             W_McClient.MOD_playSoundFX("lockon", 1.0F, 0.9F);
/* 121 */             MCH_ItemRangeFinder.mode = (MCH_ItemRangeFinder.mode + 1) % 3;
/*     */             
/* 123 */             if (this.mc.func_71356_B() && MCH_ItemRangeFinder.mode == 0)
/*     */             {
/* 125 */               MCH_ItemRangeFinder.mode = 1;
/*     */             }
/*     */           } 
/*     */         } 
/*     */       } 
/*     */     } 
/*     */     
/* 132 */     if (MCH_ItemRangeFinder.continueUsingItem)
/*     */     {
/*     */       
/* 135 */       if (itemStack.func_190926_b() || !(itemStack.func_77973_b() instanceof MCH_ItemRangeFinder))
/*     */       {
/* 137 */         MCH_ItemRangeFinder.onStopUseItem();
/*     */       }
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   private void onTick_ItemWrench(boolean inGUI, EntityPlayer player) {
/* 144 */     if (player == null) {
/*     */       return;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/* 150 */     ItemStack itemStack = player.func_184614_ca();
/*     */ 
/*     */     
/* 153 */     if (!itemStack.func_190926_b() && itemStack.func_77973_b() instanceof MCH_ItemWrench) {
/*     */       
/* 155 */       int maxdm = itemStack.func_77958_k();
/* 156 */       int dm = itemStack.func_77960_j();
/*     */       
/* 158 */       if (dm <= maxdm) {
/*     */ 
/*     */         
/* 161 */         ItemStack renderItemstack = W_Reflection.getItemRendererMainHand();
/*     */ 
/*     */         
/* 164 */         if (renderItemstack.func_190926_b() || itemStack.func_77973_b() == renderItemstack.func_77973_b())
/*     */         {
/*     */           
/* 167 */           W_Reflection.setItemRendererMainHand(player.field_71071_by.func_70448_g());
/*     */         }
/*     */       } 
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Users\leo\Downloads\Mchelioreal\!\mcheli\tool\MCH_ClientToolTickHandler.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */