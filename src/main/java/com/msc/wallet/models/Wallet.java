package com.msc.wallet.models;

import com.msc.wallet.enums.WalletOwner;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "wallets")
public class Wallet {
    @Id
    @Type(type = "uuid-char")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @Column(nullable = false)
    private double balance;

    @Column(nullable = false, unique = true)
    private String token;

    @Type(type = "uuid-char")
    @Column(nullable = false)
    private UUID currencyId;

    @Column(unique = true)
    private String hash;

    @Column(nullable = false)
    private boolean isDefault;

    @Column(nullable = false)
    private WalletOwner owner;

    @Column(nullable = false)
    private int ownerId;

    @Column
    @CreationTimestamp
    private LocalDateTime createdAt;

    @Column
    @UpdateTimestamp
    private LocalDateTime updatedAt;

    public Wallet(WalletOwner owner) {
        this.owner = owner;
    }

    public Wallet(UUID currencyId, String hash, boolean isDefault, WalletOwner owner, int ownerId) {
        this.balance = 0;
        this.token = this.generateToken(owner, ownerId);
        this.currencyId = currencyId;
        this.hash = hash;
        this.isDefault = isDefault;
        this.owner = owner;
        this.ownerId = ownerId;
    }

    public Wallet(UUID currencyId, boolean isDefault, WalletOwner owner, int ownerId) {
        this.balance = 0;
        this.token = this.generateToken(owner, ownerId);
        this.currencyId = currencyId;
        this.isDefault = isDefault;
        this.owner = owner;
        this.ownerId = ownerId;
    }

    public Wallet(String hash) {
        this.hash = hash;
    }

    public String generateToken(WalletOwner owner, int ownerId) {
        var prefix = owner.equals(WalletOwner.CUSTOMER) ? 'c' : 'm';

        return "wlt-" + UUID.randomUUID() + "-" + prefix + ownerId;
    }
}
