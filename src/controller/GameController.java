package controller;

import model.ChessColor;
import view.ChessGameFrame;
import view.Chessboard;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class GameController {
    private Chessboard chessboard;

    public GameController(Chessboard chessboard) {
        this.chessboard = chessboard;
    }

    public List<String> loadGameFromFile(String path) {
        try {
            List<String> chessData = Files.readAllLines(Path.of(path));
            chessboard.loadGame(chessData);
            return chessData;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

   public void saveFileData() {
       JFileChooser chooser = new JFileChooser();
       //后缀名过滤器
       FileNameExtensionFilter filter = new FileNameExtensionFilter(
               "(*.txt)", "txt");
       chooser.setFileFilter(filter);
       int option = chooser.showSaveDialog(null);
       if(option==JFileChooser.APPROVE_OPTION){
           File file = chooser.getSelectedFile();

           String fname = chooser.getName(file);

           if(!fname.contains(".txt")){
               file = new File(chooser.getCurrentDirectory(),fname+".txt");
               System.out.println("renamed");
               System.out.println(file.getName());
           }

           try {
               FileOutputStream fos = new FileOutputStream(file);
               String a = ClickController.jiluQiJu(chessboard.getChessComponents());

               for (int i = 0; i < a.length(); i++) {
                   fos.write(a.charAt(i));
               }

               fos.close();

           } catch (IOException e) {
               System.err.println("IO异常");
               e.printStackTrace();
           }
       }

   }

}
