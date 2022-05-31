package com.msc.wallet.repositories;

import com.github.javafaker.Faker;
import com.msc.wallet.enums.WalletOwner;
import com.msc.wallet.models.Wallet;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@DataJpaTest
@ExtendWith(SpringExtension.class)
@DisplayName("Wallet repository tests")
class WalletRepositoryTest {
    static Faker faker = new Faker();

    @Autowired
    WalletRepository repository;

    @Test
    @DisplayName("It should find first by id and owner and ownerId")
    void itShouldFindFirstByIdAndOwnerAndOwnerId() {
        WalletOwner owner = WalletOwner.MERCHANT;
        int ownerId = 1;
        Wallet wallet = new Wallet(UUID.randomUUID(), true, owner, ownerId);

        Wallet newWallet = repository.save(wallet);

        Optional<Wallet> object = repository.findFirstByIdAndOwnerAndOwnerId(newWallet.getId(), owner, ownerId);

        Assertions
                .assertThat(object)
                .isPresent()
                .contains(newWallet);
    }

    @Test
    @DisplayName("It should not find first by id and owner and ownerId")
    void itShouldNotFindFirstByIdAndOwnerAndOwnerId() {
        WalletOwner owner = WalletOwner.CUSTOMER;
        int ownerId = 1;
        Wallet wallet = new Wallet(UUID.randomUUID(), true, owner, ownerId);

        repository.save(wallet);

        Optional<Wallet> object = repository.findFirstByIdAndOwnerAndOwnerId(UUID.randomUUID(), owner, ownerId);

        Assertions
                .assertThat(object)
                .isEmpty();
    }

    @Test
    @DisplayName("It should find all by owner and ownerId")
    void itShouldFindAllByOwnerAndOwnerId() {
        WalletOwner owner = WalletOwner.MERCHANT;
        int ownerId = 1;
        Wallet wallet = new Wallet(UUID.randomUUID(), true, owner, ownerId);

        Wallet newWallet = repository.save(wallet);

        List<Wallet> list = repository.findAllByOwnerAndOwnerId(owner, ownerId);

        Assertions
                .assertThat(list)
                .hasSize(1);

    }

    @Test
    @DisplayName("It should not find all by owner and ownerId")
    void itShouldNotFindAllByOwnerAndOwnerId() {
        WalletOwner owner = WalletOwner.CUSTOMER;
        int ownerId = 1;
        Wallet wallet = new Wallet(UUID.randomUUID(), true, owner, ownerId);

        repository.save(wallet);

        List<Wallet> list = repository.findAllByOwnerAndOwnerId(WalletOwner.MERCHANT, ownerId);

        Assertions
                .assertThat(list)
                .isEmpty();
    }

    @Test
    @DisplayName("It should find all by owner")
    void itShouldFindAllByOwner() {
        WalletOwner owner = WalletOwner.MERCHANT;
        Wallet wallet = new Wallet(UUID.randomUUID(), faker.bool().bool(), owner, (int) faker.number().randomNumber());

        repository.save(wallet);

        List<Wallet> list = repository.findAllByOwner(owner);

        Assertions
                .assertThat(list)
                .hasSize(1)
                .extracting(Wallet::getOwner)
                .contains(wallet.getOwner());
    }

    @Test
    @DisplayName("It should not find all by owner")
    void itShouldNotFindAllByOwner() {
        WalletOwner owner = WalletOwner.CUSTOMER;
        Wallet wallet = new Wallet(UUID.randomUUID(), faker.bool().bool(), owner, (int) faker.number().randomNumber());

        repository.save(wallet);

        List<Wallet> list = repository.findAllByOwner(WalletOwner.MERCHANT);

        Assertions
                .assertThat(list)
                .isEmpty();
    }
}