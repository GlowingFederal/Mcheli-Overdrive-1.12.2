/*     */ package mcheli.chain;
/*     */ 
/*     */ import java.util.List;
/*     */ import javax.annotation.Nullable;
/*     */ import mcheli.MCH_Config;
/*     */ import mcheli.wrapper.W_Entity;
/*     */ import mcheli.wrapper.W_Item;
/*     */ import mcheli.wrapper.W_Lib;
/*     */ import mcheli.wrapper.W_WorldFunc;
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraft.nbt.NBTTagCompound;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class MCH_ItemChain
/*     */   extends W_Item
/*     */ {
/*     */   public MCH_ItemChain(int par1) {
/*  34 */     super(par1);
/*  35 */     func_77625_d(1);
/*     */   }
/*     */ 
/*     */   
/*     */   public static void interactEntity(ItemStack item, @Nullable Entity entity, EntityPlayer player, World world) {
/*  40 */     if (!world.field_72995_K && entity != null && !entity.field_70128_L) {
/*     */       
/*  42 */       if (entity instanceof net.minecraft.entity.item.EntityItem)
/*     */         return; 
/*  44 */       if (entity instanceof MCH_EntityChain)
/*     */         return; 
/*  46 */       if (entity instanceof mcheli.aircraft.MCH_EntityHitBox)
/*     */         return; 
/*  48 */       if (entity instanceof mcheli.aircraft.MCH_EntitySeat)
/*     */         return; 
/*  50 */       if (entity instanceof mcheli.uav.MCH_EntityUavStation)
/*     */         return; 
/*  52 */       if (entity instanceof mcheli.parachute.MCH_EntityParachute) {
/*     */         return;
/*     */       }
/*  55 */       if (W_Lib.isEntityLivingBase(entity)) {
/*     */         return;
/*     */       }
/*     */ 
/*     */       
/*  60 */       if (MCH_Config.FixVehicleAtPlacedPoint.prmBool && entity instanceof mcheli.vehicle.MCH_EntityVehicle) {
/*     */         return;
/*     */       }
/*     */ 
/*     */       
/*  65 */       MCH_EntityChain towingChain = getTowedEntityChain(entity);
/*     */       
/*  67 */       if (towingChain != null) {
/*     */         
/*  69 */         towingChain.func_70106_y();
/*     */         
/*     */         return;
/*     */       } 
/*  73 */       Entity entityTowed = getTowedEntity(item, world);
/*     */       
/*  75 */       if (entityTowed == null) {
/*     */         
/*  77 */         playConnectTowedEntity(entity);
/*  78 */         setTowedEntity(item, entity);
/*     */       }
/*     */       else {
/*     */         
/*  82 */         if (W_Entity.isEqual(entityTowed, entity)) {
/*     */           return;
/*     */         }
/*     */ 
/*     */         
/*  87 */         double diff = entity.func_70032_d(entityTowed);
/*     */         
/*  89 */         if (diff < 2.0D || diff > 16.0D) {
/*     */           return;
/*     */         }
/*     */ 
/*     */         
/*  94 */         MCH_EntityChain chain = new MCH_EntityChain(world, (entityTowed.field_70165_t + entity.field_70165_t) / 2.0D, (entityTowed.field_70163_u + entity.field_70163_u) / 2.0D, (entityTowed.field_70161_v + entity.field_70161_v) / 2.0D);
/*     */ 
/*     */         
/*  97 */         chain.setChainLength((int)diff);
/*  98 */         chain.setTowEntity(entityTowed, entity);
/*  99 */         chain.field_70169_q = chain.field_70165_t;
/* 100 */         chain.field_70167_r = chain.field_70163_u;
/* 101 */         chain.field_70166_s = chain.field_70161_v;
/*     */         
/* 103 */         world.func_72838_d((Entity)chain);
/*     */         
/* 105 */         playConnectTowingEntity(entity);
/* 106 */         setTowedEntity(item, (Entity)null);
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public static void playConnectTowingEntity(Entity e) {
/* 113 */     W_WorldFunc.MOD_playSoundEffect(e.field_70170_p, e.field_70165_t, e.field_70163_u, e.field_70161_v, "chain_ct", 1.0F, 1.0F);
/*     */   }
/*     */ 
/*     */   
/*     */   public static void playConnectTowedEntity(Entity e) {
/* 118 */     W_WorldFunc.MOD_playSoundEffect(e.field_70170_p, e.field_70165_t, e.field_70163_u, e.field_70161_v, "chain", 1.0F, 1.0F);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void func_77622_d(ItemStack par1ItemStack, World par2World, EntityPlayer par3EntityPlayer) {}
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   public static MCH_EntityChain getTowedEntityChain(Entity entity) {
/* 131 */     List<MCH_EntityChain> list = entity.field_70170_p.func_72872_a(MCH_EntityChain.class, entity
/* 132 */         .func_174813_aQ().func_72314_b(25.0D, 25.0D, 25.0D));
/*     */     
/* 134 */     if (list == null) {
/* 135 */       return null;
/*     */     }
/* 137 */     for (int i = 0; i < list.size(); i++) {
/*     */       
/* 139 */       MCH_EntityChain chain = list.get(i);
/*     */       
/* 141 */       if (chain.isTowingEntity()) {
/*     */         
/* 143 */         if (W_Entity.isEqual(chain.towEntity, entity))
/* 144 */           return chain; 
/* 145 */         if (W_Entity.isEqual(chain.towedEntity, entity))
/* 146 */           return chain; 
/*     */       } 
/*     */     } 
/* 149 */     return null;
/*     */   }
/*     */ 
/*     */   
/*     */   public static void setTowedEntity(ItemStack item, @Nullable Entity entity) {
/* 154 */     NBTTagCompound nbt = item.func_77978_p();
/*     */     
/* 156 */     if (nbt == null) {
/*     */       
/* 158 */       nbt = new NBTTagCompound();
/* 159 */       item.func_77982_d(nbt);
/*     */     } 
/*     */     
/* 162 */     if (entity != null && !entity.field_70128_L) {
/*     */       
/* 164 */       nbt.func_74768_a("TowedEntityId", W_Entity.getEntityId(entity));
/* 165 */       nbt.func_74778_a("TowedEntityUUID", entity.getPersistentID().toString());
/*     */     }
/*     */     else {
/*     */       
/* 169 */       nbt.func_74768_a("TowedEntityId", 0);
/* 170 */       nbt.func_74778_a("TowedEntityUUID", "");
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   public static Entity getTowedEntity(ItemStack item, World world) {
/* 177 */     NBTTagCompound nbt = item.func_77978_p();
/*     */     
/* 179 */     if (nbt == null) {
/*     */       
/* 181 */       nbt = new NBTTagCompound();
/* 182 */       item.func_77982_d(nbt);
/*     */     
/*     */     }
/* 185 */     else if (nbt.func_74764_b("TowedEntityId") && nbt.func_74764_b("TowedEntityUUID")) {
/*     */       
/* 187 */       int id = nbt.func_74762_e("TowedEntityId");
/* 188 */       String uuid = nbt.func_74779_i("TowedEntityUUID");
/* 189 */       Entity entity = world.func_73045_a(id);
/*     */       
/* 191 */       if (entity != null && !entity.field_70128_L && uuid.compareTo(entity.getPersistentID().toString()) == 0)
/*     */       {
/* 193 */         return entity;
/*     */       }
/*     */     } 
/*     */     
/* 197 */     return null;
/*     */   }
/*     */ }


/* Location:              C:\Users\leo\Downloads\Mchelioreal\!\mcheli\chain\MCH_ItemChain.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */