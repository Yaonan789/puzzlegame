import jdk.nashorn.internal.scripts.JD;

import javax.swing.*;
import javax.swing.border.BevelBorder;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Objects;
import java.util.Random;

public class GameJFrame extends JFrame implements KeyListener, ActionListener {

    int[][] data=new int[4][4];
    int x=0;
    int y=0;
    String path="pzgame\\image\\animal\\animal3\\";
    int[][] win={
            {1,2,3,4},
            {5,6,7,8},
            {9,10,11,12},
            {13,14,15,0}
    };
    int step=0;
    JMenuItem replayItem=new JMenuItem("重新游戏");
    JMenuItem reLoginItem=new JMenuItem("重新登录");
    JMenuItem closeItem=new JMenuItem("关闭游戏");
    JMenuItem accountItem=new JMenuItem("公众号");
    public GameJFrame(){
        //初始化界面
        initJFrame();
        //初始化菜单
        initJmenuBar();
        //打乱图片
        initData();
        //初始化图片
        initImage();
        this.setVisible(true);
    }

    private void initData() {
        int[] tempArr={0,1,2,3,4,5,6,7,8,9,10,11,12,13,14,15};
        Random r=new Random();
        for (int i = 0; i < tempArr.length; i++) {
            int index=r.nextInt(tempArr.length);
            int temp=tempArr[i];
            tempArr[i]=tempArr[index];
            tempArr[index]=temp;
        }
        for(int i=0;i<tempArr.length;i++){
            if(tempArr[i]==0){
                x=i/4;
                y=i%4;
            }
            data[i/4][i%4]=tempArr[i];
        }
    }

    private void initImage() {
        //清空
        this.getContentPane().removeAll();
        if(victory()){
            JLabel winJLabel=new JLabel(new ImageIcon("pzgame\\image\\win.png"));
            winJLabel.setBounds(203,283,197,73);
            this.getContentPane().add(winJLabel);
        }
        JLabel stepCount =new JLabel("步数"+step);
        stepCount.setBounds(50,30,100,20);
        this.getContentPane().add(stepCount);
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                int num=data[i][j];
                JLabel jLabel1=new JLabel(new ImageIcon(path+num+".jpg"));
                jLabel1.setBounds(105*j+83,105*i+134,105,105);
                jLabel1.setBorder(new BevelBorder(1));
                this.getContentPane().add(jLabel1);
            }
        }

        JLabel background=new JLabel(new ImageIcon("pzgame\\image\\background.png"));
        background.setBounds(40,40,508,560);
        this.getContentPane().add(background);
        //刷新
        this.getContentPane().repaint();

    }

    private void initJmenuBar() {
        JMenuBar jMenuBar = new JMenuBar();

        JMenu functionJMenu = new JMenu("功能");
        JMenu aboutJMenu = new JMenu("关于我们");

        functionJMenu.add(replayItem);
        functionJMenu.add(reLoginItem);
        functionJMenu.add(closeItem);
        aboutJMenu.add(accountItem);

        replayItem.addActionListener(this);
        reLoginItem.addActionListener(this);
        closeItem.addActionListener(this);
        accountItem.addActionListener(this);
        jMenuBar.add(functionJMenu);
        jMenuBar.add(aboutJMenu);

        this.setJMenuBar(jMenuBar);
    }

    private void initJFrame() {
        this.setSize(603,750);
        this.setTitle("拼图小游戏单机版1.0");
        this.setAlwaysOnTop(true);
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(3);
        this.setLayout(null);
        this.addKeyListener(this);
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        int code=e.getKeyCode();
        if(code==65){
            this.getContentPane().removeAll();
            JLabel all=new JLabel(new ImageIcon(path+"all.jpg"));
            all.setBounds(83,134,420,420);
            this.getContentPane().add(all);
            JLabel background=new JLabel(new ImageIcon("pzgame\\image\\background.png"));
            background.setBounds(40,40,508,560);
            this.getContentPane().add(background);
            //刷新
            this.getContentPane().repaint();
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        if(victory()) return;

        int code=e.getKeyCode();
        System.out.println(code);
        if(code==39){
            System.out.println("向左移动");
            if(y==3) return;
            data[x][y]=data[x][y+1];
            data[x][y+1]=0;
            y++;
            step++;
            initImage();
        }else if(code==40){
            System.out.println("向上移动");
            if(x==3) return;
            data[x][y]=data[x+1][y];
            data[x+1][y]=0;
            x++;
            step++;
            initImage();
        }else if(code==37){
            System.out.println("向右移动");
            if(y==0) return;
            data[x][y]=data[x][y-1];
            data[x][y-1]=0;
            y--;
            step++;
            initImage();
        }else if(code==38){
            System.out.println("向下移动");
            if(x==0) return;
            data[x][y]=data[x-1][y];
            data[x-1][y]=0;
            x--;
            step++;
            initImage();
        }else if(code==65){
            initImage();
        }else if(code==87){
            data = new int[][]{
                    {1,2,3,4},
                    {5,6,7,8},
                    {9,10,11,12},
                    {13,14,15,0}
            };
            initImage();
        }
    }

    public boolean victory(){
        for (int i = 0; i < data.length; i++) {
            for (int j = 0; j < data[i].length; j++) {
                if(data[i][j]!=win[i][j]) return false;
            }
        }
        return true;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Object obj=  e.getSource();
        if(replayItem == obj){
            System.out.println("重新游戏");
            step=0;
            initData();
            initImage();
        }else if(obj==reLoginItem){
            System.out.println("重新登录");
            this.setVisible(false);
        }else if(obj==closeItem){
            System.out.println("关闭游戏");
            System.exit(0);
        }else if(obj==accountItem){
            System.out.println("公众号");
            JDialog jDialog=new JDialog();
            JLabel jLabel1=new JLabel(new ImageIcon("pzgame\\image\\about.png"));
            jLabel1.setBounds(0,0,258,258);
            jDialog.getContentPane().add(jLabel1);
            jDialog.setSize(344,344);
            jDialog.setAlwaysOnTop(true);
            jDialog.setLocationRelativeTo(null);
            jDialog.setModal(true);
            jDialog.setVisible(true);
        }

    }
}