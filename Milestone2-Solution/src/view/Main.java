package view;

import java.awt.Point;
import java.io.File;
import java.io.IOException;
import java.util.concurrent.atomic.AtomicReference;

import engine.Game;
import exceptions.InvalidTargetException;
import exceptions.MovementException;
import exceptions.NoAvailableResourcesException;
import exceptions.NotEnoughActionsException;
import javafx.animation.FadeTransition;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ToggleButton;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;
import javafx.util.Duration;
import model.characters.Direction;
import model.characters.Explorer;
import model.characters.Fighter;
import model.characters.Hero;
import model.characters.Medic;
import model.characters.Zombie;
import model.collectibles.Vaccine;
import model.world.CharacterCell;
import model.world.CollectibleCell;
import model.world.TrapCell;
import view.*;

public class Main extends Application {
	private Stage primaryStage;
	static Hero h = null;
	static boolean brute = false;

	public static void main(String[] args) {
//		  try {
//				String path="./ahmed.mp3";
//
//	            Media media = new Media(new File(path).toURI().toString());
//	            MediaPlayer mediaPlayer = new MediaPlayer(media);
//	            mediaPlayer.play();
//	        } catch (Exception e) {
//	            e.printStackTrace();
//	        }
		launch(args);

	}

	public void start(Stage primaryStage) throws IOException, MovementException, NotEnoughActionsException {
		this.primaryStage = primaryStage;
		Scene one = startScene1();

		primaryStage.setScene(one);
		primaryStage.setTitle("The Last Of Us Legacy");
		primaryStage.setFullScreen(true);
		primaryStage.show();

	}

	public Scene startScene1() throws IOException, MovementException, NotEnoughActionsException {
		primaryStage.setFullScreen(true);
		primaryStage.setTitle("The Last Of Us Legacy");
		Image backgroundImage = new Image(
				"C:\\Users\\Yousef Elbrolosy\\Desktop\\GUC\\Semester 4\\Milestone2-Solution\\images\\background.jpg");
		ImageView backgroundImageView = new ImageView(backgroundImage);
		Button right = new Button("Start Game");
		Font font = Font.font("Calibri", FontWeight.BOLD, 36);
		right.setFont(font);
		right.setDefaultButton(false);
		right.setStyle(
				"-fx-background-color: #eee; -fx-background-radius: 50px; -fx-text-fill: #323232;  -fx-padding: 10px 20px; -fx-cursor: hand;");
		right.setOnMouseEntered(event -> {
			right.setStyle(
					"-fx-background-color: #2B4365; -fx-background-radius: 50px; -fx-text-fill: #fff;  -fx-padding: 10px 20px; -fx-cursor: hand;");

		});
		right.setOnMouseExited(event -> {
			right.setStyle(
					"-fx-background-color: #eee; -fx-background-radius: 50px; -fx-text-fill: #323232;  -fx-padding: 10px 20px; -fx-cursor: hand;");

		});

		StackPane layout = new StackPane();
		layout.getChildren().addAll(backgroundImageView, right);
		StackPane.setAlignment(right, Pos.BOTTOM_CENTER);

		layout.setPadding(new Insets(100));
		Scene two = startScene2();
		right.setOnAction(event -> {
			FadeTransition fadeTransition = new FadeTransition(Duration.millis(500), primaryStage.getScene().getRoot());
			fadeTransition.setFromValue(1.0);
			fadeTransition.setToValue(0.0);
			fadeTransition.setOnFinished(e -> {
				primaryStage.setScene(two);
				primaryStage.setFullScreen(true);
			});
			fadeTransition.play();

		});
		primaryStage.setFullScreen(true);

		Scene scene = new Scene(layout, backgroundImage.getWidth(), backgroundImage.getHeight());
		return scene;

	}

	public Scene startScene2() throws IOException, MovementException, NotEnoughActionsException {
		primaryStage.setFullScreen(true);
		primaryStage.setTitle("The Last Of Us Legacy");
		Image backgroundImage = new Image(
				"C:\\Users\\Yousef Elbrolosy\\Desktop\\GUC\\Semester 4\\Milestone2-Solution\\images\\hereoss.png");
		ImageView backgroundImageView = new ImageView(backgroundImage);
		Game.loadHeroes("C:\\Users\\Yousef Elbrolosy\\Desktop\\GUC\\Semester 4\\Milestone2-Solution\\Heroes.csv");
		int numColumns = 4;

		GridPane gridPane = new GridPane();
		gridPane.setHgap(10);
		gridPane.setVgap(10);
		gridPane.setAlignment(Pos.CENTER);
		Label title = newLabel("Select a Hero", 40, FontWeight.BOLD, Color.WHITE);
		int i = 0;
		StackPane[] selectedCard = new StackPane[1];
		int[] index = new int[1];
		boolean flag[] = { false };
		for (Hero hero : Game.availableHeroes) {
			String name = hero.getName();
			int maxHp = hero.getMaxHp();
			int attackDmg = hero.getAttackDmg();
			String type = (hero instanceof Fighter) ? "Fighter" : (hero instanceof Medic) ? "Medic" : "Explorer";
			int maxActions = hero.getMaxActions();
			Rectangle card = new Rectangle(250, 200, Color.TRANSPARENT);
			StackPane cardPane = new StackPane(card);

			cardPane.setAlignment(Pos.CENTER);
//			cardPane.setOnMouseEntered(event -> {
//				cardPane.setStyle("-fx-background-color: #2B4365; -fx-text-fill: #fff; -fx-cursor: crosshair;");
//
//			});
//			cardPane.setOnMouseExited(event -> {
//				card.setFill(Color.TRANSPARENT);
//				cardPane.setStyle("-fx-text-fill: #323232; -fx-cursor: hand;");
//		});

			cardPane.setOnMouseClicked(event -> {
				index[0] = GridPane.getColumnIndex(cardPane) + GridPane.getRowIndex(cardPane) * numColumns;

				card.setFill(Color.DARKORANGE);
				flag[0] = true;
				if (selectedCard[0] != null && !selectedCard[0].equals(cardPane)) {
					((Rectangle) selectedCard[0].getChildren().get(0)).setFill(Color.TRANSPARENT);
				}

				selectedCard[0] = cardPane;
			});
//			cardPane.setOnMouseClicked(event -> {
//				index[0] = GridPane.getColumnIndex(cardPane) + GridPane.getRowIndex(cardPane) * numColumns;
//			card.setStyle("-fx-background-color: #FFA500;");
////				cardPan.setS(Color.web("#FFA500"));
//				flag[0] = true;
//				selectedCard[0] = cardPane;
//				if (selectedCard[0] != null && !selectedCard[0].equals(cardPane)) {
//					((Rectangle) selectedCard[0].getChildren().get(0)).setFill(Color.TRANSPARENT);
//				}
//
//			});

			gridPane.add(cardPane, i % numColumns, i / numColumns);
			Label nameLabel = newLabel(name, 22, FontWeight.BOLD, Color.WHITE);
			Label typeLabel = newLabel(type, 18, FontWeight.NORMAL, Color.WHITE);
			Label maxHpLabel = newLabel("Max HP: " + maxHp, 18, FontWeight.NORMAL, Color.WHITE);
			Label attackDmgLabel = newLabel("Attack Damage: " + attackDmg, 18, FontWeight.NORMAL, Color.WHITE);
			Label maxActionsLabel = newLabel("Max Actions: " + maxActions, 18, FontWeight.NORMAL, Color.WHITE);
			nameLabel.setTranslateY(-50);
			typeLabel.setTranslateY(-20);
			maxHpLabel.setTranslateY(20);
			attackDmgLabel.setTranslateY(50);
			maxActionsLabel.setTranslateY(80);
			cardPane.getChildren().addAll(nameLabel, typeLabel, maxHpLabel, attackDmgLabel, maxActionsLabel);
			i++;
		}

		Button right = new Button("PLAY");
		Font font = Font.font("Calibri", FontWeight.BOLD, 36);
		right.setFont(font);
		right.setDefaultButton(false);
		right.setStyle(
				"-fx-background-color: #eee; -fx-background-radius: 50px; -fx-text-fill: #323232;  -fx-padding: 10px 20px; -fx-cursor: hand;");
		right.setOnMouseEntered(event -> {
			right.setStyle(
					"-fx-background-color: #2B4365; -fx-background-radius: 50px; -fx-text-fill: #fff;  -fx-padding: 10px 20px; -fx-cursor: hand;");

		});
		right.setOnMouseExited(event -> {
			right.setStyle(
					"-fx-background-color: #eee; -fx-background-radius: 50px; -fx-text-fill: #323232;  -fx-padding: 10px 20px; -fx-cursor: hand;");

		});

		StackPane root = new StackPane();
		root.getChildren().addAll(backgroundImageView, gridPane, title, right);
		StackPane.setAlignment(gridPane, Pos.CENTER_LEFT);
		StackPane.setAlignment(title, Pos.TOP_CENTER);
		StackPane.setAlignment(right, Pos.BOTTOM_CENTER);

		root.setPadding(new Insets(100));

		right.setOnMouseClicked(e -> {

			if (flag[0] == true) {
				this.h = Game.availableHeroes.remove(index[0]);
				System.out.println(this.h.getName());
				Game.startGame(h);

				FadeTransition fadeTransition = new FadeTransition(Duration.millis(500),
						primaryStage.getScene().getRoot());
				fadeTransition.setFromValue(1.0);
				fadeTransition.setToValue(0.0);
				fadeTransition.setOnFinished(event -> {

					Scene three;
					try {
						three = startScene3();
						primaryStage.setScene(three);
						primaryStage.setFullScreen(true);
					} catch (MovementException | NotEnoughActionsException e1) {
						Alert alert = new Alert(AlertType.WARNING);
						alert.setTitle("Message");
						alert.setHeaderText(null);
						alert.setContentText(e1.getMessage());

						alert.showAndWait();
					}

				});
				fadeTransition.play();

			} else {
				// Display a message dialog
				Alert alert = new Alert(AlertType.WARNING);
				alert.setTitle("Message");
				alert.setHeaderText(null);
				alert.setContentText("Please select a hero");

				alert.showAndWait();

			}
		});

		primaryStage.setFullScreen(true);

		Scene scene = new Scene(root, backgroundImage.getWidth(), backgroundImage.getHeight());
		return scene;
	}

	public StackPane addnewInfo(Hero x) {
		Label name = newLabel("name: " + x.getName(), 15, FontWeight.BOLD, Color.BLACK);
		int curHp = x.getCurrentHp();
		Label curH = newLabel("CurrentHp " + curHp, 15, FontWeight.BOLD, Color.BLACK);
		int attDmg = x.getAttackDmg();
		int maxAc = x.getMaxActions();
		int actionsAvailabe = x.getActionsAvailable();
		boolean useSpecial = x.isSpecialAction();
		int NoOfVacc = x.getVaccineInventory().size();
		int NoOfSupp = x.getSupplyInventory().size();
		Label attDamage = newLabel("Max damage: " + attDmg, 15, FontWeight.BOLD, Color.BLACK);
		Label maxActions = newLabel("Max actions: " + maxAc, 15, FontWeight.BOLD, Color.BLACK);
		Label actionsAvailabeLabel = newLabel("Actions Available: " + actionsAvailabe, 15, FontWeight.BOLD,
				Color.BLACK);
		Label useSpecialLabel = newLabel("Special Action used: " + useSpecial, 15, FontWeight.BOLD, Color.BLACK);
		Label NoOfVaccLabel = newLabel("Number of Vaccines: " + NoOfVacc, 15, FontWeight.BOLD, Color.BLACK);
		Label NoOfSuppLabel = newLabel("Number of Supples: " + NoOfSupp, 15, FontWeight.BOLD, Color.BLACK);
		String z = (x instanceof Fighter) ? "Fighter" : (x instanceof Explorer) ? "Explorer" : "Medic";
		Label type = newLabel("Hero type: " + z, 15, FontWeight.BOLD, Color.BLACK);

		VBox infoBox = new VBox(name, curH, attDamage, maxActions, type, actionsAvailabeLabel, useSpecialLabel,
				NoOfVaccLabel, NoOfSuppLabel);
		infoBox.setAlignment(Pos.TOP_RIGHT);
		infoBox.setSpacing(5);
		infoBox.setPadding(new Insets(10));

		StackPane card = new StackPane(infoBox);
		StackPane.setAlignment(card, Pos.TOP_RIGHT);
		return card;
	}

	public StackPane addnew() {
		VBox infoBox = new VBox();

		for (Hero x : Game.heroes) {
			Label name = newLabel("name: " + x.getName(), 15, FontWeight.BOLD, Color.BLACK);
			int curHp = x.getCurrentHp();
			Label curH = newLabel("CurrentHp " + curHp, 15, FontWeight.BOLD, Color.BLACK);
			int attDmg = x.getAttackDmg();
			int maxAc = x.getMaxActions();
			Label attDamage = newLabel("Max damage: " + attDmg, 15, FontWeight.BOLD, Color.BLACK);
			Label maxActions = newLabel("Max actions: " + maxAc, 15, FontWeight.BOLD, Color.BLACK);
			String z = (x instanceof Fighter) ? "Fighter" : (x instanceof Explorer) ? "Explorer" : "Medic";
			Label type = newLabel("Hero type: " + z, 15, FontWeight.BOLD, Color.BLACK);
//			    Rectangle card = new Rectangle(250, 200, Color.TRANSPARENT);
			StackPane cardPane = new StackPane();
			cardPane.getChildren().addAll(name, curH, attDamage, maxActions, type);

			infoBox.getChildren().add(cardPane);
			infoBox.setAlignment(Pos.TOP_LEFT);
			infoBox.setSpacing(5);
			infoBox.setPadding(new Insets(10));

		}
		StackPane card = new StackPane(infoBox);
		StackPane.setAlignment(card, Pos.TOP_LEFT);
		return card;
	}

	boolean[] check = { false };

	public Scene startScene3() throws MovementException, NotEnoughActionsException {

		VBox buttonsContainer = new VBox();
		buttonsContainer.setAlignment(Pos.BOTTOM_RIGHT);
		buttonsContainer.setSpacing(10);
		buttonsContainer.setPadding(new Insets(0, 20, 20, 20));

		Button left = new Button("left");
		Button right = new Button("right");
		Button down = new Button("down");
		Button up = new Button("up");
		Button endGame = new Button("End Turn");
		Button cure = new Button("Cure");
		Button setTarget = new Button("Set Target");
		Button attack = new Button("Attack");
		Button useSpecial = new Button("Use Special");
		// Set right styles (you can modify them according to your preferences)
//		right.setStyle("-fx-background-color: #FFFFFF");
//		left.setStyle("-fx-background-color: #FFFFFF");
//		up.setStyle("-fx-background-color: #FFFFFF");
//		down.setStyle("-fx-background-color: #FFFFFF");
//		endGame.setStyle("-fx-background-color: #FF0000");
//		cure.setStyle("-fx-background-color: #FFF200");
//		setTarget.setStyle("-fx-background-color: green");
//		attack.setStyle("-fx-background-color: blue");
//		useSpecial.setStyle("-fx-background-color: orange");
		left.setStyle("-fx-background-color: #2B4365; -fx-text-fill: white; -fx-font-size: 14px; -fx-pref-width: 120px; -fx-pref-height: 40px;");
		right.setStyle("-fx-background-color: #2B4365; -fx-text-fill: white; -fx-font-size: 14px; -fx-pref-width: 120px; -fx-pref-height: 40px;");
		down.setStyle("-fx-background-color: #2B4365; -fx-text-fill: white; -fx-font-size: 14px; -fx-pref-width: 120px; -fx-pref-height: 40px;");
		up.setStyle("-fx-background-color: #2B4365; -fx-text-fill: white; -fx-font-size: 14px; -fx-pref-width: 120px; -fx-pref-height: 40px;");
		endGame.setStyle("-fx-background-color: #2B4365; -fx-text-fill: white; -fx-font-size: 14px; -fx-pref-width: 120px; -fx-pref-height: 40px;");
		cure.setStyle("-fx-background-color: #2B4365; -fx-text-fill: white; -fx-font-size: 14px; -fx-pref-width: 120px; -fx-pref-height: 40px;");
		setTarget.setStyle("-fx-background-color: #2B4365; -fx-text-fill: white; -fx-font-size: 14px; -fx-pref-width: 120px; -fx-pref-height: 40px;");
		attack.setStyle("-fx-background-color: #2B4365; -fx-text-fill: white; -fx-font-size: 14px; -fx-pref-width: 120px; -fx-pref-height: 40px;");
		useSpecial.setStyle("-fx-background-color: #2B4365; -fx-text-fill: white; -fx-font-size: 14px; -fx-pref-width: 120px; -fx-pref-height: 40px;");
		// Set circular shape for the buttons container

		buttonsContainer.getChildren().addAll( up, down, right, left,endGame, useSpecial,attack, setTarget, cure);

		HBox box = new HBox();
//		box.setAlignment(Pos.TOP_RIGHT);
		StackPane root = new StackPane();

		AtomicReference<GridPane> gridPaneRef = new AtomicReference<>(generateMap());
		gridPaneRef.get().setAlignment(Pos.CENTER);
		gridPaneRef.get().setHgap(2);
		gridPaneRef.get().setVgap(2);
		gridPaneRef.get().setAlignment(Pos.CENTER);

		StackPane z = addnewInfo(this.h);
		box.getChildren().add(z);
		int zIndex = box.getChildren().indexOf(z);

		VBox l = new VBox();
		l.getChildren().add(createCardLayout());
		l.setAlignment(Pos.TOP_RIGHT);
		useSpecial.setOnAction(event -> {
			try {
				h.useSpecial();
				if (Game.checkGameOver()) {
					Alert alert = new Alert(AlertType.WARNING);
					alert.setTitle("Message");
					alert.setHeaderText(null);
					alert.setContentText("Please select a hero");

					alert.showAndWait();
				}
				GridPane newGridPane = generateMap();
				// Remove the old GridPane from the root
				root.getChildren().remove(gridPaneRef.get());

				// Add the newGridPane to the root
				root.getChildren().add(0, newGridPane);

				// Update the gridPaneRef with the newGridPane
				gridPaneRef.set(newGridPane);

				// Set the alignment and other properties for the newGridPane
				newGridPane.setAlignment(Pos.CENTER);
				newGridPane.setHgap(2);
				newGridPane.setVgap(2);
				StackPane.setAlignment(gridPaneRef.get(), Pos.CENTER);
				StackPane m = addnewInfo(h);

				box.getChildren().remove(zIndex);
				box.getChildren().add(zIndex, m);

	

			} catch (InvalidTargetException | NoAvailableResourcesException e) {
				Alert alert = new Alert(AlertType.WARNING);
				alert.setTitle("Message");
				alert.setHeaderText(null);
				alert.setContentText(e.getMessage());

				alert.showAndWait();
			}
		});
		attack.setOnAction(event -> {
			try {
				h.attack();
		
				GridPane newGridPane = generateMap();
				// Remove the old GridPane from the root
				root.getChildren().remove(gridPaneRef.get());

				// Add the newGridPane to the root
				root.getChildren().add(0, newGridPane);

				// Update the gridPaneRef with the newGridPane
				gridPaneRef.set(newGridPane);

				// Set the alignment and other properties for the newGridPane
				newGridPane.setAlignment(Pos.CENTER);
				newGridPane.setHgap(2);
				newGridPane.setVgap(2);
				StackPane.setAlignment(gridPaneRef.get(), Pos.CENTER);
				StackPane m = addnewInfo(h);

				box.getChildren().remove(zIndex);
				box.getChildren().add(zIndex, m);
			
			} catch (InvalidTargetException | NotEnoughActionsException e) {
				Alert alert = new Alert(AlertType.WARNING);
				alert.setTitle("Message");
				alert.setHeaderText(null);
				alert.setContentText(e.getMessage());

				alert.showAndWait();
			}
		});
		cure.setOnAction(event -> {
			try {
				h.cure();
		
				GridPane newGridPane = generateMap();
				// Remove the old GridPane from the root
				root.getChildren().remove(gridPaneRef.get());

				// Add the newGridPane to the root
				root.getChildren().add(0, newGridPane);

				// Update the gridPaneRef with the newGridPane
				gridPaneRef.set(newGridPane);

				// Set the alignment and other properties for the newGridPane
				newGridPane.setAlignment(Pos.CENTER);
				newGridPane.setHgap(2);
				newGridPane.setVgap(2);
				StackPane.setAlignment(gridPaneRef.get(), Pos.CENTER);
				StackPane m = addnewInfo(h);

				box.getChildren().remove(zIndex);
				box.getChildren().add(zIndex, m);
				
				l.getChildren().remove(0);
				l.getChildren().add(createCardLayout());
			} catch (NoAvailableResourcesException | InvalidTargetException | NotEnoughActionsException e) {
				Alert alert = new Alert(AlertType.WARNING);
				alert.setTitle("Message");
				alert.setHeaderText(null);
				alert.setContentText(e.getMessage());

				alert.showAndWait();
			}
		});

		setTarget.setOnAction(event -> {
			check[0] = true;

		});
		endGame.setOnAction(event -> {
			try {
				Game.endTurn();
		
				GridPane newGridPane = generateMap();

				// Remove the old GridPane from the root
				root.getChildren().remove(gridPaneRef.get());

				// Add the newGridPane to the root
				root.getChildren().add(0, newGridPane);

				// Update the gridPaneRef with the newGridPane
				gridPaneRef.set(newGridPane);

				// Set the alignment and other properties for the newGridPane
				newGridPane.setAlignment(Pos.CENTER);
				newGridPane.setHgap(2);
				newGridPane.setVgap(2);
				StackPane.setAlignment(gridPaneRef.get(), Pos.CENTER);
				StackPane m = addnewInfo(h);

				box.getChildren().remove(zIndex);
				box.getChildren().add(zIndex, m);
				
	
			} catch (NotEnoughActionsException | InvalidTargetException e) {
				Alert alert = new Alert(AlertType.WARNING);
				alert.setTitle("Message");
				alert.setHeaderText(null);
				alert.setContentText(e.getMessage());

				alert.showAndWait();
			}

		});

		// Register event handler for the right
		right.setOnAction(event -> {
			// Define the specific function or action you want to perform

			try {
				h.move(Direction.RIGHT);
			
				GridPane newGridPane = generateMap();

				// Remove the old GridPane from the root
				root.getChildren().remove(gridPaneRef.get());

				// Add the newGridPane to the root
				root.getChildren().add(0, newGridPane);

				// Update the gridPaneRef with the newGridPane
				gridPaneRef.set(newGridPane);

				// Set the alignment and other properties for the newGridPane
				newGridPane.setAlignment(Pos.CENTER);
				newGridPane.setHgap(2);
				newGridPane.setVgap(2);
				StackPane.setAlignment(gridPaneRef.get(), Pos.CENTER);
				StackPane m = addnewInfo(h);

				box.getChildren().remove(zIndex);
				box.getChildren().add(zIndex, m);
				
			} catch (NotEnoughActionsException | MovementException e) {
				Alert alert = new Alert(AlertType.WARNING);
				alert.setTitle("Message");
				alert.setHeaderText(null);
				alert.setContentText(e.getMessage());

				alert.showAndWait();
			}

		});
		left.setOnAction(event -> {
			// Define the specific function or action you want to perform
			try {
				h.move(Direction.LEFT);
			
				GridPane newGridPane = generateMap();

				// Remove the old GridPane from the root
				root.getChildren().remove(gridPaneRef.get());

				// Add the newGridPane to the root
				root.getChildren().add(0, newGridPane);

				// Update the gridPaneRef with the newGridPane
				gridPaneRef.set(newGridPane);

				// Set the alignment and other properties for the newGridPane
				newGridPane.setAlignment(Pos.CENTER);
				newGridPane.setHgap(2);
				newGridPane.setVgap(2);
				StackPane.setAlignment(gridPaneRef.get(), Pos.CENTER);
				StackPane m = addnewInfo(h);

				box.getChildren().remove(zIndex);
				box.getChildren().add(zIndex, m);
			
			} catch (NotEnoughActionsException | MovementException e) {
				Alert alert = new Alert(AlertType.WARNING);
				alert.setTitle("Message");
				alert.setHeaderText(null);
				alert.setContentText(e.getMessage());

				alert.showAndWait();
			}
		});
		// Register event handler for the right
		up.setOnAction(event -> {
			// Define the specific function or action you want to perform
			try {
				h.move(Direction.UP);
	
				GridPane newGridPane = generateMap();

				// Remove the old GridPane from the root
				root.getChildren().remove(gridPaneRef.get());

				// Add the newGridPane to the root
				root.getChildren().add(0, newGridPane);

				// Update the gridPaneRef with the newGridPane
				gridPaneRef.set(newGridPane);

				// Set the alignment and other properties for the newGridPane
				newGridPane.setAlignment(Pos.CENTER);
				newGridPane.setHgap(2);
				newGridPane.setVgap(2);
				StackPane.setAlignment(gridPaneRef.get(), Pos.CENTER);
				StackPane m = addnewInfo(h);

				box.getChildren().remove(zIndex);
				box.getChildren().add(zIndex, m);
			
			} catch (NotEnoughActionsException | MovementException e) {
				Alert alert = new Alert(AlertType.WARNING);
				alert.setTitle("Message");
				alert.setHeaderText(null);
				alert.setContentText(e.getMessage());

				alert.showAndWait();
			}
		});

		down.setOnAction(event -> {
			// Define the specific function or action you want to perform
			try {
				h.move(Direction.DOWN);
			
				GridPane newGridPane = generateMap();

				// Remove the old GridPane from the root
				root.getChildren().remove(gridPaneRef.get());

				// Add the newGridPane to the root
				root.getChildren().add(0, newGridPane);

				// Update the gridPaneRef with the newGridPane
				gridPaneRef.set(newGridPane);

				// Set the alignment and other properties for the newGridPane
				newGridPane.setAlignment(Pos.CENTER);
				newGridPane.setHgap(2);
				newGridPane.setVgap(2);
				StackPane.setAlignment(gridPaneRef.get(), Pos.CENTER);
				StackPane m = addnewInfo(h);

				box.getChildren().remove(zIndex);
				box.getChildren().add(zIndex, m);

			} catch (NotEnoughActionsException | MovementException e) {
				Alert alert = new Alert(AlertType.WARNING);
				alert.setTitle("Message");
				alert.setHeaderText(null);
				alert.setContentText(e.getMessage());

				alert.showAndWait();
			}
		});

		root.getChildren().addAll(gridPaneRef.get(), l, box, buttonsContainer);
		StackPane.setAlignment(gridPaneRef.get(), Pos.CENTER);
		StackPane.setAlignment(l, Pos.CENTER_RIGHT);
		root.setPadding(new Insets(100));

		Scene scene = new Scene(root, 1000, 1200);
		return scene;

	}


	public Scene startScene4() {
		primaryStage.setFullScreen(true);
		primaryStage.setTitle("The Last Of Us Legacy");
		Image backgroundImage = new Image(
				"C:\\Users\\Yousef Elbrolosy\\Desktop\\GUC\\Semester 4\\Milestone2-Solution\\images\\GameWin.png");
		ImageView backgroundImageView = new ImageView(backgroundImage);

		StackPane layout = new StackPane();
		layout.getChildren().addAll(backgroundImageView);
		layout.setPadding(new Insets(100));

		Scene scene = new Scene(layout, backgroundImage.getWidth(), backgroundImage.getHeight());
		return scene;

	}
	public VBox createCardLayout() {
	    VBox cardContainer = new VBox(); // Use HBox to hold the cards
	    cardContainer.setSpacing(10); // Set spacing between cards
	    cardContainer.setAlignment(Pos.CENTER_RIGHT); // Set alignment of cards

	    for (Hero x : Game.heroes) {
	        Label name = new Label("name: " + x.getName());
	        name.setFont(Font.font("Arial", FontWeight.BOLD, 15));
	        name.setTextFill(Color.BLACK);

	        int curHp = x.getCurrentHp();
	        Label curH = new Label("CurrentHp: " + curHp);
	        curH.setFont(Font.font("Arial", FontWeight.BOLD, 15));
	        curH.setTextFill(Color.BLACK);

	        int attDmg = x.getAttackDmg();
	        Label attDamage = new Label("Max damage: " + attDmg);
	        attDamage.setFont(Font.font("Arial", FontWeight.BOLD, 15));
	        attDamage.setTextFill(Color.BLACK);

	        int maxAc = x.getMaxActions();
	        Label maxActions = new Label("Max actions: " + maxAc);
	        maxActions.setFont(Font.font("Arial", FontWeight.BOLD, 15));
	        maxActions.setTextFill(Color.BLACK);

	        String z = (x instanceof Fighter) ? "Fighter" : (x instanceof Explorer) ? "Explorer" : "Medic";
	        Label type = new Label("Hero type: " + z);
	        type.setFont(Font.font("Arial", FontWeight.BOLD, 15));
	        type.setTextFill(Color.BLACK);

	        Rectangle card = new Rectangle(250, 200, Color.TRANSPARENT);
	        StackPane cardPane = new StackPane(card);

	        StackPane labelsContainer = new StackPane();
	        labelsContainer.getChildren().addAll(name, curH, attDamage, maxActions, type);

	        cardPane.getChildren().add(labelsContainer);
	        cardContainer.getChildren().add(cardPane); // Add each card to the card container
	        name.setTranslateY(-50);
			type.setTranslateY(-20);
			curH.setTranslateY(20);
			attDamage.setTranslateY(50);
			maxActions.setTranslateY(80);
	    }

	    return cardContainer;
	}
	

	public Scene startScene5() {
		primaryStage.setFullScreen(true);
		primaryStage.setTitle("The Last Of Us Legacy");
		Image backgroundImage = new Image(
				"C:\\Users\\Yousef Elbrolosy\\Desktop\\GUC\\Semester 4\\Milestone2-Solution\\images\\GameOver.png");
		ImageView backgroundImageView = new ImageView(backgroundImage);

		StackPane layout = new StackPane();
		layout.getChildren().addAll(backgroundImageView);
		layout.setPadding(new Insets(100));

		Scene scene = new Scene(layout, backgroundImage.getWidth(), backgroundImage.getHeight());
		return scene;
	}

	public GridPane generateMap() {
		brute = true;
		if (Game.checkWin()) {
			Alert alert = new Alert(AlertType.WARNING);
			alert.setTitle("Message");
			alert.setHeaderText(null);
			alert.setContentText("Win");
			alert.showAndWait();
			Scene four = startScene4();
			FadeTransition fadeTransition = new FadeTransition(Duration.millis(500), primaryStage.getScene().getRoot());
			fadeTransition.setFromValue(1.0);
			fadeTransition.setToValue(0.0);
			fadeTransition.setOnFinished(e -> {
				primaryStage.setScene(four);
				primaryStage.setFullScreen(true);
			});
			fadeTransition.play();

		}
		if (Game.checkGameOver()) {
			Alert alert = new Alert(AlertType.WARNING);
			alert.setTitle("Message");
			alert.setHeaderText(null);
			alert.setContentText("Lose");

			alert.showAndWait();
			Scene five = startScene5();
			FadeTransition fadeTransition = new FadeTransition(Duration.millis(500), primaryStage.getScene().getRoot());
			fadeTransition.setFromValue(1.0);
			fadeTransition.setToValue(0.0);
			fadeTransition.setOnFinished(e -> {
				primaryStage.setScene(five);
				primaryStage.setFullScreen(true);
			});
			fadeTransition.play();

		}

		GridPane gridPane = new GridPane();
		AtomicReference<Hero> selectedHero = new AtomicReference<>(null); // AtomicReference to store the selected hero
		for (int row = 14; row >= 0; row--) {
			final int finalrow = row;

			for (int col = 0; col < Game.map[0].length; col++) {
				final int finalCol = col;
				Button cell = new Button();
				cell.setPrefSize(60, 60);
				if (Game.map[row][col].isVisible()) {
					cell.setStyle("-fx-background-color: black;");

				}
				cell.setOnAction(e -> {

					if (Game.map[finalrow][finalCol] instanceof CharacterCell
							&& ((CharacterCell) Game.map[finalrow][finalCol]).getCharacter() instanceof Hero) {
						selectedHero.set((Hero) ((CharacterCell) Game.map[finalrow][finalCol]).getCharacter());
						System.out.println("Selected Hero: " + selectedHero.get().getName());
						this.h = selectedHero.get();
					}
					if (check[0] == true) {
						selectedHero.get().setTarget(((CharacterCell) Game.map[finalrow][finalCol]).getCharacter());
						check[0] = false;
					}
				});
				if (Game.map[row][col] instanceof CharacterCell) {
					if (((CharacterCell) Game.map[row][col]).getCharacter() instanceof Hero)
						cell.setStyle("-fx-background-color: orange;");
					else if (((CharacterCell) Game.map[row][col]).getCharacter() instanceof Zombie)
						if (Game.map[row][col].isVisible()) {
							cell.setStyle("-fx-background-color: green;");

						}

				}

				if (Game.map[row][col] instanceof CollectibleCell) {
					if (((CollectibleCell) Game.map[row][col]).getCollectible() instanceof Vaccine) {
						if (Game.map[row][col].isVisible())

							cell.setStyle("-fx-background-color: yellow;");

					} else {
						if (Game.map[row][col].isVisible())

							cell.setStyle("-fx-background-color: blue;");

					}
				}

				gridPane.add(cell, col, 15 - row - 1);
			}

		}
		return gridPane;
	}

	public static Label newLabel(String x, int fontSize, FontWeight weight, Color color) {
		Label heroLabel = new Label(x);
		Font font = Font.font("Courier New", weight, fontSize);
		heroLabel.setFont(font);
		heroLabel.setTextFill(color);
		return heroLabel;
	}

}