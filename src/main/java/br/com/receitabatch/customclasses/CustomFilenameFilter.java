package br.com.receitabatch.customclasses;

import java.io.File;
import java.io.FilenameFilter;

public class CustomFilenameFilter implements FilenameFilter {

    private final String pattern;

    public CustomFilenameFilter(String pattern) {
        this.pattern = pattern;
    }

    @Override
    public boolean accept(File directory, String fileName) {
        return fileName.matches(this.pattern);
    }

}