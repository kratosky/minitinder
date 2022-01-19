package simple;

import java.awt.Frame;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class MyFrame extends Frame{

    public MyFrame()
    {

        // 默认的窗体名称
        this.setTitle("显示一张图片");

        // 获得面板的实例
        MyPanel panel = new MyPanel();
        this.add(panel);
        this.addWindowListener(new WindowAdapter() {
            //设置关闭
            public void windowClosing(WindowEvent e) {
                System.exit(0);
            }
        });
        // 执行并构建窗体设定
        this.pack();
        this.setVisible(true);
    }

}