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
    if (!this.worldObj.field_72995_K)
      return; 
    clear();
    List<Entity> list = centerEntity.field_70170_p.func_72839_b(centerEntity, centerEntity
        
        .func_174813_aQ().func_72314_b(range, range, range));
    for (int i = 0; i < list.size(); i++) {
      Entity entity = list.get(i);
      if (entity instanceof net.minecraft.entity.EntityLiving) {
        double x = entity.field_70165_t - centerEntity.field_70165_t;
        double z = entity.field_70161_v - centerEntity.field_70161_v;
        if (x * x + z * z < (range * range)) {
          int y = 1 + (int)entity.field_70163_u;
          if (y < 0)
            y = 1; 
          int blockCnt = 0;
          for (; y < 200; y++) {
            if (W_WorldFunc.getBlockId(this.worldObj, (int)entity.field_70165_t, y, (int)entity.field_70161_v) != 0) {
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
