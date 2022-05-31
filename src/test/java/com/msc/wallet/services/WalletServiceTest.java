package com.msc.wallet.services;

import com.github.javafaker.Faker;
import com.msc.wallet.enums.WalletOwner;
import com.msc.wallet.models.Wallet;
import com.msc.wallet.repositories.WalletRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.UUID;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class WalletServiceTest {
    static Faker faker = new Faker();

    WalletService service;

    @Mock
    WalletRepository repository;

    @BeforeEach
    void setUp() {
        service = new WalletService(repository);
    }

    @Test
    void canIndex() {
        service.index();

        verify(repository).findAll();
    }

    @Test
    void canStore() {
        Wallet wallet = new Wallet(UUID.randomUUID(), faker.bool().bool(), WalletOwner.CUSTOMER, (int) faker.number().randomNumber());

        service.store(wallet);

        ArgumentCaptor<Wallet> walletArgumentCaptor = ArgumentCaptor
                .forClass(Wallet.class);

        verify(repository)
                .save(walletArgumentCaptor.capture());

        Wallet captureWallet = walletArgumentCaptor.getValue();

        assertThat(captureWallet).isEqualTo(wallet);
    }

    @Test
    @Disabled
    void canShow() {
    }

    @Test
    @Disabled
    void canUpdate() {
    }

    @Test
    @Disabled
    void canDestroy() {
    }

    @Test
    @Disabled
    void canIn() {
    }

    @Test
    void canOut() {
    }
}