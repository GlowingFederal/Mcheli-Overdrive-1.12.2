/*     */ package mcheli;
/*     */ 
/*     */ import java.util.List;
/*     */ import java.util.UUID;
/*     */ import mcheli.aircraft.MCH_EntityAircraft;
/*     */ import mcheli.aircraft.MCH_EntitySeat;
/*     */ import mcheli.aircraft.MCH_ItemAircraft;
/*     */ import mcheli.chain.MCH_ItemChain;
/*     */ import mcheli.command.MCH_Command;
/*     */ import mcheli.weapon.MCH_EntityBaseBullet;
/*     */ import mcheli.wrapper.W_Entity;
/*     */ import mcheli.wrapper.W_EntityPlayer;
/*     */ import mcheli.wrapper.W_EventHook;
/*     */ import mcheli.wrapper.W_Lib;
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.entity.player.EntityPlayerMP;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraft.util.EnumActionResult;
/*     */ import net.minecraftforge.event.CommandEvent;
/*     */ import net.minecraftforge.event.entity.EntityEvent;
/*     */ import net.minecraftforge.event.entity.EntityJoinWorldEvent;
/*     */ import net.minecraftforge.event.entity.living.LivingAttackEvent;
/*     */ import net.minecraftforge.event.entity.living.LivingHurtEvent;
/*     */ import net.minecraftforge.event.entity.player.PlayerInteractEvent;
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
/*     */ public class MCH_EventHook
/*     */   extends W_EventHook
/*     */ {
/*     */   public void commandEvent(CommandEvent event) {
/*  39 */     MCH_Command.onCommandEvent(event);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void entitySpawn(EntityJoinWorldEvent event) {
/*  45 */     if (W_Lib.isEntityLivingBase(event.getEntity()) && !W_EntityPlayer.isPlayer(event.getEntity())) {
/*     */ 
/*     */ 
/*     */       
/*  49 */       MCH_MOD.proxy.setRenderEntityDistanceWeight(MCH_Config.MobRenderDistanceWeight.prmDouble);
/*     */     }
/*  51 */     else if (event.getEntity() instanceof MCH_EntityAircraft) {
/*     */       
/*  53 */       MCH_EntityAircraft aircraft = (MCH_EntityAircraft)event.getEntity();
/*     */       
/*  55 */       if (!aircraft.field_70170_p.field_72995_K)
/*     */       {
/*  57 */         if (!aircraft.isCreatedSeats())
/*     */         {
/*  59 */           aircraft.createSeats(UUID.randomUUID().toString());
/*     */         }
/*     */       }
/*     */     }
/*  63 */     else if (W_EntityPlayer.isPlayer(event.getEntity())) {
/*     */ 
/*     */       
/*  66 */       Entity e = event.getEntity();
/*  67 */       boolean b = Float.isNaN(e.field_70125_A);
/*     */       
/*  69 */       b |= Float.isNaN(e.field_70127_C);
/*  70 */       b |= Float.isInfinite(e.field_70125_A);
/*  71 */       b |= Float.isInfinite(e.field_70127_C);
/*     */       
/*  73 */       if (b) {
/*     */         
/*  75 */         MCH_Lib.Log(event.getEntity(), "### EntityJoinWorldEvent Error:Player invalid rotation pitch(" + e.field_70125_A + ")", new Object[0]);
/*     */ 
/*     */         
/*  78 */         e.field_70125_A = 0.0F;
/*  79 */         e.field_70127_C = 0.0F;
/*     */       } 
/*     */       
/*  82 */       b = Float.isInfinite(e.field_70177_z);
/*  83 */       b |= Float.isInfinite(e.field_70126_B);
/*  84 */       b |= Float.isNaN(e.field_70177_z);
/*  85 */       b |= Float.isNaN(e.field_70126_B);
/*     */       
/*  87 */       if (b) {
/*     */         
/*  89 */         MCH_Lib.Log(event.getEntity(), "### EntityJoinWorldEvent Error:Player invalid rotation yaw(" + e.field_70177_z + ")", new Object[0]);
/*     */ 
/*     */         
/*  92 */         e.field_70177_z = 0.0F;
/*  93 */         e.field_70126_B = 0.0F;
/*     */       } 
/*     */       
/*  96 */       if (!e.field_70170_p.field_72995_K && event.getEntity() instanceof EntityPlayerMP) {
/*     */         
/*  98 */         MCH_Lib.DbgLog(false, "EntityJoinWorldEvent:" + event.getEntity(), new Object[0]);
/*  99 */         MCH_PacketNotifyServerSettings.send((EntityPlayerMP)event.getEntity());
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void livingAttackEvent(LivingAttackEvent event) {
/* 107 */     MCH_EntityAircraft ac = getRiddenAircraft(event.getEntity());
/* 108 */     if (ac == null)
/*     */       return; 
/* 110 */     if (ac.getAcInfo() == null)
/*     */       return; 
/* 112 */     if (ac.isDestroyed()) {
/*     */       return;
/*     */     }
/* 115 */     if ((ac.getAcInfo()).damageFactor > 0.0F) {
/*     */       return;
/*     */     }
/*     */ 
/*     */     
/* 120 */     Entity attackEntity = event.getSource().func_76346_g();
/*     */     
/* 122 */     if (attackEntity == null) {
/*     */       
/* 124 */       event.setCanceled(true);
/*     */     }
/* 126 */     else if (W_Entity.isEqual(attackEntity, event.getEntity())) {
/*     */       
/* 128 */       event.setCanceled(true);
/*     */     }
/* 130 */     else if (ac.isMountedEntity(attackEntity)) {
/*     */       
/* 132 */       event.setCanceled(true);
/*     */     }
/*     */     else {
/*     */       
/* 136 */       MCH_EntityAircraft atkac = getRiddenAircraft(attackEntity);
/*     */       
/* 138 */       if (W_Entity.isEqual((Entity)atkac, (Entity)ac))
/*     */       {
/* 140 */         event.setCanceled(true);
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void livingHurtEvent(LivingHurtEvent event) {
/* 148 */     MCH_EntityAircraft ac = getRiddenAircraft(event.getEntity());
/*     */     
/* 150 */     if (ac == null)
/*     */       return; 
/* 152 */     if (ac.getAcInfo() == null) {
/*     */       return;
/*     */     }
/* 155 */     if (ac.isDestroyed()) {
/*     */       return;
/*     */     }
/*     */ 
/*     */     
/* 160 */     Entity attackEntity = event.getSource().func_76346_g();
/* 161 */     float f = event.getAmount();
/*     */     
/* 163 */     if (attackEntity == null) {
/*     */ 
/*     */ 
/*     */       
/* 167 */       ac.func_70097_a(event.getSource(), f * 2.0F);
/* 168 */       f *= (ac.getAcInfo()).damageFactor;
/*     */     }
/* 170 */     else if (W_Entity.isEqual(attackEntity, event.getEntity())) {
/*     */ 
/*     */ 
/*     */       
/* 174 */       ac.func_70097_a(event.getSource(), f * 2.0F);
/* 175 */       f *= (ac.getAcInfo()).damageFactor;
/*     */     }
/* 177 */     else if (ac.isMountedEntity(attackEntity)) {
/*     */ 
/*     */       
/* 180 */       f = 0.0F;
/* 181 */       event.setCanceled(true);
/*     */     }
/*     */     else {
/*     */       
/* 185 */       MCH_EntityAircraft atkac = getRiddenAircraft(attackEntity);
/*     */       
/* 187 */       if (W_Entity.isEqual((Entity)atkac, (Entity)ac)) {
/*     */ 
/*     */         
/* 190 */         f = 0.0F;
/* 191 */         event.setCanceled(true);
/*     */       
/*     */       }
/*     */       else {
/*     */ 
/*     */         
/* 197 */         ac.func_70097_a(event.getSource(), f * 2.0F);
/* 198 */         f *= (ac.getAcInfo()).damageFactor;
/*     */       } 
/*     */     } 
/*     */     
/* 202 */     event.setAmount(f);
/*     */   }
/*     */ 
/*     */   
/*     */   public MCH_EntityAircraft getRiddenAircraft(Entity entity) {
/* 207 */     MCH_EntityAircraft ac = null;
/* 208 */     Entity ridden = entity.func_184187_bx();
/*     */     
/* 210 */     if (ridden instanceof MCH_EntityAircraft) {
/*     */       
/* 212 */       ac = (MCH_EntityAircraft)ridden;
/*     */     }
/* 214 */     else if (ridden instanceof MCH_EntitySeat) {
/*     */       
/* 216 */       ac = ((MCH_EntitySeat)ridden).getParent();
/*     */     } 
/*     */     
/* 219 */     if (ac == null) {
/*     */ 
/*     */ 
/*     */       
/* 223 */       List<MCH_EntityAircraft> list = entity.field_70170_p.func_72872_a(MCH_EntityAircraft.class, entity
/* 224 */           .func_174813_aQ().func_72314_b(50.0D, 50.0D, 50.0D));
/*     */       
/* 226 */       if (list != null)
/*     */       {
/* 228 */         for (int i = 0; i < list.size(); i++) {
/*     */ 
/*     */           
/* 231 */           MCH_EntityAircraft tmp = list.get(i);
/* 232 */           if (tmp.isMountedEntity(entity))
/*     */           {
/* 234 */             return tmp;
/*     */           }
/*     */         } 
/*     */       }
/*     */     } 
/* 239 */     return ac;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void entityInteractEvent(PlayerInteractEvent.EntityInteract event) {
/* 247 */     ItemStack item = event.getEntityPlayer().func_184586_b(event.getHand());
/*     */ 
/*     */     
/* 250 */     if (item.func_190926_b()) {
/*     */       return;
/*     */     }
/*     */ 
/*     */     
/* 255 */     if (item.func_77973_b() instanceof MCH_ItemChain) {
/*     */       
/* 257 */       MCH_ItemChain.interactEntity(item, event.getTarget(), event.getEntityPlayer(), 
/* 258 */           (event.getEntityPlayer()).field_70170_p);
/*     */       
/* 260 */       event.setCanceled(true);
/* 261 */       event.setCancellationResult(EnumActionResult.SUCCESS);
/*     */     }
/* 263 */     else if (item.func_77973_b() instanceof MCH_ItemAircraft) {
/*     */       
/* 265 */       ((MCH_ItemAircraft)item.func_77973_b()).rideEntity(item, event.getTarget(), event.getEntityPlayer());
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void entityCanUpdate(EntityEvent.CanUpdate event) {
/* 272 */     if (event.getEntity() instanceof MCH_EntityBaseBullet) {
/*     */       
/* 274 */       MCH_EntityBaseBullet bullet = (MCH_EntityBaseBullet)event.getEntity();
/* 275 */       bullet.func_70106_y();
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Users\leo\Downloads\Mchelioreal\!\mcheli\MCH_EventHook.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */