package com.ccsw.tutorialloan.loan.model;

import com.ccsw.tutorialloan.client.model.ClientDto;
import com.ccsw.tutorialloan.game.model.GameDto;

import java.time.LocalDate;

public class LoanDto {
    private Long id;
    private GameDto game;
    private ClientDto client;
    private LocalDate startDate;
    private LocalDate endDate;

    /**
     *
     * @return id
     */
    public Long getId() {
        return id;
    }

    /**
     *
     * @param id new value of {@link #getId}
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     *
     * @return game
     */
    public GameDto getGame() {
        return game;
    }

    /**
     *
     * @param game new value of {@link #getGame}
     */
    public void setGame(GameDto game) {
        this.game = game;
    }

    /**
     *
     * @return client
     */
    public ClientDto getClient() {
        return client;
    }

    /**
     *
     * @param client new valie of {@link #getClient}
     */
    public void setClient(ClientDto client) {
        this.client = client;
    }

    /**
     *
     * @return startDate
     */
    public LocalDate getStartDate() {
        return startDate;
    }

    /**
     *
     * @param startDate new value of {@link #getStartDate}
     */
    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    /**
     *
     * @return endDate
     */
    public LocalDate getEndDate() {
        return endDate;
    }

    /**
     *
     * @param endDate new value of {@link #getEndDate}
     */
    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }
}
