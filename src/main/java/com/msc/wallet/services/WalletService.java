package com.msc.wallet.services;

import com.msc.wallet.exceptions.ResourceNotFoundException;
import com.msc.wallet.models.Wallet;
import com.msc.wallet.repositories.WalletRepository;
import com.msc.wallet.validators.WalletUpdateValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class WalletService {
    private final WalletRepository walletRepository;

    @Autowired
    public WalletService(WalletRepository walletRepository) {
        this.walletRepository = walletRepository;
    }


    public List<Wallet> index() {
        return walletRepository.findAll();
    }

    public Wallet store(Wallet wallet) {
        return walletRepository.save(wallet);
    }

    public Wallet show(UUID id) {
        return walletRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Wallet not found"));
    }

    public Wallet update(UUID id, WalletUpdateValidator data) {
        Wallet wallet = this.show(id);

        wallet.setHash(data.getHash());

        return walletRepository.save(wallet);
    }

    public void destroy(UUID id) {
        this.show(id);

        walletRepository.deleteById(id);
    }

    public Wallet in(UUID id, double amount) {
        Wallet wallet = this.show(id);

        wallet.setBalance(wallet.getBalance() + amount);

        return walletRepository.save(wallet);
    }

    public Wallet out(UUID id, double amount) {
        Wallet wallet = this.show(id);

        wallet.setBalance(wallet.getBalance() - amount);

        return walletRepository.save(wallet);
    }
}
