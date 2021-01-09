package com.library.nnamdi_azikiwe.controller;

import com.library.nnamdi_azikiwe.model.entity.Book;
import com.library.nnamdi_azikiwe.model.entity.BorrowedBook;
import com.library.nnamdi_azikiwe.model.projection.BookSearch;
import com.library.nnamdi_azikiwe.model.requests.BookUpdateRequest;
import com.library.nnamdi_azikiwe.model.requests.BorrowBookRequest;
import com.library.nnamdi_azikiwe.service.book.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/api/v1/book")
public class BookController {
    @Autowired
    BookService bookService;

    @PostMapping
    public Book add(@RequestBody Book book) {
        return bookService.add(book);
    }

    @PutMapping
    public Book update(@RequestBody BookUpdateRequest book) {
        return bookService.update(book);
    }

    @GetMapping("/")
    public List<Book> list() {
        return bookService.list();
    }

    @GetMapping
    public List<BookSearch> searchBook(@RequestParam(defaultValue = "book") String title) {
        return bookService.search(title);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable(name = "id") int id) {
        bookService.delete(id);
       return new ResponseEntity<>("Book discarded", HttpStatus.OK);
    }

    @PostMapping("/lend")
    public ResponseEntity<BorrowedBook> lend(@RequestBody List<BorrowBookRequest> bookRequests) throws Exception {
        return new ResponseEntity<>(bookService.lend(bookRequests), HttpStatus.OK);
    }
}
