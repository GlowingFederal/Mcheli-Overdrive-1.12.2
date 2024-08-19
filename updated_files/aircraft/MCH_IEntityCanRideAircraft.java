package mcheli.aircraft;

public interface MCH_IEntityCanRideAircraft {
  boolean isSkipNormalRender();
  
  boolean canRideAircraft(MCH_EntityAircraft paramMCH_EntityAircraft, int paramInt, MCH_SeatRackInfo paramMCH_SeatRackInfo);
}
