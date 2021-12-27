package com.bankproject.demo.dao;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.bankproject.demo.model.Transaction;

@Repository
public interface TransactionDao extends CrudRepository<Transaction, Long> {

	@Query("FROM Transaction u ORDER BY u.transactionId DESC")
	Transaction findFirstByOrderByDateDesc();

	/*
	 * @Query("FROM Transaction u WHERE u.transactionDate BETWEEN :startDate AND :endDate"
	 * ) List<Transaction> getAllTransactionByFromAndToDates(@Param("startDate")
	 * LocalDateTime startDate,
	 * 
	 * @Param("endDate") LocalDateTime endDate);
	 */
	/*
	 * List<Transaction> findByTransactionDateBetween(@Temporal(TemporalType.DATE)
	 * Date startDate,
	 * 
	 * @Temporal(TemporalType.DATE) Date endDate);
	 */

	List<Transaction> findByTransactionDateBetween(LocalDateTime startDate, LocalDateTime endDate);

	@Query("SELECT u FROM Transaction u WHERE month(u.transactionDate) = :month")
	List<Transaction> getAllDebitAndCreditTransactionByMonth(@Param("month") Integer month);

	/*
	 * @Query("FROM Transaction u WHERE u.account_Id = :accountId")
	 * List<Transaction> getAllTransactionByAccountId(Integer accountId);
	 */

	// Account findById(long accountNumber);
	// long getAccountNumber();
	// void save(Transaction transaction);
	// void save(TransactionDao transactionDao);

}
