/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ucar.nc2.ui;

/**
 *
 * @author tkunicki
 * 
 * I use this to allow me to run ToolsUI in a debugger with the GeoTIFF IOSP
 * on the classpath...
 * 
 */
public class ToolsUIWrapper {
    
    public static void main(String[] args) {
        ToolsUI.main(args);
    }
}
