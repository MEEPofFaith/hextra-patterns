package com.meepoffaith.hextra;

import com.meepoffaith.hextra.init.Arithmetics;
import com.meepoffaith.hextra.init.Patterns;
import com.meepoffaith.hextra.init.SpecialHandlers;
import net.fabricmc.api.ModInitializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class HextraPatterns implements ModInitializer {
	public static final String MOD_ID = "hextra";

	// This logger is used to write text to the console and the log file.
	// It is considered best practice to use your mod id as the logger's name.
	// That way, it's clear which mod wrote info, warnings, and errors.
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	@Override
	public void onInitialize() {
		LOGGER.info("Is anyone there? Hello? I'm trAPPED IN HERE AND CAN'T GET OUT H E L P !");

		Patterns.init();
		Arithmetics.init();
	}
}
