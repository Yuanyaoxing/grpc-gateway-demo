package com.frostwolf.util;

import com.github.brainlag.nsq.lookup.DefaultNSQLookup;
import com.github.brainlag.nsq.lookup.NSQLookup;

public class NSQLookupFactory {


    private static NSQLookup lookup;

    private static final String lock = "";

    private NSQLookupFactory() {}

    public static NSQLookup init() {
        if(null == lookup) {
            synchronized (lock) {
                if(null == lookup) {
                    lookup = new DefaultNSQLookup();
                }
            }
        }
        return lookup;
    }
}
