package com.kepa.springlibraryapp.order;

import com.kepa.springlibraryapp.book.Book;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.ui.Model;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
class OrderControllerTest {

    @Mock
    OrderService orderService;

    @Mock
    Model model;

    @InjectMocks
    OrderController controller;


    MockMvc mockMvc;


    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
    }

    @Test
    void addBookToOrderTest() {
        //given
        Long bookId=1L;
        given(orderService.addBookToOrder(bookId)).willReturn(Optional.of(new Book()));

        //when
        String view = controller.addBookToOrder(bookId,model);

        //then
        then(orderService).should().addBookToOrder(bookId);
        then(model).should().addAttribute(anyString(), any());
        assertThat("message").isEqualToIgnoringCase(view);
    }

    @Test
    void addBookToOrderNotFoundTest() {
        //given
        Long bookId=1L;
        given(orderService.addBookToOrder(bookId)).willReturn(Optional.empty());

        //when
        String view = controller.addBookToOrder(bookId,model);

        //then
        then(orderService).should().addBookToOrder(bookId);
        then(model).should().addAttribute(anyString(), any());
        assertThat("message").isEqualToIgnoringCase(view);
    }

    @Test
    void addBookToOrderControllerTest() throws Exception {
        mockMvc.perform((get("/order-add")).param("bookId","1"))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("message"))
                .andExpect(view().name("message"));
    }

    @Test
    void deleteBookFromOrder() {
    }

    @Test
    void getCurrentOrder() {
    }

    @Test
    void proceedOrder() {
    }
}