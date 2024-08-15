/*     */ package mcheli.multiplay;
/*     */ 
/*     */ import com.google.common.io.ByteArrayDataInput;
/*     */ import java.io.DataOutputStream;
/*     */ import mcheli.MCH_Packet;
/*     */ import mcheli.wrapper.W_Network;
/*     */ import mcheli.wrapper.W_PacketBase;
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
/*     */ public class MCH_PacketNotifySpotedEntity
/*     */   extends MCH_Packet
/*     */ {
/*  25 */   public int count = 0;
/*  26 */   public int num = 0;
/*  27 */   public int[] entityId = null;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getMessageID() {
/*  33 */     return 268437761;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void readData(ByteArrayDataInput data) {
/*     */     try {
/*  41 */       this.count = data.readShort();
/*  42 */       this.num = data.readShort();
/*  43 */       if (this.num > 0)
/*     */       {
/*  45 */         this.entityId = new int[this.num];
/*  46 */         for (int i = 0; i < this.num; i++)
/*     */         {
/*  48 */           this.entityId[i] = data.readInt();
/*     */         }
/*     */       }
/*     */       else
/*     */       {
/*  53 */         this.num = 0;
/*     */       }
/*     */     
/*  56 */     } catch (Exception e) {
/*     */       
/*  58 */       e.printStackTrace();
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void writeData(DataOutputStream dos) {
/*     */     try {
/*  67 */       dos.writeShort(this.count);
/*  68 */       dos.writeShort(this.num);
/*  69 */       for (int i = 0; i < this.num; i++)
/*     */       {
/*  71 */         dos.writeInt(this.entityId[i]);
/*     */       }
/*     */     }
/*  74 */     catch (Exception e) {
/*     */       
/*  76 */       e.printStackTrace();
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public static void send(EntityPlayer player, int count, int[] entityId) {
/*  82 */     if (player == null || entityId == null || entityId.length <= 0 || count <= 0)
/*     */       return; 
/*  84 */     if (count > 30000)
/*     */     {
/*  86 */       count = 30000;
/*     */     }
/*  88 */     MCH_PacketNotifySpotedEntity pkt = new MCH_PacketNotifySpotedEntity();
/*  89 */     pkt.count = count;
/*  90 */     pkt.num = entityId.length;
/*  91 */     if (pkt.num > 300)
/*     */     {
/*  93 */       pkt.num = 300;
/*     */     }
/*  95 */     if (pkt.num > entityId.length)
/*     */     {
/*  97 */       pkt.num = entityId.length;
/*     */     }
/*  99 */     pkt.entityId = entityId;
/* 100 */     W_Network.sendToPlayer((W_PacketBase)pkt, player);
/*     */   }
/*     */ }


/* Location:              C:\Users\leo\Downloads\Mchelioreal\!\mcheli\multiplay\MCH_PacketNotifySpotedEntity.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */