package mcheli.command;

import com.google.gson.JsonParseException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import mcheli.MCH_Config;
import mcheli.MCH_MOD;
import mcheli.MCH_PacketNotifyServerSettings;
import mcheli.__helper.MCH_Utils;
import mcheli.multiplay.MCH_MultiplayPacketHandler;
import mcheli.multiplay.MCH_PacketIndClient;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.CommandGameMode;
import net.minecraft.command.ICommand;
import net.minecraft.command.ICommandSender;
import net.minecraft.command.SyntaxErrorException;
import net.minecraft.command.WrongUsageException;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.JsonToNBT;
import net.minecraft.nbt.NBTException;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.server.MinecraftServer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.DamageSource;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import net.minecraftforge.event.CommandEvent;
import org.apache.commons.lang3.exception.ExceptionUtils;

public class MCH_Command extends CommandBase {
  public static final String CMD_GET_SS = "sendss";
  
  public static final String CMD_MOD_LIST = "modlist";
  
  public static final String CMD_RECONFIG = "reconfig";
  
  public static final String CMD_TITLE = "title";
  
  public static final String CMD_FILL = "fill";
  
  public static final String CMD_STATUS = "status";
  
  public static final String CMD_KILL_ENTITY = "killentity";
  
  public static final String CMD_REMOVE_ENTITY = "removeentity";
  
  public static final String CMD_ATTACK_ENTITY = "attackentity";
  
  public static final String CMD_SHOW_BB = "showboundingbox";
  
  public static final String CMD_DELAY_BB = "delayhitbox";
  
  public static final String CMD_LIST = "list";
  
  public static String[] ALL_COMMAND = new String[] { 
      "sendss", "modlist", "reconfig", "title", "fill", "status", "killentity", "removeentity", "attackentity", "showboundingbox", 
      "list", "delayhitbox" };
  
  public static MCH_Command instance = new MCH_Command();
  
  public static boolean canUseCommand(Entity player) {
    return (player instanceof EntityPlayer) ? instance.canCommandSenderUseCommand((ICommandSender)player) : false;
  }
  
  public String getName() {
    return "mcheli";
  }
  
  public static boolean checkCommandPermission(MinecraftServer server, ICommandSender sender, String cmd) {
    if ((new CommandGameMode()).checkPermission(server, sender))
      return true; 
    if (sender instanceof EntityPlayer && cmd.length() > 0) {
      String playerName = ((EntityPlayer)sender).getGameProfile().getName();
      for (MCH_Config.CommandPermission c : MCH_Config.CommandPermissionList) {
        if (c.name.equals(cmd))
          for (String s : c.players) {
            if (s.equalsIgnoreCase(playerName))
              return true; 
          }  
      } 
    } 
    return false;
  }
  
  public static void onCommandEvent(CommandEvent event) {
    if (!(event.getCommand() instanceof MCH_Command))
      return; 
    if ((event.getParameters()).length <= 0 || event.getParameters()[0].length() <= 0) {
      event.setCanceled(true);
      return;
    } 
    if (!checkCommandPermission(MCH_Utils.getServer(), event.getSender(), event.getParameters()[0])) {
      event.setCanceled(true);
      TextComponentTranslation c = new TextComponentTranslation("commands.generic.permission", new Object[0]);
      c.getStyle().setColor(TextFormatting.RED);
      event.getSender().sendMessage((ITextComponent)c);
    } 
  }
  
  public boolean canCommandSenderUseCommand(ICommandSender player) {
    return true;
  }
  
  public String getUsage(ICommandSender sender) {
    return "commands.mcheli.usage";
  }
  
  public void execute(MinecraftServer server, ICommandSender sender, String[] prm) throws CommandException {
    if (!MCH_Config.EnableCommand.prmBool)
      return; 
    if (!checkCommandPermission(server, sender, prm[0])) {
      TextComponentTranslation c = new TextComponentTranslation("commands.generic.permission", new Object[0]);
      c.getStyle().setColor(TextFormatting.RED);
      sender.sendMessage((ITextComponent)c);
      return;
    } 
    if (prm[0].equalsIgnoreCase("sendss")) {
      if (prm.length == 2) {
        EntityPlayerMP player = getPlayer(server, sender, prm[1]);
        if (player != null)
          MCH_PacketIndClient.send((EntityPlayer)player, 1, prm[1]); 
      } else {
        throw new CommandException("Parameter error! : /mcheli sendss playerName", new Object[0]);
      } 
    } else if (prm[0].equalsIgnoreCase("modlist")) {
      if (prm.length >= 2) {
        EntityPlayerMP reqPlayer = (sender instanceof EntityPlayerMP) ? (EntityPlayerMP)sender : null;
        for (int i = 1; i < prm.length; i++) {
          EntityPlayerMP player = getPlayer(server, sender, prm[i]);
          if (player != null)
            MCH_PacketIndClient.send((EntityPlayer)player, 2, "" + MCH_MultiplayPacketHandler.getPlayerInfoId((EntityPlayer)reqPlayer)); 
        } 
      } else {
        throw new CommandException("Parameter error! : /mcheli modlist playerName", new Object[0]);
      } 
    } else if (prm[0].equalsIgnoreCase("reconfig")) {
      if (prm.length == 1) {
        MCH_MOD.proxy.reconfig();
        if (sender.getEntityWorld() != null)
          if (!(sender.getEntityWorld()).isRemote)
            MCH_PacketNotifyServerSettings.sendAll();  
        if (MCH_MOD.proxy.isSinglePlayer()) {
          sender.sendMessage((ITextComponent)new TextComponentString("Reload mcheli.cfg"));
        } else {
          sender.sendMessage((ITextComponent)new TextComponentString("Reload server side mcheli.cfg"));
        } 
      } else {
        throw new CommandException("Parameter error! : /mcheli reconfig", new Object[0]);
      } 
    } else if (prm[0].equalsIgnoreCase("title")) {
      if (prm.length < 4)
        throw new WrongUsageException("Parameter error! : /mcheli title time[1~180] position[0~4] messege[JSON format]", new Object[0]); 
      String s = buildString(prm, 3);
      int showTime = Integer.valueOf(prm[1]).intValue();
      if (showTime < 1)
        showTime = 1; 
      if (showTime > 180)
        showTime = 180; 
      int pos = Integer.valueOf(prm[2]).intValue();
      if (pos < 0)
        pos = 0; 
      if (pos > 5)
        pos = 5; 
      try {
        ITextComponent ichatcomponent = ITextComponent.Serializer.jsonToComponent(s);
        MCH_PacketTitle.send(ichatcomponent, 20 * showTime, pos);
      } catch (JsonParseException jsonparseexception) {
        Throwable throwable = ExceptionUtils.getRootCause((Throwable)jsonparseexception);
        throw new SyntaxErrorException("mcheli.title.jsonException", new Object[] { (throwable == null) ? "" : throwable
              
              .getMessage() });
      } 
    } else if (prm[0].equalsIgnoreCase("fill")) {
      executeFill(sender, prm);
    } else if (prm[0].equalsIgnoreCase("status")) {
      executeStatus(sender, prm);
    } else if (prm[0].equalsIgnoreCase("killentity")) {
      executeKillEntity(sender, prm);
    } else if (prm[0].equalsIgnoreCase("removeentity")) {
      executeRemoveEntity(sender, prm);
    } else if (prm[0].equalsIgnoreCase("attackentity")) {
      executeAttackEntity(sender, prm);
    } else if (prm[0].equalsIgnoreCase("showboundingbox")) {
      if (prm.length != 2)
        throw new CommandException("Parameter error! : /mcheli showboundingbox true or false", new Object[0]); 
      if (!parseBoolean(prm[1])) {
        MCH_Config.EnableDebugBoundingBox.prmBool = false;
        MCH_PacketNotifyServerSettings.sendAll();
        sender.sendMessage((ITextComponent)new TextComponentString("Disabled bounding box"));
      } else {
        MCH_Config.EnableDebugBoundingBox.prmBool = true;
        MCH_PacketNotifyServerSettings.sendAll();
        sender.sendMessage((ITextComponent)new TextComponentString("Enabled bounding box [F3 + b]"));
      } 
      MCH_MOD.proxy.save();
    } else if (prm[0].equalsIgnoreCase("list")) {
      String msg = "";
      for (String s : ALL_COMMAND)
        msg = msg + s + ", "; 
      sender.sendMessage((ITextComponent)new TextComponentString("/mcheli command list : " + msg));
    } else if (prm[0].equalsIgnoreCase("delayhitbox")) {
      if (prm.length == 1) {
        sender.sendMessage((ITextComponent)new TextComponentString("Current delay of hitbox = " + MCH_Config.HitBoxDelayTick.prmInt + " [0 ~ 50]"));
      } else if (prm.length == 2) {
        MCH_Config.HitBoxDelayTick.prmInt = parseInt(prm[1]);
        if (MCH_Config.HitBoxDelayTick.prmInt > 50)
          MCH_Config.HitBoxDelayTick.prmInt = 50; 
        MCH_MOD.proxy.save();
        sender.sendMessage((ITextComponent)new TextComponentString("Current delay of hitbox = " + MCH_Config.HitBoxDelayTick.prmInt + " [0 ~ 50]"));
      } else {
        throw new CommandException("Parameter error! : /mcheli delayhitbox 0 ~ 50", new Object[0]);
      } 
    } else {
      throw new CommandException("Unknown mcheli command. please type /mcheli list", new Object[0]);
    } 
  }
  
  private void executeAttackEntity(ICommandSender sender, String[] args) throws WrongUsageException {
    if (args.length < 3)
      throw new WrongUsageException("/mcheli attackentity <entity class name : example1 EntityBat , example2 minecraft.entity.passive> <damage> [damage source]", new Object[0]); 
    String className = args[1].toLowerCase();
    float damage = Float.valueOf(args[2]).floatValue();
    String damageName = (args.length >= 4) ? args[3].toLowerCase() : "";
    DamageSource ds = DamageSource.GENERIC;
    if (!damageName.isEmpty())
      if (damageName.equals("player")) {
        if (sender instanceof EntityPlayer)
          ds = DamageSource.causePlayerDamage((EntityPlayer)sender); 
      } else if (damageName.equals("anvil")) {
        ds = DamageSource.ANVIL;
      } else if (damageName.equals("cactus")) {
        ds = DamageSource.CACTUS;
      } else if (damageName.equals("drown")) {
        ds = DamageSource.DROWN;
      } else if (damageName.equals("fall")) {
        ds = DamageSource.FALL;
      } else if (damageName.equals("fallingblock")) {
        ds = DamageSource.FALLING_BLOCK;
      } else if (damageName.equals("generic")) {
        ds = DamageSource.GENERIC;
      } else if (damageName.equals("infire")) {
        ds = DamageSource.IN_FIRE;
      } else if (damageName.equals("inwall")) {
        ds = DamageSource.IN_WALL;
      } else if (damageName.equals("lava")) {
        ds = DamageSource.LAVA;
      } else if (damageName.equals("magic")) {
        ds = DamageSource.MAGIC;
      } else if (damageName.equals("onfire")) {
        ds = DamageSource.ON_FIRE;
      } else if (damageName.equals("starve")) {
        ds = DamageSource.STARVE;
      } else if (damageName.equals("wither")) {
        ds = DamageSource.WITHER;
      }  
    int attacked = 0;
    List<Entity> list = (sender.getEntityWorld()).loadedEntityList;
    for (int i = 0; i < list.size(); i++) {
      if (list.get(i) != null && !(list.get(i) instanceof EntityPlayer))
        if (((Entity)list.get(i)).getClass().getName().toLowerCase().indexOf(className) >= 0) {
          ((Entity)list.get(i)).attackEntityFrom(ds, damage);
          attacked++;
        }  
    } 
    sender.sendMessage((ITextComponent)new TextComponentString(attacked + " entity attacked(" + args[1] + ", damage=" + damage + ")."));
  }
  
  private void executeKillEntity(ICommandSender sender, String[] args) throws WrongUsageException {
    if (args.length < 2)
      throw new WrongUsageException("/mcheli killentity <entity class name : example1 EntityBat , example2 minecraft.entity.passive>", new Object[0]); 
    String className = args[1].toLowerCase();
    int killed = 0;
    List<Entity> list = (sender.getEntityWorld()).loadedEntityList;
    for (int i = 0; i < list.size(); i++) {
      if (list.get(i) != null && !(list.get(i) instanceof EntityPlayer))
        if (((Entity)list.get(i)).getClass().getName().toLowerCase().indexOf(className) >= 0) {
          ((Entity)list.get(i)).setDead();
          killed++;
        }  
    } 
    sender.sendMessage((ITextComponent)new TextComponentString(killed + " entity killed(" + args[1] + ")."));
  }
  
  private void executeRemoveEntity(ICommandSender sender, String[] args) throws WrongUsageException {
    if (args.length < 2)
      throw new WrongUsageException("/mcheli removeentity <entity class name : example1 EntityBat , example2 minecraft.entity.passive>", new Object[0]); 
    String className = args[1].toLowerCase();
    List<Entity> list = (sender.getEntityWorld()).loadedEntityList;
    int removed = 0;
    for (int i = 0; i < list.size(); i++) {
      if (list.get(i) != null && !(list.get(i) instanceof EntityPlayer))
        if (((Entity)list.get(i)).getClass().getName().toLowerCase().indexOf(className) >= 0) {
          ((Entity)list.get(i)).isDead = true;
          removed++;
        }  
    } 
    sender.sendMessage((ITextComponent)new TextComponentString(removed + " entity removed(" + args[1] + ")."));
  }
  
  private void executeStatus(ICommandSender sender, String[] args) throws WrongUsageException {
    if (args.length < 2)
      throw new WrongUsageException("/mcheli status <entity or tile> [min num]", new Object[0]); 
    if (args[1].equalsIgnoreCase("entity")) {
      executeStatusSub(sender, args, "Server loaded Entity List", (sender.getEntityWorld()).loadedEntityList);
    } else if (args[1].equalsIgnoreCase("tile")) {
      executeStatusSub(sender, args, "Server loaded Tile Entity List", 
          (sender.getEntityWorld()).loadedTileEntityList);
    } 
  }
  
  private void executeStatusSub(ICommandSender sender, String[] args, String title, List<?> list) {
    int minNum = (args.length >= 3) ? Integer.valueOf(args[2]).intValue() : 0;
    HashMap<String, Integer> map = new HashMap<>();
    for (int i = 0; i < list.size(); i++) {
      String key = list.get(i).getClass().getName();
      if (map.containsKey(key)) {
        map.put(key, Integer.valueOf(((Integer)map.get(key)).intValue() + 1));
      } else {
        map.put(key, Integer.valueOf(1));
      } 
    } 
    List<Map.Entry<String, Integer>> entries = new ArrayList<>(map.entrySet());
    Collections.sort(entries, new Comparator<Map.Entry<String, Integer>>() {
          public int compare(Map.Entry<String, Integer> entry1, Map.Entry<String, Integer> entry2) {
            return ((String)entry1.getKey()).compareTo(entry2.getKey());
          }
        });
    boolean send = false;
    sender.sendMessage((ITextComponent)new TextComponentString("--- " + title + " ---"));
    for (Map.Entry<String, Integer> s : entries) {
      if (((Integer)s.getValue()).intValue() >= minNum) {
        String msg = " " + (String)s.getKey() + " : " + s.getValue();
        System.out.println(msg);
        sender.sendMessage((ITextComponent)new TextComponentString(msg));
        send = true;
      } 
    } 
    if (!send) {
      System.out.println("none");
      sender.sendMessage((ITextComponent)new TextComponentString("none"));
    } 
  }
  
  public void executeFill(ICommandSender sender, String[] args) throws CommandException {
    if (args.length < 8)
      throw new WrongUsageException("/mcheli fill <x1> <y1> <z1> <x2> <y2> <z2> <block name> [meta data] [oldBlockHandling] [data tag]", new Object[0]); 
    int x1 = sender.getPosition().getX();
    int y1 = sender.getPosition().getY();
    int z1 = sender.getPosition().getZ();
    int x2 = sender.getPosition().getX();
    int y2 = sender.getPosition().getY();
    int z2 = sender.getPosition().getZ();
    x1 = MathHelper.floor(parseCoordinate(x1, args[1], true).getResult());
    y1 = MathHelper.floor(parseCoordinate(y1, args[2], true).getResult());
    z1 = MathHelper.floor(parseCoordinate(z1, args[3], true).getResult());
    x2 = MathHelper.floor(parseCoordinate(x2, args[4], true).getResult());
    y2 = MathHelper.floor(parseCoordinate(y2, args[5], true).getResult());
    z2 = MathHelper.floor(parseCoordinate(z2, args[6], true).getResult());
    Block block = CommandBase.getBlockByText(sender, args[7]);
    IBlockState iblockstate = block.getDefaultState();
    if (args.length >= 9)
      iblockstate = convertArgToBlockState(block, args[8]); 
    World world = sender.getEntityWorld();
    if (x1 > x2) {
      int t = x1;
      x1 = x2;
      x2 = t;
    } 
    if (y1 > y2) {
      int t = y1;
      y1 = y2;
      y2 = t;
    } 
    if (z1 > z2) {
      int t = z1;
      z1 = z2;
      z2 = t;
    } 
    if (y1 < 0 || y2 >= 256)
      throw new CommandException("commands.setblock.outOfWorld", new Object[0]); 
    int blockNum = (x2 - x1 + 1) * (y2 - y1 + 1) * (z2 - z1 + 1);
    if (blockNum > 3000000)
      throw new CommandException("commands.setblock.tooManyBlocks " + blockNum + " limit=327680", new Object[] { Integer.valueOf(blockNum), Integer.valueOf(3276800) }); 
    boolean result = false;
    boolean keep = (args.length >= 10 && args[9].equals("keep"));
    boolean destroy = (args.length >= 10 && args[9].equals("destroy"));
    boolean override = (args.length >= 10 && args[9].equals("override"));
    NBTTagCompound nbttagcompound = new NBTTagCompound();
    boolean flag = false;
    if (args.length >= 11 && block.hasTileEntity(iblockstate)) {
      String s = getChatComponentFromNthArg(sender, args, 10).getUnformattedText();
      try {
        NBTTagCompound nbtbase = JsonToNBT.getTagFromJson(s);
        if (!(nbtbase instanceof NBTTagCompound))
          throw new CommandException("commands.setblock.tagError", new Object[] { "Not a valid tag" }); 
        nbttagcompound = nbtbase;
        flag = true;
      } catch (NBTException nbtexception) {
        throw new CommandException("commands.setblock.tagError", new Object[] { nbtexception
              
              .getMessage() });
      } 
    } 
    for (int x = x1; x <= x2; x++) {
      for (int y = y1; y <= y2; y++) {
        for (int z = z1; z <= z2; z++) {
          BlockPos blockpos = new BlockPos(x, y, z);
          if (world.isBlockLoaded(blockpos))
            if (world.isAirBlock(blockpos) ? !override : !keep) {
              if (destroy)
                world.destroyBlock(blockpos, false); 
              TileEntity block2 = world.getTileEntity(blockpos);
              if (block2 instanceof IInventory) {
                IInventory ii = (IInventory)block2;
                for (int i = 0; i < ii.getSizeInventory(); i++) {
                  ItemStack is = ii.removeStackFromSlot(i);
                  if (!is.isEmpty())
                    is.setCount(0); 
                } 
              } 
              if (world.setBlockState(blockpos, iblockstate, 3)) {
                if (flag) {
                  TileEntity tileentity = world.getTileEntity(blockpos);
                  if (tileentity != null) {
                    nbttagcompound.setInteger("x", x);
                    nbttagcompound.setInteger("y", y);
                    nbttagcompound.setInteger("z", z);
                    tileentity.readFromNBT(nbttagcompound);
                  } 
                } 
                result = true;
              } 
            }  
        } 
      } 
    } 
    if (result) {
      notifyCommandListener(sender, (ICommand)this, "commands.setblock.success", new Object[0]);
    } else {
      throw new CommandException("commands.setblock.noChange", new Object[0]);
    } 
  }
  
  public List<String> getTabCompletions(MinecraftServer server, ICommandSender sender, String[] prm, BlockPos targetPos) {
    if (!MCH_Config.EnableCommand.prmBool)
      return super.getTabCompletions(server, sender, prm, targetPos); 
    if (prm.length <= 1)
      return getListOfStringsMatchingLastWord(prm, ALL_COMMAND); 
    if (prm[0].equalsIgnoreCase("sendss")) {
      if (prm.length == 2)
        return getListOfStringsMatchingLastWord(prm, server.getOnlinePlayerNames()); 
    } else if (prm[0].equalsIgnoreCase("modlist")) {
      if (prm.length >= 2)
        return getListOfStringsMatchingLastWord(prm, server.getOnlinePlayerNames()); 
    } else {
      if (prm[0].equalsIgnoreCase("fill")) {
        if ((prm.length == 2 || prm.length == 5) && sender instanceof Entity) {
          Entity entity = (Entity)sender;
          List<String> a = new ArrayList<>();
          int x = (entity.posX < 0.0D) ? (int)(entity.posX - 1.0D) : (int)entity.posX;
          int z = (entity.posZ < 0.0D) ? (int)(entity.posZ - 1.0D) : (int)entity.posZ;
          a.add("" + x + " " + (int)(entity.posY + 0.5D) + " " + z);
          return a;
        } 
        return (prm.length == 10) ? getListOfStringsMatchingLastWord(prm, new String[] { "replace", "destroy", "keep", "override" }) : ((prm.length == 8) ? 
          
          getListOfStringsMatchingLastWord(prm, Block.REGISTRY.getKeys()) : null);
      } 
      if (prm[0].equalsIgnoreCase("status")) {
        if (prm.length == 2)
          return getListOfStringsMatchingLastWord(prm, new String[] { "entity", "tile" }); 
      } else if (prm[0].equalsIgnoreCase("attackentity")) {
        if (prm.length == 4)
          return getListOfStringsMatchingLastWord(prm, new String[] { 
                "player", "inFire", "onFire", "lava", "inWall", "drown", "starve", "cactus", "fall", "outOfWorld", 
                "generic", "magic", "wither", "anvil", "fallingBlock" }); 
      } else if (prm[0].equalsIgnoreCase("showboundingbox")) {
        if (prm.length == 2)
          return getListOfStringsMatchingLastWord(prm, new String[] { "true", "false" }); 
      } 
    } 
    return super.getTabCompletions(server, sender, prm, targetPos);
  }
}
