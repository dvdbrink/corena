package com.danielvandenbrink.corena.server.desktop;

import com.danielvandenbrink.corena.server.GameServer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DesktopLauncher {
	private static final Logger log = LoggerFactory.getLogger("DesktopLauncher");

	public static void main(String[] args) {
		if (args.length > 0) {
			try {
				final int port = Integer.parseInt(args[0]);
				new GameServer(port);
			} catch (NumberFormatException e) {
				log.error("Invalid port: {}", args[0]);
			}
		} else {
			log.info("Missing command-line argument: port");
		}
	}
}
