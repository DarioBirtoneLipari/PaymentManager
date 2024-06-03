
/**
 * Gestione presenze e paghe
 * - employee viene pagato in base alle presenze nel mese
 * - ogni employee appartiene ad una fascia di seniority
 * - il costo orario del employee varia in base alla seniority
 * - costo straordinario: feriale -> +20%, sabato -> +33%, domenica -> +50%
 * - dobbiamo sviluppare il software di gestione paghe per ogni employee
 * 
 * es Maggio 2024
 * 1. Mario (senior: 25€/h): 20gg lavorati, 2gg malattia
 * 2. Luca (middle: 20€/h) : 22gg + 2gg sabato (sabato+33%, domenica +50%)
 * 3. Marco (junior: 15€/h) : 17gg, 5gg ferie
 * 
 * note:
 * 1gg == 8h
 * si può lavorare anche meno di 8h/gg, le restanti saranno o ferie/permesso o malattia
 * orario di ingresso: 9.00
 * orario di uscita: 18.00
 * pausa pranzo obbligatoria: 13.00-14.00
 * 
 * badge per segnare ingressi e uscite
 * in regime ORDINARIO (straordinario non abilitato -> lun-ven 9-18)
 * - se timbro l'ingresso prima delle 9.00, l'orario di ingresso considerato sarà quello delle 9
 * - se timbro l'uscita dopo le 18.00, l'orario di uscita considerato sarà quello delle 18
 * un employee può caricare hh/gg di straordinario se e solo se il suo manager ha acconsentito
 * in regime STRAORDINARIO
 * - se timbro l'ingresso prima delle 9.00, l'orario di ingresso considerato sarà quello del timbro arrotondato alla mezz'ora per difetto
 * - se timbro l'uscita dopo le 18.00, l'orario di uscita considerato sarà quello quello del timbro arrotondato alla mezz'ora per difetto
 *
 * il software riceverà 2 file di input:
 * file dipendenti (nome, cognome, codice fiscale, seniority[junior/middle/senior], data assunzione, data termine, data inizio straordinario, data fine straordiario)
 * file timbrature (codice fiscale employee, tipo timbratura[in/out], data e ora)
 * 
 * 
 *
 * il software eseguirà i calcoli per ogni employee e darà in output un file csv per ogni employee con un riassunto del mese
 * giorni lavorati/non-lavorati espresso in ore (2/5 -> 8h, 3/5 -> ferie, 6/5 -> 2h + 6h malattia, ...)
 * eventuali ore di straordinario, separato per feriale, sabato e domenica
 * subtotale totale delle singole voci [baga base, straordinario feriale, straordiario sabato, straoridinario domenica]
 * totale da pagare
 *
 * 
 */

