/*     */ package mcheli.wrapper;
/*     */ 
/*     */ import javax.annotation.Nullable;
/*     */ import net.minecraft.block.Block;
/*     */ import net.minecraft.crash.CrashReportCategory;
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.entity.EntityLivingBase;
/*     */ import net.minecraft.entity.item.EntityItem;
/*     */ import net.minecraft.item.Item;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraft.potion.Potion;
/*     */ import net.minecraft.potion.PotionEffect;
/*     */ import net.minecraft.util.DamageSource;
/*     */ import net.minecraft.util.math.BlockPos;
/*     */ import net.minecraft.world.Explosion;
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
/*     */ public abstract class W_Entity
/*     */   extends Entity
/*     */ {
/*  30 */   protected double _renderDistanceWeight = 1.0D;
/*     */ 
/*     */   
/*     */   public W_Entity(World par1World) {
/*  34 */     super(par1World);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected void func_70088_a() {}
/*     */ 
/*     */ 
/*     */   
/*     */   public static boolean isEntityFallingBlock(Entity entity) {
/*  44 */     return entity instanceof net.minecraft.entity.item.EntityFallingBlock;
/*     */   }
/*     */ 
/*     */   
/*     */   public static int getEntityId(@Nullable Entity entity) {
/*  49 */     return (entity != null) ? entity.func_145782_y() : -1;
/*     */   }
/*     */ 
/*     */   
/*     */   public static boolean isEqual(@Nullable Entity e1, @Nullable Entity e2) {
/*  54 */     int i1 = getEntityId(e1);
/*  55 */     int i2 = getEntityId(e2);
/*  56 */     return (i1 == i2);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public EntityItem func_145778_a(Item item, int par2, float par3) {
/*  62 */     return func_70099_a(new ItemStack(item, par2, 0), par3);
/*     */   }
/*     */ 
/*     */   
/*     */   public String getEntityName() {
/*  67 */     return func_70022_Q();
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
/*     */   public boolean attackEntityFrom(DamageSource par1DamageSource, int par2) {
/*  84 */     return func_70097_a(par1DamageSource, par2);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean func_70097_a(DamageSource par1DamageSource, float par2) {
/*  90 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public static boolean attackEntityFrom(Entity entity, DamageSource ds, float par2) {
/*  95 */     return entity.func_70097_a(ds, par2);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void func_85029_a(CrashReportCategory par1CrashReportCategory) {
/* 101 */     super.func_85029_a(par1CrashReportCategory);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static float getBlockExplosionResistance(Entity entity, Explosion par1Explosion, World par2World, int par3, int par4, int par5, Block par6Block) {
/* 107 */     if (par6Block != null) {
/*     */       
/*     */       try {
/*     */ 
/*     */         
/* 112 */         return entity.func_180428_a(par1Explosion, par2World, new BlockPos(par3, par4, par5), par6Block
/* 113 */             .func_176223_P());
/*     */       
/*     */       }
/* 116 */       catch (Exception e) {
/*     */         
/* 118 */         e.printStackTrace();
/*     */       } 
/*     */     }
/*     */     
/* 122 */     return 0.0F;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static boolean shouldExplodeBlock(Entity entity, Explosion par1Explosion, World par2World, int par3, int par4, int par5, int par6, float par7) {
/* 129 */     return entity.func_174816_a(par1Explosion, par2World, new BlockPos(par3, par4, par5), 
/* 130 */         W_Block.getBlockById(par6).func_176223_P(), par7);
/*     */   }
/*     */ 
/*     */   
/*     */   public static PotionEffect getActivePotionEffect(Entity entity, Potion par1Potion) {
/* 135 */     return (entity instanceof EntityLivingBase) ? ((EntityLivingBase)entity).func_70660_b(par1Potion) : null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void removePotionEffectClient(Entity entity, Potion potion) {
/* 142 */     if (entity instanceof EntityLivingBase)
/*     */     {
/*     */       
/* 145 */       ((EntityLivingBase)entity).func_184589_d(potion);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static void removePotionEffect(Entity entity, Potion potion) {
/* 152 */     if (entity instanceof EntityLivingBase)
/*     */     {
/*     */       
/* 155 */       ((EntityLivingBase)entity).func_184589_d(potion);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public static void addPotionEffect(Entity entity, PotionEffect pe) {
/* 161 */     if (entity instanceof EntityLivingBase)
/*     */     {
/* 163 */       ((EntityLivingBase)entity).func_70690_d(pe);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected void func_145775_I() {
/* 170 */     super.func_145775_I();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   @SideOnly(Side.CLIENT)
/*     */   public boolean func_70112_a(double distance) {
/* 177 */     double d0 = func_174813_aQ().func_72320_b();
/*     */     
/* 179 */     if (Double.isNaN(d0))
/*     */     {
/* 181 */       d0 = 1.0D;
/*     */     }
/*     */     
/* 184 */     d0 = d0 * 64.0D * this._renderDistanceWeight;
/* 185 */     return (distance < d0 * d0);
/*     */   }
/*     */ }


/* Location:              C:\Users\leo\Downloads\Mchelioreal\!\mcheli\wrapper\W_Entity.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */