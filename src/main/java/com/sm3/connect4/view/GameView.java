package com.sm3.connect4.view;

import com.sm3.connect4.view.Board;

import javafx.collections.FXCollections;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.*;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import com.sm3.connect4.model.ModelEvent;
import com.sm3.connect4.model.ModelListener;
import javafx.application.Application;


public class GameView implements Window, ModelListener{
	
	private final String spstart = "SP";
	private final String mpstart = "MP";
        private final String lbstart = "LB";
	private String type;
	private Stage stage;
	private Board board;
	private TextArea gameinfo;
	private char playerColor = ' ';
    
    public void displayWindow(String type) {
		this.stage = new Stage();
		this.board = new Board();
		this.type = type;
		if (type == spstart) {
			stage.setScene(new Scene(spmenu()));
			stage.show();
		} 
		else if (type == mpstart) {
			stage.setScene(new Scene(mpmenu()));
			stage.show();
		}
                else if (type == lbstart){
                        lbmenu();
                }
    	
	}
	
	private Pane spmenu() {
		
		//Setup for difficulty choice row
		ChoiceBox<String> difficultycb = new ChoiceBox<String>(FXCollections.observableArrayList("Easy", "Hard"));
		difficultycb.setValue("Easy");
		HBox hbox1 = new HBox();
		hbox1.setPadding(new Insets(10,25,0,25));
		hbox1.getChildren().add(new Text("Difficulty: "));
                hbox1.getChildren().add((difficultycb));
                
		//Setup for player color choice row
		ChoiceBox<String> playercolorcb = new ChoiceBox<String>(FXCollections.observableArrayList("Red", "Yellow"));
		playercolorcb.setValue("Red");
		
		hbox1.setPadding(new Insets(10,10,0,10));
		hbox1.getChildren().add(new Text("     Player Color: "));
                hbox1.getChildren().add(( playercolorcb));
                
		//Start game button
                HBox hbox2 = new HBox();
                hbox2.setPadding(new Insets(-15, 25, 10, 25));
                hbox2.setSpacing(15);
		Button startbutton = new Button("START");
                Button returnbutton = new Button("RETURN");
                
                
        startbutton.setOnAction(e -> 
        {
        	if (e.getSource() == startbutton) {
				this.playerColor = playercolorcb.getSelectionModel().getSelectedItem().toString() == "Red" ? 'r' : 'y';
				ViewHandler.spStart(difficultycb.getSelectionModel().getSelectedItem(), playercolorcb.getSelectionModel().getSelectedItem(), this);
				stage.setScene(new Scene(gameView()));
			}
        		
        });
        returnbutton.setOnAction(e -> 
        {
        	if (e.getSource() == returnbutton) {
				stage.close();
				e.consume();
			}
        		
        });

		//combines rows and button
		VBox vbox = new VBox(30);
		vbox.setAlignment(Pos.CENTER);
                hbox2.getChildren().add(startbutton);
                hbox2.getChildren().add(returnbutton);
                hbox2.setAlignment(Pos.CENTER);
		vbox.getChildren().addAll(hbox1, hbox2);
                
		return vbox;
	}

	private Pane mpmenu() {
				//Setup for difficulty choice row
		//Setup for difficulty choice row
		ChoiceBox<String> connectioncb = new ChoiceBox<String>(FXCollections.observableArrayList("Local", "Online"));
		connectioncb.setValue("Local");
		HBox hbox1 = new HBox();
		hbox1.setPadding(new Insets(10,25,0,25));
		hbox1.getChildren().add(new Text("Connection: "));
                hbox1.getChildren().add((connectioncb));
                
		//Setup for player color choice row
		ChoiceBox<String> playercolorcb = new ChoiceBox<String>(FXCollections.observableArrayList("Red", "Yellow"));
		playercolorcb.setValue("Red");
		
		hbox1.setPadding(new Insets(10,10,0,10));
		hbox1.getChildren().add(new Text("     Player Color: "));
                hbox1.getChildren().add((playercolorcb));
                
		//Start game button
                HBox hbox2 = new HBox();
                hbox2.setPadding(new Insets(-15, 25, 10, 25));
                hbox2.setSpacing(15);
		Button startbutton = new Button("START");
                Button returnbutton = new Button("RETURN");
                
                
        startbutton.setOnAction(e -> 
        {
        	if (e.getSource() == startbutton) {
				this.playerColor = playercolorcb.getSelectionModel().getSelectedItem().toString() == "Red" ? 'r' : 'y';
				ViewHandler.spStart(connectioncb.getSelectionModel().getSelectedItem(), playercolorcb.getSelectionModel().getSelectedItem(), this);
				stage.setScene(new Scene(gameView()));
			}
        		
        });
        returnbutton.setOnAction(e -> 
        {
        	if (e.getSource() == returnbutton) {
				
                    
                                stage.close();
				e.consume();
			}
        		
        });

		//combines rows and button
		VBox vbox = new VBox(30);
		vbox.setAlignment(Pos.CENTER);
                hbox2.getChildren().add(startbutton);
                hbox2.getChildren().add(returnbutton);
                hbox2.setAlignment(Pos.CENTER);
		vbox.getChildren().addAll(hbox1, hbox2);
                
		return vbox;
	}
        
        private void lbmenu(){
                //TODO
        }

	private Pane gameView() {
		/* REMOVED BACKGROUND IMAGE
		//loads background image
		////ImageView image = new ImageView(new Image(("rsc/background1.jpg")));

		//combines board with background
		StackPane spane = new StackPane();
		//spane.setMargin(board.createBoardPane(), new Insets(60, 160, 40, 160));
		spane.getChildren().addAll(image, board.createBoardPane());
		spane.setMargin(spane.getChildren().get(1), new Insets(60, 160, 40, 160));
		*/

		//creates textfield 
		TextArea gameinfo = new TextArea();
		gameinfo.setId("gameinfo");
		gameinfo.setEditable(false);
		gameinfo.setPrefHeight(60);
		gameinfo.setText("Game start.");
		this.gameinfo = gameinfo;

		VBox vbox = new VBox(board.createBoardPane(), gameinfo);
    	return vbox;
	}

	// Now implement the necessary event handling code 
	public void modelChanged(ModelEvent event) 
	{
		if (type == spstart) {
			if (event.getContent()) {
				board.placeDisc(event.getColor(), event.getColumn(), event.getRow());
				gameinfo.appendText("\n" + event.getMessage());
				if (event.getWin()) {
					String msg = event.getColor() == 'r' ? "Red wins!!!" : "Yellow wins!!!";
					gameinfo.appendText("\n" + msg);
				}
				gameinfo.setScrollTop(Double.MAX_VALUE);

				if (event.getColor() == this.playerColor)
					ViewHandler.botTurn();
				else
					board.setPlayerMove(true);
			}
			else {
				gameinfo.appendText("\n" + event.getMessage());
				board.setPlayerMove(true);
				gameinfo.setScrollTop(Double.MAX_VALUE);
			}
			return;
		}

	}
}
