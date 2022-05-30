package com.msc.wallet.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.msc.wallet.enums.WalletOwner;
import com.msc.wallet.models.Wallet;
import com.msc.wallet.repositories.WalletRepository;
import com.msc.wallet.resources.WalletResource;
import com.msc.wallet.services.WalletService;
import com.msc.wallet.validators.WalletInValidator;
import com.msc.wallet.validators.WalletStoreValidator;
import com.msc.wallet.validators.WalletUpdateValidator;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ContextConfiguration(classes = {WalletController.class})
@ExtendWith(SpringExtension.class)
class WalletControllerTest {
    @Autowired
    private WalletController walletController;

    @MockBean
    private WalletService walletService;

    @Test
    void testIndex() throws Exception {
        when(this.walletService.index()).thenReturn(new ArrayList<>());
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/v1/wallets");
        MockMvcBuilders.standaloneSetup(this.walletController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content().string("[]"));
    }

    @Test
    void testStore() throws Exception {
        when(this.walletService.index()).thenReturn(new ArrayList<>());

        WalletStoreValidator walletStoreValidator = new WalletStoreValidator();
        walletStoreValidator.setCurrencyId(UUID.randomUUID());
        walletStoreValidator.setDefault(true);
        walletStoreValidator.setHash("Hash");
        walletStoreValidator.setOwner("Owner");
        walletStoreValidator.setOwnerId(123);
        String content = (new ObjectMapper()).writeValueAsString(walletStoreValidator);
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/v1/wallets")
                .contentType(MediaType.APPLICATION_JSON)
                .content(content);
        MockMvcBuilders.standaloneSetup(this.walletController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content().string("[]"));
    }

    @Test
    void testShow() {
        Wallet wallet = new Wallet();
        wallet.setBalance(10.0d);
        wallet.setCreatedAt(LocalDateTime.of(1, 1, 1, 1, 1));
        UUID randomUUIDResult = UUID.randomUUID();
        wallet.setCurrencyId(randomUUIDResult);
        wallet.setDefault(true);
        wallet.setHash("Hash");
        UUID randomUUIDResult1 = UUID.randomUUID();
        wallet.setId(randomUUIDResult1);
        wallet.setOwner(WalletOwner.CUSTOMER);
        wallet.setOwnerId(123);
        wallet.setToken("ABC123");
        wallet.setUpdatedAt(LocalDateTime.of(1, 1, 1, 1, 1));
        WalletRepository walletRepository = mock(WalletRepository.class);
        when(walletRepository.findById(any())).thenReturn(Optional.of(wallet));
        WalletController walletController = new WalletController(new WalletService(walletRepository));
        ResponseEntity<WalletResource> actualShowResult = walletController.show(UUID.randomUUID());
        assertTrue(actualShowResult.hasBody());
        assertTrue(actualShowResult.getHeaders().isEmpty());
        assertEquals(HttpStatus.OK, actualShowResult.getStatusCode());
        WalletResource body = actualShowResult.getBody();
        assertEquals(123, body.getOwnerId());
        assertEquals(WalletOwner.CUSTOMER, body.getOwner());
        assertSame(randomUUIDResult1, body.getId());
        assertEquals("Hash", body.getHash());
        assertTrue(body.isDefault());
        assertEquals("01:01", body.getUpdatedAt().toLocalTime().toString());
        assertEquals(10.0d, body.getBalance());
        assertEquals("ABC123", body.getToken());
        assertSame(randomUUIDResult, body.getCurrencyId());
        assertEquals("0001-01-01", body.getCreatedAt().toLocalDate().toString());
        verify(walletRepository).findById(any());
    }

    @Test
    void testUpdate() {
        Wallet wallet = new Wallet();
        wallet.setBalance(10.0d);
        wallet.setCreatedAt(LocalDateTime.of(1, 1, 1, 1, 1));
        wallet.setCurrencyId(UUID.randomUUID());
        wallet.setDefault(true);
        wallet.setHash("Hash");
        wallet.setId(UUID.randomUUID());
        wallet.setOwner(WalletOwner.CUSTOMER);
        wallet.setOwnerId(123);
        wallet.setToken("ABC123");
        wallet.setUpdatedAt(LocalDateTime.of(1, 1, 1, 1, 1));
        Optional<Wallet> ofResult = Optional.of(wallet);

        Wallet wallet1 = new Wallet();
        wallet1.setBalance(10.0d);
        wallet1.setCreatedAt(LocalDateTime.of(1, 1, 1, 1, 1));
        UUID randomUUIDResult = UUID.randomUUID();
        wallet1.setCurrencyId(randomUUIDResult);
        wallet1.setDefault(true);
        wallet1.setHash("Hash");
        UUID randomUUIDResult1 = UUID.randomUUID();
        wallet1.setId(randomUUIDResult1);
        wallet1.setOwner(WalletOwner.CUSTOMER);
        wallet1.setOwnerId(123);
        wallet1.setToken("ABC123");
        wallet1.setUpdatedAt(LocalDateTime.of(1, 1, 1, 1, 1));
        WalletRepository walletRepository = mock(WalletRepository.class);
        when(walletRepository.save(any())).thenReturn(wallet1);
        when(walletRepository.findById(any())).thenReturn(ofResult);
        WalletController walletController = new WalletController(new WalletService(walletRepository));
        UUID id = UUID.randomUUID();

        WalletUpdateValidator walletUpdateValidator = new WalletUpdateValidator();
        walletUpdateValidator.setHash("Hash");
        ResponseEntity<WalletResource> actualUpdateResult = walletController.update(id, walletUpdateValidator);
        assertTrue(actualUpdateResult.hasBody());
        assertTrue(actualUpdateResult.getHeaders().isEmpty());
        assertEquals(HttpStatus.OK, actualUpdateResult.getStatusCode());
        WalletResource body = actualUpdateResult.getBody();
        assertEquals(123, body.getOwnerId());
        assertEquals(WalletOwner.CUSTOMER, body.getOwner());
        assertSame(randomUUIDResult1, body.getId());
        assertEquals("Hash", body.getHash());
        assertTrue(body.isDefault());
        assertEquals("01:01", body.getUpdatedAt().toLocalTime().toString());
        assertEquals(10.0d, body.getBalance());
        assertEquals("ABC123", body.getToken());
        assertSame(randomUUIDResult, body.getCurrencyId());
        assertEquals("0001-01-01", body.getCreatedAt().toLocalDate().toString());
        verify(walletRepository).save(any());
        verify(walletRepository).findById(any());
    }

    @Test
    void testDestroy() throws Exception {
        doNothing().when(this.walletService).destroy(any());
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.delete("/v1/wallets/{id}", UUID.randomUUID());
        ResultActions actualPerformResult = MockMvcBuilders.standaloneSetup(this.walletController)
                .build()
                .perform(requestBuilder);
        actualPerformResult.andExpect(MockMvcResultMatchers.status().isNoContent());
    }

    @Test
    void testIn() {
        Wallet wallet = new Wallet();
        wallet.setBalance(10.0d);
        wallet.setCreatedAt(LocalDateTime.of(1, 1, 1, 1, 1));
        wallet.setCurrencyId(UUID.randomUUID());
        wallet.setDefault(true);
        wallet.setHash("Hash");
        wallet.setId(UUID.randomUUID());
        wallet.setOwner(WalletOwner.CUSTOMER);
        wallet.setOwnerId(123);
        wallet.setToken("ABC123");
        wallet.setUpdatedAt(LocalDateTime.of(1, 1, 1, 1, 1));
        Optional<Wallet> ofResult = Optional.of(wallet);

        Wallet wallet1 = new Wallet();
        wallet1.setBalance(10.0d);
        wallet1.setCreatedAt(LocalDateTime.of(1, 1, 1, 1, 1));
        UUID randomUUIDResult = UUID.randomUUID();
        wallet1.setCurrencyId(randomUUIDResult);
        wallet1.setDefault(true);
        wallet1.setHash("Hash");
        UUID randomUUIDResult1 = UUID.randomUUID();
        wallet1.setId(randomUUIDResult1);
        wallet1.setOwner(WalletOwner.CUSTOMER);
        wallet1.setOwnerId(123);
        wallet1.setToken("ABC123");
        wallet1.setUpdatedAt(LocalDateTime.of(1, 1, 1, 1, 1));
        WalletRepository walletRepository = mock(WalletRepository.class);
        when(walletRepository.save(any())).thenReturn(wallet1);
        when(walletRepository.findById(any())).thenReturn(ofResult);
        WalletController walletController = new WalletController(new WalletService(walletRepository));
        UUID id = UUID.randomUUID();

        WalletInValidator walletInValidator = new WalletInValidator();
        walletInValidator.setAmount(10.0d);
        ResponseEntity<WalletResource> actualInResult = walletController.in(id, walletInValidator);
        assertTrue(actualInResult.hasBody());
        assertTrue(actualInResult.getHeaders().isEmpty());
        assertEquals(HttpStatus.OK, actualInResult.getStatusCode());
        WalletResource body = actualInResult.getBody();
        assertEquals(123, body.getOwnerId());
        assertEquals(WalletOwner.CUSTOMER, body.getOwner());
        assertSame(randomUUIDResult1, body.getId());
        assertEquals("Hash", body.getHash());
        assertTrue(body.isDefault());
        assertEquals("01:01", body.getUpdatedAt().toLocalTime().toString());
        assertEquals(10.0d, body.getBalance());
        assertEquals("ABC123", body.getToken());
        assertSame(randomUUIDResult, body.getCurrencyId());
        assertEquals("0001-01-01", body.getCreatedAt().toLocalDate().toString());
        verify(walletRepository).save(any());
        verify(walletRepository).findById(any());
    }

    @Test
    void testOut() {
        Wallet wallet = new Wallet();
        wallet.setBalance(10.0d);
        wallet.setCreatedAt(LocalDateTime.of(1, 1, 1, 1, 1));
        wallet.setCurrencyId(UUID.randomUUID());
        wallet.setDefault(true);
        wallet.setHash("Hash");
        wallet.setId(UUID.randomUUID());
        wallet.setOwner(WalletOwner.CUSTOMER);
        wallet.setOwnerId(123);
        wallet.setToken("ABC123");
        wallet.setUpdatedAt(LocalDateTime.of(1, 1, 1, 1, 1));
        Optional<Wallet> ofResult = Optional.of(wallet);

        Wallet wallet1 = new Wallet();
        wallet1.setBalance(10.0d);
        wallet1.setCreatedAt(LocalDateTime.of(1, 1, 1, 1, 1));
        UUID randomUUIDResult = UUID.randomUUID();
        wallet1.setCurrencyId(randomUUIDResult);
        wallet1.setDefault(true);
        wallet1.setHash("Hash");
        UUID randomUUIDResult1 = UUID.randomUUID();
        wallet1.setId(randomUUIDResult1);
        wallet1.setOwner(WalletOwner.CUSTOMER);
        wallet1.setOwnerId(123);
        wallet1.setToken("ABC123");
        wallet1.setUpdatedAt(LocalDateTime.of(1, 1, 1, 1, 1));
        WalletRepository walletRepository = mock(WalletRepository.class);
        when(walletRepository.save(any())).thenReturn(wallet1);
        when(walletRepository.findById(any())).thenReturn(ofResult);
        WalletController walletController = new WalletController(new WalletService(walletRepository));
        UUID id = UUID.randomUUID();

        WalletInValidator walletInValidator = new WalletInValidator();
        walletInValidator.setAmount(10.0d);
        ResponseEntity<WalletResource> actualOutResult = walletController.out(id, walletInValidator);
        assertTrue(actualOutResult.hasBody());
        assertTrue(actualOutResult.getHeaders().isEmpty());
        assertEquals(HttpStatus.OK, actualOutResult.getStatusCode());
        WalletResource body = actualOutResult.getBody();
        assertEquals(123, body.getOwnerId());
        assertEquals(WalletOwner.CUSTOMER, body.getOwner());
        assertSame(randomUUIDResult1, body.getId());
        assertEquals("Hash", body.getHash());
        assertTrue(body.isDefault());
        assertEquals("01:01", body.getUpdatedAt().toLocalTime().toString());
        assertEquals(10.0d, body.getBalance());
        assertEquals("ABC123", body.getToken());
        assertSame(randomUUIDResult, body.getCurrencyId());
        assertEquals("0001-01-01", body.getCreatedAt().toLocalDate().toString());
        verify(walletRepository).save(any());
        verify(walletRepository).findById(any());
    }
}

