package com.msc.wallet.controllers;

import com.msc.wallet.enums.WalletOwner;
import com.msc.wallet.models.Wallet;
import com.msc.wallet.resources.WalletResource;
import com.msc.wallet.services.WalletService;
import com.msc.wallet.validators.WalletInValidator;
import com.msc.wallet.validators.WalletStoreValidator;
import com.msc.wallet.validators.WalletUpdateValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.UUID;

@CrossOrigin
@RestController
@RequestMapping("v1/wallets")
@RequiredArgsConstructor
public class WalletController {
    private final WalletService walletService;

    @GetMapping
    public ResponseEntity<List<WalletResource>> index() {
        var wallets = walletService.index();
        return ResponseEntity.ok(WalletResource.collection(wallets));
    }

    @PostMapping
    public ResponseEntity<WalletResource> store(@RequestBody @Valid WalletStoreValidator data) {
        var wallet = new Wallet(
                data.getCurrencyId(),
                data.getHash(),
                data.isDefault(),
                WalletOwner.cast(data.getOwner()),
                data.getOwnerId()
        );

        return new ResponseEntity<>(new WalletResource(walletService.store(wallet)), HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<WalletResource> show(@PathVariable UUID id) {
        var wallet = walletService.show(id);

        return ResponseEntity.ok(new WalletResource(wallet));
    }

    @PutMapping("/{id}")
    public ResponseEntity<WalletResource> update(@PathVariable UUID id, @Valid @RequestBody WalletUpdateValidator data) {
        return ResponseEntity.ok(new WalletResource(walletService.update(id, data)));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> destroy(@PathVariable UUID id) {
        walletService.destroy(id);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PostMapping("/{id}/in")
    public ResponseEntity<WalletResource> in(@PathVariable UUID id, @Valid @RequestBody WalletInValidator data) {
        return ResponseEntity.ok(new WalletResource(walletService.in(id, data.getAmount())));
    }

    @PostMapping("/{id}/out")
    public ResponseEntity<WalletResource> out(@PathVariable UUID id, @Valid @RequestBody WalletInValidator data) {
        return ResponseEntity.ok(new WalletResource(walletService.out(id, data.getAmount())));
    }
}
