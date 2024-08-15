/*     */ package mcheli;
/*     */ 
/*     */ import java.util.ArrayList;
/*     */ import java.util.Collections;
/*     */ import java.util.Comparator;
/*     */ import java.util.List;
/*     */ import mcheli.aircraft.MCH_AircraftInfo;
/*     */ import mcheli.aircraft.MCH_ItemAircraft;
/*     */ import mcheli.wrapper.W_Item;
/*     */ import net.minecraft.creativetab.CreativeTabs;
/*     */ import net.minecraft.item.Item;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraft.util.NonNullList;
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
/*     */ public class MCH_CreativeTabs
/*     */   extends CreativeTabs
/*     */ {
/*     */   private List<ItemStack> iconItems;
/*     */   private ItemStack lastItem;
/*     */   private int currentIconIndex;
/*     */   private int switchItemWait;
/*  32 */   private Item fixedItem = null;
/*     */ 
/*     */   
/*     */   public MCH_CreativeTabs(String label) {
/*  36 */     super(label);
/*  37 */     this.iconItems = new ArrayList<>();
/*  38 */     this.currentIconIndex = 0;
/*  39 */     this.switchItemWait = 0;
/*  40 */     this.lastItem = ItemStack.field_190927_a;
/*     */   }
/*     */ 
/*     */   
/*     */   public void setFixedIconItem(String itemName) {
/*  45 */     if (itemName.indexOf(':') >= 0) {
/*     */       
/*  47 */       this.fixedItem = W_Item.getItemByName(itemName);
/*     */     
/*     */     }
/*     */     else {
/*     */ 
/*     */       
/*  53 */       this.fixedItem = W_Item.getItemByName("mcheli:" + itemName);
/*     */       
/*  55 */       if (this.fixedItem != null);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ItemStack func_78016_d() {
/*  66 */     if (this.iconItems.size() <= 0)
/*     */     {
/*  68 */       return ItemStack.field_190927_a;
/*     */     }
/*  70 */     this.currentIconIndex = (this.currentIconIndex + 1) % this.iconItems.size();
/*     */     
/*  72 */     return this.iconItems.get(this.currentIconIndex);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public ItemStack func_151244_d() {
/*  78 */     if (this.fixedItem != null)
/*     */     {
/*  80 */       return new ItemStack(this.fixedItem, 1, 0);
/*     */     }
/*     */     
/*  83 */     if (this.switchItemWait > 0) {
/*     */       
/*  85 */       this.switchItemWait--;
/*     */     }
/*     */     else {
/*     */       
/*  89 */       this.lastItem = func_78016_d();
/*  90 */       this.switchItemWait = 60;
/*     */     } 
/*     */ 
/*     */     
/*  94 */     if (this.lastItem.func_190926_b())
/*     */     {
/*     */       
/*  97 */       this.lastItem = new ItemStack(W_Item.getItemByName("iron_block"));
/*     */     }
/*     */ 
/*     */     
/* 101 */     return this.lastItem;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @SideOnly(Side.CLIENT)
/*     */   public void func_78018_a(NonNullList<ItemStack> list) {
/* 109 */     super.func_78018_a(list);
/*     */ 
/*     */     
/* 112 */     Comparator<ItemStack> cmp = new Comparator<ItemStack>()
/*     */       {
/*     */         
/*     */         public int compare(ItemStack i1, ItemStack i2)
/*     */         {
/* 117 */           if (i1.func_77973_b() instanceof MCH_ItemAircraft && i2.func_77973_b() instanceof MCH_ItemAircraft) {
/*     */             
/* 119 */             MCH_AircraftInfo info1 = ((MCH_ItemAircraft)i1.func_77973_b()).getAircraftInfo();
/* 120 */             MCH_AircraftInfo info2 = ((MCH_ItemAircraft)i2.func_77973_b()).getAircraftInfo();
/* 121 */             if (info1 != null && info2 != null) {
/*     */               
/* 123 */               String s1 = info1.category + "." + info1.name;
/* 124 */               String s2 = info2.category + "." + info2.name;
/* 125 */               return s1.compareTo(s2);
/*     */             } 
/*     */           } 
/* 128 */           return i1.func_77973_b().func_77658_a().compareTo(i2.func_77973_b().func_77658_a());
/*     */         }
/*     */       };
/*     */     
/* 132 */     Collections.sort((List<ItemStack>)list, cmp);
/*     */   }
/*     */ 
/*     */   
/*     */   public void addIconItem(Item i) {
/* 137 */     if (i != null)
/*     */     {
/* 139 */       this.iconItems.add(new ItemStack(i));
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public String func_78024_c() {
/* 145 */     return "MC Heli";
/*     */   }
/*     */ }


/* Location:              C:\Users\leo\Downloads\Mchelioreal\!\mcheli\MCH_CreativeTabs.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */