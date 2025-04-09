package com.ccsw.tutorialloan.loan;

import com.ccsw.tutorialloan.author.model.AuthorDto;
import com.ccsw.tutorialloan.client.ClientClient;
import com.ccsw.tutorialloan.client.model.ClientDto;
import com.ccsw.tutorialloan.game.GameClient;
import com.ccsw.tutorialloan.game.model.GameDto;
import com.ccsw.tutorialloan.loan.model.Loan;
import com.ccsw.tutorialloan.loan.model.LoanDto;
import com.ccsw.tutorialloan.loan.model.LoanSearchDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Tag(name = "Loan", description = "API of Loan")
@RequestMapping(value = "/loan")
@RestController
@CrossOrigin(origins = "*")
public class LoanController {

    @Autowired
    LoanService loanService;

    @Autowired
    ClientClient clientClient;
    @Autowired
    GameClient gameClient;

    @Autowired
    ModelMapper mapper;

    /**
     * Método para recuperar un listado paginada y filtrada de {@link Loan}
     *
     * @param dto dto de busqueda
     * @return {@link Page} de {@link AuthorDto}
     */
    @Operation(summary = "Find page", description = "Method that return a page of Loans")
    @RequestMapping(path = "", method = RequestMethod.POST)
    public Page<LoanDto> findPage(@RequestBody LoanSearchDto dto, @RequestParam(value = "gameId", required = false) Long idGame, @RequestParam(value = "clientId", required = false) Long idClient,
            @RequestParam(value = "date", required = false) LocalDate date) {

        Page<Loan> page = this.loanService.findPage(idGame, idClient, date, dto);
        List<GameDto> games = gameClient.findAll();
        List<ClientDto> clients = clientClient.findAll();

        return new PageImpl<>(page.getContent().stream().map(loan -> {
            LoanDto loanDto = new LoanDto();

            loanDto.setId(loan.getId());
            loanDto.setStartDate(loan.getStartDate());
            loanDto.setEndDate(loan.getEndDate());
            loanDto.setClient(clients.stream().filter(client -> client.getId().equals(loan.getIdClient())).findFirst().orElse(null));
            loanDto.setGame(games.stream().filter(game -> game.getId().equals(loan.getIdGame())).findFirst().orElse(null));

            return loanDto;

        }).collect(Collectors.toList()), page.getPageable(), page.getTotalElements());

    }

    /**
     * Método para recuperar una lista de {@link Loan}
     *
     * @param idGame PK del juego
     * @param idClient PK del cliente
     * @param date
     * @return

     @Operation(summary = "Find", description = "Method that return a list of Loans")
     @RequestMapping(path = "", method = RequestMethod.GET)
     public List<LoanDto> find(@RequestParam(value = "gameId", required = false) Long idGame, @RequestParam(value = "clientId", required = false) Long idClient, @RequestParam(value = "date", required = false) LocalDate date) {
     List<Loan> loans = loanService.findAll()

     return loans.stream().map(e -> mapper.map(e, LoanDto.class)).collect(Collectors.toList());
     }
     */

    /**
     * Método para crean un {@link Loan}
     *
     * @param id PK de la entidad
     * @param dto datos de la entidad
     */
    @Operation(summary = "Save", description = "Method that saves a Loan")
    @RequestMapping(path = { "", "/{id}" }, method = RequestMethod.PUT)
    public void save(@PathVariable(name = "id", required = false) Long id, @RequestBody LoanDto dto) {
        loanService.save(id, dto);
    }

    /**
     * Método para eliminar un {@link Loan}
     *
     * @param id PK de la entidad
     */
    @Operation(summary = "Delete", description = "Method that deletes a Loan")
    @RequestMapping(path = "/{id}", method = RequestMethod.DELETE)
    public void delete(@PathVariable("id") Long id) throws Exception {

        this.loanService.delete(id);
    }
}
