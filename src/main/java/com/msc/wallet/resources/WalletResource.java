package com.msc.wallet.resources;

import com.msc.wallet.enums.WalletOwner;
import com.msc.wallet.models.Wallet;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class WalletResource {
    private UUID id;
    private double balance;
    private String token;
    private UUID currencyId;
    private String hash;
    private boolean isDefault;
    private WalletOwner owner;
    private int ownerId;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public WalletResource(Wallet wallet) {
        this.id = wallet.getId();
        this.balance = wallet.getBalance();
        this.token = wallet.getToken();
        this.currencyId = wallet.getCurrencyId();
        this.hash = wallet.getHash();
        this.isDefault = wallet.isDefault();
        this.owner = wallet.getOwner();
        this.ownerId = wallet.getOwnerId();
        this.createdAt = wallet.getCreatedAt();
        this.updatedAt = wallet.getUpdatedAt();
    }

    public static List<WalletResource> collection(List<Wallet> wallets) {
        return wallets.stream().map(WalletResource::new).collect(Collectors.toList());
    }
}
