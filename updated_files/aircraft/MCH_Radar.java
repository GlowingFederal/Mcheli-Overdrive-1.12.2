package mcheli.aircraft;

import java.util.ArrayList;
import java.util.List;
import mcheli.MCH_Vector2;
import mcheli.wrapper.W_WorldFunc;
import net.minecraft.entity.Entity;
import net.minecraft.world.World;

public class MCH_Radar {
  private World worldObj;
  
  private ArrayList<MCH_Vector2> entityList = new ArrayList<>();
  
  private ArrayList<MCH_Vector2> enemyList = new ArrayList<>();
  
  public ArrayList<MCH_Vector2> getEntityList() {
    return this.entityList;
  }
  
  public ArrayList<MCH_Vector2> getEnemyList() {
    return this.enemyList;
  }
  
  public MCH_Radar(World world) {
    this.worldObj = world;
  }
  
  public void clear() {
    this.entityList.clear();
    this.enemyList.clear();
  }
  
  public void updateXZ(Entity centerEntity, int range) {
    if (!this.worldObj.isRemote)
      return; 
    clear();
    List<Entity> list = centerEntity.world.getEntitiesWithinAABBExcludingEntity(centerEntity, centerEntity
        
        .getEntityBoundingBox().expand(range, range, range));
    for (int i = 0; i < list.size(); i++) {
      Entity entity = list.get(i);
      if (entity instanceof net.minecraft.entity.EntityLiving) {
        double x = entity.posX - centerEntity.posX;
        double z = entity.posZ - centerEntity.posZ;
        if (x * x + z * z < (range * range)) {
          int y = 1 + (int)entity.posY;
          if (y < 0)
            y = 1; 
          int blockCnt = 0;
          for (; y < 200; y++) {
            if (W_WorldFunc.getBlockId(this.worldObj, (int)entity.posX, y, (int)entity.posZ) != 0) {
              blockCnt++;
              if (blockCnt >= 5)
                break; 
            } 
          } 
          if (blockCnt < 5)
            if (entity instanceof net.minecraft.entity.monster.EntityMob) {
              this.enemyList.add(new MCH_Vector2(x, z));
            } else {
              this.entityList.add(new MCH_Vector2(x, z));
            }  
        } 
      } 
    } 
  }
}
