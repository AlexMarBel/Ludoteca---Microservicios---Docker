package com.ccsw.tutorialloan.loan;

import com.ccsw.tutorialloan.common.criteria.SearchCriteria;
import com.ccsw.tutorialloan.loan.model.Loan;
import com.ccsw.tutorialloan.loan.model.LoanDto;
import com.ccsw.tutorialloan.loan.model.LoanSearchDto;
import jakarta.transaction.Transactional;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
import java.util.List;

@Service
@Transactional
public class LoanServiceImpl implements LoanService {

    @Autowired
    LoanRepository loanRepository;

    @Override
    public List<Loan> findAll() {
        return (List<Loan>) this.loanRepository.findAll();
    }

    @Override
    public void save(Long id, LoanDto dto) {

        if (isGameOverlapping(dto)) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Game is already loaned in these dates");
        } else if (isClientOverlapping(dto)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Client already has 2 loans in these dates");
        } else {
            Loan loan;

            loan = new Loan();
            BeanUtils.copyProperties(dto, loan, "id", "game", "client");

            loan.setIdGame(dto.getGame().getId());
            loan.setIdClient(dto.getClient().getId());

            this.loanRepository.save(loan);
        }
    }

    @Override
    public Page<Loan> findPage(Long gameId, Long clientId, LocalDate date, LoanSearchDto dto) {
        LoanSpecification idGameSpec = new LoanSpecification(new SearchCriteria("idGame", ":", gameId));
        LoanSpecification idClientSpec = new LoanSpecification(new SearchCriteria("idClient", ":", clientId));
        LoanSpecification dateEndSpec = new LoanSpecification(new SearchCriteria("endDate", "lessThanOrEqualTo", date));
        LoanSpecification dateStartSpec = new LoanSpecification(new SearchCriteria("startDate", "greaterThanOrEqualTo", date));

        Specification<Loan> spec = Specification.where(idGameSpec).and(idClientSpec).and(dateEndSpec).and(dateStartSpec);

        return this.loanRepository.findAll(spec, dto.getPageable().getPageable());
    }

    @Override
    public void delete(Long id) throws Exception {
        if (this.loanRepository.findById(id).orElse(null) == null) {
            throw new Exception("Not exists");
        }

        this.loanRepository.deleteById(id);
    }

    @Override
    public boolean isGameOverlapping(LoanDto dto) {
        Long gameId = dto.getGame().getId();
        LocalDate startDate = dto.getStartDate();
        LocalDate endDate = dto.getEndDate();

        List<Loan> loans = this.findAll();

        for (Loan loan : loans) {
            if (loan.getIdGame() == gameId) {
                LocalDate endDateLoan = loan.getEndDate();
                LocalDate startDateLoan = loan.getStartDate();

                boolean isOverLapping = (startDate.isBefore(endDateLoan) && endDate.isAfter(startDateLoan)) || (startDateLoan.isBefore(endDate) && endDateLoan.isAfter(startDate));

                if (isOverLapping) {
                    return true;
                }
            }
        }

        return false;
    }

    @Override
    public boolean isClientOverlapping(LoanDto dto) {
        Long clientId = dto.getClient().getId();
        LocalDate startDate = dto.getStartDate();
        LocalDate endDate = dto.getEndDate();

        List<Loan> loans = this.findAll();

        for (LocalDate date = startDate; !date.isAfter(endDate); date = date.plusDays(1)) {
            int loanCount = 0;
            for (Loan loan : loans) {
                if (loan.getIdClient().equals(clientId) && !loan.getEndDate().isBefore(date) && !loan.getStartDate().isAfter(date)) {
                    loanCount++;
                }
            }
            if (loanCount >= 2) {
                return true;
            }
        }
        return false;
    }
}
