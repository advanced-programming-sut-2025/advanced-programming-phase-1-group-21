package io.github.StardewValley.network;

/**
 * This is for network
 * Write this for classes that needs refreshing...
 *
 * For exmaple you are writing Class X, some data of it will change
 * Server Pokes you that some data in Class X is changed
 * So you should REQUEST it again from server... and update in View or What-ever
 *
 * maybe useless
 */
public interface Refreshable {
    void refresh();
}
