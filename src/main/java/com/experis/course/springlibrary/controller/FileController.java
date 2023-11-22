package com.experis.course.springlibrary.controller;

import com.experis.course.springlibrary.exceptions.BookNotFoundException;
import com.experis.course.springlibrary.model.Book;
import com.experis.course.springlibrary.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/files")
public class FileController {

  @Autowired
  private BookService bookService;

  // metodo che restituisce l'immagine di cover del book preso per id
  @GetMapping("/cover/{bookId}")
  public ResponseEntity<byte[]> serveCover(@PathVariable Integer bookId) {
    try {
      // recupero il libro con quell'id
      Book book = bookService.getBookById(bookId);
      byte[] coverBytes = book.getCover();
      // se ha la cover la restituisco
      if (coverBytes != null && coverBytes.length > 0) {
        MediaType mediaType = MediaType.IMAGE_JPEG;
        // restituisco i byte[] della cover come contenuto della response
        return ResponseEntity.ok().contentType(mediaType).body(coverBytes);
      } else {
        return ResponseEntity.notFound().build();
      }
    } catch (BookNotFoundException e) {
      // se non trovo il libro ritorno un HTTP 404
      return ResponseEntity.notFound().build();
    }

  }
}
