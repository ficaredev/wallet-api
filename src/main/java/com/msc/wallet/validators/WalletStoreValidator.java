package com.msc.wallet.validators;

import lombok.Data;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.UUID;

@Data
public class WalletStoreValidator {
    @NotNull
    private UUID currencyId;

    private String hash;

    @NotNull
    private boolean isDefault;

    @NotBlank
    private String owner;

    @NotNull
    @Min(0)
    private int ownerId;
}
