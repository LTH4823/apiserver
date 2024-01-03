package org.taerock.apiserver.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.taerock.apiserver.domain.Product;

public interface ProductRepository extends JpaRepository<Product, Long> {

}
