/*     */ package mcheli.wrapper;
/*     */ 
/*     */ import java.util.Arrays;
/*     */ import mcheli.MCH_Lib;
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.entity.item.EntityItem;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.inventory.IInventory;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraft.nbt.NBTBase;
/*     */ import net.minecraft.nbt.NBTTagCompound;
/*     */ import net.minecraft.nbt.NBTTagList;
/*     */ import net.minecraft.util.text.ITextComponent;
/*     */ import net.minecraft.util.text.TextComponentString;
/*     */ import net.minecraft.world.World;
/*     */ import net.minecraftforge.common.util.ITeleporter;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public abstract class W_EntityContainer
/*     */   extends W_Entity
/*     */   implements IInventory
/*     */ {
/*     */   public static final int MAX_INVENTORY_SIZE = 54;
/*     */   private ItemStack[] containerItems;
/*     */   public boolean dropContentsWhenDead = true;
/*     */   
/*     */   public W_EntityContainer(World par1World) {
/*  32 */     super(par1World);
/*  33 */     this.containerItems = new ItemStack[54];
/*  34 */     Arrays.fill((Object[])this.containerItems, ItemStack.field_190927_a);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void func_70088_a() {}
/*     */ 
/*     */ 
/*     */   
/*     */   public ItemStack func_70301_a(int par1) {
/*  45 */     return this.containerItems[par1];
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public int func_70297_j_() {
/*  51 */     return 64;
/*     */   }
/*     */ 
/*     */   
/*     */   public int getUsingSlotNum() {
/*  56 */     int numUsingSlot = 0;
/*     */     
/*  58 */     if (this.containerItems == null) {
/*     */       
/*  60 */       numUsingSlot = 0;
/*     */     }
/*     */     else {
/*     */       
/*  64 */       int n = func_70302_i_();
/*  65 */       numUsingSlot = 0;
/*     */       
/*  67 */       for (int i = 0; i < n && i < this.containerItems.length; i++) {
/*     */ 
/*     */         
/*  70 */         if (!func_70301_a(i).func_190926_b())
/*  71 */           numUsingSlot++; 
/*     */       } 
/*     */     } 
/*  74 */     return numUsingSlot;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean func_191420_l() {
/*  80 */     for (ItemStack itemstack : this.containerItems) {
/*     */       
/*  82 */       if (!itemstack.func_190926_b())
/*     */       {
/*  84 */         return false;
/*     */       }
/*     */     } 
/*  87 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ItemStack func_70298_a(int par1, int par2) {
/*  94 */     if (!this.containerItems[par1].func_190926_b()) {
/*     */ 
/*     */       
/*  97 */       if (this.containerItems[par1].func_190916_E() <= par2) {
/*     */         
/*  99 */         ItemStack itemStack = this.containerItems[par1];
/*     */         
/* 101 */         this.containerItems[par1] = ItemStack.field_190927_a;
/* 102 */         return itemStack;
/*     */       } 
/*     */       
/* 105 */       ItemStack itemstack = this.containerItems[par1].func_77979_a(par2);
/*     */ 
/*     */       
/* 108 */       if (this.containerItems[par1].func_190916_E() == 0)
/*     */       {
/*     */         
/* 111 */         this.containerItems[par1] = ItemStack.field_190927_a;
/*     */       }
/*     */       
/* 114 */       return itemstack;
/*     */     } 
/*     */ 
/*     */     
/* 118 */     return ItemStack.field_190927_a;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ItemStack func_70304_b(int par1) {
/* 125 */     if (!this.containerItems[par1].func_190926_b()) {
/*     */       
/* 127 */       ItemStack itemstack = this.containerItems[par1];
/*     */       
/* 129 */       this.containerItems[par1] = ItemStack.field_190927_a;
/* 130 */       return itemstack;
/*     */     } 
/*     */     
/* 133 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void func_70299_a(int par1, ItemStack par2ItemStack) {
/* 139 */     this.containerItems[par1] = par2ItemStack;
/*     */ 
/*     */     
/* 142 */     if (!par2ItemStack.func_190926_b() && par2ItemStack.func_190916_E() > func_70297_j_())
/*     */     {
/*     */       
/* 145 */       par2ItemStack.func_190920_e(func_70297_j_());
/*     */     }
/*     */     
/* 148 */     func_70296_d();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void onInventoryChanged() {}
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean func_70300_a(EntityPlayer par1EntityPlayer) {
/* 158 */     return !this.field_70128_L;
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
/*     */   public boolean func_94041_b(int par1, ItemStack par2ItemStack) {
/* 172 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isStackValidForSlot(int par1, ItemStack par2ItemStack) {
/* 177 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   public String getInvName() {
/* 182 */     return "Inventory";
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public String func_70005_c_() {
/* 188 */     return getInvName();
/*     */   }
/*     */ 
/*     */   
/*     */   public String getInventoryName() {
/* 193 */     return getInvName();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public ITextComponent func_145748_c_() {
/* 199 */     return (ITextComponent)new TextComponentString(getInventoryName());
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isInvNameLocalized() {
/* 204 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean func_145818_k_() {
/* 210 */     return isInvNameLocalized();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void func_70106_y() {
/* 216 */     if (this.dropContentsWhenDead && !this.field_70170_p.field_72995_K)
/*     */     {
/* 218 */       for (int i = 0; i < func_70302_i_(); i++) {
/*     */         
/* 220 */         ItemStack itemstack = func_70301_a(i);
/*     */ 
/*     */         
/* 223 */         if (!itemstack.func_190926_b()) {
/*     */           
/* 225 */           float x = this.field_70146_Z.nextFloat() * 0.8F + 0.1F;
/* 226 */           float y = this.field_70146_Z.nextFloat() * 0.8F + 0.1F;
/* 227 */           float z = this.field_70146_Z.nextFloat() * 0.8F + 0.1F;
/*     */ 
/*     */           
/* 230 */           while (itemstack.func_190916_E() > 0) {
/*     */             
/* 232 */             int j = this.field_70146_Z.nextInt(21) + 10;
/*     */ 
/*     */             
/* 235 */             if (j > itemstack.func_190916_E())
/*     */             {
/*     */               
/* 238 */               j = itemstack.func_190916_E();
/*     */             }
/*     */ 
/*     */             
/* 242 */             itemstack.func_190918_g(j);
/*     */             
/* 244 */             EntityItem entityitem = new EntityItem(this.field_70170_p, this.field_70165_t + x, this.field_70163_u + y, this.field_70161_v + z, new ItemStack(itemstack.func_77973_b(), j, itemstack.func_77960_j()));
/*     */             
/* 246 */             if (itemstack.func_77942_o())
/*     */             {
/* 248 */               entityitem.func_92059_d().func_77982_d(itemstack.func_77978_p().func_74737_b());
/*     */             }
/*     */             
/* 251 */             float f3 = 0.05F;
/* 252 */             entityitem.field_70159_w = ((float)this.field_70146_Z.nextGaussian() * f3);
/* 253 */             entityitem.field_70181_x = ((float)this.field_70146_Z.nextGaussian() * f3 + 0.2F);
/* 254 */             entityitem.field_70179_y = ((float)this.field_70146_Z.nextGaussian() * f3);
/* 255 */             this.field_70170_p.func_72838_d((Entity)entityitem);
/*     */           } 
/*     */         } 
/*     */       } 
/*     */     }
/*     */     
/* 261 */     super.func_70106_y();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected void func_70014_b(NBTTagCompound par1NBTTagCompound) {
/* 267 */     NBTTagList nbttaglist = new NBTTagList();
/*     */     
/* 269 */     for (int i = 0; i < this.containerItems.length; i++) {
/*     */ 
/*     */       
/* 272 */       if (!this.containerItems[i].func_190926_b()) {
/*     */         
/* 274 */         NBTTagCompound nbttagcompound1 = new NBTTagCompound();
/* 275 */         nbttagcompound1.func_74774_a("Slot", (byte)i);
/* 276 */         this.containerItems[i].func_77955_b(nbttagcompound1);
/* 277 */         nbttaglist.func_74742_a((NBTBase)nbttagcompound1);
/*     */       } 
/*     */     } 
/*     */     
/* 281 */     par1NBTTagCompound.func_74782_a("Items", (NBTBase)nbttaglist);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected void func_70037_a(NBTTagCompound par1NBTTagCompound) {
/* 287 */     NBTTagList nbttaglist = W_NBTTag.getTagList(par1NBTTagCompound, "Items", 10);
/* 288 */     this.containerItems = new ItemStack[func_70302_i_()];
/* 289 */     Arrays.fill((Object[])this.containerItems, ItemStack.field_190927_a);
/*     */     
/* 291 */     MCH_Lib.DbgLog(this.field_70170_p, "W_EntityContainer.readEntityFromNBT.InventorySize = %d", new Object[] {
/*     */           
/* 293 */           Integer.valueOf(func_70302_i_())
/*     */         });
/*     */     
/* 296 */     for (int i = 0; i < nbttaglist.func_74745_c(); i++) {
/*     */       
/* 298 */       NBTTagCompound nbttagcompound1 = W_NBTTag.tagAt(nbttaglist, i);
/* 299 */       int j = nbttagcompound1.func_74771_c("Slot") & 0xFF;
/*     */       
/* 301 */       if (j >= 0 && j < this.containerItems.length)
/*     */       {
/*     */         
/* 304 */         this.containerItems[j] = new ItemStack(nbttagcompound1);
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Entity changeDimension(int dimensionIn, ITeleporter teleporter) {
/* 317 */     this.dropContentsWhenDead = false;
/* 318 */     return super.changeDimension(dimensionIn, teleporter);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean displayInventory(EntityPlayer player) {
/* 324 */     if (!this.field_70170_p.field_72995_K && func_70302_i_() > 0) {
/*     */       
/* 326 */       player.func_71007_a(this);
/* 327 */       return true;
/*     */     } 
/* 329 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void func_174889_b(EntityPlayer player) {}
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void func_174886_c(EntityPlayer player) {}
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void func_70296_d() {}
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int func_70302_i_() {
/* 358 */     return 0;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void func_174888_l() {}
/*     */ 
/*     */ 
/*     */   
/*     */   public int func_174887_a_(int id) {
/* 369 */     return 0;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void func_174885_b(int id, int value) {}
/*     */ 
/*     */ 
/*     */   
/*     */   public int func_174890_g() {
/* 380 */     return 0;
/*     */   }
/*     */ }


/* Location:              C:\Users\leo\Downloads\Mchelioreal\!\mcheli\wrapper\W_EntityContainer.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */