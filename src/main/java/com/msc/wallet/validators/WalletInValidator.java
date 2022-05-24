package com.msc.wallet.validators;

import lombok.Data;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@Data
public class WalletInValidator {
    @NotNull
    @Min(0)
    private double amount;
}
