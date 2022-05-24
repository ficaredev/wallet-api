package com.msc.wallet.database.seeds;

import com.msc.wallet.database.factories.WalletFactory;
import com.msc.wallet.models.Wallet;
import com.msc.wallet.repositories.WalletRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@AllArgsConstructor
public class WalletSeeder {
    WalletRepository repository;
    WalletFactory factory;

    public Wallet create() {
        return repository.save(factory.run());
    }

    public void run(int x) {
        repository.saveAll(
                seeds(x)
        );
    }

    private List<Wallet> seeds(int x) {
        List<Wallet> wallets = new ArrayList<>();

        for (int i = 0; i < x; i++) {
            wallets.add(create());
        }

        return wallets;
    }
}
