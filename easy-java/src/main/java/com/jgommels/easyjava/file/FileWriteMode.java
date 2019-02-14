package com.jgommels.easyjava.file;

/**
 * An enum declaring the set of possible file write modes.
 */
public enum FileWriteMode {
    /** Indicates that contents will be appended to the existing file, or a new file will be created if one does not exist. */
    APPEND,

    /** Indicates that the file contents will be replaced. */
    REPLACE
}