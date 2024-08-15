/*     */ package mcheli.aircraft;
/*     */ 
/*     */ import mcheli.wrapper.W_Entity;
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.entity.player.EntityPlayer;
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
/*     */ public class MCH_EntityHitBox
/*     */   extends W_Entity
/*     */ {
/*     */   public MCH_EntityAircraft parent;
/*     */   public int debugId;
/*     */   
/*     */   public MCH_EntityHitBox(World world) {
/*  27 */     super(world);
/*  28 */     func_70105_a(1.0F, 1.0F);
/*     */     
/*  30 */     this.field_70159_w = 0.0D;
/*  31 */     this.field_70181_x = 0.0D;
/*  32 */     this.field_70179_y = 0.0D;
/*  33 */     this.parent = null;
/*  34 */     this.field_70158_ak = true;
/*  35 */     this.field_70178_ae = true;
/*     */   }
/*     */ 
/*     */   
/*     */   public MCH_EntityHitBox(World world, MCH_EntityAircraft ac, float w, float h) {
/*  40 */     this(world);
/*  41 */     func_70107_b(ac.field_70165_t, ac.field_70163_u + 1.0D, ac.field_70161_v);
/*  42 */     this.field_70169_q = ac.field_70165_t;
/*  43 */     this.field_70167_r = ac.field_70163_u + 1.0D;
/*  44 */     this.field_70166_s = ac.field_70161_v;
/*  45 */     this.parent = ac;
/*  46 */     func_70105_a(w, h);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected boolean func_70041_e_() {
/*  52 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public AxisAlignedBB func_70114_g(Entity par1Entity) {
/*  59 */     return par1Entity.func_174813_aQ();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public AxisAlignedBB func_70046_E() {
/*  66 */     return func_174813_aQ();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean func_70104_M() {
/*  72 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public double func_70042_X() {
/*  78 */     return -0.3D;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean func_70097_a(DamageSource par1DamageSource, float par2) {
/*  84 */     if (this.parent != null)
/*     */     {
/*  86 */       return this.parent.func_70097_a(par1DamageSource, par2);
/*     */     }
/*  88 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean func_70067_L() {
/*  94 */     return !this.field_70128_L;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void func_70106_y() {
/* 100 */     super.func_70106_y();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void func_70071_h_() {
/* 106 */     super.func_70071_h_();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void func_70014_b(NBTTagCompound par1NBTTagCompound) {}
/*     */ 
/*     */ 
/*     */   
/*     */   protected void func_70037_a(NBTTagCompound par1NBTTagCompound) {}
/*     */ 
/*     */ 
/*     */   
/*     */   @SideOnly(Side.CLIENT)
/*     */   public float getShadowSize() {
/* 122 */     return 0.0F;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean func_184230_a(EntityPlayer player, EnumHand hand) {
/* 130 */     return (this.parent != null) ? this.parent.func_184230_a(player, hand) : false;
/*     */   }
/*     */ }


/* Location:              C:\Users\leo\Downloads\Mchelioreal\!\mcheli\aircraft\MCH_EntityHitBox.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */