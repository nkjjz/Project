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
    public boolean qizi;
    public boolean qipandaxiao;
    public boolean xingqifang=false;

    public GameController(Chessboard chessboard) {
        this.chessboard = chessboard;
    }

    public List<String> loadGameFromFile(String path) {
        try {
            List<String> chessData = Files.readAllLines(Path.of(path));
            for (int i = 0; i < 8; i++) {
                for (int j = 0; j < 8; j++) {
                    if (chessData.get(i).charAt(j) != 'K' && chessData.get(i).charAt(j) != 'k' && chessData.get(i).charAt(j) != 'Q' && chessData.get(i).charAt(j) != 'q' && chessData.get(i).charAt(j) != 'R' && chessData.get(i).charAt(j) != 'r' && chessData.get(i).charAt(j) != 'P' && chessData.get(i).charAt(j) != 'p' && chessData.get(i).charAt(j) != 'N' && chessData.get(i).charAt(j) != 'n' && chessData.get(i).charAt(j) != 'B' && chessData.get(i).charAt(j) != 'b' && chessData.get(i).charAt(j) != '-') {
                        qizi = false;
                        JOptionPane.showMessageDialog(null,"棋子并非六种之一，棋子并非黑白棋子");
                        break;
                    } else {
                        qizi = true;
                    }
                }
            }
            for (int i = 0; i < 8; i++) {
                if (chessData.get(i).length() != 8) {
                    qipandaxiao = false;
                    JOptionPane.showMessageDialog(null,"棋盘并非8*8");
                    break;
                } else {
                    qipandaxiao = true;
                }
            }
            if (chessData.get(8).charAt(0)!='W'&&chessData.get(8).charAt(0)!='B'){
                xingqifang = false;
                JOptionPane.showMessageDialog(null,"缺少下一步行棋方");
            }else {
                xingqifang = true;
            }
            if (qizi && qipandaxiao&&xingqifang) {
                chessboard.loadGame(chessData);
                return chessData;
            }
        } catch(IOException e){
                e.printStackTrace();
            }
            return null;

    }

   public void saveFileData() {
       JFileChooser chooser = new JFileChooser();
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
