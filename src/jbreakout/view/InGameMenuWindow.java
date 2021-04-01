package jbreakout.view;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import jbreakout.controller.ControllerForView;
import jbreakout.util.Config;

public class InGameMenuWindow extends javax.swing.JFrame {
	
	private static InGameMenuWindow instance = null;

    /**
     * Creates new form InGameMenuWindow
     */
    public InGameMenuWindow() {
        initComponents();
        audioCheckBox.setSelected(Config.getInstance().soundIsEnabled());
        setLocationRelativeTo(null);
        addWindowListener(new WindowAdapter() {
        	public void windowClosing(WindowEvent evt) {
        		onExit();
    	    }
    	});
    }
    private void onExit() {
    	this.setVisible(false);
    	GameWindow.getInstance().setVisible(true);
    }

    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">                          
    private void initComponents() {

        optionTitleLabel = new javax.swing.JLabel();
        saveGameButton = new javax.swing.JButton();
        audioCheckBox = new javax.swing.JCheckBox();
        quitGameButton = new javax.swing.JButton();
        resumeGameButton = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE);
        setResizable(false);

        optionTitleLabel.setFont(new java.awt.Font("Tahoma", 0, 36)); // NOI18N
        optionTitleLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        optionTitleLabel.setText("Select an option:");

        saveGameButton.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        saveGameButton.setText("Save Game");
        saveGameButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                saveGameButtonActionPerformed(evt);
            }
        });

        audioCheckBox.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        audioCheckBox.setText("Audio on/off");
        audioCheckBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                audioCheckBoxActionPerformed(evt);
            }
        });

        quitGameButton.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        quitGameButton.setText("Quit Game");
        quitGameButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                quitGameButtonActionPerformed(evt);
            }
        });

        resumeGameButton.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        resumeGameButton.setText("Resume Game");
        resumeGameButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                resumeGameButtonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(optionTitleLabel, javax.swing.GroupLayout.DEFAULT_SIZE, 326, Short.MAX_VALUE)
                    .addComponent(saveGameButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(quitGameButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(resumeGameButton, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(audioCheckBox, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(optionTitleLabel)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 77, Short.MAX_VALUE)
                .addComponent(audioCheckBox)
                .addGap(18, 18, 18)
                .addComponent(resumeGameButton)
                .addGap(18, 18, 18)
                .addComponent(quitGameButton)
                .addGap(18, 18, 18)
                .addComponent(saveGameButton)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>                        

    private void saveGameButtonActionPerformed(java.awt.event.ActionEvent evt) {                                               
        int saveSlot = ControllerForView.getInstance().firstSlotFreeNumber();
        
        if(saveSlot == 0) {
        	// None of the save slots are available.
        	saveGameButton.setText("NO SAVE SLOTS ARE FREE!");
        } else {
        	ControllerForView.getInstance().saveGameToSlotNumber(saveSlot);
        	saveGameButton.setText("Game saved to slot number " + saveSlot + ".");
        }
    }                                              

    private void audioCheckBoxActionPerformed(java.awt.event.ActionEvent evt) {                                              
        Config.getInstance().setSoundEnabled(audioCheckBox.isSelected());
    }                                             

    private void quitGameButtonActionPerformed(java.awt.event.ActionEvent evt) {                                               
        GameWindow.getInstance().dispose();
    	dispose();
    }                                              

    private void resumeGameButtonActionPerformed(java.awt.event.ActionEvent evt) {                                                 
    	onExit();
    }                                                

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(InGameMenuWindow.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(InGameMenuWindow.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(InGameMenuWindow.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(InGameMenuWindow.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new InGameMenuWindow().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify                     
    private javax.swing.JCheckBox audioCheckBox;
    private javax.swing.JLabel optionTitleLabel;
    private javax.swing.JButton quitGameButton;
    private javax.swing.JButton resumeGameButton;
    private javax.swing.JButton saveGameButton;
    // End of variables declaration              
    
    public static InGameMenuWindow getInstance() {
    	if(instance == null)
    		instance = new InGameMenuWindow();
    	
    	return instance;
    }
    
}
