package com.shop.orderservice;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.shop.orderservice.dto.OrderLineItemsDto;
import com.shop.orderservice.dto.OrderRequest;
import com.shop.orderservice.model.Order;
import com.shop.orderservice.model.OrderLineItems;
import com.shop.orderservice.repository.OrderRepository;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.grammars.ordering.OrderingParserListener;
import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.testcontainers.containers.MySQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.DockerImageName;

import java.math.BigDecimal;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@Slf4j
@Testcontainers
@AutoConfigureMockMvc
class OrderServiceApplicationTests {

	@Container
	public final static MySQLContainer mySQLContainer = new MySQLContainer();
	@Autowired
	private MockMvc mockMvc;
	@Autowired
	private ObjectMapper objectMapper;
	@Autowired
	private OrderRepository orderRepository;

	@DynamicPropertySource
	static void setMySQLContainer(DynamicPropertyRegistry dynamicPropertyRegistry){
		dynamicPropertyRegistry.add("spring.datasource.url",mySQLContainer::getJdbcUrl);
		dynamicPropertyRegistry.add("spring.datasource.username",mySQLContainer::getUsername);
		dynamicPropertyRegistry.add("spring.datasource.password",mySQLContainer::getPassword);

	}


	@Test
	void shouldPlaceOrder() throws Exception {
		OrderRequest orderRequest = getOrderRequest();
		String orderRequestAsString = objectMapper.writeValueAsString(orderRequest);


		MockHttpServletResponse response = mockMvc.perform(MockMvcRequestBuilders.post("/api/order")
				.contentType(MediaType.APPLICATION_JSON)
				.content(orderRequestAsString))
				.andExpect(status().isCreated())
				.andReturn().getResponse();


		Assertions.assertEquals(1,orderRepository.findAll().size()) ;
		Assertions.assertEquals(response.getStatus(),201);


		log.info("result from mockMvc: "+response.getStatus() +" and " +response.getContentAsString());
	}

	private OrderRequest getOrderRequest(){
		OrderLineItemsDto orderLineItems = new OrderLineItemsDto();
		orderLineItems.setPrice(BigDecimal.valueOf(2000));
		orderLineItems.setQuantity(1);
		orderLineItems.setSkuCode("ord11");

		List<OrderLineItemsDto> orderLineItemsList = new ArrayList<>();
		orderLineItemsList.add(orderLineItems);

		OrderRequest orderRequest = new OrderRequest();
		orderRequest.setOrderLineItemsDtoList(orderLineItemsList);

		return orderRequest;
	}

}
