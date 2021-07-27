package com.visitor.urlshortener.repository;

import com.visitor.urlshortener.entity.Url;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface UrlRepository extends JpaRepository<Url, String> {
    @Query("SELECT u FROM Url u WHERE u.hashValue LIKE ?1%")
    List<Url> searchByHashValueStartsWith(String hashValue);
}
