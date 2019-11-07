package fr.d2factory.libraryapp.member;

import fr.d2factory.libraryapp.Exception.HasInsufficientSoldInWalletException;

import static fr.d2factory.libraryapp.constant.Constant.*;

public class Student extends Member {

    private Enum year;

    public void setYear(Enum year) {
        this.year = year;
    }

    @Override
    public void payBook(int numberOfDays) throws HasInsufficientSoldInWalletException {
        if (this.year.equals(Year.FIRST)) {
            if (numberOfDays > NUMBER_OF_FREE_DAYS_STUDENT_IN_FIRTS_YEAR) {
                numberOfDays = numberOfDays - NUMBER_OF_FREE_DAYS_STUDENT_IN_FIRTS_YEAR;
                updateWallet(numberOfDays);
            }
        } else {
            updateWallet(numberOfDays);
        }
    }

    private void updateWallet(int numberOfDays) throws HasInsufficientSoldInWalletException {
        float memberBookPrice = numberOfDays <= STUDENT_THRESHOLD_DAY ? numberOfDays * MEMBER_DAY_PRICE : numberOfDays * STUDENT_DAY_PRICE_LATE;
        if(wallet >= memberBookPrice) {
                wallet = wallet - (memberBookPrice);
            } else {
                throw new HasInsufficientSoldInWalletException(MEMBER_HAS_NO_SOLD_ON_HIS_WALLET);
            }
    }
}
