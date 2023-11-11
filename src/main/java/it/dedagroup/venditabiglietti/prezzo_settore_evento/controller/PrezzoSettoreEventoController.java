package it.dedagroup.venditabiglietti.prezzo_settore_evento.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import it.dedagroup.venditabiglietti.prezzo_settore_evento.dto.request.PrezzoSettoreEventoDtoRequest;
import it.dedagroup.venditabiglietti.prezzo_settore_evento.mapper.PrezzoSettoreEventoMapper;
import it.dedagroup.venditabiglietti.prezzo_settore_evento.model.PrezzoSettoreEvento;
import it.dedagroup.venditabiglietti.prezzo_settore_evento.servicedef.PrezzoSettoreEventoService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;

/**
 * Controller che gestisce le operazioni relative ai prezzi settore evento.
 */
@RestController
@Validated
public class PrezzoSettoreEventoController {
	
	@Autowired
	private PrezzoSettoreEventoService pseService;
	@Autowired
	private PrezzoSettoreEventoMapper pseMapper;
	
	/**
	 * Aggiunge un nuovo prezzo settore evento.
	 * 
	 * @param request La richiesta per aggiungere un prezzo settore evento.
	 * @return ResponseEntity con lo stato della richiesta.
	 */
	@PostMapping("/prezzi-settore-evento/add")
	public ResponseEntity<Void> aggiungiPrezzoSettoreEvento(@Valid @RequestBody PrezzoSettoreEventoDtoRequest request){
		pseService.aggiungiPrezzoSettoreEvento(pseMapper.toPrezzoSettoreEvento(request));
		return ResponseEntity.status(HttpStatus.CREATED).build();
	}
	
	/**
	 * Ottiene la lista dei prezzi settore evento per un determinato settore.
	 * 
	 * @param idSettore L'id del settore.
	 * @return ResponseEntity con la lista dei prezzi settore evento.
	 */
	@GetMapping("/prezzi-settore-evento/lista-by-settore")
	public ResponseEntity<List<PrezzoSettoreEvento>> getListaPrezzoSettoreEventoByIdSettore(@RequestParam @Min(value = 1, message = "L'id del settore non è valido") long idSettore){
		return ResponseEntity.status(HttpStatus.OK).body(pseService.findAllByIdSettore(idSettore));
	}
	
	/**
	 * Ottiene la lista dei prezzi settore evento per un determinato evento.
	 * 
	 * @param idEvento L'id dell'evento.
	 * @return ResponseEntity con la lista dei prezzi settore evento.
	 */
	@GetMapping("/prezzi-settore-evento/lista-by-evento")
	public ResponseEntity<List<PrezzoSettoreEvento>> getListaPrezzoSettoreEventoByIdEvento(@RequestParam @Min(value = 1, message = "L'id dell'evento non è valido") long idEvento){
		return ResponseEntity.status(HttpStatus.OK).body(pseService.findAllByIdEvento(idEvento));
	}
	
	/**
	 * Modifica l'id del settore di un prezzo settore evento.
	 * 
	 * @param idPse L'id del PrezzoSettoreEvento.
	 * @param idSettore Il nuovo id del settore.
	 * @return ResponseEntity con lo stato della richiesta.
	 */
	@PostMapping("/prezzi-settore-evento/modifica-id-settore")
	public ResponseEntity<Void> modificaIdSettore(
			@RequestParam("idPse") @Min(value = 1, message = "L'id del PrezzoSettoreEvento non è valido") long idPse,
			@RequestParam("idSettore") @Min(value = 1, message = "L'id del settore non è valido") long idSettore){
		PrezzoSettoreEvento pse = pseService.findById(idPse);
		pse.setIdSettore(idSettore);
		pseService.modificaPrezzoSettoreEvento(pse);
		return ResponseEntity.status(HttpStatus.OK).build();
	}
	
	/**
	 * Modifica l'id dell'evento di un prezzo settore evento.
	 * 
	 * @param idPse L'id del PrezzoSettoreEvento.
	 * @param idEvento Il nuovo id dell'evento.
	 * @return ResponseEntity con lo stato della richiesta.
	 */
	@PostMapping("/prezzi-settore-evento/modifica-id-evento")
	public ResponseEntity<Void> modificaIdEvento(
			@RequestParam("idPse") @Min(value = 1, message = "L'id del PrezzoSettoreEvento non è valido") long idPse,
			@RequestParam("idEvento") @Min(value = 1, message = "L'id dell'evento non è valido") long idEvento){
		PrezzoSettoreEvento pse = pseService.findById(idPse);
		pse.setIdEvento(idEvento);
		pseService.modificaPrezzoSettoreEvento(pse);
		return ResponseEntity.status(HttpStatus.OK).build();
	}
	
	/**
	 * Modifica il prezzo di un prezzo settore evento per un determinato settore ed evento.
	 * 
	 * @param request La richiesta con i nuovi dati.
	 * @return ResponseEntity con lo stato della richiesta.
	 */
	@PostMapping("/prezzi-settore-evento/modifica-prezzo")
	public ResponseEntity<Void> modificaPrezzoByIdSettoreAndIdEvento(@RequestBody PrezzoSettoreEventoDtoRequest request){
		pseService.modificaPrezzoByIdSettoreAndIdEvento(request.getPrezzo(), request.getIdSettore(), request.getIdEvento());
		return ResponseEntity.status(HttpStatus.OK).build();
	}
	
	/**
	 * Ottiene la lista dei prezzi settore evento per un determinato evento e settore.
	 * 
	 * @param idEvento L'id dell'evento.
	 * @param idSettore L'id del settore.
	 * @return ResponseEntity con la lista dei prezzi settore evento.
	 */
	@GetMapping("/prezzi-settore-evento/lista-by-evento-settore")
	public ResponseEntity<List<PrezzoSettoreEvento>> getListaPrezzoSettoreEventoByIdEventoAndIdSettore(
			@RequestParam @Min(value = 1, message = "L'id dell'evento non è valido") long idEvento,
			@RequestParam @Min(value = 1, message = "L'id del settore non è valido") long idSettore){
		return ResponseEntity.status(HttpStatus.OK).body(pseService.findAllByIdEventoAndIdSettore(idEvento, idSettore));
	}
	
	/**
	 * Ottiene la lista dei prezzi settore evento per un determinato evento e settore che sono disponibili.
	 * 
	 * @param idEvento L'id dell'evento.
	 * @param idSettore L'id del settore.
	 * @return ResponseEntity con la lista dei prezzi settore evento disponibili.
	 */
	@GetMapping("/prezzi-settore-evento/lista-by-evento-settore-is-available")
	public ResponseEntity<List<PrezzoSettoreEvento>> getListaPrezzoSettoreEventoByIdEventoAndIdSettoreAndIsAvailableTrue(
			@RequestParam @Min(value = 1, message = "L'id dell'evento non è valido") long idEvento,
			@RequestParam @Min(value = 1, message = "L'id del settore non è valido") long idSettore){
		return ResponseEntity.status(HttpStatus.OK).body(pseService.findAllByIdEventoAndIdSettoreAndIsAvailableTrue(idEvento, idSettore));
	}
	
	/**
	 * Ottiene la lista dei prezzi settore evento per un determinato evento che sono disponibili.
	 * 
	 * @param idEvento L'id dell'evento.
	 * @return ResponseEntity con la lista dei prezzi settore evento disponibili.
	 */
	@GetMapping("/prezzi-settore-evento/lista-by-evento-is-available")
	public ResponseEntity<List<PrezzoSettoreEvento>> getListaPrezzoSettoreEventoByIdEventoAndIsAvailableTrue(
			@RequestParam @Min(value = 1, message = "L'id dell'evento non è valido") long idEvento){
		return ResponseEntity.status(HttpStatus.OK).body(pseService.findAllByIdEventoAndIsAvailableTrue(idEvento));
	}
	
	/**
	 * Ottiene la lista dei prezzi settore evento per un determinato settore che sono disponibili.
	 * 
	 * @param idSettore L'id del settore.
	 * @return ResponseEntity con la lista dei prezzi settore evento disponibili.
	 */
	@GetMapping("/prezzi-settore-evento/lista-by-settore-is-available")
	public ResponseEntity<List<PrezzoSettoreEvento>> getListaPrezzoSettoreEventoByIdSettoreAndIsAvailableTrue(
			@RequestParam @Min(value = 1, message = "L'id del settore non è valido") long idSettore){
		return ResponseEntity.status(HttpStatus.OK).body(pseService.findAllByIdSettoreAndIsAvailableTrue(idSettore));
	}
	
	/**
	 * Elimina un prezzo settore evento per id.
	 * 
	 * @param idPse L'id del PrezzoSettoreEvento.
	 * @return ResponseEntity con lo stato della richiesta.
	 */
	@PostMapping("/prezzi-settore-evento/elimina-by-id")
	public ResponseEntity<Void> eliminaPrezzoSettoreEventoById(
			@RequestParam("idPse") @Min(value = 1, message = "L'id del PrezzoSettoreEvento non è valido") long idPse){
		pseService.eliminaPrezzoSettoreEvento(pseService.findById(idPse));
		return ResponseEntity.status(HttpStatus.OK).build();
	}
	
	/**
	 * Elimina i prezzi settore evento per un determinato settore.
	 * 
	 * @param idSettore L'id del settore.
	 * @return ResponseEntity con lo stato della richiesta.
	 */
	@PostMapping("/prezzi-settore-evento/elimina-by-settore")
	public ResponseEntity<Void> eliminaPrezzoSettoreEventoByIdSettore(
			@RequestParam("idSettore") @Min(value = 1, message = "L'id del settore non è valido") long idSettore){
		pseService.eliminaByIdSettore(idSettore);
		return ResponseEntity.status(HttpStatus.OK).build();
	}
	
	/**
	 * Elimina i prezzi settore evento per un determinato evento.
	 * 
	 * @param idEvento L'id dell'evento.
	 * @return ResponseEntity con lo stato della richiesta.
	 */
	@PostMapping("/prezzi-settore-evento/elimina-by-evento")
	public ResponseEntity<Void> eliminaPrezzoSettoreEventoByIdEvento(
			@RequestParam @Min(value = 1, message = "L'id del PrezzoSettoreEvento non è valido") long idEvento){
		pseService.eliminaByIdEvento(idEvento);
		return ResponseEntity.status(HttpStatus.OK).build();
	}
	
	/**
	 * Elimina i prezzi settore evento per un determinato settore ed evento.
	 * 
	 * @param idEvento L'id dell'evento.
	 * @param idSettore L'id del settore.
	 * @return ResponseEntity con lo stato della richiesta.
	 */
	@PostMapping("/prezzi-settore-evento/elimina-by-settore-evento")
	public ResponseEntity<Void> eliminaByIdSettoreAndIdEvento(
			@RequestParam @Min(value = 1, message = "L'id dell'evento non è valido") long idEvento,
			@RequestParam @Min(value = 1, message = "L'id del settore non è valido") long idSettore){
		pseService.eliminaByIdSettoreAndIdEvento(idSettore, idEvento);
		return ResponseEntity.status(HttpStatus.OK).build();
	}
}