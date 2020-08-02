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
import static org.mockito.Mockito.times;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
class OrderControllerTest {

    @Mock
    OrderService orderService;

    @Mock
    ClientOrder clientOrder;

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
    void deleteBookFromOrderTest() {
        //given
        Long bookId=1L;

        //when
        String view = controller.deleteBookFromOrder(bookId,model);

        //then
        then(orderService).should().deleteBookFromOrder(bookId);
        then(model).should(times(3)).addAttribute(anyString(), any());
        assertThat("orderView").isEqualToIgnoringCase(view);
    }

    @Test
    void deleteBookFromOrderControllerTest() throws Exception {
        mockMvc.perform((get("/order-delete")).param("bookIndex","1"))
                .andExpect(status().isOk())
                .andExpect(model().attributeDoesNotExist("order"))
                .andExpect(model().attributeExists("sum"))
                .andExpect(model().attributeExists("orderDetails"))
                .andExpect(view().name("orderView"));
    }

    @Test
    void getCurrentOrderTest() {
        //when
        String view = controller.getCurrentOrder(model);

        //then
        then(model).should(times(3)).addAttribute(anyString(), any());
        assertThat("orderView").isEqualToIgnoringCase(view);
    }

    @Test
    void getCurrentOrderControllerTest() throws Exception {
        mockMvc.perform((get("/order")))
                .andExpect(status().isOk())
                .andExpect(model().attributeDoesNotExist("order"))
                .andExpect(model().attributeExists("sum"))
                .andExpect(model().attributeExists("orderDetails"))
                .andExpect(view().name("orderView"));
    }

    @Test
    void proceedOrder() {
    }
}