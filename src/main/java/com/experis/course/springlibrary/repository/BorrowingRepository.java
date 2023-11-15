package com.experis.course.springlibrary.repository;

import com.experis.course.springlibrary.model.Borrowing;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BorrowingRepository extends JpaRepository<Borrowing, Integer> {

}
