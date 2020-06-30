package vn.name.ChanhDai.QuanLySinhVien.view;

import javax.swing.*;
import java.io.File;

/**
 * vn.edu.hcmus.fit.sv18120113.QuanLySinhVien.view
 *
 * @created by ncdai3651408 - StudentID : 18120113
 * @date 6/30/20 - 9:48 PM
 * @description
 */
public class FileChooserView {
    // Create a file chooser
    final JFileChooser fc = new JFileChooser();

    public FileChooserView() {
        renderFileChooser();
    }

    private void renderFileChooser() {
        JFrame frame = new JFrame();

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // JFileChooser
        // https://docs.oracle.com/javase/tutorial/uiswing/components/filechooser.html#eg
        JButton button = new JButton("Click Me!");
        button.addActionListener(e -> {
            int returnVal = fc.showOpenDialog(frame);

            if (returnVal == JFileChooser.APPROVE_OPTION) {
                File file = fc.getSelectedFile();
                //This is where a real application would open the file.
                System.out.println("Opening : " + file.getAbsolutePath() + ".");
            } else {
                System.out.println("Open command cancelled by user.");
            }
        });
        frame.getContentPane().add(button);

        // 4. Size the frame.
        frame.pack();

        // 5. Show it.
        frame.setVisible(true);
    }
}
