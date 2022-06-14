package com.example.projectjavafx;

import com.example.projectjavafx.Data.DBConnection;
import com.example.projectjavafx.Data.Model.Products;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;


import java.util.ArrayList;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class HelloApplication extends Application {

    private Scene scene;
    private static final String EMPTY = "";

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) {



        GridPane grid = new GridPane();
        grid.setAlignment(Pos.CENTER);
        grid.setVgap(10);
        grid.setHgap(10);
        DBConnection DB = new DBConnection();

        ArrayList<Products> productList = DB.getProducts();

        grid.add(new Label("Name:"), 0, 0);
        var tfName = new TextField();
        grid.add(tfName, 0, 1);
        //
        grid.add(new Label("Image:"), 1, 0);
        var tfImage = new TextField();
        grid.add(tfImage, 1, 1);
        //
        grid.add(new Label("Price:"), 2, 0);
        var tfPrice = new TextField();
        grid.add(tfPrice, 2, 1);
        //
        grid.add(new Label("Description:"),3,  0);
        var tfDescription = new TextField();
        grid.add(tfDescription, 3, 1);
        //

        // add
        var btnAdd = new Button("Add");
        btnAdd.setStyle("-fx-background-color: yellow");
        btnAdd.setPadding(new Insets(5, 15, 5, 15));
        btnAdd.setOnAction(e -> {
            String name = tfName.getText();
            String image = tfImage.getText();
            Integer price = Integer.valueOf(tfPrice.getText());
            String description = tfDescription.getText();
            if (!name.equals(EMPTY) && !image.equals(EMPTY) && !price.equals(EMPTY) && !description.equals(EMPTY)) {
                DB.insertProduct(new Products(name, image, price, description));
                try {
                    start(stage);
                } catch (Exception ex) {
                    throw new RuntimeException(ex);
                }
                return;
            }
            var alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setHeaderText(null);
            alert.setContentText("Please fill all blank!");
            alert.showAndWait();
        });
        grid.add(btnAdd, 4, 1);

        //show
        for(int i = 0; i < productList.size(); i++){

            Image image = new Image(productList.get(i).getImage());
            ImageView imageView = new ImageView();
            imageView.setImage(image);
            imageView.setFitWidth(110);
            imageView.setFitHeight(110);

            grid.add(new Label (productList.get(i).getName()), 0, i+2);
            grid.add(imageView, 1, i+2);
            grid.add(new Label ("$"+(productList.get(i).getPrice())), 2, i+2);
            grid.add(new Label (productList.get(i).getDescription()), 3, i+2);

            // Update
            var btnUpdate = new Button("Update");
            btnUpdate.setStyle("-fx-background-color: #00fff0");
            btnUpdate.setId(String.valueOf(i));
            btnUpdate.setOnAction(e -> {
                btnAdd.setVisible(false);
                int id1 = Integer.parseInt(btnUpdate.getId());
                TextField tfname = (TextField) grid.getChildren().get(1);
                tfname.setText("" + productList.get(id1).getName());
                TextField tfimage = (TextField) grid.getChildren().get(3);
                tfimage.setText("" + productList.get(id1).getImage());
//                name.setText(stdList.get(Integer.parseInt(btnUpdate.getId())).getName());
                TextField tfprice = (TextField) grid.getChildren().get(5);
                tfprice.setText("" + productList.get(id1).getPrice());
                TextField tfdescription = (TextField) grid.getChildren().get(7);
                tfdescription.setText("" + productList.get(id1).getDescription());
                var newbtnAdd = new Button("Update");
                newbtnAdd.setPadding(new Insets(5, 15, 5, 15));
                newbtnAdd.setOnAction(newe -> {
                    Integer Newid = productList.get(id1).id;
                    String Newname = tfname.getText();
                    String Newimage = tfimage.getText();
                    Integer Newprice = Integer.valueOf(tfprice.getText());
                    String Newdescription = tfdescription.getText();
                    if (!Newname.equals(EMPTY) && !Newimage.equals(EMPTY) && !Newprice.equals(EMPTY) && !Newdescription.equals(EMPTY)) {
                        DB.updateProduct(new Products(Newid, Newname, Newimage, Newprice, Newdescription));
                        try {
                            start(stage);
                        } catch (Exception ex) {
                            throw new RuntimeException(ex);
                        }
                        return;
                    }
                    var alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setHeaderText(null);
                    alert.setContentText("Please fill all blank!");
                    alert.showAndWait();
                });
                grid.add(newbtnAdd, 4, 1);
            });
            grid.add(btnUpdate, 4, i+2);

            // Delete
            var btnDelete = new Button("Delete");
            btnDelete.setStyle("-fx-background-color: #e61919");

            btnDelete.setId(String.valueOf(productList.get(i).id));
            btnDelete.setOnAction(e -> {
                int id3 = Integer.parseInt(btnDelete.getId());
                DB.deleteProduct(id3);
                var alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setHeaderText(null);
                alert.setContentText("Deleted!");
                alert.showAndWait();
                try {
                    start(stage);
                } catch (Exception ex) {
                    throw new RuntimeException(ex);
                }
            });
            grid.add(btnDelete, 5, i+2);
        }

        scene = new Scene(grid, 800, 700);
        stage.setTitle("Products Table");
        stage.setScene(scene);
        stage.show();
    }


}