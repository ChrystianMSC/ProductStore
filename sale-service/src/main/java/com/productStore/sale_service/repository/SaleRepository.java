package com.productStore.sale_service.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.productStore.sale_service.entity.Sale;

public interface SaleRepository  extends JpaRepository<Sale, Long> {

}
