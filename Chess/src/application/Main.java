package application;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Optional;
import java.util.Timer;
import java.util.TimerTask;
import java.nio.file.Paths;
import java.util.concurrent.TimeUnit;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class Main extends Application {
	/************** Declaring global variables ************/
	String playerName1 = "";
	String playerTypeButton = "";
	String playerName2 = "";
	String playerTypeButton2 = "";
	String player1Color = "";
	String player2Color = "";
	String gameModeType = "";
	Label playerOnePointsLabel = new Label();
	Label playerTwoPointsLabel = new Label();
	Label gameMoves = new Label();
	Label gameTimer = new Label();
	int timeTotalSeconds = 0;
	int timeCurrentSeconds = 0;
	int timeCurrentMinutes = 0;
	int timeCurrentHours = 0;
	boolean isLoaded = false;
	boolean isPawn = false; // for promotion
	private Scene startScene, boardScene, splash;
	private Game game;
	private ChessBoardButton[][] chessBoardButtons = new ChessBoardButton[8][8];
	private ChessBoardButton[] capturedPieceButtons = new ChessBoardButton[32];
	VBox centerMidChess = new VBox();
	/******* HBox for Captured Pieces list **********/
	HBox capturedRowLeft1 = new HBox();
	HBox capturedRowLeft2 = new HBox();
	HBox capturedRowLeft3 = new HBox();
	HBox capturedRowLeft4 = new HBox();

	HBox capturedRowRight1 = new HBox();
	HBox capturedRowRight2 = new HBox();
	HBox capturedRowRight3 = new HBox();
	HBox capturedRowRight4 = new HBox();

	Dialog<String> errorMessage = new Dialog<String>();
	CPU computer = new CPU();// CPU global
	int playerOrComp1;
	int playerOrComp2;
	int num = 0;
	int nextRow = 0;
	int nextCol = 0;
	int curRow = 0;
	int curCol = 0;
	int gameMoveNumber = 0;
	Stage primaryStage;
	Player currentPlayer;
	Button rank1 = new Button();
	Button rank2 = new Button();
	Button rank3 = new Button();
	Piece[][] defaultPieces = new Piece[8][8];
	Loader fileLoader;
	Label playOneWLLabel = new Label();
	Label playTwoWLLabel = new Label();

	@Override
	public void start(Stage primaryStage) {
		this.primaryStage = primaryStage;

		try {
			this.primaryStage.getIcons().add(new Image(new FileInputStream("assets/arcade_chess_logo.png")));
		} catch (Exception e) {
			e.printStackTrace();
		}

		this.primaryStage.setOnCloseRequest(e -> {
			e.consume();
			Alert a = new Alert(AlertType.CONFIRMATION);
			a.setTitle("Arcade Chess");
			Stage stage = (Stage) a.getDialogPane().getScene().getWindow();
			try {
				stage.getIcons().add(new Image(new FileInputStream("assets/arcade_chess_logo.png")));
			} catch (Exception ex) {
				ex.printStackTrace();
			}
			a.setHeaderText("");
			a.setContentText("Are you sure you want to exit?");
			Optional<ButtonType> res = a.showAndWait();
			if (res.get() == ButtonType.OK) {
				System.exit(0);
			}

		});

		try {
			/********** Declare elements in starting scene ***********/
			GridPane infoGrid = new GridPane();
			splash = splashScene();
			VBox startBox = new VBox();
			HBox bottomImg = new HBox();
			Label askInput = new Label("PLAYER ONE NAME : ");
			Label playerAsk1 = new Label("PLAYER ONE TYPE : ");
			Label playerAsk2 = new Label("PLAYER ONE COLOR : ");
			Label askInput2 = new Label("PLAYER TWO NAME : ");
			Label playerAsk3 = new Label("PLAYER TWO TYPE : ");
			Label playerAsk4 = new Label("PLAYER TWO COLOR : ");
			Label gameTypeLabel = new Label("GAME TYPE : ");
			TextField field = new TextField();
			TextField field2 = new TextField();
			MenuBar menuBar = new MenuBar();
			Label infoTitle = new Label("                    FILL IN INFO TO START GAME                    ");
			infoTitle.setBackground(
					new Background(new BackgroundFill(Color.web("#956e23"), CornerRadii.EMPTY, Insets.EMPTY)));
			DropShadow drop = new DropShadow();
			drop.setRadius(5.0);
			drop.setOffsetX(3.0);
			drop.setOffsetY(3.0);
			drop.setColor(Color.BLACK);
			Button playerInfoConfirm = new Button("CONFIRM");

			Menu menuFile = new Menu("Menu");
			MenuItem menuItemSO = new MenuItem("Start Over");
			menuItemSO.setOnAction(new EventHandler<ActionEvent>() {
				@Override
				public void handle(ActionEvent arg0) {
					primaryStage.setScene(splash);
					primaryStage.show();
				}
			});

			MenuItem menuItemExit = new MenuItem("Exit");
			menuItemExit.setOnAction(ActionEvent -> {
				System.out.println("Exit from the Menu.");
				System.exit(0);
			});

			menuFile.getItems().add(menuItemSO);
			menuFile.getItems().add(menuItemExit);
			menuBar.getMenus().add(menuFile);

			Font arcadeFont = null;
			try {
				arcadeFont = Font.loadFont(new FileInputStream("resources/fonts/astronboy.ttf"), 16);
				arcadeFont = Font.font("Astron Boy Rg", FontWeight.EXTRA_BOLD, 16);

			} catch (Exception e) {
				// e.printStackTrace();
			}
			try {
				Image img = new Image(new FileInputStream("assets/arcade_chess_logo.png"));
				ImageView view = new ImageView(img);
				bottomImg.getChildren().add(view);
			} catch (Exception e) {
				e.printStackTrace();
			}

			startBox.setAlignment(Pos.TOP_CENTER);
			startBox.setStyle("-fx-background-color: transparent");
			infoTitle.setFont(Font.font("Astron Boy Rg", FontWeight.BOLD, 24));

			askInput.setFont(arcadeFont);
			askInput.getStyleClass().add("player1name");
			playerAsk1.setFont(arcadeFont);
			playerAsk1.getStyleClass().add("player1name");
			playerAsk2.setFont(arcadeFont);
			playerAsk2.getStyleClass().add("player1name");
			askInput2.setFont(arcadeFont);
			askInput2.getStyleClass().add("player1name");
			playerAsk3.setFont(arcadeFont);
			playerAsk3.getStyleClass().add("player1name");
			playerAsk4.setFont(arcadeFont);
			playerAsk4.getStyleClass().add("player1name");
			gameTypeLabel.setFont(arcadeFont);
			gameTypeLabel.getStyleClass().add("player1name");

			/*********** Obtaining Player 1 info ***********/
			ToggleGroup playerType = new ToggleGroup();
			ToggleGroup playerColor = new ToggleGroup();
			RadioButton rb1 = new RadioButton();
			RadioButton rb2 = new RadioButton();
			RadioButton rb3 = new RadioButton();
			RadioButton rb4 = new RadioButton();
			rb1.setText("HUMAN");
			rb1.setToggleGroup(playerType);
			rb2.setText("COMPUTER");
			rb2.setToggleGroup(playerType);
			rb3.setText("WHITE");
			rb3.setToggleGroup(playerColor);
			rb4.setText("BLACK");
			rb4.setToggleGroup(playerColor);

			/*********** Obtaining Player 2 info ***********/
			ToggleGroup playerType2 = new ToggleGroup();
			ToggleGroup playerColor2 = new ToggleGroup();
			ToggleGroup gameType = new ToggleGroup();

			RadioButton rb5 = new RadioButton();
			RadioButton rb6 = new RadioButton();
			RadioButton rb7 = new RadioButton();
			RadioButton rb8 = new RadioButton();
			RadioButton cG = new RadioButton();
			RadioButton aG = new RadioButton();

			rb5.setText("HUMAN");
			rb5.setToggleGroup(playerType2);
			rb6.setText("COMPUTER");
			rb6.setToggleGroup(playerType2);
			rb7.setText("WHITE");
			rb7.setToggleGroup(playerColor2);
			rb8.setText("BLACK");
			rb8.setToggleGroup(playerColor2);
			cG.setText("CLASSIC");
			cG.setToggleGroup(gameType);
			aG.setText("ARCADE");
			aG.setToggleGroup(gameType);
			cG.setSelected(true);

			/*********** Making sure proper info is filled *********/
			ButtonType close = new ButtonType("OK", ButtonData.OK_DONE);
			errorMessage.getDialogPane().getButtonTypes().addAll(close);
			errorMessage.setTitle("Error");
			errorMessage.getDialogPane().setMinHeight(Region.USE_PREF_SIZE);
			errorMessage.getDialogPane().setMinWidth(Region.USE_PREF_SIZE);

			/*********** Setting GridPane Constraints ***********/
			GridPane.setConstraints(infoTitle, 0, 0); // column then row : col, row
			GridPane.setConstraints(askInput, 0, 1);
			GridPane.setConstraints(field, 1, 1);
			GridPane.setConstraints(playerAsk1, 0, 2);
			GridPane.setConstraints(rb1, 1, 2);
			GridPane.setConstraints(rb2, 2, 2);
			GridPane.setConstraints(playerAsk2, 0, 3);
			GridPane.setConstraints(rb3, 1, 3);
			GridPane.setConstraints(rb4, 2, 3);

			GridPane.setConstraints(askInput2, 0, 4);
			GridPane.setConstraints(field2, 1, 4);
			GridPane.setConstraints(playerAsk3, 0, 5);
			GridPane.setConstraints(rb5, 1, 5);
			GridPane.setConstraints(rb6, 2, 5);
			GridPane.setConstraints(playerAsk4, 0, 6);
			GridPane.setConstraints(rb7, 1, 6);
			GridPane.setConstraints(rb8, 2, 6);
			GridPane.setConstraints(gameTypeLabel, 0, 7);
			GridPane.setConstraints(cG, 1, 7);
			GridPane.setConstraints(aG, 2, 7);

			playerInfoConfirm.setAlignment(Pos.CENTER);
			bottomImg.setAlignment(Pos.CENTER);

			/*********** Adds all GUI elements to an infoGrid layout ***********/
			infoGrid.getChildren().addAll(askInput, field, rb1, rb2, rb3, rb4, playerAsk1, playerAsk2,
					playerInfoConfirm, askInput2, field2, rb5, rb6, rb7, rb8, gameTypeLabel, aG, cG, playerAsk3,
					playerAsk4);
			startBox.getChildren().add(menuBar);
			startBox.getChildren().addAll(infoTitle, infoGrid, playerInfoConfirm, bottomImg);

			// Creates a new scene with infoGrid
			startScene = new Scene(startBox, 504, 750, Color.web("#a0a0a0"));

			primaryStage.setResizable(false);
			startScene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			primaryStage.setScene(splash);
			primaryStage.setTitle("ARCADE CHESS");
			primaryStage.show();

			/***********
			 * Defines the event handler behavior for pressing the confirm button
			 ***********/
			playerInfoConfirm.setOnAction(new EventHandler<ActionEvent>() {
				@Override
				public void handle(ActionEvent e) {
					playerName1 = field.getText();
					playerName2 = field2.getText();

					if (playerName1.isEmpty() || playerName2.isEmpty() || (!rb1.isSelected() && !rb2.isSelected())
							|| (!rb3.isSelected() && !rb4.isSelected()) || (!rb5.isSelected() && !rb6.isSelected())
							|| (!rb7.isSelected() && !rb8.isSelected())) {
						errorMessage.setContentText(
								"Please make sure all info is filled on the start screen before hitting confirm.");
						errorMessage.showAndWait();
					} else if ((rb3.isSelected() && rb7.isSelected()) || (rb4.isSelected() && rb8.isSelected())) {
						errorMessage.setContentText(
								"Please make sure all info is filled on the start screen before hitting confirm, and different colors for each player is selected.");
						errorMessage.showAndWait();

					} else if (playerName1.equals(playerName2)) {
						errorMessage.setContentText(
								"Please make sure all info is filled on the start screen, and the player names are different.");
						errorMessage.showAndWait();
					} else {

						/*********** Checks for radio button selections ***********/
						if (rb1.isSelected()) {
							playerTypeButton = rb1.getText().toLowerCase();
							playerOrComp1 = 1;
						} else {
							playerTypeButton = rb2.getText().toLowerCase();
							playerOrComp1 = 2;
						}

						if (rb3.isSelected()) {
							player1Color = rb3.getText().toLowerCase();
						} else {
							player1Color = rb4.getText().toLowerCase();
						}

						if (rb5.isSelected()) {
							playerTypeButton2 = rb5.getText().toLowerCase();
							playerOrComp2 = 1;
						} else {
							playerTypeButton2 = rb6.getText().toLowerCase();
							playerOrComp2 = 2;
						}

						if (rb7.isSelected()) {
							player2Color = rb7.getText().toLowerCase();
						} else {
							player2Color = rb8.getText().toLowerCase();
						}

						if (cG.isSelected()) {
							gameModeType = cG.getText().toLowerCase();
						}

						primaryStage.hide();
						startGame();
						primaryStage.setScene(boardScene);
						primaryStage.show();
					}
				}
			});

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void startGame() {
		if (gameModeType.equals("classic")) {
			if (!isLoaded) {
				game = new Game(playerOrComp1, player1Color, playerName1, playerOrComp2, player2Color, playerName2,
						false);
				game.setGameMode("classic");
			}
			if (playerOrComp1 != 1) { // Setting Computer Player
				computer.setPlayer(game.getPlayer1());
			}
			if (playerOrComp2 != 1) {
				computer.setPlayer(game.getPlayer2());
			}
		} else {
			if (!isLoaded) {
				game = new Game(playerOrComp1, player1Color, playerName1, playerOrComp2, player2Color, playerName2,
						true);
				game.setGameMode("arcade");
			}
			if (playerOrComp1 != 1) { // Setting Computer Player
				computer.setPlayer(game.getPlayer1());
			}
			if (playerOrComp2 != 1) {
				computer.setPlayer(game.getPlayer2());
			}
		}

		currentPlayer = new Player();
		currentPlayer.setColor("white");
		boardScene = boardScene();
		settingButtons();
		if ((playerOrComp1 != 1) && (computer.getCpuPlayer().getColor().equals(currentPlayer.getColor()))) {
			System.out.println("Current player is: " + currentPlayer.getPlayerType());
			computer.findAvailablePieces(game.getBoard());// move of cpu
			computer.determineMove(game.getBoard());
			updateGameBoard(computer.getCurrentRow(), computer.getCurrentCol(), computer.getNextRow(),
					computer.getNextCol());
			currentPlayer.setColor("black");
			determinePowerUps();

		} else if ((playerOrComp2 != 1) && (computer.getCpuPlayer().getColor().equals(currentPlayer.getColor()))) {
			System.out.println("Current palyer is: " + currentPlayer.getPlayerType());
			computer.findAvailablePieces(game.getBoard());// move of cpu
			computer.determineMove(game.getBoard());
			updateGameBoard(computer.getCurrentRow(), computer.getCurrentCol(), computer.getNextRow(),
					computer.getNextCol());

			currentPlayer.setColor("black");
			determinePowerUps();
		}
	}

	public Scene boardScene() {
		/******************* Player one items ******************/
		playOneWLLabel.setText("WINS: " + Integer.toString(game.getPlayer1().getWins()) + "" + "    LOSES: "
				+ Integer.toString(game.getPlayer1().getLoses()));
		Label playOneNameLabel = new Label(playerName1.toUpperCase());
		playOneNameLabel.getStyleClass().add("playerNames");
		playOneWLLabel.getStyleClass().add("player1name");
		Label capturedPieceLabel = new Label("CAPTURED PIECES");
		capturedPieceLabel.getStyleClass().add("player1name");

		/******************** Player two items ******************/
		playTwoWLLabel.setText("WINS: " + Integer.toString(game.getPlayer2().getWins()) + "" + "    LOSES: "
				+ Integer.toString(game.getPlayer2().getLoses()));
		Label playTwoNameLabel = new Label(playerName2.toUpperCase());
		Label capturedPieceLabel2 = new Label("CAPTURED PIECES");
		playTwoNameLabel.getStyleClass().add("playerNames");
		playTwoWLLabel.getStyleClass().add("player1name");
		capturedPieceLabel2.getStyleClass().add("player1name");

		/******************* Board and Game items ***************/
		Label gameTitle = new Label("ARCADE CHESS");
		Label gameStatsLabel = new Label("GAME STATISTICS : ");
		gameStatsLabel.getStyleClass().add("player1name");
		Font arcadeFont = null;
		if (gameModeType.equals("classic")) {
			rank1.setVisible(false);
			rank2.setVisible(false);
			rank3.setVisible(false);
		}
		rank1.getStyleClass().add("powerUpButtons");
		rank1.setPrefSize(50, 50);
		rank1.setShape(new Circle(5.0));
		rank2.getStyleClass().add("powerUpButtons");
		rank2.setPrefSize(50, 50);
		rank2.setShape(new Circle(5.0));
		rank3.getStyleClass().add("powerUpButtons");
		rank3.setPrefSize(50, 50);
		rank3.setShape(new Circle(5.0));

		/********************* Menu Items ***************************/
		MenuBar menuBar = new MenuBar();
		Menu menuFile = new Menu("Menu");
		MenuItem menuItem1 = new MenuItem("Save");
		menuItem1.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent arg0) {
				Saver fileSaver = new Saver(game.getBoard(), game.getPlayer1(), game.getPlayer2(), game.getGameMode());
				fileSaver.save();
				Alert a = new Alert(AlertType.INFORMATION);
				a.setTitle("Arcade Chess");
				Stage stage = (Stage) a.getDialogPane().getScene().getWindow();
				try {
					stage.getIcons().add(new Image(new FileInputStream("assets/arcade_chess_logo.png")));
				} catch (Exception ex) {
					ex.printStackTrace();
				}
				a.setHeaderText("");
				a.setContentText("Save successful!");
				a.showAndWait();
			}
		});

		MenuItem menuItem2 = new MenuItem("Forfeit"); // TODO: Popup for player winning/losing
		menuItem2.setOnAction((ActionEvent event) -> {

			gameMoveNumber = 0;
			gameMoves.setText(Integer.toString(gameMoveNumber));

			timeTotalSeconds = 0;
			timeCurrentSeconds = 0;
			timeCurrentMinutes = 0;
			timeCurrentHours = 0;
			num = 0;
			game.getPlayer1().setPoints(0);
			game.getPlayer2().setPoints(0);
			determinePowerUps();

			if (currentPlayer.getColor().equals(game.getPlayer1().getColor())) {
				game.getPlayer2().addToWins();
				game.getPlayer1().addToLoses();
			} else {

				game.getPlayer1().addToWins();
				game.getPlayer2().addToLoses();
			}

			playOneWLLabel.setText("WINS: " + Integer.toString(game.getPlayer1().getWins()) + "" + "    LOSES: "
					+ Integer.toString(game.getPlayer1().getLoses()));

			playTwoWLLabel.setText("WINS: " + Integer.toString(game.getPlayer2().getWins()) + "" + "    LOSES: "
					+ Integer.toString(game.getPlayer2().getLoses()));

			currentPlayer.setColor("white");
			resetGameBoard();
			drawCapturedPieces();

		});

		MenuItem menuItem3 = new MenuItem("Exit");
		menuItem3.setOnAction(ActionEvent -> {
			primaryStage.close();
			System.exit(0);

		});

		menuFile.getItems().add(menuItem1);
		menuFile.getItems().add(menuItem2);
		menuFile.getItems().add(menuItem3);
		menuBar.getMenus().add(menuFile);

		try {
			rank1.setGraphic(new ImageView(new Image(new FileInputStream("assets/one_star.png"))));
			rank2.setGraphic(new ImageView(new Image(new FileInputStream("assets/two_star.png"))));
			rank3.setGraphic(new ImageView(new Image(new FileInputStream("assets/three_star.png"))));
		} catch (Exception e) {
			e.printStackTrace();
		}

		try {
			arcadeFont = Font.loadFont(new FileInputStream("resources/fonts/astronboy.ttf"), 16);
			arcadeFont = Font.font("Astron Boy Rg", FontWeight.EXTRA_BOLD, 16);

		} catch (Exception e) {
			e.printStackTrace();
		}
		gameStatsLabel.setFont(arcadeFont);
		playOneNameLabel.setFont(arcadeFont);
		playTwoNameLabel.setFont(arcadeFont);
		playTwoWLLabel.setFont(arcadeFont);
		playOneWLLabel.setFont(arcadeFont);
		capturedPieceLabel.setFont(arcadeFont);
		capturedPieceLabel2.setFont(arcadeFont);

		VBox main = new VBox();
		HBox top = new HBox();
		HBox middle = new HBox();
		VBox leftMid = new VBox(); // Captured pieces for player 1
		VBox rightMid = new VBox(); // Captured pieces for player 2
		HBox bottom = new HBox();
		VBox contents = new VBox();
		HBox stats = new HBox();
		VBox allGameInfo = new VBox();
		HBox allPlayerStats = new HBox();
		HBox allGamestats = new HBox();
		HBox pwrUpLabel = new HBox();
		HBox statsLabel = new HBox();
		HBox ranks = new HBox();
		HBox rank2Box = new HBox();
		GridPane chessGrid = new GridPane();
		/****************** GAME STATISTICS *******************/
		Label playerOneBaseLabel = new Label("PLAYER ONE POINTS : ");
		Label playerTwoBaseLabel = new Label("PLAYER TWO POINTS : ");
		Label gameTimerLabel = new Label("GAME TIMER : ");
		Label gameMovesLabel = new Label("GAME MOVES: ");

		/******* Create imageViews ***********/

		/******* Adjust Size **********/

		/****** Adding captured Pieces array to HBoxes ******/

		playerOnePointsLabel.setText(String.valueOf(game.getPlayer1().getPoints()));
		playerTwoPointsLabel.setText(String.valueOf(game.getPlayer2().getPoints()));

		Timer timer = new Timer();
		timer.scheduleAtFixedRate(new TimerTask() {
			@Override
			public void run() {
				Platform.runLater(new Runnable() {
					@Override
					public void run() {
						timeCurrentSeconds = timeTotalSeconds % 60;
						timeCurrentMinutes = (timeTotalSeconds / 60) % 60;
						timeCurrentHours = (timeTotalSeconds / 60) / 60;

						if (timeCurrentSeconds < 10 && timeCurrentMinutes < 10 && timeCurrentHours < 10) // if all three
																											// are less
																											// than ten
						{
							gameTimer.setText(
									"0" + timeCurrentHours + ":0" + timeCurrentMinutes + ":0" + timeCurrentSeconds); // show
																														// zero
																														// before
																														// each
																														// number
						}

						else if (timeCurrentSeconds < 10 || timeCurrentMinutes < 10 || timeCurrentHours < 10) // if any
																												// one
																												// of
																												// them
																												// are
																												// less
																												// than
																												// ten
						{ // do combination three choose two (three different unique combinations of two
							// items from three)
							if (timeCurrentSeconds < 10 && timeCurrentMinutes < 10) {
								gameTimer.setText(
										timeCurrentHours + ":0" + timeCurrentMinutes + ":0" + timeCurrentSeconds);
							}

							else if (timeCurrentMinutes < 10 && timeCurrentHours < 10) {
								gameTimer.setText(
										"0" + timeCurrentHours + ":0" + timeCurrentMinutes + ":" + timeCurrentSeconds);
							}

							else if (timeCurrentSeconds < 10 && timeCurrentHours < 10) {
								gameTimer.setText(
										"0" + timeCurrentHours + ":" + timeCurrentMinutes + ":0" + timeCurrentSeconds);
							}
						}

						else // if all three are greater or equal to ten
						{ // don't show zero before number
							gameTimer.setText(timeCurrentHours + ":" + timeCurrentMinutes + ":" + timeCurrentSeconds);
						}

						timeTotalSeconds++;
					}
				});
			}
		}, 0, 1000); // 1000 milliseconds = 1 second

		gameMoves.setText(String.valueOf(0));
		playerOneBaseLabel.getStyleClass().add("gameStats");
		playerTwoBaseLabel.getStyleClass().add("gameStats");
		gameTimer.getStyleClass().add("gameStats");
		gameMovesLabel.getStyleClass().add("gameStats");
		gameTimerLabel.getStyleClass().add("gameStats");
		playerOnePointsLabel.getStyleClass().add("gameStats");
		playerTwoPointsLabel.getStyleClass().add("gameStats");
		gameMoves.getStyleClass().add("gameStats");

		allPlayerStats.getChildren().addAll(playerOneBaseLabel, playerOnePointsLabel, playerTwoBaseLabel,
				playerTwoPointsLabel);
		allGamestats.getChildren().addAll(gameMovesLabel, gameMoves, gameTimerLabel, gameTimer);
		allGameInfo.getChildren().addAll(allPlayerStats, allGamestats);
		allGameInfo.setPrefWidth(512);

		/***************** POWER UPS ****************************/
		Label pwrUp = new Label("POWER UPS");
		Label rk1 = new Label("RANK 1");
		Label rk2 = new Label("RANK 2");
		Label rk3 = new Label("RANK 3");
		rk1.getStyleClass().add("powerUpLabels");
		rk2.getStyleClass().add("powerUpLabels");
		rk3.getStyleClass().add("powerUpLabels");
		pwrUpLabel.getChildren().add(pwrUp);
		pwrUpLabel.setAlignment(Pos.CENTER);

		statsLabel.getChildren().add(gameStatsLabel);
		pwrUp.setAlignment(Pos.CENTER_LEFT);
		pwrUpLabel.setPrefWidth(245);
		statsLabel.setPrefWidth(245);
		statsLabel.setAlignment(Pos.CENTER);
		rank2Box.setBackground(new Background(new BackgroundFill(Color.LIGHTGRAY, CornerRadii.EMPTY, Insets.EMPTY)));
		allPlayerStats
				.setBackground(new Background(new BackgroundFill(Color.LIGHTGRAY, CornerRadii.EMPTY, Insets.EMPTY)));
		allGamestats
				.setBackground(new Background(new BackgroundFill(Color.LIGHTGRAY, CornerRadii.EMPTY, Insets.EMPTY)));
		allGamestats.setAlignment(Pos.CENTER);
		allPlayerStats.setAlignment(Pos.CENTER);

		stats.getChildren().addAll(statsLabel, allGameInfo);

		rank2Box.getChildren().addAll(rk1, rank1, rk2, rank2, rk3, rank3);
		rank2Box.setAlignment(Pos.CENTER);
		rank2Box.setPrefWidth(512);
		ranks.getChildren().addAll(pwrUpLabel, rank2Box);
		contents.getChildren().addAll(ranks, stats);
		contents.setPrefHeight(125);
		contents.setPrefWidth(1000);
		ranks.setPrefWidth(1000);
		stats.setPrefWidth(1000);
		bottom.getChildren().add(contents);
		pwrUp.setFont(arcadeFont);
		pwrUp.getStyleClass().add("player1name");
		try {
			Image img = new Image(new FileInputStream("assets/arcadeChessWording.png"));
			ImageView view = new ImageView(img);
			top.getChildren().add(view);
		} catch (Exception e) {
			e.printStackTrace();
		}

		for (int i = 0; i < 8; i++) {
			for (int j = 0; j < 8; j++) {
				/*********** Initializes ChessBoardButton with Piece ***********/
				ChessBoardButton chessButton = new ChessBoardButton(game.getBoard().getPieceGrid()[i][j]);
				/************* Sets the preferred Width and Height *************/
				chessButton.setPrefHeight(64);
				chessButton.setPrefWidth(64);
				if ((i + j) % 2 != 0) {
					chessButton.setBackground(
							new Background(new BackgroundFill(Color.BLACK, CornerRadii.EMPTY, Insets.EMPTY)));
					chessButton.setColor("black");
				} else {
					chessButton.setBackground(
							new Background(new BackgroundFill(Color.WHITE, CornerRadii.EMPTY, Insets.EMPTY)));
					chessButton.setColor("white");
				}
				chessBoardButtons[i][j] = chessButton;
			}
		}

		for (int i = 0; i < 8; i++) {
			for (int j = 0; j < 8; j++) {
				GridPane.setConstraints(chessBoardButtons[i][j], j, i);
				chessGrid.getChildren().add(chessBoardButtons[i][j]);
			}
		}

		top.getChildren().add(gameTitle);
		centerMidChess.getChildren().addAll(chessGrid);

		top.setAlignment(Pos.CENTER);
		top.getStyleClass().add("arcadechessfont");
		centerMidChess.setPrefHeight(512);
		centerMidChess.setPrefWidth(512);

		leftMid.setPrefWidth(250);
		rightMid.setPrefWidth(250);

		/***** set preferred height for HBox *******/
		capturedRowLeft1.setPrefHeight(30);
		capturedRowLeft2.setPrefHeight(30);
		capturedRowLeft3.setPrefHeight(30);
		capturedRowLeft4.setPrefHeight(30);

		capturedRowRight1.setPrefHeight(30);
		capturedRowRight2.setPrefHeight(30);
		capturedRowRight3.setPrefHeight(30);
		capturedRowRight4.setPrefHeight(30);

		/****** Set Hbox alignment ********/
		capturedRowLeft1.setAlignment(Pos.CENTER);
		capturedRowLeft2.setAlignment(Pos.CENTER);
		capturedRowLeft3.setAlignment(Pos.CENTER);
		capturedRowLeft4.setAlignment(Pos.CENTER);

		capturedRowRight1.setAlignment(Pos.CENTER);
		capturedRowRight2.setAlignment(Pos.CENTER);
		capturedRowRight3.setAlignment(Pos.CENTER);
		capturedRowRight4.setAlignment(Pos.CENTER);

		/*** Added all elements to mid left and mid right panels ****/
		leftMid.getChildren().addAll(playOneNameLabel, playOneWLLabel, capturedPieceLabel, capturedRowLeft1,
				capturedRowLeft2, capturedRowLeft3, capturedRowLeft4);
		rightMid.getChildren().addAll(playTwoNameLabel, playTwoWLLabel, capturedPieceLabel2, capturedRowRight1,
				capturedRowRight2, capturedRowRight3, capturedRowRight4);

		top.setPrefHeight(120);
		bottom.setPrefHeight(125);
		bottom.setPrefWidth(1000);

		top.setBackground(new Background(new BackgroundFill(Color.LIGHTGRAY, CornerRadii.EMPTY, Insets.EMPTY)));
		bottom.setBackground(new Background(new BackgroundFill(Color.LIGHTGRAY, CornerRadii.EMPTY, Insets.EMPTY)));
		centerMidChess
				.setBackground(new Background(new BackgroundFill(Color.LIGHTGRAY, CornerRadii.EMPTY, Insets.EMPTY)));
		rightMid.setBackground(new Background(new BackgroundFill(Color.LIGHTGRAY, CornerRadii.EMPTY, Insets.EMPTY)));
		leftMid.setBackground(new Background(new BackgroundFill(Color.LIGHTGRAY, CornerRadii.EMPTY, Insets.EMPTY)));

		middle.getChildren().addAll(leftMid, centerMidChess, rightMid);
		main.getChildren().addAll(menuBar, top, middle, bottom);

		Scene scene2 = new Scene(main, 1000, 750);

		scene2.getStylesheets().add(getClass().getResource("application.css").toExternalForm());

		if (gameModeType.equals("classic")) {
			rank1.setVisible(false);
			rank2.setVisible(false);
			rank3.setVisible(false);
			pwrUp.setVisible(false);
			pwrUpLabel.setVisible(false);
			rk1.setVisible(false);
			rk2.setVisible(false);
			rk3.setVisible(false);
		}
		defaultButtonSetup();
		determinePowerUps();
		return scene2;
	}

	public Scene splashScene() {
		MenuBar menuBar = new MenuBar();
		VBox main = new VBox();
		HBox mainImage = new HBox();
		Menu menuFile = new Menu("Menu");
		main.setBackground(new Background(new BackgroundFill(Color.web("#a0a0a0"), CornerRadii.EMPTY, Insets.EMPTY)));
		MenuItem menuItem1 = new MenuItem("New");
		menuItem1.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent arg0) {
				// Button do stuff
				primaryStage.setScene(startScene);
				primaryStage.show();
			}
		});

		MenuItem menuItem2 = new MenuItem("Load");
		menuItem2.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent arg0) {
				isLoaded = true;
				FileChooser chooser = new FileChooser();
				String currentPath = Paths.get(".").toAbsolutePath().normalize().toString();
				chooser.setInitialDirectory(new File(currentPath));
				File file = chooser.showOpenDialog(primaryStage);

				if (file != null) {
					fileLoader = new Loader(file.getName());
					Player loadedPlayer1 = fileLoader.getPlayer1ToLoad();
					Player loadedPlayer2 = fileLoader.getPlayer2ToLoad();

					Board loadedBoard = fileLoader.getBoardToLoad();
					game = new Game(loadedPlayer1, loadedPlayer2, loadedBoard);
					game.setGameMode(fileLoader.getGameModeToLoad());
					playerOrComp1 = loadedPlayer1.getPlayerType();
					playerOrComp2 = loadedPlayer2.getPlayerType();

					if (loadedPlayer1.getColor().equals("white")) {
						currentPlayer = loadedPlayer1;
					} else {
						currentPlayer = loadedPlayer2;
					}

					playerName1 = loadedPlayer1.getName();
					playerName2 = loadedPlayer2.getName();
					player1Color = loadedPlayer1.getColor();
					player2Color = loadedPlayer2.getColor();
					gameModeType = fileLoader.getGameModeToLoad();

					startGame();
					System.out.println(currentPlayer.getName());
					primaryStage.setScene(boardScene);
					primaryStage.show();
				}
			}
		});

		MenuItem menuItem3 = new MenuItem("Exit");
		menuItem3.setOnAction(ActionEvent -> {
			System.out.println("Exit from Splash Menu");
			System.exit(0);
		});

		menuFile.getItems().add(menuItem1);
		menuFile.getItems().add(menuItem2);
		menuFile.getItems().add(menuItem3);
		menuBar.getMenus().add(menuFile);
		main.getChildren().addAll(menuBar, mainImage);

		try {
			Image img = new Image(new FileInputStream("assets/arcade_chess_logo.png"));
			ImageView view = new ImageView(img);
			mainImage.getChildren().add(view);
		} catch (Exception e) {
			e.printStackTrace();
		}
		mainImage.setAlignment(Pos.CENTER);
		Scene scene = new Scene(main, 400, 400, Color.web("#a0a0a0"));
		return scene;
	}

	public void settingButtons() {
		/***** Creating the Chess buttons ******/
		setButtonLocations();
		/****** Setting the Images ******/
		for (int i = 0; i < 8; i++) {
			for (int j = 0; j < 8; j++) {
				Image pieceImage;
				try {
					if (game.getBoard().getPieceGrid()[i][j] != null) {
						pieceImage = new Image(
								new FileInputStream("assets/" + game.getBoard().getPieceGrid()[i][j].getImg()));
						chessBoardButtons[i][j].setPiece(game.getBoard().getPieceGrid()[i][j]);
					} else {
						pieceImage = null;
					}
					ImageView imageView = new ImageView(pieceImage);
					imageView.setFitHeight(45);
					imageView.setFitWidth(45);
					chessBoardButtons[i][j].setGraphic(imageView);
				} catch (FileNotFoundException e1) {
					e1.printStackTrace();
				}
			}
		}

		/********** Setting the Chess button's OnClickListeners **************/
		for (int i = 0; i < 8; i++) {
			for (int j = 0; j < 8; j++) {
				ChessBoardButton btn = chessBoardButtons[i][j];
				btn.setOnAction(new EventHandler<ActionEvent>() {
					@Override
					public void handle(ActionEvent e) {
						turn();
						num++;
						if (num % 2 != 0) {
							curRow = btn.getRow();
							curCol = btn.getCol();
							highlightButton(btn);
							highlightLegalMoves(btn);
							if (game.getBoard().getPieceGrid()[curRow][curCol].getName().equals("pawn")) {
								isPawn = true;
							} else {
								isPawn = false;
								disableCapturedPiecesButton();
							}
						} else {
							nextRow = btn.getRow();
							nextCol = btn.getCol();
							System.out.println(" Num: " + num);
							unhighlightLegalMoves();
						}
						if (curRow == nextRow && curCol == nextCol) // if the same button is pushed twice; unselect
						{
							unhighlightButton(btn);
						} else {
							moveThePiece();
							gameMoves.setText(String.valueOf(gameMoveNumber));
							if (playerOrComp1 != 1 || playerOrComp2 != 1
									&& currentPlayer.getColor().equals(computer.getCpuPlayer().getColor())) {
								computerMove();
							}
							turn();
						}
					}
				});
			}
		}

	}

	public void drawCapturedPieces() {
		capturedRowRight1.getChildren().clear();
		capturedRowRight2.getChildren().clear();
		capturedRowRight3.getChildren().clear();
		capturedRowRight4.getChildren().clear();
		capturedRowLeft1.getChildren().clear();
		capturedRowLeft2.getChildren().clear();
		capturedRowLeft3.getChildren().clear();
		capturedRowLeft4.getChildren().clear();

		for (int i = 0; i < game.getBoard().getCapturedPieces().size(); i++) {
			Image pieceImage;
			try {
				pieceImage = new Image(
						new FileInputStream("assets/" + game.getBoard().getCapturedPieces().get(i).getImg()));
				capturedPieceButtons[i] = new ChessBoardButton(game.getBoard().getCapturedPieces().get(i));
				capturedPieceButtons[i].setPrefHeight(25);
				capturedPieceButtons[i].setPrefWidth(25);
				ImageView imageView = new ImageView(pieceImage);
				imageView.setFitHeight(25);
				imageView.setFitWidth(25);
				capturedPieceButtons[i].setGraphic(imageView);
			} catch (FileNotFoundException e1) {
				e1.printStackTrace();
			}

			if (capturedPieceButtons[i].getPiece().getColor().equals(game.getPlayer1().getColor())) {
				if (capturedRowRight1.getChildren().size() < 4) {
					capturedRowRight1.getChildren().add(capturedPieceButtons[i]);
				} else if (capturedRowRight2.getChildren().size() < 4 && capturedRowRight1.getChildren().size() == 4) {
					capturedRowRight2.getChildren().add(capturedPieceButtons[i]);
				} else if (capturedRowRight3.getChildren().size() < 4 && capturedRowRight2.getChildren().size() == 4) {
					capturedRowRight3.getChildren().add(capturedPieceButtons[i]);
				} else {
					capturedRowRight4.getChildren().add(capturedPieceButtons[i]);
				}
			} else {
				if (capturedRowLeft1.getChildren().size() < 4) {
					capturedRowLeft1.getChildren().add(capturedPieceButtons[i]);
				} else if (capturedRowLeft2.getChildren().size() < 4 && capturedRowLeft1.getChildren().size() == 4) {
					capturedRowLeft2.getChildren().add(capturedPieceButtons[i]);
				} else if (capturedRowLeft3.getChildren().size() < 4 && capturedRowLeft2.getChildren().size() == 4) {
					capturedRowLeft3.getChildren().add(capturedPieceButtons[i]);
				} else {
					capturedRowLeft4.getChildren().add(capturedPieceButtons[i]);
				}
			}

		}

		for (int i = 0; i < game.getBoard().getCapturedPieces().size(); i++) {
			ChessBoardButton btn = capturedPieceButtons[i];
			int loc = i;
			Piece promoPiece = capturedPieceButtons[i].getPiece();
			btn.setOnAction(new EventHandler<ActionEvent>() {
				@Override
				public void handle(ActionEvent e) {

					game.getBoard().getPieceGrid()[curRow][curCol] = promoPiece;

					game.getBoard().getCapturedPieces().remove(loc);

					updateGameBoard(curRow, curCol, curRow, curCol);

					drawCapturedPieces();
				}
			});
		}

		if (!hasPawnReachedEnd()) {
			disableCapturedPiecesButton();
		}
	}

	public void disableCapturedPiecesButton() {
		for (int i = 0; i < game.getBoard().getCapturedPieces().size(); i++) {
			capturedPieceButtons[i].setDisable(true);
		}
	}

	public void enableCapturedPiecesButton() {
		for (int i = 0; i < game.getBoard().getCapturedPieces().size(); i++) {
			if (currentPlayer.getColor().equals(capturedPieceButtons[i].getPiece().getColor())) {
				capturedPieceButtons[i].setDisable(false);
			} else {
				capturedPieceButtons[i].setDisable(true);
			}
		}
	}

	public boolean hasPawnReachedEnd() {
		if (chessBoardButtons[4][1].getPiece() != null) {
			System.out.println("Row 4 and Col 1" + chessBoardButtons[4][1].getPiece().getName());
		} else {
			System.out.println("Row 4 and Col 1 is null");
		}

		boolean pawnEnd = false;
		for (int i = 0; i < 8; i++) {
			// For top of the board
			if (game.getBoard().getPieceGrid()[0][i] != null
					&& game.getBoard().getPieceGrid()[0][i].getName().equals("pawn")) {

				if (isPawn && (currentPlayer.getColor().equals("white")
						&& game.getBoard().getPieceGrid()[0][i].getColor().equals("white"))) {
					System.out.println("button piece is not null " + game.getBoard().getPieceGrid()[0][i].getName()
							+ " " + chessBoardButtons[0][i].getPiece().getColor());
					enableCapturedPiecesButton();
					pawnEnd = true;
				}
			}
			// For bottom of the board
			if (game.getBoard().getPieceGrid()[7][i] != null
					&& game.getBoard().getPieceGrid()[7][i].getName().equals("pawn")) {
				if (isPawn && (currentPlayer.getColor().equals("black")
						&& game.getBoard().getPieceGrid()[7][i].getColor().equals("black"))) {
					System.out.println("button piece is not null " + game.getBoard().getPieceGrid()[7][i].getName()
							+ " " + chessBoardButtons[7][i].getPiece().getColor());
					enableCapturedPiecesButton();
					pawnEnd = true;
				}
			}

		}

		return pawnEnd;
	}

	public void setRankButtons() {
		rank1.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent s) {
				for (int i = 0; i < 8; i++) {
					for (int j = 0; j < 8; j++) {
						if (game.getBoard().getPieceGrid()[i][j] != null
								&& !game.getBoard().getPieceGrid()[i][j].getColor().equals(currentPlayer.getColor())
								&& game.getBoard().getPieceGrid()[i][j].getRank() == 1) {
							game.getBoard().addToCapturedPieces(game.getBoard().getPieceGrid()[i][j]);
							game.getBoard().getPieceGrid()[i][j] = null;
							chessBoardButtons[i][j].setGraphic(null);

						}
					}
				}
				if (!game.getPlayer1().getColor().equals(currentPlayer.getColor())) {
					game.getPlayer2().deductPoints(1000);
				} else {
					game.getPlayer1().deductPoints(1000);
				}
				if (currentPlayer.getColor().equals("white")) {
					currentPlayer.setColor("black");
					determinePowerUps();
					gameMoveNumber++;
				} else {
					currentPlayer.setColor("white");
					determinePowerUps();
					gameMoveNumber++;
				}
				playerOnePointsLabel.setText(String.valueOf(game.getPlayer1().getPoints()));
				playerTwoPointsLabel.setText(String.valueOf(game.getPlayer2().getPoints()));

				drawCapturedPieces();
				isInCheck();
				isInCheckmate();
			}
		});

		rank2.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent s) {
				for (int i = 0; i < 8; i++) {
					for (int j = 0; j < 8; j++) {
						if (game.getBoard().getPieceGrid()[i][j] != null
								&& !game.getBoard().getPieceGrid()[i][j].getColor().equals(currentPlayer.getColor())
								&& game.getBoard().getPieceGrid()[i][j].getRank() == 2) {
							game.getBoard().addToCapturedPieces(game.getBoard().getPieceGrid()[i][j]);
							game.getBoard().getPieceGrid()[i][j] = null;
							chessBoardButtons[i][j].setGraphic(null);

						}
					}
				}
				if (!game.getPlayer1().getColor().equals(currentPlayer.getColor())) {
					game.getPlayer2().deductPoints(1250);
				} else {
					game.getPlayer1().deductPoints(1250);
				}
				if (currentPlayer.getColor().equals("white")) {
					currentPlayer.setColor("black");
					determinePowerUps();
					gameMoveNumber++;
				} else {
					currentPlayer.setColor("white");
					determinePowerUps();
					gameMoveNumber++;
				}
				playerOnePointsLabel.setText(String.valueOf(game.getPlayer1().getPoints()));
				playerTwoPointsLabel.setText(String.valueOf(game.getPlayer2().getPoints()));
				drawCapturedPieces();
				isInCheck();
			}
		});

		rank3.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent s) {
				for (int i = 0; i < 8; i++) {
					for (int j = 0; j < 8; j++) {
						if (game.getBoard().getPieceGrid()[i][j] != null
								&& !game.getBoard().getPieceGrid()[i][j].getColor().equals(currentPlayer.getColor())
								&& game.getBoard().getPieceGrid()[i][j].getRank() == 3) {
							game.getBoard().addToCapturedPieces(game.getBoard().getPieceGrid()[i][j]);
							game.getBoard().getPieceGrid()[i][j] = null;
							chessBoardButtons[i][j].setGraphic(null);

						}
					}
				}
				if (!game.getPlayer1().getColor().equals(currentPlayer.getColor())) {
					game.getPlayer2().deductPoints(1500);
				} else {
					game.getPlayer1().deductPoints(1500);
				}
				if (currentPlayer.getColor().equals("white")) {
					currentPlayer.setColor("black");
					determinePowerUps();
					gameMoveNumber++;
				} else {
					currentPlayer.setColor("white");
					determinePowerUps();
					gameMoveNumber++;
				}
				playerOnePointsLabel.setText(String.valueOf(game.getPlayer1().getPoints()));
				playerTwoPointsLabel.setText(String.valueOf(game.getPlayer2().getPoints()));
				drawCapturedPieces();
				isInCheck();
			}
		});
	}

	public void turn() {
		for (int i = 0; i < 8; i++) {
			for (int j = 0; j < 8; j++) {
				if (chessBoardButtons[i][j].getPiece() != null
						&& !chessBoardButtons[i][j].getPiece().getColor().equals(currentPlayer.getColor())) {
					errorMessage.setContentText("It is not your turn");
				}
			}
		}
	}

	public void setButtonLocations() {
		for (int i = 0; i < 8; i++) {
			for (int j = 0; j < 8; j++) {
				chessBoardButtons[i][j].setLocation(i, j);
			}
		}
	}

	public void highlightButton(ChessBoardButton btn) {
		Color color;
		// if white space
		if (btn.getColor() == "white") {
			color = Color.web("fcf483");
		}
		// if black space
		else {
			color = Color.web("adab2b");
		}

		btn.setBackground(new Background(new BackgroundFill(color, CornerRadii.EMPTY, Insets.EMPTY)));
	}

	public void unhighlightButton(ChessBoardButton btn) {
		if (btn.getColor() == "white") {
			btn.setBackground(new Background(new BackgroundFill(Color.WHITE, CornerRadii.EMPTY, Insets.EMPTY)));
		} else
			btn.setBackground(new Background(new BackgroundFill(Color.BLACK, CornerRadii.EMPTY, Insets.EMPTY)));
	}

	public void highlightLegalMoves(ChessBoardButton btn) {
		Color color;
		ChessBoardButton buttonToHighlight;

		for (int i = 0; i < 8; i++) {
			for (int j = 0; j < 8; j++) {
				buttonToHighlight = chessBoardButtons[i][j];
				if (game.getBoard().isLegalMove(btn.getRow(), btn.getCol(), i, j)) {
					if (buttonToHighlight.getColor() == "white") {
						color = Color.web("66dd66");
						buttonToHighlight.setBackground(
								new Background(new BackgroundFill(color, CornerRadii.EMPTY, Insets.EMPTY)));
					} else {
						color = Color.web("339933");
						buttonToHighlight.setBackground(
								new Background(new BackgroundFill(color, CornerRadii.EMPTY, Insets.EMPTY)));
					}
				}
			}
		}
	}

	public void unhighlightLegalMoves() {
		for (int i = 0; i < 8; i++) {
			for (int j = 0; j < 8; j++) {
				if (chessBoardButtons[i][j].getColor() == "white") {
					chessBoardButtons[i][j].setBackground(
							new Background(new BackgroundFill(Color.WHITE, CornerRadii.EMPTY, Insets.EMPTY)));
				} else {
					chessBoardButtons[i][j].setBackground(
							new Background(new BackgroundFill(Color.BLACK, CornerRadii.EMPTY, Insets.EMPTY)));
				}
			}
		}
	}

	public void moveThePiece() {
		if (gameMoveNumber == 1) {
			setRankButtons();
		}
		Piece temp = game.getBoard().getPieceGrid()[nextRow][nextCol];
		if (num % 2 == 0 && num != 0) {
			if (game.getBoard().isLegalMove(curRow, curCol, nextRow, nextCol)) {
				if (game.getBoard().getPieceGrid()[curRow][curCol].getColor().equals(currentPlayer.getColor())) {
					if (!gameModeType.equals("classic")) {
						System.out.println("Current Player color: " + currentPlayer.getColor());
						System.out.println("First Player color: " + game.getPlayer1().getColor() + " Second Player: "
								+ game.getPlayer2().getColor());
						if (game.getBoard().getPieceGrid()[nextRow][nextCol] != null) {
							if (game.getPlayer1().getColor().equals(currentPlayer.getColor())) {
								game.getPlayer1()
										.addPoints(game.getBoard().getPieceGrid()[nextRow][nextCol].getValue());
								playerOnePointsLabel.setText(String.valueOf(game.getPlayer1().getPoints()));
							} else if (game.getPlayer2().getColor().equals(currentPlayer.getColor())) {
								game.getPlayer2()
										.addPoints(game.getBoard().getPieceGrid()[nextRow][nextCol].getValue());
								playerTwoPointsLabel.setText(String.valueOf(game.getPlayer2().getPoints()));
							}
						}
					}
					game.getBoard().move(curRow, curCol, nextRow, nextCol);// move of player
					// note will need to update board after cpu move to update board
					updateGameBoard(curRow, curCol, nextRow, nextCol);
					drawCapturedPieces();

					System.out.println("Moved Piece to: " + nextRow + " " + nextCol + " Num: " + num);

					// System.out.println("check if pawn reaches end: " + hasPawnReachedEnd());

					if (playerOrComp1 != 1 || playerOrComp2 != 1) {
						computer.findAvailablePieces(game.getBoard());// move of cpu
						computer.determineMove(game.getBoard());
						updateGameBoard(computer.getCurrentRow(), computer.getCurrentCol(), computer.getNextRow(),
								computer.getNextCol());
						drawCapturedPieces();
						if (currentPlayer.getColor().equals("white")) {
							currentPlayer.setColor("black");
							determinePowerUps();
						} else {
							currentPlayer.setColor("white");
							determinePowerUps();
						}
					}
					if (!playerPutSelfInCheck(curRow, curCol, nextRow, nextCol, temp)) {
						if (currentPlayer.getColor().equals("white")) {
							currentPlayer.setColor("black");
							determinePowerUps();
							gameMoveNumber++;
						} else {
							currentPlayer.setColor("white");
							determinePowerUps();
							gameMoveNumber++;
						}
					}
					isInCheck();
				} else {
					errorMessage.setContentText("It's not your turn.");
					errorMessage.showAndWait();
					unhighlightButton(chessBoardButtons[curRow][curCol]);
				}
			} else {
				errorMessage.setContentText("Not a valid move.");
				errorMessage.showAndWait();
				unhighlightButton(chessBoardButtons[curRow][curCol]);
			}
		}
	}

	public void computerMove() {
		Piece temp = computer.getCpuPiece();
		try {
			TimeUnit.SECONDS.sleep(1 / 2); // To add delay to action
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		do {
			computer.findAvailablePieces(game.getBoard());// move of cpu
			computer.determineMove(game.getBoard());
			updateGameBoard(computer.getCurrentRow(), computer.getCurrentCol(), computer.getNextRow(),
					computer.getNextCol());
		} while (playerPutSelfInCheck(computer.getCurrentRow(), computer.getCurrentCol(), computer.getNextRow(),
				computer.getNextCol(), temp));

		if (currentPlayer.getColor().equals("white")) {
			currentPlayer.setColor("black");
			determinePowerUps();
		} else {
			currentPlayer.setColor("white");
			determinePowerUps();
		}
		isInCheck();

	}

	public void isInCheck() {
		if (game.check("black", true)) { // white is in check
			errorMessage.setContentText("White is in check!");
			errorMessage.showAndWait();
			isInCheckmate();
		}
		if (game.check("white", true)) { // black is in check
			errorMessage.setContentText("Black is in check!");
			errorMessage.showAndWait();
			isInCheckmate();

		}
	}

	public void isInCheckmate() {
		if (game.getIsCheckmated()) {
			ButtonType exit = new ButtonType("Exit Game", ButtonBar.ButtonData.OK_DONE);
			ButtonType cont = new ButtonType("Continue Game", ButtonBar.ButtonData.OK_DONE);
			Alert a = new Alert(AlertType.CONFIRMATION, "Choose an option: ", exit, cont);
			a.setTitle("Arcade Chess");
			Stage stage = (Stage) a.getDialogPane().getScene().getWindow();
			try {
				stage.getIcons().add(new Image(new FileInputStream("assets/arcade_chess_logo.png")));
			} catch (Exception ex) {
				ex.printStackTrace();
			}
			a.setHeaderText("");
			a.setContentText("YOU ARE IN CHECKMATE! Game Over.");
			Optional<ButtonType> checkMatePopup = a.showAndWait();
			if (checkMatePopup.orElse(exit) == cont) {
				gameMoveNumber = 0;
				gameMoves.setText(Integer.toString(gameMoveNumber));

				timeTotalSeconds = 0;
				timeCurrentSeconds = 0;
				timeCurrentMinutes = 0;
				timeCurrentHours = 0;
				num = 0;
				game.getPlayer1().setPoints(0);
				game.getPlayer2().setPoints(0);
				determinePowerUps();

				if (currentPlayer.getColor().equals(game.getPlayer1().getColor())) {
					game.getPlayer2().addToWins();
					game.getPlayer1().addToLoses();
				} else {

					game.getPlayer1().addToWins();
					game.getPlayer2().addToLoses();
				}

				playOneWLLabel.setText("WINS: " + Integer.toString(game.getPlayer1().getWins()) + "" + "    LOSES: "
						+ Integer.toString(game.getPlayer1().getLoses()));

				playTwoWLLabel.setText("WINS: " + Integer.toString(game.getPlayer2().getWins()) + "" + "    LOSES: "
						+ Integer.toString(game.getPlayer2().getLoses()));

				currentPlayer.setColor("white");
				resetGameBoard();
				drawCapturedPieces();
				game.setIsCheckmated(false);
			} else {
				System.exit(0);
			}

		}
	}

	public boolean playerPutSelfInCheck(int curRow, int curCol, int nextRow, int nextCol, Piece capturedPiece) {
		Piece tmp = game.getBoard().getPieceGrid()[nextRow][nextCol]; // piece that is trying to capture capturePiece
		// if you put yourself into check and are black
		if (!((playerOrComp1 != 1 || playerOrComp2 != 1)
				&& ((game.check("white") && currentPlayer.getColor().equals("black")
						&& currentPlayer.getColor().equals(computer.getCpuPlayer().getColor()))
						|| (game.check("black") && currentPlayer.getColor().equals("white")
								&& currentPlayer.getColor().equals(computer.getCpuPlayer().getColor()))))) {

			if (game.check("white") && currentPlayer.getColor().equals("black")) {
				errorMessage.setContentText("Illegal move");
				errorMessage.showAndWait();
				// move piece back
				game.getBoard().getPieceGrid()[curRow][curCol] = tmp;
				game.getBoard().getPieceGrid()[nextRow][nextCol] = capturedPiece;
				updateGameBoard(curRow, curCol, nextRow, nextCol);
				return true;
				// if you put yourself into check and are white
			} else if (game.check("black") && currentPlayer.getColor().equals("white")) {
				errorMessage.setContentText("Illegal Move");
				errorMessage.showAndWait();
				game.getBoard().getPieceGrid()[curRow][curCol] = tmp;
				game.getBoard().getPieceGrid()[nextRow][nextCol] = capturedPiece;
				updateGameBoard(curRow, curCol, nextRow, nextCol);
				return true;
			}

			return false;
		} else {
			return true;
		}
	}

	public void updateGameBoard(int curRow, int curCol, int nextRow, int nextCol) {
		Image pieceImage;
		Piece temp = chessBoardButtons[curRow][curCol].getPiece();
		chessBoardButtons[curRow][curCol].setPiece(game.getBoard().getPieceGrid()[nextRow][nextCol]);
		chessBoardButtons[nextRow][nextCol].setPiece(temp);
		try {
			for (int i = 0; i < 8; i++) {
				for (int j = 0; j < 8; j++) {
					if (game.getBoard().getPieceGrid()[i][j] != null) {
						pieceImage = new Image(
								new FileInputStream("assets/" + game.getBoard().getPieceGrid()[i][j].getImg()));
						ImageView imageView = new ImageView(pieceImage);
						imageView.setFitHeight(45);
						imageView.setFitWidth(45);
						chessBoardButtons[i][j].setGraphic(imageView);
					} else {
						chessBoardButtons[i][j].setGraphic(null);
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		unhighlightButton(chessBoardButtons[curRow][curCol]);
	}

	public void resetGameBoard() {
		Image pieceImage;
		game.getBoard().getCapturedPieces().clear();

		for (int i = 0; i < 8; i++) {
			for (int j = 0; j < 8; j++) {
				game.getBoard().getPieceGrid()[i][j] = defaultPieces[i][j];
			}
		}

		for (int i = 0; i < 8; i++) {
			for (int j = 0; j < 8; j++) {
				chessBoardButtons[i][j].setPiece(game.getBoard().getPieceGrid()[i][j]);
			}
		}

		try {
			for (int i = 0; i < 8; i++) {
				for (int j = 0; j < 8; j++) {
					if (game.getBoard().getPieceGrid()[i][j] != null) {
						pieceImage = new Image(
								new FileInputStream("assets/" + game.getBoard().getPieceGrid()[i][j].getImg()));
						ImageView imageView = new ImageView(pieceImage);
						imageView.setFitHeight(45);
						imageView.setFitWidth(45);
						chessBoardButtons[i][j].setGraphic(imageView);
					} else {
						chessBoardButtons[i][j].setGraphic(null);
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		unhighlightButton(chessBoardButtons[curRow][curCol]);
		unhighlightLegalMoves();

	}

	public void determinePowerUps() {

		if (!currentPlayer.getColor().equals(game.getPlayer1().getColor())) {
			if (game.getPlayer2().getPoints() < 1000) {
				rank1.setDisable(true);
				rank2.setDisable(true);
				rank3.setDisable(true);
			} else if ((game.getPlayer2().getPoints() >= 1000) && (game.getPlayer2().getPoints() < 1250)) {
				rank1.setDisable(false);
				rank2.setDisable(true);
				rank3.setDisable(true);
			} else if ((game.getPlayer2().getPoints() >= 1250) && (game.getPlayer2().getPoints() < 1500)) {
				rank1.setDisable(false);
				rank2.setDisable(false);
				rank3.setDisable(true);
			} else if (game.getPlayer2().getPoints() >= 1500) {
				rank1.setDisable(false);
				rank2.setDisable(false);
				rank3.setDisable(false);
			}
		} else {
			if (game.getPlayer1().getPoints() < 1000) {
				rank1.setDisable(true);
				rank2.setDisable(true);
				rank3.setDisable(true);
			} else if ((game.getPlayer1().getPoints() >= 1000) && (game.getPlayer1().getPoints() < 1250)) {
				rank1.setDisable(false);
				rank2.setDisable(true);
				rank3.setDisable(true);
			} else if ((game.getPlayer1().getPoints() >= 1250) && (game.getPlayer1().getPoints() < 1500)) {
				rank1.setDisable(false);
				rank2.setDisable(false);
				rank3.setDisable(true);
			} else if (game.getPlayer1().getPoints() >= 1500) {
				rank1.setDisable(false);
				rank2.setDisable(false);
				rank3.setDisable(false);
			}
		}
	}

	public void defaultButtonSetup() {
		for (int i = 0; i < 8; i++) {
			for (int j = 0; j < 8; j++) {
				defaultPieces[i][j] = game.getBoard().getPieceGrid()[i][j];
			}
		}
	}

	public static void main(String[] args) {
		launch(args);

	}
}
