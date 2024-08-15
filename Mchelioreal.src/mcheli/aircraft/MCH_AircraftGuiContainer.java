/*     */ package mcheli.aircraft;
/*     */ 
/*     */ import mcheli.MCH_Lib;
/*     */ import mcheli.uav.MCH_EntityUavStation;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.inventory.Container;
/*     */ import net.minecraft.inventory.IInventory;
/*     */ import net.minecraft.inventory.Slot;
/*     */ import net.minecraft.item.ItemStack;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class MCH_AircraftGuiContainer
/*     */   extends Container
/*     */ {
/*     */   public final EntityPlayer player;
/*     */   public final MCH_EntityAircraft aircraft;
/*     */   
/*     */   public MCH_AircraftGuiContainer(EntityPlayer player, MCH_EntityAircraft ac) {
/*  23 */     this.player = player;
/*  24 */     this.aircraft = ac;
/*     */     
/*  26 */     MCH_AircraftInventory iv = this.aircraft.getGuiInventory();
/*     */     
/*  28 */     func_75146_a(new Slot(iv, 0, 10, 30));
/*     */     
/*  30 */     func_75146_a(new Slot(iv, 1, 10, 48));
/*     */     
/*  32 */     func_75146_a(new Slot(iv, 2, 10, 66));
/*     */     
/*  34 */     int num = this.aircraft.getNumEjectionSeat();
/*  35 */     for (int i = 0; i < num; i++)
/*     */     {
/*     */       
/*  38 */       func_75146_a(new Slot(iv, 3 + i, 10 + 18 * i, 105));
/*     */     }
/*     */     
/*  41 */     for (int y = 0; y < 3; y++) {
/*     */       
/*  43 */       for (int j = 0; j < 9; j++)
/*     */       {
/*  45 */         func_75146_a(new Slot((IInventory)player.field_71071_by, 9 + j + y * 9, 25 + j * 18, 135 + y * 18));
/*     */       }
/*     */     } 
/*     */     
/*  49 */     for (int x = 0; x < 9; x++)
/*     */     {
/*  51 */       func_75146_a(new Slot((IInventory)player.field_71071_by, x, 25 + x * 18, 195));
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public int getInventoryStartIndex() {
/*  57 */     if (this.aircraft == null)
/*  58 */       return 3; 
/*  59 */     return 3 + this.aircraft.getNumEjectionSeat();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void func_75142_b() {
/*  65 */     super.func_75142_b();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean func_75145_c(EntityPlayer player) {
/*  71 */     if (this.aircraft.getGuiInventory().func_70300_a(player))
/*     */     {
/*  73 */       return true;
/*     */     }
/*     */     
/*  76 */     if (this.aircraft.isUAV()) {
/*     */       
/*  78 */       MCH_EntityUavStation us = this.aircraft.getUavStation();
/*     */       
/*  80 */       if (us != null) {
/*     */         
/*  82 */         double x = us.field_70165_t + us.posUavX;
/*  83 */         double z = us.field_70161_v + us.posUavZ;
/*     */         
/*  85 */         if (this.aircraft.field_70165_t < x + 10.0D && this.aircraft.field_70165_t > x - 10.0D && this.aircraft.field_70161_v < z + 10.0D && this.aircraft.field_70161_v > z - 10.0D)
/*     */         {
/*     */           
/*  88 */           return true;
/*     */         }
/*     */       } 
/*     */     } 
/*     */     
/*  93 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public ItemStack func_82846_b(EntityPlayer player, int slotIndex) {
/*  99 */     MCH_AircraftInventory iv = this.aircraft.getGuiInventory();
/* 100 */     Slot slot = this.field_75151_b.get(slotIndex);
/*     */     
/* 102 */     if (slot == null)
/*     */     {
/* 104 */       return null;
/*     */     }
/*     */     
/* 107 */     ItemStack itemStack = slot.func_75211_c();
/* 108 */     MCH_Lib.DbgLog(player.field_70170_p, "transferStackInSlot : %d :" + itemStack, new Object[] {
/*     */           
/* 110 */           Integer.valueOf(slotIndex)
/*     */         });
/*     */ 
/*     */     
/* 114 */     if (itemStack.func_190926_b())
/*     */     {
/*     */       
/* 117 */       return ItemStack.field_190927_a;
/*     */     }
/*     */     
/* 120 */     if (slotIndex < getInventoryStartIndex()) {
/*     */       
/* 122 */       for (int i = getInventoryStartIndex(); i < this.field_75151_b.size(); i++)
/*     */       {
/* 124 */         Slot playerSlot = this.field_75151_b.get(i);
/*     */ 
/*     */         
/* 127 */         if (playerSlot.func_75211_c().func_190926_b())
/*     */         {
/* 129 */           playerSlot.func_75215_d(itemStack);
/*     */           
/* 131 */           slot.func_75215_d(ItemStack.field_190927_a);
/* 132 */           return itemStack;
/*     */         }
/*     */       
/*     */       }
/*     */     
/*     */     }
/* 138 */     else if (itemStack.func_77973_b() instanceof MCH_ItemFuel) {
/*     */       
/* 140 */       for (int i = 0; i < 3; i++) {
/*     */ 
/*     */         
/* 143 */         if (iv.getFuelSlotItemStack(i).func_190926_b())
/*     */         {
/*     */           
/* 146 */           iv.func_70299_a(0 + i, itemStack);
/*     */           
/* 148 */           slot.func_75215_d(ItemStack.field_190927_a);
/* 149 */           return itemStack;
/*     */         }
/*     */       
/*     */       } 
/* 153 */     } else if (itemStack.func_77973_b() instanceof mcheli.parachute.MCH_ItemParachute) {
/*     */       
/* 155 */       int num = this.aircraft.getNumEjectionSeat();
/*     */       
/* 157 */       for (int i = 0; i < num; i++) {
/*     */ 
/*     */         
/* 160 */         if (iv.getParachuteSlotItemStack(i).func_190926_b()) {
/*     */ 
/*     */           
/* 163 */           iv.func_70299_a(3 + i, itemStack);
/*     */           
/* 165 */           slot.func_75215_d(ItemStack.field_190927_a);
/* 166 */           return itemStack;
/*     */         } 
/*     */       } 
/*     */     } 
/*     */ 
/*     */     
/* 172 */     return ItemStack.field_190927_a;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void func_75134_a(EntityPlayer player) {
/* 178 */     super.func_75134_a(player);
/*     */     
/* 180 */     if (!player.field_70170_p.field_72995_K) {
/*     */       
/* 182 */       MCH_AircraftInventory iv = this.aircraft.getGuiInventory();
/*     */       int i;
/* 184 */       for (i = 0; i < 3; i++) {
/*     */         
/* 186 */         ItemStack is = iv.getFuelSlotItemStack(i);
/*     */ 
/*     */         
/* 189 */         if (!is.func_190926_b() && !(is.func_77973_b() instanceof MCH_ItemFuel))
/*     */         {
/*     */           
/* 192 */           dropPlayerItem(player, 0 + i);
/*     */         }
/*     */       } 
/*     */       
/* 196 */       for (i = 0; i < 2; i++) {
/*     */         
/* 198 */         ItemStack is = iv.getParachuteSlotItemStack(i);
/*     */ 
/*     */         
/* 201 */         if (!is.func_190926_b() && !(is.func_77973_b() instanceof mcheli.parachute.MCH_ItemParachute))
/*     */         {
/*     */           
/* 204 */           dropPlayerItem(player, 3 + i);
/*     */         }
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void dropPlayerItem(EntityPlayer player, int slotID) {
/* 212 */     if (!player.field_70170_p.field_72995_K) {
/*     */       
/* 214 */       ItemStack itemstack = this.aircraft.getGuiInventory().func_70304_b(slotID);
/*     */ 
/*     */       
/* 217 */       if (!itemstack.func_190926_b())
/*     */       {
/* 219 */         player.func_71019_a(itemstack, false);
/*     */       }
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Users\leo\Downloads\Mchelioreal\!\mcheli\aircraft\MCH_AircraftGuiContainer.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */