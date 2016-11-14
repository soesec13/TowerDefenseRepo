package helper;

import javax.swing.*;
import java.io.File;
import java.net.URI;

/**
 * Created by sebi on 003 03/11/2016.
 */
public class FileChoosing {
    static {
        /*
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        }catch(Exception ex) {
            ex.printStackTrace();
        }
        */
    }

    public static File openFile(URI location) throws Exception {

        JFileChooser fc = new JFileChooser(new File(location));
        fc.requestFocus();
        fc.requestFocusInWindow();
        fc.setVisible(true);
        int res = fc.showOpenDialog(null);


        if (res != JFileChooser.APPROVE_OPTION) {
            throw new Exception("Canceled");
        }
        return fc.getSelectedFile();
    }

    public static File saveFile(URI location) throws Exception {

        JFileChooser fc = new JFileChooser(new File(location));
        fc.requestFocus();
        fc.requestFocusInWindow();
        fc.setVisible(true);
        int res = fc.showSaveDialog(null);


        if (res != JFileChooser.APPROVE_OPTION) {
            throw new Exception("Canceled");
        }
        return fc.getSelectedFile();
    }
}
