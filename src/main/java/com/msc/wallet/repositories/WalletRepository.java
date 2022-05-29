package com.msc.wallet.repositories;

import com.msc.wallet.enums.WalletOwner;
import com.msc.wallet.models.Wallet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface WalletRepository extends JpaRepository<Wallet, UUID> {
    Optional<Wallet> findFirstByIdAndOwnerAndOwnerId(UUID id, WalletOwner owner, int ownerId);

    List<Wallet> findAllByOwnerAndOwnerId(WalletOwner owner, int ownerId);

    List<Wallet> findAllByOwner(WalletOwner owner);
}
