package mcheli.mob;

import java.util.Collections;
import java.util.List;
import javax.annotation.Nullable;
import mcheli.MCH_Config;
import mcheli.MCH_Lib;
import mcheli.MCH_MOD;
import mcheli.aircraft.MCH_AircraftInfo;
import mcheli.aircraft.MCH_EntityAircraft;
import mcheli.aircraft.MCH_EntitySeat;
import mcheli.aircraft.MCH_SeatInfo;
import mcheli.weapon.MCH_WeaponBase;
import mcheli.weapon.MCH_WeaponParam;
import mcheli.weapon.MCH_WeaponSet;
import mcheli.wrapper.W_WorldFunc;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.monster.IMob;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.scoreboard.ScorePlayerTeam;
import net.minecraft.scoreboard.Team;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumHand;
import net.minecraft.util.EnumHandSide;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ITeleporter;

public class MCH_EntityGunner extends EntityLivingBase {
  private static final DataParameter<String> TEAM_NAME = EntityDataManager.createKey(MCH_EntityGunner.class, DataSerializers.STRING);
  
  public boolean isCreative = false;
  
  public String ownerUUID = "";
  
  public int targetType = 0;
  
  public int despawnCount = 0;
  
  public int switchTargetCount = 0;
  
  public Entity targetEntity = null;
  
  public double targetPrevPosX = 0.0D;
  
  public double targetPrevPosY = 0.0D;
  
  public double targetPrevPosZ = 0.0D;
  
  public boolean waitCooldown = false;
  
  public int idleCount = 0;
  
  public int idleRotation = 0;
  
  public MCH_EntityGunner(World world) {
    super(world);
  }
  
  public MCH_EntityGunner(World world, double x, double y, double z) {
    this(world);
    setPosition(x, y, z);
  }
  
  protected void entityInit() {
    super.entityInit();
    this.dataManager.register(TEAM_NAME, "");
  }
  
  public String getTeamName() {
    return (String)this.dataManager.get(TEAM_NAME);
  }
  
  public void setTeamName(String name) {
    this.dataManager.set(TEAM_NAME, name);
  }
  
  public Team getTeam() {
    return (Team)this.world.getScoreboard().getTeam(getTeamName());
  }
  
  public boolean isOnSameTeam(Entity entityIn) {
    return super.isOnSameTeam(entityIn);
  }
  
  public ITextComponent getDisplayName() {
    Team team = getTeam();
    if (team != null) {
      String name = MCH_MOD.isTodaySep01() ? "'s EMB4" : " Gunner";
      return (ITextComponent)new TextComponentString(ScorePlayerTeam.formatPlayerName(team, team.getRegisteredName() + name));
    } 
    return (ITextComponent)new TextComponentString("");
  }
  
  public boolean isEntityInvulnerable(DamageSource source) {
    return this.isCreative;
  }
  
  public void onDeath(DamageSource source) {
    super.onDeath(source);
  }
  
  public boolean processInitialInteract(EntityPlayer player, EnumHand hand) {
    if (this.world.isRemote)
      return false; 
    if (getRidingEntity() == null)
      return false; 
    if (player.capabilities.isCreativeMode) {
      removeFromAircraft(player);
      return true;
    } 
    if (this.isCreative) {
      player.addChatMessage((ITextComponent)new TextComponentString("Creative mode only."));
      return false;
    } 
    if (getTeam() == null || isOnSameTeam((Entity)player)) {
      removeFromAircraft(player);
      return true;
    } 
    player.addChatMessage((ITextComponent)new TextComponentString("You are other team."));
    return false;
  }
  
  public void removeFromAircraft(EntityPlayer player) {
    if (!this.world.isRemote) {
      W_WorldFunc.MOD_playSoundAtEntity((Entity)player, "wrench", 1.0F, 1.0F);
      setDead();
      MCH_EntityAircraft ac = null;
      if (getRidingEntity() instanceof MCH_EntityAircraft) {
        ac = (MCH_EntityAircraft)getRidingEntity();
      } else if (getRidingEntity() instanceof MCH_EntitySeat) {
        ac = ((MCH_EntitySeat)getRidingEntity()).getParent();
      } 
      String name = "";
      if (ac != null && ac.getAcInfo() != null)
        name = " on " + (ac.getAcInfo()).displayName + " seat " + (ac.getSeatIdByEntity((Entity)this) + 1); 
      String playerName = ScorePlayerTeam.formatPlayerName(player.getTeam(), player.getDisplayName().getFormattedText());
      if (MCH_MOD.isTodaySep01()) {
        player.addChatMessage((ITextComponent)new TextComponentTranslation("chat.type.text", new Object[] { "EMB4", new TextComponentString("Bye " + playerName + "! Good vehicle" + name) }));
      } else {
        player.addChatMessage((ITextComponent)new TextComponentString("Remove gunner" + name + " by " + playerName + "."));
      } 
      dismountRidingEntity();
    } 
  }
  
  public void onUpdate() {
    super.onUpdate();
    if (!this.world.isRemote && !this.isDead) {
      if (getRidingEntity() != null && (getRidingEntity()).isDead)
        dismountRidingEntity(); 
      if (getRidingEntity() instanceof MCH_EntityAircraft) {
        shotTarget((MCH_EntityAircraft)getRidingEntity());
      } else if (getRidingEntity() instanceof MCH_EntitySeat && ((MCH_EntitySeat)
        getRidingEntity()).getParent() != null) {
        shotTarget(((MCH_EntitySeat)getRidingEntity()).getParent());
      } else if (this.despawnCount < 20) {
        this.despawnCount++;
      } else if (getRidingEntity() == null || this.ticksExisted > 100) {
        setDead();
      } 
      if (this.targetEntity == null) {
        if (this.idleCount == 0) {
          this.idleCount = (3 + this.rand.nextInt(5)) * 20;
          this.idleRotation = this.rand.nextInt(5) - 2;
        } 
        this.rotationYaw += this.idleRotation / 2.0F;
      } else {
        this.idleCount = 60;
      } 
    } 
    if (this.switchTargetCount > 0)
      this.switchTargetCount--; 
    if (this.idleCount > 0)
      this.idleCount--; 
  }
  
  public boolean canAttackEntity(EntityLivingBase entity, MCH_EntityAircraft ac, MCH_WeaponSet ws) {
    boolean ret = false;
    if (this.targetType == 0) {
      ret = (entity != this && !(entity instanceof net.minecraft.entity.monster.EntityEnderman) && !entity.isDead && !isOnSameTeam((Entity)entity) && entity.getHealth() > 0.0F && !ac.isMountedEntity((Entity)entity));
    } else {
      ret = (entity != this && !((EntityPlayer)entity).capabilities.isCreativeMode && !entity.isDead && !getTeamName().isEmpty() && !isOnSameTeam((Entity)entity) && entity.getHealth() > 0.0F && !ac.isMountedEntity((Entity)entity));
    } 
    if (ret && ws.getCurrentWeapon().getGuidanceSystem() != null)
      ret = ws.getCurrentWeapon().getGuidanceSystem().canLockEntity((Entity)entity); 
    return ret;
  }
  
  public void shotTarget(MCH_EntityAircraft ac) {
    if (ac.isDestroyed())
      return; 
    if (!ac.getGunnerStatus())
      return; 
    MCH_WeaponSet ws = ac.getCurrentWeapon((Entity)this);
    if (ws == null || ws.getInfo() == null || ws.getCurrentWeapon() == null)
      return; 
    MCH_WeaponBase cw = ws.getCurrentWeapon();
    if (this.targetEntity != null && (this.targetEntity.isDead || ((EntityLivingBase)this.targetEntity)
      .getHealth() <= 0.0F))
      if (this.switchTargetCount > 20)
        this.switchTargetCount = 20;  
    Vec3d pos = getGunnerWeaponPos(ac, ws);
    if ((this.targetEntity == null && this.switchTargetCount <= 0) || this.switchTargetCount <= 0) {
      List<? extends EntityLivingBase> list;
      this.switchTargetCount = 20;
      EntityLivingBase nextTarget = null;
      if (this.targetType == 0) {
        int rh = MCH_Config.RangeOfGunner_VsMonster_Horizontal.prmInt;
        int rv = MCH_Config.RangeOfGunner_VsMonster_Vertical.prmInt;
        list = this.world.getEntitiesWithinAABB(EntityLivingBase.class, 
            getEntityBoundingBox().expand(rh, rv, rh), IMob.MOB_SELECTOR);
      } else {
        int rh = MCH_Config.RangeOfGunner_VsPlayer_Horizontal.prmInt;
        int rv = MCH_Config.RangeOfGunner_VsPlayer_Vertical.prmInt;
        list = this.world.getEntitiesWithinAABB(EntityPlayer.class, 
            getEntityBoundingBox().expand(rh, rv, rh));
      } 
      for (int i = 0; i < list.size(); i++) {
        EntityLivingBase entity = list.get(i);
        if (canAttackEntity(entity, ac, ws))
          if (checkPitch(entity, ac, pos))
            if ((nextTarget == null || getDistanceToEntity((Entity)entity) < getDistanceToEntity((Entity)nextTarget)) && 
              canEntityBeSeen((Entity)entity))
              if (isInAttackable(entity, ac, ws, pos)) {
                nextTarget = entity;
                this.switchTargetCount = 60;
              }    
      } 
      if (nextTarget != null && this.targetEntity != nextTarget) {
        this.targetPrevPosX = nextTarget.posX;
        this.targetPrevPosY = nextTarget.posY;
        this.targetPrevPosZ = nextTarget.posZ;
      } 
      this.targetEntity = (Entity)nextTarget;
    } 
    if (this.targetEntity != null) {
      float rotSpeed = 10.0F;
      if (ac.isPilot((Entity)this))
        rotSpeed = (ac.getAcInfo()).cameraRotationSpeed / 10.0F; 
      this.rotationPitch = MathHelper.wrapDegrees(this.rotationPitch);
      this.rotationYaw = MathHelper.wrapDegrees(this.rotationYaw);
      double dist = getDistanceToEntity(this.targetEntity);
      double tick = 1.0D;
      if (dist >= 10.0D && (ws.getInfo()).acceleration > 1.0F)
        tick = dist / (ws.getInfo()).acceleration; 
      if (this.targetEntity.getRidingEntity() instanceof MCH_EntitySeat || this.targetEntity
        .getRidingEntity() instanceof MCH_EntityAircraft)
        tick -= MCH_Config.HitBoxDelayTick.prmInt; 
      double dx = (this.targetEntity.posX - this.targetPrevPosX) * tick;
      double dy = (this.targetEntity.posY - this.targetPrevPosY) * tick + this.targetEntity.height * this.rand.nextDouble();
      double dz = (this.targetEntity.posZ - this.targetPrevPosZ) * tick;
      double d0 = this.targetEntity.posX + dx - pos.x;
      double d1 = this.targetEntity.posY + dy - pos.y;
      double d2 = this.targetEntity.posZ + dz - pos.z;
      double d3 = MathHelper.sqrt(d0 * d0 + d2 * d2);
      float yaw = MathHelper.wrapDegrees((float)(Math.atan2(d2, d0) * 180.0D / Math.PI) - 90.0F);
      float pitch = (float)-(Math.atan2(d1, d3) * 180.0D / Math.PI);
      if (Math.abs(this.rotationPitch - pitch) < rotSpeed && Math.abs(this.rotationYaw - yaw) < rotSpeed) {
        float r = ac.isPilot((Entity)this) ? 0.1F : 0.5F;
        this.rotationPitch = pitch + (this.rand.nextFloat() - 0.5F) * r - cw.fixRotationPitch;
        this.rotationYaw = yaw + (this.rand.nextFloat() - 0.5F) * r;
        if (!this.waitCooldown || ws.currentHeat <= 0 || (ws.getInfo()).maxHeatCount <= 0) {
          this.waitCooldown = false;
          MCH_WeaponParam prm = new MCH_WeaponParam();
          prm.setPosition(ac.posX, ac.posY, ac.posZ);
          prm.user = (Entity)this;
          prm.entity = (Entity)ac;
          prm.option1 = (cw instanceof mcheli.weapon.MCH_WeaponEntitySeeker) ? this.targetEntity.getEntityId() : 0;
          if (ac.useCurrentWeapon(prm))
            if ((ws.getInfo()).maxHeatCount > 0 && ws.currentHeat > (ws.getInfo()).maxHeatCount * 4 / 5)
              this.waitCooldown = true;  
        } 
      } 
      if (Math.abs(pitch - this.rotationPitch) >= rotSpeed)
        this.rotationPitch += (pitch > this.rotationPitch) ? rotSpeed : -rotSpeed; 
      if (Math.abs(yaw - this.rotationYaw) >= rotSpeed)
        if (Math.abs(yaw - this.rotationYaw) <= 180.0F) {
          this.rotationYaw += (yaw > this.rotationYaw) ? rotSpeed : -rotSpeed;
        } else {
          this.rotationYaw += (yaw > this.rotationYaw) ? -rotSpeed : rotSpeed;
        }  
      this.rotationYawHead = this.rotationYaw;
      this.targetPrevPosX = this.targetEntity.posX;
      this.targetPrevPosY = this.targetEntity.posY;
      this.targetPrevPosZ = this.targetEntity.posZ;
    } else {
      this.rotationPitch *= 0.95F;
    } 
  }
  
  private boolean checkPitch(EntityLivingBase entity, MCH_EntityAircraft ac, Vec3d pos) {
    try {
      double d0 = entity.posX - pos.x;
      double d1 = entity.posY - pos.y;
      double d2 = entity.posZ - pos.z;
      double d3 = MathHelper.sqrt(d0 * d0 + d2 * d2);
      float pitch = (float)-(Math.atan2(d1, d3) * 180.0D / Math.PI);
      MCH_AircraftInfo ai = ac.getAcInfo();
      if (ac instanceof mcheli.vehicle.MCH_EntityVehicle && ac.isPilot((Entity)this))
        if (Math.abs(ai.minRotationPitch) + Math.abs(ai.maxRotationPitch) > 0.0F) {
          if (pitch < ai.minRotationPitch)
            return false; 
          if (pitch > ai.maxRotationPitch)
            return false; 
        }  
      MCH_WeaponBase cw = ac.getCurrentWeapon((Entity)this).getCurrentWeapon();
      if (!(cw instanceof mcheli.weapon.MCH_WeaponEntitySeeker)) {
        MCH_AircraftInfo.Weapon wi = ai.getWeaponById(ac.getCurrentWeaponID((Entity)this));
        if (Math.abs(wi.minPitch) + Math.abs(wi.maxPitch) > 0.0F) {
          if (pitch < wi.minPitch)
            return false; 
          if (pitch > wi.maxPitch)
            return false; 
        } 
      } 
    } catch (Exception exception) {}
    return true;
  }
  
  public Vec3d getGunnerWeaponPos(MCH_EntityAircraft ac, MCH_WeaponSet ws) {
    MCH_SeatInfo seatInfo = ac.getSeatInfo((Entity)this);
    if ((seatInfo != null && seatInfo.rotSeat) || ac instanceof mcheli.vehicle.MCH_EntityVehicle)
      return ac.calcOnTurretPos((ws.getCurrentWeapon()).position).addVector(ac.posX, ac.posY, ac.posZ); 
    return ac.getTransformedPosition((ws.getCurrentWeapon()).position);
  }
  
  private boolean isInAttackable(EntityLivingBase entity, MCH_EntityAircraft ac, MCH_WeaponSet ws, Vec3d pos) {
    if (ac instanceof mcheli.vehicle.MCH_EntityVehicle)
      return true; 
    try {
      if (ac.getCurrentWeapon((Entity)this).getCurrentWeapon() instanceof mcheli.weapon.MCH_WeaponEntitySeeker)
        return true; 
      MCH_AircraftInfo.Weapon wi = ac.getAcInfo().getWeaponById(ac.getCurrentWeaponID((Entity)this));
      Vec3d v1 = new Vec3d(0.0D, 0.0D, 1.0D);
      float yaw = -ac.getRotYaw() + (wi.maxYaw + wi.minYaw) / 2.0F - wi.defaultYaw;
      v1 = v1.rotateYaw(yaw * 3.1415927F / 180.0F);
      Vec3d v2 = (new Vec3d(entity.posX - pos.x, 0.0D, entity.posZ - pos.z)).normalize();
      double dot = v1.dotProduct(v2);
      double rad = Math.acos(dot);
      double deg = rad * 180.0D / Math.PI;
      return (deg < (Math.abs(wi.maxYaw - wi.minYaw) / 2.0F));
    } catch (Exception exception) {
      return false;
    } 
  }
  
  @Nullable
  public MCH_EntityAircraft getAc() {
    if (getRidingEntity() == null)
      return null; 
    return (getRidingEntity() instanceof MCH_EntityAircraft) ? (MCH_EntityAircraft)getRidingEntity() : (
      (getRidingEntity() instanceof MCH_EntitySeat) ? ((MCH_EntitySeat)
      getRidingEntity()).getParent() : null);
  }
  
  public void writeEntityToNBT(NBTTagCompound nbt) {
    super.writeEntityToNBT(nbt);
    nbt.setBoolean("Creative", this.isCreative);
    nbt.setString("OwnerUUID", this.ownerUUID);
    nbt.setString("TeamName", getTeamName());
    nbt.setInteger("TargetType", this.targetType);
  }
  
  public void readEntityFromNBT(NBTTagCompound nbt) {
    super.readEntityFromNBT(nbt);
    this.isCreative = nbt.getBoolean("Creative");
    this.ownerUUID = nbt.getString("OwnerUUID");
    setTeamName(nbt.getString("TeamName"));
    this.targetType = nbt.getInteger("TargetType");
  }
  
  @Nullable
  public Entity changeDimension(int dimensionIn, ITeleporter teleporter) {
    return null;
  }
  
  public void setDead() {
    if (!this.world.isRemote && !this.isDead && !this.isCreative)
      if (this.targetType == 0) {
        dropItem((Item)MCH_MOD.itemSpawnGunnerVsMonster, 1);
      } else {
        dropItem((Item)MCH_MOD.itemSpawnGunnerVsPlayer, 1);
      }  
    super.setDead();
    MCH_Lib.DbgLog(this.world, "MCH_EntityGunner.setDead type=%d :" + toString(), new Object[] { Integer.valueOf(this.targetType) });
  }
  
  public boolean attackEntityFrom(DamageSource ds, float amount) {
    if (ds == DamageSource.outOfWorld)
      setDead(); 
    return super.attackEntityFrom(ds, amount);
  }
  
  public ItemStack getItemStackFromSlot(EntityEquipmentSlot slotIn) {
    return ItemStack.EMPTY;
  }
  
  public void setItemStackToSlot(EntityEquipmentSlot slotIn, ItemStack stack) {}
  
  public Iterable<ItemStack> getArmorInventoryList() {
    return Collections.emptyList();
  }
  
  public EnumHandSide getPrimaryHand() {
    return EnumHandSide.RIGHT;
  }
}
