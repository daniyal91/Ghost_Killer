package misc;

import java.io.File;

import javax.swing.JFileChooser;

/**
 * Class used for utility functions.
 *
 * @author SnapDragon
 *
 */
public class Utils {

    /**
     * Wrapper around JFileChooser.
     *
     * @return The path of the selected file, or null if no file selected.
     * @roseuid 5837CF4012CF
     */
    public static String selectFile() {
        JFileChooser fileChooser = new JFileChooser();
        File currentDir = new File(System.getProperty("user.dir"));
        fileChooser.setCurrentDirectory(currentDir);
        if (fileChooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
            return fileChooser.getSelectedFile().getName();
        } else {
            return null;
        }

    }

}
