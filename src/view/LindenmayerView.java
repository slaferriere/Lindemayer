package view;

import java.util.HashMap;
import java.util.Map;

import controller.LindenmayerController;
import javafx.application.Application;
import javafx.beans.InvalidationListener;
import javafx.beans.Observable;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import model.LindenmayerModel;

/**
 * 
 * @author slaferriere
 * 
 * This class is the textual GUI for the game, Mastermind. It opens a JavaFX GUI and runs through the game of Mastermind, asking
 * the users for guesses until the user runs out of his/her 10 guesses OR gets the correct answer.
 *
 */


public class LindenmayerView extends Application implements Observable {
	
	private TextField xPosition = new TextField();
	private TextField yPosition = new TextField();
	private TextField angle = new TextField();
	private TextField axiom = new TextField();
	private TextField mapOne = new TextField();
	private TextField mapOneMapping = new TextField();
	private TextField mapTwo = new TextField();
	private TextField mapTwoMapping = new TextField();
	private VBox vbox;
	private StackPane stackPane = new StackPane();
	private ScrollPane scrollPane = new ScrollPane();
	private Canvas canvas = new Canvas(5000, 5000);
	private Slider slider = new Slider(0, 10, 6);
	private Label sliderLabel;
	private GraphicsContext gc = canvas.getGraphicsContext2D();
	private LindenmayerModel model = new LindenmayerModel();
	private LindenmayerController controller = new LindenmayerController(model);
	
	@Override
	public void start(Stage primaryStage) {
		BorderPane window = new BorderPane();
		gc.setFill(Color.WHITE);
		gc.fillRect(0, 0, 5000, 50000);
		createVbox();
		getTextFields();
		stackPane.getChildren().add(canvas);
		scrollPane.setContent(stackPane);
		
	//	stackPane.setStyle("-fx-background-color: red");
		window.setLeft(vbox);
		window.setCenter(scrollPane);
		
		Scene scene = new Scene(window);
		primaryStage.setTitle("Lindenmayer System Visualizer");
		primaryStage.setScene(scene);
		primaryStage.show();
	}

	private void createVbox() {
		
		Label posLabel = new Label("Initial Position");
		posLabel.setFont(new Font(20));
		HBox positionLabel = new HBox(posLabel);
		positionLabel.setAlignment(Pos.CENTER);
		
		Label itLabel = new Label("Iterations");
		itLabel.setFont(new Font(20));
		HBox iterationLabel = new HBox(itLabel);
		iterationLabel.setAlignment(Pos.CENTER);
		
		slider.setBlockIncrement(1.0);
		sliderLabel = new Label(Double.toString(slider.getValue()));
		sliderLabel.setFont(new Font(20));
		HBox sliderBox = new HBox(slider, sliderLabel);
		sliderBox.setAlignment(Pos.CENTER);
		
		Label xLabel = new Label("X");
		xLabel.setFont(new Font(20));
		Label yLabel = new Label("Y");
		yLabel.setFont(new Font(20));

		HBox positionBox = new HBox(xLabel, 
		 							xPosition,
		 							yLabel,
		 							yPosition);
		positionBox.setAlignment(Pos.CENTER);
		
		
		Label angleLabel = new Label("Angle");
		angleLabel.setFont(new Font(20));
		HBox angleBox = new HBox(angleLabel);
		angleBox.setAlignment(Pos.CENTER);
		
		Label axLabel = new Label("Axiom");
		axLabel.setFont(new Font(20));
		HBox axiomLabel = new HBox(axLabel);
		axiomLabel.setAlignment(Pos.CENTER);
		
		Label mapLabel = new Label("Mappings");
		mapLabel.setFont(new Font(20));
		HBox mappingLabel = new HBox(mapLabel);
		mappingLabel.setAlignment(Pos.CENTER);
		
		mapOne.setText("F");
		mapOne.setEditable(false);
		mapTwo.setText("G");
		mapTwo.setEditable(false);
		HBox mapOneBox = new HBox(mapOne,
				 				  new Label("->"),
				 				  mapOneMapping);
		mapOneBox.setAlignment(Pos.CENTER);
		HBox mapTwoBox = new HBox(mapTwo,
				  				 new Label("->"),
				  				 mapTwoMapping);
		mapTwoBox.setAlignment(Pos.CENTER);
		vbox = new VBox(positionLabel,
						positionBox,
						iterationLabel,
						sliderBox,
						angleBox,
						angle,
						axiomLabel,
						axiom,
						mappingLabel,
						mapOneBox,
						mapTwoBox);
		
		vbox.setPadding(new Insets(10, 10, 10, 10));
		vbox.setSpacing(10);

		
	}
	
	private class TextHandler implements EventHandler<KeyEvent> {
		
		private String map1;
		private String map1mapping;
		private String map2;
		private String map2mapping;
		private String ax;
		private double xPos;
		private double yPos;
		private double ang;
		private int iterations;
		
		@Override
		public void handle(KeyEvent event) {
			
			
			if(event.getCode() == KeyCode.ENTER) {
				xPos = new Double(xPosition.getText());
				yPos = new Double(yPosition.getText());
				ang = new Double(angle.getText());
				iterations = (int) slider.getValue();
				ax = axiom.getText();
				map1 = mapOne.getText();
			    map1mapping = mapOneMapping.getText();
			    map2 = mapTwo.getText();
			    map2mapping = mapTwoMapping.getText();
				Map<String, String> map = new HashMap<String, String>();
				map.put(map1, map1mapping);
				map.put(map2, map2mapping);
				controller.makeReplacements(iterations, ax, map);
				drawOnCanvas();				
				
			}
			

			
		}
		
		public void drawOnCanvas() {
			gc.fillRect(0, 0, 5000, 50000);
			TurtleGraphics turtleGraphic = new TurtleGraphics(new Double(xPos), new Double(yPos), new Double(ang));
			String system = controller.getReplacements();
			for (int i = 0; i < system.length(); i++) {
				if(system.charAt(i) == '+' || system.charAt(i) == '-') {
					turtleGraphic.updateHeading(system.charAt(i));
				} else if (system.charAt(i) == 'F' || system.charAt(i) == 'G') {
					turtleGraphic.updatePosition();
				} else if (system.charAt(i) == '[' || system.charAt(i) == ']') {
					turtleGraphic.updateStack(system.charAt(i), system.substring(0, i));
				}
				
				gc.strokeLine(xPos, yPos, turtleGraphic.getxPosition(), turtleGraphic.getyPosition());
				xPos = turtleGraphic.getxPosition();
				yPos = turtleGraphic.getyPosition();
			}
		}
	}
	
	public void getTextFields() {
		
		xPosition.setOnKeyPressed(new TextHandler());
		xPosition.setOnKeyPressed(new TextHandler());
		yPosition.setOnKeyPressed(new TextHandler());
		angle.setOnKeyPressed(new TextHandler());
		axiom.setOnKeyPressed(new TextHandler());
		mapOne.setOnKeyPressed(new TextHandler());
		mapOneMapping.setOnKeyPressed(new TextHandler());
		mapTwo.setOnKeyPressed(new TextHandler());
		mapTwoMapping.setOnKeyPressed(new TextHandler());
		
		slider.valueProperty().addListener(new ChangeListener<Number>() {
            public void changed(ObservableValue<? extends Number> ov,
                Number old_val, Number new_val) {
                    System.out.println(new_val.doubleValue());
                    sliderLabel.setText(String.format("%.0f", new_val));
            }
        });
		
		canvas.setOnMouseClicked(new EventHandler<MouseEvent>() {
		    @Override
		    public void handle(MouseEvent event) {
		    	xPosition.setText(Double.toString(event.getSceneX()));
		    	yPosition.setText(Double.toString(event.getSceneY()));
		    }
		});
		
		
	}

	@Override
	public void addListener(InvalidationListener listener) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void removeListener(InvalidationListener listener) {
		// TODO Auto-generated method stub
		
	}



		
}