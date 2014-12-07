package org.star.page

/**
 * Created by itagakishintarou on 2014/12/07.
 */
enum FilterOption {
    Contains("Contains"), Equal_To("Equal To")

    private final String text

    private FilterOption(final String text) {
        this.text = text
    }

    @Override
    public String toString() {
        return this.text
    }
}