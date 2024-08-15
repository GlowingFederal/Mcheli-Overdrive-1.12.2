/*     */ package mcheli.__helper.network;
/*     */ 
/*     */ import com.google.common.io.ByteArrayDataInput;
/*     */ import io.netty.handler.codec.EncoderException;
/*     */ import java.io.DataInput;
/*     */ import java.io.DataOutputStream;
/*     */ import java.io.IOException;
/*     */ import javax.annotation.Nullable;
/*     */ import net.minecraft.item.Item;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraft.item.crafting.IRecipe;
/*     */ import net.minecraft.nbt.CompressedStreamTools;
/*     */ import net.minecraft.nbt.NBTSizeTracker;
/*     */ import net.minecraft.nbt.NBTTagCompound;
/*     */ import net.minecraft.util.ResourceLocation;
/*     */ import net.minecraftforge.fml.common.registry.ForgeRegistries;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class PacketHelper
/*     */ {
/*     */   public static void writeCompoundTag(DataOutputStream dos, @Nullable NBTTagCompound nbt) throws IOException {
/*  29 */     if (nbt == null) {
/*     */       
/*  31 */       dos.writeByte(0);
/*     */     }
/*     */     else {
/*     */       
/*  35 */       dos.writeByte(1);
/*     */       
/*     */       try {
/*  38 */         CompressedStreamTools.func_74800_a(nbt, dos);
/*     */       }
/*  40 */       catch (IOException ioexception) {
/*     */         
/*  42 */         throw new EncoderException(ioexception);
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   public static NBTTagCompound readCompoundTag(ByteArrayDataInput data) throws IOException {
/*  50 */     byte b0 = data.readByte();
/*     */     
/*  52 */     if (b0 == 0)
/*     */     {
/*  54 */       return null;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     try {
/*  60 */       return CompressedStreamTools.func_152456_a((DataInput)data, new NBTSizeTracker(2097152L));
/*     */     }
/*  62 */     catch (IOException ioexception) {
/*     */       
/*  64 */       throw new EncoderException(ioexception);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static void writeItemStack(DataOutputStream dos, ItemStack itemstack) throws IOException {
/*  71 */     if (itemstack.func_190926_b()) {
/*     */       
/*  73 */       dos.writeShort(-1);
/*     */     }
/*     */     else {
/*     */       
/*  77 */       dos.writeShort(Item.func_150891_b(itemstack.func_77973_b()));
/*  78 */       dos.writeByte(itemstack.func_190916_E());
/*  79 */       dos.writeShort(itemstack.func_77960_j());
/*  80 */       NBTTagCompound nbttagcompound = null;
/*     */       
/*  82 */       if (itemstack.func_77973_b().func_77645_m() || itemstack.func_77973_b().func_77651_p())
/*     */       {
/*  84 */         nbttagcompound = itemstack.func_77973_b().getNBTShareTag(itemstack);
/*     */       }
/*     */       
/*  87 */       writeCompoundTag(dos, nbttagcompound);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public static ItemStack readItemStack(ByteArrayDataInput data) throws IOException {
/*  93 */     int i = data.readShort();
/*     */     
/*  95 */     if (i < 0)
/*     */     {
/*  97 */       return ItemStack.field_190927_a;
/*     */     }
/*     */ 
/*     */     
/* 101 */     int j = data.readByte();
/* 102 */     int k = data.readShort();
/* 103 */     ItemStack itemstack = new ItemStack(Item.func_150899_d(i), j, k);
/* 104 */     itemstack.func_77973_b().readNBTShareTag(itemstack, readCompoundTag(data));
/* 105 */     return itemstack;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static void writeRecipe(DataOutputStream dos, IRecipe recipe) throws IOException {
/* 111 */     dos.writeUTF(recipe.getRegistryName().toString());
/*     */   }
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   public static IRecipe readRecipe(ByteArrayDataInput data) throws IOException {
/* 117 */     return (IRecipe)ForgeRegistries.RECIPES.getValue(new ResourceLocation(data.readUTF()));
/*     */   }
/*     */ }


/* Location:              C:\Users\leo\Downloads\Mchelioreal\!\mcheli\__helper\network\PacketHelper.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */