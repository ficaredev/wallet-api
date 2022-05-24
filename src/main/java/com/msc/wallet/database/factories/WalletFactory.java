package com.msc.wallet.database.factories;

import com.github.javafaker.Faker;
import com.msc.wallet.enums.WalletOwner;
import com.msc.wallet.models.Wallet;
import org.springframework.stereotype.Component;

import java.util.Random;
import java.util.UUID;

@Component
public class WalletFactory {
    public static Faker faker = new Faker();

    public Wallet run() {
        int owner = new Random().nextInt(WalletOwner.values().length);

        return new Wallet(UUID.randomUUID(), faker.bool().bool(), WalletOwner.values()[owner], (int) faker.number().randomNumber());
    }
}
