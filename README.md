## Ambiente di sviluppo e test
- **Dispositivo utilizzato:** Emulatore con Google Pixel 2
- **Versione Android:** 16
- **Api:** 36.0

### Info aggiuntive
Per la gestione della sequenza di lettere nella prima schermata è stato scelto di utilizzare un'area di testo a singola riga e non multiriga.
Questo consente di applicare il metodo "StartEllipsis". Tale metodo gestisce il troncamento della sequenza di tasti premuti, mostrando solamente gli ultimi elementi e troncando i primi. Non è però applicabile a testi multiriga. 