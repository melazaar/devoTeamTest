package fr.d2factory.libraryapp.member;

import fr.d2factory.libraryapp.Exception.HasInsufficientSoldInWalletException;

import static fr.d2factory.libraryapp.constant.Constant.*;

public class Resident extends Member {

    @Override
    public void payBook(int numberOfDays) throws HasInsufficientSoldInWalletException {
        float memberBookPrice = numberOfDays <= RESIDENT_THRESHOLD_DAY ? numberOfDays * MEMBER_DAY_PRICE : numberOfDays * RESIDENT_DAY_PRICE_LATE;

        if (wallet >= memberBookPrice) {
            wallet = wallet - memberBookPrice;
        } else {
            throw new HasInsufficientSoldInWalletException(MEMBER_HAS_NO_SOLD_ON_HIS_WALLET);
        }
    }
}
