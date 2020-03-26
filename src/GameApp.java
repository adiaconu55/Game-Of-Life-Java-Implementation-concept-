import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.Random;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

public class GameApp {

    private static int[][] matrix;
    private static int n;
    private static int spawningChance;
    private static int[][] vecini = {{0,-1},{-1,-1},{-1,0},{-1,1},{0,1},{1,1},{1,0},{1,-1}};
    private static JFrame frame;
    private static JLabel img;
    private static JButton startReset;
    private static JTextArea boardsize;
    private static JLabel label1;
    private static JLabel label2;
    private static JLabel label3;
    private static JSpinner spinner;

    public static void Listeners(){
        startReset.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                n=Integer.parseInt(boardsize.getText());
                if((int)spinner.getValue()==0)
                    JOptionPane.showMessageDialog(frame,"Orice in afara de 0 pls :3");
                else{
                    spawningChance = (int)(100/(int)spinner.getValue());
                    System.out.println(n+"  "+spawningChance);
                    init();
                    createPNG();
                    img.setIcon(new ImageIcon(new ImageIcon("GrayScale.png").getImage().getScaledInstance(frame.getWidth()-200, frame.getHeight()-10, Image.SCALE_DEFAULT)));
                }
            }
        });
    }

    public static void main(String[] args) {
        frame = new JFrame("Jocul Vietii");
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setBounds(1000,100,800,600);
        img = new JLabel();
        startReset = new JButton("Start/Reset");
        startReset.setBounds(650,400,100,40);
        label2 = new JLabel("Chance of spawning");
        label3 = new JLabel("%");
        label3.setFont(new Font("Serif",Font.BOLD,20));
        label2.setBounds(640,160,150,30);
        label3.setBounds(755,200,20,20);
        spinner = new JSpinner();
        spinner.setBounds(650,200,100,25);
        label1 = new JLabel("Board size (n x n)");
        label1.setBounds(650,100,150,30);
        boardsize = new JTextArea();
        boardsize.setBounds(650,130,100,15);
        frame.add(label3);
        frame.add(spinner);
        frame.add(label2);
        frame.add(boardsize);
        frame.add(label1);
        frame.add(startReset);
        frame.add(img);
        frame.setResizable(false);
        frame.setVisible(true);
        n=100;
        spawningChance=0;
        init();
        createPNG();
        img.setIcon(new ImageIcon(new ImageIcon("GrayScale.png").getImage().getScaledInstance(frame.getWidth()-200, frame.getHeight()-10, Image.SCALE_DEFAULT)));
        Listeners();
        start();
    }

    public static void start(){
        while(true){
            try {
                TimeUnit.MILLISECONDS.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            update();
            createPNG();
            img.setIcon(new ImageIcon(new ImageIcon("GrayScale.png").getImage().getScaledInstance(frame.getWidth()-200, frame.getHeight()-10, Image.SCALE_DEFAULT)));
        }
    }

    public static void init(){
        matrix = new int[n][n];
        for(int i=0;i<n;i++)
            for(int j=0;j<n;j++){
                if(spawningChance!=0){
                    if((int)(Math.random()*spawningChance)==0)
                        matrix[i][j] = 1;
                }
            }
    }

    public static void update(){
        for(int i=1;i<n-1;i++)
            for(int j=1;j<n-1;j++) {
                int counter=0;
                if (matrix[i][j] == 1) {
                    for (int k = 0; k < 8; k++) {
                        if (matrix[i+vecini[k][0]][j+vecini[k][1]] == 1)
                            counter++;
                    }
                    if(counter!=2 && counter!=3)
                        matrix[i][j]=0;
                }
                else{
                    for (int k = 0; k < 8; k++) {
                        if (matrix[i+vecini[k][0]][j+vecini[k][1]] == 1)
                            counter++;
                    }
                    if(counter==3)
                        matrix[i][j]=1;
                }
            }
        for(int j=1;j<n-1;j++){
            int counter=0;
            if (matrix[0][j] == 1) {
                for (int k = 0; k < 8; k++) {
                    if (matrix[0+vecini[k][0]][j+vecini[k][1]] == 1)
                        counter++;
                    if(k==0)
                        k+=3;
                }
                if(counter!=2 && counter!=3)
                    matrix[0][j]=0;
            }
            else{
                for (int k = 0; k < 8; k++) {
                    if (matrix[0+vecini[k][0]][j+vecini[k][1]] == 1)
                        counter++;
                    if(k==0)
                        k+=3;
                }
                if(counter==3)
                    matrix[0][j]=1;
            }
        }
        for(int i=1;i<n-1;i++){
            int counter=0;
            if (matrix[i][n-1] == 1) {
                for (int k = 0; k < 8; k++) {
                    if (matrix[i+vecini[k][0]][n-1+vecini[k][1]] == 1)
                        counter++;
                    if(k==2)
                        k=5;
                }
                if(counter!=2 && counter!=3)
                    matrix[i][n-1]=0;
            }
            else{
                for (int k = 0; k < 8; k++) {
                    if (matrix[i+vecini[k][0]][n-1+vecini[k][1]] == 1)
                        counter++;
                    if(k==2)
                        k=5;
                }
                if(counter==3)
                    matrix[i][n-1]=1;
            }
        }
        for(int j=1;j<n-1;j++){
            int counter=0;
            if (matrix[n-1][j] == 1) {
                for (int k = 0; k < 5; k++) {
                    if (matrix[n-1+vecini[k][0]][j+vecini[k][1]] == 1)
                        counter++;
                }
                if(counter!=2 && counter!=3)
                    matrix[n-1][j]=0;
            }
            else{
                for (int k = 0; k < 5; k++) {
                    if (matrix[n-1+vecini[k][0]][j+vecini[k][1]] == 1)
                        counter++;
                }
                if(counter==3)
                    matrix[n-1][j]=1;
            }
        }
        for(int i=1;i<n-1;i++){
            int counter=0;
            if (matrix[i][0] == 1) {
                for (int k = 2; k < 7; k++) {
                    if (matrix[i+vecini[k][0]][0+vecini[k][1]] == 1)
                        counter++;
                }
                if(counter!=2 && counter!=3)
                    matrix[i][0]=0;
            }
            else{
                for (int k = 2; k < 7; k++) {
                    if (matrix[i+vecini[k][0]][0+vecini[k][1]] == 1)
                        counter++;
                }
                if(counter==3)
                    matrix[i][0]=1;
            }
        }
    }

    public static void showMatrix(){
        for(int i=0;i<n;i++){
            for(int j=0;j<n;j++)
                System.out.print(matrix[i][j]+" ");
            System.out.println();
        }
    }

    public static void createPNG(){
        try {
            BufferedImage image = new BufferedImage(n,n,1);
            for(int i=0; i<n; i++) {
                for(int j=0; j<n; j++) {
                    int a = 255;
                    if(matrix[i][j]==1)
                        a=0;
                    Color newColor = new Color(a,a,a);
                    image.setRGB(j,i,newColor.getRGB());
                }
            }
            File output = new File("GrayScale.png");
            ImageIO.write(image, "png", output);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

}
