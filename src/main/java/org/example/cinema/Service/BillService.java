package org.example.cinema.Service;

import org.example.cinema.Model.Bill;
import org.example.cinema.Repository.BillRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BillService {
    @Autowired
    private BillRepository billRepository;

    public Bill createBill(Bill bill) {
        return billRepository.save(bill);
    }
}