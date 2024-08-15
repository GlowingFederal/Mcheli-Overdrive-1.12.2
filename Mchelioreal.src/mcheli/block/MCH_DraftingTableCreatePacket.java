/*     */ package mcheli.block;
/*     */ 
/*     */ import com.google.common.io.ByteArrayDataInput;
/*     */ import java.io.DataOutputStream;
/*     */ import java.io.IOException;
/*     */ import mcheli.MCH_Lib;
/*     */ import mcheli.MCH_Packet;
/*     */ import mcheli.__helper.network.PacketHelper;
/*     */ import mcheli.wrapper.W_Network;
/*     */ import mcheli.wrapper.W_PacketBase;
/*     */ import net.minecraft.item.crafting.IRecipe;
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
/*     */ public class MCH_DraftingTableCreatePacket
/*     */   extends MCH_Packet
/*     */ {
/*     */   public IRecipe recipe;
/*     */   
/*     */   public int getMessageID() {
/*  34 */     return 537395216;
/*     */   }
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
/*     */   
/*     */   public void readData(ByteArrayDataInput data) {
/*     */     try {
/*  55 */       this.recipe = PacketHelper.readRecipe(data);
/*     */     }
/*  57 */     catch (Exception exception) {}
/*     */   }
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
/*     */   public void writeData(DataOutputStream dos) {
/*     */     try {
/*  75 */       PacketHelper.writeRecipe(dos, this.recipe);
/*     */     }
/*  77 */     catch (IOException e) {
/*     */       
/*  79 */       e.printStackTrace();
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void send(IRecipe recipe) {
/*  90 */     if (recipe != null) {
/*     */       
/*  92 */       MCH_DraftingTableCreatePacket s = new MCH_DraftingTableCreatePacket();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 101 */       s.recipe = recipe;
/* 102 */       W_Network.sendToServer((W_PacketBase)s);
/*     */ 
/*     */       
/* 105 */       MCH_Lib.DbgLog(true, "MCH_DraftingTableCreatePacket.send recipe = " + recipe.getRegistryName(), new Object[0]);
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Users\leo\Downloads\Mchelioreal\!\mcheli\block\MCH_DraftingTableCreatePacket.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */