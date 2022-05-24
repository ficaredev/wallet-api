package com.msc.wallet.enums;

import com.msc.wallet.exceptions.InvalidAttributeException;

public enum WalletOwner {
    CUSTOMER, MERCHANT;

    public static WalletOwner cast(String x) {
        switch (x) {
            case "customer":
                return CUSTOMER;
            case "merchant":
                return MERCHANT;
        }

        throw new InvalidAttributeException("Invalid owner");
    }
}
