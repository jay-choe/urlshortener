package com.visitor.urlshortener.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Url {
    @Id
    private String hashValue;

    @Column
    private String originalUrl;
}
