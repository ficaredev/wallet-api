package com.msc.wallet.database.factories;

import com.github.javafaker.Faker;
import com.msc.wallet.enums.WalletOwner;
import com.msc.wallet.models.Wallet;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class WalletFactory {
    public static Faker faker = new Faker();

    public Wallet run() {
        return new Wallet(UUID.randomUUID(), faker.bool().bool(), faker.options().option(WalletOwner.values()), (int) faker.number().randomNumber());
    }
}
