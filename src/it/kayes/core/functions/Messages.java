package it.kayes.core.functions;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

public class Messages {
	
	// str.str2 -> String[]
	private static HashMap<String, String[]> messages = new HashMap<String, String[]>();
	
	public static HashMap<String, String[]> getMessages() {
		return messages;
	}
	
	public static String[] getMessage(String path) {
		return messages.get(path);
	}
	
	private static String prefix = "";
	
	public static String getPrefix() {
		return prefix;
	}
	
	public static void load() {
		File f = new File("plugins/KayEs-Core" + File.separator + "messages.yml");
		FileConfiguration cfg = YamlConfiguration.loadConfiguration(f);
		
		if (!f.exists()) {
			try {f.createNewFile();} catch (IOException e) {e.printStackTrace();}
			
			//GENERALI
			cfg.set("general.prefix", new String[] {"&f&lKAY-ES &b&l➤"});
			cfg.set("general.accept", new String[] {"&a&l✔"});
			cfg.set("general.deny", new String[] {"&c&l✘"});
			cfg.set("general.survival", new String[] {"Sopravvivenza"});
			cfg.set("general.creative", new String[] {"Creativa"});
			cfg.set("general.adventure", new String[] {"Avventura"});
			cfg.set("general.spector", new String[] {"Spettatore"});
			
			//ERRORI
			cfg.set("error.nopermission", new String[] {"%PREFIX% &fNon possiedi i permessi per eseguire questa azione"});
			cfg.set("error.noargs", new String[] {"%PREFIX% &fArgomenti insufficenti"});
			cfg.set("error.player-notonline", new String[] {"%PREFIX% &fIl giocatore non è online"});
			cfg.set("error.player-notexist", new String[] {"%PREFIX% &fIl giocatore non esiste"});
			cfg.set("error.player-notconsole", new String[] {"%PREFIX% &fSolo un giocatore può eseguire questa azione"});
			cfg.set("error.player-notsender", new String[] {"%PREFIX% &fNon puoi inserire il tuo nome"});
			cfg.set("error.invalid-item", new String[] {"%PREFIX% &fQuesto oggetto è invalido"});
			
			//TELEPORTS
			cfg.set("teleport.request", new String[] {"","%PREFIX% &b%SENDER% &fvuole teletrasportarsi da te &7[%ACCEPT%&7] &7[%DENY%&7]",""});
			cfg.set("teleport.requesthere", new String[] {"","%PREFIX% &b%SENDER% &fvuole che ti teletrasporti da lui &7[%ACCEPT%&7] &7[%DENY%&7]",""});
			cfg.set("teleport.request-send", new String[] {"","%PREFIX% &fRichiesta inviata",""});
			cfg.set("teleport.requestaccept-you", new String[] {"","%PREFIX% &fRichiesta accettata",""});
			cfg.set("teleport.requestaccept-send", new String[] {"","%PREFIX% &b%VICTIM% &fha accettato la richiesta",""});
			cfg.set("teleport.requestdeny-you", new String[] {"","%PREFIX% &fRichiesta rifiutata",""});
			cfg.set("teleport.requestdeny-send", new String[] {"","%PREFIX% &b%VICTIM% &fha rifiutato la richiesta",""});
			cfg.set("teleport.requestcancel-you", new String[] {"","%PREFIX% &fRichiesta cancellata",""});
			cfg.set("teleport.requestcancel-send", new String[] {"","%PREFIX% &b%SENDER% &fha cancellato la richiesta",""});
			cfg.set("teleport.tp", new String[] {"","%PREFIX% &fTi sei teletrasportato da &b%VICTIM%",""});
			cfg.set("teleport.tphere", new String[] {"","%PREFIX% &fHai teletrasportato &b%VICTIM% &fda te",""});
			cfg.set("teleport.tpall", new String[] {"","%PREFIX% &fHai teletrasportato tutti i giocatori da te",""});
			cfg.set("teleport.notsecure", new String[] {"","%PREFIX% &fTeletrasporto non sicuro",""});
			cfg.set("teleport.notrequest", new String[] {"","%PREFIX% &fNon possiedi nessuna richiesta",""});
			cfg.set("teleport.alredyrequest", new String[] {"","%PREFIX% &fRichiesta già inviata",""});
			cfg.set("teleport.request-self", new String[] {"","%PREFIX% &fNon puoi inviare una richiesta a te stesso",""});
			
			//HOMES
			cfg.set("home.nohome", new String[] {"","%PREFIX% &fNon possiedi nessuna home",""});
			cfg.set("home.notexisthome", new String[] {"","%PREFIX% &fNon possiedi nessuna home con questo nome",""});
			cfg.set("home.tp", new String[] {"","%PREFIX% &fTi sei teletrasportato alla home &b%HOME%",""});
			cfg.set("home.homes", new String[] {"","%PREFIX% &fLe tue home sono: %HOMES%",""});
			cfg.set("home.homesformat", new String[] {"&7%HOME%"});
			cfg.set("home.homesseparator", new String[] {"&f, "});
			cfg.set("home.noname", new String[] {"","%PREFIX% &fInserisci il nome della home",""});
			cfg.set("home.set", new String[] {"","%PREFIX% &fHai impostato una nuova home",""});
			cfg.set("home.remove", new String[] {"","%PREFIX% &fHai eliminato una home",""});
			cfg.set("home.toohome", new String[] {"","%PREFIX% &fHai raggiunto il massimo di home impostabili",""});
			
			//ADMINHOMES
			cfg.set("adminhome.nohome", new String[] {"","%PREFIX% &b%VICTIM% &fnon possiede nessuna home",""});
			cfg.set("adminhome.notexisthome", new String[] {"","%PREFIX% &b%VICTIM% &fnon possiede nessuna home con questo nome",""});
			cfg.set("adminhome.tp", new String[] {"","%PREFIX% &fTi sei teletrasportato alla home &b%HOME% &fdi &b%VICTIM%",""});
			cfg.set("adminhome.homes", new String[] {"","%PREFIX% &fLe home di &b%VICTIM% sono: %HOMES%",""});
			cfg.set("adminhome.homesformat", new String[] {"&7%HOME%"});
			cfg.set("adminhome.homesseparator", new String[] {"&f, "});
			cfg.set("adminhome.noname", new String[] {"","%PREFIX% &fInserisci il nome della home",""});
			cfg.set("adminhome.set", new String[] {"","%PREFIX% &fHai impostato una nuova home per &b%VICTIM%",""});
			cfg.set("adminhome.remove", new String[] {"","%PREFIX% &fHai eliminato una home di &b%VICTIM%",""});
			
			//WARPS
			cfg.set("warps.nowarp", new String[] {"","%PREFIX% &fQuesto warp non esiste",""});
			cfg.set("warps.tp", new String[] {"","%PREFIX% &fTi sei teletraportato allo warp &b%WARP%",""});
			cfg.set("warps.set", new String[] {"","%PREFIX% &fHai settato un nuovo warp",""});
			cfg.set("warps.remove", new String[] {"","%PREFIX% &fHai eliminato uno warp",""});
			cfg.set("warps.warps", new String[] {"","%PREFIX% &fLista warps: %WARPS%",""});
			cfg.set("warps.warpsformat", new String[] {"&7%WARP%"});
			cfg.set("warps.warpsseparator", new String[] {"&f, "});
			
			//REPAIR
			cfg.set("repair.do", new String[] {"","%PREFIX% &fOggetto riparato",""});
			cfg.set("repair.doall", new String[] {"","%PREFIX% &fTutti gli oggetti nel tuo inventario sono stati riparati",""});
			
			//GAMEMODE
			cfg.set("gamemode.change", new String[] {"","%PREFIX% &fLa tua modalità di gioco è stata cambiata in &b%GAMEMODE%",""});
			cfg.set("gamemode.select-valid", new String[] {"","%PREFIX% &fSeleziona una modalità di gioco valida",""});
			
			//SPEED
			cfg.set("speed.change", new String[] {"","%PREFIX% &fOra ti muoverai &bx%SPEED% &fveloce",""});
			cfg.set("speed.other", new String[] {"","%PREFIX% &fOra &b%VICTIM%&f si muoverà &bx%SPEED% &fveloce",""});
			cfg.set("speed.invalid", new String[] {"","%PREFIX% &fInserisci un valore compreso fra 0 e 10",""});
			
			//FLY
			cfg.set("fly.enabled", new String[] {"","%PREFIX% &fVolo abilitato",""});
			cfg.set("fly.disabled", new String[] {"","%PREFIX% &fVolo disabilitato",""});
			
			try {cfg.save(f);} catch (IOException e) {e.printStackTrace();}
		}
		
		//GENERALI
		getMessages().put("general.prefix", cfg.getStringList("general.prefix").toArray(new String[cfg.getStringList("general.prefix").size()]));
		getMessages().put("general.accept", (String[]) cfg.getStringList("general.accept").toArray(new String[cfg.getStringList("general.accept").size()]));
		getMessages().put("general.deny", (String[]) cfg.getStringList("general.deny").toArray(new String[cfg.getStringList("general.deny").size()]));
		getMessages().put("general.survival", (String[]) cfg.getStringList("general.survival").toArray(new String[cfg.getStringList("general.survival").size()]));
		getMessages().put("general.creative", (String[]) cfg.getStringList("general.creative").toArray(new String[cfg.getStringList("general.creative").size()]));
		getMessages().put("general.adventure", (String[]) cfg.getStringList("general.adventure").toArray(new String[cfg.getStringList("general.adventure").size()]));
		getMessages().put("general.spector", (String[]) cfg.getStringList("general.spector").toArray(new String[cfg.getStringList("general.spector").size()]));
		
		prefix = ((String[]) cfg.getStringList("general.prefix").toArray(new String[cfg.getStringList("general.prefix").size()]))[0];
		
		//ERRORI
		getMessages().put("error.nopermission", (String[]) cfg.getStringList("error.nopermission").toArray(new String[cfg.getStringList("error.nopermission").size()]));
		getMessages().put("error.noargs", (String[]) cfg.getStringList("error.noargs").toArray(new String[cfg.getStringList("error.noargs").size()]));
		getMessages().put("error.player-notonline", (String[]) cfg.getStringList("error.player-notonline").toArray(new String[cfg.getStringList("error.player-notonline").size()]));
		getMessages().put("error.player-notexist", (String[]) cfg.getStringList("error.player-notexist").toArray(new String[cfg.getStringList("error.player-notexist").size()]));
		getMessages().put("error.player-notconsole", (String[]) cfg.getStringList("error.player-notconsole").toArray(new String[cfg.getStringList("error.player-notconsole").size()]));
		getMessages().put("error.player-notsender", (String[]) cfg.getStringList("error.player-notsender").toArray(new String[cfg.getStringList("error.player-notsender").size()]));
		getMessages().put("error.invalid-item", (String[]) cfg.getStringList("error.invalid-item").toArray(new String[cfg.getStringList("error.invalid-item").size()]));
		
		//TELEPORTS
		getMessages().put("teleport.request", (String[]) cfg.getStringList("teleport.request").toArray(new String[cfg.getStringList("teleport.request").size()]));
		getMessages().put("teleport.requesthere", (String[]) cfg.getStringList("teleport.requesthere").toArray(new String[cfg.getStringList("teleport.requesthere").size()]));
		getMessages().put("teleport.request-send", (String[]) cfg.getStringList("teleport.request-send").toArray(new String[cfg.getStringList("teleport.request-send").size()]));
		getMessages().put("teleport.requestaccept-you", (String[]) cfg.getStringList("teleport.requestaccept-you").toArray(new String[cfg.getStringList("teleport.requestaccept-you").size()]));
		getMessages().put("teleport.requestaccept-send", (String[]) cfg.getStringList("teleport.requestaccept-send").toArray(new String[cfg.getStringList("teleport.requestaccept-send").size()]));
		getMessages().put("teleport.requestdeny-you", (String[]) cfg.getStringList("teleport.requestdeny-you").toArray(new String[cfg.getStringList("teleport.requestdeny-send").size()]));
		getMessages().put("teleport.requestdeny-send", (String[]) cfg.getStringList("teleport.requestdeny-send").toArray(new String[cfg.getStringList("teleport.requestcancel-you").size()]));
		getMessages().put("teleport.requestcancel-you", (String[]) cfg.getStringList("teleport.requestcancel-you").toArray(new String[cfg.getStringList("teleport.requestcancel-send").size()]));
		getMessages().put("teleport.requestcancel-send", (String[]) cfg.getStringList("teleport.requestcancel-send").toArray(new String[cfg.getStringList("teleport.requestcancel-send").size()]));
		getMessages().put("teleport.tp", (String[]) cfg.getStringList("teleport.tp").toArray(new String[cfg.getStringList("teleport.tp").size()]));
		getMessages().put("teleport.tphere", (String[]) cfg.getStringList("teleport.tphere").toArray(new String[cfg.getStringList("teleport.tphere").size()]));
		getMessages().put("teleport.tpall", (String[]) cfg.getStringList("teleport.tpall").toArray(new String[cfg.getStringList("teleport.tpall").size()]));
		getMessages().put("teleport.notsecure", (String[]) cfg.getStringList("teleport.notsecure").toArray(new String[cfg.getStringList("teleport.notsecure").size()]));
		getMessages().put("teleport.notrequest", (String[]) cfg.getStringList("teleport.notrequest").toArray(new String[cfg.getStringList("teleport.notrequest").size()]));
		getMessages().put("teleport.alredyrequest", (String[]) cfg.getStringList("teleport.alredyrequest").toArray(new String[cfg.getStringList("teleport.alredyrequest").size()]));
		getMessages().put("teleport.request-self", (String[]) cfg.getStringList("teleport.request-self").toArray(new String[cfg.getStringList("teleport.request-self").size()]));
	
		//HOMES
		getMessages().put("home.nohome", (String[]) cfg.getStringList("home.nohome").toArray(new String[cfg.getStringList("home.nohome").size()]));
		getMessages().put("home.notexisthome", (String[]) cfg.getStringList("home.notexisthome").toArray(new String[cfg.getStringList("home.notexisthome").size()]));
		getMessages().put("home.tp", (String[]) cfg.getStringList("home.tp").toArray(new String[cfg.getStringList("home.tp").size()]));
		getMessages().put("home.homes", (String[]) cfg.getStringList("home.homes").toArray(new String[cfg.getStringList("home.homes").size()]));
		getMessages().put("home.homesformat", (String[]) cfg.getStringList("home.homesformat").toArray(new String[cfg.getStringList("home.homesformat").size()]));
		getMessages().put("home.homesseparator", (String[]) cfg.getStringList("home.homesseparator").toArray(new String[cfg.getStringList("home.homesseparator").size()]));
		getMessages().put("home.noname", (String[]) cfg.getStringList("home.noname").toArray(new String[cfg.getStringList("home.noname").size()]));
		getMessages().put("home.set", (String[]) cfg.getStringList("home.set").toArray(new String[cfg.getStringList("home.set").size()]));
		getMessages().put("home.remove", (String[]) cfg.getStringList("home.remove").toArray(new String[cfg.getStringList("home.remove").size()]));
		getMessages().put("home.toohome", (String[]) cfg.getStringList("home.toohome").toArray(new String[cfg.getStringList("home.toohome").size()]));

		//ADMINHOMES
		getMessages().put("adminhome.nohome", (String[]) cfg.getStringList("adminhome.nohome").toArray(new String[cfg.getStringList("adminhome.nohome").size()]));
		getMessages().put("adminhome.notexisthome", (String[]) cfg.getStringList("adminhome.notexisthome").toArray(new String[cfg.getStringList("adminhome.notexisthome").size()]));
		getMessages().put("adminhome.tp", (String[]) cfg.getStringList("adminhome.tp").toArray(new String[cfg.getStringList("adminhome.tp").size()]));
		getMessages().put("adminhome.homes", (String[]) cfg.getStringList("adminhome.homes").toArray(new String[cfg.getStringList("adminhome.homes").size()]));
		getMessages().put("adminhome.homesformat", (String[]) cfg.getStringList("adminhome.homesformat").toArray(new String[cfg.getStringList("adminhome.homesformat").size()]));
		getMessages().put("adminhome.homesseparator", (String[]) cfg.getStringList("adminhome.homesseparator").toArray(new String[cfg.getStringList("adminhome.homesseparator").size()]));
		getMessages().put("adminhome.noname", (String[]) cfg.getStringList("adminhome.noname").toArray(new String[cfg.getStringList("adminhome.noname").size()]));
		getMessages().put("adminhome.set", (String[]) cfg.getStringList("adminhome.set").toArray(new String[cfg.getStringList("adminhome.set").size()]));
		getMessages().put("adminhome.remove", (String[]) cfg.getStringList("adminhome.remove").toArray(new String[cfg.getStringList("adminhome.remove").size()]));
		
		//WARPS
		getMessages().put("warps.nowarp", (String[]) cfg.getStringList("warps.nowarp").toArray(new String[cfg.getStringList("warps.nowarp").size()]));
		getMessages().put("warps.tp", (String[]) cfg.getStringList("warps.tp").toArray(new String[cfg.getStringList("warps.tp").size()]));
		getMessages().put("warps.set", (String[]) cfg.getStringList("warps.set").toArray(new String[cfg.getStringList("warps.set").size()]));
		getMessages().put("warps.remove", (String[]) cfg.getStringList("warps.remove").toArray(new String[cfg.getStringList("warps.remove").size()]));
		getMessages().put("warps.warpsformat", (String[]) cfg.getStringList("warps.warpsformat").toArray(new String[cfg.getStringList("warps.warpsformat").size()]));
		getMessages().put("warps.warpsseparator", (String[]) cfg.getStringList("warps.warpsseparator").toArray(new String[cfg.getStringList("warps.warpsseparator").size()]));
		getMessages().put("warps.warps", (String[]) cfg.getStringList("warps.warps").toArray(new String[cfg.getStringList("warps.warps").size()]));
	
		//REPAIR
		getMessages().put("repair.do", (String[]) cfg.getStringList("repair.do").toArray(new String[cfg.getStringList("repair.do").size()]));
		getMessages().put("repair.doall", (String[]) cfg.getStringList("repair.doall").toArray(new String[cfg.getStringList("repair.doall").size()]));

		//GAMEMODE
		getMessages().put("gamemode.change", (String[]) cfg.getStringList("gamemode.change").toArray(new String[cfg.getStringList("gamemode.change").size()]));
		getMessages().put("gamemode.select-valid", (String[]) cfg.getStringList("gamemode.select-valid").toArray(new String[cfg.getStringList("gamemode.select-valid").size()]));

		//SPEED
		getMessages().put("speed.change", (String[]) cfg.getStringList("speed.change").toArray(new String[cfg.getStringList("speed.change").size()]));
		getMessages().put("speed.other", (String[]) cfg.getStringList("speed.other").toArray(new String[cfg.getStringList("speed.other").size()]));
		getMessages().put("speed.invalid", (String[]) cfg.getStringList("speed.invalid").toArray(new String[cfg.getStringList("speed.invalid").size()]));

		//FLY
		getMessages().put("fly.enabled", (String[]) cfg.getStringList("fly.enabled").toArray(new String[cfg.getStringList("fly.enabled").size()]));
		getMessages().put("fly.disabled", (String[]) cfg.getStringList("fly.disabled").toArray(new String[cfg.getStringList("fly.disabled").size()]));

	}

}
