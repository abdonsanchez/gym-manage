package com.gym.payment.repository;

import com.gym.payment.model.Invoice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface InvoiceRepository extends JpaRepository<Invoice, Long> {
    List<Invoice> findByMemberId(Long memberId);

    Optional<Invoice> findByInvoiceNumber(String invoiceNumber);
}
