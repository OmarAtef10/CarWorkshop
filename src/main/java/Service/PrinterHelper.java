package Service;

import Context.Context;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.print.PrinterJob;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;

public class PrinterHelper extends Application {
    // Create the JobStatus Label
    private Label jobStatus = new Label();

    public static void main(String[] args) {
        Application.launch(args);
    }

    @Override
    public void start(Stage stage) {
        // Create the VBox
        final VBox root = new VBox(5);

        // Create the Text Label
        Label textLabel = new Label("Please insert your Text:");

        // Create the TextArea
        final TextArea textArea = new TextArea();

        // Create the Buttons
        Button printTextButton = new Button("Print Text");
        Button printSceneButton = new Button("Print Scene");

        // Create the Event-Handlers for the Buttons
        printTextButton.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent event) {
                print(textArea);
            }
        });

        printSceneButton.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent event) {
                print(root);
            }
        });

        // Create the Status Box
        HBox jobStatusBox = new HBox(5, new Label("Job Status: "), jobStatus);
        // Create the Button Box
        HBox buttonBox = new HBox(5, printTextButton, printSceneButton);

        // Add the Children to the VBox
        root.getChildren().addAll(textLabel, textArea, buttonBox, jobStatusBox);
        // Set the Size of the VBox
        root.setPrefSize(400, 300);

        // Set the Style-properties of the VBox
        root.setStyle("-fx-padding: 10;" +
                "-fx-border-style: solid inside;" +
                "-fx-border-width: 2;" +
                "-fx-border-insets: 5;" +
                "-fx-border-radius: 5;" +
                "-fx-border-color: blue;" +
                "-fx-background-color: #ffffff");


       // root.getStylesheets().addAll(Context.getContext().getCurrentTheme());
        // Create the Scene
        Scene scene = new Scene(root);
        // Add the scene to the Stage
        stage.setScene(scene);
        // Set the title of the Stage
        stage.setTitle("A Printing Nodes Example");
        // Display the Stage
        stage.show();
    }

    private void print(Node node) {
        // Define the Job Status Message
        jobStatus.textProperty().unbind();
        jobStatus.setText("Creating a printer job...");

        // Create a printer job for the default printer
        PrinterJob job = PrinterJob.createPrinterJob();

        if (job != null) {
            // Show the printer job status
            jobStatus.textProperty().bind(job.jobStatusProperty().asString());

            // Print the node
            boolean printed = job.printPage(node);

            if (printed) {
                // End the printer job
                job.endJob();
            } else {
                // Write Error Message
                jobStatus.textProperty().unbind();
                jobStatus.setText("Printing failed.");
            }
        } else {
            // Write Error Message
            jobStatus.setText("Could not create a printer job.");
        }
    }
}
