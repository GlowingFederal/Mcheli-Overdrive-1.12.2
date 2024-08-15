/*     */ package mcheli.aircraft;
/*     */ 
/*     */ import com.google.common.io.ByteArrayDataInput;
/*     */ import java.io.DataOutputStream;
/*     */ import java.io.IOException;
/*     */ import mcheli.MCH_Packet;
/*     */ import mcheli.wrapper.W_Entity;
/*     */ import mcheli.wrapper.W_Network;
/*     */ import mcheli.wrapper.W_PacketBase;
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.entity.player.EntityPlayer;
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
/*     */ 
/*     */ public class MCH_PacketNotifyAmmoNum
/*     */   extends MCH_Packet
/*     */ {
/*  31 */   public int entityID_Ac = -1;
/*     */   public boolean all = false;
/*  33 */   public byte weaponID = -1;
/*  34 */   public byte num = 0;
/*  35 */   public short[] ammo = new short[0];
/*  36 */   public short[] restAmmo = new short[0];
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getMessageID() {
/*  42 */     return 268439604;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void readData(ByteArrayDataInput data) {
/*     */     try {
/*  50 */       this.entityID_Ac = data.readInt();
/*  51 */       this.all = (data.readByte() != 0);
/*  52 */       if (this.all) {
/*     */         
/*  54 */         this.num = data.readByte();
/*  55 */         this.ammo = new short[this.num];
/*  56 */         this.restAmmo = new short[this.num];
/*  57 */         for (int i = 0; i < this.num; i++)
/*     */         {
/*  59 */           this.ammo[i] = data.readShort();
/*  60 */           this.restAmmo[i] = data.readShort();
/*     */         }
/*     */       
/*     */       } else {
/*     */         
/*  65 */         this.weaponID = data.readByte();
/*  66 */         this
/*     */           
/*  68 */           .ammo = new short[] { data.readShort() };
/*     */         
/*  70 */         this
/*     */           
/*  72 */           .restAmmo = new short[] { data.readShort() };
/*     */       }
/*     */     
/*     */     }
/*  76 */     catch (Exception e) {
/*     */       
/*  78 */       e.printStackTrace();
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void writeData(DataOutputStream dos) {
/*     */     try {
/*  87 */       dos.writeInt(this.entityID_Ac);
/*  88 */       dos.writeByte(this.all ? 1 : 0);
/*     */       
/*  90 */       if (this.all) {
/*     */         
/*  92 */         dos.writeByte(this.num);
/*     */         
/*  94 */         for (int i = 0; i < this.num; i++)
/*     */         {
/*  96 */           dos.writeShort(this.ammo[i]);
/*  97 */           dos.writeShort(this.restAmmo[i]);
/*     */         }
/*     */       
/*     */       } else {
/*     */         
/* 102 */         dos.writeByte(this.weaponID);
/* 103 */         dos.writeShort(this.ammo[0]);
/* 104 */         dos.writeShort(this.restAmmo[0]);
/*     */       }
/*     */     
/* 107 */     } catch (IOException e) {
/*     */       
/* 109 */       e.printStackTrace();
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public static void sendAllAmmoNum(MCH_EntityAircraft ac, EntityPlayer target) {
/* 115 */     MCH_PacketNotifyAmmoNum s = new MCH_PacketNotifyAmmoNum();
/*     */     
/* 117 */     s.entityID_Ac = W_Entity.getEntityId((Entity)ac);
/* 118 */     s.all = true;
/* 119 */     s.num = (byte)ac.getWeaponNum();
/* 120 */     s.ammo = new short[s.num];
/* 121 */     s.restAmmo = new short[s.num];
/*     */     
/* 123 */     for (int i = 0; i < s.num; i++) {
/*     */       
/* 125 */       s.ammo[i] = (short)ac.getWeapon(i).getAmmoNum();
/* 126 */       s.restAmmo[i] = (short)ac.getWeapon(i).getRestAllAmmoNum();
/*     */     } 
/*     */     
/* 129 */     send(s, ac, target);
/*     */   }
/*     */ 
/*     */   
/*     */   public static void sendAmmoNum(MCH_EntityAircraft ac, EntityPlayer target, int wid) {
/* 134 */     sendAmmoNum(ac, target, wid, ac.getWeapon(wid).getAmmoNum(), ac.getWeapon(wid).getRestAllAmmoNum());
/*     */   }
/*     */ 
/*     */   
/*     */   public static void sendAmmoNum(MCH_EntityAircraft ac, EntityPlayer target, int wid, int ammo, int rest_ammo) {
/* 139 */     MCH_PacketNotifyAmmoNum s = new MCH_PacketNotifyAmmoNum();
/*     */     
/* 141 */     s.entityID_Ac = W_Entity.getEntityId((Entity)ac);
/* 142 */     s.all = false;
/* 143 */     s.weaponID = (byte)wid;
/* 144 */     s.ammo = new short[] { (short)ammo };
/*     */ 
/*     */ 
/*     */     
/* 148 */     s.restAmmo = new short[] { (short)rest_ammo };
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 153 */     send(s, ac, target);
/*     */   }
/*     */ 
/*     */   
/*     */   public static void send(MCH_PacketNotifyAmmoNum s, MCH_EntityAircraft ac, EntityPlayer target) {
/* 158 */     if (target == null) {
/*     */       
/* 160 */       for (int i = 0; i < ac.getSeatNum() + 1; i++)
/*     */       {
/* 162 */         Entity e = ac.getEntityBySeatId(i);
/*     */         
/* 164 */         if (e instanceof EntityPlayer)
/*     */         {
/* 166 */           W_Network.sendToPlayer((W_PacketBase)s, (EntityPlayer)e);
/*     */         }
/*     */       }
/*     */     
/*     */     } else {
/*     */       
/* 172 */       W_Network.sendToPlayer((W_PacketBase)s, target);
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Users\leo\Downloads\Mchelioreal\!\mcheli\aircraft\MCH_PacketNotifyAmmoNum.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */