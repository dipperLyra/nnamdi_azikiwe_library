package com.library.nnamdi_azikiwe.model.repository;

import com.library.nnamdi_azikiwe.model.entity.BorrowedBook;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BorrowRepository extends JpaRepository<BorrowedBook, Integer> {
}
