# Kay-Es Core

_Questo plugin offre un innumerevole quantità di estensioni applicabili al proprio server.
Ottimizzato per sostenere centinaia di giocatori contemporaneamente, questo plugin è costantemente
aggiornato per fornire un'esperienza il più originale possibile a qualsiasi utente del proprio Network._

## Fai una richiesta!
_Proponi, richiedi e segnala ciò che non funziona nella sezione "Issue".
Ogni messaggio verrà accolto con piacere!_

## Installazione e Configurazione
- Scarica **Kay-Es-Core.jar**
- Installa Vault nella cartella _plugins/_
- Inserisci **Kay-Es-Core.jar** in _plugins/_
- Avvia il server

### Attenzione
- _Kay-Es-Core gestisce gli inventari di tutti i giocatori in modo differente da quello di Minecraft standard. Questo potrebbe
causare conflitti con alcuni plugin che modificano l'inventario ad un giocatore offline._
- Non eseguire _/reload_ e non riavviare Kay-Es-Core con reloader custom. Se necessario, kicka prima tutti i giocatori online e riavvia Kay-Es-Core.

## Comandi e Permessi
###### Comando, Descrizione, (Permesso), [Alias]
- **/tpa <giocatore>** _Invia una richiesta di teletrasporto ad un giocatore_ (tpa) 
- **/tpahere <giocatore>** _Invia una richiesta di teletrasporto ad un giocatore nella tua posizione_ (tpahere)
- **/tpaccept** _Accetta una richiesta di teletrasporto_ (tpaccept) [tpyes]
- **/tpdeny** _Rifiuta una richiesta di teletrasporto_ (tpdeny) [tpno]
- **/tpcancel** _Cancella una richiesta di teletrasporto_ (tpcancel)
- **/tp <giocatore>** _Teletrasportati da un giocatore_ (tp)
- **/tphere <giocatore>** _Teletrasporta un giocatore da te_ (tphere)
- **/tpall** _Teletrasporta tutti i giocatori da te_ (tpall)
- **/home <nome>** _Teletrasportati alla tua home_ (home, homes.numero)
- **/homes** _Visualizza le tue homes_ (homes)
- **/sethome <nome>** _Imposta una nuova home_ (sethome)
- **/delhome <nome>** _Rimuovi una home_ (delhome)
- **/warp <nome>** _Teletrasportati ad uno warp_ (warps.name, warps.*)
- **/warps** _Visualizza la lista degli warps_
- **/setwarp <nome>** _Imposta un nuovo warp_ (setwarp)
- **/delwarp <nome>** _Rimuovi uno warp_ (delwarp)
- **/repair** _Ripara un oggetto_ (repair) [fix]
- **/repairall** _Ripara tutti gli oggetti_ (repairall) [fixall]
- **/invsee <giocatore>** _Apri l'inventario di un giocatore online/offline_ (invsee)
- **/enderchest** _Apri l'enderchest_ (enderchest) [ec]
- **/clear <giocatore>** _Svuota l'inventario di un giocatore_ (clear)
- **/gamemode <modalità> <giocatore>** _Modifica la modalità di gioco di un giocatore_ (gamemode) [gm]
- **/gms <giocatore>** _Modifica la modalità di gioco di un giocatore in Sopravvivenza_ (gamemode)
- **/gmc <giocatore>** _Modifica la modalità di gioco di un giocatore in Creativa_ (gamemode)
- **/gmsp <giocatore>** _Modifica la modalità di gioco di un giocatore in Spettatore_ (gamemode)
- **/gma <giocatore>** _Modifica la modalità di gioco di un giocatore in Avventura_ (gamemode)  
- **/speed <0-10> <giocatore>** _Modifica la velocità di movimento di un giocatore_ (speed)
- **/fly** _Abilita/Disabilita la modalità di volo_ (fly)
- **/god** _Abilita/Disabilita la modalità di immortalità_ (god)
- **/heal <giocatore>** _Ricarica la barra della vita di un giocatore_ (heal)
- **/feed <giocatore>** _Ricarica la barra della fame di un giocatore_ (feed)
- **/jump** _Teletrasportati nella direzione che stai guardando_ (jump)
- **/top** _Teletrasportati nella posizione piu' alta alle tue coordinate_ (top)
- **/kill <giocatore>** _Uccidi un giocatore_ (kill)
- **/suicide** _Suicidati_ (suicide)
- **/trash** _Apri il cestino_ (trash) [disposal, bin, cestino]
- **/skull <giocatore>** _Ottieni la testa di un giocatore_ (skull)
- **/hat** _Indossa nella testa ciò che hai in mano_ (hat)
- **/seen <giocatore>** _Controlla l'ultimo accesso di un giocatore_ (seen)
- **/near** _Visualizza i giocatori vicino a te_ (near)
- **/broadcast <messaggio>** _Invia un messaggio a tutto il server_ (broadcast)
- **/money** _Controlla il tuo bilancio monetario_ (money) [bal]
- **/pay <giocatore> <quantità>** _Invia dei soldi ad un giocatore_ (pay)
- **/baltop** _Visualizza la classifica monetaria di tutti i giocatori del server_ (baltop)
- **/back** _Torna alla posizione precedente_ (back)
- **/workbench** _Apri la crafting table_ (workbench) [craft, wb]
- **/anvil** _Apri l'incudine_ (anvil)
- **/smithingtable** _Apri il tavolo del fabbro_ (smithingtable) [smtable]
- **/grindstone** _Apri la mola_ (grindstone) [gstone]
- **/enchantingtable** _Apri il tavolo degli incantesimi_ (enchantingtable) [enchanttable]
#
- **/eco <give,take,set,reset> <giocatore> <quantità>** _Modifica il bilancio di un giocatore_ (eco, admin)
- **/adminhome <giocatore> <home>** _Teletrasportati alla home di un giocatore_ (adminhome, admin) [ahome]
- **/adminhomes <giocatore>** _Visualizza le homes di un giocatore_ (adminhomes, admin) [ahomes]
- **/adminsethome <giocatore> <home>** _Imposta una nuova home per un giocatore_ (adminsethome, admin) [asethome]
- **/admindelhome <giocatore> <home>** _Rimuovi una home di un giocatore_ (admindelhome, admin) [adelhome]
#
#### In caso si volesse limitare un giocatore un gruppo di eseguire uno o più comandi soprastanti, basterà aggiungere il permesso _permesso.**limit**_
  
  
  
  
