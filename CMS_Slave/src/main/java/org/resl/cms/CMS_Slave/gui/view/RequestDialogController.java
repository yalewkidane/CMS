package org.resl.cms.CMS_Slave.gui.view;

import org.resl.cms.CMS_Slave.gui.model.Config;
import org.resl.cms.CMS_Slave.interfaceback.InterfaceBack;
import org.resl.cms.CMS_Slave.model.KeyType;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TextField;
import javafx.stage.Stage;


public class RequestDialogController {

	@FXML
	private TextField typeField;
	@FXML
	private TextField amountField;


	private Stage dialogStage;
	private boolean okClicked = false;

	/**
	 * Initializes the controller class. This method is automatically called
	 * after the fxml file has been loaded.
	 */
	@FXML
	private void initialize() {
	}

	/**
	 * Sets the stage of this dialog.
	 * 
	 * @param dialogStage
	 */
	public void setDialogStage(Stage dialogStage) {
		this.dialogStage = dialogStage;
	}
	/**
	 * Returns true if the user clicked OK, false otherwise.
	 * 
	 * @return
	 */
	public boolean isOkClicked() {
		return okClicked;
	}

	/**
	 * Called when the user clicks request.
	 */
	@FXML
	private void handleRequest() {
		if (isInputValid()) {
			System.out.println("Request clicked!");
			System.out.println("Type: " + typeField.getText());
			System.out.println("Amount: " + amountField.getText());

			InterfaceBack back = new InterfaceBack();
			TableColumn<Config, String> tempColumn = ConfigOverviewController.getValueColumn();
			KeyType key = new KeyType();

			if (typeField.getText().trim().equals("gtin")){
				key = new KeyType("gtin", Integer.parseInt(tempColumn.getCellData(4)), 
						Integer.parseInt(tempColumn.getCellData(5)));
			}else if (typeField.getText().trim().equals("gln")){
				key = new KeyType("gln", Integer.parseInt(tempColumn.getCellData(4)), 
						Integer.parseInt(tempColumn.getCellData(6)));
			}else if (typeField.getText().trim().equals("gsrn")){
				key = new KeyType("gsrn", Integer.parseInt(tempColumn.getCellData(4)), 
						Integer.parseInt(tempColumn.getCellData(7)));
			}
			
			String status=back.configure_key(key);
			System.out.println(status);

			// step 3.2 key request
			System.out.println("Key Request");
			status=back.request(key, Integer.parseInt(amountField.getText()));

			okClicked = true;
			dialogStage.close();
		}
	}

	/**
	 * Called when the user clicks cancel.
	 */
	@FXML
	private void handleCancel() {
		dialogStage.close();
	}

	/**
	 * Validates the user input in the text fields.
	 * 
	 * @return true if the input is valid
	 */
	private boolean isInputValid() {
		String errorMessage = "";

		if (typeField.getText() == null || typeField.getText().length() == 0) {
			errorMessage += "No valid type!\n"; 
		} else if (amountField.getText() == null || amountField.getText().length() == 0) {
			errorMessage += "No valid amount!\n"; 
		}

		if (errorMessage.length() == 0) {
			return true;
		} else {
			// Show the error message.
			Alert alert = new Alert(AlertType.ERROR);
			alert.initOwner(dialogStage);
			alert.setTitle("Invalid Fields");
			alert.setHeaderText("Please correct invalid fields");
			alert.setContentText(errorMessage);

			alert.showAndWait();

			return false;
		}
	}
}
