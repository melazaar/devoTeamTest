package fr.d2factory.libraryapp.book;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Objects;

/**
 * ISBN represent the identity object of book
 */
public class ISBN {

    @JsonProperty("isbnCode")
    long isbnCode;

    public ISBN() {

    }

    public ISBN(long isbnCode) {
        this.isbnCode = isbnCode;
    }

    @Override
    public String toString() {
        return "ISBN{" +
                "isbnCode=" + isbnCode +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ISBN isbn = (ISBN) o;
        return isbnCode == isbn.isbnCode;
    }

    @Override
    public int hashCode() {
        return Objects.hash(isbnCode);
    }
}
