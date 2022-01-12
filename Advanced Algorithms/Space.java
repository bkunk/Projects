package AlgProjectCascade;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

/**
 * Cascade final project
 * @author Jessica Lilland
 * @author Bryce Kunkle
 * @version Spring 2021
 */
public class CascadeVisual extends Application implements EventHandler<ActionEvent> {

	private CascadeModel myModel;

	private Button[][] btn;

	private GridPane grid;

	private Button reset;

	private Button cw;

	private Button ccw;
	
	private Label feedback;

	private ComboBox<String> combo;


	@Override
	public void start(Stage primaryStage) {

		myModel = new CascadeModel(3);

		primaryStage.setTitle("Cascade");

		FlowPane pane = new FlowPane();
		grid = new GridPane();
		btn = new Button[8][8]; // initializing Button array, it's a little bigger just to fit preview tile 
		cw = new Button();
		ccw = new Button();
		cw.setPrefSize(40, 80);
		cw.setText("C" + '\n' + "W");
		ccw.setPrefSize(40, 80);
		ccw.setText("C" + '\n' + "C" + '\n' + "W");
		cw.setOnAction(this);
		ccw.setOnAction(this);
		
		feedback = new Label();
		feedback.setText("It's red's turn. Place your tile");
		GridPane grid3 = new GridPane();

		
		grid.add(grid3, 1, 0);
		grid3.add(cw, 0, 0);
		grid3.add(ccw, 1, 0);


		combo = new ComboBox<String>();
		combo.getItems().add("3x3");
		combo.getItems().add("5x5");
		combo.getItems().add("8x8");
		combo.setValue("3x3");
		combo.setOnAction(this);


		myModel.addPropertyChangeListener(e -> {
			feedback.setText(myModel.getPlayerFeedback()); // update feedback label
		});

		reset = new Button();
		reset.setText("RESET");
		reset.setPrefSize(80, 30);
		reset.setOnAction(this);
		
		pane.getChildren().add(grid);
		pane.getChildren().add(0, feedback);;

		buttonMaker(); // creates all of the buttons that represent the board tiles

		Scene scene = new Scene(pane, 720, 710);

		primaryStage.setScene(scene);
		primaryStage.show();

	}

	/**
	 * This method creates all of the game board buttons
	 */
	private void buttonMaker() {

		grid.getChildren().clear();

		//////// 3x3 ////////
		if (myModel.getSize() == 3) {

			GridPane grid3 = new GridPane();
			grid.add(grid3, 1, 0);

			grid3.add(cw, 0, 0);
			grid3.add(ccw, 1, 0);
			grid.add(combo, 0, 0);
			grid.add(reset, 3, 0);
			//grid.add(feedback, 4, 0);

			for (int i = 0; i < 3; i++) {
				for (int j = 0; j < 3; j++) {

					Button button = new Button(myModel.getTile(i, j));
					button.setPrefSize(80, 80);
					btn[i][j] = button;
					if (myModel.getSpaceOwner(i, j) == CascadeModel.SpaceOwner.blue) {
						btn[i][j].setStyle(
								"-fx-border-color: #000000; -fx-border-width: 5px; -fx-background-color: #0000FF; -fx-text-fill: #000000");
					} else if (myModel.getSpaceOwner(i, j) == CascadeModel.SpaceOwner.red) {
						btn[i][j].setStyle(
								"-fx-border-color: #000000; -fx-border-width: 5px; -fx-background-color: #FF0000; -fx-text-fill: #000000");
					} else {
						btn[i][j].setStyle(
								"-fx-border-color: #000000; -fx-border-width: 5px; -fx-background-color: #FFFFFF; -fx-text-fill: #000000");

					}
					grid.add(btn[i][j], j, i + 2); // i+2 to get the correct location

				}
			}

			// Do the preview tile separately here
			Button button = new Button(myModel.getTile(-1, -1)); // preview tile is not on board so it has -1 for the														// location
			button.setPrefSize(80, 80);
			
			if (myModel.getCurPlayer() == CascadeModel.SpaceOwner.blue) {
				button.setStyle(
						"-fx-border-color: #000000; -fx-border-width: 5px; -fx-background-color: #0000FF; -fx-text-fill: #000000");
			} else {
				button.setStyle(
						"-fx-border-color: #000000; -fx-border-width: 5px; -fx-background-color: #FF0000; -fx-text-fill: #000000");

			}
			grid.add(button, 2, 0);

			buttonSetup(); // method for setting buttons on action and giving them user data
		
		//////// 5x5 ////////
		} else if (myModel.getSize() == 5) {

			GridPane grid3 = new GridPane();
			grid.add(grid3, 1, 0);

			grid3.add(cw, 0, 0);
			grid3.add(ccw, 1, 0);
			grid.add(combo, 0, 0);
			grid.add(reset, 3, 0);
			//grid.add(feedback, 4, 0);


			for (int i = 0; i < 5; i++) {
				for (int j = 0; j < 5; j++) {

					Button button = new Button(myModel.getTile(i, j));
					button.setPrefSize(80, 80);
					btn[i][j] = button;
					if (myModel.getSpaceOwner(i, j) == CascadeModel.SpaceOwner.blue) {
						btn[i][j].setStyle(
								"-fx-border-color: #000000; -fx-border-width: 5px; -fx-background-color: #0000FF; -fx-text-fill: #000000");
					} else if (myModel.getSpaceOwner(i, j) == CascadeModel.SpaceOwner.red) {
						btn[i][j].setStyle(
								"-fx-border-color: #000000; -fx-border-width: 5px; -fx-background-color: #FF0000; -fx-text-fill: #000000");
					} else {
						btn[i][j].setStyle(
								"-fx-border-color: #000000; -fx-border-width: 5px; -fx-background-color: #FFFFFF; -fx-text-fill: #000000");
					}
					grid.add(btn[i][j], j, i + 2);

				}
			}

			// Do the preview tile separately here
			Button button = new Button(myModel.getTile(-1, -1)); // preview tile is not on board so it has -1 for the
																	// location
			button.setPrefSize(80, 80);
			
			if (myModel.getCurPlayer() == CascadeModel.SpaceOwner.blue) {
				button.setStyle(
						"-fx-border-color: #000000; -fx-border-width: 5px; -fx-background-color: #0000FF; -fx-text-fill: #000000");
			} else {
				button.setStyle(
						"-fx-border-color: #000000; -fx-border-width: 5px; -fx-background-color: #FF0000; -fx-text-fill: #000000");
			}
			grid.add(button, 2, 0);

			buttonSetup(); // method for setting buttons on action and giving them user data

		//////// 8x8 ////////
		} else if (myModel.getSize() == 8) {

			GridPane grid3 = new GridPane();
			grid.add(grid3, 1, 0);

			grid3.add(cw, 0, 0);
			grid3.add(ccw, 1, 0);
			grid.add(combo, 0, 0);
			grid.add(reset, 3, 0);
			//grid.add(feedback, 5, 0);


			for (int i = 0; i < 8; i++) {
				for (int j = 0; j < 8; j++) {

					Button button = new Button(myModel.getTile(i, j));
					button.setPrefSize(80, 80);
					btn[i][j] = button;
					if (myModel.getSpaceOwner(i, j) == CascadeModel.SpaceOwner.blue) {
						btn[i][j].setStyle(
								"-fx-border-color: #000000; -fx-border-width: 5px; -fx-background-color: #0000FF; -fx-text-fill: #000000");
					} else if (myModel.getSpaceOwner(i, j) == CascadeModel.SpaceOwner.red) {
						btn[i][j].setStyle(
								"-fx-border-color: #000000; -fx-border-width: 5px; -fx-background-color: #FF0000; -fx-text-fill: #000000");
					} else {
						btn[i][j].setStyle(
								"-fx-border-color: #000000; -fx-border-width: 5px; -fx-background-color: #FFFFFF; -fx-text-fill: #000000");

					}
					grid.add(btn[i][j], j, i + 2);

				}
			}

			// Do the preview tile separately here
			Button button = new Button(myModel.getTile(-1, -1)); // preview tile is not on board so it has -1 for the
																	// location
			button.setPrefSize(80, 80);

			if (myModel.getCurPlayer() == CascadeModel.SpaceOwner.blue) {
				button.setStyle(
						"-fx-border-color: #000000; -fx-border-width: 5px; -fx-background-color: #0000FF; -fx-text-fill: #000000");
			} else {
				button.setStyle(
						"-fx-border-color: #000000; -fx-border-width: 5px; -fx-background-color: #FF0000; -fx-text-fill: #000000");
			}
			grid.add(button, 2, 0);

			buttonSetup(); // method for setting buttons on action and giving them user data
		}

	}

	public static void main(String[] args) {
		launch(args);
	}

	////////////////////
	////// HANDLE //////
	////////////////////
	
	@Override
	public void handle(ActionEvent ev) {
		if(ev.getSource() == reset) {
			myModel.reset();
			buttonMaker();
			feedback.setText("It's red's turn. Place your tile");
		}
		else if(ev.getSource() == cw) {
			myModel.rotateArrowsCW();
			buttonMaker();			
		}
		else if(ev.getSource() == ccw) {
			myModel.rotateArrowsCCW();
			buttonMaker();
		}
		else if(ev.getSource() == combo) {
			myModel.reset(); // if clear button clicked, send call to the model
			String size = combo.getSelectionModel().getSelectedItem();
			myModel.setSize(Integer.parseInt(size.substring(0, 1)));
			buttonMaker();
		}
		else { // tile button clicked
			try {
			Button click = (Button) ev.getSource();
			myModel.addTile((Integer) click.getUserData() / 10, (Integer) click.getUserData() % 10);
			buttonMaker();
			}
			catch(IllegalArgumentException ex){
				Alert a = new Alert(Alert.AlertType.ERROR);
				a.setTitle("Illegal move");
				a.setContentText(ex.getMessage());
				a.showAndWait();
			}
		}
	}
	
	/**
	 * Terribly long method that just sets action and user data of each button
	 */
	public void buttonSetup() {
		int size = myModel.getSize();
		
		btn[0][0].setOnAction(this);
		btn[0][0].setUserData(00); 
		btn[0][1].setOnAction(this);
		btn[0][1].setUserData(01);
		btn[0][2].setOnAction(this);
		btn[0][2].setUserData(02);

		btn[1][0].setOnAction(this);
		btn[1][0].setUserData(10); 
		btn[1][1].setOnAction(this);
		btn[1][1].setUserData(11);
		btn[1][2].setOnAction(this);
		btn[1][2].setUserData(12);

		btn[2][0].setOnAction(this);
		btn[2][0].setUserData(20); 
		btn[2][1].setOnAction(this);
		btn[2][1].setUserData(21);
		btn[2][2].setOnAction(this);
		btn[2][2].setUserData(22);
		
		if(size == 5 || size == 8) {
			btn[0][3].setOnAction(this);
			btn[0][3].setUserData(03); 
			btn[0][4].setOnAction(this);
			btn[0][4].setUserData(04);
			
			btn[1][3].setOnAction(this);
			btn[1][3].setUserData(13); 
			btn[1][4].setOnAction(this);
			btn[1][4].setUserData(14);
			
			btn[2][3].setOnAction(this);
			btn[2][3].setUserData(23); 
			btn[2][4].setOnAction(this);
			btn[2][4].setUserData(24);
			
			btn[3][0].setOnAction(this);
			btn[3][0].setUserData(30); 
			btn[3][1].setOnAction(this);
			btn[3][1].setUserData(31);
			btn[3][2].setOnAction(this);
			btn[3][2].setUserData(32);
			btn[3][3].setOnAction(this);
			btn[3][3].setUserData(33); 
			btn[3][4].setOnAction(this);
			btn[3][4].setUserData(34);
			
			btn[4][0].setOnAction(this);
			btn[4][0].setUserData(40); 
			btn[4][1].setOnAction(this);
			btn[4][1].setUserData(41);
			btn[4][2].setOnAction(this);
			btn[4][2].setUserData(42);
			btn[4][3].setOnAction(this);
			btn[4][3].setUserData(43); 
			btn[4][4].setOnAction(this);
			btn[4][4].setUserData(44);
		}
		
		if(size == 8) {
			btn[0][5].setOnAction(this);
			btn[0][5].setUserData(05);
			btn[0][6].setOnAction(this);
			btn[0][6].setUserData(06); 
			btn[0][7].setOnAction(this);
			btn[0][7].setUserData(07);
			
			btn[1][5].setOnAction(this);
			btn[1][5].setUserData(15);
			btn[1][6].setOnAction(this);
			btn[1][6].setUserData(16); 
			btn[1][7].setOnAction(this);
			btn[1][7].setUserData(17);
			
			btn[2][5].setOnAction(this);
			btn[2][5].setUserData(25);
			btn[2][6].setOnAction(this);
			btn[2][6].setUserData(26); 
			btn[2][7].setOnAction(this);
			btn[2][7].setUserData(27);
			
			btn[3][5].setOnAction(this);
			btn[3][5].setUserData(35);
			btn[3][6].setOnAction(this);
			btn[3][6].setUserData(36); 
			btn[3][7].setOnAction(this);
			btn[3][7].setUserData(37);
			
			btn[4][5].setOnAction(this);
			btn[4][5].setUserData(45);
			btn[4][6].setOnAction(this);
			btn[4][6].setUserData(46); 
			btn[4][7].setOnAction(this);
			btn[4][7].setUserData(47);
			
			btn[5][0].setOnAction(this);
			btn[5][0].setUserData(50); 
			btn[5][1].setOnAction(this);
			btn[5][1].setUserData(51);
			btn[5][2].setOnAction(this);
			btn[5][2].setUserData(52);
			btn[5][3].setOnAction(this);
			btn[5][3].setUserData(53); 
			btn[5][4].setOnAction(this);
			btn[5][4].setUserData(54);
			btn[5][5].setOnAction(this);
			btn[5][5].setUserData(55);
			btn[5][6].setOnAction(this);
			btn[5][6].setUserData(56); 
			btn[5][7].setOnAction(this);
			btn[5][7].setUserData(57);
			
			btn[6][0].setOnAction(this);
			btn[6][0].setUserData(60); 
			btn[6][1].setOnAction(this);
			btn[6][1].setUserData(61);
			btn[6][2].setOnAction(this);
			btn[6][2].setUserData(62);
			btn[6][3].setOnAction(this);
			btn[6][3].setUserData(63); 
			btn[6][4].setOnAction(this);
			btn[6][4].setUserData(64);
			btn[6][5].setOnAction(this);
			btn[6][5].setUserData(65);
			btn[6][6].setOnAction(this);
			btn[6][6].setUserData(66); 
			btn[6][7].setOnAction(this);
			btn[6][7].setUserData(67);
			
			btn[7][0].setOnAction(this);
			btn[7][0].setUserData(70); 
			btn[7][1].setOnAction(this);
			btn[7][1].setUserData(71);
			btn[7][2].setOnAction(this);
			btn[7][2].setUserData(72);
			btn[7][3].setOnAction(this);
			btn[7][3].setUserData(73); 
			btn[7][4].setOnAction(this);
			btn[7][4].setUserData(74);
			btn[7][5].setOnAction(this);
			btn[7][5].setUserData(75);
			btn[7][6].setOnAction(this);
			btn[7][6].setUserData(76); 
			btn[7][7].setOnAction(this);
			btn[7][7].setUserData(77);
		}
	}

}
