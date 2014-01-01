/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * MainFrame.java
 *
 * Created on 2013-11-25, 7:48:23
 */
package ui;

import domain.ResourceInfo;
import java.awt.CardLayout;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import service.Brush;

/**
 *
 * @author yang
 */
public class MainFrameTest extends javax.swing.JFrame {

    private Brush brush;
    private Runnable run = null;//更新组件的线程
    private CardLayout card;
    private Action action;
    private boolean flagComment = true;
    private List<String> logList = new ArrayList<String>();
    private List<String> listDownUrl;
    private boolean isLogin = false;
    private boolean flagDownload = true;

    enum Action {

        NOTING, LOGIN_SCCESS, SHOW_MESSAGE, FETCH_SCCESS, UPDATE_TIME, WORK_LOG,
        GET_RESOURCE_URL_SUCCESS
    };
    private String message;

    /** Creates new form MainFrame */
    public MainFrameTest() {
        initComponents();
        this.setLocationRelativeTo(null);
        this.setTitle("csdn回血");
        card = (CardLayout) jPanel3.getLayout();
        //自动登陆线程
       

        //更新UI线程
        run = new Runnable() {

            public void run() {
                switch (action) {
                    case LOGIN_SCCESS:
                        card.show(jPanel3, "card5");
                        login_label.setText(brush.getUsername() + " 已登陆");
                        action = Action.NOTING;
                        updateLog(brush.getUsername() + " 已登陆！");
                        break;
                    case SHOW_MESSAGE:
                        JOptionPane.showMessageDialog(null, message);
                        action = Action.NOTING;
                        break;
//                    case WORK_LOG:
//                        logList.add(message);
//                        log_list.setListData(logList.toArray());
//                        resource_time_label.setText("70");
                    //这里没有break，要刷新待评价列表
                    case FETCH_SCCESS:
                        //更新list
                        jList1.setListData(brush.getLists().toArray());
                        resource_count_label.setText(brush.getLists().size() + "个");
                        jList1.invalidate();
                        updateLog("成功获取待评价列表！");
                        action = Action.NOTING;
                        break;
//                    case UPDATE_TIME:
//                        resource_time_label.setText(message);
//                        action = Action.NOTING;
//                        break;
                    case GET_RESOURCE_URL_SUCCESS:
                        resouce_url.setText(message);
                        updateLog("成功获取下载地址：" + message);
                        action = Action.NOTING;
                        break;

                }
            }
        };

    }

    private void loginInThread(final String username,final String password){
         new Thread() {

            public void run() {
                brush = new Brush(username,password);
                boolean ret = brush.login();
                if (ret) {
                    action = Action.LOGIN_SCCESS;
                    isLogin = true;
                } else {
                    action = Action.SHOW_MESSAGE;
                    message = "登陆失败!";
                }
                SwingUtilities.invokeLater(run);
            }
        }.start();
    }

    private void updateLog(String message) {
        logList.add(message + " --- " + new SimpleDateFormat("yyyyMMdd HH:mm:ss").format(new Date()));
        log_list.setListData(logList.toArray());
        log_list.invalidate();
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel2 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jList1 = new javax.swing.JList();
        jScrollPane2 = new javax.swing.JScrollPane();
        log_list = new javax.swing.JList();
        jScrollPane3 = new javax.swing.JScrollPane();
        jList_down_url = new javax.swing.JList();
        jPanel1 = new javax.swing.JPanel();
        jPanel3 = new javax.swing.JPanel();
        jPanel7 = new javax.swing.JPanel();
        jtf_username = new javax.swing.JTextField();
        jtf_password = new javax.swing.JTextField();
        jButton1 = new javax.swing.JButton();
        jPanel6 = new javax.swing.JPanel();
        login_label = new javax.swing.JLabel();
        jButton5 = new javax.swing.JButton();
        jPanel4 = new javax.swing.JPanel();
        jPanel8 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        resource_count_label = new javax.swing.JLabel();
        jPanel9 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        resource_time_label = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jPanel10 = new javax.swing.JPanel();
        resouce_url = new javax.swing.JTextField();
        jPanel5 = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        jTextField3 = new javax.swing.JTextField();
        jButton2 = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();
        jButton4 = new javax.swing.JButton();
        jPanel11 = new javax.swing.JPanel();
        parse_path_tv = new javax.swing.JTextField();
        jButton6 = new javax.swing.JButton();
        jButton7 = new javax.swing.JButton();
        jLabel4 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jList1.setBackground(new java.awt.Color(255, 255, 255));
        jList1.setBorder(javax.swing.BorderFactory.createTitledBorder("带评价的资源"));
        jList1.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        jScrollPane1.setViewportView(jList1);

        log_list.setBackground(new java.awt.Color(255, 255, 255));
        log_list.setBorder(javax.swing.BorderFactory.createTitledBorder("日志"));
        jScrollPane2.setViewportView(log_list);

        jList_down_url.setBackground(new java.awt.Color(255, 255, 255));
        jList_down_url.setBorder(javax.swing.BorderFactory.createTitledBorder("待下载资源"));
        jScrollPane3.setViewportView(jList_down_url);

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 263, Short.MAX_VALUE)
                    .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 263, Short.MAX_VALUE)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 263, Short.MAX_VALUE)))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 325, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 118, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 114, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        jPanel1.setLayout(new java.awt.GridLayout(4, 0));

        jPanel3.setBorder(javax.swing.BorderFactory.createTitledBorder("登陆"));
        jPanel3.setLayout(new java.awt.CardLayout());

        jPanel7.setLayout(new java.awt.GridLayout(3, 1, 3, 3));

        jtf_username.setText("用户名");
        jPanel7.add(jtf_username);

        jtf_password.setText("密码");
        jPanel7.add(jtf_password);

        jButton1.setText("登陆");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });
        jPanel7.add(jButton1);

        jPanel3.add(jPanel7, "card6");

        login_label.setText("某人已经登陆");
        jPanel6.add(login_label);

        jButton5.setText("退出");
        jButton5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton5ActionPerformed(evt);
            }
        });
        jPanel6.add(jButton5);

        jPanel3.add(jPanel6, "card5");

        jPanel1.add(jPanel3);

        jPanel4.setBorder(javax.swing.BorderFactory.createTitledBorder("资源信息"));
        jPanel4.setLayout(new java.awt.GridLayout(3, 0));

        jLabel1.setText("待评价资源个数：");
        jPanel8.add(jLabel1);

        resource_count_label.setText("x个");
        jPanel8.add(resource_count_label);

        jPanel4.add(jPanel8);

        jLabel2.setText("评价倒计时：");
        jPanel9.add(jLabel2);

        resource_time_label.setText("70");
        jPanel9.add(resource_time_label);

        jLabel5.setText("秒");
        jPanel9.add(jLabel5);

        jLabel6.setBackground(new java.awt.Color(102, 255, 255));
        jLabel6.setForeground(new java.awt.Color(0, 0, 204));
        jLabel6.setText("注意：默认70秒进行一次评价");
        jPanel9.add(jLabel6);

        jPanel4.add(jPanel9);

        javax.swing.GroupLayout jPanel10Layout = new javax.swing.GroupLayout(jPanel10);
        jPanel10.setLayout(jPanel10Layout);
        jPanel10Layout.setHorizontalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(resouce_url, javax.swing.GroupLayout.DEFAULT_SIZE, 180, Short.MAX_VALUE)
        );
        jPanel10Layout.setVerticalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel10Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(resouce_url, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel4.add(jPanel10);

        jPanel1.add(jPanel4);

        jPanel5.setBorder(javax.swing.BorderFactory.createTitledBorder("操作"));
        jPanel5.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.CENTER, 8, 8));

        jLabel3.setText("资源ID");
        jPanel5.add(jLabel3);

        jTextField3.setText("请输入ID");
        jPanel5.add(jTextField3);

        jButton2.setText("下载");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });
        jPanel5.add(jButton2);

        jButton3.setText("获取待评列表");
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });
        jPanel5.add(jButton3);

        jButton4.setText("启动评价");
        jButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton4ActionPerformed(evt);
            }
        });
        jPanel5.add(jButton4);

        jPanel1.add(jPanel5);

        jPanel11.setBorder(javax.swing.BorderFactory.createTitledBorder("解析并下载"));

        parse_path_tv.setPreferredSize(new java.awt.Dimension(180, 21));
        jPanel11.add(parse_path_tv);

        jButton6.setText("解析");
        jButton6.setPreferredSize(new java.awt.Dimension(100, 23));
        jButton6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton6ActionPerformed(evt);
            }
        });
        jPanel11.add(jButton6);

        jButton7.setText("开始下载");
        jButton7.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton7ActionPerformed(evt);
            }
        });
        jPanel11.add(jButton7);

        jLabel4.setBackground(new java.awt.Color(102, 255, 255));
        jLabel4.setForeground(new java.awt.Color(0, 0, 204));
        jLabel4.setText("注意：默认每隔60s下载一个");
        jPanel11.add(jLabel4);

        jPanel1.add(jPanel11);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 192, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(10, 10, 10)
                        .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, 579, Short.MAX_VALUE))
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        // TODO add your handling code here:
        if (isLogin) {
            try {
                String resoucePath = brush.downResoucesByResourceID(jTextField3.getText().toString());
                if (null == resoucePath || "".equals(resoucePath)) {
                    action = Action.SHOW_MESSAGE;
                    message = "获取路径失败";
                } else {
                    action = Action.GET_RESOURCE_URL_SUCCESS;
                    message = resoucePath;
                }
            } catch (IOException ex) {
                ex.printStackTrace();
                action = Action.SHOW_MESSAGE;
                message = "获取路径失败";
            } finally {
                SwingUtilities.invokeLater(run);
            }
        } else {
            JOptionPane.showMessageDialog(null, "还没有登录啊 ！！！");
        }

    }//GEN-LAST:event_jButton2ActionPerformed

    private void jButton5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton5ActionPerformed
        // TODO add your handling code here:
        CardLayout layout = (CardLayout) jPanel3.getLayout();
        layout.show(jPanel3, "card6");
    }//GEN-LAST:event_jButton5ActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:
        String username = jtf_username.getText().trim();
        String password = jtf_password.getText().trim();
        loginInThread(username, password);
//        CardLayout layout = (CardLayout) jPanel3.getLayout();
//        layout.show(jPanel3, "card5");
//        String loginUser = "这里将会有返回";
//        login_label.setText(loginUser);
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        new Thread() {

            public void run() {
                try {
                    jButton3.setEnabled(false);
                    brush.fetchNeedCommentList();
                    action = Action.FETCH_SCCESS;
                } catch (IOException ex) {
                    ex.printStackTrace();
                    action = Action.SHOW_MESSAGE;
                    message = "抓取信息失败啦...";
                } finally {
                    SwingUtilities.invokeLater(run);
                    jButton3.setEnabled(true);
                }
            }
        }.start();

    }//GEN-LAST:event_jButton3ActionPerformed

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed
        // TODO add your handling code here:
        JButton button = (JButton) evt.getSource();
        if ("启动评价".equals(button.getText())) {
            jButton4.setText("暂停评价");
            flagComment = true;
            new Thread() {

                public void run() {
                    while (flagComment && brush.getLists().size() > 0) {
                        //评价
                        ResourceInfo info = brush.getLists().get(0);
                        boolean ret = brush.commentById(info.getUrl());
                        if (ret) {
                            //action = Action.WORK_LOG;
                            message = info.getName() + "已经被评价啦。";
                            //成功了就删掉列表
                            brush.getLists().remove(0);
                            // System.out.println(message);
                            updateLog(message);
                            resource_time_label.setText("70");
                            jList1.setListData(brush.getLists().toArray());
                            resource_count_label.setText(brush.getLists().size() + "个");
                            jList1.invalidate();
                            //action = Action.NOTING;

                        } else {
                            updateLog(info.getName() + " 评价的时候出现错误！！");
                            resource_time_label.setText("70");
                            //错了继续再来。
                        }
                        try {
                            while (true) {
                                message = (Integer.parseInt(resource_time_label.getText()) - 1) + "";
                                Thread.sleep(1000);
                                resource_time_label.setText(message);
                                if (Integer.parseInt(message) <= 0 || !flagComment) {
                                    break;
                                }
                            }
                        } catch (InterruptedException ex) {
                            ex.printStackTrace();
                        }
                    }
                    if (flagComment == true) {
                        updateLog("已经没有东西好评价的啦。");
                    }
                    jButton4.setText("启动评价");
                }
            }.start();
        } else {
            flagComment = false;
            jButton4.setText("启动评价");
            updateLog("已经停止评价。可能需要等上60秒再开始操作啦。");
        }

    }//GEN-LAST:event_jButton4ActionPerformed

    private void jButton6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton6ActionPerformed
        // TODO add your handling code here:、
        String path = this.parse_path_tv.getText().toString();
        if (null == path || "".equals(path)) {
            JOptionPane.showMessageDialog(null, "请输入需要解析的连接。");
            return;
        } else {
            if (isLogin) {
                try {
                    listDownUrl = new ArrayList<String>(brush.resoucePage2RescourceList(path, false));
                    jList_down_url.setListData(listDownUrl.toArray());
                    jList_down_url.invalidate();
                    updateLog(path + " 成功解析完成。");
                } catch (IOException ex) {
                    ex.printStackTrace();
                    updateLog(path + " 解析失败。");
                }
            }
        }
    }//GEN-LAST:event_jButton6ActionPerformed

    private void jButton7ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton7ActionPerformed
        // TODO add your handling code here:
        if (jButton7.getText().equals("开始下载")) {
            jButton7.setText("停止下载");
            new Thread() {

                public void run() {
                    while (flagDownload && listDownUrl.size() > 0) {
                        String path = listDownUrl.get(0);
                        try {
                            if (!"".equals(brush.downResouce(path))) {
                                updateLog(path + "下载成功");
                            }
                            Thread.sleep(60 * 1000);
                        } catch (Exception ex) {
                            ex.printStackTrace();
                            updateLog("下载" + path + "出现异常！");
                        } finally {
                            //不管下载成功与否都直接pass
                            listDownUrl.remove(path);//去除重复
                            jList_down_url.setListData(listDownUrl.toArray());
                        }
                    }
                    jButton7.setText("开始下载");
                    if (flagDownload) {
                        updateLog("已经下载好啦！");
                    } else {
                        updateLog("暂停下载啦！");
                    }
                }
            }.start();
        } else {
            jButton7.setText("开始下载");
            flagDownload = false;
            updateLog("已经停止下载。为了安全，还是等上60秒再开始操作啦。");
        }
    }//GEN-LAST:event_jButton7ActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        String windows = "com.sun.java.swing.plaf.windows.WindowsLookAndFeel";
        try {
            UIManager.setLookAndFeel(windows);
        } catch (Exception ex) {
            Logger.getLogger(MainFrameTest.class.getName()).log(Level.SEVERE, null, ex);
        }
        java.awt.EventQueue.invokeLater(new Runnable() {

            public void run() {
                new MainFrameTest().setVisible(true);
            }
        });
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JButton jButton5;
    private javax.swing.JButton jButton6;
    private javax.swing.JButton jButton7;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JList jList1;
    private javax.swing.JList jList_down_url;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel10;
    private javax.swing.JPanel jPanel11;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JPanel jPanel9;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JTextField jTextField3;
    private javax.swing.JTextField jtf_password;
    private javax.swing.JTextField jtf_username;
    private javax.swing.JList log_list;
    private javax.swing.JLabel login_label;
    private javax.swing.JTextField parse_path_tv;
    private javax.swing.JTextField resouce_url;
    private javax.swing.JLabel resource_count_label;
    private javax.swing.JLabel resource_time_label;
    // End of variables declaration//GEN-END:variables
}
