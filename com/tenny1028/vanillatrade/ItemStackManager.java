package com.tenny1028.vanillatrade;

import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.inventory.ItemStack;

/**
 * Created by jasper on 4/29/16.
 */
public class ItemStackManager {

	public static ItemStack getPayment(FileConfiguration config){
		if(!config.contains("payment")){
			return null;
		}

		Material material = Material.matchMaterial(config.getString("payment.type","GOLD_INGOT"));
		int amount = config.getInt("payment.amount",1);
		int data = config.getInt("payment.data", 0);

		return new ItemStack(material,amount,(short)data);
	}

	public static ItemStack parsePayment(String str){
		Material paymentType = null;

		String typeStr = "";
		short data = 0;

		if(str.contains(":")){
			String[] split = str.split(":");
			typeStr = split[0];
			try{
				data = Short.parseShort(split[1]);
			}catch (NumberFormatException e){
				return null;
			}
		}else{
			typeStr = str;
			data = 0;
		}

		try{
			int id = Integer.parseInt(typeStr);
			paymentType = Material.getMaterial(id);
		}catch (NumberFormatException ex) {
			paymentType = Material.matchMaterial(typeStr.replace(" ","_"));
		}

		if(paymentType == null){
			return null;
		}

		return new ItemStack(paymentType,1,data);
	}

	public static String itemStackToHumanReadableFormat(ItemStack item, boolean ignoreNumber){
		StringBuilder builder = new StringBuilder();
		if(!ignoreNumber){
			builder.append(item.getType().name());
			if(item.getDurability()>0){
				builder.append(":").append(item.getDurability());
			}
		}

		return builder.toString();
	}

	public static String itemStackToHumanReadableFormat(ItemStack item){
		return itemStackToHumanReadableFormat(item, false);
	}
}