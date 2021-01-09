package com.library.nnamdi_azikiwe.model.repository;

import com.library.nnamdi_azikiwe.model.entity.Book;
import com.library.nnamdi_azikiwe.model.projection.BookSearch;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Repository
public interface BookRepository extends JpaRepository<Book, Integer> {

   /* Native query to search books by title and author
    This could be further extended to search with other properties, e.g: author etc.
    */
    @Query(value = "SELECT * FROM Book b WHERE TITLE LIKE %:title%",
            nativeQuery = true
    )
    List<BookSearch> findByTitleContaining(@Param("title") String title);
    Optional<Book> findByTitleAndCallNumber(String title, String callNumber);
}
