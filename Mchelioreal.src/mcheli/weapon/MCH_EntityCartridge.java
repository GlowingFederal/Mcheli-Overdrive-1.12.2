/*     */ package mcheli.weapon;
/*     */ 
/*     */ import mcheli.MCH_Config;
/*     */ import mcheli.__helper.client._IModelCustom;
/*     */ import mcheli.wrapper.W_Entity;
/*     */ import mcheli.wrapper.W_MovingObjectPosition;
/*     */ import mcheli.wrapper.W_WorldFunc;
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.nbt.NBTTagCompound;
/*     */ import net.minecraft.util.EnumFacing;
/*     */ import net.minecraft.util.math.MathHelper;
/*     */ import net.minecraft.util.math.RayTraceResult;
/*     */ import net.minecraft.util.math.Vec3d;
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
/*     */ public class MCH_EntityCartridge
/*     */   extends W_Entity
/*     */ {
/*     */   public final String texture_name;
/*     */   public final _IModelCustom model;
/*     */   private final float bound;
/*     */   private final float gravity;
/*     */   private final float scale;
/*     */   private int countOnUpdate;
/*     */   public float targetYaw;
/*     */   public float targetPitch;
/*     */   
/*     */   @SideOnly(Side.CLIENT)
/*     */   public static void spawnCartridge(World world, MCH_Cartridge cartridge, double x, double y, double z, double mx, double my, double mz, float yaw, float pitch) {
/*  40 */     if (cartridge != null) {
/*     */ 
/*     */       
/*  43 */       MCH_EntityCartridge entityFX = new MCH_EntityCartridge(world, cartridge, x, y, z, mx + (world.field_73012_v.nextFloat() - 0.5D) * 0.07D, my, mz + (world.field_73012_v.nextFloat() - 0.5D) * 0.07D);
/*     */       
/*  45 */       entityFX.field_70126_B = yaw;
/*  46 */       entityFX.field_70177_z = yaw;
/*  47 */       entityFX.targetYaw = yaw;
/*  48 */       entityFX.field_70127_C = pitch;
/*  49 */       entityFX.field_70125_A = pitch;
/*  50 */       entityFX.targetPitch = pitch;
/*     */       
/*  52 */       float cy = yaw + cartridge.yaw;
/*  53 */       float cp = pitch + cartridge.pitch;
/*  54 */       double tX = (-MathHelper.func_76126_a(cy / 180.0F * 3.1415927F) * MathHelper.func_76134_b(cp / 180.0F * 3.1415927F));
/*  55 */       double tZ = (MathHelper.func_76134_b(cy / 180.0F * 3.1415927F) * MathHelper.func_76134_b(cp / 180.0F * 3.1415927F));
/*  56 */       double tY = -MathHelper.func_76126_a(cp / 180.0F * 3.1415927F);
/*  57 */       double d = MathHelper.func_76133_a(tX * tX + tY * tY + tZ * tZ);
/*     */       
/*  59 */       if (Math.abs(d) > 0.001D) {
/*     */         
/*  61 */         entityFX.field_70159_w += tX * cartridge.acceleration / d;
/*  62 */         entityFX.field_70181_x += tY * cartridge.acceleration / d;
/*  63 */         entityFX.field_70179_y += tZ * cartridge.acceleration / d;
/*     */       } 
/*     */       
/*  66 */       world.func_72838_d((Entity)entityFX);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public MCH_EntityCartridge(World par1World, MCH_Cartridge c, double x, double y, double z, double mx, double my, double mz) {
/*  73 */     super(par1World);
/*  74 */     func_70080_a(x, y, z, 0.0F, 0.0F);
/*  75 */     this.field_70159_w = mx;
/*  76 */     this.field_70181_x = my;
/*  77 */     this.field_70179_y = mz;
/*  78 */     this.texture_name = c.name;
/*  79 */     this.model = c.model;
/*  80 */     this.bound = c.bound;
/*  81 */     this.gravity = c.gravity;
/*  82 */     this.scale = c.scale;
/*  83 */     this.countOnUpdate = 0;
/*     */   }
/*     */ 
/*     */   
/*     */   public float getScale() {
/*  88 */     return this.scale;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void func_70071_h_() {
/*  94 */     this.field_70169_q = this.field_70165_t;
/*  95 */     this.field_70167_r = this.field_70163_u;
/*  96 */     this.field_70166_s = this.field_70161_v;
/*  97 */     this.field_70126_B = this.field_70177_z;
/*  98 */     this.field_70127_C = this.field_70125_A;
/*     */     
/* 100 */     if (this.countOnUpdate < MCH_Config.AliveTimeOfCartridge.prmInt) {
/*     */       
/* 102 */       this.countOnUpdate++;
/*     */     }
/*     */     else {
/*     */       
/* 106 */       func_70106_y();
/*     */     } 
/*     */     
/* 109 */     this.field_70159_w *= 0.98D;
/* 110 */     this.field_70179_y *= 0.98D;
/* 111 */     this.field_70181_x += this.gravity;
/*     */     
/* 113 */     move();
/*     */   }
/*     */ 
/*     */   
/*     */   public void rotation() {
/* 118 */     if (this.field_70177_z < this.targetYaw - 3.0F) {
/*     */       
/* 120 */       this.field_70177_z += 10.0F;
/* 121 */       if (this.field_70177_z > this.targetYaw)
/*     */       {
/* 123 */         this.field_70177_z = this.targetYaw;
/*     */       }
/*     */     }
/* 126 */     else if (this.field_70177_z > this.targetYaw + 3.0F) {
/*     */       
/* 128 */       this.field_70177_z -= 10.0F;
/* 129 */       if (this.field_70177_z < this.targetYaw)
/*     */       {
/* 131 */         this.field_70177_z = this.targetYaw;
/*     */       }
/*     */     } 
/*     */     
/* 135 */     if (this.field_70125_A < this.targetPitch) {
/*     */       
/* 137 */       this.field_70125_A += 10.0F;
/* 138 */       if (this.field_70125_A > this.targetPitch)
/*     */       {
/* 140 */         this.field_70125_A = this.targetPitch;
/*     */       }
/*     */     }
/* 143 */     else if (this.field_70125_A > this.targetPitch) {
/*     */       
/* 145 */       this.field_70125_A -= 10.0F;
/* 146 */       if (this.field_70125_A < this.targetPitch)
/*     */       {
/* 148 */         this.field_70125_A = this.targetPitch;
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void move() {
/* 155 */     Vec3d vec1 = W_WorldFunc.getWorldVec3(this.field_70170_p, this.field_70165_t, this.field_70163_u, this.field_70161_v);
/* 156 */     Vec3d vec2 = W_WorldFunc.getWorldVec3(this.field_70170_p, this.field_70165_t + this.field_70159_w, this.field_70163_u + this.field_70181_x, this.field_70161_v + this.field_70179_y);
/*     */ 
/*     */     
/* 159 */     RayTraceResult m = W_WorldFunc.clip(this.field_70170_p, vec1, vec2);
/*     */     
/* 161 */     double d = Math.max(Math.abs(this.field_70159_w), Math.abs(this.field_70181_x));
/* 162 */     d = Math.max(d, Math.abs(this.field_70179_y));
/*     */     
/* 164 */     if (W_MovingObjectPosition.isHitTypeTile(m)) {
/*     */       
/* 166 */       func_70107_b(m.field_72307_f.field_72450_a, m.field_72307_f.field_72448_b, m.field_72307_f.field_72449_c);
/*     */       
/* 168 */       this.field_70159_w += d * (this.field_70146_Z.nextFloat() - 0.5F) * 0.10000000149011612D;
/* 169 */       this.field_70181_x += d * (this.field_70146_Z.nextFloat() - 0.5F) * 0.10000000149011612D;
/* 170 */       this.field_70179_y += d * (this.field_70146_Z.nextFloat() - 0.5F) * 0.10000000149011612D;
/*     */       
/* 172 */       if (d > 0.10000000149011612D) {
/*     */         
/* 174 */         this.targetYaw += (float)(d * (this.field_70146_Z.nextFloat() - 0.5F) * 720.0D);
/* 175 */         this.targetPitch = (float)(d * (this.field_70146_Z.nextFloat() - 0.5F) * 720.0D);
/*     */       }
/*     */       else {
/*     */         
/* 179 */         this.targetPitch = 0.0F;
/*     */       } 
/*     */       
/* 182 */       switch (m.field_178784_b) {
/*     */ 
/*     */         
/*     */         case DOWN:
/* 186 */           if (this.field_70181_x > 0.0D) {
/* 187 */             this.field_70181_x = -this.field_70181_x * this.bound;
/*     */           }
/*     */           break;
/*     */         case UP:
/* 191 */           if (this.field_70181_x < 0.0D)
/* 192 */             this.field_70181_x = -this.field_70181_x * this.bound; 
/* 193 */           this.targetPitch *= 0.3F;
/*     */           break;
/*     */         
/*     */         case NORTH:
/* 197 */           if (this.field_70179_y > 0.0D) {
/* 198 */             this.field_70179_y = -this.field_70179_y * this.bound; break;
/*     */           } 
/* 200 */           this.field_70161_v += this.field_70179_y;
/*     */           break;
/*     */         
/*     */         case SOUTH:
/* 204 */           if (this.field_70179_y < 0.0D) {
/* 205 */             this.field_70179_y = -this.field_70179_y * this.bound; break;
/*     */           } 
/* 207 */           this.field_70161_v += this.field_70179_y;
/*     */           break;
/*     */         
/*     */         case WEST:
/* 211 */           if (this.field_70159_w > 0.0D) {
/* 212 */             this.field_70159_w = -this.field_70159_w * this.bound; break;
/*     */           } 
/* 214 */           this.field_70165_t += this.field_70159_w;
/*     */           break;
/*     */         
/*     */         case EAST:
/* 218 */           if (this.field_70159_w < 0.0D) {
/* 219 */             this.field_70159_w = -this.field_70159_w * this.bound;
/*     */             break;
/*     */           } 
/* 222 */           this.field_70165_t += this.field_70159_w;
/*     */           break;
/*     */       } 
/*     */ 
/*     */ 
/*     */     
/*     */     } else {
/* 229 */       this.field_70165_t += this.field_70159_w;
/* 230 */       this.field_70163_u += this.field_70181_x;
/* 231 */       this.field_70161_v += this.field_70179_y;
/* 232 */       if (d > 0.05000000074505806D)
/*     */       {
/* 234 */         rotation();
/*     */       }
/*     */     } 
/*     */   }
/*     */   
/*     */   protected void func_70037_a(NBTTagCompound var1) {}
/*     */   
/*     */   protected void func_70014_b(NBTTagCompound var1) {}
/*     */ }


/* Location:              C:\Users\leo\Downloads\Mchelioreal\!\mcheli\weapon\MCH_EntityCartridge.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */