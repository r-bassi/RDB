package rdb.ui;

import javax.swing.JLabel;
import javax.swing.JTextField;
import rdb.exception.InputEmptyException;
import rdb.exception.InputFormatException;
import rdb.exception.InputOverflowException;
import rdb.model.Player;
import rdb.model.Table;
import rdb.model.low.Char;
import rdb.model.low.Column;
import rdb.model.low.Integer;
import rdb.model.low.Real;
import static rdb.shared.Constants.NUL_STRING;
import rdb.shared.Errors;
import rdb.util.Transcriber;

public class PlayerDialog extends javax.swing.JDialog {
  private static final Table TABLE = Player.NUL;
  private final JTextField[] fields;
  private final Player model;
  private Object fieldData;

  public PlayerDialog(java.awt.Frame parent, Player model) {
    super(parent, true);
    initComponents();
    this.fields = new JTextField[] {
      this.field0, this.field1, this.field2, this.field3, this.field4,
      this.field5, this.field6, this.field7, this.field8, this.field9
    };
    JTextField[] primaryFields
        = new JTextField[Player.NUL.getTablePrimaryKeys().length];
    System.arraycopy(this.fields, 0, primaryFields, 0, primaryFields.length);
    if (model != null)
      for (int i = model.getId() == NUL_STRING
          ? 0 : primaryFields.length; i < this.fields.length; i++)
        this.fields[i].setEditable(true);
    else {
      for (JTextField field : primaryFields) field.setEditable(true);
      model = new Player();
    }
    this.model = model;
    populateLabels();
    populateFields();
    setSize(this.getPreferredSize());
    setLocationRelativeTo(parent);
    labelStatus.setText("");
  }

  public Player getModel() {
    return this.model;
  }

  public boolean isModified() {
    return this.buttonReset.isEnabled();
  }

  public boolean isCancelled() {
    return !this.buttonCancel.isEnabled();
  }

  private void populateLabels() {
    JLabel[] labels = new JLabel[] {
      this.label0, this.label1, this.label2, this.label3, this.label4,
      this.label5, this.label6, this.label7, this.label8, this.label9
    };
    for (int i = 0; i < labels.length; i++)
      labels[i].setText(TABLE.getTableColumns()[i].getLongName());
  }

  private void populateFields() {
    int index = 0;
    this.fields[index].setText(Transcriber.toField(model.getId()));
    this.fields[++index].setText(Transcriber.toField(model.getPasscode()));
    this.fields[++index].setText(Transcriber.toField(model.getName()));
    this.fields[++index].setText(Transcriber.toField(model.getAge()));
    this.fields[++index].setText(Transcriber.toField(model.getHeight()));
    this.fields[++index].setText(Transcriber.toField(model.getWeight()));
    this.fields[++index].setText(Transcriber.toField(model.getWinshare()));
    this.fields[++index].setText(Transcriber.toField(model.getDraftYear()));
    this.fields[++index].setText(Transcriber.toField(model.getCoachId()));
    this.fields[++index].setText(Transcriber.toField(model.getAgentId()));
    setModified(false);
  }

  private void handleFieldChange() {
    for (int i = 0; i < this.fields.length; i++)
      if (this.fields[i].isEditable()) {
        if (!parseFields(i)) return;
        updateModified(i);
      }
    this.labelStatus.setText("");
  }

  private boolean parseFields(int index) {
    Column column = TABLE.getTableColumns()[index];
    String string = this.fields[index].getText();
    int typeId = column.getType().getId();
    boolean emptyAllowed = Transcriber.isEmptyAllowed(TABLE, column);
    try {
      if (typeId == Integer.DEFAULT.getId())
        this.fieldData = Transcriber.parseInt(string, emptyAllowed);
      else if (typeId == Real.DEFAULT.getId())
        this.fieldData = Transcriber.parseDouble(string, emptyAllowed);
      else if (typeId == Char.DEFAULT.getId())
        this.fieldData = Transcriber.parseString(string, emptyAllowed);
      return true;
    } catch (InputEmptyException exception) {
      complain(index, Errors.MSG_EMPTY);
    } catch (InputFormatException exception) {
      complain(index, "Invalid " + column.getType().getName() + ".");
    } catch (InputOverflowException exception) {
      complain(index, Errors.MSG_OVERFLOW);
    }
    return false;
  }

  private void updateModified(int index) {
    if (!this.buttonSet.isEnabled()) {
      Object modelData = getModelData(index);
      if (this.fieldData == null) {
        if (modelData != null)
          setModified(modelData.equals(this.fieldData));
      } else setModified(!this.fieldData.equals(modelData));
    }
  }

  private void complain(int index, String message) {
    this.buttonSet.setEnabled(false);
    this.labelStatus.setText("Field " + (index + 1) + ": " + message);
  }

  private void save() {
    int index = 0;
    this.model.setId(Transcriber.toString(
        this.fields[index].getText()));
    this.model.setPasscode(Transcriber.toString(
        this.fields[++index].getText()));
    this.model.setName(Transcriber.toString(
        this.fields[++index].getText()));
    this.model.setAge(Transcriber.toInt(
        this.fields[++index].getText()));
    this.model.setHeight(Transcriber.toDouble(
        this.fields[++index].getText()));
    this.model.setWeight(Transcriber.toDouble(
        this.fields[++index].getText()));
    this.model.setWinshare(Transcriber.toDouble(
        this.fields[++index].getText()));
    this.model.setDraftYear(Transcriber.toInt(
        this.fields[++index].getText()));
    this.model.setCoachId(Transcriber.toInt(
        this.fields[++index].getText()));
    this.model.setAgentId(Transcriber.toInt(
        this.fields[++index].getText()));
  }

  private Object getModelData(int index) {
    switch (index) {
      case 0: return model.getId();
      case 1: return model.getPasscode();
      case 2: return model.getName();
      case 3: return model.getAge();
      case 4: return model.getHeight();
      case 5: return model.getWeight();
      case 6: return model.getWinshare();
      case 7: return model.getDraftYear();
      case 8: return model.getCoachId();
      case 9: return model.getAgentId();
    }
    throw new ArrayIndexOutOfBoundsException(index);
  }

  private void setModified(boolean modified) {
    this.buttonSet.setEnabled(modified);
    this.buttonReset.setEnabled(modified);
  }

  // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
  private void initComponents() {

    label0 = new javax.swing.JLabel();
    label1 = new javax.swing.JLabel();
    label2 = new javax.swing.JLabel();
    label3 = new javax.swing.JLabel();
    label4 = new javax.swing.JLabel();
    label5 = new javax.swing.JLabel();
    label6 = new javax.swing.JLabel();
    label7 = new javax.swing.JLabel();
    label8 = new javax.swing.JLabel();
    label9 = new javax.swing.JLabel();
    field0 = new javax.swing.JTextField();
    field1 = new javax.swing.JTextField();
    field2 = new javax.swing.JTextField();
    field3 = new javax.swing.JTextField();
    field4 = new javax.swing.JTextField();
    field5 = new javax.swing.JTextField();
    field6 = new javax.swing.JTextField();
    field7 = new javax.swing.JTextField();
    field8 = new javax.swing.JTextField();
    field9 = new javax.swing.JTextField();
    buttonSet = new javax.swing.JButton();
    buttonReset = new javax.swing.JButton();
    buttonCancel = new javax.swing.JButton();
    labelStatus = new javax.swing.JLabel();

    setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
    setTitle(TABLE.getTableName());
    setModal(true);
    setResizable(false);
    addWindowListener(new java.awt.event.WindowAdapter() {
      public void windowClosing(java.awt.event.WindowEvent evt) {
        formWindowClosing(evt);
      }
    });

    label0.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
    label0.setText("label0");

    label1.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
    label1.setText("label1");

    label2.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
    label2.setText("label2");

    label3.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
    label3.setText("label3");

    label4.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
    label4.setText("label4");

    label5.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
    label5.setText("label5");

    label6.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
    label6.setText("label6");

    label7.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
    label7.setText("label7");

    label8.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
    label8.setText("label8");

    label9.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
    label9.setText("label9");

    field0.setEditable(false);
    field0.setPreferredSize(new java.awt.Dimension(191, 24));
    field0.addFocusListener(new java.awt.event.FocusAdapter() {
      public void focusLost(java.awt.event.FocusEvent evt) {
        field0FocusLost(evt);
      }
    });

    field1.setEditable(false);
    field1.setPreferredSize(new java.awt.Dimension(191, 24));
    field1.addFocusListener(new java.awt.event.FocusAdapter() {
      public void focusLost(java.awt.event.FocusEvent evt) {
        field1FocusLost(evt);
      }
    });

    field2.setEditable(false);
    field2.setPreferredSize(new java.awt.Dimension(191, 24));
    field2.addFocusListener(new java.awt.event.FocusAdapter() {
      public void focusLost(java.awt.event.FocusEvent evt) {
        field2FocusLost(evt);
      }
    });

    field3.setEditable(false);
    field3.setPreferredSize(new java.awt.Dimension(191, 24));
    field3.addFocusListener(new java.awt.event.FocusAdapter() {
      public void focusLost(java.awt.event.FocusEvent evt) {
        field3FocusLost(evt);
      }
    });

    field4.setEditable(false);
    field4.setPreferredSize(new java.awt.Dimension(191, 24));
    field4.addFocusListener(new java.awt.event.FocusAdapter() {
      public void focusLost(java.awt.event.FocusEvent evt) {
        field4FocusLost(evt);
      }
    });

    field5.setEditable(false);
    field5.setPreferredSize(new java.awt.Dimension(191, 24));
    field5.addFocusListener(new java.awt.event.FocusAdapter() {
      public void focusLost(java.awt.event.FocusEvent evt) {
        field5FocusLost(evt);
      }
    });

    field6.setEditable(false);
    field6.setPreferredSize(new java.awt.Dimension(191, 24));
    field6.addFocusListener(new java.awt.event.FocusAdapter() {
      public void focusLost(java.awt.event.FocusEvent evt) {
        field6FocusLost(evt);
      }
    });

    field7.setEditable(false);
    field7.setPreferredSize(new java.awt.Dimension(191, 24));
    field7.addFocusListener(new java.awt.event.FocusAdapter() {
      public void focusLost(java.awt.event.FocusEvent evt) {
        field7FocusLost(evt);
      }
    });

    field8.setEditable(false);
    field8.setPreferredSize(new java.awt.Dimension(191, 24));
    field8.addFocusListener(new java.awt.event.FocusAdapter() {
      public void focusLost(java.awt.event.FocusEvent evt) {
        field8FocusLost(evt);
      }
    });

    field9.setEditable(false);
    field9.setPreferredSize(new java.awt.Dimension(191, 24));
    field9.addFocusListener(new java.awt.event.FocusAdapter() {
      public void focusLost(java.awt.event.FocusEvent evt) {
        field9FocusLost(evt);
      }
    });

    buttonSet.setText("Set");
    buttonSet.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(java.awt.event.ActionEvent evt) {
        buttonSetActionPerformed(evt);
      }
    });

    buttonReset.setText("Reset");
    buttonReset.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(java.awt.event.ActionEvent evt) {
        buttonResetActionPerformed(evt);
      }
    });

    buttonCancel.setText("Cancel");
    buttonCancel.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(java.awt.event.ActionEvent evt) {
        buttonCancelActionPerformed(evt);
      }
    });

    labelStatus.setText("labelStatus");

    javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
    getContentPane().setLayout(layout);
    layout.setHorizontalGroup(
      layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
      .addGroup(layout.createSequentialGroup()
        .addContainerGap()
        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
          .addComponent(labelStatus, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
          .addGroup(layout.createSequentialGroup()
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
              .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addComponent(label0, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(field0, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
              .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addComponent(label2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(field2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
              .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addComponent(label1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(field1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
              .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                  .addComponent(label4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                  .addComponent(label3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                  .addComponent(field3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                  .addComponent(field4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
              .addGroup(layout.createSequentialGroup()
                .addComponent(label5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(field5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
              .addGroup(layout.createSequentialGroup()
                .addComponent(label6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(field6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
              .addGroup(layout.createSequentialGroup()
                .addComponent(label7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(field7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
              .addGroup(layout.createSequentialGroup()
                .addComponent(label8, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(field8, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
              .addGroup(layout.createSequentialGroup()
                .addComponent(label9, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(field9, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
          .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
            .addGap(0, 0, Short.MAX_VALUE)
            .addComponent(buttonSet)
            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
            .addComponent(buttonReset)
            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
            .addComponent(buttonCancel)))
        .addContainerGap())
    );
    layout.setVerticalGroup(
      layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
      .addGroup(layout.createSequentialGroup()
        .addContainerGap()
        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
          .addGroup(layout.createSequentialGroup()
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
              .addComponent(field0, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
              .addComponent(label0))
            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
              .addComponent(field1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
              .addComponent(label1))
            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
              .addComponent(field2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
              .addComponent(label2))
            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
              .addComponent(field3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
              .addComponent(label3))
            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
              .addComponent(field4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
              .addComponent(label4)))
          .addGroup(layout.createSequentialGroup()
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
              .addComponent(field5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
              .addComponent(label5))
            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
              .addComponent(field6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
              .addComponent(label6))
            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
              .addComponent(field7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
              .addComponent(label7))
            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
              .addComponent(field8, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
              .addComponent(label8))
            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
              .addComponent(field9, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
              .addComponent(label9))))
        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
          .addComponent(buttonSet)
          .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
            .addComponent(buttonCancel)
            .addComponent(buttonReset)))
        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
        .addComponent(labelStatus)
        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
    );

    pack();
  }// </editor-fold>//GEN-END:initComponents

  private void field0FocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_field0FocusLost
    handleFieldChange();
  }//GEN-LAST:event_field0FocusLost

  private void field1FocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_field1FocusLost
    handleFieldChange();
  }//GEN-LAST:event_field1FocusLost

  private void field2FocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_field2FocusLost
    handleFieldChange();
  }//GEN-LAST:event_field2FocusLost

  private void field3FocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_field3FocusLost
    handleFieldChange();
  }//GEN-LAST:event_field3FocusLost

  private void field4FocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_field4FocusLost
    handleFieldChange();
  }//GEN-LAST:event_field4FocusLost

  private void field5FocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_field5FocusLost
    handleFieldChange();
  }//GEN-LAST:event_field5FocusLost

  private void field6FocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_field6FocusLost
    handleFieldChange();
  }//GEN-LAST:event_field6FocusLost

  private void field7FocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_field7FocusLost
    handleFieldChange();
  }//GEN-LAST:event_field7FocusLost

  private void field8FocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_field8FocusLost
    handleFieldChange();
  }//GEN-LAST:event_field8FocusLost

  private void field9FocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_field9FocusLost
    handleFieldChange();
  }//GEN-LAST:event_field9FocusLost

  private void buttonSetActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonSetActionPerformed
    save();
    dispose();
  }//GEN-LAST:event_buttonSetActionPerformed

  private void buttonResetActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonResetActionPerformed
    populateFields();
  }//GEN-LAST:event_buttonResetActionPerformed

  private void buttonCancelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonCancelActionPerformed
    formWindowClosing(null);
  }//GEN-LAST:event_buttonCancelActionPerformed

  private void formWindowClosing(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosing
    this.buttonCancel.setEnabled(false);
    dispose();
  }//GEN-LAST:event_formWindowClosing

  // Variables declaration - do not modify//GEN-BEGIN:variables
  private javax.swing.JButton buttonCancel;
  private javax.swing.JButton buttonReset;
  private javax.swing.JButton buttonSet;
  private javax.swing.JTextField field0;
  private javax.swing.JTextField field1;
  private javax.swing.JTextField field2;
  private javax.swing.JTextField field3;
  private javax.swing.JTextField field4;
  private javax.swing.JTextField field5;
  private javax.swing.JTextField field6;
  private javax.swing.JTextField field7;
  private javax.swing.JTextField field8;
  private javax.swing.JTextField field9;
  private javax.swing.JLabel label0;
  private javax.swing.JLabel label1;
  private javax.swing.JLabel label2;
  private javax.swing.JLabel label3;
  private javax.swing.JLabel label4;
  private javax.swing.JLabel label5;
  private javax.swing.JLabel label6;
  private javax.swing.JLabel label7;
  private javax.swing.JLabel label8;
  private javax.swing.JLabel label9;
  private javax.swing.JLabel labelStatus;
  // End of variables declaration//GEN-END:variables
}
