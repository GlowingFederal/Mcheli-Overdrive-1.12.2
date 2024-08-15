/*     */ package mcheli.weapon;
/*     */ 
/*     */ import mcheli.MCH_Config;
/*     */ import mcheli.wrapper.W_Blocks;
/*     */ import mcheli.wrapper.W_Item;
/*     */ import net.minecraft.block.Block;
/*     */ import net.minecraft.block.material.Material;
/*     */ import net.minecraft.block.state.IBlockState;
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.item.Item;
/*     */ import net.minecraft.util.EnumActionResult;
/*     */ import net.minecraft.util.EnumFacing;
/*     */ import net.minecraft.util.EnumHand;
/*     */ import net.minecraft.util.math.BlockPos;
/*     */ import net.minecraft.util.math.RayTraceResult;
/*     */ import net.minecraft.world.World;
/*     */ import net.minecraftforge.event.ForgeEventFactory;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class MCH_EntityDispensedItem
/*     */   extends MCH_EntityBaseBullet
/*     */ {
/*     */   public MCH_EntityDispensedItem(World par1World) {
/*  30 */     super(par1World);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public MCH_EntityDispensedItem(World par1World, double posX, double posY, double posZ, double targetX, double targetY, double targetZ, float yaw, float pitch, double acceleration) {
/*  36 */     super(par1World, posX, posY, posZ, targetX, targetY, targetZ, yaw, pitch, acceleration);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void func_70071_h_() {
/*  42 */     super.func_70071_h_();
/*     */     
/*  44 */     if (getInfo() != null && !(getInfo()).disableSmoke)
/*     */     {
/*  46 */       spawnParticle((getInfo()).trajectoryParticleName, 3, 7.0F * (getInfo()).smokeSize);
/*     */     }
/*     */     
/*  49 */     if (!this.field_70170_p.field_72995_K && getInfo() != null) {
/*     */       
/*  51 */       if (this.acceleration < 1.0E-4D) {
/*     */         
/*  53 */         this.field_70159_w *= 0.999D;
/*  54 */         this.field_70179_y *= 0.999D;
/*     */       } 
/*     */       
/*  57 */       if (func_70090_H()) {
/*     */         
/*  59 */         this.field_70159_w *= (getInfo()).velocityInWater;
/*  60 */         this.field_70181_x *= (getInfo()).velocityInWater;
/*  61 */         this.field_70179_y *= (getInfo()).velocityInWater;
/*     */       } 
/*     */     } 
/*     */     
/*  65 */     onUpdateBomblet();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void onImpact(RayTraceResult m, float damageFactor) {
/*  72 */     if (!this.field_70170_p.field_72995_K) {
/*     */ 
/*     */ 
/*     */       
/*  76 */       func_174826_a(func_174813_aQ().func_72317_d(0.0D, 2000.0D, 0.0D));
/*     */       
/*  78 */       EntityPlayer player = null;
/*  79 */       Item item = null;
/*  80 */       int itemDamage = 0;
/*     */       
/*  82 */       if (m != null && getInfo() != null) {
/*     */         
/*  84 */         if (this.shootingAircraft instanceof EntityPlayer)
/*     */         {
/*  86 */           player = (EntityPlayer)this.shootingAircraft;
/*     */         }
/*     */         
/*  89 */         if (this.shootingEntity instanceof EntityPlayer)
/*     */         {
/*  91 */           player = (EntityPlayer)this.shootingEntity;
/*     */         }
/*     */         
/*  94 */         item = (getInfo()).dispenseItem;
/*  95 */         itemDamage = (getInfo()).dispenseDamege;
/*     */       } 
/*     */       
/*  98 */       if (player != null && !player.field_70128_L && item != null) {
/*     */         
/* 100 */         MCH_DummyEntityPlayer mCH_DummyEntityPlayer = new MCH_DummyEntityPlayer(this.field_70170_p, player);
/*     */         
/* 102 */         ((EntityPlayer)mCH_DummyEntityPlayer).field_70125_A = 90.0F;
/*     */         
/* 104 */         int RNG = (getInfo()).dispenseRange - 1;
/*     */         
/* 106 */         for (int x = -RNG; x <= RNG; x++) {
/*     */           
/* 108 */           for (int y = -RNG; y <= RNG; y++) {
/*     */             
/* 110 */             if (y >= 0 && y < 256)
/*     */             {
/* 112 */               for (int z = -RNG; z <= RNG; z++) {
/*     */                 
/* 114 */                 int dist = x * x + y * y + z * z;
/*     */                 
/* 116 */                 if (dist <= RNG * RNG)
/*     */                 {
/* 118 */                   if (dist <= 0.5D * RNG * RNG) {
/*     */ 
/*     */                     
/* 121 */                     useItemToBlock(m.func_178782_a().func_177982_a(x, y, z), item, itemDamage, (EntityPlayer)mCH_DummyEntityPlayer);
/*     */                   }
/* 123 */                   else if (this.field_70146_Z.nextInt(2) == 0) {
/*     */ 
/*     */                     
/* 126 */                     useItemToBlock(m.func_178782_a().func_177982_a(x, y, z), item, itemDamage, (EntityPlayer)mCH_DummyEntityPlayer);
/*     */                   } 
/*     */                 }
/*     */               } 
/*     */             }
/*     */           } 
/*     */         } 
/*     */       } 
/*     */       
/* 135 */       func_70106_y();
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void useItemToBlock(BlockPos blockpos, Item item, int itemDamage, EntityPlayer dummyPlayer) {
/* 145 */     dummyPlayer.field_70165_t = blockpos.func_177958_n() + 0.5D;
/* 146 */     dummyPlayer.field_70163_u = blockpos.func_177956_o() + 2.5D;
/* 147 */     dummyPlayer.field_70161_v = blockpos.func_177952_p() + 0.5D;
/* 148 */     dummyPlayer.field_70177_z = this.field_70146_Z.nextInt(360);
/*     */ 
/*     */ 
/*     */     
/* 152 */     IBlockState iblockstate = this.field_70170_p.func_180495_p(blockpos);
/* 153 */     Block block = iblockstate.func_177230_c();
/* 154 */     Material blockMat = iblockstate.func_185904_a();
/*     */     
/* 156 */     if (block != W_Blocks.field_150350_a && blockMat != Material.field_151579_a)
/*     */     {
/* 158 */       if (item == W_Item.getItemByName("water_bucket")) {
/*     */         
/* 160 */         if (MCH_Config.Collision_DestroyBlock.prmBool)
/*     */         {
/* 162 */           if (blockMat == Material.field_151581_o)
/*     */           {
/*     */             
/* 165 */             this.field_70170_p.func_175698_g(blockpos);
/*     */           }
/* 167 */           else if (blockMat == Material.field_151587_i)
/*     */           {
/*     */             
/* 170 */             int metadata = block.func_176201_c(iblockstate);
/*     */             
/* 172 */             if (metadata == 0)
/*     */             {
/*     */               
/* 175 */               this.field_70170_p.func_175656_a(blockpos, ForgeEventFactory.fireFluidPlaceBlockEvent(this.field_70170_p, blockpos, blockpos, W_Blocks.field_150343_Z
/* 176 */                     .func_176223_P()));
/*     */             }
/* 178 */             else if (metadata <= 4)
/*     */             {
/*     */               
/* 181 */               this.field_70170_p.func_175656_a(blockpos, ForgeEventFactory.fireFluidPlaceBlockEvent(this.field_70170_p, blockpos, blockpos, W_Blocks.field_150347_e
/* 182 */                     .func_176223_P()));
/*     */             }
/*     */           
/*     */           }
/*     */         
/*     */         }
/* 188 */       } else if (item.onItemUseFirst(dummyPlayer, this.field_70170_p, blockpos, EnumFacing.UP, blockpos.func_177958_n(), blockpos
/* 189 */           .func_177956_o(), blockpos.func_177952_p(), EnumHand.MAIN_HAND) == EnumActionResult.PASS) {
/*     */ 
/*     */         
/* 192 */         if (item.func_180614_a(dummyPlayer, this.field_70170_p, blockpos, EnumHand.MAIN_HAND, EnumFacing.UP, blockpos
/* 193 */             .func_177958_n(), blockpos.func_177956_o(), blockpos.func_177952_p()) == EnumActionResult.PASS)
/*     */         {
/*     */           
/* 196 */           item.func_77659_a(this.field_70170_p, dummyPlayer, EnumHand.MAIN_HAND);
/*     */         }
/*     */       } 
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void sprinkleBomblet() {
/* 205 */     if (!this.field_70170_p.field_72995_K) {
/*     */ 
/*     */       
/* 208 */       MCH_EntityDispensedItem e = new MCH_EntityDispensedItem(this.field_70170_p, this.field_70165_t, this.field_70163_u, this.field_70161_v, this.field_70159_w, this.field_70181_x, this.field_70179_y, this.field_70146_Z.nextInt(360), 0.0F, this.acceleration);
/*     */       
/* 210 */       e.setParameterFromWeapon(this, this.shootingAircraft, this.shootingEntity);
/* 211 */       e.setName(func_70005_c_());
/*     */ 
/*     */       
/* 214 */       float RANDOM = (getInfo()).bombletDiff;
/* 215 */       e.field_70159_w = this.field_70159_w * 1.0D + ((this.field_70146_Z.nextFloat() - 0.5F) * RANDOM);
/* 216 */       e.field_70181_x = this.field_70181_x * 1.0D / 2.0D + ((this.field_70146_Z.nextFloat() - 0.5F) * RANDOM / 2.0F);
/* 217 */       e.field_70179_y = this.field_70179_y * 1.0D + ((this.field_70146_Z.nextFloat() - 0.5F) * RANDOM);
/* 218 */       e.setBomblet();
/*     */       
/* 220 */       this.field_70170_p.func_72838_d((Entity)e);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public MCH_BulletModel getDefaultBulletModel() {
/* 227 */     return MCH_DefaultBulletModels.Bomb;
/*     */   }
/*     */ }


/* Location:              C:\Users\leo\Downloads\Mchelioreal\!\mcheli\weapon\MCH_EntityDispensedItem.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */