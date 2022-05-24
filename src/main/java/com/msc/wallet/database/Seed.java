package com.msc.wallet.database;

import com.msc.wallet.database.seeds.WalletSeeder;
import lombok.AllArgsConstructor;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@AllArgsConstructor
@Component
public class Seed implements ApplicationRunner {
    WalletSeeder walletSeeder;
    Environment env;

    @Override
    public void run(ApplicationArguments args) {
        if (Arrays.asList(env.getActiveProfiles()).contains("local")) {
            walletSeeder.run(1);
        }
    }
}