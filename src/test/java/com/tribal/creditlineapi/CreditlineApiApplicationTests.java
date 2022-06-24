package com.tribal.creditlineapi;

import static org.mockito.Mockito.mock;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.boot.test.context.SpringBootTest;

import com.tribal.creditlineapi.controller.CreditLineController;
import com.tribal.creditlineapi.dao.CreditLineDAO;
import com.tribal.creditlineapi.entity.CreditLineEntity;
import com.tribal.creditlineapi.service.CreditLineService;
import com.tribal.creditlineapi.service.CreditLineServiceImpl;
import com.tribal.creditlineapi.utils.CreditLineUtils;

@SpringBootTest
@RunWith(MockitoJUnitRunner.class)
class CreditlineApiApplicationTests {

	@Mock 
	private CreditLineEntity creditLineEntity;

	@Mock
	private CreditLineDAO creditLineDAO;

	@Mock
	private CreditLineService creditLineService;

	@InjectMocks
	private CreditLineServiceImpl creditLineServiceImpl;

	@InjectMocks
	private CreditLineController creditLineController;

	
	
	@BeforeEach
	public void setup() {
		this.creditLineDAO = mock(CreditLineDAO.class);
		this.creditLineServiceImpl = new CreditLineServiceImpl(this.creditLineDAO);
		MockitoAnnotations.openMocks(this);//initMocks(this);
	}
	

	@Test
	void shouldReturnAvalibleCreditLine() {
		CreditLineEntity creditLineEntity = 
			new CreditLineEntity("SME"
								, BigDecimal.valueOf(550.00)
								, BigDecimal.valueOf(1000.00)
								, LocalDateTime.now()
								, BigDecimal.valueOf(600.00));
		Assertions.assertEquals(BigDecimal.valueOf(200.00).setScale(2),CreditLineUtils.calculateAvailableCreditLine(creditLineEntity)); 

		creditLineEntity = 
			new CreditLineEntity("STARTUP"
								, BigDecimal.valueOf(550.00)
								, BigDecimal.valueOf(1000.00)
								, LocalDateTime.now()
								, BigDecimal.valueOf(600.00));
		Assertions.assertEquals(BigDecimal.valueOf(200.00).setScale(2),CreditLineUtils.calculateAvailableCreditLine(creditLineEntity));

		creditLineEntity = 
			new CreditLineEntity("SME"
								, BigDecimal.valueOf(5550.00)
								, BigDecimal.valueOf(1000.00)
								, LocalDateTime.now()
								, BigDecimal.valueOf(600.00));
		Assertions.assertEquals(BigDecimal.valueOf(200.00).setScale(2),CreditLineUtils.calculateAvailableCreditLine(creditLineEntity));

		creditLineEntity = 
			new CreditLineEntity("STARTUP"
								, BigDecimal.valueOf(5550.00)
								, BigDecimal.valueOf(1000.00)
								, LocalDateTime.now()
								, BigDecimal.valueOf(600.00));
		Assertions.assertEquals(BigDecimal.valueOf(1850.00).setScale(2),CreditLineUtils.calculateAvailableCreditLine(creditLineEntity));
	}
	/*
	 * shouldAcceptedCreditLine
	 */
	@Test
	void shouldAcceptedCreditLine() {
		CreditLineEntity creditLineEntity = 
			new CreditLineEntity("STARTUP"
								, BigDecimal.valueOf(550.00)
								, BigDecimal.valueOf(1000.00)
								, LocalDateTime.now()
								, BigDecimal.valueOf(199.00));
		Assertions.assertEquals(Boolean.TRUE,CreditLineUtils.isCreditLineAccepeted(creditLineEntity));

		creditLineEntity = 
			new CreditLineEntity("SME"
								, BigDecimal.valueOf(550.00)
								, BigDecimal.valueOf(1000.00)
								, LocalDateTime.now()
								, BigDecimal.valueOf(199.00));
		Assertions.assertEquals(Boolean.TRUE,CreditLineUtils.isCreditLineAccepeted(creditLineEntity));


		creditLineEntity = 
			new CreditLineEntity("STARTUP"
								, BigDecimal.valueOf(5550.00)
								, BigDecimal.valueOf(1000.00)
								, LocalDateTime.now()
								, BigDecimal.valueOf(600.00));
		Assertions.assertEquals(Boolean.TRUE,CreditLineUtils.isCreditLineAccepeted(creditLineEntity));

		creditLineEntity = 
			new CreditLineEntity("SME"
								, BigDecimal.valueOf(5550.00)
								, BigDecimal.valueOf(10000.00)
								, LocalDateTime.now()
								, BigDecimal.valueOf(600.00));
		Assertions.assertEquals(Boolean.TRUE,CreditLineUtils.isCreditLineAccepeted(creditLineEntity));
	}
	/*
	 * shouldNotAcceptedCreditLine
	 */
	@Test
	void shouldNotAcceptedCreditLine() {
		CreditLineEntity creditLineEntity = 
			new CreditLineEntity("SME"
								, BigDecimal.valueOf(550.00)
								, BigDecimal.valueOf(1000.00)
								, LocalDateTime.now()
								, BigDecimal.valueOf(600.00));
		Assertions.assertEquals(Boolean.FALSE,CreditLineUtils.isCreditLineAccepeted(creditLineEntity));

		creditLineEntity = 
			new CreditLineEntity("STARTUP"
							, BigDecimal.valueOf(550.00)
							, BigDecimal.valueOf(1000.00)
							, LocalDateTime.now()
								, BigDecimal.valueOf(200.00));
		Assertions.assertEquals(Boolean.FALSE,CreditLineUtils.isCreditLineAccepeted(creditLineEntity));

		creditLineEntity = 
			new CreditLineEntity("SME"
								, BigDecimal.valueOf(5550.00)
								, BigDecimal.valueOf(1000.00)
								, LocalDateTime.now()
								, BigDecimal.valueOf(600.00));
		Assertions.assertEquals(Boolean.FALSE,CreditLineUtils.isCreditLineAccepeted(creditLineEntity));

		creditLineEntity = 
			new CreditLineEntity("STARTUP"
							, BigDecimal.valueOf(550.00)
							, BigDecimal.valueOf(1000.00)
							, LocalDateTime.now()
							, BigDecimal.valueOf(1000.00));
		Assertions.assertEquals(Boolean.FALSE,CreditLineUtils.isCreditLineAccepeted(creditLineEntity));
	}

}
