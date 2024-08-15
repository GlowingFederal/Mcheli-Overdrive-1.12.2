/*     */ package mcheli.tool;
/*     */ 
/*     */ import com.google.common.collect.Multimap;
/*     */ import java.util.List;
/*     */ import java.util.Random;
/*     */ import mcheli.MCH_MOD;
/*     */ import mcheli.aircraft.MCH_EntityAircraft;
/*     */ import mcheli.aircraft.MCH_EntitySeat;
/*     */ import mcheli.wrapper.W_Item;
/*     */ import mcheli.wrapper.W_WorldFunc;
/*     */ import net.minecraft.block.material.Material;
/*     */ import net.minecraft.block.state.IBlockState;
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.entity.EntityLivingBase;
/*     */ import net.minecraft.entity.SharedMonsterAttributes;
/*     */ import net.minecraft.entity.ai.attributes.AttributeModifier;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.inventory.EntityEquipmentSlot;
/*     */ import net.minecraft.item.EnumAction;
/*     */ import net.minecraft.item.Item;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraft.nbt.NBTTagCompound;
/*     */ import net.minecraft.util.ActionResult;
/*     */ import net.minecraft.util.EnumActionResult;
/*     */ import net.minecraft.util.EnumHand;
/*     */ import net.minecraft.util.math.AxisAlignedBB;
/*     */ import net.minecraft.util.math.BlockPos;
/*     */ import net.minecraft.util.math.RayTraceResult;
/*     */ import net.minecraft.util.math.Vec3d;
/*     */ import net.minecraft.world.World;
/*     */ import net.minecraftforge.fml.relauncher.Side;
/*     */ import net.minecraftforge.fml.relauncher.SideOnly;
/*     */ import net.minecraftforge.oredict.OreDictionary;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class MCH_ItemWrench
/*     */   extends W_Item
/*     */ {
/*     */   private float damageVsEntity;
/*     */   private final Item.ToolMaterial toolMaterial;
/*  48 */   private static Random rand = new Random();
/*     */ 
/*     */   
/*     */   public MCH_ItemWrench(int itemId, Item.ToolMaterial material) {
/*  52 */     super(itemId);
/*  53 */     this.toolMaterial = material;
/*  54 */     this.field_77777_bU = 1;
/*  55 */     func_77656_e(material.func_77997_a());
/*     */     
/*  57 */     this.damageVsEntity = 4.0F + material.func_78000_c();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean func_150897_b(IBlockState blockIn) {
/*  65 */     Material material = blockIn.func_185904_a();
/*     */     
/*  67 */     if (material == Material.field_151573_f)
/*     */     {
/*  69 */       return true;
/*     */     }
/*     */     
/*  72 */     if (material instanceof net.minecraft.block.material.MaterialLogic)
/*     */     {
/*  74 */       return true;
/*     */     }
/*     */     
/*  77 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public float func_150893_a(ItemStack stack, IBlockState state) {
/*  85 */     Material material = state.func_185904_a();
/*     */     
/*  87 */     if (material == Material.field_151573_f)
/*     */     {
/*  89 */       return 20.5F;
/*     */     }
/*     */     
/*  92 */     if (material instanceof net.minecraft.block.material.MaterialLogic)
/*     */     {
/*  94 */       return 5.5F;
/*     */     }
/*     */     
/*  97 */     return 2.0F;
/*     */   }
/*     */ 
/*     */   
/*     */   public static float getUseAnimSmooth(ItemStack stack, float partialTicks) {
/* 102 */     int i = Math.abs(getUseAnimCount(stack) - 8);
/* 103 */     int j = Math.abs(getUseAnimPrevCount(stack) - 8);
/*     */     
/* 105 */     return j + (i - j) * partialTicks;
/*     */   }
/*     */ 
/*     */   
/*     */   public static int getUseAnimPrevCount(ItemStack stack) {
/* 110 */     return getAnimCount(stack, "MCH_WrenchAnimPrev");
/*     */   }
/*     */ 
/*     */   
/*     */   public static int getUseAnimCount(ItemStack stack) {
/* 115 */     return getAnimCount(stack, "MCH_WrenchAnim");
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static void setUseAnimCount(ItemStack stack, int n, int prev) {
/* 121 */     setAnimCount(stack, "MCH_WrenchAnim", n);
/* 122 */     setAnimCount(stack, "MCH_WrenchAnimPrev", prev);
/*     */   }
/*     */ 
/*     */   
/*     */   public static int getAnimCount(ItemStack stack, String name) {
/* 127 */     if (!stack.func_77942_o())
/*     */     {
/*     */       
/* 130 */       stack.func_77982_d(new NBTTagCompound());
/*     */     }
/*     */ 
/*     */     
/* 134 */     if (stack.func_77978_p().func_74764_b(name))
/*     */     {
/*     */       
/* 137 */       return stack.func_77978_p().func_74762_e(name);
/*     */     }
/*     */ 
/*     */     
/* 141 */     stack.func_77978_p().func_74768_a(name, 0);
/*     */     
/* 143 */     return 0;
/*     */   }
/*     */ 
/*     */   
/*     */   public static void setAnimCount(ItemStack stack, String name, int n) {
/* 148 */     if (!stack.func_77942_o())
/*     */     {
/*     */       
/* 151 */       stack.func_77982_d(new NBTTagCompound());
/*     */     }
/*     */ 
/*     */     
/* 155 */     stack.func_77978_p().func_74768_a(name, n);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean func_77644_a(ItemStack itemStack, EntityLivingBase entity, EntityLivingBase player) {
/* 161 */     if (!player.field_70170_p.field_72995_K)
/*     */     {
/* 163 */       if (rand.nextInt(40) == 0) {
/*     */         
/* 165 */         entity.func_70099_a(new ItemStack(W_Item.getItemByName("iron_ingot"), 1, 0), 0.0F);
/*     */       }
/* 167 */       else if (rand.nextInt(20) == 0) {
/*     */         
/* 169 */         entity.func_70099_a(new ItemStack(W_Item.getItemByName("gunpowder"), 1, 0), 0.0F);
/*     */       } 
/*     */     }
/*     */     
/* 173 */     itemStack.func_77972_a(2, player);
/* 174 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void func_77615_a(ItemStack stack, World worldIn, EntityLivingBase entityLiving, int timeLeft) {
/* 182 */     setUseAnimCount(stack, 0, 0);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void onUsingTick(ItemStack stack, EntityLivingBase player, int count) {
/* 189 */     if (player.field_70170_p.field_72995_K) {
/*     */       
/* 191 */       MCH_EntityAircraft ac = getMouseOverAircraft(player);
/*     */       
/* 193 */       if (ac != null) {
/*     */         
/* 195 */         int cnt = getUseAnimCount(stack);
/* 196 */         int prev = cnt;
/*     */         
/* 198 */         if (cnt <= 0) {
/*     */           
/* 200 */           cnt = 16;
/*     */         }
/*     */         else {
/*     */           
/* 204 */           cnt--;
/*     */         } 
/*     */ 
/*     */         
/* 208 */         setUseAnimCount(stack, cnt, prev);
/*     */       } 
/*     */     } 
/*     */     
/* 212 */     if (!player.field_70170_p.field_72995_K && count < func_77626_a(stack) && count % 20 == 0) {
/*     */       
/* 214 */       MCH_EntityAircraft ac = getMouseOverAircraft(player);
/*     */       
/* 216 */       if (ac != null && ac.getHP() > 0 && ac.repair(10)) {
/*     */         
/* 218 */         stack.func_77972_a(1, player);
/*     */         
/* 220 */         W_WorldFunc.MOD_playSoundEffect(player.field_70170_p, (int)ac.field_70165_t, (int)ac.field_70163_u, (int)ac.field_70161_v, "wrench", 1.0F, 0.9F + rand
/* 221 */             .nextFloat() * 0.2F);
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void func_77663_a(ItemStack item, World world, Entity entity, int n, boolean b) {
/* 229 */     if (entity instanceof EntityPlayer) {
/*     */       
/* 231 */       EntityPlayer player = (EntityPlayer)entity;
/*     */       
/* 233 */       ItemStack itemStack = player.func_184614_ca();
/*     */       
/* 235 */       if (itemStack == item)
/*     */       {
/* 237 */         MCH_MOD.proxy.setCreativeDigDelay(0);
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public MCH_EntityAircraft getMouseOverAircraft(EntityLivingBase entity) {
/* 246 */     RayTraceResult m = getMouseOver(entity, 1.0F);
/* 247 */     MCH_EntityAircraft ac = null;
/*     */     
/* 249 */     if (m != null)
/*     */     {
/* 251 */       if (m.field_72308_g instanceof MCH_EntityAircraft) {
/*     */         
/* 253 */         ac = (MCH_EntityAircraft)m.field_72308_g;
/*     */       }
/* 255 */       else if (m.field_72308_g instanceof MCH_EntitySeat) {
/*     */         
/* 257 */         MCH_EntitySeat seat = (MCH_EntitySeat)m.field_72308_g;
/*     */         
/* 259 */         if (seat.getParent() != null)
/*     */         {
/* 261 */           ac = seat.getParent();
/*     */         }
/*     */       } 
/*     */     }
/* 265 */     return ac;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private static RayTraceResult rayTrace(EntityLivingBase entity, double dist, float tick) {
/* 271 */     Vec3d vec3 = new Vec3d(entity.field_70165_t, entity.field_70163_u + entity.func_70047_e(), entity.field_70161_v);
/* 272 */     Vec3d vec31 = entity.func_70676_i(tick);
/* 273 */     Vec3d vec32 = vec3.func_72441_c(vec31.field_72450_a * dist, vec31.field_72448_b * dist, vec31.field_72449_c * dist);
/* 274 */     return entity.field_70170_p.func_147447_a(vec3, vec32, false, false, true);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private RayTraceResult getMouseOver(EntityLivingBase user, float tick) {
/* 280 */     Entity pointedEntity = null;
/* 281 */     double d0 = 4.0D;
/*     */     
/* 283 */     RayTraceResult objectMouseOver = rayTrace(user, d0, tick);
/* 284 */     double d1 = d0;
/* 285 */     Vec3d vec3 = new Vec3d(user.field_70165_t, user.field_70163_u + user.func_70047_e(), user.field_70161_v);
/*     */     
/* 287 */     if (objectMouseOver != null)
/*     */     {
/* 289 */       d1 = objectMouseOver.field_72307_f.func_72438_d(vec3);
/*     */     }
/*     */     
/* 292 */     Vec3d vec31 = user.func_70676_i(tick);
/* 293 */     Vec3d vec32 = vec3.func_72441_c(vec31.field_72450_a * d0, vec31.field_72448_b * d0, vec31.field_72449_c * d0);
/* 294 */     pointedEntity = null;
/* 295 */     Vec3d vec33 = null;
/* 296 */     float f1 = 1.0F;
/*     */ 
/*     */     
/* 299 */     List<Entity> list = user.field_70170_p.func_72839_b((Entity)user, user
/* 300 */         .func_174813_aQ().func_72321_a(vec31.field_72450_a * d0, vec31.field_72448_b * d0, vec31.field_72449_c * d0).func_72314_b(f1, f1, f1));
/*     */     
/* 302 */     double d2 = d1;
/*     */     
/* 304 */     for (int i = 0; i < list.size(); i++) {
/*     */       
/* 306 */       Entity entity = list.get(i);
/*     */       
/* 308 */       if (entity.func_70067_L()) {
/*     */         
/* 310 */         float f2 = entity.func_70111_Y();
/*     */ 
/*     */         
/* 313 */         AxisAlignedBB axisalignedbb = entity.func_174813_aQ().func_72314_b(f2, f2, f2);
/* 314 */         RayTraceResult movingobjectposition = axisalignedbb.func_72327_a(vec3, vec32);
/*     */         
/* 316 */         if (axisalignedbb.func_72318_a(vec3)) {
/*     */           
/* 318 */           if (0.0D < d2 || d2 == 0.0D)
/*     */           {
/* 320 */             pointedEntity = entity;
/* 321 */             vec33 = (movingobjectposition == null) ? vec3 : movingobjectposition.field_72307_f;
/* 322 */             d2 = 0.0D;
/*     */           }
/*     */         
/* 325 */         } else if (movingobjectposition != null) {
/*     */           
/* 327 */           double d3 = vec3.func_72438_d(movingobjectposition.field_72307_f);
/*     */           
/* 329 */           if (d3 < d2 || d2 == 0.0D)
/*     */           {
/* 331 */             if (entity == user.func_184187_bx() && !entity.canRiderInteract()) {
/*     */               
/* 333 */               if (d2 == 0.0D)
/*     */               {
/* 335 */                 pointedEntity = entity;
/* 336 */                 vec33 = movingobjectposition.field_72307_f;
/*     */               }
/*     */             
/*     */             } else {
/*     */               
/* 341 */               pointedEntity = entity;
/* 342 */               vec33 = movingobjectposition.field_72307_f;
/* 343 */               d2 = d3;
/*     */             } 
/*     */           }
/*     */         } 
/*     */       } 
/*     */     } 
/*     */     
/* 350 */     if (pointedEntity != null && (d2 < d1 || objectMouseOver == null))
/*     */     {
/*     */       
/* 353 */       objectMouseOver = new RayTraceResult(pointedEntity, vec33);
/*     */     }
/*     */     
/* 356 */     return objectMouseOver;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean func_179218_a(ItemStack itemStack, World world, IBlockState state, BlockPos pos, EntityLivingBase entity) {
/* 366 */     if (state.func_185887_b(world, pos) != 0.0D)
/*     */     {
/* 368 */       itemStack.func_77972_a(2, entity);
/*     */     }
/*     */     
/* 371 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   @SideOnly(Side.CLIENT)
/*     */   public boolean func_77662_d() {
/* 378 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public EnumAction func_77661_b(ItemStack itemStack) {
/* 385 */     return EnumAction.BLOCK;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public int func_77626_a(ItemStack itemStack) {
/* 391 */     return 72000;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ActionResult<ItemStack> func_77659_a(World world, EntityPlayer player, EnumHand handIn) {
/* 400 */     player.func_184598_c(handIn);
/*     */     
/* 402 */     return ActionResult.newResult(EnumActionResult.SUCCESS, player.func_184586_b(handIn));
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public int func_77619_b() {
/* 408 */     return this.toolMaterial.func_77995_e();
/*     */   }
/*     */ 
/*     */   
/*     */   public String getToolMaterialName() {
/* 413 */     return this.toolMaterial.toString();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean func_82789_a(ItemStack item1, ItemStack item2) {
/* 420 */     ItemStack mat = this.toolMaterial.getRepairItemStack();
/* 421 */     if (!mat.func_190926_b() && OreDictionary.itemMatches(mat, item2, false))
/* 422 */       return true; 
/* 423 */     return super.func_82789_a(item1, item2);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Multimap<String, AttributeModifier> func_111205_h(EntityEquipmentSlot equipmentSlot) {
/* 434 */     Multimap<String, AttributeModifier> multimap = super.func_111205_h(equipmentSlot);
/*     */     
/* 436 */     if (equipmentSlot == EntityEquipmentSlot.MAINHAND)
/*     */     {
/* 438 */       multimap.put(SharedMonsterAttributes.field_111264_e.func_111108_a(), new AttributeModifier(field_111210_e, "Weapon modifier", this.damageVsEntity, 0));
/*     */     }
/*     */ 
/*     */     
/* 442 */     return multimap;
/*     */   }
/*     */ }


/* Location:              C:\Users\leo\Downloads\Mchelioreal\!\mcheli\tool\MCH_ItemWrench.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */