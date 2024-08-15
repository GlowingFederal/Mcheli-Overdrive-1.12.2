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
/*     */ public class MCH_PacketSeatListResponse
/*     */   extends MCH_Packet
/*     */ {
/*  27 */   public int entityID_AC = -1;
/*  28 */   public byte seatNum = -1;
/*  29 */   public int[] seatEntityID = new int[] { -1 };
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getMessageID() {
/*  38 */     return 268439569;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void readData(ByteArrayDataInput data) {
/*     */     try {
/*  46 */       this.entityID_AC = data.readInt();
/*  47 */       this.seatNum = data.readByte();
/*  48 */       if (this.seatNum > 0)
/*     */       {
/*  50 */         this.seatEntityID = new int[this.seatNum];
/*  51 */         for (int i = 0; i < this.seatNum; i++)
/*     */         {
/*  53 */           this.seatEntityID[i] = data.readInt();
/*     */         }
/*     */       }
/*     */     
/*  57 */     } catch (Exception e) {
/*     */       
/*  59 */       e.printStackTrace();
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void writeData(DataOutputStream dos) {
/*     */     try {
/*  68 */       dos.writeInt(this.entityID_AC);
/*  69 */       if (this.seatNum > 0 && this.seatEntityID != null && this.seatEntityID.length == this.seatNum)
/*     */       {
/*  71 */         dos.writeByte(this.seatNum);
/*  72 */         for (int i = 0; i < this.seatNum; i++)
/*     */         {
/*  74 */           dos.writeInt(this.seatEntityID[i]);
/*     */         }
/*     */       }
/*     */       else
/*     */       {
/*  79 */         dos.writeByte(-1);
/*     */       }
/*     */     
/*  82 */     } catch (IOException e) {
/*     */       
/*  84 */       e.printStackTrace();
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public static void sendSeatList(MCH_EntityAircraft ac, EntityPlayer player) {
/*  90 */     MCH_PacketSeatListResponse s = new MCH_PacketSeatListResponse();
/*     */     
/*  92 */     s.setParameter(ac);
/*     */     
/*  94 */     W_Network.sendToPlayer((W_PacketBase)s, player);
/*     */   }
/*     */ 
/*     */   
/*     */   protected void setParameter(MCH_EntityAircraft ac) {
/*  99 */     if (ac == null) {
/*     */       return;
/*     */     }
/*     */ 
/*     */     
/* 104 */     this.entityID_AC = W_Entity.getEntityId((Entity)ac);
/* 105 */     this.seatNum = (byte)(ac.getSeats()).length;
/*     */     
/* 107 */     if (this.seatNum > 0) {
/*     */       
/* 109 */       this.seatEntityID = new int[this.seatNum];
/*     */       
/* 111 */       for (int i = 0; i < this.seatNum; i++)
/*     */       {
/* 113 */         this.seatEntityID[i] = W_Entity.getEntityId((Entity)ac.getSeat(i));
/*     */       }
/*     */     }
/*     */     else {
/*     */       
/* 118 */       this.seatEntityID = new int[] { -1 };
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Users\leo\Downloads\Mchelioreal\!\mcheli\aircraft\MCH_PacketSeatListResponse.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */