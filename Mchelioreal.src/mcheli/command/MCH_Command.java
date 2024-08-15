/*     */ package mcheli.command;
/*     */ 
/*     */ import com.google.gson.JsonParseException;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Collections;
/*     */ import java.util.Comparator;
/*     */ import java.util.HashMap;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import mcheli.MCH_Config;
/*     */ import mcheli.MCH_MOD;
/*     */ import mcheli.MCH_PacketNotifyServerSettings;
/*     */ import mcheli.__helper.MCH_Utils;
/*     */ import mcheli.multiplay.MCH_MultiplayPacketHandler;
/*     */ import mcheli.multiplay.MCH_PacketIndClient;
/*     */ import net.minecraft.block.Block;
/*     */ import net.minecraft.block.state.IBlockState;
/*     */ import net.minecraft.command.CommandBase;
/*     */ import net.minecraft.command.CommandException;
/*     */ import net.minecraft.command.CommandGameMode;
/*     */ import net.minecraft.command.ICommand;
/*     */ import net.minecraft.command.ICommandSender;
/*     */ import net.minecraft.command.SyntaxErrorException;
/*     */ import net.minecraft.command.WrongUsageException;
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.entity.player.EntityPlayerMP;
/*     */ import net.minecraft.inventory.IInventory;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraft.nbt.JsonToNBT;
/*     */ import net.minecraft.nbt.NBTException;
/*     */ import net.minecraft.nbt.NBTTagCompound;
/*     */ import net.minecraft.server.MinecraftServer;
/*     */ import net.minecraft.tileentity.TileEntity;
/*     */ import net.minecraft.util.DamageSource;
/*     */ import net.minecraft.util.math.BlockPos;
/*     */ import net.minecraft.util.math.MathHelper;
/*     */ import net.minecraft.util.text.ITextComponent;
/*     */ import net.minecraft.util.text.TextComponentString;
/*     */ import net.minecraft.util.text.TextComponentTranslation;
/*     */ import net.minecraft.util.text.TextFormatting;
/*     */ import net.minecraft.world.World;
/*     */ import net.minecraftforge.event.CommandEvent;
/*     */ import org.apache.commons.lang3.exception.ExceptionUtils;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class MCH_Command
/*     */   extends CommandBase
/*     */ {
/*     */   public static final String CMD_GET_SS = "sendss";
/*     */   public static final String CMD_MOD_LIST = "modlist";
/*     */   public static final String CMD_RECONFIG = "reconfig";
/*     */   public static final String CMD_TITLE = "title";
/*     */   public static final String CMD_FILL = "fill";
/*     */   public static final String CMD_STATUS = "status";
/*     */   public static final String CMD_KILL_ENTITY = "killentity";
/*     */   public static final String CMD_REMOVE_ENTITY = "removeentity";
/*     */   public static final String CMD_ATTACK_ENTITY = "attackentity";
/*     */   public static final String CMD_SHOW_BB = "showboundingbox";
/*     */   public static final String CMD_DELAY_BB = "delayhitbox";
/*     */   public static final String CMD_LIST = "list";
/*  68 */   public static String[] ALL_COMMAND = new String[] { "sendss", "modlist", "reconfig", "title", "fill", "status", "killentity", "removeentity", "attackentity", "showboundingbox", "list", "delayhitbox" };
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  74 */   public static MCH_Command instance = new MCH_Command();
/*     */ 
/*     */   
/*     */   public static boolean canUseCommand(Entity player) {
/*  78 */     return (player instanceof EntityPlayer) ? instance.canCommandSenderUseCommand((ICommandSender)player) : false;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public String func_71517_b() {
/*  84 */     return "mcheli";
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static boolean checkCommandPermission(MinecraftServer server, ICommandSender sender, String cmd) {
/*  91 */     if ((new CommandGameMode()).func_184882_a(server, sender))
/*     */     {
/*  93 */       return true;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*  98 */     if (sender instanceof EntityPlayer && cmd.length() > 0) {
/*     */       
/* 100 */       String playerName = ((EntityPlayer)sender).func_146103_bH().getName();
/*     */       
/* 102 */       for (MCH_Config.CommandPermission c : MCH_Config.CommandPermissionList) {
/*     */         
/* 104 */         if (c.name.equals(cmd))
/*     */         {
/* 106 */           for (String s : c.players) {
/*     */             
/* 108 */             if (s.equalsIgnoreCase(playerName))
/*     */             {
/* 110 */               return true;
/*     */             }
/*     */           } 
/*     */         }
/*     */       } 
/*     */     } 
/*     */     
/* 117 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static void onCommandEvent(CommandEvent event) {
/* 123 */     if (!(event.getCommand() instanceof MCH_Command)) {
/*     */       return;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/* 129 */     if ((event.getParameters()).length <= 0 || event.getParameters()[0].length() <= 0) {
/*     */       
/* 131 */       event.setCanceled(true);
/*     */ 
/*     */       
/*     */       return;
/*     */     } 
/*     */     
/* 137 */     if (!checkCommandPermission(MCH_Utils.getServer(), event.getSender(), event.getParameters()[0])) {
/*     */       
/* 139 */       event.setCanceled(true);
/*     */ 
/*     */ 
/*     */       
/* 143 */       TextComponentTranslation c = new TextComponentTranslation("commands.generic.permission", new Object[0]);
/* 144 */       c.func_150256_b().func_150238_a(TextFormatting.RED);
/* 145 */       event.getSender().func_145747_a((ITextComponent)c);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean canCommandSenderUseCommand(ICommandSender player) {
/* 151 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public String func_71518_a(ICommandSender sender) {
/* 157 */     return "commands.mcheli.usage";
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void func_184881_a(MinecraftServer server, ICommandSender sender, String[] prm) throws CommandException {
/* 164 */     if (!MCH_Config.EnableCommand.prmBool) {
/*     */       return;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/* 170 */     if (!checkCommandPermission(server, sender, prm[0])) {
/*     */ 
/*     */ 
/*     */       
/* 174 */       TextComponentTranslation c = new TextComponentTranslation("commands.generic.permission", new Object[0]);
/* 175 */       c.func_150256_b().func_150238_a(TextFormatting.RED);
/* 176 */       sender.func_145747_a((ITextComponent)c);
/*     */       
/*     */       return;
/*     */     } 
/* 180 */     if (prm[0].equalsIgnoreCase("sendss")) {
/*     */       
/* 182 */       if (prm.length == 2)
/*     */       {
/*     */         
/* 185 */         EntityPlayerMP player = func_184888_a(server, sender, prm[1]);
/*     */         
/* 187 */         if (player != null)
/*     */         {
/* 189 */           MCH_PacketIndClient.send((EntityPlayer)player, 1, prm[1]);
/*     */         }
/*     */       }
/*     */       else
/*     */       {
/* 194 */         throw new CommandException("Parameter error! : /mcheli sendss playerName", new Object[0]);
/*     */       }
/*     */     
/* 197 */     } else if (prm[0].equalsIgnoreCase("modlist")) {
/*     */       
/* 199 */       if (prm.length >= 2) {
/*     */         
/* 201 */         EntityPlayerMP reqPlayer = (sender instanceof EntityPlayerMP) ? (EntityPlayerMP)sender : null;
/*     */         
/* 203 */         for (int i = 1; i < prm.length; i++)
/*     */         {
/*     */           
/* 206 */           EntityPlayerMP player = func_184888_a(server, sender, prm[i]);
/*     */           
/* 208 */           if (player != null)
/*     */           {
/* 210 */             MCH_PacketIndClient.send((EntityPlayer)player, 2, "" + MCH_MultiplayPacketHandler.getPlayerInfoId((EntityPlayer)reqPlayer));
/*     */           }
/*     */         }
/*     */       
/*     */       } else {
/*     */         
/* 216 */         throw new CommandException("Parameter error! : /mcheli modlist playerName", new Object[0]);
/*     */       }
/*     */     
/* 219 */     } else if (prm[0].equalsIgnoreCase("reconfig")) {
/*     */       
/* 221 */       if (prm.length == 1) {
/*     */         
/* 223 */         MCH_MOD.proxy.reconfig();
/*     */         
/* 225 */         if (sender.func_130014_f_() != null)
/*     */         {
/* 227 */           if (!(sender.func_130014_f_()).field_72995_K)
/*     */           {
/* 229 */             MCH_PacketNotifyServerSettings.sendAll();
/*     */           }
/*     */         }
/*     */         
/* 233 */         if (MCH_MOD.proxy.isSinglePlayer())
/*     */         {
/*     */           
/* 236 */           sender.func_145747_a((ITextComponent)new TextComponentString("Reload mcheli.cfg"));
/*     */         
/*     */         }
/*     */         else
/*     */         {
/* 241 */           sender.func_145747_a((ITextComponent)new TextComponentString("Reload server side mcheli.cfg"));
/*     */         }
/*     */       
/*     */       } else {
/*     */         
/* 246 */         throw new CommandException("Parameter error! : /mcheli reconfig", new Object[0]);
/*     */       }
/*     */     
/* 249 */     } else if (prm[0].equalsIgnoreCase("title")) {
/*     */       
/* 251 */       if (prm.length < 4)
/*     */       {
/* 253 */         throw new WrongUsageException("Parameter error! : /mcheli title time[1~180] position[0~4] messege[JSON format]", new Object[0]);
/*     */       }
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 259 */       String s = func_180529_a(prm, 3);
/*     */       
/* 261 */       int showTime = Integer.valueOf(prm[1]).intValue();
/* 262 */       if (showTime < 1)
/* 263 */         showTime = 1; 
/* 264 */       if (showTime > 180)
/*     */       {
/* 266 */         showTime = 180;
/*     */       }
/* 268 */       int pos = Integer.valueOf(prm[2]).intValue();
/* 269 */       if (pos < 0)
/* 270 */         pos = 0; 
/* 271 */       if (pos > 5)
/*     */       {
/* 273 */         pos = 5;
/*     */       }
/*     */ 
/*     */       
/*     */       try {
/* 278 */         ITextComponent ichatcomponent = ITextComponent.Serializer.func_150699_a(s);
/* 279 */         MCH_PacketTitle.send(ichatcomponent, 20 * showTime, pos);
/*     */       }
/* 281 */       catch (JsonParseException jsonparseexception) {
/*     */         
/* 283 */         Throwable throwable = ExceptionUtils.getRootCause((Throwable)jsonparseexception);
/* 284 */         throw new SyntaxErrorException("mcheli.title.jsonException", new Object[] { (throwable == null) ? "" : throwable
/*     */               
/* 286 */               .getMessage() });
/*     */       
/*     */       }
/*     */     
/*     */     }
/* 291 */     else if (prm[0].equalsIgnoreCase("fill")) {
/*     */       
/* 293 */       executeFill(sender, prm);
/*     */     }
/* 295 */     else if (prm[0].equalsIgnoreCase("status")) {
/*     */       
/* 297 */       executeStatus(sender, prm);
/*     */     }
/* 299 */     else if (prm[0].equalsIgnoreCase("killentity")) {
/*     */       
/* 301 */       executeKillEntity(sender, prm);
/*     */     }
/* 303 */     else if (prm[0].equalsIgnoreCase("removeentity")) {
/*     */       
/* 305 */       executeRemoveEntity(sender, prm);
/*     */     }
/* 307 */     else if (prm[0].equalsIgnoreCase("attackentity")) {
/*     */       
/* 309 */       executeAttackEntity(sender, prm);
/*     */     }
/* 311 */     else if (prm[0].equalsIgnoreCase("showboundingbox")) {
/*     */       
/* 313 */       if (prm.length != 2)
/*     */       {
/* 315 */         throw new CommandException("Parameter error! : /mcheli showboundingbox true or false", new Object[0]);
/*     */       }
/*     */ 
/*     */       
/* 319 */       if (!func_180527_d(prm[1])) {
/*     */         
/* 321 */         MCH_Config.EnableDebugBoundingBox.prmBool = false;
/* 322 */         MCH_PacketNotifyServerSettings.sendAll();
/*     */         
/* 324 */         sender.func_145747_a((ITextComponent)new TextComponentString("Disabled bounding box"));
/*     */       }
/*     */       else {
/*     */         
/* 328 */         MCH_Config.EnableDebugBoundingBox.prmBool = true;
/* 329 */         MCH_PacketNotifyServerSettings.sendAll();
/*     */         
/* 331 */         sender.func_145747_a((ITextComponent)new TextComponentString("Enabled bounding box [F3 + b]"));
/*     */       } 
/*     */       
/* 334 */       MCH_MOD.proxy.save();
/*     */     }
/* 336 */     else if (prm[0].equalsIgnoreCase("list")) {
/*     */       
/* 338 */       String msg = "";
/*     */       
/* 340 */       for (String s : ALL_COMMAND) {
/* 341 */         msg = msg + s + ", ";
/*     */       }
/*     */       
/* 344 */       sender.func_145747_a((ITextComponent)new TextComponentString("/mcheli command list : " + msg));
/*     */     }
/* 346 */     else if (prm[0].equalsIgnoreCase("delayhitbox")) {
/*     */       
/* 348 */       if (prm.length == 1)
/*     */       {
/*     */ 
/*     */         
/* 352 */         sender.func_145747_a((ITextComponent)new TextComponentString("Current delay of hitbox = " + MCH_Config.HitBoxDelayTick.prmInt + " [0 ~ 50]"));
/*     */       
/*     */       }
/* 355 */       else if (prm.length == 2)
/*     */       {
/*     */         
/* 358 */         MCH_Config.HitBoxDelayTick.prmInt = func_175755_a(prm[1]);
/*     */         
/* 360 */         if (MCH_Config.HitBoxDelayTick.prmInt > 50)
/*     */         {
/* 362 */           MCH_Config.HitBoxDelayTick.prmInt = 50;
/*     */         }
/*     */         
/* 365 */         MCH_MOD.proxy.save();
/*     */ 
/*     */         
/* 368 */         sender.func_145747_a((ITextComponent)new TextComponentString("Current delay of hitbox = " + MCH_Config.HitBoxDelayTick.prmInt + " [0 ~ 50]"));
/*     */       
/*     */       }
/*     */       else
/*     */       {
/* 373 */         throw new CommandException("Parameter error! : /mcheli delayhitbox 0 ~ 50", new Object[0]);
/*     */       }
/*     */     
/*     */     } else {
/*     */       
/* 378 */       throw new CommandException("Unknown mcheli command. please type /mcheli list", new Object[0]);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private void executeAttackEntity(ICommandSender sender, String[] args) throws WrongUsageException {
/* 385 */     if (args.length < 3)
/*     */     {
/* 387 */       throw new WrongUsageException("/mcheli attackentity <entity class name : example1 EntityBat , example2 minecraft.entity.passive> <damage> [damage source]", new Object[0]);
/*     */     }
/*     */ 
/*     */ 
/*     */     
/* 392 */     String className = args[1].toLowerCase();
/* 393 */     float damage = Float.valueOf(args[2]).floatValue();
/* 394 */     String damageName = (args.length >= 4) ? args[3].toLowerCase() : "";
/*     */     
/* 396 */     DamageSource ds = DamageSource.field_76377_j;
/* 397 */     if (!damageName.isEmpty())
/*     */     {
/* 399 */       if (damageName.equals("player")) {
/*     */         
/* 401 */         if (sender instanceof EntityPlayer)
/*     */         {
/* 403 */           ds = DamageSource.func_76365_a((EntityPlayer)sender);
/*     */         }
/*     */       }
/* 406 */       else if (damageName.equals("anvil")) {
/*     */         
/* 408 */         ds = DamageSource.field_82728_o;
/*     */       }
/* 410 */       else if (damageName.equals("cactus")) {
/*     */         
/* 412 */         ds = DamageSource.field_76367_g;
/*     */       }
/* 414 */       else if (damageName.equals("drown")) {
/*     */         
/* 416 */         ds = DamageSource.field_76369_e;
/*     */       }
/* 418 */       else if (damageName.equals("fall")) {
/*     */         
/* 420 */         ds = DamageSource.field_76379_h;
/*     */       }
/* 422 */       else if (damageName.equals("fallingblock")) {
/*     */         
/* 424 */         ds = DamageSource.field_82729_p;
/*     */       }
/* 426 */       else if (damageName.equals("generic")) {
/*     */         
/* 428 */         ds = DamageSource.field_76377_j;
/*     */       }
/* 430 */       else if (damageName.equals("infire")) {
/*     */         
/* 432 */         ds = DamageSource.field_76372_a;
/*     */       }
/* 434 */       else if (damageName.equals("inwall")) {
/*     */         
/* 436 */         ds = DamageSource.field_76368_d;
/*     */       }
/* 438 */       else if (damageName.equals("lava")) {
/*     */         
/* 440 */         ds = DamageSource.field_76371_c;
/*     */       }
/* 442 */       else if (damageName.equals("magic")) {
/*     */         
/* 444 */         ds = DamageSource.field_76376_m;
/*     */       }
/* 446 */       else if (damageName.equals("onfire")) {
/*     */         
/* 448 */         ds = DamageSource.field_76370_b;
/*     */       }
/* 450 */       else if (damageName.equals("starve")) {
/*     */         
/* 452 */         ds = DamageSource.field_76366_f;
/*     */       }
/* 454 */       else if (damageName.equals("wither")) {
/*     */         
/* 456 */         ds = DamageSource.field_82727_n;
/*     */       } 
/*     */     }
/* 459 */     int attacked = 0;
/* 460 */     List<Entity> list = (sender.func_130014_f_()).field_72996_f;
/*     */     
/* 462 */     for (int i = 0; i < list.size(); i++) {
/*     */       
/* 464 */       if (list.get(i) != null && !(list.get(i) instanceof EntityPlayer))
/*     */       {
/* 466 */         if (((Entity)list.get(i)).getClass().getName().toLowerCase().indexOf(className) >= 0) {
/*     */           
/* 468 */           ((Entity)list.get(i)).func_70097_a(ds, damage);
/* 469 */           attacked++;
/*     */         } 
/*     */       }
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 476 */     sender.func_145747_a((ITextComponent)new TextComponentString(attacked + " entity attacked(" + args[1] + ", damage=" + damage + ")."));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void executeKillEntity(ICommandSender sender, String[] args) throws WrongUsageException {
/* 483 */     if (args.length < 2)
/*     */     {
/* 485 */       throw new WrongUsageException("/mcheli killentity <entity class name : example1 EntityBat , example2 minecraft.entity.passive>", new Object[0]);
/*     */     }
/*     */ 
/*     */ 
/*     */     
/* 490 */     String className = args[1].toLowerCase();
/* 491 */     int killed = 0;
/* 492 */     List<Entity> list = (sender.func_130014_f_()).field_72996_f;
/*     */     
/* 494 */     for (int i = 0; i < list.size(); i++) {
/*     */       
/* 496 */       if (list.get(i) != null && !(list.get(i) instanceof EntityPlayer))
/*     */       {
/* 498 */         if (((Entity)list.get(i)).getClass().getName().toLowerCase().indexOf(className) >= 0) {
/*     */           
/* 500 */           ((Entity)list.get(i)).func_70106_y();
/* 501 */           killed++;
/*     */         } 
/*     */       }
/*     */     } 
/*     */ 
/*     */     
/* 507 */     sender.func_145747_a((ITextComponent)new TextComponentString(killed + " entity killed(" + args[1] + ")."));
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private void executeRemoveEntity(ICommandSender sender, String[] args) throws WrongUsageException {
/* 513 */     if (args.length < 2)
/*     */     {
/* 515 */       throw new WrongUsageException("/mcheli removeentity <entity class name : example1 EntityBat , example2 minecraft.entity.passive>", new Object[0]);
/*     */     }
/*     */ 
/*     */ 
/*     */     
/* 520 */     String className = args[1].toLowerCase();
/* 521 */     List<Entity> list = (sender.func_130014_f_()).field_72996_f;
/* 522 */     int removed = 0;
/*     */     
/* 524 */     for (int i = 0; i < list.size(); i++) {
/*     */       
/* 526 */       if (list.get(i) != null && !(list.get(i) instanceof EntityPlayer))
/*     */       {
/* 528 */         if (((Entity)list.get(i)).getClass().getName().toLowerCase().indexOf(className) >= 0) {
/*     */           
/* 530 */           ((Entity)list.get(i)).field_70128_L = true;
/* 531 */           removed++;
/*     */         } 
/*     */       }
/*     */     } 
/*     */ 
/*     */     
/* 537 */     sender.func_145747_a((ITextComponent)new TextComponentString(removed + " entity removed(" + args[1] + ")."));
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private void executeStatus(ICommandSender sender, String[] args) throws WrongUsageException {
/* 543 */     if (args.length < 2)
/*     */     {
/* 545 */       throw new WrongUsageException("/mcheli status <entity or tile> [min num]", new Object[0]);
/*     */     }
/*     */     
/* 548 */     if (args[1].equalsIgnoreCase("entity")) {
/*     */       
/* 550 */       executeStatusSub(sender, args, "Server loaded Entity List", (sender.func_130014_f_()).field_72996_f);
/*     */     }
/* 552 */     else if (args[1].equalsIgnoreCase("tile")) {
/*     */       
/* 554 */       executeStatusSub(sender, args, "Server loaded Tile Entity List", 
/* 555 */           (sender.func_130014_f_()).field_147482_g);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private void executeStatusSub(ICommandSender sender, String[] args, String title, List<?> list) {
/* 562 */     int minNum = (args.length >= 3) ? Integer.valueOf(args[2]).intValue() : 0;
/* 563 */     HashMap<String, Integer> map = new HashMap<>();
/* 564 */     for (int i = 0; i < list.size(); i++) {
/*     */       
/* 566 */       String key = list.get(i).getClass().getName();
/* 567 */       if (map.containsKey(key)) {
/*     */         
/* 569 */         map.put(key, Integer.valueOf(((Integer)map.get(key)).intValue() + 1));
/*     */       }
/*     */       else {
/*     */         
/* 573 */         map.put(key, Integer.valueOf(1));
/*     */       } 
/*     */     } 
/*     */     
/* 577 */     List<Map.Entry<String, Integer>> entries = new ArrayList<>(map.entrySet());
/*     */     
/* 579 */     Collections.sort(entries, new Comparator<Map.Entry<String, Integer>>()
/*     */         {
/*     */           
/*     */           public int compare(Map.Entry<String, Integer> entry1, Map.Entry<String, Integer> entry2)
/*     */           {
/* 584 */             return ((String)entry1.getKey()).compareTo(entry2.getKey());
/*     */           }
/*     */         });
/*     */ 
/*     */     
/* 589 */     boolean send = false;
/*     */     
/* 591 */     sender.func_145747_a((ITextComponent)new TextComponentString("--- " + title + " ---"));
/*     */     
/* 593 */     for (Map.Entry<String, Integer> s : entries) {
/*     */       
/* 595 */       if (((Integer)s.getValue()).intValue() >= minNum) {
/*     */         
/* 597 */         String msg = " " + (String)s.getKey() + " : " + s.getValue();
/* 598 */         System.out.println(msg);
/*     */         
/* 600 */         sender.func_145747_a((ITextComponent)new TextComponentString(msg));
/* 601 */         send = true;
/*     */       } 
/*     */     } 
/*     */     
/* 605 */     if (!send) {
/*     */       
/* 607 */       System.out.println("none");
/*     */       
/* 609 */       sender.func_145747_a((ITextComponent)new TextComponentString("none"));
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void executeFill(ICommandSender sender, String[] args) throws CommandException {
/* 616 */     if (args.length < 8)
/*     */     {
/* 618 */       throw new WrongUsageException("/mcheli fill <x1> <y1> <z1> <x2> <y2> <z2> <block name> [meta data] [oldBlockHandling] [data tag]", new Object[0]);
/*     */     }
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
/*     */     
/* 635 */     int x1 = sender.func_180425_c().func_177958_n();
/* 636 */     int y1 = sender.func_180425_c().func_177956_o();
/* 637 */     int z1 = sender.func_180425_c().func_177952_p();
/* 638 */     int x2 = sender.func_180425_c().func_177958_n();
/* 639 */     int y2 = sender.func_180425_c().func_177956_o();
/* 640 */     int z2 = sender.func_180425_c().func_177952_p();
/* 641 */     x1 = MathHelper.func_76128_c(func_175770_a(x1, args[1], true).func_179628_a());
/* 642 */     y1 = MathHelper.func_76128_c(func_175770_a(y1, args[2], true).func_179628_a());
/* 643 */     z1 = MathHelper.func_76128_c(func_175770_a(z1, args[3], true).func_179628_a());
/* 644 */     x2 = MathHelper.func_76128_c(func_175770_a(x2, args[4], true).func_179628_a());
/* 645 */     y2 = MathHelper.func_76128_c(func_175770_a(y2, args[5], true).func_179628_a());
/* 646 */     z2 = MathHelper.func_76128_c(func_175770_a(z2, args[6], true).func_179628_a());
/* 647 */     Block block = CommandBase.func_147180_g(sender, args[7]);
/*     */     
/* 649 */     IBlockState iblockstate = block.func_176223_P();
/*     */     
/* 651 */     if (args.length >= 9)
/*     */     {
/*     */       
/* 654 */       iblockstate = func_190794_a(block, args[8]);
/*     */     }
/*     */     
/* 657 */     World world = sender.func_130014_f_();
/*     */     
/* 659 */     if (x1 > x2) {
/*     */       
/* 661 */       int t = x1;
/* 662 */       x1 = x2;
/* 663 */       x2 = t;
/*     */     } 
/*     */     
/* 666 */     if (y1 > y2) {
/*     */       
/* 668 */       int t = y1;
/* 669 */       y1 = y2;
/* 670 */       y2 = t;
/*     */     } 
/*     */     
/* 673 */     if (z1 > z2) {
/*     */       
/* 675 */       int t = z1;
/* 676 */       z1 = z2;
/* 677 */       z2 = t;
/*     */     } 
/*     */     
/* 680 */     if (y1 < 0 || y2 >= 256)
/*     */     {
/* 682 */       throw new CommandException("commands.setblock.outOfWorld", new Object[0]);
/*     */     }
/*     */     
/* 685 */     int blockNum = (x2 - x1 + 1) * (y2 - y1 + 1) * (z2 - z1 + 1);
/*     */     
/* 687 */     if (blockNum > 3000000)
/*     */     {
/* 689 */       throw new CommandException("commands.setblock.tooManyBlocks " + blockNum + " limit=327680", new Object[] {
/*     */             
/* 691 */             Integer.valueOf(blockNum), Integer.valueOf(3276800)
/*     */           });
/*     */     }
/*     */     
/* 695 */     boolean result = false;
/* 696 */     boolean keep = (args.length >= 10 && args[9].equals("keep"));
/* 697 */     boolean destroy = (args.length >= 10 && args[9].equals("destroy"));
/* 698 */     boolean override = (args.length >= 10 && args[9].equals("override"));
/*     */     
/* 700 */     NBTTagCompound nbttagcompound = new NBTTagCompound();
/* 701 */     boolean flag = false;
/*     */ 
/*     */     
/* 704 */     if (args.length >= 11 && block.hasTileEntity(iblockstate)) {
/*     */       
/* 706 */       String s = func_147178_a(sender, args, 10).func_150260_c();
/*     */ 
/*     */ 
/*     */       
/*     */       try {
/* 711 */         NBTTagCompound nbtbase = JsonToNBT.func_180713_a(s);
/*     */         
/* 713 */         if (!(nbtbase instanceof NBTTagCompound))
/*     */         {
/* 715 */           throw new CommandException("commands.setblock.tagError", new Object[] { "Not a valid tag" });
/*     */         }
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 721 */         nbttagcompound = nbtbase;
/* 722 */         flag = true;
/*     */       }
/* 724 */       catch (NBTException nbtexception) {
/*     */         
/* 726 */         throw new CommandException("commands.setblock.tagError", new Object[] { nbtexception
/*     */               
/* 728 */               .getMessage() });
/*     */       } 
/*     */     } 
/*     */ 
/*     */     
/* 733 */     for (int x = x1; x <= x2; x++) {
/* 734 */       for (int y = y1; y <= y2; y++) {
/* 735 */         for (int z = z1; z <= z2; z++) {
/*     */           
/* 737 */           BlockPos blockpos = new BlockPos(x, y, z);
/*     */           
/* 739 */           if (world.func_175667_e(blockpos))
/*     */           {
/*     */             
/* 742 */             if (world.func_175623_d(blockpos) ? !override : !keep) {
/*     */               
/* 744 */               if (destroy)
/*     */               {
/*     */                 
/* 747 */                 world.func_175655_b(blockpos, false);
/*     */               }
/*     */ 
/*     */               
/* 751 */               TileEntity block2 = world.func_175625_s(blockpos);
/*     */               
/* 753 */               if (block2 instanceof IInventory) {
/*     */                 
/* 755 */                 IInventory ii = (IInventory)block2;
/*     */                 
/* 757 */                 for (int i = 0; i < ii.func_70302_i_(); i++) {
/*     */                   
/* 759 */                   ItemStack is = ii.func_70304_b(i);
/*     */ 
/*     */                   
/* 762 */                   if (!is.func_190926_b())
/*     */                   {
/*     */                     
/* 765 */                     is.func_190920_e(0);
/*     */                   }
/*     */                 } 
/*     */               } 
/*     */ 
/*     */               
/* 771 */               if (world.func_180501_a(blockpos, iblockstate, 3)) {
/*     */                 
/* 773 */                 if (flag) {
/*     */ 
/*     */                   
/* 776 */                   TileEntity tileentity = world.func_175625_s(blockpos);
/*     */                   
/* 778 */                   if (tileentity != null) {
/*     */                     
/* 780 */                     nbttagcompound.func_74768_a("x", x);
/* 781 */                     nbttagcompound.func_74768_a("y", y);
/* 782 */                     nbttagcompound.func_74768_a("z", z);
/* 783 */                     tileentity.func_145839_a(nbttagcompound);
/*     */                   } 
/*     */                 } 
/*     */                 
/* 787 */                 result = true;
/*     */               } 
/*     */             }  } 
/*     */         } 
/*     */       } 
/* 792 */     }  if (result) {
/*     */       
/* 794 */       func_152373_a(sender, (ICommand)this, "commands.setblock.success", new Object[0]);
/*     */     }
/*     */     else {
/*     */       
/* 798 */       throw new CommandException("commands.setblock.noChange", new Object[0]);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public List<String> func_184883_a(MinecraftServer server, ICommandSender sender, String[] prm, BlockPos targetPos) {
/* 807 */     if (!MCH_Config.EnableCommand.prmBool)
/*     */     {
/*     */       
/* 810 */       return super.func_184883_a(server, sender, prm, targetPos);
/*     */     }
/*     */     
/* 813 */     if (prm.length <= 1)
/*     */     {
/* 815 */       return func_71530_a(prm, ALL_COMMAND);
/*     */     }
/*     */     
/* 818 */     if (prm[0].equalsIgnoreCase("sendss")) {
/*     */       
/* 820 */       if (prm.length == 2)
/*     */       {
/*     */         
/* 823 */         return func_71530_a(prm, server.func_71213_z());
/*     */       }
/*     */     }
/* 826 */     else if (prm[0].equalsIgnoreCase("modlist")) {
/*     */       
/* 828 */       if (prm.length >= 2)
/*     */       {
/*     */         
/* 831 */         return func_71530_a(prm, server.func_71213_z());
/*     */       }
/*     */     }
/*     */     else {
/*     */       
/* 836 */       if (prm[0].equalsIgnoreCase("fill")) {
/*     */         
/* 838 */         if ((prm.length == 2 || prm.length == 5) && sender instanceof Entity) {
/*     */           
/* 840 */           Entity entity = (Entity)sender;
/* 841 */           List<String> a = new ArrayList<>();
/* 842 */           int x = (entity.field_70165_t < 0.0D) ? (int)(entity.field_70165_t - 1.0D) : (int)entity.field_70165_t;
/* 843 */           int z = (entity.field_70161_v < 0.0D) ? (int)(entity.field_70161_v - 1.0D) : (int)entity.field_70161_v;
/* 844 */           a.add("" + x + " " + (int)(entity.field_70163_u + 0.5D) + " " + z);
/* 845 */           return a;
/*     */         } 
/*     */         
/* 848 */         return (prm.length == 10) ? func_71530_a(prm, new String[] { "replace", "destroy", "keep", "override" }) : ((prm.length == 8) ? 
/*     */ 
/*     */ 
/*     */ 
/*     */           
/* 853 */           func_175762_a(prm, Block.field_149771_c.func_148742_b()) : null);
/*     */       } 
/*     */       
/* 856 */       if (prm[0].equalsIgnoreCase("status")) {
/*     */         
/* 858 */         if (prm.length == 2)
/*     */         {
/* 860 */           return func_71530_a(prm, new String[] { "entity", "tile" });
/*     */ 
/*     */         
/*     */         }
/*     */ 
/*     */       
/*     */       }
/* 867 */       else if (prm[0].equalsIgnoreCase("attackentity")) {
/*     */         
/* 869 */         if (prm.length == 4)
/*     */         {
/* 871 */           return func_71530_a(prm, new String[] { "player", "inFire", "onFire", "lava", "inWall", "drown", "starve", "cactus", "fall", "outOfWorld", "generic", "magic", "wither", "anvil", "fallingBlock" });
/*     */ 
/*     */ 
/*     */         
/*     */         }
/*     */ 
/*     */       
/*     */       }
/* 879 */       else if (prm[0].equalsIgnoreCase("showboundingbox")) {
/*     */         
/* 881 */         if (prm.length == 2)
/*     */         {
/* 883 */           return func_71530_a(prm, new String[] { "true", "false" });
/*     */         }
/*     */       } 
/*     */     } 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 891 */     return super.func_184883_a(server, sender, prm, targetPos);
/*     */   }
/*     */ }


/* Location:              C:\Users\leo\Downloads\Mchelioreal\!\mcheli\command\MCH_Command.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */