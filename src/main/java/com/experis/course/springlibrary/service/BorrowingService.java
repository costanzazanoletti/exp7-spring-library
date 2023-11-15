package com.experis.course.springlibrary.service;

import com.experis.course.springlibrary.exceptions.BookNotFoundException;
import com.experis.course.springlibrary.exceptions.BorrowingNotFoundException;
import com.experis.course.springlibrary.model.Book;
import com.experis.course.springlibrary.model.Borrowing;
import com.experis.course.springlibrary.repository.BookRepository;
import com.experis.course.springlibrary.repository.BorrowingRepository;
import java.time.LocalDate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BorrowingService {

  @Autowired
  BookRepository bookRepository;
  @Autowired
  BorrowingRepository borrowingRepository;

  public Borrowing createNewBorrowing(Integer bookId) throws BookNotFoundException {
    Book book = bookRepository.findById(bookId)
        .orElseThrow(() -> new BookNotFoundException("Book with id " + bookId + " not found"));
    Borrowing borrowing = new Borrowing();
    borrowing.setStartDate(LocalDate.now());
    borrowing.setExpireDate(LocalDate.now().plusMonths(1));
    borrowing.setBook(book);
    return borrowing;
  }

  public Borrowing saveBorrowing(Borrowing borrowing) {
    return borrowingRepository.save(borrowing);
  }

  public Borrowing getBorrowing(Integer id) throws BorrowingNotFoundException {
    return borrowingRepository.findById(id)
        .orElseThrow(
            () -> new BorrowingNotFoundException("Borrowing with id " + id + " not found")
        );
  }
}
