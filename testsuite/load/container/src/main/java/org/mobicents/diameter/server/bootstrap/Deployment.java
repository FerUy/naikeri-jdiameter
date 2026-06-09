package org.mobicents.diameter.server.bootstrap;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
/**
 *
 * @author kulikov
 */
public class Deployment {

    private File file;
    private boolean isDirectory;
    private long lastModified;
    
    public Deployment(File file) {
        this.file = file;
        this.isDirectory = file.isDirectory();
        this.lastModified = file.lastModified();
    }
    
    public boolean isDirectory() {
        return this.isDirectory;
    }
    
    public URL getURL() {
        try {
            return file.toURI().toURL();
        } catch (MalformedURLException e) {
            return null;
        }
    }
    
    public long lastModified() {
        return lastModified;
    }
    
    public void update(long lastModified) {
        this.lastModified = lastModified;
    }
}
