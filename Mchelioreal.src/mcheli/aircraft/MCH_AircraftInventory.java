/*     */ package mcheli.aircraft;
/*     */ 
/*     */ import java.util.Arrays;
/*     */ import java.util.Random;
/*     */ import mcheli.wrapper.W_NBTTag;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class MCH_AircraftInventory
/*     */   implements IInventory
/*     */ {
/*  25 */   public final int SLOT_FUEL0 = 0;
/*  26 */   public final int SLOT_FUEL1 = 1;
/*  27 */   public final int SLOT_FUEL2 = 2;
/*  28 */   public final int SLOT_PARACHUTE0 = 3;
/*  29 */   public final int SLOT_PARACHUTE1 = 4;
/*     */   
/*     */   private ItemStack[] containerItems;
/*     */   
/*     */   final MCH_EntityAircraft aircraft;
/*     */   
/*     */   public MCH_AircraftInventory(MCH_EntityAircraft ac) {
/*  36 */     this.containerItems = new ItemStack[func_70302_i_()];
/*  37 */     Arrays.fill((Object[])this.containerItems, ItemStack.field_190927_a);
/*  38 */     this.aircraft = ac;
/*     */   }
/*     */ 
/*     */   
/*     */   public ItemStack getFuelSlotItemStack(int i) {
/*  43 */     return func_70301_a(0 + i);
/*     */   }
/*     */ 
/*     */   
/*     */   public ItemStack getParachuteSlotItemStack(int i) {
/*  48 */     return func_70301_a(3 + i);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean haveParachute() {
/*  53 */     for (int i = 0; i < 2; i++) {
/*     */       
/*  55 */       ItemStack item = getParachuteSlotItemStack(i);
/*     */ 
/*     */       
/*  58 */       if (!item.func_190926_b() && item.func_77973_b() instanceof mcheli.parachute.MCH_ItemParachute)
/*     */       {
/*  60 */         return true;
/*     */       }
/*     */     } 
/*  63 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public void consumeParachute() {
/*  68 */     for (int i = 0; i < 2; i++) {
/*     */       
/*  70 */       ItemStack item = getParachuteSlotItemStack(i);
/*     */ 
/*     */       
/*  73 */       if (!item.func_190926_b() && item.func_77973_b() instanceof mcheli.parachute.MCH_ItemParachute) {
/*     */ 
/*     */         
/*  76 */         func_70299_a(3 + i, ItemStack.field_190927_a);
/*     */         break;
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public int func_70302_i_() {
/*  85 */     return 10;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean func_191420_l() {
/*  91 */     for (ItemStack itemstack : this.containerItems) {
/*     */       
/*  93 */       if (!itemstack.func_190926_b())
/*     */       {
/*  95 */         return false;
/*     */       }
/*     */     } 
/*     */     
/*  99 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public ItemStack func_70301_a(int var1) {
/* 105 */     return this.containerItems[var1];
/*     */   }
/*     */ 
/*     */   
/*     */   public void setDead() {
/* 110 */     Random rand = new Random();
/* 111 */     if (this.aircraft.dropContentsWhenDead && !this.aircraft.field_70170_p.field_72995_K)
/*     */     {
/* 113 */       for (int i = 0; i < func_70302_i_(); i++) {
/*     */         
/* 115 */         ItemStack itemstack = func_70301_a(i);
/*     */ 
/*     */         
/* 118 */         if (!itemstack.func_190926_b()) {
/*     */           
/* 120 */           float x = rand.nextFloat() * 0.8F + 0.1F;
/* 121 */           float y = rand.nextFloat() * 0.8F + 0.1F;
/* 122 */           float z = rand.nextFloat() * 0.8F + 0.1F;
/*     */ 
/*     */           
/* 125 */           while (itemstack.func_190916_E() > 0) {
/*     */             
/* 127 */             int j = rand.nextInt(21) + 10;
/*     */ 
/*     */             
/* 130 */             if (j > itemstack.func_190916_E())
/*     */             {
/*     */               
/* 133 */               j = itemstack.func_190916_E();
/*     */             }
/*     */ 
/*     */             
/* 137 */             itemstack.func_190918_g(j);
/*     */ 
/*     */             
/* 140 */             EntityItem entityitem = new EntityItem(this.aircraft.field_70170_p, this.aircraft.field_70165_t + x, this.aircraft.field_70163_u + y, this.aircraft.field_70161_v + z, new ItemStack(itemstack.func_77973_b(), j, itemstack.func_77960_j()));
/*     */             
/* 142 */             if (itemstack.func_77942_o())
/*     */             {
/* 144 */               entityitem.func_92059_d().func_77982_d(itemstack.func_77978_p().func_74737_b());
/*     */             }
/*     */             
/* 147 */             float f3 = 0.05F;
/* 148 */             entityitem.field_70159_w = ((float)rand.nextGaussian() * f3);
/* 149 */             entityitem.field_70181_x = ((float)rand.nextGaussian() * f3 + 0.2F);
/* 150 */             entityitem.field_70179_y = ((float)rand.nextGaussian() * f3);
/* 151 */             this.aircraft.field_70170_p.func_72838_d((Entity)entityitem);
/*     */           } 
/*     */         } 
/*     */       } 
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ItemStack func_70298_a(int par1, int par2) {
/* 162 */     if (!this.containerItems[par1].func_190926_b()) {
/*     */ 
/*     */       
/* 165 */       if (this.containerItems[par1].func_190916_E() <= par2) {
/*     */         
/* 167 */         ItemStack itemStack = this.containerItems[par1];
/*     */         
/* 169 */         this.containerItems[par1] = ItemStack.field_190927_a;
/* 170 */         return itemStack;
/*     */       } 
/*     */       
/* 173 */       ItemStack itemstack = this.containerItems[par1].func_77979_a(par2);
/*     */ 
/*     */       
/* 176 */       if (this.containerItems[par1].func_190916_E() == 0)
/*     */       {
/*     */         
/* 179 */         this.containerItems[par1] = ItemStack.field_190927_a;
/*     */       }
/*     */       
/* 182 */       return itemstack;
/*     */     } 
/*     */ 
/*     */     
/* 186 */     return ItemStack.field_190927_a;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ItemStack func_70304_b(int par1) {
/* 193 */     if (!this.containerItems[par1].func_190926_b()) {
/*     */       
/* 195 */       ItemStack itemstack = this.containerItems[par1];
/*     */       
/* 197 */       this.containerItems[par1] = ItemStack.field_190927_a;
/* 198 */       return itemstack;
/*     */     } 
/*     */ 
/*     */     
/* 202 */     return ItemStack.field_190927_a;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void func_70299_a(int par1, ItemStack par2ItemStack) {
/* 208 */     this.containerItems[par1] = par2ItemStack;
/*     */ 
/*     */     
/* 211 */     if (!par2ItemStack.func_190926_b() && par2ItemStack.func_190916_E() > func_70297_j_())
/*     */     {
/*     */       
/* 214 */       par2ItemStack.func_190920_e(func_70297_j_());
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public String getInventoryName() {
/* 220 */     return getInvName();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public String func_70005_c_() {
/* 226 */     return getInvName();
/*     */   }
/*     */ 
/*     */   
/*     */   public String getInvName() {
/* 231 */     if (this.aircraft.getAcInfo() == null)
/* 232 */       return ""; 
/* 233 */     String s = (this.aircraft.getAcInfo()).displayName;
/* 234 */     return (s.length() <= 32) ? s : s.substring(0, 31);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isInvNameLocalized() {
/* 239 */     return (this.aircraft.getAcInfo() != null);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public ITextComponent func_145748_c_() {
/* 245 */     return (ITextComponent)new TextComponentString(getInvName());
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean func_145818_k_() {
/* 251 */     return isInvNameLocalized();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public int func_70297_j_() {
/* 257 */     return 64;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void func_70296_d() {}
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean func_70300_a(EntityPlayer player) {
/* 268 */     return (player.func_70068_e((Entity)this.aircraft) <= 144.0D);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean func_94041_b(int par1, ItemStack par2ItemStack) {
/* 274 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isStackValidForSlot(int par1, ItemStack par2ItemStack) {
/* 279 */     return true;
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
/*     */   protected void writeEntityToNBT(NBTTagCompound par1NBTTagCompound) {
/* 302 */     NBTTagList nbttaglist = new NBTTagList();
/*     */     
/* 304 */     for (int i = 0; i < this.containerItems.length; i++) {
/*     */ 
/*     */       
/* 307 */       if (!this.containerItems[i].func_190926_b()) {
/*     */         
/* 309 */         NBTTagCompound nbttagcompound1 = new NBTTagCompound();
/* 310 */         nbttagcompound1.func_74774_a("SlotAC", (byte)i);
/* 311 */         this.containerItems[i].func_77955_b(nbttagcompound1);
/* 312 */         nbttaglist.func_74742_a((NBTBase)nbttagcompound1);
/*     */       } 
/*     */     } 
/*     */     
/* 316 */     par1NBTTagCompound.func_74782_a("ItemsAC", (NBTBase)nbttaglist);
/*     */   }
/*     */ 
/*     */   
/*     */   protected void readEntityFromNBT(NBTTagCompound par1NBTTagCompound) {
/* 321 */     NBTTagList nbttaglist = W_NBTTag.getTagList(par1NBTTagCompound, "ItemsAC", 10);
/* 322 */     this.containerItems = new ItemStack[func_70302_i_()];
/* 323 */     Arrays.fill((Object[])this.containerItems, ItemStack.field_190927_a);
/*     */     
/* 325 */     for (int i = 0; i < nbttaglist.func_74745_c(); i++) {
/*     */       
/* 327 */       NBTTagCompound nbttagcompound1 = W_NBTTag.tagAt(nbttaglist, i);
/* 328 */       int j = nbttagcompound1.func_74771_c("SlotAC") & 0xFF;
/*     */       
/* 330 */       if (j >= 0 && j < this.containerItems.length)
/*     */       {
/*     */         
/* 333 */         this.containerItems[j] = new ItemStack(nbttagcompound1);
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
/*     */   public void onInventoryChanged() {}
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int func_174887_a_(int id) {
/* 353 */     return 0;
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
/* 364 */     return 0;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void func_174888_l() {
/* 370 */     for (int i = 0; i < func_70302_i_(); i++)
/*     */     {
/* 372 */       this.containerItems[i] = ItemStack.field_190927_a;
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\leo\Downloads\Mchelioreal\!\mcheli\aircraft\MCH_AircraftInventory.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */