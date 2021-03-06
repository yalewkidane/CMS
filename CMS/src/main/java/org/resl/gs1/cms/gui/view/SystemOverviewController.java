package org.resl.gs1.cms.gui.view;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

import org.resl.gs1.cms.gui.MainApp;
import org.resl.gs1.cms.gui.model.GS1Code;
import org.resl.gs1.cms.interfaceback.InterfaceBack;

public class SystemOverviewController {
	@FXML
	private TableView<GS1Code> systemTable;
	@FXML
	private TableColumn<GS1Code, String> codeColumn;
	@FXML
	private TableColumn<GS1Code, String> prefixColumn;
	@FXML
	private TableColumn<GS1Code, String> referenceColumn;

	/*@FXML
    private Label firstNameLabel;
    @FXML
    private Label lastNameLabel;
    @FXML
    private Label streetLabel;
    @FXML
    private Label postalCodeLabel;
    @FXML
    private Label cityLabel;
    @FXML
    private Label birthdayLabel;*/

	// Reference to the main application.
	private MainApp mainApp;

	/**
	 * The constructor.
	 * The constructor is called before the initialize() method.
	 */
	public SystemOverviewController() {
	}

	/**
	 * Initializes the controller class. This method is automatically called
	 * after the fxml file has been loaded.
	 */
	@FXML
	private void initialize() {
		// Initialize the person table with the two columns.
		codeColumn.setCellValueFactory(cellData -> cellData.getValue().codeTypeProperty());
		prefixColumn.setCellValueFactory(cellData -> cellData.getValue().prefixProperty());
		referenceColumn.setCellValueFactory(cellData -> cellData.getValue().referenceProperty());
	}

	/**
	 * Is called by the main application to give a reference back to itself.
	 * 
	 * @param mainApp
	 */
	public void setMainApp(MainApp mainApp) {
		this.mainApp = mainApp;

		// Add observable list data to the table
		systemTable.setItems(mainApp.getCodeData());
	}

	/**
	 * Called when the user clicks on the delete button.
	 */
	@FXML
	private void handleDeleteCode() {
		GS1Code code = systemTable.getSelectionModel().getSelectedItem();
		InterfaceBack interfaceBack=new InterfaceBack();
    	if (code.getCodeType().equals("GTIN")){
    		interfaceBack.remove(Integer.parseInt(code.getPrefix()), Integer.parseInt(code.getReference()), 0, 0);
    	}else if (code.getCodeType().equals("GLN")){
    		interfaceBack.remove(Integer.parseInt(code.getPrefix()), 0, Integer.parseInt(code.getReference()), 0);
    	}else if (code.getCodeType().equals("GSRN")){
    		interfaceBack.remove(Integer.parseInt(code.getPrefix()), 0, 0, Integer.parseInt(code.getReference()));
    	}else{
    		System.out.println("Code type invalid (need to be GTIN, GLN, or GSRN");
    	}
		
		/*GS1Code code = systemTable.getSelectionModel().getSelectedItem();
		System.out.println(code.getCodeType());
		System.out.println(code.getPrefix());
		System.out.println(code.getReference());*/		
		int selectedIndex = systemTable.getSelectionModel().getSelectedIndex();
		if (selectedIndex >= 0) {
			systemTable.getItems().remove(selectedIndex);
		} else {
			// Nothing selected.
			Alert alert = new Alert(AlertType.WARNING);
			alert.initOwner(mainApp.getPrimaryStage());
			alert.setTitle("No Selection");
			alert.setHeaderText("No Person Selected");
			alert.setContentText("Please select a person in the table.");

			alert.showAndWait();
		}
	}

	/**
	 * Called when the user clicks the new button. Opens a dialog to edit
	 * details for a new person.
	 */
	@FXML
	private void handleNewCode() {
		GS1Code tempCode = new GS1Code();
		boolean okClicked = mainApp.showCodeEditDialog(tempCode);
		if (okClicked) {
			mainApp.getCodeData().add(tempCode);
		}
	}

	/**
	 * Called when the user clicks the edit button. Opens a dialog to edit
	 * details for the selected person.
	 */
	@FXML
	private void handleEditCode() {
		GS1Code selectedCode = systemTable.getSelectionModel().getSelectedItem();
		if (selectedCode != null) {
			boolean okClicked = mainApp.showCodeEditDialog(selectedCode);
			if (okClicked) {
				GS1Code code = systemTable.getSelectionModel().getSelectedItem();
				InterfaceBack interfaceBack=new InterfaceBack();
		    	if (code.getCodeType().equals("GTIN")){
		    		interfaceBack.remove(Integer.parseInt(code.getPrefix()), Integer.parseInt(code.getReference()), 0, 0);
		    	}else if (code.getCodeType().equals("GLN")){
		    		interfaceBack.remove(Integer.parseInt(code.getPrefix()), 0, Integer.parseInt(code.getReference()), 0);
		    	}else if (code.getCodeType().equals("GSRN")){
		    		interfaceBack.remove(Integer.parseInt(code.getPrefix()), 0, 0, Integer.parseInt(code.getReference()));
		    	}else{
		    		System.out.println("Code type invalid (need to be GTIN, GLN, or GSRN");
		    	}
			}

		} else {
			// Nothing selected.
			Alert alert = new Alert(AlertType.WARNING);
			alert.initOwner(mainApp.getPrimaryStage());
			alert.setTitle("No Selection");
			alert.setHeaderText("No Code Selected");
			alert.setContentText("Please select a code in the table.");

			alert.showAndWait();
		}
	}

	/**
	 * Called when the user clicks the history button. Opens a dialog to show
	 * history details of the system.
	 */
	@FXML
	private void handleHistoryCode() {
		InterfaceBack interfaceBack = new InterfaceBack();
		String history = interfaceBack.history();
		/*String history = "Love not me for comely grace,\n" +
				"For my pleasing eye or face,\n" +
				"Nor for any outward part,\n" +
				"No, nor for my constant heart,\n" +
				"For these may fail, or turn to ill.\n" +
				"So thou and I must sever.\n" +
				"Keep therefore a true woman�s eye just to increase the length of the line,\n" +
				"And love me still, but know not why,\n" +
				"So hast thou the same reason still\n" +
				"To doat upon me ever.";*/
		mainApp.showHistoryEditDialog(history);
	}
}
