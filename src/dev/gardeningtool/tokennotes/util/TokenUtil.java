package dev.gardeningtool.tokennotes.util;

import org.bukkit.entity.Player;
import com.vk2gpz.tokenenchant.api.TokenEnchantAPI;

public class TokenUtil {

	public static void addTokens(Player p, Double d) {
		TokenEnchantAPI.getInstance().addTokens(p, d);
	}

	public static void removeTokens(Player p, Double d) {
		TokenEnchantAPI.getInstance().removeTokens(p, d);
	}

	public static double getTokens(Player p) {
		return TokenEnchantAPI.getInstance().getTokens(p);
	}
}

