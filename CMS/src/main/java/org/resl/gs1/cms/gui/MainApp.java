package org.resl.gs1.cms.gui;

import java.io.IOException;

import org.resl.gs1.cms.gui.model.GS1Code;
import org.resl.gs1.cms.gui.view.SystemOverviewController;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class MainApp extends Application {

	private Stage primaryStage;
	private BorderPane rootLayout;

	@Override
	public void start(Stage primaryStage) {
		this.primaryStage = primaryStage;
		this.primaryStage.setTitle("CMS System - Server");

		initRootLayout();

		showSystemOverview();
	}

	/**
	 * Initializes the root layout.
	 */
	public void initRootLayout() {
		try {
			// Load root layout from fxml file.
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(MainApp.class.getResource("view/RootLayout.fxml"));
			rootLayout = (BorderPane) loader.load();

			// Show the scene containing the root layout.
			Scene scene = new Scene(rootLayout);
			primaryStage.setScene(scene);
			primaryStage.show();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Shows the system overview inside the root layout.
	 */
	public void showSystemOverview() {
		try {
			// Load person overview.
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(MainApp.class.getResource("view/SystemOverview.fxml"));
			AnchorPane systemOverview = (AnchorPane) loader.load();

			// Set person overview into the center of root layout.
			rootLayout.setCenter(systemOverview);

			// Give the controller access to the main app.
			SystemOverviewController controller = loader.getController();
			controller.setMainApp(this);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Returns the main stage.
	 * @return
	 */
	public Stage getPrimaryStage() {
		return primaryStage;
	}

	public static void main(String[] args) {
		launch(args);
	}

	/**
	 * The data as an observable list of Persons.
	 */
	private ObservableList<GS1Code> gs1CodeData = FXCollections.observableArrayList();

	/**
	 * Constructor
	 */
	public MainApp() {
		// Add some sample data
		gs1CodeData.add(new GS1Code("GTIN", "1234", "1234"));
		gs1CodeData.add(new GS1Code("GLN", "something", "1234"));
		gs1CodeData.add(new GS1Code("GSRN", "hello", "how r u"));
	}

	/**
	 * Returns the data as an observable list of Persons. 
	 * @return
	 */
	public ObservableList<GS1Code> getCodeData() {
		return gs1CodeData;
	}
}