/*     */ package mcheli.aircraft;
/*     */ 
/*     */ import java.util.List;
/*     */ import javax.annotation.Nullable;
/*     */ import mcheli.MCH_Lib;
/*     */ import mcheli.__helper.entity.IEntitySinglePassenger;
/*     */ import mcheli.wrapper.W_Entity;
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraft.nbt.NBTTagCompound;
/*     */ import net.minecraft.util.DamageSource;
/*     */ import net.minecraft.util.EnumHand;
/*     */ import net.minecraft.util.math.AxisAlignedBB;
/*     */ import net.minecraft.world.World;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ public class MCH_EntitySeat
/*     */   extends W_Entity
/*     */   implements IEntitySinglePassenger
/*     */ {
/*     */   public String parentUniqueID;
/*     */   private MCH_EntityAircraft parent;
/*     */   public int seatID;
/*     */   public int parentSearchCount;
/*     */   protected Entity lastRiddenByEntity;
/*     */   public static final float BB_SIZE = 1.0F;
/*     */   
/*     */   public MCH_EntitySeat(World world) {
/*  41 */     super(world);
/*  42 */     func_70105_a(1.0F, 1.0F);
/*     */     
/*  44 */     this.field_70159_w = 0.0D;
/*  45 */     this.field_70181_x = 0.0D;
/*  46 */     this.field_70179_y = 0.0D;
/*  47 */     this.seatID = -1;
/*  48 */     setParent((MCH_EntityAircraft)null);
/*  49 */     this.parentSearchCount = 0;
/*  50 */     this.lastRiddenByEntity = null;
/*  51 */     this.field_70158_ak = true;
/*  52 */     this.field_70178_ae = true;
/*     */   }
/*     */ 
/*     */   
/*     */   public MCH_EntitySeat(World world, double x, double y, double z) {
/*  57 */     this(world);
/*  58 */     func_70107_b(x, y + 1.0D, z);
/*  59 */     this.field_70169_q = x;
/*  60 */     this.field_70167_r = y + 1.0D;
/*  61 */     this.field_70166_s = z;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected boolean func_70041_e_() {
/*  67 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public AxisAlignedBB func_70114_g(Entity par1Entity) {
/*  74 */     return par1Entity.func_174813_aQ();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public AxisAlignedBB func_70046_E() {
/*  81 */     return func_174813_aQ();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean func_70104_M() {
/*  87 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public double func_70042_X() {
/*  93 */     return -0.3D;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean func_70097_a(DamageSource par1DamageSource, float par2) {
/*  99 */     if (getParent() != null)
/*     */     {
/* 101 */       return getParent().func_70097_a(par1DamageSource, par2);
/*     */     }
/*     */     
/* 104 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean func_70067_L() {
/* 110 */     return !this.field_70128_L;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @SideOnly(Side.CLIENT)
/*     */   public void func_180426_a(double x, double y, double z, float yaw, float pitch, int posRotationIncrements, boolean teleport) {}
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void func_70106_y() {
/* 124 */     super.func_70106_y();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void func_70071_h_() {
/* 130 */     super.func_70071_h_();
/*     */     
/* 132 */     this.field_70143_R = 0.0F;
/* 133 */     Entity riddenByEntity = getRiddenByEntity();
/*     */ 
/*     */     
/* 136 */     if (riddenByEntity != null)
/*     */     {
/*     */       
/* 139 */       riddenByEntity.field_70143_R = 0.0F;
/*     */     }
/*     */ 
/*     */     
/* 143 */     if (this.lastRiddenByEntity == null && riddenByEntity != null) {
/*     */       
/* 145 */       if (getParent() != null)
/*     */       {
/* 147 */         MCH_Lib.DbgLog(this.field_70170_p, "MCH_EntitySeat.onUpdate:SeatID=%d", new Object[] {
/*     */ 
/*     */               
/* 150 */               Integer.valueOf(this.seatID), riddenByEntity.toString()
/*     */             });
/*     */         
/* 153 */         getParent().onMountPlayerSeat(this, riddenByEntity);
/*     */       }
/*     */     
/*     */     }
/* 157 */     else if (this.lastRiddenByEntity != null && riddenByEntity == null) {
/*     */       
/* 159 */       if (getParent() != null) {
/*     */         
/* 161 */         MCH_Lib.DbgLog(this.field_70170_p, "MCH_EntitySeat.onUpdate:SeatID=%d", new Object[] {
/*     */               
/* 163 */               Integer.valueOf(this.seatID), this.lastRiddenByEntity.toString()
/*     */             });
/* 165 */         getParent().onUnmountPlayerSeat(this, this.lastRiddenByEntity);
/*     */       } 
/*     */     } 
/*     */     
/* 169 */     if (this.field_70170_p.field_72995_K) {
/*     */       
/* 171 */       onUpdate_Client();
/*     */     }
/*     */     else {
/*     */       
/* 175 */       onUpdate_Server();
/*     */     } 
/*     */ 
/*     */     
/* 179 */     this.lastRiddenByEntity = getRiddenByEntity();
/*     */   }
/*     */ 
/*     */   
/*     */   private void onUpdate_Client() {
/* 184 */     checkDetachmentAndDelete();
/*     */   }
/*     */ 
/*     */   
/*     */   private void onUpdate_Server() {
/* 189 */     checkDetachmentAndDelete();
/*     */     
/* 191 */     Entity riddenByEntity = getRiddenByEntity();
/*     */ 
/*     */     
/* 194 */     if (riddenByEntity == null || riddenByEntity.field_70128_L);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void func_184232_k(Entity passenger) {
/* 205 */     updatePosition(passenger);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void updatePosition(@Nullable Entity ridEnt) {
/* 212 */     if (ridEnt != null) {
/*     */       
/* 214 */       ridEnt.func_70107_b(this.field_70165_t, this.field_70163_u, this.field_70161_v);
/* 215 */       ridEnt.field_70159_w = ridEnt.field_70181_x = ridEnt.field_70179_y = 0.0D;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void updateRotation(@Nullable Entity ridEnt, float yaw, float pitch) {
/* 223 */     if (ridEnt != null) {
/*     */       
/* 225 */       ridEnt.field_70177_z = yaw;
/* 226 */       ridEnt.field_70125_A = pitch;
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   protected void checkDetachmentAndDelete() {
/* 232 */     if (!this.field_70128_L && (this.seatID < 0 || getParent() == null || (getParent()).field_70128_L)) {
/*     */       
/* 234 */       if (getParent() != null && (getParent()).field_70128_L)
/*     */       {
/* 236 */         this.parentSearchCount = 100000000;
/*     */       }
/*     */       
/* 239 */       if (this.parentSearchCount >= 1200)
/*     */       {
/* 241 */         func_70106_y();
/*     */         
/* 243 */         if (!this.field_70170_p.field_72995_K) {
/*     */           
/* 245 */           Entity riddenByEntity = getRiddenByEntity();
/*     */ 
/*     */ 
/*     */           
/* 249 */           if (riddenByEntity != null) {
/* 250 */             riddenByEntity.func_184210_p();
/*     */           }
/*     */         } 
/*     */         
/* 254 */         setParent((MCH_EntityAircraft)null);
/*     */         
/* 256 */         MCH_Lib.DbgLog(this.field_70170_p, "[Error]座席エンティティは本体が見つからないため削除 seat=%d, parentUniqueID=%s", new Object[] {
/*     */               
/* 258 */               Integer.valueOf(this.seatID), this.parentUniqueID
/*     */             });
/*     */       }
/*     */       else
/*     */       {
/* 263 */         this.parentSearchCount++;
/*     */       }
/*     */     
/*     */     } else {
/*     */       
/* 268 */       this.parentSearchCount = 0;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected void func_70014_b(NBTTagCompound par1NBTTagCompound) {
/* 275 */     par1NBTTagCompound.func_74768_a("SeatID", this.seatID);
/* 276 */     par1NBTTagCompound.func_74778_a("ParentUniqueID", this.parentUniqueID);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected void func_70037_a(NBTTagCompound par1NBTTagCompound) {
/* 282 */     this.seatID = par1NBTTagCompound.func_74762_e("SeatID");
/* 283 */     this.parentUniqueID = par1NBTTagCompound.func_74779_i("ParentUniqueID");
/*     */   }
/*     */ 
/*     */   
/*     */   @SideOnly(Side.CLIENT)
/*     */   public float getShadowSize() {
/* 289 */     return 0.0F;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean canRideMob(Entity entity) {
/* 294 */     if (getParent() == null || this.seatID < 0)
/*     */     {
/* 296 */       return false;
/*     */     }
/*     */     
/* 299 */     if (getParent().getSeatInfo(this.seatID + 1) instanceof MCH_SeatRackInfo)
/*     */     {
/* 301 */       return false;
/*     */     }
/*     */     
/* 304 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isGunnerMode() {
/* 309 */     Entity riddenByEntity = getRiddenByEntity();
/*     */ 
/*     */     
/* 312 */     if (riddenByEntity != null)
/*     */     {
/* 314 */       if (getParent() != null)
/*     */       {
/* 316 */         return getParent().getIsGunnerMode(riddenByEntity);
/*     */       }
/*     */     }
/*     */     
/* 320 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean func_184230_a(EntityPlayer player, EnumHand hand) {
/* 327 */     if (getParent() == null || getParent().isDestroyed())
/*     */     {
/* 329 */       return false;
/*     */     }
/*     */     
/* 332 */     if (!getParent().checkTeam(player))
/*     */     {
/* 334 */       return false;
/*     */     }
/*     */ 
/*     */     
/* 338 */     ItemStack itemStack = player.func_184586_b(hand);
/*     */ 
/*     */     
/* 341 */     if (!itemStack.func_190926_b() && itemStack.func_77973_b() instanceof mcheli.tool.MCH_ItemWrench)
/*     */     {
/*     */       
/* 344 */       return getParent().func_184230_a(player, hand);
/*     */     }
/*     */ 
/*     */     
/* 348 */     if (!itemStack.func_190926_b() && itemStack.func_77973_b() instanceof mcheli.mob.MCH_ItemSpawnGunner)
/*     */     {
/*     */       
/* 351 */       return getParent().func_184230_a(player, hand);
/*     */     }
/*     */     
/* 354 */     Entity riddenByEntity = getRiddenByEntity();
/*     */ 
/*     */     
/* 357 */     if (riddenByEntity != null)
/*     */     {
/* 359 */       return false;
/*     */     }
/*     */     
/* 362 */     if (player.func_184187_bx() != null)
/*     */     {
/* 364 */       return false;
/*     */     }
/*     */     
/* 367 */     if (!canRideMob((Entity)player))
/*     */     {
/* 369 */       return false;
/*     */     }
/*     */ 
/*     */     
/* 373 */     player.func_184220_m((Entity)this);
/*     */     
/* 375 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   public MCH_EntityAircraft getParent() {
/* 381 */     return this.parent;
/*     */   }
/*     */ 
/*     */   
/*     */   public void setParent(MCH_EntityAircraft parent) {
/* 386 */     this.parent = parent;
/*     */     
/* 388 */     Entity riddenByEntity = getRiddenByEntity();
/*     */ 
/*     */     
/* 391 */     if (riddenByEntity != null) {
/*     */       
/* 393 */       MCH_Lib.DbgLog(this.field_70170_p, "MCH_EntitySeat.setParent:SeatID=%d %s : " + getParent(), new Object[] {
/*     */ 
/*     */             
/* 396 */             Integer.valueOf(this.seatID), riddenByEntity.toString()
/*     */           });
/*     */       
/* 399 */       if (getParent() != null)
/*     */       {
/*     */         
/* 402 */         getParent().onMountPlayerSeat(this, riddenByEntity);
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   public Entity getRiddenByEntity() {
/* 411 */     List<Entity> passengers = func_184188_bt();
/* 412 */     return passengers.isEmpty() ? null : passengers.get(0);
/*     */   }
/*     */ }


/* Location:              C:\Users\leo\Downloads\Mchelioreal\!\mcheli\aircraft\MCH_EntitySeat.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */