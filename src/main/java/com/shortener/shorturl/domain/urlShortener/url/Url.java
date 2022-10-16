package com.shortener.shorturl.domain.urlShortener.url;

import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.Hibernate;

@Entity
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Url {
    @Id
    private String address;

    @Column
    private String originalUrl;

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(
            o)) {
            return false;
        }
        Url url = (Url) o;
        return address != null && Objects.equals(address, url.address);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
