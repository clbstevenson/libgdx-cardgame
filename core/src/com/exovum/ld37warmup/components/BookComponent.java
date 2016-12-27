package com.exovum.ld37warmup.components;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.ArrayMap;

import java.util.Random;

/**
 * Created by exovu_000 on 12/10/2016.
 */
public class BookComponent implements Component{
    public static final String STATE_THROWN = "THROWN"; //"TREE_NORMAL";
    public static final String STATE_CAUGHT = "CAUGHT"; //"TREE_LIGHTS";
    public static final float WIDTH = 3f;
    public static final float HEIGHT = 4f;
    public BookTitle title;


    public enum BookTitle {
        QUIXOTE, MOCKING, WATCH; // TODO: make larger 128x128 images for: GATSBY, IDIOT;

        public String getAssetName() {
            switch (this) {
                case QUIXOTE:
                    return "donq";
                case MOCKING:
                    return "mock";
                case WATCH:
                    return "watch";
                /*
                case GATSBY:
                    return "gatsby";
                case IDIOT:
                    return "idiot";
                    */
                default:
                    return null;
            }
        }

        /**
         * Generate a random quote for the BookTitle
         *
         * @return Text for a random quote from the book
         */
        public String getRandomQuote() {
            // TODO: add quotes
            switch (this) {
                case QUIXOTE:
                    return (this.toString() + " by Miguel de Cervantes");
                case MOCKING:
                    return (this.toString() + " by Harper Lee");
                case WATCH:
                    return (this.toString() + "  by Zora Neale Hurston");
                /*
                case GATSBY:
                    return "'The Great Gatsby' by F. Scott Fitzgerald";
                case IDIOT:
                    return "'The Idiot' by Fyodor Dostoevsky";
                */
                default:
                    return "NO QUOTE";
            }
        }

        public static BookTitle getBookTitleByID(int bookid) {
            return BookTitle.values()[bookid];
        }

        public String toString() {
            switch(this) {
                case QUIXOTE:
                    return "\'Don Quixote\'";
                case MOCKING:
                    return "\'To Kill a Mockingbird\'";
                case WATCH:
                    return "\'Their Eyes Were Watching God\'";
                default:
                    return "No Book Title";
            }
        }
    }

    // Maps BookTitle to an array of quoted Strings
    private static ArrayMap<BookTitle, Array<String>> quotes;

    static {
        quotes = new ArrayMap<>();
        loadQuotes();
    }

    private static void loadQuotes() {
        Gdx.app.log("Book Component", "Loading all of the quotes");
        Array<String> addQuotesMOCKING = new Array<>();
        //TODO add more quotes

        // Adding BookTitle.MOCKING quotes
        addQuotesMOCKING.addAll("\'People generally see what they look for, and hear what they listen for. \'",
                "\'The one thing that doesn't abide by majority rule is a person's conscience.\'",
                "\'Until you climb inside of his skin and walk around in it.\'",
                "\'I think there's just one kind of folks. Folks.\'");
        quotes.put(BookTitle.MOCKING, addQuotesMOCKING);

        /*quotes.put(BookTitle.MOCKING, new Array<String>());
        quotes.get(BookTitle.MOCKING).add("People generally see what they look for, and hear what they listen for.");
        quotes.get(BookTitle.MOCKING).add("The one thing that doesn't abide by majority rule is a person's conscience.");
        */
        //quotes.put(BookTitle.MOCKING, getQuotesMocking());
        // DO NOT CALL clear() on the string array. the Map will be pointing to an empty array
        //addQuotesMOCKING.clear();

        // Adding BookTitle.WATCH quotes
        Array<String> addQuotesWATCH = new Array<>();
        addQuotesWATCH.addAll(
                "\'Some people could look at a mud puddle and see an ocean with ships.\'",
                "\'There are years that ask questions and years that answer.\'",
                "\'De way you looked at me when Ah said whut Ah did. Yo’ face skeered me so bad till mah whiskers drawed up.\'",
                "\'If you kin see de light at daybreak, you don't keer if you die at dusk.\'");
        addQuotesWATCH.addAll(
                "\'Some people could look at a mud puddle and see an ocean with ships.\'",
                "\'There are years that ask questions and years that answer.\'",
                "\'De way you looked at me when Ah said whut Ah did. Yo’ face skeered me so bad till mah whiskers drawed up.\'",
                "\'If you kin see de light at daybreak, you don't keer if you die at dusk.\'");
        // Make "tea cake is so fine" less common than the other quotes
        addQuotesWATCH.add("\'Tea Cake is so fiiine.\' Hey wait. Who wrote this in?");
        quotes.put(BookTitle.WATCH, addQuotesWATCH);

        // Adding BookTitle.QUIXOTE quotes
        Array<String> addQuotesQUIXOTE = new Array<>();
        addQuotesQUIXOTE.addAll("\'There is no book so bad...that it does not have something good in it.\'",
                "\'Finally, from so little sleeping and so much reading, his brain dried up and he went completely out of his mind.\'",
                "\'Thou hast seen nothing yet.\'",
                "\'Hunger is the best sauce in the world.\'",
                "\'What man can pretend to know the riddle of a woman's mind?\'",
                "\'Wit and humor do not reside in slow minds\'",
                "\'Do you see over yonder, friend Sancho, thirty or forty hulking giants? I intend to do battle with them and slay them.\'");
        quotes.put(BookTitle.QUIXOTE, addQuotesQUIXOTE);
    }

    // Generate list of quotes from 'To Kill a Mockingbird'
    private static Array<String> getQuotesMocking() {
        Array<String> addQuotes = new Array<>();
        addQuotes.addAll("Hello", "Goodbye");
        return addQuotes;
    }

    /**
     * Retrieve a random quote text for @title using Random variable
     * @param title Source book for the quote
     * @param random Random variable to choose an index
     * @return A string containing a quote from @title
     */
    public static String getRandomQuote(BookTitle title, Random random) {
        // get a random index from 0 to number of quotes for @title
        if(!quotes.containsKey(title) || quotes.get(title).size == 0) {
            return "No quotes for " + title; // will call toString for BookTitle
        }
        int randomID = random.nextInt(quotes.get(title).size);
        return quotes.get(title).get(randomID);
    }
}
