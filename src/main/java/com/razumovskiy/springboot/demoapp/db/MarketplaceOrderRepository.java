package com.razumovskiy.springboot.demoapp.db;

import com.razumovskiy.springboot.demoapp.model.MarketplaceOrder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MarketplaceOrderRepository extends JpaRepository<MarketplaceOrder, Integer> {

}
